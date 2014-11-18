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

	@Override
	public com.liferay.portlet.asset.model.AssetTag addTag(long userId,
		java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.addTag(userId, name, tagProperties,
			serviceContext);
	}

	@Override
	public void addTagResources(com.liferay.portlet.asset.model.AssetTag tag,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetTagLocalService.addTagResources(tag, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addTagResources(com.liferay.portlet.asset.model.AssetTag tag,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetTagLocalService.addTagResources(tag, groupPermissions,
			guestPermissions);
	}

	/**
	* Returns the tags matching the group and names, creating new tags with the
	* names if the group doesn't already have them.
	*
	* <p>
	* For each name, if a tag with that name doesn't already exist for the
	* group, this method creates a new tag with that name for the group. If a
	* tag with that name already exists in the company group, this method
	* copies that company group's tag's properties to the group's new tag.
	* </p>
	*
	* @param userId the primary key of the user
	* @param group ID the primary key of the tag's group
	* @param names the tag names
	* @return the tags matching the group and names and new tags matching the
	names that don't already exist for the group
	* @throws PortalException if a matching group could not be found, if the
	tag's key or value were invalid, or if a portal exception
	occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> checkTags(
		long userId, com.liferay.portal.model.Group group,
		java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.checkTags(userId, group, names);
	}

	@Override
	public void checkTags(long userId, long groupId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetTagLocalService.checkTags(userId, groupId, names);
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

	@Override
	public void deleteTag(com.liferay.portlet.asset.model.AssetTag tag)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetTagLocalService.deleteTag(tag);
	}

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
	* Returns the number of asset tags.
	*
	* @return the number of asset tags
	*/
	@Override
	public int getAssetTagsCount() {
		return _assetTagLocalService.getAssetTagsCount();
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

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getEntryTags(
		long entryId) {
		return _assetTagLocalService.getEntryTags(entryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId) {
		return _assetTagLocalService.getGroupTags(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId, int start, int end) {
		return _assetTagLocalService.getGroupTags(groupId, start, end);
	}

	@Override
	public int getGroupTagsCount(long groupId) {
		return _assetTagLocalService.getGroupTagsCount(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupsTags(
		long[] groupIds) {
		return _assetTagLocalService.getGroupsTags(groupIds);
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

	@Override
	public com.liferay.portlet.asset.model.AssetTag getTag(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.getTag(groupId, name);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTag getTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.getTag(tagId);
	}

	@Override
	public long[] getTagIds(long groupId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.getTagIds(groupId, names);
	}

	@Override
	public long[] getTagIds(long[] groupIds, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.getTagIds(groupIds, name);
	}

	@Override
	public long[] getTagIds(long[] groupIds, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.getTagIds(groupIds, names);
	}

	@Override
	public java.lang.String[] getTagNames() {
		return _assetTagLocalService.getTagNames();
	}

	@Override
	public java.lang.String[] getTagNames(java.lang.String className,
		long classPK) {
		return _assetTagLocalService.getTagNames(className, classPK);
	}

	@Override
	public java.lang.String[] getTagNames(long classNameId, long classPK) {
		return _assetTagLocalService.getTagNames(classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags() {
		return _assetTagLocalService.getTags();
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		java.lang.String className, long classPK) {
		return _assetTagLocalService.getTags(className, classPK);
	}

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

	@Override
	public boolean hasTag(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.hasTag(groupId, name);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTag incrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.incrementAssetCount(tagId, classNameId);
	}

	@Override
	public void mergeTags(long fromTagId, long toTagId,
		boolean overrideProperties)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetTagLocalService.mergeTags(fromTagId, toTagId, overrideProperties);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long groupId, java.lang.String name, java.lang.String[] tagProperties,
		int start, int end) {
		return _assetTagLocalService.search(groupId, name, tagProperties,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long[] groupIds, java.lang.String name,
		java.lang.String[] tagProperties, int start, int end) {
		return _assetTagLocalService.search(groupIds, name, tagProperties,
			start, end);
	}

	@Override
	public void setAssetEntryAssetTags(long entryId, long[] tagIds) {
		_assetTagLocalService.setAssetEntryAssetTags(entryId, tagIds);
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
		long tagId, java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetTagLocalService.updateTag(userId, tagId, name,
			tagProperties, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public AssetTagLocalService getWrappedAssetTagLocalService() {
		return _assetTagLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
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