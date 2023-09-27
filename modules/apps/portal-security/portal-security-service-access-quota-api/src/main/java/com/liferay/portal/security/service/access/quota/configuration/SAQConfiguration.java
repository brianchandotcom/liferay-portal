/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.quota.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Stian Sigvartsen
 */
@ExtendedObjectClassDefinition(
	category = "api-authentication",
	factoryInstanceLabelAttribute = "serviceSignature"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.security.service.access.quota.configuration.SAQConfiguration",
	localization = "content/Language", name = "saq-configuration-name"
)
@ProviderType
public interface SAQConfiguration {

	@Meta.AD(
		deflt = "", name = "saq-configuration-service-signature",
		required = false
	)
	public String serviceSignature();

	@Meta.AD(deflt = "60", min = "1", name = "saq-configuration-service-max")
	public int max();

	@Meta.AD(
		deflt = "60000", name = "saq-configuration-service-interval-millis"
	)
	public long intervalMillis();

	@Meta.AD(
		deflt = "", name = "saq-configuration-service-metrics", required = false
	)
	public String[] metrics();

}