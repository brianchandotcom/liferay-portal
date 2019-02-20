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
import com.liferay.change.tracking.model.CTEntryBag;

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
 * Provides the local service interface for CTEntryBag. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryBagLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CTEntryBagLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTEntryBagLocalServiceUtil} to access the ct entry bag local service. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTEntryBagLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addCTEntry(CTEntryBag ctEntryBag, CTEntry ctEntry);

	/**
	* Adds the ct entry bag to the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBag the ct entry bag
	* @return the ct entry bag that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CTEntryBag addCTEntryBag(CTEntryBag ctEntryBag);

	public void addCTEntryCTEntryBag(long ctEntryId, CTEntryBag ctEntryBag);

	public void addCTEntryCTEntryBag(long ctEntryId, long ctEntryBagId);

	public void addCTEntryCTEntryBags(long ctEntryId,
		List<CTEntryBag> ctEntryBags);

	public void addCTEntryCTEntryBags(long ctEntryId, long[] ctEntryBagIds);

	public void clearCTEntryCTEntryBags(long ctEntryId);

	/**
	* Creates a new ct entry bag with the primary key. Does not add the ct entry bag to the database.
	*
	* @param ctEntryBagId the primary key for the new ct entry bag
	* @return the new ct entry bag
	*/
	@Transactional(enabled = false)
	public CTEntryBag createCTEntryBag(long ctEntryBagId);

	public CTEntryBag createCTEntryBag(long userId, long ownerCTEntryId,
		long ctCollectionId, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Deletes the ct entry bag from the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBag the ct entry bag
	* @return the ct entry bag that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CTEntryBag deleteCTEntryBag(CTEntryBag ctEntryBag);

	/**
	* Deletes the ct entry bag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag that was removed
	* @throws PortalException if a ct entry bag with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CTEntryBag deleteCTEntryBag(long ctEntryBagId)
		throws PortalException;

	public void deleteCTEntryCTEntryBag(long ctEntryId, CTEntryBag ctEntryBag);

	public void deleteCTEntryCTEntryBag(long ctEntryId, long ctEntryBagId);

	public void deleteCTEntryCTEntryBags(long ctEntryId,
		List<CTEntryBag> ctEntryBags);

	public void deleteCTEntryCTEntryBags(long ctEntryId, long[] ctEntryBagIds);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CTEntryBag fetchCTEntryBag(long ctEntryBagId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryBag> fetchCTEntryBags(long ownerCTEntryId,
		long ctCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CTEntryBag fetchLatestCTEntryBag(long ownerCTEntryId,
		long ctCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the ct entry bag with the primary key.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag
	* @throws PortalException if a ct entry bag with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CTEntryBag getCTEntryBag(long ctEntryBagId)
		throws PortalException;

	/**
	* Returns a range of all the ct entry bags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @return the range of ct entry bags
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryBag> getCTEntryBags(int start, int end);

	/**
	* Returns the number of ct entry bags.
	*
	* @return the number of ct entry bags
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTEntryBagsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryBag> getCTEntryCTEntryBags(long ctEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryBag> getCTEntryCTEntryBags(long ctEntryId, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEntryBag> getCTEntryCTEntryBags(long ctEntryId, int start,
		int end, OrderByComparator<CTEntryBag> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTEntryCTEntryBagsCount(long ctEntryId);

	/**
	* Returns the ctEntryIds of the ct entries associated with the ct entry bag.
	*
	* @param ctEntryBagId the ctEntryBagId of the ct entry bag
	* @return long[] the ctEntryIds of ct entries associated with the ct entry bag
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getCTEntryPrimaryKeys(long ctEntryBagId);

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
	public boolean hasCTEntry(CTEntryBag ctEntryBag, CTEntry ctEntry);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTEntryCTEntryBag(long ctEntryId, long ctEntryBagId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTEntryCTEntryBags(long ctEntryId);

	public void removeCTEntry(CTEntryBag ctEntryBag, CTEntry ctEntry);

	public void setCTEntryCTEntryBags(long ctEntryId, long[] ctEntryBagIds);

	/**
	* Updates the ct entry bag in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBag the ct entry bag
	* @return the ct entry bag that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CTEntryBag updateCTEntryBag(CTEntryBag ctEntryBag);
}