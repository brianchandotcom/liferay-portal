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
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortletConstants;
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
public class LayoutExportImportTest extends BaseExportImportTestCase {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	@Transactional
	public void testLSPLinkDisabled() throws Exception {
		testLayoutSetPrototype(false, false, false, false, false);
	}

	@Test
	@Transactional
	public void testLSPLinkDisabledWithPageAddition() throws Exception {
		testLayoutSetPrototype(false, false, true, false, false);
	}

	@Test
	@Transactional
	public void testLSPLinkDisabledWithPageDeletion() throws Exception {
		testLayoutSetPrototype(false, false, true, true, false);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabled() throws Exception {
		testLayoutSetPrototype(true, false, false, false, false);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabledwithPageAddition() throws Exception {
		testLayoutSetPrototype(true, false, true, false, false);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabledwithPageAdditionFromLPLinkDisabled()
		throws Exception {

		testLayoutSetPrototype(true, false, true, false, true);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabledwithPageAdditionFromLPLinkEnabled()
		throws Exception {

		testLayoutSetPrototype(true, true, true, false, true);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabledwithPageDeletion() throws Exception {
		testLayoutSetPrototype(true, false, true, true, false);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabledwithPageDeletionFromLP() throws Exception {
		testLayoutSetPrototype(true, false, true, true, true);
	}

	protected void testLayoutSetPrototype(
			boolean layoutSetLinkEnabled, boolean layoutLinkEnabled,
			boolean addPage, boolean deletePage, boolean useLayoutPrototype)
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

		Group group = ServiceTestUtil.addGroup(ServiceTestUtil.randomString());

		SitesUtil.updateLayoutSetPrototypesLinks(
			group, layoutSetPrototype.getLayoutSetPrototypeId(), 0,
			layoutSetLinkEnabled, false);

		propagateChanges(group);

		int groupLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			group, false);

		Assert.assertEquals(
			groupLayoutsCount, layoutSetPrototypeLayoutsCount + 2);

		if (addPage) {
			Layout layout = null;

			if (useLayoutPrototype) {
				LayoutPrototype layoutPrototype =
					ServiceTestUtil.addLayoutPrototype(
						ServiceTestUtil.randomString());

				Layout layoutPrototypeLayout = layoutPrototype.getLayout();

				updateLayoutTemplateId(layoutPrototypeLayout, "2_2_columns");

				layout = addLayout(
					group.getGroupId(), ServiceTestUtil.randomString(),
					layoutPrototype, layoutLinkEnabled);

				if (layoutLinkEnabled) {
					propagateChanges(layout);
				}

				updateLayoutTemplateId(layoutPrototypeLayout, "1_column");

				if (layoutLinkEnabled) {
					Assert.assertEquals(
						"2_2_columns",
						layout.getTypeSettingsProperty(
							LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));

					propagateChanges(layout);
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
							"1_column",
							layout.getTypeSettingsProperty(
								LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));
					}
					else {
						Assert.assertEquals(
							"2_2_columns",
							layout.getTypeSettingsProperty(
								LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));
					}
				}
			}

			if (deletePage) {
				LayoutLocalServiceUtil.deleteLayout(
					layout.getPlid(), ServiceTestUtil.getServiceContext());

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