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

package com.liferay.site.internal.application.list;

import com.liferay.application.list.GroupProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(immediate = true, service = GroupProvider.class)
public class LatentGroupProvider implements GroupProvider {

	@Override
	public Group getGroup(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (!group.isControlPanel()) {
			return group;
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		long groupId = GetterUtil.getLong(
			SessionClicks.get(
				originalHttpServletRequest.getSession(), _KEY_LATENT_GROUP,
				null));

		if (groupId > 0) {
			return _groupLocalService.fetchGroup(groupId);
		}

		return null;
	}

	@Override
	public void setGroup(HttpServletRequest httpServletRequest, Group group) {
		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		SessionClicks.put(
			originalHttpServletRequest.getSession(), _KEY_LATENT_GROUP,
			String.valueOf(group.getGroupId()));
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private static final String _KEY_LATENT_GROUP =
		"com.liferay.site.application.list_latentGroup";

	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}