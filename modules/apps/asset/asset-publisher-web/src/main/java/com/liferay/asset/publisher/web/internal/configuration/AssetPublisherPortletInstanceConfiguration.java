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

package com.liferay.asset.publisher.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(
	category = "assets",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.asset.publisher.web.internal.configuration.AssetPublisherPortletInstanceConfiguration",
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
		description = "default-display-style-key-description",
		name = "default-display-style", required = false
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
		description = "display-styles-key-description", name = "display-styles",
		required = false
	)
	public String[] displayStyles();

	/**
	 * Set this to <code>true</code> if you want to enable asset entry added
	 * email.
	 *
	 * @return <code>true</code> if asset entry added email is enabled.
	 */
	@Meta.AD(
		deflt = "true",
		description = "email-asset-entry-added-enabled-description",
		name = "email-asset-entry-added-enabled", required = false
	)
	public boolean emailAssetEntryAddedEnabled();

	/**
	 * Set a sender name to use in asset entry added email.
	 *
	 * @return default sender name to use in asset entry added email.
	 */
	@Meta.AD(
		deflt = "", description = "email-from-name-description",
		name = "email-from-name", required = false
	)
	public String emailFromName();

	/**
	 * Set an email address to use in asset entry added email.
	 *
	 * @return default email address to use in asset entry added email.
	 */
	@Meta.AD(
		deflt = "", description = "email-from-address-description",
		name = "email-from-address", required = false
	)
	public String emailFromAddress();

	/**
	 * Localized template for new asset entry added email message subject.
	 *
	 * @return message subject template for asset entry added email.
	 */
	@Meta.AD(
		deflt = "${resource:com/liferay/asset/publisher/web/portlet/email/dependencies/email_asset_entry_added_subject.tmpl}",
		description = "email-asset-entry-added-subject-description",
		name = "email-asset-entry-added-subject", required = false
	)
	public LocalizedValuesMap emailAssetEntryAddedSubject();

	/**
	 * Localized template for new asset entry added email message body.
	 *
	 * @return message body template for asset entry added email.
	 */
	@Meta.AD(
		deflt = "${resource:com/liferay/asset/publisher/web/portlet/email/dependencies/email_asset_entry_added_body.tmpl}",
		description = "email-asset-entry-added-body-description",
		name = "email-asset-entry-added-body", required = false
	)
	public LocalizedValuesMap emailAssetEntryAddedBody();

}