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

package com.liferay.commerce.pricing.service.persistence;

import com.liferay.commerce.pricing.exception.NoSuchPricingClassException;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce pricing class service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePricingClassUtil
 * @generated
 */
@ProviderType
public interface CommercePricingClassPersistence
	extends BasePersistence<CommercePricingClass>,
			CTPersistence<CommercePricingClass> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePricingClassUtil} to access the commerce pricing class persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce pricing classes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce pricing classes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @return the range of matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce pricing classes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce pricing classes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce pricing class in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class
	 * @throws NoSuchPricingClassException if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Returns the first commerce pricing class in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class, or <code>null</code> if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns the last commerce pricing class in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce pricing class
	 * @throws NoSuchPricingClassException if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Returns the last commerce pricing class in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce pricing class, or <code>null</code> if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns the commerce pricing classes before and after the current commerce pricing class in the ordered set where uuid = &#63;.
	 *
	 * @param commercePricingClassId the primary key of the current commerce pricing class
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce pricing class
	 * @throws NoSuchPricingClassException if a commerce pricing class with the primary key could not be found
	 */
	public CommercePricingClass[] findByUuid_PrevAndNext(
			long commercePricingClassId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Returns all the commerce pricing classes that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce pricing classes that the user has permission to view
	 */
	public java.util.List<CommercePricingClass> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the commerce pricing classes that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @return the range of matching commerce pricing classes that the user has permission to view
	 */
	public java.util.List<CommercePricingClass> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce pricing classes that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce pricing classes that the user has permission to view
	 */
	public java.util.List<CommercePricingClass> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns the commerce pricing classes before and after the current commerce pricing class in the ordered set of commerce pricing classes that the user has permission to view where uuid = &#63;.
	 *
	 * @param commercePricingClassId the primary key of the current commerce pricing class
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce pricing class
	 * @throws NoSuchPricingClassException if a commerce pricing class with the primary key could not be found
	 */
	public CommercePricingClass[] filterFindByUuid_PrevAndNext(
			long commercePricingClassId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Removes all the commerce pricing classes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce pricing classes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce pricing classes
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of commerce pricing classes that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce pricing classes that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the commerce pricing classes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce pricing classes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @return the range of matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce pricing classes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce pricing classes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce pricing class in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class
	 * @throws NoSuchPricingClassException if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Returns the first commerce pricing class in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class, or <code>null</code> if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns the last commerce pricing class in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce pricing class
	 * @throws NoSuchPricingClassException if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Returns the last commerce pricing class in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce pricing class, or <code>null</code> if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns the commerce pricing classes before and after the current commerce pricing class in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePricingClassId the primary key of the current commerce pricing class
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce pricing class
	 * @throws NoSuchPricingClassException if a commerce pricing class with the primary key could not be found
	 */
	public CommercePricingClass[] findByUuid_C_PrevAndNext(
			long commercePricingClassId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Returns all the commerce pricing classes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce pricing classes that the user has permission to view
	 */
	public java.util.List<CommercePricingClass> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce pricing classes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @return the range of matching commerce pricing classes that the user has permission to view
	 */
	public java.util.List<CommercePricingClass> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce pricing classes that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce pricing classes that the user has permission to view
	 */
	public java.util.List<CommercePricingClass> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns the commerce pricing classes before and after the current commerce pricing class in the ordered set of commerce pricing classes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePricingClassId the primary key of the current commerce pricing class
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce pricing class
	 * @throws NoSuchPricingClassException if a commerce pricing class with the primary key could not be found
	 */
	public CommercePricingClass[] filterFindByUuid_C_PrevAndNext(
			long commercePricingClassId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Removes all the commerce pricing classes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce pricing classes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce pricing classes
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce pricing classes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce pricing classes that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce pricing classes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the commerce pricing classes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @return the range of matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce pricing classes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce pricing classes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce pricing class in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class
	 * @throws NoSuchPricingClassException if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Returns the first commerce pricing class in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class, or <code>null</code> if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns the last commerce pricing class in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce pricing class
	 * @throws NoSuchPricingClassException if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Returns the last commerce pricing class in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce pricing class, or <code>null</code> if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns the commerce pricing classes before and after the current commerce pricing class in the ordered set where companyId = &#63;.
	 *
	 * @param commercePricingClassId the primary key of the current commerce pricing class
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce pricing class
	 * @throws NoSuchPricingClassException if a commerce pricing class with the primary key could not be found
	 */
	public CommercePricingClass[] findByCompanyId_PrevAndNext(
			long commercePricingClassId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Returns all the commerce pricing classes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce pricing classes that the user has permission to view
	 */
	public java.util.List<CommercePricingClass> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce pricing classes that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @return the range of matching commerce pricing classes that the user has permission to view
	 */
	public java.util.List<CommercePricingClass> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce pricing classes that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce pricing classes that the user has permission to view
	 */
	public java.util.List<CommercePricingClass> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns the commerce pricing classes before and after the current commerce pricing class in the ordered set of commerce pricing classes that the user has permission to view where companyId = &#63;.
	 *
	 * @param commercePricingClassId the primary key of the current commerce pricing class
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce pricing class
	 * @throws NoSuchPricingClassException if a commerce pricing class with the primary key could not be found
	 */
	public CommercePricingClass[] filterFindByCompanyId_PrevAndNext(
			long commercePricingClassId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePricingClass> orderByComparator)
		throws NoSuchPricingClassException;

	/**
	 * Removes all the commerce pricing classes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce pricing classes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce pricing classes
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of commerce pricing classes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce pricing classes that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns the commerce pricing class where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchPricingClassException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce pricing class
	 * @throws NoSuchPricingClassException if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchPricingClassException;

	/**
	 * Returns the commerce pricing class where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce pricing class, or <code>null</code> if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce pricing class where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce pricing class, or <code>null</code> if a matching commerce pricing class could not be found
	 */
	public CommercePricingClass fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the commerce pricing class where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce pricing class that was removed
	 */
	public CommercePricingClass removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchPricingClassException;

	/**
	 * Returns the number of commerce pricing classes where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce pricing classes
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the commerce pricing class in the entity cache if it is enabled.
	 *
	 * @param commercePricingClass the commerce pricing class
	 */
	public void cacheResult(CommercePricingClass commercePricingClass);

	/**
	 * Caches the commerce pricing classes in the entity cache if it is enabled.
	 *
	 * @param commercePricingClasses the commerce pricing classes
	 */
	public void cacheResult(
		java.util.List<CommercePricingClass> commercePricingClasses);

	/**
	 * Creates a new commerce pricing class with the primary key. Does not add the commerce pricing class to the database.
	 *
	 * @param commercePricingClassId the primary key for the new commerce pricing class
	 * @return the new commerce pricing class
	 */
	public CommercePricingClass create(long commercePricingClassId);

	/**
	 * Removes the commerce pricing class with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePricingClassId the primary key of the commerce pricing class
	 * @return the commerce pricing class that was removed
	 * @throws NoSuchPricingClassException if a commerce pricing class with the primary key could not be found
	 */
	public CommercePricingClass remove(long commercePricingClassId)
		throws NoSuchPricingClassException;

	public CommercePricingClass updateImpl(
		CommercePricingClass commercePricingClass);

	/**
	 * Returns the commerce pricing class with the primary key or throws a <code>NoSuchPricingClassException</code> if it could not be found.
	 *
	 * @param commercePricingClassId the primary key of the commerce pricing class
	 * @return the commerce pricing class
	 * @throws NoSuchPricingClassException if a commerce pricing class with the primary key could not be found
	 */
	public CommercePricingClass findByPrimaryKey(long commercePricingClassId)
		throws NoSuchPricingClassException;

	/**
	 * Returns the commerce pricing class with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePricingClassId the primary key of the commerce pricing class
	 * @return the commerce pricing class, or <code>null</code> if a commerce pricing class with the primary key could not be found
	 */
	public CommercePricingClass fetchByPrimaryKey(long commercePricingClassId);

	/**
	 * Returns all the commerce pricing classes.
	 *
	 * @return the commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findAll();

	/**
	 * Returns a range of all the commerce pricing classes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @return the range of commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce pricing classes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce pricing classes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce pricing classes
	 */
	public java.util.List<CommercePricingClass> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePricingClass>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce pricing classes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce pricing classes.
	 *
	 * @return the number of commerce pricing classes
	 */
	public int countAll();

}