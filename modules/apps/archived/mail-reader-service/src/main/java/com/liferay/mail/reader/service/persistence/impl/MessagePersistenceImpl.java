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

package com.liferay.mail.reader.service.persistence.impl;

import com.liferay.mail.reader.exception.NoSuchMessageException;
import com.liferay.mail.reader.model.Message;
import com.liferay.mail.reader.model.MessageTable;
import com.liferay.mail.reader.model.impl.MessageImpl;
import com.liferay.mail.reader.model.impl.MessageModelImpl;
import com.liferay.mail.reader.service.persistence.MessagePersistence;
import com.liferay.mail.reader.service.persistence.impl.constants.MailPersistenceConstants;
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {MessagePersistence.class, BasePersistence.class})
public class MessagePersistenceImpl
	extends BasePersistenceImpl<Message> implements MessagePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MessageUtil</code> to access the message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MessageImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching messages
	 */
	@Override
	public List<Message> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @return the range of matching messages
	 */
	@Override
	public List<Message> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching messages
	 */
	@Override
	public List<Message> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Message> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching messages
	 */
	@Override
	public List<Message> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Message> orderByComparator, boolean useFinderCache) {

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

		List<Message> list = null;

		if (useFinderCache) {
			list = (List<Message>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Message message : list) {
					if (companyId != message.getCompanyId()) {
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

			sb.append(_SQL_SELECT_MESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<Message>)QueryUtil.list(
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
	 * Returns the first message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message
	 * @throws NoSuchMessageException if a matching message could not be found
	 */
	@Override
	public Message findByCompanyId_First(
			long companyId, OrderByComparator<Message> orderByComparator)
		throws NoSuchMessageException {

		Message message = fetchByCompanyId_First(companyId, orderByComparator);

		if (message != null) {
			return message;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMessageException(sb.toString());
	}

	/**
	 * Returns the first message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message, or <code>null</code> if a matching message could not be found
	 */
	@Override
	public Message fetchByCompanyId_First(
		long companyId, OrderByComparator<Message> orderByComparator) {

		List<Message> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message
	 * @throws NoSuchMessageException if a matching message could not be found
	 */
	@Override
	public Message findByCompanyId_Last(
			long companyId, OrderByComparator<Message> orderByComparator)
		throws NoSuchMessageException {

		Message message = fetchByCompanyId_Last(companyId, orderByComparator);

		if (message != null) {
			return message;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMessageException(sb.toString());
	}

	/**
	 * Returns the last message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message, or <code>null</code> if a matching message could not be found
	 */
	@Override
	public Message fetchByCompanyId_Last(
		long companyId, OrderByComparator<Message> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<Message> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the messages before and after the current message in the ordered set where companyId = &#63;.
	 *
	 * @param messageId the primary key of the current message
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message
	 * @throws NoSuchMessageException if a message with the primary key could not be found
	 */
	@Override
	public Message[] findByCompanyId_PrevAndNext(
			long messageId, long companyId,
			OrderByComparator<Message> orderByComparator)
		throws NoSuchMessageException {

		Message message = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			Message[] array = new MessageImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, message, companyId, orderByComparator, true);

			array[1] = message;

			array[2] = getByCompanyId_PrevAndNext(
				session, message, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Message getByCompanyId_PrevAndNext(
		Session session, Message message, long companyId,
		OrderByComparator<Message> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MESSAGE_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(MessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(message)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Message> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the messages where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (Message message :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(message);
		}
	}

	/**
	 * Returns the number of messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching messages
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MESSAGE_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"message.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByFolderId;
	private FinderPath _finderPathWithoutPaginationFindByFolderId;
	private FinderPath _finderPathCountByFolderId;

	/**
	 * Returns all the messages where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @return the matching messages
	 */
	@Override
	public List<Message> findByFolderId(long folderId) {
		return findByFolderId(
			folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the messages where folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MessageModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @return the range of matching messages
	 */
	@Override
	public List<Message> findByFolderId(long folderId, int start, int end) {
		return findByFolderId(folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the messages where folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MessageModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching messages
	 */
	@Override
	public List<Message> findByFolderId(
		long folderId, int start, int end,
		OrderByComparator<Message> orderByComparator) {

		return findByFolderId(folderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the messages where folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MessageModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching messages
	 */
	@Override
	public List<Message> findByFolderId(
		long folderId, int start, int end,
		OrderByComparator<Message> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByFolderId;
				finderArgs = new Object[] {folderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByFolderId;
			finderArgs = new Object[] {folderId, start, end, orderByComparator};
		}

		List<Message> list = null;

		if (useFinderCache) {
			list = (List<Message>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Message message : list) {
					if (folderId != message.getFolderId()) {
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

			sb.append(_SQL_SELECT_MESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_FOLDERID_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(folderId);

				list = (List<Message>)QueryUtil.list(
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
	 * Returns the first message in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message
	 * @throws NoSuchMessageException if a matching message could not be found
	 */
	@Override
	public Message findByFolderId_First(
			long folderId, OrderByComparator<Message> orderByComparator)
		throws NoSuchMessageException {

		Message message = fetchByFolderId_First(folderId, orderByComparator);

		if (message != null) {
			return message;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("folderId=");
		sb.append(folderId);

		sb.append("}");

		throw new NoSuchMessageException(sb.toString());
	}

	/**
	 * Returns the first message in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message, or <code>null</code> if a matching message could not be found
	 */
	@Override
	public Message fetchByFolderId_First(
		long folderId, OrderByComparator<Message> orderByComparator) {

		List<Message> list = findByFolderId(folderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message
	 * @throws NoSuchMessageException if a matching message could not be found
	 */
	@Override
	public Message findByFolderId_Last(
			long folderId, OrderByComparator<Message> orderByComparator)
		throws NoSuchMessageException {

		Message message = fetchByFolderId_Last(folderId, orderByComparator);

		if (message != null) {
			return message;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("folderId=");
		sb.append(folderId);

		sb.append("}");

		throw new NoSuchMessageException(sb.toString());
	}

	/**
	 * Returns the last message in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message, or <code>null</code> if a matching message could not be found
	 */
	@Override
	public Message fetchByFolderId_Last(
		long folderId, OrderByComparator<Message> orderByComparator) {

		int count = countByFolderId(folderId);

		if (count == 0) {
			return null;
		}

		List<Message> list = findByFolderId(
			folderId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the messages before and after the current message in the ordered set where folderId = &#63;.
	 *
	 * @param messageId the primary key of the current message
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message
	 * @throws NoSuchMessageException if a message with the primary key could not be found
	 */
	@Override
	public Message[] findByFolderId_PrevAndNext(
			long messageId, long folderId,
			OrderByComparator<Message> orderByComparator)
		throws NoSuchMessageException {

		Message message = findByPrimaryKey(messageId);

		Session session = null;

		try {
			session = openSession();

			Message[] array = new MessageImpl[3];

			array[0] = getByFolderId_PrevAndNext(
				session, message, folderId, orderByComparator, true);

			array[1] = message;

			array[2] = getByFolderId_PrevAndNext(
				session, message, folderId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Message getByFolderId_PrevAndNext(
		Session session, Message message, long folderId,
		OrderByComparator<Message> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MESSAGE_WHERE);

		sb.append(_FINDER_COLUMN_FOLDERID_FOLDERID_2);

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
			sb.append(MessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(folderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(message)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Message> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the messages where folderId = &#63; from the database.
	 *
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByFolderId(long folderId) {
		for (Message message :
				findByFolderId(
					folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(message);
		}
	}

	/**
	 * Returns the number of messages where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @return the number of matching messages
	 */
	@Override
	public int countByFolderId(long folderId) {
		FinderPath finderPath = _finderPathCountByFolderId;

		Object[] finderArgs = new Object[] {folderId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_FOLDERID_FOLDERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(folderId);

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

	private static final String _FINDER_COLUMN_FOLDERID_FOLDERID_2 =
		"message.folderId = ?";

	private FinderPath _finderPathFetchByF_R;
	private FinderPath _finderPathCountByF_R;

	/**
	 * Returns the message where folderId = &#63; and remoteMessageId = &#63; or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param folderId the folder ID
	 * @param remoteMessageId the remote message ID
	 * @return the matching message
	 * @throws NoSuchMessageException if a matching message could not be found
	 */
	@Override
	public Message findByF_R(long folderId, long remoteMessageId)
		throws NoSuchMessageException {

		Message message = fetchByF_R(folderId, remoteMessageId);

		if (message == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("folderId=");
			sb.append(folderId);

			sb.append(", remoteMessageId=");
			sb.append(remoteMessageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchMessageException(sb.toString());
		}

		return message;
	}

	/**
	 * Returns the message where folderId = &#63; and remoteMessageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param folderId the folder ID
	 * @param remoteMessageId the remote message ID
	 * @return the matching message, or <code>null</code> if a matching message could not be found
	 */
	@Override
	public Message fetchByF_R(long folderId, long remoteMessageId) {
		return fetchByF_R(folderId, remoteMessageId, true);
	}

	/**
	 * Returns the message where folderId = &#63; and remoteMessageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param folderId the folder ID
	 * @param remoteMessageId the remote message ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message, or <code>null</code> if a matching message could not be found
	 */
	@Override
	public Message fetchByF_R(
		long folderId, long remoteMessageId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {folderId, remoteMessageId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByF_R, finderArgs);
		}

		if (result instanceof Message) {
			Message message = (Message)result;

			if ((folderId != message.getFolderId()) ||
				(remoteMessageId != message.getRemoteMessageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_F_R_FOLDERID_2);

			sb.append(_FINDER_COLUMN_F_R_REMOTEMESSAGEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(folderId);

				queryPos.add(remoteMessageId);

				List<Message> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByF_R, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									folderId, remoteMessageId
								};
							}

							_log.warn(
								"MessagePersistenceImpl.fetchByF_R(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Message message = list.get(0);

					result = message;

					cacheResult(message);
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
			return (Message)result;
		}
	}

	/**
	 * Removes the message where folderId = &#63; and remoteMessageId = &#63; from the database.
	 *
	 * @param folderId the folder ID
	 * @param remoteMessageId the remote message ID
	 * @return the message that was removed
	 */
	@Override
	public Message removeByF_R(long folderId, long remoteMessageId)
		throws NoSuchMessageException {

		Message message = findByF_R(folderId, remoteMessageId);

		return remove(message);
	}

	/**
	 * Returns the number of messages where folderId = &#63; and remoteMessageId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param remoteMessageId the remote message ID
	 * @return the number of matching messages
	 */
	@Override
	public int countByF_R(long folderId, long remoteMessageId) {
		FinderPath finderPath = _finderPathCountByF_R;

		Object[] finderArgs = new Object[] {folderId, remoteMessageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_F_R_FOLDERID_2);

			sb.append(_FINDER_COLUMN_F_R_REMOTEMESSAGEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(folderId);

				queryPos.add(remoteMessageId);

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

	private static final String _FINDER_COLUMN_F_R_FOLDERID_2 =
		"message.folderId = ? AND ";

	private static final String _FINDER_COLUMN_F_R_REMOTEMESSAGEID_2 =
		"message.remoteMessageId = ?";

	public MessagePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("to", "to_");
		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Message.class);

		setModelImplClass(MessageImpl.class);
		setModelPKClass(long.class);

		setTable(MessageTable.INSTANCE);
	}

	/**
	 * Caches the message in the entity cache if it is enabled.
	 *
	 * @param message the message
	 */
	@Override
	public void cacheResult(Message message) {
		entityCache.putResult(
			MessageImpl.class, message.getPrimaryKey(), message);

		finderCache.putResult(
			_finderPathFetchByF_R,
			new Object[] {message.getFolderId(), message.getRemoteMessageId()},
			message);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the messages in the entity cache if it is enabled.
	 *
	 * @param messages the messages
	 */
	@Override
	public void cacheResult(List<Message> messages) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (messages.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Message message : messages) {
			if (entityCache.getResult(
					MessageImpl.class, message.getPrimaryKey()) == null) {

				cacheResult(message);
			}
		}
	}

	/**
	 * Clears the cache for all messages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MessageImpl.class);

		finderCache.clearCache(MessageImpl.class);
	}

	/**
	 * Clears the cache for the message.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Message message) {
		entityCache.removeResult(MessageImpl.class, message);
	}

	@Override
	public void clearCache(List<Message> messages) {
		for (Message message : messages) {
			entityCache.removeResult(MessageImpl.class, message);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(MessageImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MessageImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(MessageModelImpl messageModelImpl) {
		Object[] args = new Object[] {
			messageModelImpl.getFolderId(),
			messageModelImpl.getRemoteMessageId()
		};

		finderCache.putResult(_finderPathCountByF_R, args, Long.valueOf(1));
		finderCache.putResult(_finderPathFetchByF_R, args, messageModelImpl);
	}

	/**
	 * Creates a new message with the primary key. Does not add the message to the database.
	 *
	 * @param messageId the primary key for the new message
	 * @return the new message
	 */
	@Override
	public Message create(long messageId) {
		Message message = new MessageImpl();

		message.setNew(true);
		message.setPrimaryKey(messageId);

		message.setCompanyId(CompanyThreadLocal.getCompanyId());

		return message;
	}

	/**
	 * Removes the message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param messageId the primary key of the message
	 * @return the message that was removed
	 * @throws NoSuchMessageException if a message with the primary key could not be found
	 */
	@Override
	public Message remove(long messageId) throws NoSuchMessageException {
		return remove((Serializable)messageId);
	}

	/**
	 * Removes the message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message
	 * @return the message that was removed
	 * @throws NoSuchMessageException if a message with the primary key could not be found
	 */
	@Override
	public Message remove(Serializable primaryKey)
		throws NoSuchMessageException {

		Session session = null;

		try {
			session = openSession();

			Message message = (Message)session.get(
				MessageImpl.class, primaryKey);

			if (message == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMessageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(message);
		}
		catch (NoSuchMessageException noSuchEntityException) {
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
	protected Message removeImpl(Message message) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(message)) {
				message = (Message)session.get(
					MessageImpl.class, message.getPrimaryKeyObj());
			}

			if (message != null) {
				session.delete(message);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (message != null) {
			clearCache(message);
		}

		return message;
	}

	@Override
	public Message updateImpl(Message message) {
		boolean isNew = message.isNew();

		if (!(message instanceof MessageModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(message.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(message);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in message proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Message implementation " +
					message.getClass());
		}

		MessageModelImpl messageModelImpl = (MessageModelImpl)message;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (message.getCreateDate() == null)) {
			if (serviceContext == null) {
				message.setCreateDate(date);
			}
			else {
				message.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!messageModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				message.setModifiedDate(date);
			}
			else {
				message.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(message);
			}
			else {
				message = (Message)session.merge(message);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(MessageImpl.class, messageModelImpl, false, true);

		cacheUniqueFindersCache(messageModelImpl);

		if (isNew) {
			message.setNew(false);
		}

		message.resetOriginalValues();

		return message;
	}

	/**
	 * Returns the message with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message
	 * @return the message
	 * @throws NoSuchMessageException if a message with the primary key could not be found
	 */
	@Override
	public Message findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMessageException {

		Message message = fetchByPrimaryKey(primaryKey);

		if (message == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMessageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return message;
	}

	/**
	 * Returns the message with the primary key or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param messageId the primary key of the message
	 * @return the message
	 * @throws NoSuchMessageException if a message with the primary key could not be found
	 */
	@Override
	public Message findByPrimaryKey(long messageId)
		throws NoSuchMessageException {

		return findByPrimaryKey((Serializable)messageId);
	}

	/**
	 * Returns the message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param messageId the primary key of the message
	 * @return the message, or <code>null</code> if a message with the primary key could not be found
	 */
	@Override
	public Message fetchByPrimaryKey(long messageId) {
		return fetchByPrimaryKey((Serializable)messageId);
	}

	/**
	 * Returns all the messages.
	 *
	 * @return the messages
	 */
	@Override
	public List<Message> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @return the range of messages
	 */
	@Override
	public List<Message> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of messages
	 */
	@Override
	public List<Message> findAll(
		int start, int end, OrderByComparator<Message> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of messages
	 */
	@Override
	public List<Message> findAll(
		int start, int end, OrderByComparator<Message> orderByComparator,
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

		List<Message> list = null;

		if (useFinderCache) {
			list = (List<Message>)finderCache.getResult(finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MESSAGE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MESSAGE;

				sql = sql.concat(MessageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Message>)QueryUtil.list(
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
	 * Removes all the messages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Message message : findAll()) {
			remove(message);
		}
	}

	/**
	 * Returns the number of messages.
	 *
	 * @return the number of messages
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MESSAGE);

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
		return "messageId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MESSAGE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MessageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the message persistence.
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

		_finderPathWithPaginationFindByFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFolderId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"folderId"}, true);

		_finderPathWithoutPaginationFindByFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFolderId",
			new String[] {Long.class.getName()}, new String[] {"folderId"},
			true);

		_finderPathCountByFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFolderId",
			new String[] {Long.class.getName()}, new String[] {"folderId"},
			false);

		_finderPathFetchByF_R = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByF_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"folderId", "remoteMessageId"}, true);

		_finderPathCountByF_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"folderId", "remoteMessageId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MessageImpl.class.getName());
	}

	@Override
	@Reference(
		target = MailPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MailPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MailPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_MESSAGE =
		"SELECT message FROM Message message";

	private static final String _SQL_SELECT_MESSAGE_WHERE =
		"SELECT message FROM Message message WHERE ";

	private static final String _SQL_COUNT_MESSAGE =
		"SELECT COUNT(message) FROM Message message";

	private static final String _SQL_COUNT_MESSAGE_WHERE =
		"SELECT COUNT(message) FROM Message message WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "message.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Message exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Message exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MessagePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"to", "size"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private MessageModelArgumentsResolver _messageModelArgumentsResolver;

}