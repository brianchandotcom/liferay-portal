/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.email.otp.web.internal.constants;

/**
 * @author Arthur Chan
 */
public interface MFAEmailOTPEventTypes {

	public static final String MFA_EMAIL_OTP_NOT_VERIFIED =
		"MFA_EMAIL_OTP_NOT_VERIFIED";

	public static final String MFA_EMAIL_OTP_VERIFICATION_FAILURE =
		"MFA_EMAIL_OTP_VERIFICATION_FAILURE";

	public static final String MFA_EMAIL_OTP_VERIFICATION_SUCCESS =
		"MFA_EMAIL_OTP_VERIFICATION_SUCCESS";

	public static final String MFA_EMAIL_OTP_VERIFIED =
		"MFA_EMAIL_OTP_VERIFIED";

}