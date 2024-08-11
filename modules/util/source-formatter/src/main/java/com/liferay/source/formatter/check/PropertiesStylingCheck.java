/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Alan Huang
 * @author Hugo Huijser
 */
public class PropertiesStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = content.replaceAll(
			"(\n\n)((( *#+)( [^#\n]+)\n)+( *#))", "$1$4\n$2");

		content = content.replaceAll(
			"(\n\n)( *#+)(\n(\\2( [^#\n]+)\n)+)(?! *#)", "$1$2$3$2\n");

		content = content.replaceAll(
			"(\\A|(?<!\\\\)\n)( *[\\w.-]+)(( +=)|(= +))(.*)(\\Z|\n)",
			"$1$2=$6$7");

		content = content.replaceAll("(?m)^(.*,) +(\\\\)$", "$1$2");
		content = content.replaceAll(",\\\\(\n\n|\\Z)", "$1");

		return content;
	}

}