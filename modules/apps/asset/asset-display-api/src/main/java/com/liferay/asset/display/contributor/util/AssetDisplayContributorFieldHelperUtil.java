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

package com.liferay.asset.display.contributor.util;

import com.liferay.asset.display.contributor.AssetDisplayContributorField;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author     Jürgen Kappler
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.asset.info.display.contributor.util.AssetInfoDisplayContributorFieldUtil}
 */
@Deprecated
public class AssetDisplayContributorFieldHelperUtil {

	public static List<AssetDisplayContributorField>
		getAssetDisplayContributorFields(String className) {

		List<AssetDisplayContributorField> assetDisplayContributorFields =
			_serviceTrackerMap.getService(className);

		if (assetDisplayContributorFields != null) {
			return assetDisplayContributorFields;
		}

		return Collections.emptyList();
	}

	private static final ServiceTrackerMap
		<String, List<AssetDisplayContributorField>> _serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetDisplayContributorFieldHelperUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, AssetDisplayContributorField.class,
			"(model.class.name=*)",
			(serviceReference, emitter) -> emitter.emit(
				(String)serviceReference.getProperty("model.class.name")));
	}

}