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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for LayoutSetPrototype. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototypeLocalServiceUtil
 * @see com.liferay.portal.service.base.LayoutSetPrototypeLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutSetPrototypeLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutSetPrototypeLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetPrototypeLocalServiceUtil} to access the layout set prototype local service. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutSetPrototypeLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Adds the layout set prototype to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetPrototype the layout set prototype
	* @return the layout set prototype that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public LayoutSetPrototype addLayoutSetPrototype(
		LayoutSetPrototype layoutSetPrototype);

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addLayoutSetPrototype(long,
	long, Map, Map, boolean, boolean, ServiceContext)}
	*/
	@java.lang.Deprecated
	public LayoutSetPrototype addLayoutSetPrototype(long userId,
		long companyId, Map<Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active,
		boolean layoutsUpdateable, ServiceContext serviceContext)
		throws PortalException;

	public LayoutSetPrototype addLayoutSetPrototype(long userId,
		long companyId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, boolean active,
		boolean layoutsUpdateable, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new layout set prototype with the primary key. Does not add the layout set prototype to the database.
	*
	* @param layoutSetPrototypeId the primary key for the new layout set prototype
	* @return the new layout set prototype
	*/
	public LayoutSetPrototype createLayoutSetPrototype(
		long layoutSetPrototypeId);

	/**
	* Deletes the layout set prototype from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetPrototype the layout set prototype
	* @return the layout set prototype that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public LayoutSetPrototype deleteLayoutSetPrototype(
		LayoutSetPrototype layoutSetPrototype) throws PortalException;

	/**
	* Deletes the layout set prototype with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetPrototypeId the primary key of the layout set prototype
	* @return the layout set prototype that was removed
	* @throws PortalException if a layout set prototype with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public LayoutSetPrototype deleteLayoutSetPrototype(
		long layoutSetPrototypeId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSetPrototype fetchLayoutSetPrototype(long layoutSetPrototypeId);

	/**
	* Returns the layout set prototype with the matching UUID and company.
	*
	* @param uuid the layout set prototype's UUID
	* @param companyId the primary key of the company
	* @return the matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSetPrototype fetchLayoutSetPrototypeByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns the layout set prototype with the primary key.
	*
	* @param layoutSetPrototypeId the primary key of the layout set prototype
	* @return the layout set prototype
	* @throws PortalException if a layout set prototype with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSetPrototype getLayoutSetPrototype(long layoutSetPrototypeId)
		throws PortalException;

	/**
	* Returns the layout set prototype with the matching UUID and company.
	*
	* @param uuid the layout set prototype's UUID
	* @param companyId the primary key of the company
	* @return the matching layout set prototype
	* @throws PortalException if a matching layout set prototype could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSetPrototype getLayoutSetPrototypeByUuidAndCompanyId(
		java.lang.String uuid, long companyId) throws PortalException;

	/**
	* Updates the layout set prototype in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutSetPrototype the layout set prototype
	* @return the layout set prototype that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public LayoutSetPrototype updateLayoutSetPrototype(
		LayoutSetPrototype layoutSetPrototype);

	public LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId, java.lang.String settings)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#updateLayoutSetPrototype(long, Map, Map, boolean, boolean,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId, Map<Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active,
		boolean layoutsUpdateable, ServiceContext serviceContext)
		throws PortalException;

	public LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, boolean active,
		boolean layoutsUpdateable, ServiceContext serviceContext)
		throws PortalException;

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
	* Returns the number of layout set prototypes.
	*
	* @return the number of layout set prototypes
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutSetPrototypesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, java.lang.Boolean active);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the layout set prototypes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set prototypes
	* @param end the upper bound of the range of layout set prototypes (not inclusive)
	* @return the range of layout set prototypes
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutSetPrototype> getLayoutSetPrototypes(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutSetPrototype> getLayoutSetPrototypes(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutSetPrototype> search(long companyId,
		java.lang.Boolean active, int start, int end,
		OrderByComparator<LayoutSetPrototype> obc);

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

	public void deleteLayoutSetPrototypes() throws PortalException;

	public void deleteNondefaultLayoutSetPrototypes(long companyId)
		throws PortalException;
}