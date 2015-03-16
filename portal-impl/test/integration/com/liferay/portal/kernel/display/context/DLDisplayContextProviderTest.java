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

package com.liferay.portal.kernel.display.context;

import com.liferay.portal.kernel.display.context.bundle.basedisplaycontextprovider.TestDLDisplayContextFactoryImpl;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.documentlibrary.display.context.DLDisplayContextFactory;
import com.liferay.portlet.documentlibrary.display.context.DLEditFileEntryDisplayContext;
import com.liferay.portlet.documentlibrary.display.context.DLViewFileVersionDisplayContext;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Manuel de la Peña
 */
public class DLDisplayContextProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.basedisplaycontextprovider"));

	@BeforeClass
	public static void setUpClass() throws Exception {
		_baseDisplayContextProvider = new BaseDisplayContextProvider(
			DLDisplayContextFactory.class);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_baseDisplayContextProvider.close();
	}

	@Test
	public void testDisplayContextHasBeenOverriden() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		DisplayContextFactory service = registry.getService(
			DLDisplayContextFactory.class);

		Assert.assertEquals(
			TestDLDisplayContextFactoryImpl.class.getName(),
			service.getClass().getName());
	}

	@Test
	public void testDisplayContextMethodsHasBeenOverriden() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		DisplayContextFactory service = registry.getService(
			DLDisplayContextFactory.class);

		DLDisplayContextFactory dlDisplayContextFactory =
			(DLDisplayContextFactory)service;

		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext =
			null;

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			dlDisplayContextFactory.getDLEditFileEntryDisplayContext(
				parentDLEditFileEntryDisplayContext,
				new MockHttpServletRequest(), new MockHttpServletResponse(),
				new DLFileEntryTypeImpl());

		Assert.assertNull(dlEditFileEntryDisplayContext);

		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext =
			null;

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			dlDisplayContextFactory.getDLViewFileVersionDisplayContext(
				parentDLViewFileVersionDisplayContext,
				new MockHttpServletRequest(), new MockHttpServletResponse(),
				new DLFileShortcutImpl());

		Assert.assertNull(dlViewFileVersionDisplayContext);
	}

	private static BaseDisplayContextProvider _baseDisplayContextProvider;

}