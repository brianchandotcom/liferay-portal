/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public class CPDefinitionSpecificationOptionValueImpl
	extends CPDefinitionSpecificationOptionValueBaseImpl {

	public CPDefinitionSpecificationOptionValueImpl() {
	}

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