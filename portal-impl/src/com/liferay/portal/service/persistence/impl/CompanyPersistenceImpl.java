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

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.CompanyModelImpl;
import com.liferay.portal.service.persistence.CompanyPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the company service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompanyPersistence
 * @see CompanyUtil
 * @generated
 */
public class CompanyPersistenceImpl extends BasePersistenceImpl<Company>
	implements CompanyPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CompanyUtil} to access the company persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CompanyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_WEBID = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByWebId",
			new String[] { String.class.getName() },
			CompanyModelImpl.WEBID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_WEBID = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByWebId",
			new String[] { String.class.getName() });

	/**
	 * Returns the company where webId = &#63; or throws a {@link com.liferay.portal.NoSuchCompanyException} if it could not be found.
	 *
	 * @param webId the web ID
	 * @return the matching company
	 * @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	 */
	@Override
	public Company findByWebId(String webId) throws NoSuchCompanyException {
		Company company = fetchByWebId(webId);

		if (company == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("webId=");
			msg.append(webId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	/**
	 * Returns the company where webId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param webId the web ID
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchByWebId(String webId) {
		return fetchByWebId(webId, true);
	}

	/**
	 * Returns the company where webId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param webId the web ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchByWebId(String webId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { webId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_WEBID,
					finderArgs, this);
		}

		if (result instanceof Company) {
			Company company = (Company)result;

			if (!Validator.equals(webId, company.getWebId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COMPANY_WHERE);

			boolean bindWebId = false;

			if (webId == null) {
				query.append(_FINDER_COLUMN_WEBID_WEBID_1);
			}
			else if (webId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_WEBID_WEBID_3);
			}
			else {
				bindWebId = true;

				query.append(_FINDER_COLUMN_WEBID_WEBID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindWebId) {
					qPos.add(webId);
				}

				List<Company> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID,
						finderArgs, list);
				}
				else {
					Company company = list.get(0);

					result = company;

					cacheResult(company);

					if ((company.getWebId() == null) ||
							!company.getWebId().equals(webId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID,
							finderArgs, company);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_WEBID,
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
			return (Company)result;
		}
	}

	/**
	 * Removes the company where webId = &#63; from the database.
	 *
	 * @param webId the web ID
	 * @return the company that was removed
	 */
	@Override
	public Company removeByWebId(String webId) throws NoSuchCompanyException {
		Company company = findByWebId(webId);

		return remove(company);
	}

	/**
	 * Returns the number of companies where webId = &#63;.
	 *
	 * @param webId the web ID
	 * @return the number of matching companies
	 */
	@Override
	public int countByWebId(String webId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_WEBID;

		Object[] finderArgs = new Object[] { webId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMPANY_WHERE);

			boolean bindWebId = false;

			if (webId == null) {
				query.append(_FINDER_COLUMN_WEBID_WEBID_1);
			}
			else if (webId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_WEBID_WEBID_3);
			}
			else {
				bindWebId = true;

				query.append(_FINDER_COLUMN_WEBID_WEBID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindWebId) {
					qPos.add(webId);
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

	private static final String _FINDER_COLUMN_WEBID_WEBID_1 = "company.webId IS NULL";
	private static final String _FINDER_COLUMN_WEBID_WEBID_2 = "company.webId = ?";
	private static final String _FINDER_COLUMN_WEBID_WEBID_3 = "(company.webId IS NULL OR company.webId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_MX = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByMx",
			new String[] { String.class.getName() },
			CompanyModelImpl.MX_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_MX = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMx",
			new String[] { String.class.getName() });

	/**
	 * Returns the company where mx = &#63; or throws a {@link com.liferay.portal.NoSuchCompanyException} if it could not be found.
	 *
	 * @param mx the mx
	 * @return the matching company
	 * @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	 */
	@Override
	public Company findByMx(String mx) throws NoSuchCompanyException {
		Company company = fetchByMx(mx);

		if (company == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("mx=");
			msg.append(mx);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	/**
	 * Returns the company where mx = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param mx the mx
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchByMx(String mx) {
		return fetchByMx(mx, true);
	}

	/**
	 * Returns the company where mx = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param mx the mx
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchByMx(String mx, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { mx };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_MX,
					finderArgs, this);
		}

		if (result instanceof Company) {
			Company company = (Company)result;

			if (!Validator.equals(mx, company.getMx())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COMPANY_WHERE);

			boolean bindMx = false;

			if (mx == null) {
				query.append(_FINDER_COLUMN_MX_MX_1);
			}
			else if (mx.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_MX_MX_3);
			}
			else {
				bindMx = true;

				query.append(_FINDER_COLUMN_MX_MX_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMx) {
					qPos.add(mx);
				}

				List<Company> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MX,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"CompanyPersistenceImpl.fetchByMx(String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Company company = list.get(0);

					result = company;

					cacheResult(company);

					if ((company.getMx() == null) ||
							!company.getMx().equals(mx)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MX,
							finderArgs, company);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MX, finderArgs);

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
			return (Company)result;
		}
	}

	/**
	 * Removes the company where mx = &#63; from the database.
	 *
	 * @param mx the mx
	 * @return the company that was removed
	 */
	@Override
	public Company removeByMx(String mx) throws NoSuchCompanyException {
		Company company = findByMx(mx);

		return remove(company);
	}

	/**
	 * Returns the number of companies where mx = &#63;.
	 *
	 * @param mx the mx
	 * @return the number of matching companies
	 */
	@Override
	public int countByMx(String mx) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_MX;

		Object[] finderArgs = new Object[] { mx };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMPANY_WHERE);

			boolean bindMx = false;

			if (mx == null) {
				query.append(_FINDER_COLUMN_MX_MX_1);
			}
			else if (mx.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_MX_MX_3);
			}
			else {
				bindMx = true;

				query.append(_FINDER_COLUMN_MX_MX_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMx) {
					qPos.add(mx);
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

	private static final String _FINDER_COLUMN_MX_MX_1 = "company.mx IS NULL";
	private static final String _FINDER_COLUMN_MX_MX_2 = "company.mx = ?";
	private static final String _FINDER_COLUMN_MX_MX_3 = "(company.mx IS NULL OR company.mx = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_LOGOID = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByLogoId",
			new String[] { Long.class.getName() },
			CompanyModelImpl.LOGOID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LOGOID = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLogoId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the company where logoId = &#63; or throws a {@link com.liferay.portal.NoSuchCompanyException} if it could not be found.
	 *
	 * @param logoId the logo ID
	 * @return the matching company
	 * @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	 */
	@Override
	public Company findByLogoId(long logoId) throws NoSuchCompanyException {
		Company company = fetchByLogoId(logoId);

		if (company == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("logoId=");
			msg.append(logoId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	/**
	 * Returns the company where logoId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param logoId the logo ID
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchByLogoId(long logoId) {
		return fetchByLogoId(logoId, true);
	}

	/**
	 * Returns the company where logoId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param logoId the logo ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchByLogoId(long logoId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { logoId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_LOGOID,
					finderArgs, this);
		}

		if (result instanceof Company) {
			Company company = (Company)result;

			if ((logoId != company.getLogoId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COMPANY_WHERE);

			query.append(_FINDER_COLUMN_LOGOID_LOGOID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(logoId);

				List<Company> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LOGOID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"CompanyPersistenceImpl.fetchByLogoId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Company company = list.get(0);

					result = company;

					cacheResult(company);

					if ((company.getLogoId() != logoId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LOGOID,
							finderArgs, company);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LOGOID,
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
			return (Company)result;
		}
	}

	/**
	 * Removes the company where logoId = &#63; from the database.
	 *
	 * @param logoId the logo ID
	 * @return the company that was removed
	 */
	@Override
	public Company removeByLogoId(long logoId) throws NoSuchCompanyException {
		Company company = findByLogoId(logoId);

		return remove(company);
	}

	/**
	 * Returns the number of companies where logoId = &#63;.
	 *
	 * @param logoId the logo ID
	 * @return the number of matching companies
	 */
	@Override
	public int countByLogoId(long logoId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LOGOID;

		Object[] finderArgs = new Object[] { logoId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMPANY_WHERE);

			query.append(_FINDER_COLUMN_LOGOID_LOGOID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(logoId);

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

	private static final String _FINDER_COLUMN_LOGOID_LOGOID_2 = "company.logoId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SYSTEM = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySystem",
			new String[] {
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYSTEM =
		new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySystem",
			new String[] { Boolean.class.getName() },
			CompanyModelImpl.SYSTEM_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SYSTEM = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySystem",
			new String[] { Boolean.class.getName() });

	/**
	 * Returns all the companies where system = &#63;.
	 *
	 * @param system the system
	 * @return the matching companies
	 */
	@Override
	public List<Company> findBySystem(boolean system) {
		return findBySystem(system, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the companies where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CompanyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of companies
	 * @param end the upper bound of the range of companies (not inclusive)
	 * @return the range of matching companies
	 */
	@Override
	public List<Company> findBySystem(boolean system, int start, int end) {
		return findBySystem(system, start, end, null);
	}

	/**
	 * Returns an ordered range of all the companies where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CompanyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of companies
	 * @param end the upper bound of the range of companies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching companies
	 */
	@Override
	public List<Company> findBySystem(boolean system, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYSTEM;
			finderArgs = new Object[] { system };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SYSTEM;
			finderArgs = new Object[] { system, start, end, orderByComparator };
		}

		List<Company> list = (List<Company>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Company company : list) {
				if ((system != company.getSystem())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_COMPANY_WHERE);

			query.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CompanyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(system);

				if (!pagination) {
					list = (List<Company>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Company>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first company in the ordered set where system = &#63;.
	 *
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching company
	 * @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	 */
	@Override
	public Company findBySystem_First(boolean system,
		OrderByComparator orderByComparator) throws NoSuchCompanyException {
		Company company = fetchBySystem_First(system, orderByComparator);

		if (company != null) {
			return company;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("system=");
		msg.append(system);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCompanyException(msg.toString());
	}

	/**
	 * Returns the first company in the ordered set where system = &#63;.
	 *
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchBySystem_First(boolean system,
		OrderByComparator orderByComparator) {
		List<Company> list = findBySystem(system, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last company in the ordered set where system = &#63;.
	 *
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching company
	 * @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	 */
	@Override
	public Company findBySystem_Last(boolean system,
		OrderByComparator orderByComparator) throws NoSuchCompanyException {
		Company company = fetchBySystem_Last(system, orderByComparator);

		if (company != null) {
			return company;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("system=");
		msg.append(system);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCompanyException(msg.toString());
	}

	/**
	 * Returns the last company in the ordered set where system = &#63;.
	 *
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchBySystem_Last(boolean system,
		OrderByComparator orderByComparator) {
		int count = countBySystem(system);

		if (count == 0) {
			return null;
		}

		List<Company> list = findBySystem(system, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the companies before and after the current company in the ordered set where system = &#63;.
	 *
	 * @param companyId the primary key of the current company
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next company
	 * @throws com.liferay.portal.NoSuchCompanyException if a company with the primary key could not be found
	 */
	@Override
	public Company[] findBySystem_PrevAndNext(long companyId, boolean system,
		OrderByComparator orderByComparator) throws NoSuchCompanyException {
		Company company = findByPrimaryKey(companyId);

		Session session = null;

		try {
			session = openSession();

			Company[] array = new CompanyImpl[3];

			array[0] = getBySystem_PrevAndNext(session, company, system,
					orderByComparator, true);

			array[1] = company;

			array[2] = getBySystem_PrevAndNext(session, company, system,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Company getBySystem_PrevAndNext(Session session, Company company,
		boolean system, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMPANY_WHERE);

		query.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CompanyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(system);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(company);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Company> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the companies where system = &#63; from the database.
	 *
	 * @param system the system
	 */
	@Override
	public void removeBySystem(boolean system) {
		for (Company company : findBySystem(system, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(company);
		}
	}

	/**
	 * Returns the number of companies where system = &#63;.
	 *
	 * @param system the system
	 * @return the number of matching companies
	 */
	@Override
	public int countBySystem(boolean system) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SYSTEM;

		Object[] finderArgs = new Object[] { system };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMPANY_WHERE);

			query.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(system);

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

	private static final String _FINDER_COLUMN_SYSTEM_SYSTEM_2 = "company.system = ?";

	public CompanyPersistenceImpl() {
		setModelClass(Company.class);
	}

	/**
	 * Caches the company in the entity cache if it is enabled.
	 *
	 * @param company the company
	 */
	@Override
	public void cacheResult(Company company) {
		EntityCacheUtil.putResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyImpl.class, company.getPrimaryKey(), company);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID,
			new Object[] { company.getWebId() }, company);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MX,
			new Object[] { company.getMx() }, company);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LOGOID,
			new Object[] { company.getLogoId() }, company);

		company.resetOriginalValues();
	}

	/**
	 * Caches the companies in the entity cache if it is enabled.
	 *
	 * @param companies the companies
	 */
	@Override
	public void cacheResult(List<Company> companies) {
		for (Company company : companies) {
			if (EntityCacheUtil.getResult(
						CompanyModelImpl.ENTITY_CACHE_ENABLED,
						CompanyImpl.class, company.getPrimaryKey()) == null) {
				cacheResult(company);
			}
			else {
				company.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all companies.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CompanyImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CompanyImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the company.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Company company) {
		EntityCacheUtil.removeResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyImpl.class, company.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(company);
	}

	@Override
	public void clearCache(List<Company> companies) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Company company : companies) {
			EntityCacheUtil.removeResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyImpl.class, company.getPrimaryKey());

			clearUniqueFindersCache(company);
		}
	}

	protected void cacheUniqueFindersCache(Company company) {
		if (company.isNew()) {
			Object[] args = new Object[] { company.getWebId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_WEBID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID, args, company);

			args = new Object[] { company.getMx() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MX, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MX, args, company);

			args = new Object[] { company.getLogoId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LOGOID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LOGOID, args, company);
		}
		else {
			CompanyModelImpl companyModelImpl = (CompanyModelImpl)company;

			if ((companyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_WEBID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { company.getWebId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_WEBID, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID, args,
					company);
			}

			if ((companyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_MX.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { company.getMx() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MX, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MX, args, company);
			}

			if ((companyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_LOGOID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { company.getLogoId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LOGOID, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LOGOID, args,
					company);
			}
		}
	}

	protected void clearUniqueFindersCache(Company company) {
		CompanyModelImpl companyModelImpl = (CompanyModelImpl)company;

		Object[] args = new Object[] { company.getWebId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_WEBID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_WEBID, args);

		if ((companyModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_WEBID.getColumnBitmask()) != 0) {
			args = new Object[] { companyModelImpl.getOriginalWebId() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_WEBID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_WEBID, args);
		}

		args = new Object[] { company.getMx() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_MX, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MX, args);

		if ((companyModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_MX.getColumnBitmask()) != 0) {
			args = new Object[] { companyModelImpl.getOriginalMx() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_MX, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MX, args);
		}

		args = new Object[] { company.getLogoId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LOGOID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LOGOID, args);

		if ((companyModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_LOGOID.getColumnBitmask()) != 0) {
			args = new Object[] { companyModelImpl.getOriginalLogoId() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LOGOID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LOGOID, args);
		}
	}

	/**
	 * Creates a new company with the primary key. Does not add the company to the database.
	 *
	 * @param companyId the primary key for the new company
	 * @return the new company
	 */
	@Override
	public Company create(long companyId) {
		Company company = new CompanyImpl();

		company.setNew(true);
		company.setPrimaryKey(companyId);

		return company;
	}

	/**
	 * Removes the company with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param companyId the primary key of the company
	 * @return the company that was removed
	 * @throws com.liferay.portal.NoSuchCompanyException if a company with the primary key could not be found
	 */
	@Override
	public Company remove(long companyId) throws NoSuchCompanyException {
		return remove((Serializable)companyId);
	}

	/**
	 * Removes the company with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the company
	 * @return the company that was removed
	 * @throws com.liferay.portal.NoSuchCompanyException if a company with the primary key could not be found
	 */
	@Override
	public Company remove(Serializable primaryKey)
		throws NoSuchCompanyException {
		Session session = null;

		try {
			session = openSession();

			Company company = (Company)session.get(CompanyImpl.class, primaryKey);

			if (company == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCompanyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(company);
		}
		catch (NoSuchCompanyException nsee) {
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
	protected Company removeImpl(Company company) {
		company = toUnwrappedModel(company);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(company)) {
				company = (Company)session.get(CompanyImpl.class,
						company.getPrimaryKeyObj());
			}

			if (company != null) {
				session.delete(company);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (company != null) {
			clearCache(company);
		}

		return company;
	}

	@Override
	public Company updateImpl(com.liferay.portal.model.Company company) {
		company = toUnwrappedModel(company);

		boolean isNew = company.isNew();

		CompanyModelImpl companyModelImpl = (CompanyModelImpl)company;

		Session session = null;

		try {
			session = openSession();

			if (company.isNew()) {
				session.save(company);

				company.setNew(false);
			}
			else {
				session.merge(company);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CompanyModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((companyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYSTEM.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						companyModelImpl.getOriginalSystem()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SYSTEM, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYSTEM,
					args);

				args = new Object[] { companyModelImpl.getSystem() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SYSTEM, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYSTEM,
					args);
			}
		}

		EntityCacheUtil.putResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyImpl.class, company.getPrimaryKey(), company, false);

		clearUniqueFindersCache(company);
		cacheUniqueFindersCache(company);

		company.resetOriginalValues();

		return company;
	}

	protected Company toUnwrappedModel(Company company) {
		if (company instanceof CompanyImpl) {
			return company;
		}

		CompanyImpl companyImpl = new CompanyImpl();

		companyImpl.setNew(company.isNew());
		companyImpl.setPrimaryKey(company.getPrimaryKey());

		companyImpl.setMvccVersion(company.getMvccVersion());
		companyImpl.setCompanyId(company.getCompanyId());
		companyImpl.setAccountId(company.getAccountId());
		companyImpl.setWebId(company.getWebId());
		companyImpl.setKey(company.getKey());
		companyImpl.setMx(company.getMx());
		companyImpl.setHomeURL(company.getHomeURL());
		companyImpl.setLogoId(company.getLogoId());
		companyImpl.setSystem(company.isSystem());
		companyImpl.setMaxUsers(company.getMaxUsers());
		companyImpl.setActive(company.isActive());

		return companyImpl;
	}

	/**
	 * Returns the company with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the company
	 * @return the company
	 * @throws com.liferay.portal.NoSuchCompanyException if a company with the primary key could not be found
	 */
	@Override
	public Company findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCompanyException {
		Company company = fetchByPrimaryKey(primaryKey);

		if (company == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCompanyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return company;
	}

	/**
	 * Returns the company with the primary key or throws a {@link com.liferay.portal.NoSuchCompanyException} if it could not be found.
	 *
	 * @param companyId the primary key of the company
	 * @return the company
	 * @throws com.liferay.portal.NoSuchCompanyException if a company with the primary key could not be found
	 */
	@Override
	public Company findByPrimaryKey(long companyId)
		throws NoSuchCompanyException {
		return findByPrimaryKey((Serializable)companyId);
	}

	/**
	 * Returns the company with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the company
	 * @return the company, or <code>null</code> if a company with the primary key could not be found
	 */
	@Override
	public Company fetchByPrimaryKey(Serializable primaryKey) {
		Company company = (Company)EntityCacheUtil.getResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyImpl.class, primaryKey);

		if (company == _nullCompany) {
			return null;
		}

		if (company == null) {
			Session session = null;

			try {
				session = openSession();

				company = (Company)session.get(CompanyImpl.class, primaryKey);

				if (company != null) {
					cacheResult(company);
				}
				else {
					EntityCacheUtil.putResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
						CompanyImpl.class, primaryKey, _nullCompany);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
					CompanyImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return company;
	}

	/**
	 * Returns the company with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param companyId the primary key of the company
	 * @return the company, or <code>null</code> if a company with the primary key could not be found
	 */
	@Override
	public Company fetchByPrimaryKey(long companyId) {
		return fetchByPrimaryKey((Serializable)companyId);
	}

	@Override
	public Map<Serializable, Company> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Company> map = new HashMap<Serializable, Company>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Company company = fetchByPrimaryKey(primaryKey);

			if (company != null) {
				map.put(primaryKey, company);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Company company = (Company)EntityCacheUtil.getResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
					CompanyImpl.class, primaryKey);

			if (company == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, company);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMPANY_WHERE_PKS_IN);

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

			for (Company company : (List<Company>)q.list()) {
				map.put(company.getPrimaryKeyObj(), company);

				cacheResult(company);

				uncachedPrimaryKeys.remove(company.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
					CompanyImpl.class, primaryKey, _nullCompany);
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
	 * Returns all the companies.
	 *
	 * @return the companies
	 */
	@Override
	public List<Company> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the companies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CompanyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of companies
	 * @param end the upper bound of the range of companies (not inclusive)
	 * @return the range of companies
	 */
	@Override
	public List<Company> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the companies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CompanyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of companies
	 * @param end the upper bound of the range of companies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of companies
	 */
	@Override
	public List<Company> findAll(int start, int end,
		OrderByComparator orderByComparator) {
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

		List<Company> list = (List<Company>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COMPANY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMPANY;

				if (pagination) {
					sql = sql.concat(CompanyModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Company>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Company>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the companies from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Company company : findAll()) {
			remove(company);
		}
	}

	/**
	 * Returns the number of companies.
	 *
	 * @return the number of companies
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMPANY);

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

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the company persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Company")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Company>> listenersList = new ArrayList<ModelListener<Company>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Company>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CompanyImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_COMPANY = "SELECT company FROM Company company";
	private static final String _SQL_SELECT_COMPANY_WHERE_PKS_IN = "SELECT company FROM Company company WHERE companyId IN (";
	private static final String _SQL_SELECT_COMPANY_WHERE = "SELECT company FROM Company company WHERE ";
	private static final String _SQL_COUNT_COMPANY = "SELECT COUNT(company) FROM Company company";
	private static final String _SQL_COUNT_COMPANY_WHERE = "SELECT COUNT(company) FROM Company company WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "company.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Company exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Company exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(CompanyPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"key", "active"
			});
	private static Company _nullCompany = new CompanyImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Company> toCacheModel() {
				return _nullCompanyCacheModel;
			}
		};

	private static CacheModel<Company> _nullCompanyCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<Company>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return 0;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public Company toEntityModel() {
			return _nullCompany;
		}
	}
}