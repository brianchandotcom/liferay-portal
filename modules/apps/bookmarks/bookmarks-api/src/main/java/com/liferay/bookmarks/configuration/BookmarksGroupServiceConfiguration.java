/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "community-tools",
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
		name = "email-entry-added-body", required = false
	)
	public LocalizedValuesMap emailEntryAddedBody();

	@Meta.AD(
		deflt = "true", name = "email-entry-added-enabled", required = false
	)
	public boolean emailEntryAddedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_added_subject.tmpl}",
		name = "email-entry-added-subject", required = false
	)
	public LocalizedValuesMap emailEntryAddedSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_updated_body.tmpl}",
		name = "email-entry-updated-body", required = false
	)
	public LocalizedValuesMap emailEntryUpdatedBody();

	@Meta.AD(
		deflt = "true", name = "email-entry-updated-enabled", required = false
	)
	public boolean emailEntryUpdatedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/bookmarks/configuration/dependencies/email_entry_updated_subject.tmpl}",
		name = "email-entry-updated-subject", required = false
	)
	public LocalizedValuesMap emailEntryUpdatedSubject();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		name = "email-from-address", required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		name = "email-from-name", required = false
	)
	public String emailFromName();

	@Meta.AD(deflt = "true", name = "enable-related-assets", required = false)
	public boolean enableRelatedAssets();

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/search.container.page.default.delta}",
		name = "entries-per-page", required = false
	)
	public String entriesPerPage();

	@Meta.AD(
		deflt = "name|url|visits|modified-date|action", name = "entry-columns",
		required = false
	)
	public String[] entryColumns();

	@Meta.AD(
		deflt = "folder|num-of-folders|num-of-entries|action",
		name = "folder-columns", required = false
	)
	public String[] folderColumns();

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/search.container.page.default.delta}",
		name = "folders-per-page", required = false
	)
	public String foldersPerPage();

	@Meta.AD(deflt = "true", name = "show-folders-search", required = false)
	public boolean showFoldersSearch();

	@Meta.AD(deflt = "true", name = "show-subfolders", required = false)
	public boolean showSubfolders();

}