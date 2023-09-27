/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 * @author Igor Beslic
 */
public class CPDefinitionOptionRelImpl extends CPDefinitionOptionRelBaseImpl {

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
	public UnicodeProperties getTypeSettingsUnicodeProperties() {
		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				getTypeSettings()
			).build();
		}

		return _typeSettingsUnicodeProperties;
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

	private UnicodeProperties _typeSettingsUnicodeProperties;

}