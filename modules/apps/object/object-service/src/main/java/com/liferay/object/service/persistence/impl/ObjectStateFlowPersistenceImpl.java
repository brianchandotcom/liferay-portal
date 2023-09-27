/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence.impl;

import com.liferay.object.exception.NoSuchObjectStateFlowException;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.model.ObjectStateFlowTable;
import com.liferay.object.model.impl.ObjectStateFlowImpl;
import com.liferay.object.model.impl.ObjectStateFlowModelImpl;
import com.liferay.object.service.persistence.ObjectStateFlowPersistence;
import com.liferay.object.service.persistence.ObjectStateFlowUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the object state flow service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = ObjectStateFlowPersistence.class)
public class ObjectStateFlowPersistenceImpl
	extends BasePersistenceImpl<ObjectStateFlow>
	implements ObjectStateFlowPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectStateFlowUtil</code> to access the object state flow persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectStateFlowImpl.class.getName();

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
	 * Returns all the object state flows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object state flows
	 */
	@Override
	public List<ObjectStateFlow> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object state flows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @return the range of matching object state flows
	 */
	@Override
	public List<ObjectStateFlow> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state flows
	 */
	@Override
	public List<ObjectStateFlow> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state flows
	 */
	@Override
	public List<ObjectStateFlow> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator,
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

		List<ObjectStateFlow> list = null;

		if (useFinderCache) {
			list = (List<ObjectStateFlow>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectStateFlow objectStateFlow : list) {
					if (!uuid.equals(objectStateFlow.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTSTATEFLOW_WHERE);

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
				sb.append(ObjectStateFlowModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectStateFlow>)QueryUtil.list(
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
	 * Returns the first object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow findByUuid_First(
			String uuid, OrderByComparator<ObjectStateFlow> orderByComparator)
		throws NoSuchObjectStateFlowException {

		ObjectStateFlow objectStateFlow = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectStateFlow != null) {
			return objectStateFlow;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectStateFlowException(sb.toString());
	}

	/**
	 * Returns the first object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow fetchByUuid_First(
		String uuid, OrderByComparator<ObjectStateFlow> orderByComparator) {

		List<ObjectStateFlow> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow findByUuid_Last(
			String uuid, OrderByComparator<ObjectStateFlow> orderByComparator)
		throws NoSuchObjectStateFlowException {

		ObjectStateFlow objectStateFlow = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectStateFlow != null) {
			return objectStateFlow;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectStateFlowException(sb.toString());
	}

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectStateFlow> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectStateFlow> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object state flows before and after the current object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param objectStateFlowId the primary key of the current object state flow
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state flow
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	@Override
	public ObjectStateFlow[] findByUuid_PrevAndNext(
			long objectStateFlowId, String uuid,
			OrderByComparator<ObjectStateFlow> orderByComparator)
		throws NoSuchObjectStateFlowException {

		uuid = Objects.toString(uuid, "");

		ObjectStateFlow objectStateFlow = findByPrimaryKey(objectStateFlowId);

		Session session = null;

		try {
			session = openSession();

			ObjectStateFlow[] array = new ObjectStateFlowImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectStateFlow, uuid, orderByComparator, true);

			array[1] = objectStateFlow;

			array[2] = getByUuid_PrevAndNext(
				session, objectStateFlow, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectStateFlow getByUuid_PrevAndNext(
		Session session, ObjectStateFlow objectStateFlow, String uuid,
		OrderByComparator<ObjectStateFlow> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTSTATEFLOW_WHERE);

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
			sb.append(ObjectStateFlowModelImpl.ORDER_BY_JPQL);
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
						objectStateFlow)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectStateFlow> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object state flows where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectStateFlow objectStateFlow :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectStateFlow);
		}
	}

	/**
	 * Returns the number of object state flows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object state flows
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTSTATEFLOW_WHERE);

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
		"objectStateFlow.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectStateFlow.uuid IS NULL OR objectStateFlow.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object state flows
	 */
	@Override
	public List<ObjectStateFlow> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @return the range of matching object state flows
	 */
	@Override
	public List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state flows
	 */
	@Override
	public List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state flows
	 */
	@Override
	public List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator,
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

		List<ObjectStateFlow> list = null;

		if (useFinderCache) {
			list = (List<ObjectStateFlow>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectStateFlow objectStateFlow : list) {
					if (!uuid.equals(objectStateFlow.getUuid()) ||
						(companyId != objectStateFlow.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTSTATEFLOW_WHERE);

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
				sb.append(ObjectStateFlowModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectStateFlow>)QueryUtil.list(
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
	 * Returns the first object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectStateFlow> orderByComparator)
		throws NoSuchObjectStateFlowException {

		ObjectStateFlow objectStateFlow = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectStateFlow != null) {
			return objectStateFlow;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectStateFlowException(sb.toString());
	}

	/**
	 * Returns the first object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		List<ObjectStateFlow> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectStateFlow> orderByComparator)
		throws NoSuchObjectStateFlowException {

		ObjectStateFlow objectStateFlow = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectStateFlow != null) {
			return objectStateFlow;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectStateFlowException(sb.toString());
	}

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectStateFlow> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object state flows before and after the current object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectStateFlowId the primary key of the current object state flow
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state flow
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	@Override
	public ObjectStateFlow[] findByUuid_C_PrevAndNext(
			long objectStateFlowId, String uuid, long companyId,
			OrderByComparator<ObjectStateFlow> orderByComparator)
		throws NoSuchObjectStateFlowException {

		uuid = Objects.toString(uuid, "");

		ObjectStateFlow objectStateFlow = findByPrimaryKey(objectStateFlowId);

		Session session = null;

		try {
			session = openSession();

			ObjectStateFlow[] array = new ObjectStateFlowImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectStateFlow, uuid, companyId, orderByComparator,
				true);

			array[1] = objectStateFlow;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectStateFlow, uuid, companyId, orderByComparator,
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

	protected ObjectStateFlow getByUuid_C_PrevAndNext(
		Session session, ObjectStateFlow objectStateFlow, String uuid,
		long companyId, OrderByComparator<ObjectStateFlow> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTSTATEFLOW_WHERE);

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
			sb.append(ObjectStateFlowModelImpl.ORDER_BY_JPQL);
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
						objectStateFlow)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectStateFlow> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object state flows where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectStateFlow objectStateFlow :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectStateFlow);
		}
	}

	/**
	 * Returns the number of object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object state flows
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTSTATEFLOW_WHERE);

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
		"objectStateFlow.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectStateFlow.uuid IS NULL OR objectStateFlow.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectStateFlow.companyId = ?";

	private FinderPath _finderPathFetchByObjectFieldId;
	private FinderPath _finderPathCountByObjectFieldId;

	/**
	 * Returns the object state flow where objectFieldId = &#63; or throws a <code>NoSuchObjectStateFlowException</code> if it could not be found.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow findByObjectFieldId(long objectFieldId)
		throws NoSuchObjectStateFlowException {

		ObjectStateFlow objectStateFlow = fetchByObjectFieldId(objectFieldId);

		if (objectStateFlow == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("objectFieldId=");
			sb.append(objectFieldId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchObjectStateFlowException(sb.toString());
		}

		return objectStateFlow;
	}

	/**
	 * Returns the object state flow where objectFieldId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow fetchByObjectFieldId(long objectFieldId) {
		return fetchByObjectFieldId(objectFieldId, true);
	}

	/**
	 * Returns the object state flow where objectFieldId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	@Override
	public ObjectStateFlow fetchByObjectFieldId(
		long objectFieldId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {objectFieldId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByObjectFieldId, finderArgs, this);
		}

		if (result instanceof ObjectStateFlow) {
			ObjectStateFlow objectStateFlow = (ObjectStateFlow)result;

			if (objectFieldId != objectStateFlow.getObjectFieldId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_OBJECTSTATEFLOW_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectFieldId);

				List<ObjectStateFlow> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByObjectFieldId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {objectFieldId};
							}

							_log.warn(
								"ObjectStateFlowPersistenceImpl.fetchByObjectFieldId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ObjectStateFlow objectStateFlow = list.get(0);

					result = objectStateFlow;

					cacheResult(objectStateFlow);
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
			return (ObjectStateFlow)result;
		}
	}

	/**
	 * Removes the object state flow where objectFieldId = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 * @return the object state flow that was removed
	 */
	@Override
	public ObjectStateFlow removeByObjectFieldId(long objectFieldId)
		throws NoSuchObjectStateFlowException {

		ObjectStateFlow objectStateFlow = findByObjectFieldId(objectFieldId);

		return remove(objectStateFlow);
	}

	/**
	 * Returns the number of object state flows where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the number of matching object state flows
	 */
	@Override
	public int countByObjectFieldId(long objectFieldId) {
		FinderPath finderPath = _finderPathCountByObjectFieldId;

		Object[] finderArgs = new Object[] {objectFieldId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTSTATEFLOW_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectFieldId);

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

	private static final String _FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2 =
		"objectStateFlow.objectFieldId = ?";

	public ObjectStateFlowPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectStateFlow.class);

		setModelImplClass(ObjectStateFlowImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectStateFlowTable.INSTANCE);
	}

	/**
	 * Caches the object state flow in the entity cache if it is enabled.
	 *
	 * @param objectStateFlow the object state flow
	 */
	@Override
	public void cacheResult(ObjectStateFlow objectStateFlow) {
		entityCache.putResult(
			ObjectStateFlowImpl.class, objectStateFlow.getPrimaryKey(),
			objectStateFlow);

		finderCache.putResult(
			_finderPathFetchByObjectFieldId,
			new Object[] {objectStateFlow.getObjectFieldId()}, objectStateFlow);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object state flows in the entity cache if it is enabled.
	 *
	 * @param objectStateFlows the object state flows
	 */
	@Override
	public void cacheResult(List<ObjectStateFlow> objectStateFlows) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectStateFlows.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectStateFlow objectStateFlow : objectStateFlows) {
			if (entityCache.getResult(
					ObjectStateFlowImpl.class,
					objectStateFlow.getPrimaryKey()) == null) {

				cacheResult(objectStateFlow);
			}
		}
	}

	/**
	 * Clears the cache for all object state flows.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectStateFlowImpl.class);

		finderCache.clearCache(ObjectStateFlowImpl.class);
	}

	/**
	 * Clears the cache for the object state flow.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectStateFlow objectStateFlow) {
		entityCache.removeResult(ObjectStateFlowImpl.class, objectStateFlow);
	}

	@Override
	public void clearCache(List<ObjectStateFlow> objectStateFlows) {
		for (ObjectStateFlow objectStateFlow : objectStateFlows) {
			entityCache.removeResult(
				ObjectStateFlowImpl.class, objectStateFlow);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectStateFlowImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectStateFlowImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ObjectStateFlowModelImpl objectStateFlowModelImpl) {

		Object[] args = new Object[] {
			objectStateFlowModelImpl.getObjectFieldId()
		};

		finderCache.putResult(
			_finderPathCountByObjectFieldId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByObjectFieldId, args, objectStateFlowModelImpl);
	}

	/**
	 * Creates a new object state flow with the primary key. Does not add the object state flow to the database.
	 *
	 * @param objectStateFlowId the primary key for the new object state flow
	 * @return the new object state flow
	 */
	@Override
	public ObjectStateFlow create(long objectStateFlowId) {
		ObjectStateFlow objectStateFlow = new ObjectStateFlowImpl();

		objectStateFlow.setNew(true);
		objectStateFlow.setPrimaryKey(objectStateFlowId);

		String uuid = _portalUUID.generate();

		objectStateFlow.setUuid(uuid);

		objectStateFlow.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectStateFlow;
	}

	/**
	 * Removes the object state flow with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow that was removed
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	@Override
	public ObjectStateFlow remove(long objectStateFlowId)
		throws NoSuchObjectStateFlowException {

		return remove((Serializable)objectStateFlowId);
	}

	/**
	 * Removes the object state flow with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object state flow
	 * @return the object state flow that was removed
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	@Override
	public ObjectStateFlow remove(Serializable primaryKey)
		throws NoSuchObjectStateFlowException {

		Session session = null;

		try {
			session = openSession();

			ObjectStateFlow objectStateFlow = (ObjectStateFlow)session.get(
				ObjectStateFlowImpl.class, primaryKey);

			if (objectStateFlow == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectStateFlowException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectStateFlow);
		}
		catch (NoSuchObjectStateFlowException noSuchEntityException) {
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
	protected ObjectStateFlow removeImpl(ObjectStateFlow objectStateFlow) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectStateFlow)) {
				objectStateFlow = (ObjectStateFlow)session.get(
					ObjectStateFlowImpl.class,
					objectStateFlow.getPrimaryKeyObj());
			}

			if (objectStateFlow != null) {
				session.delete(objectStateFlow);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectStateFlow != null) {
			clearCache(objectStateFlow);
		}

		return objectStateFlow;
	}

	@Override
	public ObjectStateFlow updateImpl(ObjectStateFlow objectStateFlow) {
		boolean isNew = objectStateFlow.isNew();

		if (!(objectStateFlow instanceof ObjectStateFlowModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectStateFlow.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectStateFlow);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectStateFlow proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectStateFlow implementation " +
					objectStateFlow.getClass());
		}

		ObjectStateFlowModelImpl objectStateFlowModelImpl =
			(ObjectStateFlowModelImpl)objectStateFlow;

		if (Validator.isNull(objectStateFlow.getUuid())) {
			String uuid = _portalUUID.generate();

			objectStateFlow.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectStateFlow.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectStateFlow.setCreateDate(date);
			}
			else {
				objectStateFlow.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectStateFlowModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectStateFlow.setModifiedDate(date);
			}
			else {
				objectStateFlow.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectStateFlow);
			}
			else {
				objectStateFlow = (ObjectStateFlow)session.merge(
					objectStateFlow);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectStateFlowImpl.class, objectStateFlowModelImpl, false, true);

		cacheUniqueFindersCache(objectStateFlowModelImpl);

		if (isNew) {
			objectStateFlow.setNew(false);
		}

		objectStateFlow.resetOriginalValues();

		return objectStateFlow;
	}

	/**
	 * Returns the object state flow with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object state flow
	 * @return the object state flow
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	@Override
	public ObjectStateFlow findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectStateFlowException {

		ObjectStateFlow objectStateFlow = fetchByPrimaryKey(primaryKey);

		if (objectStateFlow == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectStateFlowException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectStateFlow;
	}

	/**
	 * Returns the object state flow with the primary key or throws a <code>NoSuchObjectStateFlowException</code> if it could not be found.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	@Override
	public ObjectStateFlow findByPrimaryKey(long objectStateFlowId)
		throws NoSuchObjectStateFlowException {

		return findByPrimaryKey((Serializable)objectStateFlowId);
	}

	/**
	 * Returns the object state flow with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow, or <code>null</code> if a object state flow with the primary key could not be found
	 */
	@Override
	public ObjectStateFlow fetchByPrimaryKey(long objectStateFlowId) {
		return fetchByPrimaryKey((Serializable)objectStateFlowId);
	}

	/**
	 * Returns all the object state flows.
	 *
	 * @return the object state flows
	 */
	@Override
	public List<ObjectStateFlow> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object state flows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @return the range of object state flows
	 */
	@Override
	public List<ObjectStateFlow> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object state flows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object state flows
	 */
	@Override
	public List<ObjectStateFlow> findAll(
		int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object state flows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object state flows
	 */
	@Override
	public List<ObjectStateFlow> findAll(
		int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator,
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

		List<ObjectStateFlow> list = null;

		if (useFinderCache) {
			list = (List<ObjectStateFlow>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTSTATEFLOW);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTSTATEFLOW;

				sql = sql.concat(ObjectStateFlowModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectStateFlow>)QueryUtil.list(
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
	 * Removes all the object state flows from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectStateFlow objectStateFlow : findAll()) {
			remove(objectStateFlow);
		}
	}

	/**
	 * Returns the number of object state flows.
	 *
	 * @return the number of object state flows
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTSTATEFLOW);

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
		return "objectStateFlowId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTSTATEFLOW;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectStateFlowModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object state flow persistence.
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

		_finderPathFetchByObjectFieldId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByObjectFieldId",
			new String[] {Long.class.getName()}, new String[] {"objectFieldId"},
			true);

		_finderPathCountByObjectFieldId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByObjectFieldId",
			new String[] {Long.class.getName()}, new String[] {"objectFieldId"},
			false);

		ObjectStateFlowUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ObjectStateFlowUtil.setPersistence(null);

		entityCache.removeCache(ObjectStateFlowImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTSTATEFLOW =
		"SELECT objectStateFlow FROM ObjectStateFlow objectStateFlow";

	private static final String _SQL_SELECT_OBJECTSTATEFLOW_WHERE =
		"SELECT objectStateFlow FROM ObjectStateFlow objectStateFlow WHERE ";

	private static final String _SQL_COUNT_OBJECTSTATEFLOW =
		"SELECT COUNT(objectStateFlow) FROM ObjectStateFlow objectStateFlow";

	private static final String _SQL_COUNT_OBJECTSTATEFLOW_WHERE =
		"SELECT COUNT(objectStateFlow) FROM ObjectStateFlow objectStateFlow WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectStateFlow.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectStateFlow exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectStateFlow exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectStateFlowPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}