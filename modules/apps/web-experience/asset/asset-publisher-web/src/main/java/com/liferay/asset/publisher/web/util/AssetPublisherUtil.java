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

package com.liferay.asset.publisher.web.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.configuration.AssetPublisherPortletInstanceConfiguration;
import com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.display.context.AssetEntryResult;
import com.liferay.asset.publisher.web.display.context.AssetPublisherDisplayContext;
import com.liferay.asset.util.AssetEntryQueryProcessor;
import com.liferay.asset.util.AssetHelper;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PrimitiveLongList;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.StrictPortletPreferencesImpl;
import com.liferay.sites.kernel.util.SitesUtil;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * Provides utility methods for managing the configuration, managing scopes of
 * content, and obtaining lists of assets for the Asset Publisher portlet.
 *
 * @author Raymond Augé
 * @author Julio Camarero
 * @deprecated As of 1.2.0, replaced by {@link
 *             com.liferay.asset.publisher.util.AssetPublisherHelper}
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration",
	immediate = true, service = AssetPublisherUtil.class
)
@Deprecated
@ProviderType
public class AssetPublisherUtil {

	public static final String SCOPE_ID_CHILD_GROUP_PREFIX = "ChildGroup_";

	public static final String SCOPE_ID_GROUP_PREFIX = "Group_";

	public static final String SCOPE_ID_LAYOUT_PREFIX = "Layout_";

	public static final String SCOPE_ID_LAYOUT_UUID_PREFIX = "LayoutUuid_";

	public static final String SCOPE_ID_PARENT_GROUP_PREFIX = "ParentGroup_";

	public static void addAndStoreSelection(
			PortletRequest portletRequest, String className, long classPK,
			int assetEntryOrder)
		throws Exception {

		String portletId = PortalUtil.getPortletId(portletRequest);

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		if (!rootPortletId.equals(AssetPublisherPortletKeys.ASSET_PUBLISHER)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, portletId);

		if (portletPreferences instanceof StrictPortletPreferencesImpl) {
			return;
		}

		String selectionStyle = portletPreferences.getValue(
			"selectionStyle", "dynamic");

		if (selectionStyle.equals("dynamic")) {
			return;
		}

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			className, classPK);

		addSelection(
			themeDisplay, portletPreferences, portletId,
			assetEntry.getEntryId(), assetEntryOrder, className);

