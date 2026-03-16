/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.nexus.rest.internal.resource.v1_0.util;

import com.liferay.ai.hub.nexus.rest.dto.v1_0.Scope;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Feliphe Marinho
 */
public class GroupUtil {

	public static long getGroupId(
			long companyId, GroupService groupService, Scope scope)
		throws PortalException {

		if (scope == null) {
			return WorkflowConstants.DEFAULT_GROUP_ID;
		}

		Group group = groupService.fetchGroupByExternalReferenceCode(
			scope.getExternalReferenceCode(), companyId);

		if (group == null) {
			return WorkflowConstants.DEFAULT_GROUP_ID;
		}

		return group.getGroupId();
	}

}