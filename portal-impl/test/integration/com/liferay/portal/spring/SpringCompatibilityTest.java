/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring;

import com.liferay.portal.util.BaseTestCase;

import java.lang.reflect.Field;

import java.util.Map;

import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Shuyang Zhou
 */
public class SpringCompatibilityTest extends BaseTestCase {

	public void testAbstractAutowireCapableBeanFactory() {
		Class<?> AbstractAutowireCapableBeanFactoryClass = null;

		try {
			AbstractAutowireCapableBeanFactoryClass = Class.forName(
				"org.springframework.beans.factory.support."
				+ "AbstractAutowireCapableBeanFactory");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		Field filteredPropertyDescriptorsCacheField = null;

		try {
			filteredPropertyDescriptorsCacheField =
				AbstractAutowireCapableBeanFactoryClass.getDeclaredField(
					"filteredPropertyDescriptorsCache");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		Class<?> filteredPropertyDescriptorsCacheClass =
			filteredPropertyDescriptorsCacheField.getType();

		if (!Map.class.isAssignableFrom(
			filteredPropertyDescriptorsCacheClass)) {
			fail(
				filteredPropertyDescriptorsCacheClass.getClass().getName() +
				" is not " + Map.class.getName());
		}
	}

	public void testAspectJExpressionPointcut() {
		Class<?> aspectJExpressionPointcutClass = null;

		try {
			aspectJExpressionPointcutClass = Class.forName(
				"org.springframework.aop.aspectj.AspectJExpressionPointcut");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		Field shadowMatchCacheField = null;

		try {
			shadowMatchCacheField =
				aspectJExpressionPointcutClass.getDeclaredField(
					"shadowMatchCache");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		Class<?> shadowMatchCacheClass = shadowMatchCacheField.getType();

		if (!Map.class.isAssignableFrom(shadowMatchCacheClass)) {
			fail(
				shadowMatchCacheClass.getClass().getName() + " is not " +
					Map.class.getName());
		}
	}

	public void testJdkDynamicAopProxy() {
		Class<?> jdkDynamicAopProxyClass = null;

		try {
			jdkDynamicAopProxyClass = Class.forName(
				"org.springframework.aop.framework.JdkDynamicAopProxy");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		Field advisedField = null;

		try {
			advisedField = jdkDynamicAopProxyClass.getDeclaredField("advised");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

		Class<?> advisedSupportClass = advisedField.getType();

		if (!advisedSupportClass.equals(AdvisedSupport.class)) {
			fail(
				advisedSupportClass.getClass().getName() + " is not " +
					AdvisedSupport.class.getName());
		}
	}

}