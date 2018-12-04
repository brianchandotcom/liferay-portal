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

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Peter Shin
 */
public class JavaSpringFrameworkAnnotationsCheck extends JavaAnnotationsCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		if (!fileContent.contains("org.springframework.web.bind.annotation.")) {
			return javaTerm.getContent();
		}

		return formatAnnotations(fileName, (JavaClass)javaTerm, false);
	}

	@Override
	protected String formatAnnotation(
		String fileName, JavaClass javaClass, String annotation,
		String indent) {

		if (!annotation.contains("@DeleteMapping") &&
			!annotation.contains("@GetMapping") &&
			!annotation.contains("@PatchMapping") &&
			!annotation.contains("@PostMapping") &&
			!annotation.contains("@PutMapping")) {

			return annotation;
		}

		if (StringUtils.countMatches(annotation, "\"") != 2) {
			return annotation;
		}

		if (annotation.contains("(\"\")")) {
			return annotation.replace("(\"\")", "");
		}

		if (StringUtils.countMatches(annotation, "=") != 1) {
			return annotation;
		}

		int x = annotation.indexOf("(value =");

		int y = annotation.indexOf("\"", x);

		if ((x == -1) || (y == -1)) {
			return annotation;
		}

		return annotation.substring(0, x + 1).concat(annotation.substring(y));
	}

}