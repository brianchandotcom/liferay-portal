/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaDDMFormValuesSerializerTrackerCheck
	extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!content.contains("DDMFormValuesSerializerTracker")) {
			return content;
		}

		Matcher trackerReferenceMatcher = _trackerReferencePattern.matcher(
			content);

		String newContent = content;

		if (trackerReferenceMatcher.find()) {
			newContent = StringUtil.removeSubstring(
				content, trackerReferenceMatcher.group());

			newContent = StringUtil.removeSubstring(
				newContent,
				"import com.liferay.dynamic.data.mapping.io." +
					"DDMFormValuesSerializerTracker;\n");
		}

		JavaClass javaClass = JavaClassParser.parseJavaClass(
			fileName, newContent);

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			if (javaMethodContent.contains(
					"setDDMFormValuesSerializerTracker")) {

				newContent = StringUtil.removeSubstring(
					newContent, javaMethodContent + CharPool.NEW_LINE);

				continue;
			}

			Matcher serializerMatcher = _serializerPattern.matcher(
				javaMethodContent);

			if (!serializerMatcher.find()) {
				continue;
			}

			String newJavaMethodContent = StringUtil.removeSubstring(
				javaMethodContent, serializerMatcher.group());

			newJavaMethodContent = StringUtil.replace(
				newJavaMethodContent, new String[] {serializerMatcher.group(1)},
				new String[] {"_ddmFormValuesSerializer"}, true);

			newContent = StringUtil.replace(
				newContent, javaMethodContent, newJavaMethodContent);
		}

		String newSerializerReference =
			"private DDMFormValuesSerializer _ddmFormValuesSerializer;";

		if (newContent.contains("_ddmFormValuesSerializer") &&
			!newContent.contains(newSerializerReference)) {

			newContent = StringUtil.replaceLast(
				newContent, CharPool.CLOSE_CURLY_BRACE,
				StringBundler.concat(
					"\t@Reference(target = \"",
					"(ddm.form.values.serializer.type=json)\")\n\t",
					newSerializerReference, "\n\n}"));
		}

		return newContent;
	}

	private static final Pattern _serializerPattern = Pattern.compile(
		"\t+DDMFormValuesSerializer\\s+(\\w+)\\s+=\\s+.+;\\n");
	private static final Pattern _trackerReferencePattern = Pattern.compile(
		"(?:\\t+@Reference\\n)?\\t+[a-z]+\\s+" +
			"DDMFormValuesSerializerTracker\\s+_?\\w+;\\n");

}