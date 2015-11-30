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

package com.liferay.wiki.engine.creole.util;

import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true)
public class WikiEngineCreoleComponentProvider {

	public static WikiEngineCreoleComponentProvider
		getWikiEngineCreoleComponentProvider() {

		return _wikiEngineCreoleComponentProvider;
	}

	@Activate
	public void activate() {
		_wikiEngineCreoleComponentProvider = this;
	}

	@Deactivate
	public void deactivate() {
		_wikiEngineCreoleComponentProvider = null;
	}

	public WikiGroupServiceConfiguration getWikiGroupServiceConfiguration() {
		return _wikiGroupServiceConfiguration;
	}

	@Reference
	public void setWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	protected void unsetWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = null;
	}

	private static WikiEngineCreoleComponentProvider
		_wikiEngineCreoleComponentProvider;

	private volatile WikiGroupServiceConfiguration
		_wikiGroupServiceConfiguration;

}