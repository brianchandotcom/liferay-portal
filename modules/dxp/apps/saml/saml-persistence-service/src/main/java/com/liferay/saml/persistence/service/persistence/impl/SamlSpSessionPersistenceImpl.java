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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.saml.persistence.exception.NoSuchSpSessionException;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.model.SamlSpSessionTable;
import com.liferay.saml.persistence.model.impl.SamlSpSessionImpl;
import com.liferay.saml.persistence.model.impl.SamlSpSessionModelImpl;
import com.liferay.saml.persistence.service.persistence.SamlSpSessionPersistence;
import com.liferay.saml.persistence.service.persistence.impl.constants.SamlPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
 * The persistence implementation for the saml sp session service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @generated
 */
@Component(service = {SamlSpSessionPersistence.class, BasePersistence.class})
public class SamlSpSessionPersistenceImpl
	extends BasePersistenceImpl<SamlSpSession>
	implements SamlSpSessionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SamlSpSessionUtil</code> to access the saml sp session persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SamlSpSessionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindBySamlPeerBindingId;
	private FinderPath _finderPathWithoutPaginationFindBySamlPeerBindingId;
	private FinderPath _finderPathCountBySamlPeerBindingId;

	/**
	 * Returns all the saml sp sessions where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @return the matching saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findBySamlPeerBindingId(long samlPeerBindingId) {
		return findBySamlPeerBindingId(
			samlPeerBindingId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml sp sessions where samlPeerBindingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @return the range of matching saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findBySamlPeerBindingId(
		long samlPeerBindingId, int start, int end) {

		return findBySamlPeerBindingId(samlPeerBindingId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml sp sessions where samlPeerBindingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findBySamlPeerBindingId(
		long samlPeerBindingId, int start, int end,
		OrderByComparator<SamlSpSession> orderByComparator) {

		return findBySamlPeerBindingId(
			samlPeerBindingId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml sp sessions where samlPeerBindingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findBySamlPeerBindingId(
		long samlPeerBindingId, int start, int end,
		OrderByComparator<SamlSpSession> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindBySamlPeerBindingId;
				finderArgs = new Object[] {samlPeerBindingId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySamlPeerBindingId;
			finderArgs = new Object[] {
				samlPeerBindingId, start, end, orderByComparator
			};
		}

		List<SamlSpSession> list = null;

		if (useFinderCache) {
			list = (List<SamlSpSession>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SamlSpSession samlSpSession : list) {
					if (samlPeerBindingId !=
							samlSpSession.getSamlPeerBindingId()) {

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

			sb.append(_SQL_SELECT_SAMLSPSESSION_WHERE);

			sb.append(_FINDER_COLUMN_SAMLPEERBINDINGID_SAMLPEERBINDINGID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SamlSpSessionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(samlPeerBindingId);

				list = (List<SamlSpSession>)QueryUtil.list(
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
	 * Returns the first saml sp session in the ordered set where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession findBySamlPeerBindingId_First(
			long samlPeerBindingId,
			OrderByComparator<SamlSpSession> orderByComparator)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = fetchBySamlPeerBindingId_First(
			samlPeerBindingId, orderByComparator);

		if (samlSpSession != null) {
			return samlSpSession;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("samlPeerBindingId=");
		sb.append(samlPeerBindingId);

		sb.append("}");

		throw new NoSuchSpSessionException(sb.toString());
	}

	/**
	 * Returns the first saml sp session in the ordered set where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchBySamlPeerBindingId_First(
		long samlPeerBindingId,
		OrderByComparator<SamlSpSession> orderByComparator) {

		List<SamlSpSession> list = findBySamlPeerBindingId(
			samlPeerBindingId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last saml sp session in the ordered set where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession findBySamlPeerBindingId_Last(
			long samlPeerBindingId,
			OrderByComparator<SamlSpSession> orderByComparator)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = fetchBySamlPeerBindingId_Last(
			samlPeerBindingId, orderByComparator);

		if (samlSpSession != null) {
			return samlSpSession;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("samlPeerBindingId=");
		sb.append(samlPeerBindingId);

		sb.append("}");

		throw new NoSuchSpSessionException(sb.toString());
	}

	/**
	 * Returns the last saml sp session in the ordered set where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchBySamlPeerBindingId_Last(
		long samlPeerBindingId,
		OrderByComparator<SamlSpSession> orderByComparator) {

		int count = countBySamlPeerBindingId(samlPeerBindingId);

		if (count == 0) {
			return null;
		}

		List<SamlSpSession> list = findBySamlPeerBindingId(
			samlPeerBindingId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the saml sp sessions before and after the current saml sp session in the ordered set where samlPeerBindingId = &#63;.
	 *
	 * @param samlSpSessionId the primary key of the current saml sp session
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp session
	 * @throws NoSuchSpSessionException if a saml sp session with the primary key could not be found
	 */
	@Override
	public SamlSpSession[] findBySamlPeerBindingId_PrevAndNext(
			long samlSpSessionId, long samlPeerBindingId,
			OrderByComparator<SamlSpSession> orderByComparator)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = findByPrimaryKey(samlSpSessionId);

		Session session = null;

		try {
			session = openSession();

			SamlSpSession[] array = new SamlSpSessionImpl[3];

			array[0] = getBySamlPeerBindingId_PrevAndNext(
				session, samlSpSession, samlPeerBindingId, orderByComparator,
				true);

			array[1] = samlSpSession;

			array[2] = getBySamlPeerBindingId_PrevAndNext(
				session, samlSpSession, samlPeerBindingId, orderByComparator,
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

	protected SamlSpSession getBySamlPeerBindingId_PrevAndNext(
		Session session, SamlSpSession samlSpSession, long samlPeerBindingId,
		OrderByComparator<SamlSpSession> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SAMLSPSESSION_WHERE);

		sb.append(_FINDER_COLUMN_SAMLPEERBINDINGID_SAMLPEERBINDINGID_2);

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
			sb.append(SamlSpSessionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(samlPeerBindingId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						samlSpSession)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SamlSpSession> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the saml sp sessions where samlPeerBindingId = &#63; from the database.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 */
	@Override
	public void removeBySamlPeerBindingId(long samlPeerBindingId) {
		for (SamlSpSession samlSpSession :
				findBySamlPeerBindingId(
					samlPeerBindingId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(samlSpSession);
		}
	}

	/**
	 * Returns the number of saml sp sessions where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @return the number of matching saml sp sessions
	 */
	@Override
	public int countBySamlPeerBindingId(long samlPeerBindingId) {
		FinderPath finderPath = _finderPathCountBySamlPeerBindingId;

		Object[] finderArgs = new Object[] {samlPeerBindingId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SAMLSPSESSION_WHERE);

			sb.append(_FINDER_COLUMN_SAMLPEERBINDINGID_SAMLPEERBINDINGID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(samlPeerBindingId);

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

	private static final String
		_FINDER_COLUMN_SAMLPEERBINDINGID_SAMLPEERBINDINGID_2 =
			"samlSpSession.samlPeerBindingId = ?";

	private FinderPath _finderPathFetchByJSessionId;
	private FinderPath _finderPathCountByJSessionId;

	/**
	 * Returns the saml sp session where jSessionId = &#63; or throws a <code>NoSuchSpSessionException</code> if it could not be found.
	 *
	 * @param jSessionId the j session ID
	 * @return the matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession findByJSessionId(String jSessionId)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = fetchByJSessionId(jSessionId);

		if (samlSpSession == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("jSessionId=");
			sb.append(jSessionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSpSessionException(sb.toString());
		}

		return samlSpSession;
	}

	/**
	 * Returns the saml sp session where jSessionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param jSessionId the j session ID
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchByJSessionId(String jSessionId) {
		return fetchByJSessionId(jSessionId, true);
	}

	/**
	 * Returns the saml sp session where jSessionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param jSessionId the j session ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchByJSessionId(
		String jSessionId, boolean useFinderCache) {

		jSessionId = Objects.toString(jSessionId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {jSessionId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByJSessionId, finderArgs);
		}

		if (result instanceof SamlSpSession) {
			SamlSpSession samlSpSession = (SamlSpSession)result;

			if (!Objects.equals(jSessionId, samlSpSession.getJSessionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_SAMLSPSESSION_WHERE);

			boolean bindJSessionId = false;

			if (jSessionId.isEmpty()) {
				sb.append(_FINDER_COLUMN_JSESSIONID_JSESSIONID_3);
			}
			else {
				bindJSessionId = true;

				sb.append(_FINDER_COLUMN_JSESSIONID_JSESSIONID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindJSessionId) {
					queryPos.add(jSessionId);
				}

				List<SamlSpSession> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByJSessionId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {jSessionId};
							}

							_log.warn(
								"SamlSpSessionPersistenceImpl.fetchByJSessionId(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SamlSpSession samlSpSession = list.get(0);

					result = samlSpSession;

					cacheResult(samlSpSession);
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
			return (SamlSpSession)result;
		}
	}

	/**
	 * Removes the saml sp session where jSessionId = &#63; from the database.
	 *
	 * @param jSessionId the j session ID
	 * @return the saml sp session that was removed
	 */
	@Override
	public SamlSpSession removeByJSessionId(String jSessionId)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = findByJSessionId(jSessionId);

		return remove(samlSpSession);
	}

	/**
	 * Returns the number of saml sp sessions where jSessionId = &#63;.
	 *
	 * @param jSessionId the j session ID
	 * @return the number of matching saml sp sessions
	 */
	@Override
	public int countByJSessionId(String jSessionId) {
		jSessionId = Objects.toString(jSessionId, "");

		FinderPath finderPath = _finderPathCountByJSessionId;

		Object[] finderArgs = new Object[] {jSessionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SAMLSPSESSION_WHERE);

			boolean bindJSessionId = false;

			if (jSessionId.isEmpty()) {
				sb.append(_FINDER_COLUMN_JSESSIONID_JSESSIONID_3);
			}
			else {
				bindJSessionId = true;

				sb.append(_FINDER_COLUMN_JSESSIONID_JSESSIONID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindJSessionId) {
					queryPos.add(jSessionId);
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

	private static final String _FINDER_COLUMN_JSESSIONID_JSESSIONID_2 =
		"samlSpSession.jSessionId = ?";

	private static final String _FINDER_COLUMN_JSESSIONID_JSESSIONID_3 =
		"(samlSpSession.jSessionId IS NULL OR samlSpSession.jSessionId = '')";

	private FinderPath _finderPathFetchBySamlSpSessionKey;
	private FinderPath _finderPathCountBySamlSpSessionKey;

	/**
	 * Returns the saml sp session where samlSpSessionKey = &#63; or throws a <code>NoSuchSpSessionException</code> if it could not be found.
	 *
	 * @param samlSpSessionKey the saml sp session key
	 * @return the matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession findBySamlSpSessionKey(String samlSpSessionKey)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = fetchBySamlSpSessionKey(samlSpSessionKey);

		if (samlSpSession == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("samlSpSessionKey=");
			sb.append(samlSpSessionKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSpSessionException(sb.toString());
		}

		return samlSpSession;
	}

	/**
	 * Returns the saml sp session where samlSpSessionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlSpSessionKey the saml sp session key
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchBySamlSpSessionKey(String samlSpSessionKey) {
		return fetchBySamlSpSessionKey(samlSpSessionKey, true);
	}

	/**
	 * Returns the saml sp session where samlSpSessionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlSpSessionKey the saml sp session key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchBySamlSpSessionKey(
		String samlSpSessionKey, boolean useFinderCache) {

		samlSpSessionKey = Objects.toString(samlSpSessionKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {samlSpSessionKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchBySamlSpSessionKey, finderArgs);
		}

		if (result instanceof SamlSpSession) {
			SamlSpSession samlSpSession = (SamlSpSession)result;

			if (!Objects.equals(
					samlSpSessionKey, samlSpSession.getSamlSpSessionKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_SAMLSPSESSION_WHERE);

			boolean bindSamlSpSessionKey = false;

			if (samlSpSessionKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_3);
			}
			else {
				bindSamlSpSessionKey = true;

				sb.append(_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSamlSpSessionKey) {
					queryPos.add(samlSpSessionKey);
				}

				List<SamlSpSession> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchBySamlSpSessionKey, finderArgs,
							list);
					}
				}
				else {
					SamlSpSession samlSpSession = list.get(0);

					result = samlSpSession;

					cacheResult(samlSpSession);
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
			return (SamlSpSession)result;
		}
	}

	/**
	 * Removes the saml sp session where samlSpSessionKey = &#63; from the database.
	 *
	 * @param samlSpSessionKey the saml sp session key
	 * @return the saml sp session that was removed
	 */
	@Override
	public SamlSpSession removeBySamlSpSessionKey(String samlSpSessionKey)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = findBySamlSpSessionKey(samlSpSessionKey);

		return remove(samlSpSession);
	}

	/**
	 * Returns the number of saml sp sessions where samlSpSessionKey = &#63;.
	 *
	 * @param samlSpSessionKey the saml sp session key
	 * @return the number of matching saml sp sessions
	 */
	@Override
	public int countBySamlSpSessionKey(String samlSpSessionKey) {
		samlSpSessionKey = Objects.toString(samlSpSessionKey, "");

		FinderPath finderPath = _finderPathCountBySamlSpSessionKey;

		Object[] finderArgs = new Object[] {samlSpSessionKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SAMLSPSESSION_WHERE);

			boolean bindSamlSpSessionKey = false;

			if (samlSpSessionKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_3);
			}
			else {
				bindSamlSpSessionKey = true;

				sb.append(_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSamlSpSessionKey) {
					queryPos.add(samlSpSessionKey);
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

	private static final String
		_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_2 =
			"samlSpSession.samlSpSessionKey = ?";

	private static final String
		_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_3 =
			"(samlSpSession.samlSpSessionKey IS NULL OR samlSpSession.samlSpSessionKey = '')";

	private FinderPath _finderPathFetchByC_SI;
	private FinderPath _finderPathCountByC_SI;

	/**
	 * Returns the saml sp session where companyId = &#63; and sessionIndex = &#63; or throws a <code>NoSuchSpSessionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @return the matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession findByC_SI(long companyId, String sessionIndex)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = fetchByC_SI(companyId, sessionIndex);

		if (samlSpSession == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", sessionIndex=");
			sb.append(sessionIndex);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSpSessionException(sb.toString());
		}

		return samlSpSession;
	}

	/**
	 * Returns the saml sp session where companyId = &#63; and sessionIndex = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchByC_SI(long companyId, String sessionIndex) {
		return fetchByC_SI(companyId, sessionIndex, true);
	}

	/**
	 * Returns the saml sp session where companyId = &#63; and sessionIndex = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchByC_SI(
		long companyId, String sessionIndex, boolean useFinderCache) {

		sessionIndex = Objects.toString(sessionIndex, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, sessionIndex};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_SI, finderArgs);
		}

		if (result instanceof SamlSpSession) {
			SamlSpSession samlSpSession = (SamlSpSession)result;

			if ((companyId != samlSpSession.getCompanyId()) ||
				!Objects.equals(
					sessionIndex, samlSpSession.getSessionIndex())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SAMLSPSESSION_WHERE);

			sb.append(_FINDER_COLUMN_C_SI_COMPANYID_2);

			boolean bindSessionIndex = false;

			if (sessionIndex.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_SI_SESSIONINDEX_3);
			}
			else {
				bindSessionIndex = true;

				sb.append(_FINDER_COLUMN_C_SI_SESSIONINDEX_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindSessionIndex) {
					queryPos.add(sessionIndex);
				}

				List<SamlSpSession> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_SI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, sessionIndex
								};
							}

							_log.warn(
								"SamlSpSessionPersistenceImpl.fetchByC_SI(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SamlSpSession samlSpSession = list.get(0);

					result = samlSpSession;

					cacheResult(samlSpSession);
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
			return (SamlSpSession)result;
		}
	}

	/**
	 * Removes the saml sp session where companyId = &#63; and sessionIndex = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @return the saml sp session that was removed
	 */
	@Override
	public SamlSpSession removeByC_SI(long companyId, String sessionIndex)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = findByC_SI(companyId, sessionIndex);

		return remove(samlSpSession);
	}

	/**
	 * Returns the number of saml sp sessions where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @return the number of matching saml sp sessions
	 */
	@Override
	public int countByC_SI(long companyId, String sessionIndex) {
		sessionIndex = Objects.toString(sessionIndex, "");

		FinderPath finderPath = _finderPathCountByC_SI;

		Object[] finderArgs = new Object[] {companyId, sessionIndex};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SAMLSPSESSION_WHERE);

			sb.append(_FINDER_COLUMN_C_SI_COMPANYID_2);

			boolean bindSessionIndex = false;

			if (sessionIndex.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_SI_SESSIONINDEX_3);
			}
			else {
				bindSessionIndex = true;

				sb.append(_FINDER_COLUMN_C_SI_SESSIONINDEX_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindSessionIndex) {
					queryPos.add(sessionIndex);
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

	private static final String _FINDER_COLUMN_C_SI_COMPANYID_2 =
		"samlSpSession.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_SI_SESSIONINDEX_2 =
		"samlSpSession.sessionIndex = ?";

	private static final String _FINDER_COLUMN_C_SI_SESSIONINDEX_3 =
		"(samlSpSession.sessionIndex IS NULL OR samlSpSession.sessionIndex = '')";

	public SamlSpSessionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("terminated", "terminated_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SamlSpSession.class);

		setModelImplClass(SamlSpSessionImpl.class);
		setModelPKClass(long.class);

		setTable(SamlSpSessionTable.INSTANCE);
	}

	/**
	 * Caches the saml sp session in the entity cache if it is enabled.
	 *
	 * @param samlSpSession the saml sp session
	 */
	@Override
	public void cacheResult(SamlSpSession samlSpSession) {
		entityCache.putResult(
			SamlSpSessionImpl.class, samlSpSession.getPrimaryKey(),
			samlSpSession);

		finderCache.putResult(
			_finderPathFetchByJSessionId,
			new Object[] {samlSpSession.getJSessionId()}, samlSpSession);

		finderCache.putResult(
			_finderPathFetchBySamlSpSessionKey,
			new Object[] {samlSpSession.getSamlSpSessionKey()}, samlSpSession);

		finderCache.putResult(
			_finderPathFetchByC_SI,
			new Object[] {
				samlSpSession.getCompanyId(), samlSpSession.getSessionIndex()
			},
			samlSpSession);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the saml sp sessions in the entity cache if it is enabled.
	 *
	 * @param samlSpSessions the saml sp sessions
	 */
	@Override
	public void cacheResult(List<SamlSpSession> samlSpSessions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (samlSpSessions.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SamlSpSession samlSpSession : samlSpSessions) {
			if (entityCache.getResult(
					SamlSpSessionImpl.class, samlSpSession.getPrimaryKey()) ==
						null) {

				cacheResult(samlSpSession);
			}
		}
	}

	/**
	 * Clears the cache for all saml sp sessions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SamlSpSessionImpl.class);

		finderCache.clearCache(SamlSpSessionImpl.class);
	}

	/**
	 * Clears the cache for the saml sp session.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SamlSpSession samlSpSession) {
		entityCache.removeResult(SamlSpSessionImpl.class, samlSpSession);
	}

	@Override
	public void clearCache(List<SamlSpSession> samlSpSessions) {
		for (SamlSpSession samlSpSession : samlSpSessions) {
			entityCache.removeResult(SamlSpSessionImpl.class, samlSpSession);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(SamlSpSessionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SamlSpSessionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SamlSpSessionModelImpl samlSpSessionModelImpl) {

		Object[] args = new Object[] {samlSpSessionModelImpl.getJSessionId()};

		finderCache.putResult(
			_finderPathCountByJSessionId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByJSessionId, args, samlSpSessionModelImpl);

		args = new Object[] {samlSpSessionModelImpl.getSamlSpSessionKey()};

		finderCache.putResult(
			_finderPathCountBySamlSpSessionKey, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchBySamlSpSessionKey, args, samlSpSessionModelImpl);

		args = new Object[] {
			samlSpSessionModelImpl.getCompanyId(),
			samlSpSessionModelImpl.getSessionIndex()
		};

		finderCache.putResult(_finderPathCountByC_SI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_SI, args, samlSpSessionModelImpl);
	}

	/**
	 * Creates a new saml sp session with the primary key. Does not add the saml sp session to the database.
	 *
	 * @param samlSpSessionId the primary key for the new saml sp session
	 * @return the new saml sp session
	 */
	@Override
	public SamlSpSession create(long samlSpSessionId) {
		SamlSpSession samlSpSession = new SamlSpSessionImpl();

		samlSpSession.setNew(true);
		samlSpSession.setPrimaryKey(samlSpSessionId);

		samlSpSession.setCompanyId(CompanyThreadLocal.getCompanyId());

		return samlSpSession;
	}

	/**
	 * Removes the saml sp session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpSessionId the primary key of the saml sp session
	 * @return the saml sp session that was removed
	 * @throws NoSuchSpSessionException if a saml sp session with the primary key could not be found
	 */
	@Override
	public SamlSpSession remove(long samlSpSessionId)
		throws NoSuchSpSessionException {

		return remove((Serializable)samlSpSessionId);
	}

	/**
	 * Removes the saml sp session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the saml sp session
	 * @return the saml sp session that was removed
	 * @throws NoSuchSpSessionException if a saml sp session with the primary key could not be found
	 */
	@Override
	public SamlSpSession remove(Serializable primaryKey)
		throws NoSuchSpSessionException {

		Session session = null;

		try {
			session = openSession();

			SamlSpSession samlSpSession = (SamlSpSession)session.get(
				SamlSpSessionImpl.class, primaryKey);

			if (samlSpSession == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSpSessionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(samlSpSession);
		}
		catch (NoSuchSpSessionException noSuchEntityException) {
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
	protected SamlSpSession removeImpl(SamlSpSession samlSpSession) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(samlSpSession)) {
				samlSpSession = (SamlSpSession)session.get(
					SamlSpSessionImpl.class, samlSpSession.getPrimaryKeyObj());
			}

			if (samlSpSession != null) {
				session.delete(samlSpSession);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (samlSpSession != null) {
			clearCache(samlSpSession);
		}

		return samlSpSession;
	}

	@Override
	public SamlSpSession updateImpl(SamlSpSession samlSpSession) {
		boolean isNew = samlSpSession.isNew();

		if (!(samlSpSession instanceof SamlSpSessionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(samlSpSession.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					samlSpSession);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in samlSpSession proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SamlSpSession implementation " +
					samlSpSession.getClass());
		}

		SamlSpSessionModelImpl samlSpSessionModelImpl =
			(SamlSpSessionModelImpl)samlSpSession;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (samlSpSession.getCreateDate() == null)) {
			if (serviceContext == null) {
				samlSpSession.setCreateDate(date);
			}
			else {
				samlSpSession.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!samlSpSessionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				samlSpSession.setModifiedDate(date);
			}
			else {
				samlSpSession.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(samlSpSession);
			}
			else {
				samlSpSession = (SamlSpSession)session.merge(samlSpSession);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			SamlSpSessionImpl.class, samlSpSessionModelImpl, false, true);

		cacheUniqueFindersCache(samlSpSessionModelImpl);

		if (isNew) {
			samlSpSession.setNew(false);
		}

		samlSpSession.resetOriginalValues();

		return samlSpSession;
	}

	/**
	 * Returns the saml sp session with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the saml sp session
	 * @return the saml sp session
	 * @throws NoSuchSpSessionException if a saml sp session with the primary key could not be found
	 */
	@Override
	public SamlSpSession findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = fetchByPrimaryKey(primaryKey);

		if (samlSpSession == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSpSessionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return samlSpSession;
	}

	/**
	 * Returns the saml sp session with the primary key or throws a <code>NoSuchSpSessionException</code> if it could not be found.
	 *
	 * @param samlSpSessionId the primary key of the saml sp session
	 * @return the saml sp session
	 * @throws NoSuchSpSessionException if a saml sp session with the primary key could not be found
	 */
	@Override
	public SamlSpSession findByPrimaryKey(long samlSpSessionId)
		throws NoSuchSpSessionException {

		return findByPrimaryKey((Serializable)samlSpSessionId);
	}

	/**
	 * Returns the saml sp session with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlSpSessionId the primary key of the saml sp session
	 * @return the saml sp session, or <code>null</code> if a saml sp session with the primary key could not be found
	 */
	@Override
	public SamlSpSession fetchByPrimaryKey(long samlSpSessionId) {
		return fetchByPrimaryKey((Serializable)samlSpSessionId);
	}

	/**
	 * Returns all the saml sp sessions.
	 *
	 * @return the saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml sp sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @return the range of saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml sp sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findAll(
		int start, int end,
		OrderByComparator<SamlSpSession> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml sp sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findAll(
		int start, int end, OrderByComparator<SamlSpSession> orderByComparator,
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

		List<SamlSpSession> list = null;

		if (useFinderCache) {
			list = (List<SamlSpSession>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SAMLSPSESSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SAMLSPSESSION;

				sql = sql.concat(SamlSpSessionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SamlSpSession>)QueryUtil.list(
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
	 * Removes all the saml sp sessions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SamlSpSession samlSpSession : findAll()) {
			remove(samlSpSession);
		}
	}

	/**
	 * Returns the number of saml sp sessions.
	 *
	 * @return the number of saml sp sessions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SAMLSPSESSION);

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
		return "samlSpSessionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SAMLSPSESSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SamlSpSessionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the saml sp session persistence.
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

		_finderPathWithPaginationFindBySamlPeerBindingId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySamlPeerBindingId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"samlPeerBindingId"}, true);

		_finderPathWithoutPaginationFindBySamlPeerBindingId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySamlPeerBindingId", new String[] {Long.class.getName()},
			new String[] {"samlPeerBindingId"}, true);

		_finderPathCountBySamlPeerBindingId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamlPeerBindingId", new String[] {Long.class.getName()},
			new String[] {"samlPeerBindingId"}, false);

		_finderPathFetchByJSessionId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByJSessionId",
			new String[] {String.class.getName()}, new String[] {"jSessionId"},
			true);

		_finderPathCountByJSessionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByJSessionId",
			new String[] {String.class.getName()}, new String[] {"jSessionId"},
			false);

		_finderPathFetchBySamlSpSessionKey = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchBySamlSpSessionKey",
			new String[] {String.class.getName()},
			new String[] {"samlSpSessionKey"}, true);

		_finderPathCountBySamlSpSessionKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamlSpSessionKey", new String[] {String.class.getName()},
			new String[] {"samlSpSessionKey"}, false);

		_finderPathFetchByC_SI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_SI",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "sessionIndex"}, true);

		_finderPathCountByC_SI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_SI",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "sessionIndex"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SamlSpSessionImpl.class.getName());
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

	private static final String _SQL_SELECT_SAMLSPSESSION =
		"SELECT samlSpSession FROM SamlSpSession samlSpSession";

	private static final String _SQL_SELECT_SAMLSPSESSION_WHERE =
		"SELECT samlSpSession FROM SamlSpSession samlSpSession WHERE ";

	private static final String _SQL_COUNT_SAMLSPSESSION =
		"SELECT COUNT(samlSpSession) FROM SamlSpSession samlSpSession";

	private static final String _SQL_COUNT_SAMLSPSESSION_WHERE =
		"SELECT COUNT(samlSpSession) FROM SamlSpSession samlSpSession WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "samlSpSession.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SamlSpSession exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SamlSpSession exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SamlSpSessionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"terminated"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private SamlSpSessionModelArgumentsResolver
		_samlSpSessionModelArgumentsResolver;

}