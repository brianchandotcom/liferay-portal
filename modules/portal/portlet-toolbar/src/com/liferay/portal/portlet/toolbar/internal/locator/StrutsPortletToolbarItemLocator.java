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

package com.liferay.portal.portlet.toolbar.internal.locator;

import com.liferay.portal.kernel.portlet.toolbar.item.PortletToolbarMenuItem;
import com.liferay.portal.kernel.portlet.toolbar.item.locator.PortletToolbarItemLocator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class StrutsPortletToolbarItemLocator
	implements PortletToolbarItemLocator {

	@Override
	public List<PortletToolbarMenuItem> getPortletToolbarItems(
		String portletId, PortletRequest portletRequest) {

		String strutsAction = ParamUtil.getString(
			portletRequest, "struts_action");

		List<PortletToolbarMenuItem> portletToolbarMenuItems =
			_serviceTrackerMap.getService(
				portletId.concat(StringPool.PERIOD).concat(strutsAction));

		if (ListUtil.isEmpty(portletToolbarMenuItems)) {
			portletToolbarMenuItems = _serviceTrackerMap.getService(portletId);
		}

		return portletToolbarMenuItems;
	}

	@Activate
	protected void activate() {
		_serviceTrackerMap = ServiceTrackerCollections.multiValueMap(
			PortletToolbarMenuItem.class, "(javax.portlet.name=*)",
			new ServiceReferenceMapper<String, PortletToolbarMenuItem>() {

				@Override
				public void map(
					ServiceReference<PortletToolbarMenuItem> serviceReference,
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

		_serviceTrackerMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	private static
		ServiceTrackerMap<String, List<PortletToolbarMenuItem>>
			_serviceTrackerMap;

}