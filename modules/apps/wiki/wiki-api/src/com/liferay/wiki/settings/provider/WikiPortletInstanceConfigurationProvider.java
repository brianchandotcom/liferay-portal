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

import com.liferay.wiki.settings.WikiPortletInstanceConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.wiki.portlet.instance", immediate = true,
	service = WikiPortletInstanceConfiguration.class
)
public class WikiPortletInstanceConfigurationProvider
	implements WikiPortletInstanceConfiguration {

	@Override
	public String displayStyle() {
		return _wikiPortletInstanceConfiguration.displayStyle();
	}

	@Override
	public String enableCommentRatings() {
		return _wikiPortletInstanceConfiguration.enableCommentRatings();
	}

	@Override
	public String enableComments() {
		return _wikiPortletInstanceConfiguration.enableComments();
	}

	@Override
	public String enablePageRatings() {
		return _wikiPortletInstanceConfiguration.enablePageRatings();
	}

	@Override
	public String enableRelatedAssets() {
		return _wikiPortletInstanceConfiguration.enableRelatedAssets();
	}

	@Override
	public String enableRss() {
		return _wikiPortletInstanceConfiguration.enableRss();
	}

	@Override
	public String rssDelta() {
		return _wikiPortletInstanceConfiguration.rssDelta();
	}

	@Override
	public String rssDisplayStyle() {
		return _wikiPortletInstanceConfiguration.rssDisplayStyle();
	}

	@Override
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