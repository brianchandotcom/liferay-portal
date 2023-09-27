/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.service.persistence;

import com.liferay.client.extension.exception.NoSuchClientExtensionEntryException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the client extension entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryUtil
 * @generated
 */
@ProviderType
public interface ClientExtensionEntryPersistence
	extends BasePersistence<ClientExtensionEntry>,
			CTPersistence<ClientExtensionEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ClientExtensionEntryUtil} to access the client extension entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the client extension entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the client extension entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first client extension entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the first client extension entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the last client extension entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the last client extension entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set where uuid = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry[] findByUuid_PrevAndNext(
			long clientExtensionEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns all the client extension entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the client extension entries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set of client extension entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry[] filterFindByUuid_PrevAndNext(
			long clientExtensionEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Removes all the client extension entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of client extension entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching client extension entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of client extension entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching client extension entries that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the client extension entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the client extension entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first client extension entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the first client extension entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the last client extension entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the last client extension entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry[] findByUuid_C_PrevAndNext(
			long clientExtensionEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns all the client extension entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the client extension entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set of client extension entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry[] filterFindByUuid_C_PrevAndNext(
			long clientExtensionEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Removes all the client extension entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of client extension entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching client extension entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of client extension entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching client extension entries that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the client extension entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the client extension entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first client extension entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the first client extension entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the last client extension entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the last client extension entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set where companyId = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry[] findByCompanyId_PrevAndNext(
			long clientExtensionEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns all the client extension entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the client extension entries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set of client extension entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry[] filterFindByCompanyId_PrevAndNext(
			long clientExtensionEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Removes all the client extension entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of client extension entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching client extension entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of client extension entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching client extension entries that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the client extension entries where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByC_T(
		long companyId, String type);

	/**
	 * Returns a range of all the client extension entries where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByC_T(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entries where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entries where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first client extension entry in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry findByC_T_First(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the first client extension entry in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry fetchByC_T_First(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the last client extension entry in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry findByC_T_Last(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the last client extension entry in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry fetchByC_T_Last(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry[] findByC_T_PrevAndNext(
			long clientExtensionEntryId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns all the client extension entries that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByC_T(
		long companyId, String type);

	/**
	 * Returns a range of all the client extension entries that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByC_T(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entries that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries that the user has permission to view
	 */
	public java.util.List<ClientExtensionEntry> filterFindByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set of client extension entries that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry[] filterFindByC_T_PrevAndNext(
			long clientExtensionEntryId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntry> orderByComparator)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Removes all the client extension entries where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_T(long companyId, String type);

	/**
	 * Returns the number of client extension entries where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching client extension entries
	 */
	public int countByC_T(long companyId, String type);

	/**
	 * Returns the number of client extension entries that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching client extension entries that the user has permission to view
	 */
	public int filterCountByC_T(long companyId, String type);

	/**
	 * Returns the client extension entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchClientExtensionEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the client extension entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the client extension entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public ClientExtensionEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the client extension entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the client extension entry that was removed
	 */
	public ClientExtensionEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the number of client extension entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching client extension entries
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the client extension entry in the entity cache if it is enabled.
	 *
	 * @param clientExtensionEntry the client extension entry
	 */
	public void cacheResult(ClientExtensionEntry clientExtensionEntry);

	/**
	 * Caches the client extension entries in the entity cache if it is enabled.
	 *
	 * @param clientExtensionEntries the client extension entries
	 */
	public void cacheResult(
		java.util.List<ClientExtensionEntry> clientExtensionEntries);

	/**
	 * Creates a new client extension entry with the primary key. Does not add the client extension entry to the database.
	 *
	 * @param clientExtensionEntryId the primary key for the new client extension entry
	 * @return the new client extension entry
	 */
	public ClientExtensionEntry create(long clientExtensionEntryId);

	/**
	 * Removes the client extension entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param clientExtensionEntryId the primary key of the client extension entry
	 * @return the client extension entry that was removed
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry remove(long clientExtensionEntryId)
		throws NoSuchClientExtensionEntryException;

	public ClientExtensionEntry updateImpl(
		ClientExtensionEntry clientExtensionEntry);

	/**
	 * Returns the client extension entry with the primary key or throws a <code>NoSuchClientExtensionEntryException</code> if it could not be found.
	 *
	 * @param clientExtensionEntryId the primary key of the client extension entry
	 * @return the client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry findByPrimaryKey(long clientExtensionEntryId)
		throws NoSuchClientExtensionEntryException;

	/**
	 * Returns the client extension entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param clientExtensionEntryId the primary key of the client extension entry
	 * @return the client extension entry, or <code>null</code> if a client extension entry with the primary key could not be found
	 */
	public ClientExtensionEntry fetchByPrimaryKey(long clientExtensionEntryId);

	/**
	 * Returns all the client extension entries.
	 *
	 * @return the client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findAll();

	/**
	 * Returns a range of all the client extension entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the client extension entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of client extension entries
	 */
	public java.util.List<ClientExtensionEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClientExtensionEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the client extension entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of client extension entries.
	 *
	 * @return the number of client extension entries
	 */
	public int countAll();

}