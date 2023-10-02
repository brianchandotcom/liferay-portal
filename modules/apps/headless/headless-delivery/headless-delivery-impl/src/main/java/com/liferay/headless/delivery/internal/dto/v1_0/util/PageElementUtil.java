/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.LayoutStructureItemMapper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Jürgen Kappler
 * @author Javier de Arcos
 */
public class PageElementUtil {

	public static PageElement toPageElement(
		long groupId, LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration) {

		List<PageElement> pageElements = new ArrayList<>();

		List<String> childrenItemIds = layoutStructureItem.getChildrenItemIds();

		for (String childItemId : childrenItemIds) {
			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			List<String> grandChildrenItemIds =
				childLayoutStructureItem.getChildrenItemIds();

			if (grandChildrenItemIds.isEmpty()) {
				pageElements.add(
					_toPageElement(
						groupId, childLayoutStructureItem, saveInlineContent,
						saveMappingConfiguration));
			}
			else {
				pageElements.add(
					toPageElement(
						groupId, layoutStructure, childLayoutStructureItem,
						saveInlineContent, saveMappingConfiguration));
			}
		}

		PageElement pageElement = _toPageElement(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);

		if ((pageElement != null) && !pageElements.isEmpty()) {
			pageElement.setPageElements(
				pageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	private static PageElement _toPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		Class<?> clazz = layoutStructureItem.getClass();

		LayoutStructureItemMapper layoutStructureItemMapper =
			_serviceTrackerMap.getService(clazz.getName());

		if (layoutStructureItemMapper == null) {
			return null;
		}

		return layoutStructureItemMapper.getPageElement(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);
	}

	private static final ServiceTrackerMap<String, LayoutStructureItemMapper>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(PageElementUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, LayoutStructureItemMapper.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(layoutStructureItemMapper, emitter) -> emitter.emit(
					layoutStructureItemMapper.getClassName())));
	}

}