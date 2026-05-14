/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectViewColumnException;
import com.liferay.object.model.ObjectViewColumn;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object view column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewColumnUtil
 * @generated
 */
@ProviderType
public interface ObjectViewColumnPersistence
	extends BasePersistence<ObjectViewColumn> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectViewColumnUtil} to access the object view column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public ObjectViewColumn findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public ObjectViewColumn fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Removes all the object view columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object view columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view columns
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public ObjectViewColumn findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public ObjectViewColumn fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Removes all the object view columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view columns
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns an ordered range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public ObjectViewColumn findByObjectViewId_First(
			long objectViewId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the first object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public ObjectViewColumn fetchByObjectViewId_First(
		long objectViewId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Removes all the object view columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	public void removeByObjectViewId(long objectViewId);

	/**
	 * Returns the number of object view columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view columns
	 */
	public int countByObjectViewId(long objectViewId);

	/**
	 * Returns an ordered range of all the object view columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	public java.util.List<ObjectViewColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public ObjectViewColumn findByOVI_OFN_First(
			long objectViewId, String objectFieldName,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
				orderByComparator)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the first object view column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public ObjectViewColumn fetchByOVI_OFN_First(
		long objectViewId, String objectFieldName,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator);

	/**
	 * Removes all the object view columns where objectViewId = &#63; and objectFieldName = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 */
	public void removeByOVI_OFN(long objectViewId, String objectFieldName);

	/**
	 * Returns the number of object view columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the number of matching object view columns
	 */
	public int countByOVI_OFN(long objectViewId, String objectFieldName);

	/**
	 * Creates a new object view column with the primary key. Does not add the object view column to the database.
	 *
	 * @param objectViewColumnId the primary key for the new object view column
	 * @return the new object view column
	 */
	public ObjectViewColumn create(long objectViewColumnId);

	/**
	 * Removes the object view column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column that was removed
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public ObjectViewColumn remove(long objectViewColumnId)
		throws NoSuchObjectViewColumnException;

	public ObjectViewColumn updateImpl(ObjectViewColumn objectViewColumn);

	/**
	 * Returns the object view column with the primary key or throws a <code>NoSuchObjectViewColumnException</code> if it could not be found.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public ObjectViewColumn findByPrimaryKey(long objectViewColumnId)
		throws NoSuchObjectViewColumnException;

	/**
	 * Returns the object view column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column, or <code>null</code> if a object view column with the primary key could not be found
	 */
	public ObjectViewColumn fetchByPrimaryKey(long objectViewColumnId);

	/**
	 * Returns all the object view columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByUuid(String uuid) {
		return findByUuid(
			uuid, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the object view columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByObjectViewId(
		long objectViewId) {

		return findByObjectViewId(
			objectViewId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end) {

		return findByObjectViewId(objectViewId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator) {

		return findByObjectViewId(
			objectViewId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the object view columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName) {

		return findByOVI_OFN(
			objectViewId, objectFieldName,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the object view columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end) {

		return findByOVI_OFN(
			objectViewId, objectFieldName, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the object view columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	public default java.util.List<ObjectViewColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewColumn>
			orderByComparator) {

		return findByOVI_OFN(
			objectViewId, objectFieldName, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-2036023040