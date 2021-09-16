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

import com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException;
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliasesTable;
import com.liferay.oauth2.provider.model.impl.OAuth2ApplicationScopeAliasesImpl;
import com.liferay.oauth2.provider.model.impl.OAuth2ApplicationScopeAliasesModelImpl;
import com.liferay.oauth2.provider.service.persistence.OAuth2ApplicationScopeAliasesPersistence;
import com.liferay.oauth2.provider.service.persistence.impl.constants.OAuthTwoPersistenceConstants;
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

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the o auth2 application scope aliases service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {
		OAuth2ApplicationScopeAliasesPersistence.class, BasePersistence.class
	}
)
public class OAuth2ApplicationScopeAliasesPersistenceImpl
	extends BasePersistenceImpl<OAuth2ApplicationScopeAliases>
	implements OAuth2ApplicationScopeAliasesPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OAuth2ApplicationScopeAliasesUtil</code> to access the o auth2 application scope aliases persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OAuth2ApplicationScopeAliasesImpl.class.getName();

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
	 * Returns all the o auth2 application scope aliaseses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 application scope aliaseses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationScopeAliasesModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @return the range of matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 application scope aliaseses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationScopeAliasesModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 application scope aliaseses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationScopeAliasesModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
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

		List<OAuth2ApplicationScopeAliases> list = null;

		if (useFinderCache) {
			list = (List<OAuth2ApplicationScopeAliases>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2ApplicationScopeAliases
						oAuth2ApplicationScopeAliases : list) {

					if (companyId !=
							oAuth2ApplicationScopeAliases.getCompanyId()) {

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

			sb.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuth2ApplicationScopeAliasesModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<OAuth2ApplicationScopeAliases>)QueryUtil.list(
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
	 * Returns the first o auth2 application scope aliases in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases findByCompanyId_First(
			long companyId,
			OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws NoSuchOAuth2ApplicationScopeAliasesException {

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (oAuth2ApplicationScopeAliases != null) {
			return oAuth2ApplicationScopeAliases;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOAuth2ApplicationScopeAliasesException(sb.toString());
	}

	/**
	 * Returns the first o auth2 application scope aliases in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByCompanyId_First(
		long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {

		List<OAuth2ApplicationScopeAliases> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

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
	public OAuth2ApplicationScopeAliases findByCompanyId_Last(
			long companyId,
			OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws NoSuchOAuth2ApplicationScopeAliasesException {

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (oAuth2ApplicationScopeAliases != null) {
			return oAuth2ApplicationScopeAliases;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchOAuth2ApplicationScopeAliasesException(sb.toString());
	}

	/**
	 * Returns the last o auth2 application scope aliases in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<OAuth2ApplicationScopeAliases> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

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
	public OAuth2ApplicationScopeAliases[] findByCompanyId_PrevAndNext(
			long oAuth2ApplicationScopeAliasesId, long companyId,
			OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws NoSuchOAuth2ApplicationScopeAliasesException {

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			findByPrimaryKey(oAuth2ApplicationScopeAliasesId);

		Session session = null;

		try {
			session = openSession();

			OAuth2ApplicationScopeAliases[] array =
				new OAuth2ApplicationScopeAliasesImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, oAuth2ApplicationScopeAliases, companyId,
				orderByComparator, true);

			array[1] = oAuth2ApplicationScopeAliases;

			array[2] = getByCompanyId_PrevAndNext(
				session, oAuth2ApplicationScopeAliases, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2ApplicationScopeAliases getByCompanyId_PrevAndNext(
		Session session,
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases,
		long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
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

		sb.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

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
			sb.append(OAuth2ApplicationScopeAliasesModelImpl.ORDER_BY_JPQL);
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
						oAuth2ApplicationScopeAliases)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuth2ApplicationScopeAliases> list = query.list();

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
	public void removeByCompanyId(long companyId) {
		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases :
				findByCompanyId(
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
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

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
		"oAuth2ApplicationScopeAliases.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByOAuth2ApplicationId;
	private FinderPath _finderPathWithoutPaginationFindByOAuth2ApplicationId;
	private FinderPath _finderPathCountByOAuth2ApplicationId;

	/**
	 * Returns all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByOAuth2ApplicationId(
		long oAuth2ApplicationId) {

		return findByOAuth2ApplicationId(
			oAuth2ApplicationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationScopeAliasesModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationScopeAliasesModelImpl</code>.
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

		return findByOAuth2ApplicationId(
			oAuth2ApplicationId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationScopeAliasesModelImpl</code>.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByOAuth2ApplicationId;
				finderArgs = new Object[] {oAuth2ApplicationId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByOAuth2ApplicationId;
			finderArgs = new Object[] {
				oAuth2ApplicationId, start, end, orderByComparator
			};
		}

		List<OAuth2ApplicationScopeAliases> list = null;

		if (useFinderCache) {
			list = (List<OAuth2ApplicationScopeAliases>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2ApplicationScopeAliases
						oAuth2ApplicationScopeAliases : list) {

					if (oAuth2ApplicationId !=
							oAuth2ApplicationScopeAliases.
								getOAuth2ApplicationId()) {

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

			sb.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

			sb.append(_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuth2ApplicationScopeAliasesModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(oAuth2ApplicationId);

				list = (List<OAuth2ApplicationScopeAliases>)QueryUtil.list(
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

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			fetchByOAuth2ApplicationId_First(
				oAuth2ApplicationId, orderByComparator);

		if (oAuth2ApplicationScopeAliases != null) {
			return oAuth2ApplicationScopeAliases;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("oAuth2ApplicationId=");
		sb.append(oAuth2ApplicationId);

		sb.append("}");

		throw new NoSuchOAuth2ApplicationScopeAliasesException(sb.toString());
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

		List<OAuth2ApplicationScopeAliases> list = findByOAuth2ApplicationId(
			oAuth2ApplicationId, 0, 1, orderByComparator);

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

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			fetchByOAuth2ApplicationId_Last(
				oAuth2ApplicationId, orderByComparator);

		if (oAuth2ApplicationScopeAliases != null) {
			return oAuth2ApplicationScopeAliases;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("oAuth2ApplicationId=");
		sb.append(oAuth2ApplicationId);

		sb.append("}");

		throw new NoSuchOAuth2ApplicationScopeAliasesException(sb.toString());
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

		List<OAuth2ApplicationScopeAliases> list = findByOAuth2ApplicationId(
			oAuth2ApplicationId, count - 1, count, orderByComparator);

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
	public OAuth2ApplicationScopeAliases[]
			findByOAuth2ApplicationId_PrevAndNext(
				long oAuth2ApplicationScopeAliasesId, long oAuth2ApplicationId,
				OrderByComparator<OAuth2ApplicationScopeAliases>
					orderByComparator)
		throws NoSuchOAuth2ApplicationScopeAliasesException {

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			findByPrimaryKey(oAuth2ApplicationScopeAliasesId);

		Session session = null;

		try {
			session = openSession();

			OAuth2ApplicationScopeAliases[] array =
				new OAuth2ApplicationScopeAliasesImpl[3];

			array[0] = getByOAuth2ApplicationId_PrevAndNext(
				session, oAuth2ApplicationScopeAliases, oAuth2ApplicationId,
				orderByComparator, true);

			array[1] = oAuth2ApplicationScopeAliases;

			array[2] = getByOAuth2ApplicationId_PrevAndNext(
				session, oAuth2ApplicationScopeAliases, oAuth2ApplicationId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2ApplicationScopeAliases
		getByOAuth2ApplicationId_PrevAndNext(
			Session session,
			OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases,
			long oAuth2ApplicationId,
			OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
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

		sb.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

		sb.append(_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2);

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
			sb.append(OAuth2ApplicationScopeAliasesModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(oAuth2ApplicationId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuth2ApplicationScopeAliases)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuth2ApplicationScopeAliases> list = query.list();

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
		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases :
				findByOAuth2ApplicationId(
					oAuth2ApplicationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathCountByOAuth2ApplicationId;

		Object[] finderArgs = new Object[] {oAuth2ApplicationId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES_WHERE);

			sb.append(_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(oAuth2ApplicationId);

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
		_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2 =
			"oAuth2ApplicationScopeAliases.oAuth2ApplicationId = ?";

	public OAuth2ApplicationScopeAliasesPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"oAuth2ApplicationScopeAliasesId", "oA2AScopeAliasesId");

		setDBColumnNames(dbColumnNames);

		setModelClass(OAuth2ApplicationScopeAliases.class);

		setModelImplClass(OAuth2ApplicationScopeAliasesImpl.class);
		setModelPKClass(long.class);

		setTable(OAuth2ApplicationScopeAliasesTable.INSTANCE);
	}

	/**
	 * Caches the o auth2 application scope aliases in the entity cache if it is enabled.
	 *
	 * @param oAuth2ApplicationScopeAliases the o auth2 application scope aliases
	 */
	@Override
	public void cacheResult(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {

		entityCache.putResult(
			OAuth2ApplicationScopeAliasesImpl.class,
			oAuth2ApplicationScopeAliases.getPrimaryKey(),
			oAuth2ApplicationScopeAliases);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the o auth2 application scope aliaseses in the entity cache if it is enabled.
	 *
	 * @param oAuth2ApplicationScopeAliaseses the o auth2 application scope aliaseses
	 */
	@Override
	public void cacheResult(
		List<OAuth2ApplicationScopeAliases> oAuth2ApplicationScopeAliaseses) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (oAuth2ApplicationScopeAliaseses.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases :
				oAuth2ApplicationScopeAliaseses) {

			if (entityCache.getResult(
					OAuth2ApplicationScopeAliasesImpl.class,
					oAuth2ApplicationScopeAliases.getPrimaryKey()) == null) {

				cacheResult(oAuth2ApplicationScopeAliases);
			}
		}
	}

	/**
	 * Clears the cache for all o auth2 application scope aliaseses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuth2ApplicationScopeAliasesImpl.class);

		finderCache.clearCache(OAuth2ApplicationScopeAliasesImpl.class);
	}

	/**
	 * Clears the cache for the o auth2 application scope aliases.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {

		entityCache.removeResult(
			OAuth2ApplicationScopeAliasesImpl.class,
			oAuth2ApplicationScopeAliases);
	}

	@Override
	public void clearCache(
		List<OAuth2ApplicationScopeAliases> oAuth2ApplicationScopeAliaseses) {

		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases :
				oAuth2ApplicationScopeAliaseses) {

			entityCache.removeResult(
				OAuth2ApplicationScopeAliasesImpl.class,
				oAuth2ApplicationScopeAliases);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(OAuth2ApplicationScopeAliasesImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				OAuth2ApplicationScopeAliasesImpl.class, primaryKey);
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

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			new OAuth2ApplicationScopeAliasesImpl();

		oAuth2ApplicationScopeAliases.setNew(true);
		oAuth2ApplicationScopeAliases.setPrimaryKey(
			oAuth2ApplicationScopeAliasesId);

		oAuth2ApplicationScopeAliases.setCompanyId(
			CompanyThreadLocal.getCompanyId());

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

			OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
				(OAuth2ApplicationScopeAliases)session.get(
					OAuth2ApplicationScopeAliasesImpl.class, primaryKey);

			if (oAuth2ApplicationScopeAliases == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOAuth2ApplicationScopeAliasesException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(oAuth2ApplicationScopeAliases);
		}
		catch (NoSuchOAuth2ApplicationScopeAliasesException
					noSuchEntityException) {

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
	protected OAuth2ApplicationScopeAliases removeImpl(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuth2ApplicationScopeAliases)) {
				oAuth2ApplicationScopeAliases =
					(OAuth2ApplicationScopeAliases)session.get(
						OAuth2ApplicationScopeAliasesImpl.class,
						oAuth2ApplicationScopeAliases.getPrimaryKeyObj());
			}

			if (oAuth2ApplicationScopeAliases != null) {
				session.delete(oAuth2ApplicationScopeAliases);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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

		if (!(oAuth2ApplicationScopeAliases instanceof
				OAuth2ApplicationScopeAliasesModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					oAuth2ApplicationScopeAliases.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					oAuth2ApplicationScopeAliases);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuth2ApplicationScopeAliases proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuth2ApplicationScopeAliases implementation " +
					oAuth2ApplicationScopeAliases.getClass());
		}

		OAuth2ApplicationScopeAliasesModelImpl
			oAuth2ApplicationScopeAliasesModelImpl =
				(OAuth2ApplicationScopeAliasesModelImpl)
					oAuth2ApplicationScopeAliases;

		if (isNew && (oAuth2ApplicationScopeAliases.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				oAuth2ApplicationScopeAliases.setCreateDate(date);
			}
			else {
				oAuth2ApplicationScopeAliases.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(oAuth2ApplicationScopeAliases);
			}
			else {
				oAuth2ApplicationScopeAliases =
					(OAuth2ApplicationScopeAliases)session.merge(
						oAuth2ApplicationScopeAliases);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			OAuth2ApplicationScopeAliasesImpl.class,
			oAuth2ApplicationScopeAliasesModelImpl, false, true);

		if (isNew) {
			oAuth2ApplicationScopeAliases.setNew(false);
		}

		oAuth2ApplicationScopeAliases.resetOriginalValues();

		return oAuth2ApplicationScopeAliases;
	}

	/**
	 * Returns the o auth2 application scope aliases with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases
	 * @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchOAuth2ApplicationScopeAliasesException {

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			fetchByPrimaryKey(primaryKey);

		if (oAuth2ApplicationScopeAliases == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOAuth2ApplicationScopeAliasesException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return oAuth2ApplicationScopeAliases;
	}

	/**
	 * Returns the o auth2 application scope aliases with the primary key or throws a <code>NoSuchOAuth2ApplicationScopeAliasesException</code> if it could not be found.
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
	 * @param oAuth2ApplicationScopeAliasesId the primary key of the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases, or <code>null</code> if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public OAuth2ApplicationScopeAliases fetchByPrimaryKey(
		long oAuth2ApplicationScopeAliasesId) {

		return fetchByPrimaryKey((Serializable)oAuth2ApplicationScopeAliasesId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationScopeAliasesModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationScopeAliasesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findAll(
		int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 application scope aliaseses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationScopeAliasesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth2 application scope aliaseses
	 */
	@Override
	public List<OAuth2ApplicationScopeAliases> findAll(
		int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
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

		List<OAuth2ApplicationScopeAliases> list = null;

		if (useFinderCache) {
			list = (List<OAuth2ApplicationScopeAliases>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES;

				sql = sql.concat(
					OAuth2ApplicationScopeAliasesModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<OAuth2ApplicationScopeAliases>)QueryUtil.list(
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
	 * Removes all the o auth2 application scope aliaseses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases :
				findAll()) {

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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES);

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
		return "oA2AScopeAliasesId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuth2ApplicationScopeAliasesModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth2 application scope aliases persistence.
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

		_finderPathWithPaginationFindByOAuth2ApplicationId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOAuth2ApplicationId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"oAuth2ApplicationId"}, true);

		_finderPathWithoutPaginationFindByOAuth2ApplicationId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByOAuth2ApplicationId", new String[] {Long.class.getName()},
			new String[] {"oAuth2ApplicationId"}, true);

		_finderPathCountByOAuth2ApplicationId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByOAuth2ApplicationId", new String[] {Long.class.getName()},
			new String[] {"oAuth2ApplicationId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			OAuth2ApplicationScopeAliasesImpl.class.getName());
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES =
		"SELECT oAuth2ApplicationScopeAliases FROM OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases";

	private static final String
		_SQL_SELECT_OAUTH2APPLICATIONSCOPEALIASES_WHERE =
			"SELECT oAuth2ApplicationScopeAliases FROM OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases WHERE ";

	private static final String _SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES =
		"SELECT COUNT(oAuth2ApplicationScopeAliases) FROM OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases";

	private static final String _SQL_COUNT_OAUTH2APPLICATIONSCOPEALIASES_WHERE =
		"SELECT COUNT(oAuth2ApplicationScopeAliases) FROM OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"oAuth2ApplicationScopeAliases.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OAuth2ApplicationScopeAliases exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OAuth2ApplicationScopeAliases exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2ApplicationScopeAliasesPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"oAuth2ApplicationScopeAliasesId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private OAuth2ApplicationScopeAliasesModelArgumentsResolver
		_oAuth2ApplicationScopeAliasesModelArgumentsResolver;

}