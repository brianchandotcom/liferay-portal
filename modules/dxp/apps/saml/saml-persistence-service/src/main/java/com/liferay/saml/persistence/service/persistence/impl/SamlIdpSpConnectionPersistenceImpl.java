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
import com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlIdpSpConnectionTable;
import com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionImpl;
import com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionModelImpl;
import com.liferay.saml.persistence.service.persistence.SamlIdpSpConnectionPersistence;
import com.liferay.saml.persistence.service.persistence.impl.constants.SamlPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the saml idp sp connection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @generated
 */
@Component(
	service = {SamlIdpSpConnectionPersistence.class, BasePersistence.class}
)
public class SamlIdpSpConnectionPersistenceImpl
	extends BasePersistenceImpl<SamlIdpSpConnection>
	implements SamlIdpSpConnectionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SamlIdpSpConnectionUtil</code> to access the saml idp sp connection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SamlIdpSpConnectionImpl.class.getName();

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
	 * Returns all the saml idp sp connections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml idp sp connections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpConnectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @return the range of matching saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml idp sp connections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpConnectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml idp sp connections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpConnectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator,
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

		List<SamlIdpSpConnection> list = null;

		if (useFinderCache) {
			list = (List<SamlIdpSpConnection>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SamlIdpSpConnection samlIdpSpConnection : list) {
					if (companyId != samlIdpSpConnection.getCompanyId()) {
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

			sb.append(_SQL_SELECT_SAMLIDPSPCONNECTION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SamlIdpSpConnectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<SamlIdpSpConnection>)QueryUtil.list(
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
	 * Returns the first saml idp sp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection findByCompanyId_First(
			long companyId,
			OrderByComparator<SamlIdpSpConnection> orderByComparator)
		throws NoSuchIdpSpConnectionException {

		SamlIdpSpConnection samlIdpSpConnection = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (samlIdpSpConnection != null) {
			return samlIdpSpConnection;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchIdpSpConnectionException(sb.toString());
	}

	/**
	 * Returns the first saml idp sp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByCompanyId_First(
		long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {

		List<SamlIdpSpConnection> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last saml idp sp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection findByCompanyId_Last(
			long companyId,
			OrderByComparator<SamlIdpSpConnection> orderByComparator)
		throws NoSuchIdpSpConnectionException {

		SamlIdpSpConnection samlIdpSpConnection = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (samlIdpSpConnection != null) {
			return samlIdpSpConnection;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchIdpSpConnectionException(sb.toString());
	}

	/**
	 * Returns the last saml idp sp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SamlIdpSpConnection> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the saml idp sp connections before and after the current saml idp sp connection in the ordered set where companyId = &#63;.
	 *
	 * @param samlIdpSpConnectionId the primary key of the current saml idp sp connection
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection[] findByCompanyId_PrevAndNext(
			long samlIdpSpConnectionId, long companyId,
			OrderByComparator<SamlIdpSpConnection> orderByComparator)
		throws NoSuchIdpSpConnectionException {

		SamlIdpSpConnection samlIdpSpConnection = findByPrimaryKey(
			samlIdpSpConnectionId);

		Session session = null;

		try {
			session = openSession();

			SamlIdpSpConnection[] array = new SamlIdpSpConnectionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, samlIdpSpConnection, companyId, orderByComparator,
				true);

			array[1] = samlIdpSpConnection;

			array[2] = getByCompanyId_PrevAndNext(
				session, samlIdpSpConnection, companyId, orderByComparator,
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

	protected SamlIdpSpConnection getByCompanyId_PrevAndNext(
		Session session, SamlIdpSpConnection samlIdpSpConnection,
		long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator,
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

		sb.append(_SQL_SELECT_SAMLIDPSPCONNECTION_WHERE);

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
			sb.append(SamlIdpSpConnectionModelImpl.ORDER_BY_JPQL);
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
						samlIdpSpConnection)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SamlIdpSpConnection> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the saml idp sp connections where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SamlIdpSpConnection samlIdpSpConnection :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(samlIdpSpConnection);
		}
	}

	/**
	 * Returns the number of saml idp sp connections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching saml idp sp connections
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SAMLIDPSPCONNECTION_WHERE);

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
		"samlIdpSpConnection.companyId = ?";

	private FinderPath _finderPathFetchByC_SSEI;
	private FinderPath _finderPathCountByC_SSEI;

	/**
	 * Returns the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; or throws a <code>NoSuchIdpSpConnectionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the matching saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection findByC_SSEI(
			long companyId, String samlSpEntityId)
		throws NoSuchIdpSpConnectionException {

		SamlIdpSpConnection samlIdpSpConnection = fetchByC_SSEI(
			companyId, samlSpEntityId);

		if (samlIdpSpConnection == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", samlSpEntityId=");
			sb.append(samlSpEntityId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchIdpSpConnectionException(sb.toString());
		}

		return samlIdpSpConnection;
	}

	/**
	 * Returns the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByC_SSEI(
		long companyId, String samlSpEntityId) {

		return fetchByC_SSEI(companyId, samlSpEntityId, true);
	}

	/**
	 * Returns the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByC_SSEI(
		long companyId, String samlSpEntityId, boolean useFinderCache) {

		samlSpEntityId = Objects.toString(samlSpEntityId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, samlSpEntityId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_SSEI, finderArgs);
		}

		if (result instanceof SamlIdpSpConnection) {
			SamlIdpSpConnection samlIdpSpConnection =
				(SamlIdpSpConnection)result;

			if ((companyId != samlIdpSpConnection.getCompanyId()) ||
				!Objects.equals(
					samlSpEntityId, samlIdpSpConnection.getSamlSpEntityId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SAMLIDPSPCONNECTION_WHERE);

			sb.append(_FINDER_COLUMN_C_SSEI_COMPANYID_2);

			boolean bindSamlSpEntityId = false;

			if (samlSpEntityId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_3);
			}
			else {
				bindSamlSpEntityId = true;

				sb.append(_FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindSamlSpEntityId) {
					queryPos.add(samlSpEntityId);
				}

				List<SamlIdpSpConnection> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_SSEI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, samlSpEntityId
								};
							}

							_log.warn(
								"SamlIdpSpConnectionPersistenceImpl.fetchByC_SSEI(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SamlIdpSpConnection samlIdpSpConnection = list.get(0);

					result = samlIdpSpConnection;

					cacheResult(samlIdpSpConnection);
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
			return (SamlIdpSpConnection)result;
		}
	}

	/**
	 * Removes the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the saml idp sp connection that was removed
	 */
	@Override
	public SamlIdpSpConnection removeByC_SSEI(
			long companyId, String samlSpEntityId)
		throws NoSuchIdpSpConnectionException {

		SamlIdpSpConnection samlIdpSpConnection = findByC_SSEI(
			companyId, samlSpEntityId);

		return remove(samlIdpSpConnection);
	}

	/**
	 * Returns the number of saml idp sp connections where companyId = &#63; and samlSpEntityId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the number of matching saml idp sp connections
	 */
	@Override
	public int countByC_SSEI(long companyId, String samlSpEntityId) {
		samlSpEntityId = Objects.toString(samlSpEntityId, "");

		FinderPath finderPath = _finderPathCountByC_SSEI;

		Object[] finderArgs = new Object[] {companyId, samlSpEntityId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SAMLIDPSPCONNECTION_WHERE);

			sb.append(_FINDER_COLUMN_C_SSEI_COMPANYID_2);

			boolean bindSamlSpEntityId = false;

			if (samlSpEntityId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_3);
			}
			else {
				bindSamlSpEntityId = true;

				sb.append(_FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindSamlSpEntityId) {
					queryPos.add(samlSpEntityId);
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

	private static final String _FINDER_COLUMN_C_SSEI_COMPANYID_2 =
		"samlIdpSpConnection.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_2 =
		"samlIdpSpConnection.samlSpEntityId = ?";

	private static final String _FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_3 =
		"(samlIdpSpConnection.samlSpEntityId IS NULL OR samlIdpSpConnection.samlSpEntityId = '')";

	public SamlIdpSpConnectionPersistenceImpl() {
		setModelClass(SamlIdpSpConnection.class);

		setModelImplClass(SamlIdpSpConnectionImpl.class);
		setModelPKClass(long.class);

		setTable(SamlIdpSpConnectionTable.INSTANCE);
	}

	/**
	 * Caches the saml idp sp connection in the entity cache if it is enabled.
	 *
	 * @param samlIdpSpConnection the saml idp sp connection
	 */
	@Override
	public void cacheResult(SamlIdpSpConnection samlIdpSpConnection) {
		entityCache.putResult(
			SamlIdpSpConnectionImpl.class, samlIdpSpConnection.getPrimaryKey(),
			samlIdpSpConnection);

		finderCache.putResult(
			_finderPathFetchByC_SSEI,
			new Object[] {
				samlIdpSpConnection.getCompanyId(),
				samlIdpSpConnection.getSamlSpEntityId()
			},
			samlIdpSpConnection);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the saml idp sp connections in the entity cache if it is enabled.
	 *
	 * @param samlIdpSpConnections the saml idp sp connections
	 */
	@Override
	public void cacheResult(List<SamlIdpSpConnection> samlIdpSpConnections) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (samlIdpSpConnections.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SamlIdpSpConnection samlIdpSpConnection : samlIdpSpConnections) {
			if (entityCache.getResult(
					SamlIdpSpConnectionImpl.class,
					samlIdpSpConnection.getPrimaryKey()) == null) {

				cacheResult(samlIdpSpConnection);
			}
		}
	}

	/**
	 * Clears the cache for all saml idp sp connections.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SamlIdpSpConnectionImpl.class);

		finderCache.clearCache(SamlIdpSpConnectionImpl.class);
	}

	/**
	 * Clears the cache for the saml idp sp connection.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SamlIdpSpConnection samlIdpSpConnection) {
		entityCache.removeResult(
			SamlIdpSpConnectionImpl.class, samlIdpSpConnection);
	}

	@Override
	public void clearCache(List<SamlIdpSpConnection> samlIdpSpConnections) {
		for (SamlIdpSpConnection samlIdpSpConnection : samlIdpSpConnections) {
			entityCache.removeResult(
				SamlIdpSpConnectionImpl.class, samlIdpSpConnection);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(SamlIdpSpConnectionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SamlIdpSpConnectionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SamlIdpSpConnectionModelImpl samlIdpSpConnectionModelImpl) {

		Object[] args = new Object[] {
			samlIdpSpConnectionModelImpl.getCompanyId(),
			samlIdpSpConnectionModelImpl.getSamlSpEntityId()
		};

		finderCache.putResult(_finderPathCountByC_SSEI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_SSEI, args, samlIdpSpConnectionModelImpl);
	}

	/**
	 * Creates a new saml idp sp connection with the primary key. Does not add the saml idp sp connection to the database.
	 *
	 * @param samlIdpSpConnectionId the primary key for the new saml idp sp connection
	 * @return the new saml idp sp connection
	 */
	@Override
	public SamlIdpSpConnection create(long samlIdpSpConnectionId) {
		SamlIdpSpConnection samlIdpSpConnection = new SamlIdpSpConnectionImpl();

		samlIdpSpConnection.setNew(true);
		samlIdpSpConnection.setPrimaryKey(samlIdpSpConnectionId);

		samlIdpSpConnection.setCompanyId(CompanyThreadLocal.getCompanyId());

		return samlIdpSpConnection;
	}

	/**
	 * Removes the saml idp sp connection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	 * @return the saml idp sp connection that was removed
	 * @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection remove(long samlIdpSpConnectionId)
		throws NoSuchIdpSpConnectionException {

		return remove((Serializable)samlIdpSpConnectionId);
	}

	/**
	 * Removes the saml idp sp connection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the saml idp sp connection
	 * @return the saml idp sp connection that was removed
	 * @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection remove(Serializable primaryKey)
		throws NoSuchIdpSpConnectionException {

		Session session = null;

		try {
			session = openSession();

			SamlIdpSpConnection samlIdpSpConnection =
				(SamlIdpSpConnection)session.get(
					SamlIdpSpConnectionImpl.class, primaryKey);

			if (samlIdpSpConnection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchIdpSpConnectionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(samlIdpSpConnection);
		}
		catch (NoSuchIdpSpConnectionException noSuchEntityException) {
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
	protected SamlIdpSpConnection removeImpl(
		SamlIdpSpConnection samlIdpSpConnection) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(samlIdpSpConnection)) {
				samlIdpSpConnection = (SamlIdpSpConnection)session.get(
					SamlIdpSpConnectionImpl.class,
					samlIdpSpConnection.getPrimaryKeyObj());
			}

			if (samlIdpSpConnection != null) {
				session.delete(samlIdpSpConnection);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (samlIdpSpConnection != null) {
			clearCache(samlIdpSpConnection);
		}

		return samlIdpSpConnection;
	}

	@Override
	public SamlIdpSpConnection updateImpl(
		SamlIdpSpConnection samlIdpSpConnection) {

		boolean isNew = samlIdpSpConnection.isNew();

		if (!(samlIdpSpConnection instanceof SamlIdpSpConnectionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(samlIdpSpConnection.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					samlIdpSpConnection);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in samlIdpSpConnection proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SamlIdpSpConnection implementation " +
					samlIdpSpConnection.getClass());
		}

		SamlIdpSpConnectionModelImpl samlIdpSpConnectionModelImpl =
			(SamlIdpSpConnectionModelImpl)samlIdpSpConnection;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (samlIdpSpConnection.getCreateDate() == null)) {
			if (serviceContext == null) {
				samlIdpSpConnection.setCreateDate(date);
			}
			else {
				samlIdpSpConnection.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!samlIdpSpConnectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				samlIdpSpConnection.setModifiedDate(date);
			}
			else {
				samlIdpSpConnection.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(samlIdpSpConnection);
			}
			else {
				samlIdpSpConnection = (SamlIdpSpConnection)session.merge(
					samlIdpSpConnection);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			SamlIdpSpConnectionImpl.class, samlIdpSpConnectionModelImpl, false,
			true);

		cacheUniqueFindersCache(samlIdpSpConnectionModelImpl);

		if (isNew) {
			samlIdpSpConnection.setNew(false);
		}

		samlIdpSpConnection.resetOriginalValues();

		return samlIdpSpConnection;
	}

	/**
	 * Returns the saml idp sp connection with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the saml idp sp connection
	 * @return the saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchIdpSpConnectionException {

		SamlIdpSpConnection samlIdpSpConnection = fetchByPrimaryKey(primaryKey);

		if (samlIdpSpConnection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchIdpSpConnectionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return samlIdpSpConnection;
	}

	/**
	 * Returns the saml idp sp connection with the primary key or throws a <code>NoSuchIdpSpConnectionException</code> if it could not be found.
	 *
	 * @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	 * @return the saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection findByPrimaryKey(long samlIdpSpConnectionId)
		throws NoSuchIdpSpConnectionException {

		return findByPrimaryKey((Serializable)samlIdpSpConnectionId);
	}

	/**
	 * Returns the saml idp sp connection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	 * @return the saml idp sp connection, or <code>null</code> if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByPrimaryKey(long samlIdpSpConnectionId) {
		return fetchByPrimaryKey((Serializable)samlIdpSpConnectionId);
	}

	/**
	 * Returns all the saml idp sp connections.
	 *
	 * @return the saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml idp sp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpConnectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @return the range of saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml idp sp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpConnectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findAll(
		int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml idp sp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpConnectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findAll(
		int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator,
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

		List<SamlIdpSpConnection> list = null;

		if (useFinderCache) {
			list = (List<SamlIdpSpConnection>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SAMLIDPSPCONNECTION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SAMLIDPSPCONNECTION;

				sql = sql.concat(SamlIdpSpConnectionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SamlIdpSpConnection>)QueryUtil.list(
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
	 * Removes all the saml idp sp connections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SamlIdpSpConnection samlIdpSpConnection : findAll()) {
			remove(samlIdpSpConnection);
		}
	}

	/**
	 * Returns the number of saml idp sp connections.
	 *
	 * @return the number of saml idp sp connections
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
					_SQL_COUNT_SAMLIDPSPCONNECTION);

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
		return "samlIdpSpConnectionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SAMLIDPSPCONNECTION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SamlIdpSpConnectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the saml idp sp connection persistence.
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

		_finderPathFetchByC_SSEI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_SSEI",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "samlSpEntityId"}, true);

		_finderPathCountByC_SSEI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_SSEI",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "samlSpEntityId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SamlIdpSpConnectionImpl.class.getName());
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

	private static final String _SQL_SELECT_SAMLIDPSPCONNECTION =
		"SELECT samlIdpSpConnection FROM SamlIdpSpConnection samlIdpSpConnection";

	private static final String _SQL_SELECT_SAMLIDPSPCONNECTION_WHERE =
		"SELECT samlIdpSpConnection FROM SamlIdpSpConnection samlIdpSpConnection WHERE ";

	private static final String _SQL_COUNT_SAMLIDPSPCONNECTION =
		"SELECT COUNT(samlIdpSpConnection) FROM SamlIdpSpConnection samlIdpSpConnection";

	private static final String _SQL_COUNT_SAMLIDPSPCONNECTION_WHERE =
		"SELECT COUNT(samlIdpSpConnection) FROM SamlIdpSpConnection samlIdpSpConnection WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "samlIdpSpConnection.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SamlIdpSpConnection exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SamlIdpSpConnection exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SamlIdpSpConnectionPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private SamlIdpSpConnectionModelArgumentsResolver
		_samlIdpSpConnectionModelArgumentsResolver;

}