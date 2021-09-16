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

import com.liferay.change.tracking.exception.NoSuchEntryException;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.model.impl.CTEntryImpl;
import com.liferay.change.tracking.model.impl.CTEntryModelImpl;
import com.liferay.change.tracking.service.persistence.CTEntryPersistence;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ct entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {CTEntryPersistence.class, BasePersistence.class})
public class CTEntryPersistenceImpl
	extends BasePersistenceImpl<CTEntry> implements CTEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTEntryUtil</code> to access the ct entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTEntryImpl.class.getName();

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
	 * Returns all the ct entries where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByCtCollectionId(long ctCollectionId) {
		return findByCtCollectionId(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return findByCtCollectionId(ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator, boolean useFinderCache) {

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

		List<CTEntry> list = null;

		if (useFinderCache) {
			list = (List<CTEntry>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if (ctCollectionId != ctEntry.getCtCollectionId()) {
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

			sb.append(_SQL_SELECT_CTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				list = (List<CTEntry>)QueryUtil.list(
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
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByCtCollectionId_First(
			long ctCollectionId, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByCtCollectionId_First(
		long ctCollectionId, OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByCtCollectionId(
			ctCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByCtCollectionId_Last(
			long ctCollectionId, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByCtCollectionId_Last(
			ctCollectionId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByCtCollectionId_Last(
		long ctCollectionId, OrderByComparator<CTEntry> orderByComparator) {

		int count = countByCtCollectionId(ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByCtCollectionId(
			ctCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByCtCollectionId_PrevAndNext(
			long ctEntryId, long ctCollectionId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByCtCollectionId_PrevAndNext(
				session, ctEntry, ctCollectionId, orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByCtCollectionId_PrevAndNext(
				session, ctEntry, ctCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByCtCollectionId_PrevAndNext(
		Session session, CTEntry ctEntry, long ctCollectionId,
		OrderByComparator<CTEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CTENTRY_WHERE);

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
			sb.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByCtCollectionId(long ctCollectionId) {
		for (CTEntry ctEntry :
				findByCtCollectionId(
					ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByCtCollectionId(long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByCtCollectionId;

		Object[] finderArgs = new Object[] {ctCollectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTENTRY_WHERE);

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
		"ctEntry.ctCollectionId = ?";

	private FinderPath _finderPathWithPaginationFindByC_MCNI;
	private FinderPath _finderPathWithoutPaginationFindByC_MCNI;
	private FinderPath _finderPathCountByC_MCNI;

	/**
	 * Returns all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId) {

		return findByC_MCNI(
			ctCollectionId, modelClassNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end) {

		return findByC_MCNI(ctCollectionId, modelClassNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return findByC_MCNI(
			ctCollectionId, modelClassNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_MCNI;
				finderArgs = new Object[] {ctCollectionId, modelClassNameId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_MCNI;
			finderArgs = new Object[] {
				ctCollectionId, modelClassNameId, start, end, orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (useFinderCache) {
			list = (List<CTEntry>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((ctCollectionId != ctEntry.getCtCollectionId()) ||
						(modelClassNameId != ctEntry.getModelClassNameId())) {

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

			sb.append(_SQL_SELECT_CTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_MODELCLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				list = (List<CTEntry>)QueryUtil.list(
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
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MCNI_First(
			long ctCollectionId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MCNI_First(
			ctCollectionId, modelClassNameId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append(", modelClassNameId=");
		sb.append(modelClassNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MCNI_First(
		long ctCollectionId, long modelClassNameId,
		OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByC_MCNI(
			ctCollectionId, modelClassNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MCNI_Last(
			long ctCollectionId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MCNI_Last(
			ctCollectionId, modelClassNameId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append(", modelClassNameId=");
		sb.append(modelClassNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MCNI_Last(
		long ctCollectionId, long modelClassNameId,
		OrderByComparator<CTEntry> orderByComparator) {

		int count = countByC_MCNI(ctCollectionId, modelClassNameId);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByC_MCNI(
			ctCollectionId, modelClassNameId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByC_MCNI_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByC_MCNI_PrevAndNext(
				session, ctEntry, ctCollectionId, modelClassNameId,
				orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByC_MCNI_PrevAndNext(
				session, ctEntry, ctCollectionId, modelClassNameId,
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

	protected CTEntry getByC_MCNI_PrevAndNext(
		Session session, CTEntry ctEntry, long ctCollectionId,
		long modelClassNameId, OrderByComparator<CTEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_MCNI_CTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_C_MCNI_MODELCLASSNAMEID_2);

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
			sb.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ctCollectionId);

		queryPos.add(modelClassNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 */
	@Override
	public void removeByC_MCNI(long ctCollectionId, long modelClassNameId) {
		for (CTEntry ctEntry :
				findByC_MCNI(
					ctCollectionId, modelClassNameId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_MCNI(long ctCollectionId, long modelClassNameId) {
		FinderPath finderPath = _finderPathCountByC_MCNI;

		Object[] finderArgs = new Object[] {ctCollectionId, modelClassNameId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_MODELCLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

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

	private static final String _FINDER_COLUMN_C_MCNI_CTCOLLECTIONID_2 =
		"ctEntry.ctCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_MCNI_MODELCLASSNAMEID_2 =
		"ctEntry.modelClassNameId = ?";

	private FinderPath _finderPathFetchByC_MCNI_MCPK;
	private FinderPath _finderPathCountByC_MCNI_MCPK;

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MCNI_MCPK(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);

		if (ctEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("ctCollectionId=");
			sb.append(ctCollectionId);

			sb.append(", modelClassNameId=");
			sb.append(modelClassNameId);

			sb.append(", modelClassPK=");
			sb.append(modelClassPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return ctEntry;
	}

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		return fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK, true);
	}

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				ctCollectionId, modelClassNameId, modelClassPK
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_MCNI_MCPK, finderArgs);
		}

		if (result instanceof CTEntry) {
			CTEntry ctEntry = (CTEntry)result;

			if ((ctCollectionId != ctEntry.getCtCollectionId()) ||
				(modelClassNameId != ctEntry.getModelClassNameId()) ||
				(modelClassPK != ctEntry.getModelClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_CTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_MCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				queryPos.add(modelClassPK);

				List<CTEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_MCNI_MCPK, finderArgs, list);
					}
				}
				else {
					CTEntry ctEntry = list.get(0);

					result = ctEntry;

					cacheResult(ctEntry);
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
			return (CTEntry)result;
		}
	}

	/**
	 * Removes the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the ct entry that was removed
	 */
	@Override
	public CTEntry removeByC_MCNI_MCPK(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);

		return remove(ctEntry);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		FinderPath finderPath = _finderPathCountByC_MCNI_MCPK;

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelClassNameId, modelClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_MCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				queryPos.add(modelClassPK);

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

	private static final String _FINDER_COLUMN_C_MCNI_MCPK_CTCOLLECTIONID_2 =
		"ctEntry.ctCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSNAMEID_2 =
		"ctEntry.modelClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSPK_2 =
		"ctEntry.modelClassPK = ?";

	private FinderPath _finderPathWithPaginationFindByNotC_MCNI_MCPK;
	private FinderPath _finderPathWithPaginationCountByNotC_MCNI_MCPK;

	/**
	 * Returns all the ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		return findByNotC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK,
		int start, int end) {

		return findByNotC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK,
		int start, int end, OrderByComparator<CTEntry> orderByComparator) {

		return findByNotC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK,
		int start, int end, OrderByComparator<CTEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByNotC_MCNI_MCPK;
		finderArgs = new Object[] {
			ctCollectionId, modelClassNameId, modelClassPK, start, end,
			orderByComparator
		};

		List<CTEntry> list = null;

		if (useFinderCache) {
			list = (List<CTEntry>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((ctCollectionId == ctEntry.getCtCollectionId()) ||
						(modelClassNameId != ctEntry.getModelClassNameId()) ||
						(modelClassPK != ctEntry.getModelClassPK())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_CTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				queryPos.add(modelClassPK);

				list = (List<CTEntry>)QueryUtil.list(
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
	 * Returns the first ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByNotC_MCNI_MCPK_First(
			long ctCollectionId, long modelClassNameId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByNotC_MCNI_MCPK_First(
			ctCollectionId, modelClassNameId, modelClassPK, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId!=");
		sb.append(ctCollectionId);

		sb.append(", modelClassNameId=");
		sb.append(modelClassNameId);

		sb.append(", modelClassPK=");
		sb.append(modelClassPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByNotC_MCNI_MCPK_First(
		long ctCollectionId, long modelClassNameId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByNotC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByNotC_MCNI_MCPK_Last(
			long ctCollectionId, long modelClassNameId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByNotC_MCNI_MCPK_Last(
			ctCollectionId, modelClassNameId, modelClassPK, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId!=");
		sb.append(ctCollectionId);

		sb.append(", modelClassNameId=");
		sb.append(modelClassNameId);

		sb.append(", modelClassPK=");
		sb.append(modelClassPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByNotC_MCNI_MCPK_Last(
		long ctCollectionId, long modelClassNameId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator) {

		int count = countByNotC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByNotC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByNotC_MCNI_MCPK_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelClassNameId,
			long modelClassPK, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByNotC_MCNI_MCPK_PrevAndNext(
				session, ctEntry, ctCollectionId, modelClassNameId,
				modelClassPK, orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByNotC_MCNI_MCPK_PrevAndNext(
				session, ctEntry, ctCollectionId, modelClassNameId,
				modelClassPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByNotC_MCNI_MCPK_PrevAndNext(
		Session session, CTEntry ctEntry, long ctCollectionId,
		long modelClassNameId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_CTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_CTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSPK_2);

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
			sb.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ctCollectionId);

		queryPos.add(modelClassNameId);

		queryPos.add(modelClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPKs the model class pks
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long[] modelClassPKs) {

		return findByNotC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPKs, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPKs the model class pks
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long[] modelClassPKs,
		int start, int end) {

		return findByNotC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPKs, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPKs the model class pks
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long[] modelClassPKs,
		int start, int end, OrderByComparator<CTEntry> orderByComparator) {

		return findByNotC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPKs, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long[] modelClassPKs,
		int start, int end, OrderByComparator<CTEntry> orderByComparator,
		boolean useFinderCache) {

		if (modelClassPKs == null) {
			modelClassPKs = new long[0];
		}
		else if (modelClassPKs.length > 1) {
			modelClassPKs = ArrayUtil.sortedUnique(modelClassPKs);
		}

		if (modelClassPKs.length == 1) {
			return findByNotC_MCNI_MCPK(
				ctCollectionId, modelClassNameId, modelClassPKs[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					ctCollectionId, modelClassNameId,
					StringUtil.merge(modelClassPKs)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				ctCollectionId, modelClassNameId,
				StringUtil.merge(modelClassPKs), start, end, orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (useFinderCache) {
			list = (List<CTEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByNotC_MCNI_MCPK, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((ctCollectionId == ctEntry.getCtCollectionId()) ||
						(modelClassNameId != ctEntry.getModelClassNameId()) ||
						!ArrayUtil.contains(
							modelClassPKs, ctEntry.getModelClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_CTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSNAMEID_2);

			if (modelClassPKs.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSPK_7);

				sb.append(StringUtil.merge(modelClassPKs));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				list = (List<CTEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByNotC_MCNI_MCPK,
						finderArgs, list);
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
	 * Removes all the ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 */
	@Override
	public void removeByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		for (CTEntry ctEntry :
				findByNotC_MCNI_MCPK(
					ctCollectionId, modelClassNameId, modelClassPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		FinderPath finderPath = _finderPathWithPaginationCountByNotC_MCNI_MCPK;

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelClassNameId, modelClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				queryPos.add(modelClassPK);

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

	/**
	 * Returns the number of ct entries where ctCollectionId &ne; &#63; and modelClassNameId = &#63; and modelClassPK = any &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPKs the model class pks
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByNotC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long[] modelClassPKs) {

		if (modelClassPKs == null) {
			modelClassPKs = new long[0];
		}
		else if (modelClassPKs.length > 1) {
			modelClassPKs = ArrayUtil.sortedUnique(modelClassPKs);
		}

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelClassNameId, StringUtil.merge(modelClassPKs)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByNotC_MCNI_MCPK, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_CTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSNAMEID_2);

			if (modelClassPKs.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSPK_7);

				sb.append(StringUtil.merge(modelClassPKs));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByNotC_MCNI_MCPK, finderArgs,
					count);
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

	private static final String _FINDER_COLUMN_NOTC_MCNI_MCPK_CTCOLLECTIONID_2 =
		"ctEntry.ctCollectionId != ? AND ";

	private static final String
		_FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSNAMEID_2 =
			"ctEntry.modelClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSPK_2 =
		"ctEntry.modelClassPK = ?";

	private static final String _FINDER_COLUMN_NOTC_MCNI_MCPK_MODELCLASSPK_7 =
		"ctEntry.modelClassPK IN (";

	public CTEntryPersistenceImpl() {
		setModelClass(CTEntry.class);

		setModelImplClass(CTEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CTEntryTable.INSTANCE);
	}

	/**
	 * Caches the ct entry in the entity cache if it is enabled.
	 *
	 * @param ctEntry the ct entry
	 */
	@Override
	public void cacheResult(CTEntry ctEntry) {
		entityCache.putResult(
			CTEntryImpl.class, ctEntry.getPrimaryKey(), ctEntry);

		finderCache.putResult(
			_finderPathFetchByC_MCNI_MCPK,
			new Object[] {
				ctEntry.getCtCollectionId(), ctEntry.getModelClassNameId(),
				ctEntry.getModelClassPK()
			},
			ctEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ct entries in the entity cache if it is enabled.
	 *
	 * @param ctEntries the ct entries
	 */
	@Override
	public void cacheResult(List<CTEntry> ctEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ctEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CTEntry ctEntry : ctEntries) {
			if (entityCache.getResult(
					CTEntryImpl.class, ctEntry.getPrimaryKey()) == null) {

				cacheResult(ctEntry);
			}
		}
	}

	/**
	 * Clears the cache for all ct entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTEntryImpl.class);

		finderCache.clearCache(CTEntryImpl.class);
	}

	/**
	 * Clears the cache for the ct entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTEntry ctEntry) {
		entityCache.removeResult(CTEntryImpl.class, ctEntry);
	}

	@Override
	public void clearCache(List<CTEntry> ctEntries) {
		for (CTEntry ctEntry : ctEntries) {
			entityCache.removeResult(CTEntryImpl.class, ctEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CTEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CTEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(CTEntryModelImpl ctEntryModelImpl) {
		Object[] args = new Object[] {
			ctEntryModelImpl.getCtCollectionId(),
			ctEntryModelImpl.getModelClassNameId(),
			ctEntryModelImpl.getModelClassPK()
		};

		finderCache.putResult(
			_finderPathCountByC_MCNI_MCPK, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_MCNI_MCPK, args, ctEntryModelImpl);
	}

	/**
	 * Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	 *
	 * @param ctEntryId the primary key for the new ct entry
	 * @return the new ct entry
	 */
	@Override
	public CTEntry create(long ctEntryId) {
		CTEntry ctEntry = new CTEntryImpl();

		ctEntry.setNew(true);
		ctEntry.setPrimaryKey(ctEntryId);

		ctEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctEntry;
	}

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry remove(long ctEntryId) throws NoSuchEntryException {
		return remove((Serializable)ctEntryId);
	}

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry remove(Serializable primaryKey) throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			CTEntry ctEntry = (CTEntry)session.get(
				CTEntryImpl.class, primaryKey);

			if (ctEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctEntry);
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
	protected CTEntry removeImpl(CTEntry ctEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctEntry)) {
				ctEntry = (CTEntry)session.get(
					CTEntryImpl.class, ctEntry.getPrimaryKeyObj());
			}

			if (ctEntry != null) {
				session.delete(ctEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctEntry != null) {
			clearCache(ctEntry);
		}

		return ctEntry;
	}

	@Override
	public CTEntry updateImpl(CTEntry ctEntry) {
		boolean isNew = ctEntry.isNew();

		if (!(ctEntry instanceof CTEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTEntry implementation " +
					ctEntry.getClass());
		}

		CTEntryModelImpl ctEntryModelImpl = (CTEntryModelImpl)ctEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (ctEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				ctEntry.setCreateDate(date);
			}
			else {
				ctEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!ctEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ctEntry.setModifiedDate(date);
			}
			else {
				ctEntry.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ctEntry);
			}
			else {
				ctEntry = (CTEntry)session.merge(ctEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(CTEntryImpl.class, ctEntryModelImpl, false, true);

		cacheUniqueFindersCache(ctEntryModelImpl);

		if (isNew) {
			ctEntry.setNew(false);
		}

		ctEntry.resetOriginalValues();

		return ctEntry;
	}

	/**
	 * Returns the ct entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByPrimaryKey(primaryKey);

		if (ctEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctEntry;
	}

	/**
	 * Returns the ct entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry findByPrimaryKey(long ctEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)ctEntryId);
	}

	/**
	 * Returns the ct entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry, or <code>null</code> if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry fetchByPrimaryKey(long ctEntryId) {
		return fetchByPrimaryKey((Serializable)ctEntryId);
	}

	/**
	 * Returns all the ct entries.
	 *
	 * @return the ct entries
	 */
	@Override
	public List<CTEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator,
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

		List<CTEntry> list = null;

		if (useFinderCache) {
			list = (List<CTEntry>)finderCache.getResult(finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CTENTRY;

				sql = sql.concat(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CTEntry>)QueryUtil.list(
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
	 * Removes all the ct entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTEntry ctEntry : findAll()) {
			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries.
	 *
	 * @return the number of ct entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CTENTRY);

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
		return "ctEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct entry persistence.
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

		_finderPathWithPaginationFindByC_MCNI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_MCNI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"ctCollectionId", "modelClassNameId"}, true);

		_finderPathWithoutPaginationFindByC_MCNI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_MCNI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"ctCollectionId", "modelClassNameId"}, true);

		_finderPathCountByC_MCNI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MCNI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"ctCollectionId", "modelClassNameId"}, false);

		_finderPathFetchByC_MCNI_MCPK = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_MCNI_MCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"ctCollectionId", "modelClassNameId", "modelClassPK"},
			true);

		_finderPathCountByC_MCNI_MCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MCNI_MCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"ctCollectionId", "modelClassNameId", "modelClassPK"},
			false);

		_finderPathWithPaginationFindByNotC_MCNI_MCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByNotC_MCNI_MCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ctCollectionId", "modelClassNameId", "modelClassPK"},
			true);

		_finderPathWithPaginationCountByNotC_MCNI_MCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByNotC_MCNI_MCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"ctCollectionId", "modelClassNameId", "modelClassPK"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTEntryImpl.class.getName());
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

	private static final String _SQL_SELECT_CTENTRY =
		"SELECT ctEntry FROM CTEntry ctEntry";

	private static final String _SQL_SELECT_CTENTRY_WHERE =
		"SELECT ctEntry FROM CTEntry ctEntry WHERE ";

	private static final String _SQL_COUNT_CTENTRY =
		"SELECT COUNT(ctEntry) FROM CTEntry ctEntry";

	private static final String _SQL_COUNT_CTENTRY_WHERE =
		"SELECT COUNT(ctEntry) FROM CTEntry ctEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CTEntryModelArgumentsResolver _ctEntryModelArgumentsResolver;

}