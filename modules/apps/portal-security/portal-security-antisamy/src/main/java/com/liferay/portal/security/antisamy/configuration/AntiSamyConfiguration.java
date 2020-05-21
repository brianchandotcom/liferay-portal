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

package com.liferay.portal.security.antisamy.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(category = "security-tools")
@Meta.OCD(
	id = "com.liferay.portal.security.antisamy.configuration.AntiSamyConfiguration",
	localization = "content/Language", name = "anti-samy-configuration-name"
)
public interface AntiSamyConfiguration {

	@Meta.AD(deflt = "true", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(
		deflt = "/META-INF/resources/sanitizer-configuration.xml",
		name = "configuration-file-url", required = false
	)
	public String configurationFileURL();

	@Meta.AD(name = "blacklist", required = false)
	public String[] blacklist();

	@Meta.AD(
		deflt = "com.liferay.blogs.model.BlogsEntry|com.liferay.fragment.model.FragmentEntry|com.liferay.journal.model.JournalArticle",
		name = "whitelist", required = false
	)
	public String[] whitelist();

}