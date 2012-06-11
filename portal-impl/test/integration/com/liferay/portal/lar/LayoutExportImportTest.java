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

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
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
	public void testLSPLinkDisabled() throws Exception {
		_testLayoutSetPrototype(false, false, false, false, false);
	}

	@Test
	@Transactional
	public void testLSPLinkDisabledWithPageAddition() throws Exception {
		_testLayoutSetPrototype(false, false, true, false, false);
	}

	@Test
	@Transactional
	public void testLSPLinkDisabledWithPageDeletion() throws Exception {
		_testLayoutSetPrototype(false, false, true, true, false);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabled() throws Exception {
		_testLayoutSetPrototype(true, false, false, false, false);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabledwithPageAddition() throws Exception {
		_testLayoutSetPrototype(true, false, true, false, false);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabledwithPageAdditionFromLPLinkDisabled()
		throws Exception {

		_testLayoutSetPrototype(true, false, true, false, true);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabledwithPageAdditionFromLPLinkEnabled()
		throws Exception {

		_testLayoutSetPrototype(true, true, true, false, true);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabledwithPageDeletion() throws Exception {
		_testLayoutSetPrototype(true, false, true, true, false);
	}

	@Test
	@Transactional
	public void testLSPLinkEnabledwithPageDeletionFromLP() throws Exception {
		_testLayoutSetPrototype(true, false, true, true, true);
	}

	private Layout _addLayoutUsingLayoutPrototype(
			long groupId, String name, LayoutPrototype layoutPrototype,
			boolean linkEnabled)
		throws Exception {

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				groupId, false, friendlyURL);

			return layout;
		}
		catch (NoSuchLayoutException nsle) {
		}

		String description = "This is a test page.";

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setAttribute("layoutPrototypeLinkEnabled", linkEnabled);
		serviceContext.setAttribute(
			"layoutPrototypeUuid", layoutPrototype.getUuid());

		return LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, null, description,
			LayoutConstants.TYPE_PORTLET, false, friendlyURL, serviceContext);
	}

	private Layout _changeLayoutTemplateId(
		Layout layout, String layoutTemplateId) throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			TestPropsValues.getUserId(), layoutTemplateId);

		return LayoutServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	private void _propagateChanges(Group group) throws Exception {
		LayoutLocalServiceUtil.getLayouts(
			group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	private void _propagateChanges(Layout layout) throws Exception {
		LayoutLocalServiceUtil.getLayout(layout.getPlid());
	}

	private void _testLayoutSetPrototype(
			boolean layoutSetLinkEnabled, boolean layoutLinkEnabled,
			boolean pageAddition, boolean pageDeletion,
			boolean useLayoutPrototype)
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
			site, simple.getLayoutSetPrototypeId(), 0, layoutSetLinkEnabled,
			false);

		_propagateChanges(site);

		int sitePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
			site, false);

		Assert.assertEquals(sitePagesCount, layoutSetPrototypePagesCount + 2);

		if (pageAddition) {
			Layout page = null;

			if (useLayoutPrototype) {
				LayoutPrototype simplePage = ServiceTestUtil.addLayoutPrototype(
					"Simple Page");

				Layout simplePageLayout = simplePage.getLayout();

				_changeLayoutTemplateId(simplePageLayout, "2_2_columns");

				page = _addLayoutUsingLayoutPrototype(
					site.getGroupId(), "three", simplePage, layoutLinkEnabled);

				if (layoutLinkEnabled) {
					_propagateChanges(page);
				}

				_changeLayoutTemplateId(simplePageLayout, "1_column");

				if (layoutLinkEnabled) {
					Assert.assertEquals(
						"2_2_columns",
						page.getTypeSettingsProperty(
							LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));

					_propagateChanges(page);
				}
			}
			else {
				page = ServiceTestUtil.addLayout(
					layoutsetprototypegroup.getGroupId(), "three", true);
			}

			if (!useLayoutPrototype) {
				sitePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
					site, false);

				Assert.assertEquals(
					sitePagesCount, layoutSetPrototypePagesCount + 2);
			}

			_propagateChanges(site);

			sitePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
				site, false);

			if (layoutSetLinkEnabled) {
				Assert.assertEquals(
					sitePagesCount, layoutSetPrototypePagesCount + 3);

				if (useLayoutPrototype) {
					if (layoutLinkEnabled) {
						Assert.assertEquals(
							"1_column",
							page.getTypeSettingsProperty(
								LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));
					}
					else {
						Assert.assertEquals(
							"2_2_columns",
							page.getTypeSettingsProperty(
								LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));
					}
				}
			}

			if (pageDeletion) {
				LayoutLocalServiceUtil.deleteLayout(
					page.getPlid(), ServiceTestUtil.getServiceContext());

				sitePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
					site, false);

				if (layoutSetLinkEnabled) {
					if (!useLayoutPrototype) {
						Assert.assertEquals(
							sitePagesCount, layoutSetPrototypePagesCount + 3);

						_propagateChanges(site);
					}

					sitePagesCount = LayoutLocalServiceUtil.getLayoutsCount(
						site, false);
				}

				Assert.assertEquals(
					sitePagesCount, layoutSetPrototypePagesCount + 2);
			}
		}
	}

}