/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service.persistence.impl;

import com.liferay.oauth.client.persistence.exception.DuplicateOAuthClientASLocalMetadataExternalReferenceCodeException;
import com.liferay.oauth.client.persistence.exception.NoSuchOAuthClientASLocalMetadataException;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadataTable;
import com.liferay.oauth.client.persistence.model.impl.OAuthClientASLocalMetadataImpl;
import com.liferay.oauth.client.persistence.model.impl.OAuthClientASLocalMetadataModelImpl;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientASLocalMetadataPersistence;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientASLocalMetadataUtil;
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
 * The persistence implementation for the o auth client as local metadata service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = OAuthClientASLocalMetadataPersistence.class)
public class OAuthClientASLocalMetadataPersistenceImpl
	extends BasePersistenceImpl<OAuthClientASLocalMetadata>
	implements OAuthClientASLocalMetadataPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OAuthClientASLocalMetadataUtil</code> to access the o auth client as local metadata persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OAuthClientASLocalMetadataImpl.class.getName();

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
	 * Returns all the o auth client as local metadatas where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator,
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

		List<OAuthClientASLocalMetadata> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientASLocalMetadata>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
						list) {

					if (!uuid.equals(oAuthClientASLocalMetadata.getUuid())) {
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

			sb.append(_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);

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
				sb.append(OAuthClientASLocalMetadataModelImpl.ORDER_BY_JPQL);
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

				list = (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Returns the first o auth client as local metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByUuid_First(
			String uuid,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByUuid_First(uuid, orderByComparator);

		if (oAuthClientASLocalMetadata != null) {
			return oAuthClientASLocalMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
	}

	/**
	 * Returns the first o auth client as local metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByUuid_First(
		String uuid,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		List<OAuthClientASLocalMetadata> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth client as local metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByUuid_Last(
			String uuid,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByUuid_Last(uuid, orderByComparator);

		if (oAuthClientASLocalMetadata != null) {
			return oAuthClientASLocalMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
	}

	/**
	 * Returns the last o auth client as local metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByUuid_Last(
		String uuid,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<OAuthClientASLocalMetadata> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the o auth client as local metadatas that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByUuid(
		String uuid, int start, int end) {

		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_2);
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
					OAuthClientASLocalMetadataModelImpl.
						ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(OAuthClientASLocalMetadataModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientASLocalMetadata.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientASLocalMetadataImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientASLocalMetadataImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			return (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Removes all the o auth client as local metadatas where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuthClientASLocalMetadata);
		}
	}

	/**
	 * Returns the number of o auth client as local metadatas where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching o auth client as local metadatas
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE);

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

	/**
	 * Returns the number of o auth client as local metadatas that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<OAuthClientASLocalMetadata> oAuthClientASLocalMetadatas =
				findByUuid(uuid);

			oAuthClientASLocalMetadatas = InlineSQLHelperUtil.filter(
				oAuthClientASLocalMetadatas);

			return oAuthClientASLocalMetadatas.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientASLocalMetadata.class.getName(),
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"oAuthClientASLocalMetadata.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(oAuthClientASLocalMetadata.uuid IS NULL OR oAuthClientASLocalMetadata.uuid = '')";

	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL =
		"oAuthClientASLocalMetadata.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(oAuthClientASLocalMetadata.uuid_ IS NULL OR oAuthClientASLocalMetadata.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the o auth client as local metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator,
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

		List<OAuthClientASLocalMetadata> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientASLocalMetadata>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
						list) {

					if (!uuid.equals(oAuthClientASLocalMetadata.getUuid()) ||
						(companyId !=
							oAuthClientASLocalMetadata.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);

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
				sb.append(OAuthClientASLocalMetadataModelImpl.ORDER_BY_JPQL);
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

				list = (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Returns the first o auth client as local metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (oAuthClientASLocalMetadata != null) {
			return oAuthClientASLocalMetadata;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
	}

	/**
	 * Returns the first o auth client as local metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		List<OAuthClientASLocalMetadata> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth client as local metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (oAuthClientASLocalMetadata != null) {
			return oAuthClientASLocalMetadata;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
	}

	/**
	 * Returns the last o auth client as local metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<OAuthClientASLocalMetadata> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the o auth client as local metadatas that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByUuid_C(
		String uuid, long companyId) {

		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_2);
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
					OAuthClientASLocalMetadataModelImpl.
						ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(OAuthClientASLocalMetadataModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientASLocalMetadata.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientASLocalMetadataImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientASLocalMetadataImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			return (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Removes all the o auth client as local metadatas where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(oAuthClientASLocalMetadata);
		}
	}

	/**
	 * Returns the number of o auth client as local metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching o auth client as local metadatas
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE);

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

	/**
	 * Returns the number of o auth client as local metadatas that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<OAuthClientASLocalMetadata> oAuthClientASLocalMetadatas =
				findByUuid_C(uuid, companyId);

			oAuthClientASLocalMetadatas = InlineSQLHelperUtil.filter(
				oAuthClientASLocalMetadatas);

			return oAuthClientASLocalMetadatas.size();
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE);

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
			sb.toString(), OAuthClientASLocalMetadata.class.getName(),
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"oAuthClientASLocalMetadata.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(oAuthClientASLocalMetadata.uuid IS NULL OR oAuthClientASLocalMetadata.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL =
		"oAuthClientASLocalMetadata.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(oAuthClientASLocalMetadata.uuid_ IS NULL OR oAuthClientASLocalMetadata.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"oAuthClientASLocalMetadata.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the o auth client as local metadatas where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator,
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

		List<OAuthClientASLocalMetadata> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientASLocalMetadata>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
						list) {

					if (companyId !=
							oAuthClientASLocalMetadata.getCompanyId()) {

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

			sb.append(_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuthClientASLocalMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Returns the first o auth client as local metadata in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByCompanyId_First(
			long companyId,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (oAuthClientASLocalMetadata != null) {
			return oAuthClientASLocalMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
	}

	/**
	 * Returns the first o auth client as local metadata in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByCompanyId_First(
		long companyId,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		List<OAuthClientASLocalMetadata> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth client as local metadata in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByCompanyId_Last(
			long companyId,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (oAuthClientASLocalMetadata != null) {
			return oAuthClientASLocalMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
	}

	/**
	 * Returns the last o auth client as local metadata in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<OAuthClientASLocalMetadata> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the o auth client as local metadatas that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByCompanyId(
		long companyId) {

		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_2);
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
					OAuthClientASLocalMetadataModelImpl.
						ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(OAuthClientASLocalMetadataModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientASLocalMetadata.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientASLocalMetadataImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientASLocalMetadataImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Removes all the o auth client as local metadatas where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuthClientASLocalMetadata);
		}
	}

	/**
	 * Returns the number of o auth client as local metadatas where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client as local metadatas
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE);

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

	/**
	 * Returns the number of o auth client as local metadatas that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<OAuthClientASLocalMetadata> oAuthClientASLocalMetadatas =
				findByCompanyId(companyId);

			oAuthClientASLocalMetadatas = InlineSQLHelperUtil.filter(
				oAuthClientASLocalMetadatas);

			return oAuthClientASLocalMetadatas.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientASLocalMetadata.class.getName(),
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
		"oAuthClientASLocalMetadata.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the o auth client as local metadatas where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator,
		boolean useFinderCache) {

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
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<OAuthClientASLocalMetadata> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientASLocalMetadata>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
						list) {

					if (userId != oAuthClientASLocalMetadata.getUserId()) {
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

			sb.append(_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuthClientASLocalMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Returns the first o auth client as local metadata in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByUserId_First(
			long userId,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByUserId_First(userId, orderByComparator);

		if (oAuthClientASLocalMetadata != null) {
			return oAuthClientASLocalMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
	}

	/**
	 * Returns the first o auth client as local metadata in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByUserId_First(
		long userId,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		List<OAuthClientASLocalMetadata> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth client as local metadata in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByUserId_Last(
			long userId,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByUserId_Last(userId, orderByComparator);

		if (oAuthClientASLocalMetadata != null) {
			return oAuthClientASLocalMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
	}

	/**
	 * Returns the last o auth client as local metadata in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByUserId_Last(
		long userId,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<OAuthClientASLocalMetadata> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the o auth client as local metadatas that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByUserId(long userId) {
		return filterFindByUserId(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByUserId(
		long userId, int start, int end) {

		return filterFindByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_2);
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
					OAuthClientASLocalMetadataModelImpl.
						ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(OAuthClientASLocalMetadataModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientASLocalMetadata.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientASLocalMetadataImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientASLocalMetadataImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			return (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Removes all the o auth client as local metadatas where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuthClientASLocalMetadata);
		}
	}

	/**
	 * Returns the number of o auth client as local metadatas where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client as local metadatas
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE);

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

	/**
	 * Returns the number of o auth client as local metadatas that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public int filterCountByUserId(long userId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUserId(userId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<OAuthClientASLocalMetadata> oAuthClientASLocalMetadatas =
				findByUserId(userId);

			oAuthClientASLocalMetadatas = InlineSQLHelperUtil.filter(
				oAuthClientASLocalMetadatas);

			return oAuthClientASLocalMetadatas.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientASLocalMetadata.class.getName(),
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
		"oAuthClientASLocalMetadata.userId = ?";

	private FinderPath _finderPathFetchByLocalWellKnownURI;

	/**
	 * Returns the o auth client as local metadata where localWellKnownURI = &#63; or throws a <code>NoSuchOAuthClientASLocalMetadataException</code> if it could not be found.
	 *
	 * @param localWellKnownURI the local well known uri
	 * @return the matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByLocalWellKnownURI(
			String localWellKnownURI)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByLocalWellKnownURI(localWellKnownURI);

		if (oAuthClientASLocalMetadata == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("localWellKnownURI=");
			sb.append(localWellKnownURI);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
		}

		return oAuthClientASLocalMetadata;
	}

	/**
	 * Returns the o auth client as local metadata where localWellKnownURI = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param localWellKnownURI the local well known uri
	 * @return the matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByLocalWellKnownURI(
		String localWellKnownURI) {

		return fetchByLocalWellKnownURI(localWellKnownURI, true);
	}

	/**
	 * Returns the o auth client as local metadata where localWellKnownURI = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param localWellKnownURI the local well known uri
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByLocalWellKnownURI(
		String localWellKnownURI, boolean useFinderCache) {

		localWellKnownURI = Objects.toString(localWellKnownURI, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {localWellKnownURI};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByLocalWellKnownURI, finderArgs, this);
		}

		if (result instanceof OAuthClientASLocalMetadata) {
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
				(OAuthClientASLocalMetadata)result;

			if (!Objects.equals(
					localWellKnownURI,
					oAuthClientASLocalMetadata.getLocalWellKnownURI())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);

			boolean bindLocalWellKnownURI = false;

			if (localWellKnownURI.isEmpty()) {
				sb.append(_FINDER_COLUMN_LOCALWELLKNOWNURI_LOCALWELLKNOWNURI_3);
			}
			else {
				bindLocalWellKnownURI = true;

				sb.append(_FINDER_COLUMN_LOCALWELLKNOWNURI_LOCALWELLKNOWNURI_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindLocalWellKnownURI) {
					queryPos.add(localWellKnownURI);
				}

				List<OAuthClientASLocalMetadata> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByLocalWellKnownURI, finderArgs,
							list);
					}
				}
				else {
					OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
						list.get(0);

					result = oAuthClientASLocalMetadata;

					cacheResult(oAuthClientASLocalMetadata);
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
			return (OAuthClientASLocalMetadata)result;
		}
	}

	/**
	 * Removes the o auth client as local metadata where localWellKnownURI = &#63; from the database.
	 *
	 * @param localWellKnownURI the local well known uri
	 * @return the o auth client as local metadata that was removed
	 */
	@Override
	public OAuthClientASLocalMetadata removeByLocalWellKnownURI(
			String localWellKnownURI)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			findByLocalWellKnownURI(localWellKnownURI);

		return remove(oAuthClientASLocalMetadata);
	}

	/**
	 * Returns the number of o auth client as local metadatas where localWellKnownURI = &#63;.
	 *
	 * @param localWellKnownURI the local well known uri
	 * @return the number of matching o auth client as local metadatas
	 */
	@Override
	public int countByLocalWellKnownURI(String localWellKnownURI) {
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByLocalWellKnownURI(localWellKnownURI);

		if (oAuthClientASLocalMetadata == null) {
			return 0;
		}

		return 1;
	}

	private static final String
		_FINDER_COLUMN_LOCALWELLKNOWNURI_LOCALWELLKNOWNURI_2 =
			"oAuthClientASLocalMetadata.localWellKnownURI = ?";

	private static final String
		_FINDER_COLUMN_LOCALWELLKNOWNURI_LOCALWELLKNOWNURI_3 =
			"(oAuthClientASLocalMetadata.localWellKnownURI IS NULL OR oAuthClientASLocalMetadata.localWellKnownURI = '')";

	private FinderPath _finderPathFetchByC_I;

	/**
	 * Returns the o auth client as local metadata where companyId = &#63; and issuer = &#63; or throws a <code>NoSuchOAuthClientASLocalMetadataException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByC_I(long companyId, String issuer)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = fetchByC_I(
			companyId, issuer);

		if (oAuthClientASLocalMetadata == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", issuer=");
			sb.append(issuer);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
		}

		return oAuthClientASLocalMetadata;
	}

	/**
	 * Returns the o auth client as local metadata where companyId = &#63; and issuer = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByC_I(
		long companyId, String issuer) {

		return fetchByC_I(companyId, issuer, true);
	}

	/**
	 * Returns the o auth client as local metadata where companyId = &#63; and issuer = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByC_I(
		long companyId, String issuer, boolean useFinderCache) {

		issuer = Objects.toString(issuer, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, issuer};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_I, finderArgs, this);
		}

		if (result instanceof OAuthClientASLocalMetadata) {
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
				(OAuthClientASLocalMetadata)result;

			if ((companyId != oAuthClientASLocalMetadata.getCompanyId()) ||
				!Objects.equals(
					issuer, oAuthClientASLocalMetadata.getIssuer())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);

			sb.append(_FINDER_COLUMN_C_I_COMPANYID_2);

			boolean bindIssuer = false;

			if (issuer.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_I_ISSUER_3);
			}
			else {
				bindIssuer = true;

				sb.append(_FINDER_COLUMN_C_I_ISSUER_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindIssuer) {
					queryPos.add(issuer);
				}

				List<OAuthClientASLocalMetadata> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_I, finderArgs, list);
					}
				}
				else {
					OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
						list.get(0);

					result = oAuthClientASLocalMetadata;

					cacheResult(oAuthClientASLocalMetadata);
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
			return (OAuthClientASLocalMetadata)result;
		}
	}

	/**
	 * Removes the o auth client as local metadata where companyId = &#63; and issuer = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the o auth client as local metadata that was removed
	 */
	@Override
	public OAuthClientASLocalMetadata removeByC_I(long companyId, String issuer)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = findByC_I(
			companyId, issuer);

		return remove(oAuthClientASLocalMetadata);
	}

	/**
	 * Returns the number of o auth client as local metadatas where companyId = &#63; and issuer = &#63;.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the number of matching o auth client as local metadatas
	 */
	@Override
	public int countByC_I(long companyId, String issuer) {
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = fetchByC_I(
			companyId, issuer);

		if (oAuthClientASLocalMetadata == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_I_COMPANYID_2 =
		"oAuthClientASLocalMetadata.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_I_ISSUER_2 =
		"oAuthClientASLocalMetadata.issuer = ?";

	private static final String _FINDER_COLUMN_C_I_ISSUER_3 =
		"(oAuthClientASLocalMetadata.issuer IS NULL OR oAuthClientASLocalMetadata.issuer = '')";

	private FinderPath _finderPathWithPaginationFindByC_L;
	private FinderPath _finderPathWithoutPaginationFindByC_L;
	private FinderPath _finderPathCountByC_L;

	/**
	 * Returns all the o auth client as local metadatas where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @return the matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByC_L(
		long companyId, boolean localWellKnownEnabled) {

		return findByC_L(
			companyId, localWellKnownEnabled, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByC_L(
		long companyId, boolean localWellKnownEnabled, int start, int end) {

		return findByC_L(companyId, localWellKnownEnabled, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByC_L(
		long companyId, boolean localWellKnownEnabled, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		return findByC_L(
			companyId, localWellKnownEnabled, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findByC_L(
		long companyId, boolean localWellKnownEnabled, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_L;
				finderArgs = new Object[] {companyId, localWellKnownEnabled};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_L;
			finderArgs = new Object[] {
				companyId, localWellKnownEnabled, start, end, orderByComparator
			};
		}

		List<OAuthClientASLocalMetadata> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientASLocalMetadata>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
						list) {

					if ((companyId !=
							oAuthClientASLocalMetadata.getCompanyId()) ||
						(localWellKnownEnabled !=
							oAuthClientASLocalMetadata.
								isLocalWellKnownEnabled())) {

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

			sb.append(_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);

			sb.append(_FINDER_COLUMN_C_L_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_L_LOCALWELLKNOWNENABLED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuthClientASLocalMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(localWellKnownEnabled);

				list = (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Returns the first o auth client as local metadata in the ordered set where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByC_L_First(
			long companyId, boolean localWellKnownEnabled,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByC_L_First(
				companyId, localWellKnownEnabled, orderByComparator);

		if (oAuthClientASLocalMetadata != null) {
			return oAuthClientASLocalMetadata;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", localWellKnownEnabled=");
		sb.append(localWellKnownEnabled);

		sb.append("}");

		throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
	}

	/**
	 * Returns the first o auth client as local metadata in the ordered set where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByC_L_First(
		long companyId, boolean localWellKnownEnabled,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		List<OAuthClientASLocalMetadata> list = findByC_L(
			companyId, localWellKnownEnabled, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth client as local metadata in the ordered set where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByC_L_Last(
			long companyId, boolean localWellKnownEnabled,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = fetchByC_L_Last(
			companyId, localWellKnownEnabled, orderByComparator);

		if (oAuthClientASLocalMetadata != null) {
			return oAuthClientASLocalMetadata;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", localWellKnownEnabled=");
		sb.append(localWellKnownEnabled);

		sb.append("}");

		throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
	}

	/**
	 * Returns the last o auth client as local metadata in the ordered set where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByC_L_Last(
		long companyId, boolean localWellKnownEnabled,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		int count = countByC_L(companyId, localWellKnownEnabled);

		if (count == 0) {
			return null;
		}

		List<OAuthClientASLocalMetadata> list = findByC_L(
			companyId, localWellKnownEnabled, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the o auth client as local metadatas that the user has permission to view where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @return the matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByC_L(
		long companyId, boolean localWellKnownEnabled) {

		return filterFindByC_L(
			companyId, localWellKnownEnabled, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas that the user has permission to view where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByC_L(
		long companyId, boolean localWellKnownEnabled, int start, int end) {

		return filterFindByC_L(
			companyId, localWellKnownEnabled, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas that the user has permissions to view where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public List<OAuthClientASLocalMetadata> filterFindByC_L(
		long companyId, boolean localWellKnownEnabled, int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_L(
				companyId, localWellKnownEnabled, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByC_L(
					companyId, localWellKnownEnabled, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_L_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_L_LOCALWELLKNOWNENABLED_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_2);
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
					OAuthClientASLocalMetadataModelImpl.
						ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(OAuthClientASLocalMetadataModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientASLocalMetadata.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientASLocalMetadataImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientASLocalMetadataImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(localWellKnownEnabled);

			return (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Removes all the o auth client as local metadatas where companyId = &#63; and localWellKnownEnabled = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 */
	@Override
	public void removeByC_L(long companyId, boolean localWellKnownEnabled) {
		for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
				findByC_L(
					companyId, localWellKnownEnabled, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(oAuthClientASLocalMetadata);
		}
	}

	/**
	 * Returns the number of o auth client as local metadatas where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @return the number of matching o auth client as local metadatas
	 */
	@Override
	public int countByC_L(long companyId, boolean localWellKnownEnabled) {
		FinderPath finderPath = _finderPathCountByC_L;

		Object[] finderArgs = new Object[] {companyId, localWellKnownEnabled};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE);

			sb.append(_FINDER_COLUMN_C_L_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_L_LOCALWELLKNOWNENABLED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(localWellKnownEnabled);

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
	 * Returns the number of o auth client as local metadatas that the user has permission to view where companyId = &#63; and localWellKnownEnabled = &#63;.
	 *
	 * @param companyId the company ID
	 * @param localWellKnownEnabled the local well known enabled
	 * @return the number of matching o auth client as local metadatas that the user has permission to view
	 */
	@Override
	public int filterCountByC_L(long companyId, boolean localWellKnownEnabled) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_L(companyId, localWellKnownEnabled);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<OAuthClientASLocalMetadata> oAuthClientASLocalMetadatas =
				findByC_L(companyId, localWellKnownEnabled);

			oAuthClientASLocalMetadatas = InlineSQLHelperUtil.filter(
				oAuthClientASLocalMetadatas);

			return oAuthClientASLocalMetadatas.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE);

		sb.append(_FINDER_COLUMN_C_L_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_L_LOCALWELLKNOWNENABLED_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientASLocalMetadata.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(localWellKnownEnabled);

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

	private static final String _FINDER_COLUMN_C_L_COMPANYID_2 =
		"oAuthClientASLocalMetadata.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_L_LOCALWELLKNOWNENABLED_2 =
		"oAuthClientASLocalMetadata.localWellKnownEnabled = ?";

	private FinderPath _finderPathFetchByC_O;

	/**
	 * Returns the o auth client as local metadata where companyId = &#63; and oAuthASLocalWellKnownURI = &#63; or throws a <code>NoSuchOAuthClientASLocalMetadataException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param oAuthASLocalWellKnownURI the o auth as local well known uri
	 * @return the matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByC_O(
			long companyId, String oAuthASLocalWellKnownURI)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = fetchByC_O(
			companyId, oAuthASLocalWellKnownURI);

		if (oAuthClientASLocalMetadata == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", oAuthASLocalWellKnownURI=");
			sb.append(oAuthASLocalWellKnownURI);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
		}

		return oAuthClientASLocalMetadata;
	}

	/**
	 * Returns the o auth client as local metadata where companyId = &#63; and oAuthASLocalWellKnownURI = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param oAuthASLocalWellKnownURI the o auth as local well known uri
	 * @return the matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByC_O(
		long companyId, String oAuthASLocalWellKnownURI) {

		return fetchByC_O(companyId, oAuthASLocalWellKnownURI, true);
	}

	/**
	 * Returns the o auth client as local metadata where companyId = &#63; and oAuthASLocalWellKnownURI = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param oAuthASLocalWellKnownURI the o auth as local well known uri
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByC_O(
		long companyId, String oAuthASLocalWellKnownURI,
		boolean useFinderCache) {

		oAuthASLocalWellKnownURI = Objects.toString(
			oAuthASLocalWellKnownURI, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, oAuthASLocalWellKnownURI};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_O, finderArgs, this);
		}

		if (result instanceof OAuthClientASLocalMetadata) {
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
				(OAuthClientASLocalMetadata)result;

			if ((companyId != oAuthClientASLocalMetadata.getCompanyId()) ||
				!Objects.equals(
					oAuthASLocalWellKnownURI,
					oAuthClientASLocalMetadata.getOAuthASLocalWellKnownURI())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);

			sb.append(_FINDER_COLUMN_C_O_COMPANYID_2);

			boolean bindOAuthASLocalWellKnownURI = false;

			if (oAuthASLocalWellKnownURI.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_O_OAUTHASLOCALWELLKNOWNURI_3);
			}
			else {
				bindOAuthASLocalWellKnownURI = true;

				sb.append(_FINDER_COLUMN_C_O_OAUTHASLOCALWELLKNOWNURI_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindOAuthASLocalWellKnownURI) {
					queryPos.add(oAuthASLocalWellKnownURI);
				}

				List<OAuthClientASLocalMetadata> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_O, finderArgs, list);
					}
				}
				else {
					OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
						list.get(0);

					result = oAuthClientASLocalMetadata;

					cacheResult(oAuthClientASLocalMetadata);
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
			return (OAuthClientASLocalMetadata)result;
		}
	}

	/**
	 * Removes the o auth client as local metadata where companyId = &#63; and oAuthASLocalWellKnownURI = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param oAuthASLocalWellKnownURI the o auth as local well known uri
	 * @return the o auth client as local metadata that was removed
	 */
	@Override
	public OAuthClientASLocalMetadata removeByC_O(
			long companyId, String oAuthASLocalWellKnownURI)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = findByC_O(
			companyId, oAuthASLocalWellKnownURI);

		return remove(oAuthClientASLocalMetadata);
	}

	/**
	 * Returns the number of o auth client as local metadatas where companyId = &#63; and oAuthASLocalWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param oAuthASLocalWellKnownURI the o auth as local well known uri
	 * @return the number of matching o auth client as local metadatas
	 */
	@Override
	public int countByC_O(long companyId, String oAuthASLocalWellKnownURI) {
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = fetchByC_O(
			companyId, oAuthASLocalWellKnownURI);

		if (oAuthClientASLocalMetadata == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_O_COMPANYID_2 =
		"oAuthClientASLocalMetadata.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_O_OAUTHASLOCALWELLKNOWNURI_2 =
		"oAuthClientASLocalMetadata.oAuthASLocalWellKnownURI = ?";

	private static final String _FINDER_COLUMN_C_O_OAUTHASLOCALWELLKNOWNURI_3 =
		"(oAuthClientASLocalMetadata.oAuthASLocalWellKnownURI IS NULL OR oAuthClientASLocalMetadata.oAuthASLocalWellKnownURI = '')";

	private FinderPath _finderPathFetchByERC_C;

	/**
	 * Returns the o auth client as local metadata where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchOAuthClientASLocalMetadataException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = fetchByERC_C(
			externalReferenceCode, companyId);

		if (oAuthClientASLocalMetadata == null) {
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

			throw new NoSuchOAuthClientASLocalMetadataException(sb.toString());
		}

		return oAuthClientASLocalMetadata;
	}

	/**
	 * Returns the o auth client as local metadata where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the o auth client as local metadata where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client as local metadata, or <code>null</code> if a matching o auth client as local metadata could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByERC_C(
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

		if (result instanceof OAuthClientASLocalMetadata) {
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
				(OAuthClientASLocalMetadata)result;

			if (!Objects.equals(
					externalReferenceCode,
					oAuthClientASLocalMetadata.getExternalReferenceCode()) ||
				(companyId != oAuthClientASLocalMetadata.getCompanyId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE);

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

				List<OAuthClientASLocalMetadata> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByERC_C, finderArgs, list);
					}
				}
				else {
					OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
						list.get(0);

					result = oAuthClientASLocalMetadata;

					cacheResult(oAuthClientASLocalMetadata);
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
			return (OAuthClientASLocalMetadata)result;
		}
	}

	/**
	 * Removes the o auth client as local metadata where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the o auth client as local metadata that was removed
	 */
	@Override
	public OAuthClientASLocalMetadata removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = findByERC_C(
			externalReferenceCode, companyId);

		return remove(oAuthClientASLocalMetadata);
	}

	/**
	 * Returns the number of o auth client as local metadatas where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching o auth client as local metadatas
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = fetchByERC_C(
			externalReferenceCode, companyId);

		if (oAuthClientASLocalMetadata == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2 =
		"oAuthClientASLocalMetadata.externalReferenceCode = ? AND ";

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3 =
		"(oAuthClientASLocalMetadata.externalReferenceCode IS NULL OR oAuthClientASLocalMetadata.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_C_COMPANYID_2 =
		"oAuthClientASLocalMetadata.companyId = ?";

	public OAuthClientASLocalMetadataPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(OAuthClientASLocalMetadata.class);

		setModelImplClass(OAuthClientASLocalMetadataImpl.class);
		setModelPKClass(long.class);

		setTable(OAuthClientASLocalMetadataTable.INSTANCE);
	}

	/**
	 * Caches the o auth client as local metadata in the entity cache if it is enabled.
	 *
	 * @param oAuthClientASLocalMetadata the o auth client as local metadata
	 */
	@Override
	public void cacheResult(
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata) {

		entityCache.putResult(
			OAuthClientASLocalMetadataImpl.class,
			oAuthClientASLocalMetadata.getPrimaryKey(),
			oAuthClientASLocalMetadata);

		finderCache.putResult(
			_finderPathFetchByLocalWellKnownURI,
			new Object[] {oAuthClientASLocalMetadata.getLocalWellKnownURI()},
			oAuthClientASLocalMetadata);

		finderCache.putResult(
			_finderPathFetchByC_I,
			new Object[] {
				oAuthClientASLocalMetadata.getCompanyId(),
				oAuthClientASLocalMetadata.getIssuer()
			},
			oAuthClientASLocalMetadata);

		finderCache.putResult(
			_finderPathFetchByC_O,
			new Object[] {
				oAuthClientASLocalMetadata.getCompanyId(),
				oAuthClientASLocalMetadata.getOAuthASLocalWellKnownURI()
			},
			oAuthClientASLocalMetadata);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				oAuthClientASLocalMetadata.getExternalReferenceCode(),
				oAuthClientASLocalMetadata.getCompanyId()
			},
			oAuthClientASLocalMetadata);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the o auth client as local metadatas in the entity cache if it is enabled.
	 *
	 * @param oAuthClientASLocalMetadatas the o auth client as local metadatas
	 */
	@Override
	public void cacheResult(
		List<OAuthClientASLocalMetadata> oAuthClientASLocalMetadatas) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (oAuthClientASLocalMetadatas.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
				oAuthClientASLocalMetadatas) {

			if (entityCache.getResult(
					OAuthClientASLocalMetadataImpl.class,
					oAuthClientASLocalMetadata.getPrimaryKey()) == null) {

				cacheResult(oAuthClientASLocalMetadata);
			}
		}
	}

	/**
	 * Clears the cache for all o auth client as local metadatas.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuthClientASLocalMetadataImpl.class);

		finderCache.clearCache(OAuthClientASLocalMetadataImpl.class);
	}

	/**
	 * Clears the cache for the o auth client as local metadata.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata) {

		entityCache.removeResult(
			OAuthClientASLocalMetadataImpl.class, oAuthClientASLocalMetadata);
	}

	@Override
	public void clearCache(
		List<OAuthClientASLocalMetadata> oAuthClientASLocalMetadatas) {

		for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
				oAuthClientASLocalMetadatas) {

			entityCache.removeResult(
				OAuthClientASLocalMetadataImpl.class,
				oAuthClientASLocalMetadata);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(OAuthClientASLocalMetadataImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				OAuthClientASLocalMetadataImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuthClientASLocalMetadataModelImpl
			oAuthClientASLocalMetadataModelImpl) {

		Object[] args = new Object[] {
			oAuthClientASLocalMetadataModelImpl.getLocalWellKnownURI()
		};

		finderCache.putResult(
			_finderPathFetchByLocalWellKnownURI, args,
			oAuthClientASLocalMetadataModelImpl);

		args = new Object[] {
			oAuthClientASLocalMetadataModelImpl.getCompanyId(),
			oAuthClientASLocalMetadataModelImpl.getIssuer()
		};

		finderCache.putResult(
			_finderPathFetchByC_I, args, oAuthClientASLocalMetadataModelImpl);

		args = new Object[] {
			oAuthClientASLocalMetadataModelImpl.getCompanyId(),
			oAuthClientASLocalMetadataModelImpl.getOAuthASLocalWellKnownURI()
		};

		finderCache.putResult(
			_finderPathFetchByC_O, args, oAuthClientASLocalMetadataModelImpl);

		args = new Object[] {
			oAuthClientASLocalMetadataModelImpl.getExternalReferenceCode(),
			oAuthClientASLocalMetadataModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args, oAuthClientASLocalMetadataModelImpl);
	}

	/**
	 * Creates a new o auth client as local metadata with the primary key. Does not add the o auth client as local metadata to the database.
	 *
	 * @param oAuthClientASLocalMetadataId the primary key for the new o auth client as local metadata
	 * @return the new o auth client as local metadata
	 */
	@Override
	public OAuthClientASLocalMetadata create(
		long oAuthClientASLocalMetadataId) {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			new OAuthClientASLocalMetadataImpl();

		oAuthClientASLocalMetadata.setNew(true);
		oAuthClientASLocalMetadata.setPrimaryKey(oAuthClientASLocalMetadataId);

		String uuid = PortalUUIDUtil.generate();

		oAuthClientASLocalMetadata.setUuid(uuid);

		oAuthClientASLocalMetadata.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return oAuthClientASLocalMetadata;
	}

	/**
	 * Removes the o auth client as local metadata with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthClientASLocalMetadataId the primary key of the o auth client as local metadata
	 * @return the o auth client as local metadata that was removed
	 * @throws NoSuchOAuthClientASLocalMetadataException if a o auth client as local metadata with the primary key could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata remove(long oAuthClientASLocalMetadataId)
		throws NoSuchOAuthClientASLocalMetadataException {

		return remove((Serializable)oAuthClientASLocalMetadataId);
	}

	/**
	 * Removes the o auth client as local metadata with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth client as local metadata
	 * @return the o auth client as local metadata that was removed
	 * @throws NoSuchOAuthClientASLocalMetadataException if a o auth client as local metadata with the primary key could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata remove(Serializable primaryKey)
		throws NoSuchOAuthClientASLocalMetadataException {

		Session session = null;

		try {
			session = openSession();

			OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
				(OAuthClientASLocalMetadata)session.get(
					OAuthClientASLocalMetadataImpl.class, primaryKey);

			if (oAuthClientASLocalMetadata == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOAuthClientASLocalMetadataException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(oAuthClientASLocalMetadata);
		}
		catch (NoSuchOAuthClientASLocalMetadataException
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
	protected OAuthClientASLocalMetadata removeImpl(
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuthClientASLocalMetadata)) {
				oAuthClientASLocalMetadata =
					(OAuthClientASLocalMetadata)session.get(
						OAuthClientASLocalMetadataImpl.class,
						oAuthClientASLocalMetadata.getPrimaryKeyObj());
			}

			if (oAuthClientASLocalMetadata != null) {
				session.delete(oAuthClientASLocalMetadata);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (oAuthClientASLocalMetadata != null) {
			clearCache(oAuthClientASLocalMetadata);
		}

		return oAuthClientASLocalMetadata;
	}

	@Override
	public OAuthClientASLocalMetadata updateImpl(
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata) {

		boolean isNew = oAuthClientASLocalMetadata.isNew();

		if (!(oAuthClientASLocalMetadata instanceof
				OAuthClientASLocalMetadataModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(oAuthClientASLocalMetadata.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					oAuthClientASLocalMetadata);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuthClientASLocalMetadata proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuthClientASLocalMetadata implementation " +
					oAuthClientASLocalMetadata.getClass());
		}

		OAuthClientASLocalMetadataModelImpl
			oAuthClientASLocalMetadataModelImpl =
				(OAuthClientASLocalMetadataModelImpl)oAuthClientASLocalMetadata;

		if (Validator.isNull(oAuthClientASLocalMetadata.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			oAuthClientASLocalMetadata.setUuid(uuid);
		}

		if (Validator.isNull(
				oAuthClientASLocalMetadata.getExternalReferenceCode())) {

			oAuthClientASLocalMetadata.setExternalReferenceCode(
				oAuthClientASLocalMetadata.getUuid());
		}
		else {
			if (!Objects.equals(
					oAuthClientASLocalMetadataModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					oAuthClientASLocalMetadata.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = oAuthClientASLocalMetadata.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = oAuthClientASLocalMetadata.getPrimaryKey();
					}

					try {
						oAuthClientASLocalMetadata.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								OAuthClientASLocalMetadata.class.getName(),
								classPK, ContentTypes.TEXT_HTML,
								Sanitizer.MODE_ALL,
								oAuthClientASLocalMetadata.
									getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			OAuthClientASLocalMetadata ercOAuthClientASLocalMetadata =
				fetchByERC_C(
					oAuthClientASLocalMetadata.getExternalReferenceCode(),
					oAuthClientASLocalMetadata.getCompanyId());

			if (isNew) {
				if (ercOAuthClientASLocalMetadata != null) {
					throw new DuplicateOAuthClientASLocalMetadataExternalReferenceCodeException(
						"Duplicate o auth client as local metadata with external reference code " +
							oAuthClientASLocalMetadata.
								getExternalReferenceCode() + " and company " +
									oAuthClientASLocalMetadata.getCompanyId());
				}
			}
			else {
				if ((ercOAuthClientASLocalMetadata != null) &&
					(oAuthClientASLocalMetadata.
						getOAuthClientASLocalMetadataId() !=
							ercOAuthClientASLocalMetadata.
								getOAuthClientASLocalMetadataId())) {

					throw new DuplicateOAuthClientASLocalMetadataExternalReferenceCodeException(
						"Duplicate o auth client as local metadata with external reference code " +
							oAuthClientASLocalMetadata.
								getExternalReferenceCode() + " and company " +
									oAuthClientASLocalMetadata.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (oAuthClientASLocalMetadata.getCreateDate() == null)) {
			if (serviceContext == null) {
				oAuthClientASLocalMetadata.setCreateDate(date);
			}
			else {
				oAuthClientASLocalMetadata.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!oAuthClientASLocalMetadataModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				oAuthClientASLocalMetadata.setModifiedDate(date);
			}
			else {
				oAuthClientASLocalMetadata.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(oAuthClientASLocalMetadata);
			}
			else {
				oAuthClientASLocalMetadata =
					(OAuthClientASLocalMetadata)session.merge(
						oAuthClientASLocalMetadata);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			OAuthClientASLocalMetadataImpl.class,
			oAuthClientASLocalMetadataModelImpl, false, true);

		cacheUniqueFindersCache(oAuthClientASLocalMetadataModelImpl);

		if (isNew) {
			oAuthClientASLocalMetadata.setNew(false);
		}

		oAuthClientASLocalMetadata.resetOriginalValues();

		return oAuthClientASLocalMetadata;
	}

	/**
	 * Returns the o auth client as local metadata with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth client as local metadata
	 * @return the o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a o auth client as local metadata with the primary key could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOAuthClientASLocalMetadataException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			fetchByPrimaryKey(primaryKey);

		if (oAuthClientASLocalMetadata == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOAuthClientASLocalMetadataException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return oAuthClientASLocalMetadata;
	}

	/**
	 * Returns the o auth client as local metadata with the primary key or throws a <code>NoSuchOAuthClientASLocalMetadataException</code> if it could not be found.
	 *
	 * @param oAuthClientASLocalMetadataId the primary key of the o auth client as local metadata
	 * @return the o auth client as local metadata
	 * @throws NoSuchOAuthClientASLocalMetadataException if a o auth client as local metadata with the primary key could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata findByPrimaryKey(
			long oAuthClientASLocalMetadataId)
		throws NoSuchOAuthClientASLocalMetadataException {

		return findByPrimaryKey((Serializable)oAuthClientASLocalMetadataId);
	}

	/**
	 * Returns the o auth client as local metadata with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthClientASLocalMetadataId the primary key of the o auth client as local metadata
	 * @return the o auth client as local metadata, or <code>null</code> if a o auth client as local metadata with the primary key could not be found
	 */
	@Override
	public OAuthClientASLocalMetadata fetchByPrimaryKey(
		long oAuthClientASLocalMetadataId) {

		return fetchByPrimaryKey((Serializable)oAuthClientASLocalMetadataId);
	}

	/**
	 * Returns all the o auth client as local metadatas.
	 *
	 * @return the o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client as local metadatas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @return the range of o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findAll(
		int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client as local metadatas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientASLocalMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client as local metadatas
	 * @param end the upper bound of the range of o auth client as local metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth client as local metadatas
	 */
	@Override
	public List<OAuthClientASLocalMetadata> findAll(
		int start, int end,
		OrderByComparator<OAuthClientASLocalMetadata> orderByComparator,
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

		List<OAuthClientASLocalMetadata> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientASLocalMetadata>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTHCLIENTASLOCALMETADATA;

				sql = sql.concat(
					OAuthClientASLocalMetadataModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<OAuthClientASLocalMetadata>)QueryUtil.list(
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
	 * Removes all the o auth client as local metadatas from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
				findAll()) {

			remove(oAuthClientASLocalMetadata);
		}
	}

	/**
	 * Returns the number of o auth client as local metadatas.
	 *
	 * @return the number of o auth client as local metadatas
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
					_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA);

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
		return "oAuthClientASLocalMetadataId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OAUTHCLIENTASLOCALMETADATA;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuthClientASLocalMetadataModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth client as local metadata persistence.
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

		_finderPathFetchByLocalWellKnownURI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByLocalWellKnownURI",
			new String[] {String.class.getName()},
			new String[] {"localWellKnownURI"}, true);

		_finderPathFetchByC_I = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_I",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "issuer"}, true);

		_finderPathWithPaginationFindByC_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "localWellKnownEnabled"}, true);

		_finderPathWithoutPaginationFindByC_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_L",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "localWellKnownEnabled"}, true);

		_finderPathCountByC_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_L",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "localWellKnownEnabled"}, false);

		_finderPathFetchByC_O = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_O",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "oAuthASLocalWellKnownURI"}, true);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		OAuthClientASLocalMetadataUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		OAuthClientASLocalMetadataUtil.setPersistence(null);

		entityCache.removeCache(OAuthClientASLocalMetadataImpl.class.getName());
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

	private static final String _SQL_SELECT_OAUTHCLIENTASLOCALMETADATA =
		"SELECT oAuthClientASLocalMetadata FROM OAuthClientASLocalMetadata oAuthClientASLocalMetadata";

	private static final String _SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE =
		"SELECT oAuthClientASLocalMetadata FROM OAuthClientASLocalMetadata oAuthClientASLocalMetadata WHERE ";

	private static final String _SQL_COUNT_OAUTHCLIENTASLOCALMETADATA =
		"SELECT COUNT(oAuthClientASLocalMetadata) FROM OAuthClientASLocalMetadata oAuthClientASLocalMetadata";

	private static final String _SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE =
		"SELECT COUNT(oAuthClientASLocalMetadata) FROM OAuthClientASLocalMetadata oAuthClientASLocalMetadata WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"oAuthClientASLocalMetadata.oAuthClientASLocalMetadataId";

	private static final String
		_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_WHERE =
			"SELECT DISTINCT {oAuthClientASLocalMetadata.*} FROM OAuthClientASLocalMetadata oAuthClientASLocalMetadata WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {OAuthClientASLocalMetadata.*} FROM (SELECT DISTINCT oAuthClientASLocalMetadata.oAuthClientASLocalMetadataId FROM OAuthClientASLocalMetadata oAuthClientASLocalMetadata WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OAUTHCLIENTASLOCALMETADATA_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN OAuthClientASLocalMetadata ON TEMP_TABLE.oAuthClientASLocalMetadataId = OAuthClientASLocalMetadata.oAuthClientASLocalMetadataId";

	private static final String
		_FILTER_SQL_COUNT_OAUTHCLIENTASLOCALMETADATA_WHERE =
			"SELECT COUNT(DISTINCT oAuthClientASLocalMetadata.oAuthClientASLocalMetadataId) AS COUNT_VALUE FROM OAuthClientASLocalMetadata oAuthClientASLocalMetadata WHERE ";

	private static final String _FILTER_ENTITY_ALIAS =
		"oAuthClientASLocalMetadata";

	private static final String _FILTER_ENTITY_TABLE =
		"OAuthClientASLocalMetadata";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"oAuthClientASLocalMetadata.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"OAuthClientASLocalMetadata.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OAuthClientASLocalMetadata exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OAuthClientASLocalMetadata exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthClientASLocalMetadataPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:713004000