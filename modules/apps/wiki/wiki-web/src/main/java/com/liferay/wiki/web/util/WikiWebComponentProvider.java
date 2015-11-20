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

package com.liferay.wiki.web.util;

import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.web.display.context.WikiDisplayContextProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true)
public class WikiWebComponentProvider {

	public static WikiWebComponentProvider getWikiWebComponentProvider() {
		return _wikiWebComponentProvider;
	}

	public WikiDisplayContextProvider getWikiDisplayContextProvider() {
		return _wikiDisplayContextProvider;
	}

	public WikiGroupServiceConfiguration getWikiGroupServiceConfiguration() {
		return _wikiGroupServiceConfiguration;
	}

	@Reference(unbind = "-")
	public void setWikiDisplayContextProvider(
		WikiDisplayContextProvider wikiDisplayContextProvider) {

		_wikiDisplayContextProvider = wikiDisplayContextProvider;
	}

	@Activate
	protected void activate() {
		_wikiWebComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_wikiWebComponentProvider = null;
	}

	@Reference
	protected void setWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	protected void unsetWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = null;
	}

	private static WikiWebComponentProvider _wikiWebComponentProvider;

	private volatile WikiDisplayContextProvider _wikiDisplayContextProvider;
	private volatile WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

}