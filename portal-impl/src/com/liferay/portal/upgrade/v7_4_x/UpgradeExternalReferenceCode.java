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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Amos Fong
 */
public class UpgradeExternalReferenceCode extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeExternalReferenceCodes("AccountEntry", "accountEntryId");
		upgradeExternalReferenceCodes("AccountGroup", "accountGroupId");
		upgradeExternalReferenceCodes("Address", "addressId");
		upgradeExternalReferenceCodes("AssetCategory", "categoryId");
		upgradeExternalReferenceCodes("AssetVocabulary", "vocabularyId");
		upgradeExternalReferenceCodes("BlogsEntry", "entryId");
		upgradeExternalReferenceCodes("CIWarehouse", "CIWarehouseId");
		upgradeExternalReferenceCodes("CIWarehouseItem", "CIWarehouseItemId");
		upgradeExternalReferenceCodes("CommerceCatalog", "commerceCatalogId");
		upgradeExternalReferenceCodes("CommerceChannel", "commerceChannelId");
		upgradeExternalReferenceCodes("CommerceDiscount", "commerceDiscountId");
		upgradeExternalReferenceCodes("CommerceOrder", "commerceOrderId");
		upgradeExternalReferenceCodes(
			"CommerceOrderItem", "commerceOrderItemId");
		upgradeExternalReferenceCodes(
			"CommerceOrderNote", "commerceOrderNoteId");
		upgradeExternalReferenceCodes(
			"CommerceOrderType", "commerceOrderTypeId");
		upgradeExternalReferenceCodes(
			"CommerceOrderTypeRel", "commerceOrderTypeRelId");
		upgradeExternalReferenceCodes(
			"CommercePriceEntry", "commercePriceEntryId");
		upgradeExternalReferenceCodes(
			"CommercePriceList", "commercePriceListId");
		upgradeExternalReferenceCodes(
			"CommercePriceModifier", "commercePriceModifierId");
		upgradeExternalReferenceCodes(
			"CommercePricingClass", "commercePricingClassId");
		upgradeExternalReferenceCodes(
			"CommerceTermEntry", "commerceTermEntryId");
		upgradeExternalReferenceCodes(
			"CommerceTierPriceEntry", "commerceTierPriceEntryId");
		upgradeExternalReferenceCodes("COREntry", "COREntryId");
		upgradeExternalReferenceCodes(
			"CPAttachmentFileEntry", "CPAttachmentFileEntryId");
		upgradeExternalReferenceCodes("CPInstance", "CPInstanceId");
		upgradeExternalReferenceCodes("CPOption", "CPOptionId");
		upgradeExternalReferenceCodes("CPOptionValue", "CPOptionValueId");
		upgradeExternalReferenceCodes("CProduct", "CProductId");
		upgradeExternalReferenceCodes("CPTaxCategory", "CPTaxCategoryId");
		upgradeExternalReferenceCodes("DLFileEntry", "fileEntryId");
		upgradeExternalReferenceCodes("KBArticle", "kbArticleId");
		upgradeExternalReferenceCodes("KBFolder", "kbFolderId");
		upgradeExternalReferenceCodes("MBMessage", "messageId");
		upgradeExternalReferenceCodes("ObjectEntry", "objectEntryId");
		upgradeExternalReferenceCodes("Organization_", "organizationId");
		upgradeExternalReferenceCodes("RemoteAppEntry", "remoteAppEntryId");
		upgradeExternalReferenceCodes("User_", "userId");
		upgradeExternalReferenceCodes("UserGroup", "userGroupId");
		upgradeExternalReferenceCodes("WikiNode", "nodeId");
		upgradeExternalReferenceCodes("WikiPage", "pageId");
	}

	protected void upgradeExternalReferenceCodes(
			String tableName, String primKeyColumnName)
		throws Exception {

		if (!hasTable(tableName)) {
			return;
		}

		if (hasTable(tableName) &&
			!hasColumn(tableName, "externalReferenceCode")) {

			alterTableAddColumn(
				tableName, "externalReferenceCode", "VARCHAR(75)");
		}

		boolean hasUuid = hasColumn(tableName, "uuid_");

		StringBundler selectSB = new StringBundler(7);

		selectSB.append("select ");
		selectSB.append(primKeyColumnName);

		if (hasUuid) {
			selectSB.append(", uuid_");
		}

		selectSB.append(" from ");
		selectSB.append(tableName);
		selectSB.append(" where externalReferenceCode is null or ");
		selectSB.append("externalReferenceCode = ''");

		StringBundler updateSB = new StringBundler(5);

		updateSB.append("update ");
		updateSB.append(tableName);
		updateSB.append(" set externalReferenceCode = ? where ");
		updateSB.append(primKeyColumnName);
		updateSB.append(" = ?");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				selectSB.toString());
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(updateSB.toString()))) {

			while (resultSet.next()) {
				long primKey = resultSet.getLong(1);

				if (hasUuid) {
					String uuid = resultSet.getString(2);

					preparedStatement2.setString(1, uuid);
				}
				else {
					preparedStatement2.setString(1, String.valueOf(primKey));
				}

				preparedStatement2.setLong(2, primKey);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}