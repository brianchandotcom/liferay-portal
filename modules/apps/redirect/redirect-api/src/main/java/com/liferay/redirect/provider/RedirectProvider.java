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

package com.liferay.redirect.provider;

import com.liferay.redirect.model.RedirectPatternEntry;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public interface RedirectProvider {

	public Redirect getRedirect(
		long groupId, String friendlyURL, String fullURL, String userAgent);

	public List<RedirectPatternEntry> getRedirectPatternEntries(long groupId);

	public interface Redirect {

		public String getDestinationURL();

		public boolean isPermanent();

	}

}