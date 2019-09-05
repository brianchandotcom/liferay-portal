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

package com.liferay.account.service.persistence.impl;

import com.liferay.account.exception.NoSuchEntryUserRelException;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.impl.AccountEntryUserRelImpl;
import com.liferay.account.model.impl.AccountEntryUserRelModelImpl;
import com.liferay.account.service.persistence.AccountEntryUserRelPK;
import com.liferay.account.service.persistence.AccountEntryUserRelPersistence;
import com.liferay.account.service.persistence.impl.constants.AccountPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the account entry user rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AccountEntryUserRelPersistence.class)
@ProviderType
public class AccountEntryUserRelPersistenceImpl
	extends BasePersistenceImpl<AccountEntryUserRel>
	implements AccountEntryUserRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountEntryUserRelUtil</code> to access the account entry user rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountEntryUserRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public AccountEntryUserRelPersistenceImpl() {
		setModelClass(AccountEntryUserRel.class);

		setModelImplClass(AccountEntryUserRelImpl.class);
		setModelPKClass(AccountEntryUserRelPK.class);
	}

	/**
	 * Caches the account entry user rel in the entity cache if it is enabled.
	 *
	 * @param accountEntryUserRel the account entry user rel
	 */
	@Override
	public void cacheResult(AccountEntryUserRel accountEntryUserRel) {
		entityCache.putResult(
			entityCacheEnabled, AccountEntryUserRelImpl.class,
			accountEntryUserRel.getPrimaryKey(), accountEntryUserRel);

		accountEntryUserRel.resetOriginalValues();
	}

	/**
	 * Caches the account entry user rels in the entity cache if it is enabled.
	 *
	 * @param accountEntryUserRels the account entry user rels
	 */
	@Override
	public void cacheResult(List<AccountEntryUserRel> accountEntryUserRels) {
		for (AccountEntryUserRel accountEntryUserRel : accountEntryUserRels) {
			if (entityCache.getResult(
					entityCacheEnabled, AccountEntryUserRelImpl.class,
					accountEntryUserRel.getPrimaryKey()) == null) {

				cacheResult(accountEntryUserRel);
			}
			else {
				accountEntryUserRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all account entry user rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountEntryUserRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the account entry user rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AccountEntryUserRel accountEntryUserRel) {
		entityCache.removeResult(
			entityCacheEnabled, AccountEntryUserRelImpl.class,
			accountEntryUserRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<AccountEntryUserRel> accountEntryUserRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AccountEntryUserRel accountEntryUserRel : accountEntryUserRels) {
			entityCache.removeResult(
				entityCacheEnabled, AccountEntryUserRelImpl.class,
				accountEntryUserRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new account entry user rel with the primary key. Does not add the account entry user rel to the database.
	 *
	 * @param accountEntryUserRelPK the primary key for the new account entry user rel
	 * @return the new account entry user rel
	 */
	@Override
	public AccountEntryUserRel create(
		AccountEntryUserRelPK accountEntryUserRelPK) {

		AccountEntryUserRel accountEntryUserRel = new AccountEntryUserRelImpl();

		accountEntryUserRel.setNew(true);
		accountEntryUserRel.setPrimaryKey(accountEntryUserRelPK);

		accountEntryUserRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return accountEntryUserRel;
	}

	/**
	 * Removes the account entry user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryUserRelPK the primary key of the account entry user rel
	 * @return the account entry user rel that was removed
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel remove(
			AccountEntryUserRelPK accountEntryUserRelPK)
		throws NoSuchEntryUserRelException {

		return remove((Serializable)accountEntryUserRelPK);
	}

	/**
	 * Removes the account entry user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account entry user rel
	 * @return the account entry user rel that was removed
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel remove(Serializable primaryKey)
		throws NoSuchEntryUserRelException {

		Session session = null;

		try {
			session = openSession();

			AccountEntryUserRel accountEntryUserRel =
				(AccountEntryUserRel)session.get(
					AccountEntryUserRelImpl.class, primaryKey);

			if (accountEntryUserRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryUserRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(accountEntryUserRel);
		}
		catch (NoSuchEntryUserRelException nsee) {
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
	protected AccountEntryUserRel removeImpl(
		AccountEntryUserRel accountEntryUserRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(accountEntryUserRel)) {
				accountEntryUserRel = (AccountEntryUserRel)session.get(
					AccountEntryUserRelImpl.class,
					accountEntryUserRel.getPrimaryKeyObj());
			}

			if (accountEntryUserRel != null) {
				session.delete(accountEntryUserRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (accountEntryUserRel != null) {
			clearCache(accountEntryUserRel);
		}

		return accountEntryUserRel;
	}

	@Override
	public AccountEntryUserRel updateImpl(
		AccountEntryUserRel accountEntryUserRel) {

		boolean isNew = accountEntryUserRel.isNew();

		Session session = null;

		try {
			session = openSession();

			if (accountEntryUserRel.isNew()) {
				session.save(accountEntryUserRel);

				accountEntryUserRel.setNew(false);
			}
			else {
				accountEntryUserRel = (AccountEntryUserRel)session.merge(
					accountEntryUserRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			entityCacheEnabled, AccountEntryUserRelImpl.class,
			accountEntryUserRel.getPrimaryKey(), accountEntryUserRel, false);

		accountEntryUserRel.resetOriginalValues();

		return accountEntryUserRel;
	}

	/**
	 * Returns the account entry user rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account entry user rel
	 * @return the account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryUserRelException {

		AccountEntryUserRel accountEntryUserRel = fetchByPrimaryKey(primaryKey);

		if (accountEntryUserRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryUserRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return accountEntryUserRel;
	}

	/**
	 * Returns the account entry user rel with the primary key or throws a <code>NoSuchEntryUserRelException</code> if it could not be found.
	 *
	 * @param accountEntryUserRelPK the primary key of the account entry user rel
	 * @return the account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel findByPrimaryKey(
			AccountEntryUserRelPK accountEntryUserRelPK)
		throws NoSuchEntryUserRelException {

		return findByPrimaryKey((Serializable)accountEntryUserRelPK);
	}

	/**
	 * Returns the account entry user rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEntryUserRelPK the primary key of the account entry user rel
	 * @return the account entry user rel, or <code>null</code> if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel fetchByPrimaryKey(
		AccountEntryUserRelPK accountEntryUserRelPK) {

		return fetchByPrimaryKey((Serializable)accountEntryUserRelPK);
	}

	/**
	 * Returns all the account entry user rels.
	 *
	 * @return the account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @return the range of account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findAll(
		int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findAll(
		int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<AccountEntryUserRel> list = null;

		if (useFinderCache) {
			list = (List<AccountEntryUserRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ACCOUNTENTRYUSERREL);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNTENTRYUSERREL;

				if (pagination) {
					sql = sql.concat(
						AccountEntryUserRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AccountEntryUserRel>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AccountEntryUserRel>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the account entry user rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AccountEntryUserRel accountEntryUserRel : findAll()) {
			remove(accountEntryUserRel);
		}
	}

	/**
	 * Returns the number of account entry user rels.
	 *
	 * @return the number of account entry user rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ACCOUNTENTRYUSERREL);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getCompoundPKColumnNames() {
		return _compoundPKColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "accountEntryUserRelPK";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ACCOUNTENTRYUSERREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AccountEntryUserRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account entry user rel persistence.
	 */
	@Activate
	public void activate() {
		AccountEntryUserRelModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		AccountEntryUserRelModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AccountEntryUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AccountEntryUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AccountEntryUserRelImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = AccountPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.account.model.AccountEntryUserRel"),
			true);
	}

	@Override
	@Reference(
		target = AccountPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AccountPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ACCOUNTENTRYUSERREL =
		"SELECT accountEntryUserRel FROM AccountEntryUserRel accountEntryUserRel";

	private static final String _SQL_COUNT_ACCOUNTENTRYUSERREL =
		"SELECT COUNT(accountEntryUserRel) FROM AccountEntryUserRel accountEntryUserRel";

	private static final String _ORDER_BY_ENTITY_ALIAS = "accountEntryUserRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AccountEntryUserRel exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryUserRelPersistenceImpl.class);

	private static final Set<String> _compoundPKColumnNames = SetUtil.fromArray(
		new String[] {"accountEntryId", "userId"});

	static {
		try {
			Class.forName(AccountPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}