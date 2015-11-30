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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for SocialActivitySetting. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySettingLocalServiceUtil
 * @see com.liferay.portlet.social.service.base.SocialActivitySettingLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialActivitySettingLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SocialActivitySettingLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivitySettingLocalServiceUtil} to access the social activity setting local service. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialActivitySettingLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the social activity setting to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySetting the social activity setting
	* @return the social activity setting that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.social.model.SocialActivitySetting addSocialActivitySetting(
		com.liferay.portlet.social.model.SocialActivitySetting socialActivitySetting);

	/**
	* Creates a new social activity setting with the primary key. Does not add the social activity setting to the database.
	*
	* @param activitySettingId the primary key for the new social activity setting
	* @return the new social activity setting
	*/
	public com.liferay.portlet.social.model.SocialActivitySetting createSocialActivitySetting(
		long activitySettingId);

	public void deleteActivitySetting(long groupId, java.lang.String className,
		long classPK);

	public void deleteActivitySettings(long groupId);

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	/**
	* Deletes the social activity setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activitySettingId the primary key of the social activity setting
	* @return the social activity setting that was removed
	* @throws PortalException if a social activity setting with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.social.model.SocialActivitySetting deleteSocialActivitySetting(
		long activitySettingId) throws PortalException;

	/**
	* Deletes the social activity setting from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySetting the social activity setting
	* @return the social activity setting that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.social.model.SocialActivitySetting deleteSocialActivitySetting(
		com.liferay.portlet.social.model.SocialActivitySetting socialActivitySetting);

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.social.model.SocialActivitySetting fetchSocialActivitySetting(
		long activitySettingId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.social.model.SocialActivityDefinition getActivityDefinition(
		long groupId, java.lang.String className, int activityType);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.social.model.SocialActivityDefinition> getActivityDefinitions(
		long groupId, java.lang.String className);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySetting> getActivitySettings(
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	/**
	* Returns the social activity setting with the primary key.
	*
	* @param activitySettingId the primary key of the social activity setting
	* @return the social activity setting
	* @throws PortalException if a social activity setting with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.social.model.SocialActivitySetting getSocialActivitySetting(
		long activitySettingId) throws PortalException;

	/**
	* Returns a range of all the social activity settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @return the range of social activity settings
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySetting> getSocialActivitySettings(
		int start, int end);

	/**
	* Returns the number of social activity settings.
	*
	* @return the number of social activity settings
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSocialActivitySettingsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isEnabled(long groupId, long classNameId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isEnabled(long groupId, long classNameId, long classPK);

	public void updateActivitySetting(long groupId, java.lang.String className,
		int activityType,
		com.liferay.portlet.social.model.SocialActivityCounterDefinition activityCounterDefinition)
		throws PortalException;

	public void updateActivitySetting(long groupId, java.lang.String className,
		long classPK, boolean enabled) throws PortalException;

	public void updateActivitySetting(long groupId, java.lang.String className,
		boolean enabled) throws PortalException;

	public void updateActivitySettings(long groupId,
		java.lang.String className, int activityType,
		java.util.List<com.liferay.portlet.social.model.SocialActivityCounterDefinition> activityCounterDefinitions)
		throws PortalException;

	/**
	* Updates the social activity setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySetting the social activity setting
	* @return the social activity setting that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.social.model.SocialActivitySetting updateSocialActivitySetting(
		com.liferay.portlet.social.model.SocialActivitySetting socialActivitySetting);
}