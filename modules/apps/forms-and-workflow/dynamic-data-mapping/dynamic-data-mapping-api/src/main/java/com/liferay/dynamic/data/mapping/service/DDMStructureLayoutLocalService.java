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

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for DDMStructureLayout. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayoutLocalServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMStructureLayoutLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMStructureLayoutLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMStructureLayoutLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureLayoutLocalServiceUtil} to access the ddm structure layout local service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMStructureLayoutLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormLayout getStructureLayoutDDMFormLayout(
		DDMStructureLayout structureLayout) throws PortalException;

	/**
	* Adds the ddm structure layout to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureLayout the ddm structure layout
	* @return the ddm structure layout that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDMStructureLayout addDDMStructureLayout(
		DDMStructureLayout ddmStructureLayout);

	public DDMStructureLayout addStructureLayout(long userId, long groupId,
		long structureVersionId, DDMFormLayout ddmFormLayout,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new ddm structure layout with the primary key. Does not add the ddm structure layout to the database.
	*
	* @param structureLayoutId the primary key for the new ddm structure layout
	* @return the new ddm structure layout
	*/
	public DDMStructureLayout createDDMStructureLayout(long structureLayoutId);

	/**
	* Deletes the ddm structure layout from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureLayout the ddm structure layout
	* @return the ddm structure layout that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDMStructureLayout deleteDDMStructureLayout(
		DDMStructureLayout ddmStructureLayout);

	/**
	* Deletes the ddm structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureLayoutId the primary key of the ddm structure layout
	* @return the ddm structure layout that was removed
	* @throws PortalException if a ddm structure layout with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDMStructureLayout deleteDDMStructureLayout(long structureLayoutId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureLayout fetchDDMStructureLayout(long structureLayoutId);

	/**
	* Returns the ddm structure layout matching the UUID and group.
	*
	* @param uuid the ddm structure layout's UUID
	* @param groupId the primary key of the group
	* @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureLayout fetchDDMStructureLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	/**
	* Returns the ddm structure layout with the primary key.
	*
	* @param structureLayoutId the primary key of the ddm structure layout
	* @return the ddm structure layout
	* @throws PortalException if a ddm structure layout with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureLayout getDDMStructureLayout(long structureLayoutId)
		throws PortalException;

	/**
	* Returns the ddm structure layout matching the UUID and group.
	*
	* @param uuid the ddm structure layout's UUID
	* @param groupId the primary key of the group
	* @return the matching ddm structure layout
	* @throws PortalException if a matching ddm structure layout could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureLayout getDDMStructureLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureLayout getStructureLayout(long structureLayoutId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructureLayout getStructureLayoutByStructureVersionId(
		long structureVersionId) throws PortalException;

	/**
	* Updates the ddm structure layout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureLayout the ddm structure layout
	* @return the ddm structure layout that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDMStructureLayout updateDDMStructureLayout(
		DDMStructureLayout ddmStructureLayout);

	public DDMStructureLayout updateStructureLayout(long structureLayoutId,
		DDMFormLayout ddmFormLayout, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

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
	* Returns the number of ddm structure layouts.
	*
	* @return the number of ddm structure layouts
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDDMStructureLayoutsCount();

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the ddm structure layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm structure layouts
	* @param end the upper bound of the range of ddm structure layouts (not inclusive)
	* @return the range of ddm structure layouts
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructureLayout> getDDMStructureLayouts(int start, int end);

	/**
	* Returns all the ddm structure layouts matching the UUID and company.
	*
	* @param uuid the UUID of the ddm structure layouts
	* @param companyId the primary key of the company
	* @return the matching ddm structure layouts, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructureLayout> getDDMStructureLayoutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of ddm structure layouts matching the UUID and company.
	*
	* @param uuid the UUID of the ddm structure layouts
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of ddm structure layouts
	* @param end the upper bound of the range of ddm structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching ddm structure layouts, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructureLayout> getDDMStructureLayoutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator);

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

	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteStructureLayout(DDMStructureLayout structureLayout);

	public void deleteStructureLayout(long structureLayoutId)
		throws PortalException;
}