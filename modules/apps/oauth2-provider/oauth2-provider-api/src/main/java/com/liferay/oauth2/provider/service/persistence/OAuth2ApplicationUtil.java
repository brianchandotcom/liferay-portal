/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.service.persistence;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the o auth2 application service. This utility wraps <code>com.liferay.oauth2.provider.service.persistence.impl.OAuth2ApplicationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationPersistence
 * @generated
 */
public class OAuth2ApplicationUtil {

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
	public static void clearCache(OAuth2Application oAuth2Application) {
		getPersistence().clearCache(oAuth2Application);
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
	public static Map<Serializable, OAuth2Application> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<OAuth2Application> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OAuth2Application> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OAuth2Application> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OAuth2Application update(
		OAuth2Application oAuth2Application) {

		return getPersistence().update(oAuth2Application);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OAuth2Application update(
		OAuth2Application oAuth2Application, ServiceContext serviceContext) {

		return getPersistence().update(oAuth2Application, serviceContext);
	}

	/**
	 * Returns all the o auth2 applications where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching o auth2 applications
	 */
	public static List<OAuth2Application> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the o auth2 applications where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth2 application in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	public static OAuth2Application findByUuid_First(
			String uuid, OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first o auth2 application in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByUuid_First(
		String uuid, OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last o auth2 application in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	public static OAuth2Application findByUuid_Last(
			String uuid, OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last o auth2 application in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByUuid_Last(
		String uuid, OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the o auth2 applications before and after the current o auth2 application in the ordered set where uuid = &#63;.
	 *
	 * @param oAuth2ApplicationId the primary key of the current o auth2 application
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application[] findByUuid_PrevAndNext(
			long oAuth2ApplicationId, String uuid,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByUuid_PrevAndNext(
			oAuth2ApplicationId, uuid, orderByComparator);
	}

	/**
	 * Returns all the o auth2 applications that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByUuid(String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	 * Returns a range of all the o auth2 applications that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByUuid(
		String uuid, int start, int end) {

		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().filterFindByUuid(
			uuid, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth2 applications before and after the current o auth2 application in the ordered set of o auth2 applications that the user has permission to view where uuid = &#63;.
	 *
	 * @param oAuth2ApplicationId the primary key of the current o auth2 application
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application[] filterFindByUuid_PrevAndNext(
			long oAuth2ApplicationId, String uuid,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().filterFindByUuid_PrevAndNext(
			oAuth2ApplicationId, uuid, orderByComparator);
	}

	/**
	 * Removes all the o auth2 applications where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of o auth2 applications where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching o auth2 applications
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the number of o auth2 applications that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching o auth2 applications that the user has permission to view
	 */
	public static int filterCountByUuid(String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	 * Returns all the o auth2 applications where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching o auth2 applications
	 */
	public static List<OAuth2Application> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the o auth2 applications where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth2 application in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	public static OAuth2Application findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first o auth2 application in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last o auth2 application in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	public static OAuth2Application findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last o auth2 application in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the o auth2 applications before and after the current o auth2 application in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param oAuth2ApplicationId the primary key of the current o auth2 application
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application[] findByUuid_C_PrevAndNext(
			long oAuth2ApplicationId, String uuid, long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByUuid_C_PrevAndNext(
			oAuth2ApplicationId, uuid, companyId, orderByComparator);
	}

	/**
	 * Returns all the o auth2 applications that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByUuid_C(
		String uuid, long companyId) {

		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the o auth2 applications that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().filterFindByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth2 applications before and after the current o auth2 application in the ordered set of o auth2 applications that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param oAuth2ApplicationId the primary key of the current o auth2 application
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application[] filterFindByUuid_C_PrevAndNext(
			long oAuth2ApplicationId, String uuid, long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().filterFindByUuid_C_PrevAndNext(
			oAuth2ApplicationId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the o auth2 applications where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of o auth2 applications where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching o auth2 applications
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of o auth2 applications that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching o auth2 applications that the user has permission to view
	 */
	public static int filterCountByUuid_C(String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the o auth2 applications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth2 applications
	 */
	public static List<OAuth2Application> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the o auth2 applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth2 application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	public static OAuth2Application findByCompanyId_First(
			long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first o auth2 application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByCompanyId_First(
		long companyId,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last o auth2 application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	public static OAuth2Application findByCompanyId_Last(
			long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last o auth2 application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the o auth2 applications before and after the current o auth2 application in the ordered set where companyId = &#63;.
	 *
	 * @param oAuth2ApplicationId the primary key of the current o auth2 application
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application[] findByCompanyId_PrevAndNext(
			long oAuth2ApplicationId, long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByCompanyId_PrevAndNext(
			oAuth2ApplicationId, companyId, orderByComparator);
	}

	/**
	 * Returns all the o auth2 applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByCompanyId(
		long companyId) {

		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the o auth2 applications that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth2 applications before and after the current o auth2 application in the ordered set of o auth2 applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param oAuth2ApplicationId the primary key of the current o auth2 application
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application[] filterFindByCompanyId_PrevAndNext(
			long oAuth2ApplicationId, long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			oAuth2ApplicationId, companyId, orderByComparator);
	}

	/**
	 * Removes all the o auth2 applications where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of o auth2 applications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth2 applications
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of o auth2 applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth2 applications that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns the o auth2 application where companyId = &#63; and clientId = &#63; or throws a <code>NoSuchOAuth2ApplicationException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param clientId the client ID
	 * @return the matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	public static OAuth2Application findByC_C(long companyId, String clientId)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByC_C(companyId, clientId);
	}

	/**
	 * Returns the o auth2 application where companyId = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param clientId the client ID
	 * @return the matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByC_C(
		long companyId, String clientId) {

		return getPersistence().fetchByC_C(companyId, clientId);
	}

	/**
	 * Returns the o auth2 application where companyId = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param clientId the client ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByC_C(
		long companyId, String clientId, boolean useFinderCache) {

		return getPersistence().fetchByC_C(companyId, clientId, useFinderCache);
	}

	/**
	 * Removes the o auth2 application where companyId = &#63; and clientId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param clientId the client ID
	 * @return the o auth2 application that was removed
	 */
	public static OAuth2Application removeByC_C(long companyId, String clientId)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().removeByC_C(companyId, clientId);
	}

	/**
	 * Returns the number of o auth2 applications where companyId = &#63; and clientId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param clientId the client ID
	 * @return the number of matching o auth2 applications
	 */
	public static int countByC_C(long companyId, String clientId) {
		return getPersistence().countByC_C(companyId, clientId);
	}

	/**
	 * Returns all the o auth2 applications where companyId = &#63; and clientProfile = &#63;.
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @return the matching o auth2 applications
	 */
	public static List<OAuth2Application> findByC_CP(
		long companyId, int clientProfile) {

		return getPersistence().findByC_CP(companyId, clientProfile);
	}

	/**
	 * Returns a range of all the o auth2 applications where companyId = &#63; and clientProfile = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByC_CP(
		long companyId, int clientProfile, int start, int end) {

		return getPersistence().findByC_CP(
			companyId, clientProfile, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications where companyId = &#63; and clientProfile = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByC_CP(
		long companyId, int clientProfile, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().findByC_CP(
			companyId, clientProfile, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications where companyId = &#63; and clientProfile = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 applications
	 */
	public static List<OAuth2Application> findByC_CP(
		long companyId, int clientProfile, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_CP(
			companyId, clientProfile, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first o auth2 application in the ordered set where companyId = &#63; and clientProfile = &#63;.
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	public static OAuth2Application findByC_CP_First(
			long companyId, int clientProfile,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByC_CP_First(
			companyId, clientProfile, orderByComparator);
	}

	/**
	 * Returns the first o auth2 application in the ordered set where companyId = &#63; and clientProfile = &#63;.
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByC_CP_First(
		long companyId, int clientProfile,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().fetchByC_CP_First(
			companyId, clientProfile, orderByComparator);
	}

	/**
	 * Returns the last o auth2 application in the ordered set where companyId = &#63; and clientProfile = &#63;.
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	public static OAuth2Application findByC_CP_Last(
			long companyId, int clientProfile,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByC_CP_Last(
			companyId, clientProfile, orderByComparator);
	}

	/**
	 * Returns the last o auth2 application in the ordered set where companyId = &#63; and clientProfile = &#63;.
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByC_CP_Last(
		long companyId, int clientProfile,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().fetchByC_CP_Last(
			companyId, clientProfile, orderByComparator);
	}

	/**
	 * Returns the o auth2 applications before and after the current o auth2 application in the ordered set where companyId = &#63; and clientProfile = &#63;.
	 *
	 * @param oAuth2ApplicationId the primary key of the current o auth2 application
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application[] findByC_CP_PrevAndNext(
			long oAuth2ApplicationId, long companyId, int clientProfile,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByC_CP_PrevAndNext(
			oAuth2ApplicationId, companyId, clientProfile, orderByComparator);
	}

	/**
	 * Returns all the o auth2 applications that the user has permission to view where companyId = &#63; and clientProfile = &#63;.
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @return the matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByC_CP(
		long companyId, int clientProfile) {

		return getPersistence().filterFindByC_CP(companyId, clientProfile);
	}

	/**
	 * Returns a range of all the o auth2 applications that the user has permission to view where companyId = &#63; and clientProfile = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByC_CP(
		long companyId, int clientProfile, int start, int end) {

		return getPersistence().filterFindByC_CP(
			companyId, clientProfile, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications that the user has permissions to view where companyId = &#63; and clientProfile = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 applications that the user has permission to view
	 */
	public static List<OAuth2Application> filterFindByC_CP(
		long companyId, int clientProfile, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().filterFindByC_CP(
			companyId, clientProfile, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth2 applications before and after the current o auth2 application in the ordered set of o auth2 applications that the user has permission to view where companyId = &#63; and clientProfile = &#63;.
	 *
	 * @param oAuth2ApplicationId the primary key of the current o auth2 application
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application[] filterFindByC_CP_PrevAndNext(
			long oAuth2ApplicationId, long companyId, int clientProfile,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().filterFindByC_CP_PrevAndNext(
			oAuth2ApplicationId, companyId, clientProfile, orderByComparator);
	}

	/**
	 * Removes all the o auth2 applications where companyId = &#63; and clientProfile = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 */
	public static void removeByC_CP(long companyId, int clientProfile) {
		getPersistence().removeByC_CP(companyId, clientProfile);
	}

	/**
	 * Returns the number of o auth2 applications where companyId = &#63; and clientProfile = &#63;.
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @return the number of matching o auth2 applications
	 */
	public static int countByC_CP(long companyId, int clientProfile) {
		return getPersistence().countByC_CP(companyId, clientProfile);
	}

	/**
	 * Returns the number of o auth2 applications that the user has permission to view where companyId = &#63; and clientProfile = &#63;.
	 *
	 * @param companyId the company ID
	 * @param clientProfile the client profile
	 * @return the number of matching o auth2 applications that the user has permission to view
	 */
	public static int filterCountByC_CP(long companyId, int clientProfile) {
		return getPersistence().filterCountByC_CP(companyId, clientProfile);
	}

	/**
	 * Returns the o auth2 application where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchOAuth2ApplicationException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	public static OAuth2Application findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the o auth2 application where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the o auth2 application where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	public static OAuth2Application fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the o auth2 application where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the o auth2 application that was removed
	 */
	public static OAuth2Application removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of o auth2 applications where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching o auth2 applications
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the o auth2 application in the entity cache if it is enabled.
	 *
	 * @param oAuth2Application the o auth2 application
	 */
	public static void cacheResult(OAuth2Application oAuth2Application) {
		getPersistence().cacheResult(oAuth2Application);
	}

	/**
	 * Caches the o auth2 applications in the entity cache if it is enabled.
	 *
	 * @param oAuth2Applications the o auth2 applications
	 */
	public static void cacheResult(List<OAuth2Application> oAuth2Applications) {
		getPersistence().cacheResult(oAuth2Applications);
	}

	/**
	 * Creates a new o auth2 application with the primary key. Does not add the o auth2 application to the database.
	 *
	 * @param oAuth2ApplicationId the primary key for the new o auth2 application
	 * @return the new o auth2 application
	 */
	public static OAuth2Application create(long oAuth2ApplicationId) {
		return getPersistence().create(oAuth2ApplicationId);
	}

	/**
	 * Removes the o auth2 application with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ApplicationId the primary key of the o auth2 application
	 * @return the o auth2 application that was removed
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application remove(long oAuth2ApplicationId)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().remove(oAuth2ApplicationId);
	}

	public static OAuth2Application updateImpl(
		OAuth2Application oAuth2Application) {

		return getPersistence().updateImpl(oAuth2Application);
	}

	/**
	 * Returns the o auth2 application with the primary key or throws a <code>NoSuchOAuth2ApplicationException</code> if it could not be found.
	 *
	 * @param oAuth2ApplicationId the primary key of the o auth2 application
	 * @return the o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application findByPrimaryKey(long oAuth2ApplicationId)
		throws com.liferay.oauth2.provider.exception.
			NoSuchOAuth2ApplicationException {

		return getPersistence().findByPrimaryKey(oAuth2ApplicationId);
	}

	/**
	 * Returns the o auth2 application with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuth2ApplicationId the primary key of the o auth2 application
	 * @return the o auth2 application, or <code>null</code> if a o auth2 application with the primary key could not be found
	 */
	public static OAuth2Application fetchByPrimaryKey(
		long oAuth2ApplicationId) {

		return getPersistence().fetchByPrimaryKey(oAuth2ApplicationId);
	}

	/**
	 * Returns all the o auth2 applications.
	 *
	 * @return the o auth2 applications
	 */
	public static List<OAuth2Application> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the o auth2 applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of o auth2 applications
	 */
	public static List<OAuth2Application> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 applications
	 */
	public static List<OAuth2Application> findAll(
		int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth2 applications
	 */
	public static List<OAuth2Application> findAll(
		int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the o auth2 applications from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of o auth2 applications.
	 *
	 * @return the number of o auth2 applications
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static OAuth2ApplicationPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		OAuth2ApplicationPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile OAuth2ApplicationPersistence _persistence;

}