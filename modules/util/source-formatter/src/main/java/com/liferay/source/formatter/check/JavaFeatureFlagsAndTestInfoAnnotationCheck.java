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

/**
 * @author Alan Huang
 */
public class JavaFeatureFlagsAndTestInfoAnnotationCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	protected String doProcess(
		String fileName, String absolutePath, String content) {

		for (String annotationName : _ANNOTATION_NAMES) {
			int x = -1;

			while (true) {
				x = content.indexOf("\t@" + annotationName + "(", x + 1);

				if (x == -1) {
					break;
				}

				int y = content.indexOf(")", x);

				if (y == -1) {
					continue;
				}

				String annotationContent = content.substring(
					x + annotationName.length() + 3, y);

				String trimmmedAnnotationContent = annotationContent.trim();

				if (trimmmedAnnotationContent.startsWith("\"") &&
					trimmmedAnnotationContent.endsWith("\"")) {

					trimmmedAnnotationContent =
						trimmmedAnnotationContent.substring(
							1, trimmmedAnnotationContent.length() - 1);

					String[] values = trimmmedAnnotationContent.split(",");

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
						x);
				}
				else if (trimmmedAnnotationContent.startsWith("{") &&
						 trimmmedAnnotationContent.endsWith("}")) {

					trimmmedAnnotationContent =
						trimmmedAnnotationContent.substring(
							1, trimmmedAnnotationContent.length() - 1);

					trimmmedAnnotationContent =
						trimmmedAnnotationContent.replaceAll("\n\t+", " ");
					trimmmedAnnotationContent =
						trimmmedAnnotationContent.trim();

					String[] values = trimmmedAnnotationContent.split(", ");

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
								getLineNumber(content, x));

							break;
						}
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