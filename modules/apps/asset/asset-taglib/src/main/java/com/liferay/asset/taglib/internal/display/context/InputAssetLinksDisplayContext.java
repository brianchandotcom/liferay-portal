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

package com.liferay.asset.taglib.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.asset.util.comparator.AssetRendererFactoryTypeNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author José Manuel Navarro
 */
public class InputAssetLinksDisplayContext {

	public InputAssetLinksDisplayContext(PageContext pageContext) {
		_pageContext = pageContext;

		_httpServletRequest = (HttpServletRequest)pageContext.getRequest();

		_assetEntryId = GetterUtil.getLong(
			(String)_httpServletRequest.getAttribute(
				"liferay-asset:input-asset-links:assetEntryId"));
		_className = GetterUtil.getString(
			_httpServletRequest.getAttribute(
				"liferay-asset:input-asset-links:className"));
		_portletRequest = (PortletRequest)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public AssetEntry getAssetLinkEntry(AssetLink assetLink)
		throws PortalException {

		if ((_assetEntryId > 0) || (assetLink.getEntryId1() == _assetEntryId)) {
			return AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId2());
		}

		return AssetEntryLocalServiceUtil.getEntry(assetLink.getEntryId1());
	}

	public List<AssetLink> getAssetLinks() throws PortalException {
		if (_assetLinks == null) {
			_assetLinks = _createAssetLinks();
		}

		return _assetLinks;
	}

	public int getAssetLinksCount() throws PortalException {
		List<AssetLink> assetLinks = getAssetLinks();

		return assetLinks.size();
	}

	public List<AssetRendererFactory<?>> getAssetRendererFactories() {
		List<AssetRendererFactory<?>> assetRendererFactories =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				_themeDisplay.getCompanyId());

		assetRendererFactories = ListUtil.filter(
			assetRendererFactories,
			assetRendererFactory -> {
				if (assetRendererFactory.isLinkable() &&
					assetRendererFactory.isSelectable()) {

					return true;
				}

				return false;
			});

