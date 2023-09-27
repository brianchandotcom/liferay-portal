/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.service.persistence;

import com.liferay.client.extension.exception.NoSuchClientExtensionEntryRelException;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the client extension entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryRelUtil
 * @generated
 */
@ProviderType
public interface ClientExtensionEntryRelPersistence
	extends BasePersistence<ClientExtensionEntryRel>,
			CTPersistence<ClientExtensionEntryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ClientExtensionEntryRelUtil} to access the client extension entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the client extension entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByUuid(String uuid);

	/**
	 * Returns a range of all the client extension entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public ClientExtensionEntryRel[] findByUuid_PrevAndNext(
			long clientExtensionEntryRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Removes all the client extension entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of client extension entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching client extension entry rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the client extension entry rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchClientExtensionEntryRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByUUID_G(String uuid, long groupId)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the client extension entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the client extension entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the client extension entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the client extension entry rel that was removed
	 */
	public ClientExtensionEntryRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the number of client extension entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching client extension entry rels
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public ClientExtensionEntryRel[] findByUuid_C_PrevAndNext(
			long clientExtensionEntryRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Removes all the client extension entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching client extension entry rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @return the matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode);

	/**
	 * Returns a range of all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByC_CETERC_First(
			long companyId, String cetExternalReferenceCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the first client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByC_CETERC_First(
		long companyId, String cetExternalReferenceCode,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns the last client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByC_CETERC_Last(
			long companyId, String cetExternalReferenceCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the last client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByC_CETERC_Last(
		long companyId, String cetExternalReferenceCode,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public ClientExtensionEntryRel[] findByC_CETERC_PrevAndNext(
			long clientExtensionEntryRelId, long companyId,
			String cetExternalReferenceCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Removes all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 */
	public void removeByC_CETERC(
		long companyId, String cetExternalReferenceCode);

	/**
	 * Returns the number of client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @return the number of matching client extension entry rels
	 */
	public int countByC_CETERC(long companyId, String cetExternalReferenceCode);

	/**
	 * Returns all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public ClientExtensionEntryRel[] findByC_C_PrevAndNext(
			long clientExtensionEntryRelId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Removes all the client extension entry rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching client extension entry rels
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type);

	/**
	 * Returns a range of all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type, int start, int end);

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByC_C_T_First(
			long classNameId, long classPK, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByC_C_T_First(
		long classNameId, long classPK, String type,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByC_C_T_Last(
			long classNameId, long classPK, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByC_C_T_Last(
		long classNameId, long classPK, String type,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public ClientExtensionEntryRel[] findByC_C_T_PrevAndNext(
			long clientExtensionEntryRelId, long classNameId, long classPK,
			String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Removes all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	public void removeByC_C_T(long classNameId, long classPK, String type);

	/**
	 * Returns the number of client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching client extension entry rels
	 */
	public int countByC_C_T(long classNameId, long classPK, String type);

	/**
	 * Returns the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchClientExtensionEntryRelException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel findByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByERC_G(
		String externalReferenceCode, long groupId);

	/**
	 * Returns the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public ClientExtensionEntryRel fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache);

	/**
	 * Removes the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the client extension entry rel that was removed
	 */
	public ClientExtensionEntryRel removeByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the number of client extension entry rels where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching client extension entry rels
	 */
	public int countByERC_G(String externalReferenceCode, long groupId);

	/**
	 * Caches the client extension entry rel in the entity cache if it is enabled.
	 *
	 * @param clientExtensionEntryRel the client extension entry rel
	 */
	public void cacheResult(ClientExtensionEntryRel clientExtensionEntryRel);

	/**
	 * Caches the client extension entry rels in the entity cache if it is enabled.
	 *
	 * @param clientExtensionEntryRels the client extension entry rels
	 */
	public void cacheResult(
		java.util.List<ClientExtensionEntryRel> clientExtensionEntryRels);

	/**
	 * Creates a new client extension entry rel with the primary key. Does not add the client extension entry rel to the database.
	 *
	 * @param clientExtensionEntryRelId the primary key for the new client extension entry rel
	 * @return the new client extension entry rel
	 */
	public ClientExtensionEntryRel create(long clientExtensionEntryRelId);

	/**
	 * Removes the client extension entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel that was removed
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public ClientExtensionEntryRel remove(long clientExtensionEntryRelId)
		throws NoSuchClientExtensionEntryRelException;

	public ClientExtensionEntryRel updateImpl(
		ClientExtensionEntryRel clientExtensionEntryRel);

	/**
	 * Returns the client extension entry rel with the primary key or throws a <code>NoSuchClientExtensionEntryRelException</code> if it could not be found.
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public ClientExtensionEntryRel findByPrimaryKey(
			long clientExtensionEntryRelId)
		throws NoSuchClientExtensionEntryRelException;

	/**
	 * Returns the client extension entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel, or <code>null</code> if a client extension entry rel with the primary key could not be found
	 */
	public ClientExtensionEntryRel fetchByPrimaryKey(
		long clientExtensionEntryRelId);

	/**
	 * Returns all the client extension entry rels.
	 *
	 * @return the client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findAll();

	/**
	 * Returns a range of all the client extension entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the client extension entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the client extension entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of client extension entry rels
	 */
	public java.util.List<ClientExtensionEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the client extension entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of client extension entry rels.
	 *
	 * @return the number of client extension entry rels
	 */
	public int countAll();

}