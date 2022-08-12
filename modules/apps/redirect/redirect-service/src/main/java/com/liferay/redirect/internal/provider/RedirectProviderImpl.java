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
import com.liferay.redirect.internal.configuration.RedirectPatternConfiguration;
import com.liferay.redirect.internal.util.PatternUtil;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.provider.RedirectProvider;
import com.liferay.redirect.service.RedirectEntryLocalService;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.redirect.internal.configuration.RedirectPatternConfiguration",
	immediate = true, service = RedirectProvider.class
)
public class RedirectProviderImpl implements RedirectProvider {

	@Override
	public Redirect getRedirect(
		long groupId, String fullURL, String friendlyURL) {

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

		for (Map.Entry<Pattern, String> entry : _patterns.entrySet()) {
			Pattern pattern = entry.getKey();

			Matcher matcher = pattern.matcher(friendlyURL);

			if (matcher.matches()) {
				return new RedirectImpl(
					matcher.replaceFirst(entry.getValue()), false);
			}
		}

		return null;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		RedirectPatternConfiguration redirectPatternConfiguration =
			ConfigurableUtil.createConfigurable(
				RedirectPatternConfiguration.class, properties);

		_patterns = PatternUtil.parsePatterns(
			redirectPatternConfiguration.patterns());
	}

	protected void setPatterns(Map<Pattern, String> patterns) {
		_patterns = patterns;
	}

	protected void setRedirectEntryLocalService(
		RedirectEntryLocalService redirectEntryLocalService) {

		_redirectEntryLocalService = redirectEntryLocalService;
	}

	private volatile Map<Pattern, String> _patterns;

	@Reference
	private RedirectEntryLocalService _redirectEntryLocalService;

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