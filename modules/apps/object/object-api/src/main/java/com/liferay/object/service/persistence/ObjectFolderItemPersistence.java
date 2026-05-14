/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectFolderItemException;
import com.liferay.object.model.ObjectFolderItem;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object folder item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFolderItemUtil
 * @generated
 */
@ProviderType
public interface ObjectFolderItemPersistence
	extends BasePersistence<ObjectFolderItem> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectFolderItemUtil} to access the object folder item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	public java.util.List<ObjectFolderItem> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public ObjectFolderItem findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
				orderByComparator)
		throws NoSuchObjectFolderItemException;

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public ObjectFolderItem fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator);

	/**
	 * Removes all the object folder items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object folder items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object folder items
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	public java.util.List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public ObjectFolderItem findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
				orderByComparator)
		throws NoSuchObjectFolderItemException;

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public ObjectFolderItem fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator);

	/**
	 * Removes all the object folder items where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object folder items
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns an ordered range of all the object folder items where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	public java.util.List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public ObjectFolderItem findByObjectDefinitionId_First(
			long objectDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
				orderByComparator)
		throws NoSuchObjectFolderItemException;

	/**
	 * Returns the first object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public ObjectFolderItem fetchByObjectDefinitionId_First(
		long objectDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator);

	/**
	 * Removes all the object folder items where objectDefinitionId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 */
	public void removeByObjectDefinitionId(long objectDefinitionId);

	/**
	 * Returns the number of object folder items where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the number of matching object folder items
	 */
	public int countByObjectDefinitionId(long objectDefinitionId);

	/**
	 * Returns an ordered range of all the object folder items where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	public java.util.List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public ObjectFolderItem findByObjectFolderId_First(
			long objectFolderId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
				orderByComparator)
		throws NoSuchObjectFolderItemException;

	/**
	 * Returns the first object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public ObjectFolderItem fetchByObjectFolderId_First(
		long objectFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator);

	/**
	 * Removes all the object folder items where objectFolderId = &#63; from the database.
	 *
	 * @param objectFolderId the object folder ID
	 */
	public void removeByObjectFolderId(long objectFolderId);

	/**
	 * Returns the number of object folder items where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @return the number of matching object folder items
	 */
	public int countByObjectFolderId(long objectFolderId);

	/**
	 * Returns the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; or throws a <code>NoSuchObjectFolderItemException</code> if it could not be found.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	public ObjectFolderItem findByODI_OFI(
			long objectDefinitionId, long objectFolderId)
		throws NoSuchObjectFolderItemException;

	/**
	 * Returns the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public ObjectFolderItem fetchByODI_OFI(
		long objectDefinitionId, long objectFolderId, boolean useFinderCache);

	/**
	 * Removes the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the object folder item that was removed
	 */
	public ObjectFolderItem removeByODI_OFI(
			long objectDefinitionId, long objectFolderId)
		throws NoSuchObjectFolderItemException;

	/**
	 * Returns the number of object folder items where objectDefinitionId = &#63; and objectFolderId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the number of matching object folder items
	 */
	public int countByODI_OFI(long objectDefinitionId, long objectFolderId);

	/**
	 * Creates a new object folder item with the primary key. Does not add the object folder item to the database.
	 *
	 * @param objectFolderItemId the primary key for the new object folder item
	 * @return the new object folder item
	 */
	public ObjectFolderItem create(long objectFolderItemId);

	/**
	 * Removes the object folder item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFolderItemId the primary key of the object folder item
	 * @return the object folder item that was removed
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	public ObjectFolderItem remove(long objectFolderItemId)
		throws NoSuchObjectFolderItemException;

	public ObjectFolderItem updateImpl(ObjectFolderItem objectFolderItem);

	/**
	 * Returns the object folder item with the primary key or throws a <code>NoSuchObjectFolderItemException</code> if it could not be found.
	 *
	 * @param objectFolderItemId the primary key of the object folder item
	 * @return the object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	public ObjectFolderItem findByPrimaryKey(long objectFolderItemId)
		throws NoSuchObjectFolderItemException;

	/**
	 * Returns the object folder item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFolderItemId the primary key of the object folder item
	 * @return the object folder item, or <code>null</code> if a object folder item with the primary key could not be found
	 */
	public ObjectFolderItem fetchByPrimaryKey(long objectFolderItemId);

	/**
	 * Returns the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	public default ObjectFolderItem fetchByODI_OFI(
		long objectDefinitionId, long objectFolderId) {

		return fetchByODI_OFI(objectDefinitionId, objectFolderId, true);
	}

	/**
	 * Returns all the object folder items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByUuid(String uuid) {
		return findByUuid(
			uuid, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object folder items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the object folder items where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId) {

		return findByObjectDefinitionId(
			objectDefinitionId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object folder items where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end) {

		return findByObjectDefinitionId(
			objectDefinitionId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object folder items where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator) {

		return findByObjectDefinitionId(
			objectDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the object folder items where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @return the matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId) {

		return findByObjectFolderId(
			objectFolderId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object folder items where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId, int start, int end) {

		return findByObjectFolderId(objectFolderId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object folder items where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	public default java.util.List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFolderItem>
			orderByComparator) {

		return findByObjectFolderId(
			objectFolderId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:766528653