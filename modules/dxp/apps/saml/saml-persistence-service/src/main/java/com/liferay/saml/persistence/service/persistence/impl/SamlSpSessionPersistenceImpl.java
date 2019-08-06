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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.saml.persistence.exception.NoSuchSpSessionException;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.model.impl.SamlSpSessionImpl;
import com.liferay.saml.persistence.model.impl.SamlSpSessionModelImpl;
import com.liferay.saml.persistence.service.persistence.SamlSpSessionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

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
@ProviderType
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
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("samlSpSessionKey=");
			msg.append(samlSpSessionKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSpSessionException(msg.toString());
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
				_finderPathFetchBySamlSpSessionKey, finderArgs, this);
		}

		if (result instanceof SamlSpSession) {
			SamlSpSession samlSpSession = (SamlSpSession)result;

			if (!Objects.equals(
					samlSpSessionKey, samlSpSession.getSamlSpSessionKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SAMLSPSESSION_WHERE);

			boolean bindSamlSpSessionKey = false;

			if (samlSpSessionKey.isEmpty()) {
				query.append(
					_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_3);
			}
			else {
				bindSamlSpSessionKey = true;

				query.append(
					_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamlSpSessionKey) {
					qPos.add(samlSpSessionKey);
				}

				List<SamlSpSession> list = q.list();

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
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchBySamlSpSessionKey, finderArgs);
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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMLSPSESSION_WHERE);

			boolean bindSamlSpSessionKey = false;

			if (samlSpSessionKey.isEmpty()) {
				query.append(
					_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_3);
			}
			else {
				bindSamlSpSessionKey = true;

				query.append(
					_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamlSpSessionKey) {
					qPos.add(samlSpSessionKey);
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

	private static final String
		_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_2 =
			"samlSpSession.samlSpSessionKey = ?";

	private static final String
		_FINDER_COLUMN_SAMLSPSESSIONKEY_SAMLSPSESSIONKEY_3 =
			"(samlSpSession.samlSpSessionKey IS NULL OR samlSpSession.samlSpSessionKey = '')";

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
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("jSessionId=");
			msg.append(jSessionId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSpSessionException(msg.toString());
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
				_finderPathFetchByJSessionId, finderArgs, this);
		}

		if (result instanceof SamlSpSession) {
			SamlSpSession samlSpSession = (SamlSpSession)result;

			if (!Objects.equals(jSessionId, samlSpSession.getJSessionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SAMLSPSESSION_WHERE);

			boolean bindJSessionId = false;

			if (jSessionId.isEmpty()) {
				query.append(_FINDER_COLUMN_JSESSIONID_JSESSIONID_3);
			}
			else {
				bindJSessionId = true;

				query.append(_FINDER_COLUMN_JSESSIONID_JSESSIONID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindJSessionId) {
					qPos.add(jSessionId);
				}

				List<SamlSpSession> list = q.list();

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
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByJSessionId, finderArgs);
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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMLSPSESSION_WHERE);

			boolean bindJSessionId = false;

			if (jSessionId.isEmpty()) {
				query.append(_FINDER_COLUMN_JSESSIONID_JSESSIONID_3);
			}
			else {
				bindJSessionId = true;

				query.append(_FINDER_COLUMN_JSESSIONID_JSESSIONID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindJSessionId) {
					qPos.add(jSessionId);
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

	private static final String _FINDER_COLUMN_JSESSIONID_JSESSIONID_2 =
		"samlSpSession.jSessionId = ?";

	private static final String _FINDER_COLUMN_JSESSIONID_JSESSIONID_3 =
		"(samlSpSession.jSessionId IS NULL OR samlSpSession.jSessionId = '')";

	private FinderPath _finderPathWithPaginationFindByNameIdValue;
	private FinderPath _finderPathWithoutPaginationFindByNameIdValue;
	private FinderPath _finderPathCountByNameIdValue;

	/**
	 * Returns all the saml sp sessions where nameIdValue = &#63;.
	 *
	 * @param nameIdValue the name ID value
	 * @return the matching saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findByNameIdValue(String nameIdValue) {
		return findByNameIdValue(
			nameIdValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml sp sessions where nameIdValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nameIdValue the name ID value
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @return the range of matching saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findByNameIdValue(
		String nameIdValue, int start, int end) {

		return findByNameIdValue(nameIdValue, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml sp sessions where nameIdValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nameIdValue the name ID value
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findByNameIdValue(
		String nameIdValue, int start, int end,
		OrderByComparator<SamlSpSession> orderByComparator) {

		return findByNameIdValue(
			nameIdValue, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml sp sessions where nameIdValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nameIdValue the name ID value
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml sp sessions
	 */
	@Override
	public List<SamlSpSession> findByNameIdValue(
		String nameIdValue, int start, int end,
		OrderByComparator<SamlSpSession> orderByComparator,
		boolean useFinderCache) {

		nameIdValue = Objects.toString(nameIdValue, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByNameIdValue;
				finderArgs = new Object[] {nameIdValue};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByNameIdValue;
			finderArgs = new Object[] {
				nameIdValue, start, end, orderByComparator
			};
		}

		List<SamlSpSession> list = null;

		if (useFinderCache) {
			list = (List<SamlSpSession>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SamlSpSession samlSpSession : list) {
					if (!nameIdValue.equals(samlSpSession.getNameIdValue())) {
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

			query.append(_SQL_SELECT_SAMLSPSESSION_WHERE);

			boolean bindNameIdValue = false;

			if (nameIdValue.isEmpty()) {
				query.append(_FINDER_COLUMN_NAMEIDVALUE_NAMEIDVALUE_3);
			}
			else {
				bindNameIdValue = true;

				query.append(_FINDER_COLUMN_NAMEIDVALUE_NAMEIDVALUE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(SamlSpSessionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindNameIdValue) {
					qPos.add(nameIdValue);
				}

				if (!pagination) {
					list = (List<SamlSpSession>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlSpSession>)QueryUtil.list(
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
	 * Returns the first saml sp session in the ordered set where nameIdValue = &#63;.
	 *
	 * @param nameIdValue the name ID value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession findByNameIdValue_First(
			String nameIdValue,
			OrderByComparator<SamlSpSession> orderByComparator)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = fetchByNameIdValue_First(
			nameIdValue, orderByComparator);

		if (samlSpSession != null) {
			return samlSpSession;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nameIdValue=");
		msg.append(nameIdValue);

		msg.append("}");

		throw new NoSuchSpSessionException(msg.toString());
	}

	/**
	 * Returns the first saml sp session in the ordered set where nameIdValue = &#63;.
	 *
	 * @param nameIdValue the name ID value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchByNameIdValue_First(
		String nameIdValue,
		OrderByComparator<SamlSpSession> orderByComparator) {

		List<SamlSpSession> list = findByNameIdValue(
			nameIdValue, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last saml sp session in the ordered set where nameIdValue = &#63;.
	 *
	 * @param nameIdValue the name ID value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession findByNameIdValue_Last(
			String nameIdValue,
			OrderByComparator<SamlSpSession> orderByComparator)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = fetchByNameIdValue_Last(
			nameIdValue, orderByComparator);

		if (samlSpSession != null) {
			return samlSpSession;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nameIdValue=");
		msg.append(nameIdValue);

		msg.append("}");

		throw new NoSuchSpSessionException(msg.toString());
	}

	/**
	 * Returns the last saml sp session in the ordered set where nameIdValue = &#63;.
	 *
	 * @param nameIdValue the name ID value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchByNameIdValue_Last(
		String nameIdValue,
		OrderByComparator<SamlSpSession> orderByComparator) {

		int count = countByNameIdValue(nameIdValue);

		if (count == 0) {
			return null;
		}

		List<SamlSpSession> list = findByNameIdValue(
			nameIdValue, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the saml sp sessions before and after the current saml sp session in the ordered set where nameIdValue = &#63;.
	 *
	 * @param samlSpSessionId the primary key of the current saml sp session
	 * @param nameIdValue the name ID value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp session
	 * @throws NoSuchSpSessionException if a saml sp session with the primary key could not be found
	 */
	@Override
	public SamlSpSession[] findByNameIdValue_PrevAndNext(
			long samlSpSessionId, String nameIdValue,
			OrderByComparator<SamlSpSession> orderByComparator)
		throws NoSuchSpSessionException {

		nameIdValue = Objects.toString(nameIdValue, "");

		SamlSpSession samlSpSession = findByPrimaryKey(samlSpSessionId);

		Session session = null;

		try {
			session = openSession();

			SamlSpSession[] array = new SamlSpSessionImpl[3];

			array[0] = getByNameIdValue_PrevAndNext(
				session, samlSpSession, nameIdValue, orderByComparator, true);

			array[1] = samlSpSession;

			array[2] = getByNameIdValue_PrevAndNext(
				session, samlSpSession, nameIdValue, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SamlSpSession getByNameIdValue_PrevAndNext(
		Session session, SamlSpSession samlSpSession, String nameIdValue,
		OrderByComparator<SamlSpSession> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMLSPSESSION_WHERE);

		boolean bindNameIdValue = false;

		if (nameIdValue.isEmpty()) {
			query.append(_FINDER_COLUMN_NAMEIDVALUE_NAMEIDVALUE_3);
		}
		else {
			bindNameIdValue = true;

			query.append(_FINDER_COLUMN_NAMEIDVALUE_NAMEIDVALUE_2);
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
			query.append(SamlSpSessionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindNameIdValue) {
			qPos.add(nameIdValue);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						samlSpSession)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SamlSpSession> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the saml sp sessions where nameIdValue = &#63; from the database.
	 *
	 * @param nameIdValue the name ID value
	 */
	@Override
	public void removeByNameIdValue(String nameIdValue) {
		for (SamlSpSession samlSpSession :
				findByNameIdValue(
					nameIdValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(samlSpSession);
		}
	}

	/**
	 * Returns the number of saml sp sessions where nameIdValue = &#63;.
	 *
	 * @param nameIdValue the name ID value
	 * @return the number of matching saml sp sessions
	 */
	@Override
	public int countByNameIdValue(String nameIdValue) {
		nameIdValue = Objects.toString(nameIdValue, "");

		FinderPath finderPath = _finderPathCountByNameIdValue;

		Object[] finderArgs = new Object[] {nameIdValue};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMLSPSESSION_WHERE);

			boolean bindNameIdValue = false;

			if (nameIdValue.isEmpty()) {
				query.append(_FINDER_COLUMN_NAMEIDVALUE_NAMEIDVALUE_3);
			}
			else {
				bindNameIdValue = true;

				query.append(_FINDER_COLUMN_NAMEIDVALUE_NAMEIDVALUE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindNameIdValue) {
					qPos.add(nameIdValue);
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

	private static final String _FINDER_COLUMN_NAMEIDVALUE_NAMEIDVALUE_2 =
		"samlSpSession.nameIdValue = ?";

	private static final String _FINDER_COLUMN_NAMEIDVALUE_NAMEIDVALUE_3 =
		"(samlSpSession.nameIdValue IS NULL OR samlSpSession.nameIdValue = '')";

	private FinderPath _finderPathFetchBySessionIndex;
	private FinderPath _finderPathCountBySessionIndex;

	/**
	 * Returns the saml sp session where sessionIndex = &#63; or throws a <code>NoSuchSpSessionException</code> if it could not be found.
	 *
	 * @param sessionIndex the session index
	 * @return the matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession findBySessionIndex(String sessionIndex)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = fetchBySessionIndex(sessionIndex);

		if (samlSpSession == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("sessionIndex=");
			msg.append(sessionIndex);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSpSessionException(msg.toString());
		}

		return samlSpSession;
	}

	/**
	 * Returns the saml sp session where sessionIndex = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param sessionIndex the session index
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchBySessionIndex(String sessionIndex) {
		return fetchBySessionIndex(sessionIndex, true);
	}

	/**
	 * Returns the saml sp session where sessionIndex = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param sessionIndex the session index
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	@Override
	public SamlSpSession fetchBySessionIndex(
		String sessionIndex, boolean useFinderCache) {

		sessionIndex = Objects.toString(sessionIndex, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {sessionIndex};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchBySessionIndex, finderArgs, this);
		}

		if (result instanceof SamlSpSession) {
			SamlSpSession samlSpSession = (SamlSpSession)result;

			if (!Objects.equals(
					sessionIndex, samlSpSession.getSessionIndex())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SAMLSPSESSION_WHERE);

			boolean bindSessionIndex = false;

			if (sessionIndex.isEmpty()) {
				query.append(_FINDER_COLUMN_SESSIONINDEX_SESSIONINDEX_3);
			}
			else {
				bindSessionIndex = true;

				query.append(_FINDER_COLUMN_SESSIONINDEX_SESSIONINDEX_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSessionIndex) {
					qPos.add(sessionIndex);
				}

				List<SamlSpSession> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchBySessionIndex, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {sessionIndex};
							}

							_log.warn(
								"SamlSpSessionPersistenceImpl.fetchBySessionIndex(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SamlSpSession samlSpSession = list.get(0);

					result = samlSpSession;

					cacheResult(samlSpSession);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchBySessionIndex, finderArgs);
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
			return (SamlSpSession)result;
		}
	}

	/**
	 * Removes the saml sp session where sessionIndex = &#63; from the database.
	 *
	 * @param sessionIndex the session index
	 * @return the saml sp session that was removed
	 */
	@Override
	public SamlSpSession removeBySessionIndex(String sessionIndex)
		throws NoSuchSpSessionException {

		SamlSpSession samlSpSession = findBySessionIndex(sessionIndex);

		return remove(samlSpSession);
	}

	/**
	 * Returns the number of saml sp sessions where sessionIndex = &#63;.
	 *
	 * @param sessionIndex the session index
	 * @return the number of matching saml sp sessions
	 */
	@Override
	public int countBySessionIndex(String sessionIndex) {
		sessionIndex = Objects.toString(sessionIndex, "");

		FinderPath finderPath = _finderPathCountBySessionIndex;

		Object[] finderArgs = new Object[] {sessionIndex};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMLSPSESSION_WHERE);

			boolean bindSessionIndex = false;

			if (sessionIndex.isEmpty()) {
				query.append(_FINDER_COLUMN_SESSIONINDEX_SESSIONINDEX_3);
			}
			else {
				bindSessionIndex = true;

				query.append(_FINDER_COLUMN_SESSIONINDEX_SESSIONINDEX_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSessionIndex) {
					qPos.add(sessionIndex);
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

	private static final String _FINDER_COLUMN_SESSIONINDEX_SESSIONINDEX_2 =
		"samlSpSession.sessionIndex = ?";

	private static final String _FINDER_COLUMN_SESSIONINDEX_SESSIONINDEX_3 =
		"(samlSpSession.sessionIndex IS NULL OR samlSpSession.sessionIndex = '')";

	public SamlSpSessionPersistenceImpl() {
		setModelClass(SamlSpSession.class);

		setModelImplClass(SamlSpSessionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("terminated", "terminated_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the saml sp session in the entity cache if it is enabled.
	 *
	 * @param samlSpSession the saml sp session
	 */
	@Override
	public void cacheResult(SamlSpSession samlSpSession) {
		entityCache.putResult(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionImpl.class, samlSpSession.getPrimaryKey(),
			samlSpSession);

		finderCache.putResult(
			_finderPathFetchBySamlSpSessionKey,
			new Object[] {samlSpSession.getSamlSpSessionKey()}, samlSpSession);

		finderCache.putResult(
			_finderPathFetchByJSessionId,
			new Object[] {samlSpSession.getJSessionId()}, samlSpSession);

		finderCache.putResult(
			_finderPathFetchBySessionIndex,
			new Object[] {samlSpSession.getSessionIndex()}, samlSpSession);

		samlSpSession.resetOriginalValues();
	}

	/**
	 * Caches the saml sp sessions in the entity cache if it is enabled.
	 *
	 * @param samlSpSessions the saml sp sessions
	 */
	@Override
	public void cacheResult(List<SamlSpSession> samlSpSessions) {
		for (SamlSpSession samlSpSession : samlSpSessions) {
			if (entityCache.getResult(
					SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
					SamlSpSessionImpl.class, samlSpSession.getPrimaryKey()) ==
						null) {

				cacheResult(samlSpSession);
			}
			else {
				samlSpSession.resetOriginalValues();
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

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
		entityCache.removeResult(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionImpl.class, samlSpSession.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SamlSpSessionModelImpl)samlSpSession, true);
	}

	@Override
	public void clearCache(List<SamlSpSession> samlSpSessions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SamlSpSession samlSpSession : samlSpSessions) {
			entityCache.removeResult(
				SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
				SamlSpSessionImpl.class, samlSpSession.getPrimaryKey());

			clearUniqueFindersCache(
				(SamlSpSessionModelImpl)samlSpSession, true);
		}
	}

	protected void cacheUniqueFindersCache(
		SamlSpSessionModelImpl samlSpSessionModelImpl) {

		Object[] args = new Object[] {
			samlSpSessionModelImpl.getSamlSpSessionKey()
		};

		finderCache.putResult(
			_finderPathCountBySamlSpSessionKey, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchBySamlSpSessionKey, args, samlSpSessionModelImpl,
			false);

		args = new Object[] {samlSpSessionModelImpl.getJSessionId()};

		finderCache.putResult(
			_finderPathCountByJSessionId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByJSessionId, args, samlSpSessionModelImpl, false);

		args = new Object[] {samlSpSessionModelImpl.getSessionIndex()};

		finderCache.putResult(
			_finderPathCountBySessionIndex, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchBySessionIndex, args, samlSpSessionModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		SamlSpSessionModelImpl samlSpSessionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				samlSpSessionModelImpl.getSamlSpSessionKey()
			};

			finderCache.removeResult(_finderPathCountBySamlSpSessionKey, args);
			finderCache.removeResult(_finderPathFetchBySamlSpSessionKey, args);
		}

		if ((samlSpSessionModelImpl.getColumnBitmask() &
			 _finderPathFetchBySamlSpSessionKey.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				samlSpSessionModelImpl.getOriginalSamlSpSessionKey()
			};

			finderCache.removeResult(_finderPathCountBySamlSpSessionKey, args);
			finderCache.removeResult(_finderPathFetchBySamlSpSessionKey, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				samlSpSessionModelImpl.getJSessionId()
			};

			finderCache.removeResult(_finderPathCountByJSessionId, args);
			finderCache.removeResult(_finderPathFetchByJSessionId, args);
		}

		if ((samlSpSessionModelImpl.getColumnBitmask() &
			 _finderPathFetchByJSessionId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				samlSpSessionModelImpl.getOriginalJSessionId()
			};

			finderCache.removeResult(_finderPathCountByJSessionId, args);
			finderCache.removeResult(_finderPathFetchByJSessionId, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				samlSpSessionModelImpl.getSessionIndex()
			};

			finderCache.removeResult(_finderPathCountBySessionIndex, args);
			finderCache.removeResult(_finderPathFetchBySessionIndex, args);
		}

		if ((samlSpSessionModelImpl.getColumnBitmask() &
			 _finderPathFetchBySessionIndex.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				samlSpSessionModelImpl.getOriginalSessionIndex()
			};

			finderCache.removeResult(_finderPathCountBySessionIndex, args);
			finderCache.removeResult(_finderPathFetchBySessionIndex, args);
		}
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
		catch (NoSuchSpSessionException nsee) {
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
		catch (Exception e) {
			throw processException(e);
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

		Date now = new Date();

		if (isNew && (samlSpSession.getCreateDate() == null)) {
			if (serviceContext == null) {
				samlSpSession.setCreateDate(now);
			}
			else {
				samlSpSession.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!samlSpSessionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				samlSpSession.setModifiedDate(now);
			}
			else {
				samlSpSession.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (samlSpSession.isNew()) {
				session.save(samlSpSession);

				samlSpSession.setNew(false);
			}
			else {
				samlSpSession = (SamlSpSession)session.merge(samlSpSession);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SamlSpSessionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				samlSpSessionModelImpl.getNameIdValue()
			};

			finderCache.removeResult(_finderPathCountByNameIdValue, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByNameIdValue, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((samlSpSessionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByNameIdValue.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					samlSpSessionModelImpl.getOriginalNameIdValue()
				};

				finderCache.removeResult(_finderPathCountByNameIdValue, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByNameIdValue, args);

				args = new Object[] {samlSpSessionModelImpl.getNameIdValue()};

				finderCache.removeResult(_finderPathCountByNameIdValue, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByNameIdValue, args);
			}
		}

		entityCache.putResult(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionImpl.class, samlSpSession.getPrimaryKey(),
			samlSpSession, false);

		clearUniqueFindersCache(samlSpSessionModelImpl, false);
		cacheUniqueFindersCache(samlSpSessionModelImpl);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		List<SamlSpSession> list = null;

		if (useFinderCache) {
			list = (List<SamlSpSession>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SAMLSPSESSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SAMLSPSESSION;

				if (pagination) {
					sql = sql.concat(SamlSpSessionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SamlSpSession>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlSpSession>)QueryUtil.list(
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
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SAMLSPSESSION);

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
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpSessionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpSessionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchBySamlSpSessionKey = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpSessionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchBySamlSpSessionKey", new String[] {String.class.getName()},
			SamlSpSessionModelImpl.SAMLSPSESSIONKEY_COLUMN_BITMASK);

		_finderPathCountBySamlSpSessionKey = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamlSpSessionKey", new String[] {String.class.getName()});

		_finderPathFetchByJSessionId = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpSessionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByJSessionId", new String[] {String.class.getName()},
			SamlSpSessionModelImpl.JSESSIONID_COLUMN_BITMASK);

		_finderPathCountByJSessionId = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByJSessionId",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByNameIdValue = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpSessionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByNameIdValue",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByNameIdValue = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpSessionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByNameIdValue", new String[] {String.class.getName()},
			SamlSpSessionModelImpl.NAMEIDVALUE_COLUMN_BITMASK);

		_finderPathCountByNameIdValue = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByNameIdValue",
			new String[] {String.class.getName()});

		_finderPathFetchBySessionIndex = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpSessionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchBySessionIndex", new String[] {String.class.getName()},
			SamlSpSessionModelImpl.SESSIONINDEX_COLUMN_BITMASK);

		_finderPathCountBySessionIndex = new FinderPath(
			SamlSpSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpSessionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySessionIndex",
			new String[] {String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(SamlSpSessionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
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

}