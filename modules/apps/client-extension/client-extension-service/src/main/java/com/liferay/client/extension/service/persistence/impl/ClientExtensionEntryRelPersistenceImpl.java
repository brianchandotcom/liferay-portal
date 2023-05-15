/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.client.extension.service.persistence.impl;

import com.liferay.client.extension.exception.DuplicateClientExtensionEntryRelExternalReferenceCodeException;
import com.liferay.client.extension.exception.NoSuchClientExtensionEntryRelException;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.model.ClientExtensionEntryRelTable;
import com.liferay.client.extension.model.impl.ClientExtensionEntryRelImpl;
import com.liferay.client.extension.model.impl.ClientExtensionEntryRelModelImpl;
import com.liferay.client.extension.service.persistence.ClientExtensionEntryRelPersistence;
import com.liferay.client.extension.service.persistence.ClientExtensionEntryRelUtil;
import com.liferay.client.extension.service.persistence.impl.constants.ClientExtensionPersistenceConstants;
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
 * The persistence implementation for the client extension entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ClientExtensionEntryRelPersistence.class)
public class ClientExtensionEntryRelPersistenceImpl
	extends BasePersistenceImpl<ClientExtensionEntryRel>
	implements ClientExtensionEntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ClientExtensionEntryRelUtil</code> to access the client extension entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ClientExtensionEntryRelImpl.class.getName();

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
	 * Returns all the client extension entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the client extension entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

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

		List<ClientExtensionEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ClientExtensionEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ClientExtensionEntryRel clientExtensionEntryRel : list) {
					if (!uuid.equals(clientExtensionEntryRel.getUuid())) {
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

			sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

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
				sb.append(ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<ClientExtensionEntryRel>)QueryUtil.list(
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
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByUuid_First(
			String uuid,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByUuid_First(
			uuid, orderByComparator);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchClientExtensionEntryRelException(sb.toString());
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		List<ClientExtensionEntryRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByUuid_Last(
			String uuid,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByUuid_Last(
			uuid, orderByComparator);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchClientExtensionEntryRelException(sb.toString());
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ClientExtensionEntryRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel[] findByUuid_PrevAndNext(
			long clientExtensionEntryRelId, String uuid,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		uuid = Objects.toString(uuid, "");

		ClientExtensionEntryRel clientExtensionEntryRel = findByPrimaryKey(
			clientExtensionEntryRelId);

		Session session = null;

		try {
			session = openSession();

			ClientExtensionEntryRel[] array =
				new ClientExtensionEntryRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, clientExtensionEntryRel, uuid, orderByComparator,
				true);

			array[1] = clientExtensionEntryRel;

			array[2] = getByUuid_PrevAndNext(
				session, clientExtensionEntryRel, uuid, orderByComparator,
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

	protected ClientExtensionEntryRel getByUuid_PrevAndNext(
		Session session, ClientExtensionEntryRel clientExtensionEntryRel,
		String uuid,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

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
			sb.append(ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
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
						clientExtensionEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ClientExtensionEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the client extension entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ClientExtensionEntryRel clientExtensionEntryRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(clientExtensionEntryRel);
		}
	}

	/**
	 * Returns the number of client extension entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching client extension entry rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

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

			sb.append(_SQL_COUNT_CLIENTEXTENSIONENTRYREL_WHERE);

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
		"clientExtensionEntryRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(clientExtensionEntryRel.uuid IS NULL OR clientExtensionEntryRel.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the client extension entry rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchClientExtensionEntryRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByUUID_G(String uuid, long groupId)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByUUID_G(
			uuid, groupId);

		if (clientExtensionEntryRel == null) {
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

			throw new NoSuchClientExtensionEntryRelException(sb.toString());
		}

		return clientExtensionEntryRel;
	}

	/**
	 * Returns the client extension entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the client extension entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof ClientExtensionEntryRel) {
			ClientExtensionEntryRel clientExtensionEntryRel =
				(ClientExtensionEntryRel)result;

			if (!Objects.equals(uuid, clientExtensionEntryRel.getUuid()) ||
				(groupId != clientExtensionEntryRel.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

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

				List<ClientExtensionEntryRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					ClientExtensionEntryRel clientExtensionEntryRel = list.get(
						0);

					result = clientExtensionEntryRel;

					cacheResult(clientExtensionEntryRel);
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
			return (ClientExtensionEntryRel)result;
		}
	}

	/**
	 * Removes the client extension entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the client extension entry rel that was removed
	 */
	@Override
	public ClientExtensionEntryRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = findByUUID_G(
			uuid, groupId);

		return remove(clientExtensionEntryRel);
	}

	/**
	 * Returns the number of client extension entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching client extension entry rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

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

			sb.append(_SQL_COUNT_CLIENTEXTENSIONENTRYREL_WHERE);

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
		"clientExtensionEntryRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(clientExtensionEntryRel.uuid IS NULL OR clientExtensionEntryRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"clientExtensionEntryRel.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

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

		List<ClientExtensionEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ClientExtensionEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ClientExtensionEntryRel clientExtensionEntryRel : list) {
					if (!uuid.equals(clientExtensionEntryRel.getUuid()) ||
						(companyId != clientExtensionEntryRel.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

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
				sb.append(ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<ClientExtensionEntryRel>)QueryUtil.list(
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
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchClientExtensionEntryRelException(sb.toString());
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		List<ClientExtensionEntryRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchClientExtensionEntryRelException(sb.toString());
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ClientExtensionEntryRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel[] findByUuid_C_PrevAndNext(
			long clientExtensionEntryRelId, String uuid, long companyId,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		uuid = Objects.toString(uuid, "");

		ClientExtensionEntryRel clientExtensionEntryRel = findByPrimaryKey(
			clientExtensionEntryRelId);

		Session session = null;

		try {
			session = openSession();

			ClientExtensionEntryRel[] array =
				new ClientExtensionEntryRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, clientExtensionEntryRel, uuid, companyId,
				orderByComparator, true);

			array[1] = clientExtensionEntryRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, clientExtensionEntryRel, uuid, companyId,
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

	protected ClientExtensionEntryRel getByUuid_C_PrevAndNext(
		Session session, ClientExtensionEntryRel clientExtensionEntryRel,
		String uuid, long companyId,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

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
			sb.append(ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
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
						clientExtensionEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ClientExtensionEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the client extension entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ClientExtensionEntryRel clientExtensionEntryRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(clientExtensionEntryRel);
		}
	}

	/**
	 * Returns the number of client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching client extension entry rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

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

			sb.append(_SQL_COUNT_CLIENTEXTENSIONENTRYREL_WHERE);

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
		"clientExtensionEntryRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(clientExtensionEntryRel.uuid IS NULL OR clientExtensionEntryRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"clientExtensionEntryRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_CETERC;
	private FinderPath _finderPathWithoutPaginationFindByC_CETERC;
	private FinderPath _finderPathCountByC_CETERC;

	/**
	 * Returns all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @return the matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode) {

		return findByC_CETERC(
			companyId, cetExternalReferenceCode, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode, int start, int end) {

		return findByC_CETERC(
			companyId, cetExternalReferenceCode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return findByC_CETERC(
			companyId, cetExternalReferenceCode, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		cetExternalReferenceCode = Objects.toString(
			cetExternalReferenceCode, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_CETERC;
				finderArgs = new Object[] {companyId, cetExternalReferenceCode};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_CETERC;
			finderArgs = new Object[] {
				companyId, cetExternalReferenceCode, start, end,
				orderByComparator
			};
		}

		List<ClientExtensionEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ClientExtensionEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ClientExtensionEntryRel clientExtensionEntryRel : list) {
					if ((companyId != clientExtensionEntryRel.getCompanyId()) ||
						!cetExternalReferenceCode.equals(
							clientExtensionEntryRel.
								getCETExternalReferenceCode())) {

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

			sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_CETERC_COMPANYID_2);

			boolean bindCETExternalReferenceCode = false;

			if (cetExternalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_CETERC_CETEXTERNALREFERENCECODE_3);
			}
			else {
				bindCETExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_CETERC_CETEXTERNALREFERENCECODE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCETExternalReferenceCode) {
					queryPos.add(cetExternalReferenceCode);
				}

				list = (List<ClientExtensionEntryRel>)QueryUtil.list(
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
	 * Returns the first client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByC_CETERC_First(
			long companyId, String cetExternalReferenceCode,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByC_CETERC_First(
			companyId, cetExternalReferenceCode, orderByComparator);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", cetExternalReferenceCode=");
		sb.append(cetExternalReferenceCode);

		sb.append("}");

		throw new NoSuchClientExtensionEntryRelException(sb.toString());
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByC_CETERC_First(
		long companyId, String cetExternalReferenceCode,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		List<ClientExtensionEntryRel> list = findByC_CETERC(
			companyId, cetExternalReferenceCode, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByC_CETERC_Last(
			long companyId, String cetExternalReferenceCode,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByC_CETERC_Last(
			companyId, cetExternalReferenceCode, orderByComparator);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", cetExternalReferenceCode=");
		sb.append(cetExternalReferenceCode);

		sb.append("}");

		throw new NoSuchClientExtensionEntryRelException(sb.toString());
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByC_CETERC_Last(
		long companyId, String cetExternalReferenceCode,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		int count = countByC_CETERC(companyId, cetExternalReferenceCode);

		if (count == 0) {
			return null;
		}

		List<ClientExtensionEntryRel> list = findByC_CETERC(
			companyId, cetExternalReferenceCode, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel[] findByC_CETERC_PrevAndNext(
			long clientExtensionEntryRelId, long companyId,
			String cetExternalReferenceCode,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		cetExternalReferenceCode = Objects.toString(
			cetExternalReferenceCode, "");

		ClientExtensionEntryRel clientExtensionEntryRel = findByPrimaryKey(
			clientExtensionEntryRelId);

		Session session = null;

		try {
			session = openSession();

			ClientExtensionEntryRel[] array =
				new ClientExtensionEntryRelImpl[3];

			array[0] = getByC_CETERC_PrevAndNext(
				session, clientExtensionEntryRel, companyId,
				cetExternalReferenceCode, orderByComparator, true);

			array[1] = clientExtensionEntryRel;

			array[2] = getByC_CETERC_PrevAndNext(
				session, clientExtensionEntryRel, companyId,
				cetExternalReferenceCode, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ClientExtensionEntryRel getByC_CETERC_PrevAndNext(
		Session session, ClientExtensionEntryRel clientExtensionEntryRel,
		long companyId, String cetExternalReferenceCode,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_C_CETERC_COMPANYID_2);

		boolean bindCETExternalReferenceCode = false;

		if (cetExternalReferenceCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_CETERC_CETEXTERNALREFERENCECODE_3);
		}
		else {
			bindCETExternalReferenceCode = true;

			sb.append(_FINDER_COLUMN_C_CETERC_CETEXTERNALREFERENCECODE_2);
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
			sb.append(ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindCETExternalReferenceCode) {
			queryPos.add(cetExternalReferenceCode);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						clientExtensionEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ClientExtensionEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 */
	@Override
	public void removeByC_CETERC(
		long companyId, String cetExternalReferenceCode) {

		for (ClientExtensionEntryRel clientExtensionEntryRel :
				findByC_CETERC(
					companyId, cetExternalReferenceCode, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(clientExtensionEntryRel);
		}
	}

	/**
	 * Returns the number of client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @return the number of matching client extension entry rels
	 */
	@Override
	public int countByC_CETERC(
		long companyId, String cetExternalReferenceCode) {

		cetExternalReferenceCode = Objects.toString(
			cetExternalReferenceCode, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_CETERC;

			finderArgs = new Object[] {companyId, cetExternalReferenceCode};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CLIENTEXTENSIONENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_CETERC_COMPANYID_2);

			boolean bindCETExternalReferenceCode = false;

			if (cetExternalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_CETERC_CETEXTERNALREFERENCECODE_3);
			}
			else {
				bindCETExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_CETERC_CETEXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCETExternalReferenceCode) {
					queryPos.add(cetExternalReferenceCode);
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

	private static final String _FINDER_COLUMN_C_CETERC_COMPANYID_2 =
		"clientExtensionEntryRel.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_CETERC_CETEXTERNALREFERENCECODE_2 =
			"clientExtensionEntryRel.cetExternalReferenceCode = ?";

	private static final String
		_FINDER_COLUMN_C_CETERC_CETEXTERNALREFERENCECODE_3 =
			"(clientExtensionEntryRel.cetExternalReferenceCode IS NULL OR clientExtensionEntryRel.cetExternalReferenceCode = '')";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK) {

		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<ClientExtensionEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ClientExtensionEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ClientExtensionEntryRel clientExtensionEntryRel : list) {
					if ((classNameId !=
							clientExtensionEntryRel.getClassNameId()) ||
						(classPK != clientExtensionEntryRel.getClassPK())) {

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

			sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<ClientExtensionEntryRel>)QueryUtil.list(
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
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchClientExtensionEntryRelException(sb.toString());
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		List<ClientExtensionEntryRel> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchClientExtensionEntryRelException(sb.toString());
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<ClientExtensionEntryRel> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel[] findByC_C_PrevAndNext(
			long clientExtensionEntryRelId, long classNameId, long classPK,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = findByPrimaryKey(
			clientExtensionEntryRelId);

		Session session = null;

		try {
			session = openSession();

			ClientExtensionEntryRel[] array =
				new ClientExtensionEntryRelImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, clientExtensionEntryRel, classNameId, classPK,
				orderByComparator, true);

			array[1] = clientExtensionEntryRel;

			array[2] = getByC_C_PrevAndNext(
				session, clientExtensionEntryRel, classNameId, classPK,
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

	protected ClientExtensionEntryRel getByC_C_PrevAndNext(
		Session session, ClientExtensionEntryRel clientExtensionEntryRel,
		long classNameId, long classPK,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						clientExtensionEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ClientExtensionEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the client extension entry rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (ClientExtensionEntryRel clientExtensionEntryRel :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(clientExtensionEntryRel);
		}
	}

	/**
	 * Returns the number of client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching client extension entry rels
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CLIENTEXTENSIONENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"clientExtensionEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"clientExtensionEntryRel.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_T;
	private FinderPath _finderPathWithoutPaginationFindByC_C_T;
	private FinderPath _finderPathCountByC_C_T;

	/**
	 * Returns all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type) {

		return findByC_C_T(
			classNameId, classPK, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type, int start, int end) {

		return findByC_C_T(classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C_T;
				finderArgs = new Object[] {classNameId, classPK, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C_T;
			finderArgs = new Object[] {
				classNameId, classPK, type, start, end, orderByComparator
			};
		}

		List<ClientExtensionEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ClientExtensionEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ClientExtensionEntryRel clientExtensionEntryRel : list) {
					if ((classNameId !=
							clientExtensionEntryRel.getClassNameId()) ||
						(classPK != clientExtensionEntryRel.getClassPK()) ||
						!type.equals(clientExtensionEntryRel.getType())) {

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

			sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_C_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<ClientExtensionEntryRel>)QueryUtil.list(
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
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByC_C_T_First(
			long classNameId, long classPK, String type,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByC_C_T_First(
			classNameId, classPK, type, orderByComparator);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchClientExtensionEntryRelException(sb.toString());
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByC_C_T_First(
		long classNameId, long classPK, String type,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		List<ClientExtensionEntryRel> list = findByC_C_T(
			classNameId, classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByC_C_T_Last(
			long classNameId, long classPK, String type,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByC_C_T_Last(
			classNameId, classPK, type, orderByComparator);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchClientExtensionEntryRelException(sb.toString());
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByC_C_T_Last(
		long classNameId, long classPK, String type,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		int count = countByC_C_T(classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<ClientExtensionEntryRel> list = findByC_C_T(
			classNameId, classPK, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel[] findByC_C_T_PrevAndNext(
			long clientExtensionEntryRelId, long classNameId, long classPK,
			String type,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws NoSuchClientExtensionEntryRelException {

		type = Objects.toString(type, "");

		ClientExtensionEntryRel clientExtensionEntryRel = findByPrimaryKey(
			clientExtensionEntryRelId);

		Session session = null;

		try {
			session = openSession();

			ClientExtensionEntryRel[] array =
				new ClientExtensionEntryRelImpl[3];

			array[0] = getByC_C_T_PrevAndNext(
				session, clientExtensionEntryRel, classNameId, classPK, type,
				orderByComparator, true);

			array[1] = clientExtensionEntryRel;

			array[2] = getByC_C_T_PrevAndNext(
				session, clientExtensionEntryRel, classNameId, classPK, type,
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

	protected ClientExtensionEntryRel getByC_C_T_PrevAndNext(
		Session session, ClientExtensionEntryRel clientExtensionEntryRel,
		long classNameId, long classPK, String type,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_C_T_TYPE_2);
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
			sb.append(ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						clientExtensionEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ClientExtensionEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	@Override
	public void removeByC_C_T(long classNameId, long classPK, String type) {
		for (ClientExtensionEntryRel clientExtensionEntryRel :
				findByC_C_T(
					classNameId, classPK, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(clientExtensionEntryRel);
		}
	}

	/**
	 * Returns the number of client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching client extension entry rels
	 */
	@Override
	public int countByC_C_T(long classNameId, long classPK, String type) {
		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_T;

			finderArgs = new Object[] {classNameId, classPK, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CLIENTEXTENSIONENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_C_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindType) {
					queryPos.add(type);
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

	private static final String _FINDER_COLUMN_C_C_T_CLASSNAMEID_2 =
		"clientExtensionEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_CLASSPK_2 =
		"clientExtensionEntryRel.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_TYPE_2 =
		"clientExtensionEntryRel.type = ?";

	private static final String _FINDER_COLUMN_C_C_T_TYPE_3 =
		"(clientExtensionEntryRel.type IS NULL OR clientExtensionEntryRel.type = '')";

	private FinderPath _finderPathFetchByERC_G;
	private FinderPath _finderPathCountByERC_G;

	/**
	 * Returns the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchClientExtensionEntryRelException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByERC_G(
			externalReferenceCode, groupId);

		if (clientExtensionEntryRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchClientExtensionEntryRelException(sb.toString());
		}

		return clientExtensionEntryRel;
	}

	/**
	 * Returns the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByERC_G(
		String externalReferenceCode, long groupId) {

		return fetchByERC_G(externalReferenceCode, groupId, true);
	}

	/**
	 * Returns the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {externalReferenceCode, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByERC_G, finderArgs, this);
		}

		if (result instanceof ClientExtensionEntryRel) {
			ClientExtensionEntryRel clientExtensionEntryRel =
				(ClientExtensionEntryRel)result;

			if (!Objects.equals(
					externalReferenceCode,
					clientExtensionEntryRel.getExternalReferenceCode()) ||
				(groupId != clientExtensionEntryRel.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_2);
			}

			sb.append(_FINDER_COLUMN_ERC_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				queryPos.add(groupId);

				List<ClientExtensionEntryRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByERC_G, finderArgs, list);
					}
				}
				else {
					ClientExtensionEntryRel clientExtensionEntryRel = list.get(
						0);

					result = clientExtensionEntryRel;

					cacheResult(clientExtensionEntryRel);
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
			return (ClientExtensionEntryRel)result;
		}
	}

	/**
	 * Removes the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the client extension entry rel that was removed
	 */
	@Override
	public ClientExtensionEntryRel removeByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = findByERC_G(
			externalReferenceCode, groupId);

		return remove(clientExtensionEntryRel);
	}

	/**
	 * Returns the number of client extension entry rels where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching client extension entry rels
	 */
	@Override
	public int countByERC_G(String externalReferenceCode, long groupId) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByERC_G;

			finderArgs = new Object[] {externalReferenceCode, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CLIENTEXTENSIONENTRYREL_WHERE);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_2);
			}

			sb.append(_FINDER_COLUMN_ERC_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
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

	private static final String _FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_2 =
		"clientExtensionEntryRel.externalReferenceCode = ? AND ";

	private static final String _FINDER_COLUMN_ERC_G_EXTERNALREFERENCECODE_3 =
		"(clientExtensionEntryRel.externalReferenceCode IS NULL OR clientExtensionEntryRel.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_G_GROUPID_2 =
		"clientExtensionEntryRel.groupId = ?";

	public ClientExtensionEntryRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ClientExtensionEntryRel.class);

		setModelImplClass(ClientExtensionEntryRelImpl.class);
		setModelPKClass(long.class);

		setTable(ClientExtensionEntryRelTable.INSTANCE);
	}

	/**
	 * Caches the client extension entry rel in the entity cache if it is enabled.
	 *
	 * @param clientExtensionEntryRel the client extension entry rel
	 */
	@Override
	public void cacheResult(ClientExtensionEntryRel clientExtensionEntryRel) {
		if (clientExtensionEntryRel.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			ClientExtensionEntryRelImpl.class,
			clientExtensionEntryRel.getPrimaryKey(), clientExtensionEntryRel);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				clientExtensionEntryRel.getUuid(),
				clientExtensionEntryRel.getGroupId()
			},
			clientExtensionEntryRel);

		finderCache.putResult(
			_finderPathFetchByERC_G,
			new Object[] {
				clientExtensionEntryRel.getExternalReferenceCode(),
				clientExtensionEntryRel.getGroupId()
			},
			clientExtensionEntryRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the client extension entry rels in the entity cache if it is enabled.
	 *
	 * @param clientExtensionEntryRels the client extension entry rels
	 */
	@Override
	public void cacheResult(
		List<ClientExtensionEntryRel> clientExtensionEntryRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (clientExtensionEntryRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ClientExtensionEntryRel clientExtensionEntryRel :
				clientExtensionEntryRels) {

			if (clientExtensionEntryRel.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					ClientExtensionEntryRelImpl.class,
					clientExtensionEntryRel.getPrimaryKey()) == null) {

				cacheResult(clientExtensionEntryRel);
			}
		}
	}

	/**
	 * Clears the cache for all client extension entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ClientExtensionEntryRelImpl.class);

		finderCache.clearCache(ClientExtensionEntryRelImpl.class);
	}

	/**
	 * Clears the cache for the client extension entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ClientExtensionEntryRel clientExtensionEntryRel) {
		entityCache.removeResult(
			ClientExtensionEntryRelImpl.class, clientExtensionEntryRel);
	}

	@Override
	public void clearCache(
		List<ClientExtensionEntryRel> clientExtensionEntryRels) {

		for (ClientExtensionEntryRel clientExtensionEntryRel :
				clientExtensionEntryRels) {

			entityCache.removeResult(
				ClientExtensionEntryRelImpl.class, clientExtensionEntryRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ClientExtensionEntryRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ClientExtensionEntryRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ClientExtensionEntryRelModelImpl clientExtensionEntryRelModelImpl) {

		Object[] args = new Object[] {
			clientExtensionEntryRelModelImpl.getUuid(),
			clientExtensionEntryRelModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, clientExtensionEntryRelModelImpl);

		args = new Object[] {
			clientExtensionEntryRelModelImpl.getExternalReferenceCode(),
			clientExtensionEntryRelModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByERC_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByERC_G, args, clientExtensionEntryRelModelImpl);
	}

	/**
	 * Creates a new client extension entry rel with the primary key. Does not add the client extension entry rel to the database.
	 *
	 * @param clientExtensionEntryRelId the primary key for the new client extension entry rel
	 * @return the new client extension entry rel
	 */
	@Override
	public ClientExtensionEntryRel create(long clientExtensionEntryRelId) {
		ClientExtensionEntryRel clientExtensionEntryRel =
			new ClientExtensionEntryRelImpl();

		clientExtensionEntryRel.setNew(true);
		clientExtensionEntryRel.setPrimaryKey(clientExtensionEntryRelId);

		String uuid = _portalUUID.generate();

		clientExtensionEntryRel.setUuid(uuid);

		clientExtensionEntryRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return clientExtensionEntryRel;
	}

	/**
	 * Removes the client extension entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel that was removed
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel remove(long clientExtensionEntryRelId)
		throws NoSuchClientExtensionEntryRelException {

		return remove((Serializable)clientExtensionEntryRelId);
	}

	/**
	 * Removes the client extension entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the client extension entry rel
	 * @return the client extension entry rel that was removed
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel remove(Serializable primaryKey)
		throws NoSuchClientExtensionEntryRelException {

		Session session = null;

		try {
			session = openSession();

			ClientExtensionEntryRel clientExtensionEntryRel =
				(ClientExtensionEntryRel)session.get(
					ClientExtensionEntryRelImpl.class, primaryKey);

			if (clientExtensionEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchClientExtensionEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(clientExtensionEntryRel);
		}
		catch (NoSuchClientExtensionEntryRelException noSuchEntityException) {
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
	protected ClientExtensionEntryRel removeImpl(
		ClientExtensionEntryRel clientExtensionEntryRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(clientExtensionEntryRel)) {
				clientExtensionEntryRel = (ClientExtensionEntryRel)session.get(
					ClientExtensionEntryRelImpl.class,
					clientExtensionEntryRel.getPrimaryKeyObj());
			}

			if ((clientExtensionEntryRel != null) &&
				ctPersistenceHelper.isRemove(clientExtensionEntryRel)) {

				session.delete(clientExtensionEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (clientExtensionEntryRel != null) {
			clearCache(clientExtensionEntryRel);
		}

		return clientExtensionEntryRel;
	}

	@Override
	public ClientExtensionEntryRel updateImpl(
		ClientExtensionEntryRel clientExtensionEntryRel) {

		boolean isNew = clientExtensionEntryRel.isNew();

		if (!(clientExtensionEntryRel instanceof
				ClientExtensionEntryRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(clientExtensionEntryRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					clientExtensionEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in clientExtensionEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ClientExtensionEntryRel implementation " +
					clientExtensionEntryRel.getClass());
		}

		ClientExtensionEntryRelModelImpl clientExtensionEntryRelModelImpl =
			(ClientExtensionEntryRelModelImpl)clientExtensionEntryRel;

		if (Validator.isNull(clientExtensionEntryRel.getUuid())) {
			String uuid = _portalUUID.generate();

			clientExtensionEntryRel.setUuid(uuid);
		}

		if (Validator.isNull(
				clientExtensionEntryRel.getExternalReferenceCode())) {

			clientExtensionEntryRel.setExternalReferenceCode(
				clientExtensionEntryRel.getUuid());
		}
		else {
			ClientExtensionEntryRel ercClientExtensionEntryRel = fetchByERC_G(
				clientExtensionEntryRel.getExternalReferenceCode(),
				clientExtensionEntryRel.getGroupId());

			if (isNew) {
				if (ercClientExtensionEntryRel != null) {
					throw new DuplicateClientExtensionEntryRelExternalReferenceCodeException(
						"Duplicate client extension entry rel with external reference code " +
							clientExtensionEntryRel.getExternalReferenceCode() +
								" and group " +
									clientExtensionEntryRel.getGroupId());
				}
			}
			else {
				if ((ercClientExtensionEntryRel != null) &&
					(clientExtensionEntryRel.getClientExtensionEntryRelId() !=
						ercClientExtensionEntryRel.
							getClientExtensionEntryRelId())) {

					throw new DuplicateClientExtensionEntryRelExternalReferenceCodeException(
						"Duplicate client extension entry rel with external reference code " +
							clientExtensionEntryRel.getExternalReferenceCode() +
								" and group " +
									clientExtensionEntryRel.getGroupId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (clientExtensionEntryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				clientExtensionEntryRel.setCreateDate(date);
			}
			else {
				clientExtensionEntryRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!clientExtensionEntryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				clientExtensionEntryRel.setModifiedDate(date);
			}
			else {
				clientExtensionEntryRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(clientExtensionEntryRel)) {
				if (!isNew) {
					session.evict(
						ClientExtensionEntryRelImpl.class,
						clientExtensionEntryRel.getPrimaryKeyObj());
				}

				session.save(clientExtensionEntryRel);
			}
			else {
				clientExtensionEntryRel =
					(ClientExtensionEntryRel)session.merge(
						clientExtensionEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (clientExtensionEntryRel.getCtCollectionId() != 0) {
			if (isNew) {
				clientExtensionEntryRel.setNew(false);
			}

			clientExtensionEntryRel.resetOriginalValues();

			return clientExtensionEntryRel;
		}

		entityCache.putResult(
			ClientExtensionEntryRelImpl.class, clientExtensionEntryRelModelImpl,
			false, true);

		cacheUniqueFindersCache(clientExtensionEntryRelModelImpl);

		if (isNew) {
			clientExtensionEntryRel.setNew(false);
		}

		clientExtensionEntryRel.resetOriginalValues();

		return clientExtensionEntryRel;
	}

	/**
	 * Returns the client extension entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the client extension entry rel
	 * @return the client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchClientExtensionEntryRelException {

		ClientExtensionEntryRel clientExtensionEntryRel = fetchByPrimaryKey(
			primaryKey);

		if (clientExtensionEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchClientExtensionEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return clientExtensionEntryRel;
	}

	/**
	 * Returns the client extension entry rel with the primary key or throws a <code>NoSuchClientExtensionEntryRelException</code> if it could not be found.
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel findByPrimaryKey(
			long clientExtensionEntryRelId)
		throws NoSuchClientExtensionEntryRelException {

		return findByPrimaryKey((Serializable)clientExtensionEntryRelId);
	}

	/**
	 * Returns the client extension entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the client extension entry rel
	 * @return the client extension entry rel, or <code>null</code> if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				ClientExtensionEntryRel.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		ClientExtensionEntryRel clientExtensionEntryRel = null;

		Session session = null;

		try {
			session = openSession();

			clientExtensionEntryRel = (ClientExtensionEntryRel)session.get(
				ClientExtensionEntryRelImpl.class, primaryKey);

			if (clientExtensionEntryRel != null) {
				cacheResult(clientExtensionEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return clientExtensionEntryRel;
	}

	/**
	 * Returns the client extension entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel, or <code>null</code> if a client extension entry rel with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntryRel fetchByPrimaryKey(
		long clientExtensionEntryRelId) {

		return fetchByPrimaryKey((Serializable)clientExtensionEntryRelId);
	}

	@Override
	public Map<Serializable, ClientExtensionEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				ClientExtensionEntryRel.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ClientExtensionEntryRel> map =
			new HashMap<Serializable, ClientExtensionEntryRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ClientExtensionEntryRel clientExtensionEntryRel = fetchByPrimaryKey(
				primaryKey);

			if (clientExtensionEntryRel != null) {
				map.put(primaryKey, clientExtensionEntryRel);
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

			for (ClientExtensionEntryRel clientExtensionEntryRel :
					(List<ClientExtensionEntryRel>)query.list()) {

				map.put(
					clientExtensionEntryRel.getPrimaryKeyObj(),
					clientExtensionEntryRel);

				cacheResult(clientExtensionEntryRel);
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
	 * Returns all the client extension entry rels.
	 *
	 * @return the client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the client extension entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findAll(
		int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of client extension entry rels
	 */
	@Override
	public List<ClientExtensionEntryRel> findAll(
		int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

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

		List<ClientExtensionEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ClientExtensionEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CLIENTEXTENSIONENTRYREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CLIENTEXTENSIONENTRYREL;

				sql = sql.concat(
					ClientExtensionEntryRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ClientExtensionEntryRel>)QueryUtil.list(
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
	 * Removes all the client extension entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ClientExtensionEntryRel clientExtensionEntryRel : findAll()) {
			remove(clientExtensionEntryRel);
		}
	}

	/**
	 * Returns the number of client extension entry rels.
	 *
	 * @return the number of client extension entry rels
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			ClientExtensionEntryRel.class);

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
					_SQL_COUNT_CLIENTEXTENSIONENTRYREL);

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
		return "clientExtensionEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CLIENTEXTENSIONENTRYREL;
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
		return ClientExtensionEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "ClientExtensionEntryRel";
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
		ctStrictColumnNames.add("externalReferenceCode");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("cetExternalReferenceCode");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("typeSettings");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("clientExtensionEntryRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "groupId"});
	}

	/**
	 * Initializes the client extension entry rel persistence.
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

		_finderPathWithPaginationFindByC_CETERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CETERC",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "cetExternalReferenceCode"}, true);

		_finderPathWithoutPaginationFindByC_CETERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CETERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "cetExternalReferenceCode"}, true);

		_finderPathCountByC_CETERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CETERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "cetExternalReferenceCode"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathWithPaginationFindByC_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK", "type_"}, true);

		_finderPathWithoutPaginationFindByC_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"classNameId", "classPK", "type_"}, true);

		_finderPathCountByC_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"classNameId", "classPK", "type_"}, false);

		_finderPathFetchByERC_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "groupId"}, true);

		_finderPathCountByERC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByERC_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "groupId"}, false);

		ClientExtensionEntryRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ClientExtensionEntryRelUtil.setPersistence(null);

		entityCache.removeCache(ClientExtensionEntryRelImpl.class.getName());
	}

	@Override
	@Reference(
		target = ClientExtensionPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ClientExtensionPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ClientExtensionPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_CLIENTEXTENSIONENTRYREL =
		"SELECT clientExtensionEntryRel FROM ClientExtensionEntryRel clientExtensionEntryRel";

	private static final String _SQL_SELECT_CLIENTEXTENSIONENTRYREL_WHERE =
		"SELECT clientExtensionEntryRel FROM ClientExtensionEntryRel clientExtensionEntryRel WHERE ";

	private static final String _SQL_COUNT_CLIENTEXTENSIONENTRYREL =
		"SELECT COUNT(clientExtensionEntryRel) FROM ClientExtensionEntryRel clientExtensionEntryRel";

	private static final String _SQL_COUNT_CLIENTEXTENSIONENTRYREL_WHERE =
		"SELECT COUNT(clientExtensionEntryRel) FROM ClientExtensionEntryRel clientExtensionEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"clientExtensionEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ClientExtensionEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ClientExtensionEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ClientExtensionEntryRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}