/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CountryLocalizationPersistence;
import com.liferay.portal.kernel.service.persistence.CountryPersistence;
import com.liferay.portal.kernel.service.persistence.CountryUtil;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.impl.CountryImpl;
import com.liferay.portal.model.impl.CountryModelImpl;

import java.io.Serializable;

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

/**
 * The persistence implementation for the country service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CountryPersistenceImpl
	extends BasePersistenceImpl<Country> implements CountryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CountryUtil</code> to access the country persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CountryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<Country>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching countries
	 */
	@Override
	public List<Country> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	@Override
	public List<Country> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByUuid.find(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByUuid_First(
			String uuid, OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {

		Country country = fetchByUuid_First(uuid, orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByUuid_First(
		String uuid, OrderByComparator<Country> orderByComparator) {

		List<Country> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the countries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByUuid(String uuid, int start, int end) {
		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByUuid(
					uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CountryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CountryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CountryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CountryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			return (List<Country>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the countries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (Country country :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(country);
		}
	}

	/**
	 * Returns the number of countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching countries
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByUuid.count(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid});
		}
	}

	/**
	 * Returns the number of countries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching countries that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<Country> countries = findByUuid(uuid);

			countries = InlineSQLHelperUtil.filter(countries);

			return countries.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COUNTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL =
		"country.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(country.uuid_ IS NULL OR country.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<Country>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching countries
	 */
	@Override
	public List<Country> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	@Override
	public List<Country> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {uuid, companyId}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {

		Country country = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<Country> orderByComparator) {

		List<Country> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the countries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByUuid_C(String uuid, long companyId) {
		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CountryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CountryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CountryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CountryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			return (List<Country>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the countries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (Country country :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(country);
		}
	}

	/**
	 * Returns the number of countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching countries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {uuid, companyId});
		}
	}

	/**
	 * Returns the number of countries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching countries that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<Country> countries = findByUuid_C(uuid, companyId);

			countries = InlineSQLHelperUtil.filter(countries);

			return countries.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COUNTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL =
		"country.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(country.uuid_ IS NULL OR country.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"country.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<Country>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching countries
	 */
	@Override
	public List<Country> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	@Override
	public List<Country> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByCompanyId_First(
			long companyId, OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {

		Country country = fetchByCompanyId_First(companyId, orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByCompanyId_First(
		long companyId, OrderByComparator<Country> orderByComparator) {

		List<Country> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the countries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CountryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CountryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CountryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CountryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<Country>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the countries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (Country country :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(country);
		}
	}

	/**
	 * Returns the number of countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching countries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId});
		}
	}

	/**
	 * Returns the number of countries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching countries that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<Country> countries = findByCompanyId(companyId);

			countries = InlineSQLHelperUtil.filter(countries);

			return countries.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COUNTRY_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"country.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByActive;
	private FinderPath _finderPathWithoutPaginationFindByActive;
	private FinderPath _finderPathCountByActive;
	private CollectionPersistenceFinder<Country>
		_collectionPersistenceFinderByActive;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByActive(
		boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return findByActive(active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the countries where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByActive(
		boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByActive.find(
				FinderCacheUtil.getFinderCache(), new Object[] {active}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByActive_First(
			boolean active, OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {

		Country country = fetchByActive_First(active, orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByActive_First(
		boolean active, OrderByComparator<Country> orderByComparator) {

		List<Country> list = findByActive(active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the countries that the user has permission to view where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByActive(boolean active) {
		return filterFindByActive(
			active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries that the user has permission to view where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByActive(
		boolean active, int start, int end) {

		return filterFindByActive(active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries that the user has permissions to view where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByActive(
		boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByActive(active, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByActive(
					active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CountryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CountryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CountryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CountryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			return (List<Country>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the countries where active = &#63; from the database.
	 *
	 * @param active the active
	 */
	@Override
	public void removeByActive(boolean active) {
		for (Country country :
				findByActive(
					active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByActive.count(
				FinderCacheUtil.getFinderCache(), new Object[] {active});
		}
	}

	/**
	 * Returns the number of countries that the user has permission to view where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching countries that the user has permission to view
	 */
	@Override
	public int filterCountByActive(boolean active) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByActive(active);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<Country> countries = findByActive(active);

			countries = InlineSQLHelperUtil.filter(countries);

			return countries.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COUNTRY_WHERE);

		sb.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_ACTIVE_ACTIVE_2_SQL =
		"country.active_ = ?";

	private FinderPath _finderPathFetchByC_A2;
	private UniquePersistenceFinder<Country> _uniquePersistenceFinderByC_A2;

	/**
	 * Returns the country where companyId = &#63; and a2 = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByC_A2(long companyId, String a2)
		throws NoSuchCountryException {

		Country country = fetchByC_A2(companyId, a2);

		if (country == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", a2=");
			sb.append(a2);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCountryException(sb.toString());
		}

		return country;
	}

	/**
	 * Returns the country where companyId = &#63; and a2 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_A2(long companyId, String a2) {
		return fetchByC_A2(companyId, a2, true);
	}

	/**
	 * Returns the country where companyId = &#63; and a2 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_A2(
		long companyId, String a2, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _uniquePersistenceFinderByC_A2.fetch(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId, a2},
				useFinderCache);
		}
	}

	/**
	 * Removes the country where companyId = &#63; and a2 = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the country that was removed
	 */
	@Override
	public Country removeByC_A2(long companyId, String a2)
		throws NoSuchCountryException {

		Country country = findByC_A2(companyId, a2);

		return remove(country);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and a2 = &#63;.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the number of matching countries
	 */
	@Override
	public int countByC_A2(long companyId, String a2) {
		return _uniquePersistenceFinderByC_A2.count(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId, a2});
	}

	private FinderPath _finderPathFetchByC_A3;
	private UniquePersistenceFinder<Country> _uniquePersistenceFinderByC_A3;

	/**
	 * Returns the country where companyId = &#63; and a3 = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByC_A3(long companyId, String a3)
		throws NoSuchCountryException {

		Country country = fetchByC_A3(companyId, a3);

		if (country == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", a3=");
			sb.append(a3);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCountryException(sb.toString());
		}

		return country;
	}

	/**
	 * Returns the country where companyId = &#63; and a3 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_A3(long companyId, String a3) {
		return fetchByC_A3(companyId, a3, true);
	}

	/**
	 * Returns the country where companyId = &#63; and a3 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_A3(
		long companyId, String a3, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _uniquePersistenceFinderByC_A3.fetch(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId, a3},
				useFinderCache);
		}
	}

	/**
	 * Removes the country where companyId = &#63; and a3 = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the country that was removed
	 */
	@Override
	public Country removeByC_A3(long companyId, String a3)
		throws NoSuchCountryException {

		Country country = findByC_A3(companyId, a3);

		return remove(country);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and a3 = &#63;.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the number of matching countries
	 */
	@Override
	public int countByC_A3(long companyId, String a3) {
		return _uniquePersistenceFinderByC_A3.count(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId, a3});
	}

	private FinderPath _finderPathWithPaginationFindByC_Active;
	private FinderPath _finderPathWithoutPaginationFindByC_Active;
	private FinderPath _finderPathCountByC_Active;
	private CollectionPersistenceFinder<Country>
		_collectionPersistenceFinderByC_Active;

	/**
	 * Returns all the countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching countries
	 */
	@Override
	public List<Country> findByC_Active(long companyId, boolean active) {
		return findByC_Active(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	@Override
	public List<Country> findByC_Active(
		long companyId, boolean active, int start, int end) {

		return findByC_Active(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_Active(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return findByC_Active(
			companyId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_Active(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_Active.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, active}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByC_Active_First(
			long companyId, boolean active,
			OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {

		Country country = fetchByC_Active_First(
			companyId, active, orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_Active_First(
		long companyId, boolean active,
		OrderByComparator<Country> orderByComparator) {

		List<Country> list = findByC_Active(
			companyId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the countries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_Active(long companyId, boolean active) {
		return filterFindByC_Active(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_Active(
		long companyId, boolean active, int start, int end) {

		return filterFindByC_Active(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_Active(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_Active(
				companyId, active, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_Active(
					companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_ACTIVE_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_ACTIVE_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CountryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CountryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CountryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CountryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			return (List<Country>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the countries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	@Override
	public void removeByC_Active(long companyId, boolean active) {
		for (Country country :
				findByC_Active(
					companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(country);
		}
	}

	/**
	 * Returns the number of countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching countries
	 */
	@Override
	public int countByC_Active(long companyId, boolean active) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_Active.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, active});
		}
	}

	/**
	 * Returns the number of countries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching countries that the user has permission to view
	 */
	@Override
	public int filterCountByC_Active(long companyId, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_Active(companyId, active);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<Country> countries = findByC_Active(companyId, active);

			countries = InlineSQLHelperUtil.filter(countries);

			return countries.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COUNTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_ACTIVE_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_ACTIVE_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_ACTIVE_COMPANYID_2 =
		"country.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ACTIVE_ACTIVE_2_SQL =
		"country.active_ = ?";

	private FinderPath _finderPathFetchByC_Name;
	private UniquePersistenceFinder<Country> _uniquePersistenceFinderByC_Name;

	/**
	 * Returns the country where companyId = &#63; and name = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByC_Name(long companyId, String name)
		throws NoSuchCountryException {

		Country country = fetchByC_Name(companyId, name);

		if (country == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCountryException(sb.toString());
		}

		return country;
	}

	/**
	 * Returns the country where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_Name(long companyId, String name) {
		return fetchByC_Name(companyId, name, true);
	}

	/**
	 * Returns the country where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_Name(
		long companyId, String name, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _uniquePersistenceFinderByC_Name.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, name}, useFinderCache);
		}
	}

	/**
	 * Removes the country where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the country that was removed
	 */
	@Override
	public Country removeByC_Name(long companyId, String name)
		throws NoSuchCountryException {

		Country country = findByC_Name(companyId, name);

		return remove(country);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching countries
	 */
	@Override
	public int countByC_Name(long companyId, String name) {
		return _uniquePersistenceFinderByC_Name.count(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId, name});
	}

	private FinderPath _finderPathFetchByC_Number;
	private UniquePersistenceFinder<Country> _uniquePersistenceFinderByC_Number;

	/**
	 * Returns the country where companyId = &#63; and number = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param number the number
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByC_Number(long companyId, String number)
		throws NoSuchCountryException {

		Country country = fetchByC_Number(companyId, number);

		if (country == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", number=");
			sb.append(number);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCountryException(sb.toString());
		}

		return country;
	}

	/**
	 * Returns the country where companyId = &#63; and number = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param number the number
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_Number(long companyId, String number) {
		return fetchByC_Number(companyId, number, true);
	}

	/**
	 * Returns the country where companyId = &#63; and number = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param number the number
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_Number(
		long companyId, String number, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _uniquePersistenceFinderByC_Number.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, number}, useFinderCache);
		}
	}

	/**
	 * Removes the country where companyId = &#63; and number = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param number the number
	 * @return the country that was removed
	 */
	@Override
	public Country removeByC_Number(long companyId, String number)
		throws NoSuchCountryException {

		Country country = findByC_Number(companyId, number);

		return remove(country);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and number = &#63;.
	 *
	 * @param companyId the company ID
	 * @param number the number
	 * @return the number of matching countries
	 */
	@Override
	public int countByC_Number(long companyId, String number) {
		return _uniquePersistenceFinderByC_Number.count(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId, number});
	}

	private FinderPath _finderPathWithPaginationFindByC_A_B;
	private FinderPath _finderPathWithoutPaginationFindByC_A_B;
	private FinderPath _finderPathCountByC_A_B;
	private CollectionPersistenceFinder<Country>
		_collectionPersistenceFinderByC_A_B;

	/**
	 * Returns all the countries where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @return the matching countries
	 */
	@Override
	public List<Country> findByC_A_B(
		long companyId, boolean active, boolean billingAllowed) {

		return findByC_A_B(
			companyId, active, billingAllowed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	@Override
	public List<Country> findByC_A_B(
		long companyId, boolean active, boolean billingAllowed, int start,
		int end) {

		return findByC_A_B(companyId, active, billingAllowed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_A_B(
		long companyId, boolean active, boolean billingAllowed, int start,
		int end, OrderByComparator<Country> orderByComparator) {

		return findByC_A_B(
			companyId, active, billingAllowed, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_A_B(
		long companyId, boolean active, boolean billingAllowed, int start,
		int end, OrderByComparator<Country> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_A_B.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, active, billingAllowed}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByC_A_B_First(
			long companyId, boolean active, boolean billingAllowed,
			OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {

		Country country = fetchByC_A_B_First(
			companyId, active, billingAllowed, orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", billingAllowed=");
		sb.append(billingAllowed);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_A_B_First(
		long companyId, boolean active, boolean billingAllowed,
		OrderByComparator<Country> orderByComparator) {

		List<Country> list = findByC_A_B(
			companyId, active, billingAllowed, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the countries that the user has permission to view where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @return the matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_B(
		long companyId, boolean active, boolean billingAllowed) {

		return filterFindByC_A_B(
			companyId, active, billingAllowed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries that the user has permission to view where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_B(
		long companyId, boolean active, boolean billingAllowed, int start,
		int end) {

		return filterFindByC_A_B(
			companyId, active, billingAllowed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries that the user has permissions to view where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_B(
		long companyId, boolean active, boolean billingAllowed, int start,
		int end, OrderByComparator<Country> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A_B(
				companyId, active, billingAllowed, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_A_B(
					companyId, active, billingAllowed, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_B_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_B_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_B_BILLINGALLOWED_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CountryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CountryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CountryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CountryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(billingAllowed);

			return (List<Country>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the countries where companyId = &#63; and active = &#63; and billingAllowed = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 */
	@Override
	public void removeByC_A_B(
		long companyId, boolean active, boolean billingAllowed) {

		for (Country country :
				findByC_A_B(
					companyId, active, billingAllowed, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(country);
		}
	}

	/**
	 * Returns the number of countries where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @return the number of matching countries
	 */
	@Override
	public int countByC_A_B(
		long companyId, boolean active, boolean billingAllowed) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_A_B.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, active, billingAllowed});
		}
	}

	/**
	 * Returns the number of countries that the user has permission to view where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @return the number of matching countries that the user has permission to view
	 */
	@Override
	public int filterCountByC_A_B(
		long companyId, boolean active, boolean billingAllowed) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_A_B(companyId, active, billingAllowed);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<Country> countries = findByC_A_B(
				companyId, active, billingAllowed);

			countries = InlineSQLHelperUtil.filter(countries);

			return countries.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_COUNTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_A_B_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_B_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_B_BILLINGALLOWED_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(billingAllowed);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_A_B_COMPANYID_2 =
		"country.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_B_ACTIVE_2_SQL =
		"country.active_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_B_BILLINGALLOWED_2 =
		"country.billingAllowed = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_S;
	private FinderPath _finderPathWithoutPaginationFindByC_A_S;
	private FinderPath _finderPathCountByC_A_S;
	private CollectionPersistenceFinder<Country>
		_collectionPersistenceFinderByC_A_S;

	/**
	 * Returns all the countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @return the matching countries
	 */
	@Override
	public List<Country> findByC_A_S(
		long companyId, boolean active, boolean shippingAllowed) {

		return findByC_A_S(
			companyId, active, shippingAllowed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	@Override
	public List<Country> findByC_A_S(
		long companyId, boolean active, boolean shippingAllowed, int start,
		int end) {

		return findByC_A_S(
			companyId, active, shippingAllowed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_A_S(
		long companyId, boolean active, boolean shippingAllowed, int start,
		int end, OrderByComparator<Country> orderByComparator) {

		return findByC_A_S(
			companyId, active, shippingAllowed, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_A_S(
		long companyId, boolean active, boolean shippingAllowed, int start,
		int end, OrderByComparator<Country> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_A_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, active, shippingAllowed}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByC_A_S_First(
			long companyId, boolean active, boolean shippingAllowed,
			OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {

		Country country = fetchByC_A_S_First(
			companyId, active, shippingAllowed, orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", shippingAllowed=");
		sb.append(shippingAllowed);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_A_S_First(
		long companyId, boolean active, boolean shippingAllowed,
		OrderByComparator<Country> orderByComparator) {

		List<Country> list = findByC_A_S(
			companyId, active, shippingAllowed, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the countries that the user has permission to view where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @return the matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_S(
		long companyId, boolean active, boolean shippingAllowed) {

		return filterFindByC_A_S(
			companyId, active, shippingAllowed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries that the user has permission to view where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_S(
		long companyId, boolean active, boolean shippingAllowed, int start,
		int end) {

		return filterFindByC_A_S(
			companyId, active, shippingAllowed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries that the user has permissions to view where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_S(
		long companyId, boolean active, boolean shippingAllowed, int start,
		int end, OrderByComparator<Country> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A_S(
				companyId, active, shippingAllowed, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_A_S(
					companyId, active, shippingAllowed, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_SHIPPINGALLOWED_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CountryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CountryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CountryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CountryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(shippingAllowed);

			return (List<Country>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 */
	@Override
	public void removeByC_A_S(
		long companyId, boolean active, boolean shippingAllowed) {

		for (Country country :
				findByC_A_S(
					companyId, active, shippingAllowed, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(country);
		}
	}

	/**
	 * Returns the number of countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @return the number of matching countries
	 */
	@Override
	public int countByC_A_S(
		long companyId, boolean active, boolean shippingAllowed) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_A_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, active, shippingAllowed});
		}
	}

	/**
	 * Returns the number of countries that the user has permission to view where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @return the number of matching countries that the user has permission to view
	 */
	@Override
	public int filterCountByC_A_S(
		long companyId, boolean active, boolean shippingAllowed) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_A_S(companyId, active, shippingAllowed);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<Country> countries = findByC_A_S(
				companyId, active, shippingAllowed);

			countries = InlineSQLHelperUtil.filter(countries);

			return countries.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_COUNTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_A_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_SHIPPINGALLOWED_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(shippingAllowed);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_A_S_COMPANYID_2 =
		"country.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_ACTIVE_2_SQL =
		"country.active_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_SHIPPINGALLOWED_2 =
		"country.shippingAllowed = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_B_G;
	private FinderPath _finderPathWithoutPaginationFindByC_A_B_G;
	private FinderPath _finderPathCountByC_A_B_G;
	private CollectionPersistenceFinder<Country>
		_collectionPersistenceFinderByC_A_B_G;

	/**
	 * Returns all the countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @return the matching countries
	 */
	@Override
	public List<Country> findByC_A_B_G(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled) {

		return findByC_A_B_G(
			countryId, active, billingAllowed, groupFilterEnabled,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	@Override
	public List<Country> findByC_A_B_G(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, int start, int end) {

		return findByC_A_B_G(
			countryId, active, billingAllowed, groupFilterEnabled, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_A_B_G(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return findByC_A_B_G(
			countryId, active, billingAllowed, groupFilterEnabled, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_A_B_G(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_A_B_G.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					countryId, active, billingAllowed, groupFilterEnabled
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first country in the ordered set where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByC_A_B_G_First(
			long countryId, boolean active, boolean billingAllowed,
			boolean groupFilterEnabled,
			OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {

		Country country = fetchByC_A_B_G_First(
			countryId, active, billingAllowed, groupFilterEnabled,
			orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("countryId=");
		sb.append(countryId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", billingAllowed=");
		sb.append(billingAllowed);

		sb.append(", groupFilterEnabled=");
		sb.append(groupFilterEnabled);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first country in the ordered set where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_A_B_G_First(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled,
		OrderByComparator<Country> orderByComparator) {

		List<Country> list = findByC_A_B_G(
			countryId, active, billingAllowed, groupFilterEnabled, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the countries that the user has permission to view where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @return the matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_B_G(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled) {

		return filterFindByC_A_B_G(
			countryId, active, billingAllowed, groupFilterEnabled,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries that the user has permission to view where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_B_G(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, int start, int end) {

		return filterFindByC_A_B_G(
			countryId, active, billingAllowed, groupFilterEnabled, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the countries that the user has permissions to view where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_B_G(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_A_B_G(
				countryId, active, billingAllowed, groupFilterEnabled, start,
				end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_A_B_G(
					countryId, active, billingAllowed, groupFilterEnabled,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_B_G_COUNTRYID_2);

		sb.append(_FINDER_COLUMN_C_A_B_G_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_B_G_BILLINGALLOWED_2);

		sb.append(_FINDER_COLUMN_C_A_B_G_GROUPFILTERENABLED_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CountryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CountryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CountryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CountryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(countryId);

			queryPos.add(active);

			queryPos.add(billingAllowed);

			queryPos.add(groupFilterEnabled);

			return (List<Country>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; from the database.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 */
	@Override
	public void removeByC_A_B_G(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled) {

		for (Country country :
				findByC_A_B_G(
					countryId, active, billingAllowed, groupFilterEnabled,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(country);
		}
	}

	/**
	 * Returns the number of countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @return the number of matching countries
	 */
	@Override
	public int countByC_A_B_G(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_A_B_G.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					countryId, active, billingAllowed, groupFilterEnabled
				});
		}
	}

	/**
	 * Returns the number of countries that the user has permission to view where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @return the number of matching countries that the user has permission to view
	 */
	@Override
	public int filterCountByC_A_B_G(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByC_A_B_G(
				countryId, active, billingAllowed, groupFilterEnabled);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<Country> countries = findByC_A_B_G(
				countryId, active, billingAllowed, groupFilterEnabled);

			countries = InlineSQLHelperUtil.filter(countries);

			return countries.size();
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_COUNTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_A_B_G_COUNTRYID_2);

		sb.append(_FINDER_COLUMN_C_A_B_G_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_B_G_BILLINGALLOWED_2);

		sb.append(_FINDER_COLUMN_C_A_B_G_GROUPFILTERENABLED_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(countryId);

			queryPos.add(active);

			queryPos.add(billingAllowed);

			queryPos.add(groupFilterEnabled);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_A_B_G_COUNTRYID_2 =
		"country.countryId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_B_G_ACTIVE_2_SQL =
		"country.active_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_B_G_BILLINGALLOWED_2 =
		"country.billingAllowed = ? AND ";

	private static final String _FINDER_COLUMN_C_A_B_G_GROUPFILTERENABLED_2 =
		"country.groupFilterEnabled = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_G_S;
	private FinderPath _finderPathWithoutPaginationFindByC_A_G_S;
	private FinderPath _finderPathCountByC_A_G_S;
	private CollectionPersistenceFinder<Country>
		_collectionPersistenceFinderByC_A_G_S;

	/**
	 * Returns all the countries where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @return the matching countries
	 */
	@Override
	public List<Country> findByC_A_G_S(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed) {

		return findByC_A_G_S(
			countryId, active, groupFilterEnabled, shippingAllowed,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	@Override
	public List<Country> findByC_A_G_S(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed, int start, int end) {

		return findByC_A_G_S(
			countryId, active, groupFilterEnabled, shippingAllowed, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the countries where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_A_G_S(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return findByC_A_G_S(
			countryId, active, groupFilterEnabled, shippingAllowed, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the countries where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_A_G_S(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_A_G_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					countryId, active, groupFilterEnabled, shippingAllowed
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first country in the ordered set where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByC_A_G_S_First(
			long countryId, boolean active, boolean groupFilterEnabled,
			boolean shippingAllowed,
			OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {

		Country country = fetchByC_A_G_S_First(
			countryId, active, groupFilterEnabled, shippingAllowed,
			orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("countryId=");
		sb.append(countryId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", groupFilterEnabled=");
		sb.append(groupFilterEnabled);

		sb.append(", shippingAllowed=");
		sb.append(shippingAllowed);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first country in the ordered set where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_A_G_S_First(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed, OrderByComparator<Country> orderByComparator) {

		List<Country> list = findByC_A_G_S(
			countryId, active, groupFilterEnabled, shippingAllowed, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the countries that the user has permission to view where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @return the matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_G_S(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed) {

		return filterFindByC_A_G_S(
			countryId, active, groupFilterEnabled, shippingAllowed,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries that the user has permission to view where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_G_S(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed, int start, int end) {

		return filterFindByC_A_G_S(
			countryId, active, groupFilterEnabled, shippingAllowed, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the countries that the user has permissions to view where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_G_S(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_A_G_S(
				countryId, active, groupFilterEnabled, shippingAllowed, start,
				end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_A_G_S(
					countryId, active, groupFilterEnabled, shippingAllowed,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_G_S_COUNTRYID_2);

		sb.append(_FINDER_COLUMN_C_A_G_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_G_S_GROUPFILTERENABLED_2);

		sb.append(_FINDER_COLUMN_C_A_G_S_SHIPPINGALLOWED_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CountryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CountryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CountryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CountryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(countryId);

			queryPos.add(active);

			queryPos.add(groupFilterEnabled);

			queryPos.add(shippingAllowed);

			return (List<Country>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the countries where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63; from the database.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 */
	@Override
	public void removeByC_A_G_S(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed) {

		for (Country country :
				findByC_A_G_S(
					countryId, active, groupFilterEnabled, shippingAllowed,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(country);
		}
	}

	/**
	 * Returns the number of countries where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @return the number of matching countries
	 */
	@Override
	public int countByC_A_G_S(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_A_G_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					countryId, active, groupFilterEnabled, shippingAllowed
				});
		}
	}

	/**
	 * Returns the number of countries that the user has permission to view where countryId = &#63; and active = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @return the number of matching countries that the user has permission to view
	 */
	@Override
	public int filterCountByC_A_G_S(
		long countryId, boolean active, boolean groupFilterEnabled,
		boolean shippingAllowed) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByC_A_G_S(
				countryId, active, groupFilterEnabled, shippingAllowed);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<Country> countries = findByC_A_G_S(
				countryId, active, groupFilterEnabled, shippingAllowed);

			countries = InlineSQLHelperUtil.filter(countries);

			return countries.size();
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_COUNTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_A_G_S_COUNTRYID_2);

		sb.append(_FINDER_COLUMN_C_A_G_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_G_S_GROUPFILTERENABLED_2);

		sb.append(_FINDER_COLUMN_C_A_G_S_SHIPPINGALLOWED_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(countryId);

			queryPos.add(active);

			queryPos.add(groupFilterEnabled);

			queryPos.add(shippingAllowed);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_A_G_S_COUNTRYID_2 =
		"country.countryId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_G_S_ACTIVE_2_SQL =
		"country.active_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_G_S_GROUPFILTERENABLED_2 =
		"country.groupFilterEnabled = ? AND ";

	private static final String _FINDER_COLUMN_C_A_G_S_SHIPPINGALLOWED_2 =
		"country.shippingAllowed = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_B_G_S;
	private FinderPath _finderPathWithoutPaginationFindByC_A_B_G_S;
	private FinderPath _finderPathCountByC_A_B_G_S;
	private CollectionPersistenceFinder<Country>
		_collectionPersistenceFinderByC_A_B_G_S;

	/**
	 * Returns all the countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @return the matching countries
	 */
	@Override
	public List<Country> findByC_A_B_G_S(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed) {

		return findByC_A_B_G_S(
			countryId, active, billingAllowed, groupFilterEnabled,
			shippingAllowed, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	@Override
	public List<Country> findByC_A_B_G_S(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed, int start,
		int end) {

		return findByC_A_B_G_S(
			countryId, active, billingAllowed, groupFilterEnabled,
			shippingAllowed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_A_B_G_S(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return findByC_A_B_G_S(
			countryId, active, billingAllowed, groupFilterEnabled,
			shippingAllowed, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	@Override
	public List<Country> findByC_A_B_G_S(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_A_B_G_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					countryId, active, billingAllowed, groupFilterEnabled,
					shippingAllowed
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first country in the ordered set where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	@Override
	public Country findByC_A_B_G_S_First(
			long countryId, boolean active, boolean billingAllowed,
			boolean groupFilterEnabled, boolean shippingAllowed,
			OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException {

		Country country = fetchByC_A_B_G_S_First(
			countryId, active, billingAllowed, groupFilterEnabled,
			shippingAllowed, orderByComparator);

		if (country != null) {
			return country;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("countryId=");
		sb.append(countryId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", billingAllowed=");
		sb.append(billingAllowed);

		sb.append(", groupFilterEnabled=");
		sb.append(groupFilterEnabled);

		sb.append(", shippingAllowed=");
		sb.append(shippingAllowed);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first country in the ordered set where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public Country fetchByC_A_B_G_S_First(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed,
		OrderByComparator<Country> orderByComparator) {

		List<Country> list = findByC_A_B_G_S(
			countryId, active, billingAllowed, groupFilterEnabled,
			shippingAllowed, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the countries that the user has permission to view where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @return the matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_B_G_S(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed) {

		return filterFindByC_A_B_G_S(
			countryId, active, billingAllowed, groupFilterEnabled,
			shippingAllowed, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the countries that the user has permission to view where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_B_G_S(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed, int start,
		int end) {

		return filterFindByC_A_B_G_S(
			countryId, active, billingAllowed, groupFilterEnabled,
			shippingAllowed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the countries that the user has permissions to view where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries that the user has permission to view
	 */
	@Override
	public List<Country> filterFindByC_A_B_G_S(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_A_B_G_S(
				countryId, active, billingAllowed, groupFilterEnabled,
				shippingAllowed, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_A_B_G_S(
					countryId, active, billingAllowed, groupFilterEnabled,
					shippingAllowed, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_B_G_S_COUNTRYID_2);

		sb.append(_FINDER_COLUMN_C_A_B_G_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_B_G_S_BILLINGALLOWED_2);

		sb.append(_FINDER_COLUMN_C_A_B_G_S_GROUPFILTERENABLED_2);

		sb.append(_FINDER_COLUMN_C_A_B_G_S_SHIPPINGALLOWED_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CountryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CountryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CountryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CountryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(countryId);

			queryPos.add(active);

			queryPos.add(billingAllowed);

			queryPos.add(groupFilterEnabled);

			queryPos.add(shippingAllowed);

			return (List<Country>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63; from the database.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 */
	@Override
	public void removeByC_A_B_G_S(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed) {

		for (Country country :
				findByC_A_B_G_S(
					countryId, active, billingAllowed, groupFilterEnabled,
					shippingAllowed, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(country);
		}
	}

	/**
	 * Returns the number of countries where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @return the number of matching countries
	 */
	@Override
	public int countByC_A_B_G_S(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			return _collectionPersistenceFinderByC_A_B_G_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					countryId, active, billingAllowed, groupFilterEnabled,
					shippingAllowed
				});
		}
	}

	/**
	 * Returns the number of countries that the user has permission to view where countryId = &#63; and active = &#63; and billingAllowed = &#63; and groupFilterEnabled = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the country ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param groupFilterEnabled the group filter enabled
	 * @param shippingAllowed the shipping allowed
	 * @return the number of matching countries that the user has permission to view
	 */
	@Override
	public int filterCountByC_A_B_G_S(
		long countryId, boolean active, boolean billingAllowed,
		boolean groupFilterEnabled, boolean shippingAllowed) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByC_A_B_G_S(
				countryId, active, billingAllowed, groupFilterEnabled,
				shippingAllowed);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<Country> countries = findByC_A_B_G_S(
				countryId, active, billingAllowed, groupFilterEnabled,
				shippingAllowed);

			countries = InlineSQLHelperUtil.filter(countries);

			return countries.size();
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_FILTER_SQL_COUNT_COUNTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_A_B_G_S_COUNTRYID_2);

		sb.append(_FINDER_COLUMN_C_A_B_G_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_B_G_S_BILLINGALLOWED_2);

		sb.append(_FINDER_COLUMN_C_A_B_G_S_GROUPFILTERENABLED_2);

		sb.append(_FINDER_COLUMN_C_A_B_G_S_SHIPPINGALLOWED_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), Country.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(countryId);

			queryPos.add(active);

			queryPos.add(billingAllowed);

			queryPos.add(groupFilterEnabled);

			queryPos.add(shippingAllowed);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_A_B_G_S_COUNTRYID_2 =
		"country.countryId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_B_G_S_ACTIVE_2_SQL =
		"country.active_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_B_G_S_BILLINGALLOWED_2 =
		"country.billingAllowed = ? AND ";

	private static final String _FINDER_COLUMN_C_A_B_G_S_GROUPFILTERENABLED_2 =
		"country.groupFilterEnabled = ? AND ";

	private static final String _FINDER_COLUMN_C_A_B_G_S_SHIPPINGALLOWED_2 =
		"country.shippingAllowed = ?";

	public CountryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put("idd", "idd_");
		dbColumnNames.put("number", "number_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Country.class);

		setModelImplClass(CountryImpl.class);
		setModelPKClass(long.class);

		setTable(CountryTable.INSTANCE);
	}

	/**
	 * Caches the country in the entity cache if it is enabled.
	 *
	 * @param country the country
	 */
	@Override
	public void cacheResult(Country country) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					country.getCtCollectionId())) {

			EntityCacheUtil.putResult(
				CountryImpl.class, country.getPrimaryKey(), country);

			FinderCacheUtil.putResult(
				_finderPathFetchByC_A2,
				new Object[] {country.getCompanyId(), country.getA2()},
				country);

			FinderCacheUtil.putResult(
				_finderPathFetchByC_A3,
				new Object[] {country.getCompanyId(), country.getA3()},
				country);

			FinderCacheUtil.putResult(
				_finderPathFetchByC_Name,
				new Object[] {country.getCompanyId(), country.getName()},
				country);

			FinderCacheUtil.putResult(
				_finderPathFetchByC_Number,
				new Object[] {country.getCompanyId(), country.getNumber()},
				country);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the countries in the entity cache if it is enabled.
	 *
	 * @param countries the countries
	 */
	@Override
	public void cacheResult(List<Country> countries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (countries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Country country : countries) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						country.getCtCollectionId())) {

				if (EntityCacheUtil.getResult(
						CountryImpl.class, country.getPrimaryKey()) == null) {

					cacheResult(country);
				}
			}
		}
	}

	/**
	 * Clears the cache for all countries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(CountryImpl.class);

		FinderCacheUtil.clearCache(CountryImpl.class);
	}

	/**
	 * Clears the cache for the country.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Country country) {
		EntityCacheUtil.removeResult(CountryImpl.class, country);
	}

	@Override
	public void clearCache(List<Country> countries) {
		for (Country country : countries) {
			EntityCacheUtil.removeResult(CountryImpl.class, country);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(CountryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(CountryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(CountryModelImpl countryModelImpl) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					countryModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				countryModelImpl.getCompanyId(), countryModelImpl.getA2()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByC_A2, args, countryModelImpl);

			args = new Object[] {
				countryModelImpl.getCompanyId(), countryModelImpl.getA3()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByC_A3, args, countryModelImpl);

			args = new Object[] {
				countryModelImpl.getCompanyId(), countryModelImpl.getName()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByC_Name, args, countryModelImpl);

			args = new Object[] {
				countryModelImpl.getCompanyId(), countryModelImpl.getNumber()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByC_Number, args, countryModelImpl);
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

		String uuid = PortalUUIDUtil.generate();

		country.setUuid(uuid);

		country.setCompanyId(CompanyThreadLocal.getCompanyId());

		return country;
	}

	/**
	 * Removes the country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param countryId the primary key of the country
	 * @return the country that was removed
	 * @throws NoSuchCountryException if a country with the primary key could not be found
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
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	@Override
	public Country remove(Serializable primaryKey)
		throws NoSuchCountryException {

		Session session = null;

		try {
			session = openSession();

			Country country = (Country)session.get(
				CountryImpl.class, primaryKey);

			if (country == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCountryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(country);
		}
		catch (NoSuchCountryException noSuchEntityException) {
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
	protected Country removeImpl(Country country) {
		countryLocalizationPersistence.removeByCountryId(
			country.getCountryId());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(country)) {
				country = (Country)session.get(
					CountryImpl.class, country.getPrimaryKeyObj());
			}

			if ((country != null) &&
				CTPersistenceHelperUtil.isRemove(country)) {

				session.delete(country);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
	public Country updateImpl(Country country) {
		boolean isNew = country.isNew();

		if (!(country instanceof CountryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(country.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(country);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in country proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Country implementation " +
					country.getClass());
		}

		CountryModelImpl countryModelImpl = (CountryModelImpl)country;

		if (Validator.isNull(country.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			country.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (country.getCreateDate() == null)) {
			if (serviceContext == null) {
				country.setCreateDate(date);
			}
			else {
				country.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!countryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				country.setModifiedDate(date);
			}
			else {
				country.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(country)) {
				if (!isNew) {
					session.evict(
						CountryImpl.class, country.getPrimaryKeyObj());
				}

				session.save(country);
			}
			else {
				country = (Country)session.merge(country);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			CountryImpl.class, countryModelImpl, false, true);

		cacheUniqueFindersCache(countryModelImpl);

		if (isNew) {
			country.setNew(false);
		}

		country.resetOriginalValues();

		return country;
	}

	/**
	 * Returns the country with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the country
	 * @return the country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	@Override
	public Country findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCountryException {

		Country country = fetchByPrimaryKey(primaryKey);

		if (country == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCountryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return country;
	}

	/**
	 * Returns the country with the primary key or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param countryId the primary key of the country
	 * @return the country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
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
		if (CTPersistenceHelperUtil.isProductionMode(
				Country.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		Country country = (Country)EntityCacheUtil.getResult(
			CountryImpl.class, primaryKey);

		if (country != null) {
			return country;
		}

		Session session = null;

		try {
			session = openSession();

			country = (Country)session.get(CountryImpl.class, primaryKey);

			if (country != null) {
				cacheResult(country);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
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

		if (CTPersistenceHelperUtil.isProductionMode(Country.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

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
			try (SafeCloseable safeCloseable =
					CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
						Country.class, primaryKey)) {

				Country country = (Country)EntityCacheUtil.getResult(
					CountryImpl.class, primaryKey);

				if (country == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, country);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
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

			for (Country country : (List<Country>)query.list()) {
				map.put(country.getPrimaryKeyObj(), country);

				cacheResult(country);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of countries
	 */
	@Override
	public List<Country> findAll(
		int start, int end, OrderByComparator<Country> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of countries
	 */
	@Override
	public List<Country> findAll(
		int start, int end, OrderByComparator<Country> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

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

			List<Country> list = null;

			if (useFinderCache) {
				list = (List<Country>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_COUNTRY);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_COUNTRY;

					sql = sql.concat(CountryModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<Country>)QueryUtil.list(
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
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Country.class)) {

			Long count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_COUNTRY);

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
		return "countryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COUNTRY;
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
		return CountryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "Country";
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
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("defaultLanguageId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("a2");
		ctMergeColumnNames.add("a3");
		ctMergeColumnNames.add("active_");
		ctMergeColumnNames.add("billingAllowed");
		ctMergeColumnNames.add("groupFilterEnabled");
		ctMergeColumnNames.add("idd_");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("number_");
		ctMergeColumnNames.add("position");
		ctMergeColumnNames.add("shippingAllowed");
		ctMergeColumnNames.add("subjectToVAT");
		ctMergeColumnNames.add("zipRequired");
		ctMergeColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("countryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"companyId", "a2"});

		_uniqueIndexColumnNames.add(new String[] {"companyId", "a3"});

		_uniqueIndexColumnNames.add(new String[] {"companyId", "name"});

		_uniqueIndexColumnNames.add(new String[] {"companyId", "number_"});
	}

	/**
	 * Initializes the country persistence.
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

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_collectionPersistenceFinderByUuid = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByUuid,
			_finderPathWithoutPaginationFindByUuid, _finderPathCountByUuid,
			_SQL_SELECT_COUNTRY_WHERE, _SQL_COUNT_COUNTRY_WHERE,
			CountryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"country.", "uuid", FinderColumn.Type.STRING, "=", true, true,
				Country::getUuid));

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_collectionPersistenceFinderByUuid_C =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_C,
				_finderPathWithoutPaginationFindByUuid_C,
				_finderPathCountByUuid_C, _SQL_SELECT_COUNTRY_WHERE,
				_SQL_COUNT_COUNTRY_WHERE, CountryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"country.", "uuid", FinderColumn.Type.STRING, "=", true,
					false, Country::getUuid),
				new FinderColumn<>(
					"country.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Country::getCompanyId));

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_collectionPersistenceFinderByCompanyId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCompanyId,
				_finderPathWithoutPaginationFindByCompanyId,
				_finderPathCountByCompanyId, _SQL_SELECT_COUNTRY_WHERE,
				_SQL_COUNT_COUNTRY_WHERE, CountryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"country.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Country::getCompanyId));

		_finderPathWithPaginationFindByActive = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActive",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"active_"}, true);

		_finderPathWithoutPaginationFindByActive = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActive",
			new String[] {Boolean.class.getName()}, new String[] {"active_"},
			true);

		_finderPathCountByActive = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActive",
			new String[] {Boolean.class.getName()}, new String[] {"active_"},
			false);

		_collectionPersistenceFinderByActive =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByActive,
				_finderPathWithoutPaginationFindByActive,
				_finderPathCountByActive, _SQL_SELECT_COUNTRY_WHERE,
				_SQL_COUNT_COUNTRY_WHERE, CountryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"country.", "active", FinderColumn.Type.BOOLEAN, "=", true,
					true, Country::isActive));

		_finderPathFetchByC_A2 = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_A2",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "a2"}, true);

		_uniquePersistenceFinderByC_A2 = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_A2, _SQL_SELECT_COUNTRY_WHERE,
			new FinderColumn<>(
				"country.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, Country::getCompanyId),
			new FinderColumn<>(
				"country.", "a2", FinderColumn.Type.STRING, "=", true, true,
				Country::getA2));

		_finderPathFetchByC_A3 = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_A3",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "a3"}, true);

		_uniquePersistenceFinderByC_A3 = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_A3, _SQL_SELECT_COUNTRY_WHERE,
			new FinderColumn<>(
				"country.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, Country::getCompanyId),
			new FinderColumn<>(
				"country.", "a3", FinderColumn.Type.STRING, "=", true, true,
				Country::getA3));

		_finderPathWithPaginationFindByC_Active = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_Active",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_"}, true);

		_finderPathWithoutPaginationFindByC_Active = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_Active",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "active_"}, true);

		_finderPathCountByC_Active = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_Active",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "active_"}, false);

		_collectionPersistenceFinderByC_Active =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_Active,
				_finderPathWithoutPaginationFindByC_Active,
				_finderPathCountByC_Active, _SQL_SELECT_COUNTRY_WHERE,
				_SQL_COUNT_COUNTRY_WHERE, CountryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"country.", "companyId", FinderColumn.Type.LONG, "=", true,
					false, Country::getCompanyId),
				new FinderColumn<>(
					"country.", "active", FinderColumn.Type.BOOLEAN, "=", true,
					true, Country::isActive));

		_finderPathFetchByC_Name = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_Name",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		_uniquePersistenceFinderByC_Name = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_Name, _SQL_SELECT_COUNTRY_WHERE,
			new FinderColumn<>(
				"country.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, Country::getCompanyId),
			new FinderColumn<>(
				"country.", "name", FinderColumn.Type.STRING, "=", true, true,
				Country::getName));

		_finderPathFetchByC_Number = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_Number",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "number_"}, true);

		_uniquePersistenceFinderByC_Number = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_Number, _SQL_SELECT_COUNTRY_WHERE,
			new FinderColumn<>(
				"country.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, Country::getCompanyId),
			new FinderColumn<>(
				"country.", "number", FinderColumn.Type.STRING, "=", true, true,
				Country::getNumber));

		_finderPathWithPaginationFindByC_A_B = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_B",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_", "billingAllowed"}, true);

		_finderPathWithoutPaginationFindByC_A_B = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_B",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "active_", "billingAllowed"}, true);

		_finderPathCountByC_A_B = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_B",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "active_", "billingAllowed"}, false);

		_collectionPersistenceFinderByC_A_B = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_A_B,
			_finderPathWithoutPaginationFindByC_A_B, _finderPathCountByC_A_B,
			_SQL_SELECT_COUNTRY_WHERE, _SQL_COUNT_COUNTRY_WHERE,
			CountryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"country.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, Country::getCompanyId),
			new FinderColumn<>(
				"country.", "active", FinderColumn.Type.BOOLEAN, "=", true,
				false, Country::isActive),
			new FinderColumn<>(
				"country.", "billingAllowed", FinderColumn.Type.BOOLEAN, "=",
				true, true, Country::isBillingAllowed));

		_finderPathWithPaginationFindByC_A_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_", "shippingAllowed"}, true);

		_finderPathWithoutPaginationFindByC_A_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "active_", "shippingAllowed"}, true);

		_finderPathCountByC_A_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "active_", "shippingAllowed"}, false);

		_collectionPersistenceFinderByC_A_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_A_S,
			_finderPathWithoutPaginationFindByC_A_S, _finderPathCountByC_A_S,
			_SQL_SELECT_COUNTRY_WHERE, _SQL_COUNT_COUNTRY_WHERE,
			CountryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"country.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, Country::getCompanyId),
			new FinderColumn<>(
				"country.", "active", FinderColumn.Type.BOOLEAN, "=", true,
				false, Country::isActive),
			new FinderColumn<>(
				"country.", "shippingAllowed", FinderColumn.Type.BOOLEAN, "=",
				true, true, Country::isShippingAllowed));

		_finderPathWithPaginationFindByC_A_B_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_B_G",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"countryId", "active_", "billingAllowed", "groupFilterEnabled"
			},
			true);

		_finderPathWithoutPaginationFindByC_A_B_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_B_G",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"countryId", "active_", "billingAllowed", "groupFilterEnabled"
			},
			true);

		_finderPathCountByC_A_B_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_B_G",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"countryId", "active_", "billingAllowed", "groupFilterEnabled"
			},
			false);

		_collectionPersistenceFinderByC_A_B_G =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_A_B_G,
				_finderPathWithoutPaginationFindByC_A_B_G,
				_finderPathCountByC_A_B_G, _SQL_SELECT_COUNTRY_WHERE,
				_SQL_COUNT_COUNTRY_WHERE, CountryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"country.", "countryId", FinderColumn.Type.LONG, "=", true,
					false, Country::getCountryId),
				new FinderColumn<>(
					"country.", "active", FinderColumn.Type.BOOLEAN, "=", true,
					false, Country::isActive),
				new FinderColumn<>(
					"country.", "billingAllowed", FinderColumn.Type.BOOLEAN,
					"=", true, false, Country::isBillingAllowed),
				new FinderColumn<>(
					"country.", "groupFilterEnabled", FinderColumn.Type.BOOLEAN,
					"=", true, true, Country::isGroupFilterEnabled));

		_finderPathWithPaginationFindByC_A_G_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_G_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"countryId", "active_", "groupFilterEnabled", "shippingAllowed"
			},
			true);

		_finderPathWithoutPaginationFindByC_A_G_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_G_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"countryId", "active_", "groupFilterEnabled", "shippingAllowed"
			},
			true);

		_finderPathCountByC_A_G_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_G_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"countryId", "active_", "groupFilterEnabled", "shippingAllowed"
			},
			false);

		_collectionPersistenceFinderByC_A_G_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_A_G_S,
				_finderPathWithoutPaginationFindByC_A_G_S,
				_finderPathCountByC_A_G_S, _SQL_SELECT_COUNTRY_WHERE,
				_SQL_COUNT_COUNTRY_WHERE, CountryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"country.", "countryId", FinderColumn.Type.LONG, "=", true,
					false, Country::getCountryId),
				new FinderColumn<>(
					"country.", "active", FinderColumn.Type.BOOLEAN, "=", true,
					false, Country::isActive),
				new FinderColumn<>(
					"country.", "groupFilterEnabled", FinderColumn.Type.BOOLEAN,
					"=", true, false, Country::isGroupFilterEnabled),
				new FinderColumn<>(
					"country.", "shippingAllowed", FinderColumn.Type.BOOLEAN,
					"=", true, true, Country::isShippingAllowed));

		_finderPathWithPaginationFindByC_A_B_G_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_B_G_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"countryId", "active_", "billingAllowed", "groupFilterEnabled",
				"shippingAllowed"
			},
			true);

		_finderPathWithoutPaginationFindByC_A_B_G_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_B_G_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {
				"countryId", "active_", "billingAllowed", "groupFilterEnabled",
				"shippingAllowed"
			},
			true);

		_finderPathCountByC_A_B_G_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_B_G_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {
				"countryId", "active_", "billingAllowed", "groupFilterEnabled",
				"shippingAllowed"
			},
			false);

		_collectionPersistenceFinderByC_A_B_G_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_A_B_G_S,
				_finderPathWithoutPaginationFindByC_A_B_G_S,
				_finderPathCountByC_A_B_G_S, _SQL_SELECT_COUNTRY_WHERE,
				_SQL_COUNT_COUNTRY_WHERE, CountryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"country.", "countryId", FinderColumn.Type.LONG, "=", true,
					false, Country::getCountryId),
				new FinderColumn<>(
					"country.", "active", FinderColumn.Type.BOOLEAN, "=", true,
					false, Country::isActive),
				new FinderColumn<>(
					"country.", "billingAllowed", FinderColumn.Type.BOOLEAN,
					"=", true, false, Country::isBillingAllowed),
				new FinderColumn<>(
					"country.", "groupFilterEnabled", FinderColumn.Type.BOOLEAN,
					"=", true, false, Country::isGroupFilterEnabled),
				new FinderColumn<>(
					"country.", "shippingAllowed", FinderColumn.Type.BOOLEAN,
					"=", true, true, Country::isShippingAllowed));

		CountryUtil.setPersistence(this);
	}

	public void destroy() {
		CountryUtil.setPersistence(null);

		EntityCacheUtil.removeCache(CountryImpl.class.getName());
	}

	@BeanReference(type = CountryLocalizationPersistence.class)
	protected CountryLocalizationPersistence countryLocalizationPersistence;

	private static final String _SQL_SELECT_COUNTRY =
		"SELECT country FROM Country country";

	private static final String _SQL_SELECT_COUNTRY_WHERE =
		"SELECT country FROM Country country WHERE ";

	private static final String _SQL_COUNT_COUNTRY =
		"SELECT COUNT(country) FROM Country country";

	private static final String _SQL_COUNT_COUNTRY_WHERE =
		"SELECT COUNT(country) FROM Country country WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"country.countryId";

	private static final String _FILTER_SQL_SELECT_COUNTRY_WHERE =
		"SELECT DISTINCT {country.*} FROM Country country WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {Country.*} FROM (SELECT DISTINCT country.countryId FROM Country country WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COUNTRY_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN Country ON TEMP_TABLE.countryId = Country.countryId";

	private static final String _FILTER_SQL_COUNT_COUNTRY_WHERE =
		"SELECT COUNT(DISTINCT country.countryId) AS COUNT_VALUE FROM Country country WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "country";

	private static final String _FILTER_ENTITY_TABLE = "Country";

	private static final String _ORDER_BY_ENTITY_ALIAS = "country.";

	private static final String _ORDER_BY_ENTITY_TABLE = "Country.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Country exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Country exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CountryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active", "idd", "number"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-2066515850