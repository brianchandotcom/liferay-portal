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

import com.liferay.wiki.configuration.WikiPortletInstanceConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.wiki.configuration.WikiPortletInstanceConfiguration",
	immediate = true, service = WikiPortletInstanceConfiguration.class
)
public class WikiPortletInstanceConfigurationImpl
	implements WikiPortletInstanceConfiguration {

	public String displayStyle() {
		return _wikiPortletInstanceConfiguration.displayStyle();
	}

	public boolean enableCommentRatings() {
		return _wikiPortletInstanceConfiguration.enableCommentRatings();
	}

	public boolean enableComments() {
		return _wikiPortletInstanceConfiguration.enableComments();
	}

	public boolean enablePageRatings() {
		return _wikiPortletInstanceConfiguration.enablePageRatings();
	}

	public boolean enableRelatedAssets() {
		return _wikiPortletInstanceConfiguration.enableRelatedAssets();
	}

	public boolean enableRss() {
		return _wikiPortletInstanceConfiguration.enableRss();
	}

	public String rssDelta() {
		return _wikiPortletInstanceConfiguration.rssDelta();
	}

	public String rssDisplayStyle() {
		return _wikiPortletInstanceConfiguration.rssDisplayStyle();
	}

	public String rssFeedType() {
		return _wikiPortletInstanceConfiguration.rssFeedType();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_wikiPortletInstanceConfiguration = Configurable.createConfigurable(
			WikiPortletInstanceConfiguration.class, properties);
	}

	private volatile WikiPortletInstanceConfiguration
		_wikiPortletInstanceConfiguration;

}