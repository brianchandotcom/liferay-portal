/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.exception.NoSuchFaroPreferencesException;
import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.model.FaroPreferencesTable;
import com.liferay.osb.faro.model.impl.FaroPreferencesImpl;
import com.liferay.osb.faro.model.impl.FaroPreferencesModelImpl;
import com.liferay.osb.faro.service.persistence.FaroPreferencesPersistence;
import com.liferay.osb.faro.service.persistence.FaroPreferencesUtil;
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
 * The persistence implementation for the faro preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @generated
 */
@Component(service = FaroPreferencesPersistence.class)
public class FaroPreferencesPersistenceImpl
	extends BasePersistenceImpl<FaroPreferences>
	implements FaroPreferencesPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FaroPreferencesUtil</code> to access the faro preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FaroPreferencesImpl.class.getName();

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
	 * Returns all the faro preferenceses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching faro preferenceses
	 */
	@Override
	public List<FaroPreferences> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro preferenceses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @return the range of matching faro preferenceses
	 */
	@Override
	public List<FaroPreferences> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro preferenceses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro preferenceses
	 */
	@Override
	public List<FaroPreferences> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroPreferences> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro preferenceses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro preferenceses
	 */
	@Override
	public List<FaroPreferences> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroPreferences> orderByComparator,
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

		List<FaroPreferences> list = null;

		if (useFinderCache) {
			list = (List<FaroPreferences>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroPreferences faroPreferences : list) {
					if (groupId != faroPreferences.getGroupId()) {
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

			sb.append(_SQL_SELECT_FAROPREFERENCES_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroPreferencesModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<FaroPreferences>)QueryUtil.list(
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
	 * Returns the first faro preferences in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro preferences
	 * @throws NoSuchFaroPreferencesException if a matching faro preferences could not be found
	 */
	@Override
	public FaroPreferences findByGroupId_First(
			long groupId, OrderByComparator<FaroPreferences> orderByComparator)
		throws NoSuchFaroPreferencesException {

		FaroPreferences faroPreferences = fetchByGroupId_First(
			groupId, orderByComparator);

		if (faroPreferences != null) {
			return faroPreferences;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFaroPreferencesException(sb.toString());
	}

	/**
	 * Returns the first faro preferences in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro preferences, or <code>null</code> if a matching faro preferences could not be found
	 */
	@Override
	public FaroPreferences fetchByGroupId_First(
		long groupId, OrderByComparator<FaroPreferences> orderByComparator) {

		List<FaroPreferences> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro preferences in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro preferences
	 * @throws NoSuchFaroPreferencesException if a matching faro preferences could not be found
	 */
	@Override
	public FaroPreferences findByGroupId_Last(
			long groupId, OrderByComparator<FaroPreferences> orderByComparator)
		throws NoSuchFaroPreferencesException {

		FaroPreferences faroPreferences = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (faroPreferences != null) {
			return faroPreferences;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFaroPreferencesException(sb.toString());
	}

	/**
	 * Returns the last faro preferences in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro preferences, or <code>null</code> if a matching faro preferences could not be found
	 */
	@Override
	public FaroPreferences fetchByGroupId_Last(
		long groupId, OrderByComparator<FaroPreferences> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FaroPreferences> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro preferenceses before and after the current faro preferences in the ordered set where groupId = &#63;.
	 *
	 * @param faroPreferencesId the primary key of the current faro preferences
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro preferences
	 * @throws NoSuchFaroPreferencesException if a faro preferences with the primary key could not be found
	 */
	@Override
	public FaroPreferences[] findByGroupId_PrevAndNext(
			long faroPreferencesId, long groupId,
			OrderByComparator<FaroPreferences> orderByComparator)
		throws NoSuchFaroPreferencesException {

		FaroPreferences faroPreferences = findByPrimaryKey(faroPreferencesId);

		Session session = null;

		try {
			session = openSession();

			FaroPreferences[] array = new FaroPreferencesImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, faroPreferences, groupId, orderByComparator, true);

			array[1] = faroPreferences;

			array[2] = getByGroupId_PrevAndNext(
				session, faroPreferences, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FaroPreferences getByGroupId_PrevAndNext(
		Session session, FaroPreferences faroPreferences, long groupId,
		OrderByComparator<FaroPreferences> orderByComparator,
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

		sb.append(_SQL_SELECT_FAROPREFERENCES_WHERE);

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
			sb.append(FaroPreferencesModelImpl.ORDER_BY_JPQL);
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
						faroPreferences)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroPreferences> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro preferenceses where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FaroPreferences faroPreferences :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(faroPreferences);
		}
	}

	/**
	 * Returns the number of faro preferenceses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro preferenceses
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROPREFERENCES_WHERE);

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
		"faroPreferences.groupId = ?";

	private FinderPath _finderPathFetchByG_O;
	private FinderPath _finderPathCountByG_O;

	/**
	 * Returns the faro preferences where groupId = &#63; and ownerId = &#63; or throws a <code>NoSuchFaroPreferencesException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param ownerId the owner ID
	 * @return the matching faro preferences
	 * @throws NoSuchFaroPreferencesException if a matching faro preferences could not be found
	 */
	@Override
	public FaroPreferences findByG_O(long groupId, long ownerId)
		throws NoSuchFaroPreferencesException {

		FaroPreferences faroPreferences = fetchByG_O(groupId, ownerId);

		if (faroPreferences == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", ownerId=");
			sb.append(ownerId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFaroPreferencesException(sb.toString());
		}

		return faroPreferences;
	}

	/**
	 * Returns the faro preferences where groupId = &#63; and ownerId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param ownerId the owner ID
	 * @return the matching faro preferences, or <code>null</code> if a matching faro preferences could not be found
	 */
	@Override
	public FaroPreferences fetchByG_O(long groupId, long ownerId) {
		return fetchByG_O(groupId, ownerId, true);
	}

	/**
	 * Returns the faro preferences where groupId = &#63; and ownerId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param ownerId the owner ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro preferences, or <code>null</code> if a matching faro preferences could not be found
	 */
	@Override
	public FaroPreferences fetchByG_O(
		long groupId, long ownerId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, ownerId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_O, finderArgs, this);
		}

		if (result instanceof FaroPreferences) {
			FaroPreferences faroPreferences = (FaroPreferences)result;

			if ((groupId != faroPreferences.getGroupId()) ||
				(ownerId != faroPreferences.getOwnerId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_FAROPREFERENCES_WHERE);

			sb.append(_FINDER_COLUMN_G_O_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_O_OWNERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(ownerId);

				List<FaroPreferences> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_O, finderArgs, list);
					}
				}
				else {
					FaroPreferences faroPreferences = list.get(0);

					result = faroPreferences;

					cacheResult(faroPreferences);
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
			return (FaroPreferences)result;
		}
	}

	/**
	 * Removes the faro preferences where groupId = &#63; and ownerId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param ownerId the owner ID
	 * @return the faro preferences that was removed
	 */
	@Override
	public FaroPreferences removeByG_O(long groupId, long ownerId)
		throws NoSuchFaroPreferencesException {

		FaroPreferences faroPreferences = findByG_O(groupId, ownerId);

		return remove(faroPreferences);
	}

	/**
	 * Returns the number of faro preferenceses where groupId = &#63; and ownerId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param ownerId the owner ID
	 * @return the number of matching faro preferenceses
	 */
	@Override
	public int countByG_O(long groupId, long ownerId) {
		FinderPath finderPath = _finderPathCountByG_O;

		Object[] finderArgs = new Object[] {groupId, ownerId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FAROPREFERENCES_WHERE);

			sb.append(_FINDER_COLUMN_G_O_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_O_OWNERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(ownerId);

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

	private static final String _FINDER_COLUMN_G_O_GROUPID_2 =
		"faroPreferences.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_O_OWNERID_2 =
		"faroPreferences.ownerId = ?";

	public FaroPreferencesPersistenceImpl() {
		setModelClass(FaroPreferences.class);

		setModelImplClass(FaroPreferencesImpl.class);
		setModelPKClass(long.class);

		setTable(FaroPreferencesTable.INSTANCE);
	}

	/**
	 * Caches the faro preferences in the entity cache if it is enabled.
	 *
	 * @param faroPreferences the faro preferences
	 */
	@Override
	public void cacheResult(FaroPreferences faroPreferences) {
		entityCache.putResult(
			FaroPreferencesImpl.class, faroPreferences.getPrimaryKey(),
			faroPreferences);

		finderCache.putResult(
			_finderPathFetchByG_O,
			new Object[] {
				faroPreferences.getGroupId(), faroPreferences.getOwnerId()
			},
			faroPreferences);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the faro preferenceses in the entity cache if it is enabled.
	 *
	 * @param faroPreferenceses the faro preferenceses
	 */
	@Override
	public void cacheResult(List<FaroPreferences> faroPreferenceses) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (faroPreferenceses.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FaroPreferences faroPreferences : faroPreferenceses) {
			if (entityCache.getResult(
					FaroPreferencesImpl.class,
					faroPreferences.getPrimaryKey()) == null) {

				cacheResult(faroPreferences);
			}
		}
	}

	/**
	 * Clears the cache for all faro preferenceses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FaroPreferencesImpl.class);

		finderCache.clearCache(FaroPreferencesImpl.class);
	}

	/**
	 * Clears the cache for the faro preferences.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FaroPreferences faroPreferences) {
		entityCache.removeResult(FaroPreferencesImpl.class, faroPreferences);
	}

	@Override
	public void clearCache(List<FaroPreferences> faroPreferenceses) {
		for (FaroPreferences faroPreferences : faroPreferenceses) {
			entityCache.removeResult(
				FaroPreferencesImpl.class, faroPreferences);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FaroPreferencesImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FaroPreferencesImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FaroPreferencesModelImpl faroPreferencesModelImpl) {

		Object[] args = new Object[] {
			faroPreferencesModelImpl.getGroupId(),
			faroPreferencesModelImpl.getOwnerId()
		};

		finderCache.putResult(_finderPathCountByG_O, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_O, args, faroPreferencesModelImpl);
	}

	/**
	 * Creates a new faro preferences with the primary key. Does not add the faro preferences to the database.
	 *
	 * @param faroPreferencesId the primary key for the new faro preferences
	 * @return the new faro preferences
	 */
	@Override
	public FaroPreferences create(long faroPreferencesId) {
		FaroPreferences faroPreferences = new FaroPreferencesImpl();

		faroPreferences.setNew(true);
		faroPreferences.setPrimaryKey(faroPreferencesId);

		faroPreferences.setCompanyId(CompanyThreadLocal.getCompanyId());

		return faroPreferences;
	}

	/**
	 * Removes the faro preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroPreferencesId the primary key of the faro preferences
	 * @return the faro preferences that was removed
	 * @throws NoSuchFaroPreferencesException if a faro preferences with the primary key could not be found
	 */
	@Override
	public FaroPreferences remove(long faroPreferencesId)
		throws NoSuchFaroPreferencesException {

		return remove((Serializable)faroPreferencesId);
	}

	/**
	 * Removes the faro preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the faro preferences
	 * @return the faro preferences that was removed
	 * @throws NoSuchFaroPreferencesException if a faro preferences with the primary key could not be found
	 */
	@Override
	public FaroPreferences remove(Serializable primaryKey)
		throws NoSuchFaroPreferencesException {

		Session session = null;

		try {
			session = openSession();

			FaroPreferences faroPreferences = (FaroPreferences)session.get(
				FaroPreferencesImpl.class, primaryKey);

			if (faroPreferences == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFaroPreferencesException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(faroPreferences);
		}
		catch (NoSuchFaroPreferencesException noSuchEntityException) {
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
	protected FaroPreferences removeImpl(FaroPreferences faroPreferences) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(faroPreferences)) {
				faroPreferences = (FaroPreferences)session.get(
					FaroPreferencesImpl.class,
					faroPreferences.getPrimaryKeyObj());
			}

			if (faroPreferences != null) {
				session.delete(faroPreferences);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (faroPreferences != null) {
			clearCache(faroPreferences);
		}

		return faroPreferences;
	}

	@Override
	public FaroPreferences updateImpl(FaroPreferences faroPreferences) {
		boolean isNew = faroPreferences.isNew();

		if (!(faroPreferences instanceof FaroPreferencesModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(faroPreferences.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					faroPreferences);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in faroPreferences proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FaroPreferences implementation " +
					faroPreferences.getClass());
		}

		FaroPreferencesModelImpl faroPreferencesModelImpl =
			(FaroPreferencesModelImpl)faroPreferences;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(faroPreferences);
			}
			else {
				faroPreferences = (FaroPreferences)session.merge(
					faroPreferences);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FaroPreferencesImpl.class, faroPreferencesModelImpl, false, true);

		cacheUniqueFindersCache(faroPreferencesModelImpl);

		if (isNew) {
			faroPreferences.setNew(false);
		}

		faroPreferences.resetOriginalValues();

		return faroPreferences;
	}

	/**
	 * Returns the faro preferences with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the faro preferences
	 * @return the faro preferences
	 * @throws NoSuchFaroPreferencesException if a faro preferences with the primary key could not be found
	 */
	@Override
	public FaroPreferences findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFaroPreferencesException {

		FaroPreferences faroPreferences = fetchByPrimaryKey(primaryKey);

		if (faroPreferences == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFaroPreferencesException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return faroPreferences;
	}

	/**
	 * Returns the faro preferences with the primary key or throws a <code>NoSuchFaroPreferencesException</code> if it could not be found.
	 *
	 * @param faroPreferencesId the primary key of the faro preferences
	 * @return the faro preferences
	 * @throws NoSuchFaroPreferencesException if a faro preferences with the primary key could not be found
	 */
	@Override
	public FaroPreferences findByPrimaryKey(long faroPreferencesId)
		throws NoSuchFaroPreferencesException {

		return findByPrimaryKey((Serializable)faroPreferencesId);
	}

	/**
	 * Returns the faro preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroPreferencesId the primary key of the faro preferences
	 * @return the faro preferences, or <code>null</code> if a faro preferences with the primary key could not be found
	 */
	@Override
	public FaroPreferences fetchByPrimaryKey(long faroPreferencesId) {
		return fetchByPrimaryKey((Serializable)faroPreferencesId);
	}

	/**
	 * Returns all the faro preferenceses.
	 *
	 * @return the faro preferenceses
	 */
	@Override
	public List<FaroPreferences> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @return the range of faro preferenceses
	 */
	@Override
	public List<FaroPreferences> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro preferenceses
	 */
	@Override
	public List<FaroPreferences> findAll(
		int start, int end,
		OrderByComparator<FaroPreferences> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro preferenceses
	 */
	@Override
	public List<FaroPreferences> findAll(
		int start, int end,
		OrderByComparator<FaroPreferences> orderByComparator,
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

		List<FaroPreferences> list = null;

		if (useFinderCache) {
			list = (List<FaroPreferences>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FAROPREFERENCES);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FAROPREFERENCES;

				sql = sql.concat(FaroPreferencesModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FaroPreferences>)QueryUtil.list(
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
	 * Removes all the faro preferenceses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FaroPreferences faroPreferences : findAll()) {
			remove(faroPreferences);
		}
	}

	/**
	 * Returns the number of faro preferenceses.
	 *
	 * @return the number of faro preferenceses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FAROPREFERENCES);

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
		return "faroPreferencesId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FAROPREFERENCES;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FaroPreferencesModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the faro preferences persistence.
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

		_finderPathFetchByG_O = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_O",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "ownerId"}, true);

		_finderPathCountByG_O = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_O",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "ownerId"}, false);

		FaroPreferencesUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FaroPreferencesUtil.setPersistence(null);

		entityCache.removeCache(FaroPreferencesImpl.class.getName());
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

	private static final String _SQL_SELECT_FAROPREFERENCES =
		"SELECT faroPreferences FROM FaroPreferences faroPreferences";

	private static final String _SQL_SELECT_FAROPREFERENCES_WHERE =
		"SELECT faroPreferences FROM FaroPreferences faroPreferences WHERE ";

	private static final String _SQL_COUNT_FAROPREFERENCES =
		"SELECT COUNT(faroPreferences) FROM FaroPreferences faroPreferences";

	private static final String _SQL_COUNT_FAROPREFERENCES_WHERE =
		"SELECT COUNT(faroPreferences) FROM FaroPreferences faroPreferences WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "faroPreferences.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FaroPreferences exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FaroPreferences exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FaroPreferencesPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}