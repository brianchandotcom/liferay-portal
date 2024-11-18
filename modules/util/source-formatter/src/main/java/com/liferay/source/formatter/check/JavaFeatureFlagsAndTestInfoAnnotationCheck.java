/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JavaFeatureFlagsAndTestInfoAnnotationCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		for (String annotationName : _ANNOTATION_NAMES) {
			Pattern pattern = Pattern.compile(
				"\n\t*@" + annotationName + "\\((.+?)\\)\n", Pattern.DOTALL);

			Matcher matcher = pattern.matcher(content);

			while (matcher.find()) {
				String annotationContent = matcher.group(1);

				String trimmedAnnotationContent = annotationContent.trim();

				if (trimmedAnnotationContent.startsWith("\"") &&
					trimmedAnnotationContent.endsWith("\"")) {

					trimmedAnnotationContent =
						trimmedAnnotationContent.substring(
							1, trimmedAnnotationContent.length() - 1);

					String[] values = trimmedAnnotationContent.split(",");

					if (values.length < 2) {
						continue;
					}

					Arrays.sort(values, new NaturalOrderStringComparator());

					StringBundler sb = new StringBundler(values.length * 4);

					for (String value : values) {
						sb.append(StringPool.QUOTE);
						sb.append(value);
						sb.append(StringPool.QUOTE);
						sb.append(StringPool.COMMA_AND_SPACE);
					}

					if (sb.index() > 0) {
						sb.setIndex(sb.index() - 1);
					}

					return StringUtil.replaceFirst(
						content, annotationContent, "{" + sb.toString() + "}",
						matcher.start(1));
				}
				else if (trimmedAnnotationContent.startsWith("{") &&
						 trimmedAnnotationContent.endsWith("}")) {

					trimmedAnnotationContent =
						trimmedAnnotationContent.substring(
							1, trimmedAnnotationContent.length() - 1);

					trimmedAnnotationContent =
						trimmedAnnotationContent.replaceAll("\n\t+", " ");
					trimmedAnnotationContent = trimmedAnnotationContent.trim();

					String[] values = trimmedAnnotationContent.split(", ");

					if (values.length < 2) {
						continue;
					}

					NaturalOrderStringComparator comparator =
						new NaturalOrderStringComparator();
					String previousValue = null;

					for (String value : values) {
						if (previousValue == null) {
							previousValue = value;

							continue;
						}

						if (comparator.compare(previousValue, value) > 0) {
							addMessage(
								fileName,
								StringBundler.concat(
									"Incorrect order in @", annotationName,
									": ", previousValue, " should come after ",
									value),
								getLineNumber(content, matcher.start(1)));

							break;
						}

						previousValue = value;
					}
				}
			}
		}

		return content;
	}

	private static final String[] _ANNOTATION_NAMES = {
		"FeatureFlags", "TestInfo"
	};

}