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

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.persistence.CompanyPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.CompanyModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the company service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class CompanyPersistenceImpl extends BasePersistenceImpl<Company>
	implements CompanyPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CompanyUtil</code> to access the company persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CompanyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByWebId;
	private FinderPath _finderPathCountByWebId;

	/**
	 * Returns the company where webId = &#63; or throws a <code>NoSuchCompanyException</code> if it could not be found.
	 *
	 * @param webId the web ID
	 * @return the matching company
	 * @throws NoSuchCompanyException if a matching company could not be found
	 */
	@Override
	public Company findByWebId(String webId) throws NoSuchCompanyException {
		Company company = fetchByWebId(webId);

		if (company == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("webId=");
			msg.append(webId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchByWebId(String webId, boolean retrieveFromCache) {
		webId = Objects.toString(webId, "");

		Object[] finderArgs = new Object[] { webId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByWebId,
					finderArgs, this);
		}

		if (result instanceof Company) {
			Company company = (Company)result;

			if (!Objects.equals(webId, company.getWebId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COMPANY_WHERE);

			boolean bindWebId = false;

			if (webId.isEmpty()) {
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
					FinderCacheUtil.putResult(_finderPathFetchByWebId,
						finderArgs, list);
				}
				else {
					Company company = list.get(0);

					result = company;

					cacheResult(company);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByWebId, finderArgs);

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
		webId = Objects.toString(webId, "");

		FinderPath finderPath = _finderPathCountByWebId;

		Object[] finderArgs = new Object[] { webId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMPANY_WHERE);

			boolean bindWebId = false;

			if (webId.isEmpty()) {
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

	private static final String _FINDER_COLUMN_WEBID_WEBID_2 = "company.webId = ?";
	private static final String _FINDER_COLUMN_WEBID_WEBID_3 = "(company.webId IS NULL OR company.webId = '')";
	private FinderPath _finderPathFetchByMx;
	private FinderPath _finderPathCountByMx;

	/**
	 * Returns the company where mx = &#63; or throws a <code>NoSuchCompanyException</code> if it could not be found.
	 *
	 * @param mx the mx
	 * @return the matching company
	 * @throws NoSuchCompanyException if a matching company could not be found
	 */
	@Override
	public Company findByMx(String mx) throws NoSuchCompanyException {
		Company company = fetchByMx(mx);

		if (company == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("mx=");
			msg.append(mx);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchByMx(String mx, boolean retrieveFromCache) {
		mx = Objects.toString(mx, "");

		Object[] finderArgs = new Object[] { mx };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByMx,
					finderArgs, this);
		}

		if (result instanceof Company) {
			Company company = (Company)result;

			if (!Objects.equals(mx, company.getMx())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COMPANY_WHERE);

			boolean bindMx = false;

			if (mx.isEmpty()) {
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
					FinderCacheUtil.putResult(_finderPathFetchByMx, finderArgs,
						list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"CompanyPersistenceImpl.fetchByMx(String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Company company = list.get(0);

					result = company;

					cacheResult(company);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByMx, finderArgs);

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
		mx = Objects.toString(mx, "");

		FinderPath finderPath = _finderPathCountByMx;

		Object[] finderArgs = new Object[] { mx };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMPANY_WHERE);

			boolean bindMx = false;

			if (mx.isEmpty()) {
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

	private static final String _FINDER_COLUMN_MX_MX_2 = "company.mx = ?";
	private static final String _FINDER_COLUMN_MX_MX_3 = "(company.mx IS NULL OR company.mx = '')";
	private FinderPath _finderPathFetchByLogoId;
	private FinderPath _finderPathCountByLogoId;

	/**
	 * Returns the company where logoId = &#63; or throws a <code>NoSuchCompanyException</code> if it could not be found.
	 *
	 * @param logoId the logo ID
	 * @return the matching company
	 * @throws NoSuchCompanyException if a matching company could not be found
	 */
	@Override
	public Company findByLogoId(long logoId) throws NoSuchCompanyException {
		Company company = fetchByLogoId(logoId);

		if (company == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("logoId=");
			msg.append(logoId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching company, or <code>null</code> if a matching company could not be found
	 */
	@Override
	public Company fetchByLogoId(long logoId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { logoId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByLogoId,
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
					FinderCacheUtil.putResult(_finderPathFetchByLogoId,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"CompanyPersistenceImpl.fetchByLogoId(long, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Company company = list.get(0);

					result = company;

					cacheResult(company);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByLogoId,
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
		FinderPath finderPath = _finderPathCountByLogoId;

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
	private FinderPath _finderPathWithPaginationFindBySystem;
	private FinderPath _finderPathWithoutPaginationFindBySystem;
	private FinderPath _finderPathCountBySystem;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		OrderByComparator<Company> orderByComparator) {
		return findBySystem(system, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the companies where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of companies
	 * @param end the upper bound of the range of companies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching companies
	 */
	@Override
	public List<Company> findBySystem(boolean system, int start, int end,
		OrderByComparator<Company> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindBySystem;
			finderArgs = new Object[] { system };
		}
		else {
			finderPath = _finderPathWithPaginationFindBySystem;
			finderArgs = new Object[] { system, start, end, orderByComparator };
		}

		List<Company> list = null;

		if (retrieveFromCache) {
			list = (List<Company>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Company company : list) {
					if ((system != company.isSystem())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
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
	 * @throws NoSuchCompanyException if a matching company could not be found
	 */
	@Override
	public Company findBySystem_First(boolean system,
		OrderByComparator<Company> orderByComparator)
		throws NoSuchCompanyException {
		Company company = fetchBySystem_First(system, orderByComparator);

		if (company != null) {
			return company;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("system=");
		msg.append(system);

		msg.append("}");

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
		OrderByComparator<Company> orderByComparator) {
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
	 * @throws NoSuchCompanyException if a matching company could not be found
	 */
	@Override
	public Company findBySystem_Last(boolean system,
		OrderByComparator<Company> orderByComparator)
		throws NoSuchCompanyException {
		Company company = fetchBySystem_Last(system, orderByComparator);

		if (company != null) {
			return company;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("system=");
		msg.append(system);

		msg.append("}");

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
		OrderByComparator<Company> orderByComparator) {
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
	 * @throws NoSuchCompanyException if a company with the primary key could not be found
	 */
	@Override
	public Company[] findBySystem_PrevAndNext(long companyId, boolean system,
		OrderByComparator<Company> orderByComparator)
		throws NoSuchCompanyException {
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
		boolean system, OrderByComparator<Company> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					company)) {
				qPos.add(orderByConditionValue);
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
		FinderPath finderPath = _finderPathCountBySystem;

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

		setModelImplClass(CompanyImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(CompanyModelImpl.ENTITY_CACHE_ENABLED);
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

		FinderCacheUtil.putResult(_finderPathFetchByWebId,
			new Object[] { company.getWebId() }, company);

		FinderCacheUtil.putResult(_finderPathFetchByMx,
			new Object[] { company.getMx() }, company);

		FinderCacheUtil.putResult(_finderPathFetchByLogoId,
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
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(CompanyImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the company.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Company company) {
		EntityCacheUtil.removeResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyImpl.class, company.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CompanyModelImpl)company, true);
	}

	@Override
	public void clearCache(List<Company> companies) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Company company : companies) {
			EntityCacheUtil.removeResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyImpl.class, company.getPrimaryKey());

			clearUniqueFindersCache((CompanyModelImpl)company, true);
		}
	}

	protected void cacheUniqueFindersCache(CompanyModelImpl companyModelImpl) {
		Object[] args = new Object[] { companyModelImpl.getWebId() };

		FinderCacheUtil.putResult(_finderPathCountByWebId, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByWebId, args,
			companyModelImpl, false);

		args = new Object[] { companyModelImpl.getMx() };

		FinderCacheUtil.putResult(_finderPathCountByMx, args, Long.valueOf(1),
			false);
		FinderCacheUtil.putResult(_finderPathFetchByMx, args, companyModelImpl,
			false);

		args = new Object[] { companyModelImpl.getLogoId() };

		FinderCacheUtil.putResult(_finderPathCountByLogoId, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByLogoId, args,
			companyModelImpl, false);
	}

	protected void clearUniqueFindersCache(CompanyModelImpl companyModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] { companyModelImpl.getWebId() };

			FinderCacheUtil.removeResult(_finderPathCountByWebId, args);
			FinderCacheUtil.removeResult(_finderPathFetchByWebId, args);
		}

		if ((companyModelImpl.getColumnBitmask() &
				_finderPathFetchByWebId.getColumnBitmask()) != 0) {
			Object[] args = new Object[] { companyModelImpl.getOriginalWebId() };

			FinderCacheUtil.removeResult(_finderPathCountByWebId, args);
			FinderCacheUtil.removeResult(_finderPathFetchByWebId, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] { companyModelImpl.getMx() };

			FinderCacheUtil.removeResult(_finderPathCountByMx, args);
			FinderCacheUtil.removeResult(_finderPathFetchByMx, args);
		}

		if ((companyModelImpl.getColumnBitmask() &
				_finderPathFetchByMx.getColumnBitmask()) != 0) {
			Object[] args = new Object[] { companyModelImpl.getOriginalMx() };

			FinderCacheUtil.removeResult(_finderPathCountByMx, args);
			FinderCacheUtil.removeResult(_finderPathFetchByMx, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] { companyModelImpl.getLogoId() };

			FinderCacheUtil.removeResult(_finderPathCountByLogoId, args);
			FinderCacheUtil.removeResult(_finderPathFetchByLogoId, args);
		}

		if ((companyModelImpl.getColumnBitmask() &
				_finderPathFetchByLogoId.getColumnBitmask()) != 0) {
			Object[] args = new Object[] { companyModelImpl.getOriginalLogoId() };

			FinderCacheUtil.removeResult(_finderPathCountByLogoId, args);
			FinderCacheUtil.removeResult(_finderPathFetchByLogoId, args);
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
	 * @throws NoSuchCompanyException if a company with the primary key could not be found
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
	 * @throws NoSuchCompanyException if a company with the primary key could not be found
	 */
	@Override
	public Company remove(Serializable primaryKey)
		throws NoSuchCompanyException {
		Session session = null;

		try {
			session = openSession();

			Company company = (Company)session.get(CompanyImpl.class, primaryKey);

			if (company == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
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
	public Company updateImpl(Company company) {
		boolean isNew = company.isNew();

		if (!(company instanceof CompanyModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(company.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(company);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in company proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Company implementation " +
				company.getClass());
		}

		CompanyModelImpl companyModelImpl = (CompanyModelImpl)company;

		Session session = null;

		try {
			session = openSession();

			if (company.isNew()) {
				session.save(company);

				company.setNew(false);
			}
			else {
				company = (Company)session.merge(company);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CompanyModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { companyModelImpl.isSystem() };

			FinderCacheUtil.removeResult(_finderPathCountBySystem, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindBySystem,
				args);

			FinderCacheUtil.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((companyModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindBySystem.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						companyModelImpl.getOriginalSystem()
					};

				FinderCacheUtil.removeResult(_finderPathCountBySystem, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindBySystem,
					args);

				args = new Object[] { companyModelImpl.isSystem() };

				FinderCacheUtil.removeResult(_finderPathCountBySystem, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindBySystem,
					args);
			}
		}

		EntityCacheUtil.putResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyImpl.class, company.getPrimaryKey(), company, false);

		clearUniqueFindersCache(companyModelImpl, false);
		cacheUniqueFindersCache(companyModelImpl);

		company.resetOriginalValues();

		return company;
	}

	/**
	 * Returns the company with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the company
	 * @return the company
	 * @throws NoSuchCompanyException if a company with the primary key could not be found
	 */
	@Override
	public Company findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCompanyException {
		Company company = fetchByPrimaryKey(primaryKey);

		if (company == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCompanyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return company;
	}

	/**
	 * Returns the company with the primary key or throws a <code>NoSuchCompanyException</code> if it could not be found.
	 *
	 * @param companyId the primary key of the company
	 * @return the company
	 * @throws NoSuchCompanyException if a company with the primary key could not be found
	 */
	@Override
	public Company findByPrimaryKey(long companyId)
		throws NoSuchCompanyException {
		return findByPrimaryKey((Serializable)companyId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of companies
	 * @param end the upper bound of the range of companies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of companies
	 */
	@Override
	public List<Company> findAll(int start, int end,
		OrderByComparator<Company> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the companies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of companies
	 * @param end the upper bound of the range of companies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of companies
	 */
	@Override
	public List<Company> findAll(int start, int end,
		OrderByComparator<Company> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Company> list = null;

		if (retrieveFromCache) {
			list = (List<Company>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

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
		Long count = (Long)FinderCacheUtil.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMPANY);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(_finderPathCountAll,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathCountAll,
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "companyId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMPANY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CompanyModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the company persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathFetchByWebId = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
				FINDER_CLASS_NAME_ENTITY, "fetchByWebId",
				new String[] { String.class.getName() },
				CompanyModelImpl.WEBID_COLUMN_BITMASK);

		_finderPathCountByWebId = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByWebId",
				new String[] { String.class.getName() });

		_finderPathFetchByMx = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
				FINDER_CLASS_NAME_ENTITY, "fetchByMx",
				new String[] { String.class.getName() },
				CompanyModelImpl.MX_COLUMN_BITMASK);

		_finderPathCountByMx = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMx",
				new String[] { String.class.getName() });

		_finderPathFetchByLogoId = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
				FINDER_CLASS_NAME_ENTITY, "fetchByLogoId",
				new String[] { Long.class.getName() },
				CompanyModelImpl.LOGOID_COLUMN_BITMASK);

		_finderPathCountByLogoId = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLogoId",
				new String[] { Long.class.getName() });

		_finderPathWithPaginationFindBySystem = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySystem",
				new String[] {
					Boolean.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindBySystem = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, CompanyImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySystem",
				new String[] { Boolean.class.getName() },
				CompanyModelImpl.SYSTEM_COLUMN_BITMASK);

		_finderPathCountBySystem = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySystem",
				new String[] { Boolean.class.getName() });
	}

	public void destroy() {
		EntityCacheUtil.removeCache(CompanyImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_COMPANY = "SELECT company FROM Company company";
	private static final String _SQL_SELECT_COMPANY_WHERE = "SELECT company FROM Company company WHERE ";
	private static final String _SQL_COUNT_COMPANY = "SELECT COUNT(company) FROM Company company";
	private static final String _SQL_COUNT_COMPANY_WHERE = "SELECT COUNT(company) FROM Company company WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "company.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Company exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Company exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CompanyPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"key", "system", "active"
			});
}