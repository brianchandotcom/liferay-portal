/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.service.persistence.impl;

import com.liferay.dispatch.exception.DuplicateDispatchTriggerExternalReferenceCodeException;
import com.liferay.dispatch.exception.NoSuchTriggerException;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.model.DispatchTriggerTable;
import com.liferay.dispatch.model.impl.DispatchTriggerImpl;
import com.liferay.dispatch.model.impl.DispatchTriggerModelImpl;
import com.liferay.dispatch.service.persistence.DispatchTriggerPersistence;
import com.liferay.dispatch.service.persistence.DispatchTriggerUtil;
import com.liferay.dispatch.service.persistence.impl.constants.DispatchPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
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
 * The persistence implementation for the dispatch trigger service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matija Petanjek
 * @generated
 */
@Component(service = DispatchTriggerPersistence.class)
public class DispatchTriggerPersistenceImpl
	extends BasePersistenceImpl<DispatchTrigger, NoSuchTriggerException>
	implements DispatchTriggerPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DispatchTriggerUtil</code> to access the dispatch trigger persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DispatchTriggerImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<DispatchTrigger>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the dispatch triggers where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByUuid_First(
			String uuid, OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByUuid_First(
			uuid, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		throw new NoSuchTriggerException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByUuid_First(
		String uuid, OrderByComparator<DispatchTrigger> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Returns all the dispatch triggers that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByUuid(
		String uuid, int start, int end) {

		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByUuid(
					uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					DispatchTriggerModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			return (List<DispatchTrigger>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the dispatch triggers where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of dispatch triggers where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of dispatch triggers that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DispatchTrigger> dispatchTriggers = findByUuid(uuid);

			dispatchTriggers = InlineSQLHelperUtil.filter(dispatchTriggers);

			return dispatchTriggers.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL =
		"dispatchTrigger.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(dispatchTrigger.uuid_ IS NULL OR dispatchTrigger.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<DispatchTrigger>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the dispatch triggers where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		throw new NoSuchTriggerException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Returns all the dispatch triggers that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByUuid_C(
		String uuid, long companyId) {

		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					DispatchTriggerModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			return (List<DispatchTrigger>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the dispatch triggers where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		_collectionPersistenceFinderByUuid_C.remove(
			finderCache, new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of dispatch triggers where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of dispatch triggers that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DispatchTrigger> dispatchTriggers = findByUuid_C(
				uuid, companyId);

			dispatchTriggers = InlineSQLHelperUtil.filter(dispatchTriggers);

			return dispatchTriggers.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL =
		"dispatchTrigger.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(dispatchTrigger.uuid_ IS NULL OR dispatchTrigger.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"dispatchTrigger.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<DispatchTrigger>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the dispatch triggers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByCompanyId_First(
			long companyId,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		throw new NoSuchTriggerException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByCompanyId_First(
		long companyId, OrderByComparator<DispatchTrigger> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Returns all the dispatch triggers that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					DispatchTriggerModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<DispatchTrigger>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the dispatch triggers where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of dispatch triggers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of dispatch triggers that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DispatchTrigger> dispatchTriggers = findByCompanyId(companyId);

			dispatchTriggers = InlineSQLHelperUtil.filter(dispatchTriggers);

			return dispatchTriggers.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"dispatchTrigger.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByActive;
	private FinderPath _finderPathWithoutPaginationFindByActive;
	private FinderPath _finderPathCountByActive;
	private CollectionPersistenceFinder<DispatchTrigger>
		_collectionPersistenceFinderByActive;

	/**
	 * Returns all the dispatch triggers where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByActive(boolean active) {
		return findByActive(active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByActive(
		boolean active, int start, int end) {

		return findByActive(active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByActive(
		boolean active, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findByActive(active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByActive(
		boolean active, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByActive.find(
			finderCache, new Object[] {active}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByActive_First(
			boolean active,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByActive_First(
			active, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		throw new NoSuchTriggerException(
			_collectionPersistenceFinderByActive.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {active}));
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByActive_First(
		boolean active, OrderByComparator<DispatchTrigger> orderByComparator) {

		return _collectionPersistenceFinderByActive.fetchFirst(
			finderCache, new Object[] {active}, orderByComparator);
	}

	/**
	 * Returns all the dispatch triggers that the user has permission to view where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByActive(boolean active) {
		return filterFindByActive(
			active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers that the user has permission to view where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByActive(
		boolean active, int start, int end) {

		return filterFindByActive(active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers that the user has permissions to view where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByActive(
		boolean active, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByActive(active, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByActive(
					active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					DispatchTriggerModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			return (List<DispatchTrigger>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the dispatch triggers where active = &#63; from the database.
	 *
	 * @param active the active
	 */
	@Override
	public void removeByActive(boolean active) {
		_collectionPersistenceFinderByActive.remove(
			finderCache, new Object[] {active});
	}

	/**
	 * Returns the number of dispatch triggers where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByActive(boolean active) {
		return _collectionPersistenceFinderByActive.count(
			finderCache, new Object[] {active});
	}

	/**
	 * Returns the number of dispatch triggers that the user has permission to view where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public int filterCountByActive(boolean active) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByActive(active);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DispatchTrigger> dispatchTriggers = findByActive(active);

			dispatchTriggers = InlineSQLHelperUtil.filter(dispatchTriggers);

			return dispatchTriggers.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE);

		sb.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_ACTIVE_ACTIVE_2_SQL =
		"dispatchTrigger.active_ = ?";

	private FinderPath _finderPathWithPaginationFindByC_U;
	private FinderPath _finderPathWithoutPaginationFindByC_U;
	private FinderPath _finderPathCountByC_U;
	private CollectionPersistenceFinder<DispatchTrigger>
		_collectionPersistenceFinderByC_U;

	/**
	 * Returns all the dispatch triggers where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_U(long companyId, long userId) {
		return findByC_U(
			companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_U(
		long companyId, long userId, int start, int end) {

		return findByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findByC_U(
			companyId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_U.find(
			finderCache, new Object[] {companyId, userId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByC_U_First(
			long companyId, long userId,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByC_U_First(
			companyId, userId, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		throw new NoSuchTriggerException(
			_collectionPersistenceFinderByC_U.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, userId}));
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByC_U_First(
		long companyId, long userId,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return _collectionPersistenceFinderByC_U.fetchFirst(
			finderCache, new Object[] {companyId, userId}, orderByComparator);
	}

	/**
	 * Returns all the dispatch triggers that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByC_U(long companyId, long userId) {
		return filterFindByC_U(
			companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByC_U(
		long companyId, long userId, int start, int end) {

		return filterFindByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers that the user has permissions to view where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_U(companyId, userId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_U(
					companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					DispatchTriggerModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(userId);

			return (List<DispatchTrigger>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the dispatch triggers where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByC_U(long companyId, long userId) {
		_collectionPersistenceFinderByC_U.remove(
			finderCache, new Object[] {companyId, userId});
	}

	/**
	 * Returns the number of dispatch triggers where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		return _collectionPersistenceFinderByC_U.count(
			finderCache, new Object[] {companyId, userId});
	}

	/**
	 * Returns the number of dispatch triggers that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public int filterCountByC_U(long companyId, long userId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_U(companyId, userId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DispatchTrigger> dispatchTriggers = findByC_U(
				companyId, userId);

			dispatchTriggers = InlineSQLHelperUtil.filter(dispatchTriggers);

			return dispatchTriggers.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE);

		sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(userId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 =
		"dispatchTrigger.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_USERID_2 =
		"dispatchTrigger.userId = ?";

	private FinderPath _finderPathWithPaginationFindByC_DTET;
	private FinderPath _finderPathWithoutPaginationFindByC_DTET;
	private FinderPath _finderPathCountByC_DTET;
	private CollectionPersistenceFinder<DispatchTrigger>
		_collectionPersistenceFinderByC_DTET;

	/**
	 * Returns all the dispatch triggers where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @return the matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_DTET(
		long companyId, String dispatchTaskExecutorType) {

		return findByC_DTET(
			companyId, dispatchTaskExecutorType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_DTET(
		long companyId, String dispatchTaskExecutorType, int start, int end) {

		return findByC_DTET(
			companyId, dispatchTaskExecutorType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_DTET(
		long companyId, String dispatchTaskExecutorType, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findByC_DTET(
			companyId, dispatchTaskExecutorType, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_DTET(
		long companyId, String dispatchTaskExecutorType, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_DTET.find(
			finderCache, new Object[] {companyId, dispatchTaskExecutorType},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByC_DTET_First(
			long companyId, String dispatchTaskExecutorType,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByC_DTET_First(
			companyId, dispatchTaskExecutorType, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		throw new NoSuchTriggerException(
			_collectionPersistenceFinderByC_DTET.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, dispatchTaskExecutorType}));
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByC_DTET_First(
		long companyId, String dispatchTaskExecutorType,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return _collectionPersistenceFinderByC_DTET.fetchFirst(
			finderCache, new Object[] {companyId, dispatchTaskExecutorType},
			orderByComparator);
	}

	/**
	 * Returns all the dispatch triggers that the user has permission to view where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @return the matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByC_DTET(
		long companyId, String dispatchTaskExecutorType) {

		return filterFindByC_DTET(
			companyId, dispatchTaskExecutorType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers that the user has permission to view where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByC_DTET(
		long companyId, String dispatchTaskExecutorType, int start, int end) {

		return filterFindByC_DTET(
			companyId, dispatchTaskExecutorType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers that the user has permissions to view where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByC_DTET(
		long companyId, String dispatchTaskExecutorType, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_DTET(
				companyId, dispatchTaskExecutorType, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_DTET(
					companyId, dispatchTaskExecutorType, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
		}

		dispatchTaskExecutorType = Objects.toString(
			dispatchTaskExecutorType, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_DTET_COMPANYID_2);

		boolean bindDispatchTaskExecutorType = false;

		if (dispatchTaskExecutorType.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_DTET_DISPATCHTASKEXECUTORTYPE_3);
		}
		else {
			bindDispatchTaskExecutorType = true;

			sb.append(_FINDER_COLUMN_C_DTET_DISPATCHTASKEXECUTORTYPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					DispatchTriggerModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindDispatchTaskExecutorType) {
				queryPos.add(dispatchTaskExecutorType);
			}

			return (List<DispatchTrigger>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the dispatch triggers where companyId = &#63; and dispatchTaskExecutorType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 */
	@Override
	public void removeByC_DTET(
		long companyId, String dispatchTaskExecutorType) {

		_collectionPersistenceFinderByC_DTET.remove(
			finderCache, new Object[] {companyId, dispatchTaskExecutorType});
	}

	/**
	 * Returns the number of dispatch triggers where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByC_DTET(long companyId, String dispatchTaskExecutorType) {
		return _collectionPersistenceFinderByC_DTET.count(
			finderCache, new Object[] {companyId, dispatchTaskExecutorType});
	}

	/**
	 * Returns the number of dispatch triggers that the user has permission to view where companyId = &#63; and dispatchTaskExecutorType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param dispatchTaskExecutorType the dispatch task executor type
	 * @return the number of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public int filterCountByC_DTET(
		long companyId, String dispatchTaskExecutorType) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_DTET(companyId, dispatchTaskExecutorType);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DispatchTrigger> dispatchTriggers = findByC_DTET(
				companyId, dispatchTaskExecutorType);

			dispatchTriggers = InlineSQLHelperUtil.filter(dispatchTriggers);

			return dispatchTriggers.size();
		}

		dispatchTaskExecutorType = Objects.toString(
			dispatchTaskExecutorType, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE);

		sb.append(_FINDER_COLUMN_C_DTET_COMPANYID_2);

		boolean bindDispatchTaskExecutorType = false;

		if (dispatchTaskExecutorType.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_DTET_DISPATCHTASKEXECUTORTYPE_3);
		}
		else {
			bindDispatchTaskExecutorType = true;

			sb.append(_FINDER_COLUMN_C_DTET_DISPATCHTASKEXECUTORTYPE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindDispatchTaskExecutorType) {
				queryPos.add(dispatchTaskExecutorType);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_DTET_COMPANYID_2 =
		"dispatchTrigger.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_DTET_DISPATCHTASKEXECUTORTYPE_2 =
			"dispatchTrigger.dispatchTaskExecutorType = ?";

	private static final String
		_FINDER_COLUMN_C_DTET_DISPATCHTASKEXECUTORTYPE_3 =
			"(dispatchTrigger.dispatchTaskExecutorType IS NULL OR dispatchTrigger.dispatchTaskExecutorType = '')";

	private FinderPath _finderPathFetchByC_N;
	private UniquePersistenceFinder<DispatchTrigger>
		_uniquePersistenceFinderByC_N;

	/**
	 * Returns the dispatch trigger where companyId = &#63; and name = &#63; or throws a <code>NoSuchTriggerException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByC_N(long companyId, String name)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByC_N(companyId, name);

		if (dispatchTrigger == null) {
			String message =
				_uniquePersistenceFinderByC_N.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchTriggerException(message);
		}

		return dispatchTrigger;
	}

	/**
	 * Returns the dispatch trigger where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the dispatch trigger where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return _uniquePersistenceFinderByC_N.fetch(
			finderCache, new Object[] {companyId, name}, useFinderCache);
	}

	/**
	 * Removes the dispatch trigger where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the dispatch trigger that was removed
	 */
	@Override
	public DispatchTrigger removeByC_N(long companyId, String name)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = findByC_N(companyId, name);

		return remove(dispatchTrigger);
	}

	/**
	 * Returns the number of dispatch triggers where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		return _uniquePersistenceFinderByC_N.count(
			finderCache, new Object[] {companyId, name});
	}

	private FinderPath _finderPathWithPaginationFindByA_DTCM;
	private FinderPath _finderPathWithoutPaginationFindByA_DTCM;
	private FinderPath _finderPathCountByA_DTCM;
	private FinderPath _finderPathWithPaginationCountByA_DTCM;

	/**
	 * Returns all the dispatch triggers where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @return the matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByA_DTCM(
		boolean active, int dispatchTaskClusterMode) {

		return findByA_DTCM(
			active, dispatchTaskClusterMode, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByA_DTCM(
		boolean active, int dispatchTaskClusterMode, int start, int end) {

		return findByA_DTCM(active, dispatchTaskClusterMode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByA_DTCM(
		boolean active, int dispatchTaskClusterMode, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findByA_DTCM(
			active, dispatchTaskClusterMode, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByA_DTCM(
		boolean active, int dispatchTaskClusterMode, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByA_DTCM;
				finderArgs = new Object[] {active, dispatchTaskClusterMode};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByA_DTCM;
			finderArgs = new Object[] {
				active, dispatchTaskClusterMode, start, end, orderByComparator
			};
		}

		List<DispatchTrigger> list = null;

		if (useFinderCache) {
			list = (List<DispatchTrigger>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DispatchTrigger dispatchTrigger : list) {
					if ((active != dispatchTrigger.isActive()) ||
						(dispatchTaskClusterMode !=
							dispatchTrigger.getDispatchTaskClusterMode())) {

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

			sb.append(_SQL_SELECT_DISPATCHTRIGGER_WHERE);

			sb.append(_FINDER_COLUMN_A_DTCM_ACTIVE_2);

			sb.append(_FINDER_COLUMN_A_DTCM_DISPATCHTASKCLUSTERMODE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				queryPos.add(dispatchTaskClusterMode);

				list = (List<DispatchTrigger>)QueryUtil.list(
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
	 * Returns the first dispatch trigger in the ordered set where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByA_DTCM_First(
			boolean active, int dispatchTaskClusterMode,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByA_DTCM_First(
			active, dispatchTaskClusterMode, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("active=");
		sb.append(active);

		sb.append(", dispatchTaskClusterMode=");
		sb.append(dispatchTaskClusterMode);

		sb.append("}");

		throw new NoSuchTriggerException(sb.toString());
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByA_DTCM_First(
		boolean active, int dispatchTaskClusterMode,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		List<DispatchTrigger> list = findByA_DTCM(
			active, dispatchTaskClusterMode, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the dispatch triggers that the user has permission to view where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @return the matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByA_DTCM(
		boolean active, int dispatchTaskClusterMode) {

		return filterFindByA_DTCM(
			active, dispatchTaskClusterMode, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers that the user has permission to view where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByA_DTCM(
		boolean active, int dispatchTaskClusterMode, int start, int end) {

		return filterFindByA_DTCM(
			active, dispatchTaskClusterMode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers that the user has permissions to view where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByA_DTCM(
		boolean active, int dispatchTaskClusterMode, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByA_DTCM(
				active, dispatchTaskClusterMode, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByA_DTCM(
					active, dispatchTaskClusterMode, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_A_DTCM_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_A_DTCM_DISPATCHTASKCLUSTERMODE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					DispatchTriggerModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			queryPos.add(dispatchTaskClusterMode);

			return (List<DispatchTrigger>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the dispatch triggers that the user has permission to view where active = &#63; and dispatchTaskClusterMode = any &#63;.
	 *
	 * @param active the active
	 * @param dispatchTaskClusterModes the dispatch task cluster modes
	 * @return the matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByA_DTCM(
		boolean active, int[] dispatchTaskClusterModes) {

		return filterFindByA_DTCM(
			active, dispatchTaskClusterModes, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers that the user has permission to view where active = &#63; and dispatchTaskClusterMode = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterModes the dispatch task cluster modes
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByA_DTCM(
		boolean active, int[] dispatchTaskClusterModes, int start, int end) {

		return filterFindByA_DTCM(
			active, dispatchTaskClusterModes, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers that the user has permission to view where active = &#63; and dispatchTaskClusterMode = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterModes the dispatch task cluster modes
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByA_DTCM(
		boolean active, int[] dispatchTaskClusterModes, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByA_DTCM(
				active, dispatchTaskClusterModes, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByA_DTCM(
					active, dispatchTaskClusterModes, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
		}

		if (dispatchTaskClusterModes == null) {
			dispatchTaskClusterModes = new int[0];
		}
		else if (dispatchTaskClusterModes.length > 1) {
			dispatchTaskClusterModes = ArrayUtil.sortedUnique(
				dispatchTaskClusterModes);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_A_DTCM_ACTIVE_2_SQL);

		if (dispatchTaskClusterModes.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_A_DTCM_DISPATCHTASKCLUSTERMODE_7);

			sb.append(StringUtil.merge(dispatchTaskClusterModes));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					DispatchTriggerModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			return (List<DispatchTrigger>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the dispatch triggers where active = &#63; and dispatchTaskClusterMode = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterModes the dispatch task cluster modes
	 * @return the matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByA_DTCM(
		boolean active, int[] dispatchTaskClusterModes) {

		return findByA_DTCM(
			active, dispatchTaskClusterModes, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers where active = &#63; and dispatchTaskClusterMode = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterModes the dispatch task cluster modes
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByA_DTCM(
		boolean active, int[] dispatchTaskClusterModes, int start, int end) {

		return findByA_DTCM(active, dispatchTaskClusterModes, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where active = &#63; and dispatchTaskClusterMode = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterModes the dispatch task cluster modes
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByA_DTCM(
		boolean active, int[] dispatchTaskClusterModes, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findByA_DTCM(
			active, dispatchTaskClusterModes, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where active = &#63; and dispatchTaskClusterMode = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param dispatchTaskClusterModes the dispatch task cluster modes
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByA_DTCM(
		boolean active, int[] dispatchTaskClusterModes, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
		boolean useFinderCache) {

		if (dispatchTaskClusterModes == null) {
			dispatchTaskClusterModes = new int[0];
		}
		else if (dispatchTaskClusterModes.length > 1) {
			dispatchTaskClusterModes = ArrayUtil.sortedUnique(
				dispatchTaskClusterModes);
		}

		if (dispatchTaskClusterModes.length == 1) {
			return findByA_DTCM(
				active, dispatchTaskClusterModes[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					active, StringUtil.merge(dispatchTaskClusterModes)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				active, StringUtil.merge(dispatchTaskClusterModes), start, end,
				orderByComparator
			};
		}

		List<DispatchTrigger> list = null;

		if (useFinderCache) {
			list = (List<DispatchTrigger>)finderCache.getResult(
				_finderPathWithPaginationFindByA_DTCM, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DispatchTrigger dispatchTrigger : list) {
					if ((active != dispatchTrigger.isActive()) ||
						!ArrayUtil.contains(
							dispatchTaskClusterModes,
							dispatchTrigger.getDispatchTaskClusterMode())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_DISPATCHTRIGGER_WHERE);

			sb.append(_FINDER_COLUMN_A_DTCM_ACTIVE_2);

			if (dispatchTaskClusterModes.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_A_DTCM_DISPATCHTASKCLUSTERMODE_7);

				sb.append(StringUtil.merge(dispatchTaskClusterModes));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				list = (List<DispatchTrigger>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByA_DTCM, finderArgs,
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
	 * Removes all the dispatch triggers where active = &#63; and dispatchTaskClusterMode = &#63; from the database.
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 */
	@Override
	public void removeByA_DTCM(boolean active, int dispatchTaskClusterMode) {
		for (DispatchTrigger dispatchTrigger :
				findByA_DTCM(
					active, dispatchTaskClusterMode, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(dispatchTrigger);
		}
	}

	/**
	 * Returns the number of dispatch triggers where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByA_DTCM(boolean active, int dispatchTaskClusterMode) {
		FinderPath finderPath = _finderPathCountByA_DTCM;

		Object[] finderArgs = new Object[] {active, dispatchTaskClusterMode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DISPATCHTRIGGER_WHERE);

			sb.append(_FINDER_COLUMN_A_DTCM_ACTIVE_2);

			sb.append(_FINDER_COLUMN_A_DTCM_DISPATCHTASKCLUSTERMODE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				queryPos.add(dispatchTaskClusterMode);

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
	 * Returns the number of dispatch triggers where active = &#63; and dispatchTaskClusterMode = any &#63;.
	 *
	 * @param active the active
	 * @param dispatchTaskClusterModes the dispatch task cluster modes
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByA_DTCM(boolean active, int[] dispatchTaskClusterModes) {
		if (dispatchTaskClusterModes == null) {
			dispatchTaskClusterModes = new int[0];
		}
		else if (dispatchTaskClusterModes.length > 1) {
			dispatchTaskClusterModes = ArrayUtil.sortedUnique(
				dispatchTaskClusterModes);
		}

		Object[] finderArgs = new Object[] {
			active, StringUtil.merge(dispatchTaskClusterModes)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByA_DTCM, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_DISPATCHTRIGGER_WHERE);

			sb.append(_FINDER_COLUMN_A_DTCM_ACTIVE_2);

			if (dispatchTaskClusterModes.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_A_DTCM_DISPATCHTASKCLUSTERMODE_7);

				sb.append(StringUtil.merge(dispatchTaskClusterModes));

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

				queryPos.add(active);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByA_DTCM, finderArgs, count);
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
	 * Returns the number of dispatch triggers that the user has permission to view where active = &#63; and dispatchTaskClusterMode = &#63;.
	 *
	 * @param active the active
	 * @param dispatchTaskClusterMode the dispatch task cluster mode
	 * @return the number of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public int filterCountByA_DTCM(
		boolean active, int dispatchTaskClusterMode) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByA_DTCM(active, dispatchTaskClusterMode);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DispatchTrigger> dispatchTriggers = findByA_DTCM(
				active, dispatchTaskClusterMode);

			dispatchTriggers = InlineSQLHelperUtil.filter(dispatchTriggers);

			return dispatchTriggers.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE);

		sb.append(_FINDER_COLUMN_A_DTCM_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_A_DTCM_DISPATCHTASKCLUSTERMODE_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			queryPos.add(dispatchTaskClusterMode);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of dispatch triggers that the user has permission to view where active = &#63; and dispatchTaskClusterMode = any &#63;.
	 *
	 * @param active the active
	 * @param dispatchTaskClusterModes the dispatch task cluster modes
	 * @return the number of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public int filterCountByA_DTCM(
		boolean active, int[] dispatchTaskClusterModes) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByA_DTCM(active, dispatchTaskClusterModes);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DispatchTrigger> dispatchTriggers = InlineSQLHelperUtil.filter(
				findByA_DTCM(active, dispatchTaskClusterModes));

			return dispatchTriggers.size();
		}

		if (dispatchTaskClusterModes == null) {
			dispatchTaskClusterModes = new int[0];
		}
		else if (dispatchTaskClusterModes.length > 1) {
			dispatchTaskClusterModes = ArrayUtil.sortedUnique(
				dispatchTaskClusterModes);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE);

		sb.append(_FINDER_COLUMN_A_DTCM_ACTIVE_2_SQL);

		if (dispatchTaskClusterModes.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_A_DTCM_DISPATCHTASKCLUSTERMODE_7);

			sb.append(StringUtil.merge(dispatchTaskClusterModes));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_A_DTCM_ACTIVE_2 =
		"dispatchTrigger.active = ? AND ";

	private static final String _FINDER_COLUMN_A_DTCM_ACTIVE_2_SQL =
		"dispatchTrigger.active_ = ? AND ";

	private static final String
		_FINDER_COLUMN_A_DTCM_DISPATCHTASKCLUSTERMODE_2 =
			"dispatchTrigger.dispatchTaskClusterMode = ?";

	private static final String
		_FINDER_COLUMN_A_DTCM_DISPATCHTASKCLUSTERMODE_7 =
			"dispatchTrigger.dispatchTaskClusterMode IN (";

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<DispatchTrigger>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the dispatch trigger where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchTriggerException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByERC_C(
			externalReferenceCode, companyId);

		if (dispatchTrigger == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchTriggerException(message);
		}

		return dispatchTrigger;
	}

	/**
	 * Returns the dispatch trigger where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the dispatch trigger where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_C.fetch(
			finderCache, new Object[] {externalReferenceCode, companyId},
			useFinderCache);
	}

	/**
	 * Removes the dispatch trigger where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the dispatch trigger that was removed
	 */
	@Override
	public DispatchTrigger removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = findByERC_C(
			externalReferenceCode, companyId);

		return remove(dispatchTrigger);
	}

	/**
	 * Returns the number of dispatch triggers where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public DispatchTriggerPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put("system", "system_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DispatchTrigger.class);

		setModelImplClass(DispatchTriggerImpl.class);
		setModelPKClass(long.class);

		setTable(DispatchTriggerTable.INSTANCE);
	}

	/**
	 * Caches the dispatch trigger in the entity cache if it is enabled.
	 *
	 * @param dispatchTrigger the dispatch trigger
	 */
	@Override
	public void cacheResult(DispatchTrigger dispatchTrigger) {
		entityCache.putResult(
			DispatchTriggerImpl.class, dispatchTrigger.getPrimaryKey(),
			dispatchTrigger);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				dispatchTrigger.getCompanyId(), dispatchTrigger.getName()
			},
			dispatchTrigger);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				dispatchTrigger.getExternalReferenceCode(),
				dispatchTrigger.getCompanyId()
			},
			dispatchTrigger);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the dispatch triggers in the entity cache if it is enabled.
	 *
	 * @param dispatchTriggers the dispatch triggers
	 */
	@Override
	public void cacheResult(List<DispatchTrigger> dispatchTriggers) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (dispatchTriggers.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DispatchTrigger dispatchTrigger : dispatchTriggers) {
			if (entityCache.getResult(
					DispatchTriggerImpl.class,
					dispatchTrigger.getPrimaryKey()) == null) {

				cacheResult(dispatchTrigger);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		DispatchTriggerModelImpl dispatchTriggerModelImpl) {

		Object[] args = new Object[] {
			dispatchTriggerModelImpl.getCompanyId(),
			dispatchTriggerModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathFetchByC_N, args, dispatchTriggerModelImpl);

		args = new Object[] {
			dispatchTriggerModelImpl.getExternalReferenceCode(),
			dispatchTriggerModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args, dispatchTriggerModelImpl);
	}

	/**
	 * Creates a new dispatch trigger with the primary key. Does not add the dispatch trigger to the database.
	 *
	 * @param dispatchTriggerId the primary key for the new dispatch trigger
	 * @return the new dispatch trigger
	 */
	@Override
	public DispatchTrigger create(long dispatchTriggerId) {
		DispatchTrigger dispatchTrigger = new DispatchTriggerImpl();

		dispatchTrigger.setNew(true);
		dispatchTrigger.setPrimaryKey(dispatchTriggerId);

		String uuid = PortalUUIDUtil.generate();

		dispatchTrigger.setUuid(uuid);

		dispatchTrigger.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dispatchTrigger;
	}

	/**
	 * Removes the dispatch trigger with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dispatchTriggerId the primary key of the dispatch trigger
	 * @return the dispatch trigger that was removed
	 * @throws NoSuchTriggerException if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger remove(long dispatchTriggerId)
		throws NoSuchTriggerException {

		return remove((Serializable)dispatchTriggerId);
	}

	@Override
	protected DispatchTrigger removeImpl(DispatchTrigger dispatchTrigger) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dispatchTrigger)) {
				dispatchTrigger = (DispatchTrigger)session.get(
					DispatchTriggerImpl.class,
					dispatchTrigger.getPrimaryKeyObj());
			}

			if (dispatchTrigger != null) {
				session.delete(dispatchTrigger);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dispatchTrigger != null) {
			clearCache(dispatchTrigger);
		}

		return dispatchTrigger;
	}

	@Override
	public DispatchTrigger updateImpl(DispatchTrigger dispatchTrigger) {
		boolean isNew = dispatchTrigger.isNew();

		if (!(dispatchTrigger instanceof DispatchTriggerModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dispatchTrigger.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					dispatchTrigger);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dispatchTrigger proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DispatchTrigger implementation " +
					dispatchTrigger.getClass());
		}

		DispatchTriggerModelImpl dispatchTriggerModelImpl =
			(DispatchTriggerModelImpl)dispatchTrigger;

		if (Validator.isNull(dispatchTrigger.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dispatchTrigger.setUuid(uuid);
		}

		if (Validator.isNull(dispatchTrigger.getExternalReferenceCode())) {
			dispatchTrigger.setExternalReferenceCode(dispatchTrigger.getUuid());
		}
		else {
			if (!Objects.equals(
					dispatchTriggerModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					dispatchTrigger.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = dispatchTrigger.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = dispatchTrigger.getPrimaryKey();
					}

					try {
						dispatchTrigger.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								DispatchTrigger.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								dispatchTrigger.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			DispatchTrigger ercDispatchTrigger = fetchByERC_C(
				dispatchTrigger.getExternalReferenceCode(),
				dispatchTrigger.getCompanyId());

			if (isNew) {
				if (ercDispatchTrigger != null) {
					throw new DuplicateDispatchTriggerExternalReferenceCodeException(
						"Duplicate dispatch trigger with external reference code " +
							dispatchTrigger.getExternalReferenceCode() +
								" and company " +
									dispatchTrigger.getCompanyId());
				}
			}
			else {
				if ((ercDispatchTrigger != null) &&
					(dispatchTrigger.getDispatchTriggerId() !=
						ercDispatchTrigger.getDispatchTriggerId())) {

					throw new DuplicateDispatchTriggerExternalReferenceCodeException(
						"Duplicate dispatch trigger with external reference code " +
							dispatchTrigger.getExternalReferenceCode() +
								" and company " +
									dispatchTrigger.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (dispatchTrigger.getCreateDate() == null)) {
			if (serviceContext == null) {
				dispatchTrigger.setCreateDate(date);
			}
			else {
				dispatchTrigger.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!dispatchTriggerModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dispatchTrigger.setModifiedDate(date);
			}
			else {
				dispatchTrigger.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(dispatchTrigger);
			}
			else {
				dispatchTrigger = (DispatchTrigger)session.merge(
					dispatchTrigger);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DispatchTriggerImpl.class, dispatchTriggerModelImpl, false, true);

		cacheUniqueFindersCache(dispatchTriggerModelImpl);

		if (isNew) {
			dispatchTrigger.setNew(false);
		}

		dispatchTrigger.resetOriginalValues();

		return dispatchTrigger;
	}

	/**
	 * Returns the dispatch trigger with the primary key or throws a <code>NoSuchTriggerException</code> if it could not be found.
	 *
	 * @param dispatchTriggerId the primary key of the dispatch trigger
	 * @return the dispatch trigger
	 * @throws NoSuchTriggerException if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger findByPrimaryKey(long dispatchTriggerId)
		throws NoSuchTriggerException {

		return findByPrimaryKey((Serializable)dispatchTriggerId);
	}

	/**
	 * Returns the dispatch trigger with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dispatchTriggerId the primary key of the dispatch trigger
	 * @return the dispatch trigger, or <code>null</code> if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger fetchByPrimaryKey(long dispatchTriggerId) {
		return fetchByPrimaryKey((Serializable)dispatchTriggerId);
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
		return "dispatchTriggerId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DISPATCHTRIGGER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DispatchTriggerModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dispatch trigger persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

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

		_collectionPersistenceFinderByUuid = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByUuid,
			_finderPathWithoutPaginationFindByUuid, _finderPathCountByUuid,
			_SQL_SELECT_DISPATCHTRIGGER_WHERE, _SQL_COUNT_DISPATCHTRIGGER_WHERE,
			DispatchTriggerModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"dispatchTrigger.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, DispatchTrigger::getUuid));

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

		_collectionPersistenceFinderByUuid_C =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_C,
				_finderPathWithoutPaginationFindByUuid_C,
				_finderPathCountByUuid_C, _SQL_SELECT_DISPATCHTRIGGER_WHERE,
				_SQL_COUNT_DISPATCHTRIGGER_WHERE,
				DispatchTriggerModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"dispatchTrigger.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, DispatchTrigger::getUuid),
				new FinderColumn<>(
					"dispatchTrigger.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, DispatchTrigger::getCompanyId));

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

		_collectionPersistenceFinderByCompanyId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCompanyId,
				_finderPathWithoutPaginationFindByCompanyId,
				_finderPathCountByCompanyId, _SQL_SELECT_DISPATCHTRIGGER_WHERE,
				_SQL_COUNT_DISPATCHTRIGGER_WHERE,
				DispatchTriggerModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"dispatchTrigger.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, DispatchTrigger::getCompanyId));

		_finderPathWithPaginationFindByActive = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActive",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"active_"}, true);

		_finderPathWithoutPaginationFindByActive = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActive",
			new String[] {Boolean.class.getName()}, new String[] {"active_"},
			true);

		_finderPathCountByActive = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActive",
			new String[] {Boolean.class.getName()}, new String[] {"active_"},
			false);

		_collectionPersistenceFinderByActive =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByActive,
				_finderPathWithoutPaginationFindByActive,
				_finderPathCountByActive, _SQL_SELECT_DISPATCHTRIGGER_WHERE,
				_SQL_COUNT_DISPATCHTRIGGER_WHERE,
				DispatchTriggerModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"dispatchTrigger.", "active", FinderColumn.Type.BOOLEAN,
					"=", true, true, DispatchTrigger::isActive));

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

		_collectionPersistenceFinderByC_U = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_U,
			_finderPathWithoutPaginationFindByC_U, _finderPathCountByC_U,
			_SQL_SELECT_DISPATCHTRIGGER_WHERE, _SQL_COUNT_DISPATCHTRIGGER_WHERE,
			DispatchTriggerModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"dispatchTrigger.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, DispatchTrigger::getCompanyId),
			new FinderColumn<>(
				"dispatchTrigger.", "userId", FinderColumn.Type.LONG, "=", true,
				true, DispatchTrigger::getUserId));

		_finderPathWithPaginationFindByC_DTET = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_DTET",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "dispatchTaskExecutorType"}, true);

		_finderPathWithoutPaginationFindByC_DTET = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_DTET",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "dispatchTaskExecutorType"}, true);

		_finderPathCountByC_DTET = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_DTET",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "dispatchTaskExecutorType"}, false);

		_collectionPersistenceFinderByC_DTET =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_DTET,
				_finderPathWithoutPaginationFindByC_DTET,
				_finderPathCountByC_DTET, _SQL_SELECT_DISPATCHTRIGGER_WHERE,
				_SQL_COUNT_DISPATCHTRIGGER_WHERE,
				DispatchTriggerModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"dispatchTrigger.", "companyId", FinderColumn.Type.LONG,
					"=", true, false, DispatchTrigger::getCompanyId),
				new FinderColumn<>(
					"dispatchTrigger.", "dispatchTaskExecutorType",
					FinderColumn.Type.STRING, "=", true, true,
					DispatchTrigger::getDispatchTaskExecutorType));

		_finderPathFetchByC_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		_uniquePersistenceFinderByC_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_N, _SQL_SELECT_DISPATCHTRIGGER_WHERE,
			new FinderColumn<>(
				"dispatchTrigger.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, DispatchTrigger::getCompanyId),
			new FinderColumn<>(
				"dispatchTrigger.", "name", FinderColumn.Type.STRING, "=", true,
				true, DispatchTrigger::getName));

		_finderPathWithPaginationFindByA_DTCM = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_DTCM",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"active_", "dispatchTaskClusterMode"}, true);

		_finderPathWithoutPaginationFindByA_DTCM = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_DTCM",
			new String[] {Boolean.class.getName(), Integer.class.getName()},
			new String[] {"active_", "dispatchTaskClusterMode"}, true);

		_finderPathCountByA_DTCM = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_DTCM",
			new String[] {Boolean.class.getName(), Integer.class.getName()},
			new String[] {"active_", "dispatchTaskClusterMode"}, false);

		_finderPathWithPaginationCountByA_DTCM = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByA_DTCM",
			new String[] {Boolean.class.getName(), Integer.class.getName()},
			new String[] {"active_", "dispatchTaskClusterMode"}, false);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C, _SQL_SELECT_DISPATCHTRIGGER_WHERE,
			new FinderColumn<>(
				"dispatchTrigger.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				DispatchTrigger::getExternalReferenceCode),
			new FinderColumn<>(
				"dispatchTrigger.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, DispatchTrigger::getCompanyId));

		DispatchTriggerUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		DispatchTriggerUtil.setPersistence(null);

		entityCache.removeCache(DispatchTriggerImpl.class.getName());
	}

	@Override
	@Reference(
		target = DispatchPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DispatchPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DispatchPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _ENTITY_ALIAS_PREFIX =
		DispatchTriggerModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_DISPATCHTRIGGER =
		"SELECT dispatchTrigger FROM DispatchTrigger dispatchTrigger";

	private static final String _SQL_SELECT_DISPATCHTRIGGER_WHERE =
		"SELECT dispatchTrigger FROM DispatchTrigger dispatchTrigger WHERE ";

	private static final String _SQL_COUNT_DISPATCHTRIGGER_WHERE =
		"SELECT COUNT(dispatchTrigger) FROM DispatchTrigger dispatchTrigger WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"dispatchTrigger.dispatchTriggerId";

	private static final String _FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE =
		"SELECT DISTINCT {dispatchTrigger.*} FROM DispatchTrigger dispatchTrigger WHERE ";

	private static final String
		_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {DispatchTrigger.*} FROM (SELECT DISTINCT dispatchTrigger.dispatchTriggerId FROM DispatchTrigger dispatchTrigger WHERE ";

	private static final String
		_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN DispatchTrigger ON TEMP_TABLE.dispatchTriggerId = DispatchTrigger.dispatchTriggerId";

	private static final String _FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE =
		"SELECT COUNT(DISTINCT dispatchTrigger.dispatchTriggerId) AS COUNT_VALUE FROM DispatchTrigger dispatchTrigger WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "dispatchTrigger";

	private static final String _FILTER_ENTITY_TABLE = "DispatchTrigger";

	private static final String _ORDER_BY_ENTITY_TABLE = "DispatchTrigger.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DispatchTrigger exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTriggerPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active", "system"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-452811771