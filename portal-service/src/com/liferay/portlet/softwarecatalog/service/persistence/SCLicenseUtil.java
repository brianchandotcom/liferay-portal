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

package com.liferay.portlet.softwarecatalog.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.softwarecatalog.model.SCLicense;

import java.util.List;

/**
 * The persistence utility for the s c license service. This utility wraps {@link com.liferay.portlet.softwarecatalog.service.persistence.impl.SCLicensePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCLicensePersistence
 * @see com.liferay.portlet.softwarecatalog.service.persistence.impl.SCLicensePersistenceImpl
 * @generated
 */
@ProviderType
public class SCLicenseUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(SCLicense scLicense) {
		getPersistence().clearCache(scLicense);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SCLicense> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SCLicense> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SCLicense> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SCLicense> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SCLicense update(SCLicense scLicense) {
		return getPersistence().update(scLicense);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SCLicense update(SCLicense scLicense,
		ServiceContext serviceContext) {
		return getPersistence().update(scLicense, serviceContext);
	}

	/**
	* Returns all the s c licenses where active = &#63;.
	*
	* @param active the active
	* @return the matching s c licenses
	*/
	public static List<SCLicense> findByActive(boolean active) {
		return getPersistence().findByActive(active);
	}

	/**
	* Returns a range of all the s c licenses where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of matching s c licenses
	*/
	public static List<SCLicense> findByActive(boolean active, int start,
		int end) {
		return getPersistence().findByActive(active, start, end);
	}

	/**
	* Returns an ordered range of all the s c licenses where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c licenses
	*/
	public static List<SCLicense> findByActive(boolean active, int start,
		int end, OrderByComparator<SCLicense> orderByComparator) {
		return getPersistence()
				   .findByActive(active, start, end, orderByComparator);
	}

	/**
	* Returns the first s c license in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c license
	* @throws NoSuchLicenseException if a matching s c license could not be found
	*/
	public static SCLicense findByActive_First(boolean active,
		OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByActive_First(active, orderByComparator);
	}

	/**
	* Returns the first s c license in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c license, or <code>null</code> if a matching s c license could not be found
	*/
	public static SCLicense fetchByActive_First(boolean active,
		OrderByComparator<SCLicense> orderByComparator) {
		return getPersistence().fetchByActive_First(active, orderByComparator);
	}

	/**
	* Returns the last s c license in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c license
	* @throws NoSuchLicenseException if a matching s c license could not be found
	*/
	public static SCLicense findByActive_Last(boolean active,
		OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByActive_Last(active, orderByComparator);
	}

	/**
	* Returns the last s c license in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c license, or <code>null</code> if a matching s c license could not be found
	*/
	public static SCLicense fetchByActive_Last(boolean active,
		OrderByComparator<SCLicense> orderByComparator) {
		return getPersistence().fetchByActive_Last(active, orderByComparator);
	}

	/**
	* Returns the s c licenses before and after the current s c license in the ordered set where active = &#63;.
	*
	* @param licenseId the primary key of the current s c license
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c license
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public static SCLicense[] findByActive_PrevAndNext(long licenseId,
		boolean active, OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence()
				   .findByActive_PrevAndNext(licenseId, active,
			orderByComparator);
	}

	/**
	* Returns all the s c licenses that the user has permission to view where active = &#63;.
	*
	* @param active the active
	* @return the matching s c licenses that the user has permission to view
	*/
	public static List<SCLicense> filterFindByActive(boolean active) {
		return getPersistence().filterFindByActive(active);
	}

	/**
	* Returns a range of all the s c licenses that the user has permission to view where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of matching s c licenses that the user has permission to view
	*/
	public static List<SCLicense> filterFindByActive(boolean active, int start,
		int end) {
		return getPersistence().filterFindByActive(active, start, end);
	}

	/**
	* Returns an ordered range of all the s c licenses that the user has permissions to view where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c licenses that the user has permission to view
	*/
	public static List<SCLicense> filterFindByActive(boolean active, int start,
		int end, OrderByComparator<SCLicense> orderByComparator) {
		return getPersistence()
				   .filterFindByActive(active, start, end, orderByComparator);
	}

	/**
	* Returns the s c licenses before and after the current s c license in the ordered set of s c licenses that the user has permission to view where active = &#63;.
	*
	* @param licenseId the primary key of the current s c license
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c license
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public static SCLicense[] filterFindByActive_PrevAndNext(long licenseId,
		boolean active, OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence()
				   .filterFindByActive_PrevAndNext(licenseId, active,
			orderByComparator);
	}

	/**
	* Removes all the s c licenses where active = &#63; from the database.
	*
	* @param active the active
	*/
	public static void removeByActive(boolean active) {
		getPersistence().removeByActive(active);
	}

	/**
	* Returns the number of s c licenses where active = &#63;.
	*
	* @param active the active
	* @return the number of matching s c licenses
	*/
	public static int countByActive(boolean active) {
		return getPersistence().countByActive(active);
	}

	/**
	* Returns the number of s c licenses that the user has permission to view where active = &#63;.
	*
	* @param active the active
	* @return the number of matching s c licenses that the user has permission to view
	*/
	public static int filterCountByActive(boolean active) {
		return getPersistence().filterCountByActive(active);
	}

	/**
	* Returns all the s c licenses where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @return the matching s c licenses
	*/
	public static List<SCLicense> findByA_R(boolean active, boolean recommended) {
		return getPersistence().findByA_R(active, recommended);
	}

	/**
	* Returns a range of all the s c licenses where active = &#63; and recommended = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param recommended the recommended
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of matching s c licenses
	*/
	public static List<SCLicense> findByA_R(boolean active,
		boolean recommended, int start, int end) {
		return getPersistence().findByA_R(active, recommended, start, end);
	}

	/**
	* Returns an ordered range of all the s c licenses where active = &#63; and recommended = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param recommended the recommended
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c licenses
	*/
	public static List<SCLicense> findByA_R(boolean active,
		boolean recommended, int start, int end,
		OrderByComparator<SCLicense> orderByComparator) {
		return getPersistence()
				   .findByA_R(active, recommended, start, end, orderByComparator);
	}

	/**
	* Returns the first s c license in the ordered set where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c license
	* @throws NoSuchLicenseException if a matching s c license could not be found
	*/
	public static SCLicense findByA_R_First(boolean active,
		boolean recommended, OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence()
				   .findByA_R_First(active, recommended, orderByComparator);
	}

	/**
	* Returns the first s c license in the ordered set where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c license, or <code>null</code> if a matching s c license could not be found
	*/
	public static SCLicense fetchByA_R_First(boolean active,
		boolean recommended, OrderByComparator<SCLicense> orderByComparator) {
		return getPersistence()
				   .fetchByA_R_First(active, recommended, orderByComparator);
	}

	/**
	* Returns the last s c license in the ordered set where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c license
	* @throws NoSuchLicenseException if a matching s c license could not be found
	*/
	public static SCLicense findByA_R_Last(boolean active, boolean recommended,
		OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence()
				   .findByA_R_Last(active, recommended, orderByComparator);
	}

	/**
	* Returns the last s c license in the ordered set where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c license, or <code>null</code> if a matching s c license could not be found
	*/
	public static SCLicense fetchByA_R_Last(boolean active,
		boolean recommended, OrderByComparator<SCLicense> orderByComparator) {
		return getPersistence()
				   .fetchByA_R_Last(active, recommended, orderByComparator);
	}

	/**
	* Returns the s c licenses before and after the current s c license in the ordered set where active = &#63; and recommended = &#63;.
	*
	* @param licenseId the primary key of the current s c license
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c license
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public static SCLicense[] findByA_R_PrevAndNext(long licenseId,
		boolean active, boolean recommended,
		OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence()
				   .findByA_R_PrevAndNext(licenseId, active, recommended,
			orderByComparator);
	}

	/**
	* Returns all the s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @return the matching s c licenses that the user has permission to view
	*/
	public static List<SCLicense> filterFindByA_R(boolean active,
		boolean recommended) {
		return getPersistence().filterFindByA_R(active, recommended);
	}

	/**
	* Returns a range of all the s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param recommended the recommended
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of matching s c licenses that the user has permission to view
	*/
	public static List<SCLicense> filterFindByA_R(boolean active,
		boolean recommended, int start, int end) {
		return getPersistence().filterFindByA_R(active, recommended, start, end);
	}

	/**
	* Returns an ordered range of all the s c licenses that the user has permissions to view where active = &#63; and recommended = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param recommended the recommended
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c licenses that the user has permission to view
	*/
	public static List<SCLicense> filterFindByA_R(boolean active,
		boolean recommended, int start, int end,
		OrderByComparator<SCLicense> orderByComparator) {
		return getPersistence()
				   .filterFindByA_R(active, recommended, start, end,
			orderByComparator);
	}

	/**
	* Returns the s c licenses before and after the current s c license in the ordered set of s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	*
	* @param licenseId the primary key of the current s c license
	* @param active the active
	* @param recommended the recommended
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c license
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public static SCLicense[] filterFindByA_R_PrevAndNext(long licenseId,
		boolean active, boolean recommended,
		OrderByComparator<SCLicense> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence()
				   .filterFindByA_R_PrevAndNext(licenseId, active, recommended,
			orderByComparator);
	}

	/**
	* Removes all the s c licenses where active = &#63; and recommended = &#63; from the database.
	*
	* @param active the active
	* @param recommended the recommended
	*/
	public static void removeByA_R(boolean active, boolean recommended) {
		getPersistence().removeByA_R(active, recommended);
	}

	/**
	* Returns the number of s c licenses where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @return the number of matching s c licenses
	*/
	public static int countByA_R(boolean active, boolean recommended) {
		return getPersistence().countByA_R(active, recommended);
	}

	/**
	* Returns the number of s c licenses that the user has permission to view where active = &#63; and recommended = &#63;.
	*
	* @param active the active
	* @param recommended the recommended
	* @return the number of matching s c licenses that the user has permission to view
	*/
	public static int filterCountByA_R(boolean active, boolean recommended) {
		return getPersistence().filterCountByA_R(active, recommended);
	}

	/**
	* Caches the s c license in the entity cache if it is enabled.
	*
	* @param scLicense the s c license
	*/
	public static void cacheResult(SCLicense scLicense) {
		getPersistence().cacheResult(scLicense);
	}

	/**
	* Caches the s c licenses in the entity cache if it is enabled.
	*
	* @param scLicenses the s c licenses
	*/
	public static void cacheResult(List<SCLicense> scLicenses) {
		getPersistence().cacheResult(scLicenses);
	}

	/**
	* Creates a new s c license with the primary key. Does not add the s c license to the database.
	*
	* @param licenseId the primary key for the new s c license
	* @return the new s c license
	*/
	public static SCLicense create(long licenseId) {
		return getPersistence().create(licenseId);
	}

	/**
	* Removes the s c license with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license that was removed
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public static SCLicense remove(long licenseId)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().remove(licenseId);
	}

	public static SCLicense updateImpl(SCLicense scLicense) {
		return getPersistence().updateImpl(scLicense);
	}

	/**
	* Returns the s c license with the primary key or throws a {@link NoSuchLicenseException} if it could not be found.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license
	* @throws NoSuchLicenseException if a s c license with the primary key could not be found
	*/
	public static SCLicense findByPrimaryKey(long licenseId)
		throws com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByPrimaryKey(licenseId);
	}

