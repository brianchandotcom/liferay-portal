/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.NoSuchCPConfigurationListRelException;
import com.liferay.commerce.product.model.CPConfigurationListRel;
import com.liferay.commerce.product.model.CPConfigurationListRelTable;
import com.liferay.commerce.product.model.impl.CPConfigurationListRelImpl;
import com.liferay.commerce.product.model.impl.CPConfigurationListRelModelImpl;
import com.liferay.commerce.product.service.persistence.CPConfigurationListRelPersistence;
import com.liferay.commerce.product.service.persistence.CPConfigurationListRelUtil;
import com.liferay.commerce.product.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

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
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cp configuration list rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CPConfigurationListRelPersistence.class)
public class CPConfigurationListRelPersistenceImpl
	extends BasePersistenceImpl<CPConfigurationListRel>
	implements CPConfigurationListRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPConfigurationListRelUtil</code> to access the cp configuration list rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPConfigurationListRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCPConfigurationListId;
	private FinderPath _finderPathWithoutPaginationFindByCPConfigurationListId;
	private FinderPath _finderPathCountByCPConfigurationListId;

	/**
	 * Returns all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId) {

		return findByCPConfigurationListId(
			CPConfigurationListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @return the range of matching cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId, int start, int end) {

		return findByCPConfigurationListId(
			CPConfigurationListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId, int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return findByCPConfigurationListId(
			CPConfigurationListId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findByCPConfigurationListId(
		long CPConfigurationListId, int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationListRel.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath =
						_finderPathWithoutPaginationFindByCPConfigurationListId;
					finderArgs = new Object[] {CPConfigurationListId};
				}
			}
			else if (useFinderCache) {
				finderPath =
					_finderPathWithPaginationFindByCPConfigurationListId;
				finderArgs = new Object[] {
					CPConfigurationListId, start, end, orderByComparator
				};
			}

			List<CPConfigurationListRel> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationListRel>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationListRel cpConfigurationListRel : list) {
						if (CPConfigurationListId !=
								cpConfigurationListRel.
									getCPConfigurationListId()) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLISTREL_WHERE);

				sb.append(
					_FINDER_COLUMN_CPCONFIGURATIONLISTID_CPCONFIGURATIONLISTID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListRelModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(CPConfigurationListId);

					list = (List<CPConfigurationListRel>)QueryUtil.list(
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
	}

	/**
	 * Returns the first cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel findByCPConfigurationListId_First(
			long CPConfigurationListId,
			OrderByComparator<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException {

		CPConfigurationListRel cpConfigurationListRel =
			fetchByCPConfigurationListId_First(
				CPConfigurationListId, orderByComparator);

		if (cpConfigurationListRel != null) {
			return cpConfigurationListRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPConfigurationListId=");
		sb.append(CPConfigurationListId);

		sb.append("}");

		throw new NoSuchCPConfigurationListRelException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel fetchByCPConfigurationListId_First(
		long CPConfigurationListId,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		List<CPConfigurationListRel> list = findByCPConfigurationListId(
			CPConfigurationListId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel findByCPConfigurationListId_Last(
			long CPConfigurationListId,
			OrderByComparator<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException {

		CPConfigurationListRel cpConfigurationListRel =
			fetchByCPConfigurationListId_Last(
				CPConfigurationListId, orderByComparator);

		if (cpConfigurationListRel != null) {
			return cpConfigurationListRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPConfigurationListId=");
		sb.append(CPConfigurationListId);

		sb.append("}");

		throw new NoSuchCPConfigurationListRelException(sb.toString());
	}

	/**
	 * Returns the last cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel fetchByCPConfigurationListId_Last(
		long CPConfigurationListId,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		int count = countByCPConfigurationListId(CPConfigurationListId);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationListRel> list = findByCPConfigurationListId(
			CPConfigurationListId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration list rels before and after the current cp configuration list rel in the ordered set where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListRelId the primary key of the current cp configuration list rel
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	@Override
	public CPConfigurationListRel[] findByCPConfigurationListId_PrevAndNext(
			long CPConfigurationListRelId, long CPConfigurationListId,
			OrderByComparator<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException {

		CPConfigurationListRel cpConfigurationListRel = findByPrimaryKey(
			CPConfigurationListRelId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationListRel[] array = new CPConfigurationListRelImpl[3];

			array[0] = getByCPConfigurationListId_PrevAndNext(
				session, cpConfigurationListRel, CPConfigurationListId,
				orderByComparator, true);

			array[1] = cpConfigurationListRel;

			array[2] = getByCPConfigurationListId_PrevAndNext(
				session, cpConfigurationListRel, CPConfigurationListId,
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

	protected CPConfigurationListRel getByCPConfigurationListId_PrevAndNext(
		Session session, CPConfigurationListRel cpConfigurationListRel,
		long CPConfigurationListId,
		OrderByComparator<CPConfigurationListRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONLISTREL_WHERE);

		sb.append(_FINDER_COLUMN_CPCONFIGURATIONLISTID_CPCONFIGURATIONLISTID_2);

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
			sb.append(CPConfigurationListRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPConfigurationListId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpConfigurationListRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationListRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp configuration list rels where CPConfigurationListId = &#63; from the database.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 */
	@Override
	public void removeByCPConfigurationListId(long CPConfigurationListId) {
		for (CPConfigurationListRel cpConfigurationListRel :
				findByCPConfigurationListId(
					CPConfigurationListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpConfigurationListRel);
		}
	}

	/**
	 * Returns the number of cp configuration list rels where CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the number of matching cp configuration list rels
	 */
	@Override
	public int countByCPConfigurationListId(long CPConfigurationListId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationListRel.class)) {

			FinderPath finderPath = _finderPathCountByCPConfigurationListId;

			Object[] finderArgs = new Object[] {CPConfigurationListId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLISTREL_WHERE);

				sb.append(
					_FINDER_COLUMN_CPCONFIGURATIONLISTID_CPCONFIGURATIONLISTID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(CPConfigurationListId);

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
	}

	private static final String
		_FINDER_COLUMN_CPCONFIGURATIONLISTID_CPCONFIGURATIONLISTID_2 =
			"cpConfigurationListRel.CPConfigurationListId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId) {

		return findByC_C(
			classNameId, CPConfigurationListId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @return the range of matching cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId, int start, int end) {

		return findByC_C(classNameId, CPConfigurationListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId, int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return findByC_C(
			classNameId, CPConfigurationListId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findByC_C(
		long classNameId, long CPConfigurationListId, int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationListRel.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByC_C;
					finderArgs = new Object[] {
						classNameId, CPConfigurationListId
					};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByC_C;
				finderArgs = new Object[] {
					classNameId, CPConfigurationListId, start, end,
					orderByComparator
				};
			}

			List<CPConfigurationListRel> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationListRel>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationListRel cpConfigurationListRel : list) {
						if ((classNameId !=
								cpConfigurationListRel.getClassNameId()) ||
							(CPConfigurationListId !=
								cpConfigurationListRel.
									getCPConfigurationListId())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLISTREL_WHERE);

				sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				sb.append(_FINDER_COLUMN_C_C_CPCONFIGURATIONLISTID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListRelModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(classNameId);

					queryPos.add(CPConfigurationListId);

					list = (List<CPConfigurationListRel>)QueryUtil.list(
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
	}

	/**
	 * Returns the first cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel findByC_C_First(
			long classNameId, long CPConfigurationListId,
			OrderByComparator<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException {

		CPConfigurationListRel cpConfigurationListRel = fetchByC_C_First(
			classNameId, CPConfigurationListId, orderByComparator);

		if (cpConfigurationListRel != null) {
			return cpConfigurationListRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", CPConfigurationListId=");
		sb.append(CPConfigurationListId);

		sb.append("}");

		throw new NoSuchCPConfigurationListRelException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel fetchByC_C_First(
		long classNameId, long CPConfigurationListId,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		List<CPConfigurationListRel> list = findByC_C(
			classNameId, CPConfigurationListId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel findByC_C_Last(
			long classNameId, long CPConfigurationListId,
			OrderByComparator<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException {

		CPConfigurationListRel cpConfigurationListRel = fetchByC_C_Last(
			classNameId, CPConfigurationListId, orderByComparator);

		if (cpConfigurationListRel != null) {
			return cpConfigurationListRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", CPConfigurationListId=");
		sb.append(CPConfigurationListId);

		sb.append("}");

		throw new NoSuchCPConfigurationListRelException(sb.toString());
	}

	/**
	 * Returns the last cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel fetchByC_C_Last(
		long classNameId, long CPConfigurationListId,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		int count = countByC_C(classNameId, CPConfigurationListId);

		if (count == 0) {
			return null;
		}

		List<CPConfigurationListRel> list = findByC_C(
			classNameId, CPConfigurationListId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp configuration list rels before and after the current cp configuration list rel in the ordered set where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param CPConfigurationListRelId the primary key of the current cp configuration list rel
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	@Override
	public CPConfigurationListRel[] findByC_C_PrevAndNext(
			long CPConfigurationListRelId, long classNameId,
			long CPConfigurationListId,
			OrderByComparator<CPConfigurationListRel> orderByComparator)
		throws NoSuchCPConfigurationListRelException {

		CPConfigurationListRel cpConfigurationListRel = findByPrimaryKey(
			CPConfigurationListRelId);

		Session session = null;

		try {
			session = openSession();

			CPConfigurationListRel[] array = new CPConfigurationListRelImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, cpConfigurationListRel, classNameId,
				CPConfigurationListId, orderByComparator, true);

			array[1] = cpConfigurationListRel;

			array[2] = getByC_C_PrevAndNext(
				session, cpConfigurationListRel, classNameId,
				CPConfigurationListId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPConfigurationListRel getByC_C_PrevAndNext(
		Session session, CPConfigurationListRel cpConfigurationListRel,
		long classNameId, long CPConfigurationListId,
		OrderByComparator<CPConfigurationListRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CPCONFIGURATIONLISTREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CPCONFIGURATIONLISTID_2);

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
			sb.append(CPConfigurationListRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(CPConfigurationListId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpConfigurationListRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPConfigurationListRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 */
	@Override
	public void removeByC_C(long classNameId, long CPConfigurationListId) {
		for (CPConfigurationListRel cpConfigurationListRel :
				findByC_C(
					classNameId, CPConfigurationListId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpConfigurationListRel);
		}
	}

	/**
	 * Returns the number of cp configuration list rels where classNameId = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the number of matching cp configuration list rels
	 */
	@Override
	public int countByC_C(long classNameId, long CPConfigurationListId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationListRel.class)) {

			FinderPath finderPath = _finderPathCountByC_C;

			Object[] finderArgs = new Object[] {
				classNameId, CPConfigurationListId
			};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLISTREL_WHERE);

				sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				sb.append(_FINDER_COLUMN_C_C_CPCONFIGURATIONLISTID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(classNameId);

					queryPos.add(CPConfigurationListId);

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
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"cpConfigurationListRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CPCONFIGURATIONLISTID_2 =
		"cpConfigurationListRel.CPConfigurationListId = ?";

	private FinderPath _finderPathFetchByC_C_C;

	/**
	 * Returns the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; or throws a <code>NoSuchCPConfigurationListRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel findByC_C_C(
			long classNameId, long classPK, long CPConfigurationListId)
		throws NoSuchCPConfigurationListRelException {

		CPConfigurationListRel cpConfigurationListRel = fetchByC_C_C(
			classNameId, classPK, CPConfigurationListId);

		if (cpConfigurationListRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", CPConfigurationListId=");
			sb.append(CPConfigurationListId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPConfigurationListRelException(sb.toString());
		}

		return cpConfigurationListRel;
	}

	/**
	 * Returns the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel fetchByC_C_C(
		long classNameId, long classPK, long CPConfigurationListId) {

		return fetchByC_C_C(classNameId, classPK, CPConfigurationListId, true);
	}

	/**
	 * Returns the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list rel, or <code>null</code> if a matching cp configuration list rel could not be found
	 */
	@Override
	public CPConfigurationListRel fetchByC_C_C(
		long classNameId, long classPK, long CPConfigurationListId,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationListRel.class)) {

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {
					classNameId, classPK, CPConfigurationListId
				};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByC_C_C, finderArgs, this);
			}

			if (result instanceof CPConfigurationListRel) {
				CPConfigurationListRel cpConfigurationListRel =
					(CPConfigurationListRel)result;

				if ((classNameId != cpConfigurationListRel.getClassNameId()) ||
					(classPK != cpConfigurationListRel.getClassPK()) ||
					(CPConfigurationListId !=
						cpConfigurationListRel.getCPConfigurationListId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(5);

				sb.append(_SQL_SELECT_CPCONFIGURATIONLISTREL_WHERE);

				sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

				sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

				sb.append(_FINDER_COLUMN_C_C_C_CPCONFIGURATIONLISTID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(classNameId);

					queryPos.add(classPK);

					queryPos.add(CPConfigurationListId);

					List<CPConfigurationListRel> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByC_C_C, finderArgs, list);
						}
					}
					else {
						CPConfigurationListRel cpConfigurationListRel =
							list.get(0);

						result = cpConfigurationListRel;

						cacheResult(cpConfigurationListRel);
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
				return (CPConfigurationListRel)result;
			}
		}
	}

	/**
	 * Removes the cp configuration list rel where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the cp configuration list rel that was removed
	 */
	@Override
	public CPConfigurationListRel removeByC_C_C(
			long classNameId, long classPK, long CPConfigurationListId)
		throws NoSuchCPConfigurationListRelException {

		CPConfigurationListRel cpConfigurationListRel = findByC_C_C(
			classNameId, classPK, CPConfigurationListId);

		return remove(cpConfigurationListRel);
	}

	/**
	 * Returns the number of cp configuration list rels where classNameId = &#63; and classPK = &#63; and CPConfigurationListId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CPConfigurationListId the cp configuration list ID
	 * @return the number of matching cp configuration list rels
	 */
	@Override
	public int countByC_C_C(
		long classNameId, long classPK, long CPConfigurationListId) {

		CPConfigurationListRel cpConfigurationListRel = fetchByC_C_C(
			classNameId, classPK, CPConfigurationListId);

		if (cpConfigurationListRel == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 =
		"cpConfigurationListRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"cpConfigurationListRel.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CPCONFIGURATIONLISTID_2 =
		"cpConfigurationListRel.CPConfigurationListId = ?";

	public CPConfigurationListRelPersistenceImpl() {
		setModelClass(CPConfigurationListRel.class);

		setModelImplClass(CPConfigurationListRelImpl.class);
		setModelPKClass(long.class);

		setTable(CPConfigurationListRelTable.INSTANCE);
	}

	/**
	 * Caches the cp configuration list rel in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationListRel the cp configuration list rel
	 */
	@Override
	public void cacheResult(CPConfigurationListRel cpConfigurationListRel) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpConfigurationListRel.getCtCollectionId())) {

			entityCache.putResult(
				CPConfigurationListRelImpl.class,
				cpConfigurationListRel.getPrimaryKey(), cpConfigurationListRel);

			finderCache.putResult(
				_finderPathFetchByC_C_C,
				new Object[] {
					cpConfigurationListRel.getClassNameId(),
					cpConfigurationListRel.getClassPK(),
					cpConfigurationListRel.getCPConfigurationListId()
				},
				cpConfigurationListRel);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp configuration list rels in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationListRels the cp configuration list rels
	 */
	@Override
	public void cacheResult(
		List<CPConfigurationListRel> cpConfigurationListRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpConfigurationListRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPConfigurationListRel cpConfigurationListRel :
				cpConfigurationListRels) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						cpConfigurationListRel.getCtCollectionId())) {

				if (entityCache.getResult(
						CPConfigurationListRelImpl.class,
						cpConfigurationListRel.getPrimaryKey()) == null) {

					cacheResult(cpConfigurationListRel);
				}
			}
		}
	}

	/**
	 * Clears the cache for all cp configuration list rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPConfigurationListRelImpl.class);

		finderCache.clearCache(CPConfigurationListRelImpl.class);
	}

	/**
	 * Clears the cache for the cp configuration list rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPConfigurationListRel cpConfigurationListRel) {
		entityCache.removeResult(
			CPConfigurationListRelImpl.class, cpConfigurationListRel);
	}

	@Override
	public void clearCache(
		List<CPConfigurationListRel> cpConfigurationListRels) {

		for (CPConfigurationListRel cpConfigurationListRel :
				cpConfigurationListRels) {

			entityCache.removeResult(
				CPConfigurationListRelImpl.class, cpConfigurationListRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPConfigurationListRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPConfigurationListRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPConfigurationListRelModelImpl cpConfigurationListRelModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpConfigurationListRelModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				cpConfigurationListRelModelImpl.getClassNameId(),
				cpConfigurationListRelModelImpl.getClassPK(),
				cpConfigurationListRelModelImpl.getCPConfigurationListId()
			};

			finderCache.putResult(
				_finderPathFetchByC_C_C, args, cpConfigurationListRelModelImpl);
		}
	}

	/**
	 * Creates a new cp configuration list rel with the primary key. Does not add the cp configuration list rel to the database.
	 *
	 * @param CPConfigurationListRelId the primary key for the new cp configuration list rel
	 * @return the new cp configuration list rel
	 */
	@Override
	public CPConfigurationListRel create(long CPConfigurationListRelId) {
		CPConfigurationListRel cpConfigurationListRel =
			new CPConfigurationListRelImpl();

		cpConfigurationListRel.setNew(true);
		cpConfigurationListRel.setPrimaryKey(CPConfigurationListRelId);

		cpConfigurationListRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpConfigurationListRel;
	}

	/**
	 * Removes the cp configuration list rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPConfigurationListRelId the primary key of the cp configuration list rel
	 * @return the cp configuration list rel that was removed
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	@Override
	public CPConfigurationListRel remove(long CPConfigurationListRelId)
		throws NoSuchCPConfigurationListRelException {

		return remove((Serializable)CPConfigurationListRelId);
	}

	/**
	 * Removes the cp configuration list rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp configuration list rel
	 * @return the cp configuration list rel that was removed
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	@Override
	public CPConfigurationListRel remove(Serializable primaryKey)
		throws NoSuchCPConfigurationListRelException {

		Session session = null;

		try {
			session = openSession();

			CPConfigurationListRel cpConfigurationListRel =
				(CPConfigurationListRel)session.get(
					CPConfigurationListRelImpl.class, primaryKey);

			if (cpConfigurationListRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPConfigurationListRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpConfigurationListRel);
		}
		catch (NoSuchCPConfigurationListRelException noSuchEntityException) {
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
	protected CPConfigurationListRel removeImpl(
		CPConfigurationListRel cpConfigurationListRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpConfigurationListRel)) {
				cpConfigurationListRel = (CPConfigurationListRel)session.get(
					CPConfigurationListRelImpl.class,
					cpConfigurationListRel.getPrimaryKeyObj());
			}

			if ((cpConfigurationListRel != null) &&
				ctPersistenceHelper.isRemove(cpConfigurationListRel)) {

				session.delete(cpConfigurationListRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpConfigurationListRel != null) {
			clearCache(cpConfigurationListRel);
		}

		return cpConfigurationListRel;
	}

	@Override
	public CPConfigurationListRel updateImpl(
		CPConfigurationListRel cpConfigurationListRel) {

		boolean isNew = cpConfigurationListRel.isNew();

		if (!(cpConfigurationListRel instanceof
				CPConfigurationListRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpConfigurationListRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpConfigurationListRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpConfigurationListRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPConfigurationListRel implementation " +
					cpConfigurationListRel.getClass());
		}

		CPConfigurationListRelModelImpl cpConfigurationListRelModelImpl =
			(CPConfigurationListRelModelImpl)cpConfigurationListRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpConfigurationListRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpConfigurationListRel.setCreateDate(date);
			}
			else {
				cpConfigurationListRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpConfigurationListRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpConfigurationListRel.setModifiedDate(date);
			}
			else {
				cpConfigurationListRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(cpConfigurationListRel)) {
				if (!isNew) {
					session.evict(
						CPConfigurationListRelImpl.class,
						cpConfigurationListRel.getPrimaryKeyObj());
				}

				session.save(cpConfigurationListRel);
			}
			else {
				cpConfigurationListRel = (CPConfigurationListRel)session.merge(
					cpConfigurationListRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPConfigurationListRelImpl.class, cpConfigurationListRelModelImpl,
			false, true);

		cacheUniqueFindersCache(cpConfigurationListRelModelImpl);

		if (isNew) {
			cpConfigurationListRel.setNew(false);
		}

		cpConfigurationListRel.resetOriginalValues();

		return cpConfigurationListRel;
	}

	/**
	 * Returns the cp configuration list rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp configuration list rel
	 * @return the cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	@Override
	public CPConfigurationListRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPConfigurationListRelException {

		CPConfigurationListRel cpConfigurationListRel = fetchByPrimaryKey(
			primaryKey);

		if (cpConfigurationListRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPConfigurationListRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpConfigurationListRel;
	}

	/**
	 * Returns the cp configuration list rel with the primary key or throws a <code>NoSuchCPConfigurationListRelException</code> if it could not be found.
	 *
	 * @param CPConfigurationListRelId the primary key of the cp configuration list rel
	 * @return the cp configuration list rel
	 * @throws NoSuchCPConfigurationListRelException if a cp configuration list rel with the primary key could not be found
	 */
	@Override
	public CPConfigurationListRel findByPrimaryKey(
			long CPConfigurationListRelId)
		throws NoSuchCPConfigurationListRelException {

		return findByPrimaryKey((Serializable)CPConfigurationListRelId);
	}

	/**
	 * Returns the cp configuration list rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp configuration list rel
	 * @return the cp configuration list rel, or <code>null</code> if a cp configuration list rel with the primary key could not be found
	 */
	@Override
	public CPConfigurationListRel fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				CPConfigurationListRel.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		CPConfigurationListRel cpConfigurationListRel =
			(CPConfigurationListRel)entityCache.getResult(
				CPConfigurationListRelImpl.class, primaryKey);

		if (cpConfigurationListRel != null) {
			return cpConfigurationListRel;
		}

		Session session = null;

		try {
			session = openSession();

			cpConfigurationListRel = (CPConfigurationListRel)session.get(
				CPConfigurationListRelImpl.class, primaryKey);

			if (cpConfigurationListRel != null) {
				cacheResult(cpConfigurationListRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return cpConfigurationListRel;
	}

	/**
	 * Returns the cp configuration list rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPConfigurationListRelId the primary key of the cp configuration list rel
	 * @return the cp configuration list rel, or <code>null</code> if a cp configuration list rel with the primary key could not be found
	 */
	@Override
	public CPConfigurationListRel fetchByPrimaryKey(
		long CPConfigurationListRelId) {

		return fetchByPrimaryKey((Serializable)CPConfigurationListRelId);
	}

	@Override
	public Map<Serializable, CPConfigurationListRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CPConfigurationListRel.class)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPConfigurationListRel> map =
			new HashMap<Serializable, CPConfigurationListRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPConfigurationListRel cpConfigurationListRel = fetchByPrimaryKey(
				primaryKey);

			if (cpConfigurationListRel != null) {
				map.put(primaryKey, cpConfigurationListRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						CPConfigurationListRel.class, primaryKey)) {

				CPConfigurationListRel cpConfigurationListRel =
					(CPConfigurationListRel)entityCache.getResult(
						CPConfigurationListRelImpl.class, primaryKey);

				if (cpConfigurationListRel == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, cpConfigurationListRel);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
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

			for (CPConfigurationListRel cpConfigurationListRel :
					(List<CPConfigurationListRel>)query.list()) {

				map.put(
					cpConfigurationListRel.getPrimaryKeyObj(),
					cpConfigurationListRel);

				cacheResult(cpConfigurationListRel);
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
	 * Returns all the cp configuration list rels.
	 *
	 * @return the cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration list rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @return the range of cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration list rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration list rels
	 * @param end the upper bound of the range of cp configuration list rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp configuration list rels
	 */
	@Override
	public List<CPConfigurationListRel> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationListRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationListRel.class)) {

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

			List<CPConfigurationListRel> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationListRel>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_CPCONFIGURATIONLISTREL);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_CPCONFIGURATIONLISTREL;

					sql = sql.concat(
						CPConfigurationListRelModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<CPConfigurationListRel>)QueryUtil.list(
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
	}

	/**
	 * Removes all the cp configuration list rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPConfigurationListRel cpConfigurationListRel : findAll()) {
			remove(cpConfigurationListRel);
		}
	}

	/**
	 * Returns the number of cp configuration list rels.
	 *
	 * @return the number of cp configuration list rels
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationListRel.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_CPCONFIGURATIONLISTREL);

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
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CPConfigurationListRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPCONFIGURATIONLISTREL;
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
		return CPConfigurationListRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPConfigurationListRel";
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
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctMergeColumnNames.add("CPConfigurationListId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPConfigurationListRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"classNameId", "classPK", "CPConfigurationListId"});
	}

	/**
	 * Initializes the cp configuration list rel persistence.
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

		_finderPathWithPaginationFindByCPConfigurationListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPConfigurationListId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPConfigurationListId"}, true);

		_finderPathWithoutPaginationFindByCPConfigurationListId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCPConfigurationListId",
				new String[] {Long.class.getName()},
				new String[] {"CPConfigurationListId"}, true);

		_finderPathCountByCPConfigurationListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPConfigurationListId", new String[] {Long.class.getName()},
			new String[] {"CPConfigurationListId"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "CPConfigurationListId"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "CPConfigurationListId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "CPConfigurationListId"}, false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "CPConfigurationListId"},
			true);

		CPConfigurationListRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPConfigurationListRelUtil.setPersistence(null);

		entityCache.removeCache(CPConfigurationListRelImpl.class.getName());
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
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CPCONFIGURATIONLISTREL =
		"SELECT cpConfigurationListRel FROM CPConfigurationListRel cpConfigurationListRel";

	private static final String _SQL_SELECT_CPCONFIGURATIONLISTREL_WHERE =
		"SELECT cpConfigurationListRel FROM CPConfigurationListRel cpConfigurationListRel WHERE ";

	private static final String _SQL_COUNT_CPCONFIGURATIONLISTREL =
		"SELECT COUNT(cpConfigurationListRel) FROM CPConfigurationListRel cpConfigurationListRel";

	private static final String _SQL_COUNT_CPCONFIGURATIONLISTREL_WHERE =
		"SELECT COUNT(cpConfigurationListRel) FROM CPConfigurationListRel cpConfigurationListRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpConfigurationListRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPConfigurationListRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPConfigurationListRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPConfigurationListRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-292158015