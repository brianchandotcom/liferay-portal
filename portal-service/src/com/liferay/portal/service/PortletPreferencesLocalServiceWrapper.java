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
 * Provides a wrapper for {@link PortletPreferencesLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesLocalService
 * @generated
 */
@ProviderType
public class PortletPreferencesLocalServiceWrapper
	implements PortletPreferencesLocalService,
		ServiceWrapper<PortletPreferencesLocalService> {
	public PortletPreferencesLocalServiceWrapper(
		PortletPreferencesLocalService portletPreferencesLocalService) {
		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Override
	public com.liferay.portal.model.PortletPreferences addPortletPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId, com.liferay.portal.model.Portlet portlet,
		java.lang.String defaultPreferences) {
		return _portletPreferencesLocalService.addPortletPreferences(companyId,
			ownerId, ownerType, plid, portletId, portlet, defaultPreferences);
	}

	/**
	* Adds the portlet preferences to the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferences the portlet preferences
	* @return the portlet preferences that was added
	*/
	@Override
	public com.liferay.portal.model.PortletPreferences addPortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		return _portletPreferencesLocalService.addPortletPreferences(portletPreferences);
	}

	/**
	* Creates a new portlet preferences with the primary key. Does not add the portlet preferences to the database.
	*
	* @param portletPreferencesId the primary key for the new portlet preferences
	* @return the new portlet preferences
	*/
	@Override
	public com.liferay.portal.model.PortletPreferences createPortletPreferences(
		long portletPreferencesId) {
		return _portletPreferencesLocalService.createPortletPreferences(portletPreferencesId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletPreferencesLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deletePortletPreferences(long ownerId, int ownerType, long plid) {
		_portletPreferencesLocalService.deletePortletPreferences(ownerId,
			ownerType, plid);
	}

	@Override
	public void deletePortletPreferences(long ownerId, int ownerType,
		long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_portletPreferencesLocalService.deletePortletPreferences(ownerId,
			ownerType, plid, portletId);
	}

	/**
	* Deletes the portlet preferences from the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferences the portlet preferences
	* @return the portlet preferences that was removed
	*/
	@Override
	public com.liferay.portal.model.PortletPreferences deletePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		return _portletPreferencesLocalService.deletePortletPreferences(portletPreferences);
	}

	/**
	* Deletes the portlet preferences with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences that was removed
	* @throws PortalException if a portlet preferences with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.PortletPreferences deletePortletPreferences(
		long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletPreferencesLocalService.deletePortletPreferences(portletPreferencesId);
	}

	@Override
	public void deletePortletPreferencesByPlid(long plid) {
		_portletPreferencesLocalService.deletePortletPreferencesByPlid(plid);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _portletPreferencesLocalService.dynamicQuery();
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
		return _portletPreferencesLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _portletPreferencesLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _portletPreferencesLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _portletPreferencesLocalService.dynamicQueryCount(dynamicQuery);
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
		return _portletPreferencesLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.PortletPreferences fetchPortletPreferences(
		long portletPreferencesId) {
		return _portletPreferencesLocalService.fetchPortletPreferences(portletPreferencesId);
	}

	@Override
	public javax.portlet.PortletPreferences fetchPreferences(long companyId,
		long ownerId, int ownerType, long plid, java.lang.String portletId) {
		return _portletPreferencesLocalService.fetchPreferences(companyId,
			ownerId, ownerType, plid, portletId);
	}

	@Override
	public javax.portlet.PortletPreferences fetchPreferences(
		com.liferay.portal.model.PortletPreferencesIds portletPreferencesIds) {
		return _portletPreferencesLocalService.fetchPreferences(portletPreferencesIds);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _portletPreferencesLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _portletPreferencesLocalService.getBeanIdentifier();
	}

	@Override
	public javax.portlet.PortletPreferences getDefaultPreferences(
		long companyId, java.lang.String portletId) {
		return _portletPreferencesLocalService.getDefaultPreferences(companyId,
			portletId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletPreferencesLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences() {
		return _portletPreferencesLocalService.getPortletPreferences();
	}

	@Override
	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long companyId, long groupId, long ownerId, int ownerType,
		java.lang.String portletId) {
		return _portletPreferencesLocalService.getPortletPreferences(companyId,
			groupId, ownerId, ownerType, portletId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long ownerId, int ownerType, long plid) {
		return _portletPreferencesLocalService.getPortletPreferences(ownerId,
			ownerType, plid);
	}

	@Override
	public com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletPreferencesLocalService.getPortletPreferences(ownerId,
			ownerType, plid, portletId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		int ownerType, long plid, java.lang.String portletId) {
		return _portletPreferencesLocalService.getPortletPreferences(ownerType,
			plid, portletId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long plid, java.lang.String portletId) {
		return _portletPreferencesLocalService.getPortletPreferences(plid,
			portletId);
	}

	/**
	* Returns the portlet preferences with the primary key.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences
	* @throws PortalException if a portlet preferences with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletPreferencesLocalService.getPortletPreferences(portletPreferencesId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferencesByPlid(
		long plid) {
		return _portletPreferencesLocalService.getPortletPreferencesByPlid(plid);
	}

	@Override
	public long getPortletPreferencesCount(long ownerId, int ownerType,
		long plid, com.liferay.portal.model.Portlet portlet,
		boolean excludeDefaultPreferences) {
		return _portletPreferencesLocalService.getPortletPreferencesCount(ownerId,
			ownerType, plid, portlet, excludeDefaultPreferences);
	}

	@Override
	public long getPortletPreferencesCount(long ownerId, int ownerType,
		java.lang.String portletId, boolean excludeDefaultPreferences) {
		return _portletPreferencesLocalService.getPortletPreferencesCount(ownerId,
			ownerType, portletId, excludeDefaultPreferences);
	}

	@Override
	public long getPortletPreferencesCount(int ownerType, long plid,
		java.lang.String portletId) {
		return _portletPreferencesLocalService.getPortletPreferencesCount(ownerType,
			plid, portletId);
	}

	@Override
	public long getPortletPreferencesCount(int ownerType,
		java.lang.String portletId) {
		return _portletPreferencesLocalService.getPortletPreferencesCount(ownerType,
			portletId);
	}

	/**
	* Returns a range of all the portlet preferenceses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of portlet preferenceses
	*/
	@Override
	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferenceses(
		int start, int end) {
		return _portletPreferencesLocalService.getPortletPreferenceses(start,
			end);
	}

	/**
	* Returns the number of portlet preferenceses.
	*
	* @return the number of portlet preferenceses
	*/
	@Override
	public int getPortletPreferencesesCount() {
		return _portletPreferencesLocalService.getPortletPreferencesesCount();
	}

	@Override
	public javax.portlet.PortletPreferences getPreferences(long companyId,
		long ownerId, int ownerType, long plid, java.lang.String portletId) {
		return _portletPreferencesLocalService.getPreferences(companyId,
			ownerId, ownerType, plid, portletId);
	}

	@Override
	public javax.portlet.PortletPreferences getPreferences(long companyId,
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		java.lang.String defaultPreferences) {
		return _portletPreferencesLocalService.getPreferences(companyId,
			ownerId, ownerType, plid, portletId, defaultPreferences);
	}

	@Override
	public javax.portlet.PortletPreferences getPreferences(
		com.liferay.portal.model.PortletPreferencesIds portletPreferencesIds) {
		return _portletPreferencesLocalService.getPreferences(portletPreferencesIds);
	}

	@Override
	public javax.portlet.PortletPreferences getStrictPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId) {
		return _portletPreferencesLocalService.getStrictPreferences(companyId,
			ownerId, ownerType, plid, portletId);
	}

	@Override
	public javax.portlet.PortletPreferences getStrictPreferences(
		com.liferay.portal.model.PortletPreferencesIds portletPreferencesIds) {
		return _portletPreferencesLocalService.getStrictPreferences(portletPreferencesIds);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_portletPreferencesLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the portlet preferences in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param portletPreferences the portlet preferences
	* @return the portlet preferences that was updated
	*/
	@Override
	public com.liferay.portal.model.PortletPreferences updatePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		return _portletPreferencesLocalService.updatePortletPreferences(portletPreferences);
	}

	@Override
	public com.liferay.portal.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		javax.portlet.PortletPreferences portletPreferences) {
		return _portletPreferencesLocalService.updatePreferences(ownerId,
			ownerType, plid, portletId, portletPreferences);
	}

	@Override
	public com.liferay.portal.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		java.lang.String xml) {
		return _portletPreferencesLocalService.updatePreferences(ownerId,
			ownerType, plid, portletId, xml);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public PortletPreferencesLocalService getWrappedPortletPreferencesLocalService() {
		return _portletPreferencesLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {
		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Override
	public PortletPreferencesLocalService getWrappedService() {
		return _portletPreferencesLocalService;
	}

	@Override
	public void setWrappedService(
		PortletPreferencesLocalService portletPreferencesLocalService) {
		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	private PortletPreferencesLocalService _portletPreferencesLocalService;
}