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

package com.liferay.portlet.social.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SocialRequestLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequestLocalService
 * @generated
 */
public class SocialRequestLocalServiceWrapper
	implements SocialRequestLocalService,
		ServiceWrapper<SocialRequestLocalService> {
	public SocialRequestLocalServiceWrapper(
		SocialRequestLocalService socialRequestLocalService) {
		_socialRequestLocalService = socialRequestLocalService;
	}

	/**
	* Adds the social request to the database. Also notifies the appropriate model listeners.
	*
	* @param socialRequest the social request
	* @return the social request that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialRequest addSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.addSocialRequest(socialRequest);
	}

	/**
	* Creates a new social request with the primary key. Does not add the social request to the database.
	*
	* @param requestId the primary key for the new social request
	* @return the new social request
	*/
	@Override
	public com.liferay.portlet.social.model.SocialRequest createSocialRequest(
		long requestId) {
		return _socialRequestLocalService.createSocialRequest(requestId);
	}

	/**
	* Deletes the social request with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param requestId the primary key of the social request
	* @return the social request that was removed
	* @throws PortalException if a social request with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialRequest deleteSocialRequest(
		long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.deleteSocialRequest(requestId);
	}

	/**
	* Deletes the social request from the database. Also notifies the appropriate model listeners.
	*
	* @param socialRequest the social request
	* @return the social request that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialRequest deleteSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.deleteSocialRequest(socialRequest);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _socialRequestLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public com.liferay.portlet.social.model.SocialRequest fetchSocialRequest(
		long requestId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.fetchSocialRequest(requestId);
	}

	/**
	* Returns the social request with the primary key.
	*
	* @param requestId the primary key of the social request
	* @return the social request
	* @throws PortalException if a social request with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialRequest getSocialRequest(
		long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequest(requestId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the social request matching the UUID and group.
	*
	* @param uuid the social request's UUID
	* @param groupId the primary key of the group
	* @return the matching social request
	* @throws PortalException if a matching social request could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialRequest getSocialRequestByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequestByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the social requests.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of social requests
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getSocialRequests(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequests(start, end);
	}

	/**
	* Returns the number of social requests.
	*
	* @return the number of social requests
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getSocialRequestsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequestsCount();
	}

	/**
	* Updates the social request in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialRequest the social request
	* @return the social request that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialRequest updateSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.updateSocialRequest(socialRequest);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _socialRequestLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialRequestLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds a social request to the database.
	*
	* <p>
	* In order to add a social request, both the requesting user and the
	* receiving user must be from the same company and neither of them can be
	* the default user.
	* </p>
	*
	* @param userId the primary key of the requesting user
	* @param groupId the primary key of the group
	* @param className the class name of the asset that is the subject of the
	request
	* @param classPK the primary key of the asset that is the subject of the
	request
	* @param type the request's type
	* @param extraData the extra data regarding the request
	* @param receiverUserId the primary key of the user receiving the request
	* @return the social request
	* @throws PortalException if the users could not be found, if the users
	were not from the same company, or if either of the users was the
	default user
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialRequest addRequest(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.addRequest(userId, groupId,
			className, classPK, type, extraData, receiverUserId);
	}

	/**
	* Removes all the social requests for the receiving user.
	*
	* @param receiverUserId the primary key of the receiving user
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteReceiverUserRequests(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteReceiverUserRequests(receiverUserId);
	}

	/**
	* Removes the social request identified by its primary key from the
	* database.
	*
	* @param requestId the primary key of the social request
	* @throws PortalException if the social request could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteRequest(long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteRequest(requestId);
	}

	/**
	* Removes the social request from the database.
	*
	* @param request the social request to be removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteRequest(
		com.liferay.portlet.social.model.SocialRequest request)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteRequest(request);
	}

	/**
	* Removes all the social requests for the requesting user.
	*
	* @param userId the primary key of the requesting user
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteUserRequests(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteUserRequests(userId);
	}

	/**
	* Returns a range of all the social requests for the receiving user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param receiverUserId the primary key of the receiving user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching social requests
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getReceiverUserRequests(
		long receiverUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequests(receiverUserId,
			start, end);
	}

	/**
	* Returns a range of all the social requests with the given status for the
	* receiving user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param receiverUserId the primary key of the receiving user
	* @param status the social request's status
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching social requests
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getReceiverUserRequests(
		long receiverUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequests(receiverUserId,
			status, start, end);
	}

	/**
	* Returns the number of social requests for the receiving user.
	*
	* @param receiverUserId the primary key of the receiving user
	* @return the number of matching social requests
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getReceiverUserRequestsCount(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequestsCount(receiverUserId);
	}

	/**
	* Returns the number of social requests with the given status for the
	* receiving user.
	*
	* @param receiverUserId the primary key of the receiving user
	* @param status the social request's status
	* @return the number of matching social requests
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getReceiverUserRequestsCount(long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequestsCount(receiverUserId,
			status);
	}

	/**
	* Returns a range of all the social requests for the requesting user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param userId the primary key of the requesting user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching social requests
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getUserRequests(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequests(userId, start, end);
	}

	/**
	* Returns a range of all the social requests with the given status for the
	* requesting user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param userId the primary key of the requesting user
	* @param status the social request's status
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching social requests
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getUserRequests(
		long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequests(userId, status,
			start, end);
	}

	/**
	* Returns the number of social requests for the requesting user.
	*
	* @param userId the primary key of the requesting user
	* @return the number of matching social requests
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getUserRequestsCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequestsCount(userId);
	}

	/**
	* Returns the number of social requests with the given status for the
	* requesting user.
	*
	* @param userId the primary key of the requesting user
	* @param status the social request's status
	* @return the number of matching social request
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getUserRequestsCount(long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequestsCount(userId, status);
	}

	/**
	* Returns <code>true</code> if a matching social requests exists in the
	* database.
	*
	* @param userId the primary key of the requesting user
	* @param className the class name of the asset that is the subject of the
	request
	* @param classPK the primary key of the asset that is the subject of the
	request
	* @param type the request's type
	* @param status the social request's status
	* @return <code>true</code> if the request exists; <code>false</code>
	otherwise
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasRequest(long userId, java.lang.String className,
		long classPK, int type, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.hasRequest(userId, className,
			classPK, type, status);
	}

	/**
	* Returns <code>true</code> if a matching social request exists in the
	* database.
	*
	* @param userId the primary key of the requesting user
	* @param className the class name of the asset that is the subject of the
	request
	* @param classPK the primary key of the asset that is the subject of the
	request
	* @param type the request's type
	* @param receiverUserId the primary key of the receiving user
	* @param status the social request's status
	* @return <code>true</code> if the social request exists;
	<code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasRequest(long userId, java.lang.String className,
		long classPK, int type, long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.hasRequest(userId, className,
			classPK, type, receiverUserId, status);
	}

	/**
	* Updates the social request replacing its status.
	*
	* <p>
	* If the status is updated to {@link
	* com.liferay.portlet.social.model.SocialRequestConstants#STATUS_CONFIRM}
	* then {@link
	* com.liferay.portlet.social.service.SocialRequestInterpreterLocalService#processConfirmation(
	* SocialRequest, ThemeDisplay)} is called. If the status is updated to
	* {@link
	* com.liferay.portlet.social.model.SocialRequestConstants#STATUS_IGNORE}
	* then {@link
	* com.liferay.portlet.social.service.SocialRequestInterpreterLocalService#processRejection(
	* SocialRequest, ThemeDisplay)} is called.
	* </p>
	*
	* @param requestId the primary key of the social request
	* @param status the new status
	* @param themeDisplay the theme display
	* @return the updated social request
	* @throws PortalException if the social request could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialRequest updateRequest(
		long requestId, int status,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.updateRequest(requestId, status,
			themeDisplay);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public SocialRequestLocalService getWrappedSocialRequestLocalService() {
		return _socialRequestLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedSocialRequestLocalService(
		SocialRequestLocalService socialRequestLocalService) {
		_socialRequestLocalService = socialRequestLocalService;
	}

	@Override
	public SocialRequestLocalService getWrappedService() {
		return _socialRequestLocalService;
	}

	@Override
	public void setWrappedService(
		SocialRequestLocalService socialRequestLocalService) {
		_socialRequestLocalService = socialRequestLocalService;
	}

	private SocialRequestLocalService _socialRequestLocalService;
}