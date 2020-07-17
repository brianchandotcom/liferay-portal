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

package com.liferay.frontend.token.definition.internal;

import com.liferay.frontend.token.definition.TokenDefinition;

/**
 * @author Iván Zaera
 */
public class TokenDefinitionImpl implements TokenDefinition {

	public TokenDefinitionImpl(String rawTokenDefinition, String themeId) {
		_rawTokenDefinition = rawTokenDefinition;
		_themeId = themeId;
	}

	@Override
	public String getRawTokenDefinition() {
		return _rawTokenDefinition;
	}

	public String getThemeId() {
		return _themeId;
	}

	private final String _rawTokenDefinition;
	private final String _themeId;

}