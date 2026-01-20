/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.frontend.data.set.url;

import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "fds.rest.application.key=/change-tracking-rest/v1.0/CTEntry",
	service = FDSAPIURLResolver.class
)
public class CTEntryFDSAPIURLResolver implements FDSAPIURLResolver {

	@Override
	public String getSchema() {
		return "CTEntry";
	}

	@Override
	public String resolve(String baseURL, HttpServletRequest httpServletRequest)
		throws PortalException {

		return StringUtil.replace(
			baseURL, "{ctCollectionId}",
			ParamUtil.getString(httpServletRequest, "ctCollectionId"));
	}

}