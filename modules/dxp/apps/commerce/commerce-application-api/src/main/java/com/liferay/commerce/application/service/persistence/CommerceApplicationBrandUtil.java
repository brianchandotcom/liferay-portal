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

package com.liferay.commerce.application.service.persistence;

import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the commerce application brand service. This utility wraps <code>com.liferay.commerce.application.service.persistence.impl.CommerceApplicationBrandPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationBrandPersistence
 * @generated
 */
public class CommerceApplicationBrandUtil {

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
	public static void clearCache(
		CommerceApplicationBrand commerceApplicationBrand) {

		getPersistence().clearCache(commerceApplicationBrand);
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
	public static Map<Serializable, CommerceApplicationBrand>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceApplicationBrand> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceApplicationBrand> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceApplicationBrand> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceApplicationBrand update(
		CommerceApplicationBrand commerceApplicationBrand) {

		return getPersistence().update(commerceApplicationBrand);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceApplicationBrand update(
		CommerceApplicationBrand commerceApplicationBrand,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceApplicationBrand, serviceContext);
	}

	/**
	 * Returns all the commerce application brands where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce application brands
	 */
	public static List<CommerceApplicationBrand> findByCompanyId(
		long companyId) {

		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce application brands where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @return the range of matching commerce application brands
	 */
	public static List<CommerceApplicationBrand> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce application brands where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application brands
	 */
	public static List<CommerceApplicationBrand> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce application brands where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application brands
	 */
	public static List<CommerceApplicationBrand> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application brand
	 * @throws NoSuchApplicationBrandException if a matching commerce application brand could not be found
	 */
	public static CommerceApplicationBrand findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceApplicationBrand> orderByComparator)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationBrandException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application brand, or <code>null</code> if a matching commerce application brand could not be found
	 */
	public static CommerceApplicationBrand fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application brand
	 * @throws NoSuchApplicationBrandException if a matching commerce application brand could not be found
	 */
	public static CommerceApplicationBrand findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceApplicationBrand> orderByComparator)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationBrandException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application brand, or <code>null</code> if a matching commerce application brand could not be found
	 */
	public static CommerceApplicationBrand fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the commerce application brands before and after the current commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param commerceApplicationBrandId the primary key of the current commerce application brand
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application brand
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	public static CommerceApplicationBrand[] findByCompanyId_PrevAndNext(
			long commerceApplicationBrandId, long companyId,
			OrderByComparator<CommerceApplicationBrand> orderByComparator)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationBrandException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commerceApplicationBrandId, companyId, orderByComparator);
	}

	/**
	 * Returns all the commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce application brands that the user has permission to view
	 */
	public static List<CommerceApplicationBrand> filterFindByCompanyId(
		long companyId) {

		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @return the range of matching commerce application brands that the user has permission to view
	 */
	public static List<CommerceApplicationBrand> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce application brands that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application brands that the user has permission to view
	 */
	public static List<CommerceApplicationBrand> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce application brands before and after the current commerce application brand in the ordered set of commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceApplicationBrandId the primary key of the current commerce application brand
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application brand
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	public static CommerceApplicationBrand[] filterFindByCompanyId_PrevAndNext(
			long commerceApplicationBrandId, long companyId,
			OrderByComparator<CommerceApplicationBrand> orderByComparator)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationBrandException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			commerceApplicationBrandId, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce application brands where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce application brands where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce application brands
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce application brands that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Caches the commerce application brand in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationBrand the commerce application brand
	 */
	public static void cacheResult(
		CommerceApplicationBrand commerceApplicationBrand) {

		getPersistence().cacheResult(commerceApplicationBrand);
	}

	/**
	 * Caches the commerce application brands in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationBrands the commerce application brands
	 */
	public static void cacheResult(
		List<CommerceApplicationBrand> commerceApplicationBrands) {

		getPersistence().cacheResult(commerceApplicationBrands);
	}

	/**
	 * Creates a new commerce application brand with the primary key. Does not add the commerce application brand to the database.
	 *
	 * @param commerceApplicationBrandId the primary key for the new commerce application brand
	 * @return the new commerce application brand
	 */
	public static CommerceApplicationBrand create(
		long commerceApplicationBrandId) {

		return getPersistence().create(commerceApplicationBrandId);
	}

	/**
	 * Removes the commerce application brand with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand that was removed
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	public static CommerceApplicationBrand remove(
			long commerceApplicationBrandId)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationBrandException {

		return getPersistence().remove(commerceApplicationBrandId);
	}

	public static CommerceApplicationBrand updateImpl(
		CommerceApplicationBrand commerceApplicationBrand) {

		return getPersistence().updateImpl(commerceApplicationBrand);
	}

	/**
	 * Returns the commerce application brand with the primary key or throws a <code>NoSuchApplicationBrandException</code> if it could not be found.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	public static CommerceApplicationBrand findByPrimaryKey(
			long commerceApplicationBrandId)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationBrandException {

		return getPersistence().findByPrimaryKey(commerceApplicationBrandId);
	}

	/**
	 * Returns the commerce application brand with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand, or <code>null</code> if a commerce application brand with the primary key could not be found
	 */
	public static CommerceApplicationBrand fetchByPrimaryKey(
		long commerceApplicationBrandId) {

		return getPersistence().fetchByPrimaryKey(commerceApplicationBrandId);
	}

	/**
	 * Returns all the commerce application brands.
	 *
	 * @return the commerce application brands
	 */
	public static List<CommerceApplicationBrand> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce application brands.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @return the range of commerce application brands
	 */
	public static List<CommerceApplicationBrand> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce application brands.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce application brands
	 */
	public static List<CommerceApplicationBrand> findAll(
		int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce application brands.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce application brands
	 */
	public static List<CommerceApplicationBrand> findAll(
		int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce application brands from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce application brands.
	 *
	 * @return the number of commerce application brands
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceApplicationBrandPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceApplicationBrandPersistence,
		 CommerceApplicationBrandPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceApplicationBrandPersistence.class);

		ServiceTracker
			<CommerceApplicationBrandPersistence,
			 CommerceApplicationBrandPersistence> serviceTracker =
				new ServiceTracker
					<CommerceApplicationBrandPersistence,
					 CommerceApplicationBrandPersistence>(
						 bundle.getBundleContext(),
						 CommerceApplicationBrandPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}