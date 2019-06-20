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

package com.liferay.trash.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.HorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.web.internal.constants.TrashWebKeys;
import com.liferay.trash.web.internal.servlet.taglib.util.TrashViewContentActionDropdownItemsProvider;

import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Pavel Savinov
 */
public class TrashContentHorizontalCard implements HorizontalCard {

	public TrashContentHorizontalCard(
		TrashedModel trashedModel, TrashRenderer trashRenderer,
		RenderRequest renderRequest,
		LiferayPortletResponse liferayPortletResponse, String viewContentURL) {

		_trashedModel = trashedModel;
		_trashRenderer = trashRenderer;
		_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
			renderRequest);
		_liferayPortletResponse = liferayPortletResponse;
		_viewContentURL = viewContentURL;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		try {
			ClassedModel classedModel = (ClassedModel)_trashedModel;

			TrashViewContentActionDropdownItemsProvider
				trashViewContentActionDropdownItemsProvider =
					new TrashViewContentActionDropdownItemsProvider(
						_liferayPortletRequest, _liferayPortletResponse,
						classedModel.getModelClassName(),
						_trashedModel.getTrashEntryClassPK());

			return trashViewContentActionDropdownItemsProvider.
				getActionDropdownItems();
		}
		catch (Exception e) {
			_log.error("Unable to get trashed model actions", e);
		}

		return Collections.emptyList();
	}

	@Override
	public String getDefaultEventHandler() {
		return TrashWebKeys.TRASH_ENTRIES_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		return _viewContentURL;
	}

	@Override
	public String getIcon() {
		try {
			return _trashRenderer.getIconCssClass();
		}
		catch (PortalException pe) {
			_log.error("Unable to get trash renderer icon css class", pe);
		}

		return "magic";
	}

	@Override
	public String getTitle() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _trashRenderer.getTitle(themeDisplay.getLocale());
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TrashContentHorizontalCard.class);

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final TrashedModel _trashedModel;
	private final TrashRenderer _trashRenderer;
	private final String _viewContentURL;

}