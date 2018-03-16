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

package com.liferay.knowledge.base.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for KBComment. This utility wraps
 * {@link com.liferay.knowledge.base.service.impl.KBCommentServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see KBCommentService
 * @see com.liferay.knowledge.base.service.base.KBCommentServiceBaseImpl
 * @see com.liferay.knowledge.base.service.impl.KBCommentServiceImpl
 * @generated
 */
@ProviderType
public class KBCommentServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.knowledge.base.service.impl.KBCommentServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.knowledge.base.model.KBComment deleteKBComment(
		com.liferay.knowledge.base.model.KBComment kbComment)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteKBComment(kbComment);
	}

	public static com.liferay.knowledge.base.model.KBComment deleteKBComment(
		long kbCommentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteKBComment(kbCommentId);
	}

	public static com.liferay.knowledge.base.model.KBComment getKBComment(
		long kbCommentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getKBComment(kbCommentId);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment> getKBComments(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getKBComments(groupId, status, start, end);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment> getKBComments(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.knowledge.base.model.KBComment> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getKBComments(groupId, status, start, end, obc);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment> getKBComments(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.knowledge.base.model.KBComment> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getKBComments(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment> getKBComments(
		long groupId, java.lang.String className, long classPK, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getKBComments(groupId, className, classPK, status, start,
			end);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment> getKBComments(
		long groupId, java.lang.String className, long classPK, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.knowledge.base.model.KBComment> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getKBComments(groupId, className, classPK, status, start,
			end, obc);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBComment> getKBComments(
		long groupId, java.lang.String className, long classPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.knowledge.base.model.KBComment> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getKBComments(groupId, className, classPK, start, end, obc);
	}

	public static int getKBCommentsCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getKBCommentsCount(groupId);
	}

	public static int getKBCommentsCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getKBCommentsCount(groupId, status);
	}

	public static int getKBCommentsCount(long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getKBCommentsCount(groupId, className, classPK);
	}

	public static int getKBCommentsCount(long groupId,
		java.lang.String className, long classPK, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getKBCommentsCount(groupId, className, classPK, status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.knowledge.base.model.KBComment updateKBComment(
		long kbCommentId, long classNameId, long classPK,
		java.lang.String content, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateKBComment(kbCommentId, classNameId, classPK, content,
			status, serviceContext);
	}

	public static com.liferay.knowledge.base.model.KBComment updateKBComment(
		long kbCommentId, long classNameId, long classPK,
		java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateKBComment(kbCommentId, classNameId, classPK, content,
			serviceContext);
	}

	public static com.liferay.knowledge.base.model.KBComment updateStatus(
		long kbCommentId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateStatus(kbCommentId, status, serviceContext);
	}

	public static KBCommentService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KBCommentService, KBCommentService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(KBCommentService.class);

		ServiceTracker<KBCommentService, KBCommentService> serviceTracker = new ServiceTracker<KBCommentService, KBCommentService>(bundle.getBundleContext(),
				KBCommentService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}