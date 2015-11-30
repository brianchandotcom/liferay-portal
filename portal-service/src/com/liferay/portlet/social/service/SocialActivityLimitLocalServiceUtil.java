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

package com.liferay.portlet.social.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for SocialActivityLimit. This utility wraps
 * {@link com.liferay.portlet.social.service.impl.SocialActivityLimitLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimitLocalService
 * @see com.liferay.portlet.social.service.base.SocialActivityLimitLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialActivityLimitLocalServiceImpl
 * @generated
 */
@ProviderType
public class SocialActivityLimitLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialActivityLimitLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.social.model.SocialActivityLimit addActivityLimit(
		long userId, long groupId, long classNameId, long classPK,
		int activityType, java.lang.String activityCounterName, int limitPeriod)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addActivityLimit(userId, groupId, classNameId, classPK,
			activityType, activityCounterName, limitPeriod);
	}

	/**
	* Adds the social activity limit to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityLimit the social activity limit
	* @return the social activity limit that was added
	*/
	public static com.liferay.portlet.social.model.SocialActivityLimit addSocialActivityLimit(
		com.liferay.portlet.social.model.SocialActivityLimit socialActivityLimit) {
		return getService().addSocialActivityLimit(socialActivityLimit);
	}

	/**
	* Creates a new social activity limit with the primary key. Does not add the social activity limit to the database.
	*
	* @param activityLimitId the primary key for the new social activity limit
	* @return the new social activity limit
	*/
	public static com.liferay.portlet.social.model.SocialActivityLimit createSocialActivityLimit(
		long activityLimitId) {
		return getService().createSocialActivityLimit(activityLimitId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the social activity limit with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityLimitId the primary key of the social activity limit
	* @return the social activity limit that was removed
	* @throws PortalException if a social activity limit with the primary key could not be found
	*/
	public static com.liferay.portlet.social.model.SocialActivityLimit deleteSocialActivityLimit(
		long activityLimitId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSocialActivityLimit(activityLimitId);
	}

	/**
	* Deletes the social activity limit from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityLimit the social activity limit
	* @return the social activity limit that was removed
	*/
	public static com.liferay.portlet.social.model.SocialActivityLimit deleteSocialActivityLimit(
		com.liferay.portlet.social.model.SocialActivityLimit socialActivityLimit) {
		return getService().deleteSocialActivityLimit(socialActivityLimit);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.social.model.SocialActivityLimit fetchActivityLimit(
		long groupId, long userId, long classNameId, long classPK,
		int activityType, java.lang.String activityCounterName) {
		return getService()
				   .fetchActivityLimit(groupId, userId, classNameId, classPK,
			activityType, activityCounterName);
	}

	public static com.liferay.portlet.social.model.SocialActivityLimit fetchSocialActivityLimit(
		long activityLimitId) {
		return getService().fetchSocialActivityLimit(activityLimitId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
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

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the social activity limit with the primary key.
	*
	* @param activityLimitId the primary key of the social activity limit
	* @return the social activity limit
	* @throws PortalException if a social activity limit with the primary key could not be found
	*/
	public static com.liferay.portlet.social.model.SocialActivityLimit getSocialActivityLimit(
		long activityLimitId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSocialActivityLimit(activityLimitId);
	}

	/**
	* Returns a range of all the social activity limits.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @return the range of social activity limits
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityLimit> getSocialActivityLimits(
		int start, int end) {
		return getService().getSocialActivityLimits(start, end);
	}

	/**
	* Returns the number of social activity limits.
	*
	* @return the number of social activity limits
	*/
	public static int getSocialActivityLimitsCount() {
		return getService().getSocialActivityLimitsCount();
	}

	/**
	* Updates the social activity limit in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivityLimit the social activity limit
	* @return the social activity limit that was updated
	*/
	public static com.liferay.portlet.social.model.SocialActivityLimit updateSocialActivityLimit(
		com.liferay.portlet.social.model.SocialActivityLimit socialActivityLimit) {
		return getService().updateSocialActivityLimit(socialActivityLimit);
	}

	public static SocialActivityLimitLocalService getService() {
		if (_service == null) {
			_service = (SocialActivityLimitLocalService)PortalBeanLocatorUtil.locate(SocialActivityLimitLocalService.class.getName());

			ReferenceRegistry.registerReference(SocialActivityLimitLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(SocialActivityLimitLocalService service) {
	}

	private static SocialActivityLimitLocalService _service;
}