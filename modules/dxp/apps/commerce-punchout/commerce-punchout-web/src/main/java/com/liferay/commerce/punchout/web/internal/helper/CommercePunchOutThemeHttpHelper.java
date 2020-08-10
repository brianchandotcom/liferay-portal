/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.punchout.web.internal.helper;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaclyn Ong
 */
@Component(immediate = true, service = CommercePunchOutThemeHttpHelper.class)
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