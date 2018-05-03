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

package com.liferay.document.library.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.repository.registry.RepositoryClassDefinition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinitionUtil {

	public static final String getRepositoryClassDefinitionId(
		RepositoryClassDefinition repositoryClassDefinition) {

		String className = repositoryClassDefinition.getClassName();

		Matcher matcher = _pattern.matcher(className);

		return matcher.replaceAll(StringPool.DASH);
	}

	private static final Pattern _pattern = Pattern.compile("\\W");

}