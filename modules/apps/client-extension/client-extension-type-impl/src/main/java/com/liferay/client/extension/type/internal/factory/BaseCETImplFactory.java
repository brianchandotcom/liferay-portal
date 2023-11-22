/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.Date;
import java.util.Properties;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera Avellón
 */
public abstract class BaseCETImplFactory<T extends CET>
	implements CETImplFactory<T> {

	@Override
	public final T create(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		long companyId = 0;
		Date createDate = null;
		String description = StringPool.BLANK;
		String externalReferenceCode = StringPool.BLANK;
		Date modifiedDate = null;
		String name = StringPool.BLANK;
		Properties properties = null;
		String sourceCodeURL = StringPool.BLANK;
		int status = WorkflowConstants.STATUS_APPROVED;
		UnicodeProperties unicodeProperties;

		if (clientExtensionEntry != null) {
			companyId = clientExtensionEntry.getCompanyId();
			createDate = clientExtensionEntry.getCreateDate();
			description = clientExtensionEntry.getDescription();
			externalReferenceCode =
				clientExtensionEntry.getExternalReferenceCode();
			modifiedDate = clientExtensionEntry.getModifiedDate();
			name = clientExtensionEntry.getName();

			try {
				properties = PropertiesUtil.load(
					clientExtensionEntry.getProperties());
			}
			catch (IOException ioException) {
				ReflectionUtil.throwException(ioException);
			}

			sourceCodeURL = clientExtensionEntry.getSourceCodeURL();
			status = clientExtensionEntry.getStatus();
			unicodeProperties = UnicodePropertiesBuilder.create(
				true
			).load(
				clientExtensionEntry.getTypeSettings()
			).build();
		}
		else {
			unicodeProperties = UnicodePropertiesBuilder.create(
				true
			).build();
		}

		return create(
			StringPool.BLANK, companyId, createDate, description,
			externalReferenceCode, modifiedDate, name, properties, false,
			sourceCodeURL, status, unicodeProperties);
	}

	@Override
	public final T create(PortletRequest portletRequest)
		throws PortalException {

		return create(
			StringPool.BLANK, 0, null, StringPool.BLANK, StringPool.BLANK, null,
			StringPool.BLANK, null, false, StringPool.BLANK,
			WorkflowConstants.STATUS_APPROVED,
			getUnicodeProperties(portletRequest));
	}

	@Override
	public final T create(
			String baseURL, long buildTimestamp, long companyId,
			String description, String externalReferenceCode, String name,
			Properties properties, String sourceCodeURL,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws PortalException {

		Date date = new Date(buildTimestamp);

		return create(
			baseURL, companyId, date, description, externalReferenceCode, date,
			name, properties, true, sourceCodeURL,
			WorkflowConstants.STATUS_APPROVED, typeSettingsUnicodeProperties);
	}

	@Override
	public final void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		T oldCET = null;

		if (oldTypeSettingsUnicodeProperties != null) {
			oldCET = create(
				StringPool.BLANK, 0, null, StringPool.BLANK, StringPool.BLANK,
				null, StringPool.BLANK, null, false, StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED,
				oldTypeSettingsUnicodeProperties);
		}

		validate(
			create(
				StringPool.BLANK, 0, null, StringPool.BLANK, StringPool.BLANK,
				null, StringPool.BLANK, null, false, StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED,
				newTypeSettingsUnicodeProperties),
			oldCET);
	}

	/**
	 * Construct a {@link CET} of the type designated by the factory
	 * implementation.
	 *
	 * The implementation of this method for a given type T should simply
	 * invoke T's constructor.
	 *
	 * @review
	 */
	protected abstract T create(
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
	protected abstract UnicodeProperties getUnicodeProperties(
		PortletRequest portletRequest);

	/**
	 * Validate if newCET has valid values and thus can be stored safely.
	 *
	 * @param newCET the CET object to validate
	 * @param oldCET the previous state of the CET object or null on creation
	 * @throws PortalException If any field has an invalid value
	 *
	 * @review
	 */
	protected abstract void validate(T newCET, T oldCET) throws PortalException;

}