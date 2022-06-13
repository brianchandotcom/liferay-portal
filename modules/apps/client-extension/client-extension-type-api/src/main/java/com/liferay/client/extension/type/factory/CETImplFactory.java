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

	public T cet(ClientExtensionEntry clientExtensionEntry)
		throws PortalException;

	public T cet(PortletRequest portletRequest) throws PortalException;

	public T cet(
			String baseURL, long companyId, String description,
			String externalReferenceCode, String name, Properties properties,
			String sourceCodeURL,
			UnicodeProperties toTypeSettingsUnicodeProperties)
		throws PortalException;

	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException;

}