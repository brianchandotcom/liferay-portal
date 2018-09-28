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

package com.liferay.meris.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for MerisSegment. This utility wraps
 * {@link com.liferay.meris.service.impl.MerisSegmentLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Eduardo Garcia
 * @see MerisSegmentLocalService
 * @see com.liferay.meris.service.base.MerisSegmentLocalServiceBaseImpl
 * @see com.liferay.meris.service.impl.MerisSegmentLocalServiceImpl
 * @generated
 */
@ProviderType
public class MerisSegmentLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.meris.service.impl.MerisSegmentLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the meris segment to the database. Also notifies the appropriate model listeners.
	*
	* @param merisSegment the meris segment
	* @return the meris segment that was added
	*/
	public static com.liferay.meris.model.MerisSegment addMerisSegment(
		com.liferay.meris.model.MerisSegment merisSegment) {
		return getService().addMerisSegment(merisSegment);
	}

	/**
	* Creates a new meris segment with the primary key. Does not add the meris segment to the database.
	*
	* @param merisSegmentId the primary key for the new meris segment
	* @return the new meris segment
	*/
	public static com.liferay.meris.model.MerisSegment createMerisSegment(
		long merisSegmentId) {
		return getService().createMerisSegment(merisSegmentId);
	}

	/**
	* Deletes the meris segment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param merisSegmentId the primary key of the meris segment
	* @return the meris segment that was removed
	* @throws PortalException if a meris segment with the primary key could not be found
	*/
	public static com.liferay.meris.model.MerisSegment deleteMerisSegment(
		long merisSegmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMerisSegment(merisSegmentId);
	}

	/**
	* Deletes the meris segment from the database. Also notifies the appropriate model listeners.
	*
	* @param merisSegment the meris segment
	* @return the meris segment that was removed
	*/
	public static com.liferay.meris.model.MerisSegment deleteMerisSegment(
		com.liferay.meris.model.MerisSegment merisSegment) {
		return getService().deleteMerisSegment(merisSegment);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.meris.model.impl.MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.meris.model.impl.MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.meris.model.MerisSegment fetchMerisSegment(
		long merisSegmentId) {
		return getService().fetchMerisSegment(merisSegmentId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the meris segment with the primary key.
	*
	* @param merisSegmentId the primary key of the meris segment
	* @return the meris segment
	* @throws PortalException if a meris segment with the primary key could not be found
	*/
	public static com.liferay.meris.model.MerisSegment getMerisSegment(
		long merisSegmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMerisSegment(merisSegmentId);
	}

	/**
	* Returns a range of all the meris segments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.meris.model.impl.MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @return the range of meris segments
	*/
	public static java.util.List<com.liferay.meris.model.MerisSegment> getMerisSegments(
		int start, int end) {
		return getService().getMerisSegments(start, end);
	}

	/**
	* Returns the number of meris segments.
	*
	* @return the number of meris segments
	*/
	public static int getMerisSegmentsCount() {
		return getService().getMerisSegmentsCount();
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
	* Updates the meris segment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param merisSegment the meris segment
	* @return the meris segment that was updated
	*/
	public static com.liferay.meris.model.MerisSegment updateMerisSegment(
		com.liferay.meris.model.MerisSegment merisSegment) {
		return getService().updateMerisSegment(merisSegment);
	}

	public static MerisSegmentLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MerisSegmentLocalService, MerisSegmentLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MerisSegmentLocalService.class);

		ServiceTracker<MerisSegmentLocalService, MerisSegmentLocalService> serviceTracker =
			new ServiceTracker<MerisSegmentLocalService, MerisSegmentLocalService>(bundle.getBundleContext(),
				MerisSegmentLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}