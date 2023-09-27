/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.persistence.impl;

import com.liferay.commerce.inventory.exception.NoSuchInventoryWarehouseRelException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRelTable;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseRelImpl;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseRelModelImpl;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryWarehouseRelPersistence;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryWarehouseRelUtil;
import com.liferay.commerce.inventory.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the commerce inventory warehouse rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
@Component(service = CommerceInventoryWarehouseRelPersistence.class)
public class CommerceInventoryWarehouseRelPersistenceImpl
	extends BasePersistenceImpl<CommerceInventoryWarehouseRel>
	implements CommerceInventoryWarehouseRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceInventoryWarehouseRelUtil</code> to access the commerce inventory warehouse rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceInventoryWarehouseRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath
		_finderPathWithPaginationFindByCommerceInventoryWarehouseId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId;
	private FinderPath _finderPathCountByCommerceInventoryWarehouseId;

	/**
	 * Returns all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(long commerceInventoryWarehouseId) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @return the range of matching commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			OrderByComparator<CommerceInventoryWarehouseRel>
				orderByComparator) {

		return findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId;
				finderArgs = new Object[] {commerceInventoryWarehouseId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceInventoryWarehouseId;
			finderArgs = new Object[] {
				commerceInventoryWarehouseId, start, end, orderByComparator
			};
		}

		List<CommerceInventoryWarehouseRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouseRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryWarehouseRel
						commerceInventoryWarehouseRel : list) {

					if (commerceInventoryWarehouseId !=
							commerceInventoryWarehouseRel.
								getCommerceInventoryWarehouseId()) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEINVENTORYWAREHOUSEID_COMMERCEINVENTORYWAREHOUSEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceInventoryWarehouseRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceInventoryWarehouseId);

				list = (List<CommerceInventoryWarehouseRel>)QueryUtil.list(
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
	 * Returns the first commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel
			findByCommerceInventoryWarehouseId_First(
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryWarehouseRel>
					orderByComparator)
		throws NoSuchInventoryWarehouseRelException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			fetchByCommerceInventoryWarehouseId_First(
				commerceInventoryWarehouseId, orderByComparator);

		if (commerceInventoryWarehouseRel != null) {
			return commerceInventoryWarehouseRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);

		sb.append("}");

		throw new NoSuchInventoryWarehouseRelException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel
		fetchByCommerceInventoryWarehouseId_First(
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel>
				orderByComparator) {

		List<CommerceInventoryWarehouseRel> list =
			findByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel
			findByCommerceInventoryWarehouseId_Last(
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryWarehouseRel>
					orderByComparator)
		throws NoSuchInventoryWarehouseRelException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			fetchByCommerceInventoryWarehouseId_Last(
				commerceInventoryWarehouseId, orderByComparator);

		if (commerceInventoryWarehouseRel != null) {
			return commerceInventoryWarehouseRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);

		sb.append("}");

		throw new NoSuchInventoryWarehouseRelException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel
		fetchByCommerceInventoryWarehouseId_Last(
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel>
				orderByComparator) {

		int count = countByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryWarehouseRel> list =
			findByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory warehouse rels before and after the current commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the current commerce inventory warehouse rel
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel[]
			findByCommerceInventoryWarehouseId_PrevAndNext(
				long commerceInventoryWarehouseRelId,
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryWarehouseRel>
					orderByComparator)
		throws NoSuchInventoryWarehouseRelException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			findByPrimaryKey(commerceInventoryWarehouseRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouseRel[] array =
				new CommerceInventoryWarehouseRelImpl[3];

			array[0] = getByCommerceInventoryWarehouseId_PrevAndNext(
				session, commerceInventoryWarehouseRel,
				commerceInventoryWarehouseId, orderByComparator, true);

			array[1] = commerceInventoryWarehouseRel;

			array[2] = getByCommerceInventoryWarehouseId_PrevAndNext(
				session, commerceInventoryWarehouseRel,
				commerceInventoryWarehouseId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryWarehouseRel
		getByCommerceInventoryWarehouseId_PrevAndNext(
			Session session,
			CommerceInventoryWarehouseRel commerceInventoryWarehouseRel,
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEREL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEINVENTORYWAREHOUSEID_COMMERCEINVENTORYWAREHOUSEID_2);

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
			sb.append(CommerceInventoryWarehouseRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceInventoryWarehouseId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouseRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouseRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 */
	@Override
	public void removeByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		for (CommerceInventoryWarehouseRel commerceInventoryWarehouseRel :
				findByCommerceInventoryWarehouseId(
					commerceInventoryWarehouseId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryWarehouseRel);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory warehouse rels
	 */
	@Override
	public int countByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		FinderPath finderPath = _finderPathCountByCommerceInventoryWarehouseId;

		Object[] finderArgs = new Object[] {commerceInventoryWarehouseId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEINVENTORYWAREHOUSEID_COMMERCEINVENTORYWAREHOUSEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceInventoryWarehouseId);

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
		_FINDER_COLUMN_COMMERCEINVENTORYWAREHOUSEID_COMMERCEINVENTORYWAREHOUSEID_2 =
			"commerceInventoryWarehouseRel.commerceInventoryWarehouseId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId) {

		return findByC_C(
			classNameId, commerceInventoryWarehouseId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @return the range of matching commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId, int start,
		int end) {

		return findByC_C(
			classNameId, commerceInventoryWarehouseId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator) {

		return findByC_C(
			classNameId, commerceInventoryWarehouseId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {
					classNameId, commerceInventoryWarehouseId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, commerceInventoryWarehouseId, start, end,
				orderByComparator
			};
		}

		List<CommerceInventoryWarehouseRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouseRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryWarehouseRel
						commerceInventoryWarehouseRel : list) {

					if ((classNameId !=
							commerceInventoryWarehouseRel.getClassNameId()) ||
						(commerceInventoryWarehouseId !=
							commerceInventoryWarehouseRel.
								getCommerceInventoryWarehouseId())) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEINVENTORYWAREHOUSEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceInventoryWarehouseRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(commerceInventoryWarehouseId);

				list = (List<CommerceInventoryWarehouseRel>)QueryUtil.list(
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
	 * Returns the first commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel findByC_C_First(
			long classNameId, long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator)
		throws NoSuchInventoryWarehouseRelException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			fetchByC_C_First(
				classNameId, commerceInventoryWarehouseId, orderByComparator);

		if (commerceInventoryWarehouseRel != null) {
			return commerceInventoryWarehouseRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);

		sb.append("}");

		throw new NoSuchInventoryWarehouseRelException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel fetchByC_C_First(
		long classNameId, long commerceInventoryWarehouseId,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator) {

		List<CommerceInventoryWarehouseRel> list = findByC_C(
			classNameId, commerceInventoryWarehouseId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel findByC_C_Last(
			long classNameId, long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator)
		throws NoSuchInventoryWarehouseRelException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			fetchByC_C_Last(
				classNameId, commerceInventoryWarehouseId, orderByComparator);

		if (commerceInventoryWarehouseRel != null) {
			return commerceInventoryWarehouseRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);

		sb.append("}");

		throw new NoSuchInventoryWarehouseRelException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel fetchByC_C_Last(
		long classNameId, long commerceInventoryWarehouseId,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator) {

		int count = countByC_C(classNameId, commerceInventoryWarehouseId);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryWarehouseRel> list = findByC_C(
			classNameId, commerceInventoryWarehouseId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory warehouse rels before and after the current commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the current commerce inventory warehouse rel
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel[] findByC_C_PrevAndNext(
			long commerceInventoryWarehouseRelId, long classNameId,
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator)
		throws NoSuchInventoryWarehouseRelException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			findByPrimaryKey(commerceInventoryWarehouseRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouseRel[] array =
				new CommerceInventoryWarehouseRelImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commerceInventoryWarehouseRel, classNameId,
				commerceInventoryWarehouseId, orderByComparator, true);

			array[1] = commerceInventoryWarehouseRel;

			array[2] = getByC_C_PrevAndNext(
				session, commerceInventoryWarehouseRel, classNameId,
				commerceInventoryWarehouseId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryWarehouseRel getByC_C_PrevAndNext(
		Session session,
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel,
		long classNameId, long commerceInventoryWarehouseId,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_COMMERCEINVENTORYWAREHOUSEID_2);

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
			sb.append(CommerceInventoryWarehouseRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(commerceInventoryWarehouseId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryWarehouseRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryWarehouseRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 */
	@Override
	public void removeByC_C(
		long classNameId, long commerceInventoryWarehouseId) {

		for (CommerceInventoryWarehouseRel commerceInventoryWarehouseRel :
				findByC_C(
					classNameId, commerceInventoryWarehouseId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryWarehouseRel);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory warehouse rels
	 */
	@Override
	public int countByC_C(long classNameId, long commerceInventoryWarehouseId) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {
			classNameId, commerceInventoryWarehouseId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEINVENTORYWAREHOUSEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(commerceInventoryWarehouseId);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"commerceInventoryWarehouseRel.classNameId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_C_COMMERCEINVENTORYWAREHOUSEID_2 =
			"commerceInventoryWarehouseRel.commerceInventoryWarehouseId = ?";

	private FinderPath _finderPathFetchByC_C_CIWI;
	private FinderPath _finderPathCountByC_C_CIWI;

	/**
	 * Returns the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; or throws a <code>NoSuchInventoryWarehouseRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel findByC_C_CIWI(
			long classNameId, long classPK, long commerceInventoryWarehouseId)
		throws NoSuchInventoryWarehouseRelException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			fetchByC_C_CIWI(classNameId, classPK, commerceInventoryWarehouseId);

		if (commerceInventoryWarehouseRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", commerceInventoryWarehouseId=");
			sb.append(commerceInventoryWarehouseId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchInventoryWarehouseRelException(sb.toString());
		}

		return commerceInventoryWarehouseRel;
	}

	/**
	 * Returns the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel fetchByC_C_CIWI(
		long classNameId, long classPK, long commerceInventoryWarehouseId) {

		return fetchByC_C_CIWI(
			classNameId, classPK, commerceInventoryWarehouseId, true);
	}

	/**
	 * Returns the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel fetchByC_C_CIWI(
		long classNameId, long classPK, long commerceInventoryWarehouseId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				classNameId, classPK, commerceInventoryWarehouseId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_CIWI, finderArgs, this);
		}

		if (result instanceof CommerceInventoryWarehouseRel) {
			CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
				(CommerceInventoryWarehouseRel)result;

			if ((classNameId !=
					commerceInventoryWarehouseRel.getClassNameId()) ||
				(classPK != commerceInventoryWarehouseRel.getClassPK()) ||
				(commerceInventoryWarehouseId !=
					commerceInventoryWarehouseRel.
						getCommerceInventoryWarehouseId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CIWI_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CIWI_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_CIWI_COMMERCEINVENTORYWAREHOUSEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceInventoryWarehouseId);

				List<CommerceInventoryWarehouseRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_CIWI, finderArgs, list);
					}
				}
				else {
					CommerceInventoryWarehouseRel
						commerceInventoryWarehouseRel = list.get(0);

					result = commerceInventoryWarehouseRel;

					cacheResult(commerceInventoryWarehouseRel);
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
			return (CommerceInventoryWarehouseRel)result;
		}
	}

	/**
	 * Removes the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the commerce inventory warehouse rel that was removed
	 */
	@Override
	public CommerceInventoryWarehouseRel removeByC_C_CIWI(
			long classNameId, long classPK, long commerceInventoryWarehouseId)
		throws NoSuchInventoryWarehouseRelException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			findByC_C_CIWI(classNameId, classPK, commerceInventoryWarehouseId);

		return remove(commerceInventoryWarehouseRel);
	}

	/**
	 * Returns the number of commerce inventory warehouse rels where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory warehouse rels
	 */
	@Override
	public int countByC_C_CIWI(
		long classNameId, long classPK, long commerceInventoryWarehouseId) {

		FinderPath finderPath = _finderPathCountByC_C_CIWI;

		Object[] finderArgs = new Object[] {
			classNameId, classPK, commerceInventoryWarehouseId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CIWI_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CIWI_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_CIWI_COMMERCEINVENTORYWAREHOUSEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceInventoryWarehouseId);

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

	private static final String _FINDER_COLUMN_C_C_CIWI_CLASSNAMEID_2 =
		"commerceInventoryWarehouseRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CIWI_CLASSPK_2 =
		"commerceInventoryWarehouseRel.classPK = ? AND ";

	private static final String
		_FINDER_COLUMN_C_C_CIWI_COMMERCEINVENTORYWAREHOUSEID_2 =
			"commerceInventoryWarehouseRel.commerceInventoryWarehouseId = ?";

	public CommerceInventoryWarehouseRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceInventoryWarehouseRelId", "CIWarehouseRelId");
		dbColumnNames.put("commerceInventoryWarehouseId", "CIWarehouseId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceInventoryWarehouseRel.class);

		setModelImplClass(CommerceInventoryWarehouseRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceInventoryWarehouseRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce inventory warehouse rel in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouseRel the commerce inventory warehouse rel
	 */
	@Override
	public void cacheResult(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel) {

		entityCache.putResult(
			CommerceInventoryWarehouseRelImpl.class,
			commerceInventoryWarehouseRel.getPrimaryKey(),
			commerceInventoryWarehouseRel);

		finderCache.putResult(
			_finderPathFetchByC_C_CIWI,
			new Object[] {
				commerceInventoryWarehouseRel.getClassNameId(),
				commerceInventoryWarehouseRel.getClassPK(),
				commerceInventoryWarehouseRel.getCommerceInventoryWarehouseId()
			},
			commerceInventoryWarehouseRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce inventory warehouse rels in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouseRels the commerce inventory warehouse rels
	 */
	@Override
	public void cacheResult(
		List<CommerceInventoryWarehouseRel> commerceInventoryWarehouseRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceInventoryWarehouseRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceInventoryWarehouseRel commerceInventoryWarehouseRel :
				commerceInventoryWarehouseRels) {

			if (entityCache.getResult(
					CommerceInventoryWarehouseRelImpl.class,
					commerceInventoryWarehouseRel.getPrimaryKey()) == null) {

				cacheResult(commerceInventoryWarehouseRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce inventory warehouse rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceInventoryWarehouseRelImpl.class);

		finderCache.clearCache(CommerceInventoryWarehouseRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce inventory warehouse rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel) {

		entityCache.removeResult(
			CommerceInventoryWarehouseRelImpl.class,
			commerceInventoryWarehouseRel);
	}

	@Override
	public void clearCache(
		List<CommerceInventoryWarehouseRel> commerceInventoryWarehouseRels) {

		for (CommerceInventoryWarehouseRel commerceInventoryWarehouseRel :
				commerceInventoryWarehouseRels) {

			entityCache.removeResult(
				CommerceInventoryWarehouseRelImpl.class,
				commerceInventoryWarehouseRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceInventoryWarehouseRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceInventoryWarehouseRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceInventoryWarehouseRelModelImpl
			commerceInventoryWarehouseRelModelImpl) {

		Object[] args = new Object[] {
			commerceInventoryWarehouseRelModelImpl.getClassNameId(),
			commerceInventoryWarehouseRelModelImpl.getClassPK(),
			commerceInventoryWarehouseRelModelImpl.
				getCommerceInventoryWarehouseId()
		};

		finderCache.putResult(
			_finderPathCountByC_C_CIWI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_CIWI, args,
			commerceInventoryWarehouseRelModelImpl);
	}

	/**
	 * Creates a new commerce inventory warehouse rel with the primary key. Does not add the commerce inventory warehouse rel to the database.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key for the new commerce inventory warehouse rel
	 * @return the new commerce inventory warehouse rel
	 */
	@Override
	public CommerceInventoryWarehouseRel create(
		long commerceInventoryWarehouseRelId) {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			new CommerceInventoryWarehouseRelImpl();

		commerceInventoryWarehouseRel.setNew(true);
		commerceInventoryWarehouseRel.setPrimaryKey(
			commerceInventoryWarehouseRelId);

		commerceInventoryWarehouseRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceInventoryWarehouseRel;
	}

	/**
	 * Removes the commerce inventory warehouse rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel that was removed
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel remove(
			long commerceInventoryWarehouseRelId)
		throws NoSuchInventoryWarehouseRelException {

		return remove((Serializable)commerceInventoryWarehouseRelId);
	}

	/**
	 * Removes the commerce inventory warehouse rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel that was removed
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel remove(Serializable primaryKey)
		throws NoSuchInventoryWarehouseRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
				(CommerceInventoryWarehouseRel)session.get(
					CommerceInventoryWarehouseRelImpl.class, primaryKey);

			if (commerceInventoryWarehouseRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchInventoryWarehouseRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceInventoryWarehouseRel);
		}
		catch (NoSuchInventoryWarehouseRelException noSuchEntityException) {
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
	protected CommerceInventoryWarehouseRel removeImpl(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceInventoryWarehouseRel)) {
				commerceInventoryWarehouseRel =
					(CommerceInventoryWarehouseRel)session.get(
						CommerceInventoryWarehouseRelImpl.class,
						commerceInventoryWarehouseRel.getPrimaryKeyObj());
			}

			if (commerceInventoryWarehouseRel != null) {
				session.delete(commerceInventoryWarehouseRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceInventoryWarehouseRel != null) {
			clearCache(commerceInventoryWarehouseRel);
		}

		return commerceInventoryWarehouseRel;
	}

	@Override
	public CommerceInventoryWarehouseRel updateImpl(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel) {

		boolean isNew = commerceInventoryWarehouseRel.isNew();

		if (!(commerceInventoryWarehouseRel instanceof
				CommerceInventoryWarehouseRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceInventoryWarehouseRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceInventoryWarehouseRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceInventoryWarehouseRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceInventoryWarehouseRel implementation " +
					commerceInventoryWarehouseRel.getClass());
		}

		CommerceInventoryWarehouseRelModelImpl
			commerceInventoryWarehouseRelModelImpl =
				(CommerceInventoryWarehouseRelModelImpl)
					commerceInventoryWarehouseRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceInventoryWarehouseRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceInventoryWarehouseRel.setCreateDate(date);
			}
			else {
				commerceInventoryWarehouseRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceInventoryWarehouseRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceInventoryWarehouseRel.setModifiedDate(date);
			}
			else {
				commerceInventoryWarehouseRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceInventoryWarehouseRel);
			}
			else {
				commerceInventoryWarehouseRel =
					(CommerceInventoryWarehouseRel)session.merge(
						commerceInventoryWarehouseRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceInventoryWarehouseRelImpl.class,
			commerceInventoryWarehouseRelModelImpl, false, true);

		cacheUniqueFindersCache(commerceInventoryWarehouseRelModelImpl);

		if (isNew) {
			commerceInventoryWarehouseRel.setNew(false);
		}

		commerceInventoryWarehouseRel.resetOriginalValues();

		return commerceInventoryWarehouseRel;
	}

	/**
	 * Returns the commerce inventory warehouse rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchInventoryWarehouseRelException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			fetchByPrimaryKey(primaryKey);

		if (commerceInventoryWarehouseRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchInventoryWarehouseRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceInventoryWarehouseRel;
	}

	/**
	 * Returns the commerce inventory warehouse rel with the primary key or throws a <code>NoSuchInventoryWarehouseRelException</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel findByPrimaryKey(
			long commerceInventoryWarehouseRelId)
		throws NoSuchInventoryWarehouseRelException {

		return findByPrimaryKey((Serializable)commerceInventoryWarehouseRelId);
	}

	/**
	 * Returns the commerce inventory warehouse rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel, or <code>null</code> if a commerce inventory warehouse rel with the primary key could not be found
	 */
	@Override
	public CommerceInventoryWarehouseRel fetchByPrimaryKey(
		long commerceInventoryWarehouseRelId) {

		return fetchByPrimaryKey((Serializable)commerceInventoryWarehouseRelId);
	}

	/**
	 * Returns all the commerce inventory warehouse rels.
	 *
	 * @return the commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory warehouse rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @return the range of commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory warehouse rels
	 */
	@Override
	public List<CommerceInventoryWarehouseRel> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator,
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

		List<CommerceInventoryWarehouseRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryWarehouseRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEINVENTORYWAREHOUSEREL;

				sql = sql.concat(
					CommerceInventoryWarehouseRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceInventoryWarehouseRel>)QueryUtil.list(
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
	 * Removes all the commerce inventory warehouse rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceInventoryWarehouseRel commerceInventoryWarehouseRel :
				findAll()) {

			remove(commerceInventoryWarehouseRel);
		}
	}

	/**
	 * Returns the number of commerce inventory warehouse rels.
	 *
	 * @return the number of commerce inventory warehouse rels
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
					_SQL_COUNT_COMMERCEINVENTORYWAREHOUSEREL);

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
		return "CIWarehouseRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEINVENTORYWAREHOUSEREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceInventoryWarehouseRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce inventory warehouse rel persistence.
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

		_finderPathWithPaginationFindByCommerceInventoryWarehouseId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceInventoryWarehouseId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"CIWarehouseId"}, true);

		_finderPathWithoutPaginationFindByCommerceInventoryWarehouseId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceInventoryWarehouseId",
				new String[] {Long.class.getName()},
				new String[] {"CIWarehouseId"}, true);

		_finderPathCountByCommerceInventoryWarehouseId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceInventoryWarehouseId",
			new String[] {Long.class.getName()}, new String[] {"CIWarehouseId"},
			false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "CIWarehouseId"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "CIWarehouseId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "CIWarehouseId"}, false);

		_finderPathFetchByC_C_CIWI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_CIWI",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "CIWarehouseId"}, true);

		_finderPathCountByC_C_CIWI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_CIWI",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "CIWarehouseId"}, false);

		CommerceInventoryWarehouseRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceInventoryWarehouseRelUtil.setPersistence(null);

		entityCache.removeCache(
			CommerceInventoryWarehouseRelImpl.class.getName());
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEINVENTORYWAREHOUSEREL =
		"SELECT commerceInventoryWarehouseRel FROM CommerceInventoryWarehouseRel commerceInventoryWarehouseRel";

	private static final String
		_SQL_SELECT_COMMERCEINVENTORYWAREHOUSEREL_WHERE =
			"SELECT commerceInventoryWarehouseRel FROM CommerceInventoryWarehouseRel commerceInventoryWarehouseRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEINVENTORYWAREHOUSEREL =
		"SELECT COUNT(commerceInventoryWarehouseRel) FROM CommerceInventoryWarehouseRel commerceInventoryWarehouseRel";

	private static final String _SQL_COUNT_COMMERCEINVENTORYWAREHOUSEREL_WHERE =
		"SELECT COUNT(commerceInventoryWarehouseRel) FROM CommerceInventoryWarehouseRel commerceInventoryWarehouseRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceInventoryWarehouseRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceInventoryWarehouseRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceInventoryWarehouseRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryWarehouseRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"commerceInventoryWarehouseRelId", "commerceInventoryWarehouseId"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}