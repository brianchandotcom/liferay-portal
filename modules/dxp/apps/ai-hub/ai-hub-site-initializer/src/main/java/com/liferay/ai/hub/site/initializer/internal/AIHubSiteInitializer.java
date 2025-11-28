/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal;

import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.FrameworkUtil;
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
			_deployObjectDefinition();
			_deployWorkflowDefinitions(groupId);
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

	private void _deployObjectDefinition() throws Exception {
		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource = builder.user(
			_userLocalService.getUser(PrincipalThreadLocal.getUserId())
		).build();

		try {
			BatchEngineUnitThreadLocal.setFileName(
				String.valueOf(
					FrameworkUtil.getBundle(AIHubSiteInitializer.class)));

			objectDefinitionResource.putObjectDefinitionByExternalReferenceCode(
				"L_MCP_SERVER",
				ObjectDefinition.toDTO(
					StringUtil.read(
						AIHubSiteInitializer.class.getResourceAsStream(
							"dependencies/mcp-server-object-" +
								"definition.json"))));
		}
		finally {
			BatchEngineUnitThreadLocal.setFileName(StringPool.BLANK);
		}
	}

	private void _deployWorkflowDefinition(
			Company company, String externalReferenceCode,
			String workflowDefinitionName, String workflowNodeInputVariables,
			String workflowNodeName, String workflowNodeOutputVariables,
			String workflowNodeSettingPrompt,
			String workflowNodeSettingUserMessage)
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
					"dependencies/workflow-definition.json.tpl")),
			new String[] {
				"[$WORKFLOW_DEFINITION_NAME$]",
				"[$WORKFLOW_NODE_INPUT_VARIABLES$]", "[$WORKFLOW_NODE_NAME$]",
				"[$WORKFLOW_NODE_OUTPUT_VARIABLES$]",
				"[$WORKFLOW_NODE_SETTING_PROMPT$]",
				"[$WORKFLOW_NODE_SETTING_USER_MESSAGE$]"
			},
			new String[] {
				workflowDefinitionName, workflowNodeInputVariables,
				workflowNodeName, workflowNodeOutputVariables,
				workflowNodeSettingPrompt, workflowNodeSettingUserMessage
			});

		_workflowDefinitionManager.deployWorkflowDefinition(
			externalReferenceCode, company.getCompanyId(),
			PrincipalThreadLocal.getUserId(),
			_localization.getXml(
				titleMap, _language.getLanguageId(company.getLocale()),
				"title"),
			workflowDefinitionName, "ai", json.getBytes());
	}

	private void _deployWorkflowDefinitions(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		Company company = _companyLocalService.getCompany(group.getCompanyId());

		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_CHANGE_TONE,
			WorkflowDefinitionConstants.NAME_CHANGE_TONE,
			"[{\\\"name\\\": \\\"text\\\",\\\"type\\\": \\\"string\\\"}" +
				",{\\\"name\\\": \\\"tone\\\",\\\"type\\\": \\\"string\\\"}]",
			"changeTone",
			"[{\\\"name\\\": \\\"rewrittenText\\\",\\\"type" +
				"\\\": \\\"string\\\"}]",
			StringBundler.concat(
				"You are an expert linguistic editor. Your sole task is to ",
				"adjust the tone of the provided text to be more {{tone}}. ",
				"Modify vocabulary, phrasing, and sentence structure as ",
				"needed while preserving the original meaning, intent, and ",
				"clarity. If the text already matches this tone, return it ",
				"unchanged. Output only the rewritten text, with no ",
				"explanations or commentary."),
			"This is the text whose tone was changed to be {{tone}}: {{text}}");
		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.
				EXTERNAL_REFERENCE_CODE_CHAT_MESSAGE_PIPELINE,
			WorkflowDefinitionConstants.NAME_CHAT_MESSAGE_PIPELINE,
			StringBundler.concat(
				"[{\\\"name\\\": \\\"content\\\",\\\"type\\\": \\\"string\\\"}",
				",{\\\"name\\\": \\\"title\\\",\\\"type\\\": \\\"string\\\"},{",
				"\\\"name\\\": \\\"userMessage\\\",\\\"type\\\": \\\"string",
				"\\\"}]"),
			"chatMessageHandler",
			"[{\\\"name\\\": \\\"generatedContent\\\"," +
				"\\\"type\\\": \\\"string\\\"}]",
			StringBundler.concat(
				"You are a highly helpful and context-aware chat assistant. ",
				"The context you are aware is the content and the title, they ",
				"can be present or not and they will be passed during the ",
				"conversation because they are dynamically updated. Maintain ",
				"a friendly and professional conversational tone, and use the ",
				"chat history to maintain flow."),
			"This is the content: \\\"{{content}}\\\" and title: " +
				"\\\"{{title}}\\\", reply to this message: " +
					"\\\"{{userMessage}}\\\"");
		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.
				EXTERNAL_REFERENCE_CODE_FIX_SPELLING_AND_GRAMMAR,
			WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR,
			"[{\\\"name\\\": \\\"text\\\",\\\"type\\\": \\\"string\\\"}]",
			"fixSpellingAndGrammar",
			"[{\\\"name\\\": \\\"rewrittenText\\\",\\\"type" +
				"\\\": \\\"string\\\"}]",
			StringBundler.concat(
				"You are an expert linguistic editor. Your sole task is to ",
				"correct all grammatical, spelling, and punctuation errors in ",
				"the provided text while preserving its meaning, tone, and ",
				"style. Do not alter structure or wording beyond what is ",
				"necessary for grammatical precision and natural fluency. ",
				"Output only the corrected text, with no explanations or ",
				"commentary. If the text is already correct, return it ",
				"unchanged."),
			"This is the text to be fixed: {{text}}");
		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_IMPROVE_WRITING,
			WorkflowDefinitionConstants.NAME_IMPROVE_WRITING,
			"[{\\\"name\\\": \\\"text\\\",\\\"type\\\": \\\"string\\\"}]",
			"improveWriting",
			"[{\\\"name\\\": \\\"rewrittenText\\\",\\\"type" +
				"\\\": \\\"string\\\"}]",
			StringBundler.concat(
				"You are a professional writing editor. Your sole task is to ",
				"take the provided text and rewrite it to be significantly ",
				"more concise, direct, and free of unnecessary filler words, ",
				"nominalizations, and passive voice, while retaining the ",
				"original meaning and professional tone. Only output the ",
				"revised, concise text. Do not include any explanation, ",
				"introduction, or conversation."),
			"This is the text to be rewritten: {{text}}");
		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_MAKE_LONGER,
			WorkflowDefinitionConstants.NAME_MAKE_LONGER,
			"[{\\\"name\\\": \\\"text\\\",\\\"type\\\": \\\"string\\\"}]",
			"makeLonger",
			"[{\\\"name\\\": \\\"rewrittenText\\\",\\\"type" +
				"\\\": \\\"string\\\"}]",
			StringBundler.concat(
				"You are an expert linguistic enhancer. Expand the provided ",
				"text by adding relevant and natural details that clarify or ",
				"enrich its meaning. Keep the original tone, intent, and ",
				"structure. Avoid unnecessary embellishment, repetition, or ",
				"creative exaggeration. Output only the expanded text."),
			"This is the text to be detailed: {{text}}");
		_deployWorkflowDefinition(
			company,
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_MAKE_SHORTER,
			WorkflowDefinitionConstants.NAME_MAKE_SHORTER,
			"[{\\\"name\\\": \\\"text\\\",\\\"type\\\": \\\"string\\\"}]",
			"makeShorter",
			"[{\\\"name\\\": \\\"rewrittenText\\\",\\\"type" +
				"\\\": \\\"string\\\"}]",
			StringBundler.concat(
				"You are an expert linguistic editor. Your sole task is to ",
				"reduce the length of the provided text while preserving all ",
				"essential information, key points, and original intent. ",
				"Remove redundancy, filler, and unnecessary detail without ",
				"changing meaning, tone, or clarity. If the text is already ",
				"concise and cannot be shortened without losing important ",
				"content, return it unchanged. Output only the shortened ",
				"text, with no explanations or commentary."),
			"This is the text to be shortened: {{text}}");
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
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}