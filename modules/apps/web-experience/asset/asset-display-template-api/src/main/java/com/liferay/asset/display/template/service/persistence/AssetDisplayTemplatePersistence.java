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

import com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException;
import com.liferay.asset.display.template.model.AssetDisplayTemplate;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset display template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.display.template.service.persistence.impl.AssetDisplayTemplatePersistenceImpl
 * @see AssetDisplayTemplateUtil
 * @generated
 */
@ProviderType
public interface AssetDisplayTemplatePersistence extends BasePersistence<AssetDisplayTemplate> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetDisplayTemplateUtil} to access the asset display template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset display templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset display templates
	*/
	public java.util.List<AssetDisplayTemplate> findByGroupId(long groupId);

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
	public java.util.List<AssetDisplayTemplate> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<AssetDisplayTemplate> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

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
	public java.util.List<AssetDisplayTemplate> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

	/**
	* Returns the asset display templates before and after the current asset display template in the ordered set where groupId = &#63;.
	*
	* @param assetDisplayTemplateId the primary key of the current asset display template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display template
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public AssetDisplayTemplate[] findByGroupId_PrevAndNext(
		long assetDisplayTemplateId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Returns all the asset display templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset display templates that the user has permission to view
	*/
	public java.util.List<AssetDisplayTemplate> filterFindByGroupId(
		long groupId);

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
	public java.util.List<AssetDisplayTemplate> filterFindByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<AssetDisplayTemplate> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

	/**
	* Returns the asset display templates before and after the current asset display template in the ordered set of asset display templates that the user has permission to view where groupId = &#63;.
	*
	* @param assetDisplayTemplateId the primary key of the current asset display template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display template
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public AssetDisplayTemplate[] filterFindByGroupId_PrevAndNext(
		long assetDisplayTemplateId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Removes all the asset display templates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of asset display templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset display templates
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of asset display templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset display templates that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the asset display templates where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset display templates
	*/
	public java.util.List<AssetDisplayTemplate> findByG_LikeN(long groupId,
		String name);

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
	public java.util.List<AssetDisplayTemplate> findByG_LikeN(long groupId,
		String name, int start, int end);

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
	public java.util.List<AssetDisplayTemplate> findByG_LikeN(long groupId,
		String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

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
	public java.util.List<AssetDisplayTemplate> findByG_LikeN(long groupId,
		String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate findByG_LikeN_First(long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate fetchByG_LikeN_First(long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate findByG_LikeN_Last(long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate fetchByG_LikeN_Last(long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

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
	public AssetDisplayTemplate[] findByG_LikeN_PrevAndNext(
		long assetDisplayTemplateId, long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Returns all the asset display templates that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset display templates that the user has permission to view
	*/
	public java.util.List<AssetDisplayTemplate> filterFindByG_LikeN(
		long groupId, String name);

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
	public java.util.List<AssetDisplayTemplate> filterFindByG_LikeN(
		long groupId, String name, int start, int end);

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
	public java.util.List<AssetDisplayTemplate> filterFindByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

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
	public AssetDisplayTemplate[] filterFindByG_LikeN_PrevAndNext(
		long assetDisplayTemplateId, long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Removes all the asset display templates where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public void removeByG_LikeN(long groupId, String name);

	/**
	* Returns the number of asset display templates where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset display templates
	*/
	public int countByG_LikeN(long groupId, String name);

	/**
	* Returns the number of asset display templates that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset display templates that the user has permission to view
	*/
	public int filterCountByG_LikeN(long groupId, String name);

	/**
	* Returns all the asset display templates where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching asset display templates
	*/
	public java.util.List<AssetDisplayTemplate> findByG_C(long groupId,
		long classNameId);

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
	public java.util.List<AssetDisplayTemplate> findByG_C(long groupId,
		long classNameId, int start, int end);

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
	public java.util.List<AssetDisplayTemplate> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

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
	public java.util.List<AssetDisplayTemplate> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate findByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Returns the first asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate fetchByG_C_First(long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template
	* @throws NoSuchDisplayTemplateException if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate findByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Returns the last asset display template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display template, or <code>null</code> if a matching asset display template could not be found
	*/
	public AssetDisplayTemplate fetchByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

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
	public AssetDisplayTemplate[] findByG_C_PrevAndNext(
		long assetDisplayTemplateId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Returns all the asset display templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching asset display templates that the user has permission to view
	*/
	public java.util.List<AssetDisplayTemplate> filterFindByG_C(long groupId,
		long classNameId);

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
	public java.util.List<AssetDisplayTemplate> filterFindByG_C(long groupId,
		long classNameId, int start, int end);

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
	public java.util.List<AssetDisplayTemplate> filterFindByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

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
	public AssetDisplayTemplate[] filterFindByG_C_PrevAndNext(
		long assetDisplayTemplateId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator)
		throws NoSuchDisplayTemplateException;

	/**
	* Removes all the asset display templates where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public void removeByG_C(long groupId, long classNameId);

	/**
	* Returns the number of asset display templates where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching asset display templates
	*/
	public int countByG_C(long groupId, long classNameId);

	/**
	* Returns the number of asset display templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching asset display templates that the user has permission to view
	*/
	public int filterCountByG_C(long groupId, long classNameId);

	/**
	* Caches the asset display template in the entity cache if it is enabled.
	*
	* @param assetDisplayTemplate the asset display template
	*/
	public void cacheResult(AssetDisplayTemplate assetDisplayTemplate);

	/**
	* Caches the asset display templates in the entity cache if it is enabled.
	*
	* @param assetDisplayTemplates the asset display templates
	*/
	public void cacheResult(
		java.util.List<AssetDisplayTemplate> assetDisplayTemplates);

	/**
	* Creates a new asset display template with the primary key. Does not add the asset display template to the database.
	*
	* @param assetDisplayTemplateId the primary key for the new asset display template
	* @return the new asset display template
	*/
	public AssetDisplayTemplate create(long assetDisplayTemplateId);

	/**
	* Removes the asset display template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayTemplateId the primary key of the asset display template
	* @return the asset display template that was removed
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public AssetDisplayTemplate remove(long assetDisplayTemplateId)
		throws NoSuchDisplayTemplateException;

	public AssetDisplayTemplate updateImpl(
		AssetDisplayTemplate assetDisplayTemplate);

	/**
	* Returns the asset display template with the primary key or throws a {@link NoSuchDisplayTemplateException} if it could not be found.
	*
	* @param assetDisplayTemplateId the primary key of the asset display template
	* @return the asset display template
	* @throws NoSuchDisplayTemplateException if a asset display template with the primary key could not be found
	*/
	public AssetDisplayTemplate findByPrimaryKey(long assetDisplayTemplateId)
		throws NoSuchDisplayTemplateException;

	/**
	* Returns the asset display template with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetDisplayTemplateId the primary key of the asset display template
	* @return the asset display template, or <code>null</code> if a asset display template with the primary key could not be found
	*/
	public AssetDisplayTemplate fetchByPrimaryKey(long assetDisplayTemplateId);

	@Override
	public java.util.Map<java.io.Serializable, AssetDisplayTemplate> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset display templates.
	*
	* @return the asset display templates
	*/
	public java.util.List<AssetDisplayTemplate> findAll();

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
	public java.util.List<AssetDisplayTemplate> findAll(int start, int end);

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
	public java.util.List<AssetDisplayTemplate> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator);

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
	public java.util.List<AssetDisplayTemplate> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset display templates from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset display templates.
	*
	* @return the number of asset display templates
	*/
	public int countAll();
}