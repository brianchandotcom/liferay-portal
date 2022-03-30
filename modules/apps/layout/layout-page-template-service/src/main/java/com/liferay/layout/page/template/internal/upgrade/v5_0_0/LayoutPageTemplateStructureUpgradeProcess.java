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

package com.liferay.layout.page.template.internal.upgrade.v5_0_0;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateStructureUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select layoutPageTemplateStructureId, classPK from " +
					"LayoutPageTemplateStructure where classNameId = ?");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update LayoutPageTemplateStructure set classNameId = ?, " +
						"classPK = ? where layoutPageTemplateStructureId = " +
							"?")) {

			preparedStatement1.setLong(
				1, PortalUtil.getClassNameId(LayoutPageTemplateEntry.class));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				long classNameId = PortalUtil.getClassNameId(Layout.class);

				while (resultSet.next()) {
					long layoutPageTemplateStructureId = resultSet.getLong(
						"layoutPageTemplateStructureId");
					long classPK = resultSet.getLong("classPK");

					preparedStatement2.setLong(1, classNameId);
					preparedStatement2.setLong(
						2, _getPlidFromLayoutPageTemplateEntry(classPK));
					preparedStatement2.setLong(
						3, layoutPageTemplateStructureId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private long _getPlidFromLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select plid from LayoutPageTemplateEntry where " +
					"layoutPageTemplateEntryId = ?")) {

			preparedStatement.setLong(1, layoutPageTemplateEntryId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("plid");
				}
			}
		}

		return 0;
	}

}