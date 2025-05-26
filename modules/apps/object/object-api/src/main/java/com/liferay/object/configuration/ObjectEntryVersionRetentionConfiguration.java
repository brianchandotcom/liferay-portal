/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Yuri Monteiro
 */
@ExtendedObjectClassDefinition(
	category = "object", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.object.configuration.ObjectEntryVersionRetentionConfiguration",
	localization = "content/Language",
	name = "object-entry-retention-configuration-name"
)
public interface ObjectEntryVersionRetentionConfiguration {

	@Meta.AD(
		deflt = "0", description = "maximum-entry-versions-number-description",
		name = "maximum-entry-versions-number", required = false
	)
	public int maximumEntryVersionsNumber();

	@Meta.AD(
		deflt = "1", description = "maximum-retention-period-description",
		min = "1", name = "maximum-retention-period", required = false
	)
	public int maximumRetentionPeriod();

}