/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.content.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(
	category = "web-content",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.journal.content.web.internal.configuration.JournalContentPortletInstanceConfiguration",
	localization = "content/Language",
	name = "journal-content-portlet-instance-configuration-name"
)
public interface JournalContentPortletInstanceConfiguration {

	@Meta.AD(name = "article-id", required = false)
	public String articleId();

	@Meta.AD(deflt = "0", name = "group-id", required = false)
	public long groupId();

	@Meta.AD(name = "ddm-template-key", required = false)
	public String ddmTemplateKey();

	@Meta.AD(name = "user-tool-asset-addon-entry-keys", required = false)
	public String userToolAssetAddonEntryKeys();

	@Meta.AD(name = "content-metadata-asset-addon-entry-keys", required = false)
	public String contentMetadataAssetAddonEntryKeys();

	@Meta.AD(name = "enable-view-count-increment", required = false)
	public boolean enableViewCountIncrement();

	@Meta.AD(
		deflt = "false", description = "sort-structures-by-name-help",
		name = "sort-structures-by-name", required = false
	)
	public boolean sortStructuresByByName();

}