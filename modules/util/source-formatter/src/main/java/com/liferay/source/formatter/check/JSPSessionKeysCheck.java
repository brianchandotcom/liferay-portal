/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPSessionKeysCheck extends SessionKeysCheck {

	@Override
	protected List<Pattern> getPatterns() {
		return Arrays.asList(sessionKeyPattern, _taglibSessionKeyPattern);
	}

	private static final Pattern _taglibSessionKeyPattern = Pattern.compile(
		"<liferay-ui:error [^>]+>|<liferay-ui:success [^>]+>",
		Pattern.MULTILINE);

}