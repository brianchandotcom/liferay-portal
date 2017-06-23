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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for PortletPreferences. This utility wraps
 * {@link com.liferay.portal.service.impl.PortletPreferencesLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesLocalService
 * @see com.liferay.portal.service.base.PortletPreferencesLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.PortletPreferencesLocalServiceImpl
 * @generated
 */
@ProviderType
public class PortletPreferencesLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.PortletPreferencesLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the portlet preferences to the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferences the portlet preferences
	* @return the portlet preferences that was added
	*/
	public static com.liferay.portal.kernel.model.PortletPreferences addPortletPreferences(
		com.liferay.portal.kernel.model.PortletPreferences portletPreferences) {
		return getService().addPortletPreferences(portletPreferences);
	}

	public static com.liferay.portal.kernel.model.PortletPreferences addPortletPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId,
		com.liferay.portal.kernel.model.Portlet portlet,
		java.lang.String defaultPreferences) {
		return getService()
				   .addPortletPreferences(companyId, ownerId, ownerType, plid,
			portletId, portlet, defaultPreferences);
	}

	/**
	* Creates a new portlet preferences with the primary key. Does not add the portlet preferences to the database.
	*
	* @param portletPreferencesId the primary key for the new portlet preferences
	* @return the new portlet preferences
	*/
	public static com.liferay.portal.kernel.model.PortletPreferences createPortletPreferences(
		long portletPreferencesId) {
		return getService().createPortletPreferences(portletPreferencesId);
	}

	/**
	* Deletes the portlet preferences from the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferences the portlet preferences
	* @return the portlet preferences that was removed
	*/
	public static com.liferay.portal.kernel.model.PortletPreferences deletePortletPreferences(
		com.liferay.portal.kernel.model.PortletPreferences portletPreferences) {
		return getService().deletePortletPreferences(portletPreferences);
	}

	/**
	* Deletes the portlet preferences with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences that was removed
	* @throws PortalException if a portlet preferences with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.model.PortletPreferences deletePortletPreferences(
		long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePortletPreferences(portletPreferencesId);
	}

	public static com.liferay.portal.kernel.model.PortletPreferences fetchPortletPreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId) {
		return getService()
				   .fetchPortletPreferences(ownerId, ownerType, plid, portletId);
	}

	public static com.liferay.portal.kernel.model.PortletPreferences fetchPortletPreferences(
		long portletPreferencesId) {
		return getService().fetchPortletPreferences(portletPreferencesId);
	}

	public static com.liferay.portal.kernel.model.PortletPreferences getPortletPreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getPortletPreferences(ownerId, ownerType, plid, portletId);
	}

	/**
	* Returns the portlet preferences with the primary key.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences
	* @throws PortalException if a portlet preferences with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.model.PortletPreferences getPortletPreferences(
		long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPortletPreferences(portletPreferencesId);
	}

	/**
	* Updates the portlet preferences in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param portletPreferences the portlet preferences
	* @return the portlet preferences that was updated
	*/
	public static com.liferay.portal.kernel.model.PortletPreferences updatePortletPreferences(
		com.liferay.portal.kernel.model.PortletPreferences portletPreferences) {
		return getService().updatePortletPreferences(portletPreferences);
	}

	public static com.liferay.portal.kernel.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		java.lang.String xml) {
		return getService()
				   .updatePreferences(ownerId, ownerType, plid, portletId, xml);
	}

	public static com.liferay.portal.kernel.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		javax.portlet.PortletPreferences portletPreferences) {
		return getService()
				   .updatePreferences(ownerId, ownerType, plid, portletId,
			portletPreferences);
	}

	public static com.liferay.portal.kernel.settings.Settings getPortletInstanceSettings(
		long companyId, long groupId, java.lang.String portletId,
		com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator portletInstanceSettingsLocator,
		com.liferay.portal.kernel.settings.Settings portalPreferencesSettings) {
		return getService()
				   .getPortletInstanceSettings(companyId, groupId, portletId,
			portletInstanceSettingsLocator, portalPreferencesSettings);
	}

	/**
	* Returns the number of portlet preferenceses.
	*
	* @return the number of portlet preferenceses
	*/
	public static int getPortletPreferencesesCount() {
		return getService().getPortletPreferencesesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.PortletPreferences> getPortletPreferences() {
		return getService().getPortletPreferences();
	}

	public static java.util.List<com.liferay.portal.kernel.model.PortletPreferences> getPortletPreferences(
		int ownerType, long plid, java.lang.String portletId) {
		return getService().getPortletPreferences(ownerType, plid, portletId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.PortletPreferences> getPortletPreferences(
		long companyId, long groupId, long ownerId, int ownerType,
		java.lang.String portletId, boolean privateLayout) {
		return getService()
				   .getPortletPreferences(companyId, groupId, ownerId,
			ownerType, portletId, privateLayout);
	}

	public static java.util.List<com.liferay.portal.kernel.model.PortletPreferences> getPortletPreferences(
		long ownerId, int ownerType, long plid) {
		return getService().getPortletPreferences(ownerId, ownerType, plid);
	}

	public static java.util.List<com.liferay.portal.kernel.model.PortletPreferences> getPortletPreferences(
		long plid, java.lang.String portletId) {
		return getService().getPortletPreferences(plid, portletId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.PortletPreferences> getPortletPreferencesByPlid(
		long plid) {
		return getService().getPortletPreferencesByPlid(plid);
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
	public static java.util.List<com.liferay.portal.kernel.model.PortletPreferences> getPortletPreferenceses(
		int start, int end) {
		return getService().getPortletPreferenceses(start, end);
	}

	public static java.util.Map<java.lang.String, javax.portlet.PortletPreferences> getStrictPreferences(
		com.liferay.portal.kernel.model.Layout layout,
		java.util.List<com.liferay.portal.kernel.model.Portlet> portlets) {
		return getService().getStrictPreferences(layout, portlets);
	}

	public static javax.portlet.PortletPreferences fetchPreferences(
		com.liferay.portal.kernel.model.PortletPreferencesIds portletPreferencesIds) {
		return getService().fetchPreferences(portletPreferencesIds);
	}

	public static javax.portlet.PortletPreferences fetchPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId) {
		return getService()
				   .fetchPreferences(companyId, ownerId, ownerType, plid,
			portletId);
	}

	public static javax.portlet.PortletPreferences getDefaultPreferences(
		long companyId, java.lang.String portletId) {
		return getService().getDefaultPreferences(companyId, portletId);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		com.liferay.portal.kernel.model.PortletPreferencesIds portletPreferencesIds) {
		return getService().getPreferences(portletPreferencesIds);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId) {
		return getService()
				   .getPreferences(companyId, ownerId, ownerType, plid,
			portletId);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId, java.lang.String defaultPreferences) {
		return getService()
				   .getPreferences(companyId, ownerId, ownerType, plid,
			portletId, defaultPreferences);
	}

	public static javax.portlet.PortletPreferences getStrictPreferences(
		com.liferay.portal.kernel.model.PortletPreferencesIds portletPreferencesIds) {
		return getService().getStrictPreferences(portletPreferencesIds);
	}

	public static javax.portlet.PortletPreferences getStrictPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId) {
		return getService()
				   .getStrictPreferences(companyId, ownerId, ownerType, plid,
			portletId);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static long getPortletPreferencesCount(int ownerType,
		java.lang.String portletId) {
		return getService().getPortletPreferencesCount(ownerType, portletId);
	}

	public static long getPortletPreferencesCount(int ownerType, long plid,
		java.lang.String portletId) {
		return getService()
				   .getPortletPreferencesCount(ownerType, plid, portletId);
	}

	public static long getPortletPreferencesCount(long ownerId, int ownerType,
		java.lang.String portletId, boolean excludeDefaultPreferences) {
		return getService()
				   .getPortletPreferencesCount(ownerId, ownerType, portletId,
			excludeDefaultPreferences);
	}

	public static long getPortletPreferencesCount(long ownerId, int ownerType,
		long plid, com.liferay.portal.kernel.model.Portlet portlet,
		boolean excludeDefaultPreferences) {
		return getService()
				   .getPortletPreferencesCount(ownerId, ownerType, plid,
			portlet, excludeDefaultPreferences);
	}

	public static void deletePortletPreferences(long ownerId, int ownerType,
		long plid) {
		getService().deletePortletPreferences(ownerId, ownerType, plid);
	}

	public static void deletePortletPreferences(long ownerId, int ownerType,
		long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deletePortletPreferences(ownerId, ownerType, plid, portletId);
	}

	public static void deletePortletPreferencesByPlid(long plid) {
		getService().deletePortletPreferencesByPlid(plid);
	}

	public static PortletPreferencesLocalService getService() {
		if (_service == null) {
			_service = (PortletPreferencesLocalService)PortalBeanLocatorUtil.locate(PortletPreferencesLocalService.class.getName());

			ReferenceRegistry.registerReference(PortletPreferencesLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static PortletPreferencesLocalService _service;
}