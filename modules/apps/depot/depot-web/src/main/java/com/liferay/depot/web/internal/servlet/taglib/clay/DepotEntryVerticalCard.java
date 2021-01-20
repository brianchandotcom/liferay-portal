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

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelServiceUtil;
import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.depot.web.internal.servlet.taglib.util.DepotActionDropdownItemsProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Alejandro Tardín
 */
public class DepotEntryVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public DepotEntryVerticalCard(
			DepotEntry depotEntry, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			RowChecker rowChecker)
		throws PortalException {

		super(depotEntry, rowChecker);

		_depotEntry = depotEntry;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_group = depotEntry.getGroup();

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		DepotActionDropdownItemsProvider depotActionDropdownItemsProvider =
			new DepotActionDropdownItemsProvider(
				_depotEntry, _liferayPortletRequest, _liferayPortletResponse);

		return depotActionDropdownItemsProvider.getActionDropdownItems();
	}

	@Override
	public String getDefaultEventHandler() {
		return DepotAdminWebKeys.DEPOT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		try {
			PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, _depotEntry.getGroup(),
				DepotPortletKeys.DEPOT_ADMIN, 0, 0,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName", "/depot/view_depot_dashboard");
			portletURL.setParameter(
				"depotEntryId", String.valueOf(_depotEntry.getDepotEntryId()));

			return portletURL.toString();
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public String getIcon() {
		return "books";
	}

	@Override
	public String getInputValue() {
		if (Validator.isNull(super.getInputValue())) {
			return null;
		}

		return String.valueOf(_depotEntry.getDepotEntryId());
	}

	@Override
	public String getSubtitle() {
		try {
			int count =
				DepotEntryGroupRelServiceUtil.getDepotEntryGroupRelsCount(
					_depotEntry);

			if (count != 1) {
				return LanguageUtil.format(
					_liferayPortletRequest.getHttpServletRequest(),
					"x-connected-sites", count);
			}

			return LanguageUtil.format(
				_liferayPortletRequest.getHttpServletRequest(),
				"x-connected-site", count);
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public String getTitle() {
		try {
			return HtmlUtil.escape(
				_group.getDescriptiveName(_themeDisplay.getLocale()));
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return _group.getName(_themeDisplay.getLocale());
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotEntryVerticalCard.class);

	private final DepotEntry _depotEntry;
	private final Group _group;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}