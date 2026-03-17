/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.persistence.impl;

import com.liferay.launch.exception.DuplicateLaunchSetExternalReferenceCodeException;
import com.liferay.launch.exception.NoSuchLaunchSetException;
import com.liferay.launch.model.LaunchSet;
import com.liferay.launch.model.LaunchSetTable;
import com.liferay.launch.model.impl.LaunchSetImpl;
import com.liferay.launch.model.impl.LaunchSetModelImpl;
import com.liferay.launch.service.persistence.LaunchSetPersistence;
import com.liferay.launch.service.persistence.LaunchSetUtil;
import com.liferay.launch.service.persistence.impl.constants.LaunchPersistenceConstants;
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
 * The persistence implementation for the launch set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LaunchSetPersistence.class)
public class LaunchSetPersistenceImpl
	extends BasePersistenceImpl<LaunchSet> implements LaunchSetPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LaunchSetUtil</code> to access the launch set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LaunchSetImpl.class.getName();

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
	 * Returns all the launch sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching launch sets
	 */
	@Override
	public List<LaunchSet> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @return the range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
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

		List<LaunchSet> list = null;

		if (useFinderCache) {
			list = (List<LaunchSet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LaunchSet launchSet : list) {
					if (!uuid.equals(launchSet.getUuid())) {
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

			sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

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
				sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
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

				list = (List<LaunchSet>)QueryUtil.list(
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
	 * Returns the first launch set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByUuid_First(
			String uuid, OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByUuid_First(uuid, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the first launch set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByUuid_First(
		String uuid, OrderByComparator<LaunchSet> orderByComparator) {

		List<LaunchSet> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last launch set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByUuid_Last(
			String uuid, OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByUuid_Last(uuid, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the last launch set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByUuid_Last(
		String uuid, OrderByComparator<LaunchSet> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LaunchSet> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the launch sets before and after the current launch set in the ordered set where uuid = &#63;.
	 *
	 * @param launchSetId the primary key of the current launch set
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next launch set
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet[] findByUuid_PrevAndNext(
			long launchSetId, String uuid,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		uuid = Objects.toString(uuid, "");

		LaunchSet launchSet = findByPrimaryKey(launchSetId);

		Session session = null;

		try {
			session = openSession();

			LaunchSet[] array = new LaunchSetImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, launchSet, uuid, orderByComparator, true);

			array[1] = launchSet;

			array[2] = getByUuid_PrevAndNext(
				session, launchSet, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LaunchSet getByUuid_PrevAndNext(
		Session session, LaunchSet launchSet, String uuid,
		OrderByComparator<LaunchSet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

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
			sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(launchSet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LaunchSet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the launch sets where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LaunchSet launchSet :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(launchSet);
		}
	}

	/**
	 * Returns the number of launch sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAUNCHSET_WHERE);

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
		"launchSet.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(launchSet.uuid IS NULL OR launchSet.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the launch sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching launch sets
	 */
	@Override
	public List<LaunchSet> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @return the range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
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

		List<LaunchSet> list = null;

		if (useFinderCache) {
			list = (List<LaunchSet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LaunchSet launchSet : list) {
					if (!uuid.equals(launchSet.getUuid()) ||
						(companyId != launchSet.getCompanyId())) {

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

			sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

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
				sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
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

				list = (List<LaunchSet>)QueryUtil.list(
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
	 * Returns the first launch set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the first launch set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LaunchSet> orderByComparator) {

		List<LaunchSet> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last launch set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the last launch set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LaunchSet> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LaunchSet> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the launch sets before and after the current launch set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param launchSetId the primary key of the current launch set
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next launch set
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet[] findByUuid_C_PrevAndNext(
			long launchSetId, String uuid, long companyId,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		uuid = Objects.toString(uuid, "");

		LaunchSet launchSet = findByPrimaryKey(launchSetId);

		Session session = null;

		try {
			session = openSession();

			LaunchSet[] array = new LaunchSetImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, launchSet, uuid, companyId, orderByComparator, true);

			array[1] = launchSet;

			array[2] = getByUuid_C_PrevAndNext(
				session, launchSet, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LaunchSet getByUuid_C_PrevAndNext(
		Session session, LaunchSet launchSet, String uuid, long companyId,
		OrderByComparator<LaunchSet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

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
			sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(launchSet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LaunchSet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the launch sets where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LaunchSet launchSet :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(launchSet);
		}
	}

	/**
	 * Returns the number of launch sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAUNCHSET_WHERE);

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
		"launchSet.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(launchSet.uuid IS NULL OR launchSet.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"launchSet.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the launch sets where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching launch sets
	 */
	@Override
	public List<LaunchSet> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch sets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @return the range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<LaunchSet> list = null;

		if (useFinderCache) {
			list = (List<LaunchSet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LaunchSet launchSet : list) {
					if (companyId != launchSet.getCompanyId()) {
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

			sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<LaunchSet>)QueryUtil.list(
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
	 * Returns the first launch set in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByCompanyId_First(
			long companyId, OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the first launch set in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByCompanyId_First(
		long companyId, OrderByComparator<LaunchSet> orderByComparator) {

		List<LaunchSet> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last launch set in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByCompanyId_Last(
			long companyId, OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the last launch set in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByCompanyId_Last(
		long companyId, OrderByComparator<LaunchSet> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<LaunchSet> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the launch sets before and after the current launch set in the ordered set where companyId = &#63;.
	 *
	 * @param launchSetId the primary key of the current launch set
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next launch set
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet[] findByCompanyId_PrevAndNext(
			long launchSetId, long companyId,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = findByPrimaryKey(launchSetId);

		Session session = null;

		try {
			session = openSession();

			LaunchSet[] array = new LaunchSetImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, launchSet, companyId, orderByComparator, true);

			array[1] = launchSet;

			array[2] = getByCompanyId_PrevAndNext(
				session, launchSet, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LaunchSet getByCompanyId_PrevAndNext(
		Session session, LaunchSet launchSet, long companyId,
		OrderByComparator<LaunchSet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(launchSet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LaunchSet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the launch sets where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (LaunchSet launchSet :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(launchSet);
		}
	}

	/**
	 * Returns the number of launch sets where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAUNCHSET_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"launchSet.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_U;
	private FinderPath _finderPathWithoutPaginationFindByC_U;
	private FinderPath _finderPathCountByC_U;

	/**
	 * Returns all the launch sets where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_U(long companyId, long userId) {
		return findByC_U(
			companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch sets where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @return the range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_U(
		long companyId, long userId, int start, int end) {

		return findByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator) {

		return findByC_U(
			companyId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_U;
				finderArgs = new Object[] {companyId, userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_U;
			finderArgs = new Object[] {
				companyId, userId, start, end, orderByComparator
			};
		}

		List<LaunchSet> list = null;

		if (useFinderCache) {
			list = (List<LaunchSet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LaunchSet launchSet : list) {
					if ((companyId != launchSet.getCompanyId()) ||
						(userId != launchSet.getUserId())) {

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

			sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

				list = (List<LaunchSet>)QueryUtil.list(
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
	 * Returns the first launch set in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByC_U_First(
			long companyId, long userId,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByC_U_First(
			companyId, userId, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the first launch set in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByC_U_First(
		long companyId, long userId,
		OrderByComparator<LaunchSet> orderByComparator) {

		List<LaunchSet> list = findByC_U(
			companyId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last launch set in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByC_U_Last(
			long companyId, long userId,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByC_U_Last(
			companyId, userId, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the last launch set in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByC_U_Last(
		long companyId, long userId,
		OrderByComparator<LaunchSet> orderByComparator) {

		int count = countByC_U(companyId, userId);

		if (count == 0) {
			return null;
		}

		List<LaunchSet> list = findByC_U(
			companyId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the launch sets before and after the current launch set in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param launchSetId the primary key of the current launch set
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next launch set
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet[] findByC_U_PrevAndNext(
			long launchSetId, long companyId, long userId,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = findByPrimaryKey(launchSetId);

		Session session = null;

		try {
			session = openSession();

			LaunchSet[] array = new LaunchSetImpl[3];

			array[0] = getByC_U_PrevAndNext(
				session, launchSet, companyId, userId, orderByComparator, true);

			array[1] = launchSet;

			array[2] = getByC_U_PrevAndNext(
				session, launchSet, companyId, userId, orderByComparator,
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

	protected LaunchSet getByC_U_PrevAndNext(
		Session session, LaunchSet launchSet, long companyId, long userId,
		OrderByComparator<LaunchSet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

		sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_U_USERID_2);

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
			sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(launchSet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LaunchSet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the launch sets where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByC_U(long companyId, long userId) {
		for (LaunchSet launchSet :
				findByC_U(
					companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(launchSet);
		}
	}

	/**
	 * Returns the number of launch sets where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		FinderPath finderPath = _finderPathCountByC_U;

		Object[] finderArgs = new Object[] {companyId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAUNCHSET_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 =
		"launchSet.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_USERID_2 =
		"launchSet.userId = ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;
	private FinderPath _finderPathWithPaginationCountByC_S;

	/**
	 * Returns all the launch sets where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_S(long companyId, int status) {
		return findByC_S(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch sets where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @return the range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_S(
		long companyId, int status, int start, int end) {

		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator) {

		return findByC_S(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {companyId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				companyId, status, start, end, orderByComparator
			};
		}

		List<LaunchSet> list = null;

		if (useFinderCache) {
			list = (List<LaunchSet>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LaunchSet launchSet : list) {
					if ((companyId != launchSet.getCompanyId()) ||
						(status != launchSet.getStatus())) {

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

			sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(status);

				list = (List<LaunchSet>)QueryUtil.list(
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
	 * Returns the first launch set in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByC_S_First(
			long companyId, int status,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByC_S_First(
			companyId, status, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the first launch set in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByC_S_First(
		long companyId, int status,
		OrderByComparator<LaunchSet> orderByComparator) {

		List<LaunchSet> list = findByC_S(
			companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last launch set in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByC_S_Last(
			long companyId, int status,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByC_S_Last(
			companyId, status, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the last launch set in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByC_S_Last(
		long companyId, int status,
		OrderByComparator<LaunchSet> orderByComparator) {

		int count = countByC_S(companyId, status);

		if (count == 0) {
			return null;
		}

		List<LaunchSet> list = findByC_S(
			companyId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the launch sets before and after the current launch set in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param launchSetId the primary key of the current launch set
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next launch set
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet[] findByC_S_PrevAndNext(
			long launchSetId, long companyId, int status,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = findByPrimaryKey(launchSetId);

		Session session = null;

		try {
			session = openSession();

			LaunchSet[] array = new LaunchSetImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, launchSet, companyId, status, orderByComparator, true);

			array[1] = launchSet;

			array[2] = getByC_S_PrevAndNext(
				session, launchSet, companyId, status, orderByComparator,
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

	protected LaunchSet getByC_S_PrevAndNext(
		Session session, LaunchSet launchSet, long companyId, int status,
		OrderByComparator<LaunchSet> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

		sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_S_STATUS_2);

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
			sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(launchSet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LaunchSet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the launch sets where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_S(long companyId, int[] statuses) {
		return findByC_S(
			companyId, statuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch sets where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @return the range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_S(
		long companyId, int[] statuses, int start, int end) {

		return findByC_S(companyId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_S(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator) {

		return findByC_S(
			companyId, statuses, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_S(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
		boolean useFinderCache) {

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		if (statuses.length == 1) {
			return findByC_S(
				companyId, statuses[0], start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(statuses)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, StringUtil.merge(statuses), start, end,
				orderByComparator
			};
		}

		List<LaunchSet> list = null;

		if (useFinderCache) {
			list = (List<LaunchSet>)finderCache.getResult(
				_finderPathWithPaginationFindByC_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LaunchSet launchSet : list) {
					if ((companyId != launchSet.getCompanyId()) ||
						!ArrayUtil.contains(statuses, launchSet.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			if (statuses.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_S_STATUS_7);

				sb.append(StringUtil.merge(statuses));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LaunchSetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<LaunchSet>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_S, finderArgs, list);
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
	 * Removes all the launch sets where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long companyId, int status) {
		for (LaunchSet launchSet :
				findByC_S(
					companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(launchSet);
		}
	}

	/**
	 * Returns the number of launch sets where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByC_S(long companyId, int status) {
		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {companyId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAUNCHSET_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

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
	 * Returns the number of launch sets where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByC_S(long companyId, int[] statuses) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		Object[] finderArgs = new Object[] {
			companyId, StringUtil.merge(statuses)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_S, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_LAUNCHSET_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			if (statuses.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_S_STATUS_7);

				sb.append(StringUtil.merge(statuses));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_S, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"launchSet.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_STATUS_2 =
		"launchSet.status = ?";

	private static final String _FINDER_COLUMN_C_S_STATUS_7 =
		"launchSet.status IN (";

	private FinderPath _finderPathFetchByERC_C;

	/**
	 * Returns the launch set where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchLaunchSetException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByERC_C(externalReferenceCode, companyId);

		if (launchSet == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append(", companyId=");
			sb.append(companyId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLaunchSetException(sb.toString());
		}

		return launchSet;
	}

	/**
	 * Returns the launch set where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the launch set where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {externalReferenceCode, companyId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByERC_C, finderArgs, this);
		}

		if (result instanceof LaunchSet) {
			LaunchSet launchSet = (LaunchSet)result;

			if (!Objects.equals(
					externalReferenceCode,
					launchSet.getExternalReferenceCode()) ||
				(companyId != launchSet.getCompanyId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LAUNCHSET_WHERE);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2);
			}

			sb.append(_FINDER_COLUMN_ERC_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				queryPos.add(companyId);

				List<LaunchSet> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByERC_C, finderArgs, list);
					}
				}
				else {
					LaunchSet launchSet = list.get(0);

					result = launchSet;

					cacheResult(launchSet);
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
			return (LaunchSet)result;
		}
	}

	/**
	 * Removes the launch set where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the launch set that was removed
	 */
	@Override
	public LaunchSet removeByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = findByERC_C(externalReferenceCode, companyId);

		return remove(launchSet);
	}

	/**
	 * Returns the number of launch sets where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		LaunchSet launchSet = fetchByERC_C(externalReferenceCode, companyId);

		if (launchSet == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2 =
		"launchSet.externalReferenceCode = ? AND ";

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3 =
		"(launchSet.externalReferenceCode IS NULL OR launchSet.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_C_COMPANYID_2 =
		"launchSet.companyId = ?";

	public LaunchSetPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(LaunchSet.class);

		setModelImplClass(LaunchSetImpl.class);
		setModelPKClass(long.class);

		setTable(LaunchSetTable.INSTANCE);
	}

	/**
	 * Caches the launch set in the entity cache if it is enabled.
	 *
	 * @param launchSet the launch set
	 */
	@Override
	public void cacheResult(LaunchSet launchSet) {
		entityCache.putResult(
			LaunchSetImpl.class, launchSet.getPrimaryKey(), launchSet);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				launchSet.getExternalReferenceCode(), launchSet.getCompanyId()
			},
			launchSet);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the launch sets in the entity cache if it is enabled.
	 *
	 * @param launchSets the launch sets
	 */
	@Override
	public void cacheResult(List<LaunchSet> launchSets) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (launchSets.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LaunchSet launchSet : launchSets) {
			if (entityCache.getResult(
					LaunchSetImpl.class, launchSet.getPrimaryKey()) == null) {

				cacheResult(launchSet);
			}
		}
	}

	/**
	 * Clears the cache for all launch sets.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LaunchSetImpl.class);

		finderCache.clearCache(LaunchSetImpl.class);
	}

	/**
	 * Clears the cache for the launch set.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LaunchSet launchSet) {
		entityCache.removeResult(LaunchSetImpl.class, launchSet);
	}

	@Override
	public void clearCache(List<LaunchSet> launchSets) {
		for (LaunchSet launchSet : launchSets) {
			entityCache.removeResult(LaunchSetImpl.class, launchSet);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(LaunchSetImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(LaunchSetImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LaunchSetModelImpl launchSetModelImpl) {

		Object[] args = new Object[] {
			launchSetModelImpl.getExternalReferenceCode(),
			launchSetModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args, launchSetModelImpl);
	}

	/**
	 * Creates a new launch set with the primary key. Does not add the launch set to the database.
	 *
	 * @param launchSetId the primary key for the new launch set
	 * @return the new launch set
	 */
	@Override
	public LaunchSet create(long launchSetId) {
		LaunchSet launchSet = new LaunchSetImpl();

		launchSet.setNew(true);
		launchSet.setPrimaryKey(launchSetId);

		String uuid = PortalUUIDUtil.generate();

		launchSet.setUuid(uuid);

		launchSet.setCompanyId(CompanyThreadLocal.getCompanyId());

		return launchSet;
	}

	/**
	 * Removes the launch set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set that was removed
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet remove(long launchSetId) throws NoSuchLaunchSetException {
		return remove((Serializable)launchSetId);
	}

	/**
	 * Removes the launch set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the launch set
	 * @return the launch set that was removed
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet remove(Serializable primaryKey)
		throws NoSuchLaunchSetException {

		Session session = null;

		try {
			session = openSession();

			LaunchSet launchSet = (LaunchSet)session.get(
				LaunchSetImpl.class, primaryKey);

			if (launchSet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLaunchSetException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(launchSet);
		}
		catch (NoSuchLaunchSetException noSuchEntityException) {
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
	protected LaunchSet removeImpl(LaunchSet launchSet) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(launchSet)) {
				launchSet = (LaunchSet)session.get(
					LaunchSetImpl.class, launchSet.getPrimaryKeyObj());
			}

			if (launchSet != null) {
				session.delete(launchSet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (launchSet != null) {
			clearCache(launchSet);
		}

		return launchSet;
	}

	@Override
	public LaunchSet updateImpl(LaunchSet launchSet) {
		boolean isNew = launchSet.isNew();

		if (!(launchSet instanceof LaunchSetModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(launchSet.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(launchSet);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in launchSet proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LaunchSet implementation " +
					launchSet.getClass());
		}

		LaunchSetModelImpl launchSetModelImpl = (LaunchSetModelImpl)launchSet;

		if (Validator.isNull(launchSet.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			launchSet.setUuid(uuid);
		}

		if (Validator.isNull(launchSet.getExternalReferenceCode())) {
			launchSet.setExternalReferenceCode(launchSet.getUuid());
		}
		else {
			if (!Objects.equals(
					launchSetModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					launchSet.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = launchSet.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = launchSet.getPrimaryKey();
					}

					try {
						launchSet.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								LaunchSet.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								launchSet.getExternalReferenceCode(), null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			LaunchSet ercLaunchSet = fetchByERC_C(
				launchSet.getExternalReferenceCode(), launchSet.getCompanyId());

			if (isNew) {
				if (ercLaunchSet != null) {
					throw new DuplicateLaunchSetExternalReferenceCodeException(
						"Duplicate launch set with external reference code " +
							launchSet.getExternalReferenceCode() +
								" and company " + launchSet.getCompanyId());
				}
			}
			else {
				if ((ercLaunchSet != null) &&
					(launchSet.getLaunchSetId() !=
						ercLaunchSet.getLaunchSetId())) {

					throw new DuplicateLaunchSetExternalReferenceCodeException(
						"Duplicate launch set with external reference code " +
							launchSet.getExternalReferenceCode() +
								" and company " + launchSet.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (launchSet.getCreateDate() == null)) {
			if (serviceContext == null) {
				launchSet.setCreateDate(date);
			}
			else {
				launchSet.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!launchSetModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				launchSet.setModifiedDate(date);
			}
			else {
				launchSet.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(launchSet);
			}
			else {
				launchSet = (LaunchSet)session.merge(launchSet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			LaunchSetImpl.class, launchSetModelImpl, false, true);

		cacheUniqueFindersCache(launchSetModelImpl);

		if (isNew) {
			launchSet.setNew(false);
		}

		launchSet.resetOriginalValues();

		return launchSet;
	}

	/**
	 * Returns the launch set with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the launch set
	 * @return the launch set
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByPrimaryKey(primaryKey);

		if (launchSet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLaunchSetException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return launchSet;
	}

	/**
	 * Returns the launch set with the primary key or throws a <code>NoSuchLaunchSetException</code> if it could not be found.
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet findByPrimaryKey(long launchSetId)
		throws NoSuchLaunchSetException {

		return findByPrimaryKey((Serializable)launchSetId);
	}

	/**
	 * Returns the launch set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set, or <code>null</code> if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet fetchByPrimaryKey(long launchSetId) {
		return fetchByPrimaryKey((Serializable)launchSetId);
	}

	/**
	 * Returns all the launch sets.
	 *
	 * @return the launch sets
	 */
	@Override
	public List<LaunchSet> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @return the range of launch sets
	 */
	@Override
	public List<LaunchSet> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of launch sets
	 */
	@Override
	public List<LaunchSet> findAll(
		int start, int end, OrderByComparator<LaunchSet> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of launch sets
	 */
	@Override
	public List<LaunchSet> findAll(
		int start, int end, OrderByComparator<LaunchSet> orderByComparator,
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

		List<LaunchSet> list = null;

		if (useFinderCache) {
			list = (List<LaunchSet>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LAUNCHSET);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LAUNCHSET;

				sql = sql.concat(LaunchSetModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LaunchSet>)QueryUtil.list(
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
	 * Removes all the launch sets from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LaunchSet launchSet : findAll()) {
			remove(launchSet);
		}
	}

	/**
	 * Returns the number of launch sets.
	 *
	 * @return the number of launch sets
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LAUNCHSET);

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
		return "launchSetId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAUNCHSET;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LaunchSetModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the launch set persistence.
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

		_finderPathWithPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "userId"}, true);

		_finderPathWithoutPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, true);

		_finderPathCountByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, false);

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "status"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, false);

		_finderPathWithPaginationCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, false);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		LaunchSetUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		LaunchSetUtil.setPersistence(null);

		entityCache.removeCache(LaunchSetImpl.class.getName());
	}

	@Override
	@Reference(
		target = LaunchPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = LaunchPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = LaunchPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_LAUNCHSET =
		"SELECT launchSet FROM LaunchSet launchSet";

	private static final String _SQL_SELECT_LAUNCHSET_WHERE =
		"SELECT launchSet FROM LaunchSet launchSet WHERE ";

	private static final String _SQL_COUNT_LAUNCHSET =
		"SELECT COUNT(launchSet) FROM LaunchSet launchSet";

	private static final String _SQL_COUNT_LAUNCHSET_WHERE =
		"SELECT COUNT(launchSet) FROM LaunchSet launchSet WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "launchSet.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LaunchSet exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LaunchSet exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LaunchSetPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// SB-Hash:-1274757858