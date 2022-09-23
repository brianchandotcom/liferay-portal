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

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

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

	private void _deleteOrphanLayoutPageTemplateStructures() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select layoutPageTemplateStructureId from " +
						"LayoutPageTemplateStructure where classPK not in " +
							"(select plid from Layout)");
			PreparedStatement deletePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from LayoutPageTemplateStructureRel where " +
						"layoutPageTemplateStructureId = ?")) {

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					deletePreparedStatement.setLong(
						1, resultSet.getLong("layoutPageTemplateStructureId"));

					deletePreparedStatement.addBatch();
				}
			}

			deletePreparedStatement.executeBatch();
		}

		runSQL(
			"delete from LayoutPageTemplateStructure where classPK not in " +
				"(select plid from Layout)");
	}

	private void _processLayoutPageTemplateStructuresOfWidgetLayouts()
		throws Exception {

		List<Long> plids = new ArrayList<>();

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					SQLTransformer.transform(
						"select layoutPageTemplateStructureId, classPK from " +
							"LayoutPageTemplateStructure where classPK in " +
								"(select plid from Layout where type_ = ?)"));
			PreparedStatement deletePreparedStatement1 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"delete from LayoutPageTemplateStructure where classPK = " +
						"?");
			PreparedStatement deletePreparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from LayoutPageTemplateStructureRel where " +
						"layoutPageTemplateStructureId = ?")) {

			selectPreparedStatement.setString(1, LayoutConstants.TYPE_PORTLET);

			ResultSet resultSet = selectPreparedStatement.executeQuery();

			while (resultSet.next()) {
				long classPK = resultSet.getLong("classPK");

				plids.add(classPK);

				deletePreparedStatement1.setLong(1, classPK);

				deletePreparedStatement1.addBatch();

				deletePreparedStatement2.setLong(
					1, resultSet.getLong("layoutPageTemplateStructureId"));

				deletePreparedStatement2.addBatch();
			}

			deletePreparedStatement1.executeBatch();
			deletePreparedStatement2.executeBatch();
		}

		DynamicQuery draftWidgetLayoutsWithStructureQuery =
			_layoutLocalService.dynamicQuery();

		draftWidgetLayoutsWithStructureQuery.add(
			RestrictionsFactoryUtil.and(
				RestrictionsFactoryUtil.in("plid", plids),
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
	}

	private final LayoutLocalService _layoutLocalService;

}