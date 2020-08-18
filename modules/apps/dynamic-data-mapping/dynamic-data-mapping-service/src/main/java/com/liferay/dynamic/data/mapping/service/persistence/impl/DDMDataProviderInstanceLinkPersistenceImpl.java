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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceLinkException;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLinkTable;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstanceLinkPersistence;
import com.liferay.dynamic.data.mapping.service.persistence.impl.constants.DDMPersistenceConstants;
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
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ddm data provider instance link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DDMDataProviderInstanceLinkPersistence.class)
public class DDMDataProviderInstanceLinkPersistenceImpl
	extends BasePersistenceImpl<DDMDataProviderInstanceLink>
	implements DDMDataProviderInstanceLinkPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDMDataProviderInstanceLinkUtil</code> to access the ddm data provider instance link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDMDataProviderInstanceLinkImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByDataProviderInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByDataProviderInstanceId;
	private FinderPath _finderPathCountByDataProviderInstanceId;

	/**
	 * Returns all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @return the matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId) {

		return findByDataProviderInstanceId(
			dataProviderInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @return the range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId, int start, int end) {

		return findByDataProviderInstanceId(
			dataProviderInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return findByDataProviderInstanceId(
			dataProviderInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMDataProviderInstanceLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByDataProviderInstanceId;
				finderArgs = new Object[] {dataProviderInstanceId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByDataProviderInstanceId;
			finderArgs = new Object[] {
				dataProviderInstanceId, start, end, orderByComparator
			};
		}

		List<DDMDataProviderInstanceLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMDataProviderInstanceLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
						list) {

					if (dataProviderInstanceId !=
							ddmDataProviderInstanceLink.
								getDataProviderInstanceId()) {

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

			sb.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCELINK_WHERE);

			sb.append(
				_FINDER_COLUMN_DATAPROVIDERINSTANCEID_DATAPROVIDERINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMDataProviderInstanceLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(dataProviderInstanceId);

				list = (List<DDMDataProviderInstanceLink>)QueryUtil.list(
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
	 * Returns the first ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByDataProviderInstanceId_First(
			long dataProviderInstanceId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			fetchByDataProviderInstanceId_First(
				dataProviderInstanceId, orderByComparator);

		if (ddmDataProviderInstanceLink != null) {
			return ddmDataProviderInstanceLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("dataProviderInstanceId=");
		sb.append(dataProviderInstanceId);

		sb.append("}");

		throw new NoSuchDataProviderInstanceLinkException(sb.toString());
	}

	/**
	 * Returns the first ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByDataProviderInstanceId_First(
		long dataProviderInstanceId,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		List<DDMDataProviderInstanceLink> list = findByDataProviderInstanceId(
			dataProviderInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByDataProviderInstanceId_Last(
			long dataProviderInstanceId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			fetchByDataProviderInstanceId_Last(
				dataProviderInstanceId, orderByComparator);

		if (ddmDataProviderInstanceLink != null) {
			return ddmDataProviderInstanceLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("dataProviderInstanceId=");
		sb.append(dataProviderInstanceId);

		sb.append("}");

		throw new NoSuchDataProviderInstanceLinkException(sb.toString());
	}

	/**
	 * Returns the last ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByDataProviderInstanceId_Last(
		long dataProviderInstanceId,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		int count = countByDataProviderInstanceId(dataProviderInstanceId);

		if (count == 0) {
			return null;
		}

		List<DDMDataProviderInstanceLink> list = findByDataProviderInstanceId(
			dataProviderInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm data provider instance links before and after the current ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the current ddm data provider instance link
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink[]
			findByDataProviderInstanceId_PrevAndNext(
				long dataProviderInstanceLinkId, long dataProviderInstanceId,
				OrderByComparator<DDMDataProviderInstanceLink>
					orderByComparator)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			findByPrimaryKey(dataProviderInstanceLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProviderInstanceLink[] array =
				new DDMDataProviderInstanceLinkImpl[3];

			array[0] = getByDataProviderInstanceId_PrevAndNext(
				session, ddmDataProviderInstanceLink, dataProviderInstanceId,
				orderByComparator, true);

			array[1] = ddmDataProviderInstanceLink;

			array[2] = getByDataProviderInstanceId_PrevAndNext(
				session, ddmDataProviderInstanceLink, dataProviderInstanceId,
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

	protected DDMDataProviderInstanceLink
		getByDataProviderInstanceId_PrevAndNext(
			Session session,
			DDMDataProviderInstanceLink ddmDataProviderInstanceLink,
			long dataProviderInstanceId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCELINK_WHERE);

		sb.append(
			_FINDER_COLUMN_DATAPROVIDERINSTANCEID_DATAPROVIDERINSTANCEID_2);

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
			sb.append(DDMDataProviderInstanceLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(dataProviderInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmDataProviderInstanceLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMDataProviderInstanceLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm data provider instance links where dataProviderInstanceId = &#63; from the database.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 */
	@Override
	public void removeByDataProviderInstanceId(long dataProviderInstanceId) {
		for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
				findByDataProviderInstanceId(
					dataProviderInstanceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ddmDataProviderInstanceLink);
		}
	}

	/**
	 * Returns the number of ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @return the number of matching ddm data provider instance links
	 */
	@Override
	public int countByDataProviderInstanceId(long dataProviderInstanceId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMDataProviderInstanceLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByDataProviderInstanceId;

			finderArgs = new Object[] {dataProviderInstanceId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDMDATAPROVIDERINSTANCELINK_WHERE);

			sb.append(
				_FINDER_COLUMN_DATAPROVIDERINSTANCEID_DATAPROVIDERINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(dataProviderInstanceId);

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

	private static final String
		_FINDER_COLUMN_DATAPROVIDERINSTANCEID_DATAPROVIDERINSTANCEID_2 =
			"ddmDataProviderInstanceLink.dataProviderInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByStructureId;
	private FinderPath _finderPathWithoutPaginationFindByStructureId;
	private FinderPath _finderPathCountByStructureId;

	/**
	 * Returns all the ddm data provider instance links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId) {

		return findByStructureId(
			structureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm data provider instance links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @return the range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end) {

		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return findByStructureId(
			structureId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMDataProviderInstanceLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByStructureId;
				finderArgs = new Object[] {structureId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByStructureId;
			finderArgs = new Object[] {
				structureId, start, end, orderByComparator
			};
		}

		List<DDMDataProviderInstanceLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMDataProviderInstanceLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
						list) {

					if (structureId !=
							ddmDataProviderInstanceLink.getStructureId()) {

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

			sb.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCELINK_WHERE);

			sb.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMDataProviderInstanceLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(structureId);

				list = (List<DDMDataProviderInstanceLink>)QueryUtil.list(
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
	 * Returns the first ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByStructureId_First(
			long structureId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			fetchByStructureId_First(structureId, orderByComparator);

		if (ddmDataProviderInstanceLink != null) {
			return ddmDataProviderInstanceLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("structureId=");
		sb.append(structureId);

		sb.append("}");

		throw new NoSuchDataProviderInstanceLinkException(sb.toString());
	}

	/**
	 * Returns the first ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByStructureId_First(
		long structureId,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		List<DDMDataProviderInstanceLink> list = findByStructureId(
			structureId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByStructureId_Last(
			long structureId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			fetchByStructureId_Last(structureId, orderByComparator);

		if (ddmDataProviderInstanceLink != null) {
			return ddmDataProviderInstanceLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("structureId=");
		sb.append(structureId);

		sb.append("}");

		throw new NoSuchDataProviderInstanceLinkException(sb.toString());
	}

	/**
	 * Returns the last ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByStructureId_Last(
		long structureId,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		int count = countByStructureId(structureId);

		if (count == 0) {
			return null;
		}

		List<DDMDataProviderInstanceLink> list = findByStructureId(
			structureId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm data provider instance links before and after the current ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the current ddm data provider instance link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink[] findByStructureId_PrevAndNext(
			long dataProviderInstanceLinkId, long structureId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			findByPrimaryKey(dataProviderInstanceLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProviderInstanceLink[] array =
				new DDMDataProviderInstanceLinkImpl[3];

			array[0] = getByStructureId_PrevAndNext(
				session, ddmDataProviderInstanceLink, structureId,
				orderByComparator, true);

			array[1] = ddmDataProviderInstanceLink;

			array[2] = getByStructureId_PrevAndNext(
				session, ddmDataProviderInstanceLink, structureId,
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

	protected DDMDataProviderInstanceLink getByStructureId_PrevAndNext(
		Session session,
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink,
		long structureId,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCELINK_WHERE);

		sb.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

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
			sb.append(DDMDataProviderInstanceLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(structureId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmDataProviderInstanceLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMDataProviderInstanceLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm data provider instance links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	@Override
	public void removeByStructureId(long structureId) {
		for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
				findByStructureId(
					structureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddmDataProviderInstanceLink);
		}
	}

	/**
	 * Returns the number of ddm data provider instance links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching ddm data provider instance links
	 */
	@Override
	public int countByStructureId(long structureId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMDataProviderInstanceLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByStructureId;

			finderArgs = new Object[] {structureId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDMDATAPROVIDERINSTANCELINK_WHERE);

			sb.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(structureId);

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

	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 =
		"ddmDataProviderInstanceLink.structureId = ?";

	private FinderPath _finderPathFetchByD_S;
	private FinderPath _finderPathCountByD_S;

	/**
	 * Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or throws a <code>NoSuchDataProviderInstanceLinkException</code> if it could not be found.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByD_S(
			long dataProviderInstanceId, long structureId)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink = fetchByD_S(
			dataProviderInstanceId, structureId);

		if (ddmDataProviderInstanceLink == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("dataProviderInstanceId=");
			sb.append(dataProviderInstanceId);

			sb.append(", structureId=");
			sb.append(structureId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDataProviderInstanceLinkException(sb.toString());
		}

		return ddmDataProviderInstanceLink;
	}

	/**
	 * Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByD_S(
		long dataProviderInstanceId, long structureId) {

		return fetchByD_S(dataProviderInstanceId, structureId, true);
	}

	/**
	 * Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByD_S(
		long dataProviderInstanceId, long structureId, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMDataProviderInstanceLink.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {dataProviderInstanceId, structureId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByD_S, finderArgs, this);
		}

		if (result instanceof DDMDataProviderInstanceLink) {
			DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
				(DDMDataProviderInstanceLink)result;

			if ((dataProviderInstanceId !=
					ddmDataProviderInstanceLink.getDataProviderInstanceId()) ||
				(structureId != ddmDataProviderInstanceLink.getStructureId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCELINK_WHERE);

			sb.append(_FINDER_COLUMN_D_S_DATAPROVIDERINSTANCEID_2);

			sb.append(_FINDER_COLUMN_D_S_STRUCTUREID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(dataProviderInstanceId);

				queryPos.add(structureId);

				List<DDMDataProviderInstanceLink> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByD_S, finderArgs, list);
					}
				}
				else {
					DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
						list.get(0);

					result = ddmDataProviderInstanceLink;

					cacheResult(ddmDataProviderInstanceLink);
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
			return (DDMDataProviderInstanceLink)result;
		}
	}

	/**
	 * Removes the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; from the database.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the ddm data provider instance link that was removed
	 */
	@Override
	public DDMDataProviderInstanceLink removeByD_S(
			long dataProviderInstanceId, long structureId)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink = findByD_S(
			dataProviderInstanceId, structureId);

		return remove(ddmDataProviderInstanceLink);
	}

	/**
	 * Returns the number of ddm data provider instance links where dataProviderInstanceId = &#63; and structureId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the number of matching ddm data provider instance links
	 */
	@Override
	public int countByD_S(long dataProviderInstanceId, long structureId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMDataProviderInstanceLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByD_S;

			finderArgs = new Object[] {dataProviderInstanceId, structureId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMDATAPROVIDERINSTANCELINK_WHERE);

			sb.append(_FINDER_COLUMN_D_S_DATAPROVIDERINSTANCEID_2);

			sb.append(_FINDER_COLUMN_D_S_STRUCTUREID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(dataProviderInstanceId);

				queryPos.add(structureId);

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

	private static final String _FINDER_COLUMN_D_S_DATAPROVIDERINSTANCEID_2 =
		"ddmDataProviderInstanceLink.dataProviderInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_D_S_STRUCTUREID_2 =
		"ddmDataProviderInstanceLink.structureId = ?";

	public DDMDataProviderInstanceLinkPersistenceImpl() {
		setModelClass(DDMDataProviderInstanceLink.class);

		setModelImplClass(DDMDataProviderInstanceLinkImpl.class);
		setModelPKClass(long.class);

		setTable(DDMDataProviderInstanceLinkTable.INSTANCE);
	}

	/**
	 * Caches the ddm data provider instance link in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviderInstanceLink the ddm data provider instance link
	 */
	@Override
	public void cacheResult(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		if (ddmDataProviderInstanceLink.getCtCollectionId() != 0) {
			ddmDataProviderInstanceLink.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			DDMDataProviderInstanceLinkImpl.class,
			ddmDataProviderInstanceLink.getPrimaryKey(),
			ddmDataProviderInstanceLink);

		finderCache.putResult(
			_finderPathFetchByD_S,
			new Object[] {
				ddmDataProviderInstanceLink.getDataProviderInstanceId(),
				ddmDataProviderInstanceLink.getStructureId()
			},
			ddmDataProviderInstanceLink);

		ddmDataProviderInstanceLink.resetOriginalValues();
	}

	/**
	 * Caches the ddm data provider instance links in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviderInstanceLinks the ddm data provider instance links
	 */
	@Override
	public void cacheResult(
		List<DDMDataProviderInstanceLink> ddmDataProviderInstanceLinks) {

		for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
				ddmDataProviderInstanceLinks) {

			if (ddmDataProviderInstanceLink.getCtCollectionId() != 0) {
				ddmDataProviderInstanceLink.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					DDMDataProviderInstanceLinkImpl.class,
					ddmDataProviderInstanceLink.getPrimaryKey()) == null) {

				cacheResult(ddmDataProviderInstanceLink);
			}
			else {
				ddmDataProviderInstanceLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ddm data provider instance links.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMDataProviderInstanceLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ddm data provider instance link.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		entityCache.removeResult(
			DDMDataProviderInstanceLinkImpl.class,
			ddmDataProviderInstanceLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DDMDataProviderInstanceLinkModelImpl)ddmDataProviderInstanceLink,
			true);
	}

	@Override
	public void clearCache(
		List<DDMDataProviderInstanceLink> ddmDataProviderInstanceLinks) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
				ddmDataProviderInstanceLinks) {

			entityCache.removeResult(
				DDMDataProviderInstanceLinkImpl.class,
				ddmDataProviderInstanceLink.getPrimaryKey());

			clearUniqueFindersCache(
				(DDMDataProviderInstanceLinkModelImpl)
					ddmDataProviderInstanceLink,
				true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				DDMDataProviderInstanceLinkImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMDataProviderInstanceLinkModelImpl
			ddmDataProviderInstanceLinkModelImpl) {

		Object[] args = new Object[] {
			ddmDataProviderInstanceLinkModelImpl.getDataProviderInstanceId(),
			ddmDataProviderInstanceLinkModelImpl.getStructureId()
		};

		finderCache.putResult(
			_finderPathCountByD_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByD_S, args, ddmDataProviderInstanceLinkModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		DDMDataProviderInstanceLinkModelImpl
			ddmDataProviderInstanceLinkModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ddmDataProviderInstanceLinkModelImpl.
					getDataProviderInstanceId(),
				ddmDataProviderInstanceLinkModelImpl.getStructureId()
			};

			finderCache.removeResult(_finderPathCountByD_S, args);
			finderCache.removeResult(_finderPathFetchByD_S, args);
		}

		if ((ddmDataProviderInstanceLinkModelImpl.getColumnBitmask() &
			 _finderPathFetchByD_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ddmDataProviderInstanceLinkModelImpl.
					getOriginalDataProviderInstanceId(),
				ddmDataProviderInstanceLinkModelImpl.getOriginalStructureId()
			};

			finderCache.removeResult(_finderPathCountByD_S, args);
			finderCache.removeResult(_finderPathFetchByD_S, args);
		}
	}

	/**
	 * Creates a new ddm data provider instance link with the primary key. Does not add the ddm data provider instance link to the database.
	 *
	 * @param dataProviderInstanceLinkId the primary key for the new ddm data provider instance link
	 * @return the new ddm data provider instance link
	 */
	@Override
	public DDMDataProviderInstanceLink create(long dataProviderInstanceLinkId) {
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			new DDMDataProviderInstanceLinkImpl();

		ddmDataProviderInstanceLink.setNew(true);
		ddmDataProviderInstanceLink.setPrimaryKey(dataProviderInstanceLinkId);

		ddmDataProviderInstanceLink.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return ddmDataProviderInstanceLink;
	}

	/**
	 * Removes the ddm data provider instance link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link that was removed
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink remove(long dataProviderInstanceLinkId)
		throws NoSuchDataProviderInstanceLinkException {

		return remove((Serializable)dataProviderInstanceLinkId);
	}

	/**
	 * Removes the ddm data provider instance link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link that was removed
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink remove(Serializable primaryKey)
		throws NoSuchDataProviderInstanceLinkException {

		Session session = null;

		try {
			session = openSession();

			DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
				(DDMDataProviderInstanceLink)session.get(
					DDMDataProviderInstanceLinkImpl.class, primaryKey);

			if (ddmDataProviderInstanceLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDataProviderInstanceLinkException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ddmDataProviderInstanceLink);
		}
		catch (NoSuchDataProviderInstanceLinkException noSuchEntityException) {
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
	protected DDMDataProviderInstanceLink removeImpl(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		if (!ctPersistenceHelper.isRemove(ddmDataProviderInstanceLink)) {
			return ddmDataProviderInstanceLink;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmDataProviderInstanceLink)) {
				ddmDataProviderInstanceLink =
					(DDMDataProviderInstanceLink)session.get(
						DDMDataProviderInstanceLinkImpl.class,
						ddmDataProviderInstanceLink.getPrimaryKeyObj());
			}

			if (ddmDataProviderInstanceLink != null) {
				session.delete(ddmDataProviderInstanceLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmDataProviderInstanceLink != null) {
			clearCache(ddmDataProviderInstanceLink);
		}

		return ddmDataProviderInstanceLink;
	}

	@Override
	public DDMDataProviderInstanceLink updateImpl(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		boolean isNew = ddmDataProviderInstanceLink.isNew();

		if (!(ddmDataProviderInstanceLink instanceof
				DDMDataProviderInstanceLinkModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					ddmDataProviderInstanceLink.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					ddmDataProviderInstanceLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddmDataProviderInstanceLink proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDMDataProviderInstanceLink implementation " +
					ddmDataProviderInstanceLink.getClass());
		}

		DDMDataProviderInstanceLinkModelImpl
			ddmDataProviderInstanceLinkModelImpl =
				(DDMDataProviderInstanceLinkModelImpl)
					ddmDataProviderInstanceLink;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ddmDataProviderInstanceLink)) {
				if (!isNew) {
					session.evict(
						DDMDataProviderInstanceLinkImpl.class,
						ddmDataProviderInstanceLink.getPrimaryKeyObj());
				}

				session.save(ddmDataProviderInstanceLink);

				ddmDataProviderInstanceLink.setNew(false);
			}
			else {
				ddmDataProviderInstanceLink =
					(DDMDataProviderInstanceLink)session.merge(
						ddmDataProviderInstanceLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmDataProviderInstanceLink.getCtCollectionId() != 0) {
			ddmDataProviderInstanceLink.resetOriginalValues();

			return ddmDataProviderInstanceLink;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				ddmDataProviderInstanceLinkModelImpl.getDataProviderInstanceId()
			};

			finderCache.removeResult(
				_finderPathCountByDataProviderInstanceId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByDataProviderInstanceId, args);

			args = new Object[] {
				ddmDataProviderInstanceLinkModelImpl.getStructureId()
			};

			finderCache.removeResult(_finderPathCountByStructureId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByStructureId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ddmDataProviderInstanceLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByDataProviderInstanceId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmDataProviderInstanceLinkModelImpl.
						getOriginalDataProviderInstanceId()
				};

				finderCache.removeResult(
					_finderPathCountByDataProviderInstanceId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDataProviderInstanceId,
					args);

				args = new Object[] {
					ddmDataProviderInstanceLinkModelImpl.
						getDataProviderInstanceId()
				};

				finderCache.removeResult(
					_finderPathCountByDataProviderInstanceId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDataProviderInstanceId,
					args);
			}

			if ((ddmDataProviderInstanceLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByStructureId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmDataProviderInstanceLinkModelImpl.
						getOriginalStructureId()
				};

				finderCache.removeResult(_finderPathCountByStructureId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByStructureId, args);

				args = new Object[] {
					ddmDataProviderInstanceLinkModelImpl.getStructureId()
				};

				finderCache.removeResult(_finderPathCountByStructureId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByStructureId, args);
			}
		}

		entityCache.putResult(
			DDMDataProviderInstanceLinkImpl.class,
			ddmDataProviderInstanceLink.getPrimaryKey(),
			ddmDataProviderInstanceLink, false);

		clearUniqueFindersCache(ddmDataProviderInstanceLinkModelImpl, false);
		cacheUniqueFindersCache(ddmDataProviderInstanceLinkModelImpl);

		ddmDataProviderInstanceLink.resetOriginalValues();

		return ddmDataProviderInstanceLink;
	}

	/**
	 * Returns the ddm data provider instance link with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			fetchByPrimaryKey(primaryKey);

		if (ddmDataProviderInstanceLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDataProviderInstanceLinkException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ddmDataProviderInstanceLink;
	}

	/**
	 * Returns the ddm data provider instance link with the primary key or throws a <code>NoSuchDataProviderInstanceLinkException</code> if it could not be found.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByPrimaryKey(
			long dataProviderInstanceLinkId)
		throws NoSuchDataProviderInstanceLinkException {

		return findByPrimaryKey((Serializable)dataProviderInstanceLinkId);
	}

	/**
	 * Returns the ddm data provider instance link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link, or <code>null</code> if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				DDMDataProviderInstanceLink.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink = null;

		Session session = null;

		try {
			session = openSession();

			ddmDataProviderInstanceLink =
				(DDMDataProviderInstanceLink)session.get(
					DDMDataProviderInstanceLinkImpl.class, primaryKey);

			if (ddmDataProviderInstanceLink != null) {
				cacheResult(ddmDataProviderInstanceLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ddmDataProviderInstanceLink;
	}

	/**
	 * Returns the ddm data provider instance link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link, or <code>null</code> if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByPrimaryKey(
		long dataProviderInstanceLinkId) {

		return fetchByPrimaryKey((Serializable)dataProviderInstanceLinkId);
	}

	@Override
	public Map<Serializable, DDMDataProviderInstanceLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				DDMDataProviderInstanceLink.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMDataProviderInstanceLink> map =
			new HashMap<Serializable, DDMDataProviderInstanceLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
				fetchByPrimaryKey(primaryKey);

			if (ddmDataProviderInstanceLink != null) {
				map.put(primaryKey, ddmDataProviderInstanceLink);
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

			for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
					(List<DDMDataProviderInstanceLink>)query.list()) {

				map.put(
					ddmDataProviderInstanceLink.getPrimaryKeyObj(),
					ddmDataProviderInstanceLink);

				cacheResult(ddmDataProviderInstanceLink);
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
	 * Returns all the ddm data provider instance links.
	 *
	 * @return the ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm data provider instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @return the range of ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findAll(
		int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findAll(
		int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMDataProviderInstanceLink.class);

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

		List<DDMDataProviderInstanceLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMDataProviderInstanceLink>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCELINK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DDMDATAPROVIDERINSTANCELINK;

				sql = sql.concat(
					DDMDataProviderInstanceLinkModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DDMDataProviderInstanceLink>)QueryUtil.list(
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
	 * Removes all the ddm data provider instance links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
				findAll()) {

			remove(ddmDataProviderInstanceLink);
		}
	}

	/**
	 * Returns the number of ddm data provider instance links.
	 *
	 * @return the number of ddm data provider instance links
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMDataProviderInstanceLink.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_DDMDATAPROVIDERINSTANCELINK);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "dataProviderInstanceLinkId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDMDATAPROVIDERINSTANCELINK;
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
		return DDMDataProviderInstanceLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DDMDataProviderInstanceLink";
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
		ctStrictColumnNames.add("dataProviderInstanceId");
		ctStrictColumnNames.add("structureId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("dataProviderInstanceLinkId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"dataProviderInstanceId", "structureId"});
	}

	/**
	 * Initializes the ddm data provider instance link persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DDMDataProviderInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DDMDataProviderInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByDataProviderInstanceId = new FinderPath(
			DDMDataProviderInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByDataProviderInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByDataProviderInstanceId =
			new FinderPath(
				DDMDataProviderInstanceLinkImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByDataProviderInstanceId",
				new String[] {Long.class.getName()},
				DDMDataProviderInstanceLinkModelImpl.
					DATAPROVIDERINSTANCEID_COLUMN_BITMASK);

		_finderPathCountByDataProviderInstanceId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDataProviderInstanceId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByStructureId = new FinderPath(
			DDMDataProviderInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStructureId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByStructureId = new FinderPath(
			DDMDataProviderInstanceLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureId",
			new String[] {Long.class.getName()},
			DDMDataProviderInstanceLinkModelImpl.STRUCTUREID_COLUMN_BITMASK);

		_finderPathCountByStructureId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByStructureId", new String[] {Long.class.getName()});

		_finderPathFetchByD_S = new FinderPath(
			DDMDataProviderInstanceLinkImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByD_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			DDMDataProviderInstanceLinkModelImpl.
				DATAPROVIDERINSTANCEID_COLUMN_BITMASK |
			DDMDataProviderInstanceLinkModelImpl.STRUCTUREID_COLUMN_BITMASK);

		_finderPathCountByD_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_S",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			DDMDataProviderInstanceLinkImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DDMDATAPROVIDERINSTANCELINK =
		"SELECT ddmDataProviderInstanceLink FROM DDMDataProviderInstanceLink ddmDataProviderInstanceLink";

	private static final String _SQL_SELECT_DDMDATAPROVIDERINSTANCELINK_WHERE =
		"SELECT ddmDataProviderInstanceLink FROM DDMDataProviderInstanceLink ddmDataProviderInstanceLink WHERE ";

	private static final String _SQL_COUNT_DDMDATAPROVIDERINSTANCELINK =
		"SELECT COUNT(ddmDataProviderInstanceLink) FROM DDMDataProviderInstanceLink ddmDataProviderInstanceLink";

	private static final String _SQL_COUNT_DDMDATAPROVIDERINSTANCELINK_WHERE =
		"SELECT COUNT(ddmDataProviderInstanceLink) FROM DDMDataProviderInstanceLink ddmDataProviderInstanceLink WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"ddmDataProviderInstanceLink.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DDMDataProviderInstanceLink exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDMDataProviderInstanceLink exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderInstanceLinkPersistenceImpl.class);

	static {
		try {
			Class.forName(DDMPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}