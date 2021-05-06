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

package com.liferay.json.store.service.persistence.impl;

import com.liferay.json.store.exception.NoSuchEntryException;
import com.liferay.json.store.model.JSONStoreEntry;
import com.liferay.json.store.model.JSONStoreEntryTable;
import com.liferay.json.store.model.impl.JSONStoreEntryImpl;
import com.liferay.json.store.model.impl.JSONStoreEntryModelImpl;
import com.liferay.json.store.service.persistence.JSONStoreEntryPersistence;
import com.liferay.json.store.service.persistence.impl.constants.JSONStorePersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
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
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the json store entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Preston Crary
 * @generated
 */
@Component(service = {JSONStoreEntryPersistence.class, BasePersistence.class})
public class JSONStoreEntryPersistenceImpl
	extends BasePersistenceImpl<JSONStoreEntry>
	implements JSONStoreEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>JSONStoreEntryUtil</code> to access the json store entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		JSONStoreEntryImpl.class.getName();

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
	 * Returns all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByCN_CPK(long classNameId, long classPK) {
		return findByCN_CPK(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end) {

		return findByCN_CPK(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return findByCN_CPK(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStoreEntry.class);

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

		List<JSONStoreEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JSONStoreEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (JSONStoreEntry jsonStoreEntry : list) {
					if ((classNameId != jsonStoreEntry.getClassNameId()) ||
						(classPK != jsonStoreEntry.getClassPK())) {

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

			sb.append(_SQL_SELECT_JSONSTOREENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JSONStoreEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<JSONStoreEntry>)QueryUtil.list(
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
	 * Returns the first json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry findByCN_CPK_First(
			long classNameId, long classPK,
			OrderByComparator<JSONStoreEntry> orderByComparator)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = fetchByCN_CPK_First(
			classNameId, classPK, orderByComparator);

		if (jsonStoreEntry != null) {
			return jsonStoreEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry fetchByCN_CPK_First(
		long classNameId, long classPK,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		List<JSONStoreEntry> list = findByCN_CPK(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry findByCN_CPK_Last(
			long classNameId, long classPK,
			OrderByComparator<JSONStoreEntry> orderByComparator)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = fetchByCN_CPK_Last(
			classNameId, classPK, orderByComparator);

		if (jsonStoreEntry != null) {
			return jsonStoreEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry fetchByCN_CPK_Last(
		long classNameId, long classPK,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		int count = countByCN_CPK(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<JSONStoreEntry> list = findByCN_CPK(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the json store entries before and after the current json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param jsonStoreEntryId the primary key of the current json store entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry[] findByCN_CPK_PrevAndNext(
			long jsonStoreEntryId, long classNameId, long classPK,
			OrderByComparator<JSONStoreEntry> orderByComparator)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = findByPrimaryKey(jsonStoreEntryId);

		Session session = null;

		try {
			session = openSession();

			JSONStoreEntry[] array = new JSONStoreEntryImpl[3];

			array[0] = getByCN_CPK_PrevAndNext(
				session, jsonStoreEntry, classNameId, classPK,
				orderByComparator, true);

			array[1] = jsonStoreEntry;

			array[2] = getByCN_CPK_PrevAndNext(
				session, jsonStoreEntry, classNameId, classPK,
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

	protected JSONStoreEntry getByCN_CPK_PrevAndNext(
		Session session, JSONStoreEntry jsonStoreEntry, long classNameId,
		long classPK, OrderByComparator<JSONStoreEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_JSONSTOREENTRY_WHERE);

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
			sb.append(JSONStoreEntryModelImpl.ORDER_BY_JPQL);
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
						jsonStoreEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JSONStoreEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the json store entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByCN_CPK(long classNameId, long classPK) {
		for (JSONStoreEntry jsonStoreEntry :
				findByCN_CPK(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(jsonStoreEntry);
		}
	}

	/**
	 * Returns the number of json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching json store entries
	 */
	@Override
	public int countByCN_CPK(long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStoreEntry.class);

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

			sb.append(_SQL_COUNT_JSONSTOREENTRY_WHERE);

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
		"jsonStoreEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_CLASSPK_2 =
		"jsonStoreEntry.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_CN_I_T_VL;
	private FinderPath _finderPathWithoutPaginationFindByC_CN_I_T_VL;
	private FinderPath _finderPathCountByC_CN_I_T_VL;

	/**
	 * Returns all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @return the matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong) {

		return findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end) {

		return findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, start, end, null);
	}

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end, OrderByComparator<JSONStoreEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStoreEntry.class);

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

		List<JSONStoreEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JSONStoreEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (JSONStoreEntry jsonStoreEntry : list) {
					if ((companyId != jsonStoreEntry.getCompanyId()) ||
						(classNameId != jsonStoreEntry.getClassNameId()) ||
						(index != jsonStoreEntry.getIndex()) ||
						(type != jsonStoreEntry.getType()) ||
						(valueLong != jsonStoreEntry.getValueLong())) {

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

			sb.append(_SQL_SELECT_JSONSTOREENTRY_WHERE);

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
				sb.append(JSONStoreEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<JSONStoreEntry>)QueryUtil.list(
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
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry findByC_CN_I_T_VL_First(
			long companyId, long classNameId, int index, int type,
			long valueLong, OrderByComparator<JSONStoreEntry> orderByComparator)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = fetchByC_CN_I_T_VL_First(
			companyId, classNameId, index, type, valueLong, orderByComparator);

		if (jsonStoreEntry != null) {
			return jsonStoreEntry;
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

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry fetchByC_CN_I_T_VL_First(
		long companyId, long classNameId, int index, int type, long valueLong,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		List<JSONStoreEntry> list = findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry findByC_CN_I_T_VL_Last(
			long companyId, long classNameId, int index, int type,
			long valueLong, OrderByComparator<JSONStoreEntry> orderByComparator)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = fetchByC_CN_I_T_VL_Last(
			companyId, classNameId, index, type, valueLong, orderByComparator);

		if (jsonStoreEntry != null) {
			return jsonStoreEntry;
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

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry fetchByC_CN_I_T_VL_Last(
		long companyId, long classNameId, int index, int type, long valueLong,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		int count = countByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong);

		if (count == 0) {
			return null;
		}

		List<JSONStoreEntry> list = findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the json store entries before and after the current json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param jsonStoreEntryId the primary key of the current json store entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry[] findByC_CN_I_T_VL_PrevAndNext(
			long jsonStoreEntryId, long companyId, long classNameId, int index,
			int type, long valueLong,
			OrderByComparator<JSONStoreEntry> orderByComparator)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = findByPrimaryKey(jsonStoreEntryId);

		Session session = null;

		try {
			session = openSession();

			JSONStoreEntry[] array = new JSONStoreEntryImpl[3];

			array[0] = getByC_CN_I_T_VL_PrevAndNext(
				session, jsonStoreEntry, companyId, classNameId, index, type,
				valueLong, orderByComparator, true);

			array[1] = jsonStoreEntry;

			array[2] = getByC_CN_I_T_VL_PrevAndNext(
				session, jsonStoreEntry, companyId, classNameId, index, type,
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

	protected JSONStoreEntry getByC_CN_I_T_VL_PrevAndNext(
		Session session, JSONStoreEntry jsonStoreEntry, long companyId,
		long classNameId, int index, int type, long valueLong,
		OrderByComparator<JSONStoreEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_JSONSTOREENTRY_WHERE);

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
			sb.append(JSONStoreEntryModelImpl.ORDER_BY_JPQL);
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
						jsonStoreEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JSONStoreEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63; from the database.
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

		for (JSONStoreEntry jsonStoreEntry :
				findByC_CN_I_T_VL(
					companyId, classNameId, index, type, valueLong,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(jsonStoreEntry);
		}
	}

	/**
	 * Returns the number of json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @return the number of matching json store entries
	 */
	@Override
	public int countByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStoreEntry.class);

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

			sb.append(_SQL_COUNT_JSONSTOREENTRY_WHERE);

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
		"jsonStoreEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_I_T_VL_CLASSNAMEID_2 =
		"jsonStoreEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_I_T_VL_INDEX_2 =
		"jsonStoreEntry.index = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_I_T_VL_TYPE_2 =
		"jsonStoreEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_I_T_VL_VALUELONG_2 =
		"jsonStoreEntry.valueLong = ?";

	private FinderPath _finderPathWithPaginationFindByC_CN_K_T_VL;
	private FinderPath _finderPathWithoutPaginationFindByC_CN_K_T_VL;
	private FinderPath _finderPathCountByC_CN_K_T_VL;

	/**
	 * Returns all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @return the matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type,
		long valueLong) {

		return findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end) {

		return findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, start, end, null);
	}

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json store entries
	 */
	@Override
	public List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end, OrderByComparator<JSONStoreEntry> orderByComparator,
		boolean useFinderCache) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStoreEntry.class);

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

		List<JSONStoreEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JSONStoreEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (JSONStoreEntry jsonStoreEntry : list) {
					if ((companyId != jsonStoreEntry.getCompanyId()) ||
						(classNameId != jsonStoreEntry.getClassNameId()) ||
						!key.equals(jsonStoreEntry.getKey()) ||
						(type != jsonStoreEntry.getType()) ||
						(valueLong != jsonStoreEntry.getValueLong())) {

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

			sb.append(_SQL_SELECT_JSONSTOREENTRY_WHERE);

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
				sb.append(JSONStoreEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<JSONStoreEntry>)QueryUtil.list(
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
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry findByC_CN_K_T_VL_First(
			long companyId, long classNameId, String key, int type,
			long valueLong, OrderByComparator<JSONStoreEntry> orderByComparator)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = fetchByC_CN_K_T_VL_First(
			companyId, classNameId, key, type, valueLong, orderByComparator);

		if (jsonStoreEntry != null) {
			return jsonStoreEntry;
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

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry fetchByC_CN_K_T_VL_First(
		long companyId, long classNameId, String key, int type, long valueLong,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		List<JSONStoreEntry> list = findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry findByC_CN_K_T_VL_Last(
			long companyId, long classNameId, String key, int type,
			long valueLong, OrderByComparator<JSONStoreEntry> orderByComparator)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = fetchByC_CN_K_T_VL_Last(
			companyId, classNameId, key, type, valueLong, orderByComparator);

		if (jsonStoreEntry != null) {
			return jsonStoreEntry;
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

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry fetchByC_CN_K_T_VL_Last(
		long companyId, long classNameId, String key, int type, long valueLong,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		int count = countByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong);

		if (count == 0) {
			return null;
		}

		List<JSONStoreEntry> list = findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the json store entries before and after the current json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param jsonStoreEntryId the primary key of the current json store entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry[] findByC_CN_K_T_VL_PrevAndNext(
			long jsonStoreEntryId, long companyId, long classNameId, String key,
			int type, long valueLong,
			OrderByComparator<JSONStoreEntry> orderByComparator)
		throws NoSuchEntryException {

		key = Objects.toString(key, "");

		JSONStoreEntry jsonStoreEntry = findByPrimaryKey(jsonStoreEntryId);

		Session session = null;

		try {
			session = openSession();

			JSONStoreEntry[] array = new JSONStoreEntryImpl[3];

			array[0] = getByC_CN_K_T_VL_PrevAndNext(
				session, jsonStoreEntry, companyId, classNameId, key, type,
				valueLong, orderByComparator, true);

			array[1] = jsonStoreEntry;

			array[2] = getByC_CN_K_T_VL_PrevAndNext(
				session, jsonStoreEntry, companyId, classNameId, key, type,
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

	protected JSONStoreEntry getByC_CN_K_T_VL_PrevAndNext(
		Session session, JSONStoreEntry jsonStoreEntry, long companyId,
		long classNameId, String key, int type, long valueLong,
		OrderByComparator<JSONStoreEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_JSONSTOREENTRY_WHERE);

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
			sb.append(JSONStoreEntryModelImpl.ORDER_BY_JPQL);
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
						jsonStoreEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JSONStoreEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63; from the database.
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

		for (JSONStoreEntry jsonStoreEntry :
				findByC_CN_K_T_VL(
					companyId, classNameId, key, type, valueLong,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(jsonStoreEntry);
		}
	}

	/**
	 * Returns the number of json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @return the number of matching json store entries
	 */
	@Override
	public int countByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type,
		long valueLong) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStoreEntry.class);

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

			sb.append(_SQL_COUNT_JSONSTOREENTRY_WHERE);

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
		"jsonStoreEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_CLASSNAMEID_2 =
		"jsonStoreEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_KEY_2 =
		"jsonStoreEntry.key = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_KEY_3 =
		"(jsonStoreEntry.key IS NULL OR jsonStoreEntry.key = '') AND ";

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_TYPE_2 =
		"jsonStoreEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_C_CN_K_T_VL_VALUELONG_2 =
		"jsonStoreEntry.valueLong = ?";

	private FinderPath _finderPathFetchByCN_CPK_P_I_K;
	private FinderPath _finderPathCountByCN_CPK_P_I_K;

	/**
	 * Returns the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry findByCN_CPK_P_I_K(
			long classNameId, long classPK, long parentJSONStoreEntryId,
			int index, String key)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = fetchByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStoreEntryId, index, key);

		if (jsonStoreEntry == null) {
			StringBundler sb = new StringBundler(12);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", parentJSONStoreEntryId=");
			sb.append(parentJSONStoreEntryId);

			sb.append(", index=");
			sb.append(index);

			sb.append(", key=");
			sb.append(key);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return jsonStoreEntry;
	}

	/**
	 * Returns the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry fetchByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStoreEntryId, int index,
		String key) {

		return fetchByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStoreEntryId, index, key, true);
	}

	/**
	 * Returns the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	@Override
	public JSONStoreEntry fetchByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStoreEntryId, int index,
		String key, boolean useFinderCache) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStoreEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				classNameId, classPK, parentJSONStoreEntryId, index, key
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCN_CPK_P_I_K, finderArgs);
		}

		if (result instanceof JSONStoreEntry) {
			JSONStoreEntry jsonStoreEntry = (JSONStoreEntry)result;

			if ((classNameId != jsonStoreEntry.getClassNameId()) ||
				(classPK != jsonStoreEntry.getClassPK()) ||
				(parentJSONStoreEntryId !=
					jsonStoreEntry.getParentJSONStoreEntryId()) ||
				(index != jsonStoreEntry.getIndex()) ||
				!Objects.equals(key, jsonStoreEntry.getKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_SELECT_JSONSTOREENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_CLASSPK_2);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_PARENTJSONSTOREENTRYID_2);

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

				queryPos.add(parentJSONStoreEntryId);

				queryPos.add(index);

				if (bindKey) {
					queryPos.add(key);
				}

				List<JSONStoreEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCN_CPK_P_I_K, finderArgs, list);
					}
				}
				else {
					JSONStoreEntry jsonStoreEntry = list.get(0);

					result = jsonStoreEntry;

					cacheResult(jsonStoreEntry);
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
			return (JSONStoreEntry)result;
		}
	}

	/**
	 * Removes the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the json store entry that was removed
	 */
	@Override
	public JSONStoreEntry removeByCN_CPK_P_I_K(
			long classNameId, long classPK, long parentJSONStoreEntryId,
			int index, String key)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = findByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStoreEntryId, index, key);

		return remove(jsonStoreEntry);
	}

	/**
	 * Returns the number of json store entries where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the number of matching json store entries
	 */
	@Override
	public int countByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStoreEntryId, int index,
		String key) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStoreEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCN_CPK_P_I_K;

			finderArgs = new Object[] {
				classNameId, classPK, parentJSONStoreEntryId, index, key
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_JSONSTOREENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_CLASSPK_2);

			sb.append(_FINDER_COLUMN_CN_CPK_P_I_K_PARENTJSONSTOREENTRYID_2);

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

				queryPos.add(parentJSONStoreEntryId);

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
		"jsonStoreEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_P_I_K_CLASSPK_2 =
		"jsonStoreEntry.classPK = ? AND ";

	private static final String
		_FINDER_COLUMN_CN_CPK_P_I_K_PARENTJSONSTOREENTRYID_2 =
			"jsonStoreEntry.parentJSONStoreEntryId = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_P_I_K_INDEX_2 =
		"jsonStoreEntry.index = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_P_I_K_KEY_2 =
		"jsonStoreEntry.key = ?";

	private static final String _FINDER_COLUMN_CN_CPK_P_I_K_KEY_3 =
		"(jsonStoreEntry.key IS NULL OR jsonStoreEntry.key = '')";

	public JSONStoreEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("index", "index_");
		dbColumnNames.put("key", "key_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(JSONStoreEntry.class);

		setModelImplClass(JSONStoreEntryImpl.class);
		setModelPKClass(long.class);

		setTable(JSONStoreEntryTable.INSTANCE);
	}

	/**
	 * Caches the json store entry in the entity cache if it is enabled.
	 *
	 * @param jsonStoreEntry the json store entry
	 */
	@Override
	public void cacheResult(JSONStoreEntry jsonStoreEntry) {
		if (jsonStoreEntry.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			JSONStoreEntryImpl.class, jsonStoreEntry.getPrimaryKey(),
			jsonStoreEntry);

		finderCache.putResult(
			_finderPathFetchByCN_CPK_P_I_K,
			new Object[] {
				jsonStoreEntry.getClassNameId(), jsonStoreEntry.getClassPK(),
				jsonStoreEntry.getParentJSONStoreEntryId(),
				jsonStoreEntry.getIndex(), jsonStoreEntry.getKey()
			},
			jsonStoreEntry);
	}

	/**
	 * Caches the json store entries in the entity cache if it is enabled.
	 *
	 * @param jsonStoreEntries the json store entries
	 */
	@Override
	public void cacheResult(List<JSONStoreEntry> jsonStoreEntries) {
		for (JSONStoreEntry jsonStoreEntry : jsonStoreEntries) {
			if (jsonStoreEntry.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					JSONStoreEntryImpl.class, jsonStoreEntry.getPrimaryKey()) ==
						null) {

				cacheResult(jsonStoreEntry);
			}
		}
	}

	/**
	 * Clears the cache for all json store entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(JSONStoreEntryImpl.class);

		finderCache.clearCache(JSONStoreEntryImpl.class);
	}

	/**
	 * Clears the cache for the json store entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JSONStoreEntry jsonStoreEntry) {
		entityCache.removeResult(JSONStoreEntryImpl.class, jsonStoreEntry);
	}

	@Override
	public void clearCache(List<JSONStoreEntry> jsonStoreEntries) {
		for (JSONStoreEntry jsonStoreEntry : jsonStoreEntries) {
			entityCache.removeResult(JSONStoreEntryImpl.class, jsonStoreEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(JSONStoreEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(JSONStoreEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		JSONStoreEntryModelImpl jsonStoreEntryModelImpl) {

		Object[] args = new Object[] {
			jsonStoreEntryModelImpl.getClassNameId(),
			jsonStoreEntryModelImpl.getClassPK(),
			jsonStoreEntryModelImpl.getParentJSONStoreEntryId(),
			jsonStoreEntryModelImpl.getIndex(), jsonStoreEntryModelImpl.getKey()
		};

		finderCache.putResult(
			_finderPathCountByCN_CPK_P_I_K, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCN_CPK_P_I_K, args, jsonStoreEntryModelImpl);
	}

	/**
	 * Creates a new json store entry with the primary key. Does not add the json store entry to the database.
	 *
	 * @param jsonStoreEntryId the primary key for the new json store entry
	 * @return the new json store entry
	 */
	@Override
	public JSONStoreEntry create(long jsonStoreEntryId) {
		JSONStoreEntry jsonStoreEntry = new JSONStoreEntryImpl();

		jsonStoreEntry.setNew(true);
		jsonStoreEntry.setPrimaryKey(jsonStoreEntryId);

		jsonStoreEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return jsonStoreEntry;
	}

	/**
	 * Removes the json store entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry that was removed
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry remove(long jsonStoreEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)jsonStoreEntryId);
	}

	/**
	 * Removes the json store entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the json store entry
	 * @return the json store entry that was removed
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			JSONStoreEntry jsonStoreEntry = (JSONStoreEntry)session.get(
				JSONStoreEntryImpl.class, primaryKey);

			if (jsonStoreEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(jsonStoreEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected JSONStoreEntry removeImpl(JSONStoreEntry jsonStoreEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(jsonStoreEntry)) {
				jsonStoreEntry = (JSONStoreEntry)session.get(
					JSONStoreEntryImpl.class,
					jsonStoreEntry.getPrimaryKeyObj());
			}

			if ((jsonStoreEntry != null) &&
				ctPersistenceHelper.isRemove(jsonStoreEntry)) {

				session.delete(jsonStoreEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (jsonStoreEntry != null) {
			clearCache(jsonStoreEntry);
		}

		return jsonStoreEntry;
	}

	@Override
	public JSONStoreEntry updateImpl(JSONStoreEntry jsonStoreEntry) {
		boolean isNew = jsonStoreEntry.isNew();

		if (!(jsonStoreEntry instanceof JSONStoreEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(jsonStoreEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					jsonStoreEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in jsonStoreEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom JSONStoreEntry implementation " +
					jsonStoreEntry.getClass());
		}

		JSONStoreEntryModelImpl jsonStoreEntryModelImpl =
			(JSONStoreEntryModelImpl)jsonStoreEntry;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(jsonStoreEntry)) {
				if (!isNew) {
					session.evict(
						JSONStoreEntryImpl.class,
						jsonStoreEntry.getPrimaryKeyObj());
				}

				session.save(jsonStoreEntry);
			}
			else {
				jsonStoreEntry = (JSONStoreEntry)session.merge(jsonStoreEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (jsonStoreEntry.getCtCollectionId() != 0) {
			if (isNew) {
				jsonStoreEntry.setNew(false);
			}

			jsonStoreEntry.resetOriginalValues();

			return jsonStoreEntry;
		}

		entityCache.putResult(
			JSONStoreEntryImpl.class, jsonStoreEntryModelImpl, false, true);

		cacheUniqueFindersCache(jsonStoreEntryModelImpl);

		if (isNew) {
			jsonStoreEntry.setNew(false);
		}

		jsonStoreEntry.resetOriginalValues();

		return jsonStoreEntry;
	}

	/**
	 * Returns the json store entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the json store entry
	 * @return the json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		JSONStoreEntry jsonStoreEntry = fetchByPrimaryKey(primaryKey);

		if (jsonStoreEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return jsonStoreEntry;
	}

	/**
	 * Returns the json store entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry findByPrimaryKey(long jsonStoreEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)jsonStoreEntryId);
	}

	/**
	 * Returns the json store entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the json store entry
	 * @return the json store entry, or <code>null</code> if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(JSONStoreEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		JSONStoreEntry jsonStoreEntry = null;

		Session session = null;

		try {
			session = openSession();

			jsonStoreEntry = (JSONStoreEntry)session.get(
				JSONStoreEntryImpl.class, primaryKey);

			if (jsonStoreEntry != null) {
				cacheResult(jsonStoreEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return jsonStoreEntry;
	}

	/**
	 * Returns the json store entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry, or <code>null</code> if a json store entry with the primary key could not be found
	 */
	@Override
	public JSONStoreEntry fetchByPrimaryKey(long jsonStoreEntryId) {
		return fetchByPrimaryKey((Serializable)jsonStoreEntryId);
	}

	@Override
	public Map<Serializable, JSONStoreEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(JSONStoreEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, JSONStoreEntry> map =
			new HashMap<Serializable, JSONStoreEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			JSONStoreEntry jsonStoreEntry = fetchByPrimaryKey(primaryKey);

			if (jsonStoreEntry != null) {
				map.put(primaryKey, jsonStoreEntry);
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

			for (JSONStoreEntry jsonStoreEntry :
					(List<JSONStoreEntry>)query.list()) {

				map.put(jsonStoreEntry.getPrimaryKeyObj(), jsonStoreEntry);

				cacheResult(jsonStoreEntry);
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
	 * Returns all the json store entries.
	 *
	 * @return the json store entries
	 */
	@Override
	public List<JSONStoreEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the json store entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of json store entries
	 */
	@Override
	public List<JSONStoreEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the json store entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of json store entries
	 */
	@Override
	public List<JSONStoreEntry> findAll(
		int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the json store entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of json store entries
	 */
	@Override
	public List<JSONStoreEntry> findAll(
		int start, int end, OrderByComparator<JSONStoreEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStoreEntry.class);

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

		List<JSONStoreEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JSONStoreEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_JSONSTOREENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_JSONSTOREENTRY;

				sql = sql.concat(JSONStoreEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<JSONStoreEntry>)QueryUtil.list(
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
	 * Removes all the json store entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JSONStoreEntry jsonStoreEntry : findAll()) {
			remove(jsonStoreEntry);
		}
	}

	/**
	 * Returns the number of json store entries.
	 *
	 * @return the number of json store entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JSONStoreEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_JSONSTOREENTRY);

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
		return "jsonStoreEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_JSONSTOREENTRY;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return JSONStoreEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "JSONStoreEntry";
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
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("parentJSONStoreEntryId");
		ctStrictColumnNames.add("index_");
		ctStrictColumnNames.add("key_");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("valueLong");
		ctStrictColumnNames.add("valueString");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("jsonStoreEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {
				"classNameId", "classPK", "parentJSONStoreEntryId", "index_",
				"key_"
			});
	}

	/**
	 * Initializes the json store entry persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new JSONStoreEntryModelArgumentsResolver(),
			new HashMapDictionary<>());

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
				"classNameId", "classPK", "parentJSONStoreEntryId", "index_",
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
				"classNameId", "classPK", "parentJSONStoreEntryId", "index_",
				"key_"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(JSONStoreEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
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

	private BundleContext _bundleContext;

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_JSONSTOREENTRY =
		"SELECT jsonStoreEntry FROM JSONStoreEntry jsonStoreEntry";

	private static final String _SQL_SELECT_JSONSTOREENTRY_WHERE =
		"SELECT jsonStoreEntry FROM JSONStoreEntry jsonStoreEntry WHERE ";

	private static final String _SQL_COUNT_JSONSTOREENTRY =
		"SELECT COUNT(jsonStoreEntry) FROM JSONStoreEntry jsonStoreEntry";

	private static final String _SQL_COUNT_JSONSTOREENTRY_WHERE =
		"SELECT COUNT(jsonStoreEntry) FROM JSONStoreEntry jsonStoreEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "jsonStoreEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No JSONStoreEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No JSONStoreEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		JSONStoreEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"index", "key", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class JSONStoreEntryModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			JSONStoreEntryModelImpl jsonStoreEntryModelImpl =
				(JSONStoreEntryModelImpl)baseModel;

			long columnBitmask = jsonStoreEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					jsonStoreEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						jsonStoreEntryModelImpl.getColumnBitmask(columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					jsonStoreEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return JSONStoreEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return JSONStoreEntryTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			JSONStoreEntryModelImpl jsonStoreEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						jsonStoreEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = jsonStoreEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			orderByColumnsBitmask |= JSONStoreEntryModelImpl.getColumnBitmask(
				"index_");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}