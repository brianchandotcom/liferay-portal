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
 * Provides the local service utility for DDMStorageLink. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMStorageLinkLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLinkLocalService
 * @generated
 */
public class DDMStorageLinkLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMStorageLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ddm storage link to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStorageLink the ddm storage link
	 * @return the ddm storage link that was added
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
		addDDMStorageLink(
			com.liferay.dynamic.data.mapping.model.DDMStorageLink
				ddmStorageLink) {

		return getService().addDDMStorageLink(ddmStorageLink);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
		addStorageLink(
			long classNameId, long classPK, long structureVersionId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return getService().addStorageLink(
			classNameId, classPK, structureVersionId, serviceContext);
	}

	/**
	 * Creates a new ddm storage link with the primary key. Does not add the ddm storage link to the database.
	 *
	 * @param storageLinkId the primary key for the new ddm storage link
	 * @return the new ddm storage link
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
		createDDMStorageLink(long storageLinkId) {

		return getService().createDDMStorageLink(storageLinkId);
	}

	public static void deleteClassStorageLink(long classPK) {
		getService().deleteClassStorageLink(classPK);
	}

	/**
	 * Deletes the ddm storage link from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStorageLink the ddm storage link
	 * @return the ddm storage link that was removed
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
		deleteDDMStorageLink(
			com.liferay.dynamic.data.mapping.model.DDMStorageLink
				ddmStorageLink) {

		return getService().deleteDDMStorageLink(ddmStorageLink);
	}

	/**
	 * Deletes the ddm storage link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param storageLinkId the primary key of the ddm storage link
	 * @return the ddm storage link that was removed
	 * @throws PortalException if a ddm storage link with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
			deleteDDMStorageLink(long storageLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDDMStorageLink(storageLinkId);
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

	public static void deleteStorageLink(
		com.liferay.dynamic.data.mapping.model.DDMStorageLink storageLink) {

		getService().deleteStorageLink(storageLink);
	}

	public static void deleteStorageLink(long storageLinkId) {
		getService().deleteStorageLink(storageLinkId);
	}

	public static void deleteStructureStorageLinks(long structureId) {
		getService().deleteStructureStorageLinks(structureId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl</code>.
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

	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
		fetchDDMStorageLink(long storageLinkId) {

		return getService().fetchDDMStorageLink(storageLinkId);
	}

	/**
	 * Returns the ddm storage link with the matching UUID and company.
	 *
	 * @param uuid the ddm storage link's UUID
	 * @param companyId the primary key of the company
	 * @return the matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
		fetchDDMStorageLinkByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchDDMStorageLinkByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
			getClassStorageLink(long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getClassStorageLink(classPK);
	}

	/**
	 * Returns the ddm storage link with the primary key.
	 *
	 * @param storageLinkId the primary key of the ddm storage link
	 * @return the ddm storage link
	 * @throws PortalException if a ddm storage link with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
			getDDMStorageLink(long storageLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDMStorageLink(storageLinkId);
	}

	/**
	 * Returns the ddm storage link with the matching UUID and company.
	 *
	 * @param uuid the ddm storage link's UUID
	 * @param companyId the primary key of the company
	 * @return the matching ddm storage link
	 * @throws PortalException if a matching ddm storage link could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
			getDDMStorageLinkByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDMStorageLinkByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the ddm storage links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @return the range of ddm storage links
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStorageLink>
			getDDMStorageLinks(int start, int end) {

		return getService().getDDMStorageLinks(start, end);
	}

	/**
	 * Returns the number of ddm storage links.
	 *
	 * @return the number of ddm storage links
	 */
	public static int getDDMStorageLinksCount() {
		return getService().getDDMStorageLinksCount();
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

	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
			getStorageLink(long storageLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getStorageLink(storageLinkId);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStorageLink>
			getStructureStorageLinks(long structureId) {

		return getService().getStructureStorageLinks(structureId);
	}

	public static int getStructureStorageLinksCount(long structureId) {
		return getService().getStructureStorageLinksCount(structureId);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStorageLink>
			getStructureVersionStorageLinks(long structureVersionId) {

		return getService().getStructureVersionStorageLinks(structureVersionId);
	}

	public static int getStructureVersionStorageLinksCount(
		long structureVersionId) {

		return getService().getStructureVersionStorageLinksCount(
			structureVersionId);
	}

	/**
	 * Updates the ddm storage link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStorageLink the ddm storage link
	 * @return the ddm storage link that was updated
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
		updateDDMStorageLink(
			com.liferay.dynamic.data.mapping.model.DDMStorageLink
				ddmStorageLink) {

		return getService().updateDDMStorageLink(ddmStorageLink);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStorageLink
			updateStorageLink(
				long storageLinkId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStorageLink(
			storageLinkId, classNameId, classPK);
	}

	public static DDMStorageLinkLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMStorageLinkLocalService, DDMStorageLinkLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMStorageLinkLocalService.class);

		ServiceTracker<DDMStorageLinkLocalService, DDMStorageLinkLocalService>
			serviceTracker =
				new ServiceTracker
					<DDMStorageLinkLocalService, DDMStorageLinkLocalService>(
						bundle.getBundleContext(),
						DDMStorageLinkLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}