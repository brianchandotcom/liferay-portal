/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.saml.persistence.exception.NoSuchIbSloMessageException;
import com.liferay.saml.persistence.model.SamlIbSloMessage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the saml ib slo message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlIbSloMessageUtil
 * @generated
 */
@ProviderType
public interface SamlIbSloMessagePersistence
	extends BasePersistence<SamlIbSloMessage> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SamlIbSloMessageUtil} to access the saml ib slo message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the saml ib slo message where samlIdpSessionIndex = &#63; or throws a <code>NoSuchIbSloMessageException</code> if it could not be found.
	 *
	 * @param samlIdpSessionIndex the saml idp session index
	 * @return the matching saml ib slo message
	 * @throws NoSuchIbSloMessageException if a matching saml ib slo message could not be found
	 */
	public SamlIbSloMessage findBySamlIdpSessionIndex(
			String samlIdpSessionIndex)
		throws NoSuchIbSloMessageException;

	/**
	 * Returns the saml ib slo message where samlIdpSessionIndex = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlIdpSessionIndex the saml idp session index
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml ib slo message, or <code>null</code> if a matching saml ib slo message could not be found
	 */
	public SamlIbSloMessage fetchBySamlIdpSessionIndex(
		String samlIdpSessionIndex, boolean useFinderCache);

	/**
	 * Removes the saml ib slo message where samlIdpSessionIndex = &#63; from the database.
	 *
	 * @param samlIdpSessionIndex the saml idp session index
	 * @return the saml ib slo message that was removed
	 */
	public SamlIbSloMessage removeBySamlIdpSessionIndex(
			String samlIdpSessionIndex)
		throws NoSuchIbSloMessageException;

	/**
	 * Returns the number of saml ib slo messages where samlIdpSessionIndex = &#63;.
	 *
	 * @param samlIdpSessionIndex the saml idp session index
	 * @return the number of matching saml ib slo messages
	 */
	public int countBySamlIdpSessionIndex(String samlIdpSessionIndex);

	/**
	 * Creates a new saml ib slo message with the primary key. Does not add the saml ib slo message to the database.
	 *
	 * @param samlIbSloMessageId the primary key for the new saml ib slo message
	 * @return the new saml ib slo message
	 */
	public SamlIbSloMessage create(long samlIbSloMessageId);

	/**
	 * Removes the saml ib slo message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIbSloMessageId the primary key of the saml ib slo message
	 * @return the saml ib slo message that was removed
	 * @throws NoSuchIbSloMessageException if a saml ib slo message with the primary key could not be found
	 */
	public SamlIbSloMessage remove(long samlIbSloMessageId)
		throws NoSuchIbSloMessageException;

	public SamlIbSloMessage updateImpl(SamlIbSloMessage samlIbSloMessage);

	/**
	 * Returns the saml ib slo message with the primary key or throws a <code>NoSuchIbSloMessageException</code> if it could not be found.
	 *
	 * @param samlIbSloMessageId the primary key of the saml ib slo message
	 * @return the saml ib slo message
	 * @throws NoSuchIbSloMessageException if a saml ib slo message with the primary key could not be found
	 */
	public SamlIbSloMessage findByPrimaryKey(long samlIbSloMessageId)
		throws NoSuchIbSloMessageException;

	/**
	 * Returns the saml ib slo message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlIbSloMessageId the primary key of the saml ib slo message
	 * @return the saml ib slo message, or <code>null</code> if a saml ib slo message with the primary key could not be found
	 */
	public SamlIbSloMessage fetchByPrimaryKey(long samlIbSloMessageId);

	/**
	 * Returns the saml ib slo message where samlIdpSessionIndex = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlIdpSessionIndex the saml idp session index
	 * @return the matching saml ib slo message, or <code>null</code> if a matching saml ib slo message could not be found
	 */
	public default SamlIbSloMessage fetchBySamlIdpSessionIndex(
		String samlIdpSessionIndex) {

		return fetchBySamlIdpSessionIndex(samlIdpSessionIndex, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1833982896