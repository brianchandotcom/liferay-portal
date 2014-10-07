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

package com.liferay.polls.service.permission.impl;

import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.polls.service.permission.PollsQuestionPermission;
import com.liferay.polls.util.PollsPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Carlos Sierra
 */
public class PollsQuestionPermissionImpl implements PollsQuestionPermission {

	@Override
	public void check(
		PermissionChecker permissionChecker, long questionId,
		String actionId)
		throws PortalException {

		if (!contains(permissionChecker, questionId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
		PermissionChecker permissionChecker, PollsQuestion question,
		String actionId)
		throws PortalException {

		if (!contains(permissionChecker, question, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long questionId,
		String actionId)
		throws PortalException {

		PollsQuestion question = PollsQuestionLocalServiceUtil.getQuestion(
			questionId);

		return contains(permissionChecker, question, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, PollsQuestion question,
		String actionId) {

		GroupedModel groupedModel = (GroupedModel)question;

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, groupedModel.getGroupId(),
			PollsQuestion.class.getName(), question.getQuestionId(),
			PollsPortletKeys.POLLS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
			groupedModel.getCompanyId(), PollsQuestion.class.getName(),
			question.getQuestionId(), groupedModel.getUserId(),
			actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			groupedModel.getGroupId(), PollsQuestion.class.getName(),
			question.getQuestionId(), actionId);
	}

	@Override
	public void checkBaseModel(
		PermissionChecker permissionChecker, long groupId, long primaryKey,
		String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

}