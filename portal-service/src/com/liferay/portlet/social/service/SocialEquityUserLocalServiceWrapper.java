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
 * This class is a wrapper for {@link SocialEquityUserLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityUserLocalService
 * @generated
 */
public class SocialEquityUserLocalServiceWrapper
	implements SocialEquityUserLocalService,
		ServiceWrapper<SocialEquityUserLocalService> {
	public SocialEquityUserLocalServiceWrapper(
		SocialEquityUserLocalService socialEquityUserLocalService) {
		_socialEquityUserLocalService = socialEquityUserLocalService;
	}

	/**
	* Adds the social equity user to the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user
	* @return the social equity user that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityUser addSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.addSocialEquityUser(socialEquityUser);
	}

	/**
	* Creates a new social equity user with the primary key. Does not add the social equity user to the database.
	*
	* @param equityUserId the primary key for the new social equity user
	* @return the new social equity user
	*/
	public com.liferay.portlet.social.model.SocialEquityUser createSocialEquityUser(
		long equityUserId) {
		return _socialEquityUserLocalService.createSocialEquityUser(equityUserId);
	}

	/**
	* Deletes the social equity user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityUserId the primary key of the social equity user
	* @throws PortalException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialEquityUser(long equityUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityUserLocalService.deleteSocialEquityUser(equityUserId);
	}

	/**
	* Deletes the social equity user from the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityUserLocalService.deleteSocialEquityUser(socialEquityUser);
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
		return _socialEquityUserLocalService.dynamicQuery(dynamicQuery);
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
		return _socialEquityUserLocalService.dynamicQuery(dynamicQuery, start,
			end);
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
		return _socialEquityUserLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _socialEquityUserLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the social equity user with the primary key.
	*
	* @param equityUserId the primary key of the social equity user
	* @return the social equity user
	* @throws PortalException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityUser getSocialEquityUser(
		long equityUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getSocialEquityUser(equityUserId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the social equity users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity users
	* @param end the upper bound of the range of social equity users (not inclusive)
	* @return the range of social equity users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityUser> getSocialEquityUsers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getSocialEquityUsers(start, end);
	}

	/**
	* Returns the number of social equity users.
	*
	* @return the number of social equity users
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialEquityUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getSocialEquityUsersCount();
	}

	/**
	* Updates the social equity user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user
	* @return the social equity user that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityUser updateSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.updateSocialEquityUser(socialEquityUser);
	}

	/**
	* Updates the social equity user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user
	* @param merge whether to merge the social equity user with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social equity user that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityUser updateSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.updateSocialEquityUser(socialEquityUser,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialEquityUserLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialEquityUserLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Removes ranking for the user with respect to all groups.
	*
	* <p>
	* This method is called by the portal when a user is deactivated.
	* </p>
	*
	* @param userId the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public void clearRanks(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityUserLocalService.clearRanks(userId);
	}

	/**
	* Returns the contribution equity score for the user.
	*
	* <p>
	* This method should only be used if social equity is turned on for only
	* one group, as it returns the contribution score for the first group it
	* finds. The first group found can be different from one execution to the
	* next.
	* </p>
	*
	* @param userId the primary key of the user
	* @return the contribution equity score
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityValue getContributionEquity(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getContributionEquity(userId);
	}

	/**
	* Returns the contribution equity score of the user with respect to the
	* group.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @return the contribution equity score
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityValue getContributionEquity(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getContributionEquity(userId,
			groupId);
	}

	/**
	* Returns the participation equity score for the user.
	*
	* <p>
	* This method should only be used if social equity is turned on for only
	* one group, as it returns the participation score for the first group it
	* finds. The first group found can be different from one execution to the
	* next.
	* </p>
	*
	* @param userId the primary key of the user
	* @return the participation equity score
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityValue getParticipationEquity(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getParticipationEquity(userId);
	}

	/**
	* Returns the participation equity score of the user with respect to the
	* group.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @return the participation equity score
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityValue getParticipationEquity(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getParticipationEquity(userId,
			groupId);
	}

	/**
	* Returns the rank of the user in the group based on the user's personal
	* equity.
	*
	* @param groupId the primary key of the group
	* @param userId the primary key of the user
	* @return the rank for the user in the group
	* @throws SystemException if a system exception occurred
	*/
	public int getRank(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getRank(groupId, userId);
	}

	/**
	* Returns an ordered range of all the social equity users in the group
	* with rankings greater than zero. It is strongly suggested to use {@link
	* com.liferay.portlet.social.util.comparator.SocialEquityUserRankComparator}
	* as the ordering comparator.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the
	* full result set.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param orderByComparator the comparator to order the social equity
	users, such as {@link
	com.liferay.portlet.social.util.comparator.SocialEquityUserRankComparator}
	(optionally <code>null</code>)
	* @return the ordered range of the social equity users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityUser> getRankedEquityUsers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getRankedEquityUsers(groupId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of the social equity users in the group with rankings
	* greater than zero.
	*
	* @param groupId the primary key of the group
	* @return the number of social equity users with rankings greater than
	zero
	* @throws SystemException if a system exception occurred
	*/
	public int getRankedEquityUsersCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getRankedEquityUsersCount(groupId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SocialEquityUserLocalService getWrappedSocialEquityUserLocalService() {
		return _socialEquityUserLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSocialEquityUserLocalService(
		SocialEquityUserLocalService socialEquityUserLocalService) {
		_socialEquityUserLocalService = socialEquityUserLocalService;
	}

	public SocialEquityUserLocalService getWrappedService() {
		return _socialEquityUserLocalService;
	}

	public void setWrappedService(
		SocialEquityUserLocalService socialEquityUserLocalService) {
		_socialEquityUserLocalService = socialEquityUserLocalService;
	}

	private SocialEquityUserLocalService _socialEquityUserLocalService;
}