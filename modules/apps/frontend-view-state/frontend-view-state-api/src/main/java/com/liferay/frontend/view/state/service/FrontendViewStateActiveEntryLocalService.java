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

package com.liferay.frontend.view.state.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
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

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for FrontendViewStateActiveEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see FrontendViewStateActiveEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface FrontendViewStateActiveEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.frontend.view.state.service.impl.FrontendViewStateActiveEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the frontend view state active entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link FrontendViewStateActiveEntryLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the frontend view state active entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateActiveEntry the frontend view state active entry
	 * @return the frontend view state active entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public FrontendViewStateActiveEntry addFrontendViewStateActiveEntry(
		FrontendViewStateActiveEntry frontendViewStateActiveEntry);

	/**
	 * Creates a new frontend view state active entry with the primary key. Does not add the frontend view state active entry to the database.
	 *
	 * @param frontendViewStateActiveEntryId the primary key for the new frontend view state active entry
	 * @return the new frontend view state active entry
	 */
	@Transactional(enabled = false)
	public FrontendViewStateActiveEntry createFrontendViewStateActiveEntry(
		long frontendViewStateActiveEntryId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the frontend view state active entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateActiveEntry the frontend view state active entry
	 * @return the frontend view state active entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public FrontendViewStateActiveEntry deleteFrontendViewStateActiveEntry(
		FrontendViewStateActiveEntry frontendViewStateActiveEntry);

	/**
	 * Deletes the frontend view state active entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateActiveEntryId the primary key of the frontend view state active entry
	 * @return the frontend view state active entry that was removed
	 * @throws PortalException if a frontend view state active entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public FrontendViewStateActiveEntry deleteFrontendViewStateActiveEntry(
			long frontendViewStateActiveEntryId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateActiveEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateActiveEntryModelImpl</code>.
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
	public FrontendViewStateActiveEntry fetchFrontendViewStateActiveEntry(
		long frontendViewStateActiveEntryId);

	/**
	 * Returns the frontend view state active entry with the matching UUID and company.
	 *
	 * @param uuid the frontend view state active entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching frontend view state active entry, or <code>null</code> if a matching frontend view state active entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FrontendViewStateActiveEntry
		fetchFrontendViewStateActiveEntryByUuidAndCompanyId(
			String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	/**
	 * Returns a range of all the frontend view state active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state active entries
	 * @param end the upper bound of the range of frontend view state active entries (not inclusive)
	 * @return the range of frontend view state active entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FrontendViewStateActiveEntry> getFrontendViewStateActiveEntries(
		int start, int end);

	/**
	 * Returns the number of frontend view state active entries.
	 *
	 * @return the number of frontend view state active entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFrontendViewStateActiveEntriesCount();

	/**
	 * Returns the frontend view state active entry with the primary key.
	 *
	 * @param frontendViewStateActiveEntryId the primary key of the frontend view state active entry
	 * @return the frontend view state active entry
	 * @throws PortalException if a frontend view state active entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FrontendViewStateActiveEntry getFrontendViewStateActiveEntry(
			long frontendViewStateActiveEntryId)
		throws PortalException;

	/**
	 * Returns the frontend view state active entry with the matching UUID and company.
	 *
	 * @param uuid the frontend view state active entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching frontend view state active entry
	 * @throws PortalException if a matching frontend view state active entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FrontendViewStateActiveEntry
			getFrontendViewStateActiveEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Updates the frontend view state active entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateActiveEntry the frontend view state active entry
	 * @return the frontend view state active entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public FrontendViewStateActiveEntry updateFrontendViewStateActiveEntry(
		FrontendViewStateActiveEntry frontendViewStateActiveEntry);

}