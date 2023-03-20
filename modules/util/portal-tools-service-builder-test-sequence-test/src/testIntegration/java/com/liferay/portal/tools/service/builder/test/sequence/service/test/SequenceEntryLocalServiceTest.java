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

package com.liferay.portal.tools.service.builder.test.sequence.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.sequence.service.SequenceEntryLocalService;

import java.io.InputStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Hai.Yu
 */
@RunWith(Arquillian.class)
public class SequenceEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		Assume.assumeTrue(
			(dbType == DBType.DB2) || (dbType == DBType.ORACLE) ||
			(dbType == DBType.POSTGRESQL));
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			SequenceEntryLocalServiceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		String location =
			"/com.liferay.portal.tools.service.builder.test.sequence.service." +
				"jar";

		try (InputStream inputStream =
				SequenceEntryLocalServiceTest.class.getResourceAsStream(
					location)) {

			_serviceBundle = bundleContext.installBundle(location, inputStream);
		}

		_serviceBundle.start();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceBundle.uninstall();
	}

	@Test
	public void testAddSequenceEntry() {
		Assert.assertNotNull(
			_sequenceEntryLocalService.addSequenceEntry(
				_sequenceEntryLocalService.createSequenceEntry(0)));
	}

	private static Bundle _serviceBundle;

	@Inject
	private SequenceEntryLocalService _sequenceEntryLocalService;

}