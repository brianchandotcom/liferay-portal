/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.internal;

import com.example.sample.GreetingBuilder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class GreetingBuilderImplTest {

	@Test
	public void testGetHello() {
		GreetingBuilder greetingBuilder = new GreetingBuilderImpl("World");

		Assert.assertEquals("Hello World", greetingBuilder.getHello());
	}

}