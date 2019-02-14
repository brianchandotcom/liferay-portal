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

package com.liferay.frontend.taglib.clay.attribute.provider.internal;

import static org.mockito.Matchers.any;

import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.osgi.util.tracker.ServiceTrackerCustomizer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rodolfo Roza Miranda
 */
@PrepareForTest(ServiceTrackerMapFactory.class)
@RunWith(PowerMockRunner.class)
public class ClayComponentAttributeProviderRegistryImplTest
	extends PowerMockito {

	@Test
	public void testReturnServicesInAscendingServiceRakingOrder() {
		mockStatic(ServiceTrackerMapFactory.class);

		String serviceKey = "foo";

		ServiceTrackerMap serviceTrackerMap = mock(ServiceTrackerMap.class);

		ServiceWrapper wrapper1 = _mockServiceWrapper("5");
		ServiceWrapper wrapper2 = _mockServiceWrapper("9");
		ServiceWrapper wrapper3 = _mockServiceWrapper("3");

		when(serviceTrackerMap.getService(serviceKey)).thenReturn(
			Arrays.asList(wrapper1, wrapper2, wrapper3));

		when(
			ServiceTrackerMapFactory.openMultiValueMap(
				any(), Mockito.eq(ClayComponentAttributeProvider.class),
				Mockito.eq("clay.component.attribute.provider.key"),
				any(ServiceTrackerCustomizer.class))
		).thenReturn(
			serviceTrackerMap
		);

		ClayComponentAttributeProviderRegistryImpl subject =
			new ClayComponentAttributeProviderRegistryImpl();

		subject.activate(null);

		List<ClayComponentAttributeProvider> services = subject.get(serviceKey);

		Assert.assertEquals(wrapper3.getService(), services.get(0));
		Assert.assertEquals(wrapper1.getService(), services.get(1));
		Assert.assertEquals(wrapper2.getService(), services.get(2));
	}

	private ServiceWrapper _mockServiceWrapper(String ranking) {
		ServiceWrapper wrapper = mock(ServiceWrapper.class);

		Map<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", ranking);

		when(wrapper.getProperties()).thenReturn(properties);

		ClayComponentAttributeProvider service = mock(
			ClayComponentAttributeProvider.class);

		when(wrapper.getService()).thenReturn(service);

		return wrapper;
	}

}