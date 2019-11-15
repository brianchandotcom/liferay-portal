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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for VirtualHost. This utility wraps
 * <code>com.liferay.portal.service.impl.VirtualHostLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostLocalService
 * @generated
 */
public class VirtualHostLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.VirtualHostLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the virtual host to the database. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHost the virtual host
	 * @return the virtual host that was added
	 */
	public static com.liferay.portal.kernel.model.VirtualHost addVirtualHost(
		com.liferay.portal.kernel.model.VirtualHost virtualHost) {

		return getService().addVirtualHost(virtualHost);
	}

	/**
	 * Creates a new virtual host with the primary key. Does not add the virtual host to the database.
	 *
	 * @param virtualHostId the primary key for the new virtual host
	 * @return the new virtual host
	 */
	public static com.liferay.portal.kernel.model.VirtualHost createVirtualHost(
		long virtualHostId) {

		return getService().createVirtualHost(virtualHostId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host that was removed
	 * @throws PortalException if a virtual host with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.VirtualHost deleteVirtualHost(
			long virtualHostId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteVirtualHost(virtualHostId);
	}

	/**
	 * Deletes the virtual host from the database. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHost the virtual host
	 * @return the virtual host that was removed
	 */
	public static com.liferay.portal.kernel.model.VirtualHost deleteVirtualHost(
		com.liferay.portal.kernel.model.VirtualHost virtualHost) {

		return getService().deleteVirtualHost(virtualHost);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.VirtualHostModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.VirtualHostModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.portal.kernel.model.VirtualHost fetchVirtualHost(
		long virtualHostId) {

		return getService().fetchVirtualHost(virtualHostId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getVirtualHosts(long, long)}
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.VirtualHost fetchVirtualHost(
		long companyId, long layoutSetId) {

		return getService().fetchVirtualHost(companyId, layoutSetId);
	}

	public static com.liferay.portal.kernel.model.VirtualHost fetchVirtualHost(
		String hostname) {

		return getService().fetchVirtualHost(hostname);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the virtual host with the primary key.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host
	 * @throws PortalException if a virtual host with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.VirtualHost getVirtualHost(
			long virtualHostId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVirtualHost(virtualHostId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getVirtualHosts(long, long)}
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.VirtualHost getVirtualHost(
			long companyId, long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVirtualHost(companyId, layoutSetId);
	}

	public static com.liferay.portal.kernel.model.VirtualHost getVirtualHost(
			String hostname)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVirtualHost(hostname);
	}

	/**
	 * Returns a range of all the virtual hosts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.VirtualHostModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @return the range of virtual hosts
	 */
	public static java.util.List<com.liferay.portal.kernel.model.VirtualHost>
		getVirtualHosts(int start, int end) {

		return getService().getVirtualHosts(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.VirtualHost>
			getVirtualHosts(long companyId, long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVirtualHosts(companyId, layoutSetId);
	}

	/**
	 * Returns the number of virtual hosts.
	 *
	 * @return the number of virtual hosts
	 */
	public static int getVirtualHostsCount() {
		return getService().getVirtualHostsCount();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #updateVirtualHosts(long, long, TreeMap)}
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.VirtualHost updateVirtualHost(
		long companyId, long layoutSetId, String hostname) {

		return getService().updateVirtualHost(companyId, layoutSetId, hostname);
	}

	/**
	 * Updates the virtual host in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHost the virtual host
	 * @return the virtual host that was updated
	 */
	public static com.liferay.portal.kernel.model.VirtualHost updateVirtualHost(
		com.liferay.portal.kernel.model.VirtualHost virtualHost) {

		return getService().updateVirtualHost(virtualHost);
	}

	public static java.util.List<com.liferay.portal.kernel.model.VirtualHost>
		updateVirtualHosts(
			long companyId, long layoutSetId,
			java.util.TreeMap<String, String> hostnames) {

		return getService().updateVirtualHosts(
			companyId, layoutSetId, hostnames);
	}

	public static VirtualHostLocalService getService() {
		if (_service == null) {
			_service = (VirtualHostLocalService)PortalBeanLocatorUtil.locate(
				VirtualHostLocalService.class.getName());
		}

		return _service;
	}

	private static VirtualHostLocalService _service;

}