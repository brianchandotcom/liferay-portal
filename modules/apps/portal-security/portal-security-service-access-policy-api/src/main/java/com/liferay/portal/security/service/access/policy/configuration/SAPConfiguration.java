/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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