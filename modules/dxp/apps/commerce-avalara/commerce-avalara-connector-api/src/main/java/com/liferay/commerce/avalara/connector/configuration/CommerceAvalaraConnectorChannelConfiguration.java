/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		deflt = "false", name = "disable-document-recording", required = false
	)
	public boolean disableDocumentRecording();

}