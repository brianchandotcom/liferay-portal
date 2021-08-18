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

package com.liferay.osb.portal.instance.provisioning.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Ivica Cardic
 * @author Eduardo García
 */
@ExtendedObjectClassDefinition(category = "osb-portal-instance-provisioning")
@Meta.OCD(
	id = "com.liferay.osb.portal.instance.provisioning.configuration.OSBPortalInstanceProvisioningConfiguration",
	localization = "content/Language",
	name = "osb-portal-instance-provisioning-configuration-name"
)
public interface OSBPortalInstanceProvisioningConfiguration {

	@Meta.AD(
		deflt = "localhost",
		name = "osb-portal-instance-provisioning-remote-host", required = false
	)
	public String remoteHost();

	@Meta.AD(
		deflt = "8080", name = "osb-portal-instance-provisioning-remote-port",
		required = false
	)
	public int remotePort();

	@Meta.AD(
		deflt = "https",
		name = "osb-portal-instance-provisioning-remote-schema",
		required = false
	)
	public String remoteSchema();

	@Meta.AD(
		deflt = "test@liferay.com",
		name = "osb-portal-instance-provisioning-remote-login", required = false
	)
	public String remoteLogin();

	@Meta.AD(
		deflt = "test",
		name = "osb-portal-instance-provisioning-remote-password",
		required = false
	)
	public String remotePassword();

}