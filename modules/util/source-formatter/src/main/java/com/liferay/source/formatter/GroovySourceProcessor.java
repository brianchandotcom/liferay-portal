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

package com.liferay.source.formatter;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class GroovySourceProcessor extends JavaSourceProcessor {

	@Override
	public String[] getIncludes() {
		return _INCLUDES;
	}

	@Override
	protected void checkInefficientStringMethods(
		String line, String fileName, String absolutePath, int lineCount,
		boolean javaSource) {
	}

	@Override
	protected void checkPackagePath(String fileName, String packagePath) {
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		if (!portalSource && !subrepository) {
			return new ArrayList<>();
		}

		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String fixCopyright(
			String content, String absolutePath, String fileName,
			String className)
		throws IOException {

		if (Character.isUpperCase(className.charAt(0))) {
			return super.fixCopyright(
				content, absolutePath, fileName, className);
		}

		return content;
	}

	@Override
	protected void postFormat() throws Exception {
	}

	private static final String[] _INCLUDES = new String[] {"**/*.groovy"};

}