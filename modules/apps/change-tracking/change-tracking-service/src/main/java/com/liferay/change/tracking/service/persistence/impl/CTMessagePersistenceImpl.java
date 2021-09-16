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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchMessageException;
import com.liferay.change.tracking.model.CTMessage;
import com.liferay.change.tracking.model.CTMessageTable;
import com.liferay.change.tracking.model.impl.CTMessageImpl;
import com.liferay.change.tracking.model.impl.CTMessageModelImpl;
import com.liferay.change.tracking.service.persistence.CTMessagePersistence;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
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

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ct message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {CTMessagePersistence.class, BasePersistence.class})
public class CTMessagePersistenceImpl
	extends BasePersistenceImpl<CTMessage> implements CTMessagePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTMessageUtil</code> to access the ct message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTMessageImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCtCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByCtCollectionId;
	private FinderPath _finderPathCountByCtCollectionId;

	/**
	 * Returns all the ct messages where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct messages
	 */
	@Override
	public List<CTMessage> findByCtCollectionId(long ctCollectionId) {
		return findByCtCollectionId(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct messages where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @return the range of matching ct messages
	 */
	@Override
	public List<CTMessage> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return findByCtCollectionId(ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct messages where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct messages
	 */
	@Override
	public List<CTMessage> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTMessage> orderByComparator) {

		return findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct messages where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct messages
	 */
	@Override
	public List<CTMessage> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTMessage> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCtCollectionId;
				finderArgs = new Object[] {ctCollectionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCtCollectionId;
			finderArgs = new Object[] {
				ctCollectionId, start, end, orderByComparator
			};
		}

		List<CTMessage> list = null;

		if (useFinderCache) {
			list = (List<CTMessage>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTMessage ctMessage : list) {
					if (ctCollectionId != ctMessage.getCtCollectionId()) {
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

			sb.append(_SQL_SELECT_CTMESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				list = (List<CTMessage>)QueryUtil.list(
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
	 * Returns the first ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct message
	 * @throws NoSuchMessageException if a matching ct message could not be found
	 */
	@Override
	public CTMessage findByCtCollectionId_First(
			long ctCollectionId, OrderByComparator<CTMessage> orderByComparator)
		throws NoSuchMessageException {

		CTMessage ctMessage = fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);

		if (ctMessage != null) {
			return ctMessage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchMessageException(sb.toString());
	}

	/**
	 * Returns the first ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct message, or <code>null</code> if a matching ct message could not be found
	 */
	@Override
	public CTMessage fetchByCtCollectionId_First(
		long ctCollectionId, OrderByComparator<CTMessage> orderByComparator) {

		List<CTMessage> list = findByCtCollectionId(
			ctCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct message
	 * @throws NoSuchMessageException if a matching ct message could not be found
	 */
	@Override
	public CTMessage findByCtCollectionId_Last(
			long ctCollectionId, OrderByComparator<CTMessage> orderByComparator)
		throws NoSuchMessageException {

		CTMessage ctMessage = fetchByCtCollectionId_Last(
			ctCollectionId, orderByComparator);

		if (ctMessage != null) {
			return ctMessage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchMessageException(sb.toString());
	}

	/**
	 * Returns the last ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct message, or <code>null</code> if a matching ct message could not be found
	 */
	@Override
	public CTMessage fetchByCtCollectionId_Last(
		long ctCollectionId, OrderByComparator<CTMessage> orderByComparator) {

		int count = countByCtCollectionId(ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<CTMessage> list = findByCtCollectionId(
			ctCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct messages before and after the current ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctMessageId the primary key of the current ct message
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct message
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	@Override
	public CTMessage[] findByCtCollectionId_PrevAndNext(
			long ctMessageId, long ctCollectionId,
			OrderByComparator<CTMessage> orderByComparator)
		throws NoSuchMessageException {

		CTMessage ctMessage = findByPrimaryKey(ctMessageId);

		Session session = null;

		try {
			session = openSession();

			CTMessage[] array = new CTMessageImpl[3];

			array[0] = getByCtCollectionId_PrevAndNext(
				session, ctMessage, ctCollectionId, orderByComparator, true);

			array[1] = ctMessage;

			array[2] = getByCtCollectionId_PrevAndNext(
				session, ctMessage, ctCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTMessage getByCtCollectionId_PrevAndNext(
		Session session, CTMessage ctMessage, long ctCollectionId,
		OrderByComparator<CTMessage> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CTMESSAGE_WHERE);

		sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

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
			sb.append(CTMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctMessage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTMessage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct messages where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByCtCollectionId(long ctCollectionId) {
		for (CTMessage ctMessage :
				findByCtCollectionId(
					ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctMessage);
		}
	}

	/**
	 * Returns the number of ct messages where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct messages
	 */
	@Override
	public int countByCtCollectionId(long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByCtCollectionId;

		Object[] finderArgs = new Object[] {ctCollectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTMESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

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

	private static final String _FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2 =
		"ctMessage.ctCollectionId = ?";

	public CTMessagePersistenceImpl() {
		setModelClass(CTMessage.class);

		setModelImplClass(CTMessageImpl.class);
		setModelPKClass(long.class);

		setTable(CTMessageTable.INSTANCE);
	}

	/**
	 * Caches the ct message in the entity cache if it is enabled.
	 *
	 * @param ctMessage the ct message
	 */
	@Override
	public void cacheResult(CTMessage ctMessage) {
		entityCache.putResult(
			CTMessageImpl.class, ctMessage.getPrimaryKey(), ctMessage);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ct messages in the entity cache if it is enabled.
	 *
	 * @param ctMessages the ct messages
	 */
	@Override
	public void cacheResult(List<CTMessage> ctMessages) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ctMessages.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CTMessage ctMessage : ctMessages) {
			if (entityCache.getResult(
					CTMessageImpl.class, ctMessage.getPrimaryKey()) == null) {

				cacheResult(ctMessage);
			}
		}
	}

	/**
	 * Clears the cache for all ct messages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTMessageImpl.class);

		finderCache.clearCache(CTMessageImpl.class);
	}

	/**
	 * Clears the cache for the ct message.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTMessage ctMessage) {
		entityCache.removeResult(CTMessageImpl.class, ctMessage);
	}

	@Override
	public void clearCache(List<CTMessage> ctMessages) {
		for (CTMessage ctMessage : ctMessages) {
			entityCache.removeResult(CTMessageImpl.class, ctMessage);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CTMessageImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CTMessageImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new ct message with the primary key. Does not add the ct message to the database.
	 *
	 * @param ctMessageId the primary key for the new ct message
	 * @return the new ct message
	 */
	@Override
	public CTMessage create(long ctMessageId) {
		CTMessage ctMessage = new CTMessageImpl();

		ctMessage.setNew(true);
		ctMessage.setPrimaryKey(ctMessageId);

		ctMessage.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctMessage;
	}

	/**
	 * Removes the ct message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message that was removed
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	@Override
	public CTMessage remove(long ctMessageId) throws NoSuchMessageException {
		return remove((Serializable)ctMessageId);
	}

	/**
	 * Removes the ct message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct message
	 * @return the ct message that was removed
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	@Override
	public CTMessage remove(Serializable primaryKey)
		throws NoSuchMessageException {

		Session session = null;

		try {
			session = openSession();

			CTMessage ctMessage = (CTMessage)session.get(
				CTMessageImpl.class, primaryKey);

			if (ctMessage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMessageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctMessage);
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
	protected CTMessage removeImpl(CTMessage ctMessage) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctMessage)) {
				ctMessage = (CTMessage)session.get(
					CTMessageImpl.class, ctMessage.getPrimaryKeyObj());
			}

			if (ctMessage != null) {
				session.delete(ctMessage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctMessage != null) {
			clearCache(ctMessage);
		}

		return ctMessage;
	}

	@Override
	public CTMessage updateImpl(CTMessage ctMessage) {
		boolean isNew = ctMessage.isNew();

		if (!(ctMessage instanceof CTMessageModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctMessage.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctMessage);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctMessage proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTMessage implementation " +
					ctMessage.getClass());
		}

		CTMessageModelImpl ctMessageModelImpl = (CTMessageModelImpl)ctMessage;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ctMessage);
			}
			else {
				ctMessage = (CTMessage)session.merge(ctMessage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CTMessageImpl.class, ctMessageModelImpl, false, true);

		if (isNew) {
			ctMessage.setNew(false);
		}

		ctMessage.resetOriginalValues();

		return ctMessage;
	}

	/**
	 * Returns the ct message with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct message
	 * @return the ct message
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	@Override
	public CTMessage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMessageException {

		CTMessage ctMessage = fetchByPrimaryKey(primaryKey);

		if (ctMessage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMessageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctMessage;
	}

	/**
	 * Returns the ct message with the primary key or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	@Override
	public CTMessage findByPrimaryKey(long ctMessageId)
		throws NoSuchMessageException {

		return findByPrimaryKey((Serializable)ctMessageId);
	}

	/**
	 * Returns the ct message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message, or <code>null</code> if a ct message with the primary key could not be found
	 */
	@Override
	public CTMessage fetchByPrimaryKey(long ctMessageId) {
		return fetchByPrimaryKey((Serializable)ctMessageId);
	}

	/**
	 * Returns all the ct messages.
	 *
	 * @return the ct messages
	 */
	@Override
	public List<CTMessage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @return the range of ct messages
	 */
	@Override
	public List<CTMessage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct messages
	 */
	@Override
	public List<CTMessage> findAll(
		int start, int end, OrderByComparator<CTMessage> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct messages
	 */
	@Override
	public List<CTMessage> findAll(
		int start, int end, OrderByComparator<CTMessage> orderByComparator,
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

		List<CTMessage> list = null;

		if (useFinderCache) {
			list = (List<CTMessage>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CTMESSAGE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CTMESSAGE;

				sql = sql.concat(CTMessageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CTMessage>)QueryUtil.list(
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
	 * Removes all the ct messages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTMessage ctMessage : findAll()) {
			remove(ctMessage);
		}
	}

	/**
	 * Returns the number of ct messages.
	 *
	 * @return the number of ct messages
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CTMESSAGE);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ctMessageId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTMESSAGE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTMessageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct message persistence.
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

		_finderPathWithPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCtCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ctCollectionId"}, true);

		_finderPathWithoutPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, true);

		_finderPathCountByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTMessageImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CTMESSAGE =
		"SELECT ctMessage FROM CTMessage ctMessage";

	private static final String _SQL_SELECT_CTMESSAGE_WHERE =
		"SELECT ctMessage FROM CTMessage ctMessage WHERE ";

	private static final String _SQL_COUNT_CTMESSAGE =
		"SELECT COUNT(ctMessage) FROM CTMessage ctMessage";

	private static final String _SQL_COUNT_CTMESSAGE_WHERE =
		"SELECT COUNT(ctMessage) FROM CTMessage ctMessage WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctMessage.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTMessage exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTMessage exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTMessagePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CTMessageModelArgumentsResolver _ctMessageModelArgumentsResolver;

}