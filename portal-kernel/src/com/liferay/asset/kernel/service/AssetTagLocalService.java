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

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

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
	* Adds the asset tag to the database. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public AssetTag addAssetTag(AssetTag assetTag);

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
	@Indexable(type = IndexableType.REINDEX)
	public AssetTag addTag(long userId, long groupId, java.lang.String name,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new asset tag with the primary key. Does not add the asset tag to the database.
	*
	* @param tagId the primary key for the new asset tag
	* @return the new asset tag
	*/
	public AssetTag createAssetTag(long tagId);

	/**
	* Decrements the number of assets to which the asset tag has been applied.
	*
	* @param tagId the primary key of the asset tag
	* @param classNameId the class name ID of the entity to which the asset
	tag had been applied
	* @return the asset tag
	*/
	@Indexable(type = IndexableType.REINDEX)
	public AssetTag decrementAssetCount(long tagId, long classNameId)
		throws PortalException;

	/**
	* Deletes the asset tag from the database. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public AssetTag deleteAssetTag(AssetTag assetTag);

	/**
	* Deletes the asset tag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag that was removed
	* @throws PortalException if a asset tag with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public AssetTag deleteAssetTag(long tagId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetTag fetchAssetTag(long tagId);

	/**
	* Returns the asset tag matching the UUID and group.
	*
	* @param uuid the asset tag's UUID
	* @param groupId the primary key of the group
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetTag fetchAssetTagByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	/**
	* Returns the asset tag with the name in the group.
	*
	* @param groupId the primary key of the group
	* @param name the asset tag's name
	* @return the asset tag with the name in the group or <code>null</code> if
	it could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetTag fetchTag(long groupId, java.lang.String name);

	/**
	* Returns the asset tag with the primary key.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag
	* @throws PortalException if a asset tag with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetTag getAssetTag(long tagId) throws PortalException;

	/**
	* Returns the asset tag matching the UUID and group.
	*
	* @param uuid the asset tag's UUID
	* @param groupId the primary key of the group
	* @return the matching asset tag
	* @throws PortalException if a matching asset tag could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetTag getAssetTagByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	/**
	* Returns the asset tag with the name in the group.
	*
	* @param groupId the primary key of the group
	* @param name the name of the asset tag
	* @return the asset tag with the name in the group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetTag getTag(long groupId, java.lang.String name)
		throws PortalException;

	/**
	* Returns the asset tag with the primary key.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag with the primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetTag getTag(long tagId) throws PortalException;

	/**
	* Increments the number of assets to which the asset tag has been applied.
	*
	* @param tagId the primary key of the asset tag
	* @param classNameId the class name ID of the entity to which the asset
	tag is being applied
	* @return the asset tag
	*/
	@Indexable(type = IndexableType.REINDEX)
	public AssetTag incrementAssetCount(long tagId, long classNameId)
		throws PortalException;

	/**
	* Updates the asset tag in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public AssetTag updateAssetTag(AssetTag assetTag);

	@Indexable(type = IndexableType.REINDEX)
	public AssetTag updateTag(long userId, long tagId, java.lang.String name,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<AssetTag> searchTags(long[] groupIds,
		java.lang.String name, int start, int end, Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetEntryAssetTagsCount(long entryId);

	/**
	* Returns the number of asset tags.
	*
	* @return the number of asset tags
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetTagsCount();

	/**
	* Returns the number of asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @return the number of asset tags in the group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupTagsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTagsSize(long groupId, long classNameId, java.lang.String name);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

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
	public List<AssetTag> checkTags(long userId, Group group,
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
	public List<AssetTag> checkTags(long userId, long groupId,
		java.lang.String[] names) throws PortalException;

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getAssetEntryAssetTags(long entryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getAssetEntryAssetTags(long entryId, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getAssetEntryAssetTags(long entryId, int start,
		int end, OrderByComparator<AssetTag> orderByComparator);

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
	public List<AssetTag> getAssetTags(int start, int end);

	/**
	* Returns all the asset tags matching the UUID and company.
	*
	* @param uuid the UUID of the asset tags
	* @param companyId the primary key of the company
	* @return the matching asset tags, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getAssetTagsByUuidAndCompanyId(
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
	public List<AssetTag> getAssetTagsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTag> orderByComparator);

	/**
	* Returns the asset tags of the asset entry.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset tags of the asset entry
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getEntryTags(long entryId);

	/**
	* Returns the asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @return the asset tags in the group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getGroupTags(long groupId);

	/**
	* Returns a range of all the asset tags in the group.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getGroupTags(long groupId, int start, int end);

	/**
	* Returns the asset tags in the groups.
	*
	* @param groupIds the primary keys of the groups
	* @return the asset tags in the groups
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getGroupsTags(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getSocialActivityCounterOffsetTags(long groupId,
		java.lang.String socialActivityCounterName, int startOffset,
		int endOffset);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getSocialActivityCounterPeriodTags(long groupId,
		java.lang.String socialActivityCounterName, int startPeriod,
		int endPeriod);

	/**
	* Returns all the asset tags.
	*
	* @return the asset tags
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags();

	/**
	* Returns the asset tags of the entity.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the asset tags of the entity
	*/
	@ThreadLocalCachable
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(java.lang.String className, long classPK);

	/**
	* Returns the asset tags of the entity.
	*
	* @param classNameId the class name ID of the entity
	* @param classPK the primary key of the entity
	* @return the asset tags of the entity
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(long classNameId, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(long groupId, long classNameId,
		java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetTag> getTags(long groupId, long classNameId,
		java.lang.String name, int start, int end);

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
	public List<AssetTag> search(long groupId, java.lang.String name,
		int start, int end);

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
	public List<AssetTag> search(long[] groupIds, java.lang.String name,
		int start, int end);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	/**
	* Returns the entryIds of the asset entries associated with the asset tag.
	*
	* @param tagId the tagId of the asset tag
	* @return long[] the entryIds of asset entries associated with the asset tag
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getAssetEntryPrimaryKeys(long tagId);

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

	public void addAssetEntryAssetTag(long entryId, AssetTag assetTag);

	public void addAssetEntryAssetTag(long entryId, long tagId);

	public void addAssetEntryAssetTags(long entryId, List<AssetTag> assetTags);

	public void addAssetEntryAssetTags(long entryId, long[] tagIds);

	public void clearAssetEntryAssetTags(long entryId);

	public void deleteAssetEntryAssetTag(long entryId, AssetTag assetTag);

	public void deleteAssetEntryAssetTag(long entryId, long tagId);

	public void deleteAssetEntryAssetTags(long entryId, List<AssetTag> assetTags);

	public void deleteAssetEntryAssetTags(long entryId, long[] tagIds);

	/**
	* Deletes all asset tags in the group.
	*
	* @param groupId the primary key of the group in which to delete all asset
	tags
	*/
	public void deleteGroupTags(long groupId) throws PortalException;

	/**
	* Deletes the asset tag.
	*
	* @param tag the asset tag to be deleted
	*/
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteTag(AssetTag tag) throws PortalException;

	/**
	* Deletes the asset tag.
	*
	* @param tagId the primary key of the asset tag
	*/
	public void deleteTag(long tagId) throws PortalException;

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

	public void setAssetEntryAssetTags(long entryId, long[] tagIds);
}