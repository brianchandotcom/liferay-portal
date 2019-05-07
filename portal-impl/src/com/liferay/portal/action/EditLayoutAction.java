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

package com.liferay.portal.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.LayoutTypeException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.JSONAction;
import com.liferay.sites.kernel.util.SitesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ming-Gih Lam
 * @author Hugo Huijser
 */
public class EditLayoutAction extends JSONAction {

	@Override
	public String getJSON(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(httpServletRequest, Constants.CMD);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			if (cmd.equals("add")) {
				String[] array = addPage(
					themeDisplay, httpServletRequest, httpServletResponse);

				jsonObject.put(
					"deletable", Boolean.valueOf(array[2])
				).put(
					"layoutId", array[0]
				).put(
					"sortable", Boolean.valueOf(array[3])
				).put(
					"updateable", Boolean.valueOf(array[4])
				).put(
					"url", array[1]
				);
			}
			else if (cmd.equals("delete")) {
				SitesUtil.deleteLayout(httpServletRequest, httpServletResponse);
			}
			else if (cmd.equals("display_order")) {
				updateDisplayOrder(httpServletRequest);
			}
			else if (cmd.equals("name")) {
				updateName(httpServletRequest);
			}
			else if (cmd.equals("parent_layout_id")) {
				updateParentLayoutId(httpServletRequest);
			}
			else if (cmd.equals("priority")) {
				updatePriority(httpServletRequest);
			}

			jsonObject.put("status", HttpServletResponse.SC_OK);
		}
		catch (LayoutTypeException lte) {
			jsonObject.put(
				"message",
				getLayoutTypeExceptionMessage(themeDisplay, lte, cmd));

			long plid = ParamUtil.getLong(httpServletRequest, "plid");

			if ((lte.getType() == LayoutTypeException.FIRST_LAYOUT) &&
				(plid > 0)) {

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				jsonObject.put(
					"groupId", layout.getGroupId()
				).put(
					"layoutId", layout.getLayoutId()
				).put(
					"originalParentLayoutId", layout.getParentLayoutId()
				).put(
					"originalParentPlid", layout.getParentPlid()
				).put(
					"originalPriority", layout.getPriority()
				).put(
					"plid", plid
				).put(
					"status", HttpServletResponse.SC_BAD_REQUEST
				);
			}
		}

