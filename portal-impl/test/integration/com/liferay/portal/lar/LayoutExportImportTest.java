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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portlet.sites.util.SitesUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
@Transactional
public class LayoutExportImportTest extends BaseExportImportTestCase {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testLSPLinkDisabled() throws Exception {
		runLayoutSetPrototype(false, false, false, false, false, false);
	}

	@Test
	public void testLSPLinkDisabledWithPageAddition() throws Exception {
		runLayoutSetPrototype(false, false, true, false, false, false);
	}

	@Test
	public void testLSPLinkDisabledWithPageDeletion() throws Exception {
		runLayoutSetPrototype(false, false, true, true, false, false);
	}

	@Test
	public void testLSPLinkEnabled() throws Exception {
		runLayoutSetPrototype(true, false, false, false, false, false);
	}

	@Test
	public void testLSPLinkEnabledWithPageAddition() throws Exception {
		runLayoutSetPrototype(true, false, true, false, false, false);
	}

	@Test
	public void testLSPLinkEnabledWithPageAdditionFromLPLinkDisabled()
		throws Exception {

		runLayoutSetPrototype(true, false, true, false, true, false);
	}

	@Test
	public void testLSPLinkEnabledWithPageAdditionFromLPLinkEnabled()
		throws Exception {

		runLayoutSetPrototype(true, true, true, false, true, false);
	}

	@Test
	public void testLSPLinkEnabledWithPageAdditionFromLPToLSPLinkDisabled()
		throws Exception {

		runLayoutSetPrototype(true, false, true, false, true, true);
	}

	@Test
	public void testLSPLinkEnabledWithPageAdditionFromLPToLSPLinkEnabled()
		throws Exception {

		runLayoutSetPrototype(true, true, true, false, true, true);
	}

	@Test
	public void testLSPLinkEnabledWithPageDeletion() throws Exception {
		runLayoutSetPrototype(true, false, true, true, false, false);
	}

	@Test
	public void testLSPLinkEnabledWithPageDeletionFromLP() throws Exception {
		runLayoutSetPrototype(true, false, true, true, true, false);
	}

	@Test
	public void testLSPLinkEnabledWithPageDeletionFromLPToLSP()
		throws Exception {

		runLayoutSetPrototype(true, false, true, true, true, true);
	}

	protected void runLayoutSetPrototype(
			boolean layoutSetLinkEnabled, boolean layoutLinkEnabled,
			boolean addPage, boolean deletePage, boolean useLayoutPrototype,
			boolean layoutPrototypeToLayoutSetPrototype)
		throws Exception {

		LayoutSetPrototype layoutSetPrototype =
			ServiceTestUtil.addLayoutSetPrototype(
				ServiceTestUtil.randomString());

		Group layoutSetPrototypeGroup = layoutSetPrototype.getGroup();

		int layoutSetPrototypeLayoutsCount =
			LayoutLocalServiceUtil.getLayoutsCount(
				layoutSetPrototypeGroup, true);

		ServiceTestUtil.addLayout(
			layoutSetPrototypeGroup.getGroupId(),
			ServiceTestUtil.randomString(), true);
		ServiceTestUtil.addLayout(
			layoutSetPrototypeGroup.getGroupId(),
			ServiceTestUtil.randomString(), true);

		Group group = ServiceTestUtil.addGroup();

		SitesUtil.updateLayoutSetPrototypesLinks(
			group, layoutSetPrototype.getLayoutSetPrototypeId(), 0,
			layoutSetLinkEnabled, false);

		propagateChanges(group);

		int groupLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			group, false);

		Assert.assertEquals(
			groupLayoutsCount, layoutSetPrototypeLayoutsCount + 2);

		if (addPage) {
			if (!useLayoutPrototype || layoutPrototypeToLayoutSetPrototype) {

				// Database will store Date values without milliseconds. Wait
				// for more than one second to ensure that later queries can
				// correctly compare the Date values.

				Thread.sleep(2000);
			}

			Layout layout = null;

			if (useLayoutPrototype) {
				LayoutPrototype layoutPrototype =
					ServiceTestUtil.addLayoutPrototype(
						ServiceTestUtil.randomString());

				Layout layoutPrototypeLayout = layoutPrototype.getLayout();

				updateLayoutTemplateId(layoutPrototypeLayout, "2_2_columns");

				if (layoutPrototypeToLayoutSetPrototype) {
					Layout layoutSetPrototypeLayout = ServiceTestUtil.addLayout(
						layoutSetPrototypeGroup.getGroupId(),
						ServiceTestUtil.randomString(), true, layoutPrototype,
						layoutLinkEnabled);

					propagateChanges(group);

					layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
						group.getGroupId(), false,
						layoutSetPrototypeLayout.getFriendlyURL());
				}
				else {
					layout = ServiceTestUtil.addLayout(
						group.getGroupId(), ServiceTestUtil.randomString(),
						false, layoutPrototype, layoutLinkEnabled);
				}

				if (layoutLinkEnabled) {
					layout = propagateChanges(layout);
				}

				updateLayoutTemplateId(layoutPrototypeLayout, "1_column");

				if (layoutLinkEnabled) {
					Assert.assertEquals(
						"2_2_columns", getLayoutTemplateId(layout));

					layout = propagateChanges(layout);
				}
			}
			else {
				layout = ServiceTestUtil.addLayout(
					layoutSetPrototypeGroup.getGroupId(),
					ServiceTestUtil.randomString(), true);
			}

			if (!useLayoutPrototype) {
				groupLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
					group, false);

				Assert.assertEquals(
					groupLayoutsCount, layoutSetPrototypeLayoutsCount + 2);
			}

			propagateChanges(group);

			groupLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
				group, false);

			if (layoutSetLinkEnabled) {
				Assert.assertEquals(
					groupLayoutsCount, layoutSetPrototypeLayoutsCount + 3);

				if (useLayoutPrototype) {
					if (layoutLinkEnabled) {
						Assert.assertEquals(
							"1_column", getLayoutTemplateId(layout));
					}
					else {
						Assert.assertEquals(
							"2_2_columns", getLayoutTemplateId(layout));
					}
				}
			}

			if (deletePage) {
				LayoutLocalServiceUtil.deleteLayout(
					layout, true, ServiceTestUtil.getServiceContext());

				groupLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
					group, false);

				if (layoutSetLinkEnabled) {
					if (!useLayoutPrototype) {
						Assert.assertEquals(
							groupLayoutsCount,
							layoutSetPrototypeLayoutsCount + 3);

						propagateChanges(group);
					}

					groupLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
						group, false);
				}

				Assert.assertEquals(
					groupLayoutsCount, layoutSetPrototypeLayoutsCount + 2);
			}
		}
	}

}