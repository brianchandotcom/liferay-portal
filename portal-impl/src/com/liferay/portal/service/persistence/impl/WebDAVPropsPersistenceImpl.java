/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchWebDAVPropsException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WebDAVProps;
import com.liferay.portal.kernel.model.WebDAVPropsTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.kernel.service.persistence.WebDAVPropsUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.WebDAVPropsImpl;
import com.liferay.portal.model.impl.WebDAVPropsModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the web dav props service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WebDAVPropsPersistenceImpl
	extends BasePersistenceImpl<WebDAVProps> implements WebDAVPropsPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WebDAVPropsUtil</code> to access the web dav props persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WebDAVPropsImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the web dav props where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchWebDAVPropsException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching web dav props
	 * @throws NoSuchWebDAVPropsException if a matching web dav props could not be found
	 */
	@Override
	public WebDAVProps findByC_C(long classNameId, long classPK)
		throws NoSuchWebDAVPropsException {

		WebDAVProps webDAVProps = fetchByC_C(classNameId, classPK);

		if (webDAVProps == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchWebDAVPropsException(sb.toString());
		}

		return webDAVProps;
	}

	/**
	 * Returns the web dav props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching web dav props, or <code>null</code> if a matching web dav props could not be found
	 */
	@Override
	public WebDAVProps fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the web dav props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching web dav props, or <code>null</code> if a matching web dav props could not be found
	 */
	@Override
	public WebDAVProps fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof WebDAVProps) {
			WebDAVProps webDAVProps = (WebDAVProps)result;

			if ((classNameId != webDAVProps.getClassNameId()) ||
				(classPK != webDAVProps.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_WEBDAVPROPS_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<WebDAVProps> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					WebDAVProps webDAVProps = list.get(0);

					result = webDAVProps;

					cacheResult(webDAVProps);
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
			return (WebDAVProps)result;
		}
	}

	/**
	 * Removes the web dav props where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the web dav props that was removed
	 */
	@Override
	public WebDAVProps removeByC_C(long classNameId, long classPK)
		throws NoSuchWebDAVPropsException {

		WebDAVProps webDAVProps = findByC_C(classNameId, classPK);

		return remove(webDAVProps);
	}

	/**
	 * Returns the number of web dav propses where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching web dav propses
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WEBDAVPROPS_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"webDAVProps.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"webDAVProps.classPK = ?";

	public WebDAVPropsPersistenceImpl() {
		setModelClass(WebDAVProps.class);

		setModelImplClass(WebDAVPropsImpl.class);
		setModelPKClass(long.class);

		setTable(WebDAVPropsTable.INSTANCE);
	}

	/**
	 * Caches the web dav props in the entity cache if it is enabled.
	 *
	 * @param webDAVProps the web dav props
	 */
	@Override
	public void cacheResult(WebDAVProps webDAVProps) {
		EntityCacheUtil.putResult(
			WebDAVPropsImpl.class, webDAVProps.getPrimaryKey(), webDAVProps);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				webDAVProps.getClassNameId(), webDAVProps.getClassPK()
			},
			webDAVProps);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the web dav propses in the entity cache if it is enabled.
	 *
	 * @param webDAVPropses the web dav propses
	 */
	@Override
	public void cacheResult(List<WebDAVProps> webDAVPropses) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (webDAVPropses.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (WebDAVProps webDAVProps : webDAVPropses) {
			if (EntityCacheUtil.getResult(
					WebDAVPropsImpl.class, webDAVProps.getPrimaryKey()) ==
						null) {

				cacheResult(webDAVProps);
			}
		}
	}

	/**
	 * Clears the cache for all web dav propses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(WebDAVPropsImpl.class);

		FinderCacheUtil.clearCache(WebDAVPropsImpl.class);
	}

	/**
	 * Clears the cache for the web dav props.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WebDAVProps webDAVProps) {
		EntityCacheUtil.removeResult(WebDAVPropsImpl.class, webDAVProps);
	}

	@Override
	public void clearCache(List<WebDAVProps> webDAVPropses) {
		for (WebDAVProps webDAVProps : webDAVPropses) {
			EntityCacheUtil.removeResult(WebDAVPropsImpl.class, webDAVProps);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(WebDAVPropsImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(WebDAVPropsImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		WebDAVPropsModelImpl webDAVPropsModelImpl) {

		Object[] args = new Object[] {
			webDAVPropsModelImpl.getClassNameId(),
			webDAVPropsModelImpl.getClassPK()
		};

		FinderCacheUtil.putResult(_finderPathCountByC_C, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByC_C, args, webDAVPropsModelImpl);
	}

	/**
	 * Creates a new web dav props with the primary key. Does not add the web dav props to the database.
	 *
	 * @param webDavPropsId the primary key for the new web dav props
	 * @return the new web dav props
	 */
	@Override
	public WebDAVProps create(long webDavPropsId) {
		WebDAVProps webDAVProps = new WebDAVPropsImpl();

		webDAVProps.setNew(true);
		webDAVProps.setPrimaryKey(webDavPropsId);

		webDAVProps.setCompanyId(CompanyThreadLocal.getCompanyId());

		return webDAVProps;
	}

	/**
	 * Removes the web dav props with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webDavPropsId the primary key of the web dav props
	 * @return the web dav props that was removed
	 * @throws NoSuchWebDAVPropsException if a web dav props with the primary key could not be found
	 */
	@Override
	public WebDAVProps remove(long webDavPropsId)
		throws NoSuchWebDAVPropsException {

		return remove((Serializable)webDavPropsId);
	}

	/**
	 * Removes the web dav props with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the web dav props
	 * @return the web dav props that was removed
	 * @throws NoSuchWebDAVPropsException if a web dav props with the primary key could not be found
	 */
	@Override
	public WebDAVProps remove(Serializable primaryKey)
		throws NoSuchWebDAVPropsException {

		Session session = null;

		try {
			session = openSession();

			WebDAVProps webDAVProps = (WebDAVProps)session.get(
				WebDAVPropsImpl.class, primaryKey);

			if (webDAVProps == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWebDAVPropsException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(webDAVProps);
		}
		catch (NoSuchWebDAVPropsException noSuchEntityException) {
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
	protected WebDAVProps removeImpl(WebDAVProps webDAVProps) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(webDAVProps)) {
				webDAVProps = (WebDAVProps)session.get(
					WebDAVPropsImpl.class, webDAVProps.getPrimaryKeyObj());
			}

			if (webDAVProps != null) {
				session.delete(webDAVProps);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
		boolean isNew = webDAVProps.isNew();

		if (!(webDAVProps instanceof WebDAVPropsModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(webDAVProps.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(webDAVProps);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in webDAVProps proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom WebDAVProps implementation " +
					webDAVProps.getClass());
		}

		WebDAVPropsModelImpl webDAVPropsModelImpl =
			(WebDAVPropsModelImpl)webDAVProps;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (webDAVProps.getCreateDate() == null)) {
			if (serviceContext == null) {
				webDAVProps.setCreateDate(date);
			}
			else {
				webDAVProps.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!webDAVPropsModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				webDAVProps.setModifiedDate(date);
			}
			else {
				webDAVProps.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(webDAVProps);
			}
			else {
				webDAVProps = (WebDAVProps)session.merge(webDAVProps);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			WebDAVPropsImpl.class, webDAVPropsModelImpl, false, true);

		cacheUniqueFindersCache(webDAVPropsModelImpl);

		if (isNew) {
			webDAVProps.setNew(false);
		}

		webDAVProps.resetOriginalValues();

		return webDAVProps;
	}

	/**
	 * Returns the web dav props with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the web dav props
	 * @return the web dav props
	 * @throws NoSuchWebDAVPropsException if a web dav props with the primary key could not be found
	 */
	@Override
	public WebDAVProps findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWebDAVPropsException {

		WebDAVProps webDAVProps = fetchByPrimaryKey(primaryKey);

		if (webDAVProps == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchWebDAVPropsException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return webDAVProps;
	}

	/**
	 * Returns the web dav props with the primary key or throws a <code>NoSuchWebDAVPropsException</code> if it could not be found.
	 *
	 * @param webDavPropsId the primary key of the web dav props
	 * @return the web dav props
	 * @throws NoSuchWebDAVPropsException if a web dav props with the primary key could not be found
	 */
	@Override
	public WebDAVProps findByPrimaryKey(long webDavPropsId)
		throws NoSuchWebDAVPropsException {

		return findByPrimaryKey((Serializable)webDavPropsId);
	}

	/**
	 * Returns the web dav props with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webDavPropsId the primary key of the web dav props
	 * @return the web dav props, or <code>null</code> if a web dav props with the primary key could not be found
	 */
	@Override
	public WebDAVProps fetchByPrimaryKey(long webDavPropsId) {
		return fetchByPrimaryKey((Serializable)webDavPropsId);
	}

	/**
	 * Returns all the web dav propses.
	 *
	 * @return the web dav propses
	 */
	@Override
	public List<WebDAVProps> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the web dav propses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebDAVPropsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web dav propses
	 * @param end the upper bound of the range of web dav propses (not inclusive)
	 * @return the range of web dav propses
	 */
	@Override
	public List<WebDAVProps> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the web dav propses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebDAVPropsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web dav propses
	 * @param end the upper bound of the range of web dav propses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of web dav propses
	 */
	@Override
	public List<WebDAVProps> findAll(
		int start, int end, OrderByComparator<WebDAVProps> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the web dav propses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebDAVPropsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web dav propses
	 * @param end the upper bound of the range of web dav propses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of web dav propses
	 */
	@Override
	public List<WebDAVProps> findAll(
		int start, int end, OrderByComparator<WebDAVProps> orderByComparator,
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

		List<WebDAVProps> list = null;

		if (useFinderCache) {
			list = (List<WebDAVProps>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_WEBDAVPROPS);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_WEBDAVPROPS;

				sql = sql.concat(WebDAVPropsModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<WebDAVProps>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the web dav propses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WebDAVProps webDAVProps : findAll()) {
			remove(webDAVProps);
		}
	}

	/**
	 * Returns the number of web dav propses.
	 *
	 * @return the number of web dav propses
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_WEBDAVPROPS);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "webDavPropsId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WEBDAVPROPS;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WebDAVPropsModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the web dav props persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		WebDAVPropsUtil.setPersistence(this);
	}

	public void destroy() {
		WebDAVPropsUtil.setPersistence(null);

		EntityCacheUtil.removeCache(WebDAVPropsImpl.class.getName());
	}

	private static final String _SQL_SELECT_WEBDAVPROPS =
		"SELECT webDAVProps FROM WebDAVProps webDAVProps";

	private static final String _SQL_SELECT_WEBDAVPROPS_WHERE =
		"SELECT webDAVProps FROM WebDAVProps webDAVProps WHERE ";

	private static final String _SQL_COUNT_WEBDAVPROPS =
		"SELECT COUNT(webDAVProps) FROM WebDAVProps webDAVProps";

	private static final String _SQL_COUNT_WEBDAVPROPS_WHERE =
		"SELECT COUNT(webDAVProps) FROM WebDAVProps webDAVProps WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "webDAVProps.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No WebDAVProps exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No WebDAVProps exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WebDAVPropsPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}