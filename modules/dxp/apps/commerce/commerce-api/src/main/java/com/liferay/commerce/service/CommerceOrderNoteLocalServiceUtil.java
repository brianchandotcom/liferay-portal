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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceOrderNote. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceOrderNoteLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNoteLocalService
 * @see com.liferay.commerce.service.base.CommerceOrderNoteLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceOrderNoteLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderNoteLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceOrderNoteLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce order note to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderNote the commerce order note
	* @return the commerce order note that was added
	*/
	public static com.liferay.commerce.model.CommerceOrderNote addCommerceOrderNote(
		com.liferay.commerce.model.CommerceOrderNote commerceOrderNote) {
		return getService().addCommerceOrderNote(commerceOrderNote);
	}

	public static com.liferay.commerce.model.CommerceOrderNote addCommerceOrderNote(
		long commerceOrderId, java.lang.String content, boolean restricted,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceOrderNote(commerceOrderId, content, restricted,
			serviceContext);
	}

	/**
	* Creates a new commerce order note with the primary key. Does not add the commerce order note to the database.
	*
	* @param commerceOrderNoteId the primary key for the new commerce order note
	* @return the new commerce order note
	*/
	public static com.liferay.commerce.model.CommerceOrderNote createCommerceOrderNote(
		long commerceOrderNoteId) {
		return getService().createCommerceOrderNote(commerceOrderNoteId);
	}

	/**
	* Deletes the commerce order note from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderNote the commerce order note
	* @return the commerce order note that was removed
	*/
	public static com.liferay.commerce.model.CommerceOrderNote deleteCommerceOrderNote(
		com.liferay.commerce.model.CommerceOrderNote commerceOrderNote) {
		return getService().deleteCommerceOrderNote(commerceOrderNote);
	}

	/**
	* Deletes the commerce order note with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderNoteId the primary key of the commerce order note
	* @return the commerce order note that was removed
	* @throws PortalException if a commerce order note with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceOrderNote deleteCommerceOrderNote(
		long commerceOrderNoteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceOrderNote(commerceOrderNoteId);
	}

	public static void deleteCommerceOrderNotes(long commerceOrderId) {
		getService().deleteCommerceOrderNotes(commerceOrderId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceOrderNote fetchCommerceOrderNote(
		long commerceOrderNoteId) {
		return getService().fetchCommerceOrderNote(commerceOrderNoteId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce order note with the primary key.
	*
	* @param commerceOrderNoteId the primary key of the commerce order note
	* @return the commerce order note
	* @throws PortalException if a commerce order note with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceOrderNote getCommerceOrderNote(
		long commerceOrderNoteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderNote(commerceOrderNoteId);
	}

	/**
	* Returns a range of all the commerce order notes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderNoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order notes
	* @param end the upper bound of the range of commerce order notes (not inclusive)
	* @return the range of commerce order notes
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceOrderNote> getCommerceOrderNotes(
		int start, int end) {
		return getService().getCommerceOrderNotes(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderNote> getCommerceOrderNotes(
		long commerceOrderId, boolean restricted) {
		return getService().getCommerceOrderNotes(commerceOrderId, restricted);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderNote> getCommerceOrderNotes(
		long commerceOrderId, int start, int end) {
		return getService().getCommerceOrderNotes(commerceOrderId, start, end);
	}

	/**
	* Returns the number of commerce order notes.
	*
	* @return the number of commerce order notes
	*/
	public static int getCommerceOrderNotesCount() {
		return getService().getCommerceOrderNotesCount();
	}

	public static int getCommerceOrderNotesCount(long commerceOrderId) {
		return getService().getCommerceOrderNotesCount(commerceOrderId);
	}

	public static int getCommerceOrderNotesCount(long commerceOrderId,
		boolean restricted) {
		return getService()
				   .getCommerceOrderNotesCount(commerceOrderId, restricted);
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
	* Updates the commerce order note in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderNote the commerce order note
	* @return the commerce order note that was updated
	*/
	public static com.liferay.commerce.model.CommerceOrderNote updateCommerceOrderNote(
		com.liferay.commerce.model.CommerceOrderNote commerceOrderNote) {
		return getService().updateCommerceOrderNote(commerceOrderNote);
	}

	public static com.liferay.commerce.model.CommerceOrderNote updateCommerceOrderNote(
		long commerceOrderNoteId, java.lang.String content, boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceOrderNote(commerceOrderNoteId, content,
			restricted);
	}

	public static CommerceOrderNoteLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderNoteLocalService, CommerceOrderNoteLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceOrderNoteLocalService.class);

		ServiceTracker<CommerceOrderNoteLocalService, CommerceOrderNoteLocalService> serviceTracker =
			new ServiceTracker<CommerceOrderNoteLocalService, CommerceOrderNoteLocalService>(bundle.getBundleContext(),
				CommerceOrderNoteLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}