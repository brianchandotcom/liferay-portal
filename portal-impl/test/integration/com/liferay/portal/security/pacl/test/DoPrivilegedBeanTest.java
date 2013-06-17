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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class DoPrivilegedBeanTest {

	@Test
	public void testEquals() throws Exception {
		String className = "TEST_CLASS_NAME";

		ExpandoBridge expandoBridge1 = DoPrivilegedUtil.wrap(
			new ExpandoBridgeImpl(TestPropsValues.getCompanyId(), className));

		ExpandoBridge expandoBridge2 = DoPrivilegedUtil.wrap(
			new ExpandoBridgeImpl(TestPropsValues.getCompanyId(), className));

		Assert.assertTrue(expandoBridge1.equals(expandoBridge2));
	}

}