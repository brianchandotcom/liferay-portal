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

package com.liferay.portal.configuration.metatype.util;

/**
 * @author Jorge Ferrer
 */
public class ParameterMapUtilTestUtil {

	public static final String PARAMETER_MAP_STRING = "PARAMETER_MAP";

	public static final String[] PARAMETER_MAP_STRING_ARRAY = {
		"PARAMETER_MAP1", "PARAMETER_MAP2"
	};

	public static final String TEST_BEAN_STRING = "TEST_BEAN";

	public static final String[] TEST_BEAN_STRING_ARRAY = {
		"TEST_BEAN1", "TEST_BEAN2"
	};

	protected static TestBean getTestBean() {
		return new TestBean() {

			@Override
			public boolean testBoolean1() {
				return true;
			}

			@Override
			public boolean testBoolean2() {
				return true;
			}

			@Override
			public String testString1() {
				return TEST_BEAN_STRING;
			}

			@Override
			public String testString2() {
				return TEST_BEAN_STRING;
			}

			@Override
			public String[] testStringArray1() {
				return TEST_BEAN_STRING_ARRAY;
			}

			@Override
			public String[] testStringArray2() {
				return TEST_BEAN_STRING_ARRAY;
			}

		};
	}

	protected interface TestBean {

		public boolean testBoolean1();

		public boolean testBoolean2();

		public String testString1();

		public String testString2();

		public String[] testStringArray1();

		public String[] testStringArray2();

	}

}