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

package com.liferay.message.boards.moderation.internal.configuration.persistence.listener;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.moderation.configuration.MBModerationGroupConfiguration;
import com.liferay.message.boards.moderation.internal.constants.MBModerationConstants;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.message.boards.moderation.configuration.MBModerationGroupConfiguration",
	service = ConfigurationModelListener.class
)
public class MBModerationGroupConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		MBModerationGroupConfiguration mbModerationGroupConfiguration =
			ConfigurableUtil.createConfigurable(
				MBModerationGroupConfiguration.class,
				new HashMapDictionary<>());

		long companyId = GetterUtil.getLong(properties.get("companyId"));
		boolean enableMessageBoardsModeration = GetterUtil.getBoolean(
			properties.get("enableMessageBoardsModeration"),
			mbModerationGroupConfiguration.enableMessageBoardsModeration());

		try {
			if (companyId == 0) {
				_companyLocalService.forEachCompanyId(
					curCompanyId -> _updateMBModerationWorkflow(
						curCompanyId, enableMessageBoardsModeration));
			}
			else {
				_updateMBModerationWorkflow(
					companyId, enableMessageBoardsModeration);
			}
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(), MBModerationGroupConfiguration.class,
				getClass(), properties);
		}
	}

	private void _updateMBModerationWorkflow(
			long companyId, boolean enableMessageBoardsModeration)
		throws Exception {

		if (!enableMessageBoardsModeration) {
			WorkflowDefinitionLink workflowDefinitionLink =
				_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
					companyId, 0, MBMessage.class.getName(), 0, 0);

			if (workflowDefinitionLink != null) {
				_workflowDefinitionLinkLocalService.
					deleteWorkflowDefinitionLink(workflowDefinitionLink);
			}

			return;
		}

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				companyId, MBModerationConstants.WORKFLOW_DEFINITION_NAME);

		_workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
			workflowDefinition.getUserId(), companyId, 0,
			MBMessage.class.getName(), 0, 0,
			MBModerationConstants.WORKFLOW_DEFINITION_NAME,
			workflowDefinition.getVersion());
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}