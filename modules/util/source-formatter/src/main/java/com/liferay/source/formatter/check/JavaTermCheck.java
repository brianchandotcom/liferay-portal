/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.processor.SourceProcessor;

/**
 * @author Hugo Huijser
 */
public interface JavaTermCheck extends SourceCheck {

	public String process(
			SourceProcessor sourceProcessor, String fileName,
			String absolutePath, JavaClass javaClass, String content)
		throws Exception;

}