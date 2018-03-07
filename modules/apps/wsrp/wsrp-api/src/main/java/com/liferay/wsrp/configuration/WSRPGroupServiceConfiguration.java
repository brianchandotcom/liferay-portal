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

package com.liferay.wsrp.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Peter Fellwock
 */
@ExtendedObjectClassDefinition(
	category = "wsrp", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.wsrp.configuration.WSRPGroupServiceConfiguration",
	localization = "content/Language", name = "wsrp-service-configuration-name"
)
public interface WSRPGroupServiceConfiguration {

	@Meta.AD(deflt = "", name = "consumer-request-extensions", required = false)
	public String[] consumerRequestExtensions();

	@Meta.AD(
		deflt = "com.liferay.wsrp.util.AttributeExtensionHelper",
		name = "extension-helper-impl", required = false
	)
	public String extensionHelperImpl();

	@Meta.AD(
		deflt = "0", name = "failed-consumers-check-interval", required = false
	)
	public int failedConsumersCheckInterval();

	@Meta.AD(
		deflt = "127.0.0.1|SERVER_IP", name = "proxy-url-ips-allowed",
		required = false
	)
	public String[] proxyUrlIpsAllowed();

	@Meta.AD(
		deflt = "false", name = "secure-resource-urls-enabled", required = false
	)
	public boolean secureResourceUrlsEnabled();

	@Meta.AD(
		deflt = "salt", name = "secure-resource-urls-salt", required = false
	)
	public String secureResourceUrlsSalt();

	@Meta.AD(deflt = "false", name = "soap-debug", required = false)
	public boolean soapDebug();

}