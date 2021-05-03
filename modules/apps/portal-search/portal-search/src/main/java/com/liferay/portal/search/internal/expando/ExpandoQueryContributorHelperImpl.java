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

package com.liferay.portal.search.internal.expando;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.expando.kernel.util.ExpandoBridgeIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = ExpandoQueryContributorHelper.class)
public class ExpandoQueryContributorHelperImpl
	implements ExpandoQueryContributorHelper {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		Collection<String> classNames, SearchContext searchContext) {

		if (Validator.isBlank(keywords)) {
			return;
		}

		for (String className : classNames) {
			contribute(className, booleanQuery, keywords, searchContext);
		}
	}

	@Reference(unbind = "-")
	public void setExpandoBridgeFactory(
		ExpandoBridgeFactory expandoBridgeFactory) {

		_expandoBridgeFactory = expandoBridgeFactory;
	}

	@Reference(unbind = "-")
	public void setExpandoBridgeIndexer(
		ExpandoBridgeIndexer expandoBridgeIndexer) {

		_expandoBridgeIndexer = expandoBridgeIndexer;
	}

	@Reference(unbind = "-")
	public void setExpandoColumnLocalService(
		ExpandoColumnLocalService expandoColumnLocalService) {

		_expandoColumnLocalService = expandoColumnLocalService;
	}

	protected void contribute(
		String className, BooleanQuery booleanQuery, String keywords,
		SearchContext searchContext) {

		ExpandoBridge expandoBridge = _expandoBridgeFactory.getExpandoBridge(
			searchContext.getCompanyId(), className);

		Set<String> attributeNames = SetUtil.fromEnumeration(
			expandoBridge.getAttributeNames());

		for (String attributeName : attributeNames) {
			contribute(
				attributeName, expandoBridge, booleanQuery, keywords,
				searchContext);
		}
	}

	protected void contribute(
		String attributeName, ExpandoBridge expandoBridge,
		BooleanQuery booleanQuery, String keywords,
		SearchContext searchContext) {

		UnicodeProperties unicodeProperties =
			expandoBridge.getAttributeProperties(attributeName);

		int indexType = GetterUtil.getInteger(
			unicodeProperties.getProperty(ExpandoColumnConstants.INDEX_TYPE));

		if (indexType == ExpandoColumnConstants.INDEX_TYPE_NONE) {
			return;
		}

		String fieldName = getExpandoFieldName(
			attributeName, expandoBridge, searchContext.getLocale());

		boolean like = false;

		if (indexType == ExpandoColumnConstants.INDEX_TYPE_TEXT) {
			like = true;
		}

		if (searchContext.isAndSearch()) {
			booleanQuery.addRequiredTerm(fieldName, keywords, like);
		}
		else {
			_addTerm(booleanQuery, fieldName, keywords, like);
		}
	}

	protected String getExpandoFieldName(
		String attributeName, ExpandoBridge expandoBridge, Locale locale) {

		ExpandoColumn expandoColumn =
			_expandoColumnLocalService.getDefaultTableColumn(
				expandoBridge.getCompanyId(), expandoBridge.getClassName(),
				attributeName);

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		int indexType = GetterUtil.getInteger(
			unicodeProperties.getProperty(ExpandoColumnConstants.INDEX_TYPE));

		String fieldName = _expandoBridgeIndexer.encodeFieldName(
			attributeName, indexType);

		if (expandoColumn.getType() ==
				ExpandoColumnConstants.STRING_LOCALIZED) {

			fieldName = getLocalizedName(fieldName, locale);
		}

		return fieldName;
	}

	protected Localization getLocalization() {

		// See LPS-72507

		if (localization != null) {
			return localization;
		}

		return LocalizationUtil.getLocalization();
	}

	protected String getLocalizedName(String name, Locale locale) {
		if (locale == null) {
			return name;
		}

		Localization localization = getLocalization();

		return localization.getLocalizedName(
			name, LocaleUtil.toLanguageId(locale));
	}

	protected Localization localization;

	private Query _addTerm(
		BooleanQuery booleanQuery, String fieldName, String keywords,
		boolean like) {

		try {
			return booleanQuery.addTerm(fieldName, keywords, like);
		}
		catch (ParseException parseException) {
			throw new RuntimeException(parseException);
		}
	}

	private ExpandoBridgeFactory _expandoBridgeFactory;
	private ExpandoBridgeIndexer _expandoBridgeIndexer;
	private ExpandoColumnLocalService _expandoColumnLocalService;

}