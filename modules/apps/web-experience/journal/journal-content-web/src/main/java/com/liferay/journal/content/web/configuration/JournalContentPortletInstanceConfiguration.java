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

package com.liferay.journal.content.web.configuration;

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
	id = "com.liferay.journal.content.web.configuration.JournalContentPortletInstanceConfiguration",
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

}