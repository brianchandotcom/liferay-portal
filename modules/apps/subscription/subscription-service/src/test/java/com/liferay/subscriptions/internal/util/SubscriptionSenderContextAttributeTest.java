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

package com.liferay.subscriptions.internal.util;

import com.liferay.portal.kernel.util.EscapableObject;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class SubscriptionSenderContextAttributeTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAttributeEscapedContextAttributeIsAlwaysCreated() {
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setContextAttribute("[$X$]", "<>");
		subscriptionSender.setContextAttribute("[$Y$]", "<>", false);

		Assert.assertEquals(
			"&lt;&gt;",
			_getEscapedValue(
				subscriptionSender.getContextAttribute("[$X|attr$]")));
		Assert.assertEquals(
			"&lt;&gt;",
			_getEscapedValue(
				subscriptionSender.getContextAttribute("[$Y|attr$]")));
	}

	@Test
	public void testContextAttributesAreHtmlEscapedByDefault() {
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setContextAttribute("[$X$]", "<>");

		Assert.assertEquals(
			"&lt;&gt;",
			_getEscapedValue(subscriptionSender.getContextAttribute("[$X$]")));
	}

	@Test
	public void testContextAttributesPreserveValueIfNotEscaped() {
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setContextAttribute("[$X$]", "<>", false);

		Assert.assertEquals(
			"<>",
			_getEscapedValue(subscriptionSender.getContextAttribute("[$X$]")));
	}

	@Test
	public void testHtmlEscapedContextAttributeIsAlwaysCreated() {
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setContextAttribute("[$X$]", "<>");
		subscriptionSender.setContextAttribute("[$Y$]", "<>", false);

		Assert.assertEquals(
			"&lt;&gt;",
			_getEscapedValue(
				subscriptionSender.getContextAttribute("[$X|html$]")));
		Assert.assertEquals(
			"&lt;&gt;",
			_getEscapedValue(
				subscriptionSender.getContextAttribute("[$Y|html$]")));
	}

	@Test
	public void testUriEscapedContextAttributeIsAlwaysCreated() {
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setContextAttribute("[$X$]", "&");
		subscriptionSender.setContextAttribute("[$Y$]", "&", false);

		Assert.assertEquals(
			"%26",
			_getEscapedValue(
				subscriptionSender.getContextAttribute("[$X|uri$]")));
		Assert.assertEquals(
			"%26",
			_getEscapedValue(
				subscriptionSender.getContextAttribute("[$Y|uri$]")));
	}

	private String _getEscapedValue(Object object) {
		EscapableObject<?> escapableObject = (EscapableObject<?>)object;

		return escapableObject.getEscapedValue();
	}

}