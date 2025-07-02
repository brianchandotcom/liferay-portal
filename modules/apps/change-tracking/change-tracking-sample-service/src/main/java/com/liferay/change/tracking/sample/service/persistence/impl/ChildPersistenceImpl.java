/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence.impl;

import com.liferay.change.tracking.sample.exception.NoSuchChildException;
import com.liferay.change.tracking.sample.model.Child;
import com.liferay.change.tracking.sample.model.ChildTable;
import com.liferay.change.tracking.sample.model.impl.ChildImpl;
import com.liferay.change.tracking.sample.model.impl.ChildModelImpl;
import com.liferay.change.tracking.sample.service.persistence.ChildPersistence;
import com.liferay.change.tracking.sample.service.persistence.ChildUtil;
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
 * The persistence implementation for the child service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ChildPersistence.class)
public class ChildPersistenceImpl
	extends BasePersistenceImpl<Child> implements ChildPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ChildUtil</code> to access the child persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ChildImpl.class.getName();

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
	 * Returns all the childs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching childs
	 */
	@Override
	public List<Child> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the childs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of matching childs
	 */
	@Override
	public List<Child> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching childs
	 */
	@Override
	public List<Child> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Child> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching childs
	 */
	@Override
	public List<Child> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Child> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Child.class)) {

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

			List<Child> list = null;

			if (useFinderCache) {
				list = (List<Child>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (Child child : list) {
						if (companyId != child.getCompanyId()) {
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

				sb.append(_SQL_SELECT_CHILD_WHERE);

				sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(ChildModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					list = (List<Child>)QueryUtil.list(
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
	 * Returns the first child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	@Override
	public Child findByCompanyId_First(
			long companyId, OrderByComparator<Child> orderByComparator)
		throws NoSuchChildException {

		Child child = fetchByCompanyId_First(companyId, orderByComparator);

		if (child != null) {
			return child;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchChildException(sb.toString());
	}

	/**
	 * Returns the first child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child, or <code>null</code> if a matching child could not be found
	 */
	@Override
	public Child fetchByCompanyId_First(
		long companyId, OrderByComparator<Child> orderByComparator) {

		List<Child> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	@Override
	public Child findByCompanyId_Last(
			long companyId, OrderByComparator<Child> orderByComparator)
		throws NoSuchChildException {

		Child child = fetchByCompanyId_Last(companyId, orderByComparator);

		if (child != null) {
			return child;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchChildException(sb.toString());
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child, or <code>null</code> if a matching child could not be found
	 */
	@Override
	public Child fetchByCompanyId_Last(
		long companyId, OrderByComparator<Child> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<Child> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the childs before and after the current child in the ordered set where companyId = &#63;.
	 *
	 * @param childId the primary key of the current child
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	@Override
	public Child[] findByCompanyId_PrevAndNext(
			long childId, long companyId,
			OrderByComparator<Child> orderByComparator)
		throws NoSuchChildException {

		Child child = findByPrimaryKey(childId);

		Session session = null;

		try {
			session = openSession();

			Child[] array = new ChildImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, child, companyId, orderByComparator, true);

			array[1] = child;

			array[2] = getByCompanyId_PrevAndNext(
				session, child, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Child getByCompanyId_PrevAndNext(
		Session session, Child child, long companyId,
		OrderByComparator<Child> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CHILD_WHERE);

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
			sb.append(ChildModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(child)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Child> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the childs where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (Child child :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(child);
		}
	}

	/**
	 * Returns the number of childs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching childs
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Child.class)) {

			FinderPath finderPath = _finderPathCountByCompanyId;

			Object[] finderArgs = new Object[] {companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_CHILD_WHERE);

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
		"child.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_G;
	private FinderPath _finderPathWithoutPaginationFindByC_G;
	private FinderPath _finderPathCountByC_G;

	/**
	 * Returns all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the matching childs
	 */
	@Override
	public List<Child> findByC_G(long companyId, long grandParentId) {
		return findByC_G(
			companyId, grandParentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of matching childs
	 */
	@Override
	public List<Child> findByC_G(
		long companyId, long grandParentId, int start, int end) {

		return findByC_G(companyId, grandParentId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching childs
	 */
	@Override
	public List<Child> findByC_G(
		long companyId, long grandParentId, int start, int end,
		OrderByComparator<Child> orderByComparator) {

		return findByC_G(
			companyId, grandParentId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching childs
	 */
	@Override
	public List<Child> findByC_G(
		long companyId, long grandParentId, int start, int end,
		OrderByComparator<Child> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Child.class)) {

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

			List<Child> list = null;

			if (useFinderCache) {
				list = (List<Child>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (Child child : list) {
						if ((companyId != child.getCompanyId()) ||
							(grandParentId != child.getGrandParentId())) {

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

				sb.append(_SQL_SELECT_CHILD_WHERE);

				sb.append(_FINDER_COLUMN_C_G_COMPANYID_2);

				sb.append(_FINDER_COLUMN_C_G_GRANDPARENTID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(ChildModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(grandParentId);

					list = (List<Child>)QueryUtil.list(
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
	 * Returns the first child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	@Override
	public Child findByC_G_First(
			long companyId, long grandParentId,
			OrderByComparator<Child> orderByComparator)
		throws NoSuchChildException {

		Child child = fetchByC_G_First(
			companyId, grandParentId, orderByComparator);

		if (child != null) {
			return child;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", grandParentId=");
		sb.append(grandParentId);

		sb.append("}");

		throw new NoSuchChildException(sb.toString());
	}

	/**
	 * Returns the first child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child, or <code>null</code> if a matching child could not be found
	 */
	@Override
	public Child fetchByC_G_First(
		long companyId, long grandParentId,
		OrderByComparator<Child> orderByComparator) {

		List<Child> list = findByC_G(
			companyId, grandParentId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	@Override
	public Child findByC_G_Last(
			long companyId, long grandParentId,
			OrderByComparator<Child> orderByComparator)
		throws NoSuchChildException {

		Child child = fetchByC_G_Last(
			companyId, grandParentId, orderByComparator);

		if (child != null) {
			return child;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", grandParentId=");
		sb.append(grandParentId);

		sb.append("}");

		throw new NoSuchChildException(sb.toString());
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child, or <code>null</code> if a matching child could not be found
	 */
	@Override
	public Child fetchByC_G_Last(
		long companyId, long grandParentId,
		OrderByComparator<Child> orderByComparator) {

		int count = countByC_G(companyId, grandParentId);

		if (count == 0) {
			return null;
		}

		List<Child> list = findByC_G(
			companyId, grandParentId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the childs before and after the current child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param childId the primary key of the current child
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	@Override
	public Child[] findByC_G_PrevAndNext(
			long childId, long companyId, long grandParentId,
			OrderByComparator<Child> orderByComparator)
		throws NoSuchChildException {

		Child child = findByPrimaryKey(childId);

		Session session = null;

		try {
			session = openSession();

			Child[] array = new ChildImpl[3];

			array[0] = getByC_G_PrevAndNext(
				session, child, companyId, grandParentId, orderByComparator,
				true);

			array[1] = child;

			array[2] = getByC_G_PrevAndNext(
				session, child, companyId, grandParentId, orderByComparator,
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

	protected Child getByC_G_PrevAndNext(
		Session session, Child child, long companyId, long grandParentId,
		OrderByComparator<Child> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_CHILD_WHERE);

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
			sb.append(ChildModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(child)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Child> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the childs where companyId = &#63; and grandParentId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 */
	@Override
	public void removeByC_G(long companyId, long grandParentId) {
		for (Child child :
				findByC_G(
					companyId, grandParentId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(child);
		}
	}

	/**
	 * Returns the number of childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the number of matching childs
	 */
	@Override
	public int countByC_G(long companyId, long grandParentId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Child.class)) {

			FinderPath finderPath = _finderPathCountByC_G;

			Object[] finderArgs = new Object[] {companyId, grandParentId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_CHILD_WHERE);

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
		"child.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_G_GRANDPARENTID_2 =
		"child.grandParentId = ?";

	private FinderPath _finderPathWithPaginationFindByC_P;
	private FinderPath _finderPathWithoutPaginationFindByC_P;
	private FinderPath _finderPathCountByC_P;

	/**
	 * Returns all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @return the matching childs
	 */
	@Override
	public List<Child> findByC_P(long companyId, long parentChildId) {
		return findByC_P(
			companyId, parentChildId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of matching childs
	 */
	@Override
	public List<Child> findByC_P(
		long companyId, long parentChildId, int start, int end) {

		return findByC_P(companyId, parentChildId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching childs
	 */
	@Override
	public List<Child> findByC_P(
		long companyId, long parentChildId, int start, int end,
		OrderByComparator<Child> orderByComparator) {

		return findByC_P(
			companyId, parentChildId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching childs
	 */
	@Override
	public List<Child> findByC_P(
		long companyId, long parentChildId, int start, int end,
		OrderByComparator<Child> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Child.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByC_P;
					finderArgs = new Object[] {companyId, parentChildId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByC_P;
				finderArgs = new Object[] {
					companyId, parentChildId, start, end, orderByComparator
				};
			}

			List<Child> list = null;

			if (useFinderCache) {
				list = (List<Child>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (Child child : list) {
						if ((companyId != child.getCompanyId()) ||
							(parentChildId != child.getParentChildId())) {

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

				sb.append(_SQL_SELECT_CHILD_WHERE);

				sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

				sb.append(_FINDER_COLUMN_C_P_PARENTCHILDID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(ChildModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(parentChildId);

					list = (List<Child>)QueryUtil.list(
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
	 * Returns the first child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	@Override
	public Child findByC_P_First(
			long companyId, long parentChildId,
			OrderByComparator<Child> orderByComparator)
		throws NoSuchChildException {

		Child child = fetchByC_P_First(
			companyId, parentChildId, orderByComparator);

		if (child != null) {
			return child;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentChildId=");
		sb.append(parentChildId);

		sb.append("}");

		throw new NoSuchChildException(sb.toString());
	}

	/**
	 * Returns the first child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child, or <code>null</code> if a matching child could not be found
	 */
	@Override
	public Child fetchByC_P_First(
		long companyId, long parentChildId,
		OrderByComparator<Child> orderByComparator) {

		List<Child> list = findByC_P(
			companyId, parentChildId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	@Override
	public Child findByC_P_Last(
			long companyId, long parentChildId,
			OrderByComparator<Child> orderByComparator)
		throws NoSuchChildException {

		Child child = fetchByC_P_Last(
			companyId, parentChildId, orderByComparator);

		if (child != null) {
			return child;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentChildId=");
		sb.append(parentChildId);

		sb.append("}");

		throw new NoSuchChildException(sb.toString());
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child, or <code>null</code> if a matching child could not be found
	 */
	@Override
	public Child fetchByC_P_Last(
		long companyId, long parentChildId,
		OrderByComparator<Child> orderByComparator) {

		int count = countByC_P(companyId, parentChildId);

		if (count == 0) {
			return null;
		}

		List<Child> list = findByC_P(
			companyId, parentChildId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the childs before and after the current child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param childId the primary key of the current child
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	@Override
	public Child[] findByC_P_PrevAndNext(
			long childId, long companyId, long parentChildId,
			OrderByComparator<Child> orderByComparator)
		throws NoSuchChildException {

		Child child = findByPrimaryKey(childId);

		Session session = null;

		try {
			session = openSession();

			Child[] array = new ChildImpl[3];

			array[0] = getByC_P_PrevAndNext(
				session, child, companyId, parentChildId, orderByComparator,
				true);

			array[1] = child;

			array[2] = getByC_P_PrevAndNext(
				session, child, companyId, parentChildId, orderByComparator,
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

	protected Child getByC_P_PrevAndNext(
		Session session, Child child, long companyId, long parentChildId,
		OrderByComparator<Child> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_CHILD_WHERE);

		sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_PARENTCHILDID_2);

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
			sb.append(ChildModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(parentChildId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(child)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Child> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the childs where companyId = &#63; and parentChildId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 */
	@Override
	public void removeByC_P(long companyId, long parentChildId) {
		for (Child child :
				findByC_P(
					companyId, parentChildId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(child);
		}
	}

	/**
	 * Returns the number of childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @return the number of matching childs
	 */
	@Override
	public int countByC_P(long companyId, long parentChildId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Child.class)) {

			FinderPath finderPath = _finderPathCountByC_P;

			Object[] finderArgs = new Object[] {companyId, parentChildId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_CHILD_WHERE);

				sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

				sb.append(_FINDER_COLUMN_C_P_PARENTCHILDID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(parentChildId);

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

	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 =
		"child.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_PARENTCHILDID_2 =
		"child.parentChildId = ?";

	public ChildPersistenceImpl() {
		setModelClass(Child.class);

		setModelImplClass(ChildImpl.class);
		setModelPKClass(long.class);

		setTable(ChildTable.INSTANCE);
	}

	/**
	 * Caches the child in the entity cache if it is enabled.
	 *
	 * @param child the child
	 */
	@Override
	public void cacheResult(Child child) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					child.getCtCollectionId())) {

			entityCache.putResult(
				ChildImpl.class, child.getPrimaryKey(), child);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the childs in the entity cache if it is enabled.
	 *
	 * @param childs the childs
	 */
	@Override
	public void cacheResult(List<Child> childs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (childs.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Child child : childs) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						child.getCtCollectionId())) {

				if (entityCache.getResult(
						ChildImpl.class, child.getPrimaryKey()) == null) {

					cacheResult(child);
				}
			}
		}
	}

	/**
	 * Clears the cache for all childs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ChildImpl.class);

		finderCache.clearCache(ChildImpl.class);
	}

	/**
	 * Clears the cache for the child.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Child child) {
		entityCache.removeResult(ChildImpl.class, child);
	}

	@Override
	public void clearCache(List<Child> childs) {
		for (Child child : childs) {
			entityCache.removeResult(ChildImpl.class, child);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ChildImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ChildImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new child with the primary key. Does not add the child to the database.
	 *
	 * @param childId the primary key for the new child
	 * @return the new child
	 */
	@Override
	public Child create(long childId) {
		Child child = new ChildImpl();

		child.setNew(true);
		child.setPrimaryKey(childId);

		child.setCompanyId(CompanyThreadLocal.getCompanyId());

		return child;
	}

	/**
	 * Removes the child with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param childId the primary key of the child
	 * @return the child that was removed
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	@Override
	public Child remove(long childId) throws NoSuchChildException {
		return remove((Serializable)childId);
	}

	/**
	 * Removes the child with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the child
	 * @return the child that was removed
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	@Override
	public Child remove(Serializable primaryKey) throws NoSuchChildException {
		Session session = null;

		try {
			session = openSession();

			Child child = (Child)session.get(ChildImpl.class, primaryKey);

			if (child == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchChildException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(child);
		}
		catch (NoSuchChildException noSuchEntityException) {
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
	protected Child removeImpl(Child child) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(child)) {
				child = (Child)session.get(
					ChildImpl.class, child.getPrimaryKeyObj());
			}

			if ((child != null) && ctPersistenceHelper.isRemove(child)) {
				session.delete(child);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (child != null) {
			clearCache(child);
		}

		return child;
	}

	@Override
	public Child updateImpl(Child child) {
		boolean isNew = child.isNew();

		if (!(child instanceof ChildModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(child.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(child);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in child proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Child implementation " +
					child.getClass());
		}

		ChildModelImpl childModelImpl = (ChildModelImpl)child;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(child)) {
				if (!isNew) {
					session.evict(ChildImpl.class, child.getPrimaryKeyObj());
				}

				session.save(child);
			}
			else {
				child = (Child)session.merge(child);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(ChildImpl.class, childModelImpl, false, true);

		if (isNew) {
			child.setNew(false);
		}

		child.resetOriginalValues();

		return child;
	}

	/**
	 * Returns the child with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the child
	 * @return the child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	@Override
	public Child findByPrimaryKey(Serializable primaryKey)
		throws NoSuchChildException {

		Child child = fetchByPrimaryKey(primaryKey);

		if (child == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchChildException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return child;
	}

	/**
	 * Returns the child with the primary key or throws a <code>NoSuchChildException</code> if it could not be found.
	 *
	 * @param childId the primary key of the child
	 * @return the child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	@Override
	public Child findByPrimaryKey(long childId) throws NoSuchChildException {
		return findByPrimaryKey((Serializable)childId);
	}

	/**
	 * Returns the child with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the child
	 * @return the child, or <code>null</code> if a child with the primary key could not be found
	 */
	@Override
	public Child fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(Child.class, primaryKey)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		Child child = (Child)entityCache.getResult(ChildImpl.class, primaryKey);

		if (child != null) {
			return child;
		}

		Session session = null;

		try {
			session = openSession();

			child = (Child)session.get(ChildImpl.class, primaryKey);

			if (child != null) {
				cacheResult(child);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return child;
	}

	/**
	 * Returns the child with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param childId the primary key of the child
	 * @return the child, or <code>null</code> if a child with the primary key could not be found
	 */
	@Override
	public Child fetchByPrimaryKey(long childId) {
		return fetchByPrimaryKey((Serializable)childId);
	}

	@Override
	public Map<Serializable, Child> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(Child.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Child> map = new HashMap<Serializable, Child>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Child child = fetchByPrimaryKey(primaryKey);

			if (child != null) {
				map.put(primaryKey, child);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						Child.class, primaryKey)) {

				Child child = (Child)entityCache.getResult(
					ChildImpl.class, primaryKey);

				if (child == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, child);
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

			for (Child child : (List<Child>)query.list()) {
				map.put(child.getPrimaryKeyObj(), child);

				cacheResult(child);
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
	 * Returns all the childs.
	 *
	 * @return the childs
	 */
	@Override
	public List<Child> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the childs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of childs
	 */
	@Override
	public List<Child> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the childs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of childs
	 */
	@Override
	public List<Child> findAll(
		int start, int end, OrderByComparator<Child> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the childs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of childs
	 */
	@Override
	public List<Child> findAll(
		int start, int end, OrderByComparator<Child> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Child.class)) {

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

			List<Child> list = null;

			if (useFinderCache) {
				list = (List<Child>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_CHILD);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_CHILD;

					sql = sql.concat(ChildModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<Child>)QueryUtil.list(
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
	 * Removes all the childs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Child child : findAll()) {
			remove(child);
		}
	}

	/**
	 * Returns the number of childs.
	 *
	 * @return the number of childs
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					Child.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_CHILD);

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
		return "childId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CHILD;
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
		return ChildModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "Child";
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
		ctMergeColumnNames.add("parentChildId");
		ctMergeColumnNames.add("parentName");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("childId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the child persistence.
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

		_finderPathWithPaginationFindByC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "parentChildId"}, true);

		_finderPathWithoutPaginationFindByC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "parentChildId"}, true);

		_finderPathCountByC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "parentChildId"}, false);

		ChildUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ChildUtil.setPersistence(null);

		entityCache.removeCache(ChildImpl.class.getName());
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

	private static final String _SQL_SELECT_CHILD =
		"SELECT child FROM Child child";

	private static final String _SQL_SELECT_CHILD_WHERE =
		"SELECT child FROM Child child WHERE ";

	private static final String _SQL_COUNT_CHILD =
		"SELECT COUNT(child) FROM Child child";

	private static final String _SQL_COUNT_CHILD_WHERE =
		"SELECT COUNT(child) FROM Child child WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "child.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Child exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Child exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ChildPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}