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

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceVersionException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersionTable;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceVersionPersistence;
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
import com.liferay.portal.kernel.util.SetUtil;

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
 * The persistence implementation for the ddm form instance version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DDMFormInstanceVersionPersistence.class)
public class DDMFormInstanceVersionPersistenceImpl
	extends BasePersistenceImpl<DDMFormInstanceVersion>
	implements DDMFormInstanceVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDMFormInstanceVersionUtil</code> to access the ddm form instance version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDMFormInstanceVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByFormInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByFormInstanceId;
	private FinderPath _finderPathCountByFormInstanceId;

	/**
	 * Returns all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId) {

		return findByFormInstanceId(
			formInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @return the range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end) {

		return findByFormInstanceId(formInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return findByFormInstanceId(
			formInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByFormInstanceId;
				finderArgs = new Object[] {formInstanceId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByFormInstanceId;
			finderArgs = new Object[] {
				formInstanceId, start, end, orderByComparator
			};
		}

		List<DDMFormInstanceVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceVersion ddmFormInstanceVersion : list) {
					if (formInstanceId !=
							ddmFormInstanceVersion.getFormInstanceId()) {

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

			sb.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE);

			sb.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFormInstanceVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

				list = (List<DDMFormInstanceVersion>)QueryUtil.list(
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
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByFormInstanceId_First(
			long formInstanceId,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {

		DDMFormInstanceVersion ddmFormInstanceVersion =
			fetchByFormInstanceId_First(formInstanceId, orderByComparator);

		if (ddmFormInstanceVersion != null) {
			return ddmFormInstanceVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceId=");
		sb.append(formInstanceId);

		sb.append("}");

		throw new NoSuchFormInstanceVersionException(sb.toString());
	}

	/**
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByFormInstanceId_First(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		List<DDMFormInstanceVersion> list = findByFormInstanceId(
			formInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByFormInstanceId_Last(
			long formInstanceId,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {

		DDMFormInstanceVersion ddmFormInstanceVersion =
			fetchByFormInstanceId_Last(formInstanceId, orderByComparator);

		if (ddmFormInstanceVersion != null) {
			return ddmFormInstanceVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceId=");
		sb.append(formInstanceId);

		sb.append("}");

		throw new NoSuchFormInstanceVersionException(sb.toString());
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByFormInstanceId_Last(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		int count = countByFormInstanceId(formInstanceId);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceVersion> list = findByFormInstanceId(
			formInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance versions before and after the current ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceVersionId the primary key of the current ddm form instance version
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion[] findByFormInstanceId_PrevAndNext(
			long formInstanceVersionId, long formInstanceId,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {

		DDMFormInstanceVersion ddmFormInstanceVersion = findByPrimaryKey(
			formInstanceVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceVersion[] array = new DDMFormInstanceVersionImpl[3];

			array[0] = getByFormInstanceId_PrevAndNext(
				session, ddmFormInstanceVersion, formInstanceId,
				orderByComparator, true);

			array[1] = ddmFormInstanceVersion;

			array[2] = getByFormInstanceId_PrevAndNext(
				session, ddmFormInstanceVersion, formInstanceId,
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

	protected DDMFormInstanceVersion getByFormInstanceId_PrevAndNext(
		Session session, DDMFormInstanceVersion ddmFormInstanceVersion,
		long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE);

		sb.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

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
			sb.append(DDMFormInstanceVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(formInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance versions where formInstanceId = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 */
	@Override
	public void removeByFormInstanceId(long formInstanceId) {
		for (DDMFormInstanceVersion ddmFormInstanceVersion :
				findByFormInstanceId(
					formInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddmFormInstanceVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance versions where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the number of matching ddm form instance versions
	 */
	@Override
	public int countByFormInstanceId(long formInstanceId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFormInstanceId;

			finderArgs = new Object[] {formInstanceId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDMFORMINSTANCEVERSION_WHERE);

			sb.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

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

	private static final String _FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2 =
		"ddmFormInstanceVersion.formInstanceId = ?";

	private FinderPath _finderPathFetchByF_V;
	private FinderPath _finderPathCountByF_V;

	/**
	 * Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or throws a <code>NoSuchFormInstanceVersionException</code> if it could not be found.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByF_V(long formInstanceId, String version)
		throws NoSuchFormInstanceVersionException {

		DDMFormInstanceVersion ddmFormInstanceVersion = fetchByF_V(
			formInstanceId, version);

		if (ddmFormInstanceVersion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("formInstanceId=");
			sb.append(formInstanceId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFormInstanceVersionException(sb.toString());
		}

		return ddmFormInstanceVersion;
	}

	/**
	 * Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByF_V(
		long formInstanceId, String version) {

		return fetchByF_V(formInstanceId, version, true);
	}

	/**
	 * Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByF_V(
		long formInstanceId, String version, boolean useFinderCache) {

		version = Objects.toString(version, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceVersion.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {formInstanceId, version};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByF_V, finderArgs, this);
		}

		if (result instanceof DDMFormInstanceVersion) {
			DDMFormInstanceVersion ddmFormInstanceVersion =
				(DDMFormInstanceVersion)result;

			if ((formInstanceId !=
					ddmFormInstanceVersion.getFormInstanceId()) ||
				!Objects.equals(version, ddmFormInstanceVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE);

			sb.append(_FINDER_COLUMN_F_V_FORMINSTANCEID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_V_VERSION_3);
			}
			else {
				bindVersion = true;

				sb.append(_FINDER_COLUMN_F_V_VERSION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

				if (bindVersion) {
					queryPos.add(version);
				}

				List<DDMFormInstanceVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByF_V, finderArgs, list);
					}
				}
				else {
					DDMFormInstanceVersion ddmFormInstanceVersion = list.get(0);

					result = ddmFormInstanceVersion;

					cacheResult(ddmFormInstanceVersion);
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
			return (DDMFormInstanceVersion)result;
		}
	}

	/**
	 * Removes the ddm form instance version where formInstanceId = &#63; and version = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the ddm form instance version that was removed
	 */
	@Override
	public DDMFormInstanceVersion removeByF_V(
			long formInstanceId, String version)
		throws NoSuchFormInstanceVersionException {

		DDMFormInstanceVersion ddmFormInstanceVersion = findByF_V(
			formInstanceId, version);

		return remove(ddmFormInstanceVersion);
	}

	/**
	 * Returns the number of ddm form instance versions where formInstanceId = &#63; and version = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the number of matching ddm form instance versions
	 */
	@Override
	public int countByF_V(long formInstanceId, String version) {
		version = Objects.toString(version, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByF_V;

			finderArgs = new Object[] {formInstanceId, version};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMFORMINSTANCEVERSION_WHERE);

			sb.append(_FINDER_COLUMN_F_V_FORMINSTANCEID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_V_VERSION_3);
			}
			else {
				bindVersion = true;

				sb.append(_FINDER_COLUMN_F_V_VERSION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

				if (bindVersion) {
					queryPos.add(version);
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

	private static final String _FINDER_COLUMN_F_V_FORMINSTANCEID_2 =
		"ddmFormInstanceVersion.formInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_F_V_VERSION_2 =
		"ddmFormInstanceVersion.version = ?";

	private static final String _FINDER_COLUMN_F_V_VERSION_3 =
		"(ddmFormInstanceVersion.version IS NULL OR ddmFormInstanceVersion.version = '')";

	private FinderPath _finderPathWithPaginationFindByF_S;
	private FinderPath _finderPathWithoutPaginationFindByF_S;
	private FinderPath _finderPathCountByF_S;

	/**
	 * Returns all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @return the matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status) {

		return findByF_S(
			formInstanceId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @return the range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status, int start, int end) {

		return findByF_S(formInstanceId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return findByF_S(
			formInstanceId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByF_S;
				finderArgs = new Object[] {formInstanceId, status};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByF_S;
			finderArgs = new Object[] {
				formInstanceId, status, start, end, orderByComparator
			};
		}

		List<DDMFormInstanceVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceVersion ddmFormInstanceVersion : list) {
					if ((formInstanceId !=
							ddmFormInstanceVersion.getFormInstanceId()) ||
						(status != ddmFormInstanceVersion.getStatus())) {

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

			sb.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE);

			sb.append(_FINDER_COLUMN_F_S_FORMINSTANCEID_2);

			sb.append(_FINDER_COLUMN_F_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFormInstanceVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

				queryPos.add(status);

				list = (List<DDMFormInstanceVersion>)QueryUtil.list(
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
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByF_S_First(
			long formInstanceId, int status,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {

		DDMFormInstanceVersion ddmFormInstanceVersion = fetchByF_S_First(
			formInstanceId, status, orderByComparator);

		if (ddmFormInstanceVersion != null) {
			return ddmFormInstanceVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceId=");
		sb.append(formInstanceId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFormInstanceVersionException(sb.toString());
	}

	/**
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByF_S_First(
		long formInstanceId, int status,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		List<DDMFormInstanceVersion> list = findByF_S(
			formInstanceId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByF_S_Last(
			long formInstanceId, int status,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {

		DDMFormInstanceVersion ddmFormInstanceVersion = fetchByF_S_Last(
			formInstanceId, status, orderByComparator);

		if (ddmFormInstanceVersion != null) {
			return ddmFormInstanceVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceId=");
		sb.append(formInstanceId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFormInstanceVersionException(sb.toString());
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByF_S_Last(
		long formInstanceId, int status,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		int count = countByF_S(formInstanceId, status);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceVersion> list = findByF_S(
			formInstanceId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance versions before and after the current ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceVersionId the primary key of the current ddm form instance version
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion[] findByF_S_PrevAndNext(
			long formInstanceVersionId, long formInstanceId, int status,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {

		DDMFormInstanceVersion ddmFormInstanceVersion = findByPrimaryKey(
			formInstanceVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceVersion[] array = new DDMFormInstanceVersionImpl[3];

			array[0] = getByF_S_PrevAndNext(
				session, ddmFormInstanceVersion, formInstanceId, status,
				orderByComparator, true);

			array[1] = ddmFormInstanceVersion;

			array[2] = getByF_S_PrevAndNext(
				session, ddmFormInstanceVersion, formInstanceId, status,
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

	protected DDMFormInstanceVersion getByF_S_PrevAndNext(
		Session session, DDMFormInstanceVersion ddmFormInstanceVersion,
		long formInstanceId, int status,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE);

		sb.append(_FINDER_COLUMN_F_S_FORMINSTANCEID_2);

		sb.append(_FINDER_COLUMN_F_S_STATUS_2);

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
			sb.append(DDMFormInstanceVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(formInstanceId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance versions where formInstanceId = &#63; and status = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 */
	@Override
	public void removeByF_S(long formInstanceId, int status) {
		for (DDMFormInstanceVersion ddmFormInstanceVersion :
				findByF_S(
					formInstanceId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ddmFormInstanceVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @return the number of matching ddm form instance versions
	 */
	@Override
	public int countByF_S(long formInstanceId, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByF_S;

			finderArgs = new Object[] {formInstanceId, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMFORMINSTANCEVERSION_WHERE);

			sb.append(_FINDER_COLUMN_F_S_FORMINSTANCEID_2);

			sb.append(_FINDER_COLUMN_F_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_F_S_FORMINSTANCEID_2 =
		"ddmFormInstanceVersion.formInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_F_S_STATUS_2 =
		"ddmFormInstanceVersion.status = ?";

	public DDMFormInstanceVersionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("settings", "settings_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DDMFormInstanceVersion.class);

		setModelImplClass(DDMFormInstanceVersionImpl.class);
		setModelPKClass(long.class);

		setTable(DDMFormInstanceVersionTable.INSTANCE);
	}

	/**
	 * Caches the ddm form instance version in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceVersion the ddm form instance version
	 */
	@Override
	public void cacheResult(DDMFormInstanceVersion ddmFormInstanceVersion) {
		if (ddmFormInstanceVersion.getCtCollectionId() != 0) {
			ddmFormInstanceVersion.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			DDMFormInstanceVersionImpl.class,
			ddmFormInstanceVersion.getPrimaryKey(), ddmFormInstanceVersion);

		finderCache.putResult(
			_finderPathFetchByF_V,
			new Object[] {
				ddmFormInstanceVersion.getFormInstanceId(),
				ddmFormInstanceVersion.getVersion()
			},
			ddmFormInstanceVersion);

		ddmFormInstanceVersion.resetOriginalValues();
	}

	/**
	 * Caches the ddm form instance versions in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceVersions the ddm form instance versions
	 */
	@Override
	public void cacheResult(
		List<DDMFormInstanceVersion> ddmFormInstanceVersions) {

		for (DDMFormInstanceVersion ddmFormInstanceVersion :
				ddmFormInstanceVersions) {

			if (ddmFormInstanceVersion.getCtCollectionId() != 0) {
				ddmFormInstanceVersion.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					DDMFormInstanceVersionImpl.class,
					ddmFormInstanceVersion.getPrimaryKey()) == null) {

				cacheResult(ddmFormInstanceVersion);
			}
			else {
				ddmFormInstanceVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ddm form instance versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMFormInstanceVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ddm form instance version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMFormInstanceVersion ddmFormInstanceVersion) {
		entityCache.removeResult(
			DDMFormInstanceVersionImpl.class,
			ddmFormInstanceVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DDMFormInstanceVersionModelImpl)ddmFormInstanceVersion, true);
	}

	@Override
	public void clearCache(
		List<DDMFormInstanceVersion> ddmFormInstanceVersions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMFormInstanceVersion ddmFormInstanceVersion :
				ddmFormInstanceVersions) {

			entityCache.removeResult(
				DDMFormInstanceVersionImpl.class,
				ddmFormInstanceVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(DDMFormInstanceVersionModelImpl)ddmFormInstanceVersion, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				DDMFormInstanceVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMFormInstanceVersionModelImpl ddmFormInstanceVersionModelImpl) {

		Object[] args = new Object[] {
			ddmFormInstanceVersionModelImpl.getFormInstanceId(),
			ddmFormInstanceVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByF_V, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByF_V, args, ddmFormInstanceVersionModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		DDMFormInstanceVersionModelImpl ddmFormInstanceVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ddmFormInstanceVersionModelImpl.getFormInstanceId(),
				ddmFormInstanceVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByF_V, args);
			finderCache.removeResult(_finderPathFetchByF_V, args);
		}

		if ((ddmFormInstanceVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByF_V.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ddmFormInstanceVersionModelImpl.getOriginalFormInstanceId(),
				ddmFormInstanceVersionModelImpl.getOriginalVersion()
			};

			finderCache.removeResult(_finderPathCountByF_V, args);
			finderCache.removeResult(_finderPathFetchByF_V, args);
		}
	}

	/**
	 * Creates a new ddm form instance version with the primary key. Does not add the ddm form instance version to the database.
	 *
	 * @param formInstanceVersionId the primary key for the new ddm form instance version
	 * @return the new ddm form instance version
	 */
	@Override
	public DDMFormInstanceVersion create(long formInstanceVersionId) {
		DDMFormInstanceVersion ddmFormInstanceVersion =
			new DDMFormInstanceVersionImpl();

		ddmFormInstanceVersion.setNew(true);
		ddmFormInstanceVersion.setPrimaryKey(formInstanceVersionId);

		ddmFormInstanceVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ddmFormInstanceVersion;
	}

	/**
	 * Removes the ddm form instance version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version that was removed
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion remove(long formInstanceVersionId)
		throws NoSuchFormInstanceVersionException {

		return remove((Serializable)formInstanceVersionId);
	}

	/**
	 * Removes the ddm form instance version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddm form instance version
	 * @return the ddm form instance version that was removed
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion remove(Serializable primaryKey)
		throws NoSuchFormInstanceVersionException {

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceVersion ddmFormInstanceVersion =
				(DDMFormInstanceVersion)session.get(
					DDMFormInstanceVersionImpl.class, primaryKey);

			if (ddmFormInstanceVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFormInstanceVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ddmFormInstanceVersion);
		}
		catch (NoSuchFormInstanceVersionException noSuchEntityException) {
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
	protected DDMFormInstanceVersion removeImpl(
		DDMFormInstanceVersion ddmFormInstanceVersion) {

		if (!ctPersistenceHelper.isRemove(ddmFormInstanceVersion)) {
			return ddmFormInstanceVersion;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmFormInstanceVersion)) {
				ddmFormInstanceVersion = (DDMFormInstanceVersion)session.get(
					DDMFormInstanceVersionImpl.class,
					ddmFormInstanceVersion.getPrimaryKeyObj());
			}

			if (ddmFormInstanceVersion != null) {
				session.delete(ddmFormInstanceVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmFormInstanceVersion != null) {
			clearCache(ddmFormInstanceVersion);
		}

		return ddmFormInstanceVersion;
	}

	@Override
	public DDMFormInstanceVersion updateImpl(
		DDMFormInstanceVersion ddmFormInstanceVersion) {

		boolean isNew = ddmFormInstanceVersion.isNew();

		if (!(ddmFormInstanceVersion instanceof
				DDMFormInstanceVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ddmFormInstanceVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ddmFormInstanceVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddmFormInstanceVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDMFormInstanceVersion implementation " +
					ddmFormInstanceVersion.getClass());
		}

		DDMFormInstanceVersionModelImpl ddmFormInstanceVersionModelImpl =
			(DDMFormInstanceVersionModelImpl)ddmFormInstanceVersion;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ddmFormInstanceVersion)) {
				if (!isNew) {
					session.evict(
						DDMFormInstanceVersionImpl.class,
						ddmFormInstanceVersion.getPrimaryKeyObj());
				}

				session.save(ddmFormInstanceVersion);

				ddmFormInstanceVersion.setNew(false);
			}
			else {
				ddmFormInstanceVersion = (DDMFormInstanceVersion)session.merge(
					ddmFormInstanceVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmFormInstanceVersion.getCtCollectionId() != 0) {
			ddmFormInstanceVersion.resetOriginalValues();

			return ddmFormInstanceVersion;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				ddmFormInstanceVersionModelImpl.getFormInstanceId()
			};

			finderCache.removeResult(_finderPathCountByFormInstanceId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFormInstanceId, args);

			args = new Object[] {
				ddmFormInstanceVersionModelImpl.getFormInstanceId(),
				ddmFormInstanceVersionModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByF_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByF_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ddmFormInstanceVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFormInstanceId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmFormInstanceVersionModelImpl.getOriginalFormInstanceId()
				};

				finderCache.removeResult(
					_finderPathCountByFormInstanceId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFormInstanceId, args);

				args = new Object[] {
					ddmFormInstanceVersionModelImpl.getFormInstanceId()
				};

				finderCache.removeResult(
					_finderPathCountByFormInstanceId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFormInstanceId, args);
			}

			if ((ddmFormInstanceVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByF_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmFormInstanceVersionModelImpl.getOriginalFormInstanceId(),
					ddmFormInstanceVersionModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByF_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByF_S, args);

				args = new Object[] {
					ddmFormInstanceVersionModelImpl.getFormInstanceId(),
					ddmFormInstanceVersionModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByF_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByF_S, args);
			}
		}

		entityCache.putResult(
			DDMFormInstanceVersionImpl.class,
			ddmFormInstanceVersion.getPrimaryKey(), ddmFormInstanceVersion,
			false);

		clearUniqueFindersCache(ddmFormInstanceVersionModelImpl, false);
		cacheUniqueFindersCache(ddmFormInstanceVersionModelImpl);

		ddmFormInstanceVersion.resetOriginalValues();

		return ddmFormInstanceVersion;
	}

	/**
	 * Returns the ddm form instance version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm form instance version
	 * @return the ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFormInstanceVersionException {

		DDMFormInstanceVersion ddmFormInstanceVersion = fetchByPrimaryKey(
			primaryKey);

		if (ddmFormInstanceVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFormInstanceVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ddmFormInstanceVersion;
	}

	/**
	 * Returns the ddm form instance version with the primary key or throws a <code>NoSuchFormInstanceVersionException</code> if it could not be found.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByPrimaryKey(long formInstanceVersionId)
		throws NoSuchFormInstanceVersionException {

		return findByPrimaryKey((Serializable)formInstanceVersionId);
	}

	/**
	 * Returns the ddm form instance version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm form instance version
	 * @return the ddm form instance version, or <code>null</code> if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				DDMFormInstanceVersion.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		DDMFormInstanceVersion ddmFormInstanceVersion = null;

		Session session = null;

		try {
			session = openSession();

			ddmFormInstanceVersion = (DDMFormInstanceVersion)session.get(
				DDMFormInstanceVersionImpl.class, primaryKey);

			if (ddmFormInstanceVersion != null) {
				cacheResult(ddmFormInstanceVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ddmFormInstanceVersion;
	}

	/**
	 * Returns the ddm form instance version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version, or <code>null</code> if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByPrimaryKey(
		long formInstanceVersionId) {

		return fetchByPrimaryKey((Serializable)formInstanceVersionId);
	}

	@Override
	public Map<Serializable, DDMFormInstanceVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				DDMFormInstanceVersion.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMFormInstanceVersion> map =
			new HashMap<Serializable, DDMFormInstanceVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMFormInstanceVersion ddmFormInstanceVersion = fetchByPrimaryKey(
				primaryKey);

			if (ddmFormInstanceVersion != null) {
				map.put(primaryKey, ddmFormInstanceVersion);
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

			for (DDMFormInstanceVersion ddmFormInstanceVersion :
					(List<DDMFormInstanceVersion>)query.list()) {

				map.put(
					ddmFormInstanceVersion.getPrimaryKeyObj(),
					ddmFormInstanceVersion);

				cacheResult(ddmFormInstanceVersion);
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
	 * Returns all the ddm form instance versions.
	 *
	 * @return the ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @return the range of ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findAll(
		int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findAll(
		int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceVersion.class);

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

		List<DDMFormInstanceVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DDMFORMINSTANCEVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DDMFORMINSTANCEVERSION;

				sql = sql.concat(DDMFormInstanceVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DDMFormInstanceVersion>)QueryUtil.list(
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
	 * Removes all the ddm form instance versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMFormInstanceVersion ddmFormInstanceVersion : findAll()) {
			remove(ddmFormInstanceVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance versions.
	 *
	 * @return the number of ddm form instance versions
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceVersion.class);

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
					_SQL_COUNT_DDMFORMINSTANCEVERSION);

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
		return "formInstanceVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDMFORMINSTANCEVERSION;
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
		return DDMFormInstanceVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DDMFormInstanceVersion";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctStrictColumnNames.add("formInstanceId");
		ctStrictColumnNames.add("structureVersionId");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("description");
		ctStrictColumnNames.add("settings_");
		ctStrictColumnNames.add("version");
		ctStrictColumnNames.add("status");
		ctStrictColumnNames.add("statusByUserId");
		ctStrictColumnNames.add("statusByUserName");
		ctStrictColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("formInstanceVersionId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"formInstanceId", "version"});
	}

	/**
	 * Initializes the ddm form instance version persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByFormInstanceId = new FinderPath(
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFormInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFormInstanceId = new FinderPath(
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFormInstanceId",
			new String[] {Long.class.getName()},
			DDMFormInstanceVersionModelImpl.FORMINSTANCEID_COLUMN_BITMASK);

		_finderPathCountByFormInstanceId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFormInstanceId", new String[] {Long.class.getName()});

		_finderPathFetchByF_V = new FinderPath(
			DDMFormInstanceVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByF_V",
			new String[] {Long.class.getName(), String.class.getName()},
			DDMFormInstanceVersionModelImpl.FORMINSTANCEID_COLUMN_BITMASK |
			DDMFormInstanceVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByF_V = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_V",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByF_S = new FinderPath(
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByF_S = new FinderPath(
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			DDMFormInstanceVersionModelImpl.FORMINSTANCEID_COLUMN_BITMASK |
			DDMFormInstanceVersionModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByF_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_S",
			new String[] {Long.class.getName(), Integer.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DDMFormInstanceVersionImpl.class.getName());
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

	private static final String _SQL_SELECT_DDMFORMINSTANCEVERSION =
		"SELECT ddmFormInstanceVersion FROM DDMFormInstanceVersion ddmFormInstanceVersion";

	private static final String _SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE =
		"SELECT ddmFormInstanceVersion FROM DDMFormInstanceVersion ddmFormInstanceVersion WHERE ";

	private static final String _SQL_COUNT_DDMFORMINSTANCEVERSION =
		"SELECT COUNT(ddmFormInstanceVersion) FROM DDMFormInstanceVersion ddmFormInstanceVersion";

	private static final String _SQL_COUNT_DDMFORMINSTANCEVERSION_WHERE =
		"SELECT COUNT(ddmFormInstanceVersion) FROM DDMFormInstanceVersion ddmFormInstanceVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"ddmFormInstanceVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DDMFormInstanceVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDMFormInstanceVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"settings"});

	static {
		try {
			Class.forName(DDMPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}