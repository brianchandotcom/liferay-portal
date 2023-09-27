/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service.persistence.impl;

import com.liferay.message.boards.exception.NoSuchSuspiciousActivityException;
import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.message.boards.model.MBSuspiciousActivityTable;
import com.liferay.message.boards.model.impl.MBSuspiciousActivityImpl;
import com.liferay.message.boards.model.impl.MBSuspiciousActivityModelImpl;
import com.liferay.message.boards.service.persistence.MBSuspiciousActivityPersistence;
import com.liferay.message.boards.service.persistence.MBSuspiciousActivityUtil;
import com.liferay.message.boards.service.persistence.impl.constants.MBPersistenceConstants;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

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
 * The persistence implementation for the message boards suspicious activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = MBSuspiciousActivityPersistence.class)
public class MBSuspiciousActivityPersistenceImpl
	extends BasePersistenceImpl<MBSuspiciousActivity>
	implements MBSuspiciousActivityPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MBSuspiciousActivityUtil</code> to access the message boards suspicious activity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MBSuspiciousActivityImpl.class.getName();

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
	 * Returns all the message boards suspicious activities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards suspicious activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

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

		List<MBSuspiciousActivity> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBSuspiciousActivity>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBSuspiciousActivity mbSuspiciousActivity : list) {
					if (!uuid.equals(mbSuspiciousActivity.getUuid())) {
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

			sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

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
				sb.append(MBSuspiciousActivityModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBSuspiciousActivity>)QueryUtil.list(
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
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByUuid_First(
			String uuid,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByUuid_First(
			uuid, orderByComparator);

		if (mbSuspiciousActivity != null) {
			return mbSuspiciousActivity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSuspiciousActivityException(sb.toString());
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByUuid_First(
		String uuid,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		List<MBSuspiciousActivity> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByUuid_Last(
			String uuid,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByUuid_Last(
			uuid, orderByComparator);

		if (mbSuspiciousActivity != null) {
			return mbSuspiciousActivity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSuspiciousActivityException(sb.toString());
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByUuid_Last(
		String uuid,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MBSuspiciousActivity> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	@Override
	public MBSuspiciousActivity[] findByUuid_PrevAndNext(
			long suspiciousActivityId, String uuid,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		uuid = Objects.toString(uuid, "");

		MBSuspiciousActivity mbSuspiciousActivity = findByPrimaryKey(
			suspiciousActivityId);

		Session session = null;

		try {
			session = openSession();

			MBSuspiciousActivity[] array = new MBSuspiciousActivityImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, mbSuspiciousActivity, uuid, orderByComparator, true);

			array[1] = mbSuspiciousActivity;

			array[2] = getByUuid_PrevAndNext(
				session, mbSuspiciousActivity, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBSuspiciousActivity getByUuid_PrevAndNext(
		Session session, MBSuspiciousActivity mbSuspiciousActivity, String uuid,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
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

		sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

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
			sb.append(MBSuspiciousActivityModelImpl.ORDER_BY_JPQL);
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
						mbSuspiciousActivity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBSuspiciousActivity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards suspicious activities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MBSuspiciousActivity mbSuspiciousActivity :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbSuspiciousActivity);
		}
	}

	/**
	 * Returns the number of message boards suspicious activities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message boards suspicious activities
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

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

			sb.append(_SQL_COUNT_MBSUSPICIOUSACTIVITY_WHERE);

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
		"mbSuspiciousActivity.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(mbSuspiciousActivity.uuid IS NULL OR mbSuspiciousActivity.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the message boards suspicious activity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByUUID_G(String uuid, long groupId)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByUUID_G(
			uuid, groupId);

		if (mbSuspiciousActivity == null) {
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

			throw new NoSuchSuspiciousActivityException(sb.toString());
		}

		return mbSuspiciousActivity;
	}

	/**
	 * Returns the message boards suspicious activity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the message boards suspicious activity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByUUID_G(
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

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

		if (result instanceof MBSuspiciousActivity) {
			MBSuspiciousActivity mbSuspiciousActivity =
				(MBSuspiciousActivity)result;

			if (!Objects.equals(uuid, mbSuspiciousActivity.getUuid()) ||
				(groupId != mbSuspiciousActivity.getGroupId())) {

				result = null;
			}
			else if (!ctPersistenceHelper.isProductionMode(
						MBSuspiciousActivity.class,
						mbSuspiciousActivity.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

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

				List<MBSuspiciousActivity> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					MBSuspiciousActivity mbSuspiciousActivity = list.get(0);

					result = mbSuspiciousActivity;

					cacheResult(mbSuspiciousActivity);
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
			return (MBSuspiciousActivity)result;
		}
	}

	/**
	 * Removes the message boards suspicious activity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message boards suspicious activity that was removed
	 */
	@Override
	public MBSuspiciousActivity removeByUUID_G(String uuid, long groupId)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = findByUUID_G(uuid, groupId);

		return remove(mbSuspiciousActivity);
	}

	/**
	 * Returns the number of message boards suspicious activities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message boards suspicious activities
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

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

			sb.append(_SQL_COUNT_MBSUSPICIOUSACTIVITY_WHERE);

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
		"mbSuspiciousActivity.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(mbSuspiciousActivity.uuid IS NULL OR mbSuspiciousActivity.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"mbSuspiciousActivity.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

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

		List<MBSuspiciousActivity> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBSuspiciousActivity>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBSuspiciousActivity mbSuspiciousActivity : list) {
					if (!uuid.equals(mbSuspiciousActivity.getUuid()) ||
						(companyId != mbSuspiciousActivity.getCompanyId())) {

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

			sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

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
				sb.append(MBSuspiciousActivityModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBSuspiciousActivity>)QueryUtil.list(
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
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (mbSuspiciousActivity != null) {
			return mbSuspiciousActivity;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSuspiciousActivityException(sb.toString());
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		List<MBSuspiciousActivity> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (mbSuspiciousActivity != null) {
			return mbSuspiciousActivity;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSuspiciousActivityException(sb.toString());
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MBSuspiciousActivity> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	@Override
	public MBSuspiciousActivity[] findByUuid_C_PrevAndNext(
			long suspiciousActivityId, String uuid, long companyId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		uuid = Objects.toString(uuid, "");

		MBSuspiciousActivity mbSuspiciousActivity = findByPrimaryKey(
			suspiciousActivityId);

		Session session = null;

		try {
			session = openSession();

			MBSuspiciousActivity[] array = new MBSuspiciousActivityImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, mbSuspiciousActivity, uuid, companyId,
				orderByComparator, true);

			array[1] = mbSuspiciousActivity;

			array[2] = getByUuid_C_PrevAndNext(
				session, mbSuspiciousActivity, uuid, companyId,
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

	protected MBSuspiciousActivity getByUuid_C_PrevAndNext(
		Session session, MBSuspiciousActivity mbSuspiciousActivity, String uuid,
		long companyId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
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

		sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

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
			sb.append(MBSuspiciousActivityModelImpl.ORDER_BY_JPQL);
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
						mbSuspiciousActivity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBSuspiciousActivity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards suspicious activities where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (MBSuspiciousActivity mbSuspiciousActivity :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mbSuspiciousActivity);
		}
	}

	/**
	 * Returns the number of message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message boards suspicious activities
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

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

			sb.append(_SQL_COUNT_MBSUSPICIOUSACTIVITY_WHERE);

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
		"mbSuspiciousActivity.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(mbSuspiciousActivity.uuid IS NULL OR mbSuspiciousActivity.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"mbSuspiciousActivity.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByMessageId;
	private FinderPath _finderPathWithoutPaginationFindByMessageId;
	private FinderPath _finderPathCountByMessageId;

	/**
	 * Returns all the message boards suspicious activities where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByMessageId(long messageId) {
		return findByMessageId(
			messageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards suspicious activities where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByMessageId(
		long messageId, int start, int end) {

		return findByMessageId(messageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByMessageId(
		long messageId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return findByMessageId(messageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByMessageId(
		long messageId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByMessageId;
				finderArgs = new Object[] {messageId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByMessageId;
			finderArgs = new Object[] {
				messageId, start, end, orderByComparator
			};
		}

		List<MBSuspiciousActivity> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBSuspiciousActivity>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBSuspiciousActivity mbSuspiciousActivity : list) {
					if (messageId != mbSuspiciousActivity.getMessageId()) {
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

			sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

			sb.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBSuspiciousActivityModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(messageId);

				list = (List<MBSuspiciousActivity>)QueryUtil.list(
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
	 * Returns the first message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByMessageId_First(
			long messageId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByMessageId_First(
			messageId, orderByComparator);

		if (mbSuspiciousActivity != null) {
			return mbSuspiciousActivity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("messageId=");
		sb.append(messageId);

		sb.append("}");

		throw new NoSuchSuspiciousActivityException(sb.toString());
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByMessageId_First(
		long messageId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		List<MBSuspiciousActivity> list = findByMessageId(
			messageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByMessageId_Last(
			long messageId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByMessageId_Last(
			messageId, orderByComparator);

		if (mbSuspiciousActivity != null) {
			return mbSuspiciousActivity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("messageId=");
		sb.append(messageId);

		sb.append("}");

		throw new NoSuchSuspiciousActivityException(sb.toString());
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByMessageId_Last(
		long messageId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		int count = countByMessageId(messageId);

		if (count == 0) {
			return null;
		}

		List<MBSuspiciousActivity> list = findByMessageId(
			messageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	@Override
	public MBSuspiciousActivity[] findByMessageId_PrevAndNext(
			long suspiciousActivityId, long messageId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = findByPrimaryKey(
			suspiciousActivityId);

		Session session = null;

		try {
			session = openSession();

			MBSuspiciousActivity[] array = new MBSuspiciousActivityImpl[3];

			array[0] = getByMessageId_PrevAndNext(
				session, mbSuspiciousActivity, messageId, orderByComparator,
				true);

			array[1] = mbSuspiciousActivity;

			array[2] = getByMessageId_PrevAndNext(
				session, mbSuspiciousActivity, messageId, orderByComparator,
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

	protected MBSuspiciousActivity getByMessageId_PrevAndNext(
		Session session, MBSuspiciousActivity mbSuspiciousActivity,
		long messageId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
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

		sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

		sb.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

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
			sb.append(MBSuspiciousActivityModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(messageId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						mbSuspiciousActivity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBSuspiciousActivity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards suspicious activities where messageId = &#63; from the database.
	 *
	 * @param messageId the message ID
	 */
	@Override
	public void removeByMessageId(long messageId) {
		for (MBSuspiciousActivity mbSuspiciousActivity :
				findByMessageId(
					messageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbSuspiciousActivity);
		}
	}

	/**
	 * Returns the number of message boards suspicious activities where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the number of matching message boards suspicious activities
	 */
	@Override
	public int countByMessageId(long messageId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByMessageId;

			finderArgs = new Object[] {messageId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MBSUSPICIOUSACTIVITY_WHERE);

			sb.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(messageId);

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

	private static final String _FINDER_COLUMN_MESSAGEID_MESSAGEID_2 =
		"mbSuspiciousActivity.messageId = ?";

	private FinderPath _finderPathWithPaginationFindByThreadId;
	private FinderPath _finderPathWithoutPaginationFindByThreadId;
	private FinderPath _finderPathCountByThreadId;

	/**
	 * Returns all the message boards suspicious activities where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByThreadId(long threadId) {
		return findByThreadId(
			threadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards suspicious activities where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByThreadId(
		long threadId, int start, int end) {

		return findByThreadId(threadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByThreadId(
		long threadId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return findByThreadId(threadId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findByThreadId(
		long threadId, int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByThreadId;
				finderArgs = new Object[] {threadId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByThreadId;
			finderArgs = new Object[] {threadId, start, end, orderByComparator};
		}

		List<MBSuspiciousActivity> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBSuspiciousActivity>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBSuspiciousActivity mbSuspiciousActivity : list) {
					if (threadId != mbSuspiciousActivity.getThreadId()) {
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

			sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

			sb.append(_FINDER_COLUMN_THREADID_THREADID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBSuspiciousActivityModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(threadId);

				list = (List<MBSuspiciousActivity>)QueryUtil.list(
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
	 * Returns the first message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByThreadId_First(
			long threadId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByThreadId_First(
			threadId, orderByComparator);

		if (mbSuspiciousActivity != null) {
			return mbSuspiciousActivity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("threadId=");
		sb.append(threadId);

		sb.append("}");

		throw new NoSuchSuspiciousActivityException(sb.toString());
	}

	/**
	 * Returns the first message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByThreadId_First(
		long threadId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		List<MBSuspiciousActivity> list = findByThreadId(
			threadId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByThreadId_Last(
			long threadId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByThreadId_Last(
			threadId, orderByComparator);

		if (mbSuspiciousActivity != null) {
			return mbSuspiciousActivity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("threadId=");
		sb.append(threadId);

		sb.append("}");

		throw new NoSuchSuspiciousActivityException(sb.toString());
	}

	/**
	 * Returns the last message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByThreadId_Last(
		long threadId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		int count = countByThreadId(threadId);

		if (count == 0) {
			return null;
		}

		List<MBSuspiciousActivity> list = findByThreadId(
			threadId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	@Override
	public MBSuspiciousActivity[] findByThreadId_PrevAndNext(
			long suspiciousActivityId, long threadId,
			OrderByComparator<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = findByPrimaryKey(
			suspiciousActivityId);

		Session session = null;

		try {
			session = openSession();

			MBSuspiciousActivity[] array = new MBSuspiciousActivityImpl[3];

			array[0] = getByThreadId_PrevAndNext(
				session, mbSuspiciousActivity, threadId, orderByComparator,
				true);

			array[1] = mbSuspiciousActivity;

			array[2] = getByThreadId_PrevAndNext(
				session, mbSuspiciousActivity, threadId, orderByComparator,
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

	protected MBSuspiciousActivity getByThreadId_PrevAndNext(
		Session session, MBSuspiciousActivity mbSuspiciousActivity,
		long threadId,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
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

		sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

		sb.append(_FINDER_COLUMN_THREADID_THREADID_2);

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
			sb.append(MBSuspiciousActivityModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(threadId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						mbSuspiciousActivity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBSuspiciousActivity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards suspicious activities where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 */
	@Override
	public void removeByThreadId(long threadId) {
		for (MBSuspiciousActivity mbSuspiciousActivity :
				findByThreadId(
					threadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbSuspiciousActivity);
		}
	}

	/**
	 * Returns the number of message boards suspicious activities where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message boards suspicious activities
	 */
	@Override
	public int countByThreadId(long threadId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByThreadId;

			finderArgs = new Object[] {threadId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MBSUSPICIOUSACTIVITY_WHERE);

			sb.append(_FINDER_COLUMN_THREADID_THREADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(threadId);

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

	private static final String _FINDER_COLUMN_THREADID_THREADID_2 =
		"mbSuspiciousActivity.threadId = ?";

	private FinderPath _finderPathFetchByU_M;
	private FinderPath _finderPathCountByU_M;

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and messageId = &#63; or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByU_M(long userId, long messageId)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByU_M(
			userId, messageId);

		if (mbSuspiciousActivity == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", messageId=");
			sb.append(messageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSuspiciousActivityException(sb.toString());
		}

		return mbSuspiciousActivity;
	}

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and messageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByU_M(long userId, long messageId) {
		return fetchByU_M(userId, messageId, true);
	}

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and messageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByU_M(
		long userId, long messageId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, messageId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByU_M, finderArgs, this);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

		if (result instanceof MBSuspiciousActivity) {
			MBSuspiciousActivity mbSuspiciousActivity =
				(MBSuspiciousActivity)result;

			if ((userId != mbSuspiciousActivity.getUserId()) ||
				(messageId != mbSuspiciousActivity.getMessageId())) {

				result = null;
			}
			else if (!ctPersistenceHelper.isProductionMode(
						MBSuspiciousActivity.class,
						mbSuspiciousActivity.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

			sb.append(_FINDER_COLUMN_U_M_USERID_2);

			sb.append(_FINDER_COLUMN_U_M_MESSAGEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(messageId);

				List<MBSuspiciousActivity> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByU_M, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {userId, messageId};
							}

							_log.warn(
								"MBSuspiciousActivityPersistenceImpl.fetchByU_M(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					MBSuspiciousActivity mbSuspiciousActivity = list.get(0);

					result = mbSuspiciousActivity;

					cacheResult(mbSuspiciousActivity);
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
			return (MBSuspiciousActivity)result;
		}
	}

	/**
	 * Removes the message boards suspicious activity where userId = &#63; and messageId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the message boards suspicious activity that was removed
	 */
	@Override
	public MBSuspiciousActivity removeByU_M(long userId, long messageId)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = findByU_M(
			userId, messageId);

		return remove(mbSuspiciousActivity);
	}

	/**
	 * Returns the number of message boards suspicious activities where userId = &#63; and messageId = &#63;.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the number of matching message boards suspicious activities
	 */
	@Override
	public int countByU_M(long userId, long messageId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_M;

			finderArgs = new Object[] {userId, messageId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MBSUSPICIOUSACTIVITY_WHERE);

			sb.append(_FINDER_COLUMN_U_M_USERID_2);

			sb.append(_FINDER_COLUMN_U_M_MESSAGEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(messageId);

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

	private static final String _FINDER_COLUMN_U_M_USERID_2 =
		"mbSuspiciousActivity.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_M_MESSAGEID_2 =
		"mbSuspiciousActivity.messageId = ?";

	private FinderPath _finderPathFetchByU_T;
	private FinderPath _finderPathCountByU_T;

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and threadId = &#63; or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity findByU_T(long userId, long threadId)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByU_T(
			userId, threadId);

		if (mbSuspiciousActivity == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", threadId=");
			sb.append(threadId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSuspiciousActivityException(sb.toString());
		}

		return mbSuspiciousActivity;
	}

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByU_T(long userId, long threadId) {
		return fetchByU_T(userId, threadId, true);
	}

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByU_T(
		long userId, long threadId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, threadId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByU_T, finderArgs, this);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

		if (result instanceof MBSuspiciousActivity) {
			MBSuspiciousActivity mbSuspiciousActivity =
				(MBSuspiciousActivity)result;

			if ((userId != mbSuspiciousActivity.getUserId()) ||
				(threadId != mbSuspiciousActivity.getThreadId())) {

				result = null;
			}
			else if (!ctPersistenceHelper.isProductionMode(
						MBSuspiciousActivity.class,
						mbSuspiciousActivity.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE);

			sb.append(_FINDER_COLUMN_U_T_USERID_2);

			sb.append(_FINDER_COLUMN_U_T_THREADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(threadId);

				List<MBSuspiciousActivity> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByU_T, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {userId, threadId};
							}

							_log.warn(
								"MBSuspiciousActivityPersistenceImpl.fetchByU_T(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					MBSuspiciousActivity mbSuspiciousActivity = list.get(0);

					result = mbSuspiciousActivity;

					cacheResult(mbSuspiciousActivity);
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
			return (MBSuspiciousActivity)result;
		}
	}

	/**
	 * Removes the message boards suspicious activity where userId = &#63; and threadId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the message boards suspicious activity that was removed
	 */
	@Override
	public MBSuspiciousActivity removeByU_T(long userId, long threadId)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = findByU_T(userId, threadId);

		return remove(mbSuspiciousActivity);
	}

	/**
	 * Returns the number of message boards suspicious activities where userId = &#63; and threadId = &#63;.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the number of matching message boards suspicious activities
	 */
	@Override
	public int countByU_T(long userId, long threadId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_T;

			finderArgs = new Object[] {userId, threadId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MBSUSPICIOUSACTIVITY_WHERE);

			sb.append(_FINDER_COLUMN_U_T_USERID_2);

			sb.append(_FINDER_COLUMN_U_T_THREADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(threadId);

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

	private static final String _FINDER_COLUMN_U_T_USERID_2 =
		"mbSuspiciousActivity.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_T_THREADID_2 =
		"mbSuspiciousActivity.threadId = ?";

	public MBSuspiciousActivityPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(MBSuspiciousActivity.class);

		setModelImplClass(MBSuspiciousActivityImpl.class);
		setModelPKClass(long.class);

		setTable(MBSuspiciousActivityTable.INSTANCE);
	}

	/**
	 * Caches the message boards suspicious activity in the entity cache if it is enabled.
	 *
	 * @param mbSuspiciousActivity the message boards suspicious activity
	 */
	@Override
	public void cacheResult(MBSuspiciousActivity mbSuspiciousActivity) {
		if (mbSuspiciousActivity.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			MBSuspiciousActivityImpl.class,
			mbSuspiciousActivity.getPrimaryKey(), mbSuspiciousActivity);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				mbSuspiciousActivity.getUuid(),
				mbSuspiciousActivity.getGroupId()
			},
			mbSuspiciousActivity);

		finderCache.putResult(
			_finderPathFetchByU_M,
			new Object[] {
				mbSuspiciousActivity.getUserId(),
				mbSuspiciousActivity.getMessageId()
			},
			mbSuspiciousActivity);

		finderCache.putResult(
			_finderPathFetchByU_T,
			new Object[] {
				mbSuspiciousActivity.getUserId(),
				mbSuspiciousActivity.getThreadId()
			},
			mbSuspiciousActivity);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the message boards suspicious activities in the entity cache if it is enabled.
	 *
	 * @param mbSuspiciousActivities the message boards suspicious activities
	 */
	@Override
	public void cacheResult(List<MBSuspiciousActivity> mbSuspiciousActivities) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (mbSuspiciousActivities.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (MBSuspiciousActivity mbSuspiciousActivity :
				mbSuspiciousActivities) {

			if (mbSuspiciousActivity.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					MBSuspiciousActivityImpl.class,
					mbSuspiciousActivity.getPrimaryKey()) == null) {

				cacheResult(mbSuspiciousActivity);
			}
		}
	}

	/**
	 * Clears the cache for all message boards suspicious activities.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MBSuspiciousActivityImpl.class);

		finderCache.clearCache(MBSuspiciousActivityImpl.class);
	}

	/**
	 * Clears the cache for the message boards suspicious activity.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBSuspiciousActivity mbSuspiciousActivity) {
		entityCache.removeResult(
			MBSuspiciousActivityImpl.class, mbSuspiciousActivity);
	}

	@Override
	public void clearCache(List<MBSuspiciousActivity> mbSuspiciousActivities) {
		for (MBSuspiciousActivity mbSuspiciousActivity :
				mbSuspiciousActivities) {

			entityCache.removeResult(
				MBSuspiciousActivityImpl.class, mbSuspiciousActivity);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(MBSuspiciousActivityImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				MBSuspiciousActivityImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MBSuspiciousActivityModelImpl mbSuspiciousActivityModelImpl) {

		Object[] args = new Object[] {
			mbSuspiciousActivityModelImpl.getUuid(),
			mbSuspiciousActivityModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, mbSuspiciousActivityModelImpl);

		args = new Object[] {
			mbSuspiciousActivityModelImpl.getUserId(),
			mbSuspiciousActivityModelImpl.getMessageId()
		};

		finderCache.putResult(_finderPathCountByU_M, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByU_M, args, mbSuspiciousActivityModelImpl);

		args = new Object[] {
			mbSuspiciousActivityModelImpl.getUserId(),
			mbSuspiciousActivityModelImpl.getThreadId()
		};

		finderCache.putResult(_finderPathCountByU_T, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByU_T, args, mbSuspiciousActivityModelImpl);
	}

	/**
	 * Creates a new message boards suspicious activity with the primary key. Does not add the message boards suspicious activity to the database.
	 *
	 * @param suspiciousActivityId the primary key for the new message boards suspicious activity
	 * @return the new message boards suspicious activity
	 */
	@Override
	public MBSuspiciousActivity create(long suspiciousActivityId) {
		MBSuspiciousActivity mbSuspiciousActivity =
			new MBSuspiciousActivityImpl();

		mbSuspiciousActivity.setNew(true);
		mbSuspiciousActivity.setPrimaryKey(suspiciousActivityId);

		String uuid = _portalUUID.generate();

		mbSuspiciousActivity.setUuid(uuid);

		mbSuspiciousActivity.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mbSuspiciousActivity;
	}

	/**
	 * Removes the message boards suspicious activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param suspiciousActivityId the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity that was removed
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	@Override
	public MBSuspiciousActivity remove(long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException {

		return remove((Serializable)suspiciousActivityId);
	}

	/**
	 * Removes the message boards suspicious activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity that was removed
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	@Override
	public MBSuspiciousActivity remove(Serializable primaryKey)
		throws NoSuchSuspiciousActivityException {

		Session session = null;

		try {
			session = openSession();

			MBSuspiciousActivity mbSuspiciousActivity =
				(MBSuspiciousActivity)session.get(
					MBSuspiciousActivityImpl.class, primaryKey);

			if (mbSuspiciousActivity == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSuspiciousActivityException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mbSuspiciousActivity);
		}
		catch (NoSuchSuspiciousActivityException noSuchEntityException) {
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
	protected MBSuspiciousActivity removeImpl(
		MBSuspiciousActivity mbSuspiciousActivity) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mbSuspiciousActivity)) {
				mbSuspiciousActivity = (MBSuspiciousActivity)session.get(
					MBSuspiciousActivityImpl.class,
					mbSuspiciousActivity.getPrimaryKeyObj());
			}

			if ((mbSuspiciousActivity != null) &&
				ctPersistenceHelper.isRemove(mbSuspiciousActivity)) {

				session.delete(mbSuspiciousActivity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mbSuspiciousActivity != null) {
			clearCache(mbSuspiciousActivity);
		}

		return mbSuspiciousActivity;
	}

	@Override
	public MBSuspiciousActivity updateImpl(
		MBSuspiciousActivity mbSuspiciousActivity) {

		boolean isNew = mbSuspiciousActivity.isNew();

		if (!(mbSuspiciousActivity instanceof MBSuspiciousActivityModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mbSuspiciousActivity.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					mbSuspiciousActivity);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mbSuspiciousActivity proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MBSuspiciousActivity implementation " +
					mbSuspiciousActivity.getClass());
		}

		MBSuspiciousActivityModelImpl mbSuspiciousActivityModelImpl =
			(MBSuspiciousActivityModelImpl)mbSuspiciousActivity;

		if (Validator.isNull(mbSuspiciousActivity.getUuid())) {
			String uuid = _portalUUID.generate();

			mbSuspiciousActivity.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (mbSuspiciousActivity.getCreateDate() == null)) {
			if (serviceContext == null) {
				mbSuspiciousActivity.setCreateDate(date);
			}
			else {
				mbSuspiciousActivity.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!mbSuspiciousActivityModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mbSuspiciousActivity.setModifiedDate(date);
			}
			else {
				mbSuspiciousActivity.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(mbSuspiciousActivity)) {
				if (!isNew) {
					session.evict(
						MBSuspiciousActivityImpl.class,
						mbSuspiciousActivity.getPrimaryKeyObj());
				}

				session.save(mbSuspiciousActivity);
			}
			else {
				mbSuspiciousActivity = (MBSuspiciousActivity)session.merge(
					mbSuspiciousActivity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mbSuspiciousActivity.getCtCollectionId() != 0) {
			if (isNew) {
				mbSuspiciousActivity.setNew(false);
			}

			mbSuspiciousActivity.resetOriginalValues();

			return mbSuspiciousActivity;
		}

		entityCache.putResult(
			MBSuspiciousActivityImpl.class, mbSuspiciousActivityModelImpl,
			false, true);

		cacheUniqueFindersCache(mbSuspiciousActivityModelImpl);

		if (isNew) {
			mbSuspiciousActivity.setNew(false);
		}

		mbSuspiciousActivity.resetOriginalValues();

		return mbSuspiciousActivity;
	}

	/**
	 * Returns the message boards suspicious activity with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	@Override
	public MBSuspiciousActivity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSuspiciousActivityException {

		MBSuspiciousActivity mbSuspiciousActivity = fetchByPrimaryKey(
			primaryKey);

		if (mbSuspiciousActivity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSuspiciousActivityException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mbSuspiciousActivity;
	}

	/**
	 * Returns the message boards suspicious activity with the primary key or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param suspiciousActivityId the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	@Override
	public MBSuspiciousActivity findByPrimaryKey(long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException {

		return findByPrimaryKey((Serializable)suspiciousActivityId);
	}

	/**
	 * Returns the message boards suspicious activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity, or <code>null</code> if a message boards suspicious activity with the primary key could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				MBSuspiciousActivity.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		MBSuspiciousActivity mbSuspiciousActivity = null;

		Session session = null;

		try {
			session = openSession();

			mbSuspiciousActivity = (MBSuspiciousActivity)session.get(
				MBSuspiciousActivityImpl.class, primaryKey);

			if (mbSuspiciousActivity != null) {
				cacheResult(mbSuspiciousActivity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return mbSuspiciousActivity;
	}

	/**
	 * Returns the message boards suspicious activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param suspiciousActivityId the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity, or <code>null</code> if a message boards suspicious activity with the primary key could not be found
	 */
	@Override
	public MBSuspiciousActivity fetchByPrimaryKey(long suspiciousActivityId) {
		return fetchByPrimaryKey((Serializable)suspiciousActivityId);
	}

	@Override
	public Map<Serializable, MBSuspiciousActivity> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(MBSuspiciousActivity.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MBSuspiciousActivity> map =
			new HashMap<Serializable, MBSuspiciousActivity>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MBSuspiciousActivity mbSuspiciousActivity = fetchByPrimaryKey(
				primaryKey);

			if (mbSuspiciousActivity != null) {
				map.put(primaryKey, mbSuspiciousActivity);
			}

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

			for (MBSuspiciousActivity mbSuspiciousActivity :
					(List<MBSuspiciousActivity>)query.list()) {

				map.put(
					mbSuspiciousActivity.getPrimaryKeyObj(),
					mbSuspiciousActivity);

				cacheResult(mbSuspiciousActivity);
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
	 * Returns all the message boards suspicious activities.
	 *
	 * @return the message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards suspicious activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findAll(
		int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards suspicious activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message boards suspicious activities
	 */
	@Override
	public List<MBSuspiciousActivity> findAll(
		int start, int end,
		OrderByComparator<MBSuspiciousActivity> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

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

		List<MBSuspiciousActivity> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBSuspiciousActivity>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MBSUSPICIOUSACTIVITY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MBSUSPICIOUSACTIVITY;

				sql = sql.concat(MBSuspiciousActivityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MBSuspiciousActivity>)QueryUtil.list(
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
	 * Removes all the message boards suspicious activities from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MBSuspiciousActivity mbSuspiciousActivity : findAll()) {
			remove(mbSuspiciousActivity);
		}
	}

	/**
	 * Returns the number of message boards suspicious activities.
	 *
	 * @return the number of message boards suspicious activities
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBSuspiciousActivity.class);

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
					_SQL_COUNT_MBSUSPICIOUSACTIVITY);

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
		return "suspiciousActivityId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MBSUSPICIOUSACTIVITY;
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
		return MBSuspiciousActivityModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "MBSuspiciousActivity";
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
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("messageId");
		ctStrictColumnNames.add("threadId");
		ctStrictColumnNames.add("reason");
		ctStrictColumnNames.add("validated");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("suspiciousActivityId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the message boards suspicious activity persistence.
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

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

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

		_finderPathWithPaginationFindByMessageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByMessageId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"messageId"}, true);

		_finderPathWithoutPaginationFindByMessageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByMessageId",
			new String[] {Long.class.getName()}, new String[] {"messageId"},
			true);

		_finderPathCountByMessageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMessageId",
			new String[] {Long.class.getName()}, new String[] {"messageId"},
			false);

		_finderPathWithPaginationFindByThreadId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByThreadId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"threadId"}, true);

		_finderPathWithoutPaginationFindByThreadId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByThreadId",
			new String[] {Long.class.getName()}, new String[] {"threadId"},
			true);

		_finderPathCountByThreadId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByThreadId",
			new String[] {Long.class.getName()}, new String[] {"threadId"},
			false);

		_finderPathFetchByU_M = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_M",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "messageId"}, true);

		_finderPathCountByU_M = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_M",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "messageId"}, false);

		_finderPathFetchByU_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_T",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "threadId"}, true);

		_finderPathCountByU_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "threadId"}, false);

		MBSuspiciousActivityUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		MBSuspiciousActivityUtil.setPersistence(null);

		entityCache.removeCache(MBSuspiciousActivityImpl.class.getName());
	}

	@Override
	@Reference(
		target = MBPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_MBSUSPICIOUSACTIVITY =
		"SELECT mbSuspiciousActivity FROM MBSuspiciousActivity mbSuspiciousActivity";

	private static final String _SQL_SELECT_MBSUSPICIOUSACTIVITY_WHERE =
		"SELECT mbSuspiciousActivity FROM MBSuspiciousActivity mbSuspiciousActivity WHERE ";

	private static final String _SQL_COUNT_MBSUSPICIOUSACTIVITY =
		"SELECT COUNT(mbSuspiciousActivity) FROM MBSuspiciousActivity mbSuspiciousActivity";

	private static final String _SQL_COUNT_MBSUSPICIOUSACTIVITY_WHERE =
		"SELECT COUNT(mbSuspiciousActivity) FROM MBSuspiciousActivity mbSuspiciousActivity WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"mbSuspiciousActivity.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MBSuspiciousActivity exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MBSuspiciousActivity exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MBSuspiciousActivityPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}