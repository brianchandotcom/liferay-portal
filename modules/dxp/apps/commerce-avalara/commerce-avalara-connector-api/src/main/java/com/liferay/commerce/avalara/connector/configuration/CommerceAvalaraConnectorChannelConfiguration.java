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

/**
 * @author Calvin Keum
 */
@Meta.OCD(
	id = "com.liferay.commerce.avalara.connector.configuration.CommerceAvalaraConnectorChannelConfiguration",
	localization = "content/Language",
	name = "commerce-avalara-connector-channel-configuration-name"
)
public interface CommerceAvalaraConnectorChannelConfiguration {

	@Meta.AD(name = "company-code", required = false)
	public String companyCode();

	@Meta.AD(
		deflt = "false", name = "disabled-document-recording", required = false
	)
	public boolean disabledDocumentRecording();

}