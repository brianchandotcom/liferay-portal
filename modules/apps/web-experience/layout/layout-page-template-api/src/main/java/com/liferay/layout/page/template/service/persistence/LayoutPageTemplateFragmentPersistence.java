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

import com.liferay.layout.page.template.exception.NoSuchPageTemplateFragmentException;
import com.liferay.layout.page.template.model.LayoutPageTemplateFragment;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the layout page template fragment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.layout.page.template.service.persistence.impl.LayoutPageTemplateFragmentPersistenceImpl
 * @see LayoutPageTemplateFragmentUtil
 * @generated
 */
@ProviderType
public interface LayoutPageTemplateFragmentPersistence extends BasePersistence<LayoutPageTemplateFragment> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateFragmentUtil} to access the layout page template fragment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout page template fragments where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the layout page template fragments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @return the range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the layout page template fragments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template fragments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template fragment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Returns the first layout page template fragment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns the last layout page template fragment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Returns the last layout page template fragment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns the layout page template fragments before and after the current layout page template fragment in the ordered set where groupId = &#63;.
	*
	* @param layoutPageTemplateFragmentId the primary key of the current layout page template fragment
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	*/
	public LayoutPageTemplateFragment[] findByGroupId_PrevAndNext(
		long layoutPageTemplateFragmentId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Removes all the layout page template fragments where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of layout page template fragments where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page template fragments
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @return the matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_L(long groupId,
		long layoutPageTemplateEntryId);

	/**
	* Returns a range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @return the range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end);

	/**
	* Returns an ordered range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_L(long groupId,
		long layoutPageTemplateEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment findByG_L_First(long groupId,
		long layoutPageTemplateEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Returns the first layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment fetchByG_L_First(long groupId,
		long layoutPageTemplateEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns the last layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment findByG_L_Last(long groupId,
		long layoutPageTemplateEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Returns the last layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment fetchByG_L_Last(long groupId,
		long layoutPageTemplateEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns the layout page template fragments before and after the current layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param layoutPageTemplateFragmentId the primary key of the current layout page template fragment
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	*/
	public LayoutPageTemplateFragment[] findByG_L_PrevAndNext(
		long layoutPageTemplateFragmentId, long groupId,
		long layoutPageTemplateEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Removes all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	*/
	public void removeByG_L(long groupId, long layoutPageTemplateEntryId);

	/**
	* Returns the number of layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @return the number of matching layout page template fragments
	*/
	public int countByG_L(long groupId, long layoutPageTemplateEntryId);

	/**
	* Returns all the layout page template fragments where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @return the matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_F(long groupId,
		long fragmentEntryId);

	/**
	* Returns a range of all the layout page template fragments where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @return the range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_F(long groupId,
		long fragmentEntryId, int start, int end);

	/**
	* Returns an ordered range of all the layout page template fragments where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_F(long groupId,
		long fragmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template fragments where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_F(long groupId,
		long fragmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template fragment in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment findByG_F_First(long groupId,
		long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Returns the first layout page template fragment in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment fetchByG_F_First(long groupId,
		long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns the last layout page template fragment in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment findByG_F_Last(long groupId,
		long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Returns the last layout page template fragment in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment fetchByG_F_Last(long groupId,
		long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns the layout page template fragments before and after the current layout page template fragment in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param layoutPageTemplateFragmentId the primary key of the current layout page template fragment
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	*/
	public LayoutPageTemplateFragment[] findByG_F_PrevAndNext(
		long layoutPageTemplateFragmentId, long groupId, long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Removes all the layout page template fragments where groupId = &#63; and fragmentEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	*/
	public void removeByG_F(long groupId, long fragmentEntryId);

	/**
	* Returns the number of layout page template fragments where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @return the number of matching layout page template fragments
	*/
	public int countByG_F(long groupId, long fragmentEntryId);

	/**
	* Returns all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	* @return the matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_L_F(
		long groupId, long layoutPageTemplateEntryId, long fragmentEntryId);

	/**
	* Returns a range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @return the range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_L_F(
		long groupId, long layoutPageTemplateEntryId, long fragmentEntryId,
		int start, int end);

	/**
	* Returns an ordered range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_L_F(
		long groupId, long layoutPageTemplateEntryId, long fragmentEntryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findByG_L_F(
		long groupId, long layoutPageTemplateEntryId, long fragmentEntryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment findByG_L_F_First(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Returns the first layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment fetchByG_L_F_First(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns the last layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment findByG_L_F_Last(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Returns the last layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template fragment, or <code>null</code> if a matching layout page template fragment could not be found
	*/
	public LayoutPageTemplateFragment fetchByG_L_F_Last(long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns the layout page template fragments before and after the current layout page template fragment in the ordered set where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	*
	* @param layoutPageTemplateFragmentId the primary key of the current layout page template fragment
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	*/
	public LayoutPageTemplateFragment[] findByG_L_F_PrevAndNext(
		long layoutPageTemplateFragmentId, long groupId,
		long layoutPageTemplateEntryId, long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Removes all the layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	*/
	public void removeByG_L_F(long groupId, long layoutPageTemplateEntryId,
		long fragmentEntryId);

	/**
	* Returns the number of layout page template fragments where groupId = &#63; and layoutPageTemplateEntryId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param fragmentEntryId the fragment entry ID
	* @return the number of matching layout page template fragments
	*/
	public int countByG_L_F(long groupId, long layoutPageTemplateEntryId,
		long fragmentEntryId);

	/**
	* Caches the layout page template fragment in the entity cache if it is enabled.
	*
	* @param layoutPageTemplateFragment the layout page template fragment
	*/
	public void cacheResult(
		LayoutPageTemplateFragment layoutPageTemplateFragment);

	/**
	* Caches the layout page template fragments in the entity cache if it is enabled.
	*
	* @param layoutPageTemplateFragments the layout page template fragments
	*/
	public void cacheResult(
		java.util.List<LayoutPageTemplateFragment> layoutPageTemplateFragments);

	/**
	* Creates a new layout page template fragment with the primary key. Does not add the layout page template fragment to the database.
	*
	* @param layoutPageTemplateFragmentId the primary key for the new layout page template fragment
	* @return the new layout page template fragment
	*/
	public LayoutPageTemplateFragment create(long layoutPageTemplateFragmentId);

	/**
	* Removes the layout page template fragment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFragmentId the primary key of the layout page template fragment
	* @return the layout page template fragment that was removed
	* @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	*/
	public LayoutPageTemplateFragment remove(long layoutPageTemplateFragmentId)
		throws NoSuchPageTemplateFragmentException;

	public LayoutPageTemplateFragment updateImpl(
		LayoutPageTemplateFragment layoutPageTemplateFragment);

	/**
	* Returns the layout page template fragment with the primary key or throws a {@link NoSuchPageTemplateFragmentException} if it could not be found.
	*
	* @param layoutPageTemplateFragmentId the primary key of the layout page template fragment
	* @return the layout page template fragment
	* @throws NoSuchPageTemplateFragmentException if a layout page template fragment with the primary key could not be found
	*/
	public LayoutPageTemplateFragment findByPrimaryKey(
		long layoutPageTemplateFragmentId)
		throws NoSuchPageTemplateFragmentException;

	/**
	* Returns the layout page template fragment with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutPageTemplateFragmentId the primary key of the layout page template fragment
	* @return the layout page template fragment, or <code>null</code> if a layout page template fragment with the primary key could not be found
	*/
	public LayoutPageTemplateFragment fetchByPrimaryKey(
		long layoutPageTemplateFragmentId);

	@Override
	public java.util.Map<java.io.Serializable, LayoutPageTemplateFragment> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the layout page template fragments.
	*
	* @return the layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findAll();

	/**
	* Returns a range of all the layout page template fragments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @return the range of layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findAll(int start, int end);

	/**
	* Returns an ordered range of all the layout page template fragments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template fragments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout page template fragments
	*/
	public java.util.List<LayoutPageTemplateFragment> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFragment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the layout page template fragments from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout page template fragments.
	*
	* @return the number of layout page template fragments
	*/
	public int countAll();
}