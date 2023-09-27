/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service.persistence;

import com.liferay.commerce.payment.exception.NoSuchPaymentEntryException;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce payment entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryUtil
 * @generated
 */
@ProviderType
public interface CommercePaymentEntryPersistence
	extends BasePersistence<CommercePaymentEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePaymentEntryUtil} to access the commerce payment entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce payment entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the commerce payment entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @return the range of matching commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce payment entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce payment entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce payment entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment entry
	 * @throws NoSuchPaymentEntryException if a matching commerce payment entry could not be found
	 */
	public CommercePaymentEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntry> orderByComparator)
		throws NoSuchPaymentEntryException;

	/**
	 * Returns the first commerce payment entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment entry, or <code>null</code> if a matching commerce payment entry could not be found
	 */
	public CommercePaymentEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator);

	/**
	 * Returns the last commerce payment entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment entry
	 * @throws NoSuchPaymentEntryException if a matching commerce payment entry could not be found
	 */
	public CommercePaymentEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntry> orderByComparator)
		throws NoSuchPaymentEntryException;

	/**
	 * Returns the last commerce payment entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment entry, or <code>null</code> if a matching commerce payment entry could not be found
	 */
	public CommercePaymentEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator);

	/**
	 * Returns the commerce payment entries before and after the current commerce payment entry in the ordered set where companyId = &#63;.
	 *
	 * @param commercePaymentEntryId the primary key of the current commerce payment entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment entry
	 * @throws NoSuchPaymentEntryException if a commerce payment entry with the primary key could not be found
	 */
	public CommercePaymentEntry[] findByCompanyId_PrevAndNext(
			long commercePaymentEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntry> orderByComparator)
		throws NoSuchPaymentEntryException;

	/**
	 * Returns all the commerce payment entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce payment entries that the user has permission to view
	 */
	public java.util.List<CommercePaymentEntry> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce payment entries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @return the range of matching commerce payment entries that the user has permission to view
	 */
	public java.util.List<CommercePaymentEntry> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce payment entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment entries that the user has permission to view
	 */
	public java.util.List<CommercePaymentEntry> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator);

	/**
	 * Returns the commerce payment entries before and after the current commerce payment entry in the ordered set of commerce payment entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param commercePaymentEntryId the primary key of the current commerce payment entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment entry
	 * @throws NoSuchPaymentEntryException if a commerce payment entry with the primary key could not be found
	 */
	public CommercePaymentEntry[] filterFindByCompanyId_PrevAndNext(
			long commercePaymentEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntry> orderByComparator)
		throws NoSuchPaymentEntryException;

	/**
	 * Removes all the commerce payment entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce payment entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce payment entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of commerce payment entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce payment entries that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the commerce payment entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findByC_C_C(
		long companyId, long classNameId, long classPK);

	/**
	 * Returns a range of all the commerce payment entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @return the range of matching commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the commerce payment entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce payment entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce payment entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment entry
	 * @throws NoSuchPaymentEntryException if a matching commerce payment entry could not be found
	 */
	public CommercePaymentEntry findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntry> orderByComparator)
		throws NoSuchPaymentEntryException;

	/**
	 * Returns the first commerce payment entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment entry, or <code>null</code> if a matching commerce payment entry could not be found
	 */
	public CommercePaymentEntry fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator);

	/**
	 * Returns the last commerce payment entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment entry
	 * @throws NoSuchPaymentEntryException if a matching commerce payment entry could not be found
	 */
	public CommercePaymentEntry findByC_C_C_Last(
			long companyId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntry> orderByComparator)
		throws NoSuchPaymentEntryException;

	/**
	 * Returns the last commerce payment entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment entry, or <code>null</code> if a matching commerce payment entry could not be found
	 */
	public CommercePaymentEntry fetchByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator);

	/**
	 * Returns the commerce payment entries before and after the current commerce payment entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePaymentEntryId the primary key of the current commerce payment entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment entry
	 * @throws NoSuchPaymentEntryException if a commerce payment entry with the primary key could not be found
	 */
	public CommercePaymentEntry[] findByC_C_C_PrevAndNext(
			long commercePaymentEntryId, long companyId, long classNameId,
			long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntry> orderByComparator)
		throws NoSuchPaymentEntryException;

	/**
	 * Returns all the commerce payment entries that the user has permission to view where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce payment entries that the user has permission to view
	 */
	public java.util.List<CommercePaymentEntry> filterFindByC_C_C(
		long companyId, long classNameId, long classPK);

	/**
	 * Returns a range of all the commerce payment entries that the user has permission to view where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @return the range of matching commerce payment entries that the user has permission to view
	 */
	public java.util.List<CommercePaymentEntry> filterFindByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the commerce payment entries that the user has permissions to view where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment entries that the user has permission to view
	 */
	public java.util.List<CommercePaymentEntry> filterFindByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator);

	/**
	 * Returns the commerce payment entries before and after the current commerce payment entry in the ordered set of commerce payment entries that the user has permission to view where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePaymentEntryId the primary key of the current commerce payment entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment entry
	 * @throws NoSuchPaymentEntryException if a commerce payment entry with the primary key could not be found
	 */
	public CommercePaymentEntry[] filterFindByC_C_C_PrevAndNext(
			long commercePaymentEntryId, long companyId, long classNameId,
			long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntry> orderByComparator)
		throws NoSuchPaymentEntryException;

	/**
	 * Removes all the commerce payment entries where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C_C(long companyId, long classNameId, long classPK);

	/**
	 * Returns the number of commerce payment entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce payment entries
	 */
	public int countByC_C_C(long companyId, long classNameId, long classPK);

	/**
	 * Returns the number of commerce payment entries that the user has permission to view where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce payment entries that the user has permission to view
	 */
	public int filterCountByC_C_C(
		long companyId, long classNameId, long classPK);

	/**
	 * Caches the commerce payment entry in the entity cache if it is enabled.
	 *
	 * @param commercePaymentEntry the commerce payment entry
	 */
	public void cacheResult(CommercePaymentEntry commercePaymentEntry);

	/**
	 * Caches the commerce payment entries in the entity cache if it is enabled.
	 *
	 * @param commercePaymentEntries the commerce payment entries
	 */
	public void cacheResult(
		java.util.List<CommercePaymentEntry> commercePaymentEntries);

	/**
	 * Creates a new commerce payment entry with the primary key. Does not add the commerce payment entry to the database.
	 *
	 * @param commercePaymentEntryId the primary key for the new commerce payment entry
	 * @return the new commerce payment entry
	 */
	public CommercePaymentEntry create(long commercePaymentEntryId);

	/**
	 * Removes the commerce payment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePaymentEntryId the primary key of the commerce payment entry
	 * @return the commerce payment entry that was removed
	 * @throws NoSuchPaymentEntryException if a commerce payment entry with the primary key could not be found
	 */
	public CommercePaymentEntry remove(long commercePaymentEntryId)
		throws NoSuchPaymentEntryException;

	public CommercePaymentEntry updateImpl(
		CommercePaymentEntry commercePaymentEntry);

	/**
	 * Returns the commerce payment entry with the primary key or throws a <code>NoSuchPaymentEntryException</code> if it could not be found.
	 *
	 * @param commercePaymentEntryId the primary key of the commerce payment entry
	 * @return the commerce payment entry
	 * @throws NoSuchPaymentEntryException if a commerce payment entry with the primary key could not be found
	 */
	public CommercePaymentEntry findByPrimaryKey(long commercePaymentEntryId)
		throws NoSuchPaymentEntryException;

	/**
	 * Returns the commerce payment entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePaymentEntryId the primary key of the commerce payment entry
	 * @return the commerce payment entry, or <code>null</code> if a commerce payment entry with the primary key could not be found
	 */
	public CommercePaymentEntry fetchByPrimaryKey(long commercePaymentEntryId);

	/**
	 * Returns all the commerce payment entries.
	 *
	 * @return the commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findAll();

	/**
	 * Returns a range of all the commerce payment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @return the range of commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce payment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce payment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce payment entries
	 */
	public java.util.List<CommercePaymentEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePaymentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce payment entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce payment entries.
	 *
	 * @return the number of commerce payment entries
	 */
	public int countAll();

}