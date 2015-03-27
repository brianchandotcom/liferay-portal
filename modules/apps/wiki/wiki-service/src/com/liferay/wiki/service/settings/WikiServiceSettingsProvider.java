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

package com.liferay.wiki.service.settings;

import com.liferay.portal.kernel.settings.GroupServiceSettingsProvider;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.settings.WikiGroupServiceSettings;

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
public class WikiServiceSettingsProvider {

	public static WikiServiceSettingsProvider getWikiServiceSettingsProvider() {
		return _wikiServiceSettingsProvider;
	}

	@Activate
	public void activate() {
		_wikiServiceSettingsProvider = this;
	}

	@Deactivate
	public void deactivate() {
		_wikiServiceSettingsProvider = null;
	}

	public GroupServiceSettingsProvider<WikiGroupServiceSettings>
		getGroupServiceSettingsProvider() {

		return _groupServiceSettingsProvider;
	}

	public WikiGroupServiceConfiguration getWikiGroupServiceConfiguration() {
		return _wikiGroupServiceConfiguration;
	}

	@Reference
	public void setWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	public void unsetGroupServiceSettingsProvider(
		GroupServiceSettingsProvider<WikiGroupServiceSettings>
			groupServiceSettingsProvider) {

		_groupServiceSettingsProvider = null;
	}

	public void unsetWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = null;
	}

	@Reference(
		target = "(class.name=com.liferay.wiki.settings.WikiGroupServiceSettings)"
	)
	protected void setGroupServiceSettingsProvider(
		GroupServiceSettingsProvider<WikiGroupServiceSettings>
			groupServiceSettingsProvider) {

		_groupServiceSettingsProvider = groupServiceSettingsProvider;
	}

	private static WikiServiceSettingsProvider _wikiServiceSettingsProvider;

	private GroupServiceSettingsProvider<WikiGroupServiceSettings>
		_groupServiceSettingsProvider;
	private WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

}