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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the social equity user local service. This utility wraps {@link com.liferay.portlet.social.service.impl.SocialEquityUserLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityUserLocalService
 * @see com.liferay.portlet.social.service.base.SocialEquityUserLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialEquityUserLocalServiceImpl
 * @generated
 */
public class SocialEquityUserLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialEquityUserLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the social equity user to the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user
	* @return the social equity user that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser addSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialEquityUser(socialEquityUser);
	}

	/**
	* Creates a new social equity user with the primary key. Does not add the social equity user to the database.
	*
	* @param equityUserId the primary key for the new social equity user
	* @return the new social equity user
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser createSocialEquityUser(
		long equityUserId) {
		return getService().createSocialEquityUser(equityUserId);
	}

	/**
	* Deletes the social equity user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityUserId the primary key of the social equity user
	* @throws PortalException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSocialEquityUser(long equityUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityUser(equityUserId);
	}

	/**
	* Deletes the social equity user from the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityUser(socialEquityUser);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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

	/**
	* Returns the social equity user with the primary key.
	*
	* @param equityUserId the primary key of the social equity user
	* @return the social equity user
	* @throws PortalException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser getSocialEquityUser(
		long equityUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityUser(equityUserId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> getSocialEquityUsers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityUsers(start, end);
	}

	/**
	* Returns the number of social equity users.
	*
	* @return the number of social equity users
	* @throws SystemException if a system exception occurred
	*/
	public static int getSocialEquityUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityUsersCount();
	}

	/**
	* Updates the social equity user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user
	* @return the social equity user that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser updateSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityUser(socialEquityUser);
	}

	/**
	* Updates the social equity user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user
	* @param merge whether to merge the social equity user with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social equity user that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityUser updateSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityUser(socialEquityUser, merge);
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

	public static void clearRanks(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearRanks(user);
	}

	public static void deleteSocialEquityUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityUser(user);
	}

	public static com.liferay.portlet.social.model.SocialEquityValue getContributionEquity(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getContributionEquity(userId);
	}

	public static com.liferay.portlet.social.model.SocialEquityValue getContributionEquity(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getContributionEquity(userId, groupId);
	}

	public static com.liferay.portlet.social.model.SocialEquityValue getParticipationEquity(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getParticipationEquity(userId);
	}

	public static com.liferay.portlet.social.model.SocialEquityValue getParticipationEquity(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getParticipationEquity(userId, groupId);
	}

	public static int getRank(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRank(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> getRankedEquityUsers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRankedEquityUsers(groupId, start, end, orderByComparator);
	}

	public static int getRankedEquityUsersCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRankedEquityUsersCount(groupId);
	}

	public static SocialEquityUserLocalService getService() {
		if (_service == null) {
			_service = (SocialEquityUserLocalService)PortalBeanLocatorUtil.locate(SocialEquityUserLocalService.class.getName());

			ReferenceRegistry.registerReference(SocialEquityUserLocalServiceUtil.class,
				"_service");
			MethodCache.remove(SocialEquityUserLocalService.class);
		}

		return _service;
	}

	public void setService(SocialEquityUserLocalService service) {
		MethodCache.remove(SocialEquityUserLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(SocialEquityUserLocalServiceUtil.class,
			"_service");
		MethodCache.remove(SocialEquityUserLocalService.class);
	}

	private static SocialEquityUserLocalService _service;
}