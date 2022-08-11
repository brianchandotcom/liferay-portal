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

import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.provider.RedirectProvider;
import com.liferay.redirect.service.RedirectEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = RedirectProvider.class)
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

		return null;
	}

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