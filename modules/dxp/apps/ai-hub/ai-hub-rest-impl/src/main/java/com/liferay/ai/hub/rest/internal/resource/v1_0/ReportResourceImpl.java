/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.Report;
import com.liferay.ai.hub.rest.internal.util.ContextUserUtil;
import com.liferay.ai.hub.rest.manager.v1_0.ReportManager;
import com.liferay.ai.hub.rest.resource.v1_0.ReportResource;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Fábio Alves
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/report.properties",
	scope = ServiceScope.PROTOTYPE, service = ReportResource.class
)
public class ReportResourceImpl extends BaseReportResourceImpl {

	@Override
	public Report postReport(Report report) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		return _reportManager.postReport(
			contextCompany,
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest, null,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				ContextUserUtil.initContextUser(
					report.getChatbotExternalReferenceCode(),
					contextCompany.getCompanyId(), contextUser)),
			report);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ReportManager _reportManager;

}