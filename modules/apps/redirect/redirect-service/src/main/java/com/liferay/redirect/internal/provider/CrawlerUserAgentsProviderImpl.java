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

package com.liferay.redirect.internal.provider;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.redirect.configuration.CrawlerUserAgentsConfiguration;
import com.liferay.redirect.provider.CrawlerUserAgentsProvider;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alicia García
 */
@Component(
	configurationPid = "com.liferay.redirect.configuration.CrawlerUserAgentsConfiguration",
	service = CrawlerUserAgentsProvider.class
)
public class CrawlerUserAgentsProviderImpl
	implements CrawlerUserAgentsProvider {

	@Override
	public boolean isCrawlerUserAgent(String userAgent) {
		if (Validator.isNull(userAgent) ||
			SetUtil.isEmpty(_crawlerUserAgents)) {

			return false;
		}

		userAgent = StringUtil.toLowerCase(userAgent);

		if (_crawlerUserAgents.contains(userAgent)) {
			return true;
		}

		for (String crawlerUserAgent : _crawlerUserAgents) {
			if (userAgent.contains(crawlerUserAgent)) {
				return true;
			}
		}

		return false;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		CrawlerUserAgentsConfiguration crawlerUserAgentsConfiguration =
			ConfigurableUtil.createConfigurable(
				CrawlerUserAgentsConfiguration.class, properties);

		Set<String> crawlerUserAgents = new HashSet<>();

		for (String crawlerUserAgent :
				crawlerUserAgentsConfiguration.crawlerUserAgents()) {

			crawlerUserAgents.add(StringUtil.toLowerCase(crawlerUserAgent));
		}

		_crawlerUserAgents = crawlerUserAgents;
	}

	private volatile Set<String> _crawlerUserAgents;

}