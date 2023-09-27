/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.sequence.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.tools.service.builder.test.sequence.exception.NoSuchSequenceEntryException;
import com.liferay.portal.tools.service.builder.test.sequence.model.SequenceEntry;
import com.liferay.portal.tools.service.builder.test.sequence.model.SequenceEntryTable;
import com.liferay.portal.tools.service.builder.test.sequence.model.impl.SequenceEntryImpl;
import com.liferay.portal.tools.service.builder.test.sequence.model.impl.SequenceEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.sequence.service.persistence.SequenceEntryPersistence;
import com.liferay.portal.tools.service.builder.test.sequence.service.persistence.SequenceEntryUtil;
import com.liferay.portal.tools.service.builder.test.sequence.service.persistence.impl.constants.SBTestSequencePersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the sequence entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = SequenceEntryPersistence.class)
public class SequenceEntryPersistenceImpl
	extends BasePersistenceImpl<SequenceEntry>
	implements SequenceEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SequenceEntryUtil</code> to access the sequence entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SequenceEntryImpl.class.getName();

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
	 * Returns all the sequence entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sequence entries
	 */
	@Override
	public List<SequenceEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sequence entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @return the range of matching sequence entries
	 */
	@Override
	public List<SequenceEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sequence entries
	 */
	@Override
	public List<SequenceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sequence entries
	 */
	@Override
	public List<SequenceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator,
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

		List<SequenceEntry> list = null;

		if (useFinderCache) {
			list = (List<SequenceEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SequenceEntry sequenceEntry : list) {
					if (!uuid.equals(sequenceEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_SEQUENCEENTRY_WHERE);

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
				sb.append(SequenceEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<SequenceEntry>)QueryUtil.list(
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
	 * Returns the first sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	@Override
	public SequenceEntry findByUuid_First(
			String uuid, OrderByComparator<SequenceEntry> orderByComparator)
		throws NoSuchSequenceEntryException {

		SequenceEntry sequenceEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (sequenceEntry != null) {
			return sequenceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSequenceEntryException(sb.toString());
	}

	/**
	 * Returns the first sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	@Override
	public SequenceEntry fetchByUuid_First(
		String uuid, OrderByComparator<SequenceEntry> orderByComparator) {

		List<SequenceEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	@Override
	public SequenceEntry findByUuid_Last(
			String uuid, OrderByComparator<SequenceEntry> orderByComparator)
		throws NoSuchSequenceEntryException {

		SequenceEntry sequenceEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (sequenceEntry != null) {
			return sequenceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSequenceEntryException(sb.toString());
	}

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	@Override
	public SequenceEntry fetchByUuid_Last(
		String uuid, OrderByComparator<SequenceEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SequenceEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sequence entries before and after the current sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param sequenceEntryId the primary key of the current sequence entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sequence entry
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	@Override
	public SequenceEntry[] findByUuid_PrevAndNext(
			long sequenceEntryId, String uuid,
			OrderByComparator<SequenceEntry> orderByComparator)
		throws NoSuchSequenceEntryException {

		uuid = Objects.toString(uuid, "");

		SequenceEntry sequenceEntry = findByPrimaryKey(sequenceEntryId);

		Session session = null;

		try {
			session = openSession();

			SequenceEntry[] array = new SequenceEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, sequenceEntry, uuid, orderByComparator, true);

			array[1] = sequenceEntry;

			array[2] = getByUuid_PrevAndNext(
				session, sequenceEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SequenceEntry getByUuid_PrevAndNext(
		Session session, SequenceEntry sequenceEntry, String uuid,
		OrderByComparator<SequenceEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SEQUENCEENTRY_WHERE);

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
			sb.append(SequenceEntryModelImpl.ORDER_BY_JPQL);
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
						sequenceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SequenceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sequence entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SequenceEntry sequenceEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(sequenceEntry);
		}
	}

	/**
	 * Returns the number of sequence entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sequence entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SEQUENCEENTRY_WHERE);

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
		"sequenceEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(sequenceEntry.uuid IS NULL OR sequenceEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sequence entries
	 */
	@Override
	public List<SequenceEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @return the range of matching sequence entries
	 */
	@Override
	public List<SequenceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sequence entries
	 */
	@Override
	public List<SequenceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sequence entries
	 */
	@Override
	public List<SequenceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator,
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

		List<SequenceEntry> list = null;

		if (useFinderCache) {
			list = (List<SequenceEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SequenceEntry sequenceEntry : list) {
					if (!uuid.equals(sequenceEntry.getUuid()) ||
						(companyId != sequenceEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_SEQUENCEENTRY_WHERE);

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
				sb.append(SequenceEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<SequenceEntry>)QueryUtil.list(
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
	 * Returns the first sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	@Override
	public SequenceEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SequenceEntry> orderByComparator)
		throws NoSuchSequenceEntryException {

		SequenceEntry sequenceEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (sequenceEntry != null) {
			return sequenceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSequenceEntryException(sb.toString());
	}

	/**
	 * Returns the first sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	@Override
	public SequenceEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SequenceEntry> orderByComparator) {

		List<SequenceEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	@Override
	public SequenceEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SequenceEntry> orderByComparator)
		throws NoSuchSequenceEntryException {

		SequenceEntry sequenceEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (sequenceEntry != null) {
			return sequenceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSequenceEntryException(sb.toString());
	}

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	@Override
	public SequenceEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SequenceEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SequenceEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sequence entries before and after the current sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sequenceEntryId the primary key of the current sequence entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sequence entry
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	@Override
	public SequenceEntry[] findByUuid_C_PrevAndNext(
			long sequenceEntryId, String uuid, long companyId,
			OrderByComparator<SequenceEntry> orderByComparator)
		throws NoSuchSequenceEntryException {

		uuid = Objects.toString(uuid, "");

		SequenceEntry sequenceEntry = findByPrimaryKey(sequenceEntryId);

		Session session = null;

		try {
			session = openSession();

			SequenceEntry[] array = new SequenceEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, sequenceEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = sequenceEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, sequenceEntry, uuid, companyId, orderByComparator,
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

	protected SequenceEntry getByUuid_C_PrevAndNext(
		Session session, SequenceEntry sequenceEntry, String uuid,
		long companyId, OrderByComparator<SequenceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_SEQUENCEENTRY_WHERE);

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
			sb.append(SequenceEntryModelImpl.ORDER_BY_JPQL);
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
						sequenceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SequenceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sequence entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SequenceEntry sequenceEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(sequenceEntry);
		}
	}

	/**
	 * Returns the number of sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sequence entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SEQUENCEENTRY_WHERE);

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
		"sequenceEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(sequenceEntry.uuid IS NULL OR sequenceEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"sequenceEntry.companyId = ?";

	public SequenceEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SequenceEntry.class);

		setModelImplClass(SequenceEntryImpl.class);
		setModelPKClass(long.class);

		setTable(SequenceEntryTable.INSTANCE);
	}

	/**
	 * Caches the sequence entry in the entity cache if it is enabled.
	 *
	 * @param sequenceEntry the sequence entry
	 */
	@Override
	public void cacheResult(SequenceEntry sequenceEntry) {
		entityCache.putResult(
			SequenceEntryImpl.class, sequenceEntry.getPrimaryKey(),
			sequenceEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the sequence entries in the entity cache if it is enabled.
	 *
	 * @param sequenceEntries the sequence entries
	 */
	@Override
	public void cacheResult(List<SequenceEntry> sequenceEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (sequenceEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SequenceEntry sequenceEntry : sequenceEntries) {
			if (entityCache.getResult(
					SequenceEntryImpl.class, sequenceEntry.getPrimaryKey()) ==
						null) {

				cacheResult(sequenceEntry);
			}
		}
	}

	/**
	 * Clears the cache for all sequence entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SequenceEntryImpl.class);

		finderCache.clearCache(SequenceEntryImpl.class);
	}

	/**
	 * Clears the cache for the sequence entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SequenceEntry sequenceEntry) {
		entityCache.removeResult(SequenceEntryImpl.class, sequenceEntry);
	}

	@Override
	public void clearCache(List<SequenceEntry> sequenceEntries) {
		for (SequenceEntry sequenceEntry : sequenceEntries) {
			entityCache.removeResult(SequenceEntryImpl.class, sequenceEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(SequenceEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SequenceEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new sequence entry with the primary key. Does not add the sequence entry to the database.
	 *
	 * @param sequenceEntryId the primary key for the new sequence entry
	 * @return the new sequence entry
	 */
	@Override
	public SequenceEntry create(long sequenceEntryId) {
		SequenceEntry sequenceEntry = new SequenceEntryImpl();

		sequenceEntry.setNew(true);
		sequenceEntry.setPrimaryKey(sequenceEntryId);

		String uuid = _portalUUID.generate();

		sequenceEntry.setUuid(uuid);

		sequenceEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return sequenceEntry;
	}

	/**
	 * Removes the sequence entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry that was removed
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	@Override
	public SequenceEntry remove(long sequenceEntryId)
		throws NoSuchSequenceEntryException {

		return remove((Serializable)sequenceEntryId);
	}

	/**
	 * Removes the sequence entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the sequence entry
	 * @return the sequence entry that was removed
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	@Override
	public SequenceEntry remove(Serializable primaryKey)
		throws NoSuchSequenceEntryException {

		Session session = null;

		try {
			session = openSession();

			SequenceEntry sequenceEntry = (SequenceEntry)session.get(
				SequenceEntryImpl.class, primaryKey);

			if (sequenceEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSequenceEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(sequenceEntry);
		}
		catch (NoSuchSequenceEntryException noSuchEntityException) {
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
	protected SequenceEntry removeImpl(SequenceEntry sequenceEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(sequenceEntry)) {
				sequenceEntry = (SequenceEntry)session.get(
					SequenceEntryImpl.class, sequenceEntry.getPrimaryKeyObj());
			}

			if (sequenceEntry != null) {
				session.delete(sequenceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (sequenceEntry != null) {
			clearCache(sequenceEntry);
		}

		return sequenceEntry;
	}

	@Override
	public SequenceEntry updateImpl(SequenceEntry sequenceEntry) {
		boolean isNew = sequenceEntry.isNew();

		if (!(sequenceEntry instanceof SequenceEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(sequenceEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					sequenceEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in sequenceEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SequenceEntry implementation " +
					sequenceEntry.getClass());
		}

		SequenceEntryModelImpl sequenceEntryModelImpl =
			(SequenceEntryModelImpl)sequenceEntry;

		if (Validator.isNull(sequenceEntry.getUuid())) {
			String uuid = _portalUUID.generate();

			sequenceEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(sequenceEntry);
			}
			else {
				sequenceEntry = (SequenceEntry)session.merge(sequenceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			SequenceEntryImpl.class, sequenceEntryModelImpl, false, true);

		if (isNew) {
			sequenceEntry.setNew(false);
		}

		sequenceEntry.resetOriginalValues();

		return sequenceEntry;
	}

	/**
	 * Returns the sequence entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the sequence entry
	 * @return the sequence entry
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	@Override
	public SequenceEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSequenceEntryException {

		SequenceEntry sequenceEntry = fetchByPrimaryKey(primaryKey);

		if (sequenceEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSequenceEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return sequenceEntry;
	}

	/**
	 * Returns the sequence entry with the primary key or throws a <code>NoSuchSequenceEntryException</code> if it could not be found.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	@Override
	public SequenceEntry findByPrimaryKey(long sequenceEntryId)
		throws NoSuchSequenceEntryException {

		return findByPrimaryKey((Serializable)sequenceEntryId);
	}

	/**
	 * Returns the sequence entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry, or <code>null</code> if a sequence entry with the primary key could not be found
	 */
	@Override
	public SequenceEntry fetchByPrimaryKey(long sequenceEntryId) {
		return fetchByPrimaryKey((Serializable)sequenceEntryId);
	}

	/**
	 * Returns all the sequence entries.
	 *
	 * @return the sequence entries
	 */
	@Override
	public List<SequenceEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sequence entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @return the range of sequence entries
	 */
	@Override
	public List<SequenceEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the sequence entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sequence entries
	 */
	@Override
	public List<SequenceEntry> findAll(
		int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sequence entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sequence entries
	 */
	@Override
	public List<SequenceEntry> findAll(
		int start, int end, OrderByComparator<SequenceEntry> orderByComparator,
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

		List<SequenceEntry> list = null;

		if (useFinderCache) {
			list = (List<SequenceEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SEQUENCEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SEQUENCEENTRY;

				sql = sql.concat(SequenceEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SequenceEntry>)QueryUtil.list(
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
	 * Removes all the sequence entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SequenceEntry sequenceEntry : findAll()) {
			remove(sequenceEntry);
		}
	}

	/**
	 * Returns the number of sequence entries.
	 *
	 * @return the number of sequence entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SEQUENCEENTRY);

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
		return "sequenceEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SEQUENCEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SequenceEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the sequence entry persistence.
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

		SequenceEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		SequenceEntryUtil.setPersistence(null);

		entityCache.removeCache(SequenceEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = SBTestSequencePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SBTestSequencePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SBTestSequencePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SEQUENCEENTRY =
		"SELECT sequenceEntry FROM SequenceEntry sequenceEntry";

	private static final String _SQL_SELECT_SEQUENCEENTRY_WHERE =
		"SELECT sequenceEntry FROM SequenceEntry sequenceEntry WHERE ";

	private static final String _SQL_COUNT_SEQUENCEENTRY =
		"SELECT COUNT(sequenceEntry) FROM SequenceEntry sequenceEntry";

	private static final String _SQL_COUNT_SEQUENCEENTRY_WHERE =
		"SELECT COUNT(sequenceEntry) FROM SequenceEntry sequenceEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "sequenceEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SequenceEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SequenceEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SequenceEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}