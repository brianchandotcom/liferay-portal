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

package com.liferay.portal.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchWebDAVPropsException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.WebDAVProps;
import com.liferay.portal.model.impl.WebDAVPropsImpl;
import com.liferay.portal.model.impl.WebDAVPropsModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the web d a v props service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsPersistence
 * @see com.liferay.portal.service.persistence.WebDAVPropsUtil
 * @generated
 */
@ProviderType
public class WebDAVPropsPersistenceImpl extends BasePersistenceImpl<WebDAVProps>
	implements WebDAVPropsPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link WebDAVPropsUtil} to access the web d a v props persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = WebDAVPropsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsModelImpl.FINDER_CACHE_ENABLED, WebDAVPropsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsModelImpl.FINDER_CACHE_ENABLED, WebDAVPropsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsModelImpl.FINDER_CACHE_ENABLED, WebDAVPropsImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			WebDAVPropsModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			WebDAVPropsModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the web d a v props where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchWebDAVPropsException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching web d a v props
	 * @throws NoSuchWebDAVPropsException if a matching web d a v props could not be found
	 */
	@Override
	public WebDAVProps findByC_C(long classNameId, long classPK)
		throws NoSuchWebDAVPropsException {
		WebDAVProps webDAVProps = fetchByC_C(classNameId, classPK);

		if (webDAVProps == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchWebDAVPropsException(msg.toString());
		}

		return webDAVProps;
	}

	/**
	 * Returns the web d a v props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching web d a v props, or <code>null</code> if a matching web d a v props could not be found
	 */
	@Override
	public WebDAVProps fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the web d a v props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching web d a v props, or <code>null</code> if a matching web d a v props could not be found
	 */
	@Override
	public WebDAVProps fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result instanceof WebDAVProps) {
			WebDAVProps webDAVProps = (WebDAVProps)result;

			if ((classNameId != webDAVProps.getClassNameId()) ||
					(classPK != webDAVProps.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WEBDAVPROPS_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<WebDAVProps> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, finderArgs,
						list);
				}
				else {
					WebDAVProps webDAVProps = list.get(0);

					result = webDAVProps;

					cacheResult(webDAVProps);

					if ((webDAVProps.getClassNameId() != classNameId) ||
							(webDAVProps.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, webDAVProps);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, finderArgs);

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
			return (WebDAVProps)result;
		}
	}

	/**
	 * Removes the web d a v props where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the web d a v props that was removed
	 */
	@Override
	public WebDAVProps removeByC_C(long classNameId, long classPK)
		throws NoSuchWebDAVPropsException {
		WebDAVProps webDAVProps = findByC_C(classNameId, classPK);

		return remove(webDAVProps);
	}

	/**
	 * Returns the number of web d a v propses where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching web d a v propses
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WEBDAVPROPS_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "webDAVProps.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "webDAVProps.classPK = ?";

	public WebDAVPropsPersistenceImpl() {
		setModelClass(WebDAVProps.class);
	}

	/**
	 * Caches the web d a v props in the entity cache if it is enabled.
	 *
	 * @param webDAVProps the web d a v props
	 */
	@Override
	public void cacheResult(WebDAVProps webDAVProps) {
		entityCache.putResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsImpl.class, webDAVProps.getPrimaryKey(), webDAVProps);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] { webDAVProps.getClassNameId(), webDAVProps.getClassPK() },
			webDAVProps);

		webDAVProps.resetOriginalValues();
	}

	/**
	 * Caches the web d a v propses in the entity cache if it is enabled.
	 *
	 * @param webDAVPropses the web d a v propses
	 */
	@Override
	public void cacheResult(List<WebDAVProps> webDAVPropses) {
		for (WebDAVProps webDAVProps : webDAVPropses) {
			if (entityCache.getResult(
						WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
						WebDAVPropsImpl.class, webDAVProps.getPrimaryKey()) == null) {
				cacheResult(webDAVProps);
			}
			else {
				webDAVProps.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all web d a v propses.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WebDAVPropsImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the web d a v props.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WebDAVProps webDAVProps) {
		entityCache.removeResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsImpl.class, webDAVProps.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((WebDAVPropsModelImpl)webDAVProps);
	}

	@Override
	public void clearCache(List<WebDAVProps> webDAVPropses) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WebDAVProps webDAVProps : webDAVPropses) {
			entityCache.removeResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
				WebDAVPropsImpl.class, webDAVProps.getPrimaryKey());

			clearUniqueFindersCache((WebDAVPropsModelImpl)webDAVProps);
		}
	}

	protected void cacheUniqueFindersCache(
		WebDAVPropsModelImpl webDAVPropsModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					webDAVPropsModelImpl.getClassNameId(),
					webDAVPropsModelImpl.getClassPK()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
				webDAVPropsModelImpl);
		}
		else {
			if ((webDAVPropsModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						webDAVPropsModelImpl.getClassNameId(),
						webDAVPropsModelImpl.getClassPK()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
					webDAVPropsModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		WebDAVPropsModelImpl webDAVPropsModelImpl) {
		Object[] args = new Object[] {
				webDAVPropsModelImpl.getClassNameId(),
				webDAVPropsModelImpl.getClassPK()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);

		if ((webDAVPropsModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
			args = new Object[] {
					webDAVPropsModelImpl.getOriginalClassNameId(),
					webDAVPropsModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}
	}

	/**
	 * Creates a new web d a v props with the primary key. Does not add the web d a v props to the database.
	 *
	 * @param webDavPropsId the primary key for the new web d a v props
	 * @return the new web d a v props
	 */
	@Override
	public WebDAVProps create(long webDavPropsId) {
		WebDAVProps webDAVProps = new WebDAVPropsImpl();

		webDAVProps.setNew(true);
		webDAVProps.setPrimaryKey(webDavPropsId);

		return webDAVProps;
	}

	/**
	 * Removes the web d a v props with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webDavPropsId the primary key of the web d a v props
	 * @return the web d a v props that was removed
	 * @throws NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	 */
	@Override
	public WebDAVProps remove(long webDavPropsId)
		throws NoSuchWebDAVPropsException {
		return remove((Serializable)webDavPropsId);
	}

	/**
	 * Removes the web d a v props with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the web d a v props
	 * @return the web d a v props that was removed
	 * @throws NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	 */
	@Override
	public WebDAVProps remove(Serializable primaryKey)
		throws NoSuchWebDAVPropsException {
		Session session = null;

		try {
			session = openSession();

			WebDAVProps webDAVProps = (WebDAVProps)session.get(WebDAVPropsImpl.class,
					primaryKey);

			if (webDAVProps == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWebDAVPropsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(webDAVProps);
		}
		catch (NoSuchWebDAVPropsException nsee) {
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
	protected WebDAVProps removeImpl(WebDAVProps webDAVProps) {
		webDAVProps = toUnwrappedModel(webDAVProps);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(webDAVProps)) {
				webDAVProps = (WebDAVProps)session.get(WebDAVPropsImpl.class,
						webDAVProps.getPrimaryKeyObj());
			}

			if (webDAVProps != null) {
				session.delete(webDAVProps);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (webDAVProps != null) {
			clearCache(webDAVProps);
		}

		return webDAVProps;
	}

	@Override
	public WebDAVProps updateImpl(WebDAVProps webDAVProps) {
		webDAVProps = toUnwrappedModel(webDAVProps);

		boolean isNew = webDAVProps.isNew();

		WebDAVPropsModelImpl webDAVPropsModelImpl = (WebDAVPropsModelImpl)webDAVProps;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (webDAVProps.getCreateDate() == null)) {
			if (serviceContext == null) {
				webDAVProps.setCreateDate(now);
			}
			else {
				webDAVProps.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!webDAVPropsModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				webDAVProps.setModifiedDate(now);
			}
			else {
				webDAVProps.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (webDAVProps.isNew()) {
				session.save(webDAVProps);

				webDAVProps.setNew(false);
			}
			else {
				webDAVProps = (WebDAVProps)session.merge(webDAVProps);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !WebDAVPropsModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		entityCache.putResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsImpl.class, webDAVProps.getPrimaryKey(), webDAVProps,
			false);

		clearUniqueFindersCache(webDAVPropsModelImpl);
		cacheUniqueFindersCache(webDAVPropsModelImpl, isNew);

		webDAVProps.resetOriginalValues();

		return webDAVProps;
	}

	protected WebDAVProps toUnwrappedModel(WebDAVProps webDAVProps) {
		if (webDAVProps instanceof WebDAVPropsImpl) {
			return webDAVProps;
		}

		WebDAVPropsImpl webDAVPropsImpl = new WebDAVPropsImpl();

		webDAVPropsImpl.setNew(webDAVProps.isNew());
		webDAVPropsImpl.setPrimaryKey(webDAVProps.getPrimaryKey());

		webDAVPropsImpl.setMvccVersion(webDAVProps.getMvccVersion());
		webDAVPropsImpl.setWebDavPropsId(webDAVProps.getWebDavPropsId());
		webDAVPropsImpl.setCompanyId(webDAVProps.getCompanyId());
		webDAVPropsImpl.setCreateDate(webDAVProps.getCreateDate());
		webDAVPropsImpl.setModifiedDate(webDAVProps.getModifiedDate());
		webDAVPropsImpl.setClassNameId(webDAVProps.getClassNameId());
		webDAVPropsImpl.setClassPK(webDAVProps.getClassPK());
		webDAVPropsImpl.setProps(webDAVProps.getProps());

		return webDAVPropsImpl;
	}

	/**
	 * Returns the web d a v props with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the web d a v props
	 * @return the web d a v props
	 * @throws NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	 */
	@Override
	public WebDAVProps findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWebDAVPropsException {
		WebDAVProps webDAVProps = fetchByPrimaryKey(primaryKey);

		if (webDAVProps == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchWebDAVPropsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return webDAVProps;
	}

	/**
	 * Returns the web d a v props with the primary key or throws a {@link NoSuchWebDAVPropsException} if it could not be found.
	 *
	 * @param webDavPropsId the primary key of the web d a v props
	 * @return the web d a v props
	 * @throws NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	 */
	@Override
	public WebDAVProps findByPrimaryKey(long webDavPropsId)
		throws NoSuchWebDAVPropsException {
		return findByPrimaryKey((Serializable)webDavPropsId);
	}

	/**
	 * Returns the web d a v props with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the web d a v props
	 * @return the web d a v props, or <code>null</code> if a web d a v props with the primary key could not be found
	 */
	@Override
	public WebDAVProps fetchByPrimaryKey(Serializable primaryKey) {
		WebDAVProps webDAVProps = (WebDAVProps)entityCache.getResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
				WebDAVPropsImpl.class, primaryKey);

		if (webDAVProps == _nullWebDAVProps) {
			return null;
		}

		if (webDAVProps == null) {
			Session session = null;

			try {
				session = openSession();

				webDAVProps = (WebDAVProps)session.get(WebDAVPropsImpl.class,
						primaryKey);

				if (webDAVProps != null) {
					cacheResult(webDAVProps);
				}
				else {
					entityCache.putResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
						WebDAVPropsImpl.class, primaryKey, _nullWebDAVProps);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
					WebDAVPropsImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return webDAVProps;
	}

	/**
	 * Returns the web d a v props with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webDavPropsId the primary key of the web d a v props
	 * @return the web d a v props, or <code>null</code> if a web d a v props with the primary key could not be found
	 */
	@Override
	public WebDAVProps fetchByPrimaryKey(long webDavPropsId) {
		return fetchByPrimaryKey((Serializable)webDavPropsId);
	}

	@Override
	public Map<Serializable, WebDAVProps> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, WebDAVProps> map = new HashMap<Serializable, WebDAVProps>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			WebDAVProps webDAVProps = fetchByPrimaryKey(primaryKey);

			if (webDAVProps != null) {
				map.put(primaryKey, webDAVProps);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			WebDAVProps webDAVProps = (WebDAVProps)entityCache.getResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
					WebDAVPropsImpl.class, primaryKey);

			if (webDAVProps == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, webDAVProps);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_WEBDAVPROPS_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (WebDAVProps webDAVProps : (List<WebDAVProps>)q.list()) {
				map.put(webDAVProps.getPrimaryKeyObj(), webDAVProps);

				cacheResult(webDAVProps);

				uncachedPrimaryKeys.remove(webDAVProps.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
					WebDAVPropsImpl.class, primaryKey, _nullWebDAVProps);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the web d a v propses.
	 *
	 * @return the web d a v propses
	 */
	@Override
	public List<WebDAVProps> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the web d a v propses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of web d a v propses
	 * @param end the upper bound of the range of web d a v propses (not inclusive)
	 * @return the range of web d a v propses
	 */
	@Override
	public List<WebDAVProps> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the web d a v propses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of web d a v propses
	 * @param end the upper bound of the range of web d a v propses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of web d a v propses
	 */
	@Override
	public List<WebDAVProps> findAll(int start, int end,
		OrderByComparator<WebDAVProps> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the web d a v propses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of web d a v propses
	 * @param end the upper bound of the range of web d a v propses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of web d a v propses
	 */
	@Override
	public List<WebDAVProps> findAll(int start, int end,
		OrderByComparator<WebDAVProps> orderByComparator,
		boolean retrieveFromCache) {
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

		List<WebDAVProps> list = null;

		if (retrieveFromCache) {
			list = (List<WebDAVProps>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_WEBDAVPROPS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WEBDAVPROPS;

				if (pagination) {
					sql = sql.concat(WebDAVPropsModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<WebDAVProps>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WebDAVProps>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the web d a v propses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WebDAVProps webDAVProps : findAll()) {
			remove(webDAVProps);
		}
	}

	/**
	 * Returns the number of web d a v propses.
	 *
	 * @return the number of web d a v propses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_WEBDAVPROPS);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WebDAVPropsModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the web d a v props persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(WebDAVPropsImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_WEBDAVPROPS = "SELECT webDAVProps FROM WebDAVProps webDAVProps";
	private static final String _SQL_SELECT_WEBDAVPROPS_WHERE_PKS_IN = "SELECT webDAVProps FROM WebDAVProps webDAVProps WHERE webDavPropsId IN (";
	private static final String _SQL_SELECT_WEBDAVPROPS_WHERE = "SELECT webDAVProps FROM WebDAVProps webDAVProps WHERE ";
	private static final String _SQL_COUNT_WEBDAVPROPS = "SELECT COUNT(webDAVProps) FROM WebDAVProps webDAVProps";
	private static final String _SQL_COUNT_WEBDAVPROPS_WHERE = "SELECT COUNT(webDAVProps) FROM WebDAVProps webDAVProps WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "webDAVProps.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WebDAVProps exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WebDAVProps exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(WebDAVPropsPersistenceImpl.class);
	private static final WebDAVProps _nullWebDAVProps = new WebDAVPropsImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<WebDAVProps> toCacheModel() {
				return _nullWebDAVPropsCacheModel;
			}
		};

	private static final CacheModel<WebDAVProps> _nullWebDAVPropsCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<WebDAVProps>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public WebDAVProps toEntityModel() {
			return _nullWebDAVProps;
		}
	}
}