/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.iframe.sanitizer.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(
	category = "security-tools",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.portal.security.iframe.sanitizer.configuration.IFrameConfiguration",
	localization = "content/Language", name = "iframe-configuration-name"
)
public interface IFrameConfiguration {

	@Meta.AD(deflt = "true", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(deflt = "false", name = "remove-iframe-tags", required = false)
	public boolean removeIFrameTags();

	@Meta.AD(deflt = "", name = "sandbox-attribute-values", required = false)
	public String[] sandboxAttributeValues();

	@Meta.AD(name = "blacklist", required = false)
	public String[] blacklist();

	@Meta.AD(
		deflt = "com.liferay.fragment.model.FragmentEntry|com.liferay.journal.model.JournalArticle",
		name = "whitelist", required = false
	)
	public String[] whitelist();

}