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

package com.liferay.change.tracking.engine.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.model.ChangeCollection;

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
 * Provides the local service interface for ChangeCollection. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeCollectionLocalServiceUtil
 * @see com.liferay.change.tracking.engine.service.base.ChangeCollectionLocalServiceBaseImpl
 * @see com.liferay.change.tracking.engine.service.impl.ChangeCollectionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ChangeCollectionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangeCollectionLocalServiceUtil} to access the change collection local service. Add custom service methods to {@link com.liferay.change.tracking.engine.service.impl.ChangeCollectionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the change collection to the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollection the change collection
	* @return the change collection that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ChangeCollection addChangeCollection(
		ChangeCollection changeCollection);

	public void addChangeEntryChangeCollection(long changeEntryId,
		ChangeCollection changeCollection);

	public void addChangeEntryChangeCollection(long changeEntryId,
		long changeCollectionId);

	public void addChangeEntryChangeCollections(long changeEntryId,
		List<ChangeCollection> changeCollections);

	public void addChangeEntryChangeCollections(long changeEntryId,
		long[] changeCollectionIds);

	public void clearChangeEntryChangeCollections(long changeEntryId);

	/**
	* Creates a new change collection with the primary key. Does not add the change collection to the database.
	*
	* @param changeCollectionId the primary key for the new change collection
	* @return the new change collection
	*/
	@Transactional(enabled = false)
	public ChangeCollection createChangeCollection(long changeCollectionId);

	/**
	* Deletes the change collection from the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollection the change collection
	* @return the change collection that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public ChangeCollection deleteChangeCollection(
		ChangeCollection changeCollection);

	/**
	* Deletes the change collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection that was removed
	* @throws PortalException if a change collection with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public ChangeCollection deleteChangeCollection(long changeCollectionId)
		throws PortalException;

	public void deleteChangeEntryChangeCollection(long changeEntryId,
		ChangeCollection changeCollection);

	public void deleteChangeEntryChangeCollection(long changeEntryId,
		long changeCollectionId);

	public void deleteChangeEntryChangeCollections(long changeEntryId,
		List<ChangeCollection> changeCollections);

	public void deleteChangeEntryChangeCollections(long changeEntryId,
		long[] changeCollectionIds);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public ChangeCollection fetchChangeCollection(long changeCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the change collection with the primary key.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection
	* @throws PortalException if a change collection with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangeCollection getChangeCollection(long changeCollectionId)
		throws PortalException;

	/**
	* Returns a range of all the change collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change collections
	* @param end the upper bound of the range of change collections (not inclusive)
	* @return the range of change collections
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeCollection> getChangeCollections(int start, int end);

	/**
	* Returns the number of change collections.
	*
	* @return the number of change collections
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChangeCollectionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeCollection> getChangeEntryChangeCollections(
		long changeEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeCollection> getChangeEntryChangeCollections(
		long changeEntryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangeCollection> getChangeEntryChangeCollections(
		long changeEntryId, int start, int end,
		OrderByComparator<ChangeCollection> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChangeEntryChangeCollectionsCount(long changeEntryId);

	/**
	* Returns the changeEntryIds of the change entries associated with the change collection.
	*
	* @param changeCollectionId the changeCollectionId of the change collection
	* @return long[] the changeEntryIds of change entries associated with the change collection
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getChangeEntryPrimaryKeys(long changeCollectionId);

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
	public boolean hasChangeEntryChangeCollection(long changeEntryId,
		long changeCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasChangeEntryChangeCollections(long changeEntryId);

	public void setChangeEntryChangeCollections(long changeEntryId,
		long[] changeCollectionIds);

	/**
	* Updates the change collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeCollection the change collection
	* @return the change collection that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ChangeCollection updateChangeCollection(
		ChangeCollection changeCollection);
}