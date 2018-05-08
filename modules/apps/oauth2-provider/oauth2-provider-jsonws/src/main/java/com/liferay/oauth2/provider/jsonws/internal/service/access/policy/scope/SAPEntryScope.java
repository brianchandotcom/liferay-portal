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

package com.liferay.oauth2.provider.jsonws.internal.service.access.policy.scope;

import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import java.util.Locale;

/**
 * @author Tomas Polesovsky
 */
public class SAPEntryScope {

	public SAPEntryScope(SAPEntry sapEntry, String scope) {
		_sapEntry = sapEntry;
		_scope = scope;
	}

	public String getSapEntryName() {
		return _sapEntry.getName();
	}

	public String getScope() {
		return _scope;
	}

	public String getTitle(Locale locale) {
		return _sapEntry.getTitle(locale);
	}

	private final SAPEntry _sapEntry;
	private final String _scope;

}