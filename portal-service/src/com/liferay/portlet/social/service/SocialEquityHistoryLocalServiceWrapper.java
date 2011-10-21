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
 * This class is a wrapper for {@link SocialEquityHistoryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityHistoryLocalService
 * @generated
 */
public class SocialEquityHistoryLocalServiceWrapper
	implements SocialEquityHistoryLocalService,
		ServiceWrapper<SocialEquityHistoryLocalService> {
	public SocialEquityHistoryLocalServiceWrapper(
		SocialEquityHistoryLocalService socialEquityHistoryLocalService) {
		_socialEquityHistoryLocalService = socialEquityHistoryLocalService;
	}

	/**
	* Adds the social equity history to the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityHistory the social equity history
	* @return the social equity history that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityHistory addSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.addSocialEquityHistory(socialEquityHistory);
	}

	/**
	* Creates a new social equity history with the primary key. Does not add the social equity history to the database.
	*
	* @param equityHistoryId the primary key for the new social equity history
	* @return the new social equity history
	*/
	public com.liferay.portlet.social.model.SocialEquityHistory createSocialEquityHistory(
		long equityHistoryId) {
		return _socialEquityHistoryLocalService.createSocialEquityHistory(equityHistoryId);
	}

	/**
	* Deletes the social equity history with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityHistoryId the primary key of the social equity history
	* @throws PortalException if a social equity history with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialEquityHistory(long equityHistoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityHistoryLocalService.deleteSocialEquityHistory(equityHistoryId);
	}

	/**
	* Deletes the social equity history from the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityHistory the social equity history
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityHistoryLocalService.deleteSocialEquityHistory(socialEquityHistory);
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
		return _socialEquityHistoryLocalService.dynamicQuery(dynamicQuery);
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
		return _socialEquityHistoryLocalService.dynamicQuery(dynamicQuery,
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
		return _socialEquityHistoryLocalService.dynamicQuery(dynamicQuery,
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
		return _socialEquityHistoryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the social equity history with the primary key.
	*
	* @param equityHistoryId the primary key of the social equity history
	* @return the social equity history
	* @throws PortalException if a social equity history with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityHistory getSocialEquityHistory(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.getSocialEquityHistory(equityHistoryId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the social equity histories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity histories
	* @param end the upper bound of the range of social equity histories (not inclusive)
	* @return the range of social equity histories
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> getSocialEquityHistories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.getSocialEquityHistories(start,
			end);
	}

	/**
	* Returns the number of social equity histories.
	*
	* @return the number of social equity histories
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialEquityHistoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.getSocialEquityHistoriesCount();
	}

	/**
	* Updates the social equity history in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityHistory the social equity history
	* @return the social equity history that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityHistory updateSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.updateSocialEquityHistory(socialEquityHistory);
	}

	/**
	* Updates the social equity history in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityHistory the social equity history
	* @param merge whether to merge the social equity history with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social equity history that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityHistory updateSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.updateSocialEquityHistory(socialEquityHistory,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialEquityHistoryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialEquityHistoryLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SocialEquityHistoryLocalService getWrappedSocialEquityHistoryLocalService() {
		return _socialEquityHistoryLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSocialEquityHistoryLocalService(
		SocialEquityHistoryLocalService socialEquityHistoryLocalService) {
		_socialEquityHistoryLocalService = socialEquityHistoryLocalService;
	}

	public SocialEquityHistoryLocalService getWrappedService() {
		return _socialEquityHistoryLocalService;
	}

	public void setWrappedService(
		SocialEquityHistoryLocalService socialEquityHistoryLocalService) {
		_socialEquityHistoryLocalService = socialEquityHistoryLocalService;
	}

	private SocialEquityHistoryLocalService _socialEquityHistoryLocalService;
}