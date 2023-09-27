/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.settings;

import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "settingsId=" + WorkflowConstants.SERVICE_NAME,
	service = FallbackKeys.class
)
public class WorkflowGroupServiceSettingsFallbackKeys extends FallbackKeys {

	@Activate
	protected void activate() {
		add(
			"emailFromAddress", PropsKeys.WORKFLOW_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		add(
			"emailFromName", PropsKeys.WORKFLOW_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
	}

}