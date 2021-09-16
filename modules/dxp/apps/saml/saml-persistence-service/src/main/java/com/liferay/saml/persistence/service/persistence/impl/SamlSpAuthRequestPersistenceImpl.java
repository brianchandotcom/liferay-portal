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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.saml.persistence.exception.NoSuchSpAuthRequestException;
import com.liferay.saml.persistence.model.SamlSpAuthRequest;
import com.liferay.saml.persistence.model.SamlSpAuthRequestTable;
import com.liferay.saml.persistence.model.impl.SamlSpAuthRequestImpl;
import com.liferay.saml.persistence.model.impl.SamlSpAuthRequestModelImpl;
import com.liferay.saml.persistence.service.persistence.SamlSpAuthRequestPersistence;
import com.liferay.saml.persistence.service.persistence.impl.constants.SamlPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
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
 * The persistence implementation for the saml sp auth request service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @generated
 */
@Component(
	service = {SamlSpAuthRequestPersistence.class, BasePersistence.class}
)
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
	private FinderPath _finderPathWithPaginationFindByLtCreateDate;
	private FinderPath _finderPathWithPaginationCountByLtCreateDate;

	/**
	 * Returns all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the matching saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findByLtCreateDate(Date createDate) {
		return findByLtCreateDate(
			createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @return the range of matching saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findByLtCreateDate(
		Date createDate, int start, int end) {

		return findByLtCreateDate(createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> findByLtCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		return findByLtCreateDate(
			createDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>.
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
	public List<SamlSpAuthRequest> findByLtCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<SamlSpAuthRequest> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtCreateDate;
		finderArgs = new Object[] {
			_getTime(createDate), start, end, orderByComparator
		};

		List<SamlSpAuthRequest> list = null;

		if (useFinderCache) {
			list = (List<SamlSpAuthRequest>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SamlSpAuthRequest samlSpAuthRequest : list) {
					if (createDate.getTime() <= samlSpAuthRequest.getCreateDate(
						).getTime()) {

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

			sb.append(_SQL_SELECT_SAMLSPAUTHREQUEST_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SamlSpAuthRequestModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
				}

				list = (List<SamlSpAuthRequest>)QueryUtil.list(
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
	 * Returns the first saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a matching saml sp auth request could not be found
	 */
	@Override
	public SamlSpAuthRequest findByLtCreateDate_First(
			Date createDate,
			OrderByComparator<SamlSpAuthRequest> orderByComparator)
		throws NoSuchSpAuthRequestException {

		SamlSpAuthRequest samlSpAuthRequest = fetchByLtCreateDate_First(
			createDate, orderByComparator);

		if (samlSpAuthRequest != null) {
			return samlSpAuthRequest;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createDate<");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchSpAuthRequestException(sb.toString());
	}

	/**
	 * Returns the first saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	@Override
	public SamlSpAuthRequest fetchByLtCreateDate_First(
		Date createDate,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		List<SamlSpAuthRequest> list = findByLtCreateDate(
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
	public SamlSpAuthRequest findByLtCreateDate_Last(
			Date createDate,
			OrderByComparator<SamlSpAuthRequest> orderByComparator)
		throws NoSuchSpAuthRequestException {

		SamlSpAuthRequest samlSpAuthRequest = fetchByLtCreateDate_Last(
			createDate, orderByComparator);

		if (samlSpAuthRequest != null) {
			return samlSpAuthRequest;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createDate<");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchSpAuthRequestException(sb.toString());
	}

	/**
	 * Returns the last saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	@Override
	public SamlSpAuthRequest fetchByLtCreateDate_Last(
		Date createDate,
		OrderByComparator<SamlSpAuthRequest> orderByComparator) {

		int count = countByLtCreateDate(createDate);

		if (count == 0) {
			return null;
		}

		List<SamlSpAuthRequest> list = findByLtCreateDate(
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
	public SamlSpAuthRequest[] findByLtCreateDate_PrevAndNext(
			long samlSpAuthnRequestId, Date createDate,
			OrderByComparator<SamlSpAuthRequest> orderByComparator)
		throws NoSuchSpAuthRequestException {

		SamlSpAuthRequest samlSpAuthRequest = findByPrimaryKey(
			samlSpAuthnRequestId);

		Session session = null;

		try {
			session = openSession();

			SamlSpAuthRequest[] array = new SamlSpAuthRequestImpl[3];

			array[0] = getByLtCreateDate_PrevAndNext(
				session, samlSpAuthRequest, createDate, orderByComparator,
				true);

			array[1] = samlSpAuthRequest;

			array[2] = getByLtCreateDate_PrevAndNext(
				session, samlSpAuthRequest, createDate, orderByComparator,
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

	protected SamlSpAuthRequest getByLtCreateDate_PrevAndNext(
		Session session, SamlSpAuthRequest samlSpAuthRequest, Date createDate,
		OrderByComparator<SamlSpAuthRequest> orderByComparator,
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

		sb.append(_SQL_SELECT_SAMLSPAUTHREQUEST_WHERE);

		boolean bindCreateDate = false;

		if (createDate == null) {
			sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_2);
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
			sb.append(SamlSpAuthRequestModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindCreateDate) {
			queryPos.add(new Timestamp(createDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						samlSpAuthRequest)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SamlSpAuthRequest> list = query.list();

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
	public void removeByLtCreateDate(Date createDate) {
		for (SamlSpAuthRequest samlSpAuthRequest :
				findByLtCreateDate(
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
	public int countByLtCreateDate(Date createDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtCreateDate;

		Object[] finderArgs = new Object[] {_getTime(createDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SAMLSPAUTHREQUEST_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
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

	private static final String _FINDER_COLUMN_LTCREATEDATE_CREATEDATE_1 =
		"samlSpAuthRequest.createDate IS NULL";

	private static final String _FINDER_COLUMN_LTCREATEDATE_CREATEDATE_2 =
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
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("samlIdpEntityId=");
			sb.append(samlIdpEntityId);

			sb.append(", samlSpAuthRequestKey=");
			sb.append(samlSpAuthRequestKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSpAuthRequestException(sb.toString());
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
				_finderPathFetchBySIEI_SSARK, finderArgs);
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
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SAMLSPAUTHREQUEST_WHERE);

			boolean bindSamlIdpEntityId = false;

			if (samlIdpEntityId.isEmpty()) {
				sb.append(_FINDER_COLUMN_SIEI_SSARK_SAMLIDPENTITYID_3);
			}
			else {
				bindSamlIdpEntityId = true;

				sb.append(_FINDER_COLUMN_SIEI_SSARK_SAMLIDPENTITYID_2);
			}

			boolean bindSamlSpAuthRequestKey = false;

			if (samlSpAuthRequestKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_SIEI_SSARK_SAMLSPAUTHREQUESTKEY_3);
			}
			else {
				bindSamlSpAuthRequestKey = true;

				sb.append(_FINDER_COLUMN_SIEI_SSARK_SAMLSPAUTHREQUESTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSamlIdpEntityId) {
					queryPos.add(samlIdpEntityId);
				}

				if (bindSamlSpAuthRequestKey) {
					queryPos.add(samlSpAuthRequestKey);
				}

				List<SamlSpAuthRequest> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SAMLSPAUTHREQUEST_WHERE);

			boolean bindSamlIdpEntityId = false;

			if (samlIdpEntityId.isEmpty()) {
				sb.append(_FINDER_COLUMN_SIEI_SSARK_SAMLIDPENTITYID_3);
			}
			else {
				bindSamlIdpEntityId = true;

				sb.append(_FINDER_COLUMN_SIEI_SSARK_SAMLIDPENTITYID_2);
			}

			boolean bindSamlSpAuthRequestKey = false;

			if (samlSpAuthRequestKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_SIEI_SSARK_SAMLSPAUTHREQUESTKEY_3);
			}
			else {
				bindSamlSpAuthRequestKey = true;

				sb.append(_FINDER_COLUMN_SIEI_SSARK_SAMLSPAUTHREQUESTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSamlIdpEntityId) {
					queryPos.add(samlIdpEntityId);
				}

				if (bindSamlSpAuthRequestKey) {
					queryPos.add(samlSpAuthRequestKey);
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

		setTable(SamlSpAuthRequestTable.INSTANCE);
	}

	/**
	 * Caches the saml sp auth request in the entity cache if it is enabled.
	 *
	 * @param samlSpAuthRequest the saml sp auth request
	 */
	@Override
	public void cacheResult(SamlSpAuthRequest samlSpAuthRequest) {
		entityCache.putResult(
			SamlSpAuthRequestImpl.class, samlSpAuthRequest.getPrimaryKey(),
			samlSpAuthRequest);

		finderCache.putResult(
			_finderPathFetchBySIEI_SSARK,
			new Object[] {
				samlSpAuthRequest.getSamlIdpEntityId(),
				samlSpAuthRequest.getSamlSpAuthRequestKey()
			},
			samlSpAuthRequest);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the saml sp auth requests in the entity cache if it is enabled.
	 *
	 * @param samlSpAuthRequests the saml sp auth requests
	 */
	@Override
	public void cacheResult(List<SamlSpAuthRequest> samlSpAuthRequests) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (samlSpAuthRequests.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SamlSpAuthRequest samlSpAuthRequest : samlSpAuthRequests) {
			if (entityCache.getResult(
					SamlSpAuthRequestImpl.class,
					samlSpAuthRequest.getPrimaryKey()) == null) {

				cacheResult(samlSpAuthRequest);
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

		finderCache.clearCache(SamlSpAuthRequestImpl.class);
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
			SamlSpAuthRequestImpl.class, samlSpAuthRequest);
	}

	@Override
	public void clearCache(List<SamlSpAuthRequest> samlSpAuthRequests) {
		for (SamlSpAuthRequest samlSpAuthRequest : samlSpAuthRequests) {
			entityCache.removeResult(
				SamlSpAuthRequestImpl.class, samlSpAuthRequest);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(SamlSpAuthRequestImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SamlSpAuthRequestImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SamlSpAuthRequestModelImpl samlSpAuthRequestModelImpl) {

		Object[] args = new Object[] {
			samlSpAuthRequestModelImpl.getSamlIdpEntityId(),
			samlSpAuthRequestModelImpl.getSamlSpAuthRequestKey()
		};

		finderCache.putResult(
			_finderPathCountBySIEI_SSARK, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchBySIEI_SSARK, args, samlSpAuthRequestModelImpl);
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
		catch (NoSuchSpAuthRequestException noSuchEntityException) {
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
		catch (Exception exception) {
			throw processException(exception);
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

		if (isNew && (samlSpAuthRequest.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				samlSpAuthRequest.setCreateDate(date);
			}
			else {
				samlSpAuthRequest.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(samlSpAuthRequest);
			}
			else {
				samlSpAuthRequest = (SamlSpAuthRequest)session.merge(
					samlSpAuthRequest);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			SamlSpAuthRequestImpl.class, samlSpAuthRequestModelImpl, false,
			true);

		cacheUniqueFindersCache(samlSpAuthRequestModelImpl);

		if (isNew) {
			samlSpAuthRequest.setNew(false);
		}

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>.
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

		List<SamlSpAuthRequest> list = null;

		if (useFinderCache) {
			list = (List<SamlSpAuthRequest>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SAMLSPAUTHREQUEST);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SAMLSPAUTHREQUEST;

				sql = sql.concat(SamlSpAuthRequestModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SamlSpAuthRequest>)QueryUtil.list(
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
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SAMLSPAUTHREQUEST);

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

		_finderPathWithPaginationFindByLtCreateDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtCreateDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"createDate"}, true);

		_finderPathWithPaginationCountByLtCreateDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtCreateDate",
			new String[] {Date.class.getName()}, new String[] {"createDate"},
			false);

		_finderPathFetchBySIEI_SSARK = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchBySIEI_SSARK",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"samlIdpEntityId", "samlSpAuthRequestKey"}, true);

		_finderPathCountBySIEI_SSARK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySIEI_SSARK",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"samlIdpEntityId", "samlSpAuthRequestKey"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SamlSpAuthRequestImpl.class.getName());
	}

	@Override
	@Reference(
		target = SamlPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SamlPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SamlPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
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

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private SamlSpAuthRequestModelArgumentsResolver
		_samlSpAuthRequestModelArgumentsResolver;

}