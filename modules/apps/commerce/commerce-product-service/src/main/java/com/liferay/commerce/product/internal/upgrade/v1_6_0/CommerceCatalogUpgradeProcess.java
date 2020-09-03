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

package com.liferay.commerce.product.internal.upgrade.v1_6_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelConstants;
import com.liferay.commerce.product.model.impl.CommerceCatalogModelImpl;
import com.liferay.commerce.product.model.impl.CommerceChannelModelImpl;
import com.liferay.commerce.product.model.impl.CommerceChannelRelModelImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Alec Sloan
 */
public class CommerceCatalogUpgradeProcess extends UpgradeProcess {

	public CommerceCatalogUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		GroupLocalService groupLocalService) {

		_classNameLocalService = classNameLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable(CommerceChannelModelImpl.TABLE_NAME)) {
			runSQL(CommerceChannelModelImpl.TABLE_SQL_CREATE);
		}

		if (!hasTable(CommerceChannelRelModelImpl.TABLE_NAME)) {
			runSQL(CommerceChannelRelModelImpl.TABLE_SQL_CREATE);
		}

		if (!hasTable(CommerceCatalogModelImpl.TABLE_NAME)) {
			runSQL(CommerceCatalogModelImpl.TABLE_SQL_CREATE);
		}

		String insertCommerceCatalogSQL = StringBundler.concat(
			"insert into CommerceCatalog (commerceCatalogId, companyId, ",
			"userId, userName, createDate, modifiedDate, name, ",
			"catalogDefaultLanguageId) values (?, ?, ?, ?, ?, ?, ?, ?)");

		String insertCommerceChannelSQL = StringBundler.concat(
			"insert into CommerceChannel (commerceChannelId, companyId, ",
			"userId, userName, createDate, modifiedDate, name, siteGroupId, ",
			"type_) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

		String insertCommerceChannelRelSQL = StringBundler.concat(
			"insert into CommerceChannelRel (commerceChannelRelId, companyId, ",
			"userId, userName, createDate, modifiedDate, classNameId, ",
			"classPK, commerceChannelId) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, insertCommerceCatalogSQL);
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, insertCommerceChannelSQL);
			PreparedStatement ps3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, insertCommerceChannelRelSQL);
			Statement s1 = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = s1.executeQuery(
				"select distinct groupId, companyId, userId, userName, " +
					"defaultLanguageId from CPDefinition")) {

			long cpDefinitionClassNameId =
				_classNameLocalService.getClassNameId(
					CPDefinition.class.getName());

			while (rs.next()) {
				long commerceCatalogId = increment();
				long commerceChannelId = increment();
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Date now = new Date(System.currentTimeMillis());
				String defaultLanguageId = rs.getString("defaultLanguageId");

				Group siteGroup = _groupLocalService.getGroup(groupId);

				ps1.setLong(1, commerceCatalogId);
				ps1.setLong(2, companyId);
				ps1.setLong(3, userId);
				ps1.setString(4, userName);
				ps1.setDate(5, now);
				ps1.setDate(6, now);

				ps1.setString(7, siteGroup.getName(defaultLanguageId));

				ps1.setString(8, defaultLanguageId);

				ps1.addBatch();

				ps2.setLong(1, commerceChannelId);
				ps2.setLong(2, companyId);
				ps2.setLong(3, userId);
				ps2.setString(4, userName);
				ps2.setDate(5, now);
				ps2.setDate(6, now);
				ps2.setString(7, siteGroup.getName(defaultLanguageId));
				ps2.setLong(8, siteGroup.getGroupId());
				ps2.setString(9, CommerceChannelConstants.CHANNEL_TYPE_SITE);

				ps2.addBatch();

				Group catalogGroup = _groupLocalService.addGroup(
					userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
					CommerceCatalog.class.getName(), commerceCatalogId,
					GroupConstants.DEFAULT_LIVE_GROUP_ID,
					siteGroup.getNameMap(), null,
					GroupConstants.TYPE_SITE_PRIVATE, false,
					GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false,
					true, null);

				Group channelGroup = _groupLocalService.addGroup(
					userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
					CommerceChannel.class.getName(), commerceChannelId,
					GroupConstants.DEFAULT_LIVE_GROUP_ID,
					siteGroup.getNameMap(), null,
					GroupConstants.TYPE_SITE_PRIVATE, false,
					GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false,
					true, null);

				String updateTableGroupIdSQL =
					"update %s set groupId = %s where groupId = %s";

				runSQL(
					String.format(
						updateTableGroupIdSQL, "CPDefinition",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));

				Statement s2 = connection.createStatement(
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

				ResultSet cpDefinitionsResultSet = s2.executeQuery(
					"select distinct cpDefinitionId from CPDefinition where " +
						"groupId = " + catalogGroup.getGroupId());

				while (cpDefinitionsResultSet.next()) {
					long commerceChannelRelId = increment();

					ps3.setLong(1, commerceChannelRelId);

					ps3.setLong(2, companyId);
					ps3.setLong(3, userId);
					ps3.setString(4, userName);
					ps3.setDate(5, now);
					ps3.setDate(6, now);
					ps3.setLong(7, cpDefinitionClassNameId);
					ps3.setLong(
						8, cpDefinitionsResultSet.getLong("cpDefinitionId"));
					ps3.setLong(9, commerceChannelId);

					ps3.addBatch();
				}

				runSQL(
					String.format(
						updateTableGroupIdSQL, "AssetEntry",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));
				runSQL(
					String.format(
						updateTableGroupIdSQL, "AssetCategory",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));
				runSQL(
					String.format(
						updateTableGroupIdSQL, "CPAttachmentFileEntry",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));
				runSQL(
					String.format(
						updateTableGroupIdSQL, "CPDefinitionLink",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));
				runSQL(
					String.format(
						updateTableGroupIdSQL, "CPDefinitionOptionRel",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));
				runSQL(
					String.format(
						updateTableGroupIdSQL, "CPDefinitionOptionValueRel",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));
				runSQL(
					String.format(
						updateTableGroupIdSQL, "CPDSpecificationOptionValue",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));
				runSQL(
					String.format(
						updateTableGroupIdSQL, "CPDisplayLayout",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));
				runSQL(
					String.format(
						updateTableGroupIdSQL, "CPInstance",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));
				runSQL(
					String.format(
						updateTableGroupIdSQL, "CProduct",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));

				if (hasTable("CPFriendlyURLEntry")) {
					runSQL(
						String.format(
							updateTableGroupIdSQL, "CPFriendlyURLEntry",
							GroupConstants.DEFAULT_LIVE_GROUP_ID,
							siteGroup.getGroupId()));
				}

				runSQL(
					String.format(
						updateTableGroupIdSQL, "CommerceOrder",
						channelGroup.getGroupId(), siteGroup.getGroupId()));

				runSQL(
					String.format(
						updateTableGroupIdSQL, "CommerceShipment",
						channelGroup.getGroupId(), siteGroup.getGroupId()));

				runSQL(
					String.format(
						updateTableGroupIdSQL, "CommercePriceList",
						catalogGroup.getGroupId(), siteGroup.getGroupId()));
			}

			ps1.executeBatch();
			ps2.executeBatch();
			ps3.executeBatch();
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final GroupLocalService _groupLocalService;

}