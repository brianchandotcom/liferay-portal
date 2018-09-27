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

package com.liferay.sharing.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.web.renderer.SharingEntryEditRenderer;

import java.util.List;
import java.util.function.Function;

import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class SharedWithMeViewDisplayContext {

	public SharedWithMeViewDisplayContext(
		ThemeDisplay themeDisplay,
		SharingEntryLocalService sharingEntryLocalService,
		Function<SharingEntry, SharingEntryInterpreter<Object>>
			sharingEntryInterpreterFunction) {

		_themeDisplay = themeDisplay;
		_sharingEntryLocalService = sharingEntryLocalService;
		_sharingEntryInterpreterFunction = sharingEntryInterpreterFunction;
	}

	public String getAssetTypeTitle(SharingEntry sharingEntry) {
		SharingEntryInterpreter<Object> sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if (sharingEntryInterpreter == null) {
			return StringPool.BLANK;
		}

		return sharingEntryInterpreter.getAssetTypeTitle(
			sharingEntry, _themeDisplay.getLocale());
	}

	public String getTitle(SharingEntry sharingEntry) {
		SharingEntryInterpreter<Object> sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if (sharingEntryInterpreter == null) {
			return StringPool.BLANK;
		}

		return sharingEntryInterpreter.getTitle(sharingEntry);
	}

	public PortletURL getURLEdit(
			SharingEntry sharingEntry,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		SharingEntryInterpreter<Object> sharingEntryInterpreter =
			_sharingEntryInterpreterFunction.apply(sharingEntry);

		if (sharingEntryInterpreter == null) {
			return null;
		}

		SharingEntryEditRenderer<Object> sharingEntryEditRenderer =
			sharingEntryInterpreter.getSharingEntryEditRenderer();

		return sharingEntryEditRenderer.getURLEdit(
			sharingEntryInterpreter.getEntry(sharingEntry),
			liferayPortletRequest, liferayPortletResponse);
	}

	public boolean hasEditPermission(SharingEntry sharingEntry) {
		return _sharingEntryLocalService.hasSharingPermission(
			sharingEntry, SharingEntryAction.UPDATE);
	}

	public void populateResults(SearchContainer<SharingEntry> searchContainer) {
		int total = _sharingEntryLocalService.countToUserSharingEntries(
			_themeDisplay.getUserId());

		searchContainer.setTotal(total);

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(
				_themeDisplay.getUserId(), searchContainer.getStart(),
				searchContainer.getEnd());

		searchContainer.setResults(sharingEntries);
	}

	private final Function<SharingEntry, SharingEntryInterpreter<Object>>
		_sharingEntryInterpreterFunction;
	private final SharingEntryLocalService _sharingEntryLocalService;
	private final ThemeDisplay _themeDisplay;

}