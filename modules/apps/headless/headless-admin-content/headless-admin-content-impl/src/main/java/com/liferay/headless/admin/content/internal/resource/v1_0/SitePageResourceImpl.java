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

package com.liferay.headless.admin.content.internal.resource.v1_0;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.headless.admin.content.resource.v1_0.SitePageResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/site-page.properties",
	scope = ServiceScope.PROTOTYPE, service = SitePageResource.class
)
public class SitePageResourceImpl extends BaseSitePageResourceImpl {

	@Override
	public void deleteSiteSitePage(Long siteId, String friendlyUrlPath)
		throws Exception {

		Layout layout = _getLayout(siteId, friendlyUrlPath);

		_layoutService.deleteLayout(layout.getPlid(), new ServiceContext());
	}

	private Layout _getLayout(long groupId, String friendlyUrlPath)
		throws Exception {

		String resourceName = ResourceActionsUtil.getCompositeModelName(
			Layout.class.getName(), "false");

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.getFriendlyURLEntryLocalization(
				groupId, _portal.getClassNameId(resourceName),
				StringPool.FORWARD_SLASH + friendlyUrlPath);

		return _layoutLocalService.getLayout(
			friendlyURLEntryLocalization.getClassPK());
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}