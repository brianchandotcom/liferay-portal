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

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JavaModuleExtendedObjectClassDefinitionCheck
	extends BaseFileCheck {

	public JavaModuleExtendedObjectClassDefinitionCheck(boolean subrepository) {
		_subrepository = subrepository;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (absolutePath.contains("/test/") || !content.contains("@Meta.OCD") ||
			content.contains("@ExtendedObjectClassDefinition") ||
			!isModulesFile(absolutePath, _subrepository)) {

			return new Tuple(content, Collections.emptySet());
		}

		String packagePath = JavaSourceUtil.getPackagePath(content);

		if (!packagePath.startsWith("com.liferay")) {
			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		addMessage(
			sourceFormatterMessages, fileName,
			"Specify category using @ExtendedObjectClassDefinition, see " +
				"LPS-60186");

		return new Tuple(content, sourceFormatterMessages);
	}

	private final boolean _subrepository;

}