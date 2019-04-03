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

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.exception.NoSuchEntryException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryAggregate;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
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
@Component(service = CTEntryPersistence.class)
@ProviderType
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
	private FinderPath _finderPathWithPaginationFindByModelClassNameId;
	private FinderPath _finderPathWithoutPaginationFindByModelClassNameId;
	private FinderPath _finderPathCountByModelClassNameId;

	/**
	 * Returns all the ct entries where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByModelClassNameId(long modelClassNameId) {
		return findByModelClassNameId(
			modelClassNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end) {

		return findByModelClassNameId(modelClassNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return findByModelClassNameId(
			modelClassNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByModelClassNameId;
			finderArgs = new Object[] {modelClassNameId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByModelClassNameId;
			finderArgs = new Object[] {
				modelClassNameId, start, end, orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((modelClassNameId != ctEntry.getModelClassNameId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_MODELCLASSNAMEID_MODELCLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modelClassNameId);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByModelClassNameId_First(
			long modelClassNameId, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByModelClassNameId_First(
			modelClassNameId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByModelClassNameId_First(
		long modelClassNameId, OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByModelClassNameId(
			modelClassNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByModelClassNameId_Last(
			long modelClassNameId, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByModelClassNameId_Last(
			modelClassNameId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByModelClassNameId_Last(
		long modelClassNameId, OrderByComparator<CTEntry> orderByComparator) {

		int count = countByModelClassNameId(modelClassNameId);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByModelClassNameId(
			modelClassNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByModelClassNameId_PrevAndNext(
			long ctEntryId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByModelClassNameId_PrevAndNext(
				session, ctEntry, modelClassNameId, orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByModelClassNameId_PrevAndNext(
				session, ctEntry, modelClassNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByModelClassNameId_PrevAndNext(
		Session session, CTEntry ctEntry, long modelClassNameId,
		OrderByComparator<CTEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CTENTRY_WHERE);

		query.append(_FINDER_COLUMN_MODELCLASSNAMEID_MODELCLASSNAMEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(modelClassNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where modelClassNameId = &#63; from the database.
	 *
	 * @param modelClassNameId the model class name ID
	 */
	@Override
	public void removeByModelClassNameId(long modelClassNameId) {
		for (CTEntry ctEntry :
				findByModelClassNameId(
					modelClassNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByModelClassNameId(long modelClassNameId) {
		FinderPath finderPath = _finderPathCountByModelClassNameId;

		Object[] finderArgs = new Object[] {modelClassNameId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_MODELCLASSNAMEID_MODELCLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modelClassNameId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_MODELCLASSNAMEID_MODELCLASSNAMEID_2 =
			"ctEntry.modelClassNameId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_C(long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_C(modelClassNameId, modelClassPK);

		if (ctEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("modelClassNameId=");
			msg.append(modelClassNameId);

			msg.append(", modelClassPK=");
			msg.append(modelClassPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return ctEntry;
	}

	/**
	 * Returns the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_C(long modelClassNameId, long modelClassPK) {
		return fetchByC_C(modelClassNameId, modelClassPK, true);
	}

	/**
	 * Returns the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_C(
		long modelClassNameId, long modelClassPK, boolean retrieveFromCache) {

		Object[] finderArgs = new Object[] {modelClassNameId, modelClassPK};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof CTEntry) {
			CTEntry ctEntry = (CTEntry)result;

			if ((modelClassNameId != ctEntry.getModelClassNameId()) ||
				(modelClassPK != ctEntry.getModelClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_MODELCLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modelClassNameId);

				qPos.add(modelClassPK);

				List<CTEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByC_C, finderArgs, list);
				}
				else {
					CTEntry ctEntry = list.get(0);

					result = ctEntry;

					cacheResult(ctEntry);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByC_C, finderArgs);

				throw processException(e);
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
	 * Removes the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the ct entry that was removed
	 */
	@Override
	public CTEntry removeByC_C(long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByC_C(modelClassNameId, modelClassPK);

		return remove(ctEntry);
	}

	/**
	 * Returns the number of ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_C(long modelClassNameId, long modelClassPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {modelClassNameId, modelClassPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_MODELCLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modelClassNameId);

				qPos.add(modelClassPK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_MODELCLASSNAMEID_2 =
		"ctEntry.modelClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_MODELCLASSPK_2 =
		"ctEntry.modelClassPK = ?";

	public CTEntryPersistenceImpl() {
		setModelClass(CTEntry.class);

		setModelImplClass(CTEntryImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the ct entry in the entity cache if it is enabled.
	 *
	 * @param ctEntry the ct entry
	 */
	@Override
	public void cacheResult(CTEntry ctEntry) {
		entityCache.putResult(
			entityCacheEnabled, CTEntryImpl.class, ctEntry.getPrimaryKey(),
			ctEntry);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				ctEntry.getModelClassNameId(), ctEntry.getModelClassPK()
			},
			ctEntry);

		ctEntry.resetOriginalValues();
	}

	/**
	 * Caches the ct entries in the entity cache if it is enabled.
	 *
	 * @param ctEntries the ct entries
	 */
	@Override
	public void cacheResult(List<CTEntry> ctEntries) {
		for (CTEntry ctEntry : ctEntries) {
			if (entityCache.getResult(
					entityCacheEnabled, CTEntryImpl.class,
					ctEntry.getPrimaryKey()) == null) {

				cacheResult(ctEntry);
			}
			else {
				ctEntry.resetOriginalValues();
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

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
		entityCache.removeResult(
			entityCacheEnabled, CTEntryImpl.class, ctEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CTEntryModelImpl)ctEntry, true);
	}

	@Override
	public void clearCache(List<CTEntry> ctEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CTEntry ctEntry : ctEntries) {
			entityCache.removeResult(
				entityCacheEnabled, CTEntryImpl.class, ctEntry.getPrimaryKey());

			clearUniqueFindersCache((CTEntryModelImpl)ctEntry, true);
		}
	}

	protected void cacheUniqueFindersCache(CTEntryModelImpl ctEntryModelImpl) {
		Object[] args = new Object[] {
			ctEntryModelImpl.getModelClassNameId(),
			ctEntryModelImpl.getModelClassPK()
		};

		finderCache.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C, args, ctEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CTEntryModelImpl ctEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ctEntryModelImpl.getModelClassNameId(),
				ctEntryModelImpl.getModelClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}

		if ((ctEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ctEntryModelImpl.getOriginalModelClassNameId(),
				ctEntryModelImpl.getOriginalModelClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}
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

		ctEntry.setCompanyId(companyProvider.getCompanyId());

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
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CTEntry removeImpl(CTEntry ctEntry) {
		ctEntryToCTEntryAggregateTableMapper.deleteLeftPrimaryKeyTableMappings(
			ctEntry.getPrimaryKey());

		ctEntryToCTCollectionTableMapper.deleteLeftPrimaryKeyTableMappings(
			ctEntry.getPrimaryKey());

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
		catch (Exception e) {
			throw processException(e);
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

		Date now = new Date();

		if (isNew && (ctEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				ctEntry.setCreateDate(now);
			}
			else {
				ctEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!ctEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ctEntry.setModifiedDate(now);
			}
			else {
				ctEntry.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctEntry.isNew()) {
				session.save(ctEntry);

				ctEntry.setNew(false);
			}
			else {
				ctEntry = (CTEntry)session.merge(ctEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				ctEntryModelImpl.getModelClassNameId()
			};

			finderCache.removeResult(_finderPathCountByModelClassNameId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByModelClassNameId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ctEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByModelClassNameId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctEntryModelImpl.getOriginalModelClassNameId()
				};

				finderCache.removeResult(
					_finderPathCountByModelClassNameId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByModelClassNameId, args);

				args = new Object[] {ctEntryModelImpl.getModelClassNameId()};

				finderCache.removeResult(
					_finderPathCountByModelClassNameId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByModelClassNameId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, CTEntryImpl.class, ctEntry.getPrimaryKey(),
			ctEntry, false);

		clearUniqueFindersCache(ctEntryModelImpl, false);
		cacheUniqueFindersCache(ctEntryModelImpl);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CTENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CTENTRY;

				if (pagination) {
					sql = sql.concat(CTEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CTENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the primaryKeys of ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return long[] of the primaryKeys of ct entry aggregates associated with the ct entry
	 */
	@Override
	public long[] getCTEntryAggregatePrimaryKeys(long pk) {
		long[] pks = ctEntryToCTEntryAggregateTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the ct entries associated with the ct entry aggregate
	 */
	@Override
	public List<CTEntry> getCTEntryAggregateCTEntries(long pk) {
		return getCTEntryAggregateCTEntries(
			pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entries associated with the ct entry aggregate
	 */
	@Override
	public List<CTEntry> getCTEntryAggregateCTEntries(
		long pk, int start, int end) {

		return getCTEntryAggregateCTEntries(pk, start, end, null);
	}

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries associated with the ct entry aggregate
	 */
	@Override
	public List<CTEntry> getCTEntryAggregateCTEntries(
		long pk, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return ctEntryToCTEntryAggregateTableMapper.getLeftBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the number of ct entry aggregates associated with the ct entry
	 */
	@Override
	public int getCTEntryAggregatesSize(long pk) {
		long[] pks = ctEntryToCTEntryAggregateTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the ct entry aggregate is associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 * @return <code>true</code> if the ct entry aggregate is associated with the ct entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		return ctEntryToCTEntryAggregateTableMapper.containsTableMapping(
			pk, ctEntryAggregatePK);
	}

	/**
	 * Returns <code>true</code> if the ct entry has any ct entry aggregates associated with it.
	 *
	 * @param pk the primary key of the ct entry to check for associations with ct entry aggregates
	 * @return <code>true</code> if the ct entry has any ct entry aggregates associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntryAggregates(long pk) {
		if (getCTEntryAggregatesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	@Override
	public void addCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk, ctEntryAggregatePK);
		}
		else {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				ctEntry.getCompanyId(), pk, ctEntryAggregatePK);
		}
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	@Override
	public void addCTEntryAggregate(
		long pk, CTEntryAggregate ctEntryAggregate) {

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk,
				ctEntryAggregate.getPrimaryKey());
		}
		else {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				ctEntry.getCompanyId(), pk, ctEntryAggregate.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	@Override
	public void addCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		long companyId = 0;

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntry.getCompanyId();
		}

		ctEntryToCTEntryAggregateTableMapper.addTableMappings(
			companyId, pk, ctEntryAggregatePKs);
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	@Override
	public void addCTEntryAggregates(
		long pk, List<CTEntryAggregate> ctEntryAggregates) {

		addCTEntryAggregates(
			pk,
			ListUtil.toLongArray(
				ctEntryAggregates,
				CTEntryAggregate.CT_ENTRY_AGGREGATE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the ct entry and its ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry to clear the associated ct entry aggregates from
	 */
	@Override
	public void clearCTEntryAggregates(long pk) {
		ctEntryToCTEntryAggregateTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	@Override
	public void removeCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		ctEntryToCTEntryAggregateTableMapper.deleteTableMapping(
			pk, ctEntryAggregatePK);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	@Override
	public void removeCTEntryAggregate(
		long pk, CTEntryAggregate ctEntryAggregate) {

		ctEntryToCTEntryAggregateTableMapper.deleteTableMapping(
			pk, ctEntryAggregate.getPrimaryKey());
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	@Override
	public void removeCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		ctEntryToCTEntryAggregateTableMapper.deleteTableMappings(
			pk, ctEntryAggregatePKs);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	@Override
	public void removeCTEntryAggregates(
		long pk, List<CTEntryAggregate> ctEntryAggregates) {

		removeCTEntryAggregates(
			pk,
			ListUtil.toLongArray(
				ctEntryAggregates,
				CTEntryAggregate.CT_ENTRY_AGGREGATE_ID_ACCESSOR));
	}

	/**
	 * Sets the ct entry aggregates associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates to be associated with the ct entry
	 */
	@Override
	public void setCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		Set<Long> newCTEntryAggregatePKsSet = SetUtil.fromArray(
			ctEntryAggregatePKs);
		Set<Long> oldCTEntryAggregatePKsSet = SetUtil.fromArray(
			ctEntryToCTEntryAggregateTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeCTEntryAggregatePKsSet = new HashSet<Long>(
			oldCTEntryAggregatePKsSet);

		removeCTEntryAggregatePKsSet.removeAll(newCTEntryAggregatePKsSet);

		ctEntryToCTEntryAggregateTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeCTEntryAggregatePKsSet));

		newCTEntryAggregatePKsSet.removeAll(oldCTEntryAggregatePKsSet);

		long companyId = 0;

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntry.getCompanyId();
		}

		ctEntryToCTEntryAggregateTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newCTEntryAggregatePKsSet));
	}

	/**
	 * Sets the ct entry aggregates associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates to be associated with the ct entry
	 */
	@Override
	public void setCTEntryAggregates(
		long pk, List<CTEntryAggregate> ctEntryAggregates) {

		try {
			long[] ctEntryAggregatePKs = new long[ctEntryAggregates.size()];

			for (int i = 0; i < ctEntryAggregates.size(); i++) {
				CTEntryAggregate ctEntryAggregate = ctEntryAggregates.get(i);

				ctEntryAggregatePKs[i] = ctEntryAggregate.getPrimaryKey();
			}

			setCTEntryAggregates(pk, ctEntryAggregatePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	/**
	 * Returns the primaryKeys of ct collections associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return long[] of the primaryKeys of ct collections associated with the ct entry
	 */
	@Override
	public long[] getCTCollectionPrimaryKeys(long pk) {
		long[] pks = ctEntryToCTCollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the ct entry associated with the ct collection.
	 *
	 * @param pk the primary key of the ct collection
	 * @return the ct entries associated with the ct collection
	 */
	@Override
	public List<CTEntry> getCTCollectionCTEntries(long pk) {
		return getCTCollectionCTEntries(
			pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns all the ct entry associated with the ct collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct collection
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @return the range of ct entries associated with the ct collection
	 */
	@Override
	public List<CTEntry> getCTCollectionCTEntries(long pk, int start, int end) {
		return getCTCollectionCTEntries(pk, start, end, null);
	}

	/**
	 * Returns all the ct entry associated with the ct collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct collection
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries associated with the ct collection
	 */
	@Override
	public List<CTEntry> getCTCollectionCTEntries(
		long pk, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return ctEntryToCTCollectionTableMapper.getLeftBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct collections associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the number of ct collections associated with the ct entry
	 */
	@Override
	public int getCTCollectionsSize(long pk) {
		long[] pks = ctEntryToCTCollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the ct collection is associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPK the primary key of the ct collection
	 * @return <code>true</code> if the ct collection is associated with the ct entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTCollection(long pk, long ctCollectionPK) {
		return ctEntryToCTCollectionTableMapper.containsTableMapping(
			pk, ctCollectionPK);
	}

	/**
	 * Returns <code>true</code> if the ct entry has any ct collections associated with it.
	 *
	 * @param pk the primary key of the ct entry to check for associations with ct collections
	 * @return <code>true</code> if the ct entry has any ct collections associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTCollections(long pk) {
		if (getCTCollectionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPK the primary key of the ct collection
	 */
	@Override
	public void addCTCollection(long pk, long ctCollectionPK) {
		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			ctEntryToCTCollectionTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk, ctCollectionPK);
		}
		else {
			ctEntryToCTCollectionTableMapper.addTableMapping(
				ctEntry.getCompanyId(), pk, ctCollectionPK);
		}
	}

	/**
	 * Adds an association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollection the ct collection
	 */
	@Override
	public void addCTCollection(long pk, CTCollection ctCollection) {
		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			ctEntryToCTCollectionTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk,
				ctCollection.getPrimaryKey());
		}
		else {
			ctEntryToCTCollectionTableMapper.addTableMapping(
				ctEntry.getCompanyId(), pk, ctCollection.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPKs the primary keys of the ct collections
	 */
	@Override
	public void addCTCollections(long pk, long[] ctCollectionPKs) {
		long companyId = 0;

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntry.getCompanyId();
		}

		ctEntryToCTCollectionTableMapper.addTableMappings(
			companyId, pk, ctCollectionPKs);
	}

	/**
	 * Adds an association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollections the ct collections
	 */
	@Override
	public void addCTCollections(long pk, List<CTCollection> ctCollections) {
		addCTCollections(
			pk,
			ListUtil.toLongArray(
				ctCollections, CTCollection.CT_COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the ct entry and its ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry to clear the associated ct collections from
	 */
	@Override
	public void clearCTCollections(long pk) {
		ctEntryToCTCollectionTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPK the primary key of the ct collection
	 */
	@Override
	public void removeCTCollection(long pk, long ctCollectionPK) {
		ctEntryToCTCollectionTableMapper.deleteTableMapping(pk, ctCollectionPK);
	}

	/**
	 * Removes the association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollection the ct collection
	 */
	@Override
	public void removeCTCollection(long pk, CTCollection ctCollection) {
		ctEntryToCTCollectionTableMapper.deleteTableMapping(
			pk, ctCollection.getPrimaryKey());
	}

	/**
	 * Removes the association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPKs the primary keys of the ct collections
	 */
	@Override
	public void removeCTCollections(long pk, long[] ctCollectionPKs) {
		ctEntryToCTCollectionTableMapper.deleteTableMappings(
			pk, ctCollectionPKs);
	}

	/**
	 * Removes the association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollections the ct collections
	 */
	@Override
	public void removeCTCollections(long pk, List<CTCollection> ctCollections) {
		removeCTCollections(
			pk,
			ListUtil.toLongArray(
				ctCollections, CTCollection.CT_COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Sets the ct collections associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPKs the primary keys of the ct collections to be associated with the ct entry
	 */
	@Override
	public void setCTCollections(long pk, long[] ctCollectionPKs) {
		Set<Long> newCTCollectionPKsSet = SetUtil.fromArray(ctCollectionPKs);
		Set<Long> oldCTCollectionPKsSet = SetUtil.fromArray(
			ctEntryToCTCollectionTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeCTCollectionPKsSet = new HashSet<Long>(
			oldCTCollectionPKsSet);

		removeCTCollectionPKsSet.removeAll(newCTCollectionPKsSet);

		ctEntryToCTCollectionTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeCTCollectionPKsSet));

		newCTCollectionPKsSet.removeAll(oldCTCollectionPKsSet);

		long companyId = 0;

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntry.getCompanyId();
		}

		ctEntryToCTCollectionTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newCTCollectionPKsSet));
	}

	/**
	 * Sets the ct collections associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollections the ct collections to be associated with the ct entry
	 */
	@Override
	public void setCTCollections(long pk, List<CTCollection> ctCollections) {
		try {
			long[] ctCollectionPKs = new long[ctCollections.size()];

			for (int i = 0; i < ctCollections.size(); i++) {
				CTCollection ctCollection = ctCollections.get(i);

				ctCollectionPKs[i] = ctCollection.getPrimaryKey();
			}

			setCTCollections(pk, ctCollectionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
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
		CTEntryModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		CTEntryModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		ctEntryToCTEntryAggregateTableMapper =
			TableMapperFactory.getTableMapper(
				"CTEntryAggregates_CTEntries#ctEntryId",
				"CTEntryAggregates_CTEntries", "companyId", "ctEntryId",
				"ctEntryAggregateId", this, CTEntryAggregate.class);

		ctEntryToCTCollectionTableMapper = TableMapperFactory.getTableMapper(
			"CTCollections_CTEntries#ctEntryId", "CTCollections_CTEntries",
			"companyId", "ctEntryId", "ctCollectionId", this,
			CTCollection.class);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByModelClassNameId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByModelClassNameId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByModelClassNameId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByModelClassNameId",
			new String[] {Long.class.getName()},
			CTEntryModelImpl.MODELCLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByModelClassNameId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByModelClassNameId", new String[] {Long.class.getName()});

		_finderPathFetchByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			CTEntryModelImpl.MODELCLASSNAMEID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELCLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper(
			"CTEntryAggregates_CTEntries#ctEntryId");
		TableMapperFactory.removeTableMapper(
			"CTCollections_CTEntries#ctEntryId");
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.change.tracking.model.CTEntry"),
			true);
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

	private boolean _columnBitmaskEnabled;

	@Reference(service = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	protected TableMapper<CTEntry, CTEntryAggregate>
		ctEntryToCTEntryAggregateTableMapper;
	protected TableMapper<CTEntry, CTCollection>
		ctEntryToCTCollectionTableMapper;

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

}