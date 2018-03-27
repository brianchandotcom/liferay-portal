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

import com.liferay.source.formatter.parser.JavaTerm;

/**
 * @author Hugo Huijser
 */
public class JavaModuleComponentCheck extends BaseJavaTermCheck {

	@Override
	public boolean isModulesCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (javaTerm.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		if (javaTerm.hasAnnotation("Component") &&
			(absolutePath.contains("-api/") ||
			 absolutePath.contains("-spi/"))) {

			addMessage(
				fileName,
				"Do not use @Component in '-api' or '-spi' module");
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

}