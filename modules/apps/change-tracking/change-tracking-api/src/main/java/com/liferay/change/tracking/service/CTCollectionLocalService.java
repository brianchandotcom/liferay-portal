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

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
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

/**
 * Provides the local service interface for CTCollection. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CTCollectionLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTCollectionLocalServiceUtil} to access the ct collection local service. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTCollectionLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the ct collection to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollection the ct collection
	 * @return the ct collection that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CTCollection addCTCollection(CTCollection ctCollection);

	public CTCollection addCTCollection(
			long userId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

	public void addCTEntryAggregateCTCollection(
		long ctEntryAggregateId, CTCollection ctCollection);

	public void addCTEntryAggregateCTCollection(
		long ctEntryAggregateId, long ctCollectionId);

	public void addCTEntryAggregateCTCollections(
		long ctEntryAggregateId, List<CTCollection> ctCollections);

	public void addCTEntryAggregateCTCollections(
		long ctEntryAggregateId, long[] ctCollectionIds);

	public void addCTEntryCTCollection(
		long ctEntryId, CTCollection ctCollection);

	public void addCTEntryCTCollection(long ctEntryId, long ctCollectionId);

	public void addCTEntryCTCollections(
		long ctEntryId, List<CTCollection> ctCollections);

	public void addCTEntryCTCollections(long ctEntryId, long[] ctCollectionIds);

	public void clearCTEntryAggregateCTCollections(long ctEntryAggregateId);

	public void clearCTEntryCTCollections(long ctEntryId);

	/**
	 * Creates a new ct collection with the primary key. Does not add the ct collection to the database.
	 *
	 * @param ctCollectionId the primary key for the new ct collection
	 * @return the new ct collection
	 */
	@Transactional(enabled = false)
	public CTCollection createCTCollection(long ctCollectionId);

	public void deleteCompanyCTCollections(long companyId)
		throws PortalException;

	/**
	 * Deletes the ct collection from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollection the ct collection
	 * @return the ct collection that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	public CTCollection deleteCTCollection(CTCollection ctCollection)
		throws PortalException;

	/**
	 * Deletes the ct collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollectionId the primary key of the ct collection
	 * @return the ct collection that was removed
	 * @throws PortalException if a ct collection with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public CTCollection deleteCTCollection(long ctCollectionId)
		throws PortalException;

	public void deleteCTEntryAggregateCTCollection(
		long ctEntryAggregateId, CTCollection ctCollection);

	public void deleteCTEntryAggregateCTCollection(
		long ctEntryAggregateId, long ctCollectionId);

	public void deleteCTEntryAggregateCTCollections(
		long ctEntryAggregateId, List<CTCollection> ctCollections);

	public void deleteCTEntryAggregateCTCollections(
		long ctEntryAggregateId, long[] ctCollectionIds);

	public void deleteCTEntryCTCollection(
		long ctEntryId, CTCollection ctCollection);

	public void deleteCTEntryCTCollection(long ctEntryId, long ctCollectionId);

	public void deleteCTEntryCTCollections(
		long ctEntryId, List<CTCollection> ctCollections);

	public void deleteCTEntryCTCollections(
		long ctEntryId, long[] ctCollectionIds);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CTCollection fetchCTCollection(long ctCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CTCollection fetchCTCollection(long companyId, String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the ct collection with the primary key.
	 *
	 * @param ctCollectionId the primary key of the ct collection
	 * @return the ct collection
	 * @throws PortalException if a ct collection with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CTCollection getCTCollection(long ctCollectionId)
		throws PortalException;

	/**
	 * Returns a range of all the ct collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @return the range of ct collections
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTCollections(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTCollections(
		long companyId, QueryDefinition<CTCollection> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTCollections(
		long companyId, QueryDefinition<CTCollection> queryDefinition,
		boolean includeProduction);

	/**
	 * Returns the number of ct collections.
	 *
	 * @return the number of ct collections
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTCollectionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTEntryAggregateCTCollections(
		long ctEntryAggregateId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTEntryAggregateCTCollections(
		long ctEntryAggregateId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTEntryAggregateCTCollections(
		long ctEntryAggregateId, int start, int end,
		OrderByComparator<CTCollection> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTEntryAggregateCTCollectionsCount(long ctEntryAggregateId);

	/**
	 * Returns the ctEntryAggregateIds of the ct entry aggregates associated with the ct collection.
	 *
	 * @param ctCollectionId the ctCollectionId of the ct collection
	 * @return long[] the ctEntryAggregateIds of ct entry aggregates associated with the ct collection
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getCTEntryAggregatePrimaryKeys(long ctCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTEntryCTCollections(long ctEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTEntryCTCollections(
		long ctEntryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTEntryCTCollections(
		long ctEntryId, int start, int end,
		OrderByComparator<CTCollection> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTEntryCTCollectionsCount(long ctEntryId);

	/**
	 * Returns the ctEntryIds of the ct entries associated with the ct collection.
	 *
	 * @param ctCollectionId the ctCollectionId of the ct collection
	 * @return long[] the ctEntryIds of ct entries associated with the ct collection
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getCTEntryPrimaryKeys(long ctCollectionId);

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
	public boolean hasCTEntryAggregateCTCollection(
		long ctEntryAggregateId, long ctCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTEntryAggregateCTCollections(long ctEntryAggregateId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTEntryCTCollection(long ctEntryId, long ctCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTEntryCTCollections(long ctEntryId);

	public void setCTEntryAggregateCTCollections(
		long ctEntryAggregateId, long[] ctCollectionIds);

	public void setCTEntryCTCollections(long ctEntryId, long[] ctCollectionIds);

	/**
	 * Updates the ct collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollection the ct collection
	 * @return the ct collection that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CTCollection updateCTCollection(CTCollection ctCollection);

	public CTCollection updateStatus(
			long userId, CTCollection ctCollection, int status,
			ServiceContext serviceContext)
		throws PortalException;

}