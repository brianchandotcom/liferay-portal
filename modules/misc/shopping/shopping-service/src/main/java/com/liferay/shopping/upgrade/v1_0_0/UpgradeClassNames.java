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

package com.liferay.shopping.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.classname.ClassNameDependency;
import com.liferay.portal.upgrade.util.classname.ClassNameDependencyUpgrader;
import com.liferay.portal.upgrade.util.classname.dependency.ResourcePermissionClassNameDependency;
import com.liferay.shopping.model.ShoppingCart;
import com.liferay.shopping.model.ShoppingCartItem;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.ShoppingCoupon;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.model.ShoppingItemField;
import com.liferay.shopping.model.ShoppingItemPrice;
import com.liferay.shopping.model.ShoppingOrder;
import com.liferay.shopping.model.ShoppingOrderItem;

import java.util.Collections;
import java.util.List;

/**
 * @author Philip Jones
 */
public class UpgradeClassNames extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		List<ClassNameDependency> classNameDependencies =
			Collections.singletonList(
				(ClassNameDependency)
					(new ResourcePermissionClassNameDependency()));

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.shopping", "com.liferay.shopping",
				classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.shopping.model.ShoppingCart",
			ShoppingCart.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.shopping.model.ShoppingCartItem",
			ShoppingCartItem.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.shopping.model.ShoppingCategory",
			ShoppingCategory.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.shopping.model.ShoppingCoupon",
			ShoppingCoupon.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.shopping.model.ShoppingItem",
			ShoppingItem.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.shopping.model.ShoppingItemField",
			ShoppingItemField.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.shopping.model.ShoppingItemPrice",
			ShoppingItemPrice.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.shopping.model.ShoppingOrder",
			ShoppingOrder.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.shopping.model.ShoppingOrderItem",
			ShoppingOrderItem.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

}