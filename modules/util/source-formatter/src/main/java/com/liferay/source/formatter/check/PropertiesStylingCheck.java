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

		return content.replaceAll("(?m)^(.*,) +(\\\\)$", "$1$2");
	}

}