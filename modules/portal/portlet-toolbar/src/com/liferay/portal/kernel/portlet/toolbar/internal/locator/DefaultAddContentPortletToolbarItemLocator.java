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

package com.liferay.portal.kernel.portlet.toolbar.internal.locator;

import com.liferay.portal.kernel.portlet.toolbar.item.AddContentPortletToolbarItem;
import com.liferay.portal.kernel.portlet.toolbar.item.locator.AddContentPortletToolbarItemLocator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class DefaultAddContentPortletToolbarItemLocator
	implements AddContentPortletToolbarItemLocator {

	@Override
	public List<AddContentPortletToolbarItem> getPortletToolbarItems(
		String portletId, PortletRequest portletRequest) {

		String strutsAction = ParamUtil.getString(
			portletRequest, "struts_action");

		List<AddContentPortletToolbarItem> addContentPortletToolbarItems =
			_serviceTrackerMap.getService(
				portletId.concat(StringPool.PERIOD).concat(strutsAction));

		if (ListUtil.isEmpty(addContentPortletToolbarItems)) {
			addContentPortletToolbarItems = _serviceTrackerMap.getService(
				portletId);
		}

		return addContentPortletToolbarItems;
	}

	private static final
		ServiceTrackerMap<String, List<AddContentPortletToolbarItem>>
		_serviceTrackerMap = ServiceTrackerCollections.multiValueMap(
			AddContentPortletToolbarItem.class, "(javax.portlet.name=*)",
			new ServiceReferenceMapper<String, AddContentPortletToolbarItem>() {

			@Override
			public void map(
				ServiceReference<AddContentPortletToolbarItem> serviceReference,
				Emitter<String> emitter) {

				String portletName = (String)serviceReference.getProperty(
					"javax.portlet.name");
				String strutsAction = (String)serviceReference.getProperty(
					"struts.action");

				String key = portletName;

				if (strutsAction != null) {
					key += StringPool.PERIOD.concat(strutsAction);
				}

				emitter.emit(key);
			}

		});

	static {
		_serviceTrackerMap.open();
	}

}