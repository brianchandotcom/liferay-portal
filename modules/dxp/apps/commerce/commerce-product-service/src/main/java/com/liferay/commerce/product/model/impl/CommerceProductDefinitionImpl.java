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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CommerceProductDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
@ProviderType
public class CommerceProductDefinitionImpl
	extends CommerceProductDefinitionBaseImpl {

	public CommerceProductDefinitionImpl() {
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		if (_descriptionMap != null) {
			return _descriptionMap;
		}

		_descriptionMap =
			CommerceProductDefinitionLocalServiceUtil.
				getCommerceProductDefinitionDescriptionMap(
					getCommerceProductDefinitionId());

		return _descriptionMap;
	}

	@Override
	public String getDescriptionMapAsXML() {
		return LocalizationUtil.updateLocalization(
			getDescriptionMap(), StringPool.BLANK, "Description",
			getDefaultLanguageId());
	}

	@Override
	public Map<Locale, String> getTitleMap() {
		if (_titleMap != null) {
			return _titleMap;
		}

		_titleMap =
			CommerceProductDefinitionLocalServiceUtil.
				getCommerceProductDefinitionTitleMap(
					getCommerceProductDefinitionId());

		return _titleMap;
	}

	@Override
	public String getTitleMapAsXML() {
		return LocalizationUtil.updateLocalization(
			getTitleMap(), StringPool.BLANK, "Title", getDefaultLanguageId());
	}

	private Map<Locale, String> _descriptionMap;
	private Map<Locale, String> _titleMap;

}