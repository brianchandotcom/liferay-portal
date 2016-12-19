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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;

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
 * Provides the local service interface for DDMStructureVersion. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersionLocalServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMStructureVersionLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMStructureVersionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMStructureVersionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureVersionLocalServiceUtil} to access the ddm structure version local service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMStructureVersionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMForm getStructureVersionDDMForm(
		DDMStructureVersion structureVersion) throws PortalException;

	/**
	* Adds the ddm structure version to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the ddm structure version
	* @return the ddm structure version that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDMStructureVersion addDDMStructureVersion(
		DDMStructureVersion ddmStructureVersion);

	/**
	* Creates a new ddm structure version with the primary key. Does not add the ddm structure version to the database.
	*
	* @param structureVersionId the primary key for the new ddm structure version
	* @return the new ddm structure version
	*/
	public DDMStructureVersion createDDMStructureVersion(
		long structureVersionId);

	/**
	* Deletes the ddm structure version from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the ddm structure version
	* @return the ddm structure version that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDMStructureVersion deleteDDMStructureVersion(
		DDMStructureVersion ddmStructureVersion);

	/**
	* Deletes the ddm structure version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureVersionId the primary key of the ddm structure version
	* @return the ddm structure version that was removed
	* @throws PortalException if a ddm structure version with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDMStructureVersion deleteDDMStructureVersion(
		long structureVersionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureVersion fetchDDMStructureVersion(long structureVersionId);

	/**
	* Returns the ddm structure version with the primary key.
	*
	* @param structureVersionId the primary key of the ddm structure version
	* @return the ddm structure version
	* @throws PortalException if a ddm structure version with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureVersion getDDMStructureVersion(long structureVersionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureVersion getLatestStructureVersion(long structureId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureVersion getStructureVersion(long structureId,
		java.lang.String version) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureVersion getStructureVersion(long structureVersionId)
		throws PortalException;

	/**
	* Updates the ddm structure version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the ddm structure version
	* @return the ddm structure version that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDMStructureVersion updateDDMStructureVersion(
		DDMStructureVersion ddmStructureVersion);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Returns the number of ddm structure versions.
	*
	* @return the number of ddm structure versions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDDMStructureVersionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getStructureVersionsCount(long structureId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns a range of all the ddm structure versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm structure versions
	* @param end the upper bound of the range of ddm structure versions (not inclusive)
	* @return the range of ddm structure versions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructureVersion> getDDMStructureVersions(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructureVersion> getStructureVersions(long structureId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructureVersion> getStructureVersions(long structureId,
		int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);
}