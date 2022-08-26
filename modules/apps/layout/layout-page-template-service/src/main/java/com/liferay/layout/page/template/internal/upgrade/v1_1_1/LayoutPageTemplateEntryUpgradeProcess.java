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

package com.liferay.layout.page.template.internal.upgrade.v1_1_1;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan McCann
 */
public class LayoutPageTemplateEntryUpgradeProcess extends UpgradeProcess {

	public LayoutPageTemplateEntryUpgradeProcess(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<PreparedStatement> preparedStatements1 = new ArrayList<>();
		List<PreparedStatement> preparedStatements2 = new ArrayList<>();

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select layoutPageTemplateEntryId, companyId, name, ",
						"layoutPrototypeId from LayoutPageTemplateEntry where ",
						"type_ = ",
						LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE,
						" and groupId in (select groupId from Group_ where ",
						"site = [$FALSE$])")));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select count(*) from LayoutPageTemplateEntry where groupId " +
					"= ? and name = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update LayoutPageTemplateEntry set groupId = ? , " +
						"layoutPageTemplateCollectionId = 0, name = ? where " +
							"layoutPageTemplateEntryId = ?");
			PreparedStatement preparedStatement4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from LayoutPageTemplateEntry where groupId <> ? " +
						"and layoutPageTemplateCollectionId <> 0 and type_ = " +
							"? and layoutPrototypeId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String name = resultSet.getString("name");

				Company company = _companyLocalService.getCompany(
					resultSet.getLong("companyId"));

				String newName = name;

				for (int i = 1;; i++) {
					preparedStatement2.setLong(1, company.getGroupId());
					preparedStatement2.setString(2, newName);

					ResultSet countResultSet =
						preparedStatement2.executeQuery();

					if (countResultSet.next() &&
						(countResultSet.getInt(1) > 0)) {

						newName = name + i;
					}
					else {
						break;
					}
				}

				preparedStatement3.setLong(1, company.getGroupId());
				preparedStatement3.setString(2, newName);
				preparedStatement3.setLong(
					3, resultSet.getLong("layoutPageTemplateEntryId"));

				preparedStatement3.addBatch();

				preparedStatements1.add(preparedStatement3);

				preparedStatement4.setLong(1, company.getGroupId());
				preparedStatement4.setInt(
					2, LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE);
				preparedStatement4.setLong(
					3, resultSet.getLong("layoutPrototypeId"));

				preparedStatement4.addBatch();

				preparedStatements2.add(preparedStatement4);
			}

			PreparedStatement preparedStatement5 = null;

			for (int i = 0; i < preparedStatements1.size(); i++) {
				preparedStatement5 = preparedStatements1.get(i);

				preparedStatement5.executeBatch();

				preparedStatement5 = preparedStatements2.get(i);

				preparedStatement5.executeBatch();
			}
		}
		finally {
			for (PreparedStatement preparedStatement : preparedStatements1) {
				DataAccess.cleanUp(preparedStatement);
			}

			for (PreparedStatement preparedStatement : preparedStatements2) {
				DataAccess.cleanUp(preparedStatement);
			}
		}
	}

	private final CompanyLocalService _companyLocalService;

}