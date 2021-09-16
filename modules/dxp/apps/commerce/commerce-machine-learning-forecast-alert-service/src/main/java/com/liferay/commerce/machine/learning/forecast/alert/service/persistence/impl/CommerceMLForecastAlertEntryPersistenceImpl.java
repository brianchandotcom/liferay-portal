/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.machine.learning.forecast.alert.service.persistence.impl;

import com.liferay.commerce.machine.learning.forecast.alert.exception.NoSuchMLForecastAlertEntryException;
import com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntry;
import com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntryTable;
import com.liferay.commerce.machine.learning.forecast.alert.model.impl.CommerceMLForecastAlertEntryImpl;
import com.liferay.commerce.machine.learning.forecast.alert.model.impl.CommerceMLForecastAlertEntryModelImpl;
import com.liferay.commerce.machine.learning.forecast.alert.service.persistence.CommerceMLForecastAlertEntryPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce ml forecast alert entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Ferrari
 * @generated
 */
public class CommerceMLForecastAlertEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceMLForecastAlertEntry>
	implements CommerceMLForecastAlertEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceMLForecastAlertEntryUtil</code> to access the commerce ml forecast alert entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceMLForecastAlertEntryImpl.class.getName();

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
	 * Returns all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
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

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if (!uuid.equals(commerceMLForecastAlertEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

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
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
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
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByUuid_First(
			String uuid,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByUuid_First(uuid, orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		List<CommerceMLForecastAlertEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceMLForecastAlertEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce ml forecast alert entries before and after the current commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the current commerce ml forecast alert entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry[] findByUuid_PrevAndNext(
			long commerceMLForecastAlertEntryId, String uuid,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		uuid = Objects.toString(uuid, "");

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			findByPrimaryKey(commerceMLForecastAlertEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceMLForecastAlertEntry[] array =
				new CommerceMLForecastAlertEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceMLForecastAlertEntry, uuid, orderByComparator,
				true);

			array[1] = commerceMLForecastAlertEntry;

			array[2] = getByUuid_PrevAndNext(
				session, commerceMLForecastAlertEntry, uuid, orderByComparator,
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

	protected CommerceMLForecastAlertEntry getByUuid_PrevAndNext(
		Session session,
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry, String uuid,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

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
			sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
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
						commerceMLForecastAlertEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceMLForecastAlertEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce ml forecast alert entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceMLForecastAlertEntry);
		}
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

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
		"commerceMLForecastAlertEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceMLForecastAlertEntry.uuid IS NULL OR commerceMLForecastAlertEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
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

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if (!uuid.equals(commerceMLForecastAlertEntry.getUuid()) ||
						(companyId !=
							commerceMLForecastAlertEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

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
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
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
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		List<CommerceMLForecastAlertEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceMLForecastAlertEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce ml forecast alert entries before and after the current commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the current commerce ml forecast alert entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry[] findByUuid_C_PrevAndNext(
			long commerceMLForecastAlertEntryId, String uuid, long companyId,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		uuid = Objects.toString(uuid, "");

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			findByPrimaryKey(commerceMLForecastAlertEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceMLForecastAlertEntry[] array =
				new CommerceMLForecastAlertEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceMLForecastAlertEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = commerceMLForecastAlertEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceMLForecastAlertEntry, uuid, companyId,
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

	protected CommerceMLForecastAlertEntry getByUuid_C_PrevAndNext(
		Session session,
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry, String uuid,
		long companyId,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

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
			sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
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
						commerceMLForecastAlertEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceMLForecastAlertEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceMLForecastAlertEntry);
		}
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

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
		"commerceMLForecastAlertEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceMLForecastAlertEntry.uuid IS NULL OR commerceMLForecastAlertEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceMLForecastAlertEntry.companyId = ?";

	private FinderPath _finderPathFetchByC_C_T;
	private FinderPath _finderPathCountByC_C_T;

	/**
	 * Returns the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; or throws a <code>NoSuchMLForecastAlertEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_T(
			long companyId, long commerceAccountId, Date timestamp)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_T(companyId, commerceAccountId, timestamp);

		if (commerceMLForecastAlertEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", commerceAccountId=");
			sb.append(commerceAccountId);

			sb.append(", timestamp=");
			sb.append(timestamp);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchMLForecastAlertEntryException(sb.toString());
		}

		return commerceMLForecastAlertEntry;
	}

	/**
	 * Returns the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_T(
		long companyId, long commerceAccountId, Date timestamp) {

		return fetchByC_C_T(companyId, commerceAccountId, timestamp, true);
	}

	/**
	 * Returns the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_T(
		long companyId, long commerceAccountId, Date timestamp,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, commerceAccountId, _getTime(timestamp)
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C_T, finderArgs);
		}

		if (result instanceof CommerceMLForecastAlertEntry) {
			CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
				(CommerceMLForecastAlertEntry)result;

			if ((companyId != commerceMLForecastAlertEntry.getCompanyId()) ||
				(commerceAccountId !=
					commerceMLForecastAlertEntry.getCommerceAccountId()) ||
				!Objects.equals(
					timestamp, commerceMLForecastAlertEntry.getTimestamp())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_T_COMMERCEACCOUNTID_2);

			boolean bindTimestamp = false;

			if (timestamp == null) {
				sb.append(_FINDER_COLUMN_C_C_T_TIMESTAMP_1);
			}
			else {
				bindTimestamp = true;

				sb.append(_FINDER_COLUMN_C_C_T_TIMESTAMP_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				if (bindTimestamp) {
					queryPos.add(new Timestamp(timestamp.getTime()));
				}

				List<CommerceMLForecastAlertEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_T, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, commerceAccountId,
									_getTime(timestamp)
								};
							}

							_log.warn(
								"CommerceMLForecastAlertEntryPersistenceImpl.fetchByC_C_T(long, long, Date, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
						list.get(0);

					result = commerceMLForecastAlertEntry;

					cacheResult(commerceMLForecastAlertEntry);
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
			return (CommerceMLForecastAlertEntry)result;
		}
	}

	/**
	 * Removes the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the commerce ml forecast alert entry that was removed
	 */
	@Override
	public CommerceMLForecastAlertEntry removeByC_C_T(
			long companyId, long commerceAccountId, Date timestamp)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry = findByC_C_T(
			companyId, commerceAccountId, timestamp);

		return remove(commerceMLForecastAlertEntry);
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_T(
		long companyId, long commerceAccountId, Date timestamp) {

		FinderPath finderPath = _finderPathCountByC_C_T;

		Object[] finderArgs = new Object[] {
			companyId, commerceAccountId, _getTime(timestamp)
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_T_COMMERCEACCOUNTID_2);

			boolean bindTimestamp = false;

			if (timestamp == null) {
				sb.append(_FINDER_COLUMN_C_C_T_TIMESTAMP_1);
			}
			else {
				bindTimestamp = true;

				sb.append(_FINDER_COLUMN_C_C_T_TIMESTAMP_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				if (bindTimestamp) {
					queryPos.add(new Timestamp(timestamp.getTime()));
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

	private static final String _FINDER_COLUMN_C_C_T_COMPANYID_2 =
		"commerceMLForecastAlertEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_COMMERCEACCOUNTID_2 =
		"commerceMLForecastAlertEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_TIMESTAMP_1 =
		"commerceMLForecastAlertEntry.timestamp IS NULL";

	private static final String _FINDER_COLUMN_C_C_T_TIMESTAMP_2 =
		"commerceMLForecastAlertEntry.timestamp = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_S;
	private FinderPath _finderPathWithoutPaginationFindByC_C_S;
	private FinderPath _finderPathCountByC_C_S;
	private FinderPath _finderPathWithPaginationCountByC_C_S;

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status) {

		return findByC_C_S(
			companyId, commerceAccountId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status, int start,
		int end) {

		return findByC_C_S(
			companyId, commerceAccountId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_S(
			companyId, commerceAccountId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C_S;
				finderArgs = new Object[] {
					companyId, commerceAccountId, status
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C_S;
			finderArgs = new Object[] {
				companyId, commerceAccountId, status, start, end,
				orderByComparator
			};
		}

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						(commerceAccountId !=
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
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
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_S_First(
			long companyId, long commerceAccountId, int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_S_First(
				companyId, commerceAccountId, status, orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_S_First(
		long companyId, long commerceAccountId, int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		List<CommerceMLForecastAlertEntry> list = findByC_C_S(
			companyId, commerceAccountId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_S_Last(
			long companyId, long commerceAccountId, int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_S_Last(
				companyId, commerceAccountId, status, orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_S_Last(
		long companyId, long commerceAccountId, int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		int count = countByC_C_S(companyId, commerceAccountId, status);

		if (count == 0) {
			return null;
		}

		List<CommerceMLForecastAlertEntry> list = findByC_C_S(
			companyId, commerceAccountId, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce ml forecast alert entries before and after the current commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the current commerce ml forecast alert entry
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry[] findByC_C_S_PrevAndNext(
			long commerceMLForecastAlertEntryId, long companyId,
			long commerceAccountId, int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			findByPrimaryKey(commerceMLForecastAlertEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceMLForecastAlertEntry[] array =
				new CommerceMLForecastAlertEntryImpl[3];

			array[0] = getByC_C_S_PrevAndNext(
				session, commerceMLForecastAlertEntry, companyId,
				commerceAccountId, status, orderByComparator, true);

			array[1] = commerceMLForecastAlertEntry;

			array[2] = getByC_C_S_PrevAndNext(
				session, commerceMLForecastAlertEntry, companyId,
				commerceAccountId, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceMLForecastAlertEntry getByC_C_S_PrevAndNext(
		Session session,
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry,
		long companyId, long commerceAccountId, int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_2);

		sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

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
			sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(commerceAccountId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceMLForecastAlertEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceMLForecastAlertEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status) {

		return findByC_C_S(
			companyId, commerceAccountIds, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status, int start,
		int end) {

		return findByC_C_S(
			companyId, commerceAccountIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status, int start,
		int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_S(
			companyId, commerceAccountIds, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status, int start,
		int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		if (commerceAccountIds.length == 1) {
			return findByC_C_S(
				companyId, commerceAccountIds[0], status, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(commerceAccountIds), status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, StringUtil.merge(commerceAccountIds), status, start,
				end, orderByComparator
			};
		}

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByC_C_S, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						!ArrayUtil.contains(
							commerceAccountIds,
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_C_S, finderArgs, list);
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
	 * Removes all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 */
	@Override
	public void removeByC_C_S(
		long companyId, long commerceAccountId, int status) {

		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				findByC_C_S(
					companyId, commerceAccountId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceMLForecastAlertEntry);
		}
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_S(
		long companyId, long commerceAccountId, int status) {

		FinderPath finderPath = _finderPathCountByC_C_S;

		Object[] finderArgs = new Object[] {
			companyId, commerceAccountId, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(status);

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

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_S(
		long companyId, long[] commerceAccountIds, int status) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		Object[] finderArgs = new Object[] {
			companyId, StringUtil.merge(commerceAccountIds), status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_C_S, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_C_S, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_C_S_COMPANYID_2 =
		"commerceMLForecastAlertEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_2 =
		"commerceMLForecastAlertEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_7 =
		"commerceMLForecastAlertEntry.commerceAccountId IN (";

	private static final String _FINDER_COLUMN_C_C_S_STATUS_2 =
		"commerceMLForecastAlertEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_GtRc_S;
	private FinderPath _finderPathWithPaginationCountByC_C_GtRc_S;

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountId, relativeChange, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountId, relativeChange, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountId, relativeChange, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_C_GtRc_S;
		finderArgs = new Object[] {
			companyId, commerceAccountId, relativeChange, status, start, end,
			orderByComparator
		};

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						(commerceAccountId !=
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(relativeChange >=
							commerceMLForecastAlertEntry.getRelativeChange()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
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
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_GtRc_S_First(
			long companyId, long commerceAccountId, double relativeChange,
			int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_GtRc_S_First(
				companyId, commerceAccountId, relativeChange, status,
				orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", relativeChange>");
		sb.append(relativeChange);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_GtRc_S_First(
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		List<CommerceMLForecastAlertEntry> list = findByC_C_GtRc_S(
			companyId, commerceAccountId, relativeChange, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_GtRc_S_Last(
			long companyId, long commerceAccountId, double relativeChange,
			int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_GtRc_S_Last(
				companyId, commerceAccountId, relativeChange, status,
				orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", relativeChange>");
		sb.append(relativeChange);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_GtRc_S_Last(
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		int count = countByC_C_GtRc_S(
			companyId, commerceAccountId, relativeChange, status);

		if (count == 0) {
			return null;
		}

		List<CommerceMLForecastAlertEntry> list = findByC_C_GtRc_S(
			companyId, commerceAccountId, relativeChange, status, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce ml forecast alert entries before and after the current commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the current commerce ml forecast alert entry
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry[] findByC_C_GtRc_S_PrevAndNext(
			long commerceMLForecastAlertEntryId, long companyId,
			long commerceAccountId, double relativeChange, int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			findByPrimaryKey(commerceMLForecastAlertEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceMLForecastAlertEntry[] array =
				new CommerceMLForecastAlertEntryImpl[3];

			array[0] = getByC_C_GtRc_S_PrevAndNext(
				session, commerceMLForecastAlertEntry, companyId,
				commerceAccountId, relativeChange, status, orderByComparator,
				true);

			array[1] = commerceMLForecastAlertEntry;

			array[2] = getByC_C_GtRc_S_PrevAndNext(
				session, commerceMLForecastAlertEntry, companyId,
				commerceAccountId, relativeChange, status, orderByComparator,
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

	protected CommerceMLForecastAlertEntry getByC_C_GtRc_S_PrevAndNext(
		Session session,
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry,
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_2);

		sb.append(_FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2);

		sb.append(_FINDER_COLUMN_C_C_GTRC_S_STATUS_2);

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
			sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(commerceAccountId);

		queryPos.add(relativeChange);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceMLForecastAlertEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceMLForecastAlertEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountIds, relativeChange, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountIds, relativeChange, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountIds, relativeChange, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		if (commerceAccountIds.length == 1) {
			return findByC_C_GtRc_S(
				companyId, commerceAccountIds[0], relativeChange, status, start,
				end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(commerceAccountIds),
					relativeChange, status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, StringUtil.merge(commerceAccountIds), relativeChange,
				status, start, end, orderByComparator
			};
		}

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByC_C_GtRc_S, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						!ArrayUtil.contains(
							commerceAccountIds,
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(relativeChange >=
							commerceMLForecastAlertEntry.getRelativeChange()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_C_GtRc_S, finderArgs,
						list);
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
	 * Removes all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 */
	@Override
	public void removeByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				findByC_C_GtRc_S(
					companyId, commerceAccountId, relativeChange, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceMLForecastAlertEntry);
		}
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		FinderPath finderPath = _finderPathWithPaginationCountByC_C_GtRc_S;

		Object[] finderArgs = new Object[] {
			companyId, commerceAccountId, relativeChange, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(relativeChange);

				queryPos.add(status);

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

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		Object[] finderArgs = new Object[] {
			companyId, StringUtil.merge(commerceAccountIds), relativeChange,
			status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_C_GtRc_S, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_C_GtRc_S, finderArgs,
					count);
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

	private static final String _FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2 =
		"commerceMLForecastAlertEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_2 =
		"commerceMLForecastAlertEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_7 =
		"commerceMLForecastAlertEntry.commerceAccountId IN (";

	private static final String _FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2 =
		"commerceMLForecastAlertEntry.relativeChange > ? AND ";

	private static final String _FINDER_COLUMN_C_C_GTRC_S_STATUS_2 =
		"commerceMLForecastAlertEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_LtRc_S;
	private FinderPath _finderPathWithPaginationCountByC_C_LtRc_S;

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountId, relativeChange, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountId, relativeChange, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountId, relativeChange, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_C_LtRc_S;
		finderArgs = new Object[] {
			companyId, commerceAccountId, relativeChange, status, start, end,
			orderByComparator
		};

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						(commerceAccountId !=
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(relativeChange <=
							commerceMLForecastAlertEntry.getRelativeChange()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
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
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_LtRc_S_First(
			long companyId, long commerceAccountId, double relativeChange,
			int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_LtRc_S_First(
				companyId, commerceAccountId, relativeChange, status,
				orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", relativeChange<");
		sb.append(relativeChange);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_LtRc_S_First(
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		List<CommerceMLForecastAlertEntry> list = findByC_C_LtRc_S(
			companyId, commerceAccountId, relativeChange, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_LtRc_S_Last(
			long companyId, long commerceAccountId, double relativeChange,
			int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_LtRc_S_Last(
				companyId, commerceAccountId, relativeChange, status,
				orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", relativeChange<");
		sb.append(relativeChange);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_LtRc_S_Last(
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		int count = countByC_C_LtRc_S(
			companyId, commerceAccountId, relativeChange, status);

		if (count == 0) {
			return null;
		}

		List<CommerceMLForecastAlertEntry> list = findByC_C_LtRc_S(
			companyId, commerceAccountId, relativeChange, status, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce ml forecast alert entries before and after the current commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the current commerce ml forecast alert entry
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry[] findByC_C_LtRc_S_PrevAndNext(
			long commerceMLForecastAlertEntryId, long companyId,
			long commerceAccountId, double relativeChange, int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			findByPrimaryKey(commerceMLForecastAlertEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceMLForecastAlertEntry[] array =
				new CommerceMLForecastAlertEntryImpl[3];

			array[0] = getByC_C_LtRc_S_PrevAndNext(
				session, commerceMLForecastAlertEntry, companyId,
				commerceAccountId, relativeChange, status, orderByComparator,
				true);

			array[1] = commerceMLForecastAlertEntry;

			array[2] = getByC_C_LtRc_S_PrevAndNext(
				session, commerceMLForecastAlertEntry, companyId,
				commerceAccountId, relativeChange, status, orderByComparator,
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

	protected CommerceMLForecastAlertEntry getByC_C_LtRc_S_PrevAndNext(
		Session session,
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry,
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_2);

		sb.append(_FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2);

		sb.append(_FINDER_COLUMN_C_C_LTRC_S_STATUS_2);

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
			sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(commerceAccountId);

		queryPos.add(relativeChange);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceMLForecastAlertEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceMLForecastAlertEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountIds, relativeChange, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountIds, relativeChange, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountIds, relativeChange, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		if (commerceAccountIds.length == 1) {
			return findByC_C_LtRc_S(
				companyId, commerceAccountIds[0], relativeChange, status, start,
				end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(commerceAccountIds),
					relativeChange, status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, StringUtil.merge(commerceAccountIds), relativeChange,
				status, start, end, orderByComparator
			};
		}

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByC_C_LtRc_S, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						!ArrayUtil.contains(
							commerceAccountIds,
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(relativeChange <=
							commerceMLForecastAlertEntry.getRelativeChange()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_C_LtRc_S, finderArgs,
						list);
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
	 * Removes all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 */
	@Override
	public void removeByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				findByC_C_LtRc_S(
					companyId, commerceAccountId, relativeChange, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceMLForecastAlertEntry);
		}
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		FinderPath finderPath = _finderPathWithPaginationCountByC_C_LtRc_S;

		Object[] finderArgs = new Object[] {
			companyId, commerceAccountId, relativeChange, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(relativeChange);

				queryPos.add(status);

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

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		Object[] finderArgs = new Object[] {
			companyId, StringUtil.merge(commerceAccountIds), relativeChange,
			status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_C_LtRc_S, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_C_LtRc_S, finderArgs,
					count);
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

	private static final String _FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2 =
		"commerceMLForecastAlertEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_2 =
		"commerceMLForecastAlertEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_7 =
		"commerceMLForecastAlertEntry.commerceAccountId IN (";

	private static final String _FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2 =
		"commerceMLForecastAlertEntry.relativeChange < ? AND ";

	private static final String _FINDER_COLUMN_C_C_LTRC_S_STATUS_2 =
		"commerceMLForecastAlertEntry.status = ?";

	public CommerceMLForecastAlertEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceMLForecastAlertEntry.class);

		setModelImplClass(CommerceMLForecastAlertEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceMLForecastAlertEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce ml forecast alert entry in the entity cache if it is enabled.
	 *
	 * @param commerceMLForecastAlertEntry the commerce ml forecast alert entry
	 */
	@Override
	public void cacheResult(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		entityCache.putResult(
			CommerceMLForecastAlertEntryImpl.class,
			commerceMLForecastAlertEntry.getPrimaryKey(),
			commerceMLForecastAlertEntry);

		finderCache.putResult(
			_finderPathFetchByC_C_T,
			new Object[] {
				commerceMLForecastAlertEntry.getCompanyId(),
				commerceMLForecastAlertEntry.getCommerceAccountId(),
				commerceMLForecastAlertEntry.getTimestamp()
			},
			commerceMLForecastAlertEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce ml forecast alert entries in the entity cache if it is enabled.
	 *
	 * @param commerceMLForecastAlertEntries the commerce ml forecast alert entries
	 */
	@Override
	public void cacheResult(
		List<CommerceMLForecastAlertEntry> commerceMLForecastAlertEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceMLForecastAlertEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				commerceMLForecastAlertEntries) {

			if (entityCache.getResult(
					CommerceMLForecastAlertEntryImpl.class,
					commerceMLForecastAlertEntry.getPrimaryKey()) == null) {

				cacheResult(commerceMLForecastAlertEntry);
			}
		}
	}

	/**
	 * Clears the cache for all commerce ml forecast alert entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceMLForecastAlertEntryImpl.class);

		finderCache.clearCache(CommerceMLForecastAlertEntryImpl.class);
	}

	/**
	 * Clears the cache for the commerce ml forecast alert entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		entityCache.removeResult(
			CommerceMLForecastAlertEntryImpl.class,
			commerceMLForecastAlertEntry);
	}

	@Override
	public void clearCache(
		List<CommerceMLForecastAlertEntry> commerceMLForecastAlertEntries) {

		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				commerceMLForecastAlertEntries) {

			entityCache.removeResult(
				CommerceMLForecastAlertEntryImpl.class,
				commerceMLForecastAlertEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceMLForecastAlertEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceMLForecastAlertEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceMLForecastAlertEntryModelImpl
			commerceMLForecastAlertEntryModelImpl) {

		Object[] args = new Object[] {
			commerceMLForecastAlertEntryModelImpl.getCompanyId(),
			commerceMLForecastAlertEntryModelImpl.getCommerceAccountId(),
			_getTime(commerceMLForecastAlertEntryModelImpl.getTimestamp())
		};

		finderCache.putResult(_finderPathCountByC_C_T, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_T, args,
			commerceMLForecastAlertEntryModelImpl);
	}

	/**
	 * Creates a new commerce ml forecast alert entry with the primary key. Does not add the commerce ml forecast alert entry to the database.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key for the new commerce ml forecast alert entry
	 * @return the new commerce ml forecast alert entry
	 */
	@Override
	public CommerceMLForecastAlertEntry create(
		long commerceMLForecastAlertEntryId) {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			new CommerceMLForecastAlertEntryImpl();

		commerceMLForecastAlertEntry.setNew(true);
		commerceMLForecastAlertEntry.setPrimaryKey(
			commerceMLForecastAlertEntryId);

		String uuid = PortalUUIDUtil.generate();

		commerceMLForecastAlertEntry.setUuid(uuid);

		commerceMLForecastAlertEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceMLForecastAlertEntry;
	}

	/**
	 * Removes the commerce ml forecast alert entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry that was removed
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry remove(
			long commerceMLForecastAlertEntryId)
		throws NoSuchMLForecastAlertEntryException {

		return remove((Serializable)commerceMLForecastAlertEntryId);
	}

	/**
	 * Removes the commerce ml forecast alert entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry that was removed
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry remove(Serializable primaryKey)
		throws NoSuchMLForecastAlertEntryException {

		Session session = null;

		try {
			session = openSession();

			CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
				(CommerceMLForecastAlertEntry)session.get(
					CommerceMLForecastAlertEntryImpl.class, primaryKey);

			if (commerceMLForecastAlertEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMLForecastAlertEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceMLForecastAlertEntry);
		}
		catch (NoSuchMLForecastAlertEntryException noSuchEntityException) {
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
	protected CommerceMLForecastAlertEntry removeImpl(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceMLForecastAlertEntry)) {
				commerceMLForecastAlertEntry =
					(CommerceMLForecastAlertEntry)session.get(
						CommerceMLForecastAlertEntryImpl.class,
						commerceMLForecastAlertEntry.getPrimaryKeyObj());
			}

			if (commerceMLForecastAlertEntry != null) {
				session.delete(commerceMLForecastAlertEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceMLForecastAlertEntry != null) {
			clearCache(commerceMLForecastAlertEntry);
		}

		return commerceMLForecastAlertEntry;
	}

	@Override
	public CommerceMLForecastAlertEntry updateImpl(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		boolean isNew = commerceMLForecastAlertEntry.isNew();

		if (!(commerceMLForecastAlertEntry instanceof
				CommerceMLForecastAlertEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceMLForecastAlertEntry.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceMLForecastAlertEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceMLForecastAlertEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceMLForecastAlertEntry implementation " +
					commerceMLForecastAlertEntry.getClass());
		}

		CommerceMLForecastAlertEntryModelImpl
			commerceMLForecastAlertEntryModelImpl =
				(CommerceMLForecastAlertEntryModelImpl)
					commerceMLForecastAlertEntry;

		if (Validator.isNull(commerceMLForecastAlertEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceMLForecastAlertEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceMLForecastAlertEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceMLForecastAlertEntry.setCreateDate(date);
			}
			else {
				commerceMLForecastAlertEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceMLForecastAlertEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceMLForecastAlertEntry.setModifiedDate(date);
			}
			else {
				commerceMLForecastAlertEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceMLForecastAlertEntry);
			}
			else {
				commerceMLForecastAlertEntry =
					(CommerceMLForecastAlertEntry)session.merge(
						commerceMLForecastAlertEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceMLForecastAlertEntryImpl.class,
			commerceMLForecastAlertEntryModelImpl, false, true);

		cacheUniqueFindersCache(commerceMLForecastAlertEntryModelImpl);

		if (isNew) {
			commerceMLForecastAlertEntry.setNew(false);
		}

		commerceMLForecastAlertEntry.resetOriginalValues();

		return commerceMLForecastAlertEntry;
	}

	/**
	 * Returns the commerce ml forecast alert entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByPrimaryKey(primaryKey);

		if (commerceMLForecastAlertEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMLForecastAlertEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceMLForecastAlertEntry;
	}

	/**
	 * Returns the commerce ml forecast alert entry with the primary key or throws a <code>NoSuchMLForecastAlertEntryException</code> if it could not be found.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByPrimaryKey(
			long commerceMLForecastAlertEntryId)
		throws NoSuchMLForecastAlertEntryException {

		return findByPrimaryKey((Serializable)commerceMLForecastAlertEntryId);
	}

	/**
	 * Returns the commerce ml forecast alert entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry, or <code>null</code> if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByPrimaryKey(
		long commerceMLForecastAlertEntryId) {

		return fetchByPrimaryKey((Serializable)commerceMLForecastAlertEntryId);
	}

	/**
	 * Returns all the commerce ml forecast alert entries.
	 *
	 * @return the commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
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

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEMLFORECASTALERTENTRY;

				sql = sql.concat(
					CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
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
	 * Removes all the commerce ml forecast alert entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				findAll()) {

			remove(commerceMLForecastAlertEntry);
		}
	}

	/**
	 * Returns the number of commerce ml forecast alert entries.
	 *
	 * @return the number of commerce ml forecast alert entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY);

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
		return "commerceMLForecastAlertEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEMLFORECASTALERTENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceMLForecastAlertEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce ml forecast alert entry persistence.
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

		_finderPathFetchByC_C_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "timestamp"}, true);

		_finderPathCountByC_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "timestamp"},
			false);

		_finderPathWithPaginationFindByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "status"}, true);

		_finderPathWithoutPaginationFindByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "status"}, true);

		_finderPathCountByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "status"}, false);

		_finderPathWithPaginationCountByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "status"}, false);

		_finderPathWithPaginationFindByC_C_GtRc_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_GtRc_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "commerceAccountId", "relativeChange", "status"
			},
			true);

		_finderPathWithPaginationCountByC_C_GtRc_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_GtRc_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName(), Integer.class.getName()
			},
			new String[] {
				"companyId", "commerceAccountId", "relativeChange", "status"
			},
			false);

		_finderPathWithPaginationFindByC_C_LtRc_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_LtRc_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "commerceAccountId", "relativeChange", "status"
			},
			true);

		_finderPathWithPaginationCountByC_C_LtRc_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_LtRc_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName(), Integer.class.getName()
			},
			new String[] {
				"companyId", "commerceAccountId", "relativeChange", "status"
			},
			false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceMLForecastAlertEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCEMLFORECASTALERTENTRY =
		"SELECT commerceMLForecastAlertEntry FROM CommerceMLForecastAlertEntry commerceMLForecastAlertEntry";

	private static final String _SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE =
		"SELECT commerceMLForecastAlertEntry FROM CommerceMLForecastAlertEntry commerceMLForecastAlertEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCEMLFORECASTALERTENTRY =
		"SELECT COUNT(commerceMLForecastAlertEntry) FROM CommerceMLForecastAlertEntry commerceMLForecastAlertEntry";

	private static final String _SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE =
		"SELECT COUNT(commerceMLForecastAlertEntry) FROM CommerceMLForecastAlertEntry commerceMLForecastAlertEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceMLForecastAlertEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceMLForecastAlertEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceMLForecastAlertEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLForecastAlertEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}