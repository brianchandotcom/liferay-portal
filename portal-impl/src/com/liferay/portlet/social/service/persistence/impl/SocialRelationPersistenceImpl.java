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

package com.liferay.portlet.social.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.social.model.impl.SocialRelationImpl;
import com.liferay.portlet.social.model.impl.SocialRelationModelImpl;
import com.liferay.social.kernel.exception.NoSuchRelationException;
import com.liferay.social.kernel.model.SocialRelation;
import com.liferay.social.kernel.model.SocialRelationTable;
import com.liferay.social.kernel.service.persistence.SocialRelationPersistence;

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

/**
 * The persistence implementation for the social relation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialRelationPersistenceImpl
	extends BasePersistenceImpl<SocialRelation>
	implements SocialRelationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SocialRelationUtil</code> to access the social relation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SocialRelationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the social relations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching social relations
	 */
	@Override
	public List<SocialRelation> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialRelation socialRelation : list) {
					if (!uuid.equals(socialRelation.getUuid())) {
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

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first social relation in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByUuid_First(
			String uuid, OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByUuid_First(
			uuid, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByUuid_First(
		String uuid, OrderByComparator<SocialRelation> orderByComparator) {

		List<SocialRelation> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByUuid_Last(
			String uuid, OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByUuid_Last(
			uuid, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByUuid_Last(
		String uuid, OrderByComparator<SocialRelation> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SocialRelation> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where uuid = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation[] findByUuid_PrevAndNext(
			long relationId, String uuid,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		uuid = Objects.toString(uuid, "");

		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, socialRelation, uuid, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByUuid_PrevAndNext(
				session, socialRelation, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByUuid_PrevAndNext(
		Session session, SocialRelation socialRelation, String uuid,
		OrderByComparator<SocialRelation> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
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
			sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialRelation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialRelation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social relations where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SocialRelation socialRelation :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching social relations
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"socialRelation.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(socialRelation.uuid IS NULL OR socialRelation.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the social relations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching social relations
	 */
	@Override
	public List<SocialRelation> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialRelation socialRelation : list) {
					if (!uuid.equals(socialRelation.getUuid()) ||
						(companyId != socialRelation.getCompanyId())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SocialRelation> orderByComparator) {

		List<SocialRelation> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SocialRelation> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SocialRelation> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation[] findByUuid_C_PrevAndNext(
			long relationId, String uuid, long companyId,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		uuid = Objects.toString(uuid, "");

		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, socialRelation, uuid, companyId, orderByComparator,
				true);

			array[1] = socialRelation;

			array[2] = getByUuid_C_PrevAndNext(
				session, socialRelation, uuid, companyId, orderByComparator,
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

	protected SocialRelation getByUuid_C_PrevAndNext(
		Session session, SocialRelation socialRelation, String uuid,
		long companyId, OrderByComparator<SocialRelation> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialRelation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialRelation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social relations where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SocialRelation socialRelation :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching social relations
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"socialRelation.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(socialRelation.uuid IS NULL OR socialRelation.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"socialRelation.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the social relations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching social relations
	 */
	@Override
	public List<SocialRelation> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialRelation socialRelation : list) {
					if (companyId != socialRelation.getCompanyId()) {
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

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first social relation in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByCompanyId_First(
			long companyId, OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByCompanyId_First(
		long companyId, OrderByComparator<SocialRelation> orderByComparator) {

		List<SocialRelation> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByCompanyId_Last(
			long companyId, OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByCompanyId_Last(
		long companyId, OrderByComparator<SocialRelation> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SocialRelation> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where companyId = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation[] findByCompanyId_PrevAndNext(
			long relationId, long companyId,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, socialRelation, companyId, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByCompanyId_PrevAndNext(
				session, socialRelation, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByCompanyId_PrevAndNext(
		Session session, SocialRelation socialRelation, long companyId,
		OrderByComparator<SocialRelation> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

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
			sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
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
						socialRelation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialRelation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social relations where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SocialRelation socialRelation :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching social relations
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"socialRelation.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId1;
	private FinderPath _finderPathWithoutPaginationFindByUserId1;
	private FinderPath _finderPathCountByUserId1;

	/**
	 * Returns all the social relations where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @return the matching social relations
	 */
	@Override
	public List<SocialRelation> findByUserId1(long userId1) {
		return findByUserId1(
			userId1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where userId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUserId1(
		long userId1, int start, int end) {

		return findByUserId1(userId1, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where userId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUserId1(
		long userId1, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findByUserId1(userId1, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations where userId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUserId1(
		long userId1, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUserId1;
				finderArgs = new Object[] {userId1};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUserId1;
			finderArgs = new Object[] {userId1, start, end, orderByComparator};
		}

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialRelation socialRelation : list) {
					if (userId1 != socialRelation.getUserId1()) {
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

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_USERID1_USERID1_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId1);

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first social relation in the ordered set where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByUserId1_First(
			long userId1, OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByUserId1_First(
			userId1, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId1=");
		sb.append(userId1);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByUserId1_First(
		long userId1, OrderByComparator<SocialRelation> orderByComparator) {

		List<SocialRelation> list = findByUserId1(
			userId1, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByUserId1_Last(
			long userId1, OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByUserId1_Last(
			userId1, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId1=");
		sb.append(userId1);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByUserId1_Last(
		long userId1, OrderByComparator<SocialRelation> orderByComparator) {

		int count = countByUserId1(userId1);

		if (count == 0) {
			return null;
		}

		List<SocialRelation> list = findByUserId1(
			userId1, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param userId1 the user id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation[] findByUserId1_PrevAndNext(
			long relationId, long userId1,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUserId1_PrevAndNext(
				session, socialRelation, userId1, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByUserId1_PrevAndNext(
				session, socialRelation, userId1, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByUserId1_PrevAndNext(
		Session session, SocialRelation socialRelation, long userId1,
		OrderByComparator<SocialRelation> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		sb.append(_FINDER_COLUMN_USERID1_USERID1_2);

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
			sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId1);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialRelation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialRelation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social relations where userId1 = &#63; from the database.
	 *
	 * @param userId1 the user id1
	 */
	@Override
	public void removeByUserId1(long userId1) {
		for (SocialRelation socialRelation :
				findByUserId1(
					userId1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where userId1 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @return the number of matching social relations
	 */
	@Override
	public int countByUserId1(long userId1) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUserId1;

			finderArgs = new Object[] {userId1};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_USERID1_USERID1_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId1);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_USERID1_USERID1_2 =
		"socialRelation.userId1 = ?";

	private FinderPath _finderPathWithPaginationFindByUserId2;
	private FinderPath _finderPathWithoutPaginationFindByUserId2;
	private FinderPath _finderPathCountByUserId2;

	/**
	 * Returns all the social relations where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @return the matching social relations
	 */
	@Override
	public List<SocialRelation> findByUserId2(long userId2) {
		return findByUserId2(
			userId2, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where userId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId2 the user id2
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUserId2(
		long userId2, int start, int end) {

		return findByUserId2(userId2, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where userId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId2 the user id2
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUserId2(
		long userId2, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findByUserId2(userId2, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations where userId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId2 the user id2
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByUserId2(
		long userId2, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUserId2;
				finderArgs = new Object[] {userId2};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUserId2;
			finderArgs = new Object[] {userId2, start, end, orderByComparator};
		}

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialRelation socialRelation : list) {
					if (userId2 != socialRelation.getUserId2()) {
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

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_USERID2_USERID2_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId2);

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first social relation in the ordered set where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByUserId2_First(
			long userId2, OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByUserId2_First(
			userId2, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId2=");
		sb.append(userId2);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByUserId2_First(
		long userId2, OrderByComparator<SocialRelation> orderByComparator) {

		List<SocialRelation> list = findByUserId2(
			userId2, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByUserId2_Last(
			long userId2, OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByUserId2_Last(
			userId2, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId2=");
		sb.append(userId2);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByUserId2_Last(
		long userId2, OrderByComparator<SocialRelation> orderByComparator) {

		int count = countByUserId2(userId2);

		if (count == 0) {
			return null;
		}

		List<SocialRelation> list = findByUserId2(
			userId2, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where userId2 = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation[] findByUserId2_PrevAndNext(
			long relationId, long userId2,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByUserId2_PrevAndNext(
				session, socialRelation, userId2, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByUserId2_PrevAndNext(
				session, socialRelation, userId2, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByUserId2_PrevAndNext(
		Session session, SocialRelation socialRelation, long userId2,
		OrderByComparator<SocialRelation> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		sb.append(_FINDER_COLUMN_USERID2_USERID2_2);

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
			sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId2);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialRelation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialRelation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social relations where userId2 = &#63; from the database.
	 *
	 * @param userId2 the user id2
	 */
	@Override
	public void removeByUserId2(long userId2) {
		for (SocialRelation socialRelation :
				findByUserId2(
					userId2, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where userId2 = &#63;.
	 *
	 * @param userId2 the user id2
	 * @return the number of matching social relations
	 */
	@Override
	public int countByUserId2(long userId2) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUserId2;

			finderArgs = new Object[] {userId2};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_USERID2_USERID2_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId2);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_USERID2_USERID2_2 =
		"socialRelation.userId2 = ?";

	private FinderPath _finderPathWithPaginationFindByType;
	private FinderPath _finderPathWithoutPaginationFindByType;
	private FinderPath _finderPathCountByType;

	/**
	 * Returns all the social relations where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching social relations
	 */
	@Override
	public List<SocialRelation> findByType(int type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByType(int type, int start, int end) {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByType(
		int type, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByType(
		int type, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByType;
				finderArgs = new Object[] {type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByType;
			finderArgs = new Object[] {type, start, end, orderByComparator};
		}

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialRelation socialRelation : list) {
					if (type != socialRelation.getType()) {
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

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_TYPE_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first social relation in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByType_First(
			int type, OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByType_First(
			type, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByType_First(
		int type, OrderByComparator<SocialRelation> orderByComparator) {

		List<SocialRelation> list = findByType(type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByType_Last(
			int type, OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByType_Last(
			type, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByType_Last(
		int type, OrderByComparator<SocialRelation> orderByComparator) {

		int count = countByType(type);

		if (count == 0) {
			return null;
		}

		List<SocialRelation> list = findByType(
			type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where type = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation[] findByType_PrevAndNext(
			long relationId, int type,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByType_PrevAndNext(
				session, socialRelation, type, orderByComparator, true);

			array[1] = socialRelation;

			array[2] = getByType_PrevAndNext(
				session, socialRelation, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialRelation getByType_PrevAndNext(
		Session session, SocialRelation socialRelation, int type,
		OrderByComparator<SocialRelation> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		sb.append(_FINDER_COLUMN_TYPE_TYPE_2);

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
			sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialRelation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialRelation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social relations where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(int type) {
		for (SocialRelation socialRelation :
				findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching social relations
	 */
	@Override
	public int countByType(int type) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByType;

			finderArgs = new Object[] {type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_TYPE_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_TYPE_TYPE_2 =
		"socialRelation.type = ?";

	private FinderPath _finderPathWithPaginationFindByC_T;
	private FinderPath _finderPathWithoutPaginationFindByC_T;
	private FinderPath _finderPathCountByC_T;

	/**
	 * Returns all the social relations where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching social relations
	 */
	@Override
	public List<SocialRelation> findByC_T(long companyId, int type) {
		return findByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByC_T(
		long companyId, int type, int start, int end) {

		return findByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findByC_T(companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_T;
				finderArgs = new Object[] {companyId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_T;
			finderArgs = new Object[] {
				companyId, type, start, end, orderByComparator
			};
		}

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialRelation socialRelation : list) {
					if ((companyId != socialRelation.getCompanyId()) ||
						(type != socialRelation.getType())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first social relation in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByC_T_First(
			long companyId, int type,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByC_T_First(
			companyId, type, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByC_T_First(
		long companyId, int type,
		OrderByComparator<SocialRelation> orderByComparator) {

		List<SocialRelation> list = findByC_T(
			companyId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByC_T_Last(
			long companyId, int type,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByC_T_Last(
			companyId, type, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByC_T_Last(
		long companyId, int type,
		OrderByComparator<SocialRelation> orderByComparator) {

		int count = countByC_T(companyId, type);

		if (count == 0) {
			return null;
		}

		List<SocialRelation> list = findByC_T(
			companyId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation[] findByC_T_PrevAndNext(
			long relationId, long companyId, int type,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByC_T_PrevAndNext(
				session, socialRelation, companyId, type, orderByComparator,
				true);

			array[1] = socialRelation;

			array[2] = getByC_T_PrevAndNext(
				session, socialRelation, companyId, type, orderByComparator,
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

	protected SocialRelation getByC_T_PrevAndNext(
		Session session, SocialRelation socialRelation, long companyId,
		int type, OrderByComparator<SocialRelation> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_T_TYPE_2);

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
			sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialRelation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialRelation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social relations where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByC_T(long companyId, int type) {
		for (SocialRelation socialRelation :
				findByC_T(
					companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching social relations
	 */
	@Override
	public int countByC_T(long companyId, int type) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_T;

			finderArgs = new Object[] {companyId, type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_T_COMPANYID_2 =
		"socialRelation.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_TYPE_2 =
		"socialRelation.type = ?";

	private FinderPath _finderPathWithPaginationFindByU1_U2;
	private FinderPath _finderPathWithoutPaginationFindByU1_U2;
	private FinderPath _finderPathCountByU1_U2;

	/**
	 * Returns all the social relations where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @return the matching social relations
	 */
	@Override
	public List<SocialRelation> findByU1_U2(long userId1, long userId2) {
		return findByU1_U2(
			userId1, userId2, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where userId1 = &#63; and userId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByU1_U2(
		long userId1, long userId2, int start, int end) {

		return findByU1_U2(userId1, userId2, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where userId1 = &#63; and userId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByU1_U2(
		long userId1, long userId2, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findByU1_U2(
			userId1, userId2, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations where userId1 = &#63; and userId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByU1_U2(
		long userId1, long userId2, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByU1_U2;
				finderArgs = new Object[] {userId1, userId2};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByU1_U2;
			finderArgs = new Object[] {
				userId1, userId2, start, end, orderByComparator
			};
		}

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialRelation socialRelation : list) {
					if ((userId1 != socialRelation.getUserId1()) ||
						(userId2 != socialRelation.getUserId2())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_U1_U2_USERID1_2);

			sb.append(_FINDER_COLUMN_U1_U2_USERID2_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId1);

				queryPos.add(userId2);

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByU1_U2_First(
			long userId1, long userId2,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByU1_U2_First(
			userId1, userId2, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId1=");
		sb.append(userId1);

		sb.append(", userId2=");
		sb.append(userId2);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByU1_U2_First(
		long userId1, long userId2,
		OrderByComparator<SocialRelation> orderByComparator) {

		List<SocialRelation> list = findByU1_U2(
			userId1, userId2, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByU1_U2_Last(
			long userId1, long userId2,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByU1_U2_Last(
			userId1, userId2, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId1=");
		sb.append(userId1);

		sb.append(", userId2=");
		sb.append(userId2);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByU1_U2_Last(
		long userId1, long userId2,
		OrderByComparator<SocialRelation> orderByComparator) {

		int count = countByU1_U2(userId1, userId2);

		if (count == 0) {
			return null;
		}

		List<SocialRelation> list = findByU1_U2(
			userId1, userId2, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation[] findByU1_U2_PrevAndNext(
			long relationId, long userId1, long userId2,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByU1_U2_PrevAndNext(
				session, socialRelation, userId1, userId2, orderByComparator,
				true);

			array[1] = socialRelation;

			array[2] = getByU1_U2_PrevAndNext(
				session, socialRelation, userId1, userId2, orderByComparator,
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

	protected SocialRelation getByU1_U2_PrevAndNext(
		Session session, SocialRelation socialRelation, long userId1,
		long userId2, OrderByComparator<SocialRelation> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		sb.append(_FINDER_COLUMN_U1_U2_USERID1_2);

		sb.append(_FINDER_COLUMN_U1_U2_USERID2_2);

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
			sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId1);

		queryPos.add(userId2);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialRelation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialRelation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social relations where userId1 = &#63; and userId2 = &#63; from the database.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 */
	@Override
	public void removeByU1_U2(long userId1, long userId2) {
		for (SocialRelation socialRelation :
				findByU1_U2(
					userId1, userId2, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where userId1 = &#63; and userId2 = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @return the number of matching social relations
	 */
	@Override
	public int countByU1_U2(long userId1, long userId2) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU1_U2;

			finderArgs = new Object[] {userId1, userId2};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_U1_U2_USERID1_2);

			sb.append(_FINDER_COLUMN_U1_U2_USERID2_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId1);

				queryPos.add(userId2);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_U1_U2_USERID1_2 =
		"socialRelation.userId1 = ? AND ";

	private static final String _FINDER_COLUMN_U1_U2_USERID2_2 =
		"socialRelation.userId2 = ?";

	private FinderPath _finderPathWithPaginationFindByU1_T;
	private FinderPath _finderPathWithoutPaginationFindByU1_T;
	private FinderPath _finderPathCountByU1_T;

	/**
	 * Returns all the social relations where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @return the matching social relations
	 */
	@Override
	public List<SocialRelation> findByU1_T(long userId1, int type) {
		return findByU1_T(
			userId1, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where userId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByU1_T(
		long userId1, int type, int start, int end) {

		return findByU1_T(userId1, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where userId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByU1_T(
		long userId1, int type, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findByU1_T(userId1, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations where userId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByU1_T(
		long userId1, int type, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByU1_T;
				finderArgs = new Object[] {userId1, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByU1_T;
			finderArgs = new Object[] {
				userId1, type, start, end, orderByComparator
			};
		}

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialRelation socialRelation : list) {
					if ((userId1 != socialRelation.getUserId1()) ||
						(type != socialRelation.getType())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_U1_T_USERID1_2);

			sb.append(_FINDER_COLUMN_U1_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId1);

				queryPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first social relation in the ordered set where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByU1_T_First(
			long userId1, int type,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByU1_T_First(
			userId1, type, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId1=");
		sb.append(userId1);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByU1_T_First(
		long userId1, int type,
		OrderByComparator<SocialRelation> orderByComparator) {

		List<SocialRelation> list = findByU1_T(
			userId1, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByU1_T_Last(
			long userId1, int type,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByU1_T_Last(
			userId1, type, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId1=");
		sb.append(userId1);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByU1_T_Last(
		long userId1, int type,
		OrderByComparator<SocialRelation> orderByComparator) {

		int count = countByU1_T(userId1, type);

		if (count == 0) {
			return null;
		}

		List<SocialRelation> list = findByU1_T(
			userId1, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63; and type = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param userId1 the user id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation[] findByU1_T_PrevAndNext(
			long relationId, long userId1, int type,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByU1_T_PrevAndNext(
				session, socialRelation, userId1, type, orderByComparator,
				true);

			array[1] = socialRelation;

			array[2] = getByU1_T_PrevAndNext(
				session, socialRelation, userId1, type, orderByComparator,
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

	protected SocialRelation getByU1_T_PrevAndNext(
		Session session, SocialRelation socialRelation, long userId1, int type,
		OrderByComparator<SocialRelation> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		sb.append(_FINDER_COLUMN_U1_T_USERID1_2);

		sb.append(_FINDER_COLUMN_U1_T_TYPE_2);

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
			sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId1);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialRelation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialRelation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social relations where userId1 = &#63; and type = &#63; from the database.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 */
	@Override
	public void removeByU1_T(long userId1, int type) {
		for (SocialRelation socialRelation :
				findByU1_T(
					userId1, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where userId1 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param type the type
	 * @return the number of matching social relations
	 */
	@Override
	public int countByU1_T(long userId1, int type) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU1_T;

			finderArgs = new Object[] {userId1, type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_U1_T_USERID1_2);

			sb.append(_FINDER_COLUMN_U1_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId1);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_U1_T_USERID1_2 =
		"socialRelation.userId1 = ? AND ";

	private static final String _FINDER_COLUMN_U1_T_TYPE_2 =
		"socialRelation.type = ?";

	private FinderPath _finderPathWithPaginationFindByU2_T;
	private FinderPath _finderPathWithoutPaginationFindByU2_T;
	private FinderPath _finderPathCountByU2_T;

	/**
	 * Returns all the social relations where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the matching social relations
	 */
	@Override
	public List<SocialRelation> findByU2_T(long userId2, int type) {
		return findByU2_T(
			userId2, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations where userId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByU2_T(
		long userId2, int type, int start, int end) {

		return findByU2_T(userId2, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations where userId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByU2_T(
		long userId2, int type, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findByU2_T(userId2, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations where userId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching social relations
	 */
	@Override
	public List<SocialRelation> findByU2_T(
		long userId2, int type, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByU2_T;
				finderArgs = new Object[] {userId2, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByU2_T;
			finderArgs = new Object[] {
				userId2, type, start, end, orderByComparator
			};
		}

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialRelation socialRelation : list) {
					if ((userId2 != socialRelation.getUserId2()) ||
						(type != socialRelation.getType())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_U2_T_USERID2_2);

			sb.append(_FINDER_COLUMN_U2_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId2);

				queryPos.add(type);

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first social relation in the ordered set where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByU2_T_First(
			long userId2, int type,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByU2_T_First(
			userId2, type, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId2=");
		sb.append(userId2);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the first social relation in the ordered set where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByU2_T_First(
		long userId2, int type,
		OrderByComparator<SocialRelation> orderByComparator) {

		List<SocialRelation> list = findByU2_T(
			userId2, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social relation in the ordered set where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByU2_T_Last(
			long userId2, int type,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByU2_T_Last(
			userId2, type, orderByComparator);

		if (socialRelation != null) {
			return socialRelation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId2=");
		sb.append(userId2);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchRelationException(sb.toString());
	}

	/**
	 * Returns the last social relation in the ordered set where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByU2_T_Last(
		long userId2, int type,
		OrderByComparator<SocialRelation> orderByComparator) {

		int count = countByU2_T(userId2, type);

		if (count == 0) {
			return null;
		}

		List<SocialRelation> list = findByU2_T(
			userId2, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social relations before and after the current social relation in the ordered set where userId2 = &#63; and type = &#63;.
	 *
	 * @param relationId the primary key of the current social relation
	 * @param userId2 the user id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation[] findByU2_T_PrevAndNext(
			long relationId, long userId2, int type,
			OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException {

		SocialRelation socialRelation = findByPrimaryKey(relationId);

		Session session = null;

		try {
			session = openSession();

			SocialRelation[] array = new SocialRelationImpl[3];

			array[0] = getByU2_T_PrevAndNext(
				session, socialRelation, userId2, type, orderByComparator,
				true);

			array[1] = socialRelation;

			array[2] = getByU2_T_PrevAndNext(
				session, socialRelation, userId2, type, orderByComparator,
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

	protected SocialRelation getByU2_T_PrevAndNext(
		Session session, SocialRelation socialRelation, long userId2, int type,
		OrderByComparator<SocialRelation> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

		sb.append(_FINDER_COLUMN_U2_T_USERID2_2);

		sb.append(_FINDER_COLUMN_U2_T_TYPE_2);

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
			sb.append(SocialRelationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId2);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						socialRelation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SocialRelation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social relations where userId2 = &#63; and type = &#63; from the database.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 */
	@Override
	public void removeByU2_T(long userId2, int type) {
		for (SocialRelation socialRelation :
				findByU2_T(
					userId2, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations where userId2 = &#63; and type = &#63;.
	 *
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the number of matching social relations
	 */
	@Override
	public int countByU2_T(long userId2, int type) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU2_T;

			finderArgs = new Object[] {userId2, type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_U2_T_USERID2_2);

			sb.append(_FINDER_COLUMN_U2_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId2);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_U2_T_USERID2_2 =
		"socialRelation.userId2 = ? AND ";

	private static final String _FINDER_COLUMN_U2_T_TYPE_2 =
		"socialRelation.type = ?";

	private FinderPath _finderPathFetchByU1_U2_T;
	private FinderPath _finderPathCountByU1_U2_T;

	/**
	 * Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or throws a <code>NoSuchRelationException</code> if it could not be found.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the matching social relation
	 * @throws NoSuchRelationException if a matching social relation could not be found
	 */
	@Override
	public SocialRelation findByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByU1_U2_T(userId1, userId2, type);

		if (socialRelation == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId1=");
			sb.append(userId1);

			sb.append(", userId2=");
			sb.append(userId2);

			sb.append(", type=");
			sb.append(type);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchRelationException(sb.toString());
		}

		return socialRelation;
	}

	/**
	 * Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByU1_U2_T(long userId1, long userId2, int type) {
		return fetchByU1_U2_T(userId1, userId2, type, true);
	}

	/**
	 * Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching social relation, or <code>null</code> if a matching social relation could not be found
	 */
	@Override
	public SocialRelation fetchByU1_U2_T(
		long userId1, long userId2, int type, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {userId1, userId2, type};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByU1_U2_T, finderArgs, this);
		}

		if (result instanceof SocialRelation) {
			SocialRelation socialRelation = (SocialRelation)result;

			if ((userId1 != socialRelation.getUserId1()) ||
				(userId2 != socialRelation.getUserId2()) ||
				(type != socialRelation.getType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_U1_U2_T_USERID1_2);

			sb.append(_FINDER_COLUMN_U1_U2_T_USERID2_2);

			sb.append(_FINDER_COLUMN_U1_U2_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId1);

				queryPos.add(userId2);

				queryPos.add(type);

				List<SocialRelation> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByU1_U2_T, finderArgs, list);
					}
				}
				else {
					SocialRelation socialRelation = list.get(0);

					result = socialRelation;

					cacheResult(socialRelation);
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
			return (SocialRelation)result;
		}
	}

	/**
	 * Removes the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; from the database.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the social relation that was removed
	 */
	@Override
	public SocialRelation removeByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException {

		SocialRelation socialRelation = findByU1_U2_T(userId1, userId2, type);

		return remove(socialRelation);
	}

	/**
	 * Returns the number of social relations where userId1 = &#63; and userId2 = &#63; and type = &#63;.
	 *
	 * @param userId1 the user id1
	 * @param userId2 the user id2
	 * @param type the type
	 * @return the number of matching social relations
	 */
	@Override
	public int countByU1_U2_T(long userId1, long userId2, int type) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU1_U2_T;

			finderArgs = new Object[] {userId1, userId2, type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SOCIALRELATION_WHERE);

			sb.append(_FINDER_COLUMN_U1_U2_T_USERID1_2);

			sb.append(_FINDER_COLUMN_U1_U2_T_USERID2_2);

			sb.append(_FINDER_COLUMN_U1_U2_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId1);

				queryPos.add(userId2);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_U1_U2_T_USERID1_2 =
		"socialRelation.userId1 = ? AND ";

	private static final String _FINDER_COLUMN_U1_U2_T_USERID2_2 =
		"socialRelation.userId2 = ? AND ";

	private static final String _FINDER_COLUMN_U1_U2_T_TYPE_2 =
		"socialRelation.type = ?";

	public SocialRelationPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SocialRelation.class);

		setModelImplClass(SocialRelationImpl.class);
		setModelPKClass(long.class);

		setTable(SocialRelationTable.INSTANCE);
	}

	/**
	 * Caches the social relation in the entity cache if it is enabled.
	 *
	 * @param socialRelation the social relation
	 */
	@Override
	public void cacheResult(SocialRelation socialRelation) {
		if (socialRelation.getCtCollectionId() != 0) {
			socialRelation.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			SocialRelationImpl.class, socialRelation.getPrimaryKey(),
			socialRelation);

		FinderCacheUtil.putResult(
			_finderPathFetchByU1_U2_T,
			new Object[] {
				socialRelation.getUserId1(), socialRelation.getUserId2(),
				socialRelation.getType()
			},
			socialRelation);

		socialRelation.resetOriginalValues();
	}

	/**
	 * Caches the social relations in the entity cache if it is enabled.
	 *
	 * @param socialRelations the social relations
	 */
	@Override
	public void cacheResult(List<SocialRelation> socialRelations) {
		for (SocialRelation socialRelation : socialRelations) {
			if (socialRelation.getCtCollectionId() != 0) {
				socialRelation.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					SocialRelationImpl.class, socialRelation.getPrimaryKey()) ==
						null) {

				cacheResult(socialRelation);
			}
			else {
				socialRelation.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social relations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(SocialRelationImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social relation.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialRelation socialRelation) {
		EntityCacheUtil.removeResult(
			SocialRelationImpl.class, socialRelation.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SocialRelationModelImpl)socialRelation, true);
	}

	@Override
	public void clearCache(List<SocialRelation> socialRelations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialRelation socialRelation : socialRelations) {
			EntityCacheUtil.removeResult(
				SocialRelationImpl.class, socialRelation.getPrimaryKey());

			clearUniqueFindersCache(
				(SocialRelationModelImpl)socialRelation, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(SocialRelationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SocialRelationModelImpl socialRelationModelImpl) {

		Object[] args = new Object[] {
			socialRelationModelImpl.getUserId1(),
			socialRelationModelImpl.getUserId2(),
			socialRelationModelImpl.getType()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByU1_U2_T, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByU1_U2_T, args, socialRelationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SocialRelationModelImpl socialRelationModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				socialRelationModelImpl.getUserId1(),
				socialRelationModelImpl.getUserId2(),
				socialRelationModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU1_U2_T, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU1_U2_T, args);
		}

		if ((socialRelationModelImpl.getColumnBitmask() &
			 _finderPathFetchByU1_U2_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				socialRelationModelImpl.getOriginalUserId1(),
				socialRelationModelImpl.getOriginalUserId2(),
				socialRelationModelImpl.getOriginalType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU1_U2_T, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU1_U2_T, args);
		}
	}

	/**
	 * Creates a new social relation with the primary key. Does not add the social relation to the database.
	 *
	 * @param relationId the primary key for the new social relation
	 * @return the new social relation
	 */
	@Override
	public SocialRelation create(long relationId) {
		SocialRelation socialRelation = new SocialRelationImpl();

		socialRelation.setNew(true);
		socialRelation.setPrimaryKey(relationId);

		String uuid = PortalUUIDUtil.generate();

		socialRelation.setUuid(uuid);

		socialRelation.setCompanyId(CompanyThreadLocal.getCompanyId());

		return socialRelation;
	}

	/**
	 * Removes the social relation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param relationId the primary key of the social relation
	 * @return the social relation that was removed
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation remove(long relationId)
		throws NoSuchRelationException {

		return remove((Serializable)relationId);
	}

	/**
	 * Removes the social relation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social relation
	 * @return the social relation that was removed
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation remove(Serializable primaryKey)
		throws NoSuchRelationException {

		Session session = null;

		try {
			session = openSession();

			SocialRelation socialRelation = (SocialRelation)session.get(
				SocialRelationImpl.class, primaryKey);

			if (socialRelation == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRelationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(socialRelation);
		}
		catch (NoSuchRelationException noSuchEntityException) {
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
	protected SocialRelation removeImpl(SocialRelation socialRelation) {
		if (!CTPersistenceHelperUtil.isRemove(socialRelation)) {
			return socialRelation;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(socialRelation)) {
				socialRelation = (SocialRelation)session.get(
					SocialRelationImpl.class,
					socialRelation.getPrimaryKeyObj());
			}

			if (socialRelation != null) {
				session.delete(socialRelation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (socialRelation != null) {
			clearCache(socialRelation);
		}

		return socialRelation;
	}

	@Override
	public SocialRelation updateImpl(SocialRelation socialRelation) {
		boolean isNew = socialRelation.isNew();

		if (!(socialRelation instanceof SocialRelationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(socialRelation.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					socialRelation);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in socialRelation proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SocialRelation implementation " +
					socialRelation.getClass());
		}

		SocialRelationModelImpl socialRelationModelImpl =
			(SocialRelationModelImpl)socialRelation;

		if (Validator.isNull(socialRelation.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			socialRelation.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(socialRelation)) {
				if (!isNew) {
					session.evict(
						SocialRelationImpl.class,
						socialRelation.getPrimaryKeyObj());
				}

				session.save(socialRelation);

				socialRelation.setNew(false);
			}
			else {
				socialRelation = (SocialRelation)session.merge(socialRelation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (socialRelation.getCtCollectionId() != 0) {
			socialRelation.resetOriginalValues();

			return socialRelation;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {socialRelationModelImpl.getUuid()};

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				socialRelationModelImpl.getUuid(),
				socialRelationModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {socialRelationModelImpl.getCompanyId()};

			FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {socialRelationModelImpl.getUserId1()};

			FinderCacheUtil.removeResult(_finderPathCountByUserId1, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUserId1, args);

			args = new Object[] {socialRelationModelImpl.getUserId2()};

			FinderCacheUtil.removeResult(_finderPathCountByUserId2, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUserId2, args);

			args = new Object[] {socialRelationModelImpl.getType()};

			FinderCacheUtil.removeResult(_finderPathCountByType, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByType, args);

			args = new Object[] {
				socialRelationModelImpl.getCompanyId(),
				socialRelationModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_T, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_T, args);

			args = new Object[] {
				socialRelationModelImpl.getUserId1(),
				socialRelationModelImpl.getUserId2()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU1_U2, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU1_U2, args);

			args = new Object[] {
				socialRelationModelImpl.getUserId1(),
				socialRelationModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU1_T, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU1_T, args);

			args = new Object[] {
				socialRelationModelImpl.getUserId2(),
				socialRelationModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU2_T, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU2_T, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((socialRelationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialRelationModelImpl.getOriginalUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {socialRelationModelImpl.getUuid()};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialRelationModelImpl.getOriginalUuid(),
					socialRelationModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					socialRelationModelImpl.getUuid(),
					socialRelationModelImpl.getCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					socialRelationModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {socialRelationModelImpl.getCompanyId()};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId1.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					socialRelationModelImpl.getOriginalUserId1()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUserId1, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId1, args);

				args = new Object[] {socialRelationModelImpl.getUserId1()};

				FinderCacheUtil.removeResult(_finderPathCountByUserId1, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId1, args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId2.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					socialRelationModelImpl.getOriginalUserId2()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUserId2, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId2, args);

				args = new Object[] {socialRelationModelImpl.getUserId2()};

				FinderCacheUtil.removeResult(_finderPathCountByUserId2, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId2, args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByType.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialRelationModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByType, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByType, args);

				args = new Object[] {socialRelationModelImpl.getType()};

				FinderCacheUtil.removeResult(_finderPathCountByType, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByType, args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialRelationModelImpl.getOriginalCompanyId(),
					socialRelationModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_T, args);

				args = new Object[] {
					socialRelationModelImpl.getCompanyId(),
					socialRelationModelImpl.getType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_T, args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU1_U2.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialRelationModelImpl.getOriginalUserId1(),
					socialRelationModelImpl.getOriginalUserId2()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU1_U2, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU1_U2, args);

				args = new Object[] {
					socialRelationModelImpl.getUserId1(),
					socialRelationModelImpl.getUserId2()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU1_U2, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU1_U2, args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU1_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialRelationModelImpl.getOriginalUserId1(),
					socialRelationModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU1_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU1_T, args);

				args = new Object[] {
					socialRelationModelImpl.getUserId1(),
					socialRelationModelImpl.getType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU1_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU1_T, args);
			}

			if ((socialRelationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU2_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					socialRelationModelImpl.getOriginalUserId2(),
					socialRelationModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU2_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU2_T, args);

				args = new Object[] {
					socialRelationModelImpl.getUserId2(),
					socialRelationModelImpl.getType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU2_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU2_T, args);
			}
		}

		EntityCacheUtil.putResult(
			SocialRelationImpl.class, socialRelation.getPrimaryKey(),
			socialRelation, false);

		clearUniqueFindersCache(socialRelationModelImpl, false);
		cacheUniqueFindersCache(socialRelationModelImpl);

		socialRelation.resetOriginalValues();

		return socialRelation;
	}

	/**
	 * Returns the social relation with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social relation
	 * @return the social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRelationException {

		SocialRelation socialRelation = fetchByPrimaryKey(primaryKey);

		if (socialRelation == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRelationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return socialRelation;
	}

	/**
	 * Returns the social relation with the primary key or throws a <code>NoSuchRelationException</code> if it could not be found.
	 *
	 * @param relationId the primary key of the social relation
	 * @return the social relation
	 * @throws NoSuchRelationException if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation findByPrimaryKey(long relationId)
		throws NoSuchRelationException {

		return findByPrimaryKey((Serializable)relationId);
	}

	/**
	 * Returns the social relation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social relation
	 * @return the social relation, or <code>null</code> if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(SocialRelation.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		SocialRelation socialRelation = null;

		Session session = null;

		try {
			session = openSession();

			socialRelation = (SocialRelation)session.get(
				SocialRelationImpl.class, primaryKey);

			if (socialRelation != null) {
				cacheResult(socialRelation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return socialRelation;
	}

	/**
	 * Returns the social relation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param relationId the primary key of the social relation
	 * @return the social relation, or <code>null</code> if a social relation with the primary key could not be found
	 */
	@Override
	public SocialRelation fetchByPrimaryKey(long relationId) {
		return fetchByPrimaryKey((Serializable)relationId);
	}

	@Override
	public Map<Serializable, SocialRelation> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(SocialRelation.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SocialRelation> map =
			new HashMap<Serializable, SocialRelation>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SocialRelation socialRelation = fetchByPrimaryKey(primaryKey);

			if (socialRelation != null) {
				map.put(primaryKey, socialRelation);
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

			for (SocialRelation socialRelation :
					(List<SocialRelation>)query.list()) {

				map.put(socialRelation.getPrimaryKeyObj(), socialRelation);

				cacheResult(socialRelation);
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
	 * Returns all the social relations.
	 *
	 * @return the social relations
	 */
	@Override
	public List<SocialRelation> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social relations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @return the range of social relations
	 */
	@Override
	public List<SocialRelation> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social relations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social relations
	 */
	@Override
	public List<SocialRelation> findAll(
		int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social relations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SocialRelationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social relations
	 * @param end the upper bound of the range of social relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of social relations
	 */
	@Override
	public List<SocialRelation> findAll(
		int start, int end, OrderByComparator<SocialRelation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

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

		List<SocialRelation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SocialRelation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SOCIALRELATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALRELATION;

				sql = sql.concat(SocialRelationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SocialRelation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the social relations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SocialRelation socialRelation : findAll()) {
			remove(socialRelation);
		}
	}

	/**
	 * Returns the number of social relations.
	 *
	 * @return the number of social relations
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SocialRelation.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SOCIALRELATION);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "relationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SOCIALRELATION;
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
		return SocialRelationModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "SocialRelation";
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
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("createDate");
		ctStrictColumnNames.add("userId1");
		ctStrictColumnNames.add("userId2");
		ctStrictColumnNames.add("type_");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("relationId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"userId1", "userId2", "type_"});
	}

	/**
	 * Initializes the social relation persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			SocialRelationModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			SocialRelationModelImpl.UUID_COLUMN_BITMASK |
			SocialRelationModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] {Long.class.getName()},
			SocialRelationModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByUserId1 = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId1",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId1 = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserId1", new String[] {Long.class.getName()},
			SocialRelationModelImpl.USERID1_COLUMN_BITMASK);

		_finderPathCountByUserId1 = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserId1", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByUserId2 = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId2",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId2 = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserId2", new String[] {Long.class.getName()},
			SocialRelationModelImpl.USERID2_COLUMN_BITMASK);

		_finderPathCountByUserId2 = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserId2", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByType = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByType",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByType = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByType", new String[] {Integer.class.getName()},
			SocialRelationModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByType = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByType", new String[] {Integer.class.getName()});

		_finderPathWithPaginationFindByC_T = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_T = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			SocialRelationModelImpl.COMPANYID_COLUMN_BITMASK |
			SocialRelationModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByC_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByU1_U2 = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU1_U2",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU1_U2 = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByU1_U2",
			new String[] {Long.class.getName(), Long.class.getName()},
			SocialRelationModelImpl.USERID1_COLUMN_BITMASK |
			SocialRelationModelImpl.USERID2_COLUMN_BITMASK);

		_finderPathCountByU1_U2 = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByU1_U2",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByU1_T = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU1_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU1_T = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByU1_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			SocialRelationModelImpl.USERID1_COLUMN_BITMASK |
			SocialRelationModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByU1_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByU1_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByU2_T = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU2_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU2_T = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByU2_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			SocialRelationModelImpl.USERID2_COLUMN_BITMASK |
			SocialRelationModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByU2_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByU2_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathFetchByU1_U2_T = new FinderPath(
			SocialRelationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU1_U2_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialRelationModelImpl.USERID1_COLUMN_BITMASK |
			SocialRelationModelImpl.USERID2_COLUMN_BITMASK |
			SocialRelationModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByU1_U2_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByU1_U2_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SocialRelationImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SOCIALRELATION =
		"SELECT socialRelation FROM SocialRelation socialRelation";

	private static final String _SQL_SELECT_SOCIALRELATION_WHERE =
		"SELECT socialRelation FROM SocialRelation socialRelation WHERE ";

	private static final String _SQL_COUNT_SOCIALRELATION =
		"SELECT COUNT(socialRelation) FROM SocialRelation socialRelation";

	private static final String _SQL_COUNT_SOCIALRELATION_WHERE =
		"SELECT COUNT(socialRelation) FROM SocialRelation socialRelation WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "socialRelation.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SocialRelation exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SocialRelation exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SocialRelationPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

}