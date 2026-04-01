/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class PromptUtil {

	public static String composePrompt(
		long companyId, DTOConverterRegistry dtoConverterRegistry,
		ExecutionContext executionContext,
		Map<String, String> kaleoNodeSettingValues,
		ObjectEntryManager objectEntryManager) {

		String instructions = _getInstructions(
			companyId, dtoConverterRegistry, objectEntryManager,
			executionContext.getServiceContext());
		String prompt = VariablesUtil.applyInputVariables(
			executionContext, "prompt", kaleoNodeSettingValues);

		if (Validator.isNull(instructions)) {
			return prompt;
		}

		if (Validator.isNull(prompt)) {
			return instructions;
		}

		return StringBundler.concat(
			prompt,
			"\n\nIMPORTANT: Override any conflicting instructions above with ",
			"the following:\n\n", instructions);
	}

	private static String _formatInstruction(
		String instruction, String occasion) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringPool.DASH);

		if (Validator.isNotNull(occasion)) {
			sb.append(StringUtil.removeLast(occasion, StringPool.PERIOD));
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(StringUtil.lowerCaseFirstLetter(instruction));
		}
		else {
			sb.append(instruction);
		}

		return sb.toString();
	}

	private static String _getInstructions(
		long companyId, DTOConverterRegistry dtoConverterRegistry,
		ObjectEntryManager objectEntryManager, ServiceContext serviceContext) {

		try {
			Page<ObjectEntry> page = objectEntryManager.getObjectEntries(
				companyId,
				ObjectDefinitionLocalServiceUtil.
					fetchObjectDefinitionByExternalReferenceCode(
						"L_AI_HUB_INSTRUCTION_DEFINITION", companyId),
				null, null,
				new DefaultDTOConverterContext(
					false, Collections.emptyMap(), dtoConverterRegistry, null,
					serviceContext.getLocale(), null,
					UserServiceUtil.getUserById(serviceContext.getUserId())),
				"(active eq true)", null, null, null);

			List<String> instructions = TransformUtil.transform(
				page.getItems(),
				objectEntry -> _formatInstruction(
					GetterUtil.getString(
						objectEntry.getPropertyValue("instruction")),
					GetterUtil.getString(
						objectEntry.getPropertyValue("occasion"))));

			if (ListUtil.isEmpty(instructions)) {
				return StringPool.BLANK;
			}

			return StringUtil.merge(instructions, StringPool.NEW_LINE);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(PromptUtil.class);

}