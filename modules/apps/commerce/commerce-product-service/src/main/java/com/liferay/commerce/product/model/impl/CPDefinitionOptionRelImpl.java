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

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPOptionLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 * @author Igor Beslic
 */
public class CPDefinitionOptionRelImpl extends CPDefinitionOptionRelBaseImpl {

	public CPDefinitionOptionRelImpl() {
	}

	@Override
	public CPDefinitionOptionValueRel
		fetchPreselectedCPDefinitionOptionValueRel() {

		return CPDefinitionOptionValueRelLocalServiceUtil.
			fetchPreselectedCPDefinitionOptionValueRel(
				getCPDefinitionOptionRelId());
	}

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		return CPDefinitionLocalServiceUtil.getCPDefinition(
			getCPDefinitionId());
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels() {
		return CPDefinitionOptionValueRelLocalServiceUtil.
			getCPDefinitionOptionValueRels(
				getCPDefinitionOptionRelId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount() {
		return CPDefinitionOptionValueRelLocalServiceUtil.
			getCPDefinitionOptionValueRelsCount(getCPDefinitionOptionRelId());
	}

	@Override
	public CPOption getCPOption() throws PortalException {
		return CPOptionLocalServiceUtil.getCPOption(getCPOptionId());
	}

	@Override
	public boolean isPriceContributor() {
		if (Validator.isNotNull(getPriceType())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isPriceTypeDynamic() {
		if (Objects.equals(
				getPriceType(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPriceTypeStatic() {
		if (Objects.equals(
				getPriceType(), CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC)) {

			return true;
		}

		return false;
	}

}