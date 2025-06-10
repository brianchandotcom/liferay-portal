/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.impl;

import com.liferay.exportimport.report.service.base.ImportReportEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(
	property = "model.class.name=com.liferay.exportimport.report.model.ImportReportEntry",
	service = AopService.class
)
public class ImportReportEntryLocalServiceImpl
	extends ImportReportEntryLocalServiceBaseImpl {
}