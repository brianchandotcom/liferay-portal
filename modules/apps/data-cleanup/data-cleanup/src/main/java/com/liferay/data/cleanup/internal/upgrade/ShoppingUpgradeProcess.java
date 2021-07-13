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

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.data.cleanup.internal.upgrade.util.LayoutTypeSettingsUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.service.ImageLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 */
public class ShoppingUpgradeProcess extends BaseUpgradeProcess {

	public ShoppingUpgradeProcess(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.shopping.model.ShoppingCart'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.shopping.model.ShoppingCategory'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.shopping.model.ShoppingCoupon'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.shopping.model.ShoppingItem'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.shopping.model.ShoppingItemField'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.shopping.model.ShoppingItemPrice'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.shopping.model.ShoppingOrder'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.shopping.model.ShoppingOrderItem'");

		_deleteImages("smallImage");
		_deleteImages("mediumImage");
		_deleteImages("largeImage");

		LayoutTypeSettingsUtil.removePortletId(
			connection, "com_liferay_shopping_web_portlet_ShoppingPortlet");

		runSQL(
			"delete from Portlet where portletId = " +
				"'com_liferay_shopping_web_portlet_ShoppingAdminPortlet'");
		runSQL(
			"delete from Portlet where portletId = " +
				"'com_liferay_shopping_web_portlet_ShoppingPortlet'");

		runSQL(
			"delete from PortletPreferences where portletId = " +
				"'com_liferay_shopping_web_portlet_ShoppingAdminPortlet'");
		runSQL(
			"delete from PortletPreferences where portletId = " +
				"'com_liferay_shopping_web_portlet_ShoppingPortlet'");

		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.shopping.service'");
		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.shopping.web'");

		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.shopping.model.ShoppingCart'");
		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.shopping.model.ShoppingCategory'");
		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.shopping.model.ShoppingCoupon'");
		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.shopping.model.ShoppingItem'");
		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.shopping.model.ShoppingItemField'");
		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.shopping.model.ShoppingItemPrice'");
		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.shopping.model.ShoppingOrder'");
		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.shopping.model.ShoppingOrderItem'");

		runSQL(
			"delete from ServiceComponent where buildNamespace = 'Shopping'");

		_dropTables();
	}

	private void _deleteImages(String type) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select ", type, "Id from ShoppingItem where ", type,
						" = [$TRUE$]")));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				_imageLocalService.deleteImage(resultSet.getLong(1));
			}
		}
	}

	private void _dropTables() throws Exception {
		String[] tableNames = {
			"ShoppingCart", "ShoppingCategory", "ShoppingCoupon",
			"ShoppingItem", "ShoppingItemField", "ShoppingItemPrice",
			"ShoppingOrder", "ShoppingOrderItem"
		};

		for (String tableName : tableNames) {
			if (hasTable(tableName)) {
				runSQL("drop table " + tableName);
			}
		}
	}

	private final ImageLocalService _imageLocalService;

}