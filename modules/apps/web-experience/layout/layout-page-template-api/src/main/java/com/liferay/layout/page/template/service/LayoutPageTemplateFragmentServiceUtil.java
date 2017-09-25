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

package com.liferay.layout.page.template.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for LayoutPageTemplateFragment. This utility wraps
 * {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateFragmentServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFragmentService
 * @see com.liferay.layout.page.template.service.base.LayoutPageTemplateFragmentServiceBaseImpl
 * @see com.liferay.layout.page.template.service.impl.LayoutPageTemplateFragmentServiceImpl
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFragmentServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateFragmentServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment addLayoutPageTemplateFragment(
		com.liferay.layout.page.template.model.LayoutPageTemplateFragment layoutPageTemplateFragment,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutPageTemplateFragment(layoutPageTemplateFragment,
			serviceContext);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment addLayoutPageTemplateFragment(
		long groupId, long layoutPageTemplateId, long fragmentId, int position,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutPageTemplateFragment(groupId,
			layoutPageTemplateId, fragmentId, position, serviceContext);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> deleteByLayoutPageTemplate(
		long groupId, long layoutPageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteByLayoutPageTemplate(groupId, layoutPageTemplateId);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
		long groupId, long layoutPageTemplateId, long fragmentId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteLayoutPageTemplateFragment(groupId,
			layoutPageTemplateId, fragmentId, serviceContext);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> getLayoutPageTemplateFragmentsByPageTemplate(
		long groupId, long layoutPageTemplateId) {
		return getService()
				   .getLayoutPageTemplateFragmentsByPageTemplate(groupId,
			layoutPageTemplateId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static LayoutPageTemplateFragmentService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LayoutPageTemplateFragmentService, LayoutPageTemplateFragmentService> _serviceTracker =
		ServiceTrackerFactory.open(LayoutPageTemplateFragmentService.class);
}