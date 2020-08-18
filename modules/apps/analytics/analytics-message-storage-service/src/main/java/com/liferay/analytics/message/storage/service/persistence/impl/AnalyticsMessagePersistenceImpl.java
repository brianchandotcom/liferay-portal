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

package com.liferay.analytics.message.storage.service.persistence.impl;

import com.liferay.analytics.message.storage.exception.NoSuchMessageException;
import com.liferay.analytics.message.storage.model.AnalyticsMessage;
import com.liferay.analytics.message.storage.model.AnalyticsMessageTable;
import com.liferay.analytics.message.storage.model.impl.AnalyticsMessageImpl;
import com.liferay.analytics.message.storage.model.impl.AnalyticsMessageModelImpl;
import com.liferay.analytics.message.storage.service.persistence.AnalyticsMessagePersistence;
import com.liferay.analytics.message.storage.service.persistence.impl.constants.AnalyticsPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
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
 * The persistence implementation for the analytics message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AnalyticsMessagePersistence.class)
public class AnalyticsMessagePersistenceImpl
	extends BasePersistenceImpl<AnalyticsMessage>
	implements AnalyticsMessagePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AnalyticsMessageUtil</code> to access the analytics message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AnalyticsMessageImpl.class.getName();

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
	 * Returns all the analytics messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching analytics messages
	 */
	@Override
	public List<AnalyticsMessage> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the analytics messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @return the range of matching analytics messages
	 */
	@Override
	public List<AnalyticsMessage> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the analytics messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics messages
	 */
	@Override
	public List<AnalyticsMessage> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsMessage> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the analytics messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics messages
	 */
	@Override
	public List<AnalyticsMessage> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsMessage> orderByComparator,
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

		List<AnalyticsMessage> list = null;

		if (useFinderCache) {
			list = (List<AnalyticsMessage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnalyticsMessage analyticsMessage : list) {
					if (companyId != analyticsMessage.getCompanyId()) {
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

			sb.append(_SQL_SELECT_ANALYTICSMESSAGE_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AnalyticsMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<AnalyticsMessage>)QueryUtil.list(
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
	 * Returns the first analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message
	 * @throws NoSuchMessageException if a matching analytics message could not be found
	 */
	@Override
	public AnalyticsMessage findByCompanyId_First(
			long companyId,
			OrderByComparator<AnalyticsMessage> orderByComparator)
		throws NoSuchMessageException {

		AnalyticsMessage analyticsMessage = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (analyticsMessage != null) {
			return analyticsMessage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMessageException(sb.toString());
	}

	/**
	 * Returns the first analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message, or <code>null</code> if a matching analytics message could not be found
	 */
	@Override
	public AnalyticsMessage fetchByCompanyId_First(
		long companyId, OrderByComparator<AnalyticsMessage> orderByComparator) {

		List<AnalyticsMessage> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message
	 * @throws NoSuchMessageException if a matching analytics message could not be found
	 */
	@Override
	public AnalyticsMessage findByCompanyId_Last(
			long companyId,
			OrderByComparator<AnalyticsMessage> orderByComparator)
		throws NoSuchMessageException {

		AnalyticsMessage analyticsMessage = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (analyticsMessage != null) {
			return analyticsMessage;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMessageException(sb.toString());
	}

	/**
	 * Returns the last analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message, or <code>null</code> if a matching analytics message could not be found
	 */
	@Override
	public AnalyticsMessage fetchByCompanyId_Last(
		long companyId, OrderByComparator<AnalyticsMessage> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AnalyticsMessage> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the analytics messages before and after the current analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param analyticsMessageId the primary key of the current analytics message
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics message
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	@Override
	public AnalyticsMessage[] findByCompanyId_PrevAndNext(
			long analyticsMessageId, long companyId,
			OrderByComparator<AnalyticsMessage> orderByComparator)
		throws NoSuchMessageException {

		AnalyticsMessage analyticsMessage = findByPrimaryKey(
			analyticsMessageId);

		Session session = null;

		try {
			session = openSession();

			AnalyticsMessage[] array = new AnalyticsMessageImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, analyticsMessage, companyId, orderByComparator, true);

			array[1] = analyticsMessage;

			array[2] = getByCompanyId_PrevAndNext(
				session, analyticsMessage, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnalyticsMessage getByCompanyId_PrevAndNext(
		Session session, AnalyticsMessage analyticsMessage, long companyId,
		OrderByComparator<AnalyticsMessage> orderByComparator,
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

		sb.append(_SQL_SELECT_ANALYTICSMESSAGE_WHERE);

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
			sb.append(AnalyticsMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						analyticsMessage)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AnalyticsMessage> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the analytics messages where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AnalyticsMessage analyticsMessage :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(analyticsMessage);
		}
	}

	/**
	 * Returns the number of analytics messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching analytics messages
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ANALYTICSMESSAGE_WHERE);

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
		"analyticsMessage.companyId = ?";

	public AnalyticsMessagePersistenceImpl() {
		setModelClass(AnalyticsMessage.class);

		setModelImplClass(AnalyticsMessageImpl.class);
		setModelPKClass(long.class);

		setTable(AnalyticsMessageTable.INSTANCE);
	}

	/**
	 * Caches the analytics message in the entity cache if it is enabled.
	 *
	 * @param analyticsMessage the analytics message
	 */
	@Override
	public void cacheResult(AnalyticsMessage analyticsMessage) {
		entityCache.putResult(
			AnalyticsMessageImpl.class, analyticsMessage.getPrimaryKey(),
			analyticsMessage);

		analyticsMessage.resetOriginalValues();
	}

	/**
	 * Caches the analytics messages in the entity cache if it is enabled.
	 *
	 * @param analyticsMessages the analytics messages
	 */
	@Override
	public void cacheResult(List<AnalyticsMessage> analyticsMessages) {
		for (AnalyticsMessage analyticsMessage : analyticsMessages) {
			if (entityCache.getResult(
					AnalyticsMessageImpl.class,
					analyticsMessage.getPrimaryKey()) == null) {

				cacheResult(analyticsMessage);
			}
			else {
				analyticsMessage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all analytics messages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AnalyticsMessageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the analytics message.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnalyticsMessage analyticsMessage) {
		entityCache.removeResult(
			AnalyticsMessageImpl.class, analyticsMessage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<AnalyticsMessage> analyticsMessages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AnalyticsMessage analyticsMessage : analyticsMessages) {
			entityCache.removeResult(
				AnalyticsMessageImpl.class, analyticsMessage.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AnalyticsMessageImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new analytics message with the primary key. Does not add the analytics message to the database.
	 *
	 * @param analyticsMessageId the primary key for the new analytics message
	 * @return the new analytics message
	 */
	@Override
	public AnalyticsMessage create(long analyticsMessageId) {
		AnalyticsMessage analyticsMessage = new AnalyticsMessageImpl();

		analyticsMessage.setNew(true);
		analyticsMessage.setPrimaryKey(analyticsMessageId);

		analyticsMessage.setCompanyId(CompanyThreadLocal.getCompanyId());

		return analyticsMessage;
	}

	/**
	 * Removes the analytics message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message that was removed
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	@Override
	public AnalyticsMessage remove(long analyticsMessageId)
		throws NoSuchMessageException {

		return remove((Serializable)analyticsMessageId);
	}

	/**
	 * Removes the analytics message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the analytics message
	 * @return the analytics message that was removed
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	@Override
	public AnalyticsMessage remove(Serializable primaryKey)
		throws NoSuchMessageException {

		Session session = null;

		try {
			session = openSession();

			AnalyticsMessage analyticsMessage = (AnalyticsMessage)session.get(
				AnalyticsMessageImpl.class, primaryKey);

			if (analyticsMessage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMessageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(analyticsMessage);
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
	protected AnalyticsMessage removeImpl(AnalyticsMessage analyticsMessage) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(analyticsMessage)) {
				analyticsMessage = (AnalyticsMessage)session.get(
					AnalyticsMessageImpl.class,
					analyticsMessage.getPrimaryKeyObj());
			}

			if (analyticsMessage != null) {
				session.delete(analyticsMessage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (analyticsMessage != null) {
			clearCache(analyticsMessage);
		}

		return analyticsMessage;
	}

	@Override
	public AnalyticsMessage updateImpl(AnalyticsMessage analyticsMessage) {
		boolean isNew = analyticsMessage.isNew();

		if (!(analyticsMessage instanceof AnalyticsMessageModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(analyticsMessage.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					analyticsMessage);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in analyticsMessage proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AnalyticsMessage implementation " +
					analyticsMessage.getClass());
		}

		AnalyticsMessageModelImpl analyticsMessageModelImpl =
			(AnalyticsMessageModelImpl)analyticsMessage;

		Session session = null;

		try {
			session = openSession();

			if (analyticsMessage.isNew()) {
				session.save(analyticsMessage);

				analyticsMessage.setNew(false);
			}
			else {
				session.evict(
					AnalyticsMessageImpl.class,
					analyticsMessage.getPrimaryKeyObj());

				session.saveOrUpdate(analyticsMessage);
			}

			session.flush();
			session.clear();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				analyticsMessageModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((analyticsMessageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					analyticsMessageModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {analyticsMessageModelImpl.getCompanyId()};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}
		}

		entityCache.putResult(
			AnalyticsMessageImpl.class, analyticsMessage.getPrimaryKey(),
			analyticsMessage, false);

		analyticsMessage.resetOriginalValues();

		return analyticsMessage;
	}

	/**
	 * Returns the analytics message with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the analytics message
	 * @return the analytics message
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	@Override
	public AnalyticsMessage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMessageException {

		AnalyticsMessage analyticsMessage = fetchByPrimaryKey(primaryKey);

		if (analyticsMessage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMessageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return analyticsMessage;
	}

	/**
	 * Returns the analytics message with the primary key or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	@Override
	public AnalyticsMessage findByPrimaryKey(long analyticsMessageId)
		throws NoSuchMessageException {

		return findByPrimaryKey((Serializable)analyticsMessageId);
	}

	/**
	 * Returns the analytics message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message, or <code>null</code> if a analytics message with the primary key could not be found
	 */
	@Override
	public AnalyticsMessage fetchByPrimaryKey(long analyticsMessageId) {
		return fetchByPrimaryKey((Serializable)analyticsMessageId);
	}

	/**
	 * Returns all the analytics messages.
	 *
	 * @return the analytics messages
	 */
	@Override
	public List<AnalyticsMessage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @return the range of analytics messages
	 */
	@Override
	public List<AnalyticsMessage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics messages
	 */
	@Override
	public List<AnalyticsMessage> findAll(
		int start, int end,
		OrderByComparator<AnalyticsMessage> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics messages
	 */
	@Override
	public List<AnalyticsMessage> findAll(
		int start, int end,
		OrderByComparator<AnalyticsMessage> orderByComparator,
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

		List<AnalyticsMessage> list = null;

		if (useFinderCache) {
			list = (List<AnalyticsMessage>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ANALYTICSMESSAGE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ANALYTICSMESSAGE;

				sql = sql.concat(AnalyticsMessageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AnalyticsMessage>)QueryUtil.list(
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
	 * Removes all the analytics messages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AnalyticsMessage analyticsMessage : findAll()) {
			remove(analyticsMessage);
		}
	}

	/**
	 * Returns the number of analytics messages.
	 *
	 * @return the number of analytics messages
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ANALYTICSMESSAGE);

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
		return "analyticsMessageId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ANALYTICSMESSAGE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AnalyticsMessageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the analytics message persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AnalyticsMessageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AnalyticsMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			AnalyticsMessageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			AnalyticsMessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			AnalyticsMessageModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AnalyticsMessageImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ANALYTICSMESSAGE =
		"SELECT analyticsMessage FROM AnalyticsMessage analyticsMessage";

	private static final String _SQL_SELECT_ANALYTICSMESSAGE_WHERE =
		"SELECT analyticsMessage FROM AnalyticsMessage analyticsMessage WHERE ";

	private static final String _SQL_COUNT_ANALYTICSMESSAGE =
		"SELECT COUNT(analyticsMessage) FROM AnalyticsMessage analyticsMessage";

	private static final String _SQL_COUNT_ANALYTICSMESSAGE_WHERE =
		"SELECT COUNT(analyticsMessage) FROM AnalyticsMessage analyticsMessage WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "analyticsMessage.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AnalyticsMessage exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AnalyticsMessage exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsMessagePersistenceImpl.class);

	static {
		try {
			Class.forName(AnalyticsPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}