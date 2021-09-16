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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchAutoResolutionInfoException;
import com.liferay.change.tracking.model.CTAutoResolutionInfo;
import com.liferay.change.tracking.model.CTAutoResolutionInfoTable;
import com.liferay.change.tracking.model.impl.CTAutoResolutionInfoImpl;
import com.liferay.change.tracking.model.impl.CTAutoResolutionInfoModelImpl;
import com.liferay.change.tracking.service.persistence.CTAutoResolutionInfoPersistence;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ct auto resolution info service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {CTAutoResolutionInfoPersistence.class, BasePersistence.class}
)
public class CTAutoResolutionInfoPersistenceImpl
	extends BasePersistenceImpl<CTAutoResolutionInfo>
	implements CTAutoResolutionInfoPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTAutoResolutionInfoUtil</code> to access the ct auto resolution info persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTAutoResolutionInfoImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCtCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByCtCollectionId;
	private FinderPath _finderPathCountByCtCollectionId;

	/**
	 * Returns all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCtCollectionId(
		long ctCollectionId) {

		return findByCtCollectionId(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return findByCtCollectionId(ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCtCollectionId;
				finderArgs = new Object[] {ctCollectionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCtCollectionId;
			finderArgs = new Object[] {
				ctCollectionId, start, end, orderByComparator
			};
		}

		List<CTAutoResolutionInfo> list = null;

		if (useFinderCache) {
			list = (List<CTAutoResolutionInfo>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTAutoResolutionInfo ctAutoResolutionInfo : list) {
					if (ctCollectionId !=
							ctAutoResolutionInfo.getCtCollectionId()) {

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

			sb.append(_SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				list = (List<CTAutoResolutionInfo>)QueryUtil.list(
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
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByCtCollectionId_First(
			long ctCollectionId,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);

		if (ctAutoResolutionInfo != null) {
			return ctAutoResolutionInfo;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchAutoResolutionInfoException(sb.toString());
	}

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByCtCollectionId_First(
		long ctCollectionId,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		List<CTAutoResolutionInfo> list = findByCtCollectionId(
			ctCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByCtCollectionId_Last(
			long ctCollectionId,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = fetchByCtCollectionId_Last(
			ctCollectionId, orderByComparator);

		if (ctAutoResolutionInfo != null) {
			return ctAutoResolutionInfo;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchAutoResolutionInfoException(sb.toString());
	}

	/**
	 * Returns the last ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByCtCollectionId_Last(
		long ctCollectionId,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		int count = countByCtCollectionId(ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<CTAutoResolutionInfo> list = findByCtCollectionId(
			ctCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct auto resolution infos before and after the current ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the current ct auto resolution info
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo[] findByCtCollectionId_PrevAndNext(
			long ctAutoResolutionInfoId, long ctCollectionId,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = findByPrimaryKey(
			ctAutoResolutionInfoId);

		Session session = null;

		try {
			session = openSession();

			CTAutoResolutionInfo[] array = new CTAutoResolutionInfoImpl[3];

			array[0] = getByCtCollectionId_PrevAndNext(
				session, ctAutoResolutionInfo, ctCollectionId,
				orderByComparator, true);

			array[1] = ctAutoResolutionInfo;

			array[2] = getByCtCollectionId_PrevAndNext(
				session, ctAutoResolutionInfo, ctCollectionId,
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

	protected CTAutoResolutionInfo getByCtCollectionId_PrevAndNext(
		Session session, CTAutoResolutionInfo ctAutoResolutionInfo,
		long ctCollectionId,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
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

		sb.append(_SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE);

		sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

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
			sb.append(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ctAutoResolutionInfo)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTAutoResolutionInfo> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct auto resolution infos where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByCtCollectionId(long ctCollectionId) {
		for (CTAutoResolutionInfo ctAutoResolutionInfo :
				findByCtCollectionId(
					ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctAutoResolutionInfo);
		}
	}

	/**
	 * Returns the number of ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct auto resolution infos
	 */
	@Override
	public int countByCtCollectionId(long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByCtCollectionId;

		Object[] finderArgs = new Object[] {ctCollectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTAUTORESOLUTIONINFO_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

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

	private static final String _FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2 =
		"ctAutoResolutionInfo.ctCollectionId = ?";

	private FinderPath _finderPathWithPaginationFindByC_MCNI_SMCPK;
	private FinderPath _finderPathWithoutPaginationFindByC_MCNI_SMCPK;
	private FinderPath _finderPathCountByC_MCNI_SMCPK;
	private FinderPath _finderPathWithPaginationCountByC_MCNI_SMCPK;

	/**
	 * Returns all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @return the matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
		int start, int end) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPK, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_MCNI_SMCPK;
				finderArgs = new Object[] {
					ctCollectionId, modelClassNameId, sourceModelClassPK
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_MCNI_SMCPK;
			finderArgs = new Object[] {
				ctCollectionId, modelClassNameId, sourceModelClassPK, start,
				end, orderByComparator
			};
		}

		List<CTAutoResolutionInfo> list = null;

		if (useFinderCache) {
			list = (List<CTAutoResolutionInfo>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTAutoResolutionInfo ctAutoResolutionInfo : list) {
					if ((ctCollectionId !=
							ctAutoResolutionInfo.getCtCollectionId()) ||
						(modelClassNameId !=
							ctAutoResolutionInfo.getModelClassNameId()) ||
						(sourceModelClassPK !=
							ctAutoResolutionInfo.getSourceModelClassPK())) {

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

			sb.append(_SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				queryPos.add(sourceModelClassPK);

				list = (List<CTAutoResolutionInfo>)QueryUtil.list(
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
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByC_MCNI_SMCPK_First(
			long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = fetchByC_MCNI_SMCPK_First(
			ctCollectionId, modelClassNameId, sourceModelClassPK,
			orderByComparator);

		if (ctAutoResolutionInfo != null) {
			return ctAutoResolutionInfo;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append(", modelClassNameId=");
		sb.append(modelClassNameId);

		sb.append(", sourceModelClassPK=");
		sb.append(sourceModelClassPK);

		sb.append("}");

		throw new NoSuchAutoResolutionInfoException(sb.toString());
	}

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByC_MCNI_SMCPK_First(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		List<CTAutoResolutionInfo> list = findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct auto resolution info in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByC_MCNI_SMCPK_Last(
			long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = fetchByC_MCNI_SMCPK_Last(
			ctCollectionId, modelClassNameId, sourceModelClassPK,
			orderByComparator);

		if (ctAutoResolutionInfo != null) {
			return ctAutoResolutionInfo;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append(", modelClassNameId=");
		sb.append(modelClassNameId);

		sb.append(", sourceModelClassPK=");
		sb.append(sourceModelClassPK);

		sb.append("}");

		throw new NoSuchAutoResolutionInfoException(sb.toString());
	}

	/**
	 * Returns the last ct auto resolution info in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByC_MCNI_SMCPK_Last(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		int count = countByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPK);

		if (count == 0) {
			return null;
		}

		List<CTAutoResolutionInfo> list = findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPK, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct auto resolution infos before and after the current ct auto resolution info in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the current ct auto resolution info
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo[] findByC_MCNI_SMCPK_PrevAndNext(
			long ctAutoResolutionInfoId, long ctCollectionId,
			long modelClassNameId, long sourceModelClassPK,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = findByPrimaryKey(
			ctAutoResolutionInfoId);

		Session session = null;

		try {
			session = openSession();

			CTAutoResolutionInfo[] array = new CTAutoResolutionInfoImpl[3];

			array[0] = getByC_MCNI_SMCPK_PrevAndNext(
				session, ctAutoResolutionInfo, ctCollectionId, modelClassNameId,
				sourceModelClassPK, orderByComparator, true);

			array[1] = ctAutoResolutionInfo;

			array[2] = getByC_MCNI_SMCPK_PrevAndNext(
				session, ctAutoResolutionInfo, ctCollectionId, modelClassNameId,
				sourceModelClassPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTAutoResolutionInfo getByC_MCNI_SMCPK_PrevAndNext(
		Session session, CTAutoResolutionInfo ctAutoResolutionInfo,
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE);

		sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_2);

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
			sb.append(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ctCollectionId);

		queryPos.add(modelClassNameId);

		queryPos.add(sourceModelClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ctAutoResolutionInfo)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTAutoResolutionInfo> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPKs the source model class pks
	 * @return the matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId,
		long[] sourceModelClassPKs) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPKs,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPKs the source model class pks
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long[] sourceModelClassPKs,
		int start, int end) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPKs, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPKs the source model class pks
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long[] sourceModelClassPKs,
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPKs, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long[] sourceModelClassPKs,
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean useFinderCache) {

		if (sourceModelClassPKs == null) {
			sourceModelClassPKs = new long[0];
		}
		else if (sourceModelClassPKs.length > 1) {
			sourceModelClassPKs = ArrayUtil.sortedUnique(sourceModelClassPKs);
		}

		if (sourceModelClassPKs.length == 1) {
			return findByC_MCNI_SMCPK(
				ctCollectionId, modelClassNameId, sourceModelClassPKs[0], start,
				end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					ctCollectionId, modelClassNameId,
					StringUtil.merge(sourceModelClassPKs)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				ctCollectionId, modelClassNameId,
				StringUtil.merge(sourceModelClassPKs), start, end,
				orderByComparator
			};
		}

		List<CTAutoResolutionInfo> list = null;

		if (useFinderCache) {
			list = (List<CTAutoResolutionInfo>)finderCache.getResult(
				_finderPathWithPaginationFindByC_MCNI_SMCPK, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTAutoResolutionInfo ctAutoResolutionInfo : list) {
					if ((ctCollectionId !=
							ctAutoResolutionInfo.getCtCollectionId()) ||
						(modelClassNameId !=
							ctAutoResolutionInfo.getModelClassNameId()) ||
						!ArrayUtil.contains(
							sourceModelClassPKs,
							ctAutoResolutionInfo.getSourceModelClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2);

			if (sourceModelClassPKs.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_7);

				sb.append(StringUtil.merge(sourceModelClassPKs));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				list = (List<CTAutoResolutionInfo>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_MCNI_SMCPK, finderArgs,
						list);
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
	 * Removes all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 */
	@Override
	public void removeByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK) {

		for (CTAutoResolutionInfo ctAutoResolutionInfo :
				findByC_MCNI_SMCPK(
					ctCollectionId, modelClassNameId, sourceModelClassPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ctAutoResolutionInfo);
		}
	}

	/**
	 * Returns the number of ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @return the number of matching ct auto resolution infos
	 */
	@Override
	public int countByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK) {

		FinderPath finderPath = _finderPathCountByC_MCNI_SMCPK;

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelClassNameId, sourceModelClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CTAUTORESOLUTIONINFO_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				queryPos.add(sourceModelClassPK);

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

	/**
	 * Returns the number of ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = any &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPKs the source model class pks
	 * @return the number of matching ct auto resolution infos
	 */
	@Override
	public int countByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId,
		long[] sourceModelClassPKs) {

		if (sourceModelClassPKs == null) {
			sourceModelClassPKs = new long[0];
		}
		else if (sourceModelClassPKs.length > 1) {
			sourceModelClassPKs = ArrayUtil.sortedUnique(sourceModelClassPKs);
		}

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelClassNameId,
			StringUtil.merge(sourceModelClassPKs)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_MCNI_SMCPK, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_CTAUTORESOLUTIONINFO_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2);

			if (sourceModelClassPKs.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_7);

				sb.append(StringUtil.merge(sourceModelClassPKs));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_MCNI_SMCPK, finderArgs,
					count);
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

	private static final String _FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2 =
		"ctAutoResolutionInfo.ctCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2 =
		"ctAutoResolutionInfo.modelClassNameId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_2 =
			"ctAutoResolutionInfo.sourceModelClassPK = ?";

	private static final String
		_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_7 =
			"ctAutoResolutionInfo.sourceModelClassPK IN (";

	public CTAutoResolutionInfoPersistenceImpl() {
		setModelClass(CTAutoResolutionInfo.class);

		setModelImplClass(CTAutoResolutionInfoImpl.class);
		setModelPKClass(long.class);

		setTable(CTAutoResolutionInfoTable.INSTANCE);
	}

	/**
	 * Caches the ct auto resolution info in the entity cache if it is enabled.
	 *
	 * @param ctAutoResolutionInfo the ct auto resolution info
	 */
	@Override
	public void cacheResult(CTAutoResolutionInfo ctAutoResolutionInfo) {
		entityCache.putResult(
			CTAutoResolutionInfoImpl.class,
			ctAutoResolutionInfo.getPrimaryKey(), ctAutoResolutionInfo);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ct auto resolution infos in the entity cache if it is enabled.
	 *
	 * @param ctAutoResolutionInfos the ct auto resolution infos
	 */
	@Override
	public void cacheResult(List<CTAutoResolutionInfo> ctAutoResolutionInfos) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ctAutoResolutionInfos.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CTAutoResolutionInfo ctAutoResolutionInfo :
				ctAutoResolutionInfos) {

			if (entityCache.getResult(
					CTAutoResolutionInfoImpl.class,
					ctAutoResolutionInfo.getPrimaryKey()) == null) {

				cacheResult(ctAutoResolutionInfo);
			}
		}
	}

	/**
	 * Clears the cache for all ct auto resolution infos.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTAutoResolutionInfoImpl.class);

		finderCache.clearCache(CTAutoResolutionInfoImpl.class);
	}

	/**
	 * Clears the cache for the ct auto resolution info.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTAutoResolutionInfo ctAutoResolutionInfo) {
		entityCache.removeResult(
			CTAutoResolutionInfoImpl.class, ctAutoResolutionInfo);
	}

	@Override
	public void clearCache(List<CTAutoResolutionInfo> ctAutoResolutionInfos) {
		for (CTAutoResolutionInfo ctAutoResolutionInfo :
				ctAutoResolutionInfos) {

			entityCache.removeResult(
				CTAutoResolutionInfoImpl.class, ctAutoResolutionInfo);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CTAutoResolutionInfoImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CTAutoResolutionInfoImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new ct auto resolution info with the primary key. Does not add the ct auto resolution info to the database.
	 *
	 * @param ctAutoResolutionInfoId the primary key for the new ct auto resolution info
	 * @return the new ct auto resolution info
	 */
	@Override
	public CTAutoResolutionInfo create(long ctAutoResolutionInfoId) {
		CTAutoResolutionInfo ctAutoResolutionInfo =
			new CTAutoResolutionInfoImpl();

		ctAutoResolutionInfo.setNew(true);
		ctAutoResolutionInfo.setPrimaryKey(ctAutoResolutionInfoId);

		ctAutoResolutionInfo.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctAutoResolutionInfo;
	}

	/**
	 * Removes the ct auto resolution info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info that was removed
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo remove(long ctAutoResolutionInfoId)
		throws NoSuchAutoResolutionInfoException {

		return remove((Serializable)ctAutoResolutionInfoId);
	}

	/**
	 * Removes the ct auto resolution info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct auto resolution info
	 * @return the ct auto resolution info that was removed
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo remove(Serializable primaryKey)
		throws NoSuchAutoResolutionInfoException {

		Session session = null;

		try {
			session = openSession();

			CTAutoResolutionInfo ctAutoResolutionInfo =
				(CTAutoResolutionInfo)session.get(
					CTAutoResolutionInfoImpl.class, primaryKey);

			if (ctAutoResolutionInfo == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAutoResolutionInfoException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctAutoResolutionInfo);
		}
		catch (NoSuchAutoResolutionInfoException noSuchEntityException) {
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
	protected CTAutoResolutionInfo removeImpl(
		CTAutoResolutionInfo ctAutoResolutionInfo) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctAutoResolutionInfo)) {
				ctAutoResolutionInfo = (CTAutoResolutionInfo)session.get(
					CTAutoResolutionInfoImpl.class,
					ctAutoResolutionInfo.getPrimaryKeyObj());
			}

			if (ctAutoResolutionInfo != null) {
				session.delete(ctAutoResolutionInfo);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctAutoResolutionInfo != null) {
			clearCache(ctAutoResolutionInfo);
		}

		return ctAutoResolutionInfo;
	}

	@Override
	public CTAutoResolutionInfo updateImpl(
		CTAutoResolutionInfo ctAutoResolutionInfo) {

		boolean isNew = ctAutoResolutionInfo.isNew();

		if (!(ctAutoResolutionInfo instanceof CTAutoResolutionInfoModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctAutoResolutionInfo.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ctAutoResolutionInfo);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctAutoResolutionInfo proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTAutoResolutionInfo implementation " +
					ctAutoResolutionInfo.getClass());
		}

		CTAutoResolutionInfoModelImpl ctAutoResolutionInfoModelImpl =
			(CTAutoResolutionInfoModelImpl)ctAutoResolutionInfo;

		if (isNew && (ctAutoResolutionInfo.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				ctAutoResolutionInfo.setCreateDate(date);
			}
			else {
				ctAutoResolutionInfo.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ctAutoResolutionInfo);
			}
			else {
				ctAutoResolutionInfo = (CTAutoResolutionInfo)session.merge(
					ctAutoResolutionInfo);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CTAutoResolutionInfoImpl.class, ctAutoResolutionInfoModelImpl,
			false, true);

		if (isNew) {
			ctAutoResolutionInfo.setNew(false);
		}

		ctAutoResolutionInfo.resetOriginalValues();

		return ctAutoResolutionInfo;
	}

	/**
	 * Returns the ct auto resolution info with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct auto resolution info
	 * @return the ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = fetchByPrimaryKey(
			primaryKey);

		if (ctAutoResolutionInfo == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAutoResolutionInfoException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctAutoResolutionInfo;
	}

	/**
	 * Returns the ct auto resolution info with the primary key or throws a <code>NoSuchAutoResolutionInfoException</code> if it could not be found.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByPrimaryKey(long ctAutoResolutionInfoId)
		throws NoSuchAutoResolutionInfoException {

		return findByPrimaryKey((Serializable)ctAutoResolutionInfoId);
	}

	/**
	 * Returns the ct auto resolution info with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info, or <code>null</code> if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByPrimaryKey(long ctAutoResolutionInfoId) {
		return fetchByPrimaryKey((Serializable)ctAutoResolutionInfoId);
	}

	/**
	 * Returns all the ct auto resolution infos.
	 *
	 * @return the ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findAll(
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findAll(
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
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

		List<CTAutoResolutionInfo> list = null;

		if (useFinderCache) {
			list = (List<CTAutoResolutionInfo>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CTAUTORESOLUTIONINFO);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CTAUTORESOLUTIONINFO;

				sql = sql.concat(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CTAutoResolutionInfo>)QueryUtil.list(
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
	 * Removes all the ct auto resolution infos from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTAutoResolutionInfo ctAutoResolutionInfo : findAll()) {
			remove(ctAutoResolutionInfo);
		}
	}

	/**
	 * Returns the number of ct auto resolution infos.
	 *
	 * @return the number of ct auto resolution infos
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
					_SQL_COUNT_CTAUTORESOLUTIONINFO);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ctAutoResolutionInfoId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTAUTORESOLUTIONINFO;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTAutoResolutionInfoModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct auto resolution info persistence.
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

		_finderPathWithPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCtCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ctCollectionId"}, true);

		_finderPathWithoutPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, true);

		_finderPathCountByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, false);

		_finderPathWithPaginationFindByC_MCNI_SMCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_MCNI_SMCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"ctCollectionId", "modelClassNameId", "sourceModelClassPK"
			},
			true);

		_finderPathWithoutPaginationFindByC_MCNI_SMCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_MCNI_SMCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"ctCollectionId", "modelClassNameId", "sourceModelClassPK"
			},
			true);

		_finderPathCountByC_MCNI_SMCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MCNI_SMCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"ctCollectionId", "modelClassNameId", "sourceModelClassPK"
			},
			false);

		_finderPathWithPaginationCountByC_MCNI_SMCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_MCNI_SMCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"ctCollectionId", "modelClassNameId", "sourceModelClassPK"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTAutoResolutionInfoImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CTAUTORESOLUTIONINFO =
		"SELECT ctAutoResolutionInfo FROM CTAutoResolutionInfo ctAutoResolutionInfo";

	private static final String _SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE =
		"SELECT ctAutoResolutionInfo FROM CTAutoResolutionInfo ctAutoResolutionInfo WHERE ";

	private static final String _SQL_COUNT_CTAUTORESOLUTIONINFO =
		"SELECT COUNT(ctAutoResolutionInfo) FROM CTAutoResolutionInfo ctAutoResolutionInfo";

	private static final String _SQL_COUNT_CTAUTORESOLUTIONINFO_WHERE =
		"SELECT COUNT(ctAutoResolutionInfo) FROM CTAutoResolutionInfo ctAutoResolutionInfo WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"ctAutoResolutionInfo.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTAutoResolutionInfo exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTAutoResolutionInfo exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTAutoResolutionInfoPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CTAutoResolutionInfoModelArgumentsResolver
		_ctAutoResolutionInfoModelArgumentsResolver;

}