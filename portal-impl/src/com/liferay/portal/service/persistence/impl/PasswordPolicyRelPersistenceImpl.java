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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.NoSuchPasswordPolicyRelException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.model.impl.PasswordPolicyRelImpl;
import com.liferay.portal.model.impl.PasswordPolicyRelModelImpl;
import com.liferay.portal.service.persistence.PasswordPolicyRelPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the password policy rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyRelPersistence
 * @see PasswordPolicyRelUtil
 * @generated
 */
public class PasswordPolicyRelPersistenceImpl extends BasePersistenceImpl<PasswordPolicyRel>
	implements PasswordPolicyRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PasswordPolicyRelUtil} to access the password policy rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PasswordPolicyRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PASSWORDPOLICYID =
		new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPasswordPolicyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PASSWORDPOLICYID =
		new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPasswordPolicyId", new String[] { Long.class.getName() },
			PasswordPolicyRelModelImpl.PASSWORDPOLICYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PASSWORDPOLICYID = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPasswordPolicyId", new String[] { Long.class.getName() });

	/**
	 * Returns all the password policy rels where passwordPolicyId = &#63;.
	 *
	 * @param passwordPolicyId the password policy ID
	 * @return the matching password policy rels
	 */
	@Override
	public List<PasswordPolicyRel> findByPasswordPolicyId(long passwordPolicyId) {
		return findByPasswordPolicyId(passwordPolicyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password policy rels where passwordPolicyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param passwordPolicyId the password policy ID
	 * @param start the lower bound of the range of password policy rels
	 * @param end the upper bound of the range of password policy rels (not inclusive)
	 * @return the range of matching password policy rels
	 */
	@Override
	public List<PasswordPolicyRel> findByPasswordPolicyId(
		long passwordPolicyId, int start, int end) {
		return findByPasswordPolicyId(passwordPolicyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the password policy rels where passwordPolicyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param passwordPolicyId the password policy ID
	 * @param start the lower bound of the range of password policy rels
	 * @param end the upper bound of the range of password policy rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policy rels
	 */
	@Override
	public List<PasswordPolicyRel> findByPasswordPolicyId(
		long passwordPolicyId, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PASSWORDPOLICYID;
			finderArgs = new Object[] { passwordPolicyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PASSWORDPOLICYID;
			finderArgs = new Object[] {
					passwordPolicyId,
					
					start, end, orderByComparator
				};
		}

		List<PasswordPolicyRel> list = (List<PasswordPolicyRel>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PasswordPolicyRel passwordPolicyRel : list) {
				if ((passwordPolicyId != passwordPolicyRel.getPasswordPolicyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

			query.append(_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PasswordPolicyRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(passwordPolicyId);

				if (!pagination) {
					list = (List<PasswordPolicyRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PasswordPolicyRel>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first password policy rel in the ordered set where passwordPolicyId = &#63;.
	 *
	 * @param passwordPolicyId the password policy ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy rel
	 * @throws com.liferay.portal.NoSuchPasswordPolicyRelException if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel findByPasswordPolicyId_First(
		long passwordPolicyId, OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyRelException {
		PasswordPolicyRel passwordPolicyRel = fetchByPasswordPolicyId_First(passwordPolicyId,
				orderByComparator);

		if (passwordPolicyRel != null) {
			return passwordPolicyRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("passwordPolicyId=");
		msg.append(passwordPolicyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPasswordPolicyRelException(msg.toString());
	}

	/**
	 * Returns the first password policy rel in the ordered set where passwordPolicyId = &#63;.
	 *
	 * @param passwordPolicyId the password policy ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy rel, or <code>null</code> if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel fetchByPasswordPolicyId_First(
		long passwordPolicyId, OrderByComparator orderByComparator) {
		List<PasswordPolicyRel> list = findByPasswordPolicyId(passwordPolicyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last password policy rel in the ordered set where passwordPolicyId = &#63;.
	 *
	 * @param passwordPolicyId the password policy ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy rel
	 * @throws com.liferay.portal.NoSuchPasswordPolicyRelException if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel findByPasswordPolicyId_Last(
		long passwordPolicyId, OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyRelException {
		PasswordPolicyRel passwordPolicyRel = fetchByPasswordPolicyId_Last(passwordPolicyId,
				orderByComparator);

		if (passwordPolicyRel != null) {
			return passwordPolicyRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("passwordPolicyId=");
		msg.append(passwordPolicyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPasswordPolicyRelException(msg.toString());
	}

	/**
	 * Returns the last password policy rel in the ordered set where passwordPolicyId = &#63;.
	 *
	 * @param passwordPolicyId the password policy ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy rel, or <code>null</code> if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel fetchByPasswordPolicyId_Last(
		long passwordPolicyId, OrderByComparator orderByComparator) {
		int count = countByPasswordPolicyId(passwordPolicyId);

		if (count == 0) {
			return null;
		}

		List<PasswordPolicyRel> list = findByPasswordPolicyId(passwordPolicyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the password policy rels before and after the current password policy rel in the ordered set where passwordPolicyId = &#63;.
	 *
	 * @param passwordPolicyRelId the primary key of the current password policy rel
	 * @param passwordPolicyId the password policy ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy rel
	 * @throws com.liferay.portal.NoSuchPasswordPolicyRelException if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel[] findByPasswordPolicyId_PrevAndNext(
		long passwordPolicyRelId, long passwordPolicyId,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyRelException {
		PasswordPolicyRel passwordPolicyRel = findByPrimaryKey(passwordPolicyRelId);

		Session session = null;

		try {
			session = openSession();

			PasswordPolicyRel[] array = new PasswordPolicyRelImpl[3];

			array[0] = getByPasswordPolicyId_PrevAndNext(session,
					passwordPolicyRel, passwordPolicyId, orderByComparator, true);

			array[1] = passwordPolicyRel;

			array[2] = getByPasswordPolicyId_PrevAndNext(session,
					passwordPolicyRel, passwordPolicyId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PasswordPolicyRel getByPasswordPolicyId_PrevAndNext(
		Session session, PasswordPolicyRel passwordPolicyRel,
		long passwordPolicyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

		query.append(_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2);

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
			query.append(PasswordPolicyRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(passwordPolicyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(passwordPolicyRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PasswordPolicyRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the password policy rels where passwordPolicyId = &#63; from the database.
	 *
	 * @param passwordPolicyId the password policy ID
	 */
	@Override
	public void removeByPasswordPolicyId(long passwordPolicyId) {
		for (PasswordPolicyRel passwordPolicyRel : findByPasswordPolicyId(
				passwordPolicyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(passwordPolicyRel);
		}
	}

	/**
	 * Returns the number of password policy rels where passwordPolicyId = &#63;.
	 *
	 * @param passwordPolicyId the password policy ID
	 * @return the number of matching password policy rels
	 */
	@Override
	public int countByPasswordPolicyId(long passwordPolicyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PASSWORDPOLICYID;

		Object[] finderArgs = new Object[] { passwordPolicyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PASSWORDPOLICYREL_WHERE);

			query.append(_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(passwordPolicyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2 =
		"passwordPolicyRel.passwordPolicyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			PasswordPolicyRelModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			PasswordPolicyRelModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the password policy rel where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchPasswordPolicyRelException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching password policy rel
	 * @throws com.liferay.portal.NoSuchPasswordPolicyRelException if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel findByC_C(long classNameId, long classPK)
		throws NoSuchPasswordPolicyRelException {
		PasswordPolicyRel passwordPolicyRel = fetchByC_C(classNameId, classPK);

		if (passwordPolicyRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPasswordPolicyRelException(msg.toString());
		}

		return passwordPolicyRel;
	}

	/**
	 * Returns the password policy rel where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching password policy rel, or <code>null</code> if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the password policy rel where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching password policy rel, or <code>null</code> if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result instanceof PasswordPolicyRel) {
			PasswordPolicyRel passwordPolicyRel = (PasswordPolicyRel)result;

			if ((classNameId != passwordPolicyRel.getClassNameId()) ||
					(classPK != passwordPolicyRel.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<PasswordPolicyRel> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					PasswordPolicyRel passwordPolicyRel = list.get(0);

					result = passwordPolicyRel;

					cacheResult(passwordPolicyRel);

					if ((passwordPolicyRel.getClassNameId() != classNameId) ||
							(passwordPolicyRel.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, passwordPolicyRel);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs);

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
			return (PasswordPolicyRel)result;
		}
	}

	/**
	 * Removes the password policy rel where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the password policy rel that was removed
	 */
	@Override
	public PasswordPolicyRel removeByC_C(long classNameId, long classPK)
		throws NoSuchPasswordPolicyRelException {
		PasswordPolicyRel passwordPolicyRel = findByC_C(classNameId, classPK);

		return remove(passwordPolicyRel);
	}

	/**
	 * Returns the number of password policy rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching password policy rels
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PASSWORDPOLICYREL_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "passwordPolicyRel.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "passwordPolicyRel.classPK = ?";

	public PasswordPolicyRelPersistenceImpl() {
		setModelClass(PasswordPolicyRel.class);
	}

	/**
	 * Caches the password policy rel in the entity cache if it is enabled.
	 *
	 * @param passwordPolicyRel the password policy rel
	 */
	@Override
	public void cacheResult(PasswordPolicyRel passwordPolicyRel) {
		EntityCacheUtil.putResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelImpl.class, passwordPolicyRel.getPrimaryKey(),
			passwordPolicyRel);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				passwordPolicyRel.getClassNameId(),
				passwordPolicyRel.getClassPK()
			}, passwordPolicyRel);

		passwordPolicyRel.resetOriginalValues();
	}

	/**
	 * Caches the password policy rels in the entity cache if it is enabled.
	 *
	 * @param passwordPolicyRels the password policy rels
	 */
	@Override
	public void cacheResult(List<PasswordPolicyRel> passwordPolicyRels) {
		for (PasswordPolicyRel passwordPolicyRel : passwordPolicyRels) {
			if (EntityCacheUtil.getResult(
						PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
						PasswordPolicyRelImpl.class,
						passwordPolicyRel.getPrimaryKey()) == null) {
				cacheResult(passwordPolicyRel);
			}
			else {
				passwordPolicyRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all password policy rels.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PasswordPolicyRelImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PasswordPolicyRelImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the password policy rel.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PasswordPolicyRel passwordPolicyRel) {
		EntityCacheUtil.removeResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelImpl.class, passwordPolicyRel.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(passwordPolicyRel);
	}

	@Override
	public void clearCache(List<PasswordPolicyRel> passwordPolicyRels) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PasswordPolicyRel passwordPolicyRel : passwordPolicyRels) {
			EntityCacheUtil.removeResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
				PasswordPolicyRelImpl.class, passwordPolicyRel.getPrimaryKey());

			clearUniqueFindersCache(passwordPolicyRel);
		}
	}

	protected void cacheUniqueFindersCache(PasswordPolicyRel passwordPolicyRel) {
		if (passwordPolicyRel.isNew()) {
			Object[] args = new Object[] {
					passwordPolicyRel.getClassNameId(),
					passwordPolicyRel.getClassPK()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C, args,
				passwordPolicyRel);
		}
		else {
			PasswordPolicyRelModelImpl passwordPolicyRelModelImpl = (PasswordPolicyRelModelImpl)passwordPolicyRel;

			if ((passwordPolicyRelModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						passwordPolicyRel.getClassNameId(),
						passwordPolicyRel.getClassPK()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C, args,
					passwordPolicyRel);
			}
		}
	}

	protected void clearUniqueFindersCache(PasswordPolicyRel passwordPolicyRel) {
		PasswordPolicyRelModelImpl passwordPolicyRelModelImpl = (PasswordPolicyRelModelImpl)passwordPolicyRel;

		Object[] args = new Object[] {
				passwordPolicyRel.getClassNameId(),
				passwordPolicyRel.getClassPK()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C, args);

		if ((passwordPolicyRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
			args = new Object[] {
					passwordPolicyRelModelImpl.getOriginalClassNameId(),
					passwordPolicyRelModelImpl.getOriginalClassPK()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}
	}

	/**
	 * Creates a new password policy rel with the primary key. Does not add the password policy rel to the database.
	 *
	 * @param passwordPolicyRelId the primary key for the new password policy rel
	 * @return the new password policy rel
	 */
	@Override
	public PasswordPolicyRel create(long passwordPolicyRelId) {
		PasswordPolicyRel passwordPolicyRel = new PasswordPolicyRelImpl();

		passwordPolicyRel.setNew(true);
		passwordPolicyRel.setPrimaryKey(passwordPolicyRelId);

		return passwordPolicyRel;
	}

	/**
	 * Removes the password policy rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param passwordPolicyRelId the primary key of the password policy rel
	 * @return the password policy rel that was removed
	 * @throws com.liferay.portal.NoSuchPasswordPolicyRelException if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel remove(long passwordPolicyRelId)
		throws NoSuchPasswordPolicyRelException {
		return remove((Serializable)passwordPolicyRelId);
	}

	/**
	 * Removes the password policy rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the password policy rel
	 * @return the password policy rel that was removed
	 * @throws com.liferay.portal.NoSuchPasswordPolicyRelException if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel remove(Serializable primaryKey)
		throws NoSuchPasswordPolicyRelException {
		Session session = null;

		try {
			session = openSession();

			PasswordPolicyRel passwordPolicyRel = (PasswordPolicyRel)session.get(PasswordPolicyRelImpl.class,
					primaryKey);

			if (passwordPolicyRel == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPasswordPolicyRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(passwordPolicyRel);
		}
		catch (NoSuchPasswordPolicyRelException nsee) {
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
	protected PasswordPolicyRel removeImpl(PasswordPolicyRel passwordPolicyRel) {
		passwordPolicyRel = toUnwrappedModel(passwordPolicyRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(passwordPolicyRel)) {
				passwordPolicyRel = (PasswordPolicyRel)session.get(PasswordPolicyRelImpl.class,
						passwordPolicyRel.getPrimaryKeyObj());
			}

			if (passwordPolicyRel != null) {
				session.delete(passwordPolicyRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (passwordPolicyRel != null) {
			clearCache(passwordPolicyRel);
		}

		return passwordPolicyRel;
	}

	@Override
	public PasswordPolicyRel updateImpl(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel) {
		passwordPolicyRel = toUnwrappedModel(passwordPolicyRel);

		boolean isNew = passwordPolicyRel.isNew();

		PasswordPolicyRelModelImpl passwordPolicyRelModelImpl = (PasswordPolicyRelModelImpl)passwordPolicyRel;

		Session session = null;

		try {
			session = openSession();

			if (passwordPolicyRel.isNew()) {
				session.save(passwordPolicyRel);

				passwordPolicyRel.setNew(false);
			}
			else {
				session.merge(passwordPolicyRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PasswordPolicyRelModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((passwordPolicyRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PASSWORDPOLICYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						passwordPolicyRelModelImpl.getOriginalPasswordPolicyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PASSWORDPOLICYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PASSWORDPOLICYID,
					args);

				args = new Object[] {
						passwordPolicyRelModelImpl.getPasswordPolicyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PASSWORDPOLICYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PASSWORDPOLICYID,
					args);
			}
		}

		EntityCacheUtil.putResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyRelImpl.class, passwordPolicyRel.getPrimaryKey(),
			passwordPolicyRel, false);

		clearUniqueFindersCache(passwordPolicyRel);
		cacheUniqueFindersCache(passwordPolicyRel);

		passwordPolicyRel.resetOriginalValues();

		return passwordPolicyRel;
	}

	protected PasswordPolicyRel toUnwrappedModel(
		PasswordPolicyRel passwordPolicyRel) {
		if (passwordPolicyRel instanceof PasswordPolicyRelImpl) {
			return passwordPolicyRel;
		}

		PasswordPolicyRelImpl passwordPolicyRelImpl = new PasswordPolicyRelImpl();

		passwordPolicyRelImpl.setNew(passwordPolicyRel.isNew());
		passwordPolicyRelImpl.setPrimaryKey(passwordPolicyRel.getPrimaryKey());

		passwordPolicyRelImpl.setMvccVersion(passwordPolicyRel.getMvccVersion());
		passwordPolicyRelImpl.setPasswordPolicyRelId(passwordPolicyRel.getPasswordPolicyRelId());
		passwordPolicyRelImpl.setPasswordPolicyId(passwordPolicyRel.getPasswordPolicyId());
		passwordPolicyRelImpl.setClassNameId(passwordPolicyRel.getClassNameId());
		passwordPolicyRelImpl.setClassPK(passwordPolicyRel.getClassPK());

		return passwordPolicyRelImpl;
	}

	/**
	 * Returns the password policy rel with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the password policy rel
	 * @return the password policy rel
	 * @throws com.liferay.portal.NoSuchPasswordPolicyRelException if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPasswordPolicyRelException {
		PasswordPolicyRel passwordPolicyRel = fetchByPrimaryKey(primaryKey);

		if (passwordPolicyRel == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPasswordPolicyRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return passwordPolicyRel;
	}

	/**
	 * Returns the password policy rel with the primary key or throws a {@link com.liferay.portal.NoSuchPasswordPolicyRelException} if it could not be found.
	 *
	 * @param passwordPolicyRelId the primary key of the password policy rel
	 * @return the password policy rel
	 * @throws com.liferay.portal.NoSuchPasswordPolicyRelException if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel findByPrimaryKey(long passwordPolicyRelId)
		throws NoSuchPasswordPolicyRelException {
		return findByPrimaryKey((Serializable)passwordPolicyRelId);
	}

	/**
	 * Returns the password policy rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the password policy rel
	 * @return the password policy rel, or <code>null</code> if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel fetchByPrimaryKey(Serializable primaryKey) {
		PasswordPolicyRel passwordPolicyRel = (PasswordPolicyRel)EntityCacheUtil.getResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
				PasswordPolicyRelImpl.class, primaryKey);

		if (passwordPolicyRel == _nullPasswordPolicyRel) {
			return null;
		}

		if (passwordPolicyRel == null) {
			Session session = null;

			try {
				session = openSession();

				passwordPolicyRel = (PasswordPolicyRel)session.get(PasswordPolicyRelImpl.class,
						primaryKey);

				if (passwordPolicyRel != null) {
					cacheResult(passwordPolicyRel);
				}
				else {
					EntityCacheUtil.putResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
						PasswordPolicyRelImpl.class, primaryKey,
						_nullPasswordPolicyRel);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
					PasswordPolicyRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return passwordPolicyRel;
	}

	/**
	 * Returns the password policy rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param passwordPolicyRelId the primary key of the password policy rel
	 * @return the password policy rel, or <code>null</code> if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel fetchByPrimaryKey(long passwordPolicyRelId) {
		return fetchByPrimaryKey((Serializable)passwordPolicyRelId);
	}

	@Override
	public Map<Serializable, PasswordPolicyRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PasswordPolicyRel> map = new HashMap<Serializable, PasswordPolicyRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PasswordPolicyRel passwordPolicyRel = fetchByPrimaryKey(primaryKey);

			if (passwordPolicyRel != null) {
				map.put(primaryKey, passwordPolicyRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			PasswordPolicyRel passwordPolicyRel = (PasswordPolicyRel)EntityCacheUtil.getResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
					PasswordPolicyRelImpl.class, primaryKey);

			if (passwordPolicyRel == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, passwordPolicyRel);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (PasswordPolicyRel passwordPolicyRel : (List<PasswordPolicyRel>)q.list()) {
				map.put(passwordPolicyRel.getPrimaryKeyObj(), passwordPolicyRel);

				cacheResult(passwordPolicyRel);

				uncachedPrimaryKeys.remove(passwordPolicyRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(PasswordPolicyRelModelImpl.ENTITY_CACHE_ENABLED,
					PasswordPolicyRelImpl.class, primaryKey,
					_nullPasswordPolicyRel);
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
	 * Returns all the password policy rels.
	 *
	 * @return the password policy rels
	 */
	@Override
	public List<PasswordPolicyRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password policy rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policy rels
	 * @param end the upper bound of the range of password policy rels (not inclusive)
	 * @return the range of password policy rels
	 */
	@Override
	public List<PasswordPolicyRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the password policy rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policy rels
	 * @param end the upper bound of the range of password policy rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of password policy rels
	 */
	@Override
	public List<PasswordPolicyRel> findAll(int start, int end,
		OrderByComparator orderByComparator) {
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

		List<PasswordPolicyRel> list = (List<PasswordPolicyRel>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PASSWORDPOLICYREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PASSWORDPOLICYREL;

				if (pagination) {
					sql = sql.concat(PasswordPolicyRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PasswordPolicyRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PasswordPolicyRel>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the password policy rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PasswordPolicyRel passwordPolicyRel : findAll()) {
			remove(passwordPolicyRel);
		}
	}

	/**
	 * Returns the number of password policy rels.
	 *
	 * @return the number of password policy rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PASSWORDPOLICYREL);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the password policy rel persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PasswordPolicyRel")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PasswordPolicyRel>> listenersList = new ArrayList<ModelListener<PasswordPolicyRel>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PasswordPolicyRel>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PasswordPolicyRelImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PASSWORDPOLICYREL = "SELECT passwordPolicyRel FROM PasswordPolicyRel passwordPolicyRel";
	private static final String _SQL_SELECT_PASSWORDPOLICYREL_WHERE_PKS_IN = "SELECT passwordPolicyRel FROM PasswordPolicyRel passwordPolicyRel WHERE passwordPolicyRelId IN (";
	private static final String _SQL_SELECT_PASSWORDPOLICYREL_WHERE = "SELECT passwordPolicyRel FROM PasswordPolicyRel passwordPolicyRel WHERE ";
	private static final String _SQL_COUNT_PASSWORDPOLICYREL = "SELECT COUNT(passwordPolicyRel) FROM PasswordPolicyRel passwordPolicyRel";
	private static final String _SQL_COUNT_PASSWORDPOLICYREL_WHERE = "SELECT COUNT(passwordPolicyRel) FROM PasswordPolicyRel passwordPolicyRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "passwordPolicyRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PasswordPolicyRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PasswordPolicyRel exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(PasswordPolicyRelPersistenceImpl.class);
	private static PasswordPolicyRel _nullPasswordPolicyRel = new PasswordPolicyRelImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PasswordPolicyRel> toCacheModel() {
				return _nullPasswordPolicyRelCacheModel;
			}
		};

	private static CacheModel<PasswordPolicyRel> _nullPasswordPolicyRelCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<PasswordPolicyRel>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return 0;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public PasswordPolicyRel toEntityModel() {
			return _nullPasswordPolicyRel;
		}
	}
}