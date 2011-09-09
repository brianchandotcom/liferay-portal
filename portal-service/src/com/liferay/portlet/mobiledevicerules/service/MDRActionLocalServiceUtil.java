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

package com.liferay.portlet.mobiledevicerules.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the m d r action local service. This utility wraps {@link com.liferay.portlet.mobiledevicerules.service.impl.MDRActionLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Edward C. Han
 * @see MDRActionLocalService
 * @see com.liferay.portlet.mobiledevicerules.service.base.MDRActionLocalServiceBaseImpl
 * @see com.liferay.portlet.mobiledevicerules.service.impl.MDRActionLocalServiceImpl
 * @generated
 */
public class MDRActionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.mobiledevicerules.service.impl.MDRActionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the m d r action to the database. Also notifies the appropriate model listeners.
	*
	* @param mdrAction the m d r action
	* @return the m d r action that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction addMDRAction(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addMDRAction(mdrAction);
	}

	/**
	* Creates a new m d r action with the primary key. Does not add the m d r action to the database.
	*
	* @param actionId the primary key for the new m d r action
	* @return the new m d r action
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction createMDRAction(
		long actionId) {
		return getService().createMDRAction(actionId);
	}

	/**
	* Deletes the m d r action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param actionId the primary key of the m d r action
	* @throws PortalException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteMDRAction(long actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMDRAction(actionId);
	}

	/**
	* Deletes the m d r action from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrAction the m d r action
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteMDRAction(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMDRAction(mdrAction);
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
	* Returns the m d r action with the primary key.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action
	* @throws PortalException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction getMDRAction(
		long actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMDRAction(actionId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the m d r action with the UUID in the group.
	*
	* @param uuid the UUID of m d r action
	* @param groupId the group id of the m d r action
	* @return the m d r action
	* @throws PortalException if a m d r action with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction getMDRActionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMDRActionByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the m d r actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> getMDRActions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMDRActions(start, end);
	}

	/**
	* Returns the number of m d r actions.
	*
	* @return the number of m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static int getMDRActionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMDRActionsCount();
	}

	/**
	* Updates the m d r action in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrAction the m d r action
	* @return the m d r action that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction updateMDRAction(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMDRAction(mdrAction);
	}

	/**
	* Updates the m d r action in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrAction the m d r action
	* @param merge whether to merge the m d r action with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the m d r action that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction updateMDRAction(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMDRAction(mdrAction, merge);
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

	public static com.liferay.portlet.mobiledevicerules.model.MDRAction addAction(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addAction(groupId, className, classPK, ruleGroupInstanceId,
			nameMap, descriptionMap, type, typeSettings, serviceContext);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRAction addAction(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addAction(groupId, className, classPK, ruleGroupInstanceId,
			nameMap, descriptionMap, type, typeSettingsProperties,
			serviceContext);
	}

	public static void deleteAction(long actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAction(actionId);
	}

	public static void deleteAction(
		com.liferay.portlet.mobiledevicerules.model.MDRAction action)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAction(action);
	}

	public static void deleteActions(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteActions(ruleGroupInstanceId);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRAction fetchAction(
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchAction(actionId);
	}

	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> getActions(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getActions(ruleGroupInstanceId);
	}

	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> getActions(
		long ruleGroupInstanceId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getActions(ruleGroupInstanceId, start, end);
	}

	public static int getActionsCount(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getActionsCount(ruleGroupInstanceId);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRAction updateAction(
		long actionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateAction(actionId, nameMap, descriptionMap, type,
			typeSettings, serviceContext);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRAction updateAction(
		long actionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateAction(actionId, nameMap, descriptionMap, type,
			typeSettingsProperties, serviceContext);
	}

	public static MDRActionLocalService getService() {
		if (_service == null) {
			_service = (MDRActionLocalService)PortalBeanLocatorUtil.locate(MDRActionLocalService.class.getName());

			ReferenceRegistry.registerReference(MDRActionLocalServiceUtil.class,
				"_service");
			MethodCache.remove(MDRActionLocalService.class);
		}

		return _service;
	}

	public void setService(MDRActionLocalService service) {
		MethodCache.remove(MDRActionLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(MDRActionLocalServiceUtil.class,
			"_service");
		MethodCache.remove(MDRActionLocalService.class);
	}

	private static MDRActionLocalService _service;
}