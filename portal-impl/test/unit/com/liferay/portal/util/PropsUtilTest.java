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

package com.liferay.portal.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.logging.Level;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class PropsUtilTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@NewEnv(type = NewEnv.Type.JVM)
	@NewEnv.Environment(
		variables = {
			"LIFERAY_PROPS_BY_COMPANY_1=key1=company1Value1\nkey2=company1Value2",
			"LIFERAY_PROPS_BY_COMPANY_2=key1=company2Value1\nkey2=company2Value2"
		}
	)
	@NewEnv.JVMArgsLine("-Dcompany-id-properties=true")
	@Test
	public void testPropsByCompany() {
		CentralizedThreadLocal<Long> companyIdThreadLocal =
			ReflectionTestUtil.getFieldValue(
				CompanyThreadLocal.class, "_companyId");

		try (SafeCloseable safeCloseable =
				companyIdThreadLocal.setWithSafeCloseable(
					CompanyConstants.SYSTEM)) {

			Assert.assertNull(PropsUtil.get("key1"));
			Assert.assertNull(PropsUtil.get("key2"));
		}

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				PropsUtil.class.getName(), Level.OFF);
			SafeCloseable safeCloseable =
				companyIdThreadLocal.setWithSafeCloseable(1L)) {

			Assert.assertEquals("company1Value1", PropsUtil.get("key1"));
			Assert.assertEquals("company1Value2", PropsUtil.get("key2"));
		}

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				PropsUtil.class.getName(), Level.OFF);
			SafeCloseable safeCloseable =
				companyIdThreadLocal.setWithSafeCloseable(2L)) {

			Assert.assertEquals("company2Value1", PropsUtil.get("key1"));
			Assert.assertEquals("company2Value2", PropsUtil.get("key2"));
		}
	}

}