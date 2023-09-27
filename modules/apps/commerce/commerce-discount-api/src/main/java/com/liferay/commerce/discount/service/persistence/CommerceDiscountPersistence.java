/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.service.persistence;

import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce discount service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountUtil
 * @generated
 */
@ProviderType
public interface CommerceDiscountPersistence
	extends BasePersistence<CommerceDiscount> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceDiscountUtil} to access the commerce discount persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce discounts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce discounts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discounts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the first commerce discount in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the last commerce discount in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the last commerce discount in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set where uuid = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] findByUuid_PrevAndNext(
			long commerceDiscountId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns all the commerce discounts that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set of commerce discounts that the user has permission to view where uuid = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] filterFindByUuid_PrevAndNext(
			long commerceDiscountId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Removes all the commerce discounts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce discounts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce discounts
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of commerce discounts that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the commerce discounts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the first commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the last commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the last commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] findByUuid_C_PrevAndNext(
			long commerceDiscountId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns all the commerce discounts that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set of commerce discounts that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] filterFindByUuid_C_PrevAndNext(
			long commerceDiscountId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Removes all the commerce discounts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce discounts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce discounts
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce discounts that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce discounts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the commerce discounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the last commerce discount in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the last commerce discount in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set where companyId = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] findByCompanyId_PrevAndNext(
			long commerceDiscountId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns all the commerce discounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set of commerce discounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] filterFindByCompanyId_PrevAndNext(
			long commerceDiscountId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Removes all the commerce discounts where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce discounts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce discounts
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of commerce discounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the commerce discounts where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @return the matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByC_C(
		long companyId, String couponCode);

	/**
	 * Returns a range of all the commerce discounts where companyId = &#63; and couponCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByC_C(
		long companyId, String couponCode, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63; and couponCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByC_C(
		long companyId, String couponCode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63; and couponCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByC_C(
		long companyId, String couponCode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByC_C_First(
			long companyId, String couponCode,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByC_C_First(
		long companyId, String couponCode,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the last commerce discount in the ordered set where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByC_C_Last(
			long companyId, String couponCode,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the last commerce discount in the ordered set where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByC_C_Last(
		long companyId, String couponCode,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] findByC_C_PrevAndNext(
			long commerceDiscountId, long companyId, String couponCode,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns all the commerce discounts that the user has permission to view where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @return the matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByC_C(
		long companyId, String couponCode);

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where companyId = &#63; and couponCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByC_C(
		long companyId, String couponCode, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where companyId = &#63; and couponCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByC_C(
		long companyId, String couponCode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set of commerce discounts that the user has permission to view where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] filterFindByC_C_PrevAndNext(
			long commerceDiscountId, long companyId, String couponCode,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Removes all the commerce discounts where companyId = &#63; and couponCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 */
	public void removeByC_C(long companyId, String couponCode);

	/**
	 * Returns the number of commerce discounts where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @return the number of matching commerce discounts
	 */
	public int countByC_C(long companyId, String couponCode);

	/**
	 * Returns the number of commerce discounts that the user has permission to view where companyId = &#63; and couponCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	public int filterCountByC_C(long companyId, String couponCode);

	/**
	 * Returns all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the first commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the last commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the last commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] findByLtD_S_PrevAndNext(
			long commerceDiscountId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns all the commerce discounts that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set of commerce discounts that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] filterFindByLtD_S_PrevAndNext(
			long commerceDiscountId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Removes all the commerce discounts where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce discounts where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce discounts
	 */
	public int countByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce discounts that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	public int filterCountByLtD_S(Date displayDate, int status);

	/**
	 * Returns all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByLtE_S_First(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the first commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByLtE_S_First(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the last commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByLtE_S_Last(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the last commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByLtE_S_Last(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] findByLtE_S_PrevAndNext(
			long commerceDiscountId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns all the commerce discounts that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set of commerce discounts that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] filterFindByLtE_S_PrevAndNext(
			long commerceDiscountId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Removes all the commerce discounts where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public void removeByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce discounts
	 */
	public int countByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce discounts that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	public int filterCountByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the commerce discount where companyId = &#63; and couponCode = &#63; and active = &#63; or throws a <code>NoSuchDiscountException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param active the active
	 * @return the matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByC_C_A(
			long companyId, String couponCode, boolean active)
		throws NoSuchDiscountException;

	/**
	 * Returns the commerce discount where companyId = &#63; and couponCode = &#63; and active = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param active the active
	 * @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByC_C_A(
		long companyId, String couponCode, boolean active);

	/**
	 * Returns the commerce discount where companyId = &#63; and couponCode = &#63; and active = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param active the active
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByC_C_A(
		long companyId, String couponCode, boolean active,
		boolean useFinderCache);

	/**
	 * Removes the commerce discount where companyId = &#63; and couponCode = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param active the active
	 * @return the commerce discount that was removed
	 */
	public CommerceDiscount removeByC_C_A(
			long companyId, String couponCode, boolean active)
		throws NoSuchDiscountException;

	/**
	 * Returns the number of commerce discounts where companyId = &#63; and couponCode = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param couponCode the coupon code
	 * @param active the active
	 * @return the number of matching commerce discounts
	 */
	public int countByC_C_A(long companyId, String couponCode, boolean active);

	/**
	 * Returns all the commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @return the matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByC_L_A_S(
		long companyId, String level, boolean active, int status);

	/**
	 * Returns a range of all the commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByC_L_A_S(
		long companyId, String level, boolean active, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByC_L_A_S(
		long companyId, String level, boolean active, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discounts
	 */
	public java.util.List<CommerceDiscount> findByC_L_A_S(
		long companyId, String level, boolean active, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByC_L_A_S_First(
			long companyId, String level, boolean active, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the first commerce discount in the ordered set where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByC_L_A_S_First(
		long companyId, String level, boolean active, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the last commerce discount in the ordered set where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByC_L_A_S_Last(
			long companyId, String level, boolean active, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns the last commerce discount in the ordered set where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByC_L_A_S_Last(
		long companyId, String level, boolean active, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] findByC_L_A_S_PrevAndNext(
			long commerceDiscountId, long companyId, String level,
			boolean active, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Returns all the commerce discounts that the user has permission to view where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @return the matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByC_L_A_S(
		long companyId, String level, boolean active, int status);

	/**
	 * Returns a range of all the commerce discounts that the user has permission to view where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByC_L_A_S(
		long companyId, String level, boolean active, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the commerce discounts that the user has permissions to view where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discounts that the user has permission to view
	 */
	public java.util.List<CommerceDiscount> filterFindByC_L_A_S(
		long companyId, String level, boolean active, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns the commerce discounts before and after the current commerce discount in the ordered set of commerce discounts that the user has permission to view where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param commerceDiscountId the primary key of the current commerce discount
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount[] filterFindByC_L_A_S_PrevAndNext(
			long commerceDiscountId, long companyId, String level,
			boolean active, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
				orderByComparator)
		throws NoSuchDiscountException;

	/**
	 * Removes all the commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 */
	public void removeByC_L_A_S(
		long companyId, String level, boolean active, int status);

	/**
	 * Returns the number of commerce discounts where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @return the number of matching commerce discounts
	 */
	public int countByC_L_A_S(
		long companyId, String level, boolean active, int status);

	/**
	 * Returns the number of commerce discounts that the user has permission to view where companyId = &#63; and level = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param level the level
	 * @param active the active
	 * @param status the status
	 * @return the number of matching commerce discounts that the user has permission to view
	 */
	public int filterCountByC_L_A_S(
		long companyId, String level, boolean active, int status);

	/**
	 * Returns the commerce discount where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchDiscountException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce discount
	 * @throws NoSuchDiscountException if a matching commerce discount could not be found
	 */
	public CommerceDiscount findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchDiscountException;

	/**
	 * Returns the commerce discount where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the commerce discount where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	 */
	public CommerceDiscount fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the commerce discount where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce discount that was removed
	 */
	public CommerceDiscount removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchDiscountException;

	/**
	 * Returns the number of commerce discounts where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce discounts
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the commerce discount in the entity cache if it is enabled.
	 *
	 * @param commerceDiscount the commerce discount
	 */
	public void cacheResult(CommerceDiscount commerceDiscount);

	/**
	 * Caches the commerce discounts in the entity cache if it is enabled.
	 *
	 * @param commerceDiscounts the commerce discounts
	 */
	public void cacheResult(java.util.List<CommerceDiscount> commerceDiscounts);

	/**
	 * Creates a new commerce discount with the primary key. Does not add the commerce discount to the database.
	 *
	 * @param commerceDiscountId the primary key for the new commerce discount
	 * @return the new commerce discount
	 */
	public CommerceDiscount create(long commerceDiscountId);

	/**
	 * Removes the commerce discount with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountId the primary key of the commerce discount
	 * @return the commerce discount that was removed
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount remove(long commerceDiscountId)
		throws NoSuchDiscountException;

	public CommerceDiscount updateImpl(CommerceDiscount commerceDiscount);

	/**
	 * Returns the commerce discount with the primary key or throws a <code>NoSuchDiscountException</code> if it could not be found.
	 *
	 * @param commerceDiscountId the primary key of the commerce discount
	 * @return the commerce discount
	 * @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount findByPrimaryKey(long commerceDiscountId)
		throws NoSuchDiscountException;

	/**
	 * Returns the commerce discount with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountId the primary key of the commerce discount
	 * @return the commerce discount, or <code>null</code> if a commerce discount with the primary key could not be found
	 */
	public CommerceDiscount fetchByPrimaryKey(long commerceDiscountId);

	/**
	 * Returns all the commerce discounts.
	 *
	 * @return the commerce discounts
	 */
	public java.util.List<CommerceDiscount> findAll();

	/**
	 * Returns a range of all the commerce discounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @return the range of commerce discounts
	 */
	public java.util.List<CommerceDiscount> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce discounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discounts
	 */
	public java.util.List<CommerceDiscount> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discounts
	 * @param end the upper bound of the range of commerce discounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discounts
	 */
	public java.util.List<CommerceDiscount> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce discounts from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce discounts.
	 *
	 * @return the number of commerce discounts
	 */
	public int countAll();

}