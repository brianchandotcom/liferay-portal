/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service.persistence.impl;

import com.liferay.oauth.client.persistence.exception.DuplicateOAuthClientEntryExternalReferenceCodeException;
import com.liferay.oauth.client.persistence.exception.NoSuchOAuthClientEntryException;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.model.OAuthClientEntryTable;
import com.liferay.oauth.client.persistence.model.impl.OAuthClientEntryImpl;
import com.liferay.oauth.client.persistence.model.impl.OAuthClientEntryModelImpl;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientEntryPersistence;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientEntryUtil;
import com.liferay.oauth.client.persistence.service.persistence.impl.constants.OAuthClientPersistenceConstants;
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
 * The persistence implementation for the o auth client entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = OAuthClientEntryPersistence.class)
public class OAuthClientEntryPersistenceImpl
	extends BasePersistenceImpl
		<OAuthClientEntry, NoSuchOAuthClientEntryException>
	implements OAuthClientEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OAuthClientEntryUtil</code> to access the o auth client entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OAuthClientEntryImpl.class.getName();

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
	private CollectionPersistenceFinder<OAuthClientEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the o auth client entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry findByUuid_First(
			String uuid, OrderByComparator<OAuthClientEntry> orderByComparator)
		throws NoSuchOAuthClientEntryException {

		OAuthClientEntry oAuthClientEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (oAuthClientEntry != null) {
			return oAuthClientEntry;
		}

		throw new NoSuchOAuthClientEntryException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first o auth client entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry fetchByUuid_First(
		String uuid, OrderByComparator<OAuthClientEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Returns all the o auth client entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByUuid(
		String uuid, int start, int end) {

		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					OAuthClientEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(OAuthClientEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			return (List<OAuthClientEntry>)QueryUtil.list(
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
	 * Removes all the o auth client entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of o auth client entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching o auth client entries
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of o auth client entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<OAuthClientEntry> oAuthClientEntries = findByUuid(uuid);

			oAuthClientEntries = InlineSQLHelperUtil.filter(oAuthClientEntries);

			return oAuthClientEntries.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientEntry.class.getName(),
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
		"oAuthClientEntry.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(oAuthClientEntry.uuid_ IS NULL OR oAuthClientEntry.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<OAuthClientEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the o auth client entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws NoSuchOAuthClientEntryException {

		OAuthClientEntry oAuthClientEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (oAuthClientEntry != null) {
			return oAuthClientEntry;
		}

		throw new NoSuchOAuthClientEntryException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first o auth client entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Returns all the o auth client entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByUuid_C(
		String uuid, long companyId) {

		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					OAuthClientEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(OAuthClientEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			return (List<OAuthClientEntry>)QueryUtil.list(
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
	 * Removes all the o auth client entries where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of o auth client entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching o auth client entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of o auth client entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<OAuthClientEntry> oAuthClientEntries = findByUuid_C(
				uuid, companyId);

			oAuthClientEntries = InlineSQLHelperUtil.filter(oAuthClientEntries);

			return oAuthClientEntries.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTENTRY_WHERE);

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
			sb.toString(), OAuthClientEntry.class.getName(),
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
		"oAuthClientEntry.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(oAuthClientEntry.uuid_ IS NULL OR oAuthClientEntry.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"oAuthClientEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<OAuthClientEntry>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the o auth client entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws NoSuchOAuthClientEntryException {

		OAuthClientEntry oAuthClientEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (oAuthClientEntry != null) {
			return oAuthClientEntry;
		}

		throw new NoSuchOAuthClientEntryException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<OAuthClientEntry> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Returns all the o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					OAuthClientEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(OAuthClientEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<OAuthClientEntry>)QueryUtil.list(
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
	 * Removes all the o auth client entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of o auth client entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<OAuthClientEntry> oAuthClientEntries = findByCompanyId(
				companyId);

			oAuthClientEntries = InlineSQLHelperUtil.filter(oAuthClientEntries);

			return oAuthClientEntries.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientEntry.class.getName(),
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
		"oAuthClientEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;
	private CollectionPersistenceFinder<OAuthClientEntry>
		_collectionPersistenceFinderByUserId;

	/**
	 * Returns all the o auth client entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUserId.find(
			finderCache, new Object[] {userId}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry findByUserId_First(
			long userId, OrderByComparator<OAuthClientEntry> orderByComparator)
		throws NoSuchOAuthClientEntryException {

		OAuthClientEntry oAuthClientEntry = fetchByUserId_First(
			userId, orderByComparator);

		if (oAuthClientEntry != null) {
			return oAuthClientEntry;
		}

		throw new NoSuchOAuthClientEntryException(
			_collectionPersistenceFinderByUserId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {userId}));
	}

	/**
	 * Returns the first o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry fetchByUserId_First(
		long userId, OrderByComparator<OAuthClientEntry> orderByComparator) {

		return _collectionPersistenceFinderByUserId.fetchFirst(
			finderCache, new Object[] {userId}, orderByComparator);
	}

	/**
	 * Returns all the o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByUserId(long userId) {
		return filterFindByUserId(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByUserId(
		long userId, int start, int end) {

		return filterFindByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUserId(userId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					OAuthClientEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(OAuthClientEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			return (List<OAuthClientEntry>)QueryUtil.list(
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
	 * Removes all the o auth client entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		_collectionPersistenceFinderByUserId.remove(
			finderCache, new Object[] {userId});
	}

	/**
	 * Returns the number of o auth client entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client entries
	 */
	@Override
	public int countByUserId(long userId) {
		return _collectionPersistenceFinderByUserId.count(
			finderCache, new Object[] {userId});
	}

	/**
	 * Returns the number of o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	@Override
	public int filterCountByUserId(long userId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUserId(userId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<OAuthClientEntry> oAuthClientEntries = findByUserId(userId);

			oAuthClientEntries = InlineSQLHelperUtil.filter(oAuthClientEntries);

			return oAuthClientEntries.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"oAuthClientEntry.userId = ?";

	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;
	private CollectionPersistenceFinder<OAuthClientEntry>
		_collectionPersistenceFinderByC_A;

	/**
	 * Returns all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI) {

		return findByC_A(
			companyId, authServerWellKnownURI, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI, int start, int end) {

		return findByC_A(companyId, authServerWellKnownURI, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return findByC_A(
			companyId, authServerWellKnownURI, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_A.find(
			finderCache, new Object[] {companyId, authServerWellKnownURI},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry findByC_A_First(
			long companyId, String authServerWellKnownURI,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws NoSuchOAuthClientEntryException {

		OAuthClientEntry oAuthClientEntry = fetchByC_A_First(
			companyId, authServerWellKnownURI, orderByComparator);

		if (oAuthClientEntry != null) {
			return oAuthClientEntry;
		}

		throw new NoSuchOAuthClientEntryException(
			_collectionPersistenceFinderByC_A.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, authServerWellKnownURI}));
	}

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry fetchByC_A_First(
		long companyId, String authServerWellKnownURI,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return _collectionPersistenceFinderByC_A.fetchFirst(
			finderCache, new Object[] {companyId, authServerWellKnownURI},
			orderByComparator);
	}

	/**
	 * Returns all the o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByC_A(
		long companyId, String authServerWellKnownURI) {

		return filterFindByC_A(
			companyId, authServerWellKnownURI, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByC_A(
		long companyId, String authServerWellKnownURI, int start, int end) {

		return filterFindByC_A(
			companyId, authServerWellKnownURI, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	@Override
	public List<OAuthClientEntry> filterFindByC_A(
		long companyId, String authServerWellKnownURI, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A(
				companyId, authServerWellKnownURI, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_A(
					companyId, authServerWellKnownURI, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator));
		}

		authServerWellKnownURI = Objects.toString(authServerWellKnownURI, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		boolean bindAuthServerWellKnownURI = false;

		if (authServerWellKnownURI.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_AUTHSERVERWELLKNOWNURI_3);
		}
		else {
			bindAuthServerWellKnownURI = true;

			sb.append(_FINDER_COLUMN_C_A_AUTHSERVERWELLKNOWNURI_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					OAuthClientEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(OAuthClientEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindAuthServerWellKnownURI) {
				queryPos.add(authServerWellKnownURI);
			}

			return (List<OAuthClientEntry>)QueryUtil.list(
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
	 * Removes all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 */
	@Override
	public void removeByC_A(long companyId, String authServerWellKnownURI) {
		_collectionPersistenceFinderByC_A.remove(
			finderCache, new Object[] {companyId, authServerWellKnownURI});
	}

	/**
	 * Returns the number of o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the number of matching o auth client entries
	 */
	@Override
	public int countByC_A(long companyId, String authServerWellKnownURI) {
		return _collectionPersistenceFinderByC_A.count(
			finderCache, new Object[] {companyId, authServerWellKnownURI});
	}

	/**
	 * Returns the number of o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	@Override
	public int filterCountByC_A(long companyId, String authServerWellKnownURI) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_A(companyId, authServerWellKnownURI);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<OAuthClientEntry> oAuthClientEntries = findByC_A(
				companyId, authServerWellKnownURI);

			oAuthClientEntries = InlineSQLHelperUtil.filter(oAuthClientEntries);

			return oAuthClientEntries.size();
		}

		authServerWellKnownURI = Objects.toString(authServerWellKnownURI, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		boolean bindAuthServerWellKnownURI = false;

		if (authServerWellKnownURI.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_AUTHSERVERWELLKNOWNURI_3);
		}
		else {
			bindAuthServerWellKnownURI = true;

			sb.append(_FINDER_COLUMN_C_A_AUTHSERVERWELLKNOWNURI_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindAuthServerWellKnownURI) {
				queryPos.add(authServerWellKnownURI);
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

	private static final String _FINDER_COLUMN_C_A_COMPANYID_2 =
		"oAuthClientEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_AUTHSERVERWELLKNOWNURI_2 =
		"oAuthClientEntry.authServerWellKnownURI = ?";

	private static final String _FINDER_COLUMN_C_A_AUTHSERVERWELLKNOWNURI_3 =
		"(oAuthClientEntry.authServerWellKnownURI IS NULL OR oAuthClientEntry.authServerWellKnownURI = '')";

	private FinderPath _finderPathFetchByC_A_C;
	private UniquePersistenceFinder<OAuthClientEntry>
		_uniquePersistenceFinderByC_A_C;

	/**
	 * Returns the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; or throws a <code>NoSuchOAuthClientEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry findByC_A_C(
			long companyId, String authServerWellKnownURI, String clientId)
		throws NoSuchOAuthClientEntryException {

		OAuthClientEntry oAuthClientEntry = fetchByC_A_C(
			companyId, authServerWellKnownURI, clientId);

		if (oAuthClientEntry == null) {
			String message =
				_uniquePersistenceFinderByC_A_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {companyId, authServerWellKnownURI, clientId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchOAuthClientEntryException(message);
		}

		return oAuthClientEntry;
	}

	/**
	 * Returns the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry fetchByC_A_C(
		long companyId, String authServerWellKnownURI, String clientId) {

		return fetchByC_A_C(companyId, authServerWellKnownURI, clientId, true);
	}

	/**
	 * Returns the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry fetchByC_A_C(
		long companyId, String authServerWellKnownURI, String clientId,
		boolean useFinderCache) {

		return _uniquePersistenceFinderByC_A_C.fetch(
			finderCache,
			new Object[] {companyId, authServerWellKnownURI, clientId},
			useFinderCache);
	}

	/**
	 * Removes the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the o auth client entry that was removed
	 */
	@Override
	public OAuthClientEntry removeByC_A_C(
			long companyId, String authServerWellKnownURI, String clientId)
		throws NoSuchOAuthClientEntryException {

		OAuthClientEntry oAuthClientEntry = findByC_A_C(
			companyId, authServerWellKnownURI, clientId);

		return remove(oAuthClientEntry);
	}

	/**
	 * Returns the number of o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the number of matching o auth client entries
	 */
	@Override
	public int countByC_A_C(
		long companyId, String authServerWellKnownURI, String clientId) {

		return _uniquePersistenceFinderByC_A_C.count(
			finderCache,
			new Object[] {companyId, authServerWellKnownURI, clientId});
	}

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<OAuthClientEntry>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the o auth client entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchOAuthClientEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchOAuthClientEntryException {

		OAuthClientEntry oAuthClientEntry = fetchByERC_C(
			externalReferenceCode, companyId);

		if (oAuthClientEntry == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchOAuthClientEntryException(message);
		}

		return oAuthClientEntry;
	}

	/**
	 * Returns the o auth client entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the o auth client entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	@Override
	public OAuthClientEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_C.fetch(
			finderCache, new Object[] {externalReferenceCode, companyId},
			useFinderCache);
	}

	/**
	 * Removes the o auth client entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the o auth client entry that was removed
	 */
	@Override
	public OAuthClientEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchOAuthClientEntryException {

		OAuthClientEntry oAuthClientEntry = findByERC_C(
			externalReferenceCode, companyId);

		return remove(oAuthClientEntry);
	}

	/**
	 * Returns the number of o auth client entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching o auth client entries
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public OAuthClientEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(OAuthClientEntry.class);

		setModelImplClass(OAuthClientEntryImpl.class);
		setModelPKClass(long.class);

		setTable(OAuthClientEntryTable.INSTANCE);
	}

	/**
	 * Caches the o auth client entry in the entity cache if it is enabled.
	 *
	 * @param oAuthClientEntry the o auth client entry
	 */
	@Override
	public void cacheResult(OAuthClientEntry oAuthClientEntry) {
		entityCache.putResult(
			OAuthClientEntryImpl.class, oAuthClientEntry.getPrimaryKey(),
			oAuthClientEntry);

		finderCache.putResult(
			_finderPathFetchByC_A_C,
			new Object[] {
				oAuthClientEntry.getCompanyId(),
				oAuthClientEntry.getAuthServerWellKnownURI(),
				oAuthClientEntry.getClientId()
			},
			oAuthClientEntry);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				oAuthClientEntry.getExternalReferenceCode(),
				oAuthClientEntry.getCompanyId()
			},
			oAuthClientEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the o auth client entries in the entity cache if it is enabled.
	 *
	 * @param oAuthClientEntries the o auth client entries
	 */
	@Override
	public void cacheResult(List<OAuthClientEntry> oAuthClientEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (oAuthClientEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (OAuthClientEntry oAuthClientEntry : oAuthClientEntries) {
			if (entityCache.getResult(
					OAuthClientEntryImpl.class,
					oAuthClientEntry.getPrimaryKey()) == null) {

				cacheResult(oAuthClientEntry);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		OAuthClientEntryModelImpl oAuthClientEntryModelImpl) {

		Object[] args = new Object[] {
			oAuthClientEntryModelImpl.getCompanyId(),
			oAuthClientEntryModelImpl.getAuthServerWellKnownURI(),
			oAuthClientEntryModelImpl.getClientId()
		};

		finderCache.putResult(
			_finderPathFetchByC_A_C, args, oAuthClientEntryModelImpl);

		args = new Object[] {
			oAuthClientEntryModelImpl.getExternalReferenceCode(),
			oAuthClientEntryModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args, oAuthClientEntryModelImpl);
	}

	/**
	 * Creates a new o auth client entry with the primary key. Does not add the o auth client entry to the database.
	 *
	 * @param oAuthClientEntryId the primary key for the new o auth client entry
	 * @return the new o auth client entry
	 */
	@Override
	public OAuthClientEntry create(long oAuthClientEntryId) {
		OAuthClientEntry oAuthClientEntry = new OAuthClientEntryImpl();

		oAuthClientEntry.setNew(true);
		oAuthClientEntry.setPrimaryKey(oAuthClientEntryId);

		String uuid = PortalUUIDUtil.generate();

		oAuthClientEntry.setUuid(uuid);

		oAuthClientEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return oAuthClientEntry;
	}

	/**
	 * Removes the o auth client entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthClientEntryId the primary key of the o auth client entry
	 * @return the o auth client entry that was removed
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	@Override
	public OAuthClientEntry remove(long oAuthClientEntryId)
		throws NoSuchOAuthClientEntryException {

		return remove((Serializable)oAuthClientEntryId);
	}

	@Override
	protected OAuthClientEntry removeImpl(OAuthClientEntry oAuthClientEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuthClientEntry)) {
				oAuthClientEntry = (OAuthClientEntry)session.get(
					OAuthClientEntryImpl.class,
					oAuthClientEntry.getPrimaryKeyObj());
			}

			if (oAuthClientEntry != null) {
				session.delete(oAuthClientEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (oAuthClientEntry != null) {
			clearCache(oAuthClientEntry);
		}

		return oAuthClientEntry;
	}

	@Override
	public OAuthClientEntry updateImpl(OAuthClientEntry oAuthClientEntry) {
		boolean isNew = oAuthClientEntry.isNew();

		if (!(oAuthClientEntry instanceof OAuthClientEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(oAuthClientEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					oAuthClientEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuthClientEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuthClientEntry implementation " +
					oAuthClientEntry.getClass());
		}

		OAuthClientEntryModelImpl oAuthClientEntryModelImpl =
			(OAuthClientEntryModelImpl)oAuthClientEntry;

		if (Validator.isNull(oAuthClientEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			oAuthClientEntry.setUuid(uuid);
		}

		if (Validator.isNull(oAuthClientEntry.getExternalReferenceCode())) {
			oAuthClientEntry.setExternalReferenceCode(
				oAuthClientEntry.getUuid());
		}
		else {
			if (!Objects.equals(
					oAuthClientEntryModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					oAuthClientEntry.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = oAuthClientEntry.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = oAuthClientEntry.getPrimaryKey();
					}

					try {
						oAuthClientEntry.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								OAuthClientEntry.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								oAuthClientEntry.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			OAuthClientEntry ercOAuthClientEntry = fetchByERC_C(
				oAuthClientEntry.getExternalReferenceCode(),
				oAuthClientEntry.getCompanyId());

			if (isNew) {
				if (ercOAuthClientEntry != null) {
					throw new DuplicateOAuthClientEntryExternalReferenceCodeException(
						"Duplicate o auth client entry with external reference code " +
							oAuthClientEntry.getExternalReferenceCode() +
								" and company " +
									oAuthClientEntry.getCompanyId());
				}
			}
			else {
				if ((ercOAuthClientEntry != null) &&
					(oAuthClientEntry.getOAuthClientEntryId() !=
						ercOAuthClientEntry.getOAuthClientEntryId())) {

					throw new DuplicateOAuthClientEntryExternalReferenceCodeException(
						"Duplicate o auth client entry with external reference code " +
							oAuthClientEntry.getExternalReferenceCode() +
								" and company " +
									oAuthClientEntry.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (oAuthClientEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				oAuthClientEntry.setCreateDate(date);
			}
			else {
				oAuthClientEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!oAuthClientEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				oAuthClientEntry.setModifiedDate(date);
			}
			else {
				oAuthClientEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(oAuthClientEntry);
			}
			else {
				oAuthClientEntry = (OAuthClientEntry)session.merge(
					oAuthClientEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			OAuthClientEntryImpl.class, oAuthClientEntryModelImpl, false, true);

		cacheUniqueFindersCache(oAuthClientEntryModelImpl);

		if (isNew) {
			oAuthClientEntry.setNew(false);
		}

		oAuthClientEntry.resetOriginalValues();

		return oAuthClientEntry;
	}

	/**
	 * Returns the o auth client entry with the primary key or throws a <code>NoSuchOAuthClientEntryException</code> if it could not be found.
	 *
	 * @param oAuthClientEntryId the primary key of the o auth client entry
	 * @return the o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	@Override
	public OAuthClientEntry findByPrimaryKey(long oAuthClientEntryId)
		throws NoSuchOAuthClientEntryException {

		return findByPrimaryKey((Serializable)oAuthClientEntryId);
	}

	/**
	 * Returns the o auth client entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthClientEntryId the primary key of the o auth client entry
	 * @return the o auth client entry, or <code>null</code> if a o auth client entry with the primary key could not be found
	 */
	@Override
	public OAuthClientEntry fetchByPrimaryKey(long oAuthClientEntryId) {
		return fetchByPrimaryKey((Serializable)oAuthClientEntryId);
	}

	/**
	 * Returns all the o auth client entries.
	 *
	 * @return the o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findAll(
		int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth client entries
	 */
	@Override
	public List<OAuthClientEntry> findAll(
		int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator,
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

		List<OAuthClientEntry> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OAUTHCLIENTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTHCLIENTENTRY;

				sql = sql.concat(OAuthClientEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<OAuthClientEntry>)QueryUtil.list(
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
	 * Removes all the o auth client entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuthClientEntry oAuthClientEntry : findAll()) {
			remove(oAuthClientEntry);
		}
	}

	/**
	 * Returns the number of o auth client entries.
	 *
	 * @return the number of o auth client entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OAUTHCLIENTENTRY);

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
		return "oAuthClientEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OAUTHCLIENTENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuthClientEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth client entry persistence.
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

		_collectionPersistenceFinderByUuid = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByUuid,
			_finderPathWithoutPaginationFindByUuid, _finderPathCountByUuid,
			_SQL_SELECT_OAUTHCLIENTENTRY_WHERE,
			_SQL_COUNT_OAUTHCLIENTENTRY_WHERE,
			OAuthClientEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"oAuthClientEntry.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, OAuthClientEntry::getUuid));

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
				_finderPathCountByUuid_C, _SQL_SELECT_OAUTHCLIENTENTRY_WHERE,
				_SQL_COUNT_OAUTHCLIENTENTRY_WHERE,
				OAuthClientEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"oAuthClientEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, OAuthClientEntry::getUuid),
				new FinderColumn<>(
					"oAuthClientEntry.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, OAuthClientEntry::getCompanyId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_OAUTHCLIENTENTRY_WHERE,
				_SQL_COUNT_OAUTHCLIENTENTRY_WHERE,
				OAuthClientEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"oAuthClientEntry.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, OAuthClientEntry::getCompanyId));

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

		_collectionPersistenceFinderByUserId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUserId,
				_finderPathWithoutPaginationFindByUserId,
				_finderPathCountByUserId, _SQL_SELECT_OAUTHCLIENTENTRY_WHERE,
				_SQL_COUNT_OAUTHCLIENTENTRY_WHERE,
				OAuthClientEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"oAuthClientEntry.", "userId", FinderColumn.Type.LONG, "=",
					true, true, OAuthClientEntry::getUserId));

		_finderPathWithPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "authServerWellKnownURI"}, true);

		_finderPathWithoutPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "authServerWellKnownURI"}, true);

		_finderPathCountByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "authServerWellKnownURI"}, false);

		_collectionPersistenceFinderByC_A = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_A,
			_finderPathWithoutPaginationFindByC_A, _finderPathCountByC_A,
			_SQL_SELECT_OAUTHCLIENTENTRY_WHERE,
			_SQL_COUNT_OAUTHCLIENTENTRY_WHERE,
			OAuthClientEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"oAuthClientEntry.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, OAuthClientEntry::getCompanyId),
			new FinderColumn<>(
				"oAuthClientEntry.", "authServerWellKnownURI",
				FinderColumn.Type.STRING, "=", true, true,
				OAuthClientEntry::getAuthServerWellKnownURI));

		_finderPathFetchByC_A_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_A_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "authServerWellKnownURI", "clientId"},
			true);

		_uniquePersistenceFinderByC_A_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_A_C, _SQL_SELECT_OAUTHCLIENTENTRY_WHERE,
			new FinderColumn<>(
				"oAuthClientEntry.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, OAuthClientEntry::getCompanyId),
			new FinderColumn<>(
				"oAuthClientEntry.", "authServerWellKnownURI",
				FinderColumn.Type.STRING, "=", true, false,
				OAuthClientEntry::getAuthServerWellKnownURI),
			new FinderColumn<>(
				"oAuthClientEntry.", "clientId", FinderColumn.Type.STRING, "=",
				true, true, OAuthClientEntry::getClientId));

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C, _SQL_SELECT_OAUTHCLIENTENTRY_WHERE,
			new FinderColumn<>(
				"oAuthClientEntry.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				OAuthClientEntry::getExternalReferenceCode),
			new FinderColumn<>(
				"oAuthClientEntry.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, OAuthClientEntry::getCompanyId));

		OAuthClientEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		OAuthClientEntryUtil.setPersistence(null);

		entityCache.removeCache(OAuthClientEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = OAuthClientPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OAuthClientPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OAuthClientPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OAUTHCLIENTENTRY =
		"SELECT oAuthClientEntry FROM OAuthClientEntry oAuthClientEntry";

	private static final String _SQL_SELECT_OAUTHCLIENTENTRY_WHERE =
		"SELECT oAuthClientEntry FROM OAuthClientEntry oAuthClientEntry WHERE ";

	private static final String _SQL_COUNT_OAUTHCLIENTENTRY =
		"SELECT COUNT(oAuthClientEntry) FROM OAuthClientEntry oAuthClientEntry";

	private static final String _SQL_COUNT_OAUTHCLIENTENTRY_WHERE =
		"SELECT COUNT(oAuthClientEntry) FROM OAuthClientEntry oAuthClientEntry WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"oAuthClientEntry.oAuthClientEntryId";

	private static final String _FILTER_SQL_SELECT_OAUTHCLIENTENTRY_WHERE =
		"SELECT DISTINCT {oAuthClientEntry.*} FROM OAuthClientEntry oAuthClientEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {OAuthClientEntry.*} FROM (SELECT DISTINCT oAuthClientEntry.oAuthClientEntryId FROM OAuthClientEntry oAuthClientEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OAUTHCLIENTENTRY_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN OAuthClientEntry ON TEMP_TABLE.oAuthClientEntryId = OAuthClientEntry.oAuthClientEntryId";

	private static final String _FILTER_SQL_COUNT_OAUTHCLIENTENTRY_WHERE =
		"SELECT COUNT(DISTINCT oAuthClientEntry.oAuthClientEntryId) AS COUNT_VALUE FROM OAuthClientEntry oAuthClientEntry WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "oAuthClientEntry";

	private static final String _FILTER_ENTITY_TABLE = "OAuthClientEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuthClientEntry.";

	private static final String _ORDER_BY_ENTITY_TABLE = "OAuthClientEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OAuthClientEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthClientEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1075338764