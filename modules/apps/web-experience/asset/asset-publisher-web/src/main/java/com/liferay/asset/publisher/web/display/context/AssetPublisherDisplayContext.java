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

package com.liferay.asset.publisher.web.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.web.configuration.AssetPublisherPortletInstanceConfiguration;
import com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.constants.AssetPublisherWebKeys;
import com.liferay.asset.publisher.web.util.AssetPublisherCustomizer;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.RSSUtil;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.util.AssetUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides utility methods moved from the Asset Publisher portlet's JSP files
 * to reduce the complexity of the views.
 *
 * @author Eudaldo Alonso
 */
public class AssetPublisherDisplayContext {

	public static final String PAGINATION_TYPE_NONE = "none";

	public static final String PAGINATION_TYPE_REGULAR = "regular";

	public static final String PAGINATION_TYPE_SIMPLE = "simple";

	public static final String[] PAGINATION_TYPES =
		{PAGINATION_TYPE_NONE, PAGINATION_TYPE_REGULAR, PAGINATION_TYPE_SIMPLE};

	public AssetPublisherDisplayContext(
			AssetPublisherCustomizer assetPublisherCustomizer,
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortletPreferences portletPreferences)
		throws ConfigurationException {

		_assetPublisherCustomizer = assetPublisherCustomizer;
		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_portletPreferences = portletPreferences;

		_assetPublisherPortletInstanceConfiguration =
			(AssetPublisherPortletInstanceConfiguration)
				portletRequest.getAttribute(
					AssetPublisherWebKeys.
						ASSET_PUBLISHER_PORTLET_INSTANCE_CONFIGURATION);
		_assetPublisherWebConfiguration =
			(AssetPublisherWebConfiguration)portletRequest.getAttribute(
				AssetPublisherWebKeys.ASSET_PUBLISHER_WEB_CONFIGURATION);
		_request = PortalUtil.getHttpServletRequest(portletRequest);
	}

	/**
	 * @deprecated As of 2.0.0, replaced by {@link
	 *             #AssetPublisherDisplayContext(AssetPublisherCustomizer,
	 *             PortletRequest,PortletResponse, PortletPreferences)}
	 */
	@Deprecated
	public AssetPublisherDisplayContext(
		PortletRequest portletRequest, PortletPreferences portletPreferences) {

		throw new UnsupportedOperationException(
			"This constructor is deprecated and replaced by " +
				"#AssetPublisherDisplayContext(PortletRequest, " +
					"PortletResponse, PortletPreferences)");
	}

	public int getAbstractLength() {
		if (_abstractLength == null) {
			_abstractLength = GetterUtil.getInteger(
				_portletPreferences.getValue("abstractLength", null),
				AssetUtil.ASSET_ENTRY_ABSTRACT_LENGTH);
		}

		return _abstractLength;
	}

	public long[] getAllAssetCategoryIds() throws Exception {
		if (_allAssetCategoryIds != null) {
			return _allAssetCategoryIds;
		}

		_allAssetCategoryIds = new long[0];

		long assetCategoryId = ParamUtil.getLong(_request, "categoryId");

		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals("dynamic")) {
			_allAssetCategoryIds = AssetPublisherUtil.getAssetCategoryIds(
				_portletPreferences);
		}

		if ((assetCategoryId > 0) &&
			!ArrayUtil.contains(_allAssetCategoryIds, assetCategoryId)) {

			_allAssetCategoryIds = ArrayUtil.append(
				_allAssetCategoryIds, assetCategoryId);
		}

