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

package com.liferay.portlet.blogs.asset;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.service.persistence.BaseAssetSearchTestCase;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.BlogsTestUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BlogsEntryAssetSearchTest extends BaseAssetSearchTestCase {

	@Ignore()
	@Override
	@Test
	public void testClassTypeIds1() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testClassTypeIds2() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testOrderByExpirationDateAsc() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testOrderByExpirationDateDesc() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), keywords, true, serviceContext);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BlogsEntry.class;
	}

	@Override
	protected String getSearchKeywords() {
		return "title";
	}

}