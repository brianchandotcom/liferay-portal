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

package com.liferay.portlet.asset.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryProperty;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.model.NullClassTypeReader;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetCategoryPropertyLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetUtil {

	public static final int ASSET_ENTRY_ABSTRACT_LENGTH = 200;

	public static final String CLASSNAME_SEPARATOR = "_CLASSNAME_";

	public static final char[] INVALID_CHARACTERS = new char[] {
		CharPool.AMPERSAND, CharPool.APOSTROPHE, CharPool.AT,
		CharPool.BACK_SLASH, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.COLON, CharPool.COMMA, CharPool.EQUAL, CharPool.GREATER_THAN,
		CharPool.FORWARD_SLASH, CharPool.LESS_THAN, CharPool.NEW_LINE,
		CharPool.OPEN_BRACKET, CharPool.OPEN_CURLY_BRACE, CharPool.PERCENT,
		CharPool.PIPE, CharPool.PLUS, CharPool.POUND, CharPool.PRIME,
		CharPool.QUESTION, CharPool.QUOTE, CharPool.RETURN, CharPool.SEMICOLON,
		CharPool.SLASH, CharPool.STAR, CharPool.TILDE
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

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		boolean portletBreadcrumbEntry = false;

		if (Validator.isNotNull(portletDisplay.getId()) &&
			!portletDisplay.isFocused()) {

			portletBreadcrumbEntry = true;
		}

		addPortletBreadcrumbEntries(
			assetCategoryId, request, portletURL, portletBreadcrumbEntry);
	}

	public static void addPortletBreadcrumbEntries(
			long assetCategoryId, HttpServletRequest request,
			PortletURL portletURL, boolean portletBreadcrumbEntry)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategory(
			assetCategoryId);

		List<AssetCategory> ancestorCategories = assetCategory.getAncestors();

		Collections.reverse(ancestorCategories);

		for (AssetCategory ancestorCategory : ancestorCategories) {
			portletURL.setParameter(
				"categoryId", String.valueOf(ancestorCategory.getCategoryId()));

			PortalUtil.addPortletBreadcrumbEntry(
				request, ancestorCategory.getTitle(themeDisplay.getLocale()),
				portletURL.toString(), null, portletBreadcrumbEntry);
		}

		portletURL.setParameter("categoryId", String.valueOf(assetCategoryId));

		PortalUtil.addPortletBreadcrumbEntry(
			request, assetCategory.getTitle(themeDisplay.getLocale()),
			portletURL.toString(), null, portletBreadcrumbEntry);
	}

	public static String checkViewURL(
		AssetEntry assetEntry, boolean viewInContext, String viewURL,
		String currentURL, ThemeDisplay themeDisplay) {

		if (Validator.isNull(viewURL)) {
			return viewURL;
		}

		viewURL = HttpUtil.setParameter(
			viewURL, "inheritRedirect", viewInContext);

		Layout layout = themeDisplay.getLayout();

		String assetEntryLayoutUuid = assetEntry.getLayoutUuid();

		if (!viewInContext ||
			(Validator.isNotNull(assetEntryLayoutUuid) &&
			 !assetEntryLayoutUuid.equals(layout.getUuid()))) {

			viewURL = HttpUtil.setParameter(viewURL, "redirect", currentURL);
		}

		return viewURL;
	}

	public static long[] filterCategoryIds(
			PermissionChecker permissionChecker, long[] categoryIds)
		throws PortalException {

		List<Long> viewableCategoryIds = new ArrayList<>();

		for (long categoryId : categoryIds) {
			AssetCategory category =
				AssetCategoryLocalServiceUtil.fetchCategory(categoryId);

			if ((category != null) &&
				AssetCategoryPermission.contains(
					permissionChecker, category, ActionKeys.VIEW)) {

				viewableCategoryIds.add(categoryId);
			}
		}

		return ArrayUtil.toArray(
			viewableCategoryIds.toArray(new Long[viewableCategoryIds.size()]));
	}

	public static List<AssetVocabulary> filterVocabularies(
		List<AssetVocabulary> vocabularies, String className,
		final long classTypePK) {

		final long classNameId = PortalUtil.getClassNameId(className);

		PredicateFilter<AssetVocabulary> predicateFilter =
			new PredicateFilter<AssetVocabulary>() {

				@Override
				public boolean filter(AssetVocabulary assetVocabulary) {
					return
						assetVocabulary.isAssociatedToClassNameIdAndClassTypePK(
							classNameId, classTypePK);
				}

			};

		return ListUtil.filter(vocabularies, predicateFilter);
	}

	public static long[] filterVocabularyIds(
			PermissionChecker permissionChecker, long[] vocabularyIds)
		throws PortalException {

		List<Long> viewableVocabularyIds = new ArrayList<>();

		for (long vocabularyId : vocabularyIds) {
			if (AssetVocabularyPermission.contains(
					permissionChecker, vocabularyId, ActionKeys.VIEW)) {

				viewableVocabularyIds.add(vocabularyId);
			}
		}

		return ArrayUtil.toArray(
			viewableVocabularyIds.toArray(
				new Long[viewableVocabularyIds.size()]));
	}

	public static PortletURL getAddPortletURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long groupId,
			String className, long classTypeId, long[] allAssetCategoryIds,
			String[] allAssetTagNames, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.hasAddPermission(
				themeDisplay.getPermissionChecker(), groupId, classTypeId)) {

			return null;
		}

		if (groupId > 0) {
			Group group = GroupLocalServiceUtil.fetchGroup(groupId);

			liferayPortletRequest.setAttribute(
				WebKeys.ASSET_RENDERER_FACTORY_GROUP, group);
		}

		PortletURL addPortletURL = assetRendererFactory.getURLAdd(
			liferayPortletRequest, liferayPortletResponse, classTypeId);

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
					new HashMap<>();

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

		addPortletURL.setPortletMode(PortletMode.VIEW);
		addPortletURL.setWindowState(LiferayWindowState.POP_UP);

		return addPortletURL;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getAddPortletURL(LiferayPortletRequest,
	 *             LiferayPortletResponse, long, String, long, long[], String[],
	 *             String)}
	 */
	@Deprecated
	public static PortletURL getAddPortletURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classTypeId, long[] allAssetCategoryIds,
			String[] allAssetTagNames, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return getAddPortletURL(
			liferayPortletRequest, liferayPortletResponse,
			themeDisplay.getScopeGroupId(), className, classTypeId,
			allAssetCategoryIds, allAssetTagNames, redirect);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getAssetPublisherAddItemHolders(LiferayPortletRequest,
	 *             LiferayPortletResponse, long, long[],
	 *             long[], long[], String[], String)}
	 */
	@Deprecated
	public static Map<String, PortletURL> getAddPortletURLs(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long groupId,
			long[] classNameIds, long[] classTypeIds,
			long[] allAssetCategoryIds, String[] allAssetTagNames,
			String redirect)
		throws Exception {

		List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders =
			getAssetPublisherAddItemHolders(
				liferayPortletRequest, liferayPortletResponse, groupId,
				classNameIds, classTypeIds, allAssetCategoryIds,
				allAssetTagNames, redirect);

		Map<String, PortletURL> addPortletURLs = new LinkedHashMap<>();

		for (AssetPublisherAddItemHolder assetPublisherAddItemHolder :
				assetPublisherAddItemHolders) {

			addPortletURLs.put(
				assetPublisherAddItemHolder.getName(),
				assetPublisherAddItemHolder.getPortletURL());
		}

		return addPortletURLs;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getAssetPublisherAddItemHolders(LiferayPortletRequest,
	 *             LiferayPortletResponse, long, long[], long[], long[],
	 *             String[], String)}
	 */
	@Deprecated
	public static Map<String, PortletURL> getAddPortletURLs(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long[] classNameIds,
			long[] classTypeIds, long[] allAssetCategoryIds,
			String[] allAssetTagNames, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return getAddPortletURLs(
			liferayPortletRequest, liferayPortletResponse,
			themeDisplay.getScopeGroupId(), classNameIds, classTypeIds,
			allAssetCategoryIds, allAssetTagNames, redirect);
	}

	public static String getAddURLPopUp(
		long groupId, long plid, PortletURL addPortletURL,
		boolean addDisplayPageParameter, Layout layout) {

		addPortletURL.setParameter(
			"hideDefaultSuccessMessage", Boolean.TRUE.toString());
		addPortletURL.setParameter("groupId", String.valueOf(groupId));
		addPortletURL.setParameter("showHeader", Boolean.FALSE.toString());

		if (addDisplayPageParameter && (layout != null)) {
			addPortletURL.setParameter("layoutUuid", layout.getUuid());
		}

		if (addPortletURL instanceof PortletURLImpl) {
			PortletURLImpl portletURLImpl = (PortletURLImpl)addPortletURL;

			portletURLImpl.setRefererPlid(plid);

			return portletURLImpl.toString();
		}

		return HttpUtil.addParameter(
			addPortletURL.toString(), "refererPlid", plid);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getAddURLPopUp(long, long,
	 *             PortletURL, boolean, Layout)}
	 */
	@Deprecated
	public static String getAddURLPopUp(
		long groupId, long plid, PortletURL addPortletURL, String portletId,
		boolean addDisplayPageParameter, Layout layout) {

		return getAddURLPopUp(
			groupId, plid, addPortletURL, addDisplayPageParameter, layout);
	}

	public static List<AssetEntry> getAssetEntries(Hits hits) {
		List<AssetEntry> assetEntries = new ArrayList<>();

		if (hits.getDocs() == null) {
			return assetEntries;
		}

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

	public static String getAssetKeywords(String className, long classPK) {
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

	public static List<AssetPublisherAddItemHolder>
			getAssetPublisherAddItemHolders(
				LiferayPortletRequest liferayPortletRequest,
				LiferayPortletResponse liferayPortletResponse, long groupId,
				long[] classNameIds, long[] classTypeIds,
				long[] allAssetCategoryIds, String[] allAssetTagNames,
				String redirect)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders =
			new ArrayList<>();

		for (long classNameId : classNameIds) {
			String className = PortalUtil.getClassName(classNameId);

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			if (Validator.isNull(assetRendererFactory.getPortletId())) {
				continue;
			}

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(),
				assetRendererFactory.getPortletId());

			if (!portlet.isActive()) {
				continue;
			}

			PortletBag portletBag = PortletBagPool.get(
				portlet.getRootPortletId());

			ResourceBundle resourceBundle = portletBag.getResourceBundle(
				locale);

			ClassTypeReader classTypeReader =
				assetRendererFactory.getClassTypeReader();

			List<ClassType> classTypes = Collections.emptyList();

			if (!(classTypeReader instanceof NullClassTypeReader)) {
				classTypes = classTypeReader.getAvailableClassTypes(
					PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
					themeDisplay.getLocale());
			}

			if (classTypes.isEmpty()) {
				PortletURL addPortletURL = getAddPortletURL(
					liferayPortletRequest, liferayPortletResponse, groupId,
					className, 0, allAssetCategoryIds, allAssetTagNames,
					redirect);

				if (addPortletURL != null) {
					assetPublisherAddItemHolders.add(
						new AssetPublisherAddItemHolder(
							portlet.getPortletId(), className, resourceBundle,
							locale, addPortletURL));
				}
			}

			for (ClassType classType : classTypes) {
				long classTypeId = classType.getClassTypeId();

				if (ArrayUtil.contains(classTypeIds, classTypeId) ||
					(classTypeIds.length == 0)) {

					PortletURL addPortletURL = getAddPortletURL(
						liferayPortletRequest, liferayPortletResponse, groupId,
						className, classTypeId, allAssetCategoryIds,
						allAssetTagNames, redirect);

					if (addPortletURL != null) {
						assetPublisherAddItemHolders.add(
							new AssetPublisherAddItemHolder(
								portlet.getPortletId(), classType.getName(),
								resourceBundle, locale, addPortletURL));
					}
				}
			}
		}

		if (assetPublisherAddItemHolders.size() <= 1) {
			return assetPublisherAddItemHolders;
		}

		assetPublisherAddItemHolders.sort(null);

		return assetPublisherAddItemHolders;
	}

	public static String getClassName(String className) {
		int pos = className.indexOf(AssetUtil.CLASSNAME_SEPARATOR);

		if (pos != -1) {
			className = className.substring(0, pos);
		}

		return className;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String getClassNameMessage(String className, Locale locale) {
		String message = null;

		int pos = className.indexOf(AssetUtil.CLASSNAME_SEPARATOR);

		if (pos != -1) {
			message = className.substring(
				pos + AssetUtil.CLASSNAME_SEPARATOR.length());

			className = className.substring(0, pos);
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (pos == -1) {
			message = assetRendererFactory.getTypeName(locale);
		}

		return message;
	}

	public static String getDefaultAssetPublisherId(Layout layout) {
		return layout.getTypeSettingsProperty(
			LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
			StringPool.BLANK);
	}

	public static Set<String> getLayoutTagNames(HttpServletRequest request) {
		Set<String> tagNames = (Set<String>)request.getAttribute(
			WebKeys.ASSET_LAYOUT_TAG_NAMES);

		if (tagNames == null) {
			tagNames = new HashSet<>();

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

	public static boolean isDefaultAssetPublisher(
		Layout layout, String portletId, String portletResource) {

		String defaultAssetPublisherPortletId = getDefaultAssetPublisherId(
			layout);

		if (Validator.isNull(defaultAssetPublisherPortletId)) {
			return false;
		}

		if (defaultAssetPublisherPortletId.equals(portletId) ||
			defaultAssetPublisherPortletId.equals(portletResource)) {

			return true;
		}

		return false;
	}

	public static boolean isValidWord(String word) {
		if (Validator.isBlank(word)) {
			return false;
		}

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

		AssetSearcher assetSearcher = getAssetSearcher(
			searchContext, assetEntryQuery, start, end);

		return assetSearcher.search(searchContext);
	}

	public static BaseModelSearchResult<AssetEntry> searchAssetEntries(
			AssetEntryQuery assetEntryQuery, long[] assetCategoryIds,
			String[] assetTagNames, Map<String, Serializable> attributes,
			long companyId, String keywords, Layout layout, Locale locale,
			long scopeGroupId, TimeZone timeZone, long userId, int start,
			int end)
		throws Exception {

		SearchContext searchContext = SearchContextFactory.getInstance(
			assetCategoryIds, assetTagNames, attributes, companyId, keywords,
			layout, locale, scopeGroupId, timeZone, userId);

		return searchAssetEntries(searchContext, assetEntryQuery, start, end);
	}

	public static BaseModelSearchResult<AssetEntry> searchAssetEntries(
			HttpServletRequest request, AssetEntryQuery assetEntryQuery,
			int start, int end)
		throws Exception {

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		return searchAssetEntries(searchContext, assetEntryQuery, start, end);
	}

	public static BaseModelSearchResult<AssetEntry> searchAssetEntries(
			SearchContext searchContext, AssetEntryQuery assetEntryQuery,
			int start, int end)
		throws Exception {

		AssetSearcher assetSearcher = getAssetSearcher(
			searchContext, assetEntryQuery, start, end);

		Hits hits = assetSearcher.search(searchContext);

		List<AssetEntry> assetEntries = getAssetEntries(hits);

		return new BaseModelSearchResult<>(assetEntries, hits.getLength());
	}

	public static String substituteCategoryPropertyVariables(
		long groupId, long categoryId, String s) {

		String result = s;

		AssetCategory category = null;

		if (categoryId > 0) {
			category = AssetCategoryLocalServiceUtil.fetchCategory(categoryId);
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

	public static String toWord(String text) {
		if (Validator.isNull(text)) {
			return text;
		}

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

	protected static AssetSearcher getAssetSearcher(
			SearchContext searchContext, AssetEntryQuery assetEntryQuery,
			int start, int end)
		throws Exception {

		Indexer<?> searcher = AssetSearcher.getInstance();

		AssetSearcher assetSearcher = (AssetSearcher)searcher;

		assetSearcher.setAssetEntryQuery(assetEntryQuery);

		Layout layout = assetEntryQuery.getLayout();

		if (layout != null) {
			searchContext.setAttribute(Field.LAYOUT_UUID, layout.getUuid());
		}

		String ddmStructureFieldName = (String)assetEntryQuery.getAttribute(
			"ddmStructureFieldName");
		Serializable ddmStructureFieldValue = assetEntryQuery.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			searchContext.setAttribute(
				"ddmStructureFieldName", ddmStructureFieldName);
			searchContext.setAttribute(
				"ddmStructureFieldValue", ddmStructureFieldValue);
		}

		String paginationType = GetterUtil.getString(
			assetEntryQuery.getPaginationType(), "more");

		if (!paginationType.equals("none") &&
			!paginationType.equals("simple")) {

			searchContext.setAttribute("paginationType", paginationType);
		}

		searchContext.setClassTypeIds(assetEntryQuery.getClassTypeIds());
		searchContext.setEnd(end);
		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		if (Validator.isNull(assetEntryQuery.getKeywords())) {
			QueryConfig queryConfig = searchContext.getQueryConfig();

			queryConfig.setScoreEnabled(false);
		}
		else {
			searchContext.setLike(true);
		}

		searchContext.setSorts(
			getSorts(assetEntryQuery, searchContext.getLocale()));
		searchContext.setStart(start);

		return assetSearcher;
	}

	protected static String getDDMFormFieldType(String sortField)
		throws PortalException {

		String[] sortFields = sortField.split(
			DDMStructureManager.STRUCTURE_INDEXER_FIELD_SEPARATOR);

		long ddmStructureId = GetterUtil.getLong(sortFields[2]);
		String fieldName = sortFields[3];

		DDMStructure ddmStructure = DDMStructureManagerUtil.getStructure(
			ddmStructureId);

		return ddmStructure.getFieldType(fieldName);
	}

	protected static String getOrderByCol(
		String sortField, String fieldType, int sortType, Locale locale) {

		if (sortField.startsWith(
				DDMStructureManager.STRUCTURE_INDEXER_FIELD_PREFIX)) {

			sortField = sortField.concat(StringPool.UNDERLINE).concat(
				LocaleUtil.toLanguageId(locale));

			if (!fieldType.equals("ddm-date") &&
				((sortType == Sort.DOUBLE_TYPE) ||
				 (sortType == Sort.FLOAT_TYPE) || (sortType == Sort.INT_TYPE) ||
				 (sortType == Sort.LONG_TYPE))) {

				sortField = sortField.concat(StringPool.UNDERLINE).concat(
					"Number");
			}

			sortField = DocumentImpl.getSortableFieldName(sortField);
		}
		else if (sortField.equals("modifiedDate")) {
			sortField = Field.MODIFIED_DATE;
		}
		else if (sortField.equals("title")) {
			sortField = DocumentImpl.getSortableFieldName(
				"localized_title_".concat(LocaleUtil.toLanguageId(locale)));
		}

		return sortField;
	}

	protected static Sort getSort(
			String orderByType, String sortField, Locale locale)
		throws Exception {

		String ddmFormFieldType = sortField;

		if (ddmFormFieldType.startsWith(
				DDMStructureManager.STRUCTURE_INDEXER_FIELD_PREFIX)) {

			ddmFormFieldType = getDDMFormFieldType(ddmFormFieldType);
		}

		int sortType = getSortType(ddmFormFieldType);

		return SortFactoryUtil.getSort(
			AssetEntry.class, sortType,
			getOrderByCol(sortField, ddmFormFieldType, sortType, locale),
			!sortField.startsWith(
				DDMStructureManager.STRUCTURE_INDEXER_FIELD_PREFIX),
			orderByType);
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

	protected static int getSortType(String fieldType) {
		int sortType = Sort.STRING_TYPE;

		if (fieldType.equals(Field.CREATE_DATE) ||
			fieldType.equals(Field.EXPIRATION_DATE) ||
			fieldType.equals(Field.PUBLISH_DATE) ||
			fieldType.equals("ddm-date") || fieldType.equals("modifiedDate")) {

			sortType = Sort.LONG_TYPE;
		}
		else if (fieldType.equals(Field.PRIORITY) ||
				 fieldType.equals("ddm-decimal") ||
				 fieldType.equals("ddm-number")) {

			sortType = Sort.DOUBLE_TYPE;
		}
		else if (fieldType.equals("ddm-integer")) {
			sortType = Sort.INT_TYPE;
		}

		return sortType;
	}

	private static final Log _log = LogFactoryUtil.getLog(AssetUtil.class);

}