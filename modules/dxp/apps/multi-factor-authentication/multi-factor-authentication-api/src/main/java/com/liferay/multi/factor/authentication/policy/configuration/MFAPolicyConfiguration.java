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

package com.liferay.multi.factor.authentication.policy.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(
	category = "multi-factor-authentication",
	factoryInstanceLabelAttribute = "name"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.multi.factor.authentication.policy.configuration.MFAPolicyConfiguration",
	localization = "content/Language", name = "mfa-policy-configuration-name"
)
@ProviderType
public interface MFAPolicyConfiguration {

	@Meta.AD(
		description = "mfa-policy-name-description", name = "mfa-policy-name",
		required = false
	)
	public String name();

	@Meta.AD(
		description = "mfa-checker-names-description",
		name = "mfa-checker-names", required = false
	)
	public String[] checkerNames();

}