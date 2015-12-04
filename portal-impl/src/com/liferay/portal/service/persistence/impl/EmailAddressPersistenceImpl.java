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

package com.liferay.portal.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchEmailAddressException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.impl.EmailAddressImpl;
import com.liferay.portal.model.impl.EmailAddressModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.EmailAddressPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the email address service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddressPersistence
 * @see com.liferay.portal.service.persistence.EmailAddressUtil
 * @generated
 */
@ProviderType
public class EmailAddressPersistenceImpl extends BasePersistenceImpl<EmailAddress>
	implements EmailAddressPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link EmailAddressUtil} to access the email address persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = EmailAddressImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			EmailAddressModelImpl.UUID_COLUMN_BITMASK |
			EmailAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the email addresses where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the email addresses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @return the range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the email addresses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUuid(String uuid, int start, int end,
		OrderByComparator<EmailAddress> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the email addresses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUuid(String uuid, int start, int end,
		OrderByComparator<EmailAddress> orderByComparator,
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

		List<EmailAddress> list = null;

		if (retrieveFromCache) {
			list = (List<EmailAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (EmailAddress emailAddress : list) {
					if (!Validator.equals(uuid, emailAddress.getUuid())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
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
				query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
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
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first email address in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByUuid_First(String uuid,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByUuid_First(uuid, orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the first email address in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByUuid_First(String uuid,
		OrderByComparator<EmailAddress> orderByComparator) {
		List<EmailAddress> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last email address in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByUuid_Last(String uuid,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByUuid_Last(uuid, orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the last email address in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByUuid_Last(String uuid,
		OrderByComparator<EmailAddress> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<EmailAddress> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the email addresses before and after the current email address in the ordered set where uuid = &#63;.
	 *
	 * @param emailAddressId the primary key of the current email address
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next email address
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress[] findByUuid_PrevAndNext(long emailAddressId,
		String uuid, OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);

		Session session = null;

		try {
			session = openSession();

			EmailAddress[] array = new EmailAddressImpl[3];

			array[0] = getByUuid_PrevAndNext(session, emailAddress, uuid,
					orderByComparator, true);

			array[1] = emailAddress;

			array[2] = getByUuid_PrevAndNext(session, emailAddress, uuid,
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

	protected EmailAddress getByUuid_PrevAndNext(Session session,
		EmailAddress emailAddress, String uuid,
		OrderByComparator<EmailAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
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
			query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(emailAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<EmailAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the email addresses where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (EmailAddress emailAddress : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(emailAddress);
		}
	}

	/**
	 * Returns the number of email addresses where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching email addresses
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EMAILADDRESS_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "emailAddress.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "emailAddress.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(emailAddress.uuid IS NULL OR emailAddress.uuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			EmailAddressModelImpl.UUID_COLUMN_BITMASK |
			EmailAddressModelImpl.COMPANYID_COLUMN_BITMASK |
			EmailAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the email addresses where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the email addresses where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @return the range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the email addresses where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<EmailAddress> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the email addresses where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<EmailAddress> orderByComparator,
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

		List<EmailAddress> list = null;

		if (retrieveFromCache) {
			list = (List<EmailAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (EmailAddress emailAddress : list) {
					if (!Validator.equals(uuid, emailAddress.getUuid()) ||
							(companyId != emailAddress.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
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
				query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
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
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first email address in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the first email address in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<EmailAddress> orderByComparator) {
		List<EmailAddress> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last email address in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the last email address in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<EmailAddress> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<EmailAddress> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the email addresses before and after the current email address in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param emailAddressId the primary key of the current email address
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next email address
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress[] findByUuid_C_PrevAndNext(long emailAddressId,
		String uuid, long companyId,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);

		Session session = null;

		try {
			session = openSession();

			EmailAddress[] array = new EmailAddressImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, emailAddress, uuid,
					companyId, orderByComparator, true);

			array[1] = emailAddress;

			array[2] = getByUuid_C_PrevAndNext(session, emailAddress, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected EmailAddress getByUuid_C_PrevAndNext(Session session,
		EmailAddress emailAddress, String uuid, long companyId,
		OrderByComparator<EmailAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
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
			query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(emailAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<EmailAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the email addresses where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (EmailAddress emailAddress : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(emailAddress);
		}
	}

	/**
	 * Returns the number of email addresses where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching email addresses
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EMAILADDRESS_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "emailAddress.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "emailAddress.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(emailAddress.uuid IS NULL OR emailAddress.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "emailAddress.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			EmailAddressModelImpl.COMPANYID_COLUMN_BITMASK |
			EmailAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the email addresses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching email addresses
	 */
	@Override
	public List<EmailAddress> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the email addresses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @return the range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the email addresses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<EmailAddress> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the email addresses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<EmailAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<EmailAddress> list = null;

		if (retrieveFromCache) {
			list = (List<EmailAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (EmailAddress emailAddress : list) {
					if ((companyId != emailAddress.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first email address in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByCompanyId_First(long companyId,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the first email address in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByCompanyId_First(long companyId,
		OrderByComparator<EmailAddress> orderByComparator) {
		List<EmailAddress> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last email address in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByCompanyId_Last(long companyId,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the last email address in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByCompanyId_Last(long companyId,
		OrderByComparator<EmailAddress> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<EmailAddress> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the email addresses before and after the current email address in the ordered set where companyId = &#63;.
	 *
	 * @param emailAddressId the primary key of the current email address
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next email address
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress[] findByCompanyId_PrevAndNext(long emailAddressId,
		long companyId, OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);

		Session session = null;

		try {
			session = openSession();

			EmailAddress[] array = new EmailAddressImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, emailAddress,
					companyId, orderByComparator, true);

			array[1] = emailAddress;

			array[2] = getByCompanyId_PrevAndNext(session, emailAddress,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected EmailAddress getByCompanyId_PrevAndNext(Session session,
		EmailAddress emailAddress, long companyId,
		OrderByComparator<EmailAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(emailAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<EmailAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the email addresses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (EmailAddress emailAddress : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(emailAddress);
		}
	}

	/**
	 * Returns the number of email addresses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching email addresses
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EMAILADDRESS_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "emailAddress.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			EmailAddressModelImpl.USERID_COLUMN_BITMASK |
			EmailAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the email addresses where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the email addresses where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @return the range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the email addresses where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUserId(long userId, int start, int end,
		OrderByComparator<EmailAddress> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the email addresses where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByUserId(long userId, int start, int end,
		OrderByComparator<EmailAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<EmailAddress> list = null;

		if (retrieveFromCache) {
			list = (List<EmailAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (EmailAddress emailAddress : list) {
					if ((userId != emailAddress.getUserId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first email address in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByUserId_First(long userId,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByUserId_First(userId,
				orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the first email address in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByUserId_First(long userId,
		OrderByComparator<EmailAddress> orderByComparator) {
		List<EmailAddress> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last email address in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByUserId_Last(long userId,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByUserId_Last(userId, orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the last email address in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByUserId_Last(long userId,
		OrderByComparator<EmailAddress> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<EmailAddress> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the email addresses before and after the current email address in the ordered set where userId = &#63;.
	 *
	 * @param emailAddressId the primary key of the current email address
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next email address
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress[] findByUserId_PrevAndNext(long emailAddressId,
		long userId, OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);

		Session session = null;

		try {
			session = openSession();

			EmailAddress[] array = new EmailAddressImpl[3];

			array[0] = getByUserId_PrevAndNext(session, emailAddress, userId,
					orderByComparator, true);

			array[1] = emailAddress;

			array[2] = getByUserId_PrevAndNext(session, emailAddress, userId,
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

	protected EmailAddress getByUserId_PrevAndNext(Session session,
		EmailAddress emailAddress, long userId,
		OrderByComparator<EmailAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(emailAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<EmailAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the email addresses where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (EmailAddress emailAddress : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(emailAddress);
		}
	}

	/**
	 * Returns the number of email addresses where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching email addresses
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EMAILADDRESS_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "emailAddress.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			EmailAddressModelImpl.COMPANYID_COLUMN_BITMASK |
			EmailAddressModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			EmailAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the email addresses where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C(long companyId, long classNameId) {
		return findByC_C(companyId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the email addresses where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @return the range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C(long companyId, long classNameId,
		int start, int end) {
		return findByC_C(companyId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the email addresses where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C(long companyId, long classNameId,
		int start, int end, OrderByComparator<EmailAddress> orderByComparator) {
		return findByC_C(companyId, classNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the email addresses where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C(long companyId, long classNameId,
		int start, int end, OrderByComparator<EmailAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { companyId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					companyId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<EmailAddress> list = null;

		if (retrieveFromCache) {
			list = (List<EmailAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (EmailAddress emailAddress : list) {
					if ((companyId != emailAddress.getCompanyId()) ||
							(classNameId != emailAddress.getClassNameId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first email address in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByC_C_First(long companyId, long classNameId,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByC_C_First(companyId, classNameId,
				orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the first email address in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByC_C_First(long companyId, long classNameId,
		OrderByComparator<EmailAddress> orderByComparator) {
		List<EmailAddress> list = findByC_C(companyId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last email address in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByC_C_Last(long companyId, long classNameId,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByC_C_Last(companyId, classNameId,
				orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the last email address in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByC_C_Last(long companyId, long classNameId,
		OrderByComparator<EmailAddress> orderByComparator) {
		int count = countByC_C(companyId, classNameId);

		if (count == 0) {
			return null;
		}

		List<EmailAddress> list = findByC_C(companyId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the email addresses before and after the current email address in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param emailAddressId the primary key of the current email address
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next email address
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress[] findByC_C_PrevAndNext(long emailAddressId,
		long companyId, long classNameId,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);

		Session session = null;

		try {
			session = openSession();

			EmailAddress[] array = new EmailAddressImpl[3];

			array[0] = getByC_C_PrevAndNext(session, emailAddress, companyId,
					classNameId, orderByComparator, true);

			array[1] = emailAddress;

			array[2] = getByC_C_PrevAndNext(session, emailAddress, companyId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected EmailAddress getByC_C_PrevAndNext(Session session,
		EmailAddress emailAddress, long companyId, long classNameId,
		OrderByComparator<EmailAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

		query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

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
			query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(emailAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<EmailAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the email addresses where companyId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByC_C(long companyId, long classNameId) {
		for (EmailAddress emailAddress : findByC_C(companyId, classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(emailAddress);
		}
	}

	/**
	 * Returns the number of email addresses where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the number of matching email addresses
	 */
	@Override
	public int countByC_C(long companyId, long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { companyId, classNameId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EMAILADDRESS_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 = "emailAddress.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "emailAddress.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_C = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			EmailAddressModelImpl.COMPANYID_COLUMN_BITMASK |
			EmailAddressModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			EmailAddressModelImpl.CLASSPK_COLUMN_BITMASK |
			EmailAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C_C(long companyId, long classNameId,
		long classPK) {
		return findByC_C_C(companyId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @return the range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end) {
		return findByC_C_C(companyId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<EmailAddress> orderByComparator) {
		return findByC_C_C(companyId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<EmailAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C;
			finderArgs = new Object[] { companyId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_C;
			finderArgs = new Object[] {
					companyId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<EmailAddress> list = null;

		if (retrieveFromCache) {
			list = (List<EmailAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (EmailAddress emailAddress : list) {
					if ((companyId != emailAddress.getCompanyId()) ||
							(classNameId != emailAddress.getClassNameId()) ||
							(classPK != emailAddress.getClassPK())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first email address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByC_C_C_First(long companyId, long classNameId,
		long classPK, OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByC_C_C_First(companyId, classNameId,
				classPK, orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the first email address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByC_C_C_First(long companyId, long classNameId,
		long classPK, OrderByComparator<EmailAddress> orderByComparator) {
		List<EmailAddress> list = findByC_C_C(companyId, classNameId, classPK,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last email address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByC_C_C_Last(long companyId, long classNameId,
		long classPK, OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByC_C_C_Last(companyId, classNameId,
				classPK, orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the last email address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByC_C_C_Last(long companyId, long classNameId,
		long classPK, OrderByComparator<EmailAddress> orderByComparator) {
		int count = countByC_C_C(companyId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<EmailAddress> list = findByC_C_C(companyId, classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the email addresses before and after the current email address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param emailAddressId the primary key of the current email address
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next email address
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress[] findByC_C_C_PrevAndNext(long emailAddressId,
		long companyId, long classNameId, long classPK,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);

		Session session = null;

		try {
			session = openSession();

			EmailAddress[] array = new EmailAddressImpl[3];

			array[0] = getByC_C_C_PrevAndNext(session, emailAddress, companyId,
					classNameId, classPK, orderByComparator, true);

			array[1] = emailAddress;

			array[2] = getByC_C_C_PrevAndNext(session, emailAddress, companyId,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected EmailAddress getByC_C_C_PrevAndNext(Session session,
		EmailAddress emailAddress, long companyId, long classNameId,
		long classPK, OrderByComparator<EmailAddress> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

		query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

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
			query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(emailAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<EmailAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 */
	@Override
	public void removeByC_C_C(long companyId, long classNameId, long classPK) {
		for (EmailAddress emailAddress : findByC_C_C(companyId, classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(emailAddress);
		}
	}

	/**
	 * Returns the number of email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching email addresses
	 */
	@Override
	public int countByC_C_C(long companyId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_C;

		Object[] finderArgs = new Object[] { companyId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_EMAILADDRESS_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_C_COMPANYID_2 = "emailAddress.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 = "emailAddress.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 = "emailAddress.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_C_P = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C_P =
		new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, EmailAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			EmailAddressModelImpl.COMPANYID_COLUMN_BITMASK |
			EmailAddressModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			EmailAddressModelImpl.CLASSPK_COLUMN_BITMASK |
			EmailAddressModelImpl.PRIMARY_COLUMN_BITMASK |
			EmailAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C_P = new FinderPath(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 * @return the matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary) {
		return findByC_C_C_P(companyId, classNameId, classPK, primary,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @return the range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary, int start, int end) {
		return findByC_C_C_P(companyId, classNameId, classPK, primary, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary, int start, int end,
		OrderByComparator<EmailAddress> orderByComparator) {
		return findByC_C_C_P(companyId, classNameId, classPK, primary, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching email addresses
	 */
	@Override
	public List<EmailAddress> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary, int start, int end,
		OrderByComparator<EmailAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C_P;
			finderArgs = new Object[] { companyId, classNameId, classPK, primary };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_C_P;
			finderArgs = new Object[] {
					companyId, classNameId, classPK, primary,
					
					start, end, orderByComparator
				};
		}

		List<EmailAddress> list = null;

		if (retrieveFromCache) {
			list = (List<EmailAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (EmailAddress emailAddress : list) {
					if ((companyId != emailAddress.getCompanyId()) ||
							(classNameId != emailAddress.getClassNameId()) ||
							(classPK != emailAddress.getClassPK()) ||
							(primary != emailAddress.getPrimary())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_P_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_C_P_PRIMARY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

				if (!pagination) {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first email address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByC_C_C_P_First(long companyId, long classNameId,
		long classPK, boolean primary,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByC_C_C_P_First(companyId,
				classNameId, classPK, primary, orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", primary=");
		msg.append(primary);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the first email address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByC_C_C_P_First(long companyId, long classNameId,
		long classPK, boolean primary,
		OrderByComparator<EmailAddress> orderByComparator) {
		List<EmailAddress> list = findByC_C_C_P(companyId, classNameId,
				classPK, primary, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last email address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address
	 * @throws NoSuchEmailAddressException if a matching email address could not be found
	 */
	@Override
	public EmailAddress findByC_C_C_P_Last(long companyId, long classNameId,
		long classPK, boolean primary,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByC_C_C_P_Last(companyId, classNameId,
				classPK, primary, orderByComparator);

		if (emailAddress != null) {
			return emailAddress;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", primary=");
		msg.append(primary);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEmailAddressException(msg.toString());
	}

	/**
	 * Returns the last email address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching email address, or <code>null</code> if a matching email address could not be found
	 */
	@Override
	public EmailAddress fetchByC_C_C_P_Last(long companyId, long classNameId,
		long classPK, boolean primary,
		OrderByComparator<EmailAddress> orderByComparator) {
		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);

		if (count == 0) {
			return null;
		}

		List<EmailAddress> list = findByC_C_C_P(companyId, classNameId,
				classPK, primary, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the email addresses before and after the current email address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	 *
	 * @param emailAddressId the primary key of the current email address
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next email address
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress[] findByC_C_C_P_PrevAndNext(long emailAddressId,
		long companyId, long classNameId, long classPK, boolean primary,
		OrderByComparator<EmailAddress> orderByComparator)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);

		Session session = null;

		try {
			session = openSession();

			EmailAddress[] array = new EmailAddressImpl[3];

			array[0] = getByC_C_C_P_PrevAndNext(session, emailAddress,
					companyId, classNameId, classPK, primary,
					orderByComparator, true);

			array[1] = emailAddress;

			array[2] = getByC_C_C_P_PrevAndNext(session, emailAddress,
					companyId, classNameId, classPK, primary,
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

	protected EmailAddress getByC_C_C_P_PrevAndNext(Session session,
		EmailAddress emailAddress, long companyId, long classNameId,
		long classPK, boolean primary,
		OrderByComparator<EmailAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EMAILADDRESS_WHERE);

		query.append(_FINDER_COLUMN_C_C_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_C_C_P_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_C_P_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_C_P_PRIMARY_2);

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
			query.append(EmailAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(primary);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(emailAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<EmailAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 */
	@Override
	public void removeByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) {
		for (EmailAddress emailAddress : findByC_C_C_P(companyId, classNameId,
				classPK, primary, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(emailAddress);
		}
	}

	/**
	 * Returns the number of email addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param primary the primary
	 * @return the number of matching email addresses
	 */
	@Override
	public int countByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_C_P;

		Object[] finderArgs = new Object[] {
				companyId, classNameId, classPK, primary
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_EMAILADDRESS_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_P_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_C_P_PRIMARY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

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

	private static final String _FINDER_COLUMN_C_C_C_P_COMPANYID_2 = "emailAddress.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_P_CLASSNAMEID_2 = "emailAddress.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_P_CLASSPK_2 = "emailAddress.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_P_PRIMARY_2 = "emailAddress.primary = ?";

	public EmailAddressPersistenceImpl() {
		setModelClass(EmailAddress.class);
	}

	/**
	 * Caches the email address in the entity cache if it is enabled.
	 *
	 * @param emailAddress the email address
	 */
	@Override
	public void cacheResult(EmailAddress emailAddress) {
		entityCache.putResult(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressImpl.class, emailAddress.getPrimaryKey(), emailAddress);

		emailAddress.resetOriginalValues();
	}

	/**
	 * Caches the email addresses in the entity cache if it is enabled.
	 *
	 * @param emailAddresses the email addresses
	 */
	@Override
	public void cacheResult(List<EmailAddress> emailAddresses) {
		for (EmailAddress emailAddress : emailAddresses) {
			if (entityCache.getResult(
						EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
						EmailAddressImpl.class, emailAddress.getPrimaryKey()) == null) {
				cacheResult(emailAddress);
			}
			else {
				emailAddress.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all email addresses.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(EmailAddressImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the email address.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(EmailAddress emailAddress) {
		entityCache.removeResult(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressImpl.class, emailAddress.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<EmailAddress> emailAddresses) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (EmailAddress emailAddress : emailAddresses) {
			entityCache.removeResult(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
				EmailAddressImpl.class, emailAddress.getPrimaryKey());
		}
	}

	/**
	 * Creates a new email address with the primary key. Does not add the email address to the database.
	 *
	 * @param emailAddressId the primary key for the new email address
	 * @return the new email address
	 */
	@Override
	public EmailAddress create(long emailAddressId) {
		EmailAddress emailAddress = new EmailAddressImpl();

		emailAddress.setNew(true);
		emailAddress.setPrimaryKey(emailAddressId);

		String uuid = PortalUUIDUtil.generate();

		emailAddress.setUuid(uuid);

		return emailAddress;
	}

	/**
	 * Removes the email address with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param emailAddressId the primary key of the email address
	 * @return the email address that was removed
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress remove(long emailAddressId)
		throws NoSuchEmailAddressException {
		return remove((Serializable)emailAddressId);
	}

	/**
	 * Removes the email address with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the email address
	 * @return the email address that was removed
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress remove(Serializable primaryKey)
		throws NoSuchEmailAddressException {
		Session session = null;

		try {
			session = openSession();

			EmailAddress emailAddress = (EmailAddress)session.get(EmailAddressImpl.class,
					primaryKey);

			if (emailAddress == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEmailAddressException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(emailAddress);
		}
		catch (NoSuchEmailAddressException nsee) {
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
	protected EmailAddress removeImpl(EmailAddress emailAddress) {
		emailAddress = toUnwrappedModel(emailAddress);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(emailAddress)) {
				emailAddress = (EmailAddress)session.get(EmailAddressImpl.class,
						emailAddress.getPrimaryKeyObj());
			}

			if (emailAddress != null) {
				session.delete(emailAddress);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (emailAddress != null) {
			clearCache(emailAddress);
		}

		return emailAddress;
	}

	@Override
	public EmailAddress updateImpl(EmailAddress emailAddress) {
		emailAddress = toUnwrappedModel(emailAddress);

		boolean isNew = emailAddress.isNew();

		EmailAddressModelImpl emailAddressModelImpl = (EmailAddressModelImpl)emailAddress;

		if (Validator.isNull(emailAddress.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			emailAddress.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (emailAddress.getCreateDate() == null)) {
			if (serviceContext == null) {
				emailAddress.setCreateDate(now);
			}
			else {
				emailAddress.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!emailAddressModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				emailAddress.setModifiedDate(now);
			}
			else {
				emailAddress.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (emailAddress.isNew()) {
				session.save(emailAddress);

				emailAddress.setNew(false);
			}
			else {
				emailAddress = (EmailAddress)session.merge(emailAddress);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !EmailAddressModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((emailAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						emailAddressModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { emailAddressModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((emailAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						emailAddressModelImpl.getOriginalUuid(),
						emailAddressModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						emailAddressModelImpl.getUuid(),
						emailAddressModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((emailAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						emailAddressModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { emailAddressModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((emailAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						emailAddressModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { emailAddressModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((emailAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						emailAddressModelImpl.getOriginalCompanyId(),
						emailAddressModelImpl.getOriginalClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						emailAddressModelImpl.getCompanyId(),
						emailAddressModelImpl.getClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((emailAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						emailAddressModelImpl.getOriginalCompanyId(),
						emailAddressModelImpl.getOriginalClassNameId(),
						emailAddressModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C,
					args);

				args = new Object[] {
						emailAddressModelImpl.getCompanyId(),
						emailAddressModelImpl.getClassNameId(),
						emailAddressModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C,
					args);
			}

			if ((emailAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						emailAddressModelImpl.getOriginalCompanyId(),
						emailAddressModelImpl.getOriginalClassNameId(),
						emailAddressModelImpl.getOriginalClassPK(),
						emailAddressModelImpl.getOriginalPrimary()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C_P,
					args);

				args = new Object[] {
						emailAddressModelImpl.getCompanyId(),
						emailAddressModelImpl.getClassNameId(),
						emailAddressModelImpl.getClassPK(),
						emailAddressModelImpl.getPrimary()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C_P,
					args);
			}
		}

		entityCache.putResult(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
			EmailAddressImpl.class, emailAddress.getPrimaryKey(), emailAddress,
			false);

		emailAddress.resetOriginalValues();

		return emailAddress;
	}

	protected EmailAddress toUnwrappedModel(EmailAddress emailAddress) {
		if (emailAddress instanceof EmailAddressImpl) {
			return emailAddress;
		}

		EmailAddressImpl emailAddressImpl = new EmailAddressImpl();

		emailAddressImpl.setNew(emailAddress.isNew());
		emailAddressImpl.setPrimaryKey(emailAddress.getPrimaryKey());

		emailAddressImpl.setMvccVersion(emailAddress.getMvccVersion());
		emailAddressImpl.setUuid(emailAddress.getUuid());
		emailAddressImpl.setEmailAddressId(emailAddress.getEmailAddressId());
		emailAddressImpl.setCompanyId(emailAddress.getCompanyId());
		emailAddressImpl.setUserId(emailAddress.getUserId());
		emailAddressImpl.setUserName(emailAddress.getUserName());
		emailAddressImpl.setCreateDate(emailAddress.getCreateDate());
		emailAddressImpl.setModifiedDate(emailAddress.getModifiedDate());
		emailAddressImpl.setClassNameId(emailAddress.getClassNameId());
		emailAddressImpl.setClassPK(emailAddress.getClassPK());
		emailAddressImpl.setAddress(emailAddress.getAddress());
		emailAddressImpl.setTypeId(emailAddress.getTypeId());
		emailAddressImpl.setPrimary(emailAddress.isPrimary());

		return emailAddressImpl;
	}

	/**
	 * Returns the email address with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the email address
	 * @return the email address
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEmailAddressException {
		EmailAddress emailAddress = fetchByPrimaryKey(primaryKey);

		if (emailAddress == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEmailAddressException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return emailAddress;
	}

	/**
	 * Returns the email address with the primary key or throws a {@link NoSuchEmailAddressException} if it could not be found.
	 *
	 * @param emailAddressId the primary key of the email address
	 * @return the email address
	 * @throws NoSuchEmailAddressException if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress findByPrimaryKey(long emailAddressId)
		throws NoSuchEmailAddressException {
		return findByPrimaryKey((Serializable)emailAddressId);
	}

	/**
	 * Returns the email address with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the email address
	 * @return the email address, or <code>null</code> if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress fetchByPrimaryKey(Serializable primaryKey) {
		EmailAddress emailAddress = (EmailAddress)entityCache.getResult(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
				EmailAddressImpl.class, primaryKey);

		if (emailAddress == _nullEmailAddress) {
			return null;
		}

		if (emailAddress == null) {
			Session session = null;

			try {
				session = openSession();

				emailAddress = (EmailAddress)session.get(EmailAddressImpl.class,
						primaryKey);

				if (emailAddress != null) {
					cacheResult(emailAddress);
				}
				else {
					entityCache.putResult(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
						EmailAddressImpl.class, primaryKey, _nullEmailAddress);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
					EmailAddressImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return emailAddress;
	}

	/**
	 * Returns the email address with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param emailAddressId the primary key of the email address
	 * @return the email address, or <code>null</code> if a email address with the primary key could not be found
	 */
	@Override
	public EmailAddress fetchByPrimaryKey(long emailAddressId) {
		return fetchByPrimaryKey((Serializable)emailAddressId);
	}

	@Override
	public Map<Serializable, EmailAddress> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, EmailAddress> map = new HashMap<Serializable, EmailAddress>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			EmailAddress emailAddress = fetchByPrimaryKey(primaryKey);

			if (emailAddress != null) {
				map.put(primaryKey, emailAddress);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			EmailAddress emailAddress = (EmailAddress)entityCache.getResult(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
					EmailAddressImpl.class, primaryKey);

			if (emailAddress == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, emailAddress);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_EMAILADDRESS_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (EmailAddress emailAddress : (List<EmailAddress>)q.list()) {
				map.put(emailAddress.getPrimaryKeyObj(), emailAddress);

				cacheResult(emailAddress);

				uncachedPrimaryKeys.remove(emailAddress.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(EmailAddressModelImpl.ENTITY_CACHE_ENABLED,
					EmailAddressImpl.class, primaryKey, _nullEmailAddress);
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
	 * Returns all the email addresses.
	 *
	 * @return the email addresses
	 */
	@Override
	public List<EmailAddress> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the email addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @return the range of email addresses
	 */
	@Override
	public List<EmailAddress> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the email addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of email addresses
	 */
	@Override
	public List<EmailAddress> findAll(int start, int end,
		OrderByComparator<EmailAddress> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the email addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of email addresses
	 */
	@Override
	public List<EmailAddress> findAll(int start, int end,
		OrderByComparator<EmailAddress> orderByComparator,
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

		List<EmailAddress> list = null;

		if (retrieveFromCache) {
			list = (List<EmailAddress>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_EMAILADDRESS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_EMAILADDRESS;

				if (pagination) {
					sql = sql.concat(EmailAddressModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<EmailAddress>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the email addresses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (EmailAddress emailAddress : findAll()) {
			remove(emailAddress);
		}
	}

	/**
	 * Returns the number of email addresses.
	 *
	 * @return the number of email addresses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_EMAILADDRESS);

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
		return EmailAddressModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the email address persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(EmailAddressImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_EMAILADDRESS = "SELECT emailAddress FROM EmailAddress emailAddress";
	private static final String _SQL_SELECT_EMAILADDRESS_WHERE_PKS_IN = "SELECT emailAddress FROM EmailAddress emailAddress WHERE emailAddressId IN (";
	private static final String _SQL_SELECT_EMAILADDRESS_WHERE = "SELECT emailAddress FROM EmailAddress emailAddress WHERE ";
	private static final String _SQL_COUNT_EMAILADDRESS = "SELECT COUNT(emailAddress) FROM EmailAddress emailAddress";
	private static final String _SQL_COUNT_EMAILADDRESS_WHERE = "SELECT COUNT(emailAddress) FROM EmailAddress emailAddress WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "emailAddress.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No EmailAddress exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No EmailAddress exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(EmailAddressPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "primary"
			});
	private static final EmailAddress _nullEmailAddress = new EmailAddressImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<EmailAddress> toCacheModel() {
				return _nullEmailAddressCacheModel;
			}
		};

	private static final CacheModel<EmailAddress> _nullEmailAddressCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<EmailAddress>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public EmailAddress toEntityModel() {
			return _nullEmailAddress;
		}
	}
}