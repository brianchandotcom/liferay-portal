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

package com.liferay.content.space.headless.model;

import com.liferay.apio.architect.annotation.Id;
import com.liferay.apio.architect.annotation.Vocabulary;
import com.liferay.apio.architect.identifier.Identifier;

import java.util.List;
import java.util.Locale;

/**
 * Holds information about a content space. The internal method {@code
 * com.liferay.portal.kernel.model.GroupModel#getGroupId()} returns the
 * identifier.
 *
 * @author Javier Gamarra
 * @author Cristina González
 * @review
 */
@Vocabulary.Type("ContentSpace")
public interface ContentSpace extends Identifier<Long> {

	@Vocabulary.Field("availableLanguages")
	public List<String> getAvailableLanguages();

	@Vocabulary.Field("description")
	public String getDescription(Locale locale);

	@Id
	public long getId();

	@Vocabulary.Field("name")
	public String getName(Locale locale);

}