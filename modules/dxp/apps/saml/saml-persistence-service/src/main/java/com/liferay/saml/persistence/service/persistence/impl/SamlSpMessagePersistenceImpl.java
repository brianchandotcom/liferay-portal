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

package com.liferay.saml.persistence.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.saml.persistence.exception.NoSuchSpMessageException;
import com.liferay.saml.persistence.model.SamlSpMessage;
import com.liferay.saml.persistence.model.impl.SamlSpMessageImpl;
import com.liferay.saml.persistence.model.impl.SamlSpMessageModelImpl;
import com.liferay.saml.persistence.service.persistence.SamlSpMessagePersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence implementation for the saml sp message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @generated
 */
@ProviderType
public class SamlSpMessagePersistenceImpl
	extends BasePersistenceImpl<SamlSpMessage>
	implements SamlSpMessagePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SamlSpMessageUtil</code> to access the saml sp message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SamlSpMessageImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByExpirationDate;
	private FinderPath _finderPathWithPaginationCountByExpirationDate;

	/**
	 * Returns all the saml sp messages where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching saml sp messages
	 */
	@Override
	public List<SamlSpMessage> findByExpirationDate(Date expirationDate) {
		return findByExpirationDate(
			expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml sp messages where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @return the range of matching saml sp messages
	 */
	@Override
	public List<SamlSpMessage> findByExpirationDate(
		Date expirationDate, int start, int end) {

		return findByExpirationDate(expirationDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml sp messages where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp messages
	 */
	@Override
	public List<SamlSpMessage> findByExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<SamlSpMessage> orderByComparator) {

		return findByExpirationDate(
			expirationDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml sp messages where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml sp messages
	 */
	@Override
	public List<SamlSpMessage> findByExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<SamlSpMessage> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByExpirationDate;
		finderArgs = new Object[] {
			_getTime(expirationDate), start, end, orderByComparator
		};

		List<SamlSpMessage> list = null;

		if (useFinderCache) {
			list = (List<SamlSpMessage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SamlSpMessage samlSpMessage : list) {
					if ((expirationDate.getTime() <=
							samlSpMessage.getExpirationDate().getTime())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMLSPMESSAGE_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(SamlSpMessageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindExpirationDate) {
					qPos.add(new Timestamp(expirationDate.getTime()));
				}

				if (!pagination) {
					list = (List<SamlSpMessage>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlSpMessage>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp message
	 * @throws NoSuchSpMessageException if a matching saml sp message could not be found
	 */
	@Override
	public SamlSpMessage findByExpirationDate_First(
			Date expirationDate,
			OrderByComparator<SamlSpMessage> orderByComparator)
		throws NoSuchSpMessageException {

		SamlSpMessage samlSpMessage = fetchByExpirationDate_First(
			expirationDate, orderByComparator);

		if (samlSpMessage != null) {
			return samlSpMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("expirationDate=");
		msg.append(expirationDate);

		msg.append("}");

		throw new NoSuchSpMessageException(msg.toString());
	}

	/**
	 * Returns the first saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	@Override
	public SamlSpMessage fetchByExpirationDate_First(
		Date expirationDate,
		OrderByComparator<SamlSpMessage> orderByComparator) {

		List<SamlSpMessage> list = findByExpirationDate(
			expirationDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp message
	 * @throws NoSuchSpMessageException if a matching saml sp message could not be found
	 */
	@Override
	public SamlSpMessage findByExpirationDate_Last(
			Date expirationDate,
			OrderByComparator<SamlSpMessage> orderByComparator)
		throws NoSuchSpMessageException {

		SamlSpMessage samlSpMessage = fetchByExpirationDate_Last(
			expirationDate, orderByComparator);

		if (samlSpMessage != null) {
			return samlSpMessage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("expirationDate=");
		msg.append(expirationDate);

		msg.append("}");

		throw new NoSuchSpMessageException(msg.toString());
	}

	/**
	 * Returns the last saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	@Override
	public SamlSpMessage fetchByExpirationDate_Last(
		Date expirationDate,
		OrderByComparator<SamlSpMessage> orderByComparator) {

		int count = countByExpirationDate(expirationDate);

		if (count == 0) {
			return null;
		}

		List<SamlSpMessage> list = findByExpirationDate(
			expirationDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the saml sp messages before and after the current saml sp message in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param samlSpMessageId the primary key of the current saml sp message
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp message
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	@Override
	public SamlSpMessage[] findByExpirationDate_PrevAndNext(
			long samlSpMessageId, Date expirationDate,
			OrderByComparator<SamlSpMessage> orderByComparator)
		throws NoSuchSpMessageException {

		SamlSpMessage samlSpMessage = findByPrimaryKey(samlSpMessageId);

		Session session = null;

		try {
			session = openSession();

			SamlSpMessage[] array = new SamlSpMessageImpl[3];

			array[0] = getByExpirationDate_PrevAndNext(
				session, samlSpMessage, expirationDate, orderByComparator,
				true);

			array[1] = samlSpMessage;

			array[2] = getByExpirationDate_PrevAndNext(
				session, samlSpMessage, expirationDate, orderByComparator,
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

	protected SamlSpMessage getByExpirationDate_PrevAndNext(
		Session session, SamlSpMessage samlSpMessage, Date expirationDate,
		OrderByComparator<SamlSpMessage> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMLSPMESSAGE_WHERE);

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(SamlSpMessageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindExpirationDate) {
			qPos.add(new Timestamp(expirationDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						samlSpMessage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SamlSpMessage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the saml sp messages where expirationDate &lt; &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	@Override
	public void removeByExpirationDate(Date expirationDate) {
		for (SamlSpMessage samlSpMessage :
				findByExpirationDate(
					expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(samlSpMessage);
		}
	}

	/**
	 * Returns the number of saml sp messages where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching saml sp messages
	 */
	@Override
	public int countByExpirationDate(Date expirationDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByExpirationDate;

		Object[] finderArgs = new Object[] {_getTime(expirationDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMLSPMESSAGE_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindExpirationDate) {
					qPos.add(new Timestamp(expirationDate.getTime()));
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

	private static final String _FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1 =
		"samlSpMessage.expirationDate IS NULL";

	private static final String _FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2 =
		"samlSpMessage.expirationDate < ?";

	private FinderPath _finderPathFetchBySIEI_SIRK;
	private FinderPath _finderPathCountBySIEI_SIRK;

	/**
	 * Returns the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; or throws a <code>NoSuchSpMessageException</code> if it could not be found.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the matching saml sp message
	 * @throws NoSuchSpMessageException if a matching saml sp message could not be found
	 */
	@Override
	public SamlSpMessage findBySIEI_SIRK(
			String samlIdpEntityId, String samlIdpResponseKey)
		throws NoSuchSpMessageException {

		SamlSpMessage samlSpMessage = fetchBySIEI_SIRK(
			samlIdpEntityId, samlIdpResponseKey);

		if (samlSpMessage == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("samlIdpEntityId=");
			msg.append(samlIdpEntityId);

			msg.append(", samlIdpResponseKey=");
			msg.append(samlIdpResponseKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSpMessageException(msg.toString());
		}

		return samlSpMessage;
	}

	/**
	 * Returns the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	@Override
	public SamlSpMessage fetchBySIEI_SIRK(
		String samlIdpEntityId, String samlIdpResponseKey) {

		return fetchBySIEI_SIRK(samlIdpEntityId, samlIdpResponseKey, true);
	}

	/**
	 * Returns the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp message, or <code>null</code> if a matching saml sp message could not be found
	 */
	@Override
	public SamlSpMessage fetchBySIEI_SIRK(
		String samlIdpEntityId, String samlIdpResponseKey,
		boolean useFinderCache) {

		samlIdpEntityId = Objects.toString(samlIdpEntityId, "");
		samlIdpResponseKey = Objects.toString(samlIdpResponseKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {samlIdpEntityId, samlIdpResponseKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchBySIEI_SIRK, finderArgs, this);
		}

		if (result instanceof SamlSpMessage) {
			SamlSpMessage samlSpMessage = (SamlSpMessage)result;

			if (!Objects.equals(
					samlIdpEntityId, samlSpMessage.getSamlIdpEntityId()) ||
				!Objects.equals(
					samlIdpResponseKey,
					samlSpMessage.getSamlIdpResponseKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SAMLSPMESSAGE_WHERE);

			boolean bindSamlIdpEntityId = false;

			if (samlIdpEntityId.isEmpty()) {
				query.append(_FINDER_COLUMN_SIEI_SIRK_SAMLIDPENTITYID_3);
			}
			else {
				bindSamlIdpEntityId = true;

				query.append(_FINDER_COLUMN_SIEI_SIRK_SAMLIDPENTITYID_2);
			}

			boolean bindSamlIdpResponseKey = false;

			if (samlIdpResponseKey.isEmpty()) {
				query.append(_FINDER_COLUMN_SIEI_SIRK_SAMLIDPRESPONSEKEY_3);
			}
			else {
				bindSamlIdpResponseKey = true;

				query.append(_FINDER_COLUMN_SIEI_SIRK_SAMLIDPRESPONSEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamlIdpEntityId) {
					qPos.add(samlIdpEntityId);
				}

				if (bindSamlIdpResponseKey) {
					qPos.add(samlIdpResponseKey);
				}

				List<SamlSpMessage> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchBySIEI_SIRK, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									samlIdpEntityId, samlIdpResponseKey
								};
							}

							_log.warn(
								"SamlSpMessagePersistenceImpl.fetchBySIEI_SIRK(String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SamlSpMessage samlSpMessage = list.get(0);

					result = samlSpMessage;

					cacheResult(samlSpMessage);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchBySIEI_SIRK, finderArgs);
				}

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
			return (SamlSpMessage)result;
		}
	}

	/**
	 * Removes the saml sp message where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63; from the database.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the saml sp message that was removed
	 */
	@Override
	public SamlSpMessage removeBySIEI_SIRK(
			String samlIdpEntityId, String samlIdpResponseKey)
		throws NoSuchSpMessageException {

		SamlSpMessage samlSpMessage = findBySIEI_SIRK(
			samlIdpEntityId, samlIdpResponseKey);

		return remove(samlSpMessage);
	}

	/**
	 * Returns the number of saml sp messages where samlIdpEntityId = &#63; and samlIdpResponseKey = &#63;.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlIdpResponseKey the saml idp response key
	 * @return the number of matching saml sp messages
	 */
	@Override
	public int countBySIEI_SIRK(
		String samlIdpEntityId, String samlIdpResponseKey) {

		samlIdpEntityId = Objects.toString(samlIdpEntityId, "");
		samlIdpResponseKey = Objects.toString(samlIdpResponseKey, "");

		FinderPath finderPath = _finderPathCountBySIEI_SIRK;

		Object[] finderArgs = new Object[] {
			samlIdpEntityId, samlIdpResponseKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMLSPMESSAGE_WHERE);

			boolean bindSamlIdpEntityId = false;

			if (samlIdpEntityId.isEmpty()) {
				query.append(_FINDER_COLUMN_SIEI_SIRK_SAMLIDPENTITYID_3);
			}
			else {
				bindSamlIdpEntityId = true;

				query.append(_FINDER_COLUMN_SIEI_SIRK_SAMLIDPENTITYID_2);
			}

			boolean bindSamlIdpResponseKey = false;

			if (samlIdpResponseKey.isEmpty()) {
				query.append(_FINDER_COLUMN_SIEI_SIRK_SAMLIDPRESPONSEKEY_3);
			}
			else {
				bindSamlIdpResponseKey = true;

				query.append(_FINDER_COLUMN_SIEI_SIRK_SAMLIDPRESPONSEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamlIdpEntityId) {
					qPos.add(samlIdpEntityId);
				}

				if (bindSamlIdpResponseKey) {
					qPos.add(samlIdpResponseKey);
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

	private static final String _FINDER_COLUMN_SIEI_SIRK_SAMLIDPENTITYID_2 =
		"samlSpMessage.samlIdpEntityId = ? AND ";

	private static final String _FINDER_COLUMN_SIEI_SIRK_SAMLIDPENTITYID_3 =
		"(samlSpMessage.samlIdpEntityId IS NULL OR samlSpMessage.samlIdpEntityId = '') AND ";

	private static final String _FINDER_COLUMN_SIEI_SIRK_SAMLIDPRESPONSEKEY_2 =
		"samlSpMessage.samlIdpResponseKey = ?";

	private static final String _FINDER_COLUMN_SIEI_SIRK_SAMLIDPRESPONSEKEY_3 =
		"(samlSpMessage.samlIdpResponseKey IS NULL OR samlSpMessage.samlIdpResponseKey = '')";

	public SamlSpMessagePersistenceImpl() {
		setModelClass(SamlSpMessage.class);

		setModelImplClass(SamlSpMessageImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the saml sp message in the entity cache if it is enabled.
	 *
	 * @param samlSpMessage the saml sp message
	 */
	@Override
	public void cacheResult(SamlSpMessage samlSpMessage) {
		entityCache.putResult(
			SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpMessageImpl.class, samlSpMessage.getPrimaryKey(),
			samlSpMessage);

		finderCache.putResult(
			_finderPathFetchBySIEI_SIRK,
			new Object[] {
				samlSpMessage.getSamlIdpEntityId(),
				samlSpMessage.getSamlIdpResponseKey()
			},
			samlSpMessage);

		samlSpMessage.resetOriginalValues();
	}

	/**
	 * Caches the saml sp messages in the entity cache if it is enabled.
	 *
	 * @param samlSpMessages the saml sp messages
	 */
	@Override
	public void cacheResult(List<SamlSpMessage> samlSpMessages) {
		for (SamlSpMessage samlSpMessage : samlSpMessages) {
			if (entityCache.getResult(
					SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
					SamlSpMessageImpl.class, samlSpMessage.getPrimaryKey()) ==
						null) {

				cacheResult(samlSpMessage);
			}
			else {
				samlSpMessage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all saml sp messages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SamlSpMessageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the saml sp message.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SamlSpMessage samlSpMessage) {
		entityCache.removeResult(
			SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpMessageImpl.class, samlSpMessage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SamlSpMessageModelImpl)samlSpMessage, true);
	}

	@Override
	public void clearCache(List<SamlSpMessage> samlSpMessages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SamlSpMessage samlSpMessage : samlSpMessages) {
			entityCache.removeResult(
				SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
				SamlSpMessageImpl.class, samlSpMessage.getPrimaryKey());

			clearUniqueFindersCache(
				(SamlSpMessageModelImpl)samlSpMessage, true);
		}
	}

	protected void cacheUniqueFindersCache(
		SamlSpMessageModelImpl samlSpMessageModelImpl) {

		Object[] args = new Object[] {
			samlSpMessageModelImpl.getSamlIdpEntityId(),
			samlSpMessageModelImpl.getSamlIdpResponseKey()
		};

		finderCache.putResult(
			_finderPathCountBySIEI_SIRK, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchBySIEI_SIRK, args, samlSpMessageModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SamlSpMessageModelImpl samlSpMessageModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				samlSpMessageModelImpl.getSamlIdpEntityId(),
				samlSpMessageModelImpl.getSamlIdpResponseKey()
			};

			finderCache.removeResult(_finderPathCountBySIEI_SIRK, args);
			finderCache.removeResult(_finderPathFetchBySIEI_SIRK, args);
		}

		if ((samlSpMessageModelImpl.getColumnBitmask() &
			 _finderPathFetchBySIEI_SIRK.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				samlSpMessageModelImpl.getOriginalSamlIdpEntityId(),
				samlSpMessageModelImpl.getOriginalSamlIdpResponseKey()
			};

			finderCache.removeResult(_finderPathCountBySIEI_SIRK, args);
			finderCache.removeResult(_finderPathFetchBySIEI_SIRK, args);
		}
	}

	/**
	 * Creates a new saml sp message with the primary key. Does not add the saml sp message to the database.
	 *
	 * @param samlSpMessageId the primary key for the new saml sp message
	 * @return the new saml sp message
	 */
	@Override
	public SamlSpMessage create(long samlSpMessageId) {
		SamlSpMessage samlSpMessage = new SamlSpMessageImpl();

		samlSpMessage.setNew(true);
		samlSpMessage.setPrimaryKey(samlSpMessageId);

		samlSpMessage.setCompanyId(CompanyThreadLocal.getCompanyId());

		return samlSpMessage;
	}

	/**
	 * Removes the saml sp message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpMessageId the primary key of the saml sp message
	 * @return the saml sp message that was removed
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	@Override
	public SamlSpMessage remove(long samlSpMessageId)
		throws NoSuchSpMessageException {

		return remove((Serializable)samlSpMessageId);
	}

	/**
	 * Removes the saml sp message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the saml sp message
	 * @return the saml sp message that was removed
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	@Override
	public SamlSpMessage remove(Serializable primaryKey)
		throws NoSuchSpMessageException {

		Session session = null;

		try {
			session = openSession();

			SamlSpMessage samlSpMessage = (SamlSpMessage)session.get(
				SamlSpMessageImpl.class, primaryKey);

			if (samlSpMessage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSpMessageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(samlSpMessage);
		}
		catch (NoSuchSpMessageException nsee) {
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
	protected SamlSpMessage removeImpl(SamlSpMessage samlSpMessage) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(samlSpMessage)) {
				samlSpMessage = (SamlSpMessage)session.get(
					SamlSpMessageImpl.class, samlSpMessage.getPrimaryKeyObj());
			}

			if (samlSpMessage != null) {
				session.delete(samlSpMessage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (samlSpMessage != null) {
			clearCache(samlSpMessage);
		}

		return samlSpMessage;
	}

	@Override
	public SamlSpMessage updateImpl(SamlSpMessage samlSpMessage) {
		boolean isNew = samlSpMessage.isNew();

		if (!(samlSpMessage instanceof SamlSpMessageModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(samlSpMessage.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					samlSpMessage);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in samlSpMessage proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SamlSpMessage implementation " +
					samlSpMessage.getClass());
		}

		SamlSpMessageModelImpl samlSpMessageModelImpl =
			(SamlSpMessageModelImpl)samlSpMessage;

		Session session = null;

		try {
			session = openSession();

			if (samlSpMessage.isNew()) {
				session.save(samlSpMessage);

				samlSpMessage.setNew(false);
			}
			else {
				samlSpMessage = (SamlSpMessage)session.merge(samlSpMessage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SamlSpMessageModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpMessageImpl.class, samlSpMessage.getPrimaryKey(),
			samlSpMessage, false);

		clearUniqueFindersCache(samlSpMessageModelImpl, false);
		cacheUniqueFindersCache(samlSpMessageModelImpl);

		samlSpMessage.resetOriginalValues();

		return samlSpMessage;
	}

	/**
	 * Returns the saml sp message with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the saml sp message
	 * @return the saml sp message
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	@Override
	public SamlSpMessage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSpMessageException {

		SamlSpMessage samlSpMessage = fetchByPrimaryKey(primaryKey);

		if (samlSpMessage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSpMessageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return samlSpMessage;
	}

	/**
	 * Returns the saml sp message with the primary key or throws a <code>NoSuchSpMessageException</code> if it could not be found.
	 *
	 * @param samlSpMessageId the primary key of the saml sp message
	 * @return the saml sp message
	 * @throws NoSuchSpMessageException if a saml sp message with the primary key could not be found
	 */
	@Override
	public SamlSpMessage findByPrimaryKey(long samlSpMessageId)
		throws NoSuchSpMessageException {

		return findByPrimaryKey((Serializable)samlSpMessageId);
	}

	/**
	 * Returns the saml sp message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlSpMessageId the primary key of the saml sp message
	 * @return the saml sp message, or <code>null</code> if a saml sp message with the primary key could not be found
	 */
	@Override
	public SamlSpMessage fetchByPrimaryKey(long samlSpMessageId) {
		return fetchByPrimaryKey((Serializable)samlSpMessageId);
	}

	/**
	 * Returns all the saml sp messages.
	 *
	 * @return the saml sp messages
	 */
	@Override
	public List<SamlSpMessage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml sp messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @return the range of saml sp messages
	 */
	@Override
	public List<SamlSpMessage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml sp messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml sp messages
	 */
	@Override
	public List<SamlSpMessage> findAll(
		int start, int end,
		OrderByComparator<SamlSpMessage> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml sp messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpMessageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp messages
	 * @param end the upper bound of the range of saml sp messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saml sp messages
	 */
	@Override
	public List<SamlSpMessage> findAll(
		int start, int end, OrderByComparator<SamlSpMessage> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<SamlSpMessage> list = null;

		if (useFinderCache) {
			list = (List<SamlSpMessage>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SAMLSPMESSAGE);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SAMLSPMESSAGE;

				if (pagination) {
					sql = sql.concat(SamlSpMessageModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SamlSpMessage>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlSpMessage>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the saml sp messages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SamlSpMessage samlSpMessage : findAll()) {
			remove(samlSpMessage);
		}
	}

	/**
	 * Returns the number of saml sp messages.
	 *
	 * @return the number of saml sp messages
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SAMLSPMESSAGE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
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
		return "samlSpMessageId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SAMLSPMESSAGE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SamlSpMessageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the saml sp message persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpMessageModelImpl.FINDER_CACHE_ENABLED,
			SamlSpMessageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpMessageModelImpl.FINDER_CACHE_ENABLED,
			SamlSpMessageImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByExpirationDate = new FinderPath(
			SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpMessageModelImpl.FINDER_CACHE_ENABLED,
			SamlSpMessageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByExpirationDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByExpirationDate = new FinderPath(
			SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByExpirationDate",
			new String[] {Date.class.getName()});

		_finderPathFetchBySIEI_SIRK = new FinderPath(
			SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpMessageModelImpl.FINDER_CACHE_ENABLED,
			SamlSpMessageImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchBySIEI_SIRK",
			new String[] {String.class.getName(), String.class.getName()},
			SamlSpMessageModelImpl.SAMLIDPENTITYID_COLUMN_BITMASK |
			SamlSpMessageModelImpl.SAMLIDPRESPONSEKEY_COLUMN_BITMASK);

		_finderPathCountBySIEI_SIRK = new FinderPath(
			SamlSpMessageModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpMessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySIEI_SIRK",
			new String[] {String.class.getName(), String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(SamlSpMessageImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_SAMLSPMESSAGE =
		"SELECT samlSpMessage FROM SamlSpMessage samlSpMessage";

	private static final String _SQL_SELECT_SAMLSPMESSAGE_WHERE =
		"SELECT samlSpMessage FROM SamlSpMessage samlSpMessage WHERE ";

	private static final String _SQL_COUNT_SAMLSPMESSAGE =
		"SELECT COUNT(samlSpMessage) FROM SamlSpMessage samlSpMessage";

	private static final String _SQL_COUNT_SAMLSPMESSAGE_WHERE =
		"SELECT COUNT(samlSpMessage) FROM SamlSpMessage samlSpMessage WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "samlSpMessage.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SamlSpMessage exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SamlSpMessage exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SamlSpMessagePersistenceImpl.class);

}