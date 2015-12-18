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

package com.liferay.portal.service;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class ServiceComponentLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_serviceComponentsCount =
			ServiceComponentLocalServiceUtil.getServiceComponentsCount();

		_serviceComponent1 = addServiceComponent(_SERVICE_COMPONENT_1, 1);
		_serviceComponent2 = addServiceComponent(_SERVICE_COMPONENT_2, 1);
	}

	@Test
	public void testGetLatestServiceComponentsWithMultipleVersions()
		throws Exception {

		ServiceComponent serviceComponent = addServiceComponent(
			_SERVICE_COMPONENT_1, 2);

		List<ServiceComponent> serviceComponents =
			ServiceComponentLocalServiceUtil.getLatestServiceComponents();

		Assert.assertEquals(
			2, serviceComponents.size() - _serviceComponentsCount);

		ServiceComponent latestServiceComponent = getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_1);

		Assert.assertEquals(2, latestServiceComponent.getBuildNumber());

		latestServiceComponent = getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_2);

		Assert.assertEquals(1, latestServiceComponent.getBuildNumber());

		ServiceComponentLocalServiceUtil.deleteServiceComponent(
			serviceComponent);
	}

	@Test
	public void testGetLatestServiceComponentsWithSingleVersion()
		throws Exception {

		List<ServiceComponent> serviceComponents =
			ServiceComponentLocalServiceUtil.getLatestServiceComponents();

		Assert.assertEquals(
			2, serviceComponents.size() - _serviceComponentsCount);

		ServiceComponent latestServiceComponent = getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_1);

		Assert.assertEquals(1, latestServiceComponent.getBuildNumber());

		latestServiceComponent = getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_2);

		Assert.assertEquals(1, latestServiceComponent.getBuildNumber());
	}

	protected ServiceComponent addServiceComponent(
		String buildNameSpace, long buildNumber) {

		long serviceComponentId = CounterLocalServiceUtil.increment();

		ServiceComponent serviceComponent =
			ServiceComponentLocalServiceUtil.createServiceComponent(
				serviceComponentId);

		serviceComponent.setBuildNamespace(buildNameSpace);
		serviceComponent.setBuildNumber(buildNumber);

		return ServiceComponentLocalServiceUtil.updateServiceComponent(
			serviceComponent);
	}

	protected ServiceComponent getServiceComponent(
		List<ServiceComponent> serviceComponents, String buildNamespace) {

		for (ServiceComponent serviceComponent : serviceComponents) {
			if (buildNamespace.equals(serviceComponent.getBuildNamespace())) {
				return serviceComponent;
			}
		}

		return null;
	}

	private static final String _SERVICE_COMPONENT_1 = "SERVICE_COMPONENT_1";

	private static final String _SERVICE_COMPONENT_2 = "SERVICE_COMPONENT_2";

	@DeleteAfterTestRun
	private ServiceComponent _serviceComponent1;

	@DeleteAfterTestRun
	private ServiceComponent _serviceComponent2;

	private int _serviceComponentsCount;

}