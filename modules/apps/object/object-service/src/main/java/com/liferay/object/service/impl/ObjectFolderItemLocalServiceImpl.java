/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.object.service.base.ObjectFolderItemLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Murilo Stodolni
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectFolderItem",
	service = AopService.class
)
public class ObjectFolderItemLocalServiceImpl
	extends ObjectFolderItemLocalServiceBaseImpl {
}