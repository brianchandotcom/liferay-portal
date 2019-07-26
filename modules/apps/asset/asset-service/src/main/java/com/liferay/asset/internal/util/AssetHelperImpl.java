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

package com.liferay.asset.internal.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.model.NullClassTypeReader;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.AssetPublisherAddItemHolder;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.AssetSearcher;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = AssetHelper.class)
public class AssetHelperImpl implements AssetHelper {

	@Override
	public Set<String> addLayoutTags(
		HttpServletRequest httpServletRequest, List<AssetTag> tags) {

		Set<String> tagNames = (Set<String>)httpServletRequest.getAttribute(
			WebKeys.ASSET_LAYOUT_TAG_NAMES);

		if (tagNames == null) {
			tagNames = new HashSet<>();

			httpServletRequest.setAttribute(
				WebKeys.ASSET_LAYOUT_TAG_NAMES, tagNames);
		}

		for (AssetTag tag : tags) {
			tagNames.add(tag.getName());
		}

		return tagNames;
	}

	@Override
	public PortletURL getAddPortletURL(
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
			Group group = _groupLocalService.fetchGroup(groupId);

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
						_assetCategoryLocalService.fetchAssetCategory(
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

		return addPortletURL;
	}

	@Override
	public String getAddURLPopUp(
		long groupId, long plid, PortletURL addPortletURL,
		boolean addDisplayPageParameter, Layout layout) {

		addPortletURL.setParameter("groupId", String.valueOf(groupId));

		if (addDisplayPageParameter && (layout != null)) {
			addPortletURL.setParameter("layoutUuid", layout.getUuid());
		}

		if (addPortletURL instanceof LiferayPortletURL) {
			LiferayPortletURL liferayPortletURL =
				(LiferayPortletURL)addPortletURL;

			liferayPortletURL.setRefererPlid(plid);

			return liferayPortletURL.toString();
		}

		return _http.addParameter(
			addPortletURL.toString(), "refererPlid", plid);
	}

	@Override
	public List<AssetEntry> getAssetEntries(Hits hits) {
		if (hits.getDocs() == null) {
			return Collections.emptyList();
		}

		List<AssetEntry> assetEntries = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			String className = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				className, classPK);

			if (assetEntry != null) {
				assetEntries.add(assetEntry);
			}
		}

