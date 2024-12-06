/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.service.base.CTScoreLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTScore",
	service = AopService.class
)
public class CTScoreLocalServiceImpl extends CTScoreLocalServiceBaseImpl {
}