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

package com.liferay.custom.elements.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.custom.elements.exception.CustomElementsSourceHTMLElementNameException;
import com.liferay.custom.elements.exception.DuplicateCustomElementsSourceException;
import com.liferay.custom.elements.service.CustomElementsSourceLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Arquillian.class)
public class CustomElementsSourceLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddCustomElementsSource() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		// No CustomElementSource exists with the same HTML element name

		try {
			CustomElementsSourceLocalServiceUtil.addCustomElementsSource(
				TestPropsValues.getUserId(), "vanilla-counter",
				"Vanilla Counter",
				"http://liferay.com/vanilla-counter/index.js", serviceContext);

			CustomElementsSourceLocalServiceUtil.addCustomElementsSource(
				TestPropsValues.getUserId(), "vanilla-counter",
				"Vanilla Counter",
				"http://liferay.com/vanilla-counter/index.js", serviceContext);

			Assert.fail();
		}
		catch (DuplicateCustomElementsSourceException
					duplicateCustomElementsSourceException) {

			Assert.assertEquals(
				"Duplicate HTML element name vanilla-counter",
				duplicateCustomElementsSourceException.getMessage());
		}

		// HTML element name is null

		try {
			CustomElementsSourceLocalServiceUtil.addCustomElementsSource(
				TestPropsValues.getUserId(), null, "Vanilla Counter",
				"http://liferay.com/vanilla-counter/index.js", serviceContext);

			Assert.fail();
		}
		catch (CustomElementsSourceHTMLElementNameException
					customElementsSourceHTMLElementNameException) {

			Assert.assertEquals(
				"HTML element name is null",
				customElementsSourceHTMLElementNameException.getMessage());
		}

		// HTML element name must not have uppercase letters

		try {
			CustomElementsSourceLocalServiceUtil.addCustomElementsSource(
				TestPropsValues.getUserId(), "vanilla-COUNTer",
				"Vanilla Counter",
				"http://liferay.com/vanilla-counter/index.js", serviceContext);

			Assert.fail();
		}
		catch (CustomElementsSourceHTMLElementNameException
					customElementsSourceHTMLElementNameException) {

			Assert.assertEquals(
				"HTML element name must only contain lowercase letters or " +
					"hyphens",
				customElementsSourceHTMLElementNameException.getMessage());
		}

		// HTML element name must start with lowercase letter

		try {
			CustomElementsSourceLocalServiceUtil.addCustomElementsSource(
				TestPropsValues.getUserId(), "Vanilla-counter",
				"Vanilla Counter",
				"http://liferay.com/vanilla-counter/index.js", serviceContext);

			Assert.fail();
		}
		catch (CustomElementsSourceHTMLElementNameException
					customElementsSourceHTMLElementNameException) {

			Assert.assertEquals(
				"HTML element name must start with a lowercase letter",
				customElementsSourceHTMLElementNameException.getMessage());
		}

		// HTML element name must contain at least one hyphen

		try {
			CustomElementsSourceLocalServiceUtil.addCustomElementsSource(
				TestPropsValues.getUserId(), "vanillacounter",
				"Vanilla Counter",
				"http://liferay.com/vanilla-counter/index.js", serviceContext);

			Assert.fail();
		}
		catch (CustomElementsSourceHTMLElementNameException
					customElementsSourceHTMLElementNameException) {

			Assert.assertEquals(
				"HTML element name must contain at least one hyphen",
				customElementsSourceHTMLElementNameException.getMessage());
		}
	}

}