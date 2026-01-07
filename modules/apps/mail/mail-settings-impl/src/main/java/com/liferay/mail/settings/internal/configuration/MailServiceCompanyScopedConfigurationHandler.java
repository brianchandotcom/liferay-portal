/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.settings.internal.configuration;

import com.liferay.mail.kernel.service.MailService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jiefeng Wu
 */
@Component(
	configurationPid = "com.liferay.mail.settings.configuration.MailSettingCompanyConfiguration.scoped",
	service = {}
)
public class MailServiceCompanyScopedConfigurationHandler {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mailService.clearSession(
			GetterUtil.getLong(properties.get("companyId")));
	}

	@Reference
	private MailService _mailService;

}