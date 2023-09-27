/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class GreetingBuilderTest {

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