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

package com.liferay.asset.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.list.model.AssetListSegmentRel;
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
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for AssetListSegmentRel. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListSegmentRelLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface AssetListSegmentRelLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListSegmentRelLocalServiceUtil} to access the asset list segment rel local service. Add custom service methods to <code>com.liferay.asset.list.service.impl.AssetListSegmentRelLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the asset list segment rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRel the asset list segment rel
	 * @return the asset list segment rel that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public AssetListSegmentRel addAssetListSegmentRel(
		AssetListSegmentRel assetListSegmentRel);

	/**
	 * Creates a new asset list segment rel with the primary key. Does not add the asset list segment rel to the database.
	 *
	 * @param assetListSegmentRelId the primary key for the new asset list segment rel
	 * @return the new asset list segment rel
	 */
	@Transactional(enabled = false)
	public AssetListSegmentRel createAssetListSegmentRel(
		long assetListSegmentRelId);

	/**
	 * Deletes the asset list segment rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRel the asset list segment rel
	 * @return the asset list segment rel that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public AssetListSegmentRel deleteAssetListSegmentRel(
		AssetListSegmentRel assetListSegmentRel);

	/**
	 * Deletes the asset list segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel that was removed
	 * @throws PortalException if a asset list segment rel with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public AssetListSegmentRel deleteAssetListSegmentRel(
			long assetListSegmentRelId)
		throws PortalException;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public AssetListSegmentRel fetchAssetListSegmentRel(
		long assetListSegmentRelId);

	/**
	 * Returns the asset list segment rel matching the UUID and group.
	 *
	 * @param uuid the asset list segment rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetListSegmentRel fetchAssetListSegmentRelByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the asset list segment rel with the primary key.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel
	 * @throws PortalException if a asset list segment rel with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetListSegmentRel getAssetListSegmentRel(
			long assetListSegmentRelId)
		throws PortalException;

	/**
	 * Returns the asset list segment rel matching the UUID and group.
	 *
	 * @param uuid the asset list segment rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list segment rel
	 * @throws PortalException if a matching asset list segment rel could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetListSegmentRel getAssetListSegmentRelByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the asset list segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of asset list segment rels
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListSegmentRel> getAssetListSegmentRels(
		int start, int end);

	/**
	 * Returns all the asset list segment rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list segment rels
	 * @param companyId the primary key of the company
	 * @return the matching asset list segment rels, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListSegmentRel> getAssetListSegmentRelsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of asset list segment rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list segment rels
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset list segment rels, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListSegmentRel> getAssetListSegmentRelsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator);

	/**
	 * Returns the number of asset list segment rels.
	 *
	 * @return the number of asset list segment rels
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetListSegmentRelsCount();

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

	/**
	 * Updates the asset list segment rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRel the asset list segment rel
	 * @return the asset list segment rel that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public AssetListSegmentRel updateAssetListSegmentRel(
		AssetListSegmentRel assetListSegmentRel);

}