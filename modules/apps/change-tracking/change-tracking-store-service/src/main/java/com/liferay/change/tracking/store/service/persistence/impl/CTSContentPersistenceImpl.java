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

package com.liferay.change.tracking.store.service.persistence.impl;

import com.liferay.change.tracking.store.exception.NoSuchContentException;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.change.tracking.store.model.impl.CTSContentImpl;
import com.liferay.change.tracking.store.model.impl.CTSContentModelImpl;
import com.liferay.change.tracking.store.service.persistence.CTSContentPersistence;
import com.liferay.change.tracking.store.service.persistence.impl.constants.CTSPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * The persistence implementation for the cts content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @generated
 */
@Component(service = CTSContentPersistence.class)
public class CTSContentPersistenceImpl
	extends BasePersistenceImpl<CTSContent> implements CTSContentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTSContentUtil</code> to access the cts content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTSContentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC_R_S;
	private FinderPath _finderPathWithoutPaginationFindByC_R_S;
	private FinderPath _finderPathCountByC_R_S;

	/**
	 * Returns all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @return the matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType) {

		return findByC_R_S(
			companyId, repositoryId, storeType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType, int start,
		int end) {

		return findByC_R_S(
			companyId, repositoryId, storeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType, int start, int end,
		OrderByComparator<CTSContent> orderByComparator) {

		return findByC_R_S(
			companyId, repositoryId, storeType, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType, int start, int end,
		OrderByComparator<CTSContent> orderByComparator,
		boolean useFinderCache) {

		storeType = Objects.toString(storeType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CTSContent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_R_S;
				finderArgs = new Object[] {companyId, repositoryId, storeType};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_R_S;
			finderArgs = new Object[] {
				companyId, repositoryId, storeType, start, end,
				orderByComparator
			};
		}

		List<CTSContent> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CTSContent>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTSContent ctsContent : list) {
					if ((companyId != ctsContent.getCompanyId()) ||
						(repositoryId != ctsContent.getRepositoryId()) ||
						!storeType.equals(ctsContent.getStoreType())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_CTSCONTENT_WHERE);

			sb.append(_FINDER_COLUMN_C_R_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_R_S_REPOSITORYID_2);

			boolean bindStoreType = false;

			if (storeType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_S_STORETYPE_3);
			}
			else {
				bindStoreType = true;

				sb.append(_FINDER_COLUMN_C_R_S_STORETYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTSContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(repositoryId);

				if (bindStoreType) {
					queryPos.add(storeType);
				}

				list = (List<CTSContent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_S_First(
			long companyId, long repositoryId, String storeType,
			OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_S_First(
			companyId, repositoryId, storeType, orderByComparator);

		if (ctsContent != null) {
			return ctsContent;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", repositoryId=");
		sb.append(repositoryId);

		sb.append(", storeType=");
		sb.append(storeType);

		sb.append("}");

		throw new NoSuchContentException(sb.toString());
	}

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_S_First(
		long companyId, long repositoryId, String storeType,
		OrderByComparator<CTSContent> orderByComparator) {

		List<CTSContent> list = findByC_R_S(
			companyId, repositoryId, storeType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_S_Last(
			long companyId, long repositoryId, String storeType,
			OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_S_Last(
			companyId, repositoryId, storeType, orderByComparator);

		if (ctsContent != null) {
			return ctsContent;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", repositoryId=");
		sb.append(repositoryId);

		sb.append(", storeType=");
		sb.append(storeType);

		sb.append("}");

		throw new NoSuchContentException(sb.toString());
	}

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_S_Last(
		long companyId, long repositoryId, String storeType,
		OrderByComparator<CTSContent> orderByComparator) {

		int count = countByC_R_S(companyId, repositoryId, storeType);

		if (count == 0) {
			return null;
		}

		List<CTSContent> list = findByC_R_S(
			companyId, repositoryId, storeType, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cts contents before and after the current cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param ctsContentId the primary key of the current cts content
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent[] findByC_R_S_PrevAndNext(
			long ctsContentId, long companyId, long repositoryId,
			String storeType, OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		storeType = Objects.toString(storeType, "");

		CTSContent ctsContent = findByPrimaryKey(ctsContentId);

		Session session = null;

		try {
			session = openSession();

			CTSContent[] array = new CTSContentImpl[3];

			array[0] = getByC_R_S_PrevAndNext(
				session, ctsContent, companyId, repositoryId, storeType,
				orderByComparator, true);

			array[1] = ctsContent;

			array[2] = getByC_R_S_PrevAndNext(
				session, ctsContent, companyId, repositoryId, storeType,
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

	protected CTSContent getByC_R_S_PrevAndNext(
		Session session, CTSContent ctsContent, long companyId,
		long repositoryId, String storeType,
		OrderByComparator<CTSContent> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_CTSCONTENT_WHERE);

		sb.append(_FINDER_COLUMN_C_R_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_R_S_REPOSITORYID_2);

		boolean bindStoreType = false;

		if (storeType.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_R_S_STORETYPE_3);
		}
		else {
			bindStoreType = true;

			sb.append(_FINDER_COLUMN_C_R_S_STORETYPE_2);
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
			sb.append(CTSContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(repositoryId);

		if (bindStoreType) {
			queryPos.add(storeType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctsContent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTSContent> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 */
	@Override
	public void removeByC_R_S(
		long companyId, long repositoryId, String storeType) {

		for (CTSContent ctsContent :
				findByC_R_S(
					companyId, repositoryId, storeType, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ctsContent);
		}
	}

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	@Override
	public int countByC_R_S(
		long companyId, long repositoryId, String storeType) {

		storeType = Objects.toString(storeType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CTSContent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_R_S;

			finderArgs = new Object[] {companyId, repositoryId, storeType};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CTSCONTENT_WHERE);

			sb.append(_FINDER_COLUMN_C_R_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_R_S_REPOSITORYID_2);

			boolean bindStoreType = false;

			if (storeType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_S_STORETYPE_3);
			}
			else {
				bindStoreType = true;

				sb.append(_FINDER_COLUMN_C_R_S_STORETYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(repositoryId);

				if (bindStoreType) {
					queryPos.add(storeType);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_R_S_COMPANYID_2 =
		"ctsContent.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_S_REPOSITORYID_2 =
		"ctsContent.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_S_STORETYPE_2 =
		"ctsContent.storeType = ?";

	private static final String _FINDER_COLUMN_C_R_S_STORETYPE_3 =
		"(ctsContent.storeType IS NULL OR ctsContent.storeType = '')";

	private FinderPath _finderPathWithPaginationFindByC_R_P_S;
	private FinderPath _finderPathWithoutPaginationFindByC_R_P_S;
	private FinderPath _finderPathCountByC_R_P_S;

	/**
	 * Returns all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType) {

		return findByC_R_P_S(
			companyId, repositoryId, path, storeType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end) {

		return findByC_R_P_S(
			companyId, repositoryId, path, storeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end, OrderByComparator<CTSContent> orderByComparator) {

		return findByC_R_P_S(
			companyId, repositoryId, path, storeType, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end, OrderByComparator<CTSContent> orderByComparator,
		boolean useFinderCache) {

		path = Objects.toString(path, "");
		storeType = Objects.toString(storeType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CTSContent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_R_P_S;
				finderArgs = new Object[] {
					companyId, repositoryId, path, storeType
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_R_P_S;
			finderArgs = new Object[] {
				companyId, repositoryId, path, storeType, start, end,
				orderByComparator
			};
		}

		List<CTSContent> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CTSContent>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTSContent ctsContent : list) {
					if ((companyId != ctsContent.getCompanyId()) ||
						(repositoryId != ctsContent.getRepositoryId()) ||
						!path.equals(ctsContent.getPath()) ||
						!storeType.equals(ctsContent.getStoreType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_CTSCONTENT_WHERE);

			sb.append(_FINDER_COLUMN_C_R_P_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_R_P_S_REPOSITORYID_2);

			boolean bindPath = false;

			if (path.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_P_S_PATH_3);
			}
			else {
				bindPath = true;

				sb.append(_FINDER_COLUMN_C_R_P_S_PATH_2);
			}

			boolean bindStoreType = false;

			if (storeType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_P_S_STORETYPE_3);
			}
			else {
				bindStoreType = true;

				sb.append(_FINDER_COLUMN_C_R_P_S_STORETYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTSContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(repositoryId);

				if (bindPath) {
					queryPos.add(path);
				}

				if (bindStoreType) {
					queryPos.add(storeType);
				}

				list = (List<CTSContent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_P_S_First(
			long companyId, long repositoryId, String path, String storeType,
			OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_P_S_First(
			companyId, repositoryId, path, storeType, orderByComparator);

		if (ctsContent != null) {
			return ctsContent;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", repositoryId=");
		sb.append(repositoryId);

		sb.append(", path=");
		sb.append(path);

		sb.append(", storeType=");
		sb.append(storeType);

		sb.append("}");

		throw new NoSuchContentException(sb.toString());
	}

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_P_S_First(
		long companyId, long repositoryId, String path, String storeType,
		OrderByComparator<CTSContent> orderByComparator) {

		List<CTSContent> list = findByC_R_P_S(
			companyId, repositoryId, path, storeType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_P_S_Last(
			long companyId, long repositoryId, String path, String storeType,
			OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_P_S_Last(
			companyId, repositoryId, path, storeType, orderByComparator);

		if (ctsContent != null) {
			return ctsContent;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", repositoryId=");
		sb.append(repositoryId);

		sb.append(", path=");
		sb.append(path);

		sb.append(", storeType=");
		sb.append(storeType);

		sb.append("}");

		throw new NoSuchContentException(sb.toString());
	}

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_P_S_Last(
		long companyId, long repositoryId, String path, String storeType,
		OrderByComparator<CTSContent> orderByComparator) {

		int count = countByC_R_P_S(companyId, repositoryId, path, storeType);

		if (count == 0) {
			return null;
		}

		List<CTSContent> list = findByC_R_P_S(
			companyId, repositoryId, path, storeType, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cts contents before and after the current cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param ctsContentId the primary key of the current cts content
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent[] findByC_R_P_S_PrevAndNext(
			long ctsContentId, long companyId, long repositoryId, String path,
			String storeType, OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		path = Objects.toString(path, "");
		storeType = Objects.toString(storeType, "");

		CTSContent ctsContent = findByPrimaryKey(ctsContentId);

		Session session = null;

		try {
			session = openSession();

			CTSContent[] array = new CTSContentImpl[3];

			array[0] = getByC_R_P_S_PrevAndNext(
				session, ctsContent, companyId, repositoryId, path, storeType,
				orderByComparator, true);

			array[1] = ctsContent;

			array[2] = getByC_R_P_S_PrevAndNext(
				session, ctsContent, companyId, repositoryId, path, storeType,
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

	protected CTSContent getByC_R_P_S_PrevAndNext(
		Session session, CTSContent ctsContent, long companyId,
		long repositoryId, String path, String storeType,
		OrderByComparator<CTSContent> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_CTSCONTENT_WHERE);

		sb.append(_FINDER_COLUMN_C_R_P_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_R_P_S_REPOSITORYID_2);

		boolean bindPath = false;

		if (path.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_R_P_S_PATH_3);
		}
		else {
			bindPath = true;

			sb.append(_FINDER_COLUMN_C_R_P_S_PATH_2);
		}

		boolean bindStoreType = false;

		if (storeType.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_R_P_S_STORETYPE_3);
		}
		else {
			bindStoreType = true;

			sb.append(_FINDER_COLUMN_C_R_P_S_STORETYPE_2);
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
			sb.append(CTSContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(repositoryId);

		if (bindPath) {
			queryPos.add(path);
		}

		if (bindStoreType) {
			queryPos.add(storeType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctsContent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTSContent> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 */
	@Override
	public void removeByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType) {

		for (CTSContent ctsContent :
				findByC_R_P_S(
					companyId, repositoryId, path, storeType, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ctsContent);
		}
	}

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	@Override
	public int countByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType) {

		path = Objects.toString(path, "");
		storeType = Objects.toString(storeType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CTSContent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_R_P_S;

			finderArgs = new Object[] {
				companyId, repositoryId, path, storeType
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_CTSCONTENT_WHERE);

			sb.append(_FINDER_COLUMN_C_R_P_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_R_P_S_REPOSITORYID_2);

			boolean bindPath = false;

			if (path.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_P_S_PATH_3);
			}
			else {
				bindPath = true;

				sb.append(_FINDER_COLUMN_C_R_P_S_PATH_2);
			}

			boolean bindStoreType = false;

			if (storeType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_P_S_STORETYPE_3);
			}
			else {
				bindStoreType = true;

				sb.append(_FINDER_COLUMN_C_R_P_S_STORETYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(repositoryId);

				if (bindPath) {
					queryPos.add(path);
				}

				if (bindStoreType) {
					queryPos.add(storeType);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_R_P_S_COMPANYID_2 =
		"ctsContent.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_P_S_REPOSITORYID_2 =
		"ctsContent.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_P_S_PATH_2 =
		"ctsContent.path = ? AND ";

	private static final String _FINDER_COLUMN_C_R_P_S_PATH_3 =
		"(ctsContent.path IS NULL OR ctsContent.path = '') AND ";

	private static final String _FINDER_COLUMN_C_R_P_S_STORETYPE_2 =
		"ctsContent.storeType = ?";

	private static final String _FINDER_COLUMN_C_R_P_S_STORETYPE_3 =
		"(ctsContent.storeType IS NULL OR ctsContent.storeType = '')";

	private FinderPath _finderPathWithPaginationFindByC_R_LikeP_S;
	private FinderPath _finderPathWithPaginationCountByC_R_LikeP_S;

	/**
	 * Returns all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType) {

		return findByC_R_LikeP_S(
			companyId, repositoryId, path, storeType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end) {

		return findByC_R_LikeP_S(
			companyId, repositoryId, path, storeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end, OrderByComparator<CTSContent> orderByComparator) {

		return findByC_R_LikeP_S(
			companyId, repositoryId, path, storeType, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end, OrderByComparator<CTSContent> orderByComparator,
		boolean useFinderCache) {

		path = Objects.toString(path, "");
		storeType = Objects.toString(storeType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CTSContent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_R_LikeP_S;
		finderArgs = new Object[] {
			companyId, repositoryId, path, storeType, start, end,
			orderByComparator
		};

		List<CTSContent> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CTSContent>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTSContent ctsContent : list) {
					if ((companyId != ctsContent.getCompanyId()) ||
						(repositoryId != ctsContent.getRepositoryId()) ||
						!StringUtil.wildcardMatches(
							ctsContent.getPath(), path, '_', '%', '\\', true) ||
						!storeType.equals(ctsContent.getStoreType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_CTSCONTENT_WHERE);

			sb.append(_FINDER_COLUMN_C_R_LIKEP_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_R_LIKEP_S_REPOSITORYID_2);

			boolean bindPath = false;

			if (path.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_LIKEP_S_PATH_3);
			}
			else {
				bindPath = true;

				sb.append(_FINDER_COLUMN_C_R_LIKEP_S_PATH_2);
			}

			boolean bindStoreType = false;

			if (storeType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_LIKEP_S_STORETYPE_3);
			}
			else {
				bindStoreType = true;

				sb.append(_FINDER_COLUMN_C_R_LIKEP_S_STORETYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTSContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(repositoryId);

				if (bindPath) {
					queryPos.add(path);
				}

				if (bindStoreType) {
					queryPos.add(storeType);
				}

				list = (List<CTSContent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_LikeP_S_First(
			long companyId, long repositoryId, String path, String storeType,
			OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_LikeP_S_First(
			companyId, repositoryId, path, storeType, orderByComparator);

		if (ctsContent != null) {
			return ctsContent;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", repositoryId=");
		sb.append(repositoryId);

		sb.append(", pathLIKE");
		sb.append(path);

		sb.append(", storeType=");
		sb.append(storeType);

		sb.append("}");

		throw new NoSuchContentException(sb.toString());
	}

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_LikeP_S_First(
		long companyId, long repositoryId, String path, String storeType,
		OrderByComparator<CTSContent> orderByComparator) {

		List<CTSContent> list = findByC_R_LikeP_S(
			companyId, repositoryId, path, storeType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_LikeP_S_Last(
			long companyId, long repositoryId, String path, String storeType,
			OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_LikeP_S_Last(
			companyId, repositoryId, path, storeType, orderByComparator);

		if (ctsContent != null) {
			return ctsContent;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", repositoryId=");
		sb.append(repositoryId);

		sb.append(", pathLIKE");
		sb.append(path);

		sb.append(", storeType=");
		sb.append(storeType);

		sb.append("}");

		throw new NoSuchContentException(sb.toString());
	}

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_LikeP_S_Last(
		long companyId, long repositoryId, String path, String storeType,
		OrderByComparator<CTSContent> orderByComparator) {

		int count = countByC_R_LikeP_S(
			companyId, repositoryId, path, storeType);

		if (count == 0) {
			return null;
		}

		List<CTSContent> list = findByC_R_LikeP_S(
			companyId, repositoryId, path, storeType, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cts contents before and after the current cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param ctsContentId the primary key of the current cts content
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent[] findByC_R_LikeP_S_PrevAndNext(
			long ctsContentId, long companyId, long repositoryId, String path,
			String storeType, OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		path = Objects.toString(path, "");
		storeType = Objects.toString(storeType, "");

		CTSContent ctsContent = findByPrimaryKey(ctsContentId);

		Session session = null;

		try {
			session = openSession();

			CTSContent[] array = new CTSContentImpl[3];

			array[0] = getByC_R_LikeP_S_PrevAndNext(
				session, ctsContent, companyId, repositoryId, path, storeType,
				orderByComparator, true);

			array[1] = ctsContent;

			array[2] = getByC_R_LikeP_S_PrevAndNext(
				session, ctsContent, companyId, repositoryId, path, storeType,
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

	protected CTSContent getByC_R_LikeP_S_PrevAndNext(
		Session session, CTSContent ctsContent, long companyId,
		long repositoryId, String path, String storeType,
		OrderByComparator<CTSContent> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_CTSCONTENT_WHERE);

		sb.append(_FINDER_COLUMN_C_R_LIKEP_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_R_LIKEP_S_REPOSITORYID_2);

		boolean bindPath = false;

		if (path.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_R_LIKEP_S_PATH_3);
		}
		else {
			bindPath = true;

			sb.append(_FINDER_COLUMN_C_R_LIKEP_S_PATH_2);
		}

		boolean bindStoreType = false;

		if (storeType.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_R_LIKEP_S_STORETYPE_3);
		}
		else {
			bindStoreType = true;

			sb.append(_FINDER_COLUMN_C_R_LIKEP_S_STORETYPE_2);
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
			sb.append(CTSContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(repositoryId);

		if (bindPath) {
			queryPos.add(path);
		}

		if (bindStoreType) {
			queryPos.add(storeType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctsContent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTSContent> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 */
	@Override
	public void removeByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType) {

		for (CTSContent ctsContent :
				findByC_R_LikeP_S(
					companyId, repositoryId, path, storeType, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ctsContent);
		}
	}

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	@Override
	public int countByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType) {

		path = Objects.toString(path, "");
		storeType = Objects.toString(storeType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CTSContent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_R_LikeP_S;

			finderArgs = new Object[] {
				companyId, repositoryId, path, storeType
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_CTSCONTENT_WHERE);

			sb.append(_FINDER_COLUMN_C_R_LIKEP_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_R_LIKEP_S_REPOSITORYID_2);

			boolean bindPath = false;

			if (path.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_LIKEP_S_PATH_3);
			}
			else {
				bindPath = true;

				sb.append(_FINDER_COLUMN_C_R_LIKEP_S_PATH_2);
			}

			boolean bindStoreType = false;

			if (storeType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_LIKEP_S_STORETYPE_3);
			}
			else {
				bindStoreType = true;

				sb.append(_FINDER_COLUMN_C_R_LIKEP_S_STORETYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(repositoryId);

				if (bindPath) {
					queryPos.add(path);
				}

				if (bindStoreType) {
					queryPos.add(storeType);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_R_LIKEP_S_COMPANYID_2 =
		"ctsContent.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_LIKEP_S_REPOSITORYID_2 =
		"ctsContent.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_LIKEP_S_PATH_2 =
		"ctsContent.path LIKE ? AND ";

	private static final String _FINDER_COLUMN_C_R_LIKEP_S_PATH_3 =
		"(ctsContent.path IS NULL OR ctsContent.path LIKE '') AND ";

	private static final String _FINDER_COLUMN_C_R_LIKEP_S_STORETYPE_2 =
		"ctsContent.storeType = ?";

	private static final String _FINDER_COLUMN_C_R_LIKEP_S_STORETYPE_3 =
		"(ctsContent.storeType IS NULL OR ctsContent.storeType = '')";

	private FinderPath _finderPathFetchByC_R_P_V_S;
	private FinderPath _finderPathCountByC_R_P_V_S;

	/**
	 * Returns the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; or throws a <code>NoSuchContentException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_P_V_S(
			long companyId, long repositoryId, String path, String version,
			String storeType)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_P_V_S(
			companyId, repositoryId, path, version, storeType);

		if (ctsContent == null) {
			StringBundler sb = new StringBundler(12);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", repositoryId=");
			sb.append(repositoryId);

			sb.append(", path=");
			sb.append(path);

			sb.append(", version=");
			sb.append(version);

			sb.append(", storeType=");
			sb.append(storeType);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchContentException(sb.toString());
		}

		return ctsContent;
	}

	/**
	 * Returns the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_P_V_S(
		long companyId, long repositoryId, String path, String version,
		String storeType) {

		return fetchByC_R_P_V_S(
			companyId, repositoryId, path, version, storeType, true);
	}

	/**
	 * Returns the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_P_V_S(
		long companyId, long repositoryId, String path, String version,
		String storeType, boolean useFinderCache) {

		path = Objects.toString(path, "");
		version = Objects.toString(version, "");
		storeType = Objects.toString(storeType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CTSContent.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				companyId, repositoryId, path, version, storeType
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByC_R_P_V_S, finderArgs, this);
		}

		if (result instanceof CTSContent) {
			CTSContent ctsContent = (CTSContent)result;

			if ((companyId != ctsContent.getCompanyId()) ||
				(repositoryId != ctsContent.getRepositoryId()) ||
				!Objects.equals(path, ctsContent.getPath()) ||
				!Objects.equals(version, ctsContent.getVersion()) ||
				!Objects.equals(storeType, ctsContent.getStoreType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_SELECT_CTSCONTENT_WHERE);

			sb.append(_FINDER_COLUMN_C_R_P_V_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_R_P_V_S_REPOSITORYID_2);

			boolean bindPath = false;

			if (path.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_P_V_S_PATH_3);
			}
			else {
				bindPath = true;

				sb.append(_FINDER_COLUMN_C_R_P_V_S_PATH_2);
			}

			boolean bindVersion = false;

			if (version.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_P_V_S_VERSION_3);
			}
			else {
				bindVersion = true;

				sb.append(_FINDER_COLUMN_C_R_P_V_S_VERSION_2);
			}

			boolean bindStoreType = false;

			if (storeType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_P_V_S_STORETYPE_3);
			}
			else {
				bindStoreType = true;

				sb.append(_FINDER_COLUMN_C_R_P_V_S_STORETYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(repositoryId);

				if (bindPath) {
					queryPos.add(path);
				}

				if (bindVersion) {
					queryPos.add(version);
				}

				if (bindStoreType) {
					queryPos.add(storeType);
				}

				List<CTSContent> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByC_R_P_V_S, finderArgs, list);
					}
				}
				else {
					CTSContent ctsContent = list.get(0);

					result = ctsContent;

					cacheResult(ctsContent);
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
			return (CTSContent)result;
		}
	}

	/**
	 * Removes the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the cts content that was removed
	 */
	@Override
	public CTSContent removeByC_R_P_V_S(
			long companyId, long repositoryId, String path, String version,
			String storeType)
		throws NoSuchContentException {

		CTSContent ctsContent = findByC_R_P_V_S(
			companyId, repositoryId, path, version, storeType);

		return remove(ctsContent);
	}

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	@Override
	public int countByC_R_P_V_S(
		long companyId, long repositoryId, String path, String version,
		String storeType) {

		path = Objects.toString(path, "");
		version = Objects.toString(version, "");
		storeType = Objects.toString(storeType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CTSContent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_R_P_V_S;

			finderArgs = new Object[] {
				companyId, repositoryId, path, version, storeType
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_CTSCONTENT_WHERE);

			sb.append(_FINDER_COLUMN_C_R_P_V_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_R_P_V_S_REPOSITORYID_2);

			boolean bindPath = false;

			if (path.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_P_V_S_PATH_3);
			}
			else {
				bindPath = true;

				sb.append(_FINDER_COLUMN_C_R_P_V_S_PATH_2);
			}

			boolean bindVersion = false;

			if (version.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_P_V_S_VERSION_3);
			}
			else {
				bindVersion = true;

				sb.append(_FINDER_COLUMN_C_R_P_V_S_VERSION_2);
			}

			boolean bindStoreType = false;

			if (storeType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_R_P_V_S_STORETYPE_3);
			}
			else {
				bindStoreType = true;

				sb.append(_FINDER_COLUMN_C_R_P_V_S_STORETYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(repositoryId);

				if (bindPath) {
					queryPos.add(path);
				}

				if (bindVersion) {
					queryPos.add(version);
				}

				if (bindStoreType) {
					queryPos.add(storeType);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_R_P_V_S_COMPANYID_2 =
		"ctsContent.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_P_V_S_REPOSITORYID_2 =
		"ctsContent.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_P_V_S_PATH_2 =
		"ctsContent.path = ? AND ";

	private static final String _FINDER_COLUMN_C_R_P_V_S_PATH_3 =
		"(ctsContent.path IS NULL OR ctsContent.path = '') AND ";

	private static final String _FINDER_COLUMN_C_R_P_V_S_VERSION_2 =
		"ctsContent.version = ? AND ";

	private static final String _FINDER_COLUMN_C_R_P_V_S_VERSION_3 =
		"(ctsContent.version IS NULL OR ctsContent.version = '') AND ";

	private static final String _FINDER_COLUMN_C_R_P_V_S_STORETYPE_2 =
		"ctsContent.storeType = ?";

	private static final String _FINDER_COLUMN_C_R_P_V_S_STORETYPE_3 =
		"(ctsContent.storeType IS NULL OR ctsContent.storeType = '')";

	public CTSContentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("path", "path_");
		dbColumnNames.put("data", "data_");
		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CTSContent.class);

		setModelImplClass(CTSContentImpl.class);
		setModelPKClass(long.class);

		setTable(CTSContentTable.INSTANCE);
	}

	/**
	 * Caches the cts content in the entity cache if it is enabled.
	 *
	 * @param ctsContent the cts content
	 */
	@Override
	public void cacheResult(CTSContent ctsContent) {
		if (ctsContent.getCtCollectionId() != 0) {
			ctsContent.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			CTSContentImpl.class, ctsContent.getPrimaryKey(), ctsContent);

		finderCache.putResult(
			_finderPathFetchByC_R_P_V_S,
			new Object[] {
				ctsContent.getCompanyId(), ctsContent.getRepositoryId(),
				ctsContent.getPath(), ctsContent.getVersion(),
				ctsContent.getStoreType()
			},
			ctsContent);

		ctsContent.resetOriginalValues();
	}

	/**
	 * Caches the cts contents in the entity cache if it is enabled.
	 *
	 * @param ctsContents the cts contents
	 */
	@Override
	public void cacheResult(List<CTSContent> ctsContents) {
		for (CTSContent ctsContent : ctsContents) {
			if (ctsContent.getCtCollectionId() != 0) {
				ctsContent.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					CTSContentImpl.class, ctsContent.getPrimaryKey()) == null) {

				cacheResult(ctsContent);
			}
			else {
				ctsContent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cts contents.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTSContentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cts content.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTSContent ctsContent) {
		entityCache.removeResult(
			CTSContentImpl.class, ctsContent.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CTSContentModelImpl)ctsContent, true);
	}

	@Override
	public void clearCache(List<CTSContent> ctsContents) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CTSContent ctsContent : ctsContents) {
			entityCache.removeResult(
				CTSContentImpl.class, ctsContent.getPrimaryKey());

			clearUniqueFindersCache((CTSContentModelImpl)ctsContent, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CTSContentImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CTSContentModelImpl ctsContentModelImpl) {

		Object[] args = new Object[] {
			ctsContentModelImpl.getCompanyId(),
			ctsContentModelImpl.getRepositoryId(),
			ctsContentModelImpl.getPath(), ctsContentModelImpl.getVersion(),
			ctsContentModelImpl.getStoreType()
		};

		finderCache.putResult(
			_finderPathCountByC_R_P_V_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_R_P_V_S, args, ctsContentModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CTSContentModelImpl ctsContentModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ctsContentModelImpl.getCompanyId(),
				ctsContentModelImpl.getRepositoryId(),
				ctsContentModelImpl.getPath(), ctsContentModelImpl.getVersion(),
				ctsContentModelImpl.getStoreType()
			};

			finderCache.removeResult(_finderPathCountByC_R_P_V_S, args);
			finderCache.removeResult(_finderPathFetchByC_R_P_V_S, args);
		}

		if ((ctsContentModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_R_P_V_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ctsContentModelImpl.getOriginalCompanyId(),
				ctsContentModelImpl.getOriginalRepositoryId(),
				ctsContentModelImpl.getOriginalPath(),
				ctsContentModelImpl.getOriginalVersion(),
				ctsContentModelImpl.getOriginalStoreType()
			};

			finderCache.removeResult(_finderPathCountByC_R_P_V_S, args);
			finderCache.removeResult(_finderPathFetchByC_R_P_V_S, args);
		}
	}

	/**
	 * Creates a new cts content with the primary key. Does not add the cts content to the database.
	 *
	 * @param ctsContentId the primary key for the new cts content
	 * @return the new cts content
	 */
	@Override
	public CTSContent create(long ctsContentId) {
		CTSContent ctsContent = new CTSContentImpl();

		ctsContent.setNew(true);
		ctsContent.setPrimaryKey(ctsContentId);

		ctsContent.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctsContent;
	}

	/**
	 * Removes the cts content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content that was removed
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent remove(long ctsContentId) throws NoSuchContentException {
		return remove((Serializable)ctsContentId);
	}

	/**
	 * Removes the cts content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cts content
	 * @return the cts content that was removed
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent remove(Serializable primaryKey)
		throws NoSuchContentException {

		Session session = null;

		try {
			session = openSession();

			CTSContent ctsContent = (CTSContent)session.get(
				CTSContentImpl.class, primaryKey);

			if (ctsContent == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchContentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctsContent);
		}
		catch (NoSuchContentException noSuchEntityException) {
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
	protected CTSContent removeImpl(CTSContent ctsContent) {
		if (!ctPersistenceHelper.isRemove(ctsContent)) {
			return ctsContent;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctsContent)) {
				ctsContent = (CTSContent)session.get(
					CTSContentImpl.class, ctsContent.getPrimaryKeyObj());
			}

			if (ctsContent != null) {
				session.delete(ctsContent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctsContent != null) {
			clearCache(ctsContent);
		}

		return ctsContent;
	}

	@Override
	public CTSContent updateImpl(CTSContent ctsContent) {
		boolean isNew = ctsContent.isNew();

		if (!(ctsContent instanceof CTSContentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctsContent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctsContent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctsContent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTSContent implementation " +
					ctsContent.getClass());
		}

		CTSContentModelImpl ctsContentModelImpl =
			(CTSContentModelImpl)ctsContent;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ctsContent)) {
				if (!isNew) {
					session.evict(
						CTSContentImpl.class, ctsContent.getPrimaryKeyObj());
				}

				session.save(ctsContent);

				ctsContent.setNew(false);
			}
			else {
				session.evict(
					CTSContentImpl.class, ctsContent.getPrimaryKeyObj());

				session.saveOrUpdate(ctsContent);
			}

			session.flush();
			session.clear();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctsContent.getCtCollectionId() != 0) {
			ctsContent.resetOriginalValues();

			return ctsContent;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				ctsContentModelImpl.getCompanyId(),
				ctsContentModelImpl.getRepositoryId(),
				ctsContentModelImpl.getStoreType()
			};

			finderCache.removeResult(_finderPathCountByC_R_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_R_S, args);

			args = new Object[] {
				ctsContentModelImpl.getCompanyId(),
				ctsContentModelImpl.getRepositoryId(),
				ctsContentModelImpl.getPath(),
				ctsContentModelImpl.getStoreType()
			};

			finderCache.removeResult(_finderPathCountByC_R_P_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_R_P_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ctsContentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_R_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ctsContentModelImpl.getOriginalCompanyId(),
					ctsContentModelImpl.getOriginalRepositoryId(),
					ctsContentModelImpl.getOriginalStoreType()
				};

				finderCache.removeResult(_finderPathCountByC_R_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_R_S, args);

				args = new Object[] {
					ctsContentModelImpl.getCompanyId(),
					ctsContentModelImpl.getRepositoryId(),
					ctsContentModelImpl.getStoreType()
				};

				finderCache.removeResult(_finderPathCountByC_R_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_R_S, args);
			}

			if ((ctsContentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_R_P_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctsContentModelImpl.getOriginalCompanyId(),
					ctsContentModelImpl.getOriginalRepositoryId(),
					ctsContentModelImpl.getOriginalPath(),
					ctsContentModelImpl.getOriginalStoreType()
				};

				finderCache.removeResult(_finderPathCountByC_R_P_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_R_P_S, args);

				args = new Object[] {
					ctsContentModelImpl.getCompanyId(),
					ctsContentModelImpl.getRepositoryId(),
					ctsContentModelImpl.getPath(),
					ctsContentModelImpl.getStoreType()
				};

				finderCache.removeResult(_finderPathCountByC_R_P_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_R_P_S, args);
			}
		}

		entityCache.putResult(
			CTSContentImpl.class, ctsContent.getPrimaryKey(), ctsContent,
			false);

		clearUniqueFindersCache(ctsContentModelImpl, false);
		cacheUniqueFindersCache(ctsContentModelImpl);

		ctsContent.resetOriginalValues();

		return ctsContent;
	}

	/**
	 * Returns the cts content with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cts content
	 * @return the cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByPrimaryKey(primaryKey);

		if (ctsContent == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchContentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctsContent;
	}

	/**
	 * Returns the cts content with the primary key or throws a <code>NoSuchContentException</code> if it could not be found.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent findByPrimaryKey(long ctsContentId)
		throws NoSuchContentException {

		return findByPrimaryKey((Serializable)ctsContentId);
	}

	/**
	 * Returns the cts content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cts content
	 * @return the cts content, or <code>null</code> if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(CTSContent.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		CTSContent ctsContent = null;

		Session session = null;

		try {
			session = openSession();

			ctsContent = (CTSContent)session.get(
				CTSContentImpl.class, primaryKey);

			if (ctsContent != null) {
				cacheResult(ctsContent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ctsContent;
	}

	/**
	 * Returns the cts content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content, or <code>null</code> if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent fetchByPrimaryKey(long ctsContentId) {
		return fetchByPrimaryKey((Serializable)ctsContentId);
	}

	@Override
	public Map<Serializable, CTSContent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(CTSContent.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CTSContent> map =
			new HashMap<Serializable, CTSContent>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CTSContent ctsContent = fetchByPrimaryKey(primaryKey);

			if (ctsContent != null) {
				map.put(primaryKey, ctsContent);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (CTSContent ctsContent : (List<CTSContent>)query.list()) {
				map.put(ctsContent.getPrimaryKeyObj(), ctsContent);

				cacheResult(ctsContent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the cts contents.
	 *
	 * @return the cts contents
	 */
	@Override
	public List<CTSContent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cts contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of cts contents
	 */
	@Override
	public List<CTSContent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cts contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cts contents
	 */
	@Override
	public List<CTSContent> findAll(
		int start, int end, OrderByComparator<CTSContent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cts contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cts contents
	 */
	@Override
	public List<CTSContent> findAll(
		int start, int end, OrderByComparator<CTSContent> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CTSContent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CTSContent> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CTSContent>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CTSCONTENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CTSCONTENT;

				sql = sql.concat(CTSContentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CTSContent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Removes all the cts contents from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTSContent ctsContent : findAll()) {
			remove(ctsContent);
		}
	}

	/**
	 * Returns the number of cts contents.
	 *
	 * @return the number of cts contents
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CTSContent.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CTSCONTENT);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return "ctsContentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTSCONTENT;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return CTSContentModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CTSContent";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("repositoryId");
		ctStrictColumnNames.add("path_");
		ctStrictColumnNames.add("version");
		ctStrictColumnNames.add("data_");
		ctStrictColumnNames.add("size_");
		ctStrictColumnNames.add("storeType");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("ctsContentId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {
				"companyId", "repositoryId", "path_", "version", "storeType"
			});
	}

	/**
	 * Initializes the cts content persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CTSContentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CTSContentImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByC_R_S = new FinderPath(
			CTSContentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_R_S = new FinderPath(
			CTSContentImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			CTSContentModelImpl.COMPANYID_COLUMN_BITMASK |
			CTSContentModelImpl.REPOSITORYID_COLUMN_BITMASK |
			CTSContentModelImpl.STORETYPE_COLUMN_BITMASK |
			CTSContentModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByC_R_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByC_R_P_S = new FinderPath(
			CTSContentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_R_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_R_P_S = new FinderPath(
			CTSContentImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_R_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			CTSContentModelImpl.COMPANYID_COLUMN_BITMASK |
			CTSContentModelImpl.REPOSITORYID_COLUMN_BITMASK |
			CTSContentModelImpl.PATH_COLUMN_BITMASK |
			CTSContentModelImpl.STORETYPE_COLUMN_BITMASK |
			CTSContentModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByC_R_P_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_R_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});

		_finderPathWithPaginationFindByC_R_LikeP_S = new FinderPath(
			CTSContentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_R_LikeP_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByC_R_LikeP_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByC_R_LikeP_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});

		_finderPathFetchByC_R_P_V_S = new FinderPath(
			CTSContentImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_R_P_V_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				String.class.getName()
			},
			CTSContentModelImpl.COMPANYID_COLUMN_BITMASK |
			CTSContentModelImpl.REPOSITORYID_COLUMN_BITMASK |
			CTSContentModelImpl.PATH_COLUMN_BITMASK |
			CTSContentModelImpl.VERSION_COLUMN_BITMASK |
			CTSContentModelImpl.STORETYPE_COLUMN_BITMASK);

		_finderPathCountByC_R_P_V_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_R_P_V_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTSContentImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CTSCONTENT =
		"SELECT ctsContent FROM CTSContent ctsContent";

	private static final String _SQL_SELECT_CTSCONTENT_WHERE =
		"SELECT ctsContent FROM CTSContent ctsContent WHERE ";

	private static final String _SQL_COUNT_CTSCONTENT =
		"SELECT COUNT(ctsContent) FROM CTSContent ctsContent";

	private static final String _SQL_COUNT_CTSCONTENT_WHERE =
		"SELECT COUNT(ctsContent) FROM CTSContent ctsContent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctsContent.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTSContent exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTSContent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTSContentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"path", "data", "size"});

	static {
		try {
			Class.forName(CTSPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}