/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.frontend.token.definition.FrontendToken;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.frontend.token.definition.internal.json.JSONLocalizer;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;

/**
 * @author Iván Zaera
 */
public class FrontendTokenMappingImpl implements FrontendTokenMapping {

	public FrontendTokenMappingImpl(
		FrontendTokenImpl frontendTokenImpl, JSONObject jsonObject) {

		_frontendTokenImpl = frontendTokenImpl;

		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
			frontendTokenImpl.getFrontendTokenDefinition();

		_jsonLocalizer = frontendTokenDefinitionImpl.createJSONLocalizer(
			jsonObject);

		_type = jsonObject.getString("type");
		_value = jsonObject.getString("value");
	}

	@Override
	public FrontendToken getFrontendToken() {
		return _frontendTokenImpl;
	}

	@Override
	public JSONObject getJSONObject(Locale locale) {
		return _jsonLocalizer.getJSONObject(locale);
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	private final FrontendTokenImpl _frontendTokenImpl;
	private final JSONLocalizer _jsonLocalizer;
	private final String _type;
	private final String _value;

}