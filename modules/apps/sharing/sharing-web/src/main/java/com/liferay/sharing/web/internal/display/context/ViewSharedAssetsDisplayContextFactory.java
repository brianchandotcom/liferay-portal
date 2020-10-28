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

package com.liferay.sharing.web.internal.display.context;

import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.display.context.util.SharingMenuItemFactory;
import com.liferay.sharing.interpreter.SharingEntryInterpreterProvider;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.filter.SharedAssetsFilterItemTracker;
import com.liferay.sharing.web.internal.servlet.taglib.ui.SharingEntryMenuItemContributorRegistry;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ViewSharedAssetsDisplayContextFactory.class)
public class ViewSharedAssetsDisplayContextFactory {

	public ViewSharedAssetsDisplayContext getViewSharedAssetsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return new ViewSharedAssetsDisplayContext(
			_groupLocalService, _itemSelector,
			_portal.getLiferayPortletRequest(renderRequest),
			_portal.getLiferayPortletResponse(renderResponse),
			_sharedAssetsFilterItemTracker, _sharingConfigurationFactory,
			_sharingEntryInterpreterProvider::getSharingEntryInterpreter,
			_sharingEntryLocalService, _sharingEntryMenuItemContributorRegistry,
			_sharingMenuItemFactory, _sharingPermission);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

	@Reference
	private SharedAssetsFilterItemTracker _sharedAssetsFilterItemTracker;

	@Reference
	private SharingConfigurationFactory _sharingConfigurationFactory;

	@Reference
	private SharingEntryInterpreterProvider _sharingEntryInterpreterProvider;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingEntryMenuItemContributorRegistry
		_sharingEntryMenuItemContributorRegistry;

	@Reference
	private SharingMenuItemFactory _sharingMenuItemFactory;

	@Reference
	private SharingPermission _sharingPermission;

}