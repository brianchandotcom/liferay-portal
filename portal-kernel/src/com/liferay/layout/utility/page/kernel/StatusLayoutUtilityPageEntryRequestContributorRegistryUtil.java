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

package com.liferay.layout.utility.page.kernel;

import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.kernel.request.contributor.StatusLayoutUtilityPageEntryRequestContributor;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;

/**
 * @author Jürgen Kappler
 */
public class StatusLayoutUtilityPageEntryRequestContributorRegistryUtil {

	public static StatusLayoutUtilityPageEntryRequestContributor
		getStatusLayoutUtilityPageEntryRequestContributor(int statusCode) {

		String layoutUtilityPageEntryType = _externalToInternalValuesMap.get(
			statusCode);

		if (Validator.isNull(layoutUtilityPageEntryType)) {
			return null;
		}

		return _layoutUtilityPageEntryViewRenderersServiceTrackerMap.getService(
			layoutUtilityPageEntryType);
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final Map<Integer, String> _externalToInternalValuesMap =
		HashMapBuilder.put(
			HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
			LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR
		).put(
			HttpServletResponse.SC_NOT_FOUND,
			LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND
		).build();

	private static final ServiceTrackerMap
		<String, StatusLayoutUtilityPageEntryRequestContributor>
			_layoutUtilityPageEntryViewRenderersServiceTrackerMap =
				ServiceTrackerMapFactory.openSingleValueMap(
					_bundleContext,
					(Class<StatusLayoutUtilityPageEntryRequestContributor>)
						(Class)
							StatusLayoutUtilityPageEntryRequestContributor.
								class,
					null,
					(serviceReference, emitter) -> {
						try {
							List<String> utilityPageTypes = StringUtil.asList(
								serviceReference.getProperty(
									"utility.page.type"));

							for (String utilityPageType : utilityPageTypes) {
								emitter.emit(utilityPageType);
							}
						}
						finally {
							_bundleContext.ungetService(serviceReference);
						}
					},
					new PropertyServiceReferenceComparator<>(
						"service.ranking"));

}