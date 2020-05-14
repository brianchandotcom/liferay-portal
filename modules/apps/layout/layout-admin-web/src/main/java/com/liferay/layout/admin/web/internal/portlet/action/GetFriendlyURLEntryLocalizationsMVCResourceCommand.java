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

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/get_friendly_url_entry_localizations"
	},
	service = MVCResourceCommand.class
)
public class GetFriendlyURLEntryLocalizationsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			throw new PrincipalException.MustBeAuthenticated(
				themeDisplay.getUserId());
		}

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			_getFriendlyURLEntryLocalizationsJSONObject(httpServletRequest));
	}

	private FriendlyURLEntryLocalization _getFriendlyURLEntryLocalization(
		FriendlyURLEntry friendlyURLEntry, String languageId) {

		try {
			return _friendlyURLEntryLocalService.
				getFriendlyURLEntryLocalization(
					friendlyURLEntry.getFriendlyURLEntryId(), languageId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	private JSONObject _getFriendlyURLEntryLocalizationsJSONObject(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(
			ParamUtil.getLong(httpServletRequest, "plid"));

		FriendlyURLEntry mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_layoutFriendlyURLEntryHelper.getClassNameId(
					layout.isPrivateLayout()),
				layout.getPlid());

		JSONObject friendlyURLEntryLocalizationsJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (String languageId : layout.getAvailableLanguageIds()) {
			FriendlyURLEntryLocalization mainFriendlyURLEntryLocalization =
				_getFriendlyURLEntryLocalization(
					mainFriendlyURLEntry, languageId);

			friendlyURLEntryLocalizationsJSONObject.put(
				languageId,
				JSONUtil.put(
					"current",
					_serializeFriendlyURLEntryLocalization(
						mainFriendlyURLEntryLocalization)
				).put(
					"history",
					_getJSONJArray(
						ListUtil.remove(
							_friendlyURLEntryLocalService.
								getFriendlyURLEntryLocalizations(
									layout.getGroupId(),
									_layoutFriendlyURLEntryHelper.
										getClassNameId(
											layout.isPrivateLayout()),
									layout.getPlid(), languageId),
							Arrays.asList(mainFriendlyURLEntryLocalization)),
						this::_serializeFriendlyURLEntryLocalization)
				));
		}

		return friendlyURLEntryLocalizationsJSONObject;
	}

	private <T> JSONArray _getJSONJArray(
		List<T> list, Function<T, JSONSerializable> serialize) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		list.forEach(t -> jsonArray.put(serialize.apply(t)));

		return jsonArray;
	}

	private JSONObject _serializeFriendlyURLEntryLocalization(
		FriendlyURLEntryLocalization friendlyEntryLocalization) {

		return JSONUtil.put(
			"friendlyURLEntryId",
			friendlyEntryLocalization.getFriendlyURLEntryId()
		).put(
			"friendlyURLEntryLocalizationId",
			Long.valueOf(
				friendlyEntryLocalization.getFriendlyURLEntryLocalizationId())
		).put(
			"urlTitle", friendlyEntryLocalization.getUrlTitle()
		);
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}