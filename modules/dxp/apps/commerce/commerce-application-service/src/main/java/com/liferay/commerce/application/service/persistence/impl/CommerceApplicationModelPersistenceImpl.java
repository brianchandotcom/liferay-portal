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

import com.liferay.commerce.application.exception.NoSuchApplicationModelException;
import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.commerce.application.model.CommerceApplicationModelTable;
import com.liferay.commerce.application.model.impl.CommerceApplicationModelImpl;
import com.liferay.commerce.application.model.impl.CommerceApplicationModelModelImpl;
import com.liferay.commerce.application.service.persistence.CommerceApplicationModelPersistence;
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
 * The persistence implementation for the commerce application model service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceApplicationModelPersistenceImpl
	extends BasePersistenceImpl<CommerceApplicationModel>
	implements CommerceApplicationModelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceApplicationModelUtil</code> to access the commerce application model persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceApplicationModelImpl.class.getName();

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
	 * Returns all the commerce application models where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce application models where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @return the range of matching commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application models where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceApplicationModel> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce application models where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceApplicationModel> orderByComparator,
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

		List<CommerceApplicationModel> list = null;

		if (useFinderCache) {
			list = (List<CommerceApplicationModel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceApplicationModel commerceApplicationModel : list) {
					if (companyId != commerceApplicationModel.getCompanyId()) {
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

			sb.append(_SQL_SELECT_COMMERCEAPPLICATIONMODEL_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceApplicationModelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceApplicationModel>)QueryUtil.list(
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
	 * Returns the first commerce application model in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model
	 * @throws NoSuchApplicationModelException if a matching commerce application model could not be found
	 */
	@Override
	public CommerceApplicationModel findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceApplicationModel> orderByComparator)
		throws NoSuchApplicationModelException {

		CommerceApplicationModel commerceApplicationModel =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (commerceApplicationModel != null) {
			return commerceApplicationModel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchApplicationModelException(sb.toString());
	}

	/**
	 * Returns the first commerce application model in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model, or <code>null</code> if a matching commerce application model could not be found
	 */
	@Override
	public CommerceApplicationModel fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceApplicationModel> orderByComparator) {

		List<CommerceApplicationModel> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce application model in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model
	 * @throws NoSuchApplicationModelException if a matching commerce application model could not be found
	 */
	@Override
	public CommerceApplicationModel findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceApplicationModel> orderByComparator)
		throws NoSuchApplicationModelException {

		CommerceApplicationModel commerceApplicationModel =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (commerceApplicationModel != null) {
			return commerceApplicationModel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchApplicationModelException(sb.toString());
	}

	/**
	 * Returns the last commerce application model in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model, or <code>null</code> if a matching commerce application model could not be found
	 */
	@Override
	public CommerceApplicationModel fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceApplicationModel> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceApplicationModel> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce application models before and after the current commerce application model in the ordered set where companyId = &#63;.
	 *
	 * @param commerceApplicationModelId the primary key of the current commerce application model
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application model
	 * @throws NoSuchApplicationModelException if a commerce application model with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModel[] findByCompanyId_PrevAndNext(
			long commerceApplicationModelId, long companyId,
			OrderByComparator<CommerceApplicationModel> orderByComparator)
		throws NoSuchApplicationModelException {

		CommerceApplicationModel commerceApplicationModel = findByPrimaryKey(
			commerceApplicationModelId);

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationModel[] array =
				new CommerceApplicationModelImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceApplicationModel, companyId, orderByComparator,
				true);

			array[1] = commerceApplicationModel;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceApplicationModel, companyId, orderByComparator,
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

	protected CommerceApplicationModel getByCompanyId_PrevAndNext(
		Session session, CommerceApplicationModel commerceApplicationModel,
		long companyId,
		OrderByComparator<CommerceApplicationModel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEAPPLICATIONMODEL_WHERE);

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
			sb.append(CommerceApplicationModelModelImpl.ORDER_BY_JPQL);
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
						commerceApplicationModel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceApplicationModel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce application models that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce application models that the user has permission to view
	 */
	@Override
	public List<CommerceApplicationModel> filterFindByCompanyId(
		long companyId) {

		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce application models that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @return the range of matching commerce application models that the user has permission to view
	 */
	@Override
	public List<CommerceApplicationModel> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application models that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application models that the user has permission to view
	 */
	@Override
	public List<CommerceApplicationModel> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceApplicationModel> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(CommerceApplicationModelModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceApplicationModelModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceApplicationModel.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceApplicationModelImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceApplicationModelImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<CommerceApplicationModel>)QueryUtil.list(
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
	 * Returns the commerce application models before and after the current commerce application model in the ordered set of commerce application models that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceApplicationModelId the primary key of the current commerce application model
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application model
	 * @throws NoSuchApplicationModelException if a commerce application model with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModel[] filterFindByCompanyId_PrevAndNext(
			long commerceApplicationModelId, long companyId,
			OrderByComparator<CommerceApplicationModel> orderByComparator)
		throws NoSuchApplicationModelException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				commerceApplicationModelId, companyId, orderByComparator);
		}

		CommerceApplicationModel commerceApplicationModel = findByPrimaryKey(
			commerceApplicationModelId);

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationModel[] array =
				new CommerceApplicationModelImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, commerceApplicationModel, companyId, orderByComparator,
				true);

			array[1] = commerceApplicationModel;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, commerceApplicationModel, companyId, orderByComparator,
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

	protected CommerceApplicationModel filterGetByCompanyId_PrevAndNext(
		Session session, CommerceApplicationModel commerceApplicationModel,
		long companyId,
		OrderByComparator<CommerceApplicationModel> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(CommerceApplicationModelModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceApplicationModelModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceApplicationModel.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceApplicationModelImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceApplicationModelImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceApplicationModel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceApplicationModel> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce application models where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceApplicationModel commerceApplicationModel :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceApplicationModel);
		}
	}

	/**
	 * Returns the number of commerce application models where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce application models
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEAPPLICATIONMODEL_WHERE);

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
	 * Returns the number of commerce application models that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce application models that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEAPPLICATIONMODEL_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceApplicationModel.class.getName(),
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
		"commerceApplicationModel.companyId = ?";

	private FinderPath
		_finderPathWithPaginationFindByCommerceApplicationBrandId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceApplicationBrandId;
	private FinderPath _finderPathCountByCommerceApplicationBrandId;

	/**
	 * Returns all the commerce application models where commerceApplicationBrandId = &#63;.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @return the matching commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findByCommerceApplicationBrandId(
		long commerceApplicationBrandId) {

		return findByCommerceApplicationBrandId(
			commerceApplicationBrandId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce application models where commerceApplicationBrandId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @return the range of matching commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findByCommerceApplicationBrandId(
		long commerceApplicationBrandId, int start, int end) {

		return findByCommerceApplicationBrandId(
			commerceApplicationBrandId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application models where commerceApplicationBrandId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findByCommerceApplicationBrandId(
		long commerceApplicationBrandId, int start, int end,
		OrderByComparator<CommerceApplicationModel> orderByComparator) {

		return findByCommerceApplicationBrandId(
			commerceApplicationBrandId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce application models where commerceApplicationBrandId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findByCommerceApplicationBrandId(
		long commerceApplicationBrandId, int start, int end,
		OrderByComparator<CommerceApplicationModel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceApplicationBrandId;
				finderArgs = new Object[] {commerceApplicationBrandId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceApplicationBrandId;
			finderArgs = new Object[] {
				commerceApplicationBrandId, start, end, orderByComparator
			};
		}

		List<CommerceApplicationModel> list = null;

		if (useFinderCache) {
			list = (List<CommerceApplicationModel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceApplicationModel commerceApplicationModel : list) {
					if (commerceApplicationBrandId !=
							commerceApplicationModel.
								getCommerceApplicationBrandId()) {

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

			sb.append(_SQL_SELECT_COMMERCEAPPLICATIONMODEL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEAPPLICATIONBRANDID_COMMERCEAPPLICATIONBRANDID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceApplicationModelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceApplicationBrandId);

				list = (List<CommerceApplicationModel>)QueryUtil.list(
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
	 * Returns the first commerce application model in the ordered set where commerceApplicationBrandId = &#63;.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model
	 * @throws NoSuchApplicationModelException if a matching commerce application model could not be found
	 */
	@Override
	public CommerceApplicationModel findByCommerceApplicationBrandId_First(
			long commerceApplicationBrandId,
			OrderByComparator<CommerceApplicationModel> orderByComparator)
		throws NoSuchApplicationModelException {

		CommerceApplicationModel commerceApplicationModel =
			fetchByCommerceApplicationBrandId_First(
				commerceApplicationBrandId, orderByComparator);

		if (commerceApplicationModel != null) {
			return commerceApplicationModel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceApplicationBrandId=");
		sb.append(commerceApplicationBrandId);

		sb.append("}");

		throw new NoSuchApplicationModelException(sb.toString());
	}

	/**
	 * Returns the first commerce application model in the ordered set where commerceApplicationBrandId = &#63;.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model, or <code>null</code> if a matching commerce application model could not be found
	 */
	@Override
	public CommerceApplicationModel fetchByCommerceApplicationBrandId_First(
		long commerceApplicationBrandId,
		OrderByComparator<CommerceApplicationModel> orderByComparator) {

		List<CommerceApplicationModel> list = findByCommerceApplicationBrandId(
			commerceApplicationBrandId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce application model in the ordered set where commerceApplicationBrandId = &#63;.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model
	 * @throws NoSuchApplicationModelException if a matching commerce application model could not be found
	 */
	@Override
	public CommerceApplicationModel findByCommerceApplicationBrandId_Last(
			long commerceApplicationBrandId,
			OrderByComparator<CommerceApplicationModel> orderByComparator)
		throws NoSuchApplicationModelException {

		CommerceApplicationModel commerceApplicationModel =
			fetchByCommerceApplicationBrandId_Last(
				commerceApplicationBrandId, orderByComparator);

		if (commerceApplicationModel != null) {
			return commerceApplicationModel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceApplicationBrandId=");
		sb.append(commerceApplicationBrandId);

		sb.append("}");

		throw new NoSuchApplicationModelException(sb.toString());
	}

	/**
	 * Returns the last commerce application model in the ordered set where commerceApplicationBrandId = &#63;.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model, or <code>null</code> if a matching commerce application model could not be found
	 */
	@Override
	public CommerceApplicationModel fetchByCommerceApplicationBrandId_Last(
		long commerceApplicationBrandId,
		OrderByComparator<CommerceApplicationModel> orderByComparator) {

		int count = countByCommerceApplicationBrandId(
			commerceApplicationBrandId);

		if (count == 0) {
			return null;
		}

		List<CommerceApplicationModel> list = findByCommerceApplicationBrandId(
			commerceApplicationBrandId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce application models before and after the current commerce application model in the ordered set where commerceApplicationBrandId = &#63;.
	 *
	 * @param commerceApplicationModelId the primary key of the current commerce application model
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application model
	 * @throws NoSuchApplicationModelException if a commerce application model with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModel[]
			findByCommerceApplicationBrandId_PrevAndNext(
				long commerceApplicationModelId,
				long commerceApplicationBrandId,
				OrderByComparator<CommerceApplicationModel> orderByComparator)
		throws NoSuchApplicationModelException {

		CommerceApplicationModel commerceApplicationModel = findByPrimaryKey(
			commerceApplicationModelId);

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationModel[] array =
				new CommerceApplicationModelImpl[3];

			array[0] = getByCommerceApplicationBrandId_PrevAndNext(
				session, commerceApplicationModel, commerceApplicationBrandId,
				orderByComparator, true);

			array[1] = commerceApplicationModel;

			array[2] = getByCommerceApplicationBrandId_PrevAndNext(
				session, commerceApplicationModel, commerceApplicationBrandId,
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

	protected CommerceApplicationModel
		getByCommerceApplicationBrandId_PrevAndNext(
			Session session, CommerceApplicationModel commerceApplicationModel,
			long commerceApplicationBrandId,
			OrderByComparator<CommerceApplicationModel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEAPPLICATIONMODEL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEAPPLICATIONBRANDID_COMMERCEAPPLICATIONBRANDID_2);

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
			sb.append(CommerceApplicationModelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceApplicationBrandId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceApplicationModel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceApplicationModel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce application models that the user has permission to view where commerceApplicationBrandId = &#63;.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @return the matching commerce application models that the user has permission to view
	 */
	@Override
	public List<CommerceApplicationModel>
		filterFindByCommerceApplicationBrandId(
			long commerceApplicationBrandId) {

		return filterFindByCommerceApplicationBrandId(
			commerceApplicationBrandId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce application models that the user has permission to view where commerceApplicationBrandId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @return the range of matching commerce application models that the user has permission to view
	 */
	@Override
	public List<CommerceApplicationModel>
		filterFindByCommerceApplicationBrandId(
			long commerceApplicationBrandId, int start, int end) {

		return filterFindByCommerceApplicationBrandId(
			commerceApplicationBrandId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application models that the user has permissions to view where commerceApplicationBrandId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application models that the user has permission to view
	 */
	@Override
	public List<CommerceApplicationModel>
		filterFindByCommerceApplicationBrandId(
			long commerceApplicationBrandId, int start, int end,
			OrderByComparator<CommerceApplicationModel> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCommerceApplicationBrandId(
				commerceApplicationBrandId, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(
			_FINDER_COLUMN_COMMERCEAPPLICATIONBRANDID_COMMERCEAPPLICATIONBRANDID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(CommerceApplicationModelModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceApplicationModelModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceApplicationModel.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceApplicationModelImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceApplicationModelImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceApplicationBrandId);

			return (List<CommerceApplicationModel>)QueryUtil.list(
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
	 * Returns the commerce application models before and after the current commerce application model in the ordered set of commerce application models that the user has permission to view where commerceApplicationBrandId = &#63;.
	 *
	 * @param commerceApplicationModelId the primary key of the current commerce application model
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application model
	 * @throws NoSuchApplicationModelException if a commerce application model with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModel[]
			filterFindByCommerceApplicationBrandId_PrevAndNext(
				long commerceApplicationModelId,
				long commerceApplicationBrandId,
				OrderByComparator<CommerceApplicationModel> orderByComparator)
		throws NoSuchApplicationModelException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCommerceApplicationBrandId_PrevAndNext(
				commerceApplicationModelId, commerceApplicationBrandId,
				orderByComparator);
		}

		CommerceApplicationModel commerceApplicationModel = findByPrimaryKey(
			commerceApplicationModelId);

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationModel[] array =
				new CommerceApplicationModelImpl[3];

			array[0] = filterGetByCommerceApplicationBrandId_PrevAndNext(
				session, commerceApplicationModel, commerceApplicationBrandId,
				orderByComparator, true);

			array[1] = commerceApplicationModel;

			array[2] = filterGetByCommerceApplicationBrandId_PrevAndNext(
				session, commerceApplicationModel, commerceApplicationBrandId,
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

	protected CommerceApplicationModel
		filterGetByCommerceApplicationBrandId_PrevAndNext(
			Session session, CommerceApplicationModel commerceApplicationModel,
			long commerceApplicationBrandId,
			OrderByComparator<CommerceApplicationModel> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(
			_FINDER_COLUMN_COMMERCEAPPLICATIONBRANDID_COMMERCEAPPLICATIONBRANDID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(CommerceApplicationModelModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceApplicationModelModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceApplicationModel.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceApplicationModelImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceApplicationModelImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(commerceApplicationBrandId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceApplicationModel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceApplicationModel> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce application models where commerceApplicationBrandId = &#63; from the database.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 */
	@Override
	public void removeByCommerceApplicationBrandId(
		long commerceApplicationBrandId) {

		for (CommerceApplicationModel commerceApplicationModel :
				findByCommerceApplicationBrandId(
					commerceApplicationBrandId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceApplicationModel);
		}
	}

	/**
	 * Returns the number of commerce application models where commerceApplicationBrandId = &#63;.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @return the number of matching commerce application models
	 */
	@Override
	public int countByCommerceApplicationBrandId(
		long commerceApplicationBrandId) {

		FinderPath finderPath = _finderPathCountByCommerceApplicationBrandId;

		Object[] finderArgs = new Object[] {commerceApplicationBrandId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEAPPLICATIONMODEL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEAPPLICATIONBRANDID_COMMERCEAPPLICATIONBRANDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceApplicationBrandId);

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
	 * Returns the number of commerce application models that the user has permission to view where commerceApplicationBrandId = &#63;.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID
	 * @return the number of matching commerce application models that the user has permission to view
	 */
	@Override
	public int filterCountByCommerceApplicationBrandId(
		long commerceApplicationBrandId) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCommerceApplicationBrandId(
				commerceApplicationBrandId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEAPPLICATIONMODEL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEAPPLICATIONBRANDID_COMMERCEAPPLICATIONBRANDID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceApplicationModel.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceApplicationBrandId);

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

	private static final String
		_FINDER_COLUMN_COMMERCEAPPLICATIONBRANDID_COMMERCEAPPLICATIONBRANDID_2 =
			"commerceApplicationModel.commerceApplicationBrandId = ?";

	public CommerceApplicationModelPersistenceImpl() {
		setModelClass(CommerceApplicationModel.class);

		setModelImplClass(CommerceApplicationModelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceApplicationModelTable.INSTANCE);
	}

	/**
	 * Caches the commerce application model in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationModel the commerce application model
	 */
	@Override
	public void cacheResult(CommerceApplicationModel commerceApplicationModel) {
		entityCache.putResult(
			CommerceApplicationModelImpl.class,
			commerceApplicationModel.getPrimaryKey(), commerceApplicationModel);
	}

	/**
	 * Caches the commerce application models in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationModels the commerce application models
	 */
	@Override
	public void cacheResult(
		List<CommerceApplicationModel> commerceApplicationModels) {

		for (CommerceApplicationModel commerceApplicationModel :
				commerceApplicationModels) {

			if (entityCache.getResult(
					CommerceApplicationModelImpl.class,
					commerceApplicationModel.getPrimaryKey()) == null) {

				cacheResult(commerceApplicationModel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce application models.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceApplicationModelImpl.class);

		finderCache.clearCache(CommerceApplicationModelImpl.class);
	}

	/**
	 * Clears the cache for the commerce application model.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceApplicationModel commerceApplicationModel) {
		entityCache.removeResult(
			CommerceApplicationModelImpl.class, commerceApplicationModel);
	}

	@Override
	public void clearCache(
		List<CommerceApplicationModel> commerceApplicationModels) {

		for (CommerceApplicationModel commerceApplicationModel :
				commerceApplicationModels) {

			entityCache.removeResult(
				CommerceApplicationModelImpl.class, commerceApplicationModel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceApplicationModelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceApplicationModelImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce application model with the primary key. Does not add the commerce application model to the database.
	 *
	 * @param commerceApplicationModelId the primary key for the new commerce application model
	 * @return the new commerce application model
	 */
	@Override
	public CommerceApplicationModel create(long commerceApplicationModelId) {
		CommerceApplicationModel commerceApplicationModel =
			new CommerceApplicationModelImpl();

		commerceApplicationModel.setNew(true);
		commerceApplicationModel.setPrimaryKey(commerceApplicationModelId);

		commerceApplicationModel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceApplicationModel;
	}

	/**
	 * Removes the commerce application model with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceApplicationModelId the primary key of the commerce application model
	 * @return the commerce application model that was removed
	 * @throws NoSuchApplicationModelException if a commerce application model with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModel remove(long commerceApplicationModelId)
		throws NoSuchApplicationModelException {

		return remove((Serializable)commerceApplicationModelId);
	}

	/**
	 * Removes the commerce application model with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce application model
	 * @return the commerce application model that was removed
	 * @throws NoSuchApplicationModelException if a commerce application model with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModel remove(Serializable primaryKey)
		throws NoSuchApplicationModelException {

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationModel commerceApplicationModel =
				(CommerceApplicationModel)session.get(
					CommerceApplicationModelImpl.class, primaryKey);

			if (commerceApplicationModel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchApplicationModelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceApplicationModel);
		}
		catch (NoSuchApplicationModelException noSuchEntityException) {
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
	protected CommerceApplicationModel removeImpl(
		CommerceApplicationModel commerceApplicationModel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceApplicationModel)) {
				commerceApplicationModel =
					(CommerceApplicationModel)session.get(
						CommerceApplicationModelImpl.class,
						commerceApplicationModel.getPrimaryKeyObj());
			}

			if (commerceApplicationModel != null) {
				session.delete(commerceApplicationModel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceApplicationModel != null) {
			clearCache(commerceApplicationModel);
		}

		return commerceApplicationModel;
	}

	@Override
	public CommerceApplicationModel updateImpl(
		CommerceApplicationModel commerceApplicationModel) {

		boolean isNew = commerceApplicationModel.isNew();

		if (!(commerceApplicationModel instanceof
				CommerceApplicationModelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceApplicationModel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceApplicationModel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceApplicationModel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceApplicationModel implementation " +
					commerceApplicationModel.getClass());
		}

		CommerceApplicationModelModelImpl commerceApplicationModelModelImpl =
			(CommerceApplicationModelModelImpl)commerceApplicationModel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceApplicationModel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceApplicationModel.setCreateDate(now);
			}
			else {
				commerceApplicationModel.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceApplicationModelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceApplicationModel.setModifiedDate(now);
			}
			else {
				commerceApplicationModel.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceApplicationModel);
			}
			else {
				commerceApplicationModel =
					(CommerceApplicationModel)session.merge(
						commerceApplicationModel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceApplicationModelImpl.class,
			commerceApplicationModelModelImpl, false, true);

		if (isNew) {
			commerceApplicationModel.setNew(false);
		}

		commerceApplicationModel.resetOriginalValues();

		return commerceApplicationModel;
	}

	/**
	 * Returns the commerce application model with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce application model
	 * @return the commerce application model
	 * @throws NoSuchApplicationModelException if a commerce application model with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchApplicationModelException {

		CommerceApplicationModel commerceApplicationModel = fetchByPrimaryKey(
			primaryKey);

		if (commerceApplicationModel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchApplicationModelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceApplicationModel;
	}

	/**
	 * Returns the commerce application model with the primary key or throws a <code>NoSuchApplicationModelException</code> if it could not be found.
	 *
	 * @param commerceApplicationModelId the primary key of the commerce application model
	 * @return the commerce application model
	 * @throws NoSuchApplicationModelException if a commerce application model with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModel findByPrimaryKey(
			long commerceApplicationModelId)
		throws NoSuchApplicationModelException {

		return findByPrimaryKey((Serializable)commerceApplicationModelId);
	}

	/**
	 * Returns the commerce application model with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceApplicationModelId the primary key of the commerce application model
	 * @return the commerce application model, or <code>null</code> if a commerce application model with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModel fetchByPrimaryKey(
		long commerceApplicationModelId) {

		return fetchByPrimaryKey((Serializable)commerceApplicationModelId);
	}

	/**
	 * Returns all the commerce application models.
	 *
	 * @return the commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce application models.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @return the range of commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application models.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findAll(
		int start, int end,
		OrderByComparator<CommerceApplicationModel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce application models.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce application models
	 */
	@Override
	public List<CommerceApplicationModel> findAll(
		int start, int end,
		OrderByComparator<CommerceApplicationModel> orderByComparator,
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

		List<CommerceApplicationModel> list = null;

		if (useFinderCache) {
			list = (List<CommerceApplicationModel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEAPPLICATIONMODEL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEAPPLICATIONMODEL;

				sql = sql.concat(
					CommerceApplicationModelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceApplicationModel>)QueryUtil.list(
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
	 * Removes all the commerce application models from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceApplicationModel commerceApplicationModel : findAll()) {
			remove(commerceApplicationModel);
		}
	}

	/**
	 * Returns the number of commerce application models.
	 *
	 * @return the number of commerce application models
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
					_SQL_COUNT_COMMERCEAPPLICATIONMODEL);

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
		return "commerceApplicationModelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEAPPLICATIONMODEL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceApplicationModelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce application model persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceApplicationModelPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceApplicationModelModelArgumentsResolver(),
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

		_finderPathWithPaginationFindByCommerceApplicationBrandId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceApplicationBrandId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceApplicationBrandId"}, true);

		_finderPathWithoutPaginationFindByCommerceApplicationBrandId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceApplicationBrandId",
				new String[] {Long.class.getName()},
				new String[] {"commerceApplicationBrandId"}, true);

		_finderPathCountByCommerceApplicationBrandId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceApplicationBrandId",
			new String[] {Long.class.getName()},
			new String[] {"commerceApplicationBrandId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceApplicationModelImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEAPPLICATIONMODEL =
		"SELECT commerceApplicationModel FROM CommerceApplicationModel commerceApplicationModel";

	private static final String _SQL_SELECT_COMMERCEAPPLICATIONMODEL_WHERE =
		"SELECT commerceApplicationModel FROM CommerceApplicationModel commerceApplicationModel WHERE ";

	private static final String _SQL_COUNT_COMMERCEAPPLICATIONMODEL =
		"SELECT COUNT(commerceApplicationModel) FROM CommerceApplicationModel commerceApplicationModel";

	private static final String _SQL_COUNT_COMMERCEAPPLICATIONMODEL_WHERE =
		"SELECT COUNT(commerceApplicationModel) FROM CommerceApplicationModel commerceApplicationModel WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"commerceApplicationModel.commerceApplicationModelId";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_WHERE =
			"SELECT DISTINCT {commerceApplicationModel.*} FROM CommerceApplicationModel commerceApplicationModel WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CommerceApplicationModel.*} FROM (SELECT DISTINCT commerceApplicationModel.commerceApplicationModelId FROM CommerceApplicationModel commerceApplicationModel WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEAPPLICATIONMODEL_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CommerceApplicationModel ON TEMP_TABLE.commerceApplicationModelId = CommerceApplicationModel.commerceApplicationModelId";

	private static final String
		_FILTER_SQL_COUNT_COMMERCEAPPLICATIONMODEL_WHERE =
			"SELECT COUNT(DISTINCT commerceApplicationModel.commerceApplicationModelId) AS COUNT_VALUE FROM CommerceApplicationModel commerceApplicationModel WHERE ";

	private static final String _FILTER_ENTITY_ALIAS =
		"commerceApplicationModel";

	private static final String _FILTER_ENTITY_TABLE =
		"CommerceApplicationModel";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceApplicationModel.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"CommerceApplicationModel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceApplicationModel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceApplicationModel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceApplicationModelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CommerceApplicationModelModelArgumentsResolver
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

			CommerceApplicationModelModelImpl
				commerceApplicationModelModelImpl =
					(CommerceApplicationModelModelImpl)baseModel;

			long columnBitmask =
				commerceApplicationModelModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceApplicationModelModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceApplicationModelModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceApplicationModelModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CommerceApplicationModelImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CommerceApplicationModelTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CommerceApplicationModelModelImpl commerceApplicationModelModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceApplicationModelModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						commerceApplicationModelModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}