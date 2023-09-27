/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.control.menu.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuCategory;

import org.osgi.framework.ServiceReference;

/**
 * @author     Julio Camarero
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class ProductNavigationControlMenuCategoryServiceReferenceMapper
	implements ServiceReferenceMapper
		<String, ProductNavigationControlMenuCategory> {

	@Override
	public void map(
		ServiceReference<ProductNavigationControlMenuCategory> serviceReference,
		Emitter<String> emitter) {

		String productNavigationControlMenuCategoryKey =
			(String)serviceReference.getProperty(
				"product.navigation.control.menu.category.key");

		if (Validator.isNull(productNavigationControlMenuCategoryKey)) {
			_log.error(
				"Unable to register product navigation control menu category " +
					"because of missing service property " +
						"\"product.navigation.control.menu.category.key\"");
		}
		else {
			emitter.emit(productNavigationControlMenuCategoryKey);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductNavigationControlMenuCategoryServiceReferenceMapper.class);

}