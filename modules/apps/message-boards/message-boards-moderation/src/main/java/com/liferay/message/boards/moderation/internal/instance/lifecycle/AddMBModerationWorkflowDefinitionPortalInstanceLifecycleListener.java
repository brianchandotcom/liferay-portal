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

package com.liferay.message.boards.moderation.internal.instance.lifecycle;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.moderation.internal.constants.MBModerationConstants;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true, property = "service.ranking:Integer=100",
	service = PortalInstanceLifecycleListener.class
)
public class AddMBModerationWorkflowDefinitionPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		int workflowDefinitionsCount =
			_workflowDefinitionManager.getWorkflowDefinitionsCount(
				company.getCompanyId(),
				MBModerationConstants.WORKFLOW_DEFINITION_NAME);

		if (workflowDefinitionsCount > 0) {
			return;
		}

		long defaultUserId = _userLocalService.getDefaultUserId(
			company.getCompanyId());

		String content = StringUtil.read(
			AddMBModerationWorkflowDefinitionPortalInstanceLifecycleListener.
				class,
			"dependencies/message-boards-moderation-workflow-definition.xml");

		_workflowDefinitionManager.deployWorkflowDefinition(
			company.getCompanyId(), defaultUserId,
			_localization.getXml(
				_getTitleMap(company.getCompanyId()),
				_language.getLanguageId(company.getLocale()), "title"),
			MBModerationConstants.WORKFLOW_DEFINITION_NAME,
			MBMessage.class.getName(), content.getBytes());
	}

	private Map<String, String> _getTitleMap(long companyId) {
		Map<String, String> titleMap = new HashMap<>();

		for (Locale locale : _language.getCompanyAvailableLocales(companyId)) {
			titleMap.put(
				_language.getLanguageId(locale),
				_language.get(
					locale, MBModerationConstants.WORKFLOW_DEFINITION_NAME));
		}

		return titleMap;
	}

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}