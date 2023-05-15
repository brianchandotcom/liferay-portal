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

import com.liferay.osb.faro.exception.NoSuchFaroProjectEmailDomainException;
import com.liferay.osb.faro.model.FaroProjectEmailDomain;
import com.liferay.osb.faro.model.FaroProjectEmailDomainTable;
import com.liferay.osb.faro.model.impl.FaroProjectEmailDomainImpl;
import com.liferay.osb.faro.model.impl.FaroProjectEmailDomainModelImpl;
import com.liferay.osb.faro.service.persistence.FaroProjectEmailDomainPersistence;
import com.liferay.osb.faro.service.persistence.FaroProjectEmailDomainUtil;
import com.liferay.osb.faro.service.persistence.impl.constants.OSBFaroPersistenceConstants;
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
 * The persistence implementation for the faro project email domain service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @generated
 */
@Component(service = FaroProjectEmailDomainPersistence.class)
public class FaroProjectEmailDomainPersistenceImpl
	extends BasePersistenceImpl<FaroProjectEmailDomain>
	implements FaroProjectEmailDomainPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FaroProjectEmailDomainUtil</code> to access the faro project email domain persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FaroProjectEmailDomainImpl.class.getName();

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
	 * Returns all the faro project email domains where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro project email domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email domains
	 * @param end the upper bound of the range of faro project email domains (not inclusive)
	 * @return the range of matching faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro project email domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email domains
	 * @param end the upper bound of the range of faro project email domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro project email domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email domains
	 * @param end the upper bound of the range of faro project email domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator,
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

		List<FaroProjectEmailDomain> list = null;

		if (useFinderCache) {
			list = (List<FaroProjectEmailDomain>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroProjectEmailDomain faroProjectEmailDomain : list) {
					if (groupId != faroProjectEmailDomain.getGroupId()) {
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

			sb.append(_SQL_SELECT_FAROPROJECTEMAILDOMAIN_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroProjectEmailDomainModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<FaroProjectEmailDomain>)QueryUtil.list(
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
	 * Returns the first faro project email domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email domain
	 * @throws NoSuchFaroProjectEmailDomainException if a matching faro project email domain could not be found
	 */
	@Override
	public FaroProjectEmailDomain findByGroupId_First(
			long groupId,
			OrderByComparator<FaroProjectEmailDomain> orderByComparator)
		throws NoSuchFaroProjectEmailDomainException {

		FaroProjectEmailDomain faroProjectEmailDomain = fetchByGroupId_First(
			groupId, orderByComparator);

		if (faroProjectEmailDomain != null) {
			return faroProjectEmailDomain;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFaroProjectEmailDomainException(sb.toString());
	}

	/**
	 * Returns the first faro project email domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email domain, or <code>null</code> if a matching faro project email domain could not be found
	 */
	@Override
	public FaroProjectEmailDomain fetchByGroupId_First(
		long groupId,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator) {

		List<FaroProjectEmailDomain> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro project email domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email domain
	 * @throws NoSuchFaroProjectEmailDomainException if a matching faro project email domain could not be found
	 */
	@Override
	public FaroProjectEmailDomain findByGroupId_Last(
			long groupId,
			OrderByComparator<FaroProjectEmailDomain> orderByComparator)
		throws NoSuchFaroProjectEmailDomainException {

		FaroProjectEmailDomain faroProjectEmailDomain = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (faroProjectEmailDomain != null) {
			return faroProjectEmailDomain;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFaroProjectEmailDomainException(sb.toString());
	}

	/**
	 * Returns the last faro project email domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email domain, or <code>null</code> if a matching faro project email domain could not be found
	 */
	@Override
	public FaroProjectEmailDomain fetchByGroupId_Last(
		long groupId,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FaroProjectEmailDomain> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro project email domains before and after the current faro project email domain in the ordered set where groupId = &#63;.
	 *
	 * @param faroProjectEmailDomainId the primary key of the current faro project email domain
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro project email domain
	 * @throws NoSuchFaroProjectEmailDomainException if a faro project email domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailDomain[] findByGroupId_PrevAndNext(
			long faroProjectEmailDomainId, long groupId,
			OrderByComparator<FaroProjectEmailDomain> orderByComparator)
		throws NoSuchFaroProjectEmailDomainException {

		FaroProjectEmailDomain faroProjectEmailDomain = findByPrimaryKey(
			faroProjectEmailDomainId);

		Session session = null;

		try {
			session = openSession();

			FaroProjectEmailDomain[] array = new FaroProjectEmailDomainImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, faroProjectEmailDomain, groupId, orderByComparator,
				true);

			array[1] = faroProjectEmailDomain;

			array[2] = getByGroupId_PrevAndNext(
				session, faroProjectEmailDomain, groupId, orderByComparator,
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

	protected FaroProjectEmailDomain getByGroupId_PrevAndNext(
		Session session, FaroProjectEmailDomain faroProjectEmailDomain,
		long groupId,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator,
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

		sb.append(_SQL_SELECT_FAROPROJECTEMAILDOMAIN_WHERE);

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
			sb.append(FaroProjectEmailDomainModelImpl.ORDER_BY_JPQL);
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
						faroProjectEmailDomain)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroProjectEmailDomain> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro project email domains where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FaroProjectEmailDomain faroProjectEmailDomain :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(faroProjectEmailDomain);
		}
	}

	/**
	 * Returns the number of faro project email domains where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro project email domains
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROPROJECTEMAILDOMAIN_WHERE);

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
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"faroProjectEmailDomain.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByFaroProjectId;
	private FinderPath _finderPathWithoutPaginationFindByFaroProjectId;
	private FinderPath _finderPathCountByFaroProjectId;

	/**
	 * Returns all the faro project email domains where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @return the matching faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findByFaroProjectId(
		long faroProjectId) {

		return findByFaroProjectId(
			faroProjectId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro project email domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email domains
	 * @param end the upper bound of the range of faro project email domains (not inclusive)
	 * @return the range of matching faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findByFaroProjectId(
		long faroProjectId, int start, int end) {

		return findByFaroProjectId(faroProjectId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro project email domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email domains
	 * @param end the upper bound of the range of faro project email domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findByFaroProjectId(
		long faroProjectId, int start, int end,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator) {

		return findByFaroProjectId(
			faroProjectId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro project email domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email domains
	 * @param end the upper bound of the range of faro project email domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findByFaroProjectId(
		long faroProjectId, int start, int end,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator,
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

		List<FaroProjectEmailDomain> list = null;

		if (useFinderCache) {
			list = (List<FaroProjectEmailDomain>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroProjectEmailDomain faroProjectEmailDomain : list) {
					if (faroProjectId !=
							faroProjectEmailDomain.getFaroProjectId()) {

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

			sb.append(_SQL_SELECT_FAROPROJECTEMAILDOMAIN_WHERE);

			sb.append(_FINDER_COLUMN_FAROPROJECTID_FAROPROJECTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroProjectEmailDomainModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(faroProjectId);

				list = (List<FaroProjectEmailDomain>)QueryUtil.list(
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
	 * Returns the first faro project email domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email domain
	 * @throws NoSuchFaroProjectEmailDomainException if a matching faro project email domain could not be found
	 */
	@Override
	public FaroProjectEmailDomain findByFaroProjectId_First(
			long faroProjectId,
			OrderByComparator<FaroProjectEmailDomain> orderByComparator)
		throws NoSuchFaroProjectEmailDomainException {

		FaroProjectEmailDomain faroProjectEmailDomain =
			fetchByFaroProjectId_First(faroProjectId, orderByComparator);

		if (faroProjectEmailDomain != null) {
			return faroProjectEmailDomain;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("faroProjectId=");
		sb.append(faroProjectId);

		sb.append("}");

		throw new NoSuchFaroProjectEmailDomainException(sb.toString());
	}

	/**
	 * Returns the first faro project email domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email domain, or <code>null</code> if a matching faro project email domain could not be found
	 */
	@Override
	public FaroProjectEmailDomain fetchByFaroProjectId_First(
		long faroProjectId,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator) {

		List<FaroProjectEmailDomain> list = findByFaroProjectId(
			faroProjectId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro project email domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email domain
	 * @throws NoSuchFaroProjectEmailDomainException if a matching faro project email domain could not be found
	 */
	@Override
	public FaroProjectEmailDomain findByFaroProjectId_Last(
			long faroProjectId,
			OrderByComparator<FaroProjectEmailDomain> orderByComparator)
		throws NoSuchFaroProjectEmailDomainException {

		FaroProjectEmailDomain faroProjectEmailDomain =
			fetchByFaroProjectId_Last(faroProjectId, orderByComparator);

		if (faroProjectEmailDomain != null) {
			return faroProjectEmailDomain;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("faroProjectId=");
		sb.append(faroProjectId);

		sb.append("}");

		throw new NoSuchFaroProjectEmailDomainException(sb.toString());
	}

	/**
	 * Returns the last faro project email domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email domain, or <code>null</code> if a matching faro project email domain could not be found
	 */
	@Override
	public FaroProjectEmailDomain fetchByFaroProjectId_Last(
		long faroProjectId,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator) {

		int count = countByFaroProjectId(faroProjectId);

		if (count == 0) {
			return null;
		}

		List<FaroProjectEmailDomain> list = findByFaroProjectId(
			faroProjectId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro project email domains before and after the current faro project email domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectEmailDomainId the primary key of the current faro project email domain
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro project email domain
	 * @throws NoSuchFaroProjectEmailDomainException if a faro project email domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailDomain[] findByFaroProjectId_PrevAndNext(
			long faroProjectEmailDomainId, long faroProjectId,
			OrderByComparator<FaroProjectEmailDomain> orderByComparator)
		throws NoSuchFaroProjectEmailDomainException {

		FaroProjectEmailDomain faroProjectEmailDomain = findByPrimaryKey(
			faroProjectEmailDomainId);

		Session session = null;

		try {
			session = openSession();

			FaroProjectEmailDomain[] array = new FaroProjectEmailDomainImpl[3];

			array[0] = getByFaroProjectId_PrevAndNext(
				session, faroProjectEmailDomain, faroProjectId,
				orderByComparator, true);

			array[1] = faroProjectEmailDomain;

			array[2] = getByFaroProjectId_PrevAndNext(
				session, faroProjectEmailDomain, faroProjectId,
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

	protected FaroProjectEmailDomain getByFaroProjectId_PrevAndNext(
		Session session, FaroProjectEmailDomain faroProjectEmailDomain,
		long faroProjectId,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator,
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

		sb.append(_SQL_SELECT_FAROPROJECTEMAILDOMAIN_WHERE);

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
			sb.append(FaroProjectEmailDomainModelImpl.ORDER_BY_JPQL);
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
						faroProjectEmailDomain)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroProjectEmailDomain> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro project email domains where faroProjectId = &#63; from the database.
	 *
	 * @param faroProjectId the faro project ID
	 */
	@Override
	public void removeByFaroProjectId(long faroProjectId) {
		for (FaroProjectEmailDomain faroProjectEmailDomain :
				findByFaroProjectId(
					faroProjectId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(faroProjectEmailDomain);
		}
	}

	/**
	 * Returns the number of faro project email domains where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @return the number of matching faro project email domains
	 */
	@Override
	public int countByFaroProjectId(long faroProjectId) {
		FinderPath finderPath = _finderPathCountByFaroProjectId;

		Object[] finderArgs = new Object[] {faroProjectId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROPROJECTEMAILDOMAIN_WHERE);

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
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_FAROPROJECTID_FAROPROJECTID_2 =
		"faroProjectEmailDomain.faroProjectId = ?";

	public FaroProjectEmailDomainPersistenceImpl() {
		setModelClass(FaroProjectEmailDomain.class);

		setModelImplClass(FaroProjectEmailDomainImpl.class);
		setModelPKClass(long.class);

		setTable(FaroProjectEmailDomainTable.INSTANCE);
	}

	/**
	 * Caches the faro project email domain in the entity cache if it is enabled.
	 *
	 * @param faroProjectEmailDomain the faro project email domain
	 */
	@Override
	public void cacheResult(FaroProjectEmailDomain faroProjectEmailDomain) {
		entityCache.putResult(
			FaroProjectEmailDomainImpl.class,
			faroProjectEmailDomain.getPrimaryKey(), faroProjectEmailDomain);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the faro project email domains in the entity cache if it is enabled.
	 *
	 * @param faroProjectEmailDomains the faro project email domains
	 */
	@Override
	public void cacheResult(
		List<FaroProjectEmailDomain> faroProjectEmailDomains) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (faroProjectEmailDomains.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FaroProjectEmailDomain faroProjectEmailDomain :
				faroProjectEmailDomains) {

			if (entityCache.getResult(
					FaroProjectEmailDomainImpl.class,
					faroProjectEmailDomain.getPrimaryKey()) == null) {

				cacheResult(faroProjectEmailDomain);
			}
		}
	}

	/**
	 * Clears the cache for all faro project email domains.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FaroProjectEmailDomainImpl.class);

		finderCache.clearCache(FaroProjectEmailDomainImpl.class);
	}

	/**
	 * Clears the cache for the faro project email domain.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FaroProjectEmailDomain faroProjectEmailDomain) {
		entityCache.removeResult(
			FaroProjectEmailDomainImpl.class, faroProjectEmailDomain);
	}

	@Override
	public void clearCache(
		List<FaroProjectEmailDomain> faroProjectEmailDomains) {

		for (FaroProjectEmailDomain faroProjectEmailDomain :
				faroProjectEmailDomains) {

			entityCache.removeResult(
				FaroProjectEmailDomainImpl.class, faroProjectEmailDomain);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FaroProjectEmailDomainImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				FaroProjectEmailDomainImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new faro project email domain with the primary key. Does not add the faro project email domain to the database.
	 *
	 * @param faroProjectEmailDomainId the primary key for the new faro project email domain
	 * @return the new faro project email domain
	 */
	@Override
	public FaroProjectEmailDomain create(long faroProjectEmailDomainId) {
		FaroProjectEmailDomain faroProjectEmailDomain =
			new FaroProjectEmailDomainImpl();

		faroProjectEmailDomain.setNew(true);
		faroProjectEmailDomain.setPrimaryKey(faroProjectEmailDomainId);

		faroProjectEmailDomain.setCompanyId(CompanyThreadLocal.getCompanyId());

		return faroProjectEmailDomain;
	}

	/**
	 * Removes the faro project email domain with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroProjectEmailDomainId the primary key of the faro project email domain
	 * @return the faro project email domain that was removed
	 * @throws NoSuchFaroProjectEmailDomainException if a faro project email domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailDomain remove(long faroProjectEmailDomainId)
		throws NoSuchFaroProjectEmailDomainException {

		return remove((Serializable)faroProjectEmailDomainId);
	}

	/**
	 * Removes the faro project email domain with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the faro project email domain
	 * @return the faro project email domain that was removed
	 * @throws NoSuchFaroProjectEmailDomainException if a faro project email domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailDomain remove(Serializable primaryKey)
		throws NoSuchFaroProjectEmailDomainException {

		Session session = null;

		try {
			session = openSession();

			FaroProjectEmailDomain faroProjectEmailDomain =
				(FaroProjectEmailDomain)session.get(
					FaroProjectEmailDomainImpl.class, primaryKey);

			if (faroProjectEmailDomain == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFaroProjectEmailDomainException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(faroProjectEmailDomain);
		}
		catch (NoSuchFaroProjectEmailDomainException noSuchEntityException) {
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
	protected FaroProjectEmailDomain removeImpl(
		FaroProjectEmailDomain faroProjectEmailDomain) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(faroProjectEmailDomain)) {
				faroProjectEmailDomain = (FaroProjectEmailDomain)session.get(
					FaroProjectEmailDomainImpl.class,
					faroProjectEmailDomain.getPrimaryKeyObj());
			}

			if (faroProjectEmailDomain != null) {
				session.delete(faroProjectEmailDomain);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (faroProjectEmailDomain != null) {
			clearCache(faroProjectEmailDomain);
		}

		return faroProjectEmailDomain;
	}

	@Override
	public FaroProjectEmailDomain updateImpl(
		FaroProjectEmailDomain faroProjectEmailDomain) {

		boolean isNew = faroProjectEmailDomain.isNew();

		if (!(faroProjectEmailDomain instanceof
				FaroProjectEmailDomainModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(faroProjectEmailDomain.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					faroProjectEmailDomain);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in faroProjectEmailDomain proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FaroProjectEmailDomain implementation " +
					faroProjectEmailDomain.getClass());
		}

		FaroProjectEmailDomainModelImpl faroProjectEmailDomainModelImpl =
			(FaroProjectEmailDomainModelImpl)faroProjectEmailDomain;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(faroProjectEmailDomain);
			}
			else {
				faroProjectEmailDomain = (FaroProjectEmailDomain)session.merge(
					faroProjectEmailDomain);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FaroProjectEmailDomainImpl.class, faroProjectEmailDomainModelImpl,
			false, true);

		if (isNew) {
			faroProjectEmailDomain.setNew(false);
		}

		faroProjectEmailDomain.resetOriginalValues();

		return faroProjectEmailDomain;
	}

	/**
	 * Returns the faro project email domain with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the faro project email domain
	 * @return the faro project email domain
	 * @throws NoSuchFaroProjectEmailDomainException if a faro project email domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailDomain findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFaroProjectEmailDomainException {

		FaroProjectEmailDomain faroProjectEmailDomain = fetchByPrimaryKey(
			primaryKey);

		if (faroProjectEmailDomain == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFaroProjectEmailDomainException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return faroProjectEmailDomain;
	}

	/**
	 * Returns the faro project email domain with the primary key or throws a <code>NoSuchFaroProjectEmailDomainException</code> if it could not be found.
	 *
	 * @param faroProjectEmailDomainId the primary key of the faro project email domain
	 * @return the faro project email domain
	 * @throws NoSuchFaroProjectEmailDomainException if a faro project email domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailDomain findByPrimaryKey(
			long faroProjectEmailDomainId)
		throws NoSuchFaroProjectEmailDomainException {

		return findByPrimaryKey((Serializable)faroProjectEmailDomainId);
	}

	/**
	 * Returns the faro project email domain with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroProjectEmailDomainId the primary key of the faro project email domain
	 * @return the faro project email domain, or <code>null</code> if a faro project email domain with the primary key could not be found
	 */
	@Override
	public FaroProjectEmailDomain fetchByPrimaryKey(
		long faroProjectEmailDomainId) {

		return fetchByPrimaryKey((Serializable)faroProjectEmailDomainId);
	}

	/**
	 * Returns all the faro project email domains.
	 *
	 * @return the faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro project email domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email domains
	 * @param end the upper bound of the range of faro project email domains (not inclusive)
	 * @return the range of faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro project email domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email domains
	 * @param end the upper bound of the range of faro project email domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findAll(
		int start, int end,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro project email domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email domains
	 * @param end the upper bound of the range of faro project email domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro project email domains
	 */
	@Override
	public List<FaroProjectEmailDomain> findAll(
		int start, int end,
		OrderByComparator<FaroProjectEmailDomain> orderByComparator,
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

		List<FaroProjectEmailDomain> list = null;

		if (useFinderCache) {
			list = (List<FaroProjectEmailDomain>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FAROPROJECTEMAILDOMAIN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FAROPROJECTEMAILDOMAIN;

				sql = sql.concat(FaroProjectEmailDomainModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FaroProjectEmailDomain>)QueryUtil.list(
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
	 * Removes all the faro project email domains from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FaroProjectEmailDomain faroProjectEmailDomain : findAll()) {
			remove(faroProjectEmailDomain);
		}
	}

	/**
	 * Returns the number of faro project email domains.
	 *
	 * @return the number of faro project email domains
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
					_SQL_COUNT_FAROPROJECTEMAILDOMAIN);

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
		return "faroProjectEmailDomainId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FAROPROJECTEMAILDOMAIN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FaroProjectEmailDomainModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the faro project email domain persistence.
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

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByFaroProjectId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFaroProjectId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"faroProjectId"}, true);

		_finderPathWithoutPaginationFindByFaroProjectId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFaroProjectId",
			new String[] {Long.class.getName()}, new String[] {"faroProjectId"},
			true);

		_finderPathCountByFaroProjectId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFaroProjectId",
			new String[] {Long.class.getName()}, new String[] {"faroProjectId"},
			false);

		FaroProjectEmailDomainUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FaroProjectEmailDomainUtil.setPersistence(null);

		entityCache.removeCache(FaroProjectEmailDomainImpl.class.getName());
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FAROPROJECTEMAILDOMAIN =
		"SELECT faroProjectEmailDomain FROM FaroProjectEmailDomain faroProjectEmailDomain";

	private static final String _SQL_SELECT_FAROPROJECTEMAILDOMAIN_WHERE =
		"SELECT faroProjectEmailDomain FROM FaroProjectEmailDomain faroProjectEmailDomain WHERE ";

	private static final String _SQL_COUNT_FAROPROJECTEMAILDOMAIN =
		"SELECT COUNT(faroProjectEmailDomain) FROM FaroProjectEmailDomain faroProjectEmailDomain";

	private static final String _SQL_COUNT_FAROPROJECTEMAILDOMAIN_WHERE =
		"SELECT COUNT(faroProjectEmailDomain) FROM FaroProjectEmailDomain faroProjectEmailDomain WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"faroProjectEmailDomain.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FaroProjectEmailDomain exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FaroProjectEmailDomain exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FaroProjectEmailDomainPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}