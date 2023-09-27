/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.ProxyFactory;
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
		com.liferay.portal.kernel.util.PropsUtil.setProps(
			ProxyFactory.newDummyInstance(Props.class));

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