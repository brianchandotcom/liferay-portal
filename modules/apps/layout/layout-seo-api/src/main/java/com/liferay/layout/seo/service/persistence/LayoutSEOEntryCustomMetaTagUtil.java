/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.service.persistence;

import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTag;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the layout seo entry custom meta tag service. This utility wraps <code>com.liferay.layout.seo.service.persistence.impl.LayoutSEOEntryCustomMetaTagPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntryCustomMetaTagPersistence
 * @generated
 */
public class LayoutSEOEntryCustomMetaTagUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

		getPersistence().clearCache(layoutSEOEntryCustomMetaTag);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, LayoutSEOEntryCustomMetaTag>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutSEOEntryCustomMetaTag> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutSEOEntryCustomMetaTag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutSEOEntryCustomMetaTag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutSEOEntryCustomMetaTag update(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

		return getPersistence().update(layoutSEOEntryCustomMetaTag);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutSEOEntryCustomMetaTag update(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag,
		ServiceContext serviceContext) {

		return getPersistence().update(
			layoutSEOEntryCustomMetaTag, serviceContext);
	}

	/**
	 * Returns all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @return the matching layout seo entry custom meta tags
	 */
	public static List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId) {

		return getPersistence().findByG_L(groupId, layoutSEOEntryId);
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
	public static List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId, int start, int end) {

		return getPersistence().findByG_L(
			groupId, layoutSEOEntryId, start, end);
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
	public static List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId, int start, int end,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator) {

		return getPersistence().findByG_L(
			groupId, layoutSEOEntryId, start, end, orderByComparator);
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
	public static List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId, int start, int end,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_L(
			groupId, layoutSEOEntryId, start, end, orderByComparator,
			useFinderCache);
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
	public static LayoutSEOEntryCustomMetaTag findByG_L_First(
			long groupId, long layoutSEOEntryId,
			OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator)
		throws com.liferay.layout.seo.exception.
			NoSuchEntryCustomMetaTagException {

		return getPersistence().findByG_L_First(
			groupId, layoutSEOEntryId, orderByComparator);
	}

	/**
	 * Returns the first layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry custom meta tag, or <code>null</code> if a matching layout seo entry custom meta tag could not be found
	 */
	public static LayoutSEOEntryCustomMetaTag fetchByG_L_First(
		long groupId, long layoutSEOEntryId,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator) {

		return getPersistence().fetchByG_L_First(
			groupId, layoutSEOEntryId, orderByComparator);
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
	public static LayoutSEOEntryCustomMetaTag findByG_L_Last(
			long groupId, long layoutSEOEntryId,
			OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator)
		throws com.liferay.layout.seo.exception.
			NoSuchEntryCustomMetaTagException {

		return getPersistence().findByG_L_Last(
			groupId, layoutSEOEntryId, orderByComparator);
	}

	/**
	 * Returns the last layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry custom meta tag, or <code>null</code> if a matching layout seo entry custom meta tag could not be found
	 */
	public static LayoutSEOEntryCustomMetaTag fetchByG_L_Last(
		long groupId, long layoutSEOEntryId,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator) {

		return getPersistence().fetchByG_L_Last(
			groupId, layoutSEOEntryId, orderByComparator);
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
	public static LayoutSEOEntryCustomMetaTag[] findByG_L_PrevAndNext(
			long layoutSEOEntryCustomMetaTagId, long groupId,
			long layoutSEOEntryId,
			OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator)
		throws com.liferay.layout.seo.exception.
			NoSuchEntryCustomMetaTagException {

		return getPersistence().findByG_L_PrevAndNext(
			layoutSEOEntryCustomMetaTagId, groupId, layoutSEOEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 */
	public static void removeByG_L(long groupId, long layoutSEOEntryId) {
		getPersistence().removeByG_L(groupId, layoutSEOEntryId);
	}

	/**
	 * Returns the number of layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @return the number of matching layout seo entry custom meta tags
	 */
	public static int countByG_L(long groupId, long layoutSEOEntryId) {
		return getPersistence().countByG_L(groupId, layoutSEOEntryId);
	}

	/**
	 * Caches the layout seo entry custom meta tag in the entity cache if it is enabled.
	 *
	 * @param layoutSEOEntryCustomMetaTag the layout seo entry custom meta tag
	 */
	public static void cacheResult(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

		getPersistence().cacheResult(layoutSEOEntryCustomMetaTag);
	}

	/**
	 * Caches the layout seo entry custom meta tags in the entity cache if it is enabled.
	 *
	 * @param layoutSEOEntryCustomMetaTags the layout seo entry custom meta tags
	 */
	public static void cacheResult(
		List<LayoutSEOEntryCustomMetaTag> layoutSEOEntryCustomMetaTags) {

		getPersistence().cacheResult(layoutSEOEntryCustomMetaTags);
	}

	/**
	 * Creates a new layout seo entry custom meta tag with the primary key. Does not add the layout seo entry custom meta tag to the database.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key for the new layout seo entry custom meta tag
	 * @return the new layout seo entry custom meta tag
	 */
	public static LayoutSEOEntryCustomMetaTag create(
		long layoutSEOEntryCustomMetaTagId) {

		return getPersistence().create(layoutSEOEntryCustomMetaTagId);
	}

	/**
	 * Removes the layout seo entry custom meta tag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag that was removed
	 * @throws NoSuchEntryCustomMetaTagException if a layout seo entry custom meta tag with the primary key could not be found
	 */
	public static LayoutSEOEntryCustomMetaTag remove(
			long layoutSEOEntryCustomMetaTagId)
		throws com.liferay.layout.seo.exception.
			NoSuchEntryCustomMetaTagException {

		return getPersistence().remove(layoutSEOEntryCustomMetaTagId);
	}

	public static LayoutSEOEntryCustomMetaTag updateImpl(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

		return getPersistence().updateImpl(layoutSEOEntryCustomMetaTag);
	}

	/**
	 * Returns the layout seo entry custom meta tag with the primary key or throws a <code>NoSuchEntryCustomMetaTagException</code> if it could not be found.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag
	 * @throws NoSuchEntryCustomMetaTagException if a layout seo entry custom meta tag with the primary key could not be found
	 */
	public static LayoutSEOEntryCustomMetaTag findByPrimaryKey(
			long layoutSEOEntryCustomMetaTagId)
		throws com.liferay.layout.seo.exception.
			NoSuchEntryCustomMetaTagException {

		return getPersistence().findByPrimaryKey(layoutSEOEntryCustomMetaTagId);
	}

	/**
	 * Returns the layout seo entry custom meta tag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag, or <code>null</code> if a layout seo entry custom meta tag with the primary key could not be found
	 */
	public static LayoutSEOEntryCustomMetaTag fetchByPrimaryKey(
		long layoutSEOEntryCustomMetaTagId) {

		return getPersistence().fetchByPrimaryKey(
			layoutSEOEntryCustomMetaTagId);
	}

	/**
	 * Returns all the layout seo entry custom meta tags.
	 *
	 * @return the layout seo entry custom meta tags
	 */
	public static List<LayoutSEOEntryCustomMetaTag> findAll() {
		return getPersistence().findAll();
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
	public static List<LayoutSEOEntryCustomMetaTag> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
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
	public static List<LayoutSEOEntryCustomMetaTag> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<LayoutSEOEntryCustomMetaTag> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOEntryCustomMetaTag> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the layout seo entry custom meta tags from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout seo entry custom meta tags.
	 *
	 * @return the number of layout seo entry custom meta tags
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutSEOEntryCustomMetaTagPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		LayoutSEOEntryCustomMetaTagPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile LayoutSEOEntryCustomMetaTagPersistence _persistence;

}