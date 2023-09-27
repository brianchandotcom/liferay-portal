/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.persistence.impl;

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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.workflow.kaleo.exception.NoSuchNodeException;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNodePersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNodeUtil;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

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
 * The persistence implementation for the kaleo node service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = KaleoNodePersistence.class)
public class KaleoNodePersistenceImpl
	extends BasePersistenceImpl<KaleoNode> implements KaleoNodePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoNodeUtil</code> to access the kaleo node persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoNodeImpl.class.getName();

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
	 * Returns all the kaleo nodes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo nodes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNode.class);

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

		List<KaleoNode> list = null;

		if (useFinderCache && productionMode) {
			list = (List<KaleoNode>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoNode kaleoNode : list) {
					if (companyId != kaleoNode.getCompanyId()) {
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

			sb.append(_SQL_SELECT_KALEONODE_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<KaleoNode>)QueryUtil.list(
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
	 * Returns the first kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByCompanyId_First(
			long companyId, OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {

		KaleoNode kaleoNode = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNodeException(sb.toString());
	}

	/**
	 * Returns the first kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByCompanyId_First(
		long companyId, OrderByComparator<KaleoNode> orderByComparator) {

		List<KaleoNode> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByCompanyId_Last(
			long companyId, OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {

		KaleoNode kaleoNode = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNodeException(sb.toString());
	}

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByCompanyId_Last(
		long companyId, OrderByComparator<KaleoNode> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoNode> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo nodes before and after the current kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoNodeId the primary key of the current kaleo node
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode[] findByCompanyId_PrevAndNext(
			long kaleoNodeId, long companyId,
			OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {

		KaleoNode kaleoNode = findByPrimaryKey(kaleoNodeId);

		Session session = null;

		try {
			session = openSession();

			KaleoNode[] array = new KaleoNodeImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoNode, companyId, orderByComparator, true);

			array[1] = kaleoNode;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoNode, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoNode getByCompanyId_PrevAndNext(
		Session session, KaleoNode kaleoNode, long companyId,
		OrderByComparator<KaleoNode> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEONODE_WHERE);

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
			sb.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kaleoNode)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoNode> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo nodes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoNode kaleoNode :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoNode);
		}
	}

	/**
	 * Returns the number of kaleo nodes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo nodes
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNode.class);

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

			sb.append(_SQL_COUNT_KALEONODE_WHERE);

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
		"kaleoNode.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoDefinitionVersionId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
	private FinderPath _finderPathCountByKaleoDefinitionVersionId;

	/**
	 * Returns all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNode.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
				finderArgs = new Object[] {kaleoDefinitionVersionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath =
				_finderPathWithPaginationFindByKaleoDefinitionVersionId;
			finderArgs = new Object[] {
				kaleoDefinitionVersionId, start, end, orderByComparator
			};
		}

		List<KaleoNode> list = null;

		if (useFinderCache && productionMode) {
			list = (List<KaleoNode>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoNode kaleoNode : list) {
					if (kaleoDefinitionVersionId !=
							kaleoNode.getKaleoDefinitionVersionId()) {

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

			sb.append(_SQL_SELECT_KALEONODE_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoNode>)QueryUtil.list(
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
	 * Returns the first kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {

		KaleoNode kaleoNode = fetchByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchNodeException(sb.toString());
	}

	/**
	 * Returns the first kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator) {

		List<KaleoNode> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {

		KaleoNode kaleoNode = fetchByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchNodeException(sb.toString());
	}

	/**
	 * Returns the last kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator) {

		int count = countByKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoNode> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo nodes before and after the current kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoNodeId the primary key of the current kaleo node
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoNodeId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {

		KaleoNode kaleoNode = findByPrimaryKey(kaleoNodeId);

		Session session = null;

		try {
			session = openSession();

			KaleoNode[] array = new KaleoNodeImpl[3];

			array[0] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoNode, kaleoDefinitionVersionId, orderByComparator,
				true);

			array[1] = kaleoNode;

			array[2] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoNode, kaleoDefinitionVersionId, orderByComparator,
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

	protected KaleoNode getByKaleoDefinitionVersionId_PrevAndNext(
		Session session, KaleoNode kaleoNode, long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEONODE_WHERE);

		sb.append(
			_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

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
			sb.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoDefinitionVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kaleoNode)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoNode> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo nodes where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		for (KaleoNode kaleoNode :
				findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoNode);
		}
	}

	/**
	 * Returns the number of kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo nodes
	 */
	@Override
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNode.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByKaleoDefinitionVersionId;

			finderArgs = new Object[] {kaleoDefinitionVersionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEONODE_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

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

	private static final String
		_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2 =
			"kaleoNode.kaleoDefinitionVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByC_KDVI;
	private FinderPath _finderPathWithoutPaginationFindByC_KDVI;
	private FinderPath _finderPathCountByC_KDVI;

	/**
	 * Returns all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByC_KDVI(
		long companyId, long kaleoDefinitionVersionId) {

		return findByC_KDVI(
			companyId, kaleoDefinitionVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByC_KDVI(
		long companyId, long kaleoDefinitionVersionId, int start, int end) {

		return findByC_KDVI(
			companyId, kaleoDefinitionVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByC_KDVI(
		long companyId, long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator) {

		return findByC_KDVI(
			companyId, kaleoDefinitionVersionId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByC_KDVI(
		long companyId, long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNode.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_KDVI;
				finderArgs = new Object[] {companyId, kaleoDefinitionVersionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_KDVI;
			finderArgs = new Object[] {
				companyId, kaleoDefinitionVersionId, start, end,
				orderByComparator
			};
		}

		List<KaleoNode> list = null;

		if (useFinderCache && productionMode) {
			list = (List<KaleoNode>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoNode kaleoNode : list) {
					if ((companyId != kaleoNode.getCompanyId()) ||
						(kaleoDefinitionVersionId !=
							kaleoNode.getKaleoDefinitionVersionId())) {

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

			sb.append(_SQL_SELECT_KALEONODE_WHERE);

			sb.append(_FINDER_COLUMN_C_KDVI_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_KDVI_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoNode>)QueryUtil.list(
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
	 * Returns the first kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByC_KDVI_First(
			long companyId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {

		KaleoNode kaleoNode = fetchByC_KDVI_First(
			companyId, kaleoDefinitionVersionId, orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchNodeException(sb.toString());
	}

	/**
	 * Returns the first kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByC_KDVI_First(
		long companyId, long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator) {

		List<KaleoNode> list = findByC_KDVI(
			companyId, kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByC_KDVI_Last(
			long companyId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {

		KaleoNode kaleoNode = fetchByC_KDVI_Last(
			companyId, kaleoDefinitionVersionId, orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchNodeException(sb.toString());
	}

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByC_KDVI_Last(
		long companyId, long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator) {

		int count = countByC_KDVI(companyId, kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoNode> list = findByC_KDVI(
			companyId, kaleoDefinitionVersionId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo nodes before and after the current kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoNodeId the primary key of the current kaleo node
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode[] findByC_KDVI_PrevAndNext(
			long kaleoNodeId, long companyId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {

		KaleoNode kaleoNode = findByPrimaryKey(kaleoNodeId);

		Session session = null;

		try {
			session = openSession();

			KaleoNode[] array = new KaleoNodeImpl[3];

			array[0] = getByC_KDVI_PrevAndNext(
				session, kaleoNode, companyId, kaleoDefinitionVersionId,
				orderByComparator, true);

			array[1] = kaleoNode;

			array[2] = getByC_KDVI_PrevAndNext(
				session, kaleoNode, companyId, kaleoDefinitionVersionId,
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

	protected KaleoNode getByC_KDVI_PrevAndNext(
		Session session, KaleoNode kaleoNode, long companyId,
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_KALEONODE_WHERE);

		sb.append(_FINDER_COLUMN_C_KDVI_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_KDVI_KALEODEFINITIONVERSIONID_2);

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
			sb.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(kaleoDefinitionVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kaleoNode)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoNode> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByC_KDVI(long companyId, long kaleoDefinitionVersionId) {
		for (KaleoNode kaleoNode :
				findByC_KDVI(
					companyId, kaleoDefinitionVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoNode);
		}
	}

	/**
	 * Returns the number of kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo nodes
	 */
	@Override
	public int countByC_KDVI(long companyId, long kaleoDefinitionVersionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNode.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_KDVI;

			finderArgs = new Object[] {companyId, kaleoDefinitionVersionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEONODE_WHERE);

			sb.append(_FINDER_COLUMN_C_KDVI_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_KDVI_KALEODEFINITIONVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(kaleoDefinitionVersionId);

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

	private static final String _FINDER_COLUMN_C_KDVI_COMPANYID_2 =
		"kaleoNode.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_KDVI_KALEODEFINITIONVERSIONID_2 =
			"kaleoNode.kaleoDefinitionVersionId = ?";

	public KaleoNodePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");
		dbColumnNames.put("initial", "initial_");

		setDBColumnNames(dbColumnNames);

		setModelClass(KaleoNode.class);

		setModelImplClass(KaleoNodeImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoNodeTable.INSTANCE);
	}

	/**
	 * Caches the kaleo node in the entity cache if it is enabled.
	 *
	 * @param kaleoNode the kaleo node
	 */
	@Override
	public void cacheResult(KaleoNode kaleoNode) {
		if (kaleoNode.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			KaleoNodeImpl.class, kaleoNode.getPrimaryKey(), kaleoNode);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo nodes in the entity cache if it is enabled.
	 *
	 * @param kaleoNodes the kaleo nodes
	 */
	@Override
	public void cacheResult(List<KaleoNode> kaleoNodes) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoNodes.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoNode kaleoNode : kaleoNodes) {
			if (kaleoNode.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					KaleoNodeImpl.class, kaleoNode.getPrimaryKey()) == null) {

				cacheResult(kaleoNode);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo nodes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoNodeImpl.class);

		finderCache.clearCache(KaleoNodeImpl.class);
	}

	/**
	 * Clears the cache for the kaleo node.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoNode kaleoNode) {
		entityCache.removeResult(KaleoNodeImpl.class, kaleoNode);
	}

	@Override
	public void clearCache(List<KaleoNode> kaleoNodes) {
		for (KaleoNode kaleoNode : kaleoNodes) {
			entityCache.removeResult(KaleoNodeImpl.class, kaleoNode);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoNodeImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(KaleoNodeImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new kaleo node with the primary key. Does not add the kaleo node to the database.
	 *
	 * @param kaleoNodeId the primary key for the new kaleo node
	 * @return the new kaleo node
	 */
	@Override
	public KaleoNode create(long kaleoNodeId) {
		KaleoNode kaleoNode = new KaleoNodeImpl();

		kaleoNode.setNew(true);
		kaleoNode.setPrimaryKey(kaleoNodeId);

		kaleoNode.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoNode;
	}

	/**
	 * Removes the kaleo node with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node that was removed
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode remove(long kaleoNodeId) throws NoSuchNodeException {
		return remove((Serializable)kaleoNodeId);
	}

	/**
	 * Removes the kaleo node with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo node
	 * @return the kaleo node that was removed
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode remove(Serializable primaryKey)
		throws NoSuchNodeException {

		Session session = null;

		try {
			session = openSession();

			KaleoNode kaleoNode = (KaleoNode)session.get(
				KaleoNodeImpl.class, primaryKey);

			if (kaleoNode == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNodeException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoNode);
		}
		catch (NoSuchNodeException noSuchEntityException) {
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
	protected KaleoNode removeImpl(KaleoNode kaleoNode) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoNode)) {
				kaleoNode = (KaleoNode)session.get(
					KaleoNodeImpl.class, kaleoNode.getPrimaryKeyObj());
			}

			if ((kaleoNode != null) &&
				ctPersistenceHelper.isRemove(kaleoNode)) {

				session.delete(kaleoNode);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoNode != null) {
			clearCache(kaleoNode);
		}

		return kaleoNode;
	}

	@Override
	public KaleoNode updateImpl(KaleoNode kaleoNode) {
		boolean isNew = kaleoNode.isNew();

		if (!(kaleoNode instanceof KaleoNodeModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoNode.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(kaleoNode);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoNode proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoNode implementation " +
					kaleoNode.getClass());
		}

		KaleoNodeModelImpl kaleoNodeModelImpl = (KaleoNodeModelImpl)kaleoNode;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoNode.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoNode.setCreateDate(date);
			}
			else {
				kaleoNode.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoNodeModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoNode.setModifiedDate(date);
			}
			else {
				kaleoNode.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(kaleoNode)) {
				if (!isNew) {
					session.evict(
						KaleoNodeImpl.class, kaleoNode.getPrimaryKeyObj());
				}

				session.save(kaleoNode);
			}
			else {
				kaleoNode = (KaleoNode)session.merge(kaleoNode);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoNode.getCtCollectionId() != 0) {
			if (isNew) {
				kaleoNode.setNew(false);
			}

			kaleoNode.resetOriginalValues();

			return kaleoNode;
		}

		entityCache.putResult(
			KaleoNodeImpl.class, kaleoNodeModelImpl, false, true);

		if (isNew) {
			kaleoNode.setNew(false);
		}

		kaleoNode.resetOriginalValues();

		return kaleoNode;
	}

	/**
	 * Returns the kaleo node with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo node
	 * @return the kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode findByPrimaryKey(Serializable primaryKey)
		throws NoSuchNodeException {

		KaleoNode kaleoNode = fetchByPrimaryKey(primaryKey);

		if (kaleoNode == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNodeException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoNode;
	}

	/**
	 * Returns the kaleo node with the primary key or throws a <code>NoSuchNodeException</code> if it could not be found.
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode findByPrimaryKey(long kaleoNodeId)
		throws NoSuchNodeException {

		return findByPrimaryKey((Serializable)kaleoNodeId);
	}

	/**
	 * Returns the kaleo node with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo node
	 * @return the kaleo node, or <code>null</code> if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(KaleoNode.class, primaryKey)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		KaleoNode kaleoNode = null;

		Session session = null;

		try {
			session = openSession();

			kaleoNode = (KaleoNode)session.get(KaleoNodeImpl.class, primaryKey);

			if (kaleoNode != null) {
				cacheResult(kaleoNode);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return kaleoNode;
	}

	/**
	 * Returns the kaleo node with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node, or <code>null</code> if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode fetchByPrimaryKey(long kaleoNodeId) {
		return fetchByPrimaryKey((Serializable)kaleoNodeId);
	}

	@Override
	public Map<Serializable, KaleoNode> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(KaleoNode.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, KaleoNode> map =
			new HashMap<Serializable, KaleoNode>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			KaleoNode kaleoNode = fetchByPrimaryKey(primaryKey);

			if (kaleoNode != null) {
				map.put(primaryKey, kaleoNode);
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

			for (KaleoNode kaleoNode : (List<KaleoNode>)query.list()) {
				map.put(kaleoNode.getPrimaryKeyObj(), kaleoNode);

				cacheResult(kaleoNode);
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
	 * Returns all the kaleo nodes.
	 *
	 * @return the kaleo nodes
	 */
	@Override
	public List<KaleoNode> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of kaleo nodes
	 */
	@Override
	public List<KaleoNode> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo nodes
	 */
	@Override
	public List<KaleoNode> findAll(
		int start, int end, OrderByComparator<KaleoNode> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo nodes
	 */
	@Override
	public List<KaleoNode> findAll(
		int start, int end, OrderByComparator<KaleoNode> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNode.class);

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

		List<KaleoNode> list = null;

		if (useFinderCache && productionMode) {
			list = (List<KaleoNode>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEONODE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEONODE;

				sql = sql.concat(KaleoNodeModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoNode>)QueryUtil.list(
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
	 * Removes all the kaleo nodes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoNode kaleoNode : findAll()) {
			remove(kaleoNode);
		}
	}

	/**
	 * Returns the number of kaleo nodes.
	 *
	 * @return the number of kaleo nodes
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			KaleoNode.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_KALEONODE);

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
		return "kaleoNodeId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEONODE;
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
		return KaleoNodeModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "KaleoNode";
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
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("kaleoDefinitionId");
		ctStrictColumnNames.add("kaleoDefinitionVersionId");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("label");
		ctStrictColumnNames.add("metadata");
		ctStrictColumnNames.add("description");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("initial_");
		ctStrictColumnNames.add("terminal");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("kaleoNodeId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the kaleo node persistence.
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

		_finderPathWithPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"kaleoDefinitionVersionId"}, true);

		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {Long.class.getName()},
				new String[] {"kaleoDefinitionVersionId"}, true);

		_finderPathCountByKaleoDefinitionVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoDefinitionVersionId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoDefinitionVersionId"}, false);

		_finderPathWithPaginationFindByC_KDVI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_KDVI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "kaleoDefinitionVersionId"}, true);

		_finderPathWithoutPaginationFindByC_KDVI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_KDVI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "kaleoDefinitionVersionId"}, true);

		_finderPathCountByC_KDVI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_KDVI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "kaleoDefinitionVersionId"}, false);

		KaleoNodeUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		KaleoNodeUtil.setPersistence(null);

		entityCache.removeCache(KaleoNodeImpl.class.getName());
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_KALEONODE =
		"SELECT kaleoNode FROM KaleoNode kaleoNode";

	private static final String _SQL_SELECT_KALEONODE_WHERE =
		"SELECT kaleoNode FROM KaleoNode kaleoNode WHERE ";

	private static final String _SQL_COUNT_KALEONODE =
		"SELECT COUNT(kaleoNode) FROM KaleoNode kaleoNode";

	private static final String _SQL_COUNT_KALEONODE_WHERE =
		"SELECT COUNT(kaleoNode) FROM KaleoNode kaleoNode WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoNode.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoNode exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoNode exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoNodePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type", "initial"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}