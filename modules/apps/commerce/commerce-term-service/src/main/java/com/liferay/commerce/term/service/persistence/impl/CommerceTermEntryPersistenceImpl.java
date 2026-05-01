/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.service.persistence.impl;

import com.liferay.commerce.term.exception.DuplicateCommerceTermEntryExternalReferenceCodeException;
import com.liferay.commerce.term.exception.NoSuchTermEntryException;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryTable;
import com.liferay.commerce.term.model.impl.CommerceTermEntryImpl;
import com.liferay.commerce.term.model.impl.CommerceTermEntryModelImpl;
import com.liferay.commerce.term.service.persistence.CTermEntryLocalizationPersistence;
import com.liferay.commerce.term.service.persistence.CommerceTermEntryPersistence;
import com.liferay.commerce.term.service.persistence.CommerceTermEntryUtil;
import com.liferay.commerce.term.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
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

import java.sql.Timestamp;

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
 * The persistence implementation for the commerce term entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
@Component(service = CommerceTermEntryPersistence.class)
public class CommerceTermEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceTermEntry, NoSuchTermEntryException>
	implements CommerceTermEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceTermEntryUtil</code> to access the commerce term entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceTermEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<CommerceTermEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the commerce term entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry findByUuid_First(
			String uuid, OrderByComparator<CommerceTermEntry> orderByComparator)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (commerceTermEntry != null) {
			return commerceTermEntry;
		}

		throw new NoSuchTermEntryException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByUuid_First(
		String uuid, OrderByComparator<CommerceTermEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByUuid(
		String uuid, int start, int end) {

		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_COMMERCETERMENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceTermEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceTermEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceTermEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceTermEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			return (List<CommerceTermEntry>)QueryUtil.list(
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
	 * Removes all the commerce term entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of commerce term entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce term entries
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceTermEntry> commerceTermEntries = findByUuid(uuid);

			commerceTermEntries = InlineSQLHelperUtil.filter(
				commerceTermEntries);

			return commerceTermEntries.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCETERMENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
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
		"commerceTermEntry.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(commerceTermEntry.uuid_ IS NULL OR commerceTermEntry.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CommerceTermEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (commerceTermEntry != null) {
			return commerceTermEntry;
		}

		throw new NoSuchTermEntryException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first commerce term entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByUuid_C(
		String uuid, long companyId) {

		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_COMMERCETERMENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceTermEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceTermEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceTermEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceTermEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			return (List<CommerceTermEntry>)QueryUtil.list(
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
	 * Removes all the commerce term entries where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of commerce term entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce term entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceTermEntry> commerceTermEntries = findByUuid_C(
				uuid, companyId);

			commerceTermEntries = InlineSQLHelperUtil.filter(
				commerceTermEntries);

			return commerceTermEntries.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCETERMENTRY_WHERE);

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
			sb.toString(), CommerceTermEntry.class.getName(),
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
		"commerceTermEntry.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(commerceTermEntry.uuid_ IS NULL OR commerceTermEntry.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceTermEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;
	private CollectionPersistenceFinder<CommerceTermEntry>
		_collectionPersistenceFinderByC_A;

	/**
	 * Returns all the commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_A(long companyId, boolean active) {
		return findByC_A(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_A(
		long companyId, boolean active, int start, int end) {

		return findByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return findByC_A(
			companyId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_A.find(
			finderCache, new Object[] {companyId, active}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = fetchByC_A_First(
			companyId, active, orderByComparator);

		if (commerceTermEntry != null) {
			return commerceTermEntry;
		}

		throw new NoSuchTermEntryException(
			_collectionPersistenceFinderByC_A.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, active}));
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return _collectionPersistenceFinderByC_A.fetchFirst(
			finderCache, new Object[] {companyId, active}, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByC_A(
		long companyId, boolean active) {

		return filterFindByC_A(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByC_A(
		long companyId, boolean active, int start, int end) {

		return filterFindByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A(companyId, active, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_A(
					companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_COMMERCETERMENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceTermEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceTermEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceTermEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceTermEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			return (List<CommerceTermEntry>)QueryUtil.list(
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
	 * Removes all the commerce term entries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	@Override
	public void removeByC_A(long companyId, boolean active) {
		_collectionPersistenceFinderByC_A.remove(
			finderCache, new Object[] {companyId, active});
	}

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce term entries
	 */
	@Override
	public int countByC_A(long companyId, boolean active) {
		return _collectionPersistenceFinderByC_A.count(
			finderCache, new Object[] {companyId, active});
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	@Override
	public int filterCountByC_A(long companyId, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_A(companyId, active);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceTermEntry> commerceTermEntries = findByC_A(
				companyId, active);

			commerceTermEntries = InlineSQLHelperUtil.filter(
				commerceTermEntries);

			return commerceTermEntries.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCETERMENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_C_A_COMPANYID_2 =
		"commerceTermEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_ACTIVE_2_SQL =
		"commerceTermEntry.active_ = ?";

	private FinderPath _finderPathFetchByC_N;
	private UniquePersistenceFinder<CommerceTermEntry>
		_uniquePersistenceFinderByC_N;

	/**
	 * Returns the commerce term entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry findByC_N(long companyId, String name)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = fetchByC_N(companyId, name);

		if (commerceTermEntry == null) {
			String message =
				_uniquePersistenceFinderByC_N.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchTermEntryException(message);
		}

		return commerceTermEntry;
	}

	/**
	 * Returns the commerce term entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the commerce term entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return _uniquePersistenceFinderByC_N.fetch(
			finderCache, new Object[] {companyId, name}, useFinderCache);
	}

	/**
	 * Removes the commerce term entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the commerce term entry that was removed
	 */
	@Override
	public CommerceTermEntry removeByC_N(long companyId, String name)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = findByC_N(companyId, name);

		return remove(commerceTermEntry);
	}

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching commerce term entries
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		return _uniquePersistenceFinderByC_N.count(
			finderCache, new Object[] {companyId, name});
	}

	private FinderPath _finderPathWithPaginationFindByC_LikeType;
	private FinderPath _finderPathWithPaginationCountByC_LikeType;
	private CollectionPersistenceFinder<CommerceTermEntry>
		_collectionPersistenceFinderByC_LikeType;

	/**
	 * Returns all the commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type) {

		return findByC_LikeType(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type, int start, int end) {

		return findByC_LikeType(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return findByC_LikeType(
			companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_LikeType.find(
			finderCache, new Object[] {companyId, type}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry findByC_LikeType_First(
			long companyId, String type,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = fetchByC_LikeType_First(
			companyId, type, orderByComparator);

		if (commerceTermEntry != null) {
			return commerceTermEntry;
		}

		throw new NoSuchTermEntryException(
			_collectionPersistenceFinderByC_LikeType.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, type}));
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByC_LikeType_First(
		long companyId, String type,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return _collectionPersistenceFinderByC_LikeType.fetchFirst(
			finderCache, new Object[] {companyId, type}, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByC_LikeType(
		long companyId, String type) {

		return filterFindByC_LikeType(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end) {

		return filterFindByC_LikeType(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_LikeType(
				companyId, type, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_LikeType(
					companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator));
		}

		type = Objects.toString(type, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCETERMENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_LIKETYPE_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKETYPE_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_LIKETYPE_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceTermEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceTermEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceTermEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceTermEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindType) {
				queryPos.add(type);
			}

			return (List<CommerceTermEntry>)QueryUtil.list(
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
	 * Removes all the commerce term entries where companyId = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByC_LikeType(long companyId, String type) {
		_collectionPersistenceFinderByC_LikeType.remove(
			finderCache, new Object[] {companyId, type});
	}

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce term entries
	 */
	@Override
	public int countByC_LikeType(long companyId, String type) {
		return _collectionPersistenceFinderByC_LikeType.count(
			finderCache, new Object[] {companyId, type});
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	@Override
	public int filterCountByC_LikeType(long companyId, String type) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_LikeType(companyId, type);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceTermEntry> commerceTermEntries = findByC_LikeType(
				companyId, type);

			commerceTermEntries = InlineSQLHelperUtil.filter(
				commerceTermEntries);

			return commerceTermEntries.size();
		}

		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCETERMENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_LIKETYPE_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKETYPE_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_LIKETYPE_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindType) {
				queryPos.add(type);
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

	private static final String _FINDER_COLUMN_C_LIKETYPE_COMPANYID_2 =
		"commerceTermEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_LIKETYPE_TYPE_2_SQL =
		"commerceTermEntry.type_ LIKE ?";

	private static final String _FINDER_COLUMN_C_LIKETYPE_TYPE_3_SQL =
		"(commerceTermEntry.type_ IS NULL OR commerceTermEntry.type_ LIKE '')";

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;
	private CollectionPersistenceFinder<CommerceTermEntry>
		_collectionPersistenceFinderByLtD_S;

	/**
	 * Returns all the commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByLtD_S.find(
			finderCache, new Object[] {displayDate, status}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (commerceTermEntry != null) {
			return commerceTermEntry;
		}

		throw new NoSuchTermEntryException(
			_collectionPersistenceFinderByLtD_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {displayDate, status}));
	}

	/**
	 * Returns the first commerce term entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return _collectionPersistenceFinderByLtD_S.fetchFirst(
			finderCache, new Object[] {displayDate, status}, orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByLtD_S(
		Date displayDate, int status) {

		return filterFindByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByLtD_S(
		Date displayDate, int status, int start, int end) {

		return filterFindByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByLtD_S(
				displayDate, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByLtD_S(
					displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_COMMERCETERMENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceTermEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceTermEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceTermEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceTermEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindDisplayDate) {
				queryPos.add(new Timestamp(displayDate.getTime()));
			}

			queryPos.add(status);

			return (List<CommerceTermEntry>)QueryUtil.list(
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
	 * Removes all the commerce term entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		_collectionPersistenceFinderByLtD_S.remove(
			finderCache, new Object[] {displayDate, status});
	}

	/**
	 * Returns the number of commerce term entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce term entries
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		return _collectionPersistenceFinderByLtD_S.count(
			finderCache, new Object[] {displayDate, status});
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	@Override
	public int filterCountByLtD_S(Date displayDate, int status) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByLtD_S(displayDate, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceTermEntry> commerceTermEntries = findByLtD_S(
				displayDate, status);

			commerceTermEntries = InlineSQLHelperUtil.filter(
				commerceTermEntries);

			return commerceTermEntries.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCETERMENTRY_WHERE);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindDisplayDate) {
				queryPos.add(new Timestamp(displayDate.getTime()));
			}

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_1 =
		"commerceTermEntry.displayDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 =
		"commerceTermEntry.displayDate < ? AND ";

	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 =
		"commerceTermEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByLtE_S;
	private FinderPath _finderPathWithPaginationCountByLtE_S;
	private CollectionPersistenceFinder<CommerceTermEntry>
		_collectionPersistenceFinderByLtE_S;

	/**
	 * Returns all the commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status) {

		return findByLtE_S(
			expirationDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return findByLtE_S(expirationDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return findByLtE_S(
			expirationDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByLtE_S.find(
			finderCache, new Object[] {expirationDate, status}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry findByLtE_S_First(
			Date expirationDate, int status,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = fetchByLtE_S_First(
			expirationDate, status, orderByComparator);

		if (commerceTermEntry != null) {
			return commerceTermEntry;
		}

		throw new NoSuchTermEntryException(
			_collectionPersistenceFinderByLtE_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {expirationDate, status}));
	}

	/**
	 * Returns the first commerce term entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByLtE_S_First(
		Date expirationDate, int status,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return _collectionPersistenceFinderByLtE_S.fetchFirst(
			finderCache, new Object[] {expirationDate, status},
			orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByLtE_S(
		Date expirationDate, int status) {

		return filterFindByLtE_S(
			expirationDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return filterFindByLtE_S(expirationDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByLtE_S(
				expirationDate, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByLtE_S(
					expirationDate, status, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_COMMERCETERMENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTE_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceTermEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceTermEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceTermEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceTermEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindExpirationDate) {
				queryPos.add(new Timestamp(expirationDate.getTime()));
			}

			queryPos.add(status);

			return (List<CommerceTermEntry>)QueryUtil.list(
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
	 * Removes all the commerce term entries where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	@Override
	public void removeByLtE_S(Date expirationDate, int status) {
		_collectionPersistenceFinderByLtE_S.remove(
			finderCache, new Object[] {expirationDate, status});
	}

	/**
	 * Returns the number of commerce term entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce term entries
	 */
	@Override
	public int countByLtE_S(Date expirationDate, int status) {
		return _collectionPersistenceFinderByLtE_S.count(
			finderCache, new Object[] {expirationDate, status});
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	@Override
	public int filterCountByLtE_S(Date expirationDate, int status) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByLtE_S(expirationDate, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceTermEntry> commerceTermEntries = findByLtE_S(
				expirationDate, status);

			commerceTermEntries = InlineSQLHelperUtil.filter(
				commerceTermEntries);

			return commerceTermEntries.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCETERMENTRY_WHERE);

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTE_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindExpirationDate) {
				queryPos.add(new Timestamp(expirationDate.getTime()));
			}

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1 =
		"commerceTermEntry.expirationDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2 =
		"commerceTermEntry.expirationDate < ? AND ";

	private static final String _FINDER_COLUMN_LTE_S_STATUS_2 =
		"commerceTermEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_LikeType;
	private FinderPath _finderPathWithPaginationCountByC_A_LikeType;
	private CollectionPersistenceFinder<CommerceTermEntry>
		_collectionPersistenceFinderByC_A_LikeType;

	/**
	 * Returns all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type) {

		return findByC_A_LikeType(
			companyId, active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end) {

		return findByC_A_LikeType(companyId, active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return findByC_A_LikeType(
			companyId, active, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entries
	 */
	@Override
	public List<CommerceTermEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_A_LikeType.find(
			finderCache, new Object[] {companyId, active, type}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry findByC_A_LikeType_First(
			long companyId, boolean active, String type,
			OrderByComparator<CommerceTermEntry> orderByComparator)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = fetchByC_A_LikeType_First(
			companyId, active, type, orderByComparator);

		if (commerceTermEntry != null) {
			return commerceTermEntry;
		}

		throw new NoSuchTermEntryException(
			_collectionPersistenceFinderByC_A_LikeType.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, active, type}));
	}

	/**
	 * Returns the first commerce term entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByC_A_LikeType_First(
		long companyId, boolean active, String type,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		return _collectionPersistenceFinderByC_A_LikeType.fetchFirst(
			finderCache, new Object[] {companyId, active, type},
			orderByComparator);
	}

	/**
	 * Returns all the commerce term entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type) {

		return filterFindByC_A_LikeType(
			companyId, active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce term entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @return the range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end) {

		return filterFindByC_A_LikeType(
			companyId, active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entries that the user has permissions to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce term entries
	 * @param end the upper bound of the range of commerce term entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entries that the user has permission to view
	 */
	@Override
	public List<CommerceTermEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceTermEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A_LikeType(
				companyId, active, type, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_A_LikeType(
					companyId, active, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
		}

		type = Objects.toString(type, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCETERMENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_LIKETYPE_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_LIKETYPE_ACTIVE_2_SQL);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_LIKETYPE_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_A_LIKETYPE_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
					CommerceTermEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CommerceTermEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceTermEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceTermEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			if (bindType) {
				queryPos.add(type);
			}

			return (List<CommerceTermEntry>)QueryUtil.list(
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
	 * Removes all the commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 */
	@Override
	public void removeByC_A_LikeType(
		long companyId, boolean active, String type) {

		_collectionPersistenceFinderByC_A_LikeType.remove(
			finderCache, new Object[] {companyId, active, type});
	}

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce term entries
	 */
	@Override
	public int countByC_A_LikeType(
		long companyId, boolean active, String type) {

		return _collectionPersistenceFinderByC_A_LikeType.count(
			finderCache, new Object[] {companyId, active, type});
	}

	/**
	 * Returns the number of commerce term entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce term entries that the user has permission to view
	 */
	@Override
	public int filterCountByC_A_LikeType(
		long companyId, boolean active, String type) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_A_LikeType(companyId, active, type);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CommerceTermEntry> commerceTermEntries = findByC_A_LikeType(
				companyId, active, type);

			commerceTermEntries = InlineSQLHelperUtil.filter(
				commerceTermEntries);

			return commerceTermEntries.size();
		}

		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_COMMERCETERMENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_A_LIKETYPE_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_LIKETYPE_ACTIVE_2_SQL);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_LIKETYPE_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_A_LIKETYPE_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceTermEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			if (bindType) {
				queryPos.add(type);
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

	private static final String _FINDER_COLUMN_C_A_LIKETYPE_COMPANYID_2 =
		"commerceTermEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_LIKETYPE_ACTIVE_2_SQL =
		"commerceTermEntry.active_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_LIKETYPE_TYPE_2_SQL =
		"commerceTermEntry.type_ LIKE ?";

	private static final String _FINDER_COLUMN_C_A_LIKETYPE_TYPE_3_SQL =
		"(commerceTermEntry.type_ IS NULL OR commerceTermEntry.type_ LIKE '')";

	private FinderPath _finderPathFetchByC_P_T;
	private UniquePersistenceFinder<CommerceTermEntry>
		_uniquePersistenceFinderByC_P_T;

	/**
	 * Returns the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry findByC_P_T(
			long companyId, double priority, String type)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = fetchByC_P_T(
			companyId, priority, type);

		if (commerceTermEntry == null) {
			String message =
				_uniquePersistenceFinderByC_P_T.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {companyId, priority, type});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchTermEntryException(message);
		}

		return commerceTermEntry;
	}

	/**
	 * Returns the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByC_P_T(
		long companyId, double priority, String type) {

		return fetchByC_P_T(companyId, priority, type, true);
	}

	/**
	 * Returns the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByC_P_T(
		long companyId, double priority, String type, boolean useFinderCache) {

		return _uniquePersistenceFinderByC_P_T.fetch(
			finderCache, new Object[] {companyId, priority, type},
			useFinderCache);
	}

	/**
	 * Removes the commerce term entry where companyId = &#63; and priority = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the commerce term entry that was removed
	 */
	@Override
	public CommerceTermEntry removeByC_P_T(
			long companyId, double priority, String type)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = findByC_P_T(
			companyId, priority, type);

		return remove(commerceTermEntry);
	}

	/**
	 * Returns the number of commerce term entries where companyId = &#63; and priority = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param priority the priority
	 * @param type the type
	 * @return the number of matching commerce term entries
	 */
	@Override
	public int countByC_P_T(long companyId, double priority, String type) {
		return _uniquePersistenceFinderByC_P_T.count(
			finderCache, new Object[] {companyId, priority, type});
	}

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<CommerceTermEntry>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce term entry
	 * @throws NoSuchTermEntryException if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = fetchByERC_C(
			externalReferenceCode, companyId);

		if (commerceTermEntry == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchTermEntryException(message);
		}

		return commerceTermEntry;
	}

	/**
	 * Returns the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry, or <code>null</code> if a matching commerce term entry could not be found
	 */
	@Override
	public CommerceTermEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_C.fetch(
			finderCache, new Object[] {externalReferenceCode, companyId},
			useFinderCache);
	}

	/**
	 * Removes the commerce term entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce term entry that was removed
	 */
	@Override
	public CommerceTermEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchTermEntryException {

		CommerceTermEntry commerceTermEntry = findByERC_C(
			externalReferenceCode, companyId);

		return remove(commerceTermEntry);
	}

	/**
	 * Returns the number of commerce term entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce term entries
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public CommerceTermEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceTermEntry.class);

		setModelImplClass(CommerceTermEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceTermEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce term entry in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntry the commerce term entry
	 */
	@Override
	public void cacheResult(CommerceTermEntry commerceTermEntry) {
		entityCache.putResult(
			CommerceTermEntryImpl.class, commerceTermEntry.getPrimaryKey(),
			commerceTermEntry);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				commerceTermEntry.getCompanyId(), commerceTermEntry.getName()
			},
			commerceTermEntry);

		finderCache.putResult(
			_finderPathFetchByC_P_T,
			new Object[] {
				commerceTermEntry.getCompanyId(),
				commerceTermEntry.getPriority(), commerceTermEntry.getType()
			},
			commerceTermEntry);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				commerceTermEntry.getExternalReferenceCode(),
				commerceTermEntry.getCompanyId()
			},
			commerceTermEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce term entries in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntries the commerce term entries
	 */
	@Override
	public void cacheResult(List<CommerceTermEntry> commerceTermEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceTermEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceTermEntry commerceTermEntry : commerceTermEntries) {
			if (entityCache.getResult(
					CommerceTermEntryImpl.class,
					commerceTermEntry.getPrimaryKey()) == null) {

				cacheResult(commerceTermEntry);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTermEntryModelImpl commerceTermEntryModelImpl) {

		Object[] args = new Object[] {
			commerceTermEntryModelImpl.getCompanyId(),
			commerceTermEntryModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathFetchByC_N, args, commerceTermEntryModelImpl);

		args = new Object[] {
			commerceTermEntryModelImpl.getCompanyId(),
			commerceTermEntryModelImpl.getPriority(),
			commerceTermEntryModelImpl.getType()
		};

		finderCache.putResult(
			_finderPathFetchByC_P_T, args, commerceTermEntryModelImpl);

		args = new Object[] {
			commerceTermEntryModelImpl.getExternalReferenceCode(),
			commerceTermEntryModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args, commerceTermEntryModelImpl);
	}

	/**
	 * Creates a new commerce term entry with the primary key. Does not add the commerce term entry to the database.
	 *
	 * @param commerceTermEntryId the primary key for the new commerce term entry
	 * @return the new commerce term entry
	 */
	@Override
	public CommerceTermEntry create(long commerceTermEntryId) {
		CommerceTermEntry commerceTermEntry = new CommerceTermEntryImpl();

		commerceTermEntry.setNew(true);
		commerceTermEntry.setPrimaryKey(commerceTermEntryId);

		String uuid = PortalUUIDUtil.generate();

		commerceTermEntry.setUuid(uuid);

		commerceTermEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceTermEntry;
	}

	/**
	 * Removes the commerce term entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTermEntryId the primary key of the commerce term entry
	 * @return the commerce term entry that was removed
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	@Override
	public CommerceTermEntry remove(long commerceTermEntryId)
		throws NoSuchTermEntryException {

		return remove((Serializable)commerceTermEntryId);
	}

	@Override
	protected CommerceTermEntry removeImpl(
		CommerceTermEntry commerceTermEntry) {

		cTermEntryLocalizationPersistence.removeByCommerceTermEntryId(
			commerceTermEntry.getCommerceTermEntryId());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTermEntry)) {
				commerceTermEntry = (CommerceTermEntry)session.get(
					CommerceTermEntryImpl.class,
					commerceTermEntry.getPrimaryKeyObj());
			}

			if (commerceTermEntry != null) {
				session.delete(commerceTermEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceTermEntry != null) {
			clearCache(commerceTermEntry);
		}

		return commerceTermEntry;
	}

	@Override
	public CommerceTermEntry updateImpl(CommerceTermEntry commerceTermEntry) {
		boolean isNew = commerceTermEntry.isNew();

		if (!(commerceTermEntry instanceof CommerceTermEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceTermEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceTermEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTermEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTermEntry implementation " +
					commerceTermEntry.getClass());
		}

		CommerceTermEntryModelImpl commerceTermEntryModelImpl =
			(CommerceTermEntryModelImpl)commerceTermEntry;

		if (Validator.isNull(commerceTermEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceTermEntry.setUuid(uuid);
		}

		if (Validator.isNull(commerceTermEntry.getExternalReferenceCode())) {
			commerceTermEntry.setExternalReferenceCode(
				commerceTermEntry.getUuid());
		}
		else {
			if (!Objects.equals(
					commerceTermEntryModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					commerceTermEntry.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = commerceTermEntry.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = commerceTermEntry.getPrimaryKey();
					}

					try {
						commerceTermEntry.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								CommerceTermEntry.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								commerceTermEntry.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			CommerceTermEntry ercCommerceTermEntry = fetchByERC_C(
				commerceTermEntry.getExternalReferenceCode(),
				commerceTermEntry.getCompanyId());

			if (isNew) {
				if (ercCommerceTermEntry != null) {
					throw new DuplicateCommerceTermEntryExternalReferenceCodeException(
						"Duplicate commerce term entry with external reference code " +
							commerceTermEntry.getExternalReferenceCode() +
								" and company " +
									commerceTermEntry.getCompanyId());
				}
			}
			else {
				if ((ercCommerceTermEntry != null) &&
					(commerceTermEntry.getCommerceTermEntryId() !=
						ercCommerceTermEntry.getCommerceTermEntryId())) {

					throw new DuplicateCommerceTermEntryExternalReferenceCodeException(
						"Duplicate commerce term entry with external reference code " +
							commerceTermEntry.getExternalReferenceCode() +
								" and company " +
									commerceTermEntry.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceTermEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTermEntry.setCreateDate(date);
			}
			else {
				commerceTermEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceTermEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTermEntry.setModifiedDate(date);
			}
			else {
				commerceTermEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceTermEntry);
			}
			else {
				commerceTermEntry = (CommerceTermEntry)session.merge(
					commerceTermEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceTermEntryImpl.class, commerceTermEntryModelImpl, false,
			true);

		cacheUniqueFindersCache(commerceTermEntryModelImpl);

		if (isNew) {
			commerceTermEntry.setNew(false);
		}

		commerceTermEntry.resetOriginalValues();

		return commerceTermEntry;
	}

	/**
	 * Returns the commerce term entry with the primary key or throws a <code>NoSuchTermEntryException</code> if it could not be found.
	 *
	 * @param commerceTermEntryId the primary key of the commerce term entry
	 * @return the commerce term entry
	 * @throws NoSuchTermEntryException if a commerce term entry with the primary key could not be found
	 */
	@Override
	public CommerceTermEntry findByPrimaryKey(long commerceTermEntryId)
		throws NoSuchTermEntryException {

		return findByPrimaryKey((Serializable)commerceTermEntryId);
	}

	/**
	 * Returns the commerce term entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTermEntryId the primary key of the commerce term entry
	 * @return the commerce term entry, or <code>null</code> if a commerce term entry with the primary key could not be found
	 */
	@Override
	public CommerceTermEntry fetchByPrimaryKey(long commerceTermEntryId) {
		return fetchByPrimaryKey((Serializable)commerceTermEntryId);
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
		return "commerceTermEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCETERMENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceTermEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce term entry persistence.
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
			_SQL_SELECT_COMMERCETERMENTRY_WHERE,
			_SQL_COUNT_COMMERCETERMENTRY_WHERE,
			CommerceTermEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"commerceTermEntry.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, CommerceTermEntry::getUuid));

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
				_finderPathCountByUuid_C, _SQL_SELECT_COMMERCETERMENTRY_WHERE,
				_SQL_COUNT_COMMERCETERMENTRY_WHERE,
				CommerceTermEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"commerceTermEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, CommerceTermEntry::getUuid),
				new FinderColumn<>(
					"commerceTermEntry.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, CommerceTermEntry::getCompanyId));

		_finderPathWithPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_"}, true);

		_finderPathWithoutPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "active_"}, true);

		_finderPathCountByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "active_"}, false);

		_collectionPersistenceFinderByC_A = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_A,
			_finderPathWithoutPaginationFindByC_A, _finderPathCountByC_A,
			_SQL_SELECT_COMMERCETERMENTRY_WHERE,
			_SQL_COUNT_COMMERCETERMENTRY_WHERE,
			CommerceTermEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"commerceTermEntry.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, CommerceTermEntry::getCompanyId),
			new FinderColumn<>(
				"commerceTermEntry.", "active", FinderColumn.Type.BOOLEAN, "=",
				true, true, CommerceTermEntry::isActive));

		_finderPathFetchByC_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		_uniquePersistenceFinderByC_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_N, _SQL_SELECT_COMMERCETERMENTRY_WHERE,
			new FinderColumn<>(
				"commerceTermEntry.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, CommerceTermEntry::getCompanyId),
			new FinderColumn<>(
				"commerceTermEntry.", "name", FinderColumn.Type.STRING, "=",
				true, true, CommerceTermEntry::getName));

		_finderPathWithPaginationFindByC_LikeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LikeType",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "type_"}, true);

		_finderPathWithPaginationCountByC_LikeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LikeType",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "type_"}, false);

		_collectionPersistenceFinderByC_LikeType =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_LikeType, null,
				_finderPathWithPaginationCountByC_LikeType,
				_SQL_SELECT_COMMERCETERMENTRY_WHERE,
				_SQL_COUNT_COMMERCETERMENTRY_WHERE,
				CommerceTermEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"commerceTermEntry.", "companyId", FinderColumn.Type.LONG,
					"=", true, false, CommerceTermEntry::getCompanyId),
				new FinderColumn<>(
					"commerceTermEntry.", "type", FinderColumn.Type.STRING,
					"LIKE", true, true, CommerceTermEntry::getType));

		_finderPathWithPaginationFindByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"displayDate", "status"}, true);

		_finderPathWithPaginationCountByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"displayDate", "status"}, false);

		_collectionPersistenceFinderByLtD_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByLtD_S, null,
			_finderPathWithPaginationCountByLtD_S,
			_SQL_SELECT_COMMERCETERMENTRY_WHERE,
			_SQL_COUNT_COMMERCETERMENTRY_WHERE,
			CommerceTermEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"commerceTermEntry.", "displayDate", FinderColumn.Type.DATE,
				"<", true, false, CommerceTermEntry::getDisplayDate),
			new FinderColumn<>(
				"commerceTermEntry.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, CommerceTermEntry::getStatus));

		_finderPathWithPaginationFindByLtE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtE_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"expirationDate", "status"}, true);

		_finderPathWithPaginationCountByLtE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtE_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"expirationDate", "status"}, false);

		_collectionPersistenceFinderByLtE_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByLtE_S, null,
			_finderPathWithPaginationCountByLtE_S,
			_SQL_SELECT_COMMERCETERMENTRY_WHERE,
			_SQL_COUNT_COMMERCETERMENTRY_WHERE,
			CommerceTermEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"commerceTermEntry.", "expirationDate", FinderColumn.Type.DATE,
				"<", true, false, CommerceTermEntry::getExpirationDate),
			new FinderColumn<>(
				"commerceTermEntry.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, CommerceTermEntry::getStatus));

		_finderPathWithPaginationFindByC_A_LikeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_LikeType",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_", "type_"}, true);

		_finderPathWithPaginationCountByC_A_LikeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_A_LikeType",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "active_", "type_"}, false);

		_collectionPersistenceFinderByC_A_LikeType =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_A_LikeType, null,
				_finderPathWithPaginationCountByC_A_LikeType,
				_SQL_SELECT_COMMERCETERMENTRY_WHERE,
				_SQL_COUNT_COMMERCETERMENTRY_WHERE,
				CommerceTermEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"commerceTermEntry.", "companyId", FinderColumn.Type.LONG,
					"=", true, false, CommerceTermEntry::getCompanyId),
				new FinderColumn<>(
					"commerceTermEntry.", "active", FinderColumn.Type.BOOLEAN,
					"=", true, false, CommerceTermEntry::isActive),
				new FinderColumn<>(
					"commerceTermEntry.", "type", FinderColumn.Type.STRING,
					"LIKE", true, true, CommerceTermEntry::getType));

		_finderPathFetchByC_P_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_P_T",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "priority", "type_"}, true);

		_uniquePersistenceFinderByC_P_T = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_P_T, _SQL_SELECT_COMMERCETERMENTRY_WHERE,
			new FinderColumn<>(
				"commerceTermEntry.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, CommerceTermEntry::getCompanyId),
			new FinderColumn<>(
				"commerceTermEntry.", "priority", FinderColumn.Type.DOUBLE, "=",
				true, false, CommerceTermEntry::getPriority),
			new FinderColumn<>(
				"commerceTermEntry.", "type", FinderColumn.Type.STRING, "=",
				true, true, CommerceTermEntry::getType));

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C, _SQL_SELECT_COMMERCETERMENTRY_WHERE,
			new FinderColumn<>(
				"commerceTermEntry.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				CommerceTermEntry::getExternalReferenceCode),
			new FinderColumn<>(
				"commerceTermEntry.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, CommerceTermEntry::getCompanyId));

		CommerceTermEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceTermEntryUtil.setPersistence(null);

		entityCache.removeCache(CommerceTermEntryImpl.class.getName());
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

	@Reference
	protected CTermEntryLocalizationPersistence
		cTermEntryLocalizationPersistence;

	private static final String _ENTITY_ALIAS_PREFIX =
		CommerceTermEntryModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_COMMERCETERMENTRY =
		"SELECT commerceTermEntry FROM CommerceTermEntry commerceTermEntry";

	private static final String _SQL_SELECT_COMMERCETERMENTRY_WHERE =
		"SELECT commerceTermEntry FROM CommerceTermEntry commerceTermEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCETERMENTRY_WHERE =
		"SELECT COUNT(commerceTermEntry) FROM CommerceTermEntry commerceTermEntry WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"commerceTermEntry.commerceTermEntryId";

	private static final String _FILTER_SQL_SELECT_COMMERCETERMENTRY_WHERE =
		"SELECT DISTINCT {commerceTermEntry.*} FROM CommerceTermEntry commerceTermEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CommerceTermEntry.*} FROM (SELECT DISTINCT commerceTermEntry.commerceTermEntryId FROM CommerceTermEntry commerceTermEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCETERMENTRY_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CommerceTermEntry ON TEMP_TABLE.commerceTermEntryId = CommerceTermEntry.commerceTermEntryId";

	private static final String _FILTER_SQL_COUNT_COMMERCETERMENTRY_WHERE =
		"SELECT COUNT(DISTINCT commerceTermEntry.commerceTermEntryId) AS COUNT_VALUE FROM CommerceTermEntry commerceTermEntry WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "commerceTermEntry";

	private static final String _FILTER_ENTITY_TABLE = "CommerceTermEntry";

	private static final String _ORDER_BY_ENTITY_TABLE = "CommerceTermEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceTermEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTermEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1315622148