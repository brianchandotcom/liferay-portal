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

import com.liferay.oauth2.provider.exception.NoSuchOAuth2ScopeGrantException;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantImpl;
import com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantModelImpl;
import com.liferay.oauth2.provider.service.persistence.OAuth2AuthorizationPersistence;
import com.liferay.oauth2.provider.service.persistence.OAuth2ScopeGrantPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the o auth2 scope grant service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrantPersistence
 * @see com.liferay.oauth2.provider.service.persistence.OAuth2ScopeGrantUtil
 * @generated
 */
@ProviderType
public class OAuth2ScopeGrantPersistenceImpl extends BasePersistenceImpl<OAuth2ScopeGrant>
	implements OAuth2ScopeGrantPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link OAuth2ScopeGrantUtil} to access the o auth2 scope grant persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = OAuth2ScopeGrantImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ScopeGrantImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ScopeGrantImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_OAUTH2APPLICATIONSCOPEALIASESID =
		new FinderPath(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ScopeGrantImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByOAuth2ApplicationScopeAliasesId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONSCOPEALIASESID =
		new FinderPath(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ScopeGrantImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByOAuth2ApplicationScopeAliasesId",
			new String[] { Long.class.getName() },
			OAuth2ScopeGrantModelImpl.OAUTH2APPLICATIONSCOPEALIASESID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONSCOPEALIASESID =
		new FinderPath(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByOAuth2ApplicationScopeAliasesId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @return the matching o auth2 scope grants
	 */
	@Override
	public List<OAuth2ScopeGrant> findByOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {
		return findByOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @return the range of matching o auth2 scope grants
	 */
	@Override
	public List<OAuth2ScopeGrant> findByOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId, int start, int end) {
		return findByOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 scope grants
	 */
	@Override
	public List<OAuth2ScopeGrant> findByOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId, int start, int end,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator) {
		return findByOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching o auth2 scope grants
	 */
	@Override
	public List<OAuth2ScopeGrant> findByOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId, int start, int end,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONSCOPEALIASESID;
			finderArgs = new Object[] { oAuth2ApplicationScopeAliasesId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_OAUTH2APPLICATIONSCOPEALIASESID;
			finderArgs = new Object[] {
					oAuth2ApplicationScopeAliasesId,
					
					start, end, orderByComparator
				};
		}

		List<OAuth2ScopeGrant> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2ScopeGrant>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2ScopeGrant oAuth2ScopeGrant : list) {
					if ((oAuth2ApplicationScopeAliasesId != oAuth2ScopeGrant.getOAuth2ApplicationScopeAliasesId())) {
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

			query.append(_SQL_SELECT_OAUTH2SCOPEGRANT_WHERE);

			query.append(_FINDER_COLUMN_OAUTH2APPLICATIONSCOPEALIASESID_OAUTH2APPLICATIONSCOPEALIASESID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(OAuth2ScopeGrantModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2ApplicationScopeAliasesId);

				if (!pagination) {
					list = (List<OAuth2ScopeGrant>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2ScopeGrant>)QueryUtil.list(q,
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
	 * Returns the first o auth2 scope grant in the ordered set where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	 */
	@Override
	public OAuth2ScopeGrant findByOAuth2ApplicationScopeAliasesId_First(
		long oAuth2ApplicationScopeAliasesId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator)
		throws NoSuchOAuth2ScopeGrantException {
		OAuth2ScopeGrant oAuth2ScopeGrant = fetchByOAuth2ApplicationScopeAliasesId_First(oAuth2ApplicationScopeAliasesId,
				orderByComparator);

		if (oAuth2ScopeGrant != null) {
			return oAuth2ScopeGrant;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2ApplicationScopeAliasesId=");
		msg.append(oAuth2ApplicationScopeAliasesId);

		msg.append("}");

		throw new NoSuchOAuth2ScopeGrantException(msg.toString());
	}

	/**
	 * Returns the first o auth2 scope grant in the ordered set where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	 */
	@Override
	public OAuth2ScopeGrant fetchByOAuth2ApplicationScopeAliasesId_First(
		long oAuth2ApplicationScopeAliasesId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator) {
		List<OAuth2ScopeGrant> list = findByOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 scope grant in the ordered set where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	 */
	@Override
	public OAuth2ScopeGrant findByOAuth2ApplicationScopeAliasesId_Last(
		long oAuth2ApplicationScopeAliasesId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator)
		throws NoSuchOAuth2ScopeGrantException {
		OAuth2ScopeGrant oAuth2ScopeGrant = fetchByOAuth2ApplicationScopeAliasesId_Last(oAuth2ApplicationScopeAliasesId,
				orderByComparator);

		if (oAuth2ScopeGrant != null) {
			return oAuth2ScopeGrant;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2ApplicationScopeAliasesId=");
		msg.append(oAuth2ApplicationScopeAliasesId);

		msg.append("}");

		throw new NoSuchOAuth2ScopeGrantException(msg.toString());
	}

	/**
	 * Returns the last o auth2 scope grant in the ordered set where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	 */
	@Override
	public OAuth2ScopeGrant fetchByOAuth2ApplicationScopeAliasesId_Last(
		long oAuth2ApplicationScopeAliasesId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator) {
		int count = countByOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId);

		if (count == 0) {
			return null;
		}

		List<OAuth2ScopeGrant> list = findByOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 scope grants before and after the current o auth2 scope grant in the ordered set where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ScopeGrantId the primary key of the current o auth2 scope grant
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	 */
	@Override
	public OAuth2ScopeGrant[] findByOAuth2ApplicationScopeAliasesId_PrevAndNext(
		long oAuth2ScopeGrantId, long oAuth2ApplicationScopeAliasesId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator)
		throws NoSuchOAuth2ScopeGrantException {
		OAuth2ScopeGrant oAuth2ScopeGrant = findByPrimaryKey(oAuth2ScopeGrantId);

		Session session = null;

		try {
			session = openSession();

			OAuth2ScopeGrant[] array = new OAuth2ScopeGrantImpl[3];

			array[0] = getByOAuth2ApplicationScopeAliasesId_PrevAndNext(session,
					oAuth2ScopeGrant, oAuth2ApplicationScopeAliasesId,
					orderByComparator, true);

			array[1] = oAuth2ScopeGrant;

			array[2] = getByOAuth2ApplicationScopeAliasesId_PrevAndNext(session,
					oAuth2ScopeGrant, oAuth2ApplicationScopeAliasesId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2ScopeGrant getByOAuth2ApplicationScopeAliasesId_PrevAndNext(
		Session session, OAuth2ScopeGrant oAuth2ScopeGrant,
		long oAuth2ApplicationScopeAliasesId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2SCOPEGRANT_WHERE);

		query.append(_FINDER_COLUMN_OAUTH2APPLICATIONSCOPEALIASESID_OAUTH2APPLICATIONSCOPEALIASESID_2);

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
			query.append(OAuth2ScopeGrantModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(oAuth2ApplicationScopeAliasesId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(oAuth2ScopeGrant);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OAuth2ScopeGrant> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63; from the database.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 */
	@Override
	public void removeByOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {
		for (OAuth2ScopeGrant oAuth2ScopeGrant : findByOAuth2ApplicationScopeAliasesId(
				oAuth2ApplicationScopeAliasesId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(oAuth2ScopeGrant);
		}
	}

	/**
	 * Returns the number of o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @return the number of matching o auth2 scope grants
	 */
	@Override
	public int countByOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONSCOPEALIASESID;

		Object[] finderArgs = new Object[] { oAuth2ApplicationScopeAliasesId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2SCOPEGRANT_WHERE);

			query.append(_FINDER_COLUMN_OAUTH2APPLICATIONSCOPEALIASESID_OAUTH2APPLICATIONSCOPEALIASESID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2ApplicationScopeAliasesId);

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

	private static final String _FINDER_COLUMN_OAUTH2APPLICATIONSCOPEALIASESID_OAUTH2APPLICATIONSCOPEALIASESID_2 =
		"oAuth2ScopeGrant.oAuth2ApplicationScopeAliasesId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_O_A_B_S = new FinderPath(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ScopeGrantImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_O_A_B_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				String.class.getName()
			},
			OAuth2ScopeGrantModelImpl.COMPANYID_COLUMN_BITMASK |
			OAuth2ScopeGrantModelImpl.OAUTH2APPLICATIONSCOPEALIASESID_COLUMN_BITMASK |
			OAuth2ScopeGrantModelImpl.APPLICATIONNAME_COLUMN_BITMASK |
			OAuth2ScopeGrantModelImpl.BUNDLESYMBOLICNAME_COLUMN_BITMASK |
			OAuth2ScopeGrantModelImpl.SCOPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_O_A_B_S = new FinderPath(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_O_A_B_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the o auth2 scope grant where companyId = &#63; and oAuth2ApplicationScopeAliasesId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; or throws a {@link NoSuchOAuth2ScopeGrantException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param applicationName the application name
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param scope the scope
	 * @return the matching o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	 */
	@Override
	public OAuth2ScopeGrant findByC_O_A_B_S(long companyId,
		long oAuth2ApplicationScopeAliasesId, String applicationName,
		String bundleSymbolicName, String scope)
		throws NoSuchOAuth2ScopeGrantException {
		OAuth2ScopeGrant oAuth2ScopeGrant = fetchByC_O_A_B_S(companyId,
				oAuth2ApplicationScopeAliasesId, applicationName,
				bundleSymbolicName, scope);

		if (oAuth2ScopeGrant == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", oAuth2ApplicationScopeAliasesId=");
			msg.append(oAuth2ApplicationScopeAliasesId);

			msg.append(", applicationName=");
			msg.append(applicationName);

			msg.append(", bundleSymbolicName=");
			msg.append(bundleSymbolicName);

			msg.append(", scope=");
			msg.append(scope);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchOAuth2ScopeGrantException(msg.toString());
		}

		return oAuth2ScopeGrant;
	}

	/**
	 * Returns the o auth2 scope grant where companyId = &#63; and oAuth2ApplicationScopeAliasesId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param applicationName the application name
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param scope the scope
	 * @return the matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	 */
	@Override
	public OAuth2ScopeGrant fetchByC_O_A_B_S(long companyId,
		long oAuth2ApplicationScopeAliasesId, String applicationName,
		String bundleSymbolicName, String scope) {
		return fetchByC_O_A_B_S(companyId, oAuth2ApplicationScopeAliasesId,
			applicationName, bundleSymbolicName, scope, true);
	}

	/**
	 * Returns the o auth2 scope grant where companyId = &#63; and oAuth2ApplicationScopeAliasesId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param applicationName the application name
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param scope the scope
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	 */
	@Override
	public OAuth2ScopeGrant fetchByC_O_A_B_S(long companyId,
		long oAuth2ApplicationScopeAliasesId, String applicationName,
		String bundleSymbolicName, String scope, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				companyId, oAuth2ApplicationScopeAliasesId, applicationName,
				bundleSymbolicName, scope
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_O_A_B_S,
					finderArgs, this);
		}

		if (result instanceof OAuth2ScopeGrant) {
			OAuth2ScopeGrant oAuth2ScopeGrant = (OAuth2ScopeGrant)result;

			if ((companyId != oAuth2ScopeGrant.getCompanyId()) ||
					(oAuth2ApplicationScopeAliasesId != oAuth2ScopeGrant.getOAuth2ApplicationScopeAliasesId()) ||
					!Objects.equals(applicationName,
						oAuth2ScopeGrant.getApplicationName()) ||
					!Objects.equals(bundleSymbolicName,
						oAuth2ScopeGrant.getBundleSymbolicName()) ||
					!Objects.equals(scope, oAuth2ScopeGrant.getScope())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_OAUTH2SCOPEGRANT_WHERE);

			query.append(_FINDER_COLUMN_C_O_A_B_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_O_A_B_S_OAUTH2APPLICATIONSCOPEALIASESID_2);

			boolean bindApplicationName = false;

			if (applicationName == null) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_APPLICATIONNAME_1);
			}
			else if (applicationName.equals("")) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_APPLICATIONNAME_3);
			}
			else {
				bindApplicationName = true;

				query.append(_FINDER_COLUMN_C_O_A_B_S_APPLICATIONNAME_2);
			}

			boolean bindBundleSymbolicName = false;

			if (bundleSymbolicName == null) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_BUNDLESYMBOLICNAME_1);
			}
			else if (bundleSymbolicName.equals("")) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_BUNDLESYMBOLICNAME_3);
			}
			else {
				bindBundleSymbolicName = true;

				query.append(_FINDER_COLUMN_C_O_A_B_S_BUNDLESYMBOLICNAME_2);
			}

			boolean bindScope = false;

			if (scope == null) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_SCOPE_1);
			}
			else if (scope.equals("")) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_SCOPE_3);
			}
			else {
				bindScope = true;

				query.append(_FINDER_COLUMN_C_O_A_B_S_SCOPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(oAuth2ApplicationScopeAliasesId);

				if (bindApplicationName) {
					qPos.add(applicationName);
				}

				if (bindBundleSymbolicName) {
					qPos.add(bundleSymbolicName);
				}

				if (bindScope) {
					qPos.add(scope);
				}

				List<OAuth2ScopeGrant> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_O_A_B_S,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"OAuth2ScopeGrantPersistenceImpl.fetchByC_O_A_B_S(long, long, String, String, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					OAuth2ScopeGrant oAuth2ScopeGrant = list.get(0);

					result = oAuth2ScopeGrant;

					cacheResult(oAuth2ScopeGrant);

					if ((oAuth2ScopeGrant.getCompanyId() != companyId) ||
							(oAuth2ScopeGrant.getOAuth2ApplicationScopeAliasesId() != oAuth2ApplicationScopeAliasesId) ||
							(oAuth2ScopeGrant.getApplicationName() == null) ||
							!oAuth2ScopeGrant.getApplicationName()
												 .equals(applicationName) ||
							(oAuth2ScopeGrant.getBundleSymbolicName() == null) ||
							!oAuth2ScopeGrant.getBundleSymbolicName()
												 .equals(bundleSymbolicName) ||
							(oAuth2ScopeGrant.getScope() == null) ||
							!oAuth2ScopeGrant.getScope().equals(scope)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_O_A_B_S,
							finderArgs, oAuth2ScopeGrant);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_O_A_B_S,
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
			return (OAuth2ScopeGrant)result;
		}
	}

	/**
	 * Removes the o auth2 scope grant where companyId = &#63; and oAuth2ApplicationScopeAliasesId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param applicationName the application name
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param scope the scope
	 * @return the o auth2 scope grant that was removed
	 */
	@Override
	public OAuth2ScopeGrant removeByC_O_A_B_S(long companyId,
		long oAuth2ApplicationScopeAliasesId, String applicationName,
		String bundleSymbolicName, String scope)
		throws NoSuchOAuth2ScopeGrantException {
		OAuth2ScopeGrant oAuth2ScopeGrant = findByC_O_A_B_S(companyId,
				oAuth2ApplicationScopeAliasesId, applicationName,
				bundleSymbolicName, scope);

		return remove(oAuth2ScopeGrant);
	}

	/**
	 * Returns the number of o auth2 scope grants where companyId = &#63; and oAuth2ApplicationScopeAliasesId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param applicationName the application name
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param scope the scope
	 * @return the number of matching o auth2 scope grants
	 */
	@Override
	public int countByC_O_A_B_S(long companyId,
		long oAuth2ApplicationScopeAliasesId, String applicationName,
		String bundleSymbolicName, String scope) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_O_A_B_S;

		Object[] finderArgs = new Object[] {
				companyId, oAuth2ApplicationScopeAliasesId, applicationName,
				bundleSymbolicName, scope
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_OAUTH2SCOPEGRANT_WHERE);

			query.append(_FINDER_COLUMN_C_O_A_B_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_O_A_B_S_OAUTH2APPLICATIONSCOPEALIASESID_2);

			boolean bindApplicationName = false;

			if (applicationName == null) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_APPLICATIONNAME_1);
			}
			else if (applicationName.equals("")) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_APPLICATIONNAME_3);
			}
			else {
				bindApplicationName = true;

				query.append(_FINDER_COLUMN_C_O_A_B_S_APPLICATIONNAME_2);
			}

			boolean bindBundleSymbolicName = false;

			if (bundleSymbolicName == null) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_BUNDLESYMBOLICNAME_1);
			}
			else if (bundleSymbolicName.equals("")) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_BUNDLESYMBOLICNAME_3);
			}
			else {
				bindBundleSymbolicName = true;

				query.append(_FINDER_COLUMN_C_O_A_B_S_BUNDLESYMBOLICNAME_2);
			}

			boolean bindScope = false;

			if (scope == null) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_SCOPE_1);
			}
			else if (scope.equals("")) {
				query.append(_FINDER_COLUMN_C_O_A_B_S_SCOPE_3);
			}
			else {
				bindScope = true;

				query.append(_FINDER_COLUMN_C_O_A_B_S_SCOPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(oAuth2ApplicationScopeAliasesId);

				if (bindApplicationName) {
					qPos.add(applicationName);
				}

				if (bindBundleSymbolicName) {
					qPos.add(bundleSymbolicName);
				}

				if (bindScope) {
					qPos.add(scope);
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

	private static final String _FINDER_COLUMN_C_O_A_B_S_COMPANYID_2 = "oAuth2ScopeGrant.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_O_A_B_S_OAUTH2APPLICATIONSCOPEALIASESID_2 =
		"oAuth2ScopeGrant.oAuth2ApplicationScopeAliasesId = ? AND ";
	private static final String _FINDER_COLUMN_C_O_A_B_S_APPLICATIONNAME_1 = "oAuth2ScopeGrant.applicationName IS NULL AND ";
	private static final String _FINDER_COLUMN_C_O_A_B_S_APPLICATIONNAME_2 = "oAuth2ScopeGrant.applicationName = ? AND ";
	private static final String _FINDER_COLUMN_C_O_A_B_S_APPLICATIONNAME_3 = "(oAuth2ScopeGrant.applicationName IS NULL OR oAuth2ScopeGrant.applicationName = '') AND ";
	private static final String _FINDER_COLUMN_C_O_A_B_S_BUNDLESYMBOLICNAME_1 = "oAuth2ScopeGrant.bundleSymbolicName IS NULL AND ";
	private static final String _FINDER_COLUMN_C_O_A_B_S_BUNDLESYMBOLICNAME_2 = "oAuth2ScopeGrant.bundleSymbolicName = ? AND ";
	private static final String _FINDER_COLUMN_C_O_A_B_S_BUNDLESYMBOLICNAME_3 = "(oAuth2ScopeGrant.bundleSymbolicName IS NULL OR oAuth2ScopeGrant.bundleSymbolicName = '') AND ";
	private static final String _FINDER_COLUMN_C_O_A_B_S_SCOPE_1 = "oAuth2ScopeGrant.scope IS NULL";
	private static final String _FINDER_COLUMN_C_O_A_B_S_SCOPE_2 = "oAuth2ScopeGrant.scope = ?";
	private static final String _FINDER_COLUMN_C_O_A_B_S_SCOPE_3 = "(oAuth2ScopeGrant.scope IS NULL OR oAuth2ScopeGrant.scope = '')";

	public OAuth2ScopeGrantPersistenceImpl() {
		setModelClass(OAuth2ScopeGrant.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("oAuth2ApplicationScopeAliasesId",
				"oA2AScopeAliasesId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the o auth2 scope grant in the entity cache if it is enabled.
	 *
	 * @param oAuth2ScopeGrant the o auth2 scope grant
	 */
	@Override
	public void cacheResult(OAuth2ScopeGrant oAuth2ScopeGrant) {
		entityCache.putResult(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantImpl.class, oAuth2ScopeGrant.getPrimaryKey(),
			oAuth2ScopeGrant);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_O_A_B_S,
			new Object[] {
				oAuth2ScopeGrant.getCompanyId(),
				oAuth2ScopeGrant.getOAuth2ApplicationScopeAliasesId(),
				oAuth2ScopeGrant.getApplicationName(),
				oAuth2ScopeGrant.getBundleSymbolicName(),
				oAuth2ScopeGrant.getScope()
			}, oAuth2ScopeGrant);

		oAuth2ScopeGrant.resetOriginalValues();
	}

	/**
	 * Caches the o auth2 scope grants in the entity cache if it is enabled.
	 *
	 * @param oAuth2ScopeGrants the o auth2 scope grants
	 */
	@Override
	public void cacheResult(List<OAuth2ScopeGrant> oAuth2ScopeGrants) {
		for (OAuth2ScopeGrant oAuth2ScopeGrant : oAuth2ScopeGrants) {
			if (entityCache.getResult(
						OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
						OAuth2ScopeGrantImpl.class,
						oAuth2ScopeGrant.getPrimaryKey()) == null) {
				cacheResult(oAuth2ScopeGrant);
			}
			else {
				oAuth2ScopeGrant.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all o auth2 scope grants.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuth2ScopeGrantImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the o auth2 scope grant.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OAuth2ScopeGrant oAuth2ScopeGrant) {
		entityCache.removeResult(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantImpl.class, oAuth2ScopeGrant.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((OAuth2ScopeGrantModelImpl)oAuth2ScopeGrant,
			true);
	}

	@Override
	public void clearCache(List<OAuth2ScopeGrant> oAuth2ScopeGrants) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OAuth2ScopeGrant oAuth2ScopeGrant : oAuth2ScopeGrants) {
			entityCache.removeResult(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
				OAuth2ScopeGrantImpl.class, oAuth2ScopeGrant.getPrimaryKey());

			clearUniqueFindersCache((OAuth2ScopeGrantModelImpl)oAuth2ScopeGrant,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuth2ScopeGrantModelImpl oAuth2ScopeGrantModelImpl) {
		Object[] args = new Object[] {
				oAuth2ScopeGrantModelImpl.getCompanyId(),
				oAuth2ScopeGrantModelImpl.getOAuth2ApplicationScopeAliasesId(),
				oAuth2ScopeGrantModelImpl.getApplicationName(),
				oAuth2ScopeGrantModelImpl.getBundleSymbolicName(),
				oAuth2ScopeGrantModelImpl.getScope()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_O_A_B_S, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_O_A_B_S, args,
			oAuth2ScopeGrantModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		OAuth2ScopeGrantModelImpl oAuth2ScopeGrantModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					oAuth2ScopeGrantModelImpl.getCompanyId(),
					oAuth2ScopeGrantModelImpl.getOAuth2ApplicationScopeAliasesId(),
					oAuth2ScopeGrantModelImpl.getApplicationName(),
					oAuth2ScopeGrantModelImpl.getBundleSymbolicName(),
					oAuth2ScopeGrantModelImpl.getScope()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_O_A_B_S, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_O_A_B_S, args);
		}

		if ((oAuth2ScopeGrantModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_O_A_B_S.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					oAuth2ScopeGrantModelImpl.getOriginalCompanyId(),
					oAuth2ScopeGrantModelImpl.getOriginalOAuth2ApplicationScopeAliasesId(),
					oAuth2ScopeGrantModelImpl.getOriginalApplicationName(),
					oAuth2ScopeGrantModelImpl.getOriginalBundleSymbolicName(),
					oAuth2ScopeGrantModelImpl.getOriginalScope()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_O_A_B_S, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_O_A_B_S, args);
		}
	}

	/**
	 * Creates a new o auth2 scope grant with the primary key. Does not add the o auth2 scope grant to the database.
	 *
	 * @param oAuth2ScopeGrantId the primary key for the new o auth2 scope grant
	 * @return the new o auth2 scope grant
	 */
	@Override
	public OAuth2ScopeGrant create(long oAuth2ScopeGrantId) {
		OAuth2ScopeGrant oAuth2ScopeGrant = new OAuth2ScopeGrantImpl();

		oAuth2ScopeGrant.setNew(true);
		oAuth2ScopeGrant.setPrimaryKey(oAuth2ScopeGrantId);

		oAuth2ScopeGrant.setCompanyId(companyProvider.getCompanyId());

		return oAuth2ScopeGrant;
	}

	/**
	 * Removes the o auth2 scope grant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant that was removed
	 * @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	 */
	@Override
	public OAuth2ScopeGrant remove(long oAuth2ScopeGrantId)
		throws NoSuchOAuth2ScopeGrantException {
		return remove((Serializable)oAuth2ScopeGrantId);
	}

	/**
	 * Removes the o auth2 scope grant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant that was removed
	 * @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	 */
	@Override
	public OAuth2ScopeGrant remove(Serializable primaryKey)
		throws NoSuchOAuth2ScopeGrantException {
		Session session = null;

		try {
			session = openSession();

			OAuth2ScopeGrant oAuth2ScopeGrant = (OAuth2ScopeGrant)session.get(OAuth2ScopeGrantImpl.class,
					primaryKey);

			if (oAuth2ScopeGrant == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOAuth2ScopeGrantException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(oAuth2ScopeGrant);
		}
		catch (NoSuchOAuth2ScopeGrantException nsee) {
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
	protected OAuth2ScopeGrant removeImpl(OAuth2ScopeGrant oAuth2ScopeGrant) {
		oAuth2ScopeGrant = toUnwrappedModel(oAuth2ScopeGrant);

		oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.deleteLeftPrimaryKeyTableMappings(oAuth2ScopeGrant.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuth2ScopeGrant)) {
				oAuth2ScopeGrant = (OAuth2ScopeGrant)session.get(OAuth2ScopeGrantImpl.class,
						oAuth2ScopeGrant.getPrimaryKeyObj());
			}

			if (oAuth2ScopeGrant != null) {
				session.delete(oAuth2ScopeGrant);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (oAuth2ScopeGrant != null) {
			clearCache(oAuth2ScopeGrant);
		}

		return oAuth2ScopeGrant;
	}

	@Override
	public OAuth2ScopeGrant updateImpl(OAuth2ScopeGrant oAuth2ScopeGrant) {
		oAuth2ScopeGrant = toUnwrappedModel(oAuth2ScopeGrant);

		boolean isNew = oAuth2ScopeGrant.isNew();

		OAuth2ScopeGrantModelImpl oAuth2ScopeGrantModelImpl = (OAuth2ScopeGrantModelImpl)oAuth2ScopeGrant;

		Session session = null;

		try {
			session = openSession();

			if (oAuth2ScopeGrant.isNew()) {
				session.save(oAuth2ScopeGrant);

				oAuth2ScopeGrant.setNew(false);
			}
			else {
				oAuth2ScopeGrant = (OAuth2ScopeGrant)session.merge(oAuth2ScopeGrant);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!OAuth2ScopeGrantModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					oAuth2ScopeGrantModelImpl.getOAuth2ApplicationScopeAliasesId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONSCOPEALIASESID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONSCOPEALIASESID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((oAuth2ScopeGrantModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONSCOPEALIASESID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						oAuth2ScopeGrantModelImpl.getOriginalOAuth2ApplicationScopeAliasesId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONSCOPEALIASESID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONSCOPEALIASESID,
					args);

				args = new Object[] {
						oAuth2ScopeGrantModelImpl.getOAuth2ApplicationScopeAliasesId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONSCOPEALIASESID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONSCOPEALIASESID,
					args);
			}
		}

		entityCache.putResult(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ScopeGrantImpl.class, oAuth2ScopeGrant.getPrimaryKey(),
			oAuth2ScopeGrant, false);

		clearUniqueFindersCache(oAuth2ScopeGrantModelImpl, false);
		cacheUniqueFindersCache(oAuth2ScopeGrantModelImpl);

		oAuth2ScopeGrant.resetOriginalValues();

		return oAuth2ScopeGrant;
	}

	protected OAuth2ScopeGrant toUnwrappedModel(
		OAuth2ScopeGrant oAuth2ScopeGrant) {
		if (oAuth2ScopeGrant instanceof OAuth2ScopeGrantImpl) {
			return oAuth2ScopeGrant;
		}

		OAuth2ScopeGrantImpl oAuth2ScopeGrantImpl = new OAuth2ScopeGrantImpl();

		oAuth2ScopeGrantImpl.setNew(oAuth2ScopeGrant.isNew());
		oAuth2ScopeGrantImpl.setPrimaryKey(oAuth2ScopeGrant.getPrimaryKey());

		oAuth2ScopeGrantImpl.setOAuth2ScopeGrantId(oAuth2ScopeGrant.getOAuth2ScopeGrantId());
		oAuth2ScopeGrantImpl.setCompanyId(oAuth2ScopeGrant.getCompanyId());
		oAuth2ScopeGrantImpl.setOAuth2ApplicationScopeAliasesId(oAuth2ScopeGrant.getOAuth2ApplicationScopeAliasesId());
		oAuth2ScopeGrantImpl.setApplicationName(oAuth2ScopeGrant.getApplicationName());
		oAuth2ScopeGrantImpl.setBundleSymbolicName(oAuth2ScopeGrant.getBundleSymbolicName());
		oAuth2ScopeGrantImpl.setScope(oAuth2ScopeGrant.getScope());

		return oAuth2ScopeGrantImpl;
	}

	/**
	 * Returns the o auth2 scope grant with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	 */
	@Override
	public OAuth2ScopeGrant findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOAuth2ScopeGrantException {
		OAuth2ScopeGrant oAuth2ScopeGrant = fetchByPrimaryKey(primaryKey);

		if (oAuth2ScopeGrant == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOAuth2ScopeGrantException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return oAuth2ScopeGrant;
	}

	/**
	 * Returns the o auth2 scope grant with the primary key or throws a {@link NoSuchOAuth2ScopeGrantException} if it could not be found.
	 *
	 * @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	 */
	@Override
	public OAuth2ScopeGrant findByPrimaryKey(long oAuth2ScopeGrantId)
		throws NoSuchOAuth2ScopeGrantException {
		return findByPrimaryKey((Serializable)oAuth2ScopeGrantId);
	}

	/**
	 * Returns the o auth2 scope grant with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant, or <code>null</code> if a o auth2 scope grant with the primary key could not be found
	 */
	@Override
	public OAuth2ScopeGrant fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
				OAuth2ScopeGrantImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		OAuth2ScopeGrant oAuth2ScopeGrant = (OAuth2ScopeGrant)serializable;

		if (oAuth2ScopeGrant == null) {
			Session session = null;

			try {
				session = openSession();

				oAuth2ScopeGrant = (OAuth2ScopeGrant)session.get(OAuth2ScopeGrantImpl.class,
						primaryKey);

				if (oAuth2ScopeGrant != null) {
					cacheResult(oAuth2ScopeGrant);
				}
				else {
					entityCache.putResult(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
						OAuth2ScopeGrantImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2ScopeGrantImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return oAuth2ScopeGrant;
	}

	/**
	 * Returns the o auth2 scope grant with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant, or <code>null</code> if a o auth2 scope grant with the primary key could not be found
	 */
	@Override
	public OAuth2ScopeGrant fetchByPrimaryKey(long oAuth2ScopeGrantId) {
		return fetchByPrimaryKey((Serializable)oAuth2ScopeGrantId);
	}

	@Override
	public Map<Serializable, OAuth2ScopeGrant> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, OAuth2ScopeGrant> map = new HashMap<Serializable, OAuth2ScopeGrant>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			OAuth2ScopeGrant oAuth2ScopeGrant = fetchByPrimaryKey(primaryKey);

			if (oAuth2ScopeGrant != null) {
				map.put(primaryKey, oAuth2ScopeGrant);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2ScopeGrantImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (OAuth2ScopeGrant)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_OAUTH2SCOPEGRANT_WHERE_PKS_IN);

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

			for (OAuth2ScopeGrant oAuth2ScopeGrant : (List<OAuth2ScopeGrant>)q.list()) {
				map.put(oAuth2ScopeGrant.getPrimaryKeyObj(), oAuth2ScopeGrant);

				cacheResult(oAuth2ScopeGrant);

				uncachedPrimaryKeys.remove(oAuth2ScopeGrant.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(OAuth2ScopeGrantModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2ScopeGrantImpl.class, primaryKey, nullModel);
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
	 * Returns all the o auth2 scope grants.
	 *
	 * @return the o auth2 scope grants
	 */
	@Override
	public List<OAuth2ScopeGrant> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 scope grants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @return the range of o auth2 scope grants
	 */
	@Override
	public List<OAuth2ScopeGrant> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 scope grants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 scope grants
	 */
	@Override
	public List<OAuth2ScopeGrant> findAll(int start, int end,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 scope grants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of o auth2 scope grants
	 */
	@Override
	public List<OAuth2ScopeGrant> findAll(int start, int end,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator,
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

		List<OAuth2ScopeGrant> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2ScopeGrant>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_OAUTH2SCOPEGRANT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTH2SCOPEGRANT;

				if (pagination) {
					sql = sql.concat(OAuth2ScopeGrantModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<OAuth2ScopeGrant>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2ScopeGrant>)QueryUtil.list(q,
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
	 * Removes all the o auth2 scope grants from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuth2ScopeGrant oAuth2ScopeGrant : findAll()) {
			remove(oAuth2ScopeGrant);
		}
	}

	/**
	 * Returns the number of o auth2 scope grants.
	 *
	 * @return the number of o auth2 scope grants
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_OAUTH2SCOPEGRANT);

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

	/**
	 * Returns the primaryKeys of o auth2 authorizations associated with the o auth2 scope grant.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @return long[] of the primaryKeys of o auth2 authorizations associated with the o auth2 scope grant
	 */
	@Override
	public long[] getOAuth2AuthorizationPrimaryKeys(long pk) {
		long[] pks = oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the o auth2 authorizations associated with the o auth2 scope grant.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @return the o auth2 authorizations associated with the o auth2 scope grant
	 */
	@Override
	public List<com.liferay.oauth2.provider.model.OAuth2Authorization> getOAuth2Authorizations(
		long pk) {
		return getOAuth2Authorizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the o auth2 authorizations associated with the o auth2 scope grant.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @return the range of o auth2 authorizations associated with the o auth2 scope grant
	 */
	@Override
	public List<com.liferay.oauth2.provider.model.OAuth2Authorization> getOAuth2Authorizations(
		long pk, int start, int end) {
		return getOAuth2Authorizations(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations associated with the o auth2 scope grant.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 authorizations associated with the o auth2 scope grant
	 */
	@Override
	public List<com.liferay.oauth2.provider.model.OAuth2Authorization> getOAuth2Authorizations(
		long pk, int start, int end,
		OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator) {
		return oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.getRightBaseModels(pk,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of o auth2 authorizations associated with the o auth2 scope grant.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @return the number of o auth2 authorizations associated with the o auth2 scope grant
	 */
	@Override
	public int getOAuth2AuthorizationsSize(long pk) {
		long[] pks = oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the o auth2 authorization is associated with the o auth2 scope grant.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPK the primary key of the o auth2 authorization
	 * @return <code>true</code> if the o auth2 authorization is associated with the o auth2 scope grant; <code>false</code> otherwise
	 */
	@Override
	public boolean containsOAuth2Authorization(long pk,
		long oAuth2AuthorizationPK) {
		return oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.containsTableMapping(pk,
			oAuth2AuthorizationPK);
	}

	/**
	 * Returns <code>true</code> if the o auth2 scope grant has any o auth2 authorizations associated with it.
	 *
	 * @param pk the primary key of the o auth2 scope grant to check for associations with o auth2 authorizations
	 * @return <code>true</code> if the o auth2 scope grant has any o auth2 authorizations associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsOAuth2Authorizations(long pk) {
		if (getOAuth2AuthorizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the o auth2 scope grant and the o auth2 authorization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPK the primary key of the o auth2 authorization
	 */
	@Override
	public void addOAuth2Authorization(long pk, long oAuth2AuthorizationPK) {
		OAuth2ScopeGrant oAuth2ScopeGrant = fetchByPrimaryKey(pk);

		if (oAuth2ScopeGrant == null) {
			oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, oAuth2AuthorizationPK);
		}
		else {
			oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.addTableMapping(oAuth2ScopeGrant.getCompanyId(),
				pk, oAuth2AuthorizationPK);
		}
	}

	/**
	 * Adds an association between the o auth2 scope grant and the o auth2 authorization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2Authorization the o auth2 authorization
	 */
	@Override
	public void addOAuth2Authorization(long pk,
		com.liferay.oauth2.provider.model.OAuth2Authorization oAuth2Authorization) {
		OAuth2ScopeGrant oAuth2ScopeGrant = fetchByPrimaryKey(pk);

		if (oAuth2ScopeGrant == null) {
			oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, oAuth2Authorization.getPrimaryKey());
		}
		else {
			oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.addTableMapping(oAuth2ScopeGrant.getCompanyId(),
				pk, oAuth2Authorization.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the o auth2 scope grant and the o auth2 authorizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPKs the primary keys of the o auth2 authorizations
	 */
	@Override
	public void addOAuth2Authorizations(long pk, long[] oAuth2AuthorizationPKs) {
		long companyId = 0;

		OAuth2ScopeGrant oAuth2ScopeGrant = fetchByPrimaryKey(pk);

		if (oAuth2ScopeGrant == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = oAuth2ScopeGrant.getCompanyId();
		}

		oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.addTableMappings(companyId,
			pk, oAuth2AuthorizationPKs);
	}

	/**
	 * Adds an association between the o auth2 scope grant and the o auth2 authorizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2Authorizations the o auth2 authorizations
	 */
	@Override
	public void addOAuth2Authorizations(long pk,
		List<com.liferay.oauth2.provider.model.OAuth2Authorization> oAuth2Authorizations) {
		addOAuth2Authorizations(pk,
			ListUtil.toLongArray(oAuth2Authorizations,
				com.liferay.oauth2.provider.model.OAuth2Authorization.O_AUTH2_AUTHORIZATION_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the o auth2 scope grant and its o auth2 authorizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant to clear the associated o auth2 authorizations from
	 */
	@Override
	public void clearOAuth2Authorizations(long pk) {
		oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the o auth2 scope grant and the o auth2 authorization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPK the primary key of the o auth2 authorization
	 */
	@Override
	public void removeOAuth2Authorization(long pk, long oAuth2AuthorizationPK) {
		oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.deleteTableMapping(pk,
			oAuth2AuthorizationPK);
	}

	/**
	 * Removes the association between the o auth2 scope grant and the o auth2 authorization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2Authorization the o auth2 authorization
	 */
	@Override
	public void removeOAuth2Authorization(long pk,
		com.liferay.oauth2.provider.model.OAuth2Authorization oAuth2Authorization) {
		oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.deleteTableMapping(pk,
			oAuth2Authorization.getPrimaryKey());
	}

	/**
	 * Removes the association between the o auth2 scope grant and the o auth2 authorizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPKs the primary keys of the o auth2 authorizations
	 */
	@Override
	public void removeOAuth2Authorizations(long pk,
		long[] oAuth2AuthorizationPKs) {
		oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.deleteTableMappings(pk,
			oAuth2AuthorizationPKs);
	}

	/**
	 * Removes the association between the o auth2 scope grant and the o auth2 authorizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2Authorizations the o auth2 authorizations
	 */
	@Override
	public void removeOAuth2Authorizations(long pk,
		List<com.liferay.oauth2.provider.model.OAuth2Authorization> oAuth2Authorizations) {
		removeOAuth2Authorizations(pk,
			ListUtil.toLongArray(oAuth2Authorizations,
				com.liferay.oauth2.provider.model.OAuth2Authorization.O_AUTH2_AUTHORIZATION_ID_ACCESSOR));
	}

	/**
	 * Sets the o auth2 authorizations associated with the o auth2 scope grant, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPKs the primary keys of the o auth2 authorizations to be associated with the o auth2 scope grant
	 */
	@Override
	public void setOAuth2Authorizations(long pk, long[] oAuth2AuthorizationPKs) {
		Set<Long> newOAuth2AuthorizationPKsSet = SetUtil.fromArray(oAuth2AuthorizationPKs);
		Set<Long> oldOAuth2AuthorizationPKsSet = SetUtil.fromArray(oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeOAuth2AuthorizationPKsSet = new HashSet<Long>(oldOAuth2AuthorizationPKsSet);

		removeOAuth2AuthorizationPKsSet.removeAll(newOAuth2AuthorizationPKsSet);

		oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.deleteTableMappings(pk,
			ArrayUtil.toLongArray(removeOAuth2AuthorizationPKsSet));

		newOAuth2AuthorizationPKsSet.removeAll(oldOAuth2AuthorizationPKsSet);

		long companyId = 0;

		OAuth2ScopeGrant oAuth2ScopeGrant = fetchByPrimaryKey(pk);

		if (oAuth2ScopeGrant == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = oAuth2ScopeGrant.getCompanyId();
		}

		oAuth2ScopeGrantToOAuth2AuthorizationTableMapper.addTableMappings(companyId,
			pk, ArrayUtil.toLongArray(newOAuth2AuthorizationPKsSet));
	}

	/**
	 * Sets the o auth2 authorizations associated with the o auth2 scope grant, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2Authorizations the o auth2 authorizations to be associated with the o auth2 scope grant
	 */
	@Override
	public void setOAuth2Authorizations(long pk,
		List<com.liferay.oauth2.provider.model.OAuth2Authorization> oAuth2Authorizations) {
		try {
			long[] oAuth2AuthorizationPKs = new long[oAuth2Authorizations.size()];

			for (int i = 0; i < oAuth2Authorizations.size(); i++) {
				com.liferay.oauth2.provider.model.OAuth2Authorization oAuth2Authorization =
					oAuth2Authorizations.get(i);

				oAuth2AuthorizationPKs[i] = oAuth2Authorization.getPrimaryKey();
			}

			setOAuth2Authorizations(pk, oAuth2AuthorizationPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuth2ScopeGrantModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth2 scope grant persistence.
	 */
	public void afterPropertiesSet() {
		oAuth2ScopeGrantToOAuth2AuthorizationTableMapper = TableMapperFactory.getTableMapper("OA2Auths_OA2ScopeGrants",
				"companyId", "oAuth2ScopeGrantId", "oAuth2AuthorizationId",
				this, oAuth2AuthorizationPersistence);
	}

	public void destroy() {
		entityCache.removeCache(OAuth2ScopeGrantImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("OA2Auths_OA2ScopeGrants");
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	@BeanReference(type = OAuth2AuthorizationPersistence.class)
	protected OAuth2AuthorizationPersistence oAuth2AuthorizationPersistence;
	protected TableMapper<OAuth2ScopeGrant, com.liferay.oauth2.provider.model.OAuth2Authorization> oAuth2ScopeGrantToOAuth2AuthorizationTableMapper;
	private static final String _SQL_SELECT_OAUTH2SCOPEGRANT = "SELECT oAuth2ScopeGrant FROM OAuth2ScopeGrant oAuth2ScopeGrant";
	private static final String _SQL_SELECT_OAUTH2SCOPEGRANT_WHERE_PKS_IN = "SELECT oAuth2ScopeGrant FROM OAuth2ScopeGrant oAuth2ScopeGrant WHERE oAuth2ScopeGrantId IN (";
	private static final String _SQL_SELECT_OAUTH2SCOPEGRANT_WHERE = "SELECT oAuth2ScopeGrant FROM OAuth2ScopeGrant oAuth2ScopeGrant WHERE ";
	private static final String _SQL_COUNT_OAUTH2SCOPEGRANT = "SELECT COUNT(oAuth2ScopeGrant) FROM OAuth2ScopeGrant oAuth2ScopeGrant";
	private static final String _SQL_COUNT_OAUTH2SCOPEGRANT_WHERE = "SELECT COUNT(oAuth2ScopeGrant) FROM OAuth2ScopeGrant oAuth2ScopeGrant WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuth2ScopeGrant.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No OAuth2ScopeGrant exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No OAuth2ScopeGrant exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(OAuth2ScopeGrantPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"oAuth2ApplicationScopeAliasesId"
			});
}