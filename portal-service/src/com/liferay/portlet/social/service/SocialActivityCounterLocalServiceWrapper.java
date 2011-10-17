/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SocialActivityCounterLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityCounterLocalService
 * @generated
 */
public class SocialActivityCounterLocalServiceWrapper
	implements SocialActivityCounterLocalService,
		ServiceWrapper<SocialActivityCounterLocalService> {
	public SocialActivityCounterLocalServiceWrapper(
		SocialActivityCounterLocalService socialActivityCounterLocalService) {
		_socialActivityCounterLocalService = socialActivityCounterLocalService;
	}

	/**
	* Adds the social activity counter to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @return the social activity counter that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter addSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.addSocialActivityCounter(socialActivityCounter);
	}

	/**
	* Creates a new social activity counter with the primary key. Does not add the social activity counter to the database.
	*
	* @param activityCounterId the primary key for the new social activity counter
	* @return the new social activity counter
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter createSocialActivityCounter(
		long activityCounterId) {
		return _socialActivityCounterLocalService.createSocialActivityCounter(activityCounterId);
	}

	/**
	* Deletes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @throws PortalException if a social activity counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialActivityCounter(long activityCounterId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.deleteSocialActivityCounter(activityCounterId);
	}

	/**
	* Deletes the social activity counter from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.deleteSocialActivityCounter(socialActivityCounter);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the social activity counter with the primary key.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter
	* @throws PortalException if a social activity counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter getSocialActivityCounter(
		long activityCounterId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getSocialActivityCounter(activityCounterId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getSocialActivityCounters(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getSocialActivityCounters(start,
			end);
	}

	/**
	* Returns the number of social activity counters.
	*
	* @return the number of social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialActivityCountersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getSocialActivityCountersCount();
	}

	/**
	* Updates the social activity counter in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @return the social activity counter that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter updateSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.updateSocialActivityCounter(socialActivityCounter);
	}

	/**
	* Updates the social activity counter in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @param merge whether to merge the social activity counter with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social activity counter that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter updateSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.updateSocialActivityCounter(socialActivityCounter,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialActivityCounterLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialActivityCounterLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SocialActivityCounterLocalService getWrappedSocialActivityCounterLocalService() {
		return _socialActivityCounterLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSocialActivityCounterLocalService(
		SocialActivityCounterLocalService socialActivityCounterLocalService) {
		_socialActivityCounterLocalService = socialActivityCounterLocalService;
	}

	public SocialActivityCounterLocalService getWrappedService() {
		return _socialActivityCounterLocalService;
	}

	public void setWrappedService(
		SocialActivityCounterLocalService socialActivityCounterLocalService) {
		_socialActivityCounterLocalService = socialActivityCounterLocalService;
	}

	private SocialActivityCounterLocalService _socialActivityCounterLocalService;
}