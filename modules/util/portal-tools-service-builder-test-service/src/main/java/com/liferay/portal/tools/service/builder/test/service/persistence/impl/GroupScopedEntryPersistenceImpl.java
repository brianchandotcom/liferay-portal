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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchGroupScopedEntryException;
import com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry;
import com.liferay.portal.tools.service.builder.test.model.GroupScopedEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.GroupScopedEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.GroupScopedEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.GroupScopedEntryPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the group scoped entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class GroupScopedEntryPersistenceImpl
	extends BasePersistenceImpl<GroupScopedEntry>
	implements GroupScopedEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>GroupScopedEntryUtil</code> to access the group scoped entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		GroupScopedEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByG_ERC;
	private FinderPath _finderPathCountByG_ERC;

	/**
	 * Returns the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchGroupScopedEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching group scoped entry
	 * @throws NoSuchGroupScopedEntryException if a matching group scoped entry could not be found
	 */
	@Override
	public GroupScopedEntry findByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchGroupScopedEntryException {

		GroupScopedEntry groupScopedEntry = fetchByG_ERC(
			groupId, externalReferenceCode);

		if (groupScopedEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchGroupScopedEntryException(sb.toString());
		}

		return groupScopedEntry;
	}

	/**
	 * Returns the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching group scoped entry, or <code>null</code> if a matching group scoped entry could not be found
	 */
	@Override
	public GroupScopedEntry fetchByG_ERC(
		long groupId, String externalReferenceCode) {

		return fetchByG_ERC(groupId, externalReferenceCode, true);
	}

	/**
	 * Returns the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching group scoped entry, or <code>null</code> if a matching group scoped entry could not be found
	 */
	@Override
	public GroupScopedEntry fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByG_ERC, finderArgs);
		}

		if (result instanceof GroupScopedEntry) {
			GroupScopedEntry groupScopedEntry = (GroupScopedEntry)result;

			if ((groupId != groupScopedEntry.getGroupId()) ||
				!Objects.equals(
					externalReferenceCode,
					groupScopedEntry.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_GROUPSCOPEDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_ERC_GROUPID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<GroupScopedEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									groupId, externalReferenceCode
								};
							}

							_log.warn(
								"GroupScopedEntryPersistenceImpl.fetchByG_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					GroupScopedEntry groupScopedEntry = list.get(0);

					result = groupScopedEntry;

					cacheResult(groupScopedEntry);
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
			return (GroupScopedEntry)result;
		}
	}

	/**
	 * Removes the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the group scoped entry that was removed
	 */
	@Override
	public GroupScopedEntry removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchGroupScopedEntryException {

		GroupScopedEntry groupScopedEntry = findByG_ERC(
			groupId, externalReferenceCode);

		return remove(groupScopedEntry);
	}

	/**
	 * Returns the number of group scoped entries where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching group scoped entries
	 */
	@Override
	public int countByG_ERC(long groupId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByG_ERC;

		Object[] finderArgs = new Object[] {groupId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_GROUPSCOPEDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_ERC_GROUPID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_ERC_GROUPID_2 =
		"groupScopedEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_2 =
		"groupScopedEntry.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_3 =
		"(groupScopedEntry.externalReferenceCode IS NULL OR groupScopedEntry.externalReferenceCode = '')";

	public GroupScopedEntryPersistenceImpl() {
		setModelClass(GroupScopedEntry.class);

		setModelImplClass(GroupScopedEntryImpl.class);
		setModelPKClass(long.class);

		setTable(GroupScopedEntryTable.INSTANCE);
	}

	/**
	 * Caches the group scoped entry in the entity cache if it is enabled.
	 *
	 * @param groupScopedEntry the group scoped entry
	 */
	@Override
	public void cacheResult(GroupScopedEntry groupScopedEntry) {
		entityCache.putResult(
			GroupScopedEntryImpl.class, groupScopedEntry.getPrimaryKey(),
			groupScopedEntry);

		finderCache.putResult(
			_finderPathFetchByG_ERC,
			new Object[] {
				groupScopedEntry.getGroupId(),
				groupScopedEntry.getExternalReferenceCode()
			},
			groupScopedEntry);
	}

	/**
	 * Caches the group scoped entries in the entity cache if it is enabled.
	 *
	 * @param groupScopedEntries the group scoped entries
	 */
	@Override
	public void cacheResult(List<GroupScopedEntry> groupScopedEntries) {
		for (GroupScopedEntry groupScopedEntry : groupScopedEntries) {
			if (entityCache.getResult(
					GroupScopedEntryImpl.class,
					groupScopedEntry.getPrimaryKey()) == null) {

				cacheResult(groupScopedEntry);
			}
		}
	}

	/**
	 * Clears the cache for all group scoped entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(GroupScopedEntryImpl.class);

		finderCache.clearCache(GroupScopedEntryImpl.class);
	}

	/**
	 * Clears the cache for the group scoped entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(GroupScopedEntry groupScopedEntry) {
		entityCache.removeResult(GroupScopedEntryImpl.class, groupScopedEntry);
	}

	@Override
	public void clearCache(List<GroupScopedEntry> groupScopedEntries) {
		for (GroupScopedEntry groupScopedEntry : groupScopedEntries) {
			entityCache.removeResult(
				GroupScopedEntryImpl.class, groupScopedEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(GroupScopedEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(GroupScopedEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		GroupScopedEntryModelImpl groupScopedEntryModelImpl) {

		Object[] args = new Object[] {
			groupScopedEntryModelImpl.getGroupId(),
			groupScopedEntryModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByG_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_ERC, args, groupScopedEntryModelImpl);
	}

	/**
	 * Creates a new group scoped entry with the primary key. Does not add the group scoped entry to the database.
	 *
	 * @param GroupScopedEntryId the primary key for the new group scoped entry
	 * @return the new group scoped entry
	 */
	@Override
	public GroupScopedEntry create(long GroupScopedEntryId) {
		GroupScopedEntry groupScopedEntry = new GroupScopedEntryImpl();

		groupScopedEntry.setNew(true);
		groupScopedEntry.setPrimaryKey(GroupScopedEntryId);

		groupScopedEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return groupScopedEntry;
	}

	/**
	 * Removes the group scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry that was removed
	 * @throws NoSuchGroupScopedEntryException if a group scoped entry with the primary key could not be found
	 */
	@Override
	public GroupScopedEntry remove(long GroupScopedEntryId)
		throws NoSuchGroupScopedEntryException {

		return remove((Serializable)GroupScopedEntryId);
	}

	/**
	 * Removes the group scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the group scoped entry
	 * @return the group scoped entry that was removed
	 * @throws NoSuchGroupScopedEntryException if a group scoped entry with the primary key could not be found
	 */
	@Override
	public GroupScopedEntry remove(Serializable primaryKey)
		throws NoSuchGroupScopedEntryException {

		Session session = null;

		try {
			session = openSession();

			GroupScopedEntry groupScopedEntry = (GroupScopedEntry)session.get(
				GroupScopedEntryImpl.class, primaryKey);

			if (groupScopedEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchGroupScopedEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(groupScopedEntry);
		}
		catch (NoSuchGroupScopedEntryException noSuchEntityException) {
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
	protected GroupScopedEntry removeImpl(GroupScopedEntry groupScopedEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(groupScopedEntry)) {
				groupScopedEntry = (GroupScopedEntry)session.get(
					GroupScopedEntryImpl.class,
					groupScopedEntry.getPrimaryKeyObj());
			}

			if (groupScopedEntry != null) {
				session.delete(groupScopedEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (groupScopedEntry != null) {
			clearCache(groupScopedEntry);
		}

		return groupScopedEntry;
	}

	@Override
	public GroupScopedEntry updateImpl(GroupScopedEntry groupScopedEntry) {
		boolean isNew = groupScopedEntry.isNew();

		if (!(groupScopedEntry instanceof GroupScopedEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(groupScopedEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					groupScopedEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in groupScopedEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom GroupScopedEntry implementation " +
					groupScopedEntry.getClass());
		}

		GroupScopedEntryModelImpl groupScopedEntryModelImpl =
			(GroupScopedEntryModelImpl)groupScopedEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(groupScopedEntry);
			}
			else {
				groupScopedEntry = (GroupScopedEntry)session.merge(
					groupScopedEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			GroupScopedEntryImpl.class, groupScopedEntryModelImpl, false, true);

		cacheUniqueFindersCache(groupScopedEntryModelImpl);

		if (isNew) {
			groupScopedEntry.setNew(false);
		}

		groupScopedEntry.resetOriginalValues();

		return groupScopedEntry;
	}

	/**
	 * Returns the group scoped entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the group scoped entry
	 * @return the group scoped entry
	 * @throws NoSuchGroupScopedEntryException if a group scoped entry with the primary key could not be found
	 */
	@Override
	public GroupScopedEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchGroupScopedEntryException {

		GroupScopedEntry groupScopedEntry = fetchByPrimaryKey(primaryKey);

		if (groupScopedEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchGroupScopedEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return groupScopedEntry;
	}

	/**
	 * Returns the group scoped entry with the primary key or throws a <code>NoSuchGroupScopedEntryException</code> if it could not be found.
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry
	 * @throws NoSuchGroupScopedEntryException if a group scoped entry with the primary key could not be found
	 */
	@Override
	public GroupScopedEntry findByPrimaryKey(long GroupScopedEntryId)
		throws NoSuchGroupScopedEntryException {

		return findByPrimaryKey((Serializable)GroupScopedEntryId);
	}

	/**
	 * Returns the group scoped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry, or <code>null</code> if a group scoped entry with the primary key could not be found
	 */
	@Override
	public GroupScopedEntry fetchByPrimaryKey(long GroupScopedEntryId) {
		return fetchByPrimaryKey((Serializable)GroupScopedEntryId);
	}

	/**
	 * Returns all the group scoped entries.
	 *
	 * @return the group scoped entries
	 */
	@Override
	public List<GroupScopedEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the group scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of group scoped entries
	 * @param end the upper bound of the range of group scoped entries (not inclusive)
	 * @return the range of group scoped entries
	 */
	@Override
	public List<GroupScopedEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the group scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of group scoped entries
	 * @param end the upper bound of the range of group scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of group scoped entries
	 */
	@Override
	public List<GroupScopedEntry> findAll(
		int start, int end,
		OrderByComparator<GroupScopedEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the group scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of group scoped entries
	 * @param end the upper bound of the range of group scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of group scoped entries
	 */
	@Override
	public List<GroupScopedEntry> findAll(
		int start, int end,
		OrderByComparator<GroupScopedEntry> orderByComparator,
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

		List<GroupScopedEntry> list = null;

		if (useFinderCache) {
			list = (List<GroupScopedEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_GROUPSCOPEDENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_GROUPSCOPEDENTRY;

				sql = sql.concat(GroupScopedEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<GroupScopedEntry>)QueryUtil.list(
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
	 * Removes all the group scoped entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (GroupScopedEntry groupScopedEntry : findAll()) {
			remove(groupScopedEntry);
		}
	}

	/**
	 * Returns the number of group scoped entries.
	 *
	 * @return the number of group scoped entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_GROUPSCOPEDENTRY);

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
		return "GroupScopedEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_GROUPSCOPEDENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return GroupScopedEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the group scoped entry persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			GroupScopedEntryPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new GroupScopedEntryModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByG_ERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "externalReferenceCode"}, true);

		_finderPathCountByG_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "externalReferenceCode"}, false);
	}

	public void destroy() {
		entityCache.removeCache(GroupScopedEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_GROUPSCOPEDENTRY =
		"SELECT groupScopedEntry FROM GroupScopedEntry groupScopedEntry";

	private static final String _SQL_SELECT_GROUPSCOPEDENTRY_WHERE =
		"SELECT groupScopedEntry FROM GroupScopedEntry groupScopedEntry WHERE ";

	private static final String _SQL_COUNT_GROUPSCOPEDENTRY =
		"SELECT COUNT(groupScopedEntry) FROM GroupScopedEntry groupScopedEntry";

	private static final String _SQL_COUNT_GROUPSCOPEDENTRY_WHERE =
		"SELECT COUNT(groupScopedEntry) FROM GroupScopedEntry groupScopedEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "groupScopedEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No GroupScopedEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No GroupScopedEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		GroupScopedEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class GroupScopedEntryModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			GroupScopedEntryModelImpl groupScopedEntryModelImpl =
				(GroupScopedEntryModelImpl)baseModel;

			long columnBitmask = groupScopedEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					groupScopedEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						groupScopedEntryModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					groupScopedEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return GroupScopedEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return GroupScopedEntryTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			GroupScopedEntryModelImpl groupScopedEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						groupScopedEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = groupScopedEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}