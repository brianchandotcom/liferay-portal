/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence.impl;

import com.liferay.change.tracking.sample.exception.NoSuchParentException;
import com.liferay.change.tracking.sample.model.Parent;
import com.liferay.change.tracking.sample.model.ParentTable;
import com.liferay.change.tracking.sample.model.impl.ParentImpl;
import com.liferay.change.tracking.sample.model.impl.ParentModelImpl;
import com.liferay.change.tracking.sample.service.persistence.ParentPersistence;
import com.liferay.change.tracking.sample.service.persistence.ParentUtil;
import com.liferay.change.tracking.sample.service.persistence.impl.constants.CTSPersistenceConstants;
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
 * The persistence implementation for the parent service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ParentPersistence.class)
public class ParentPersistenceImpl
	extends BasePersistenceImpl<Parent> implements ParentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ParentUtil</code> to access the parent persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ParentImpl.class.getName();

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
	 * Returns all the parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching parents
	 */
	@Override
	public List<Parent> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @return the range of matching parents
	 */
	@Override
	public List<Parent> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching parents
	 */
	@Override
	public List<Parent> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Parent> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching parents
	 */
	@Override
	public List<Parent> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Parent> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Parent.class)) {

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

			List<Parent> list = null;

			if (useFinderCache) {
				list = (List<Parent>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (Parent parent : list) {
						if (companyId != parent.getCompanyId()) {
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

				sb.append(_SQL_SELECT_PARENT_WHERE);

				sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(ParentModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					list = (List<Parent>)QueryUtil.list(
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
	 * Returns the first parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	@Override
	public Parent findByCompanyId_First(
			long companyId, OrderByComparator<Parent> orderByComparator)
		throws NoSuchParentException {

		Parent parent = fetchByCompanyId_First(companyId, orderByComparator);

		if (parent != null) {
			return parent;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchParentException(sb.toString());
	}

	/**
	 * Returns the first parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent, or <code>null</code> if a matching parent could not be found
	 */
	@Override
	public Parent fetchByCompanyId_First(
		long companyId, OrderByComparator<Parent> orderByComparator) {

		List<Parent> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	@Override
	public Parent findByCompanyId_Last(
			long companyId, OrderByComparator<Parent> orderByComparator)
		throws NoSuchParentException {

		Parent parent = fetchByCompanyId_Last(companyId, orderByComparator);

		if (parent != null) {
			return parent;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchParentException(sb.toString());
	}

	/**
	 * Returns the last parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent, or <code>null</code> if a matching parent could not be found
	 */
	@Override
	public Parent fetchByCompanyId_Last(
		long companyId, OrderByComparator<Parent> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<Parent> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the parents before and after the current parent in the ordered set where companyId = &#63;.
	 *
	 * @param parentId the primary key of the current parent
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next parent
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	@Override
	public Parent[] findByCompanyId_PrevAndNext(
			long parentId, long companyId,
			OrderByComparator<Parent> orderByComparator)
		throws NoSuchParentException {

		Parent parent = findByPrimaryKey(parentId);

		Session session = null;

		try {
			session = openSession();

			Parent[] array = new ParentImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, parent, companyId, orderByComparator, true);

			array[1] = parent;

			array[2] = getByCompanyId_PrevAndNext(
				session, parent, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Parent getByCompanyId_PrevAndNext(
		Session session, Parent parent, long companyId,
		OrderByComparator<Parent> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_PARENT_WHERE);

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
			sb.append(ParentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(parent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Parent> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the parents where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (Parent parent :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(parent);
		}
	}

	/**
	 * Returns the number of parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching parents
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Parent.class)) {

			FinderPath finderPath = _finderPathCountByCompanyId;

			Object[] finderArgs = new Object[] {companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_PARENT_WHERE);

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
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"parent.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_G;
	private FinderPath _finderPathWithoutPaginationFindByC_G;
	private FinderPath _finderPathCountByC_G;

	/**
	 * Returns all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the matching parents
	 */
	@Override
	public List<Parent> findByC_G(long companyId, long grandParentId) {
		return findByC_G(
			companyId, grandParentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @return the range of matching parents
	 */
	@Override
	public List<Parent> findByC_G(
		long companyId, long grandParentId, int start, int end) {

		return findByC_G(companyId, grandParentId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching parents
	 */
	@Override
	public List<Parent> findByC_G(
		long companyId, long grandParentId, int start, int end,
		OrderByComparator<Parent> orderByComparator) {

		return findByC_G(
			companyId, grandParentId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching parents
	 */
	@Override
	public List<Parent> findByC_G(
		long companyId, long grandParentId, int start, int end,
		OrderByComparator<Parent> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Parent.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByC_G;
					finderArgs = new Object[] {companyId, grandParentId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByC_G;
				finderArgs = new Object[] {
					companyId, grandParentId, start, end, orderByComparator
				};
			}

			List<Parent> list = null;

			if (useFinderCache) {
				list = (List<Parent>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (Parent parent : list) {
						if ((companyId != parent.getCompanyId()) ||
							(grandParentId != parent.getGrandParentId())) {

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

				sb.append(_SQL_SELECT_PARENT_WHERE);

				sb.append(_FINDER_COLUMN_C_G_COMPANYID_2);

				sb.append(_FINDER_COLUMN_C_G_GRANDPARENTID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(ParentModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(grandParentId);

					list = (List<Parent>)QueryUtil.list(
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
	 * Returns the first parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	@Override
	public Parent findByC_G_First(
			long companyId, long grandParentId,
			OrderByComparator<Parent> orderByComparator)
		throws NoSuchParentException {

		Parent parent = fetchByC_G_First(
			companyId, grandParentId, orderByComparator);

		if (parent != null) {
			return parent;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", grandParentId=");
		sb.append(grandParentId);

		sb.append("}");

		throw new NoSuchParentException(sb.toString());
	}

	/**
	 * Returns the first parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent, or <code>null</code> if a matching parent could not be found
	 */
	@Override
	public Parent fetchByC_G_First(
		long companyId, long grandParentId,
		OrderByComparator<Parent> orderByComparator) {

		List<Parent> list = findByC_G(
			companyId, grandParentId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	@Override
	public Parent findByC_G_Last(
			long companyId, long grandParentId,
			OrderByComparator<Parent> orderByComparator)
		throws NoSuchParentException {

		Parent parent = fetchByC_G_Last(
			companyId, grandParentId, orderByComparator);

		if (parent != null) {
			return parent;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", grandParentId=");
		sb.append(grandParentId);

		sb.append("}");

		throw new NoSuchParentException(sb.toString());
	}

	/**
	 * Returns the last parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent, or <code>null</code> if a matching parent could not be found
	 */
	@Override
	public Parent fetchByC_G_Last(
		long companyId, long grandParentId,
		OrderByComparator<Parent> orderByComparator) {

		int count = countByC_G(companyId, grandParentId);

		if (count == 0) {
			return null;
		}

		List<Parent> list = findByC_G(
			companyId, grandParentId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the parents before and after the current parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param parentId the primary key of the current parent
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next parent
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	@Override
	public Parent[] findByC_G_PrevAndNext(
			long parentId, long companyId, long grandParentId,
			OrderByComparator<Parent> orderByComparator)
		throws NoSuchParentException {

		Parent parent = findByPrimaryKey(parentId);

		Session session = null;

		try {
			session = openSession();

			Parent[] array = new ParentImpl[3];

			array[0] = getByC_G_PrevAndNext(
				session, parent, companyId, grandParentId, orderByComparator,
				true);

			array[1] = parent;

			array[2] = getByC_G_PrevAndNext(
				session, parent, companyId, grandParentId, orderByComparator,
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

	protected Parent getByC_G_PrevAndNext(
		Session session, Parent parent, long companyId, long grandParentId,
		OrderByComparator<Parent> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_PARENT_WHERE);

		sb.append(_FINDER_COLUMN_C_G_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_G_GRANDPARENTID_2);

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
			sb.append(ParentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(grandParentId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(parent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Parent> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the parents where companyId = &#63; and grandParentId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 */
	@Override
	public void removeByC_G(long companyId, long grandParentId) {
		for (Parent parent :
				findByC_G(
					companyId, grandParentId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(parent);
		}
	}

	/**
	 * Returns the number of parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the number of matching parents
	 */
	@Override
	public int countByC_G(long companyId, long grandParentId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Parent.class)) {

			FinderPath finderPath = _finderPathCountByC_G;

			Object[] finderArgs = new Object[] {companyId, grandParentId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_PARENT_WHERE);

				sb.append(_FINDER_COLUMN_C_G_COMPANYID_2);

				sb.append(_FINDER_COLUMN_C_G_GRANDPARENTID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(grandParentId);

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

	private static final String _FINDER_COLUMN_C_G_COMPANYID_2 =
		"parent.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_G_GRANDPARENTID_2 =
		"parent.grandParentId = ?";

	public ParentPersistenceImpl() {
		setModelClass(Parent.class);

		setModelImplClass(ParentImpl.class);
		setModelPKClass(long.class);

		setTable(ParentTable.INSTANCE);
	}

	/**
	 * Caches the parent in the entity cache if it is enabled.
	 *
	 * @param parent the parent
	 */
	@Override
	public void cacheResult(Parent parent) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					parent.getCtCollectionId())) {

			entityCache.putResult(
				ParentImpl.class, parent.getPrimaryKey(), parent);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the parents in the entity cache if it is enabled.
	 *
	 * @param parents the parents
	 */
	@Override
	public void cacheResult(List<Parent> parents) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (parents.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Parent parent : parents) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						parent.getCtCollectionId())) {

				if (entityCache.getResult(
						ParentImpl.class, parent.getPrimaryKey()) == null) {

					cacheResult(parent);
				}
			}
		}
	}

	/**
	 * Clears the cache for all parents.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ParentImpl.class);

		finderCache.clearCache(ParentImpl.class);
	}

	/**
	 * Clears the cache for the parent.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Parent parent) {
		entityCache.removeResult(ParentImpl.class, parent);
	}

	@Override
	public void clearCache(List<Parent> parents) {
		for (Parent parent : parents) {
			entityCache.removeResult(ParentImpl.class, parent);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ParentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ParentImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new parent with the primary key. Does not add the parent to the database.
	 *
	 * @param parentId the primary key for the new parent
	 * @return the new parent
	 */
	@Override
	public Parent create(long parentId) {
		Parent parent = new ParentImpl();

		parent.setNew(true);
		parent.setPrimaryKey(parentId);

		parent.setCompanyId(CompanyThreadLocal.getCompanyId());

		return parent;
	}

	/**
	 * Removes the parent with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent that was removed
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	@Override
	public Parent remove(long parentId) throws NoSuchParentException {
		return remove((Serializable)parentId);
	}

	/**
	 * Removes the parent with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the parent
	 * @return the parent that was removed
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	@Override
	public Parent remove(Serializable primaryKey) throws NoSuchParentException {
		Session session = null;

		try {
			session = openSession();

			Parent parent = (Parent)session.get(ParentImpl.class, primaryKey);

			if (parent == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchParentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(parent);
		}
		catch (NoSuchParentException noSuchEntityException) {
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
	protected Parent removeImpl(Parent parent) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(parent)) {
				parent = (Parent)session.get(
					ParentImpl.class, parent.getPrimaryKeyObj());
			}

			if ((parent != null) && ctPersistenceHelper.isRemove(parent)) {
				session.delete(parent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (parent != null) {
			clearCache(parent);
		}

		return parent;
	}

	@Override
	public Parent updateImpl(Parent parent) {
		boolean isNew = parent.isNew();

		if (!(parent instanceof ParentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(parent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(parent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in parent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Parent implementation " +
					parent.getClass());
		}

		ParentModelImpl parentModelImpl = (ParentModelImpl)parent;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(parent)) {
				if (!isNew) {
					session.evict(ParentImpl.class, parent.getPrimaryKeyObj());
				}

				session.save(parent);
			}
			else {
				parent = (Parent)session.merge(parent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(ParentImpl.class, parentModelImpl, false, true);

		if (isNew) {
			parent.setNew(false);
		}

		parent.resetOriginalValues();

		return parent;
	}

	/**
	 * Returns the parent with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the parent
	 * @return the parent
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	@Override
	public Parent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchParentException {

		Parent parent = fetchByPrimaryKey(primaryKey);

		if (parent == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchParentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return parent;
	}

	/**
	 * Returns the parent with the primary key or throws a <code>NoSuchParentException</code> if it could not be found.
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	@Override
	public Parent findByPrimaryKey(long parentId) throws NoSuchParentException {
		return findByPrimaryKey((Serializable)parentId);
	}

	/**
	 * Returns the parent with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the parent
	 * @return the parent, or <code>null</code> if a parent with the primary key could not be found
	 */
	@Override
	public Parent fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(Parent.class, primaryKey)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		Parent parent = (Parent)entityCache.getResult(
			ParentImpl.class, primaryKey);

		if (parent != null) {
			return parent;
		}

		Session session = null;

		try {
			session = openSession();

			parent = (Parent)session.get(ParentImpl.class, primaryKey);

			if (parent != null) {
				cacheResult(parent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return parent;
	}

	/**
	 * Returns the parent with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent, or <code>null</code> if a parent with the primary key could not be found
	 */
	@Override
	public Parent fetchByPrimaryKey(long parentId) {
		return fetchByPrimaryKey((Serializable)parentId);
	}

	@Override
	public Map<Serializable, Parent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(Parent.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Parent> map = new HashMap<Serializable, Parent>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Parent parent = fetchByPrimaryKey(primaryKey);

			if (parent != null) {
				map.put(primaryKey, parent);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						Parent.class, primaryKey)) {

				Parent parent = (Parent)entityCache.getResult(
					ParentImpl.class, primaryKey);

				if (parent == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, parent);
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

			for (Parent parent : (List<Parent>)query.list()) {
				map.put(parent.getPrimaryKeyObj(), parent);

				cacheResult(parent);
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
	 * Returns all the parents.
	 *
	 * @return the parents
	 */
	@Override
	public List<Parent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @return the range of parents
	 */
	@Override
	public List<Parent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of parents
	 */
	@Override
	public List<Parent> findAll(
		int start, int end, OrderByComparator<Parent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of parents
	 */
	@Override
	public List<Parent> findAll(
		int start, int end, OrderByComparator<Parent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Parent.class)) {

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

			List<Parent> list = null;

			if (useFinderCache) {
				list = (List<Parent>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_PARENT);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_PARENT;

					sql = sql.concat(ParentModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<Parent>)QueryUtil.list(
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
	 * Removes all the parents from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Parent parent : findAll()) {
			remove(parent);
		}
	}

	/**
	 * Returns the number of parents.
	 *
	 * @return the number of parents
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Parent.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_PARENT);

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
		return "parentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PARENT;
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
		return ParentModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "Parent";
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
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("grandParentId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("parentId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the parent persistence.
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

		_finderPathWithPaginationFindByC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "grandParentId"}, true);

		_finderPathWithoutPaginationFindByC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_G",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "grandParentId"}, true);

		_finderPathCountByC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "grandParentId"}, false);

		ParentUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ParentUtil.setPersistence(null);

		entityCache.removeCache(ParentImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_PARENT =
		"SELECT parent FROM Parent parent";

	private static final String _SQL_SELECT_PARENT_WHERE =
		"SELECT parent FROM Parent parent WHERE ";

	private static final String _SQL_COUNT_PARENT =
		"SELECT COUNT(parent) FROM Parent parent";

	private static final String _SQL_COUNT_PARENT_WHERE =
		"SELECT COUNT(parent) FROM Parent parent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "parent.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Parent exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Parent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ParentPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}