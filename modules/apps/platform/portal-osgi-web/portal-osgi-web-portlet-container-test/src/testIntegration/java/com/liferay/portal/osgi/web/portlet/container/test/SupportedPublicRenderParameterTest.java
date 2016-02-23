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
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class SupportedPublicRenderParameterTest
	extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testPortalProvidedPRP() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		String prpName = "categoryId";

		properties.put(
			"javax.portlet.supported-public-render-parameter", prpName);

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), TEST_PORTLET_ID);

		Assert.assertFalse(portlet.isUndeployedPortlet());

		Set<PublicRenderParameter> publicRenderParameters =
			portlet.getPublicRenderParameters();

		Assert.assertNotNull(publicRenderParameters);

		boolean found = false;

		for (PublicRenderParameter publicRenderParameter :
				publicRenderParameters) {

			if (prpName.equals(publicRenderParameter.getIdentifier())) {
				found = true;
			}
		}

		Assert.assertTrue(found);
	}

	@Test
	public void testPortletProvidedPRP() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		String prpName = "myprp";

		properties.put(
			"javax.portlet.supported-public-render-parameter",
			prpName + ";http://some.uri.tld/space");

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), TEST_PORTLET_ID);

		Assert.assertFalse(portlet.isUndeployedPortlet());

		Set<PublicRenderParameter> publicRenderParameters =
			portlet.getPublicRenderParameters();

		Assert.assertNotNull(publicRenderParameters);

		boolean found = false;

		for (PublicRenderParameter publicRenderParameter :
				publicRenderParameters) {

			if (prpName.equals(publicRenderParameter.getIdentifier())) {
				found = true;
			}
		}

		Assert.assertTrue(found);
	}

}