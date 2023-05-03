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

import com.google.re2j.Matcher;
import com.google.re2j.Pattern;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.redirect.constants.RedirectConstants;
import com.liferay.redirect.internal.configuration.RedirectPatternConfiguration;
import com.liferay.redirect.internal.util.PatternUtil;
import com.liferay.redirect.matcher.UserAgentMatcher;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.model.RedirectPatternEntry;
import com.liferay.redirect.provider.RedirectProvider;
import com.liferay.redirect.service.RedirectEntryLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = Constants.SERVICE_PID + "=com.liferay.redirect.internal.configuration.RedirectPatternConfiguration.scoped",
	service = {ManagedServiceFactory.class, RedirectProvider.class}
)
public class RedirectProviderImpl
	implements ManagedServiceFactory, RedirectProvider {

	@Override
	public void deleted(String pid) {
		_unmapPid(pid);
	}

	@Override
	public String getName() {
		return "com.liferay.redirect.internal.configuration." +
			"RedirectPatternConfiguration.scoped";
	}

	@Override
	public Redirect getRedirect(
		long groupId, String friendlyURL, String fullURL, String userAgent) {

		if (friendlyURL.contains("/control_panel/manage")) {
			return null;
		}

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.fetchRedirectEntry(
				groupId, fullURL, false);

		if (redirectEntry == null) {
			redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
				groupId, friendlyURL, true);
		}

		if (redirectEntry != null) {
			return new RedirectImpl(
				redirectEntry.getDestinationURL(), redirectEntry.isPermanent());
		}

		List<RedirectPatternEntry> redirectPatternEntries =
			_redirectPatternEntries.getOrDefault(
				groupId, Collections.emptyList());

		for (RedirectPatternEntry redirectPatternEntry :
				redirectPatternEntries) {

			if (_isUserAgentMatch(redirectPatternEntry, userAgent)) {
				Pattern pattern = redirectPatternEntry.getPattern();

				Matcher matcher = pattern.matcher(friendlyURL);

				if (matcher.matches()) {
					return new RedirectImpl(
						matcher.replaceFirst(
							redirectPatternEntry.getDestinationURL()),
						false);
				}
			}
		}

		return null;
	}

	@Override
	public List<RedirectPatternEntry> getRedirectPatternEntries(long groupId) {
		List<RedirectPatternEntry> redirectPatternEntries =
			_redirectPatternEntries.get(groupId);

		if (redirectPatternEntries != null) {
			return redirectPatternEntries;
		}

		return new ArrayList<>();
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		_unmapPid(pid);

		long groupId = GetterUtil.getLong(
			dictionary.get("groupId"), GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			return;
		}

		_groupIds.put(pid, groupId);

		RedirectPatternConfiguration redirectPatternConfiguration =
			ConfigurableUtil.createConfigurable(
				RedirectPatternConfiguration.class, dictionary);

		_redirectPatternEntries.put(
			groupId,
			PatternUtil.parse(redirectPatternConfiguration.patternStrings()));
	}

	protected void setCrawlerUserAgentsMatcher(
		UserAgentMatcher userAgentMatcher) {

		_userAgentMatcher = userAgentMatcher;
	}

	protected void setRedirectEntryLocalService(
		RedirectEntryLocalService redirectEntryLocalService) {

		_redirectEntryLocalService = redirectEntryLocalService;
	}

	protected void setRedirectPatternEntries(
		Map<Long, List<RedirectPatternEntry>> redirectPatternEntries) {

		_redirectPatternEntries = redirectPatternEntries;
	}

	private boolean _isUserAgentMatch(
		RedirectPatternEntry redirectPatternEntry, String userAgent) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-175850") ||
			Validator.isNull(redirectPatternEntry.getUserAgent()) ||
			Validator.isNull(userAgent) ||
			Objects.equals(
				RedirectConstants.USER_AGENT_ALL,
				redirectPatternEntry.getUserAgent())) {

			return true;
		}

		boolean crawlerUserAgent = _userAgentMatcher.isCrawlerUserAgent(
			userAgent);

		if (crawlerUserAgent &&
			Objects.equals(
				RedirectConstants.USER_AGENT_BOT,
				redirectPatternEntry.getUserAgent())) {

			return true;
		}

		if (!crawlerUserAgent &&
			Objects.equals(
				RedirectConstants.USER_AGENT_HUMAN,
				redirectPatternEntry.getUserAgent())) {

			return true;
		}

		return false;
	}

	private void _unmapPid(String pid) {
		if (_groupIds.containsKey(pid)) {
			Long groupId = _groupIds.remove(pid);

			_redirectPatternEntries.remove(groupId);
		}
	}

	private final Map<String, Long> _groupIds = new ConcurrentHashMap<>();

	@Reference
	private RedirectEntryLocalService _redirectEntryLocalService;

	private Map<Long, List<RedirectPatternEntry>> _redirectPatternEntries =
		new ConcurrentHashMap<>();

	@Reference
	private UserAgentMatcher _userAgentMatcher;

	private static class RedirectImpl implements Redirect {

		public RedirectImpl(String destinationURL, boolean permanent) {
			_destinationURL = destinationURL;
			_permanent = permanent;
		}

		@Override
		public String getDestinationURL() {
			return _destinationURL;
		}

		@Override
		public boolean isPermanent() {
			return _permanent;
		}

		private final String _destinationURL;
		private final boolean _permanent;

	}

}