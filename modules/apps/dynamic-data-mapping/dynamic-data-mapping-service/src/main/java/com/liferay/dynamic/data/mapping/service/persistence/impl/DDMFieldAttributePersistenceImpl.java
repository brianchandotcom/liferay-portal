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

import com.liferay.dynamic.data.mapping.exception.NoSuchFieldAttributeException;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttributeTable;
import com.liferay.dynamic.data.mapping.model.impl.DDMFieldAttributeImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMFieldAttributeModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFieldAttributePersistence;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
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
 * The persistence implementation for the ddm field attribute service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {DDMFieldAttributePersistence.class, BasePersistence.class}
)
public class DDMFieldAttributePersistenceImpl
	extends BasePersistenceImpl<DDMFieldAttribute>
	implements DDMFieldAttributePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDMFieldAttributeUtil</code> to access the ddm field attribute persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDMFieldAttributeImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByStorageId;
	private FinderPath _finderPathWithoutPaginationFindByStorageId;
	private FinderPath _finderPathCountByStorageId;

	/**
	 * Returns all the ddm field attributes where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @return the matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByStorageId(long storageId) {
		return findByStorageId(
			storageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm field attributes where storageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @return the range of matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByStorageId(
		long storageId, int start, int end) {

		return findByStorageId(storageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm field attributes where storageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByStorageId(
		long storageId, int start, int end,
		OrderByComparator<DDMFieldAttribute> orderByComparator) {

		return findByStorageId(storageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm field attributes where storageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByStorageId(
		long storageId, int start, int end,
		OrderByComparator<DDMFieldAttribute> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFieldAttribute.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByStorageId;
				finderArgs = new Object[] {storageId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByStorageId;
			finderArgs = new Object[] {
				storageId, start, end, orderByComparator
			};
		}

		List<DDMFieldAttribute> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFieldAttribute>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFieldAttribute ddmFieldAttribute : list) {
					if (storageId != ddmFieldAttribute.getStorageId()) {
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

			sb.append(_SQL_SELECT_DDMFIELDATTRIBUTE_WHERE);

			sb.append(_FINDER_COLUMN_STORAGEID_STORAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFieldAttributeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(storageId);

				list = (List<DDMFieldAttribute>)QueryUtil.list(
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
	 * Returns the first ddm field attribute in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute findByStorageId_First(
			long storageId,
			OrderByComparator<DDMFieldAttribute> orderByComparator)
		throws NoSuchFieldAttributeException {

		DDMFieldAttribute ddmFieldAttribute = fetchByStorageId_First(
			storageId, orderByComparator);

		if (ddmFieldAttribute != null) {
			return ddmFieldAttribute;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("storageId=");
		sb.append(storageId);

		sb.append("}");

		throw new NoSuchFieldAttributeException(sb.toString());
	}

	/**
	 * Returns the first ddm field attribute in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute fetchByStorageId_First(
		long storageId,
		OrderByComparator<DDMFieldAttribute> orderByComparator) {

		List<DDMFieldAttribute> list = findByStorageId(
			storageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm field attribute in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute findByStorageId_Last(
			long storageId,
			OrderByComparator<DDMFieldAttribute> orderByComparator)
		throws NoSuchFieldAttributeException {

		DDMFieldAttribute ddmFieldAttribute = fetchByStorageId_Last(
			storageId, orderByComparator);

		if (ddmFieldAttribute != null) {
			return ddmFieldAttribute;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("storageId=");
		sb.append(storageId);

		sb.append("}");

		throw new NoSuchFieldAttributeException(sb.toString());
	}

	/**
	 * Returns the last ddm field attribute in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute fetchByStorageId_Last(
		long storageId,
		OrderByComparator<DDMFieldAttribute> orderByComparator) {

		int count = countByStorageId(storageId);

		if (count == 0) {
			return null;
		}

		List<DDMFieldAttribute> list = findByStorageId(
			storageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm field attributes before and after the current ddm field attribute in the ordered set where storageId = &#63;.
	 *
	 * @param fieldAttributeId the primary key of the current ddm field attribute
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm field attribute
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	@Override
	public DDMFieldAttribute[] findByStorageId_PrevAndNext(
			long fieldAttributeId, long storageId,
			OrderByComparator<DDMFieldAttribute> orderByComparator)
		throws NoSuchFieldAttributeException {

		DDMFieldAttribute ddmFieldAttribute = findByPrimaryKey(
			fieldAttributeId);

		Session session = null;

		try {
			session = openSession();

			DDMFieldAttribute[] array = new DDMFieldAttributeImpl[3];

			array[0] = getByStorageId_PrevAndNext(
				session, ddmFieldAttribute, storageId, orderByComparator, true);

			array[1] = ddmFieldAttribute;

			array[2] = getByStorageId_PrevAndNext(
				session, ddmFieldAttribute, storageId, orderByComparator,
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

	protected DDMFieldAttribute getByStorageId_PrevAndNext(
		Session session, DDMFieldAttribute ddmFieldAttribute, long storageId,
		OrderByComparator<DDMFieldAttribute> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFIELDATTRIBUTE_WHERE);

		sb.append(_FINDER_COLUMN_STORAGEID_STORAGEID_2);

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
			sb.append(DDMFieldAttributeModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(storageId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFieldAttribute)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFieldAttribute> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm field attributes where storageId = &#63; from the database.
	 *
	 * @param storageId the storage ID
	 */
	@Override
	public void removeByStorageId(long storageId) {
		for (DDMFieldAttribute ddmFieldAttribute :
				findByStorageId(
					storageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddmFieldAttribute);
		}
	}

	/**
	 * Returns the number of ddm field attributes where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @return the number of matching ddm field attributes
	 */
	@Override
	public int countByStorageId(long storageId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFieldAttribute.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByStorageId;

			finderArgs = new Object[] {storageId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDMFIELDATTRIBUTE_WHERE);

			sb.append(_FINDER_COLUMN_STORAGEID_STORAGEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(storageId);

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

	private static final String _FINDER_COLUMN_STORAGEID_STORAGEID_2 =
		"ddmFieldAttribute.storageId = ?";

	private FinderPath _finderPathWithPaginationFindByS_L;
	private FinderPath _finderPathWithoutPaginationFindByS_L;
	private FinderPath _finderPathCountByS_L;

	/**
	 * Returns all the ddm field attributes where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @return the matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByS_L(
		long storageId, String languageId) {

		return findByS_L(
			storageId, languageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm field attributes where storageId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @return the range of matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByS_L(
		long storageId, String languageId, int start, int end) {

		return findByS_L(storageId, languageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm field attributes where storageId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByS_L(
		long storageId, String languageId, int start, int end,
		OrderByComparator<DDMFieldAttribute> orderByComparator) {

		return findByS_L(
			storageId, languageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm field attributes where storageId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByS_L(
		long storageId, String languageId, int start, int end,
		OrderByComparator<DDMFieldAttribute> orderByComparator,
		boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFieldAttribute.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByS_L;
				finderArgs = new Object[] {storageId, languageId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByS_L;
			finderArgs = new Object[] {
				storageId, languageId, start, end, orderByComparator
			};
		}

		List<DDMFieldAttribute> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFieldAttribute>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFieldAttribute ddmFieldAttribute : list) {
					if ((storageId != ddmFieldAttribute.getStorageId()) ||
						!languageId.equals(ddmFieldAttribute.getLanguageId())) {

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

			sb.append(_SQL_SELECT_DDMFIELDATTRIBUTE_WHERE);

			sb.append(_FINDER_COLUMN_S_L_STORAGEID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_S_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_S_L_LANGUAGEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFieldAttributeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(storageId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				list = (List<DDMFieldAttribute>)QueryUtil.list(
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
	 * Returns the first ddm field attribute in the ordered set where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute findByS_L_First(
			long storageId, String languageId,
			OrderByComparator<DDMFieldAttribute> orderByComparator)
		throws NoSuchFieldAttributeException {

		DDMFieldAttribute ddmFieldAttribute = fetchByS_L_First(
			storageId, languageId, orderByComparator);

		if (ddmFieldAttribute != null) {
			return ddmFieldAttribute;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("storageId=");
		sb.append(storageId);

		sb.append(", languageId=");
		sb.append(languageId);

		sb.append("}");

		throw new NoSuchFieldAttributeException(sb.toString());
	}

	/**
	 * Returns the first ddm field attribute in the ordered set where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute fetchByS_L_First(
		long storageId, String languageId,
		OrderByComparator<DDMFieldAttribute> orderByComparator) {

		List<DDMFieldAttribute> list = findByS_L(
			storageId, languageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm field attribute in the ordered set where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute findByS_L_Last(
			long storageId, String languageId,
			OrderByComparator<DDMFieldAttribute> orderByComparator)
		throws NoSuchFieldAttributeException {

		DDMFieldAttribute ddmFieldAttribute = fetchByS_L_Last(
			storageId, languageId, orderByComparator);

		if (ddmFieldAttribute != null) {
			return ddmFieldAttribute;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("storageId=");
		sb.append(storageId);

		sb.append(", languageId=");
		sb.append(languageId);

		sb.append("}");

		throw new NoSuchFieldAttributeException(sb.toString());
	}

	/**
	 * Returns the last ddm field attribute in the ordered set where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute fetchByS_L_Last(
		long storageId, String languageId,
		OrderByComparator<DDMFieldAttribute> orderByComparator) {

		int count = countByS_L(storageId, languageId);

		if (count == 0) {
			return null;
		}

		List<DDMFieldAttribute> list = findByS_L(
			storageId, languageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm field attributes before and after the current ddm field attribute in the ordered set where storageId = &#63; and languageId = &#63;.
	 *
	 * @param fieldAttributeId the primary key of the current ddm field attribute
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm field attribute
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	@Override
	public DDMFieldAttribute[] findByS_L_PrevAndNext(
			long fieldAttributeId, long storageId, String languageId,
			OrderByComparator<DDMFieldAttribute> orderByComparator)
		throws NoSuchFieldAttributeException {

		languageId = Objects.toString(languageId, "");

		DDMFieldAttribute ddmFieldAttribute = findByPrimaryKey(
			fieldAttributeId);

		Session session = null;

		try {
			session = openSession();

			DDMFieldAttribute[] array = new DDMFieldAttributeImpl[3];

			array[0] = getByS_L_PrevAndNext(
				session, ddmFieldAttribute, storageId, languageId,
				orderByComparator, true);

			array[1] = ddmFieldAttribute;

			array[2] = getByS_L_PrevAndNext(
				session, ddmFieldAttribute, storageId, languageId,
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

	protected DDMFieldAttribute getByS_L_PrevAndNext(
		Session session, DDMFieldAttribute ddmFieldAttribute, long storageId,
		String languageId,
		OrderByComparator<DDMFieldAttribute> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFIELDATTRIBUTE_WHERE);

		sb.append(_FINDER_COLUMN_S_L_STORAGEID_2);

		boolean bindLanguageId = false;

		if (languageId.isEmpty()) {
			sb.append(_FINDER_COLUMN_S_L_LANGUAGEID_3);
		}
		else {
			bindLanguageId = true;

			sb.append(_FINDER_COLUMN_S_L_LANGUAGEID_2);
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
			sb.append(DDMFieldAttributeModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(storageId);

		if (bindLanguageId) {
			queryPos.add(languageId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFieldAttribute)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFieldAttribute> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm field attributes where storageId = &#63; and languageId = &#63; from the database.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 */
	@Override
	public void removeByS_L(long storageId, String languageId) {
		for (DDMFieldAttribute ddmFieldAttribute :
				findByS_L(
					storageId, languageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddmFieldAttribute);
		}
	}

	/**
	 * Returns the number of ddm field attributes where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @return the number of matching ddm field attributes
	 */
	@Override
	public int countByS_L(long storageId, String languageId) {
		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFieldAttribute.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByS_L;

			finderArgs = new Object[] {storageId, languageId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMFIELDATTRIBUTE_WHERE);

			sb.append(_FINDER_COLUMN_S_L_STORAGEID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_S_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_S_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(storageId);

				if (bindLanguageId) {
					queryPos.add(languageId);
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

	private static final String _FINDER_COLUMN_S_L_STORAGEID_2 =
		"ddmFieldAttribute.storageId = ? AND ";

	private static final String _FINDER_COLUMN_S_L_LANGUAGEID_2 =
		"ddmFieldAttribute.languageId = ?";

	private static final String _FINDER_COLUMN_S_L_LANGUAGEID_3 =
		"(ddmFieldAttribute.languageId IS NULL OR ddmFieldAttribute.languageId = '')";

	private FinderPath _finderPathWithPaginationFindByAN_SAV;
	private FinderPath _finderPathWithoutPaginationFindByAN_SAV;
	private FinderPath _finderPathCountByAN_SAV;

	/**
	 * Returns all the ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @return the matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByAN_SAV(
		String attributeName, String smallAttributeValue) {

		return findByAN_SAV(
			attributeName, smallAttributeValue, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @return the range of matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByAN_SAV(
		String attributeName, String smallAttributeValue, int start, int end) {

		return findByAN_SAV(
			attributeName, smallAttributeValue, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByAN_SAV(
		String attributeName, String smallAttributeValue, int start, int end,
		OrderByComparator<DDMFieldAttribute> orderByComparator) {

		return findByAN_SAV(
			attributeName, smallAttributeValue, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findByAN_SAV(
		String attributeName, String smallAttributeValue, int start, int end,
		OrderByComparator<DDMFieldAttribute> orderByComparator,
		boolean useFinderCache) {

		attributeName = Objects.toString(attributeName, "");
		smallAttributeValue = Objects.toString(smallAttributeValue, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFieldAttribute.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByAN_SAV;
				finderArgs = new Object[] {attributeName, smallAttributeValue};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByAN_SAV;
			finderArgs = new Object[] {
				attributeName, smallAttributeValue, start, end,
				orderByComparator
			};
		}

		List<DDMFieldAttribute> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFieldAttribute>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFieldAttribute ddmFieldAttribute : list) {
					if (!attributeName.equals(
							ddmFieldAttribute.getAttributeName()) ||
						!smallAttributeValue.equals(
							ddmFieldAttribute.getSmallAttributeValue())) {

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

			sb.append(_SQL_SELECT_DDMFIELDATTRIBUTE_WHERE);

			boolean bindAttributeName = false;

			if (attributeName.isEmpty()) {
				sb.append(_FINDER_COLUMN_AN_SAV_ATTRIBUTENAME_3);
			}
			else {
				bindAttributeName = true;

				sb.append(_FINDER_COLUMN_AN_SAV_ATTRIBUTENAME_2);
			}

			boolean bindSmallAttributeValue = false;

			if (smallAttributeValue.isEmpty()) {
				sb.append(_FINDER_COLUMN_AN_SAV_SMALLATTRIBUTEVALUE_3);
			}
			else {
				bindSmallAttributeValue = true;

				sb.append(_FINDER_COLUMN_AN_SAV_SMALLATTRIBUTEVALUE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFieldAttributeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindAttributeName) {
					queryPos.add(attributeName);
				}

				if (bindSmallAttributeValue) {
					queryPos.add(smallAttributeValue);
				}

				list = (List<DDMFieldAttribute>)QueryUtil.list(
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
	 * Returns the first ddm field attribute in the ordered set where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute findByAN_SAV_First(
			String attributeName, String smallAttributeValue,
			OrderByComparator<DDMFieldAttribute> orderByComparator)
		throws NoSuchFieldAttributeException {

		DDMFieldAttribute ddmFieldAttribute = fetchByAN_SAV_First(
			attributeName, smallAttributeValue, orderByComparator);

		if (ddmFieldAttribute != null) {
			return ddmFieldAttribute;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("attributeName=");
		sb.append(attributeName);

		sb.append(", smallAttributeValue=");
		sb.append(smallAttributeValue);

		sb.append("}");

		throw new NoSuchFieldAttributeException(sb.toString());
	}

	/**
	 * Returns the first ddm field attribute in the ordered set where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute fetchByAN_SAV_First(
		String attributeName, String smallAttributeValue,
		OrderByComparator<DDMFieldAttribute> orderByComparator) {

		List<DDMFieldAttribute> list = findByAN_SAV(
			attributeName, smallAttributeValue, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm field attribute in the ordered set where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute findByAN_SAV_Last(
			String attributeName, String smallAttributeValue,
			OrderByComparator<DDMFieldAttribute> orderByComparator)
		throws NoSuchFieldAttributeException {

		DDMFieldAttribute ddmFieldAttribute = fetchByAN_SAV_Last(
			attributeName, smallAttributeValue, orderByComparator);

		if (ddmFieldAttribute != null) {
			return ddmFieldAttribute;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("attributeName=");
		sb.append(attributeName);

		sb.append(", smallAttributeValue=");
		sb.append(smallAttributeValue);

		sb.append("}");

		throw new NoSuchFieldAttributeException(sb.toString());
	}

	/**
	 * Returns the last ddm field attribute in the ordered set where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute fetchByAN_SAV_Last(
		String attributeName, String smallAttributeValue,
		OrderByComparator<DDMFieldAttribute> orderByComparator) {

		int count = countByAN_SAV(attributeName, smallAttributeValue);

		if (count == 0) {
			return null;
		}

		List<DDMFieldAttribute> list = findByAN_SAV(
			attributeName, smallAttributeValue, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm field attributes before and after the current ddm field attribute in the ordered set where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param fieldAttributeId the primary key of the current ddm field attribute
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm field attribute
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	@Override
	public DDMFieldAttribute[] findByAN_SAV_PrevAndNext(
			long fieldAttributeId, String attributeName,
			String smallAttributeValue,
			OrderByComparator<DDMFieldAttribute> orderByComparator)
		throws NoSuchFieldAttributeException {

		attributeName = Objects.toString(attributeName, "");
		smallAttributeValue = Objects.toString(smallAttributeValue, "");

		DDMFieldAttribute ddmFieldAttribute = findByPrimaryKey(
			fieldAttributeId);

		Session session = null;

		try {
			session = openSession();

			DDMFieldAttribute[] array = new DDMFieldAttributeImpl[3];

			array[0] = getByAN_SAV_PrevAndNext(
				session, ddmFieldAttribute, attributeName, smallAttributeValue,
				orderByComparator, true);

			array[1] = ddmFieldAttribute;

			array[2] = getByAN_SAV_PrevAndNext(
				session, ddmFieldAttribute, attributeName, smallAttributeValue,
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

	protected DDMFieldAttribute getByAN_SAV_PrevAndNext(
		Session session, DDMFieldAttribute ddmFieldAttribute,
		String attributeName, String smallAttributeValue,
		OrderByComparator<DDMFieldAttribute> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFIELDATTRIBUTE_WHERE);

		boolean bindAttributeName = false;

		if (attributeName.isEmpty()) {
			sb.append(_FINDER_COLUMN_AN_SAV_ATTRIBUTENAME_3);
		}
		else {
			bindAttributeName = true;

			sb.append(_FINDER_COLUMN_AN_SAV_ATTRIBUTENAME_2);
		}

		boolean bindSmallAttributeValue = false;

		if (smallAttributeValue.isEmpty()) {
			sb.append(_FINDER_COLUMN_AN_SAV_SMALLATTRIBUTEVALUE_3);
		}
		else {
			bindSmallAttributeValue = true;

			sb.append(_FINDER_COLUMN_AN_SAV_SMALLATTRIBUTEVALUE_2);
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
			sb.append(DDMFieldAttributeModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindAttributeName) {
			queryPos.add(attributeName);
		}

		if (bindSmallAttributeValue) {
			queryPos.add(smallAttributeValue);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFieldAttribute)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFieldAttribute> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63; from the database.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 */
	@Override
	public void removeByAN_SAV(
		String attributeName, String smallAttributeValue) {

		for (DDMFieldAttribute ddmFieldAttribute :
				findByAN_SAV(
					attributeName, smallAttributeValue, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ddmFieldAttribute);
		}
	}

	/**
	 * Returns the number of ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @return the number of matching ddm field attributes
	 */
	@Override
	public int countByAN_SAV(String attributeName, String smallAttributeValue) {
		attributeName = Objects.toString(attributeName, "");
		smallAttributeValue = Objects.toString(smallAttributeValue, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFieldAttribute.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByAN_SAV;

			finderArgs = new Object[] {attributeName, smallAttributeValue};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMFIELDATTRIBUTE_WHERE);

			boolean bindAttributeName = false;

			if (attributeName.isEmpty()) {
				sb.append(_FINDER_COLUMN_AN_SAV_ATTRIBUTENAME_3);
			}
			else {
				bindAttributeName = true;

				sb.append(_FINDER_COLUMN_AN_SAV_ATTRIBUTENAME_2);
			}

			boolean bindSmallAttributeValue = false;

			if (smallAttributeValue.isEmpty()) {
				sb.append(_FINDER_COLUMN_AN_SAV_SMALLATTRIBUTEVALUE_3);
			}
			else {
				bindSmallAttributeValue = true;

				sb.append(_FINDER_COLUMN_AN_SAV_SMALLATTRIBUTEVALUE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindAttributeName) {
					queryPos.add(attributeName);
				}

				if (bindSmallAttributeValue) {
					queryPos.add(smallAttributeValue);
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

	private static final String _FINDER_COLUMN_AN_SAV_ATTRIBUTENAME_2 =
		"ddmFieldAttribute.attributeName = ? AND ";

	private static final String _FINDER_COLUMN_AN_SAV_ATTRIBUTENAME_3 =
		"(ddmFieldAttribute.attributeName IS NULL OR ddmFieldAttribute.attributeName = '') AND ";

	private static final String _FINDER_COLUMN_AN_SAV_SMALLATTRIBUTEVALUE_2 =
		"ddmFieldAttribute.smallAttributeValue = ?";

	private static final String _FINDER_COLUMN_AN_SAV_SMALLATTRIBUTEVALUE_3 =
		"(ddmFieldAttribute.smallAttributeValue IS NULL OR ddmFieldAttribute.smallAttributeValue = '')";

	private FinderPath _finderPathFetchByF_AN_L;
	private FinderPath _finderPathCountByF_AN_L;

	/**
	 * Returns the ddm field attribute where fieldId = &#63; and attributeName = &#63; and languageId = &#63; or throws a <code>NoSuchFieldAttributeException</code> if it could not be found.
	 *
	 * @param fieldId the field ID
	 * @param attributeName the attribute name
	 * @param languageId the language ID
	 * @return the matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute findByF_AN_L(
			long fieldId, String attributeName, String languageId)
		throws NoSuchFieldAttributeException {

		DDMFieldAttribute ddmFieldAttribute = fetchByF_AN_L(
			fieldId, attributeName, languageId);

		if (ddmFieldAttribute == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("fieldId=");
			sb.append(fieldId);

			sb.append(", attributeName=");
			sb.append(attributeName);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFieldAttributeException(sb.toString());
		}

		return ddmFieldAttribute;
	}

	/**
	 * Returns the ddm field attribute where fieldId = &#63; and attributeName = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fieldId the field ID
	 * @param attributeName the attribute name
	 * @param languageId the language ID
	 * @return the matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute fetchByF_AN_L(
		long fieldId, String attributeName, String languageId) {

		return fetchByF_AN_L(fieldId, attributeName, languageId, true);
	}

	/**
	 * Returns the ddm field attribute where fieldId = &#63; and attributeName = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fieldId the field ID
	 * @param attributeName the attribute name
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	@Override
	public DDMFieldAttribute fetchByF_AN_L(
		long fieldId, String attributeName, String languageId,
		boolean useFinderCache) {

		attributeName = Objects.toString(attributeName, "");
		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFieldAttribute.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {fieldId, attributeName, languageId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByF_AN_L, finderArgs);
		}

		if (result instanceof DDMFieldAttribute) {
			DDMFieldAttribute ddmFieldAttribute = (DDMFieldAttribute)result;

			if ((fieldId != ddmFieldAttribute.getFieldId()) ||
				!Objects.equals(
					attributeName, ddmFieldAttribute.getAttributeName()) ||
				!Objects.equals(
					languageId, ddmFieldAttribute.getLanguageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_DDMFIELDATTRIBUTE_WHERE);

			sb.append(_FINDER_COLUMN_F_AN_L_FIELDID_2);

			boolean bindAttributeName = false;

			if (attributeName.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_AN_L_ATTRIBUTENAME_3);
			}
			else {
				bindAttributeName = true;

				sb.append(_FINDER_COLUMN_F_AN_L_ATTRIBUTENAME_2);
			}

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_AN_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_F_AN_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fieldId);

				if (bindAttributeName) {
					queryPos.add(attributeName);
				}

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				List<DDMFieldAttribute> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByF_AN_L, finderArgs, list);
					}
				}
				else {
					DDMFieldAttribute ddmFieldAttribute = list.get(0);

					result = ddmFieldAttribute;

					cacheResult(ddmFieldAttribute);
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
			return (DDMFieldAttribute)result;
		}
	}

	/**
	 * Removes the ddm field attribute where fieldId = &#63; and attributeName = &#63; and languageId = &#63; from the database.
	 *
	 * @param fieldId the field ID
	 * @param attributeName the attribute name
	 * @param languageId the language ID
	 * @return the ddm field attribute that was removed
	 */
	@Override
	public DDMFieldAttribute removeByF_AN_L(
			long fieldId, String attributeName, String languageId)
		throws NoSuchFieldAttributeException {

		DDMFieldAttribute ddmFieldAttribute = findByF_AN_L(
			fieldId, attributeName, languageId);

		return remove(ddmFieldAttribute);
	}

	/**
	 * Returns the number of ddm field attributes where fieldId = &#63; and attributeName = &#63; and languageId = &#63;.
	 *
	 * @param fieldId the field ID
	 * @param attributeName the attribute name
	 * @param languageId the language ID
	 * @return the number of matching ddm field attributes
	 */
	@Override
	public int countByF_AN_L(
		long fieldId, String attributeName, String languageId) {

		attributeName = Objects.toString(attributeName, "");
		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFieldAttribute.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByF_AN_L;

			finderArgs = new Object[] {fieldId, attributeName, languageId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_DDMFIELDATTRIBUTE_WHERE);

			sb.append(_FINDER_COLUMN_F_AN_L_FIELDID_2);

			boolean bindAttributeName = false;

			if (attributeName.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_AN_L_ATTRIBUTENAME_3);
			}
			else {
				bindAttributeName = true;

				sb.append(_FINDER_COLUMN_F_AN_L_ATTRIBUTENAME_2);
			}

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_AN_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_F_AN_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fieldId);

				if (bindAttributeName) {
					queryPos.add(attributeName);
				}

				if (bindLanguageId) {
					queryPos.add(languageId);
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

	private static final String _FINDER_COLUMN_F_AN_L_FIELDID_2 =
		"ddmFieldAttribute.fieldId = ? AND ";

	private static final String _FINDER_COLUMN_F_AN_L_ATTRIBUTENAME_2 =
		"ddmFieldAttribute.attributeName = ? AND ";

	private static final String _FINDER_COLUMN_F_AN_L_ATTRIBUTENAME_3 =
		"(ddmFieldAttribute.attributeName IS NULL OR ddmFieldAttribute.attributeName = '') AND ";

	private static final String _FINDER_COLUMN_F_AN_L_LANGUAGEID_2 =
		"ddmFieldAttribute.languageId = ?";

	private static final String _FINDER_COLUMN_F_AN_L_LANGUAGEID_3 =
		"(ddmFieldAttribute.languageId IS NULL OR ddmFieldAttribute.languageId = '')";

	public DDMFieldAttributePersistenceImpl() {
		setModelClass(DDMFieldAttribute.class);

		setModelImplClass(DDMFieldAttributeImpl.class);
		setModelPKClass(long.class);

		setTable(DDMFieldAttributeTable.INSTANCE);
	}

	/**
	 * Caches the ddm field attribute in the entity cache if it is enabled.
	 *
	 * @param ddmFieldAttribute the ddm field attribute
	 */
	@Override
	public void cacheResult(DDMFieldAttribute ddmFieldAttribute) {
		if (ddmFieldAttribute.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			DDMFieldAttributeImpl.class, ddmFieldAttribute.getPrimaryKey(),
			ddmFieldAttribute);

		finderCache.putResult(
			_finderPathFetchByF_AN_L,
			new Object[] {
				ddmFieldAttribute.getFieldId(),
				ddmFieldAttribute.getAttributeName(),
				ddmFieldAttribute.getLanguageId()
			},
			ddmFieldAttribute);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ddm field attributes in the entity cache if it is enabled.
	 *
	 * @param ddmFieldAttributes the ddm field attributes
	 */
	@Override
	public void cacheResult(List<DDMFieldAttribute> ddmFieldAttributes) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ddmFieldAttributes.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DDMFieldAttribute ddmFieldAttribute : ddmFieldAttributes) {
			if (ddmFieldAttribute.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					DDMFieldAttributeImpl.class,
					ddmFieldAttribute.getPrimaryKey()) == null) {

				cacheResult(ddmFieldAttribute);
			}
		}
	}

	/**
	 * Clears the cache for all ddm field attributes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMFieldAttributeImpl.class);

		finderCache.clearCache(DDMFieldAttributeImpl.class);
	}

	/**
	 * Clears the cache for the ddm field attribute.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMFieldAttribute ddmFieldAttribute) {
		entityCache.removeResult(
			DDMFieldAttributeImpl.class, ddmFieldAttribute);
	}

	@Override
	public void clearCache(List<DDMFieldAttribute> ddmFieldAttributes) {
		for (DDMFieldAttribute ddmFieldAttribute : ddmFieldAttributes) {
			entityCache.removeResult(
				DDMFieldAttributeImpl.class, ddmFieldAttribute);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DDMFieldAttributeImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DDMFieldAttributeImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMFieldAttributeModelImpl ddmFieldAttributeModelImpl) {

		Object[] args = new Object[] {
			ddmFieldAttributeModelImpl.getFieldId(),
			ddmFieldAttributeModelImpl.getAttributeName(),
			ddmFieldAttributeModelImpl.getLanguageId()
		};

		finderCache.putResult(_finderPathCountByF_AN_L, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByF_AN_L, args, ddmFieldAttributeModelImpl);
	}

	/**
	 * Creates a new ddm field attribute with the primary key. Does not add the ddm field attribute to the database.
	 *
	 * @param fieldAttributeId the primary key for the new ddm field attribute
	 * @return the new ddm field attribute
	 */
	@Override
	public DDMFieldAttribute create(long fieldAttributeId) {
		DDMFieldAttribute ddmFieldAttribute = new DDMFieldAttributeImpl();

		ddmFieldAttribute.setNew(true);
		ddmFieldAttribute.setPrimaryKey(fieldAttributeId);

		ddmFieldAttribute.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ddmFieldAttribute;
	}

	/**
	 * Removes the ddm field attribute with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fieldAttributeId the primary key of the ddm field attribute
	 * @return the ddm field attribute that was removed
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	@Override
	public DDMFieldAttribute remove(long fieldAttributeId)
		throws NoSuchFieldAttributeException {

		return remove((Serializable)fieldAttributeId);
	}

	/**
	 * Removes the ddm field attribute with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddm field attribute
	 * @return the ddm field attribute that was removed
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	@Override
	public DDMFieldAttribute remove(Serializable primaryKey)
		throws NoSuchFieldAttributeException {

		Session session = null;

		try {
			session = openSession();

			DDMFieldAttribute ddmFieldAttribute =
				(DDMFieldAttribute)session.get(
					DDMFieldAttributeImpl.class, primaryKey);

			if (ddmFieldAttribute == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFieldAttributeException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ddmFieldAttribute);
		}
		catch (NoSuchFieldAttributeException noSuchEntityException) {
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
	protected DDMFieldAttribute removeImpl(
		DDMFieldAttribute ddmFieldAttribute) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmFieldAttribute)) {
				ddmFieldAttribute = (DDMFieldAttribute)session.get(
					DDMFieldAttributeImpl.class,
					ddmFieldAttribute.getPrimaryKeyObj());
			}

			if ((ddmFieldAttribute != null) &&
				ctPersistenceHelper.isRemove(ddmFieldAttribute)) {

				session.delete(ddmFieldAttribute);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmFieldAttribute != null) {
			clearCache(ddmFieldAttribute);
		}

		return ddmFieldAttribute;
	}

	@Override
	public DDMFieldAttribute updateImpl(DDMFieldAttribute ddmFieldAttribute) {
		boolean isNew = ddmFieldAttribute.isNew();

		if (!(ddmFieldAttribute instanceof DDMFieldAttributeModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ddmFieldAttribute.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ddmFieldAttribute);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddmFieldAttribute proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDMFieldAttribute implementation " +
					ddmFieldAttribute.getClass());
		}

		DDMFieldAttributeModelImpl ddmFieldAttributeModelImpl =
			(DDMFieldAttributeModelImpl)ddmFieldAttribute;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ddmFieldAttribute)) {
				if (!isNew) {
					session.evict(
						DDMFieldAttributeImpl.class,
						ddmFieldAttribute.getPrimaryKeyObj());
				}

				session.save(ddmFieldAttribute);
			}
			else {
				ddmFieldAttribute = (DDMFieldAttribute)session.merge(
					ddmFieldAttribute);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmFieldAttribute.getCtCollectionId() != 0) {
			if (isNew) {
				ddmFieldAttribute.setNew(false);
			}

			ddmFieldAttribute.resetOriginalValues();

			return ddmFieldAttribute;
		}

		entityCache.putResult(
			DDMFieldAttributeImpl.class, ddmFieldAttributeModelImpl, false,
			true);

		cacheUniqueFindersCache(ddmFieldAttributeModelImpl);

		if (isNew) {
			ddmFieldAttribute.setNew(false);
		}

		ddmFieldAttribute.resetOriginalValues();

		return ddmFieldAttribute;
	}

	/**
	 * Returns the ddm field attribute with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm field attribute
	 * @return the ddm field attribute
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	@Override
	public DDMFieldAttribute findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFieldAttributeException {

		DDMFieldAttribute ddmFieldAttribute = fetchByPrimaryKey(primaryKey);

		if (ddmFieldAttribute == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFieldAttributeException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ddmFieldAttribute;
	}

	/**
	 * Returns the ddm field attribute with the primary key or throws a <code>NoSuchFieldAttributeException</code> if it could not be found.
	 *
	 * @param fieldAttributeId the primary key of the ddm field attribute
	 * @return the ddm field attribute
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	@Override
	public DDMFieldAttribute findByPrimaryKey(long fieldAttributeId)
		throws NoSuchFieldAttributeException {

		return findByPrimaryKey((Serializable)fieldAttributeId);
	}

	/**
	 * Returns the ddm field attribute with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm field attribute
	 * @return the ddm field attribute, or <code>null</code> if a ddm field attribute with the primary key could not be found
	 */
	@Override
	public DDMFieldAttribute fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(DDMFieldAttribute.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		DDMFieldAttribute ddmFieldAttribute = null;

		Session session = null;

		try {
			session = openSession();

			ddmFieldAttribute = (DDMFieldAttribute)session.get(
				DDMFieldAttributeImpl.class, primaryKey);

			if (ddmFieldAttribute != null) {
				cacheResult(ddmFieldAttribute);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ddmFieldAttribute;
	}

	/**
	 * Returns the ddm field attribute with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fieldAttributeId the primary key of the ddm field attribute
	 * @return the ddm field attribute, or <code>null</code> if a ddm field attribute with the primary key could not be found
	 */
	@Override
	public DDMFieldAttribute fetchByPrimaryKey(long fieldAttributeId) {
		return fetchByPrimaryKey((Serializable)fieldAttributeId);
	}

	@Override
	public Map<Serializable, DDMFieldAttribute> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(DDMFieldAttribute.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMFieldAttribute> map =
			new HashMap<Serializable, DDMFieldAttribute>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMFieldAttribute ddmFieldAttribute = fetchByPrimaryKey(primaryKey);

			if (ddmFieldAttribute != null) {
				map.put(primaryKey, ddmFieldAttribute);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

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

			for (DDMFieldAttribute ddmFieldAttribute :
					(List<DDMFieldAttribute>)query.list()) {

				map.put(
					ddmFieldAttribute.getPrimaryKeyObj(), ddmFieldAttribute);

				cacheResult(ddmFieldAttribute);
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
	 * Returns all the ddm field attributes.
	 *
	 * @return the ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm field attributes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @return the range of ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm field attributes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findAll(
		int start, int end,
		OrderByComparator<DDMFieldAttribute> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm field attributes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm field attributes
	 */
	@Override
	public List<DDMFieldAttribute> findAll(
		int start, int end,
		OrderByComparator<DDMFieldAttribute> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFieldAttribute.class);

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

		List<DDMFieldAttribute> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFieldAttribute>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DDMFIELDATTRIBUTE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DDMFIELDATTRIBUTE;

				sql = sql.concat(DDMFieldAttributeModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DDMFieldAttribute>)QueryUtil.list(
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
	 * Removes all the ddm field attributes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMFieldAttribute ddmFieldAttribute : findAll()) {
			remove(ddmFieldAttribute);
		}
	}

	/**
	 * Returns the number of ddm field attributes.
	 *
	 * @return the number of ddm field attributes
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFieldAttribute.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_DDMFIELDATTRIBUTE);

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
		return "fieldAttributeId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDMFIELDATTRIBUTE;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return DDMFieldAttributeModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DDMFieldAttribute";
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
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("fieldId");
		ctStrictColumnNames.add("storageId");
		ctStrictColumnNames.add("attributeName");
		ctStrictColumnNames.add("languageId");
		ctStrictColumnNames.add("largeAttributeValue");
		ctStrictColumnNames.add("smallAttributeValue");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("fieldAttributeId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"fieldId", "attributeName", "languageId"});
	}

	/**
	 * Initializes the ddm field attribute persistence.
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

		_finderPathWithPaginationFindByStorageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStorageId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"storageId"}, true);

		_finderPathWithoutPaginationFindByStorageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStorageId",
			new String[] {Long.class.getName()}, new String[] {"storageId"},
			true);

		_finderPathCountByStorageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStorageId",
			new String[] {Long.class.getName()}, new String[] {"storageId"},
			false);

		_finderPathWithPaginationFindByS_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"storageId", "languageId"}, true);

		_finderPathWithoutPaginationFindByS_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_L",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"storageId", "languageId"}, true);

		_finderPathCountByS_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_L",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"storageId", "languageId"}, false);

		_finderPathWithPaginationFindByAN_SAV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAN_SAV",
			new String[] {
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"attributeName", "smallAttributeValue"}, true);

		_finderPathWithoutPaginationFindByAN_SAV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAN_SAV",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"attributeName", "smallAttributeValue"}, true);

		_finderPathCountByAN_SAV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAN_SAV",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"attributeName", "smallAttributeValue"}, false);

		_finderPathFetchByF_AN_L = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByF_AN_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"fieldId", "attributeName", "languageId"}, true);

		_finderPathCountByF_AN_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_AN_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"fieldId", "attributeName", "languageId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DDMFieldAttributeImpl.class.getName());
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

	private static final String _SQL_SELECT_DDMFIELDATTRIBUTE =
		"SELECT ddmFieldAttribute FROM DDMFieldAttribute ddmFieldAttribute";

	private static final String _SQL_SELECT_DDMFIELDATTRIBUTE_WHERE =
		"SELECT ddmFieldAttribute FROM DDMFieldAttribute ddmFieldAttribute WHERE ";

	private static final String _SQL_COUNT_DDMFIELDATTRIBUTE =
		"SELECT COUNT(ddmFieldAttribute) FROM DDMFieldAttribute ddmFieldAttribute";

	private static final String _SQL_COUNT_DDMFIELDATTRIBUTE_WHERE =
		"SELECT COUNT(ddmFieldAttribute) FROM DDMFieldAttribute ddmFieldAttribute WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmFieldAttribute.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DDMFieldAttribute exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDMFieldAttribute exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFieldAttributePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private DDMFieldAttributeModelArgumentsResolver
		_ddmFieldAttributeModelArgumentsResolver;

}