/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.notification.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.exception.NoSuchNotificationAttachmentException;
import com.liferay.commerce.notification.model.CommerceNotificationAttachment;
import com.liferay.commerce.notification.model.impl.CommerceNotificationAttachmentImpl;
import com.liferay.commerce.notification.model.impl.CommerceNotificationAttachmentModelImpl;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationAttachmentPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * @see CommerceNotificationAttachmentPersistence
 * @see com.liferay.commerce.notification.service.persistence.CommerceNotificationAttachmentUtil
 * @generated
 */
@ProviderType
public class CommerceNotificationAttachmentPersistenceImpl
	extends BasePersistenceImpl<CommerceNotificationAttachment>
	implements CommerceNotificationAttachmentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceNotificationAttachmentUtil} to access the commerce notification attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceNotificationAttachmentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommerceNotificationAttachmentModelImpl.UUID_COLUMN_BITMASK |
			CommerceNotificationAttachmentModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid(String uuid,
		int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<CommerceNotificationAttachment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationAttachment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationAttachment commerceNotificationAttachment : list) {
					if (!Objects.equals(uuid,
								commerceNotificationAttachment.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<CommerceNotificationAttachment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationAttachment>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	public CommerceNotificationAttachment findByUuid_First(String uuid,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {
		CommerceNotificationAttachment commerceNotificationAttachment = fetchByUuid_First(uuid,
				orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchNotificationAttachmentException(msg.toString());
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByUuid_First(String uuid,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {
		List<CommerceNotificationAttachment> list = findByUuid(uuid, 0, 1,
				orderByComparator);

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
	public CommerceNotificationAttachment findByUuid_Last(String uuid,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {
		CommerceNotificationAttachment commerceNotificationAttachment = fetchByUuid_Last(uuid,
				orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchNotificationAttachmentException(msg.toString());
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationAttachment> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

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
		CommerceNotificationAttachment commerceNotificationAttachment = findByPrimaryKey(commerceNotificationAttachmentId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationAttachment[] array = new CommerceNotificationAttachmentImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					commerceNotificationAttachment, uuid, orderByComparator,
					true);

			array[1] = commerceNotificationAttachment;

			array[2] = getByUuid_PrevAndNext(session,
					commerceNotificationAttachment, uuid, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
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
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals("")) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationAttachment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationAttachment> list = q.list();

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
		for (CommerceNotificationAttachment commerceNotificationAttachment : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commerceNotificationAttachment.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commerceNotificationAttachment.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commerceNotificationAttachment.uuid IS NULL OR commerceNotificationAttachment.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceNotificationAttachmentModelImpl.UUID_COLUMN_BITMASK |
			CommerceNotificationAttachmentModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchNotificationAttachmentException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment findByUUID_G(String uuid, long groupId)
		throws NoSuchNotificationAttachmentException {
		CommerceNotificationAttachment commerceNotificationAttachment = fetchByUUID_G(uuid,
				groupId);

		if (commerceNotificationAttachment == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchNotificationAttachmentException(msg.toString());
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
	public CommerceNotificationAttachment fetchByUUID_G(String uuid,
		long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommerceNotificationAttachment) {
			CommerceNotificationAttachment commerceNotificationAttachment = (CommerceNotificationAttachment)result;

			if (!Objects.equals(uuid, commerceNotificationAttachment.getUuid()) ||
					(groupId != commerceNotificationAttachment.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<CommerceNotificationAttachment> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommerceNotificationAttachment commerceNotificationAttachment =
						list.get(0);

					result = commerceNotificationAttachment;

					cacheResult(commerceNotificationAttachment);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

				throw processException(e);
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
	public CommerceNotificationAttachment removeByUUID_G(String uuid,
		long groupId) throws NoSuchNotificationAttachmentException {
		CommerceNotificationAttachment commerceNotificationAttachment = findByUUID_G(uuid,
				groupId);

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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commerceNotificationAttachment.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commerceNotificationAttachment.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commerceNotificationAttachment.uuid IS NULL OR commerceNotificationAttachment.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commerceNotificationAttachment.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceNotificationAttachmentModelImpl.UUID_COLUMN_BITMASK |
			CommerceNotificationAttachmentModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceNotificationAttachmentModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<CommerceNotificationAttachment> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceNotificationAttachment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationAttachment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationAttachment commerceNotificationAttachment : list) {
					if (!Objects.equals(uuid,
								commerceNotificationAttachment.getUuid()) ||
							(companyId != commerceNotificationAttachment.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceNotificationAttachment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationAttachment>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	public CommerceNotificationAttachment findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {
		CommerceNotificationAttachment commerceNotificationAttachment = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchNotificationAttachmentException(msg.toString());
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
	public CommerceNotificationAttachment fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {
		List<CommerceNotificationAttachment> list = findByUuid_C(uuid,
				companyId, 0, 1, orderByComparator);

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
	public CommerceNotificationAttachment findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {
		CommerceNotificationAttachment commerceNotificationAttachment = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchNotificationAttachmentException(msg.toString());
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
	public CommerceNotificationAttachment fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationAttachment> list = findByUuid_C(uuid,
				companyId, count - 1, count, orderByComparator);

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
		CommerceNotificationAttachment commerceNotificationAttachment = findByPrimaryKey(commerceNotificationAttachmentId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationAttachment[] array = new CommerceNotificationAttachmentImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					commerceNotificationAttachment, uuid, companyId,
					orderByComparator, true);

			array[1] = commerceNotificationAttachment;

			array[2] = getByUuid_C_PrevAndNext(session,
					commerceNotificationAttachment, uuid, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
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
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals("")) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationAttachment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationAttachment> list = q.list();

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
		for (CommerceNotificationAttachment commerceNotificationAttachment : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commerceNotificationAttachment.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commerceNotificationAttachment.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commerceNotificationAttachment.uuid IS NULL OR commerceNotificationAttachment.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commerceNotificationAttachment.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCENOTIFICATIONQUEUEENTRYID =
		new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceNotificationQueueEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONQUEUEENTRYID =
		new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceNotificationQueueEntryId",
			new String[] { Long.class.getName() },
			CommerceNotificationAttachmentModelImpl.COMMERCENOTIFICATIONQUEUEENTRYID_COLUMN_BITMASK |
			CommerceNotificationAttachmentModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONQUEUEENTRYID =
		new FinderPath(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceNotificationQueueEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @return the matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId) {
		return findByCommerceNotificationQueueEntryId(commerceNotificationQueueEntryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId, int start, int end) {
		return findByCommerceNotificationQueueEntryId(commerceNotificationQueueEntryId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {
		return findByCommerceNotificationQueueEntryId(commerceNotificationQueueEntryId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findByCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId, int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONQUEUEENTRYID;
			finderArgs = new Object[] { commerceNotificationQueueEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCENOTIFICATIONQUEUEENTRYID;
			finderArgs = new Object[] {
					commerceNotificationQueueEntryId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceNotificationAttachment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationAttachment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationAttachment commerceNotificationAttachment : list) {
					if ((commerceNotificationQueueEntryId != commerceNotificationAttachment.getCommerceNotificationQueueEntryId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

			query.append(_FINDER_COLUMN_COMMERCENOTIFICATIONQUEUEENTRYID_COMMERCENOTIFICATIONQUEUEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceNotificationQueueEntryId);

				if (!pagination) {
					list = (List<CommerceNotificationAttachment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationAttachment>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	public CommerceNotificationAttachment findByCommerceNotificationQueueEntryId_First(
		long commerceNotificationQueueEntryId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {
		CommerceNotificationAttachment commerceNotificationAttachment = fetchByCommerceNotificationQueueEntryId_First(commerceNotificationQueueEntryId,
				orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceNotificationQueueEntryId=");
		msg.append(commerceNotificationQueueEntryId);

		msg.append("}");

		throw new NoSuchNotificationAttachmentException(msg.toString());
	}

	/**
	 * Returns the first commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByCommerceNotificationQueueEntryId_First(
		long commerceNotificationQueueEntryId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {
		List<CommerceNotificationAttachment> list = findByCommerceNotificationQueueEntryId(commerceNotificationQueueEntryId,
				0, 1, orderByComparator);

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
	public CommerceNotificationAttachment findByCommerceNotificationQueueEntryId_Last(
		long commerceNotificationQueueEntryId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {
		CommerceNotificationAttachment commerceNotificationAttachment = fetchByCommerceNotificationQueueEntryId_Last(commerceNotificationQueueEntryId,
				orderByComparator);

		if (commerceNotificationAttachment != null) {
			return commerceNotificationAttachment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceNotificationQueueEntryId=");
		msg.append(commerceNotificationQueueEntryId);

		msg.append("}");

		throw new NoSuchNotificationAttachmentException(msg.toString());
	}

	/**
	 * Returns the last commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByCommerceNotificationQueueEntryId_Last(
		long commerceNotificationQueueEntryId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {
		int count = countByCommerceNotificationQueueEntryId(commerceNotificationQueueEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationAttachment> list = findByCommerceNotificationQueueEntryId(commerceNotificationQueueEntryId,
				count - 1, count, orderByComparator);

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
	public CommerceNotificationAttachment[] findByCommerceNotificationQueueEntryId_PrevAndNext(
		long commerceNotificationAttachmentId,
		long commerceNotificationQueueEntryId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException {
		CommerceNotificationAttachment commerceNotificationAttachment = findByPrimaryKey(commerceNotificationAttachmentId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationAttachment[] array = new CommerceNotificationAttachmentImpl[3];

			array[0] = getByCommerceNotificationQueueEntryId_PrevAndNext(session,
					commerceNotificationAttachment,
					commerceNotificationQueueEntryId, orderByComparator, true);

			array[1] = commerceNotificationAttachment;

			array[2] = getByCommerceNotificationQueueEntryId_PrevAndNext(session,
					commerceNotificationAttachment,
					commerceNotificationQueueEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceNotificationAttachment getByCommerceNotificationQueueEntryId_PrevAndNext(
		Session session,
		CommerceNotificationAttachment commerceNotificationAttachment,
		long commerceNotificationQueueEntryId,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

		query.append(_FINDER_COLUMN_COMMERCENOTIFICATIONQUEUEENTRYID_COMMERCENOTIFICATIONQUEUEENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceNotificationQueueEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationAttachment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationAttachment> list = q.list();

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
		for (CommerceNotificationAttachment commerceNotificationAttachment : findByCommerceNotificationQueueEntryId(
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONQUEUEENTRYID;

		Object[] finderArgs = new Object[] { commerceNotificationQueueEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT_WHERE);

			query.append(_FINDER_COLUMN_COMMERCENOTIFICATIONQUEUEENTRYID_COMMERCENOTIFICATIONQUEUEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceNotificationQueueEntryId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMMERCENOTIFICATIONQUEUEENTRYID_COMMERCENOTIFICATIONQUEUEENTRYID_2 =
		"commerceNotificationAttachment.commerceNotificationQueueEntryId = ?";

	public CommerceNotificationAttachmentPersistenceImpl() {
		setModelClass(CommerceNotificationAttachment.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("commerceNotificationAttachmentId",
				"CNotificationAttachmentId");
			dbColumnNames.put("commerceNotificationQueueEntryId",
				"CNotificationQueueEntryId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce notification attachment in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationAttachment the commerce notification attachment
	 */
	@Override
	public void cacheResult(
		CommerceNotificationAttachment commerceNotificationAttachment) {
		entityCache.putResult(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			commerceNotificationAttachment.getPrimaryKey(),
			commerceNotificationAttachment);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commerceNotificationAttachment.getUuid(),
				commerceNotificationAttachment.getGroupId()
			}, commerceNotificationAttachment);

		commerceNotificationAttachment.resetOriginalValues();
	}

	/**
	 * Caches the commerce notification attachments in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationAttachments the commerce notification attachments
	 */
	@Override
	public void cacheResult(
		List<CommerceNotificationAttachment> commerceNotificationAttachments) {
		for (CommerceNotificationAttachment commerceNotificationAttachment : commerceNotificationAttachments) {
			if (entityCache.getResult(
						CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
						CommerceNotificationAttachmentImpl.class,
						commerceNotificationAttachment.getPrimaryKey()) == null) {
				cacheResult(commerceNotificationAttachment);
			}
			else {
				commerceNotificationAttachment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce notification attachments.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceNotificationAttachmentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce notification attachment.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceNotificationAttachment commerceNotificationAttachment) {
		entityCache.removeResult(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			commerceNotificationAttachment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceNotificationAttachmentModelImpl)commerceNotificationAttachment,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceNotificationAttachment> commerceNotificationAttachments) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceNotificationAttachment commerceNotificationAttachment : commerceNotificationAttachments) {
			entityCache.removeResult(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				CommerceNotificationAttachmentImpl.class,
				commerceNotificationAttachment.getPrimaryKey());

			clearUniqueFindersCache((CommerceNotificationAttachmentModelImpl)commerceNotificationAttachment,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceNotificationAttachmentModelImpl commerceNotificationAttachmentModelImpl) {
		Object[] args = new Object[] {
				commerceNotificationAttachmentModelImpl.getUuid(),
				commerceNotificationAttachmentModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commerceNotificationAttachmentModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceNotificationAttachmentModelImpl commerceNotificationAttachmentModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceNotificationAttachmentModelImpl.getUuid(),
					commerceNotificationAttachmentModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commerceNotificationAttachmentModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceNotificationAttachmentModelImpl.getOriginalUuid(),
					commerceNotificationAttachmentModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
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
		CommerceNotificationAttachment commerceNotificationAttachment = new CommerceNotificationAttachmentImpl();

		commerceNotificationAttachment.setNew(true);
		commerceNotificationAttachment.setPrimaryKey(commerceNotificationAttachmentId);

		String uuid = PortalUUIDUtil.generate();

		commerceNotificationAttachment.setUuid(uuid);

		commerceNotificationAttachment.setCompanyId(companyProvider.getCompanyId());

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

			CommerceNotificationAttachment commerceNotificationAttachment = (CommerceNotificationAttachment)session.get(CommerceNotificationAttachmentImpl.class,
					primaryKey);

			if (commerceNotificationAttachment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationAttachmentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceNotificationAttachment);
		}
		catch (NoSuchNotificationAttachmentException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
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
				commerceNotificationAttachment = (CommerceNotificationAttachment)session.get(CommerceNotificationAttachmentImpl.class,
						commerceNotificationAttachment.getPrimaryKeyObj());
			}

			if (commerceNotificationAttachment != null) {
				session.delete(commerceNotificationAttachment);
			}
		}
		catch (Exception e) {
			throw processException(e);
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

		if (!(commerceNotificationAttachment instanceof CommerceNotificationAttachmentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
						commerceNotificationAttachment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceNotificationAttachment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceNotificationAttachment proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceNotificationAttachment implementation " +
				commerceNotificationAttachment.getClass());
		}

		CommerceNotificationAttachmentModelImpl commerceNotificationAttachmentModelImpl =
			(CommerceNotificationAttachmentModelImpl)commerceNotificationAttachment;

		if (Validator.isNull(commerceNotificationAttachment.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceNotificationAttachment.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceNotificationAttachment.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceNotificationAttachment.setCreateDate(now);
			}
			else {
				commerceNotificationAttachment.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceNotificationAttachmentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceNotificationAttachment.setModifiedDate(now);
			}
			else {
				commerceNotificationAttachment.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceNotificationAttachment.isNew()) {
				session.save(commerceNotificationAttachment);

				commerceNotificationAttachment.setNew(false);
			}
			else {
				commerceNotificationAttachment = (CommerceNotificationAttachment)session.merge(commerceNotificationAttachment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceNotificationAttachmentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceNotificationAttachmentModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commerceNotificationAttachmentModelImpl.getUuid(),
					commerceNotificationAttachmentModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					commerceNotificationAttachmentModelImpl.getCommerceNotificationQueueEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONQUEUEENTRYID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONQUEUEENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceNotificationAttachmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationAttachmentModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] {
						commerceNotificationAttachmentModelImpl.getUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commerceNotificationAttachmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationAttachmentModelImpl.getOriginalUuid(),
						commerceNotificationAttachmentModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commerceNotificationAttachmentModelImpl.getUuid(),
						commerceNotificationAttachmentModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commerceNotificationAttachmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONQUEUEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationAttachmentModelImpl.getOriginalCommerceNotificationQueueEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONQUEUEENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONQUEUEENTRYID,
					args);

				args = new Object[] {
						commerceNotificationAttachmentModelImpl.getCommerceNotificationQueueEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONQUEUEENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONQUEUEENTRYID,
					args);
			}
		}

		entityCache.putResult(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationAttachmentImpl.class,
			commerceNotificationAttachment.getPrimaryKey(),
			commerceNotificationAttachment, false);

		clearUniqueFindersCache(commerceNotificationAttachmentModelImpl, false);
		cacheUniqueFindersCache(commerceNotificationAttachmentModelImpl);

		commerceNotificationAttachment.resetOriginalValues();

		return commerceNotificationAttachment;
	}

	/**
	 * Returns the commerce notification attachment with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification attachment
	 * @return the commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	@Override
	public CommerceNotificationAttachment findByPrimaryKey(
		Serializable primaryKey) throws NoSuchNotificationAttachmentException {
		CommerceNotificationAttachment commerceNotificationAttachment = fetchByPrimaryKey(primaryKey);

		if (commerceNotificationAttachment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationAttachmentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceNotificationAttachment;
	}

	/**
	 * Returns the commerce notification attachment with the primary key or throws a {@link NoSuchNotificationAttachmentException} if it could not be found.
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
	 * @param primaryKey the primary key of the commerce notification attachment
	 * @return the commerce notification attachment, or <code>null</code> if a commerce notification attachment with the primary key could not be found
	 */
	@Override
	public CommerceNotificationAttachment fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
				CommerceNotificationAttachmentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceNotificationAttachment commerceNotificationAttachment = (CommerceNotificationAttachment)serializable;

		if (commerceNotificationAttachment == null) {
			Session session = null;

			try {
				session = openSession();

				commerceNotificationAttachment = (CommerceNotificationAttachment)session.get(CommerceNotificationAttachmentImpl.class,
						primaryKey);

				if (commerceNotificationAttachment != null) {
					cacheResult(commerceNotificationAttachment);
				}
				else {
					entityCache.putResult(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
						CommerceNotificationAttachmentImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationAttachmentImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceNotificationAttachment;
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
		return fetchByPrimaryKey((Serializable)commerceNotificationAttachmentId);
	}

	@Override
	public Map<Serializable, CommerceNotificationAttachment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceNotificationAttachment> map = new HashMap<Serializable, CommerceNotificationAttachment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceNotificationAttachment commerceNotificationAttachment = fetchByPrimaryKey(primaryKey);

			if (commerceNotificationAttachment != null) {
				map.put(primaryKey, commerceNotificationAttachment);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationAttachmentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceNotificationAttachment)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CommerceNotificationAttachment commerceNotificationAttachment : (List<CommerceNotificationAttachment>)q.list()) {
				map.put(commerceNotificationAttachment.getPrimaryKeyObj(),
					commerceNotificationAttachment);

				cacheResult(commerceNotificationAttachment);

				uncachedPrimaryKeys.remove(commerceNotificationAttachment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceNotificationAttachmentModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationAttachmentImpl.class, primaryKey,
					nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findAll(int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationAttachmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce notification attachments
	 */
	@Override
	public List<CommerceNotificationAttachment> findAll(int start, int end,
		OrderByComparator<CommerceNotificationAttachment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<CommerceNotificationAttachment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationAttachment>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT;

				if (pagination) {
					sql = sql.concat(CommerceNotificationAttachmentModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceNotificationAttachment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationAttachment>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
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
		for (CommerceNotificationAttachment commerceNotificationAttachment : findAll()) {
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
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
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceNotificationAttachmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce notification attachment persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceNotificationAttachmentImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT = "SELECT commerceNotificationAttachment FROM CommerceNotificationAttachment commerceNotificationAttachment";
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE_PKS_IN =
		"SELECT commerceNotificationAttachment FROM CommerceNotificationAttachment commerceNotificationAttachment WHERE CNotificationAttachmentId IN (";
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONATTACHMENT_WHERE =
		"SELECT commerceNotificationAttachment FROM CommerceNotificationAttachment commerceNotificationAttachment WHERE ";
	private static final String _SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT = "SELECT COUNT(commerceNotificationAttachment) FROM CommerceNotificationAttachment commerceNotificationAttachment";
	private static final String _SQL_COUNT_COMMERCENOTIFICATIONATTACHMENT_WHERE = "SELECT COUNT(commerceNotificationAttachment) FROM CommerceNotificationAttachment commerceNotificationAttachment WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceNotificationAttachment.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceNotificationAttachment exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceNotificationAttachment exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceNotificationAttachmentPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "commerceNotificationAttachmentId",
				"commerceNotificationQueueEntryId"
			});
}