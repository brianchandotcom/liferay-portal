/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectEntryFolderException;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object entry folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectEntryFolderUtil
 * @generated
 */
@ProviderType
public interface ObjectEntryFolderPersistence
	extends BasePersistence<ObjectEntryFolder> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectEntryFolderUtil} to access the object entry folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object entry folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByUuid(String uuid);

	/**
	 * Returns a range of all the object entry folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object entry folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object entry folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object entry folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the first object entry folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns the last object entry folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the last object entry folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns the object entry folders before and after the current object entry folder in the ordered set where uuid = &#63;.
	 *
	 * @param objectEntryFolderId the primary key of the current object entry folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object entry folder
	 * @throws NoSuchObjectEntryFolderException if a object entry folder with the primary key could not be found
	 */
	public ObjectEntryFolder[] findByUuid_PrevAndNext(
			long objectEntryFolderId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Removes all the object entry folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object entry folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object entry folders
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the object entry folder where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchObjectEntryFolderException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the object entry folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the object entry folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the object entry folder where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the object entry folder that was removed
	 */
	public ObjectEntryFolder removeByUUID_G(String uuid, long groupId)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the number of object entry folders where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching object entry folders
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the object entry folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object entry folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object entry folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object entry folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object entry folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the first object entry folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns the last object entry folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the last object entry folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns the object entry folders before and after the current object entry folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectEntryFolderId the primary key of the current object entry folder
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object entry folder
	 * @throws NoSuchObjectEntryFolderException if a object entry folder with the primary key could not be found
	 */
	public ObjectEntryFolder[] findByUuid_C_PrevAndNext(
			long objectEntryFolderId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Removes all the object entry folders where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object entry folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object entry folders
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the object entry folder where externalReferenceCode = &#63; and groupId = &#63; and companyId = &#63; or throws a <code>NoSuchObjectEntryFolderException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByERC_G_C(
			String externalReferenceCode, long groupId, long companyId)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the object entry folder where externalReferenceCode = &#63; and groupId = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByERC_G_C(
		String externalReferenceCode, long groupId, long companyId);

	/**
	 * Returns the object entry folder where externalReferenceCode = &#63; and groupId = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByERC_G_C(
		String externalReferenceCode, long groupId, long companyId,
		boolean useFinderCache);

	/**
	 * Removes the object entry folder where externalReferenceCode = &#63; and groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the object entry folder that was removed
	 */
	public ObjectEntryFolder removeByERC_G_C(
			String externalReferenceCode, long groupId, long companyId)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the number of object entry folders where externalReferenceCode = &#63; and groupId = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the number of matching object entry folders
	 */
	public int countByERC_G_C(
		String externalReferenceCode, long groupId, long companyId);

	/**
	 * Returns all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId);

	/**
	 * Returns a range of all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId, int start,
		int end);

	/**
	 * Returns an ordered range of all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByG_C_P_First(
			long groupId, long companyId, long parentObjectEntryFolderId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the first object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByG_C_P_First(
		long groupId, long companyId, long parentObjectEntryFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns the last object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByG_C_P_Last(
			long groupId, long companyId, long parentObjectEntryFolderId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the last object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByG_C_P_Last(
		long groupId, long companyId, long parentObjectEntryFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns the object entry folders before and after the current object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param objectEntryFolderId the primary key of the current object entry folder
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object entry folder
	 * @throws NoSuchObjectEntryFolderException if a object entry folder with the primary key could not be found
	 */
	public ObjectEntryFolder[] findByG_C_P_PrevAndNext(
			long objectEntryFolderId, long groupId, long companyId,
			long parentObjectEntryFolderId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns all the object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the matching object entry folders that the user has permission to view
	 */
	public java.util.List<ObjectEntryFolder> filterFindByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId);

	/**
	 * Returns a range of all the object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders that the user has permission to view
	 */
	public java.util.List<ObjectEntryFolder> filterFindByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId, int start,
		int end);

	/**
	 * Returns an ordered range of all the object entry folders that the user has permissions to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders that the user has permission to view
	 */
	public java.util.List<ObjectEntryFolder> filterFindByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns the object entry folders before and after the current object entry folder in the ordered set of object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param objectEntryFolderId the primary key of the current object entry folder
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object entry folder
	 * @throws NoSuchObjectEntryFolderException if a object entry folder with the primary key could not be found
	 */
	public ObjectEntryFolder[] filterFindByG_C_P_PrevAndNext(
			long objectEntryFolderId, long groupId, long companyId,
			long parentObjectEntryFolderId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Removes all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 */
	public void removeByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId);

	/**
	 * Returns the number of object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the number of matching object entry folders
	 */
	public int countByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId);

	/**
	 * Returns the number of object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the number of matching object entry folders that the user has permission to view
	 */
	public int filterCountByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId);

	/**
	 * Returns all the object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByG_C_LikeT(
		long groupId, long companyId, String treePath);

	/**
	 * Returns a range of all the object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByG_C_LikeT(
		long groupId, long companyId, String treePath, int start, int end);

	/**
	 * Returns an ordered range of all the object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByG_C_LikeT(
		long groupId, long companyId, String treePath, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findByG_C_LikeT(
		long groupId, long companyId, String treePath, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByG_C_LikeT_First(
			long groupId, long companyId, String treePath,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the first object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByG_C_LikeT_First(
		long groupId, long companyId, String treePath,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns the last object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByG_C_LikeT_Last(
			long groupId, long companyId, String treePath,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the last object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByG_C_LikeT_Last(
		long groupId, long companyId, String treePath,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns the object entry folders before and after the current object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param objectEntryFolderId the primary key of the current object entry folder
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object entry folder
	 * @throws NoSuchObjectEntryFolderException if a object entry folder with the primary key could not be found
	 */
	public ObjectEntryFolder[] findByG_C_LikeT_PrevAndNext(
			long objectEntryFolderId, long groupId, long companyId,
			String treePath,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns all the object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the matching object entry folders that the user has permission to view
	 */
	public java.util.List<ObjectEntryFolder> filterFindByG_C_LikeT(
		long groupId, long companyId, String treePath);

	/**
	 * Returns a range of all the object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders that the user has permission to view
	 */
	public java.util.List<ObjectEntryFolder> filterFindByG_C_LikeT(
		long groupId, long companyId, String treePath, int start, int end);

	/**
	 * Returns an ordered range of all the object entry folders that the user has permissions to view where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders that the user has permission to view
	 */
	public java.util.List<ObjectEntryFolder> filterFindByG_C_LikeT(
		long groupId, long companyId, String treePath, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns the object entry folders before and after the current object entry folder in the ordered set of object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param objectEntryFolderId the primary key of the current object entry folder
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object entry folder
	 * @throws NoSuchObjectEntryFolderException if a object entry folder with the primary key could not be found
	 */
	public ObjectEntryFolder[] filterFindByG_C_LikeT_PrevAndNext(
			long objectEntryFolderId, long groupId, long companyId,
			String treePath,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
				orderByComparator)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Removes all the object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 */
	public void removeByG_C_LikeT(
		long groupId, long companyId, String treePath);

	/**
	 * Returns the number of object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the number of matching object entry folders
	 */
	public int countByG_C_LikeT(long groupId, long companyId, String treePath);

	/**
	 * Returns the number of object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the number of matching object entry folders that the user has permission to view
	 */
	public int filterCountByG_C_LikeT(
		long groupId, long companyId, String treePath);

	/**
	 * Returns the object entry folder where groupId = &#63; and companyId = &#63; and name = &#63; and parentObjectEntryFolderId = &#63; or throws a <code>NoSuchObjectEntryFolderException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param name the name
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder findByG_C_N_P(
			long groupId, long companyId, String name,
			long parentObjectEntryFolderId)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the object entry folder where groupId = &#63; and companyId = &#63; and name = &#63; and parentObjectEntryFolderId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param name the name
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByG_C_N_P(
		long groupId, long companyId, String name,
		long parentObjectEntryFolderId);

	/**
	 * Returns the object entry folder where groupId = &#63; and companyId = &#63; and name = &#63; and parentObjectEntryFolderId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param name the name
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	public ObjectEntryFolder fetchByG_C_N_P(
		long groupId, long companyId, String name,
		long parentObjectEntryFolderId, boolean useFinderCache);

	/**
	 * Removes the object entry folder where groupId = &#63; and companyId = &#63; and name = &#63; and parentObjectEntryFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param name the name
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the object entry folder that was removed
	 */
	public ObjectEntryFolder removeByG_C_N_P(
			long groupId, long companyId, String name,
			long parentObjectEntryFolderId)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the number of object entry folders where groupId = &#63; and companyId = &#63; and name = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param name the name
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the number of matching object entry folders
	 */
	public int countByG_C_N_P(
		long groupId, long companyId, String name,
		long parentObjectEntryFolderId);

	/**
	 * Caches the object entry folder in the entity cache if it is enabled.
	 *
	 * @param objectEntryFolder the object entry folder
	 */
	public void cacheResult(ObjectEntryFolder objectEntryFolder);

	/**
	 * Caches the object entry folders in the entity cache if it is enabled.
	 *
	 * @param objectEntryFolders the object entry folders
	 */
	public void cacheResult(
		java.util.List<ObjectEntryFolder> objectEntryFolders);

	/**
	 * Creates a new object entry folder with the primary key. Does not add the object entry folder to the database.
	 *
	 * @param objectEntryFolderId the primary key for the new object entry folder
	 * @return the new object entry folder
	 */
	public ObjectEntryFolder create(long objectEntryFolderId);

	/**
	 * Removes the object entry folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectEntryFolderId the primary key of the object entry folder
	 * @return the object entry folder that was removed
	 * @throws NoSuchObjectEntryFolderException if a object entry folder with the primary key could not be found
	 */
	public ObjectEntryFolder remove(long objectEntryFolderId)
		throws NoSuchObjectEntryFolderException;

	public ObjectEntryFolder updateImpl(ObjectEntryFolder objectEntryFolder);

	/**
	 * Returns the object entry folder with the primary key or throws a <code>NoSuchObjectEntryFolderException</code> if it could not be found.
	 *
	 * @param objectEntryFolderId the primary key of the object entry folder
	 * @return the object entry folder
	 * @throws NoSuchObjectEntryFolderException if a object entry folder with the primary key could not be found
	 */
	public ObjectEntryFolder findByPrimaryKey(long objectEntryFolderId)
		throws NoSuchObjectEntryFolderException;

	/**
	 * Returns the object entry folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectEntryFolderId the primary key of the object entry folder
	 * @return the object entry folder, or <code>null</code> if a object entry folder with the primary key could not be found
	 */
	public ObjectEntryFolder fetchByPrimaryKey(long objectEntryFolderId);

	/**
	 * Returns all the object entry folders.
	 *
	 * @return the object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findAll();

	/**
	 * Returns a range of all the object entry folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object entry folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object entry folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object entry folders
	 */
	public java.util.List<ObjectEntryFolder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectEntryFolder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object entry folders from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object entry folders.
	 *
	 * @return the number of object entry folders
	 */
	public int countAll();

}