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

package com.liferay.arquillian.extension.junit.bridge.junit;

import com.liferay.arquillian.extension.junit.bridge.util.FrameworkMethodComparator;

import java.lang.annotation.Annotation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.rules.TestRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
public class Arquillian extends org.jboss.arquillian.junit.Arquillian {

	public Arquillian(Class<?> clazz)
		throws org.junit.runners.model.InitializationError {

		super(clazz);
	}

	@Override
	protected List<TestRule> classRules() {
		return Collections.emptyList();
	}

	@Override
	protected TestClass createTestClass(Class<?> testClass) {
		return new TestClass(testClass) {

			@Override
			public List<FrameworkMethod> getAnnotatedMethods(
				Class<? extends Annotation> annotationClass) {

				if ((annotationClass == AfterClass.class) ||
					(annotationClass == BeforeClass.class)) {

					return Collections.emptyList();
				}

				List<FrameworkMethod> frameworkMethods = new ArrayList<>(
					super.getAnnotatedMethods(annotationClass));

				Collections.sort(
					frameworkMethods, FrameworkMethodComparator.INSTANCE);

				return frameworkMethods;
			}

		};
	}

	@Override
	protected List<TestRule> getTestRules(Object target) {
		return Collections.emptyList();
	}

	@Override
	protected Statement withAfters(
		FrameworkMethod method, Object target, Statement statement) {

		return statement;
	}

	@Override
	protected Statement withBefores(
		FrameworkMethod method, Object target, Statement statement) {

		return statement;
	}

}