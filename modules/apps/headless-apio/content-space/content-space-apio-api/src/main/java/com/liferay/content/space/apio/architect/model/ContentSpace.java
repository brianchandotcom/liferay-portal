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

package com.liferay.content.space.apio.architect.model;

import com.liferay.apio.architect.annotation.Id;
import com.liferay.apio.architect.annotation.Vocabulary;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.folder.apio.architect.identifier.RootFolderIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;

import java.util.List;
import java.util.Locale;

/**
 * Holds information about a content space. The internal method {@code
 * com.liferay.portal.kernel.model.GroupModel#getGroupId()} returns the
 * identifier.
 *
 * @author Javier Gamarra
 * @author Cristina González
 */
@Vocabulary.Type("ContentSpace")
public interface ContentSpace extends Identifier<Long> {

	@Vocabulary.Field("availableLanguages")
	public List<String> getAvailableLanguages();

	@Vocabulary.Field("creator")
	@Vocabulary.LinkTo(resource = PersonIdentifier.class)
	public Long getCreatorId();

	@Vocabulary.Field("description")
	public String getDescription(Locale locale);

	@Vocabulary.Field("documentsRepository")
	@Vocabulary.LinkTo(resource = RootFolderIdentifier.class)
	public Long getDocumentsRepositoryId();

	@Id
	public long getId();

	@Vocabulary.Field("name")
	public String getName(Locale locale);

}