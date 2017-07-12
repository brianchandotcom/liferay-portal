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

package com.liferay.asset.publisher.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(
	category = "web-experience",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.asset.publisher.web.configuration.AssetPublisherPortletInstanceConfiguration",
	localization = "content/Language",
	name = "asset-publisher-portlet-instance-configuration-name"
)
public interface AssetPublisherPortletInstanceConfiguration {

	/**
	 * Set the name of the display style which will be used by default.
	 *
	 * @return default display style.
	 */
	@Meta.AD(
		deflt = "abstracts",
		description = "default-display-style-key-description", required = false
	)
	public String defaultDisplayStyle();

	/**
	 * Input a list of comma delimited display styles that will be available in
	 * the configuration screen of the Asset Publisher portlet.
	 *
	 * @return available display styles.
	 */
	@Meta.AD(
		deflt = "table|title-list|abstracts|full-content",
		description = "display-styles-key-description", required = false
	)
	public String[] displayStyles();

	@Meta.AD(
		deflt = "${resource:com/liferay/asset/publisher/web/portlet/email/dependencies/email_asset_entry_added_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailAssetEntryAddedBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailAssetEntryAddedEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/asset/publisher/web/portlet/email/dependencies/email_asset_entry_added_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailAssetEntryAddedSubject();

	@Meta.AD(deflt = "", required = false)
	public String emailFromAddress();

	@Meta.AD(deflt = "", required = false)
	public String emailFromName();

}