		return ListUtil.sort(
			assetRendererFactories,
			new AssetRendererFactoryTypeNameComparator(
				_themeDisplay.getLocale()));
	}

	public String getAssetType(AssetEntry entry) {
		AssetRendererFactory<?> assetRendererFactory =
			entry.getAssetRendererFactory();

		String assetType = assetRendererFactory.getTypeName(
			_themeDisplay.getLocale());

		if (!assetRendererFactory.isSupportsClassTypes()) {
			return assetType;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		try {
			ClassType classType = classTypeReader.getClassType(
				entry.getClassTypeId(), _themeDisplay.getLocale());

			assetType = classType.getName();
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get asset type for class type primary key " +
					entry.getClassTypeId(),
				portalException);
		}

		return assetType;
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = _randomNamespace + "selectAsset";

		return _eventName;
	}

	public String getGroupDescriptiveName(AssetEntry assetEntry)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(assetEntry.getGroupId());

		return group.getDescriptiveName(_themeDisplay.getLocale());
	}

	public String getRandomNamespace() {
		if (_randomNamespace != null) {
			return _randomNamespace;
		}

		String randomKey = PortalUtil.generateRandomKey(
			_httpServletRequest, "taglib_asset_input_asset_links_page");

		_randomNamespace = randomKey + StringPool.UNDERLINE;

		return _randomNamespace;
	}

	public List<Map<String, Object>> getSelectorEntries() throws Exception {
		List<Map<String, Object>> selectorEntries = new ArrayList<>();

		AssetRendererFactory baseAssetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_className);

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		boolean baseAssetStaged = stagingGroupHelper.isStagedPortlet(
			_themeDisplay.getScopeGroupId(),
			baseAssetRendererFactory.getPortletId());

		for (AssetRendererFactory<?> assetRendererFactory :
				getAssetRendererFactories()) {

			boolean assetStaged = stagingGroupHelper.isStagedPortlet(
				_themeDisplay.getScopeGroupId(),
				assetRendererFactory.getPortletId());

			if (!baseAssetStaged && assetStaged) {
				continue;
			}

			if (assetRendererFactory.isSupportsClassTypes()) {
				selectorEntries.addAll(
					_getSelectorEntries(assetRendererFactory));
			}
			else {
				Map<String, Object> selectorEntry =
					HashMapBuilder.<String, Object>put(
						"data", _getSelectorEntryData(assetRendererFactory)
					).put(
						"iconCssClass",
						_getSelectorEntryIconCssClass(assetRendererFactory)
					).put(
						"id", _getSelectorEntryId(assetRendererFactory)
					).put(
						"message",
						_getSelectorEntryMessage(assetRendererFactory)
					).build();

				selectorEntries.add(selectorEntry);
			}
		}

		return selectorEntries;
	}

	private List<AssetLink> _createAssetLinks() throws PortalException {
		List<AssetLink> assetLinks = new ArrayList<>();

		String assetLinksSearchContainerPrimaryKeys = ParamUtil.getString(
			_httpServletRequest, "assetLinksSearchContainerPrimaryKeys");

		if (Validator.isNull(assetLinksSearchContainerPrimaryKeys) &&
			SessionErrors.isEmpty(_portletRequest) && (_assetEntryId > 0)) {

			List<AssetLink> directAssetLinks =
				AssetLinkLocalServiceUtil.getDirectLinks(_assetEntryId, false);

			for (AssetLink assetLink : directAssetLinks) {
				AssetEntry assetLinkEntry = getAssetLinkEntry(assetLink);

				AssetRendererFactory<?> assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassName(
							assetLinkEntry.getClassName());

				if (assetRendererFactory.isActive(
						_themeDisplay.getCompanyId())) {

					assetLinks.add(assetLink);
				}
			}
		}
		else {
			String[] assetEntriesPrimaryKeys = StringUtil.split(
				assetLinksSearchContainerPrimaryKeys);

			for (String assetEntryPrimaryKey : assetEntriesPrimaryKeys) {
				long assetEntryPrimaryKeyLong = GetterUtil.getLong(
					assetEntryPrimaryKey);

				AssetEntry assetEntry = AssetEntryServiceUtil.getEntry(
					assetEntryPrimaryKeyLong);

				AssetLink assetLink = AssetLinkLocalServiceUtil.createAssetLink(
					0);

				if (_assetEntryId > 0) {
					assetLink.setEntryId1(_assetEntryId);
				}
				else {
					assetLink.setEntryId1(0);
				}

				assetLink.setEntryId2(assetEntry.getEntryId());

				assetLinks.add(assetLink);
			}
		}

		return assetLinks;
	}

	private long _getAssetBrowserGroupId(
		AssetRendererFactory<?> assetRendererFactory) {

		Group scopeGroup = _themeDisplay.getScopeGroup();

		long groupId = scopeGroup.getGroupId();

		if (_isStagedLocally() && scopeGroup.isStagingGroup()) {
			boolean stagedReferencePortlet = scopeGroup.isStagedPortlet(
				assetRendererFactory.getPortletId());

			if (_isStagedReferrerPortlet() && !stagedReferencePortlet) {
				groupId = scopeGroup.getLiveGroupId();
			}
		}

		return groupId;
	}

	private PortletURL _getAssetBrowserPortletURL(
			AssetRendererFactory<?> assetRendererFactory)
		throws Exception {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			_httpServletRequest, assetRendererFactory.getClassName(),
			PortletProvider.Action.BROWSE);

		if (portletURL == null) {
			return null;
		}

		long groupId = _getAssetBrowserGroupId(assetRendererFactory);

		portletURL.setParameter("groupId", String.valueOf(groupId));

		portletURL.setParameter("selectedGroupId", String.valueOf(groupId));

		if (_assetEntryId > 0) {
			portletURL.setParameter(
				"refererAssetEntryId", String.valueOf(_assetEntryId));
		}

		portletURL.setParameter(
			"typeSelection", assetRendererFactory.getClassName());
		portletURL.setParameter("eventName", getEventName());
		portletURL.setParameter(
			"multipleSelection", String.valueOf(Boolean.TRUE));
		portletURL.setParameter("showScheduled", String.valueOf(Boolean.TRUE));
		portletURL.setParameter(
			"showNonindexable", String.valueOf(Boolean.TRUE));
		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL;
	}

	private List<Map<String, Object>> _getSelectorEntries(
			AssetRendererFactory<?> assetRendererFactory)
		throws Exception {

		long groupId = _getAssetBrowserGroupId(assetRendererFactory);

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
			_themeDisplay.getLocale());

		if (classTypes.isEmpty()) {
			return Collections.emptyList();
		}

		List<Map<String, Object>> selectorEntries = new ArrayList<>();

		for (ClassType classType : classTypes) {
			Map<String, Object> selectorEntry =
				HashMapBuilder.<String, Object>put(
					"data",
					_getSelectorEntryData(assetRendererFactory, classType)
				).put(
					"iconCssClass",
					_getSelectorEntryIconCssClass(assetRendererFactory)
				).put(
					"id", _getSelectorEntryId(assetRendererFactory, classType)
				).put(
					"message", _getSelectorEntryMessage(classType)
				).build();

			selectorEntries.add(selectorEntry);
		}

		return selectorEntries;
	}

	private Map<String, Object> _getSelectorEntryData(
			AssetRendererFactory<?> assetRendererFactory)
		throws Exception {

		Map<String, Object> selectorEntryData = new HashMap<>();

		PortletURL assetBrowserPortletURL = _getAssetBrowserPortletURL(
			assetRendererFactory);

		if (assetBrowserPortletURL != null) {
			selectorEntryData.put("href", assetBrowserPortletURL.toString());
		}

		ResourceBundle resourceBundle = TagResourceBundleUtil.getResourceBundle(
			_pageContext);

		String typeName = assetRendererFactory.getTypeName(
			_themeDisplay.getLocale());

		selectorEntryData.put(
			"title",
			LanguageUtil.format(resourceBundle, "select-x", typeName, false));

		selectorEntryData.put("type", assetRendererFactory.getClassName());

		return selectorEntryData;
	}

	private Map<String, Object> _getSelectorEntryData(
			AssetRendererFactory<?> assetRendererFactory, ClassType classType)
		throws Exception {

		Map<String, Object> selectorEntryData = new HashMap<>();

		PortletURL portletURL = _getAssetBrowserPortletURL(
			assetRendererFactory);

		if (portletURL != null) {
			portletURL.setParameter(
				"subtypeSelectionId",
				String.valueOf(classType.getClassTypeId()));

			selectorEntryData.put("href", portletURL.toString());
		}

		selectorEntryData.put(
			"title",
			LanguageUtil.format(
				TagResourceBundleUtil.getResourceBundle(_pageContext),
				"select-x", classType.getName(), false));

		selectorEntryData.put("type", classType.getName());

		return selectorEntryData;
	}

	private String _getSelectorEntryIconCssClass(
			AssetRendererFactory<?> assetRendererFactory)
		throws Exception {

		return assetRendererFactory.getIconCssClass();
	}

	private String _getSelectorEntryId(
		AssetRendererFactory<?> assetRendererFactory) {

		return FriendlyURLNormalizerUtil.normalize(
			assetRendererFactory.getTypeName(_themeDisplay.getLocale()));
	}

	private String _getSelectorEntryId(
		AssetRendererFactory<?> assetRendererFactory, ClassType classType) {

		String selectorEntryId = String.valueOf(
			_getAssetBrowserGroupId(assetRendererFactory));

		selectorEntryId += FriendlyURLNormalizerUtil.normalize(
			classType.getName());

		return selectorEntryId;
	}

	private String _getSelectorEntryMessage(
		AssetRendererFactory<?> assetRendererFactory) {

		return assetRendererFactory.getTypeName(_themeDisplay.getLocale());
	}

	private String _getSelectorEntryMessage(ClassType classType) {
		return classType.getName();
	}

	private boolean _isStagedLocally() {
		if (_stagedLocally != null) {
			return _stagedLocally;
		}

		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagedRemotely()) {
			_stagedLocally = true;
		}
		else {
			_stagedLocally = false;
		}

		return _stagedLocally;
	}

	private boolean _isStagedReferrerPortlet() {
		if (_stagedReferrerPortlet != null) {
			return _stagedReferrerPortlet;
		}

		if (_isStagedLocally()) {
			String className = (String)_httpServletRequest.getAttribute(
				"liferay-asset:input-asset-links:className");

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			Group scopeGroup = _themeDisplay.getScopeGroup();

			_stagedReferrerPortlet = scopeGroup.isStagedPortlet(
				assetRendererFactory.getPortletId());
		}
		else {
			_stagedReferrerPortlet = false;
		}

		return _stagedReferrerPortlet;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InputAssetLinksDisplayContext.class);

	private final long _assetEntryId;
	private List<AssetLink> _assetLinks;
	private final String _className;
	private String _eventName;
	private final HttpServletRequest _httpServletRequest;
	private final PageContext _pageContext;
	private final PortletRequest _portletRequest;
	private String _randomNamespace;
	private Boolean _stagedLocally;
	private Boolean _stagedReferrerPortlet;
	private final ThemeDisplay _themeDisplay;

}