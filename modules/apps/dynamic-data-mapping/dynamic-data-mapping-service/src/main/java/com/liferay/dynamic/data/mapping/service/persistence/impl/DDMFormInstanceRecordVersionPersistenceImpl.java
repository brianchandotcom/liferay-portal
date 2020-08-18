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

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceRecordVersionException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionTable;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordVersionPersistence;
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
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ddm form instance record version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DDMFormInstanceRecordVersionPersistence.class)
public class DDMFormInstanceRecordVersionPersistenceImpl
	extends BasePersistenceImpl<DDMFormInstanceRecordVersion>
	implements DDMFormInstanceRecordVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDMFormInstanceRecordVersionUtil</code> to access the ddm form instance record version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDMFormInstanceRecordVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByFormInstanceRecordId;
	private FinderPath _finderPathWithoutPaginationFindByFormInstanceRecordId;
	private FinderPath _finderPathCountByFormInstanceRecordId;

	/**
	 * Returns all the ddm form instance record versions where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByFormInstanceRecordId(
		long formInstanceRecordId) {

		return findByFormInstanceRecordId(
			formInstanceRecordId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance record versions where formInstanceRecordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByFormInstanceRecordId(
		long formInstanceRecordId, int start, int end) {

		return findByFormInstanceRecordId(
			formInstanceRecordId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceRecordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByFormInstanceRecordId(
		long formInstanceRecordId, int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		return findByFormInstanceRecordId(
			formInstanceRecordId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceRecordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByFormInstanceRecordId(
		long formInstanceRecordId, int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByFormInstanceRecordId;
				finderArgs = new Object[] {formInstanceRecordId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByFormInstanceRecordId;
			finderArgs = new Object[] {
				formInstanceRecordId, start, end, orderByComparator
			};
		}

		List<DDMFormInstanceRecordVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecordVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
						list) {

					if (formInstanceRecordId !=
							ddmFormInstanceRecordVersion.
								getFormInstanceRecordId()) {

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

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORDVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_FORMINSTANCERECORDID_FORMINSTANCERECORDID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFormInstanceRecordVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceRecordId);

				list = (List<DDMFormInstanceRecordVersion>)QueryUtil.list(
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
	 * Returns the first ddm form instance record version in the ordered set where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByFormInstanceRecordId_First(
			long formInstanceRecordId,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			fetchByFormInstanceRecordId_First(
				formInstanceRecordId, orderByComparator);

		if (ddmFormInstanceRecordVersion != null) {
			return ddmFormInstanceRecordVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceRecordId=");
		sb.append(formInstanceRecordId);

		sb.append("}");

		throw new NoSuchFormInstanceRecordVersionException(sb.toString());
	}

	/**
	 * Returns the first ddm form instance record version in the ordered set where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByFormInstanceRecordId_First(
		long formInstanceRecordId,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		List<DDMFormInstanceRecordVersion> list = findByFormInstanceRecordId(
			formInstanceRecordId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByFormInstanceRecordId_Last(
			long formInstanceRecordId,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			fetchByFormInstanceRecordId_Last(
				formInstanceRecordId, orderByComparator);

		if (ddmFormInstanceRecordVersion != null) {
			return ddmFormInstanceRecordVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceRecordId=");
		sb.append(formInstanceRecordId);

		sb.append("}");

		throw new NoSuchFormInstanceRecordVersionException(sb.toString());
	}

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByFormInstanceRecordId_Last(
		long formInstanceRecordId,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		int count = countByFormInstanceRecordId(formInstanceRecordId);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecordVersion> list = findByFormInstanceRecordId(
			formInstanceRecordId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance record versions before and after the current ddm form instance record version in the ordered set where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordVersionId the primary key of the current ddm form instance record version
	 * @param formInstanceRecordId the form instance record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion[]
			findByFormInstanceRecordId_PrevAndNext(
				long formInstanceRecordVersionId, long formInstanceRecordId,
				OrderByComparator<DDMFormInstanceRecordVersion>
					orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			findByPrimaryKey(formInstanceRecordVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecordVersion[] array =
				new DDMFormInstanceRecordVersionImpl[3];

			array[0] = getByFormInstanceRecordId_PrevAndNext(
				session, ddmFormInstanceRecordVersion, formInstanceRecordId,
				orderByComparator, true);

			array[1] = ddmFormInstanceRecordVersion;

			array[2] = getByFormInstanceRecordId_PrevAndNext(
				session, ddmFormInstanceRecordVersion, formInstanceRecordId,
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

	protected DDMFormInstanceRecordVersion
		getByFormInstanceRecordId_PrevAndNext(
			Session session,
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
			long formInstanceRecordId,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFORMINSTANCERECORDVERSION_WHERE);

		sb.append(_FINDER_COLUMN_FORMINSTANCERECORDID_FORMINSTANCERECORDID_2);

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
			sb.append(DDMFormInstanceRecordVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(formInstanceRecordId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceRecordVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceRecordVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance record versions where formInstanceRecordId = &#63; from the database.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 */
	@Override
	public void removeByFormInstanceRecordId(long formInstanceRecordId) {
		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				findByFormInstanceRecordId(
					formInstanceRecordId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddmFormInstanceRecordVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance record versions where formInstanceRecordId = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @return the number of matching ddm form instance record versions
	 */
	@Override
	public int countByFormInstanceRecordId(long formInstanceRecordId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFormInstanceRecordId;

			finderArgs = new Object[] {formInstanceRecordId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORDVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_FORMINSTANCERECORDID_FORMINSTANCERECORDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceRecordId);

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
		_FINDER_COLUMN_FORMINSTANCERECORDID_FORMINSTANCERECORDID_2 =
			"ddmFormInstanceRecordVersion.formInstanceRecordId = ?";

	private FinderPath _finderPathWithPaginationFindByF_F;
	private FinderPath _finderPathWithoutPaginationFindByF_F;
	private FinderPath _finderPathCountByF_F;

	/**
	 * Returns all the ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @return the matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByF_F(
		long formInstanceId, String formInstanceVersion) {

		return findByF_F(
			formInstanceId, formInstanceVersion, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end) {

		return findByF_F(formInstanceId, formInstanceVersion, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		return findByF_F(
			formInstanceId, formInstanceVersion, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator,
		boolean useFinderCache) {

		formInstanceVersion = Objects.toString(formInstanceVersion, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByF_F;
				finderArgs = new Object[] {formInstanceId, formInstanceVersion};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByF_F;
			finderArgs = new Object[] {
				formInstanceId, formInstanceVersion, start, end,
				orderByComparator
			};
		}

		List<DDMFormInstanceRecordVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecordVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
						list) {

					if ((formInstanceId !=
							ddmFormInstanceRecordVersion.getFormInstanceId()) ||
						!formInstanceVersion.equals(
							ddmFormInstanceRecordVersion.
								getFormInstanceVersion())) {

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

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORDVERSION_WHERE);

			sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEID_2);

			boolean bindFormInstanceVersion = false;

			if (formInstanceVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3);
			}
			else {
				bindFormInstanceVersion = true;

				sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFormInstanceRecordVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

				if (bindFormInstanceVersion) {
					queryPos.add(formInstanceVersion);
				}

				list = (List<DDMFormInstanceRecordVersion>)QueryUtil.list(
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
	 * Returns the first ddm form instance record version in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByF_F_First(
			long formInstanceId, String formInstanceVersion,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			fetchByF_F_First(
				formInstanceId, formInstanceVersion, orderByComparator);

		if (ddmFormInstanceRecordVersion != null) {
			return ddmFormInstanceRecordVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceId=");
		sb.append(formInstanceId);

		sb.append(", formInstanceVersion=");
		sb.append(formInstanceVersion);

		sb.append("}");

		throw new NoSuchFormInstanceRecordVersionException(sb.toString());
	}

	/**
	 * Returns the first ddm form instance record version in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByF_F_First(
		long formInstanceId, String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		List<DDMFormInstanceRecordVersion> list = findByF_F(
			formInstanceId, formInstanceVersion, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByF_F_Last(
			long formInstanceId, String formInstanceVersion,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			fetchByF_F_Last(
				formInstanceId, formInstanceVersion, orderByComparator);

		if (ddmFormInstanceRecordVersion != null) {
			return ddmFormInstanceRecordVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceId=");
		sb.append(formInstanceId);

		sb.append(", formInstanceVersion=");
		sb.append(formInstanceVersion);

		sb.append("}");

		throw new NoSuchFormInstanceRecordVersionException(sb.toString());
	}

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByF_F_Last(
		long formInstanceId, String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		int count = countByF_F(formInstanceId, formInstanceVersion);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecordVersion> list = findByF_F(
			formInstanceId, formInstanceVersion, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance record versions before and after the current ddm form instance record version in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceRecordVersionId the primary key of the current ddm form instance record version
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion[] findByF_F_PrevAndNext(
			long formInstanceRecordVersionId, long formInstanceId,
			String formInstanceVersion,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		formInstanceVersion = Objects.toString(formInstanceVersion, "");

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			findByPrimaryKey(formInstanceRecordVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecordVersion[] array =
				new DDMFormInstanceRecordVersionImpl[3];

			array[0] = getByF_F_PrevAndNext(
				session, ddmFormInstanceRecordVersion, formInstanceId,
				formInstanceVersion, orderByComparator, true);

			array[1] = ddmFormInstanceRecordVersion;

			array[2] = getByF_F_PrevAndNext(
				session, ddmFormInstanceRecordVersion, formInstanceId,
				formInstanceVersion, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecordVersion getByF_F_PrevAndNext(
		Session session,
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
		long formInstanceId, String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFORMINSTANCERECORDVERSION_WHERE);

		sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEID_2);

		boolean bindFormInstanceVersion = false;

		if (formInstanceVersion.isEmpty()) {
			sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3);
		}
		else {
			bindFormInstanceVersion = true;

			sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2);
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
			sb.append(DDMFormInstanceRecordVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(formInstanceId);

		if (bindFormInstanceVersion) {
			queryPos.add(formInstanceVersion);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceRecordVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceRecordVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 */
	@Override
	public void removeByF_F(long formInstanceId, String formInstanceVersion) {
		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				findByF_F(
					formInstanceId, formInstanceVersion, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ddmFormInstanceRecordVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance record versions where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @return the number of matching ddm form instance record versions
	 */
	@Override
	public int countByF_F(long formInstanceId, String formInstanceVersion) {
		formInstanceVersion = Objects.toString(formInstanceVersion, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByF_F;

			finderArgs = new Object[] {formInstanceId, formInstanceVersion};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORDVERSION_WHERE);

			sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEID_2);

			boolean bindFormInstanceVersion = false;

			if (formInstanceVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3);
			}
			else {
				bindFormInstanceVersion = true;

				sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

				if (bindFormInstanceVersion) {
					queryPos.add(formInstanceVersion);
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

	private static final String _FINDER_COLUMN_F_F_FORMINSTANCEID_2 =
		"ddmFormInstanceRecordVersion.formInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2 =
		"ddmFormInstanceRecordVersion.formInstanceVersion = ?";

	private static final String _FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3 =
		"(ddmFormInstanceRecordVersion.formInstanceVersion IS NULL OR ddmFormInstanceRecordVersion.formInstanceVersion = '')";

	private FinderPath _finderPathFetchByF_V;
	private FinderPath _finderPathCountByF_V;

	/**
	 * Returns the ddm form instance record version where formInstanceRecordId = &#63; and version = &#63; or throws a <code>NoSuchFormInstanceRecordVersionException</code> if it could not be found.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param version the version
	 * @return the matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByF_V(
			long formInstanceRecordId, String version)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion = fetchByF_V(
			formInstanceRecordId, version);

		if (ddmFormInstanceRecordVersion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("formInstanceRecordId=");
			sb.append(formInstanceRecordId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFormInstanceRecordVersionException(sb.toString());
		}

		return ddmFormInstanceRecordVersion;
	}

	/**
	 * Returns the ddm form instance record version where formInstanceRecordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param version the version
	 * @return the matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByF_V(
		long formInstanceRecordId, String version) {

		return fetchByF_V(formInstanceRecordId, version, true);
	}

	/**
	 * Returns the ddm form instance record version where formInstanceRecordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByF_V(
		long formInstanceRecordId, String version, boolean useFinderCache) {

		version = Objects.toString(version, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {formInstanceRecordId, version};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByF_V, finderArgs, this);
		}

		if (result instanceof DDMFormInstanceRecordVersion) {
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				(DDMFormInstanceRecordVersion)result;

			if ((formInstanceRecordId !=
					ddmFormInstanceRecordVersion.getFormInstanceRecordId()) ||
				!Objects.equals(
					version, ddmFormInstanceRecordVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORDVERSION_WHERE);

			sb.append(_FINDER_COLUMN_F_V_FORMINSTANCERECORDID_2);

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

				queryPos.add(formInstanceRecordId);

				if (bindVersion) {
					queryPos.add(version);
				}

				List<DDMFormInstanceRecordVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByF_V, finderArgs, list);
					}
				}
				else {
					DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
						list.get(0);

					result = ddmFormInstanceRecordVersion;

					cacheResult(ddmFormInstanceRecordVersion);
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
			return (DDMFormInstanceRecordVersion)result;
		}
	}

	/**
	 * Removes the ddm form instance record version where formInstanceRecordId = &#63; and version = &#63; from the database.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param version the version
	 * @return the ddm form instance record version that was removed
	 */
	@Override
	public DDMFormInstanceRecordVersion removeByF_V(
			long formInstanceRecordId, String version)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion = findByF_V(
			formInstanceRecordId, version);

		return remove(ddmFormInstanceRecordVersion);
	}

	/**
	 * Returns the number of ddm form instance record versions where formInstanceRecordId = &#63; and version = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param version the version
	 * @return the number of matching ddm form instance record versions
	 */
	@Override
	public int countByF_V(long formInstanceRecordId, String version) {
		version = Objects.toString(version, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByF_V;

			finderArgs = new Object[] {formInstanceRecordId, version};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORDVERSION_WHERE);

			sb.append(_FINDER_COLUMN_F_V_FORMINSTANCERECORDID_2);

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

				queryPos.add(formInstanceRecordId);

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

	private static final String _FINDER_COLUMN_F_V_FORMINSTANCERECORDID_2 =
		"ddmFormInstanceRecordVersion.formInstanceRecordId = ? AND ";

	private static final String _FINDER_COLUMN_F_V_VERSION_2 =
		"ddmFormInstanceRecordVersion.version = ?";

	private static final String _FINDER_COLUMN_F_V_VERSION_3 =
		"(ddmFormInstanceRecordVersion.version IS NULL OR ddmFormInstanceRecordVersion.version = '')";

	private FinderPath _finderPathWithPaginationFindByF_S;
	private FinderPath _finderPathWithoutPaginationFindByF_S;
	private FinderPath _finderPathCountByF_S;

	/**
	 * Returns all the ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @return the matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByF_S(
		long formInstanceRecordId, int status) {

		return findByF_S(
			formInstanceRecordId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByF_S(
		long formInstanceRecordId, int status, int start, int end) {

		return findByF_S(formInstanceRecordId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByF_S(
		long formInstanceRecordId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		return findByF_S(
			formInstanceRecordId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByF_S(
		long formInstanceRecordId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByF_S;
				finderArgs = new Object[] {formInstanceRecordId, status};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByF_S;
			finderArgs = new Object[] {
				formInstanceRecordId, status, start, end, orderByComparator
			};
		}

		List<DDMFormInstanceRecordVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecordVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
						list) {

					if ((formInstanceRecordId !=
							ddmFormInstanceRecordVersion.
								getFormInstanceRecordId()) ||
						(status != ddmFormInstanceRecordVersion.getStatus())) {

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

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORDVERSION_WHERE);

			sb.append(_FINDER_COLUMN_F_S_FORMINSTANCERECORDID_2);

			sb.append(_FINDER_COLUMN_F_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFormInstanceRecordVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceRecordId);

				queryPos.add(status);

				list = (List<DDMFormInstanceRecordVersion>)QueryUtil.list(
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
	 * Returns the first ddm form instance record version in the ordered set where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByF_S_First(
			long formInstanceRecordId, int status,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			fetchByF_S_First(formInstanceRecordId, status, orderByComparator);

		if (ddmFormInstanceRecordVersion != null) {
			return ddmFormInstanceRecordVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceRecordId=");
		sb.append(formInstanceRecordId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFormInstanceRecordVersionException(sb.toString());
	}

	/**
	 * Returns the first ddm form instance record version in the ordered set where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByF_S_First(
		long formInstanceRecordId, int status,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		List<DDMFormInstanceRecordVersion> list = findByF_S(
			formInstanceRecordId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByF_S_Last(
			long formInstanceRecordId, int status,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			fetchByF_S_Last(formInstanceRecordId, status, orderByComparator);

		if (ddmFormInstanceRecordVersion != null) {
			return ddmFormInstanceRecordVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceRecordId=");
		sb.append(formInstanceRecordId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFormInstanceRecordVersionException(sb.toString());
	}

	/**
	 * Returns the last ddm form instance record version in the ordered set where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByF_S_Last(
		long formInstanceRecordId, int status,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		int count = countByF_S(formInstanceRecordId, status);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecordVersion> list = findByF_S(
			formInstanceRecordId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance record versions before and after the current ddm form instance record version in the ordered set where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordVersionId the primary key of the current ddm form instance record version
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion[] findByF_S_PrevAndNext(
			long formInstanceRecordVersionId, long formInstanceRecordId,
			int status,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			findByPrimaryKey(formInstanceRecordVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecordVersion[] array =
				new DDMFormInstanceRecordVersionImpl[3];

			array[0] = getByF_S_PrevAndNext(
				session, ddmFormInstanceRecordVersion, formInstanceRecordId,
				status, orderByComparator, true);

			array[1] = ddmFormInstanceRecordVersion;

			array[2] = getByF_S_PrevAndNext(
				session, ddmFormInstanceRecordVersion, formInstanceRecordId,
				status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecordVersion getByF_S_PrevAndNext(
		Session session,
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
		long formInstanceRecordId, int status,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFORMINSTANCERECORDVERSION_WHERE);

		sb.append(_FINDER_COLUMN_F_S_FORMINSTANCERECORDID_2);

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
			sb.append(DDMFormInstanceRecordVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(formInstanceRecordId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceRecordVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceRecordVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63; from the database.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 */
	@Override
	public void removeByF_S(long formInstanceRecordId, int status) {
		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				findByF_S(
					formInstanceRecordId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ddmFormInstanceRecordVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance record versions where formInstanceRecordId = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordId the form instance record ID
	 * @param status the status
	 * @return the number of matching ddm form instance record versions
	 */
	@Override
	public int countByF_S(long formInstanceRecordId, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByF_S;

			finderArgs = new Object[] {formInstanceRecordId, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORDVERSION_WHERE);

			sb.append(_FINDER_COLUMN_F_S_FORMINSTANCERECORDID_2);

			sb.append(_FINDER_COLUMN_F_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceRecordId);

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

	private static final String _FINDER_COLUMN_F_S_FORMINSTANCERECORDID_2 =
		"ddmFormInstanceRecordVersion.formInstanceRecordId = ? AND ";

	private static final String _FINDER_COLUMN_F_S_STATUS_2 =
		"ddmFormInstanceRecordVersion.status = ?";

	private FinderPath _finderPathWithPaginationFindByU_F_F_S;
	private FinderPath _finderPathWithoutPaginationFindByU_F_F_S;
	private FinderPath _finderPathCountByU_F_F_S;

	/**
	 * Returns all the ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @return the matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status) {

		return findByU_F_F_S(
			userId, formInstanceId, formInstanceVersion, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status, int start, int end) {

		return findByU_F_F_S(
			userId, formInstanceId, formInstanceVersion, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status, int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		return findByU_F_F_S(
			userId, formInstanceId, formInstanceVersion, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status, int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator,
		boolean useFinderCache) {

		formInstanceVersion = Objects.toString(formInstanceVersion, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByU_F_F_S;
				finderArgs = new Object[] {
					userId, formInstanceId, formInstanceVersion, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByU_F_F_S;
			finderArgs = new Object[] {
				userId, formInstanceId, formInstanceVersion, status, start, end,
				orderByComparator
			};
		}

		List<DDMFormInstanceRecordVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecordVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
						list) {

					if ((userId != ddmFormInstanceRecordVersion.getUserId()) ||
						(formInstanceId !=
							ddmFormInstanceRecordVersion.getFormInstanceId()) ||
						!formInstanceVersion.equals(
							ddmFormInstanceRecordVersion.
								getFormInstanceVersion()) ||
						(status != ddmFormInstanceRecordVersion.getStatus())) {

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

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORDVERSION_WHERE);

			sb.append(_FINDER_COLUMN_U_F_F_S_USERID_2);

			sb.append(_FINDER_COLUMN_U_F_F_S_FORMINSTANCEID_2);

			boolean bindFormInstanceVersion = false;

			if (formInstanceVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_F_F_S_FORMINSTANCEVERSION_3);
			}
			else {
				bindFormInstanceVersion = true;

				sb.append(_FINDER_COLUMN_U_F_F_S_FORMINSTANCEVERSION_2);
			}

			sb.append(_FINDER_COLUMN_U_F_F_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFormInstanceRecordVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(formInstanceId);

				if (bindFormInstanceVersion) {
					queryPos.add(formInstanceVersion);
				}

				queryPos.add(status);

				list = (List<DDMFormInstanceRecordVersion>)QueryUtil.list(
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
	 * Returns the first ddm form instance record version in the ordered set where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByU_F_F_S_First(
			long userId, long formInstanceId, String formInstanceVersion,
			int status,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			fetchByU_F_F_S_First(
				userId, formInstanceId, formInstanceVersion, status,
				orderByComparator);

		if (ddmFormInstanceRecordVersion != null) {
			return ddmFormInstanceRecordVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", formInstanceId=");
		sb.append(formInstanceId);

		sb.append(", formInstanceVersion=");
		sb.append(formInstanceVersion);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFormInstanceRecordVersionException(sb.toString());
	}

	/**
	 * Returns the first ddm form instance record version in the ordered set where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByU_F_F_S_First(
		long userId, long formInstanceId, String formInstanceVersion,
		int status,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		List<DDMFormInstanceRecordVersion> list = findByU_F_F_S(
			userId, formInstanceId, formInstanceVersion, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance record version in the ordered set where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByU_F_F_S_Last(
			long userId, long formInstanceId, String formInstanceVersion,
			int status,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			fetchByU_F_F_S_Last(
				userId, formInstanceId, formInstanceVersion, status,
				orderByComparator);

		if (ddmFormInstanceRecordVersion != null) {
			return ddmFormInstanceRecordVersion;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", formInstanceId=");
		sb.append(formInstanceId);

		sb.append(", formInstanceVersion=");
		sb.append(formInstanceVersion);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFormInstanceRecordVersionException(sb.toString());
	}

	/**
	 * Returns the last ddm form instance record version in the ordered set where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record version, or <code>null</code> if a matching ddm form instance record version could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByU_F_F_S_Last(
		long userId, long formInstanceId, String formInstanceVersion,
		int status,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		int count = countByU_F_F_S(
			userId, formInstanceId, formInstanceVersion, status);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecordVersion> list = findByU_F_F_S(
			userId, formInstanceId, formInstanceVersion, status, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance record versions before and after the current ddm form instance record version in the ordered set where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param formInstanceRecordVersionId the primary key of the current ddm form instance record version
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion[] findByU_F_F_S_PrevAndNext(
			long formInstanceRecordVersionId, long userId, long formInstanceId,
			String formInstanceVersion, int status,
			OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator)
		throws NoSuchFormInstanceRecordVersionException {

		formInstanceVersion = Objects.toString(formInstanceVersion, "");

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			findByPrimaryKey(formInstanceRecordVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecordVersion[] array =
				new DDMFormInstanceRecordVersionImpl[3];

			array[0] = getByU_F_F_S_PrevAndNext(
				session, ddmFormInstanceRecordVersion, userId, formInstanceId,
				formInstanceVersion, status, orderByComparator, true);

			array[1] = ddmFormInstanceRecordVersion;

			array[2] = getByU_F_F_S_PrevAndNext(
				session, ddmFormInstanceRecordVersion, userId, formInstanceId,
				formInstanceVersion, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecordVersion getByU_F_F_S_PrevAndNext(
		Session session,
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion, long userId,
		long formInstanceId, String formInstanceVersion, int status,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_DDMFORMINSTANCERECORDVERSION_WHERE);

		sb.append(_FINDER_COLUMN_U_F_F_S_USERID_2);

		sb.append(_FINDER_COLUMN_U_F_F_S_FORMINSTANCEID_2);

		boolean bindFormInstanceVersion = false;

		if (formInstanceVersion.isEmpty()) {
			sb.append(_FINDER_COLUMN_U_F_F_S_FORMINSTANCEVERSION_3);
		}
		else {
			bindFormInstanceVersion = true;

			sb.append(_FINDER_COLUMN_U_F_F_S_FORMINSTANCEVERSION_2);
		}

		sb.append(_FINDER_COLUMN_U_F_F_S_STATUS_2);

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
			sb.append(DDMFormInstanceRecordVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		queryPos.add(formInstanceId);

		if (bindFormInstanceVersion) {
			queryPos.add(formInstanceVersion);
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceRecordVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceRecordVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 */
	@Override
	public void removeByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status) {

		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				findByU_F_F_S(
					userId, formInstanceId, formInstanceVersion, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddmFormInstanceRecordVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance record versions where userId = &#63; and formInstanceId = &#63; and formInstanceVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param status the status
	 * @return the number of matching ddm form instance record versions
	 */
	@Override
	public int countByU_F_F_S(
		long userId, long formInstanceId, String formInstanceVersion,
		int status) {

		formInstanceVersion = Objects.toString(formInstanceVersion, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_F_F_S;

			finderArgs = new Object[] {
				userId, formInstanceId, formInstanceVersion, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORDVERSION_WHERE);

			sb.append(_FINDER_COLUMN_U_F_F_S_USERID_2);

			sb.append(_FINDER_COLUMN_U_F_F_S_FORMINSTANCEID_2);

			boolean bindFormInstanceVersion = false;

			if (formInstanceVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_F_F_S_FORMINSTANCEVERSION_3);
			}
			else {
				bindFormInstanceVersion = true;

				sb.append(_FINDER_COLUMN_U_F_F_S_FORMINSTANCEVERSION_2);
			}

			sb.append(_FINDER_COLUMN_U_F_F_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(formInstanceId);

				if (bindFormInstanceVersion) {
					queryPos.add(formInstanceVersion);
				}

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

	private static final String _FINDER_COLUMN_U_F_F_S_USERID_2 =
		"ddmFormInstanceRecordVersion.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_F_F_S_FORMINSTANCEID_2 =
		"ddmFormInstanceRecordVersion.formInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_U_F_F_S_FORMINSTANCEVERSION_2 =
		"ddmFormInstanceRecordVersion.formInstanceVersion = ? AND ";

	private static final String _FINDER_COLUMN_U_F_F_S_FORMINSTANCEVERSION_3 =
		"(ddmFormInstanceRecordVersion.formInstanceVersion IS NULL OR ddmFormInstanceRecordVersion.formInstanceVersion = '') AND ";

	private static final String _FINDER_COLUMN_U_F_F_S_STATUS_2 =
		"ddmFormInstanceRecordVersion.status = ?";

	public DDMFormInstanceRecordVersionPersistenceImpl() {
		setModelClass(DDMFormInstanceRecordVersion.class);

		setModelImplClass(DDMFormInstanceRecordVersionImpl.class);
		setModelPKClass(long.class);

		setTable(DDMFormInstanceRecordVersionTable.INSTANCE);
	}

	/**
	 * Caches the ddm form instance record version in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceRecordVersion the ddm form instance record version
	 */
	@Override
	public void cacheResult(
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion) {

		if (ddmFormInstanceRecordVersion.getCtCollectionId() != 0) {
			ddmFormInstanceRecordVersion.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			DDMFormInstanceRecordVersionImpl.class,
			ddmFormInstanceRecordVersion.getPrimaryKey(),
			ddmFormInstanceRecordVersion);

		finderCache.putResult(
			_finderPathFetchByF_V,
			new Object[] {
				ddmFormInstanceRecordVersion.getFormInstanceRecordId(),
				ddmFormInstanceRecordVersion.getVersion()
			},
			ddmFormInstanceRecordVersion);

		ddmFormInstanceRecordVersion.resetOriginalValues();
	}

	/**
	 * Caches the ddm form instance record versions in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceRecordVersions the ddm form instance record versions
	 */
	@Override
	public void cacheResult(
		List<DDMFormInstanceRecordVersion> ddmFormInstanceRecordVersions) {

		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				ddmFormInstanceRecordVersions) {

			if (ddmFormInstanceRecordVersion.getCtCollectionId() != 0) {
				ddmFormInstanceRecordVersion.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					DDMFormInstanceRecordVersionImpl.class,
					ddmFormInstanceRecordVersion.getPrimaryKey()) == null) {

				cacheResult(ddmFormInstanceRecordVersion);
			}
			else {
				ddmFormInstanceRecordVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ddm form instance record versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMFormInstanceRecordVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ddm form instance record version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion) {

		entityCache.removeResult(
			DDMFormInstanceRecordVersionImpl.class,
			ddmFormInstanceRecordVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DDMFormInstanceRecordVersionModelImpl)ddmFormInstanceRecordVersion,
			true);
	}

	@Override
	public void clearCache(
		List<DDMFormInstanceRecordVersion> ddmFormInstanceRecordVersions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				ddmFormInstanceRecordVersions) {

			entityCache.removeResult(
				DDMFormInstanceRecordVersionImpl.class,
				ddmFormInstanceRecordVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(DDMFormInstanceRecordVersionModelImpl)
					ddmFormInstanceRecordVersion,
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
				DDMFormInstanceRecordVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMFormInstanceRecordVersionModelImpl
			ddmFormInstanceRecordVersionModelImpl) {

		Object[] args = new Object[] {
			ddmFormInstanceRecordVersionModelImpl.getFormInstanceRecordId(),
			ddmFormInstanceRecordVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByF_V, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByF_V, args, ddmFormInstanceRecordVersionModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		DDMFormInstanceRecordVersionModelImpl
			ddmFormInstanceRecordVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ddmFormInstanceRecordVersionModelImpl.getFormInstanceRecordId(),
				ddmFormInstanceRecordVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByF_V, args);
			finderCache.removeResult(_finderPathFetchByF_V, args);
		}

		if ((ddmFormInstanceRecordVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByF_V.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ddmFormInstanceRecordVersionModelImpl.
					getOriginalFormInstanceRecordId(),
				ddmFormInstanceRecordVersionModelImpl.getOriginalVersion()
			};

			finderCache.removeResult(_finderPathCountByF_V, args);
			finderCache.removeResult(_finderPathFetchByF_V, args);
		}
	}

	/**
	 * Creates a new ddm form instance record version with the primary key. Does not add the ddm form instance record version to the database.
	 *
	 * @param formInstanceRecordVersionId the primary key for the new ddm form instance record version
	 * @return the new ddm form instance record version
	 */
	@Override
	public DDMFormInstanceRecordVersion create(
		long formInstanceRecordVersionId) {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			new DDMFormInstanceRecordVersionImpl();

		ddmFormInstanceRecordVersion.setNew(true);
		ddmFormInstanceRecordVersion.setPrimaryKey(formInstanceRecordVersionId);

		ddmFormInstanceRecordVersion.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return ddmFormInstanceRecordVersion;
	}

	/**
	 * Removes the ddm form instance record version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceRecordVersionId the primary key of the ddm form instance record version
	 * @return the ddm form instance record version that was removed
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion remove(long formInstanceRecordVersionId)
		throws NoSuchFormInstanceRecordVersionException {

		return remove((Serializable)formInstanceRecordVersionId);
	}

	/**
	 * Removes the ddm form instance record version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddm form instance record version
	 * @return the ddm form instance record version that was removed
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion remove(Serializable primaryKey)
		throws NoSuchFormInstanceRecordVersionException {

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				(DDMFormInstanceRecordVersion)session.get(
					DDMFormInstanceRecordVersionImpl.class, primaryKey);

			if (ddmFormInstanceRecordVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFormInstanceRecordVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ddmFormInstanceRecordVersion);
		}
		catch (NoSuchFormInstanceRecordVersionException noSuchEntityException) {
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
	protected DDMFormInstanceRecordVersion removeImpl(
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion) {

		if (!ctPersistenceHelper.isRemove(ddmFormInstanceRecordVersion)) {
			return ddmFormInstanceRecordVersion;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmFormInstanceRecordVersion)) {
				ddmFormInstanceRecordVersion =
					(DDMFormInstanceRecordVersion)session.get(
						DDMFormInstanceRecordVersionImpl.class,
						ddmFormInstanceRecordVersion.getPrimaryKeyObj());
			}

			if (ddmFormInstanceRecordVersion != null) {
				session.delete(ddmFormInstanceRecordVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmFormInstanceRecordVersion != null) {
			clearCache(ddmFormInstanceRecordVersion);
		}

		return ddmFormInstanceRecordVersion;
	}

	@Override
	public DDMFormInstanceRecordVersion updateImpl(
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion) {

		boolean isNew = ddmFormInstanceRecordVersion.isNew();

		if (!(ddmFormInstanceRecordVersion instanceof
				DDMFormInstanceRecordVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					ddmFormInstanceRecordVersion.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					ddmFormInstanceRecordVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddmFormInstanceRecordVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDMFormInstanceRecordVersion implementation " +
					ddmFormInstanceRecordVersion.getClass());
		}

		DDMFormInstanceRecordVersionModelImpl
			ddmFormInstanceRecordVersionModelImpl =
				(DDMFormInstanceRecordVersionModelImpl)
					ddmFormInstanceRecordVersion;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ddmFormInstanceRecordVersion)) {
				if (!isNew) {
					session.evict(
						DDMFormInstanceRecordVersionImpl.class,
						ddmFormInstanceRecordVersion.getPrimaryKeyObj());
				}

				session.save(ddmFormInstanceRecordVersion);

				ddmFormInstanceRecordVersion.setNew(false);
			}
			else {
				ddmFormInstanceRecordVersion =
					(DDMFormInstanceRecordVersion)session.merge(
						ddmFormInstanceRecordVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmFormInstanceRecordVersion.getCtCollectionId() != 0) {
			ddmFormInstanceRecordVersion.resetOriginalValues();

			return ddmFormInstanceRecordVersion;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				ddmFormInstanceRecordVersionModelImpl.getFormInstanceRecordId()
			};

			finderCache.removeResult(
				_finderPathCountByFormInstanceRecordId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFormInstanceRecordId, args);

			args = new Object[] {
				ddmFormInstanceRecordVersionModelImpl.getFormInstanceId(),
				ddmFormInstanceRecordVersionModelImpl.getFormInstanceVersion()
			};

			finderCache.removeResult(_finderPathCountByF_F, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByF_F, args);

			args = new Object[] {
				ddmFormInstanceRecordVersionModelImpl.getFormInstanceRecordId(),
				ddmFormInstanceRecordVersionModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByF_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByF_S, args);

			args = new Object[] {
				ddmFormInstanceRecordVersionModelImpl.getUserId(),
				ddmFormInstanceRecordVersionModelImpl.getFormInstanceId(),
				ddmFormInstanceRecordVersionModelImpl.getFormInstanceVersion(),
				ddmFormInstanceRecordVersionModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByU_F_F_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByU_F_F_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ddmFormInstanceRecordVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFormInstanceRecordId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmFormInstanceRecordVersionModelImpl.
						getOriginalFormInstanceRecordId()
				};

				finderCache.removeResult(
					_finderPathCountByFormInstanceRecordId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFormInstanceRecordId,
					args);

				args = new Object[] {
					ddmFormInstanceRecordVersionModelImpl.
						getFormInstanceRecordId()
				};

				finderCache.removeResult(
					_finderPathCountByFormInstanceRecordId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFormInstanceRecordId,
					args);
			}

			if ((ddmFormInstanceRecordVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByF_F.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmFormInstanceRecordVersionModelImpl.
						getOriginalFormInstanceId(),
					ddmFormInstanceRecordVersionModelImpl.
						getOriginalFormInstanceVersion()
				};

				finderCache.removeResult(_finderPathCountByF_F, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByF_F, args);

				args = new Object[] {
					ddmFormInstanceRecordVersionModelImpl.getFormInstanceId(),
					ddmFormInstanceRecordVersionModelImpl.
						getFormInstanceVersion()
				};

				finderCache.removeResult(_finderPathCountByF_F, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByF_F, args);
			}

			if ((ddmFormInstanceRecordVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByF_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmFormInstanceRecordVersionModelImpl.
						getOriginalFormInstanceRecordId(),
					ddmFormInstanceRecordVersionModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByF_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByF_S, args);

				args = new Object[] {
					ddmFormInstanceRecordVersionModelImpl.
						getFormInstanceRecordId(),
					ddmFormInstanceRecordVersionModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByF_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByF_S, args);
			}

			if ((ddmFormInstanceRecordVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_F_F_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmFormInstanceRecordVersionModelImpl.getOriginalUserId(),
					ddmFormInstanceRecordVersionModelImpl.
						getOriginalFormInstanceId(),
					ddmFormInstanceRecordVersionModelImpl.
						getOriginalFormInstanceVersion(),
					ddmFormInstanceRecordVersionModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByU_F_F_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_F_F_S, args);

				args = new Object[] {
					ddmFormInstanceRecordVersionModelImpl.getUserId(),
					ddmFormInstanceRecordVersionModelImpl.getFormInstanceId(),
					ddmFormInstanceRecordVersionModelImpl.
						getFormInstanceVersion(),
					ddmFormInstanceRecordVersionModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByU_F_F_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_F_F_S, args);
			}
		}

		entityCache.putResult(
			DDMFormInstanceRecordVersionImpl.class,
			ddmFormInstanceRecordVersion.getPrimaryKey(),
			ddmFormInstanceRecordVersion, false);

		clearUniqueFindersCache(ddmFormInstanceRecordVersionModelImpl, false);
		cacheUniqueFindersCache(ddmFormInstanceRecordVersionModelImpl);

		ddmFormInstanceRecordVersion.resetOriginalValues();

		return ddmFormInstanceRecordVersion;
	}

	/**
	 * Returns the ddm form instance record version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm form instance record version
	 * @return the ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchFormInstanceRecordVersionException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			fetchByPrimaryKey(primaryKey);

		if (ddmFormInstanceRecordVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFormInstanceRecordVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ddmFormInstanceRecordVersion;
	}

	/**
	 * Returns the ddm form instance record version with the primary key or throws a <code>NoSuchFormInstanceRecordVersionException</code> if it could not be found.
	 *
	 * @param formInstanceRecordVersionId the primary key of the ddm form instance record version
	 * @return the ddm form instance record version
	 * @throws NoSuchFormInstanceRecordVersionException if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion findByPrimaryKey(
			long formInstanceRecordVersionId)
		throws NoSuchFormInstanceRecordVersionException {

		return findByPrimaryKey((Serializable)formInstanceRecordVersionId);
	}

	/**
	 * Returns the ddm form instance record version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm form instance record version
	 * @return the ddm form instance record version, or <code>null</code> if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				DDMFormInstanceRecordVersion.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion = null;

		Session session = null;

		try {
			session = openSession();

			ddmFormInstanceRecordVersion =
				(DDMFormInstanceRecordVersion)session.get(
					DDMFormInstanceRecordVersionImpl.class, primaryKey);

			if (ddmFormInstanceRecordVersion != null) {
				cacheResult(ddmFormInstanceRecordVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ddmFormInstanceRecordVersion;
	}

	/**
	 * Returns the ddm form instance record version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formInstanceRecordVersionId the primary key of the ddm form instance record version
	 * @return the ddm form instance record version, or <code>null</code> if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecordVersion fetchByPrimaryKey(
		long formInstanceRecordVersionId) {

		return fetchByPrimaryKey((Serializable)formInstanceRecordVersionId);
	}

	@Override
	public Map<Serializable, DDMFormInstanceRecordVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				DDMFormInstanceRecordVersion.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMFormInstanceRecordVersion> map =
			new HashMap<Serializable, DDMFormInstanceRecordVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				fetchByPrimaryKey(primaryKey);

			if (ddmFormInstanceRecordVersion != null) {
				map.put(primaryKey, ddmFormInstanceRecordVersion);
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

			for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
					(List<DDMFormInstanceRecordVersion>)query.list()) {

				map.put(
					ddmFormInstanceRecordVersion.getPrimaryKeyObj(),
					ddmFormInstanceRecordVersion);

				cacheResult(ddmFormInstanceRecordVersion);
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
	 * Returns all the ddm form instance record versions.
	 *
	 * @return the ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findAll(
		int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm form instance record versions
	 */
	@Override
	public List<DDMFormInstanceRecordVersion> findAll(
		int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

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

		List<DDMFormInstanceRecordVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecordVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DDMFORMINSTANCERECORDVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DDMFORMINSTANCERECORDVERSION;

				sql = sql.concat(
					DDMFormInstanceRecordVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DDMFormInstanceRecordVersion>)QueryUtil.list(
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
	 * Removes all the ddm form instance record versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				findAll()) {

			remove(ddmFormInstanceRecordVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance record versions.
	 *
	 * @return the number of ddm form instance record versions
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecordVersion.class);

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
					_SQL_COUNT_DDMFORMINSTANCERECORDVERSION);

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
		return "formInstanceRecordVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDMFORMINSTANCERECORDVERSION;
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
		return DDMFormInstanceRecordVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DDMFormInstanceRecordVersion";
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
		ctStrictColumnNames.add("formInstanceVersion");
		ctStrictColumnNames.add("formInstanceRecordId");
		ctStrictColumnNames.add("version");
		ctStrictColumnNames.add("storageId");
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
			Collections.singleton("formInstanceRecordVersionId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"formInstanceRecordId", "version"});
	}

	/**
	 * Initializes the ddm form instance record version persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByFormInstanceRecordId = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByFormInstanceRecordId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFormInstanceRecordId = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByFormInstanceRecordId", new String[] {Long.class.getName()},
			DDMFormInstanceRecordVersionModelImpl.
				FORMINSTANCERECORDID_COLUMN_BITMASK);

		_finderPathCountByFormInstanceRecordId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFormInstanceRecordId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByF_F = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_F",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByF_F = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_F",
			new String[] {Long.class.getName(), String.class.getName()},
			DDMFormInstanceRecordVersionModelImpl.
				FORMINSTANCEID_COLUMN_BITMASK |
			DDMFormInstanceRecordVersionModelImpl.
				FORMINSTANCEVERSION_COLUMN_BITMASK);

		_finderPathCountByF_F = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_F",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByF_V = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByF_V",
			new String[] {Long.class.getName(), String.class.getName()},
			DDMFormInstanceRecordVersionModelImpl.
				FORMINSTANCERECORDID_COLUMN_BITMASK |
			DDMFormInstanceRecordVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByF_V = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_V",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByF_S = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByF_S = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			DDMFormInstanceRecordVersionModelImpl.
				FORMINSTANCERECORDID_COLUMN_BITMASK |
			DDMFormInstanceRecordVersionModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByF_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_S",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByU_F_F_S = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_F_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_F_F_S = new FinderPath(
			DDMFormInstanceRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_F_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			DDMFormInstanceRecordVersionModelImpl.USERID_COLUMN_BITMASK |
			DDMFormInstanceRecordVersionModelImpl.
				FORMINSTANCEID_COLUMN_BITMASK |
			DDMFormInstanceRecordVersionModelImpl.
				FORMINSTANCEVERSION_COLUMN_BITMASK |
			DDMFormInstanceRecordVersionModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByU_F_F_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByU_F_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			DDMFormInstanceRecordVersionImpl.class.getName());
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

	private static final String _SQL_SELECT_DDMFORMINSTANCERECORDVERSION =
		"SELECT ddmFormInstanceRecordVersion FROM DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion";

	private static final String _SQL_SELECT_DDMFORMINSTANCERECORDVERSION_WHERE =
		"SELECT ddmFormInstanceRecordVersion FROM DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion WHERE ";

	private static final String _SQL_COUNT_DDMFORMINSTANCERECORDVERSION =
		"SELECT COUNT(ddmFormInstanceRecordVersion) FROM DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion";

	private static final String _SQL_COUNT_DDMFORMINSTANCERECORDVERSION_WHERE =
		"SELECT COUNT(ddmFormInstanceRecordVersion) FROM DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"ddmFormInstanceRecordVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DDMFormInstanceRecordVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDMFormInstanceRecordVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordVersionPersistenceImpl.class);

	static {
		try {
			Class.forName(DDMPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}