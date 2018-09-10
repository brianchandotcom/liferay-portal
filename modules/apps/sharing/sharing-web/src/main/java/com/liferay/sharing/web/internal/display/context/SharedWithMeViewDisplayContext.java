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
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.renderer.SharingEntryEditRenderer;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.interpreter.SharingEntryInterpreterTracker;

import java.util.List;
import java.util.Optional;

import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class SharedWithMeViewDisplayContext {

	public SharedWithMeViewDisplayContext(
		ThemeDisplay themeDisplay,
		SharingEntryLocalService sharingEntryLocalService,
		SharingEntryInterpreterTracker sharingEntryInterpreterTracker) {

		_themeDisplay = themeDisplay;
		_sharingEntryLocalService = sharingEntryLocalService;
		_sharingEntryInterpreterTracker = sharingEntryInterpreterTracker;
	}

	public String getAssetTypeTitle(SharingEntry sharingEntry) {
		Optional<SharingEntryInterpreter> sharingEntryInterpreter =
			_sharingEntryInterpreterTracker.getSharingEntryInterpreter(
				sharingEntry.getClassNameId());

		return sharingEntryInterpreter.map(
			curSharingEntryInterpreter ->
				curSharingEntryInterpreter.getAssetTypeTitle(
					sharingEntry, _themeDisplay.getLocale())
		).orElse(
			StringPool.BLANK
		);
	}

	public String getTitle(SharingEntry sharingEntry) {
		Optional<SharingEntryInterpreter> sharingEntryInterpreterOptional =
			_sharingEntryInterpreterTracker.getSharingEntryInterpreter(
				sharingEntry.getClassNameId());

		return sharingEntryInterpreterOptional.map(
			curSharingEntryInterpreter ->
				curSharingEntryInterpreter.getTitle(sharingEntry)
		).orElse(
			StringPool.BLANK
		);
	}

	public PortletURL getURLEdit(
			SharingEntry sharingEntry,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		Optional<SharingEntryInterpreter> sharingEntryInterpreterOptional =
			_sharingEntryInterpreterTracker.getSharingEntryInterpreter(
				sharingEntry.getClassNameId());

		if (!sharingEntryInterpreterOptional.isPresent()) {
			return null;
		}

		SharingEntryInterpreter sharingEntryInterpreter =
			sharingEntryInterpreterOptional.get();

		SharingEntryEditRenderer sharingEntryEditRenderer =
			sharingEntryInterpreter.getSharingEntryEditRenderer();

		return sharingEntryEditRenderer.getURLEdit(
			sharingEntryInterpreter.getEntry(sharingEntry),
			liferayPortletRequest, liferayPortletResponse);
	}

	public boolean hasEditPermission(SharingEntry sharingEntry) {
		return _sharingEntryLocalService.hasSharingPermission(
			sharingEntry, SharingEntryActionKey.UPDATE);
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

	private final SharingEntryInterpreterTracker
		_sharingEntryInterpreterTracker;
	private final SharingEntryLocalService _sharingEntryLocalService;
	private final ThemeDisplay _themeDisplay;

}