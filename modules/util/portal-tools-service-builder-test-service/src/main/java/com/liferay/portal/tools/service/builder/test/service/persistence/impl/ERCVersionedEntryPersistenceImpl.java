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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.DuplicateERCVersionedEntryExternalReferenceCodeException;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCVersionedEntryException;
import com.liferay.portal.tools.service.builder.test.model.ERCVersionedEntry;
import com.liferay.portal.tools.service.builder.test.model.ERCVersionedEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.ERCVersionedEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.ERCVersionedEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCVersionedEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCVersionedEntryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the erc versioned entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ERCVersionedEntryPersistenceImpl
	extends BasePersistenceImpl<ERCVersionedEntry>
	implements ERCVersionedEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ERCVersionedEntryUtil</code> to access the erc versioned entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ERCVersionedEntryImpl.class.getName();

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

	/**
	 * Returns all the erc versioned entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
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

		List<ERCVersionedEntry> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntry ercVersionedEntry : list) {
					if (!uuid.equals(ercVersionedEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

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
				sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<ERCVersionedEntry>)QueryUtil.list(
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
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUuid_First(
			String uuid, OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUuid_First(
		String uuid, OrderByComparator<ERCVersionedEntry> orderByComparator) {

		List<ERCVersionedEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUuid_Last(
			String uuid, OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUuid_Last(
		String uuid, OrderByComparator<ERCVersionedEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entries before and after the current erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param ercVersionedEntryId the primary key of the current erc versioned entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry[] findByUuid_PrevAndNext(
			long ercVersionedEntryId, String uuid,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		uuid = Objects.toString(uuid, "");

		ERCVersionedEntry ercVersionedEntry = findByPrimaryKey(
			ercVersionedEntryId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntry[] array = new ERCVersionedEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, ercVersionedEntry, uuid, orderByComparator, true);

			array[1] = ercVersionedEntry;

			array[2] = getByUuid_PrevAndNext(
				session, ercVersionedEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ERCVersionedEntry getByUuid_PrevAndNext(
		Session session, ERCVersionedEntry ercVersionedEntry, String uuid,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

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
			sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
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
						ercVersionedEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ERCVersionedEntry ercVersionedEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ercVersionedEntry);
		}
	}

	/**
	 * Returns the number of erc versioned entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching erc versioned entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRY_WHERE);

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
		"ercVersionedEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(ercVersionedEntry.uuid IS NULL OR ercVersionedEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_Head;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Head;
	private FinderPath _finderPathCountByUuid_Head;

	/**
	 * Returns all the erc versioned entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_Head(String uuid, boolean head) {
		return findByUuid_Head(
			uuid, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end) {

		return findByUuid_Head(uuid, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return findByUuid_Head(uuid, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_Head;
				finderArgs = new Object[] {uuid, head};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_Head;
			finderArgs = new Object[] {
				uuid, head, start, end, orderByComparator
			};
		}

		List<ERCVersionedEntry> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntry ercVersionedEntry : list) {
					if (!uuid.equals(ercVersionedEntry.getUuid()) ||
						(head != ercVersionedEntry.isHead())) {

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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(head);

				list = (List<ERCVersionedEntry>)QueryUtil.list(
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
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUuid_Head_First(
			String uuid, boolean head,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUuid_Head_First(
			uuid, head, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUuid_Head_First(
		String uuid, boolean head,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		List<ERCVersionedEntry> list = findByUuid_Head(
			uuid, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUuid_Head_Last(
			String uuid, boolean head,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUuid_Head_Last(
			uuid, head, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUuid_Head_Last(
		String uuid, boolean head,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		int count = countByUuid_Head(uuid, head);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntry> list = findByUuid_Head(
			uuid, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entries before and after the current erc versioned entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param ercVersionedEntryId the primary key of the current erc versioned entry
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry[] findByUuid_Head_PrevAndNext(
			long ercVersionedEntryId, String uuid, boolean head,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		uuid = Objects.toString(uuid, "");

		ERCVersionedEntry ercVersionedEntry = findByPrimaryKey(
			ercVersionedEntryId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntry[] array = new ERCVersionedEntryImpl[3];

			array[0] = getByUuid_Head_PrevAndNext(
				session, ercVersionedEntry, uuid, head, orderByComparator,
				true);

			array[1] = ercVersionedEntry;

			array[2] = getByUuid_Head_PrevAndNext(
				session, ercVersionedEntry, uuid, head, orderByComparator,
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

	protected ERCVersionedEntry getByUuid_Head_PrevAndNext(
		Session session, ERCVersionedEntry ercVersionedEntry, String uuid,
		boolean head, OrderByComparator<ERCVersionedEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

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
			sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ercVersionedEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entries where uuid = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 */
	@Override
	public void removeByUuid_Head(String uuid, boolean head) {
		for (ERCVersionedEntry ercVersionedEntry :
				findByUuid_Head(
					uuid, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ercVersionedEntry);
		}
	}

	/**
	 * Returns the number of erc versioned entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the number of matching erc versioned entries
	 */
	@Override
	public int countByUuid_Head(String uuid, boolean head) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_Head;

		Object[] finderArgs = new Object[] {uuid, head};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_HEAD_UUID_2 =
		"ercVersionedEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_HEAD_UUID_3 =
		"(ercVersionedEntry.uuid IS NULL OR ercVersionedEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_HEAD_HEAD_2 =
		"ercVersionedEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByUUID_G;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns all the erc versioned entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUUID_G(String uuid, long groupId) {
		return findByUUID_G(
			uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
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

		List<ERCVersionedEntry> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntry ercVersionedEntry : list) {
					if (!uuid.equals(ercVersionedEntry.getUuid()) ||
						(groupId != ercVersionedEntry.getGroupId())) {

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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

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
				sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<ERCVersionedEntry>)QueryUtil.list(
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
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUUID_G_First(
			uuid, groupId, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		List<ERCVersionedEntry> list = findByUUID_G(
			uuid, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUUID_G_Last(
			String uuid, long groupId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUUID_G_Last(
			uuid, groupId, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUUID_G_Last(
		String uuid, long groupId,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		int count = countByUUID_G(uuid, groupId);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntry> list = findByUUID_G(
			uuid, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entries before and after the current erc versioned entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param ercVersionedEntryId the primary key of the current erc versioned entry
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry[] findByUUID_G_PrevAndNext(
			long ercVersionedEntryId, String uuid, long groupId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		uuid = Objects.toString(uuid, "");

		ERCVersionedEntry ercVersionedEntry = findByPrimaryKey(
			ercVersionedEntryId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntry[] array = new ERCVersionedEntryImpl[3];

			array[0] = getByUUID_G_PrevAndNext(
				session, ercVersionedEntry, uuid, groupId, orderByComparator,
				true);

			array[1] = ercVersionedEntry;

			array[2] = getByUUID_G_PrevAndNext(
				session, ercVersionedEntry, uuid, groupId, orderByComparator,
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

	protected ERCVersionedEntry getByUUID_G_PrevAndNext(
		Session session, ERCVersionedEntry ercVersionedEntry, String uuid,
		long groupId, OrderByComparator<ERCVersionedEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

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
			sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
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
						ercVersionedEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entries where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUUID_G(String uuid, long groupId) {
		for (ERCVersionedEntry ercVersionedEntry :
				findByUUID_G(
					uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ercVersionedEntry);
		}
	}

	/**
	 * Returns the number of erc versioned entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching erc versioned entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRY_WHERE);

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
		"ercVersionedEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(ercVersionedEntry.uuid IS NULL OR ercVersionedEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"ercVersionedEntry.groupId = ?";

	private FinderPath _finderPathFetchByUUID_G_Head;

	/**
	 * Returns the erc versioned entry where uuid = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchERCVersionedEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUUID_G_Head(
			uuid, groupId, head);

		if (ercVersionedEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append(", head=");
			sb.append(head);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchERCVersionedEntryException(sb.toString());
		}

		return ercVersionedEntry;
	}

	/**
	 * Returns the erc versioned entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head) {

		return fetchByUUID_G_Head(uuid, groupId, head, true);
	}

	/**
	 * Returns the erc versioned entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId, head};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G_Head, finderArgs, this);
		}

		if (result instanceof ERCVersionedEntry) {
			ERCVersionedEntry ercVersionedEntry = (ERCVersionedEntry)result;

			if (!Objects.equals(uuid, ercVersionedEntry.getUuid()) ||
				(groupId != ercVersionedEntry.getGroupId()) ||
				(head != ercVersionedEntry.isHead())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_UUID_G_HEAD_HEAD_2);

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

				queryPos.add(head);

				List<ERCVersionedEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G_Head, finderArgs, list);
					}
				}
				else {
					ERCVersionedEntry ercVersionedEntry = list.get(0);

					result = ercVersionedEntry;

					cacheResult(ercVersionedEntry);
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
			return (ERCVersionedEntry)result;
		}
	}

	/**
	 * Removes the erc versioned entry where uuid = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the erc versioned entry that was removed
	 */
	@Override
	public ERCVersionedEntry removeByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = findByUUID_G_Head(
			uuid, groupId, head);

		return remove(ercVersionedEntry);
	}

	/**
	 * Returns the number of erc versioned entries where uuid = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching erc versioned entries
	 */
	@Override
	public int countByUUID_G_Head(String uuid, long groupId, boolean head) {
		ERCVersionedEntry ercVersionedEntry = fetchByUUID_G_Head(
			uuid, groupId, head);

		if (ercVersionedEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_UUID_G_HEAD_UUID_2 =
		"ercVersionedEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_UUID_3 =
		"(ercVersionedEntry.uuid IS NULL OR ercVersionedEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_GROUPID_2 =
		"ercVersionedEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_HEAD_2 =
		"ercVersionedEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
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

		List<ERCVersionedEntry> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntry ercVersionedEntry : list) {
					if (!uuid.equals(ercVersionedEntry.getUuid()) ||
						(companyId != ercVersionedEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

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
				sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<ERCVersionedEntry>)QueryUtil.list(
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
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		List<ERCVersionedEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entries before and after the current erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ercVersionedEntryId the primary key of the current erc versioned entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry[] findByUuid_C_PrevAndNext(
			long ercVersionedEntryId, String uuid, long companyId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		uuid = Objects.toString(uuid, "");

		ERCVersionedEntry ercVersionedEntry = findByPrimaryKey(
			ercVersionedEntryId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntry[] array = new ERCVersionedEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, ercVersionedEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = ercVersionedEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, ercVersionedEntry, uuid, companyId, orderByComparator,
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

	protected ERCVersionedEntry getByUuid_C_PrevAndNext(
		Session session, ERCVersionedEntry ercVersionedEntry, String uuid,
		long companyId, OrderByComparator<ERCVersionedEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

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
			sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
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
						ercVersionedEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ERCVersionedEntry ercVersionedEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ercVersionedEntry);
		}
	}

	/**
	 * Returns the number of erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching erc versioned entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRY_WHERE);

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
		"ercVersionedEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(ercVersionedEntry.uuid IS NULL OR ercVersionedEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"ercVersionedEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C_Head;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Head;
	private FinderPath _finderPathCountByUuid_C_Head;

	/**
	 * Returns all the erc versioned entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head) {

		return findByUuid_C_Head(
			uuid, companyId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end) {

		return findByUuid_C_Head(uuid, companyId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return findByUuid_C_Head(
			uuid, companyId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C_Head;
				finderArgs = new Object[] {uuid, companyId, head};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C_Head;
			finderArgs = new Object[] {
				uuid, companyId, head, start, end, orderByComparator
			};
		}

		List<ERCVersionedEntry> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntry ercVersionedEntry : list) {
					if (!uuid.equals(ercVersionedEntry.getUuid()) ||
						(companyId != ercVersionedEntry.getCompanyId()) ||
						(head != ercVersionedEntry.isHead())) {

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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(head);

				list = (List<ERCVersionedEntry>)QueryUtil.list(
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
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUuid_C_Head_First(
			String uuid, long companyId, boolean head,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUuid_C_Head_First(
			uuid, companyId, head, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUuid_C_Head_First(
		String uuid, long companyId, boolean head,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		List<ERCVersionedEntry> list = findByUuid_C_Head(
			uuid, companyId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByUuid_C_Head_Last(
			String uuid, long companyId, boolean head,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByUuid_C_Head_Last(
			uuid, companyId, head, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByUuid_C_Head_Last(
		String uuid, long companyId, boolean head,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		int count = countByUuid_C_Head(uuid, companyId, head);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntry> list = findByUuid_C_Head(
			uuid, companyId, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entries before and after the current erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param ercVersionedEntryId the primary key of the current erc versioned entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry[] findByUuid_C_Head_PrevAndNext(
			long ercVersionedEntryId, String uuid, long companyId, boolean head,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		uuid = Objects.toString(uuid, "");

		ERCVersionedEntry ercVersionedEntry = findByPrimaryKey(
			ercVersionedEntryId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntry[] array = new ERCVersionedEntryImpl[3];

			array[0] = getByUuid_C_Head_PrevAndNext(
				session, ercVersionedEntry, uuid, companyId, head,
				orderByComparator, true);

			array[1] = ercVersionedEntry;

			array[2] = getByUuid_C_Head_PrevAndNext(
				session, ercVersionedEntry, uuid, companyId, head,
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

	protected ERCVersionedEntry getByUuid_C_Head_PrevAndNext(
		Session session, ERCVersionedEntry ercVersionedEntry, String uuid,
		long companyId, boolean head,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

		sb.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

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
			sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
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

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ercVersionedEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entries where uuid = &#63; and companyId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 */
	@Override
	public void removeByUuid_C_Head(String uuid, long companyId, boolean head) {
		for (ERCVersionedEntry ercVersionedEntry :
				findByUuid_C_Head(
					uuid, companyId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ercVersionedEntry);
		}
	}

	/**
	 * Returns the number of erc versioned entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the number of matching erc versioned entries
	 */
	@Override
	public int countByUuid_C_Head(String uuid, long companyId, boolean head) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C_Head;

		Object[] finderArgs = new Object[] {uuid, companyId, head};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

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

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_C_HEAD_UUID_2 =
		"ercVersionedEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_UUID_3 =
		"(ercVersionedEntry.uuid IS NULL OR ercVersionedEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2 =
		"ercVersionedEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_HEAD_2 =
		"ercVersionedEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByERC_G;
	private FinderPath _finderPathWithoutPaginationFindByERC_G;
	private FinderPath _finderPathCountByERC_G;

	/**
	 * Returns all the erc versioned entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByERC_G(
		String externalReferenceCode, long groupId) {

		return findByERC_G(
			externalReferenceCode, groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByERC_G(
		String externalReferenceCode, long groupId, int start, int end) {

		return findByERC_G(externalReferenceCode, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByERC_G(
		String externalReferenceCode, long groupId, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return findByERC_G(
			externalReferenceCode, groupId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findByERC_G(
		String externalReferenceCode, long groupId, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
		boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByERC_G;
				finderArgs = new Object[] {externalReferenceCode, groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByERC_G;
			finderArgs = new Object[] {
				externalReferenceCode, groupId, start, end, orderByComparator
			};
		}

		List<ERCVersionedEntry> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ERCVersionedEntry ercVersionedEntry : list) {
					if (!externalReferenceCode.equals(
							ercVersionedEntry.getExternalReferenceCode()) ||
						(groupId != ercVersionedEntry.getGroupId())) {

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

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_2);
			}

			sb.append(_FINDER_COLUMN_ERC_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				queryPos.add(groupId);

				list = (List<ERCVersionedEntry>)QueryUtil.list(
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
	 * Returns the first erc versioned entry in the ordered set where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByERC_G_First(
			String externalReferenceCode, long groupId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByERC_G_First(
			externalReferenceCode, groupId, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("externalReferenceCode=");
		sb.append(externalReferenceCode);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the first erc versioned entry in the ordered set where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByERC_G_First(
		String externalReferenceCode, long groupId,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		List<ERCVersionedEntry> list = findByERC_G(
			externalReferenceCode, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByERC_G_Last(
			String externalReferenceCode, long groupId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByERC_G_Last(
			externalReferenceCode, groupId, orderByComparator);

		if (ercVersionedEntry != null) {
			return ercVersionedEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("externalReferenceCode=");
		sb.append(externalReferenceCode);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchERCVersionedEntryException(sb.toString());
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByERC_G_Last(
		String externalReferenceCode, long groupId,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		int count = countByERC_G(externalReferenceCode, groupId);

		if (count == 0) {
			return null;
		}

		List<ERCVersionedEntry> list = findByERC_G(
			externalReferenceCode, groupId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the erc versioned entries before and after the current erc versioned entry in the ordered set where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param ercVersionedEntryId the primary key of the current erc versioned entry
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry[] findByERC_G_PrevAndNext(
			long ercVersionedEntryId, String externalReferenceCode,
			long groupId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws NoSuchERCVersionedEntryException {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		ERCVersionedEntry ercVersionedEntry = findByPrimaryKey(
			ercVersionedEntryId);

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntry[] array = new ERCVersionedEntryImpl[3];

			array[0] = getByERC_G_PrevAndNext(
				session, ercVersionedEntry, externalReferenceCode, groupId,
				orderByComparator, true);

			array[1] = ercVersionedEntry;

			array[2] = getByERC_G_PrevAndNext(
				session, ercVersionedEntry, externalReferenceCode, groupId,
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

	protected ERCVersionedEntry getByERC_G_PrevAndNext(
		Session session, ERCVersionedEntry ercVersionedEntry,
		String externalReferenceCode, long groupId,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

		boolean bindExternalReferenceCode = false;

		if (externalReferenceCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_3);
		}
		else {
			bindExternalReferenceCode = true;

			sb.append(_FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_2);
		}

		sb.append(_FINDER_COLUMN_ERC_G_GROUPID_2);

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
			sb.append(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindExternalReferenceCode) {
			queryPos.add(externalReferenceCode);
		}

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ercVersionedEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ERCVersionedEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the erc versioned entries where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 */
	@Override
	public void removeByERC_G(String externalReferenceCode, long groupId) {
		for (ERCVersionedEntry ercVersionedEntry :
				findByERC_G(
					externalReferenceCode, groupId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ercVersionedEntry);
		}
	}

	/**
	 * Returns the number of erc versioned entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching erc versioned entries
	 */
	@Override
	public int countByERC_G(String externalReferenceCode, long groupId) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByERC_G;

		Object[] finderArgs = new Object[] {externalReferenceCode, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ERCVERSIONEDENTRY_WHERE);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_2);
			}

			sb.append(_FINDER_COLUMN_ERC_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
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

	private static final String _FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_2 =
		"ercVersionedEntry.externalReferenceCode = ? AND ";

	private static final String _FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_3 =
		"(ercVersionedEntry.externalReferenceCode IS NULL OR ercVersionedEntry.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_G_GROUPID_2 =
		"ercVersionedEntry.groupId = ?";

	private FinderPath _finderPathFetchByERC_G_Head;

	/**
	 * Returns the erc versioned entry where externalReferenceCode = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchERCVersionedEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByERC_G_Head(
			String externalReferenceCode, long groupId, boolean head)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByERC_G_Head(
			externalReferenceCode, groupId, head);

		if (ercVersionedEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append(", head=");
			sb.append(head);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchERCVersionedEntryException(sb.toString());
		}

		return ercVersionedEntry;
	}

	/**
	 * Returns the erc versioned entry where externalReferenceCode = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByERC_G_Head(
		String externalReferenceCode, long groupId, boolean head) {

		return fetchByERC_G_Head(externalReferenceCode, groupId, head, true);
	}

	/**
	 * Returns the erc versioned entry where externalReferenceCode = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByERC_G_Head(
		String externalReferenceCode, long groupId, boolean head,
		boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {externalReferenceCode, groupId, head};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByERC_G_Head, finderArgs, this);
		}

		if (result instanceof ERCVersionedEntry) {
			ERCVersionedEntry ercVersionedEntry = (ERCVersionedEntry)result;

			if (!Objects.equals(
					externalReferenceCode,
					ercVersionedEntry.getExternalReferenceCode()) ||
				(groupId != ercVersionedEntry.getGroupId()) ||
				(head != ercVersionedEntry.isHead())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERC_G_HEAD_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_ERC_G_HEAD_EXTERNALREFERENCECODE_2);
			}

			sb.append(_FINDER_COLUMN_ERC_G_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_ERC_G_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				queryPos.add(groupId);

				queryPos.add(head);

				List<ERCVersionedEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByERC_G_Head, finderArgs, list);
					}
				}
				else {
					ERCVersionedEntry ercVersionedEntry = list.get(0);

					result = ercVersionedEntry;

					cacheResult(ercVersionedEntry);
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
			return (ERCVersionedEntry)result;
		}
	}

	/**
	 * Removes the erc versioned entry where externalReferenceCode = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param head the head
	 * @return the erc versioned entry that was removed
	 */
	@Override
	public ERCVersionedEntry removeByERC_G_Head(
			String externalReferenceCode, long groupId, boolean head)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = findByERC_G_Head(
			externalReferenceCode, groupId, head);

		return remove(ercVersionedEntry);
	}

	/**
	 * Returns the number of erc versioned entries where externalReferenceCode = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching erc versioned entries
	 */
	@Override
	public int countByERC_G_Head(
		String externalReferenceCode, long groupId, boolean head) {

		ERCVersionedEntry ercVersionedEntry = fetchByERC_G_Head(
			externalReferenceCode, groupId, head);

		if (ercVersionedEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String
		_FINDER_COLUMN_ERC_G_HEAD_EXTERNALREFERENCECODE_2 =
			"ercVersionedEntry.externalReferenceCode = ? AND ";

	private static final String
		_FINDER_COLUMN_ERC_G_HEAD_EXTERNALREFERENCECODE_3 =
			"(ercVersionedEntry.externalReferenceCode IS NULL OR ercVersionedEntry.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_G_HEAD_GROUPID_2 =
		"ercVersionedEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_ERC_G_HEAD_HEAD_2 =
		"ercVersionedEntry.head = ?";

	private FinderPath _finderPathFetchByHeadId;

	/**
	 * Returns the erc versioned entry where headId = &#63; or throws a <code>NoSuchERCVersionedEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry findByHeadId(long headId)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByHeadId(headId);

		if (ercVersionedEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("headId=");
			sb.append(headId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchERCVersionedEntryException(sb.toString());
		}

		return ercVersionedEntry;
	}

	/**
	 * Returns the erc versioned entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByHeadId(long headId) {
		return fetchByHeadId(headId, true);
	}

	/**
	 * Returns the erc versioned entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByHeadId(
		long headId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {headId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByHeadId, finderArgs, this);
		}

		if (result instanceof ERCVersionedEntry) {
			ERCVersionedEntry ercVersionedEntry = (ERCVersionedEntry)result;

			if (headId != ercVersionedEntry.getHeadId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_ERCVERSIONEDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(headId);

				List<ERCVersionedEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByHeadId, finderArgs, list);
					}
				}
				else {
					ERCVersionedEntry ercVersionedEntry = list.get(0);

					result = ercVersionedEntry;

					cacheResult(ercVersionedEntry);
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
			return (ERCVersionedEntry)result;
		}
	}

	/**
	 * Removes the erc versioned entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the erc versioned entry that was removed
	 */
	@Override
	public ERCVersionedEntry removeByHeadId(long headId)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = findByHeadId(headId);

		return remove(ercVersionedEntry);
	}

	/**
	 * Returns the number of erc versioned entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching erc versioned entries
	 */
	@Override
	public int countByHeadId(long headId) {
		ERCVersionedEntry ercVersionedEntry = fetchByHeadId(headId);

		if (ercVersionedEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_HEADID_HEADID_2 =
		"ercVersionedEntry.headId = ?";

	public ERCVersionedEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ERCVersionedEntry.class);

		setModelImplClass(ERCVersionedEntryImpl.class);
		setModelPKClass(long.class);

		setTable(ERCVersionedEntryTable.INSTANCE);
	}

	/**
	 * Caches the erc versioned entry in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntry the erc versioned entry
	 */
	@Override
	public void cacheResult(ERCVersionedEntry ercVersionedEntry) {
		entityCache.putResult(
			ERCVersionedEntryImpl.class, ercVersionedEntry.getPrimaryKey(),
			ercVersionedEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G_Head,
			new Object[] {
				ercVersionedEntry.getUuid(), ercVersionedEntry.getGroupId(),
				ercVersionedEntry.isHead()
			},
			ercVersionedEntry);

		finderCache.putResult(
			_finderPathFetchByERC_G_Head,
			new Object[] {
				ercVersionedEntry.getExternalReferenceCode(),
				ercVersionedEntry.getGroupId(), ercVersionedEntry.isHead()
			},
			ercVersionedEntry);

		finderCache.putResult(
			_finderPathFetchByHeadId,
			new Object[] {ercVersionedEntry.getHeadId()}, ercVersionedEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the erc versioned entries in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntries the erc versioned entries
	 */
	@Override
	public void cacheResult(List<ERCVersionedEntry> ercVersionedEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ercVersionedEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ERCVersionedEntry ercVersionedEntry : ercVersionedEntries) {
			if (entityCache.getResult(
					ERCVersionedEntryImpl.class,
					ercVersionedEntry.getPrimaryKey()) == null) {

				cacheResult(ercVersionedEntry);
			}
		}
	}

	/**
	 * Clears the cache for all erc versioned entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ERCVersionedEntryImpl.class);

		finderCache.clearCache(ERCVersionedEntryImpl.class);
	}

	/**
	 * Clears the cache for the erc versioned entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ERCVersionedEntry ercVersionedEntry) {
		entityCache.removeResult(
			ERCVersionedEntryImpl.class, ercVersionedEntry);
	}

	@Override
	public void clearCache(List<ERCVersionedEntry> ercVersionedEntries) {
		for (ERCVersionedEntry ercVersionedEntry : ercVersionedEntries) {
			entityCache.removeResult(
				ERCVersionedEntryImpl.class, ercVersionedEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ERCVersionedEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ERCVersionedEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ERCVersionedEntryModelImpl ercVersionedEntryModelImpl) {

		Object[] args = new Object[] {
			ercVersionedEntryModelImpl.getUuid(),
			ercVersionedEntryModelImpl.getGroupId(),
			ercVersionedEntryModelImpl.isHead()
		};

		finderCache.putResult(
			_finderPathFetchByUUID_G_Head, args, ercVersionedEntryModelImpl);

		args = new Object[] {
			ercVersionedEntryModelImpl.getExternalReferenceCode(),
			ercVersionedEntryModelImpl.getGroupId(),
			ercVersionedEntryModelImpl.isHead()
		};

		finderCache.putResult(
			_finderPathFetchByERC_G_Head, args, ercVersionedEntryModelImpl);

		args = new Object[] {ercVersionedEntryModelImpl.getHeadId()};

		finderCache.putResult(
			_finderPathFetchByHeadId, args, ercVersionedEntryModelImpl);
	}

	/**
	 * Creates a new erc versioned entry with the primary key. Does not add the erc versioned entry to the database.
	 *
	 * @param ercVersionedEntryId the primary key for the new erc versioned entry
	 * @return the new erc versioned entry
	 */
	@Override
	public ERCVersionedEntry create(long ercVersionedEntryId) {
		ERCVersionedEntry ercVersionedEntry = new ERCVersionedEntryImpl();

		ercVersionedEntry.setNew(true);
		ercVersionedEntry.setPrimaryKey(ercVersionedEntryId);

		String uuid = PortalUUIDUtil.generate();

		ercVersionedEntry.setUuid(uuid);

		ercVersionedEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ercVersionedEntry;
	}

	/**
	 * Removes the erc versioned entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry that was removed
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry remove(long ercVersionedEntryId)
		throws NoSuchERCVersionedEntryException {

		return remove((Serializable)ercVersionedEntryId);
	}

	/**
	 * Removes the erc versioned entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the erc versioned entry
	 * @return the erc versioned entry that was removed
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry remove(Serializable primaryKey)
		throws NoSuchERCVersionedEntryException {

		Session session = null;

		try {
			session = openSession();

			ERCVersionedEntry ercVersionedEntry =
				(ERCVersionedEntry)session.get(
					ERCVersionedEntryImpl.class, primaryKey);

			if (ercVersionedEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchERCVersionedEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ercVersionedEntry);
		}
		catch (NoSuchERCVersionedEntryException noSuchEntityException) {
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
	protected ERCVersionedEntry removeImpl(
		ERCVersionedEntry ercVersionedEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ercVersionedEntry)) {
				ercVersionedEntry = (ERCVersionedEntry)session.get(
					ERCVersionedEntryImpl.class,
					ercVersionedEntry.getPrimaryKeyObj());
			}

			if (ercVersionedEntry != null) {
				session.delete(ercVersionedEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ercVersionedEntry != null) {
			clearCache(ercVersionedEntry);
		}

		return ercVersionedEntry;
	}

	@Override
	public ERCVersionedEntry updateImpl(ERCVersionedEntry ercVersionedEntry) {
		boolean isNew = ercVersionedEntry.isNew();

		if (!(ercVersionedEntry instanceof ERCVersionedEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ercVersionedEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ercVersionedEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ercVersionedEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ERCVersionedEntry implementation " +
					ercVersionedEntry.getClass());
		}

		ERCVersionedEntryModelImpl ercVersionedEntryModelImpl =
			(ERCVersionedEntryModelImpl)ercVersionedEntry;

		if (Validator.isNull(ercVersionedEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ercVersionedEntry.setUuid(uuid);
		}

		if (Validator.isNull(ercVersionedEntry.getExternalReferenceCode())) {
			ercVersionedEntry.setExternalReferenceCode(
				ercVersionedEntry.getUuid());
		}
		else {
			if (!Objects.equals(
					ercVersionedEntryModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					ercVersionedEntry.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = ercVersionedEntry.getCompanyId();

					long groupId = ercVersionedEntry.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = ercVersionedEntry.getPrimaryKey();
					}

					try {
						ercVersionedEntry.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								ERCVersionedEntry.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								ercVersionedEntry.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			ERCVersionedEntry ercERCVersionedEntry = fetchByERC_G_Head(
				ercVersionedEntry.getExternalReferenceCode(),
				ercVersionedEntry.getGroupId(), ercVersionedEntry.isHead());

			if (isNew) {
				if (ercERCVersionedEntry != null) {
					throw new DuplicateERCVersionedEntryExternalReferenceCodeException(
						"Duplicate erc versioned entry with external reference code " +
							ercVersionedEntry.getExternalReferenceCode() +
								" and group " + ercVersionedEntry.getGroupId());
				}
			}
			else {
				if ((ercERCVersionedEntry != null) &&
					(ercVersionedEntry.getErcVersionedEntryId() !=
						ercERCVersionedEntry.getErcVersionedEntryId())) {

					throw new DuplicateERCVersionedEntryExternalReferenceCodeException(
						"Duplicate erc versioned entry with external reference code " +
							ercVersionedEntry.getExternalReferenceCode() +
								" and group " + ercVersionedEntry.getGroupId());
				}
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ercVersionedEntry);
			}
			else {
				ercVersionedEntry = (ERCVersionedEntry)session.merge(
					ercVersionedEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ERCVersionedEntryImpl.class, ercVersionedEntryModelImpl, false,
			true);

		cacheUniqueFindersCache(ercVersionedEntryModelImpl);

		if (isNew) {
			ercVersionedEntry.setNew(false);
		}

		ercVersionedEntry.resetOriginalValues();

		return ercVersionedEntry;
	}

	/**
	 * Returns the erc versioned entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the erc versioned entry
	 * @return the erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchERCVersionedEntryException {

		ERCVersionedEntry ercVersionedEntry = fetchByPrimaryKey(primaryKey);

		if (ercVersionedEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchERCVersionedEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ercVersionedEntry;
	}

	/**
	 * Returns the erc versioned entry with the primary key or throws a <code>NoSuchERCVersionedEntryException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry findByPrimaryKey(long ercVersionedEntryId)
		throws NoSuchERCVersionedEntryException {

		return findByPrimaryKey((Serializable)ercVersionedEntryId);
	}

	/**
	 * Returns the erc versioned entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry, or <code>null</code> if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntry fetchByPrimaryKey(long ercVersionedEntryId) {
		return fetchByPrimaryKey((Serializable)ercVersionedEntryId);
	}

	/**
	 * Returns all the erc versioned entries.
	 *
	 * @return the erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findAll(
		int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc versioned entries
	 */
	@Override
	public List<ERCVersionedEntry> findAll(
		int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
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

		List<ERCVersionedEntry> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ERCVERSIONEDENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ERCVERSIONEDENTRY;

				sql = sql.concat(ERCVersionedEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ERCVersionedEntry>)QueryUtil.list(
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
	 * Removes all the erc versioned entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ERCVersionedEntry ercVersionedEntry : findAll()) {
			remove(ercVersionedEntry);
		}
	}

	/**
	 * Returns the number of erc versioned entries.
	 *
	 * @return the number of erc versioned entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ERCVERSIONEDENTRY);

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
		return "ercVersionedEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ERCVERSIONEDENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ERCVersionedEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the erc versioned entry persistence.
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

		_finderPathWithPaginationFindByUuid_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_Head",
			new String[] {
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "head"}, true);

		_finderPathWithoutPaginationFindByUuid_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_Head",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"uuid_", "head"}, true);

		_finderPathCountByUuid_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_Head",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"uuid_", "head"}, false);

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

		_finderPathFetchByUUID_G_Head = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"uuid_", "groupId", "head"}, true);

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

		_finderPathWithPaginationFindByUuid_C_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId", "head"}, true);

		_finderPathWithoutPaginationFindByUuid_C_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"uuid_", "companyId", "head"}, true);

		_finderPathCountByUuid_C_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"uuid_", "companyId", "head"}, false);

		_finderPathWithPaginationFindByERC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByERC_G",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"externalReferenceCode", "groupId"}, true);

		_finderPathWithoutPaginationFindByERC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByERC_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "groupId"}, true);

		_finderPathCountByERC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByERC_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "groupId"}, false);

		_finderPathFetchByERC_G_Head = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_G_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"externalReferenceCode", "groupId", "head"}, true);

		_finderPathFetchByHeadId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByHeadId",
			new String[] {Long.class.getName()}, new String[] {"headId"}, true);

		ERCVersionedEntryUtil.setPersistence(this);
	}

	public void destroy() {
		ERCVersionedEntryUtil.setPersistence(null);

		entityCache.removeCache(ERCVersionedEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ERCVERSIONEDENTRY =
		"SELECT ercVersionedEntry FROM ERCVersionedEntry ercVersionedEntry";

	private static final String _SQL_SELECT_ERCVERSIONEDENTRY_WHERE =
		"SELECT ercVersionedEntry FROM ERCVersionedEntry ercVersionedEntry WHERE ";

	private static final String _SQL_COUNT_ERCVERSIONEDENTRY =
		"SELECT COUNT(ercVersionedEntry) FROM ERCVersionedEntry ercVersionedEntry";

	private static final String _SQL_COUNT_ERCVERSIONEDENTRY_WHERE =
		"SELECT COUNT(ercVersionedEntry) FROM ERCVersionedEntry ercVersionedEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ercVersionedEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ERCVersionedEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ERCVersionedEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ERCVersionedEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}