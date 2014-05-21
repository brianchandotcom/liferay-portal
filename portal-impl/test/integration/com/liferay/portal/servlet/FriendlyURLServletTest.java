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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.Portal;
import com.liferay.test.portal.util.GroupTestUtil;
import com.liferay.test.portal.util.LayoutTestUtil;
import com.liferay.test.portal.util.RandomTestUtil;
import com.liferay.test.portal.util.ServiceContextTestUtil;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

import org.testng.Assert;

/**
 * @author László Csontos
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class FriendlyURLServletTest {

	@Before
	public void setUp() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetRedirectWithExistentSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), RandomTestUtil.randomString());

		testGetRedirect(
			getPath(group, layout), Portal.PATH_MAIN,
			new Object[] {getURL(layout), false});
	}

	@Test
	public void testGetRedirectWithInvalidPath() throws Exception {
		testGetRedirect(
			null, Portal.PATH_MAIN, new Object[] {Portal.PATH_MAIN, false});
		testGetRedirect(
			"test", Portal.PATH_MAIN, new Object[] {Portal.PATH_MAIN, false});
	}

	@Test(expected = NoSuchGroupException.class)
	public void testGetRedirectWithNonExistentSite() throws Exception {
		testGetRedirect("/non-existent-site/home", Portal.PATH_MAIN, null);
	}

	protected String getPath(Group group, Layout layout) {
		return group.getFriendlyURL() + layout.getFriendlyURL();
	}

	protected String getURL(Layout layout) {
		return "/c/portal/layout?p_l_id=" + layout.getPlid() +
			"&p_v_l_s_g_id=0";
	}

	protected void testGetRedirect(
			String path, String mainPath, Object[] expectedRedirectArray)
		throws Exception {

		Object[] actualRedirectArray = _friendlyURLServlet.getRedirect(
			_request, path, mainPath, Collections.<String, String[]>emptyMap());

		Assert.assertEquals(actualRedirectArray, expectedRedirectArray);
	}

	private FriendlyURLServlet _friendlyURLServlet = new FriendlyURLServlet();
	private HttpServletRequest _request = new MockHttpServletRequest();

}