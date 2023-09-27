/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.exception.NoSuchFaroChannelException;
import com.liferay.osb.faro.model.FaroChannel;
import com.liferay.osb.faro.model.FaroChannelTable;
import com.liferay.osb.faro.model.impl.FaroChannelImpl;
import com.liferay.osb.faro.model.impl.FaroChannelModelImpl;
import com.liferay.osb.faro.service.persistence.FaroChannelPersistence;
import com.liferay.osb.faro.service.persistence.FaroChannelUtil;
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
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the faro channel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @generated
 */
@Component(service = FaroChannelPersistence.class)
public class FaroChannelPersistenceImpl
	extends BasePersistenceImpl<FaroChannel> implements FaroChannelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FaroChannelUtil</code> to access the faro channel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FaroChannelImpl.class.getName();

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
	 * Returns all the faro channels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching faro channels
	 */
	@Override
	public List<FaroChannel> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro channels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @return the range of matching faro channels
	 */
	@Override
	public List<FaroChannel> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro channels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro channels
	 */
	@Override
	public List<FaroChannel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroChannel> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro channels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro channels
	 */
	@Override
	public List<FaroChannel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroChannel> orderByComparator,
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

		List<FaroChannel> list = null;

		if (useFinderCache) {
			list = (List<FaroChannel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroChannel faroChannel : list) {
					if (groupId != faroChannel.getGroupId()) {
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

			sb.append(_SQL_SELECT_FAROCHANNEL_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroChannelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<FaroChannel>)QueryUtil.list(
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
	 * Returns the first faro channel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro channel
	 * @throws NoSuchFaroChannelException if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel findByGroupId_First(
			long groupId, OrderByComparator<FaroChannel> orderByComparator)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = fetchByGroupId_First(
			groupId, orderByComparator);

		if (faroChannel != null) {
			return faroChannel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFaroChannelException(sb.toString());
	}

	/**
	 * Returns the first faro channel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro channel, or <code>null</code> if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel fetchByGroupId_First(
		long groupId, OrderByComparator<FaroChannel> orderByComparator) {

		List<FaroChannel> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro channel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro channel
	 * @throws NoSuchFaroChannelException if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel findByGroupId_Last(
			long groupId, OrderByComparator<FaroChannel> orderByComparator)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (faroChannel != null) {
			return faroChannel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFaroChannelException(sb.toString());
	}

	/**
	 * Returns the last faro channel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro channel, or <code>null</code> if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel fetchByGroupId_Last(
		long groupId, OrderByComparator<FaroChannel> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FaroChannel> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro channels before and after the current faro channel in the ordered set where groupId = &#63;.
	 *
	 * @param faroChannelId the primary key of the current faro channel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro channel
	 * @throws NoSuchFaroChannelException if a faro channel with the primary key could not be found
	 */
	@Override
	public FaroChannel[] findByGroupId_PrevAndNext(
			long faroChannelId, long groupId,
			OrderByComparator<FaroChannel> orderByComparator)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = findByPrimaryKey(faroChannelId);

		Session session = null;

		try {
			session = openSession();

			FaroChannel[] array = new FaroChannelImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, faroChannel, groupId, orderByComparator, true);

			array[1] = faroChannel;

			array[2] = getByGroupId_PrevAndNext(
				session, faroChannel, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FaroChannel getByGroupId_PrevAndNext(
		Session session, FaroChannel faroChannel, long groupId,
		OrderByComparator<FaroChannel> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FAROCHANNEL_WHERE);

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
			sb.append(FaroChannelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(faroChannel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroChannel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro channels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FaroChannel faroChannel :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(faroChannel);
		}
	}

	/**
	 * Returns the number of faro channels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro channels
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROCHANNEL_WHERE);

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
		"faroChannel.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByWorkspaceGroupId;
	private FinderPath _finderPathWithoutPaginationFindByWorkspaceGroupId;
	private FinderPath _finderPathCountByWorkspaceGroupId;

	/**
	 * Returns all the faro channels where workspaceGroupId = &#63;.
	 *
	 * @param workspaceGroupId the workspace group ID
	 * @return the matching faro channels
	 */
	@Override
	public List<FaroChannel> findByWorkspaceGroupId(long workspaceGroupId) {
		return findByWorkspaceGroupId(
			workspaceGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro channels where workspaceGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param workspaceGroupId the workspace group ID
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @return the range of matching faro channels
	 */
	@Override
	public List<FaroChannel> findByWorkspaceGroupId(
		long workspaceGroupId, int start, int end) {

		return findByWorkspaceGroupId(workspaceGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro channels where workspaceGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param workspaceGroupId the workspace group ID
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro channels
	 */
	@Override
	public List<FaroChannel> findByWorkspaceGroupId(
		long workspaceGroupId, int start, int end,
		OrderByComparator<FaroChannel> orderByComparator) {

		return findByWorkspaceGroupId(
			workspaceGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro channels where workspaceGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param workspaceGroupId the workspace group ID
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro channels
	 */
	@Override
	public List<FaroChannel> findByWorkspaceGroupId(
		long workspaceGroupId, int start, int end,
		OrderByComparator<FaroChannel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByWorkspaceGroupId;
				finderArgs = new Object[] {workspaceGroupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByWorkspaceGroupId;
			finderArgs = new Object[] {
				workspaceGroupId, start, end, orderByComparator
			};
		}

		List<FaroChannel> list = null;

		if (useFinderCache) {
			list = (List<FaroChannel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroChannel faroChannel : list) {
					if (workspaceGroupId != faroChannel.getWorkspaceGroupId()) {
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

			sb.append(_SQL_SELECT_FAROCHANNEL_WHERE);

			sb.append(_FINDER_COLUMN_WORKSPACEGROUPID_WORKSPACEGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroChannelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(workspaceGroupId);

				list = (List<FaroChannel>)QueryUtil.list(
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
	 * Returns the first faro channel in the ordered set where workspaceGroupId = &#63;.
	 *
	 * @param workspaceGroupId the workspace group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro channel
	 * @throws NoSuchFaroChannelException if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel findByWorkspaceGroupId_First(
			long workspaceGroupId,
			OrderByComparator<FaroChannel> orderByComparator)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = fetchByWorkspaceGroupId_First(
			workspaceGroupId, orderByComparator);

		if (faroChannel != null) {
			return faroChannel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("workspaceGroupId=");
		sb.append(workspaceGroupId);

		sb.append("}");

		throw new NoSuchFaroChannelException(sb.toString());
	}

	/**
	 * Returns the first faro channel in the ordered set where workspaceGroupId = &#63;.
	 *
	 * @param workspaceGroupId the workspace group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro channel, or <code>null</code> if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel fetchByWorkspaceGroupId_First(
		long workspaceGroupId,
		OrderByComparator<FaroChannel> orderByComparator) {

		List<FaroChannel> list = findByWorkspaceGroupId(
			workspaceGroupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro channel in the ordered set where workspaceGroupId = &#63;.
	 *
	 * @param workspaceGroupId the workspace group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro channel
	 * @throws NoSuchFaroChannelException if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel findByWorkspaceGroupId_Last(
			long workspaceGroupId,
			OrderByComparator<FaroChannel> orderByComparator)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = fetchByWorkspaceGroupId_Last(
			workspaceGroupId, orderByComparator);

		if (faroChannel != null) {
			return faroChannel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("workspaceGroupId=");
		sb.append(workspaceGroupId);

		sb.append("}");

		throw new NoSuchFaroChannelException(sb.toString());
	}

	/**
	 * Returns the last faro channel in the ordered set where workspaceGroupId = &#63;.
	 *
	 * @param workspaceGroupId the workspace group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro channel, or <code>null</code> if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel fetchByWorkspaceGroupId_Last(
		long workspaceGroupId,
		OrderByComparator<FaroChannel> orderByComparator) {

		int count = countByWorkspaceGroupId(workspaceGroupId);

		if (count == 0) {
			return null;
		}

		List<FaroChannel> list = findByWorkspaceGroupId(
			workspaceGroupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro channels before and after the current faro channel in the ordered set where workspaceGroupId = &#63;.
	 *
	 * @param faroChannelId the primary key of the current faro channel
	 * @param workspaceGroupId the workspace group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro channel
	 * @throws NoSuchFaroChannelException if a faro channel with the primary key could not be found
	 */
	@Override
	public FaroChannel[] findByWorkspaceGroupId_PrevAndNext(
			long faroChannelId, long workspaceGroupId,
			OrderByComparator<FaroChannel> orderByComparator)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = findByPrimaryKey(faroChannelId);

		Session session = null;

		try {
			session = openSession();

			FaroChannel[] array = new FaroChannelImpl[3];

			array[0] = getByWorkspaceGroupId_PrevAndNext(
				session, faroChannel, workspaceGroupId, orderByComparator,
				true);

			array[1] = faroChannel;

			array[2] = getByWorkspaceGroupId_PrevAndNext(
				session, faroChannel, workspaceGroupId, orderByComparator,
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

	protected FaroChannel getByWorkspaceGroupId_PrevAndNext(
		Session session, FaroChannel faroChannel, long workspaceGroupId,
		OrderByComparator<FaroChannel> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FAROCHANNEL_WHERE);

		sb.append(_FINDER_COLUMN_WORKSPACEGROUPID_WORKSPACEGROUPID_2);

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
			sb.append(FaroChannelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(workspaceGroupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(faroChannel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroChannel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro channels where workspaceGroupId = &#63; from the database.
	 *
	 * @param workspaceGroupId the workspace group ID
	 */
	@Override
	public void removeByWorkspaceGroupId(long workspaceGroupId) {
		for (FaroChannel faroChannel :
				findByWorkspaceGroupId(
					workspaceGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(faroChannel);
		}
	}

	/**
	 * Returns the number of faro channels where workspaceGroupId = &#63;.
	 *
	 * @param workspaceGroupId the workspace group ID
	 * @return the number of matching faro channels
	 */
	@Override
	public int countByWorkspaceGroupId(long workspaceGroupId) {
		FinderPath finderPath = _finderPathCountByWorkspaceGroupId;

		Object[] finderArgs = new Object[] {workspaceGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROCHANNEL_WHERE);

			sb.append(_FINDER_COLUMN_WORKSPACEGROUPID_WORKSPACEGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(workspaceGroupId);

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
		_FINDER_COLUMN_WORKSPACEGROUPID_WORKSPACEGROUPID_2 =
			"faroChannel.workspaceGroupId = ?";

	private FinderPath _finderPathWithPaginationFindByG_U;
	private FinderPath _finderPathWithoutPaginationFindByG_U;
	private FinderPath _finderPathCountByG_U;

	/**
	 * Returns all the faro channels where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching faro channels
	 */
	@Override
	public List<FaroChannel> findByG_U(long groupId, long userId) {
		return findByG_U(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro channels where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @return the range of matching faro channels
	 */
	@Override
	public List<FaroChannel> findByG_U(
		long groupId, long userId, int start, int end) {

		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro channels where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro channels
	 */
	@Override
	public List<FaroChannel> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<FaroChannel> orderByComparator) {

		return findByG_U(groupId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro channels where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro channels
	 */
	@Override
	public List<FaroChannel> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<FaroChannel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_U;
				finderArgs = new Object[] {groupId, userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_U;
			finderArgs = new Object[] {
				groupId, userId, start, end, orderByComparator
			};
		}

		List<FaroChannel> list = null;

		if (useFinderCache) {
			list = (List<FaroChannel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroChannel faroChannel : list) {
					if ((groupId != faroChannel.getGroupId()) ||
						(userId != faroChannel.getUserId())) {

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

			sb.append(_SQL_SELECT_FAROCHANNEL_WHERE);

			sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroChannelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				list = (List<FaroChannel>)QueryUtil.list(
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
	 * Returns the first faro channel in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro channel
	 * @throws NoSuchFaroChannelException if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel findByG_U_First(
			long groupId, long userId,
			OrderByComparator<FaroChannel> orderByComparator)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = fetchByG_U_First(
			groupId, userId, orderByComparator);

		if (faroChannel != null) {
			return faroChannel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchFaroChannelException(sb.toString());
	}

	/**
	 * Returns the first faro channel in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro channel, or <code>null</code> if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel fetchByG_U_First(
		long groupId, long userId,
		OrderByComparator<FaroChannel> orderByComparator) {

		List<FaroChannel> list = findByG_U(
			groupId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro channel in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro channel
	 * @throws NoSuchFaroChannelException if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel findByG_U_Last(
			long groupId, long userId,
			OrderByComparator<FaroChannel> orderByComparator)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = fetchByG_U_Last(
			groupId, userId, orderByComparator);

		if (faroChannel != null) {
			return faroChannel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchFaroChannelException(sb.toString());
	}

	/**
	 * Returns the last faro channel in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro channel, or <code>null</code> if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel fetchByG_U_Last(
		long groupId, long userId,
		OrderByComparator<FaroChannel> orderByComparator) {

		int count = countByG_U(groupId, userId);

		if (count == 0) {
			return null;
		}

		List<FaroChannel> list = findByG_U(
			groupId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro channels before and after the current faro channel in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param faroChannelId the primary key of the current faro channel
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro channel
	 * @throws NoSuchFaroChannelException if a faro channel with the primary key could not be found
	 */
	@Override
	public FaroChannel[] findByG_U_PrevAndNext(
			long faroChannelId, long groupId, long userId,
			OrderByComparator<FaroChannel> orderByComparator)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = findByPrimaryKey(faroChannelId);

		Session session = null;

		try {
			session = openSession();

			FaroChannel[] array = new FaroChannelImpl[3];

			array[0] = getByG_U_PrevAndNext(
				session, faroChannel, groupId, userId, orderByComparator, true);

			array[1] = faroChannel;

			array[2] = getByG_U_PrevAndNext(
				session, faroChannel, groupId, userId, orderByComparator,
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

	protected FaroChannel getByG_U_PrevAndNext(
		Session session, FaroChannel faroChannel, long groupId, long userId,
		OrderByComparator<FaroChannel> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FAROCHANNEL_WHERE);

		sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_USERID_2);

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
			sb.append(FaroChannelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(faroChannel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroChannel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro channels where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_U(long groupId, long userId) {
		for (FaroChannel faroChannel :
				findByG_U(
					groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(faroChannel);
		}
	}

	/**
	 * Returns the number of faro channels where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching faro channels
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		FinderPath finderPath = _finderPathCountByG_U;

		Object[] finderArgs = new Object[] {groupId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FAROCHANNEL_WHERE);

			sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 =
		"faroChannel.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_USERID_2 =
		"faroChannel.userId = ?";

	private FinderPath _finderPathFetchByC_W;
	private FinderPath _finderPathCountByC_W;

	/**
	 * Returns the faro channel where channelId = &#63; and workspaceGroupId = &#63; or throws a <code>NoSuchFaroChannelException</code> if it could not be found.
	 *
	 * @param channelId the channel ID
	 * @param workspaceGroupId the workspace group ID
	 * @return the matching faro channel
	 * @throws NoSuchFaroChannelException if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel findByC_W(String channelId, long workspaceGroupId)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = fetchByC_W(channelId, workspaceGroupId);

		if (faroChannel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("channelId=");
			sb.append(channelId);

			sb.append(", workspaceGroupId=");
			sb.append(workspaceGroupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFaroChannelException(sb.toString());
		}

		return faroChannel;
	}

	/**
	 * Returns the faro channel where channelId = &#63; and workspaceGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param channelId the channel ID
	 * @param workspaceGroupId the workspace group ID
	 * @return the matching faro channel, or <code>null</code> if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel fetchByC_W(String channelId, long workspaceGroupId) {
		return fetchByC_W(channelId, workspaceGroupId, true);
	}

	/**
	 * Returns the faro channel where channelId = &#63; and workspaceGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param channelId the channel ID
	 * @param workspaceGroupId the workspace group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro channel, or <code>null</code> if a matching faro channel could not be found
	 */
	@Override
	public FaroChannel fetchByC_W(
		String channelId, long workspaceGroupId, boolean useFinderCache) {

		channelId = Objects.toString(channelId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {channelId, workspaceGroupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_W, finderArgs, this);
		}

		if (result instanceof FaroChannel) {
			FaroChannel faroChannel = (FaroChannel)result;

			if (!Objects.equals(channelId, faroChannel.getChannelId()) ||
				(workspaceGroupId != faroChannel.getWorkspaceGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_FAROCHANNEL_WHERE);

			boolean bindChannelId = false;

			if (channelId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_W_CHANNELID_3);
			}
			else {
				bindChannelId = true;

				sb.append(_FINDER_COLUMN_C_W_CHANNELID_2);
			}

			sb.append(_FINDER_COLUMN_C_W_WORKSPACEGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindChannelId) {
					queryPos.add(channelId);
				}

				queryPos.add(workspaceGroupId);

				List<FaroChannel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_W, finderArgs, list);
					}
				}
				else {
					FaroChannel faroChannel = list.get(0);

					result = faroChannel;

					cacheResult(faroChannel);
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
			return (FaroChannel)result;
		}
	}

	/**
	 * Removes the faro channel where channelId = &#63; and workspaceGroupId = &#63; from the database.
	 *
	 * @param channelId the channel ID
	 * @param workspaceGroupId the workspace group ID
	 * @return the faro channel that was removed
	 */
	@Override
	public FaroChannel removeByC_W(String channelId, long workspaceGroupId)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = findByC_W(channelId, workspaceGroupId);

		return remove(faroChannel);
	}

	/**
	 * Returns the number of faro channels where channelId = &#63; and workspaceGroupId = &#63;.
	 *
	 * @param channelId the channel ID
	 * @param workspaceGroupId the workspace group ID
	 * @return the number of matching faro channels
	 */
	@Override
	public int countByC_W(String channelId, long workspaceGroupId) {
		channelId = Objects.toString(channelId, "");

		FinderPath finderPath = _finderPathCountByC_W;

		Object[] finderArgs = new Object[] {channelId, workspaceGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FAROCHANNEL_WHERE);

			boolean bindChannelId = false;

			if (channelId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_W_CHANNELID_3);
			}
			else {
				bindChannelId = true;

				sb.append(_FINDER_COLUMN_C_W_CHANNELID_2);
			}

			sb.append(_FINDER_COLUMN_C_W_WORKSPACEGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindChannelId) {
					queryPos.add(channelId);
				}

				queryPos.add(workspaceGroupId);

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

	private static final String _FINDER_COLUMN_C_W_CHANNELID_2 =
		"faroChannel.channelId = ? AND ";

	private static final String _FINDER_COLUMN_C_W_CHANNELID_3 =
		"(faroChannel.channelId IS NULL OR faroChannel.channelId = '') AND ";

	private static final String _FINDER_COLUMN_C_W_WORKSPACEGROUPID_2 =
		"faroChannel.workspaceGroupId = ?";

	public FaroChannelPersistenceImpl() {
		setModelClass(FaroChannel.class);

		setModelImplClass(FaroChannelImpl.class);
		setModelPKClass(long.class);

		setTable(FaroChannelTable.INSTANCE);
	}

	/**
	 * Caches the faro channel in the entity cache if it is enabled.
	 *
	 * @param faroChannel the faro channel
	 */
	@Override
	public void cacheResult(FaroChannel faroChannel) {
		entityCache.putResult(
			FaroChannelImpl.class, faroChannel.getPrimaryKey(), faroChannel);

		finderCache.putResult(
			_finderPathFetchByC_W,
			new Object[] {
				faroChannel.getChannelId(), faroChannel.getWorkspaceGroupId()
			},
			faroChannel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the faro channels in the entity cache if it is enabled.
	 *
	 * @param faroChannels the faro channels
	 */
	@Override
	public void cacheResult(List<FaroChannel> faroChannels) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (faroChannels.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FaroChannel faroChannel : faroChannels) {
			if (entityCache.getResult(
					FaroChannelImpl.class, faroChannel.getPrimaryKey()) ==
						null) {

				cacheResult(faroChannel);
			}
		}
	}

	/**
	 * Clears the cache for all faro channels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FaroChannelImpl.class);

		finderCache.clearCache(FaroChannelImpl.class);
	}

	/**
	 * Clears the cache for the faro channel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FaroChannel faroChannel) {
		entityCache.removeResult(FaroChannelImpl.class, faroChannel);
	}

	@Override
	public void clearCache(List<FaroChannel> faroChannels) {
		for (FaroChannel faroChannel : faroChannels) {
			entityCache.removeResult(FaroChannelImpl.class, faroChannel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FaroChannelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FaroChannelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FaroChannelModelImpl faroChannelModelImpl) {

		Object[] args = new Object[] {
			faroChannelModelImpl.getChannelId(),
			faroChannelModelImpl.getWorkspaceGroupId()
		};

		finderCache.putResult(_finderPathCountByC_W, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_W, args, faroChannelModelImpl);
	}

	/**
	 * Creates a new faro channel with the primary key. Does not add the faro channel to the database.
	 *
	 * @param faroChannelId the primary key for the new faro channel
	 * @return the new faro channel
	 */
	@Override
	public FaroChannel create(long faroChannelId) {
		FaroChannel faroChannel = new FaroChannelImpl();

		faroChannel.setNew(true);
		faroChannel.setPrimaryKey(faroChannelId);

		faroChannel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return faroChannel;
	}

	/**
	 * Removes the faro channel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroChannelId the primary key of the faro channel
	 * @return the faro channel that was removed
	 * @throws NoSuchFaroChannelException if a faro channel with the primary key could not be found
	 */
	@Override
	public FaroChannel remove(long faroChannelId)
		throws NoSuchFaroChannelException {

		return remove((Serializable)faroChannelId);
	}

	/**
	 * Removes the faro channel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the faro channel
	 * @return the faro channel that was removed
	 * @throws NoSuchFaroChannelException if a faro channel with the primary key could not be found
	 */
	@Override
	public FaroChannel remove(Serializable primaryKey)
		throws NoSuchFaroChannelException {

		Session session = null;

		try {
			session = openSession();

			FaroChannel faroChannel = (FaroChannel)session.get(
				FaroChannelImpl.class, primaryKey);

			if (faroChannel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFaroChannelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(faroChannel);
		}
		catch (NoSuchFaroChannelException noSuchEntityException) {
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
	protected FaroChannel removeImpl(FaroChannel faroChannel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(faroChannel)) {
				faroChannel = (FaroChannel)session.get(
					FaroChannelImpl.class, faroChannel.getPrimaryKeyObj());
			}

			if (faroChannel != null) {
				session.delete(faroChannel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (faroChannel != null) {
			clearCache(faroChannel);
		}

		return faroChannel;
	}

	@Override
	public FaroChannel updateImpl(FaroChannel faroChannel) {
		boolean isNew = faroChannel.isNew();

		if (!(faroChannel instanceof FaroChannelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(faroChannel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(faroChannel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in faroChannel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FaroChannel implementation " +
					faroChannel.getClass());
		}

		FaroChannelModelImpl faroChannelModelImpl =
			(FaroChannelModelImpl)faroChannel;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(faroChannel);
			}
			else {
				faroChannel = (FaroChannel)session.merge(faroChannel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FaroChannelImpl.class, faroChannelModelImpl, false, true);

		cacheUniqueFindersCache(faroChannelModelImpl);

		if (isNew) {
			faroChannel.setNew(false);
		}

		faroChannel.resetOriginalValues();

		return faroChannel;
	}

	/**
	 * Returns the faro channel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the faro channel
	 * @return the faro channel
	 * @throws NoSuchFaroChannelException if a faro channel with the primary key could not be found
	 */
	@Override
	public FaroChannel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFaroChannelException {

		FaroChannel faroChannel = fetchByPrimaryKey(primaryKey);

		if (faroChannel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFaroChannelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return faroChannel;
	}

	/**
	 * Returns the faro channel with the primary key or throws a <code>NoSuchFaroChannelException</code> if it could not be found.
	 *
	 * @param faroChannelId the primary key of the faro channel
	 * @return the faro channel
	 * @throws NoSuchFaroChannelException if a faro channel with the primary key could not be found
	 */
	@Override
	public FaroChannel findByPrimaryKey(long faroChannelId)
		throws NoSuchFaroChannelException {

		return findByPrimaryKey((Serializable)faroChannelId);
	}

	/**
	 * Returns the faro channel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroChannelId the primary key of the faro channel
	 * @return the faro channel, or <code>null</code> if a faro channel with the primary key could not be found
	 */
	@Override
	public FaroChannel fetchByPrimaryKey(long faroChannelId) {
		return fetchByPrimaryKey((Serializable)faroChannelId);
	}

	/**
	 * Returns all the faro channels.
	 *
	 * @return the faro channels
	 */
	@Override
	public List<FaroChannel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro channels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @return the range of faro channels
	 */
	@Override
	public List<FaroChannel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro channels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro channels
	 */
	@Override
	public List<FaroChannel> findAll(
		int start, int end, OrderByComparator<FaroChannel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro channels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroChannelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro channels
	 * @param end the upper bound of the range of faro channels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro channels
	 */
	@Override
	public List<FaroChannel> findAll(
		int start, int end, OrderByComparator<FaroChannel> orderByComparator,
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

		List<FaroChannel> list = null;

		if (useFinderCache) {
			list = (List<FaroChannel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FAROCHANNEL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FAROCHANNEL;

				sql = sql.concat(FaroChannelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FaroChannel>)QueryUtil.list(
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
	 * Removes all the faro channels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FaroChannel faroChannel : findAll()) {
			remove(faroChannel);
		}
	}

	/**
	 * Returns the number of faro channels.
	 *
	 * @return the number of faro channels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FAROCHANNEL);

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
		return "faroChannelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FAROCHANNEL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FaroChannelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the faro channel persistence.
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

		_finderPathWithPaginationFindByWorkspaceGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByWorkspaceGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"workspaceGroupId"}, true);

		_finderPathWithoutPaginationFindByWorkspaceGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByWorkspaceGroupId",
			new String[] {Long.class.getName()},
			new String[] {"workspaceGroupId"}, true);

		_finderPathCountByWorkspaceGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByWorkspaceGroupId", new String[] {Long.class.getName()},
			new String[] {"workspaceGroupId"}, false);

		_finderPathWithPaginationFindByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId"}, true);

		_finderPathWithoutPaginationFindByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, true);

		_finderPathCountByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, false);

		_finderPathFetchByC_W = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_W",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"channelId", "workspaceGroupId"}, true);

		_finderPathCountByC_W = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_W",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"channelId", "workspaceGroupId"}, false);

		FaroChannelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FaroChannelUtil.setPersistence(null);

		entityCache.removeCache(FaroChannelImpl.class.getName());
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

	private static final String _SQL_SELECT_FAROCHANNEL =
		"SELECT faroChannel FROM FaroChannel faroChannel";

	private static final String _SQL_SELECT_FAROCHANNEL_WHERE =
		"SELECT faroChannel FROM FaroChannel faroChannel WHERE ";

	private static final String _SQL_COUNT_FAROCHANNEL =
		"SELECT COUNT(faroChannel) FROM FaroChannel faroChannel";

	private static final String _SQL_COUNT_FAROCHANNEL_WHERE =
		"SELECT COUNT(faroChannel) FROM FaroChannel faroChannel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "faroChannel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FaroChannel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FaroChannel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FaroChannelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}