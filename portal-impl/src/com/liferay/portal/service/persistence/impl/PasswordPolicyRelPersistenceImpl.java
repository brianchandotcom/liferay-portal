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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchPasswordPolicyRelException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PasswordPolicyRel;
import com.liferay.portal.kernel.model.PasswordPolicyRelTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.PasswordPolicyRelPersistence;
import com.liferay.portal.kernel.service.persistence.PasswordPolicyRelUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.PasswordPolicyRelImpl;
import com.liferay.portal.model.impl.PasswordPolicyRelModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * @generated
 */
public class PasswordPolicyRelPersistenceImpl
	extends BasePersistenceImpl<PasswordPolicyRel>
	implements PasswordPolicyRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PasswordPolicyRelUtil</code> to access the password policy rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PasswordPolicyRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByPasswordPolicyId;
	private FinderPath _finderPathWithoutPaginationFindByPasswordPolicyId;
	private FinderPath _finderPathCountByPasswordPolicyId;

	/**
	 * Returns all the password policy rels where passwordPolicyId = &#63;.
	 *
	 * @param passwordPolicyId the password policy ID
	 * @return the matching password policy rels
	 */
	@Override
	public List<PasswordPolicyRel> findByPasswordPolicyId(
		long passwordPolicyId) {

		return findByPasswordPolicyId(
			passwordPolicyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password policy rels where passwordPolicyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyRelModelImpl</code>.
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
		OrderByComparator<PasswordPolicyRel> orderByComparator) {

		return findByPasswordPolicyId(
			passwordPolicyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the password policy rels where passwordPolicyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyRelModelImpl</code>.
	 * </p>
	 *
	 * @param passwordPolicyId the password policy ID
	 * @param start the lower bound of the range of password policy rels
	 * @param end the upper bound of the range of password policy rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching password policy rels
	 */
	@Override
	public List<PasswordPolicyRel> findByPasswordPolicyId(
		long passwordPolicyId, int start, int end,
		OrderByComparator<PasswordPolicyRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByPasswordPolicyId;
				finderArgs = new Object[] {passwordPolicyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByPasswordPolicyId;
			finderArgs = new Object[] {
				passwordPolicyId, start, end, orderByComparator
			};
		}

		List<PasswordPolicyRel> list = null;

		if (useFinderCache) {
			list = (List<PasswordPolicyRel>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PasswordPolicyRel passwordPolicyRel : list) {
					if (passwordPolicyId !=
							passwordPolicyRel.getPasswordPolicyId()) {

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

			sb.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

			sb.append(_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PasswordPolicyRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(passwordPolicyId);

				list = (List<PasswordPolicyRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first password policy rel in the ordered set where passwordPolicyId = &#63;.
	 *
	 * @param passwordPolicyId the password policy ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy rel
	 * @throws NoSuchPasswordPolicyRelException if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel findByPasswordPolicyId_First(
			long passwordPolicyId,
			OrderByComparator<PasswordPolicyRel> orderByComparator)
		throws NoSuchPasswordPolicyRelException {

		PasswordPolicyRel passwordPolicyRel = fetchByPasswordPolicyId_First(
			passwordPolicyId, orderByComparator);

		if (passwordPolicyRel != null) {
			return passwordPolicyRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("passwordPolicyId=");
		sb.append(passwordPolicyId);

		sb.append("}");

		throw new NoSuchPasswordPolicyRelException(sb.toString());
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
		long passwordPolicyId,
		OrderByComparator<PasswordPolicyRel> orderByComparator) {

		List<PasswordPolicyRel> list = findByPasswordPolicyId(
			passwordPolicyId, 0, 1, orderByComparator);

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
	 * @throws NoSuchPasswordPolicyRelException if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel findByPasswordPolicyId_Last(
			long passwordPolicyId,
			OrderByComparator<PasswordPolicyRel> orderByComparator)
		throws NoSuchPasswordPolicyRelException {

		PasswordPolicyRel passwordPolicyRel = fetchByPasswordPolicyId_Last(
			passwordPolicyId, orderByComparator);

		if (passwordPolicyRel != null) {
			return passwordPolicyRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("passwordPolicyId=");
		sb.append(passwordPolicyId);

		sb.append("}");

		throw new NoSuchPasswordPolicyRelException(sb.toString());
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
		long passwordPolicyId,
		OrderByComparator<PasswordPolicyRel> orderByComparator) {

		int count = countByPasswordPolicyId(passwordPolicyId);

		if (count == 0) {
			return null;
		}

		List<PasswordPolicyRel> list = findByPasswordPolicyId(
			passwordPolicyId, count - 1, count, orderByComparator);

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
	 * @throws NoSuchPasswordPolicyRelException if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel[] findByPasswordPolicyId_PrevAndNext(
			long passwordPolicyRelId, long passwordPolicyId,
			OrderByComparator<PasswordPolicyRel> orderByComparator)
		throws NoSuchPasswordPolicyRelException {

		PasswordPolicyRel passwordPolicyRel = findByPrimaryKey(
			passwordPolicyRelId);

		Session session = null;

		try {
			session = openSession();

			PasswordPolicyRel[] array = new PasswordPolicyRelImpl[3];

			array[0] = getByPasswordPolicyId_PrevAndNext(
				session, passwordPolicyRel, passwordPolicyId, orderByComparator,
				true);

			array[1] = passwordPolicyRel;

			array[2] = getByPasswordPolicyId_PrevAndNext(
				session, passwordPolicyRel, passwordPolicyId, orderByComparator,
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

	protected PasswordPolicyRel getByPasswordPolicyId_PrevAndNext(
		Session session, PasswordPolicyRel passwordPolicyRel,
		long passwordPolicyId,
		OrderByComparator<PasswordPolicyRel> orderByComparator,
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

		sb.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

		sb.append(_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2);

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
			sb.append(PasswordPolicyRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(passwordPolicyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						passwordPolicyRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PasswordPolicyRel> list = query.list();

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
		for (PasswordPolicyRel passwordPolicyRel :
				findByPasswordPolicyId(
					passwordPolicyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathCountByPasswordPolicyId;

		Object[] finderArgs = new Object[] {passwordPolicyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PASSWORDPOLICYREL_WHERE);

			sb.append(_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(passwordPolicyId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		_FINDER_COLUMN_PASSWORDPOLICYID_PASSWORDPOLICYID_2 =
			"passwordPolicyRel.passwordPolicyId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the password policy rel where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchPasswordPolicyRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching password policy rel
	 * @throws NoSuchPasswordPolicyRelException if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel findByC_C(long classNameId, long classPK)
		throws NoSuchPasswordPolicyRelException {

		PasswordPolicyRel passwordPolicyRel = fetchByC_C(classNameId, classPK);

		if (passwordPolicyRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPasswordPolicyRelException(sb.toString());
		}

		return passwordPolicyRel;
	}

	/**
	 * Returns the password policy rel where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
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
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching password policy rel, or <code>null</code> if a matching password policy rel could not be found
	 */
	@Override
	public PasswordPolicyRel fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof PasswordPolicyRel) {
			PasswordPolicyRel passwordPolicyRel = (PasswordPolicyRel)result;

			if ((classNameId != passwordPolicyRel.getClassNameId()) ||
				(classPK != passwordPolicyRel.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_PASSWORDPOLICYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<PasswordPolicyRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					PasswordPolicyRel passwordPolicyRel = list.get(0);

					result = passwordPolicyRel;

					cacheResult(passwordPolicyRel);
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
			return (PasswordPolicyRel)result;
		}
	}

	/**
	 * Removes the password policy rel where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
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
	 * @param classPK the class pk
	 * @return the number of matching password policy rels
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_PASSWORDPOLICYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"passwordPolicyRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"passwordPolicyRel.classPK = ?";

	public PasswordPolicyRelPersistenceImpl() {
		setModelClass(PasswordPolicyRel.class);

		setModelImplClass(PasswordPolicyRelImpl.class);
		setModelPKClass(long.class);

		setTable(PasswordPolicyRelTable.INSTANCE);
	}

	/**
	 * Caches the password policy rel in the entity cache if it is enabled.
	 *
	 * @param passwordPolicyRel the password policy rel
	 */
	@Override
	public void cacheResult(PasswordPolicyRel passwordPolicyRel) {
		EntityCacheUtil.putResult(
			PasswordPolicyRelImpl.class, passwordPolicyRel.getPrimaryKey(),
			passwordPolicyRel);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				passwordPolicyRel.getClassNameId(),
				passwordPolicyRel.getClassPK()
			},
			passwordPolicyRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the password policy rels in the entity cache if it is enabled.
	 *
	 * @param passwordPolicyRels the password policy rels
	 */
	@Override
	public void cacheResult(List<PasswordPolicyRel> passwordPolicyRels) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (passwordPolicyRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (PasswordPolicyRel passwordPolicyRel : passwordPolicyRels) {
			if (EntityCacheUtil.getResult(
					PasswordPolicyRelImpl.class,
					passwordPolicyRel.getPrimaryKey()) == null) {

				cacheResult(passwordPolicyRel);
			}
		}
	}

	/**
	 * Clears the cache for all password policy rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(PasswordPolicyRelImpl.class);

		FinderCacheUtil.clearCache(PasswordPolicyRelImpl.class);
	}

	/**
	 * Clears the cache for the password policy rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PasswordPolicyRel passwordPolicyRel) {
		EntityCacheUtil.removeResult(
			PasswordPolicyRelImpl.class, passwordPolicyRel);
	}

	@Override
	public void clearCache(List<PasswordPolicyRel> passwordPolicyRels) {
		for (PasswordPolicyRel passwordPolicyRel : passwordPolicyRels) {
			EntityCacheUtil.removeResult(
				PasswordPolicyRelImpl.class, passwordPolicyRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(PasswordPolicyRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				PasswordPolicyRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PasswordPolicyRelModelImpl passwordPolicyRelModelImpl) {

		Object[] args = new Object[] {
			passwordPolicyRelModelImpl.getClassNameId(),
			passwordPolicyRelModelImpl.getClassPK()
		};

		FinderCacheUtil.putResult(_finderPathCountByC_C, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByC_C, args, passwordPolicyRelModelImpl);
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

		passwordPolicyRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return passwordPolicyRel;
	}

	/**
	 * Removes the password policy rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param passwordPolicyRelId the primary key of the password policy rel
	 * @return the password policy rel that was removed
	 * @throws NoSuchPasswordPolicyRelException if a password policy rel with the primary key could not be found
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
	 * @throws NoSuchPasswordPolicyRelException if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel remove(Serializable primaryKey)
		throws NoSuchPasswordPolicyRelException {

		Session session = null;

		try {
			session = openSession();

			PasswordPolicyRel passwordPolicyRel =
				(PasswordPolicyRel)session.get(
					PasswordPolicyRelImpl.class, primaryKey);

			if (passwordPolicyRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPasswordPolicyRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(passwordPolicyRel);
		}
		catch (NoSuchPasswordPolicyRelException noSuchEntityException) {
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
	protected PasswordPolicyRel removeImpl(
		PasswordPolicyRel passwordPolicyRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(passwordPolicyRel)) {
				passwordPolicyRel = (PasswordPolicyRel)session.get(
					PasswordPolicyRelImpl.class,
					passwordPolicyRel.getPrimaryKeyObj());
			}

			if (passwordPolicyRel != null) {
				session.delete(passwordPolicyRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
	public PasswordPolicyRel updateImpl(PasswordPolicyRel passwordPolicyRel) {
		boolean isNew = passwordPolicyRel.isNew();

		if (!(passwordPolicyRel instanceof PasswordPolicyRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(passwordPolicyRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					passwordPolicyRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in passwordPolicyRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PasswordPolicyRel implementation " +
					passwordPolicyRel.getClass());
		}

		PasswordPolicyRelModelImpl passwordPolicyRelModelImpl =
			(PasswordPolicyRelModelImpl)passwordPolicyRel;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(passwordPolicyRel);
			}
			else {
				passwordPolicyRel = (PasswordPolicyRel)session.merge(
					passwordPolicyRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			PasswordPolicyRelImpl.class, passwordPolicyRelModelImpl, false,
			true);

		cacheUniqueFindersCache(passwordPolicyRelModelImpl);

		if (isNew) {
			passwordPolicyRel.setNew(false);
		}

		passwordPolicyRel.resetOriginalValues();

		return passwordPolicyRel;
	}

	/**
	 * Returns the password policy rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the password policy rel
	 * @return the password policy rel
	 * @throws NoSuchPasswordPolicyRelException if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPasswordPolicyRelException {

		PasswordPolicyRel passwordPolicyRel = fetchByPrimaryKey(primaryKey);

		if (passwordPolicyRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPasswordPolicyRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return passwordPolicyRel;
	}

	/**
	 * Returns the password policy rel with the primary key or throws a <code>NoSuchPasswordPolicyRelException</code> if it could not be found.
	 *
	 * @param passwordPolicyRelId the primary key of the password policy rel
	 * @return the password policy rel
	 * @throws NoSuchPasswordPolicyRelException if a password policy rel with the primary key could not be found
	 */
	@Override
	public PasswordPolicyRel findByPrimaryKey(long passwordPolicyRelId)
		throws NoSuchPasswordPolicyRelException {

		return findByPrimaryKey((Serializable)passwordPolicyRelId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policy rels
	 * @param end the upper bound of the range of password policy rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of password policy rels
	 */
	@Override
	public List<PasswordPolicyRel> findAll(
		int start, int end,
		OrderByComparator<PasswordPolicyRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the password policy rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PasswordPolicyRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policy rels
	 * @param end the upper bound of the range of password policy rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of password policy rels
	 */
	@Override
	public List<PasswordPolicyRel> findAll(
		int start, int end,
		OrderByComparator<PasswordPolicyRel> orderByComparator,
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

		List<PasswordPolicyRel> list = null;

		if (useFinderCache) {
			list = (List<PasswordPolicyRel>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PASSWORDPOLICYREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PASSWORDPOLICYREL;

				sql = sql.concat(PasswordPolicyRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PasswordPolicyRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_PASSWORDPOLICYREL);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "passwordPolicyRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PASSWORDPOLICYREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PasswordPolicyRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the password policy rel persistence.
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

		_finderPathWithPaginationFindByPasswordPolicyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPasswordPolicyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"passwordPolicyId"}, true);

		_finderPathWithoutPaginationFindByPasswordPolicyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPasswordPolicyId",
			new String[] {Long.class.getName()},
			new String[] {"passwordPolicyId"}, true);

		_finderPathCountByPasswordPolicyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPasswordPolicyId", new String[] {Long.class.getName()},
			new String[] {"passwordPolicyId"}, false);

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		PasswordPolicyRelUtil.setPersistence(this);
	}

	public void destroy() {
		PasswordPolicyRelUtil.setPersistence(null);

		EntityCacheUtil.removeCache(PasswordPolicyRelImpl.class.getName());
	}

	private static final String _SQL_SELECT_PASSWORDPOLICYREL =
		"SELECT passwordPolicyRel FROM PasswordPolicyRel passwordPolicyRel";

	private static final String _SQL_SELECT_PASSWORDPOLICYREL_WHERE =
		"SELECT passwordPolicyRel FROM PasswordPolicyRel passwordPolicyRel WHERE ";

	private static final String _SQL_COUNT_PASSWORDPOLICYREL =
		"SELECT COUNT(passwordPolicyRel) FROM PasswordPolicyRel passwordPolicyRel";

	private static final String _SQL_COUNT_PASSWORDPOLICYREL_WHERE =
		"SELECT COUNT(passwordPolicyRel) FROM PasswordPolicyRel passwordPolicyRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "passwordPolicyRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PasswordPolicyRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PasswordPolicyRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PasswordPolicyRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}