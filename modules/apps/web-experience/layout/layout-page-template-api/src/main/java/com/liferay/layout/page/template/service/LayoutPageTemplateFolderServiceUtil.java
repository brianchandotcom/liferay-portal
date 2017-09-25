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
 * Provides the remote service utility for LayoutPageTemplateFolder. This utility wraps
 * {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateFolderServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFolderService
 * @see com.liferay.layout.page.template.service.base.LayoutPageTemplateFolderServiceBaseImpl
 * @see com.liferay.layout.page.template.service.impl.LayoutPageTemplateFolderServiceImpl
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFolderServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateFolderServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.layout.page.template.model.LayoutPageTemplateFolder addLayoutPageTemplateFolder(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutPageTemplateFolder(groupId, name, description,
			serviceContext);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateFolder deleteLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteLayoutPageTemplateFolder(layoutPageTemplateFolderId);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> deleteLayoutPageTemplateFolders(
		long[] layoutPageTemplateFolderIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteLayoutPageTemplateFolders(layoutPageTemplateFolderIds);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateFolder fetchLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchLayoutPageTemplateFolder(layoutPageTemplateFolderId);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutPageTemplateFolders(groupId, start, end);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLayoutPageTemplateFolders(groupId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLayoutPageTemplateFolders(groupId, name, start, end,
			orderByComparator);
	}

	public static int getLayoutPageTemplateFoldersCount(long groupId) {
		return getService().getLayoutPageTemplateFoldersCount(groupId);
	}

	public static int getLayoutPageTemplateFoldersCount(long groupId,
		java.lang.String name) {
		return getService().getLayoutPageTemplateFoldersCount(groupId, name);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateFolder updateLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutPageTemplateFolder(layoutPageTemplateFolderId,
			name, description);
	}

	public static LayoutPageTemplateFolderService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LayoutPageTemplateFolderService, LayoutPageTemplateFolderService> _serviceTracker =
		ServiceTrackerFactory.open(LayoutPageTemplateFolderService.class);
}