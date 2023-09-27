/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.punchout.web.internal.helper;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaclyn Ong
 */
@Component(service = CommercePunchOutThemeHttpHelper.class)
public class CommercePunchOutThemeHttpHelper {

	public boolean punchOutSession(HttpServletRequest httpServletRequest) {
		if (_punchOutSessionHelper.punchOutEnabled(httpServletRequest) &&
			_punchOutSessionHelper.punchOutAllowed(httpServletRequest)) {

			return true;
		}

		return false;
	}

	@Reference
	private PunchOutSessionHelper _punchOutSessionHelper;

}