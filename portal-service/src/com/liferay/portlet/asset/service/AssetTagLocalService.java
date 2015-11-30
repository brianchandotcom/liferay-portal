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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for AssetTag. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagLocalServiceUtil
 * @see com.liferay.portlet.asset.service.base.AssetTagLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetTagLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AssetTagLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetTagLocalServiceUtil} to access the asset tag local service. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetTagLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addAssetEntryAssetTag(long entryId,
		com.liferay.portlet.asset.model.AssetTag assetTag);

	public void addAssetEntryAssetTag(long entryId, long tagId);

	public void addAssetEntryAssetTags(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> AssetTags);

	public void addAssetEntryAssetTags(long entryId, long[] tagIds);

	/**
	* Adds the asset tag to the database. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.asset.model.AssetTag addAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag);

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
	public com.liferay.portlet.asset.model.AssetTag addTag(long userId,
		long groupId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

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
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> checkTags(
		long userId, com.liferay.portal.model.Group group,
		java.lang.String[] names) throws PortalException;

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
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> checkTags(
		long userId, long groupId, java.lang.String[] names)
		throws PortalException;

	public void clearAssetEntryAssetTags(long entryId);

	/**
	* Creates a new asset tag with the primary key. Does not add the asset tag to the database.
	*
	* @param tagId the primary key for the new asset tag
	* @return the new asset tag
	*/
	public com.liferay.portlet.asset.model.AssetTag createAssetTag(long tagId);

	/**
	* Decrements the number of assets to which the asset tag has been applied.
	*
	* @param tagId the primary key of the asset tag
	* @param classNameId the class name ID of the entity to which the asset
	tag had been applied
	* @return the asset tag
	*/
	public com.liferay.portlet.asset.model.AssetTag decrementAssetCount(
		long tagId, long classNameId) throws PortalException;

	public void deleteAssetEntryAssetTag(long entryId,
		com.liferay.portlet.asset.model.AssetTag assetTag);

	public void deleteAssetEntryAssetTag(long entryId, long tagId);

	public void deleteAssetEntryAssetTags(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> AssetTags);

	public void deleteAssetEntryAssetTags(long entryId, long[] tagIds);

	/**
	* Deletes the asset tag from the database. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.asset.model.AssetTag deleteAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag);

	/**
	* Deletes the asset tag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag that was removed
	* @throws PortalException if a asset tag with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.asset.model.AssetTag deleteAssetTag(long tagId)
		throws PortalException;

	/**
	* Deletes all asset tags in the group.
	*
	* @param groupId the primary key of the group in which to delete all asset
	tags
	*/
	public void deleteGroupTags(long groupId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	/**
	* Deletes the asset tag.
	*
	* @param tag the asset tag to be deleted
	*/
	@com.liferay.portal.kernel.systemevent.SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteTag(com.liferay.portlet.asset.model.AssetTag tag)
		throws PortalException;

	/**
	* Deletes the asset tag.
	*
	* @param tagId the primary key of the asset tag
	*/
	public void deleteTag(long tagId) throws PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetTag fetchAssetTag(long tagId);

	/**
	* Returns the asset tag matching the UUID and group.
	*
	* @param uuid the asset tag's UUID
	* @param groupId the primary key of the group
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetTag fetchAssetTagByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	/**
	* Returns the asset tag with the name in the group.
	*
	* @param groupId the primary key of the group
	* @param name the asset tag's name
	* @return the asset tag with the name in the group or <code>null</code> if
	it could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetTag fetchTag(long groupId,
		java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetEntryAssetTagsCount(long entryId);

	/**
	* Returns the entryIds of the asset entries associated with the asset tag.
	*
	* @param tagId the tagId of the asset tag
	* @return long[] the entryIds of asset entries associated with the asset tag
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getAssetEntryPrimaryKeys(long tagId);

	/**
	* Returns the asset tag with the primary key.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag
	* @throws PortalException if a asset tag with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetTag getAssetTag(long tagId)
		throws PortalException;

	/**
	* Returns the asset tag matching the UUID and group.
	*
	* @param uuid the asset tag's UUID
	* @param groupId the primary key of the group
	* @return the matching asset tag
	* @throws PortalException if a matching asset tag could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetTag getAssetTagByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		int start, int end);

	/**
	* Returns all the asset tags matching the UUID and company.
	*
	* @param uuid the UUID of the asset tags
	* @param companyId the primary key of the company
	* @return the matching asset tags, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTagsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTagsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns the number of asset tags.
	*
	* @return the number of asset tags
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetTagsCount();

	/**
	* Returns the asset tags of the asset entry.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset tags of the asset entry
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getEntryTags(
		long entryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

	/**
	* Returns the asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @return the asset tags in the group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId);

	/**
	* Returns a range of all the asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId, int start, int end);

	/**
	* Returns the number of asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @return the number of asset tags in the group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupTagsCount(long groupId);

	/**
	* Returns the asset tags in the groups.
	*
	* @param groupIds the primary keys of the groups
	* @return the asset tags in the groups
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupsTags(
		long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getSocialActivityCounterOffsetTags(
		long groupId, java.lang.String socialActivityCounterName,
		int startOffset, int endOffset);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getSocialActivityCounterPeriodTags(
		long groupId, java.lang.String socialActivityCounterName,
		int startPeriod, int endPeriod);

	/**
	* Returns the asset tag with the name in the group.
	*
	* @param groupId the primary key of the group
	* @param name the name of the asset tag
	* @return the asset tag with the name in the group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetTag getTag(long groupId,
		java.lang.String name) throws PortalException;

	/**
	* Returns the asset tag with the primary key.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag with the primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetTag getTag(long tagId)
		throws PortalException;

	/**
	* Returns the primary keys of the asset tags with the names in the group.
	*
	* @param groupId the primary key of the group
	* @param names the names of the asset tags
	* @return the primary keys of the asset tags with the names in the group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getTagIds(long groupId, java.lang.String[] names);

	/**
	* Returns the primary keys of the asset tags with the name in the groups.
	*
	* @param groupIds the primary keys of the groups
	* @param name the name of the asset tags
	* @return the primary keys of the asset tags with the name in the groups
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getTagIds(long[] groupIds, java.lang.String name);

	/**
	* Returns the primary keys of the asset tags with the names in the groups.
	*
	* @param groupIds the primary keys of the groups
	* @param names the names of the asset tags
	* @return the primary keys of the asset tags with the names in the groups
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getTagIds(long[] groupIds, java.lang.String[] names);

	/**
	* Returns the names of all the asset tags.
	*
	* @return the names of all the asset tags
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTagNames();

	/**
	* Returns the names of the asset tags of the entity
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the names of the asset tags of the entity
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTagNames(java.lang.String className,
		long classPK);

	/**
	* Returns the names of the asset tags of the entity.
	*
	* @param classNameId the class name ID of the entity
	* @param classPK the primary key of the entity
	* @return the names of the asset tags of the entity
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTagNames(long classNameId, long classPK);

	/**
	* Returns all the asset tags.
	*
	* @return the asset tags
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags();

	/**
	* Returns the asset tags of the entity.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the asset tags of the entity
	*/
	@com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		java.lang.String className, long classPK);

	/**
	* Returns the asset tags of the entity.
	*
	* @param classNameId the class name ID of the entity
	* @param classPK the primary key of the entity
	* @return the asset tags of the entity
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long classNameId, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTagsSize(long groupId, long classNameId, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasAssetEntryAssetTag(long entryId, long tagId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasAssetEntryAssetTags(long entryId);

	/**
	* Returns <code>true</code> if the group contains an asset tag with the
	* name.
	*
	* @param groupId the primary key of the group
	* @param name the name of the asset tag
	* @return <code>true</code> if the group contains an asset tag with the
	name; <code>false</code> otherwise.
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasTag(long groupId, java.lang.String name);

	/**
	* Increments the number of assets to which the asset tag has been applied.
	*
	* @param tagId the primary key of the asset tag
	* @param classNameId the class name ID of the entity to which the asset
	tag is being applied
	* @return the asset tag
	*/
	public com.liferay.portlet.asset.model.AssetTag incrementAssetCount(
		long tagId, long classNameId) throws PortalException;

	/**
	* Replaces all occurrences of the first asset tag with the second asset tag
	* and deletes the first asset tag.
	*
	* @param fromTagId the primary key of the asset tag to be replaced
	* @param toTagId the primary key of the asset tag to apply to the asset
	entries of the other asset tag
	*/
	public void mergeTags(long fromTagId, long toTagId)
		throws PortalException;

	/**
	* Returns the asset tags in the group whose names match the pattern.
	*
	* @param groupId the primary key of the group
	* @param name the pattern to match
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the asset tags in the group whose names match the pattern
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long groupId, java.lang.String name, int start, int end);

	/**
	* Returns the asset tags in the groups whose names match the pattern.
	*
	* @param groupIds the primary keys of the groups
	* @param name the pattern to match
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the asset tags in the groups whose names match the pattern
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long[] groupIds, java.lang.String name, int start, int end);

	public void setAssetEntryAssetTags(long entryId, long[] tagIds);

	/**
	* Updates the asset tag in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.asset.model.AssetTag updateAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag);

	public com.liferay.portlet.asset.model.AssetTag updateTag(long userId,
		long tagId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}