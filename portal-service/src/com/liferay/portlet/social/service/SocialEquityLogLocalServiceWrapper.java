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

/**
 * <p>
 * This class is a wrapper for {@link SocialEquityLogLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLogLocalService
 * @generated
 */
public class SocialEquityLogLocalServiceWrapper
	implements SocialEquityLogLocalService {
	public SocialEquityLogLocalServiceWrapper(
		SocialEquityLogLocalService socialEquityLogLocalService) {
		_socialEquityLogLocalService = socialEquityLogLocalService;
	}

	/**
	* Adds the social equity log to the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityLog the social equity log
	* @return the social equity log that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog addSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.addSocialEquityLog(socialEquityLog);
	}

	/**
	* Creates a new social equity log with the primary key. Does not add the social equity log to the database.
	*
	* @param equityLogId the primary key for the new social equity log
	* @return the new social equity log
	*/
	public com.liferay.portlet.social.model.SocialEquityLog createSocialEquityLog(
		long equityLogId) {
		return _socialEquityLogLocalService.createSocialEquityLog(equityLogId);
	}

	/**
	* Deletes the social equity log with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityLogId the primary key of the social equity log
	* @throws PortalException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialEquityLog(long equityLogId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deleteSocialEquityLog(equityLogId);
	}

	/**
	* Deletes the social equity log from the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityLog the social equity log
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deleteSocialEquityLog(socialEquityLog);
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
		return _socialEquityLogLocalService.dynamicQuery(dynamicQuery);
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
		return _socialEquityLogLocalService.dynamicQuery(dynamicQuery, start,
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
		return _socialEquityLogLocalService.dynamicQuery(dynamicQuery, start,
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
		return _socialEquityLogLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the social equity log with the primary key.
	*
	* @param equityLogId the primary key of the social equity log
	* @return the social equity log
	* @throws PortalException if a social equity log with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog getSocialEquityLog(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.getSocialEquityLog(equityLogId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.getPersistedModel(primaryKeyObj);
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
	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> getSocialEquityLogs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.getSocialEquityLogs(start, end);
	}

	/**
	* Returns the number of social equity logs.
	*
	* @return the number of social equity logs
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialEquityLogsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.getSocialEquityLogsCount();
	}

	/**
	* Updates the social equity log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityLog the social equity log
	* @return the social equity log that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog updateSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.updateSocialEquityLog(socialEquityLog);
	}

	/**
	* Updates the social equity log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityLog the social equity log
	* @param merge whether to merge the social equity log with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social equity log that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityLog updateSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.updateSocialEquityLog(socialEquityLog,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialEquityLogLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialEquityLogLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* @deprecated {@link #addEquityLogs(long, long, String, String)}
	*/
	public void addEquityLogs(long userId, long assetEntryId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addEquityLogs(userId, assetEntryId,
			actionId);
	}

	public void addEquityLogs(long userId, long assetEntryId,
		java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addEquityLogs(userId, assetEntryId,
			actionId, extraData);
	}

	public void addEquityLogs(long userId, java.lang.String className,
		long classPK, java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addEquityLogs(userId, className, classPK,
			actionId, extraData);
	}

	public void addSocialEquityAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addSocialEquityAssetEntry(assetEntry);
	}

	public void addSocialEquityUser(long groupId,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addSocialEquityUser(groupId, user);
	}

	public void checkEquityLogs()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.checkEquityLogs();
	}

	public void deactivateEquityLogs(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(assetEntryId);
	}

	/**
	* @deprecated {@link #deactivateEquityLogs(long, String, long, String,
	String)}
	*/
	public void deactivateEquityLogs(long userId, long assetEntryId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(userId, assetEntryId,
			actionId);
	}

	/**
	* @deprecated {@link #deactivateEquityLogs(long, String, long, String,
	String)}
	*/
	public void deactivateEquityLogs(long userId, java.lang.String className,
		long classPK, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(userId, className,
			classPK, actionId);
	}

	public void deactivateEquityLogs(long userId, java.lang.String className,
		long classPK, java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(userId, className,
			classPK, actionId, extraData);
	}

	public void deactivateEquityLogs(java.lang.String className, long classPK,
		java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(className, classPK,
			actionId, extraData);
	}

	public void deactivateUserEquityLogs(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateUserEquityLogs(userId);
	}

	public void incrementSocialEquityAssetEntry_IQ(long assetEntryId,
		com.liferay.portlet.social.model.SocialEquityIncrementPayload equityPayload)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.incrementSocialEquityAssetEntry_IQ(assetEntryId,
			equityPayload);
	}

	public void incrementSocialEquityUser_CQ(long groupId, long userId,
		com.liferay.portlet.social.model.SocialEquityIncrementPayload equityPayload)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.incrementSocialEquityUser_CQ(groupId,
			userId, equityPayload);
	}

	public void incrementSocialEquityUser_PQ(long groupId, long userId,
		com.liferay.portlet.social.model.SocialEquityIncrementPayload equityPayload)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.incrementSocialEquityUser_PQ(groupId,
			userId, equityPayload);
	}

	public void updateRanks() {
		_socialEquityLogLocalService.updateRanks();
	}

	public void updateRanks(long groupId) {
		_socialEquityLogLocalService.updateRanks(groupId);
	}

	public SocialEquityLogLocalService getWrappedSocialEquityLogLocalService() {
		return _socialEquityLogLocalService;
	}

	public void setWrappedSocialEquityLogLocalService(
		SocialEquityLogLocalService socialEquityLogLocalService) {
		_socialEquityLogLocalService = socialEquityLogLocalService;
	}

	private SocialEquityLogLocalService _socialEquityLogLocalService;
}