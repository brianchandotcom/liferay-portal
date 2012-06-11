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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portlet.sites.util.SitesUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Julio Camarero
 */
@PrepareForTest({PortletLocalServiceUtil.class})

@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LayoutExportImportTest extends PowerMockito {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	@Transactional
	public void testLayoutSetPrototypeLinkDisabled() throws Exception {
		_testLayoutSetPrototype(false, false, false);
	}

	@Test
	@Transactional
	public void testLayoutSetPrototypeLinkDisabledWithPageAddition()
		throws Exception {

		_testLayoutSetPrototype(false, true, false);
	}

	@Test
	@Transactional
	public void testLayoutSetPrototypeLinkDisabledWithPageDeletion()
		throws Exception {

		_testLayoutSetPrototype(false, true, true);
	}

	@Test
	@Transactional
	public void testLayoutSetPrototypeLinkEnabled() throws Exception {
		_testLayoutSetPrototype(true, false, false);
	}

	@Test
	@Transactional
	public void testLayoutSetPrototypeLinkEnabledwithPageAddition()
		throws Exception {

		_testLayoutSetPrototype(true, true, false);
	}

	@Test
	@Transactional
	public void testLayoutSetPrototypeLinkEnabledwithPageDeletion()
		throws Exception {

		_testLayoutSetPrototype(true, true, true);
	}

	private void _propagateChanges(Group group) throws Exception {
		LayoutLocalServiceUtil.getLayouts(
			group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	private void _testLayoutSetPrototype(
			boolean linkEnabled, boolean pageAddition, boolean pageDeletion)
		throws Exception {

		LayoutSetPrototype simple = ServiceTestUtil.addLayoutSetPrototype(
			"simple");

		Group layoutsetprototypegroup = simple.getGroup();

		int layoutSetPrototypePagesCount =
			LayoutLocalServiceUtil.getLayoutsCount(
				layoutsetprototypegroup, true);

		ServiceTestUtil.addLayout(
			layoutsetprototypegroup.getGroupId(), "one", true);
		ServiceTestUtil.addLayout(
			layoutsetprototypegroup.getGroupId(), "two", true);

		Group site = ServiceTestUtil.addGroup("group-one");

		SitesUtil.updateLayoutSetPrototypesLinks(
			site, simple.getLayoutSetPrototypeId(), 0, linkEnabled, false);

		_propagateChanges(site);

		int sitePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
			site, false);

		Assert.assertEquals(sitePagesCount, layoutSetPrototypePagesCount + 2);

		if (pageAddition) {
			Layout page = ServiceTestUtil.addLayout(
				layoutsetprototypegroup.getGroupId(), "three", true);

			sitePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
				site, false);

			Assert.assertEquals(
				sitePagesCount, layoutSetPrototypePagesCount + 2);

			_propagateChanges(site);

			sitePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
				site, false);

			if (linkEnabled) {
				Assert.assertEquals(
					sitePagesCount, layoutSetPrototypePagesCount + 3);
			}

			if (pageDeletion) {
				LayoutLocalServiceUtil.deleteLayout(
					page.getPlid(), ServiceTestUtil.getServiceContext());

				sitePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
					site, false);

				if (linkEnabled) {
					Assert.assertEquals(
						sitePagesCount, layoutSetPrototypePagesCount + 3);

					_propagateChanges(site);

					sitePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
						site, false);
				}

				Assert.assertEquals(
					sitePagesCount, layoutSetPrototypePagesCount + 2);
			}
		}
	}

}