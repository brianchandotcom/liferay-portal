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

package com.liferay.portal.search.suggest;

import com.liferay.portal.kernel.search.suggest.NGramHolder;
import com.liferay.portal.kernel.search.suggest.NGramHolderBuilder;

/**
 * @author Michael C. Han
 */
public class NullNGramHolderBuilder implements NGramHolderBuilder {

	@Override
	public NGramHolder buildNGramHolder(String input) {
		return new NGramHolder();
	}

	@Override
	public NGramHolder buildNGramHolder(String input, int nGramMaxLength) {
		return new NGramHolder();
	}

	@Override
	public NGramHolder buildNGramHolder(
		String input, int nGramMinLength, int nGramMaxLength) {

		return new NGramHolder();
	}

}