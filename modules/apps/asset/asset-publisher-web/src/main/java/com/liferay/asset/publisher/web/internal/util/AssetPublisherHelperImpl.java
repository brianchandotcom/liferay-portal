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

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.util.AssetEntryResult;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.util.AssetHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration",
	immediate = true, service = AssetPublisherHelper.class
)
public class AssetPublisherHelperImpl implements AssetPublisherHelper {

	@Override
	public long[] getAssetCategoryIds(PortletPreferences portletPreferences) {
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

	@Override
	public BaseModelSearchResult<AssetEntry> getAssetEntries(
			AssetEntryQuery assetEntryQuery, Layout layout,
			PortletPreferences portletPreferences, String portletName,
			Locale locale, TimeZone timeZone, long companyId, long scopeGroupId,
			long userId, Map<String, Serializable> attributes, int start,
			int end)
		throws Exception {

		if (_isSearchWithIndex(portletName, assetEntryQuery)) {
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

	@Override
	public List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			boolean deleteMissingAssetEntries, boolean checkPermission)
		throws Exception {

		return getAssetEntries(
			portletRequest, portletPreferences, permissionChecker, groupIds,
			deleteMissingAssetEntries, checkPermission, false);
	}

	@Override
	public List<AssetEntry> getAssetEntries(
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

	@Override
	public List<AssetEntry> getAssetEntries(
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
			_removeAndStoreSelection(
				missingAssetEntryUuids, portletPreferences);

			if (!missingAssetEntryUuids.isEmpty()) {
				SessionMessages.add(
					portletRequest, "deletedMissingAssetEntries",
					missingAssetEntryUuids);
			}
		}

		return assetEntries;
	}

	@Override
	public List<AssetEntry> getAssetEntries(
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

	@Override
	public AssetEntryQuery getAssetEntryQuery(
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

	@Override
	public List<AssetEntryResult> getAssetEntryResults(
			SearchContainer searchContainer, AssetEntryQuery assetEntryQuery,
			Layout layout, PortletPreferences portletPreferences,
			String portletName, Locale locale, TimeZone timeZone,
			long companyId, long scopeGroupId, long userId, long[] classNameIds,
			Map<String, Serializable> attributes)
		throws Exception {

		if (!_isShowAssetEntryResults(portletName, assetEntryQuery)) {
			return Collections.emptyList();
		}

		long assetVocabularyId = GetterUtil.getLong(
			portletPreferences.getValue("assetVocabularyId", null));

		if (assetVocabularyId > 0) {
			return _getAssetEntryResultsByVocabulary(
				searchContainer, assetEntryQuery, layout, portletPreferences,
				portletName, locale, timeZone, companyId, scopeGroupId, userId,
				classNameIds, assetVocabularyId, attributes);
		}
		else if (assetVocabularyId <= -1) {
			return _getAssetEntryResultsByClassName(
				searchContainer, assetEntryQuery, layout, portletPreferences,
				portletName, locale, timeZone, companyId, scopeGroupId, userId,
				classNameIds, attributes);
		}

		return _getAssetEntryResultsByDefault(
			searchContainer, assetEntryQuery, layout, portletPreferences,
			portletName, locale, timeZone, companyId, scopeGroupId, userId,
			classNameIds, attributes);
	}

	@Override
	public String[] getAssetTagNames(PortletPreferences portletPreferences) {
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

	@Override
	public String getAssetViewURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, AssetEntry assetEntry) {

		return getAssetViewURL(
			liferayPortletRequest, liferayPortletResponse, assetEntry, false);
	}

	@Override
	public String getAssetViewURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, AssetEntry assetEntry,
		boolean viewInContext) {

		AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

		return getAssetViewURL(
			liferayPortletRequest, liferayPortletResponse, assetRenderer,
			assetEntry, viewInContext);
	}

	@Override
	public String getAssetViewURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		AssetRenderer<?> assetRenderer, AssetEntry assetEntry,
		boolean viewInContext) {

		PortletURL viewFullContentURL =
			liferayPortletResponse.createRenderURL();

		viewFullContentURL.setParameter("mvcPath", "/view_content.jsp");
		viewFullContentURL.setParameter(
			"assetEntryId", String.valueOf(assetEntry.getEntryId()));

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		int cur = ParamUtil.getInteger(liferayPortletRequest, "cur");
		int delta = ParamUtil.getInteger(liferayPortletRequest, "delta");
		boolean resetCur = ParamUtil.getBoolean(
			liferayPortletRequest, "resetCur");

		redirectURL.setParameter("cur", String.valueOf(cur));

		if (delta > 0) {
			redirectURL.setParameter("delta", String.valueOf(delta));
		}

		redirectURL.setParameter("resetCur", String.valueOf(resetCur));
		redirectURL.setParameter(
			"assetEntryId", String.valueOf(assetEntry.getEntryId()));

		viewFullContentURL.setParameter("redirect", redirectURL.toString());

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		viewFullContentURL.setParameter("type", assetRendererFactory.getType());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
			if (assetRenderer.getGroupId() != themeDisplay.getScopeGroupId()) {
				viewFullContentURL.setParameter(
					"groupId", String.valueOf(assetRenderer.getGroupId()));
			}

			viewFullContentURL.setParameter(
				"urlTitle", assetRenderer.getUrlTitle());
		}

		String viewURL = null;

		if (viewInContext) {
			try {
				String noSuchEntryRedirect = viewFullContentURL.toString();

				viewURL = assetRenderer.getURLViewInContext(
					liferayPortletRequest, liferayPortletResponse,
					noSuchEntryRedirect);

				if (Validator.isNotNull(viewURL) &&
					!Objects.equals(viewURL, noSuchEntryRedirect)) {

					viewURL = _http.setParameter(
						viewURL, "redirect",
						_portal.getCurrentURL(liferayPortletRequest));
				}
			}
			catch (Exception e) {
			}
		}

		if (Validator.isNull(viewURL)) {
			viewURL = viewFullContentURL.toString();
		}

		return viewURL;
	}

	@Override
	public long[] getClassNameIds(
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

		return availableClassNameIds;
	}

	@Override
	public long getGroupIdFromScopeId(
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

	@Override
	public long[] getGroupIds(
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

	@Override
	public String getScopeId(Group group, long scopeGroupId) {
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

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties)
		throws ConfigurationException {

		_assetPublisherWebConfiguration = ConfigurableUtil.createConfigurable(
			AssetPublisherWebConfiguration.class, properties);
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

	private static boolean _isShowAssetEntryResults(
		String portletName, AssetEntryQuery assetEntryQuery) {

		if (!portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS) ||
			(assetEntryQuery.getLinkedAssetEntryId() > 0)) {

			return true;
		}

		return false;
	}

	private long[] _filterAssetCategoryIds(long[] assetCategoryIds) {
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

	private List<AssetEntryResult> _getAssetEntryResultsByClassName(
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

	private List<AssetEntryResult> _getAssetEntryResultsByDefault(
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

	private List<AssetEntryResult> _getAssetEntryResultsByVocabulary(
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

	private long[] _getSiteGroupIds(long[] groupIds) {
		Set<Long> siteGroupIds = new LinkedHashSet<>();

		for (long groupId : groupIds) {
			siteGroupIds.add(_portal.getSiteGroupId(groupId));
		}

		return ArrayUtil.toLongArray(siteGroupIds);
	}

	private boolean _isSearchWithIndex(
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

	private void _removeAndStoreSelection(
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

	private void _setCategoriesAndTags(
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

		long[] siteGroupIds = _getSiteGroupIds(scopeGroupIds);

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

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetEntryService _assetEntryService;

	@Reference
	private AssetHelper _assetHelper;

	private AssetPublisherWebConfiguration _assetPublisherWebConfiguration;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}