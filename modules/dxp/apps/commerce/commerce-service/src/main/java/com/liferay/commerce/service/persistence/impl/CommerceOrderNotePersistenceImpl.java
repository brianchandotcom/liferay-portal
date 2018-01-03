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

package com.liferay.commerce.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchOrderNoteException;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.model.impl.CommerceOrderNoteImpl;
import com.liferay.commerce.model.impl.CommerceOrderNoteModelImpl;
import com.liferay.commerce.service.persistence.CommerceOrderNotePersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce order note service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNotePersistence
 * @see com.liferay.commerce.service.persistence.CommerceOrderNoteUtil
 * @generated
 */
@ProviderType
public class CommerceOrderNotePersistenceImpl extends BasePersistenceImpl<CommerceOrderNote>
	implements CommerceOrderNotePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceOrderNoteUtil} to access the commerce order note persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceOrderNoteImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderNoteModelImpl.FINDER_CACHE_ENABLED,
			CommerceOrderNoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderNoteModelImpl.FINDER_CACHE_ENABLED,
			CommerceOrderNoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderNoteModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEORDERID =
		new FinderPath(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderNoteModelImpl.FINDER_CACHE_ENABLED,
			CommerceOrderNoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceOrderId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID =
		new FinderPath(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderNoteModelImpl.FINDER_CACHE_ENABLED,
			CommerceOrderNoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCommerceOrderId",
			new String[] { Long.class.getName() },
			CommerceOrderNoteModelImpl.COMMERCEORDERID_COLUMN_BITMASK |
			CommerceOrderNoteModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEORDERID = new FinderPath(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderNoteModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceOrderId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByCommerceOrderId(long commerceOrderId) {
		return findByCommerceOrderId(commerceOrderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByCommerceOrderId(long commerceOrderId,
		int start, int end) {
		return findByCommerceOrderId(commerceOrderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByCommerceOrderId(long commerceOrderId,
		int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {
		return findByCommerceOrderId(commerceOrderId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByCommerceOrderId(long commerceOrderId,
		int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID;
			finderArgs = new Object[] { commerceOrderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEORDERID;
			finderArgs = new Object[] {
					commerceOrderId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceOrderNote> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceOrderNote>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderNote commerceOrderNote : list) {
					if ((commerceOrderId != commerceOrderNote.getCommerceOrderId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_COMMERCEORDERNOTE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceOrderNoteModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceOrderId);

				if (!pagination) {
					list = (List<CommerceOrderNote>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceOrderNote>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote findByCommerceOrderId_First(long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException {
		CommerceOrderNote commerceOrderNote = fetchByCommerceOrderId_First(commerceOrderId,
				orderByComparator);

		if (commerceOrderNote != null) {
			return commerceOrderNote;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceOrderId=");
		msg.append(commerceOrderId);

		msg.append("}");

		throw new NoSuchOrderNoteException(msg.toString());
	}

	/**
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote fetchByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator) {
		List<CommerceOrderNote> list = findByCommerceOrderId(commerceOrderId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote findByCommerceOrderId_Last(long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException {
		CommerceOrderNote commerceOrderNote = fetchByCommerceOrderId_Last(commerceOrderId,
				orderByComparator);

		if (commerceOrderNote != null) {
			return commerceOrderNote;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceOrderId=");
		msg.append(commerceOrderId);

		msg.append("}");

		throw new NoSuchOrderNoteException(msg.toString());
	}

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote fetchByCommerceOrderId_Last(long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator) {
		int count = countByCommerceOrderId(commerceOrderId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderNote> list = findByCommerceOrderId(commerceOrderId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order notes before and after the current commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderNoteId the primary key of the current commerce order note
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	@Override
	public CommerceOrderNote[] findByCommerceOrderId_PrevAndNext(
		long commerceOrderNoteId, long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException {
		CommerceOrderNote commerceOrderNote = findByPrimaryKey(commerceOrderNoteId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderNote[] array = new CommerceOrderNoteImpl[3];

			array[0] = getByCommerceOrderId_PrevAndNext(session,
					commerceOrderNote, commerceOrderId, orderByComparator, true);

			array[1] = commerceOrderNote;

			array[2] = getByCommerceOrderId_PrevAndNext(session,
					commerceOrderNote, commerceOrderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceOrderNote getByCommerceOrderId_PrevAndNext(
		Session session, CommerceOrderNote commerceOrderNote,
		long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEORDERNOTE_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(CommerceOrderNoteModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceOrderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceOrderNote);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceOrderNote> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order notes where commerceOrderId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 */
	@Override
	public void removeByCommerceOrderId(long commerceOrderId) {
		for (CommerceOrderNote commerceOrderNote : findByCommerceOrderId(
				commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceOrderNote);
		}
	}

	/**
	 * Returns the number of commerce order notes where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the number of matching commerce order notes
	 */
	@Override
	public int countByCommerceOrderId(long commerceOrderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEORDERID;

		Object[] finderArgs = new Object[] { commerceOrderId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEORDERNOTE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceOrderId);

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

	private static final String _FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2 =
		"commerceOrderNote.commerceOrderId = ?";

	public CommerceOrderNotePersistenceImpl() {
		setModelClass(CommerceOrderNote.class);
	}

	/**
	 * Caches the commerce order note in the entity cache if it is enabled.
	 *
	 * @param commerceOrderNote the commerce order note
	 */
	@Override
	public void cacheResult(CommerceOrderNote commerceOrderNote) {
		entityCache.putResult(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderNoteImpl.class, commerceOrderNote.getPrimaryKey(),
			commerceOrderNote);

		commerceOrderNote.resetOriginalValues();
	}

	/**
	 * Caches the commerce order notes in the entity cache if it is enabled.
	 *
	 * @param commerceOrderNotes the commerce order notes
	 */
	@Override
	public void cacheResult(List<CommerceOrderNote> commerceOrderNotes) {
		for (CommerceOrderNote commerceOrderNote : commerceOrderNotes) {
			if (entityCache.getResult(
						CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
						CommerceOrderNoteImpl.class,
						commerceOrderNote.getPrimaryKey()) == null) {
				cacheResult(commerceOrderNote);
			}
			else {
				commerceOrderNote.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce order notes.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceOrderNoteImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce order note.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceOrderNote commerceOrderNote) {
		entityCache.removeResult(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderNoteImpl.class, commerceOrderNote.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceOrderNote> commerceOrderNotes) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceOrderNote commerceOrderNote : commerceOrderNotes) {
			entityCache.removeResult(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
				CommerceOrderNoteImpl.class, commerceOrderNote.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce order note with the primary key. Does not add the commerce order note to the database.
	 *
	 * @param commerceOrderNoteId the primary key for the new commerce order note
	 * @return the new commerce order note
	 */
	@Override
	public CommerceOrderNote create(long commerceOrderNoteId) {
		CommerceOrderNote commerceOrderNote = new CommerceOrderNoteImpl();

		commerceOrderNote.setNew(true);
		commerceOrderNote.setPrimaryKey(commerceOrderNoteId);

		commerceOrderNote.setCompanyId(companyProvider.getCompanyId());

		return commerceOrderNote;
	}

	/**
	 * Removes the commerce order note with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderNoteId the primary key of the commerce order note
	 * @return the commerce order note that was removed
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	@Override
	public CommerceOrderNote remove(long commerceOrderNoteId)
		throws NoSuchOrderNoteException {
		return remove((Serializable)commerceOrderNoteId);
	}

	/**
	 * Removes the commerce order note with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce order note
	 * @return the commerce order note that was removed
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	@Override
	public CommerceOrderNote remove(Serializable primaryKey)
		throws NoSuchOrderNoteException {
		Session session = null;

		try {
			session = openSession();

			CommerceOrderNote commerceOrderNote = (CommerceOrderNote)session.get(CommerceOrderNoteImpl.class,
					primaryKey);

			if (commerceOrderNote == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrderNoteException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceOrderNote);
		}
		catch (NoSuchOrderNoteException nsee) {
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
	protected CommerceOrderNote removeImpl(CommerceOrderNote commerceOrderNote) {
		commerceOrderNote = toUnwrappedModel(commerceOrderNote);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceOrderNote)) {
				commerceOrderNote = (CommerceOrderNote)session.get(CommerceOrderNoteImpl.class,
						commerceOrderNote.getPrimaryKeyObj());
			}

			if (commerceOrderNote != null) {
				session.delete(commerceOrderNote);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceOrderNote != null) {
			clearCache(commerceOrderNote);
		}

		return commerceOrderNote;
	}

	@Override
	public CommerceOrderNote updateImpl(CommerceOrderNote commerceOrderNote) {
		commerceOrderNote = toUnwrappedModel(commerceOrderNote);

		boolean isNew = commerceOrderNote.isNew();

		CommerceOrderNoteModelImpl commerceOrderNoteModelImpl = (CommerceOrderNoteModelImpl)commerceOrderNote;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceOrderNote.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceOrderNote.setCreateDate(now);
			}
			else {
				commerceOrderNote.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceOrderNoteModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceOrderNote.setModifiedDate(now);
			}
			else {
				commerceOrderNote.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceOrderNote.isNew()) {
				session.save(commerceOrderNote);

				commerceOrderNote.setNew(false);
			}
			else {
				commerceOrderNote = (CommerceOrderNote)session.merge(commerceOrderNote);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceOrderNoteModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceOrderNoteModelImpl.getCommerceOrderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceOrderNoteModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceOrderNoteModelImpl.getOriginalCommerceOrderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID,
					args);

				args = new Object[] {
						commerceOrderNoteModelImpl.getCommerceOrderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID,
					args);
			}
		}

		entityCache.putResult(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderNoteImpl.class, commerceOrderNote.getPrimaryKey(),
			commerceOrderNote, false);

		commerceOrderNote.resetOriginalValues();

		return commerceOrderNote;
	}

	protected CommerceOrderNote toUnwrappedModel(
		CommerceOrderNote commerceOrderNote) {
		if (commerceOrderNote instanceof CommerceOrderNoteImpl) {
			return commerceOrderNote;
		}

		CommerceOrderNoteImpl commerceOrderNoteImpl = new CommerceOrderNoteImpl();

		commerceOrderNoteImpl.setNew(commerceOrderNote.isNew());
		commerceOrderNoteImpl.setPrimaryKey(commerceOrderNote.getPrimaryKey());

		commerceOrderNoteImpl.setCommerceOrderNoteId(commerceOrderNote.getCommerceOrderNoteId());
		commerceOrderNoteImpl.setGroupId(commerceOrderNote.getGroupId());
		commerceOrderNoteImpl.setCompanyId(commerceOrderNote.getCompanyId());
		commerceOrderNoteImpl.setUserId(commerceOrderNote.getUserId());
		commerceOrderNoteImpl.setUserName(commerceOrderNote.getUserName());
		commerceOrderNoteImpl.setCreateDate(commerceOrderNote.getCreateDate());
		commerceOrderNoteImpl.setModifiedDate(commerceOrderNote.getModifiedDate());
		commerceOrderNoteImpl.setCommerceOrderId(commerceOrderNote.getCommerceOrderId());
		commerceOrderNoteImpl.setContent(commerceOrderNote.getContent());
		commerceOrderNoteImpl.setRestricted(commerceOrderNote.isRestricted());

		return commerceOrderNoteImpl;
	}

	/**
	 * Returns the commerce order note with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce order note
	 * @return the commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	@Override
	public CommerceOrderNote findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrderNoteException {
		CommerceOrderNote commerceOrderNote = fetchByPrimaryKey(primaryKey);

		if (commerceOrderNote == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrderNoteException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceOrderNote;
	}

	/**
	 * Returns the commerce order note with the primary key or throws a {@link NoSuchOrderNoteException} if it could not be found.
	 *
	 * @param commerceOrderNoteId the primary key of the commerce order note
	 * @return the commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	@Override
	public CommerceOrderNote findByPrimaryKey(long commerceOrderNoteId)
		throws NoSuchOrderNoteException {
		return findByPrimaryKey((Serializable)commerceOrderNoteId);
	}

	/**
	 * Returns the commerce order note with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce order note
	 * @return the commerce order note, or <code>null</code> if a commerce order note with the primary key could not be found
	 */
	@Override
	public CommerceOrderNote fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
				CommerceOrderNoteImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceOrderNote commerceOrderNote = (CommerceOrderNote)serializable;

		if (commerceOrderNote == null) {
			Session session = null;

			try {
				session = openSession();

				commerceOrderNote = (CommerceOrderNote)session.get(CommerceOrderNoteImpl.class,
						primaryKey);

				if (commerceOrderNote != null) {
					cacheResult(commerceOrderNote);
				}
				else {
					entityCache.putResult(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
						CommerceOrderNoteImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
					CommerceOrderNoteImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceOrderNote;
	}

	/**
	 * Returns the commerce order note with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderNoteId the primary key of the commerce order note
	 * @return the commerce order note, or <code>null</code> if a commerce order note with the primary key could not be found
	 */
	@Override
	public CommerceOrderNote fetchByPrimaryKey(long commerceOrderNoteId) {
		return fetchByPrimaryKey((Serializable)commerceOrderNoteId);
	}

	@Override
	public Map<Serializable, CommerceOrderNote> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceOrderNote> map = new HashMap<Serializable, CommerceOrderNote>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceOrderNote commerceOrderNote = fetchByPrimaryKey(primaryKey);

			if (commerceOrderNote != null) {
				map.put(primaryKey, commerceOrderNote);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
					CommerceOrderNoteImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceOrderNote)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEORDERNOTE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CommerceOrderNote commerceOrderNote : (List<CommerceOrderNote>)q.list()) {
				map.put(commerceOrderNote.getPrimaryKeyObj(), commerceOrderNote);

				cacheResult(commerceOrderNote);

				uncachedPrimaryKeys.remove(commerceOrderNote.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceOrderNoteModelImpl.ENTITY_CACHE_ENABLED,
					CommerceOrderNoteImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the commerce order notes.
	 *
	 * @return the commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order notes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order notes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findAll(int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order notes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findAll(int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<CommerceOrderNote> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceOrderNote>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEORDERNOTE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEORDERNOTE;

				if (pagination) {
					sql = sql.concat(CommerceOrderNoteModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceOrderNote>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceOrderNote>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the commerce order notes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceOrderNote commerceOrderNote : findAll()) {
			remove(commerceOrderNote);
		}
	}

	/**
	 * Returns the number of commerce order notes.
	 *
	 * @return the number of commerce order notes
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEORDERNOTE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceOrderNoteModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce order note persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceOrderNoteImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_COMMERCEORDERNOTE = "SELECT commerceOrderNote FROM CommerceOrderNote commerceOrderNote";
	private static final String _SQL_SELECT_COMMERCEORDERNOTE_WHERE_PKS_IN = "SELECT commerceOrderNote FROM CommerceOrderNote commerceOrderNote WHERE commerceOrderNoteId IN (";
	private static final String _SQL_SELECT_COMMERCEORDERNOTE_WHERE = "SELECT commerceOrderNote FROM CommerceOrderNote commerceOrderNote WHERE ";
	private static final String _SQL_COUNT_COMMERCEORDERNOTE = "SELECT COUNT(commerceOrderNote) FROM CommerceOrderNote commerceOrderNote";
	private static final String _SQL_COUNT_COMMERCEORDERNOTE_WHERE = "SELECT COUNT(commerceOrderNote) FROM CommerceOrderNote commerceOrderNote WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceOrderNote.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceOrderNote exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceOrderNote exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceOrderNotePersistenceImpl.class);
}