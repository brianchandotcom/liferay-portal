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
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

import java.util.Collections;

/**
 * @author Hugo Huijser
 */
public class JSPEmptyLinesCheck extends EmptyLinesCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		content = fixMissingEmptyLines(content);

		content = fixRedundantEmptyLines(content);

		content = fixIncorrectEmptyLineBeforeCloseCurlyBrace(content);

		content = fixMissingEmptyLineAfterSettingVariable(content);

		return new Tuple(content, Collections.emptySet());
	}

	@Override
	protected boolean isJavaSource(String content, int pos) {
		return JSPSourceUtil.isJavaSource(content, pos);
	}

}