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

import com.liferay.portal.NoSuchCountryException;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.impl.CountryImpl;
import com.liferay.portal.model.impl.CountryModelImpl;
import com.liferay.portal.service.persistence.CountryPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the country service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CountryPersistence
 * @see CountryUtil
 * @generated
 */
@ProviderType
public class CountryPersistenceImpl extends BasePersistenceImpl<Country>
	implements CountryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CountryUtil} to access the country persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CountryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, CountryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, CountryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_NAME = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, CountryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByName",
			new String[] { String.class.getName() },
			CountryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] { String.class.getName() });

	/**
	 * Returns the country where name = &#63; or throws a {@link com.liferay.portal.NoSuchCountryException} if it could not be found.
	 *
	 * @param name the name
	 * @return the matching country
	 * @throws com.liferay.portal.NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByName(String name) throws NoSuchCountryException {
		Country country = fetchByName(name);

		if (country == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCountryException(msg.toString());
		}

		return country;
	}

	/**
	 * Returns the country where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the country where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByName(String name, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_NAME,
					finderArgs, this);
		}

		if (result instanceof Country) {
			Country country = (Country)result;

			if (!Validator.equals(name, country.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COUNTRY_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				List<Country> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
						finderArgs, list);
				}
				else {
					Country country = list.get(0);

					result = country;

					cacheResult(country);

					if ((country.getName() == null) ||
							!country.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
							finderArgs, country);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME,
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
			return (Country)result;
		}
	}

	/**
	 * Removes the country where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the country that was removed
	 */
	@Override
	public Country removeByName(String name) throws NoSuchCountryException {
		Country country = findByName(name);

		return remove(country);
	}

	/**
	 * Returns the number of countries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching countries
	 */
	@Override
	public int countByName(String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_NAME;

		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COUNTRY_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_NAME_NAME_1 = "country.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "country.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(country.name IS NULL OR country.name = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_A2 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, CountryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByA2",
			new String[] { String.class.getName() },
			CountryModelImpl.A2_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_A2 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA2",
			new String[] { String.class.getName() });

	/**
	 * Returns the country where a2 = &#63; or throws a {@link com.liferay.portal.NoSuchCountryException} if it could not be found.
	 *
	 * @param a2 the a2
	 * @return the matching country
	 * @throws com.liferay.portal.NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByA2(String a2) throws NoSuchCountryException {
		Country country = fetchByA2(a2);

		if (country == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("a2=");
			msg.append(a2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCountryException(msg.toString());
		}

		return country;
	}

	/**
	 * Returns the country where a2 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param a2 the a2
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByA2(String a2) {
		return fetchByA2(a2, true);
	}

	/**
	 * Returns the country where a2 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param a2 the a2
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByA2(String a2, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { a2 };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_A2,
					finderArgs, this);
		}

		if (result instanceof Country) {
			Country country = (Country)result;

			if (!Validator.equals(a2, country.getA2())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COUNTRY_WHERE);

			boolean bindA2 = false;

			if (a2 == null) {
				query.append(_FINDER_COLUMN_A2_A2_1);
			}
			else if (a2.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_A2_A2_3);
			}
			else {
				bindA2 = true;

				query.append(_FINDER_COLUMN_A2_A2_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindA2) {
					qPos.add(a2);
				}

				List<Country> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
						finderArgs, list);
				}
				else {
					Country country = list.get(0);

					result = country;

					cacheResult(country);

					if ((country.getA2() == null) ||
							!country.getA2().equals(a2)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
							finderArgs, country);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A2, finderArgs);

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
			return (Country)result;
		}
	}

	/**
	 * Removes the country where a2 = &#63; from the database.
	 *
	 * @param a2 the a2
	 * @return the country that was removed
	 */
	@Override
	public Country removeByA2(String a2) throws NoSuchCountryException {
		Country country = findByA2(a2);

		return remove(country);
	}

	/**
	 * Returns the number of countries where a2 = &#63;.
	 *
	 * @param a2 the a2
	 * @return the number of matching countries
	 */
	@Override
	public int countByA2(String a2) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_A2;

		Object[] finderArgs = new Object[] { a2 };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COUNTRY_WHERE);

			boolean bindA2 = false;

			if (a2 == null) {
				query.append(_FINDER_COLUMN_A2_A2_1);
			}
			else if (a2.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_A2_A2_3);
			}
			else {
				bindA2 = true;

				query.append(_FINDER_COLUMN_A2_A2_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindA2) {
					qPos.add(a2);
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

	private static final String _FINDER_COLUMN_A2_A2_1 = "country.a2 IS NULL";
	private static final String _FINDER_COLUMN_A2_A2_2 = "country.a2 = ?";
	private static final String _FINDER_COLUMN_A2_A2_3 = "(country.a2 IS NULL OR country.a2 = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_A3 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, CountryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByA3",
			new String[] { String.class.getName() },
			CountryModelImpl.A3_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_A3 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA3",
			new String[] { String.class.getName() });

	/**
	 * Returns the country where a3 = &#63; or throws a {@link com.liferay.portal.NoSuchCountryException} if it could not be found.
	 *
	 * @param a3 the a3
	 * @return the matching country
	 * @throws com.liferay.portal.NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByA3(String a3) throws NoSuchCountryException {
		Country country = fetchByA3(a3);

		if (country == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("a3=");
			msg.append(a3);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCountryException(msg.toString());
		}

		return country;
	}

	/**
	 * Returns the country where a3 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param a3 the a3
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByA3(String a3) {
		return fetchByA3(a3, true);
	}

	/**
	 * Returns the country where a3 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param a3 the a3
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByA3(String a3, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { a3 };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_A3,
					finderArgs, this);
		}

		if (result instanceof Country) {
			Country country = (Country)result;

			if (!Validator.equals(a3, country.getA3())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COUNTRY_WHERE);

			boolean bindA3 = false;

			if (a3 == null) {
				query.append(_FINDER_COLUMN_A3_A3_1);
			}
			else if (a3.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_A3_A3_3);
			}
			else {
				bindA3 = true;

				query.append(_FINDER_COLUMN_A3_A3_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindA3) {
					qPos.add(a3);
				}

				List<Country> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
						finderArgs, list);
				}
				else {
					Country country = list.get(0);

					result = country;

					cacheResult(country);

					if ((country.getA3() == null) ||
							!country.getA3().equals(a3)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
							finderArgs, country);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A3, finderArgs);

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
			return (Country)result;
		}
	}

	/**
	 * Removes the country where a3 = &#63; from the database.
	 *
	 * @param a3 the a3
	 * @return the country that was removed
	 */
	@Override
	public Country removeByA3(String a3) throws NoSuchCountryException {
		Country country = findByA3(a3);

		return remove(country);
	}

	/**
	 * Returns the number of countries where a3 = &#63;.
	 *
	 * @param a3 the a3
	 * @return the number of matching countries
	 */
	@Override
	public int countByA3(String a3) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_A3;

		Object[] finderArgs = new Object[] { a3 };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COUNTRY_WHERE);

			boolean bindA3 = false;

			if (a3 == null) {
				query.append(_FINDER_COLUMN_A3_A3_1);
			}
			else if (a3.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_A3_A3_3);
			}
			else {
				bindA3 = true;

				query.append(_FINDER_COLUMN_A3_A3_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindA3) {
					qPos.add(a3);
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

	private static final String _FINDER_COLUMN_A3_A3_1 = "country.a3 IS NULL";
	private static final String _FINDER_COLUMN_A3_A3_2 = "country.a3 = ?";
	private static final String _FINDER_COLUMN_A3_A3_3 = "(country.a3 IS NULL OR country.a3 = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIVE = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, CountryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActive",
			new String[] {
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE =
		new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, CountryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActive",
			new String[] { Boolean.class.getName() },
			CountryModelImpl.ACTIVE_COLUMN_BITMASK |
			CountryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIVE = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActive",
			new String[] { Boolean.class.getName() });

	/**
	 * Returns all the countries where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching countries
	 */
	@Override
	public List<Country> findByActive(boolean active) {
		return findByActive(active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	@Override
	public List<Country> findByActive(boolean active, int start, int end) {
		return findByActive(active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByActive(boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE;
			finderArgs = new Object[] { active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIVE;
			finderArgs = new Object[] { active, start, end, orderByComparator };
		}

		List<Country> list = (List<Country>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Country country : list) {
				if ((active != country.getActive())) {
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

			query.append(_SQL_SELECT_COUNTRY_WHERE);

			query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CountryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				if (!pagination) {
					list = (List<Country>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Country>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws com.liferay.portal.NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByActive_First(boolean active,
		OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {
		Country country = fetchByActive_First(active, orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCountryException(msg.toString());
	}

	/**
	 * Returns the first country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByActive_First(boolean active,
		OrderByComparator<Country> orderByComparator) {
		List<Country> list = findByActive(active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws com.liferay.portal.NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByActive_Last(boolean active,
		OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {
		Country country = fetchByActive_Last(active, orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCountryException(msg.toString());
	}

	/**
	 * Returns the last country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByActive_Last(boolean active,
		OrderByComparator<Country> orderByComparator) {
		int count = countByActive(active);

		if (count == 0) {
			return null;
		}

		List<Country> list = findByActive(active, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the countries before and after the current country in the ordered set where active = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws com.liferay.portal.NoSuchCountryException if a country with the primary key could not be found
	 */
	@Override
	public Country[] findByActive_PrevAndNext(long countryId, boolean active,
		OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {
		Country country = findByPrimaryKey(countryId);

		Session session = null;

		try {
			session = openSession();

			Country[] array = new CountryImpl[3];

			array[0] = getByActive_PrevAndNext(session, country, active,
					orderByComparator, true);

			array[1] = country;

			array[2] = getByActive_PrevAndNext(session, country, active,
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

	protected Country getByActive_PrevAndNext(Session session, Country country,
		boolean active, OrderByComparator<Country> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COUNTRY_WHERE);

		query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

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
			query.append(CountryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(country);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Country> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the countries where active = &#63; from the database.
	 *
	 * @param active the active
	 */
	@Override
	public void removeByActive(boolean active) {
		for (Country country : findByActive(active, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(country);
		}
	}

	/**
	 * Returns the number of countries where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching countries
	 */
	@Override
	public int countByActive(boolean active) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ACTIVE;

		Object[] finderArgs = new Object[] { active };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COUNTRY_WHERE);

			query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

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

	private static final String _FINDER_COLUMN_ACTIVE_ACTIVE_2 = "country.active = ?";

	public CountryPersistenceImpl() {
		setModelClass(Country.class);
	}

	/**
	 * Caches the country in the entity cache if it is enabled.
	 *
	 * @param country the country
	 */
	@Override
	public void cacheResult(Country country) {
		EntityCacheUtil.putResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryImpl.class, country.getPrimaryKey(), country);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { country.getName() }, country);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
			new Object[] { country.getA2() }, country);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
			new Object[] { country.getA3() }, country);

		country.resetOriginalValues();
	}

	/**
	 * Caches the countries in the entity cache if it is enabled.
	 *
	 * @param countries the countries
	 */
	@Override
	public void cacheResult(List<Country> countries) {
		for (Country country : countries) {
			if (EntityCacheUtil.getResult(
						CountryModelImpl.ENTITY_CACHE_ENABLED,
						CountryImpl.class, country.getPrimaryKey()) == null) {
				cacheResult(country);
			}
			else {
				country.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all countries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CountryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CountryImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the country.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Country country) {
		EntityCacheUtil.removeResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryImpl.class, country.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(country);
	}

	@Override
	public void clearCache(List<Country> countries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Country country : countries) {
			EntityCacheUtil.removeResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
				CountryImpl.class, country.getPrimaryKey());

			clearUniqueFindersCache(country);
		}
	}

	protected void cacheUniqueFindersCache(Country country) {
		if (country.isNew()) {
			Object[] args = new Object[] { country.getName() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME, args, country);

			args = new Object[] { country.getA2() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A2, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2, args, country);

			args = new Object[] { country.getA3() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A3, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3, args, country);
		}
		else {
			CountryModelImpl countryModelImpl = (CountryModelImpl)country;

			if ((countryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_NAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { country.getName() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME, args,
					country);
			}

			if ((countryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_A2.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { country.getA2() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A2, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2, args, country);
			}

			if ((countryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_A3.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { country.getA3() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A3, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3, args, country);
			}
		}
	}

	protected void clearUniqueFindersCache(Country country) {
		CountryModelImpl countryModelImpl = (CountryModelImpl)country;

		Object[] args = new Object[] { country.getName() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME, args);

		if ((countryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_NAME.getColumnBitmask()) != 0) {
			args = new Object[] { countryModelImpl.getOriginalName() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME, args);
		}

		args = new Object[] { country.getA2() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_A2, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A2, args);

		if ((countryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_A2.getColumnBitmask()) != 0) {
			args = new Object[] { countryModelImpl.getOriginalA2() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_A2, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A2, args);
		}

		args = new Object[] { country.getA3() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_A3, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A3, args);

		if ((countryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_A3.getColumnBitmask()) != 0) {
			args = new Object[] { countryModelImpl.getOriginalA3() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_A3, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A3, args);
		}
	}

	/**
	 * Creates a new country with the primary key. Does not add the country to the database.
	 *
	 * @param countryId the primary key for the new country
	 * @return the new country
	 */
	@Override
	public Country create(long countryId) {
		Country country = new CountryImpl();

		country.setNew(true);
		country.setPrimaryKey(countryId);

		return country;
	}

	/**
	 * Removes the country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param countryId the primary key of the country
	 * @return the country that was removed
	 * @throws com.liferay.portal.NoSuchCountryException if a country with the primary key could not be found
	 */
	@Override
	public Country remove(long countryId) throws NoSuchCountryException {
		return remove((Serializable)countryId);
	}

	/**
	 * Removes the country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the country
	 * @return the country that was removed
	 * @throws com.liferay.portal.NoSuchCountryException if a country with the primary key could not be found
	 */
	@Override
	public Country remove(Serializable primaryKey)
		throws NoSuchCountryException {
		Session session = null;

		try {
			session = openSession();

			Country country = (Country)session.get(CountryImpl.class, primaryKey);

			if (country == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCountryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(country);
		}
		catch (NoSuchCountryException nsee) {
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
	protected Country removeImpl(Country country) {
		country = toUnwrappedModel(country);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(country)) {
				country = (Country)session.get(CountryImpl.class,
						country.getPrimaryKeyObj());
			}

			if (country != null) {
				session.delete(country);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (country != null) {
			clearCache(country);
		}

		return country;
	}

	@Override
	public Country updateImpl(com.liferay.portal.model.Country country) {
		country = toUnwrappedModel(country);

		boolean isNew = country.isNew();

		CountryModelImpl countryModelImpl = (CountryModelImpl)country;

		Session session = null;

		try {
			session = openSession();

			if (country.isNew()) {
				session.save(country);

				country.setNew(false);
			}
			else {
				session.merge(country);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CountryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((countryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						countryModelImpl.getOriginalActive()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIVE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE,
					args);

				args = new Object[] { countryModelImpl.getActive() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIVE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIVE,
					args);
			}
		}

		EntityCacheUtil.putResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryImpl.class, country.getPrimaryKey(), country, false);

		clearUniqueFindersCache(country);
		cacheUniqueFindersCache(country);

		country.resetOriginalValues();

		return country;
	}

	protected Country toUnwrappedModel(Country country) {
		if (country instanceof CountryImpl) {
			return country;
		}

		CountryImpl countryImpl = new CountryImpl();

		countryImpl.setNew(country.isNew());
		countryImpl.setPrimaryKey(country.getPrimaryKey());

		countryImpl.setMvccVersion(country.getMvccVersion());
		countryImpl.setCountryId(country.getCountryId());
		countryImpl.setName(country.getName());
		countryImpl.setA2(country.getA2());
		countryImpl.setA3(country.getA3());
		countryImpl.setNumber(country.getNumber());
		countryImpl.setIdd(country.getIdd());
		countryImpl.setZipRequired(country.isZipRequired());
		countryImpl.setActive(country.isActive());

		return countryImpl;
	}

	/**
	 * Returns the country with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the country
	 * @return the country
	 * @throws com.liferay.portal.NoSuchCountryException if a country with the primary key could not be found
	 */
	@Override
	public Country findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCountryException {
		Country country = fetchByPrimaryKey(primaryKey);

		if (country == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCountryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return country;
	}

	/**
	 * Returns the country with the primary key or throws a {@link com.liferay.portal.NoSuchCountryException} if it could not be found.
	 *
	 * @param countryId the primary key of the country
	 * @return the country
	 * @throws com.liferay.portal.NoSuchCountryException if a country with the primary key could not be found
	 */
	@Override
	public Country findByPrimaryKey(long countryId)
		throws NoSuchCountryException {
		return findByPrimaryKey((Serializable)countryId);
	}

	/**
	 * Returns the country with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the country
	 * @return the country, or <code>null</code> if a country with the primary key could not be found
	 */
	@Override
	public Country fetchByPrimaryKey(Serializable primaryKey) {
		Country country = (Country)EntityCacheUtil.getResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
				CountryImpl.class, primaryKey);

		if (country == _nullCountry) {
			return null;
		}

		if (country == null) {
			Session session = null;

			try {
				session = openSession();

				country = (Country)session.get(CountryImpl.class, primaryKey);

				if (country != null) {
					cacheResult(country);
				}
				else {
					EntityCacheUtil.putResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
						CountryImpl.class, primaryKey, _nullCountry);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
					CountryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return country;
	}

	/**
	 * Returns the country with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param countryId the primary key of the country
	 * @return the country, or <code>null</code> if a country with the primary key could not be found
	 */
	@Override
	public Country fetchByPrimaryKey(long countryId) {
		return fetchByPrimaryKey((Serializable)countryId);
	}

	@Override
	public Map<Serializable, Country> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Country> map = new HashMap<Serializable, Country>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Country country = fetchByPrimaryKey(primaryKey);

			if (country != null) {
				map.put(primaryKey, country);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Country country = (Country)EntityCacheUtil.getResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
					CountryImpl.class, primaryKey);

			if (country == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, country);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COUNTRY_WHERE_PKS_IN);

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

			for (Country country : (List<Country>)q.list()) {
				map.put(country.getPrimaryKeyObj(), country);

				cacheResult(country);

				uncachedPrimaryKeys.remove(country.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
					CountryImpl.class, primaryKey, _nullCountry);
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
	 * Returns all the countries.
	 *
	 * @return the countries
	 */
	@Override
	public List<Country> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of countries
	 */
	@Override
	public List<Country> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of countries
	 */
	@Override
	public List<Country> findAll(int start, int end,
		OrderByComparator<Country> orderByComparator) {
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

		List<Country> list = (List<Country>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COUNTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COUNTRY;

				if (pagination) {
					sql = sql.concat(CountryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Country>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Country>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the countries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Country country : findAll()) {
			remove(country);
		}
	}

	/**
	 * Returns the number of countries.
	 *
	 * @return the number of countries
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COUNTRY);

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
	 * Initializes the country persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(CountryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_COUNTRY = "SELECT country FROM Country country";
	private static final String _SQL_SELECT_COUNTRY_WHERE_PKS_IN = "SELECT country FROM Country country WHERE countryId IN (";
	private static final String _SQL_SELECT_COUNTRY_WHERE = "SELECT country FROM Country country WHERE ";
	private static final String _SQL_COUNT_COUNTRY = "SELECT COUNT(country) FROM Country country";
	private static final String _SQL_COUNT_COUNTRY_WHERE = "SELECT COUNT(country) FROM Country country WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "country.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Country exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Country exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(CountryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"number", "idd", "active"
			});
	private static final Country _nullCountry = new CountryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Country> toCacheModel() {
				return _nullCountryCacheModel;
			}
		};

	private static final CacheModel<Country> _nullCountryCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<Country>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public Country toEntityModel() {
			return _nullCountry;
		}
	}
}