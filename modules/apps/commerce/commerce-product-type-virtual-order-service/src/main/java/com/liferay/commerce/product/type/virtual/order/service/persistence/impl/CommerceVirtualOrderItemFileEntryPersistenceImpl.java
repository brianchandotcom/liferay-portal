/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service.persistence.impl;

import com.liferay.commerce.product.type.virtual.order.exception.NoSuchVirtualOrderItemFileEntryException;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemFileEntry;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemFileEntryTable;
import com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemFileEntryImpl;
import com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemFileEntryModelImpl;
import com.liferay.commerce.product.type.virtual.order.service.persistence.CommerceVirtualOrderItemFileEntryPersistence;
import com.liferay.commerce.product.type.virtual.order.service.persistence.CommerceVirtualOrderItemFileEntryUtil;
import com.liferay.commerce.product.type.virtual.order.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
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
 * The persistence implementation for the commerce virtual order item file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = CommerceVirtualOrderItemFileEntryPersistence.class)
public class CommerceVirtualOrderItemFileEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceVirtualOrderItemFileEntry>
	implements CommerceVirtualOrderItemFileEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceVirtualOrderItemFileEntryUtil</code> to access the commerce virtual order item file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceVirtualOrderItemFileEntryImpl.class.getName();

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
	 * Returns all the commerce virtual order item file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce virtual order item file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @return the range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
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

		List<CommerceVirtualOrderItemFileEntry> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceVirtualOrderItemFileEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceVirtualOrderItemFileEntry
						commerceVirtualOrderItemFileEntry : list) {

					if (!uuid.equals(
							commerceVirtualOrderItemFileEntry.getUuid())) {

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

			sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

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
				sb.append(
					CommerceVirtualOrderItemFileEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceVirtualOrderItemFileEntry>)QueryUtil.list(
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
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry findByUuid_First(
			String uuid,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByUuid_First(uuid, orderByComparator);

		if (commerceVirtualOrderItemFileEntry != null) {
			return commerceVirtualOrderItemFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchVirtualOrderItemFileEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		List<CommerceVirtualOrderItemFileEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commerceVirtualOrderItemFileEntry != null) {
			return commerceVirtualOrderItemFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchVirtualOrderItemFileEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceVirtualOrderItemFileEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce virtual order item file entries before and after the current commerce virtual order item file entry in the ordered set where uuid = &#63;.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the current commerce virtual order item file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry[] findByUuid_PrevAndNext(
			long commerceVirtualOrderItemFileEntryId, String uuid,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		uuid = Objects.toString(uuid, "");

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			findByPrimaryKey(commerceVirtualOrderItemFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceVirtualOrderItemFileEntry[] array =
				new CommerceVirtualOrderItemFileEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceVirtualOrderItemFileEntry, uuid,
				orderByComparator, true);

			array[1] = commerceVirtualOrderItemFileEntry;

			array[2] = getByUuid_PrevAndNext(
				session, commerceVirtualOrderItemFileEntry, uuid,
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

	protected CommerceVirtualOrderItemFileEntry getByUuid_PrevAndNext(
		Session session,
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry,
		String uuid,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

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
			sb.append(CommerceVirtualOrderItemFileEntryModelImpl.ORDER_BY_JPQL);
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
						commerceVirtualOrderItemFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceVirtualOrderItemFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce virtual order item file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceVirtualOrderItemFileEntry
				commerceVirtualOrderItemFileEntry :
					findByUuid(
						uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceVirtualOrderItemFileEntry);
		}
	}

	/**
	 * Returns the number of commerce virtual order item file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce virtual order item file entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

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
		"commerceVirtualOrderItemFileEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceVirtualOrderItemFileEntry.uuid IS NULL OR commerceVirtualOrderItemFileEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;

	/**
	 * Returns the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVirtualOrderItemFileEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry findByUUID_G(
			String uuid, long groupId)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByUUID_G(uuid, groupId);

		if (commerceVirtualOrderItemFileEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchVirtualOrderItemFileEntryException(sb.toString());
		}

		return commerceVirtualOrderItemFileEntry;
	}

	/**
	 * Returns the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof CommerceVirtualOrderItemFileEntry) {
			CommerceVirtualOrderItemFileEntry
				commerceVirtualOrderItemFileEntry =
					(CommerceVirtualOrderItemFileEntry)result;

			if (!Objects.equals(
					uuid, commerceVirtualOrderItemFileEntry.getUuid()) ||
				(groupId != commerceVirtualOrderItemFileEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

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

				List<CommerceVirtualOrderItemFileEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CommerceVirtualOrderItemFileEntry
						commerceVirtualOrderItemFileEntry = list.get(0);

					result = commerceVirtualOrderItemFileEntry;

					cacheResult(commerceVirtualOrderItemFileEntry);
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
			return (CommerceVirtualOrderItemFileEntry)result;
		}
	}

	/**
	 * Removes the commerce virtual order item file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce virtual order item file entry that was removed
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			findByUUID_G(uuid, groupId);

		return remove(commerceVirtualOrderItemFileEntry);
	}

	/**
	 * Returns the number of commerce virtual order item file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByUUID_G(uuid, groupId);

		if (commerceVirtualOrderItemFileEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"commerceVirtualOrderItemFileEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(commerceVirtualOrderItemFileEntry.uuid IS NULL OR commerceVirtualOrderItemFileEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"commerceVirtualOrderItemFileEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @return the range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
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

		List<CommerceVirtualOrderItemFileEntry> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceVirtualOrderItemFileEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceVirtualOrderItemFileEntry
						commerceVirtualOrderItemFileEntry : list) {

					if (!uuid.equals(
							commerceVirtualOrderItemFileEntry.getUuid()) ||
						(companyId !=
							commerceVirtualOrderItemFileEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

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
				sb.append(
					CommerceVirtualOrderItemFileEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceVirtualOrderItemFileEntry>)QueryUtil.list(
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
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commerceVirtualOrderItemFileEntry != null) {
			return commerceVirtualOrderItemFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchVirtualOrderItemFileEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		List<CommerceVirtualOrderItemFileEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commerceVirtualOrderItemFileEntry != null) {
			return commerceVirtualOrderItemFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchVirtualOrderItemFileEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceVirtualOrderItemFileEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce virtual order item file entries before and after the current commerce virtual order item file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the current commerce virtual order item file entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry[] findByUuid_C_PrevAndNext(
			long commerceVirtualOrderItemFileEntryId, String uuid,
			long companyId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		uuid = Objects.toString(uuid, "");

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			findByPrimaryKey(commerceVirtualOrderItemFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceVirtualOrderItemFileEntry[] array =
				new CommerceVirtualOrderItemFileEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceVirtualOrderItemFileEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = commerceVirtualOrderItemFileEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceVirtualOrderItemFileEntry, uuid, companyId,
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

	protected CommerceVirtualOrderItemFileEntry getByUuid_C_PrevAndNext(
		Session session,
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry,
		String uuid, long companyId,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

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
			sb.append(CommerceVirtualOrderItemFileEntryModelImpl.ORDER_BY_JPQL);
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
						commerceVirtualOrderItemFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceVirtualOrderItemFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce virtual order item file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceVirtualOrderItemFileEntry
				commerceVirtualOrderItemFileEntry :
					findByUuid_C(
						uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null)) {

			remove(commerceVirtualOrderItemFileEntry);
		}
	}

	/**
	 * Returns the number of commerce virtual order item file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

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
		"commerceVirtualOrderItemFileEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceVirtualOrderItemFileEntry.uuid IS NULL OR commerceVirtualOrderItemFileEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceVirtualOrderItemFileEntry.companyId = ?";

	private FinderPath
		_finderPathWithPaginationFindByCommerceVirtualOrderItemId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceVirtualOrderItemId;
	private FinderPath _finderPathCountByCommerceVirtualOrderItemId;

	/**
	 * Returns all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @return the matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(long commerceVirtualOrderItemId) {

		return findByCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @return the range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(
			long commerceVirtualOrderItemId, int start, int end) {

		return findByCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(
			long commerceVirtualOrderItemId, int start, int end,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator) {

		return findByCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry>
		findByCommerceVirtualOrderItemId(
			long commerceVirtualOrderItemId, int start, int end,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceVirtualOrderItemId;
				finderArgs = new Object[] {commerceVirtualOrderItemId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceVirtualOrderItemId;
			finderArgs = new Object[] {
				commerceVirtualOrderItemId, start, end, orderByComparator
			};
		}

		List<CommerceVirtualOrderItemFileEntry> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceVirtualOrderItemFileEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceVirtualOrderItemFileEntry
						commerceVirtualOrderItemFileEntry : list) {

					if (commerceVirtualOrderItemId !=
							commerceVirtualOrderItemFileEntry.
								getCommerceVirtualOrderItemId()) {

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

			sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEVIRTUALORDERITEMID_COMMERCEVIRTUALORDERITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceVirtualOrderItemFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceVirtualOrderItemId);

				list = (List<CommerceVirtualOrderItemFileEntry>)QueryUtil.list(
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
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry
			findByCommerceVirtualOrderItemId_First(
				long commerceVirtualOrderItemId,
				OrderByComparator<CommerceVirtualOrderItemFileEntry>
					orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByCommerceVirtualOrderItemId_First(
				commerceVirtualOrderItemId, orderByComparator);

		if (commerceVirtualOrderItemFileEntry != null) {
			return commerceVirtualOrderItemFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceVirtualOrderItemId=");
		sb.append(commerceVirtualOrderItemId);

		sb.append("}");

		throw new NoSuchVirtualOrderItemFileEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry
		fetchByCommerceVirtualOrderItemId_First(
			long commerceVirtualOrderItemId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator) {

		List<CommerceVirtualOrderItemFileEntry> list =
			findByCommerceVirtualOrderItemId(
				commerceVirtualOrderItemId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry
			findByCommerceVirtualOrderItemId_Last(
				long commerceVirtualOrderItemId,
				OrderByComparator<CommerceVirtualOrderItemFileEntry>
					orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByCommerceVirtualOrderItemId_Last(
				commerceVirtualOrderItemId, orderByComparator);

		if (commerceVirtualOrderItemFileEntry != null) {
			return commerceVirtualOrderItemFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceVirtualOrderItemId=");
		sb.append(commerceVirtualOrderItemId);

		sb.append("}");

		throw new NoSuchVirtualOrderItemFileEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry
		fetchByCommerceVirtualOrderItemId_Last(
			long commerceVirtualOrderItemId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator) {

		int count = countByCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId);

		if (count == 0) {
			return null;
		}

		List<CommerceVirtualOrderItemFileEntry> list =
			findByCommerceVirtualOrderItemId(
				commerceVirtualOrderItemId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce virtual order item file entries before and after the current commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the current commerce virtual order item file entry
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry[]
			findByCommerceVirtualOrderItemId_PrevAndNext(
				long commerceVirtualOrderItemFileEntryId,
				long commerceVirtualOrderItemId,
				OrderByComparator<CommerceVirtualOrderItemFileEntry>
					orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			findByPrimaryKey(commerceVirtualOrderItemFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceVirtualOrderItemFileEntry[] array =
				new CommerceVirtualOrderItemFileEntryImpl[3];

			array[0] = getByCommerceVirtualOrderItemId_PrevAndNext(
				session, commerceVirtualOrderItemFileEntry,
				commerceVirtualOrderItemId, orderByComparator, true);

			array[1] = commerceVirtualOrderItemFileEntry;

			array[2] = getByCommerceVirtualOrderItemId_PrevAndNext(
				session, commerceVirtualOrderItemFileEntry,
				commerceVirtualOrderItemId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceVirtualOrderItemFileEntry
		getByCommerceVirtualOrderItemId_PrevAndNext(
			Session session,
			CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry,
			long commerceVirtualOrderItemId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEVIRTUALORDERITEMID_COMMERCEVIRTUALORDERITEMID_2);

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
			sb.append(CommerceVirtualOrderItemFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceVirtualOrderItemId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceVirtualOrderItemFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceVirtualOrderItemFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; from the database.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 */
	@Override
	public void removeByCommerceVirtualOrderItemId(
		long commerceVirtualOrderItemId) {

		for (CommerceVirtualOrderItemFileEntry
				commerceVirtualOrderItemFileEntry :
					findByCommerceVirtualOrderItemId(
						commerceVirtualOrderItemId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceVirtualOrderItemFileEntry);
		}
	}

	/**
	 * Returns the number of commerce virtual order item file entries where commerceVirtualOrderItemId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	@Override
	public int countByCommerceVirtualOrderItemId(
		long commerceVirtualOrderItemId) {

		FinderPath finderPath = _finderPathCountByCommerceVirtualOrderItemId;

		Object[] finderArgs = new Object[] {commerceVirtualOrderItemId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEVIRTUALORDERITEMID_COMMERCEVIRTUALORDERITEMID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceVirtualOrderItemId);

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
		_FINDER_COLUMN_COMMERCEVIRTUALORDERITEMID_COMMERCEVIRTUALORDERITEMID_2 =
			"commerceVirtualOrderItemFileEntry.commerceVirtualOrderItemId = ?";

	private FinderPath _finderPathWithPaginationFindByC_F;
	private FinderPath _finderPathWithoutPaginationFindByC_F;
	private FinderPath _finderPathCountByC_F;

	/**
	 * Returns all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @return the matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId) {

		return findByC_F(
			commerceVirtualOrderItemId, fileEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @return the range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId, int start, int end) {

		return findByC_F(
			commerceVirtualOrderItemId, fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return findByC_F(
			commerceVirtualOrderItemId, fileEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findByC_F(
		long commerceVirtualOrderItemId, long fileEntryId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_F;
				finderArgs = new Object[] {
					commerceVirtualOrderItemId, fileEntryId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_F;
			finderArgs = new Object[] {
				commerceVirtualOrderItemId, fileEntryId, start, end,
				orderByComparator
			};
		}

		List<CommerceVirtualOrderItemFileEntry> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceVirtualOrderItemFileEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceVirtualOrderItemFileEntry
						commerceVirtualOrderItemFileEntry : list) {

					if ((commerceVirtualOrderItemId !=
							commerceVirtualOrderItemFileEntry.
								getCommerceVirtualOrderItemId()) ||
						(fileEntryId !=
							commerceVirtualOrderItemFileEntry.
								getFileEntryId())) {

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

			sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_F_COMMERCEVIRTUALORDERITEMID_2);

			sb.append(_FINDER_COLUMN_C_F_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceVirtualOrderItemFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceVirtualOrderItemId);

				queryPos.add(fileEntryId);

				list = (List<CommerceVirtualOrderItemFileEntry>)QueryUtil.list(
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
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry findByC_F_First(
			long commerceVirtualOrderItemId, long fileEntryId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByC_F_First(
				commerceVirtualOrderItemId, fileEntryId, orderByComparator);

		if (commerceVirtualOrderItemFileEntry != null) {
			return commerceVirtualOrderItemFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceVirtualOrderItemId=");
		sb.append(commerceVirtualOrderItemId);

		sb.append(", fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchVirtualOrderItemFileEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry fetchByC_F_First(
		long commerceVirtualOrderItemId, long fileEntryId,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		List<CommerceVirtualOrderItemFileEntry> list = findByC_F(
			commerceVirtualOrderItemId, fileEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry findByC_F_Last(
			long commerceVirtualOrderItemId, long fileEntryId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByC_F_Last(
				commerceVirtualOrderItemId, fileEntryId, orderByComparator);

		if (commerceVirtualOrderItemFileEntry != null) {
			return commerceVirtualOrderItemFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceVirtualOrderItemId=");
		sb.append(commerceVirtualOrderItemId);

		sb.append(", fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchVirtualOrderItemFileEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item file entry, or <code>null</code> if a matching commerce virtual order item file entry could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry fetchByC_F_Last(
		long commerceVirtualOrderItemId, long fileEntryId,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		int count = countByC_F(commerceVirtualOrderItemId, fileEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceVirtualOrderItemFileEntry> list = findByC_F(
			commerceVirtualOrderItemId, fileEntryId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce virtual order item file entries before and after the current commerce virtual order item file entry in the ordered set where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the current commerce virtual order item file entry
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry[] findByC_F_PrevAndNext(
			long commerceVirtualOrderItemFileEntryId,
			long commerceVirtualOrderItemId, long fileEntryId,
			OrderByComparator<CommerceVirtualOrderItemFileEntry>
				orderByComparator)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			findByPrimaryKey(commerceVirtualOrderItemFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceVirtualOrderItemFileEntry[] array =
				new CommerceVirtualOrderItemFileEntryImpl[3];

			array[0] = getByC_F_PrevAndNext(
				session, commerceVirtualOrderItemFileEntry,
				commerceVirtualOrderItemId, fileEntryId, orderByComparator,
				true);

			array[1] = commerceVirtualOrderItemFileEntry;

			array[2] = getByC_F_PrevAndNext(
				session, commerceVirtualOrderItemFileEntry,
				commerceVirtualOrderItemId, fileEntryId, orderByComparator,
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

	protected CommerceVirtualOrderItemFileEntry getByC_F_PrevAndNext(
		Session session,
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry,
		long commerceVirtualOrderItemId, long fileEntryId,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_F_COMMERCEVIRTUALORDERITEMID_2);

		sb.append(_FINDER_COLUMN_C_F_FILEENTRYID_2);

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
			sb.append(CommerceVirtualOrderItemFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceVirtualOrderItemId);

		queryPos.add(fileEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceVirtualOrderItemFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceVirtualOrderItemFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByC_F(long commerceVirtualOrderItemId, long fileEntryId) {
		for (CommerceVirtualOrderItemFileEntry
				commerceVirtualOrderItemFileEntry :
					findByC_F(
						commerceVirtualOrderItemId, fileEntryId,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceVirtualOrderItemFileEntry);
		}
	}

	/**
	 * Returns the number of commerce virtual order item file entries where commerceVirtualOrderItemId = &#63; and fileEntryId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the commerce virtual order item ID
	 * @param fileEntryId the file entry ID
	 * @return the number of matching commerce virtual order item file entries
	 */
	@Override
	public int countByC_F(long commerceVirtualOrderItemId, long fileEntryId) {
		FinderPath finderPath = _finderPathCountByC_F;

		Object[] finderArgs = new Object[] {
			commerceVirtualOrderItemId, fileEntryId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_F_COMMERCEVIRTUALORDERITEMID_2);

			sb.append(_FINDER_COLUMN_C_F_FILEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceVirtualOrderItemId);

				queryPos.add(fileEntryId);

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
		_FINDER_COLUMN_C_F_COMMERCEVIRTUALORDERITEMID_2 =
			"commerceVirtualOrderItemFileEntry.commerceVirtualOrderItemId = ? AND ";

	private static final String _FINDER_COLUMN_C_F_FILEENTRYID_2 =
		"commerceVirtualOrderItemFileEntry.fileEntryId = ?";

	public CommerceVirtualOrderItemFileEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"commerceVirtualOrderItemFileEntryId",
			"cVirtualOrderItemFileEntryId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceVirtualOrderItemFileEntry.class);

		setModelImplClass(CommerceVirtualOrderItemFileEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceVirtualOrderItemFileEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce virtual order item file entry in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItemFileEntry the commerce virtual order item file entry
	 */
	@Override
	public void cacheResult(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		entityCache.putResult(
			CommerceVirtualOrderItemFileEntryImpl.class,
			commerceVirtualOrderItemFileEntry.getPrimaryKey(),
			commerceVirtualOrderItemFileEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				commerceVirtualOrderItemFileEntry.getUuid(),
				commerceVirtualOrderItemFileEntry.getGroupId()
			},
			commerceVirtualOrderItemFileEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce virtual order item file entries in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItemFileEntries the commerce virtual order item file entries
	 */
	@Override
	public void cacheResult(
		List<CommerceVirtualOrderItemFileEntry>
			commerceVirtualOrderItemFileEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceVirtualOrderItemFileEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceVirtualOrderItemFileEntry
				commerceVirtualOrderItemFileEntry :
					commerceVirtualOrderItemFileEntries) {

			if (entityCache.getResult(
					CommerceVirtualOrderItemFileEntryImpl.class,
					commerceVirtualOrderItemFileEntry.getPrimaryKey()) ==
						null) {

				cacheResult(commerceVirtualOrderItemFileEntry);
			}
		}
	}

	/**
	 * Clears the cache for all commerce virtual order item file entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceVirtualOrderItemFileEntryImpl.class);

		finderCache.clearCache(CommerceVirtualOrderItemFileEntryImpl.class);
	}

	/**
	 * Clears the cache for the commerce virtual order item file entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		entityCache.removeResult(
			CommerceVirtualOrderItemFileEntryImpl.class,
			commerceVirtualOrderItemFileEntry);
	}

	@Override
	public void clearCache(
		List<CommerceVirtualOrderItemFileEntry>
			commerceVirtualOrderItemFileEntries) {

		for (CommerceVirtualOrderItemFileEntry
				commerceVirtualOrderItemFileEntry :
					commerceVirtualOrderItemFileEntries) {

			entityCache.removeResult(
				CommerceVirtualOrderItemFileEntryImpl.class,
				commerceVirtualOrderItemFileEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceVirtualOrderItemFileEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceVirtualOrderItemFileEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceVirtualOrderItemFileEntryModelImpl
			commerceVirtualOrderItemFileEntryModelImpl) {

		Object[] args = new Object[] {
			commerceVirtualOrderItemFileEntryModelImpl.getUuid(),
			commerceVirtualOrderItemFileEntryModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			commerceVirtualOrderItemFileEntryModelImpl);
	}

	/**
	 * Creates a new commerce virtual order item file entry with the primary key. Does not add the commerce virtual order item file entry to the database.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key for the new commerce virtual order item file entry
	 * @return the new commerce virtual order item file entry
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry create(
		long commerceVirtualOrderItemFileEntryId) {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			new CommerceVirtualOrderItemFileEntryImpl();

		commerceVirtualOrderItemFileEntry.setNew(true);
		commerceVirtualOrderItemFileEntry.setPrimaryKey(
			commerceVirtualOrderItemFileEntryId);

		String uuid = PortalUUIDUtil.generate();

		commerceVirtualOrderItemFileEntry.setUuid(uuid);

		commerceVirtualOrderItemFileEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceVirtualOrderItemFileEntry;
	}

	/**
	 * Removes the commerce virtual order item file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry that was removed
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry remove(
			long commerceVirtualOrderItemFileEntryId)
		throws NoSuchVirtualOrderItemFileEntryException {

		return remove((Serializable)commerceVirtualOrderItemFileEntryId);
	}

	/**
	 * Removes the commerce virtual order item file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry that was removed
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry remove(Serializable primaryKey)
		throws NoSuchVirtualOrderItemFileEntryException {

		Session session = null;

		try {
			session = openSession();

			CommerceVirtualOrderItemFileEntry
				commerceVirtualOrderItemFileEntry =
					(CommerceVirtualOrderItemFileEntry)session.get(
						CommerceVirtualOrderItemFileEntryImpl.class,
						primaryKey);

			if (commerceVirtualOrderItemFileEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVirtualOrderItemFileEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceVirtualOrderItemFileEntry);
		}
		catch (NoSuchVirtualOrderItemFileEntryException noSuchEntityException) {
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
	protected CommerceVirtualOrderItemFileEntry removeImpl(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceVirtualOrderItemFileEntry)) {
				commerceVirtualOrderItemFileEntry =
					(CommerceVirtualOrderItemFileEntry)session.get(
						CommerceVirtualOrderItemFileEntryImpl.class,
						commerceVirtualOrderItemFileEntry.getPrimaryKeyObj());
			}

			if (commerceVirtualOrderItemFileEntry != null) {
				session.delete(commerceVirtualOrderItemFileEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceVirtualOrderItemFileEntry != null) {
			clearCache(commerceVirtualOrderItemFileEntry);
		}

		return commerceVirtualOrderItemFileEntry;
	}

	@Override
	public CommerceVirtualOrderItemFileEntry updateImpl(
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		boolean isNew = commerceVirtualOrderItemFileEntry.isNew();

		if (!(commerceVirtualOrderItemFileEntry instanceof
				CommerceVirtualOrderItemFileEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceVirtualOrderItemFileEntry.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceVirtualOrderItemFileEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceVirtualOrderItemFileEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceVirtualOrderItemFileEntry implementation " +
					commerceVirtualOrderItemFileEntry.getClass());
		}

		CommerceVirtualOrderItemFileEntryModelImpl
			commerceVirtualOrderItemFileEntryModelImpl =
				(CommerceVirtualOrderItemFileEntryModelImpl)
					commerceVirtualOrderItemFileEntry;

		if (Validator.isNull(commerceVirtualOrderItemFileEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceVirtualOrderItemFileEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(commerceVirtualOrderItemFileEntry.getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceVirtualOrderItemFileEntry.setCreateDate(date);
			}
			else {
				commerceVirtualOrderItemFileEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceVirtualOrderItemFileEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceVirtualOrderItemFileEntry.setModifiedDate(date);
			}
			else {
				commerceVirtualOrderItemFileEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceVirtualOrderItemFileEntry);
			}
			else {
				commerceVirtualOrderItemFileEntry =
					(CommerceVirtualOrderItemFileEntry)session.merge(
						commerceVirtualOrderItemFileEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceVirtualOrderItemFileEntryImpl.class,
			commerceVirtualOrderItemFileEntryModelImpl, false, true);

		cacheUniqueFindersCache(commerceVirtualOrderItemFileEntryModelImpl);

		if (isNew) {
			commerceVirtualOrderItemFileEntry.setNew(false);
		}

		commerceVirtualOrderItemFileEntry.resetOriginalValues();

		return commerceVirtualOrderItemFileEntry;
	}

	/**
	 * Returns the commerce virtual order item file entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchVirtualOrderItemFileEntryException {

		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry =
			fetchByPrimaryKey(primaryKey);

		if (commerceVirtualOrderItemFileEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVirtualOrderItemFileEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceVirtualOrderItemFileEntry;
	}

	/**
	 * Returns the commerce virtual order item file entry with the primary key or throws a <code>NoSuchVirtualOrderItemFileEntryException</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry
	 * @throws NoSuchVirtualOrderItemFileEntryException if a commerce virtual order item file entry with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry findByPrimaryKey(
			long commerceVirtualOrderItemFileEntryId)
		throws NoSuchVirtualOrderItemFileEntryException {

		return findByPrimaryKey(
			(Serializable)commerceVirtualOrderItemFileEntryId);
	}

	/**
	 * Returns the commerce virtual order item file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemFileEntryId the primary key of the commerce virtual order item file entry
	 * @return the commerce virtual order item file entry, or <code>null</code> if a commerce virtual order item file entry with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItemFileEntry fetchByPrimaryKey(
		long commerceVirtualOrderItemFileEntryId) {

		return fetchByPrimaryKey(
			(Serializable)commerceVirtualOrderItemFileEntryId);
	}

	/**
	 * Returns all the commerce virtual order item file entries.
	 *
	 * @return the commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce virtual order item file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @return the range of commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order item file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order item file entries
	 * @param end the upper bound of the range of commerce virtual order item file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce virtual order item file entries
	 */
	@Override
	public List<CommerceVirtualOrderItemFileEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceVirtualOrderItemFileEntry> orderByComparator,
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

		List<CommerceVirtualOrderItemFileEntry> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceVirtualOrderItemFileEntry>)finderCache.getResult(
					finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY;

				sql = sql.concat(
					CommerceVirtualOrderItemFileEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceVirtualOrderItemFileEntry>)QueryUtil.list(
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
	 * Removes all the commerce virtual order item file entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceVirtualOrderItemFileEntry
				commerceVirtualOrderItemFileEntry : findAll()) {

			remove(commerceVirtualOrderItemFileEntry);
		}
	}

	/**
	 * Returns the number of commerce virtual order item file entries.
	 *
	 * @return the number of commerce virtual order item file entries
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
					_SQL_COUNT_COMMERCEVIRTUALORDERITEMFILEENTRY);

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
		return "cVirtualOrderItemFileEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceVirtualOrderItemFileEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce virtual order item file entry persistence.
	 */
	@Activate
	public void activate() {
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

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

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

		_finderPathWithPaginationFindByCommerceVirtualOrderItemId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceVirtualOrderItemId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceVirtualOrderItemId"}, true);

		_finderPathWithoutPaginationFindByCommerceVirtualOrderItemId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceVirtualOrderItemId",
				new String[] {Long.class.getName()},
				new String[] {"commerceVirtualOrderItemId"}, true);

		_finderPathCountByCommerceVirtualOrderItemId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceVirtualOrderItemId",
			new String[] {Long.class.getName()},
			new String[] {"commerceVirtualOrderItemId"}, false);

		_finderPathWithPaginationFindByC_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commerceVirtualOrderItemId", "fileEntryId"}, true);

		_finderPathWithoutPaginationFindByC_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceVirtualOrderItemId", "fileEntryId"}, true);

		_finderPathCountByC_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceVirtualOrderItemId", "fileEntryId"}, false);

		CommerceVirtualOrderItemFileEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceVirtualOrderItemFileEntryUtil.setPersistence(null);

		entityCache.removeCache(
			CommerceVirtualOrderItemFileEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY =
		"SELECT commerceVirtualOrderItemFileEntry FROM CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry";

	private static final String
		_SQL_SELECT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE =
			"SELECT commerceVirtualOrderItemFileEntry FROM CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCEVIRTUALORDERITEMFILEENTRY =
		"SELECT COUNT(commerceVirtualOrderItemFileEntry) FROM CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry";

	private static final String
		_SQL_COUNT_COMMERCEVIRTUALORDERITEMFILEENTRY_WHERE =
			"SELECT COUNT(commerceVirtualOrderItemFileEntry) FROM CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceVirtualOrderItemFileEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceVirtualOrderItemFileEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceVirtualOrderItemFileEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceVirtualOrderItemFileEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "commerceVirtualOrderItemFileEntryId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}