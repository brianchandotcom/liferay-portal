/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * @author David Mendez Gonzalez
 */
public class TokenizerUtil {

	public static Tokenizer getTokenizer() {
		PortalRuntimePermission.checkGetBeanProperty(TokenizerUtil.class);

		return _tokenizer;
	}

	public static List<String> tokenize(
			String fieldName, String input, String languageId)
		throws SearchException {

		return getTokenizer().tokenize(fieldName, input, languageId);
	}

	public void setTokenizer(Tokenizer tokenizer) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_tokenizer = tokenizer;
	}

	private static Tokenizer _tokenizer;

}