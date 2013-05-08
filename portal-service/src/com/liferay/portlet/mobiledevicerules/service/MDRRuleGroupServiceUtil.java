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

package com.liferay.portlet.mobiledevicerules.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for MDRRuleGroup. This utility wraps
 * {@link com.liferay.portlet.mobiledevicerules.service.impl.MDRRuleGroupServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupService
 * @see com.liferay.portlet.mobiledevicerules.service.base.MDRRuleGroupServiceBaseImpl
 * @see com.liferay.portlet.mobiledevicerules.service.impl.MDRRuleGroupServiceImpl
 * @generated
 */
public class MDRRuleGroupServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.mobiledevicerules.service.impl.MDRRuleGroupServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

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

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup addRuleGroup(
		long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRuleGroup(groupId, nameMap, descriptionMap,
			serviceContext);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup copyRuleGroup(
		long ruleGroupId, long groupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().copyRuleGroup(ruleGroupId, groupId, serviceContext);
	}

	public static void deleteRuleGroup(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRuleGroup(ruleGroupId);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchRuleGroup(ruleGroupId);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup getRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRuleGroup(ruleGroupId);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup updateRuleGroup(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateRuleGroup(ruleGroupId, nameMap, descriptionMap,
			serviceContext);
	}

	public static MDRRuleGroupService getService() {
		if (_service == null) {
			_service = (MDRRuleGroupService)PortalBeanLocatorUtil.locate(MDRRuleGroupService.class.getName());

			ReferenceRegistry.registerReference(MDRRuleGroupServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(MDRRuleGroupService service) {
	}

	private static MDRRuleGroupService _service;
}