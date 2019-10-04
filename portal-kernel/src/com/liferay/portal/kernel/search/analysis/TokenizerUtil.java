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

package com.liferay.portal.kernel.search.analysis;

import com.liferay.portal.kernel.search.SearchException;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.List;

/**
 * @author     David Mendez Gonzalez
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class TokenizerUtil {

	public static Tokenizer getTokenizer() {
		Tokenizer tokenizer = _tokenizerUtil._serviceTracker.getService();

		if (tokenizer == null) {
			tokenizer = _defaultTokenizer;
		}

		return tokenizer;
	}

	public static List<String> tokenize(
			String fieldName, String input, String languageId)
		throws SearchException {

		return getTokenizer().tokenize(fieldName, input, languageId);
	}

	private TokenizerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(Tokenizer.class);
	}

	private static final Tokenizer _defaultTokenizer = new SimpleTokenizer();
	private static final TokenizerUtil _tokenizerUtil = new TokenizerUtil();

	private final ServiceTracker<Tokenizer, Tokenizer> _serviceTracker;

}