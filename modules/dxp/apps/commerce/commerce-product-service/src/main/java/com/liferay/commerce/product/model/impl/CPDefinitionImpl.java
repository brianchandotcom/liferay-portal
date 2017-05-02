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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CPDefinitionImpl extends CPDefinitionBaseImpl {

	public CPDefinitionImpl() {
	}

	@Override
	public String[] getAvailableLanguageIds() {
		Set<String> availableLanguageIds = new TreeSet<>();

		availableLanguageIds.addAll(
			CPDefinitionLocalServiceUtil.getCPDefinitionLocalizationLanguageIds(
				getCPDefinitionId()));

		return availableLanguageIds.toArray(
			new String[availableLanguageIds.size()]);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels() {
		return CPDefinitionOptionRelLocalServiceUtil.getCPDefinitionOptionRels(
			getCPDefinitionId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		if (_descriptionMap != null) {
			return _descriptionMap;
		}

		_descriptionMap =
			CPDefinitionLocalServiceUtil.getCPDefinitionDescriptionMap(
				getCPDefinitionId());

		return _descriptionMap;
	}

	@Override
	public Map<Locale, String> getTitleMap() {
		if (_titleMap != null) {
			return _titleMap;
		}

		_titleMap = CPDefinitionLocalServiceUtil.getCPDefinitionTitleMap(
			getCPDefinitionId());

		return _titleMap;
	}

	@Override
	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		_descriptionMap = descriptionMap;
	}

	@Override
	public void setTitleMap(Map<Locale, String> titleMap) {
		_titleMap = titleMap;
	}

	private Map<Locale, String> _descriptionMap;
	private Map<Locale, String> _titleMap;

}