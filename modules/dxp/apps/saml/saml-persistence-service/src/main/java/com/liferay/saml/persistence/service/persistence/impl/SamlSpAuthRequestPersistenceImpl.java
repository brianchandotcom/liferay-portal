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
import com.liferay.saml.persistence.exception.NoSuchSpAuthRequestException;
import com.liferay.saml.persistence.model.SamlSpAuthRequest;
import com.liferay.saml.persistence.model.impl.SamlSpAuthRequestImpl;
import com.liferay.saml.persistence.model.impl.SamlSpAuthRequestModelImpl;
import com.liferay.saml.persistence.service.persistence.SamlSpAuthRequestPersistence;

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
 * The persistence implementation for the saml sp auth request service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @generated
 */
@ProviderType
public class SamlSpAuthRequestPersistenceImpl
	extends BasePersistenceImpl<SamlSpAuthRequest>
	implements SamlSpAuthRequestPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SamlSpAuthRequestUtil</code> to access the saml sp auth request persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SamlSpAuthRequestImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCreateDate;
	private FinderPath _finderPathWithPaginationCountByCreateDate;

	/**
	 * Returns all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the matching saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findByCreateDate(Date createDate) {
		return findByCreateDate(
			createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @return the range of matching saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findByCreateDate(
		Date createDate, int start, int end) {

		return findByCreateDate(createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findByCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		return findByCreateDate(
			createDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findByCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByCreateDate;
		finderArgs = new Object[] {
			_getTime(createDate), start, end, orderByComparator
		};

		List<SamlSpAuthRequest> list = null;

		if (useFinderCache) {
			list = (List<SamlSpAuthRequest>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SamlSpAuthRequest samlSpAuthRequest : list) {
					if ((createDate.getTime() <=
							samlSpAuthRequest.getCreateDate().getTime())) {

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

			query.append(_SQL_SELECT_SAMLSPAUTHREQUEST_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(SamlSpAuthRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindCreateDate) {
					qPos.add(new Timestamp(createDate.getTime()));
				}

				if (!pagination) {
					list = (List<SamlSpAuthRequest>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlSpAuthRequest>)QueryUtil.list(
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
	 * Returns the first saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a matching saml sp auth request could not be found
	 */
	@Override
	public SamlSpAuthRequest findByCreateDate_First(
			Date createDate,
			OrderByComparator<SamlSpAuthRequest> orderByComparator)
		throws NoSuchSpAuthRequestException {

		SamlSpAuthRequest samlSpAuthRequest = fetchByCreateDate_First(
			createDate, orderByComparator);

		if (samlSpAuthRequest != null) {
			return samlSpAuthRequest;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("createDate=");
		msg.append(createDate);

		msg.append("}");

		throw new NoSuchSpAuthRequestException(msg.toString());
	}

	/**
	 * Returns the first saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	@Override
	public SamlSpAuthRequest fetchByCreateDate_First(
		Date createDate,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		List<SamlSpAuthRequest> list = findByCreateDate(
			createDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a matching saml sp auth request could not be found
	 */
	@Override
	public SamlSpAuthRequest findByCreateDate_Last(
			Date createDate,
			OrderByComparator<SamlSpAuthRequest> orderByComparator)
		throws NoSuchSpAuthRequestException {

		SamlSpAuthRequest samlSpAuthRequest = fetchByCreateDate_Last(
			createDate, orderByComparator);

		if (samlSpAuthRequest != null) {
			return samlSpAuthRequest;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("createDate=");
		msg.append(createDate);

		msg.append("}");

		throw new NoSuchSpAuthRequestException(msg.toString());
	}

	/**
	 * Returns the last saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	@Override
	public SamlSpAuthRequest fetchByCreateDate_Last(
		Date createDate,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		int count = countByCreateDate(createDate);

		if (count == 0) {
			return null;
		}

		List<SamlSpAuthRequest> list = findByCreateDate(
			createDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the saml sp auth requests before and after the current saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param samlSpAuthnRequestId the primary key of the current saml sp auth request
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	@Override
	public SamlSpAuthRequest[] findByCreateDate_PrevAndNext(
			long samlSpAuthnRequestId, Date createDate,
			OrderByComparator<SamlSpAuthRequest> orderByComparator)
		throws NoSuchSpAuthRequestException {

		SamlSpAuthRequest samlSpAuthRequest = findByPrimaryKey(
			samlSpAuthnRequestId);

		Session session = null;

		try {
			session = openSession();

			SamlSpAuthRequest[] array = new SamlSpAuthRequestImpl[3];

			array[0] = getByCreateDate_PrevAndNext(
				session, samlSpAuthRequest, createDate, orderByComparator,
				true);

			array[1] = samlSpAuthRequest;

			array[2] = getByCreateDate_PrevAndNext(
				session, samlSpAuthRequest, createDate, orderByComparator,
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

	protected SamlSpAuthRequest getByCreateDate_PrevAndNext(
		Session session, SamlSpAuthRequest samlSpAuthRequest, Date createDate,
		OrderByComparator<SamlSpAuthRequest> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMLSPAUTHREQUEST_WHERE);

		boolean bindCreateDate = false;

		if (createDate == null) {
			query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
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
			query.append(SamlSpAuthRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindCreateDate) {
			qPos.add(new Timestamp(createDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						samlSpAuthRequest)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SamlSpAuthRequest> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the saml sp auth requests where createDate &lt; &#63; from the database.
	 *
	 * @param createDate the create date
	 */
	@Override
	public void removeByCreateDate(Date createDate) {
		for (SamlSpAuthRequest samlSpAuthRequest :
				findByCreateDate(
					createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(samlSpAuthRequest);
		}
	}

	/**
	 * Returns the number of saml sp auth requests where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the number of matching saml sp auth requests
	 */
	@Override
	public int countByCreateDate(Date createDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByCreateDate;

		Object[] finderArgs = new Object[] {_getTime(createDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMLSPAUTHREQUEST_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindCreateDate) {
					qPos.add(new Timestamp(createDate.getTime()));
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

	private static final String _FINDER_COLUMN_CREATEDATE_CREATEDATE_1 =
		"samlSpAuthRequest.createDate IS NULL";

	private static final String _FINDER_COLUMN_CREATEDATE_CREATEDATE_2 =
		"samlSpAuthRequest.createDate < ?";

	private FinderPath _finderPathFetchBySIEI_SSARK;
	private FinderPath _finderPathCountBySIEI_SSARK;

	/**
	 * Returns the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; or throws a <code>NoSuchSpAuthRequestException</code> if it could not be found.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the matching saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a matching saml sp auth request could not be found
	 */
	@Override
	public SamlSpAuthRequest findBySIEI_SSARK(
			String samlIdpEntityId, String samlSpAuthRequestKey)
		throws NoSuchSpAuthRequestException {

		SamlSpAuthRequest samlSpAuthRequest = fetchBySIEI_SSARK(
			samlIdpEntityId, samlSpAuthRequestKey);

		if (samlSpAuthRequest == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("samlIdpEntityId=");
			msg.append(samlIdpEntityId);

			msg.append(", samlSpAuthRequestKey=");
			msg.append(samlSpAuthRequestKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSpAuthRequestException(msg.toString());
		}

		return samlSpAuthRequest;
	}

	/**
	 * Returns the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	@Override
	public SamlSpAuthRequest fetchBySIEI_SSARK(
		String samlIdpEntityId, String samlSpAuthRequestKey) {

		return fetchBySIEI_SSARK(samlIdpEntityId, samlSpAuthRequestKey, true);
	}

	/**
	 * Returns the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	@Override
	public SamlSpAuthRequest fetchBySIEI_SSARK(
		String samlIdpEntityId, String samlSpAuthRequestKey,
		boolean useFinderCache) {

		samlIdpEntityId = Objects.toString(samlIdpEntityId, "");
		samlSpAuthRequestKey = Objects.toString(samlSpAuthRequestKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {samlIdpEntityId, samlSpAuthRequestKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchBySIEI_SSARK, finderArgs, this);
		}

		if (result instanceof SamlSpAuthRequest) {
			SamlSpAuthRequest samlSpAuthRequest = (SamlSpAuthRequest)result;

			if (!Objects.equals(
					samlIdpEntityId, samlSpAuthRequest.getSamlIdpEntityId()) ||
				!Objects.equals(
					samlSpAuthRequestKey,
					samlSpAuthRequest.getSamlSpAuthRequestKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SAMLSPAUTHREQUEST_WHERE);

			boolean bindSamlIdpEntityId = false;

			if (samlIdpEntityId.isEmpty()) {
				query.append(_FINDER_COLUMN_SIEI_SSARK_SAMLIDPENTITYID_3);
			}
			else {
				bindSamlIdpEntityId = true;

				query.append(_FINDER_COLUMN_SIEI_SSARK_SAMLIDPENTITYID_2);
			}

			boolean bindSamlSpAuthRequestKey = false;

			if (samlSpAuthRequestKey.isEmpty()) {
				query.append(_FINDER_COLUMN_SIEI_SSARK_SAMLSPAUTHREQUESTKEY_3);
			}
			else {
				bindSamlSpAuthRequestKey = true;

				query.append(_FINDER_COLUMN_SIEI_SSARK_SAMLSPAUTHREQUESTKEY_2);
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

				if (bindSamlSpAuthRequestKey) {
					qPos.add(samlSpAuthRequestKey);
				}

				List<SamlSpAuthRequest> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchBySIEI_SSARK, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									samlIdpEntityId, samlSpAuthRequestKey
								};
							}

							_log.warn(
								"SamlSpAuthRequestPersistenceImpl.fetchBySIEI_SSARK(String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SamlSpAuthRequest samlSpAuthRequest = list.get(0);

					result = samlSpAuthRequest;

					cacheResult(samlSpAuthRequest);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchBySIEI_SSARK, finderArgs);
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
			return (SamlSpAuthRequest)result;
		}
	}

	/**
	 * Removes the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; from the database.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the saml sp auth request that was removed
	 */
	@Override
	public SamlSpAuthRequest removeBySIEI_SSARK(
			String samlIdpEntityId, String samlSpAuthRequestKey)
		throws NoSuchSpAuthRequestException {

		SamlSpAuthRequest samlSpAuthRequest = findBySIEI_SSARK(
			samlIdpEntityId, samlSpAuthRequestKey);

		return remove(samlSpAuthRequest);
	}

	/**
	 * Returns the number of saml sp auth requests where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63;.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the number of matching saml sp auth requests
	 */
	@Override
	public int countBySIEI_SSARK(
		String samlIdpEntityId, String samlSpAuthRequestKey) {

		samlIdpEntityId = Objects.toString(samlIdpEntityId, "");
		samlSpAuthRequestKey = Objects.toString(samlSpAuthRequestKey, "");

		FinderPath finderPath = _finderPathCountBySIEI_SSARK;

		Object[] finderArgs = new Object[] {
			samlIdpEntityId, samlSpAuthRequestKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMLSPAUTHREQUEST_WHERE);

			boolean bindSamlIdpEntityId = false;

			if (samlIdpEntityId.isEmpty()) {
				query.append(_FINDER_COLUMN_SIEI_SSARK_SAMLIDPENTITYID_3);
			}
			else {
				bindSamlIdpEntityId = true;

				query.append(_FINDER_COLUMN_SIEI_SSARK_SAMLIDPENTITYID_2);
			}

			boolean bindSamlSpAuthRequestKey = false;

			if (samlSpAuthRequestKey.isEmpty()) {
				query.append(_FINDER_COLUMN_SIEI_SSARK_SAMLSPAUTHREQUESTKEY_3);
			}
			else {
				bindSamlSpAuthRequestKey = true;

				query.append(_FINDER_COLUMN_SIEI_SSARK_SAMLSPAUTHREQUESTKEY_2);
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

				if (bindSamlSpAuthRequestKey) {
					qPos.add(samlSpAuthRequestKey);
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

	private static final String _FINDER_COLUMN_SIEI_SSARK_SAMLIDPENTITYID_2 =
		"samlSpAuthRequest.samlIdpEntityId = ? AND ";

	private static final String _FINDER_COLUMN_SIEI_SSARK_SAMLIDPENTITYID_3 =
		"(samlSpAuthRequest.samlIdpEntityId IS NULL OR samlSpAuthRequest.samlIdpEntityId = '') AND ";

	private static final String
		_FINDER_COLUMN_SIEI_SSARK_SAMLSPAUTHREQUESTKEY_2 =
			"samlSpAuthRequest.samlSpAuthRequestKey = ?";

	private static final String
		_FINDER_COLUMN_SIEI_SSARK_SAMLSPAUTHREQUESTKEY_3 =
			"(samlSpAuthRequest.samlSpAuthRequestKey IS NULL OR samlSpAuthRequest.samlSpAuthRequestKey = '')";

	public SamlSpAuthRequestPersistenceImpl() {
		setModelClass(SamlSpAuthRequest.class);

		setModelImplClass(SamlSpAuthRequestImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the saml sp auth request in the entity cache if it is enabled.
	 *
	 * @param samlSpAuthRequest the saml sp auth request
	 */
	@Override
	public void cacheResult(SamlSpAuthRequest samlSpAuthRequest) {
		entityCache.putResult(
			SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpAuthRequestImpl.class, samlSpAuthRequest.getPrimaryKey(),
			samlSpAuthRequest);

		finderCache.putResult(
			_finderPathFetchBySIEI_SSARK,
			new Object[] {
				samlSpAuthRequest.getSamlIdpEntityId(),
				samlSpAuthRequest.getSamlSpAuthRequestKey()
			},
			samlSpAuthRequest);

		samlSpAuthRequest.resetOriginalValues();
	}

	/**
	 * Caches the saml sp auth requests in the entity cache if it is enabled.
	 *
	 * @param samlSpAuthRequests the saml sp auth requests
	 */
	@Override
	public void cacheResult(List<SamlSpAuthRequest> samlSpAuthRequests) {
		for (SamlSpAuthRequest samlSpAuthRequest : samlSpAuthRequests) {
			if (entityCache.getResult(
					SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
					SamlSpAuthRequestImpl.class,
					samlSpAuthRequest.getPrimaryKey()) == null) {

				cacheResult(samlSpAuthRequest);
			}
			else {
				samlSpAuthRequest.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all saml sp auth requests.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SamlSpAuthRequestImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the saml sp auth request.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SamlSpAuthRequest samlSpAuthRequest) {
		entityCache.removeResult(
			SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpAuthRequestImpl.class, samlSpAuthRequest.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SamlSpAuthRequestModelImpl)samlSpAuthRequest, true);
	}

	@Override
	public void clearCache(List<SamlSpAuthRequest> samlSpAuthRequests) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SamlSpAuthRequest samlSpAuthRequest : samlSpAuthRequests) {
			entityCache.removeResult(
				SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
				SamlSpAuthRequestImpl.class, samlSpAuthRequest.getPrimaryKey());

			clearUniqueFindersCache(
				(SamlSpAuthRequestModelImpl)samlSpAuthRequest, true);
		}
	}

	protected void cacheUniqueFindersCache(
		SamlSpAuthRequestModelImpl samlSpAuthRequestModelImpl) {

		Object[] args = new Object[] {
			samlSpAuthRequestModelImpl.getSamlIdpEntityId(),
			samlSpAuthRequestModelImpl.getSamlSpAuthRequestKey()
		};

		finderCache.putResult(
			_finderPathCountBySIEI_SSARK, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchBySIEI_SSARK, args, samlSpAuthRequestModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		SamlSpAuthRequestModelImpl samlSpAuthRequestModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				samlSpAuthRequestModelImpl.getSamlIdpEntityId(),
				samlSpAuthRequestModelImpl.getSamlSpAuthRequestKey()
			};

			finderCache.removeResult(_finderPathCountBySIEI_SSARK, args);
			finderCache.removeResult(_finderPathFetchBySIEI_SSARK, args);
		}

		if ((samlSpAuthRequestModelImpl.getColumnBitmask() &
			 _finderPathFetchBySIEI_SSARK.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				samlSpAuthRequestModelImpl.getOriginalSamlIdpEntityId(),
				samlSpAuthRequestModelImpl.getOriginalSamlSpAuthRequestKey()
			};

			finderCache.removeResult(_finderPathCountBySIEI_SSARK, args);
			finderCache.removeResult(_finderPathFetchBySIEI_SSARK, args);
		}
	}

	/**
	 * Creates a new saml sp auth request with the primary key. Does not add the saml sp auth request to the database.
	 *
	 * @param samlSpAuthnRequestId the primary key for the new saml sp auth request
	 * @return the new saml sp auth request
	 */
	@Override
	public SamlSpAuthRequest create(long samlSpAuthnRequestId) {
		SamlSpAuthRequest samlSpAuthRequest = new SamlSpAuthRequestImpl();

		samlSpAuthRequest.setNew(true);
		samlSpAuthRequest.setPrimaryKey(samlSpAuthnRequestId);

		samlSpAuthRequest.setCompanyId(CompanyThreadLocal.getCompanyId());

		return samlSpAuthRequest;
	}

	/**
	 * Removes the saml sp auth request with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request that was removed
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	@Override
	public SamlSpAuthRequest remove(long samlSpAuthnRequestId)
		throws NoSuchSpAuthRequestException {

		return remove((Serializable)samlSpAuthnRequestId);
	}

	/**
	 * Removes the saml sp auth request with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the saml sp auth request
	 * @return the saml sp auth request that was removed
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	@Override
	public SamlSpAuthRequest remove(Serializable primaryKey)
		throws NoSuchSpAuthRequestException {

		Session session = null;

		try {
			session = openSession();

			SamlSpAuthRequest samlSpAuthRequest =
				(SamlSpAuthRequest)session.get(
					SamlSpAuthRequestImpl.class, primaryKey);

			if (samlSpAuthRequest == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSpAuthRequestException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(samlSpAuthRequest);
		}
		catch (NoSuchSpAuthRequestException nsee) {
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
	protected SamlSpAuthRequest removeImpl(
		SamlSpAuthRequest samlSpAuthRequest) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(samlSpAuthRequest)) {
				samlSpAuthRequest = (SamlSpAuthRequest)session.get(
					SamlSpAuthRequestImpl.class,
					samlSpAuthRequest.getPrimaryKeyObj());
			}

			if (samlSpAuthRequest != null) {
				session.delete(samlSpAuthRequest);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (samlSpAuthRequest != null) {
			clearCache(samlSpAuthRequest);
		}

		return samlSpAuthRequest;
	}

	@Override
	public SamlSpAuthRequest updateImpl(SamlSpAuthRequest samlSpAuthRequest) {
		boolean isNew = samlSpAuthRequest.isNew();

		if (!(samlSpAuthRequest instanceof SamlSpAuthRequestModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(samlSpAuthRequest.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					samlSpAuthRequest);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in samlSpAuthRequest proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SamlSpAuthRequest implementation " +
					samlSpAuthRequest.getClass());
		}

		SamlSpAuthRequestModelImpl samlSpAuthRequestModelImpl =
			(SamlSpAuthRequestModelImpl)samlSpAuthRequest;

		Session session = null;

		try {
			session = openSession();

			if (samlSpAuthRequest.isNew()) {
				session.save(samlSpAuthRequest);

				samlSpAuthRequest.setNew(false);
			}
			else {
				samlSpAuthRequest = (SamlSpAuthRequest)session.merge(
					samlSpAuthRequest);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SamlSpAuthRequestModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpAuthRequestImpl.class, samlSpAuthRequest.getPrimaryKey(),
			samlSpAuthRequest, false);

		clearUniqueFindersCache(samlSpAuthRequestModelImpl, false);
		cacheUniqueFindersCache(samlSpAuthRequestModelImpl);

		samlSpAuthRequest.resetOriginalValues();

		return samlSpAuthRequest;
	}

	/**
	 * Returns the saml sp auth request with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the saml sp auth request
	 * @return the saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	@Override
	public SamlSpAuthRequest findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSpAuthRequestException {

		SamlSpAuthRequest samlSpAuthRequest = fetchByPrimaryKey(primaryKey);

		if (samlSpAuthRequest == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSpAuthRequestException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return samlSpAuthRequest;
	}

	/**
	 * Returns the saml sp auth request with the primary key or throws a <code>NoSuchSpAuthRequestException</code> if it could not be found.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	@Override
	public SamlSpAuthRequest findByPrimaryKey(long samlSpAuthnRequestId)
		throws NoSuchSpAuthRequestException {

		return findByPrimaryKey((Serializable)samlSpAuthnRequestId);
	}

	/**
	 * Returns the saml sp auth request with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request, or <code>null</code> if a saml sp auth request with the primary key could not be found
	 */
	@Override
	public SamlSpAuthRequest fetchByPrimaryKey(long samlSpAuthnRequestId) {
		return fetchByPrimaryKey((Serializable)samlSpAuthnRequestId);
	}

	/**
	 * Returns all the saml sp auth requests.
	 *
	 * @return the saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml sp auth requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @return the range of saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml sp auth requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findAll(
		int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml sp auth requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findAll(
		int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator,
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

		List<SamlSpAuthRequest> list = null;

		if (useFinderCache) {
			list = (List<SamlSpAuthRequest>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SAMLSPAUTHREQUEST);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SAMLSPAUTHREQUEST;

				if (pagination) {
					sql = sql.concat(SamlSpAuthRequestModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SamlSpAuthRequest>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlSpAuthRequest>)QueryUtil.list(
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
	 * Removes all the saml sp auth requests from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SamlSpAuthRequest samlSpAuthRequest : findAll()) {
			remove(samlSpAuthRequest);
		}
	}

	/**
	 * Returns the number of saml sp auth requests.
	 *
	 * @return the number of saml sp auth requests
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SAMLSPAUTHREQUEST);

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
		return "samlSpAuthnRequestId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SAMLSPAUTHREQUEST;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SamlSpAuthRequestModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the saml sp auth request persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpAuthRequestModelImpl.FINDER_CACHE_ENABLED,
			SamlSpAuthRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpAuthRequestModelImpl.FINDER_CACHE_ENABLED,
			SamlSpAuthRequestImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpAuthRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCreateDate = new FinderPath(
			SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpAuthRequestModelImpl.FINDER_CACHE_ENABLED,
			SamlSpAuthRequestImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCreateDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByCreateDate = new FinderPath(
			SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpAuthRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByCreateDate",
			new String[] {Date.class.getName()});

		_finderPathFetchBySIEI_SSARK = new FinderPath(
			SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpAuthRequestModelImpl.FINDER_CACHE_ENABLED,
			SamlSpAuthRequestImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchBySIEI_SSARK",
			new String[] {String.class.getName(), String.class.getName()},
			SamlSpAuthRequestModelImpl.SAMLIDPENTITYID_COLUMN_BITMASK |
			SamlSpAuthRequestModelImpl.SAMLSPAUTHREQUESTKEY_COLUMN_BITMASK);

		_finderPathCountBySIEI_SSARK = new FinderPath(
			SamlSpAuthRequestModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpAuthRequestModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySIEI_SSARK",
			new String[] {String.class.getName(), String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(SamlSpAuthRequestImpl.class.getName());
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

	private static final String _SQL_SELECT_SAMLSPAUTHREQUEST =
		"SELECT samlSpAuthRequest FROM SamlSpAuthRequest samlSpAuthRequest";

	private static final String _SQL_SELECT_SAMLSPAUTHREQUEST_WHERE =
		"SELECT samlSpAuthRequest FROM SamlSpAuthRequest samlSpAuthRequest WHERE ";

	private static final String _SQL_COUNT_SAMLSPAUTHREQUEST =
		"SELECT COUNT(samlSpAuthRequest) FROM SamlSpAuthRequest samlSpAuthRequest";

	private static final String _SQL_COUNT_SAMLSPAUTHREQUEST_WHERE =
		"SELECT COUNT(samlSpAuthRequest) FROM SamlSpAuthRequest samlSpAuthRequest WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "samlSpAuthRequest.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SamlSpAuthRequest exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SamlSpAuthRequest exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SamlSpAuthRequestPersistenceImpl.class);

}