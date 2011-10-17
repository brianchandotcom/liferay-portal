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
 * This class is a wrapper for {@link SocialEquityLogLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLogLocalService
 * @generated
 */
public class SocialEquityLogLocalServiceWrapper
	implements SocialEquityLogLocalService,
		ServiceWrapper<SocialEquityLogLocalService> {
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
	* Records the social equity action and adjusts social equity scores.
	*
	* @param userId the primary key of the acting user
	* @param assetEntryId the primary key of the target asset entry
	* @param actionId the ID of the action
	* @throws PortalException if the asset entry could not be found
	* @throws SystemException if a system exception occurred
	* @deprecated Replaced by {@link #addEquityLogs(long, long, String,
	String)} to support the <code>extraData</code> parameter
	*/
	public void addEquityLogs(long userId, long assetEntryId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addEquityLogs(userId, assetEntryId,
			actionId);
	}

	/**
	* Records the social equity action and adjusts social equity scores based
	* on the user's action done on the target asset entry.
	*
	* <p>
	* The <code>extraData</code> parameter can contain further information
	* about the action such as the file name for a download action. It is used
	* to distinguish between otherwise equal actions, such as multiple
	* downloads of message boards attachments.
	* </p>
	*
	* @param userId the primary key of the acting user
	* @param assetEntryId the primary key of the target asset entry
	* @param actionId the ID of the action
	* @param extraData the extra data associated with the action
	* @throws PortalException if the asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void addEquityLogs(long userId, long assetEntryId,
		java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addEquityLogs(userId, assetEntryId,
			actionId, extraData);
	}

	/**
	* Records the social equity action and adjusts social equity scores based
	* on the user's action done on the target asset entry identified by the
	* className/classPK pair.
	*
	* <p>
	* The <code>extraData</code> parameter can contain further information
	* about the action such as the file name for a download action. It is used
	* to distinguish between otherwise equal actions, such as multiple
	* downloads of message boards attachments.
	* </p>
	*
	* @param userId the primary key of the acting user
	* @param className the class name of the target asset
	* @param classPK the primary key of the target asset (not the asset entry
	referring to it)
	* @param actionId the ID of the action
	* @param extraData the extra data associated with the action
	* @throws PortalException if the asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void addEquityLogs(long userId, java.lang.String className,
		long classPK, java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addEquityLogs(userId, className, classPK,
			actionId, extraData);
	}

	/**
	* Inserts a new row for the asset entry into the
	* <code>SocialEquityAssetEntry</code> table.
	*
	* <p>
	* This method should not be used directly by portlets. It is made public
	* so that it can be in its own transaction to safeguard against
	* concurrency issues.
	* </p>
	*
	* @param assetEntry the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void addSocialEquityAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addSocialEquityAssetEntry(assetEntry);
	}

	/**
	* Inserts a new row for the user into the <code>SocialEquityUser</code>
	* table.
	*
	* <p>
	* This method should not be used directly by portlets. It is made public
	* so that it can be in its own transaction to safeguard against
	* concurrency issues.
	* </p>
	*
	* @param groupId the primary key of the group the user is currently
	acting in
	* @param user the acting user
	* @throws SystemException if a system exception occurred
	*/
	public void addSocialEquityUser(long groupId,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addSocialEquityUser(groupId, user);
	}

	/**
	* This is a cleanup method to remove expired actions and any data
	* associated with them.
	*
	* <p>
	* <i>This method should normally only be called by the portal.</i>
	* </p>
	*
	* <p>
	* By default it is run by the scheduler once a day, but the frequency can
	* be modified by overriding the
	* <code>social.equity.equity.log.check.interval</code> property found in
	* <code>portal.properties</code>.
	* </p>
	*
	* @throws SystemException if a system exception occurred
	*/
	public void checkEquityLogs()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.checkEquityLogs();
	}

	/**
	* Removes all actions associated with the asset and adjusts equity scores
	* accordingly.
	*
	* <p>
	* This method is called by the <code>AssetEntry</code> service
	* automatically when an asset entry is deleted.
	* </p>
	*
	* @param assetEntryId the primary key of the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void deactivateEquityLogs(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(assetEntryId);
	}

	/**
	* Removes actions identified by the acting user, the action ID and the
	* target asset's primary key.
	*
	* @param userId the primary key of the acting user
	* @param assetEntryId the primary key of the target asset entry
	* @param actionId the ID of the action
	* @throws PortalException if the asset entry could not be found
	* @throws SystemException if a system exception occurred
	* @deprecated Replaced by {@link #deactivateEquityLogs(long, String, long,
	String, String)} to support the <code>extraData</code>
	parameter
	*/
	public void deactivateEquityLogs(long userId, long assetEntryId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(userId, assetEntryId,
			actionId);
	}

	/**
	* Removes actions identified by the acting user, the action ID and the
	* target asset's className/classPK pair.
	*
	* @param userId the primary key of the acting user
	* @param className the class name of the target asset
	* @param classPK the primary key of the target asset (not the asset
	entry referring to it)
	* @param actionId the ID of the action
	* @throws PortalException if the asset entry cannot be retrieved
	* @throws SystemException if a system exception occurred
	* @deprecated Replaced by {@link #deactivateEquityLogs(long, String, long,
	String, String)} to support the <code>extraData</code>
	parameter
	*/
	public void deactivateEquityLogs(long userId, java.lang.String className,
		long classPK, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(userId, className,
			classPK, actionId);
	}

	/**
	* Removes actions identified by the acting user, the action ID and the
	* target asset's className/classPK pair.
	*
	* <p>
	* The <code>extraData</code> parameter can be used to further identify the
	* action.
	* </p>
	*
	* @param userId the primary key of the acting user
	* @param className the class name of the target asset
	* @param classPK the primary key of the target asset (not the asset entry
	referring to it)
	* @param actionId the ID of the action
	* @param extraData the extra data associated with the action
	* @throws PortalException if the asset entry cannot be retrieved
	* @throws SystemException if a system exception occurred
	*/
	public void deactivateEquityLogs(long userId, java.lang.String className,
		long classPK, java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(userId, className,
			classPK, actionId, extraData);
	}

	/**
	* Removes actions identified by action ID done on an asset by any user.
	*
	* @param className the class name of the target asset
	* @param classPK the primary key of the target asset (not the asset entry
	referring to it)
	* @param actionId the ID of the action
	* @param extraData the extra data associated with the action
	* @throws PortalException if the asset entry cannot be retrieved
	* @throws SystemException if a system exception occurred
	*/
	public void deactivateEquityLogs(java.lang.String className, long classPK,
		java.lang.String actionId, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(className, classPK,
			actionId, extraData);
	}

	/**
	* Removes all actions done by the user.
	*
	* <p>
	* This method is called by the portal when a user is deleted.
	* </p>
	*
	* @param userId the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public void deactivateUserEquityLogs(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateUserEquityLogs(userId);
	}

	/**
	* Increments the information equity value of the asset by the number set
	* in the equity payload.
	*
	* <p>
	* This method is annotated with the <code>BufferedIncrement</code>
	* annotation, which means that in case of heavy load, invocations of this
	* method can be aggregated into one method call containing the sum of the
	* individual increments.
	* </p>
	*
	* <p>
	* <i>This method should not be called directly by portlets. It is made
	* public only to accommodate the <code>BufferedIncrement</code>
	* annotation.</i>
	* </p>
	*
	* @param assetEntryId the primary key of the target asset entry
	* @param equityPayload the equity payload containing the increments
	* @throws SystemException if a system exception occurred
	*/
	public void incrementSocialEquityAssetEntry_IQ(long assetEntryId,
		com.liferay.portlet.social.model.SocialEquityIncrementPayload equityPayload)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.incrementSocialEquityAssetEntry_IQ(assetEntryId,
			equityPayload);
	}

	/**
	* Increments the contribution equity value of the user by the number set
	* in the equity payload.
	*
	* <p>
	* This method is annotated with the <code>BufferedIncrement</code>
	* annotation, which means that in case of heavy load, invocations of this
	* method can be aggregated into one method call containing the sum of the
	* individual increments.
	* </p>
	*
	* <P>
	* <i>This method should not be called directly by portlets. It is made
	* public only to accommodate the <code>BufferedIncrement</code>
	* annotation.</i>
	* </p>
	*
	* @param groupId the primary key of the group in which the user is acting
	* @param userId the primary key of the acting user
	* @param equityPayload the equity payload containing the increments
	* @throws SystemException if a system exception occurred
	*/
	public void incrementSocialEquityUser_CQ(long groupId, long userId,
		com.liferay.portlet.social.model.SocialEquityIncrementPayload equityPayload)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.incrementSocialEquityUser_CQ(groupId,
			userId, equityPayload);
	}

	/**
	* Increments the participation equity value of the user by the number set
	* in the equity payload.
	*
	* <p>
	* This method is annotated with the <code>BufferedIncrement</code>
	* annotation, which means that in case of heavy load, invocations of this
	* method can be aggregated into one method call containing the sum of the
	* individual increments.
	* </p>
	*
	* <p>
	* <i>This method should not be called directly by portlets. It is made
	* public only to accommodate the <code>BufferedIncrement</code>
	* annotation. </i>
	* </p>
	*
	* @param groupId the primary key of the group in which the user is acting
	* @param userId the primary key of the acting user
	* @param equityPayload the equity payload containing the increments
	* @throws SystemException if a system exception occurred
	*/
	public void incrementSocialEquityUser_PQ(long groupId, long userId,
		com.liferay.portlet.social.model.SocialEquityIncrementPayload equityPayload)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.incrementSocialEquityUser_PQ(groupId,
			userId, equityPayload);
	}

	/**
	* Updates user ranking for all groups.
	*/
	public void updateRanks() {
		_socialEquityLogLocalService.updateRanks();
	}

	/**
	* Updates user ranking for a group.
	*
	* @param groupId the primary key of the group
	*/
	public void updateRanks(long groupId) {
		_socialEquityLogLocalService.updateRanks(groupId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SocialEquityLogLocalService getWrappedSocialEquityLogLocalService() {
		return _socialEquityLogLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSocialEquityLogLocalService(
		SocialEquityLogLocalService socialEquityLogLocalService) {
		_socialEquityLogLocalService = socialEquityLogLocalService;
	}

	public SocialEquityLogLocalService getWrappedService() {
		return _socialEquityLogLocalService;
	}

	public void setWrappedService(
		SocialEquityLogLocalService socialEquityLogLocalService) {
		_socialEquityLogLocalService = socialEquityLogLocalService;
	}

	private SocialEquityLogLocalService _socialEquityLogLocalService;
}