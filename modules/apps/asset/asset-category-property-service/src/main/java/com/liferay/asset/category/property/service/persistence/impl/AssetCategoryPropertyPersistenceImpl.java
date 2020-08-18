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

package com.liferay.asset.category.property.service.persistence.impl;

import com.liferay.asset.category.property.exception.NoSuchCategoryPropertyException;
import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.model.AssetCategoryPropertyTable;
import com.liferay.asset.category.property.model.impl.AssetCategoryPropertyImpl;
import com.liferay.asset.category.property.model.impl.AssetCategoryPropertyModelImpl;
import com.liferay.asset.category.property.service.persistence.AssetCategoryPropertyPersistence;
import com.liferay.asset.category.property.service.persistence.impl.constants.AssetPersistenceConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
 * The persistence implementation for the asset category property service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AssetCategoryPropertyPersistence.class)
public class AssetCategoryPropertyPersistenceImpl
	extends BasePersistenceImpl<AssetCategoryProperty>
	implements AssetCategoryPropertyPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetCategoryPropertyUtil</code> to access the asset category property persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetCategoryPropertyImpl.class.getName();

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
	 * Returns all the asset category properties where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset category properties where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset category properties where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetCategoryProperty.class);

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

		List<AssetCategoryProperty> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetCategoryProperty>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetCategoryProperty assetCategoryProperty : list) {
					if (companyId != assetCategoryProperty.getCompanyId()) {
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

			sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<AssetCategoryProperty>)QueryUtil.list(
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
	 * Returns the first asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByCompanyId_First(
			long companyId,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCategoryPropertyException(sb.toString());
	}

	/**
	 * Returns the first asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		List<AssetCategoryProperty> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByCompanyId_Last(
			long companyId,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCategoryPropertyException(sb.toString());
	}

	/**
	 * Returns the last asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AssetCategoryProperty> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset category properties before and after the current asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param categoryPropertyId the primary key of the current asset category property
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category property
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty[] findByCompanyId_PrevAndNext(
			long categoryPropertyId, long companyId,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = findByPrimaryKey(
			categoryPropertyId);

		Session session = null;

		try {
			session = openSession();

			AssetCategoryProperty[] array = new AssetCategoryPropertyImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, assetCategoryProperty, companyId, orderByComparator,
				true);

			array[1] = assetCategoryProperty;

			array[2] = getByCompanyId_PrevAndNext(
				session, assetCategoryProperty, companyId, orderByComparator,
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

	protected AssetCategoryProperty getByCompanyId_PrevAndNext(
		Session session, AssetCategoryProperty assetCategoryProperty,
		long companyId,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

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
			sb.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
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
						assetCategoryProperty)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetCategoryProperty> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset category properties where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AssetCategoryProperty assetCategoryProperty :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetCategoryProperty);
		}
	}

	/**
	 * Returns the number of asset category properties where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching asset category properties
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetCategoryProperty.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"assetCategoryProperty.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByCategoryId;
	private FinderPath _finderPathCountByCategoryId;

	/**
	 * Returns all the asset category properties where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @return the matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCategoryId(long categoryId) {
		return findByCategoryId(
			categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset category properties where categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end) {

		return findByCategoryId(categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties where categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		return findByCategoryId(
			categoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset category properties where categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetCategoryProperty.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCategoryId;
				finderArgs = new Object[] {categoryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCategoryId;
			finderArgs = new Object[] {
				categoryId, start, end, orderByComparator
			};
		}

		List<AssetCategoryProperty> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetCategoryProperty>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetCategoryProperty assetCategoryProperty : list) {
					if (categoryId != assetCategoryProperty.getCategoryId()) {
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

			sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			sb.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(categoryId);

				list = (List<AssetCategoryProperty>)QueryUtil.list(
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
	 * Returns the first asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByCategoryId_First(
			long categoryId,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByCategoryId_First(
			categoryId, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("categoryId=");
		sb.append(categoryId);

		sb.append("}");

		throw new NoSuchCategoryPropertyException(sb.toString());
	}

	/**
	 * Returns the first asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByCategoryId_First(
		long categoryId,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		List<AssetCategoryProperty> list = findByCategoryId(
			categoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByCategoryId_Last(
			long categoryId,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByCategoryId_Last(
			categoryId, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("categoryId=");
		sb.append(categoryId);

		sb.append("}");

		throw new NoSuchCategoryPropertyException(sb.toString());
	}

	/**
	 * Returns the last asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByCategoryId_Last(
		long categoryId,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		int count = countByCategoryId(categoryId);

		if (count == 0) {
			return null;
		}

		List<AssetCategoryProperty> list = findByCategoryId(
			categoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset category properties before and after the current asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryPropertyId the primary key of the current asset category property
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category property
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty[] findByCategoryId_PrevAndNext(
			long categoryPropertyId, long categoryId,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = findByPrimaryKey(
			categoryPropertyId);

		Session session = null;

		try {
			session = openSession();

			AssetCategoryProperty[] array = new AssetCategoryPropertyImpl[3];

			array[0] = getByCategoryId_PrevAndNext(
				session, assetCategoryProperty, categoryId, orderByComparator,
				true);

			array[1] = assetCategoryProperty;

			array[2] = getByCategoryId_PrevAndNext(
				session, assetCategoryProperty, categoryId, orderByComparator,
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

	protected AssetCategoryProperty getByCategoryId_PrevAndNext(
		Session session, AssetCategoryProperty assetCategoryProperty,
		long categoryId,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

		sb.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

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
			sb.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(categoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetCategoryProperty)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetCategoryProperty> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset category properties where categoryId = &#63; from the database.
	 *
	 * @param categoryId the category ID
	 */
	@Override
	public void removeByCategoryId(long categoryId) {
		for (AssetCategoryProperty assetCategoryProperty :
				findByCategoryId(
					categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetCategoryProperty);
		}
	}

	/**
	 * Returns the number of asset category properties where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @return the number of matching asset category properties
	 */
	@Override
	public int countByCategoryId(long categoryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetCategoryProperty.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCategoryId;

			finderArgs = new Object[] {categoryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

			sb.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(categoryId);

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

	private static final String _FINDER_COLUMN_CATEGORYID_CATEGORYID_2 =
		"assetCategoryProperty.categoryId = ?";

	private FinderPath _finderPathWithPaginationFindByC_K;
	private FinderPath _finderPathWithoutPaginationFindByC_K;
	private FinderPath _finderPathCountByC_K;

	/**
	 * Returns all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByC_K(long companyId, String key) {
		return findByC_K(
			companyId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByC_K(
		long companyId, String key, int start, int end) {

		return findByC_K(companyId, key, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByC_K(
		long companyId, String key, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		return findByC_K(companyId, key, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByC_K(
		long companyId, String key, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean useFinderCache) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetCategoryProperty.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_K;
				finderArgs = new Object[] {companyId, key};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_K;
			finderArgs = new Object[] {
				companyId, key, start, end, orderByComparator
			};
		}

		List<AssetCategoryProperty> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetCategoryProperty>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetCategoryProperty assetCategoryProperty : list) {
					if ((companyId != assetCategoryProperty.getCompanyId()) ||
						!key.equals(assetCategoryProperty.getKey())) {

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

			sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKey) {
					queryPos.add(key);
				}

				list = (List<AssetCategoryProperty>)QueryUtil.list(
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
	 * Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByC_K_First(
			long companyId, String key,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByC_K_First(
			companyId, key, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", key=");
		sb.append(key);

		sb.append("}");

		throw new NoSuchCategoryPropertyException(sb.toString());
	}

	/**
	 * Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByC_K_First(
		long companyId, String key,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		List<AssetCategoryProperty> list = findByC_K(
			companyId, key, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByC_K_Last(
			long companyId, String key,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByC_K_Last(
			companyId, key, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", key=");
		sb.append(key);

		sb.append("}");

		throw new NoSuchCategoryPropertyException(sb.toString());
	}

	/**
	 * Returns the last asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByC_K_Last(
		long companyId, String key,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		int count = countByC_K(companyId, key);

		if (count == 0) {
			return null;
		}

		List<AssetCategoryProperty> list = findByC_K(
			companyId, key, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset category properties before and after the current asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param categoryPropertyId the primary key of the current asset category property
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category property
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty[] findByC_K_PrevAndNext(
			long categoryPropertyId, long companyId, String key,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		key = Objects.toString(key, "");

		AssetCategoryProperty assetCategoryProperty = findByPrimaryKey(
			categoryPropertyId);

		Session session = null;

		try {
			session = openSession();

			AssetCategoryProperty[] array = new AssetCategoryPropertyImpl[3];

			array[0] = getByC_K_PrevAndNext(
				session, assetCategoryProperty, companyId, key,
				orderByComparator, true);

			array[1] = assetCategoryProperty;

			array[2] = getByC_K_PrevAndNext(
				session, assetCategoryProperty, companyId, key,
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

	protected AssetCategoryProperty getByC_K_PrevAndNext(
		Session session, AssetCategoryProperty assetCategoryProperty,
		long companyId, String key,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

		sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

		boolean bindKey = false;

		if (key.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_K_KEY_3);
		}
		else {
			bindKey = true;

			sb.append(_FINDER_COLUMN_C_K_KEY_2);
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
			sb.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindKey) {
			queryPos.add(key);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetCategoryProperty)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetCategoryProperty> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset category properties where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 */
	@Override
	public void removeByC_K(long companyId, String key) {
		for (AssetCategoryProperty assetCategoryProperty :
				findByC_K(
					companyId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetCategoryProperty);
		}
	}

	/**
	 * Returns the number of asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching asset category properties
	 */
	@Override
	public int countByC_K(long companyId, String key) {
		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetCategoryProperty.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_K;

			finderArgs = new Object[] {companyId, key};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

			sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKey) {
					queryPos.add(key);
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

	private static final String _FINDER_COLUMN_C_K_COMPANYID_2 =
		"assetCategoryProperty.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_K_KEY_2 =
		"assetCategoryProperty.key = ?";

	private static final String _FINDER_COLUMN_C_K_KEY_3 =
		"(assetCategoryProperty.key IS NULL OR assetCategoryProperty.key = '')";

	private FinderPath _finderPathFetchByCA_K;
	private FinderPath _finderPathCountByCA_K;

	/**
	 * Returns the asset category property where categoryId = &#63; and key = &#63; or throws a <code>NoSuchCategoryPropertyException</code> if it could not be found.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByCA_K(long categoryId, String key)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByCA_K(
			categoryId, key);

		if (assetCategoryProperty == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("categoryId=");
			sb.append(categoryId);

			sb.append(", key=");
			sb.append(key);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCategoryPropertyException(sb.toString());
		}

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByCA_K(long categoryId, String key) {
		return fetchByCA_K(categoryId, key, true);
	}

	/**
	 * Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByCA_K(
		long categoryId, String key, boolean useFinderCache) {

		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetCategoryProperty.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {categoryId, key};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCA_K, finderArgs, this);
		}

		if (result instanceof AssetCategoryProperty) {
			AssetCategoryProperty assetCategoryProperty =
				(AssetCategoryProperty)result;

			if ((categoryId != assetCategoryProperty.getCategoryId()) ||
				!Objects.equals(key, assetCategoryProperty.getKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			sb.append(_FINDER_COLUMN_CA_K_CATEGORYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_CA_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_CA_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(categoryId);

				if (bindKey) {
					queryPos.add(key);
				}

				List<AssetCategoryProperty> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCA_K, finderArgs, list);
					}
				}
				else {
					AssetCategoryProperty assetCategoryProperty = list.get(0);

					result = assetCategoryProperty;

					cacheResult(assetCategoryProperty);
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
			return (AssetCategoryProperty)result;
		}
	}

	/**
	 * Removes the asset category property where categoryId = &#63; and key = &#63; from the database.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the asset category property that was removed
	 */
	@Override
	public AssetCategoryProperty removeByCA_K(long categoryId, String key)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = findByCA_K(
			categoryId, key);

		return remove(assetCategoryProperty);
	}

	/**
	 * Returns the number of asset category properties where categoryId = &#63; and key = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the number of matching asset category properties
	 */
	@Override
	public int countByCA_K(long categoryId, String key) {
		key = Objects.toString(key, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetCategoryProperty.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCA_K;

			finderArgs = new Object[] {categoryId, key};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

			sb.append(_FINDER_COLUMN_CA_K_CATEGORYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_CA_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_CA_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(categoryId);

				if (bindKey) {
					queryPos.add(key);
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

	private static final String _FINDER_COLUMN_CA_K_CATEGORYID_2 =
		"assetCategoryProperty.categoryId = ? AND ";

	private static final String _FINDER_COLUMN_CA_K_KEY_2 =
		"assetCategoryProperty.key = ?";

	private static final String _FINDER_COLUMN_CA_K_KEY_3 =
		"(assetCategoryProperty.key IS NULL OR assetCategoryProperty.key = '')";

	public AssetCategoryPropertyPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetCategoryProperty.class);

		setModelImplClass(AssetCategoryPropertyImpl.class);
		setModelPKClass(long.class);

		setTable(AssetCategoryPropertyTable.INSTANCE);
	}

	/**
	 * Caches the asset category property in the entity cache if it is enabled.
	 *
	 * @param assetCategoryProperty the asset category property
	 */
	@Override
	public void cacheResult(AssetCategoryProperty assetCategoryProperty) {
		if (assetCategoryProperty.getCtCollectionId() != 0) {
			assetCategoryProperty.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			AssetCategoryPropertyImpl.class,
			assetCategoryProperty.getPrimaryKey(), assetCategoryProperty);

		finderCache.putResult(
			_finderPathFetchByCA_K,
			new Object[] {
				assetCategoryProperty.getCategoryId(),
				assetCategoryProperty.getKey()
			},
			assetCategoryProperty);

		assetCategoryProperty.resetOriginalValues();
	}

	/**
	 * Caches the asset category properties in the entity cache if it is enabled.
	 *
	 * @param assetCategoryProperties the asset category properties
	 */
	@Override
	public void cacheResult(
		List<AssetCategoryProperty> assetCategoryProperties) {

		for (AssetCategoryProperty assetCategoryProperty :
				assetCategoryProperties) {

			if (assetCategoryProperty.getCtCollectionId() != 0) {
				assetCategoryProperty.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					AssetCategoryPropertyImpl.class,
					assetCategoryProperty.getPrimaryKey()) == null) {

				cacheResult(assetCategoryProperty);
			}
			else {
				assetCategoryProperty.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset category properties.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetCategoryPropertyImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset category property.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetCategoryProperty assetCategoryProperty) {
		entityCache.removeResult(
			AssetCategoryPropertyImpl.class,
			assetCategoryProperty.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AssetCategoryPropertyModelImpl)assetCategoryProperty, true);
	}

	@Override
	public void clearCache(
		List<AssetCategoryProperty> assetCategoryProperties) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetCategoryProperty assetCategoryProperty :
				assetCategoryProperties) {

			entityCache.removeResult(
				AssetCategoryPropertyImpl.class,
				assetCategoryProperty.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetCategoryPropertyModelImpl)assetCategoryProperty, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AssetCategoryPropertyImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetCategoryPropertyModelImpl assetCategoryPropertyModelImpl) {

		Object[] args = new Object[] {
			assetCategoryPropertyModelImpl.getCategoryId(),
			assetCategoryPropertyModelImpl.getKey()
		};

		finderCache.putResult(
			_finderPathCountByCA_K, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByCA_K, args, assetCategoryPropertyModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		AssetCategoryPropertyModelImpl assetCategoryPropertyModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetCategoryPropertyModelImpl.getCategoryId(),
				assetCategoryPropertyModelImpl.getKey()
			};

			finderCache.removeResult(_finderPathCountByCA_K, args);
			finderCache.removeResult(_finderPathFetchByCA_K, args);
		}

		if ((assetCategoryPropertyModelImpl.getColumnBitmask() &
			 _finderPathFetchByCA_K.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetCategoryPropertyModelImpl.getOriginalCategoryId(),
				assetCategoryPropertyModelImpl.getOriginalKey()
			};

			finderCache.removeResult(_finderPathCountByCA_K, args);
			finderCache.removeResult(_finderPathFetchByCA_K, args);
		}
	}

	/**
	 * Creates a new asset category property with the primary key. Does not add the asset category property to the database.
	 *
	 * @param categoryPropertyId the primary key for the new asset category property
	 * @return the new asset category property
	 */
	@Override
	public AssetCategoryProperty create(long categoryPropertyId) {
		AssetCategoryProperty assetCategoryProperty =
			new AssetCategoryPropertyImpl();

		assetCategoryProperty.setNew(true);
		assetCategoryProperty.setPrimaryKey(categoryPropertyId);

		assetCategoryProperty.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetCategoryProperty;
	}

	/**
	 * Removes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param categoryPropertyId the primary key of the asset category property
	 * @return the asset category property that was removed
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty remove(long categoryPropertyId)
		throws NoSuchCategoryPropertyException {

		return remove((Serializable)categoryPropertyId);
	}

	/**
	 * Removes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset category property
	 * @return the asset category property that was removed
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty remove(Serializable primaryKey)
		throws NoSuchCategoryPropertyException {

		Session session = null;

		try {
			session = openSession();

			AssetCategoryProperty assetCategoryProperty =
				(AssetCategoryProperty)session.get(
					AssetCategoryPropertyImpl.class, primaryKey);

			if (assetCategoryProperty == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCategoryPropertyException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetCategoryProperty);
		}
		catch (NoSuchCategoryPropertyException noSuchEntityException) {
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
	protected AssetCategoryProperty removeImpl(
		AssetCategoryProperty assetCategoryProperty) {

		if (!ctPersistenceHelper.isRemove(assetCategoryProperty)) {
			return assetCategoryProperty;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetCategoryProperty)) {
				assetCategoryProperty = (AssetCategoryProperty)session.get(
					AssetCategoryPropertyImpl.class,
					assetCategoryProperty.getPrimaryKeyObj());
			}

			if (assetCategoryProperty != null) {
				session.delete(assetCategoryProperty);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetCategoryProperty != null) {
			clearCache(assetCategoryProperty);
		}

		return assetCategoryProperty;
	}

	@Override
	public AssetCategoryProperty updateImpl(
		AssetCategoryProperty assetCategoryProperty) {

		boolean isNew = assetCategoryProperty.isNew();

		if (!(assetCategoryProperty instanceof
				AssetCategoryPropertyModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetCategoryProperty.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetCategoryProperty);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetCategoryProperty proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetCategoryProperty implementation " +
					assetCategoryProperty.getClass());
		}

		AssetCategoryPropertyModelImpl assetCategoryPropertyModelImpl =
			(AssetCategoryPropertyModelImpl)assetCategoryProperty;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetCategoryProperty.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetCategoryProperty.setCreateDate(now);
			}
			else {
				assetCategoryProperty.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!assetCategoryPropertyModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetCategoryProperty.setModifiedDate(now);
			}
			else {
				assetCategoryProperty.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(assetCategoryProperty)) {
				if (!isNew) {
					session.evict(
						AssetCategoryPropertyImpl.class,
						assetCategoryProperty.getPrimaryKeyObj());
				}

				session.save(assetCategoryProperty);

				assetCategoryProperty.setNew(false);
			}
			else {
				assetCategoryProperty = (AssetCategoryProperty)session.merge(
					assetCategoryProperty);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetCategoryProperty.getCtCollectionId() != 0) {
			assetCategoryProperty.resetOriginalValues();

			return assetCategoryProperty;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				assetCategoryPropertyModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				assetCategoryPropertyModelImpl.getCategoryId()
			};

			finderCache.removeResult(_finderPathCountByCategoryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCategoryId, args);

			args = new Object[] {
				assetCategoryPropertyModelImpl.getCompanyId(),
				assetCategoryPropertyModelImpl.getKey()
			};

			finderCache.removeResult(_finderPathCountByC_K, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_K, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetCategoryPropertyModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetCategoryPropertyModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {
					assetCategoryPropertyModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((assetCategoryPropertyModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCategoryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetCategoryPropertyModelImpl.getOriginalCategoryId()
				};

				finderCache.removeResult(_finderPathCountByCategoryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCategoryId, args);

				args = new Object[] {
					assetCategoryPropertyModelImpl.getCategoryId()
				};

				finderCache.removeResult(_finderPathCountByCategoryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCategoryId, args);
			}

			if ((assetCategoryPropertyModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_K.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetCategoryPropertyModelImpl.getOriginalCompanyId(),
					assetCategoryPropertyModelImpl.getOriginalKey()
				};

				finderCache.removeResult(_finderPathCountByC_K, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_K, args);

				args = new Object[] {
					assetCategoryPropertyModelImpl.getCompanyId(),
					assetCategoryPropertyModelImpl.getKey()
				};

				finderCache.removeResult(_finderPathCountByC_K, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_K, args);
			}
		}

		entityCache.putResult(
			AssetCategoryPropertyImpl.class,
			assetCategoryProperty.getPrimaryKey(), assetCategoryProperty,
			false);

		clearUniqueFindersCache(assetCategoryPropertyModelImpl, false);
		cacheUniqueFindersCache(assetCategoryPropertyModelImpl);

		assetCategoryProperty.resetOriginalValues();

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset category property
	 * @return the asset category property
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByPrimaryKey(
			primaryKey);

		if (assetCategoryProperty == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCategoryPropertyException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property with the primary key or throws a <code>NoSuchCategoryPropertyException</code> if it could not be found.
	 *
	 * @param categoryPropertyId the primary key of the asset category property
	 * @return the asset category property
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty findByPrimaryKey(long categoryPropertyId)
		throws NoSuchCategoryPropertyException {

		return findByPrimaryKey((Serializable)categoryPropertyId);
	}

	/**
	 * Returns the asset category property with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset category property
	 * @return the asset category property, or <code>null</code> if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(AssetCategoryProperty.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		AssetCategoryProperty assetCategoryProperty = null;

		Session session = null;

		try {
			session = openSession();

			assetCategoryProperty = (AssetCategoryProperty)session.get(
				AssetCategoryPropertyImpl.class, primaryKey);

			if (assetCategoryProperty != null) {
				cacheResult(assetCategoryProperty);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param categoryPropertyId the primary key of the asset category property
	 * @return the asset category property, or <code>null</code> if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByPrimaryKey(long categoryPropertyId) {
		return fetchByPrimaryKey((Serializable)categoryPropertyId);
	}

	@Override
	public Map<Serializable, AssetCategoryProperty> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(AssetCategoryProperty.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetCategoryProperty> map =
			new HashMap<Serializable, AssetCategoryProperty>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetCategoryProperty assetCategoryProperty = fetchByPrimaryKey(
				primaryKey);

			if (assetCategoryProperty != null) {
				map.put(primaryKey, assetCategoryProperty);
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

			for (AssetCategoryProperty assetCategoryProperty :
					(List<AssetCategoryProperty>)query.list()) {

				map.put(
					assetCategoryProperty.getPrimaryKeyObj(),
					assetCategoryProperty);

				cacheResult(assetCategoryProperty);
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
	 * Returns all the asset category properties.
	 *
	 * @return the asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset category properties.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findAll(
		int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset category properties.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findAll(
		int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetCategoryProperty.class);

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

		List<AssetCategoryProperty> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetCategoryProperty>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETCATEGORYPROPERTY;

				sql = sql.concat(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetCategoryProperty>)QueryUtil.list(
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
	 * Removes all the asset category properties from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetCategoryProperty assetCategoryProperty : findAll()) {
			remove(assetCategoryProperty);
		}
	}

	/**
	 * Returns the number of asset category properties.
	 *
	 * @return the number of asset category properties
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetCategoryProperty.class);

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
					_SQL_COUNT_ASSETCATEGORYPROPERTY);

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
		return "categoryPropertyId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETCATEGORYPROPERTY;
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
		return AssetCategoryPropertyModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetCategoryProperty";
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
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("categoryId");
		ctStrictColumnNames.add("key_");
		ctStrictColumnNames.add("value");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("categoryPropertyId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"categoryId", "key_"});
	}

	/**
	 * Initializes the asset category property persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			AssetCategoryPropertyModelImpl.COMPANYID_COLUMN_BITMASK |
			AssetCategoryPropertyModelImpl.KEY_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCategoryId = new FinderPath(
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCategoryId = new FinderPath(
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCategoryId",
			new String[] {Long.class.getName()},
			AssetCategoryPropertyModelImpl.CATEGORYID_COLUMN_BITMASK |
			AssetCategoryPropertyModelImpl.KEY_COLUMN_BITMASK);

		_finderPathCountByCategoryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCategoryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_K = new FinderPath(
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_K",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_K = new FinderPath(
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			AssetCategoryPropertyModelImpl.COMPANYID_COLUMN_BITMASK |
			AssetCategoryPropertyModelImpl.KEY_COLUMN_BITMASK);

		_finderPathCountByC_K = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByCA_K = new FinderPath(
			AssetCategoryPropertyImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCA_K",
			new String[] {Long.class.getName(), String.class.getName()},
			AssetCategoryPropertyModelImpl.CATEGORYID_COLUMN_BITMASK |
			AssetCategoryPropertyModelImpl.KEY_COLUMN_BITMASK);

		_finderPathCountByCA_K = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCA_K",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AssetCategoryPropertyImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_ASSETCATEGORYPROPERTY =
		"SELECT assetCategoryProperty FROM AssetCategoryProperty assetCategoryProperty";

	private static final String _SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE =
		"SELECT assetCategoryProperty FROM AssetCategoryProperty assetCategoryProperty WHERE ";

	private static final String _SQL_COUNT_ASSETCATEGORYPROPERTY =
		"SELECT COUNT(assetCategoryProperty) FROM AssetCategoryProperty assetCategoryProperty";

	private static final String _SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE =
		"SELECT COUNT(assetCategoryProperty) FROM AssetCategoryProperty assetCategoryProperty WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"assetCategoryProperty.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetCategoryProperty exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetCategoryProperty exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryPropertyPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"key"});

	static {
		try {
			Class.forName(AssetPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}