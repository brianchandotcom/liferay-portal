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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

import java.io.StringReader;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Hugo Huijser
 */
public class ClassUtilTest {

	@Test
	public void testGetClassesFromAnnotation() throws Exception {
		testGetClassesFromAnnotation("Annotation", "Annotation");
		testGetClassesFromAnnotation(
			"AnnotationClass.Annotation", "AnnotationClass");
	}

	@Test
	public void testGetClassesFromAnnotationsWithArrayParameter()
		throws Exception {

		testGetClassesFromAnnotation("Annotation", "Annotation", "A");
		testGetClassesFromAnnotation("Annotation", "Annotation", "A", "B");
		testGetClassesFromAnnotation("Annotation", "Annotation", "A", "B", "C");

		testGetClassesFromAnnotation(
			"AnnotationClass.Annotation", "AnnotationClass", "A");
		testGetClassesFromAnnotation(
			"AnnotationClass.Annotation", "AnnotationClass", "A", "B");
		testGetClassesFromAnnotation(
			"AnnotationClass.Annotation", "AnnotationClass", "A", "B", "C");
	}

	protected void testGetClassesFromAnnotation(
			String annotation, String expectedAnnotationClassName,
			String... arrayParameterClassNames)
		throws Exception {

		StringBundler sb = new StringBundler(
			arrayParameterClassNames.length * 3 + 2);

		sb.append(StringPool.AT);
		sb.append(annotation);

		if (arrayParameterClassNames.length > 0) {
			sb.append("({");

			for (int i = 0; i < arrayParameterClassNames.length; i++) {
				sb.append(arrayParameterClassNames[i]);
				sb.append(".class");

				if (i < (arrayParameterClassNames.length - 1)) {
					sb.append(StringPool.COMMA);
				}
			}

			sb.append("})");
		}

		Set<String> actualClassNames = ClassUtil.getClasses(
			new StringReader(sb.toString()), null);

		Set<String> expectedClassNames = new HashSet<>();

		expectedClassNames.add(expectedAnnotationClassName);

		Collections.addAll(expectedClassNames, arrayParameterClassNames);

		Assert.assertEquals(expectedClassNames, actualClassNames);
	}

}