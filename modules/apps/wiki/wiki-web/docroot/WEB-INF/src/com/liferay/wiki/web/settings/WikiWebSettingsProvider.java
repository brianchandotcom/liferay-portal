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

package com.liferay.wiki.web.settings;

import com.liferay.portal.kernel.settings.PortletInstanceSettingsProvider;
import com.liferay.portal.kernel.settings.SettingsProvider;
import com.liferay.wiki.configuration.WikiConfiguration;
import com.liferay.wiki.settings.WikiPortletInstanceSettings;
import com.liferay.wiki.settings.WikiSettings;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true
)
public class WikiWebSettingsProvider {

	public static WikiWebSettingsProvider getWikiWebSettingsProvider() {
		return _wikiWebSettingsProvider;
	}

	public WikiConfiguration getWikiConfiguration() {
		return _wikiConfiguration;
	}

	public PortletInstanceSettingsProvider<WikiPortletInstanceSettings>
		getWikiPortletInstanceSettingsProvider() {

		return _wikiPortletInstanceSettingsProvider;
	}

	public SettingsProvider<WikiSettings> getWikiSettingsProvider() {
		return _settingsProvider;
	}

	@Activate
	protected void activate() {
		_wikiWebSettingsProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_wikiWebSettingsProvider = null;
	}

	@Reference(
		target = "(class.name=com.liferay.wiki.settings.WikiSettings)"
	)
	protected void setSettingsProvider(
		SettingsProvider<WikiSettings> settingsProvider) {

		_settingsProvider = settingsProvider;
	}

	@Reference
	protected void setWikiConfiguration(WikiConfiguration wikiConfiguration) {
		_wikiConfiguration = wikiConfiguration;
	}

	@Reference(
		target = "(class.name=com.liferay.wiki.settings.WikiPortletInstanceSettings)"
	)
	protected void setWikiPortletInstanceSettingsProvider(
		PortletInstanceSettingsProvider<WikiPortletInstanceSettings>
			wikiPortletInstanceSettingsProvider) {

		_wikiPortletInstanceSettingsProvider =
			wikiPortletInstanceSettingsProvider;
	}

	protected void unsetSettingsProvider(
		SettingsProvider<WikiSettings> settingsProvider) {

		_settingsProvider = null;
	}

	protected void unsetWikiConfiguration(WikiConfiguration wikiConfiguration) {
		_wikiConfiguration = null;
	}

	protected void unsetWikiPortletIntanceSettingsProvider(
		PortletInstanceSettingsProvider<WikiPortletInstanceSettings>
			wikiPortletIntanceSettingsProvider) {

		_wikiPortletInstanceSettingsProvider = null;
	}

	private static WikiWebSettingsProvider _wikiWebSettingsProvider;

	private PortletInstanceSettingsProvider<WikiPortletInstanceSettings>
		_wikiPortletInstanceSettingsProvider;
	private SettingsProvider<WikiSettings> _settingsProvider;
	private WikiConfiguration _wikiConfiguration;

}