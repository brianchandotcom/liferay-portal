/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchPasswordPolicyException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.impl.PasswordPolicyImpl;
import com.liferay.portal.model.impl.PasswordPolicyModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the password policy service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyPersistence
 * @see PasswordPolicyUtil
 * @generated
 */
public class PasswordPolicyPersistenceImpl extends BasePersistenceImpl<PasswordPolicy>
	implements PasswordPolicyPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PasswordPolicyUtil} to access the password policy persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PasswordPolicyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_C_DP = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_DP",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			PasswordPolicyModelImpl.COMPANYID_COLUMN_BITMASK |
			PasswordPolicyModelImpl.DEFAULTPOLICY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_DP = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_DP",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the matching password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy findByC_DP(long companyId, boolean defaultPolicy)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = fetchByC_DP(companyId, defaultPolicy);

		if (passwordPolicy == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", defaultPolicy=");
			msg.append(defaultPolicy);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPasswordPolicyException(msg.toString());
		}

		return passwordPolicy;
	}

	/**
	 * Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy fetchByC_DP(long companyId, boolean defaultPolicy)
		throws SystemException {
		return fetchByC_DP(companyId, defaultPolicy, true);
	}

	/**
	 * Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy fetchByC_DP(long companyId, boolean defaultPolicy,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, defaultPolicy };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_DP,
					finderArgs, this);
		}

		if (result instanceof PasswordPolicy) {
			PasswordPolicy passwordPolicy = (PasswordPolicy)result;

			if ((companyId != passwordPolicy.getCompanyId()) ||
					(defaultPolicy != passwordPolicy.getDefaultPolicy())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

			query.append(_FINDER_COLUMN_C_DP_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_DP_DEFAULTPOLICY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultPolicy);

				List<PasswordPolicy> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"PasswordPolicyPersistenceImpl.fetchByC_DP(long, boolean, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					PasswordPolicy passwordPolicy = list.get(0);

					result = passwordPolicy;

					cacheResult(passwordPolicy);

					if ((passwordPolicy.getCompanyId() != companyId) ||
							(passwordPolicy.getDefaultPolicy() != defaultPolicy)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
							finderArgs, passwordPolicy);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP,
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
			return (PasswordPolicy)result;
		}
	}

	/**
	 * Removes the password policy where companyId = &#63; and defaultPolicy = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the password policy that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy removeByC_DP(long companyId, boolean defaultPolicy)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = findByC_DP(companyId, defaultPolicy);

		return remove(passwordPolicy);
	}

	/**
	 * Returns the number of password policies where companyId = &#63; and defaultPolicy = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the number of matching password policies
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_DP(long companyId, boolean defaultPolicy)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_DP;

		Object[] finderArgs = new Object[] { companyId, defaultPolicy };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PASSWORDPOLICY_WHERE);

			query.append(_FINDER_COLUMN_C_DP_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_DP_DEFAULTPOLICY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultPolicy);

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

	private static final String _FINDER_COLUMN_C_DP_COMPANYID_2 = "passwordPolicy.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_DP_DEFAULTPOLICY_2 = "passwordPolicy.defaultPolicy = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			PasswordPolicyModelImpl.COMPANYID_COLUMN_BITMASK |
			PasswordPolicyModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the password policy where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy findByC_N(long companyId, String name)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = fetchByC_N(companyId, name);

		if (passwordPolicy == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPasswordPolicyException(msg.toString());
		}

		return passwordPolicy;
	}

	/**
	 * Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy fetchByC_N(long companyId, String name)
		throws SystemException {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result instanceof PasswordPolicy) {
			PasswordPolicy passwordPolicy = (PasswordPolicy)result;

			if ((companyId != passwordPolicy.getCompanyId()) ||
					!Validator.equals(name, passwordPolicy.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				List<PasswordPolicy> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					PasswordPolicy passwordPolicy = list.get(0);

					result = passwordPolicy;

					cacheResult(passwordPolicy);

					if ((passwordPolicy.getCompanyId() != companyId) ||
							(passwordPolicy.getName() == null) ||
							!passwordPolicy.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, passwordPolicy);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
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
			return (PasswordPolicy)result;
		}
	}

	/**
	 * Removes the password policy where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the password policy that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy removeByC_N(long companyId, String name)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = findByC_N(companyId, name);

		return remove(passwordPolicy);
	}

	/**
	 * Returns the number of password policies where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching password policies
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_N(long companyId, String name)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PASSWORDPOLICY_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "passwordPolicy.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "passwordPolicy.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "passwordPolicy.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(passwordPolicy.name IS NULL OR passwordPolicy.name = '')";

	/**
	 * Caches the password policy in the entity cache if it is enabled.
	 *
	 * @param passwordPolicy the password policy
	 */
	public void cacheResult(PasswordPolicy passwordPolicy) {
		EntityCacheUtil.putResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey(),
			passwordPolicy);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
			new Object[] {
				passwordPolicy.getCompanyId(), passwordPolicy.getDefaultPolicy()
			}, passwordPolicy);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { passwordPolicy.getCompanyId(), passwordPolicy.getName() },
			passwordPolicy);

		passwordPolicy.resetOriginalValues();
	}

	/**
	 * Caches the password policies in the entity cache if it is enabled.
	 *
	 * @param passwordPolicies the password policies
	 */
	public void cacheResult(List<PasswordPolicy> passwordPolicies) {
		for (PasswordPolicy passwordPolicy : passwordPolicies) {
			if (EntityCacheUtil.getResult(
						PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
						PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey()) == null) {
				cacheResult(passwordPolicy);
			}
			else {
				passwordPolicy.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all password policies.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PasswordPolicyImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PasswordPolicyImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the password policy.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PasswordPolicy passwordPolicy) {
		EntityCacheUtil.removeResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(passwordPolicy);
	}

	@Override
	public void clearCache(List<PasswordPolicy> passwordPolicies) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PasswordPolicy passwordPolicy : passwordPolicies) {
			EntityCacheUtil.removeResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
				PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey());

			clearUniqueFindersCache(passwordPolicy);
		}
	}

	protected void cacheUniqueFindersCache(PasswordPolicy passwordPolicy) {
		if (passwordPolicy.isNew()) {
			Object[] args = new Object[] {
					passwordPolicy.getCompanyId(),
					passwordPolicy.getDefaultPolicy()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_DP, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP, args,
				passwordPolicy);

			args = new Object[] {
					passwordPolicy.getCompanyId(), passwordPolicy.getName()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N, args,
				passwordPolicy);
		}
		else {
			PasswordPolicyModelImpl passwordPolicyModelImpl = (PasswordPolicyModelImpl)passwordPolicy;

			if ((passwordPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_DP.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						passwordPolicy.getCompanyId(),
						passwordPolicy.getDefaultPolicy()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_DP, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP, args,
					passwordPolicy);
			}

			if ((passwordPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						passwordPolicy.getCompanyId(), passwordPolicy.getName()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N, args,
					passwordPolicy);
			}
		}
	}

	protected void clearUniqueFindersCache(PasswordPolicy passwordPolicy) {
		PasswordPolicyModelImpl passwordPolicyModelImpl = (PasswordPolicyModelImpl)passwordPolicy;

		Object[] args = new Object[] {
				passwordPolicy.getCompanyId(), passwordPolicy.getDefaultPolicy()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_DP, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP, args);

		if ((passwordPolicyModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_DP.getColumnBitmask()) != 0) {
			args = new Object[] {
					passwordPolicyModelImpl.getOriginalCompanyId(),
					passwordPolicyModelImpl.getOriginalDefaultPolicy()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_DP, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP, args);
		}

		args = new Object[] {
				passwordPolicy.getCompanyId(), passwordPolicy.getName()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

		if ((passwordPolicyModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					passwordPolicyModelImpl.getOriginalCompanyId(),
					passwordPolicyModelImpl.getOriginalName()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);
		}
	}

	/**
	 * Creates a new password policy with the primary key. Does not add the password policy to the database.
	 *
	 * @param passwordPolicyId the primary key for the new password policy
	 * @return the new password policy
	 */
	public PasswordPolicy create(long passwordPolicyId) {
		PasswordPolicy passwordPolicy = new PasswordPolicyImpl();

		passwordPolicy.setNew(true);
		passwordPolicy.setPrimaryKey(passwordPolicyId);

		return passwordPolicy;
	}

	/**
	 * Removes the password policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @return the password policy that was removed
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy remove(long passwordPolicyId)
		throws NoSuchPasswordPolicyException, SystemException {
		return remove((Serializable)passwordPolicyId);
	}

	/**
	 * Removes the password policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the password policy
	 * @return the password policy that was removed
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PasswordPolicy remove(Serializable primaryKey)
		throws NoSuchPasswordPolicyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PasswordPolicy passwordPolicy = (PasswordPolicy)session.get(PasswordPolicyImpl.class,
					primaryKey);

			if (passwordPolicy == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPasswordPolicyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(passwordPolicy);
		}
		catch (NoSuchPasswordPolicyException nsee) {
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
	protected PasswordPolicy removeImpl(PasswordPolicy passwordPolicy)
		throws SystemException {
		passwordPolicy = toUnwrappedModel(passwordPolicy);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(passwordPolicy)) {
				passwordPolicy = (PasswordPolicy)session.get(PasswordPolicyImpl.class,
						passwordPolicy.getPrimaryKeyObj());
			}

			if (passwordPolicy != null) {
				session.delete(passwordPolicy);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (passwordPolicy != null) {
			clearCache(passwordPolicy);
		}

		return passwordPolicy;
	}

	@Override
	public PasswordPolicy updateImpl(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws SystemException {
		passwordPolicy = toUnwrappedModel(passwordPolicy);

		boolean isNew = passwordPolicy.isNew();

		Session session = null;

		try {
			session = openSession();

			if (passwordPolicy.isNew()) {
				session.save(passwordPolicy);

				passwordPolicy.setNew(false);
			}
			else {
				session.merge(passwordPolicy);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PasswordPolicyModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey(),
			passwordPolicy);

		clearUniqueFindersCache(passwordPolicy);
		cacheUniqueFindersCache(passwordPolicy);

		return passwordPolicy;
	}

	protected PasswordPolicy toUnwrappedModel(PasswordPolicy passwordPolicy) {
		if (passwordPolicy instanceof PasswordPolicyImpl) {
			return passwordPolicy;
		}

		PasswordPolicyImpl passwordPolicyImpl = new PasswordPolicyImpl();

		passwordPolicyImpl.setNew(passwordPolicy.isNew());
		passwordPolicyImpl.setPrimaryKey(passwordPolicy.getPrimaryKey());

		passwordPolicyImpl.setPasswordPolicyId(passwordPolicy.getPasswordPolicyId());
		passwordPolicyImpl.setCompanyId(passwordPolicy.getCompanyId());
		passwordPolicyImpl.setUserId(passwordPolicy.getUserId());
		passwordPolicyImpl.setUserName(passwordPolicy.getUserName());
		passwordPolicyImpl.setCreateDate(passwordPolicy.getCreateDate());
		passwordPolicyImpl.setModifiedDate(passwordPolicy.getModifiedDate());
		passwordPolicyImpl.setDefaultPolicy(passwordPolicy.isDefaultPolicy());
		passwordPolicyImpl.setName(passwordPolicy.getName());
		passwordPolicyImpl.setDescription(passwordPolicy.getDescription());
		passwordPolicyImpl.setChangeable(passwordPolicy.isChangeable());
		passwordPolicyImpl.setChangeRequired(passwordPolicy.isChangeRequired());
		passwordPolicyImpl.setMinAge(passwordPolicy.getMinAge());
		passwordPolicyImpl.setCheckSyntax(passwordPolicy.isCheckSyntax());
		passwordPolicyImpl.setAllowDictionaryWords(passwordPolicy.isAllowDictionaryWords());
		passwordPolicyImpl.setMinAlphanumeric(passwordPolicy.getMinAlphanumeric());
		passwordPolicyImpl.setMinLength(passwordPolicy.getMinLength());
		passwordPolicyImpl.setMinLowerCase(passwordPolicy.getMinLowerCase());
		passwordPolicyImpl.setMinNumbers(passwordPolicy.getMinNumbers());
		passwordPolicyImpl.setMinSymbols(passwordPolicy.getMinSymbols());
		passwordPolicyImpl.setMinUpperCase(passwordPolicy.getMinUpperCase());
		passwordPolicyImpl.setRegex(passwordPolicy.getRegex());
		passwordPolicyImpl.setHistory(passwordPolicy.isHistory());
		passwordPolicyImpl.setHistoryCount(passwordPolicy.getHistoryCount());
		passwordPolicyImpl.setExpireable(passwordPolicy.isExpireable());
		passwordPolicyImpl.setMaxAge(passwordPolicy.getMaxAge());
		passwordPolicyImpl.setWarningTime(passwordPolicy.getWarningTime());
		passwordPolicyImpl.setGraceLimit(passwordPolicy.getGraceLimit());
		passwordPolicyImpl.setLockout(passwordPolicy.isLockout());
		passwordPolicyImpl.setMaxFailure(passwordPolicy.getMaxFailure());
		passwordPolicyImpl.setLockoutDuration(passwordPolicy.getLockoutDuration());
		passwordPolicyImpl.setRequireUnlock(passwordPolicy.isRequireUnlock());
		passwordPolicyImpl.setResetFailureCount(passwordPolicy.getResetFailureCount());
		passwordPolicyImpl.setResetTicketMaxAge(passwordPolicy.getResetTicketMaxAge());

		return passwordPolicyImpl;
	}

	/**
	 * Returns the password policy with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the password policy
	 * @return the password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PasswordPolicy findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = fetchByPrimaryKey(primaryKey);

		if (passwordPolicy == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPasswordPolicyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return passwordPolicy;
	}

	/**
	 * Returns the password policy with the primary key or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @return the password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy findByPrimaryKey(long passwordPolicyId)
		throws NoSuchPasswordPolicyException, SystemException {
		return findByPrimaryKey((Serializable)passwordPolicyId);
	}

	/**
	 * Returns the password policy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the password policy
	 * @return the password policy, or <code>null</code> if a password policy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PasswordPolicy fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		PasswordPolicy passwordPolicy = (PasswordPolicy)EntityCacheUtil.getResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
				PasswordPolicyImpl.class, primaryKey);

		if (passwordPolicy == _nullPasswordPolicy) {
			return null;
		}

		if (passwordPolicy == null) {
			Session session = null;

			try {
				session = openSession();

				passwordPolicy = (PasswordPolicy)session.get(PasswordPolicyImpl.class,
						primaryKey);

				if (passwordPolicy != null) {
					cacheResult(passwordPolicy);
				}
				else {
					EntityCacheUtil.putResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
						PasswordPolicyImpl.class, primaryKey,
						_nullPasswordPolicy);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
					PasswordPolicyImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return passwordPolicy;
	}

	/**
	 * Returns the password policy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @return the password policy, or <code>null</code> if a password policy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordPolicy fetchByPrimaryKey(long passwordPolicyId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)passwordPolicyId);
	}

	/**
	 * Returns all the password policies.
	 *
	 * @return the password policies
	 * @throws SystemException if a system exception occurred
	 */
	public List<PasswordPolicy> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of password policies
	 * @throws SystemException if a system exception occurred
	 */
	public List<PasswordPolicy> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the password policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of password policies
	 * @throws SystemException if a system exception occurred
	 */
	public List<PasswordPolicy> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<PasswordPolicy> list = (List<PasswordPolicy>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PASSWORDPOLICY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PASSWORDPOLICY;

				if (pagination) {
					sql = sql.concat(PasswordPolicyModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PasswordPolicy>(list);
				}
				else {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
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
	 * Removes all the password policies from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (PasswordPolicy passwordPolicy : findAll()) {
			remove(passwordPolicy);
		}
	}

	/**
	 * Returns the number of password policies.
	 *
	 * @return the number of password policies
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PASSWORDPOLICY);

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
	 * Initializes the password policy persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PasswordPolicy")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PasswordPolicy>> listenersList = new ArrayList<ModelListener<PasswordPolicy>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PasswordPolicy>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PasswordPolicyImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PASSWORDPOLICY = "SELECT passwordPolicy FROM PasswordPolicy passwordPolicy";
	private static final String _SQL_SELECT_PASSWORDPOLICY_WHERE = "SELECT passwordPolicy FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _SQL_COUNT_PASSWORDPOLICY = "SELECT COUNT(passwordPolicy) FROM PasswordPolicy passwordPolicy";
	private static final String _SQL_COUNT_PASSWORDPOLICY_WHERE = "SELECT COUNT(passwordPolicy) FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "passwordPolicy.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PasswordPolicy exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PasswordPolicy exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(PasswordPolicyPersistenceImpl.class);
	private static PasswordPolicy _nullPasswordPolicy = new PasswordPolicyImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PasswordPolicy> toCacheModel() {
				return _nullPasswordPolicyCacheModel;
			}
		};

	private static CacheModel<PasswordPolicy> _nullPasswordPolicyCacheModel = new CacheModel<PasswordPolicy>() {
			public PasswordPolicy toEntityModel() {
				return _nullPasswordPolicy;
			}
		};
}