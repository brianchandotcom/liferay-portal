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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.Portal;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		_friendlyURLServlet = new FriendlyURLServlet();

		_serviceContext = ServiceTestUtil.getServiceContext();
	}

	@Before
	public void setUp() throws Exception {
		_mockHttpServletRequest = new MockHttpServletRequest();

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetRedirectWithExistentSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		String[] pathAndURL = getPathAndURL(group, layout);

		doTestGetRedirect(
			pathAndURL[0], Portal.PATH_MAIN, new Object[] {
				pathAndURL[1], false
			});
	}

	@Test
	public void testGetRedirectWithInvalidPath() throws Exception {
		doTestGetRedirect(
			null, Portal.PATH_MAIN, new Object[] {
				Portal.PATH_MAIN, false
			});

		doTestGetRedirect(
			"test", Portal.PATH_MAIN, new Object[] {
				Portal.PATH_MAIN, false
			});
	}

	@Test(expected = NoSuchGroupException.class)
	public void testGetRedirectWithNonExistentSite() throws Exception {
		doTestGetRedirect("/non-existent-site/home", Portal.PATH_MAIN, null);
	}

	protected void doTestGetRedirect(
			String path, String mainPath, Object[] expectedRedirectArray)
		throws Exception {

		Object[] actualRedirectArray = _friendlyURLServlet.getRedirect(
			_mockHttpServletRequest, path, mainPath,
			Collections.<String, String[]>emptyMap());

		Assert.assertEquals(actualRedirectArray, expectedRedirectArray);
	}

	protected String[] getPathAndURL(Group group, Layout layout) {
		String[] pathAndURL = new String[2];

		StringBundler sb = new StringBundler(4);

		sb.append(group.getFriendlyURL());
		sb.append(layout.getFriendlyURL());

		pathAndURL[0] = sb.toString();
		pathAndURL[1] =
			"/c/portal/layout?p_l_id=" + layout.getPlid() + "&p_v_l_s_g_id=0";

		return pathAndURL;
	}

	private static FriendlyURLServlet _friendlyURLServlet;
	private static ServiceContext _serviceContext;

	private MockHttpServletRequest _mockHttpServletRequest;

}