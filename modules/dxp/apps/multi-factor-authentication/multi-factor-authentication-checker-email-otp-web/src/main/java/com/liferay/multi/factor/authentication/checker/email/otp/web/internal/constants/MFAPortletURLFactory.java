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

package com.liferay.multi.factor.authentication.checker.email.otp.web.internal.constants;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public interface MFAPortletURLFactory {

	public static final String MFA_USER_ID =
		MFAPortletURLFactory.class.getName() + "#MFA_USER_ID";

	public LiferayPortletURL createVerifyURL(
		HttpServletRequest httpServletRequest, String redirectURL, long userId);

}