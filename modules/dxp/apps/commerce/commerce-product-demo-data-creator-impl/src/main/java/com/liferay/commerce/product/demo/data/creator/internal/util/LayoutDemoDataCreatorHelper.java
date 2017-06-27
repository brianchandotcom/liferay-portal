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

package com.liferay.commerce.product.demo.data.creator.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = LayoutDemoDataCreatorHelper.class)
public class LayoutDemoDataCreatorHelper extends BaseCPDemoDataCreatorHelper {

	public Layout createLayout(
			long userId, long groupId, String name, String friendlyURL)
		throws PortalException {

		Layout layout = _layouts.get(name);

		if (layout != null) {
			return layout;
		}

		layout = _layoutLocalService.fetchLayoutByFriendlyURL(
			groupId, false, friendlyURL);

		if (layout != null) {
			_layouts.put(name, layout);

			return layout;
		}

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		layout = _layoutLocalService.addLayout(
			userId, groupId, false, 0, name, name, null, "portlet", false,
			friendlyURL, serviceContext);

		_layouts.put(name, layout);

		return layout;
	}

	public void deleteLayouts(long userId, long groupId)
		throws PortalException {

		Set<Map.Entry<String, Layout>> entrySet = _layouts.entrySet();

		Iterator<Map.Entry<String, Layout>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Layout> entry = iterator.next();

			_layoutLocalService.deleteLayout(
				entry.getValue(), true, getServiceContext(userId, groupId));

			iterator.remove();
		}
	}

	public String getLayoutUuid(long userId, long groupId, String name)
		throws PortalException {

		String friendlyURL = StringPool.FORWARD_SLASH + name;

		Layout layout = createLayout(userId, groupId, name, friendlyURL);

		return layout.getUuid();
	}

	public void init() {
		_layouts = new HashMap<>();
	}

	@Activate
	protected void activate() {
		init();
	}

	@Deactivate
	protected void deactivate() {
		_layouts = null;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	private Map<String, Layout> _layouts;

}