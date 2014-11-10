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

package com.liferay.polls.service.permission;

import com.liferay.polls.model.PollsQuestion;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.facet.util.FacetValueValidator;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
public class PollsQuestionPermissionUtil  {

	public static void check(
			PermissionChecker permissionChecker, long questionId,
			String actionId)
		throws PortalException {

		getPollsQuestionPermission().check(
			permissionChecker, questionId, actionId);

	}

	public static PollsQuestionPermission getPollsQuestionPermission() {
		return _pollsQuestionPermissionServiceTracker.getService();
	}

	public static void check(
			PermissionChecker permissionChecker, PollsQuestion question,
			String actionId)
		throws PortalException {

		getPollsQuestionPermission().check(
			permissionChecker, question, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long questionId,
			String actionId)
		throws PortalException {

		return getPollsQuestionPermission().contains(
			permissionChecker, questionId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, PollsQuestion question,
		String actionId) {

		return getPollsQuestionPermission().contains(
			permissionChecker, question, actionId);
	}

	private static
		ServiceTracker<PollsQuestionPermission, PollsQuestionPermission>
			_pollsQuestionPermissionServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			PollsQuestionPermissionUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_pollsQuestionPermissionServiceTracker = new ServiceTracker<>(
			bundleContext, PollsQuestionPermission.class, null);

		_pollsQuestionPermissionServiceTracker.open();
	}
}
