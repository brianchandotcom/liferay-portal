/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.manager.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.Report;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

/**
 * @author Fábio Alves
 */
public interface ReportManager {

	public Report postReport(
			Company company, DTOConverterContext dtoConverterContext,
			Report report)
		throws Exception;

}