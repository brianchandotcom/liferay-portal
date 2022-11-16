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

package com.liferay.segments.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.segments.constants.SegmentsPortletKeys;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(service = {})
public class SegmentsPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return SegmentsPortletKeys.SEGMENTS;
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-166954"))) {
			return true;
		}

		if (group.isLayoutSetPrototype() || group.isUser()) {
			return false;
		}

		return super.isShow(permissionChecker, group);
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-166954"))) {
			_serviceRegistration = bundleContext.registerService(
				PanelApp.class, this,
				HashMapDictionaryBuilder.<String, Object>put(
					"panel.app.order", 300
				).put(
					"panel.category.key",
					PanelCategoryKeys.SITE_ADMINISTRATION_MEMBERS
				).build());
		}
		else {
			_serviceRegistration = bundleContext.registerService(
				PanelApp.class, this,
				HashMapDictionaryBuilder.<String, Object>put(
					"panel.app.order", 800
				).put(
					"panel.category.key", PanelCategoryKeys.CONTROL_PANEL_USERS
				).build());
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration == null) {
			return;
		}

		_serviceRegistration.unregister();
	}

	private volatile ServiceRegistration<PanelApp> _serviceRegistration;

}