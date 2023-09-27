/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.service.component.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class ServiceComponentLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Set<String> buildNamespaces = new HashSet<>();

		List<ServiceComponent> serviceComponents =
			_serviceComponentLocalService.getServiceComponents(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ServiceComponent serviceComponent : serviceComponents) {
			buildNamespaces.add(serviceComponent.getBuildNamespace());
		}

		_initialServiceComponentsCount = buildNamespaces.size();

		_serviceComponents.add(_addServiceComponent(_SERVICE_COMPONENT_1, 1));
		_serviceComponents.add(_addServiceComponent(_SERVICE_COMPONENT_2, 1));

		_release = _releaseLocalService.addRelease(
			"ServiceComponentLocalServiceTest", "0.0.0");
	}

	@Test
	public void testGetLatestServiceComponentsWithMultipleVersions() {
		_serviceComponents.add(_addServiceComponent(_SERVICE_COMPONENT_1, 2));

		List<ServiceComponent> serviceComponents =
			_serviceComponentLocalService.getLatestServiceComponents();

		Assert.assertEquals(
			2, serviceComponents.size() - _initialServiceComponentsCount);

		ServiceComponent latestServiceComponent1 = _getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_1);

		Assert.assertEquals(2, latestServiceComponent1.getBuildNumber());

		ServiceComponent latestServiceComponent2 = _getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_2);

		Assert.assertEquals(1, latestServiceComponent2.getBuildNumber());
	}

	@Test
	public void testGetLatestServiceComponentsWithSingleVersion() {
		List<ServiceComponent> serviceComponents =
			_serviceComponentLocalService.getLatestServiceComponents();

		Assert.assertEquals(
			2, serviceComponents.size() - _initialServiceComponentsCount);

		ServiceComponent latestServiceComponent1 = _getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_1);

		Assert.assertEquals(1, latestServiceComponent1.getBuildNumber());

		ServiceComponent latestServiceComponent2 = _getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_2);

		Assert.assertEquals(1, latestServiceComponent2.getBuildNumber());
	}

	private ServiceComponent _addServiceComponent(
		String buildNameSpace, long buildNumber) {

		long serviceComponentId = _counterLocalService.increment();

		ServiceComponent serviceComponent =
			_serviceComponentLocalService.createServiceComponent(
				serviceComponentId);

		serviceComponent.setBuildNamespace(buildNameSpace);
		serviceComponent.setBuildNumber(buildNumber);

		return _serviceComponentLocalService.updateServiceComponent(
			serviceComponent);
	}

	private ServiceComponent _getServiceComponent(
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

	@Inject
	private CounterLocalService _counterLocalService;

	private int _initialServiceComponentsCount;

	@DeleteAfterTestRun
	private Release _release;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	@Inject
	private ServiceComponentLocalService _serviceComponentLocalService;

	@DeleteAfterTestRun
	private final List<ServiceComponent> _serviceComponents = new ArrayList<>();

}