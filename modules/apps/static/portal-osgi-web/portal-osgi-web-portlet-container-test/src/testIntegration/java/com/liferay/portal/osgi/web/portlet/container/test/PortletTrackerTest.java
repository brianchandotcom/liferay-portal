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

package com.liferay.portal.osgi.web.portlet.container.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.osgi.web.portlet.container.test.util.PortletContainerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Sanz
 */
@RunWith(Arquillian.class)
public class PortletTrackerTest extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testPortletTrackerRegistrationUsingPortletClassName()
		throws Exception {

		_testPortletTrackerRegistration(
			"com_liferay_portal_osgi_web_portlet_container_test_" +
				"PortletTrackerTest_InternalClassTestPortlet");
	}

	@Test
	public void testPortletTrackerRegistrationUsingPortletNameWithDollar()
		throws Exception {

		_testPortletTrackerRegistration("dollar$portlet", "dollar_portlet");
	}

	@Test
	public void testPortletTrackerRegistrationUsingPortletNameWithDot()
		throws Exception {

		_testPortletTrackerRegistration("dot.portlet", "dot_portlet");
	}

	@Test
	public void testPortletTrackerRegistrationUsingPortletNameWithHyphen()
		throws Exception {

		_testPortletTrackerRegistration("hyphen-portlet", "hyphenportlet");
	}

	@Test
	public void testPortletTrackerRegistrationUsingPortletNameWithSpace()
		throws Exception {

		_testPortletTrackerRegistration("space portlet", "spaceportlet");
	}

	@Test
	public void testPortletTrackerRegistrationUsingSimpleName()
		throws Exception {

		_testPortletTrackerRegistration("simplename", "simplename");
	}

	private void _testPortletIsAvailable(String expectedPortletId)
		throws Exception {

		Portlet registeredLiferayPortlet =
			PortletLocalServiceUtil.getPortletById(expectedPortletId);

		Assert.assertNotNull(registeredLiferayPortlet);

		LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), layout, expectedPortletId, "column-1",
			new HashMap<String, String[]>());

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, expectedPortletId, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		PortletContainerTestUtil.Response response =
			PortletContainerTestUtil.request(portletURL.toString());

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(_internalClassTestPortlet.isCalledRender());

		_internalClassTestPortlet.reset();
	}

	private void _testPortletTrackerRegistration(String expectedPortletId)
		throws Exception {

		// Register portlet using class name

		registerService(
			javax.portlet.Portlet.class, _internalClassTestPortlet,
			new HashMapDictionary<String, Object>());

		_testPortletIsAvailable(expectedPortletId);
	}

	private void _testPortletTrackerRegistration(
			String givenPortletId, String expectedPortletId)
		throws Exception {

		// Register portlet using actual portlet ID

		setUpPortlet(
			_internalClassTestPortlet, new HashMapDictionary<String, Object>(),
			givenPortletId, false);

		_testPortletIsAvailable(expectedPortletId);
	}

	private final InternalClassTestPortlet _internalClassTestPortlet =
		new InternalClassTestPortlet();

	private class InternalClassTestPortlet extends TestPortlet {

		@Override
		public void render(
				RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

			super.render(renderRequest, renderResponse);

			PrintWriter printWriter = renderResponse.getWriter();

			Class<?> clazz = getClass();

			printWriter.write(clazz.getName());

			printWriter.write(getPortletName());
		}

	}

}