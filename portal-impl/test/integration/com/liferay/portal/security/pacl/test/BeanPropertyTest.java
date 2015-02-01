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

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.PortalCustomSQL;
import com.liferay.portal.kernel.dao.orm.PortalCustomSQLUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.test.rule.PACLTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.util.JournalContentUtil;

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
		try {
			JournalContentUtil.getJournalContent();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet3() throws Exception {
		try {
			LanguageUtil.getLanguage();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testGet4() throws Exception {
		try {
			PortalRuntimePermission.checkGetBeanProperty(PortalUtil.class);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testGet5() throws Exception {
		try {
			LanguageUtil.getLocale("en_US");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testSet1() throws Exception {
		try {
			EntityCache entityCache = EntityCacheUtil.getEntityCache();

			EntityCacheUtil entityCacheUtil = new EntityCacheUtil();

			entityCacheUtil.setEntityCache(entityCache);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testSet2() throws Exception {
		try {
			FinderCacheUtil finderCacheUtil = new FinderCacheUtil();

			FinderCache finderCache = FinderCacheUtil.getFinderCache();

			finderCacheUtil.setFinderCache(finderCache);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testSet3() throws Exception {
		try {
			PortalCustomSQLUtil portalCustomSQLUtil = new PortalCustomSQLUtil();

			PortalCustomSQL portalCustomSQL =
				PortalCustomSQLUtil.getPortalCustomSQL();

			portalCustomSQLUtil.setPortalCustomSQL(portalCustomSQL);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

}