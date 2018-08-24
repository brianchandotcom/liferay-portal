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

package com.liferay.document.library.opener.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DLOpenerFileEntryReference. This utility wraps
 * {@link com.liferay.document.library.opener.service.impl.DLOpenerFileEntryReferenceLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLOpenerFileEntryReferenceLocalService
 * @see com.liferay.document.library.opener.service.base.DLOpenerFileEntryReferenceLocalServiceBaseImpl
 * @see com.liferay.document.library.opener.service.impl.DLOpenerFileEntryReferenceLocalServiceImpl
 * @generated
 */
@ProviderType
public class DLOpenerFileEntryReferenceLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.document.library.opener.service.impl.DLOpenerFileEntryReferenceLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the dl opener file entry reference to the database. Also notifies the appropriate model listeners.
	*
	* @param dlOpenerFileEntryReference the dl opener file entry reference
	* @return the dl opener file entry reference that was added
	*/
	public static com.liferay.document.library.opener.model.DLOpenerFileEntryReference addDLOpenerFileEntryReference(
		com.liferay.document.library.opener.model.DLOpenerFileEntryReference dlOpenerFileEntryReference) {
		return getService()
				   .addDLOpenerFileEntryReference(dlOpenerFileEntryReference);
	}

	public static com.liferay.document.library.opener.model.DLOpenerFileEntryReference addDLOpenerFileEntryReference(
		long userId, String referenceKey,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry, int type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addDLOpenerFileEntryReference(userId, referenceKey,
			fileEntry, type);
	}

	/**
	* Creates a new dl opener file entry reference with the primary key. Does not add the dl opener file entry reference to the database.
	*
	* @param dlOpenerFileEntryReferenceId the primary key for the new dl opener file entry reference
	* @return the new dl opener file entry reference
	*/
	public static com.liferay.document.library.opener.model.DLOpenerFileEntryReference createDLOpenerFileEntryReference(
		long dlOpenerFileEntryReferenceId) {
		return getService()
				   .createDLOpenerFileEntryReference(dlOpenerFileEntryReferenceId);
	}

	/**
	* Deletes the dl opener file entry reference from the database. Also notifies the appropriate model listeners.
	*
	* @param dlOpenerFileEntryReference the dl opener file entry reference
	* @return the dl opener file entry reference that was removed
	*/
	public static com.liferay.document.library.opener.model.DLOpenerFileEntryReference deleteDLOpenerFileEntryReference(
		com.liferay.document.library.opener.model.DLOpenerFileEntryReference dlOpenerFileEntryReference) {
		return getService()
				   .deleteDLOpenerFileEntryReference(dlOpenerFileEntryReference);
	}

	public static void deleteDLOpenerFileEntryReference(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteDLOpenerFileEntryReference(fileEntry);
	}

	/**
	* Deletes the dl opener file entry reference with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	* @return the dl opener file entry reference that was removed
	* @throws PortalException if a dl opener file entry reference with the primary key could not be found
	*/
	public static com.liferay.document.library.opener.model.DLOpenerFileEntryReference deleteDLOpenerFileEntryReference(
		long dlOpenerFileEntryReferenceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteDLOpenerFileEntryReference(dlOpenerFileEntryReferenceId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.opener.model.impl.DLOpenerFileEntryReferenceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.opener.model.impl.DLOpenerFileEntryReferenceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.document.library.opener.model.DLOpenerFileEntryReference fetchDLOpenerFileEntryReference(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry) {
		return getService().fetchDLOpenerFileEntryReference(fileEntry);
	}

	public static com.liferay.document.library.opener.model.DLOpenerFileEntryReference fetchDLOpenerFileEntryReference(
		long dlOpenerFileEntryReferenceId) {
		return getService()
				   .fetchDLOpenerFileEntryReference(dlOpenerFileEntryReferenceId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.document.library.opener.model.DLOpenerFileEntryReference getDLOpenerFileEntryReference(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDLOpenerFileEntryReference(fileEntry);
	}

	/**
	* Returns the dl opener file entry reference with the primary key.
	*
	* @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	* @return the dl opener file entry reference
	* @throws PortalException if a dl opener file entry reference with the primary key could not be found
	*/
	public static com.liferay.document.library.opener.model.DLOpenerFileEntryReference getDLOpenerFileEntryReference(
		long dlOpenerFileEntryReferenceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getDLOpenerFileEntryReference(dlOpenerFileEntryReferenceId);
	}

	/**
	* Returns a range of all the dl opener file entry references.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.opener.model.impl.DLOpenerFileEntryReferenceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of dl opener file entry references
	* @param end the upper bound of the range of dl opener file entry references (not inclusive)
	* @return the range of dl opener file entry references
	*/
	public static java.util.List<com.liferay.document.library.opener.model.DLOpenerFileEntryReference> getDLOpenerFileEntryReferences(
		int start, int end) {
		return getService().getDLOpenerFileEntryReferences(start, end);
	}

	/**
	* Returns the number of dl opener file entry references.
	*
	* @return the number of dl opener file entry references
	*/
	public static int getDLOpenerFileEntryReferencesCount() {
		return getService().getDLOpenerFileEntryReferencesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
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

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the dl opener file entry reference in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlOpenerFileEntryReference the dl opener file entry reference
	* @return the dl opener file entry reference that was updated
	*/
	public static com.liferay.document.library.opener.model.DLOpenerFileEntryReference updateDLOpenerFileEntryReference(
		com.liferay.document.library.opener.model.DLOpenerFileEntryReference dlOpenerFileEntryReference) {
		return getService()
				   .updateDLOpenerFileEntryReference(dlOpenerFileEntryReference);
	}

	public static DLOpenerFileEntryReferenceLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DLOpenerFileEntryReferenceLocalService, DLOpenerFileEntryReferenceLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DLOpenerFileEntryReferenceLocalService.class);

		ServiceTracker<DLOpenerFileEntryReferenceLocalService, DLOpenerFileEntryReferenceLocalService> serviceTracker =
			new ServiceTracker<DLOpenerFileEntryReferenceLocalService, DLOpenerFileEntryReferenceLocalService>(bundle.getBundleContext(),
				DLOpenerFileEntryReferenceLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}