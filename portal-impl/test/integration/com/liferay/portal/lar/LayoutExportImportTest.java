/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.TestPropsValues;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class LayoutExportImportTest extends BaseExportImportTestCase {

	@Test
	public void testExportImportLayouts() throws Exception {
		LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		long[] layoutIds = new long[0];

		exportImportLayouts(layoutIds);

		Assert.assertEquals(
			LayoutLocalServiceUtil.getLayoutsCount(group, false),
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));
	}

	@Test
	public void testExportImportSelectedLayouts() throws Exception {
		Layout layout2 = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		long[] layoutIds = new long[] {layout2.getLayoutId()};

		exportImportLayouts(layoutIds);

		Assert.assertEquals(
			layoutIds.length,
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));

		importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout2.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNotNull(importedLayout);
	}

	@Test
	public void testFriendlyURLCollision() throws Exception {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		Layout layoutA = LayoutTestUtil.addLayout(
			group.getGroupId(), "layoutA");

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getPlid(), "/layoutA-de", "de");

		Layout layoutB = LayoutTestUtil.addLayout(
			group.getGroupId(), "layoutB");

		layoutB = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutB.getPlid(), "/layoutB-de", "de");

		long[] layoutIds = new long[] {
			layoutA.getLayoutId(), layoutB.getLayoutId()};

		exportImportLayouts(layoutIds);

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getPlid(), "/temp", defaultLanguageId);

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getPlid(), "/temp-de", "de");

		layoutB = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutB.getPlid(), "/layoutA", defaultLanguageId);

		layoutB = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutB.getPlid(), "/layoutA-de", "de");

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getPlid(), "/layoutB", defaultLanguageId);

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getPlid(), "/layoutB-de", "de");

		exportImportLayouts(layoutIds);
	}

	protected void exportImportLayouts(long[] layoutIds) throws Exception {
		larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(
			group.getGroupId(), false, layoutIds, getExportParameterMap(), null,
			null);

		LayoutLocalServiceUtil.importLayouts(
			TestPropsValues.getUserId(), importedGroup.getGroupId(), false,
			getImportParameterMap(), larFile);
	}

}