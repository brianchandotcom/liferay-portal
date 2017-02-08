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
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.PortletContainerTestUtil;
import com.liferay.portal.util.test.PortletContainerTestUtil.Response;
import com.liferay.portlet.PortletURLImpl;
import junit.framework.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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

		_testPortletTrackerRegistration(_INTERNAL_CLASS_PORTLET_ID);
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
	public void testPortletTrackerRegistrationUsingSimplePortletName()
		throws Exception {

		_testPortletTrackerRegistration("simplename", "simplename");
	}

	private void _testPortletTrackerRegistration(String expectedPortletId)
		throws Exception {

		// make PortletTracker register portlet using class name
		registerService(
			javax.portlet.Portlet.class, _internalClassPortlet,
			new HashMapDictionary<String, Object>());

		_testPortletIsAvailable(expectedPortletId);
	}

	private void _testPortletTrackerRegistration(
			String givenPortletId, String expectedPortletId)
		throws Exception {

		// make PortletTracker register portlet using actualPortletId
		setUpPortlet(
			_internalClassPortlet, new HashMapDictionary<String, Object>(),
			givenPortletId, false);

		_testPortletIsAvailable(expectedPortletId);
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

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, expectedPortletId, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		Response response = PortletContainerTestUtil.request(
			portletURL.toString());

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(_internalClassPortlet.isCalledRender());

		_internalClassPortlet.reset();
	}

	private class InternalClassPortlet extends TestPortlet {

		@Override
		public void render(
				RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

			super.render(renderRequest, renderResponse);

			PrintWriter printWriter = renderResponse.getWriter();

			printWriter.write(getClass().getName());
			printWriter.write(getPortletName());
		}

	}

	private InternalClassPortlet _internalClassPortlet =
		new InternalClassPortlet();

	private static final String _INTERNAL_CLASS_PORTLET_ID =
		"com_liferay_portal_osgi_web_portlet_container_test_" +
			"PortletTrackerTest_InternalClassPortlet";
}
