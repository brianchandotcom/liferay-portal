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
import com.liferay.saml.persistence.model.SamlSpAuthRequest;

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
 * The persistence utility for the saml sp auth request service. This utility wraps <code>com.liferay.saml.persistence.service.persistence.impl.SamlSpAuthRequestPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpAuthRequestPersistence
 * @generated
 */
@ProviderType
public class SamlSpAuthRequestUtil {

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
	public static void clearCache(SamlSpAuthRequest samlSpAuthRequest) {
		getPersistence().clearCache(samlSpAuthRequest);
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
	public static Map<Serializable, SamlSpAuthRequest> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SamlSpAuthRequest> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SamlSpAuthRequest> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SamlSpAuthRequest> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SamlSpAuthRequest update(
		SamlSpAuthRequest samlSpAuthRequest) {

		return getPersistence().update(samlSpAuthRequest);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SamlSpAuthRequest update(
		SamlSpAuthRequest samlSpAuthRequest, ServiceContext serviceContext) {

		return getPersistence().update(samlSpAuthRequest, serviceContext);
	}

	/**
	 * Returns all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the matching saml sp auth requests
	 */
	public static List<SamlSpAuthRequest> findByCreateDate(Date createDate) {
		return getPersistence().findByCreateDate(createDate);
	}

	/**
	 * Returns a range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @return the range of matching saml sp auth requests
	 */
	public static List<SamlSpAuthRequest> findByCreateDate(
		Date createDate, int start, int end) {

		return getPersistence().findByCreateDate(createDate, start, end);
	}

	/**
	 * Returns an ordered range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp auth requests
	 */
	public static List<SamlSpAuthRequest> findByCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		return getPersistence().findByCreateDate(
			createDate, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml sp auth requests
	 */
	public static List<SamlSpAuthRequest> findByCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCreateDate(
			createDate, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a matching saml sp auth request could not be found
	 */
	public static SamlSpAuthRequest findByCreateDate_First(
			Date createDate,
			OrderByComparator<SamlSpAuthRequest> orderByComparator)
		throws com.liferay.saml.persistence.exception.
			NoSuchSpAuthRequestException {

		return getPersistence().findByCreateDate_First(
			createDate, orderByComparator);
	}

	/**
	 * Returns the first saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	public static SamlSpAuthRequest fetchByCreateDate_First(
		Date createDate,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		return getPersistence().fetchByCreateDate_First(
			createDate, orderByComparator);
	}

	/**
	 * Returns the last saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a matching saml sp auth request could not be found
	 */
	public static SamlSpAuthRequest findByCreateDate_Last(
			Date createDate,
			OrderByComparator<SamlSpAuthRequest> orderByComparator)
		throws com.liferay.saml.persistence.exception.
			NoSuchSpAuthRequestException {

		return getPersistence().findByCreateDate_Last(
			createDate, orderByComparator);
	}

	/**
	 * Returns the last saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	public static SamlSpAuthRequest fetchByCreateDate_Last(
		Date createDate,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		return getPersistence().fetchByCreateDate_Last(
			createDate, orderByComparator);
	}

	/**
	 * Returns the saml sp auth requests before and after the current saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param samlSpAuthnRequestId the primary key of the current saml sp auth request
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	public static SamlSpAuthRequest[] findByCreateDate_PrevAndNext(
			long samlSpAuthnRequestId, Date createDate,
			OrderByComparator<SamlSpAuthRequest> orderByComparator)
		throws com.liferay.saml.persistence.exception.
			NoSuchSpAuthRequestException {

		return getPersistence().findByCreateDate_PrevAndNext(
			samlSpAuthnRequestId, createDate, orderByComparator);
	}

	/**
	 * Removes all the saml sp auth requests where createDate &lt; &#63; from the database.
	 *
	 * @param createDate the create date
	 */
	public static void removeByCreateDate(Date createDate) {
		getPersistence().removeByCreateDate(createDate);
	}

	/**
	 * Returns the number of saml sp auth requests where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the number of matching saml sp auth requests
	 */
	public static int countByCreateDate(Date createDate) {
		return getPersistence().countByCreateDate(createDate);
	}

	/**
	 * Returns the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; or throws a <code>NoSuchSpAuthRequestException</code> if it could not be found.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the matching saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a matching saml sp auth request could not be found
	 */
	public static SamlSpAuthRequest findBySIEI_SSARK(
			String samlIdpEntityId, String samlSpAuthRequestKey)
		throws com.liferay.saml.persistence.exception.
			NoSuchSpAuthRequestException {

		return getPersistence().findBySIEI_SSARK(
			samlIdpEntityId, samlSpAuthRequestKey);
	}

	/**
	 * Returns the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	public static SamlSpAuthRequest fetchBySIEI_SSARK(
		String samlIdpEntityId, String samlSpAuthRequestKey) {

		return getPersistence().fetchBySIEI_SSARK(
			samlIdpEntityId, samlSpAuthRequestKey);
	}

	/**
	 * Returns the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	public static SamlSpAuthRequest fetchBySIEI_SSARK(
		String samlIdpEntityId, String samlSpAuthRequestKey,
		boolean useFinderCache) {

		return getPersistence().fetchBySIEI_SSARK(
			samlIdpEntityId, samlSpAuthRequestKey, useFinderCache);
	}

	/**
	 * Removes the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; from the database.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the saml sp auth request that was removed
	 */
	public static SamlSpAuthRequest removeBySIEI_SSARK(
			String samlIdpEntityId, String samlSpAuthRequestKey)
		throws com.liferay.saml.persistence.exception.
			NoSuchSpAuthRequestException {

		return getPersistence().removeBySIEI_SSARK(
			samlIdpEntityId, samlSpAuthRequestKey);
	}

	/**
	 * Returns the number of saml sp auth requests where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63;.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the number of matching saml sp auth requests
	 */
	public static int countBySIEI_SSARK(
		String samlIdpEntityId, String samlSpAuthRequestKey) {

		return getPersistence().countBySIEI_SSARK(
			samlIdpEntityId, samlSpAuthRequestKey);
	}

	/**
	 * Caches the saml sp auth request in the entity cache if it is enabled.
	 *
	 * @param samlSpAuthRequest the saml sp auth request
	 */
	public static void cacheResult(SamlSpAuthRequest samlSpAuthRequest) {
		getPersistence().cacheResult(samlSpAuthRequest);
	}

	/**
	 * Caches the saml sp auth requests in the entity cache if it is enabled.
	 *
	 * @param samlSpAuthRequests the saml sp auth requests
	 */
	public static void cacheResult(List<SamlSpAuthRequest> samlSpAuthRequests) {
		getPersistence().cacheResult(samlSpAuthRequests);
	}

	/**
	 * Creates a new saml sp auth request with the primary key. Does not add the saml sp auth request to the database.
	 *
	 * @param samlSpAuthnRequestId the primary key for the new saml sp auth request
	 * @return the new saml sp auth request
	 */
	public static SamlSpAuthRequest create(long samlSpAuthnRequestId) {
		return getPersistence().create(samlSpAuthnRequestId);
	}

	/**
	 * Removes the saml sp auth request with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request that was removed
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	public static SamlSpAuthRequest remove(long samlSpAuthnRequestId)
		throws com.liferay.saml.persistence.exception.
			NoSuchSpAuthRequestException {

		return getPersistence().remove(samlSpAuthnRequestId);
	}

	public static SamlSpAuthRequest updateImpl(
		SamlSpAuthRequest samlSpAuthRequest) {

		return getPersistence().updateImpl(samlSpAuthRequest);
	}

	/**
	 * Returns the saml sp auth request with the primary key or throws a <code>NoSuchSpAuthRequestException</code> if it could not be found.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	public static SamlSpAuthRequest findByPrimaryKey(long samlSpAuthnRequestId)
		throws com.liferay.saml.persistence.exception.
			NoSuchSpAuthRequestException {

		return getPersistence().findByPrimaryKey(samlSpAuthnRequestId);
	}

	/**
	 * Returns the saml sp auth request with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request, or <code>null</code> if a saml sp auth request with the primary key could not be found
	 */
	public static SamlSpAuthRequest fetchByPrimaryKey(
		long samlSpAuthnRequestId) {

		return getPersistence().fetchByPrimaryKey(samlSpAuthnRequestId);
	}

	/**
	 * Returns all the saml sp auth requests.
	 *
	 * @return the saml sp auth requests
	 */
	public static List<SamlSpAuthRequest> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the saml sp auth requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @return the range of saml sp auth requests
	 */
	public static List<SamlSpAuthRequest> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the saml sp auth requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml sp auth requests
	 */
	public static List<SamlSpAuthRequest> findAll(
		int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the saml sp auth requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saml sp auth requests
	 */
	public static List<SamlSpAuthRequest> findAll(
		int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the saml sp auth requests from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of saml sp auth requests.
	 *
	 * @return the number of saml sp auth requests
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SamlSpAuthRequestPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SamlSpAuthRequestPersistence, SamlSpAuthRequestPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SamlSpAuthRequestPersistence.class);

		ServiceTracker
			<SamlSpAuthRequestPersistence, SamlSpAuthRequestPersistence>
				serviceTracker =
					new ServiceTracker
						<SamlSpAuthRequestPersistence,
						 SamlSpAuthRequestPersistence>(
							 bundle.getBundleContext(),
							 SamlSpAuthRequestPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}