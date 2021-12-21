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
		_deleteFromShoppingItem("smallImage");
		_deleteFromShoppingItem("mediumImage");
		_deleteFromShoppingItem("largeImage");

		removePortletData(
			new String[] {"com.liferay.shopping.web"}, null,
			new String[] {
				"com_liferay_shopping_web_portlet_ShoppingPortlet",
				"com.liferay.portlet.shopping"
			});

		removeServiceData(
			"Shopping", new String[] {"com.liferay.shopping.service"},
			new String[] {
				"com.liferay.portlet.shopping.model.ShoppingCart",
				"com.liferay.portlet.shopping.model.ShoppingCategory",
				"com.liferay.portlet.shopping.model.ShoppingCoupon",
				"com.liferay.portlet.shopping.model.ShoppingItem",
				"com.liferay.portlet.shopping.model.ShoppingItemField",
				"com.liferay.portlet.shopping.model.ShoppingItemPrice",
				"com.liferay.portlet.shopping.model.ShoppingOrder",
				"com.liferay.portlet.shopping.model.ShoppingOrderItem",
				"com.liferay.shopping.model.ShoppingCart",
				"com.liferay.shopping.model.ShoppingCategory",
				"com.liferay.shopping.model.ShoppingCoupon",
				"com.liferay.shopping.model.ShoppingItem",
				"com.liferay.shopping.model.ShoppingItemField",
				"com.liferay.shopping.model.ShoppingItemPrice",
				"com.liferay.shopping.model.ShoppingOrder",
				"com.liferay.shopping.model.ShoppingOrderItem"
			},
			new String[] {
				"ShoppingCart", "ShoppingCategory", "ShoppingCoupon",
				"ShoppingItem", "ShoppingItemField", "ShoppingItemPrice",
				"ShoppingOrder", "ShoppingOrderItem"
			});
	}

	private void _deleteFromShoppingItem(String type) throws Exception {
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

	private final ImageLocalService _imageLocalService;

}