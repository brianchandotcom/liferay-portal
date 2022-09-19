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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Objects;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Rubén Pulido
 */
public class UpdateFragmentPortletSetsSortConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass()
		throws IllegalAccessException, InstantiationException,
			   InvocationTargetException, NoSuchMethodException {

		Class<?> clazz =
			UpdateFragmentPortletSetsSortConfigurationMVCActionCommand.class;

		Method[] methods = clazz.getDeclaredMethods();

		_mergeFragmentCollectionKeysMethod = null;

		for (Method method : methods) {
			if (Objects.equals(
					method.getName(), "_mergeFragmentCollectionKeys")) {

				_mergeFragmentCollectionKeysMethod = method;

				break;
			}
		}

		_mergeFragmentCollectionKeysMethod.setAccessible(true);

		Constructor<?> constructor = clazz.getConstructor();

		_updateFragmentPortletSetsSortConfigurationMVCActionCommand =
			constructor.newInstance();
	}

	private static Method _mergeFragmentCollectionKeysMethod;
	private static Object
		_updateFragmentPortletSetsSortConfigurationMVCActionCommand;

}