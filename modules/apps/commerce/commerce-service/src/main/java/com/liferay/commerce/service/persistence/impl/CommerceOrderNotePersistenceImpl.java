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

import com.liferay.commerce.exception.NoSuchOrderNoteException;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.model.CommerceOrderNoteTable;
import com.liferay.commerce.model.impl.CommerceOrderNoteImpl;
import com.liferay.commerce.model.impl.CommerceOrderNoteModelImpl;
import com.liferay.commerce.service.persistence.CommerceOrderNotePersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce order note service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderNotePersistenceImpl
	extends BasePersistenceImpl<CommerceOrderNote>
	implements CommerceOrderNotePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceOrderNoteUtil</code> to access the commerce order note persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceOrderNoteImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceOrderId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceOrderId;
	private FinderPath _finderPathCountByCommerceOrderId;

	/**
	 * Returns all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByCommerceOrderId(long commerceOrderId) {
		return findByCommerceOrderId(
			commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {

		return findByCommerceOrderId(commerceOrderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCommerceOrderId;
				finderArgs = new Object[] {commerceOrderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceOrderId;
			finderArgs = new Object[] {
				commerceOrderId, start, end, orderByComparator
			};
		}

		List<CommerceOrderNote> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderNote>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderNote commerceOrderNote : list) {
					if (commerceOrderId !=
							commerceOrderNote.getCommerceOrderId()) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERNOTE_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderNoteModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				list = (List<CommerceOrderNote>)QueryUtil.list(
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
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote findByCommerceOrderId_First(
			long commerceOrderId,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException {

		CommerceOrderNote commerceOrderNote = fetchByCommerceOrderId_First(
			commerceOrderId, orderByComparator);

		if (commerceOrderNote != null) {
			return commerceOrderNote;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append("}");

		throw new NoSuchOrderNoteException(sb.toString());
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

		List<CommerceOrderNote> list = findByCommerceOrderId(
			commerceOrderId, 0, 1, orderByComparator);

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
	public CommerceOrderNote findByCommerceOrderId_Last(
			long commerceOrderId,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException {

		CommerceOrderNote commerceOrderNote = fetchByCommerceOrderId_Last(
			commerceOrderId, orderByComparator);

		if (commerceOrderNote != null) {
			return commerceOrderNote;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append("}");

		throw new NoSuchOrderNoteException(sb.toString());
	}

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote fetchByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		int count = countByCommerceOrderId(commerceOrderId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderNote> list = findByCommerceOrderId(
			commerceOrderId, count - 1, count, orderByComparator);

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

		CommerceOrderNote commerceOrderNote = findByPrimaryKey(
			commerceOrderNoteId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderNote[] array = new CommerceOrderNoteImpl[3];

			array[0] = getByCommerceOrderId_PrevAndNext(
				session, commerceOrderNote, commerceOrderId, orderByComparator,
				true);

			array[1] = commerceOrderNote;

			array[2] = getByCommerceOrderId_PrevAndNext(
				session, commerceOrderNote, commerceOrderId, orderByComparator,
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

	protected CommerceOrderNote getByCommerceOrderId_PrevAndNext(
		Session session, CommerceOrderNote commerceOrderNote,
		long commerceOrderId,
		OrderByComparator<CommerceOrderNote> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERNOTE_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

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
			sb.append(CommerceOrderNoteModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceOrderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderNote)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderNote> list = query.list();

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
		for (CommerceOrderNote commerceOrderNote :
				findByCommerceOrderId(
					commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathCountByCommerceOrderId;

		Object[] finderArgs = new Object[] {commerceOrderId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEORDERNOTE_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

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
		_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2 =
			"commerceOrderNote.commerceOrderId = ?";

	private FinderPath _finderPathWithPaginationFindByC_R;
	private FinderPath _finderPathWithoutPaginationFindByC_R;
	private FinderPath _finderPathCountByC_R;

	/**
	 * Returns all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @return the matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted) {

		return findByC_R(
			commerceOrderId, restricted, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @return the range of matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end) {

		return findByC_R(commerceOrderId, restricted, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return findByC_R(
			commerceOrderId, restricted, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_R;
				finderArgs = new Object[] {commerceOrderId, restricted};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_R;
			finderArgs = new Object[] {
				commerceOrderId, restricted, start, end, orderByComparator
			};
		}

		List<CommerceOrderNote> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderNote>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderNote commerceOrderNote : list) {
					if ((commerceOrderId !=
							commerceOrderNote.getCommerceOrderId()) ||
						(restricted != commerceOrderNote.isRestricted())) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERNOTE_WHERE);

			sb.append(_FINDER_COLUMN_C_R_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_C_R_RESTRICTED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderNoteModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				queryPos.add(restricted);

				list = (List<CommerceOrderNote>)QueryUtil.list(
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
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote findByC_R_First(
			long commerceOrderId, boolean restricted,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException {

		CommerceOrderNote commerceOrderNote = fetchByC_R_First(
			commerceOrderId, restricted, orderByComparator);

		if (commerceOrderNote != null) {
			return commerceOrderNote;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append(", restricted=");
		sb.append(restricted);

		sb.append("}");

		throw new NoSuchOrderNoteException(sb.toString());
	}

	/**
	 * Returns the first commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote fetchByC_R_First(
		long commerceOrderId, boolean restricted,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		List<CommerceOrderNote> list = findByC_R(
			commerceOrderId, restricted, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote findByC_R_Last(
			long commerceOrderId, boolean restricted,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException {

		CommerceOrderNote commerceOrderNote = fetchByC_R_Last(
			commerceOrderId, restricted, orderByComparator);

		if (commerceOrderNote != null) {
			return commerceOrderNote;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append(", restricted=");
		sb.append(restricted);

		sb.append("}");

		throw new NoSuchOrderNoteException(sb.toString());
	}

	/**
	 * Returns the last commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote fetchByC_R_Last(
		long commerceOrderId, boolean restricted,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		int count = countByC_R(commerceOrderId, restricted);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderNote> list = findByC_R(
			commerceOrderId, restricted, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order notes before and after the current commerce order note in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderNoteId the primary key of the current commerce order note
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order note
	 * @throws NoSuchOrderNoteException if a commerce order note with the primary key could not be found
	 */
	@Override
	public CommerceOrderNote[] findByC_R_PrevAndNext(
			long commerceOrderNoteId, long commerceOrderId, boolean restricted,
			OrderByComparator<CommerceOrderNote> orderByComparator)
		throws NoSuchOrderNoteException {

		CommerceOrderNote commerceOrderNote = findByPrimaryKey(
			commerceOrderNoteId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderNote[] array = new CommerceOrderNoteImpl[3];

			array[0] = getByC_R_PrevAndNext(
				session, commerceOrderNote, commerceOrderId, restricted,
				orderByComparator, true);

			array[1] = commerceOrderNote;

			array[2] = getByC_R_PrevAndNext(
				session, commerceOrderNote, commerceOrderId, restricted,
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

	protected CommerceOrderNote getByC_R_PrevAndNext(
		Session session, CommerceOrderNote commerceOrderNote,
		long commerceOrderId, boolean restricted,
		OrderByComparator<CommerceOrderNote> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERNOTE_WHERE);

		sb.append(_FINDER_COLUMN_C_R_COMMERCEORDERID_2);

		sb.append(_FINDER_COLUMN_C_R_RESTRICTED_2);

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
			sb.append(CommerceOrderNoteModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceOrderId);

		queryPos.add(restricted);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderNote)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderNote> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order notes where commerceOrderId = &#63; and restricted = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 */
	@Override
	public void removeByC_R(long commerceOrderId, boolean restricted) {
		for (CommerceOrderNote commerceOrderNote :
				findByC_R(
					commerceOrderId, restricted, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceOrderNote);
		}
	}

	/**
	 * Returns the number of commerce order notes where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @return the number of matching commerce order notes
	 */
	@Override
	public int countByC_R(long commerceOrderId, boolean restricted) {
		FinderPath finderPath = _finderPathCountByC_R;

		Object[] finderArgs = new Object[] {commerceOrderId, restricted};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEORDERNOTE_WHERE);

			sb.append(_FINDER_COLUMN_C_R_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_C_R_RESTRICTED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				queryPos.add(restricted);

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

	private static final String _FINDER_COLUMN_C_R_COMMERCEORDERID_2 =
		"commerceOrderNote.commerceOrderId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_RESTRICTED_2 =
		"commerceOrderNote.restricted = ?";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce order note where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchOrderNoteException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order note
	 * @throws NoSuchOrderNoteException if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrderNoteException {

		CommerceOrderNote commerceOrderNote = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (commerceOrderNote == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchOrderNoteException(sb.toString());
		}

		return commerceOrderNote;
	}

	/**
	 * Returns the commerce order note where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce order note where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order note, or <code>null</code> if a matching commerce order note could not be found
	 */
	@Override
	public CommerceOrderNote fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_ERC, finderArgs);
		}

		if (result instanceof CommerceOrderNote) {
			CommerceOrderNote commerceOrderNote = (CommerceOrderNote)result;

			if ((companyId != commerceOrderNote.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commerceOrderNote.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEORDERNOTE_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<CommerceOrderNote> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"CommerceOrderNotePersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceOrderNote commerceOrderNote = list.get(0);

					result = commerceOrderNote;

					cacheResult(commerceOrderNote);
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
			return (CommerceOrderNote)result;
		}
	}

	/**
	 * Removes the commerce order note where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce order note that was removed
	 */
	@Override
	public CommerceOrderNote removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrderNoteException {

		CommerceOrderNote commerceOrderNote = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(commerceOrderNote);
	}

	/**
	 * Returns the number of commerce order notes where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce order notes
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEORDERNOTE_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
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

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"commerceOrderNote.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commerceOrderNote.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commerceOrderNote.externalReferenceCode IS NULL OR commerceOrderNote.externalReferenceCode = '')";

	public CommerceOrderNotePersistenceImpl() {
		setModelClass(CommerceOrderNote.class);

		setModelImplClass(CommerceOrderNoteImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceOrderNoteTable.INSTANCE);
	}

	/**
	 * Caches the commerce order note in the entity cache if it is enabled.
	 *
	 * @param commerceOrderNote the commerce order note
	 */
	@Override
	public void cacheResult(CommerceOrderNote commerceOrderNote) {
		entityCache.putResult(
			CommerceOrderNoteImpl.class, commerceOrderNote.getPrimaryKey(),
			commerceOrderNote);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commerceOrderNote.getCompanyId(),
				commerceOrderNote.getExternalReferenceCode()
			},
			commerceOrderNote);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce order notes in the entity cache if it is enabled.
	 *
	 * @param commerceOrderNotes the commerce order notes
	 */
	@Override
	public void cacheResult(List<CommerceOrderNote> commerceOrderNotes) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceOrderNotes.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceOrderNote commerceOrderNote : commerceOrderNotes) {
			if (entityCache.getResult(
					CommerceOrderNoteImpl.class,
					commerceOrderNote.getPrimaryKey()) == null) {

				cacheResult(commerceOrderNote);
			}
		}
	}

	/**
	 * Clears the cache for all commerce order notes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceOrderNoteImpl.class);

		finderCache.clearCache(CommerceOrderNoteImpl.class);
	}

	/**
	 * Clears the cache for the commerce order note.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceOrderNote commerceOrderNote) {
		entityCache.removeResult(
			CommerceOrderNoteImpl.class, commerceOrderNote);
	}

	@Override
	public void clearCache(List<CommerceOrderNote> commerceOrderNotes) {
		for (CommerceOrderNote commerceOrderNote : commerceOrderNotes) {
			entityCache.removeResult(
				CommerceOrderNoteImpl.class, commerceOrderNote);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceOrderNoteImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceOrderNoteImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceOrderNoteModelImpl commerceOrderNoteModelImpl) {

		Object[] args = new Object[] {
			commerceOrderNoteModelImpl.getCompanyId(),
			commerceOrderNoteModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, commerceOrderNoteModelImpl);
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

		commerceOrderNote.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			CommerceOrderNote commerceOrderNote =
				(CommerceOrderNote)session.get(
					CommerceOrderNoteImpl.class, primaryKey);

			if (commerceOrderNote == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrderNoteException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceOrderNote);
		}
		catch (NoSuchOrderNoteException noSuchEntityException) {
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
	protected CommerceOrderNote removeImpl(
		CommerceOrderNote commerceOrderNote) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceOrderNote)) {
				commerceOrderNote = (CommerceOrderNote)session.get(
					CommerceOrderNoteImpl.class,
					commerceOrderNote.getPrimaryKeyObj());
			}

			if (commerceOrderNote != null) {
				session.delete(commerceOrderNote);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
		boolean isNew = commerceOrderNote.isNew();

		if (!(commerceOrderNote instanceof CommerceOrderNoteModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceOrderNote.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceOrderNote);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceOrderNote proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceOrderNote implementation " +
					commerceOrderNote.getClass());
		}

		CommerceOrderNoteModelImpl commerceOrderNoteModelImpl =
			(CommerceOrderNoteModelImpl)commerceOrderNote;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceOrderNote.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceOrderNote.setCreateDate(date);
			}
			else {
				commerceOrderNote.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceOrderNoteModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceOrderNote.setModifiedDate(date);
			}
			else {
				commerceOrderNote.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceOrderNote);
			}
			else {
				commerceOrderNote = (CommerceOrderNote)session.merge(
					commerceOrderNote);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceOrderNoteImpl.class, commerceOrderNoteModelImpl, false,
			true);

		cacheUniqueFindersCache(commerceOrderNoteModelImpl);

		if (isNew) {
			commerceOrderNote.setNew(false);
		}

		commerceOrderNote.resetOriginalValues();

		return commerceOrderNote;
	}

	/**
	 * Returns the commerce order note with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
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

			throw new NoSuchOrderNoteException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceOrderNote;
	}

	/**
	 * Returns the commerce order note with the primary key or throws a <code>NoSuchOrderNoteException</code> if it could not be found.
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
	 * @param commerceOrderNoteId the primary key of the commerce order note
	 * @return the commerce order note, or <code>null</code> if a commerce order note with the primary key could not be found
	 */
	@Override
	public CommerceOrderNote fetchByPrimaryKey(long commerceOrderNoteId) {
		return fetchByPrimaryKey((Serializable)commerceOrderNoteId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order notes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderNoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order notes
	 * @param end the upper bound of the range of commerce order notes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order notes
	 */
	@Override
	public List<CommerceOrderNote> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderNote> orderByComparator,
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

		List<CommerceOrderNote> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderNote>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEORDERNOTE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEORDERNOTE;

				sql = sql.concat(CommerceOrderNoteModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceOrderNote>)QueryUtil.list(
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCEORDERNOTE);

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
		return "commerceOrderNoteId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEORDERNOTE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceOrderNoteModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce order note persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByCommerceOrderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceOrderId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceOrderId"}, true);

		_finderPathWithoutPaginationFindByCommerceOrderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCommerceOrderId",
			new String[] {Long.class.getName()},
			new String[] {"commerceOrderId"}, true);

		_finderPathCountByCommerceOrderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCommerceOrderId",
			new String[] {Long.class.getName()},
			new String[] {"commerceOrderId"}, false);

		_finderPathWithPaginationFindByC_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commerceOrderId", "restricted"}, true);

		_finderPathWithoutPaginationFindByC_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_R",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"commerceOrderId", "restricted"}, true);

		_finderPathCountByC_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"commerceOrderId", "restricted"}, false);

		_finderPathFetchByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, true);

		_finderPathCountByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceOrderNoteImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEORDERNOTE =
		"SELECT commerceOrderNote FROM CommerceOrderNote commerceOrderNote";

	private static final String _SQL_SELECT_COMMERCEORDERNOTE_WHERE =
		"SELECT commerceOrderNote FROM CommerceOrderNote commerceOrderNote WHERE ";

	private static final String _SQL_COUNT_COMMERCEORDERNOTE =
		"SELECT COUNT(commerceOrderNote) FROM CommerceOrderNote commerceOrderNote";

	private static final String _SQL_COUNT_COMMERCEORDERNOTE_WHERE =
		"SELECT COUNT(commerceOrderNote) FROM CommerceOrderNote commerceOrderNote WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceOrderNote.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceOrderNote exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceOrderNote exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderNotePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}