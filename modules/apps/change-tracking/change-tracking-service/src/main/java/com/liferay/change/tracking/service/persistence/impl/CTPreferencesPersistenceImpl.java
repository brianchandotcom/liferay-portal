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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchPreferencesException;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTPreferencesTable;
import com.liferay.change.tracking.model.impl.CTPreferencesImpl;
import com.liferay.change.tracking.model.impl.CTPreferencesModelImpl;
import com.liferay.change.tracking.service.persistence.CTPreferencesPersistence;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ct preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {CTPreferencesPersistence.class, BasePersistence.class})
public class CTPreferencesPersistenceImpl
	extends BasePersistenceImpl<CTPreferences>
	implements CTPreferencesPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTPreferencesUtil</code> to access the ct preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTPreferencesImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCtCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByCtCollectionId;
	private FinderPath _finderPathCountByCtCollectionId;

	/**
	 * Returns all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct preferenceses
	 */
	@Override
	public List<CTPreferences> findByCtCollectionId(long ctCollectionId) {
		return findByCtCollectionId(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @return the range of matching ct preferenceses
	 */
	@Override
	public List<CTPreferences> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return findByCtCollectionId(ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct preferenceses
	 */
	@Override
	public List<CTPreferences> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTPreferences> orderByComparator) {

		return findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct preferenceses
	 */
	@Override
	public List<CTPreferences> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTPreferences> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCtCollectionId;
				finderArgs = new Object[] {ctCollectionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCtCollectionId;
			finderArgs = new Object[] {
				ctCollectionId, start, end, orderByComparator
			};
		}

		List<CTPreferences> list = null;

		if (useFinderCache) {
			list = (List<CTPreferences>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTPreferences ctPreferences : list) {
					if (ctCollectionId != ctPreferences.getCtCollectionId()) {
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

			sb.append(_SQL_SELECT_CTPREFERENCES_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTPreferencesModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				list = (List<CTPreferences>)QueryUtil.list(
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
	 * Returns the first ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences findByCtCollectionId_First(
			long ctCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws NoSuchPreferencesException {

		CTPreferences ctPreferences = fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);

		if (ctPreferences != null) {
			return ctPreferences;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchPreferencesException(sb.toString());
	}

	/**
	 * Returns the first ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences fetchByCtCollectionId_First(
		long ctCollectionId,
		OrderByComparator<CTPreferences> orderByComparator) {

		List<CTPreferences> list = findByCtCollectionId(
			ctCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences findByCtCollectionId_Last(
			long ctCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws NoSuchPreferencesException {

		CTPreferences ctPreferences = fetchByCtCollectionId_Last(
			ctCollectionId, orderByComparator);

		if (ctPreferences != null) {
			return ctPreferences;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchPreferencesException(sb.toString());
	}

	/**
	 * Returns the last ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences fetchByCtCollectionId_Last(
		long ctCollectionId,
		OrderByComparator<CTPreferences> orderByComparator) {

		int count = countByCtCollectionId(ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<CTPreferences> list = findByCtCollectionId(
			ctCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct preferenceses before and after the current ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctPreferencesId the primary key of the current ct preferences
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct preferences
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	@Override
	public CTPreferences[] findByCtCollectionId_PrevAndNext(
			long ctPreferencesId, long ctCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws NoSuchPreferencesException {

		CTPreferences ctPreferences = findByPrimaryKey(ctPreferencesId);

		Session session = null;

		try {
			session = openSession();

			CTPreferences[] array = new CTPreferencesImpl[3];

			array[0] = getByCtCollectionId_PrevAndNext(
				session, ctPreferences, ctCollectionId, orderByComparator,
				true);

			array[1] = ctPreferences;

			array[2] = getByCtCollectionId_PrevAndNext(
				session, ctPreferences, ctCollectionId, orderByComparator,
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

	protected CTPreferences getByCtCollectionId_PrevAndNext(
		Session session, CTPreferences ctPreferences, long ctCollectionId,
		OrderByComparator<CTPreferences> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CTPREFERENCES_WHERE);

		sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

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
			sb.append(CTPreferencesModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ctPreferences)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTPreferences> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct preferenceses where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByCtCollectionId(long ctCollectionId) {
		for (CTPreferences ctPreferences :
				findByCtCollectionId(
					ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctPreferences);
		}
	}

	/**
	 * Returns the number of ct preferenceses where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct preferenceses
	 */
	@Override
	public int countByCtCollectionId(long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByCtCollectionId;

		Object[] finderArgs = new Object[] {ctCollectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTPREFERENCES_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

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

	private static final String _FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2 =
		"ctPreferences.ctCollectionId = ?";

	private FinderPath _finderPathWithPaginationFindByPreviousCtCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByPreviousCtCollectionId;
	private FinderPath _finderPathCountByPreviousCtCollectionId;

	/**
	 * Returns all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @return the matching ct preferenceses
	 */
	@Override
	public List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId) {

		return findByPreviousCtCollectionId(
			previousCtCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @return the range of matching ct preferenceses
	 */
	@Override
	public List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId, int start, int end) {

		return findByPreviousCtCollectionId(
			previousCtCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct preferenceses
	 */
	@Override
	public List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId, int start, int end,
		OrderByComparator<CTPreferences> orderByComparator) {

		return findByPreviousCtCollectionId(
			previousCtCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct preferenceses
	 */
	@Override
	public List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId, int start, int end,
		OrderByComparator<CTPreferences> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByPreviousCtCollectionId;
				finderArgs = new Object[] {previousCtCollectionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByPreviousCtCollectionId;
			finderArgs = new Object[] {
				previousCtCollectionId, start, end, orderByComparator
			};
		}

		List<CTPreferences> list = null;

		if (useFinderCache) {
			list = (List<CTPreferences>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTPreferences ctPreferences : list) {
					if (previousCtCollectionId !=
							ctPreferences.getPreviousCtCollectionId()) {

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

			sb.append(_SQL_SELECT_CTPREFERENCES_WHERE);

			sb.append(
				_FINDER_COLUMN_PREVIOUSCTCOLLECTIONID_PREVIOUSCTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTPreferencesModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(previousCtCollectionId);

				list = (List<CTPreferences>)QueryUtil.list(
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
	 * Returns the first ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences findByPreviousCtCollectionId_First(
			long previousCtCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws NoSuchPreferencesException {

		CTPreferences ctPreferences = fetchByPreviousCtCollectionId_First(
			previousCtCollectionId, orderByComparator);

		if (ctPreferences != null) {
			return ctPreferences;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("previousCtCollectionId=");
		sb.append(previousCtCollectionId);

		sb.append("}");

		throw new NoSuchPreferencesException(sb.toString());
	}

	/**
	 * Returns the first ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences fetchByPreviousCtCollectionId_First(
		long previousCtCollectionId,
		OrderByComparator<CTPreferences> orderByComparator) {

		List<CTPreferences> list = findByPreviousCtCollectionId(
			previousCtCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences findByPreviousCtCollectionId_Last(
			long previousCtCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws NoSuchPreferencesException {

		CTPreferences ctPreferences = fetchByPreviousCtCollectionId_Last(
			previousCtCollectionId, orderByComparator);

		if (ctPreferences != null) {
			return ctPreferences;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("previousCtCollectionId=");
		sb.append(previousCtCollectionId);

		sb.append("}");

		throw new NoSuchPreferencesException(sb.toString());
	}

	/**
	 * Returns the last ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences fetchByPreviousCtCollectionId_Last(
		long previousCtCollectionId,
		OrderByComparator<CTPreferences> orderByComparator) {

		int count = countByPreviousCtCollectionId(previousCtCollectionId);

		if (count == 0) {
			return null;
		}

		List<CTPreferences> list = findByPreviousCtCollectionId(
			previousCtCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct preferenceses before and after the current ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param ctPreferencesId the primary key of the current ct preferences
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct preferences
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	@Override
	public CTPreferences[] findByPreviousCtCollectionId_PrevAndNext(
			long ctPreferencesId, long previousCtCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws NoSuchPreferencesException {

		CTPreferences ctPreferences = findByPrimaryKey(ctPreferencesId);

		Session session = null;

		try {
			session = openSession();

			CTPreferences[] array = new CTPreferencesImpl[3];

			array[0] = getByPreviousCtCollectionId_PrevAndNext(
				session, ctPreferences, previousCtCollectionId,
				orderByComparator, true);

			array[1] = ctPreferences;

			array[2] = getByPreviousCtCollectionId_PrevAndNext(
				session, ctPreferences, previousCtCollectionId,
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

	protected CTPreferences getByPreviousCtCollectionId_PrevAndNext(
		Session session, CTPreferences ctPreferences,
		long previousCtCollectionId,
		OrderByComparator<CTPreferences> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CTPREFERENCES_WHERE);

		sb.append(
			_FINDER_COLUMN_PREVIOUSCTCOLLECTIONID_PREVIOUSCTCOLLECTIONID_2);

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
			sb.append(CTPreferencesModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(previousCtCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ctPreferences)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTPreferences> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct preferenceses where previousCtCollectionId = &#63; from the database.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 */
	@Override
	public void removeByPreviousCtCollectionId(long previousCtCollectionId) {
		for (CTPreferences ctPreferences :
				findByPreviousCtCollectionId(
					previousCtCollectionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ctPreferences);
		}
	}

	/**
	 * Returns the number of ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @return the number of matching ct preferenceses
	 */
	@Override
	public int countByPreviousCtCollectionId(long previousCtCollectionId) {
		FinderPath finderPath = _finderPathCountByPreviousCtCollectionId;

		Object[] finderArgs = new Object[] {previousCtCollectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTPREFERENCES_WHERE);

			sb.append(
				_FINDER_COLUMN_PREVIOUSCTCOLLECTIONID_PREVIOUSCTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(previousCtCollectionId);

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
		_FINDER_COLUMN_PREVIOUSCTCOLLECTIONID_PREVIOUSCTCOLLECTIONID_2 =
			"ctPreferences.previousCtCollectionId = ?";

	private FinderPath _finderPathFetchByC_U;
	private FinderPath _finderPathCountByC_U;

	/**
	 * Returns the ct preferences where companyId = &#63; and userId = &#63; or throws a <code>NoSuchPreferencesException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences findByC_U(long companyId, long userId)
		throws NoSuchPreferencesException {

		CTPreferences ctPreferences = fetchByC_U(companyId, userId);

		if (ctPreferences == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", userId=");
			sb.append(userId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPreferencesException(sb.toString());
		}

		return ctPreferences;
	}

	/**
	 * Returns the ct preferences where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences fetchByC_U(long companyId, long userId) {
		return fetchByC_U(companyId, userId, true);
	}

	/**
	 * Returns the ct preferences where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	@Override
	public CTPreferences fetchByC_U(
		long companyId, long userId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, userId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_U, finderArgs);
		}

		if (result instanceof CTPreferences) {
			CTPreferences ctPreferences = (CTPreferences)result;

			if ((companyId != ctPreferences.getCompanyId()) ||
				(userId != ctPreferences.getUserId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CTPREFERENCES_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

				List<CTPreferences> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_U, finderArgs, list);
					}
				}
				else {
					CTPreferences ctPreferences = list.get(0);

					result = ctPreferences;

					cacheResult(ctPreferences);
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
			return (CTPreferences)result;
		}
	}

	/**
	 * Removes the ct preferences where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the ct preferences that was removed
	 */
	@Override
	public CTPreferences removeByC_U(long companyId, long userId)
		throws NoSuchPreferencesException {

		CTPreferences ctPreferences = findByC_U(companyId, userId);

		return remove(ctPreferences);
	}

	/**
	 * Returns the number of ct preferenceses where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching ct preferenceses
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		FinderPath finderPath = _finderPathCountByC_U;

		Object[] finderArgs = new Object[] {companyId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CTPREFERENCES_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 =
		"ctPreferences.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_USERID_2 =
		"ctPreferences.userId = ?";

	public CTPreferencesPersistenceImpl() {
		setModelClass(CTPreferences.class);

		setModelImplClass(CTPreferencesImpl.class);
		setModelPKClass(long.class);

		setTable(CTPreferencesTable.INSTANCE);
	}

	/**
	 * Caches the ct preferences in the entity cache if it is enabled.
	 *
	 * @param ctPreferences the ct preferences
	 */
	@Override
	public void cacheResult(CTPreferences ctPreferences) {
		entityCache.putResult(
			CTPreferencesImpl.class, ctPreferences.getPrimaryKey(),
			ctPreferences);

		finderCache.putResult(
			_finderPathFetchByC_U,
			new Object[] {
				ctPreferences.getCompanyId(), ctPreferences.getUserId()
			},
			ctPreferences);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ct preferenceses in the entity cache if it is enabled.
	 *
	 * @param ctPreferenceses the ct preferenceses
	 */
	@Override
	public void cacheResult(List<CTPreferences> ctPreferenceses) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ctPreferenceses.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CTPreferences ctPreferences : ctPreferenceses) {
			if (entityCache.getResult(
					CTPreferencesImpl.class, ctPreferences.getPrimaryKey()) ==
						null) {

				cacheResult(ctPreferences);
			}
		}
	}

	/**
	 * Clears the cache for all ct preferenceses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTPreferencesImpl.class);

		finderCache.clearCache(CTPreferencesImpl.class);
	}

	/**
	 * Clears the cache for the ct preferences.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTPreferences ctPreferences) {
		entityCache.removeResult(CTPreferencesImpl.class, ctPreferences);
	}

	@Override
	public void clearCache(List<CTPreferences> ctPreferenceses) {
		for (CTPreferences ctPreferences : ctPreferenceses) {
			entityCache.removeResult(CTPreferencesImpl.class, ctPreferences);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CTPreferencesImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CTPreferencesImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CTPreferencesModelImpl ctPreferencesModelImpl) {

		Object[] args = new Object[] {
			ctPreferencesModelImpl.getCompanyId(),
			ctPreferencesModelImpl.getUserId()
		};

		finderCache.putResult(_finderPathCountByC_U, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_U, args, ctPreferencesModelImpl);
	}

	/**
	 * Creates a new ct preferences with the primary key. Does not add the ct preferences to the database.
	 *
	 * @param ctPreferencesId the primary key for the new ct preferences
	 * @return the new ct preferences
	 */
	@Override
	public CTPreferences create(long ctPreferencesId) {
		CTPreferences ctPreferences = new CTPreferencesImpl();

		ctPreferences.setNew(true);
		ctPreferences.setPrimaryKey(ctPreferencesId);

		ctPreferences.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctPreferences;
	}

	/**
	 * Removes the ct preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences that was removed
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	@Override
	public CTPreferences remove(long ctPreferencesId)
		throws NoSuchPreferencesException {

		return remove((Serializable)ctPreferencesId);
	}

	/**
	 * Removes the ct preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct preferences
	 * @return the ct preferences that was removed
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	@Override
	public CTPreferences remove(Serializable primaryKey)
		throws NoSuchPreferencesException {

		Session session = null;

		try {
			session = openSession();

			CTPreferences ctPreferences = (CTPreferences)session.get(
				CTPreferencesImpl.class, primaryKey);

			if (ctPreferences == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPreferencesException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctPreferences);
		}
		catch (NoSuchPreferencesException noSuchEntityException) {
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
	protected CTPreferences removeImpl(CTPreferences ctPreferences) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctPreferences)) {
				ctPreferences = (CTPreferences)session.get(
					CTPreferencesImpl.class, ctPreferences.getPrimaryKeyObj());
			}

			if (ctPreferences != null) {
				session.delete(ctPreferences);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctPreferences != null) {
			clearCache(ctPreferences);
		}

		return ctPreferences;
	}

	@Override
	public CTPreferences updateImpl(CTPreferences ctPreferences) {
		boolean isNew = ctPreferences.isNew();

		if (!(ctPreferences instanceof CTPreferencesModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctPreferences.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ctPreferences);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctPreferences proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTPreferences implementation " +
					ctPreferences.getClass());
		}

		CTPreferencesModelImpl ctPreferencesModelImpl =
			(CTPreferencesModelImpl)ctPreferences;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ctPreferences);
			}
			else {
				ctPreferences = (CTPreferences)session.merge(ctPreferences);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CTPreferencesImpl.class, ctPreferencesModelImpl, false, true);

		cacheUniqueFindersCache(ctPreferencesModelImpl);

		if (isNew) {
			ctPreferences.setNew(false);
		}

		ctPreferences.resetOriginalValues();

		return ctPreferences;
	}

	/**
	 * Returns the ct preferences with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct preferences
	 * @return the ct preferences
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	@Override
	public CTPreferences findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPreferencesException {

		CTPreferences ctPreferences = fetchByPrimaryKey(primaryKey);

		if (ctPreferences == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPreferencesException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctPreferences;
	}

	/**
	 * Returns the ct preferences with the primary key or throws a <code>NoSuchPreferencesException</code> if it could not be found.
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	@Override
	public CTPreferences findByPrimaryKey(long ctPreferencesId)
		throws NoSuchPreferencesException {

		return findByPrimaryKey((Serializable)ctPreferencesId);
	}

	/**
	 * Returns the ct preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences, or <code>null</code> if a ct preferences with the primary key could not be found
	 */
	@Override
	public CTPreferences fetchByPrimaryKey(long ctPreferencesId) {
		return fetchByPrimaryKey((Serializable)ctPreferencesId);
	}

	/**
	 * Returns all the ct preferenceses.
	 *
	 * @return the ct preferenceses
	 */
	@Override
	public List<CTPreferences> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @return the range of ct preferenceses
	 */
	@Override
	public List<CTPreferences> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct preferenceses
	 */
	@Override
	public List<CTPreferences> findAll(
		int start, int end,
		OrderByComparator<CTPreferences> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct preferenceses
	 */
	@Override
	public List<CTPreferences> findAll(
		int start, int end, OrderByComparator<CTPreferences> orderByComparator,
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

		List<CTPreferences> list = null;

		if (useFinderCache) {
			list = (List<CTPreferences>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CTPREFERENCES);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CTPREFERENCES;

				sql = sql.concat(CTPreferencesModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CTPreferences>)QueryUtil.list(
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
	 * Removes all the ct preferenceses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTPreferences ctPreferences : findAll()) {
			remove(ctPreferences);
		}
	}

	/**
	 * Returns the number of ct preferenceses.
	 *
	 * @return the number of ct preferenceses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CTPREFERENCES);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ctPreferencesId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTPREFERENCES;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTPreferencesModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct preferences persistence.
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

		_finderPathWithPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCtCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ctCollectionId"}, true);

		_finderPathWithoutPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, true);

		_finderPathCountByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, false);

		_finderPathWithPaginationFindByPreviousCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPreviousCtCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"previousCtCollectionId"}, true);

		_finderPathWithoutPaginationFindByPreviousCtCollectionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByPreviousCtCollectionId",
				new String[] {Long.class.getName()},
				new String[] {"previousCtCollectionId"}, true);

		_finderPathCountByPreviousCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPreviousCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"previousCtCollectionId"}, false);

		_finderPathFetchByC_U = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, true);

		_finderPathCountByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTPreferencesImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CTPREFERENCES =
		"SELECT ctPreferences FROM CTPreferences ctPreferences";

	private static final String _SQL_SELECT_CTPREFERENCES_WHERE =
		"SELECT ctPreferences FROM CTPreferences ctPreferences WHERE ";

	private static final String _SQL_COUNT_CTPREFERENCES =
		"SELECT COUNT(ctPreferences) FROM CTPreferences ctPreferences";

	private static final String _SQL_COUNT_CTPREFERENCES_WHERE =
		"SELECT COUNT(ctPreferences) FROM CTPreferences ctPreferences WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctPreferences.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTPreferences exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTPreferences exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTPreferencesPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CTPreferencesModelArgumentsResolver
		_ctPreferencesModelArgumentsResolver;

}