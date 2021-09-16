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

import com.liferay.mail.reader.exception.NoSuchAttachmentException;
import com.liferay.mail.reader.model.Attachment;
import com.liferay.mail.reader.model.AttachmentTable;
import com.liferay.mail.reader.model.impl.AttachmentImpl;
import com.liferay.mail.reader.model.impl.AttachmentModelImpl;
import com.liferay.mail.reader.service.persistence.AttachmentPersistence;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {AttachmentPersistence.class, BasePersistence.class})
public class AttachmentPersistenceImpl
	extends BasePersistenceImpl<Attachment> implements AttachmentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AttachmentUtil</code> to access the attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AttachmentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByMessageId;
	private FinderPath _finderPathWithoutPaginationFindByMessageId;
	private FinderPath _finderPathCountByMessageId;

	/**
	 * Returns all the attachments where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the matching attachments
	 */
	@Override
	public List<Attachment> findByMessageId(long messageId) {
		return findByMessageId(
			messageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the attachments where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of attachments
	 * @param end the upper bound of the range of attachments (not inclusive)
	 * @return the range of matching attachments
	 */
	@Override
	public List<Attachment> findByMessageId(
		long messageId, int start, int end) {

		return findByMessageId(messageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the attachments where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of attachments
	 * @param end the upper bound of the range of attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching attachments
	 */
	@Override
	public List<Attachment> findByMessageId(
		long messageId, int start, int end,
		OrderByComparator<Attachment> orderByComparator) {

		return findByMessageId(messageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the attachments where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of attachments
	 * @param end the upper bound of the range of attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching attachments
	 */
	@Override
	public List<Attachment> findByMessageId(
		long messageId, int start, int end,
		OrderByComparator<Attachment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByMessageId;
				finderArgs = new Object[] {messageId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByMessageId;
			finderArgs = new Object[] {
				messageId, start, end, orderByComparator
			};
		}

		List<Attachment> list = null;

		if (useFinderCache) {
			list = (List<Attachment>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Attachment attachment : list) {
					if (messageId != attachment.getMessageId()) {
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

			sb.append(_SQL_SELECT_ATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AttachmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(messageId);

				list = (List<Attachment>)QueryUtil.list(
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
	 * Returns the first attachment in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching attachment
	 * @throws NoSuchAttachmentException if a matching attachment could not be found
	 */
	@Override
	public Attachment findByMessageId_First(
			long messageId, OrderByComparator<Attachment> orderByComparator)
		throws NoSuchAttachmentException {

		Attachment attachment = fetchByMessageId_First(
			messageId, orderByComparator);

		if (attachment != null) {
			return attachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("messageId=");
		sb.append(messageId);

		sb.append("}");

		throw new NoSuchAttachmentException(sb.toString());
	}

	/**
	 * Returns the first attachment in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching attachment, or <code>null</code> if a matching attachment could not be found
	 */
	@Override
	public Attachment fetchByMessageId_First(
		long messageId, OrderByComparator<Attachment> orderByComparator) {

		List<Attachment> list = findByMessageId(
			messageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last attachment in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching attachment
	 * @throws NoSuchAttachmentException if a matching attachment could not be found
	 */
	@Override
	public Attachment findByMessageId_Last(
			long messageId, OrderByComparator<Attachment> orderByComparator)
		throws NoSuchAttachmentException {

		Attachment attachment = fetchByMessageId_Last(
			messageId, orderByComparator);

		if (attachment != null) {
			return attachment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("messageId=");
		sb.append(messageId);

		sb.append("}");

		throw new NoSuchAttachmentException(sb.toString());
	}

	/**
	 * Returns the last attachment in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching attachment, or <code>null</code> if a matching attachment could not be found
	 */
	@Override
	public Attachment fetchByMessageId_Last(
		long messageId, OrderByComparator<Attachment> orderByComparator) {

		int count = countByMessageId(messageId);

		if (count == 0) {
			return null;
		}

		List<Attachment> list = findByMessageId(
			messageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the attachments before and after the current attachment in the ordered set where messageId = &#63;.
	 *
	 * @param attachmentId the primary key of the current attachment
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next attachment
	 * @throws NoSuchAttachmentException if a attachment with the primary key could not be found
	 */
	@Override
	public Attachment[] findByMessageId_PrevAndNext(
			long attachmentId, long messageId,
			OrderByComparator<Attachment> orderByComparator)
		throws NoSuchAttachmentException {

		Attachment attachment = findByPrimaryKey(attachmentId);

		Session session = null;

		try {
			session = openSession();

			Attachment[] array = new AttachmentImpl[3];

			array[0] = getByMessageId_PrevAndNext(
				session, attachment, messageId, orderByComparator, true);

			array[1] = attachment;

			array[2] = getByMessageId_PrevAndNext(
				session, attachment, messageId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Attachment getByMessageId_PrevAndNext(
		Session session, Attachment attachment, long messageId,
		OrderByComparator<Attachment> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ATTACHMENT_WHERE);

		sb.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

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
			sb.append(AttachmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(messageId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(attachment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Attachment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the attachments where messageId = &#63; from the database.
	 *
	 * @param messageId the message ID
	 */
	@Override
	public void removeByMessageId(long messageId) {
		for (Attachment attachment :
				findByMessageId(
					messageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(attachment);
		}
	}

	/**
	 * Returns the number of attachments where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the number of matching attachments
	 */
	@Override
	public int countByMessageId(long messageId) {
		FinderPath finderPath = _finderPathCountByMessageId;

		Object[] finderArgs = new Object[] {messageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ATTACHMENT_WHERE);

			sb.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(messageId);

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

	private static final String _FINDER_COLUMN_MESSAGEID_MESSAGEID_2 =
		"attachment.messageId = ?";

	public AttachmentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Attachment.class);

		setModelImplClass(AttachmentImpl.class);
		setModelPKClass(long.class);

		setTable(AttachmentTable.INSTANCE);
	}

	/**
	 * Caches the attachment in the entity cache if it is enabled.
	 *
	 * @param attachment the attachment
	 */
	@Override
	public void cacheResult(Attachment attachment) {
		entityCache.putResult(
			AttachmentImpl.class, attachment.getPrimaryKey(), attachment);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the attachments in the entity cache if it is enabled.
	 *
	 * @param attachments the attachments
	 */
	@Override
	public void cacheResult(List<Attachment> attachments) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (attachments.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Attachment attachment : attachments) {
			if (entityCache.getResult(
					AttachmentImpl.class, attachment.getPrimaryKey()) == null) {

				cacheResult(attachment);
			}
		}
	}

	/**
	 * Clears the cache for all attachments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AttachmentImpl.class);

		finderCache.clearCache(AttachmentImpl.class);
	}

	/**
	 * Clears the cache for the attachment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Attachment attachment) {
		entityCache.removeResult(AttachmentImpl.class, attachment);
	}

	@Override
	public void clearCache(List<Attachment> attachments) {
		for (Attachment attachment : attachments) {
			entityCache.removeResult(AttachmentImpl.class, attachment);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AttachmentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AttachmentImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new attachment with the primary key. Does not add the attachment to the database.
	 *
	 * @param attachmentId the primary key for the new attachment
	 * @return the new attachment
	 */
	@Override
	public Attachment create(long attachmentId) {
		Attachment attachment = new AttachmentImpl();

		attachment.setNew(true);
		attachment.setPrimaryKey(attachmentId);

		attachment.setCompanyId(CompanyThreadLocal.getCompanyId());

		return attachment;
	}

	/**
	 * Removes the attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param attachmentId the primary key of the attachment
	 * @return the attachment that was removed
	 * @throws NoSuchAttachmentException if a attachment with the primary key could not be found
	 */
	@Override
	public Attachment remove(long attachmentId)
		throws NoSuchAttachmentException {

		return remove((Serializable)attachmentId);
	}

	/**
	 * Removes the attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the attachment
	 * @return the attachment that was removed
	 * @throws NoSuchAttachmentException if a attachment with the primary key could not be found
	 */
	@Override
	public Attachment remove(Serializable primaryKey)
		throws NoSuchAttachmentException {

		Session session = null;

		try {
			session = openSession();

			Attachment attachment = (Attachment)session.get(
				AttachmentImpl.class, primaryKey);

			if (attachment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAttachmentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(attachment);
		}
		catch (NoSuchAttachmentException noSuchEntityException) {
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
	protected Attachment removeImpl(Attachment attachment) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(attachment)) {
				attachment = (Attachment)session.get(
					AttachmentImpl.class, attachment.getPrimaryKeyObj());
			}

			if (attachment != null) {
				session.delete(attachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (attachment != null) {
			clearCache(attachment);
		}

		return attachment;
	}

	@Override
	public Attachment updateImpl(Attachment attachment) {
		boolean isNew = attachment.isNew();

		if (!(attachment instanceof AttachmentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(attachment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(attachment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in attachment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Attachment implementation " +
					attachment.getClass());
		}

		AttachmentModelImpl attachmentModelImpl =
			(AttachmentModelImpl)attachment;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(attachment);
			}
			else {
				attachment = (Attachment)session.merge(attachment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AttachmentImpl.class, attachmentModelImpl, false, true);

		if (isNew) {
			attachment.setNew(false);
		}

		attachment.resetOriginalValues();

		return attachment;
	}

	/**
	 * Returns the attachment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the attachment
	 * @return the attachment
	 * @throws NoSuchAttachmentException if a attachment with the primary key could not be found
	 */
	@Override
	public Attachment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAttachmentException {

		Attachment attachment = fetchByPrimaryKey(primaryKey);

		if (attachment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAttachmentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return attachment;
	}

	/**
	 * Returns the attachment with the primary key or throws a <code>NoSuchAttachmentException</code> if it could not be found.
	 *
	 * @param attachmentId the primary key of the attachment
	 * @return the attachment
	 * @throws NoSuchAttachmentException if a attachment with the primary key could not be found
	 */
	@Override
	public Attachment findByPrimaryKey(long attachmentId)
		throws NoSuchAttachmentException {

		return findByPrimaryKey((Serializable)attachmentId);
	}

	/**
	 * Returns the attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param attachmentId the primary key of the attachment
	 * @return the attachment, or <code>null</code> if a attachment with the primary key could not be found
	 */
	@Override
	public Attachment fetchByPrimaryKey(long attachmentId) {
		return fetchByPrimaryKey((Serializable)attachmentId);
	}

	/**
	 * Returns all the attachments.
	 *
	 * @return the attachments
	 */
	@Override
	public List<Attachment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of attachments
	 * @param end the upper bound of the range of attachments (not inclusive)
	 * @return the range of attachments
	 */
	@Override
	public List<Attachment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of attachments
	 * @param end the upper bound of the range of attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of attachments
	 */
	@Override
	public List<Attachment> findAll(
		int start, int end, OrderByComparator<Attachment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of attachments
	 * @param end the upper bound of the range of attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of attachments
	 */
	@Override
	public List<Attachment> findAll(
		int start, int end, OrderByComparator<Attachment> orderByComparator,
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

		List<Attachment> list = null;

		if (useFinderCache) {
			list = (List<Attachment>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ATTACHMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ATTACHMENT;

				sql = sql.concat(AttachmentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Attachment>)QueryUtil.list(
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
	 * Removes all the attachments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Attachment attachment : findAll()) {
			remove(attachment);
		}
	}

	/**
	 * Returns the number of attachments.
	 *
	 * @return the number of attachments
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ATTACHMENT);

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
		return "attachmentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ATTACHMENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AttachmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the attachment persistence.
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

		_finderPathWithPaginationFindByMessageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByMessageId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"messageId"}, true);

		_finderPathWithoutPaginationFindByMessageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByMessageId",
			new String[] {Long.class.getName()}, new String[] {"messageId"},
			true);

		_finderPathCountByMessageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMessageId",
			new String[] {Long.class.getName()}, new String[] {"messageId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AttachmentImpl.class.getName());
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

	private static final String _SQL_SELECT_ATTACHMENT =
		"SELECT attachment FROM Attachment attachment";

	private static final String _SQL_SELECT_ATTACHMENT_WHERE =
		"SELECT attachment FROM Attachment attachment WHERE ";

	private static final String _SQL_COUNT_ATTACHMENT =
		"SELECT COUNT(attachment) FROM Attachment attachment";

	private static final String _SQL_COUNT_ATTACHMENT_WHERE =
		"SELECT COUNT(attachment) FROM Attachment attachment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "attachment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Attachment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Attachment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AttachmentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"size"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private AttachmentModelArgumentsResolver _attachmentModelArgumentsResolver;

}