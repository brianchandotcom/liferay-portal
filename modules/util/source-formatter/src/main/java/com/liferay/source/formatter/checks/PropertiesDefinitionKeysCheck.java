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

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.Tuple;

import java.util.Collections;

/**
 * @author Hugo Huijser
 */
public class PropertiesDefinitionKeysCheck extends DefinitionKeysCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("/liferay-plugin-package.properties")) {
			content = sortDefinitionKeys(
				content, getDefinitions(content),
				new NaturalOrderStringComparator());
		}

		return new Tuple(content, Collections.emptySet());
	}

}