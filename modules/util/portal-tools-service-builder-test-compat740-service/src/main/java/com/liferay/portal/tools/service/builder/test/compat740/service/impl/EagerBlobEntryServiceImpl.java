/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.tools.service.builder.test.compat740.service.base.EagerBlobEntryServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=sbcompat740",
		"json.web.service.context.path=EagerBlobEntry"
	},
	service = AopService.class
)
public class EagerBlobEntryServiceImpl extends EagerBlobEntryServiceBaseImpl {
}