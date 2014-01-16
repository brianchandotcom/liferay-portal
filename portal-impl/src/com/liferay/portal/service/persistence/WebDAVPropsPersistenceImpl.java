/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchWebDAVPropsException;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.WebDAVProps;
import com.liferay.portal.model.impl.WebDAVPropsImpl;
import com.liferay.portal.model.impl.WebDAVPropsModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the web d a v props service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsPersistence
 * @see WebDAVPropsUtil
 * @generated
 */
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
	 * Returns the web d a v props where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchWebDAVPropsException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching web d a v props
	 * @throws com.liferay.portal.NoSuchWebDAVPropsException if a matching web d a v props could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WebDAVProps findByC_C(long classNameId, long classPK)
		throws NoSuchWebDAVPropsException, SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WebDAVProps fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the web d a v props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching web d a v props, or <code>null</code> if a matching web d a v props could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WebDAVProps fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
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
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					WebDAVProps webDAVProps = list.get(0);

					result = webDAVProps;

					cacheResult(webDAVProps);

					if ((webDAVProps.getClassNameId() != classNameId) ||
							(webDAVProps.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, webDAVProps);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
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
			return (WebDAVProps)result;
		}
	}

	/**
	 * Removes the web d a v props where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the web d a v props that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WebDAVProps removeByC_C(long classNameId, long classPK)
		throws NoSuchWebDAVPropsException, SystemException {
		WebDAVProps webDAVProps = findByC_C(classNameId, classPK);

		return remove(webDAVProps);
	}

	/**
	 * Returns the number of web d a v propses where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching web d a v propses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

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
		EntityCacheUtil.putResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsImpl.class, webDAVProps.getPrimaryKey(), webDAVProps);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
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
			if (EntityCacheUtil.getResult(
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
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(WebDAVPropsImpl.class.getName());
		}

		EntityCacheUtil.clearCache(WebDAVPropsImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the web d a v props.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WebDAVProps webDAVProps) {
		EntityCacheUtil.removeResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsImpl.class, webDAVProps.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(webDAVProps);
	}

	@Override
	public void clearCache(List<WebDAVProps> webDAVPropses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WebDAVProps webDAVProps : webDAVPropses) {
			EntityCacheUtil.removeResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
				WebDAVPropsImpl.class, webDAVProps.getPrimaryKey());

			clearUniqueFindersCache(webDAVProps);
		}
	}

	protected void cacheUniqueFindersCache(WebDAVProps webDAVProps) {
		if (webDAVProps.isNew()) {
			Object[] args = new Object[] {
					webDAVProps.getClassNameId(), webDAVProps.getClassPK()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C, args,
				webDAVProps);
		}
		else {
			WebDAVPropsModelImpl webDAVPropsModelImpl = (WebDAVPropsModelImpl)webDAVProps;

			if ((webDAVPropsModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						webDAVProps.getClassNameId(), webDAVProps.getClassPK()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C, args,
					webDAVProps);
			}
		}
	}

	protected void clearUniqueFindersCache(WebDAVProps webDAVProps) {
		WebDAVPropsModelImpl webDAVPropsModelImpl = (WebDAVPropsModelImpl)webDAVProps;

		Object[] args = new Object[] {
				webDAVProps.getClassNameId(), webDAVProps.getClassPK()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C, args);

		if ((webDAVPropsModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
			args = new Object[] {
					webDAVPropsModelImpl.getOriginalClassNameId(),
					webDAVPropsModelImpl.getOriginalClassPK()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
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
	 * @throws com.liferay.portal.NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WebDAVProps remove(long webDavPropsId)
		throws NoSuchWebDAVPropsException, SystemException {
		return remove((Serializable)webDavPropsId);
	}

	/**
	 * Removes the web d a v props with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the web d a v props
	 * @return the web d a v props that was removed
	 * @throws com.liferay.portal.NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WebDAVProps remove(Serializable primaryKey)
		throws NoSuchWebDAVPropsException, SystemException {
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
	protected WebDAVProps removeImpl(WebDAVProps webDAVProps)
		throws SystemException {
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
	public WebDAVProps updateImpl(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws SystemException {
		webDAVProps = toUnwrappedModel(webDAVProps);

		boolean isNew = webDAVProps.isNew();

		Session session = null;

		try {
			session = openSession();

			if (webDAVProps.isNew()) {
				session.save(webDAVProps);

				webDAVProps.setNew(false);
			}
			else {
				session.merge(webDAVProps);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !WebDAVPropsModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsImpl.class, webDAVProps.getPrimaryKey(), webDAVProps);

		clearUniqueFindersCache(webDAVProps);
		cacheUniqueFindersCache(webDAVProps);

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

		webDAVPropsImpl.setWebDavPropsId(webDAVProps.getWebDavPropsId());
		webDAVPropsImpl.setCompanyId(webDAVProps.getCompanyId());
		webDAVPropsImpl.setCreateDate(webDAVProps.getCreateDate());
		webDAVPropsImpl.setModifiedDate(webDAVProps.getModifiedDate());
		webDAVPropsImpl.setClassNameId(webDAVProps.getClassNameId());
		webDAVPropsImpl.setClassPK(webDAVProps.getClassPK());
		webDAVPropsImpl.setProps(webDAVProps.getProps());
		webDAVPropsImpl.setMvccVersion(webDAVProps.getMvccVersion());

		return webDAVPropsImpl;
	}

	/**
	 * Returns the web d a v props with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the web d a v props
	 * @return the web d a v props
	 * @throws com.liferay.portal.NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WebDAVProps findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWebDAVPropsException, SystemException {
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
	 * Returns the web d a v props with the primary key or throws a {@link com.liferay.portal.NoSuchWebDAVPropsException} if it could not be found.
	 *
	 * @param webDavPropsId the primary key of the web d a v props
	 * @return the web d a v props
	 * @throws com.liferay.portal.NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WebDAVProps findByPrimaryKey(long webDavPropsId)
		throws NoSuchWebDAVPropsException, SystemException {
		return findByPrimaryKey((Serializable)webDavPropsId);
	}

	/**
	 * Returns the web d a v props with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the web d a v props
	 * @return the web d a v props, or <code>null</code> if a web d a v props with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WebDAVProps fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		WebDAVProps webDAVProps = (WebDAVProps)EntityCacheUtil.getResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
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
					EntityCacheUtil.putResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
						WebDAVPropsImpl.class, primaryKey, _nullWebDAVProps);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WebDAVProps fetchByPrimaryKey(long webDavPropsId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)webDavPropsId);
	}

	/**
	 * Returns all the web d a v propses.
	 *
	 * @return the web d a v propses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<WebDAVProps> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the web d a v propses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of web d a v propses
	 * @param end the upper bound of the range of web d a v propses (not inclusive)
	 * @return the range of web d a v propses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<WebDAVProps> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the web d a v propses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of web d a v propses
	 * @param end the upper bound of the range of web d a v propses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of web d a v propses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<WebDAVProps> findAll(int start, int end,
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

		List<WebDAVProps> list = (List<WebDAVProps>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

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

					list = new UnmodifiableList<WebDAVProps>(list);
				}
				else {
					list = (List<WebDAVProps>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the web d a v propses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (WebDAVProps webDAVProps : findAll()) {
			remove(webDAVProps);
		}
	}

	/**
	 * Returns the number of web d a v propses.
	 *
	 * @return the number of web d a v propses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_WEBDAVPROPS);

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
	 * Initializes the web d a v props persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.WebDAVProps")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<WebDAVProps>> listenersList = new ArrayList<ModelListener<WebDAVProps>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<WebDAVProps>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(WebDAVPropsImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_WEBDAVPROPS = "SELECT webDAVProps FROM WebDAVProps webDAVProps";
	private static final String _SQL_SELECT_WEBDAVPROPS_WHERE = "SELECT webDAVProps FROM WebDAVProps webDAVProps WHERE ";
	private static final String _SQL_COUNT_WEBDAVPROPS = "SELECT COUNT(webDAVProps) FROM WebDAVProps webDAVProps";
	private static final String _SQL_COUNT_WEBDAVPROPS_WHERE = "SELECT COUNT(webDAVProps) FROM WebDAVProps webDAVProps WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "webDAVProps.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WebDAVProps exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WebDAVProps exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(WebDAVPropsPersistenceImpl.class);
	private static WebDAVProps _nullWebDAVProps = new WebDAVPropsImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<WebDAVProps> toCacheModel() {
				return _nullWebDAVPropsCacheModel;
			}
		};

	private static CacheModel<WebDAVProps> _nullWebDAVPropsCacheModel = new CacheModel<WebDAVProps>() {
			@Override
			public WebDAVProps toEntityModel() {
				return _nullWebDAVProps;
			}
		};
}