/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.asset.categories.web.internal.util;

import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPAssetCategoryWebPortletUtil.class)
public class CPAssetCategoryWebPortletUtil {

	public CPType getCPType(String name) {
		return _cpTypeRegistry.getCPType(name);
	}

	@Reference
	private CPTypeRegistry _cpTypeRegistry;

}