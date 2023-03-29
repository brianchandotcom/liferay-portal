/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.exception.NoSuchFaroProjectEmailAddressDomainException;
import com.liferay.osb.faro.model.FaroProjectEmailAddressDomain;
import com.liferay.osb.faro.model.impl.FaroProjectEmailAddressDomainImpl;
import com.liferay.osb.faro.model.impl.FaroProjectEmailAddressDomainModelImpl;
import com.liferay.osb.faro.service.persistence.FaroProjectEmailAddressDomainPersistence;
import com.liferay.osb.faro.service.persistence.FaroProjectEmailAddressDomainUtil;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the faro project email address domain service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @generated
 */
public class FaroProjectEmailAddressDomainPersistenceImpl
	extends BasePersistenceImpl<FaroProjectEmailAddressDomain>
	implements FaroProjectEmailAddressDomainPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FaroProjectEmailAddressDomainUtil</code> to access the faro project email address domain persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FaroProjectEmailAddressDomainImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the faro project email address domains where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro project email address domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @return the range of matching faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<FaroProjectEmailAddressDomain> list = null;

		if (useFinderCache) {
			list = (List<FaroProjectEmailAddressDomain>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroProjectEmailAddressDomain
						faroProjectEmailAddressDomain : list) {

					if (groupId != faroProjectEmailAddressDomain.getGroupId()) {
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

			sb.append(_SQL_SELECT_FAROPROJECTEMAILADDRESSDOMAIN_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroProjectEmailAddressDomainModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<FaroProjectEmailAddressDomain>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain findByGroupId_First(
			long groupId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException {

		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
			fetchByGroupId_First(groupId, orderByComparator);

		if (faroProjectEmailAddressDomain != null) {
			return faroProjectEmailAddressDomain;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFaroProjectEmailAddressDomainException(sb.toString());
	}

	/**
	 * Returns the first faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain fetchByGroupId_First(
		long groupId,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		List<FaroProjectEmailAddressDomain> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain findByGroupId_Last(
			long groupId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException {

		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
			fetchByGroupId_Last(groupId, orderByComparator);

		if (faroProjectEmailAddressDomain != null) {
			return faroProjectEmailAddressDomain;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFaroProjectEmailAddressDomainException(sb.toString());
	}

	/**
	 * Returns the last faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain fetchByGroupId_Last(
		long groupId,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FaroProjectEmailAddressDomain> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro project email address domains before and after the current faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the current faro project email address domain
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain[] findByGroupId_PrevAndNext(
			long faroProjectEmailAddressDomainId, long groupId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException {

		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
			findByPrimaryKey(faroProjectEmailAddressDomainId);

		Session session = null;

		try {
			session = openSession();

			FaroProjectEmailAddressDomain[] array =
				new FaroProjectEmailAddressDomainImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, faroProjectEmailAddressDomain, groupId,
				orderByComparator, true);

			array[1] = faroProjectEmailAddressDomain;

			array[2] = getByGroupId_PrevAndNext(
				session, faroProjectEmailAddressDomain, groupId,
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

	protected FaroProjectEmailAddressDomain getByGroupId_PrevAndNext(
		Session session,
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain,
		long groupId,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator,
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

		sb.append(_SQL_SELECT_FAROPROJECTEMAILADDRESSDOMAIN_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(FaroProjectEmailAddressDomainModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						faroProjectEmailAddressDomain)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroProjectEmailAddressDomain> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro project email address domains where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FaroProjectEmailAddressDomain faroProjectEmailAddressDomain :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(faroProjectEmailAddressDomain);
		}
	}

	/**
	 * Returns the number of faro project email address domains where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro project email address domains
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROPROJECTEMAILADDRESSDOMAIN_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"faroProjectEmailAddressDomain.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByFaroProjectId;
	private FinderPath _finderPathWithoutPaginationFindByFaroProjectId;
	private FinderPath _finderPathCountByFaroProjectId;

	/**
	 * Returns all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @return the matching faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId) {

		return findByFaroProjectId(
			faroProjectId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @return the range of matching faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId, int start, int end) {

		return findByFaroProjectId(faroProjectId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId, int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return findByFaroProjectId(
			faroProjectId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId, int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByFaroProjectId;
				finderArgs = new Object[] {faroProjectId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByFaroProjectId;
			finderArgs = new Object[] {
				faroProjectId, start, end, orderByComparator
			};
		}

		List<FaroProjectEmailAddressDomain> list = null;

		if (useFinderCache) {
			list = (List<FaroProjectEmailAddressDomain>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroProjectEmailAddressDomain
						faroProjectEmailAddressDomain : list) {

					if (faroProjectId !=
							faroProjectEmailAddressDomain.getFaroProjectId()) {

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

			sb.append(_SQL_SELECT_FAROPROJECTEMAILADDRESSDOMAIN_WHERE);

			sb.append(_FINDER_COLUMN_FAROPROJECTID_FAROPROJECTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroProjectEmailAddressDomainModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(faroProjectId);

				list = (List<FaroProjectEmailAddressDomain>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain findByFaroProjectId_First(
			long faroProjectId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException {

		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
			fetchByFaroProjectId_First(faroProjectId, orderByComparator);

		if (faroProjectEmailAddressDomain != null) {
			return faroProjectEmailAddressDomain;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("faroProjectId=");
		sb.append(faroProjectId);

		sb.append("}");

		throw new NoSuchFaroProjectEmailAddressDomainException(sb.toString());
	}

	/**
	 * Returns the first faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain fetchByFaroProjectId_First(
		long faroProjectId,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		List<FaroProjectEmailAddressDomain> list = findByFaroProjectId(
			faroProjectId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain findByFaroProjectId_Last(
			long faroProjectId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException {

		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
			fetchByFaroProjectId_Last(faroProjectId, orderByComparator);

		if (faroProjectEmailAddressDomain != null) {
			return faroProjectEmailAddressDomain;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("faroProjectId=");
		sb.append(faroProjectId);

		sb.append("}");

		throw new NoSuchFaroProjectEmailAddressDomainException(sb.toString());
	}

	/**
	 * Returns the last faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain fetchByFaroProjectId_Last(
		long faroProjectId,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		int count = countByFaroProjectId(faroProjectId);

		if (count == 0) {
			return null;
		}

		List<FaroProjectEmailAddressDomain> list = findByFaroProjectId(
			faroProjectId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro project email address domains before and after the current faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the current faro project email address domain
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain[] findByFaroProjectId_PrevAndNext(
			long faroProjectEmailAddressDomainId, long faroProjectId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException {

		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
			findByPrimaryKey(faroProjectEmailAddressDomainId);

		Session session = null;

		try {
			session = openSession();

			FaroProjectEmailAddressDomain[] array =
				new FaroProjectEmailAddressDomainImpl[3];

			array[0] = getByFaroProjectId_PrevAndNext(
				session, faroProjectEmailAddressDomain, faroProjectId,
				orderByComparator, true);

			array[1] = faroProjectEmailAddressDomain;

			array[2] = getByFaroProjectId_PrevAndNext(
				session, faroProjectEmailAddressDomain, faroProjectId,
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

	protected FaroProjectEmailAddressDomain getByFaroProjectId_PrevAndNext(
		Session session,
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain,
		long faroProjectId,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator,
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

		sb.append(_SQL_SELECT_FAROPROJECTEMAILADDRESSDOMAIN_WHERE);

		sb.append(_FINDER_COLUMN_FAROPROJECTID_FAROPROJECTID_2);

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
			sb.append(FaroProjectEmailAddressDomainModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(faroProjectId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						faroProjectEmailAddressDomain)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroProjectEmailAddressDomain> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro project email address domains where faroProjectId = &#63; from the database.
	 *
	 * @param faroProjectId the faro project ID
	 */
	@Override
	public void removeByFaroProjectId(long faroProjectId) {
		for (FaroProjectEmailAddressDomain faroProjectEmailAddressDomain :
				findByFaroProjectId(
					faroProjectId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(faroProjectEmailAddressDomain);
		}
	}

	/**
	 * Returns the number of faro project email address domains where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @return the number of matching faro project email address domains
	 */
	@Override
	public int countByFaroProjectId(long faroProjectId) {
		FinderPath finderPath = _finderPathCountByFaroProjectId;

		Object[] finderArgs = new Object[] {faroProjectId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROPROJECTEMAILADDRESSDOMAIN_WHERE);

			sb.append(_FINDER_COLUMN_FAROPROJECTID_FAROPROJECTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(faroProjectId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_FAROPROJECTID_FAROPROJECTID_2 =
		"faroProjectEmailAddressDomain.faroProjectId = ?";

	public FaroProjectEmailAddressDomainPersistenceImpl() {
		setModelClass(FaroProjectEmailAddressDomain.class);
	}

	/**
	 * Caches the faro project email address domain in the entity cache if it is enabled.
	 *
	 * @param faroProjectEmailAddressDomain the faro project email address domain
	 */
	@Override
	public void cacheResult(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		entityCache.putResult(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainImpl.class,
			faroProjectEmailAddressDomain.getPrimaryKey(),
			faroProjectEmailAddressDomain);

		faroProjectEmailAddressDomain.resetOriginalValues();
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the faro project email address domains in the entity cache if it is enabled.
	 *
	 * @param faroProjectEmailAddressDomains the faro project email address domains
	 */
	@Override
	public void cacheResult(
		List<FaroProjectEmailAddressDomain> faroProjectEmailAddressDomains) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (faroProjectEmailAddressDomains.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FaroProjectEmailAddressDomain faroProjectEmailAddressDomain :
				faroProjectEmailAddressDomains) {

			if (entityCache.getResult(
					FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
					FaroProjectEmailAddressDomainImpl.class,
					faroProjectEmailAddressDomain.getPrimaryKey()) == null) {

				cacheResult(faroProjectEmailAddressDomain);
			}
			else {
				faroProjectEmailAddressDomain.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all faro project email address domains.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FaroProjectEmailAddressDomainImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the faro project email address domain.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		entityCache.removeResult(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainImpl.class,
			faroProjectEmailAddressDomain.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<FaroProjectEmailAddressDomain> faroProjectEmailAddressDomains) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FaroProjectEmailAddressDomain faroProjectEmailAddressDomain :
				faroProjectEmailAddressDomains) {

			entityCache.removeResult(
				FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
				FaroProjectEmailAddressDomainImpl.class,
				faroProjectEmailAddressDomain.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
				FaroProjectEmailAddressDomainImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new faro project email address domain with the primary key. Does not add the faro project email address domain to the database.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key for the new faro project email address domain
	 * @return the new faro project email address domain
	 */
	@Override
	public FaroProjectEmailAddressDomain create(
		long faroProjectEmailAddressDomainId) {

		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
			new FaroProjectEmailAddressDomainImpl();

		faroProjectEmailAddressDomain.setNew(true);
		faroProjectEmailAddressDomain.setPrimaryKey(
			faroProjectEmailAddressDomainId);

		return faroProjectEmailAddressDomain;
	}

	/**
	 * Removes the faro project email address domain with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain that was removed
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain remove(
			long faroProjectEmailAddressDomainId)
		throws NoSuchFaroProjectEmailAddressDomainException {

		return remove((Serializable)faroProjectEmailAddressDomainId);
	}

	/**
	 * Removes the faro project email address domain with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the faro project email address domain
	 * @return the faro project email address domain that was removed
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain remove(Serializable primaryKey)
		throws NoSuchFaroProjectEmailAddressDomainException {

		Session session = null;

		try {
			session = openSession();

			FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
				(FaroProjectEmailAddressDomain)session.get(
					FaroProjectEmailAddressDomainImpl.class, primaryKey);

			if (faroProjectEmailAddressDomain == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFaroProjectEmailAddressDomainException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(faroProjectEmailAddressDomain);
		}
		catch (NoSuchFaroProjectEmailAddressDomainException
					noSuchEntityException) {

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
	protected FaroProjectEmailAddressDomain removeImpl(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(faroProjectEmailAddressDomain)) {
				faroProjectEmailAddressDomain =
					(FaroProjectEmailAddressDomain)session.get(
						FaroProjectEmailAddressDomainImpl.class,
						faroProjectEmailAddressDomain.getPrimaryKeyObj());
			}

			if (faroProjectEmailAddressDomain != null) {
				session.delete(faroProjectEmailAddressDomain);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (faroProjectEmailAddressDomain != null) {
			clearCache(faroProjectEmailAddressDomain);
		}

		return faroProjectEmailAddressDomain;
	}

	@Override
	public FaroProjectEmailAddressDomain updateImpl(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		boolean isNew = faroProjectEmailAddressDomain.isNew();

		if (!(faroProjectEmailAddressDomain instanceof
				FaroProjectEmailAddressDomainModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					faroProjectEmailAddressDomain.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					faroProjectEmailAddressDomain);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in faroProjectEmailAddressDomain proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FaroProjectEmailAddressDomain implementation " +
					faroProjectEmailAddressDomain.getClass());
		}

		FaroProjectEmailAddressDomainModelImpl
			faroProjectEmailAddressDomainModelImpl =
				(FaroProjectEmailAddressDomainModelImpl)
					faroProjectEmailAddressDomain;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(faroProjectEmailAddressDomain);

				faroProjectEmailAddressDomain.setNew(false);
			}
			else {
				faroProjectEmailAddressDomain =
					(FaroProjectEmailAddressDomain)session.merge(
						faroProjectEmailAddressDomain);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!FaroProjectEmailAddressDomainModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				faroProjectEmailAddressDomainModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				faroProjectEmailAddressDomainModelImpl.getFaroProjectId()
			};

			finderCache.removeResult(_finderPathCountByFaroProjectId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFaroProjectId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((faroProjectEmailAddressDomainModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					faroProjectEmailAddressDomainModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {
					faroProjectEmailAddressDomainModelImpl.getGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((faroProjectEmailAddressDomainModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFaroProjectId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					faroProjectEmailAddressDomainModelImpl.
						getOriginalFaroProjectId()
				};

				finderCache.removeResult(_finderPathCountByFaroProjectId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFaroProjectId, args);

				args = new Object[] {
					faroProjectEmailAddressDomainModelImpl.getFaroProjectId()
				};

				finderCache.removeResult(_finderPathCountByFaroProjectId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFaroProjectId, args);
			}
		}

		entityCache.putResult(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainImpl.class,
			faroProjectEmailAddressDomain.getPrimaryKey(),
			faroProjectEmailAddressDomain, false);

		faroProjectEmailAddressDomain.resetOriginalValues();

		return faroProjectEmailAddressDomain;
	}

	/**
	 * Returns the faro project email address domain with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the faro project email address domain
	 * @return the faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchFaroProjectEmailAddressDomainException {

		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
			fetchByPrimaryKey(primaryKey);

		if (faroProjectEmailAddressDomain == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFaroProjectEmailAddressDomainException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return faroProjectEmailAddressDomain;
	}

	/**
	 * Returns the faro project email address domain with the primary key or throws a <code>NoSuchFaroProjectEmailAddressDomainException</code> if it could not be found.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain findByPrimaryKey(
			long faroProjectEmailAddressDomainId)
		throws NoSuchFaroProjectEmailAddressDomainException {

		return findByPrimaryKey((Serializable)faroProjectEmailAddressDomainId);
	}

	/**
	 * Returns the faro project email address domain with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the faro project email address domain
	 * @return the faro project email address domain, or <code>null</code> if a faro project email address domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
			(FaroProjectEmailAddressDomain)serializable;

		if (faroProjectEmailAddressDomain == null) {
			Session session = null;

			try {
				session = openSession();

				faroProjectEmailAddressDomain =
					(FaroProjectEmailAddressDomain)session.get(
						FaroProjectEmailAddressDomainImpl.class, primaryKey);

				if (faroProjectEmailAddressDomain != null) {
					cacheResult(faroProjectEmailAddressDomain);
				}
				else {
					entityCache.putResult(
						FaroProjectEmailAddressDomainModelImpl.
							ENTITY_CACHE_ENABLED,
						FaroProjectEmailAddressDomainImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
					FaroProjectEmailAddressDomainImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return faroProjectEmailAddressDomain;
	}

	/**
	 * Returns the faro project email address domain with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain, or <code>null</code> if a faro project email address domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailAddressDomain fetchByPrimaryKey(
		long faroProjectEmailAddressDomainId) {

		return fetchByPrimaryKey((Serializable)faroProjectEmailAddressDomainId);
	}

	@Override
	public Map<Serializable, FaroProjectEmailAddressDomain> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FaroProjectEmailAddressDomain> map =
			new HashMap<Serializable, FaroProjectEmailAddressDomain>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FaroProjectEmailAddressDomain faroProjectEmailAddressDomain =
				fetchByPrimaryKey(primaryKey);

			if (faroProjectEmailAddressDomain != null) {
				map.put(primaryKey, faroProjectEmailAddressDomain);
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

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
				FaroProjectEmailAddressDomainImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey,
						(FaroProjectEmailAddressDomain)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			(uncachedPrimaryKeys.size() * 2) + 1);

		sb.append(_SQL_SELECT_FAROPROJECTEMAILADDRESSDOMAIN_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
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

			for (FaroProjectEmailAddressDomain faroProjectEmailAddressDomain :
					(List<FaroProjectEmailAddressDomain>)query.list()) {

				map.put(
					faroProjectEmailAddressDomain.getPrimaryKeyObj(),
					faroProjectEmailAddressDomain);

				cacheResult(faroProjectEmailAddressDomain);

				uncachedPrimaryKeys.remove(
					faroProjectEmailAddressDomain.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
					FaroProjectEmailAddressDomainImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the faro project email address domains.
	 *
	 * @return the faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro project email address domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @return the range of faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findAll(
		int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro project email address domains
	 */
	@Override
	public List<FaroProjectEmailAddressDomain> findAll(
		int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator,
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

		List<FaroProjectEmailAddressDomain> list = null;

		if (useFinderCache) {
			list = (List<FaroProjectEmailAddressDomain>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FAROPROJECTEMAILADDRESSDOMAIN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FAROPROJECTEMAILADDRESSDOMAIN;

				sql = sql.concat(
					FaroProjectEmailAddressDomainModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FaroProjectEmailAddressDomain>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the faro project email address domains from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FaroProjectEmailAddressDomain faroProjectEmailAddressDomain :
				findAll()) {

			remove(faroProjectEmailAddressDomain);
		}
	}

	/**
	 * Returns the number of faro project email address domains.
	 *
	 * @return the number of faro project email address domains
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_FAROPROJECTEMAILADDRESSDOMAIN);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FaroProjectEmailAddressDomainModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the faro project email address domain persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainModelImpl.FINDER_CACHE_ENABLED,
			FaroProjectEmailAddressDomainImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainModelImpl.FINDER_CACHE_ENABLED,
			FaroProjectEmailAddressDomainImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainModelImpl.FINDER_CACHE_ENABLED,
			FaroProjectEmailAddressDomainImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainModelImpl.FINDER_CACHE_ENABLED,
			FaroProjectEmailAddressDomainImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			FaroProjectEmailAddressDomainModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByFaroProjectId = new FinderPath(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainModelImpl.FINDER_CACHE_ENABLED,
			FaroProjectEmailAddressDomainImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFaroProjectId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFaroProjectId = new FinderPath(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainModelImpl.FINDER_CACHE_ENABLED,
			FaroProjectEmailAddressDomainImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFaroProjectId",
			new String[] {Long.class.getName()},
			FaroProjectEmailAddressDomainModelImpl.
				FAROPROJECTID_COLUMN_BITMASK);

		_finderPathCountByFaroProjectId = new FinderPath(
			FaroProjectEmailAddressDomainModelImpl.ENTITY_CACHE_ENABLED,
			FaroProjectEmailAddressDomainModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFaroProjectId", new String[] {Long.class.getName()});

		_setFaroProjectEmailAddressDomainUtilPersistence(this);
	}

	public void destroy() {
		_setFaroProjectEmailAddressDomainUtilPersistence(null);

		entityCache.removeCache(
			FaroProjectEmailAddressDomainImpl.class.getName());

		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private void _setFaroProjectEmailAddressDomainUtilPersistence(
		FaroProjectEmailAddressDomainPersistence
			faroProjectEmailAddressDomainPersistence) {

		try {
			Field field =
				FaroProjectEmailAddressDomainUtil.class.getDeclaredField(
					"_persistence");

			field.setAccessible(true);

			field.set(null, faroProjectEmailAddressDomainPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FAROPROJECTEMAILADDRESSDOMAIN =
		"SELECT faroProjectEmailAddressDomain FROM FaroProjectEmailAddressDomain faroProjectEmailAddressDomain";

	private static final String
		_SQL_SELECT_FAROPROJECTEMAILADDRESSDOMAIN_WHERE_PKS_IN =
			"SELECT faroProjectEmailAddressDomain FROM FaroProjectEmailAddressDomain faroProjectEmailAddressDomain WHERE faroProjectEmailAddressDomainId IN (";

	private static final String
		_SQL_SELECT_FAROPROJECTEMAILADDRESSDOMAIN_WHERE =
			"SELECT faroProjectEmailAddressDomain FROM FaroProjectEmailAddressDomain faroProjectEmailAddressDomain WHERE ";

	private static final String _SQL_COUNT_FAROPROJECTEMAILADDRESSDOMAIN =
		"SELECT COUNT(faroProjectEmailAddressDomain) FROM FaroProjectEmailAddressDomain faroProjectEmailAddressDomain";

	private static final String _SQL_COUNT_FAROPROJECTEMAILADDRESSDOMAIN_WHERE =
		"SELECT COUNT(faroProjectEmailAddressDomain) FROM FaroProjectEmailAddressDomain faroProjectEmailAddressDomain WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"faroProjectEmailAddressDomain.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FaroProjectEmailAddressDomain exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FaroProjectEmailAddressDomain exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FaroProjectEmailAddressDomainPersistenceImpl.class);

}