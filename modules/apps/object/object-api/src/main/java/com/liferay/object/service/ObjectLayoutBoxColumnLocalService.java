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

package com.liferay.object.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.object.model.ObjectLayoutBoxColumn;
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
 * Provides the local service interface for ObjectLayoutBoxColumn. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxColumnLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ObjectLayoutBoxColumnLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectLayoutBoxColumnLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the object layout box column local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ObjectLayoutBoxColumnLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the object layout box column to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxColumnLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxColumn the object layout box column
	 * @return the object layout box column that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ObjectLayoutBoxColumn addObjectLayoutBoxColumn(
		ObjectLayoutBoxColumn objectLayoutBoxColumn);

	/**
	 * Creates a new object layout box column with the primary key. Does not add the object layout box column to the database.
	 *
	 * @param objectLayoutBoxColumnId the primary key for the new object layout box column
	 * @return the new object layout box column
	 */
	@Transactional(enabled = false)
	public ObjectLayoutBoxColumn createObjectLayoutBoxColumn(
		long objectLayoutBoxColumnId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the object layout box column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxColumnLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxColumnId the primary key of the object layout box column
	 * @return the object layout box column that was removed
	 * @throws PortalException if a object layout box column with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public ObjectLayoutBoxColumn deleteObjectLayoutBoxColumn(
			long objectLayoutBoxColumnId)
		throws PortalException;

	/**
	 * Deletes the object layout box column from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxColumnLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxColumn the object layout box column
	 * @return the object layout box column that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public ObjectLayoutBoxColumn deleteObjectLayoutBoxColumn(
		ObjectLayoutBoxColumn objectLayoutBoxColumn);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int dslQueryCount(DSLQuery dslQuery);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxColumnModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxColumnModelImpl</code>.
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
	public ObjectLayoutBoxColumn fetchObjectLayoutBoxColumn(
		long objectLayoutBoxColumnId);

	/**
	 * Returns the object layout box column with the matching UUID and company.
	 *
	 * @param uuid the object layout box column's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ObjectLayoutBoxColumn fetchObjectLayoutBoxColumnByUuidAndCompanyId(
		String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the object layout box column with the primary key.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the object layout box column
	 * @return the object layout box column
	 * @throws PortalException if a object layout box column with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ObjectLayoutBoxColumn getObjectLayoutBoxColumn(
			long objectLayoutBoxColumnId)
		throws PortalException;

	/**
	 * Returns the object layout box column with the matching UUID and company.
	 *
	 * @param uuid the object layout box column's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object layout box column
	 * @throws PortalException if a matching object layout box column could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ObjectLayoutBoxColumn getObjectLayoutBoxColumnByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException;

	/**
	 * Returns a range of all the object layout box columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @return the range of object layout box columns
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ObjectLayoutBoxColumn> getObjectLayoutBoxColumns(
		int start, int end);

	/**
	 * Returns the number of object layout box columns.
	 *
	 * @return the number of object layout box columns
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getObjectLayoutBoxColumnsCount();

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
	 * Updates the object layout box column in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxColumnLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxColumn the object layout box column
	 * @return the object layout box column that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ObjectLayoutBoxColumn updateObjectLayoutBoxColumn(
		ObjectLayoutBoxColumn objectLayoutBoxColumn);

}