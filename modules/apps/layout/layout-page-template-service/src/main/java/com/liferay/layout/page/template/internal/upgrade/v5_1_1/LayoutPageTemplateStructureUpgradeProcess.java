/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.internal.upgrade.v5_1_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutModel;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Márk Gulácsy
 */
public class LayoutPageTemplateStructureUpgradeProcess extends UpgradeProcess {

	public LayoutPageTemplateStructureUpgradeProcess(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_deleteOrphanLayoutPageTemplateStructures();
		_processLayoutPageTemplateStructuresOfWidgetLayouts();
	}

	private String[][] _createIdBatches(String[] ids, int size) {
		return (String[][])ArrayUtil.split(ids, size);
	}

	private String _createInClause(String[] idBatch) {
		StringBundler sb = new StringBundler(idBatch.length + 1);

		sb.append("in (?");

		for (int i = 1; i < idBatch.length; i++) {
			sb.append(", ?");
		}

		sb.append(")");

		return sb.toString();
	}

	private void _deleteOrphanLayoutPageTemplateStructures() throws Exception {
		ArrayList<Long> layoutPageTemplateStructureIds = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select layoutPageTemplateStructureId from " +
					"LayoutPageTemplateStructure where classPK not in " +
						"(select plid from Layout)")) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					layoutPageTemplateStructureIds.add(
						resultSet.getLong("layoutPageTemplateStructureId"));
				}
			}
		}

		_deleteWidgetLayoutsTemplateStructureRels(
			layoutPageTemplateStructureIds);

		String sql =
			"delete from LayoutPageTemplateStructure where classPK not in " +
				"(select plid from Layout)";

		runSQL(sql);
	}

	private void _deleteWidgetLayoutsTemplateStructureRels(
			ArrayList<Long> layoutPageTemplateStructureIds)
		throws Exception {

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"delete from LayoutPageTemplateStructureRel where " +
						"layoutPageTemplateStructureId = ?")) {

			for (long id : layoutPageTemplateStructureIds) {
				preparedStatement.setLong(1, id);
				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private boolean _isOracleDB() {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.ORACLE) {
			return true;
		}

		return false;
	}

	private void _processLayoutPageTemplateStructuresOfWidgetLayouts()
		throws Exception {

		DynamicQuery widgetLayoutsQuery = _layoutLocalService.dynamicQuery();

		widgetLayoutsQuery.add(
			RestrictionsFactoryUtil.eq("type", LayoutConstants.TYPE_PORTLET));

		List<Layout> widgetLayouts = _layoutLocalService.dynamicQuery(
			widgetLayoutsQuery);

		Stream<Layout> widgetLayoutsStream = widgetLayouts.stream();

		String[] widgetPlids = widgetLayoutsStream.mapToLong(
			LayoutModel::getPlid
		).boxed(
		).map(
			String::valueOf
		).toArray(
			String[]::new
		);

		int batchSize;
		String sql =
			"select layoutPageTemplateStructureId, classPK from " +
				"LayoutPageTemplateStructure where classPK ";

		if (_isOracleDB()) {
			batchSize = 1000;
		}
		else {
			batchSize = widgetPlids.length;
		}

		String[][] idBatches = _createIdBatches(widgetPlids, batchSize);

		for (String[] batch : idBatches) {
			String inClause = _createInClause(batch);

			_processOneBatchOfNonorphanPagesAndStructures(
				sql + inClause, batch);
		}
	}

	private void _processOneBatchOfNonorphanPagesAndStructures(
			String sql, String[] idBatch)
		throws Exception {

		ArrayList<Long> widgetLayoutsWithStructurePlids = new ArrayList<>();
		ArrayList<Long> widgetLayoutTemplateStructureIds = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(sql))) {

			for (int i = 0; i < idBatch.length; i++) {
				preparedStatement.setString(i + 1, idBatch[i]);
			}

			ResultSet widgetLayoutTemplateStructuresResultSet =
				preparedStatement.executeQuery();

			while (widgetLayoutTemplateStructuresResultSet.next()) {
				widgetLayoutsWithStructurePlids.add(
					widgetLayoutTemplateStructuresResultSet.getLong("classPK"));
				widgetLayoutTemplateStructureIds.add(
					widgetLayoutTemplateStructuresResultSet.getLong(
						"layoutPageTemplateStructureId"));
			}
		}

		DynamicQuery draftWidgetLayoutsWithStructureQuery =
			_layoutLocalService.dynamicQuery();

		draftWidgetLayoutsWithStructureQuery.add(
			RestrictionsFactoryUtil.and(
				RestrictionsFactoryUtil.in(
					"plid", widgetLayoutsWithStructurePlids),
				RestrictionsFactoryUtil.eq(
					"status", WorkflowConstants.STATUS_DRAFT)));

		List<Layout> draftWidgetLayoutsWithStructure =
			_layoutLocalService.dynamicQuery(
				draftWidgetLayoutsWithStructureQuery);

		ServiceContext serviceContext = new ServiceContext();

		for (Layout layout : draftWidgetLayoutsWithStructure) {
			_layoutLocalService.updateStatus(
				layout.getUserId(), layout.getPlid(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		}

		_deleteWidgetLayoutsTemplateStructureRels(
			widgetLayoutTemplateStructureIds);

		try (PreparedStatement preparedStatement1 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"delete from LayoutPageTemplateStructure where classPK =" +
						"?")) {

			for (long plid : widgetLayoutsWithStructurePlids) {
				preparedStatement1.setLong(1, plid);
				preparedStatement1.addBatch();
			}

			preparedStatement1.executeBatch();
		}
	}

	private final LayoutLocalService _layoutLocalService;

}