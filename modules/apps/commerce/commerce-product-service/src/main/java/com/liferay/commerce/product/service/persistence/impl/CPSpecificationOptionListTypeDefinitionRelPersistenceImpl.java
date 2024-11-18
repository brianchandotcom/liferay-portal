/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionListTypeDefinitionRelException;
import com.liferay.commerce.product.model.CPSpecificationOptionListTypeDefinitionRel;
import com.liferay.commerce.product.model.CPSpecificationOptionListTypeDefinitionRelTable;
import com.liferay.commerce.product.model.impl.CPSpecificationOptionListTypeDefinitionRelImpl;
import com.liferay.commerce.product.model.impl.CPSpecificationOptionListTypeDefinitionRelModelImpl;
import com.liferay.commerce.product.service.persistence.CPSpecificationOptionListTypeDefinitionRelPersistence;
import com.liferay.commerce.product.service.persistence.CPSpecificationOptionListTypeDefinitionRelUtil;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cp specification option list type definition rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(
	service = CPSpecificationOptionListTypeDefinitionRelPersistence.class
)
public class CPSpecificationOptionListTypeDefinitionRelPersistenceImpl
	extends BasePersistenceImpl<CPSpecificationOptionListTypeDefinitionRel>
	implements CPSpecificationOptionListTypeDefinitionRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPSpecificationOptionListTypeDefinitionRelUtil</code> to access the cp specification option list type definition rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPSpecificationOptionListTypeDefinitionRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCPSpecificationOptionId;
	private FinderPath
		_finderPathWithoutPaginationFindByCPSpecificationOptionId;
	private FinderPath _finderPathCountByCPSpecificationOptionId;

	/**
	 * Returns all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the matching cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(long CPSpecificationOptionId) {

		return findByCPSpecificationOptionId(
			CPSpecificationOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @return the range of matching cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end) {

		return findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		return findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator,
			boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPSpecificationOptionListTypeDefinitionRel.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath =
						_finderPathWithoutPaginationFindByCPSpecificationOptionId;
					finderArgs = new Object[] {CPSpecificationOptionId};
				}
			}
			else if (useFinderCache) {
				finderPath =
					_finderPathWithPaginationFindByCPSpecificationOptionId;
				finderArgs = new Object[] {
					CPSpecificationOptionId, start, end, orderByComparator
				};
			}

			List<CPSpecificationOptionListTypeDefinitionRel> list = null;

			if (useFinderCache) {
				list =
					(List<CPSpecificationOptionListTypeDefinitionRel>)
						finderCache.getResult(finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPSpecificationOptionListTypeDefinitionRel
							cpSpecificationOptionListTypeDefinitionRel : list) {

						if (CPSpecificationOptionId !=
								cpSpecificationOptionListTypeDefinitionRel.
									getCPSpecificationOptionId()) {

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

				sb.append(
					_SQL_SELECT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL_WHERE);

				sb.append(
					_FINDER_COLUMN_CPSPECIFICATIONOPTIONID_CPSPECIFICATIONOPTIONID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(
						CPSpecificationOptionListTypeDefinitionRelModelImpl.
							ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(CPSpecificationOptionId);

					list =
						(List<CPSpecificationOptionListTypeDefinitionRel>)
							QueryUtil.list(query, getDialect(), start, end);

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
	 * Returns the first cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel
			findByCPSpecificationOptionId_First(
				long CPSpecificationOptionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel =
				fetchByCPSpecificationOptionId_First(
					CPSpecificationOptionId, orderByComparator);

		if (cpSpecificationOptionListTypeDefinitionRel != null) {
			return cpSpecificationOptionListTypeDefinitionRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPSpecificationOptionId=");
		sb.append(CPSpecificationOptionId);

		sb.append("}");

		throw new NoSuchCPSpecificationOptionListTypeDefinitionRelException(
			sb.toString());
	}

	/**
	 * Returns the first cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel
		fetchByCPSpecificationOptionId_First(
			long CPSpecificationOptionId,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		List<CPSpecificationOptionListTypeDefinitionRel> list =
			findByCPSpecificationOptionId(
				CPSpecificationOptionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel
			findByCPSpecificationOptionId_Last(
				long CPSpecificationOptionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel =
				fetchByCPSpecificationOptionId_Last(
					CPSpecificationOptionId, orderByComparator);

		if (cpSpecificationOptionListTypeDefinitionRel != null) {
			return cpSpecificationOptionListTypeDefinitionRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPSpecificationOptionId=");
		sb.append(CPSpecificationOptionId);

		sb.append("}");

		throw new NoSuchCPSpecificationOptionListTypeDefinitionRelException(
			sb.toString());
	}

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel
		fetchByCPSpecificationOptionId_Last(
			long CPSpecificationOptionId,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		int count = countByCPSpecificationOptionId(CPSpecificationOptionId);

		if (count == 0) {
			return null;
		}

		List<CPSpecificationOptionListTypeDefinitionRel> list =
			findByCPSpecificationOptionId(
				CPSpecificationOptionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp specification option list type definition rels before and after the current cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the current cp specification option list type definition rel
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel[]
			findByCPSpecificationOptionId_PrevAndNext(
				long CPSpecificationOptionListTypeDefinitionRelId,
				long CPSpecificationOptionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel = findByPrimaryKey(
				CPSpecificationOptionListTypeDefinitionRelId);

		Session session = null;

		try {
			session = openSession();

			CPSpecificationOptionListTypeDefinitionRel[] array =
				new CPSpecificationOptionListTypeDefinitionRelImpl[3];

			array[0] = getByCPSpecificationOptionId_PrevAndNext(
				session, cpSpecificationOptionListTypeDefinitionRel,
				CPSpecificationOptionId, orderByComparator, true);

			array[1] = cpSpecificationOptionListTypeDefinitionRel;

			array[2] = getByCPSpecificationOptionId_PrevAndNext(
				session, cpSpecificationOptionListTypeDefinitionRel,
				CPSpecificationOptionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPSpecificationOptionListTypeDefinitionRel
		getByCPSpecificationOptionId_PrevAndNext(
			Session session,
			CPSpecificationOptionListTypeDefinitionRel
				cpSpecificationOptionListTypeDefinitionRel,
			long CPSpecificationOptionId,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
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

		sb.append(_SQL_SELECT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL_WHERE);

		sb.append(
			_FINDER_COLUMN_CPSPECIFICATIONOPTIONID_CPSPECIFICATIONOPTIONID_2);

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
				CPSpecificationOptionListTypeDefinitionRelModelImpl.
					ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPSpecificationOptionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpSpecificationOptionListTypeDefinitionRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPSpecificationOptionListTypeDefinitionRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp specification option list type definition rels where CPSpecificationOptionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 */
	@Override
	public void removeByCPSpecificationOptionId(long CPSpecificationOptionId) {
		for (CPSpecificationOptionListTypeDefinitionRel
				cpSpecificationOptionListTypeDefinitionRel :
					findByCPSpecificationOptionId(
						CPSpecificationOptionId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(cpSpecificationOptionListTypeDefinitionRel);
		}
	}

	/**
	 * Returns the number of cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the number of matching cp specification option list type definition rels
	 */
	@Override
	public int countByCPSpecificationOptionId(long CPSpecificationOptionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPSpecificationOptionListTypeDefinitionRel.class)) {

			FinderPath finderPath = _finderPathCountByCPSpecificationOptionId;

			Object[] finderArgs = new Object[] {CPSpecificationOptionId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(
					_SQL_COUNT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL_WHERE);

				sb.append(
					_FINDER_COLUMN_CPSPECIFICATIONOPTIONID_CPSPECIFICATIONOPTIONID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(CPSpecificationOptionId);

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
		_FINDER_COLUMN_CPSPECIFICATIONOPTIONID_CPSPECIFICATIONOPTIONID_2 =
			"cpSpecificationOptionListTypeDefinitionRel.CPSpecificationOptionId = ?";

	private FinderPath _finderPathWithPaginationFindByListTypeDefinitionId;
	private FinderPath _finderPathWithoutPaginationFindByListTypeDefinitionId;
	private FinderPath _finderPathCountByListTypeDefinitionId;

	/**
	 * Returns all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(long listTypeDefinitionId) {

		return findByListTypeDefinitionId(
			listTypeDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @return the range of matching cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(
			long listTypeDefinitionId, int start, int end) {

		return findByListTypeDefinitionId(
			listTypeDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(
			long listTypeDefinitionId, int start, int end,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		return findByListTypeDefinitionId(
			listTypeDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(
			long listTypeDefinitionId, int start, int end,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator,
			boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPSpecificationOptionListTypeDefinitionRel.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath =
						_finderPathWithoutPaginationFindByListTypeDefinitionId;
					finderArgs = new Object[] {listTypeDefinitionId};
				}
			}
			else if (useFinderCache) {
				finderPath =
					_finderPathWithPaginationFindByListTypeDefinitionId;
				finderArgs = new Object[] {
					listTypeDefinitionId, start, end, orderByComparator
				};
			}

			List<CPSpecificationOptionListTypeDefinitionRel> list = null;

			if (useFinderCache) {
				list =
					(List<CPSpecificationOptionListTypeDefinitionRel>)
						finderCache.getResult(finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPSpecificationOptionListTypeDefinitionRel
							cpSpecificationOptionListTypeDefinitionRel : list) {

						if (listTypeDefinitionId !=
								cpSpecificationOptionListTypeDefinitionRel.
									getListTypeDefinitionId()) {

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

				sb.append(
					_SQL_SELECT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL_WHERE);

				sb.append(
					_FINDER_COLUMN_LISTTYPEDEFINITIONID_LISTTYPEDEFINITIONID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(
						CPSpecificationOptionListTypeDefinitionRelModelImpl.
							ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(listTypeDefinitionId);

					list =
						(List<CPSpecificationOptionListTypeDefinitionRel>)
							QueryUtil.list(query, getDialect(), start, end);

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
	 * Returns the first cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel
			findByListTypeDefinitionId_First(
				long listTypeDefinitionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel =
				fetchByListTypeDefinitionId_First(
					listTypeDefinitionId, orderByComparator);

		if (cpSpecificationOptionListTypeDefinitionRel != null) {
			return cpSpecificationOptionListTypeDefinitionRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("listTypeDefinitionId=");
		sb.append(listTypeDefinitionId);

		sb.append("}");

		throw new NoSuchCPSpecificationOptionListTypeDefinitionRelException(
			sb.toString());
	}

	/**
	 * Returns the first cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel
		fetchByListTypeDefinitionId_First(
			long listTypeDefinitionId,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		List<CPSpecificationOptionListTypeDefinitionRel> list =
			findByListTypeDefinitionId(
				listTypeDefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel
			findByListTypeDefinitionId_Last(
				long listTypeDefinitionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel =
				fetchByListTypeDefinitionId_Last(
					listTypeDefinitionId, orderByComparator);

		if (cpSpecificationOptionListTypeDefinitionRel != null) {
			return cpSpecificationOptionListTypeDefinitionRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("listTypeDefinitionId=");
		sb.append(listTypeDefinitionId);

		sb.append("}");

		throw new NoSuchCPSpecificationOptionListTypeDefinitionRelException(
			sb.toString());
	}

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel
		fetchByListTypeDefinitionId_Last(
			long listTypeDefinitionId,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		int count = countByListTypeDefinitionId(listTypeDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CPSpecificationOptionListTypeDefinitionRel> list =
			findByListTypeDefinitionId(
				listTypeDefinitionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp specification option list type definition rels before and after the current cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the current cp specification option list type definition rel
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel[]
			findByListTypeDefinitionId_PrevAndNext(
				long CPSpecificationOptionListTypeDefinitionRelId,
				long listTypeDefinitionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel = findByPrimaryKey(
				CPSpecificationOptionListTypeDefinitionRelId);

		Session session = null;

		try {
			session = openSession();

			CPSpecificationOptionListTypeDefinitionRel[] array =
				new CPSpecificationOptionListTypeDefinitionRelImpl[3];

			array[0] = getByListTypeDefinitionId_PrevAndNext(
				session, cpSpecificationOptionListTypeDefinitionRel,
				listTypeDefinitionId, orderByComparator, true);

			array[1] = cpSpecificationOptionListTypeDefinitionRel;

			array[2] = getByListTypeDefinitionId_PrevAndNext(
				session, cpSpecificationOptionListTypeDefinitionRel,
				listTypeDefinitionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPSpecificationOptionListTypeDefinitionRel
		getByListTypeDefinitionId_PrevAndNext(
			Session session,
			CPSpecificationOptionListTypeDefinitionRel
				cpSpecificationOptionListTypeDefinitionRel,
			long listTypeDefinitionId,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
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

		sb.append(_SQL_SELECT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL_WHERE);

		sb.append(_FINDER_COLUMN_LISTTYPEDEFINITIONID_LISTTYPEDEFINITIONID_2);

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
				CPSpecificationOptionListTypeDefinitionRelModelImpl.
					ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(listTypeDefinitionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpSpecificationOptionListTypeDefinitionRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPSpecificationOptionListTypeDefinitionRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp specification option list type definition rels where listTypeDefinitionId = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 */
	@Override
	public void removeByListTypeDefinitionId(long listTypeDefinitionId) {
		for (CPSpecificationOptionListTypeDefinitionRel
				cpSpecificationOptionListTypeDefinitionRel :
					findByListTypeDefinitionId(
						listTypeDefinitionId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(cpSpecificationOptionListTypeDefinitionRel);
		}
	}

	/**
	 * Returns the number of cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching cp specification option list type definition rels
	 */
	@Override
	public int countByListTypeDefinitionId(long listTypeDefinitionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPSpecificationOptionListTypeDefinitionRel.class)) {

			FinderPath finderPath = _finderPathCountByListTypeDefinitionId;

			Object[] finderArgs = new Object[] {listTypeDefinitionId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(
					_SQL_COUNT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL_WHERE);

				sb.append(
					_FINDER_COLUMN_LISTTYPEDEFINITIONID_LISTTYPEDEFINITIONID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(listTypeDefinitionId);

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
		_FINDER_COLUMN_LISTTYPEDEFINITIONID_LISTTYPEDEFINITIONID_2 =
			"cpSpecificationOptionListTypeDefinitionRel.listTypeDefinitionId = ?";

	private FinderPath _finderPathFetchByC_L;

	/**
	 * Returns the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or throws a <code>NoSuchCPSpecificationOptionListTypeDefinitionRelException</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel findByC_L(
			long CPSpecificationOptionId, long listTypeDefinitionId)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel = fetchByC_L(
				CPSpecificationOptionId, listTypeDefinitionId);

		if (cpSpecificationOptionListTypeDefinitionRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPSpecificationOptionId=");
			sb.append(CPSpecificationOptionId);

			sb.append(", listTypeDefinitionId=");
			sb.append(listTypeDefinitionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPSpecificationOptionListTypeDefinitionRelException(
				sb.toString());
		}

		return cpSpecificationOptionListTypeDefinitionRel;
	}

	/**
	 * Returns the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel fetchByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId) {

		return fetchByC_L(CPSpecificationOptionId, listTypeDefinitionId, true);
	}

	/**
	 * Returns the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel fetchByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPSpecificationOptionListTypeDefinitionRel.class)) {

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {
					CPSpecificationOptionId, listTypeDefinitionId
				};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByC_L, finderArgs, this);
			}

			if (result instanceof CPSpecificationOptionListTypeDefinitionRel) {
				CPSpecificationOptionListTypeDefinitionRel
					cpSpecificationOptionListTypeDefinitionRel =
						(CPSpecificationOptionListTypeDefinitionRel)result;

				if ((CPSpecificationOptionId !=
						cpSpecificationOptionListTypeDefinitionRel.
							getCPSpecificationOptionId()) ||
					(listTypeDefinitionId !=
						cpSpecificationOptionListTypeDefinitionRel.
							getListTypeDefinitionId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(
					_SQL_SELECT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL_WHERE);

				sb.append(_FINDER_COLUMN_C_L_CPSPECIFICATIONOPTIONID_2);

				sb.append(_FINDER_COLUMN_C_L_LISTTYPEDEFINITIONID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(CPSpecificationOptionId);

					queryPos.add(listTypeDefinitionId);

					List<CPSpecificationOptionListTypeDefinitionRel> list =
						query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByC_L, finderArgs, list);
						}
					}
					else {
						if (list.size() > 1) {
							Collections.sort(list, Collections.reverseOrder());

							if (_log.isWarnEnabled()) {
								if (!useFinderCache) {
									finderArgs = new Object[] {
										CPSpecificationOptionId,
										listTypeDefinitionId
									};
								}

								_log.warn(
									"CPSpecificationOptionListTypeDefinitionRelPersistenceImpl.fetchByC_L(long, long, boolean) with parameters (" +
										StringUtil.merge(finderArgs) +
											") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
							}
						}

						CPSpecificationOptionListTypeDefinitionRel
							cpSpecificationOptionListTypeDefinitionRel =
								list.get(0);

						result = cpSpecificationOptionListTypeDefinitionRel;

						cacheResult(cpSpecificationOptionListTypeDefinitionRel);
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
				return (CPSpecificationOptionListTypeDefinitionRel)result;
			}
		}
	}

	/**
	 * Removes the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the cp specification option list type definition rel that was removed
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel removeByC_L(
			long CPSpecificationOptionId, long listTypeDefinitionId)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel = findByC_L(
				CPSpecificationOptionId, listTypeDefinitionId);

		return remove(cpSpecificationOptionListTypeDefinitionRel);
	}

	/**
	 * Returns the number of cp specification option list type definition rels where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching cp specification option list type definition rels
	 */
	@Override
	public int countByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId) {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel = fetchByC_L(
				CPSpecificationOptionId, listTypeDefinitionId);

		if (cpSpecificationOptionListTypeDefinitionRel == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_L_CPSPECIFICATIONOPTIONID_2 =
		"cpSpecificationOptionListTypeDefinitionRel.CPSpecificationOptionId = ? AND ";

	private static final String _FINDER_COLUMN_C_L_LISTTYPEDEFINITIONID_2 =
		"cpSpecificationOptionListTypeDefinitionRel.listTypeDefinitionId = ?";

	public CPSpecificationOptionListTypeDefinitionRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"CPSpecificationOptionListTypeDefinitionRelId",
			"CPSOListTypeDefinitionRelId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPSpecificationOptionListTypeDefinitionRel.class);

		setModelImplClass(CPSpecificationOptionListTypeDefinitionRelImpl.class);
		setModelPKClass(long.class);

		setTable(CPSpecificationOptionListTypeDefinitionRelTable.INSTANCE);
	}

	/**
	 * Caches the cp specification option list type definition rel in the entity cache if it is enabled.
	 *
	 * @param cpSpecificationOptionListTypeDefinitionRel the cp specification option list type definition rel
	 */
	@Override
	public void cacheResult(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpSpecificationOptionListTypeDefinitionRel.
						getCtCollectionId())) {

			entityCache.putResult(
				CPSpecificationOptionListTypeDefinitionRelImpl.class,
				cpSpecificationOptionListTypeDefinitionRel.getPrimaryKey(),
				cpSpecificationOptionListTypeDefinitionRel);

			finderCache.putResult(
				_finderPathFetchByC_L,
				new Object[] {
					cpSpecificationOptionListTypeDefinitionRel.
						getCPSpecificationOptionId(),
					cpSpecificationOptionListTypeDefinitionRel.
						getListTypeDefinitionId()
				},
				cpSpecificationOptionListTypeDefinitionRel);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp specification option list type definition rels in the entity cache if it is enabled.
	 *
	 * @param cpSpecificationOptionListTypeDefinitionRels the cp specification option list type definition rels
	 */
	@Override
	public void cacheResult(
		List<CPSpecificationOptionListTypeDefinitionRel>
			cpSpecificationOptionListTypeDefinitionRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpSpecificationOptionListTypeDefinitionRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPSpecificationOptionListTypeDefinitionRel
				cpSpecificationOptionListTypeDefinitionRel :
					cpSpecificationOptionListTypeDefinitionRels) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						cpSpecificationOptionListTypeDefinitionRel.
							getCtCollectionId())) {

				if (entityCache.getResult(
						CPSpecificationOptionListTypeDefinitionRelImpl.class,
						cpSpecificationOptionListTypeDefinitionRel.
							getPrimaryKey()) == null) {

					cacheResult(cpSpecificationOptionListTypeDefinitionRel);
				}
			}
		}
	}

	/**
	 * Clears the cache for all cp specification option list type definition rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(
			CPSpecificationOptionListTypeDefinitionRelImpl.class);

		finderCache.clearCache(
			CPSpecificationOptionListTypeDefinitionRelImpl.class);
	}

	/**
	 * Clears the cache for the cp specification option list type definition rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel) {

		entityCache.removeResult(
			CPSpecificationOptionListTypeDefinitionRelImpl.class,
			cpSpecificationOptionListTypeDefinitionRel);
	}

	@Override
	public void clearCache(
		List<CPSpecificationOptionListTypeDefinitionRel>
			cpSpecificationOptionListTypeDefinitionRels) {

		for (CPSpecificationOptionListTypeDefinitionRel
				cpSpecificationOptionListTypeDefinitionRel :
					cpSpecificationOptionListTypeDefinitionRels) {

			entityCache.removeResult(
				CPSpecificationOptionListTypeDefinitionRelImpl.class,
				cpSpecificationOptionListTypeDefinitionRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(
			CPSpecificationOptionListTypeDefinitionRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPSpecificationOptionListTypeDefinitionRelImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPSpecificationOptionListTypeDefinitionRelModelImpl
			cpSpecificationOptionListTypeDefinitionRelModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpSpecificationOptionListTypeDefinitionRelModelImpl.
						getCtCollectionId())) {

			Object[] args = new Object[] {
				cpSpecificationOptionListTypeDefinitionRelModelImpl.
					getCPSpecificationOptionId(),
				cpSpecificationOptionListTypeDefinitionRelModelImpl.
					getListTypeDefinitionId()
			};

			finderCache.putResult(
				_finderPathFetchByC_L, args,
				cpSpecificationOptionListTypeDefinitionRelModelImpl);
		}
	}

	/**
	 * Creates a new cp specification option list type definition rel with the primary key. Does not add the cp specification option list type definition rel to the database.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key for the new cp specification option list type definition rel
	 * @return the new cp specification option list type definition rel
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel create(
		long CPSpecificationOptionListTypeDefinitionRelId) {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel =
				new CPSpecificationOptionListTypeDefinitionRelImpl();

		cpSpecificationOptionListTypeDefinitionRel.setNew(true);
		cpSpecificationOptionListTypeDefinitionRel.setPrimaryKey(
			CPSpecificationOptionListTypeDefinitionRelId);

		cpSpecificationOptionListTypeDefinitionRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpSpecificationOptionListTypeDefinitionRel;
	}

	/**
	 * Removes the cp specification option list type definition rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel that was removed
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel remove(
			long CPSpecificationOptionListTypeDefinitionRelId)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return remove(
			(Serializable)CPSpecificationOptionListTypeDefinitionRelId);
	}

	/**
	 * Removes the cp specification option list type definition rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel that was removed
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel remove(
			Serializable primaryKey)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		Session session = null;

		try {
			session = openSession();

			CPSpecificationOptionListTypeDefinitionRel
				cpSpecificationOptionListTypeDefinitionRel =
					(CPSpecificationOptionListTypeDefinitionRel)session.get(
						CPSpecificationOptionListTypeDefinitionRelImpl.class,
						primaryKey);

			if (cpSpecificationOptionListTypeDefinitionRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPSpecificationOptionListTypeDefinitionRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpSpecificationOptionListTypeDefinitionRel);
		}
		catch (NoSuchCPSpecificationOptionListTypeDefinitionRelException
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
	protected CPSpecificationOptionListTypeDefinitionRel removeImpl(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpSpecificationOptionListTypeDefinitionRel)) {
				cpSpecificationOptionListTypeDefinitionRel =
					(CPSpecificationOptionListTypeDefinitionRel)session.get(
						CPSpecificationOptionListTypeDefinitionRelImpl.class,
						cpSpecificationOptionListTypeDefinitionRel.
							getPrimaryKeyObj());
			}

			if ((cpSpecificationOptionListTypeDefinitionRel != null) &&
				ctPersistenceHelper.isRemove(
					cpSpecificationOptionListTypeDefinitionRel)) {

				session.delete(cpSpecificationOptionListTypeDefinitionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpSpecificationOptionListTypeDefinitionRel != null) {
			clearCache(cpSpecificationOptionListTypeDefinitionRel);
		}

		return cpSpecificationOptionListTypeDefinitionRel;
	}

	@Override
	public CPSpecificationOptionListTypeDefinitionRel updateImpl(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel) {

		boolean isNew = cpSpecificationOptionListTypeDefinitionRel.isNew();

		if (!(cpSpecificationOptionListTypeDefinitionRel instanceof
				CPSpecificationOptionListTypeDefinitionRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					cpSpecificationOptionListTypeDefinitionRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					cpSpecificationOptionListTypeDefinitionRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpSpecificationOptionListTypeDefinitionRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPSpecificationOptionListTypeDefinitionRel implementation " +
					cpSpecificationOptionListTypeDefinitionRel.getClass());
		}

		CPSpecificationOptionListTypeDefinitionRelModelImpl
			cpSpecificationOptionListTypeDefinitionRelModelImpl =
				(CPSpecificationOptionListTypeDefinitionRelModelImpl)
					cpSpecificationOptionListTypeDefinitionRel;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(
					cpSpecificationOptionListTypeDefinitionRel)) {

				if (!isNew) {
					session.evict(
						CPSpecificationOptionListTypeDefinitionRelImpl.class,
						cpSpecificationOptionListTypeDefinitionRel.
							getPrimaryKeyObj());
				}

				session.save(cpSpecificationOptionListTypeDefinitionRel);
			}
			else {
				cpSpecificationOptionListTypeDefinitionRel =
					(CPSpecificationOptionListTypeDefinitionRel)session.merge(
						cpSpecificationOptionListTypeDefinitionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPSpecificationOptionListTypeDefinitionRelImpl.class,
			cpSpecificationOptionListTypeDefinitionRelModelImpl, false, true);

		cacheUniqueFindersCache(
			cpSpecificationOptionListTypeDefinitionRelModelImpl);

		if (isNew) {
			cpSpecificationOptionListTypeDefinitionRel.setNew(false);
		}

		cpSpecificationOptionListTypeDefinitionRel.resetOriginalValues();

		return cpSpecificationOptionListTypeDefinitionRel;
	}

	/**
	 * Returns the cp specification option list type definition rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel = fetchByPrimaryKey(
				primaryKey);

		if (cpSpecificationOptionListTypeDefinitionRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPSpecificationOptionListTypeDefinitionRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpSpecificationOptionListTypeDefinitionRel;
	}

	/**
	 * Returns the cp specification option list type definition rel with the primary key or throws a <code>NoSuchCPSpecificationOptionListTypeDefinitionRelException</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel findByPrimaryKey(
			long CPSpecificationOptionListTypeDefinitionRelId)
		throws NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return findByPrimaryKey(
			(Serializable)CPSpecificationOptionListTypeDefinitionRelId);
	}

	/**
	 * Returns the cp specification option list type definition rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel, or <code>null</code> if a cp specification option list type definition rel with the primary key could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				CPSpecificationOptionListTypeDefinitionRel.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel =
				(CPSpecificationOptionListTypeDefinitionRel)
					entityCache.getResult(
						CPSpecificationOptionListTypeDefinitionRelImpl.class,
						primaryKey);

		if (cpSpecificationOptionListTypeDefinitionRel != null) {
			return cpSpecificationOptionListTypeDefinitionRel;
		}

		Session session = null;

		try {
			session = openSession();

			cpSpecificationOptionListTypeDefinitionRel =
				(CPSpecificationOptionListTypeDefinitionRel)session.get(
					CPSpecificationOptionListTypeDefinitionRelImpl.class,
					primaryKey);

			if (cpSpecificationOptionListTypeDefinitionRel != null) {
				cacheResult(cpSpecificationOptionListTypeDefinitionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return cpSpecificationOptionListTypeDefinitionRel;
	}

	/**
	 * Returns the cp specification option list type definition rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel, or <code>null</code> if a cp specification option list type definition rel with the primary key could not be found
	 */
	@Override
	public CPSpecificationOptionListTypeDefinitionRel fetchByPrimaryKey(
		long CPSpecificationOptionListTypeDefinitionRelId) {

		return fetchByPrimaryKey(
			(Serializable)CPSpecificationOptionListTypeDefinitionRelId);
	}

	@Override
	public Map<Serializable, CPSpecificationOptionListTypeDefinitionRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CPSpecificationOptionListTypeDefinitionRel.class)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPSpecificationOptionListTypeDefinitionRel> map =
			new HashMap
				<Serializable, CPSpecificationOptionListTypeDefinitionRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPSpecificationOptionListTypeDefinitionRel
				cpSpecificationOptionListTypeDefinitionRel = fetchByPrimaryKey(
					primaryKey);

			if (cpSpecificationOptionListTypeDefinitionRel != null) {
				map.put(primaryKey, cpSpecificationOptionListTypeDefinitionRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						CPSpecificationOptionListTypeDefinitionRel.class,
						primaryKey)) {

				CPSpecificationOptionListTypeDefinitionRel
					cpSpecificationOptionListTypeDefinitionRel =
						(CPSpecificationOptionListTypeDefinitionRel)
							entityCache.getResult(
								CPSpecificationOptionListTypeDefinitionRelImpl.
									class,
								primaryKey);

				if (cpSpecificationOptionListTypeDefinitionRel == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey, cpSpecificationOptionListTypeDefinitionRel);
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

			for (CPSpecificationOptionListTypeDefinitionRel
					cpSpecificationOptionListTypeDefinitionRel :
						(List<CPSpecificationOptionListTypeDefinitionRel>)
							query.list()) {

				map.put(
					cpSpecificationOptionListTypeDefinitionRel.
						getPrimaryKeyObj(),
					cpSpecificationOptionListTypeDefinitionRel);

				cacheResult(cpSpecificationOptionListTypeDefinitionRel);
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
	 * Returns all the cp specification option list type definition rels.
	 *
	 * @return the cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp specification option list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @return the range of cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel> findAll(
		int start, int end,
		OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp specification option list type definition rels
	 */
	@Override
	public List<CPSpecificationOptionListTypeDefinitionRel> findAll(
		int start, int end,
		OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPSpecificationOptionListTypeDefinitionRel.class)) {

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

			List<CPSpecificationOptionListTypeDefinitionRel> list = null;

			if (useFinderCache) {
				list =
					(List<CPSpecificationOptionListTypeDefinitionRel>)
						finderCache.getResult(finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(
						_SQL_SELECT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql =
						_SQL_SELECT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL;

					sql = sql.concat(
						CPSpecificationOptionListTypeDefinitionRelModelImpl.
							ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list =
						(List<CPSpecificationOptionListTypeDefinitionRel>)
							QueryUtil.list(query, getDialect(), start, end);

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
	 * Removes all the cp specification option list type definition rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPSpecificationOptionListTypeDefinitionRel
				cpSpecificationOptionListTypeDefinitionRel : findAll()) {

			remove(cpSpecificationOptionListTypeDefinitionRel);
		}
	}

	/**
	 * Returns the number of cp specification option list type definition rels.
	 *
	 * @return the number of cp specification option list type definition rels
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPSpecificationOptionListTypeDefinitionRel.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CPSOListTypeDefinitionRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL;
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
		return CPSpecificationOptionListTypeDefinitionRelModelImpl.
			TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPSOListTypeDefinitionRel";
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
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("CPSpecificationOptionId");
		ctMergeColumnNames.add("listTypeDefinitionId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPSOListTypeDefinitionRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the cp specification option list type definition rel persistence.
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

		_finderPathWithPaginationFindByCPSpecificationOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPSpecificationOptionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPSpecificationOptionId"}, true);

		_finderPathWithoutPaginationFindByCPSpecificationOptionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCPSpecificationOptionId",
				new String[] {Long.class.getName()},
				new String[] {"CPSpecificationOptionId"}, true);

		_finderPathCountByCPSpecificationOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPSpecificationOptionId",
			new String[] {Long.class.getName()},
			new String[] {"CPSpecificationOptionId"}, false);

		_finderPathWithPaginationFindByListTypeDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByListTypeDefinitionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"listTypeDefinitionId"}, true);

		_finderPathWithoutPaginationFindByListTypeDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByListTypeDefinitionId", new String[] {Long.class.getName()},
			new String[] {"listTypeDefinitionId"}, true);

		_finderPathCountByListTypeDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByListTypeDefinitionId", new String[] {Long.class.getName()},
			new String[] {"listTypeDefinitionId"}, false);

		_finderPathFetchByC_L = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_L",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPSpecificationOptionId", "listTypeDefinitionId"},
			true);

		CPSpecificationOptionListTypeDefinitionRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPSpecificationOptionListTypeDefinitionRelUtil.setPersistence(null);

		entityCache.removeCache(
			CPSpecificationOptionListTypeDefinitionRelImpl.class.getName());
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

	private static final String
		_SQL_SELECT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL =
			"SELECT cpSpecificationOptionListTypeDefinitionRel FROM CPSpecificationOptionListTypeDefinitionRel cpSpecificationOptionListTypeDefinitionRel";

	private static final String
		_SQL_SELECT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL_WHERE =
			"SELECT cpSpecificationOptionListTypeDefinitionRel FROM CPSpecificationOptionListTypeDefinitionRel cpSpecificationOptionListTypeDefinitionRel WHERE ";

	private static final String
		_SQL_COUNT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL =
			"SELECT COUNT(cpSpecificationOptionListTypeDefinitionRel) FROM CPSpecificationOptionListTypeDefinitionRel cpSpecificationOptionListTypeDefinitionRel";

	private static final String
		_SQL_COUNT_CPSPECIFICATIONOPTIONLISTTYPEDEFINITIONREL_WHERE =
			"SELECT COUNT(cpSpecificationOptionListTypeDefinitionRel) FROM CPSpecificationOptionListTypeDefinitionRel cpSpecificationOptionListTypeDefinitionRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpSpecificationOptionListTypeDefinitionRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPSpecificationOptionListTypeDefinitionRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPSpecificationOptionListTypeDefinitionRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPSpecificationOptionListTypeDefinitionRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"CPSpecificationOptionListTypeDefinitionRelId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}