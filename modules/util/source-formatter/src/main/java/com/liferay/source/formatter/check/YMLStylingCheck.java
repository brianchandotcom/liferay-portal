/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Alan Huang
 */
public class YMLStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (content.startsWith("---\n")) {
			content = content.substring(4);
		}

		content = content.replaceAll(
			"(\\A|\n)( *)(description:) (?!\\|-)(.+)(\\Z|\n)",
			"$1$2$3\n    $2$4$5");

		content = content.replaceAll("(\\A|\n) *description:\n +\"\"", "");

		content = content.replaceAll(
			"(\\A|\n)( *#)@? ?(review)(\\Z|\n)", "$1$2 @$3$4");

		content = content.replaceAll(
			"(\\A|\n)(( *)|(.+: ))'([^'\"]*)'(\\Z|\n)", "$1$2\"$5\"$6");

		content = content.replaceAll(
			"(\\A|\n)( *)'([^'\"]+)'(:.*)(\\Z|\n)", "$1$2\"$3\"$4$5");

		if (fileName.endsWith("/rest-config.yaml")) {
			content = content.replaceAll(
				"(\\A|\n)( *baseURI: ((['\"](?!/))|(?!['\"/])))(.*)",
				"$1$2/$5");
		}

		return content;
	}

}