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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for AssetTag. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetTagLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagLocalService
 * @see com.liferay.portlet.asset.service.base.AssetTagLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetTagLocalServiceImpl
 * @generated
 */
@ProviderType
public class AssetTagLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetTagLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addAssetEntryAssetTag(long entryId,
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		getService().addAssetEntryAssetTag(entryId, assetTag);
	}

	public static void addAssetEntryAssetTag(long entryId, long tagId) {
		getService().addAssetEntryAssetTag(entryId, tagId);
	}

	public static void addAssetEntryAssetTags(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> AssetTags) {
		getService().addAssetEntryAssetTags(entryId, AssetTags);
	}

	public static void addAssetEntryAssetTags(long entryId, long[] tagIds) {
		getService().addAssetEntryAssetTags(entryId, tagIds);
	}

	/**
	* Adds the asset tag to the database. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was added
	*/
	public static com.liferay.portlet.asset.model.AssetTag addAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		return getService().addAssetTag(assetTag);
	}

	public static com.liferay.portlet.asset.model.AssetTag addTag(long userId,
		java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addTag(userId, name, tagProperties, serviceContext);
	}

	public static void addTagResources(
		com.liferay.portlet.asset.model.AssetTag tag,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addTagResources(tag, addGroupPermissions, addGuestPermissions);
	}

	public static void addTagResources(
		com.liferay.portlet.asset.model.AssetTag tag,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addTagResources(tag, groupPermissions, guestPermissions);
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
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> checkTags(
		long userId, com.liferay.portal.model.Group group,
		java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().checkTags(userId, group, names);
	}

	public static void checkTags(long userId, long groupId,
		java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkTags(userId, groupId, names);
	}

	public static void clearAssetEntryAssetTags(long entryId) {
		getService().clearAssetEntryAssetTags(entryId);
	}

	/**
	* Creates a new asset tag with the primary key. Does not add the asset tag to the database.
	*
	* @param tagId the primary key for the new asset tag
	* @return the new asset tag
	*/
	public static com.liferay.portlet.asset.model.AssetTag createAssetTag(
		long tagId) {
		return getService().createAssetTag(tagId);
	}

	public static com.liferay.portlet.asset.model.AssetTag decrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().decrementAssetCount(tagId, classNameId);
	}

	public static void deleteAssetEntryAssetTag(long entryId,
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		getService().deleteAssetEntryAssetTag(entryId, assetTag);
	}

	public static void deleteAssetEntryAssetTag(long entryId, long tagId) {
		getService().deleteAssetEntryAssetTag(entryId, tagId);
	}

	public static void deleteAssetEntryAssetTags(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> AssetTags) {
		getService().deleteAssetEntryAssetTags(entryId, AssetTags);
	}

	public static void deleteAssetEntryAssetTags(long entryId, long[] tagIds) {
		getService().deleteAssetEntryAssetTags(entryId, tagIds);
	}

	/**
	* Deletes the asset tag from the database. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was removed
	*/
	public static com.liferay.portlet.asset.model.AssetTag deleteAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		return getService().deleteAssetTag(assetTag);
	}

	/**
	* Deletes the asset tag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag that was removed
	* @throws PortalException if a asset tag with the primary key could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetTag deleteAssetTag(
		long tagId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAssetTag(tagId);
	}

	public static void deleteGroupTags(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteGroupTags(groupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteTag(com.liferay.portlet.asset.model.AssetTag tag)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTag(tag);
	}

	public static void deleteTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTag(tagId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.asset.model.AssetTag fetchAssetTag(
		long tagId) {
		return getService().fetchAssetTag(tagId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId) {
		return getService().getAssetEntryAssetTags(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId, int start, int end) {
		return getService().getAssetEntryAssetTags(entryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetEntryAssetTags(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator) {
		return getService()
				   .getAssetEntryAssetTags(entryId, start, end,
			orderByComparator);
	}

	public static int getAssetEntryAssetTagsCount(long entryId) {
		return getService().getAssetEntryAssetTagsCount(entryId);
	}

	/**
	* Returns the entryIds of the asset entries associated with the asset tag.
	*
	* @param tagId the tagId of the asset tag
	* @return long[] the entryIds of asset entries associated with the asset tag
	*/
	public static long[] getAssetEntryPrimaryKeys(long tagId) {
		return getService().getAssetEntryPrimaryKeys(tagId);
	}

	/**
	* Returns the asset tag with the primary key.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag
	* @throws PortalException if a asset tag with the primary key could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetTag getAssetTag(
		long tagId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetTag(tagId);
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
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		int start, int end) {
		return getService().getAssetTags(start, end);
	}

	/**
	* Returns the number of asset tags.
	*
	* @return the number of asset tags
	*/
	public static int getAssetTagsCount() {
		return getService().getAssetTagsCount();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getEntryTags(
		long entryId) {
		return getService().getEntryTags(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId) {
		return getService().getGroupTags(groupId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId, int start, int end) {
		return getService().getGroupTags(groupId, start, end);
	}

	public static int getGroupTagsCount(long groupId) {
		return getService().getGroupTagsCount(groupId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupsTags(
		long[] groupIds) {
		return getService().getGroupsTags(groupIds);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getSocialActivityCounterOffsetTags(
		long groupId, java.lang.String socialActivityCounterName,
		int startOffset, int endOffset) {
		return getService()
				   .getSocialActivityCounterOffsetTags(groupId,
			socialActivityCounterName, startOffset, endOffset);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getSocialActivityCounterPeriodTags(
		long groupId, java.lang.String socialActivityCounterName,
		int startPeriod, int endPeriod) {
		return getService()
				   .getSocialActivityCounterPeriodTags(groupId,
			socialActivityCounterName, startPeriod, endPeriod);
	}

	public static com.liferay.portlet.asset.model.AssetTag getTag(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTag(groupId, name);
	}

	public static com.liferay.portlet.asset.model.AssetTag getTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTag(tagId);
	}

	public static long[] getTagIds(long groupId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTagIds(groupId, names);
	}

	public static long[] getTagIds(long[] groupIds, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTagIds(groupIds, name);
	}

	public static long[] getTagIds(long[] groupIds, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTagIds(groupIds, names);
	}

	public static java.lang.String[] getTagNames() {
		return getService().getTagNames();
	}

	public static java.lang.String[] getTagNames(java.lang.String className,
		long classPK) {
		return getService().getTagNames(className, classPK);
	}

	public static java.lang.String[] getTagNames(long classNameId, long classPK) {
		return getService().getTagNames(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags() {
		return getService().getTags();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		java.lang.String className, long classPK) {
		return getService().getTags(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long classNameId, long classPK) {
		return getService().getTags(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name) {
		return getService().getTags(groupId, classNameId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name, int start,
		int end) {
		return getService().getTags(groupId, classNameId, name, start, end);
	}

	public static int getTagsSize(long groupId, long classNameId,
		java.lang.String name) {
		return getService().getTagsSize(groupId, classNameId, name);
	}

	public static boolean hasAssetEntryAssetTag(long entryId, long tagId) {
		return getService().hasAssetEntryAssetTag(entryId, tagId);
	}

	public static boolean hasAssetEntryAssetTags(long entryId) {
		return getService().hasAssetEntryAssetTags(entryId);
	}

	public static boolean hasTag(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().hasTag(groupId, name);
	}

	public static com.liferay.portlet.asset.model.AssetTag incrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().incrementAssetCount(tagId, classNameId);
	}

	public static void mergeTags(long fromTagId, long toTagId,
		boolean overrideProperties)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().mergeTags(fromTagId, toTagId, overrideProperties);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long groupId, java.lang.String name, java.lang.String[] tagProperties,
		int start, int end) {
		return getService().search(groupId, name, tagProperties, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> search(
		long[] groupIds, java.lang.String name,
		java.lang.String[] tagProperties, int start, int end) {
		return getService().search(groupIds, name, tagProperties, start, end);
	}

	public static void setAssetEntryAssetTags(long entryId, long[] tagIds) {
		getService().setAssetEntryAssetTags(entryId, tagIds);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the asset tag in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetTag the asset tag
	* @return the asset tag that was updated
	*/
	public static com.liferay.portlet.asset.model.AssetTag updateAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		return getService().updateAssetTag(assetTag);
	}

	public static com.liferay.portlet.asset.model.AssetTag updateTag(
		long userId, long tagId, java.lang.String name,
		java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateTag(userId, tagId, name, tagProperties, serviceContext);
	}

	public static AssetTagLocalService getService() {
		if (_service == null) {
			_service = (AssetTagLocalService)PortalBeanLocatorUtil.locate(AssetTagLocalService.class.getName());

			ReferenceRegistry.registerReference(AssetTagLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(AssetTagLocalService service) {
	}

	private static AssetTagLocalService _service;
}