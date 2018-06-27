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

package com.liferay.asset.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetTagDisplay;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service interface for AssetTag. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagServiceUtil
 * @see com.liferay.portlet.asset.service.base.AssetTagServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetTagServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AssetTagService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetTagServiceUtil} to access the asset tag remote service. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetTagServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public AssetTag addTag(long groupId, String name,
		ServiceContext serviceContext) throws PortalException;

	public void deleteTag(long tagId) throws PortalException;

	public void deleteTags(long[] tagIds) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getGroupsTags(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getGroupTags(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getGroupTags(long groupId, int start, int end,
		OrderByComparator<AssetTag> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupTagsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetTagDisplay getGroupTagsDisplay(long groupId, String name,
		int start, int end);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetTag getTag(long tagId) throws PortalException;

	/**
	* Returns the number of tags associated to the asset from the given
	* classNameId-classPK pair value.
	*
	* @param classNameId the classNameId.
	* @param classPK the primary key.
	* @return the number of tags.
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTagCount(long classNameId, long classPK);

	/**
	* Returns the number of tags associated to the asset from the given
	* className-classPK pair value.
	*
	* @param className the name of the class.
	* @param classPK the primary key.
	* @return the number of tags.
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTagCount(String className, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(long groupId, long classNameId, String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(long groupId, long classNameId, String name,
		int start, int end, OrderByComparator<AssetTag> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(long groupId, String name, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(long groupId, String name, int start,
		int end, OrderByComparator<AssetTag> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(long[] groupIds, String name, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(long[] groupIds, String name, int start,
		int end, OrderByComparator<AssetTag> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(String className, long classPK);

	/**
	* Returns a range of asset tags of the entity.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the asset tags of the entity
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(String className, long classPK, int start,
		int end, OrderByComparator<AssetTag> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTagsCount(long groupId, String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getVisibleAssetsTagsCount(long groupId, long classNameId,
		String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getVisibleAssetsTagsCount(long groupId, String name);

	public void mergeTags(long fromTagId, long toTagId)
		throws PortalException;

	public void mergeTags(long[] fromTagIds, long toTagId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray search(long groupId, String name, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray search(long[] groupIds, String name, int start, int end);

	public AssetTag updateTag(long tagId, String name,
		ServiceContext serviceContext) throws PortalException;
}