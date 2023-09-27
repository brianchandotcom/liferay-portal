/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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