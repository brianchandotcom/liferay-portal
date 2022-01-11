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

package com.liferay.source.formatter.checks;

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