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

package com.liferay.fragment.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.model.FragmentEntryInstanceLink;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the fragment entry instance link service. This utility wraps {@link com.liferay.fragment.service.persistence.impl.FragmentEntryInstanceLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryInstanceLinkPersistence
 * @see com.liferay.fragment.service.persistence.impl.FragmentEntryInstanceLinkPersistenceImpl
 * @generated
 */
@ProviderType
public class FragmentEntryInstanceLinkUtil {
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
		FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		getPersistence().clearCache(fragmentEntryInstanceLink);
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
	public static List<FragmentEntryInstanceLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FragmentEntryInstanceLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FragmentEntryInstanceLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FragmentEntryInstanceLink update(
		FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		return getPersistence().update(fragmentEntryInstanceLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FragmentEntryInstanceLink update(
		FragmentEntryInstanceLink fragmentEntryInstanceLink,
		ServiceContext serviceContext) {
		return getPersistence().update(fragmentEntryInstanceLink, serviceContext);
	}

	/**
	* Returns all the fragment entry instance links where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the fragment entry instance links where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @return the range of matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the fragment entry instance links where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment entry instance links where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first fragment entry instance link in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry instance link
	* @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink findByGroupId_First(long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first fragment entry instance link in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink fetchByGroupId_First(long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last fragment entry instance link in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry instance link
	* @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink findByGroupId_Last(long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last fragment entry instance link in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink fetchByGroupId_Last(long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the fragment entry instance links before and after the current fragment entry instance link in the ordered set where groupId = &#63;.
	*
	* @param fragmentEntryInstanceLinkId the primary key of the current fragment entry instance link
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry instance link
	* @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	*/
	public static FragmentEntryInstanceLink[] findByGroupId_PrevAndNext(
		long fragmentEntryInstanceLinkId, long groupId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(fragmentEntryInstanceLinkId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the fragment entry instance links where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of fragment entry instance links where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching fragment entry instance links
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @return the matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByG_F(long groupId,
		long fragmentEntryId) {
		return getPersistence().findByG_F(groupId, fragmentEntryId);
	}

	/**
	* Returns a range of all the fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @return the range of matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByG_F(long groupId,
		long fragmentEntryId, int start, int end) {
		return getPersistence().findByG_F(groupId, fragmentEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByG_F(long groupId,
		long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence()
				   .findByG_F(groupId, fragmentEntryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByG_F(long groupId,
		long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_F(groupId, fragmentEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first fragment entry instance link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry instance link
	* @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink findByG_F_First(long groupId,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence()
				   .findByG_F_First(groupId, fragmentEntryId, orderByComparator);
	}

	/**
	* Returns the first fragment entry instance link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink fetchByG_F_First(long groupId,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_First(groupId, fragmentEntryId, orderByComparator);
	}

	/**
	* Returns the last fragment entry instance link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry instance link
	* @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink findByG_F_Last(long groupId,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence()
				   .findByG_F_Last(groupId, fragmentEntryId, orderByComparator);
	}

	/**
	* Returns the last fragment entry instance link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink fetchByG_F_Last(long groupId,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_Last(groupId, fragmentEntryId, orderByComparator);
	}

	/**
	* Returns the fragment entry instance links before and after the current fragment entry instance link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param fragmentEntryInstanceLinkId the primary key of the current fragment entry instance link
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry instance link
	* @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	*/
	public static FragmentEntryInstanceLink[] findByG_F_PrevAndNext(
		long fragmentEntryInstanceLinkId, long groupId, long fragmentEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence()
				   .findByG_F_PrevAndNext(fragmentEntryInstanceLinkId, groupId,
			fragmentEntryId, orderByComparator);
	}

	/**
	* Removes all the fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	*/
	public static void removeByG_F(long groupId, long fragmentEntryId) {
		getPersistence().removeByG_F(groupId, fragmentEntryId);
	}

	/**
	* Returns the number of fragment entry instance links where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @return the number of matching fragment entry instance links
	*/
	public static int countByG_F(long groupId, long fragmentEntryId) {
		return getPersistence().countByG_F(groupId, fragmentEntryId);
	}

	/**
	* Returns all the fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @return the matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByG_L(long groupId,
		long layoutPageTemplateEntryId) {
		return getPersistence().findByG_L(groupId, layoutPageTemplateEntryId);
	}

	/**
	* Returns a range of all the fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @return the range of matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end) {
		return getPersistence()
				   .findByG_L(groupId, layoutPageTemplateEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence()
				   .findByG_L(groupId, layoutPageTemplateEntryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_L(groupId, layoutPageTemplateEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first fragment entry instance link in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry instance link
	* @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink findByG_L_First(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence()
				   .findByG_L_First(groupId, layoutPageTemplateEntryId,
			orderByComparator);
	}

	/**
	* Returns the first fragment entry instance link in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink fetchByG_L_First(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence()
				   .fetchByG_L_First(groupId, layoutPageTemplateEntryId,
			orderByComparator);
	}

	/**
	* Returns the last fragment entry instance link in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry instance link
	* @throws NoSuchEntryInstanceLinkException if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink findByG_L_Last(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence()
				   .findByG_L_Last(groupId, layoutPageTemplateEntryId,
			orderByComparator);
	}

	/**
	* Returns the last fragment entry instance link in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry instance link, or <code>null</code> if a matching fragment entry instance link could not be found
	*/
	public static FragmentEntryInstanceLink fetchByG_L_Last(long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence()
				   .fetchByG_L_Last(groupId, layoutPageTemplateEntryId,
			orderByComparator);
	}

	/**
	* Returns the fragment entry instance links before and after the current fragment entry instance link in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param fragmentEntryInstanceLinkId the primary key of the current fragment entry instance link
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry instance link
	* @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	*/
	public static FragmentEntryInstanceLink[] findByG_L_PrevAndNext(
		long fragmentEntryInstanceLinkId, long groupId,
		long layoutPageTemplateEntryId,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence()
				   .findByG_L_PrevAndNext(fragmentEntryInstanceLinkId, groupId,
			layoutPageTemplateEntryId, orderByComparator);
	}

	/**
	* Removes all the fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	*/
	public static void removeByG_L(long groupId, long layoutPageTemplateEntryId) {
		getPersistence().removeByG_L(groupId, layoutPageTemplateEntryId);
	}

	/**
	* Returns the number of fragment entry instance links where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @return the number of matching fragment entry instance links
	*/
	public static int countByG_L(long groupId, long layoutPageTemplateEntryId) {
		return getPersistence().countByG_L(groupId, layoutPageTemplateEntryId);
	}

	/**
	* Caches the fragment entry instance link in the entity cache if it is enabled.
	*
	* @param fragmentEntryInstanceLink the fragment entry instance link
	*/
	public static void cacheResult(
		FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		getPersistence().cacheResult(fragmentEntryInstanceLink);
	}

	/**
	* Caches the fragment entry instance links in the entity cache if it is enabled.
	*
	* @param fragmentEntryInstanceLinks the fragment entry instance links
	*/
	public static void cacheResult(
		List<FragmentEntryInstanceLink> fragmentEntryInstanceLinks) {
		getPersistence().cacheResult(fragmentEntryInstanceLinks);
	}

	/**
	* Creates a new fragment entry instance link with the primary key. Does not add the fragment entry instance link to the database.
	*
	* @param fragmentEntryInstanceLinkId the primary key for the new fragment entry instance link
	* @return the new fragment entry instance link
	*/
	public static FragmentEntryInstanceLink create(
		long fragmentEntryInstanceLinkId) {
		return getPersistence().create(fragmentEntryInstanceLinkId);
	}

	/**
	* Removes the fragment entry instance link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryInstanceLinkId the primary key of the fragment entry instance link
	* @return the fragment entry instance link that was removed
	* @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	*/
	public static FragmentEntryInstanceLink remove(
		long fragmentEntryInstanceLinkId)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence().remove(fragmentEntryInstanceLinkId);
	}

	public static FragmentEntryInstanceLink updateImpl(
		FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		return getPersistence().updateImpl(fragmentEntryInstanceLink);
	}

	/**
	* Returns the fragment entry instance link with the primary key or throws a {@link NoSuchEntryInstanceLinkException} if it could not be found.
	*
	* @param fragmentEntryInstanceLinkId the primary key of the fragment entry instance link
	* @return the fragment entry instance link
	* @throws NoSuchEntryInstanceLinkException if a fragment entry instance link with the primary key could not be found
	*/
	public static FragmentEntryInstanceLink findByPrimaryKey(
		long fragmentEntryInstanceLinkId)
		throws com.liferay.fragment.exception.NoSuchEntryInstanceLinkException {
		return getPersistence().findByPrimaryKey(fragmentEntryInstanceLinkId);
	}

	/**
	* Returns the fragment entry instance link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fragmentEntryInstanceLinkId the primary key of the fragment entry instance link
	* @return the fragment entry instance link, or <code>null</code> if a fragment entry instance link with the primary key could not be found
	*/
	public static FragmentEntryInstanceLink fetchByPrimaryKey(
		long fragmentEntryInstanceLinkId) {
		return getPersistence().fetchByPrimaryKey(fragmentEntryInstanceLinkId);
	}

	public static java.util.Map<java.io.Serializable, FragmentEntryInstanceLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the fragment entry instance links.
	*
	* @return the fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the fragment entry instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @return the range of fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the fragment entry instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findAll(int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the fragment entry instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entry instance links
	* @param end the upper bound of the range of fragment entry instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of fragment entry instance links
	*/
	public static List<FragmentEntryInstanceLink> findAll(int start, int end,
		OrderByComparator<FragmentEntryInstanceLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the fragment entry instance links from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of fragment entry instance links.
	*
	* @return the number of fragment entry instance links
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FragmentEntryInstanceLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FragmentEntryInstanceLinkPersistence, FragmentEntryInstanceLinkPersistence> _serviceTracker =
		ServiceTrackerFactory.open(FragmentEntryInstanceLinkPersistence.class);
}