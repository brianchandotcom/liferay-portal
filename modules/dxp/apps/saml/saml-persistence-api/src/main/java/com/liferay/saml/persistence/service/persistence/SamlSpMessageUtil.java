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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.saml.persistence.model.SamlSpMessage;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the saml sp message service. This utility wraps <code>com.liferay.saml.persistence.service.persistence.impl.SamlSpMessagePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpMessagePersistence
 * @generated
 */
@ProviderType
public class SamlSpMessageUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(SamlSpMessage samlSpMessage) {
		getPersistence().clearCache(samlSpMessage);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, SamlSpMessage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SamlSpMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SamlSpMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SamlSpMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SamlSpMessage> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SamlSpMessage update(SamlSpMessage samlSpMessage) {
		return getPersistence().update(samlSpMessage);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SamlSpMessage update(
		SamlSpMessage samlSpMessage, ServiceContext serviceContext) {

		return getPersistence().update(samlSpMessage, serviceContext);
	}

	/**
	 * Returns all the saml sp messages where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching saml sp messages
	 */
	public static List<SamlSpMessage> findByExpirationDate(
		Date expirationDate) {

		return getPersistence().findByExpirationDate(expirationDate);
	}

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
	public static List<SamlSpMessage> findByExpirationDate(
		Date expirationDate, int start, int end) {

		return getPersistence().findByExpirationDate(
			expirationDate, start, end);
	}

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
	public static List<SamlSpMessage> findByExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<SamlSpMessage> orderByComparator) {

		return getPersistence().findByExpirationDate(
			expirationDate, start, end, orderByComparator);
	}

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
	public static List<SamlSpMessage> findByExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<SamlSpMessage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByExpirationDate(
			expirationDate, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp message
	 * @throws NoSuchSpMessageException if a matching saml sp message could not be found
	 */
	public static SamlSpMessage findByExpirationDate_First(
			Date expirationDate,
			OrderByComparator<SamlSpMessage> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchSpMessageException {

		return getPersistence().findByExpirationDate_First(
			expirationDate, orderByComparator);
	}

	/**
	 * Returns the first saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	public static SamlSpMessage fetchByExpirationDate_First(
		Date expirationDate,
		OrderByComparator<SamlSpMessage> orderByComparator) {

		return getPersistence().fetchByExpirationDate_First(
			expirationDate, orderByComparator);
	}

	/**
	 * Returns the last saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp message
	 * @throws NoSuchSpMessageException if a matching saml sp message could not be found
	 */
	public static SamlSpMessage findByExpirationDate_Last(
			Date expirationDate,
			OrderByComparator<SamlSpMessage> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchSpMessageException {

		return getPersistence().findByExpirationDate_Last(
			expirationDate, orderByComparator);
	}

	/**
	 * Returns the last saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	public static SamlSpMessage fetchByExpirationDate_Last(
		Date expirationDate,
		OrderByComparator<SamlSpMessage> orderByComparator) {

		return getPersistence().fetchByExpirationDate_Last(
			expirationDate, orderByComparator);
	}

	/**
	 * Returns the saml sp messages before and after the current saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param samlSpMessageId the primary key of the current saml sp message
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp message
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	public static SamlSpMessage[] findByExpirationDate_PrevAndNext(
			long samlSpMessageId, Date expirationDate,
			OrderByComparator<SamlSpMessage> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchSpMessageException {

		return getPersistence().findByExpirationDate_PrevAndNext(
			samlSpMessageId, expirationDate, orderByComparator);
	}

	/**
	 * Removes all the saml sp messages where expirationDate &lt; &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	public static void removeByExpirationDate(Date expirationDate) {
		getPersistence().removeByExpirationDate(expirationDate);
	}

	/**
	 * Returns the number of saml sp messages where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching saml sp messages
	 */
	public static int countByExpirationDate(Date expirationDate) {
		return getPersistence().countByExpirationDate(expirationDate);
	}

	/**
	 * Returns the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; or throws a <code>NoSuchSpMessageException</code> if it could not be found.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the matching saml sp message
	 * @throws NoSuchSpMessageException if a matching saml sp message could not be found
	 */
	public static SamlSpMessage findBySIEI_SIRK(
			String samlIdpEntityId, String samlIdpResponseKey)
		throws com.liferay.saml.persistence.exception.NoSuchSpMessageException {

		return getPersistence().findBySIEI_SIRK(
			samlIdpEntityId, samlIdpResponseKey);
	}

	/**
	 * Returns the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	public static SamlSpMessage fetchBySIEI_SIRK(
		String samlIdpEntityId, String samlIdpResponseKey) {

		return getPersistence().fetchBySIEI_SIRK(
			samlIdpEntityId, samlIdpResponseKey);
	}

	/**
	 * Returns the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	public static SamlSpMessage fetchBySIEI_SIRK(
		String samlIdpEntityId, String samlIdpResponseKey,
		boolean useFinderCache) {

		return getPersistence().fetchBySIEI_SIRK(
			samlIdpEntityId, samlIdpResponseKey, useFinderCache);
	}

	/**
	 * Removes the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; from the database.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the saml sp message that was removed
	 */
	public static SamlSpMessage removeBySIEI_SIRK(
			String samlIdpEntityId, String samlIdpResponseKey)
		throws com.liferay.saml.persistence.exception.NoSuchSpMessageException {

		return getPersistence().removeBySIEI_SIRK(
			samlIdpEntityId, samlIdpResponseKey);
	}

	/**
	 * Returns the number of saml sp messages where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63;.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the number of matching saml sp messages
	 */
	public static int countBySIEI_SIRK(
		String samlIdpEntityId, String samlIdpResponseKey) {

		return getPersistence().countBySIEI_SIRK(
			samlIdpEntityId, samlIdpResponseKey);
	}

	/**
	 * Caches the saml sp message in the entity cache if it is enabled.
	 *
	 * @param samlSpMessage the saml sp message
	 */
	public static void cacheResult(SamlSpMessage samlSpMessage) {
		getPersistence().cacheResult(samlSpMessage);
	}

	/**
	 * Caches the saml sp messages in the entity cache if it is enabled.
	 *
	 * @param samlSpMessages the saml sp messages
	 */
	public static void cacheResult(List<SamlSpMessage> samlSpMessages) {
		getPersistence().cacheResult(samlSpMessages);
	}

	/**
	 * Creates a new saml sp message with the primary key. Does not add the saml sp message to the database.
	 *
	 * @param samlSpMessageId the primary key for the new saml sp message
	 * @return the new saml sp message
	 */
	public static SamlSpMessage create(long samlSpMessageId) {
		return getPersistence().create(samlSpMessageId);
	}

	/**
	 * Removes the saml sp message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpMessageId the primary key of the saml sp message
	 * @return the saml sp message that was removed
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	public static SamlSpMessage remove(long samlSpMessageId)
		throws com.liferay.saml.persistence.exception.NoSuchSpMessageException {

		return getPersistence().remove(samlSpMessageId);
	}

	public static SamlSpMessage updateImpl(SamlSpMessage samlSpMessage) {
		return getPersistence().updateImpl(samlSpMessage);
	}

	/**
	 * Returns the saml sp message with the primary key or throws a <code>NoSuchSpMessageException</code> if it could not be found.
	 *
	 * @param samlSpMessageId the primary key of the saml sp message
	 * @return the saml sp message
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	public static SamlSpMessage findByPrimaryKey(long samlSpMessageId)
		throws com.liferay.saml.persistence.exception.NoSuchSpMessageException {

		return getPersistence().findByPrimaryKey(samlSpMessageId);
	}

	/**
	 * Returns the saml sp message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlSpMessageId the primary key of the saml sp message
	 * @return the saml sp message, or <code>null</code> if a saml sp message with the primary key could not be found
	 */
	public static SamlSpMessage fetchByPrimaryKey(long samlSpMessageId) {
		return getPersistence().fetchByPrimaryKey(samlSpMessageId);
	}

	/**
	 * Returns all the saml sp messages.
	 *
	 * @return the saml sp messages
	 */
	public static List<SamlSpMessage> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<SamlSpMessage> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<SamlSpMessage> findAll(
		int start, int end,
		OrderByComparator<SamlSpMessage> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<SamlSpMessage> findAll(
		int start, int end, OrderByComparator<SamlSpMessage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the saml sp messages from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of saml sp messages.
	 *
	 * @return the number of saml sp messages
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SamlSpMessagePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SamlSpMessagePersistence, SamlSpMessagePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SamlSpMessagePersistence.class);

		ServiceTracker<SamlSpMessagePersistence, SamlSpMessagePersistence>
			serviceTracker =
				new ServiceTracker
					<SamlSpMessagePersistence, SamlSpMessagePersistence>(
						bundle.getBundleContext(),
						SamlSpMessagePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}