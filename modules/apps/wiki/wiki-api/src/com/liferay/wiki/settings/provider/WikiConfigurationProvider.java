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

package com.liferay.wiki.settings.provider;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.wiki.settings.WikiConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.wiki", immediate = true,
	service = WikiConfiguration.class
)
public class WikiConfigurationProvider implements WikiConfiguration {

	public String defaultFormat() {
		return _wikiConfiguration.defaultFormat();
	}

	public String displayTemplatesConfig() {
		return _wikiConfiguration.displayTemplatesConfig();
	}

	public String emailFromAddress() {
		return _wikiConfiguration.emailFromAddress();
	}

	public String emailFromName() {
		return _wikiConfiguration.emailFromName();
	}

	public String emailPageAddedBody() {
		return _wikiConfiguration.emailPageAddedBody();
	}

	public String emailPageAddedEnabled() {
		return _wikiConfiguration.emailPageAddedEnabled();
	}

	public String emailPageAddedSubject() {
		return _wikiConfiguration.emailPageAddedSubject();
	}

	public String emailPageUpdatedBody() {
		return _wikiConfiguration.emailPageUpdatedBody();
	}

	public String emailPageUpdatedEnabled() {
		return _wikiConfiguration.emailPageUpdatedEnabled();
	}

	public String emailPageUpdatedSubject() {
		return _wikiConfiguration.emailPageUpdatedSubject();
	}

	public String frontPageName() {
		return _wikiConfiguration.frontPageName();
	}

	public String initialNodeName() {
		return _wikiConfiguration.initialNodeName();
	}

	public String pageCommentsEnabled() {
		return _wikiConfiguration.pageCommentsEnabled();
	}

	public String pageMinorEditAddSocialActivity() {
		return _wikiConfiguration.pageMinorEditAddSocialActivity();
	}

	public String pageMinorEditSendEmail() {
		return _wikiConfiguration.pageMinorEditSendEmail();
	}

	public String pageTitlesRegexp() {
		return _wikiConfiguration.pageTitlesRegexp();
	}

	public String pageTitlesRemoveRegexp() {
		return _wikiConfiguration.pageTitlesRemoveRegexp();
	}

	public String[] parsersCreoleSupportedProtocols() {
		return _wikiConfiguration.parsersCreoleSupportedProtocols();
	}

	public String rssAbstractLength() {
		return _wikiConfiguration.rssAbstractLength();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_wikiConfiguration = Configurable.createConfigurable(
			WikiConfiguration.class, properties);
	}

	private volatile WikiConfiguration _wikiConfiguration;

}