		return assetEntries;
	}

	@Override
	public String getAssetKeywords(String className, long classPK) {
		String[] tagNames = _assetTagLocalService.getTagNames(
			className, classPK);
		String[] categoryNames = _assetCategoryLocalService.getCategoryNames(
			className, classPK);

		String[] keywords = new String[tagNames.length + categoryNames.length];

		ArrayUtil.combine(tagNames, categoryNames, keywords);

		return StringUtil.merge(keywords);
	}

	@Override
	public List<AssetPublisherAddItemHolder> getAssetPublisherAddItemHolders(
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
			String className = _portal.getClassName(classNameId);

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			if (Validator.isNull(assetRendererFactory.getPortletId())) {
				continue;
			}

			Portlet portlet = _portletLocalService.getPortletById(
				themeDisplay.getCompanyId(),
				assetRendererFactory.getPortletId());

			if (!portlet.isActive()) {
				continue;
			}

			PortletBag portletBag = PortletBagPool.get(
				portlet.getRootPortletId());

			if (portletBag == null) {
				continue;
			}

			ResourceBundle resourceBundle = portletBag.getResourceBundle(
				locale);

			ClassTypeReader classTypeReader =
				assetRendererFactory.getClassTypeReader();

			List<ClassType> classTypes = Collections.emptyList();

			if (!(classTypeReader instanceof NullClassTypeReader)) {
				classTypes = classTypeReader.getAvailableClassTypes(
					_portal.getCurrentAndAncestorSiteGroupIds(groupId),
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

	@Override
	public boolean isValidWord(String word) {
		if (Validator.isBlank(word)) {
			return false;
		}

		char[] wordCharArray = word.toCharArray();

		for (char c : wordCharArray) {
			for (char invalidChar : AssetHelper.INVALID_CHARACTERS) {
				if (c == invalidChar) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Word ", word, " is not valid because ", c,
								" is not allowed"));
					}

					return false;
				}
			}
		}

		return true;
	}

	@Override
	public Hits search(
			HttpServletRequest httpServletRequest,
			AssetEntryQuery assetEntryQuery, int start, int end)
		throws Exception {

		SearchContext searchContext = SearchContextFactory.getInstance(
			httpServletRequest);

		return search(searchContext, assetEntryQuery, start, end);
	}

	@Override
	public Hits search(
			SearchContext searchContext, AssetEntryQuery assetEntryQuery,
			int start, int end)
		throws Exception {

		AssetSearcher assetSearcher = _getAssetSearcher(
			searchContext, assetEntryQuery, start, end);

		return assetSearcher.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<AssetEntry> searchAssetEntries(
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

	@Override
	public BaseModelSearchResult<AssetEntry> searchAssetEntries(
			HttpServletRequest httpServletRequest,
			AssetEntryQuery assetEntryQuery, int start, int end)
		throws Exception {

		SearchContext searchContext = SearchContextFactory.getInstance(
			httpServletRequest);

		return searchAssetEntries(searchContext, assetEntryQuery, start, end);
	}

	@Override
	public BaseModelSearchResult<AssetEntry> searchAssetEntries(
			SearchContext searchContext, AssetEntryQuery assetEntryQuery,
			int start, int end)
		throws Exception {

		AssetSearcher assetSearcher = _getAssetSearcher(
			searchContext, assetEntryQuery, start, end);

		Hits hits = assetSearcher.search(searchContext);

		return new BaseModelSearchResult<>(
			getAssetEntries(hits), hits.getLength());
	}

	private AssetSearcher _getAssetSearcher(
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
		searchContext.setGroupIds(
			ArrayUtil.clone(assetEntryQuery.getGroupIds()));

		if (Validator.isNull(assetEntryQuery.getKeywords())) {
			QueryConfig queryConfig = searchContext.getQueryConfig();

			queryConfig.setScoreEnabled(false);
		}
		else {
			searchContext.setLike(true);
		}

		searchContext.setSorts(
			_getSorts(assetEntryQuery, searchContext.getLocale()));
		searchContext.setStart(start);

		return assetSearcher;
	}

	private boolean _getDDMFormFieldLocalizable(String sortField)
		throws PortalException {

		String[] sortFields = StringUtil.split(
			sortField, DDMStructureManager.STRUCTURE_INDEXER_FIELD_SEPARATOR);

		long ddmStructureId = GetterUtil.getLong(sortFields[2]);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			ddmStructureId);

		return GetterUtil.getBoolean(
			ddmStructure.getFieldProperty(sortFields[3], "localizable"));
	}

	private String _getDDMFormFieldType(String sortField)
		throws PortalException {

		String[] sortFields = sortField.split(
			DDMStructureManager.STRUCTURE_INDEXER_FIELD_SEPARATOR);

		long ddmStructureId = GetterUtil.getLong(sortFields[2]);
		String fieldName = sortFields[3];

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			ddmStructureId);

		return ddmStructure.getFieldType(fieldName);
	}

	private String _getOrderByCol(
		String sortField, String fieldType, boolean fieldLocalizable,
		int sortType, Locale locale) {

		if (sortField.startsWith(
				DDMStructureManager.STRUCTURE_INDEXER_FIELD_PREFIX)) {

			StringBundler sb = new StringBundler(5);

			sb.append(sortField);
			sb.append(StringPool.UNDERLINE);

			if (fieldLocalizable) {
				sb.append(LocaleUtil.toLanguageId(locale));
				sb.append(StringPool.UNDERLINE);
			}

			String suffix = "String";

			if (!fieldType.equals("ddm-date") &&
				((sortType == Sort.DOUBLE_TYPE) ||
				 (sortType == Sort.FLOAT_TYPE) || (sortType == Sort.INT_TYPE) ||
				 (sortType == Sort.LONG_TYPE))) {

				suffix = "Number";
			}

			sb.append(suffix);

			sortField = Field.getSortableFieldName(sb.toString());
		}
		else if (sortField.equals("modifiedDate")) {
			sortField = Field.MODIFIED_DATE;
		}
		else if (sortField.equals("title")) {
			sortField = Field.getSortableFieldName(
				"localized_title_".concat(LocaleUtil.toLanguageId(locale)));
		}

		return sortField;
	}

	private Sort _getSort(String orderByType, String sortField, Locale locale)
		throws Exception {

		boolean ddmFormFieldLocalizable = true;
		String ddmFormFieldType = sortField;

		if (ddmFormFieldType.startsWith(
				DDMStructureManager.STRUCTURE_INDEXER_FIELD_PREFIX)) {

			ddmFormFieldLocalizable = _getDDMFormFieldLocalizable(sortField);

			ddmFormFieldType = _getDDMFormFieldType(ddmFormFieldType);
		}

		int sortType = _getSortType(ddmFormFieldType);

		return SortFactoryUtil.getSort(
			AssetEntry.class, sortType,
			_getOrderByCol(
				sortField, ddmFormFieldType, ddmFormFieldLocalizable, sortType,
				locale),
			!sortField.startsWith(
				DDMStructureManager.STRUCTURE_INDEXER_FIELD_PREFIX),
			orderByType);
	}

	private Sort[] _getSorts(AssetEntryQuery assetEntryQuery, Locale locale)
		throws Exception {

		Sort sort1 = _getSort(
			assetEntryQuery.getOrderByType1(), assetEntryQuery.getOrderByCol1(),
			locale);
		Sort sort2 = _getSort(
			assetEntryQuery.getOrderByType2(), assetEntryQuery.getOrderByCol2(),
			locale);

		return new Sort[] {sort1, sort2};
	}

	private int _getSortType(String fieldType) {
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
		else if (fieldType.equals("ddm-integer") ||
				 fieldType.equals("viewCount")) {

			sortType = Sort.INT_TYPE;
		}

		return sortType;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetHelperImpl.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

}