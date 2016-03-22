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

package com.liferay.portlet.asset.util;

import com.liferay.asset.kernel.util.AssetEntryValidator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Julio Camarero
 */
public class AssetEntryValidatorRegistry {

	public void afterPropertiesSet() {
		_serviceTrackerMap = ServiceTrackerCollections.openMultiValueMap(
			AssetEntryValidator.class, "model.class.name");
	}

	public void destroy() {
		_serviceTrackerMap.close();
	}

	public List<AssetEntryValidator> getAssetEntryValidators(String className) {
		List<AssetEntryValidator> assetEntryValidators = new ArrayList<>();

		List<AssetEntryValidator> genericAssetEntryValidators =
			_serviceTrackerMap.getService("*");

		if (!ListUtil.isEmpty(genericAssetEntryValidators)) {
			assetEntryValidators.addAll(genericAssetEntryValidators);
		}

		if (Validator.isNotNull(className)) {
			List<AssetEntryValidator> classNameAssetEntryValidators =
				_serviceTrackerMap.getService(className);

			if (!ListUtil.isEmpty(classNameAssetEntryValidators)) {
				assetEntryValidators.addAll(classNameAssetEntryValidators);
			}
		}

		return assetEntryValidators;
	}

	private ServiceTrackerMap<String, List<AssetEntryValidator>>
		_serviceTrackerMap;

}