/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model.adapter;

import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilderLocator;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Máté Thurzó
 */
public class ModelAdapterUtil {

	public static <T, V> List<V> adapt(
		List<T> adapteeModels, Class<T> adapteeModelClass,
		Class<V> adaptedModelClass) {

		List<V> adaptedModels = new ArrayList<>();

		for (T adapteeModel : adapteeModels) {
			adaptedModels.add(
				adapt(adapteeModel, adapteeModelClass, adaptedModelClass));
		}

		return adaptedModels;
	}

	public static <T, V> List<V> adapt(
		List<T> adapteeModels, Class<V> adaptedModelClass) {

		List<V> adaptedModels = new ArrayList<>();

		for (T adapteeModel : adapteeModels) {
			adaptedModels.add(adapt(adapteeModel, adaptedModelClass));
		}

		return adaptedModels;
	}

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
			_modelAdapterBuilderLocatorSnapshot.get();

		if (modelAdapterBuilderLocator == null) {
			return null;
		}

		ModelAdapterBuilder<T, V> modelAdapterBuilder =
			modelAdapterBuilderLocator.locate(
				adapteeModelClass, adaptedModelClass);

		return modelAdapterBuilder.build(adapteeModel);
	}

	private static final Snapshot<ModelAdapterBuilderLocator>
		_modelAdapterBuilderLocatorSnapshot = new Snapshot<>(
			ModelAdapterUtil.class, ModelAdapterBuilderLocator.class);

}