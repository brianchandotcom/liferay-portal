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

package com.liferay.portal.workflow.kaleo.runtime.internal.settings;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Iván Zaera
 * @author Joshua Cords
 */
@Settings.Config(settingsIds = WorkflowConstants.SERVICE_NAME)
public class WorkflowGroupServiceSettings {

	public static WorkflowGroupServiceSettings getInstance(long companyId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new CompanyServiceSettingsLocator(
				companyId, WorkflowConstants.SERVICE_NAME));

		return new WorkflowGroupServiceSettings(settings);
	}

	public WorkflowGroupServiceSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	private static FallbackKeys _getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"emailFromAddress", PropsKeys.WORKFLOW_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		fallbackKeys.add(
			"emailFromName", PropsKeys.WORKFLOW_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);

		return fallbackKeys;
	}

	static {
		SettingsFactoryUtil.registerSettingsMetadata(
			WorkflowGroupServiceSettings.class, null, _getFallbackKeys());
	}

	private final TypedSettings _typedSettings;

}