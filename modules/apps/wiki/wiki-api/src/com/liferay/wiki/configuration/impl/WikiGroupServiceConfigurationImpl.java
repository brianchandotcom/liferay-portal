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

package com.liferay.wiki.configuration.impl;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.wiki.configuration.WikiGroupServiceConfiguration",
	immediate = true, service = WikiGroupServiceConfiguration.class
)
public class WikiGroupServiceConfigurationImpl
	implements WikiGroupServiceConfiguration {

	public String defaultFormat() {
		return _wikiGroupServiceConfiguration.defaultFormat();
	}

	public String emailFromAddress() {
		return _wikiGroupServiceConfiguration.emailFromAddress();
	}

	public String emailFromName() {
		return _wikiGroupServiceConfiguration.emailFromName();
	}

	public String emailPageAddedBody() {
		return _wikiGroupServiceConfiguration.emailPageAddedBody();
	}

	public boolean emailPageAddedEnabled() {
		return _wikiGroupServiceConfiguration.emailPageAddedEnabled();
	}

	public String emailPageAddedSubject() {
		return _wikiGroupServiceConfiguration.emailPageAddedSubject();
	}

	public String emailPageUpdatedBody() {
		return _wikiGroupServiceConfiguration.emailPageUpdatedBody();
	}

	public boolean emailPageUpdatedEnabled() {
		return _wikiGroupServiceConfiguration.emailPageUpdatedEnabled();
	}

	public String emailPageUpdatedSubject() {
		return _wikiGroupServiceConfiguration.emailPageUpdatedSubject();
	}

	public String frontPageName() {
		return _wikiGroupServiceConfiguration.frontPageName();
	}

	public String initialNodeName() {
		return _wikiGroupServiceConfiguration.initialNodeName();
	}

	public boolean pageCommentsEnabled() {
		return _wikiGroupServiceConfiguration.pageCommentsEnabled();
	}

	public boolean pageMinorEditAddSocialActivity() {
		return _wikiGroupServiceConfiguration.pageMinorEditAddSocialActivity();
	}

	public boolean pageMinorEditSendEmail() {
		return _wikiGroupServiceConfiguration.pageMinorEditSendEmail();
	}

	public String pageTitlesRegexp() {
		return _wikiGroupServiceConfiguration.pageTitlesRegexp();
	}

	public String pageTitlesRemoveRegexp() {
		return _wikiGroupServiceConfiguration.pageTitlesRemoveRegexp();
	}

	public String[] parsersCreoleSupportedProtocols() {
		return _wikiGroupServiceConfiguration.parsersCreoleSupportedProtocols();
	}

	public int rssAbstractLength() {
		return _wikiGroupServiceConfiguration.rssAbstractLength();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_wikiGroupServiceConfiguration = Configurable.createConfigurable(
			WikiGroupServiceConfiguration.class, properties);
	}

	private volatile WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

}