		return _allAssetCategoryIds;
	}

	public String[] getAllAssetTagNames() throws Exception {
		if (_allAssetTagNames != null) {
			return _allAssetTagNames;
		}

		_allAssetTagNames = new String[0];

		String assetTagName = ParamUtil.getString(_request, "tag");

		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals("dynamic")) {
			_allAssetTagNames = AssetPublisherUtil.getAssetTagNames(
				_portletPreferences);
		}

		if (Validator.isNotNull(assetTagName) &&
			!ArrayUtil.contains(_allAssetTagNames, assetTagName)) {

			_allAssetTagNames = ArrayUtil.append(
				_allAssetTagNames, assetTagName);
		}

		if (isMergeURLTags()) {
			_allAssetTagNames = ArrayUtil.append(
				_allAssetTagNames, getCompilerTagNames());
		}

		_allAssetTagNames = ArrayUtil.distinct(
			_allAssetTagNames, new StringComparator());

		return _allAssetTagNames;
	}

	public AssetEntryQuery getAssetEntryQuery() throws Exception {
		if (_assetEntryQuery != null) {
			return _assetEntryQuery;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_assetEntryQuery = AssetPublisherUtil.getAssetEntryQuery(
			_portletPreferences, getGroupIds(), getAllAssetCategoryIds(),
			getAllAssetTagNames());

		_assetEntryQuery.setClassTypeIds(getClassTypeIds());
		_assetEntryQuery.setEnablePermissions(isEnablePermissions());
		_assetEntryQuery.setExcludeZeroViewCount(isExcludeZeroViewCount());

		configureSubtypeFieldFilter(_assetEntryQuery, themeDisplay.getLocale());

		if (isShowOnlyLayoutAssets()) {
			_assetEntryQuery.setLayout(themeDisplay.getLayout());
		}

		_assetEntryQuery.setPaginationType(getPaginationType());
		_assetEntryQuery.setOrderByCol1(getOrderByColumn1());
		_assetEntryQuery.setOrderByCol2(getOrderByColumn2());
		_assetEntryQuery.setOrderByType1(getOrderByType1());
		_assetEntryQuery.setOrderByType2(getOrderByType2());

		AssetPublisherUtil.processAssetEntryQuery(
			themeDisplay.getUser(), _portletPreferences, _assetEntryQuery);

		_assetPublisherCustomizer.setAssetEntryQueryOptions(
			_assetEntryQuery, _request);

		return _assetEntryQuery;
	}

	public String getAssetLinkBehavior() {
		if (_assetLinkBehavior == null) {
			_assetLinkBehavior = GetterUtil.getString(
				_portletPreferences.getValue(
					"assetLinkBehavior", "showFullContent"));
		}

		return _assetLinkBehavior;
	}

	public AssetPublisherPortletInstanceConfiguration
		getAssetPublisherPortletInstanceConfiguration() {

		return _assetPublisherPortletInstanceConfiguration;
	}

	public Map<String, Serializable> getAttributes() {
		if (_attributes != null) {
			return _attributes;
		}

		_attributes = new HashMap<>();

		Map<String, String[]> parameters = _request.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			if (ArrayUtil.isNotEmpty(values)) {
				if (values.length == 1) {
					_attributes.put(name, values[0]);
				}
				else {
					_attributes.put(name, values);
				}
			}
		}

		return _attributes;
	}

	public long[] getAvailableClassNameIds() {
		if (_availableClassNameIds == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_availableClassNameIds =
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					themeDisplay.getCompanyId(), true);
		}

		return _availableClassNameIds;
	}

	public long[] getClassNameIds() {
		if (_classNameIds == null) {
			_classNameIds = AssetPublisherUtil.getClassNameIds(
				_portletPreferences, getAvailableClassNameIds());
		}

		return _classNameIds;
	}

	public long[] getClassTypeIds() {
		if (_classTypeIds == null) {
			_classTypeIds = GetterUtil.getLongValues(
				_portletPreferences.getValues("classTypeIds", null));
		}

		return _classTypeIds;
	}

	public Long getCompanyId() {
		if (_companyId != null) {
			return _companyId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_companyId = themeDisplay.getCompanyId();

		return _companyId;
	}

	public String[] getCompilerTagNames() {
		if (_compilerTagNames != null) {
			return _compilerTagNames;
		}

		_compilerTagNames = new String[0];

		if (isMergeURLTags()) {
			_compilerTagNames = ParamUtil.getParameterValues(_request, "tags");
		}

		return _compilerTagNames;
	}

	public String getDDMStructureDisplayFieldValue() throws Exception {
		if (_ddmStructureDisplayFieldValue == null) {
			setDDMStructure();
		}

		return _ddmStructureDisplayFieldValue;
	}

	public String getDDMStructureFieldLabel() throws Exception {
		if (_ddmStructureFieldLabel == null) {
			setDDMStructure();
		}

		return _ddmStructureFieldLabel;
	}

	public String getDDMStructureFieldName() throws Exception {
		if (_ddmStructureFieldName == null) {
			setDDMStructure();
		}

		return _ddmStructureFieldName;
	}

	public String getDDMStructureFieldValue() throws Exception {
		if (_ddmStructureFieldValue == null) {
			setDDMStructure();
		}

		return _ddmStructureFieldValue;
	}

	public Integer getDelta() {
		return _assetPublisherCustomizer.getDelta(_request);
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = GetterUtil.getString(
				_portletPreferences.getValue(
					"displayStyle",
					_assetPublisherPortletInstanceConfiguration.
						defaultDisplayStyle()));
		}

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = GetterUtil.getLong(
				_portletPreferences.getValue("displayStyleGroupId", null),
				themeDisplay.getScopeGroupId());
		}

		return _displayStyleGroupId;
	}

	public String[] getExtensions() {
		if (_extensions == null) {
			_extensions = _portletPreferences.getValues(
				"extensions", new String[0]);
		}

		return _extensions;
	}

	public String[] getExtensions(AssetRenderer<?> assetRenderer) {
		final String[] supportedConversions =
			assetRenderer.getSupportedConversions();

		if (supportedConversions == null) {
			return getExtensions();
		}

		return ArrayUtil.filter(
			getExtensions(),
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String extension) {
					return ArrayUtil.contains(supportedConversions, extension);
				}

			});
	}

	public long[] getGroupIds() {
		if (_groupIds == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_groupIds = AssetPublisherUtil.getGroupIds(
				_portletPreferences, themeDisplay.getScopeGroupId(),
				themeDisplay.getLayout());
		}

		return _groupIds;
	}

	public Layout getLayout() {
		if (_layout != null) {
			return _layout;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_layout = themeDisplay.getLayout();

		return _layout;
	}

	public Locale getLocale() {
		if (_locale != null) {
			return _locale;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_locale = themeDisplay.getLocale();

		return _locale;
	}

	public String[] getMetadataFields() {
		if (_metadataFields == null) {
			_metadataFields = StringUtil.split(
				_portletPreferences.getValue(
					"metadataFields", StringPool.BLANK));
		}

		return _metadataFields;
	}

	public String getOrderByColumn1() {
		if (_orderByColumn1 == null) {
			_orderByColumn1 = GetterUtil.getString(
				_portletPreferences.getValue("orderByColumn1", "modifiedDate"));
		}

		return _orderByColumn1;
	}

	public String getOrderByColumn2() {
		if (_orderByColumn2 == null) {
			_orderByColumn2 = GetterUtil.getString(
				_portletPreferences.getValue("orderByColumn2", "title"));
		}

		return _orderByColumn2;
	}

	public String getOrderByType1() {
		if (_orderByType1 == null) {
			_orderByType1 = GetterUtil.getString(
				_portletPreferences.getValue("orderByType1", "DESC"));
		}

		return _orderByType1;
	}

	public String getOrderByType2() {
		if (_orderByType2 == null) {
			_orderByType2 = GetterUtil.getString(
				_portletPreferences.getValue("orderByType2", "ASC"));
		}

		return _orderByType2;
	}

	public String getPaginationType() {
		if (_paginationType == null) {
			_paginationType = GetterUtil.getString(
				_portletPreferences.getValue("paginationType", "none"));

			if (!ArrayUtil.contains(PAGINATION_TYPES, _paginationType)) {
				_paginationType = PAGINATION_TYPE_NONE;
			}
		}

		return _paginationType;
	}

	public String getPortletName() {
		PortletConfig portletConfig = (PortletConfig)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfig == null) {
			return StringPool.BLANK;
		}

		return portletConfig.getPortletName();
	}

	public String getPortletResource() {
		if (_portletResource == null) {
			_portletResource = ParamUtil.getString(_request, "portletResource");
		}

		return _portletResource;
	}

	public long[] getReferencedModelsGroupIds() throws PortalException {

		// Referenced models are asset subtypes, tags or categories that
		// are used to filter assets and can belong to a different scope of
		// the asset they are associated to

		if (_referencedModelsGroupIds == null) {
			_referencedModelsGroupIds =
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					getGroupIds(), true);
		}

		return _referencedModelsGroupIds;
	}

	public String getRootPortletId() {
		if (_rootPortletId == null) {
			_rootPortletId = PortletIdCodec.decodePortletName(
				getPortletResource());
		}

		return _rootPortletId;
	}

	public int getRSSDelta() {
		if (_rssDelta == null) {
			_rssDelta = GetterUtil.getInteger(
				_portletPreferences.getValue("rssDelta", StringPool.BLANK),
				SearchContainer.DEFAULT_DELTA);
		}

		return _rssDelta;
	}

	public String getRSSDisplayStyle() {
		if (_rssDisplayStyle == null) {
			_rssDisplayStyle = _portletPreferences.getValue(
				"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_ABSTRACT);
		}

		return _rssDisplayStyle;
	}

	public String getRSSFeedType() {
		if (_rssFeedType == null) {
			_rssFeedType = _portletPreferences.getValue(
				"rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
		}

		return _rssFeedType;
	}

	public String getRSSName() {
		if (_rssName != null) {
			return _rssName;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_rssName = _portletPreferences.getValue(
			"rssName", portletDisplay.getTitle());

		return _rssName;
	}

	public Map<Long, Map<String, PortletURL>> getScopeAddPortletURLs(int max)
		throws Exception {

		long[] groupIds = getGroupIds();

		if (groupIds.length == 0) {
			return Collections.emptyMap();
		}

		Map<Long, Map<String, PortletURL>> scopeAddPortletURLs = new HashMap();

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_portletResponse);

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectURL.setParameter(
			"hideDefaultSuccessMessage", Boolean.TRUE.toString());
		redirectURL.setParameter("mvcPath", "/add_asset_redirect.jsp");

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(_portletRequest);

		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		redirectURL.setParameter("redirect", currentURLObj.toString());

		redirectURL.setWindowState(LiferayWindowState.POP_UP);

		String redirect = redirectURL.toString();

		for (long groupId : groupIds) {
			Map<String, PortletURL> addPortletURLs =
				AssetUtil.getAddPortletURLs(
					liferayPortletRequest, liferayPortletResponse, groupId,
					getClassNameIds(), getClassTypeIds(),
					getAllAssetCategoryIds(), getAllAssetTagNames(), redirect);

			if (MapUtil.isNotEmpty(addPortletURLs)) {
				scopeAddPortletURLs.put(groupId, addPortletURLs);
			}

			if (scopeAddPortletURLs.size() > max) {
				break;
			}
		}

		return scopeAddPortletURLs;
	}

	public Long getScopeGroupId() {
		if (_scopeGroupId != null) {
			return _scopeGroupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_scopeGroupId = themeDisplay.getScopeGroupId();

		return _scopeGroupId;
	}

	public String getSelectionStyle() {
		if (_selectionStyle == null) {
			_selectionStyle = GetterUtil.getString(
				_portletPreferences.getValue("selectionStyle", null),
				"dynamic");
		}

		return _selectionStyle;
	}

	public String getSocialBookmarksDisplayPosition() {
		if (_socialBookmarksDisplayPosition == null) {
			_socialBookmarksDisplayPosition = _portletPreferences.getValue(
				"socialBookmarksDisplayPosition", "bottom");
		}

		return _socialBookmarksDisplayPosition;
	}

	public String getSocialBookmarksDisplayStyle() {
		if (_socialBookmarksDisplayStyle != null) {
			return _socialBookmarksDisplayStyle;
		}

		_socialBookmarksDisplayStyle = _portletPreferences.getValue(
			"socialBookmarksDisplayStyle", null);

		if (Validator.isNull(_socialBookmarksDisplayStyle)) {
			String[] socialBookmarksDisplayStyles = PropsUtil.getArray(
				PropsKeys.SOCIAL_BOOKMARK_DISPLAY_STYLES);

			_socialBookmarksDisplayStyle = socialBookmarksDisplayStyles[0];
		}

		return _socialBookmarksDisplayStyle;
	}

	public TimeZone getTimeZone() {
		if (_timeZone != null) {
			return _timeZone;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_timeZone = themeDisplay.getTimeZone();

		return _timeZone;
	}

	public Long getUserId() {
		if (_userId != null) {
			return _userId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_userId = themeDisplay.getUserId();

		return _userId;
	}

	public AssetEntry incrementViewCounter(AssetEntry assetEntry)
		throws PortalException {

		// Dynamically created asset entries are never persisted so incrementing
		// the view counter breaks

		if ((assetEntry == null) || assetEntry.isNew() ||
			!assetEntry.isVisible() || !isEnableViewCountIncrement()) {

			return assetEntry;
		}

		if (isEnablePermissions()) {
			return AssetEntryServiceUtil.incrementViewCounter(
				assetEntry.getClassName(), assetEntry.getClassPK());
		}
		else {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			return AssetEntryLocalServiceUtil.incrementViewCounter(
				themeDisplay.getUserId(), assetEntry.getClassName(),
				assetEntry.getClassPK());
		}
	}

	public Boolean isAnyAssetType() {
		if (_anyAssetType == null) {
			_anyAssetType = GetterUtil.getBoolean(
				_portletPreferences.getValue("anyAssetType", null), true);
		}

		return _anyAssetType;
	}

	public boolean isAssetLinkBehaviorShowFullContent() {
		String assetLinkBehavior = getAssetLinkBehavior();

		return assetLinkBehavior.equals("showFullContent");
	}

	public boolean isAssetLinkBehaviorViewInPortlet() {
		String assetLinkBehavior = getAssetLinkBehavior();

		return assetLinkBehavior.equals("viewInPortlet");
	}

	public boolean isDefaultAssetPublisher() {
		if (_defaultAssetPublisher != null) {
			return _defaultAssetPublisher;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_defaultAssetPublisher = AssetUtil.isDefaultAssetPublisher(
			themeDisplay.getLayout(), portletDisplay.getId(),
			getPortletResource());

		return _defaultAssetPublisher;
	}

	public boolean isEnableCommentRatings() {
		if (_enableCommentRatings == null) {
			_enableCommentRatings = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableCommentRatings", null));
		}

		return _enableCommentRatings;
	}

	public boolean isEnableComments() {
		if (_enableComments == null) {
			_enableComments = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableComments", null));
		}

		return _enableComments;
	}

	public Boolean isEnableConversions() throws Exception {
		if (_enableConversions == null) {
			_enableConversions =
				isOpenOfficeServerEnabled() &&
				ArrayUtil.isNotEmpty(getExtensions());
		}

		return _enableConversions;
	}

	public boolean isEnableFlags() {
		if (_enableFlags == null) {
			_enableFlags = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableFlags", null));
		}

		return _enableFlags;
	}

	public Boolean isEnablePermissions() {
		return _assetPublisherCustomizer.isEnablePermissions(_request);
	}

	public boolean isEnablePrint() {
		if (_enablePrint == null) {
			_enablePrint = GetterUtil.getBoolean(
				_portletPreferences.getValue("enablePrint", null));
		}

		return _enablePrint;
	}

	public boolean isEnableRatings() {
		if (_enableRatings == null) {
			_enableRatings = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableRatings", null));
		}

		return _enableRatings;
	}

	public boolean isEnableRelatedAssets() {
		if (_enableRelatedAssets == null) {
			_enableRelatedAssets = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableRelatedAssets", null),
				true);
		}

		return _enableRelatedAssets;
	}

	public boolean isEnableRSS() {
		if (_enableRSS == null) {
			_enableRSS = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableRss", null));
		}

		return _enableRSS;
	}

	public boolean isEnableSetAsDefaultAssetPublisher() {
		String rootPortletId = getRootPortletId();

		if (rootPortletId.equals(AssetPublisherPortletKeys.ASSET_PUBLISHER)) {
			return true;
		}

		return false;
	}

	public boolean isEnableSocialBookmarks() {
		if (_enableSocialBookmarks == null) {
			_enableSocialBookmarks = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableSocialBookmarks", null),
				true);
		}

		return _enableSocialBookmarks;
	}

	public boolean isEnableTagBasedNavigation() {
		if (_enableTagBasedNavigation == null) {
			_enableTagBasedNavigation = GetterUtil.getBoolean(
				_portletPreferences.getValue("enableTagBasedNavigation", null));
		}

		return _enableTagBasedNavigation;
	}

	public boolean isEnableViewCountIncrement() {
		if (_enableViewCountIncrement != null) {
			return _enableViewCountIncrement;
		}

		_enableViewCountIncrement = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableViewCountIncrement", null),
			PropsValues.ASSET_ENTRY_BUFFERED_INCREMENT_ENABLED);

		return _enableViewCountIncrement;
	}

	public boolean isExcludeZeroViewCount() {
		if (_excludeZeroViewCount == null) {
			_excludeZeroViewCount = GetterUtil.getBoolean(
				_portletPreferences.getValue("excludeZeroViewCount", null));
		}

		return _excludeZeroViewCount;
	}

	public boolean isMergeURLTags() {
		if (_mergeURLTags == null) {
			_mergeURLTags = GetterUtil.getBoolean(
				_portletPreferences.getValue("mergeUrlTags", null), true);
		}

		return _mergeURLTags;
	}

	public boolean isOpenOfficeServerEnabled() {
		return DocumentConversionUtil.isEnabled();
	}

	public boolean isOrderingAndGroupingEnabled() {
		return _assetPublisherCustomizer.isOrderingAndGroupingEnabled(_request);
	}

	public boolean isOrderingByTitleEnabled() {
		return _assetPublisherCustomizer.isOrderingByTitleEnabled(_request);
	}

	public boolean isPaginationTypeNone() {
		String paginationType = getPaginationType();

		return paginationType.equals(PAGINATION_TYPE_NONE);
	}

	public boolean isPaginationTypeSelected(String paginationType) {
		String curPaginationType = getPaginationType();

		return curPaginationType.equals(paginationType);
	}

	public boolean isSelectionStyleDynamic() {
		String selectionStyle = getSelectionStyle();

		return selectionStyle.equals("dynamic");
	}

	public boolean isSelectionStyleEnabled() {
		return _assetPublisherCustomizer.isSelectionStyleEnabled(_request);
	}

	public boolean isSelectionStyleManual() {
		String selectionStyle = getSelectionStyle();

		return selectionStyle.equals("manual");
	}

	public boolean isShowAddContentButton() {
		if (_showAddContentButton == null) {
			_showAddContentButton = GetterUtil.getBoolean(
				_portletPreferences.getValue("showAddContentButton", null),
				true);
		}

		return _showAddContentButton;
	}

	public Boolean isShowAssetTitle() {
		if (_showAssetTitle == null) {
			_showAssetTitle = GetterUtil.getBoolean(
				_portletPreferences.getValue("showAssetTitle", null), true);
		}

		return _showAssetTitle;
	}

	public Boolean isShowAvailableLocales() {
		if (_showAvailableLocales == null) {
			_showAvailableLocales = GetterUtil.getBoolean(
				_portletPreferences.getValue("showAvailableLocales", null));
		}

		return _showAvailableLocales;
	}

	public Boolean isShowContextLink() {
		if (_showContextLink == null) {
			_showContextLink = GetterUtil.getBoolean(
				_portletPreferences.getValue("showContextLink", null), true);
		}

		return _showContextLink;
	}

	public Boolean isShowContextLink(long groupId, String portletId)
		throws PortalException {

		if (_showContextLink == null) {
			_showContextLink = isShowContextLink();

			if (_showContextLink) {
				if (PortalUtil.getPlidFromPortletId(groupId, portletId) == 0) {
					_showContextLink = false;
				}
			}
		}

		return _showContextLink;
	}

	public boolean isShowEnableAddContentButton() {
		return _assetPublisherCustomizer.isShowEnableAddContentButton(_request);
	}

	public Boolean isShowEnablePermissions() {
		if (_assetPublisherWebConfiguration.searchWithIndex()) {
			return false;
		}

		return _assetPublisherWebConfiguration.permissionCheckingConfigurable();
	}

	public boolean isShowEnableRelatedAssets() {
		return _assetPublisherCustomizer.isShowEnableRelatedAssets(_request);
	}

	public boolean isShowExtraInfo() {
		if (_showExtraInfo == null) {
			_showExtraInfo = GetterUtil.getBoolean(
				_portletPreferences.getValue("showExtraInfo", null), true);
		}

		return _showExtraInfo;
	}

	public boolean isShowMetadataDescriptions() {
		if (_showMetadataDescriptions == null) {
			_showMetadataDescriptions = GetterUtil.getBoolean(
				_portletPreferences.getValue("showMetadataDescriptions", null),
				true);
		}

		return _showMetadataDescriptions;
	}

	public boolean isShowOnlyLayoutAssets() {
		if (_showOnlyLayoutAssets == null) {
			_showOnlyLayoutAssets = GetterUtil.getBoolean(
				_portletPreferences.getValue("showOnlyLayoutAssets", null));
		}

		return _showOnlyLayoutAssets;
	}

	public boolean isShowScopeSelector() {
		String rootPortletId = getRootPortletId();

		if (rootPortletId.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {
			return false;
		}

		return true;
	}

	public boolean isShowSubtypeFieldsFilter() {
		return _assetPublisherCustomizer.isShowSubtypeFieldsFilter(_request);
	}

	public boolean isSubtypeFieldsFilterEnabled() {
		if (_subtypeFieldsFilterEnabled == null) {
			_subtypeFieldsFilterEnabled = GetterUtil.getBoolean(
				_portletPreferences.getValue(
					"subtypeFieldsFilterEnabled", Boolean.FALSE.toString()));
		}

		return _subtypeFieldsFilterEnabled;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setLayoutAssetEntry(AssetEntry assetEntry)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String defaultAssetPublisherPortletId =
			AssetUtil.getDefaultAssetPublisherId(themeDisplay.getLayout());

		if (isDefaultAssetPublisher() ||
			Validator.isNull(defaultAssetPublisherPortletId) ||
			!PortletPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				defaultAssetPublisherPortletId, ActionKeys.VIEW)) {

			_request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);
		}
	}

	public void setSelectionStyle(String selectionStyle) {
		_selectionStyle = selectionStyle;
	}

	public void setShowContextLink(Boolean showContextLink) {
		_showContextLink = showContextLink;
	}

	protected void configureSubtypeFieldFilter(
			AssetEntryQuery assetEntryQuery, Locale locale)
		throws Exception {

		long[] classNameIds = getClassNameIds();
		long[] classTypeIds = getClassTypeIds();

		if (!isSubtypeFieldsFilterEnabled() || (classNameIds.length != 1) ||
			(classTypeIds.length != 1) ||
			Validator.isNull(getDDMStructureFieldName()) ||
			Validator.isNull(getDDMStructureFieldValue())) {

			return;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameIds[0]);

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(
			classTypeIds[0], locale);

		ClassTypeField classTypeField = classType.getClassTypeField(
			getDDMStructureFieldName());

		assetEntryQuery.setAttribute(
			"ddmStructureFieldName",
			AssetPublisherUtil.encodeName(
				classTypeField.getClassTypeId(), getDDMStructureFieldName(),
				locale));

		assetEntryQuery.setAttribute(
			"ddmStructureFieldValue", getDDMStructureFieldValue());
	}

	protected void setDDMStructure() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_ddmStructureDisplayFieldValue = StringPool.BLANK;
		_ddmStructureFieldLabel = StringPool.BLANK;
		_ddmStructureFieldName = StringPool.BLANK;
		_ddmStructureFieldValue = null;

		long[] classNameIds = getClassNameIds();
		long[] classTypeIds = getClassTypeIds();

		if (!isSubtypeFieldsFilterEnabled() || (classNameIds.length != 1) ||
			(classTypeIds.length != 1)) {

			return;
		}

		_ddmStructureDisplayFieldValue = ParamUtil.getString(
			_request, "ddmStructureDisplayFieldValue",
			_portletPreferences.getValue(
				"ddmStructureDisplayFieldValue", StringPool.BLANK));
		_ddmStructureFieldName = ParamUtil.getString(
			_request, "ddmStructureFieldName",
			_portletPreferences.getValue(
				"ddmStructureFieldName", StringPool.BLANK));
		_ddmStructureFieldValue = ParamUtil.getString(
			_request, "ddmStructureFieldValue",
			_portletPreferences.getValue(
				"ddmStructureFieldValue", StringPool.BLANK));

		if (Validator.isNotNull(_ddmStructureFieldName) &&
			Validator.isNotNull(_ddmStructureFieldValue)) {

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(classNameIds[0]);

			ClassTypeReader classTypeReader =
				assetRendererFactory.getClassTypeReader();

			ClassType classType = classTypeReader.getClassType(
				classTypeIds[0], themeDisplay.getLocale());

			ClassTypeField classTypeField = classType.getClassTypeField(
				_ddmStructureFieldName);

			_ddmStructureFieldLabel = classTypeField.getLabel();
		}
	}

	private Integer _abstractLength;
	private long[] _allAssetCategoryIds;
	private String[] _allAssetTagNames;
	private Boolean _anyAssetType;
	private AssetEntryQuery _assetEntryQuery;
	private String _assetLinkBehavior;
	private final AssetPublisherCustomizer _assetPublisherCustomizer;
	private final AssetPublisherPortletInstanceConfiguration
		_assetPublisherPortletInstanceConfiguration;
	private final AssetPublisherWebConfiguration
		_assetPublisherWebConfiguration;
	private Map<String, Serializable> _attributes;
	private long[] _availableClassNameIds;
	private long[] _classNameIds;
	private long[] _classTypeIds;
	private Long _companyId;
	private String[] _compilerTagNames;
	private String _ddmStructureDisplayFieldValue;
	private String _ddmStructureFieldLabel;
	private String _ddmStructureFieldName;
	private String _ddmStructureFieldValue;
	private Boolean _defaultAssetPublisher;
	private String _displayStyle;
	private Long _displayStyleGroupId;
	private Boolean _enableCommentRatings;
	private Boolean _enableComments;
	private Boolean _enableConversions;
	private Boolean _enableFlags;
	private Boolean _enablePrint;
	private Boolean _enableRatings;
	private Boolean _enableRelatedAssets;
	private Boolean _enableRSS;
	private Boolean _enableSocialBookmarks;
	private Boolean _enableTagBasedNavigation;
	private Boolean _enableViewCountIncrement;
	private Boolean _excludeZeroViewCount;
	private String[] _extensions;
	private long[] _groupIds;
	private Layout _layout;
	private Locale _locale;
	private Boolean _mergeURLTags;
	private String[] _metadataFields;
	private String _orderByColumn1;
	private String _orderByColumn2;
	private String _orderByType1;
	private String _orderByType2;
	private String _paginationType;
	private final PortletPreferences _portletPreferences;
	private final PortletRequest _portletRequest;
	private String _portletResource;
	private final PortletResponse _portletResponse;
	private long[] _referencedModelsGroupIds;
	private final HttpServletRequest _request;
	private String _rootPortletId;
	private Integer _rssDelta;
	private String _rssDisplayStyle;
	private String _rssFeedType;
	private String _rssName;
	private Long _scopeGroupId;
	private String _selectionStyle;
	private Boolean _showAddContentButton;
	private Boolean _showAssetTitle;
	private Boolean _showAvailableLocales;
	private Boolean _showContextLink;
	private Boolean _showExtraInfo;
	private Boolean _showMetadataDescriptions;
	private Boolean _showOnlyLayoutAssets;
	private String _socialBookmarksDisplayPosition;
	private String _socialBookmarksDisplayStyle;
	private Boolean _subtypeFieldsFilterEnabled;
	private TimeZone _timeZone;
	private Long _userId;

}