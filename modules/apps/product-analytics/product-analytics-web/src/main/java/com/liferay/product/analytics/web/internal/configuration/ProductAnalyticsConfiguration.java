/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.analytics.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Christopher Kian
 */
@ExtendedObjectClassDefinition(
	category = "privacy", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	description = "product-analytics-configuration-description",
	id = "com.liferay.product.analytics.web.internal.configuration.ProductAnalyticsConfiguration",
	localization = "content/Language",
	name = "product-analytics-configuration-name"
)
public interface ProductAnalyticsConfiguration {

	@Meta.AD(
		deflt = "12",
		description = "product-analytics-cookie-consent-renewal-period-help",
		max = "12", min = "1", name = "cookie-consent-renewal-period",
		required = false
	)
	public int consentRenewalPeriod();

	@Meta.AD(
		deflt = "false",
		description = "product-analytics-enable-user-preference-handling-help",
		name = "enabled", required = false
	)
	public boolean enabled();

	@ExtendedAttributeDefinition(
		visibilityControllerKey = "com.liferay.product.analytics.web.internal.configuration.admin.display.ProductAnalyticsConfigurationVisibilityController"
	)
	@Meta.AD(deflt = "0", name = "last-modified", required = false)
	public long lastModified();

}