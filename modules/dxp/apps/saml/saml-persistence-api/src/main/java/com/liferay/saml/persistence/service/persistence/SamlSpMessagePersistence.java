/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.saml.persistence.exception.NoSuchSpMessageException;
import com.liferay.saml.persistence.model.SamlSpMessage;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the saml sp message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpMessageUtil
 * @generated
 */
@ProviderType
public interface SamlSpMessagePersistence
	extends BasePersistence<SamlSpMessage> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SamlSpMessageUtil} to access the saml sp message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the saml sp messages where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching saml sp messages
	 */
	public java.util.List<SamlSpMessage> findByExpirationDate(
		Date expirationDate);

	/**
	 * Returns a range of all the saml sp messages where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @return the range of matching saml sp messages
	 */
	public java.util.List<SamlSpMessage> findByExpirationDate(
		Date expirationDate, int start, int end);

	/**
	 * Returns an ordered range of all the saml sp messages where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp messages
	 */
	public java.util.List<SamlSpMessage> findByExpirationDate(
		Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saml sp messages where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml sp messages
	 */
	public java.util.List<SamlSpMessage> findByExpirationDate(
		Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp message
	 * @throws NoSuchSpMessageException if a matching saml sp message could not be found
	 */
	public SamlSpMessage findByExpirationDate_First(
			Date expirationDate,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpMessage>
				orderByComparator)
		throws NoSuchSpMessageException;

	/**
	 * Returns the first saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	public SamlSpMessage fetchByExpirationDate_First(
		Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpMessage>
			orderByComparator);

	/**
	 * Returns the last saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp message
	 * @throws NoSuchSpMessageException if a matching saml sp message could not be found
	 */
	public SamlSpMessage findByExpirationDate_Last(
			Date expirationDate,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpMessage>
				orderByComparator)
		throws NoSuchSpMessageException;

	/**
	 * Returns the last saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	public SamlSpMessage fetchByExpirationDate_Last(
		Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpMessage>
			orderByComparator);

	/**
	 * Returns the saml sp messages before and after the current saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param samlSpMessageId the primary key of the current saml sp message
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp message
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	public SamlSpMessage[] findByExpirationDate_PrevAndNext(
			long samlSpMessageId, Date expirationDate,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpMessage>
				orderByComparator)
		throws NoSuchSpMessageException;

	/**
	 * Removes all the saml sp messages where expirationDate &lt; &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	public void removeByExpirationDate(Date expirationDate);

	/**
	 * Returns the number of saml sp messages where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching saml sp messages
	 */
	public int countByExpirationDate(Date expirationDate);

	/**
	 * Returns the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; or throws a <code>NoSuchSpMessageException</code> if it could not be found.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the matching saml sp message
	 * @throws NoSuchSpMessageException if a matching saml sp message could not be found
	 */
	public SamlSpMessage findBySIEI_SIRK(
			String samlIdpEntityId, String samlIdpResponseKey)
		throws NoSuchSpMessageException;

	/**
	 * Returns the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	public SamlSpMessage fetchBySIEI_SIRK(
		String samlIdpEntityId, String samlIdpResponseKey);

	/**
	 * Returns the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	public SamlSpMessage fetchBySIEI_SIRK(
		String samlIdpEntityId, String samlIdpResponseKey,
		boolean useFinderCache);

	/**
	 * Removes the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; from the database.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the saml sp message that was removed
	 */
	public SamlSpMessage removeBySIEI_SIRK(
			String samlIdpEntityId, String samlIdpResponseKey)
		throws NoSuchSpMessageException;

	/**
	 * Returns the number of saml sp messages where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63;.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the number of matching saml sp messages
	 */
	public int countBySIEI_SIRK(
		String samlIdpEntityId, String samlIdpResponseKey);

	/**
	 * Caches the saml sp message in the entity cache if it is enabled.
	 *
	 * @param samlSpMessage the saml sp message
	 */
	public void cacheResult(SamlSpMessage samlSpMessage);

	/**
	 * Caches the saml sp messages in the entity cache if it is enabled.
	 *
	 * @param samlSpMessages the saml sp messages
	 */
	public void cacheResult(java.util.List<SamlSpMessage> samlSpMessages);

	/**
	 * Creates a new saml sp message with the primary key. Does not add the saml sp message to the database.
	 *
	 * @param samlSpMessageId the primary key for the new saml sp message
	 * @return the new saml sp message
	 */
	public SamlSpMessage create(long samlSpMessageId);

	/**
	 * Removes the saml sp message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpMessageId the primary key of the saml sp message
	 * @return the saml sp message that was removed
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	public SamlSpMessage remove(long samlSpMessageId)
		throws NoSuchSpMessageException;

	public SamlSpMessage updateImpl(SamlSpMessage samlSpMessage);

	/**
	 * Returns the saml sp message with the primary key or throws a <code>NoSuchSpMessageException</code> if it could not be found.
	 *
	 * @param samlSpMessageId the primary key of the saml sp message
	 * @return the saml sp message
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	public SamlSpMessage findByPrimaryKey(long samlSpMessageId)
		throws NoSuchSpMessageException;

	/**
	 * Returns the saml sp message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlSpMessageId the primary key of the saml sp message
	 * @return the saml sp message, or <code>null</code> if a saml sp message with the primary key could not be found
	 */
	public SamlSpMessage fetchByPrimaryKey(long samlSpMessageId);

	/**
	 * Returns all the saml sp messages.
	 *
	 * @return the saml sp messages
	 */
	public java.util.List<SamlSpMessage> findAll();

	/**
	 * Returns a range of all the saml sp messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @return the range of saml sp messages
	 */
	public java.util.List<SamlSpMessage> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the saml sp messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml sp messages
	 */
	public java.util.List<SamlSpMessage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saml sp messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saml sp messages
	 */
	public java.util.List<SamlSpMessage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the saml sp messages from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of saml sp messages.
	 *
	 * @return the number of saml sp messages
	 */
	public int countAll();

}