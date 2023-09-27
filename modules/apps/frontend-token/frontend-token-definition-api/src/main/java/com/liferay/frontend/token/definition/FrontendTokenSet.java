/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.Collection;
import java.util.Locale;

/**
 * @author Iván Zaera
 */
public interface FrontendTokenSet {

	public FrontendTokenCategory getFrontendTokenCategory();

	public Collection<FrontendTokenMapping> getFrontendTokenMappings();

	public Collection<FrontendToken> getFrontendTokens();

	public JSONObject getJSONObject(Locale locale);

}