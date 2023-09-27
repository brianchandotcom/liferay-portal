/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.assignment;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.workflow.kaleo.KaleoTaskAssignmentFactory;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.constants.AssigneeConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class BaseKaleoTaskAssignmentSelector
	implements KaleoTaskAssignmentSelector {

	@SuppressWarnings("unchecked")
	protected Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
		Map<String, ?> results) {

		List<KaleoTaskAssignment> kaleoTaskAssignments = new ArrayList<>();

		if (results.get(AssigneeConstants.ROLES) != null) {
			getRoleKaleoTaskAssignments(
				(List<Role>)results.get(AssigneeConstants.ROLES),
				kaleoTaskAssignments);

			return kaleoTaskAssignments;
		}

		if (results.get(AssigneeConstants.USER) != null) {
			kaleoTaskAssignments.add(
				getUserKaleoTaskAssignment(
					(User)results.get(AssigneeConstants.USER)));

			return kaleoTaskAssignments;
		}

		for (User user : (List<User>)results.get(AssigneeConstants.USERS)) {
			kaleoTaskAssignments.add(getUserKaleoTaskAssignment(user));
		}

		return kaleoTaskAssignments;
	}

	protected void getRoleKaleoTaskAssignments(
		List<Role> roles, List<KaleoTaskAssignment> kaleoTaskAssignments) {

		if (roles == null) {
			return;
		}

		for (Role role : roles) {
			KaleoTaskAssignment kaleoTaskAssignment =
				kaleoTaskAssignmentFactory.createKaleoTaskAssignment();

			kaleoTaskAssignment.setAssigneeClassName(Role.class.getName());
			kaleoTaskAssignment.setAssigneeClassPK(role.getRoleId());

			kaleoTaskAssignments.add(kaleoTaskAssignment);
		}
	}

	protected KaleoTaskAssignment getUserKaleoTaskAssignment(User user) {
		KaleoTaskAssignment kaleoTaskAssignment =
			kaleoTaskAssignmentFactory.createKaleoTaskAssignment();

		kaleoTaskAssignment.setAssigneeClassName(User.class.getName());
		kaleoTaskAssignment.setAssigneeClassPK(user.getUserId());

		return kaleoTaskAssignment;
	}

	@Reference
	protected KaleoTaskAssignmentFactory kaleoTaskAssignmentFactory;

}