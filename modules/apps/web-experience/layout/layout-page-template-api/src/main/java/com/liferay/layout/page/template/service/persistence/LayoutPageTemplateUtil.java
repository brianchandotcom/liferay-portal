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

package com.liferay.layout.page.template.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.model.LayoutPageTemplate;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the layout page template service. This utility wraps {@link com.liferay.layout.page.template.service.persistence.impl.LayoutPageTemplatePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplatePersistence
 * @see com.liferay.layout.page.template.service.persistence.impl.LayoutPageTemplatePersistenceImpl
 * @generated
 */
@ProviderType
public class LayoutPageTemplateUtil {
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
	public static void clearCache(LayoutPageTemplate layoutPageTemplate) {
		getPersistence().clearCache(layoutPageTemplate);
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
	public static List<LayoutPageTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutPageTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutPageTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutPageTemplate update(
		LayoutPageTemplate layoutPageTemplate) {
		return getPersistence().update(layoutPageTemplate);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutPageTemplate update(
		LayoutPageTemplate layoutPageTemplate, ServiceContext serviceContext) {
		return getPersistence().update(layoutPageTemplate, serviceContext);
	}

	/**
	* Returns all the layout page templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the layout page templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @return the range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the layout page templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout page templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate findByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate findByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the layout page templates before and after the current layout page template in the ordered set where groupId = &#63;.
	*
	* @param layoutPageTemplateId the primary key of the current layout page template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public static LayoutPageTemplate[] findByGroupId_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(layoutPageTemplateId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the layout page templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page templates that the user has permission to view
	*/
	public static List<LayoutPageTemplate> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the layout page templates that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @return the range of matching layout page templates that the user has permission to view
	*/
	public static List<LayoutPageTemplate> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the layout page templates that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page templates that the user has permission to view
	*/
	public static List<LayoutPageTemplate> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the layout page templates before and after the current layout page template in the ordered set of layout page templates that the user has permission to view where groupId = &#63;.
	*
	* @param layoutPageTemplateId the primary key of the current layout page template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public static LayoutPageTemplate[] filterFindByGroupId_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(layoutPageTemplateId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the layout page templates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of layout page templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page templates
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of layout page templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page templates that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the layout page templates where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId) {
		return getPersistence()
				   .findByLayoutPageTemplateFolderId(layoutPageTemplateFolderId);
	}

	/**
	* Returns a range of all the layout page templates where layoutPageTemplateFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @return the range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId, int start, int end) {
		return getPersistence()
				   .findByLayoutPageTemplateFolderId(layoutPageTemplateFolderId,
			start, end);
	}

	/**
	* Returns an ordered range of all the layout page templates where layoutPageTemplateFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .findByLayoutPageTemplateFolderId(layoutPageTemplateFolderId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout page templates where layoutPageTemplateFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutPageTemplateFolderId(layoutPageTemplateFolderId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate findByLayoutPageTemplateFolderId_First(
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .findByLayoutPageTemplateFolderId_First(layoutPageTemplateFolderId,
			orderByComparator);
	}

	/**
	* Returns the first layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate fetchByLayoutPageTemplateFolderId_First(
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutPageTemplateFolderId_First(layoutPageTemplateFolderId,
			orderByComparator);
	}

	/**
	* Returns the last layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate findByLayoutPageTemplateFolderId_Last(
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .findByLayoutPageTemplateFolderId_Last(layoutPageTemplateFolderId,
			orderByComparator);
	}

	/**
	* Returns the last layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate fetchByLayoutPageTemplateFolderId_Last(
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutPageTemplateFolderId_Last(layoutPageTemplateFolderId,
			orderByComparator);
	}

	/**
	* Returns the layout page templates before and after the current layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateId the primary key of the current layout page template
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public static LayoutPageTemplate[] findByLayoutPageTemplateFolderId_PrevAndNext(
		long layoutPageTemplateId, long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .findByLayoutPageTemplateFolderId_PrevAndNext(layoutPageTemplateId,
			layoutPageTemplateFolderId, orderByComparator);
	}

	/**
	* Removes all the layout page templates where layoutPageTemplateFolderId = &#63; from the database.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	*/
	public static void removeByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId) {
		getPersistence()
			.removeByLayoutPageTemplateFolderId(layoutPageTemplateFolderId);
	}

	/**
	* Returns the number of layout page templates where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the number of matching layout page templates
	*/
	public static int countByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId) {
		return getPersistence()
				   .countByLayoutPageTemplateFolderId(layoutPageTemplateFolderId);
	}

	/**
	* Returns all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId) {
		return getPersistence()
				   .findByG_LPTFI(groupId, layoutPageTemplateFolderId);
	}

	/**
	* Returns a range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @return the range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId, int start, int end) {
		return getPersistence()
				   .findByG_LPTFI(groupId, layoutPageTemplateFolderId, start,
			end);
	}

	/**
	* Returns an ordered range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_LPTFI(groupId, layoutPageTemplateFolderId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LPTFI(groupId, layoutPageTemplateFolderId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate findByG_LPTFI_First(long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .findByG_LPTFI_First(groupId, layoutPageTemplateFolderId,
			orderByComparator);
	}

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate fetchByG_LPTFI_First(long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_LPTFI_First(groupId, layoutPageTemplateFolderId,
			orderByComparator);
	}

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate findByG_LPTFI_Last(long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .findByG_LPTFI_Last(groupId, layoutPageTemplateFolderId,
			orderByComparator);
	}

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate fetchByG_LPTFI_Last(long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_LPTFI_Last(groupId, layoutPageTemplateFolderId,
			orderByComparator);
	}

	/**
	* Returns the layout page templates before and after the current layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateId the primary key of the current layout page template
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public static LayoutPageTemplate[] findByG_LPTFI_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .findByG_LPTFI_PrevAndNext(layoutPageTemplateId, groupId,
			layoutPageTemplateFolderId, orderByComparator);
	}

	/**
	* Returns all the layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the matching layout page templates that the user has permission to view
	*/
	public static List<LayoutPageTemplate> filterFindByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId) {
		return getPersistence()
				   .filterFindByG_LPTFI(groupId, layoutPageTemplateFolderId);
	}

	/**
	* Returns a range of all the layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @return the range of matching layout page templates that the user has permission to view
	*/
	public static List<LayoutPageTemplate> filterFindByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId, int start, int end) {
		return getPersistence()
				   .filterFindByG_LPTFI(groupId, layoutPageTemplateFolderId,
			start, end);
	}

	/**
	* Returns an ordered range of all the layout page templates that the user has permissions to view where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page templates that the user has permission to view
	*/
	public static List<LayoutPageTemplate> filterFindByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LPTFI(groupId, layoutPageTemplateFolderId,
			start, end, orderByComparator);
	}

	/**
	* Returns the layout page templates before and after the current layout page template in the ordered set of layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateId the primary key of the current layout page template
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public static LayoutPageTemplate[] filterFindByG_LPTFI_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		long layoutPageTemplateFolderId,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .filterFindByG_LPTFI_PrevAndNext(layoutPageTemplateId,
			groupId, layoutPageTemplateFolderId, orderByComparator);
	}

	/**
	* Removes all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	*/
	public static void removeByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId) {
		getPersistence().removeByG_LPTFI(groupId, layoutPageTemplateFolderId);
	}

	/**
	* Returns the number of layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the number of matching layout page templates
	*/
	public static int countByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId) {
		return getPersistence()
				   .countByG_LPTFI(groupId, layoutPageTemplateFolderId);
	}

	/**
	* Returns the number of layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the number of matching layout page templates that the user has permission to view
	*/
	public static int filterCountByG_LPTFI(long groupId,
		long layoutPageTemplateFolderId) {
		return getPersistence()
				   .filterCountByG_LPTFI(groupId, layoutPageTemplateFolderId);
	}

	/**
	* Returns the layout page template where groupId = &#63; and name = &#63; or throws a {@link NoSuchPageTemplateException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate findByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the layout page template where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate fetchByG_N(long groupId,
		java.lang.String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the layout page template where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the layout page template where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the layout page template that was removed
	*/
	public static LayoutPageTemplate removeByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of layout page templates where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching layout page templates
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Returns all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @return the matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name) {
		return getPersistence()
				   .findByG_LPTFI_LikeN(groupId, layoutPageTemplateFolderId,
			name);
	}

	/**
	* Returns a range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @return the range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name, int start,
		int end) {
		return getPersistence()
				   .findByG_LPTFI_LikeN(groupId, layoutPageTemplateFolderId,
			name, start, end);
	}

	/**
	* Returns an ordered range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name, int start,
		int end, OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_LPTFI_LikeN(groupId, layoutPageTemplateFolderId,
			name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page templates
	*/
	public static List<LayoutPageTemplate> findByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name, int start,
		int end, OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LPTFI_LikeN(groupId, layoutPageTemplateFolderId,
			name, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate findByG_LPTFI_LikeN_First(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .findByG_LPTFI_LikeN_First(groupId,
			layoutPageTemplateFolderId, name, orderByComparator);
	}

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate fetchByG_LPTFI_LikeN_First(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_LPTFI_LikeN_First(groupId,
			layoutPageTemplateFolderId, name, orderByComparator);
	}

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate findByG_LPTFI_LikeN_Last(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .findByG_LPTFI_LikeN_Last(groupId,
			layoutPageTemplateFolderId, name, orderByComparator);
	}

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public static LayoutPageTemplate fetchByG_LPTFI_LikeN_Last(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_LPTFI_LikeN_Last(groupId,
			layoutPageTemplateFolderId, name, orderByComparator);
	}

	/**
	* Returns the layout page templates before and after the current layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param layoutPageTemplateId the primary key of the current layout page template
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public static LayoutPageTemplate[] findByG_LPTFI_LikeN_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .findByG_LPTFI_LikeN_PrevAndNext(layoutPageTemplateId,
			groupId, layoutPageTemplateFolderId, name, orderByComparator);
	}

	/**
	* Returns all the layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @return the matching layout page templates that the user has permission to view
	*/
	public static List<LayoutPageTemplate> filterFindByG_LPTFI_LikeN(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name) {
		return getPersistence()
				   .filterFindByG_LPTFI_LikeN(groupId,
			layoutPageTemplateFolderId, name);
	}

	/**
	* Returns a range of all the layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @return the range of matching layout page templates that the user has permission to view
	*/
	public static List<LayoutPageTemplate> filterFindByG_LPTFI_LikeN(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		int start, int end) {
		return getPersistence()
				   .filterFindByG_LPTFI_LikeN(groupId,
			layoutPageTemplateFolderId, name, start, end);
	}

	/**
	* Returns an ordered range of all the layout page templates that the user has permissions to view where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page templates that the user has permission to view
	*/
	public static List<LayoutPageTemplate> filterFindByG_LPTFI_LikeN(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LPTFI_LikeN(groupId,
			layoutPageTemplateFolderId, name, start, end, orderByComparator);
	}

	/**
	* Returns the layout page templates before and after the current layout page template in the ordered set of layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param layoutPageTemplateId the primary key of the current layout page template
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public static LayoutPageTemplate[] filterFindByG_LPTFI_LikeN_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence()
				   .filterFindByG_LPTFI_LikeN_PrevAndNext(layoutPageTemplateId,
			groupId, layoutPageTemplateFolderId, name, orderByComparator);
	}

	/**
	* Removes all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	*/
	public static void removeByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name) {
		getPersistence()
			.removeByG_LPTFI_LikeN(groupId, layoutPageTemplateFolderId, name);
	}

	/**
	* Returns the number of layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @return the number of matching layout page templates
	*/
	public static int countByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name) {
		return getPersistence()
				   .countByG_LPTFI_LikeN(groupId, layoutPageTemplateFolderId,
			name);
	}

	/**
	* Returns the number of layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @return the number of matching layout page templates that the user has permission to view
	*/
	public static int filterCountByG_LPTFI_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name) {
		return getPersistence()
				   .filterCountByG_LPTFI_LikeN(groupId,
			layoutPageTemplateFolderId, name);
	}

	/**
	* Caches the layout page template in the entity cache if it is enabled.
	*
	* @param layoutPageTemplate the layout page template
	*/
	public static void cacheResult(LayoutPageTemplate layoutPageTemplate) {
		getPersistence().cacheResult(layoutPageTemplate);
	}

	/**
	* Caches the layout page templates in the entity cache if it is enabled.
	*
	* @param layoutPageTemplates the layout page templates
	*/
	public static void cacheResult(List<LayoutPageTemplate> layoutPageTemplates) {
		getPersistence().cacheResult(layoutPageTemplates);
	}

	/**
	* Creates a new layout page template with the primary key. Does not add the layout page template to the database.
	*
	* @param layoutPageTemplateId the primary key for the new layout page template
	* @return the new layout page template
	*/
	public static LayoutPageTemplate create(long layoutPageTemplateId) {
		return getPersistence().create(layoutPageTemplateId);
	}

	/**
	* Removes the layout page template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateId the primary key of the layout page template
	* @return the layout page template that was removed
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public static LayoutPageTemplate remove(long layoutPageTemplateId)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence().remove(layoutPageTemplateId);
	}

	public static LayoutPageTemplate updateImpl(
		LayoutPageTemplate layoutPageTemplate) {
		return getPersistence().updateImpl(layoutPageTemplate);
	}

	/**
	* Returns the layout page template with the primary key or throws a {@link NoSuchPageTemplateException} if it could not be found.
	*
	* @param layoutPageTemplateId the primary key of the layout page template
	* @return the layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public static LayoutPageTemplate findByPrimaryKey(long layoutPageTemplateId)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateException {
		return getPersistence().findByPrimaryKey(layoutPageTemplateId);
	}

	/**
	* Returns the layout page template with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutPageTemplateId the primary key of the layout page template
	* @return the layout page template, or <code>null</code> if a layout page template with the primary key could not be found
	*/
	public static LayoutPageTemplate fetchByPrimaryKey(
		long layoutPageTemplateId) {
		return getPersistence().fetchByPrimaryKey(layoutPageTemplateId);
	}

	public static java.util.Map<java.io.Serializable, LayoutPageTemplate> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the layout page templates.
	*
	* @return the layout page templates
	*/
	public static List<LayoutPageTemplate> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the layout page templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @return the range of layout page templates
	*/
	public static List<LayoutPageTemplate> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the layout page templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout page templates
	*/
	public static List<LayoutPageTemplate> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout page templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout page templates
	*/
	public static List<LayoutPageTemplate> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the layout page templates from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of layout page templates.
	*
	* @return the number of layout page templates
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutPageTemplatePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LayoutPageTemplatePersistence, LayoutPageTemplatePersistence> _serviceTracker =
		ServiceTrackerFactory.open(LayoutPageTemplatePersistence.class);
}