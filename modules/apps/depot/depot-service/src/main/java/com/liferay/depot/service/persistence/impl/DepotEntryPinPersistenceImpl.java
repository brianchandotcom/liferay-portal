/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.service.persistence.impl;

import com.liferay.depot.exception.NoSuchEntryPinException;
import com.liferay.depot.model.DepotEntryPin;
import com.liferay.depot.model.DepotEntryPinTable;
import com.liferay.depot.model.impl.DepotEntryPinImpl;
import com.liferay.depot.model.impl.DepotEntryPinModelImpl;
import com.liferay.depot.service.persistence.DepotEntryPinPersistence;
import com.liferay.depot.service.persistence.DepotEntryPinUtil;
import com.liferay.depot.service.persistence.impl.constants.DepotPersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
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

import java.util.ArrayList;
import java.util.Collections;
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
 * The persistence implementation for the depot entry pin service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DepotEntryPinPersistence.class)
public class DepotEntryPinPersistenceImpl
	extends BasePersistenceImpl<DepotEntryPin>
	implements DepotEntryPinPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DepotEntryPinUtil</code> to access the depot entry pin persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DepotEntryPinImpl.class.getName();

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
	 * Returns all the depot entry pins where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the depot entry pins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

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

			List<DepotEntryPin> list = null;

			if (useFinderCache) {
				list = (List<DepotEntryPin>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (DepotEntryPin depotEntryPin : list) {
						if (!uuid.equals(depotEntryPin.getUuid())) {
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

				sb.append(_SQL_SELECT_DEPOTENTRYPIN_WHERE);

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
					sb.append(DepotEntryPinModelImpl.ORDER_BY_JPQL);
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

					list = (List<DepotEntryPin>)QueryUtil.list(
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
	}

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin findByUuid_First(
			String uuid, OrderByComparator<DepotEntryPin> orderByComparator)
		throws NoSuchEntryPinException {

		DepotEntryPin depotEntryPin = fetchByUuid_First(
			uuid, orderByComparator);

		if (depotEntryPin != null) {
			return depotEntryPin;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryPinException(sb.toString());
	}

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin fetchByUuid_First(
		String uuid, OrderByComparator<DepotEntryPin> orderByComparator) {

		List<DepotEntryPin> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the depot entry pins where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DepotEntryPin depotEntryPin :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(depotEntryPin);
		}
	}

	/**
	 * Returns the number of depot entry pins where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching depot entry pins
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid;

			Object[] finderArgs = new Object[] {uuid};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_DEPOTENTRYPIN_WHERE);

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
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"depotEntryPin.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(depotEntryPin.uuid IS NULL OR depotEntryPin.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;

	/**
	 * Returns the depot entry pin where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryPinException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryPinException {

		DepotEntryPin depotEntryPin = fetchByUUID_G(uuid, groupId);

		if (depotEntryPin == null) {
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

			throw new NoSuchEntryPinException(sb.toString());
		}

		return depotEntryPin;
	}

	/**
	 * Returns the depot entry pin where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the depot entry pin where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

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

			if (result instanceof DepotEntryPin) {
				DepotEntryPin depotEntryPin = (DepotEntryPin)result;

				if (!Objects.equals(uuid, depotEntryPin.getUuid()) ||
					(groupId != depotEntryPin.getGroupId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_DEPOTENTRYPIN_WHERE);

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

					List<DepotEntryPin> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByUUID_G, finderArgs, list);
						}
					}
					else {
						DepotEntryPin depotEntryPin = list.get(0);

						result = depotEntryPin;

						cacheResult(depotEntryPin);
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
				return (DepotEntryPin)result;
			}
		}
	}

	/**
	 * Removes the depot entry pin where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the depot entry pin that was removed
	 */
	@Override
	public DepotEntryPin removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryPinException {

		DepotEntryPin depotEntryPin = findByUUID_G(uuid, groupId);

		return remove(depotEntryPin);
	}

	/**
	 * Returns the number of depot entry pins where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching depot entry pins
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		DepotEntryPin depotEntryPin = fetchByUUID_G(uuid, groupId);

		if (depotEntryPin == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"depotEntryPin.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(depotEntryPin.uuid IS NULL OR depotEntryPin.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"depotEntryPin.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

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

			List<DepotEntryPin> list = null;

			if (useFinderCache) {
				list = (List<DepotEntryPin>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (DepotEntryPin depotEntryPin : list) {
						if (!uuid.equals(depotEntryPin.getUuid()) ||
							(companyId != depotEntryPin.getCompanyId())) {

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

				sb.append(_SQL_SELECT_DEPOTENTRYPIN_WHERE);

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
					sb.append(DepotEntryPinModelImpl.ORDER_BY_JPQL);
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

					list = (List<DepotEntryPin>)QueryUtil.list(
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
	}

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DepotEntryPin> orderByComparator)
		throws NoSuchEntryPinException {

		DepotEntryPin depotEntryPin = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (depotEntryPin != null) {
			return depotEntryPin;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryPinException(sb.toString());
	}

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		List<DepotEntryPin> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the depot entry pins where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DepotEntryPin depotEntryPin :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(depotEntryPin);
		}
	}

	/**
	 * Returns the number of depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching depot entry pins
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid_C;

			Object[] finderArgs = new Object[] {uuid, companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_DEPOTENTRYPIN_WHERE);

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
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"depotEntryPin.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(depotEntryPin.uuid IS NULL OR depotEntryPin.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"depotEntryPin.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the depot entry pins where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the depot entry pins where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUserId(
		long userId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByUserId(
		long userId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByUserId;
					finderArgs = new Object[] {userId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByUserId;
				finderArgs = new Object[] {
					userId, start, end, orderByComparator
				};
			}

			List<DepotEntryPin> list = null;

			if (useFinderCache) {
				list = (List<DepotEntryPin>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (DepotEntryPin depotEntryPin : list) {
						if (userId != depotEntryPin.getUserId()) {
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

				sb.append(_SQL_SELECT_DEPOTENTRYPIN_WHERE);

				sb.append(_FINDER_COLUMN_USERID_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(DepotEntryPinModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(userId);

					list = (List<DepotEntryPin>)QueryUtil.list(
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
	}

	/**
	 * Returns the first depot entry pin in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin findByUserId_First(
			long userId, OrderByComparator<DepotEntryPin> orderByComparator)
		throws NoSuchEntryPinException {

		DepotEntryPin depotEntryPin = fetchByUserId_First(
			userId, orderByComparator);

		if (depotEntryPin != null) {
			return depotEntryPin;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchEntryPinException(sb.toString());
	}

	/**
	 * Returns the first depot entry pin in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin fetchByUserId_First(
		long userId, OrderByComparator<DepotEntryPin> orderByComparator) {

		List<DepotEntryPin> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the depot entry pins where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (DepotEntryPin depotEntryPin :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(depotEntryPin);
		}
	}

	/**
	 * Returns the number of depot entry pins where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching depot entry pins
	 */
	@Override
	public int countByUserId(long userId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

			FinderPath finderPath = _finderPathCountByUserId;

			Object[] finderArgs = new Object[] {userId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_DEPOTENTRYPIN_WHERE);

				sb.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

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
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"depotEntryPin.userId = ?";

	private FinderPath _finderPathWithPaginationFindByDepotEntryId;
	private FinderPath _finderPathWithoutPaginationFindByDepotEntryId;
	private FinderPath _finderPathCountByDepotEntryId;

	/**
	 * Returns all the depot entry pins where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByDepotEntryId(long depotEntryId) {
		return findByDepotEntryId(
			depotEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the depot entry pins where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByDepotEntryId(
		long depotEntryId, int start, int end) {

		return findByDepotEntryId(depotEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return findByDepotEntryId(
			depotEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByDepotEntryId;
					finderArgs = new Object[] {depotEntryId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByDepotEntryId;
				finderArgs = new Object[] {
					depotEntryId, start, end, orderByComparator
				};
			}

			List<DepotEntryPin> list = null;

			if (useFinderCache) {
				list = (List<DepotEntryPin>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (DepotEntryPin depotEntryPin : list) {
						if (depotEntryId != depotEntryPin.getDepotEntryId()) {
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

				sb.append(_SQL_SELECT_DEPOTENTRYPIN_WHERE);

				sb.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(DepotEntryPinModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(depotEntryId);

					list = (List<DepotEntryPin>)QueryUtil.list(
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
	}

	/**
	 * Returns the first depot entry pin in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin findByDepotEntryId_First(
			long depotEntryId,
			OrderByComparator<DepotEntryPin> orderByComparator)
		throws NoSuchEntryPinException {

		DepotEntryPin depotEntryPin = fetchByDepotEntryId_First(
			depotEntryId, orderByComparator);

		if (depotEntryPin != null) {
			return depotEntryPin;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("depotEntryId=");
		sb.append(depotEntryId);

		sb.append("}");

		throw new NoSuchEntryPinException(sb.toString());
	}

	/**
	 * Returns the first depot entry pin in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin fetchByDepotEntryId_First(
		long depotEntryId, OrderByComparator<DepotEntryPin> orderByComparator) {

		List<DepotEntryPin> list = findByDepotEntryId(
			depotEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the depot entry pins where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	@Override
	public void removeByDepotEntryId(long depotEntryId) {
		for (DepotEntryPin depotEntryPin :
				findByDepotEntryId(
					depotEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(depotEntryPin);
		}
	}

	/**
	 * Returns the number of depot entry pins where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot entry pins
	 */
	@Override
	public int countByDepotEntryId(long depotEntryId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

			FinderPath finderPath = _finderPathCountByDepotEntryId;

			Object[] finderArgs = new Object[] {depotEntryId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_DEPOTENTRYPIN_WHERE);

				sb.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(depotEntryId);

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
	}

	private static final String _FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2 =
		"depotEntryPin.depotEntryId = ?";

	private FinderPath _finderPathFetchByU_D;

	/**
	 * Returns the depot entry pin where userId = &#63; and depotEntryId = &#63; or throws a <code>NoSuchEntryPinException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin findByU_D(long userId, long depotEntryId)
		throws NoSuchEntryPinException {

		DepotEntryPin depotEntryPin = fetchByU_D(userId, depotEntryId);

		if (depotEntryPin == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", depotEntryId=");
			sb.append(depotEntryId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryPinException(sb.toString());
		}

		return depotEntryPin;
	}

	/**
	 * Returns the depot entry pin where userId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin fetchByU_D(long userId, long depotEntryId) {
		return fetchByU_D(userId, depotEntryId, true);
	}

	/**
	 * Returns the depot entry pin where userId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	@Override
	public DepotEntryPin fetchByU_D(
		long userId, long depotEntryId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {userId, depotEntryId};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByU_D, finderArgs, this);
			}

			if (result instanceof DepotEntryPin) {
				DepotEntryPin depotEntryPin = (DepotEntryPin)result;

				if ((userId != depotEntryPin.getUserId()) ||
					(depotEntryId != depotEntryPin.getDepotEntryId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_DEPOTENTRYPIN_WHERE);

				sb.append(_FINDER_COLUMN_U_D_USERID_2);

				sb.append(_FINDER_COLUMN_U_D_DEPOTENTRYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(userId);

					queryPos.add(depotEntryId);

					List<DepotEntryPin> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByU_D, finderArgs, list);
						}
					}
					else {
						DepotEntryPin depotEntryPin = list.get(0);

						result = depotEntryPin;

						cacheResult(depotEntryPin);
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
				return (DepotEntryPin)result;
			}
		}
	}

	/**
	 * Removes the depot entry pin where userId = &#63; and depotEntryId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the depot entry pin that was removed
	 */
	@Override
	public DepotEntryPin removeByU_D(long userId, long depotEntryId)
		throws NoSuchEntryPinException {

		DepotEntryPin depotEntryPin = findByU_D(userId, depotEntryId);

		return remove(depotEntryPin);
	}

	/**
	 * Returns the number of depot entry pins where userId = &#63; and depotEntryId = &#63;.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot entry pins
	 */
	@Override
	public int countByU_D(long userId, long depotEntryId) {
		DepotEntryPin depotEntryPin = fetchByU_D(userId, depotEntryId);

		if (depotEntryPin == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_U_D_USERID_2 =
		"depotEntryPin.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_D_DEPOTENTRYID_2 =
		"depotEntryPin.depotEntryId = ?";

	public DepotEntryPinPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DepotEntryPin.class);

		setModelImplClass(DepotEntryPinImpl.class);
		setModelPKClass(long.class);

		setTable(DepotEntryPinTable.INSTANCE);
	}

	/**
	 * Caches the depot entry pin in the entity cache if it is enabled.
	 *
	 * @param depotEntryPin the depot entry pin
	 */
	@Override
	public void cacheResult(DepotEntryPin depotEntryPin) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					depotEntryPin.getCtCollectionId())) {

			entityCache.putResult(
				DepotEntryPinImpl.class, depotEntryPin.getPrimaryKey(),
				depotEntryPin);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					depotEntryPin.getUuid(), depotEntryPin.getGroupId()
				},
				depotEntryPin);

			finderCache.putResult(
				_finderPathFetchByU_D,
				new Object[] {
					depotEntryPin.getUserId(), depotEntryPin.getDepotEntryId()
				},
				depotEntryPin);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the depot entry pins in the entity cache if it is enabled.
	 *
	 * @param depotEntryPins the depot entry pins
	 */
	@Override
	public void cacheResult(List<DepotEntryPin> depotEntryPins) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (depotEntryPins.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DepotEntryPin depotEntryPin : depotEntryPins) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						depotEntryPin.getCtCollectionId())) {

				if (entityCache.getResult(
						DepotEntryPinImpl.class,
						depotEntryPin.getPrimaryKey()) == null) {

					cacheResult(depotEntryPin);
				}
			}
		}
	}

	/**
	 * Clears the cache for all depot entry pins.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DepotEntryPinImpl.class);

		finderCache.clearCache(DepotEntryPinImpl.class);
	}

	/**
	 * Clears the cache for the depot entry pin.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DepotEntryPin depotEntryPin) {
		entityCache.removeResult(DepotEntryPinImpl.class, depotEntryPin);
	}

	@Override
	public void clearCache(List<DepotEntryPin> depotEntryPins) {
		for (DepotEntryPin depotEntryPin : depotEntryPins) {
			entityCache.removeResult(DepotEntryPinImpl.class, depotEntryPin);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DepotEntryPinImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DepotEntryPinImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DepotEntryPinModelImpl depotEntryPinModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					depotEntryPinModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				depotEntryPinModelImpl.getUuid(),
				depotEntryPinModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, depotEntryPinModelImpl);

			args = new Object[] {
				depotEntryPinModelImpl.getUserId(),
				depotEntryPinModelImpl.getDepotEntryId()
			};

			finderCache.putResult(
				_finderPathFetchByU_D, args, depotEntryPinModelImpl);
		}
	}

	/**
	 * Creates a new depot entry pin with the primary key. Does not add the depot entry pin to the database.
	 *
	 * @param depotEntryPinId the primary key for the new depot entry pin
	 * @return the new depot entry pin
	 */
	@Override
	public DepotEntryPin create(long depotEntryPinId) {
		DepotEntryPin depotEntryPin = new DepotEntryPinImpl();

		depotEntryPin.setNew(true);
		depotEntryPin.setPrimaryKey(depotEntryPinId);

		String uuid = PortalUUIDUtil.generate();

		depotEntryPin.setUuid(uuid);

		depotEntryPin.setCompanyId(CompanyThreadLocal.getCompanyId());

		return depotEntryPin;
	}

	/**
	 * Removes the depot entry pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryPinId the primary key of the depot entry pin
	 * @return the depot entry pin that was removed
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	@Override
	public DepotEntryPin remove(long depotEntryPinId)
		throws NoSuchEntryPinException {

		return remove((Serializable)depotEntryPinId);
	}

	/**
	 * Removes the depot entry pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the depot entry pin
	 * @return the depot entry pin that was removed
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	@Override
	public DepotEntryPin remove(Serializable primaryKey)
		throws NoSuchEntryPinException {

		Session session = null;

		try {
			session = openSession();

			DepotEntryPin depotEntryPin = (DepotEntryPin)session.get(
				DepotEntryPinImpl.class, primaryKey);

			if (depotEntryPin == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryPinException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(depotEntryPin);
		}
		catch (NoSuchEntryPinException noSuchEntityException) {
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
	protected DepotEntryPin removeImpl(DepotEntryPin depotEntryPin) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(depotEntryPin)) {
				depotEntryPin = (DepotEntryPin)session.get(
					DepotEntryPinImpl.class, depotEntryPin.getPrimaryKeyObj());
			}

			if ((depotEntryPin != null) &&
				ctPersistenceHelper.isRemove(depotEntryPin)) {

				session.delete(depotEntryPin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (depotEntryPin != null) {
			clearCache(depotEntryPin);
		}

		return depotEntryPin;
	}

	@Override
	public DepotEntryPin updateImpl(DepotEntryPin depotEntryPin) {
		boolean isNew = depotEntryPin.isNew();

		if (!(depotEntryPin instanceof DepotEntryPinModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(depotEntryPin.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					depotEntryPin);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in depotEntryPin proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DepotEntryPin implementation " +
					depotEntryPin.getClass());
		}

		DepotEntryPinModelImpl depotEntryPinModelImpl =
			(DepotEntryPinModelImpl)depotEntryPin;

		if (Validator.isNull(depotEntryPin.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			depotEntryPin.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(depotEntryPin)) {
				if (!isNew) {
					session.evict(
						DepotEntryPinImpl.class,
						depotEntryPin.getPrimaryKeyObj());
				}

				session.save(depotEntryPin);
			}
			else {
				depotEntryPin = (DepotEntryPin)session.merge(depotEntryPin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DepotEntryPinImpl.class, depotEntryPinModelImpl, false, true);

		cacheUniqueFindersCache(depotEntryPinModelImpl);

		if (isNew) {
			depotEntryPin.setNew(false);
		}

		depotEntryPin.resetOriginalValues();

		return depotEntryPin;
	}

	/**
	 * Returns the depot entry pin with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the depot entry pin
	 * @return the depot entry pin
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	@Override
	public DepotEntryPin findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryPinException {

		DepotEntryPin depotEntryPin = fetchByPrimaryKey(primaryKey);

		if (depotEntryPin == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryPinException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return depotEntryPin;
	}

	/**
	 * Returns the depot entry pin with the primary key or throws a <code>NoSuchEntryPinException</code> if it could not be found.
	 *
	 * @param depotEntryPinId the primary key of the depot entry pin
	 * @return the depot entry pin
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	@Override
	public DepotEntryPin findByPrimaryKey(long depotEntryPinId)
		throws NoSuchEntryPinException {

		return findByPrimaryKey((Serializable)depotEntryPinId);
	}

	/**
	 * Returns the depot entry pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the depot entry pin
	 * @return the depot entry pin, or <code>null</code> if a depot entry pin with the primary key could not be found
	 */
	@Override
	public DepotEntryPin fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				DepotEntryPin.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		DepotEntryPin depotEntryPin = (DepotEntryPin)entityCache.getResult(
			DepotEntryPinImpl.class, primaryKey);

		if (depotEntryPin != null) {
			return depotEntryPin;
		}

		Session session = null;

		try {
			session = openSession();

			depotEntryPin = (DepotEntryPin)session.get(
				DepotEntryPinImpl.class, primaryKey);

			if (depotEntryPin != null) {
				cacheResult(depotEntryPin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return depotEntryPin;
	}

	/**
	 * Returns the depot entry pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param depotEntryPinId the primary key of the depot entry pin
	 * @return the depot entry pin, or <code>null</code> if a depot entry pin with the primary key could not be found
	 */
	@Override
	public DepotEntryPin fetchByPrimaryKey(long depotEntryPinId) {
		return fetchByPrimaryKey((Serializable)depotEntryPinId);
	}

	@Override
	public Map<Serializable, DepotEntryPin> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(DepotEntryPin.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DepotEntryPin> map =
			new HashMap<Serializable, DepotEntryPin>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DepotEntryPin depotEntryPin = fetchByPrimaryKey(primaryKey);

			if (depotEntryPin != null) {
				map.put(primaryKey, depotEntryPin);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						DepotEntryPin.class, primaryKey)) {

				DepotEntryPin depotEntryPin =
					(DepotEntryPin)entityCache.getResult(
						DepotEntryPinImpl.class, primaryKey);

				if (depotEntryPin == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, depotEntryPin);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
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

			for (DepotEntryPin depotEntryPin :
					(List<DepotEntryPin>)query.list()) {

				map.put(depotEntryPin.getPrimaryKeyObj(), depotEntryPin);

				cacheResult(depotEntryPin);
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
	 * Returns all the depot entry pins.
	 *
	 * @return the depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the depot entry pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the depot entry pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findAll(
		int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the depot entry pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of depot entry pins
	 */
	@Override
	public List<DepotEntryPin> findAll(
		int start, int end, OrderByComparator<DepotEntryPin> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

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

			List<DepotEntryPin> list = null;

			if (useFinderCache) {
				list = (List<DepotEntryPin>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_DEPOTENTRYPIN);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_DEPOTENTRYPIN;

					sql = sql.concat(DepotEntryPinModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<DepotEntryPin>)QueryUtil.list(
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
	}

	/**
	 * Removes all the depot entry pins from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DepotEntryPin depotEntryPin : findAll()) {
			remove(depotEntryPin);
		}
	}

	/**
	 * Returns the number of depot entry pins.
	 *
	 * @return the number of depot entry pins
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DepotEntryPin.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_DEPOTENTRYPIN);

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
		return "depotEntryPinId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DEPOTENTRYPIN;
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
		return DepotEntryPinModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DepotEntryPin";
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
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctMergeColumnNames.add("depotEntryId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("depotEntryPinId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"userId", "depotEntryId"});
	}

	/**
	 * Initializes the depot entry pin persistence.
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

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathWithPaginationFindByDepotEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDepotEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"depotEntryId"}, true);

		_finderPathWithoutPaginationFindByDepotEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDepotEntryId",
			new String[] {Long.class.getName()}, new String[] {"depotEntryId"},
			true);

		_finderPathCountByDepotEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDepotEntryId",
			new String[] {Long.class.getName()}, new String[] {"depotEntryId"},
			false);

		_finderPathFetchByU_D = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_D",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "depotEntryId"}, true);

		DepotEntryPinUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		DepotEntryPinUtil.setPersistence(null);

		entityCache.removeCache(DepotEntryPinImpl.class.getName());
	}

	@Override
	@Reference(
		target = DepotPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DepotPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DepotPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DEPOTENTRYPIN =
		"SELECT depotEntryPin FROM DepotEntryPin depotEntryPin";

	private static final String _SQL_SELECT_DEPOTENTRYPIN_WHERE =
		"SELECT depotEntryPin FROM DepotEntryPin depotEntryPin WHERE ";

	private static final String _SQL_COUNT_DEPOTENTRYPIN =
		"SELECT COUNT(depotEntryPin) FROM DepotEntryPin depotEntryPin";

	private static final String _SQL_COUNT_DEPOTENTRYPIN_WHERE =
		"SELECT COUNT(depotEntryPin) FROM DepotEntryPin depotEntryPin WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "depotEntryPin.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DepotEntryPin exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DepotEntryPin exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DepotEntryPinPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-559887671