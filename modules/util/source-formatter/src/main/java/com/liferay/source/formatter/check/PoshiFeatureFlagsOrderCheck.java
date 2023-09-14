/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class PoshiFeatureFlagsOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith(".testcase")) {
			return content;
		}

		Matcher matcher = _customPropertiesPattern.matcher(content);

		outLooper:
		while (matcher.find()) {
			String[] featureFlagsArray = StringUtil.split(
				matcher.group(1), "${line.separator}");

			if (featureFlagsArray.length == 1) {
				continue;
			}

			for (String featureFlag : featureFlagsArray) {
				if (!featureFlag.matches(
						"feature\\.flag\\.([A-Z]+-\\d+)=\\w+")) {

					continue outLooper;
				}
			}

			List<String> featureFlags = Arrays.asList(featureFlagsArray);

			featureFlags.sort(new NaturalOrderStringComparator());

			String newCustomPropertiesValue = StringUtil.merge(
				featureFlags, "${line.separator}");

			if (!StringUtil.equals(
					matcher.group(1), newCustomPropertiesValue)) {

				return StringUtil.replaceFirst(
					content, matcher.group(1), newCustomPropertiesValue,
					matcher.start(1));
			}
		}

		return content;
	}

	private static final Pattern _customPropertiesPattern = Pattern.compile(
		"\t+property custom.properties = \"(.+)\"");

}