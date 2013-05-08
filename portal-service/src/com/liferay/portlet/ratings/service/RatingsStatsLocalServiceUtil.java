/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for RatingsStats. This utility wraps
 * {@link com.liferay.portlet.ratings.service.impl.RatingsStatsLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsStatsLocalService
 * @see com.liferay.portlet.ratings.service.base.RatingsStatsLocalServiceBaseImpl
 * @see com.liferay.portlet.ratings.service.impl.RatingsStatsLocalServiceImpl
 * @generated
 */
public class RatingsStatsLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.ratings.service.impl.RatingsStatsLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the ratings stats to the database. Also notifies the appropriate model listeners.
	*
	* @param ratingsStats the ratings stats
	* @return the ratings stats that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsStats addRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addRatingsStats(ratingsStats);
	}

	/**
	* Creates a new ratings stats with the primary key. Does not add the ratings stats to the database.
	*
	* @param statsId the primary key for the new ratings stats
	* @return the new ratings stats
	*/
	public static com.liferay.portlet.ratings.model.RatingsStats createRatingsStats(
		long statsId) {
		return getService().createRatingsStats(statsId);
	}

	/**
	* Deletes the ratings stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsId the primary key of the ratings stats
	* @return the ratings stats that was removed
	* @throws PortalException if a ratings stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsStats deleteRatingsStats(
		long statsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteRatingsStats(statsId);
	}

	/**
	* Deletes the ratings stats from the database. Also notifies the appropriate model listeners.
	*
	* @param ratingsStats the ratings stats
	* @return the ratings stats that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsStats deleteRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteRatingsStats(ratingsStats);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats fetchRatingsStats(
		long statsId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchRatingsStats(statsId);
	}

	/**
	* Returns the ratings stats with the primary key.
	*
	* @param statsId the primary key of the ratings stats
	* @return the ratings stats
	* @throws PortalException if a ratings stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsStats getRatingsStats(
		long statsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRatingsStats(statsId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the ratings statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ratings statses
	* @param end the upper bound of the range of ratings statses (not inclusive)
	* @return the range of ratings statses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsStats> getRatingsStatses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRatingsStatses(start, end);
	}

	/**
	* Returns the number of ratings statses.
	*
	* @return the number of ratings statses
	* @throws SystemException if a system exception occurred
	*/
	public static int getRatingsStatsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRatingsStatsesCount();
	}

	/**
	* Updates the ratings stats in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ratingsStats the ratings stats
	* @return the ratings stats that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.ratings.model.RatingsStats updateRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRatingsStats(ratingsStats);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats addStats(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addStats(classNameId, classPK);
	}

	public static void deleteStats(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStats(className, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats getStats(
		long statsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStats(statsId);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsStats> getStats(
		java.lang.String className, java.util.List<java.lang.Long> classPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStats(className, classPKs);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats getStats(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStats(className, classPK);
	}

	public static RatingsStatsLocalService getService() {
		if (_service == null) {
			_service = (RatingsStatsLocalService)PortalBeanLocatorUtil.locate(RatingsStatsLocalService.class.getName());

			ReferenceRegistry.registerReference(RatingsStatsLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(RatingsStatsLocalService service) {
	}

	private static RatingsStatsLocalService _service;
}