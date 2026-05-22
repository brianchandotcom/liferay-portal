/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.ai.hub.guardrail.ModelArmorTemplateHandler;
import com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.guardrail.ModelArmorInputGuardrail;
import com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.guardrail.ModelArmorOutputGuardrail;
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
import com.liferay.portal.kernel.util.Validator;
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
		ModelArmorTemplateHandler modelArmorTemplateHandler,
		ObjectEntryManager objectEntryManager,
		List<OutputGuardrail> outputGuardrails, ServiceContext serviceContext,
		Map<String, Serializable> workflowContext) {

		String agentDefinitionExternalReferenceCode = GetterUtil.getString(
			workflowContext.get("agentDefinitionExternalReferenceCode"));

		if (Validator.isNull(agentDefinitionExternalReferenceCode)) {
			return;
		}

		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getAndSetNestedFieldsContext(
				new NestedFieldsContext(
					1, List.of("agentDefinitionsToModelArmorTemplates")));

		try {
			long companyId = serviceContext.getCompanyId();

			ObjectEntry agentDefinitionObjectEntry =
				objectEntryManager.getObjectEntry(
					companyId,
					new DefaultDTOConverterContext(
						false, Map.of(), dtoConverterRegistry, null,
						serviceContext.getLocale(), null,
						UserLocalServiceUtil.getUserById(
							serviceContext.getUserId())),
					agentDefinitionExternalReferenceCode,
					ObjectDefinitionLocalServiceUtil.
						fetchObjectDefinitionByExternalReferenceCode(
							"L_AI_HUB_AGENT_DEFINITION", companyId),
					null);

			if (agentDefinitionObjectEntry == null) {
				return;
			}

			ObjectEntry[] modelArmorTemplateObjectEntries =
				(ObjectEntry[])agentDefinitionObjectEntry.getPropertyValue(
					"agentDefinitionsToModelArmorTemplates");

			if (ArrayUtil.isEmpty(modelArmorTemplateObjectEntries)) {
				return;
			}

			for (ObjectEntry modelArmorTemplateObjectEntry :
					modelArmorTemplateObjectEntries) {

				if (!GetterUtil.getBoolean(
						modelArmorTemplateObjectEntry.getPropertyValue(
							"active"))) {

					continue;
				}

				ListEntry guardrailType =
					(ListEntry)modelArmorTemplateObjectEntry.getPropertyValue(
						"guardrailType");

				if (guardrailType == null) {
					continue;
				}

				String guardrailTypeKey = guardrailType.getKey();

				if (Objects.equals(guardrailTypeKey, "input")) {
					inputGuardrails.add(
						new ModelArmorInputGuardrail(
							companyId,
							modelArmorTemplateObjectEntry.
								getExternalReferenceCode(),
							GetterUtil.getString(
								modelArmorTemplateObjectEntry.getPropertyValue(
									"location")),
							modelArmorTemplateHandler));
				}
				else if (Objects.equals(guardrailTypeKey, "output")) {
					outputGuardrails.add(
						new ModelArmorOutputGuardrail(
							companyId,
							modelArmorTemplateObjectEntry.
								getExternalReferenceCode(),
							GetterUtil.getString(
								modelArmorTemplateObjectEntry.getPropertyValue(
									"location")),
							modelArmorTemplateHandler));
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