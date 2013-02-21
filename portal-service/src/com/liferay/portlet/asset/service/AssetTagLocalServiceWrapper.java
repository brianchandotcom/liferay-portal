/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link AssetTagLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagLocalService
 * @generated
 */
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
	public com.liferay.portlet.asset.model.AssetTag deleteAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.deleteAssetTag(assetTag);
	}

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
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.dynamicQueryCount(dynamicQuery);
	}

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
	public com.liferay.portlet.asset.model.AssetTag getAssetTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getAssetTag(tagId);
	}

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
	public com.liferay.portlet.asset.model.AssetTag updateAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.updateAssetTag(assetTag);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _assetTagLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetTagLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* @param userId
	* @param name
	* @param tagProperties
	* @param serviceContext
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public com.liferay.portlet.asset.model.AssetTag addTag(long userId,
		java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.addTag(userId, name, tagProperties,
			serviceContext);
	}

	/**
	* @param tag
	* @param addGroupPermissions
	* @param addGuestPermissions
	* @throws PortalException
	* @throws SystemException
	*/
	public void addTagResources(com.liferay.portlet.asset.model.AssetTag tag,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.addTagResources(tag, addGroupPermissions,
			addGuestPermissions);
	}

	/**
	* @param tag
	* @param groupPermissions
	* @param guestPermissions
	* @throws PortalException
	* @throws SystemException
	*/
	public void addTagResources(com.liferay.portlet.asset.model.AssetTag tag,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.addTagResources(tag, groupPermissions,
			guestPermissions);
	}

	/**
	* @param userId
	* @param groupId
	* @param names
	* @throws PortalException
	* @throws SystemException
	*/
	public void checkTags(long userId, long groupId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.checkTags(userId, groupId, names);
	}

	/**
	* @param tagId
	* @param classNameId
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public com.liferay.portlet.asset.model.AssetTag decrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.decrementAssetCount(tagId, classNameId);
	}

	/**
	* @param tag
	* @throws PortalException
	* @throws SystemException
	*/
	public void deleteTag(com.liferay.portlet.asset.model.AssetTag tag)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.deleteTag(tag);
	}

	/**
	* @param tagId
	* @throws PortalException
	* @throws SystemException
	*/
	public void deleteTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.deleteTag(tagId);
	}

	/**
	* @param entryId
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getEntryTags(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getEntryTags(entryId);
	}

	/**
	* @param groupIds
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupsTags(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getGroupsTags(groupIds);
	}

	/**
	* @param groupId
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getGroupTags(groupId);
	}

	/**
	* @param groupId
	* @param start
	* @param end
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getGroupTags(groupId, start, end);
	}

	/**
	* @param groupId
	* @return
	* @throws SystemException
	*/
	public int getGroupTagsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getGroupTagsCount(groupId);
	}

	/**
	* @param groupId
	* @param socialActivityCounterName
	* @param startOffset
	* @param endOffset
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getSocialActivityCounterOffsetTags(
		long groupId, java.lang.String socialActivityCounterName,
		int startOffset, int endOffset)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getSocialActivityCounterOffsetTags(groupId,
			socialActivityCounterName, startOffset, endOffset);
	}

	/**
	* @param groupId
	* @param socialActivityCounterName
	* @param startPeriod
	* @param endPeriod
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getSocialActivityCounterPeriodTags(
		long groupId, java.lang.String socialActivityCounterName,
		int startPeriod, int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getSocialActivityCounterPeriodTags(groupId,
			socialActivityCounterName, startPeriod, endPeriod);
	}

	/**
	* @param tagId
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public com.liferay.portlet.asset.model.AssetTag getTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTag(tagId);
	}

	/**
	* @param groupId
	* @param name
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public com.liferay.portlet.asset.model.AssetTag getTag(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTag(groupId, name);
	}

	/**
	* @param groupId
	* @param names
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public long[] getTagIds(long groupId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagIds(groupId, names);
	}

	/**
	* @param groupIds
	* @param name
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public long[] getTagIds(long[] groupIds, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagIds(groupIds, name);
	}

	/**
	* @param groupIds
	* @param names
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public long[] getTagIds(long[] groupIds, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagIds(groupIds, names);
	}

	/**
	* @return
	* @throws SystemException
	*/
	public java.lang.String[] getTagNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagNames();
	}

	/**
	* @param classNameId
	* @param classPK
	* @return
	* @throws SystemException
	*/
	public java.lang.String[] getTagNames(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagNames(classNameId, classPK);
	}

	/**
	* @param className
	* @param classPK
	* @return
	* @throws SystemException
	*/
	public java.lang.String[] getTagNames(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagNames(className, classPK);
	}

	/**
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTags();
	}

	/**
	* @param classNameId
	* @param classPK
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTags(classNameId, classPK);
	}

	/**
	* @param groupId
	* @param classNameId
	* @param name
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTags(groupId, classNameId, name);
	}

	/**
	* @param groupId
	* @param classNameId
	* @param name
	* @param start
	* @param end
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTags(groupId, classNameId, name, start,
			end);
	}

	/**
	* @param className
	* @param classPK
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTags(className, classPK);
	}

	/**
	* @param groupId
	* @param classNameId
	* @param name
	* @return
	* @throws SystemException
	*/
	public int getTagsSize(long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.getTagsSize(groupId, classNameId, name);
	}

	/**
	* @param groupId
	* @param name
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public boolean hasTag(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.hasTag(groupId, name);
	}

	/**
	* @param tagId
	* @param classNameId
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public com.liferay.portlet.asset.model.AssetTag incrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.incrementAssetCount(tagId, classNameId);
	}

	/**
	* @param fromTagId
	* @param toTagId
	* @param overrideProperties
	* @throws PortalException
	* @throws SystemException
	*/
	public void mergeTags(long fromTagId, long toTagId,
		boolean overrideProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagLocalService.mergeTags(fromTagId, toTagId, overrideProperties);
	}

	/**
	* @param groupId
	* @param name
	* @param tagProperties
	* @param start
	* @param end
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long groupId, java.lang.String name, java.lang.String[] tagProperties,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.search(groupId, name, tagProperties,
			start, end);
	}

	/**
	* @param groupIds
	* @param name
	* @param tagProperties
	* @param start
	* @param end
	* @return
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long[] groupIds, java.lang.String name,
		java.lang.String[] tagProperties, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.search(groupIds, name, tagProperties,
			start, end);
	}

	/**
	* @param userId
	* @param tagId
	* @param name
	* @param tagProperties
	* @param serviceContext
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public com.liferay.portlet.asset.model.AssetTag updateTag(long userId,
		long tagId, java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagLocalService.updateTag(userId, tagId, name,
			tagProperties, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public AssetTagLocalService getWrappedAssetTagLocalService() {
		return _assetTagLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {
		_assetTagLocalService = assetTagLocalService;
	}

	public AssetTagLocalService getWrappedService() {
		return _assetTagLocalService;
	}

	public void setWrappedService(AssetTagLocalService assetTagLocalService) {
		_assetTagLocalService = assetTagLocalService;
	}

	private AssetTagLocalService _assetTagLocalService;
}