/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.ai.hub.guardrail.ModelArmorHandler;
import com.liferay.ai.hub.internal.langchain4j.guardrail.InputGuardrailImpl;
import com.liferay.ai.hub.internal.langchain4j.guardrail.OutputGuardrailImpl;
import com.liferay.ai.hub.quota.QuotaManager;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;

import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrail;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author João Victor Alves
 */
public class GuardrailsUtil {

	public static void populate(
		DTOConverterRegistry dtoConverterRegistry,
		List<InputGuardrail> inputGuardrails,
		ModelArmorHandler modelArmorHandler,
		ObjectEntryManager objectEntryManager,
		List<OutputGuardrail> outputGuardrails, QuotaManager quotaManager,
		ServiceContext serviceContext,
		Map<String, Serializable> workflowContext) {

		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getAndSetNestedFieldsContext(
				new NestedFieldsContext(
					1, List.of("aiHubAgentDefinitionsToAIHubGuardrails")));

		try {
			ObjectEntry agentDefinitionObjectEntry =
				objectEntryManager.getObjectEntry(
					serviceContext.getCompanyId(),
					new DefaultDTOConverterContext(
						false, Map.of(), dtoConverterRegistry, null,
						serviceContext.getLocale(), null,
						UserLocalServiceUtil.getUserById(
							serviceContext.getUserId())),
					GetterUtil.getString(
						workflowContext.get(
							"agentDefinitionExternalReferenceCode")),
					ObjectDefinitionLocalServiceUtil.
						fetchObjectDefinitionByExternalReferenceCode(
							"L_AI_HUB_AGENT_DEFINITION",
							serviceContext.getCompanyId()),
					null);

			ObjectEntry[] guardrailObjectEntries =
				(ObjectEntry[])agentDefinitionObjectEntry.getPropertyValue(
					"aiHubAgentDefinitionsToAIHubGuardrails");

			if (ArrayUtil.isEmpty(guardrailObjectEntries)) {
				return;
			}

			for (ObjectEntry guardrailObjectEntry : guardrailObjectEntries) {
				if (!GetterUtil.getBoolean(
						guardrailObjectEntry.getPropertyValue("active"))) {

					continue;
				}

				ListEntry listEntry =
					(ListEntry)guardrailObjectEntry.getPropertyValue(
						"guardrailType");

				if (Objects.equals(listEntry.getKey(), "input")) {
					inputGuardrails.add(
						new InputGuardrailImpl(
							serviceContext.getCompanyId(),
							guardrailObjectEntry.getExternalReferenceCode(),
							GetterUtil.getString(
								guardrailObjectEntry.getPropertyValue(
									"location")),
							modelArmorHandler, quotaManager,
							serviceContext.getUserId(), workflowContext));
				}
				else if (Objects.equals(listEntry.getKey(), "output")) {
					outputGuardrails.add(
						new OutputGuardrailImpl(
							serviceContext.getCompanyId(),
							guardrailObjectEntry.getExternalReferenceCode(),
							GetterUtil.getString(
								guardrailObjectEntry.getPropertyValue(
									"location")),
							modelArmorHandler, quotaManager,
							serviceContext.getUserId(), workflowContext));
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
		finally {
			NestedFieldsContextThreadLocal.setNestedFieldsContext(
				nestedFieldsContext);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(GuardrailsUtil.class);

}