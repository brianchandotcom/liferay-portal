/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.ai.hub.agent.AgentContext;
import com.liferay.ai.hub.agent.SupervisorAgent;
import com.liferay.ai.hub.rest.dto.v1_0.Message;
import com.liferay.ai.hub.rest.resource.v1_0.MessageResource;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Feliphe Marinho
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/message.properties",
	scope = ServiceScope.PROTOTYPE, service = MessageResource.class
)
public class MessageResourceImpl extends BaseMessageResourceImpl {

	@Override
	public Message postChatByExternalReferenceCodeMessage(
			String externalReferenceCode, Message message)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		User user = _initContextUser(
			message.getChatbotExternalReferenceCode(),
			contextCompany.getCompanyId(), contextUser);

		_supervisorAgent.invoke(
			AgentContext.builder(
			).accessToken(
				contextHttpServletRequest.getHeader("Authorization")
			).chatbotExternalReferenceCode(
				message.getChatbotExternalReferenceCode()
			).companyId(
				contextCompany.getCompanyId()
			).dtoConverterContext(
				new DefaultDTOConverterContext(
					contextAcceptLanguage.isAcceptAllLanguages(), null,
					_dtoConverterRegistry, contextHttpServletRequest, null,
					contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
					user)
			).groupId(
				AccountEntryUtil.getUserAccountEntryGroupId(user.getUserId())
			).input(
				Map.of("message", message.getText())
			).instructionDefinitionScope(
				message.getInstructionDefinitionScopeAsString()
			).serviceContext(
				ServiceContextFactory.getInstance(contextHttpServletRequest)
			).sseEventSinkKey(
				externalReferenceCode
			).userId(
				user.getUserId()
			).userToken(
				contextHttpServletRequest.getHeader(
					"Liferay-AI-Hub-Cell-On-Behalf-Of")
			).build());

		return message;
	}

	private User _initContextUser(
			String chatbotExternalReferenceCode, long companyId, User user)
		throws Exception {

		if (!user.isGuestUser() ||
			Validator.isNull(chatbotExternalReferenceCode)) {

			return user;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_CHATBOT", companyId);

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			chatbotExternalReferenceCode, 0L,
			objectDefinition.getObjectDefinitionId());

		for (AccountEntryUserRel accountEntryUserRel :
				_accountEntryUserRelLocalService.
					getAccountEntryUserRelsByAccountEntryId(
						MapUtil.getLong(
							objectEntry.getValues(),
							"r_accountToAIHubChatbots_accountEntryId"))) {

			User accountEntryUserRelUser = accountEntryUserRel.getUser();

			if (accountEntryUserRelUser.isServiceAccountUser()) {
				AccessControlUtil.initContextUser(
					accountEntryUserRelUser.getUserId());

				return accountEntryUserRelUser;
			}
		}

		return user;
	}

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private SupervisorAgent _supervisorAgent;

}