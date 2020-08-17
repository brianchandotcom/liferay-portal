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

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.exception.NoSuchEntryVersionException;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.model.FragmentEntryVersionTable;
import com.liferay.fragment.model.impl.FragmentEntryVersionImpl;
import com.liferay.fragment.model.impl.FragmentEntryVersionModelImpl;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.fragment.service.persistence.impl.constants.FragmentPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

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

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the fragment entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = FragmentEntryVersionPersistence.class)
public class FragmentEntryVersionPersistenceImpl
	extends BasePersistenceImpl<FragmentEntryVersion>
	implements FragmentEntryVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FragmentEntryVersionUtil</code> to access the fragment entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FragmentEntryVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByFragmentEntryId;
	private FinderPath _finderPathWithoutPaginationFindByFragmentEntryId;
	private FinderPath _finderPathCountByFragmentEntryId;

	/**
	 * Returns all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId) {

		return findByFragmentEntryId(
			fragmentEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId, int start, int end) {

		return findByFragmentEntryId(fragmentEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByFragmentEntryId(
			fragmentEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByFragmentEntryId;
				finderArgs = new Object[] {fragmentEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByFragmentEntryId;
			finderArgs = new Object[] {
				fragmentEntryId, start, end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if (fragmentEntryId !=
							fragmentEntryVersion.getFragmentEntryId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_FRAGMENTENTRYID_FRAGMENTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentEntryId);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentEntryId_First(
			long fragmentEntryId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByFragmentEntryId_First(fragmentEntryId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentEntryId_First(
		long fragmentEntryId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByFragmentEntryId(
			fragmentEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentEntryId_Last(
			long fragmentEntryId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByFragmentEntryId_Last(
			fragmentEntryId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentEntryId_Last(
		long fragmentEntryId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByFragmentEntryId(fragmentEntryId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByFragmentEntryId(
			fragmentEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByFragmentEntryId_PrevAndNext(
			long fragmentEntryVersionId, long fragmentEntryId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByFragmentEntryId_PrevAndNext(
				session, fragmentEntryVersion, fragmentEntryId,
				orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByFragmentEntryId_PrevAndNext(
				session, fragmentEntryVersion, fragmentEntryId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByFragmentEntryId_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_FRAGMENTENTRYID_FRAGMENTENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(fragmentEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where fragmentEntryId = &#63; from the database.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 */
	@Override
	public void removeByFragmentEntryId(long fragmentEntryId) {
		for (FragmentEntryVersion fragmentEntryVersion :
				findByFragmentEntryId(
					fragmentEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByFragmentEntryId(long fragmentEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFragmentEntryId;

			finderArgs = new Object[] {fragmentEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_FRAGMENTENTRYID_FRAGMENTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentEntryId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String
		_FINDER_COLUMN_FRAGMENTENTRYID_FRAGMENTENTRYID_2 =
			"fragmentEntryVersion.fragmentEntryId = ?";

	private FinderPath _finderPathFetchByFragmentEntryId_Version;
	private FinderPath _finderPathCountByFragmentEntryId_Version;

	/**
	 * Returns the fragment entry version where fragmentEntryId = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentEntryId_Version(
			long fragmentEntryId, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByFragmentEntryId_Version(fragmentEntryId, version);

		if (fragmentEntryVersion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("fragmentEntryId=");
			sb.append(fragmentEntryId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryVersionException(sb.toString());
		}

		return fragmentEntryVersion;
	}

	/**
	 * Returns the fragment entry version where fragmentEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentEntryId_Version(
		long fragmentEntryId, int version) {

		return fetchByFragmentEntryId_Version(fragmentEntryId, version, true);
	}

	/**
	 * Returns the fragment entry version where fragmentEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentEntryId_Version(
		long fragmentEntryId, int version, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {fragmentEntryId, version};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByFragmentEntryId_Version, finderArgs, this);
		}

		if (result instanceof FragmentEntryVersion) {
			FragmentEntryVersion fragmentEntryVersion =
				(FragmentEntryVersion)result;

			if ((fragmentEntryId !=
					fragmentEntryVersion.getFragmentEntryId()) ||
				(version != fragmentEntryVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_FRAGMENTENTRYID_VERSION_FRAGMENTENTRYID_2);

			sb.append(_FINDER_COLUMN_FRAGMENTENTRYID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentEntryId);

				queryPos.add(version);

				List<FragmentEntryVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByFragmentEntryId_Version,
							finderArgs, list);
					}
				}
				else {
					FragmentEntryVersion fragmentEntryVersion = list.get(0);

					result = fragmentEntryVersion;

					cacheResult(fragmentEntryVersion);
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
			return (FragmentEntryVersion)result;
		}
	}

	/**
	 * Removes the fragment entry version where fragmentEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the fragment entry version that was removed
	 */
	@Override
	public FragmentEntryVersion removeByFragmentEntryId_Version(
			long fragmentEntryId, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			findByFragmentEntryId_Version(fragmentEntryId, version);

		return remove(fragmentEntryVersion);
	}

	/**
	 * Returns the number of fragment entry versions where fragmentEntryId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByFragmentEntryId_Version(
		long fragmentEntryId, int version) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFragmentEntryId_Version;

			finderArgs = new Object[] {fragmentEntryId, version};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_FRAGMENTENTRYID_VERSION_FRAGMENTENTRYID_2);

			sb.append(_FINDER_COLUMN_FRAGMENTENTRYID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentEntryId);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String
		_FINDER_COLUMN_FRAGMENTENTRYID_VERSION_FRAGMENTENTRYID_2 =
			"fragmentEntryVersion.fragmentEntryId = ? AND ";

	private static final String
		_FINDER_COLUMN_FRAGMENTENTRYID_VERSION_VERSION_2 =
			"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the fragment entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if (!uuid.equals(fragmentEntryVersion.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_First(
			String uuid,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_First(
			uuid, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_First(
		String uuid,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_Last(
			String uuid,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_Last(
			uuid, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_Last(
		String uuid,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByUuid_PrevAndNext(
			long fragmentEntryVersionId, String uuid,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		uuid = Objects.toString(uuid, "");

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, fragmentEntryVersion, uuid, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByUuid_PrevAndNext(
				session, fragmentEntryVersion, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByUuid_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion, String uuid,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FragmentEntryVersion fragmentEntryVersion :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"fragmentEntryVersion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(fragmentEntryVersion.uuid IS NULL OR fragmentEntryVersion.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Version;
	private FinderPath _finderPathCountByUuid_Version;

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version) {

		return findByUuid_Version(
			uuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end) {

		return findByUuid_Version(uuid, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByUuid_Version(
			uuid, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_Version;
				finderArgs = new Object[] {uuid, version};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_Version;
			finderArgs = new Object[] {
				uuid, version, start, end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if (!uuid.equals(fragmentEntryVersion.getUuid()) ||
						(version != fragmentEntryVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(version);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_Version_First(
			String uuid, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_Version_First(
			uuid, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_Version_First(
		String uuid, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByUuid_Version(
			uuid, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_Version_Last(
			String uuid, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_Version_Last(
			uuid, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_Version_Last(
		String uuid, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByUuid_Version(uuid, version);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByUuid_Version(
			uuid, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByUuid_Version_PrevAndNext(
			long fragmentEntryVersionId, String uuid, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		uuid = Objects.toString(uuid, "");

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByUuid_Version_PrevAndNext(
				session, fragmentEntryVersion, uuid, version, orderByComparator,
				true);

			array[1] = fragmentEntryVersion;

			array[2] = getByUuid_Version_PrevAndNext(
				session, fragmentEntryVersion, uuid, version, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByUuid_Version_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion, String uuid,
		int version, OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	@Override
	public void removeByUuid_Version(String uuid, int version) {
		for (FragmentEntryVersion fragmentEntryVersion :
				findByUuid_Version(
					uuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUuid_Version(String uuid, int version) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_Version;

			finderArgs = new Object[] {uuid, version};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_VERSION_UUID_2 =
		"fragmentEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_VERSION_UUID_3 =
		"(fragmentEntryVersion.uuid IS NULL OR fragmentEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUUID_G;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUUID_G(String uuid, long groupId) {
		return findByUUID_G(
			uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUUID_G;
				finderArgs = new Object[] {uuid, groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUUID_G;
			finderArgs = new Object[] {
				uuid, groupId, start, end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if (!uuid.equals(fragmentEntryVersion.getUuid()) ||
						(groupId != fragmentEntryVersion.getGroupId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUUID_G_First(
			uuid, groupId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByUUID_G(
			uuid, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUUID_G_Last(
			String uuid, long groupId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUUID_G_Last(
			uuid, groupId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUUID_G_Last(
		String uuid, long groupId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByUUID_G(uuid, groupId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByUUID_G(
			uuid, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByUUID_G_PrevAndNext(
			long fragmentEntryVersionId, String uuid, long groupId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		uuid = Objects.toString(uuid, "");

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByUUID_G_PrevAndNext(
				session, fragmentEntryVersion, uuid, groupId, orderByComparator,
				true);

			array[1] = fragmentEntryVersion;

			array[2] = getByUUID_G_PrevAndNext(
				session, fragmentEntryVersion, uuid, groupId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByUUID_G_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion, String uuid,
		long groupId, OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUUID_G(String uuid, long groupId) {
		for (FragmentEntryVersion fragmentEntryVersion :
				findByUUID_G(
					uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"fragmentEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(fragmentEntryVersion.uuid IS NULL OR fragmentEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"fragmentEntryVersion.groupId = ?";

	private FinderPath _finderPathFetchByUUID_G_Version;
	private FinderPath _finderPathCountByUUID_G_Version;

	/**
	 * Returns the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUUID_G_Version(
			uuid, groupId, version);

		if (fragmentEntryVersion == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryVersionException(sb.toString());
		}

		return fragmentEntryVersion;
	}

	/**
	 * Returns the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version) {

		return fetchByUUID_G_Version(uuid, groupId, version, true);
	}

	/**
	 * Returns the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId, version};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G_Version, finderArgs, this);
		}

		if (result instanceof FragmentEntryVersion) {
			FragmentEntryVersion fragmentEntryVersion =
				(FragmentEntryVersion)result;

			if (!Objects.equals(uuid, fragmentEntryVersion.getUuid()) ||
				(groupId != fragmentEntryVersion.getGroupId()) ||
				(version != fragmentEntryVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_UUID_G_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				queryPos.add(version);

				List<FragmentEntryVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G_Version, finderArgs, list);
					}
				}
				else {
					FragmentEntryVersion fragmentEntryVersion = list.get(0);

					result = fragmentEntryVersion;

					cacheResult(fragmentEntryVersion);
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
			return (FragmentEntryVersion)result;
		}
	}

	/**
	 * Removes the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the fragment entry version that was removed
	 */
	@Override
	public FragmentEntryVersion removeByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByUUID_G_Version(
			uuid, groupId, version);

		return remove(fragmentEntryVersion);
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and groupId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUUID_G_Version(String uuid, long groupId, int version) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G_Version;

			finderArgs = new Object[] {uuid, groupId, version};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_UUID_G_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_G_VERSION_UUID_2 =
		"fragmentEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_VERSION_UUID_3 =
		"(fragmentEntryVersion.uuid IS NULL OR fragmentEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_VERSION_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if (!uuid.equals(fragmentEntryVersion.getUuid()) ||
						(companyId != fragmentEntryVersion.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByUuid_C_PrevAndNext(
			long fragmentEntryVersionId, String uuid, long companyId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		uuid = Objects.toString(uuid, "");

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, fragmentEntryVersion, uuid, companyId,
				orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByUuid_C_PrevAndNext(
				session, fragmentEntryVersion, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByUuid_C_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion, String uuid,
		long companyId,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FragmentEntryVersion fragmentEntryVersion :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"fragmentEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(fragmentEntryVersion.uuid IS NULL OR fragmentEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"fragmentEntryVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Version;
	private FinderPath _finderPathCountByUuid_C_Version;

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version) {

		return findByUuid_C_Version(
			uuid, companyId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end) {

		return findByUuid_C_Version(uuid, companyId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C_Version;
				finderArgs = new Object[] {uuid, companyId, version};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C_Version;
			finderArgs = new Object[] {
				uuid, companyId, version, start, end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if (!uuid.equals(fragmentEntryVersion.getUuid()) ||
						(companyId != fragmentEntryVersion.getCompanyId()) ||
						(version != fragmentEntryVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2);

			sb.append(_FINDER_COLUMN_UUID_C_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				queryPos.add(version);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_C_Version_First(
			uuid, companyId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByUuid_C_Version(
			uuid, companyId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_C_Version_Last(
			String uuid, long companyId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_C_Version_Last(
			uuid, companyId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_C_Version_Last(
		String uuid, long companyId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByUuid_C_Version(uuid, companyId, version);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByUuid_C_Version(
			uuid, companyId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByUuid_C_Version_PrevAndNext(
			long fragmentEntryVersionId, String uuid, long companyId,
			int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		uuid = Objects.toString(uuid, "");

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByUuid_C_Version_PrevAndNext(
				session, fragmentEntryVersion, uuid, companyId, version,
				orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByUuid_C_Version_PrevAndNext(
				session, fragmentEntryVersion, uuid, companyId, version,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByUuid_C_Version_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion, String uuid,
		long companyId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2);

		sb.append(_FINDER_COLUMN_UUID_C_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	@Override
	public void removeByUuid_C_Version(
		String uuid, long companyId, int version) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByUuid_C_Version(
					uuid, companyId, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUuid_C_Version(String uuid, long companyId, int version) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C_Version;

			finderArgs = new Object[] {uuid, companyId, version};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2);

			sb.append(_FINDER_COLUMN_UUID_C_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_C_VERSION_UUID_2 =
		"fragmentEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_UUID_3 =
		"(fragmentEntryVersion.uuid IS NULL OR fragmentEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2 =
		"fragmentEntryVersion.companyId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the fragment entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if (groupId != fragmentEntryVersion.getGroupId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByGroupId_First(
			long groupId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByGroupId_First(
			groupId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByGroupId_First(
		long groupId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByGroupId_Last(
			long groupId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByGroupId_Last(
		long groupId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByGroupId_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, fragmentEntryVersion, groupId, orderByComparator,
				true);

			array[1] = fragmentEntryVersion;

			array[2] = getByGroupId_PrevAndNext(
				session, fragmentEntryVersion, groupId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByGroupId_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FragmentEntryVersion fragmentEntryVersion :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"fragmentEntryVersion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId_Version;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Version;
	private FinderPath _finderPathCountByGroupId_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version) {

		return findByGroupId_Version(
			groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end) {

		return findByGroupId_Version(groupId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByGroupId_Version(
			groupId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId_Version;
				finderArgs = new Object[] {groupId, version};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId_Version;
			finderArgs = new Object[] {
				groupId, version, start, end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(version != fragmentEntryVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(version);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByGroupId_Version_First(
			long groupId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByGroupId_Version_First(groupId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByGroupId_Version_First(
		long groupId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByGroupId_Version(
			groupId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByGroupId_Version_Last(
			long groupId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByGroupId_Version_Last(
			groupId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByGroupId_Version_Last(
		long groupId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByGroupId_Version(groupId, version);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByGroupId_Version(
			groupId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByGroupId_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByGroupId_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, version,
				orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByGroupId_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, version,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByGroupId_Version_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

		sb.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	@Override
	public void removeByGroupId_Version(long groupId, int version) {
		for (FragmentEntryVersion fragmentEntryVersion :
				findByGroupId_Version(
					groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByGroupId_Version(long groupId, int version) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId_Version;

			finderArgs = new Object[] {groupId, version};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_GROUPID_VERSION_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_GROUPID_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByFragmentCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByFragmentCollectionId;
	private FinderPath _finderPathCountByFragmentCollectionId;

	/**
	 * Returns all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId) {

		return findByFragmentCollectionId(
			fragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end) {

		return findByFragmentCollectionId(
			fragmentCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByFragmentCollectionId(
			fragmentCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByFragmentCollectionId;
				finderArgs = new Object[] {fragmentCollectionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByFragmentCollectionId;
			finderArgs = new Object[] {
				fragmentCollectionId, start, end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if (fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_FRAGMENTCOLLECTIONID_FRAGMENTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentCollectionId);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentCollectionId_First(
			long fragmentCollectionId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByFragmentCollectionId_First(
				fragmentCollectionId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentCollectionId_First(
		long fragmentCollectionId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByFragmentCollectionId(
			fragmentCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentCollectionId_Last(
			long fragmentCollectionId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByFragmentCollectionId_Last(
				fragmentCollectionId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentCollectionId_Last(
		long fragmentCollectionId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByFragmentCollectionId(fragmentCollectionId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByFragmentCollectionId(
			fragmentCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByFragmentCollectionId_PrevAndNext(
			long fragmentEntryVersionId, long fragmentCollectionId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByFragmentCollectionId_PrevAndNext(
				session, fragmentEntryVersion, fragmentCollectionId,
				orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByFragmentCollectionId_PrevAndNext(
				session, fragmentEntryVersion, fragmentCollectionId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByFragmentCollectionId_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long fragmentCollectionId,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_FRAGMENTCOLLECTIONID_FRAGMENTCOLLECTIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(fragmentCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where fragmentCollectionId = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 */
	@Override
	public void removeByFragmentCollectionId(long fragmentCollectionId) {
		for (FragmentEntryVersion fragmentEntryVersion :
				findByFragmentCollectionId(
					fragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByFragmentCollectionId(long fragmentCollectionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFragmentCollectionId;

			finderArgs = new Object[] {fragmentCollectionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_FRAGMENTCOLLECTIONID_FRAGMENTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentCollectionId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String
		_FINDER_COLUMN_FRAGMENTCOLLECTIONID_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ?";

	private FinderPath
		_finderPathWithPaginationFindByFragmentCollectionId_Version;
	private FinderPath
		_finderPathWithoutPaginationFindByFragmentCollectionId_Version;
	private FinderPath _finderPathCountByFragmentCollectionId_Version;

	/**
	 * Returns all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId_Version(
		long fragmentCollectionId, int version) {

		return findByFragmentCollectionId_Version(
			fragmentCollectionId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId_Version(
		long fragmentCollectionId, int version, int start, int end) {

		return findByFragmentCollectionId_Version(
			fragmentCollectionId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId_Version(
		long fragmentCollectionId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByFragmentCollectionId_Version(
			fragmentCollectionId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId_Version(
		long fragmentCollectionId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByFragmentCollectionId_Version;
				finderArgs = new Object[] {fragmentCollectionId, version};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath =
				_finderPathWithPaginationFindByFragmentCollectionId_Version;
			finderArgs = new Object[] {
				fragmentCollectionId, version, start, end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						(version != fragmentEntryVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_FRAGMENTCOLLECTIONID_VERSION_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_FRAGMENTCOLLECTIONID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentCollectionId);

				queryPos.add(version);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentCollectionId_Version_First(
			long fragmentCollectionId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByFragmentCollectionId_Version_First(
				fragmentCollectionId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentCollectionId_Version_First(
		long fragmentCollectionId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByFragmentCollectionId_Version(
			fragmentCollectionId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentCollectionId_Version_Last(
			long fragmentCollectionId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByFragmentCollectionId_Version_Last(
				fragmentCollectionId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentCollectionId_Version_Last(
		long fragmentCollectionId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByFragmentCollectionId_Version(
			fragmentCollectionId, version);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByFragmentCollectionId_Version(
			fragmentCollectionId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[]
			findByFragmentCollectionId_Version_PrevAndNext(
				long fragmentEntryVersionId, long fragmentCollectionId,
				int version,
				OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByFragmentCollectionId_Version_PrevAndNext(
				session, fragmentEntryVersion, fragmentCollectionId, version,
				orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByFragmentCollectionId_Version_PrevAndNext(
				session, fragmentEntryVersion, fragmentCollectionId, version,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion
		getByFragmentCollectionId_Version_PrevAndNext(
			Session session, FragmentEntryVersion fragmentEntryVersion,
			long fragmentCollectionId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator,
			boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(
			_FINDER_COLUMN_FRAGMENTCOLLECTIONID_VERSION_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_FRAGMENTCOLLECTIONID_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(fragmentCollectionId);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 */
	@Override
	public void removeByFragmentCollectionId_Version(
		long fragmentCollectionId, int version) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByFragmentCollectionId_Version(
					fragmentCollectionId, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByFragmentCollectionId_Version(
		long fragmentCollectionId, int version) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFragmentCollectionId_Version;

			finderArgs = new Object[] {fragmentCollectionId, version};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_FRAGMENTCOLLECTIONID_VERSION_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_FRAGMENTCOLLECTIONID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentCollectionId);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String
		_FINDER_COLUMN_FRAGMENTCOLLECTIONID_VERSION_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String
		_FINDER_COLUMN_FRAGMENTCOLLECTIONID_VERSION_VERSION_2 =
			"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI;
	private FinderPath _finderPathCountByG_FCI;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId) {

		return findByG_FCI(
			groupId, fragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end) {

		return findByG_FCI(groupId, fragmentCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI;
				finderArgs = new Object[] {groupId, fragmentCollectionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, start, end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_FRAGMENTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_First(
			long groupId, long fragmentCollectionId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_First(
			groupId, fragmentCollectionId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_First(
		long groupId, long fragmentCollectionId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI(
			groupId, fragmentCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_Last(
			long groupId, long fragmentCollectionId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_Last(
			groupId, fragmentCollectionId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_Last(
		long groupId, long fragmentCollectionId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI(groupId, fragmentCollectionId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI(
			groupId, fragmentCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_FRAGMENTCOLLECTIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 */
	@Override
	public void removeByG_FCI(long groupId, long fragmentCollectionId) {
		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI(
					groupId, fragmentCollectionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI(long groupId, long fragmentCollectionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI;

			finderArgs = new Object[] {groupId, fragmentCollectionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_FRAGMENTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_FRAGMENTCOLLECTIONID_2 =
		"fragmentEntryVersion.fragmentCollectionId = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_Version;
	private FinderPath _finderPathCountByG_FCI_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version) {

		return findByG_FCI_Version(
			groupId, fragmentCollectionId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version, int start,
		int end) {

		return findByG_FCI_Version(
			groupId, fragmentCollectionId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version, int start,
		int end, OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_Version(
			groupId, fragmentCollectionId, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version, int start,
		int end, OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_Version;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, version
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_Version;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, version, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						(version != fragmentEntryVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_VERSION_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(version);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_Version_First(
			long groupId, long fragmentCollectionId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_Version_First(
			groupId, fragmentCollectionId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_Version_First(
		long groupId, long fragmentCollectionId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_Version(
			groupId, fragmentCollectionId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_Version_Last(
			long groupId, long fragmentCollectionId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_Version_Last(
			groupId, fragmentCollectionId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_Version_Last(
		long groupId, long fragmentCollectionId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_Version(
			groupId, fragmentCollectionId, version);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_Version(
			groupId, fragmentCollectionId, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				version, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				version, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_Version_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_VERSION_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_VERSION_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_Version(
					groupId, fragmentCollectionId, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_Version;

			finderArgs = new Object[] {groupId, fragmentCollectionId, version};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_VERSION_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_VERSION_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_VERSION_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_FEK;
	private FinderPath _finderPathWithoutPaginationFindByG_FEK;
	private FinderPath _finderPathCountByG_FEK;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey) {

		return findByG_FEK(
			groupId, fragmentEntryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end) {

		return findByG_FEK(groupId, fragmentEntryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FEK(
			groupId, fragmentEntryKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FEK;
				finderArgs = new Object[] {groupId, fragmentEntryKey};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FEK;
			finderArgs = new Object[] {
				groupId, fragmentEntryKey, start, end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						!fragmentEntryKey.equals(
							fragmentEntryVersion.getFragmentEntryKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FEK_GROUPID_2);

			boolean bindFragmentEntryKey = false;

			if (fragmentEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_3);
			}
			else {
				bindFragmentEntryKey = true;

				sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFragmentEntryKey) {
					queryPos.add(fragmentEntryKey);
				}

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FEK_First(
			long groupId, String fragmentEntryKey,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FEK_First(
			groupId, fragmentEntryKey, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryKey=");
		sb.append(fragmentEntryKey);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FEK_First(
		long groupId, String fragmentEntryKey,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FEK(
			groupId, fragmentEntryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FEK_Last(
			long groupId, String fragmentEntryKey,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FEK_Last(
			groupId, fragmentEntryKey, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryKey=");
		sb.append(fragmentEntryKey);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FEK_Last(
		long groupId, String fragmentEntryKey,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FEK(groupId, fragmentEntryKey);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FEK(
			groupId, fragmentEntryKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FEK_PrevAndNext(
			long fragmentEntryVersionId, long groupId, String fragmentEntryKey,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FEK_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentEntryKey,
				orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FEK_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentEntryKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FEK_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, String fragmentEntryKey,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FEK_GROUPID_2);

		boolean bindFragmentEntryKey = false;

		if (fragmentEntryKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_3);
		}
		else {
			bindFragmentEntryKey = true;

			sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindFragmentEntryKey) {
			queryPos.add(fragmentEntryKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 */
	@Override
	public void removeByG_FEK(long groupId, String fragmentEntryKey) {
		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FEK(
					groupId, fragmentEntryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FEK(long groupId, String fragmentEntryKey) {
		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FEK;

			finderArgs = new Object[] {groupId, fragmentEntryKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FEK_GROUPID_2);

			boolean bindFragmentEntryKey = false;

			if (fragmentEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_3);
			}
			else {
				bindFragmentEntryKey = true;

				sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFragmentEntryKey) {
					queryPos.add(fragmentEntryKey);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FEK_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_2 =
		"fragmentEntryVersion.fragmentEntryKey = ?";

	private static final String _FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_3 =
		"(fragmentEntryVersion.fragmentEntryKey IS NULL OR fragmentEntryVersion.fragmentEntryKey = '')";

	private FinderPath _finderPathFetchByG_FEK_Version;
	private FinderPath _finderPathCountByG_FEK_Version;

	/**
	 * Returns the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FEK_Version(
			long groupId, String fragmentEntryKey, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FEK_Version(
			groupId, fragmentEntryKey, version);

		if (fragmentEntryVersion == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", fragmentEntryKey=");
			sb.append(fragmentEntryKey);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryVersionException(sb.toString());
		}

		return fragmentEntryVersion;
	}

	/**
	 * Returns the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FEK_Version(
		long groupId, String fragmentEntryKey, int version) {

		return fetchByG_FEK_Version(groupId, fragmentEntryKey, version, true);
	}

	/**
	 * Returns the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FEK_Version(
		long groupId, String fragmentEntryKey, int version,
		boolean useFinderCache) {

		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, fragmentEntryKey, version};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_FEK_Version, finderArgs, this);
		}

		if (result instanceof FragmentEntryVersion) {
			FragmentEntryVersion fragmentEntryVersion =
				(FragmentEntryVersion)result;

			if ((groupId != fragmentEntryVersion.getGroupId()) ||
				!Objects.equals(
					fragmentEntryKey,
					fragmentEntryVersion.getFragmentEntryKey()) ||
				(version != fragmentEntryVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FEK_VERSION_GROUPID_2);

			boolean bindFragmentEntryKey = false;

			if (fragmentEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FEK_VERSION_FRAGMENTENTRYKEY_3);
			}
			else {
				bindFragmentEntryKey = true;

				sb.append(_FINDER_COLUMN_G_FEK_VERSION_FRAGMENTENTRYKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_FEK_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFragmentEntryKey) {
					queryPos.add(fragmentEntryKey);
				}

				queryPos.add(version);

				List<FragmentEntryVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_FEK_Version, finderArgs, list);
					}
				}
				else {
					FragmentEntryVersion fragmentEntryVersion = list.get(0);

					result = fragmentEntryVersion;

					cacheResult(fragmentEntryVersion);
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
			return (FragmentEntryVersion)result;
		}
	}

	/**
	 * Removes the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the fragment entry version that was removed
	 */
	@Override
	public FragmentEntryVersion removeByG_FEK_Version(
			long groupId, String fragmentEntryKey, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByG_FEK_Version(
			groupId, fragmentEntryKey, version);

		return remove(fragmentEntryVersion);
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FEK_Version(
		long groupId, String fragmentEntryKey, int version) {

		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FEK_Version;

			finderArgs = new Object[] {groupId, fragmentEntryKey, version};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FEK_VERSION_GROUPID_2);

			boolean bindFragmentEntryKey = false;

			if (fragmentEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FEK_VERSION_FRAGMENTENTRYKEY_3);
			}
			else {
				bindFragmentEntryKey = true;

				sb.append(_FINDER_COLUMN_G_FEK_VERSION_FRAGMENTENTRYKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_FEK_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFragmentEntryKey) {
					queryPos.add(fragmentEntryKey);
				}

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FEK_VERSION_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FEK_VERSION_FRAGMENTENTRYKEY_2 =
			"fragmentEntryVersion.fragmentEntryKey = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FEK_VERSION_FRAGMENTENTRYKEY_3 =
			"(fragmentEntryVersion.fragmentEntryKey IS NULL OR fragmentEntryVersion.fragmentEntryKey = '') AND ";

	private static final String _FINDER_COLUMN_G_FEK_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_LikeN;
	private FinderPath _finderPathCountByG_FCI_LikeN;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_LikeN;
				finderArgs = new Object[] {groupId, fragmentCollectionId, name};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_LikeN;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, name, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						!name.equals(fragmentEntryVersion.getName())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_First(
			long groupId, long fragmentCollectionId, String name,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_LikeN_First(
			groupId, fragmentCollectionId, name, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_First(
		long groupId, long fragmentCollectionId, String name,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_Last(
			long groupId, long fragmentCollectionId, String name,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_LikeN_Last(
			groupId, fragmentCollectionId, name, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_Last(
		long groupId, long fragmentCollectionId, String name,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_LikeN(groupId, fragmentCollectionId, name);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_LikeN_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, String name,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		name = Objects.toString(name, "");

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_LikeN_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				name, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_LikeN_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				name, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_LikeN_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, String name,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_FRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 */
	@Override
	public void removeByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_LikeN(
					groupId, fragmentCollectionId, name, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_LikeN;

			finderArgs = new Object[] {groupId, fragmentCollectionId, name};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_LIKEN_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_NAME_2 =
		"fragmentEntryVersion.name = ?";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_NAME_3 =
		"(fragmentEntryVersion.name IS NULL OR fragmentEntryVersion.name = '')";

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_LikeN_Version;
	private FinderPath _finderPathCountByG_FCI_LikeN_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version) {

		return findByG_FCI_LikeN_Version(
			groupId, fragmentCollectionId, name, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version,
		int start, int end) {

		return findByG_FCI_LikeN_Version(
			groupId, fragmentCollectionId, name, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_LikeN_Version(
			groupId, fragmentCollectionId, name, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByG_FCI_LikeN_Version;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, name, version
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_LikeN_Version;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, name, version, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						!name.equals(fragmentEntryVersion.getName()) ||
						(version != fragmentEntryVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_G_FCI_LIKEN_VERSION_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(version);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_Version_First(
			long groupId, long fragmentCollectionId, String name, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_LikeN_Version_First(
				groupId, fragmentCollectionId, name, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_Version_First(
		long groupId, long fragmentCollectionId, String name, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_LikeN_Version(
			groupId, fragmentCollectionId, name, version, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_Version_Last(
			long groupId, long fragmentCollectionId, String name, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_LikeN_Version_Last(
				groupId, fragmentCollectionId, name, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_Version_Last(
		long groupId, long fragmentCollectionId, String name, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_LikeN_Version(
			groupId, fragmentCollectionId, name, version);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_LikeN_Version(
			groupId, fragmentCollectionId, name, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_LikeN_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, String name, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		name = Objects.toString(name, "");

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_LikeN_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				name, version, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_LikeN_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				name, version, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_LikeN_Version_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, String name, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_FRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_LikeN_Version(
					groupId, fragmentCollectionId, name, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_LikeN_Version;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, name, version
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_G_FCI_LIKEN_VERSION_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_VERSION_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_LIKEN_VERSION_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_VERSION_NAME_2 =
		"fragmentEntryVersion.name = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_VERSION_NAME_3 =
		"(fragmentEntryVersion.name IS NULL OR fragmentEntryVersion.name = '') AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_T;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T;
	private FinderPath _finderPathCountByG_FCI_T;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_T;
				finderArgs = new Object[] {groupId, fragmentCollectionId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_T;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						(type != fragmentEntryVersion.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_First(
			long groupId, long fragmentCollectionId, int type,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_T_First(
			groupId, fragmentCollectionId, type, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_First(
		long groupId, long fragmentCollectionId, int type,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_T(
			groupId, fragmentCollectionId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_Last(
			long groupId, long fragmentCollectionId, int type,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_T_Last(
			groupId, fragmentCollectionId, type, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_Last(
		long groupId, long fragmentCollectionId, int type,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_T(groupId, fragmentCollectionId, type);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_T(
			groupId, fragmentCollectionId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_T_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int type,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_T_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				type, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_T_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_T_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, int type,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_TYPE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 */
	@Override
	public void removeByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_T(
					groupId, fragmentCollectionId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_T;

			finderArgs = new Object[] {groupId, fragmentCollectionId, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_T_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_FRAGMENTCOLLECTIONID_2 =
		"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_TYPE_2 =
		"fragmentEntryVersion.type = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_Version;
	private FinderPath _finderPathCountByG_FCI_T_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version) {

		return findByG_FCI_T_Version(
			groupId, fragmentCollectionId, type, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version,
		int start, int end) {

		return findByG_FCI_T_Version(
			groupId, fragmentCollectionId, type, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_T_Version(
			groupId, fragmentCollectionId, type, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_T_Version;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, type, version
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_T_Version;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, version, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						(type != fragmentEntryVersion.getType()) ||
						(version != fragmentEntryVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(version);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_Version_First(
			long groupId, long fragmentCollectionId, int type, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_T_Version_First(
				groupId, fragmentCollectionId, type, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_Version_First(
		long groupId, long fragmentCollectionId, int type, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_T_Version(
			groupId, fragmentCollectionId, type, version, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_Version_Last(
			long groupId, long fragmentCollectionId, int type, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_T_Version_Last(
			groupId, fragmentCollectionId, type, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_Version_Last(
		long groupId, long fragmentCollectionId, int type, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_T_Version(
			groupId, fragmentCollectionId, type, version);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_T_Version(
			groupId, fragmentCollectionId, type, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_T_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int type, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_T_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				type, version, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_T_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				type, version, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_T_Version_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, int type, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_TYPE_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(type);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_T_Version(
					groupId, fragmentCollectionId, type, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_T_Version;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, version
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_T_VERSION_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_T_VERSION_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_VERSION_TYPE_2 =
		"fragmentEntryVersion.type = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_S;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_S;
	private FinderPath _finderPathCountByG_FCI_S;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start,
		int end) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_S;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_S;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, status, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						(status != fragmentEntryVersion.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(status);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_S_First(
			long groupId, long fragmentCollectionId, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_S_First(
			groupId, fragmentCollectionId, status, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_S_First(
		long groupId, long fragmentCollectionId, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_S(
			groupId, fragmentCollectionId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_S_Last(
			long groupId, long fragmentCollectionId, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_S_Last(
			groupId, fragmentCollectionId, status, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_S_Last(
		long groupId, long fragmentCollectionId, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_S(groupId, fragmentCollectionId, status);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_S(
			groupId, fragmentCollectionId, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_S_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_S_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				status, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_S_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_S_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_S_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 */
	@Override
	public void removeByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_S(
					groupId, fragmentCollectionId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_S;

			finderArgs = new Object[] {groupId, fragmentCollectionId, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_S_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_S_FRAGMENTCOLLECTIONID_2 =
		"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_S_STATUS_2 =
		"fragmentEntryVersion.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_S_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_S_Version;
	private FinderPath _finderPathCountByG_FCI_S_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version) {

		return findByG_FCI_S_Version(
			groupId, fragmentCollectionId, status, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version,
		int start, int end) {

		return findByG_FCI_S_Version(
			groupId, fragmentCollectionId, status, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_S_Version(
			groupId, fragmentCollectionId, status, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_S_Version;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, status, version
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_S_Version;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, status, version, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						(status != fragmentEntryVersion.getStatus()) ||
						(version != fragmentEntryVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(status);

				queryPos.add(version);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_S_Version_First(
			long groupId, long fragmentCollectionId, int status, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_S_Version_First(
				groupId, fragmentCollectionId, status, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", status=");
		sb.append(status);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_S_Version_First(
		long groupId, long fragmentCollectionId, int status, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_S_Version(
			groupId, fragmentCollectionId, status, version, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_S_Version_Last(
			long groupId, long fragmentCollectionId, int status, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_S_Version_Last(
			groupId, fragmentCollectionId, status, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", status=");
		sb.append(status);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_S_Version_Last(
		long groupId, long fragmentCollectionId, int status, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_S_Version(
			groupId, fragmentCollectionId, status, version);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_S_Version(
			groupId, fragmentCollectionId, status, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_S_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int status, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_S_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				status, version, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_S_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				status, version, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_S_Version_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, int status, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_STATUS_2);

		sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(status);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_S_Version(
					groupId, fragmentCollectionId, status, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_S_Version;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, status, version
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(status);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_S_VERSION_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_S_VERSION_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_S_VERSION_STATUS_2 =
		"fragmentEntryVersion.status = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_S_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_S;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_LikeN_S;
	private FinderPath _finderPathCountByG_FCI_LikeN_S;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_LikeN_S;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, name, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_LikeN_S;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, name, status, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						!name.equals(fragmentEntryVersion.getName()) ||
						(status != fragmentEntryVersion.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(status);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_S_First(
			long groupId, long fragmentCollectionId, String name, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_LikeN_S_First(
			groupId, fragmentCollectionId, name, status, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_S_First(
		long groupId, long fragmentCollectionId, String name, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_S_Last(
			long groupId, long fragmentCollectionId, String name, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_LikeN_S_Last(
			groupId, fragmentCollectionId, name, status, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_S_Last(
		long groupId, long fragmentCollectionId, String name, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_LikeN_S_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, String name, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		name = Objects.toString(name, "");

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_LikeN_S_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				name, status, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_LikeN_S_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				name, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_LikeN_S_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, String name, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_FRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 */
	@Override
	public void removeByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_LikeN_S(
					groupId, fragmentCollectionId, name, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_LikeN_S;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, name, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_LIKEN_S_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_NAME_2 =
		"fragmentEntryVersion.name = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_NAME_3 =
		"(fragmentEntryVersion.name IS NULL OR fragmentEntryVersion.name = '') AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_STATUS_2 =
		"fragmentEntryVersion.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_S_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_LikeN_S_Version;
	private FinderPath _finderPathCountByG_FCI_LikeN_S_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version) {

		return findByG_FCI_LikeN_S_Version(
			groupId, fragmentCollectionId, name, status, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version, int start, int end) {

		return findByG_FCI_LikeN_S_Version(
			groupId, fragmentCollectionId, name, status, version, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_LikeN_S_Version(
			groupId, fragmentCollectionId, name, status, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByG_FCI_LikeN_S_Version;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, name, status, version
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_LikeN_S_Version;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, name, status, version, start,
				end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						!name.equals(fragmentEntryVersion.getName()) ||
						(status != fragmentEntryVersion.getStatus()) ||
						(version != fragmentEntryVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(status);

				queryPos.add(version);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_S_Version_First(
			long groupId, long fragmentCollectionId, String name, int status,
			int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_LikeN_S_Version_First(
				groupId, fragmentCollectionId, name, status, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", status=");
		sb.append(status);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_S_Version_First(
		long groupId, long fragmentCollectionId, String name, int status,
		int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_LikeN_S_Version(
			groupId, fragmentCollectionId, name, status, version, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_S_Version_Last(
			long groupId, long fragmentCollectionId, String name, int status,
			int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_LikeN_S_Version_Last(
				groupId, fragmentCollectionId, name, status, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", status=");
		sb.append(status);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_S_Version_Last(
		long groupId, long fragmentCollectionId, String name, int status,
		int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_LikeN_S_Version(
			groupId, fragmentCollectionId, name, status, version);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_LikeN_S_Version(
			groupId, fragmentCollectionId, name, status, version, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_LikeN_S_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, String name, int status, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		name = Objects.toString(name, "");

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_LikeN_S_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				name, status, version, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_LikeN_S_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				name, status, version, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_LikeN_S_Version_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, String name, int status,
		int version, OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_FRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_STATUS_2);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(status);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_LikeN_S_Version(
					groupId, fragmentCollectionId, name, status, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_LikeN_S_Version;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, name, status, version
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(status);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_NAME_2 =
		"fragmentEntryVersion.name = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_NAME_3 =
		"(fragmentEntryVersion.name IS NULL OR fragmentEntryVersion.name = '') AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_STATUS_2 =
		"fragmentEntryVersion.status = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_S;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_S;
	private FinderPath _finderPathCountByG_FCI_T_S;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_T_S;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, type, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_T_S;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, status, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						(type != fragmentEntryVersion.getType()) ||
						(status != fragmentEntryVersion.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(status);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_S_First(
			long groupId, long fragmentCollectionId, int type, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_T_S_First(
			groupId, fragmentCollectionId, type, status, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_S_First(
		long groupId, long fragmentCollectionId, int type, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_S_Last(
			long groupId, long fragmentCollectionId, int type, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_T_S_Last(
			groupId, fragmentCollectionId, type, status, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_S_Last(
		long groupId, long fragmentCollectionId, int type, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_T_S_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int type, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_T_S_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				type, status, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_T_S_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				type, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_T_S_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, int type, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_TYPE_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_T_S(
					groupId, fragmentCollectionId, type, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_T_S;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_T_S_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_T_S_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_S_TYPE_2 =
		"fragmentEntryVersion.type = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_S_STATUS_2 =
		"fragmentEntryVersion.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_S_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_S_Version;
	private FinderPath _finderPathCountByG_FCI_T_S_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version) {

		return findByG_FCI_T_S_Version(
			groupId, fragmentCollectionId, type, status, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version, int start, int end) {

		return findByG_FCI_T_S_Version(
			groupId, fragmentCollectionId, type, status, version, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_T_S_Version(
			groupId, fragmentCollectionId, type, status, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByG_FCI_T_S_Version;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, type, status, version
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_T_S_Version;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, status, version, start,
				end, orderByComparator
			};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryVersion fragmentEntryVersion : list) {
					if ((groupId != fragmentEntryVersion.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntryVersion.getFragmentCollectionId()) ||
						(type != fragmentEntryVersion.getType()) ||
						(status != fragmentEntryVersion.getStatus()) ||
						(version != fragmentEntryVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(status);

				queryPos.add(version);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_S_Version_First(
			long groupId, long fragmentCollectionId, int type, int status,
			int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_T_S_Version_First(
				groupId, fragmentCollectionId, type, status, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_S_Version_First(
		long groupId, long fragmentCollectionId, int type, int status,
		int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		List<FragmentEntryVersion> list = findByG_FCI_T_S_Version(
			groupId, fragmentCollectionId, type, status, version, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_S_Version_Last(
			long groupId, long fragmentCollectionId, int type, int status,
			int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_T_S_Version_Last(
				groupId, fragmentCollectionId, type, status, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_S_Version_Last(
		long groupId, long fragmentCollectionId, int type, int status,
		int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		int count = countByG_FCI_T_S_Version(
			groupId, fragmentCollectionId, type, status, version);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryVersion> list = findByG_FCI_T_S_Version(
			groupId, fragmentCollectionId, type, status, version, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion[] findByG_FCI_T_S_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int type, int status, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByPrimaryKey(
			fragmentEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion[] array = new FragmentEntryVersionImpl[3];

			array[0] = getByG_FCI_T_S_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				type, status, version, orderByComparator, true);

			array[1] = fragmentEntryVersion;

			array[2] = getByG_FCI_T_S_Version_PrevAndNext(
				session, fragmentEntryVersion, groupId, fragmentCollectionId,
				type, status, version, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryVersion getByG_FCI_T_S_Version_PrevAndNext(
		Session session, FragmentEntryVersion fragmentEntryVersion,
		long groupId, long fragmentCollectionId, int type, int status,
		int version, OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_TYPE_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_STATUS_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(type);

		queryPos.add(status);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version) {

		for (FragmentEntryVersion fragmentEntryVersion :
				findByG_FCI_T_S_Version(
					groupId, fragmentCollectionId, type, status, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_T_S_Version;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, status, version
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(status);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_FCI_T_S_VERSION_GROUPID_2 =
		"fragmentEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_T_S_VERSION_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntryVersion.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_S_VERSION_TYPE_2 =
		"fragmentEntryVersion.type = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_S_VERSION_STATUS_2 =
		"fragmentEntryVersion.status = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_S_VERSION_VERSION_2 =
		"fragmentEntryVersion.version = ?";

	public FragmentEntryVersionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FragmentEntryVersion.class);

		setModelImplClass(FragmentEntryVersionImpl.class);
		setModelPKClass(long.class);

		setTable(FragmentEntryVersionTable.INSTANCE);
	}

	/**
	 * Caches the fragment entry version in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryVersion the fragment entry version
	 */
	@Override
	public void cacheResult(FragmentEntryVersion fragmentEntryVersion) {
		if (fragmentEntryVersion.getCtCollectionId() != 0) {
			fragmentEntryVersion.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			FragmentEntryVersionImpl.class,
			fragmentEntryVersion.getPrimaryKey(), fragmentEntryVersion);

		finderCache.putResult(
			_finderPathFetchByFragmentEntryId_Version,
			new Object[] {
				fragmentEntryVersion.getFragmentEntryId(),
				fragmentEntryVersion.getVersion()
			},
			fragmentEntryVersion);

		finderCache.putResult(
			_finderPathFetchByUUID_G_Version,
			new Object[] {
				fragmentEntryVersion.getUuid(),
				fragmentEntryVersion.getGroupId(),
				fragmentEntryVersion.getVersion()
			},
			fragmentEntryVersion);

		finderCache.putResult(
			_finderPathFetchByG_FEK_Version,
			new Object[] {
				fragmentEntryVersion.getGroupId(),
				fragmentEntryVersion.getFragmentEntryKey(),
				fragmentEntryVersion.getVersion()
			},
			fragmentEntryVersion);

		fragmentEntryVersion.resetOriginalValues();
	}

	/**
	 * Caches the fragment entry versions in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryVersions the fragment entry versions
	 */
	@Override
	public void cacheResult(List<FragmentEntryVersion> fragmentEntryVersions) {
		for (FragmentEntryVersion fragmentEntryVersion :
				fragmentEntryVersions) {

			if (fragmentEntryVersion.getCtCollectionId() != 0) {
				fragmentEntryVersion.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					FragmentEntryVersionImpl.class,
					fragmentEntryVersion.getPrimaryKey()) == null) {

				cacheResult(fragmentEntryVersion);
			}
			else {
				fragmentEntryVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all fragment entry versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FragmentEntryVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the fragment entry version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FragmentEntryVersion fragmentEntryVersion) {
		entityCache.removeResult(
			FragmentEntryVersionImpl.class,
			fragmentEntryVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(FragmentEntryVersionModelImpl)fragmentEntryVersion, true);
	}

	@Override
	public void clearCache(List<FragmentEntryVersion> fragmentEntryVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FragmentEntryVersion fragmentEntryVersion :
				fragmentEntryVersions) {

			entityCache.removeResult(
				FragmentEntryVersionImpl.class,
				fragmentEntryVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(FragmentEntryVersionModelImpl)fragmentEntryVersion, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				FragmentEntryVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FragmentEntryVersionModelImpl fragmentEntryVersionModelImpl) {

		Object[] args = new Object[] {
			fragmentEntryVersionModelImpl.getFragmentEntryId(),
			fragmentEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByFragmentEntryId_Version, args, Long.valueOf(1),
			false);
		finderCache.putResult(
			_finderPathFetchByFragmentEntryId_Version, args,
			fragmentEntryVersionModelImpl, false);

		args = new Object[] {
			fragmentEntryVersionModelImpl.getUuid(),
			fragmentEntryVersionModelImpl.getGroupId(),
			fragmentEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G_Version, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G_Version, args,
			fragmentEntryVersionModelImpl, false);

		args = new Object[] {
			fragmentEntryVersionModelImpl.getGroupId(),
			fragmentEntryVersionModelImpl.getFragmentEntryKey(),
			fragmentEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByG_FEK_Version, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_FEK_Version, args,
			fragmentEntryVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FragmentEntryVersionModelImpl fragmentEntryVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				fragmentEntryVersionModelImpl.getFragmentEntryId(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(
				_finderPathCountByFragmentEntryId_Version, args);
			finderCache.removeResult(
				_finderPathFetchByFragmentEntryId_Version, args);
		}

		if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByFragmentEntryId_Version.getColumnBitmask()) !=
				 0) {

			Object[] args = new Object[] {
				fragmentEntryVersionModelImpl.getOriginalFragmentEntryId(),
				fragmentEntryVersionModelImpl.getOriginalVersion()
			};

			finderCache.removeResult(
				_finderPathCountByFragmentEntryId_Version, args);
			finderCache.removeResult(
				_finderPathFetchByFragmentEntryId_Version, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				fragmentEntryVersionModelImpl.getUuid(),
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByUUID_G_Version, args);
			finderCache.removeResult(_finderPathFetchByUUID_G_Version, args);
		}

		if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G_Version.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				fragmentEntryVersionModelImpl.getOriginalUuid(),
				fragmentEntryVersionModelImpl.getOriginalGroupId(),
				fragmentEntryVersionModelImpl.getOriginalVersion()
			};

			finderCache.removeResult(_finderPathCountByUUID_G_Version, args);
			finderCache.removeResult(_finderPathFetchByUUID_G_Version, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentEntryKey(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByG_FEK_Version, args);
			finderCache.removeResult(_finderPathFetchByG_FEK_Version, args);
		}

		if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_FEK_Version.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				fragmentEntryVersionModelImpl.getOriginalGroupId(),
				fragmentEntryVersionModelImpl.getOriginalFragmentEntryKey(),
				fragmentEntryVersionModelImpl.getOriginalVersion()
			};

			finderCache.removeResult(_finderPathCountByG_FEK_Version, args);
			finderCache.removeResult(_finderPathFetchByG_FEK_Version, args);
		}
	}

	/**
	 * Creates a new fragment entry version with the primary key. Does not add the fragment entry version to the database.
	 *
	 * @param fragmentEntryVersionId the primary key for the new fragment entry version
	 * @return the new fragment entry version
	 */
	@Override
	public FragmentEntryVersion create(long fragmentEntryVersionId) {
		FragmentEntryVersion fragmentEntryVersion =
			new FragmentEntryVersionImpl();

		fragmentEntryVersion.setNew(true);
		fragmentEntryVersion.setPrimaryKey(fragmentEntryVersionId);

		fragmentEntryVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return fragmentEntryVersion;
	}

	/**
	 * Removes the fragment entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryVersionId the primary key of the fragment entry version
	 * @return the fragment entry version that was removed
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion remove(long fragmentEntryVersionId)
		throws NoSuchEntryVersionException {

		return remove((Serializable)fragmentEntryVersionId);
	}

	/**
	 * Removes the fragment entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fragment entry version
	 * @return the fragment entry version that was removed
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion remove(Serializable primaryKey)
		throws NoSuchEntryVersionException {

		Session session = null;

		try {
			session = openSession();

			FragmentEntryVersion fragmentEntryVersion =
				(FragmentEntryVersion)session.get(
					FragmentEntryVersionImpl.class, primaryKey);

			if (fragmentEntryVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(fragmentEntryVersion);
		}
		catch (NoSuchEntryVersionException noSuchEntityException) {
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
	protected FragmentEntryVersion removeImpl(
		FragmentEntryVersion fragmentEntryVersion) {

		if (!ctPersistenceHelper.isRemove(fragmentEntryVersion)) {
			return fragmentEntryVersion;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fragmentEntryVersion)) {
				fragmentEntryVersion = (FragmentEntryVersion)session.get(
					FragmentEntryVersionImpl.class,
					fragmentEntryVersion.getPrimaryKeyObj());
			}

			if (fragmentEntryVersion != null) {
				session.delete(fragmentEntryVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntryVersion != null) {
			clearCache(fragmentEntryVersion);
		}

		return fragmentEntryVersion;
	}

	@Override
	public FragmentEntryVersion updateImpl(
		FragmentEntryVersion fragmentEntryVersion) {

		boolean isNew = fragmentEntryVersion.isNew();

		if (!(fragmentEntryVersion instanceof FragmentEntryVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fragmentEntryVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fragmentEntryVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fragmentEntryVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FragmentEntryVersion implementation " +
					fragmentEntryVersion.getClass());
		}

		FragmentEntryVersionModelImpl fragmentEntryVersionModelImpl =
			(FragmentEntryVersionModelImpl)fragmentEntryVersion;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (fragmentEntryVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				fragmentEntryVersion.setCreateDate(now);
			}
			else {
				fragmentEntryVersion.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!fragmentEntryVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fragmentEntryVersion.setModifiedDate(now);
			}
			else {
				fragmentEntryVersion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(fragmentEntryVersion)) {
				if (!isNew) {
					session.evict(
						FragmentEntryVersionImpl.class,
						fragmentEntryVersion.getPrimaryKeyObj());
				}

				session.save(fragmentEntryVersion);

				fragmentEntryVersion.setNew(false);
			}
			else {
				throw new IllegalArgumentException(
					"FragmentEntryVersion is read only, create a new version instead");
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntryVersion.getCtCollectionId() != 0) {
			fragmentEntryVersion.resetOriginalValues();

			return fragmentEntryVersion;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				fragmentEntryVersionModelImpl.getFragmentEntryId()
			};

			finderCache.removeResult(_finderPathCountByFragmentEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFragmentEntryId, args);

			args = new Object[] {fragmentEntryVersionModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getUuid(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByUuid_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_Version, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getUuid(),
				fragmentEntryVersionModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUUID_G, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getUuid(),
				fragmentEntryVersionModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getUuid(),
				fragmentEntryVersionModelImpl.getCompanyId(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByUuid_C_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C_Version, args);

			args = new Object[] {fragmentEntryVersionModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByGroupId_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId_Version, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getFragmentCollectionId()
			};

			finderCache.removeResult(
				_finderPathCountByFragmentCollectionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFragmentCollectionId, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(
				_finderPathCountByFragmentCollectionId_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFragmentCollectionId_Version,
				args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId()
			};

			finderCache.removeResult(_finderPathCountByG_FCI, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_Version, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentEntryKey()
			};

			finderCache.removeResult(_finderPathCountByG_FEK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FEK, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_LikeN, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_LikeN, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getName(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(
				_finderPathCountByG_FCI_LikeN_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_LikeN_Version, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_T, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getType(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_T_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_T_Version, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_S, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getStatus(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_S_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_S_Version, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getName(),
				fragmentEntryVersionModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_LikeN_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_LikeN_S, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getName(),
				fragmentEntryVersionModelImpl.getStatus(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(
				_finderPathCountByG_FCI_LikeN_S_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_LikeN_S_Version, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getType(),
				fragmentEntryVersionModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_T_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_T_S, args);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentCollectionId(),
				fragmentEntryVersionModelImpl.getType(),
				fragmentEntryVersionModelImpl.getStatus(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_T_S_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_T_S_Version, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFragmentEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalFragmentEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentEntryId, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getFragmentEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentEntryId, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {fragmentEntryVersionModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalUuid(),
					fragmentEntryVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(_finderPathCountByUuid_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_Version, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getUuid(),
					fragmentEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(_finderPathCountByUuid_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_Version, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUUID_G.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalUuid(),
					fragmentEntryVersionModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByUUID_G, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUUID_G, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getUuid(),
					fragmentEntryVersionModelImpl.getGroupId()
				};

				finderCache.removeResult(_finderPathCountByUUID_G, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUUID_G, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalUuid(),
					fragmentEntryVersionModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getUuid(),
					fragmentEntryVersionModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalUuid(),
					fragmentEntryVersionModelImpl.getOriginalCompanyId(),
					fragmentEntryVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(
					_finderPathCountByUuid_C_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C_Version, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getUuid(),
					fragmentEntryVersionModelImpl.getCompanyId(),
					fragmentEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByUuid_C_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C_Version, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(
					_finderPathCountByGroupId_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Version, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByGroupId_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Version, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFragmentCollectionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentCollectionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentCollectionId,
					args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getFragmentCollectionId()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentCollectionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentCollectionId,
					args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFragmentCollectionId_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentCollectionId_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentCollectionId_Version,
					args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentCollectionId_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentCollectionId_Version,
					args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId()
				};

				finderCache.removeResult(_finderPathCountByG_FCI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId()
				};

				finderCache.removeResult(_finderPathCountByG_FCI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_Version, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_Version, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FEK.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.getOriginalFragmentEntryKey()
				};

				finderCache.removeResult(_finderPathCountByG_FEK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FEK, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentEntryKey()
				};

				finderCache.removeResult(_finderPathCountByG_FEK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FEK, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_LikeN.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalName()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_LikeN, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_LikeN, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getName()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_LikeN, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_LikeN, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_LikeN_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalName(),
					fragmentEntryVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_LikeN_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_LikeN_Version,
					args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getName(),
					fragmentEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_LikeN_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_LikeN_Version,
					args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_T_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalType(),
					fragmentEntryVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_T_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_Version, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getType(),
					fragmentEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_T_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_Version, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_S, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_S, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_S_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalStatus(),
					fragmentEntryVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_S_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_S_Version, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getStatus(),
					fragmentEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_S_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_S_Version, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_LikeN_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalName(),
					fragmentEntryVersionModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_LikeN_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_LikeN_S, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getName(),
					fragmentEntryVersionModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_LikeN_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_LikeN_S, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_LikeN_S_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalName(),
					fragmentEntryVersionModelImpl.getOriginalStatus(),
					fragmentEntryVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_LikeN_S_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_LikeN_S_Version,
					args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getName(),
					fragmentEntryVersionModelImpl.getStatus(),
					fragmentEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_LikeN_S_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_LikeN_S_Version,
					args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_T_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalType(),
					fragmentEntryVersionModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_T_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_S, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getType(),
					fragmentEntryVersionModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_T_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_S, args);
			}

			if ((fragmentEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_T_S_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryVersionModelImpl.getOriginalGroupId(),
					fragmentEntryVersionModelImpl.
						getOriginalFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getOriginalType(),
					fragmentEntryVersionModelImpl.getOriginalStatus(),
					fragmentEntryVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_T_S_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_S_Version, args);

				args = new Object[] {
					fragmentEntryVersionModelImpl.getGroupId(),
					fragmentEntryVersionModelImpl.getFragmentCollectionId(),
					fragmentEntryVersionModelImpl.getType(),
					fragmentEntryVersionModelImpl.getStatus(),
					fragmentEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_T_S_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_S_Version, args);
			}
		}

		entityCache.putResult(
			FragmentEntryVersionImpl.class,
			fragmentEntryVersion.getPrimaryKey(), fragmentEntryVersion, false);

		clearUniqueFindersCache(fragmentEntryVersionModelImpl, false);
		cacheUniqueFindersCache(fragmentEntryVersionModelImpl);

		fragmentEntryVersion.resetOriginalValues();

		return fragmentEntryVersion;
	}

	/**
	 * Returns the fragment entry version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment entry version
	 * @return the fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByPrimaryKey(
			primaryKey);

		if (fragmentEntryVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return fragmentEntryVersion;
	}

	/**
	 * Returns the fragment entry version with the primary key or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param fragmentEntryVersionId the primary key of the fragment entry version
	 * @return the fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion findByPrimaryKey(long fragmentEntryVersionId)
		throws NoSuchEntryVersionException {

		return findByPrimaryKey((Serializable)fragmentEntryVersionId);
	}

	/**
	 * Returns the fragment entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment entry version
	 * @return the fragment entry version, or <code>null</code> if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(FragmentEntryVersion.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		FragmentEntryVersion fragmentEntryVersion = null;

		Session session = null;

		try {
			session = openSession();

			fragmentEntryVersion = (FragmentEntryVersion)session.get(
				FragmentEntryVersionImpl.class, primaryKey);

			if (fragmentEntryVersion != null) {
				cacheResult(fragmentEntryVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return fragmentEntryVersion;
	}

	/**
	 * Returns the fragment entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryVersionId the primary key of the fragment entry version
	 * @return the fragment entry version, or <code>null</code> if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByPrimaryKey(long fragmentEntryVersionId) {
		return fetchByPrimaryKey((Serializable)fragmentEntryVersionId);
	}

	@Override
	public Map<Serializable, FragmentEntryVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(FragmentEntryVersion.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FragmentEntryVersion> map =
			new HashMap<Serializable, FragmentEntryVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FragmentEntryVersion fragmentEntryVersion = fetchByPrimaryKey(
				primaryKey);

			if (fragmentEntryVersion != null) {
				map.put(primaryKey, fragmentEntryVersion);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

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

			for (FragmentEntryVersion fragmentEntryVersion :
					(List<FragmentEntryVersion>)query.list()) {

				map.put(
					fragmentEntryVersion.getPrimaryKeyObj(),
					fragmentEntryVersion);

				cacheResult(fragmentEntryVersion);
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
	 * Returns all the fragment entry versions.
	 *
	 * @return the fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<FragmentEntryVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FRAGMENTENTRYVERSION;

				sql = sql.concat(FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FragmentEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Removes all the fragment entry versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FragmentEntryVersion fragmentEntryVersion : findAll()) {
			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions.
	 *
	 * @return the number of fragment entry versions
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryVersion.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_FRAGMENTENTRYVERSION);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "fragmentEntryVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FRAGMENTENTRYVERSION;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return FragmentEntryVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "FragmentEntryVersion";
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
		ctStrictColumnNames.add("version");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("fragmentEntryId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("fragmentCollectionId");
		ctStrictColumnNames.add("fragmentEntryKey");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("css");
		ctStrictColumnNames.add("html");
		ctStrictColumnNames.add("js");
		ctStrictColumnNames.add("cacheable");
		ctStrictColumnNames.add("configuration");
		ctStrictColumnNames.add("previewFileEntryId");
		ctStrictColumnNames.add("readOnly");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("lastPublishDate");
		ctStrictColumnNames.add("status");
		ctStrictColumnNames.add("statusByUserId");
		ctStrictColumnNames.add("statusByUserName");
		ctStrictColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("fragmentEntryVersionId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"fragmentEntryId", "version"});

		_uniqueIndexColumnNames.add(
			new String[] {"uuid_", "groupId", "version"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "fragmentEntryKey", "version"});
	}

	/**
	 * Initializes the fragment entry version persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByFragmentEntryId = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFragmentEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFragmentEntryId = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFragmentEntryId",
			new String[] {Long.class.getName()},
			FragmentEntryVersionModelImpl.FRAGMENTENTRYID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByFragmentEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentEntryId", new String[] {Long.class.getName()});

		_finderPathFetchByFragmentEntryId_Version = new FinderPath(
			FragmentEntryVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByFragmentEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			FragmentEntryVersionModelImpl.FRAGMENTENTRYID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByFragmentEntryId_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			FragmentEntryVersionModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_Version",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()},
			FragmentEntryVersionModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUuid_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByUUID_G = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUUID_G",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUUID_G = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			FragmentEntryVersionModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByUUID_G_Version = new FinderPath(
			FragmentEntryVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUUID_G_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			FragmentEntryVersionModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUuid_C_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByGroupId_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByGroupId_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByFragmentCollectionId = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByFragmentCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFragmentCollectionId = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByFragmentCollectionId", new String[] {Long.class.getName()},
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByFragmentCollectionId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentCollectionId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByFragmentCollectionId_Version =
			new FinderPath(
				FragmentEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByFragmentCollectionId_Version",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByFragmentCollectionId_Version =
			new FinderPath(
				FragmentEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByFragmentCollectionId_Version",
				new String[] {Long.class.getName(), Integer.class.getName()},
				FragmentEntryVersionModelImpl.
					FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
				FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByFragmentCollectionId_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentCollectionId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByG_FCI = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI",
			new String[] {Long.class.getName(), Long.class.getName()},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_FCI_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FEK = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FEK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FEK = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FEK",
			new String[] {Long.class.getName(), String.class.getName()},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTENTRYKEY_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FEK = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FEK",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByG_FEK_Version = new FinderPath(
			FragmentEntryVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_FEK_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTENTRYKEY_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FEK_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FEK_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_LikeN = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_LikeN = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.NAME_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_LikeN = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_LikeN_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_LikeN_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_LikeN_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_LikeN_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.NAME_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_LikeN_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_LikeN_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_T = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_T = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.TYPE_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_T_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_T_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_T_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_T_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.TYPE_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_T_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_T_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_S = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_S = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.STATUS_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_S_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_S_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.STATUS_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_S_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_LikeN_S = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_LikeN_S = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.NAME_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.STATUS_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_LikeN_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_LikeN_S_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_LikeN_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_LikeN_S_Version =
			new FinderPath(
				FragmentEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByG_FCI_LikeN_S_Version",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName(), Integer.class.getName(),
					Integer.class.getName()
				},
				FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
				FragmentEntryVersionModelImpl.
					FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
				FragmentEntryVersionModelImpl.NAME_COLUMN_BITMASK |
				FragmentEntryVersionModelImpl.STATUS_COLUMN_BITMASK |
				FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_LikeN_S_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_LikeN_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_T_S = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_T_S = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.TYPE_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.STATUS_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_T_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_T_S_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_T_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_T_S_Version = new FinderPath(
			FragmentEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_T_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			FragmentEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.TYPE_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.STATUS_COLUMN_BITMASK |
			FragmentEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_FCI_T_S_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_T_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(FragmentEntryVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FRAGMENTENTRYVERSION =
		"SELECT fragmentEntryVersion FROM FragmentEntryVersion fragmentEntryVersion";

	private static final String _SQL_SELECT_FRAGMENTENTRYVERSION_WHERE =
		"SELECT fragmentEntryVersion FROM FragmentEntryVersion fragmentEntryVersion WHERE ";

	private static final String _SQL_COUNT_FRAGMENTENTRYVERSION =
		"SELECT COUNT(fragmentEntryVersion) FROM FragmentEntryVersion fragmentEntryVersion";

	private static final String _SQL_COUNT_FRAGMENTENTRYVERSION_WHERE =
		"SELECT COUNT(fragmentEntryVersion) FROM FragmentEntryVersion fragmentEntryVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"fragmentEntryVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FragmentEntryVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FragmentEntryVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	static {
		try {
			Class.forName(FragmentPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}