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

		doExportImportLayout(layoutIds);

		Assert.assertEquals(
			LayoutLocalServiceUtil.getLayoutsCount(group, false),
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));
	}

	@Test
	public void testExportImportSelectedLayouts() throws Exception {
		Layout layout2 = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		long[] layoutIds = new long[] {layout2.getLayoutId()};

		doExportImportLayout(layoutIds);

		Assert.assertEquals(
			layoutIds.length,
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));

		importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout2.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNotNull(importedLayout);
	}

	protected void doExportImportLayout(long[] layoutIds) throws Exception {
		larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(
			group.getGroupId(), false, layoutIds, getExportParameterMap(), null,
			null);

		LayoutLocalServiceUtil.importLayouts(
			TestPropsValues.getUserId(), importedGroup.getGroupId(), false,
			getImportParameterMap(), larFile);
	}

}