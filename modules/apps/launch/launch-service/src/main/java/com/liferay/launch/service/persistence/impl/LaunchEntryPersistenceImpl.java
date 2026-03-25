/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.persistence.impl;

import com.liferay.launch.exception.DuplicateLaunchEntryExternalReferenceCodeException;
import com.liferay.launch.exception.NoSuchLaunchEntryException;
import com.liferay.launch.model.LaunchEntry;
import com.liferay.launch.model.LaunchEntryTable;
import com.liferay.launch.model.impl.LaunchEntryImpl;
import com.liferay.launch.model.impl.LaunchEntryModelImpl;
import com.liferay.launch.service.persistence.LaunchEntryPersistence;
import com.liferay.launch.service.persistence.LaunchEntryUtil;
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
import com.liferay.portal.kernel.util.ContentTypes;
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
 * The persistence implementation for the launch entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LaunchEntryPersistence.class)
public class LaunchEntryPersistenceImpl
	extends BasePersistenceImpl<LaunchEntry> implements LaunchEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LaunchEntryUtil</code> to access the launch entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LaunchEntryImpl.class.getName();

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
	 * Returns all the launch entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @return the range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator,
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

		List<LaunchEntry> list = null;

		if (useFinderCache) {
			list = (List<LaunchEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LaunchEntry launchEntry : list) {
					if (!uuid.equals(launchEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_LAUNCHENTRY_WHERE);

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
				sb.append(LaunchEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<LaunchEntry>)QueryUtil.list(
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
	 * Returns the first launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry findByUuid_First(
			String uuid, OrderByComparator<LaunchEntry> orderByComparator)
		throws NoSuchLaunchEntryException {

		LaunchEntry launchEntry = fetchByUuid_First(uuid, orderByComparator);

		if (launchEntry != null) {
			return launchEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchLaunchEntryException(sb.toString());
	}

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByUuid_First(
		String uuid, OrderByComparator<LaunchEntry> orderByComparator) {

		List<LaunchEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the launch entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LaunchEntry launchEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(launchEntry);
		}
	}

	/**
	 * Returns the number of launch entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching launch entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAUNCHENTRY_WHERE);

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
		"launchEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(launchEntry.uuid IS NULL OR launchEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @return the range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator,
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

		List<LaunchEntry> list = null;

		if (useFinderCache) {
			list = (List<LaunchEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LaunchEntry launchEntry : list) {
					if (!uuid.equals(launchEntry.getUuid()) ||
						(companyId != launchEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_LAUNCHENTRY_WHERE);

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
				sb.append(LaunchEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<LaunchEntry>)QueryUtil.list(
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
	 * Returns the first launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LaunchEntry> orderByComparator)
		throws NoSuchLaunchEntryException {

		LaunchEntry launchEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (launchEntry != null) {
			return launchEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLaunchEntryException(sb.toString());
	}

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LaunchEntry> orderByComparator) {

		List<LaunchEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the launch entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LaunchEntry launchEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(launchEntry);
		}
	}

	/**
	 * Returns the number of launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching launch entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAUNCHENTRY_WHERE);

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
		"launchEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(launchEntry.uuid IS NULL OR launchEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"launchEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByLaunchSetId;
	private FinderPath _finderPathWithoutPaginationFindByLaunchSetId;
	private FinderPath _finderPathCountByLaunchSetId;

	/**
	 * Returns all the launch entries where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @return the matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByLaunchSetId(long launchSetId) {
		return findByLaunchSetId(
			launchSetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch entries where launchSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param launchSetId the launch set ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @return the range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByLaunchSetId(
		long launchSetId, int start, int end) {

		return findByLaunchSetId(launchSetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch entries where launchSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param launchSetId the launch set ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByLaunchSetId(
		long launchSetId, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator) {

		return findByLaunchSetId(
			launchSetId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch entries where launchSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param launchSetId the launch set ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByLaunchSetId(
		long launchSetId, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByLaunchSetId;
				finderArgs = new Object[] {launchSetId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByLaunchSetId;
			finderArgs = new Object[] {
				launchSetId, start, end, orderByComparator
			};
		}

		List<LaunchEntry> list = null;

		if (useFinderCache) {
			list = (List<LaunchEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LaunchEntry launchEntry : list) {
					if (launchSetId != launchEntry.getLaunchSetId()) {
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

			sb.append(_SQL_SELECT_LAUNCHENTRY_WHERE);

			sb.append(_FINDER_COLUMN_LAUNCHSETID_LAUNCHSETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LaunchEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(launchSetId);

				list = (List<LaunchEntry>)QueryUtil.list(
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
	 * Returns the first launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry findByLaunchSetId_First(
			long launchSetId, OrderByComparator<LaunchEntry> orderByComparator)
		throws NoSuchLaunchEntryException {

		LaunchEntry launchEntry = fetchByLaunchSetId_First(
			launchSetId, orderByComparator);

		if (launchEntry != null) {
			return launchEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("launchSetId=");
		sb.append(launchSetId);

		sb.append("}");

		throw new NoSuchLaunchEntryException(sb.toString());
	}

	/**
	 * Returns the first launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByLaunchSetId_First(
		long launchSetId, OrderByComparator<LaunchEntry> orderByComparator) {

		List<LaunchEntry> list = findByLaunchSetId(
			launchSetId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the launch entries where launchSetId = &#63; from the database.
	 *
	 * @param launchSetId the launch set ID
	 */
	@Override
	public void removeByLaunchSetId(long launchSetId) {
		for (LaunchEntry launchEntry :
				findByLaunchSetId(
					launchSetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(launchEntry);
		}
	}

	/**
	 * Returns the number of launch entries where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @return the number of matching launch entries
	 */
	@Override
	public int countByLaunchSetId(long launchSetId) {
		FinderPath finderPath = _finderPathCountByLaunchSetId;

		Object[] finderArgs = new Object[] {launchSetId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAUNCHENTRY_WHERE);

			sb.append(_FINDER_COLUMN_LAUNCHSETID_LAUNCHSETID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(launchSetId);

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

	private static final String _FINDER_COLUMN_LAUNCHSETID_LAUNCHSETID_2 =
		"launchEntry.launchSetId = ?";

	private FinderPath _finderPathFetchByC_C_C;

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry findByC_C_C(
			long classNameId, long classPK, String classVersion)
		throws NoSuchLaunchEntryException {

		LaunchEntry launchEntry = fetchByC_C_C(
			classNameId, classPK, classVersion);

		if (launchEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", classVersion=");
			sb.append(classVersion);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLaunchEntryException(sb.toString());
		}

		return launchEntry;
	}

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByC_C_C(
		long classNameId, long classPK, String classVersion) {

		return fetchByC_C_C(classNameId, classPK, classVersion, true);
	}

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByC_C_C(
		long classNameId, long classPK, String classVersion,
		boolean useFinderCache) {

		classVersion = Objects.toString(classVersion, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK, classVersion};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_C, finderArgs, this);
		}

		if (result instanceof LaunchEntry) {
			LaunchEntry launchEntry = (LaunchEntry)result;

			if ((classNameId != launchEntry.getClassNameId()) ||
				(classPK != launchEntry.getClassPK()) ||
				!Objects.equals(classVersion, launchEntry.getClassVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_LAUNCHENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			boolean bindClassVersion = false;

			if (classVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_C_CLASSVERSION_3);
			}
			else {
				bindClassVersion = true;

				sb.append(_FINDER_COLUMN_C_C_C_CLASSVERSION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindClassVersion) {
					queryPos.add(classVersion);
				}

				List<LaunchEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_C, finderArgs, list);
					}
				}
				else {
					LaunchEntry launchEntry = list.get(0);

					result = launchEntry;

					cacheResult(launchEntry);
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
			return (LaunchEntry)result;
		}
	}

	/**
	 * Removes the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the launch entry that was removed
	 */
	@Override
	public LaunchEntry removeByC_C_C(
			long classNameId, long classPK, String classVersion)
		throws NoSuchLaunchEntryException {

		LaunchEntry launchEntry = findByC_C_C(
			classNameId, classPK, classVersion);

		return remove(launchEntry);
	}

	/**
	 * Returns the number of launch entries where classNameId = &#63; and classPK = &#63; and classVersion = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the number of matching launch entries
	 */
	@Override
	public int countByC_C_C(
		long classNameId, long classPK, String classVersion) {

		LaunchEntry launchEntry = fetchByC_C_C(
			classNameId, classPK, classVersion);

		if (launchEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 =
		"launchEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"launchEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSVERSION_2 =
		"launchEntry.classVersion = ?";

	private static final String _FINDER_COLUMN_C_C_C_CLASSVERSION_3 =
		"(launchEntry.classVersion IS NULL OR launchEntry.classVersion = '')";

	private FinderPath _finderPathFetchByERC_C;

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry findByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchLaunchEntryException {

		LaunchEntry launchEntry = fetchByERC_C(
			externalReferenceCode, companyId);

		if (launchEntry == null) {
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

			throw new NoSuchLaunchEntryException(sb.toString());
		}

		return launchEntry;
	}

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByERC_C(
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

		if (result instanceof LaunchEntry) {
			LaunchEntry launchEntry = (LaunchEntry)result;

			if (!Objects.equals(
					externalReferenceCode,
					launchEntry.getExternalReferenceCode()) ||
				(companyId != launchEntry.getCompanyId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LAUNCHENTRY_WHERE);

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

				List<LaunchEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByERC_C, finderArgs, list);
					}
				}
				else {
					LaunchEntry launchEntry = list.get(0);

					result = launchEntry;

					cacheResult(launchEntry);
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
			return (LaunchEntry)result;
		}
	}

	/**
	 * Removes the launch entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the launch entry that was removed
	 */
	@Override
	public LaunchEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchLaunchEntryException {

		LaunchEntry launchEntry = findByERC_C(externalReferenceCode, companyId);

		return remove(launchEntry);
	}

	/**
	 * Returns the number of launch entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching launch entries
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		LaunchEntry launchEntry = fetchByERC_C(
			externalReferenceCode, companyId);

		if (launchEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2 =
		"launchEntry.externalReferenceCode = ? AND ";

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3 =
		"(launchEntry.externalReferenceCode IS NULL OR launchEntry.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_C_COMPANYID_2 =
		"launchEntry.companyId = ?";

	public LaunchEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(LaunchEntry.class);

		setModelImplClass(LaunchEntryImpl.class);
		setModelPKClass(long.class);

		setTable(LaunchEntryTable.INSTANCE);
	}

	/**
	 * Caches the launch entry in the entity cache if it is enabled.
	 *
	 * @param launchEntry the launch entry
	 */
	@Override
	public void cacheResult(LaunchEntry launchEntry) {
		entityCache.putResult(
			LaunchEntryImpl.class, launchEntry.getPrimaryKey(), launchEntry);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				launchEntry.getClassNameId(), launchEntry.getClassPK(),
				launchEntry.getClassVersion()
			},
			launchEntry);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				launchEntry.getExternalReferenceCode(),
				launchEntry.getCompanyId()
			},
			launchEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the launch entries in the entity cache if it is enabled.
	 *
	 * @param launchEntries the launch entries
	 */
	@Override
	public void cacheResult(List<LaunchEntry> launchEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (launchEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LaunchEntry launchEntry : launchEntries) {
			if (entityCache.getResult(
					LaunchEntryImpl.class, launchEntry.getPrimaryKey()) ==
						null) {

				cacheResult(launchEntry);
			}
		}
	}

	/**
	 * Clears the cache for all launch entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LaunchEntryImpl.class);

		finderCache.clearCache(LaunchEntryImpl.class);
	}

	/**
	 * Clears the cache for the launch entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LaunchEntry launchEntry) {
		entityCache.removeResult(LaunchEntryImpl.class, launchEntry);
	}

	@Override
	public void clearCache(List<LaunchEntry> launchEntries) {
		for (LaunchEntry launchEntry : launchEntries) {
			entityCache.removeResult(LaunchEntryImpl.class, launchEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(LaunchEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(LaunchEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LaunchEntryModelImpl launchEntryModelImpl) {

		Object[] args = new Object[] {
			launchEntryModelImpl.getClassNameId(),
			launchEntryModelImpl.getClassPK(),
			launchEntryModelImpl.getClassVersion()
		};

		finderCache.putResult(
			_finderPathFetchByC_C_C, args, launchEntryModelImpl);

		args = new Object[] {
			launchEntryModelImpl.getExternalReferenceCode(),
			launchEntryModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args, launchEntryModelImpl);
	}

	/**
	 * Creates a new launch entry with the primary key. Does not add the launch entry to the database.
	 *
	 * @param launchEntryId the primary key for the new launch entry
	 * @return the new launch entry
	 */
	@Override
	public LaunchEntry create(long launchEntryId) {
		LaunchEntry launchEntry = new LaunchEntryImpl();

		launchEntry.setNew(true);
		launchEntry.setPrimaryKey(launchEntryId);

		String uuid = PortalUUIDUtil.generate();

		launchEntry.setUuid(uuid);

		launchEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return launchEntry;
	}

	/**
	 * Removes the launch entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry that was removed
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	@Override
	public LaunchEntry remove(long launchEntryId)
		throws NoSuchLaunchEntryException {

		return remove((Serializable)launchEntryId);
	}

	/**
	 * Removes the launch entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the launch entry
	 * @return the launch entry that was removed
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	@Override
	public LaunchEntry remove(Serializable primaryKey)
		throws NoSuchLaunchEntryException {

		Session session = null;

		try {
			session = openSession();

			LaunchEntry launchEntry = (LaunchEntry)session.get(
				LaunchEntryImpl.class, primaryKey);

			if (launchEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLaunchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(launchEntry);
		}
		catch (NoSuchLaunchEntryException noSuchEntityException) {
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
	protected LaunchEntry removeImpl(LaunchEntry launchEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(launchEntry)) {
				launchEntry = (LaunchEntry)session.get(
					LaunchEntryImpl.class, launchEntry.getPrimaryKeyObj());
			}

			if (launchEntry != null) {
				session.delete(launchEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (launchEntry != null) {
			clearCache(launchEntry);
		}

		return launchEntry;
	}

	@Override
	public LaunchEntry updateImpl(LaunchEntry launchEntry) {
		boolean isNew = launchEntry.isNew();

		if (!(launchEntry instanceof LaunchEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(launchEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(launchEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in launchEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LaunchEntry implementation " +
					launchEntry.getClass());
		}

		LaunchEntryModelImpl launchEntryModelImpl =
			(LaunchEntryModelImpl)launchEntry;

		if (Validator.isNull(launchEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			launchEntry.setUuid(uuid);
		}

		if (Validator.isNull(launchEntry.getExternalReferenceCode())) {
			launchEntry.setExternalReferenceCode(launchEntry.getUuid());
		}
		else {
			if (!Objects.equals(
					launchEntryModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					launchEntry.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = launchEntry.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = launchEntry.getPrimaryKey();
					}

					try {
						launchEntry.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								LaunchEntry.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								launchEntry.getExternalReferenceCode(), null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			LaunchEntry ercLaunchEntry = fetchByERC_C(
				launchEntry.getExternalReferenceCode(),
				launchEntry.getCompanyId());

			if (isNew) {
				if (ercLaunchEntry != null) {
					throw new DuplicateLaunchEntryExternalReferenceCodeException(
						"Duplicate launch entry with external reference code " +
							launchEntry.getExternalReferenceCode() +
								" and company " + launchEntry.getCompanyId());
				}
			}
			else {
				if ((ercLaunchEntry != null) &&
					(launchEntry.getLaunchEntryId() !=
						ercLaunchEntry.getLaunchEntryId())) {

					throw new DuplicateLaunchEntryExternalReferenceCodeException(
						"Duplicate launch entry with external reference code " +
							launchEntry.getExternalReferenceCode() +
								" and company " + launchEntry.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (launchEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				launchEntry.setCreateDate(date);
			}
			else {
				launchEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!launchEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				launchEntry.setModifiedDate(date);
			}
			else {
				launchEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(launchEntry);
			}
			else {
				launchEntry = (LaunchEntry)session.merge(launchEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			LaunchEntryImpl.class, launchEntryModelImpl, false, true);

		cacheUniqueFindersCache(launchEntryModelImpl);

		if (isNew) {
			launchEntry.setNew(false);
		}

		launchEntry.resetOriginalValues();

		return launchEntry;
	}

	/**
	 * Returns the launch entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the launch entry
	 * @return the launch entry
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	@Override
	public LaunchEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLaunchEntryException {

		LaunchEntry launchEntry = fetchByPrimaryKey(primaryKey);

		if (launchEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLaunchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return launchEntry;
	}

	/**
	 * Returns the launch entry with the primary key or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	@Override
	public LaunchEntry findByPrimaryKey(long launchEntryId)
		throws NoSuchLaunchEntryException {

		return findByPrimaryKey((Serializable)launchEntryId);
	}

	/**
	 * Returns the launch entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry, or <code>null</code> if a launch entry with the primary key could not be found
	 */
	@Override
	public LaunchEntry fetchByPrimaryKey(long launchEntryId) {
		return fetchByPrimaryKey((Serializable)launchEntryId);
	}

	/**
	 * Returns all the launch entries.
	 *
	 * @return the launch entries
	 */
	@Override
	public List<LaunchEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the launch entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @return the range of launch entries
	 */
	@Override
	public List<LaunchEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the launch entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of launch entries
	 */
	@Override
	public List<LaunchEntry> findAll(
		int start, int end, OrderByComparator<LaunchEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the launch entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of launch entries
	 */
	@Override
	public List<LaunchEntry> findAll(
		int start, int end, OrderByComparator<LaunchEntry> orderByComparator,
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

		List<LaunchEntry> list = null;

		if (useFinderCache) {
			list = (List<LaunchEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LAUNCHENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LAUNCHENTRY;

				sql = sql.concat(LaunchEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LaunchEntry>)QueryUtil.list(
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
	 * Removes all the launch entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LaunchEntry launchEntry : findAll()) {
			remove(launchEntry);
		}
	}

	/**
	 * Returns the number of launch entries.
	 *
	 * @return the number of launch entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LAUNCHENTRY);

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
		return "launchEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAUNCHENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LaunchEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the launch entry persistence.
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

		_finderPathWithPaginationFindByLaunchSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLaunchSetId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"launchSetId"}, true);

		_finderPathWithoutPaginationFindByLaunchSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLaunchSetId",
			new String[] {Long.class.getName()}, new String[] {"launchSetId"},
			true);

		_finderPathCountByLaunchSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLaunchSetId",
			new String[] {Long.class.getName()}, new String[] {"launchSetId"},
			false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"classNameId", "classPK", "classVersion"}, true);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		LaunchEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		LaunchEntryUtil.setPersistence(null);

		entityCache.removeCache(LaunchEntryImpl.class.getName());
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

	private static final String _SQL_SELECT_LAUNCHENTRY =
		"SELECT launchEntry FROM LaunchEntry launchEntry";

	private static final String _SQL_SELECT_LAUNCHENTRY_WHERE =
		"SELECT launchEntry FROM LaunchEntry launchEntry WHERE ";

	private static final String _SQL_COUNT_LAUNCHENTRY =
		"SELECT COUNT(launchEntry) FROM LaunchEntry launchEntry";

	private static final String _SQL_COUNT_LAUNCHENTRY_WHERE =
		"SELECT COUNT(launchEntry) FROM LaunchEntry launchEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "launchEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LaunchEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LaunchEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LaunchEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1300086805