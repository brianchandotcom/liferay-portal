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

package com.liferay.portal.security.exportimport;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class UserExporterUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.userexporterutil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Before
	public void setUp() {
		_atomicState.reset();
	}

	@After
	public void tearDown() {
		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testExportUser1() throws Exception {
		UserExporterUtil.exportUser(new ContactImpl(), null);
	}

	@Test
	public void testExportUser2() throws Exception {
		UserExporterUtil.exportUser(1, 1, null);
	}

	@Test
	public void testExportUser3() throws Exception {
		UserExporterUtil.exportUser(new UserImpl(), null);
	}

	private static AtomicState _atomicState;

}