		portletPreferences.store();
	}

	public static void addSelection(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences, String portletId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long assetEntryId = ParamUtil.getLong(portletRequest, "assetEntryId");
		int assetEntryOrder = ParamUtil.getInteger(
			portletRequest, "assetEntryOrder");
		String assetEntryType = ParamUtil.getString(
			portletRequest, "assetEntryType");

		addSelection(
			themeDisplay, portletPreferences, portletId, assetEntryId,
			assetEntryOrder, assetEntryType);
	}

	public static void addSelection(
			ThemeDisplay themeDisplay, PortletPreferences portletPreferences,
			String portletId, long assetEntryId, int assetEntryOrder,
			String assetEntryType)
		throws Exception {

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetEntryId);

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		String assetEntryXml = _getAssetEntryXml(
			assetEntryType, assetEntry.getClassUuid());

		if (!ArrayUtil.contains(assetEntryXmls, assetEntryXml)) {
			if (assetEntryOrder > -1) {
				assetEntryXmls[assetEntryOrder] = assetEntryXml;
			}
			else {
				assetEntryXmls = ArrayUtil.append(
					assetEntryXmls, assetEntryXml);
			}

			portletPreferences.setValues("assetEntryXml", assetEntryXmls);
		}

		try {
			portletPreferences.store();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public static void addUserAttributes(
		User user, String[] customUserAttributeNames,
		AssetEntryQuery assetEntryQuery) {

		if ((user == null) || (customUserAttributeNames.length == 0)) {
			return;
		}

		Group companyGroup = _groupLocalService.fetchCompanyGroup(
			user.getCompanyId());

		long[] allCategoryIds = assetEntryQuery.getAllCategoryIds();

		PrimitiveLongList allCategoryIdsList = new PrimitiveLongList(
			allCategoryIds.length + customUserAttributeNames.length);

		allCategoryIdsList.addAll(allCategoryIds);

		for (String customUserAttributeName : customUserAttributeNames) {
			ExpandoBridge userCustomAttributes = user.getExpandoBridge();

			Serializable userCustomFieldValue =
				userCustomAttributes.getAttribute(customUserAttributeName);

			if (userCustomFieldValue == null) {
				continue;
			}

			String userCustomFieldValueString = userCustomFieldValue.toString();

			List<AssetCategory> assetCategories =
				_assetCategoryLocalService.search(
					companyGroup.getGroupId(), userCustomFieldValueString,
					new String[0], QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (AssetCategory assetCategory : assetCategories) {
				allCategoryIdsList.add(assetCategory.getCategoryId());
			}
		}

		assetEntryQuery.setAllCategoryIds(allCategoryIdsList.getArray());
	}

	public static String encodeName(
		long ddmStructureId, String fieldName, Locale locale) {

		return _ddmIndexer.encodeName(ddmStructureId, fieldName, locale);
	}

	public static String filterAssetTagNames(
		long groupId, String assetTagNames) {

		List<String> filteredAssetTagNames = new ArrayList<>();

		String[] assetTagNamesArray = StringUtil.split(assetTagNames);

		long[] assetTagIds = _assetTagLocalService.getTagIds(
			groupId, assetTagNamesArray);

		for (long assetTagId : assetTagIds) {
			AssetTag assetTag = _assetTagLocalService.fetchAssetTag(assetTagId);

			if (assetTag != null) {
				filteredAssetTagNames.add(assetTag.getName());
			}
		}

		return StringUtil.merge(filteredAssetTagNames);
	}

	public static long[] getAssetCategoryIds(
		PortletPreferences portletPreferences) {

		long[] assetCategoryIds = new long[0];

		for (int i = 0; true; i++) {
			String[] queryValues = portletPreferences.getValues(
				"queryValues" + i, null);

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = portletPreferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (Objects.equals(queryName, "assetCategories") && queryContains &&
				(queryAndOperator || (queryValues.length == 1))) {

				assetCategoryIds = ArrayUtil.append(
					assetCategoryIds, GetterUtil.getLongValues(queryValues));
			}
		}

		return assetCategoryIds;
	}

	public static BaseModelSearchResult<AssetEntry> getAssetEntries(
			AssetEntryQuery assetEntryQuery, Layout layout,
			PortletPreferences portletPreferences, String portletName,
			Locale locale, TimeZone timeZone, long companyId, long scopeGroupId,
			long userId, Map<String, Serializable> attributes, int start,
			int end)
		throws Exception {

		if (isSearchWithIndex(portletName, assetEntryQuery)) {
			return _assetHelper.searchAssetEntries(
				assetEntryQuery, getAssetCategoryIds(portletPreferences),
				getAssetTagNames(portletPreferences), attributes, companyId,
				assetEntryQuery.getKeywords(), layout, locale, scopeGroupId,
				timeZone, userId, start, end);
		}

		int total = _assetEntryService.getEntriesCount(assetEntryQuery);

		assetEntryQuery.setEnd(end);
		assetEntryQuery.setStart(start);

		List<AssetEntry> results = _assetEntryService.getEntries(
			assetEntryQuery);

		return new BaseModelSearchResult<>(results, total);
	}

	/**
	 * @deprecated As of 2.0.0, with no direct replacement
	 */
	@Deprecated
	public static List<AssetEntry> getAssetEntries(
			PortletPreferences portletPreferences, Layout layout,
			long scopeGroupId, int max, boolean checkPermission)
		throws PortalException {

		return Collections.emptyList();
	}

	public static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			boolean deleteMissingAssetEntries, boolean checkPermission)
		throws Exception {

		return getAssetEntries(
			portletRequest, portletPreferences, permissionChecker, groupIds,
			deleteMissingAssetEntries, checkPermission, false);
	}

	public static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			boolean deleteMissingAssetEntries, boolean checkPermission,
			boolean includeNonVisibleAssets)
		throws Exception {

		return getAssetEntries(
			portletRequest, portletPreferences, permissionChecker, groupIds,
			deleteMissingAssetEntries, checkPermission, includeNonVisibleAssets,
			AssetRendererFactory.TYPE_LATEST_APPROVED);
	}

	public static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			boolean deleteMissingAssetEntries, boolean checkPermission,
			boolean includeNonVisibleAssets, int type)
		throws Exception {

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		List<AssetEntry> assetEntries = new ArrayList<>();

		List<String> missingAssetEntryUuids = new ArrayList<>();

		for (String assetEntryXml : assetEntryXmls) {
			Document document = SAXReaderUtil.read(assetEntryXml);

			Element rootElement = document.getRootElement();

			String assetEntryUuid = rootElement.elementText("asset-entry-uuid");

			String assetEntryType = rootElement.elementText("asset-entry-type");

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(assetEntryType);

			String portletId = null;

			if (assetRendererFactory != null) {
				portletId = assetRendererFactory.getPortletId();
			}

			AssetEntry assetEntry = null;

			for (long groupId : groupIds) {
				Group group = _groupLocalService.fetchGroup(groupId);

				if ((portletId != null) && group.isStagingGroup() &&
					!group.isStagedPortlet(portletId)) {

					groupId = group.getLiveGroupId();
				}

				assetEntry = _assetEntryLocalService.fetchEntry(
					groupId, assetEntryUuid);

				if (assetEntry != null) {
					break;
				}
			}

			if (assetEntry == null) {
				if (deleteMissingAssetEntries) {
					missingAssetEntryUuids.add(assetEntryUuid);
				}

				continue;
			}

			if (!assetEntry.isVisible() && !includeNonVisibleAssets) {
				continue;
			}

			assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						assetEntry.getClassName());

			if (assetRendererFactory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No asset renderer factory associated with " +
							assetEntry.getClassName());
				}

				continue;
			}

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					assetEntry.getClassPK(), type);

			if (!assetRendererFactory.isActive(
					permissionChecker.getCompanyId())) {

				if (deleteMissingAssetEntries) {
					missingAssetEntryUuids.add(assetEntryUuid);
				}

				continue;
			}

			if (checkPermission) {
				if (!assetRenderer.isDisplayable() &&
					!includeNonVisibleAssets) {

					continue;
				}
				else if (!assetRenderer.hasViewPermission(permissionChecker)) {
					assetRenderer = assetRendererFactory.getAssetRenderer(
						assetEntry.getClassPK(),
						AssetRendererFactory.TYPE_LATEST_APPROVED);

					if (!assetRenderer.hasViewPermission(permissionChecker)) {
						continue;
					}
				}
			}

			assetEntries.add(assetEntry);
		}

		if (deleteMissingAssetEntries) {
			removeAndStoreSelection(missingAssetEntryUuids, portletPreferences);

			if (!missingAssetEntryUuids.isEmpty()) {
				SessionMessages.add(
					portletRequest, "deletedMissingAssetEntries",
					missingAssetEntryUuids);
			}
		}

		return assetEntries;
	}

	public static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			long[] allCategoryIds, String[] allTagNames,
			boolean deleteMissingAssetEntries, boolean checkPermission)
		throws Exception {

		List<AssetEntry> assetEntries = getAssetEntries(
			portletRequest, portletPreferences, permissionChecker, groupIds,
			deleteMissingAssetEntries, checkPermission);

		if (assetEntries.isEmpty() ||
			(ArrayUtil.isEmpty(allCategoryIds) &&
			 ArrayUtil.isEmpty(allTagNames))) {

			return assetEntries;
		}

		if (!ArrayUtil.isEmpty(allCategoryIds)) {
			assetEntries = _filterAssetCategoriesAssetEntries(
				assetEntries, allCategoryIds);
		}

		if (!ArrayUtil.isEmpty(allTagNames)) {
			assetEntries = _filterAssetTagNamesAssetEntries(
				assetEntries, allTagNames);
		}

		return assetEntries;
	}

	public static AssetEntryQuery getAssetEntryQuery(
			PortletPreferences portletPreferences, long groupId, Layout layout,
			long[] overrideAllAssetCategoryIds,
			String[] overrideAllAssetTagNames)
		throws PortalException {

		long[] groupIds = getGroupIds(portletPreferences, groupId, layout);

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		_setCategoriesAndTags(
			assetEntryQuery, portletPreferences, groupIds,
			overrideAllAssetCategoryIds, overrideAllAssetTagNames);

		assetEntryQuery.setGroupIds(groupIds);

		boolean anyAssetType = GetterUtil.getBoolean(
			portletPreferences.getValue("anyAssetType", null), true);

		if (!anyAssetType) {
			long[] availableClassNameIds =
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					layout.getCompanyId());

			long[] classNameIds = getClassNameIds(
				portletPreferences, availableClassNameIds);

			assetEntryQuery.setClassNameIds(classNameIds);
		}

		long[] classTypeIds = GetterUtil.getLongValues(
			portletPreferences.getValues("classTypeIds", null));

		assetEntryQuery.setClassTypeIds(classTypeIds);

		boolean enablePermissions = GetterUtil.getBoolean(
			portletPreferences.getValue("enablePermissions", null));

		assetEntryQuery.setEnablePermissions(enablePermissions);

		boolean excludeZeroViewCount = GetterUtil.getBoolean(
			portletPreferences.getValue("excludeZeroViewCount", null));

		assetEntryQuery.setExcludeZeroViewCount(excludeZeroViewCount);

		boolean showOnlyLayoutAssets = GetterUtil.getBoolean(
			portletPreferences.getValue("showOnlyLayoutAssets", null));

		if (showOnlyLayoutAssets) {
			assetEntryQuery.setLayout(layout);
		}

		String orderByColumn1 = GetterUtil.getString(
			portletPreferences.getValue("orderByColumn1", "modifiedDate"));

		assetEntryQuery.setOrderByCol1(orderByColumn1);

		String orderByColumn2 = GetterUtil.getString(
			portletPreferences.getValue("orderByColumn2", "title"));

		assetEntryQuery.setOrderByCol2(orderByColumn2);

		String orderByType1 = GetterUtil.getString(
			portletPreferences.getValue("orderByType1", "DESC"));

		assetEntryQuery.setOrderByType1(orderByType1);

		String orderByType2 = GetterUtil.getString(
			portletPreferences.getValue("orderByType2", "ASC"));

		assetEntryQuery.setOrderByType2(orderByType2);

		return assetEntryQuery;
	}

	public static AssetEntryQuery getAssetEntryQuery(
		PortletPreferences portletPreferences, long[] scopeGroupIds,
		long[] overrideAllAssetCategoryIds, String[] overrideAllAssetTagNames) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		_setCategoriesAndTags(
			assetEntryQuery, portletPreferences, scopeGroupIds,
			overrideAllAssetCategoryIds, overrideAllAssetTagNames);

		return assetEntryQuery;
	}

	public static List<AssetEntryQueryProcessor>
		getAssetEntryQueryProcessors() {

		return _instance._assetEntryQueryProcessors;
	}

	public static List<AssetEntryResult> getAssetEntryResults(
			AssetPublisherDisplayContext assetPublisherDisplayContext,
			SearchContainer searchContainer,
			PortletPreferences portletPreferences)
		throws Exception {

		AssetEntryQuery assetEntryQuery =
			assetPublisherDisplayContext.getAssetEntryQuery();

		Layout layout = assetPublisherDisplayContext.getLayout();

		String portletName = assetPublisherDisplayContext.getPortletName();

		Locale locale = assetPublisherDisplayContext.getLocale();

		TimeZone timeZone = assetPublisherDisplayContext.getTimeZone();

		long companyId = assetPublisherDisplayContext.getCompanyId();
		long scopeGroupId = assetPublisherDisplayContext.getScopeGroupId();
		long userId = assetPublisherDisplayContext.getUserId();

		long[] classNameIds = assetPublisherDisplayContext.getClassNameIds();

		Map<String, Serializable> attributes =
			assetPublisherDisplayContext.getAttributes();

		return getAssetEntryResults(
			searchContainer, assetEntryQuery, layout, portletPreferences,
			portletName, locale, timeZone, companyId, scopeGroupId, userId,
			classNameIds, attributes);
	}

	public static List<AssetEntryResult> getAssetEntryResults(
			SearchContainer searchContainer, AssetEntryQuery assetEntryQuery,
			Layout layout, PortletPreferences portletPreferences,
			String portletName, Locale locale, TimeZone timeZone,
			long companyId, long scopeGroupId, long userId, long[] classNameIds,
			Map<String, Serializable> attributes)
		throws Exception {

		if (!isShowAssetEntryResults(portletName, assetEntryQuery)) {
			return Collections.emptyList();
		}

		long assetVocabularyId = GetterUtil.getLong(
			portletPreferences.getValue("assetVocabularyId", null));

		if (assetVocabularyId > 0) {
			return getAssetEntryResultsByVocabulary(
				searchContainer, assetEntryQuery, layout, portletPreferences,
				portletName, locale, timeZone, companyId, scopeGroupId, userId,
				classNameIds, assetVocabularyId, attributes);
		}
		else if (assetVocabularyId <= -1) {
			return getAssetEntryResultsByClassName(
				searchContainer, assetEntryQuery, layout, portletPreferences,
				portletName, locale, timeZone, companyId, scopeGroupId, userId,
				classNameIds, attributes);
		}

		return getAssetEntryResultsByDefault(
			searchContainer, assetEntryQuery, layout, portletPreferences,
			portletName, locale, timeZone, companyId, scopeGroupId, userId,
			classNameIds, attributes);
	}

	public static String[] getAssetTagNames(
		PortletPreferences portletPreferences) {

		String[] allAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = portletPreferences.getValues(
				"queryValues" + i, null);

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = portletPreferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (!Objects.equals(queryName, "assetCategories") &&
				queryContains &&
				(queryAndOperator || (queryValues.length == 1))) {

				allAssetTagNames = queryValues;
			}
		}

		return allAssetTagNames;
	}

	public static String getClassName(
		AssetRendererFactory<?> assetRendererFactory) {

		Class<?> clazz = assetRendererFactory.getClass();

		String className = clazz.getName();

		int pos = className.lastIndexOf(StringPool.PERIOD);

		return className.substring(pos + 1);
	}

	public static long[] getClassNameIds(
		PortletPreferences portletPreferences, long[] availableClassNameIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			portletPreferences.getValue(
				"anyAssetType", Boolean.TRUE.toString()));
		String selectionStyle = portletPreferences.getValue(
			"selectionStyle", "dynamic");

		if (anyAssetType || selectionStyle.equals("manual")) {
			return availableClassNameIds;
		}

		long defaultClassNameId = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		if (defaultClassNameId > 0) {
			return new long[] {defaultClassNameId};
		}

		long[] classNameIds = GetterUtil.getLongValues(
			portletPreferences.getValues("classNameIds", null));

		if (ArrayUtil.isNotEmpty(classNameIds)) {
			return classNameIds;
		}
		else {
			return availableClassNameIds;
		}
	}

	public static Long[] getClassTypeIds(
		PortletPreferences portletPreferences, String className,
		List<ClassType> availableClassTypes) {

		Long[] availableClassTypeIds = new Long[availableClassTypes.size()];

		for (int i = 0; i < availableClassTypeIds.length; i++) {
			ClassType classType = availableClassTypes.get(i);

			availableClassTypeIds[i] = classType.getClassTypeId();
		}

		return getClassTypeIds(
			portletPreferences, className, availableClassTypeIds);
	}

	public static Long[] getClassTypeIds(
		PortletPreferences portletPreferences, String className,
		Long[] availableClassTypeIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			portletPreferences.getValue(
				"anyClassType" + className, Boolean.TRUE.toString()));

		if (anyAssetType) {
			return availableClassTypeIds;
		}

		long defaultClassTypeId = GetterUtil.getLong(
			portletPreferences.getValue("anyClassType" + className, null), -1);

		if (defaultClassTypeId > -1) {
			return new Long[] {defaultClassTypeId};
		}

		Long[] classTypeIds = ArrayUtil.toArray(
			StringUtil.split(
				portletPreferences.getValue("classTypeIds" + className, null),
				0L));

		if (classTypeIds != null) {
			return classTypeIds;
		}
		else {
			return availableClassTypeIds;
		}
	}

	public static Map<Locale, String> getEmailAssetEntryAddedBodyMap(
		PortletPreferences portletPreferences) {

		LocalizedValuesMap emailAssetEntryAddedLocalizedBodyMap =
			_assetPublisherPortletInstanceConfiguration.
				emailAssetEntryAddedBody();

		Map<Locale, String> emailAssetEntryAddedBodyMap =
			LocalizationUtil.getLocalizationMap(
				portletPreferences, "emailAssetEntryAddedBody",
				StringPool.BLANK, StringPool.BLANK,
				AssetPublisherUtil.class.getClassLoader());

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		if (Validator.isNull(emailAssetEntryAddedBodyMap.get(defaultLocale))) {
			emailAssetEntryAddedBodyMap.put(
				defaultLocale,
				emailAssetEntryAddedLocalizedBodyMap.getDefaultValue());
		}

		return emailAssetEntryAddedBodyMap;
	}

	public static boolean getEmailAssetEntryAddedEnabled(
		PortletPreferences portletPreferences) {

		String emailAssetEntryAddedEnabled = portletPreferences.getValue(
			"emailAssetEntryAddedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailAssetEntryAddedEnabled)) {
			return GetterUtil.getBoolean(emailAssetEntryAddedEnabled);
		}
		else {
			return _assetPublisherPortletInstanceConfiguration.
				emailAssetEntryAddedEnabled();
		}
	}

	public static Map<Locale, String> getEmailAssetEntryAddedSubjectMap(
		PortletPreferences portletPreferences) {

		LocalizedValuesMap emailAssetEntryAddedLocalizedSubjectMap =
			_assetPublisherPortletInstanceConfiguration.
				emailAssetEntryAddedSubject();

		Map<Locale, String> emailAssetEntryAddedSubjectMap =
			LocalizationUtil.getLocalizationMap(
				portletPreferences, "emailAssetEntryAddedSubject",
				StringPool.BLANK, StringPool.BLANK,
				AssetPublisherUtil.class.getClassLoader());

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		if (Validator.isNull(
				emailAssetEntryAddedSubjectMap.get(defaultLocale))) {

			emailAssetEntryAddedSubjectMap.put(
				defaultLocale,
				emailAssetEntryAddedLocalizedSubjectMap.getDefaultValue());
		}

		return emailAssetEntryAddedSubjectMap;
	}

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$ASSET_ENTRIES$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-list-of-assets"));
		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-assets"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-assets"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-assets"));
		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		definitionTerms.put(
			"[$PORTLET_NAME$]",
			HtmlUtil.escape(
				PortalUtil.getPortletTitle(
					AssetPublisherPortletKeys.ASSET_PUBLISHER,
					themeDisplay.getLocale())));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		definitionTerms.put(
			"[$PORTLET_TITLE$]", HtmlUtil.escape(portletDisplay.getTitle()));

		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-assets"));
		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-recipient"));

		return definitionTerms;
	}

	public static String getEmailFromAddress(
		PortletPreferences portletPreferences, long companyId) {

		return PortalUtil.getEmailFromAddress(
			portletPreferences, companyId,
			_assetPublisherPortletInstanceConfiguration.emailFromAddress());
	}

	public static String getEmailFromName(
		PortletPreferences portletPreferences, long companyId) {

		return PortalUtil.getEmailFromName(
			portletPreferences, companyId,
			_assetPublisherPortletInstanceConfiguration.emailFromName());
	}

	public static long getGroupIdFromScopeId(
			String scopeId, long siteGroupId, boolean privateLayout)
		throws PortalException {

		if (scopeId.startsWith(SCOPE_ID_CHILD_GROUP_PREFIX)) {
			String scopeIdSuffix = scopeId.substring(
				SCOPE_ID_CHILD_GROUP_PREFIX.length());

			long childGroupId = GetterUtil.getLong(scopeIdSuffix);

			Group childGroup = _groupLocalService.getGroup(childGroupId);

			if (!childGroup.hasAncestor(siteGroupId)) {
				throw new PrincipalException();
			}

			return childGroupId;
		}
		else if (scopeId.startsWith(SCOPE_ID_GROUP_PREFIX)) {
			String scopeIdSuffix = scopeId.substring(
				SCOPE_ID_GROUP_PREFIX.length());

			if (scopeIdSuffix.equals(GroupConstants.DEFAULT)) {
				return siteGroupId;
			}

			long scopeGroupId = GetterUtil.getLong(scopeIdSuffix);

			Group scopeGroup = _groupLocalService.getGroup(scopeGroupId);

			return scopeGroup.getGroupId();
		}
		else if (scopeId.startsWith(SCOPE_ID_LAYOUT_UUID_PREFIX)) {
			String layoutUuid = scopeId.substring(
				SCOPE_ID_LAYOUT_UUID_PREFIX.length());

			Layout scopeIdLayout =
				_layoutLocalService.getLayoutByUuidAndGroupId(
					layoutUuid, siteGroupId, privateLayout);

			Group scopeIdGroup = _groupLocalService.checkScopeGroup(
				scopeIdLayout, PrincipalThreadLocal.getUserId());

			return scopeIdGroup.getGroupId();
		}
		else if (scopeId.startsWith(SCOPE_ID_LAYOUT_PREFIX)) {

			// Legacy portlet preferences

			String scopeIdSuffix = scopeId.substring(
				SCOPE_ID_LAYOUT_PREFIX.length());

			long scopeIdLayoutId = GetterUtil.getLong(scopeIdSuffix);

			Layout scopeIdLayout = _layoutLocalService.getLayout(
				siteGroupId, privateLayout, scopeIdLayoutId);

			Group scopeIdGroup = scopeIdLayout.getScopeGroup();

			return scopeIdGroup.getGroupId();
		}
		else if (scopeId.startsWith(SCOPE_ID_PARENT_GROUP_PREFIX)) {
			String scopeIdSuffix = scopeId.substring(
				SCOPE_ID_PARENT_GROUP_PREFIX.length());

			long parentGroupId = GetterUtil.getLong(scopeIdSuffix);

			Group parentGroup = _groupLocalService.getGroup(parentGroupId);

			if (!SitesUtil.isContentSharingWithChildrenEnabled(parentGroup)) {
				throw new PrincipalException();
			}

			Group group = _groupLocalService.getGroup(siteGroupId);

			if (!group.hasAncestor(parentGroupId)) {
				throw new PrincipalException();
			}

			return parentGroupId;
		}
		else {
			throw new IllegalArgumentException("Invalid scope ID " + scopeId);
		}
	}

	public static long[] getGroupIds(
		PortletPreferences portletPreferences, long scopeGroupId,
		Layout layout) {

		String[] scopeIds = portletPreferences.getValues(
			"scopeIds", new String[] {SCOPE_ID_GROUP_PREFIX + scopeGroupId});

		Set<Long> groupIds = new LinkedHashSet<>();

		for (String scopeId : scopeIds) {
			try {
				long groupId = getGroupIdFromScopeId(
					scopeId, scopeGroupId, layout.isPrivateLayout());

				groupIds.add(groupId);
			}
			catch (Exception e) {
				continue;
			}
		}

		return ArrayUtil.toLongArray(groupIds);
	}

	public static String getScopeId(Group group, long scopeGroupId) {
		String key = null;

		if (group.isLayout()) {
			Layout layout = _layoutLocalService.fetchLayout(group.getClassPK());

			key = SCOPE_ID_LAYOUT_UUID_PREFIX + layout.getUuid();
		}
		else if (group.isLayoutPrototype() ||
				 (group.getGroupId() == scopeGroupId)) {

			key = SCOPE_ID_GROUP_PREFIX + GroupConstants.DEFAULT;
		}
		else {
			Group scopeGroup = _groupLocalService.fetchGroup(scopeGroupId);

			if (scopeGroup.hasAncestor(group.getGroupId()) &&
				SitesUtil.isContentSharingWithChildrenEnabled(group)) {

				key = SCOPE_ID_PARENT_GROUP_PREFIX + group.getGroupId();
			}
			else if (group.hasAncestor(scopeGroup.getGroupId())) {
				key = SCOPE_ID_CHILD_GROUP_PREFIX + group.getGroupId();
			}
			else {
				key = SCOPE_ID_GROUP_PREFIX + group.getGroupId();
			}
		}

		return key;
	}

	public static long getSubscriptionClassPK(
			long ownerId, int ownerType, long plid, String portletId)
		throws PortalException {

		if (plid != LayoutConstants.DEFAULT_PLID) {
			Layout layout = _layoutLocalService.fetchLayout(plid);

			if (layout != null) {
				long userId = 0;

				if (PortletIdCodec.hasUserId(portletId)) {
					userId = PortletIdCodec.decodeUserId(portletId);
				}

				PortletPreferencesIds portletPreferencesIds =
					_portletPreferencesFactory.getPortletPreferencesIds(
						layout.getGroupId(), userId, layout, portletId, false);

				ownerId = portletPreferencesIds.getOwnerId();
				ownerType = portletPreferencesIds.getOwnerType();
				plid = portletPreferencesIds.getPlid();
				portletId = portletPreferencesIds.getPortletId();
			}
		}

		if (PortletIdCodec.hasUserId(portletId)) {
			ownerId = PortletIdCodec.decodeUserId(portletId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		com.liferay.portal.kernel.model.PortletPreferences
			portletPreferencesModel =
				_portletPreferencesLocalService.getPortletPreferences(
					ownerId, ownerType, plid, portletId);

		return portletPreferencesModel.getPortletPreferencesId();
	}

	public static long getSubscriptionClassPK(long plid, String portletId)
		throws PortalException {

		return getSubscriptionClassPK(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);
	}

	public static boolean isScopeIdSelectable(
			PermissionChecker permissionChecker, String scopeId,
			long companyGroupId, Layout layout)
		throws PortalException {

		return isScopeIdSelectable(
			permissionChecker, scopeId, companyGroupId, layout, true);
	}

	public static boolean isScopeIdSelectable(
			PermissionChecker permissionChecker, String scopeId,
			long companyGroupId, Layout layout, boolean checkPermission)
		throws PortalException {

		long groupId = getGroupIdFromScopeId(
			scopeId, layout.getGroupId(), layout.isPrivateLayout());

		if (scopeId.startsWith(SCOPE_ID_CHILD_GROUP_PREFIX)) {
			Group group = _groupLocalService.getGroup(groupId);

			if (!group.hasAncestor(layout.getGroupId())) {
				return false;
			}
		}
		else if (scopeId.startsWith(SCOPE_ID_PARENT_GROUP_PREFIX)) {
			Group siteGroup = layout.getGroup();

			if (!siteGroup.hasAncestor(groupId)) {
				return false;
			}

			Group group = _groupLocalService.getGroup(groupId);

			if (SitesUtil.isContentSharingWithChildrenEnabled(group)) {
				return true;
			}

			if (!PrefsPropsUtil.getBoolean(
					layout.getCompanyId(),
					PropsKeys.
						SITES_CONTENT_SHARING_THROUGH_ADMINISTRATORS_ENABLED)) {

				return false;
			}

			if (checkPermission) {
				return GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.UPDATE);
			}
		}
		else if ((groupId != companyGroupId) && checkPermission) {
			return GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.UPDATE);
		}

		return true;
	}

	public static boolean isSubscribed(
			long companyId, long userId, long plid, String portletId)
		throws PortalException {

		return _subscriptionLocalService.isSubscribed(
			companyId, userId,
			com.liferay.portal.kernel.model.PortletPreferences.class.getName(),
			getSubscriptionClassPK(plid, portletId));
	}

	/**
	 * @deprecated As of 1.2.0, with no direct replacement
	 */
	@Deprecated
	public static void notifySubscriber(
		long userId, PortletPreferences portletPreferences,
		List<AssetEntry> assetEntries) {
	}

	/**
	 * @deprecated As of 1.2.0
	 */
	@Deprecated
	public static void notifySubscribers(
			PortletPreferences portletPreferences, long plid, String portletId,
			List<AssetEntry> assetEntries)
		throws PortalException {
	}

	public static void processAssetEntryQuery(
			User user, PortletPreferences portletPreferences,
			AssetEntryQuery assetEntryQuery)
		throws Exception {

		for (AssetEntryQueryProcessor assetEntryQueryProcessor :
				getAssetEntryQueryProcessors()) {

			assetEntryQueryProcessor.processAssetEntryQuery(
				user, portletPreferences, assetEntryQuery);
		}
	}

	/**
	 * @deprecated As of 1.2.0, with no direct replacement
	 */
	@Deprecated
	public static void registerAssetQueryProcessor(
		String assetQueryProcessorClassName,
		AssetEntryQueryProcessor assetQueryProcessor) {
	}

	public static void removeAndStoreSelection(
			List<String> assetEntryUuids, PortletPreferences portletPreferences)
		throws Exception {

		if (assetEntryUuids.isEmpty()) {
			return;
		}

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		List<String> assetEntryXmlsList = ListUtil.fromArray(assetEntryXmls);

		Iterator<String> itr = assetEntryXmlsList.iterator();

		while (itr.hasNext()) {
			String assetEntryXml = itr.next();

			Document document = SAXReaderUtil.read(assetEntryXml);

			Element rootElement = document.getRootElement();

			String assetEntryUuid = rootElement.elementText("asset-entry-uuid");

			if (assetEntryUuids.contains(assetEntryUuid)) {
				itr.remove();
			}
		}

		portletPreferences.setValues(
			"assetEntryXml",
			assetEntryXmlsList.toArray(new String[assetEntryXmlsList.size()]));

		portletPreferences.store();
	}

	public static void subscribe(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(plid);

		PortletPermissionUtil.check(
			permissionChecker, 0, layout, portletId, ActionKeys.SUBSCRIBE,
			false, false);

		_subscriptionLocalService.addSubscription(
			permissionChecker.getUserId(), groupId,
			com.liferay.portal.kernel.model.PortletPreferences.class.getName(),
			getSubscriptionClassPK(plid, portletId));
	}

	/**
	 * @deprecated As of 1.2.0, with no direct replacement
	 */
	@Deprecated
	public static void unregisterAssetQueryProcessor(
		String assetQueryProcessorClassName) {
	}

	public static void unsubscribe(
			PermissionChecker permissionChecker, long plid, String portletId)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(plid);

		PortletPermissionUtil.check(
			permissionChecker, 0, layout, portletId, ActionKeys.SUBSCRIBE,
			false, false);

		_subscriptionLocalService.deleteSubscription(
			permissionChecker.getUserId(),
			com.liferay.portal.kernel.model.PortletPreferences.class.getName(),
			getSubscriptionClassPK(plid, portletId));
	}

	/**
	 * @deprecated As of 2.0.0, with no direct replacement
	 */
	@Deprecated
	public void checkAssetEntries() throws Exception {
	}

	protected static List<AssetEntryResult> getAssetEntryResultsByClassName(
			SearchContainer searchContainer, AssetEntryQuery assetEntryQuery,
			Layout layout, PortletPreferences portletPreferences,
			String portletName, Locale locale, TimeZone timeZone,
			long companyId, long scopeGroupId, long userId, long[] classNameIds,
			Map<String, Serializable> attributes)
		throws Exception {

		List<AssetEntryResult> assetEntryResults = new ArrayList<>();

		int end = searchContainer.getEnd();
		int start = searchContainer.getStart();

		int total = 0;

		for (long classNameId : classNameIds) {
			assetEntryQuery.setClassNameIds(new long[] {classNameId});

			BaseModelSearchResult<AssetEntry> baseModelSearchResult =
				getAssetEntries(
					assetEntryQuery, layout, portletPreferences, portletName,
					locale, timeZone, companyId, scopeGroupId, userId,
					attributes, start, end);

			int groupTotal = baseModelSearchResult.getLength();

			total += groupTotal;

			List<AssetEntry> assetEntries =
				baseModelSearchResult.getBaseModels();

			if (!assetEntries.isEmpty() && (start < groupTotal)) {
				AssetRendererFactory<?> groupAssetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassNameId(classNameId);

				String title = ResourceActionsUtil.getModelResource(
					locale, groupAssetRendererFactory.getClassName());

				assetEntryResults.add(
					new AssetEntryResult(title, assetEntries));
			}

			if (!portletName.equals(AssetPublisherPortletKeys.RECENT_CONTENT)) {
				if (groupTotal > 0) {
					if ((end > 0) && (end > groupTotal)) {
						end -= groupTotal;
					}
					else {
						end = 0;
					}

					if ((start > 0) && (start > groupTotal)) {
						start -= groupTotal;
					}
					else {
						start = 0;
					}
				}

				assetEntryQuery.setEnd(QueryUtil.ALL_POS);
				assetEntryQuery.setStart(QueryUtil.ALL_POS);
			}
		}

		searchContainer.setTotal(total);

		return assetEntryResults;
	}

	protected static List<AssetEntryResult> getAssetEntryResultsByDefault(
			SearchContainer searchContainer, AssetEntryQuery assetEntryQuery,
			Layout layout, PortletPreferences portletPreferences,
			String portletName, Locale locale, TimeZone timeZone,
			long companyId, long scopeGroupId, long userId, long[] classNameIds,
			Map<String, Serializable> attributes)
		throws Exception {

		List<AssetEntryResult> assetEntryResults = new ArrayList<>();

		int end = searchContainer.getEnd();
		int start = searchContainer.getStart();

		assetEntryQuery.setClassNameIds(classNameIds);

		BaseModelSearchResult<AssetEntry> baseModelSearchResult =
			getAssetEntries(
				assetEntryQuery, layout, portletPreferences, portletName,
				locale, timeZone, companyId, scopeGroupId, userId, attributes,
				start, end);

		int total = baseModelSearchResult.getLength();

		searchContainer.setTotal(total);

		List<AssetEntry> assetEntries = baseModelSearchResult.getBaseModels();

		if (!assetEntries.isEmpty() && (start < total)) {
			assetEntryResults.add(new AssetEntryResult(assetEntries));
		}

		return assetEntryResults;
	}

	protected static List<AssetEntryResult> getAssetEntryResultsByVocabulary(
			SearchContainer searchContainer, AssetEntryQuery assetEntryQuery,
			Layout layout, PortletPreferences portletPreferences,
			String portletName, Locale locale, TimeZone timeZone,
			long companyId, long scopeGroupId, long userId, long[] classNameIds,
			long assetVocabularyId, Map<String, Serializable> attributes)
		throws Exception {

		List<AssetEntryResult> assetEntryResults = new ArrayList<>();

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getVocabularyRootCategories(
				assetVocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		assetEntryQuery.setClassNameIds(classNameIds);

		int end = searchContainer.getEnd();
		int start = searchContainer.getStart();

		int total = 0;

		for (AssetCategory assetCategory : assetCategories) {
			long[] oldAllCategoryIds = assetEntryQuery.getAllCategoryIds();

			long[] newAllAssetCategoryIds = ArrayUtil.append(
				oldAllCategoryIds, assetCategory.getCategoryId());

			assetEntryQuery.setAllCategoryIds(newAllAssetCategoryIds);

			BaseModelSearchResult<AssetEntry> baseModelSearchResult =
				getAssetEntries(
					assetEntryQuery, layout, portletPreferences, portletName,
					locale, timeZone, companyId, scopeGroupId, userId,
					attributes, start, end);

			int groupTotal = baseModelSearchResult.getLength();

			total += groupTotal;

			List<AssetEntry> assetEntries =
				baseModelSearchResult.getBaseModels();

			if (!assetEntries.isEmpty() && (start < groupTotal)) {
				String title = assetCategory.getTitle(locale);

				assetEntryResults.add(
					new AssetEntryResult(title, assetEntries));
			}

			if (groupTotal > 0) {
				if ((end > 0) && (end > groupTotal)) {
					end -= groupTotal;
				}
				else {
					end = 0;
				}

				if ((start > 0) && (start > groupTotal)) {
					start -= groupTotal;
				}
				else {
					start = 0;
				}
			}

			assetEntryQuery.setAllCategoryIds(oldAllCategoryIds);
			assetEntryQuery.setEnd(QueryUtil.ALL_POS);
			assetEntryQuery.setStart(QueryUtil.ALL_POS);
		}

		searchContainer.setTotal(total);

		return assetEntryResults;
	}

	protected static long[] getSiteGroupIds(long[] groupIds) {
		Set<Long> siteGroupIds = new LinkedHashSet<>();

		for (long groupId : groupIds) {
			siteGroupIds.add(PortalUtil.getSiteGroupId(groupId));
		}

		return ArrayUtil.toLongArray(siteGroupIds);
	}

	protected static boolean isSearchWithIndex(
		String portletName, AssetEntryQuery assetEntryQuery) {

		if (_assetPublisherWebConfiguration.searchWithIndex() &&
			(assetEntryQuery.getLinkedAssetEntryId() == 0) &&
			!portletName.equals(
				AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) &&
			!portletName.equals(AssetPublisherPortletKeys.MOST_VIEWED_ASSETS)) {

			return true;
		}

		return false;
	}

	protected static boolean isShowAssetEntryResults(
		String portletName, AssetEntryQuery assetEntryQuery) {

		if (!portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS) ||
			(assetEntryQuery.getLinkedAssetEntryId() > 0)) {

			return true;
		}

		return false;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties)
		throws ConfigurationException {

		_instance = this;

		_assetPublisherWebConfiguration = ConfigurableUtil.createConfigurable(
			AssetPublisherWebConfiguration.class, properties);

		_assetPublisherPortletInstanceConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				AssetPublisherPortletInstanceConfiguration.class);
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor) {

		_assetEntryQueryProcessors.add(assetEntryQueryProcessor);
	}

	@Reference(unbind = "-")
	protected void setAssetEntryService(AssetEntryService assetEntryService) {
		_assetEntryService = assetEntryService;
	}

	@Reference(unbind = "-")
	protected void setAssetHelper(AssetHelper assetHelper) {
		_assetHelper = assetHelper;
	}

	@Reference(unbind = "-")
	protected void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {

		_assetTagLocalService = assetTagLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMIndexer(DDMIndexer ddmIndexer) {
		_ddmIndexer = ddmIndexer;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesFactory(
		PortletPreferencesFactory portletPreferencesFactory) {

		_portletPreferencesFactory = portletPreferencesFactory;
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	/**
	 * @deprecated As of 2.0.0, with no direct replacement
	 */
	@Deprecated
	protected void setSubscriptionLocalService(
		com.liferay.portal.kernel.service.SubscriptionLocalService
			subscriptionLocalService) {
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	/**
	 * @deprecated As of 2.0.0, with no direct replacement
	 */
	@Deprecated
	protected void setUserLocalService(UserLocalService userLocalService) {
	}

	protected void unsetAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor) {

		_assetEntryQueryProcessors.remove(assetEntryQueryProcessor);
	}

	private static List<AssetEntry> _filterAssetCategoriesAssetEntries(
		List<AssetEntry> assetEntries, long[] assetCategoryIds) {

		List<AssetEntry> filteredAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			if (ArrayUtil.containsAll(
					assetEntry.getCategoryIds(), assetCategoryIds)) {

				filteredAssetEntries.add(assetEntry);
			}
		}

		return filteredAssetEntries;
	}

	private static long[] _filterAssetCategoryIds(long[] assetCategoryIds) {
		List<Long> assetCategoryIdsList = new ArrayList<>();

		for (long assetCategoryId : assetCategoryIds) {
			AssetCategory category =
				_assetCategoryLocalService.fetchAssetCategory(assetCategoryId);

			if (category == null) {
				continue;
			}

			assetCategoryIdsList.add(assetCategoryId);
		}

		return ArrayUtil.toArray(
			assetCategoryIdsList.toArray(
				new Long[assetCategoryIdsList.size()]));
	}

	private static List<AssetEntry> _filterAssetTagNamesAssetEntries(
		List<AssetEntry> assetEntries, String[] assetTagNames) {

		List<AssetEntry> filteredAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			List<AssetTag> assetTags = assetEntry.getTags();

			String[] assetEntryAssetTagNames = new String[assetTags.size()];

			for (int i = 0; i < assetTags.size(); i++) {
				AssetTag assetTag = assetTags.get(i);

				assetEntryAssetTagNames[i] = assetTag.getName();
			}

			if (ArrayUtil.containsAll(assetEntryAssetTagNames, assetTagNames)) {
				filteredAssetEntries.add(assetEntry);
			}
		}

		return filteredAssetEntries;
	}

	private static String _getAssetEntryXml(
		String assetEntryType, String assetEntryUuid) {

		String xml = null;

		try {
			Document document = SAXReaderUtil.createDocument(StringPool.UTF8);

			Element assetEntryElement = document.addElement("asset-entry");

			Element assetEntryTypeElement = assetEntryElement.addElement(
				"asset-entry-type");

			assetEntryTypeElement.addText(assetEntryType);

			Element assetEntryUuidElement = assetEntryElement.addElement(
				"asset-entry-uuid");

			assetEntryUuidElement.addText(assetEntryUuid);

			xml = document.formattedString(StringPool.BLANK);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe);
			}
		}

		return xml;
	}

	private static void _setCategoriesAndTags(
		AssetEntryQuery assetEntryQuery, PortletPreferences portletPreferences,
		long[] scopeGroupIds, long[] overrideAllAssetCategoryIds,
		String[] overrideAllAssetTagNames) {

		long[] allAssetCategoryIds = new long[0];
		long[] anyAssetCategoryIds = new long[0];
		long[] notAllAssetCategoryIds = new long[0];
		long[] notAnyAssetCategoryIds = new long[0];

		String[] allAssetTagNames = new String[0];
		String[] anyAssetTagNames = new String[0];
		String[] notAllAssetTagNames = new String[0];
		String[] notAnyAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = portletPreferences.getValues(
				"queryValues" + i, null);

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = portletPreferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (Objects.equals(queryName, "assetCategories")) {
				long[] assetCategoryIds = GetterUtil.getLongValues(queryValues);

				if (queryContains && queryAndOperator) {
					allAssetCategoryIds = assetCategoryIds;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetCategoryIds = assetCategoryIds;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetCategoryIds = assetCategoryIds;
				}
				else {
					notAnyAssetCategoryIds = assetCategoryIds;
				}
			}
			else {
				if (queryContains && queryAndOperator) {
					allAssetTagNames = queryValues;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetTagNames = queryValues;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetTagNames = queryValues;
				}
				else {
					notAnyAssetTagNames = queryValues;
				}
			}
		}

		if (overrideAllAssetCategoryIds != null) {
			allAssetCategoryIds = overrideAllAssetCategoryIds;
		}

		allAssetCategoryIds = _filterAssetCategoryIds(allAssetCategoryIds);

		assetEntryQuery.setAllCategoryIds(allAssetCategoryIds);

		if (overrideAllAssetTagNames != null) {
			allAssetTagNames = overrideAllAssetTagNames;
		}

		long[] siteGroupIds = getSiteGroupIds(scopeGroupIds);

		for (String assetTagName : allAssetTagNames) {
			long[] allAssetTagIds = _assetTagLocalService.getTagIds(
				siteGroupIds, assetTagName);

			assetEntryQuery.addAllTagIdsArray(allAssetTagIds);
		}

		assetEntryQuery.setAnyCategoryIds(anyAssetCategoryIds);

		long[] anyAssetTagIds = _assetTagLocalService.getTagIds(
			siteGroupIds, anyAssetTagNames);

		assetEntryQuery.setAnyTagIds(anyAssetTagIds);

		assetEntryQuery.setNotAllCategoryIds(notAllAssetCategoryIds);

		for (String assetTagName : notAllAssetTagNames) {
			long[] notAllAssetTagIds = _assetTagLocalService.getTagIds(
				siteGroupIds, assetTagName);

			assetEntryQuery.addNotAllTagIdsArray(notAllAssetTagIds);
		}

		assetEntryQuery.setNotAnyCategoryIds(notAnyAssetCategoryIds);

		long[] notAnyAssetTagIds = _assetTagLocalService.getTagIds(
			siteGroupIds, notAnyAssetTagNames);

		assetEntryQuery.setNotAnyTagIds(notAnyAssetTagIds);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherUtil.class);

	private static AssetPublisherUtil _instance;

	private static AssetCategoryLocalService _assetCategoryLocalService;
	private static AssetEntryLocalService _assetEntryLocalService;
	private static AssetEntryService _assetEntryService;
	private static AssetHelper _assetHelper;
	private static AssetPublisherPortletInstanceConfiguration
		_assetPublisherPortletInstanceConfiguration;
	private static AssetPublisherWebConfiguration
		_assetPublisherWebConfiguration;
	private static AssetTagLocalService _assetTagLocalService;
	private static DDMIndexer _ddmIndexer;
	private static GroupLocalService _groupLocalService;
	private static LayoutLocalService _layoutLocalService;
	private static PortletPreferencesFactory _portletPreferencesFactory;
	private static PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private static SubscriptionLocalService _subscriptionLocalService;

	private final List<AssetEntryQueryProcessor> _assetEntryQueryProcessors =
		new CopyOnWriteArrayList<>();

}