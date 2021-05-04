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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
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

		if (datasetViewActiveEntry != null) {
			datasetViewStateEntry =
				_datasetViewStateEntryLocalService.fetchDatasetViewStateEntry(
					datasetViewActiveEntry.getDatasetViewStateEntryId());
		}
		else {
			datasetViewStateEntry = _upgradeDatasetViewStateEntry(
				httpServletRequest, datasetDisplayId);

			if (datasetViewStateEntry == null) {
				datasetViewStateEntry = _createDatasetViewStateEntry(
					datasetDisplayId, themeDisplay.getPlid(),
					portletDisplay.getId(), themeDisplay.getUserId(), "{}");
			}
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

	private DatasetViewStateEntry _createDatasetViewStateEntry(
		String datasetDisplayId, long plid, String portletId, long userId,
		String viewState) {

		DatasetViewStateEntry datasetViewStateEntry =
			_datasetViewStateEntryLocalService.createDatasetViewStateEntry(
				viewState);

		_datasetViewStateEntryLocalService.updateDatasetViewStateEntry(
			datasetViewStateEntry);

		DatasetViewActiveEntry datasetViewActiveEntry =
			_datasetViewActiveEntryLocalService.createDatasetViewActiveEntry(
				datasetDisplayId,
				datasetViewStateEntry.getDatasetViewStateEntryId(), plid,
				portletId, userId);

		_datasetViewActiveEntryLocalService.updateDatasetViewActiveEntry(
			datasetViewActiveEntry);

		return datasetViewStateEntry;
	}

	private String _getClayDataSetDisplaySettingsNamespace(
		String datasetDisplayId, long plid, String portletId) {

		StringBundler sb = new StringBundler(8);

		sb.append("com.liferay.frontend.taglib.clay.servlet.taglib.");
		sb.append("DataSetDisplayTag");
		sb.append(StringPool.POUND);
		sb.append(_portal.getPortletNamespace(portletId));
		sb.append(StringPool.POUND);
		sb.append(plid);
		sb.append(StringPool.POUND);
		sb.append(datasetDisplayId);

		return sb.toString();
	}

	private DatasetViewStateEntry _upgradeDatasetViewStateEntry(
		HttpServletRequest httpServletRequest, String datasetDisplayId) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String clayDataSetDisplaySettingsNamespace =
			_getClayDataSetDisplaySettingsNamespace(
				datasetDisplayId, themeDisplay.getPlid(),
				portletDisplay.getId());

		String activeViewSettingsJSON = portalPreferences.getValue(
			clayDataSetDisplaySettingsNamespace, "activeViewSettingsJSON");

		if (Validator.isNotNull(activeViewSettingsJSON)) {
			DatasetViewStateEntry datasetViewStateEntry =
				_createDatasetViewStateEntry(
					datasetDisplayId, themeDisplay.getPlid(),
					portletDisplay.getId(), themeDisplay.getUserId(),
					activeViewSettingsJSON);

			portalPreferences.setValue(
				clayDataSetDisplaySettingsNamespace, "activeViewSettingsJSON",
				null);

			return datasetViewStateEntry;
		}

		return null;
	}

	@Reference
	private DatasetViewActiveEntryLocalService
		_datasetViewActiveEntryLocalService;

	@Reference
	private DatasetViewStateEntryLocalService
		_datasetViewStateEntryLocalService;

	@Reference
	private Portal _portal;

}