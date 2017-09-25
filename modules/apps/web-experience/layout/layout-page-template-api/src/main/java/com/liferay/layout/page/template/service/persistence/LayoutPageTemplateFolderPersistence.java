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

import com.liferay.layout.page.template.exception.NoSuchPageTemplateFolderException;
import com.liferay.layout.page.template.model.LayoutPageTemplateFolder;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the layout page template folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.layout.page.template.service.persistence.impl.LayoutPageTemplateFolderPersistenceImpl
 * @see LayoutPageTemplateFolderUtil
 * @generated
 */
@ProviderType
public interface LayoutPageTemplateFolderPersistence extends BasePersistence<LayoutPageTemplateFolder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateFolderUtil} to access the layout page template folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout page template folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findByGroupId(long groupId);

	/**
	* Returns a range of all the layout page template folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @return the range of matching layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the layout page template folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template folder
	* @throws NoSuchPageTemplateFolderException if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException;

	/**
	* Returns the first layout page template folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator);

	/**
	* Returns the last layout page template folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template folder
	* @throws NoSuchPageTemplateFolderException if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException;

	/**
	* Returns the last layout page template folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator);

	/**
	* Returns the layout page template folders before and after the current layout page template folder in the ordered set where groupId = &#63;.
	*
	* @param layoutPageTemplateFolderId the primary key of the current layout page template folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template folder
	* @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	*/
	public LayoutPageTemplateFolder[] findByGroupId_PrevAndNext(
		long layoutPageTemplateFolderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException;

	/**
	* Returns all the layout page template folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page template folders that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateFolder> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the layout page template folders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @return the range of matching layout page template folders that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateFolder> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the layout page template folders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template folders that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateFolder> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator);

	/**
	* Returns the layout page template folders before and after the current layout page template folder in the ordered set of layout page template folders that the user has permission to view where groupId = &#63;.
	*
	* @param layoutPageTemplateFolderId the primary key of the current layout page template folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template folder
	* @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	*/
	public LayoutPageTemplateFolder[] filterFindByGroupId_PrevAndNext(
		long layoutPageTemplateFolderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException;

	/**
	* Removes all the layout page template folders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of layout page template folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page template folders
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of layout page template folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page template folders that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the layout page template folder where groupId = &#63; and name = &#63; or throws a {@link NoSuchPageTemplateFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template folder
	* @throws NoSuchPageTemplateFolderException if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder findByG_N(long groupId,
		java.lang.String name) throws NoSuchPageTemplateFolderException;

	/**
	* Returns the layout page template folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder fetchByG_N(long groupId,
		java.lang.String name);

	/**
	* Returns the layout page template folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the layout page template folder where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the layout page template folder that was removed
	*/
	public LayoutPageTemplateFolder removeByG_N(long groupId,
		java.lang.String name) throws NoSuchPageTemplateFolderException;

	/**
	* Returns the number of layout page template folders where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching layout page template folders
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the layout page template folders where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findByG_LikeN(
		long groupId, java.lang.String name);

	/**
	* Returns a range of all the layout page template folders where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @return the range of matching layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findByG_LikeN(
		long groupId, java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the layout page template folders where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findByG_LikeN(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template folders where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findByG_LikeN(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template folder in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template folder
	* @throws NoSuchPageTemplateFolderException if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder findByG_LikeN_First(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException;

	/**
	* Returns the first layout page template folder in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder fetchByG_LikeN_First(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator);

	/**
	* Returns the last layout page template folder in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template folder
	* @throws NoSuchPageTemplateFolderException if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder findByG_LikeN_Last(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException;

	/**
	* Returns the last layout page template folder in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template folder, or <code>null</code> if a matching layout page template folder could not be found
	*/
	public LayoutPageTemplateFolder fetchByG_LikeN_Last(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator);

	/**
	* Returns the layout page template folders before and after the current layout page template folder in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param layoutPageTemplateFolderId the primary key of the current layout page template folder
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template folder
	* @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	*/
	public LayoutPageTemplateFolder[] findByG_LikeN_PrevAndNext(
		long layoutPageTemplateFolderId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException;

	/**
	* Returns all the layout page template folders that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template folders that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateFolder> filterFindByG_LikeN(
		long groupId, java.lang.String name);

	/**
	* Returns a range of all the layout page template folders that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @return the range of matching layout page template folders that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateFolder> filterFindByG_LikeN(
		long groupId, java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the layout page template folders that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template folders that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateFolder> filterFindByG_LikeN(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator);

	/**
	* Returns the layout page template folders before and after the current layout page template folder in the ordered set of layout page template folders that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param layoutPageTemplateFolderId the primary key of the current layout page template folder
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template folder
	* @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	*/
	public LayoutPageTemplateFolder[] filterFindByG_LikeN_PrevAndNext(
		long layoutPageTemplateFolderId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws NoSuchPageTemplateFolderException;

	/**
	* Removes all the layout page template folders where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public void removeByG_LikeN(long groupId, java.lang.String name);

	/**
	* Returns the number of layout page template folders where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching layout page template folders
	*/
	public int countByG_LikeN(long groupId, java.lang.String name);

	/**
	* Returns the number of layout page template folders that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching layout page template folders that the user has permission to view
	*/
	public int filterCountByG_LikeN(long groupId, java.lang.String name);

	/**
	* Caches the layout page template folder in the entity cache if it is enabled.
	*
	* @param layoutPageTemplateFolder the layout page template folder
	*/
	public void cacheResult(LayoutPageTemplateFolder layoutPageTemplateFolder);

	/**
	* Caches the layout page template folders in the entity cache if it is enabled.
	*
	* @param layoutPageTemplateFolders the layout page template folders
	*/
	public void cacheResult(
		java.util.List<LayoutPageTemplateFolder> layoutPageTemplateFolders);

	/**
	* Creates a new layout page template folder with the primary key. Does not add the layout page template folder to the database.
	*
	* @param layoutPageTemplateFolderId the primary key for the new layout page template folder
	* @return the new layout page template folder
	*/
	public LayoutPageTemplateFolder create(long layoutPageTemplateFolderId);

	/**
	* Removes the layout page template folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFolderId the primary key of the layout page template folder
	* @return the layout page template folder that was removed
	* @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	*/
	public LayoutPageTemplateFolder remove(long layoutPageTemplateFolderId)
		throws NoSuchPageTemplateFolderException;

	public LayoutPageTemplateFolder updateImpl(
		LayoutPageTemplateFolder layoutPageTemplateFolder);

	/**
	* Returns the layout page template folder with the primary key or throws a {@link NoSuchPageTemplateFolderException} if it could not be found.
	*
	* @param layoutPageTemplateFolderId the primary key of the layout page template folder
	* @return the layout page template folder
	* @throws NoSuchPageTemplateFolderException if a layout page template folder with the primary key could not be found
	*/
	public LayoutPageTemplateFolder findByPrimaryKey(
		long layoutPageTemplateFolderId)
		throws NoSuchPageTemplateFolderException;

	/**
	* Returns the layout page template folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutPageTemplateFolderId the primary key of the layout page template folder
	* @return the layout page template folder, or <code>null</code> if a layout page template folder with the primary key could not be found
	*/
	public LayoutPageTemplateFolder fetchByPrimaryKey(
		long layoutPageTemplateFolderId);

	@Override
	public java.util.Map<java.io.Serializable, LayoutPageTemplateFolder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the layout page template folders.
	*
	* @return the layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findAll();

	/**
	* Returns a range of all the layout page template folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @return the range of layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findAll(int start, int end);

	/**
	* Returns an ordered range of all the layout page template folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout page template folders
	*/
	public java.util.List<LayoutPageTemplateFolder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the layout page template folders from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout page template folders.
	*
	* @return the number of layout page template folders
	*/
	public int countAll();
}