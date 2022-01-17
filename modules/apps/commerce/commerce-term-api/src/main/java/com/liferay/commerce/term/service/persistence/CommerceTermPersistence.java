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

package com.liferay.commerce.term.service.persistence;

import com.liferay.commerce.term.exception.NoSuchTermException;
import com.liferay.commerce.term.model.CommerceTerm;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce term service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceTermUtil
 * @generated
 */
@ProviderType
public interface CommerceTermPersistence extends BasePersistence<CommerceTerm> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTermUtil} to access the commerce term persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce terms where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_A(
		long companyId, boolean active);

	/**
	 * Returns a range of all the commerce terms where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_A(
		long companyId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByC_A_First(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Returns the first commerce term in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByC_A_Last(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns the commerce terms before and after the current commerce term in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceTermId the primary key of the current commerce term
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	public CommerceTerm[] findByC_A_PrevAndNext(
			long commerceTermId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Removes all the commerce terms where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public void removeByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce terms where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce terms
	 */
	public int countByC_A(long companyId, boolean active);

	/**
	 * Returns all the commerce terms where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_LikeType(
		long companyId, String type);

	/**
	 * Returns a range of all the commerce terms where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_LikeType(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByC_LikeType_First(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Returns the first commerce term in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByC_LikeType_First(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByC_LikeType_Last(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByC_LikeType_Last(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns the commerce terms before and after the current commerce term in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param commerceTermId the primary key of the current commerce term
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	public CommerceTerm[] findByC_LikeType_PrevAndNext(
			long commerceTermId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Removes all the commerce terms where companyId = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_LikeType(long companyId, String type);

	/**
	 * Returns the number of commerce terms where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce terms
	 */
	public int countByC_LikeType(long companyId, String type);

	/**
	 * Returns all the commerce terms where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the commerce terms where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce terms where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce terms where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Returns the first commerce term in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns the last commerce term in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Returns the last commerce term in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns the commerce terms before and after the current commerce term in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTermId the primary key of the current commerce term
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	public CommerceTerm[] findByLtD_S_PrevAndNext(
			long commerceTermId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Removes all the commerce terms where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce terms where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce terms
	 */
	public int countByLtD_S(Date displayDate, int status);

	/**
	 * Returns all the commerce terms where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the commerce terms where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce terms where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce terms where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByLtE_S_First(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Returns the first commerce term in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByLtE_S_First(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns the last commerce term in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByLtE_S_Last(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Returns the last commerce term in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByLtE_S_Last(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns the commerce terms before and after the current commerce term in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTermId the primary key of the current commerce term
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	public CommerceTerm[] findByLtE_S_PrevAndNext(
			long commerceTermId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Removes all the commerce terms where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public void removeByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce terms where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce terms
	 */
	public int countByLtE_S(Date expirationDate, int status);

	/**
	 * Returns all the commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns a range of all the commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end);

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce terms
	 */
	public java.util.List<CommerceTerm> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce term in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByC_A_LikeType_First(
			long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Returns the first commerce term in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByC_A_LikeType_First(
		long companyId, boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByC_A_LikeType_Last(
			long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByC_A_LikeType_Last(
		long companyId, boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns the commerce terms before and after the current commerce term in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param commerceTermId the primary key of the current commerce term
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	public CommerceTerm[] findByC_A_LikeType_PrevAndNext(
			long commerceTermId, long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
				orderByComparator)
		throws NoSuchTermException;

	/**
	 * Removes all the commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 */
	public void removeByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns the number of commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce terms
	 */
	public int countByC_A_LikeType(long companyId, boolean active, String type);

	/**
	 * Returns the commerce term where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchTermException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	public CommerceTerm findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchTermException;

	/**
	 * Returns the commerce term where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce term where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	public CommerceTerm fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the commerce term where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce term that was removed
	 */
	public CommerceTerm removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchTermException;

	/**
	 * Returns the number of commerce terms where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce terms
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the commerce term in the entity cache if it is enabled.
	 *
	 * @param commerceTerm the commerce term
	 */
	public void cacheResult(CommerceTerm commerceTerm);

	/**
	 * Caches the commerce terms in the entity cache if it is enabled.
	 *
	 * @param commerceTerms the commerce terms
	 */
	public void cacheResult(java.util.List<CommerceTerm> commerceTerms);

	/**
	 * Creates a new commerce term with the primary key. Does not add the commerce term to the database.
	 *
	 * @param commerceTermId the primary key for the new commerce term
	 * @return the new commerce term
	 */
	public CommerceTerm create(long commerceTermId);

	/**
	 * Removes the commerce term with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTermId the primary key of the commerce term
	 * @return the commerce term that was removed
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	public CommerceTerm remove(long commerceTermId) throws NoSuchTermException;

	public CommerceTerm updateImpl(CommerceTerm commerceTerm);

	/**
	 * Returns the commerce term with the primary key or throws a <code>NoSuchTermException</code> if it could not be found.
	 *
	 * @param commerceTermId the primary key of the commerce term
	 * @return the commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	public CommerceTerm findByPrimaryKey(long commerceTermId)
		throws NoSuchTermException;

	/**
	 * Returns the commerce term with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTermId the primary key of the commerce term
	 * @return the commerce term, or <code>null</code> if a commerce term with the primary key could not be found
	 */
	public CommerceTerm fetchByPrimaryKey(long commerceTermId);

	/**
	 * Returns all the commerce terms.
	 *
	 * @return the commerce terms
	 */
	public java.util.List<CommerceTerm> findAll();

	/**
	 * Returns a range of all the commerce terms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of commerce terms
	 */
	public java.util.List<CommerceTerm> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce terms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce terms
	 */
	public java.util.List<CommerceTerm> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce terms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce terms
	 */
	public java.util.List<CommerceTerm> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTerm>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce terms from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce terms.
	 *
	 * @return the number of commerce terms
	 */
	public int countAll();

}