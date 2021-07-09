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

package com.liferay.commerce.product.content.web.internal.asset.display.page.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	enabled = false, service = AssetDisplayPageFriendlyURLResolverHelper.class
)
public class AssetDisplayPageFriendlyURLResolverHelper {

	public long getPlidFromPortletId(
		List<Layout> layouts, String portletId, long scopeGroupId) {

		for (Layout layout : layouts) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (_hasNonstaticPortletId(layoutTypePortlet, portletId) &&
				(_portal.getScopeGroupId(layout, portletId) == scopeGroupId)) {

				return layout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public long getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		long scopeGroupId = groupId;

		try {
			Group group = _groupLocalService.getGroup(groupId);

			if (group.isLayout()) {
				Layout scopeLayout = _layoutLocalService.getLayout(
					group.getClassPK());

				groupId = scopeLayout.getGroupId();
			}

			String[] validLayoutTypes = {
				LayoutConstants.TYPE_PORTLET,
				LayoutConstants.TYPE_FULL_PAGE_APPLICATION,
				LayoutConstants.TYPE_PANEL, LayoutConstants.TYPE_CONTENT
			};

			for (String layoutType : validLayoutTypes) {
				List<Layout> layouts = _layoutLocalService.getLayouts(
					groupId, privateLayout, layoutType);

				long plid = getPlidFromPortletId(
					layouts, portletId, scopeGroupId);

				if (plid != LayoutConstants.DEFAULT_PLID) {
					return plid;
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public int getURLSeparatorLength(String urlSeparator) {
		return urlSeparator.length();
	}

	private boolean _hasNonstaticPortletId(
		LayoutTypePortlet layoutTypePortlet, String portletId) {

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		for (String columnId : layoutTemplate.getColumns()) {
			String[] columnValues = StringUtil.split(
				layoutTypePortlet.getTypeSettingsProperty(columnId));

			for (String nonstaticPortletId : columnValues) {
				if (nonstaticPortletId.equals(portletId)) {
					return true;
				}

				String decodedNonstaticPortletName =
					PortletIdCodec.decodePortletName(nonstaticPortletId);

				if (decodedNonstaticPortletName.equals(portletId)) {
					return true;
				}
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetDisplayPageFriendlyURLResolverHelper.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}