/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.factory;

import com.liferay.client.extension.type.CET;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;
import java.util.Properties;

import javax.portlet.PortletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Iván Zaera Avellón
 */
@ProviderType
public interface CETImplFactory<T extends CET> {

	/**
	 * Construct a {@link CET} of the type designated by the factory
	 * implementation.
	 *
	 * The implementation of this method for a given type T should simply
	 * invoke T's constructor.
	 *
	 * @review
	 */
	public T create(
		String baseURL, long companyId, Date createDate, String description,
		String externalReferenceCode, Date modifiedDate, String name,
		Properties properties, boolean readOnly, String sourceCodeURL,
		int status, UnicodeProperties typeSettingsUnicodeProperties);

	/**
	 * Construct the typeSettingsUnicodeProperties field of the type designated
	 * by the factory implementation.
	 *
	 * The implementation of this method should extract the relevant values from
	 * the {@link PortletRequest}'s parameters and return them as a
	 * {@link UnicodeProperties} object.
	 *
	 * @review
	 */
	public UnicodeProperties getUnicodeProperties(
		PortletRequest portletRequest);

	public boolean isURLCETPropertyName(String name);

	/**
	 * Validate if newCET has valid values and thus can be stored safely.
	 *
	 * @param newCET the CET object to validate
	 * @param oldCET the previous state of the CET object or null on creation
	 * @throws PortalException If any field has an invalid value
	 *
	 * @review
	 */
	public void validate(T newCET, T oldCET) throws PortalException;

}