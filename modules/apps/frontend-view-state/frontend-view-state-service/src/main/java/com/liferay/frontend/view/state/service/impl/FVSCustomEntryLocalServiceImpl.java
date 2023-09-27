/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.service.impl;

import com.liferay.frontend.view.state.service.base.FVSCustomEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.frontend.view.state.model.FVSCustomEntry",
	service = AopService.class
)
public class FVSCustomEntryLocalServiceImpl
	extends FVSCustomEntryLocalServiceBaseImpl {
}