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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDMDataProviderInstanceLink. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceLinkLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLinkLocalService
 * @generated
 */
public class DDMDataProviderInstanceLinkLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMDataProviderInstanceLinkLocalServiceUtil} to access the ddm data provider instance link local service. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceLinkLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink
			addDataProviderInstanceLink(
				long dataProviderInstanceId, long structureId) {

		return getService().addDataProviderInstanceLink(
			dataProviderInstanceId, structureId);
	}

	/**
	 * Adds the ddm data provider instance link to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmDataProviderInstanceLink the ddm data provider instance link
	 * @return the ddm data provider instance link that was added
	 */
	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink
			addDDMDataProviderInstanceLink(
				com.liferay.dynamic.data.mapping.model.
					DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		return getService().addDDMDataProviderInstanceLink(
			ddmDataProviderInstanceLink);
	}

	/**
	 * Creates a new ddm data provider instance link with the primary key. Does not add the ddm data provider instance link to the database.
	 *
	 * @param dataProviderInstanceLinkId the primary key for the new ddm data provider instance link
	 * @return the new ddm data provider instance link
	 */
	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink
			createDDMDataProviderInstanceLink(long dataProviderInstanceLinkId) {

		return getService().createDDMDataProviderInstanceLink(
			dataProviderInstanceLinkId);
	}

	public static void deleteDataProviderInstanceLink(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink
			dataProviderInstanceLink) {

		getService().deleteDataProviderInstanceLink(dataProviderInstanceLink);
	}

	public static void deleteDataProviderInstanceLink(
			long dataProviderInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteDataProviderInstanceLink(dataProviderInstanceLinkId);
	}

	public static void deleteDataProviderInstanceLink(
			long dataProviderInstanceId, long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteDataProviderInstanceLink(
			dataProviderInstanceId, structureId);
	}

	public static void deleteDataProviderInstanceLinks(long structureId) {
		getService().deleteDataProviderInstanceLinks(structureId);
	}

	/**
	 * Deletes the ddm data provider instance link from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmDataProviderInstanceLink the ddm data provider instance link
	 * @return the ddm data provider instance link that was removed
	 */
	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink
			deleteDDMDataProviderInstanceLink(
				com.liferay.dynamic.data.mapping.model.
					DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		return getService().deleteDDMDataProviderInstanceLink(
			ddmDataProviderInstanceLink);
	}

	/**
	 * Deletes the ddm data provider instance link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link that was removed
	 * @throws PortalException if a ddm data provider instance link with the primary key could not be found
	 */
	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink
				deleteDDMDataProviderInstanceLink(
					long dataProviderInstanceLinkId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDDMDataProviderInstanceLink(
			dataProviderInstanceLinkId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkModelImpl</code>.
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

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink
			fetchDataProviderInstanceLink(
				long dataProviderInstanceId, long structureId) {

		return getService().fetchDataProviderInstanceLink(
			dataProviderInstanceId, structureId);
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink
			fetchDDMDataProviderInstanceLink(long dataProviderInstanceLinkId) {

		return getService().fetchDDMDataProviderInstanceLink(
			dataProviderInstanceLinkId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink>
			getDataProviderInstanceLinks(long structureId) {

		return getService().getDataProviderInstanceLinks(structureId);
	}

	/**
	 * Returns the ddm data provider instance link with the primary key.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link
	 * @throws PortalException if a ddm data provider instance link with the primary key could not be found
	 */
	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink
				getDDMDataProviderInstanceLink(long dataProviderInstanceLinkId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDMDataProviderInstanceLink(
			dataProviderInstanceLinkId);
	}

	/**
	 * Returns a range of all the ddm data provider instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @return the range of ddm data provider instance links
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink>
			getDDMDataProviderInstanceLinks(int start, int end) {

		return getService().getDDMDataProviderInstanceLinks(start, end);
	}

	/**
	 * Returns the number of ddm data provider instance links.
	 *
	 * @return the number of ddm data provider instance links
	 */
	public static int getDDMDataProviderInstanceLinksCount() {
		return getService().getDDMDataProviderInstanceLinksCount();
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
	 * Updates the ddm data provider instance link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmDataProviderInstanceLink the ddm data provider instance link
	 * @return the ddm data provider instance link that was updated
	 */
	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink
			updateDDMDataProviderInstanceLink(
				com.liferay.dynamic.data.mapping.model.
					DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		return getService().updateDDMDataProviderInstanceLink(
			ddmDataProviderInstanceLink);
	}

	public static DDMDataProviderInstanceLinkLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMDataProviderInstanceLinkLocalService,
		 DDMDataProviderInstanceLinkLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMDataProviderInstanceLinkLocalService.class);

		ServiceTracker
			<DDMDataProviderInstanceLinkLocalService,
			 DDMDataProviderInstanceLinkLocalService> serviceTracker =
				new ServiceTracker
					<DDMDataProviderInstanceLinkLocalService,
					 DDMDataProviderInstanceLinkLocalService>(
						 bundle.getBundleContext(),
						 DDMDataProviderInstanceLinkLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}