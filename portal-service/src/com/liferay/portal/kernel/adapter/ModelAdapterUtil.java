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

package com.liferay.portal.kernel.adapter;

import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Mate Thurzo
 */
public class ModelAdapterUtil {

	public static <T, V> V adapt(
		T adapteeModel, Class<T> adapteeModelClass,
		Class<V> adaptedModelClass) {

		return doAdapt(adapteeModel, adapteeModelClass, adaptedModelClass);
	}

	public static <T, V> V adapt(T adapteeModel, Class<V> adaptedModelClass) {
		Class<T> adapteeModelClass = (Class<T>)adapteeModel.getClass();

		return doAdapt(adapteeModel, adapteeModelClass, adaptedModelClass);
	}

	protected static <T, V> V doAdapt(
		T adapteeModel, Class<T> adapteeModelClass,
		Class<V> adaptedModelClass) {

		ModelAdapterBuilderLocator modelAdapterBuilderLocator =
			_serviceTracker.getService();

		if (modelAdapterBuilderLocator == null) {
			return null;
		}

		ModelAdapterBuilder<T, V> modelAdapterBuilder =
			(ModelAdapterBuilder<T, V>)modelAdapterBuilderLocator.locate(
				adapteeModelClass, adaptedModelClass);

		return modelAdapterBuilder.build(adapteeModel);
	}

	private static final
		ServiceTracker<ModelAdapterBuilderLocator, ModelAdapterBuilderLocator>
			_serviceTracker = RegistryUtil.getRegistry().trackServices(
				ModelAdapterBuilderLocator.class);

	static {
		_serviceTracker.open();
	}

}