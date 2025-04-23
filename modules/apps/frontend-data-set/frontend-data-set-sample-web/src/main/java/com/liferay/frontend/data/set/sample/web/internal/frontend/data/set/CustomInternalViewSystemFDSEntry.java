/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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
	property = "frontend.data.set.name=" + FDSSampleFDSNames.CUSTOM_INTERNAL_VIEW,
	service = SystemFDSEntry.class
)
public class CustomInternalViewSystemFDSEntry implements SystemFDSEntry {

	@Override
	public String getDescription() {
		return "This is the custom internal view sample of a frontend data " +
			"set.";
	}

	@Override
	public String getName() {
		return FDSSampleFDSNames.CUSTOM_INTERNAL_VIEW;
	}

	@Override
	public String getPropsTransformer() {
		return "{CustomInternalViewPropsTransformer} from frontend-data-set-" +
			"sample-web";
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
		return "rotate";
	}

	@Override
	public String getTitle() {
		return "Custom Internal View Sample";
	}

}