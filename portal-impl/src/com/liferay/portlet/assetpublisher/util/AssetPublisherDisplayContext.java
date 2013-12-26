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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil;
import com.liferay.util.RSSUtil;

import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetPublisherDisplayContext {

	public AssetPublisherDisplayContext(
		HttpServletRequest request, PortletPreferences portletPreferences) {

		_request = request;
		_portletPreferences = portletPreferences;
	}

	public int getAbstractLength() {
		if (_abstractLength == null) {
			_abstractLength = GetterUtil.getInteger(
				_portletPreferences.getValue("abstractLength", null), 200);
		}

		return _abstractLength;
	}

	public long[] getAllAssetCategoryIds() throws Exception {
		if (_allAssetCategoryIds == null) {
			_allAssetCategoryIds = new long[0];

			String selectionStyle = getSelectionStyle();

			if (selectionStyle.equals("dynamic")) {
				_allAssetCategoryIds = AssetPublisherUtil.getAssetCategoryIds(
					_portletPreferences);
			}

			long assetCategoryId = ParamUtil.getLong(_request, "categoryId");

			if (assetCategoryId > 0) {
				if (selectionStyle.equals("manual")) {
					_allAssetCategoryIds = ArrayUtil.append(
						_allAssetCategoryIds, assetCategoryId);
				}
			}
		}

		return _allAssetCategoryIds;
	}

	public String[] getAllAssetTagNames() throws Exception {
		if (_allAssetTagNames == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_allAssetTagNames = new String[0];

			String selectionStyle = getSelectionStyle();

			if (selectionStyle.equals("dynamic")) {
				_allAssetTagNames = AssetPublisherUtil.getAssetTagNames(
					_portletPreferences, themeDisplay.getScopeGroupId());
			}

			String assetTagName = ParamUtil.getString(_request, "tag");

			if (Validator.isNotNull(assetTagName)) {
				if (selectionStyle.equals("manual")) {
					_allAssetTagNames = ArrayUtil.append(
						_allAssetTagNames, assetTagName);
				}
			}

			if (isMergeURLTags() || isMergeLayoutTags()) {
				_allAssetTagNames = ArrayUtil.append(
					_allAssetTagNames, getCompilerTagNames());
			}

			_allAssetTagNames = ArrayUtil.distinct(
				_allAssetTagNames, new StringComparator());
		}

		return _allAssetTagNames;
	}

	public AssetEntryQuery getAssetEntryQuery() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] groupIds = getGroupIds();

		if (!ArrayUtil.contains(groupIds, themeDisplay.getScopeGroupId())) {
			groupIds = ArrayUtil.append(
				groupIds, themeDisplay.getScopeGroupId());
		}

		AssetEntryQuery assetEntryQuery = AssetPublisherUtil.getAssetEntryQuery(
			_portletPreferences, groupIds);

		long[] classNameIds = getClassNameIds();
		long[] classTypeIds = getClassTypeIds();

		if (isSubtypeFieldsFilterEnabled() && (classNameIds.length == 1) &&
			(classTypeIds.length == 1) &&
			Validator.isNotNull(getDDMStructureFieldName()) &&
			Validator.isNotNull(getDDMStructureFieldValue())) {

			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						PortalUtil.getClassName(classNameIds[0]));

			Tuple classTypeFieldName =
				assetRendererFactory.getClassTypeFieldName(
					classTypeIds[0], getDDMStructureFieldName(),
					themeDisplay.getLocale());

			long ddmStructureId = GetterUtil.getLong(
				classTypeFieldName.getObject(3));

			assetEntryQuery.setAttribute(
				"ddmStructureFieldName",
				DDMIndexerUtil.encodeName(
					ddmStructureId, getDDMStructureFieldName(),
					themeDisplay.getLocale()));
			assetEntryQuery.setAttribute(
				"ddmStructureFieldValue", getDDMStructureFieldValue());
		}

		AssetPublisherUtil.processAssetEntryQuery(
			themeDisplay.getUser(), _portletPreferences, assetEntryQuery);

		assetEntryQuery.setAllCategoryIds(getAllAssetCategoryIds());

		if (hasPageScope(groupIds)) {
			assetEntryQuery.setAllTagIds(
				AssetTagLocalServiceUtil.getTagIds(
					groupIds, getAllAssetTagNames()));
		}
		else {
			assetEntryQuery.setAllTagIds(
				AssetTagLocalServiceUtil.getTagIds(
					getGroupIds(), getAllAssetTagNames()));
		}

		assetEntryQuery.setClassTypeIds(classTypeIds);
		assetEntryQuery.setEnablePermissions(isEnablePermissions());
		assetEntryQuery.setExcludeZeroViewCount(isExcludeZeroViewCount());

		String portletName = getPortletName();

		if (!portletName.equals(PortletKeys.RELATED_ASSETS)) {
			assetEntryQuery.setGroupIds(getGroupIds());
		}

		if (isShowOnlyLayoutAssets()) {
			assetEntryQuery.setLayout(themeDisplay.getLayout());
		}

		if (portletName.equals(PortletKeys.RELATED_ASSETS)) {
			AssetEntry layoutAssetEntry = (AssetEntry)_request.getAttribute(
				WebKeys.LAYOUT_ASSET_ENTRY);

			if (layoutAssetEntry != null) {
				assetEntryQuery.setLinkedAssetEntryId(
					layoutAssetEntry.getEntryId());
			}
		}

		assetEntryQuery.setPaginationType(getPaginationType());
		assetEntryQuery.setOrderByCol1(getOrderByColumn1());
		assetEntryQuery.setOrderByCol2(getOrderByColumn2());
		assetEntryQuery.setOrderByType1(getOrderByType1());
		assetEntryQuery.setOrderByType2(getOrderByType2());

		return assetEntryQuery;
	}

	public String getAssetLinkBehavior() {
		if (_assetLinkBehavior == null) {
			_assetLinkBehavior = GetterUtil.getString(
				_portletPreferences.getValue(
					"assetLinkBehavior", "showFullContent"));
		}

		return _assetLinkBehavior;
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

	public String[] getCompilerTagNames() {
		if (_compilerTagNames == null) {
			_compilerTagNames = new String[0];

			if (isMergeURLTags()) {
				_compilerTagNames = ParamUtil.getParameterValues(
					_request, "tags");
			}

			if (isMergeLayoutTags()) {
				Set<String> layoutTagNames = AssetUtil.getLayoutTagNames(
					_request);

				if (!layoutTagNames.isEmpty()) {
					_compilerTagNames = ArrayUtil.append(
						_compilerTagNames,
						layoutTagNames.toArray(
							new String[layoutTagNames.size()]));
				}
			}
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
		if (_delta == null) {
			_delta = GetterUtil.getInteger(
				_portletPreferences.getValue("delta", null),
				SearchContainer.DEFAULT_DELTA);

			String portletName = getPortletName();

			if (portletName.equals(PortletKeys.RECENT_CONTENT)) {
				_delta = PropsValues.RECENT_CONTENT_MAX_DISPLAY_ITEMS;
			}
		}

		return _delta;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = GetterUtil.getString(
				_portletPreferences.getValue(
					"displayStyle",
					PropsValues.ASSET_PUBLISHER_DISPLAY_STYLE_DEFAULT));
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
				_portletPreferences.getValue("orderByType1", "ASC"));
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
		}

		return _paginationType;
	}

	public Long getPortletDisplayDDMTemplateId() {
		if (_portletDisplayDDMTemplateId == null) {
			_portletDisplayDDMTemplateId =
				PortletDisplayTemplateUtil.
					getPortletDisplayTemplateDDMTemplateId(
						getDisplayStyleGroupId(), getDisplayStyle());
		}

		return _portletDisplayDDMTemplateId;
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
		if (_rssName == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			_rssName = _portletPreferences.getValue(
				"rssName", portletDisplay.getTitle());
		}

		return _rssName;
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
		if (_socialBookmarksDisplayStyle == null) {
			_socialBookmarksDisplayStyle = _portletPreferences.getValue(
				"socialBookmarksDisplayStyle", null);

			if (Validator.isNull(_socialBookmarksDisplayStyle)) {
				String[] socialBookmarksDisplayStyles = PropsUtil.getArray(
					PropsKeys.SOCIAL_BOOKMARK_DISPLAY_STYLES);

				_socialBookmarksDisplayStyle = socialBookmarksDisplayStyles[0];
			}
		}

		return _socialBookmarksDisplayStyle;
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
		if (_enablePermissions == null) {
			if (!PropsValues.ASSET_PUBLISHER_SEARCH_WITH_INDEX) {
				_enablePermissions = false;

				return _enablePermissions;
			}

			String portletName = getPortletName();

			if (portletName.equals(PortletKeys.HIGHEST_RATED_ASSETS) ||
				portletName.equals(PortletKeys.MOST_VIEWED_ASSETS)) {

				_enablePermissions = false;

				return _enablePermissions;
			}

			_enablePermissions = GetterUtil.getBoolean(
				_portletPreferences.getValue("enablePermissions", null));
		}

		return _enablePermissions;
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

	public boolean isExcludeZeroViewCount() {
		if (_excludeZeroViewCount == null) {
			_excludeZeroViewCount = GetterUtil.getBoolean(
				_portletPreferences.getValue("excludeZeroViewCount", null));
		}

		return _excludeZeroViewCount;
	}

	public boolean isMergeLayoutTags() {
		if (_mergeLayoutTags == null) {
			_mergeLayoutTags = GetterUtil.getBoolean(
				_portletPreferences.getValue("mergeLayoutTags", null), false);
		}

		return _mergeLayoutTags;
	}

	public boolean isMergeURLTags() {
		if (_mergeURLTags == null) {
			_mergeURLTags = GetterUtil.getBoolean(
				_portletPreferences.getValue("mergeUrlTags", null), true);
		}

		return _mergeURLTags;
	}

	public boolean isOpenOfficeServerEnabled() throws SystemException {
		if (_openOfficeServerEnabled == null) {
			_openOfficeServerEnabled = PrefsPropsUtil.getBoolean(
				PropsKeys.OPENOFFICE_SERVER_ENABLED,
				PropsValues.OPENOFFICE_SERVER_ENABLED);
		}

		return _openOfficeServerEnabled;
	}

	public boolean isPaginationTypeNone() {
		String paginationType = getPaginationType();

		return paginationType.equals("none");
	}

	public boolean isPaginationTypeRegular() {
		String paginationType = getPaginationType();

		return paginationType.equals("regular");
	}

	public boolean isPaginationTypeSimple() {
		String paginationType = getPaginationType();

		return paginationType.equals("simple");
	}

	public boolean isSelectionStyleDynamic() {
		String selectionStyle = getSelectionStyle();

		return selectionStyle.equals("dynamic");
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

	public boolean isSubtypeFieldsFilterEnabled() {
		if (_subtypeFieldsFilterEnabled == null) {
			_subtypeFieldsFilterEnabled = GetterUtil.getBoolean(
				_portletPreferences.getValue(
					"subtypeFieldsFilterEnabled", Boolean.FALSE.toString()));
		}

		return _subtypeFieldsFilterEnabled;
	}

	public void setSelectionStyle(String selectionStyle) {
		_selectionStyle = selectionStyle;
	}

	public void setShowContextLink(Boolean showContextLink) {
		_showContextLink = showContextLink;
	}

	protected String getPortletName() {
		PortletConfig portletConfig = (PortletConfig)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfig == null) {
			return StringPool.BLANK;
		}

		return portletConfig.getPortletName();
	}

	protected boolean hasPageScope(long[] groupIds) throws SystemException {
		for (long groupId : groupIds) {
			Group group = GroupLocalServiceUtil.fetchGroup(groupId);

			if ((group != null) && group.isLayout()) {
				return true;
			}
		}

		return false;
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

		if (isSubtypeFieldsFilterEnabled() && (classNameIds.length == 1) &&
			(classTypeIds.length == 1)) {

			_ddmStructureDisplayFieldValue = GetterUtil.getString(
				_portletPreferences.getValue(
					"ddmStructureDisplayFieldValue", StringPool.BLANK));
			_ddmStructureFieldName = GetterUtil.getString(
				_portletPreferences.getValue(
					"ddmStructureFieldName", StringPool.BLANK));
			_ddmStructureFieldValue = _portletPreferences.getValue(
				"ddmStructureFieldValue", StringPool.BLANK);

			if (Validator.isNotNull(_ddmStructureFieldName) &&
				Validator.isNotNull(_ddmStructureFieldValue)) {

				AssetRendererFactory assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassName(
							PortalUtil.getClassName(classNameIds[0]));

				Tuple classTypeFieldName =
					assetRendererFactory.getClassTypeFieldName(
						classTypeIds[0], _ddmStructureFieldName,
						themeDisplay.getLocale());

				_ddmStructureFieldLabel = (String)classTypeFieldName.getObject(
					0);
			}
		}
	}

	private Integer _abstractLength;
	private long[] _allAssetCategoryIds;
	private String[] _allAssetTagNames;
	private Boolean _anyAssetType;
	private String _assetLinkBehavior;
	private long[] _availableClassNameIds;
	private long[] _classNameIds;
	private long[] _classTypeIds;
	private String[] _compilerTagNames;
	private String _ddmStructureDisplayFieldValue;
	private String _ddmStructureFieldLabel;
	private String _ddmStructureFieldName;
	private String _ddmStructureFieldValue;
	private Integer _delta;
	private String _displayStyle;
	private Long _displayStyleGroupId;
	private Boolean _enableCommentRatings;
	private Boolean _enableComments;
	private Boolean _enableConversions;
	private Boolean _enableFlags;
	private Boolean _enablePermissions;
	private Boolean _enablePrint;
	private Boolean _enableRatings;
	private Boolean _enableRelatedAssets;
	private Boolean _enableRSS;
	private Boolean _enableSocialBookmarks;
	private Boolean _enableTagBasedNavigation;
	private Boolean _excludeZeroViewCount;
	private String[] _extensions;
	private long[] _groupIds;
	private Boolean _mergeLayoutTags;
	private Boolean _mergeURLTags;
	private String[] _metadataFields;
	private Boolean _openOfficeServerEnabled;
	private String _orderByColumn1;
	private String _orderByColumn2;
	private String _orderByType1;
	private String _orderByType2;
	private String _paginationType;
	private Long _portletDisplayDDMTemplateId;
	private PortletPreferences _portletPreferences;
	private HttpServletRequest _request;
	private Integer _rssDelta;
	private String _rssDisplayStyle;
	private String _rssFeedType;
	private String _rssName;
	private String _selectionStyle;
	private Boolean _showAddContentButton;
	private Boolean _showAssetTitle;
	private Boolean _showAvailableLocales;
	private Boolean _showContextLink;
	private Boolean _showMetadataDescriptions;
	private Boolean _showOnlyLayoutAssets;
	private String _socialBookmarksDisplayPosition;
	private String _socialBookmarksDisplayStyle;
	private Boolean _subtypeFieldsFilterEnabled;

}