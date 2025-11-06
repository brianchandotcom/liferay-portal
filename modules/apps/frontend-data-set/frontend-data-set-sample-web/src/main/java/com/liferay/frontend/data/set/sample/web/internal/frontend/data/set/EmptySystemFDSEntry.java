/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.frontend.data.set;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Sanz
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.EMPTY,
	service = SystemFDSEntry.class
)
public class EmptySystemFDSEntry implements SystemFDSEntry {

	@Override
	public String getAdditionalAPIURLParameters(
		HttpServletRequest httpServletRequest) {

		return "filter=('color' eq 'empty')";
	}

	@Override
	public String getDescription() {
		return "This is the \"Empty\" sample of a frontend data set.";
	}

	@Override
	public boolean getHideManagementBarInEmptyState() {
		return true;
	}

	@Override
	public String getName() {
		return FDSSampleFDSNames.EMPTY;
	}

	@Override
	public String getRESTApplication() {
		return "/c/fdssamples";
	}

	@Override
	public String getRESTEndpoint() {
		return "/";
	}

	@Override
	public String getRESTSchema() {
		return "FDSSample";
	}

	@Override
	public String getSymbol() {
		return "rectangle";
	}

	@Override
	public String getTitle() {
		return "Empty Sample";
	}

}