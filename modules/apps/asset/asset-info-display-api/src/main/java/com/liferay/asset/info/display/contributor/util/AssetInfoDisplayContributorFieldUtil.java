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

package com.liferay.asset.info.display.contributor.util;

import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Jürgen Kappler
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 *             com.liferay.info.display.field.InfoDisplayFieldProvider}
 */
@Deprecated
public class AssetInfoDisplayContributorFieldUtil {

	public static List<InfoDisplayContributorField>
		getInfoDisplayContributorFields(String className) {

		List<InfoDisplayContributorField> infoDisplayContributorFields =
			Optional.ofNullable(
				ListUtil.copy(_serviceTrackerMap.getService(className))
			).orElse(
				new ArrayList<>()
			);

		infoDisplayContributorFields.addAll(
			ExpandoInfoDisplayContributorFieldUtil.
				getInfoDisplayContributorFields(className));

		return infoDisplayContributorFields;
	}

	private static final ServiceTrackerMap
		<String, List<InfoDisplayContributorField>> _serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetInfoDisplayContributorFieldUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, InfoDisplayContributorField.class,
			"(model.class.name=*)",
			(serviceReference, emitter) -> emitter.emit(
				(String)serviceReference.getProperty("model.class.name")));
	}

}