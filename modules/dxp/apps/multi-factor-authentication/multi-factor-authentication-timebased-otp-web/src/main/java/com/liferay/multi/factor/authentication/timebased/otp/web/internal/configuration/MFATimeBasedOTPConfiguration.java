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

package com.liferay.multi.factor.authentication.timebased.otp.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 * @author Marta Medio
 */
@ExtendedObjectClassDefinition(
	category = "multi-factor-authentication",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY,
	visibilityControllerKey = "multi-factor-authentication"
)
@Meta.OCD(
	id = "com.liferay.multi.factor.authentication.timebased.otp.web.internal.configuration.MFATimeBasedOTPConfiguration",
	localization = "content/Language",
	name = "mfa-timebased-otp-configuration-name"
)
public interface MFATimeBasedOTPConfiguration {

	@Meta.AD(
		deflt = "false", description = "mfa-timebased-otp-enabled-description",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "100", description = "order-description",
		id = "service.ranking", name = "order", required = false
	)
	public int order();

	@Meta.AD(
		deflt = "3000", description = "clock-skew-description",
		name = "clock-skew", required = false
	)
	public long clockSkew();

	@Meta.AD(
		deflt = "20", description = "algorithm-key-size-description",
		name = "algorithm-key-size", required = false
	)
	public int algorithmKeySize();

}