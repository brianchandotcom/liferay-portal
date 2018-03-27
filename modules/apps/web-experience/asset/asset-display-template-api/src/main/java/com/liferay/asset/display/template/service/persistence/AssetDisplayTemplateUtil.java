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

package com.liferay.asset.display.template.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.display.template.model.AssetDisplayTemplate;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the asset display template service. This utility wraps {@link com.liferay.asset.display.template.service.persistence.impl.AssetDisplayTemplatePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayTemplatePersistence
 * @see com.liferay.asset.display.template.service.persistence.impl.AssetDisplayTemplatePersistenceImpl
 * @generated
 */
@ProviderType
public class AssetDisplayTemplateUtil {
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
	public static void clearCache(AssetDisplayTemplate assetDisplayTemplate) {
		getPersistence().clearCache(assetDisplayTemplate);
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
	public static List<AssetDisplayTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetDisplayTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetDisplayTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetDisplayTemplate update(
		AssetDisplayTemplate assetDisplayTemplate) {
		return getPersistence().update(assetDisplayTemplate);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetDisplayTemplate update(
		AssetDisplayTemplate assetDisplayTemplate, ServiceContext serviceContext) {
		return getPersistence().update(assetDisplayTemplate, serviceContext);
	}

	/**
	* Returns all the asset display templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset display templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @return the range of matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset display templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset display templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate findByGroupId_First(long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate fetchByGroupId_First(long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate findByGroupId_Last(long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate fetchByGroupId_Last(long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the asset display templates before and after the current asset display template in the ordered set where groupId = &#63;.
	*
	* @param assetDisplayTemplateId the primary key of the current asset display template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display template
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public static AssetDisplayTemplate[] findByGroupId_PrevAndNext(
		long assetDisplayTemplateId, long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(assetDisplayTemplateId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the asset display templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset display templates that the user has permission to view
	*/
	public static List<AssetDisplayTemplate> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset display templates that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @return the range of matching asset display templates that the user has permission to view
	*/
	public static List<AssetDisplayTemplate> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset display templates that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset display templates that the user has permission to view
	*/
	public static List<AssetDisplayTemplate> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the asset display templates before and after the current asset display template in the ordered set of asset display templates that the user has permission to view where groupId = &#63;.
	*
	* @param assetDisplayTemplateId the primary key of the current asset display template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display template
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public static AssetDisplayTemplate[] filterFindByGroupId_PrevAndNext(
		long assetDisplayTemplateId, long groupId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(assetDisplayTemplateId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the asset display templates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of asset display templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset display templates
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of asset display templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset display templates that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the asset display templates where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByG_LikeN(long groupId,
		java.lang.String name) {
		return getPersistence().findByG_LikeN(groupId, name);
	}

	/**
	* Returns a range of all the asset display templates where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @return the range of matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end) {
		return getPersistence().findByG_LikeN(groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the asset display templates where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_LikeN(groupId, name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset display templates where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LikeN(groupId, name, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate findByG_LikeN_First(long groupId,
		java.lang.String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence()
				   .findByG_LikeN_First(groupId, name, orderByComparator);
	}

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate fetchByG_LikeN_First(long groupId,
		java.lang.String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeN_First(groupId, name, orderByComparator);
	}

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate findByG_LikeN_Last(long groupId,
		java.lang.String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence()
				   .findByG_LikeN_Last(groupId, name, orderByComparator);
	}

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate fetchByG_LikeN_Last(long groupId,
		java.lang.String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeN_Last(groupId, name, orderByComparator);
	}

	/**
	* Returns the asset display templates before and after the current asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param assetDisplayTemplateId the primary key of the current asset display template
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display template
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public static AssetDisplayTemplate[] findByG_LikeN_PrevAndNext(
		long assetDisplayTemplateId, long groupId, java.lang.String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence()
				   .findByG_LikeN_PrevAndNext(assetDisplayTemplateId, groupId,
			name, orderByComparator);
	}

	/**
	* Returns all the asset display templates that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset display templates that the user has permission to view
	*/
	public static List<AssetDisplayTemplate> filterFindByG_LikeN(long groupId,
		java.lang.String name) {
		return getPersistence().filterFindByG_LikeN(groupId, name);
	}

	/**
	* Returns a range of all the asset display templates that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @return the range of matching asset display templates that the user has permission to view
	*/
	public static List<AssetDisplayTemplate> filterFindByG_LikeN(long groupId,
		java.lang.String name, int start, int end) {
		return getPersistence().filterFindByG_LikeN(groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the asset display templates that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset display templates that the user has permission to view
	*/
	public static List<AssetDisplayTemplate> filterFindByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LikeN(groupId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns the asset display templates before and after the current asset display template in the ordered set of asset display templates that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param assetDisplayTemplateId the primary key of the current asset display template
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display template
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public static AssetDisplayTemplate[] filterFindByG_LikeN_PrevAndNext(
		long assetDisplayTemplateId, long groupId, java.lang.String name,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence()
				   .filterFindByG_LikeN_PrevAndNext(assetDisplayTemplateId,
			groupId, name, orderByComparator);
	}

	/**
	* Removes all the asset display templates where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public static void removeByG_LikeN(long groupId, java.lang.String name) {
		getPersistence().removeByG_LikeN(groupId, name);
	}

	/**
	* Returns the number of asset display templates where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset display templates
	*/
	public static int countByG_LikeN(long groupId, java.lang.String name) {
		return getPersistence().countByG_LikeN(groupId, name);
	}

	/**
	* Returns the number of asset display templates that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset display templates that the user has permission to view
	*/
	public static int filterCountByG_LikeN(long groupId, java.lang.String name) {
		return getPersistence().filterCountByG_LikeN(groupId, name);
	}

	/**
	* Returns all the asset display templates where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByG_C(long groupId,
		long classNameId) {
		return getPersistence().findByG_C(groupId, classNameId);
	}

	/**
	* Returns a range of all the asset display templates where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @return the range of matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByG_C(long groupId,
		long classNameId, int start, int end) {
		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the asset display templates where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByG_C(long groupId,
		long classNameId, int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset display templates where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset display templates
	*/
	public static List<AssetDisplayTemplate> findByG_C(long groupId,
		long classNameId, int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate findByG_C_First(long groupId,
		long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence()
				   .findByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate fetchByG_C_First(long groupId,
		long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate findByG_C_Last(long groupId,
		long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence()
				   .findByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public static AssetDisplayTemplate fetchByG_C_Last(long groupId,
		long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the asset display templates before and after the current asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param assetDisplayTemplateId the primary key of the current asset display template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display template
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public static AssetDisplayTemplate[] findByG_C_PrevAndNext(
		long assetDisplayTemplateId, long groupId, long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence()
				   .findByG_C_PrevAndNext(assetDisplayTemplateId, groupId,
			classNameId, orderByComparator);
	}

	/**
	* Returns all the asset display templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching asset display templates that the user has permission to view
	*/
	public static List<AssetDisplayTemplate> filterFindByG_C(long groupId,
		long classNameId) {
		return getPersistence().filterFindByG_C(groupId, classNameId);
	}

	/**
	* Returns a range of all the asset display templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @return the range of matching asset display templates that the user has permission to view
	*/
	public static List<AssetDisplayTemplate> filterFindByG_C(long groupId,
		long classNameId, int start, int end) {
		return getPersistence().filterFindByG_C(groupId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the asset display templates that the user has permissions to view where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset display templates that the user has permission to view
	*/
	public static List<AssetDisplayTemplate> filterFindByG_C(long groupId,
		long classNameId, int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns the asset display templates before and after the current asset display template in the ordered set of asset display templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param assetDisplayTemplateId the primary key of the current asset display template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display template
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public static AssetDisplayTemplate[] filterFindByG_C_PrevAndNext(
		long assetDisplayTemplateId, long groupId, long classNameId,
		OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence()
				   .filterFindByG_C_PrevAndNext(assetDisplayTemplateId,
			groupId, classNameId, orderByComparator);
	}

	/**
	* Removes all the asset display templates where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public static void removeByG_C(long groupId, long classNameId) {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	/**
	* Returns the number of asset display templates where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching asset display templates
	*/
	public static int countByG_C(long groupId, long classNameId) {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	/**
	* Returns the number of asset display templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching asset display templates that the user has permission to view
	*/
	public static int filterCountByG_C(long groupId, long classNameId) {
		return getPersistence().filterCountByG_C(groupId, classNameId);
	}

	/**
	* Caches the asset display template in the entity cache if it is enabled.
	*
	* @param assetDisplayTemplate the asset display template
	*/
	public static void cacheResult(AssetDisplayTemplate assetDisplayTemplate) {
		getPersistence().cacheResult(assetDisplayTemplate);
	}

	/**
	* Caches the asset display templates in the entity cache if it is enabled.
	*
	* @param assetDisplayTemplates the asset display templates
	*/
	public static void cacheResult(
		List<AssetDisplayTemplate> assetDisplayTemplates) {
		getPersistence().cacheResult(assetDisplayTemplates);
	}

	/**
	* Creates a new asset display template with the primary key. Does not add the asset display template to the database.
	*
	* @param assetDisplayTemplateId the primary key for the new asset display template
	* @return the new asset display template
	*/
	public static AssetDisplayTemplate create(long assetDisplayTemplateId) {
		return getPersistence().create(assetDisplayTemplateId);
	}

	/**
	* Removes the asset display template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayTemplateId the primary key of the asset display template
	* @return the asset display template that was removed
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public static AssetDisplayTemplate remove(long assetDisplayTemplateId)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence().remove(assetDisplayTemplateId);
	}

	public static AssetDisplayTemplate updateImpl(
		AssetDisplayTemplate assetDisplayTemplate) {
		return getPersistence().updateImpl(assetDisplayTemplate);
	}

	/**
	* Returns the asset display template with the primary key or throws a {@link NoSuchDisplayTemplateException} if it could not be found.
	*
	* @param assetDisplayTemplateId the primary key of the asset display template
	* @return the asset display template
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public static AssetDisplayTemplate findByPrimaryKey(
		long assetDisplayTemplateId)
		throws com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException {
		return getPersistence().findByPrimaryKey(assetDisplayTemplateId);
	}

	/**
	* Returns the asset display template with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetDisplayTemplateId the primary key of the asset display template
	* @return the asset display template, or <code>null</code> if a asset display template with the primary key could not be found
	*/
	public static AssetDisplayTemplate fetchByPrimaryKey(
		long assetDisplayTemplateId) {
		return getPersistence().fetchByPrimaryKey(assetDisplayTemplateId);
	}

	public static java.util.Map<java.io.Serializable, AssetDisplayTemplate> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset display templates.
	*
	* @return the asset display templates
	*/
	public static List<AssetDisplayTemplate> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset display templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @return the range of asset display templates
	*/
	public static List<AssetDisplayTemplate> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset display templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset display templates
	*/
	public static List<AssetDisplayTemplate> findAll(int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset display templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display templates
	* @param end the upper bound of the range of asset display templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset display templates
	*/
	public static List<AssetDisplayTemplate> findAll(int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset display templates from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset display templates.
	*
	* @return the number of asset display templates
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetDisplayTemplatePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetDisplayTemplatePersistence, AssetDisplayTemplatePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetDisplayTemplatePersistence.class);

		ServiceTracker<AssetDisplayTemplatePersistence, AssetDisplayTemplatePersistence> serviceTracker =
			new ServiceTracker<AssetDisplayTemplatePersistence, AssetDisplayTemplatePersistence>(bundle.getBundleContext(),
				AssetDisplayTemplatePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}