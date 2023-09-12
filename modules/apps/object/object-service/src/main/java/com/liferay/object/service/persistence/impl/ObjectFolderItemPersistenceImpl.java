/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence.impl;

import com.liferay.object.exception.NoSuchObjectFolderItemException;
import com.liferay.object.model.ObjectFolderItem;
import com.liferay.object.model.ObjectFolderItemTable;
import com.liferay.object.model.impl.ObjectFolderItemImpl;
import com.liferay.object.model.impl.ObjectFolderItemModelImpl;
import com.liferay.object.service.persistence.ObjectFolderItemPersistence;
import com.liferay.object.service.persistence.ObjectFolderItemUtil;
import com.liferay.object.service.persistence.impl.constants.ObjectPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the object folder item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = ObjectFolderItemPersistence.class)
public class ObjectFolderItemPersistenceImpl
	extends BasePersistenceImpl<ObjectFolderItem>
	implements ObjectFolderItemPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectFolderItemUtil</code> to access the object folder item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectFolderItemImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the object folder items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object folder items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<ObjectFolderItem> list = null;

		if (useFinderCache) {
			list = (List<ObjectFolderItem>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectFolderItem objectFolderItem : list) {
					if (!uuid.equals(objectFolderItem.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_OBJECTFOLDERITEM_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectFolderItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<ObjectFolderItem>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem findByUuid_First(
			String uuid, OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectFolderItem != null) {
			return objectFolderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectFolderItemException(sb.toString());
	}

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem fetchByUuid_First(
		String uuid, OrderByComparator<ObjectFolderItem> orderByComparator) {

		List<ObjectFolderItem> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem findByUuid_Last(
			String uuid, OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectFolderItem != null) {
			return objectFolderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectFolderItemException(sb.toString());
	}

	/**
	 * Returns the last object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectFolderItem> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectFolderItem> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object folder items before and after the current object folder item in the ordered set where uuid = &#63;.
	 *
	 * @param objectFolderItemId the primary key of the current object folder item
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	@Override
	public ObjectFolderItem[] findByUuid_PrevAndNext(
			long objectFolderItemId, String uuid,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		uuid = Objects.toString(uuid, "");

		ObjectFolderItem objectFolderItem = findByPrimaryKey(
			objectFolderItemId);

		Session session = null;

		try {
			session = openSession();

			ObjectFolderItem[] array = new ObjectFolderItemImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectFolderItem, uuid, orderByComparator, true);

			array[1] = objectFolderItem;

			array[2] = getByUuid_PrevAndNext(
				session, objectFolderItem, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectFolderItem getByUuid_PrevAndNext(
		Session session, ObjectFolderItem objectFolderItem, String uuid,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OBJECTFOLDERITEM_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ObjectFolderItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectFolderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectFolderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object folder items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectFolderItem objectFolderItem :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectFolderItem);
		}
	}

	/**
	 * Returns the number of object folder items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object folder items
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTFOLDERITEM_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"objectFolderItem.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectFolderItem.uuid IS NULL OR objectFolderItem.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
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
	@Override
	public List<ObjectFolderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<ObjectFolderItem> list = null;

		if (useFinderCache) {
			list = (List<ObjectFolderItem>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectFolderItem objectFolderItem : list) {
					if (!uuid.equals(objectFolderItem.getUuid()) ||
						(companyId != objectFolderItem.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_OBJECTFOLDERITEM_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectFolderItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<ObjectFolderItem>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectFolderItem != null) {
			return objectFolderItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectFolderItemException(sb.toString());
	}

	/**
	 * Returns the first object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		List<ObjectFolderItem> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectFolderItem != null) {
			return objectFolderItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectFolderItemException(sb.toString());
	}

	/**
	 * Returns the last object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectFolderItem> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object folder items before and after the current object folder item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFolderItemId the primary key of the current object folder item
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	@Override
	public ObjectFolderItem[] findByUuid_C_PrevAndNext(
			long objectFolderItemId, String uuid, long companyId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		uuid = Objects.toString(uuid, "");

		ObjectFolderItem objectFolderItem = findByPrimaryKey(
			objectFolderItemId);

		Session session = null;

		try {
			session = openSession();

			ObjectFolderItem[] array = new ObjectFolderItemImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectFolderItem, uuid, companyId, orderByComparator,
				true);

			array[1] = objectFolderItem;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectFolderItem, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectFolderItem getByUuid_C_PrevAndNext(
		Session session, ObjectFolderItem objectFolderItem, String uuid,
		long companyId, OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_OBJECTFOLDERITEM_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ObjectFolderItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectFolderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectFolderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object folder items where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectFolderItem objectFolderItem :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectFolderItem);
		}
	}

	/**
	 * Returns the number of object folder items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object folder items
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTFOLDERITEM_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"objectFolderItem.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectFolderItem.uuid IS NULL OR objectFolderItem.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectFolderItem.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectDefinitionId;
	private FinderPath _finderPathWithoutPaginationFindByObjectDefinitionId;
	private FinderPath _finderPathCountByObjectDefinitionId;

	/**
	 * Returns all the object folder items where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId) {

		return findByObjectDefinitionId(
			objectDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object folder items where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end) {

		return findByObjectDefinitionId(objectDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object folder items where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return findByObjectDefinitionId(
			objectDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object folder items where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByObjectDefinitionId;
				finderArgs = new Object[] {objectDefinitionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectDefinitionId;
			finderArgs = new Object[] {
				objectDefinitionId, start, end, orderByComparator
			};
		}

		List<ObjectFolderItem> list = null;

		if (useFinderCache) {
			list = (List<ObjectFolderItem>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectFolderItem objectFolderItem : list) {
					if (objectDefinitionId !=
							objectFolderItem.getObjectDefinitionId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_OBJECTFOLDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID_OBJECTDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectFolderItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				list = (List<ObjectFolderItem>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem findByObjectDefinitionId_First(
			long objectDefinitionId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = fetchByObjectDefinitionId_First(
			objectDefinitionId, orderByComparator);

		if (objectFolderItem != null) {
			return objectFolderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append("}");

		throw new NoSuchObjectFolderItemException(sb.toString());
	}

	/**
	 * Returns the first object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem fetchByObjectDefinitionId_First(
		long objectDefinitionId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		List<ObjectFolderItem> list = findByObjectDefinitionId(
			objectDefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem findByObjectDefinitionId_Last(
			long objectDefinitionId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = fetchByObjectDefinitionId_Last(
			objectDefinitionId, orderByComparator);

		if (objectFolderItem != null) {
			return objectFolderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append("}");

		throw new NoSuchObjectFolderItemException(sb.toString());
	}

	/**
	 * Returns the last object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem fetchByObjectDefinitionId_Last(
		long objectDefinitionId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		int count = countByObjectDefinitionId(objectDefinitionId);

		if (count == 0) {
			return null;
		}

		List<ObjectFolderItem> list = findByObjectDefinitionId(
			objectDefinitionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object folder items before and after the current object folder item in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectFolderItemId the primary key of the current object folder item
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	@Override
	public ObjectFolderItem[] findByObjectDefinitionId_PrevAndNext(
			long objectFolderItemId, long objectDefinitionId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = findByPrimaryKey(
			objectFolderItemId);

		Session session = null;

		try {
			session = openSession();

			ObjectFolderItem[] array = new ObjectFolderItemImpl[3];

			array[0] = getByObjectDefinitionId_PrevAndNext(
				session, objectFolderItem, objectDefinitionId,
				orderByComparator, true);

			array[1] = objectFolderItem;

			array[2] = getByObjectDefinitionId_PrevAndNext(
				session, objectFolderItem, objectDefinitionId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectFolderItem getByObjectDefinitionId_PrevAndNext(
		Session session, ObjectFolderItem objectFolderItem,
		long objectDefinitionId,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OBJECTFOLDERITEM_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID_OBJECTDEFINITIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ObjectFolderItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectDefinitionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectFolderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectFolderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object folder items where objectDefinitionId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 */
	@Override
	public void removeByObjectDefinitionId(long objectDefinitionId) {
		for (ObjectFolderItem objectFolderItem :
				findByObjectDefinitionId(
					objectDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectFolderItem);
		}
	}

	/**
	 * Returns the number of object folder items where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the number of matching object folder items
	 */
	@Override
	public int countByObjectDefinitionId(long objectDefinitionId) {
		FinderPath finderPath = _finderPathCountByObjectDefinitionId;

		Object[] finderArgs = new Object[] {objectDefinitionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTFOLDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID_OBJECTDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_OBJECTDEFINITIONID_OBJECTDEFINITIONID_2 =
			"objectFolderItem.objectDefinitionId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectFolderId;
	private FinderPath _finderPathWithoutPaginationFindByObjectFolderId;
	private FinderPath _finderPathCountByObjectFolderId;

	/**
	 * Returns all the object folder items where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @return the matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByObjectFolderId(long objectFolderId) {
		return findByObjectFolderId(
			objectFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object folder items where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId, int start, int end) {

		return findByObjectFolderId(objectFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object folder items where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return findByObjectFolderId(
			objectFolderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object folder items where objectFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param objectFolderId the object folder ID
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object folder items
	 */
	@Override
	public List<ObjectFolderItem> findByObjectFolderId(
		long objectFolderId, int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByObjectFolderId;
				finderArgs = new Object[] {objectFolderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectFolderId;
			finderArgs = new Object[] {
				objectFolderId, start, end, orderByComparator
			};
		}

		List<ObjectFolderItem> list = null;

		if (useFinderCache) {
			list = (List<ObjectFolderItem>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectFolderItem objectFolderItem : list) {
					if (objectFolderId !=
							objectFolderItem.getObjectFolderId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_OBJECTFOLDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTFOLDERID_OBJECTFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectFolderItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectFolderId);

				list = (List<ObjectFolderItem>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem findByObjectFolderId_First(
			long objectFolderId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = fetchByObjectFolderId_First(
			objectFolderId, orderByComparator);

		if (objectFolderItem != null) {
			return objectFolderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectFolderId=");
		sb.append(objectFolderId);

		sb.append("}");

		throw new NoSuchObjectFolderItemException(sb.toString());
	}

	/**
	 * Returns the first object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem fetchByObjectFolderId_First(
		long objectFolderId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		List<ObjectFolderItem> list = findByObjectFolderId(
			objectFolderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem findByObjectFolderId_Last(
			long objectFolderId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = fetchByObjectFolderId_Last(
			objectFolderId, orderByComparator);

		if (objectFolderItem != null) {
			return objectFolderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectFolderId=");
		sb.append(objectFolderId);

		sb.append("}");

		throw new NoSuchObjectFolderItemException(sb.toString());
	}

	/**
	 * Returns the last object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem fetchByObjectFolderId_Last(
		long objectFolderId,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		int count = countByObjectFolderId(objectFolderId);

		if (count == 0) {
			return null;
		}

		List<ObjectFolderItem> list = findByObjectFolderId(
			objectFolderId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object folder items before and after the current object folder item in the ordered set where objectFolderId = &#63;.
	 *
	 * @param objectFolderItemId the primary key of the current object folder item
	 * @param objectFolderId the object folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	@Override
	public ObjectFolderItem[] findByObjectFolderId_PrevAndNext(
			long objectFolderItemId, long objectFolderId,
			OrderByComparator<ObjectFolderItem> orderByComparator)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = findByPrimaryKey(
			objectFolderItemId);

		Session session = null;

		try {
			session = openSession();

			ObjectFolderItem[] array = new ObjectFolderItemImpl[3];

			array[0] = getByObjectFolderId_PrevAndNext(
				session, objectFolderItem, objectFolderId, orderByComparator,
				true);

			array[1] = objectFolderItem;

			array[2] = getByObjectFolderId_PrevAndNext(
				session, objectFolderItem, objectFolderId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectFolderItem getByObjectFolderId_PrevAndNext(
		Session session, ObjectFolderItem objectFolderItem, long objectFolderId,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OBJECTFOLDERITEM_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTFOLDERID_OBJECTFOLDERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(ObjectFolderItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectFolderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectFolderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object folder items where objectFolderId = &#63; from the database.
	 *
	 * @param objectFolderId the object folder ID
	 */
	@Override
	public void removeByObjectFolderId(long objectFolderId) {
		for (ObjectFolderItem objectFolderItem :
				findByObjectFolderId(
					objectFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectFolderItem);
		}
	}

	/**
	 * Returns the number of object folder items where objectFolderId = &#63;.
	 *
	 * @param objectFolderId the object folder ID
	 * @return the number of matching object folder items
	 */
	@Override
	public int countByObjectFolderId(long objectFolderId) {
		FinderPath finderPath = _finderPathCountByObjectFolderId;

		Object[] finderArgs = new Object[] {objectFolderId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTFOLDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTFOLDERID_OBJECTFOLDERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectFolderId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_OBJECTFOLDERID_OBJECTFOLDERID_2 =
		"objectFolderItem.objectFolderId = ?";

	private FinderPath _finderPathFetchByODI_OFI;
	private FinderPath _finderPathCountByODI_OFI;

	/**
	 * Returns the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; or throws a <code>NoSuchObjectFolderItemException</code> if it could not be found.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the matching object folder item
	 * @throws NoSuchObjectFolderItemException if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem findByODI_OFI(
			long objectDefinitionId, long objectFolderId)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = fetchByODI_OFI(
			objectDefinitionId, objectFolderId);

		if (objectFolderItem == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("objectDefinitionId=");
			sb.append(objectDefinitionId);

			sb.append(", objectFolderId=");
			sb.append(objectFolderId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchObjectFolderItemException(sb.toString());
		}

		return objectFolderItem;
	}

	/**
	 * Returns the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem fetchByODI_OFI(
		long objectDefinitionId, long objectFolderId) {

		return fetchByODI_OFI(objectDefinitionId, objectFolderId, true);
	}

	/**
	 * Returns the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object folder item, or <code>null</code> if a matching object folder item could not be found
	 */
	@Override
	public ObjectFolderItem fetchByODI_OFI(
		long objectDefinitionId, long objectFolderId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {objectDefinitionId, objectFolderId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByODI_OFI, finderArgs, this);
		}

		if (result instanceof ObjectFolderItem) {
			ObjectFolderItem objectFolderItem = (ObjectFolderItem)result;

			if ((objectDefinitionId !=
					objectFolderItem.getObjectDefinitionId()) ||
				(objectFolderId != objectFolderItem.getObjectFolderId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_OBJECTFOLDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_ODI_OFI_OBJECTDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_ODI_OFI_OBJECTFOLDERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				queryPos.add(objectFolderId);

				List<ObjectFolderItem> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByODI_OFI, finderArgs, list);
					}
				}
				else {
					ObjectFolderItem objectFolderItem = list.get(0);

					result = objectFolderItem;

					cacheResult(objectFolderItem);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ObjectFolderItem)result;
		}
	}

	/**
	 * Removes the object folder item where objectDefinitionId = &#63; and objectFolderId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the object folder item that was removed
	 */
	@Override
	public ObjectFolderItem removeByODI_OFI(
			long objectDefinitionId, long objectFolderId)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = findByODI_OFI(
			objectDefinitionId, objectFolderId);

		return remove(objectFolderItem);
	}

	/**
	 * Returns the number of object folder items where objectDefinitionId = &#63; and objectFolderId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param objectFolderId the object folder ID
	 * @return the number of matching object folder items
	 */
	@Override
	public int countByODI_OFI(long objectDefinitionId, long objectFolderId) {
		FinderPath finderPath = _finderPathCountByODI_OFI;

		Object[] finderArgs = new Object[] {objectDefinitionId, objectFolderId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTFOLDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_ODI_OFI_OBJECTDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_ODI_OFI_OBJECTFOLDERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				queryPos.add(objectFolderId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ODI_OFI_OBJECTDEFINITIONID_2 =
		"objectFolderItem.objectDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_ODI_OFI_OBJECTFOLDERID_2 =
		"objectFolderItem.objectFolderId = ?";

	public ObjectFolderItemPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectFolderItem.class);

		setModelImplClass(ObjectFolderItemImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectFolderItemTable.INSTANCE);
	}

	/**
	 * Caches the object folder item in the entity cache if it is enabled.
	 *
	 * @param objectFolderItem the object folder item
	 */
	@Override
	public void cacheResult(ObjectFolderItem objectFolderItem) {
		entityCache.putResult(
			ObjectFolderItemImpl.class, objectFolderItem.getPrimaryKey(),
			objectFolderItem);

		finderCache.putResult(
			_finderPathFetchByODI_OFI,
			new Object[] {
				objectFolderItem.getObjectDefinitionId(),
				objectFolderItem.getObjectFolderId()
			},
			objectFolderItem);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object folder items in the entity cache if it is enabled.
	 *
	 * @param objectFolderItems the object folder items
	 */
	@Override
	public void cacheResult(List<ObjectFolderItem> objectFolderItems) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectFolderItems.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectFolderItem objectFolderItem : objectFolderItems) {
			if (entityCache.getResult(
					ObjectFolderItemImpl.class,
					objectFolderItem.getPrimaryKey()) == null) {

				cacheResult(objectFolderItem);
			}
		}
	}

	/**
	 * Clears the cache for all object folder items.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectFolderItemImpl.class);

		finderCache.clearCache(ObjectFolderItemImpl.class);
	}

	/**
	 * Clears the cache for the object folder item.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectFolderItem objectFolderItem) {
		entityCache.removeResult(ObjectFolderItemImpl.class, objectFolderItem);
	}

	@Override
	public void clearCache(List<ObjectFolderItem> objectFolderItems) {
		for (ObjectFolderItem objectFolderItem : objectFolderItems) {
			entityCache.removeResult(
				ObjectFolderItemImpl.class, objectFolderItem);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectFolderItemImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectFolderItemImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ObjectFolderItemModelImpl objectFolderItemModelImpl) {

		Object[] args = new Object[] {
			objectFolderItemModelImpl.getObjectDefinitionId(),
			objectFolderItemModelImpl.getObjectFolderId()
		};

		finderCache.putResult(_finderPathCountByODI_OFI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByODI_OFI, args, objectFolderItemModelImpl);
	}

	/**
	 * Creates a new object folder item with the primary key. Does not add the object folder item to the database.
	 *
	 * @param objectFolderItemId the primary key for the new object folder item
	 * @return the new object folder item
	 */
	@Override
	public ObjectFolderItem create(long objectFolderItemId) {
		ObjectFolderItem objectFolderItem = new ObjectFolderItemImpl();

		objectFolderItem.setNew(true);
		objectFolderItem.setPrimaryKey(objectFolderItemId);

		String uuid = _portalUUID.generate();

		objectFolderItem.setUuid(uuid);

		objectFolderItem.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectFolderItem;
	}

	/**
	 * Removes the object folder item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFolderItemId the primary key of the object folder item
	 * @return the object folder item that was removed
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	@Override
	public ObjectFolderItem remove(long objectFolderItemId)
		throws NoSuchObjectFolderItemException {

		return remove((Serializable)objectFolderItemId);
	}

	/**
	 * Removes the object folder item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object folder item
	 * @return the object folder item that was removed
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	@Override
	public ObjectFolderItem remove(Serializable primaryKey)
		throws NoSuchObjectFolderItemException {

		Session session = null;

		try {
			session = openSession();

			ObjectFolderItem objectFolderItem = (ObjectFolderItem)session.get(
				ObjectFolderItemImpl.class, primaryKey);

			if (objectFolderItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectFolderItemException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectFolderItem);
		}
		catch (NoSuchObjectFolderItemException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ObjectFolderItem removeImpl(ObjectFolderItem objectFolderItem) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectFolderItem)) {
				objectFolderItem = (ObjectFolderItem)session.get(
					ObjectFolderItemImpl.class,
					objectFolderItem.getPrimaryKeyObj());
			}

			if (objectFolderItem != null) {
				session.delete(objectFolderItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectFolderItem != null) {
			clearCache(objectFolderItem);
		}

		return objectFolderItem;
	}

	@Override
	public ObjectFolderItem updateImpl(ObjectFolderItem objectFolderItem) {
		boolean isNew = objectFolderItem.isNew();

		if (!(objectFolderItem instanceof ObjectFolderItemModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectFolderItem.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectFolderItem);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectFolderItem proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectFolderItem implementation " +
					objectFolderItem.getClass());
		}

		ObjectFolderItemModelImpl objectFolderItemModelImpl =
			(ObjectFolderItemModelImpl)objectFolderItem;

		if (Validator.isNull(objectFolderItem.getUuid())) {
			String uuid = _portalUUID.generate();

			objectFolderItem.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectFolderItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectFolderItem.setCreateDate(date);
			}
			else {
				objectFolderItem.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectFolderItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectFolderItem.setModifiedDate(date);
			}
			else {
				objectFolderItem.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectFolderItem);
			}
			else {
				objectFolderItem = (ObjectFolderItem)session.merge(
					objectFolderItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectFolderItemImpl.class, objectFolderItemModelImpl, false, true);

		cacheUniqueFindersCache(objectFolderItemModelImpl);

		if (isNew) {
			objectFolderItem.setNew(false);
		}

		objectFolderItem.resetOriginalValues();

		return objectFolderItem;
	}

	/**
	 * Returns the object folder item with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object folder item
	 * @return the object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	@Override
	public ObjectFolderItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectFolderItemException {

		ObjectFolderItem objectFolderItem = fetchByPrimaryKey(primaryKey);

		if (objectFolderItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectFolderItemException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectFolderItem;
	}

	/**
	 * Returns the object folder item with the primary key or throws a <code>NoSuchObjectFolderItemException</code> if it could not be found.
	 *
	 * @param objectFolderItemId the primary key of the object folder item
	 * @return the object folder item
	 * @throws NoSuchObjectFolderItemException if a object folder item with the primary key could not be found
	 */
	@Override
	public ObjectFolderItem findByPrimaryKey(long objectFolderItemId)
		throws NoSuchObjectFolderItemException {

		return findByPrimaryKey((Serializable)objectFolderItemId);
	}

	/**
	 * Returns the object folder item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFolderItemId the primary key of the object folder item
	 * @return the object folder item, or <code>null</code> if a object folder item with the primary key could not be found
	 */
	@Override
	public ObjectFolderItem fetchByPrimaryKey(long objectFolderItemId) {
		return fetchByPrimaryKey((Serializable)objectFolderItemId);
	}

	/**
	 * Returns all the object folder items.
	 *
	 * @return the object folder items
	 */
	@Override
	public List<ObjectFolderItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object folder items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @return the range of object folder items
	 */
	@Override
	public List<ObjectFolderItem> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object folder items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object folder items
	 */
	@Override
	public List<ObjectFolderItem> findAll(
		int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object folder items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFolderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object folder items
	 * @param end the upper bound of the range of object folder items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object folder items
	 */
	@Override
	public List<ObjectFolderItem> findAll(
		int start, int end,
		OrderByComparator<ObjectFolderItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<ObjectFolderItem> list = null;

		if (useFinderCache) {
			list = (List<ObjectFolderItem>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTFOLDERITEM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTFOLDERITEM;

				sql = sql.concat(ObjectFolderItemModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectFolderItem>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the object folder items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectFolderItem objectFolderItem : findAll()) {
			remove(objectFolderItem);
		}
	}

	/**
	 * Returns the number of object folder items.
	 *
	 * @return the number of object folder items
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTFOLDERITEM);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "objectFolderItemId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTFOLDERITEM;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectFolderItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object folder item persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByObjectDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectDefinitionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectDefinitionId"}, true);

		_finderPathWithoutPaginationFindByObjectDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByObjectDefinitionId", new String[] {Long.class.getName()},
			new String[] {"objectDefinitionId"}, true);

		_finderPathCountByObjectDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByObjectDefinitionId", new String[] {Long.class.getName()},
			new String[] {"objectDefinitionId"}, false);

		_finderPathWithPaginationFindByObjectFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectFolderId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectFolderId"}, true);

		_finderPathWithoutPaginationFindByObjectFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByObjectFolderId",
			new String[] {Long.class.getName()},
			new String[] {"objectFolderId"}, true);

		_finderPathCountByObjectFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByObjectFolderId",
			new String[] {Long.class.getName()},
			new String[] {"objectFolderId"}, false);

		_finderPathFetchByODI_OFI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByODI_OFI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"objectDefinitionId", "objectFolderId"}, true);

		_finderPathCountByODI_OFI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByODI_OFI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"objectDefinitionId", "objectFolderId"}, false);

		ObjectFolderItemUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ObjectFolderItemUtil.setPersistence(null);

		entityCache.removeCache(ObjectFolderItemImpl.class.getName());
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OBJECTFOLDERITEM =
		"SELECT objectFolderItem FROM ObjectFolderItem objectFolderItem";

	private static final String _SQL_SELECT_OBJECTFOLDERITEM_WHERE =
		"SELECT objectFolderItem FROM ObjectFolderItem objectFolderItem WHERE ";

	private static final String _SQL_COUNT_OBJECTFOLDERITEM =
		"SELECT COUNT(objectFolderItem) FROM ObjectFolderItem objectFolderItem";

	private static final String _SQL_COUNT_OBJECTFOLDERITEM_WHERE =
		"SELECT COUNT(objectFolderItem) FROM ObjectFolderItem objectFolderItem WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectFolderItem.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectFolderItem exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectFolderItem exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFolderItemPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}