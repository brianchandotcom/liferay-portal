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

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryAggregate;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for CTEntryAggregate. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryAggregateLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CTEntryAggregateLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTEntryAggregateLocalServiceUtil} to access the ct entry aggregate local service. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTEntryAggregateLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addCTCollectionCTEntryAggregate(
		long ctCollectionId, CTEntryAggregate ctEntryAggregate);

	public void addCTCollectionCTEntryAggregate(
		long ctCollectionId, long ctEntryAggregateId);

	public void addCTCollectionCTEntryAggregates(
		long ctCollectionId, List<CTEntryAggregate> ctEntryAggregates);

	public void addCTCollectionCTEntryAggregates(
		long ctCollectionId, long[] ctEntryAggregateIds);

	public void addCTEntry(CTEntryAggregate ctEntryAggregate, CTEntry ctEntry);

	/**
	 * Adds the ct entry aggregate to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 * @return the ct entry aggregate that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CTEntryAggregate addCTEntryAggregate(
		CTEntryAggregate ctEntryAggregate);

	public CTEntryAggregate addCTEntryAggregate(
			long userId, long ctCollectionId, long ownerCTEntryId,
			ServiceContext serviceContext)
		throws PortalException;

	public void addCTEntryCTEntryAggregate(
		long ctEntryId, CTEntryAggregate ctEntryAggregate);

	public void addCTEntryCTEntryAggregate(
		long ctEntryId, long ctEntryAggregateId);

	public void addCTEntryCTEntryAggregates(
		long ctEntryId, List<CTEntryAggregate> ctEntryAggregates);

	public void addCTEntryCTEntryAggregates(
		long ctEntryId, long[] ctEntryAggregateIds);

	public void clearCTCollectionCTEntryAggregates(long ctCollectionId);

	public void clearCTEntryCTEntryAggregates(long ctEntryId);

	/**
	 * Creates a new ct entry aggregate with the primary key. Does not add the ct entry aggregate to the database.
	 *
	 * @param ctEntryAggregateId the primary key for the new ct entry aggregate
	 * @return the new ct entry aggregate
	 */
	@Transactional(enabled = false)
	public CTEntryAggregate createCTEntryAggregate(long ctEntryAggregateId);

	public void deleteCTCollectionCTEntryAggregate(
		long ctCollectionId, CTEntryAggregate ctEntryAggregate);

	public void deleteCTCollectionCTEntryAggregate(
		long ctCollectionId, long ctEntryAggregateId);

	public void deleteCTCollectionCTEntryAggregates(
		long ctCollectionId, List<CTEntryAggregate> ctEntryAggregates);

	public void deleteCTCollectionCTEntryAggregates(
		long ctCollectionId, long[] ctEntryAggregateIds);

	/**
	 * Deletes the ct entry aggregate from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 * @return the ct entry aggregate that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public CTEntryAggregate deleteCTEntryAggregate(
		CTEntryAggregate ctEntryAggregate);

	/**
	 * Deletes the ct entry aggregate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate that was removed
	 * @throws PortalException if a ct entry aggregate with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public CTEntryAggregate deleteCTEntryAggregate(long ctEntryAggregateId)
		throws PortalException;

	public void deleteCTEntryCTEntryAggregate(
		long ctEntryId, CTEntryAggregate ctEntryAggregate);

	public void deleteCTEntryCTEntryAggregate(
		long ctEntryId, long ctEntryAggregateId);

	public void deleteCTEntryCTEntryAggregates(
		long ctEntryId, List<CTEntryAggregate> ctEntryAggregates);

	public void deleteCTEntryCTEntryAggregates(
		long ctEntryId, long[] ctEntryAggregateIds);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CTEntryAggregate fetchCTEntryAggregate(long ctEntryAggregateId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryAggregate> fetchCTEntryAggregates(
		long ctCollectionId, long ownerCTEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CTEntryAggregate fetchLatestCTEntryAggregate(
		long ctCollectionId, long ownerCTEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryAggregate> getCTCollectionCTEntryAggregates(
		long ctCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryAggregate> getCTCollectionCTEntryAggregates(
		long ctCollectionId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryAggregate> getCTCollectionCTEntryAggregates(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTCollectionCTEntryAggregatesCount(long ctCollectionId);

	/**
	 * Returns the ctCollectionIds of the ct collections associated with the ct entry aggregate.
	 *
	 * @param ctEntryAggregateId the ctEntryAggregateId of the ct entry aggregate
	 * @return long[] the ctCollectionIds of ct collections associated with the ct entry aggregate
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getCTCollectionPrimaryKeys(long ctEntryAggregateId);

	/**
	 * Returns the ct entry aggregate with the primary key.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate
	 * @throws PortalException if a ct entry aggregate with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CTEntryAggregate getCTEntryAggregate(long ctEntryAggregateId)
		throws PortalException;

	/**
	 * Returns a range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entry aggregates
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryAggregate> getCTEntryAggregates(int start, int end);

	/**
	 * Returns the number of ct entry aggregates.
	 *
	 * @return the number of ct entry aggregates
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTEntryAggregatesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryAggregate> getCTEntryCTEntryAggregates(long ctEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryAggregate> getCTEntryCTEntryAggregates(
		long ctEntryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryAggregate> getCTEntryCTEntryAggregates(
		long ctEntryId, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTEntryCTEntryAggregatesCount(long ctEntryId);

	/**
	 * Returns the ctEntryIds of the ct entries associated with the ct entry aggregate.
	 *
	 * @param ctEntryAggregateId the ctEntryAggregateId of the ct entry aggregate
	 * @return long[] the ctEntryIds of ct entries associated with the ct entry aggregate
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getCTEntryPrimaryKeys(long ctEntryAggregateId);

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
	public boolean hasCTCollectionCTEntryAggregate(
		long ctCollectionId, long ctEntryAggregateId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTCollectionCTEntryAggregates(long ctCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTEntry(
		CTEntryAggregate ctEntryAggregate, CTEntry ctEntry);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTEntryCTEntryAggregate(
		long ctEntryId, long ctEntryAggregateId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTEntryCTEntryAggregates(long ctEntryId);

	public void removeCTEntry(
		CTEntryAggregate ctEntryAggregate, CTEntry ctEntry);

	public void setCTCollectionCTEntryAggregates(
		long ctCollectionId, long[] ctEntryAggregateIds);

	public void setCTEntryCTEntryAggregates(
		long ctEntryId, long[] ctEntryAggregateIds);

	/**
	 * Updates the ct entry aggregate in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 * @return the ct entry aggregate that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CTEntryAggregate updateCTEntryAggregate(
		CTEntryAggregate ctEntryAggregate);

}