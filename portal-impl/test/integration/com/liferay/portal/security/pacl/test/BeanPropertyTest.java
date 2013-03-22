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

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.PortalCustomSQL;
import com.liferay.portal.kernel.dao.orm.PortalCustomSQLUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class BeanPropertyTest {

	@Test
	public void get1() throws Exception {
		try {
			PortalRuntimePermission.checkGetBeanProperty(HttpUtil.class);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void get2() throws Exception {
		try {
			JournalContentUtil.getJournalContent();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void get3() throws Exception {
		try {
			LanguageUtil.getLanguage();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void get4() throws Exception {
		try {
			PortalRuntimePermission.checkGetBeanProperty(PortalUtil.class);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void get5() throws Exception {
		try {
			LanguageUtil.getLocale("en_US");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void set1() throws Exception {
		try {
			EntityCacheUtil entityCacheUtil = new EntityCacheUtil();

			EntityCache entityCache = entityCacheUtil.getEntityCache();

			entityCacheUtil.setEntityCache(entityCache);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void set2() throws Exception {
		try {
			FinderCacheUtil finderCacheUtil = new FinderCacheUtil();

			FinderCache finderCache = finderCacheUtil.getFinderCache();

			finderCacheUtil.setFinderCache(finderCache);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void set3() throws Exception {
		try {
			PortalCustomSQLUtil portalCustomSQLUtil = new PortalCustomSQLUtil();

			PortalCustomSQL portalCustomSQL =
				portalCustomSQLUtil.getPortalCustomSQL();

			portalCustomSQLUtil.setPortalCustomSQL(portalCustomSQL);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

}