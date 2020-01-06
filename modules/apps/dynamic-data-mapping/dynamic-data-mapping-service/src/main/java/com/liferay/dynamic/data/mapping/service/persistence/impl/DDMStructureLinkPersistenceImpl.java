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

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLinkImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLinkModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLinkPersistence;
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
import com.liferay.portal.kernel.util.GetterUtil;
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
 * The persistence implementation for the ddm structure link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DDMStructureLinkPersistence.class)
public class DDMStructureLinkPersistenceImpl
	extends BasePersistenceImpl<DDMStructureLink>
	implements DDMStructureLinkPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDMStructureLinkUtil</code> to access the ddm structure link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDMStructureLinkImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByClassNameId;
	private FinderPath _finderPathWithoutPaginationFindByClassNameId;
	private FinderPath _finderPathCountByClassNameId;

	/**
	 * Returns all the ddm structure links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByClassNameId(long classNameId) {
		return findByClassNameId(
			classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end) {

		return findByClassNameId(classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return findByClassNameId(
			classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByClassNameId;
				finderArgs = new Object[] {classNameId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByClassNameId;
			finderArgs = new Object[] {
				classNameId, start, end, orderByComparator
			};
		}

		List<DDMStructureLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLink ddmStructureLink : list) {
					if (classNameId != ddmStructureLink.getClassNameId()) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				list = (List<DDMStructureLink>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink findByClassNameId_First(
			long classNameId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = fetchByClassNameId_First(
			classNameId, orderByComparator);

		if (ddmStructureLink != null) {
			return ddmStructureLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchStructureLinkException(msg.toString());
	}

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByClassNameId_First(
		long classNameId,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		List<DDMStructureLink> list = findByClassNameId(
			classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink findByClassNameId_Last(
			long classNameId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = fetchByClassNameId_Last(
			classNameId, orderByComparator);

		if (ddmStructureLink != null) {
			return ddmStructureLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchStructureLinkException(msg.toString());
	}

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByClassNameId_Last(
		long classNameId,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		int count = countByClassNameId(classNameId);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLink> list = findByClassNameId(
			classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm structure links before and after the current ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param structureLinkId the primary key of the current ddm structure link
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink[] findByClassNameId_PrevAndNext(
			long structureLinkId, long classNameId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = findByPrimaryKey(structureLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLink[] array = new DDMStructureLinkImpl[3];

			array[0] = getByClassNameId_PrevAndNext(
				session, ddmStructureLink, classNameId, orderByComparator,
				true);

			array[1] = ddmStructureLink;

			array[2] = getByClassNameId_PrevAndNext(
				session, ddmStructureLink, classNameId, orderByComparator,
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

	protected DDMStructureLink getByClassNameId_PrevAndNext(
		Session session, DDMStructureLink ddmStructureLink, long classNameId,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

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
			query.append(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmStructureLink)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DDMStructureLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm structure links where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByClassNameId(long classNameId) {
		for (DDMStructureLink ddmStructureLink :
				findByClassNameId(
					classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddmStructureLink);
		}
	}

	/**
	 * Returns the number of ddm structure links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching ddm structure links
	 */
	@Override
	public int countByClassNameId(long classNameId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByClassNameId;

			finderArgs = new Object[] {classNameId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2 =
		"ddmStructureLink.classNameId = ?";

	private FinderPath _finderPathWithPaginationFindByStructureId;
	private FinderPath _finderPathWithoutPaginationFindByStructureId;
	private FinderPath _finderPathCountByStructureId;

	/**
	 * Returns all the ddm structure links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByStructureId(long structureId) {
		return findByStructureId(
			structureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end) {

		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return findByStructureId(
			structureId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLink.class);

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

		List<DDMStructureLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLink ddmStructureLink : list) {
					if (structureId != ddmStructureLink.getStructureId()) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				list = (List<DDMStructureLink>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink findByStructureId_First(
			long structureId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = fetchByStructureId_First(
			structureId, orderByComparator);

		if (ddmStructureLink != null) {
			return ddmStructureLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append("}");

		throw new NoSuchStructureLinkException(msg.toString());
	}

	/**
	 * Returns the first ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByStructureId_First(
		long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		List<DDMStructureLink> list = findByStructureId(
			structureId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink findByStructureId_Last(
			long structureId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = fetchByStructureId_Last(
			structureId, orderByComparator);

		if (ddmStructureLink != null) {
			return ddmStructureLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append("}");

		throw new NoSuchStructureLinkException(msg.toString());
	}

	/**
	 * Returns the last ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByStructureId_Last(
		long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		int count = countByStructureId(structureId);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLink> list = findByStructureId(
			structureId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm structure links before and after the current ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureLinkId the primary key of the current ddm structure link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink[] findByStructureId_PrevAndNext(
			long structureLinkId, long structureId,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = findByPrimaryKey(structureLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLink[] array = new DDMStructureLinkImpl[3];

			array[0] = getByStructureId_PrevAndNext(
				session, ddmStructureLink, structureId, orderByComparator,
				true);

			array[1] = ddmStructureLink;

			array[2] = getByStructureId_PrevAndNext(
				session, ddmStructureLink, structureId, orderByComparator,
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

	protected DDMStructureLink getByStructureId_PrevAndNext(
		Session session, DDMStructureLink ddmStructureLink, long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

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
			query.append(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(structureId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmStructureLink)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DDMStructureLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm structure links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	@Override
	public void removeByStructureId(long structureId) {
		for (DDMStructureLink ddmStructureLink :
				findByStructureId(
					structureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddmStructureLink);
		}
	}

	/**
	 * Returns the number of ddm structure links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching ddm structure links
	 */
	@Override
	public int countByStructureId(long structureId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByStructureId;

			finderArgs = new Object[] {structureId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 =
		"ddmStructureLink.structureId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<DDMStructureLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLink ddmStructureLink : list) {
					if ((classNameId != ddmStructureLink.getClassNameId()) ||
						(classPK != ddmStructureLink.getClassPK())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<DDMStructureLink>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (ddmStructureLink != null) {
			return ddmStructureLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchStructureLinkException(msg.toString());
	}

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		List<DDMStructureLink> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (ddmStructureLink != null) {
			return ddmStructureLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchStructureLinkException(msg.toString());
	}

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLink> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm structure links before and after the current ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param structureLinkId the primary key of the current ddm structure link
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink[] findByC_C_PrevAndNext(
			long structureLinkId, long classNameId, long classPK,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = findByPrimaryKey(structureLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLink[] array = new DDMStructureLinkImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, ddmStructureLink, classNameId, classPK,
				orderByComparator, true);

			array[1] = ddmStructureLink;

			array[2] = getByC_C_PrevAndNext(
				session, ddmStructureLink, classNameId, classPK,
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

	protected DDMStructureLink getByC_C_PrevAndNext(
		Session session, DDMStructureLink ddmStructureLink, long classNameId,
		long classPK, OrderByComparator<DDMStructureLink> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmStructureLink)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DDMStructureLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm structure links where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (DDMStructureLink ddmStructureLink :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddmStructureLink);
		}
	}

	/**
	 * Returns the number of ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching ddm structure links
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"ddmStructureLink.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"ddmStructureLink.classPK = ?";

	private FinderPath _finderPathFetchByC_C_S;
	private FinderPath _finderPathCountByC_C_S;

	/**
	 * Returns the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or throws a <code>NoSuchStructureLinkException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink findByC_C_S(
			long classNameId, long classPK, long structureId)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = fetchByC_C_S(
			classNameId, classPK, structureId);

		if (ddmStructureLink == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchStructureLinkException(msg.toString());
		}

		return ddmStructureLink;
	}

	/**
	 * Returns the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByC_C_S(
		long classNameId, long classPK, long structureId) {

		return fetchByC_C_S(classNameId, classPK, structureId, true);
	}

	/**
	 * Returns the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	@Override
	public DDMStructureLink fetchByC_C_S(
		long classNameId, long classPK, long structureId,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLink.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {classNameId, classPK, structureId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_S, finderArgs, this);
		}

		if (result instanceof DDMStructureLink) {
			DDMStructureLink ddmStructureLink = (DDMStructureLink)result;

			if ((classNameId != ddmStructureLink.getClassNameId()) ||
				(classPK != ddmStructureLink.getClassPK()) ||
				(structureId != ddmStructureLink.getStructureId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_S_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(structureId);

				List<DDMStructureLink> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByC_C_S, finderArgs, list);
					}
				}
				else {
					DDMStructureLink ddmStructureLink = list.get(0);

					result = ddmStructureLink;

					cacheResult(ddmStructureLink);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(
						_finderPathFetchByC_C_S, finderArgs);
				}

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
			return (DDMStructureLink)result;
		}
	}

	/**
	 * Removes the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the ddm structure link that was removed
	 */
	@Override
	public DDMStructureLink removeByC_C_S(
			long classNameId, long classPK, long structureId)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = findByC_C_S(
			classNameId, classPK, structureId);

		return remove(ddmStructureLink);
	}

	/**
	 * Returns the number of ddm structure links where classNameId = &#63; and classPK = &#63; and structureId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the number of matching ddm structure links
	 */
	@Override
	public int countByC_C_S(long classNameId, long classPK, long structureId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_S;

			finderArgs = new Object[] {classNameId, classPK, structureId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_S_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(structureId);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_S_CLASSNAMEID_2 =
		"ddmStructureLink.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_CLASSPK_2 =
		"ddmStructureLink.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_STRUCTUREID_2 =
		"ddmStructureLink.structureId = ?";

	public DDMStructureLinkPersistenceImpl() {
		setModelClass(DDMStructureLink.class);

		setModelImplClass(DDMStructureLinkImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the ddm structure link in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLink the ddm structure link
	 */
	@Override
	public void cacheResult(DDMStructureLink ddmStructureLink) {
		if (ddmStructureLink.getCtCollectionId() != 0) {
			ddmStructureLink.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			entityCacheEnabled, DDMStructureLinkImpl.class,
			ddmStructureLink.getPrimaryKey(), ddmStructureLink);

		finderCache.putResult(
			_finderPathFetchByC_C_S,
			new Object[] {
				ddmStructureLink.getClassNameId(),
				ddmStructureLink.getClassPK(), ddmStructureLink.getStructureId()
			},
			ddmStructureLink);

		ddmStructureLink.resetOriginalValues();
	}

	/**
	 * Caches the ddm structure links in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLinks the ddm structure links
	 */
	@Override
	public void cacheResult(List<DDMStructureLink> ddmStructureLinks) {
		for (DDMStructureLink ddmStructureLink : ddmStructureLinks) {
			if (ddmStructureLink.getCtCollectionId() != 0) {
				ddmStructureLink.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					entityCacheEnabled, DDMStructureLinkImpl.class,
					ddmStructureLink.getPrimaryKey()) == null) {

				cacheResult(ddmStructureLink);
			}
			else {
				ddmStructureLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ddm structure links.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMStructureLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ddm structure link.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMStructureLink ddmStructureLink) {
		entityCache.removeResult(
			entityCacheEnabled, DDMStructureLinkImpl.class,
			ddmStructureLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DDMStructureLinkModelImpl)ddmStructureLink, true);
	}

	@Override
	public void clearCache(List<DDMStructureLink> ddmStructureLinks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMStructureLink ddmStructureLink : ddmStructureLinks) {
			entityCache.removeResult(
				entityCacheEnabled, DDMStructureLinkImpl.class,
				ddmStructureLink.getPrimaryKey());

			clearUniqueFindersCache(
				(DDMStructureLinkModelImpl)ddmStructureLink, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, DDMStructureLinkImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMStructureLinkModelImpl ddmStructureLinkModelImpl) {

		Object[] args = new Object[] {
			ddmStructureLinkModelImpl.getClassNameId(),
			ddmStructureLinkModelImpl.getClassPK(),
			ddmStructureLinkModelImpl.getStructureId()
		};

		finderCache.putResult(
			_finderPathCountByC_C_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C_S, args, ddmStructureLinkModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DDMStructureLinkModelImpl ddmStructureLinkModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ddmStructureLinkModelImpl.getClassNameId(),
				ddmStructureLinkModelImpl.getClassPK(),
				ddmStructureLinkModelImpl.getStructureId()
			};

			finderCache.removeResult(_finderPathCountByC_C_S, args);
			finderCache.removeResult(_finderPathFetchByC_C_S, args);
		}

		if ((ddmStructureLinkModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ddmStructureLinkModelImpl.getOriginalClassNameId(),
				ddmStructureLinkModelImpl.getOriginalClassPK(),
				ddmStructureLinkModelImpl.getOriginalStructureId()
			};

			finderCache.removeResult(_finderPathCountByC_C_S, args);
			finderCache.removeResult(_finderPathFetchByC_C_S, args);
		}
	}

	/**
	 * Creates a new ddm structure link with the primary key. Does not add the ddm structure link to the database.
	 *
	 * @param structureLinkId the primary key for the new ddm structure link
	 * @return the new ddm structure link
	 */
	@Override
	public DDMStructureLink create(long structureLinkId) {
		DDMStructureLink ddmStructureLink = new DDMStructureLinkImpl();

		ddmStructureLink.setNew(true);
		ddmStructureLink.setPrimaryKey(structureLinkId);

		ddmStructureLink.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ddmStructureLink;
	}

	/**
	 * Removes the ddm structure link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureLinkId the primary key of the ddm structure link
	 * @return the ddm structure link that was removed
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink remove(long structureLinkId)
		throws NoSuchStructureLinkException {

		return remove((Serializable)structureLinkId);
	}

	/**
	 * Removes the ddm structure link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddm structure link
	 * @return the ddm structure link that was removed
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink remove(Serializable primaryKey)
		throws NoSuchStructureLinkException {

		Session session = null;

		try {
			session = openSession();

			DDMStructureLink ddmStructureLink = (DDMStructureLink)session.get(
				DDMStructureLinkImpl.class, primaryKey);

			if (ddmStructureLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStructureLinkException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ddmStructureLink);
		}
		catch (NoSuchStructureLinkException noSuchEntityException) {
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
	protected DDMStructureLink removeImpl(DDMStructureLink ddmStructureLink) {
		if (!ctPersistenceHelper.isRemove(ddmStructureLink)) {
			return ddmStructureLink;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmStructureLink)) {
				ddmStructureLink = (DDMStructureLink)session.get(
					DDMStructureLinkImpl.class,
					ddmStructureLink.getPrimaryKeyObj());
			}

			if (ddmStructureLink != null) {
				session.delete(ddmStructureLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmStructureLink != null) {
			clearCache(ddmStructureLink);
		}

		return ddmStructureLink;
	}

	@Override
	public DDMStructureLink updateImpl(DDMStructureLink ddmStructureLink) {
		boolean isNew = ddmStructureLink.isNew();

		if (!(ddmStructureLink instanceof DDMStructureLinkModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ddmStructureLink.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ddmStructureLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddmStructureLink proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDMStructureLink implementation " +
					ddmStructureLink.getClass());
		}

		DDMStructureLinkModelImpl ddmStructureLinkModelImpl =
			(DDMStructureLinkModelImpl)ddmStructureLink;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ddmStructureLink)) {
				if (!isNew) {
					DDMStructureLink oldDDMStructureLink =
						(DDMStructureLink)session.get(
							DDMStructureLinkImpl.class,
							ddmStructureLink.getPrimaryKeyObj());

					if (oldDDMStructureLink != null) {
						session.evict(oldDDMStructureLink);
					}
				}

				session.save(ddmStructureLink);

				ddmStructureLink.setNew(false);
			}
			else {
				ddmStructureLink = (DDMStructureLink)session.merge(
					ddmStructureLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmStructureLink.getCtCollectionId() != 0) {
			ddmStructureLink.resetOriginalValues();

			return ddmStructureLink;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				ddmStructureLinkModelImpl.getClassNameId()
			};

			finderCache.removeResult(_finderPathCountByClassNameId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByClassNameId, args);

			args = new Object[] {ddmStructureLinkModelImpl.getStructureId()};

			finderCache.removeResult(_finderPathCountByStructureId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByStructureId, args);

			args = new Object[] {
				ddmStructureLinkModelImpl.getClassNameId(),
				ddmStructureLinkModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ddmStructureLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByClassNameId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmStructureLinkModelImpl.getOriginalClassNameId()
				};

				finderCache.removeResult(_finderPathCountByClassNameId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByClassNameId, args);

				args = new Object[] {
					ddmStructureLinkModelImpl.getClassNameId()
				};

				finderCache.removeResult(_finderPathCountByClassNameId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByClassNameId, args);
			}

			if ((ddmStructureLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByStructureId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmStructureLinkModelImpl.getOriginalStructureId()
				};

				finderCache.removeResult(_finderPathCountByStructureId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByStructureId, args);

				args = new Object[] {
					ddmStructureLinkModelImpl.getStructureId()
				};

				finderCache.removeResult(_finderPathCountByStructureId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByStructureId, args);
			}

			if ((ddmStructureLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmStructureLinkModelImpl.getOriginalClassNameId(),
					ddmStructureLinkModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					ddmStructureLinkModelImpl.getClassNameId(),
					ddmStructureLinkModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, DDMStructureLinkImpl.class,
			ddmStructureLink.getPrimaryKey(), ddmStructureLink, false);

		clearUniqueFindersCache(ddmStructureLinkModelImpl, false);
		cacheUniqueFindersCache(ddmStructureLinkModelImpl);

		ddmStructureLink.resetOriginalValues();

		return ddmStructureLink;
	}

	/**
	 * Returns the ddm structure link with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm structure link
	 * @return the ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStructureLinkException {

		DDMStructureLink ddmStructureLink = fetchByPrimaryKey(primaryKey);

		if (ddmStructureLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStructureLinkException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ddmStructureLink;
	}

	/**
	 * Returns the ddm structure link with the primary key or throws a <code>NoSuchStructureLinkException</code> if it could not be found.
	 *
	 * @param structureLinkId the primary key of the ddm structure link
	 * @return the ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink findByPrimaryKey(long structureLinkId)
		throws NoSuchStructureLinkException {

		return findByPrimaryKey((Serializable)structureLinkId);
	}

	/**
	 * Returns the ddm structure link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm structure link
	 * @return the ddm structure link, or <code>null</code> if a ddm structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(DDMStructureLink.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		DDMStructureLink ddmStructureLink = null;

		Session session = null;

		try {
			session = openSession();

			ddmStructureLink = (DDMStructureLink)session.get(
				DDMStructureLinkImpl.class, primaryKey);

			if (ddmStructureLink != null) {
				cacheResult(ddmStructureLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ddmStructureLink;
	}

	/**
	 * Returns the ddm structure link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureLinkId the primary key of the ddm structure link
	 * @return the ddm structure link, or <code>null</code> if a ddm structure link with the primary key could not be found
	 */
	@Override
	public DDMStructureLink fetchByPrimaryKey(long structureLinkId) {
		return fetchByPrimaryKey((Serializable)structureLinkId);
	}

	@Override
	public Map<Serializable, DDMStructureLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(DDMStructureLink.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMStructureLink> map =
			new HashMap<Serializable, DDMStructureLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMStructureLink ddmStructureLink = fetchByPrimaryKey(primaryKey);

			if (ddmStructureLink != null) {
				map.put(primaryKey, ddmStructureLink);
			}

			return map;
		}

		StringBundler query = new StringBundler(primaryKeys.size() * 2 + 1);

		query.append(getSelectSQL());
		query.append(" WHERE ");
		query.append(getPKDBName());
		query.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
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

			for (DDMStructureLink ddmStructureLink :
					(List<DDMStructureLink>)q.list()) {

				map.put(ddmStructureLink.getPrimaryKeyObj(), ddmStructureLink);

				cacheResult(ddmStructureLink);
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
	 * Returns all the ddm structure links.
	 *
	 * @return the ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findAll(
		int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm structure links
	 */
	@Override
	public List<DDMStructureLink> findAll(
		int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLink.class);

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

		List<DDMStructureLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLink>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DDMSTRUCTURELINK);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTRUCTURELINK;

				sql = sql.concat(DDMStructureLinkModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<DDMStructureLink>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the ddm structure links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMStructureLink ddmStructureLink : findAll()) {
			remove(ddmStructureLink);
		}
	}

	/**
	 * Returns the number of ddm structure links.
	 *
	 * @return the number of ddm structure links
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLink.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMSTRUCTURELINK);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					finderCache.removeResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY);
				}

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
		return "structureLinkId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDMSTRUCTURELINK;
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
		return DDMStructureLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DDMStructureLink";
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
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("structureId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("structureLinkId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"classNameId", "classPK", "structureId"});
	}

	/**
	 * Initializes the ddm structure link persistence.
	 */
	@Activate
	public void activate() {
		DDMStructureLinkModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		DDMStructureLinkModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByClassNameId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByClassNameId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByClassNameId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByClassNameId",
			new String[] {Long.class.getName()},
			DDMStructureLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByClassNameId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassNameId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByStructureId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStructureId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByStructureId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureId",
			new String[] {Long.class.getName()},
			DDMStructureLinkModelImpl.STRUCTUREID_COLUMN_BITMASK);

		_finderPathCountByStructureId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStructureId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			DDMStructureLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMStructureLinkModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByC_C_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DDMStructureLinkImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DDMStructureLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMStructureLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			DDMStructureLinkModelImpl.STRUCTUREID_COLUMN_BITMASK);

		_finderPathCountByC_C_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DDMStructureLinkImpl.class.getName());
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
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.dynamic.data.mapping.model.DDMStructureLink"),
			true);
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

	private boolean _columnBitmaskEnabled;

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DDMSTRUCTURELINK =
		"SELECT ddmStructureLink FROM DDMStructureLink ddmStructureLink";

	private static final String _SQL_SELECT_DDMSTRUCTURELINK_WHERE =
		"SELECT ddmStructureLink FROM DDMStructureLink ddmStructureLink WHERE ";

	private static final String _SQL_COUNT_DDMSTRUCTURELINK =
		"SELECT COUNT(ddmStructureLink) FROM DDMStructureLink ddmStructureLink";

	private static final String _SQL_COUNT_DDMSTRUCTURELINK_WHERE =
		"SELECT COUNT(ddmStructureLink) FROM DDMStructureLink ddmStructureLink WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStructureLink.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DDMStructureLink exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDMStructureLink exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureLinkPersistenceImpl.class);

	static {
		try {
			Class.forName(DDMPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}