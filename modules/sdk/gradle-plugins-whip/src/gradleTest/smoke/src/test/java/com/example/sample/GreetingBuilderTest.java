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

package com.example.sample;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class GreetingBuilderTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		_greetingBuilder = new GreetingBuilder("World");
	}

	@Test
	public void testGetGoodbye() {
		Assert.assertEquals("Goodbye World", _greetingBuilder.getGoodbye());
	}

	@Test
	public void testGetHello() {
		Assert.assertEquals("Hello World", _greetingBuilder.getHello());
	}

	private GreetingBuilder _greetingBuilder;

}