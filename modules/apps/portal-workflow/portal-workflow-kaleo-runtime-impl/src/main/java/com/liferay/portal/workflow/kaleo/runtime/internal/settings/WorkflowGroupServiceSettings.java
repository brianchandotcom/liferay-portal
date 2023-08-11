/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.settings;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Iván Zaera
 * @author Joshua Cords
 */
@Settings.Config
public class WorkflowGroupServiceSettings {

	public static WorkflowGroupServiceSettings getInstance(long companyId)
		throws PortalException {

		Settings settings = FallbackKeysSettingsUtil.getSettings(
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

	private final TypedSettings _typedSettings;

}