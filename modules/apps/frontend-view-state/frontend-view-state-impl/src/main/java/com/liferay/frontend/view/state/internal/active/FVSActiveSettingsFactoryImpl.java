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

package com.liferay.frontend.view.state.internal.active;

import com.liferay.frontend.view.state.active.FVSActiveSettings;
import com.liferay.frontend.view.state.active.FVSActiveSettingsFactory;
import com.liferay.frontend.view.state.model.FVSActiveEntry;
import com.liferay.frontend.view.state.model.FVSEntry;
import com.liferay.frontend.view.state.service.FVSActiveEntryLocalService;
import com.liferay.frontend.view.state.service.FVSEntryLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = FVSActiveSettingsFactory.class)
public class FVSActiveSettingsFactoryImpl implements FVSActiveSettingsFactory {

	public FVSActiveSettings getFVSActiveSettings(
		HttpServletRequest httpServletRequest, String clayDatasetDisplayId) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		FVSActiveEntry fvsActiveEntry =
			_fvsActiveEntryLocalService.fetchFVSActiveEntry(
				themeDisplay.getUserId(), clayDatasetDisplayId,
				themeDisplay.getPlid(), portletDisplay.getId());

		FVSEntry fvsEntry;

		if (fvsActiveEntry == null) {
			fvsEntry = _fvsEntryLocalService.createFVSEntry("{}");

			_fvsEntryLocalService.updateFVSEntry(fvsEntry);

			fvsActiveEntry = _fvsActiveEntryLocalService.createFVSActiveEntry(
				themeDisplay.getUserId(), fvsEntry.getFvsEntryId(),
				clayDatasetDisplayId, themeDisplay.getPlid(),
				portletDisplay.getId());

			_fvsActiveEntryLocalService.updateFVSActiveEntry(fvsActiveEntry);
		}
		else {
			fvsEntry = _fvsEntryLocalService.fetchFVSEntry(
				fvsActiveEntry.getFvsEntryId());
		}

		return new FVSActiveSettingsImpl(fvsEntry);
	}

	public void storeFVSActiveSettings(FVSActiveSettings fvsActiveSettings) {
		FVSActiveSettingsImpl fvsActiveSettingsImpl =
			(FVSActiveSettingsImpl)fvsActiveSettings;

		_fvsEntryLocalService.updateFVSEntry(
			fvsActiveSettingsImpl.getFVSEntry());
	}

	@Reference
	private FVSActiveEntryLocalService _fvsActiveEntryLocalService;

	@Reference
	private FVSEntryLocalService _fvsEntryLocalService;

}