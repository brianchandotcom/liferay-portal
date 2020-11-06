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

package com.liferay.commerce.bom.service.persistence.impl;

import com.liferay.commerce.bom.exception.NoSuchBOMFolderApplicationRelException;
import com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel;
import com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRelTable;
import com.liferay.commerce.bom.model.impl.CommerceBOMFolderApplicationRelImpl;
import com.liferay.commerce.bom.model.impl.CommerceBOMFolderApplicationRelModelImpl;
import com.liferay.commerce.bom.service.persistence.CommerceBOMFolderApplicationRelPersistence;
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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce bom folder application rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceBOMFolderApplicationRelPersistenceImpl
	extends BasePersistenceImpl<CommerceBOMFolderApplicationRel>
	implements CommerceBOMFolderApplicationRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceBOMFolderApplicationRelUtil</code> to access the commerce bom folder application rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceBOMFolderApplicationRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceBOMFolderId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceBOMFolderId;
	private FinderPath _finderPathCountByCommerceBOMFolderId;

	/**
	 * Returns all the commerce bom folder application rels where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @return the matching commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel> findByCommerceBOMFolderId(
		long commerceBOMFolderId) {

		return findByCommerceBOMFolderId(
			commerceBOMFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce bom folder application rels where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @return the range of matching commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel> findByCommerceBOMFolderId(
		long commerceBOMFolderId, int start, int end) {

		return findByCommerceBOMFolderId(commerceBOMFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce bom folder application rels where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel> findByCommerceBOMFolderId(
		long commerceBOMFolderId, int start, int end,
		OrderByComparator<CommerceBOMFolderApplicationRel> orderByComparator) {

		return findByCommerceBOMFolderId(
			commerceBOMFolderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce bom folder application rels where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel> findByCommerceBOMFolderId(
		long commerceBOMFolderId, int start, int end,
		OrderByComparator<CommerceBOMFolderApplicationRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceBOMFolderId;
				finderArgs = new Object[] {commerceBOMFolderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceBOMFolderId;
			finderArgs = new Object[] {
				commerceBOMFolderId, start, end, orderByComparator
			};
		}

		List<CommerceBOMFolderApplicationRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceBOMFolderApplicationRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceBOMFolderApplicationRel
						commerceBOMFolderApplicationRel : list) {

					if (commerceBOMFolderId !=
							commerceBOMFolderApplicationRel.
								getCommerceBOMFolderId()) {

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

			sb.append(_SQL_SELECT_COMMERCEBOMFOLDERAPPLICATIONREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEBOMFOLDERID_COMMERCEBOMFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceBOMFolderApplicationRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceBOMFolderId);

				list = (List<CommerceBOMFolderApplicationRel>)QueryUtil.list(
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
	 * Returns the first commerce bom folder application rel in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a matching commerce bom folder application rel could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel findByCommerceBOMFolderId_First(
			long commerceBOMFolderId,
			OrderByComparator<CommerceBOMFolderApplicationRel>
				orderByComparator)
		throws NoSuchBOMFolderApplicationRelException {

		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
			fetchByCommerceBOMFolderId_First(
				commerceBOMFolderId, orderByComparator);

		if (commerceBOMFolderApplicationRel != null) {
			return commerceBOMFolderApplicationRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceBOMFolderId=");
		sb.append(commerceBOMFolderId);

		sb.append("}");

		throw new NoSuchBOMFolderApplicationRelException(sb.toString());
	}

	/**
	 * Returns the first commerce bom folder application rel in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder application rel, or <code>null</code> if a matching commerce bom folder application rel could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel fetchByCommerceBOMFolderId_First(
		long commerceBOMFolderId,
		OrderByComparator<CommerceBOMFolderApplicationRel> orderByComparator) {

		List<CommerceBOMFolderApplicationRel> list = findByCommerceBOMFolderId(
			commerceBOMFolderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce bom folder application rel in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a matching commerce bom folder application rel could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel findByCommerceBOMFolderId_Last(
			long commerceBOMFolderId,
			OrderByComparator<CommerceBOMFolderApplicationRel>
				orderByComparator)
		throws NoSuchBOMFolderApplicationRelException {

		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
			fetchByCommerceBOMFolderId_Last(
				commerceBOMFolderId, orderByComparator);

		if (commerceBOMFolderApplicationRel != null) {
			return commerceBOMFolderApplicationRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceBOMFolderId=");
		sb.append(commerceBOMFolderId);

		sb.append("}");

		throw new NoSuchBOMFolderApplicationRelException(sb.toString());
	}

	/**
	 * Returns the last commerce bom folder application rel in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder application rel, or <code>null</code> if a matching commerce bom folder application rel could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel fetchByCommerceBOMFolderId_Last(
		long commerceBOMFolderId,
		OrderByComparator<CommerceBOMFolderApplicationRel> orderByComparator) {

		int count = countByCommerceBOMFolderId(commerceBOMFolderId);

		if (count == 0) {
			return null;
		}

		List<CommerceBOMFolderApplicationRel> list = findByCommerceBOMFolderId(
			commerceBOMFolderId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce bom folder application rels before and after the current commerce bom folder application rel in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the current commerce bom folder application rel
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a commerce bom folder application rel with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel[]
			findByCommerceBOMFolderId_PrevAndNext(
				long commerceBOMFolderApplicationRelId,
				long commerceBOMFolderId,
				OrderByComparator<CommerceBOMFolderApplicationRel>
					orderByComparator)
		throws NoSuchBOMFolderApplicationRelException {

		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
			findByPrimaryKey(commerceBOMFolderApplicationRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceBOMFolderApplicationRel[] array =
				new CommerceBOMFolderApplicationRelImpl[3];

			array[0] = getByCommerceBOMFolderId_PrevAndNext(
				session, commerceBOMFolderApplicationRel, commerceBOMFolderId,
				orderByComparator, true);

			array[1] = commerceBOMFolderApplicationRel;

			array[2] = getByCommerceBOMFolderId_PrevAndNext(
				session, commerceBOMFolderApplicationRel, commerceBOMFolderId,
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

	protected CommerceBOMFolderApplicationRel
		getByCommerceBOMFolderId_PrevAndNext(
			Session session,
			CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel,
			long commerceBOMFolderId,
			OrderByComparator<CommerceBOMFolderApplicationRel>
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

		sb.append(_SQL_SELECT_COMMERCEBOMFOLDERAPPLICATIONREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEBOMFOLDERID_COMMERCEBOMFOLDERID_2);

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
			sb.append(CommerceBOMFolderApplicationRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceBOMFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceBOMFolderApplicationRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceBOMFolderApplicationRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce bom folder application rels where commerceBOMFolderId = &#63; from the database.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 */
	@Override
	public void removeByCommerceBOMFolderId(long commerceBOMFolderId) {
		for (CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel :
				findByCommerceBOMFolderId(
					commerceBOMFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceBOMFolderApplicationRel);
		}
	}

	/**
	 * Returns the number of commerce bom folder application rels where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @return the number of matching commerce bom folder application rels
	 */
	@Override
	public int countByCommerceBOMFolderId(long commerceBOMFolderId) {
		FinderPath finderPath = _finderPathCountByCommerceBOMFolderId;

		Object[] finderArgs = new Object[] {commerceBOMFolderId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEBOMFOLDERAPPLICATIONREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEBOMFOLDERID_COMMERCEBOMFOLDERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceBOMFolderId);

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
		_FINDER_COLUMN_COMMERCEBOMFOLDERID_COMMERCEBOMFOLDERID_2 =
			"commerceBOMFolderApplicationRel.commerceBOMFolderId = ?";

	private FinderPath
		_finderPathWithPaginationFindByCommerceApplicationModelId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceApplicationModelId;
	private FinderPath _finderPathCountByCommerceApplicationModelId;

	/**
	 * Returns all the commerce bom folder application rels where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @return the matching commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel>
		findByCommerceApplicationModelId(long commerceApplicationModelId) {

		return findByCommerceApplicationModelId(
			commerceApplicationModelId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce bom folder application rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @return the range of matching commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end) {

		return findByCommerceApplicationModelId(
			commerceApplicationModelId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce bom folder application rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end,
			OrderByComparator<CommerceBOMFolderApplicationRel>
				orderByComparator) {

		return findByCommerceApplicationModelId(
			commerceApplicationModelId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce bom folder application rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end,
			OrderByComparator<CommerceBOMFolderApplicationRel>
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

		List<CommerceBOMFolderApplicationRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceBOMFolderApplicationRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceBOMFolderApplicationRel
						commerceBOMFolderApplicationRel : list) {

					if (commerceApplicationModelId !=
							commerceBOMFolderApplicationRel.
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

			sb.append(_SQL_SELECT_COMMERCEBOMFOLDERAPPLICATIONREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEAPPLICATIONMODELID_COMMERCEAPPLICATIONMODELID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceBOMFolderApplicationRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceApplicationModelId);

				list = (List<CommerceBOMFolderApplicationRel>)QueryUtil.list(
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
	 * Returns the first commerce bom folder application rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a matching commerce bom folder application rel could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel
			findByCommerceApplicationModelId_First(
				long commerceApplicationModelId,
				OrderByComparator<CommerceBOMFolderApplicationRel>
					orderByComparator)
		throws NoSuchBOMFolderApplicationRelException {

		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
			fetchByCommerceApplicationModelId_First(
				commerceApplicationModelId, orderByComparator);

		if (commerceBOMFolderApplicationRel != null) {
			return commerceBOMFolderApplicationRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceApplicationModelId=");
		sb.append(commerceApplicationModelId);

		sb.append("}");

		throw new NoSuchBOMFolderApplicationRelException(sb.toString());
	}

	/**
	 * Returns the first commerce bom folder application rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder application rel, or <code>null</code> if a matching commerce bom folder application rel could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel
		fetchByCommerceApplicationModelId_First(
			long commerceApplicationModelId,
			OrderByComparator<CommerceBOMFolderApplicationRel>
				orderByComparator) {

		List<CommerceBOMFolderApplicationRel> list =
			findByCommerceApplicationModelId(
				commerceApplicationModelId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce bom folder application rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a matching commerce bom folder application rel could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel
			findByCommerceApplicationModelId_Last(
				long commerceApplicationModelId,
				OrderByComparator<CommerceBOMFolderApplicationRel>
					orderByComparator)
		throws NoSuchBOMFolderApplicationRelException {

		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
			fetchByCommerceApplicationModelId_Last(
				commerceApplicationModelId, orderByComparator);

		if (commerceBOMFolderApplicationRel != null) {
			return commerceBOMFolderApplicationRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceApplicationModelId=");
		sb.append(commerceApplicationModelId);

		sb.append("}");

		throw new NoSuchBOMFolderApplicationRelException(sb.toString());
	}

	/**
	 * Returns the last commerce bom folder application rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder application rel, or <code>null</code> if a matching commerce bom folder application rel could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel
		fetchByCommerceApplicationModelId_Last(
			long commerceApplicationModelId,
			OrderByComparator<CommerceBOMFolderApplicationRel>
				orderByComparator) {

		int count = countByCommerceApplicationModelId(
			commerceApplicationModelId);

		if (count == 0) {
			return null;
		}

		List<CommerceBOMFolderApplicationRel> list =
			findByCommerceApplicationModelId(
				commerceApplicationModelId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce bom folder application rels before and after the current commerce bom folder application rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the current commerce bom folder application rel
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a commerce bom folder application rel with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel[]
			findByCommerceApplicationModelId_PrevAndNext(
				long commerceBOMFolderApplicationRelId,
				long commerceApplicationModelId,
				OrderByComparator<CommerceBOMFolderApplicationRel>
					orderByComparator)
		throws NoSuchBOMFolderApplicationRelException {

		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
			findByPrimaryKey(commerceBOMFolderApplicationRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceBOMFolderApplicationRel[] array =
				new CommerceBOMFolderApplicationRelImpl[3];

			array[0] = getByCommerceApplicationModelId_PrevAndNext(
				session, commerceBOMFolderApplicationRel,
				commerceApplicationModelId, orderByComparator, true);

			array[1] = commerceBOMFolderApplicationRel;

			array[2] = getByCommerceApplicationModelId_PrevAndNext(
				session, commerceBOMFolderApplicationRel,
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

	protected CommerceBOMFolderApplicationRel
		getByCommerceApplicationModelId_PrevAndNext(
			Session session,
			CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel,
			long commerceApplicationModelId,
			OrderByComparator<CommerceBOMFolderApplicationRel>
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

		sb.append(_SQL_SELECT_COMMERCEBOMFOLDERAPPLICATIONREL_WHERE);

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
			sb.append(CommerceBOMFolderApplicationRelModelImpl.ORDER_BY_JPQL);
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
						commerceBOMFolderApplicationRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceBOMFolderApplicationRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce bom folder application rels where commerceApplicationModelId = &#63; from the database.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 */
	@Override
	public void removeByCommerceApplicationModelId(
		long commerceApplicationModelId) {

		for (CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel :
				findByCommerceApplicationModelId(
					commerceApplicationModelId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceBOMFolderApplicationRel);
		}
	}

	/**
	 * Returns the number of commerce bom folder application rels where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @return the number of matching commerce bom folder application rels
	 */
	@Override
	public int countByCommerceApplicationModelId(
		long commerceApplicationModelId) {

		FinderPath finderPath = _finderPathCountByCommerceApplicationModelId;

		Object[] finderArgs = new Object[] {commerceApplicationModelId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEBOMFOLDERAPPLICATIONREL_WHERE);

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
			"commerceBOMFolderApplicationRel.commerceApplicationModelId = ?";

	public CommerceBOMFolderApplicationRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceBOMFolderApplicationRelId", "CBOMFolderApplicationRelId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceBOMFolderApplicationRel.class);

		setModelImplClass(CommerceBOMFolderApplicationRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceBOMFolderApplicationRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce bom folder application rel in the entity cache if it is enabled.
	 *
	 * @param commerceBOMFolderApplicationRel the commerce bom folder application rel
	 */
	@Override
	public void cacheResult(
		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel) {

		entityCache.putResult(
			CommerceBOMFolderApplicationRelImpl.class,
			commerceBOMFolderApplicationRel.getPrimaryKey(),
			commerceBOMFolderApplicationRel);
	}

	/**
	 * Caches the commerce bom folder application rels in the entity cache if it is enabled.
	 *
	 * @param commerceBOMFolderApplicationRels the commerce bom folder application rels
	 */
	@Override
	public void cacheResult(
		List<CommerceBOMFolderApplicationRel>
			commerceBOMFolderApplicationRels) {

		for (CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel :
				commerceBOMFolderApplicationRels) {

			if (entityCache.getResult(
					CommerceBOMFolderApplicationRelImpl.class,
					commerceBOMFolderApplicationRel.getPrimaryKey()) == null) {

				cacheResult(commerceBOMFolderApplicationRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce bom folder application rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceBOMFolderApplicationRelImpl.class);

		finderCache.clearCache(CommerceBOMFolderApplicationRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce bom folder application rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel) {

		entityCache.removeResult(
			CommerceBOMFolderApplicationRelImpl.class,
			commerceBOMFolderApplicationRel);
	}

	@Override
	public void clearCache(
		List<CommerceBOMFolderApplicationRel>
			commerceBOMFolderApplicationRels) {

		for (CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel :
				commerceBOMFolderApplicationRels) {

			entityCache.removeResult(
				CommerceBOMFolderApplicationRelImpl.class,
				commerceBOMFolderApplicationRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceBOMFolderApplicationRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceBOMFolderApplicationRelImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce bom folder application rel with the primary key. Does not add the commerce bom folder application rel to the database.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key for the new commerce bom folder application rel
	 * @return the new commerce bom folder application rel
	 */
	@Override
	public CommerceBOMFolderApplicationRel create(
		long commerceBOMFolderApplicationRelId) {

		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
			new CommerceBOMFolderApplicationRelImpl();

		commerceBOMFolderApplicationRel.setNew(true);
		commerceBOMFolderApplicationRel.setPrimaryKey(
			commerceBOMFolderApplicationRelId);

		commerceBOMFolderApplicationRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceBOMFolderApplicationRel;
	}

	/**
	 * Removes the commerce bom folder application rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the commerce bom folder application rel
	 * @return the commerce bom folder application rel that was removed
	 * @throws NoSuchBOMFolderApplicationRelException if a commerce bom folder application rel with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel remove(
			long commerceBOMFolderApplicationRelId)
		throws NoSuchBOMFolderApplicationRelException {

		return remove((Serializable)commerceBOMFolderApplicationRelId);
	}

	/**
	 * Removes the commerce bom folder application rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce bom folder application rel
	 * @return the commerce bom folder application rel that was removed
	 * @throws NoSuchBOMFolderApplicationRelException if a commerce bom folder application rel with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel remove(Serializable primaryKey)
		throws NoSuchBOMFolderApplicationRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
				(CommerceBOMFolderApplicationRel)session.get(
					CommerceBOMFolderApplicationRelImpl.class, primaryKey);

			if (commerceBOMFolderApplicationRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBOMFolderApplicationRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceBOMFolderApplicationRel);
		}
		catch (NoSuchBOMFolderApplicationRelException noSuchEntityException) {
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
	protected CommerceBOMFolderApplicationRel removeImpl(
		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceBOMFolderApplicationRel)) {
				commerceBOMFolderApplicationRel =
					(CommerceBOMFolderApplicationRel)session.get(
						CommerceBOMFolderApplicationRelImpl.class,
						commerceBOMFolderApplicationRel.getPrimaryKeyObj());
			}

			if (commerceBOMFolderApplicationRel != null) {
				session.delete(commerceBOMFolderApplicationRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceBOMFolderApplicationRel != null) {
			clearCache(commerceBOMFolderApplicationRel);
		}

		return commerceBOMFolderApplicationRel;
	}

	@Override
	public CommerceBOMFolderApplicationRel updateImpl(
		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel) {

		boolean isNew = commerceBOMFolderApplicationRel.isNew();

		if (!(commerceBOMFolderApplicationRel instanceof
				CommerceBOMFolderApplicationRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceBOMFolderApplicationRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceBOMFolderApplicationRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceBOMFolderApplicationRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceBOMFolderApplicationRel implementation " +
					commerceBOMFolderApplicationRel.getClass());
		}

		CommerceBOMFolderApplicationRelModelImpl
			commerceBOMFolderApplicationRelModelImpl =
				(CommerceBOMFolderApplicationRelModelImpl)
					commerceBOMFolderApplicationRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
			(commerceBOMFolderApplicationRel.getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceBOMFolderApplicationRel.setCreateDate(now);
			}
			else {
				commerceBOMFolderApplicationRel.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceBOMFolderApplicationRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceBOMFolderApplicationRel.setModifiedDate(now);
			}
			else {
				commerceBOMFolderApplicationRel.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceBOMFolderApplicationRel);
			}
			else {
				commerceBOMFolderApplicationRel =
					(CommerceBOMFolderApplicationRel)session.merge(
						commerceBOMFolderApplicationRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceBOMFolderApplicationRelImpl.class,
			commerceBOMFolderApplicationRelModelImpl, false, true);

		if (isNew) {
			commerceBOMFolderApplicationRel.setNew(false);
		}

		commerceBOMFolderApplicationRel.resetOriginalValues();

		return commerceBOMFolderApplicationRel;
	}

	/**
	 * Returns the commerce bom folder application rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce bom folder application rel
	 * @return the commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a commerce bom folder application rel with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchBOMFolderApplicationRelException {

		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
			fetchByPrimaryKey(primaryKey);

		if (commerceBOMFolderApplicationRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBOMFolderApplicationRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceBOMFolderApplicationRel;
	}

	/**
	 * Returns the commerce bom folder application rel with the primary key or throws a <code>NoSuchBOMFolderApplicationRelException</code> if it could not be found.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the commerce bom folder application rel
	 * @return the commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a commerce bom folder application rel with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel findByPrimaryKey(
			long commerceBOMFolderApplicationRelId)
		throws NoSuchBOMFolderApplicationRelException {

		return findByPrimaryKey(
			(Serializable)commerceBOMFolderApplicationRelId);
	}

	/**
	 * Returns the commerce bom folder application rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the commerce bom folder application rel
	 * @return the commerce bom folder application rel, or <code>null</code> if a commerce bom folder application rel with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolderApplicationRel fetchByPrimaryKey(
		long commerceBOMFolderApplicationRelId) {

		return fetchByPrimaryKey(
			(Serializable)commerceBOMFolderApplicationRelId);
	}

	/**
	 * Returns all the commerce bom folder application rels.
	 *
	 * @return the commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce bom folder application rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @return the range of commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce bom folder application rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel> findAll(
		int start, int end,
		OrderByComparator<CommerceBOMFolderApplicationRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce bom folder application rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce bom folder application rels
	 */
	@Override
	public List<CommerceBOMFolderApplicationRel> findAll(
		int start, int end,
		OrderByComparator<CommerceBOMFolderApplicationRel> orderByComparator,
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

		List<CommerceBOMFolderApplicationRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceBOMFolderApplicationRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEBOMFOLDERAPPLICATIONREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEBOMFOLDERAPPLICATIONREL;

				sql = sql.concat(
					CommerceBOMFolderApplicationRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceBOMFolderApplicationRel>)QueryUtil.list(
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
	 * Removes all the commerce bom folder application rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel :
				findAll()) {

			remove(commerceBOMFolderApplicationRel);
		}
	}

	/**
	 * Returns the number of commerce bom folder application rels.
	 *
	 * @return the number of commerce bom folder application rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEBOMFOLDERAPPLICATIONREL);

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
		return "CBOMFolderApplicationRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEBOMFOLDERAPPLICATIONREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceBOMFolderApplicationRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce bom folder application rel persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceBOMFolderApplicationRelPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceBOMFolderApplicationRelModelArgumentsResolver(),
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

		_finderPathWithPaginationFindByCommerceBOMFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceBOMFolderId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceBOMFolderId"}, true);

		_finderPathWithoutPaginationFindByCommerceBOMFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceBOMFolderId", new String[] {Long.class.getName()},
			new String[] {"commerceBOMFolderId"}, true);

		_finderPathCountByCommerceBOMFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceBOMFolderId", new String[] {Long.class.getName()},
			new String[] {"commerceBOMFolderId"}, false);

		_finderPathWithPaginationFindByCommerceApplicationModelId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceApplicationModelId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceApplicationModelId"}, true);

		_finderPathWithoutPaginationFindByCommerceApplicationModelId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceApplicationModelId",
				new String[] {Long.class.getName()},
				new String[] {"commerceApplicationModelId"}, true);

		_finderPathCountByCommerceApplicationModelId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceApplicationModelId",
			new String[] {Long.class.getName()},
			new String[] {"commerceApplicationModelId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceBOMFolderApplicationRelImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEBOMFOLDERAPPLICATIONREL =
		"SELECT commerceBOMFolderApplicationRel FROM CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel";

	private static final String
		_SQL_SELECT_COMMERCEBOMFOLDERAPPLICATIONREL_WHERE =
			"SELECT commerceBOMFolderApplicationRel FROM CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEBOMFOLDERAPPLICATIONREL =
		"SELECT COUNT(commerceBOMFolderApplicationRel) FROM CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel";

	private static final String
		_SQL_COUNT_COMMERCEBOMFOLDERAPPLICATIONREL_WHERE =
			"SELECT COUNT(commerceBOMFolderApplicationRel) FROM CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceBOMFolderApplicationRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceBOMFolderApplicationRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceBOMFolderApplicationRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceBOMFolderApplicationRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceBOMFolderApplicationRelId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CommerceBOMFolderApplicationRelModelArgumentsResolver
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

			CommerceBOMFolderApplicationRelModelImpl
				commerceBOMFolderApplicationRelModelImpl =
					(CommerceBOMFolderApplicationRelModelImpl)baseModel;

			long columnBitmask =
				commerceBOMFolderApplicationRelModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceBOMFolderApplicationRelModelImpl, columnNames,
					original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceBOMFolderApplicationRelModelImpl.
							getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceBOMFolderApplicationRelModelImpl, columnNames,
					original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CommerceBOMFolderApplicationRelImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CommerceBOMFolderApplicationRelTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CommerceBOMFolderApplicationRelModelImpl
				commerceBOMFolderApplicationRelModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceBOMFolderApplicationRelModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						commerceBOMFolderApplicationRelModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}