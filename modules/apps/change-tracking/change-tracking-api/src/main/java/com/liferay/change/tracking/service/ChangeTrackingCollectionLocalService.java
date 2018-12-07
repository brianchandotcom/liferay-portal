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

package com.liferay.change.tracking.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.model.ChangeTrackingCollection;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
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
 * Provides the local service interface for ChangeTrackingCollection. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingCollectionLocalServiceUtil
 * @see com.liferay.change.tracking.service.base.ChangeTrackingCollectionLocalServiceBaseImpl
 * @see com.liferay.change.tracking.service.impl.ChangeTrackingCollectionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ChangeTrackingCollectionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangeTrackingCollectionLocalServiceUtil} to access the change tracking collection local service. Add custom service methods to {@link com.liferay.change.tracking.service.impl.ChangeTrackingCollectionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the change tracking collection to the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollection the change tracking collection
	* @return the change tracking collection that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ChangeTrackingCollection addChangeTrackingCollection(
		ChangeTrackingCollection changeTrackingCollection);

	public void addChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId,
		ChangeTrackingCollection changeTrackingCollection);

	public void addChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId, long changeTrackingCollectionId);

	public void addChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId,
		List<ChangeTrackingCollection> changeTrackingCollections);

	public void addChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, long[] changeTrackingCollectionIds);

	public void clearChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId);

	/**
	* Creates a new change tracking collection with the primary key. Does not add the change tracking collection to the database.
	*
	* @param changeTrackingCollectionId the primary key for the new change tracking collection
	* @return the new change tracking collection
	*/
	@Transactional(enabled = false)
	public ChangeTrackingCollection createChangeTrackingCollection(
		long changeTrackingCollectionId);

	/**
	* Deletes the change tracking collection from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollection the change tracking collection
	* @return the change tracking collection that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public ChangeTrackingCollection deleteChangeTrackingCollection(
		ChangeTrackingCollection changeTrackingCollection);

	/**
	* Deletes the change tracking collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection that was removed
	* @throws PortalException if a change tracking collection with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public ChangeTrackingCollection deleteChangeTrackingCollection(
		long changeTrackingCollectionId) throws PortalException;

	public void deleteChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId,
		ChangeTrackingCollection changeTrackingCollection);

	public void deleteChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId, long changeTrackingCollectionId);

	public void deleteChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId,
		List<ChangeTrackingCollection> changeTrackingCollections);

	public void deleteChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, long[] changeTrackingCollectionIds);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

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
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangeTrackingCollection fetchChangeTrackingCollection(
		long changeTrackingCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the change tracking collection with the primary key.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection
	* @throws PortalException if a change tracking collection with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangeTrackingCollection getChangeTrackingCollection(
		long changeTrackingCollectionId) throws PortalException;

	/**
	* Returns a range of all the change tracking collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking collections
	* @param end the upper bound of the range of change tracking collections (not inclusive)
	* @return the range of change tracking collections
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeTrackingCollection> getChangeTrackingCollections(
		int start, int end);

	/**
	* Returns the number of change tracking collections.
	*
	* @return the number of change tracking collections
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChangeTrackingCollectionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeTrackingCollection> getChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeTrackingCollection> getChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeTrackingCollection> getChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, int start, int end,
		OrderByComparator<ChangeTrackingCollection> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChangeTrackingEntryChangeTrackingCollectionsCount(
		long changeTrackingEntryId);

	/**
	* Returns the changeTrackingEntryIds of the change tracking entries associated with the change tracking collection.
	*
	* @param changeTrackingCollectionId the changeTrackingCollectionId of the change tracking collection
	* @return long[] the changeTrackingEntryIds of change tracking entries associated with the change tracking collection
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getChangeTrackingEntryPrimaryKeys(
		long changeTrackingCollectionId);

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
	public boolean hasChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId, long changeTrackingCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId);

	public void setChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, long[] changeTrackingCollectionIds);

	/**
	* Updates the change tracking collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollection the change tracking collection
	* @return the change tracking collection that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ChangeTrackingCollection updateChangeTrackingCollection(
		ChangeTrackingCollection changeTrackingCollection);
}