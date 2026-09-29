/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.report.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Brian I. Kim
 */
@ExtendedObjectClassDefinition(
	category = "orders", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.report.internal.configuration.CommerceReportExporterConfiguration",
	localization = "content/Language",
	name = "commerce-report-exporter-configuration-name"
)
public interface CommerceReportExporterConfiguration {

	/**
	 * Sets the fully qualified names of the additional classes print order
	 * templates can use.
	 */
	@Meta.AD(
		deflt = "",
		description = "print-order-template-allowed-class-names-description",
		name = "allowed-class-names", required = false
	)
	public String[] allowedClassNames();

}