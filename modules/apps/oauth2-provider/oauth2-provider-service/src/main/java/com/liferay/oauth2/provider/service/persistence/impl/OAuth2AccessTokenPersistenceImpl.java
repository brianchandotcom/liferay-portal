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

import com.liferay.oauth2.provider.exception.NoSuchOAuth2AccessTokenException;
import com.liferay.oauth2.provider.model.OAuth2AccessToken;
import com.liferay.oauth2.provider.model.impl.OAuth2AccessTokenImpl;
import com.liferay.oauth2.provider.model.impl.OAuth2AccessTokenModelImpl;
import com.liferay.oauth2.provider.service.persistence.OAuth2AccessTokenPersistence;

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
 * The persistence implementation for the o auth2 access token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AccessTokenPersistence
 * @see com.liferay.oauth2.provider.service.persistence.OAuth2AccessTokenUtil
 * @generated
 */
@ProviderType
public class OAuth2AccessTokenPersistenceImpl extends BasePersistenceImpl<OAuth2AccessToken>
	implements OAuth2AccessTokenPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link OAuth2AccessTokenUtil} to access the o auth2 access token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = OAuth2AccessTokenImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED,
			OAuth2AccessTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED,
			OAuth2AccessTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_OAUTH2APPLICATIONID =
		new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED,
			OAuth2AccessTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByOAuth2ApplicationId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID =
		new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED,
			OAuth2AccessTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByOAuth2ApplicationId", new String[] { Long.class.getName() },
			OAuth2AccessTokenModelImpl.OAUTH2APPLICATIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONID = new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByOAuth2ApplicationId", new String[] { Long.class.getName() });

	/**
	 * Returns all the o auth2 access tokens where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the matching o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findByOAuth2ApplicationId(
		long oAuth2ApplicationId) {
		return findByOAuth2ApplicationId(oAuth2ApplicationId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 access tokens where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 access tokens
	 * @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	 * @return the range of matching o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end) {
		return findByOAuth2ApplicationId(oAuth2ApplicationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 access tokens where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 access tokens
	 * @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2AccessToken> orderByComparator) {
		return findByOAuth2ApplicationId(oAuth2ApplicationId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 access tokens where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 access tokens
	 * @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2AccessToken> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID;
			finderArgs = new Object[] { oAuth2ApplicationId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_OAUTH2APPLICATIONID;
			finderArgs = new Object[] {
					oAuth2ApplicationId,
					
					start, end, orderByComparator
				};
		}

		List<OAuth2AccessToken> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2AccessToken>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2AccessToken oAuth2AccessToken : list) {
					if ((oAuth2ApplicationId != oAuth2AccessToken.getOAuth2ApplicationId())) {
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

			query.append(_SQL_SELECT_OAUTH2ACCESSTOKEN_WHERE);

			query.append(_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(OAuth2AccessTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2ApplicationId);

				if (!pagination) {
					list = (List<OAuth2AccessToken>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2AccessToken>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first o auth2 access token in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 access token
	 * @throws NoSuchOAuth2AccessTokenException if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken findByOAuth2ApplicationId_First(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException {
		OAuth2AccessToken oAuth2AccessToken = fetchByOAuth2ApplicationId_First(oAuth2ApplicationId,
				orderByComparator);

		if (oAuth2AccessToken != null) {
			return oAuth2AccessToken;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2ApplicationId=");
		msg.append(oAuth2ApplicationId);

		msg.append("}");

		throw new NoSuchOAuth2AccessTokenException(msg.toString());
	}

	/**
	 * Returns the first o auth2 access token in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken fetchByOAuth2ApplicationId_First(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2AccessToken> orderByComparator) {
		List<OAuth2AccessToken> list = findByOAuth2ApplicationId(oAuth2ApplicationId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 access token in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 access token
	 * @throws NoSuchOAuth2AccessTokenException if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken findByOAuth2ApplicationId_Last(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException {
		OAuth2AccessToken oAuth2AccessToken = fetchByOAuth2ApplicationId_Last(oAuth2ApplicationId,
				orderByComparator);

		if (oAuth2AccessToken != null) {
			return oAuth2AccessToken;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2ApplicationId=");
		msg.append(oAuth2ApplicationId);

		msg.append("}");

		throw new NoSuchOAuth2AccessTokenException(msg.toString());
	}

	/**
	 * Returns the last o auth2 access token in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken fetchByOAuth2ApplicationId_Last(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2AccessToken> orderByComparator) {
		int count = countByOAuth2ApplicationId(oAuth2ApplicationId);

		if (count == 0) {
			return null;
		}

		List<OAuth2AccessToken> list = findByOAuth2ApplicationId(oAuth2ApplicationId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 access tokens before and after the current o auth2 access token in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2AccessTokenId the primary key of the current o auth2 access token
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 access token
	 * @throws NoSuchOAuth2AccessTokenException if a o auth2 access token with the primary key could not be found
	 */
	@Override
	public OAuth2AccessToken[] findByOAuth2ApplicationId_PrevAndNext(
		long oAuth2AccessTokenId, long oAuth2ApplicationId,
		OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException {
		OAuth2AccessToken oAuth2AccessToken = findByPrimaryKey(oAuth2AccessTokenId);

		Session session = null;

		try {
			session = openSession();

			OAuth2AccessToken[] array = new OAuth2AccessTokenImpl[3];

			array[0] = getByOAuth2ApplicationId_PrevAndNext(session,
					oAuth2AccessToken, oAuth2ApplicationId, orderByComparator,
					true);

			array[1] = oAuth2AccessToken;

			array[2] = getByOAuth2ApplicationId_PrevAndNext(session,
					oAuth2AccessToken, oAuth2ApplicationId, orderByComparator,
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

	protected OAuth2AccessToken getByOAuth2ApplicationId_PrevAndNext(
		Session session, OAuth2AccessToken oAuth2AccessToken,
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2AccessToken> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2ACCESSTOKEN_WHERE);

		query.append(_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2);

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
			query.append(OAuth2AccessTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(oAuth2ApplicationId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(oAuth2AccessToken);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OAuth2AccessToken> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 access tokens where oAuth2ApplicationId = &#63; from the database.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 */
	@Override
	public void removeByOAuth2ApplicationId(long oAuth2ApplicationId) {
		for (OAuth2AccessToken oAuth2AccessToken : findByOAuth2ApplicationId(
				oAuth2ApplicationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(oAuth2AccessToken);
		}
	}

	/**
	 * Returns the number of o auth2 access tokens where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the number of matching o auth2 access tokens
	 */
	@Override
	public int countByOAuth2ApplicationId(long oAuth2ApplicationId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONID;

		Object[] finderArgs = new Object[] { oAuth2ApplicationId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2ACCESSTOKEN_WHERE);

			query.append(_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2);

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

	private static final String _FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2 =
		"oAuth2AccessToken.oAuth2ApplicationId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_OAUTH2REFRESHTOKENID =
		new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED,
			OAuth2AccessTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByOAuth2RefreshTokenId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2REFRESHTOKENID =
		new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED,
			OAuth2AccessTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByOAuth2RefreshTokenId",
			new String[] { Long.class.getName() },
			OAuth2AccessTokenModelImpl.OAUTH2REFRESHTOKENID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_OAUTH2REFRESHTOKENID = new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByOAuth2RefreshTokenId", new String[] { Long.class.getName() });

	/**
	 * Returns all the o auth2 access tokens where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @return the matching o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findByOAuth2RefreshTokenId(
		long oAuth2RefreshTokenId) {
		return findByOAuth2RefreshTokenId(oAuth2RefreshTokenId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 access tokens where oAuth2RefreshTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param start the lower bound of the range of o auth2 access tokens
	 * @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	 * @return the range of matching o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findByOAuth2RefreshTokenId(
		long oAuth2RefreshTokenId, int start, int end) {
		return findByOAuth2RefreshTokenId(oAuth2RefreshTokenId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 access tokens where oAuth2RefreshTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param start the lower bound of the range of o auth2 access tokens
	 * @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findByOAuth2RefreshTokenId(
		long oAuth2RefreshTokenId, int start, int end,
		OrderByComparator<OAuth2AccessToken> orderByComparator) {
		return findByOAuth2RefreshTokenId(oAuth2RefreshTokenId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 access tokens where oAuth2RefreshTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param start the lower bound of the range of o auth2 access tokens
	 * @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findByOAuth2RefreshTokenId(
		long oAuth2RefreshTokenId, int start, int end,
		OrderByComparator<OAuth2AccessToken> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2REFRESHTOKENID;
			finderArgs = new Object[] { oAuth2RefreshTokenId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_OAUTH2REFRESHTOKENID;
			finderArgs = new Object[] {
					oAuth2RefreshTokenId,
					
					start, end, orderByComparator
				};
		}

		List<OAuth2AccessToken> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2AccessToken>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2AccessToken oAuth2AccessToken : list) {
					if ((oAuth2RefreshTokenId != oAuth2AccessToken.getOAuth2RefreshTokenId())) {
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

			query.append(_SQL_SELECT_OAUTH2ACCESSTOKEN_WHERE);

			query.append(_FINDER_COLUMN_OAUTH2REFRESHTOKENID_OAUTH2REFRESHTOKENID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(OAuth2AccessTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2RefreshTokenId);

				if (!pagination) {
					list = (List<OAuth2AccessToken>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2AccessToken>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first o auth2 access token in the ordered set where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 access token
	 * @throws NoSuchOAuth2AccessTokenException if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken findByOAuth2RefreshTokenId_First(
		long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException {
		OAuth2AccessToken oAuth2AccessToken = fetchByOAuth2RefreshTokenId_First(oAuth2RefreshTokenId,
				orderByComparator);

		if (oAuth2AccessToken != null) {
			return oAuth2AccessToken;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2RefreshTokenId=");
		msg.append(oAuth2RefreshTokenId);

		msg.append("}");

		throw new NoSuchOAuth2AccessTokenException(msg.toString());
	}

	/**
	 * Returns the first o auth2 access token in the ordered set where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken fetchByOAuth2RefreshTokenId_First(
		long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2AccessToken> orderByComparator) {
		List<OAuth2AccessToken> list = findByOAuth2RefreshTokenId(oAuth2RefreshTokenId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 access token in the ordered set where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 access token
	 * @throws NoSuchOAuth2AccessTokenException if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken findByOAuth2RefreshTokenId_Last(
		long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException {
		OAuth2AccessToken oAuth2AccessToken = fetchByOAuth2RefreshTokenId_Last(oAuth2RefreshTokenId,
				orderByComparator);

		if (oAuth2AccessToken != null) {
			return oAuth2AccessToken;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2RefreshTokenId=");
		msg.append(oAuth2RefreshTokenId);

		msg.append("}");

		throw new NoSuchOAuth2AccessTokenException(msg.toString());
	}

	/**
	 * Returns the last o auth2 access token in the ordered set where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken fetchByOAuth2RefreshTokenId_Last(
		long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2AccessToken> orderByComparator) {
		int count = countByOAuth2RefreshTokenId(oAuth2RefreshTokenId);

		if (count == 0) {
			return null;
		}

		List<OAuth2AccessToken> list = findByOAuth2RefreshTokenId(oAuth2RefreshTokenId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 access tokens before and after the current o auth2 access token in the ordered set where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2AccessTokenId the primary key of the current o auth2 access token
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 access token
	 * @throws NoSuchOAuth2AccessTokenException if a o auth2 access token with the primary key could not be found
	 */
	@Override
	public OAuth2AccessToken[] findByOAuth2RefreshTokenId_PrevAndNext(
		long oAuth2AccessTokenId, long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2AccessToken> orderByComparator)
		throws NoSuchOAuth2AccessTokenException {
		OAuth2AccessToken oAuth2AccessToken = findByPrimaryKey(oAuth2AccessTokenId);

		Session session = null;

		try {
			session = openSession();

			OAuth2AccessToken[] array = new OAuth2AccessTokenImpl[3];

			array[0] = getByOAuth2RefreshTokenId_PrevAndNext(session,
					oAuth2AccessToken, oAuth2RefreshTokenId, orderByComparator,
					true);

			array[1] = oAuth2AccessToken;

			array[2] = getByOAuth2RefreshTokenId_PrevAndNext(session,
					oAuth2AccessToken, oAuth2RefreshTokenId, orderByComparator,
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

	protected OAuth2AccessToken getByOAuth2RefreshTokenId_PrevAndNext(
		Session session, OAuth2AccessToken oAuth2AccessToken,
		long oAuth2RefreshTokenId,
		OrderByComparator<OAuth2AccessToken> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2ACCESSTOKEN_WHERE);

		query.append(_FINDER_COLUMN_OAUTH2REFRESHTOKENID_OAUTH2REFRESHTOKENID_2);

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
			query.append(OAuth2AccessTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(oAuth2RefreshTokenId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(oAuth2AccessToken);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OAuth2AccessToken> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 access tokens where oAuth2RefreshTokenId = &#63; from the database.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 */
	@Override
	public void removeByOAuth2RefreshTokenId(long oAuth2RefreshTokenId) {
		for (OAuth2AccessToken oAuth2AccessToken : findByOAuth2RefreshTokenId(
				oAuth2RefreshTokenId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(oAuth2AccessToken);
		}
	}

	/**
	 * Returns the number of o auth2 access tokens where oAuth2RefreshTokenId = &#63;.
	 *
	 * @param oAuth2RefreshTokenId the o auth2 refresh token ID
	 * @return the number of matching o auth2 access tokens
	 */
	@Override
	public int countByOAuth2RefreshTokenId(long oAuth2RefreshTokenId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_OAUTH2REFRESHTOKENID;

		Object[] finderArgs = new Object[] { oAuth2RefreshTokenId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2ACCESSTOKEN_WHERE);

			query.append(_FINDER_COLUMN_OAUTH2REFRESHTOKENID_OAUTH2REFRESHTOKENID_2);

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

	private static final String _FINDER_COLUMN_OAUTH2REFRESHTOKENID_OAUTH2REFRESHTOKENID_2 =
		"oAuth2AccessToken.oAuth2RefreshTokenId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_TOKENCONTENT = new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED,
			OAuth2AccessTokenImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByTokenContent", new String[] { String.class.getName() },
			OAuth2AccessTokenModelImpl.TOKENCONTENT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TOKENCONTENT = new FinderPath(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTokenContent",
			new String[] { String.class.getName() });

	/**
	 * Returns the o auth2 access token where tokenContent = &#63; or throws a {@link NoSuchOAuth2AccessTokenException} if it could not be found.
	 *
	 * @param tokenContent the token content
	 * @return the matching o auth2 access token
	 * @throws NoSuchOAuth2AccessTokenException if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken findByTokenContent(String tokenContent)
		throws NoSuchOAuth2AccessTokenException {
		OAuth2AccessToken oAuth2AccessToken = fetchByTokenContent(tokenContent);

		if (oAuth2AccessToken == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tokenContent=");
			msg.append(tokenContent);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchOAuth2AccessTokenException(msg.toString());
		}

		return oAuth2AccessToken;
	}

	/**
	 * Returns the o auth2 access token where tokenContent = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param tokenContent the token content
	 * @return the matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken fetchByTokenContent(String tokenContent) {
		return fetchByTokenContent(tokenContent, true);
	}

	/**
	 * Returns the o auth2 access token where tokenContent = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param tokenContent the token content
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching o auth2 access token, or <code>null</code> if a matching o auth2 access token could not be found
	 */
	@Override
	public OAuth2AccessToken fetchByTokenContent(String tokenContent,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { tokenContent };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_TOKENCONTENT,
					finderArgs, this);
		}

		if (result instanceof OAuth2AccessToken) {
			OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken)result;

			if (!Objects.equals(tokenContent,
						oAuth2AccessToken.getTokenContent())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_OAUTH2ACCESSTOKEN_WHERE);

			boolean bindTokenContent = false;

			if (tokenContent == null) {
				query.append(_FINDER_COLUMN_TOKENCONTENT_TOKENCONTENT_1);
			}
			else if (tokenContent.equals("")) {
				query.append(_FINDER_COLUMN_TOKENCONTENT_TOKENCONTENT_3);
			}
			else {
				bindTokenContent = true;

				query.append(_FINDER_COLUMN_TOKENCONTENT_TOKENCONTENT_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindTokenContent) {
					qPos.add(tokenContent);
				}

				List<OAuth2AccessToken> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_TOKENCONTENT,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"OAuth2AccessTokenPersistenceImpl.fetchByTokenContent(String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					OAuth2AccessToken oAuth2AccessToken = list.get(0);

					result = oAuth2AccessToken;

					cacheResult(oAuth2AccessToken);

					if ((oAuth2AccessToken.getTokenContent() == null) ||
							!oAuth2AccessToken.getTokenContent()
												  .equals(tokenContent)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_TOKENCONTENT,
							finderArgs, oAuth2AccessToken);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_TOKENCONTENT,
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
			return (OAuth2AccessToken)result;
		}
	}

	/**
	 * Removes the o auth2 access token where tokenContent = &#63; from the database.
	 *
	 * @param tokenContent the token content
	 * @return the o auth2 access token that was removed
	 */
	@Override
	public OAuth2AccessToken removeByTokenContent(String tokenContent)
		throws NoSuchOAuth2AccessTokenException {
		OAuth2AccessToken oAuth2AccessToken = findByTokenContent(tokenContent);

		return remove(oAuth2AccessToken);
	}

	/**
	 * Returns the number of o auth2 access tokens where tokenContent = &#63;.
	 *
	 * @param tokenContent the token content
	 * @return the number of matching o auth2 access tokens
	 */
	@Override
	public int countByTokenContent(String tokenContent) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TOKENCONTENT;

		Object[] finderArgs = new Object[] { tokenContent };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2ACCESSTOKEN_WHERE);

			boolean bindTokenContent = false;

			if (tokenContent == null) {
				query.append(_FINDER_COLUMN_TOKENCONTENT_TOKENCONTENT_1);
			}
			else if (tokenContent.equals("")) {
				query.append(_FINDER_COLUMN_TOKENCONTENT_TOKENCONTENT_3);
			}
			else {
				bindTokenContent = true;

				query.append(_FINDER_COLUMN_TOKENCONTENT_TOKENCONTENT_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindTokenContent) {
					qPos.add(tokenContent);
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

	private static final String _FINDER_COLUMN_TOKENCONTENT_TOKENCONTENT_1 = "oAuth2AccessToken.tokenContent IS NULL";
	private static final String _FINDER_COLUMN_TOKENCONTENT_TOKENCONTENT_2 = "CAST_CLOB_TEXT(oAuth2AccessToken.tokenContent) = ?";
	private static final String _FINDER_COLUMN_TOKENCONTENT_TOKENCONTENT_3 = "(oAuth2AccessToken.tokenContent IS NULL OR CAST_CLOB_TEXT(oAuth2AccessToken.tokenContent) = '')";

	public OAuth2AccessTokenPersistenceImpl() {
		setModelClass(OAuth2AccessToken.class);
	}

	/**
	 * Caches the o auth2 access token in the entity cache if it is enabled.
	 *
	 * @param oAuth2AccessToken the o auth2 access token
	 */
	@Override
	public void cacheResult(OAuth2AccessToken oAuth2AccessToken) {
		entityCache.putResult(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenImpl.class, oAuth2AccessToken.getPrimaryKey(),
			oAuth2AccessToken);

		finderCache.putResult(FINDER_PATH_FETCH_BY_TOKENCONTENT,
			new Object[] { oAuth2AccessToken.getTokenContent() },
			oAuth2AccessToken);

		oAuth2AccessToken.resetOriginalValues();
	}

	/**
	 * Caches the o auth2 access tokens in the entity cache if it is enabled.
	 *
	 * @param oAuth2AccessTokens the o auth2 access tokens
	 */
	@Override
	public void cacheResult(List<OAuth2AccessToken> oAuth2AccessTokens) {
		for (OAuth2AccessToken oAuth2AccessToken : oAuth2AccessTokens) {
			if (entityCache.getResult(
						OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
						OAuth2AccessTokenImpl.class,
						oAuth2AccessToken.getPrimaryKey()) == null) {
				cacheResult(oAuth2AccessToken);
			}
			else {
				oAuth2AccessToken.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all o auth2 access tokens.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuth2AccessTokenImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the o auth2 access token.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OAuth2AccessToken oAuth2AccessToken) {
		entityCache.removeResult(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenImpl.class, oAuth2AccessToken.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((OAuth2AccessTokenModelImpl)oAuth2AccessToken,
			true);
	}

	@Override
	public void clearCache(List<OAuth2AccessToken> oAuth2AccessTokens) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OAuth2AccessToken oAuth2AccessToken : oAuth2AccessTokens) {
			entityCache.removeResult(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
				OAuth2AccessTokenImpl.class, oAuth2AccessToken.getPrimaryKey());

			clearUniqueFindersCache((OAuth2AccessTokenModelImpl)oAuth2AccessToken,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuth2AccessTokenModelImpl oAuth2AccessTokenModelImpl) {
		Object[] args = new Object[] {
				oAuth2AccessTokenModelImpl.getTokenContent()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_TOKENCONTENT, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_TOKENCONTENT, args,
			oAuth2AccessTokenModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		OAuth2AccessTokenModelImpl oAuth2AccessTokenModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					oAuth2AccessTokenModelImpl.getTokenContent()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_TOKENCONTENT, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_TOKENCONTENT, args);
		}

		if ((oAuth2AccessTokenModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_TOKENCONTENT.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					oAuth2AccessTokenModelImpl.getOriginalTokenContent()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_TOKENCONTENT, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_TOKENCONTENT, args);
		}
	}

	/**
	 * Creates a new o auth2 access token with the primary key. Does not add the o auth2 access token to the database.
	 *
	 * @param oAuth2AccessTokenId the primary key for the new o auth2 access token
	 * @return the new o auth2 access token
	 */
	@Override
	public OAuth2AccessToken create(long oAuth2AccessTokenId) {
		OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessTokenImpl();

		oAuth2AccessToken.setNew(true);
		oAuth2AccessToken.setPrimaryKey(oAuth2AccessTokenId);

		oAuth2AccessToken.setCompanyId(companyProvider.getCompanyId());

		return oAuth2AccessToken;
	}

	/**
	 * Removes the o auth2 access token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2AccessTokenId the primary key of the o auth2 access token
	 * @return the o auth2 access token that was removed
	 * @throws NoSuchOAuth2AccessTokenException if a o auth2 access token with the primary key could not be found
	 */
	@Override
	public OAuth2AccessToken remove(long oAuth2AccessTokenId)
		throws NoSuchOAuth2AccessTokenException {
		return remove((Serializable)oAuth2AccessTokenId);
	}

	/**
	 * Removes the o auth2 access token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth2 access token
	 * @return the o auth2 access token that was removed
	 * @throws NoSuchOAuth2AccessTokenException if a o auth2 access token with the primary key could not be found
	 */
	@Override
	public OAuth2AccessToken remove(Serializable primaryKey)
		throws NoSuchOAuth2AccessTokenException {
		Session session = null;

		try {
			session = openSession();

			OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken)session.get(OAuth2AccessTokenImpl.class,
					primaryKey);

			if (oAuth2AccessToken == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOAuth2AccessTokenException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(oAuth2AccessToken);
		}
		catch (NoSuchOAuth2AccessTokenException nsee) {
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
	protected OAuth2AccessToken removeImpl(OAuth2AccessToken oAuth2AccessToken) {
		oAuth2AccessToken = toUnwrappedModel(oAuth2AccessToken);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuth2AccessToken)) {
				oAuth2AccessToken = (OAuth2AccessToken)session.get(OAuth2AccessTokenImpl.class,
						oAuth2AccessToken.getPrimaryKeyObj());
			}

			if (oAuth2AccessToken != null) {
				session.delete(oAuth2AccessToken);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (oAuth2AccessToken != null) {
			clearCache(oAuth2AccessToken);
		}

		return oAuth2AccessToken;
	}

	@Override
	public OAuth2AccessToken updateImpl(OAuth2AccessToken oAuth2AccessToken) {
		oAuth2AccessToken = toUnwrappedModel(oAuth2AccessToken);

		boolean isNew = oAuth2AccessToken.isNew();

		OAuth2AccessTokenModelImpl oAuth2AccessTokenModelImpl = (OAuth2AccessTokenModelImpl)oAuth2AccessToken;

		Session session = null;

		try {
			session = openSession();

			if (oAuth2AccessToken.isNew()) {
				session.save(oAuth2AccessToken);

				oAuth2AccessToken.setNew(false);
			}
			else {
				oAuth2AccessToken = (OAuth2AccessToken)session.merge(oAuth2AccessToken);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!OAuth2AccessTokenModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					oAuth2AccessTokenModelImpl.getOAuth2ApplicationId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID,
				args);

			args = new Object[] {
					oAuth2AccessTokenModelImpl.getOAuth2RefreshTokenId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2REFRESHTOKENID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2REFRESHTOKENID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((oAuth2AccessTokenModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						oAuth2AccessTokenModelImpl.getOriginalOAuth2ApplicationId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID,
					args);

				args = new Object[] {
						oAuth2AccessTokenModelImpl.getOAuth2ApplicationId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID,
					args);
			}

			if ((oAuth2AccessTokenModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2REFRESHTOKENID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						oAuth2AccessTokenModelImpl.getOriginalOAuth2RefreshTokenId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2REFRESHTOKENID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2REFRESHTOKENID,
					args);

				args = new Object[] {
						oAuth2AccessTokenModelImpl.getOAuth2RefreshTokenId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2REFRESHTOKENID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2REFRESHTOKENID,
					args);
			}
		}

		entityCache.putResult(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2AccessTokenImpl.class, oAuth2AccessToken.getPrimaryKey(),
			oAuth2AccessToken, false);

		clearUniqueFindersCache(oAuth2AccessTokenModelImpl, false);
		cacheUniqueFindersCache(oAuth2AccessTokenModelImpl);

		oAuth2AccessToken.resetOriginalValues();

		return oAuth2AccessToken;
	}

	protected OAuth2AccessToken toUnwrappedModel(
		OAuth2AccessToken oAuth2AccessToken) {
		if (oAuth2AccessToken instanceof OAuth2AccessTokenImpl) {
			return oAuth2AccessToken;
		}

		OAuth2AccessTokenImpl oAuth2AccessTokenImpl = new OAuth2AccessTokenImpl();

		oAuth2AccessTokenImpl.setNew(oAuth2AccessToken.isNew());
		oAuth2AccessTokenImpl.setPrimaryKey(oAuth2AccessToken.getPrimaryKey());

		oAuth2AccessTokenImpl.setOAuth2AccessTokenId(oAuth2AccessToken.getOAuth2AccessTokenId());
		oAuth2AccessTokenImpl.setCompanyId(oAuth2AccessToken.getCompanyId());
		oAuth2AccessTokenImpl.setUserId(oAuth2AccessToken.getUserId());
		oAuth2AccessTokenImpl.setUserName(oAuth2AccessToken.getUserName());
		oAuth2AccessTokenImpl.setCreateDate(oAuth2AccessToken.getCreateDate());
		oAuth2AccessTokenImpl.setOAuth2ApplicationId(oAuth2AccessToken.getOAuth2ApplicationId());
		oAuth2AccessTokenImpl.setOAuth2RefreshTokenId(oAuth2AccessToken.getOAuth2RefreshTokenId());
		oAuth2AccessTokenImpl.setExpirationDate(oAuth2AccessToken.getExpirationDate());
		oAuth2AccessTokenImpl.setRemoteIPInfo(oAuth2AccessToken.getRemoteIPInfo());
		oAuth2AccessTokenImpl.setScopeAliases(oAuth2AccessToken.getScopeAliases());
		oAuth2AccessTokenImpl.setTokenContent(oAuth2AccessToken.getTokenContent());
		oAuth2AccessTokenImpl.setTokenType(oAuth2AccessToken.getTokenType());

		return oAuth2AccessTokenImpl;
	}

	/**
	 * Returns the o auth2 access token with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 access token
	 * @return the o auth2 access token
	 * @throws NoSuchOAuth2AccessTokenException if a o auth2 access token with the primary key could not be found
	 */
	@Override
	public OAuth2AccessToken findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOAuth2AccessTokenException {
		OAuth2AccessToken oAuth2AccessToken = fetchByPrimaryKey(primaryKey);

		if (oAuth2AccessToken == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOAuth2AccessTokenException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return oAuth2AccessToken;
	}

	/**
	 * Returns the o auth2 access token with the primary key or throws a {@link NoSuchOAuth2AccessTokenException} if it could not be found.
	 *
	 * @param oAuth2AccessTokenId the primary key of the o auth2 access token
	 * @return the o auth2 access token
	 * @throws NoSuchOAuth2AccessTokenException if a o auth2 access token with the primary key could not be found
	 */
	@Override
	public OAuth2AccessToken findByPrimaryKey(long oAuth2AccessTokenId)
		throws NoSuchOAuth2AccessTokenException {
		return findByPrimaryKey((Serializable)oAuth2AccessTokenId);
	}

	/**
	 * Returns the o auth2 access token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 access token
	 * @return the o auth2 access token, or <code>null</code> if a o auth2 access token with the primary key could not be found
	 */
	@Override
	public OAuth2AccessToken fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
				OAuth2AccessTokenImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken)serializable;

		if (oAuth2AccessToken == null) {
			Session session = null;

			try {
				session = openSession();

				oAuth2AccessToken = (OAuth2AccessToken)session.get(OAuth2AccessTokenImpl.class,
						primaryKey);

				if (oAuth2AccessToken != null) {
					cacheResult(oAuth2AccessToken);
				}
				else {
					entityCache.putResult(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
						OAuth2AccessTokenImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2AccessTokenImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return oAuth2AccessToken;
	}

	/**
	 * Returns the o auth2 access token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuth2AccessTokenId the primary key of the o auth2 access token
	 * @return the o auth2 access token, or <code>null</code> if a o auth2 access token with the primary key could not be found
	 */
	@Override
	public OAuth2AccessToken fetchByPrimaryKey(long oAuth2AccessTokenId) {
		return fetchByPrimaryKey((Serializable)oAuth2AccessTokenId);
	}

	@Override
	public Map<Serializable, OAuth2AccessToken> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, OAuth2AccessToken> map = new HashMap<Serializable, OAuth2AccessToken>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			OAuth2AccessToken oAuth2AccessToken = fetchByPrimaryKey(primaryKey);

			if (oAuth2AccessToken != null) {
				map.put(primaryKey, oAuth2AccessToken);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2AccessTokenImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (OAuth2AccessToken)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_OAUTH2ACCESSTOKEN_WHERE_PKS_IN);

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

			for (OAuth2AccessToken oAuth2AccessToken : (List<OAuth2AccessToken>)q.list()) {
				map.put(oAuth2AccessToken.getPrimaryKeyObj(), oAuth2AccessToken);

				cacheResult(oAuth2AccessToken);

				uncachedPrimaryKeys.remove(oAuth2AccessToken.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(OAuth2AccessTokenModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2AccessTokenImpl.class, primaryKey, nullModel);
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
	 * Returns all the o auth2 access tokens.
	 *
	 * @return the o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 access tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 access tokens
	 * @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	 * @return the range of o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 access tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 access tokens
	 * @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findAll(int start, int end,
		OrderByComparator<OAuth2AccessToken> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 access tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 access tokens
	 * @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of o auth2 access tokens
	 */
	@Override
	public List<OAuth2AccessToken> findAll(int start, int end,
		OrderByComparator<OAuth2AccessToken> orderByComparator,
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

		List<OAuth2AccessToken> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2AccessToken>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_OAUTH2ACCESSTOKEN);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTH2ACCESSTOKEN;

				if (pagination) {
					sql = sql.concat(OAuth2AccessTokenModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<OAuth2AccessToken>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2AccessToken>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the o auth2 access tokens from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuth2AccessToken oAuth2AccessToken : findAll()) {
			remove(oAuth2AccessToken);
		}
	}

	/**
	 * Returns the number of o auth2 access tokens.
	 *
	 * @return the number of o auth2 access tokens
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_OAUTH2ACCESSTOKEN);

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
		return OAuth2AccessTokenModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth2 access token persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(OAuth2AccessTokenImpl.class.getName());
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
	private static final String _SQL_SELECT_OAUTH2ACCESSTOKEN = "SELECT oAuth2AccessToken FROM OAuth2AccessToken oAuth2AccessToken";
	private static final String _SQL_SELECT_OAUTH2ACCESSTOKEN_WHERE_PKS_IN = "SELECT oAuth2AccessToken FROM OAuth2AccessToken oAuth2AccessToken WHERE oAuth2AccessTokenId IN (";
	private static final String _SQL_SELECT_OAUTH2ACCESSTOKEN_WHERE = "SELECT oAuth2AccessToken FROM OAuth2AccessToken oAuth2AccessToken WHERE ";
	private static final String _SQL_COUNT_OAUTH2ACCESSTOKEN = "SELECT COUNT(oAuth2AccessToken) FROM OAuth2AccessToken oAuth2AccessToken";
	private static final String _SQL_COUNT_OAUTH2ACCESSTOKEN_WHERE = "SELECT COUNT(oAuth2AccessToken) FROM OAuth2AccessToken oAuth2AccessToken WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuth2AccessToken.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No OAuth2AccessToken exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No OAuth2AccessToken exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(OAuth2AccessTokenPersistenceImpl.class);
}