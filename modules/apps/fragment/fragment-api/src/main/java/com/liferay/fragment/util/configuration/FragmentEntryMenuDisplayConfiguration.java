/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.util.configuration;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuMode;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryMenuDisplayConfiguration {

	public FragmentEntryMenuDisplayConfiguration(long groupId, String json) {
		if (!JSONUtil.isJSONObject(json)) {
			_source = _DEFAULT_SOURCE;

			return;
		}

		JSONObject jsonObject = _createJSONObject(json);

		if (jsonObject.has("contextualMenu")) {
			_source = ContextualMenu.parse(
				jsonObject.getString("contextualMenu"));

			return;
		}

		long parentSiteNavigationMenuItemId = jsonObject.getLong(
			"parentSiteNavigationMenuItemId");
		String siteNavigationMenuExternalReferenceCode = jsonObject.getString(
			"siteNavigationMenuExternalReferenceCode");
		long siteNavigationMenuId = jsonObject.getLong("siteNavigationMenuId");

		if ((parentSiteNavigationMenuItemId <= 0) &&
			Validator.isNull(siteNavigationMenuExternalReferenceCode) &&
			(siteNavigationMenuId <= 0)) {

			_source = _DEFAULT_SOURCE;

			return;
		}

		if (Validator.isNull(siteNavigationMenuExternalReferenceCode) &&
			(siteNavigationMenuId <= 0)) {

			_source = new SiteNavigationMenuSource(
				parentSiteNavigationMenuItemId,
				jsonObject.getBoolean("privateLayout"), siteNavigationMenuId);

			return;
		}

		Group group = _fetchGroup(
			groupId,
			jsonObject.getString(
				"siteNavigationMenuScopeExternalReferenceCode"));

		_source = new SiteNavigationMenuSource(
			_getSiteNavigationMenuItemId(
				group,
				jsonObject.getString(
					"parentSiteNavigationMenuItemExternalReferenceCode"),
				parentSiteNavigationMenuItemId),
			jsonObject.getBoolean("privateLayout"),
			_getSiteNavigationMenuId(
				group, siteNavigationMenuExternalReferenceCode,
				siteNavigationMenuId));
	}

	public NavigationMenuMode getNavigationMenuMode() {
		if (_source instanceof SiteNavigationMenuSource) {
			SiteNavigationMenuSource siteNavigationMenuSource =
				(SiteNavigationMenuSource)_source;

			if (siteNavigationMenuSource.isPrivateLayout()) {
				return NavigationMenuMode.PRIVATE_PAGES;
			}

			return NavigationMenuMode.PUBLIC_PAGES;
		}

		return NavigationMenuMode.PUBLIC_PAGES;
	}

	public String getRootItemId() {
		if (!(_source instanceof SiteNavigationMenuSource)) {
			return null;
		}

		SiteNavigationMenuSource siteNavigationMenuSource =
			(SiteNavigationMenuSource)_source;

		long parentSiteNavigationMenuItemId =
			siteNavigationMenuSource.getParentSiteNavigationMenuItemId();

		if (parentSiteNavigationMenuItemId <= 0) {
			return null;
		}

		if (siteNavigationMenuSource.getSiteNavigationMenuId() == 0) {
			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				parentSiteNavigationMenuItemId);

			return layout.getUuid();
		}

		return String.valueOf(parentSiteNavigationMenuItemId);
	}

	public int getRootItemLevel() {
		if (_source instanceof ContextualMenu) {
			ContextualMenu contextualMenu = (ContextualMenu)_source;

			if (contextualMenu == ContextualMenu.CHILDREN) {
				return 0;
			}
			else if (contextualMenu == ContextualMenu.PARENT_AND_ITS_SIBLINGS) {
				return 2;
			}
			else if (contextualMenu == ContextualMenu.SELF_AND_SIBLINGS) {
				return 1;
			}
		}

		return 1;
	}

	public String getRootItemType() {
		if (_source instanceof ContextualMenu) {
			return "relative";
		}

		return "select";
	}

	public long getSiteNavigationMenuId() {
		if (_source instanceof
				SiteNavigationMenuSource siteNavigationMenuSource) {

			return siteNavigationMenuSource.getSiteNavigationMenuId();
		}

		return 0;
	}

	private JSONObject _createJSONObject(String value) {
		try {
			return JSONFactoryUtil.createJSONObject(value);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	private Group _fetchGroup(
		long groupId, String siteNavigationMenuScopeExternalReferenceCode) {

		Group scopeGroup = GroupLocalServiceUtil.fetchGroup(groupId);

		if (Validator.isNotNull(siteNavigationMenuScopeExternalReferenceCode) &&
			(scopeGroup != null) &&
			!Objects.equals(
				siteNavigationMenuScopeExternalReferenceCode,
				scopeGroup.getExternalReferenceCode())) {

			return GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				siteNavigationMenuScopeExternalReferenceCode,
				scopeGroup.getCompanyId());
		}

		return scopeGroup;
	}

	private long _getSiteNavigationMenuId(
		Group group, String siteNavigationMenuExternalReferenceCode,
		long siteNavigationMenuId) {

		if (siteNavigationMenuId > 0) {
			return siteNavigationMenuId;
		}

		if (Validator.isNull(siteNavigationMenuExternalReferenceCode) ||
			(group == null)) {

			return 0;
		}

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.
				fetchSiteNavigationMenuByExternalReferenceCode(
					siteNavigationMenuExternalReferenceCode,
					group.getGroupId());

		if (siteNavigationMenu == null) {
			return 0;
		}

		return siteNavigationMenu.getSiteNavigationMenuId();
	}

	private long _getSiteNavigationMenuItemId(
		Group group, String siteNavigationMenuItemExternalReferenceCode,
		long siteNavigationMenuItemId) {

		if (siteNavigationMenuItemId > 0) {
			return siteNavigationMenuItemId;
		}

		if (Validator.isNull(siteNavigationMenuItemExternalReferenceCode) ||
			(group == null)) {

			return 0;
		}

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemLocalServiceUtil.
				fetchSiteNavigationMenuItemByExternalReferenceCode(
					siteNavigationMenuItemExternalReferenceCode,
					group.getGroupId());

		if (siteNavigationMenuItem == null) {
			return 0;
		}

		return siteNavigationMenuItem.getSiteNavigationMenuItemId();
	}

	private static final Source _DEFAULT_SOURCE = new DefaultSource();

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryMenuDisplayConfiguration.class);

	private final Source _source;

	private static class DefaultSource implements Source {
	}

	private static class SiteNavigationMenuSource implements Source {

		public SiteNavigationMenuSource(
			long parentSiteNavigationMenuItemId, boolean privateLayout,
			long siteNavigationMenuId) {

			_parentSiteNavigationMenuItemId = parentSiteNavigationMenuItemId;
			_privateLayout = privateLayout;
			_siteNavigationMenuId = siteNavigationMenuId;
		}

		public long getParentSiteNavigationMenuItemId() {
			return _parentSiteNavigationMenuItemId;
		}

		public long getSiteNavigationMenuId() {
			return _siteNavigationMenuId;
		}

		public boolean isPrivateLayout() {
			return _privateLayout;
		}

		private final long _parentSiteNavigationMenuItemId;
		private final boolean _privateLayout;
		private final long _siteNavigationMenuId;

	}

	private enum ContextualMenu implements Source {

		CHILDREN("children"),
		PARENT_AND_ITS_SIBLINGS("parent-and-its-siblings"),
		SELF_AND_SIBLINGS("self-and-siblings");

		public static ContextualMenu parse(String stringValue) {
			for (ContextualMenu contextualMenu : values()) {
				if (Objects.equals(contextualMenu.getValue(), stringValue)) {
					return contextualMenu;
				}
			}

			return SELF_AND_SIBLINGS;
		}

		public String getValue() {
			return _value;
		}

		private ContextualMenu(String value) {
			_value = value;
		}

		private final String _value;

	}

	private interface Source {
	}

}