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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for AssetEntry. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryLocalService
 * @see com.liferay.portlet.asset.service.base.AssetEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetEntryLocalServiceImpl
 * @generated
 */
public class AssetEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the asset entry to the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry
	* @return the asset entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry addAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAssetEntry(assetEntry);
	}

	/**
	* Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	*
	* @param entryId the primary key for the new asset entry
	* @return the new asset entry
	*/
	public static com.liferay.portlet.asset.model.AssetEntry createAssetEntry(
		long entryId) {
		return getService().createAssetEntry(entryId);
	}

	/**
	* Deletes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry that was removed
	* @throws PortalException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry deleteAssetEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteAssetEntry(entryId);
	}

	/**
	* Deletes the asset entry from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry
	* @return the asset entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry deleteAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteAssetEntry(assetEntry);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.asset.model.AssetEntry fetchAssetEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchAssetEntry(entryId);
	}

	/**
	* Returns the asset entry with the primary key.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry
	* @throws PortalException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry getAssetEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetEntry(entryId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetEntries(start, end);
	}

	/**
	* Returns the number of asset entries.
	*
	* @return the number of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetEntriesCount();
	}

	/**
	* Updates the asset entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry
	* @return the asset entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry updateAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetEntry(assetEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategoryAssetEntry(long categoryId, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetCategoryAssetEntry(categoryId, entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategoryAssetEntry(long categoryId,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetCategoryAssetEntry(categoryId, assetEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategoryAssetEntries(long categoryId,
		long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetCategoryAssetEntries(categoryId, entryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategoryAssetEntries(long categoryId,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> AssetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetCategoryAssetEntries(categoryId, AssetEntries);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearAssetCategoryAssetEntries(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearAssetCategoryAssetEntries(categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetCategoryAssetEntry(long categoryId,
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetCategoryAssetEntry(categoryId, entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetCategoryAssetEntry(long categoryId,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetCategoryAssetEntry(categoryId, assetEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetCategoryAssetEntries(long categoryId,
		long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetCategoryAssetEntries(categoryId, entryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetCategoryAssetEntries(long categoryId,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> AssetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetCategoryAssetEntries(categoryId, AssetEntries);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetCategoryAssetEntries(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoryAssetEntries(categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetCategoryAssetEntries(
		long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoryAssetEntries(categoryId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetCategoryAssetEntries(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getAssetCategoryAssetEntries(categoryId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetCategoryAssetEntriesCount(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoryAssetEntriesCount(categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasAssetCategoryAssetEntry(long categoryId,
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasAssetCategoryAssetEntry(categoryId, entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasAssetCategoryAssetEntries(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasAssetCategoryAssetEntries(categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetCategoryAssetEntries(long categoryId,
		long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().setAssetCategoryAssetEntries(categoryId, entryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTagAssetEntry(long tagId, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetTagAssetEntry(tagId, entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTagAssetEntry(long tagId,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetTagAssetEntry(tagId, assetEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTagAssetEntries(long tagId, long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetTagAssetEntries(tagId, entryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTagAssetEntries(long tagId,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> AssetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetTagAssetEntries(tagId, AssetEntries);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearAssetTagAssetEntries(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearAssetTagAssetEntries(tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetTagAssetEntry(long tagId, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetTagAssetEntry(tagId, entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetTagAssetEntry(long tagId,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetTagAssetEntry(tagId, assetEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetTagAssetEntries(long tagId, long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetTagAssetEntries(tagId, entryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetTagAssetEntries(long tagId,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> AssetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetTagAssetEntries(tagId, AssetEntries);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetTagAssetEntries(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetTagAssetEntries(tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetTagAssetEntries(
		long tagId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetTagAssetEntries(tagId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetTagAssetEntries(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getAssetTagAssetEntries(tagId, start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetTagAssetEntriesCount(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetTagAssetEntriesCount(tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasAssetTagAssetEntry(long tagId, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasAssetTagAssetEntry(tagId, entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasAssetTagAssetEntries(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasAssetTagAssetEntries(tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetTagAssetEntries(long tagId, long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().setAssetTagAssetEntries(tagId, entryIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void deleteEntry(
		com.liferay.portlet.asset.model.AssetEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entry);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entryId);
	}

	public static void deleteEntry(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(className, classPK);
	}

	public static com.liferay.portlet.asset.model.AssetEntry fetchEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchEntry(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry fetchEntry(
		long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchEntry(groupId, classUuid);
	}

	public static com.liferay.portlet.asset.model.AssetEntry fetchEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchEntry(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAncestorEntries(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAncestorEntries(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getChildEntries(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getChildEntries(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getCompanyEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntries(companyId, start, end);
	}

	public static int getCompanyEntriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntriesCount(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(entryQuery);
	}

	public static int getEntriesCount(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntriesCount(entryQuery);
	}

	public static com.liferay.portlet.asset.model.AssetEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry getEntry(
		long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(groupId, classUuid);
	}

	public static com.liferay.portlet.asset.model.AssetEntry getEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(className, classPK);
	}

	public static com.liferay.portlet.asset.model.AssetEntry getNextEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getNextEntry(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry getParentEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getParentEntry(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry getPreviousEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPreviousEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getTopViewedEntries(
		java.lang.String className, boolean asc, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTopViewedEntries(className, asc, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getTopViewedEntries(
		java.lang.String[] className, boolean asc, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTopViewedEntries(className, asc, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetEntry incrementViewCounter(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().incrementViewCounter(userId, className, classPK);
	}

	public static com.liferay.portlet.asset.model.AssetEntry incrementViewCounter(
		long userId, java.lang.String className, long classPK, int increment)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .incrementViewCounter(userId, className, classPK, increment);
	}

	public static void reindex(
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> entries)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().reindex(entries);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	String, String, int, int, int)}
	*/
	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		java.lang.String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, userId, className, keywords,
			start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		java.lang.String keywords, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, userId, className, keywords,
			status, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	String, String, String, String, String, String, int, boolean,
	int, int)}
	*/
	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.String assetCategoryIds,
		java.lang.String assetTagNames, boolean andSearch, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, userId, className, userName,
			title, description, assetCategoryIds, assetTagNames, andSearch,
			start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.String assetCategoryIds,
		java.lang.String assetTagNames, int status, boolean andSearch,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, userId, className, userName,
			title, description, assetCategoryIds, assetTagNames, status,
			andSearch, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	String, String, int, int, int)}
	*/
	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, java.lang.String className, java.lang.String keywords,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, className, keywords, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateEntry(
		long userId, long groupId, java.util.Date createDate,
		java.util.Date modifiedDate, java.lang.String className, long classPK,
		java.lang.String classUuid, long classTypeId, long[] categoryIds,
		java.lang.String[] tagNames, boolean visible, java.util.Date startDate,
		java.util.Date endDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, java.lang.String layoutUuid, int height,
		int width, java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(userId, groupId, createDate, modifiedDate,
			className, classPK, classUuid, classTypeId, categoryIds, tagNames,
			visible, startDate, endDate, expirationDate, mimeType, title,
			description, summary, url, layoutUuid, height, width, priority, sync);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateEntry(
		long userId, long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(userId, groupId, className, classPK,
			categoryIds, tagNames);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, long,
	String, long, String, long, long[], String[], boolean, Date,
	Date, Date, String, String, String, String, String, String,
	int, int, Integer, boolean)}
	*/
	public static com.liferay.portlet.asset.model.AssetEntry updateEntry(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String classUuid, long classTypeId, long[] categoryIds,
		java.lang.String[] tagNames, boolean visible, java.util.Date startDate,
		java.util.Date endDate, java.util.Date publishDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url,
		java.lang.String layoutUuid, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(userId, groupId, className, classPK, classUuid,
			classTypeId, categoryIds, tagNames, visible, startDate, endDate,
			publishDate, expirationDate, mimeType, title, description, summary,
			url, layoutUuid, height, width, priority, sync);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, long,
	Date, Date, String, long, String, long, long[], String[],
	boolean, Date, Date, Date, String, String, String, String,
	String, String, int, int, Integer, boolean)}
	*/
	public static com.liferay.portlet.asset.model.AssetEntry updateEntry(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String classUuid, long classTypeId, long[] categoryIds,
		java.lang.String[] tagNames, boolean visible, java.util.Date startDate,
		java.util.Date endDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, java.lang.String layoutUuid, int height,
		int width, java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(userId, groupId, className, classPK, classUuid,
			classTypeId, categoryIds, tagNames, visible, startDate, endDate,
			expirationDate, mimeType, title, description, summary, url,
			layoutUuid, height, width, priority, sync);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateEntry(
		java.lang.String className, long classPK, java.util.Date publishDate,
		boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateEntry(className, classPK, publishDate, visible);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateEntry(
		java.lang.String className, long classPK, java.util.Date publishDate,
		java.util.Date expirationDate, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(className, classPK, publishDate,
			expirationDate, visible);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateVisible(
		java.lang.String className, long classPK, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateVisible(className, classPK, visible);
	}

	public static void validate(long groupId, java.lang.String className,
		long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().validate(groupId, className, categoryIds, tagNames);
	}

	public static AssetEntryLocalService getService() {
		if (_service == null) {
			_service = (AssetEntryLocalService)PortalBeanLocatorUtil.locate(AssetEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(AssetEntryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(AssetEntryLocalService service) {
	}

	private static AssetEntryLocalService _service;
}