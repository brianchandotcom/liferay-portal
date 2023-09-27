/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static String getRepositoryClassDefinitionId(
		RepositoryClassDefinition repositoryClassDefinition) {

		Matcher matcher = _pattern.matcher(
			repositoryClassDefinition.getClassName());

		return matcher.replaceAll(StringPool.DASH);
	}

	private static final Pattern _pattern = Pattern.compile("\\W");

}