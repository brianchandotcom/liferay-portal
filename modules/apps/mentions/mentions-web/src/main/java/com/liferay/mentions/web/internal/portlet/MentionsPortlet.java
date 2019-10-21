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

package com.liferay.mentions.web.internal.portlet;

import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.mentions.strategy.MentionsStrategy;
import com.liferay.mentions.util.MentionsUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.ui.UserPortraitTag;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javax.portlet.Portlet;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.icon=/icons/mentions.png",
		"javax.portlet.display-name=Mentions",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.name=" + MentionsPortletKeys.MENTIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class MentionsPortlet extends MVCPortlet {

	@Override
	public void serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (!MentionsUtil.isMentionsEnabled(
					themeDisplay.getSiteGroupId())) {

				return;
			}

			JSONArray jsonArray = _getJSONArray(
				_getSupplier(
					themeDisplay,
					ParamUtil.getString(resourceRequest, "strategy"),
					ParamUtil.getString(resourceRequest, "query")),
				themeDisplay);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(resourceResponse);

			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

			ServletResponseUtil.write(
				httpServletResponse, jsonArray.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, MentionsStrategy.class, "mentions.strategy");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private JSONArray _getJSONArray(
			Supplier<List<User>> usersSupplier, ThemeDisplay themeDisplay)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User user : usersSupplier.get()) {
			if (user.isDefaultUser() ||
				(themeDisplay.getUserId() == user.getUserId())) {

				continue;
			}

			JSONObject jsonObject = JSONUtil.put(
				"fullName", user.getFullName());

			String mention = "@" + user.getScreenName();

			String profileURL = user.getDisplayURL(themeDisplay);

			if (Validator.isNotNull(profileURL)) {
				mention = StringBundler.concat(
					"<a href=\"", profileURL, "\">@", user.getScreenName(),
					"</a>");
			}

			jsonObject.put(
				"mention", mention
			).put(
				"portraitHTML",
				UserPortraitTag.getUserPortraitHTML(
					StringPool.BLANK, user, themeDisplay)
			).put(
				"screenName", user.getScreenName()
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private JSONObject _getJSONObject(String strategyString)
		throws PortalException {

		if ((strategyString == null) || strategyString.isEmpty()) {
			return JSONUtil.put("strategy", "default");
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			strategyString);

		if (jsonObject.isNull("strategy")) {
			throw new PortalException(
				"strategy field is mandatory in " + strategyString);
		}

		return jsonObject;
	}

	private Supplier<List<User>> _getSupplier(
			ThemeDisplay themeDisplay, String strategyString, String query)
		throws PortalException {

		JSONObject jsonObject = _getJSONObject(strategyString);

		String strategy = jsonObject.getString("strategy");

		MentionsStrategy mentionsStrategy = _serviceTrackerMap.getService(
			strategy);

		if (mentionsStrategy == null) {
			throw new PortalException(
				"No mentions strategy registered with " + strategy);
		}

		return () -> {
			try {
				return mentionsStrategy.getUsers(
					themeDisplay.getCompanyId(), themeDisplay.getUserId(),
					query, jsonObject);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);

				return Collections.emptyList();
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MentionsPortlet.class);

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, MentionsStrategy> _serviceTrackerMap;

}