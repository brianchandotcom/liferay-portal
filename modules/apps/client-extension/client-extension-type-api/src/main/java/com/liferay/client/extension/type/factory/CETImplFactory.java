/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.factory;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Properties;

import javax.portlet.PortletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Iván Zaera Avellón
 */
@ProviderType
public interface CETImplFactory<T extends CET> {

	/**
	 * Create a CET object of type T from a {@link ClientExtensionEntry} object.
	 *
	 * This method is used when a client extension is configured from the
	 * {@link com.liferay.client.extension.service.ClientExtensionEntryService}.
	 *
	 * @review
	 */
	public T create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException;

	/**
	 * Create a partial CET object of type T from a {@link PortletRequest}
	 * object.
	 *
	 * This method is used to create temporary CET objects of type T to be used
	 * when rendering the administration UI.
	 *
	 * @review
	 */
	public T create(PortletRequest portletRequest) throws PortalException;

	/**
	 * Create a CET object of type T from given values.
	 *
	 * This method is used when a client extension is deployed from a Liferay
	 * Workspace.
	 *
	 * @review
	 */
	public T create(
			String baseURL, long buildTimestamp, long companyId,
			String description, String externalReferenceCode, String name,
			Properties properties, String sourceCodeURL,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws PortalException;

	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException;

}