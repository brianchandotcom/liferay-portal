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

package com.liferay.oauth2.provider.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException;
import com.liferay.oauth2.provider.model.OAuth2Token;
import com.liferay.oauth2.provider.model.impl.OAuth2TokenImpl;
import com.liferay.oauth2.provider.model.impl.OAuth2TokenModelImpl;
import com.liferay.oauth2.provider.service.persistence.OAuth2TokenPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the o auth2 token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2TokenPersistence
 * @see com.liferay.oauth2.provider.service.persistence.OAuth2TokenUtil
 * @generated
 */
@ProviderType
public class OAuth2TokenPersistenceImpl extends BasePersistenceImpl<OAuth2Token>
	implements OAuth2TokenPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link OAuth2TokenUtil} to access the o auth2 token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = OAuth2TokenImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, OAuth2TokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, OAuth2TokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_CONTENT = new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, OAuth2TokenImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByContent",
			new String[] { String.class.getName() },
			OAuth2TokenModelImpl.OAUTH2TOKENCONTENT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CONTENT = new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByContent",
			new String[] { String.class.getName() });

	/**
	 * Returns the o auth2 token where oAuth2TokenContent = &#63; or throws a {@link NoSuchOAuth2TokenException} if it could not be found.
	 *
	 * @param oAuth2TokenContent the o auth2 token content
	 * @return the matching o auth2 token
	 * @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token findByContent(String oAuth2TokenContent)
		throws NoSuchOAuth2TokenException {
		OAuth2Token oAuth2Token = fetchByContent(oAuth2TokenContent);

		if (oAuth2Token == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("oAuth2TokenContent=");
			msg.append(oAuth2TokenContent);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchOAuth2TokenException(msg.toString());
		}

		return oAuth2Token;
	}

	/**
	 * Returns the o auth2 token where oAuth2TokenContent = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param oAuth2TokenContent the o auth2 token content
	 * @return the matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token fetchByContent(String oAuth2TokenContent) {
		return fetchByContent(oAuth2TokenContent, true);
	}

	/**
	 * Returns the o auth2 token where oAuth2TokenContent = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param oAuth2TokenContent the o auth2 token content
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token fetchByContent(String oAuth2TokenContent,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { oAuth2TokenContent };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CONTENT,
					finderArgs, this);
		}

		if (result instanceof OAuth2Token) {
			OAuth2Token oAuth2Token = (OAuth2Token)result;

			if (!Objects.equals(oAuth2TokenContent,
						oAuth2Token.getOAuth2TokenContent())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_OAUTH2TOKEN_WHERE);

			boolean bindOAuth2TokenContent = false;

			if (oAuth2TokenContent == null) {
				query.append(_FINDER_COLUMN_CONTENT_OAUTH2TOKENCONTENT_1);
			}
			else if (oAuth2TokenContent.equals("")) {
				query.append(_FINDER_COLUMN_CONTENT_OAUTH2TOKENCONTENT_3);
			}
			else {
				bindOAuth2TokenContent = true;

				query.append(_FINDER_COLUMN_CONTENT_OAUTH2TOKENCONTENT_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindOAuth2TokenContent) {
					qPos.add(oAuth2TokenContent);
				}

				List<OAuth2Token> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_CONTENT,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"OAuth2TokenPersistenceImpl.fetchByContent(String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					OAuth2Token oAuth2Token = list.get(0);

					result = oAuth2Token;

					cacheResult(oAuth2Token);

					if ((oAuth2Token.getOAuth2TokenContent() == null) ||
							!oAuth2Token.getOAuth2TokenContent()
											.equals(oAuth2TokenContent)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CONTENT,
							finderArgs, oAuth2Token);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_CONTENT,
					finderArgs);

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
			return (OAuth2Token)result;
		}
	}

	/**
	 * Removes the o auth2 token where oAuth2TokenContent = &#63; from the database.
	 *
	 * @param oAuth2TokenContent the o auth2 token content
	 * @return the o auth2 token that was removed
	 */
	@Override
	public OAuth2Token removeByContent(String oAuth2TokenContent)
		throws NoSuchOAuth2TokenException {
		OAuth2Token oAuth2Token = findByContent(oAuth2TokenContent);

		return remove(oAuth2Token);
	}

	/**
	 * Returns the number of o auth2 tokens where oAuth2TokenContent = &#63;.
	 *
	 * @param oAuth2TokenContent the o auth2 token content
	 * @return the number of matching o auth2 tokens
	 */
	@Override
	public int countByContent(String oAuth2TokenContent) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CONTENT;

		Object[] finderArgs = new Object[] { oAuth2TokenContent };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2TOKEN_WHERE);

			boolean bindOAuth2TokenContent = false;

			if (oAuth2TokenContent == null) {
				query.append(_FINDER_COLUMN_CONTENT_OAUTH2TOKENCONTENT_1);
			}
			else if (oAuth2TokenContent.equals("")) {
				query.append(_FINDER_COLUMN_CONTENT_OAUTH2TOKENCONTENT_3);
			}
			else {
				bindOAuth2TokenContent = true;

				query.append(_FINDER_COLUMN_CONTENT_OAUTH2TOKENCONTENT_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindOAuth2TokenContent) {
					qPos.add(oAuth2TokenContent);
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

	private static final String _FINDER_COLUMN_CONTENT_OAUTH2TOKENCONTENT_1 = "oAuth2Token.oAuth2TokenContent IS NULL";
	private static final String _FINDER_COLUMN_CONTENT_OAUTH2TOKENCONTENT_2 = "CAST_CLOB_TEXT(oAuth2Token.oAuth2TokenContent) = ?";
	private static final String _FINDER_COLUMN_CONTENT_OAUTH2TOKENCONTENT_3 = "(oAuth2Token.oAuth2TokenContent IS NULL OR CAST_CLOB_TEXT(oAuth2Token.oAuth2TokenContent) = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_A = new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, OAuth2TokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A = new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, OAuth2TokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA",
			new String[] { Long.class.getName() },
			OAuth2TokenModelImpl.OAUTH2APPLICATIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_A = new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the o auth2 tokens where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the matching o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findByA(long oAuth2ApplicationId) {
		return findByA(oAuth2ApplicationId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 tokens where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 tokens
	 * @param end the upper bound of the range of o auth2 tokens (not inclusive)
	 * @return the range of matching o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findByA(long oAuth2ApplicationId, int start,
		int end) {
		return findByA(oAuth2ApplicationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 tokens where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 tokens
	 * @param end the upper bound of the range of o auth2 tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findByA(long oAuth2ApplicationId, int start,
		int end, OrderByComparator<OAuth2Token> orderByComparator) {
		return findByA(oAuth2ApplicationId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 tokens where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 tokens
	 * @param end the upper bound of the range of o auth2 tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findByA(long oAuth2ApplicationId, int start,
		int end, OrderByComparator<OAuth2Token> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A;
			finderArgs = new Object[] { oAuth2ApplicationId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_A;
			finderArgs = new Object[] {
					oAuth2ApplicationId,
					
					start, end, orderByComparator
				};
		}

		List<OAuth2Token> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2Token>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2Token oAuth2Token : list) {
					if ((oAuth2ApplicationId != oAuth2Token.getOAuth2ApplicationId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_OAUTH2TOKEN_WHERE);

			query.append(_FINDER_COLUMN_A_OAUTH2APPLICATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(OAuth2TokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2ApplicationId);

				if (!pagination) {
					list = (List<OAuth2Token>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2Token>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 token
	 * @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token findByA_First(long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException {
		OAuth2Token oAuth2Token = fetchByA_First(oAuth2ApplicationId,
				orderByComparator);

		if (oAuth2Token != null) {
			return oAuth2Token;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2ApplicationId=");
		msg.append(oAuth2ApplicationId);

		msg.append("}");

		throw new NoSuchOAuth2TokenException(msg.toString());
	}

	/**
	 * Returns the first o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token fetchByA_First(long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator) {
		List<OAuth2Token> list = findByA(oAuth2ApplicationId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 token
	 * @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token findByA_Last(long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException {
		OAuth2Token oAuth2Token = fetchByA_Last(oAuth2ApplicationId,
				orderByComparator);

		if (oAuth2Token != null) {
			return oAuth2Token;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2ApplicationId=");
		msg.append(oAuth2ApplicationId);

		msg.append("}");

		throw new NoSuchOAuth2TokenException(msg.toString());
	}

	/**
	 * Returns the last o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token fetchByA_Last(long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator) {
		int count = countByA(oAuth2ApplicationId);

		if (count == 0) {
			return null;
		}

		List<OAuth2Token> list = findByA(oAuth2ApplicationId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 tokens before and after the current o auth2 token in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2TokenId the primary key of the current o auth2 token
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 token
	 * @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	 */
	@Override
	public OAuth2Token[] findByA_PrevAndNext(long oAuth2TokenId,
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException {
		OAuth2Token oAuth2Token = findByPrimaryKey(oAuth2TokenId);

		Session session = null;

		try {
			session = openSession();

			OAuth2Token[] array = new OAuth2TokenImpl[3];

			array[0] = getByA_PrevAndNext(session, oAuth2Token,
					oAuth2ApplicationId, orderByComparator, true);

			array[1] = oAuth2Token;

			array[2] = getByA_PrevAndNext(session, oAuth2Token,
					oAuth2ApplicationId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2Token getByA_PrevAndNext(Session session,
		OAuth2Token oAuth2Token, long oAuth2ApplicationId,
		OrderByComparator<OAuth2Token> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2TOKEN_WHERE);

		query.append(_FINDER_COLUMN_A_OAUTH2APPLICATIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(OAuth2TokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(oAuth2ApplicationId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(oAuth2Token);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OAuth2Token> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 tokens where oAuth2ApplicationId = &#63; from the database.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 */
	@Override
	public void removeByA(long oAuth2ApplicationId) {
		for (OAuth2Token oAuth2Token : findByA(oAuth2ApplicationId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(oAuth2Token);
		}
	}

	/**
	 * Returns the number of o auth2 tokens where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the number of matching o auth2 tokens
	 */
	@Override
	public int countByA(long oAuth2ApplicationId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_A;

		Object[] finderArgs = new Object[] { oAuth2ApplicationId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2TOKEN_WHERE);

			query.append(_FINDER_COLUMN_A_OAUTH2APPLICATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2ApplicationId);

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

	private static final String _FINDER_COLUMN_A_OAUTH2APPLICATIONID_2 = "oAuth2Token.oAuth2ApplicationId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_REFRESHTOKEN =
		new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, OAuth2TokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRefreshToken",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REFRESHTOKEN =
		new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, OAuth2TokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRefreshToken",
			new String[] { Long.class.getName() },
			OAuth2TokenModelImpl.OAUTH2REFRESHTOKENID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_REFRESHTOKEN = new FinderPath(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRefreshToken",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @return the matching o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findByRefreshToken(long oAuth2RefreshTokenId) {
		return findByRefreshToken(oAuth2RefreshTokenId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param start the lower bound of the range of o auth2 tokens
	 * @param end the upper bound of the range of o auth2 tokens (not inclusive)
	 * @return the range of matching o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findByRefreshToken(long oAuth2RefreshTokenId,
		int start, int end) {
		return findByRefreshToken(oAuth2RefreshTokenId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param start the lower bound of the range of o auth2 tokens
	 * @param end the upper bound of the range of o auth2 tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findByRefreshToken(long oAuth2RefreshTokenId,
		int start, int end, OrderByComparator<OAuth2Token> orderByComparator) {
		return findByRefreshToken(oAuth2RefreshTokenId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param start the lower bound of the range of o auth2 tokens
	 * @param end the upper bound of the range of o auth2 tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findByRefreshToken(long oAuth2RefreshTokenId,
		int start, int end, OrderByComparator<OAuth2Token> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REFRESHTOKEN;
			finderArgs = new Object[] { oAuth2RefreshTokenId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_REFRESHTOKEN;
			finderArgs = new Object[] {
					oAuth2RefreshTokenId,
					
					start, end, orderByComparator
				};
		}

		List<OAuth2Token> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2Token>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2Token oAuth2Token : list) {
					if ((oAuth2RefreshTokenId != oAuth2Token.getOAuth2RefreshTokenId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_OAUTH2TOKEN_WHERE);

			query.append(_FINDER_COLUMN_REFRESHTOKEN_OAUTH2REFRESHTOKENID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(OAuth2TokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2RefreshTokenId);

				if (!pagination) {
					list = (List<OAuth2Token>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2Token>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 token
	 * @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token findByRefreshToken_First(long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException {
		OAuth2Token oAuth2Token = fetchByRefreshToken_First(oAuth2RefreshTokenId,
				orderByComparator);

		if (oAuth2Token != null) {
			return oAuth2Token;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2RefreshTokenId=");
		msg.append(oAuth2RefreshTokenId);

		msg.append("}");

		throw new NoSuchOAuth2TokenException(msg.toString());
	}

	/**
	 * Returns the first o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token fetchByRefreshToken_First(long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator) {
		List<OAuth2Token> list = findByRefreshToken(oAuth2RefreshTokenId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 token
	 * @throws NoSuchOAuth2TokenException if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token findByRefreshToken_Last(long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException {
		OAuth2Token oAuth2Token = fetchByRefreshToken_Last(oAuth2RefreshTokenId,
				orderByComparator);

		if (oAuth2Token != null) {
			return oAuth2Token;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2RefreshTokenId=");
		msg.append(oAuth2RefreshTokenId);

		msg.append("}");

		throw new NoSuchOAuth2TokenException(msg.toString());
	}

	/**
	 * Returns the last o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 token, or <code>null</code> if a matching o auth2 token could not be found
	 */
	@Override
	public OAuth2Token fetchByRefreshToken_Last(long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator) {
		int count = countByRefreshToken(oAuth2RefreshTokenId);

		if (count == 0) {
			return null;
		}

		List<OAuth2Token> list = findByRefreshToken(oAuth2RefreshTokenId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 tokens before and after the current o auth2 token in the ordered set where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2TokenId the primary key of the current o auth2 token
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 token
	 * @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	 */
	@Override
	public OAuth2Token[] findByRefreshToken_PrevAndNext(long oAuth2TokenId,
		long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator)
		throws NoSuchOAuth2TokenException {
		OAuth2Token oAuth2Token = findByPrimaryKey(oAuth2TokenId);

		Session session = null;

		try {
			session = openSession();

			OAuth2Token[] array = new OAuth2TokenImpl[3];

			array[0] = getByRefreshToken_PrevAndNext(session, oAuth2Token,
					oAuth2RefreshTokenId, orderByComparator, true);

			array[1] = oAuth2Token;

			array[2] = getByRefreshToken_PrevAndNext(session, oAuth2Token,
					oAuth2RefreshTokenId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2Token getByRefreshToken_PrevAndNext(Session session,
		OAuth2Token oAuth2Token, long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2Token> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2TOKEN_WHERE);

		query.append(_FINDER_COLUMN_REFRESHTOKEN_OAUTH2REFRESHTOKENID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(OAuth2TokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(oAuth2RefreshTokenId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(oAuth2Token);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OAuth2Token> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 tokens where oAuth2RefreshTokenId = &#63; from the database.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 */
	@Override
	public void removeByRefreshToken(long oAuth2RefreshTokenId) {
		for (OAuth2Token oAuth2Token : findByRefreshToken(
				oAuth2RefreshTokenId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(oAuth2Token);
		}
	}

	/**
	 * Returns the number of o auth2 tokens where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @return the number of matching o auth2 tokens
	 */
	@Override
	public int countByRefreshToken(long oAuth2RefreshTokenId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_REFRESHTOKEN;

		Object[] finderArgs = new Object[] { oAuth2RefreshTokenId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2TOKEN_WHERE);

			query.append(_FINDER_COLUMN_REFRESHTOKEN_OAUTH2REFRESHTOKENID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2RefreshTokenId);

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

	private static final String _FINDER_COLUMN_REFRESHTOKEN_OAUTH2REFRESHTOKENID_2 =
		"oAuth2Token.oAuth2RefreshTokenId = ?";

	public OAuth2TokenPersistenceImpl() {
		setModelClass(OAuth2Token.class);
	}

	/**
	 * Caches the o auth2 token in the entity cache if it is enabled.
	 *
	 * @param oAuth2Token the o auth2 token
	 */
	@Override
	public void cacheResult(OAuth2Token oAuth2Token) {
		entityCache.putResult(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenImpl.class, oAuth2Token.getPrimaryKey(), oAuth2Token);

		finderCache.putResult(FINDER_PATH_FETCH_BY_CONTENT,
			new Object[] { oAuth2Token.getOAuth2TokenContent() }, oAuth2Token);

		oAuth2Token.resetOriginalValues();
	}

	/**
	 * Caches the o auth2 tokens in the entity cache if it is enabled.
	 *
	 * @param oAuth2Tokens the o auth2 tokens
	 */
	@Override
	public void cacheResult(List<OAuth2Token> oAuth2Tokens) {
		for (OAuth2Token oAuth2Token : oAuth2Tokens) {
			if (entityCache.getResult(
						OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
						OAuth2TokenImpl.class, oAuth2Token.getPrimaryKey()) == null) {
				cacheResult(oAuth2Token);
			}
			else {
				oAuth2Token.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all o auth2 tokens.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuth2TokenImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the o auth2 token.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OAuth2Token oAuth2Token) {
		entityCache.removeResult(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenImpl.class, oAuth2Token.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((OAuth2TokenModelImpl)oAuth2Token, true);
	}

	@Override
	public void clearCache(List<OAuth2Token> oAuth2Tokens) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OAuth2Token oAuth2Token : oAuth2Tokens) {
			entityCache.removeResult(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
				OAuth2TokenImpl.class, oAuth2Token.getPrimaryKey());

			clearUniqueFindersCache((OAuth2TokenModelImpl)oAuth2Token, true);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuth2TokenModelImpl oAuth2TokenModelImpl) {
		Object[] args = new Object[] {
				oAuth2TokenModelImpl.getOAuth2TokenContent()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_CONTENT, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_CONTENT, args,
			oAuth2TokenModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		OAuth2TokenModelImpl oAuth2TokenModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					oAuth2TokenModelImpl.getOAuth2TokenContent()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CONTENT, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CONTENT, args);
		}

		if ((oAuth2TokenModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CONTENT.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					oAuth2TokenModelImpl.getOriginalOAuth2TokenContent()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CONTENT, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CONTENT, args);
		}
	}

	/**
	 * Creates a new o auth2 token with the primary key. Does not add the o auth2 token to the database.
	 *
	 * @param oAuth2TokenId the primary key for the new o auth2 token
	 * @return the new o auth2 token
	 */
	@Override
	public OAuth2Token create(long oAuth2TokenId) {
		OAuth2Token oAuth2Token = new OAuth2TokenImpl();

		oAuth2Token.setNew(true);
		oAuth2Token.setPrimaryKey(oAuth2TokenId);

		oAuth2Token.setCompanyId(companyProvider.getCompanyId());

		return oAuth2Token;
	}

	/**
	 * Removes the o auth2 token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2TokenId the primary key of the o auth2 token
	 * @return the o auth2 token that was removed
	 * @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	 */
	@Override
	public OAuth2Token remove(long oAuth2TokenId)
		throws NoSuchOAuth2TokenException {
		return remove((Serializable)oAuth2TokenId);
	}

	/**
	 * Removes the o auth2 token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth2 token
	 * @return the o auth2 token that was removed
	 * @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	 */
	@Override
	public OAuth2Token remove(Serializable primaryKey)
		throws NoSuchOAuth2TokenException {
		Session session = null;

		try {
			session = openSession();

			OAuth2Token oAuth2Token = (OAuth2Token)session.get(OAuth2TokenImpl.class,
					primaryKey);

			if (oAuth2Token == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOAuth2TokenException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(oAuth2Token);
		}
		catch (NoSuchOAuth2TokenException nsee) {
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
	protected OAuth2Token removeImpl(OAuth2Token oAuth2Token) {
		oAuth2Token = toUnwrappedModel(oAuth2Token);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuth2Token)) {
				oAuth2Token = (OAuth2Token)session.get(OAuth2TokenImpl.class,
						oAuth2Token.getPrimaryKeyObj());
			}

			if (oAuth2Token != null) {
				session.delete(oAuth2Token);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (oAuth2Token != null) {
			clearCache(oAuth2Token);
		}

		return oAuth2Token;
	}

	@Override
	public OAuth2Token updateImpl(OAuth2Token oAuth2Token) {
		oAuth2Token = toUnwrappedModel(oAuth2Token);

		boolean isNew = oAuth2Token.isNew();

		OAuth2TokenModelImpl oAuth2TokenModelImpl = (OAuth2TokenModelImpl)oAuth2Token;

		Session session = null;

		try {
			session = openSession();

			if (oAuth2Token.isNew()) {
				session.save(oAuth2Token);

				oAuth2Token.setNew(false);
			}
			else {
				oAuth2Token = (OAuth2Token)session.merge(oAuth2Token);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!OAuth2TokenModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					oAuth2TokenModelImpl.getOAuth2ApplicationId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_A, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A,
				args);

			args = new Object[] { oAuth2TokenModelImpl.getOAuth2RefreshTokenId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_REFRESHTOKEN, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REFRESHTOKEN,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((oAuth2TokenModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						oAuth2TokenModelImpl.getOriginalOAuth2ApplicationId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A,
					args);

				args = new Object[] {
						oAuth2TokenModelImpl.getOAuth2ApplicationId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_A,
					args);
			}

			if ((oAuth2TokenModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REFRESHTOKEN.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						oAuth2TokenModelImpl.getOriginalOAuth2RefreshTokenId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_REFRESHTOKEN, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REFRESHTOKEN,
					args);

				args = new Object[] {
						oAuth2TokenModelImpl.getOAuth2RefreshTokenId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_REFRESHTOKEN, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REFRESHTOKEN,
					args);
			}
		}

		entityCache.putResult(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2TokenImpl.class, oAuth2Token.getPrimaryKey(), oAuth2Token,
			false);

		clearUniqueFindersCache(oAuth2TokenModelImpl, false);
		cacheUniqueFindersCache(oAuth2TokenModelImpl);

		oAuth2Token.resetOriginalValues();

		return oAuth2Token;
	}

	protected OAuth2Token toUnwrappedModel(OAuth2Token oAuth2Token) {
		if (oAuth2Token instanceof OAuth2TokenImpl) {
			return oAuth2Token;
		}

		OAuth2TokenImpl oAuth2TokenImpl = new OAuth2TokenImpl();

		oAuth2TokenImpl.setNew(oAuth2Token.isNew());
		oAuth2TokenImpl.setPrimaryKey(oAuth2Token.getPrimaryKey());

		oAuth2TokenImpl.setOAuth2TokenId(oAuth2Token.getOAuth2TokenId());
		oAuth2TokenImpl.setCompanyId(oAuth2Token.getCompanyId());
		oAuth2TokenImpl.setUserId(oAuth2Token.getUserId());
		oAuth2TokenImpl.setUserName(oAuth2Token.getUserName());
		oAuth2TokenImpl.setCreateDate(oAuth2Token.getCreateDate());
		oAuth2TokenImpl.setExpirationDate(oAuth2Token.getExpirationDate());
		oAuth2TokenImpl.setRemoteIPInfo(oAuth2Token.getRemoteIPInfo());
		oAuth2TokenImpl.setOAuth2TokenContent(oAuth2Token.getOAuth2TokenContent());
		oAuth2TokenImpl.setOAuth2ApplicationId(oAuth2Token.getOAuth2ApplicationId());
		oAuth2TokenImpl.setOAuth2TokenType(oAuth2Token.getOAuth2TokenType());
		oAuth2TokenImpl.setOAuth2RefreshTokenId(oAuth2Token.getOAuth2RefreshTokenId());
		oAuth2TokenImpl.setScopes(oAuth2Token.getScopes());

		return oAuth2TokenImpl;
	}

	/**
	 * Returns the o auth2 token with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 token
	 * @return the o auth2 token
	 * @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	 */
	@Override
	public OAuth2Token findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOAuth2TokenException {
		OAuth2Token oAuth2Token = fetchByPrimaryKey(primaryKey);

		if (oAuth2Token == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOAuth2TokenException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return oAuth2Token;
	}

	/**
	 * Returns the o auth2 token with the primary key or throws a {@link NoSuchOAuth2TokenException} if it could not be found.
	 *
	 * @param oAuth2TokenId the primary key of the o auth2 token
	 * @return the o auth2 token
	 * @throws NoSuchOAuth2TokenException if a o auth2 token with the primary key could not be found
	 */
	@Override
	public OAuth2Token findByPrimaryKey(long oAuth2TokenId)
		throws NoSuchOAuth2TokenException {
		return findByPrimaryKey((Serializable)oAuth2TokenId);
	}

	/**
	 * Returns the o auth2 token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 token
	 * @return the o auth2 token, or <code>null</code> if a o auth2 token with the primary key could not be found
	 */
	@Override
	public OAuth2Token fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
				OAuth2TokenImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		OAuth2Token oAuth2Token = (OAuth2Token)serializable;

		if (oAuth2Token == null) {
			Session session = null;

			try {
				session = openSession();

				oAuth2Token = (OAuth2Token)session.get(OAuth2TokenImpl.class,
						primaryKey);

				if (oAuth2Token != null) {
					cacheResult(oAuth2Token);
				}
				else {
					entityCache.putResult(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
						OAuth2TokenImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2TokenImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return oAuth2Token;
	}

	/**
	 * Returns the o auth2 token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuth2TokenId the primary key of the o auth2 token
	 * @return the o auth2 token, or <code>null</code> if a o auth2 token with the primary key could not be found
	 */
	@Override
	public OAuth2Token fetchByPrimaryKey(long oAuth2TokenId) {
		return fetchByPrimaryKey((Serializable)oAuth2TokenId);
	}

	@Override
	public Map<Serializable, OAuth2Token> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, OAuth2Token> map = new HashMap<Serializable, OAuth2Token>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			OAuth2Token oAuth2Token = fetchByPrimaryKey(primaryKey);

			if (oAuth2Token != null) {
				map.put(primaryKey, oAuth2Token);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2TokenImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (OAuth2Token)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_OAUTH2TOKEN_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (OAuth2Token oAuth2Token : (List<OAuth2Token>)q.list()) {
				map.put(oAuth2Token.getPrimaryKeyObj(), oAuth2Token);

				cacheResult(oAuth2Token);

				uncachedPrimaryKeys.remove(oAuth2Token.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(OAuth2TokenModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2TokenImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the o auth2 tokens.
	 *
	 * @return the o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 tokens
	 * @param end the upper bound of the range of o auth2 tokens (not inclusive)
	 * @return the range of o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 tokens
	 * @param end the upper bound of the range of o auth2 tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findAll(int start, int end,
		OrderByComparator<OAuth2Token> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 tokens
	 * @param end the upper bound of the range of o auth2 tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of o auth2 tokens
	 */
	@Override
	public List<OAuth2Token> findAll(int start, int end,
		OrderByComparator<OAuth2Token> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<OAuth2Token> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2Token>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_OAUTH2TOKEN);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTH2TOKEN;

				if (pagination) {
					sql = sql.concat(OAuth2TokenModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<OAuth2Token>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2Token>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the o auth2 tokens from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuth2Token oAuth2Token : findAll()) {
			remove(oAuth2Token);
		}
	}

	/**
	 * Returns the number of o auth2 tokens.
	 *
	 * @return the number of o auth2 tokens
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_OAUTH2TOKEN);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuth2TokenModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth2 token persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(OAuth2TokenImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_OAUTH2TOKEN = "SELECT oAuth2Token FROM OAuth2Token oAuth2Token";
	private static final String _SQL_SELECT_OAUTH2TOKEN_WHERE_PKS_IN = "SELECT oAuth2Token FROM OAuth2Token oAuth2Token WHERE oAuth2TokenId IN (";
	private static final String _SQL_SELECT_OAUTH2TOKEN_WHERE = "SELECT oAuth2Token FROM OAuth2Token oAuth2Token WHERE ";
	private static final String _SQL_COUNT_OAUTH2TOKEN = "SELECT COUNT(oAuth2Token) FROM OAuth2Token oAuth2Token";
	private static final String _SQL_COUNT_OAUTH2TOKEN_WHERE = "SELECT COUNT(oAuth2Token) FROM OAuth2Token oAuth2Token WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuth2Token.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No OAuth2Token exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No OAuth2Token exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(OAuth2TokenPersistenceImpl.class);
}