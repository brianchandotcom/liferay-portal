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
 * Provides the local service utility for WSRPProducer. This utility wraps
 * {@link com.liferay.wsrp.service.impl.WSRPProducerLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WSRPProducerLocalService
 * @see com.liferay.wsrp.service.base.WSRPProducerLocalServiceBaseImpl
 * @see com.liferay.wsrp.service.impl.WSRPProducerLocalServiceImpl
 * @generated
 */
@ProviderType
public class WSRPProducerLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.wsrp.service.impl.WSRPProducerLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.wsrp.model.WSRPProducer addWSRPProducer(
		long userId, long groupId, java.lang.String name,
		java.lang.String version, java.lang.String portletIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addWSRPProducer(userId, groupId, name, version, portletIds,
			serviceContext);
	}

	public static com.liferay.wsrp.model.WSRPProducer addWSRPProducer(
		long userId, java.lang.String name, java.lang.String version,
		java.lang.String portletIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addWSRPProducer(userId, name, version, portletIds,
			serviceContext);
	}

	/**
	* Adds the wsrp producer to the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpProducer the wsrp producer
	* @return the wsrp producer that was added
	*/
	public static com.liferay.wsrp.model.WSRPProducer addWSRPProducer(
		com.liferay.wsrp.model.WSRPProducer wsrpProducer) {
		return getService().addWSRPProducer(wsrpProducer);
	}

	/**
	* Creates a new wsrp producer with the primary key. Does not add the wsrp producer to the database.
	*
	* @param wsrpProducerId the primary key for the new wsrp producer
	* @return the new wsrp producer
	*/
	public static com.liferay.wsrp.model.WSRPProducer createWSRPProducer(
		long wsrpProducerId) {
		return getService().createWSRPProducer(wsrpProducerId);
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
	* Deletes the wsrp producer with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpProducerId the primary key of the wsrp producer
	* @return the wsrp producer that was removed
	* @throws PortalException if a wsrp producer with the primary key could not be found
	*/
	public static com.liferay.wsrp.model.WSRPProducer deleteWSRPProducer(
		long wsrpProducerId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteWSRPProducer(wsrpProducerId);
	}

	/**
	* Deletes the wsrp producer from the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpProducer the wsrp producer
	* @return the wsrp producer that was removed
	* @throws PortalException
	*/
	public static com.liferay.wsrp.model.WSRPProducer deleteWSRPProducer(
		com.liferay.wsrp.model.WSRPProducer wsrpProducer)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteWSRPProducer(wsrpProducer);
	}

	public static void deleteWSRPProducers(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteWSRPProducers(companyId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPProducerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPProducerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.wsrp.model.WSRPProducer fetchWSRPProducer(
		long wsrpProducerId) {
		return getService().fetchWSRPProducer(wsrpProducerId);
	}

	/**
	* Returns the wsrp producer matching the UUID and group.
	*
	* @param uuid the wsrp producer's UUID
	* @param groupId the primary key of the group
	* @return the matching wsrp producer, or <code>null</code> if a matching wsrp producer could not be found
	*/
	public static com.liferay.wsrp.model.WSRPProducer fetchWSRPProducerByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchWSRPProducerByUuidAndGroupId(uuid, groupId);
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
	* Returns the wsrp producer with the primary key.
	*
	* @param wsrpProducerId the primary key of the wsrp producer
	* @return the wsrp producer
	* @throws PortalException if a wsrp producer with the primary key could not be found
	*/
	public static com.liferay.wsrp.model.WSRPProducer getWSRPProducer(
		long wsrpProducerId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWSRPProducer(wsrpProducerId);
	}

	public static com.liferay.wsrp.model.WSRPProducer getWSRPProducer(
		java.lang.String wsrpProducerUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWSRPProducer(wsrpProducerUuid);
	}

	/**
	* Returns the wsrp producer matching the UUID and group.
	*
	* @param uuid the wsrp producer's UUID
	* @param groupId the primary key of the group
	* @return the matching wsrp producer
	* @throws PortalException if a matching wsrp producer could not be found
	*/
	public static com.liferay.wsrp.model.WSRPProducer getWSRPProducerByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWSRPProducerByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the wsrp producers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPProducerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp producers
	* @param end the upper bound of the range of wsrp producers (not inclusive)
	* @return the range of wsrp producers
	*/
	public static java.util.List<com.liferay.wsrp.model.WSRPProducer> getWSRPProducers(
		int start, int end) {
		return getService().getWSRPProducers(start, end);
	}

	public static java.util.List<com.liferay.wsrp.model.WSRPProducer> getWSRPProducers(
		long companyId, int start, int end) {
		return getService().getWSRPProducers(companyId, start, end);
	}

	/**
	* Returns all the wsrp producers matching the UUID and company.
	*
	* @param uuid the UUID of the wsrp producers
	* @param companyId the primary key of the company
	* @return the matching wsrp producers, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.wsrp.model.WSRPProducer> getWSRPProducersByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getWSRPProducersByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of wsrp producers matching the UUID and company.
	*
	* @param uuid the UUID of the wsrp producers
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of wsrp producers
	* @param end the upper bound of the range of wsrp producers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching wsrp producers, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.wsrp.model.WSRPProducer> getWSRPProducersByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wsrp.model.WSRPProducer> orderByComparator) {
		return getService()
				   .getWSRPProducersByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of wsrp producers.
	*
	* @return the number of wsrp producers
	*/
	public static int getWSRPProducersCount() {
		return getService().getWSRPProducersCount();
	}

	public static int getWSRPProducersCount(long companyId) {
		return getService().getWSRPProducersCount(companyId);
	}

	public static com.liferay.wsrp.model.WSRPProducer updateWSRPProducer(
		long wsrpProducerId, java.lang.String name, java.lang.String version,
		java.lang.String portletIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateWSRPProducer(wsrpProducerId, name, version, portletIds);
	}

	/**
	* Updates the wsrp producer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wsrpProducer the wsrp producer
	* @return the wsrp producer that was updated
	*/
	public static com.liferay.wsrp.model.WSRPProducer updateWSRPProducer(
		com.liferay.wsrp.model.WSRPProducer wsrpProducer) {
		return getService().updateWSRPProducer(wsrpProducer);
	}

	public static WSRPProducerLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<WSRPProducerLocalService, WSRPProducerLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(WSRPProducerLocalService.class);

		ServiceTracker<WSRPProducerLocalService, WSRPProducerLocalService> serviceTracker =
			new ServiceTracker<WSRPProducerLocalService, WSRPProducerLocalService>(bundle.getBundleContext(),
				WSRPProducerLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}