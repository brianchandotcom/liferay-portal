/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.renderer.menu.display.internal;

import com.liferay.fragment.renderer.menu.display.internal.MenuDisplayFragmentConfiguration.DisplayStyle;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.fragment.util.configuration.FragmentEntryMenuDisplayConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = MenuDisplayFragmentConfigurationParser.class)
public class MenuDisplayFragmentConfigurationParser {

	public MenuDisplayFragmentConfiguration parse(
		String configuration, String editableValues) {

		String displayStyle = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues,
				LocaleUtil.getMostRelevantLocale(), "displayStyle"));

		String hoveredItemColor = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues,
				LocaleUtil.getMostRelevantLocale(), "hoveredItemColor"));

		String selectedItemColor = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues,
				LocaleUtil.getMostRelevantLocale(), "selectedItemColor"));

		String source = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues,
				LocaleUtil.getMostRelevantLocale(), "source"));

		int sublevels = GetterUtil.getInteger(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, editableValues,
				LocaleUtil.getMostRelevantLocale(), "sublevels"));

		return new MenuDisplayFragmentConfiguration(
			DisplayStyle.parse(displayStyle),
			new FragmentEntryMenuDisplayConfiguration(source), hoveredItemColor,
			selectedItemColor, sublevels);
	}

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

}