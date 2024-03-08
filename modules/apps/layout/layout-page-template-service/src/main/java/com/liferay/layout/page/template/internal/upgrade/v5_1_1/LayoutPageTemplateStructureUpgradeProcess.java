/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v5_1_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
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

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select LayoutPageTemplateStructure.",
					"layoutPageTemplateStructureId, ",
					"LayoutPageTemplateStructure.ctCollectionId, ",
					"LayoutPageTemplateStructure.classPK from ",
					"LayoutPageTemplateStructure inner join Layout on ",
					"LayoutPageTemplateStructure.classPK = Layout.plid and ",
					"LayoutPageTemplateStructure.ctCollectionId = ",
					"Layout.ctCollectionId and Layout.type_ = ?"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"delete from LayoutPageTemplateStructure where " +
						"layoutPageTemplateStructureId = ? and " +
							"ctCollectionId = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from LayoutPageTemplateStructureRel where " +
						"layoutPageTemplateStructureId = ? and " +
							"ctCollectionId = ?")) {

			preparedStatement1.setString(1, LayoutConstants.TYPE_PORTLET);

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				long classPK = resultSet.getLong(
					"LayoutPageTemplateStructure.classPK");
				long ctCollectionId = resultSet.getLong(
					"LayoutPageTemplateStructure.ctCollectionId");
				long layoutPageTemplateStructureId = resultSet.getLong(
					"LayoutPageTemplateStructure." +
						"layoutPageTemplateStructureId");

				plids.add(classPK);

				preparedStatement2.setLong(1, layoutPageTemplateStructureId);
				preparedStatement2.setLong(2, ctCollectionId);

				preparedStatement2.addBatch();

				preparedStatement3.setLong(1, layoutPageTemplateStructureId);
				preparedStatement3.setLong(2, ctCollectionId);

				preparedStatement3.addBatch();
			}

			preparedStatement2.executeBatch();

			preparedStatement3.executeBatch();
		}

		ServiceContext serviceContext = new ServiceContext();

		for (long plid : plids) {
			Layout layout = _layoutLocalService.fetchLayout(plid);

			if (layout.getStatus() != WorkflowConstants.STATUS_DRAFT) {
				continue;
			}

			_layoutLocalService.updateStatus(
				layout.getUserId(), plid, WorkflowConstants.STATUS_APPROVED,
				serviceContext);
		}
	}

	private final LayoutLocalService _layoutLocalService;

}