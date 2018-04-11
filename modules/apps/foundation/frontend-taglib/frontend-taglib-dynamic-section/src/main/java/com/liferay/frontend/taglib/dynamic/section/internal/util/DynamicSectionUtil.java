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

package com.liferay.frontend.taglib.dynamic.section.internal.util;

import com.liferay.frontend.taglib.dynamic.section.DynamicSection;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Comparator;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class DynamicSectionUtil {

	public static StringBundler modify(
			String name, PageContext pageContext, StringBundler sb)
		throws Exception {

		List<DynamicSection> dynamicSections = _serviceTrackerMap.getService(
			name);

		if (dynamicSections == null) {
			return sb;
		}

		for (DynamicSection dynamicSection : dynamicSections) {
			sb = dynamicSection.modify(sb, pageContext);
		}

		return sb;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, DynamicSection.class, "(name=*)",
			(serviceReference, emitter) ->
				emitter.emit((String)serviceReference.getProperty("name")),
			Comparator.comparing(this::_getServiceRanking));
	}

	private Integer _getServiceRanking(ServiceReference serviceReference) {
		Object serviceRankingObject = serviceReference.getProperty(
			Constants.SERVICE_RANKING);

		return GetterUtil.getInteger(serviceRankingObject.toString());
	}

	private static ServiceTrackerMap<String, List<DynamicSection>>
		_serviceTrackerMap;

}