/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPOptionCategoryLocalServiceUtil;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
public class CPDefinitionSpecificationOptionValueImpl
	extends CPDefinitionSpecificationOptionValueBaseImpl {

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		return CPDefinitionLocalServiceUtil.getCPDefinition(
			getCPDefinitionId());
	}

	@Override
	public CPOptionCategory getCPOptionCategory() throws PortalException {
		long cpOptionCategoryId = getCPOptionCategoryId();

		if (cpOptionCategoryId !=
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID) {

			return CPOptionCategoryLocalServiceUtil.getCPOptionCategory(
				cpOptionCategoryId);
		}

		return null;
	}

	@Override
	public CPSpecificationOption getCPSpecificationOption()
		throws PortalException {

		return CPSpecificationOptionLocalServiceUtil.getCPSpecificationOption(
			getCPSpecificationOptionId());
	}

}