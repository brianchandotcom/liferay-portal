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

package com.liferay.commerce.notification.service.persistence.impl;

import com.liferay.commerce.notification.exception.NoSuchNotificationAttachmentException;
import com.liferay.commerce.notification.model.CommerceNotificationAttachment;
import com.liferay.commerce.notification.model.CommerceNotificationAttachmentTable;
import com.liferay.commerce.notification.model.impl.CommerceNotificationAttachmentImpl;
import com.liferay.commerce.notification.model.impl.CommerceNotificationAttachmentModelImpl;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationAttachmentPersistence;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce notification attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceNotificationAttachmentPersistenceImpl
	extends BasePersistenceImpl<CommerceNotificationAttachment>
	implements CommerceNotificationAttachmentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceNotificationAttachmentUtil</code> to access the commerce notification attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceNotificationAttachmentImpl.class.getName();

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
	 * Returns all the commerce notification attachments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
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

		List<CommerceNotificationAttachment> list = null;

		if (useFinderCache) {
			list = (List<CommerceNotificationAttachment>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationAttachment
						commerceNotificationAttachment : list) {

					if (!uuid.equals(
							commerceNotificationAttachment.getUuid())) {

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

			sb.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

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
				sb.append(
					CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceNotificationAttachment>)QueryUtil.list(
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
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment findByUuid_First(
			String uuid,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {

		CommerceNotificationAttachment commerceNotificationAttachment =
			fetchByUuid_First(uuid, orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchNotificationAttachmentException(sb.toString());
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		List<CommerceNotificationAttachment> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {

		CommerceNotificationAttachment commerceNotificationAttachment =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchNotificationAttachmentException(sb.toString());
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationAttachment> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification attachments before and after the current commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the current commerce notification attachment
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	@Override
	public CommerceNotificationAttachment[] findByUuid_PrevAndNext(
			long commerceNotificationAttachmentId, String uuid,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {

		uuid = Objects.toString(uuid, "");

		CommerceNotificationAttachment commerceNotificationAttachment =
			findByPrimaryKey(commerceNotificationAttachmentId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationAttachment[] array =
				new CommerceNotificationAttachmentImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceNotificationAttachment, uuid,
				orderByComparator, true);

			array[1] = commerceNotificationAttachment;

			array[2] = getByUuid_PrevAndNext(
				session, commerceNotificationAttachment, uuid,
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

	protected CommerceNotificationAttachment getByUuid_PrevAndNext(
		Session session,
		CommerceNotificationAttachment commerceNotificationAttachment,
		String uuid,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

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
			sb.append(CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
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
						commerceNotificationAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceNotificationAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification attachments where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceNotificationAttachment commerceNotificationAttachment :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceNotificationAttachment);
		}
	}

	/**
	 * Returns the number of commerce notification attachments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce notification attachments
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

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
		"commerceNotificationAttachment.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceNotificationAttachment.uuid IS NULL OR commerceNotificationAttachment.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchNotificationAttachmentException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment findByUUID_G(
			String uuid, long groupId)
		throws NoSuchNotificationAttachmentException {

		CommerceNotificationAttachment commerceNotificationAttachment =
			fetchByUUID_G(uuid, groupId);

		if (commerceNotificationAttachment == null) {
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

			throw new NoSuchNotificationAttachmentException(sb.toString());
		}

		return commerceNotificationAttachment;
	}

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof CommerceNotificationAttachment) {
			CommerceNotificationAttachment commerceNotificationAttachment =
				(CommerceNotificationAttachment)result;

			if (!Objects.equals(
					uuid, commerceNotificationAttachment.getUuid()) ||
				(groupId != commerceNotificationAttachment.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

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

				List<CommerceNotificationAttachment> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CommerceNotificationAttachment
						commerceNotificationAttachment = list.get(0);

					result = commerceNotificationAttachment;

					cacheResult(commerceNotificationAttachment);
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
			return (CommerceNotificationAttachment)result;
		}
	}

	/**
	 * Removes the commerce notification attachment where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce notification attachment that was removed
	 */
	@Override
	public CommerceNotificationAttachment removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchNotificationAttachmentException {

		CommerceNotificationAttachment commerceNotificationAttachment =
			findByUUID_G(uuid, groupId);

		return remove(commerceNotificationAttachment);
	}

	/**
	 * Returns the number of commerce notification attachments where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce notification attachments
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"commerceNotificationAttachment.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(commerceNotificationAttachment.uuid IS NULL OR commerceNotificationAttachment.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"commerceNotificationAttachment.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
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

		List<CommerceNotificationAttachment> list = null;

		if (useFinderCache) {
			list = (List<CommerceNotificationAttachment>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationAttachment
						commerceNotificationAttachment : list) {

					if (!uuid.equals(
							commerceNotificationAttachment.getUuid()) ||
						(companyId !=
							commerceNotificationAttachment.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

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
				sb.append(
					CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceNotificationAttachment>)QueryUtil.list(
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
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {

		CommerceNotificationAttachment commerceNotificationAttachment =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNotificationAttachmentException(sb.toString());
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		List<CommerceNotificationAttachment> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {

		CommerceNotificationAttachment commerceNotificationAttachment =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNotificationAttachmentException(sb.toString());
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationAttachment> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification attachments before and after the current commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the current commerce notification attachment
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	@Override
	public CommerceNotificationAttachment[] findByUuid_C_PrevAndNext(
			long commerceNotificationAttachmentId, String uuid, long companyId,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {

		uuid = Objects.toString(uuid, "");

		CommerceNotificationAttachment commerceNotificationAttachment =
			findByPrimaryKey(commerceNotificationAttachmentId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationAttachment[] array =
				new CommerceNotificationAttachmentImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceNotificationAttachment, uuid, companyId,
				orderByComparator, true);

			array[1] = commerceNotificationAttachment;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceNotificationAttachment, uuid, companyId,
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

	protected CommerceNotificationAttachment getByUuid_C_PrevAndNext(
		Session session,
		CommerceNotificationAttachment commerceNotificationAttachment,
		String uuid, long companyId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

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
			sb.append(CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
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
						commerceNotificationAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceNotificationAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification attachments where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceNotificationAttachment commerceNotificationAttachment :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceNotificationAttachment);
		}
	}

	/**
	 * Returns the number of commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce notification attachments
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

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
		"commerceNotificationAttachment.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceNotificationAttachment.uuid IS NULL OR commerceNotificationAttachment.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceNotificationAttachment.companyId = ?";

	private FinderPath
		_finderPathWithPaginationFindByCommerceNotificationQueueEntryId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceNotificationQueueEntryId;
	private FinderPath _finderPathCountByCommerceNotificationQueueEntryId;

	/**
	 * Returns all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @return the matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId) {

		return findByCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId, int start, int end) {

		return findByCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId, int start, int end,
			OrderByComparator<CommerceNotificationAttachment>
				orderByComparator) {

		return findByCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId, int start, int end,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceNotificationQueueEntryId;
				finderArgs = new Object[] {commerceNotificationQueueEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceNotificationQueueEntryId;
			finderArgs = new Object[] {
				commerceNotificationQueueEntryId, start, end, orderByComparator
			};
		}

		List<CommerceNotificationAttachment> list = null;

		if (useFinderCache) {
			list = (List<CommerceNotificationAttachment>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationAttachment
						commerceNotificationAttachment : list) {

					if (commerceNotificationQueueEntryId !=
							commerceNotificationAttachment.
								getCommerceNotificationQueueEntryId()) {

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

			sb.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCENOTIFICATIONQUEUEENTRYID_COMMERCENOTIFICATIONQUEUEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceNotificationQueueEntryId);

				list = (List<CommerceNotificationAttachment>)QueryUtil.list(
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
	 * Returns the first commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment
			findByCommerceNotificationQueueEntryId_First(
				long commerceNotificationQueueEntryId,
				OrderByComparator<CommerceNotificationAttachment>
					orderByComparator)
		throws NoSuchNotificationAttachmentException {

		CommerceNotificationAttachment commerceNotificationAttachment =
			fetchByCommerceNotificationQueueEntryId_First(
				commerceNotificationQueueEntryId, orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceNotificationQueueEntryId=");
		sb.append(commerceNotificationQueueEntryId);

		sb.append("}");

		throw new NoSuchNotificationAttachmentException(sb.toString());
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment
		fetchByCommerceNotificationQueueEntryId_First(
			long commerceNotificationQueueEntryId,
			OrderByComparator<CommerceNotificationAttachment>
				orderByComparator) {

		List<CommerceNotificationAttachment> list =
			findByCommerceNotificationQueueEntryId(
				commerceNotificationQueueEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment
			findByCommerceNotificationQueueEntryId_Last(
				long commerceNotificationQueueEntryId,
				OrderByComparator<CommerceNotificationAttachment>
					orderByComparator)
		throws NoSuchNotificationAttachmentException {

		CommerceNotificationAttachment commerceNotificationAttachment =
			fetchByCommerceNotificationQueueEntryId_Last(
				commerceNotificationQueueEntryId, orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceNotificationQueueEntryId=");
		sb.append(commerceNotificationQueueEntryId);

		sb.append("}");

		throw new NoSuchNotificationAttachmentException(sb.toString());
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment
		fetchByCommerceNotificationQueueEntryId_Last(
			long commerceNotificationQueueEntryId,
			OrderByComparator<CommerceNotificationAttachment>
				orderByComparator) {

		int count = countByCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationAttachment> list =
			findByCommerceNotificationQueueEntryId(
				commerceNotificationQueueEntryId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification attachments before and after the current commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the current commerce notification attachment
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	@Override
	public CommerceNotificationAttachment[]
			findByCommerceNotificationQueueEntryId_PrevAndNext(
				long commerceNotificationAttachmentId,
				long commerceNotificationQueueEntryId,
				OrderByComparator<CommerceNotificationAttachment>
					orderByComparator)
		throws NoSuchNotificationAttachmentException {

		CommerceNotificationAttachment commerceNotificationAttachment =
			findByPrimaryKey(commerceNotificationAttachmentId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationAttachment[] array =
				new CommerceNotificationAttachmentImpl[3];

			array[0] = getByCommerceNotificationQueueEntryId_PrevAndNext(
				session, commerceNotificationAttachment,
				commerceNotificationQueueEntryId, orderByComparator, true);

			array[1] = commerceNotificationAttachment;

			array[2] = getByCommerceNotificationQueueEntryId_PrevAndNext(
				session, commerceNotificationAttachment,
				commerceNotificationQueueEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceNotificationAttachment
		getByCommerceNotificationQueueEntryId_PrevAndNext(
			Session session,
			CommerceNotificationAttachment commerceNotificationAttachment,
			long commerceNotificationQueueEntryId,
			OrderByComparator<CommerceNotificationAttachment> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCENOTIFICATIONQUEUEENTRYID_COMMERCENOTIFICATIONQUEUEENTRYID_2);

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
			sb.append(CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceNotificationQueueEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceNotificationAttachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceNotificationAttachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification attachments where commerceNotificationQueueEntryId = &#63; from the database.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 */
	@Override
	public void removeByCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId) {

		for (CommerceNotificationAttachment commerceNotificationAttachment :
				findByCommerceNotificationQueueEntryId(
					commerceNotificationQueueEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceNotificationAttachment);
		}
	}

	/**
	 * Returns the number of commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @return the number of matching commerce notification attachments
	 */
	@Override
	public int countByCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId) {

		FinderPath finderPath =
			_finderPathCountByCommerceNotificationQueueEntryId;

		Object[] finderArgs = new Object[] {commerceNotificationQueueEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCENOTIFICATIONQUEUEENTRYID_COMMERCENOTIFICATIONQUEUEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceNotificationQueueEntryId);

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

	private static final String
		_FINDER_COLUMN_COMMERCENOTIFICATIONQUEUEENTRYID_COMMERCENOTIFICATIONQUEUEENTRYID_2 =
			"commerceNotificationAttachment.commerceNotificationQueueEntryId = ?";

	public CommerceNotificationAttachmentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"commerceNotificationAttachmentId", "CNotificationAttachmentId");
		dbColumnNames.put(
			"commerceNotificationQueueEntryId", "CNotificationQueueEntryId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceNotificationAttachment.class);

		setModelImplClass(CommerceNotificationAttachmentImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceNotificationAttachmentTable.INSTANCE);
	}

	/**
	 * Caches the commerce notification attachment in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationAttachment the commerce notification attachment
	 */
	@Override
	public void cacheResult(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		entityCache.putResult(
			CommerceNotificationAttachmentImpl.class,
			commerceNotificationAttachment.getPrimaryKey(),
			commerceNotificationAttachment);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				commerceNotificationAttachment.getUuid(),
				commerceNotificationAttachment.getGroupId()
			},
			commerceNotificationAttachment);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce notification attachments in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationAttachments the commerce notification attachments
	 */
	@Override
	public void cacheResult(
		List<CommerceNotificationAttachment> commerceNotificationAttachments) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceNotificationAttachments.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceNotificationAttachment commerceNotificationAttachment :
				commerceNotificationAttachments) {

			if (entityCache.getResult(
					CommerceNotificationAttachmentImpl.class,
					commerceNotificationAttachment.getPrimaryKey()) == null) {

				cacheResult(commerceNotificationAttachment);
			}
		}
	}

	/**
	 * Clears the cache for all commerce notification attachments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceNotificationAttachmentImpl.class);

		finderCache.clearCache(CommerceNotificationAttachmentImpl.class);
	}

	/**
	 * Clears the cache for the commerce notification attachment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		entityCache.removeResult(
			CommerceNotificationAttachmentImpl.class,
			commerceNotificationAttachment);
	}

	@Override
	public void clearCache(
		List<CommerceNotificationAttachment> commerceNotificationAttachments) {

		for (CommerceNotificationAttachment commerceNotificationAttachment :
				commerceNotificationAttachments) {

			entityCache.removeResult(
				CommerceNotificationAttachmentImpl.class,
				commerceNotificationAttachment);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceNotificationAttachmentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceNotificationAttachmentImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceNotificationAttachmentModelImpl
			commerceNotificationAttachmentModelImpl) {

		Object[] args = new Object[] {
			commerceNotificationAttachmentModelImpl.getUuid(),
			commerceNotificationAttachmentModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			commerceNotificationAttachmentModelImpl);
	}

	/**
	 * Creates a new commerce notification attachment with the primary key. Does not add the commerce notification attachment to the database.
	 *
	 * @param commerceNotificationAttachmentId the primary key for the new commerce notification attachment
	 * @return the new commerce notification attachment
	 */
	@Override
	public CommerceNotificationAttachment create(
		long commerceNotificationAttachmentId) {

		CommerceNotificationAttachment commerceNotificationAttachment =
			new CommerceNotificationAttachmentImpl();

		commerceNotificationAttachment.setNew(true);
		commerceNotificationAttachment.setPrimaryKey(
			commerceNotificationAttachmentId);

		String uuid = PortalUUIDUtil.generate();

		commerceNotificationAttachment.setUuid(uuid);

		commerceNotificationAttachment.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceNotificationAttachment;
	}

	/**
	 * Removes the commerce notification attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the commerce notification attachment
	 * @return the commerce notification attachment that was removed
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	@Override
	public CommerceNotificationAttachment remove(
			long commerceNotificationAttachmentId)
		throws NoSuchNotificationAttachmentException {

		return remove((Serializable)commerceNotificationAttachmentId);
	}

	/**
	 * Removes the commerce notification attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce notification attachment
	 * @return the commerce notification attachment that was removed
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	@Override
	public CommerceNotificationAttachment remove(Serializable primaryKey)
		throws NoSuchNotificationAttachmentException {

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationAttachment commerceNotificationAttachment =
				(CommerceNotificationAttachment)session.get(
					CommerceNotificationAttachmentImpl.class, primaryKey);

			if (commerceNotificationAttachment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationAttachmentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceNotificationAttachment);
		}
		catch (NoSuchNotificationAttachmentException noSuchEntityException) {
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
	protected CommerceNotificationAttachment removeImpl(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceNotificationAttachment)) {
				commerceNotificationAttachment =
					(CommerceNotificationAttachment)session.get(
						CommerceNotificationAttachmentImpl.class,
						commerceNotificationAttachment.getPrimaryKeyObj());
			}

			if (commerceNotificationAttachment != null) {
				session.delete(commerceNotificationAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceNotificationAttachment != null) {
			clearCache(commerceNotificationAttachment);
		}

		return commerceNotificationAttachment;
	}

	@Override
	public CommerceNotificationAttachment updateImpl(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		boolean isNew = commerceNotificationAttachment.isNew();

		if (!(commerceNotificationAttachment instanceof
				CommerceNotificationAttachmentModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceNotificationAttachment.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceNotificationAttachment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceNotificationAttachment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceNotificationAttachment implementation " +
					commerceNotificationAttachment.getClass());
		}

		CommerceNotificationAttachmentModelImpl
			commerceNotificationAttachmentModelImpl =
				(CommerceNotificationAttachmentModelImpl)
					commerceNotificationAttachment;

		if (Validator.isNull(commerceNotificationAttachment.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceNotificationAttachment.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceNotificationAttachment.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceNotificationAttachment.setCreateDate(date);
			}
			else {
				commerceNotificationAttachment.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceNotificationAttachmentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceNotificationAttachment.setModifiedDate(date);
			}
			else {
				commerceNotificationAttachment.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceNotificationAttachment);
			}
			else {
				commerceNotificationAttachment =
					(CommerceNotificationAttachment)session.merge(
						commerceNotificationAttachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceNotificationAttachmentImpl.class,
			commerceNotificationAttachmentModelImpl, false, true);

		cacheUniqueFindersCache(commerceNotificationAttachmentModelImpl);

		if (isNew) {
			commerceNotificationAttachment.setNew(false);
		}

		commerceNotificationAttachment.resetOriginalValues();

		return commerceNotificationAttachment;
	}

	/**
	 * Returns the commerce notification attachment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification attachment
	 * @return the commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	@Override
	public CommerceNotificationAttachment findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationAttachmentException {

		CommerceNotificationAttachment commerceNotificationAttachment =
			fetchByPrimaryKey(primaryKey);

		if (commerceNotificationAttachment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationAttachmentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceNotificationAttachment;
	}

	/**
	 * Returns the commerce notification attachment with the primary key or throws a <code>NoSuchNotificationAttachmentException</code> if it could not be found.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the commerce notification attachment
	 * @return the commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	@Override
	public CommerceNotificationAttachment findByPrimaryKey(
			long commerceNotificationAttachmentId)
		throws NoSuchNotificationAttachmentException {

		return findByPrimaryKey((Serializable)commerceNotificationAttachmentId);
	}

	/**
	 * Returns the commerce notification attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the commerce notification attachment
	 * @return the commerce notification attachment, or <code>null</code> if a commerce notification attachment with the primary key could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByPrimaryKey(
		long commerceNotificationAttachmentId) {

		return fetchByPrimaryKey(
			(Serializable)commerceNotificationAttachmentId);
	}

	/**
	 * Returns all the commerce notification attachments.
	 *
	 * @return the commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
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

		List<CommerceNotificationAttachment> list = null;

		if (useFinderCache) {
			list = (List<CommerceNotificationAttachment>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT;

				sql = sql.concat(
					CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceNotificationAttachment>)QueryUtil.list(
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
	 * Removes all the commerce notification attachments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceNotificationAttachment commerceNotificationAttachment :
				findAll()) {

			remove(commerceNotificationAttachment);
		}
	}

	/**
	 * Returns the number of commerce notification attachments.
	 *
	 * @return the number of commerce notification attachments
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
					_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT);

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
		return "CNotificationAttachmentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceNotificationAttachmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce notification attachment persistence.
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

		_finderPathWithPaginationFindByCommerceNotificationQueueEntryId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceNotificationQueueEntryId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"CNotificationQueueEntryId"}, true);

		_finderPathWithoutPaginationFindByCommerceNotificationQueueEntryId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceNotificationQueueEntryId",
				new String[] {Long.class.getName()},
				new String[] {"CNotificationQueueEntryId"}, true);

		_finderPathCountByCommerceNotificationQueueEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceNotificationQueueEntryId",
			new String[] {Long.class.getName()},
			new String[] {"CNotificationQueueEntryId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceNotificationAttachmentImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT =
		"SELECT commerceNotificationAttachment FROM CommerceNotificationAttachment commerceNotificationAttachment";

	private static final String
		_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE =
			"SELECT commerceNotificationAttachment FROM CommerceNotificationAttachment commerceNotificationAttachment WHERE ";

	private static final String _SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT =
		"SELECT COUNT(commerceNotificationAttachment) FROM CommerceNotificationAttachment commerceNotificationAttachment";

	private static final String
		_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT_WHERE =
			"SELECT COUNT(commerceNotificationAttachment) FROM CommerceNotificationAttachment commerceNotificationAttachment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceNotificationAttachment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceNotificationAttachment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceNotificationAttachment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceNotificationAttachmentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"uuid", "commerceNotificationAttachmentId",
			"commerceNotificationQueueEntryId"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}