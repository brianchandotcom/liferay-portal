/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.analysis;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.analysis.Tokenizer;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class SimpleTokenizer implements Tokenizer {

	@Override
	public List<String> tokenize(
		String fieldName, String input, String languageId) {

		return Arrays.asList(StringUtil.split(input, StringPool.PERIOD));
	}

}