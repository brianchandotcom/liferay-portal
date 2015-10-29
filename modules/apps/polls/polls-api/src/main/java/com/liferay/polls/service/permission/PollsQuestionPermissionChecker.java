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

package com.liferay.polls.service.permission;

import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.exportimport.staging.permission.StagingPermissionUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.polls.model.PollsQuestion"}
)
public class PollsQuestionPermissionChecker
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long questionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, questionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, PollsQuestion.class.getName(), questionId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, PollsQuestion question,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, question, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, PollsQuestion.class.getName(),
				question.getQuestionId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long questionId,
			String actionId)
		throws PortalException {

		PollsQuestion question = _pollsQuestionLocalService.getQuestion(
			questionId);

		return contains(permissionChecker, question, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, PollsQuestion question,
		String actionId) {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, question.getGroupId(),
			PollsQuestion.class.getName(), question.getQuestionId(),
			PollsPortletKeys.POLLS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				question.getCompanyId(), PollsQuestion.class.getName(),
				question.getQuestionId(), question.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			question.getGroupId(), PollsQuestion.class.getName(),
			question.getQuestionId(), actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setPollsQuestionLocalService(
		PollsQuestionLocalService pollsQuestionLocalService) {

		_pollsQuestionLocalService = pollsQuestionLocalService;
	}

	private static PollsQuestionLocalService _pollsQuestionLocalService;

}