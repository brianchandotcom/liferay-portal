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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JSPModuleIllegalImportsCheck extends BaseFileCheck {

	public JSPModuleIllegalImportsCheck(boolean subrepository) {
		_subrepository = subrepository;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (!isModulesFile(absolutePath, _subrepository)) {
			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		// LPS-62989

		if (content.contains("import=\"com.liferay.registry.Registry")) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Do not use com.liferay.registry.Registry in modules, see " +
					"LPS-62989");
		}

		// LPS-64335

		if (content.contains("import=\"com.liferay.util.ContentUtil")) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Do not use com.liferay.util.ContentUtil in modules, see " +
					"LPS-64335");
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private final boolean _subrepository;

}