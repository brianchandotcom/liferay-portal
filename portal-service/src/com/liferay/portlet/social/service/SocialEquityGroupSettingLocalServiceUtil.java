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
 * The utility for the social equity group setting local service. This utility wraps {@link com.liferay.portlet.social.service.impl.SocialEquityGroupSettingLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityGroupSettingLocalService
 * @see com.liferay.portlet.social.service.base.SocialEquityGroupSettingLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialEquityGroupSettingLocalServiceImpl
 * @generated
 */
public class SocialEquityGroupSettingLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialEquityGroupSettingLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the social equity group setting to the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityGroupSetting the social equity group setting
	* @return the social equity group setting that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityGroupSetting addSocialEquityGroupSetting(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialEquityGroupSetting(socialEquityGroupSetting);
	}

	/**
	* Creates a new social equity group setting with the primary key. Does not add the social equity group setting to the database.
	*
	* @param equityGroupSettingId the primary key for the new social equity group setting
	* @return the new social equity group setting
	*/
	public static com.liferay.portlet.social.model.SocialEquityGroupSetting createSocialEquityGroupSetting(
		long equityGroupSettingId) {
		return getService().createSocialEquityGroupSetting(equityGroupSettingId);
	}

	/**
	* Deletes the social equity group setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityGroupSettingId the primary key of the social equity group setting
	* @throws PortalException if a social equity group setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSocialEquityGroupSetting(long equityGroupSettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityGroupSetting(equityGroupSettingId);
	}

	/**
	* Deletes the social equity group setting from the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityGroupSetting the social equity group setting
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSocialEquityGroupSetting(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityGroupSetting(socialEquityGroupSetting);
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
	* Returns the social equity group setting with the primary key.
	*
	* @param equityGroupSettingId the primary key of the social equity group setting
	* @return the social equity group setting
	* @throws PortalException if a social equity group setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityGroupSetting getSocialEquityGroupSetting(
		long equityGroupSettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityGroupSetting(equityGroupSettingId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the social equity group settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity group settings
	* @param end the upper bound of the range of social equity group settings (not inclusive)
	* @return the range of social equity group settings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityGroupSetting> getSocialEquityGroupSettings(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityGroupSettings(start, end);
	}

	/**
	* Returns the number of social equity group settings.
	*
	* @return the number of social equity group settings
	* @throws SystemException if a system exception occurred
	*/
	public static int getSocialEquityGroupSettingsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityGroupSettingsCount();
	}

	/**
	* Updates the social equity group setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityGroupSetting the social equity group setting
	* @return the social equity group setting that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityGroupSetting updateSocialEquityGroupSetting(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateSocialEquityGroupSetting(socialEquityGroupSetting);
	}

	/**
	* Updates the social equity group setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityGroupSetting the social equity group setting
	* @param merge whether to merge the social equity group setting with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social equity group setting that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityGroupSetting updateSocialEquityGroupSetting(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateSocialEquityGroupSetting(socialEquityGroupSetting,
			merge);
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

	public static boolean isEnabled(long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().isEnabled(groupId, className);
	}

	public static boolean isEnabled(long groupId, java.lang.String className,
		int type) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().isEnabled(groupId, className, type);
	}

	public static void updateEquityGroupSetting(long groupId,
		java.lang.String className, int type, boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateEquityGroupSetting(groupId, className, type, enabled);
	}

	public static SocialEquityGroupSettingLocalService getService() {
		if (_service == null) {
			_service = (SocialEquityGroupSettingLocalService)PortalBeanLocatorUtil.locate(SocialEquityGroupSettingLocalService.class.getName());

			ReferenceRegistry.registerReference(SocialEquityGroupSettingLocalServiceUtil.class,
				"_service");
			MethodCache.remove(SocialEquityGroupSettingLocalService.class);
		}

		return _service;
	}

	public void setService(SocialEquityGroupSettingLocalService service) {
		MethodCache.remove(SocialEquityGroupSettingLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(SocialEquityGroupSettingLocalServiceUtil.class,
			"_service");
		MethodCache.remove(SocialEquityGroupSettingLocalService.class);
	}

	private static SocialEquityGroupSettingLocalService _service;
}