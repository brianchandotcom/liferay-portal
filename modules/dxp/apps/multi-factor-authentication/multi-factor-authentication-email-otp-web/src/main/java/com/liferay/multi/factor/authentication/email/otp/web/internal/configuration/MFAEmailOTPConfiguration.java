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

package com.liferay.multi.factor.authentication.email.otp.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Tomas Polesovsky
 */
@Meta.OCD(
	id = "com.liferay.multi.factor.authentication.email.otp.web.internal.configuration.MFAEmailOTPConfiguration",
	localization = "content/Language", name = "mfa-email-otp-configuration-name"
)
public interface MFAEmailOTPConfiguration {

	@Meta.AD(deflt = "-1", name = "retry-timeout", required = false)
	public long retryTimeout();

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(deflt = "30", name = "resend-email-timeout", required = false)
	public long resendEmailTimeout();

	@Meta.AD(
		deflt = "-1", name = "validation-expiration-time", required = false
	)
	public long validationExpirationTime();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		name = "email-template-from", required = false
	)
	public String emailTemplateFrom();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		name = "email-template-from-sender", required = false
	)
	public String emailTemplateFromName();

	@Meta.AD(deflt = "", name = "email-template-subject", required = false)
	public String emailTemplateSubject();

	@Meta.AD(deflt = "", name = "email-template-body", required = false)
	public String emailTemplateBody();

	@Meta.AD(deflt = "-1", name = "failed-attempts-allowed", required = false)
	public int failedAttemptsAllowed();

	@Meta.AD(deflt = "6", name = "otp-size", required = false)
	public int otpSize();

	public final String DEFAULT_EMAIL_OTP_BODY =
		"/com/liferay/multi/factor/authentication/email/otp/web/internal" +
			"/configuration/dependencies/email_otp_sent_body.tmpl";

	public final String DEFAULT_EMAIL_OTP_SUBJECT =
		"/com/liferay/multi/factor/authentication/email/otp/web/internal" +
			"/configuration/dependencies/email_otp_sent_subject.tmpl";

}