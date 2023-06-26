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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.set.prototype.helper.LayoutSetPrototypeHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.impl.LayoutLocalServiceHelper;

import java.util.Locale;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_admin/get_friendly_url_warning"
	},
	service = MVCResourceCommand.class
)
public class GetFriendlyURLWarningResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-174417")) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put("hasWarnings", false));

			return;
		}

		long plid = ParamUtil.getLong(
			resourceRequest, "plid", LayoutConstants.DEFAULT_PLID);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			String friendlyURL = ParamUtil.getString(
				resourceRequest, "friendlyURL");

			if (Validator.isNull(friendlyURL)) {
				JSONPortletResponseUtil.writeJSON(
					resourceRequest, resourceResponse,
					JSONUtil.put("hasWarnings", false));

				return;
			}

			Layout layout = _layoutLocalService.getLayout(plid);

			if (!_layoutSetPrototypeHelper.hasDuplicatedFriendlyURLs(
					layout.getUuid(), layout.getGroupId(),
					layout.isPrivateLayout(), friendlyURL)) {

				JSONPortletResponseUtil.writeJSON(
					resourceRequest, resourceResponse,
					JSONUtil.put("hasWarnings", false));

				return;
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"hasWarnings", true
				).put(
					"warningMessage",
					() -> {
						ThemeDisplay themeDisplay =
							(ThemeDisplay)resourceRequest.getAttribute(
								WebKeys.THEME_DISPLAY);

						return _getWarningMessage(
							layout.getGroup(), themeDisplay.getLocale(),
							layout.isPrivateLayout());
					}
				));

			return;
		}

		String name = ParamUtil.getString(resourceRequest, "name");
		long groupId = ParamUtil.getLong(resourceRequest, "groupId");

		if ((groupId == 0) || Validator.isNull(name)) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put("hasWarnings", false));

			return;
		}

		boolean privateLayout = ParamUtil.getBoolean(
			resourceRequest, "privateLayout");

		String friendlyURL = StringPool.SLASH.concat(
			_layoutLocalServiceHelper.getFriendlyURL(name));

		if (!_layoutSetPrototypeHelper.hasDuplicatedFriendlyURLs(
				null, groupId, privateLayout, friendlyURL)) {

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put("hasWarnings", false));

			return;
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"hasWarnings", true
			).put(
				"warningMessage",
				() -> {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)resourceRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					return _getWarningMessage(
						_groupLocalService.getGroup(groupId),
						themeDisplay.getLocale(), privateLayout);
				}
			));
	}

	private String _getWarningMessage(
			Group group, Locale locale, boolean privateLayout)
		throws PortalException {

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			group.getGroupId(), privateLayout);

		if (group.isLayoutSetPrototype() ||
			layoutSet.isLayoutSetPrototypeLinkEnabled()) {

			return _language.get(
				locale,
				"the-friendly-url-of-the-site-template-page-you-are-trying-" +
					"to-save-conflicts");
		}

		return _language.get(
			locale,
			"the-friendly-url-of-the-page-you-are-trying-to-save-conflicts");
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutLocalServiceHelper _layoutLocalServiceHelper;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeHelper _layoutSetPrototypeHelper;

}