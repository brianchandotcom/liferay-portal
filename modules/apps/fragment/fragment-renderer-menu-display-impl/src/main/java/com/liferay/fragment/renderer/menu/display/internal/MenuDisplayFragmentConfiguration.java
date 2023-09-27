/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.renderer.menu.display.internal;

import java.util.Objects;

/**
 * @author Víctor Galán
 */
public class MenuDisplayFragmentConfiguration {

	public static final Source DEFAULT_SOURCE = new SiteNavigationMenuSource(
		0, false, 0);

	public MenuDisplayFragmentConfiguration(
		DisplayStyle displayStyle, String hoveredItemColor,
		String selectedItemColor, Source source, int sublevels) {

		_displayStyle = displayStyle;
		_hoveredItemColor = hoveredItemColor;
		_selectedItemColor = selectedItemColor;
		_source = source;
		_sublevels = sublevels;
	}

	public DisplayStyle getDisplayStyle() {
		return _displayStyle;
	}

	public String getHoveredItemColor() {
		return _hoveredItemColor;
	}

	public String getSelectedItemColor() {
		return _selectedItemColor;
	}

	public Source getSource() {
		return _source;
	}

	public int sublevels() {
		return _sublevels;
	}

	public static class SiteNavigationMenuSource implements Source {

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

	public enum ContextualMenu implements Source {

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

	public enum DisplayStyle {

		HORIZONTAL("horizontal"), STACKED("stacked");

		public static DisplayStyle parse(String stringValue) {
			for (DisplayStyle displayStyle : values()) {
				if (Objects.equals(displayStyle.getValue(), stringValue)) {
					return displayStyle;
				}
			}

			return HORIZONTAL;
		}

		public String getValue() {
			return _value;
		}

		private DisplayStyle(String value) {
			_value = value;
		}

		private final String _value;

	}

	public interface Source {
	}

	private final DisplayStyle _displayStyle;
	private final String _hoveredItemColor;
	private final String _selectedItemColor;
	private final Source _source;
	private final int _sublevels;

}