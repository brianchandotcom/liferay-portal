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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), moved to {@link
 *             com.liferay.portal.search.analysis.SimpleTokenizer}
 */
@Deprecated
public class SimpleTokenizer implements Tokenizer {

	@Override
	public List<String> tokenize(
		String fieldName, String input, String languageId) {

		return Arrays.asList(StringUtil.split(input, StringPool.PERIOD));
	}

}