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

import com.liferay.osb.faro.exception.NoSuchFaroProjectException;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.model.FaroProjectTable;
import com.liferay.osb.faro.model.impl.FaroProjectImpl;
import com.liferay.osb.faro.model.impl.FaroProjectModelImpl;
import com.liferay.osb.faro.service.persistence.FaroProjectPersistence;
import com.liferay.osb.faro.service.persistence.FaroProjectUtil;
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
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
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
 * The persistence implementation for the faro project service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @generated
 */
@Component(service = FaroProjectPersistence.class)
public class FaroProjectPersistenceImpl
	extends BasePersistenceImpl<FaroProject> implements FaroProjectPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FaroProjectUtil</code> to access the faro project persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FaroProjectImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns the faro project where groupId = &#63; or throws a <code>NoSuchFaroProjectException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @return the matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByGroupId(long groupId)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByGroupId(groupId);

		if (faroProject == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFaroProjectException(sb.toString());
		}

		return faroProject;
	}

	/**
	 * Returns the faro project where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByGroupId(long groupId) {
		return fetchByGroupId(groupId, true);
	}

	/**
	 * Returns the faro project where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByGroupId(long groupId, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByGroupId, finderArgs, this);
		}

		if (result instanceof FaroProject) {
			FaroProject faroProject = (FaroProject)result;

			if (groupId != faroProject.getGroupId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_FAROPROJECT_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				List<FaroProject> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByGroupId, finderArgs, list);
					}
				}
				else {
					FaroProject faroProject = list.get(0);

					result = faroProject;

					cacheResult(faroProject);
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
			return (FaroProject)result;
		}
	}

	/**
	 * Removes the faro project where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @return the faro project that was removed
	 */
	@Override
	public FaroProject removeByGroupId(long groupId)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = findByGroupId(groupId);

		return remove(faroProject);
	}

	/**
	 * Returns the number of faro projects where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro projects
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROPROJECT_WHERE);

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
		"faroProject.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the faro projects where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching faro projects
	 */
	@Override
	public List<FaroProject> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro projects where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @return the range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro projects where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByUserId(
		long userId, int start, int end,
		OrderByComparator<FaroProject> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro projects where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByUserId(
		long userId, int start, int end,
		OrderByComparator<FaroProject> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<FaroProject> list = null;

		if (useFinderCache) {
			list = (List<FaroProject>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroProject faroProject : list) {
					if (userId != faroProject.getUserId()) {
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

			sb.append(_SQL_SELECT_FAROPROJECT_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroProjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<FaroProject>)QueryUtil.list(
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
	 * Returns the first faro project in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByUserId_First(
			long userId, OrderByComparator<FaroProject> orderByComparator)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByUserId_First(
			userId, orderByComparator);

		if (faroProject != null) {
			return faroProject;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchFaroProjectException(sb.toString());
	}

	/**
	 * Returns the first faro project in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByUserId_First(
		long userId, OrderByComparator<FaroProject> orderByComparator) {

		List<FaroProject> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro project in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByUserId_Last(
			long userId, OrderByComparator<FaroProject> orderByComparator)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByUserId_Last(userId, orderByComparator);

		if (faroProject != null) {
			return faroProject;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchFaroProjectException(sb.toString());
	}

	/**
	 * Returns the last faro project in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByUserId_Last(
		long userId, OrderByComparator<FaroProject> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<FaroProject> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro projects before and after the current faro project in the ordered set where userId = &#63;.
	 *
	 * @param faroProjectId the primary key of the current faro project
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro project
	 * @throws NoSuchFaroProjectException if a faro project with the primary key could not be found
	 */
	@Override
	public FaroProject[] findByUserId_PrevAndNext(
			long faroProjectId, long userId,
			OrderByComparator<FaroProject> orderByComparator)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = findByPrimaryKey(faroProjectId);

		Session session = null;

		try {
			session = openSession();

			FaroProject[] array = new FaroProjectImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, faroProject, userId, orderByComparator, true);

			array[1] = faroProject;

			array[2] = getByUserId_PrevAndNext(
				session, faroProject, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FaroProject getByUserId_PrevAndNext(
		Session session, FaroProject faroProject, long userId,
		OrderByComparator<FaroProject> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FAROPROJECT_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

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
			sb.append(FaroProjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(faroProject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroProject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro projects where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (FaroProject faroProject :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(faroProject);
		}
	}

	/**
	 * Returns the number of faro projects where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching faro projects
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROPROJECT_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"faroProject.userId = ?";

	private FinderPath _finderPathFetchByCorpProjectUuid;
	private FinderPath _finderPathCountByCorpProjectUuid;

	/**
	 * Returns the faro project where corpProjectUuid = &#63; or throws a <code>NoSuchFaroProjectException</code> if it could not be found.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByCorpProjectUuid(String corpProjectUuid)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByCorpProjectUuid(corpProjectUuid);

		if (faroProject == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("corpProjectUuid=");
			sb.append(corpProjectUuid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFaroProjectException(sb.toString());
		}

		return faroProject;
	}

	/**
	 * Returns the faro project where corpProjectUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByCorpProjectUuid(String corpProjectUuid) {
		return fetchByCorpProjectUuid(corpProjectUuid, true);
	}

	/**
	 * Returns the faro project where corpProjectUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByCorpProjectUuid(
		String corpProjectUuid, boolean useFinderCache) {

		corpProjectUuid = Objects.toString(corpProjectUuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {corpProjectUuid};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCorpProjectUuid, finderArgs, this);
		}

		if (result instanceof FaroProject) {
			FaroProject faroProject = (FaroProject)result;

			if (!Objects.equals(
					corpProjectUuid, faroProject.getCorpProjectUuid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_FAROPROJECT_WHERE);

			boolean bindCorpProjectUuid = false;

			if (corpProjectUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_3);
			}
			else {
				bindCorpProjectUuid = true;

				sb.append(_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCorpProjectUuid) {
					queryPos.add(corpProjectUuid);
				}

				List<FaroProject> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCorpProjectUuid, finderArgs,
							list);
					}
				}
				else {
					FaroProject faroProject = list.get(0);

					result = faroProject;

					cacheResult(faroProject);
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
			return (FaroProject)result;
		}
	}

	/**
	 * Removes the faro project where corpProjectUuid = &#63; from the database.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the faro project that was removed
	 */
	@Override
	public FaroProject removeByCorpProjectUuid(String corpProjectUuid)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = findByCorpProjectUuid(corpProjectUuid);

		return remove(faroProject);
	}

	/**
	 * Returns the number of faro projects where corpProjectUuid = &#63;.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the number of matching faro projects
	 */
	@Override
	public int countByCorpProjectUuid(String corpProjectUuid) {
		corpProjectUuid = Objects.toString(corpProjectUuid, "");

		FinderPath finderPath = _finderPathCountByCorpProjectUuid;

		Object[] finderArgs = new Object[] {corpProjectUuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROPROJECT_WHERE);

			boolean bindCorpProjectUuid = false;

			if (corpProjectUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_3);
			}
			else {
				bindCorpProjectUuid = true;

				sb.append(_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCorpProjectUuid) {
					queryPos.add(corpProjectUuid);
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

	private static final String
		_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_2 =
			"faroProject.corpProjectUuid = ?";

	private static final String
		_FINDER_COLUMN_CORPPROJECTUUID_CORPPROJECTUUID_3 =
			"(faroProject.corpProjectUuid IS NULL OR faroProject.corpProjectUuid = '')";

	private FinderPath _finderPathWithPaginationFindByServerLocation;
	private FinderPath _finderPathWithoutPaginationFindByServerLocation;
	private FinderPath _finderPathCountByServerLocation;

	/**
	 * Returns all the faro projects where serverLocation = &#63;.
	 *
	 * @param serverLocation the server location
	 * @return the matching faro projects
	 */
	@Override
	public List<FaroProject> findByServerLocation(String serverLocation) {
		return findByServerLocation(
			serverLocation, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro projects where serverLocation = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param serverLocation the server location
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @return the range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByServerLocation(
		String serverLocation, int start, int end) {

		return findByServerLocation(serverLocation, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro projects where serverLocation = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param serverLocation the server location
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByServerLocation(
		String serverLocation, int start, int end,
		OrderByComparator<FaroProject> orderByComparator) {

		return findByServerLocation(
			serverLocation, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro projects where serverLocation = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param serverLocation the server location
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByServerLocation(
		String serverLocation, int start, int end,
		OrderByComparator<FaroProject> orderByComparator,
		boolean useFinderCache) {

		serverLocation = Objects.toString(serverLocation, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByServerLocation;
				finderArgs = new Object[] {serverLocation};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByServerLocation;
			finderArgs = new Object[] {
				serverLocation, start, end, orderByComparator
			};
		}

		List<FaroProject> list = null;

		if (useFinderCache) {
			list = (List<FaroProject>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroProject faroProject : list) {
					if (!serverLocation.equals(
							faroProject.getServerLocation())) {

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

			sb.append(_SQL_SELECT_FAROPROJECT_WHERE);

			boolean bindServerLocation = false;

			if (serverLocation.isEmpty()) {
				sb.append(_FINDER_COLUMN_SERVERLOCATION_SERVERLOCATION_3);
			}
			else {
				bindServerLocation = true;

				sb.append(_FINDER_COLUMN_SERVERLOCATION_SERVERLOCATION_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroProjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindServerLocation) {
					queryPos.add(serverLocation);
				}

				list = (List<FaroProject>)QueryUtil.list(
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
	 * Returns the first faro project in the ordered set where serverLocation = &#63;.
	 *
	 * @param serverLocation the server location
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByServerLocation_First(
			String serverLocation,
			OrderByComparator<FaroProject> orderByComparator)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByServerLocation_First(
			serverLocation, orderByComparator);

		if (faroProject != null) {
			return faroProject;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("serverLocation=");
		sb.append(serverLocation);

		sb.append("}");

		throw new NoSuchFaroProjectException(sb.toString());
	}

	/**
	 * Returns the first faro project in the ordered set where serverLocation = &#63;.
	 *
	 * @param serverLocation the server location
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByServerLocation_First(
		String serverLocation,
		OrderByComparator<FaroProject> orderByComparator) {

		List<FaroProject> list = findByServerLocation(
			serverLocation, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro project in the ordered set where serverLocation = &#63;.
	 *
	 * @param serverLocation the server location
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByServerLocation_Last(
			String serverLocation,
			OrderByComparator<FaroProject> orderByComparator)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByServerLocation_Last(
			serverLocation, orderByComparator);

		if (faroProject != null) {
			return faroProject;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("serverLocation=");
		sb.append(serverLocation);

		sb.append("}");

		throw new NoSuchFaroProjectException(sb.toString());
	}

	/**
	 * Returns the last faro project in the ordered set where serverLocation = &#63;.
	 *
	 * @param serverLocation the server location
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByServerLocation_Last(
		String serverLocation,
		OrderByComparator<FaroProject> orderByComparator) {

		int count = countByServerLocation(serverLocation);

		if (count == 0) {
			return null;
		}

		List<FaroProject> list = findByServerLocation(
			serverLocation, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro projects before and after the current faro project in the ordered set where serverLocation = &#63;.
	 *
	 * @param faroProjectId the primary key of the current faro project
	 * @param serverLocation the server location
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro project
	 * @throws NoSuchFaroProjectException if a faro project with the primary key could not be found
	 */
	@Override
	public FaroProject[] findByServerLocation_PrevAndNext(
			long faroProjectId, String serverLocation,
			OrderByComparator<FaroProject> orderByComparator)
		throws NoSuchFaroProjectException {

		serverLocation = Objects.toString(serverLocation, "");

		FaroProject faroProject = findByPrimaryKey(faroProjectId);

		Session session = null;

		try {
			session = openSession();

			FaroProject[] array = new FaroProjectImpl[3];

			array[0] = getByServerLocation_PrevAndNext(
				session, faroProject, serverLocation, orderByComparator, true);

			array[1] = faroProject;

			array[2] = getByServerLocation_PrevAndNext(
				session, faroProject, serverLocation, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FaroProject getByServerLocation_PrevAndNext(
		Session session, FaroProject faroProject, String serverLocation,
		OrderByComparator<FaroProject> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FAROPROJECT_WHERE);

		boolean bindServerLocation = false;

		if (serverLocation.isEmpty()) {
			sb.append(_FINDER_COLUMN_SERVERLOCATION_SERVERLOCATION_3);
		}
		else {
			bindServerLocation = true;

			sb.append(_FINDER_COLUMN_SERVERLOCATION_SERVERLOCATION_2);
		}

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
			sb.append(FaroProjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindServerLocation) {
			queryPos.add(serverLocation);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(faroProject)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroProject> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro projects where serverLocation = &#63; from the database.
	 *
	 * @param serverLocation the server location
	 */
	@Override
	public void removeByServerLocation(String serverLocation) {
		for (FaroProject faroProject :
				findByServerLocation(
					serverLocation, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(faroProject);
		}
	}

	/**
	 * Returns the number of faro projects where serverLocation = &#63;.
	 *
	 * @param serverLocation the server location
	 * @return the number of matching faro projects
	 */
	@Override
	public int countByServerLocation(String serverLocation) {
		serverLocation = Objects.toString(serverLocation, "");

		FinderPath finderPath = _finderPathCountByServerLocation;

		Object[] finderArgs = new Object[] {serverLocation};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROPROJECT_WHERE);

			boolean bindServerLocation = false;

			if (serverLocation.isEmpty()) {
				sb.append(_FINDER_COLUMN_SERVERLOCATION_SERVERLOCATION_3);
			}
			else {
				bindServerLocation = true;

				sb.append(_FINDER_COLUMN_SERVERLOCATION_SERVERLOCATION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindServerLocation) {
					queryPos.add(serverLocation);
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

	private static final String _FINDER_COLUMN_SERVERLOCATION_SERVERLOCATION_2 =
		"faroProject.serverLocation = ?";

	private static final String _FINDER_COLUMN_SERVERLOCATION_SERVERLOCATION_3 =
		"(faroProject.serverLocation IS NULL OR faroProject.serverLocation = '')";

	private FinderPath _finderPathFetchByWeDeployKey;
	private FinderPath _finderPathCountByWeDeployKey;

	/**
	 * Returns the faro project where weDeployKey = &#63; or throws a <code>NoSuchFaroProjectException</code> if it could not be found.
	 *
	 * @param weDeployKey the we deploy key
	 * @return the matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByWeDeployKey(String weDeployKey)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByWeDeployKey(weDeployKey);

		if (faroProject == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("weDeployKey=");
			sb.append(weDeployKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFaroProjectException(sb.toString());
		}

		return faroProject;
	}

	/**
	 * Returns the faro project where weDeployKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param weDeployKey the we deploy key
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByWeDeployKey(String weDeployKey) {
		return fetchByWeDeployKey(weDeployKey, true);
	}

	/**
	 * Returns the faro project where weDeployKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param weDeployKey the we deploy key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByWeDeployKey(
		String weDeployKey, boolean useFinderCache) {

		weDeployKey = Objects.toString(weDeployKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {weDeployKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByWeDeployKey, finderArgs, this);
		}

		if (result instanceof FaroProject) {
			FaroProject faroProject = (FaroProject)result;

			if (!Objects.equals(weDeployKey, faroProject.getWeDeployKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_FAROPROJECT_WHERE);

			boolean bindWeDeployKey = false;

			if (weDeployKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_WEDEPLOYKEY_WEDEPLOYKEY_3);
			}
			else {
				bindWeDeployKey = true;

				sb.append(_FINDER_COLUMN_WEDEPLOYKEY_WEDEPLOYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindWeDeployKey) {
					queryPos.add(weDeployKey);
				}

				List<FaroProject> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByWeDeployKey, finderArgs, list);
					}
				}
				else {
					FaroProject faroProject = list.get(0);

					result = faroProject;

					cacheResult(faroProject);
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
			return (FaroProject)result;
		}
	}

	/**
	 * Removes the faro project where weDeployKey = &#63; from the database.
	 *
	 * @param weDeployKey the we deploy key
	 * @return the faro project that was removed
	 */
	@Override
	public FaroProject removeByWeDeployKey(String weDeployKey)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = findByWeDeployKey(weDeployKey);

		return remove(faroProject);
	}

	/**
	 * Returns the number of faro projects where weDeployKey = &#63;.
	 *
	 * @param weDeployKey the we deploy key
	 * @return the number of matching faro projects
	 */
	@Override
	public int countByWeDeployKey(String weDeployKey) {
		weDeployKey = Objects.toString(weDeployKey, "");

		FinderPath finderPath = _finderPathCountByWeDeployKey;

		Object[] finderArgs = new Object[] {weDeployKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROPROJECT_WHERE);

			boolean bindWeDeployKey = false;

			if (weDeployKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_WEDEPLOYKEY_WEDEPLOYKEY_3);
			}
			else {
				bindWeDeployKey = true;

				sb.append(_FINDER_COLUMN_WEDEPLOYKEY_WEDEPLOYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindWeDeployKey) {
					queryPos.add(weDeployKey);
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

	private static final String _FINDER_COLUMN_WEDEPLOYKEY_WEDEPLOYKEY_2 =
		"faroProject.weDeployKey = ?";

	private static final String _FINDER_COLUMN_WEDEPLOYKEY_WEDEPLOYKEY_3 =
		"(faroProject.weDeployKey IS NULL OR faroProject.weDeployKey = '')";

	public FaroProjectPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("state", "state_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FaroProject.class);

		setModelImplClass(FaroProjectImpl.class);
		setModelPKClass(long.class);

		setTable(FaroProjectTable.INSTANCE);
	}

	/**
	 * Caches the faro project in the entity cache if it is enabled.
	 *
	 * @param faroProject the faro project
	 */
	@Override
	public void cacheResult(FaroProject faroProject) {
		entityCache.putResult(
			FaroProjectImpl.class, faroProject.getPrimaryKey(), faroProject);

		finderCache.putResult(
			_finderPathFetchByGroupId, new Object[] {faroProject.getGroupId()},
			faroProject);

		finderCache.putResult(
			_finderPathFetchByCorpProjectUuid,
			new Object[] {faroProject.getCorpProjectUuid()}, faroProject);

		finderCache.putResult(
			_finderPathFetchByWeDeployKey,
			new Object[] {faroProject.getWeDeployKey()}, faroProject);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the faro projects in the entity cache if it is enabled.
	 *
	 * @param faroProjects the faro projects
	 */
	@Override
	public void cacheResult(List<FaroProject> faroProjects) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (faroProjects.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FaroProject faroProject : faroProjects) {
			if (entityCache.getResult(
					FaroProjectImpl.class, faroProject.getPrimaryKey()) ==
						null) {

				cacheResult(faroProject);
			}
		}
	}

	/**
	 * Clears the cache for all faro projects.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FaroProjectImpl.class);

		finderCache.clearCache(FaroProjectImpl.class);
	}

	/**
	 * Clears the cache for the faro project.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FaroProject faroProject) {
		entityCache.removeResult(FaroProjectImpl.class, faroProject);
	}

	@Override
	public void clearCache(List<FaroProject> faroProjects) {
		for (FaroProject faroProject : faroProjects) {
			entityCache.removeResult(FaroProjectImpl.class, faroProject);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FaroProjectImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FaroProjectImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FaroProjectModelImpl faroProjectModelImpl) {

		Object[] args = new Object[] {faroProjectModelImpl.getGroupId()};

		finderCache.putResult(_finderPathCountByGroupId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByGroupId, args, faroProjectModelImpl);

		args = new Object[] {faroProjectModelImpl.getCorpProjectUuid()};

		finderCache.putResult(
			_finderPathCountByCorpProjectUuid, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCorpProjectUuid, args, faroProjectModelImpl);

		args = new Object[] {faroProjectModelImpl.getWeDeployKey()};

		finderCache.putResult(
			_finderPathCountByWeDeployKey, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByWeDeployKey, args, faroProjectModelImpl);
	}

	/**
	 * Creates a new faro project with the primary key. Does not add the faro project to the database.
	 *
	 * @param faroProjectId the primary key for the new faro project
	 * @return the new faro project
	 */
	@Override
	public FaroProject create(long faroProjectId) {
		FaroProject faroProject = new FaroProjectImpl();

		faroProject.setNew(true);
		faroProject.setPrimaryKey(faroProjectId);

		faroProject.setCompanyId(CompanyThreadLocal.getCompanyId());

		return faroProject;
	}

	/**
	 * Removes the faro project with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroProjectId the primary key of the faro project
	 * @return the faro project that was removed
	 * @throws NoSuchFaroProjectException if a faro project with the primary key could not be found
	 */
	@Override
	public FaroProject remove(long faroProjectId)
		throws NoSuchFaroProjectException {

		return remove((Serializable)faroProjectId);
	}

	/**
	 * Removes the faro project with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the faro project
	 * @return the faro project that was removed
	 * @throws NoSuchFaroProjectException if a faro project with the primary key could not be found
	 */
	@Override
	public FaroProject remove(Serializable primaryKey)
		throws NoSuchFaroProjectException {

		Session session = null;

		try {
			session = openSession();

			FaroProject faroProject = (FaroProject)session.get(
				FaroProjectImpl.class, primaryKey);

			if (faroProject == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFaroProjectException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(faroProject);
		}
		catch (NoSuchFaroProjectException noSuchEntityException) {
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
	protected FaroProject removeImpl(FaroProject faroProject) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(faroProject)) {
				faroProject = (FaroProject)session.get(
					FaroProjectImpl.class, faroProject.getPrimaryKeyObj());
			}

			if (faroProject != null) {
				session.delete(faroProject);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (faroProject != null) {
			clearCache(faroProject);
		}

		return faroProject;
	}

	@Override
	public FaroProject updateImpl(FaroProject faroProject) {
		boolean isNew = faroProject.isNew();

		if (!(faroProject instanceof FaroProjectModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(faroProject.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(faroProject);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in faroProject proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FaroProject implementation " +
					faroProject.getClass());
		}

		FaroProjectModelImpl faroProjectModelImpl =
			(FaroProjectModelImpl)faroProject;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(faroProject);
			}
			else {
				faroProject = (FaroProject)session.merge(faroProject);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FaroProjectImpl.class, faroProjectModelImpl, false, true);

		cacheUniqueFindersCache(faroProjectModelImpl);

		if (isNew) {
			faroProject.setNew(false);
		}

		faroProject.resetOriginalValues();

		return faroProject;
	}

	/**
	 * Returns the faro project with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the faro project
	 * @return the faro project
	 * @throws NoSuchFaroProjectException if a faro project with the primary key could not be found
	 */
	@Override
	public FaroProject findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByPrimaryKey(primaryKey);

		if (faroProject == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFaroProjectException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return faroProject;
	}

	/**
	 * Returns the faro project with the primary key or throws a <code>NoSuchFaroProjectException</code> if it could not be found.
	 *
	 * @param faroProjectId the primary key of the faro project
	 * @return the faro project
	 * @throws NoSuchFaroProjectException if a faro project with the primary key could not be found
	 */
	@Override
	public FaroProject findByPrimaryKey(long faroProjectId)
		throws NoSuchFaroProjectException {

		return findByPrimaryKey((Serializable)faroProjectId);
	}

	/**
	 * Returns the faro project with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroProjectId the primary key of the faro project
	 * @return the faro project, or <code>null</code> if a faro project with the primary key could not be found
	 */
	@Override
	public FaroProject fetchByPrimaryKey(long faroProjectId) {
		return fetchByPrimaryKey((Serializable)faroProjectId);
	}

	/**
	 * Returns all the faro projects.
	 *
	 * @return the faro projects
	 */
	@Override
	public List<FaroProject> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @return the range of faro projects
	 */
	@Override
	public List<FaroProject> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro projects
	 */
	@Override
	public List<FaroProject> findAll(
		int start, int end, OrderByComparator<FaroProject> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro projects
	 */
	@Override
	public List<FaroProject> findAll(
		int start, int end, OrderByComparator<FaroProject> orderByComparator,
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

		List<FaroProject> list = null;

		if (useFinderCache) {
			list = (List<FaroProject>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FAROPROJECT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FAROPROJECT;

				sql = sql.concat(FaroProjectModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FaroProject>)QueryUtil.list(
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
	 * Removes all the faro projects from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FaroProject faroProject : findAll()) {
			remove(faroProject);
		}
	}

	/**
	 * Returns the number of faro projects.
	 *
	 * @return the number of faro projects
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FAROPROJECT);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "faroProjectId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FAROPROJECT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FaroProjectModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the faro project persistence.
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

		_finderPathFetchByGroupId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathFetchByCorpProjectUuid = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCorpProjectUuid",
			new String[] {String.class.getName()},
			new String[] {"corpProjectUuid"}, true);

		_finderPathCountByCorpProjectUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCorpProjectUuid",
			new String[] {String.class.getName()},
			new String[] {"corpProjectUuid"}, false);

		_finderPathWithPaginationFindByServerLocation = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByServerLocation",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"serverLocation"}, true);

		_finderPathWithoutPaginationFindByServerLocation = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByServerLocation",
			new String[] {String.class.getName()},
			new String[] {"serverLocation"}, true);

		_finderPathCountByServerLocation = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByServerLocation",
			new String[] {String.class.getName()},
			new String[] {"serverLocation"}, false);

		_finderPathFetchByWeDeployKey = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByWeDeployKey",
			new String[] {String.class.getName()}, new String[] {"weDeployKey"},
			true);

		_finderPathCountByWeDeployKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByWeDeployKey",
			new String[] {String.class.getName()}, new String[] {"weDeployKey"},
			false);

		FaroProjectUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FaroProjectUtil.setPersistence(null);

		entityCache.removeCache(FaroProjectImpl.class.getName());
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

	private static final String _SQL_SELECT_FAROPROJECT =
		"SELECT faroProject FROM FaroProject faroProject";

	private static final String _SQL_SELECT_FAROPROJECT_WHERE =
		"SELECT faroProject FROM FaroProject faroProject WHERE ";

	private static final String _SQL_COUNT_FAROPROJECT =
		"SELECT COUNT(faroProject) FROM FaroProject faroProject";

	private static final String _SQL_COUNT_FAROPROJECT_WHERE =
		"SELECT COUNT(faroProject) FROM FaroProject faroProject WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "faroProject.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FaroProject exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FaroProject exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FaroProjectPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"state"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}