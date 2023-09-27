/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.matcher;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.redirect.configuration.CrawlerUserAgentsConfiguration;
import com.liferay.redirect.matcher.UserAgentMatcher;

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
	service = UserAgentMatcher.class
)
public class UserAgentMatcherImpl implements UserAgentMatcher {

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

	protected void setCrawlerUserAgents(Set<String> crawlerUserAgents) {
		_crawlerUserAgents = crawlerUserAgents;
	}

	private volatile Set<String> _crawlerUserAgents;

}