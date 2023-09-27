/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectFolderException;
import com.liferay.object.model.ObjectFolder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFolderUtil
 * @generated
 */
@ProviderType
public interface ObjectFolderPersistence extends BasePersistence<ObjectFolder> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectFolderUtil} to access the object folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object folders
	 */
	public java.util.List<ObjectFolder> findByUuid(String uuid);

	/**
	 * Returns a range of all the object folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders
	 */
	public java.util.List<ObjectFolder> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders
	 */
	public java.util.List<ObjectFolder> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folders
	 */
	public java.util.List<ObjectFolder> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public ObjectFolder findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the first object folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public ObjectFolder fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns the last object folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public ObjectFolder findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the last object folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public ObjectFolder fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns the object folders before and after the current object folder in the ordered set where uuid = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public ObjectFolder[] findByUuid_PrevAndNext(
			long objectFolderId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Returns all the object folders that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object folders that the user has permission to view
	 */
	public java.util.List<ObjectFolder> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the object folders that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders that the user has permission to view
	 */
	public java.util.List<ObjectFolder> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object folders that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders that the user has permission to view
	 */
	public java.util.List<ObjectFolder> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns the object folders before and after the current object folder in the ordered set of object folders that the user has permission to view where uuid = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public ObjectFolder[] filterFindByUuid_PrevAndNext(
			long objectFolderId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Removes all the object folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object folders
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of object folders that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object folders that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the object folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object folders
	 */
	public java.util.List<ObjectFolder> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders
	 */
	public java.util.List<ObjectFolder> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders
	 */
	public java.util.List<ObjectFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folders
	 */
	public java.util.List<ObjectFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public ObjectFolder findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the first object folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public ObjectFolder fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns the last object folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public ObjectFolder findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the last object folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public ObjectFolder fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns the object folders before and after the current object folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public ObjectFolder[] findByUuid_C_PrevAndNext(
			long objectFolderId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Returns all the object folders that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object folders that the user has permission to view
	 */
	public java.util.List<ObjectFolder> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object folders that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders that the user has permission to view
	 */
	public java.util.List<ObjectFolder> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object folders that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders that the user has permission to view
	 */
	public java.util.List<ObjectFolder> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns the object folders before and after the current object folder in the ordered set of object folders that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public ObjectFolder[] filterFindByUuid_C_PrevAndNext(
			long objectFolderId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Removes all the object folders where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object folders
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object folders that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object folders that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching object folders
	 */
	public java.util.List<ObjectFolder> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the object folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders
	 */
	public java.util.List<ObjectFolder> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders
	 */
	public java.util.List<ObjectFolder> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folders
	 */
	public java.util.List<ObjectFolder> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public ObjectFolder findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the first object folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public ObjectFolder fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns the last object folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public ObjectFolder findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the last object folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public ObjectFolder fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns the object folders before and after the current object folder in the ordered set where companyId = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public ObjectFolder[] findByCompanyId_PrevAndNext(
			long objectFolderId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Returns all the object folders that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching object folders that the user has permission to view
	 */
	public java.util.List<ObjectFolder> filterFindByCompanyId(long companyId);

	/**
	 * Returns a range of all the object folders that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of matching object folders that the user has permission to view
	 */
	public java.util.List<ObjectFolder> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object folders that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folders that the user has permission to view
	 */
	public java.util.List<ObjectFolder> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns the object folders before and after the current object folder in the ordered set of object folders that the user has permission to view where companyId = &#63;.
	 *
	 * @param objectFolderId the primary key of the current object folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public ObjectFolder[] filterFindByCompanyId_PrevAndNext(
			long objectFolderId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
				orderByComparator)
		throws NoSuchObjectFolderException;

	/**
	 * Removes all the object folders where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of object folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching object folders
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of object folders that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching object folders that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns the object folder where companyId = &#63; and name = &#63; or throws a <code>NoSuchObjectFolderException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public ObjectFolder findByC_N(long companyId, String name)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the object folder where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public ObjectFolder fetchByC_N(long companyId, String name);

	/**
	 * Returns the object folder where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public ObjectFolder fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the object folder where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the object folder that was removed
	 */
	public ObjectFolder removeByC_N(long companyId, String name)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the number of object folders where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching object folders
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Returns the object folder where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchObjectFolderException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching object folder
	 * @throws NoSuchObjectFolderException if a matching object folder could not be found
	 */
	public ObjectFolder findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the object folder where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public ObjectFolder fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the object folder where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object folder, or <code>null</code> if a matching object folder could not be found
	 */
	public ObjectFolder fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the object folder where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the object folder that was removed
	 */
	public ObjectFolder removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the number of object folders where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching object folders
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the object folder in the entity cache if it is enabled.
	 *
	 * @param objectFolder the object folder
	 */
	public void cacheResult(ObjectFolder objectFolder);

	/**
	 * Caches the object folders in the entity cache if it is enabled.
	 *
	 * @param objectFolders the object folders
	 */
	public void cacheResult(java.util.List<ObjectFolder> objectFolders);

	/**
	 * Creates a new object folder with the primary key. Does not add the object folder to the database.
	 *
	 * @param objectFolderId the primary key for the new object folder
	 * @return the new object folder
	 */
	public ObjectFolder create(long objectFolderId);

	/**
	 * Removes the object folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFolderId the primary key of the object folder
	 * @return the object folder that was removed
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public ObjectFolder remove(long objectFolderId)
		throws NoSuchObjectFolderException;

	public ObjectFolder updateImpl(ObjectFolder objectFolder);

	/**
	 * Returns the object folder with the primary key or throws a <code>NoSuchObjectFolderException</code> if it could not be found.
	 *
	 * @param objectFolderId the primary key of the object folder
	 * @return the object folder
	 * @throws NoSuchObjectFolderException if a object folder with the primary key could not be found
	 */
	public ObjectFolder findByPrimaryKey(long objectFolderId)
		throws NoSuchObjectFolderException;

	/**
	 * Returns the object folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFolderId the primary key of the object folder
	 * @return the object folder, or <code>null</code> if a object folder with the primary key could not be found
	 */
	public ObjectFolder fetchByPrimaryKey(long objectFolderId);

	/**
	 * Returns all the object folders.
	 *
	 * @return the object folders
	 */
	public java.util.List<ObjectFolder> findAll();

	/**
	 * Returns a range of all the object folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @return the range of object folders
	 */
	public java.util.List<ObjectFolder> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object folders
	 */
	public java.util.List<ObjectFolder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folders
	 * @param end the upper bound of the range of object folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object folders
	 */
	public java.util.List<ObjectFolder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolder>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object folders from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object folders.
	 *
	 * @return the number of object folders
	 */
	public int countAll();

}