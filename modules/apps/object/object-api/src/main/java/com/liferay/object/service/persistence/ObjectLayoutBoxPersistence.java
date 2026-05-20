/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectLayoutBoxException;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object layout box service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxUtil
 * @generated
 */
@ProviderType
public interface ObjectLayoutBoxPersistence
	extends BasePersistence<ObjectLayoutBox> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectLayoutBoxUtil} to access the object layout box persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public ObjectLayoutBox findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public ObjectLayoutBox fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Removes all the object layout boxes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object layout boxes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout boxes
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public ObjectLayoutBox findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public ObjectLayoutBox fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Removes all the object layout boxes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout boxes
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns an ordered range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	public java.util.List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public ObjectLayoutBox findByObjectLayoutTabId_First(
			long objectLayoutTabId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
				orderByComparator)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the first object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public ObjectLayoutBox fetchByObjectLayoutTabId_First(
		long objectLayoutTabId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator);

	/**
	 * Removes all the object layout boxes where objectLayoutTabId = &#63; from the database.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 */
	public void removeByObjectLayoutTabId(long objectLayoutTabId);

	/**
	 * Returns the number of object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @return the number of matching object layout boxes
	 */
	public int countByObjectLayoutTabId(long objectLayoutTabId);

	/**
	 * Creates a new object layout box with the primary key. Does not add the object layout box to the database.
	 *
	 * @param objectLayoutBoxId the primary key for the new object layout box
	 * @return the new object layout box
	 */
	public ObjectLayoutBox create(long objectLayoutBoxId);

	/**
	 * Removes the object layout box with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box that was removed
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public ObjectLayoutBox remove(long objectLayoutBoxId)
		throws NoSuchObjectLayoutBoxException;

	public ObjectLayoutBox updateImpl(ObjectLayoutBox objectLayoutBox);

	/**
	 * Returns the object layout box with the primary key or throws a <code>NoSuchObjectLayoutBoxException</code> if it could not be found.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public ObjectLayoutBox findByPrimaryKey(long objectLayoutBoxId)
		throws NoSuchObjectLayoutBoxException;

	/**
	 * Returns the object layout box with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box, or <code>null</code> if a object layout box with the primary key could not be found
	 */
	public ObjectLayoutBox fetchByPrimaryKey(long objectLayoutBoxId);

	/**
	 * Returns all the object layout boxes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout boxes
	 */
	public default java.util.List<ObjectLayoutBox> findByUuid(String uuid) {
		return findByUuid(
			uuid, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	public default java.util.List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	public default java.util.List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout boxes
	 */
	public default java.util.List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	public default java.util.List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	public default java.util.List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @return the matching object layout boxes
	 */
	public default java.util.List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId) {

		return findByObjectLayoutTabId(
			objectLayoutTabId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	public default java.util.List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end) {

		return findByObjectLayoutTabId(
			objectLayoutTabId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	public default java.util.List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutBox>
			orderByComparator) {

		return findByObjectLayoutTabId(
			objectLayoutTabId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:969296505