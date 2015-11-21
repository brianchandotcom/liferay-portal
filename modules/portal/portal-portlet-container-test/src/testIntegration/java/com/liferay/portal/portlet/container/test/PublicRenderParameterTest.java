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

package com.liferay.portal.portlet.container.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.application.type.ApplicationType;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.test.randomizerbumpers.FriendlyURLRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.PortletContainerTestUtil;
import com.liferay.portal.util.test.PortletContainerTestUtil.Response;
import com.liferay.portlet.PortletQName;
import com.liferay.portlet.PortletURLImpl;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Dictionary;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class PublicRenderParameterTest extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Test
	public void testWithModuleLayoutTypeController() throws Exception {
		final String prpName = "categoryId";
		final String prpValue = RandomTestUtil.randomString(
			FriendlyURLRandomizerBumper.INSTANCE,
			NumericStringRandomizerBumper.INSTANCE,
			UniqueStringRandomizerBumper.INSTANCE);
		final AtomicBoolean success = new AtomicBoolean(false);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"com.liferay.portlet.application-type",
			new String[] {
				ApplicationType.FULL_PAGE_APPLICATION.toString(),
				ApplicationType.WIDGET.toString()
			});
		properties.put(
			"javax.portlet.supported-public-render-parameter", prpName);

		testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				PrintWriter writer = renderResponse.getWriter();

				String value = renderRequest.getParameter(prpName);

				if (prpValue.equals(value)) {
					success.set(true);
				}

				writer.write(value);
			}

		};

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID, false);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), TEST_PORTLET_ID);

		Assert.assertFalse(portlet.isUndeployedPortlet());

		String name = RandomTestUtil.randomString(
			FriendlyURLRandomizerBumper.INSTANCE,
			NumericStringRandomizerBumper.INSTANCE,
			UniqueStringRandomizerBumper.INSTANCE);

		layout = LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, null, null,
			"full_page_application", false,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name),
			ServiceContextTestUtil.getServiceContext());

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(prpName, prpValue);

		String portletURLString = portletURL.toString();

		Assert.assertTrue(
			portletURLString.contains(
				PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE));

		Response response = PortletContainerTestUtil.request(portletURLString);

		Assert.assertEquals(200, response.getCode());
		Assert.assertTrue(success.get());
	}

	@Test
	public void testWithPortalLayoutTypeController() throws Exception {
		final String prpName = "categoryId";
		final String prpValue = RandomTestUtil.randomString(
			FriendlyURLRandomizerBumper.INSTANCE,
			NumericStringRandomizerBumper.INSTANCE,
			UniqueStringRandomizerBumper.INSTANCE);
		final AtomicBoolean success = new AtomicBoolean(false);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"javax.portlet.supported-public-render-parameter", prpName);

		testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				PrintWriter writer = renderResponse.getWriter();

				String value = renderRequest.getParameter(prpName);

				if (prpValue.equals(value)) {
					success.set(true);
				}

				writer.write(value);
			}

		};

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(prpName, prpValue);

		String portletURLString = portletURL.toString();

		Assert.assertTrue(
			portletURLString.contains(
				PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE));

		Response response = PortletContainerTestUtil.request(portletURLString);

		Assert.assertEquals(200, response.getCode());
		Assert.assertTrue(success.get());
	}

}