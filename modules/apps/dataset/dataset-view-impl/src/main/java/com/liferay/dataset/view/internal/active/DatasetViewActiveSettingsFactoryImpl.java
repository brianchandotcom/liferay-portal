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

package com.liferay.dataset.view.internal.active;

import com.liferay.dataset.view.active.DatasetViewActiveSettings;
import com.liferay.dataset.view.active.DatasetViewActiveSettingsFactory;
import com.liferay.dataset.view.model.DatasetViewActiveEntry;
import com.liferay.dataset.view.model.DatasetViewStateEntry;
import com.liferay.dataset.view.service.DatasetViewActiveEntryLocalService;
import com.liferay.dataset.view.service.DatasetViewStateEntryLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DatasetViewActiveSettingsFactory.class)
public class DatasetViewActiveSettingsFactoryImpl
	implements DatasetViewActiveSettingsFactory {

	@Override
	public DatasetViewActiveSettings getDatasetViewActiveSettings(
		HttpServletRequest httpServletRequest, String datasetDisplayId) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		DatasetViewActiveEntry datasetViewActiveEntry =
			_datasetViewActiveEntryLocalService.fetchDatasetViewActiveEntry(
				datasetDisplayId, themeDisplay.getPlid(),
				portletDisplay.getId(), themeDisplay.getUserId());

		DatasetViewStateEntry datasetViewStateEntry;

		if (datasetViewActiveEntry == null) {
			datasetViewStateEntry =
				_datasetViewStateEntryLocalService.createDatasetViewStateEntry(
					"{}");

			_datasetViewStateEntryLocalService.updateDatasetViewStateEntry(
				datasetViewStateEntry);

			datasetViewActiveEntry =
				_datasetViewActiveEntryLocalService.
					createDatasetViewActiveEntry(
						datasetDisplayId,
						datasetViewStateEntry.getDatasetViewStateEntryId(),
						themeDisplay.getPlid(), portletDisplay.getId(),
						themeDisplay.getUserId());

			_datasetViewActiveEntryLocalService.updateDatasetViewActiveEntry(
				datasetViewActiveEntry);
		}
		else {
			datasetViewStateEntry =
				_datasetViewStateEntryLocalService.fetchDatasetViewStateEntry(
					datasetViewActiveEntry.getDatasetViewStateEntryId());
		}

		return new DatasetViewActiveSettingsImpl(datasetViewStateEntry);
	}

	@Override
	public void storeDatasetViewActiveSettings(
		DatasetViewActiveSettings datasetViewActiveSettings) {

		DatasetViewActiveSettingsImpl datasetViewActiveSettingsImpl =
			(DatasetViewActiveSettingsImpl)datasetViewActiveSettings;

		_datasetViewStateEntryLocalService.updateDatasetViewStateEntry(
			datasetViewActiveSettingsImpl.getDatasetViewStateEntry());
	}

	@Reference
	private DatasetViewActiveEntryLocalService
		_datasetViewActiveEntryLocalService;

	@Reference
	private DatasetViewStateEntryLocalService
		_datasetViewStateEntryLocalService;

}