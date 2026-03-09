/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.service.impl;

import com.liferay.cookies.service.base.ConsentPreferenceServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=cookies",
		"json.web.service.context.path=ConsentPreference"
	},
	service = AopService.class
)
public class ConsentPreferenceServiceImpl
	extends ConsentPreferenceServiceBaseImpl {
}