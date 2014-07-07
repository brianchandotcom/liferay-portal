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

import java.io.StringReader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class ClassUtilGetClassesAnnotationsTest {

	@Test
	public void testArrayParameter1Element() throws Exception {
		testArrayParameter("A");
	}

	@Ignore
	@Test
	public void testArrayParameter2Elements() throws Exception {
		testArrayParameter("A", "B");
	}

	@Ignore
	@Test
	public void testArrayParameter3Elements() throws Exception {
		testArrayParameter("A", "B", "C");
	}

	protected void testArrayParameter(String... classNames) throws Exception {
		List<String> classNamesList = Arrays.asList(classNames);

		String parameters = classNamesList.get(0) + ".class";

		for (int i = 1; i < classNamesList.size(); i++) {
			parameters += ", " + classNamesList.get(i) + ".class";
		}

		String source = "@AnnotationWithArrayParameter({" + parameters + "})";

		HashSet<String> classes = new HashSet<String>(
			ClassUtil.getClasses(new StringReader(source), null));

		HashSet<String> expectedClasses = new HashSet<String>();
		expectedClasses.add("AnnotationWithArrayParameter");
		expectedClasses.addAll(classNamesList);

		Assert.assertEquals(expectedClasses, classes);
	}

}