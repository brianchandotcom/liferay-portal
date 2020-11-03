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

package com.liferay.adaptive.media.web.internal.portlet.action;

import com.liferay.adaptive.media.exception.AMImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.web.internal.constants.AMPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Map;
import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AMPortletKeys.ADAPTIVE_MEDIA,
		"mvc.command.name=/adaptive_media/edit_image_configuration_entry"
	},
	service = MVCActionCommand.class
)
public class EditImageConfigurationEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doPermissionCheckedProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		hideDefaultErrorMessage(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String uuid = ParamUtil.getString(actionRequest, "uuid");

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", ParamUtil.getString(actionRequest, "maxHeight")
		).put(
			"max-width", ParamUtil.getString(actionRequest, "maxWidth")
		).build();

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				themeDisplay.getCompanyId(), uuid);

		boolean automaticUuid = ParamUtil.getBoolean(
			actionRequest, "automaticUuid");

		String newUuid = null;

		boolean autoModifiedUuid = false;

		if (automaticUuid) {
			String normalizedName =
				FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(name);

			newUuid = _getAutomaticUuid(
				themeDisplay.getCompanyId(), normalizedName, uuid);

			if (!newUuid.equals(normalizedName)) {
				autoModifiedUuid = true;
			}
		}
		else {
			newUuid = ParamUtil.getString(actionRequest, "newUuid");
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			if (amImageConfigurationEntryOptional.isPresent()) {
				AMImageConfigurationEntry amImageConfigurationEntry =
					amImageConfigurationEntryOptional.get();

				if (!_isConfigurationEntryEditable(
						themeDisplay.getCompanyId(),
						amImageConfigurationEntryOptional.get())) {

					newUuid = amImageConfigurationEntry.getUUID();

					properties = amImageConfigurationEntry.getProperties();

					autoModifiedUuid = false;
				}

				amImageConfigurationEntry =
					_amImageConfigurationHelper.updateAMImageConfigurationEntry(
						themeDisplay.getCompanyId(), uuid, name, description,
						newUuid, properties);

				if (autoModifiedUuid) {
					SessionMessages.add(
						actionRequest, "configurationEntryUpdatedAndIDRenamed",
						amImageConfigurationEntry);
				}
				else {
					SessionMessages.add(
						actionRequest, "configurationEntryUpdated",
						amImageConfigurationEntry);
				}
			}
			else {
				AMImageConfigurationEntry amImageConfigurationEntry =
					_amImageConfigurationHelper.addAMImageConfigurationEntry(
						themeDisplay.getCompanyId(), name, description, newUuid,
						properties);

				boolean addHighResolution = ParamUtil.getBoolean(
					actionRequest, "addHighResolution");

				if (addHighResolution) {
					AMImageConfigurationEntry
						highResolutionAMImageConfigurationEntry =
							_addHighResolutionConfigurationEntry(
								themeDisplay.getCompanyId(),
								amImageConfigurationEntry);

					SessionMessages.add(
						actionRequest, "highResolutionConfigurationEntryAdded",
						new AMImageConfigurationEntry[] {
							amImageConfigurationEntry,
							highResolutionAMImageConfigurationEntry
						});
				}
				else {
					if (autoModifiedUuid) {
						SessionMessages.add(
							actionRequest,
							"configurationEntryAddedAndIDRenamed",
							amImageConfigurationEntry);
					}
					else {
						SessionMessages.add(
							actionRequest, "configurationEntryAdded",
							amImageConfigurationEntry);
					}
				}
			}

			jsonObject.put("success", true);
		}
		catch (AMImageConfigurationException amImageConfigurationException) {
			SessionErrors.add(
				actionRequest, amImageConfigurationException.getClass());

			jsonObject.put("success", false);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private AMImageConfigurationEntry _addHighResolutionConfigurationEntry(
			long companyId, AMImageConfigurationEntry amImageConfigurationEntry)
		throws AMImageConfigurationException, IOException {

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

		int doubleMaxHeight =
			GetterUtil.getInteger(properties.get("max-height")) * 2;
		int doubleMaxWidth =
			GetterUtil.getInteger(properties.get("max-width")) * 2;

		properties.put("max-height", String.valueOf(doubleMaxHeight));
		properties.put("max-width", String.valueOf(doubleMaxWidth));

		String name = amImageConfigurationEntry.getName();
		String description = amImageConfigurationEntry.getDescription();
		String uuid = amImageConfigurationEntry.getUUID();

		return _amImageConfigurationHelper.addAMImageConfigurationEntry(
			companyId, name.concat("-2x"), "2x " + description,
			uuid.concat("-2x"), properties);
	}

	private String _getAutomaticUuid(
		long companyId, String normalizedName, String oldUuid) {

		String curUuid = normalizedName;

		for (int i = 1;; i++) {
			if (curUuid.equals(oldUuid)) {
				break;
			}

			Optional<AMImageConfigurationEntry>
				amImageConfigurationEntryOptional =
					_amImageConfigurationHelper.getAMImageConfigurationEntry(
						companyId, curUuid);

			if (!amImageConfigurationEntryOptional.isPresent()) {
				break;
			}

			String suffix = StringPool.DASH + i;

			curUuid = FriendlyURLNormalizerUtil.normalize(
				normalizedName + suffix);
		}

		return curUuid;
	}

	private boolean _isConfigurationEntryEditable(
		long companyId, AMImageConfigurationEntry amImageConfigurationEntry) {

		int entriesCount = _amImageEntryLocalService.getAMImageEntriesCount(
			companyId, amImageConfigurationEntry.getUUID());

		if (entriesCount == 0) {
			return true;
		}

		return false;
	}

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

}