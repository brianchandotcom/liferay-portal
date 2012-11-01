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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchPasswordPolicyException;
import com.liferay.portal.kernel.bean.BeanReference;
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
				Long.valueOf(passwordPolicy.getCompanyId()),
				Boolean.valueOf(passwordPolicy.getDefaultPolicy())
			}, passwordPolicy);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				Long.valueOf(passwordPolicy.getCompanyId()),
				
			passwordPolicy.getName()
			}, passwordPolicy);

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

	protected void clearUniqueFindersCache(PasswordPolicy passwordPolicy) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP,
			new Object[] {
				Long.valueOf(passwordPolicy.getCompanyId()),
				Boolean.valueOf(passwordPolicy.getDefaultPolicy())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				Long.valueOf(passwordPolicy.getCompanyId()),
				
			passwordPolicy.getName()
			});
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
		return remove(Long.valueOf(passwordPolicyId));
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

			if (passwordPolicy.isCachedModel()) {
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

		PasswordPolicyModelImpl passwordPolicyModelImpl = (PasswordPolicyModelImpl)passwordPolicy;

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

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
				new Object[] {
					Long.valueOf(passwordPolicy.getCompanyId()),
					Boolean.valueOf(passwordPolicy.getDefaultPolicy())
				}, passwordPolicy);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] {
					Long.valueOf(passwordPolicy.getCompanyId()),
					
				passwordPolicy.getName()
				}, passwordPolicy);
		}
		else {
			if ((passwordPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_DP.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(passwordPolicyModelImpl.getOriginalCompanyId()),
						Boolean.valueOf(passwordPolicyModelImpl.getOriginalDefaultPolicy())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_DP, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
					new Object[] {
						Long.valueOf(passwordPolicy.getCompanyId()),
						Boolean.valueOf(passwordPolicy.getDefaultPolicy())
					}, passwordPolicy);
			}

			if ((passwordPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(passwordPolicyModelImpl.getOriginalCompanyId()),
						
						passwordPolicyModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
					new Object[] {
						Long.valueOf(passwordPolicy.getCompanyId()),
						
					passwordPolicy.getName()
					}, passwordPolicy);
			}
		}

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
	 * @throws com.liferay.portal.NoSuchModelException if a password policy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PasswordPolicy findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
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
		PasswordPolicy passwordPolicy = fetchByPrimaryKey(passwordPolicyId);

		if (passwordPolicy == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + passwordPolicyId);
			}

			throw new NoSuchPasswordPolicyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				passwordPolicyId);
		}

		return passwordPolicy;
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
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
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
		PasswordPolicy passwordPolicy = (PasswordPolicy)EntityCacheUtil.getResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
				PasswordPolicyImpl.class, passwordPolicyId);

		if (passwordPolicy == _nullPasswordPolicy) {
			return null;
		}

		if (passwordPolicy == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				passwordPolicy = (PasswordPolicy)session.get(PasswordPolicyImpl.class,
						Long.valueOf(passwordPolicyId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (passwordPolicy != null) {
					cacheResult(passwordPolicy);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
						PasswordPolicyImpl.class, passwordPolicyId,
						_nullPasswordPolicy);
				}

				closeSession(session);
			}
		}

		return passwordPolicy;
	}

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
			StringBundler query = new StringBundler(3);

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

				result = list;

				PasswordPolicy passwordPolicy = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
						finderArgs, list);
				}
				else {
					passwordPolicy = list.get(0);

					cacheResult(passwordPolicy);

					if ((passwordPolicy.getCompanyId() != companyId) ||
							(passwordPolicy.getDefaultPolicy() != defaultPolicy)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
							finderArgs, passwordPolicy);
					}
				}

				return passwordPolicy;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PasswordPolicy)result;
			}
		}
	}

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
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				List<PasswordPolicy> list = q.list();

				result = list;

				PasswordPolicy passwordPolicy = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					passwordPolicy = list.get(0);

					cacheResult(passwordPolicy);

					if ((passwordPolicy.getCompanyId() != companyId) ||
							(passwordPolicy.getName() == null) ||
							!passwordPolicy.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, passwordPolicy);
					}
				}

				return passwordPolicy;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PasswordPolicy)result;
			}
		}
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
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
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
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
	 * Returns the number of password policies where companyId = &#63; and defaultPolicy = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the number of matching password policies
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_DP(long companyId, boolean defaultPolicy)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, defaultPolicy };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_DP,
				finderArgs, this);

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
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_DP,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
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
		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PASSWORDPOLICY_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
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
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

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
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_PASSWORDPOLICY = "SELECT passwordPolicy FROM PasswordPolicy passwordPolicy";
	private static final String _SQL_SELECT_PASSWORDPOLICY_WHERE = "SELECT passwordPolicy FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _SQL_COUNT_PASSWORDPOLICY = "SELECT COUNT(passwordPolicy) FROM PasswordPolicy passwordPolicy";
	private static final String _SQL_COUNT_PASSWORDPOLICY_WHERE = "SELECT COUNT(passwordPolicy) FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _FINDER_COLUMN_C_DP_COMPANYID_2 = "passwordPolicy.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_DP_DEFAULTPOLICY_2 = "passwordPolicy.defaultPolicy = ?";
	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "passwordPolicy.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "passwordPolicy.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "passwordPolicy.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(passwordPolicy.name IS NULL OR passwordPolicy.name = ?)";
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