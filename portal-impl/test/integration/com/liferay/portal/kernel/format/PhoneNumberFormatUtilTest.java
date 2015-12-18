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

package com.liferay.portal.kernel.format;

import com.liferay.portal.kernel.format.bundle.phonenumberformatutil.TestPhoneNumberFormat;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class PhoneNumberFormatUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.phonenumberformatutil"));

	@Test
	public void testFormat() {
		Assert.assertEquals(
			TestPhoneNumberFormat.FORMATTED,
			PhoneNumberFormatUtil.format(TestPhoneNumberFormat.UNFORMATTED));
	}

	@Test
	public void testGetPhoneNumberFormat() {
		PhoneNumberFormat phoneNumberFormat =
			PhoneNumberFormatUtil.getPhoneNumberFormat();

		Class<?> clazz = phoneNumberFormat.getClass();

		Assert.assertEquals(
			TestPhoneNumberFormat.class.getName(), clazz.getName());
	}

	@Test
	public void testStrip() {
		Assert.assertEquals(
			TestPhoneNumberFormat.UNFORMATTED,
			PhoneNumberFormatUtil.strip(TestPhoneNumberFormat.FORMATTED));
	}

	@Test
	public void testValidate() {
		Assert.assertTrue(
			PhoneNumberFormatUtil.validate(TestPhoneNumberFormat.FORMATTED));
		Assert.assertFalse(
			PhoneNumberFormatUtil.validate(TestPhoneNumberFormat.UNFORMATTED));
	}

}