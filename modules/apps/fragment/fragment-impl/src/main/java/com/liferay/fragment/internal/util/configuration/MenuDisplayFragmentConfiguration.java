/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.util.configuration;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuMode;

import java.util.Objects;

/**
 * @author Víctor Galán
 * @author Evan Thibodeau
 */
public class MenuDisplayFragmentConfiguration {

	public MenuDisplayFragmentConfiguration(String source) {
		_rootLayoutUUID = null;
		_siteNavigationMenuId = 0;

		if (!JSONUtil.isValid(source)) {
			_navigationMenuMode = NavigationMenuMode.PUBLIC_PAGES;
			_rootLayoutType = "select";
		}
		else {
			JSONObject jsonObject = _createJSONObject(source);

			if (jsonObject.has("contextualMenu")) {
				_rootLayoutType = "relative";

				ContextualMenu contextualMenu = ContextualMenu.parse(
					jsonObject.getString("contextualMenu"));

				if (contextualMenu == ContextualMenu.CHILDREN) {
					_rootLayoutLevel = 0;
				}
				else if (contextualMenu ==
							ContextualMenu.PARENT_AND_ITS_SIBLINGS) {

					_rootLayoutLevel = 2;
				}
				else if (contextualMenu == ContextualMenu.SELF_AND_SIBLINGS) {
					_rootLayoutLevel = 1;
				}
			}
			else if (jsonObject.has("siteNavigationMenuId")) {
				_rootLayoutType = "select";

				if (jsonObject.getBoolean("privateLayout")) {
					_navigationMenuMode = NavigationMenuMode.PRIVATE_PAGES;
				}
				else {
					_navigationMenuMode = NavigationMenuMode.PUBLIC_PAGES;
				}

				long siteNavigationMenuId = jsonObject.getLong(
					"siteNavigationMenuId");

				_siteNavigationMenuId = siteNavigationMenuId;

				long parentSiteNavigationMenuItemId = jsonObject.getLong(
					"parentSiteNavigationMenuItemId");

				if (parentSiteNavigationMenuItemId > 0) {
					if (_isLayoutHierarchy(siteNavigationMenuId)) {
						Layout layout = LayoutLocalServiceUtil.fetchLayout(
							parentSiteNavigationMenuItemId);

						_rootLayoutUUID = layout.getUuid();
					}
					else {
						_rootLayoutUUID = String.valueOf(
							parentSiteNavigationMenuItemId);
					}
				}
			}
		}
	}

	public NavigationMenuMode getNavigationMenuMode() {
		return _navigationMenuMode;
	}

	public int getRootLayoutLevel() {
		return _rootLayoutLevel;
	}

	public String getRootLayoutType() {
		return _rootLayoutType;
	}

	public String getRootLayoutUUID() {
		return _rootLayoutUUID;
	}

	public long getSiteNavigationMenuId() {
		return _siteNavigationMenuId;
	}

	public void setNavigationMenuMode(NavigationMenuMode navigationMenuMode) {
		_navigationMenuMode = navigationMenuMode;
	}

	public void setRootLayoutLevel(int rootLayoutLevel) {
		_rootLayoutLevel = rootLayoutLevel;
	}

	public void setRootLayoutType(String rootLayoutType) {
		_rootLayoutType = rootLayoutType;
	}

	public void setRootLayoutUUID(String rootLayoutUUID) {
		_rootLayoutUUID = rootLayoutUUID;
	}

	public void setSiteNavigationMenuId(long siteNavigationMenuId) {
		_siteNavigationMenuId = siteNavigationMenuId;
	}

	public enum ContextualMenu {

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

	private boolean _isLayoutHierarchy(long siteNavigationMenuId) {
		if (siteNavigationMenuId == 0) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MenuDisplayFragmentConfiguration.class);

	private NavigationMenuMode _navigationMenuMode = NavigationMenuMode.DEFAULT;
	private int _rootLayoutLevel = 1;
	private String _rootLayoutType = "absolute";
	private String _rootLayoutUUID;
	private long _siteNavigationMenuId;

}