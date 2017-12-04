/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.html.preview.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.html.preview.model.HtmlPreview;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the html preview service. This utility wraps {@link com.liferay.html.preview.service.persistence.impl.HtmlPreviewPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewPersistence
 * @see com.liferay.html.preview.service.persistence.impl.HtmlPreviewPersistenceImpl
 * @generated
 */
@ProviderType
public class HtmlPreviewUtil {
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
	public static void clearCache(HtmlPreview htmlPreview) {
		getPersistence().clearCache(htmlPreview);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<HtmlPreview> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<HtmlPreview> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<HtmlPreview> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<HtmlPreview> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static HtmlPreview update(HtmlPreview htmlPreview) {
		return getPersistence().update(htmlPreview);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static HtmlPreview update(HtmlPreview htmlPreview,
		ServiceContext serviceContext) {
		return getPersistence().update(htmlPreview, serviceContext);
	}

	/**
	* Returns the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchHtmlPreviewException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching html preview
	* @throws NoSuchHtmlPreviewException if a matching html preview could not be found
	*/
	public static HtmlPreview findByG_C_C(long groupId, long classNameId,
		long classPK)
		throws com.liferay.html.preview.exception.NoSuchHtmlPreviewException {
		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching html preview, or <code>null</code> if a matching html preview could not be found
	*/
	public static HtmlPreview fetchByG_C_C(long groupId, long classNameId,
		long classPK) {
		return getPersistence().fetchByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching html preview, or <code>null</code> if a matching html preview could not be found
	*/
	public static HtmlPreview fetchByG_C_C(long groupId, long classNameId,
		long classPK, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C(groupId, classNameId, classPK,
			retrieveFromCache);
	}

	/**
	* Removes the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the html preview that was removed
	*/
	public static HtmlPreview removeByG_C_C(long groupId, long classNameId,
		long classPK)
		throws com.liferay.html.preview.exception.NoSuchHtmlPreviewException {
		return getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of html previews where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching html previews
	*/
	public static int countByG_C_C(long groupId, long classNameId, long classPK) {
		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Caches the html preview in the entity cache if it is enabled.
	*
	* @param htmlPreview the html preview
	*/
	public static void cacheResult(HtmlPreview htmlPreview) {
		getPersistence().cacheResult(htmlPreview);
	}

	/**
	* Caches the html previews in the entity cache if it is enabled.
	*
	* @param htmlPreviews the html previews
	*/
	public static void cacheResult(List<HtmlPreview> htmlPreviews) {
		getPersistence().cacheResult(htmlPreviews);
	}

	/**
	* Creates a new html preview with the primary key. Does not add the html preview to the database.
	*
	* @param htmlPreviewId the primary key for the new html preview
	* @return the new html preview
	*/
	public static HtmlPreview create(long htmlPreviewId) {
		return getPersistence().create(htmlPreviewId);
	}

	/**
	* Removes the html preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param htmlPreviewId the primary key of the html preview
	* @return the html preview that was removed
	* @throws NoSuchHtmlPreviewException if a html preview with the primary key could not be found
	*/
	public static HtmlPreview remove(long htmlPreviewId)
		throws com.liferay.html.preview.exception.NoSuchHtmlPreviewException {
		return getPersistence().remove(htmlPreviewId);
	}

	public static HtmlPreview updateImpl(HtmlPreview htmlPreview) {
		return getPersistence().updateImpl(htmlPreview);
	}

	/**
	* Returns the html preview with the primary key or throws a {@link NoSuchHtmlPreviewException} if it could not be found.
	*
	* @param htmlPreviewId the primary key of the html preview
	* @return the html preview
	* @throws NoSuchHtmlPreviewException if a html preview with the primary key could not be found
	*/
	public static HtmlPreview findByPrimaryKey(long htmlPreviewId)
		throws com.liferay.html.preview.exception.NoSuchHtmlPreviewException {
		return getPersistence().findByPrimaryKey(htmlPreviewId);
	}

	/**
	* Returns the html preview with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param htmlPreviewId the primary key of the html preview
	* @return the html preview, or <code>null</code> if a html preview with the primary key could not be found
	*/
	public static HtmlPreview fetchByPrimaryKey(long htmlPreviewId) {
		return getPersistence().fetchByPrimaryKey(htmlPreviewId);
	}

	public static java.util.Map<java.io.Serializable, HtmlPreview> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the html previews.
	*
	* @return the html previews
	*/
	public static List<HtmlPreview> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the html previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of html previews
	* @param end the upper bound of the range of html previews (not inclusive)
	* @return the range of html previews
	*/
	public static List<HtmlPreview> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the html previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of html previews
	* @param end the upper bound of the range of html previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of html previews
	*/
	public static List<HtmlPreview> findAll(int start, int end,
		OrderByComparator<HtmlPreview> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the html previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of html previews
	* @param end the upper bound of the range of html previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of html previews
	*/
	public static List<HtmlPreview> findAll(int start, int end,
		OrderByComparator<HtmlPreview> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the html previews from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of html previews.
	*
	* @return the number of html previews
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static HtmlPreviewPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<HtmlPreviewPersistence, HtmlPreviewPersistence> _serviceTracker =
		ServiceTrackerFactory.open(HtmlPreviewPersistence.class);
}