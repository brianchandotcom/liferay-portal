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

package com.liferay.fragment.renderer.menu.display.internal;

/**
 * @author Víctor Galán
 */
public class MenuDisplayFragmentConfiguration {

	public MenuDisplayFragmentConfiguration(
		DisplayStyle displayStyle, int numberOfSublevels, Source source) {

		_displayStyle = displayStyle;
		_numberOfSublevels = numberOfSublevels;
		_source = source;
	}

	public DisplayStyle getDisplayStyle() {
		return _displayStyle;
	}

	public int getNumberOfSublevels() {
		return _numberOfSublevels;
	}

	public Source getSource() {
		return _source;
	}

	public static class SiteNavigationMenuSource implements Source {

		public SiteNavigationMenuSource(
			long parentSiteNavigationMenuItemId, long siteNavigationMenuId) {

			_parentSiteNavigationMenuItemId = parentSiteNavigationMenuItemId;
			_siteNavigationMenuId = siteNavigationMenuId;
		}

		public long getParentSiteNavigationMenuItemId() {
			return _parentSiteNavigationMenuItemId;
		}

		public long getSiteNavigationMenuId() {
			return _siteNavigationMenuId;
		}

		private final long _parentSiteNavigationMenuItemId;
		private final long _siteNavigationMenuId;

	}

	public enum ContextualMenu implements Source {

		SAME_LEVEL, SECOND_LEVEL, UPPER_LEVEL

	}

	public enum DisplayStyle {

		HORIZONTAL, STACKED

	}

	public interface Source {
	}

	private final DisplayStyle _displayStyle;
	private final int _numberOfSublevels;
	private final Source _source;

}