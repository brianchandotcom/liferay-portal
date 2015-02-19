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

package com.liferay.wiki.configuration;

import aQute.bnd.annotation.metatype.Configurable;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.wiki.configuration.WikiServiceConfiguration",
	immediate = true, service = WikiServiceConfiguration.class
)
public class WikiServiceConfigurationImpl
	implements WikiServiceConfiguration {

	public static WikiServiceConfiguration getWikiServiceConfiguration() {
		if (_wikiServiceConfigurationImpl == null) {
			throw new IllegalStateException(
				"WikiServiceConfiguration is not yet available");
		}

		return _wikiServiceConfigurationImpl;
	}

	public String defaultFormat() {
		return _wikiServiceConfiguration.defaultFormat();
	}

	public String emailFromAddress() {
		return _wikiServiceConfiguration.emailFromAddress();
	}

	public String emailFromName() {
		return _wikiServiceConfiguration.emailFromName();
	}

	public String emailPageAddedBody() {
		return _wikiServiceConfiguration.emailPageAddedBody();
	}

	public boolean emailPageAddedEnabled() {
		return _wikiServiceConfiguration.emailPageAddedEnabled();
	}

	public String emailPageAddedSubject() {
		return _wikiServiceConfiguration.emailPageAddedSubject();
	}

	public String emailPageUpdatedBody() {
		return _wikiServiceConfiguration.emailPageUpdatedBody();
	}

	public boolean emailPageUpdatedEnabled() {
		return _wikiServiceConfiguration.emailPageUpdatedEnabled();
	}

	public String emailPageUpdatedSubject() {
		return _wikiServiceConfiguration.emailPageUpdatedSubject();
	}

	public String frontPageName() {
		return _wikiServiceConfiguration.frontPageName();
	}

	public String initialNodeName() {
		return _wikiServiceConfiguration.initialNodeName();
	}

	public boolean pageCommentsEnabled() {
		return _wikiServiceConfiguration.pageCommentsEnabled();
	}

	public boolean pageMinorEditAddSocialActivity() {
		return _wikiServiceConfiguration.pageMinorEditAddSocialActivity();
	}

	public boolean pageMinorEditSendEmail() {
		return _wikiServiceConfiguration.pageMinorEditSendEmail();
	}

	public String pageTitlesRegexp() {
		return _wikiServiceConfiguration.pageTitlesRegexp();
	}

	public String pageTitlesRemoveRegexp() {
		return _wikiServiceConfiguration.pageTitlesRemoveRegexp();
	}

	public String[] parsersCreoleSupportedProtocols() {
		return _wikiServiceConfiguration.parsersCreoleSupportedProtocols();
	}

	public int rssAbstractLength() {
		return _wikiServiceConfiguration.rssAbstractLength();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_wikiServiceConfiguration = Configurable.createConfigurable(
			WikiServiceConfiguration.class, properties);

		_wikiServiceConfigurationImpl = this;
	}

	@Deactivate
	protected void deactivate() {
		_wikiServiceConfigurationImpl = null;
	}

	private static WikiServiceConfigurationImpl
		_wikiServiceConfigurationImpl;

	private volatile WikiServiceConfiguration _wikiServiceConfiguration;

}