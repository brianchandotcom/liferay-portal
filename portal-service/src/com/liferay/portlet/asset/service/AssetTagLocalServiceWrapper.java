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

	@Override
	public void addAssetEntryAssetTag(long entryId,
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		_assetTagLocalService.addAssetEntryAssetTag(entryId, assetTag);
	}

	@Override
	public void addAssetEntryAssetTag(long entryId, long tagId) {
		_assetTagLocalService.addAssetEntryAssetTag(entryId, tagId);
	}

	@Override
	public void addAssetEntryAssetTags(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> AssetTags) {
		_assetTagLocalService.addAssetEntryAssetTags(entryId, AssetTags);
	}

	@Override
	public void addAssetEntryAssetTags(long entryId, long[] tagIds) {
		_assetTagLocalService.addAssetEntryAssetTags(entryId, tagIds);
	}

	/**
	* Adds the asset tag to the database. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was added
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag addAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		return _assetTagLocalService.addAssetTag(assetTag);
	}

	/**
	* Adds an asset tag.
	*
	* @param userId the primary key of the user adding the asset tag
	* @param groupId the primary key of the group in which the asset tag is to
	be added
	* @param name the asset tag's name
	* @param serviceContext the service context to be applied
	* @return the asset tag that was added
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag addTag(long userId,
		long groupId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.addTag(userId, groupId, name,
			serviceContext);
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
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> checkTags(
		long userId, com.liferay.portal.model.Group group,
		java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException {
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
	* @return the asset tags matching the group and names and new asset tags
	matching the names that don't already exist in the group
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> checkTags(
		long userId, long groupId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.checkTags(userId, groupId, names);
	}

	@Override
	public void clearAssetEntryAssetTags(long entryId) {
		_assetTagLocalService.clearAssetEntryAssetTags(entryId);
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
	* Decrements the number of assets to which the asset tag has been applied.
	*
	* @param tagId the primary key of the asset tag
	* @param classNameId the class name ID of the entity to which the asset
	tag had been applied
	* @return the asset tag
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag decrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.decrementAssetCount(tagId, classNameId);
	}

	@Override
	public void deleteAssetEntryAssetTag(long entryId,
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		_assetTagLocalService.deleteAssetEntryAssetTag(entryId, assetTag);
	}

	@Override
	public void deleteAssetEntryAssetTag(long entryId, long tagId) {
		_assetTagLocalService.deleteAssetEntryAssetTag(entryId, tagId);
	}

	@Override
	public void deleteAssetEntryAssetTags(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> AssetTags) {
		_assetTagLocalService.deleteAssetEntryAssetTags(entryId, AssetTags);
	}

	@Override
	public void deleteAssetEntryAssetTags(long entryId, long[] tagIds) {
		_assetTagLocalService.deleteAssetEntryAssetTags(entryId, tagIds);
	}

	/**
	* Deletes the asset tag from the database. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was removed
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag deleteAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		return _assetTagLocalService.deleteAssetTag(assetTag);
	}

	/**
	* Deletes the asset tag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag that was removed
	* @throws PortalException if a asset tag with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag deleteAssetTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.deleteAssetTag(tagId);
	}

	/**
	* Deletes all asset tags in the group.
	*
	* @param groupId the primary key of the group in which to delete all asset
	tags
	*/
	@Override
	public void deleteGroupTags(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetTagLocalService.deleteGroupTags(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the asset tag.
	*
	* @param tag the asset tag to be deleted
	*/
	@Override
	public void deleteTag(com.liferay.portlet.asset.model.AssetTag tag)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetTagLocalService.deleteTag(tag);
	}

	/**
	* Deletes the asset tag.
	*
	* @param tagId the primary key of the asset tag
	*/
	@Override
	public void deleteTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetTagLocalService.deleteTag(tagId);
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _assetTagLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _assetTagLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _assetTagLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTag fetchAssetTag(long tagId) {
		return _assetTagLocalService.fetchAssetTag(tagId);
	}

	/**
	* Returns the asset tag matching the UUID and group.
	*
	* @param uuid the asset tag's UUID
	* @param groupId the primary key of the group
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag fetchAssetTagByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _assetTagLocalService.fetchAssetTagByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the asset tag with the name in the group.
	*
	* @param groupId the primary key of the group
	* @param name the asset tag's name
	* @return the asset tag with the name in the group or <code>null</code> if
	it could not be found
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag fetchTag(long groupId,
		java.lang.String name) {
		return _assetTagLocalService.fetchTag(groupId, name);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _assetTagLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId) {
		return _assetTagLocalService.getAssetEntryAssetTags(entryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId, int start, int end) {
		return _assetTagLocalService.getAssetEntryAssetTags(entryId, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator) {
		return _assetTagLocalService.getAssetEntryAssetTags(entryId, start,
			end, orderByComparator);
	}

	@Override
	public int getAssetEntryAssetTagsCount(long entryId) {
		return _assetTagLocalService.getAssetEntryAssetTagsCount(entryId);
	}

	/**
	* Returns the entryIds of the asset entries associated with the asset tag.
	*
	* @param tagId the tagId of the asset tag
	* @return long[] the entryIds of asset entries associated with the asset tag
	*/
	@Override
	public long[] getAssetEntryPrimaryKeys(long tagId) {
		return _assetTagLocalService.getAssetEntryPrimaryKeys(tagId);
	}

	/**
	* Returns the asset tag with the primary key.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag
	* @throws PortalException if a asset tag with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag getAssetTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.getAssetTag(tagId);
	}

	/**
	* Returns the asset tag matching the UUID and group.
	*
	* @param uuid the asset tag's UUID
	* @param groupId the primary key of the group
	* @return the matching asset tag
	* @throws PortalException if a matching asset tag could not be found
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag getAssetTagByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.getAssetTagByUuidAndGroupId(uuid, groupId);
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
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		int start, int end) {
		return _assetTagLocalService.getAssetTags(start, end);
	}

	/**
	* Returns all the asset tags matching the UUID and company.
	*
	* @param uuid the UUID of the asset tags
	* @param companyId the primary key of the company
	* @return the matching asset tags, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTagsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _assetTagLocalService.getAssetTagsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of asset tags matching the UUID and company.
	*
	* @param uuid the UUID of the asset tags
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching asset tags, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTagsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator) {
		return _assetTagLocalService.getAssetTagsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of asset tags.
	*
	* @return the number of asset tags
	*/
	@Override
	public int getAssetTagsCount() {
		return _assetTagLocalService.getAssetTagsCount();
	}

	/**
	* Returns the asset tags of the asset entry.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset tags of the asset entry
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getEntryTags(
		long entryId) {
		return _assetTagLocalService.getEntryTags(entryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _assetTagLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* Returns the asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @return the asset tags in the group
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId) {
		return _assetTagLocalService.getGroupTags(groupId);
	}

	/**
	* Returns a range of all the asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId, int start, int end) {
		return _assetTagLocalService.getGroupTags(groupId, start, end);
	}

	/**
	* Returns the number of asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @return the number of asset tags in the group
	*/
	@Override
	public int getGroupTagsCount(long groupId) {
		return _assetTagLocalService.getGroupTagsCount(groupId);
	}

	/**
	* Returns the asset tags in the groups.
	*
	* @param groupIds the primary keys of the groups
	* @return the asset tags in the groups
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupsTags(
		long[] groupIds) {
		return _assetTagLocalService.getGroupsTags(groupIds);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _assetTagLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _assetTagLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getSocialActivityCounterOffsetTags(
		long groupId, java.lang.String socialActivityCounterName,
		int startOffset, int endOffset) {
		return _assetTagLocalService.getSocialActivityCounterOffsetTags(groupId,
			socialActivityCounterName, startOffset, endOffset);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getSocialActivityCounterPeriodTags(
		long groupId, java.lang.String socialActivityCounterName,
		int startPeriod, int endPeriod) {
		return _assetTagLocalService.getSocialActivityCounterPeriodTags(groupId,
			socialActivityCounterName, startPeriod, endPeriod);
	}

	/**
	* Returns the asset tag with the name in the group.
	*
	* @param groupId the primary key of the group
	* @param name the name of the asset tag
	* @return the asset tag with the name in the group
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag getTag(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.getTag(groupId, name);
	}

	/**
	* Returns the asset tag with the primary key.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag with the primary key
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag getTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.getTag(tagId);
	}

	/**
	* Returns the primary keys of the asset tags with the names in the group.
	*
	* @param groupId the primary key of the group
	* @param names the names of the asset tags
	* @return the primary keys of the asset tags with the names in the group
	*/
	@Override
	public long[] getTagIds(long groupId, java.lang.String[] names) {
		return _assetTagLocalService.getTagIds(groupId, names);
	}

	/**
	* Returns the primary keys of the asset tags with the name in the groups.
	*
	* @param groupIds the primary keys of the groups
	* @param name the name of the asset tags
	* @return the primary keys of the asset tags with the name in the groups
	*/
	@Override
	public long[] getTagIds(long[] groupIds, java.lang.String name) {
		return _assetTagLocalService.getTagIds(groupIds, name);
	}

	/**
	* Returns the primary keys of the asset tags with the names in the groups.
	*
	* @param groupIds the primary keys of the groups
	* @param names the names of the asset tags
	* @return the primary keys of the asset tags with the names in the groups
	*/
	@Override
	public long[] getTagIds(long[] groupIds, java.lang.String[] names) {
		return _assetTagLocalService.getTagIds(groupIds, names);
	}

	/**
	* Returns the names of all the asset tags.
	*
	* @return the names of all the asset tags
	*/
	@Override
	public java.lang.String[] getTagNames() {
		return _assetTagLocalService.getTagNames();
	}

	/**
	* Returns the names of the asset tags of the entity
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the names of the asset tags of the entity
	*/
	@Override
	public java.lang.String[] getTagNames(java.lang.String className,
		long classPK) {
		return _assetTagLocalService.getTagNames(className, classPK);
	}

	/**
	* Returns the names of the asset tags of the entity.
	*
	* @param classNameId the class name ID of the entity
	* @param classPK the primary key of the entity
	* @return the names of the asset tags of the entity
	*/
	@Override
	public java.lang.String[] getTagNames(long classNameId, long classPK) {
		return _assetTagLocalService.getTagNames(classNameId, classPK);
	}

	/**
	* Returns all the asset tags.
	*
	* @return the asset tags
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags() {
		return _assetTagLocalService.getTags();
	}

	/**
	* Returns the asset tags of the entity.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the asset tags of the entity
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		java.lang.String className, long classPK) {
		return _assetTagLocalService.getTags(className, classPK);
	}

	/**
	* Returns the asset tags of the entity.
	*
	* @param classNameId the class name ID of the entity
	* @param classPK the primary key of the entity
	* @return the asset tags of the entity
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long classNameId, long classPK) {
		return _assetTagLocalService.getTags(classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name) {
		return _assetTagLocalService.getTags(groupId, classNameId, name);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name, int start,
		int end) {
		return _assetTagLocalService.getTags(groupId, classNameId, name, start,
			end);
	}

	@Override
	public int getTagsSize(long groupId, long classNameId, java.lang.String name) {
		return _assetTagLocalService.getTagsSize(groupId, classNameId, name);
	}

	@Override
	public boolean hasAssetEntryAssetTag(long entryId, long tagId) {
		return _assetTagLocalService.hasAssetEntryAssetTag(entryId, tagId);
	}

	@Override
	public boolean hasAssetEntryAssetTags(long entryId) {
		return _assetTagLocalService.hasAssetEntryAssetTags(entryId);
	}

	/**
	* Returns <code>true</code> if the group contains an asset tag with the
	* name.
	*
	* @param groupId the primary key of the group
	* @param name the name of the asset tag
	* @return <code>true</code> if the group contains an asset tag with the
	name; <code>false</code> otherwise.
	*/
	@Override
	public boolean hasTag(long groupId, java.lang.String name) {
		return _assetTagLocalService.hasTag(groupId, name);
	}

	/**
	* Increments the number of assets to which the asset tag has been applied.
	*
	* @param tagId the primary key of the asset tag
	* @param classNameId the class name ID of the entity to which the asset
	tag is being applied
	* @return the asset tag
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag incrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.incrementAssetCount(tagId, classNameId);
	}

	/**
	* Replaces all occurrences of the first asset tag with the second asset tag
	* and deletes the first asset tag.
	*
	* @param fromTagId the primary key of the asset tag to be replaced
	* @param toTagId the primary key of the asset tag to apply to the asset
	entries of the other asset tag
	*/
	@Override
	public void mergeTags(long fromTagId, long toTagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetTagLocalService.mergeTags(fromTagId, toTagId);
	}

	/**
	* Returns the asset tags in the group whose names match the pattern.
	*
	* @param groupId the primary key of the group
	* @param name the pattern to match
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the asset tags in the group whose names match the pattern
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long groupId, java.lang.String name, int start, int end) {
		return _assetTagLocalService.search(groupId, name, start, end);
	}

	/**
	* Returns the asset tags in the groups whose names match the pattern.
	*
	* @param groupIds the primary keys of the groups
	* @param name the pattern to match
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the asset tags in the groups whose names match the pattern
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long[] groupIds, java.lang.String name, int start, int end) {
		return _assetTagLocalService.search(groupIds, name, start, end);
	}

	@Override
	public void setAssetEntryAssetTags(long entryId, long[] tagIds) {
		_assetTagLocalService.setAssetEntryAssetTags(entryId, tagIds);
	}

	/**
	* Updates the asset tag in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was updated
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTag updateAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		return _assetTagLocalService.updateAssetTag(assetTag);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTag updateTag(long userId,
		long tagId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.updateTag(userId, tagId, name,
			serviceContext);
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