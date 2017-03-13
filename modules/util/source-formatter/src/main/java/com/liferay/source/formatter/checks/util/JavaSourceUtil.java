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

package com.liferay.source.formatter.checks.util;


/**
 * @author Hugo Huijser
 */
public class JavaSourceUtil extends SourceUtil {

	public static boolean isValidJavaParameter(String javaParameter) {
		if (javaParameter.contains(" implements ") ||
			javaParameter.contains(" throws ")) {

			return false;
		}

		if ((getLevel(javaParameter, "(", ")") == 0) &&
			(getLevel(javaParameter, "<", ">") == 0) &&
			(getLevel(javaParameter, "{", "}") == 0)) {

			return true;
		}

		return false;
	}

}