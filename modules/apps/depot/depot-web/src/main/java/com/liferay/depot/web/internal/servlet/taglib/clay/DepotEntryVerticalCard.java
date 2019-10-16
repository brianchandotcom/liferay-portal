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

package com.liferay.depot.web.internal.servlet.taglib.clay;

import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.depot.web.internal.util.DepotEntryURLUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.util.GroupURLProvider;

import java.util.List;

import javax.portlet.ActionURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DepotEntryVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public DepotEntryVerticalCard(
		BaseModel<?> baseModel, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, RowChecker rowChecker) {

		super(baseModel, rowChecker);

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_group = (Group)baseModel;

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_liferayPortletRequest);

		_groupURLProvider = (GroupURLProvider)httpServletRequest.getAttribute(
			DepotAdminWebKeys.DEPOT_ADMIN_GROUP_URL_PROVIDER);

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_liferayPortletRequest);

		return new DropdownItemList() {
			{
				if (_hasUpdatePermission()) {
					add(
						dropdownItem -> {
							dropdownItem.setHref(
								DepotEntryURLUtil.getEditDepotEntryRenderURL(
									_group.getClassPK(),
									_themeDisplay.getURLCurrent(),
									_liferayPortletResponse));

							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, "edit"));
						});
				}

				if (_hasDeletePermission()) {
					add(
						dropdownItem -> {
							ActionURL deleteDepotEntryActionURL =
								DepotEntryURLUtil.getDeleteDepotEntryActionURL(
									_group.getClassPK(),
									_liferayPortletResponse);

							dropdownItem.putData("action", "deleteDepotEntry");

							dropdownItem.putData(
								"deleteDepotEntryURL",
								deleteDepotEntryActionURL.toString());

							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, "delete"));
						});
				}
			}
		};
	}

	@Override
	public String getDefaultEventHandler() {
		return DepotAdminWebKeys.DEPOT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		return _groupURLProvider.getGroupURL(_group, _liferayPortletRequest);
	}

	@Override
	public String getIcon() {
		return "repository";
	}

	@Override
	public String getTitle() {
		try {
			return HtmlUtil.escape(
				_group.getDescriptiveName(_themeDisplay.getLocale()));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return _group.getName(_themeDisplay.getLocale());
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private boolean _hasDeletePermission() {
		try {
			if (!GroupPermissionUtil.contains(
					_themeDisplay.getPermissionChecker(), _group,
					ActionKeys.DELETE)) {

				return false;
			}

			return true;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private boolean _hasUpdatePermission() {
		try {
			return GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _group,
				ActionKeys.UPDATE);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotEntryVerticalCard.class);

	private final Group _group;
	private final GroupURLProvider _groupURLProvider;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}