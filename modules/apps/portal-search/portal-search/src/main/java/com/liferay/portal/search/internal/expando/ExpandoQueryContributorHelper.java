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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class ExpandoQueryContributorHelper {

	public ExpandoQueryContributorHelper(
		ExpandoBridgeFactory expandoBridgeFactory,
		ExpandoBridgeIndexer expandoBridgeIndexer,
		ExpandoColumnLocalService expandoColumnLocalService,
		Localization localization) {

		_expandoBridgeFactory = expandoBridgeFactory;
		_expandoBridgeIndexer = expandoBridgeIndexer;
		_expandoColumnLocalService = expandoColumnLocalService;
		_localization = localization;
	}

	public void contribute() {
		if (Validator.isBlank(_keywords)) {
			return;
		}

		_classNamesStream.forEach(this::contribute);
	}

	public void setAndSearch(boolean andSearch) {
		_andSearch = andSearch;
	}

	public void setBooleanQuery(BooleanQuery booleanQuery) {
		_booleanQuery = booleanQuery;
	}

	public void setClassNamesStream(Stream<String> classNamesStream) {
		_classNamesStream = classNamesStream;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	protected void contribute(String className) {
		ExpandoBridge expandoBridge = _expandoBridgeFactory.getExpandoBridge(
			_companyId, className);

		Set<String> attributeNames = SetUtil.fromEnumeration(
			expandoBridge.getAttributeNames());

		for (String attributeName : attributeNames) {
			contribute(attributeName, expandoBridge);
		}
	}

	protected void contribute(
		String attributeName, ExpandoBridge expandoBridge) {

		UnicodeProperties unicodeProperties =
			expandoBridge.getAttributeProperties(attributeName);

		int indexType = GetterUtil.getInteger(
			unicodeProperties.getProperty(ExpandoColumnConstants.INDEX_TYPE));

		if (indexType == ExpandoColumnConstants.INDEX_TYPE_NONE) {
			return;
		}

		String fieldName = getExpandoFieldName(attributeName, expandoBridge);

		if (fieldName.endsWith("_geolocation")) {
			return;
		}

		boolean like = false;

		if (indexType == ExpandoColumnConstants.INDEX_TYPE_TEXT) {
			like = true;
		}

		if (_andSearch) {
			_booleanQuery.addRequiredTerm(fieldName, _keywords, like);
		}
		else {
			_addTerm(_booleanQuery, fieldName, _keywords, like);
		}
	}

	protected String getExpandoFieldName(
		String attributeName, ExpandoBridge expandoBridge) {

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

			fieldName = getLocalizedName(fieldName);
		}

		if (expandoColumn.getType() == ExpandoColumnConstants.GEOLOCATION) {
			fieldName = fieldName.concat("_geolocation");
		}

		return fieldName;
	}

	protected String getLocalizedName(String name) {
		if (_locale == null) {
			return name;
		}

		return _localization.getLocalizedName(
			name, LocaleUtil.toLanguageId(_locale));
	}

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

	private boolean _andSearch;
	private BooleanQuery _booleanQuery;
	private Stream<String> _classNamesStream = Stream.empty();
	private long _companyId;
	private final ExpandoBridgeFactory _expandoBridgeFactory;
	private final ExpandoBridgeIndexer _expandoBridgeIndexer;
	private final ExpandoColumnLocalService _expandoColumnLocalService;
	private String _keywords;
	private Locale _locale;
	private final Localization _localization;

}