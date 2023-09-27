/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.kernel;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.Collection;

import org.osgi.framework.BundleContext;

/**
 * @author Eudaldo Alonso
 */
public class LayoutUtilityPageEntryViewRendererRegistryUtil {

	public static LayoutUtilityPageEntryViewRenderer
		getLayoutUtilityPageEntryViewRenderer(String type) {

		return _layoutUtilityPageEntryViewRenderersServiceTrackerMap.getService(
			type);
	}

	public static Collection<LayoutUtilityPageEntryViewRenderer>
		getLayoutUtilityPageEntryViewRenderers() {

		return _layoutUtilityPageEntryViewRenderersServiceTrackerMap.values();
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final ServiceTrackerMap
		<String, LayoutUtilityPageEntryViewRenderer>
			_layoutUtilityPageEntryViewRenderersServiceTrackerMap =
				ServiceTrackerMapFactory.openSingleValueMap(
					_bundleContext, LayoutUtilityPageEntryViewRenderer.class,
					null,
					new PropertyServiceReferenceMapper<>("utility.page.type"));

}