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

import com.liferay.portal.kernel.format.PhoneNumberFormatUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;
import com.liferay.portal.security.pacl.test.hook.action.FailureStrutsAction;
import com.liferay.portal.security.pacl.test.hook.action.SuccessStrutsAction;
import com.liferay.portal.security.pacl.test.hook.indexer.OrganizationIndexerPostProcessor;
import com.liferay.portal.security.pacl.test.hook.indexer.UserIndexerPostProcessor;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalServiceUtil;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class HookTest {

	@Test
	public void indexer1()
		throws Exception {

		Assert.assertFalse(OrganizationIndexerPostProcessor.isInstantiated());
	}

	@Test
	public void indexer2() throws Exception {
		Assert.assertTrue(UserIndexerPostProcessor.isInstantiated());
	}

	@Test
	public void language1() throws Exception {
		Assert.assertEquals(
			LanguageUtil.get(new Locale("es"), "stars"), "Estrellas");
	}

	@Test
	public void language2() throws Exception {
		Assert.assertEquals(LanguageUtil.get(Locale.ITALY, "stars"), "Stelle");
	}

	@Test
	public void language3() throws Exception {
		Assert.assertEquals(
			LanguageUtil.get(new Locale("pt", "BR"), "stars"), "Ricardo Kaka");
	}

	@Test
	public void language4() throws Exception {
		Assert.assertEquals(
			LanguageUtil.get(new Locale("pt", "PT"), "stars"),
			"Cristiano Ronaldo");
	}

	@Test
	public void language5() throws Exception {
		Assert.assertEquals(
			LanguageUtil.get(Locale.UK, "stars"), "David Beckham");
	}

	@Test
	public void language6() throws Exception {
		Assert.assertEquals(LanguageUtil.get(Locale.US, "stars"), "Stars");
	}

	@Test
	public void portalProperties1() throws Exception {
		Assert.assertFalse(LanguageUtil.isBetaLocale(Locale.US));
	}

	@Test
	public void portalProperties2() throws Exception {
		String phoneNumber = PhoneNumberFormatUtil.format("123");

		Assert.assertTrue(phoneNumber.startsWith("(TEST"));
	}

	@Test
	public void services1() throws Exception {
		Assert.assertTrue(
			BlogsEntryLocalServiceUtil.getBlogsEntriesCount() < 0);
	}

	@Test
	public void services2()
		throws Exception {

		Assert.assertTrue(
			BlogsStatsUserLocalServiceUtil.getBlogsStatsUsersCount() >= 0);
	}

	@Test
	public void struts1() throws Exception {
		Assert.assertFalse(FailureStrutsAction.isInstantiated());
	}

	@Test
	public void struts2() throws Exception {
		Assert.assertTrue(SuccessStrutsAction.isInstantiated());
	}

}