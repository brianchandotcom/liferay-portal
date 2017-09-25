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
 * Provides the remote service utility for LayoutPageTemplate. This utility wraps
 * {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateService
 * @see com.liferay.layout.page.template.service.base.LayoutPageTemplateServiceBaseImpl
 * @see com.liferay.layout.page.template.service.impl.LayoutPageTemplateServiceImpl
 * @generated
 */
@ProviderType
public class LayoutPageTemplateServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.layout.page.template.model.LayoutPageTemplate addLayoutPageTemplate(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutPageTemplate(groupId, layoutPageTemplateFolderId,
			name, serviceContext);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplate deleteLayoutPageTemplate(
		long pageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteLayoutPageTemplate(pageTemplateId);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> deletePageTemplates(
		long[] pageTemplatesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePageTemplates(pageTemplatesIds);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplate fetchLayoutPageTemplate(
		long pageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchLayoutPageTemplate(pageTemplateId);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> fetchPageTemplates(
		long layoutPageTemplateFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchPageTemplates(layoutPageTemplateFolderId);
	}

	public static int getLayoutPageTemplateFoldersCount(long groupId,
		long layoutPageTemplateFolderId) {
		return getService()
				   .getLayoutPageTemplateFoldersCount(groupId,
			layoutPageTemplateFolderId);
	}

	public static int getLayoutPageTemplateFoldersCount(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name) {
		return getService()
				   .getLayoutPageTemplateFoldersCount(groupId,
			layoutPageTemplateFolderId, name);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getPageTemplates(
		long groupId, long layoutPageTemplateFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getPageTemplates(groupId, layoutPageTemplateFolderId,
			start, end);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getPageTemplates(
		long groupId, long layoutPageTemplateFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplate> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getPageTemplates(groupId, layoutPageTemplateFolderId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getPageTemplates(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplate> orderByComparator) {
		return getService()
				   .getPageTemplates(groupId, layoutPageTemplateFolderId, name,
			start, end, orderByComparator);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplate updateLayoutPageTemplate(
		long pageTemplateId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateLayoutPageTemplate(pageTemplateId, name);
	}

	public static LayoutPageTemplateService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LayoutPageTemplateService, LayoutPageTemplateService> _serviceTracker =
		ServiceTrackerFactory.open(LayoutPageTemplateService.class);
}