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

package com.liferay.portal.lar;

import com.liferay.portal.LARTypeException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingConstants;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.StagingLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class LayoutExportImportTest extends BaseExportImportTestCase {

	@Test
	public void testDeleteMissingLayouts() throws Exception {
		Layout layout1 = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());
		Layout layout2 = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		exportImportLayouts(null, getImportParameterMap());

		Assert.assertEquals(
			LayoutLocalServiceUtil.getLayoutsCount(group, false),
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));

		LayoutTestUtil.addLayout(
			importedGroup.getGroupId(), ServiceTestUtil.randomString());

		Map<String, String[]> parameterMap = getImportParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});

		long[] layoutIds = new long[] {layout1.getLayoutId()};

		exportImportLayouts(layoutIds, getImportParameterMap());

		Assert.assertEquals(
			LayoutLocalServiceUtil.getLayoutsCount(group, false),
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));

		Layout importedLayout1 =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layout1.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNotNull(importedLayout1);

		Layout importedLayout2 =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layout2.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNotNull(importedLayout2);
	}

	@Test
	@Transactional
	public void testExportImportCompanyGroupInvalidLARType() throws Exception {

		// Import a layout set to a company layout set

		Group originalImportedGroup = importedGroup;
		Group originalGroup = group;

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		importedGroup = company.getGroup();

		long[] layoutIds = new long[0];

		try {
			exportImportLayouts(layoutIds, getImportParameterMap());

			Assert.fail();
		}
		catch (LARTypeException lte) {
		}
		finally {
			importedGroup = originalImportedGroup;
		}

		// Import a company layout set to a layout set

		group = company.getGroup();
		importedGroup = originalGroup;

		try {
			exportImportLayouts(layoutIds, getImportParameterMap());

			Assert.fail();
		}
		catch (LARTypeException lte) {
		}
		finally {
			importedGroup = originalImportedGroup;
			group = originalGroup;
		}
	}

	@Test
	@Transactional
	public void testExportImportLayoutPrototypeInvalidLARType()
		throws Exception {

		// Import a layout prototype to a layout set

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			ServiceTestUtil.randomString());

		group = layoutPrototype.getGroup();
		importedGroup = GroupTestUtil.addGroup();

		long[] layoutIds = new long[0];

		try {
			exportImportLayouts(layoutIds, getImportParameterMap());

			Assert.fail();
		}
		catch (LARTypeException lte) {
		}

		// Import a layout prototype to a layout set pototype

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(
				ServiceTestUtil.randomString());

		importedGroup = layoutSetPrototype.getGroup();

		try {
			exportImportLayouts(layoutIds, getImportParameterMap());

			Assert.fail();
		}
		catch (LARTypeException lte) {
		}
	}

	@Test
	public void testExportImportLayouts() throws Exception {
		LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		long[] layoutIds = new long[0];

		exportImportLayouts(layoutIds, getImportParameterMap());

		Assert.assertEquals(
			LayoutLocalServiceUtil.getLayoutsCount(group, false),
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));
	}

	@Test
	@Transactional
	public void testExportImportLayoutSetInvalidLARType() throws Exception {

		// Import a layout set to a layout prototype

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			ServiceTestUtil.randomString());

		importedGroup = layoutPrototype.getGroup();

		long[] layoutIds = new long[0];

		try {
			exportImportLayouts(layoutIds, getImportParameterMap());

			Assert.fail();
		}
		catch (LARTypeException lte) {
		}

		// Import a layout set to a layout set prototype

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(
				ServiceTestUtil.randomString());

		importedGroup = layoutSetPrototype.getGroup();

		try {
			exportImportLayouts(layoutIds, getImportParameterMap());

			Assert.fail();
		}
		catch (LARTypeException lte) {
		}
	}

	@Test
	@Transactional
	public void testExportImportLayoutSetPrototypeInvalidLARType()
		throws Exception {

		// Import a layout set prototype to a layout set

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(
				ServiceTestUtil.randomString());

		group = layoutSetPrototype.getGroup();
		importedGroup = GroupTestUtil.addGroup();

		long[] layoutIds = new long[0];

		try {
			exportImportLayouts(layoutIds, getImportParameterMap());

			Assert.fail();
		}
		catch (LARTypeException lte) {
		}

		// Import a layout set prototype to a layout prototyope

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			ServiceTestUtil.randomString());

		importedGroup = layoutPrototype.getGroup();

		try {
			exportImportLayouts(layoutIds, getImportParameterMap());

			Assert.fail();
		}
		catch (LARTypeException lte) {
		}
	}

	@Test
	public void testExportImportLayoutsInvalidAvailableLocales()
		throws Exception {

		testAvailableLocales(
			new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN},
			new Locale[] {LocaleUtil.US, LocaleUtil.GERMANY}, true);
	}

	@Test
	public void testExportImportLayoutsValidAvailableLocales()
		throws Exception {

		testAvailableLocales(
			new Locale[] {LocaleUtil.US, LocaleUtil.US},
			new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.US},
			false);
	}

	@Test
	public void testExportImportSelectedLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		long[] layoutIds = new long[] {layout.getLayoutId()};

		exportImportLayouts(layoutIds, getImportParameterMap());

		Assert.assertEquals(
			layoutIds.length,
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));

		importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNotNull(importedLayout);
	}

	@Test
	public void testFriendlyURLCollision() throws Exception {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		Layout layoutA = LayoutTestUtil.addLayout(
			group.getGroupId(), "layoutA");

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getUserId(), layoutA.getPlid(), "/layoutA-de", "de");

		Layout layoutB = LayoutTestUtil.addLayout(
			group.getGroupId(), "layoutB");

		layoutB = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutB.getUserId(), layoutB.getPlid(), "/layoutB-de", "de");

		long[] layoutIds = {layoutA.getLayoutId(), layoutB.getLayoutId()};

		exportImportLayouts(layoutIds, getImportParameterMap());

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getUserId(), layoutA.getPlid(), "/temp", defaultLanguageId);

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getUserId(), layoutA.getPlid(), "/temp-de", "de");

		layoutB = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutB.getUserId(), layoutB.getPlid(), "/layoutA",
			defaultLanguageId);

		LayoutLocalServiceUtil.updateFriendlyURL(
			layoutB.getUserId(), layoutB.getPlid(), "/layoutA-de", "de");

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getUserId(), layoutA.getPlid(), "/layoutB",
			defaultLanguageId);

		LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getUserId(), layoutA.getPlid(), "/layoutB-de", "de");

		exportImportLayouts(layoutIds, getImportParameterMap());
	}

	@Test
	public void testGetPortletDataHandlerPortlets() throws Exception {
		LayoutTestUtil.addPortletToLayout(layout, PortletKeys.BOOKMARKS);
		LayoutTestUtil.addPortletToLayout(layout, PortletKeys.LOGIN);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setAttribute(
			StagingConstants.STAGED_PORTLET + PortletKeys.BOOKMARKS,
			Boolean.TRUE);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false, serviceContext);

		Group stagingGroup = group.getStagingGroup();

		Layout stagingLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			layout.getUuid(), stagingGroup.getGroupId(),
			layout.isPrivateLayout());

		List<Layout> layouts = new ArrayList<Layout>();

		layouts.add(stagingLayout);

		List<Portlet> portlets = LayoutExporter.getPortletDataHandlerPortlets(
			layouts);

		Assert.assertEquals(2, portlets.size());

		for (Portlet portlet : portlets) {
			String portletId = portlet.getPortletId();

			if (!portletId.equals(PortletKeys.BOOKMARKS) &&
				!portletId.equals(PortletKeys.LOGIN)) {

				Assert.fail();
			}
		}

		ServiceContextThreadLocal.popServiceContext();
	}

	protected void exportImportLayouts(
			long[] layoutIds, Map<String, String[]> parameterMap)
		throws Exception {

		larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(
			group.getGroupId(), false, layoutIds, getExportParameterMap(), null,
			null);

		LayoutLocalServiceUtil.importLayouts(
			TestPropsValues.getUserId(), importedGroup.getGroupId(), false,
			parameterMap, larFile);
	}

	protected void testAvailableLocales(
			Locale[] sourceAvailableLocales, Locale[] targetAvailableLocales,
			boolean expectFailure)
		throws Exception {

		group = GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), sourceAvailableLocales, null);
		importedGroup = GroupTestUtil.updateDisplaySettings(
			importedGroup.getGroupId(), targetAvailableLocales, null);

		LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		long[] layoutIds = new long[0];

		try {
			exportImportLayouts(layoutIds, getImportParameterMap());

			if (expectFailure) {
				Assert.fail();
			}
		}
		catch (LocaleException le) {
			if (!expectFailure) {
				Assert.fail();
			}
		}
	}

}