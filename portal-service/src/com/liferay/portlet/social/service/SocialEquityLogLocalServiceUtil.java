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
 * The utility for the social equity log local service. This utility wraps {@link com.liferay.portlet.social.service.impl.SocialEquityLogLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityLogLocalService
 * @see com.liferay.portlet.social.service.base.SocialEquityLogLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialEquityLogLocalServiceImpl
 * @generated
 */
public class SocialEquityLogLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialEquityLogLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the social equity log to the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityLog the social equity log
	* @return the social equity log that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityLog addSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialEquityLog(socialEquityLog);
	}

	/**
	* Creates a new social equity log with the primary key. Does not add the social equity log to the database.
	*
	* @param equityLogId the primary key for the new social equity log
	* @return the new social equity log
	*/
	public static com.liferay.portlet.social.model.SocialEquityLog createSocialEquityLog(
		long equityLogId) {
		return getService().createSocialEquityLog(equityLogId);
	}

	/**
	* Deletes the social equity log with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityLogId the primary key of the social equity log
	* @throws PortalException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSocialEquityLog(long equityLogId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityLog(equityLogId);
	}

	/**
	* Deletes the social equity log from the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityLog the social equity log
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityLog(socialEquityLog);
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
	* Returns the social equity log with the primary key.
	*
	* @param equityLogId the primary key of the social equity log
	* @return the social equity log
	* @throws PortalException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityLog getSocialEquityLog(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityLog(equityLogId);
	}

	/**
	* Returns a range of all the social equity logs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity logs
	* @param end the upper bound of the range of social equity logs (not inclusive)
	* @return the range of social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> getSocialEquityLogs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityLogs(start, end);
	}

	/**
	* Returns the number of social equity logs.
	*
	* @return the number of social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public static int getSocialEquityLogsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityLogsCount();
	}

	/**
	* Updates the social equity log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityLog the social equity log
	* @return the social equity log that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityLog updateSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityLog(socialEquityLog);
	}

	/**
	* Updates the social equity log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityLog the social equity log
	* @param merge whether to merge the social equity log with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social equity log that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityLog updateSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityLog(socialEquityLog, merge);
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

	/**
	* @deprecated {@link #addEquityLogs(long, long, String, String)}
	*/
	public static void addEquityLogs(long userId, long assetEntryId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addEquityLogs(userId, assetEntryId, actionId);
	}

	public static void addEquityLogs(long userId, long assetEntryId,
		java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addEquityLogs(userId, assetEntryId, actionId, extraData);
	}

	public static void addEquityLogs(long userId, java.lang.String className,
		long classPK, java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addEquityLogs(userId, className, classPK, actionId, extraData);
	}

	public static void addSocialEquityAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addSocialEquityAssetEntry(assetEntry);
	}

	public static void addSocialEquityUser(long groupId,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addSocialEquityUser(groupId, user);
	}

	public static void checkEquityLogs()
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().checkEquityLogs();
	}

	public static void deactivateEquityLogs(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deactivateEquityLogs(assetEntryId);
	}

	/**
	* @deprecated {@link #deactivateEquityLogs(long, String, long, String,
	String)}
	*/
	public static void deactivateEquityLogs(long userId, long assetEntryId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deactivateEquityLogs(userId, assetEntryId, actionId);
	}

	/**
	* @deprecated {@link #deactivateEquityLogs(long, String, long, String,
	String)}
	*/
	public static void deactivateEquityLogs(long userId,
		java.lang.String className, long classPK, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deactivateEquityLogs(userId, className, classPK, actionId);
	}

	public static void deactivateEquityLogs(long userId,
		java.lang.String className, long classPK, java.lang.String actionId,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deactivateEquityLogs(userId, className, classPK, actionId,
			extraData);
	}

	public static void deactivateEquityLogs(java.lang.String className,
		long classPK, java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deactivateEquityLogs(className, classPK, actionId, extraData);
	}

	public static void incrementSocialEquityAssetEntry_IQ(long assetEntryId,
		com.liferay.portlet.social.model.SocialEquityIncrementPayload equityPayload)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.incrementSocialEquityAssetEntry_IQ(assetEntryId, equityPayload);
	}

	public static void incrementSocialEquityUser_CQ(long groupId, long userId,
		com.liferay.portlet.social.model.SocialEquityIncrementPayload equityPayload)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().incrementSocialEquityUser_CQ(groupId, userId, equityPayload);
	}

	public static void incrementSocialEquityUser_PQ(long groupId, long userId,
		com.liferay.portlet.social.model.SocialEquityIncrementPayload equityPayload)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().incrementSocialEquityUser_PQ(groupId, userId, equityPayload);
	}

	public static void updateRanks() {
		getService().updateRanks();
	}

	public static void updateRanks(long groupId) {
		getService().updateRanks(groupId);
	}

	public static SocialEquityLogLocalService getService() {
		if (_service == null) {
			_service = (SocialEquityLogLocalService)PortalBeanLocatorUtil.locate(SocialEquityLogLocalService.class.getName());

			ReferenceRegistry.registerReference(SocialEquityLogLocalServiceUtil.class,
				"_service");
			MethodCache.remove(SocialEquityLogLocalService.class);
		}

		return _service;
	}

	public void setService(SocialEquityLogLocalService service) {
		MethodCache.remove(SocialEquityLogLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(SocialEquityLogLocalServiceUtil.class,
			"_service");
		MethodCache.remove(SocialEquityLogLocalService.class);
	}

	private static SocialEquityLogLocalService _service;
}