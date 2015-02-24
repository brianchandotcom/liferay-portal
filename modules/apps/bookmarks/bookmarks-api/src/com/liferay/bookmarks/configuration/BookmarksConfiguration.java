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

/**
 * @author Adolfo Pérez
 */
@Meta.OCD(
	id = "com.liferay.bookmarks.configuration.BookmarksConfiguration"
)
public interface BookmarksConfiguration {

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_added_body.tmpl}",
		required = false
	)
	public String emailEntryAddedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailEntryAddedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_added_subject.tmpl}",
		required = false
	)
	public String emailEntryAddedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_updated_body.tmpl}",
		required = false
	)
	public String emailEntryUpdatedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailEntryUpdatedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_updated_subject.tmpl}",
		required = false
	)
	public String emailEntryUpdatedSubject();

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

	@Meta.AD(deflt = "20", required = false)
	public int entriesPerPage();

	@Meta.AD(deflt = "name|url|visits|modified-date|action", required = false)
	public String[] entryColumns();

	@Meta.AD(
		deflt = "folder|num-of-folders|num-of-entries|action", required = false
	)
	public String[] folderColumns();

	@Meta.AD(deflt = "20", required = false)
	public int foldersPerPage();

	@Meta.AD(deflt = "true", required = false)
	public boolean showFoldersSearch();

	@Meta.AD(deflt = "true", required = false)
	public boolean showSubfolders();

}