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

import com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException;
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.model.impl.OAuth2ApplicationScopeAliasesImpl;
import com.liferay.oauth2.provider.model.impl.OAuth2ApplicationScopeAliasesModelImpl;
import com.liferay.oauth2.provider.service.persistence.OAuth2ApplicationScopeAliasesPersistence;

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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the o auth2 application scope aliases service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationScopeAliasesPersistence
 * @see com.liferay.oauth2.provider.service.persistence.OAuth2ApplicationScopeAliasesUtil
 * @generated
 */
@ProviderType
public class OAuth2ApplicationScopeAliasesPersistenceImpl
	extends BasePersistenceImpl<OAuth2ApplicationScopeAliases>
	implements OAuth2ApplicationScopeAliasesPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link OAuth2ApplicationScopeAliasesUtil} to access the o auth2 application scope aliases persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = OAuth2ApplicationScopeAliasesImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C = new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C = new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC",
			new String[] { Long.class.getName() },
			OAuth2ApplicationScopeAliasesModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C = new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the o auth2 application scope aliaseses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByC(long companyId) {
		return findByC(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 application scope aliaseses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @return the range of matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByC(long companyId,
		int start, int end) {
		return findByC(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 application scope aliaseses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByC(long companyId,
		int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return findByC(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 application scope aliaseses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByC(long companyId,
		int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<OAuth2ApplicationScopeAliases> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2ApplicationScopeAliases>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases : list) {
					if ((companyId != oAuth2ApplicationScopeAliases.getCompanyId())) {
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

			query.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

			query.append(_FINDER_COLUMN_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(OAuth2ApplicationScopeAliasesModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<OAuth2ApplicationScopeAliases>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2ApplicationScopeAliases>)QueryUtil.list(q,
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
	 * Returns the first o auth2 application scope aliases in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases findByC_First(long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = fetchByC_First(companyId,
				orderByComparator);

		if (oAuth2ApplicationScopeAliases != null) {
			return oAuth2ApplicationScopeAliases;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchOAuth2ApplicationScopeAliasesException(msg.toString());
	}

	/**
	 * Returns the first o auth2 application scope aliases in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByC_First(long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		List<OAuth2ApplicationScopeAliases> list = findByC(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 application scope aliases in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases findByC_Last(long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = fetchByC_Last(companyId,
				orderByComparator);

		if (oAuth2ApplicationScopeAliases != null) {
			return oAuth2ApplicationScopeAliases;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchOAuth2ApplicationScopeAliasesException(msg.toString());
	}

	/**
	 * Returns the last o auth2 application scope aliases in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByC_Last(long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		int count = countByC(companyId);

		if (count == 0) {
			return null;
		}

		List<OAuth2ApplicationScopeAliases> list = findByC(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 application scope aliaseses before and after the current o auth2 application scope aliases in the ordered set where companyId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the primary key of the current o auth2 application scope aliases
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases[] findByC_PrevAndNext(
		long oAuth2ApplicationScopeAliasesId, long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = findByPrimaryKey(oAuth2ApplicationScopeAliasesId);

		Session session = null;

		try {
			session = openSession();

			OAuth2ApplicationScopeAliases[] array = new OAuth2ApplicationScopeAliasesImpl[3];

			array[0] = getByC_PrevAndNext(session,
					oAuth2ApplicationScopeAliases, companyId,
					orderByComparator, true);

			array[1] = oAuth2ApplicationScopeAliases;

			array[2] = getByC_PrevAndNext(session,
					oAuth2ApplicationScopeAliases, companyId,
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

	protected OAuth2ApplicationScopeAliases getByC_PrevAndNext(
		Session session,
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases,
		long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

		query.append(_FINDER_COLUMN_C_COMPANYID_2);

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
			query.append(OAuth2ApplicationScopeAliasesModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(oAuth2ApplicationScopeAliases);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OAuth2ApplicationScopeAliases> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 application scope aliaseses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByC(long companyId) {
		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases : findByC(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(oAuth2ApplicationScopeAliases);
		}
	}

	/**
	 * Returns the number of o auth2 application scope aliaseses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth2 application scope aliaseses
	 */
	@Override
	public int countByC(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

			query.append(_FINDER_COLUMN_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_C_COMPANYID_2 = "oAuth2ApplicationScopeAliases.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_OAUTH2APPLICATIONID =
		new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByOAuth2ApplicationId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID =
		new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByOAuth2ApplicationId", new String[] { Long.class.getName() },
			OAuth2ApplicationScopeAliasesModelImpl.OAUTH2APPLICATIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONID = new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByOAuth2ApplicationId", new String[] { Long.class.getName() });

	/**
	 * Returns all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByOAuth2ApplicationId(
		long oAuth2ApplicationId) {
		return findByOAuth2ApplicationId(oAuth2ApplicationId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @return the range of matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end) {
		return findByOAuth2ApplicationId(oAuth2ApplicationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return findByOAuth2ApplicationId(oAuth2ApplicationId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
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

		List<OAuth2ApplicationScopeAliases> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2ApplicationScopeAliases>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases : list) {
					if ((oAuth2ApplicationId != oAuth2ApplicationScopeAliases.getOAuth2ApplicationId())) {
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

			query.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

			query.append(_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(OAuth2ApplicationScopeAliasesModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2ApplicationId);

				if (!pagination) {
					list = (List<OAuth2ApplicationScopeAliases>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2ApplicationScopeAliases>)QueryUtil.list(q,
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
	 * Returns the first o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases findByOAuth2ApplicationId_First(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = fetchByOAuth2ApplicationId_First(oAuth2ApplicationId,
				orderByComparator);

		if (oAuth2ApplicationScopeAliases != null) {
			return oAuth2ApplicationScopeAliases;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2ApplicationId=");
		msg.append(oAuth2ApplicationId);

		msg.append("}");

		throw new NoSuchOAuth2ApplicationScopeAliasesException(msg.toString());
	}

	/**
	 * Returns the first o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByOAuth2ApplicationId_First(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		List<OAuth2ApplicationScopeAliases> list = findByOAuth2ApplicationId(oAuth2ApplicationId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases findByOAuth2ApplicationId_Last(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = fetchByOAuth2ApplicationId_Last(oAuth2ApplicationId,
				orderByComparator);

		if (oAuth2ApplicationScopeAliases != null) {
			return oAuth2ApplicationScopeAliases;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2ApplicationId=");
		msg.append(oAuth2ApplicationId);

		msg.append("}");

		throw new NoSuchOAuth2ApplicationScopeAliasesException(msg.toString());
	}

	/**
	 * Returns the last o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByOAuth2ApplicationId_Last(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		int count = countByOAuth2ApplicationId(oAuth2ApplicationId);

		if (count == 0) {
			return null;
		}

		List<OAuth2ApplicationScopeAliases> list = findByOAuth2ApplicationId(oAuth2ApplicationId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 application scope aliaseses before and after the current o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the primary key of the current o auth2 application scope aliases
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases[] findByOAuth2ApplicationId_PrevAndNext(
		long oAuth2ApplicationScopeAliasesId, long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = findByPrimaryKey(oAuth2ApplicationScopeAliasesId);

		Session session = null;

		try {
			session = openSession();

			OAuth2ApplicationScopeAliases[] array = new OAuth2ApplicationScopeAliasesImpl[3];

			array[0] = getByOAuth2ApplicationId_PrevAndNext(session,
					oAuth2ApplicationScopeAliases, oAuth2ApplicationId,
					orderByComparator, true);

			array[1] = oAuth2ApplicationScopeAliases;

			array[2] = getByOAuth2ApplicationId_PrevAndNext(session,
					oAuth2ApplicationScopeAliases, oAuth2ApplicationId,
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

	protected OAuth2ApplicationScopeAliases getByOAuth2ApplicationId_PrevAndNext(
		Session session,
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases,
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

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
			query.append(OAuth2ApplicationScopeAliasesModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(oAuth2ApplicationId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(oAuth2ApplicationScopeAliases);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OAuth2ApplicationScopeAliases> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63; from the database.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 */
	@Override
	public void removeByOAuth2ApplicationId(long oAuth2ApplicationId) {
		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases : findByOAuth2ApplicationId(
				oAuth2ApplicationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(oAuth2ApplicationScopeAliases);
		}
	}

	/**
	 * Returns the number of o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the number of matching o auth2 application scope aliaseses
	 */
	@Override
	public int countByOAuth2ApplicationId(long oAuth2ApplicationId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONID;

		Object[] finderArgs = new Object[] { oAuth2ApplicationId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

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
		"oAuth2ApplicationScopeAliases.oAuth2ApplicationId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_O_S = new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByO_S",
			new String[] { Long.class.getName(), String.class.getName() },
			OAuth2ApplicationScopeAliasesModelImpl.OAUTH2APPLICATIONID_COLUMN_BITMASK |
			OAuth2ApplicationScopeAliasesModelImpl.SCOPEALIASES_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_O_S = new FinderPath(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByO_S",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the o auth2 application scope aliases where oAuth2ApplicationId = &#63; and scopeAliases = &#63; or throws a {@link NoSuchOAuth2ApplicationScopeAliasesException} if it could not be found.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param scopeAliases the scope aliases
	 * @return the matching o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases findByO_S(long oAuth2ApplicationId,
		String scopeAliases)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = fetchByO_S(oAuth2ApplicationId,
				scopeAliases);

		if (oAuth2ApplicationScopeAliases == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("oAuth2ApplicationId=");
			msg.append(oAuth2ApplicationId);

			msg.append(", scopeAliases=");
			msg.append(scopeAliases);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchOAuth2ApplicationScopeAliasesException(msg.toString());
		}

		return oAuth2ApplicationScopeAliases;
	}

	/**
	 * Returns the o auth2 application scope aliases where oAuth2ApplicationId = &#63; and scopeAliases = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param scopeAliases the scope aliases
	 * @return the matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByO_S(long oAuth2ApplicationId,
		String scopeAliases) {
		return fetchByO_S(oAuth2ApplicationId, scopeAliases, true);
	}

	/**
	 * Returns the o auth2 application scope aliases where oAuth2ApplicationId = &#63; and scopeAliases = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param scopeAliases the scope aliases
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByO_S(long oAuth2ApplicationId,
		String scopeAliases, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { oAuth2ApplicationId, scopeAliases };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_O_S,
					finderArgs, this);
		}

		if (result instanceof OAuth2ApplicationScopeAliases) {
			OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = (OAuth2ApplicationScopeAliases)result;

			if ((oAuth2ApplicationId != oAuth2ApplicationScopeAliases.getOAuth2ApplicationId()) ||
					!Objects.equals(scopeAliases,
						oAuth2ApplicationScopeAliases.getScopeAliases())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

			query.append(_FINDER_COLUMN_O_S_OAUTH2APPLICATIONID_2);

			boolean bindScopeAliases = false;

			if (scopeAliases == null) {
				query.append(_FINDER_COLUMN_O_S_SCOPEALIASES_1);
			}
			else if (scopeAliases.equals("")) {
				query.append(_FINDER_COLUMN_O_S_SCOPEALIASES_3);
			}
			else {
				bindScopeAliases = true;

				query.append(_FINDER_COLUMN_O_S_SCOPEALIASES_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2ApplicationId);

				if (bindScopeAliases) {
					qPos.add(scopeAliases);
				}

				List<OAuth2ApplicationScopeAliases> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_O_S, finderArgs,
						list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"OAuth2ApplicationScopeAliasesPersistenceImpl.fetchByO_S(long, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = list.get(0);

					result = oAuth2ApplicationScopeAliases;

					cacheResult(oAuth2ApplicationScopeAliases);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_O_S, finderArgs);

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
			return (OAuth2ApplicationScopeAliases)result;
		}
	}

	/**
	 * Removes the o auth2 application scope aliases where oAuth2ApplicationId = &#63; and scopeAliases = &#63; from the database.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param scopeAliases the scope aliases
	 * @return the o auth2 application scope aliases that was removed
	 */
	@Override
	public OAuth2ApplicationScopeAliases removeByO_S(long oAuth2ApplicationId,
		String scopeAliases)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = findByO_S(oAuth2ApplicationId,
				scopeAliases);

		return remove(oAuth2ApplicationScopeAliases);
	}

	/**
	 * Returns the number of o auth2 application scope aliaseses where oAuth2ApplicationId = &#63; and scopeAliases = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param scopeAliases the scope aliases
	 * @return the number of matching o auth2 application scope aliaseses
	 */
	@Override
	public int countByO_S(long oAuth2ApplicationId, String scopeAliases) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_O_S;

		Object[] finderArgs = new Object[] { oAuth2ApplicationId, scopeAliases };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

			query.append(_FINDER_COLUMN_O_S_OAUTH2APPLICATIONID_2);

			boolean bindScopeAliases = false;

			if (scopeAliases == null) {
				query.append(_FINDER_COLUMN_O_S_SCOPEALIASES_1);
			}
			else if (scopeAliases.equals("")) {
				query.append(_FINDER_COLUMN_O_S_SCOPEALIASES_3);
			}
			else {
				bindScopeAliases = true;

				query.append(_FINDER_COLUMN_O_S_SCOPEALIASES_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2ApplicationId);

				if (bindScopeAliases) {
					qPos.add(scopeAliases);
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

	private static final String _FINDER_COLUMN_O_S_OAUTH2APPLICATIONID_2 = "oAuth2ApplicationScopeAliases.oAuth2ApplicationId = ? AND ";
	private static final String _FINDER_COLUMN_O_S_SCOPEALIASES_1 = "oAuth2ApplicationScopeAliases.scopeAliases IS NULL";
	private static final String _FINDER_COLUMN_O_S_SCOPEALIASES_2 = "oAuth2ApplicationScopeAliases.scopeAliases = ?";
	private static final String _FINDER_COLUMN_O_S_SCOPEALIASES_3 = "(oAuth2ApplicationScopeAliases.scopeAliases IS NULL OR oAuth2ApplicationScopeAliases.scopeAliases = '')";

	public OAuth2ApplicationScopeAliasesPersistenceImpl() {
		setModelClass(OAuth2ApplicationScopeAliases.class);

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
	 * Caches the o auth2 application scope aliases in the entity cache if it is enabled.
	 *
	 * @param oAuth2ApplicationScopeAliases the o auth2 application scope aliases
	 */
	@Override
	public void cacheResult(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {
		entityCache.putResult(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesImpl.class,
			oAuth2ApplicationScopeAliases.getPrimaryKey(),
			oAuth2ApplicationScopeAliases);

		finderCache.putResult(FINDER_PATH_FETCH_BY_O_S,
			new Object[] {
				oAuth2ApplicationScopeAliases.getOAuth2ApplicationId(),
				oAuth2ApplicationScopeAliases.getScopeAliases()
			}, oAuth2ApplicationScopeAliases);

		oAuth2ApplicationScopeAliases.resetOriginalValues();
	}

	/**
	 * Caches the o auth2 application scope aliaseses in the entity cache if it is enabled.
	 *
	 * @param oAuth2ApplicationScopeAliaseses the o auth2 application scope aliaseses
	 */
	@Override
	public void cacheResult(
		List<OAuth2ApplicationScopeAliases> oAuth2ApplicationScopeAliaseses) {
		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases : oAuth2ApplicationScopeAliaseses) {
			if (entityCache.getResult(
						OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
						OAuth2ApplicationScopeAliasesImpl.class,
						oAuth2ApplicationScopeAliases.getPrimaryKey()) == null) {
				cacheResult(oAuth2ApplicationScopeAliases);
			}
			else {
				oAuth2ApplicationScopeAliases.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all o auth2 application scope aliaseses.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuth2ApplicationScopeAliasesImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the o auth2 application scope aliases.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {
		entityCache.removeResult(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesImpl.class,
			oAuth2ApplicationScopeAliases.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((OAuth2ApplicationScopeAliasesModelImpl)oAuth2ApplicationScopeAliases,
			true);
	}

	@Override
	public void clearCache(
		List<OAuth2ApplicationScopeAliases> oAuth2ApplicationScopeAliaseses) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases : oAuth2ApplicationScopeAliaseses) {
			entityCache.removeResult(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
				OAuth2ApplicationScopeAliasesImpl.class,
				oAuth2ApplicationScopeAliases.getPrimaryKey());

			clearUniqueFindersCache((OAuth2ApplicationScopeAliasesModelImpl)oAuth2ApplicationScopeAliases,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuth2ApplicationScopeAliasesModelImpl oAuth2ApplicationScopeAliasesModelImpl) {
		Object[] args = new Object[] {
				oAuth2ApplicationScopeAliasesModelImpl.getOAuth2ApplicationId(),
				oAuth2ApplicationScopeAliasesModelImpl.getScopeAliases()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_O_S, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_O_S, args,
			oAuth2ApplicationScopeAliasesModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		OAuth2ApplicationScopeAliasesModelImpl oAuth2ApplicationScopeAliasesModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					oAuth2ApplicationScopeAliasesModelImpl.getOAuth2ApplicationId(),
					oAuth2ApplicationScopeAliasesModelImpl.getScopeAliases()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_O_S, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_O_S, args);
		}

		if ((oAuth2ApplicationScopeAliasesModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_O_S.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					oAuth2ApplicationScopeAliasesModelImpl.getOriginalOAuth2ApplicationId(),
					oAuth2ApplicationScopeAliasesModelImpl.getOriginalScopeAliases()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_O_S, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_O_S, args);
		}
	}

	/**
	 * Creates a new o auth2 application scope aliases with the primary key. Does not add the o auth2 application scope aliases to the database.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the primary key for the new o auth2 application scope aliases
	 * @return the new o auth2 application scope aliases
	 */
	@Override
	public OAuth2ApplicationScopeAliases create(
		long oAuth2ApplicationScopeAliasesId) {
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = new OAuth2ApplicationScopeAliasesImpl();

		oAuth2ApplicationScopeAliases.setNew(true);
		oAuth2ApplicationScopeAliases.setPrimaryKey(oAuth2ApplicationScopeAliasesId);

		oAuth2ApplicationScopeAliases.setCompanyId(companyProvider.getCompanyId());

		return oAuth2ApplicationScopeAliases;
	}

	/**
	 * Removes the o auth2 application scope aliases with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the primary key of the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases that was removed
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases remove(
		long oAuth2ApplicationScopeAliasesId)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		return remove((Serializable)oAuth2ApplicationScopeAliasesId);
	}

	/**
	 * Removes the o auth2 application scope aliases with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases that was removed
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases remove(Serializable primaryKey)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		Session session = null;

		try {
			session = openSession();

			OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = (OAuth2ApplicationScopeAliases)session.get(OAuth2ApplicationScopeAliasesImpl.class,
					primaryKey);

			if (oAuth2ApplicationScopeAliases == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOAuth2ApplicationScopeAliasesException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(oAuth2ApplicationScopeAliases);
		}
		catch (NoSuchOAuth2ApplicationScopeAliasesException nsee) {
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
	protected OAuth2ApplicationScopeAliases removeImpl(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuth2ApplicationScopeAliases)) {
				oAuth2ApplicationScopeAliases = (OAuth2ApplicationScopeAliases)session.get(OAuth2ApplicationScopeAliasesImpl.class,
						oAuth2ApplicationScopeAliases.getPrimaryKeyObj());
			}

			if (oAuth2ApplicationScopeAliases != null) {
				session.delete(oAuth2ApplicationScopeAliases);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (oAuth2ApplicationScopeAliases != null) {
			clearCache(oAuth2ApplicationScopeAliases);
		}

		return oAuth2ApplicationScopeAliases;
	}

	@Override
	public OAuth2ApplicationScopeAliases updateImpl(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {
		boolean isNew = oAuth2ApplicationScopeAliases.isNew();

		if (!(oAuth2ApplicationScopeAliases instanceof OAuth2ApplicationScopeAliasesModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(oAuth2ApplicationScopeAliases.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(oAuth2ApplicationScopeAliases);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuth2ApplicationScopeAliases proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuth2ApplicationScopeAliases implementation " +
				oAuth2ApplicationScopeAliases.getClass());
		}

		OAuth2ApplicationScopeAliasesModelImpl oAuth2ApplicationScopeAliasesModelImpl =
			(OAuth2ApplicationScopeAliasesModelImpl)oAuth2ApplicationScopeAliases;

		Session session = null;

		try {
			session = openSession();

			if (oAuth2ApplicationScopeAliases.isNew()) {
				session.save(oAuth2ApplicationScopeAliases);

				oAuth2ApplicationScopeAliases.setNew(false);
			}
			else {
				oAuth2ApplicationScopeAliases = (OAuth2ApplicationScopeAliases)session.merge(oAuth2ApplicationScopeAliases);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!OAuth2ApplicationScopeAliasesModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					oAuth2ApplicationScopeAliasesModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C,
				args);

			args = new Object[] {
					oAuth2ApplicationScopeAliasesModelImpl.getOAuth2ApplicationId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((oAuth2ApplicationScopeAliasesModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						oAuth2ApplicationScopeAliasesModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C,
					args);

				args = new Object[] {
						oAuth2ApplicationScopeAliasesModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C,
					args);
			}

			if ((oAuth2ApplicationScopeAliasesModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						oAuth2ApplicationScopeAliasesModelImpl.getOriginalOAuth2ApplicationId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID,
					args);

				args = new Object[] {
						oAuth2ApplicationScopeAliasesModelImpl.getOAuth2ApplicationId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_OAUTH2APPLICATIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_OAUTH2APPLICATIONID,
					args);
			}
		}

		entityCache.putResult(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
			OAuth2ApplicationScopeAliasesImpl.class,
			oAuth2ApplicationScopeAliases.getPrimaryKey(),
			oAuth2ApplicationScopeAliases, false);

		clearUniqueFindersCache(oAuth2ApplicationScopeAliasesModelImpl, false);
		cacheUniqueFindersCache(oAuth2ApplicationScopeAliasesModelImpl);

		oAuth2ApplicationScopeAliases.resetOriginalValues();

		return oAuth2ApplicationScopeAliases;
	}

	/**
	 * Returns the o auth2 application scope aliases with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = fetchByPrimaryKey(primaryKey);

		if (oAuth2ApplicationScopeAliases == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOAuth2ApplicationScopeAliasesException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return oAuth2ApplicationScopeAliases;
	}

	/**
	 * Returns the o auth2 application scope aliases with the primary key or throws a {@link NoSuchOAuth2ApplicationScopeAliasesException} if it could not be found.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the primary key of the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases findByPrimaryKey(
		long oAuth2ApplicationScopeAliasesId)
		throws NoSuchOAuth2ApplicationScopeAliasesException {
		return findByPrimaryKey((Serializable)oAuth2ApplicationScopeAliasesId);
	}

	/**
	 * Returns the o auth2 application scope aliases with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases, or <code>null</code> if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
				OAuth2ApplicationScopeAliasesImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = (OAuth2ApplicationScopeAliases)serializable;

		if (oAuth2ApplicationScopeAliases == null) {
			Session session = null;

			try {
				session = openSession();

				oAuth2ApplicationScopeAliases = (OAuth2ApplicationScopeAliases)session.get(OAuth2ApplicationScopeAliasesImpl.class,
						primaryKey);

				if (oAuth2ApplicationScopeAliases != null) {
					cacheResult(oAuth2ApplicationScopeAliases);
				}
				else {
					entityCache.putResult(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
						OAuth2ApplicationScopeAliasesImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2ApplicationScopeAliasesImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return oAuth2ApplicationScopeAliases;
	}

	/**
	 * Returns the o auth2 application scope aliases with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the primary key of the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases, or <code>null</code> if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByPrimaryKey(
		long oAuth2ApplicationScopeAliasesId) {
		return fetchByPrimaryKey((Serializable)oAuth2ApplicationScopeAliasesId);
	}

	@Override
	public Map<Serializable, OAuth2ApplicationScopeAliases> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, OAuth2ApplicationScopeAliases> map = new HashMap<Serializable, OAuth2ApplicationScopeAliases>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases = fetchByPrimaryKey(primaryKey);

			if (oAuth2ApplicationScopeAliases != null) {
				map.put(primaryKey, oAuth2ApplicationScopeAliases);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2ApplicationScopeAliasesImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(OAuth2ApplicationScopeAliases)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE_PKS_IN);

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

			for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases : (List<OAuth2ApplicationScopeAliases>)q.list()) {
				map.put(oAuth2ApplicationScopeAliases.getPrimaryKeyObj(),
					oAuth2ApplicationScopeAliases);

				cacheResult(oAuth2ApplicationScopeAliases);

				uncachedPrimaryKeys.remove(oAuth2ApplicationScopeAliases.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(OAuth2ApplicationScopeAliasesModelImpl.ENTITY_CACHE_ENABLED,
					OAuth2ApplicationScopeAliasesImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the o auth2 application scope aliaseses.
	 *
	 * @return the o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 application scope aliaseses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @return the range of o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 application scope aliaseses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findAll(int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 application scope aliaseses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findAll(int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
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

		List<OAuth2ApplicationScopeAliases> list = null;

		if (retrieveFromCache) {
			list = (List<OAuth2ApplicationScopeAliases>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES;

				if (pagination) {
					sql = sql.concat(OAuth2ApplicationScopeAliasesModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<OAuth2ApplicationScopeAliases>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<OAuth2ApplicationScopeAliases>)QueryUtil.list(q,
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
	 * Removes all the o auth2 application scope aliaseses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases : findAll()) {
			remove(oAuth2ApplicationScopeAliases);
		}
	}

	/**
	 * Returns the number of o auth2 application scope aliaseses.
	 *
	 * @return the number of o auth2 application scope aliaseses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuth2ApplicationScopeAliasesModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth2 application scope aliases persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(OAuth2ApplicationScopeAliasesImpl.class.getName());
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
	private static final String _SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES = "SELECT oAuth2ApplicationScopeAliases FROM OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases";
	private static final String _SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE_PKS_IN =
		"SELECT oAuth2ApplicationScopeAliases FROM OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases WHERE oA2AScopeAliasesId IN (";
	private static final String _SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE = "SELECT oAuth2ApplicationScopeAliases FROM OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases WHERE ";
	private static final String _SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES = "SELECT COUNT(oAuth2ApplicationScopeAliases) FROM OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases";
	private static final String _SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES_WHERE = "SELECT COUNT(oAuth2ApplicationScopeAliases) FROM OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuth2ApplicationScopeAliases.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No OAuth2ApplicationScopeAliases exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No OAuth2ApplicationScopeAliases exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(OAuth2ApplicationScopeAliasesPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"oAuth2ApplicationScopeAliasesId"
			});
}