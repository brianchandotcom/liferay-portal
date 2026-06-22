/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noor Najjar
 */
@Component(
	property = "frontend.data.set.name=" + SEOStudioFDSNames.INSIGHT_TYPE_SECTION,
	service = FDSFilter.class
)
public class SeveritySelectionFDSFilter extends BaseListTypeSelectionFDSFilter {

	@Override
	public String getId() {
		return "severity";
	}

	@Override
	public String getLabel() {
		return "impact";
	}

	@Override
	protected String getListTypeDefinitionExternalReferenceCode() {
		return "L_SEO_STUDIO_SCAN_INSIGHT_SEVERITIES";
	}

}