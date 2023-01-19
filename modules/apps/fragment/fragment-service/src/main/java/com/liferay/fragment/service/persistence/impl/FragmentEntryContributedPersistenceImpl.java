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

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.exception.NoSuchEntryContributedException;
import com.liferay.fragment.model.FragmentEntryContributed;
import com.liferay.fragment.model.FragmentEntryContributedTable;
import com.liferay.fragment.model.impl.FragmentEntryContributedImpl;
import com.liferay.fragment.model.impl.FragmentEntryContributedModelImpl;
import com.liferay.fragment.service.persistence.FragmentEntryContributedPersistence;
import com.liferay.fragment.service.persistence.FragmentEntryContributedUtil;
import com.liferay.fragment.service.persistence.impl.constants.FragmentPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * The persistence implementation for the fragment entry contributed service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = FragmentEntryContributedPersistence.class)
public class FragmentEntryContributedPersistenceImpl
	extends BasePersistenceImpl<FragmentEntryContributed>
	implements FragmentEntryContributedPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FragmentEntryContributedUtil</code> to access the fragment entry contributed persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FragmentEntryContributedImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByFragmentEntryKey;
	private FinderPath _finderPathCountByFragmentEntryKey;

	/**
	 * Returns the fragment entry contributed where fragmentEntryKey = &#63; or throws a <code>NoSuchEntryContributedException</code> if it could not be found.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entry contributed
	 * @throws NoSuchEntryContributedException if a matching fragment entry contributed could not be found
	 */
	@Override
	public FragmentEntryContributed findByFragmentEntryKey(
			String fragmentEntryKey)
		throws NoSuchEntryContributedException {

		FragmentEntryContributed fragmentEntryContributed =
			fetchByFragmentEntryKey(fragmentEntryKey);

		if (fragmentEntryContributed == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("fragmentEntryKey=");
			sb.append(fragmentEntryKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryContributedException(sb.toString());
		}

		return fragmentEntryContributed;
	}

	/**
	 * Returns the fragment entry contributed where fragmentEntryKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entry contributed, or <code>null</code> if a matching fragment entry contributed could not be found
	 */
	@Override
	public FragmentEntryContributed fetchByFragmentEntryKey(
		String fragmentEntryKey) {

		return fetchByFragmentEntryKey(fragmentEntryKey, true);
	}

	/**
	 * Returns the fragment entry contributed where fragmentEntryKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry contributed, or <code>null</code> if a matching fragment entry contributed could not be found
	 */
	@Override
	public FragmentEntryContributed fetchByFragmentEntryKey(
		String fragmentEntryKey, boolean useFinderCache) {

		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryContributed.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {fragmentEntryKey};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByFragmentEntryKey, finderArgs, this);
		}

		if (result instanceof FragmentEntryContributed) {
			FragmentEntryContributed fragmentEntryContributed =
				(FragmentEntryContributed)result;

			if (!Objects.equals(
					fragmentEntryKey,
					fragmentEntryContributed.getFragmentEntryKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_FRAGMENTENTRYCONTRIBUTED_WHERE);

			boolean bindFragmentEntryKey = false;

			if (fragmentEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_FRAGMENTENTRYKEY_FRAGMENTENTRYKEY_3);
			}
			else {
				bindFragmentEntryKey = true;

				sb.append(_FINDER_COLUMN_FRAGMENTENTRYKEY_FRAGMENTENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindFragmentEntryKey) {
					queryPos.add(fragmentEntryKey);
				}

				List<FragmentEntryContributed> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByFragmentEntryKey, finderArgs,
							list);
					}
				}
				else {
					FragmentEntryContributed fragmentEntryContributed =
						list.get(0);

					result = fragmentEntryContributed;

					cacheResult(fragmentEntryContributed);
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
			return (FragmentEntryContributed)result;
		}
	}

	/**
	 * Removes the fragment entry contributed where fragmentEntryKey = &#63; from the database.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the fragment entry contributed that was removed
	 */
	@Override
	public FragmentEntryContributed removeByFragmentEntryKey(
			String fragmentEntryKey)
		throws NoSuchEntryContributedException {

		FragmentEntryContributed fragmentEntryContributed =
			findByFragmentEntryKey(fragmentEntryKey);

		return remove(fragmentEntryContributed);
	}

	/**
	 * Returns the number of fragment entry contributeds where fragmentEntryKey = &#63;.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the number of matching fragment entry contributeds
	 */
	@Override
	public int countByFragmentEntryKey(String fragmentEntryKey) {
		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryContributed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFragmentEntryKey;

			finderArgs = new Object[] {fragmentEntryKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRYCONTRIBUTED_WHERE);

			boolean bindFragmentEntryKey = false;

			if (fragmentEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_FRAGMENTENTRYKEY_FRAGMENTENTRYKEY_3);
			}
			else {
				bindFragmentEntryKey = true;

				sb.append(_FINDER_COLUMN_FRAGMENTENTRYKEY_FRAGMENTENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindFragmentEntryKey) {
					queryPos.add(fragmentEntryKey);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		_FINDER_COLUMN_FRAGMENTENTRYKEY_FRAGMENTENTRYKEY_2 =
			"fragmentEntryContributed.fragmentEntryKey = ?";

	private static final String
		_FINDER_COLUMN_FRAGMENTENTRYKEY_FRAGMENTENTRYKEY_3 =
			"(fragmentEntryContributed.fragmentEntryKey IS NULL OR fragmentEntryContributed.fragmentEntryKey = '')";

	public FragmentEntryContributedPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FragmentEntryContributed.class);

		setModelImplClass(FragmentEntryContributedImpl.class);
		setModelPKClass(long.class);

		setTable(FragmentEntryContributedTable.INSTANCE);
	}

	/**
	 * Caches the fragment entry contributed in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryContributed the fragment entry contributed
	 */
	@Override
	public void cacheResult(FragmentEntryContributed fragmentEntryContributed) {
		if (fragmentEntryContributed.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			FragmentEntryContributedImpl.class,
			fragmentEntryContributed.getPrimaryKey(), fragmentEntryContributed);

		finderCache.putResult(
			_finderPathFetchByFragmentEntryKey,
			new Object[] {fragmentEntryContributed.getFragmentEntryKey()},
			fragmentEntryContributed);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the fragment entry contributeds in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryContributeds the fragment entry contributeds
	 */
	@Override
	public void cacheResult(
		List<FragmentEntryContributed> fragmentEntryContributeds) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (fragmentEntryContributeds.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FragmentEntryContributed fragmentEntryContributed :
				fragmentEntryContributeds) {

			if (fragmentEntryContributed.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					FragmentEntryContributedImpl.class,
					fragmentEntryContributed.getPrimaryKey()) == null) {

				cacheResult(fragmentEntryContributed);
			}
		}
	}

	/**
	 * Clears the cache for all fragment entry contributeds.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FragmentEntryContributedImpl.class);

		finderCache.clearCache(FragmentEntryContributedImpl.class);
	}

	/**
	 * Clears the cache for the fragment entry contributed.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FragmentEntryContributed fragmentEntryContributed) {
		entityCache.removeResult(
			FragmentEntryContributedImpl.class, fragmentEntryContributed);
	}

	@Override
	public void clearCache(
		List<FragmentEntryContributed> fragmentEntryContributeds) {

		for (FragmentEntryContributed fragmentEntryContributed :
				fragmentEntryContributeds) {

			entityCache.removeResult(
				FragmentEntryContributedImpl.class, fragmentEntryContributed);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FragmentEntryContributedImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				FragmentEntryContributedImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FragmentEntryContributedModelImpl fragmentEntryContributedModelImpl) {

		Object[] args = new Object[] {
			fragmentEntryContributedModelImpl.getFragmentEntryKey()
		};

		finderCache.putResult(
			_finderPathCountByFragmentEntryKey, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByFragmentEntryKey, args,
			fragmentEntryContributedModelImpl);
	}

	/**
	 * Creates a new fragment entry contributed with the primary key. Does not add the fragment entry contributed to the database.
	 *
	 * @param fragmentEntryContributedId the primary key for the new fragment entry contributed
	 * @return the new fragment entry contributed
	 */
	@Override
	public FragmentEntryContributed create(long fragmentEntryContributedId) {
		FragmentEntryContributed fragmentEntryContributed =
			new FragmentEntryContributedImpl();

		fragmentEntryContributed.setNew(true);
		fragmentEntryContributed.setPrimaryKey(fragmentEntryContributedId);

		return fragmentEntryContributed;
	}

	/**
	 * Removes the fragment entry contributed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed that was removed
	 * @throws NoSuchEntryContributedException if a fragment entry contributed with the primary key could not be found
	 */
	@Override
	public FragmentEntryContributed remove(long fragmentEntryContributedId)
		throws NoSuchEntryContributedException {

		return remove((Serializable)fragmentEntryContributedId);
	}

	/**
	 * Removes the fragment entry contributed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fragment entry contributed
	 * @return the fragment entry contributed that was removed
	 * @throws NoSuchEntryContributedException if a fragment entry contributed with the primary key could not be found
	 */
	@Override
	public FragmentEntryContributed remove(Serializable primaryKey)
		throws NoSuchEntryContributedException {

		Session session = null;

		try {
			session = openSession();

			FragmentEntryContributed fragmentEntryContributed =
				(FragmentEntryContributed)session.get(
					FragmentEntryContributedImpl.class, primaryKey);

			if (fragmentEntryContributed == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryContributedException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(fragmentEntryContributed);
		}
		catch (NoSuchEntryContributedException noSuchEntityException) {
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
	protected FragmentEntryContributed removeImpl(
		FragmentEntryContributed fragmentEntryContributed) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fragmentEntryContributed)) {
				fragmentEntryContributed =
					(FragmentEntryContributed)session.get(
						FragmentEntryContributedImpl.class,
						fragmentEntryContributed.getPrimaryKeyObj());
			}

			if ((fragmentEntryContributed != null) &&
				ctPersistenceHelper.isRemove(fragmentEntryContributed)) {

				session.delete(fragmentEntryContributed);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntryContributed != null) {
			clearCache(fragmentEntryContributed);
		}

		return fragmentEntryContributed;
	}

	@Override
	public FragmentEntryContributed updateImpl(
		FragmentEntryContributed fragmentEntryContributed) {

		boolean isNew = fragmentEntryContributed.isNew();

		if (!(fragmentEntryContributed instanceof
				FragmentEntryContributedModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fragmentEntryContributed.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fragmentEntryContributed);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fragmentEntryContributed proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FragmentEntryContributed implementation " +
					fragmentEntryContributed.getClass());
		}

		FragmentEntryContributedModelImpl fragmentEntryContributedModelImpl =
			(FragmentEntryContributedModelImpl)fragmentEntryContributed;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (fragmentEntryContributed.getCreateDate() == null)) {
			if (serviceContext == null) {
				fragmentEntryContributed.setCreateDate(date);
			}
			else {
				fragmentEntryContributed.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!fragmentEntryContributedModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fragmentEntryContributed.setModifiedDate(date);
			}
			else {
				fragmentEntryContributed.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(fragmentEntryContributed)) {
				if (!isNew) {
					session.evict(
						FragmentEntryContributedImpl.class,
						fragmentEntryContributed.getPrimaryKeyObj());
				}

				session.save(fragmentEntryContributed);
			}
			else {
				fragmentEntryContributed =
					(FragmentEntryContributed)session.merge(
						fragmentEntryContributed);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntryContributed.getCtCollectionId() != 0) {
			if (isNew) {
				fragmentEntryContributed.setNew(false);
			}

			fragmentEntryContributed.resetOriginalValues();

			return fragmentEntryContributed;
		}

		entityCache.putResult(
			FragmentEntryContributedImpl.class,
			fragmentEntryContributedModelImpl, false, true);

		cacheUniqueFindersCache(fragmentEntryContributedModelImpl);

		if (isNew) {
			fragmentEntryContributed.setNew(false);
		}

		fragmentEntryContributed.resetOriginalValues();

		return fragmentEntryContributed;
	}

	/**
	 * Returns the fragment entry contributed with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment entry contributed
	 * @return the fragment entry contributed
	 * @throws NoSuchEntryContributedException if a fragment entry contributed with the primary key could not be found
	 */
	@Override
	public FragmentEntryContributed findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryContributedException {

		FragmentEntryContributed fragmentEntryContributed = fetchByPrimaryKey(
			primaryKey);

		if (fragmentEntryContributed == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryContributedException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return fragmentEntryContributed;
	}

	/**
	 * Returns the fragment entry contributed with the primary key or throws a <code>NoSuchEntryContributedException</code> if it could not be found.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed
	 * @throws NoSuchEntryContributedException if a fragment entry contributed with the primary key could not be found
	 */
	@Override
	public FragmentEntryContributed findByPrimaryKey(
			long fragmentEntryContributedId)
		throws NoSuchEntryContributedException {

		return findByPrimaryKey((Serializable)fragmentEntryContributedId);
	}

	/**
	 * Returns the fragment entry contributed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment entry contributed
	 * @return the fragment entry contributed, or <code>null</code> if a fragment entry contributed with the primary key could not be found
	 */
	@Override
	public FragmentEntryContributed fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				FragmentEntryContributed.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		FragmentEntryContributed fragmentEntryContributed = null;

		Session session = null;

		try {
			session = openSession();

			fragmentEntryContributed = (FragmentEntryContributed)session.get(
				FragmentEntryContributedImpl.class, primaryKey);

			if (fragmentEntryContributed != null) {
				cacheResult(fragmentEntryContributed);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return fragmentEntryContributed;
	}

	/**
	 * Returns the fragment entry contributed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed, or <code>null</code> if a fragment entry contributed with the primary key could not be found
	 */
	@Override
	public FragmentEntryContributed fetchByPrimaryKey(
		long fragmentEntryContributedId) {

		return fetchByPrimaryKey((Serializable)fragmentEntryContributedId);
	}

	@Override
	public Map<Serializable, FragmentEntryContributed> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				FragmentEntryContributed.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FragmentEntryContributed> map =
			new HashMap<Serializable, FragmentEntryContributed>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FragmentEntryContributed fragmentEntryContributed =
				fetchByPrimaryKey(primaryKey);

			if (fragmentEntryContributed != null) {
				map.put(primaryKey, fragmentEntryContributed);
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

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
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

			for (FragmentEntryContributed fragmentEntryContributed :
					(List<FragmentEntryContributed>)query.list()) {

				map.put(
					fragmentEntryContributed.getPrimaryKeyObj(),
					fragmentEntryContributed);

				cacheResult(fragmentEntryContributed);
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
	 * Returns all the fragment entry contributeds.
	 *
	 * @return the fragment entry contributeds
	 */
	@Override
	public List<FragmentEntryContributed> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry contributeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryContributedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry contributeds
	 * @param end the upper bound of the range of fragment entry contributeds (not inclusive)
	 * @return the range of fragment entry contributeds
	 */
	@Override
	public List<FragmentEntryContributed> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry contributeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryContributedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry contributeds
	 * @param end the upper bound of the range of fragment entry contributeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entry contributeds
	 */
	@Override
	public List<FragmentEntryContributed> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryContributed> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry contributeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryContributedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry contributeds
	 * @param end the upper bound of the range of fragment entry contributeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment entry contributeds
	 */
	@Override
	public List<FragmentEntryContributed> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryContributed> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryContributed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<FragmentEntryContributed> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryContributed>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FRAGMENTENTRYCONTRIBUTED);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FRAGMENTENTRYCONTRIBUTED;

				sql = sql.concat(
					FragmentEntryContributedModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FragmentEntryContributed>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Removes all the fragment entry contributeds from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FragmentEntryContributed fragmentEntryContributed : findAll()) {
			remove(fragmentEntryContributed);
		}
	}

	/**
	 * Returns the number of fragment entry contributeds.
	 *
	 * @return the number of fragment entry contributeds
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryContributed.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_FRAGMENTENTRYCONTRIBUTED);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return "fragmentEntryContributedId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FRAGMENTENTRYCONTRIBUTED;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return FragmentEntryContributedModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "FragmentEntryContributed";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("fragmentEntryKey");
		ctStrictColumnNames.add("css");
		ctStrictColumnNames.add("html");
		ctStrictColumnNames.add("js");
		ctStrictColumnNames.add("configuration");
		ctStrictColumnNames.add("type_");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("fragmentEntryContributedId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"fragmentEntryKey"});
	}

	/**
	 * Initializes the fragment entry contributed persistence.
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

		_finderPathFetchByFragmentEntryKey = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByFragmentEntryKey",
			new String[] {String.class.getName()},
			new String[] {"fragmentEntryKey"}, true);

		_finderPathCountByFragmentEntryKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentEntryKey", new String[] {String.class.getName()},
			new String[] {"fragmentEntryKey"}, false);

		_setFragmentEntryContributedUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setFragmentEntryContributedUtilPersistence(null);

		entityCache.removeCache(FragmentEntryContributedImpl.class.getName());
	}

	private void _setFragmentEntryContributedUtilPersistence(
		FragmentEntryContributedPersistence
			fragmentEntryContributedPersistence) {

		try {
			Field field = FragmentEntryContributedUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, fragmentEntryContributedPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FRAGMENTENTRYCONTRIBUTED =
		"SELECT fragmentEntryContributed FROM FragmentEntryContributed fragmentEntryContributed";

	private static final String _SQL_SELECT_FRAGMENTENTRYCONTRIBUTED_WHERE =
		"SELECT fragmentEntryContributed FROM FragmentEntryContributed fragmentEntryContributed WHERE ";

	private static final String _SQL_COUNT_FRAGMENTENTRYCONTRIBUTED =
		"SELECT COUNT(fragmentEntryContributed) FROM FragmentEntryContributed fragmentEntryContributed";

	private static final String _SQL_COUNT_FRAGMENTENTRYCONTRIBUTED_WHERE =
		"SELECT COUNT(fragmentEntryContributed) FROM FragmentEntryContributed fragmentEntryContributed WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"fragmentEntryContributed.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FragmentEntryContributed exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FragmentEntryContributed exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryContributedPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}