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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPRule. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPRuleServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPRuleService
 * @see com.liferay.commerce.product.service.base.CPRuleServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPRuleServiceImpl
 * @generated
 */
@ProviderType
public class CPRuleServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPRuleServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPRule addCPRule(
		String name, boolean active, String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addCPRule(name, active, type, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPRule deleteCPRule(
		long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPRule(cpRuleId);
	}

	public static com.liferay.commerce.product.model.CPRule getCPRule(
		long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPRule(cpRuleId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPRule> getCPRules(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPRule> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPRules(groupId, start, end, orderByComparator);
	}

	public static int getCPRulesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPRulesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPRule> searchCPRules(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPRules(companyId, groupId, keywords, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPRule> searchCPRules(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().searchCPRules(searchContext);
	}

	public static com.liferay.commerce.product.model.CPRule updateCPRule(
		long cpRuleId, String name, boolean active, String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPRule(cpRuleId, name, active, type, serviceContext);
	}

	public static CPRuleService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPRuleService, CPRuleService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPRuleService.class);

		ServiceTracker<CPRuleService, CPRuleService> serviceTracker = new ServiceTracker<CPRuleService, CPRuleService>(bundle.getBundleContext(),
				CPRuleService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}