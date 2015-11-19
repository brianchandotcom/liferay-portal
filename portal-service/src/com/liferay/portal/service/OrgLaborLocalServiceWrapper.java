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
 * Provides a wrapper for {@link OrgLaborLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see OrgLaborLocalService
 * @generated
 */
@ProviderType
public class OrgLaborLocalServiceWrapper implements OrgLaborLocalService,
	ServiceWrapper<OrgLaborLocalService> {
	public OrgLaborLocalServiceWrapper(
		OrgLaborLocalService orgLaborLocalService) {
		_orgLaborLocalService = orgLaborLocalService;
	}

	/**
	* Adds the org labor to the database. Also notifies the appropriate model listeners.
	*
	* @param orgLabor the org labor
	* @return the org labor that was added
	*/
	@Override
	public com.liferay.portal.model.OrgLabor addOrgLabor(
		com.liferay.portal.model.OrgLabor orgLabor) {
		return _orgLaborLocalService.addOrgLabor(orgLabor);
	}

	@Override
	public com.liferay.portal.model.OrgLabor addOrgLabor(long organizationId,
		long typeId, int sunOpen, int sunClose, int monOpen, int monClose,
		int tueOpen, int tueClose, int wedOpen, int wedClose, int thuOpen,
		int thuClose, int friOpen, int friClose, int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _orgLaborLocalService.addOrgLabor(organizationId, typeId,
			sunOpen, sunClose, monOpen, monClose, tueOpen, tueClose, wedOpen,
			wedClose, thuOpen, thuClose, friOpen, friClose, satOpen, satClose);
	}

	/**
	* Creates a new org labor with the primary key. Does not add the org labor to the database.
	*
	* @param orgLaborId the primary key for the new org labor
	* @return the new org labor
	*/
	@Override
	public com.liferay.portal.model.OrgLabor createOrgLabor(long orgLaborId) {
		return _orgLaborLocalService.createOrgLabor(orgLaborId);
	}

	/**
	* Deletes the org labor from the database. Also notifies the appropriate model listeners.
	*
	* @param orgLabor the org labor
	* @return the org labor that was removed
	*/
	@Override
	public com.liferay.portal.model.OrgLabor deleteOrgLabor(
		com.liferay.portal.model.OrgLabor orgLabor) {
		return _orgLaborLocalService.deleteOrgLabor(orgLabor);
	}

	/**
	* Deletes the org labor with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orgLaborId the primary key of the org labor
	* @return the org labor that was removed
	* @throws PortalException if a org labor with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.OrgLabor deleteOrgLabor(long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _orgLaborLocalService.deleteOrgLabor(orgLaborId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _orgLaborLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _orgLaborLocalService.dynamicQuery();
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
		return _orgLaborLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgLaborModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _orgLaborLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgLaborModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _orgLaborLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _orgLaborLocalService.dynamicQueryCount(dynamicQuery);
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
		return _orgLaborLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.model.OrgLabor fetchOrgLabor(long orgLaborId) {
		return _orgLaborLocalService.fetchOrgLabor(orgLaborId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _orgLaborLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _orgLaborLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _orgLaborLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Returns the org labor with the primary key.
	*
	* @param orgLaborId the primary key of the org labor
	* @return the org labor
	* @throws PortalException if a org labor with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.OrgLabor getOrgLabor(long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _orgLaborLocalService.getOrgLabor(orgLaborId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.OrgLabor> getOrgLabors(
		long organizationId) {
		return _orgLaborLocalService.getOrgLabors(organizationId);
	}

	/**
	* Returns a range of all the org labors.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgLaborModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of org labors
	* @param end the upper bound of the range of org labors (not inclusive)
	* @return the range of org labors
	*/
	@Override
	public java.util.List<com.liferay.portal.model.OrgLabor> getOrgLabors(
		int start, int end) {
		return _orgLaborLocalService.getOrgLabors(start, end);
	}

	/**
	* Returns the number of org labors.
	*
	* @return the number of org labors
	*/
	@Override
	public int getOrgLaborsCount() {
		return _orgLaborLocalService.getOrgLaborsCount();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _orgLaborLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the org labor in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param orgLabor the org labor
	* @return the org labor that was updated
	*/
	@Override
	public com.liferay.portal.model.OrgLabor updateOrgLabor(
		com.liferay.portal.model.OrgLabor orgLabor) {
		return _orgLaborLocalService.updateOrgLabor(orgLabor);
	}

	@Override
	public com.liferay.portal.model.OrgLabor updateOrgLabor(long orgLaborId,
		long typeId, int sunOpen, int sunClose, int monOpen, int monClose,
		int tueOpen, int tueClose, int wedOpen, int wedClose, int thuOpen,
		int thuClose, int friOpen, int friClose, int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _orgLaborLocalService.updateOrgLabor(orgLaborId, typeId,
			sunOpen, sunClose, monOpen, monClose, tueOpen, tueClose, wedOpen,
			wedClose, thuOpen, thuClose, friOpen, friClose, satOpen, satClose);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public OrgLaborLocalService getWrappedOrgLaborLocalService() {
		return _orgLaborLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedOrgLaborLocalService(
		OrgLaborLocalService orgLaborLocalService) {
		_orgLaborLocalService = orgLaborLocalService;
	}

	@Override
	public OrgLaborLocalService getWrappedService() {
		return _orgLaborLocalService;
	}

	@Override
	public void setWrappedService(OrgLaborLocalService orgLaborLocalService) {
		_orgLaborLocalService = orgLaborLocalService;
	}

	private OrgLaborLocalService _orgLaborLocalService;
}