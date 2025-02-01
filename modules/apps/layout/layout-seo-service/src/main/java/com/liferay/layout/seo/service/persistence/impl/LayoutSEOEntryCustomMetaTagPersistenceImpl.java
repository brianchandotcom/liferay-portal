/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.service.persistence.impl;

import com.liferay.layout.seo.exception.NoSuchEntryCustomMetaTagException;
import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTag;
import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTagTable;
import com.liferay.layout.seo.model.impl.LayoutSEOEntryCustomMetaTagImpl;
import com.liferay.layout.seo.model.impl.LayoutSEOEntryCustomMetaTagModelImpl;
import com.liferay.layout.seo.service.persistence.LayoutSEOEntryCustomMetaTagPersistence;
import com.liferay.layout.seo.service.persistence.LayoutSEOEntryCustomMetaTagUtil;
import com.liferay.layout.seo.service.persistence.impl.constants.LayoutSEOPersistenceConstants;
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
 * The persistence implementation for the layout seo entry custom meta tag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LayoutSEOEntryCustomMetaTagPersistence.class)
public class LayoutSEOEntryCustomMetaTagPersistenceImpl
	extends BasePersistenceImpl<LayoutSEOEntryCustomMetaTag>
	implements LayoutSEOEntryCustomMetaTagPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutSEOEntryCustomMetaTagUtil</code> to access the layout seo entry custom meta tag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutSEOEntryCustomMetaTagImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByG_L;
	private FinderPath _finderPathWithoutPaginationFindByG_L;
	private FinderPath _finderPathCountByG_L;

	/**
	 * Returns all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @return the matching layout seo entry custom meta tags
	 */
	@Override
	public List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId) {

		return findByG_L(
			groupId, layoutSEOEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @return the range of matching layout seo entry custom meta tags
	 */
	@Override
	public List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId, int start, int end) {

		return findByG_L(groupId, layoutSEOEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo entry custom meta tags
	 */
	@Override
	public List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId, int start, int end,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator) {

		return findByG_L(
			groupId, layoutSEOEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo entry custom meta tags
	 */
	@Override
	public List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId, int start, int end,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					LayoutSEOEntryCustomMetaTag.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_L;
					finderArgs = new Object[] {groupId, layoutSEOEntryId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_L;
				finderArgs = new Object[] {
					groupId, layoutSEOEntryId, start, end, orderByComparator
				};
			}

			List<LayoutSEOEntryCustomMetaTag> list = null;

			if (useFinderCache) {
				list = (List<LayoutSEOEntryCustomMetaTag>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (LayoutSEOEntryCustomMetaTag
							layoutSEOEntryCustomMetaTag : list) {

						if ((groupId !=
								layoutSEOEntryCustomMetaTag.getGroupId()) ||
							(layoutSEOEntryId !=
								layoutSEOEntryCustomMetaTag.
									getLayoutSEOEntryId())) {

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

				sb.append(_SQL_SELECT_LAYOUTSEOENTRYCUSTOMMETATAG_WHERE);

				sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_L_LAYOUTSEOENTRYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(
						LayoutSEOEntryCustomMetaTagModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(layoutSEOEntryId);

					list = (List<LayoutSEOEntryCustomMetaTag>)QueryUtil.list(
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
	 * Returns the first layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry custom meta tag
	 * @throws NoSuchEntryCustomMetaTagException if a matching layout seo entry custom meta tag could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag findByG_L_First(
			long groupId, long layoutSEOEntryId,
			OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator)
		throws NoSuchEntryCustomMetaTagException {

		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag =
			fetchByG_L_First(groupId, layoutSEOEntryId, orderByComparator);

		if (layoutSEOEntryCustomMetaTag != null) {
			return layoutSEOEntryCustomMetaTag;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutSEOEntryId=");
		sb.append(layoutSEOEntryId);

		sb.append("}");

		throw new NoSuchEntryCustomMetaTagException(sb.toString());
	}

	/**
	 * Returns the first layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry custom meta tag, or <code>null</code> if a matching layout seo entry custom meta tag could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag fetchByG_L_First(
		long groupId, long layoutSEOEntryId,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator) {

		List<LayoutSEOEntryCustomMetaTag> list = findByG_L(
			groupId, layoutSEOEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry custom meta tag
	 * @throws NoSuchEntryCustomMetaTagException if a matching layout seo entry custom meta tag could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag findByG_L_Last(
			long groupId, long layoutSEOEntryId,
			OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator)
		throws NoSuchEntryCustomMetaTagException {

		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag =
			fetchByG_L_Last(groupId, layoutSEOEntryId, orderByComparator);

		if (layoutSEOEntryCustomMetaTag != null) {
			return layoutSEOEntryCustomMetaTag;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutSEOEntryId=");
		sb.append(layoutSEOEntryId);

		sb.append("}");

		throw new NoSuchEntryCustomMetaTagException(sb.toString());
	}

	/**
	 * Returns the last layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry custom meta tag, or <code>null</code> if a matching layout seo entry custom meta tag could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag fetchByG_L_Last(
		long groupId, long layoutSEOEntryId,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator) {

		int count = countByG_L(groupId, layoutSEOEntryId);

		if (count == 0) {
			return null;
		}

		List<LayoutSEOEntryCustomMetaTag> list = findByG_L(
			groupId, layoutSEOEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout seo entry custom meta tags before and after the current layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the current layout seo entry custom meta tag
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo entry custom meta tag
	 * @throws NoSuchEntryCustomMetaTagException if a layout seo entry custom meta tag with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag[] findByG_L_PrevAndNext(
			long layoutSEOEntryCustomMetaTagId, long groupId,
			long layoutSEOEntryId,
			OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator)
		throws NoSuchEntryCustomMetaTagException {

		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag =
			findByPrimaryKey(layoutSEOEntryCustomMetaTagId);

		Session session = null;

		try {
			session = openSession();

			LayoutSEOEntryCustomMetaTag[] array =
				new LayoutSEOEntryCustomMetaTagImpl[3];

			array[0] = getByG_L_PrevAndNext(
				session, layoutSEOEntryCustomMetaTag, groupId, layoutSEOEntryId,
				orderByComparator, true);

			array[1] = layoutSEOEntryCustomMetaTag;

			array[2] = getByG_L_PrevAndNext(
				session, layoutSEOEntryCustomMetaTag, groupId, layoutSEOEntryId,
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

	protected LayoutSEOEntryCustomMetaTag getByG_L_PrevAndNext(
		Session session,
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag, long groupId,
		long layoutSEOEntryId,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTSEOENTRYCUSTOMMETATAG_WHERE);

		sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LAYOUTSEOENTRYID_2);

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
			sb.append(LayoutSEOEntryCustomMetaTagModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(layoutSEOEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutSEOEntryCustomMetaTag)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutSEOEntryCustomMetaTag> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 */
	@Override
	public void removeByG_L(long groupId, long layoutSEOEntryId) {
		for (LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag :
				findByG_L(
					groupId, layoutSEOEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutSEOEntryCustomMetaTag);
		}
	}

	/**
	 * Returns the number of layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @return the number of matching layout seo entry custom meta tags
	 */
	@Override
	public int countByG_L(long groupId, long layoutSEOEntryId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					LayoutSEOEntryCustomMetaTag.class)) {

			FinderPath finderPath = _finderPathCountByG_L;

			Object[] finderArgs = new Object[] {groupId, layoutSEOEntryId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_LAYOUTSEOENTRYCUSTOMMETATAG_WHERE);

				sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_L_LAYOUTSEOENTRYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(layoutSEOEntryId);

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

	private static final String _FINDER_COLUMN_G_L_GROUPID_2 =
		"layoutSEOEntryCustomMetaTag.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_L_LAYOUTSEOENTRYID_2 =
		"layoutSEOEntryCustomMetaTag.layoutSEOEntryId = ?";

	public LayoutSEOEntryCustomMetaTagPersistenceImpl() {
		setModelClass(LayoutSEOEntryCustomMetaTag.class);

		setModelImplClass(LayoutSEOEntryCustomMetaTagImpl.class);
		setModelPKClass(long.class);

		setTable(LayoutSEOEntryCustomMetaTagTable.INSTANCE);
	}

	/**
	 * Caches the layout seo entry custom meta tag in the entity cache if it is enabled.
	 *
	 * @param layoutSEOEntryCustomMetaTag the layout seo entry custom meta tag
	 */
	@Override
	public void cacheResult(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					layoutSEOEntryCustomMetaTag.getCtCollectionId())) {

			entityCache.putResult(
				LayoutSEOEntryCustomMetaTagImpl.class,
				layoutSEOEntryCustomMetaTag.getPrimaryKey(),
				layoutSEOEntryCustomMetaTag);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the layout seo entry custom meta tags in the entity cache if it is enabled.
	 *
	 * @param layoutSEOEntryCustomMetaTags the layout seo entry custom meta tags
	 */
	@Override
	public void cacheResult(
		List<LayoutSEOEntryCustomMetaTag> layoutSEOEntryCustomMetaTags) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (layoutSEOEntryCustomMetaTags.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag :
				layoutSEOEntryCustomMetaTags) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						layoutSEOEntryCustomMetaTag.getCtCollectionId())) {

				if (entityCache.getResult(
						LayoutSEOEntryCustomMetaTagImpl.class,
						layoutSEOEntryCustomMetaTag.getPrimaryKey()) == null) {

					cacheResult(layoutSEOEntryCustomMetaTag);
				}
			}
		}
	}

	/**
	 * Clears the cache for all layout seo entry custom meta tags.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutSEOEntryCustomMetaTagImpl.class);

		finderCache.clearCache(LayoutSEOEntryCustomMetaTagImpl.class);
	}

	/**
	 * Clears the cache for the layout seo entry custom meta tag.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

		entityCache.removeResult(
			LayoutSEOEntryCustomMetaTagImpl.class, layoutSEOEntryCustomMetaTag);
	}

	@Override
	public void clearCache(
		List<LayoutSEOEntryCustomMetaTag> layoutSEOEntryCustomMetaTags) {

		for (LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag :
				layoutSEOEntryCustomMetaTags) {

			entityCache.removeResult(
				LayoutSEOEntryCustomMetaTagImpl.class,
				layoutSEOEntryCustomMetaTag);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(LayoutSEOEntryCustomMetaTagImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LayoutSEOEntryCustomMetaTagImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new layout seo entry custom meta tag with the primary key. Does not add the layout seo entry custom meta tag to the database.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key for the new layout seo entry custom meta tag
	 * @return the new layout seo entry custom meta tag
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag create(
		long layoutSEOEntryCustomMetaTagId) {

		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag =
			new LayoutSEOEntryCustomMetaTagImpl();

		layoutSEOEntryCustomMetaTag.setNew(true);
		layoutSEOEntryCustomMetaTag.setPrimaryKey(
			layoutSEOEntryCustomMetaTagId);

		layoutSEOEntryCustomMetaTag.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return layoutSEOEntryCustomMetaTag;
	}

	/**
	 * Removes the layout seo entry custom meta tag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag that was removed
	 * @throws NoSuchEntryCustomMetaTagException if a layout seo entry custom meta tag with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag remove(
			long layoutSEOEntryCustomMetaTagId)
		throws NoSuchEntryCustomMetaTagException {

		return remove((Serializable)layoutSEOEntryCustomMetaTagId);
	}

	/**
	 * Removes the layout seo entry custom meta tag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag that was removed
	 * @throws NoSuchEntryCustomMetaTagException if a layout seo entry custom meta tag with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag remove(Serializable primaryKey)
		throws NoSuchEntryCustomMetaTagException {

		Session session = null;

		try {
			session = openSession();

			LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag =
				(LayoutSEOEntryCustomMetaTag)session.get(
					LayoutSEOEntryCustomMetaTagImpl.class, primaryKey);

			if (layoutSEOEntryCustomMetaTag == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryCustomMetaTagException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutSEOEntryCustomMetaTag);
		}
		catch (NoSuchEntryCustomMetaTagException noSuchEntityException) {
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
	protected LayoutSEOEntryCustomMetaTag removeImpl(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutSEOEntryCustomMetaTag)) {
				layoutSEOEntryCustomMetaTag =
					(LayoutSEOEntryCustomMetaTag)session.get(
						LayoutSEOEntryCustomMetaTagImpl.class,
						layoutSEOEntryCustomMetaTag.getPrimaryKeyObj());
			}

			if ((layoutSEOEntryCustomMetaTag != null) &&
				ctPersistenceHelper.isRemove(layoutSEOEntryCustomMetaTag)) {

				session.delete(layoutSEOEntryCustomMetaTag);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutSEOEntryCustomMetaTag != null) {
			clearCache(layoutSEOEntryCustomMetaTag);
		}

		return layoutSEOEntryCustomMetaTag;
	}

	@Override
	public LayoutSEOEntryCustomMetaTag updateImpl(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

		boolean isNew = layoutSEOEntryCustomMetaTag.isNew();

		if (!(layoutSEOEntryCustomMetaTag instanceof
				LayoutSEOEntryCustomMetaTagModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					layoutSEOEntryCustomMetaTag.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutSEOEntryCustomMetaTag);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutSEOEntryCustomMetaTag proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutSEOEntryCustomMetaTag implementation " +
					layoutSEOEntryCustomMetaTag.getClass());
		}

		LayoutSEOEntryCustomMetaTagModelImpl
			layoutSEOEntryCustomMetaTagModelImpl =
				(LayoutSEOEntryCustomMetaTagModelImpl)
					layoutSEOEntryCustomMetaTag;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(layoutSEOEntryCustomMetaTag)) {
				if (!isNew) {
					session.evict(
						LayoutSEOEntryCustomMetaTagImpl.class,
						layoutSEOEntryCustomMetaTag.getPrimaryKeyObj());
				}

				session.save(layoutSEOEntryCustomMetaTag);
			}
			else {
				layoutSEOEntryCustomMetaTag =
					(LayoutSEOEntryCustomMetaTag)session.merge(
						layoutSEOEntryCustomMetaTag);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			LayoutSEOEntryCustomMetaTagImpl.class,
			layoutSEOEntryCustomMetaTagModelImpl, false, true);

		if (isNew) {
			layoutSEOEntryCustomMetaTag.setNew(false);
		}

		layoutSEOEntryCustomMetaTag.resetOriginalValues();

		return layoutSEOEntryCustomMetaTag;
	}

	/**
	 * Returns the layout seo entry custom meta tag with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag
	 * @throws NoSuchEntryCustomMetaTagException if a layout seo entry custom meta tag with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryCustomMetaTagException {

		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag =
			fetchByPrimaryKey(primaryKey);

		if (layoutSEOEntryCustomMetaTag == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryCustomMetaTagException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutSEOEntryCustomMetaTag;
	}

	/**
	 * Returns the layout seo entry custom meta tag with the primary key or throws a <code>NoSuchEntryCustomMetaTagException</code> if it could not be found.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag
	 * @throws NoSuchEntryCustomMetaTagException if a layout seo entry custom meta tag with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag findByPrimaryKey(
			long layoutSEOEntryCustomMetaTagId)
		throws NoSuchEntryCustomMetaTagException {

		return findByPrimaryKey((Serializable)layoutSEOEntryCustomMetaTagId);
	}

	/**
	 * Returns the layout seo entry custom meta tag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag, or <code>null</code> if a layout seo entry custom meta tag with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				LayoutSEOEntryCustomMetaTag.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag =
			(LayoutSEOEntryCustomMetaTag)entityCache.getResult(
				LayoutSEOEntryCustomMetaTagImpl.class, primaryKey);

		if (layoutSEOEntryCustomMetaTag != null) {
			return layoutSEOEntryCustomMetaTag;
		}

		Session session = null;

		try {
			session = openSession();

			layoutSEOEntryCustomMetaTag =
				(LayoutSEOEntryCustomMetaTag)session.get(
					LayoutSEOEntryCustomMetaTagImpl.class, primaryKey);

			if (layoutSEOEntryCustomMetaTag != null) {
				cacheResult(layoutSEOEntryCustomMetaTag);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return layoutSEOEntryCustomMetaTag;
	}

	/**
	 * Returns the layout seo entry custom meta tag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag, or <code>null</code> if a layout seo entry custom meta tag with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntryCustomMetaTag fetchByPrimaryKey(
		long layoutSEOEntryCustomMetaTagId) {

		return fetchByPrimaryKey((Serializable)layoutSEOEntryCustomMetaTagId);
	}

	@Override
	public Map<Serializable, LayoutSEOEntryCustomMetaTag> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				LayoutSEOEntryCustomMetaTag.class)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutSEOEntryCustomMetaTag> map =
			new HashMap<Serializable, LayoutSEOEntryCustomMetaTag>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag =
				fetchByPrimaryKey(primaryKey);

			if (layoutSEOEntryCustomMetaTag != null) {
				map.put(primaryKey, layoutSEOEntryCustomMetaTag);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						LayoutSEOEntryCustomMetaTag.class, primaryKey)) {

				LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag =
					(LayoutSEOEntryCustomMetaTag)entityCache.getResult(
						LayoutSEOEntryCustomMetaTagImpl.class, primaryKey);

				if (layoutSEOEntryCustomMetaTag == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, layoutSEOEntryCustomMetaTag);
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

			for (LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag :
					(List<LayoutSEOEntryCustomMetaTag>)query.list()) {

				map.put(
					layoutSEOEntryCustomMetaTag.getPrimaryKeyObj(),
					layoutSEOEntryCustomMetaTag);

				cacheResult(layoutSEOEntryCustomMetaTag);
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
	 * Returns all the layout seo entry custom meta tags.
	 *
	 * @return the layout seo entry custom meta tags
	 */
	@Override
	public List<LayoutSEOEntryCustomMetaTag> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo entry custom meta tags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @return the range of layout seo entry custom meta tags
	 */
	@Override
	public List<LayoutSEOEntryCustomMetaTag> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo entry custom meta tags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo entry custom meta tags
	 */
	@Override
	public List<LayoutSEOEntryCustomMetaTag> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo entry custom meta tags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo entry custom meta tags
	 */
	@Override
	public List<LayoutSEOEntryCustomMetaTag> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					LayoutSEOEntryCustomMetaTag.class)) {

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

			List<LayoutSEOEntryCustomMetaTag> list = null;

			if (useFinderCache) {
				list = (List<LayoutSEOEntryCustomMetaTag>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_LAYOUTSEOENTRYCUSTOMMETATAG);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_LAYOUTSEOENTRYCUSTOMMETATAG;

					sql = sql.concat(
						LayoutSEOEntryCustomMetaTagModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<LayoutSEOEntryCustomMetaTag>)QueryUtil.list(
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
	 * Removes all the layout seo entry custom meta tags from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag :
				findAll()) {

			remove(layoutSEOEntryCustomMetaTag);
		}
	}

	/**
	 * Returns the number of layout seo entry custom meta tags.
	 *
	 * @return the number of layout seo entry custom meta tags
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					LayoutSEOEntryCustomMetaTag.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_LAYOUTSEOENTRYCUSTOMMETATAG);

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
		return "layoutSEOEntryCustomMetaTagId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTSEOENTRYCUSTOMMETATAG;
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
		return LayoutSEOEntryCustomMetaTagModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "LayoutSEOEntryCustomMetaTag";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("layoutSEOEntryId");
		ctMergeColumnNames.add("content");
		ctMergeColumnNames.add("property");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("layoutSEOEntryCustomMetaTagId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the layout seo entry custom meta tag persistence.
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

		_finderPathWithPaginationFindByG_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "layoutSEOEntryId"}, true);

		_finderPathWithoutPaginationFindByG_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "layoutSEOEntryId"}, true);

		_finderPathCountByG_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_L",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "layoutSEOEntryId"}, false);

		LayoutSEOEntryCustomMetaTagUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		LayoutSEOEntryCustomMetaTagUtil.setPersistence(null);

		entityCache.removeCache(
			LayoutSEOEntryCustomMetaTagImpl.class.getName());
	}

	@Override
	@Reference(
		target = LayoutSEOPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = LayoutSEOPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = LayoutSEOPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_LAYOUTSEOENTRYCUSTOMMETATAG =
		"SELECT layoutSEOEntryCustomMetaTag FROM LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag";

	private static final String _SQL_SELECT_LAYOUTSEOENTRYCUSTOMMETATAG_WHERE =
		"SELECT layoutSEOEntryCustomMetaTag FROM LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag WHERE ";

	private static final String _SQL_COUNT_LAYOUTSEOENTRYCUSTOMMETATAG =
		"SELECT COUNT(layoutSEOEntryCustomMetaTag) FROM LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag";

	private static final String _SQL_COUNT_LAYOUTSEOENTRYCUSTOMMETATAG_WHERE =
		"SELECT COUNT(layoutSEOEntryCustomMetaTag) FROM LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"layoutSEOEntryCustomMetaTag.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutSEOEntryCustomMetaTag exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutSEOEntryCustomMetaTag exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEOEntryCustomMetaTagPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}