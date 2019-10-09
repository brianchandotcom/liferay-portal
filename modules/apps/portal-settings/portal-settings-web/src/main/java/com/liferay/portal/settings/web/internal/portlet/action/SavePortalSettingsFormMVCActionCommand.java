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

package com.liferay.portal.settings.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ValidatorException;

/**
 * @author Michael C. Han
 */
public class SavePortalSettingsFormMVCActionCommand
	extends BasePortalSettingsFormMVCActionCommand {

	public SavePortalSettingsFormMVCActionCommand(
		PortalSettingsFormContributor portalSettingsFormContributor) {

		super(portalSettingsFormContributor);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			portalSettingsFormContributor.validateForm(
				actionRequest, actionResponse);

			if (!SessionErrors.isEmpty(actionRequest)) {
				throw new PortalException();
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			if (!hasPermissions(actionRequest, actionResponse, themeDisplay)) {
				return;
			}

			storeSettings(actionRequest, themeDisplay);
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass(), pe);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
	}

	@Override
	protected void doValidateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	}

	protected String getParameterNamespace() {
		return portalSettingsFormContributor.getParameterNamespace();
	}

	protected String getString(ActionRequest actionRequest, String name) {
		return ParamUtil.getString(
			actionRequest, getParameterNamespace() + name);
	}

	protected String[] getValues(ActionRequest actionRequest, String name) {
		String value = getString(actionRequest, name + "Indexes");

		if (Validator.isNull(value)) {
			return new String[] {getString(actionRequest, name)};
		}

		List<String> list = Arrays.asList(value.split(","));

		Stream<String> stream = list.stream();

		String[] ret = stream.map(
			index -> getString(actionRequest, name.concat(index))
		).filter(
			Validator::isNotNull
		).toArray(
			String[]::new
		);

		if (ret.length == 0) {
			return new String[] {StringPool.BLANK};
		}

		return ret;
	}

	protected void storeSettings(
			ActionRequest actionRequest, ThemeDisplay themeDisplay)
		throws IOException, SettingsException, ValidatorException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new CompanyServiceSettingsLocator(
				themeDisplay.getCompanyId(), getSettingsId()));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		SettingsDescriptor settingsDescriptor =
			SettingsFactoryUtil.getSettingsDescriptor(getSettingsId());

		for (String name : settingsDescriptor.getAllKeys()) {
			String[] values = getValues(actionRequest, name);

			String[] oldValues = settings.getValues(name, null);

			if (oldValues == null) {
				modifiableSettings.setValues(name, values);

				continue;
			}

			for (int i = 0; i < oldValues.length; i++) {
				if (i >= values.length) {
					modifiableSettings.setValues(name, values);

					break;
				}
				else if (values[i].equals(Portal.TEMP_OBFUSCATION_VALUE)) {
					values[i] = oldValues[i];
				}

				if (!values[i].equals(oldValues[i])) {
					modifiableSettings.setValues(name, values);

					break;
				}
				else if ((i == (oldValues.length - 1)) &&
						 (values.length > oldValues.length)) {

					modifiableSettings.setValues(name, values);
				}
			}
		}

		modifiableSettings.store();
	}

}