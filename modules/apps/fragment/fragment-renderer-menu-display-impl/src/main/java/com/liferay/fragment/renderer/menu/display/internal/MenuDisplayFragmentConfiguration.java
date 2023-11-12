/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.renderer.menu.display.internal;

import com.liferay.fragment.util.configuration.FragmentEntryMenuDisplayConfiguration;

import java.util.Objects;

/**
 * @author Víctor Galán
 */
public class MenuDisplayFragmentConfiguration {

	public MenuDisplayFragmentConfiguration(
		DisplayStyle displayStyle,
		FragmentEntryMenuDisplayConfiguration
			fragmentEntryMenuDisplayConfiguration,
		String hoveredItemColor, String selectedItemColor, int sublevels) {

		_displayStyle = displayStyle;
		_fragmentEntryMenuDisplayConfiguration =
			fragmentEntryMenuDisplayConfiguration;
		_hoveredItemColor = hoveredItemColor;
		_selectedItemColor = selectedItemColor;
		_sublevels = sublevels;
	}

	public DisplayStyle getDisplayStyle() {
		return _displayStyle;
	}

	public FragmentEntryMenuDisplayConfiguration
		getFragmentEntryMenuDisplayConfiguration() {

		return _fragmentEntryMenuDisplayConfiguration;
	}

	public String getHoveredItemColor() {
		return _hoveredItemColor;
	}

	public String getSelectedItemColor() {
		return _selectedItemColor;
	}

	public int sublevels() {
		return _sublevels;
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
	private final FragmentEntryMenuDisplayConfiguration
		_fragmentEntryMenuDisplayConfiguration;
	private final String _hoveredItemColor;
	private final String _selectedItemColor;
	private final int _sublevels;

}