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