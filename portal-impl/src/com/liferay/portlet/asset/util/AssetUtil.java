/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.comparator.ModelResourceComparator;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetCategoryPropertyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagPropertyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.assetpublisher.util.AssetSearcher;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerImpl;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetUtil {

	public static final String CLASSNAME_SEPARATOR = "_CLASSNAME_";

	public static final char[] INVALID_CHARACTERS = new char[] {
		CharPool.AMPERSAND, CharPool.APOSTROPHE, CharPool.AT,
		CharPool.BACK_SLASH, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.COLON, CharPool.COMMA, CharPool.EQUAL, CharPool.GREATER_THAN,
		CharPool.FORWARD_SLASH, CharPool.LESS_THAN, CharPool.NEW_LINE,
		CharPool.OPEN_BRACKET, CharPool.OPEN_CURLY_BRACE, CharPool.PERCENT,
		CharPool.PIPE, CharPool.PLUS, CharPool.POUND, CharPool.QUESTION,
		CharPool.QUOTE, CharPool.RETURN, CharPool.SEMICOLON, CharPool.SLASH,
		CharPool.STAR, CharPool.TILDE
	};

	public static Set<String> addLayoutTags(
		HttpServletRequest request, List<AssetTag> tags) {

		Set<String> layoutTags = getLayoutTagNames(request);

		for (AssetTag tag : tags) {
			layoutTags.add(tag.getName());
		}

		return layoutTags;
	}

	public static void addPortletBreadcrumbEntries(
			long assetCategoryId, HttpServletRequest request,
			PortletURL portletURL)
		throws Exception {

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategory(
			assetCategoryId);

		List<AssetCategory> ancestorCategories = assetCategory.getAncestors();

		Collections.reverse(ancestorCategories);

		for (AssetCategory ancestorCategory : ancestorCategories) {
			portletURL.setParameter(
				"categoryId", String.valueOf(ancestorCategory.getCategoryId()));

			PortalUtil.addPortletBreadcrumbEntry(
				request, ancestorCategory.getTitleCurrentValue(),
				portletURL.toString());
		}

		portletURL.setParameter("categoryId", String.valueOf(assetCategoryId));

		PortalUtil.addPortletBreadcrumbEntry(
			request, assetCategory.getTitleCurrentValue(),
			portletURL.toString());
	}

	public static PortletURL getAddPortletURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classTypeId, long[] allAssetCategoryIds,
			String[] allAssetTagNames, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory == null) {
			return null;
		}

		liferayPortletRequest.setAttribute(
			WebKeys.ASSET_RENDERER_FACTORY_CLASS_TYPE_ID, classTypeId);

		PortletURL addPortletURL = assetRendererFactory.getURLAdd(
			liferayPortletRequest, liferayPortletResponse);

		if (addPortletURL == null) {
			return null;
		}

		if (redirect != null) {
			addPortletURL.setParameter("redirect", redirect);
		}

		String referringPortletResource = ParamUtil.getString(
			liferayPortletRequest, "portletResource");

		if (Validator.isNotNull(referringPortletResource)) {
			addPortletURL.setParameter(
				"referringPortletResource", referringPortletResource);
		}
		else {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			addPortletURL.setParameter(
				"referringPortletResource", portletDisplay.getId());

			if (allAssetCategoryIds != null) {
				Map<Long, String> assetVocabularyAssetCategoryIds =
					new HashMap<Long, String>();

				for (long assetCategoryId : allAssetCategoryIds) {
					AssetCategory assetCategory =
						AssetCategoryLocalServiceUtil.fetchAssetCategory(
							assetCategoryId);

					if (assetCategory == null) {
						continue;
					}

					long assetVocabularyId = assetCategory.getVocabularyId();

					if (assetVocabularyAssetCategoryIds.containsKey(
							assetVocabularyId)) {

						String assetCategoryIds =
							assetVocabularyAssetCategoryIds.get(
								assetVocabularyId);

						assetVocabularyAssetCategoryIds.put(
							assetVocabularyId,
							assetCategoryIds + StringPool.COMMA +
								assetCategoryId);
					}
					else {
						assetVocabularyAssetCategoryIds.put(
							assetVocabularyId, String.valueOf(assetCategoryId));
					}
				}

				for (Map.Entry<Long, String> entry :
						assetVocabularyAssetCategoryIds.entrySet()) {

					long assetVocabularyId = entry.getKey();
					String assetCategoryIds = entry.getValue();

					addPortletURL.setParameter(
						"assetCategoryIds_" + assetVocabularyId,
						assetCategoryIds);
				}
			}

			if (allAssetTagNames != null) {
				addPortletURL.setParameter(
					"assetTagNames", StringUtil.merge(allAssetTagNames));
			}
		}

		if (classTypeId > 0) {
			addPortletURL.setParameter(
				"classTypeId", String.valueOf(classTypeId));

			if (className.equals(DLFileEntry.class.getName())) {
				addPortletURL.setParameter(Constants.CMD, Constants.ADD);
				addPortletURL.setParameter(
					"folderId",
					String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));
				addPortletURL.setParameter(
					"fileEntryTypeId", String.valueOf(classTypeId));
			}

			if (className.equals(JournalArticle.class.getName())) {
				DDMStructure ddmStructure =
					DDMStructureLocalServiceUtil.getStructure(classTypeId);

				addPortletURL.setParameter(
					"structureId", ddmStructure.getStructureKey());
			}
		}

		addPortletURL.setPortletMode(PortletMode.VIEW);
		addPortletURL.setWindowState(LiferayWindowState.POP_UP);

		return addPortletURL;
	}

	public static Map<String, PortletURL> getAddPortletURLs(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long[] classNameIds,
			long[] classTypeIds, long[] allAssetCategoryIds,
			String[] allAssetTagNames)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Map<String, PortletURL> addPortletURLs =
			new TreeMap<String, PortletURL>(
				new ModelResourceComparator(themeDisplay.getLocale()));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL redirectURL = liferayPortletResponse.createLiferayPortletURL(
			themeDisplay.getPlid(), portletDisplay.getId(),
			PortletRequest.RENDER_PHASE, false);

		redirectURL.setParameter(
			"struts_action", "/asset_publisher/add_asset_redirect");
		redirectURL.setWindowState(LiferayWindowState.POP_UP);

		for (long classNameId : classNameIds) {
			String className = PortalUtil.getClassName(classNameId);

			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(),
				assetRendererFactory.getPortletId());

			if (!portlet.isActive()) {
				continue;
			}

			Map<Long, String> classTypes = assetRendererFactory.getClassTypes(
				new long[] {
					themeDisplay.getCompanyGroupId(),
					themeDisplay.getScopeGroupId()
				},
				themeDisplay.getLocale());

			if ((classTypeIds.length == 0) || classTypes.isEmpty()) {
				PortletURL addPortletURL = getAddPortletURL(
					liferayPortletRequest, liferayPortletResponse, className, 0,
					allAssetCategoryIds, allAssetTagNames,
					redirectURL.toString());

				if (addPortletURL != null) {
					addPortletURLs.put(className, addPortletURL);
				}
			}

			for (long classTypeId : classTypes.keySet()) {
				if (ArrayUtil.contains(classTypeIds, classTypeId) ||
					(classTypeIds.length == 0)) {

					PortletURL addPortletURL = getAddPortletURL(
						liferayPortletRequest, liferayPortletResponse,
						className, classTypeId, allAssetCategoryIds,
						allAssetTagNames, redirectURL.toString());

					if (addPortletURL != null) {
						String mesage = className + CLASSNAME_SEPARATOR +
							classTypes.get(classTypeId);

						addPortletURLs.put(mesage, addPortletURL);
					}
				}
			}
		}

		return addPortletURLs;
	}

	public static List<AssetEntry> getAssetEntries(Hits hits) {
		List<AssetEntry> assetEntries = new ArrayList<AssetEntry>();

		for (Document document : hits.getDocs()) {
			String className = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
					className, classPK);

				assetEntries.add(assetEntry);
			}
			catch (Exception e) {
			}
		}

		return assetEntries;
	}

	public static String getAssetKeywords(String className, long classPK)
		throws SystemException {

		List<AssetTag> tags = AssetTagLocalServiceUtil.getTags(
			className, classPK);
		List<AssetCategory> categories =
			AssetCategoryLocalServiceUtil.getCategories(className, classPK);

		StringBuffer sb = new StringBuffer();

		sb.append(ListUtil.toString(tags, AssetTag.NAME_ACCESSOR));

		if (!tags.isEmpty()) {
			sb.append(StringPool.COMMA);
		}

		sb.append(ListUtil.toString(categories, AssetCategory.NAME_ACCESSOR));

		return sb.toString();
	}

	public static Set<String> getLayoutTagNames(HttpServletRequest request) {
		Set<String> tagNames = (Set<String>)request.getAttribute(
			WebKeys.ASSET_LAYOUT_TAG_NAMES);

		if (tagNames == null) {
			tagNames = new HashSet<String>();

			request.setAttribute(WebKeys.ASSET_LAYOUT_TAG_NAMES, tagNames);
		}

		return tagNames;
	}

	public static boolean hasSubtype(
			String subtypeClassName, Map<String, PortletURL> addPortletURLs) {

		for (Map.Entry<String, PortletURL> entry : addPortletURLs.entrySet()) {
			String className = entry.getKey();

			if (className.startsWith(subtypeClassName) &&
				!className.equals(subtypeClassName)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isValidWord(String word) {
		if (Validator.isNull(word)) {
			return false;
		}
		else {
			char[] wordCharArray = word.toCharArray();

			for (char c : wordCharArray) {
				for (char invalidChar : INVALID_CHARACTERS) {
					if (c == invalidChar) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Word " + word + " is not valid because " + c +
									" is not allowed");
						}

						return false;
					}
				}
			}
		}

		return true;
	}

	public static Hits search(
			HttpServletRequest request, AssetEntryQuery assetEntryQuery,
			int start, int end)
		throws Exception {

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		return search(searchContext, assetEntryQuery, start, end);
	}

	public static Hits search(
			SearchContext searchContext, AssetEntryQuery assetEntryQuery,
			int start, int end)
		throws Exception {

		Indexer searcher = AssetSearcher.getInstance();

		AssetSearcher assetSearcher = (AssetSearcher)searcher;

		assetSearcher.setAssetEntryQuery(assetEntryQuery);

		Layout layout = assetEntryQuery.getLayout();

		if (layout != null) {
			searchContext.setAttribute(Field.LAYOUT_UUID, layout.getUuid());
		}

		String ddmStructureFieldName = (String)assetEntryQuery.getAttribute(
			"ddmStructureFieldName");
		String ddmStructureFieldValue = (String)assetEntryQuery.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			searchContext.setAttribute(
				"ddmStructureFieldName", ddmStructureFieldName);
			searchContext.setAttribute(
				"ddmStructureFieldValue",
				StringPool.QUOTE + ddmStructureFieldValue + StringPool.QUOTE);
		}

		searchContext.setClassTypeIds(assetEntryQuery.getClassTypeIds());
		searchContext.setEnd(end);
		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		if (Validator.isNotNull(assetEntryQuery.getKeywords())) {
			searchContext.setLike(true);
		}

		searchContext.setSorts(
			getSorts(assetEntryQuery, searchContext.getLocale()));
		searchContext.setStart(start);

		return assetSearcher.search(searchContext);
	}

	public static String substituteCategoryPropertyVariables(
			long groupId, long categoryId, String s)
		throws PortalException, SystemException {

		String result = s;

		AssetCategory category = null;

		if (categoryId > 0) {
			try {
				category = AssetCategoryLocalServiceUtil.getCategory(
					categoryId);
			}
			catch (NoSuchCategoryException nsce) {
			}
		}

		if (category != null) {
			List<AssetCategoryProperty> categoryProperties =
				AssetCategoryPropertyLocalServiceUtil.getCategoryProperties(
					categoryId);

			for (AssetCategoryProperty categoryProperty : categoryProperties) {
				result = StringUtil.replace(
					result, "[$" + categoryProperty.getKey() + "$]",
					categoryProperty.getValue());
			}
		}

		return StringUtil.stripBetween(result, "[$", "$]");
	}

	public static String substituteTagPropertyVariables(
			long groupId, String tagName, String s)
		throws PortalException, SystemException {

		String result = s;

		AssetTag tag = null;

		if (tagName != null) {
			try {
				tag = AssetTagLocalServiceUtil.getTag(groupId, tagName);
			}
			catch (NoSuchTagException nste) {
			}
		}

		if (tag != null) {
			List<AssetTagProperty> tagProperties =
				AssetTagPropertyLocalServiceUtil.getTagProperties(
					tag.getTagId());

			for (AssetTagProperty tagProperty : tagProperties) {
				result = StringUtil.replace(
					result, "[$" + tagProperty.getKey() + "$]",
					tagProperty.getValue());
			}
		}

		return StringUtil.stripBetween(result, "[$", "$]");
	}

	public static String toWord(String text) {
		if (Validator.isNull(text)) {
			return text;
		}
		else {
			char[] textCharArray = text.toCharArray();

			for (int i = 0; i < textCharArray.length; i++) {
				char c = textCharArray[i];

				for (char invalidChar : INVALID_CHARACTERS) {
					if (c == invalidChar) {
						textCharArray[i] = CharPool.SPACE;

						break;
					}
				}
			}

			return new String(textCharArray);
		}
	}

	protected static Sort getSort(
			String orderByType, String sortField, Locale locale)
		throws Exception {

		if (Validator.isNull(orderByType)) {
			orderByType = "asc";
		}

		int sortType = getSortType(sortField);

		if (sortField.startsWith(
				DDMIndexerImpl.DDM_FIELD_NAMESPACE +
					StringPool.FORWARD_SLASH)) {

			String[] sortFields = sortField.split(StringPool.FORWARD_SLASH);

			long ddmStructureId = GetterUtil.getLong(sortFields[1]);
			String fieldName = sortFields[2];

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getStructure(ddmStructureId);

			String fieldType = ddmStructure.getFieldType(fieldName);

			sortType = getSortType(fieldType);

			sortField = sortField.concat(StringPool.UNDERLINE).concat(
				LocaleUtil.toLanguageId(locale));
		}
		else if (sortField.equals("modifiedDate")) {
			sortField = Field.MODIFIED_DATE;
		}
		else if (sortField.equals("title")) {
			sortField = "localized_title_".concat(
				LocaleUtil.toLanguageId(locale));
		}

		return new Sort(
			sortField, sortType, !orderByType.equalsIgnoreCase("asc"));
	}

	protected static Sort[] getSorts(
			AssetEntryQuery assetEntryQuery, Locale locale)
		throws Exception {

		Sort sort1 = getSort(
			assetEntryQuery.getOrderByType1(), assetEntryQuery.getOrderByCol1(),
			locale);
		Sort sort2 = getSort(
			assetEntryQuery.getOrderByType2(), assetEntryQuery.getOrderByCol2(),
			locale);

		return new Sort[] {sort1, sort2};
	}

	protected static int getSortType(String sortField) {
		int sortType = Sort.STRING_TYPE;

		if (sortField.equals(Field.CREATE_DATE) ||
			sortField.equals(Field.EXPIRATION_DATE) ||
			sortField.equals(Field.PUBLISH_DATE) ||
			sortField.equals("ddm-date") ||
			sortField.equals("modifiedDate")) {

			sortType = Sort.LONG_TYPE;
		}
		else if (sortField.equals(Field.PRIORITY) ||
				 sortField.equals(Field.RATINGS) ||
				 sortField.equals("ddm-decimal") ||
				 sortField.equals("ddm-number")) {

			sortType = Sort.DOUBLE_TYPE;
		}
		else if (sortField.equals(Field.VIEW_COUNT) ||
				 sortField.equals("ddm-integer")) {

			sortType = Sort.INT_TYPE;
		}

		return sortType;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetUtil.class);

}