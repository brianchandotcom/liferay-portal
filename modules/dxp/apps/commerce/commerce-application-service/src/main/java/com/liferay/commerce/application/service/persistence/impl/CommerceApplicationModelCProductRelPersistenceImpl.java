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

import com.liferay.commerce.application.exception.NoSuchApplicationModelCProductRelException;
import com.liferay.commerce.application.model.CommerceApplicationModelCProductRel;
import com.liferay.commerce.application.model.CommerceApplicationModelCProductRelTable;
import com.liferay.commerce.application.model.impl.CommerceApplicationModelCProductRelImpl;
import com.liferay.commerce.application.model.impl.CommerceApplicationModelCProductRelModelImpl;
import com.liferay.commerce.application.service.persistence.CommerceApplicationModelCProductRelPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce application model c product rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceApplicationModelCProductRelPersistenceImpl
	extends BasePersistenceImpl<CommerceApplicationModelCProductRel>
	implements CommerceApplicationModelCProductRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceApplicationModelCProductRelUtil</code> to access the commerce application model c product rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceApplicationModelCProductRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath
		_finderPathWithPaginationFindByCommerceApplicationModelId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceApplicationModelId;
	private FinderPath _finderPathCountByCommerceApplicationModelId;

	/**
	 * Returns all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @return the matching commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(long commerceApplicationModelId) {

		return findByCommerceApplicationModelId(
			commerceApplicationModelId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @return the range of matching commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end) {

		return findByCommerceApplicationModelId(
			commerceApplicationModelId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator) {

		return findByCommerceApplicationModelId(
			commerceApplicationModelId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceApplicationModelId;
				finderArgs = new Object[] {commerceApplicationModelId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceApplicationModelId;
			finderArgs = new Object[] {
				commerceApplicationModelId, start, end, orderByComparator
			};
		}

		List<CommerceApplicationModelCProductRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceApplicationModelCProductRel>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceApplicationModelCProductRel
						commerceApplicationModelCProductRel : list) {

					if (commerceApplicationModelId !=
							commerceApplicationModelCProductRel.
								getCommerceApplicationModelId()) {

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

			sb.append(_SQL_SELECT_COMMERCEAPPLICATIONMODELCPRODUCTREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEAPPLICATIONMODELID_COMMERCEAPPLICATIONMODELID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceApplicationModelCProductRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceApplicationModelId);

				list =
					(List<CommerceApplicationModelCProductRel>)QueryUtil.list(
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
	 * Returns the first commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel
			findByCommerceApplicationModelId_First(
				long commerceApplicationModelId,
				OrderByComparator<CommerceApplicationModelCProductRel>
					orderByComparator)
		throws NoSuchApplicationModelCProductRelException {

		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel =
				fetchByCommerceApplicationModelId_First(
					commerceApplicationModelId, orderByComparator);

		if (commerceApplicationModelCProductRel != null) {
			return commerceApplicationModelCProductRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceApplicationModelId=");
		sb.append(commerceApplicationModelId);

		sb.append("}");

		throw new NoSuchApplicationModelCProductRelException(sb.toString());
	}

	/**
	 * Returns the first commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel
		fetchByCommerceApplicationModelId_First(
			long commerceApplicationModelId,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator) {

		List<CommerceApplicationModelCProductRel> list =
			findByCommerceApplicationModelId(
				commerceApplicationModelId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel
			findByCommerceApplicationModelId_Last(
				long commerceApplicationModelId,
				OrderByComparator<CommerceApplicationModelCProductRel>
					orderByComparator)
		throws NoSuchApplicationModelCProductRelException {

		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel =
				fetchByCommerceApplicationModelId_Last(
					commerceApplicationModelId, orderByComparator);

		if (commerceApplicationModelCProductRel != null) {
			return commerceApplicationModelCProductRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceApplicationModelId=");
		sb.append(commerceApplicationModelId);

		sb.append("}");

		throw new NoSuchApplicationModelCProductRelException(sb.toString());
	}

	/**
	 * Returns the last commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel
		fetchByCommerceApplicationModelId_Last(
			long commerceApplicationModelId,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator) {

		int count = countByCommerceApplicationModelId(
			commerceApplicationModelId);

		if (count == 0) {
			return null;
		}

		List<CommerceApplicationModelCProductRel> list =
			findByCommerceApplicationModelId(
				commerceApplicationModelId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce application model c product rels before and after the current commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the current commerce application model c product rel
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel[]
			findByCommerceApplicationModelId_PrevAndNext(
				long commerceApplicationModelCProductRelId,
				long commerceApplicationModelId,
				OrderByComparator<CommerceApplicationModelCProductRel>
					orderByComparator)
		throws NoSuchApplicationModelCProductRelException {

		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel = findByPrimaryKey(
				commerceApplicationModelCProductRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationModelCProductRel[] array =
				new CommerceApplicationModelCProductRelImpl[3];

			array[0] = getByCommerceApplicationModelId_PrevAndNext(
				session, commerceApplicationModelCProductRel,
				commerceApplicationModelId, orderByComparator, true);

			array[1] = commerceApplicationModelCProductRel;

			array[2] = getByCommerceApplicationModelId_PrevAndNext(
				session, commerceApplicationModelCProductRel,
				commerceApplicationModelId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceApplicationModelCProductRel
		getByCommerceApplicationModelId_PrevAndNext(
			Session session,
			CommerceApplicationModelCProductRel
				commerceApplicationModelCProductRel,
			long commerceApplicationModelId,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEAPPLICATIONMODELCPRODUCTREL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEAPPLICATIONMODELID_COMMERCEAPPLICATIONMODELID_2);

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
			sb.append(
				CommerceApplicationModelCProductRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceApplicationModelId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceApplicationModelCProductRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceApplicationModelCProductRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce application model c product rels where commerceApplicationModelId = &#63; from the database.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 */
	@Override
	public void removeByCommerceApplicationModelId(
		long commerceApplicationModelId) {

		for (CommerceApplicationModelCProductRel
				commerceApplicationModelCProductRel :
					findByCommerceApplicationModelId(
						commerceApplicationModelId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceApplicationModelCProductRel);
		}
	}

	/**
	 * Returns the number of commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @return the number of matching commerce application model c product rels
	 */
	@Override
	public int countByCommerceApplicationModelId(
		long commerceApplicationModelId) {

		FinderPath finderPath = _finderPathCountByCommerceApplicationModelId;

		Object[] finderArgs = new Object[] {commerceApplicationModelId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEAPPLICATIONMODELCPRODUCTREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEAPPLICATIONMODELID_COMMERCEAPPLICATIONMODELID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceApplicationModelId);

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

	private static final String
		_FINDER_COLUMN_COMMERCEAPPLICATIONMODELID_COMMERCEAPPLICATIONMODELID_2 =
			"commerceApplicationModelCProductRel.commerceApplicationModelId = ?";

	private FinderPath _finderPathWithPaginationFindByCProductId;
	private FinderPath _finderPathWithoutPaginationFindByCProductId;
	private FinderPath _finderPathCountByCProductId;

	/**
	 * Returns all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the matching commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId) {

		return findByCProductId(
			CProductId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @return the range of matching commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId, int start, int end) {

		return findByCProductId(CProductId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator) {

		return findByCProductId(
			CProductId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCProductId;
				finderArgs = new Object[] {CProductId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCProductId;
			finderArgs = new Object[] {
				CProductId, start, end, orderByComparator
			};
		}

		List<CommerceApplicationModelCProductRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceApplicationModelCProductRel>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceApplicationModelCProductRel
						commerceApplicationModelCProductRel : list) {

					if (CProductId !=
							commerceApplicationModelCProductRel.
								getCProductId()) {

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

			sb.append(_SQL_SELECT_COMMERCEAPPLICATIONMODELCPRODUCTREL_WHERE);

			sb.append(_FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceApplicationModelCProductRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

				list =
					(List<CommerceApplicationModelCProductRel>)QueryUtil.list(
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
	 * Returns the first commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel findByCProductId_First(
			long CProductId,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator)
		throws NoSuchApplicationModelCProductRelException {

		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel = fetchByCProductId_First(
				CProductId, orderByComparator);

		if (commerceApplicationModelCProductRel != null) {
			return commerceApplicationModelCProductRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CProductId=");
		sb.append(CProductId);

		sb.append("}");

		throw new NoSuchApplicationModelCProductRelException(sb.toString());
	}

	/**
	 * Returns the first commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel fetchByCProductId_First(
		long CProductId,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator) {

		List<CommerceApplicationModelCProductRel> list = findByCProductId(
			CProductId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel findByCProductId_Last(
			long CProductId,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator)
		throws NoSuchApplicationModelCProductRelException {

		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel = fetchByCProductId_Last(
				CProductId, orderByComparator);

		if (commerceApplicationModelCProductRel != null) {
			return commerceApplicationModelCProductRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CProductId=");
		sb.append(CProductId);

		sb.append("}");

		throw new NoSuchApplicationModelCProductRelException(sb.toString());
	}

	/**
	 * Returns the last commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel fetchByCProductId_Last(
		long CProductId,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator) {

		int count = countByCProductId(CProductId);

		if (count == 0) {
			return null;
		}

		List<CommerceApplicationModelCProductRel> list = findByCProductId(
			CProductId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce application model c product rels before and after the current commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the current commerce application model c product rel
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel[] findByCProductId_PrevAndNext(
			long commerceApplicationModelCProductRelId, long CProductId,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator)
		throws NoSuchApplicationModelCProductRelException {

		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel = findByPrimaryKey(
				commerceApplicationModelCProductRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationModelCProductRel[] array =
				new CommerceApplicationModelCProductRelImpl[3];

			array[0] = getByCProductId_PrevAndNext(
				session, commerceApplicationModelCProductRel, CProductId,
				orderByComparator, true);

			array[1] = commerceApplicationModelCProductRel;

			array[2] = getByCProductId_PrevAndNext(
				session, commerceApplicationModelCProductRel, CProductId,
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

	protected CommerceApplicationModelCProductRel getByCProductId_PrevAndNext(
		Session session,
		CommerceApplicationModelCProductRel commerceApplicationModelCProductRel,
		long CProductId,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEAPPLICATIONMODELCPRODUCTREL_WHERE);

		sb.append(_FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2);

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
			sb.append(
				CommerceApplicationModelCProductRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CProductId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceApplicationModelCProductRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceApplicationModelCProductRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce application model c product rels where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 */
	@Override
	public void removeByCProductId(long CProductId) {
		for (CommerceApplicationModelCProductRel
				commerceApplicationModelCProductRel :
					findByCProductId(
						CProductId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null)) {

			remove(commerceApplicationModelCProductRel);
		}
	}

	/**
	 * Returns the number of commerce application model c product rels where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching commerce application model c product rels
	 */
	@Override
	public int countByCProductId(long CProductId) {
		FinderPath finderPath = _finderPathCountByCProductId;

		Object[] finderArgs = new Object[] {CProductId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEAPPLICATIONMODELCPRODUCTREL_WHERE);

			sb.append(_FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

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

	private static final String _FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2 =
		"commerceApplicationModelCProductRel.CProductId = ?";

	public CommerceApplicationModelCProductRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceApplicationModelCProductRelId", "CAModelCProductRelId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceApplicationModelCProductRel.class);

		setModelImplClass(CommerceApplicationModelCProductRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceApplicationModelCProductRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce application model c product rel in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationModelCProductRel the commerce application model c product rel
	 */
	@Override
	public void cacheResult(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		entityCache.putResult(
			CommerceApplicationModelCProductRelImpl.class,
			commerceApplicationModelCProductRel.getPrimaryKey(),
			commerceApplicationModelCProductRel);
	}

	/**
	 * Caches the commerce application model c product rels in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationModelCProductRels the commerce application model c product rels
	 */
	@Override
	public void cacheResult(
		List<CommerceApplicationModelCProductRel>
			commerceApplicationModelCProductRels) {

		for (CommerceApplicationModelCProductRel
				commerceApplicationModelCProductRel :
					commerceApplicationModelCProductRels) {

			if (entityCache.getResult(
					CommerceApplicationModelCProductRelImpl.class,
					commerceApplicationModelCProductRel.getPrimaryKey()) ==
						null) {

				cacheResult(commerceApplicationModelCProductRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce application model c product rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceApplicationModelCProductRelImpl.class);

		finderCache.clearCache(CommerceApplicationModelCProductRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce application model c product rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		entityCache.removeResult(
			CommerceApplicationModelCProductRelImpl.class,
			commerceApplicationModelCProductRel);
	}

	@Override
	public void clearCache(
		List<CommerceApplicationModelCProductRel>
			commerceApplicationModelCProductRels) {

		for (CommerceApplicationModelCProductRel
				commerceApplicationModelCProductRel :
					commerceApplicationModelCProductRels) {

			entityCache.removeResult(
				CommerceApplicationModelCProductRelImpl.class,
				commerceApplicationModelCProductRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceApplicationModelCProductRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceApplicationModelCProductRelImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce application model c product rel with the primary key. Does not add the commerce application model c product rel to the database.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key for the new commerce application model c product rel
	 * @return the new commerce application model c product rel
	 */
	@Override
	public CommerceApplicationModelCProductRel create(
		long commerceApplicationModelCProductRelId) {

		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel =
				new CommerceApplicationModelCProductRelImpl();

		commerceApplicationModelCProductRel.setNew(true);
		commerceApplicationModelCProductRel.setPrimaryKey(
			commerceApplicationModelCProductRelId);

		commerceApplicationModelCProductRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceApplicationModelCProductRel;
	}

	/**
	 * Removes the commerce application model c product rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel that was removed
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel remove(
			long commerceApplicationModelCProductRelId)
		throws NoSuchApplicationModelCProductRelException {

		return remove((Serializable)commerceApplicationModelCProductRelId);
	}

	/**
	 * Removes the commerce application model c product rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel that was removed
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel remove(Serializable primaryKey)
		throws NoSuchApplicationModelCProductRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceApplicationModelCProductRel
				commerceApplicationModelCProductRel =
					(CommerceApplicationModelCProductRel)session.get(
						CommerceApplicationModelCProductRelImpl.class,
						primaryKey);

			if (commerceApplicationModelCProductRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchApplicationModelCProductRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceApplicationModelCProductRel);
		}
		catch (NoSuchApplicationModelCProductRelException
					noSuchEntityException) {

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
	protected CommerceApplicationModelCProductRel removeImpl(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceApplicationModelCProductRel)) {
				commerceApplicationModelCProductRel =
					(CommerceApplicationModelCProductRel)session.get(
						CommerceApplicationModelCProductRelImpl.class,
						commerceApplicationModelCProductRel.getPrimaryKeyObj());
			}

			if (commerceApplicationModelCProductRel != null) {
				session.delete(commerceApplicationModelCProductRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceApplicationModelCProductRel != null) {
			clearCache(commerceApplicationModelCProductRel);
		}

		return commerceApplicationModelCProductRel;
	}

	@Override
	public CommerceApplicationModelCProductRel updateImpl(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		boolean isNew = commerceApplicationModelCProductRel.isNew();

		if (!(commerceApplicationModelCProductRel instanceof
				CommerceApplicationModelCProductRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceApplicationModelCProductRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceApplicationModelCProductRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceApplicationModelCProductRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceApplicationModelCProductRel implementation " +
					commerceApplicationModelCProductRel.getClass());
		}

		CommerceApplicationModelCProductRelModelImpl
			commerceApplicationModelCProductRelModelImpl =
				(CommerceApplicationModelCProductRelModelImpl)
					commerceApplicationModelCProductRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
			(commerceApplicationModelCProductRel.getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceApplicationModelCProductRel.setCreateDate(now);
			}
			else {
				commerceApplicationModelCProductRel.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceApplicationModelCProductRelModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				commerceApplicationModelCProductRel.setModifiedDate(now);
			}
			else {
				commerceApplicationModelCProductRel.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceApplicationModelCProductRel);
			}
			else {
				commerceApplicationModelCProductRel =
					(CommerceApplicationModelCProductRel)session.merge(
						commerceApplicationModelCProductRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceApplicationModelCProductRelImpl.class,
			commerceApplicationModelCProductRelModelImpl, false, true);

		if (isNew) {
			commerceApplicationModelCProductRel.setNew(false);
		}

		commerceApplicationModelCProductRel.resetOriginalValues();

		return commerceApplicationModelCProductRel;
	}

	/**
	 * Returns the commerce application model c product rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchApplicationModelCProductRelException {

		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel = fetchByPrimaryKey(primaryKey);

		if (commerceApplicationModelCProductRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchApplicationModelCProductRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceApplicationModelCProductRel;
	}

	/**
	 * Returns the commerce application model c product rel with the primary key or throws a <code>NoSuchApplicationModelCProductRelException</code> if it could not be found.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel findByPrimaryKey(
			long commerceApplicationModelCProductRelId)
		throws NoSuchApplicationModelCProductRelException {

		return findByPrimaryKey(
			(Serializable)commerceApplicationModelCProductRelId);
	}

	/**
	 * Returns the commerce application model c product rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel, or <code>null</code> if a commerce application model c product rel with the primary key could not be found
	 */
	@Override
	public CommerceApplicationModelCProductRel fetchByPrimaryKey(
		long commerceApplicationModelCProductRelId) {

		return fetchByPrimaryKey(
			(Serializable)commerceApplicationModelCProductRelId);
	}

	/**
	 * Returns all the commerce application model c product rels.
	 *
	 * @return the commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce application model c product rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @return the range of commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel> findAll(
		int start, int end,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce application model c product rels
	 */
	@Override
	public List<CommerceApplicationModelCProductRel> findAll(
		int start, int end,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator,
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

		List<CommerceApplicationModelCProductRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceApplicationModelCProductRel>)
					finderCache.getResult(finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEAPPLICATIONMODELCPRODUCTREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEAPPLICATIONMODELCPRODUCTREL;

				sql = sql.concat(
					CommerceApplicationModelCProductRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list =
					(List<CommerceApplicationModelCProductRel>)QueryUtil.list(
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
	 * Removes all the commerce application model c product rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceApplicationModelCProductRel
				commerceApplicationModelCProductRel : findAll()) {

			remove(commerceApplicationModelCProductRel);
		}
	}

	/**
	 * Returns the number of commerce application model c product rels.
	 *
	 * @return the number of commerce application model c product rels
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
					_SQL_COUNT_COMMERCEAPPLICATIONMODELCPRODUCTREL);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CAModelCProductRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEAPPLICATIONMODELCPRODUCTREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceApplicationModelCProductRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce application model c product rel persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceApplicationModelCProductRelPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceApplicationModelCProductRelModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name",
				CommerceApplicationModelCProductRel.class.getName()));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCommerceApplicationModelId =
			_createFinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceApplicationModelId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceApplicationModelId"}, true);

		_finderPathWithoutPaginationFindByCommerceApplicationModelId =
			_createFinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceApplicationModelId",
				new String[] {Long.class.getName()},
				new String[] {"commerceApplicationModelId"}, true);

		_finderPathCountByCommerceApplicationModelId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceApplicationModelId",
			new String[] {Long.class.getName()},
			new String[] {"commerceApplicationModelId"}, false);

		_finderPathWithPaginationFindByCProductId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCProductId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CProductId"}, true);

		_finderPathWithoutPaginationFindByCProductId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCProductId",
			new String[] {Long.class.getName()}, new String[] {"CProductId"},
			true);

		_finderPathCountByCProductId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCProductId",
			new String[] {Long.class.getName()}, new String[] {"CProductId"},
			false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceApplicationModelCProductRelImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String
		_SQL_SELECT_COMMERCEAPPLICATIONMODELCPRODUCTREL =
			"SELECT commerceApplicationModelCProductRel FROM CommerceApplicationModelCProductRel commerceApplicationModelCProductRel";

	private static final String
		_SQL_SELECT_COMMERCEAPPLICATIONMODELCPRODUCTREL_WHERE =
			"SELECT commerceApplicationModelCProductRel FROM CommerceApplicationModelCProductRel commerceApplicationModelCProductRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEAPPLICATIONMODELCPRODUCTREL =
		"SELECT COUNT(commerceApplicationModelCProductRel) FROM CommerceApplicationModelCProductRel commerceApplicationModelCProductRel";

	private static final String
		_SQL_COUNT_COMMERCEAPPLICATIONMODELCPRODUCTREL_WHERE =
			"SELECT COUNT(commerceApplicationModelCProductRel) FROM CommerceApplicationModelCProductRel commerceApplicationModelCProductRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceApplicationModelCProductRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceApplicationModelCProductRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceApplicationModelCProductRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceApplicationModelCProductRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceApplicationModelCProductRelId"});

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;
	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();

	private static class
		CommerceApplicationModelCProductRelModelArgumentsResolver
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

			CommerceApplicationModelCProductRelModelImpl
				commerceApplicationModelCProductRelModelImpl =
					(CommerceApplicationModelCProductRelModelImpl)baseModel;

			long columnBitmask =
				commerceApplicationModelCProductRelModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceApplicationModelCProductRelModelImpl, columnNames,
					original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceApplicationModelCProductRelModelImpl.
							getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceApplicationModelCProductRelModelImpl, columnNames,
					original);
			}

			return null;
		}

		private Object[] _getValue(
			CommerceApplicationModelCProductRelModelImpl
				commerceApplicationModelCProductRelModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceApplicationModelCProductRelModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						commerceApplicationModelCProductRelModelImpl.
							getColumnValue(columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}