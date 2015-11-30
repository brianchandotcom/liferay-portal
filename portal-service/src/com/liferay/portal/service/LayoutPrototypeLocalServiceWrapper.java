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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link LayoutPrototypeLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototypeLocalService
 * @generated
 */
@ProviderType
public class LayoutPrototypeLocalServiceWrapper
	implements LayoutPrototypeLocalService,
		ServiceWrapper<LayoutPrototypeLocalService> {
	public LayoutPrototypeLocalServiceWrapper(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {
		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	/**
	* Adds the layout prototype to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPrototype the layout prototype
	* @return the layout prototype that was added
	*/
	@Override
	public com.liferay.portal.model.LayoutPrototype addLayoutPrototype(
		com.liferay.portal.model.LayoutPrototype layoutPrototype) {
		return _layoutPrototypeLocalService.addLayoutPrototype(layoutPrototype);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addLayoutPrototype(long,
	long, Map, String, boolean, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.LayoutPrototype addLayoutPrototype(
		long userId, long companyId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.addLayoutPrototype(userId,
			companyId, nameMap, description, active);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addLayoutPrototype(long,
	long, Map, Map, boolean, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.LayoutPrototype addLayoutPrototype(
		long userId, long companyId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.addLayoutPrototype(userId,
			companyId, nameMap, description, active, serviceContext);
	}

	@Override
	public com.liferay.portal.model.LayoutPrototype addLayoutPrototype(
		long userId, long companyId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean active, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.addLayoutPrototype(userId,
			companyId, nameMap, descriptionMap, active, serviceContext);
	}

	/**
	* Creates a new layout prototype with the primary key. Does not add the layout prototype to the database.
	*
	* @param layoutPrototypeId the primary key for the new layout prototype
	* @return the new layout prototype
	*/
	@Override
	public com.liferay.portal.model.LayoutPrototype createLayoutPrototype(
		long layoutPrototypeId) {
		return _layoutPrototypeLocalService.createLayoutPrototype(layoutPrototypeId);
	}

	/**
	* Deletes the layout prototype from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPrototype the layout prototype
	* @return the layout prototype that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.LayoutPrototype deleteLayoutPrototype(
		com.liferay.portal.model.LayoutPrototype layoutPrototype)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.deleteLayoutPrototype(layoutPrototype);
	}

	/**
	* Deletes the layout prototype with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPrototypeId the primary key of the layout prototype
	* @return the layout prototype that was removed
	* @throws PortalException if a layout prototype with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.LayoutPrototype deleteLayoutPrototype(
		long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.deleteLayoutPrototype(layoutPrototypeId);
	}

	@Override
	public void deleteNondefaultLayoutPrototypes(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutPrototypeLocalService.deleteNondefaultLayoutPrototypes(companyId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutPrototypeLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _layoutPrototypeLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _layoutPrototypeLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _layoutPrototypeLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _layoutPrototypeLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _layoutPrototypeLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.LayoutPrototype fetchLayoutPrototype(
		long layoutPrototypeId) {
		return _layoutPrototypeLocalService.fetchLayoutPrototype(layoutPrototypeId);
	}

	/**
	* Returns the layout prototype with the matching UUID and company.
	*
	* @param uuid the layout prototype's UUID
	* @param companyId the primary key of the company
	* @return the matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	@Override
	public com.liferay.portal.model.LayoutPrototype fetchLayoutPrototypeByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _layoutPrototypeLocalService.fetchLayoutPrototypeByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _layoutPrototypeLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _layoutPrototypeLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _layoutPrototypeLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the layout prototype with the primary key.
	*
	* @param layoutPrototypeId the primary key of the layout prototype
	* @return the layout prototype
	* @throws PortalException if a layout prototype with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.LayoutPrototype getLayoutPrototype(
		long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.getLayoutPrototype(layoutPrototypeId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getLayoutPrototypeByUuidAndCompanyId(String, long)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.LayoutPrototype getLayoutPrototypeByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.getLayoutPrototypeByUuid(uuid);
	}

	/**
	* Returns the layout prototype with the matching UUID and company.
	*
	* @param uuid the layout prototype's UUID
	* @param companyId the primary key of the company
	* @return the matching layout prototype
	* @throws PortalException if a matching layout prototype could not be found
	*/
	@Override
	public com.liferay.portal.model.LayoutPrototype getLayoutPrototypeByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.getLayoutPrototypeByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of all the layout prototypes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of layout prototypes
	*/
	@Override
	public java.util.List<com.liferay.portal.model.LayoutPrototype> getLayoutPrototypes(
		int start, int end) {
		return _layoutPrototypeLocalService.getLayoutPrototypes(start, end);
	}

	/**
	* Returns the number of layout prototypes.
	*
	* @return the number of layout prototypes
	*/
	@Override
	public int getLayoutPrototypesCount() {
		return _layoutPrototypeLocalService.getLayoutPrototypesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutPrototypeLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutPrototype> search(
		long companyId, java.lang.Boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.LayoutPrototype> obc) {
		return _layoutPrototypeLocalService.search(companyId, active, start,
			end, obc);
	}

	@Override
	public int searchCount(long companyId, java.lang.Boolean active) {
		return _layoutPrototypeLocalService.searchCount(companyId, active);
	}

	/**
	* Updates the layout prototype in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutPrototype the layout prototype
	* @return the layout prototype that was updated
	*/
	@Override
	public com.liferay.portal.model.LayoutPrototype updateLayoutPrototype(
		com.liferay.portal.model.LayoutPrototype layoutPrototype) {
		return _layoutPrototypeLocalService.updateLayoutPrototype(layoutPrototype);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updateLayoutPrototype(long,
	Map, String, boolean, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.LayoutPrototype updateLayoutPrototype(
		long layoutPrototypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.updateLayoutPrototype(layoutPrototypeId,
			nameMap, description, active);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateLayoutPrototype(long,
	Map, Map, boolean, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.LayoutPrototype updateLayoutPrototype(
		long layoutPrototypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.updateLayoutPrototype(layoutPrototypeId,
			nameMap, description, active, serviceContext);
	}

	@Override
	public com.liferay.portal.model.LayoutPrototype updateLayoutPrototype(
		long layoutPrototypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean active, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPrototypeLocalService.updateLayoutPrototype(layoutPrototypeId,
			nameMap, descriptionMap, active, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public LayoutPrototypeLocalService getWrappedLayoutPrototypeLocalService() {
		return _layoutPrototypeLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedLayoutPrototypeLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {
		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Override
	public LayoutPrototypeLocalService getWrappedService() {
		return _layoutPrototypeLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {
		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	private LayoutPrototypeLocalService _layoutPrototypeLocalService;
}