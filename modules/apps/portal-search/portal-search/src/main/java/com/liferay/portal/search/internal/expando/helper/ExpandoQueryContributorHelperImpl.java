/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.expando.helper;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.expando.ExpandoBridgeIndexer;
import com.liferay.portal.search.internal.indexer.IndexerProvidedClausesUtil;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = ExpandoQueryContributorHelper.class)
public class ExpandoQueryContributorHelperImpl
	implements ExpandoQueryContributorHelper {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		Collection<String> classNames, SearchContext searchContext) {

		if (IndexerProvidedClausesUtil.shouldSuppress(searchContext) ||
			Validator.isBlank(keywords)) {

			return;
		}

		for (String className : classNames) {
			contribute(className, booleanQuery, keywords, searchContext);
		}
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

		String fieldName = _getExpandoFieldName(
			attributeName, expandoBridge, searchContext.getLocale());

		if (fieldName.endsWith("_geolocation")) {
			return;
		}

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

	private String _getExpandoFieldName(
		String attributeName, ExpandoBridge expandoBridge, Locale locale) {

		ExpandoColumn expandoColumn =
			_expandoColumnLocalService.getDefaultTableColumn(
				expandoBridge.getCompanyId(), expandoBridge.getClassName(),
				attributeName);

		String fieldName = _expandoBridgeIndexer.encodeFieldName(expandoColumn);

		String numericSuffix = _expandoBridgeIndexer.getNumericSuffix(
			expandoColumn.getType());

		if (!numericSuffix.equals(StringPool.BLANK)) {
			fieldName = fieldName.concat(".keyword");
		}
		else if (expandoColumn.getType() ==
					ExpandoColumnConstants.STRING_LOCALIZED) {

			fieldName = _getLocalizedName(fieldName, locale);
		}
		else if (expandoColumn.getType() ==
					ExpandoColumnConstants.GEOLOCATION) {

			fieldName = fieldName.concat("_geolocation");
		}

		return fieldName;
	}

	private String _getLocalizedName(String name, Locale locale) {
		if (locale == null) {
			return name;
		}

		return _localization.getLocalizedName(
			name, LocaleUtil.toLanguageId(locale));
	}

	@Reference
	private ExpandoBridgeFactory _expandoBridgeFactory;

	@Reference
	private ExpandoBridgeIndexer _expandoBridgeIndexer;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private Localization _localization;

}