	/**
	* Returns the s c license with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license, or <code>null</code> if a s c license with the primary key could not be found
	*/
	public static SCLicense fetchByPrimaryKey(long licenseId) {
		return getPersistence().fetchByPrimaryKey(licenseId);
	}

	public static java.util.Map<java.io.Serializable, SCLicense> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the s c licenses.
	*
	* @return the s c licenses
	*/
	public static List<SCLicense> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the s c licenses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of s c licenses
	*/
	public static List<SCLicense> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the s c licenses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c licenses
	*/
	public static List<SCLicense> findAll(int start, int end,
		OrderByComparator<SCLicense> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the s c licenses from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of s c licenses.
	*
	* @return the number of s c licenses
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of s c product entries associated with the s c license.
	*
	* @param pk the primary key of the s c license
	* @return long[] of the primaryKeys of s c product entries associated with the s c license
	*/
	public static long[] getSCProductEntryPrimaryKeys(long pk) {
		return getPersistence().getSCProductEntryPrimaryKeys(pk);
	}

	/**
	* Returns all the s c product entries associated with the s c license.
	*
	* @param pk the primary key of the s c license
	* @return the s c product entries associated with the s c license
	*/
	public static List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk) {
		return getPersistence().getSCProductEntries(pk);
	}

	/**
	* Returns a range of all the s c product entries associated with the s c license.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c license
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of s c product entries associated with the s c license
	*/
	public static List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end) {
		return getPersistence().getSCProductEntries(pk, start, end);
	}

	/**
	* Returns an ordered range of all the s c product entries associated with the s c license.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c license
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c product entries associated with the s c license
	*/
	public static List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCProductEntry> orderByComparator) {
		return getPersistence()
				   .getSCProductEntries(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of s c product entries associated with the s c license.
	*
	* @param pk the primary key of the s c license
	* @return the number of s c product entries associated with the s c license
	*/
	public static int getSCProductEntriesSize(long pk) {
		return getPersistence().getSCProductEntriesSize(pk);
	}

	/**
	* Returns <code>true</code> if the s c product entry is associated with the s c license.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPK the primary key of the s c product entry
	* @return <code>true</code> if the s c product entry is associated with the s c license; <code>false</code> otherwise
	*/
	public static boolean containsSCProductEntry(long pk, long scProductEntryPK) {
		return getPersistence().containsSCProductEntry(pk, scProductEntryPK);
	}

	/**
	* Returns <code>true</code> if the s c license has any s c product entries associated with it.
	*
	* @param pk the primary key of the s c license to check for associations with s c product entries
	* @return <code>true</code> if the s c license has any s c product entries associated with it; <code>false</code> otherwise
	*/
	public static boolean containsSCProductEntries(long pk) {
		return getPersistence().containsSCProductEntries(pk);
	}

	/**
	* Adds an association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPK the primary key of the s c product entry
	*/
	public static void addSCProductEntry(long pk, long scProductEntryPK) {
		getPersistence().addSCProductEntry(pk, scProductEntryPK);
	}

	/**
	* Adds an association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntry the s c product entry
	*/
	public static void addSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry) {
		getPersistence().addSCProductEntry(pk, scProductEntry);
	}

	/**
	* Adds an association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPKs the primary keys of the s c product entries
	*/
	public static void addSCProductEntries(long pk, long[] scProductEntryPKs) {
		getPersistence().addSCProductEntries(pk, scProductEntryPKs);
	}

	/**
	* Adds an association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntries the s c product entries
	*/
	public static void addSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries) {
		getPersistence().addSCProductEntries(pk, scProductEntries);
	}

	/**
	* Clears all associations between the s c license and its s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license to clear the associated s c product entries from
	*/
	public static void clearSCProductEntries(long pk) {
		getPersistence().clearSCProductEntries(pk);
	}

	/**
	* Removes the association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPK the primary key of the s c product entry
	*/
	public static void removeSCProductEntry(long pk, long scProductEntryPK) {
		getPersistence().removeSCProductEntry(pk, scProductEntryPK);
	}

	/**
	* Removes the association between the s c license and the s c product entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntry the s c product entry
	*/
	public static void removeSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry) {
		getPersistence().removeSCProductEntry(pk, scProductEntry);
	}

	/**
	* Removes the association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPKs the primary keys of the s c product entries
	*/
	public static void removeSCProductEntries(long pk, long[] scProductEntryPKs) {
		getPersistence().removeSCProductEntries(pk, scProductEntryPKs);
	}

	/**
	* Removes the association between the s c license and the s c product entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntries the s c product entries
	*/
	public static void removeSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries) {
		getPersistence().removeSCProductEntries(pk, scProductEntries);
	}

	/**
	* Sets the s c product entries associated with the s c license, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntryPKs the primary keys of the s c product entries to be associated with the s c license
	*/
	public static void setSCProductEntries(long pk, long[] scProductEntryPKs) {
		getPersistence().setSCProductEntries(pk, scProductEntryPKs);
	}

	/**
	* Sets the s c product entries associated with the s c license, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c license
	* @param scProductEntries the s c product entries to be associated with the s c license
	*/
	public static void setSCProductEntries(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries) {
		getPersistence().setSCProductEntries(pk, scProductEntries);
	}

	public static SCLicensePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SCLicensePersistence)PortalBeanLocatorUtil.locate(SCLicensePersistence.class.getName());

			ReferenceRegistry.registerReference(SCLicenseUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(SCLicensePersistence persistence) {
	}

	private static SCLicensePersistence _persistence;
}