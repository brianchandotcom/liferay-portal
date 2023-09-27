/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence.impl;

import com.liferay.object.exception.NoSuchObjectViewFilterColumnException;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.model.ObjectViewFilterColumnTable;
import com.liferay.object.model.impl.ObjectViewFilterColumnImpl;
import com.liferay.object.model.impl.ObjectViewFilterColumnModelImpl;
import com.liferay.object.service.persistence.ObjectViewFilterColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewFilterColumnUtil;
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
 * The persistence implementation for the object view filter column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = ObjectViewFilterColumnPersistence.class)
public class ObjectViewFilterColumnPersistenceImpl
	extends BasePersistenceImpl<ObjectViewFilterColumn>
	implements ObjectViewFilterColumnPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectViewFilterColumnUtil</code> to access the object view filter column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectViewFilterColumnImpl.class.getName();

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
	 * Returns all the object view filter columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
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

		List<ObjectViewFilterColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewFilterColumn>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewFilterColumn objectViewFilterColumn : list) {
					if (!uuid.equals(objectViewFilterColumn.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE);

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
				sb.append(ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectViewFilterColumn>)QueryUtil.list(
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
	 * Returns the first object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByUuid_First(
			String uuid,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectViewFilterColumnException(sb.toString());
	}

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		List<ObjectViewFilterColumn> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectViewFilterColumnException(sb.toString());
	}

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByUuid_Last(
		String uuid,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectViewFilterColumn> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn[] findByUuid_PrevAndNext(
			long objectViewFilterColumnId, String uuid,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		uuid = Objects.toString(uuid, "");

		ObjectViewFilterColumn objectViewFilterColumn = findByPrimaryKey(
			objectViewFilterColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewFilterColumn[] array = new ObjectViewFilterColumnImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectViewFilterColumn, uuid, orderByComparator, true);

			array[1] = objectViewFilterColumn;

			array[2] = getByUuid_PrevAndNext(
				session, objectViewFilterColumn, uuid, orderByComparator,
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

	protected ObjectViewFilterColumn getByUuid_PrevAndNext(
		Session session, ObjectViewFilterColumn objectViewFilterColumn,
		String uuid,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE);

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
			sb.append(ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL);
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
						objectViewFilterColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewFilterColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view filter columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectViewFilterColumn objectViewFilterColumn :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectViewFilterColumn);
		}
	}

	/**
	 * Returns the number of object view filter columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view filter columns
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTVIEWFILTERCOLUMN_WHERE);

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
		"objectViewFilterColumn.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectViewFilterColumn.uuid IS NULL OR objectViewFilterColumn.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
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

		List<ObjectViewFilterColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewFilterColumn>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewFilterColumn objectViewFilterColumn : list) {
					if (!uuid.equals(objectViewFilterColumn.getUuid()) ||
						(companyId != objectViewFilterColumn.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE);

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
				sb.append(ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectViewFilterColumn>)QueryUtil.list(
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
	 * Returns the first object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectViewFilterColumnException(sb.toString());
	}

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		List<ObjectViewFilterColumn> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectViewFilterColumnException(sb.toString());
	}

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectViewFilterColumn> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn[] findByUuid_C_PrevAndNext(
			long objectViewFilterColumnId, String uuid, long companyId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		uuid = Objects.toString(uuid, "");

		ObjectViewFilterColumn objectViewFilterColumn = findByPrimaryKey(
			objectViewFilterColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewFilterColumn[] array = new ObjectViewFilterColumnImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectViewFilterColumn, uuid, companyId,
				orderByComparator, true);

			array[1] = objectViewFilterColumn;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectViewFilterColumn, uuid, companyId,
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

	protected ObjectViewFilterColumn getByUuid_C_PrevAndNext(
		Session session, ObjectViewFilterColumn objectViewFilterColumn,
		String uuid, long companyId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE);

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
			sb.append(ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL);
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
						objectViewFilterColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewFilterColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view filter columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectViewFilterColumn objectViewFilterColumn :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectViewFilterColumn);
		}
	}

	/**
	 * Returns the number of object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view filter columns
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVIEWFILTERCOLUMN_WHERE);

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
		"objectViewFilterColumn.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectViewFilterColumn.uuid IS NULL OR objectViewFilterColumn.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectViewFilterColumn.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectViewId;
	private FinderPath _finderPathWithoutPaginationFindByObjectViewId;
	private FinderPath _finderPathCountByObjectViewId;

	/**
	 * Returns all the object view filter columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByObjectViewId(long objectViewId) {
		return findByObjectViewId(
			objectViewId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end) {

		return findByObjectViewId(objectViewId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return findByObjectViewId(
			objectViewId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByObjectViewId;
				finderArgs = new Object[] {objectViewId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectViewId;
			finderArgs = new Object[] {
				objectViewId, start, end, orderByComparator
			};
		}

		List<ObjectViewFilterColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewFilterColumn>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewFilterColumn objectViewFilterColumn : list) {
					if (objectViewId !=
							objectViewFilterColumn.getObjectViewId()) {

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

			sb.append(_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTVIEWID_OBJECTVIEWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectViewId);

				list = (List<ObjectViewFilterColumn>)QueryUtil.list(
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
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByObjectViewId_First(
			long objectViewId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn =
			fetchByObjectViewId_First(objectViewId, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectViewId=");
		sb.append(objectViewId);

		sb.append("}");

		throw new NoSuchObjectViewFilterColumnException(sb.toString());
	}

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByObjectViewId_First(
		long objectViewId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		List<ObjectViewFilterColumn> list = findByObjectViewId(
			objectViewId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByObjectViewId_Last(
			long objectViewId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn =
			fetchByObjectViewId_Last(objectViewId, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectViewId=");
		sb.append(objectViewId);

		sb.append("}");

		throw new NoSuchObjectViewFilterColumnException(sb.toString());
	}

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByObjectViewId_Last(
		long objectViewId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		int count = countByObjectViewId(objectViewId);

		if (count == 0) {
			return null;
		}

		List<ObjectViewFilterColumn> list = findByObjectViewId(
			objectViewId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn[] findByObjectViewId_PrevAndNext(
			long objectViewFilterColumnId, long objectViewId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = findByPrimaryKey(
			objectViewFilterColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewFilterColumn[] array = new ObjectViewFilterColumnImpl[3];

			array[0] = getByObjectViewId_PrevAndNext(
				session, objectViewFilterColumn, objectViewId,
				orderByComparator, true);

			array[1] = objectViewFilterColumn;

			array[2] = getByObjectViewId_PrevAndNext(
				session, objectViewFilterColumn, objectViewId,
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

	protected ObjectViewFilterColumn getByObjectViewId_PrevAndNext(
		Session session, ObjectViewFilterColumn objectViewFilterColumn,
		long objectViewId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTVIEWID_OBJECTVIEWID_2);

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
			sb.append(ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectViewId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectViewFilterColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewFilterColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view filter columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	@Override
	public void removeByObjectViewId(long objectViewId) {
		for (ObjectViewFilterColumn objectViewFilterColumn :
				findByObjectViewId(
					objectViewId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectViewFilterColumn);
		}
	}

	/**
	 * Returns the number of object view filter columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view filter columns
	 */
	@Override
	public int countByObjectViewId(long objectViewId) {
		FinderPath finderPath = _finderPathCountByObjectViewId;

		Object[] finderArgs = new Object[] {objectViewId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTVIEWFILTERCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTVIEWID_OBJECTVIEWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectViewId);

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

	private static final String _FINDER_COLUMN_OBJECTVIEWID_OBJECTVIEWID_2 =
		"objectViewFilterColumn.objectViewId = ?";

	private FinderPath _finderPathWithPaginationFindByOVI_OFN;
	private FinderPath _finderPathWithoutPaginationFindByOVI_OFN;
	private FinderPath _finderPathCountByOVI_OFN;

	/**
	 * Returns all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName) {

		return findByOVI_OFN(
			objectViewId, objectFieldName, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end) {

		return findByOVI_OFN(objectViewId, objectFieldName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return findByOVI_OFN(
			objectViewId, objectFieldName, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		objectFieldName = Objects.toString(objectFieldName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByOVI_OFN;
				finderArgs = new Object[] {objectViewId, objectFieldName};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByOVI_OFN;
			finderArgs = new Object[] {
				objectViewId, objectFieldName, start, end, orderByComparator
			};
		}

		List<ObjectViewFilterColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewFilterColumn>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewFilterColumn objectViewFilterColumn : list) {
					if ((objectViewId !=
							objectViewFilterColumn.getObjectViewId()) ||
						!objectFieldName.equals(
							objectViewFilterColumn.getObjectFieldName())) {

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

			sb.append(_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTVIEWID_2);

			boolean bindObjectFieldName = false;

			if (objectFieldName.isEmpty()) {
				sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_3);
			}
			else {
				bindObjectFieldName = true;

				sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectViewId);

				if (bindObjectFieldName) {
					queryPos.add(objectFieldName);
				}

				list = (List<ObjectViewFilterColumn>)QueryUtil.list(
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
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByOVI_OFN_First(
			long objectViewId, String objectFieldName,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = fetchByOVI_OFN_First(
			objectViewId, objectFieldName, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectViewId=");
		sb.append(objectViewId);

		sb.append(", objectFieldName=");
		sb.append(objectFieldName);

		sb.append("}");

		throw new NoSuchObjectViewFilterColumnException(sb.toString());
	}

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByOVI_OFN_First(
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		List<ObjectViewFilterColumn> list = findByOVI_OFN(
			objectViewId, objectFieldName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByOVI_OFN_Last(
			long objectViewId, String objectFieldName,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = fetchByOVI_OFN_Last(
			objectViewId, objectFieldName, orderByComparator);

		if (objectViewFilterColumn != null) {
			return objectViewFilterColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectViewId=");
		sb.append(objectViewId);

		sb.append(", objectFieldName=");
		sb.append(objectFieldName);

		sb.append("}");

		throw new NoSuchObjectViewFilterColumnException(sb.toString());
	}

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByOVI_OFN_Last(
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		int count = countByOVI_OFN(objectViewId, objectFieldName);

		if (count == 0) {
			return null;
		}

		List<ObjectViewFilterColumn> list = findByOVI_OFN(
			objectViewId, objectFieldName, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn[] findByOVI_OFN_PrevAndNext(
			long objectViewFilterColumnId, long objectViewId,
			String objectFieldName,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException {

		objectFieldName = Objects.toString(objectFieldName, "");

		ObjectViewFilterColumn objectViewFilterColumn = findByPrimaryKey(
			objectViewFilterColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewFilterColumn[] array = new ObjectViewFilterColumnImpl[3];

			array[0] = getByOVI_OFN_PrevAndNext(
				session, objectViewFilterColumn, objectViewId, objectFieldName,
				orderByComparator, true);

			array[1] = objectViewFilterColumn;

			array[2] = getByOVI_OFN_PrevAndNext(
				session, objectViewFilterColumn, objectViewId, objectFieldName,
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

	protected ObjectViewFilterColumn getByOVI_OFN_PrevAndNext(
		Session session, ObjectViewFilterColumn objectViewFilterColumn,
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE);

		sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTVIEWID_2);

		boolean bindObjectFieldName = false;

		if (objectFieldName.isEmpty()) {
			sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_3);
		}
		else {
			bindObjectFieldName = true;

			sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_2);
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
			sb.append(ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectViewId);

		if (bindObjectFieldName) {
			queryPos.add(objectFieldName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectViewFilterColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewFilterColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 */
	@Override
	public void removeByOVI_OFN(long objectViewId, String objectFieldName) {
		for (ObjectViewFilterColumn objectViewFilterColumn :
				findByOVI_OFN(
					objectViewId, objectFieldName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(objectViewFilterColumn);
		}
	}

	/**
	 * Returns the number of object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the number of matching object view filter columns
	 */
	@Override
	public int countByOVI_OFN(long objectViewId, String objectFieldName) {
		objectFieldName = Objects.toString(objectFieldName, "");

		FinderPath finderPath = _finderPathCountByOVI_OFN;

		Object[] finderArgs = new Object[] {objectViewId, objectFieldName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVIEWFILTERCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTVIEWID_2);

			boolean bindObjectFieldName = false;

			if (objectFieldName.isEmpty()) {
				sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_3);
			}
			else {
				bindObjectFieldName = true;

				sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectViewId);

				if (bindObjectFieldName) {
					queryPos.add(objectFieldName);
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

	private static final String _FINDER_COLUMN_OVI_OFN_OBJECTVIEWID_2 =
		"objectViewFilterColumn.objectViewId = ? AND ";

	private static final String _FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_2 =
		"objectViewFilterColumn.objectFieldName = ?";

	private static final String _FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_3 =
		"(objectViewFilterColumn.objectFieldName IS NULL OR objectViewFilterColumn.objectFieldName = '')";

	public ObjectViewFilterColumnPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectViewFilterColumn.class);

		setModelImplClass(ObjectViewFilterColumnImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectViewFilterColumnTable.INSTANCE);
	}

	/**
	 * Caches the object view filter column in the entity cache if it is enabled.
	 *
	 * @param objectViewFilterColumn the object view filter column
	 */
	@Override
	public void cacheResult(ObjectViewFilterColumn objectViewFilterColumn) {
		entityCache.putResult(
			ObjectViewFilterColumnImpl.class,
			objectViewFilterColumn.getPrimaryKey(), objectViewFilterColumn);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object view filter columns in the entity cache if it is enabled.
	 *
	 * @param objectViewFilterColumns the object view filter columns
	 */
	@Override
	public void cacheResult(
		List<ObjectViewFilterColumn> objectViewFilterColumns) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectViewFilterColumns.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectViewFilterColumn objectViewFilterColumn :
				objectViewFilterColumns) {

			if (entityCache.getResult(
					ObjectViewFilterColumnImpl.class,
					objectViewFilterColumn.getPrimaryKey()) == null) {

				cacheResult(objectViewFilterColumn);
			}
		}
	}

	/**
	 * Clears the cache for all object view filter columns.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectViewFilterColumnImpl.class);

		finderCache.clearCache(ObjectViewFilterColumnImpl.class);
	}

	/**
	 * Clears the cache for the object view filter column.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectViewFilterColumn objectViewFilterColumn) {
		entityCache.removeResult(
			ObjectViewFilterColumnImpl.class, objectViewFilterColumn);
	}

	@Override
	public void clearCache(
		List<ObjectViewFilterColumn> objectViewFilterColumns) {

		for (ObjectViewFilterColumn objectViewFilterColumn :
				objectViewFilterColumns) {

			entityCache.removeResult(
				ObjectViewFilterColumnImpl.class, objectViewFilterColumn);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectViewFilterColumnImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ObjectViewFilterColumnImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object view filter column with the primary key. Does not add the object view filter column to the database.
	 *
	 * @param objectViewFilterColumnId the primary key for the new object view filter column
	 * @return the new object view filter column
	 */
	@Override
	public ObjectViewFilterColumn create(long objectViewFilterColumnId) {
		ObjectViewFilterColumn objectViewFilterColumn =
			new ObjectViewFilterColumnImpl();

		objectViewFilterColumn.setNew(true);
		objectViewFilterColumn.setPrimaryKey(objectViewFilterColumnId);

		String uuid = _portalUUID.generate();

		objectViewFilterColumn.setUuid(uuid);

		objectViewFilterColumn.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectViewFilterColumn;
	}

	/**
	 * Removes the object view filter column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column that was removed
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn remove(long objectViewFilterColumnId)
		throws NoSuchObjectViewFilterColumnException {

		return remove((Serializable)objectViewFilterColumnId);
	}

	/**
	 * Removes the object view filter column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object view filter column
	 * @return the object view filter column that was removed
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn remove(Serializable primaryKey)
		throws NoSuchObjectViewFilterColumnException {

		Session session = null;

		try {
			session = openSession();

			ObjectViewFilterColumn objectViewFilterColumn =
				(ObjectViewFilterColumn)session.get(
					ObjectViewFilterColumnImpl.class, primaryKey);

			if (objectViewFilterColumn == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectViewFilterColumnException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectViewFilterColumn);
		}
		catch (NoSuchObjectViewFilterColumnException noSuchEntityException) {
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
	protected ObjectViewFilterColumn removeImpl(
		ObjectViewFilterColumn objectViewFilterColumn) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectViewFilterColumn)) {
				objectViewFilterColumn = (ObjectViewFilterColumn)session.get(
					ObjectViewFilterColumnImpl.class,
					objectViewFilterColumn.getPrimaryKeyObj());
			}

			if (objectViewFilterColumn != null) {
				session.delete(objectViewFilterColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectViewFilterColumn != null) {
			clearCache(objectViewFilterColumn);
		}

		return objectViewFilterColumn;
	}

	@Override
	public ObjectViewFilterColumn updateImpl(
		ObjectViewFilterColumn objectViewFilterColumn) {

		boolean isNew = objectViewFilterColumn.isNew();

		if (!(objectViewFilterColumn instanceof
				ObjectViewFilterColumnModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectViewFilterColumn.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectViewFilterColumn);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectViewFilterColumn proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectViewFilterColumn implementation " +
					objectViewFilterColumn.getClass());
		}

		ObjectViewFilterColumnModelImpl objectViewFilterColumnModelImpl =
			(ObjectViewFilterColumnModelImpl)objectViewFilterColumn;

		if (Validator.isNull(objectViewFilterColumn.getUuid())) {
			String uuid = _portalUUID.generate();

			objectViewFilterColumn.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectViewFilterColumn.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectViewFilterColumn.setCreateDate(date);
			}
			else {
				objectViewFilterColumn.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectViewFilterColumnModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectViewFilterColumn.setModifiedDate(date);
			}
			else {
				objectViewFilterColumn.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectViewFilterColumn);
			}
			else {
				objectViewFilterColumn = (ObjectViewFilterColumn)session.merge(
					objectViewFilterColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectViewFilterColumnImpl.class, objectViewFilterColumnModelImpl,
			false, true);

		if (isNew) {
			objectViewFilterColumn.setNew(false);
		}

		objectViewFilterColumn.resetOriginalValues();

		return objectViewFilterColumn;
	}

	/**
	 * Returns the object view filter column with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object view filter column
	 * @return the object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectViewFilterColumnException {

		ObjectViewFilterColumn objectViewFilterColumn = fetchByPrimaryKey(
			primaryKey);

		if (objectViewFilterColumn == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectViewFilterColumnException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectViewFilterColumn;
	}

	/**
	 * Returns the object view filter column with the primary key or throws a <code>NoSuchObjectViewFilterColumnException</code> if it could not be found.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn findByPrimaryKey(
			long objectViewFilterColumnId)
		throws NoSuchObjectViewFilterColumnException {

		return findByPrimaryKey((Serializable)objectViewFilterColumnId);
	}

	/**
	 * Returns the object view filter column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column, or <code>null</code> if a object view filter column with the primary key could not be found
	 */
	@Override
	public ObjectViewFilterColumn fetchByPrimaryKey(
		long objectViewFilterColumnId) {

		return fetchByPrimaryKey((Serializable)objectViewFilterColumnId);
	}

	/**
	 * Returns all the object view filter columns.
	 *
	 * @return the object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object view filter columns
	 */
	@Override
	public List<ObjectViewFilterColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
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

		List<ObjectViewFilterColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewFilterColumn>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTVIEWFILTERCOLUMN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTVIEWFILTERCOLUMN;

				sql = sql.concat(ObjectViewFilterColumnModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectViewFilterColumn>)QueryUtil.list(
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
	 * Removes all the object view filter columns from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectViewFilterColumn objectViewFilterColumn : findAll()) {
			remove(objectViewFilterColumn);
		}
	}

	/**
	 * Returns the number of object view filter columns.
	 *
	 * @return the number of object view filter columns
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_OBJECTVIEWFILTERCOLUMN);

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
		return "objectViewFilterColumnId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTVIEWFILTERCOLUMN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectViewFilterColumnModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object view filter column persistence.
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

		_finderPathWithPaginationFindByObjectViewId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectViewId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectViewId"}, true);

		_finderPathWithoutPaginationFindByObjectViewId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByObjectViewId",
			new String[] {Long.class.getName()}, new String[] {"objectViewId"},
			true);

		_finderPathCountByObjectViewId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByObjectViewId",
			new String[] {Long.class.getName()}, new String[] {"objectViewId"},
			false);

		_finderPathWithPaginationFindByOVI_OFN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOVI_OFN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"objectViewId", "objectFieldName"}, true);

		_finderPathWithoutPaginationFindByOVI_OFN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByOVI_OFN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectViewId", "objectFieldName"}, true);

		_finderPathCountByOVI_OFN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOVI_OFN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectViewId", "objectFieldName"}, false);

		ObjectViewFilterColumnUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ObjectViewFilterColumnUtil.setPersistence(null);

		entityCache.removeCache(ObjectViewFilterColumnImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTVIEWFILTERCOLUMN =
		"SELECT objectViewFilterColumn FROM ObjectViewFilterColumn objectViewFilterColumn";

	private static final String _SQL_SELECT_OBJECTVIEWFILTERCOLUMN_WHERE =
		"SELECT objectViewFilterColumn FROM ObjectViewFilterColumn objectViewFilterColumn WHERE ";

	private static final String _SQL_COUNT_OBJECTVIEWFILTERCOLUMN =
		"SELECT COUNT(objectViewFilterColumn) FROM ObjectViewFilterColumn objectViewFilterColumn";

	private static final String _SQL_COUNT_OBJECTVIEWFILTERCOLUMN_WHERE =
		"SELECT COUNT(objectViewFilterColumn) FROM ObjectViewFilterColumn objectViewFilterColumn WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"objectViewFilterColumn.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectViewFilterColumn exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectViewFilterColumn exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectViewFilterColumnPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}