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

package com.liferay.portal.test;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.test.portal.service.ServiceTestUtil;
import com.liferay.test.portal.util.TestPropsValues;

/**
 * @author Miguel Pastor
 */
public class EnvironmentExecutionTestListener
	extends AbstractExecutionTestListener {

	@Override
	public void runAfterClass(TestContext testContext) {
		ServiceTestUtil.destroyServices();

		try {
			LuceneHelperUtil.delete(TestPropsValues.getCompanyId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		ServiceTestUtil.initServices();
		ServiceTestUtil.initPermissions();
	}

	private static Log _log = LogFactoryUtil.getLog(
		EnvironmentExecutionTestListener.class);

}