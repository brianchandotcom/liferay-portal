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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.dao.orm.PortalCustomSQL;
import com.liferay.portal.kernel.dao.orm.PortalCustomSQLUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.test.rule.PACLTestRule;
import com.liferay.portal.util.PortalUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class BeanPropertyTest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Test
	public void testGet1() throws Exception {
		try {
			PortalRuntimePermission.checkGetBeanProperty(HttpUtil.class);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet2() throws Exception {
		LanguageUtil.getLanguage();
	}

	@Test
	public void testGet3() throws Exception {
		LanguageUtil.getLocale("en_US");
	}

	@Test
	public void testGet4() throws Exception {
		PortalRuntimePermission.checkGetBeanProperty(PortalUtil.class);
	}

	@Test
	public void testSet() throws Exception {
		try {
			LanguageUtil languageUtil = new LanguageUtil();

			languageUtil.setLanguage(null);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testSet3() throws Exception {
		PortalCustomSQLUtil portalCustomSQLUtil = new PortalCustomSQLUtil();

		PortalCustomSQL portalCustomSQL =
			PortalCustomSQLUtil.getPortalCustomSQL();

		portalCustomSQLUtil.setPortalCustomSQL(portalCustomSQL);
	}

}