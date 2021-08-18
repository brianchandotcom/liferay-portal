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

package com.liferay.multi.factor.authentication.email.otp.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Tomas Polesovsky
 * @author Marta Medio
 */
@ExtendedObjectClassDefinition(
	category = "multi-factor-authentication",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.multi.factor.authentication.email.otp.configuration.MFAEmailOTPConfiguration",
	localization = "content/Language", name = "mfa-email-otp-configuration-name"
)
public interface MFAEmailOTPConfiguration {

	@Meta.AD(
		deflt = "false", description = "mfa-email-otp-enabled-description",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "10", description = "order-description", id = "service.ranking",
		name = "order", required = false
	)
	public int order();

	@Meta.AD(
		deflt = "6", description = "otp-size-description", name = "otp-size",
		required = false
	)
	public int otpSize();

	@Meta.AD(
		deflt = "30", description = "resend-email-timeout-description",
		name = "resend-email-timeout", required = false
	)
	public long resendEmailTimeout();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		description = "set-the-sender-address-on-the-one-time-password-email",
		name = "email-from-field[template]", required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		name = "email-from-name", required = false
	)
	public String emailFromName();

	@Meta.AD(
		deflt = "${resource:com/liferay/multi/factor/authentication/email/otp/configuration/dependencies/email_otp_sent_subject.tmpl}",
		name = "email-otp-sent-subject", required = false
	)
	public LocalizedValuesMap emailOTPSentSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/multi/factor/authentication/email/otp/configuration/dependencies/email_otp_sent_body.tmpl}",
		name = "email-otp-sent-body", required = false
	)
	public LocalizedValuesMap emailOTPSentBody();

	@Meta.AD(
		deflt = "-1", description = "failed-attempts-allowed-description",
		name = "failed-attempts-allowed", required = false
	)
	public int failedAttemptsAllowed();

	@Meta.AD(
		deflt = "-1", description = "retry-timeout-description",
		name = "retry-timeout", required = false
	)
	public long retryTimeout();

}