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

package com.liferay.change.tracking.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CTEntryBag. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTEntryBagLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryBagLocalService
 * @generated
 */
@ProviderType
public class CTEntryBagLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTEntryBagLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addCTEntry(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		getService().addCTEntry(ctEntryBag, ctEntry);
	}

	/**
	* Adds the ct entry bag to the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBag the ct entry bag
	* @return the ct entry bag that was added
	*/
	public static com.liferay.change.tracking.model.CTEntryBag addCTEntryBag(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag) {
		return getService().addCTEntryBag(ctEntryBag);
	}

	public static void addCTEntryCTEntryBag(long ctEntryId,
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag) {
		getService().addCTEntryCTEntryBag(ctEntryId, ctEntryBag);
	}

	public static void addCTEntryCTEntryBag(long ctEntryId, long ctEntryBagId) {
		getService().addCTEntryCTEntryBag(ctEntryId, ctEntryBagId);
	}

	public static void addCTEntryCTEntryBags(long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTEntryBag> ctEntryBags) {
		getService().addCTEntryCTEntryBags(ctEntryId, ctEntryBags);
	}

	public static void addCTEntryCTEntryBags(long ctEntryId,
		long[] ctEntryBagIds) {
		getService().addCTEntryCTEntryBags(ctEntryId, ctEntryBagIds);
	}

	public static void clearCTEntryCTEntryBags(long ctEntryId) {
		getService().clearCTEntryCTEntryBags(ctEntryId);
	}

	/**
	* Creates a new ct entry bag with the primary key. Does not add the ct entry bag to the database.
	*
	* @param ctEntryBagId the primary key for the new ct entry bag
	* @return the new ct entry bag
	*/
	public static com.liferay.change.tracking.model.CTEntryBag createCTEntryBag(
		long ctEntryBagId) {
		return getService().createCTEntryBag(ctEntryBagId);
	}

	public static com.liferay.change.tracking.model.CTEntryBag createCTEntryBag(
		long userId, long ownerCTEntryId, long ctCollectionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .createCTEntryBag(userId, ownerCTEntryId, ctCollectionId,
			serviceContext);
	}

	/**
	* Deletes the ct entry bag from the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBag the ct entry bag
	* @return the ct entry bag that was removed
	*/
	public static com.liferay.change.tracking.model.CTEntryBag deleteCTEntryBag(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag) {
		return getService().deleteCTEntryBag(ctEntryBag);
	}

	/**
	* Deletes the ct entry bag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag that was removed
	* @throws PortalException if a ct entry bag with the primary key could not be found
	*/
	public static com.liferay.change.tracking.model.CTEntryBag deleteCTEntryBag(
		long ctEntryBagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCTEntryBag(ctEntryBagId);
	}

	public static void deleteCTEntryCTEntryBag(long ctEntryId,
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag) {
		getService().deleteCTEntryCTEntryBag(ctEntryId, ctEntryBag);
	}

	public static void deleteCTEntryCTEntryBag(long ctEntryId, long ctEntryBagId) {
		getService().deleteCTEntryCTEntryBag(ctEntryId, ctEntryBagId);
	}

	public static void deleteCTEntryCTEntryBags(long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTEntryBag> ctEntryBags) {
		getService().deleteCTEntryCTEntryBags(ctEntryId, ctEntryBags);
	}

	public static void deleteCTEntryCTEntryBags(long ctEntryId,
		long[] ctEntryBagIds) {
		getService().deleteCTEntryCTEntryBags(ctEntryId, ctEntryBagIds);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.change.tracking.model.CTEntryBag fetchCTEntryBag(
		long ctEntryBagId) {
		return getService().fetchCTEntryBag(ctEntryBagId);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTEntryBag> fetchCTEntryBags(
		long ownerCTEntryId, long ctCollectionId) {
		return getService().fetchCTEntryBags(ownerCTEntryId, ctCollectionId);
	}

	public static com.liferay.change.tracking.model.CTEntryBag fetchLatestCTEntryBag(
		long ownerCTEntryId, long ctCollectionId) {
		return getService().fetchLatestCTEntryBag(ownerCTEntryId, ctCollectionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the ct entry bag with the primary key.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag
	* @throws PortalException if a ct entry bag with the primary key could not be found
	*/
	public static com.liferay.change.tracking.model.CTEntryBag getCTEntryBag(
		long ctEntryBagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCTEntryBag(ctEntryBagId);
	}

	/**
	* Returns a range of all the ct entry bags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @return the range of ct entry bags
	*/
	public static java.util.List<com.liferay.change.tracking.model.CTEntryBag> getCTEntryBags(
		int start, int end) {
		return getService().getCTEntryBags(start, end);
	}

	/**
	* Returns the number of ct entry bags.
	*
	* @return the number of ct entry bags
	*/
	public static int getCTEntryBagsCount() {
		return getService().getCTEntryBagsCount();
	}

	public static java.util.List<com.liferay.change.tracking.model.CTEntryBag> getCTEntryCTEntryBags(
		long ctEntryId) {
		return getService().getCTEntryCTEntryBags(ctEntryId);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTEntryBag> getCTEntryCTEntryBags(
		long ctEntryId, int start, int end) {
		return getService().getCTEntryCTEntryBags(ctEntryId, start, end);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTEntryBag> getCTEntryCTEntryBags(
		long ctEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.CTEntryBag> orderByComparator) {
		return getService()
				   .getCTEntryCTEntryBags(ctEntryId, start, end,
			orderByComparator);
	}

	public static int getCTEntryCTEntryBagsCount(long ctEntryId) {
		return getService().getCTEntryCTEntryBagsCount(ctEntryId);
	}

	/**
	* Returns the ctEntryIds of the ct entries associated with the ct entry bag.
	*
	* @param ctEntryBagId the ctEntryBagId of the ct entry bag
	* @return long[] the ctEntryIds of ct entries associated with the ct entry bag
	*/
	public static long[] getCTEntryPrimaryKeys(long ctEntryBagId) {
		return getService().getCTEntryPrimaryKeys(ctEntryBagId);
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

	public static boolean hasCTEntry(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		return getService().hasCTEntry(ctEntryBag, ctEntry);
	}

	public static boolean hasCTEntryCTEntryBag(long ctEntryId, long ctEntryBagId) {
		return getService().hasCTEntryCTEntryBag(ctEntryId, ctEntryBagId);
	}

	public static boolean hasCTEntryCTEntryBags(long ctEntryId) {
		return getService().hasCTEntryCTEntryBags(ctEntryId);
	}

	public static void removeCTEntry(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		getService().removeCTEntry(ctEntryBag, ctEntry);
	}

	public static void setCTEntryCTEntryBags(long ctEntryId,
		long[] ctEntryBagIds) {
		getService().setCTEntryCTEntryBags(ctEntryId, ctEntryBagIds);
	}

	/**
	* Updates the ct entry bag in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBag the ct entry bag
	* @return the ct entry bag that was updated
	*/
	public static com.liferay.change.tracking.model.CTEntryBag updateCTEntryBag(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag) {
		return getService().updateCTEntryBag(ctEntryBag);
	}

	public static CTEntryBagLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTEntryBagLocalService, CTEntryBagLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTEntryBagLocalService.class);

		ServiceTracker<CTEntryBagLocalService, CTEntryBagLocalService> serviceTracker =
			new ServiceTracker<CTEntryBagLocalService, CTEntryBagLocalService>(bundle.getBundleContext(),
				CTEntryBagLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}