		return jsonObject.toString();
	}

	protected String[] addPage(
			ThemeDisplay themeDisplay, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String doAsUserId = ParamUtil.getString(
			httpServletRequest, "doAsUserId");
		String doAsUserLanguageId = ParamUtil.getString(
			httpServletRequest, "doAsUserLanguageId");

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			httpServletRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			httpServletRequest, "parentLayoutId");
		String name = ParamUtil.getString(
			httpServletRequest, "name", "New Page");
		String title = StringPool.BLANK;
		String description = StringPool.BLANK;
		String friendlyURL = StringPool.BLANK;
		long layoutPrototypeId = ParamUtil.getLong(
			httpServletRequest, "layoutPrototypeId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		Layout layout = null;

		if (layoutPrototypeId > 0) {
			LayoutPrototype layoutPrototype =
				LayoutPrototypeServiceUtil.getLayoutPrototype(
					layoutPrototypeId);

			serviceContext.setAttribute(
				"layoutPrototypeUuid", layoutPrototype.getUuid());

			layout = LayoutServiceUtil.addLayout(
				groupId, privateLayout, parentLayoutId, name, title,
				description, LayoutConstants.TYPE_PORTLET, false, friendlyURL,
				serviceContext);
		}
		else {
			layout = LayoutServiceUtil.addLayout(
				groupId, privateLayout, parentLayoutId, name, title,
				description, LayoutConstants.TYPE_PORTLET, false, friendlyURL,
				serviceContext);
		}

		LayoutType layoutType = layout.getLayoutType();

		EventsProcessorUtil.process(
			PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
			layoutType.getConfigurationActionUpdate(), httpServletRequest,
			httpServletResponse);

		String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);

		if (Validator.isNotNull(doAsUserId)) {
			layoutURL = HttpUtil.addParameter(
				layoutURL, "doAsUserId", themeDisplay.getDoAsUserId());
		}

		if (Validator.isNotNull(doAsUserLanguageId)) {
			layoutURL = HttpUtil.addParameter(
				layoutURL, "doAsUserLanguageId",
				themeDisplay.getDoAsUserLanguageId());
		}

		boolean deleteable = LayoutPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), layout, ActionKeys.DELETE);
		boolean sortable =
			GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout.getGroupId(),
				ActionKeys.MANAGE_LAYOUTS) &&
			SitesUtil.isLayoutSortable(layout);
		boolean updateable = LayoutPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), layout, ActionKeys.UPDATE);

		return new String[] {
			String.valueOf(layout.getLayoutId()), layoutURL,
			String.valueOf(deleteable), String.valueOf(sortable),
			String.valueOf(updateable)
		};
	}

	protected String getLayoutTypeExceptionMessage(
		ThemeDisplay themeDisplay, LayoutTypeException lte, String cmd) {

		if (Validator.isNotNull(cmd)) {
			if (cmd.equals("delete") &&
				(lte.getType() == LayoutTypeException.FIRST_LAYOUT)) {

				return themeDisplay.translate(
					"you-cannot-delete-this-page-because-the-next-page-is-of-" +
						"type-x-and-so-cannot-be-the-first-page",
					"layout.types." + lte.getLayoutType());
			}

			if (cmd.equals("delete") &&
				(lte.getType() ==
					LayoutTypeException.FIRST_LAYOUT_PERMISSION)) {

				return themeDisplay.translate(
					"you-cannot-delete-this-page-because-the-next-page-is-" +
						"not-vieweable-by-unathenticated-users-and-so-cannot-" +
							"be-the-first-page");
			}

			if ((cmd.equals("display_order") || cmd.equals("priority")) &&
				(lte.getType() == LayoutTypeException.FIRST_LAYOUT)) {

				return themeDisplay.translate(
					"you-cannot-move-this-page-because-the-resulting-order-" +
						"would-place-a-page-of-type-x-as-the-first-page",
					"layout.types." + lte.getLayoutType());
			}

			if (cmd.equals("parent_layout_id") &&
				(lte.getType() == LayoutTypeException.FIRST_LAYOUT)) {

				return themeDisplay.translate(
					"you-cannot-move-this-page-because-the-resulting-order-" +
						"would-place-a-page-of-type-x-as-the-first-page",
					"layout.types." + lte.getLayoutType());
			}
		}

		if (lte.getType() == LayoutTypeException.FIRST_LAYOUT) {
			return themeDisplay.translate(
				"the-first-page-cannot-be-of-type-x",
				"layout.types." + lte.getLayoutType());
		}
		else if (lte.getType() == LayoutTypeException.NOT_PARENTABLE) {
			return themeDisplay.translate(
				"a-page-cannot-become-a-child-of-a-page-that-is-not-" +
					"parentable");
		}

		return StringPool.BLANK;
	}

	protected void updateDisplayOrder(HttpServletRequest httpServletRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			httpServletRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			httpServletRequest, "parentLayoutId");
		long[] layoutIds = StringUtil.split(
			ParamUtil.getString(httpServletRequest, "layoutIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		LayoutServiceUtil.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds, serviceContext);
	}

	protected void updateName(HttpServletRequest httpServletRequest)
		throws Exception {

		long plid = ParamUtil.getLong(httpServletRequest, "plid");

		String name = ParamUtil.getString(httpServletRequest, "name");
		String languageId = ParamUtil.getString(
			httpServletRequest, "languageId");

		if (plid <= 0) {
			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				httpServletRequest, "privateLayout");
			long layoutId = ParamUtil.getLong(httpServletRequest, "layoutId");

			LayoutServiceUtil.updateName(
				groupId, privateLayout, layoutId, name, languageId);
		}
		else {
			LayoutServiceUtil.updateName(plid, name, languageId);
		}
	}

	protected void updateParentLayoutId(HttpServletRequest httpServletRequest)
		throws Exception {

		long plid = ParamUtil.getLong(httpServletRequest, "plid");
		long parentPlid = ParamUtil.getLong(httpServletRequest, "parentPlid");
		int priority = ParamUtil.getInteger(httpServletRequest, "priority");

		LayoutServiceUtil.updateParentLayoutIdAndPriority(
			plid, parentPlid, priority);
	}

	protected void updatePriority(HttpServletRequest httpServletRequest)
		throws Exception {

		long plid = ParamUtil.getLong(httpServletRequest, "plid");

		if (plid <= 0) {
			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				httpServletRequest, "privateLayout");
			long layoutId = ParamUtil.getLong(httpServletRequest, "layoutId");
			long nextLayoutId = ParamUtil.getLong(
				httpServletRequest, "nextLayoutId");
			long previousLayoutId = ParamUtil.getLong(
				httpServletRequest, "previousLayoutId");

			LayoutServiceUtil.updatePriority(
				groupId, privateLayout, layoutId, nextLayoutId,
				previousLayoutId);
		}
		else {
			int priority = ParamUtil.getInteger(httpServletRequest, "priority");

			LayoutServiceUtil.updatePriority(plid, priority);
		}
	}

}