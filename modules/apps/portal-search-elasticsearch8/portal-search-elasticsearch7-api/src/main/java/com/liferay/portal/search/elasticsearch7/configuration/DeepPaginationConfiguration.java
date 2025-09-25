/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Gustavo Lima
 */
@ExtendedObjectClassDefinition(
	category = "search", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.portal.search.elasticsearch7.configuration.DeepPaginationConfiguration",
	localization = "content/Language",
	name = "deep-pagination-configuration-name"
)
@ProviderType
public interface DeepPaginationConfiguration {

	@Meta.AD(
		deflt = "false", description = "enable-deep-pagination-help",
		name = "enable-deep-pagination", required = false
	)
	public boolean enableDeepPagination();

	@Meta.AD(
		deflt = "60", description = "point-in-time-keep-alive-seconds-help",
		name = "point-in-time-keep-alive-seconds", required = false
	)
	public int pointInTimeKeepAliveSeconds();

}