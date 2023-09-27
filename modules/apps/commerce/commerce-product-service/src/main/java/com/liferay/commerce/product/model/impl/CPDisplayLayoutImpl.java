/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPDisplayLayoutImpl extends CPDisplayLayoutBaseImpl {

	@Override
	public AssetCategory fetchAssetCategory() {
		String className = getClassName();

		if (className.equals(AssetCategory.class.getName())) {
			return AssetCategoryLocalServiceUtil.fetchAssetCategory(
				getClassPK());
		}

		return null;
	}

	@Override
	public CPDefinition fetchCPDefinition() {
		String className = getClassName();

		if (className.equals(CPDefinition.class.getName())) {
			return CPDefinitionLocalServiceUtil.fetchCPDefinition(getClassPK());
		}

		return null;
	}

	@Override
	public Layout fetchLayout() {
		if (Validator.isNull(getLayoutUuid())) {
			return null;
		}

		Layout layout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			getLayoutUuid(), getGroupId(), false);

		if (layout == null) {
			layout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				getLayoutUuid(), getGroupId(), true);
		}

		return layout;
	}

}