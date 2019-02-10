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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util;

import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
 * @author Cristina González
 */
public class MockFeature {

	public MockFeature(Feature feature) {
		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(
			EntityModel.class, MockResource.ENTITY_MODEL,
			new HashMap<String, Object>() {
				{
					put(
						"entity.model.name",
						MockResource.ODATA_ENTITY_MODEL_NAME);
				}
			});

		FeatureContext featureContext = new FeatureContext() {

			@Override
			public Configuration getConfiguration() {
				return null;
			}

			@Override
			public FeatureContext property(String s, Object o) {
				return null;
			}

			@Override
			public FeatureContext register(Class<?> clazz) {
				return this;
			}

			@Override
			public FeatureContext register(
				Class<?> clazz, Class<?>... classes) {

				return null;
			}

			@Override
			public FeatureContext register(Class<?> clazz, int i) {
				return null;
			}

			@Override
			public FeatureContext register(
				Class<?> clazz, Map<Class<?>, Integer> map) {

				return null;
			}

			@Override
			public FeatureContext register(Object object) {
				Class<?> clazz = object.getClass();

				_objects.put(clazz.getCanonicalName(), object);

				return this;
			}

			@Override
			public FeatureContext register(Object object, Class<?>... classes) {
				return null;
			}

			@Override
			public FeatureContext register(Object object, int integer) {
				return null;
			}

			@Override
			public FeatureContext register(
				Object object, Map<Class<?>, Integer> map) {

				return null;
			}

		};

		feature.configure(featureContext);
	}

	public Object getObject(String className) {
		return _objects.get(className);
	}

	private Map<String, Object> _objects = new HashMap<>();

}