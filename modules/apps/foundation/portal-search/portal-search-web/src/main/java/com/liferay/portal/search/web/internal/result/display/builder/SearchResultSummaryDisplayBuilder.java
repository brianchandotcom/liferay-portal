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

package com.liferay.portal.search.web.internal.result.display.builder;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.display.context.PortletURLFactory;
import com.liferay.portal.search.web.internal.display.context.SearchResultPreferences;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultFieldDisplayContext;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author André de Oliveira
 */
public class SearchResultSummaryDisplayBuilder {

	public SearchResultSummaryDisplayContext build() throws PortletException {
		SearchResultSummary srs = _searchResultSummary;

		SearchResultSummaryDisplayContext srsdc =
			new SearchResultSummaryDisplayContext();

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			srs.className, srs.classPK);

		if (assetEntry != null) {
			srsdc.assetEntryUserId = getAssetEntryUserId(assetEntry);
			srsdc.hasUserPortrait = true;
		}

		srsdc.viewURL = checkViewURL(
			srs.viewURL, srs.currentURL, srs.searchResultPreferences);

		srsdc.highlightedTitle = srs.summary.getHighlightedTitle();

		if (hasAssetRendererURLDownload(srs.assetRenderer)) {
			srsdc.assetRendererURLDownload = srs.assetRenderer.getURLDownload(
				_themeDisplay);
			srsdc.hasAssetRendererURLDownload = true;
			srsdc.title = srs.summary.getTitle();
		}

		srsdc.modelResource = _resourceActions.getModelResource(
			_themeDisplay.getLocale(), srs.className);

		if (srs.locale != srs.summary.getLocale()) {
			Locale summaryLocale = srs.summary.getLocale();

			srsdc.hasLocaleReminder = true;
			srsdc.localeLanguageId = LocaleUtil.toLanguageId(summaryLocale);
			srsdc.localeReminder = _language.format(
				_request,
				"this-result-comes-from-the-x-version-of-this-content",
				summaryLocale.getDisplayLanguage(srs.locale), false);
		}

		if (Validator.isNotNull(srs.summary.getContent())) {
			srsdc.content = srs.summary.getHighlightedContent();
			srsdc.hasContent = true;
		}

		if (assetEntry != null) {
			if (hasAssetCategoriesOrTags(assetEntry)) {
				srsdc.className = srs.className;
				srsdc.classPK = srs.classPK;
				srsdc.hasAssetCategoriesOrTags = true;
				srsdc.fieldAssetCategoryIds = Field.ASSET_CATEGORY_IDS;
				srsdc.fieldAssetTagNames = Field.ASSET_TAG_NAMES;
				srsdc.portletURL = _portletURLFactory.getPortletURL();
			}
		}

		if (srs.searchResultPreferences.isDisplayResultsInDocumentForm()) {
			srsdc.documentFormFieldDisplayContexts = buildFields(srs.document);
			srsdc.hasDocumentForm = true;
		}

		return srsdc;
	}

	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	public void setLanguage(Language language) {
		_language = language;
	}

	public void setPortletURLFactory(PortletURLFactory portletURLFactory) {
		_portletURLFactory = portletURLFactory;
	}

	public void setRequest(HttpServletRequest request) {
		_request = request;
	}

	public void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	public void setSearchResultSummary(
		SearchResultSummary searchResultSummary) {

		_searchResultSummary = searchResultSummary;
	}

	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	protected SearchResultFieldDisplayContext buildField(Field field) {
		SearchResultFieldDisplayContext srfdc =
			new SearchResultFieldDisplayContext();

		srfdc.boost = field.getBoost();
		srfdc.isArray = isArray(field);
		srfdc.isNumeric = field.isNumeric();
		srfdc.isTokenized = field.isTokenized();
		srfdc.name = field.getName();
		srfdc.valuesToString = getValuesToString(field);

		return srfdc;
	}

	protected List<SearchResultFieldDisplayContext> buildFields(
		Document document) {

		Map<String, Field> map = document.getFields();

		List<Map.Entry<String, Field>> entries = new LinkedList(map.entrySet());

		Collections.sort(
			entries,
			new Comparator<Map.Entry<String, Field>>() {

				@Override
				public int compare(
					Map.Entry<String, Field> entry1,
					Map.Entry<String, Field> entry2) {

					String key = entry1.getKey();

					return key.compareTo(entry2.getKey());
				}

			});

		List<SearchResultFieldDisplayContext> searchResultFieldDisplayContexts =
			new ArrayList<>(entries.size());

		for (Map.Entry<String, Field> entry : entries) {
			Field field = entry.getValue();

			String fieldName = field.getName();

			if (fieldName.equals(Field.UID)) {
				continue;
			}

			searchResultFieldDisplayContexts.add(buildField(field));
		}

		return searchResultFieldDisplayContexts;
	}

	protected String checkViewURL(
		String viewURL, String currentURL,
		SearchResultPreferences searchResultPreferences) {

		if (Validator.isNotNull(viewURL) &&
			viewURL.startsWith(_themeDisplay.getURLPortal())) {

			viewURL = HttpUtil.setParameter(
				viewURL, "inheritRedirect",
				searchResultPreferences.isViewInContext());

			if (!searchResultPreferences.isViewInContext()) {
				viewURL = HttpUtil.setParameter(
					viewURL, "redirect", currentURL);
			}
		}

		return viewURL;
	}

	protected long getAssetEntryUserId(AssetEntry assetEntry) {
		if (Objects.equals(assetEntry.getClassName(), User.class.getName())) {
			return assetEntry.getClassPK();
		}

		return assetEntry.getUserId();
	}

	protected String getValuesToString(Field field) {
		String[] values = field.getValues();

		StringBundler sb = new StringBundler(4 * values.length);

		for (int i = 0; i < values.length; i++) {
			if (field.isNumeric()) {
				sb.append(HtmlUtil.escape(values[i]));
			}
			else {
				sb.append(StringPool.QUOTE);
				sb.append(HtmlUtil.escape(values[i]));
				sb.append(StringPool.QUOTE);
			}

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		if (values.length > 1) {
			sb.setStringAt(StringPool.OPEN_BRACKET, 0);

			sb.append(StringPool.CLOSE_BRACKET);
		}

		return sb.toString();
	}

	protected boolean hasAssetCategoriesOrTags(AssetEntry assetEntry) {
		if (ArrayUtil.isNotEmpty(assetEntry.getCategoryIds())) {
			return true;
		}

		if (ArrayUtil.isNotEmpty(assetEntry.getTagNames())) {
			return true;
		}

		return false;
	}

	protected boolean hasAssetRendererURLDownload(
		AssetRenderer<?> assetRenderer) {

		if (assetRenderer == null) {
			return false;
		}

		if (Validator.isNull(assetRenderer.getURLDownload(_themeDisplay))) {
			return false;
		}

		return true;
	}

	protected boolean isArray(Field field) {
		String[] values = field.getValues();

		if (values.length > 1) {
			return true;
		}

		return false;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private Language _language;
	private PortletURLFactory _portletURLFactory;
	private HttpServletRequest _request;
	private ResourceActions _resourceActions;
	private SearchResultSummary _searchResultSummary;
	private ThemeDisplay _themeDisplay;

}