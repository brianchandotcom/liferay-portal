/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal;

import com.liferay.ai.hub.site.initializer.internal.workflow.kaleo.runtime.node.ChangeToneTaskNodeExecutorAIDelegate;
import com.liferay.ai.hub.site.initializer.internal.workflow.kaleo.runtime.node.FixSpellingAndGrammarTaskNodeExecutorAIDelegate;
import com.liferay.ai.hub.site.initializer.internal.workflow.kaleo.runtime.node.ImproveWritingTaskNodeExecutorAIDelegate;
import com.liferay.ai.hub.site.initializer.internal.workflow.kaleo.runtime.node.MakeLongerTaskNodeExecutorAIDelegate;
import com.liferay.ai.hub.site.initializer.internal.workflow.kaleo.runtime.node.MakeShorterTaskNodeExecutorAIDelegate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "site.initializer.key=" + AIHubSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class AIHubSiteInitializer implements SiteInitializer {

	public static final String KEY = "ai-hub-initializer";

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "ai-hub");
	}

	@Override
	public String getThumbnailSrc() {
		return StringPool.BLANK;
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			_initialize(groupId);
		}
		catch (InitializationException initializationException) {
			throw initializationException;
		}
		catch (Exception exception) {
			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return FeatureFlagManagerUtil.isEnabled(companyId, "LPD-62272");
	}

	private void _deployWorkflowDefinition(
			Company company, String externalReferenceCode,
			String workflowDefinitionName, String workflowTaskName)
		throws Exception {

		int count = _workflowDefinitionManager.getWorkflowDefinitionsCount(
			company.getCompanyId(), workflowDefinitionName);

		if (count > 0) {
			return;
		}

		Map<String, String> titleMap = new HashMap<>();

		for (Locale locale :
				_language.getCompanyAvailableLocales(company.getCompanyId())) {

			titleMap.put(
				_language.getLanguageId(locale),
				_language.get(locale, workflowDefinitionName));
		}

		String json = StringUtil.replace(
			StringUtil.read(
				AIHubSiteInitializer.class.getResourceAsStream(
					"dependencies" +
						"/writing-assistant-workflow-definition.json.tpl")),
			new String[] {
				"__WORKFLOW_DEFINITION_NAME__", "__WORKFLOW_TASK_NAME__"
			},
			new String[] {workflowDefinitionName, workflowTaskName});

		_workflowDefinitionManager.deployWorkflowDefinition(
			externalReferenceCode, company.getCompanyId(),
			PrincipalThreadLocal.getUserId(),
			_localization.getXml(
				titleMap, _language.getLanguageId(company.getLocale()),
				"title"),
			workflowDefinitionName, json.getBytes());
	}

	private void _initialize(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		Company company = _companyLocalService.getCompany(group.getCompanyId());

		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_CHANGE_TONE,
			WorkflowDefinitionConstants.NAME_CHANGE_TONE,
			ChangeToneTaskNodeExecutorAIDelegate.KEY);
		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.
				EXTERNAL_REFERENCE_CODE_FIX_SPELLING_AND_GRAMMAR,
			WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR,
			FixSpellingAndGrammarTaskNodeExecutorAIDelegate.KEY);
		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_IMPROVE_WRITING,
			WorkflowDefinitionConstants.NAME_IMPROVE_WRITING,
			ImproveWritingTaskNodeExecutorAIDelegate.KEY);
		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_MAKE_LONGER,
			WorkflowDefinitionConstants.NAME_MAKE_LONGER,
			MakeLongerTaskNodeExecutorAIDelegate.KEY);
		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_MAKE_SHORTER,
			WorkflowDefinitionConstants.NAME_MAKE_SHORTER,
			MakeShorterTaskNodeExecutorAIDelegate.KEY);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}