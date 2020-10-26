/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.application.service.persistence.impl;

import com.liferay.commerce.application.exception.NoSuchApplicationBrandException;
import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.commerce.application.model.CommerceApplicationBrandTable;
import com.liferay.commerce.application.model.impl.CommerceApplicationBrandImpl;
import com.liferay.commerce.application.model.impl.CommerceApplicationBrandModelImpl;
import com.liferay.commerce.application.service.persistence.CommerceApplicationBrandPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce application brand service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceApplicationBrandPersistenceImpl
	extends BasePersistenceImpl<CommerceApplicationBrand>
	implements CommerceApplicationBrandPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceApplicationBrandUtil</code> to access the commerce application brand persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceApplicationBrandImpl.class.getName();

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
	 * Returns all the commerce application brands where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce application brands
	 */
	@Override
	public List<CommerceApplicationBrand> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce application brands where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @return the range of matching commerce application brands
	 */
	@Override
	public List<CommerceApplicationBrand> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application brands where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application brands
	 */
	@Override
	public List<CommerceApplicationBrand> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce application brands where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application brands
	 */
	@Override
	public List<CommerceApplicationBrand> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator,
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

		List<CommerceApplicationBrand> list = null;

		if (useFinderCache) {
			list = (List<CommerceApplicationBrand>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceApplicationBrand commerceApplicationBrand : list) {
					if (companyId != commerceApplicationBrand.getCompanyId()) {
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

			sb.append(_SQL_SELECT_COMMERCEAPPLICATIONBRAND_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceApplicationBrandModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceApplicationBrand>)QueryUtil.list(
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
	 * Returns the first commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application brand
	 * @throws NoSuchApplicationBrandException if a matching commerce application brand could not be found
	 */
	@Override
	public CommerceApplicationBrand findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceApplicationBrand> orderByComparator)
		throws NoSuchApplicationBrandException {

		CommerceApplicationBrand commerceApplicationBrand =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (commerceApplicationBrand != null) {
			return commerceApplicationBrand;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchApplicationBrandException(sb.toString());
	}

	/**
	 * Returns the first commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application brand, or <code>null</code> if a matching commerce application brand could not be found
	 */
	@Override
	public CommerceApplicationBrand fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		List<CommerceApplicationBrand> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application brand
	 * @throws NoSuchApplicationBrandException if a matching commerce application brand could not be found
	 */
	@Override
	public CommerceApplicationBrand findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceApplicationBrand> orderByComparator)
		throws NoSuchApplicationBrandException {

		CommerceApplicationBrand commerceApplicationBrand =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (commerceApplicationBrand != null) {
			return commerceApplicationBrand;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchApplicationBrandException(sb.toString());
	}

	/**
	 * Returns the last commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application brand, or <code>null</code> if a matching commerce application brand could not be found
	 */
	@Override
	public CommerceApplicationBrand fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceApplicationBrand> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce application brands before and after the current commerce application brand in the ordered set where companyId = &#63;.
	 *
	 * @param commerceApplicationBrandId the primary key of the current commerce application brand
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application brand
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	@Override
	public CommerceApplicationBrand[] findByCompanyId_PrevAndNext(
			long commerceApplicationBrandId, long companyId,
			OrderByComparator<CommerceApplicationBrand> orderByComparator)
		throws NoSuchApplicationBrandException {

		CommerceApplicationBrand commerceApplicationBrand = findByPrimaryKey(
			commerceApplicationBrandId);

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationBrand[] array =
				new CommerceApplicationBrandImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceApplicationBrand, companyId, orderByComparator,
				true);

			array[1] = commerceApplicationBrand;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceApplicationBrand, companyId, orderByComparator,
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

	protected CommerceApplicationBrand getByCompanyId_PrevAndNext(
		Session session, CommerceApplicationBrand commerceApplicationBrand,
		long companyId,
		OrderByComparator<CommerceApplicationBrand> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEAPPLICATIONBRAND_WHERE);

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
			sb.append(CommerceApplicationBrandModelImpl.ORDER_BY_JPQL);
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
						commerceApplicationBrand)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceApplicationBrand> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce application brands that the user has permission to view
	 */
	@Override
	public List<CommerceApplicationBrand> filterFindByCompanyId(
		long companyId) {

		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @return the range of matching commerce application brands that the user has permission to view
	 */
	@Override
	public List<CommerceApplicationBrand> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application brands that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application brands that the user has permission to view
	 */
	@Override
	public List<CommerceApplicationBrand> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEAPPLICATIONBRAND_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONBRAND_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONBRAND_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceApplicationBrandModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceApplicationBrandModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceApplicationBrand.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceApplicationBrandImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceApplicationBrandImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<CommerceApplicationBrand>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce application brands before and after the current commerce application brand in the ordered set of commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceApplicationBrandId the primary key of the current commerce application brand
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application brand
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	@Override
	public CommerceApplicationBrand[] filterFindByCompanyId_PrevAndNext(
			long commerceApplicationBrandId, long companyId,
			OrderByComparator<CommerceApplicationBrand> orderByComparator)
		throws NoSuchApplicationBrandException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				commerceApplicationBrandId, companyId, orderByComparator);
		}

		CommerceApplicationBrand commerceApplicationBrand = findByPrimaryKey(
			commerceApplicationBrandId);

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationBrand[] array =
				new CommerceApplicationBrandImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, commerceApplicationBrand, companyId, orderByComparator,
				true);

			array[1] = commerceApplicationBrand;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, commerceApplicationBrand, companyId, orderByComparator,
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

	protected CommerceApplicationBrand filterGetByCompanyId_PrevAndNext(
		Session session, CommerceApplicationBrand commerceApplicationBrand,
		long companyId,
		OrderByComparator<CommerceApplicationBrand> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEAPPLICATIONBRAND_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONBRAND_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONBRAND_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceApplicationBrandModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceApplicationBrandModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceApplicationBrand.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceApplicationBrandImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceApplicationBrandImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceApplicationBrand)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceApplicationBrand> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce application brands where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceApplicationBrand commerceApplicationBrand :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceApplicationBrand);
		}
	}

	/**
	 * Returns the number of commerce application brands where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce application brands
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEAPPLICATIONBRAND_WHERE);

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

	/**
	 * Returns the number of commerce application brands that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce application brands that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEAPPLICATIONBRAND_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceApplicationBrand.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"commerceApplicationBrand.companyId = ?";

	public CommerceApplicationBrandPersistenceImpl() {
		setModelClass(CommerceApplicationBrand.class);

		setModelImplClass(CommerceApplicationBrandImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceApplicationBrandTable.INSTANCE);
	}

	/**
	 * Caches the commerce application brand in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationBrand the commerce application brand
	 */
	@Override
	public void cacheResult(CommerceApplicationBrand commerceApplicationBrand) {
		entityCache.putResult(
			CommerceApplicationBrandImpl.class,
			commerceApplicationBrand.getPrimaryKey(), commerceApplicationBrand);
	}

	/**
	 * Caches the commerce application brands in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationBrands the commerce application brands
	 */
	@Override
	public void cacheResult(
		List<CommerceApplicationBrand> commerceApplicationBrands) {

		for (CommerceApplicationBrand commerceApplicationBrand :
				commerceApplicationBrands) {

			if (entityCache.getResult(
					CommerceApplicationBrandImpl.class,
					commerceApplicationBrand.getPrimaryKey()) == null) {

				cacheResult(commerceApplicationBrand);
			}
		}
	}

	/**
	 * Clears the cache for all commerce application brands.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceApplicationBrandImpl.class);

		finderCache.clearCache(CommerceApplicationBrandImpl.class);
	}

	/**
	 * Clears the cache for the commerce application brand.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceApplicationBrand commerceApplicationBrand) {
		entityCache.removeResult(
			CommerceApplicationBrandImpl.class, commerceApplicationBrand);
	}

	@Override
	public void clearCache(
		List<CommerceApplicationBrand> commerceApplicationBrands) {

		for (CommerceApplicationBrand commerceApplicationBrand :
				commerceApplicationBrands) {

			entityCache.removeResult(
				CommerceApplicationBrandImpl.class, commerceApplicationBrand);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceApplicationBrandImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceApplicationBrandImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce application brand with the primary key. Does not add the commerce application brand to the database.
	 *
	 * @param commerceApplicationBrandId the primary key for the new commerce application brand
	 * @return the new commerce application brand
	 */
	@Override
	public CommerceApplicationBrand create(long commerceApplicationBrandId) {
		CommerceApplicationBrand commerceApplicationBrand =
			new CommerceApplicationBrandImpl();

		commerceApplicationBrand.setNew(true);
		commerceApplicationBrand.setPrimaryKey(commerceApplicationBrandId);

		commerceApplicationBrand.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceApplicationBrand;
	}

	/**
	 * Removes the commerce application brand with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand that was removed
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	@Override
	public CommerceApplicationBrand remove(long commerceApplicationBrandId)
		throws NoSuchApplicationBrandException {

		return remove((Serializable)commerceApplicationBrandId);
	}

	/**
	 * Removes the commerce application brand with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce application brand
	 * @return the commerce application brand that was removed
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	@Override
	public CommerceApplicationBrand remove(Serializable primaryKey)
		throws NoSuchApplicationBrandException {

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationBrand commerceApplicationBrand =
				(CommerceApplicationBrand)session.get(
					CommerceApplicationBrandImpl.class, primaryKey);

			if (commerceApplicationBrand == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchApplicationBrandException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceApplicationBrand);
		}
		catch (NoSuchApplicationBrandException noSuchEntityException) {
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
	protected CommerceApplicationBrand removeImpl(
		CommerceApplicationBrand commerceApplicationBrand) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceApplicationBrand)) {
				commerceApplicationBrand =
					(CommerceApplicationBrand)session.get(
						CommerceApplicationBrandImpl.class,
						commerceApplicationBrand.getPrimaryKeyObj());
			}

			if (commerceApplicationBrand != null) {
				session.delete(commerceApplicationBrand);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceApplicationBrand != null) {
			clearCache(commerceApplicationBrand);
		}

		return commerceApplicationBrand;
	}

	@Override
	public CommerceApplicationBrand updateImpl(
		CommerceApplicationBrand commerceApplicationBrand) {

		boolean isNew = commerceApplicationBrand.isNew();

		if (!(commerceApplicationBrand instanceof
				CommerceApplicationBrandModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceApplicationBrand.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceApplicationBrand);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceApplicationBrand proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceApplicationBrand implementation " +
					commerceApplicationBrand.getClass());
		}

		CommerceApplicationBrandModelImpl commerceApplicationBrandModelImpl =
			(CommerceApplicationBrandModelImpl)commerceApplicationBrand;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceApplicationBrand.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceApplicationBrand.setCreateDate(now);
			}
			else {
				commerceApplicationBrand.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceApplicationBrandModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceApplicationBrand.setModifiedDate(now);
			}
			else {
				commerceApplicationBrand.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceApplicationBrand);
			}
			else {
				commerceApplicationBrand =
					(CommerceApplicationBrand)session.merge(
						commerceApplicationBrand);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceApplicationBrandImpl.class,
			commerceApplicationBrandModelImpl, false, true);

		if (isNew) {
			commerceApplicationBrand.setNew(false);
		}

		commerceApplicationBrand.resetOriginalValues();

		return commerceApplicationBrand;
	}

	/**
	 * Returns the commerce application brand with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce application brand
	 * @return the commerce application brand
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	@Override
	public CommerceApplicationBrand findByPrimaryKey(Serializable primaryKey)
		throws NoSuchApplicationBrandException {

		CommerceApplicationBrand commerceApplicationBrand = fetchByPrimaryKey(
			primaryKey);

		if (commerceApplicationBrand == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchApplicationBrandException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceApplicationBrand;
	}

	/**
	 * Returns the commerce application brand with the primary key or throws a <code>NoSuchApplicationBrandException</code> if it could not be found.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand
	 * @throws NoSuchApplicationBrandException if a commerce application brand with the primary key could not be found
	 */
	@Override
	public CommerceApplicationBrand findByPrimaryKey(
			long commerceApplicationBrandId)
		throws NoSuchApplicationBrandException {

		return findByPrimaryKey((Serializable)commerceApplicationBrandId);
	}

	/**
	 * Returns the commerce application brand with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand, or <code>null</code> if a commerce application brand with the primary key could not be found
	 */
	@Override
	public CommerceApplicationBrand fetchByPrimaryKey(
		long commerceApplicationBrandId) {

		return fetchByPrimaryKey((Serializable)commerceApplicationBrandId);
	}

	/**
	 * Returns all the commerce application brands.
	 *
	 * @return the commerce application brands
	 */
	@Override
	public List<CommerceApplicationBrand> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce application brands.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @return the range of commerce application brands
	 */
	@Override
	public List<CommerceApplicationBrand> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application brands.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce application brands
	 */
	@Override
	public List<CommerceApplicationBrand> findAll(
		int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce application brands.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce application brands
	 */
	@Override
	public List<CommerceApplicationBrand> findAll(
		int start, int end,
		OrderByComparator<CommerceApplicationBrand> orderByComparator,
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

		List<CommerceApplicationBrand> list = null;

		if (useFinderCache) {
			list = (List<CommerceApplicationBrand>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEAPPLICATIONBRAND);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEAPPLICATIONBRAND;

				sql = sql.concat(
					CommerceApplicationBrandModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceApplicationBrand>)QueryUtil.list(
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
	 * Removes all the commerce application brands from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceApplicationBrand commerceApplicationBrand : findAll()) {
			remove(commerceApplicationBrand);
		}
	}

	/**
	 * Returns the number of commerce application brands.
	 *
	 * @return the number of commerce application brands
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEAPPLICATIONBRAND);

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
		return "commerceApplicationBrandId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEAPPLICATIONBRAND;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceApplicationBrandModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce application brand persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceApplicationBrandPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceApplicationBrandModelArgumentsResolver(),
			new HashMapDictionary<>());

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
	}

	public void destroy() {
		entityCache.removeCache(CommerceApplicationBrandImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEAPPLICATIONBRAND =
		"SELECT commerceApplicationBrand FROM CommerceApplicationBrand commerceApplicationBrand";

	private static final String _SQL_SELECT_COMMERCEAPPLICATIONBRAND_WHERE =
		"SELECT commerceApplicationBrand FROM CommerceApplicationBrand commerceApplicationBrand WHERE ";

	private static final String _SQL_COUNT_COMMERCEAPPLICATIONBRAND =
		"SELECT COUNT(commerceApplicationBrand) FROM CommerceApplicationBrand commerceApplicationBrand";

	private static final String _SQL_COUNT_COMMERCEAPPLICATIONBRAND_WHERE =
		"SELECT COUNT(commerceApplicationBrand) FROM CommerceApplicationBrand commerceApplicationBrand WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"commerceApplicationBrand.commerceApplicationBrandId";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEAPPLICATIONBRAND_WHERE =
			"SELECT DISTINCT {commerceApplicationBrand.*} FROM CommerceApplicationBrand commerceApplicationBrand WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEAPPLICATIONBRAND_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CommerceApplicationBrand.*} FROM (SELECT DISTINCT commerceApplicationBrand.commerceApplicationBrandId FROM CommerceApplicationBrand commerceApplicationBrand WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEAPPLICATIONBRAND_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CommerceApplicationBrand ON TEMP_TABLE.commerceApplicationBrandId = CommerceApplicationBrand.commerceApplicationBrandId";

	private static final String
		_FILTER_SQL_COUNT_COMMERCEAPPLICATIONBRAND_WHERE =
			"SELECT COUNT(DISTINCT commerceApplicationBrand.commerceApplicationBrandId) AS COUNT_VALUE FROM CommerceApplicationBrand commerceApplicationBrand WHERE ";

	private static final String _FILTER_ENTITY_ALIAS =
		"commerceApplicationBrand";

	private static final String _FILTER_ENTITY_TABLE =
		"CommerceApplicationBrand";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceApplicationBrand.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"CommerceApplicationBrand.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceApplicationBrand exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceApplicationBrand exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceApplicationBrandPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CommerceApplicationBrandModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CommerceApplicationBrandModelImpl
				commerceApplicationBrandModelImpl =
					(CommerceApplicationBrandModelImpl)baseModel;

			long columnBitmask =
				commerceApplicationBrandModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceApplicationBrandModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceApplicationBrandModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceApplicationBrandModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CommerceApplicationBrandImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CommerceApplicationBrandTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CommerceApplicationBrandModelImpl commerceApplicationBrandModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceApplicationBrandModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						commerceApplicationBrandModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}