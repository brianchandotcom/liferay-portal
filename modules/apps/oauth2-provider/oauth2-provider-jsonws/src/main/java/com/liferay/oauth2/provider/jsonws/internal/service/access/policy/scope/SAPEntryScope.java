/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public SAPEntry getSAPEntry() {
		return _sapEntry;
	}

	public String getSAPEntryName() {
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