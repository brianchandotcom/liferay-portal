/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetTagLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagLocalService
 * @generated
 */
@ProviderType
public class AssetTagLocalServiceWrapper implements AssetTagLocalService,
	ServiceWrapper<AssetTagLocalService> {
	public AssetTagLocalServiceWrapper(
		AssetTagLocalService assetTagLocalService) {
		_assetTagLocalService = assetTagLocalService;
	}

	/**
	* Adds the asset tag to the database. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag addAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.addAssetTag(assetTag);
	}

	/**
	* Creates a new asset tag with the primary key. Does not add the asset tag to the database.
	*
	* @param tagId the primary key for the new asset tag
	* @return the new asset tag
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag createAssetTag(long tagId) {
		return _assetTagLocalService.createAssetTag(tagId);
	}

	/**
	* Deletes the asset tag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag that was removed
	* @throws PortalException if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag deleteAssetTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.deleteAssetTag(tagId);
	}

	/**
	* Deletes the asset tag from the database. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag deleteAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.deleteAssetTag(assetTag);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetTagLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTag fetchAssetTag(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.fetchAssetTag(tagId);
	}

	/**
	* Returns the asset tag with the primary key.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag
	* @throws PortalException if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag getAssetTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getAssetTag(tagId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the asset tags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of asset tags
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getAssetTags(start, end);
	}

	/**
	* Returns the number of asset tags.
	*
	* @return the number of asset tags
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getAssetTagsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getAssetTagsCount();
	}

	/**
	* Updates the asset tag in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag updateAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.updateAssetTag(assetTag);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetEntryAssetTag(long entryId, long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.addAssetEntryAssetTag(entryId, tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetEntryAssetTag(long entryId,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.addAssetEntryAssetTag(entryId, assetTag);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetEntryAssetTags(long entryId, long[] tagIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.addAssetEntryAssetTags(entryId, tagIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetEntryAssetTags(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> AssetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.addAssetEntryAssetTags(entryId, AssetTags);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void clearAssetEntryAssetTags(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.clearAssetEntryAssetTags(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetEntryAssetTag(long entryId, long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.deleteAssetEntryAssetTag(entryId, tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetEntryAssetTag(long entryId,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.deleteAssetEntryAssetTag(entryId, assetTag);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetEntryAssetTags(long entryId, long[] tagIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.deleteAssetEntryAssetTags(entryId, tagIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetEntryAssetTags(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> AssetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.deleteAssetEntryAssetTags(entryId, AssetTags);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getAssetEntryAssetTags(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getAssetEntryAssetTags(entryId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getAssetEntryAssetTags(entryId, start,
			end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getAssetEntryAssetTagsCount(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getAssetEntryAssetTagsCount(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasAssetEntryAssetTag(long entryId, long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.hasAssetEntryAssetTag(entryId, tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasAssetEntryAssetTags(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.hasAssetEntryAssetTags(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void setAssetEntryAssetTags(long entryId, long[] tagIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.setAssetEntryAssetTags(entryId, tagIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _assetTagLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetTagLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds an asset tag.
	*
	* @param userId the primary key of the user adding the asset tag
	* @param name the asset tag's name
	* @param tagProperties the tag's properties
	* @param serviceContext the service context
	* @return the asset tag that was added
	* @throws PortalException if a user with the primary key could not be
	found, if an asset tag already exists with the name, or if a
	portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag addTag(long userId,
		java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.addTag(userId, name, tagProperties,
			serviceContext);
	}

	/**
	* Adds resources for the asset tag.
	*
	* @param tag the asset tag for which to add resources
	* @param addGroupPermissions whether to add group permissions
	* @param addGuestPermissions whether to add guest permissions
	* @throws PortalException if resources could not be added for the asset tag
	or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addTagResources(com.liferay.portlet.asset.model.AssetTag tag,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.addTagResources(tag, addGroupPermissions,
			addGuestPermissions);
	}

	/**
	* Adds resources for the asset tag using the group and guest permissions.
	*
	* @param tag the asset tag for which to add resources
	* @param groupPermissions the group permissions to be applied
	* @param guestPermissions the guest permissions to be applied
	* @throws PortalException if resources could not be added for the asset tag
	or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addTagResources(com.liferay.portlet.asset.model.AssetTag tag,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.addTagResources(tag, groupPermissions,
			guestPermissions);
	}

	/**
	* Returns the asset tags matching the group and names, creating new asset
	* tags matching the names if the group doesn't already have them.
	*
	* <p>
	* For each name, if an asset tag with the name doesn't already exist in the
	* group, this method creates a new asset tag with the name in the group.
	* </p>
	*
	* @param userId the primary key of the user checking the asset tags
	* @param group the group in which to check the asset tags
	* @param names the asset tag names
	* @return the asset tags matching the group and names and new asset tags
	matching the names that don't already exist in the group
	* @throws PortalException if a matching group could not be found or if a
	portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> checkTags(
		long userId, com.liferay.portal.model.Group group,
		java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.checkTags(userId, group, names);
	}

	/**
	* Returns the asset tags matching the group and names, creating new asset
	* tags matching the names if the group doesn't already have them.
	*
	* @param userId the primary key of the user checking the asset tags
	* @param groupId the primary key of the group in which check the asset
	tags
	* @param names the asset tag names
	* @throws PortalException if a matching group could not be found or if a
	portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void checkTags(long userId, long groupId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.checkTags(userId, groupId, names);
	}

	/**
	* Decrements the number of assets to which the asset tag has been applied.
	*
	* @param tagId the primary key of the asset tag
	* @param classNameId the class name ID of the entity to which the asset
	tag had been applied
	* @return the asset tag
	* @throws PortalException if an asset tag with the primary key could not be
	found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag decrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.decrementAssetCount(tagId, classNameId);
	}

	/**
	* Deletes all asset tags in the group.
	*
	* @param groupId the primary key of the group in which to delete all asset
	tags
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteGroupTags(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.deleteGroupTags(groupId);
	}

	/**
	* Deletes the asset tag.
	*
	* @param tag the asset tag to be deleted
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTag(com.liferay.portlet.asset.model.AssetTag tag)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.deleteTag(tag);
	}

	/**
	* Deletes the asset tag.
	*
	* @param tagId the primary key of the asset tag
	* @throws PortalException if no asset tag could be found with the primary
	key or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.deleteTag(tagId);
	}

	/**
	* Returns the asset tags of the asset entry.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset tags of the asset entry
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getEntryTags(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getEntryTags(entryId);
	}

	/**
	* Returns the asset tags in the groups.
	*
	* @param groupIds the primary keys of the groups
	* @return the asset tags in the groups
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupsTags(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getGroupsTags(groupIds);
	}

	/**
	* Returns the asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @return the asset tags in the group
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getGroupTags(groupId);
	}

	/**
	* Returns a range of all the asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getGroupTags(groupId, start, end);
	}

	/**
	* Returns the number of asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @return the number of asset tags in the group
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getGroupTagsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getGroupTagsCount(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getSocialActivityCounterOffsetTags(
		long groupId, java.lang.String socialActivityCounterName,
		int startOffset, int endOffset)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getSocialActivityCounterOffsetTags(groupId,
			socialActivityCounterName, startOffset, endOffset);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getSocialActivityCounterPeriodTags(
		long groupId, java.lang.String socialActivityCounterName,
		int startPeriod, int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getSocialActivityCounterPeriodTags(groupId,
			socialActivityCounterName, startPeriod, endPeriod);
	}

	/**
	* Returns the asset tag with the primary key.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag with the primary key
	* @throws PortalException if an asset tag with the primary key could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag getTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTag(tagId);
	}

	/**
	* Returns the asset tag with the name in the group.
	*
	* @param groupId the primary key of the group
	* @param name the name of the asset tag
	* @return the asset tag with the name in the group
	* @throws PortalException if a matching asset tag could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag getTag(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTag(groupId, name);
	}

	/**
	* Returns the primary keys of the asset tags with the names in the group.
	*
	* @param groupId the primary key of the group
	* @param names the names of the asset tags
	* @return the primary keys of the asset tags with the names in the group
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long[] getTagIds(long groupId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagIds(groupId, names);
	}

	/**
	* Returns the primary keys of the asset tags with the name in the groups.
	*
	* @param groupIds the primary keys of the groups
	* @param name the name of the asset tags
	* @return the primary keys of the asset tags with the name in the groups
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long[] getTagIds(long[] groupIds, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagIds(groupIds, name);
	}

	/**
	* Returns the primary keys of the asset tags with the names in the groups.
	*
	* @param groupIds the primary keys of the groups
	* @param names the names of the asset tags
	* @return the primary keys of the asset tags with the names in the groups
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long[] getTagIds(long[] groupIds, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagIds(groupIds, names);
	}

	/**
	* Returns the names of all the asset tags.
	*
	* @return the names of all the asset tags
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String[] getTagNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagNames();
	}

	/**
	* Returns the names of the asset tags of the entity.
	*
	* @param classNameId the class name ID of the entity
	* @param classPK the primary key of the entity
	* @return the names of the asset tags of the entity
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String[] getTagNames(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagNames(classNameId, classPK);
	}

	/**
	* Returns the names of the asset tags of the entity
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the names of the asset tags of the entity
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String[] getTagNames(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagNames(className, classPK);
	}

	/**
	* Returns all the asset tags.
	*
	* @return the asset tags
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTags();
	}

	/**
	* Returns the asset tags of the entity.
	*
	* @param classNameId the class name ID of the entity
	* @param classPK the primary key of the entity
	* @return the asset tags of the entity
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTags(classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTags(groupId, classNameId, name);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTags(groupId, classNameId, name, start,
			end);
	}

	/**
	* Returns the asset tags of the entity.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the asset tags of the entity
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTags(className, classPK);
	}

	@Override
	public int getTagsSize(long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagsSize(groupId, classNameId, name);
	}

	/**
	* Returns <code>true</code> if the group contains an asset tag with the
	* name.
	*
	* @param groupId the primary key of the group
	* @param name the name of the asset tag
	* @return <code>true</code> if the group contains an asset tag with the
	name; <code>false</code> otherwise.
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasTag(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.hasTag(groupId, name);
	}

	/**
	* Increments the number of assets to which the asset tag has been applied.
	*
	* @param tagId the primary key of the asset tag
	* @param classNameId the class name ID of the entity to which the asset
	tag is being applied
	* @return the asset tag
	* @throws PortalException if a asset tag with the primary key could not be
	found or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag incrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.incrementAssetCount(tagId, classNameId);
	}

	/**
	* Replaces all occurrences of the first asset tag with the second asset tag
	* and deletes the first asset tag.
	*
	* @param fromTagId the primary key of the asset tag to be replaced
	* @param toTagId the primary key of the asset tag to apply to the asset
	entries of the other asset tag
	* @param overrideProperties whether to override the properties of the
	second asset tag with the properties of the first asset tag
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void mergeTags(long fromTagId, long toTagId,
		boolean overrideProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.mergeTags(fromTagId, toTagId, overrideProperties);
	}

	/**
	* Returns the asset tags in the group whose names match the pattern and the
	* properties.
	*
	* @param groupId the primary key of the group
	* @param name the pattern to match
	* @param tagProperties the properties to match
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the asset tags in the group whose names match the pattern
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long groupId, java.lang.String name, java.lang.String[] tagProperties,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.search(groupId, name, tagProperties,
			start, end);
	}

	/**
	* Returns the asset tags in the groups whose names match the pattern and
	* the properties.
	*
	* @param groupIds the primary keys of the groups
	* @param name the pattern to match
	* @param tagProperties the properties to match
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the asset tags in the groups whose names match the pattern
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long[] groupIds, java.lang.String name,
		java.lang.String[] tagProperties, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.search(groupIds, name, tagProperties,
			start, end);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTag updateTag(long userId,
		long tagId, java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.updateTag(userId, tagId, name,
			tagProperties, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public AssetTagLocalService getWrappedAssetTagLocalService() {
		return _assetTagLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {
		_assetTagLocalService = assetTagLocalService;
	}

	@Override
	public AssetTagLocalService getWrappedService() {
		return _assetTagLocalService;
	}

	@Override
	public void setWrappedService(AssetTagLocalService assetTagLocalService) {
		_assetTagLocalService = assetTagLocalService;
	}

	private AssetTagLocalService _assetTagLocalService;
}