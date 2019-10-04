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

package com.liferay.portal.validation;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Máté Thurzó
 */
public class ModelValidatorRegistryUtil {

	public static <T extends ClassedModel> ModelValidator<T> getModelValidator(
		Class<T> modelClass) {

		return getModelValidator(modelClass.getName());
	}

	public static <T extends ClassedModel> ModelValidator<T> getModelValidator(
		String className) {

		return _modelValidatorRegistryUtil._getModelValidator(className);
	}

	public static <T extends ClassedModel, U extends ModelValidator<T>> U
		getSpecificModelValidator(
			Class<T> modelClass, Class<U> modelValidatorClass) {

		ModelValidator<T> modelValidator = getModelValidator(modelClass);

		return (U)modelValidator;
	}

	private ModelValidatorRegistryUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			ModelValidatorRegistryUtil.class);

		_bundleContext = bundle.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext,
			(Class<ModelValidator<?>>)(Class<?>)ModelValidator.class,
			new ModelValidatorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private <T extends ClassedModel> ModelValidator<T> _getModelValidator(
		String className) {

		return (ModelValidator<T>)_modelvalidators.get(className);
	}

	private static final ModelValidatorRegistryUtil
		_modelValidatorRegistryUtil = new ModelValidatorRegistryUtil();

	private final BundleContext _bundleContext;
	private final Map<String, ModelValidator<?>> _modelvalidators =
		new ConcurrentHashMap<>();
	private final ServiceTracker<ModelValidator<?>, ModelValidator<?>>
		_serviceTracker;

	private class ModelValidatorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ModelValidator<?>, ModelValidator<?>> {

		@Override
		public ModelValidator<?> addingService(
			ServiceReference<ModelValidator<?>> serviceReference) {

			ModelValidator<?> modelValidator = _bundleContext.getService(
				serviceReference);

			String modelClassName = GetterUtil.getString(
				serviceReference.getProperty("model.class.name"));

			_modelvalidators.put(modelClassName, modelValidator);

			return modelValidator;
		}

		@Override
		public void modifiedService(
			ServiceReference<ModelValidator<?>> serviceReference,
			ModelValidator<?> modelValidator) {

			removedService(serviceReference, modelValidator);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<ModelValidator<?>> serviceReference,
			ModelValidator<?> modelValidator) {

			_bundleContext.ungetService(serviceReference);

			String modelClassName = GetterUtil.getString(
				serviceReference.getProperty("model.class.name"));

			_modelvalidators.remove(modelClassName);
		}

	}

}