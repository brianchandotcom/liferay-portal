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

package com.liferay.wsrp.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for WSRPConsumerPortlet. This utility wraps
 * {@link com.liferay.wsrp.service.impl.WSRPConsumerPortletLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WSRPConsumerPortletLocalService
 * @see com.liferay.wsrp.service.base.WSRPConsumerPortletLocalServiceBaseImpl
 * @see com.liferay.wsrp.service.impl.WSRPConsumerPortletLocalServiceImpl
 * @generated
 */
@ProviderType
public class WSRPConsumerPortletLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.wsrp.service.impl.WSRPConsumerPortletLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.wsrp.model.WSRPConsumerPortlet addWSRPConsumerPortlet(
		long wsrpConsumerId, java.lang.String name,
		java.lang.String portletHandle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addWSRPConsumerPortlet(wsrpConsumerId, name, portletHandle,
			serviceContext);
	}

	public static com.liferay.wsrp.model.WSRPConsumerPortlet addWSRPConsumerPortlet(
		java.lang.String wsrpConsumerUuid, java.lang.String name,
		java.lang.String portletHandle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addWSRPConsumerPortlet(wsrpConsumerUuid, name,
			portletHandle, serviceContext);
	}

	/**
	* Adds the wsrp consumer portlet to the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpConsumerPortlet the wsrp consumer portlet
	* @return the wsrp consumer portlet that was added
	*/
	public static com.liferay.wsrp.model.WSRPConsumerPortlet addWSRPConsumerPortlet(
		com.liferay.wsrp.model.WSRPConsumerPortlet wsrpConsumerPortlet) {
		return getService().addWSRPConsumerPortlet(wsrpConsumerPortlet);
	}

	/**
	* Creates a new wsrp consumer portlet with the primary key. Does not add the wsrp consumer portlet to the database.
	*
	* @param wsrpConsumerPortletId the primary key for the new wsrp consumer portlet
	* @return the new wsrp consumer portlet
	*/
	public static com.liferay.wsrp.model.WSRPConsumerPortlet createWSRPConsumerPortlet(
		long wsrpConsumerPortletId) {
		return getService().createWSRPConsumerPortlet(wsrpConsumerPortletId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the wsrp consumer portlet with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpConsumerPortletId the primary key of the wsrp consumer portlet
	* @return the wsrp consumer portlet that was removed
	* @throws PortalException if a wsrp consumer portlet with the primary key could not be found
	*/
	public static com.liferay.wsrp.model.WSRPConsumerPortlet deleteWSRPConsumerPortlet(
		long wsrpConsumerPortletId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteWSRPConsumerPortlet(wsrpConsumerPortletId);
	}

	public static void deleteWSRPConsumerPortlet(
		java.lang.String wsrpConsumerPortletUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteWSRPConsumerPortlet(wsrpConsumerPortletUuid);
	}

	/**
	* Deletes the wsrp consumer portlet from the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpConsumerPortlet the wsrp consumer portlet
	* @return the wsrp consumer portlet that was removed
	* @throws PortalException
	*/
	public static com.liferay.wsrp.model.WSRPConsumerPortlet deleteWSRPConsumerPortlet(
		com.liferay.wsrp.model.WSRPConsumerPortlet wsrpConsumerPortlet)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteWSRPConsumerPortlet(wsrpConsumerPortlet);
	}

	public static void deleteWSRPConsumerPortlets(long wsrpConsumerId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteWSRPConsumerPortlets(wsrpConsumerId);
	}

	public static void destroyWSRPConsumerPortlet(long wsrpConsumerPortletId,
		java.lang.String wsrpConsumerPortletUuid, java.lang.String url) {
		getService()
			.destroyWSRPConsumerPortlet(wsrpConsumerPortletId,
			wsrpConsumerPortletUuid, url);
	}

	public static void destroyWSRPConsumerPortlets()
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().destroyWSRPConsumerPortlets();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.wsrp.model.WSRPConsumerPortlet fetchWSRPConsumerPortlet(
		long wsrpConsumerPortletId) {
		return getService().fetchWSRPConsumerPortlet(wsrpConsumerPortletId);
	}

	/**
	* Returns the wsrp consumer portlet with the matching UUID and company.
	*
	* @param uuid the wsrp consumer portlet's UUID
	* @param companyId the primary key of the company
	* @return the matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public static com.liferay.wsrp.model.WSRPConsumerPortlet fetchWSRPConsumerPortletByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .fetchWSRPConsumerPortletByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the wsrp consumer portlet with the primary key.
	*
	* @param wsrpConsumerPortletId the primary key of the wsrp consumer portlet
	* @return the wsrp consumer portlet
	* @throws PortalException if a wsrp consumer portlet with the primary key could not be found
	*/
	public static com.liferay.wsrp.model.WSRPConsumerPortlet getWSRPConsumerPortlet(
		long wsrpConsumerPortletId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWSRPConsumerPortlet(wsrpConsumerPortletId);
	}

	public static com.liferay.wsrp.model.WSRPConsumerPortlet getWSRPConsumerPortlet(
		long wsrpConsumerId, java.lang.String portletHandle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWSRPConsumerPortlet(wsrpConsumerId, portletHandle);
	}

	public static com.liferay.wsrp.model.WSRPConsumerPortlet getWSRPConsumerPortlet(
		java.lang.String wsrpConsumerPortletUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWSRPConsumerPortlet(wsrpConsumerPortletUuid);
	}

	/**
	* Returns the wsrp consumer portlet with the matching UUID and company.
	*
	* @param uuid the wsrp consumer portlet's UUID
	* @param companyId the primary key of the company
	* @return the matching wsrp consumer portlet
	* @throws PortalException if a matching wsrp consumer portlet could not be found
	*/
	public static com.liferay.wsrp.model.WSRPConsumerPortlet getWSRPConsumerPortletByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getWSRPConsumerPortletByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of all the wsrp consumer portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @return the range of wsrp consumer portlets
	*/
	public static java.util.List<com.liferay.wsrp.model.WSRPConsumerPortlet> getWSRPConsumerPortlets(
		int start, int end) {
		return getService().getWSRPConsumerPortlets(start, end);
	}

	public static java.util.List<com.liferay.wsrp.model.WSRPConsumerPortlet> getWSRPConsumerPortlets(
		long wsrpConsumerId, int start, int end) {
		return getService().getWSRPConsumerPortlets(wsrpConsumerId, start, end);
	}

	/**
	* Returns the number of wsrp consumer portlets.
	*
	* @return the number of wsrp consumer portlets
	*/
	public static int getWSRPConsumerPortletsCount() {
		return getService().getWSRPConsumerPortletsCount();
	}

	public static int getWSRPConsumerPortletsCount(long wsrpConsumerId) {
		return getService().getWSRPConsumerPortletsCount(wsrpConsumerId);
	}

	public static void initFailedWSRPConsumerPortlets() {
		getService().initFailedWSRPConsumerPortlets();
	}

	public static void initWSRPConsumerPortlet(long companyId,
		long wsrpConsumerId, long wsrpConsumerPortletId,
		java.lang.String wsrpConsumerPortletUuid, java.lang.String name,
		java.lang.String portletHandle)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.initWSRPConsumerPortlet(companyId, wsrpConsumerId,
			wsrpConsumerPortletId, wsrpConsumerPortletUuid, name, portletHandle);
	}

	public static void initWSRPConsumerPortlets() {
		getService().initWSRPConsumerPortlets();
	}

	public static com.liferay.wsrp.model.WSRPConsumerPortlet updateWSRPConsumerPortlet(
		long wsrpConsumerPortletId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateWSRPConsumerPortlet(wsrpConsumerPortletId, name);
	}

	/**
	* Updates the wsrp consumer portlet in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wsrpConsumerPortlet the wsrp consumer portlet
	* @return the wsrp consumer portlet that was updated
	*/
	public static com.liferay.wsrp.model.WSRPConsumerPortlet updateWSRPConsumerPortlet(
		com.liferay.wsrp.model.WSRPConsumerPortlet wsrpConsumerPortlet) {
		return getService().updateWSRPConsumerPortlet(wsrpConsumerPortlet);
	}

	public static WSRPConsumerPortletLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<WSRPConsumerPortletLocalService, WSRPConsumerPortletLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(WSRPConsumerPortletLocalService.class);

		ServiceTracker<WSRPConsumerPortletLocalService, WSRPConsumerPortletLocalService> serviceTracker =
			new ServiceTracker<WSRPConsumerPortletLocalService, WSRPConsumerPortletLocalService>(bundle.getBundleContext(),
				WSRPConsumerPortletLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}