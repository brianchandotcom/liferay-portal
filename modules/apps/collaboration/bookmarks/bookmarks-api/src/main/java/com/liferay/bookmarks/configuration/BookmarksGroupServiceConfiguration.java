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

package com.liferay.bookmarks.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "collaboration",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.bookmarks.configuration.BookmarksGroupServiceConfiguration",
	localization = "content/Language",
	name = "bookmarks-group-service-configuration-name"
)
public interface BookmarksGroupServiceConfiguration {

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_added_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailEntryAddedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailEntryAddedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_added_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailEntryAddedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_updated_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailEntryUpdatedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailEntryUpdatedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_updated_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailEntryUpdatedSubject();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		required = false
	)
	public String emailFromName();

	@Meta.AD(deflt = "true", required = false)
	public boolean enableRelatedAssets();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/search.container.page.default.delta}",
		required = false
	)
	public String entriesPerPage();

	@Meta.AD(deflt = "name|url|visits|modified-date|action", required = false)
	public String[] entryColumns();

	@Meta.AD(
		deflt = "folder|num-of-folders|num-of-entries|action", required = false
	)
	public String[] folderColumns();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/search.container.page.default.delta}",
		required = false
	)
	public String foldersPerPage();

	@Meta.AD(deflt = "true", required = false)
	public boolean showFoldersSearch();

	@Meta.AD(deflt = "true", required = false)
	public boolean showSubfolders();

}