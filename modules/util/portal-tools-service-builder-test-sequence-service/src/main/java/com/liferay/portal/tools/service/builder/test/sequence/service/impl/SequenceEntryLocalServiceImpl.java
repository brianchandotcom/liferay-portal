/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.sequence.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.tools.service.builder.test.sequence.service.base.SequenceEntryLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.tools.service.builder.test.sequence.model.SequenceEntry",
	service = AopService.class
)
public class SequenceEntryLocalServiceImpl
	extends SequenceEntryLocalServiceBaseImpl {
}