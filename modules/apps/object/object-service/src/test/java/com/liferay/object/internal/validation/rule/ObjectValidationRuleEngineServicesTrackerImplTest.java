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

package com.liferay.object.internal.validation.rule;

import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcela Cunha
 */
@PrepareForTest(ServiceTrackerMapFactory.class)
@RunWith(PowerMockRunner.class)
public class ObjectValidationRuleEngineServicesTrackerImplTest
	extends PowerMockito {

	@Before
	public void setUp() {
		mockStatic(ServiceTrackerMapFactory.class);
	}

	@Test
	public void testGetObjectValidationRuleEngine() {
		ObjectValidationRuleEngineServicesTrackerImpl
			objectValidationRuleEngineServicesTrackerImpl =
				new ObjectValidationRuleEngineServicesTrackerImpl();

		objectValidationRuleEngineServicesTrackerImpl.
			objectValidationRuleEngineServiceTrackerMap =
				_objectValidationRuleEngineServiceTrackerMap;

		objectValidationRuleEngineServicesTrackerImpl.
			getObjectValidationRuleEngine(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM);

		Mockito.verify(
			_objectValidationRuleEngineServiceTrackerMap, Mockito.times(1)
		).getService(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM
		);
	}

	@Test
	public void testGetObjectValidationRuleEngines() {
		ObjectValidationRuleEngineServicesTrackerImpl
			objectValidationRuleEngineServicesTrackerImpl =
				new ObjectValidationRuleEngineServicesTrackerImpl();

		objectValidationRuleEngineServicesTrackerImpl.
			objectValidationRuleEngineServiceTrackerMap =
				_objectValidationRuleEngineServiceTrackerMap;

		objectValidationRuleEngineServicesTrackerImpl.
			getObjectValidationRuleEngines();

		Mockito.verify(
			_objectValidationRuleEngineServiceTrackerMap, Mockito.times(1)
		).values();
	}

	@Mock
	private ServiceTrackerMap<String, ObjectValidationRuleEngine>
		_objectValidationRuleEngineServiceTrackerMap;

}