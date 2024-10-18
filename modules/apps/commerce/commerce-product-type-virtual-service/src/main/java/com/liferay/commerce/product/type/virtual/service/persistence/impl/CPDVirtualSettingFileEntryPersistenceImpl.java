/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.service.persistence.impl;

import com.liferay.commerce.product.type.virtual.exception.NoSuchCPDVirtualSettingFileEntryException;
import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntryTable;
import com.liferay.commerce.product.type.virtual.model.impl.CPDVirtualSettingFileEntryImpl;
import com.liferay.commerce.product.type.virtual.model.impl.CPDVirtualSettingFileEntryModelImpl;
import com.liferay.commerce.product.type.virtual.service.persistence.CPDVirtualSettingFileEntryPersistence;
import com.liferay.commerce.product.type.virtual.service.persistence.CPDVirtualSettingFileEntryUtil;
import com.liferay.commerce.product.type.virtual.service.persistence.impl.constants.CommercePersistenceConstants;
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
 * The persistence implementation for the cpd virtual setting file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CPDVirtualSettingFileEntryPersistence.class)
public class CPDVirtualSettingFileEntryPersistenceImpl
	extends BasePersistenceImpl<CPDVirtualSettingFileEntry>
	implements CPDVirtualSettingFileEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDVirtualSettingFileEntryUtil</code> to access the cpd virtual setting file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDVirtualSettingFileEntryImpl.class.getName();

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
	 * Returns all the cpd virtual setting file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd virtual setting file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @return the range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator,
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

		List<CPDVirtualSettingFileEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDVirtualSettingFileEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
						list) {

					if (!uuid.equals(cpdVirtualSettingFileEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

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
				sb.append(CPDVirtualSettingFileEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPDVirtualSettingFileEntry>)QueryUtil.list(
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
	 * Returns the first cpd virtual setting file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByUuid_First(
			String uuid,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			fetchByUuid_First(uuid, orderByComparator);

		if (cpdVirtualSettingFileEntry != null) {
			return cpdVirtualSettingFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDVirtualSettingFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		List<CPDVirtualSettingFileEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			fetchByUuid_Last(uuid, orderByComparator);

		if (cpdVirtualSettingFileEntry != null) {
			return cpdVirtualSettingFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDVirtualSettingFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDVirtualSettingFileEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cpd virtual setting file entries before and after the current cpd virtual setting file entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the current cpd virtual setting file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry[] findByUuid_PrevAndNext(
			long CPDefinitionVirtualSettingFileEntryId, String uuid,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		uuid = Objects.toString(uuid, "");

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			findByPrimaryKey(CPDefinitionVirtualSettingFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDVirtualSettingFileEntry[] array =
				new CPDVirtualSettingFileEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpdVirtualSettingFileEntry, uuid, orderByComparator,
				true);

			array[1] = cpdVirtualSettingFileEntry;

			array[2] = getByUuid_PrevAndNext(
				session, cpdVirtualSettingFileEntry, uuid, orderByComparator,
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

	protected CPDVirtualSettingFileEntry getByUuid_PrevAndNext(
		Session session, CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry,
		String uuid,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

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
			sb.append(CPDVirtualSettingFileEntryModelImpl.ORDER_BY_JPQL);
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
						cpdVirtualSettingFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDVirtualSettingFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cpd virtual setting file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpdVirtualSettingFileEntry);
		}
	}

	/**
	 * Returns the number of cpd virtual setting file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cpd virtual setting file entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

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
		"cpdVirtualSettingFileEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpdVirtualSettingFileEntry.uuid IS NULL OR cpdVirtualSettingFileEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;

	/**
	 * Returns the cpd virtual setting file entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDVirtualSettingFileEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry = fetchByUUID_G(
			uuid, groupId);

		if (cpdVirtualSettingFileEntry == null) {
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

			throw new NoSuchCPDVirtualSettingFileEntryException(sb.toString());
		}

		return cpdVirtualSettingFileEntry;
	}

	/**
	 * Returns the cpd virtual setting file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cpd virtual setting file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByUUID_G(
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

		if (result instanceof CPDVirtualSettingFileEntry) {
			CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
				(CPDVirtualSettingFileEntry)result;

			if (!Objects.equals(uuid, cpdVirtualSettingFileEntry.getUuid()) ||
				(groupId != cpdVirtualSettingFileEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

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

				List<CPDVirtualSettingFileEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
						list.get(0);

					result = cpdVirtualSettingFileEntry;

					cacheResult(cpdVirtualSettingFileEntry);
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
			return (CPDVirtualSettingFileEntry)result;
		}
	}

	/**
	 * Removes the cpd virtual setting file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cpd virtual setting file entry that was removed
	 */
	@Override
	public CPDVirtualSettingFileEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry = findByUUID_G(
			uuid, groupId);

		return remove(cpdVirtualSettingFileEntry);
	}

	/**
	 * Returns the number of cpd virtual setting file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cpd virtual setting file entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry = fetchByUUID_G(
			uuid, groupId);

		if (cpdVirtualSettingFileEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"cpdVirtualSettingFileEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(cpdVirtualSettingFileEntry.uuid IS NULL OR cpdVirtualSettingFileEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"cpdVirtualSettingFileEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cpd virtual setting file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd virtual setting file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @return the range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator,
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

		List<CPDVirtualSettingFileEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDVirtualSettingFileEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
						list) {

					if (!uuid.equals(cpdVirtualSettingFileEntry.getUuid()) ||
						(companyId !=
							cpdVirtualSettingFileEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

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
				sb.append(CPDVirtualSettingFileEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPDVirtualSettingFileEntry>)QueryUtil.list(
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
	 * Returns the first cpd virtual setting file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (cpdVirtualSettingFileEntry != null) {
			return cpdVirtualSettingFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDVirtualSettingFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		List<CPDVirtualSettingFileEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (cpdVirtualSettingFileEntry != null) {
			return cpdVirtualSettingFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDVirtualSettingFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDVirtualSettingFileEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cpd virtual setting file entries before and after the current cpd virtual setting file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the current cpd virtual setting file entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry[] findByUuid_C_PrevAndNext(
			long CPDefinitionVirtualSettingFileEntryId, String uuid,
			long companyId,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		uuid = Objects.toString(uuid, "");

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			findByPrimaryKey(CPDefinitionVirtualSettingFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDVirtualSettingFileEntry[] array =
				new CPDVirtualSettingFileEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpdVirtualSettingFileEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = cpdVirtualSettingFileEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpdVirtualSettingFileEntry, uuid, companyId,
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

	protected CPDVirtualSettingFileEntry getByUuid_C_PrevAndNext(
		Session session, CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry,
		String uuid, long companyId,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

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
			sb.append(CPDVirtualSettingFileEntryModelImpl.ORDER_BY_JPQL);
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
						cpdVirtualSettingFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDVirtualSettingFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cpd virtual setting file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpdVirtualSettingFileEntry);
		}
	}

	/**
	 * Returns the number of cpd virtual setting file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cpd virtual setting file entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

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
		"cpdVirtualSettingFileEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpdVirtualSettingFileEntry.uuid IS NULL OR cpdVirtualSettingFileEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpdVirtualSettingFileEntry.companyId = ?";

	private FinderPath
		_finderPathWithPaginationFindByCPDefinitionVirtualSettingId;
	private FinderPath
		_finderPathWithoutPaginationFindByCPDefinitionVirtualSettingId;
	private FinderPath _finderPathCountByCPDefinitionVirtualSettingId;

	/**
	 * Returns all the cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @return the matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId) {

		return findByCPDefinitionVirtualSettingId(
			CPDefinitionVirtualSettingId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @return the range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId, int start, int end) {

		return findByCPDefinitionVirtualSettingId(
			CPDefinitionVirtualSettingId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId, int start, int end,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		return findByCPDefinitionVirtualSettingId(
			CPDefinitionVirtualSettingId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId, int start, int end,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCPDefinitionVirtualSettingId;
				finderArgs = new Object[] {CPDefinitionVirtualSettingId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCPDefinitionVirtualSettingId;
			finderArgs = new Object[] {
				CPDefinitionVirtualSettingId, start, end, orderByComparator
			};
		}

		List<CPDVirtualSettingFileEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDVirtualSettingFileEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
						list) {

					if (CPDefinitionVirtualSettingId !=
							cpdVirtualSettingFileEntry.
								getCPDefinitionVirtualSettingId()) {

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

			sb.append(_SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_CPDEFINITIONVIRTUALSETTINGID_CPDEFINITIONVIRTUALSETTINGID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDVirtualSettingFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionVirtualSettingId);

				list = (List<CPDVirtualSettingFileEntry>)QueryUtil.list(
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
	 * Returns the first cpd virtual setting file entry in the ordered set where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByCPDefinitionVirtualSettingId_First(
			long CPDefinitionVirtualSettingId,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			fetchByCPDefinitionVirtualSettingId_First(
				CPDefinitionVirtualSettingId, orderByComparator);

		if (cpdVirtualSettingFileEntry != null) {
			return cpdVirtualSettingFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionVirtualSettingId=");
		sb.append(CPDefinitionVirtualSettingId);

		sb.append("}");

		throw new NoSuchCPDVirtualSettingFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByCPDefinitionVirtualSettingId_First(
		long CPDefinitionVirtualSettingId,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		List<CPDVirtualSettingFileEntry> list =
			findByCPDefinitionVirtualSettingId(
				CPDefinitionVirtualSettingId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByCPDefinitionVirtualSettingId_Last(
			long CPDefinitionVirtualSettingId,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			fetchByCPDefinitionVirtualSettingId_Last(
				CPDefinitionVirtualSettingId, orderByComparator);

		if (cpdVirtualSettingFileEntry != null) {
			return cpdVirtualSettingFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionVirtualSettingId=");
		sb.append(CPDefinitionVirtualSettingId);

		sb.append("}");

		throw new NoSuchCPDVirtualSettingFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByCPDefinitionVirtualSettingId_Last(
		long CPDefinitionVirtualSettingId,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		int count = countByCPDefinitionVirtualSettingId(
			CPDefinitionVirtualSettingId);

		if (count == 0) {
			return null;
		}

		List<CPDVirtualSettingFileEntry> list =
			findByCPDefinitionVirtualSettingId(
				CPDefinitionVirtualSettingId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cpd virtual setting file entries before and after the current cpd virtual setting file entry in the ordered set where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the current cpd virtual setting file entry
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry[]
			findByCPDefinitionVirtualSettingId_PrevAndNext(
				long CPDefinitionVirtualSettingFileEntryId,
				long CPDefinitionVirtualSettingId,
				OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			findByPrimaryKey(CPDefinitionVirtualSettingFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDVirtualSettingFileEntry[] array =
				new CPDVirtualSettingFileEntryImpl[3];

			array[0] = getByCPDefinitionVirtualSettingId_PrevAndNext(
				session, cpdVirtualSettingFileEntry,
				CPDefinitionVirtualSettingId, orderByComparator, true);

			array[1] = cpdVirtualSettingFileEntry;

			array[2] = getByCPDefinitionVirtualSettingId_PrevAndNext(
				session, cpdVirtualSettingFileEntry,
				CPDefinitionVirtualSettingId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDVirtualSettingFileEntry
		getByCPDefinitionVirtualSettingId_PrevAndNext(
			Session session,
			CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry,
			long CPDefinitionVirtualSettingId,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

		sb.append(
			_FINDER_COLUMN_CPDEFINITIONVIRTUALSETTINGID_CPDEFINITIONVIRTUALSETTINGID_2);

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
			sb.append(CPDVirtualSettingFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPDefinitionVirtualSettingId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpdVirtualSettingFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDVirtualSettingFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63; from the database.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 */
	@Override
	public void removeByCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId) {

		for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
				findByCPDefinitionVirtualSettingId(
					CPDefinitionVirtualSettingId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpdVirtualSettingFileEntry);
		}
	}

	/**
	 * Returns the number of cpd virtual setting file entries where CPDefinitionVirtualSettingId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID
	 * @return the number of matching cpd virtual setting file entries
	 */
	@Override
	public int countByCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId) {

		FinderPath finderPath = _finderPathCountByCPDefinitionVirtualSettingId;

		Object[] finderArgs = new Object[] {CPDefinitionVirtualSettingId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_CPDEFINITIONVIRTUALSETTINGID_CPDEFINITIONVIRTUALSETTINGID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionVirtualSettingId);

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
		_FINDER_COLUMN_CPDEFINITIONVIRTUALSETTINGID_CPDEFINITIONVIRTUALSETTINGID_2 =
			"cpdVirtualSettingFileEntry.CPDefinitionVirtualSettingId = ?";

	private FinderPath _finderPathWithPaginationFindByFileEntryId;
	private FinderPath _finderPathWithoutPaginationFindByFileEntryId;
	private FinderPath _finderPathCountByFileEntryId;

	/**
	 * Returns all the cpd virtual setting file entries where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByFileEntryId(
		long fileEntryId) {

		return findByFileEntryId(
			fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd virtual setting file entries where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @return the range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByFileEntryId(
		long fileEntryId, int start, int end) {

		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		return findByFileEntryId(
			fileEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByFileEntryId;
				finderArgs = new Object[] {fileEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByFileEntryId;
			finderArgs = new Object[] {
				fileEntryId, start, end, orderByComparator
			};
		}

		List<CPDVirtualSettingFileEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDVirtualSettingFileEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
						list) {

					if (fileEntryId !=
							cpdVirtualSettingFileEntry.getFileEntryId()) {

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

			sb.append(_SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDVirtualSettingFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				list = (List<CPDVirtualSettingFileEntry>)QueryUtil.list(
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
	 * Returns the first cpd virtual setting file entry in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByFileEntryId_First(
			long fileEntryId,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			fetchByFileEntryId_First(fileEntryId, orderByComparator);

		if (cpdVirtualSettingFileEntry != null) {
			return cpdVirtualSettingFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchCPDVirtualSettingFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cpd virtual setting file entry in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByFileEntryId_First(
		long fileEntryId,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		List<CPDVirtualSettingFileEntry> list = findByFileEntryId(
			fileEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByFileEntryId_Last(
			long fileEntryId,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			fetchByFileEntryId_Last(fileEntryId, orderByComparator);

		if (cpdVirtualSettingFileEntry != null) {
			return cpdVirtualSettingFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchCPDVirtualSettingFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cpd virtual setting file entry in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd virtual setting file entry, or <code>null</code> if a matching cpd virtual setting file entry could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByFileEntryId_Last(
		long fileEntryId,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		int count = countByFileEntryId(fileEntryId);

		if (count == 0) {
			return null;
		}

		List<CPDVirtualSettingFileEntry> list = findByFileEntryId(
			fileEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cpd virtual setting file entries before and after the current cpd virtual setting file entry in the ordered set where fileEntryId = &#63;.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the current cpd virtual setting file entry
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry[] findByFileEntryId_PrevAndNext(
			long CPDefinitionVirtualSettingFileEntryId, long fileEntryId,
			OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			findByPrimaryKey(CPDefinitionVirtualSettingFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDVirtualSettingFileEntry[] array =
				new CPDVirtualSettingFileEntryImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(
				session, cpdVirtualSettingFileEntry, fileEntryId,
				orderByComparator, true);

			array[1] = cpdVirtualSettingFileEntry;

			array[2] = getByFileEntryId_PrevAndNext(
				session, cpdVirtualSettingFileEntry, fileEntryId,
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

	protected CPDVirtualSettingFileEntry getByFileEntryId_PrevAndNext(
		Session session, CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry,
		long fileEntryId,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

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
			sb.append(CPDVirtualSettingFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(fileEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpdVirtualSettingFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDVirtualSettingFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cpd virtual setting file entries where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByFileEntryId(long fileEntryId) {
		for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
				findByFileEntryId(
					fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpdVirtualSettingFileEntry);
		}
	}

	/**
	 * Returns the number of cpd virtual setting file entries where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching cpd virtual setting file entries
	 */
	@Override
	public int countByFileEntryId(long fileEntryId) {
		FinderPath finderPath = _finderPathCountByFileEntryId;

		Object[] finderArgs = new Object[] {fileEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDVIRTUALSETTINGFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 =
		"cpdVirtualSettingFileEntry.fileEntryId = ?";

	public CPDVirtualSettingFileEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"CPDefinitionVirtualSettingFileEntryId",
			"CPDVirtualSettingFileEntryId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDVirtualSettingFileEntry.class);

		setModelImplClass(CPDVirtualSettingFileEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CPDVirtualSettingFileEntryTable.INSTANCE);
	}

	/**
	 * Caches the cpd virtual setting file entry in the entity cache if it is enabled.
	 *
	 * @param cpdVirtualSettingFileEntry the cpd virtual setting file entry
	 */
	@Override
	public void cacheResult(
		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry) {

		entityCache.putResult(
			CPDVirtualSettingFileEntryImpl.class,
			cpdVirtualSettingFileEntry.getPrimaryKey(),
			cpdVirtualSettingFileEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				cpdVirtualSettingFileEntry.getUuid(),
				cpdVirtualSettingFileEntry.getGroupId()
			},
			cpdVirtualSettingFileEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cpd virtual setting file entries in the entity cache if it is enabled.
	 *
	 * @param cpdVirtualSettingFileEntries the cpd virtual setting file entries
	 */
	@Override
	public void cacheResult(
		List<CPDVirtualSettingFileEntry> cpdVirtualSettingFileEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpdVirtualSettingFileEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
				cpdVirtualSettingFileEntries) {

			if (entityCache.getResult(
					CPDVirtualSettingFileEntryImpl.class,
					cpdVirtualSettingFileEntry.getPrimaryKey()) == null) {

				cacheResult(cpdVirtualSettingFileEntry);
			}
		}
	}

	/**
	 * Clears the cache for all cpd virtual setting file entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDVirtualSettingFileEntryImpl.class);

		finderCache.clearCache(CPDVirtualSettingFileEntryImpl.class);
	}

	/**
	 * Clears the cache for the cpd virtual setting file entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry) {

		entityCache.removeResult(
			CPDVirtualSettingFileEntryImpl.class, cpdVirtualSettingFileEntry);
	}

	@Override
	public void clearCache(
		List<CPDVirtualSettingFileEntry> cpdVirtualSettingFileEntries) {

		for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
				cpdVirtualSettingFileEntries) {

			entityCache.removeResult(
				CPDVirtualSettingFileEntryImpl.class,
				cpdVirtualSettingFileEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDVirtualSettingFileEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPDVirtualSettingFileEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDVirtualSettingFileEntryModelImpl
			cpdVirtualSettingFileEntryModelImpl) {

		Object[] args = new Object[] {
			cpdVirtualSettingFileEntryModelImpl.getUuid(),
			cpdVirtualSettingFileEntryModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			cpdVirtualSettingFileEntryModelImpl);
	}

	/**
	 * Creates a new cpd virtual setting file entry with the primary key. Does not add the cpd virtual setting file entry to the database.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key for the new cpd virtual setting file entry
	 * @return the new cpd virtual setting file entry
	 */
	@Override
	public CPDVirtualSettingFileEntry create(
		long CPDefinitionVirtualSettingFileEntryId) {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			new CPDVirtualSettingFileEntryImpl();

		cpdVirtualSettingFileEntry.setNew(true);
		cpdVirtualSettingFileEntry.setPrimaryKey(
			CPDefinitionVirtualSettingFileEntryId);

		String uuid = PortalUUIDUtil.generate();

		cpdVirtualSettingFileEntry.setUuid(uuid);

		cpdVirtualSettingFileEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpdVirtualSettingFileEntry;
	}

	/**
	 * Removes the cpd virtual setting file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the cpd virtual setting file entry
	 * @return the cpd virtual setting file entry that was removed
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry remove(
			long CPDefinitionVirtualSettingFileEntryId)
		throws NoSuchCPDVirtualSettingFileEntryException {

		return remove((Serializable)CPDefinitionVirtualSettingFileEntryId);
	}

	/**
	 * Removes the cpd virtual setting file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cpd virtual setting file entry
	 * @return the cpd virtual setting file entry that was removed
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry remove(Serializable primaryKey)
		throws NoSuchCPDVirtualSettingFileEntryException {

		Session session = null;

		try {
			session = openSession();

			CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
				(CPDVirtualSettingFileEntry)session.get(
					CPDVirtualSettingFileEntryImpl.class, primaryKey);

			if (cpdVirtualSettingFileEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDVirtualSettingFileEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpdVirtualSettingFileEntry);
		}
		catch (NoSuchCPDVirtualSettingFileEntryException
					noSuchEntityException) {

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
	protected CPDVirtualSettingFileEntry removeImpl(
		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpdVirtualSettingFileEntry)) {
				cpdVirtualSettingFileEntry =
					(CPDVirtualSettingFileEntry)session.get(
						CPDVirtualSettingFileEntryImpl.class,
						cpdVirtualSettingFileEntry.getPrimaryKeyObj());
			}

			if (cpdVirtualSettingFileEntry != null) {
				session.delete(cpdVirtualSettingFileEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpdVirtualSettingFileEntry != null) {
			clearCache(cpdVirtualSettingFileEntry);
		}

		return cpdVirtualSettingFileEntry;
	}

	@Override
	public CPDVirtualSettingFileEntry updateImpl(
		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry) {

		boolean isNew = cpdVirtualSettingFileEntry.isNew();

		if (!(cpdVirtualSettingFileEntry instanceof
				CPDVirtualSettingFileEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpdVirtualSettingFileEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpdVirtualSettingFileEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpdVirtualSettingFileEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDVirtualSettingFileEntry implementation " +
					cpdVirtualSettingFileEntry.getClass());
		}

		CPDVirtualSettingFileEntryModelImpl
			cpdVirtualSettingFileEntryModelImpl =
				(CPDVirtualSettingFileEntryModelImpl)cpdVirtualSettingFileEntry;

		if (Validator.isNull(cpdVirtualSettingFileEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpdVirtualSettingFileEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpdVirtualSettingFileEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpdVirtualSettingFileEntry.setCreateDate(date);
			}
			else {
				cpdVirtualSettingFileEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpdVirtualSettingFileEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpdVirtualSettingFileEntry.setModifiedDate(date);
			}
			else {
				cpdVirtualSettingFileEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpdVirtualSettingFileEntry);
			}
			else {
				cpdVirtualSettingFileEntry =
					(CPDVirtualSettingFileEntry)session.merge(
						cpdVirtualSettingFileEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDVirtualSettingFileEntryImpl.class,
			cpdVirtualSettingFileEntryModelImpl, false, true);

		cacheUniqueFindersCache(cpdVirtualSettingFileEntryModelImpl);

		if (isNew) {
			cpdVirtualSettingFileEntry.setNew(false);
		}

		cpdVirtualSettingFileEntry.resetOriginalValues();

		return cpdVirtualSettingFileEntry;
	}

	/**
	 * Returns the cpd virtual setting file entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cpd virtual setting file entry
	 * @return the cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDVirtualSettingFileEntryException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			fetchByPrimaryKey(primaryKey);

		if (cpdVirtualSettingFileEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDVirtualSettingFileEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpdVirtualSettingFileEntry;
	}

	/**
	 * Returns the cpd virtual setting file entry with the primary key or throws a <code>NoSuchCPDVirtualSettingFileEntryException</code> if it could not be found.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the cpd virtual setting file entry
	 * @return the cpd virtual setting file entry
	 * @throws NoSuchCPDVirtualSettingFileEntryException if a cpd virtual setting file entry with the primary key could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry findByPrimaryKey(
			long CPDefinitionVirtualSettingFileEntryId)
		throws NoSuchCPDVirtualSettingFileEntryException {

		return findByPrimaryKey(
			(Serializable)CPDefinitionVirtualSettingFileEntryId);
	}

	/**
	 * Returns the cpd virtual setting file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the primary key of the cpd virtual setting file entry
	 * @return the cpd virtual setting file entry, or <code>null</code> if a cpd virtual setting file entry with the primary key could not be found
	 */
	@Override
	public CPDVirtualSettingFileEntry fetchByPrimaryKey(
		long CPDefinitionVirtualSettingFileEntryId) {

		return fetchByPrimaryKey(
			(Serializable)CPDefinitionVirtualSettingFileEntryId);
	}

	/**
	 * Returns all the cpd virtual setting file entries.
	 *
	 * @return the cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd virtual setting file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @return the range of cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findAll(
		int start, int end,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd virtual setting file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDVirtualSettingFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd virtual setting file entries
	 * @param end the upper bound of the range of cpd virtual setting file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cpd virtual setting file entries
	 */
	@Override
	public List<CPDVirtualSettingFileEntry> findAll(
		int start, int end,
		OrderByComparator<CPDVirtualSettingFileEntry> orderByComparator,
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

		List<CPDVirtualSettingFileEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDVirtualSettingFileEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY;

				sql = sql.concat(
					CPDVirtualSettingFileEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPDVirtualSettingFileEntry>)QueryUtil.list(
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
	 * Removes all the cpd virtual setting file entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry :
				findAll()) {

			remove(cpdVirtualSettingFileEntry);
		}
	}

	/**
	 * Returns the number of cpd virtual setting file entries.
	 *
	 * @return the number of cpd virtual setting file entries
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
					_SQL_COUNT_CPDVIRTUALSETTINGFILEENTRY);

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
		return "CPDVirtualSettingFileEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDVirtualSettingFileEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cpd virtual setting file entry persistence.
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

		_finderPathWithPaginationFindByCPDefinitionVirtualSettingId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCPDefinitionVirtualSettingId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"CPDefinitionVirtualSettingId"}, true);

		_finderPathWithoutPaginationFindByCPDefinitionVirtualSettingId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCPDefinitionVirtualSettingId",
				new String[] {Long.class.getName()},
				new String[] {"CPDefinitionVirtualSettingId"}, true);

		_finderPathCountByCPDefinitionVirtualSettingId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPDefinitionVirtualSettingId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionVirtualSettingId"}, false);

		_finderPathWithPaginationFindByFileEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"fileEntryId"}, true);

		_finderPathWithoutPaginationFindByFileEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryId",
			new String[] {Long.class.getName()}, new String[] {"fileEntryId"},
			true);

		_finderPathCountByFileEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] {Long.class.getName()}, new String[] {"fileEntryId"},
			false);

		CPDVirtualSettingFileEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPDVirtualSettingFileEntryUtil.setPersistence(null);

		entityCache.removeCache(CPDVirtualSettingFileEntryImpl.class.getName());
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

	private static final String _SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY =
		"SELECT cpdVirtualSettingFileEntry FROM CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry";

	private static final String _SQL_SELECT_CPDVIRTUALSETTINGFILEENTRY_WHERE =
		"SELECT cpdVirtualSettingFileEntry FROM CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry WHERE ";

	private static final String _SQL_COUNT_CPDVIRTUALSETTINGFILEENTRY =
		"SELECT COUNT(cpdVirtualSettingFileEntry) FROM CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry";

	private static final String _SQL_COUNT_CPDVIRTUALSETTINGFILEENTRY_WHERE =
		"SELECT COUNT(cpdVirtualSettingFileEntry) FROM CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpdVirtualSettingFileEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDVirtualSettingFileEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDVirtualSettingFileEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDVirtualSettingFileEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "CPDefinitionVirtualSettingFileEntryId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}