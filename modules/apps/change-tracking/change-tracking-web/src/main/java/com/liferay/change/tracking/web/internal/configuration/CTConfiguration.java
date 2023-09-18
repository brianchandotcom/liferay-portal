/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(
	category = "publications",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.change.tracking.web.internal.configuration.CTConfiguration",
	localization = "content/Language",
	name = "publications-portal-configuration-name"
)
public interface CTConfiguration {

	@Meta.AD(
		deflt = "1000", description = "context-view-limit-count-help",
		name = "context-view-limit-count", required = false
	)
	public int contextViewLimitCount();

	@Meta.AD(
		deflt = "true", description = "context-view-include-production-help",
		name = "context-view-include-production", required = false
	)
	public boolean contextViewIncludeProduction();

	@Meta.AD(
		description = "hidden-applications-help", name = "hidden-applications",
		required = false
	)
	public String[] hiddenApplications();

	@Meta.AD(
		deflt = "true", description = "show-all-items-enabled-help",
		name = "show-all-items-enabled", required = false
	)
	public boolean showAllItemsEnabled();

}