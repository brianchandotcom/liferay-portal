/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.json.storage.service.persistence.impl;

import com.liferay.json.storage.exception.NoSuchJSONStorageEntryException;
import com.liferay.json.storage.model.JSONStorageEntry;
import com.liferay.json.storage.model.JSONStorageEntryTable;
import com.liferay.json.storage.model.impl.JSONStorageEntryImpl;
import com.liferay.json.storage.model.impl.JSONStorageEntryModelImpl;
import com.liferay.json.storage.service.persistence.JSONStorageEntryPersistence;
import com.liferay.json.storage.service.persistence.impl.constants.JSONStorePersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * The persistence implementation for the json storage entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Preston Crary
 * @generated
 */
@Component(service = {JSONStorageEntryPersistence.class, BasePersistence.class})
public class JSONStorageEntryPersistenceImpl
	extends BasePersistenceImpl<JSONStorageEntry>
	implements JSONStorageEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>JSONStorageEntryUtil</code> to access the json storage entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		JSONStorageEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCN_CPK;
	private FinderPath _finderPathWithoutPaginationFindByCN_CPK;
	private FinderPath _finderPathCountByCN_CPK;

	/**
	 * Returns all the json storage entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByCN_CPK(long classNameId, long classPK) {
		return findByCN_CPK(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the json storage entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @return the range of matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end) {

		return findByCN_CPK(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the json storage entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<JSONStorageEntry> orderByComparator) {

		return findByCN_CPK(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the json storage entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<JSONStorageEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStorageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCN_CPK;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCN_CPK;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<JSONStorageEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JSONStorageEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (JSONStorageEntry jsonStorageEntry : list) {
					if ((classNameId != jsonStorageEntry.getClassNameId()) ||
						(classPK != jsonStorageEntry.getClassPK())) {

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

			sb.append(_SQL_SELECT_JSONSTORAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JSONStorageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<JSONStorageEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first json storage entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry findByCN_CPK_First(
			long classNameId, long classPK,
			OrderByComparator<JSONStorageEntry> orderByComparator)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = fetchByCN_CPK_First(
			classNameId, classPK, orderByComparator);

		if (jsonStorageEntry != null) {
			return jsonStorageEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchJSONStorageEntryException(sb.toString());
	}

	/**
	 * Returns the first json storage entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry fetchByCN_CPK_First(
		long classNameId, long classPK,
		OrderByComparator<JSONStorageEntry> orderByComparator) {

		List<JSONStorageEntry> list = findByCN_CPK(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last json storage entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry findByCN_CPK_Last(
			long classNameId, long classPK,
			OrderByComparator<JSONStorageEntry> orderByComparator)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = fetchByCN_CPK_Last(
			classNameId, classPK, orderByComparator);

		if (jsonStorageEntry != null) {
			return jsonStorageEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchJSONStorageEntryException(sb.toString());
	}

	/**
	 * Returns the last json storage entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry fetchByCN_CPK_Last(
		long classNameId, long classPK,
		OrderByComparator<JSONStorageEntry> orderByComparator) {

		int count = countByCN_CPK(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<JSONStorageEntry> list = findByCN_CPK(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the json storage entries before and after the current json storage entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param jsonStorageEntryId the primary key of the current json storage entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json storage entry
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	@Override
	public JSONStorageEntry[] findByCN_CPK_PrevAndNext(
			long jsonStorageEntryId, long classNameId, long classPK,
			OrderByComparator<JSONStorageEntry> orderByComparator)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = findByPrimaryKey(
			jsonStorageEntryId);

		Session session = null;

		try {
			session = openSession();

			JSONStorageEntry[] array = new JSONStorageEntryImpl[3];

			array[0] = getByCN_CPK_PrevAndNext(
				session, jsonStorageEntry, classNameId, classPK,
				orderByComparator, true);

			array[1] = jsonStorageEntry;

			array[2] = getByCN_CPK_PrevAndNext(
				session, jsonStorageEntry, classNameId, classPK,
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

	protected JSONStorageEntry getByCN_CPK_PrevAndNext(
		Session session, JSONStorageEntry jsonStorageEntry, long classNameId,
		long classPK, OrderByComparator<JSONStorageEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_JSONSTORAGEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

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
			sb.append(JSONStorageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						jsonStorageEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JSONStorageEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the json storage entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByCN_CPK(long classNameId, long classPK) {
		for (JSONStorageEntry jsonStorageEntry :
				findByCN_CPK(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(jsonStorageEntry);
		}
	}

	/**
	 * Returns the number of json storage entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching json storage entries
	 */
	@Override
	public int countByCN_CPK(long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStorageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCN_CPK;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_JSONSTORAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_CN_CPK_CLASSNAMEID_2 =
		"jsonStorageEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_CLASSPK_2 =
		"jsonStorageEntry.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_CN_I_T_VL;
	private FinderPath _finderPathWithoutPaginationFindByC_CN_I_T_VL;
	private FinderPath _finderPathCountByC_CN_I_T_VL;

	/**
	 * Returns all the json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @return the matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong) {

		return findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @return the range of matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end) {

		return findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, start, end, null);
	}

	/**
	 * Returns an ordered range of all the json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end,
		OrderByComparator<JSONStorageEntry> orderByComparator) {

		return findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end,
		OrderByComparator<JSONStorageEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStorageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_CN_I_T_VL;
				finderArgs = new Object[] {
					companyId, classNameId, index, type, valueLong
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_CN_I_T_VL;
			finderArgs = new Object[] {
				companyId, classNameId, index, type, valueLong, start, end,
				orderByComparator
			};
		}

		List<JSONStorageEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JSONStorageEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (JSONStorageEntry jsonStorageEntry : list) {
					if ((companyId != jsonStorageEntry.getCompanyId()) ||
						(classNameId != jsonStorageEntry.getClassNameId()) ||
						(index != jsonStorageEntry.getIndex()) ||
						(type != jsonStorageEntry.getType()) ||
						(valueLong != jsonStorageEntry.getValueLong())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_JSONSTORAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_CN_I_T_VL_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_CN_I_T_VL_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_CN_I_T_VL_INDEX_2);

			sb.append(_FINDER_COLUMN_C_CN_I_T_VL_TYPE_2);

			sb.append(_FINDER_COLUMN_C_CN_I_T_VL_VALUELONG_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JSONStorageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				queryPos.add(index);

				queryPos.add(type);

				queryPos.add(valueLong);

				list = (List<JSONStorageEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry findByC_CN_I_T_VL_First(
			long companyId, long classNameId, int index, int type,
			long valueLong,
			OrderByComparator<JSONStorageEntry> orderByComparator)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = fetchByC_CN_I_T_VL_First(
			companyId, classNameId, index, type, valueLong, orderByComparator);

		if (jsonStorageEntry != null) {
			return jsonStorageEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", index=");
		sb.append(index);

		sb.append(", type=");
		sb.append(type);

		sb.append(", valueLong=");
		sb.append(valueLong);

		sb.append("}");

		throw new NoSuchJSONStorageEntryException(sb.toString());
	}

	/**
	 * Returns the first json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry fetchByC_CN_I_T_VL_First(
		long companyId, long classNameId, int index, int type, long valueLong,
		OrderByComparator<JSONStorageEntry> orderByComparator) {

		List<JSONStorageEntry> list = findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry findByC_CN_I_T_VL_Last(
			long companyId, long classNameId, int index, int type,
			long valueLong,
			OrderByComparator<JSONStorageEntry> orderByComparator)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = fetchByC_CN_I_T_VL_Last(
			companyId, classNameId, index, type, valueLong, orderByComparator);

		if (jsonStorageEntry != null) {
			return jsonStorageEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", index=");
		sb.append(index);

		sb.append(", type=");
		sb.append(type);

		sb.append(", valueLong=");
		sb.append(valueLong);

		sb.append("}");

		throw new NoSuchJSONStorageEntryException(sb.toString());
	}

	/**
	 * Returns the last json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry fetchByC_CN_I_T_VL_Last(
		long companyId, long classNameId, int index, int type, long valueLong,
		OrderByComparator<JSONStorageEntry> orderByComparator) {

		int count = countByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong);

		if (count == 0) {
			return null;
		}

		List<JSONStorageEntry> list = findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the json storage entries before and after the current json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param jsonStorageEntryId the primary key of the current json storage entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json storage entry
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	@Override
	public JSONStorageEntry[] findByC_CN_I_T_VL_PrevAndNext(
			long jsonStorageEntryId, long companyId, long classNameId,
			int index, int type, long valueLong,
			OrderByComparator<JSONStorageEntry> orderByComparator)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = findByPrimaryKey(
			jsonStorageEntryId);

		Session session = null;

		try {
			session = openSession();

			JSONStorageEntry[] array = new JSONStorageEntryImpl[3];

			array[0] = getByC_CN_I_T_VL_PrevAndNext(
				session, jsonStorageEntry, companyId, classNameId, index, type,
				valueLong, orderByComparator, true);

			array[1] = jsonStorageEntry;

			array[2] = getByC_CN_I_T_VL_PrevAndNext(
				session, jsonStorageEntry, companyId, classNameId, index, type,
				valueLong, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected JSONStorageEntry getByC_CN_I_T_VL_PrevAndNext(
		Session session, JSONStorageEntry jsonStorageEntry, long companyId,
		long classNameId, int index, int type, long valueLong,
		OrderByComparator<JSONStorageEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_JSONSTORAGEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_CN_I_T_VL_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_CN_I_T_VL_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_CN_I_T_VL_INDEX_2);

		sb.append(_FINDER_COLUMN_C_CN_I_T_VL_TYPE_2);

		sb.append(_FINDER_COLUMN_C_CN_I_T_VL_VALUELONG_2);

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
			sb.append(JSONStorageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(classNameId);

		queryPos.add(index);

		queryPos.add(type);

		queryPos.add(valueLong);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						jsonStorageEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JSONStorageEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 */
	@Override
	public void removeByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong) {

		for (JSONStorageEntry jsonStorageEntry :
				findByC_CN_I_T_VL(
					companyId, classNameId, index, type, valueLong,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(jsonStorageEntry);
		}
	}

	/**
	 * Returns the number of json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @return the number of matching json storage entries
	 */
	@Override
	public int countByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStorageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_CN_I_T_VL;

			finderArgs = new Object[] {
				companyId, classNameId, index, type, valueLong
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_JSONSTORAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_CN_I_T_VL_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_CN_I_T_VL_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_CN_I_T_VL_INDEX_2);

			sb.append(_FINDER_COLUMN_C_CN_I_T_VL_TYPE_2);

			sb.append(_FINDER_COLUMN_C_CN_I_T_VL_VALUELONG_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				queryPos.add(index);

				queryPos.add(type);

				queryPos.add(valueLong);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_CN_I_T_VL_COMPANYID_2 =
		"jsonStorageEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_I_T_VL_CLASSNAMEID_2 =
		"jsonStorageEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_I_T_VL_INDEX_2 =
		"jsonStorageEntry.index = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_I_T_VL_TYPE_2 =
		"jsonStorageEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_I_T_VL_VALUELONG_2 =
		"jsonStorageEntry.valueLong = ?";

	private FinderPath _finderPathWithPaginationFindByC_CN_K_T_VL;
	private FinderPath _finderPathWithoutPaginationFindByC_CN_K_T_VL;
	private FinderPath _finderPathCountByC_CN_K_T_VL;

	/**
	 * Returns all the json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @return the matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type,
		long valueLong) {

		return findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @return the range of matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end) {

		return findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, start, end, null);
	}

	/**
	 * Returns an ordered range of all the json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end,
		OrderByComparator<JSONStorageEntry> orderByComparator) {

		return findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end,
		OrderByComparator<JSONStorageEntry> orderByComparator,
		boolean useFinderCache) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStorageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_CN_K_T_VL;
				finderArgs = new Object[] {
					companyId, classNameId, key, type, valueLong
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_CN_K_T_VL;
			finderArgs = new Object[] {
				companyId, classNameId, key, type, valueLong, start, end,
				orderByComparator
			};
		}

		List<JSONStorageEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JSONStorageEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (JSONStorageEntry jsonStorageEntry : list) {
					if ((companyId != jsonStorageEntry.getCompanyId()) ||
						(classNameId != jsonStorageEntry.getClassNameId()) ||
						!key.equals(jsonStorageEntry.getKey()) ||
						(type != jsonStorageEntry.getType()) ||
						(valueLong != jsonStorageEntry.getValueLong())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_JSONSTORAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_CN_K_T_VL_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_CN_K_T_VL_CLASSNAMEID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_CN_K_T_VL_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_CN_K_T_VL_KEY_2);
			}

			sb.append(_FINDER_COLUMN_C_CN_K_T_VL_TYPE_2);

			sb.append(_FINDER_COLUMN_C_CN_K_T_VL_VALUELONG_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JSONStorageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				if (bindKey) {
					queryPos.add(key);
				}

				queryPos.add(type);

				queryPos.add(valueLong);

				list = (List<JSONStorageEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry findByC_CN_K_T_VL_First(
			long companyId, long classNameId, String key, int type,
			long valueLong,
			OrderByComparator<JSONStorageEntry> orderByComparator)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = fetchByC_CN_K_T_VL_First(
			companyId, classNameId, key, type, valueLong, orderByComparator);

		if (jsonStorageEntry != null) {
			return jsonStorageEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", key=");
		sb.append(key);

		sb.append(", type=");
		sb.append(type);

		sb.append(", valueLong=");
		sb.append(valueLong);

		sb.append("}");

		throw new NoSuchJSONStorageEntryException(sb.toString());
	}

	/**
	 * Returns the first json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry fetchByC_CN_K_T_VL_First(
		long companyId, long classNameId, String key, int type, long valueLong,
		OrderByComparator<JSONStorageEntry> orderByComparator) {

		List<JSONStorageEntry> list = findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry findByC_CN_K_T_VL_Last(
			long companyId, long classNameId, String key, int type,
			long valueLong,
			OrderByComparator<JSONStorageEntry> orderByComparator)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = fetchByC_CN_K_T_VL_Last(
			companyId, classNameId, key, type, valueLong, orderByComparator);

		if (jsonStorageEntry != null) {
			return jsonStorageEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", key=");
		sb.append(key);

		sb.append(", type=");
		sb.append(type);

		sb.append(", valueLong=");
		sb.append(valueLong);

		sb.append("}");

		throw new NoSuchJSONStorageEntryException(sb.toString());
	}

	/**
	 * Returns the last json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry fetchByC_CN_K_T_VL_Last(
		long companyId, long classNameId, String key, int type, long valueLong,
		OrderByComparator<JSONStorageEntry> orderByComparator) {

		int count = countByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong);

		if (count == 0) {
			return null;
		}

		List<JSONStorageEntry> list = findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the json storage entries before and after the current json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param jsonStorageEntryId the primary key of the current json storage entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json storage entry
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	@Override
	public JSONStorageEntry[] findByC_CN_K_T_VL_PrevAndNext(
			long jsonStorageEntryId, long companyId, long classNameId,
			String key, int type, long valueLong,
			OrderByComparator<JSONStorageEntry> orderByComparator)
		throws NoSuchJSONStorageEntryException {

		key = Objects.toString(key, "");

		JSONStorageEntry jsonStorageEntry = findByPrimaryKey(
			jsonStorageEntryId);

		Session session = null;

		try {
			session = openSession();

			JSONStorageEntry[] array = new JSONStorageEntryImpl[3];

			array[0] = getByC_CN_K_T_VL_PrevAndNext(
				session, jsonStorageEntry, companyId, classNameId, key, type,
				valueLong, orderByComparator, true);

			array[1] = jsonStorageEntry;

			array[2] = getByC_CN_K_T_VL_PrevAndNext(
				session, jsonStorageEntry, companyId, classNameId, key, type,
				valueLong, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected JSONStorageEntry getByC_CN_K_T_VL_PrevAndNext(
		Session session, JSONStorageEntry jsonStorageEntry, long companyId,
		long classNameId, String key, int type, long valueLong,
		OrderByComparator<JSONStorageEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_JSONSTORAGEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_CN_K_T_VL_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_CN_K_T_VL_CLASSNAMEID_2);

		boolean bindKey = false;

		if (key.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_CN_K_T_VL_KEY_3);
		}
		else {
			bindKey = true;

			sb.append(_FINDER_COLUMN_C_CN_K_T_VL_KEY_2);
		}

		sb.append(_FINDER_COLUMN_C_CN_K_T_VL_TYPE_2);

		sb.append(_FINDER_COLUMN_C_CN_K_T_VL_VALUELONG_2);

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
			sb.append(JSONStorageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(classNameId);

		if (bindKey) {
			queryPos.add(key);
		}

		queryPos.add(type);

		queryPos.add(valueLong);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						jsonStorageEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JSONStorageEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 */
	@Override
	public void removeByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type,
		long valueLong) {

		for (JSONStorageEntry jsonStorageEntry :
				findByC_CN_K_T_VL(
					companyId, classNameId, key, type, valueLong,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(jsonStorageEntry);
		}
	}

	/**
	 * Returns the number of json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @return the number of matching json storage entries
	 */
	@Override
	public int countByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type,
		long valueLong) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStorageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_CN_K_T_VL;

			finderArgs = new Object[] {
				companyId, classNameId, key, type, valueLong
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_JSONSTORAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_CN_K_T_VL_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_CN_K_T_VL_CLASSNAMEID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_CN_K_T_VL_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_CN_K_T_VL_KEY_2);
			}

			sb.append(_FINDER_COLUMN_C_CN_K_T_VL_TYPE_2);

			sb.append(_FINDER_COLUMN_C_CN_K_T_VL_VALUELONG_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				if (bindKey) {
					queryPos.add(key);
				}

				queryPos.add(type);

				queryPos.add(valueLong);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_COMPANYID_2 =
		"jsonStorageEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_CLASSNAMEID_2 =
		"jsonStorageEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_KEY_2 =
		"jsonStorageEntry.key = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_KEY_3 =
		"(jsonStorageEntry.key IS NULL OR jsonStorageEntry.key = '') AND ";

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_TYPE_2 =
		"jsonStorageEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_VALUELONG_2 =
		"jsonStorageEntry.valueLong = ?";

	private FinderPath _finderPathFetchByCN_CPK_P_I_K;
	private FinderPath _finderPathCountByCN_CPK_P_I_K;

	/**
	 * Returns the json storage entry where classNameId = &#63; and classPK = &#63; and parentJSONStorageEntryId = &#63; and index = &#63; and key = &#63; or throws a <code>NoSuchJSONStorageEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStorageEntryId the parent json storage entry ID
	 * @param index the index
	 * @param key the key
	 * @return the matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry findByCN_CPK_P_I_K(
			long classNameId, long classPK, long parentJSONStorageEntryId,
			int index, String key)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = fetchByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStorageEntryId, index, key);

		if (jsonStorageEntry == null) {
			StringBundler sb = new StringBundler(12);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", parentJSONStorageEntryId=");
			sb.append(parentJSONStorageEntryId);

			sb.append(", index=");
			sb.append(index);

			sb.append(", key=");
			sb.append(key);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchJSONStorageEntryException(sb.toString());
		}

		return jsonStorageEntry;
	}

	/**
	 * Returns the json storage entry where classNameId = &#63; and classPK = &#63; and parentJSONStorageEntryId = &#63; and index = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStorageEntryId the parent json storage entry ID
	 * @param index the index
	 * @param key the key
	 * @return the matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry fetchByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStorageEntryId,
		int index, String key) {

		return fetchByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStorageEntryId, index, key, true);
	}

	/**
	 * Returns the json storage entry where classNameId = &#63; and classPK = &#63; and parentJSONStorageEntryId = &#63; and index = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStorageEntryId the parent json storage entry ID
	 * @param index the index
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	@Override
	public JSONStorageEntry fetchByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStorageEntryId,
		int index, String key, boolean useFinderCache) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStorageEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				classNameId, classPK, parentJSONStorageEntryId, index, key
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCN_CPK_P_I_K, finderArgs);
		}

		if (result instanceof JSONStorageEntry) {
			JSONStorageEntry jsonStorageEntry = (JSONStorageEntry)result;

			if ((classNameId != jsonStorageEntry.getClassNameId()) ||
				(classPK != jsonStorageEntry.getClassPK()) ||
				(parentJSONStorageEntryId !=
					jsonStorageEntry.getParentJSONStorageEntryId()) ||
				(index != jsonStorageEntry.getIndex()) ||
				!Objects.equals(key, jsonStorageEntry.getKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_SELECT_JSONSTORAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_CLASSPK_2);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_PARENTJSONSTORAGEENTRYID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_INDEX_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(parentJSONStorageEntryId);

				queryPos.add(index);

				if (bindKey) {
					queryPos.add(key);
				}

				List<JSONStorageEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCN_CPK_P_I_K, finderArgs, list);
					}
				}
				else {
					JSONStorageEntry jsonStorageEntry = list.get(0);

					result = jsonStorageEntry;

					cacheResult(jsonStorageEntry);
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
			return (JSONStorageEntry)result;
		}
	}

	/**
	 * Removes the json storage entry where classNameId = &#63; and classPK = &#63; and parentJSONStorageEntryId = &#63; and index = &#63; and key = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStorageEntryId the parent json storage entry ID
	 * @param index the index
	 * @param key the key
	 * @return the json storage entry that was removed
	 */
	@Override
	public JSONStorageEntry removeByCN_CPK_P_I_K(
			long classNameId, long classPK, long parentJSONStorageEntryId,
			int index, String key)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = findByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStorageEntryId, index, key);

		return remove(jsonStorageEntry);
	}

	/**
	 * Returns the number of json storage entries where classNameId = &#63; and classPK = &#63; and parentJSONStorageEntryId = &#63; and index = &#63; and key = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStorageEntryId the parent json storage entry ID
	 * @param index the index
	 * @param key the key
	 * @return the number of matching json storage entries
	 */
	@Override
	public int countByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStorageEntryId,
		int index, String key) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStorageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCN_CPK_P_I_K;

			finderArgs = new Object[] {
				classNameId, classPK, parentJSONStorageEntryId, index, key
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_JSONSTORAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_CLASSPK_2);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_PARENTJSONSTORAGEENTRYID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_INDEX_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(parentJSONStorageEntryId);

				queryPos.add(index);

				if (bindKey) {
					queryPos.add(key);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_CN_CPK_P_I_K_CLASSNAMEID_2 =
		"jsonStorageEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_P_I_K_CLASSPK_2 =
		"jsonStorageEntry.classPK = ? AND ";

	private static final String
		_FINDER_COLUMN_CN_CPK_P_I_K_PARENTJSONSTORAGEENTRYID_2 =
			"jsonStorageEntry.parentJSONStorageEntryId = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_P_I_K_INDEX_2 =
		"jsonStorageEntry.index = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_P_I_K_KEY_2 =
		"jsonStorageEntry.key = ?";

	private static final String _FINDER_COLUMN_CN_CPK_P_I_K_KEY_3 =
		"(jsonStorageEntry.key IS NULL OR jsonStorageEntry.key = '')";

	public JSONStorageEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("index", "index_");
		dbColumnNames.put("key", "key_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(JSONStorageEntry.class);

		setModelImplClass(JSONStorageEntryImpl.class);
		setModelPKClass(long.class);

		setTable(JSONStorageEntryTable.INSTANCE);
	}

	/**
	 * Caches the json storage entry in the entity cache if it is enabled.
	 *
	 * @param jsonStorageEntry the json storage entry
	 */
	@Override
	public void cacheResult(JSONStorageEntry jsonStorageEntry) {
		if (jsonStorageEntry.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			JSONStorageEntryImpl.class, jsonStorageEntry.getPrimaryKey(),
			jsonStorageEntry);

		finderCache.putResult(
			_finderPathFetchByCN_CPK_P_I_K,
			new Object[] {
				jsonStorageEntry.getClassNameId(),
				jsonStorageEntry.getClassPK(),
				jsonStorageEntry.getParentJSONStorageEntryId(),
				jsonStorageEntry.getIndex(), jsonStorageEntry.getKey()
			},
			jsonStorageEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the json storage entries in the entity cache if it is enabled.
	 *
	 * @param jsonStorageEntries the json storage entries
	 */
	@Override
	public void cacheResult(List<JSONStorageEntry> jsonStorageEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (jsonStorageEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (JSONStorageEntry jsonStorageEntry : jsonStorageEntries) {
			if (jsonStorageEntry.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					JSONStorageEntryImpl.class,
					jsonStorageEntry.getPrimaryKey()) == null) {

				cacheResult(jsonStorageEntry);
			}
		}
	}

	/**
	 * Clears the cache for all json storage entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(JSONStorageEntryImpl.class);

		finderCache.clearCache(JSONStorageEntryImpl.class);
	}

	/**
	 * Clears the cache for the json storage entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JSONStorageEntry jsonStorageEntry) {
		entityCache.removeResult(JSONStorageEntryImpl.class, jsonStorageEntry);
	}

	@Override
	public void clearCache(List<JSONStorageEntry> jsonStorageEntries) {
		for (JSONStorageEntry jsonStorageEntry : jsonStorageEntries) {
			entityCache.removeResult(
				JSONStorageEntryImpl.class, jsonStorageEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(JSONStorageEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(JSONStorageEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		JSONStorageEntryModelImpl jsonStorageEntryModelImpl) {

		Object[] args = new Object[] {
			jsonStorageEntryModelImpl.getClassNameId(),
			jsonStorageEntryModelImpl.getClassPK(),
			jsonStorageEntryModelImpl.getParentJSONStorageEntryId(),
			jsonStorageEntryModelImpl.getIndex(),
			jsonStorageEntryModelImpl.getKey()
		};

		finderCache.putResult(
			_finderPathCountByCN_CPK_P_I_K, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCN_CPK_P_I_K, args, jsonStorageEntryModelImpl);
	}

	/**
	 * Creates a new json storage entry with the primary key. Does not add the json storage entry to the database.
	 *
	 * @param jsonStorageEntryId the primary key for the new json storage entry
	 * @return the new json storage entry
	 */
	@Override
	public JSONStorageEntry create(long jsonStorageEntryId) {
		JSONStorageEntry jsonStorageEntry = new JSONStorageEntryImpl();

		jsonStorageEntry.setNew(true);
		jsonStorageEntry.setPrimaryKey(jsonStorageEntryId);

		jsonStorageEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return jsonStorageEntry;
	}

	/**
	 * Removes the json storage entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param jsonStorageEntryId the primary key of the json storage entry
	 * @return the json storage entry that was removed
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	@Override
	public JSONStorageEntry remove(long jsonStorageEntryId)
		throws NoSuchJSONStorageEntryException {

		return remove((Serializable)jsonStorageEntryId);
	}

	/**
	 * Removes the json storage entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the json storage entry
	 * @return the json storage entry that was removed
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	@Override
	public JSONStorageEntry remove(Serializable primaryKey)
		throws NoSuchJSONStorageEntryException {

		Session session = null;

		try {
			session = openSession();

			JSONStorageEntry jsonStorageEntry = (JSONStorageEntry)session.get(
				JSONStorageEntryImpl.class, primaryKey);

			if (jsonStorageEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchJSONStorageEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(jsonStorageEntry);
		}
		catch (NoSuchJSONStorageEntryException noSuchEntityException) {
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
	protected JSONStorageEntry removeImpl(JSONStorageEntry jsonStorageEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(jsonStorageEntry)) {
				jsonStorageEntry = (JSONStorageEntry)session.get(
					JSONStorageEntryImpl.class,
					jsonStorageEntry.getPrimaryKeyObj());
			}

			if ((jsonStorageEntry != null) &&
				ctPersistenceHelper.isRemove(jsonStorageEntry)) {

				session.delete(jsonStorageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (jsonStorageEntry != null) {
			clearCache(jsonStorageEntry);
		}

		return jsonStorageEntry;
	}

	@Override
	public JSONStorageEntry updateImpl(JSONStorageEntry jsonStorageEntry) {
		boolean isNew = jsonStorageEntry.isNew();

		if (!(jsonStorageEntry instanceof JSONStorageEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(jsonStorageEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					jsonStorageEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in jsonStorageEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom JSONStorageEntry implementation " +
					jsonStorageEntry.getClass());
		}

		JSONStorageEntryModelImpl jsonStorageEntryModelImpl =
			(JSONStorageEntryModelImpl)jsonStorageEntry;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(jsonStorageEntry)) {
				if (!isNew) {
					session.evict(
						JSONStorageEntryImpl.class,
						jsonStorageEntry.getPrimaryKeyObj());
				}

				session.save(jsonStorageEntry);
			}
			else {
				jsonStorageEntry = (JSONStorageEntry)session.merge(
					jsonStorageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (jsonStorageEntry.getCtCollectionId() != 0) {
			if (isNew) {
				jsonStorageEntry.setNew(false);
			}

			jsonStorageEntry.resetOriginalValues();

			return jsonStorageEntry;
		}

		entityCache.putResult(
			JSONStorageEntryImpl.class, jsonStorageEntryModelImpl, false, true);

		cacheUniqueFindersCache(jsonStorageEntryModelImpl);

		if (isNew) {
			jsonStorageEntry.setNew(false);
		}

		jsonStorageEntry.resetOriginalValues();

		return jsonStorageEntry;
	}

	/**
	 * Returns the json storage entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the json storage entry
	 * @return the json storage entry
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	@Override
	public JSONStorageEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchJSONStorageEntryException {

		JSONStorageEntry jsonStorageEntry = fetchByPrimaryKey(primaryKey);

		if (jsonStorageEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchJSONStorageEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return jsonStorageEntry;
	}

	/**
	 * Returns the json storage entry with the primary key or throws a <code>NoSuchJSONStorageEntryException</code> if it could not be found.
	 *
	 * @param jsonStorageEntryId the primary key of the json storage entry
	 * @return the json storage entry
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	@Override
	public JSONStorageEntry findByPrimaryKey(long jsonStorageEntryId)
		throws NoSuchJSONStorageEntryException {

		return findByPrimaryKey((Serializable)jsonStorageEntryId);
	}

	/**
	 * Returns the json storage entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the json storage entry
	 * @return the json storage entry, or <code>null</code> if a json storage entry with the primary key could not be found
	 */
	@Override
	public JSONStorageEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(JSONStorageEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		JSONStorageEntry jsonStorageEntry = null;

		Session session = null;

		try {
			session = openSession();

			jsonStorageEntry = (JSONStorageEntry)session.get(
				JSONStorageEntryImpl.class, primaryKey);

			if (jsonStorageEntry != null) {
				cacheResult(jsonStorageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return jsonStorageEntry;
	}

	/**
	 * Returns the json storage entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param jsonStorageEntryId the primary key of the json storage entry
	 * @return the json storage entry, or <code>null</code> if a json storage entry with the primary key could not be found
	 */
	@Override
	public JSONStorageEntry fetchByPrimaryKey(long jsonStorageEntryId) {
		return fetchByPrimaryKey((Serializable)jsonStorageEntryId);
	}

	@Override
	public Map<Serializable, JSONStorageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(JSONStorageEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, JSONStorageEntry> map =
			new HashMap<Serializable, JSONStorageEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			JSONStorageEntry jsonStorageEntry = fetchByPrimaryKey(primaryKey);

			if (jsonStorageEntry != null) {
				map.put(primaryKey, jsonStorageEntry);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (JSONStorageEntry jsonStorageEntry :
					(List<JSONStorageEntry>)query.list()) {

				map.put(jsonStorageEntry.getPrimaryKeyObj(), jsonStorageEntry);

				cacheResult(jsonStorageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the json storage entries.
	 *
	 * @return the json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the json storage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @return the range of json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the json storage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findAll(
		int start, int end,
		OrderByComparator<JSONStorageEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the json storage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of json storage entries
	 */
	@Override
	public List<JSONStorageEntry> findAll(
		int start, int end,
		OrderByComparator<JSONStorageEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStorageEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<JSONStorageEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JSONStorageEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_JSONSTORAGEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_JSONSTORAGEENTRY;

				sql = sql.concat(JSONStorageEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<JSONStorageEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Removes all the json storage entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JSONStorageEntry jsonStorageEntry : findAll()) {
			remove(jsonStorageEntry);
		}
	}

	/**
	 * Returns the number of json storage entries.
	 *
	 * @return the number of json storage entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStorageEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_JSONSTORAGEENTRY);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return "jsonStorageEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_JSONSTORAGEENTRY;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return JSONStorageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "JSONStorageEntry";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("parentJSONStorageEntryId");
		ctStrictColumnNames.add("index_");
		ctStrictColumnNames.add("key_");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("valueLong");
		ctStrictColumnNames.add("valueString");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("jsonStorageEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {
				"classNameId", "classPK", "parentJSONStorageEntryId", "index_",
				"key_"
			});
	}

	/**
	 * Initializes the json storage entry persistence.
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

		_finderPathWithPaginationFindByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCN_CPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCN_CPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathWithPaginationFindByC_CN_I_T_VL = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CN_I_T_VL",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "classNameId", "index_", "type_", "valueLong"
			},
			true);

		_finderPathWithoutPaginationFindByC_CN_I_T_VL = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CN_I_T_VL",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"companyId", "classNameId", "index_", "type_", "valueLong"
			},
			true);

		_finderPathCountByC_CN_I_T_VL = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CN_I_T_VL",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"companyId", "classNameId", "index_", "type_", "valueLong"
			},
			false);

		_finderPathWithPaginationFindByC_CN_K_T_VL = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CN_K_T_VL",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "classNameId", "key_", "type_", "valueLong"
			},
			true);

		_finderPathWithoutPaginationFindByC_CN_K_T_VL = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CN_K_T_VL",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"companyId", "classNameId", "key_", "type_", "valueLong"
			},
			true);

		_finderPathCountByC_CN_K_T_VL = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CN_K_T_VL",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"companyId", "classNameId", "key_", "type_", "valueLong"
			},
			false);

		_finderPathFetchByCN_CPK_P_I_K = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCN_CPK_P_I_K",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			new String[] {
				"classNameId", "classPK", "parentJSONStorageEntryId", "index_",
				"key_"
			},
			true);

		_finderPathCountByCN_CPK_P_I_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCN_CPK_P_I_K",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			new String[] {
				"classNameId", "classPK", "parentJSONStorageEntryId", "index_",
				"key_"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(JSONStorageEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = JSONStorePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = JSONStorePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = JSONStorePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_JSONSTORAGEENTRY =
		"SELECT jsonStorageEntry FROM JSONStorageEntry jsonStorageEntry";

	private static final String _SQL_SELECT_JSONSTORAGEENTRY_WHERE =
		"SELECT jsonStorageEntry FROM JSONStorageEntry jsonStorageEntry WHERE ";

	private static final String _SQL_COUNT_JSONSTORAGEENTRY =
		"SELECT COUNT(jsonStorageEntry) FROM JSONStorageEntry jsonStorageEntry";

	private static final String _SQL_COUNT_JSONSTORAGEENTRY_WHERE =
		"SELECT COUNT(jsonStorageEntry) FROM JSONStorageEntry jsonStorageEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "jsonStorageEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No JSONStorageEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No JSONStorageEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		JSONStorageEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"index", "key", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private JSONStorageEntryModelArgumentsResolver
		_jsonStorageEntryModelArgumentsResolver;

}