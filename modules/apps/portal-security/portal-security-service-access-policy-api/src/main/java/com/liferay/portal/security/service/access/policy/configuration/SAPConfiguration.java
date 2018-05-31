/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.service.access.policy.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Mika Koivisto
 */
@ExtendedObjectClassDefinition(category = "api-authentication")
@Meta.OCD(
	id = "com.liferay.portal.security.service.access.policy.configuration.SAPConfiguration",
	localization = "content/Language", name = "sap-configuration-name"
)
public interface SAPConfiguration {

	@Meta.AD(deflt = "true", name = "use-system-sap-entries", required = false)
	public boolean useSystemSAPEntries();

	@Meta.AD(
		deflt = "SYSTEM_DEFAULT", name = "system-default-sap-entry-name",
		required = false
	)
	public String systemDefaultSAPEntryName();

	@Meta.AD(
		deflt = "System Service Access Policy Applied on Every Request",
		name = "system-default-sap-entry-description", required = false
	)
	public String systemDefaultSAPEntryDescription();

	@Meta.AD(
		deflt = "com.liferay.portal.kernel.service.CountryService#getCountries\ncom.liferay.portal.kernel.service.RegionService#getRegions",
		name = "system-default-sap-entry-service-signatures", required = false
	)
	public String systemDefaultSAPEntryServiceSignatures();

	@Meta.AD(
		deflt = "SYSTEM_USER_PASSWORD",
		name = "system-user-password-sap-entry-name", required = false
	)
	public String systemUserPasswordSAPEntryName();

	@Meta.AD(
		deflt = "System Service Access Policy for Requests Authenticated Using User Password",
		name = "system-user-password-sap-entry-description", required = false
	)
	public String systemUserPasswordSAPEntryDescription();

	@Meta.AD(
		deflt = "*", name = "system-user-password-sap-entry-service-signatures",
		required = false
	)
	public String systemUserPasswordSAPEntryServiceSignatures();

}