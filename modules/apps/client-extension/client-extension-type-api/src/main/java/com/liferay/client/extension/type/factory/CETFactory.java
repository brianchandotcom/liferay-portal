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

package com.liferay.client.extension.type.factory;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Collection;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface CETFactory {

	public CET create(
			CETConfiguration cetConfiguration, long companyId,
			String externalReferenceCode)
		throws PortalException;

	public CET create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException;

	public CET create(PortletRequest portletRequest, String type)
		throws PortalException;

	public Collection<String> getTypes();

	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties, String type)
		throws PortalException;

	public final Map<String, String> FEATURE_FLAG_KEYS = HashMapBuilder.put(

		// feature.flag.LPS-164563

		ClientExtensionEntryConstants.TYPE_FDS_CELL_RENDERER, "LPS-164563"
	).put(

		// feature.flag.LPS-166479

		ClientExtensionEntryConstants.TYPE_THEME_SPRITEMAP, "LPS-166479"
	).put(

		// feature.flag.LPS-172903

		ClientExtensionEntryConstants.TYPE_JS_IMPORT_MAPS_ENTRY, "LPS-172903"
	).put(

		// feature.flag.LPS-177027

		ClientExtensionEntryConstants.TYPE_STATIC_CONTENT, "LPS-177027"
	).put(

		// feature.flag.LPS-184016

		ClientExtensionEntryConstants.TYPE_FDS_FILTER, "LPS-184016"
	).build();

}