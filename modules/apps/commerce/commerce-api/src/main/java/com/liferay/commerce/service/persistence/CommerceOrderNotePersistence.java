/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.exception.NoSuchOrderNoteException;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce order note service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNoteUtil
 * @generated
 */
@ProviderType
public interface CommerceOrderNotePersistence
	extends BasePersistence<CommerceOrderNote> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderNoteUtil} to access the commerce order note persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce order notes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce order notes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order notes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order notes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order note in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public CommerceOrderNote findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the first commerce order note in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns the last commerce order note in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public CommerceOrderNote findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the last commerce order note in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns the commerce order notes before and after the current commerce order note in the ordered set where uuid = &#63;.
	 *
	 * @param commerceOrderNoteId the primary key of the current commerce order note
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public CommerceOrderNote[] findByUuid_PrevAndNext(
			long commerceOrderNoteId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Removes all the commerce order notes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce order notes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce order notes
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the commerce order note where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchOrderNoteException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public CommerceOrderNote findByUUID_G(String uuid, long groupId)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the commerce order note where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the commerce order note where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the commerce order note where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce order note that was removed
	 */
	public CommerceOrderNote removeByUUID_G(String uuid, long groupId)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the number of commerce order notes where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce order notes
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the commerce order notes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce order notes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order notes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order notes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order note in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public CommerceOrderNote findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the first commerce order note in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns the last commerce order note in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public CommerceOrderNote findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the last commerce order note in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns the commerce order notes before and after the current commerce order note in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceOrderNoteId the primary key of the current commerce order note
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public CommerceOrderNote[] findByUuid_C_PrevAndNext(
			long commerceOrderNoteId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Removes all the commerce order notes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce order notes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce order notes
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId);

	/**
	 * Returns a range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public CommerceOrderNote findByCommerceOrderId_First(
			long commerceOrderId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByCommerceOrderId_First(
		long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public CommerceOrderNote findByCommerceOrderId_Last(
			long commerceOrderId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByCommerceOrderId_Last(
		long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns the commerce order notes before and after the current commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderNoteId the primary key of the current commerce order note
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public CommerceOrderNote[] findByCommerceOrderId_PrevAndNext(
			long commerceOrderNoteId, long commerceOrderId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Removes all the commerce order notes where commerceOrderId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 */
	public void removeByCommerceOrderId(long commerceOrderId);

	/**
	 * Returns the number of commerce order notes where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the number of matching commerce order notes
	 */
	public int countByCommerceOrderId(long commerceOrderId);

	/**
	 * Returns all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @return the matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted);

	/**
	 * Returns a range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public CommerceOrderNote findByC_R_First(
			long commerceOrderId, boolean restricted,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByC_R_First(
		long commerceOrderId, boolean restricted,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public CommerceOrderNote findByC_R_Last(
			long commerceOrderId, boolean restricted,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByC_R_Last(
		long commerceOrderId, boolean restricted,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns the commerce order notes before and after the current commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderNoteId the primary key of the current commerce order note
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public CommerceOrderNote[] findByC_R_PrevAndNext(
			long commerceOrderNoteId, long commerceOrderId, boolean restricted,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
				orderByComparator)
		throws NoSuchOrderNoteException;

	/**
	 * Removes all the commerce order notes where commerceOrderId = &#63; and restricted = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 */
	public void removeByC_R(long commerceOrderId, boolean restricted);

	/**
	 * Returns the number of commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @return the number of matching commerce order notes
	 */
	public int countByC_R(long commerceOrderId, boolean restricted);

	/**
	 * Returns the commerce order note where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchOrderNoteException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	public CommerceOrderNote findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the commerce order note where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the commerce order note where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	public CommerceOrderNote fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the commerce order note where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce order note that was removed
	 */
	public CommerceOrderNote removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the number of commerce order notes where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce order notes
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the commerce order note in the entity cache if it is enabled.
	 *
	 * @param commerceOrderNote the commerce order note
	 */
	public void cacheResult(CommerceOrderNote commerceOrderNote);

	/**
	 * Caches the commerce order notes in the entity cache if it is enabled.
	 *
	 * @param commerceOrderNotes the commerce order notes
	 */
	public void cacheResult(
		java.util.List<CommerceOrderNote> commerceOrderNotes);

	/**
	 * Creates a new commerce order note with the primary key. Does not add the commerce order note to the database.
	 *
	 * @param commerceOrderNoteId the primary key for the new commerce order note
	 * @return the new commerce order note
	 */
	public CommerceOrderNote create(long commerceOrderNoteId);

	/**
	 * Removes the commerce order note with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderNoteId the primary key of the commerce order note
	 * @return the commerce order note that was removed
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public CommerceOrderNote remove(long commerceOrderNoteId)
		throws NoSuchOrderNoteException;

	public CommerceOrderNote updateImpl(CommerceOrderNote commerceOrderNote);

	/**
	 * Returns the commerce order note with the primary key or throws a <code>NoSuchOrderNoteException</code> if it could not be found.
	 *
	 * @param commerceOrderNoteId the primary key of the commerce order note
	 * @return the commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	public CommerceOrderNote findByPrimaryKey(long commerceOrderNoteId)
		throws NoSuchOrderNoteException;

	/**
	 * Returns the commerce order note with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderNoteId the primary key of the commerce order note
	 * @return the commerce order note, or <code>null</code> if a commerce order note with the primary key could not be found
	 */
	public CommerceOrderNote fetchByPrimaryKey(long commerceOrderNoteId);

	/**
	 * Returns all the commerce order notes.
	 *
	 * @return the commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findAll();

	/**
	 * Returns a range of all the commerce order notes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce order notes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order notes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order notes
	 */
	public java.util.List<CommerceOrderNote> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderNote>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce order notes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce order notes.
	 *
	 * @return the number of commerce order notes
	 */
	public int countAll();

}