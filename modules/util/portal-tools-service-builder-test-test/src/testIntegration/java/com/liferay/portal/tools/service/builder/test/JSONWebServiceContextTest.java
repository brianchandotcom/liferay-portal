/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.service.LazyBlobEntryService;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class JSONWebServiceContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testJSONWebServiceContext() {
		OSGiBeanProperties osgiBeanProperties = AnnotationLocator.locate(
			LazyBlobEntryService.class, OSGiBeanProperties.class);

		Map<String, Object> properties = OSGiBeanProperties.Convert.toMap(
			osgiBeanProperties);

		String contextName = (String)properties.get(
			"json.web.service.context.name");
		String contextPath = (String)properties.get(
			"json.web.service.context.path");

		Bundle bundle = FrameworkUtil.getBundle(
			JSONWebServiceContextTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<LazyBlobEntryService> serviceReference =
			bundleContext.getServiceReference(LazyBlobEntryService.class);

		Assert.assertEquals(
			contextName,
			serviceReference.getProperty("json.web.service.context.name"));
		Assert.assertEquals(
			contextPath,
			serviceReference.getProperty("json.web.service.context.path"));
	}

}