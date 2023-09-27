/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.NoSuchChannelAccountEntryRelException;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRelTable;
import com.liferay.commerce.product.model.impl.CommerceChannelAccountEntryRelImpl;
import com.liferay.commerce.product.model.impl.CommerceChannelAccountEntryRelModelImpl;
import com.liferay.commerce.product.service.persistence.CommerceChannelAccountEntryRelPersistence;
import com.liferay.commerce.product.service.persistence.CommerceChannelAccountEntryRelUtil;
import com.liferay.commerce.product.service.persistence.impl.constants.CommercePersistenceConstants;
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
 * The persistence implementation for the commerce channel account entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CommerceChannelAccountEntryRelPersistence.class)
public class CommerceChannelAccountEntryRelPersistenceImpl
	extends BasePersistenceImpl<CommerceChannelAccountEntryRel>
	implements CommerceChannelAccountEntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceChannelAccountEntryRelUtil</code> to access the commerce channel account entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceChannelAccountEntryRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByAccountEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAccountEntryId;
	private FinderPath _finderPathCountByAccountEntryId;

	/**
	 * Returns all the commerce channel account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByAccountEntryId(
		long accountEntryId) {

		return findByAccountEntryId(
			accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce channel account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @return the range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return findByAccountEntryId(accountEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		return findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByAccountEntryId;
				finderArgs = new Object[] {accountEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByAccountEntryId;
			finderArgs = new Object[] {
				accountEntryId, start, end, orderByComparator
			};
		}

		List<CommerceChannelAccountEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceChannelAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceChannelAccountEntryRel
						commerceChannelAccountEntryRel : list) {

					if (accountEntryId !=
							commerceChannelAccountEntryRel.
								getAccountEntryId()) {

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

			sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				list = (List<CommerceChannelAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce channel account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByAccountEntryId_First(accountEntryId, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce channel account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		List<CommerceChannelAccountEntryRel> list = findByAccountEntryId(
			accountEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByAccountEntryId_Last(accountEntryId, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		int count = countByAccountEntryId(accountEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceChannelAccountEntryRel> list = findByAccountEntryId(
			accountEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce channel account entry rels before and after the current commerce channel account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key of the current commerce channel account entry rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel[] findByAccountEntryId_PrevAndNext(
			long commerceChannelAccountEntryRelId, long accountEntryId,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			findByPrimaryKey(commerceChannelAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceChannelAccountEntryRel[] array =
				new CommerceChannelAccountEntryRelImpl[3];

			array[0] = getByAccountEntryId_PrevAndNext(
				session, commerceChannelAccountEntryRel, accountEntryId,
				orderByComparator, true);

			array[1] = commerceChannelAccountEntryRel;

			array[2] = getByAccountEntryId_PrevAndNext(
				session, commerceChannelAccountEntryRel, accountEntryId,
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

	protected CommerceChannelAccountEntryRel getByAccountEntryId_PrevAndNext(
		Session session,
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
		long accountEntryId,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

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
			sb.append(CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceChannelAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceChannelAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce channel account entry rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	@Override
	public void removeByAccountEntryId(long accountEntryId) {
		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				findByAccountEntryId(
					accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceChannelAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce channel account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching commerce channel account entry rels
	 */
	@Override
	public int countByAccountEntryId(long accountEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByAccountEntryId;

			finderArgs = new Object[] {accountEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

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

	private static final String _FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2 =
		"commerceChannelAccountEntryRel.accountEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceChannelId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceChannelId;
	private FinderPath _finderPathCountByCommerceChannelId;

	/**
	 * Returns all the commerce channel account entry rels where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByCommerceChannelId(
		long commerceChannelId) {

		return findByCommerceChannelId(
			commerceChannelId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce channel account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @return the range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByCommerceChannelId(
		long commerceChannelId, int start, int end) {

		return findByCommerceChannelId(commerceChannelId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByCommerceChannelId(
		long commerceChannelId, int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		return findByCommerceChannelId(
			commerceChannelId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByCommerceChannelId(
		long commerceChannelId, int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceChannelId;
				finderArgs = new Object[] {commerceChannelId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCommerceChannelId;
			finderArgs = new Object[] {
				commerceChannelId, start, end, orderByComparator
			};
		}

		List<CommerceChannelAccountEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceChannelAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceChannelAccountEntryRel
						commerceChannelAccountEntryRel : list) {

					if (commerceChannelId !=
							commerceChannelAccountEntryRel.
								getCommerceChannelId()) {

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

			sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCECHANNELID_COMMERCECHANNELID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceChannelId);

				list = (List<CommerceChannelAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce channel account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByCommerceChannelId_First(
			long commerceChannelId,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByCommerceChannelId_First(
				commerceChannelId, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceChannelId=");
		sb.append(commerceChannelId);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce channel account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByCommerceChannelId_First(
		long commerceChannelId,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		List<CommerceChannelAccountEntryRel> list = findByCommerceChannelId(
			commerceChannelId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByCommerceChannelId_Last(
			long commerceChannelId,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByCommerceChannelId_Last(commerceChannelId, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceChannelId=");
		sb.append(commerceChannelId);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByCommerceChannelId_Last(
		long commerceChannelId,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		int count = countByCommerceChannelId(commerceChannelId);

		if (count == 0) {
			return null;
		}

		List<CommerceChannelAccountEntryRel> list = findByCommerceChannelId(
			commerceChannelId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce channel account entry rels before and after the current commerce channel account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key of the current commerce channel account entry rel
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel[] findByCommerceChannelId_PrevAndNext(
			long commerceChannelAccountEntryRelId, long commerceChannelId,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			findByPrimaryKey(commerceChannelAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceChannelAccountEntryRel[] array =
				new CommerceChannelAccountEntryRelImpl[3];

			array[0] = getByCommerceChannelId_PrevAndNext(
				session, commerceChannelAccountEntryRel, commerceChannelId,
				orderByComparator, true);

			array[1] = commerceChannelAccountEntryRel;

			array[2] = getByCommerceChannelId_PrevAndNext(
				session, commerceChannelAccountEntryRel, commerceChannelId,
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

	protected CommerceChannelAccountEntryRel getByCommerceChannelId_PrevAndNext(
		Session session,
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
		long commerceChannelId,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCECHANNELID_COMMERCECHANNELID_2);

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
			sb.append(CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceChannelId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceChannelAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceChannelAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce channel account entry rels where commerceChannelId = &#63; from the database.
	 *
	 * @param commerceChannelId the commerce channel ID
	 */
	@Override
	public void removeByCommerceChannelId(long commerceChannelId) {
		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				findByCommerceChannelId(
					commerceChannelId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceChannelAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce channel account entry rels where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @return the number of matching commerce channel account entry rels
	 */
	@Override
	public int countByCommerceChannelId(long commerceChannelId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCommerceChannelId;

			finderArgs = new Object[] {commerceChannelId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCECHANNELID_COMMERCECHANNELID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceChannelId);

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
		_FINDER_COLUMN_COMMERCECHANNELID_COMMERCECHANNELID_2 =
			"commerceChannelAccountEntryRel.commerceChannelId = ?";

	private FinderPath _finderPathWithPaginationFindByA_T;
	private FinderPath _finderPathWithoutPaginationFindByA_T;
	private FinderPath _finderPathCountByA_T;

	/**
	 * Returns all the commerce channel account entry rels where accountEntryId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 * @return the matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByA_T(
		long accountEntryId, int type) {

		return findByA_T(
			accountEntryId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce channel account entry rels where accountEntryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @return the range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByA_T(
		long accountEntryId, int type, int start, int end) {

		return findByA_T(accountEntryId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where accountEntryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByA_T(
		long accountEntryId, int type, int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		return findByA_T(
			accountEntryId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where accountEntryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByA_T(
		long accountEntryId, int type, int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByA_T;
				finderArgs = new Object[] {accountEntryId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByA_T;
			finderArgs = new Object[] {
				accountEntryId, type, start, end, orderByComparator
			};
		}

		List<CommerceChannelAccountEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceChannelAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceChannelAccountEntryRel
						commerceChannelAccountEntryRel : list) {

					if ((accountEntryId !=
							commerceChannelAccountEntryRel.
								getAccountEntryId()) ||
						(type != commerceChannelAccountEntryRel.getType())) {

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

			sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_T_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(type);

				list = (List<CommerceChannelAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce channel account entry rel in the ordered set where accountEntryId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByA_T_First(
			long accountEntryId, int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByA_T_First(accountEntryId, type, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce channel account entry rel in the ordered set where accountEntryId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByA_T_First(
		long accountEntryId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		List<CommerceChannelAccountEntryRel> list = findByA_T(
			accountEntryId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where accountEntryId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByA_T_Last(
			long accountEntryId, int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByA_T_Last(accountEntryId, type, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where accountEntryId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByA_T_Last(
		long accountEntryId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		int count = countByA_T(accountEntryId, type);

		if (count == 0) {
			return null;
		}

		List<CommerceChannelAccountEntryRel> list = findByA_T(
			accountEntryId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce channel account entry rels before and after the current commerce channel account entry rel in the ordered set where accountEntryId = &#63; and type = &#63;.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key of the current commerce channel account entry rel
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel[] findByA_T_PrevAndNext(
			long commerceChannelAccountEntryRelId, long accountEntryId,
			int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			findByPrimaryKey(commerceChannelAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceChannelAccountEntryRel[] array =
				new CommerceChannelAccountEntryRelImpl[3];

			array[0] = getByA_T_PrevAndNext(
				session, commerceChannelAccountEntryRel, accountEntryId, type,
				orderByComparator, true);

			array[1] = commerceChannelAccountEntryRel;

			array[2] = getByA_T_PrevAndNext(
				session, commerceChannelAccountEntryRel, accountEntryId, type,
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

	protected CommerceChannelAccountEntryRel getByA_T_PrevAndNext(
		Session session,
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
		long accountEntryId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_A_T_ACCOUNTENTRYID_2);

		sb.append(_FINDER_COLUMN_A_T_TYPE_2);

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
			sb.append(CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountEntryId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceChannelAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceChannelAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce channel account entry rels where accountEntryId = &#63; and type = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 */
	@Override
	public void removeByA_T(long accountEntryId, int type) {
		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				findByA_T(
					accountEntryId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceChannelAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce channel account entry rels where accountEntryId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param type the type
	 * @return the number of matching commerce channel account entry rels
	 */
	@Override
	public int countByA_T(long accountEntryId, int type) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByA_T;

			finderArgs = new Object[] {accountEntryId, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_T_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_A_T_ACCOUNTENTRYID_2 =
		"commerceChannelAccountEntryRel.accountEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_T_TYPE_2 =
		"commerceChannelAccountEntryRel.type = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce channel account entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_C(
		long classNameId, long classPK) {

		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce channel account entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @return the range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<CommerceChannelAccountEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceChannelAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceChannelAccountEntryRel
						commerceChannelAccountEntryRel : list) {

					if ((classNameId !=
							commerceChannelAccountEntryRel.getClassNameId()) ||
						(classPK !=
							commerceChannelAccountEntryRel.getClassPK())) {

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

			sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<CommerceChannelAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce channel account entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByC_C_First(classNameId, classPK, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce channel account entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		List<CommerceChannelAccountEntryRel> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByC_C_Last(classNameId, classPK, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CommerceChannelAccountEntryRel> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce channel account entry rels before and after the current commerce channel account entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key of the current commerce channel account entry rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel[] findByC_C_PrevAndNext(
			long commerceChannelAccountEntryRelId, long classNameId,
			long classPK,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			findByPrimaryKey(commerceChannelAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceChannelAccountEntryRel[] array =
				new CommerceChannelAccountEntryRelImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commerceChannelAccountEntryRel, classNameId, classPK,
				orderByComparator, true);

			array[1] = commerceChannelAccountEntryRel;

			array[2] = getByC_C_PrevAndNext(
				session, commerceChannelAccountEntryRel, classNameId, classPK,
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

	protected CommerceChannelAccountEntryRel getByC_C_PrevAndNext(
		Session session,
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
		long classNameId, long classPK,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceChannelAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceChannelAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce channel account entry rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceChannelAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce channel account entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce channel account entry rels
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"commerceChannelAccountEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"commerceChannelAccountEntryRel.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_T;
	private FinderPath _finderPathWithoutPaginationFindByC_T;
	private FinderPath _finderPathCountByC_T;

	/**
	 * Returns all the commerce channel account entry rels where commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @return the matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_T(
		long commerceChannelId, int type) {

		return findByC_T(
			commerceChannelId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce channel account entry rels where commerceChannelId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @return the range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_T(
		long commerceChannelId, int type, int start, int end) {

		return findByC_T(commerceChannelId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where commerceChannelId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_T(
		long commerceChannelId, int type, int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		return findByC_T(
			commerceChannelId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where commerceChannelId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_T(
		long commerceChannelId, int type, int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_T;
				finderArgs = new Object[] {commerceChannelId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_T;
			finderArgs = new Object[] {
				commerceChannelId, type, start, end, orderByComparator
			};
		}

		List<CommerceChannelAccountEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceChannelAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceChannelAccountEntryRel
						commerceChannelAccountEntryRel : list) {

					if ((commerceChannelId !=
							commerceChannelAccountEntryRel.
								getCommerceChannelId()) ||
						(type != commerceChannelAccountEntryRel.getType())) {

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

			sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMMERCECHANNELID_2);

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceChannelId);

				queryPos.add(type);

				list = (List<CommerceChannelAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce channel account entry rel in the ordered set where commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByC_T_First(
			long commerceChannelId, int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByC_T_First(commerceChannelId, type, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceChannelId=");
		sb.append(commerceChannelId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce channel account entry rel in the ordered set where commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByC_T_First(
		long commerceChannelId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		List<CommerceChannelAccountEntryRel> list = findByC_T(
			commerceChannelId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByC_T_Last(
			long commerceChannelId, int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByC_T_Last(commerceChannelId, type, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceChannelId=");
		sb.append(commerceChannelId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByC_T_Last(
		long commerceChannelId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		int count = countByC_T(commerceChannelId, type);

		if (count == 0) {
			return null;
		}

		List<CommerceChannelAccountEntryRel> list = findByC_T(
			commerceChannelId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce channel account entry rels before and after the current commerce channel account entry rel in the ordered set where commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key of the current commerce channel account entry rel
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel[] findByC_T_PrevAndNext(
			long commerceChannelAccountEntryRelId, long commerceChannelId,
			int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			findByPrimaryKey(commerceChannelAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceChannelAccountEntryRel[] array =
				new CommerceChannelAccountEntryRelImpl[3];

			array[0] = getByC_T_PrevAndNext(
				session, commerceChannelAccountEntryRel, commerceChannelId,
				type, orderByComparator, true);

			array[1] = commerceChannelAccountEntryRel;

			array[2] = getByC_T_PrevAndNext(
				session, commerceChannelAccountEntryRel, commerceChannelId,
				type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceChannelAccountEntryRel getByC_T_PrevAndNext(
		Session session,
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
		long commerceChannelId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMMERCECHANNELID_2);

		sb.append(_FINDER_COLUMN_C_T_TYPE_2);

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
			sb.append(CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceChannelId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceChannelAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceChannelAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce channel account entry rels where commerceChannelId = &#63; and type = &#63; from the database.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 */
	@Override
	public void removeByC_T(long commerceChannelId, int type) {
		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				findByC_T(
					commerceChannelId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceChannelAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce channel account entry rels where commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @return the number of matching commerce channel account entry rels
	 */
	@Override
	public int countByC_T(long commerceChannelId, int type) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_T;

			finderArgs = new Object[] {commerceChannelId, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMMERCECHANNELID_2);

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceChannelId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_C_T_COMMERCECHANNELID_2 =
		"commerceChannelAccountEntryRel.commerceChannelId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_TYPE_2 =
		"commerceChannelAccountEntryRel.type = ?";

	private FinderPath _finderPathWithPaginationFindByA_C_T;
	private FinderPath _finderPathWithoutPaginationFindByA_C_T;
	private FinderPath _finderPathCountByA_C_T;

	/**
	 * Returns all the commerce channel account entry rels where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @return the matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByA_C_T(
		long accountEntryId, long commerceChannelId, int type) {

		return findByA_C_T(
			accountEntryId, commerceChannelId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce channel account entry rels where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @return the range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByA_C_T(
		long accountEntryId, long commerceChannelId, int type, int start,
		int end) {

		return findByA_C_T(
			accountEntryId, commerceChannelId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByA_C_T(
		long accountEntryId, long commerceChannelId, int type, int start,
		int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		return findByA_C_T(
			accountEntryId, commerceChannelId, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByA_C_T(
		long accountEntryId, long commerceChannelId, int type, int start,
		int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByA_C_T;
				finderArgs = new Object[] {
					accountEntryId, commerceChannelId, type
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByA_C_T;
			finderArgs = new Object[] {
				accountEntryId, commerceChannelId, type, start, end,
				orderByComparator
			};
		}

		List<CommerceChannelAccountEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceChannelAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceChannelAccountEntryRel
						commerceChannelAccountEntryRel : list) {

					if ((accountEntryId !=
							commerceChannelAccountEntryRel.
								getAccountEntryId()) ||
						(commerceChannelId !=
							commerceChannelAccountEntryRel.
								getCommerceChannelId()) ||
						(type != commerceChannelAccountEntryRel.getType())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_C_T_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_C_T_COMMERCECHANNELID_2);

			sb.append(_FINDER_COLUMN_A_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(commerceChannelId);

				queryPos.add(type);

				list = (List<CommerceChannelAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce channel account entry rel in the ordered set where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByA_C_T_First(
			long accountEntryId, long commerceChannelId, int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByA_C_T_First(
				accountEntryId, commerceChannelId, type, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append(", commerceChannelId=");
		sb.append(commerceChannelId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce channel account entry rel in the ordered set where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByA_C_T_First(
		long accountEntryId, long commerceChannelId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		List<CommerceChannelAccountEntryRel> list = findByA_C_T(
			accountEntryId, commerceChannelId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByA_C_T_Last(
			long accountEntryId, long commerceChannelId, int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByA_C_T_Last(
				accountEntryId, commerceChannelId, type, orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append(", commerceChannelId=");
		sb.append(commerceChannelId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByA_C_T_Last(
		long accountEntryId, long commerceChannelId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		int count = countByA_C_T(accountEntryId, commerceChannelId, type);

		if (count == 0) {
			return null;
		}

		List<CommerceChannelAccountEntryRel> list = findByA_C_T(
			accountEntryId, commerceChannelId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce channel account entry rels before and after the current commerce channel account entry rel in the ordered set where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key of the current commerce channel account entry rel
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel[] findByA_C_T_PrevAndNext(
			long commerceChannelAccountEntryRelId, long accountEntryId,
			long commerceChannelId, int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			findByPrimaryKey(commerceChannelAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceChannelAccountEntryRel[] array =
				new CommerceChannelAccountEntryRelImpl[3];

			array[0] = getByA_C_T_PrevAndNext(
				session, commerceChannelAccountEntryRel, accountEntryId,
				commerceChannelId, type, orderByComparator, true);

			array[1] = commerceChannelAccountEntryRel;

			array[2] = getByA_C_T_PrevAndNext(
				session, commerceChannelAccountEntryRel, accountEntryId,
				commerceChannelId, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceChannelAccountEntryRel getByA_C_T_PrevAndNext(
		Session session,
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
		long accountEntryId, long commerceChannelId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_A_C_T_ACCOUNTENTRYID_2);

		sb.append(_FINDER_COLUMN_A_C_T_COMMERCECHANNELID_2);

		sb.append(_FINDER_COLUMN_A_C_T_TYPE_2);

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
			sb.append(CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountEntryId);

		queryPos.add(commerceChannelId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceChannelAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceChannelAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce channel account entry rels where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 */
	@Override
	public void removeByA_C_T(
		long accountEntryId, long commerceChannelId, int type) {

		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				findByA_C_T(
					accountEntryId, commerceChannelId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceChannelAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce channel account entry rels where accountEntryId = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @return the number of matching commerce channel account entry rels
	 */
	@Override
	public int countByA_C_T(
		long accountEntryId, long commerceChannelId, int type) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByA_C_T;

			finderArgs = new Object[] {accountEntryId, commerceChannelId, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_C_T_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_C_T_COMMERCECHANNELID_2);

			sb.append(_FINDER_COLUMN_A_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(commerceChannelId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_A_C_T_ACCOUNTENTRYID_2 =
		"commerceChannelAccountEntryRel.accountEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_C_T_COMMERCECHANNELID_2 =
		"commerceChannelAccountEntryRel.commerceChannelId = ? AND ";

	private static final String _FINDER_COLUMN_A_C_T_TYPE_2 =
		"commerceChannelAccountEntryRel.type = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_C_T;
	private FinderPath _finderPathWithoutPaginationFindByC_C_C_T;
	private FinderPath _finderPathCountByC_C_C_T;

	/**
	 * Returns all the commerce channel account entry rels where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @return the matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_C_C_T(
		long classNameId, long classPK, long commerceChannelId, int type) {

		return findByC_C_C_T(
			classNameId, classPK, commerceChannelId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce channel account entry rels where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @return the range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_C_C_T(
		long classNameId, long classPK, long commerceChannelId, int type,
		int start, int end) {

		return findByC_C_C_T(
			classNameId, classPK, commerceChannelId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_C_C_T(
		long classNameId, long classPK, long commerceChannelId, int type,
		int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		return findByC_C_C_T(
			classNameId, classPK, commerceChannelId, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findByC_C_C_T(
		long classNameId, long classPK, long commerceChannelId, int type,
		int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C_C_T;
				finderArgs = new Object[] {
					classNameId, classPK, commerceChannelId, type
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C_C_T;
			finderArgs = new Object[] {
				classNameId, classPK, commerceChannelId, type, start, end,
				orderByComparator
			};
		}

		List<CommerceChannelAccountEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceChannelAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceChannelAccountEntryRel
						commerceChannelAccountEntryRel : list) {

					if ((classNameId !=
							commerceChannelAccountEntryRel.getClassNameId()) ||
						(classPK !=
							commerceChannelAccountEntryRel.getClassPK()) ||
						(commerceChannelId !=
							commerceChannelAccountEntryRel.
								getCommerceChannelId()) ||
						(type != commerceChannelAccountEntryRel.getType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_T_COMMERCECHANNELID_2);

			sb.append(_FINDER_COLUMN_C_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceChannelId);

				queryPos.add(type);

				list = (List<CommerceChannelAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce channel account entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByC_C_C_T_First(
			long classNameId, long classPK, long commerceChannelId, int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByC_C_C_T_First(
				classNameId, classPK, commerceChannelId, type,
				orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", commerceChannelId=");
		sb.append(commerceChannelId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce channel account entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByC_C_C_T_First(
		long classNameId, long classPK, long commerceChannelId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		List<CommerceChannelAccountEntryRel> list = findByC_C_C_T(
			classNameId, classPK, commerceChannelId, type, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByC_C_C_T_Last(
			long classNameId, long classPK, long commerceChannelId, int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByC_C_C_T_Last(
				classNameId, classPK, commerceChannelId, type,
				orderByComparator);

		if (commerceChannelAccountEntryRel != null) {
			return commerceChannelAccountEntryRel;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", commerceChannelId=");
		sb.append(commerceChannelId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchChannelAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce channel account entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByC_C_C_T_Last(
		long classNameId, long classPK, long commerceChannelId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		int count = countByC_C_C_T(
			classNameId, classPK, commerceChannelId, type);

		if (count == 0) {
			return null;
		}

		List<CommerceChannelAccountEntryRel> list = findByC_C_C_T(
			classNameId, classPK, commerceChannelId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce channel account entry rels before and after the current commerce channel account entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key of the current commerce channel account entry rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel[] findByC_C_C_T_PrevAndNext(
			long commerceChannelAccountEntryRelId, long classNameId,
			long classPK, long commerceChannelId, int type,
			OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			findByPrimaryKey(commerceChannelAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceChannelAccountEntryRel[] array =
				new CommerceChannelAccountEntryRelImpl[3];

			array[0] = getByC_C_C_T_PrevAndNext(
				session, commerceChannelAccountEntryRel, classNameId, classPK,
				commerceChannelId, type, orderByComparator, true);

			array[1] = commerceChannelAccountEntryRel;

			array[2] = getByC_C_C_T_PrevAndNext(
				session, commerceChannelAccountEntryRel, classNameId, classPK,
				commerceChannelId, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceChannelAccountEntryRel getByC_C_C_T_PrevAndNext(
		Session session,
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
		long classNameId, long classPK, long commerceChannelId, int type,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_C_T_CLASSPK_2);

		sb.append(_FINDER_COLUMN_C_C_C_T_COMMERCECHANNELID_2);

		sb.append(_FINDER_COLUMN_C_C_C_T_TYPE_2);

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
			sb.append(CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(commerceChannelId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceChannelAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceChannelAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce channel account entry rels where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 */
	@Override
	public void removeByC_C_C_T(
		long classNameId, long classPK, long commerceChannelId, int type) {

		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				findByC_C_C_T(
					classNameId, classPK, commerceChannelId, type,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceChannelAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce channel account entry rels where classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @return the number of matching commerce channel account entry rels
	 */
	@Override
	public int countByC_C_C_T(
		long classNameId, long classPK, long commerceChannelId, int type) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_C_T;

			finderArgs = new Object[] {
				classNameId, classPK, commerceChannelId, type
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_T_COMMERCECHANNELID_2);

			sb.append(_FINDER_COLUMN_C_C_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceChannelId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_C_C_C_T_CLASSNAMEID_2 =
		"commerceChannelAccountEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_T_CLASSPK_2 =
		"commerceChannelAccountEntryRel.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_T_COMMERCECHANNELID_2 =
		"commerceChannelAccountEntryRel.commerceChannelId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_T_TYPE_2 =
		"commerceChannelAccountEntryRel.type = ?";

	private FinderPath _finderPathFetchByA_C_C_C_T;
	private FinderPath _finderPathCountByA_C_C_C_T;

	/**
	 * Returns the commerce channel account entry rel where accountEntryId = &#63; and classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63; or throws a <code>NoSuchChannelAccountEntryRelException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @return the matching commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByA_C_C_C_T(
			long accountEntryId, long classNameId, long classPK,
			long commerceChannelId, int type)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByA_C_C_C_T(
				accountEntryId, classNameId, classPK, commerceChannelId, type);

		if (commerceChannelAccountEntryRel == null) {
			StringBundler sb = new StringBundler(12);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("accountEntryId=");
			sb.append(accountEntryId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", commerceChannelId=");
			sb.append(commerceChannelId);

			sb.append(", type=");
			sb.append(type);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchChannelAccountEntryRelException(sb.toString());
		}

		return commerceChannelAccountEntryRel;
	}

	/**
	 * Returns the commerce channel account entry rel where accountEntryId = &#63; and classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @return the matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByA_C_C_C_T(
		long accountEntryId, long classNameId, long classPK,
		long commerceChannelId, int type) {

		return fetchByA_C_C_C_T(
			accountEntryId, classNameId, classPK, commerceChannelId, type,
			true);
	}

	/**
	 * Returns the commerce channel account entry rel where accountEntryId = &#63; and classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce channel account entry rel, or <code>null</code> if a matching commerce channel account entry rel could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByA_C_C_C_T(
		long accountEntryId, long classNameId, long classPK,
		long commerceChannelId, int type, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				accountEntryId, classNameId, classPK, commerceChannelId, type
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByA_C_C_C_T, finderArgs, this);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		if (result instanceof CommerceChannelAccountEntryRel) {
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
				(CommerceChannelAccountEntryRel)result;

			if ((accountEntryId !=
					commerceChannelAccountEntryRel.getAccountEntryId()) ||
				(classNameId !=
					commerceChannelAccountEntryRel.getClassNameId()) ||
				(classPK != commerceChannelAccountEntryRel.getClassPK()) ||
				(commerceChannelId !=
					commerceChannelAccountEntryRel.getCommerceChannelId()) ||
				(type != commerceChannelAccountEntryRel.getType())) {

				result = null;
			}
			else if (!ctPersistenceHelper.isProductionMode(
						CommerceChannelAccountEntryRel.class,
						commerceChannelAccountEntryRel.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_C_C_C_T_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_C_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_A_C_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_A_C_C_C_T_COMMERCECHANNELID_2);

			sb.append(_FINDER_COLUMN_A_C_C_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceChannelId);

				queryPos.add(type);

				List<CommerceChannelAccountEntryRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByA_C_C_C_T, finderArgs, list);
					}
				}
				else {
					CommerceChannelAccountEntryRel
						commerceChannelAccountEntryRel = list.get(0);

					result = commerceChannelAccountEntryRel;

					cacheResult(commerceChannelAccountEntryRel);
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
			return (CommerceChannelAccountEntryRel)result;
		}
	}

	/**
	 * Removes the commerce channel account entry rel where accountEntryId = &#63; and classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @return the commerce channel account entry rel that was removed
	 */
	@Override
	public CommerceChannelAccountEntryRel removeByA_C_C_C_T(
			long accountEntryId, long classNameId, long classPK,
			long commerceChannelId, int type)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			findByA_C_C_C_T(
				accountEntryId, classNameId, classPK, commerceChannelId, type);

		return remove(commerceChannelAccountEntryRel);
	}

	/**
	 * Returns the number of commerce channel account entry rels where accountEntryId = &#63; and classNameId = &#63; and classPK = &#63; and commerceChannelId = &#63; and type = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceChannelId the commerce channel ID
	 * @param type the type
	 * @return the number of matching commerce channel account entry rels
	 */
	@Override
	public int countByA_C_C_C_T(
		long accountEntryId, long classNameId, long classPK,
		long commerceChannelId, int type) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByA_C_C_C_T;

			finderArgs = new Object[] {
				accountEntryId, classNameId, classPK, commerceChannelId, type
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_C_C_C_T_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_C_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_A_C_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_A_C_C_C_T_COMMERCECHANNELID_2);

			sb.append(_FINDER_COLUMN_A_C_C_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceChannelId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_A_C_C_C_T_ACCOUNTENTRYID_2 =
		"commerceChannelAccountEntryRel.accountEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_C_C_C_T_CLASSNAMEID_2 =
		"commerceChannelAccountEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_A_C_C_C_T_CLASSPK_2 =
		"commerceChannelAccountEntryRel.classPK = ? AND ";

	private static final String _FINDER_COLUMN_A_C_C_C_T_COMMERCECHANNELID_2 =
		"commerceChannelAccountEntryRel.commerceChannelId = ? AND ";

	private static final String _FINDER_COLUMN_A_C_C_C_T_TYPE_2 =
		"commerceChannelAccountEntryRel.type = ?";

	public CommerceChannelAccountEntryRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceChannelAccountEntryRelId", "CChannelAccountEntryRelId");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceChannelAccountEntryRel.class);

		setModelImplClass(CommerceChannelAccountEntryRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceChannelAccountEntryRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce channel account entry rel in the entity cache if it is enabled.
	 *
	 * @param commerceChannelAccountEntryRel the commerce channel account entry rel
	 */
	@Override
	public void cacheResult(
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel) {

		if (commerceChannelAccountEntryRel.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CommerceChannelAccountEntryRelImpl.class,
			commerceChannelAccountEntryRel.getPrimaryKey(),
			commerceChannelAccountEntryRel);

		finderCache.putResult(
			_finderPathFetchByA_C_C_C_T,
			new Object[] {
				commerceChannelAccountEntryRel.getAccountEntryId(),
				commerceChannelAccountEntryRel.getClassNameId(),
				commerceChannelAccountEntryRel.getClassPK(),
				commerceChannelAccountEntryRel.getCommerceChannelId(),
				commerceChannelAccountEntryRel.getType()
			},
			commerceChannelAccountEntryRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce channel account entry rels in the entity cache if it is enabled.
	 *
	 * @param commerceChannelAccountEntryRels the commerce channel account entry rels
	 */
	@Override
	public void cacheResult(
		List<CommerceChannelAccountEntryRel> commerceChannelAccountEntryRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceChannelAccountEntryRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				commerceChannelAccountEntryRels) {

			if (commerceChannelAccountEntryRel.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CommerceChannelAccountEntryRelImpl.class,
					commerceChannelAccountEntryRel.getPrimaryKey()) == null) {

				cacheResult(commerceChannelAccountEntryRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce channel account entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceChannelAccountEntryRelImpl.class);

		finderCache.clearCache(CommerceChannelAccountEntryRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce channel account entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel) {

		entityCache.removeResult(
			CommerceChannelAccountEntryRelImpl.class,
			commerceChannelAccountEntryRel);
	}

	@Override
	public void clearCache(
		List<CommerceChannelAccountEntryRel> commerceChannelAccountEntryRels) {

		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				commerceChannelAccountEntryRels) {

			entityCache.removeResult(
				CommerceChannelAccountEntryRelImpl.class,
				commerceChannelAccountEntryRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceChannelAccountEntryRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceChannelAccountEntryRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceChannelAccountEntryRelModelImpl
			commerceChannelAccountEntryRelModelImpl) {

		Object[] args = new Object[] {
			commerceChannelAccountEntryRelModelImpl.getAccountEntryId(),
			commerceChannelAccountEntryRelModelImpl.getClassNameId(),
			commerceChannelAccountEntryRelModelImpl.getClassPK(),
			commerceChannelAccountEntryRelModelImpl.getCommerceChannelId(),
			commerceChannelAccountEntryRelModelImpl.getType()
		};

		finderCache.putResult(
			_finderPathCountByA_C_C_C_T, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByA_C_C_C_T, args,
			commerceChannelAccountEntryRelModelImpl);
	}

	/**
	 * Creates a new commerce channel account entry rel with the primary key. Does not add the commerce channel account entry rel to the database.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key for the new commerce channel account entry rel
	 * @return the new commerce channel account entry rel
	 */
	@Override
	public CommerceChannelAccountEntryRel create(
		long commerceChannelAccountEntryRelId) {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			new CommerceChannelAccountEntryRelImpl();

		commerceChannelAccountEntryRel.setNew(true);
		commerceChannelAccountEntryRel.setPrimaryKey(
			commerceChannelAccountEntryRelId);

		commerceChannelAccountEntryRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceChannelAccountEntryRel;
	}

	/**
	 * Removes the commerce channel account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key of the commerce channel account entry rel
	 * @return the commerce channel account entry rel that was removed
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel remove(
			long commerceChannelAccountEntryRelId)
		throws NoSuchChannelAccountEntryRelException {

		return remove((Serializable)commerceChannelAccountEntryRelId);
	}

	/**
	 * Removes the commerce channel account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce channel account entry rel
	 * @return the commerce channel account entry rel that was removed
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel remove(Serializable primaryKey)
		throws NoSuchChannelAccountEntryRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
				(CommerceChannelAccountEntryRel)session.get(
					CommerceChannelAccountEntryRelImpl.class, primaryKey);

			if (commerceChannelAccountEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchChannelAccountEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceChannelAccountEntryRel);
		}
		catch (NoSuchChannelAccountEntryRelException noSuchEntityException) {
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
	protected CommerceChannelAccountEntryRel removeImpl(
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceChannelAccountEntryRel)) {
				commerceChannelAccountEntryRel =
					(CommerceChannelAccountEntryRel)session.get(
						CommerceChannelAccountEntryRelImpl.class,
						commerceChannelAccountEntryRel.getPrimaryKeyObj());
			}

			if ((commerceChannelAccountEntryRel != null) &&
				ctPersistenceHelper.isRemove(commerceChannelAccountEntryRel)) {

				session.delete(commerceChannelAccountEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceChannelAccountEntryRel != null) {
			clearCache(commerceChannelAccountEntryRel);
		}

		return commerceChannelAccountEntryRel;
	}

	@Override
	public CommerceChannelAccountEntryRel updateImpl(
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel) {

		boolean isNew = commerceChannelAccountEntryRel.isNew();

		if (!(commerceChannelAccountEntryRel instanceof
				CommerceChannelAccountEntryRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceChannelAccountEntryRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceChannelAccountEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceChannelAccountEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceChannelAccountEntryRel implementation " +
					commerceChannelAccountEntryRel.getClass());
		}

		CommerceChannelAccountEntryRelModelImpl
			commerceChannelAccountEntryRelModelImpl =
				(CommerceChannelAccountEntryRelModelImpl)
					commerceChannelAccountEntryRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceChannelAccountEntryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceChannelAccountEntryRel.setCreateDate(date);
			}
			else {
				commerceChannelAccountEntryRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceChannelAccountEntryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceChannelAccountEntryRel.setModifiedDate(date);
			}
			else {
				commerceChannelAccountEntryRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(commerceChannelAccountEntryRel)) {
				if (!isNew) {
					session.evict(
						CommerceChannelAccountEntryRelImpl.class,
						commerceChannelAccountEntryRel.getPrimaryKeyObj());
				}

				session.save(commerceChannelAccountEntryRel);
			}
			else {
				commerceChannelAccountEntryRel =
					(CommerceChannelAccountEntryRel)session.merge(
						commerceChannelAccountEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceChannelAccountEntryRel.getCtCollectionId() != 0) {
			if (isNew) {
				commerceChannelAccountEntryRel.setNew(false);
			}

			commerceChannelAccountEntryRel.resetOriginalValues();

			return commerceChannelAccountEntryRel;
		}

		entityCache.putResult(
			CommerceChannelAccountEntryRelImpl.class,
			commerceChannelAccountEntryRelModelImpl, false, true);

		cacheUniqueFindersCache(commerceChannelAccountEntryRelModelImpl);

		if (isNew) {
			commerceChannelAccountEntryRel.setNew(false);
		}

		commerceChannelAccountEntryRel.resetOriginalValues();

		return commerceChannelAccountEntryRel;
	}

	/**
	 * Returns the commerce channel account entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce channel account entry rel
	 * @return the commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchChannelAccountEntryRelException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchByPrimaryKey(primaryKey);

		if (commerceChannelAccountEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchChannelAccountEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceChannelAccountEntryRel;
	}

	/**
	 * Returns the commerce channel account entry rel with the primary key or throws a <code>NoSuchChannelAccountEntryRelException</code> if it could not be found.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key of the commerce channel account entry rel
	 * @return the commerce channel account entry rel
	 * @throws NoSuchChannelAccountEntryRelException if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel findByPrimaryKey(
			long commerceChannelAccountEntryRelId)
		throws NoSuchChannelAccountEntryRelException {

		return findByPrimaryKey((Serializable)commerceChannelAccountEntryRelId);
	}

	/**
	 * Returns the commerce channel account entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce channel account entry rel
	 * @return the commerce channel account entry rel, or <code>null</code> if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				CommerceChannelAccountEntryRel.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel = null;

		Session session = null;

		try {
			session = openSession();

			commerceChannelAccountEntryRel =
				(CommerceChannelAccountEntryRel)session.get(
					CommerceChannelAccountEntryRelImpl.class, primaryKey);

			if (commerceChannelAccountEntryRel != null) {
				cacheResult(commerceChannelAccountEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return commerceChannelAccountEntryRel;
	}

	/**
	 * Returns the commerce channel account entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceChannelAccountEntryRelId the primary key of the commerce channel account entry rel
	 * @return the commerce channel account entry rel, or <code>null</code> if a commerce channel account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceChannelAccountEntryRel fetchByPrimaryKey(
		long commerceChannelAccountEntryRelId) {

		return fetchByPrimaryKey(
			(Serializable)commerceChannelAccountEntryRelId);
	}

	@Override
	public Map<Serializable, CommerceChannelAccountEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CommerceChannelAccountEntryRel.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceChannelAccountEntryRel> map =
			new HashMap<Serializable, CommerceChannelAccountEntryRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
				fetchByPrimaryKey(primaryKey);

			if (commerceChannelAccountEntryRel != null) {
				map.put(primaryKey, commerceChannelAccountEntryRel);
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

			for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
					(List<CommerceChannelAccountEntryRel>)query.list()) {

				map.put(
					commerceChannelAccountEntryRel.getPrimaryKeyObj(),
					commerceChannelAccountEntryRel);

				cacheResult(commerceChannelAccountEntryRel);
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
	 * Returns all the commerce channel account entry rels.
	 *
	 * @return the commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce channel account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @return the range of commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce channel account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceChannelAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce channel account entry rels
	 * @param end the upper bound of the range of commerce channel account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce channel account entry rels
	 */
	@Override
	public List<CommerceChannelAccountEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceChannelAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

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

		List<CommerceChannelAccountEntryRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceChannelAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL;

				sql = sql.concat(
					CommerceChannelAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceChannelAccountEntryRel>)QueryUtil.list(
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
	 * Removes all the commerce channel account entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				findAll()) {

			remove(commerceChannelAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce channel account entry rels.
	 *
	 * @return the number of commerce channel account entry rels
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceChannelAccountEntryRel.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL);

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
		return "CChannelAccountEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL;
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
		return CommerceChannelAccountEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CChannelAccountEntryRel";
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
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("accountEntryId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("commerceChannelId");
		ctStrictColumnNames.add("overrideEligibility");
		ctStrictColumnNames.add("priority");
		ctStrictColumnNames.add("type_");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CChannelAccountEntryRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {
				"accountEntryId", "classNameId", "classPK", "commerceChannelId",
				"type_"
			});
	}

	/**
	 * Initializes the commerce channel account entry rel persistence.
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

		_finderPathWithPaginationFindByAccountEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAccountEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"accountEntryId"}, true);

		_finderPathWithoutPaginationFindByAccountEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAccountEntryId",
			new String[] {Long.class.getName()},
			new String[] {"accountEntryId"}, true);

		_finderPathCountByAccountEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccountEntryId",
			new String[] {Long.class.getName()},
			new String[] {"accountEntryId"}, false);

		_finderPathWithPaginationFindByCommerceChannelId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceChannelId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceChannelId"}, true);

		_finderPathWithoutPaginationFindByCommerceChannelId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceChannelId", new String[] {Long.class.getName()},
			new String[] {"commerceChannelId"}, true);

		_finderPathCountByCommerceChannelId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceChannelId", new String[] {Long.class.getName()},
			new String[] {"commerceChannelId"}, false);

		_finderPathWithPaginationFindByA_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"accountEntryId", "type_"}, true);

		_finderPathWithoutPaginationFindByA_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"accountEntryId", "type_"}, true);

		_finderPathCountByA_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"accountEntryId", "type_"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathWithPaginationFindByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commerceChannelId", "type_"}, true);

		_finderPathWithoutPaginationFindByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"commerceChannelId", "type_"}, true);

		_finderPathCountByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"commerceChannelId", "type_"}, false);

		_finderPathWithPaginationFindByA_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"accountEntryId", "commerceChannelId", "type_"},
			true);

		_finderPathWithoutPaginationFindByA_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"accountEntryId", "commerceChannelId", "type_"},
			true);

		_finderPathCountByA_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"accountEntryId", "commerceChannelId", "type_"},
			false);

		_finderPathWithPaginationFindByC_C_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"classNameId", "classPK", "commerceChannelId", "type_"
			},
			true);

		_finderPathWithoutPaginationFindByC_C_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {
				"classNameId", "classPK", "commerceChannelId", "type_"
			},
			true);

		_finderPathCountByC_C_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {
				"classNameId", "classPK", "commerceChannelId", "type_"
			},
			false);

		_finderPathFetchByA_C_C_C_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByA_C_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"accountEntryId", "classNameId", "classPK", "commerceChannelId",
				"type_"
			},
			true);

		_finderPathCountByA_C_C_C_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_C_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"accountEntryId", "classNameId", "classPK", "commerceChannelId",
				"type_"
			},
			false);

		CommerceChannelAccountEntryRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceChannelAccountEntryRelUtil.setPersistence(null);

		entityCache.removeCache(
			CommerceChannelAccountEntryRelImpl.class.getName());
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

	private static final String _SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL =
		"SELECT commerceChannelAccountEntryRel FROM CommerceChannelAccountEntryRel commerceChannelAccountEntryRel";

	private static final String
		_SQL_SELECT_COMMERCECHANNELACCOUNTENTRYREL_WHERE =
			"SELECT commerceChannelAccountEntryRel FROM CommerceChannelAccountEntryRel commerceChannelAccountEntryRel WHERE ";

	private static final String _SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL =
		"SELECT COUNT(commerceChannelAccountEntryRel) FROM CommerceChannelAccountEntryRel commerceChannelAccountEntryRel";

	private static final String
		_SQL_COUNT_COMMERCECHANNELACCOUNTENTRYREL_WHERE =
			"SELECT COUNT(commerceChannelAccountEntryRel) FROM CommerceChannelAccountEntryRel commerceChannelAccountEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceChannelAccountEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceChannelAccountEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceChannelAccountEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelAccountEntryRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceChannelAccountEntryRelId", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}