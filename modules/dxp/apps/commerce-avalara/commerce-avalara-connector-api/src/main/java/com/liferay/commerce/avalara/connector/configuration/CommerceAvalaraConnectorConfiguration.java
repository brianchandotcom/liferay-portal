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

package com.liferay.commerce.avalara.connector.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Calvin Keum
 */
@ExtendedObjectClassDefinition(
	category = "tax-rate", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.avalara.connector.configuration.CommerceAvalaraConnectorConfiguration",
	localization = "content/Language",
	name = "commerce-avalara-connecto-configuration-name"
)
public interface CommerceAvalaraConnectorConfiguration {

	@Meta.AD(name = "account-number", required = false)
	public String accountNumber();

	@Meta.AD(name = "license-key", required = false)
	public String licenseKey();

	@Meta.AD(name = "service-url", required = false)
	public String serviceURL();

	@Meta.AD(name = "company-code", required = false)
	public String companyCode();

	@Meta.AD(
		deflt = "false", name = "disabled-document-recording", required = false
	)
	public boolean disabledDocumentRecording();

}