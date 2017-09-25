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

import com.liferay.layout.page.template.exception.NoSuchPageTemplateException;
import com.liferay.layout.page.template.model.LayoutPageTemplate;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the layout page template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.layout.page.template.service.persistence.impl.LayoutPageTemplatePersistenceImpl
 * @see LayoutPageTemplateUtil
 * @generated
 */
@ProviderType
public interface LayoutPageTemplatePersistence extends BasePersistence<LayoutPageTemplate> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateUtil} to access the layout page template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout page templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page templates
	*/
	public java.util.List<LayoutPageTemplate> findByGroupId(long groupId);

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
	public java.util.List<LayoutPageTemplate> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<LayoutPageTemplate> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

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
	public java.util.List<LayoutPageTemplate> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public LayoutPageTemplate findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public LayoutPageTemplate fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public LayoutPageTemplate findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public LayoutPageTemplate fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

	/**
	* Returns the layout page templates before and after the current layout page template in the ordered set where groupId = &#63;.
	*
	* @param layoutPageTemplateId the primary key of the current layout page template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public LayoutPageTemplate[] findByGroupId_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns all the layout page templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page templates that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplate> filterFindByGroupId(long groupId);

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
	public java.util.List<LayoutPageTemplate> filterFindByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<LayoutPageTemplate> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

	/**
	* Returns the layout page templates before and after the current layout page template in the ordered set of layout page templates that the user has permission to view where groupId = &#63;.
	*
	* @param layoutPageTemplateId the primary key of the current layout page template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public LayoutPageTemplate[] filterFindByGroupId_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Removes all the layout page templates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of layout page templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page templates
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of layout page templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page templates that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the layout page templates where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the matching layout page templates
	*/
	public java.util.List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId);

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
	public java.util.List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId, int start, int end);

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
	public java.util.List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

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
	public java.util.List<LayoutPageTemplate> findByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public LayoutPageTemplate findByLayoutPageTemplateFolderId_First(
		long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns the first layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public LayoutPageTemplate fetchByLayoutPageTemplateFolderId_First(
		long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

	/**
	* Returns the last layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public LayoutPageTemplate findByLayoutPageTemplateFolderId_Last(
		long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns the last layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public LayoutPageTemplate fetchByLayoutPageTemplateFolderId_Last(
		long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

	/**
	* Returns the layout page templates before and after the current layout page template in the ordered set where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateId the primary key of the current layout page template
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public LayoutPageTemplate[] findByLayoutPageTemplateFolderId_PrevAndNext(
		long layoutPageTemplateId, long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Removes all the layout page templates where layoutPageTemplateFolderId = &#63; from the database.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	*/
	public void removeByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId);

	/**
	* Returns the number of layout page templates where layoutPageTemplateFolderId = &#63;.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the number of matching layout page templates
	*/
	public int countByLayoutPageTemplateFolderId(
		long layoutPageTemplateFolderId);

	/**
	* Returns all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the matching layout page templates
	*/
	public java.util.List<LayoutPageTemplate> findByG_L(long groupId,
		long layoutPageTemplateFolderId);

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
	public java.util.List<LayoutPageTemplate> findByG_L(long groupId,
		long layoutPageTemplateFolderId, int start, int end);

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
	public java.util.List<LayoutPageTemplate> findByG_L(long groupId,
		long layoutPageTemplateFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

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
	public java.util.List<LayoutPageTemplate> findByG_L(long groupId,
		long layoutPageTemplateFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public LayoutPageTemplate findByG_L_First(long groupId,
		long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public LayoutPageTemplate fetchByG_L_First(long groupId,
		long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public LayoutPageTemplate findByG_L_Last(long groupId,
		long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public LayoutPageTemplate fetchByG_L_Last(long groupId,
		long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

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
	public LayoutPageTemplate[] findByG_L_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns all the layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the matching layout page templates that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplate> filterFindByG_L(long groupId,
		long layoutPageTemplateFolderId);

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
	public java.util.List<LayoutPageTemplate> filterFindByG_L(long groupId,
		long layoutPageTemplateFolderId, int start, int end);

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
	public java.util.List<LayoutPageTemplate> filterFindByG_L(long groupId,
		long layoutPageTemplateFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

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
	public LayoutPageTemplate[] filterFindByG_L_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		long layoutPageTemplateFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Removes all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	*/
	public void removeByG_L(long groupId, long layoutPageTemplateFolderId);

	/**
	* Returns the number of layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the number of matching layout page templates
	*/
	public int countByG_L(long groupId, long layoutPageTemplateFolderId);

	/**
	* Returns the number of layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @return the number of matching layout page templates that the user has permission to view
	*/
	public int filterCountByG_L(long groupId, long layoutPageTemplateFolderId);

	/**
	* Returns the layout page template where groupId = &#63; and name = &#63; or throws a {@link NoSuchPageTemplateException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template
	* @throws NoSuchPageTemplateException if a matching layout page template could not be found
	*/
	public LayoutPageTemplate findByG_N(long groupId, java.lang.String name)
		throws NoSuchPageTemplateException;

	/**
	* Returns the layout page template where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public LayoutPageTemplate fetchByG_N(long groupId, java.lang.String name);

	/**
	* Returns the layout page template where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public LayoutPageTemplate fetchByG_N(long groupId, java.lang.String name,
		boolean retrieveFromCache);

	/**
	* Removes the layout page template where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the layout page template that was removed
	*/
	public LayoutPageTemplate removeByG_N(long groupId, java.lang.String name)
		throws NoSuchPageTemplateException;

	/**
	* Returns the number of layout page templates where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching layout page templates
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @return the matching layout page templates
	*/
	public java.util.List<LayoutPageTemplate> findByG_L_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name);

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
	public java.util.List<LayoutPageTemplate> findByG_L_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name, int start,
		int end);

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
	public java.util.List<LayoutPageTemplate> findByG_L_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

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
	public java.util.List<LayoutPageTemplate> findByG_L_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache);

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
	public LayoutPageTemplate findByG_L_LikeN_First(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns the first layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public LayoutPageTemplate fetchByG_L_LikeN_First(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

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
	public LayoutPageTemplate findByG_L_LikeN_Last(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns the last layout page template in the ordered set where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template, or <code>null</code> if a matching layout page template could not be found
	*/
	public LayoutPageTemplate fetchByG_L_LikeN_Last(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

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
	public LayoutPageTemplate[] findByG_L_LikeN_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Returns all the layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @return the matching layout page templates that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplate> filterFindByG_L_LikeN(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name);

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
	public java.util.List<LayoutPageTemplate> filterFindByG_L_LikeN(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		int start, int end);

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
	public java.util.List<LayoutPageTemplate> filterFindByG_L_LikeN(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

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
	public LayoutPageTemplate[] filterFindByG_L_LikeN_PrevAndNext(
		long layoutPageTemplateId, long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws NoSuchPageTemplateException;

	/**
	* Removes all the layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	*/
	public void removeByG_L_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name);

	/**
	* Returns the number of layout page templates where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @return the number of matching layout page templates
	*/
	public int countByG_L_LikeN(long groupId, long layoutPageTemplateFolderId,
		java.lang.String name);

	/**
	* Returns the number of layout page templates that the user has permission to view where groupId = &#63; and layoutPageTemplateFolderId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateFolderId the layout page template folder ID
	* @param name the name
	* @return the number of matching layout page templates that the user has permission to view
	*/
	public int filterCountByG_L_LikeN(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name);

	/**
	* Caches the layout page template in the entity cache if it is enabled.
	*
	* @param layoutPageTemplate the layout page template
	*/
	public void cacheResult(LayoutPageTemplate layoutPageTemplate);

	/**
	* Caches the layout page templates in the entity cache if it is enabled.
	*
	* @param layoutPageTemplates the layout page templates
	*/
	public void cacheResult(
		java.util.List<LayoutPageTemplate> layoutPageTemplates);

	/**
	* Creates a new layout page template with the primary key. Does not add the layout page template to the database.
	*
	* @param layoutPageTemplateId the primary key for the new layout page template
	* @return the new layout page template
	*/
	public LayoutPageTemplate create(long layoutPageTemplateId);

	/**
	* Removes the layout page template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateId the primary key of the layout page template
	* @return the layout page template that was removed
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public LayoutPageTemplate remove(long layoutPageTemplateId)
		throws NoSuchPageTemplateException;

	public LayoutPageTemplate updateImpl(LayoutPageTemplate layoutPageTemplate);

	/**
	* Returns the layout page template with the primary key or throws a {@link NoSuchPageTemplateException} if it could not be found.
	*
	* @param layoutPageTemplateId the primary key of the layout page template
	* @return the layout page template
	* @throws NoSuchPageTemplateException if a layout page template with the primary key could not be found
	*/
	public LayoutPageTemplate findByPrimaryKey(long layoutPageTemplateId)
		throws NoSuchPageTemplateException;

	/**
	* Returns the layout page template with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutPageTemplateId the primary key of the layout page template
	* @return the layout page template, or <code>null</code> if a layout page template with the primary key could not be found
	*/
	public LayoutPageTemplate fetchByPrimaryKey(long layoutPageTemplateId);

	@Override
	public java.util.Map<java.io.Serializable, LayoutPageTemplate> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the layout page templates.
	*
	* @return the layout page templates
	*/
	public java.util.List<LayoutPageTemplate> findAll();

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
	public java.util.List<LayoutPageTemplate> findAll(int start, int end);

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
	public java.util.List<LayoutPageTemplate> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator);

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
	public java.util.List<LayoutPageTemplate> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the layout page templates from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout page templates.
	*
	* @return the number of layout page templates
	*/
	public int countAll();
}