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

package com.liferay.portal.kernel.search.suggest;

import com.liferay.portal.kernel.search.SearchException;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class NGramHolderBuilderUtil {

	public static NGramHolder buildNGramHolder(String input)
		throws SearchException {

		return getNGramHolderBuilder().buildNGramHolder(input);
	}

	public static NGramHolder buildNGramHolder(String input, int maxNGramLength)
		throws SearchException {

		return getNGramHolderBuilder().buildNGramHolder(input, maxNGramLength);
	}

	public static NGramHolder buildNGramHolder(
			String input, int nGramMinLength, int nGramMaxLength)
		throws SearchException {

		return getNGramHolderBuilder().buildNGramHolder(
			input, nGramMinLength, nGramMaxLength);
	}

	public static NGramHolderBuilder getNGramHolderBuilder() {
		if (_nGramHolderBuilderUtil._serviceTracker.getService() == null) {
			return _defaultNGramHolderBuilder;
		}

		return _nGramHolderBuilderUtil._serviceTracker.getService();
	}

	private NGramHolderBuilderUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(NGramHolderBuilder.class);

		_serviceTracker.open();
	}

	private static final NGramHolderBuilder _defaultNGramHolderBuilder =
		new NullNGramHolderBuilder();
	private static final NGramHolderBuilderUtil _nGramHolderBuilderUtil =
		new NGramHolderBuilderUtil();

	private final ServiceTracker<NGramHolderBuilder, NGramHolderBuilder>
		_serviceTracker;

}