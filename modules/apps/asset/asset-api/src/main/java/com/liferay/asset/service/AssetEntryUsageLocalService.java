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

package com.liferay.asset.service;

import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for AssetEntryUsage. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryUsageLocalServiceUtil
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 com.liferay.layout.service.impl.LayoutClassedModelUsageLocalServiceImpl}
 * @generated
 */
@Deprecated
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface AssetEntryUsageLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryUsageLocalServiceUtil} to access the asset entry usage local service. Add custom service methods to <code>com.liferay.asset.service.impl.AssetEntryUsageLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the asset entry usage to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsage the asset entry usage
	 * @return the asset entry usage that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public AssetEntryUsage addAssetEntryUsage(AssetEntryUsage assetEntryUsage);

	public AssetEntryUsage addAssetEntryUsage(
		long groupId, long assetEntryId, long containerType,
		String containerKey, long plid, ServiceContext serviceContext);

	public AssetEntryUsage addDefaultAssetEntryUsage(
		long groupId, long assetEntryId, ServiceContext serviceContext);

	/**
	 * Creates a new asset entry usage with the primary key. Does not add the asset entry usage to the database.
	 *
	 * @param assetEntryUsageId the primary key for the new asset entry usage
	 * @return the new asset entry usage
	 */
	@Transactional(enabled = false)
	public AssetEntryUsage createAssetEntryUsage(long assetEntryUsageId);

	/**
	 * Deletes the asset entry usage from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsage the asset entry usage
	 * @return the asset entry usage that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public AssetEntryUsage deleteAssetEntryUsage(
		AssetEntryUsage assetEntryUsage);

	/**
	 * Deletes the asset entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage that was removed
	 * @throws PortalException if a asset entry usage with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public AssetEntryUsage deleteAssetEntryUsage(long assetEntryUsageId)
		throws PortalException;

	public void deleteAssetEntryUsages(long assetEntryId);

	public void deleteAssetEntryUsages(
		long containerType, String containerKey, long plid);

	public void deleteAssetEntryUsagesByPlid(long plid);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.model.impl.AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.model.impl.AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetEntryUsage fetchAssetEntryUsage(long assetEntryUsageId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetEntryUsage fetchAssetEntryUsage(
		long assetEntryId, long containerType, String containerKey, long plid);

	/**
	 * Returns the asset entry usage matching the UUID and group.
	 *
	 * @param uuid the asset entry usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetEntryUsage fetchAssetEntryUsageByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the asset entry usage with the primary key.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage
	 * @throws PortalException if a asset entry usage with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetEntryUsage getAssetEntryUsage(long assetEntryUsageId)
		throws PortalException;

	/**
	 * Returns the asset entry usage matching the UUID and group.
	 *
	 * @param uuid the asset entry usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset entry usage
	 * @throws PortalException if a matching asset entry usage could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetEntryUsage getAssetEntryUsageByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the asset entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.model.impl.AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of asset entry usages
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetEntryUsage> getAssetEntryUsages(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetEntryUsage> getAssetEntryUsages(long assetEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, int type, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetEntryUsage> getAssetEntryUsagesByPlid(long plid);

	/**
	 * Returns all the asset entry usages matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset entry usages
	 * @param companyId the primary key of the company
	 * @return the matching asset entry usages, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetEntryUsage> getAssetEntryUsagesByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of asset entry usages matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset entry usages
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset entry usages, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetEntryUsage> getAssetEntryUsagesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	 * Returns the number of asset entry usages.
	 *
	 * @return the number of asset entry usages
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetEntryUsagesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetEntryUsagesCount(long assetEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetEntryUsagesCount(long assetEntryId, int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUniqueAssetEntryUsagesCount(long assetEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasDefaultAssetEntryUsage(long assetEntryId);

	/**
	 * Updates the asset entry usage in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsage the asset entry usage
	 * @return the asset entry usage that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public AssetEntryUsage updateAssetEntryUsage(
		AssetEntryUsage assetEntryUsage);

}