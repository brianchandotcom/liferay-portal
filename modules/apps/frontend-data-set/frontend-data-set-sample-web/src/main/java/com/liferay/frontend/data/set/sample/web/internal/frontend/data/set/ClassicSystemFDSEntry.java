/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.frontend.data.set;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marko Cikos
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.CLASSIC,
	service = SystemFDSEntry.class
)
public class ClassicSystemFDSEntry implements SystemFDSEntry {

	@Override
	public String getAdditionalAPIURLParameters() {
		return "groupId={siteId}";
	}

	@Override
	public String getDescription() {
		return "This is the \"Classic\" sample of a frontend data set.";
	}

	@Override
	public String getName() {
		return FDSSampleFDSNames.CLASSIC;
	}

	@Override
	public String getRESTApplication() {
		return "/frontend-data-set-taglib/app/data-set/com_liferay_frontend_" +
			"data_set_sample_web_internal_portlet_FDSSamplePortlet-classic";
	}

	@Override
	public String getRESTEndpoint() {
		return "/com_liferay_frontend_data_set_sample_web_internal_portlet_" +
			"FDSSamplePortlet-classic";
	}

	@Override
	public String getRESTSchema() {
		return FDSSampleFDSNames.CLASSIC;
	}

	@Override
	public String getTitle() {
		return "Classic Sample";
	}

}