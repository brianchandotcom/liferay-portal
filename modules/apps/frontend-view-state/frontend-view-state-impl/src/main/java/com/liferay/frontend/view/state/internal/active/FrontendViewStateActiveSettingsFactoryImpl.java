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

import com.liferay.frontend.view.state.active.FrontendViewStateActiveSettings;
import com.liferay.frontend.view.state.active.FrontendViewStateActiveSettingsFactory;
import com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry;
import com.liferay.frontend.view.state.model.FrontendViewStateEntry;
import com.liferay.frontend.view.state.service.FrontendViewStateActiveEntryLocalService;
import com.liferay.frontend.view.state.service.FrontendViewStateEntryLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = FrontendViewStateActiveSettingsFactory.class)
public class FrontendViewStateActiveSettingsFactoryImpl
	implements FrontendViewStateActiveSettingsFactory {

	public FrontendViewStateActiveSettings getFrontendViewStateActiveSettings(
		HttpServletRequest httpServletRequest, String datasetDisplayId) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		FrontendViewStateActiveEntry frontendViewStateActiveEntry =
			_frontendViewStateActiveEntryLocalService.
				fetchFrontendViewStateActiveEntry(
					datasetDisplayId, themeDisplay.getPlid(),
					portletDisplay.getId(), themeDisplay.getUserId());

		FrontendViewStateEntry frontendViewStateEntry;

		if (frontendViewStateActiveEntry == null) {
			frontendViewStateEntry =
				_frontendViewStateEntryLocalService.
					createFrontendViewStateEntry("{}");

			_frontendViewStateEntryLocalService.updateFrontendViewStateEntry(
				frontendViewStateEntry);

			frontendViewStateActiveEntry =
				_frontendViewStateActiveEntryLocalService.
					createFrontendViewStateActiveEntry(
						datasetDisplayId,
						frontendViewStateEntry.getFrontendViewStateEntryId(),
						themeDisplay.getPlid(), portletDisplay.getId(),
						themeDisplay.getUserId());

			_frontendViewStateActiveEntryLocalService.
				updateFrontendViewStateActiveEntry(
					frontendViewStateActiveEntry);
		}
		else {
			frontendViewStateEntry =
				_frontendViewStateEntryLocalService.fetchFrontendViewStateEntry(
					frontendViewStateActiveEntry.getFrontendViewStateEntryId());
		}

		return new FrontendViewStateActiveSettingsImpl(frontendViewStateEntry);
	}

	public void storeFrontendViewStateActiveSettings(
		FrontendViewStateActiveSettings frontendViewStateActiveSettings) {

		FrontendViewStateActiveSettingsImpl
			frontendViewStateActiveSettingsImpl =
				(FrontendViewStateActiveSettingsImpl)
					frontendViewStateActiveSettings;

		_frontendViewStateEntryLocalService.updateFrontendViewStateEntry(
			frontendViewStateActiveSettingsImpl.getFrontendViewStateEntry());
	}

	@Reference
	private FrontendViewStateActiveEntryLocalService
		_frontendViewStateActiveEntryLocalService;

	@Reference
	private FrontendViewStateEntryLocalService
		_frontendViewStateEntryLocalService;

}