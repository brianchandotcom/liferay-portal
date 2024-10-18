/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCVersionedEntryVersionException;
import com.liferay.portal.tools.service.builder.test.model.ERCVersionedEntryVersion;
import com.liferay.portal.tools.service.builder.test.model.ERCVersionedEntryVersionTable;
import com.liferay.portal.tools.service.builder.test.model.impl.ERCVersionedEntryVersionImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.ERCVersionedEntryVersionModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCVersionedEntryVersionPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCVersionedEntryVersionUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the erc versioned entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ERCVersionedEntryVersionPersistenceImpl
	extends BasePersistenceImpl<ERCVersionedEntryVersion>
	implements ERCVersionedEntryVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ERCVersionedEntryVersionUtil</code> to access the erc versioned entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ERCVersionedEntryVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByErcVersionedEntryId;
	private FinderPath _finderPathWithoutPaginationFindByErcVersionedEntryId;
	private FinderPath _finderPathCountByErcVersionedEntryId;

	/**
	 * Returns all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId) {

		return findByErcVersionedEntryId(
			ercVersionedEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId, int start, int end) {

		return findByErcVersionedEntryId(ercVersionedEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByErcVersionedEntryId(
			ercVersionedEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByErcVersionedEntryId;
				finderArgs = new Object[] {ercVersionedEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByErcVersionedEntryId;
			finderArgs = new Object[] {
				ercVersionedEntryId, start, end, orderByComparator
			};
		}

		List<ERCVersionedEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntryVersion ercVersionedEntryVersion : list) {
					if (ercVersionedEntryId !=
							ercVersionedEntryVersion.getErcVersionedEntryId()) {

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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_ERCVERSIONEDENTRYID_ERCVERSIONEDENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ercVersionedEntryId);

				list = (List<ERCVersionedEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByErcVersionedEntryId_First(
			long ercVersionedEntryId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByErcVersionedEntryId_First(
				ercVersionedEntryId, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ercVersionedEntryId=");
		sb.append(ercVersionedEntryId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByErcVersionedEntryId_First(
		long ercVersionedEntryId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		List<ERCVersionedEntryVersion> list = findByErcVersionedEntryId(
			ercVersionedEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByErcVersionedEntryId_Last(
			long ercVersionedEntryId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByErcVersionedEntryId_Last(
				ercVersionedEntryId, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ercVersionedEntryId=");
		sb.append(ercVersionedEntryId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByErcVersionedEntryId_Last(
		long ercVersionedEntryId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		int count = countByErcVersionedEntryId(ercVersionedEntryId);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntryVersion> list = findByErcVersionedEntryId(
			ercVersionedEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion[] findByErcVersionedEntryId_PrevAndNext(
			long ercVersionedEntryVersionId, long ercVersionedEntryId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = findByPrimaryKey(
			ercVersionedEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntryVersion[] array =
				new ERCVersionedEntryVersionImpl[3];

			array[0] = getByErcVersionedEntryId_PrevAndNext(
				session, ercVersionedEntryVersion, ercVersionedEntryId,
				orderByComparator, true);

			array[1] = ercVersionedEntryVersion;

			array[2] = getByErcVersionedEntryId_PrevAndNext(
				session, ercVersionedEntryVersion, ercVersionedEntryId,
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

	protected ERCVersionedEntryVersion getByErcVersionedEntryId_PrevAndNext(
		Session session, ERCVersionedEntryVersion ercVersionedEntryVersion,
		long ercVersionedEntryId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_ERCVERSIONEDENTRYID_ERCVERSIONEDENTRYID_2);

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
			sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ercVersionedEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ercVersionedEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entry versions where ercVersionedEntryId = &#63; from the database.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 */
	@Override
	public void removeByErcVersionedEntryId(long ercVersionedEntryId) {
		for (ERCVersionedEntryVersion ercVersionedEntryVersion :
				findByErcVersionedEntryId(
					ercVersionedEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ercVersionedEntryVersion);
		}
	}

	/**
	 * Returns the number of erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByErcVersionedEntryId(long ercVersionedEntryId) {
		FinderPath finderPath = _finderPathCountByErcVersionedEntryId;

		Object[] finderArgs = new Object[] {ercVersionedEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_ERCVERSIONEDENTRYID_ERCVERSIONEDENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ercVersionedEntryId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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
		_FINDER_COLUMN_ERCVERSIONEDENTRYID_ERCVERSIONEDENTRYID_2 =
			"ercVersionedEntryVersion.ercVersionedEntryId = ?";

	private FinderPath _finderPathFetchByErcVersionedEntryId_Version;

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByErcVersionedEntryId_Version(
			long ercVersionedEntryId, int version)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByErcVersionedEntryId_Version(ercVersionedEntryId, version);

		if (ercVersionedEntryVersion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("ercVersionedEntryId=");
			sb.append(ercVersionedEntryId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchERCVersionedEntryVersionException(sb.toString());
		}

		return ercVersionedEntryVersion;
	}

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version) {

		return fetchByErcVersionedEntryId_Version(
			ercVersionedEntryId, version, true);
	}

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {ercVersionedEntryId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByErcVersionedEntryId_Version, finderArgs,
				this);
		}

		if (result instanceof ERCVersionedEntryVersion) {
			ERCVersionedEntryVersion ercVersionedEntryVersion =
				(ERCVersionedEntryVersion)result;

			if ((ercVersionedEntryId !=
					ercVersionedEntryVersion.getErcVersionedEntryId()) ||
				(version != ercVersionedEntryVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_ERCVERSIONEDENTRYID_VERSION_ERCVERSIONEDENTRYID_2);

			sb.append(_FINDER_COLUMN_ERCVERSIONEDENTRYID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ercVersionedEntryId);

				queryPos.add(version);

				List<ERCVersionedEntryVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByErcVersionedEntryId_Version,
							finderArgs, list);
					}
				}
				else {
					ERCVersionedEntryVersion ercVersionedEntryVersion =
						list.get(0);

					result = ercVersionedEntryVersion;

					cacheResult(ercVersionedEntryVersion);
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
			return (ERCVersionedEntryVersion)result;
		}
	}

	/**
	 * Removes the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the erc versioned entry version that was removed
	 */
	@Override
	public ERCVersionedEntryVersion removeByErcVersionedEntryId_Version(
			long ercVersionedEntryId, int version)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			findByErcVersionedEntryId_Version(ercVersionedEntryId, version);

		return remove(ercVersionedEntryVersion);
	}

	/**
	 * Returns the number of erc versioned entry versions where ercVersionedEntryId = &#63; and version = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version) {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByErcVersionedEntryId_Version(ercVersionedEntryId, version);

		if (ercVersionedEntryVersion == null) {
			return 0;
		}

		return 1;
	}

	private static final String
		_FINDER_COLUMN_ERCVERSIONEDENTRYID_VERSION_ERCVERSIONEDENTRYID_2 =
			"ercVersionedEntryVersion.ercVersionedEntryId = ? AND ";

	private static final String
		_FINDER_COLUMN_ERCVERSIONEDENTRYID_VERSION_VERSION_2 =
			"ercVersionedEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<ERCVersionedEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntryVersion ercVersionedEntryVersion : list) {
					if (!uuid.equals(ercVersionedEntryVersion.getUuid())) {
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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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
				sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<ERCVersionedEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_First(
			String uuid,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = fetchByUuid_First(
			uuid, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_First(
		String uuid,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		List<ERCVersionedEntryVersion> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_Last(
			String uuid,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = fetchByUuid_Last(
			uuid, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_Last(
		String uuid,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntryVersion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion[] findByUuid_PrevAndNext(
			long ercVersionedEntryVersionId, String uuid,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		uuid = Objects.toString(uuid, "");

		ERCVersionedEntryVersion ercVersionedEntryVersion = findByPrimaryKey(
			ercVersionedEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntryVersion[] array =
				new ERCVersionedEntryVersionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, ercVersionedEntryVersion, uuid, orderByComparator,
				true);

			array[1] = ercVersionedEntryVersion;

			array[2] = getByUuid_PrevAndNext(
				session, ercVersionedEntryVersion, uuid, orderByComparator,
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

	protected ERCVersionedEntryVersion getByUuid_PrevAndNext(
		Session session, ERCVersionedEntryVersion ercVersionedEntryVersion,
		String uuid,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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
			sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
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
						ercVersionedEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ERCVersionedEntryVersion ercVersionedEntryVersion :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ercVersionedEntryVersion);
		}
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
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
		"ercVersionedEntryVersion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(ercVersionedEntryVersion.uuid IS NULL OR ercVersionedEntryVersion.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Version;
	private FinderPath _finderPathCountByUuid_Version;

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version) {

		return findByUuid_Version(
			uuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end) {

		return findByUuid_Version(uuid, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid_Version(
			uuid, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_Version;
				finderArgs = new Object[] {uuid, version};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_Version;
			finderArgs = new Object[] {
				uuid, version, start, end, orderByComparator
			};
		}

		List<ERCVersionedEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntryVersion ercVersionedEntryVersion : list) {
					if (!uuid.equals(ercVersionedEntryVersion.getUuid()) ||
						(version != ercVersionedEntryVersion.getVersion())) {

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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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
				sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<ERCVersionedEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_Version_First(
			String uuid, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByUuid_Version_First(uuid, version, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_Version_First(
		String uuid, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		List<ERCVersionedEntryVersion> list = findByUuid_Version(
			uuid, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_Version_Last(
			String uuid, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByUuid_Version_Last(uuid, version, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_Version_Last(
		String uuid, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		int count = countByUuid_Version(uuid, version);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntryVersion> list = findByUuid_Version(
			uuid, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion[] findByUuid_Version_PrevAndNext(
			long ercVersionedEntryVersionId, String uuid, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		uuid = Objects.toString(uuid, "");

		ERCVersionedEntryVersion ercVersionedEntryVersion = findByPrimaryKey(
			ercVersionedEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntryVersion[] array =
				new ERCVersionedEntryVersionImpl[3];

			array[0] = getByUuid_Version_PrevAndNext(
				session, ercVersionedEntryVersion, uuid, version,
				orderByComparator, true);

			array[1] = ercVersionedEntryVersion;

			array[2] = getByUuid_Version_PrevAndNext(
				session, ercVersionedEntryVersion, uuid, version,
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

	protected ERCVersionedEntryVersion getByUuid_Version_PrevAndNext(
		Session session, ERCVersionedEntryVersion ercVersionedEntryVersion,
		String uuid, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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
			sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
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
						ercVersionedEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	@Override
	public void removeByUuid_Version(String uuid, int version) {
		for (ERCVersionedEntryVersion ercVersionedEntryVersion :
				findByUuid_Version(
					uuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ercVersionedEntryVersion);
		}
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUuid_Version(String uuid, int version) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_Version;

		Object[] finderArgs = new Object[] {uuid, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
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
		"ercVersionedEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_VERSION_UUID_3 =
		"(ercVersionedEntryVersion.uuid IS NULL OR ercVersionedEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_VERSION_VERSION_2 =
		"ercVersionedEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUUID_G;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId) {

		return findByUUID_G(
			uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUUID_G;
				finderArgs = new Object[] {uuid, groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUUID_G;
			finderArgs = new Object[] {
				uuid, groupId, start, end, orderByComparator
			};
		}

		List<ERCVersionedEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntryVersion ercVersionedEntryVersion : list) {
					if (!uuid.equals(ercVersionedEntryVersion.getUuid()) ||
						(groupId != ercVersionedEntryVersion.getGroupId())) {

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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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
				sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<ERCVersionedEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = fetchByUUID_G_First(
			uuid, groupId, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		List<ERCVersionedEntryVersion> list = findByUUID_G(
			uuid, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUUID_G_Last(
			String uuid, long groupId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = fetchByUUID_G_Last(
			uuid, groupId, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUUID_G_Last(
		String uuid, long groupId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		int count = countByUUID_G(uuid, groupId);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntryVersion> list = findByUUID_G(
			uuid, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion[] findByUUID_G_PrevAndNext(
			long ercVersionedEntryVersionId, String uuid, long groupId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		uuid = Objects.toString(uuid, "");

		ERCVersionedEntryVersion ercVersionedEntryVersion = findByPrimaryKey(
			ercVersionedEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntryVersion[] array =
				new ERCVersionedEntryVersionImpl[3];

			array[0] = getByUUID_G_PrevAndNext(
				session, ercVersionedEntryVersion, uuid, groupId,
				orderByComparator, true);

			array[1] = ercVersionedEntryVersion;

			array[2] = getByUUID_G_PrevAndNext(
				session, ercVersionedEntryVersion, uuid, groupId,
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

	protected ERCVersionedEntryVersion getByUUID_G_PrevAndNext(
		Session session, ERCVersionedEntryVersion ercVersionedEntryVersion,
		String uuid, long groupId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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
			sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
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
						ercVersionedEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUUID_G(String uuid, long groupId) {
		for (ERCVersionedEntryVersion ercVersionedEntryVersion :
				findByUUID_G(
					uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ercVersionedEntryVersion);
		}
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
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
		"ercVersionedEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(ercVersionedEntryVersion.uuid IS NULL OR ercVersionedEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"ercVersionedEntryVersion.groupId = ?";

	private FinderPath _finderPathFetchByUUID_G_Version;

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByUUID_G_Version(uuid, groupId, version);

		if (ercVersionedEntryVersion == null) {
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

			throw new NoSuchERCVersionedEntryVersionException(sb.toString());
		}

		return ercVersionedEntryVersion;
	}

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version) {

		return fetchByUUID_G_Version(uuid, groupId, version, true);
	}

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G_Version, finderArgs, this);
		}

		if (result instanceof ERCVersionedEntryVersion) {
			ERCVersionedEntryVersion ercVersionedEntryVersion =
				(ERCVersionedEntryVersion)result;

			if (!Objects.equals(uuid, ercVersionedEntryVersion.getUuid()) ||
				(groupId != ercVersionedEntryVersion.getGroupId()) ||
				(version != ercVersionedEntryVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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

				List<ERCVersionedEntryVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G_Version, finderArgs, list);
					}
				}
				else {
					ERCVersionedEntryVersion ercVersionedEntryVersion =
						list.get(0);

					result = ercVersionedEntryVersion;

					cacheResult(ercVersionedEntryVersion);
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
			return (ERCVersionedEntryVersion)result;
		}
	}

	/**
	 * Removes the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the erc versioned entry version that was removed
	 */
	@Override
	public ERCVersionedEntryVersion removeByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			findByUUID_G_Version(uuid, groupId, version);

		return remove(ercVersionedEntryVersion);
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and groupId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUUID_G_Version(String uuid, long groupId, int version) {
		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByUUID_G_Version(uuid, groupId, version);

		if (ercVersionedEntryVersion == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_UUID_G_VERSION_UUID_2 =
		"ercVersionedEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_VERSION_UUID_3 =
		"(ercVersionedEntryVersion.uuid IS NULL OR ercVersionedEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_VERSION_GROUPID_2 =
		"ercVersionedEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_VERSION_VERSION_2 =
		"ercVersionedEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<ERCVersionedEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntryVersion ercVersionedEntryVersion : list) {
					if (!uuid.equals(ercVersionedEntryVersion.getUuid()) ||
						(companyId !=
							ercVersionedEntryVersion.getCompanyId())) {

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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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
				sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<ERCVersionedEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		List<ERCVersionedEntryVersion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntryVersion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion[] findByUuid_C_PrevAndNext(
			long ercVersionedEntryVersionId, String uuid, long companyId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		uuid = Objects.toString(uuid, "");

		ERCVersionedEntryVersion ercVersionedEntryVersion = findByPrimaryKey(
			ercVersionedEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntryVersion[] array =
				new ERCVersionedEntryVersionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, ercVersionedEntryVersion, uuid, companyId,
				orderByComparator, true);

			array[1] = ercVersionedEntryVersion;

			array[2] = getByUuid_C_PrevAndNext(
				session, ercVersionedEntryVersion, uuid, companyId,
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

	protected ERCVersionedEntryVersion getByUuid_C_PrevAndNext(
		Session session, ERCVersionedEntryVersion ercVersionedEntryVersion,
		String uuid, long companyId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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
			sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
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
						ercVersionedEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ERCVersionedEntryVersion ercVersionedEntryVersion :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ercVersionedEntryVersion);
		}
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
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
		"ercVersionedEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(ercVersionedEntryVersion.uuid IS NULL OR ercVersionedEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"ercVersionedEntryVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Version;
	private FinderPath _finderPathCountByUuid_C_Version;

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version) {

		return findByUuid_C_Version(
			uuid, companyId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end) {

		return findByUuid_C_Version(uuid, companyId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C_Version;
				finderArgs = new Object[] {uuid, companyId, version};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C_Version;
			finderArgs = new Object[] {
				uuid, companyId, version, start, end, orderByComparator
			};
		}

		List<ERCVersionedEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntryVersion ercVersionedEntryVersion : list) {
					if (!uuid.equals(ercVersionedEntryVersion.getUuid()) ||
						(companyId !=
							ercVersionedEntryVersion.getCompanyId()) ||
						(version != ercVersionedEntryVersion.getVersion())) {

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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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
				sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<ERCVersionedEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByUuid_C_Version_First(
				uuid, companyId, version, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
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

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		List<ERCVersionedEntryVersion> list = findByUuid_C_Version(
			uuid, companyId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_C_Version_Last(
			String uuid, long companyId, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByUuid_C_Version_Last(
				uuid, companyId, version, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
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

		throw new NoSuchERCVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_C_Version_Last(
		String uuid, long companyId, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		int count = countByUuid_C_Version(uuid, companyId, version);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntryVersion> list = findByUuid_C_Version(
			uuid, companyId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion[] findByUuid_C_Version_PrevAndNext(
			long ercVersionedEntryVersionId, String uuid, long companyId,
			int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		uuid = Objects.toString(uuid, "");

		ERCVersionedEntryVersion ercVersionedEntryVersion = findByPrimaryKey(
			ercVersionedEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntryVersion[] array =
				new ERCVersionedEntryVersionImpl[3];

			array[0] = getByUuid_C_Version_PrevAndNext(
				session, ercVersionedEntryVersion, uuid, companyId, version,
				orderByComparator, true);

			array[1] = ercVersionedEntryVersion;

			array[2] = getByUuid_C_Version_PrevAndNext(
				session, ercVersionedEntryVersion, uuid, companyId, version,
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

	protected ERCVersionedEntryVersion getByUuid_C_Version_PrevAndNext(
		Session session, ERCVersionedEntryVersion ercVersionedEntryVersion,
		String uuid, long companyId, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE);

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
			sb.append(ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
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
						ercVersionedEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	@Override
	public void removeByUuid_C_Version(
		String uuid, long companyId, int version) {

		for (ERCVersionedEntryVersion ercVersionedEntryVersion :
				findByUuid_C_Version(
					uuid, companyId, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ercVersionedEntryVersion);
		}
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUuid_C_Version(String uuid, long companyId, int version) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C_Version;

		Object[] finderArgs = new Object[] {uuid, companyId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
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
		"ercVersionedEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_UUID_3 =
		"(ercVersionedEntryVersion.uuid IS NULL OR ercVersionedEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2 =
		"ercVersionedEntryVersion.companyId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_VERSION_2 =
		"ercVersionedEntryVersion.version = ?";

	public ERCVersionedEntryVersionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ERCVersionedEntryVersion.class);

		setModelImplClass(ERCVersionedEntryVersionImpl.class);
		setModelPKClass(long.class);

		setTable(ERCVersionedEntryVersionTable.INSTANCE);
	}

	/**
	 * Caches the erc versioned entry version in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntryVersion the erc versioned entry version
	 */
	@Override
	public void cacheResult(ERCVersionedEntryVersion ercVersionedEntryVersion) {
		entityCache.putResult(
			ERCVersionedEntryVersionImpl.class,
			ercVersionedEntryVersion.getPrimaryKey(), ercVersionedEntryVersion);

		finderCache.putResult(
			_finderPathFetchByErcVersionedEntryId_Version,
			new Object[] {
				ercVersionedEntryVersion.getErcVersionedEntryId(),
				ercVersionedEntryVersion.getVersion()
			},
			ercVersionedEntryVersion);

		finderCache.putResult(
			_finderPathFetchByUUID_G_Version,
			new Object[] {
				ercVersionedEntryVersion.getUuid(),
				ercVersionedEntryVersion.getGroupId(),
				ercVersionedEntryVersion.getVersion()
			},
			ercVersionedEntryVersion);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the erc versioned entry versions in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntryVersions the erc versioned entry versions
	 */
	@Override
	public void cacheResult(
		List<ERCVersionedEntryVersion> ercVersionedEntryVersions) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ercVersionedEntryVersions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ERCVersionedEntryVersion ercVersionedEntryVersion :
				ercVersionedEntryVersions) {

			if (entityCache.getResult(
					ERCVersionedEntryVersionImpl.class,
					ercVersionedEntryVersion.getPrimaryKey()) == null) {

				cacheResult(ercVersionedEntryVersion);
			}
		}
	}

	/**
	 * Clears the cache for all erc versioned entry versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ERCVersionedEntryVersionImpl.class);

		finderCache.clearCache(ERCVersionedEntryVersionImpl.class);
	}

	/**
	 * Clears the cache for the erc versioned entry version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ERCVersionedEntryVersion ercVersionedEntryVersion) {
		entityCache.removeResult(
			ERCVersionedEntryVersionImpl.class, ercVersionedEntryVersion);
	}

	@Override
	public void clearCache(
		List<ERCVersionedEntryVersion> ercVersionedEntryVersions) {

		for (ERCVersionedEntryVersion ercVersionedEntryVersion :
				ercVersionedEntryVersions) {

			entityCache.removeResult(
				ERCVersionedEntryVersionImpl.class, ercVersionedEntryVersion);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ERCVersionedEntryVersionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ERCVersionedEntryVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ERCVersionedEntryVersionModelImpl ercVersionedEntryVersionModelImpl) {

		Object[] args = new Object[] {
			ercVersionedEntryVersionModelImpl.getErcVersionedEntryId(),
			ercVersionedEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathFetchByErcVersionedEntryId_Version, args,
			ercVersionedEntryVersionModelImpl);

		args = new Object[] {
			ercVersionedEntryVersionModelImpl.getUuid(),
			ercVersionedEntryVersionModelImpl.getGroupId(),
			ercVersionedEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathFetchByUUID_G_Version, args,
			ercVersionedEntryVersionModelImpl);
	}

	/**
	 * Creates a new erc versioned entry version with the primary key. Does not add the erc versioned entry version to the database.
	 *
	 * @param ercVersionedEntryVersionId the primary key for the new erc versioned entry version
	 * @return the new erc versioned entry version
	 */
	@Override
	public ERCVersionedEntryVersion create(long ercVersionedEntryVersionId) {
		ERCVersionedEntryVersion ercVersionedEntryVersion =
			new ERCVersionedEntryVersionImpl();

		ercVersionedEntryVersion.setNew(true);
		ercVersionedEntryVersion.setPrimaryKey(ercVersionedEntryVersionId);

		ercVersionedEntryVersion.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return ercVersionedEntryVersion;
	}

	/**
	 * Removes the erc versioned entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version that was removed
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion remove(long ercVersionedEntryVersionId)
		throws NoSuchERCVersionedEntryVersionException {

		return remove((Serializable)ercVersionedEntryVersionId);
	}

	/**
	 * Removes the erc versioned entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the erc versioned entry version
	 * @return the erc versioned entry version that was removed
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion remove(Serializable primaryKey)
		throws NoSuchERCVersionedEntryVersionException {

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntryVersion ercVersionedEntryVersion =
				(ERCVersionedEntryVersion)session.get(
					ERCVersionedEntryVersionImpl.class, primaryKey);

			if (ercVersionedEntryVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchERCVersionedEntryVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ercVersionedEntryVersion);
		}
		catch (NoSuchERCVersionedEntryVersionException noSuchEntityException) {
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
	protected ERCVersionedEntryVersion removeImpl(
		ERCVersionedEntryVersion ercVersionedEntryVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ercVersionedEntryVersion)) {
				ercVersionedEntryVersion =
					(ERCVersionedEntryVersion)session.get(
						ERCVersionedEntryVersionImpl.class,
						ercVersionedEntryVersion.getPrimaryKeyObj());
			}

			if (ercVersionedEntryVersion != null) {
				session.delete(ercVersionedEntryVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ercVersionedEntryVersion != null) {
			clearCache(ercVersionedEntryVersion);
		}

		return ercVersionedEntryVersion;
	}

	@Override
	public ERCVersionedEntryVersion updateImpl(
		ERCVersionedEntryVersion ercVersionedEntryVersion) {

		boolean isNew = ercVersionedEntryVersion.isNew();

		if (!(ercVersionedEntryVersion instanceof
				ERCVersionedEntryVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ercVersionedEntryVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ercVersionedEntryVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ercVersionedEntryVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ERCVersionedEntryVersion implementation " +
					ercVersionedEntryVersion.getClass());
		}

		ERCVersionedEntryVersionModelImpl ercVersionedEntryVersionModelImpl =
			(ERCVersionedEntryVersionModelImpl)ercVersionedEntryVersion;

		if (Validator.isNull(
				ercVersionedEntryVersion.getExternalReferenceCode())) {

			ercVersionedEntryVersion.setExternalReferenceCode(
				String.valueOf(ercVersionedEntryVersion.getPrimaryKey()));
		}
		else {
			if (!Objects.equals(
					ercVersionedEntryVersionModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					ercVersionedEntryVersion.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = ercVersionedEntryVersion.getCompanyId();

					long groupId = ercVersionedEntryVersion.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = ercVersionedEntryVersion.getPrimaryKey();
					}

					try {
						ercVersionedEntryVersion.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								ERCVersionedEntryVersion.class.getName(),
								classPK, ContentTypes.TEXT_HTML,
								Sanitizer.MODE_ALL,
								ercVersionedEntryVersion.
									getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ercVersionedEntryVersion);
			}
			else {
				throw new IllegalArgumentException(
					"ERCVersionedEntryVersion is read only, create a new version instead");
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ERCVersionedEntryVersionImpl.class,
			ercVersionedEntryVersionModelImpl, false, true);

		cacheUniqueFindersCache(ercVersionedEntryVersionModelImpl);

		if (isNew) {
			ercVersionedEntryVersion.setNew(false);
		}

		ercVersionedEntryVersion.resetOriginalValues();

		return ercVersionedEntryVersion;
	}

	/**
	 * Returns the erc versioned entry version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the erc versioned entry version
	 * @return the erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = fetchByPrimaryKey(
			primaryKey);

		if (ercVersionedEntryVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchERCVersionedEntryVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ercVersionedEntryVersion;
	}

	/**
	 * Returns the erc versioned entry version with the primary key or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByPrimaryKey(
			long ercVersionedEntryVersionId)
		throws NoSuchERCVersionedEntryVersionException {

		return findByPrimaryKey((Serializable)ercVersionedEntryVersionId);
	}

	/**
	 * Returns the erc versioned entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version, or <code>null</code> if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByPrimaryKey(
		long ercVersionedEntryVersionId) {

		return fetchByPrimaryKey((Serializable)ercVersionedEntryVersionId);
	}

	/**
	 * Returns all the erc versioned entry versions.
	 *
	 * @return the erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findAll(
		int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findAll(
		int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
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

		List<ERCVersionedEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ERCVERSIONEDENTRYVERSION;

				sql = sql.concat(
					ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ERCVersionedEntryVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Removes all the erc versioned entry versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ERCVersionedEntryVersion ercVersionedEntryVersion : findAll()) {
			remove(ercVersionedEntryVersion);
		}
	}

	/**
	 * Returns the number of erc versioned entry versions.
	 *
	 * @return the number of erc versioned entry versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_ERCVERSIONEDENTRYVERSION);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ercVersionedEntryVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ERCVERSIONEDENTRYVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ERCVersionedEntryVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the erc versioned entry version persistence.
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

		_finderPathWithPaginationFindByErcVersionedEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByErcVersionedEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ercVersionedEntryId"}, true);

		_finderPathWithoutPaginationFindByErcVersionedEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByErcVersionedEntryId", new String[] {Long.class.getName()},
			new String[] {"ercVersionedEntryId"}, true);

		_finderPathCountByErcVersionedEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByErcVersionedEntryId", new String[] {Long.class.getName()},
			new String[] {"ercVersionedEntryId"}, false);

		_finderPathFetchByErcVersionedEntryId_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByErcVersionedEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"ercVersionedEntryId", "version"}, true);

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

		_finderPathWithPaginationFindByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_Version",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "version"}, true);

		_finderPathWithoutPaginationFindByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"uuid_", "version"}, true);

		_finderPathCountByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"uuid_", "version"}, false);

		_finderPathWithPaginationFindByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUUID_G",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathWithoutPaginationFindByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathFetchByUUID_G_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "groupId", "version"}, true);

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

		_finderPathWithPaginationFindByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, true);

		_finderPathWithoutPaginationFindByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, true);

		_finderPathCountByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, false);

		ERCVersionedEntryVersionUtil.setPersistence(this);
	}

	public void destroy() {
		ERCVersionedEntryVersionUtil.setPersistence(null);

		entityCache.removeCache(ERCVersionedEntryVersionImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ERCVERSIONEDENTRYVERSION =
		"SELECT ercVersionedEntryVersion FROM ERCVersionedEntryVersion ercVersionedEntryVersion";

	private static final String _SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE =
		"SELECT ercVersionedEntryVersion FROM ERCVersionedEntryVersion ercVersionedEntryVersion WHERE ";

	private static final String _SQL_COUNT_ERCVERSIONEDENTRYVERSION =
		"SELECT COUNT(ercVersionedEntryVersion) FROM ERCVersionedEntryVersion ercVersionedEntryVersion";

	private static final String _SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE =
		"SELECT COUNT(ercVersionedEntryVersion) FROM ERCVersionedEntryVersion ercVersionedEntryVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"ercVersionedEntryVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ERCVersionedEntryVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ERCVersionedEntryVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ERCVersionedEntryVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}