/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.moderation.internal.kaleo.runtime.condition;

import com.liferay.message.boards.moderation.configuration.MBModerationGroupConfiguration;
import com.liferay.message.boards.service.MBStatsUserLocalService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoCondition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.condition.ConditionEvaluator;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "scripting.language=java", service = ConditionEvaluator.class
)
public class MBModerationConditionEvaluator implements ConditionEvaluator {

	@Override
	public String evaluate(
			KaleoCondition kaleoCondition, ExecutionContext executionContext)
		throws PortalException {

		Map<String, Serializable> workflowContext =
			executionContext.getWorkflowContext();

		long groupId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));

		MBModerationGroupConfiguration mbModerationGroupConfiguration =
			_configurationProvider.getGroupConfiguration(
				MBModerationGroupConfiguration.class, groupId);

		long userId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));

		if (_mbStatsUserLocalService.getMessageCountByUserId(userId) >=
				mbModerationGroupConfiguration.minimumContributedMessages()) {

			return "approve";
		}

		User user = _userLocalService.getUser(userId);

		for (String authorizedDomainName :
				mbModerationGroupConfiguration.authorizedDomainNames()) {

			if (Validator.isNotNull(authorizedDomainName) &&
				StringUtil.endsWith(
					user.getEmailAddress(), "@" + authorizedDomainName)) {

				return "approve";
			}
		}

		return "review";
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private MBStatsUserLocalService _mbStatsUserLocalService;

	@Reference
	private UserLocalService _userLocalService;

}