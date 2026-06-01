/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.util;

import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Fábio Alves
 */
public class ContextUserUtil {

	public static User initContextUser(
			String chatbotExternalReferenceCode, long companyId, User user)
		throws Exception {

		if (!user.isGuestUser() ||
			Validator.isNull(chatbotExternalReferenceCode)) {

			return user;
		}

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				getObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_CHATBOT", companyId);

		ObjectEntry objectEntry = ObjectEntryLocalServiceUtil.getObjectEntry(
			chatbotExternalReferenceCode, 0L,
			objectDefinition.getObjectDefinitionId());

		for (AccountEntryUserRel accountEntryUserRel :
				AccountEntryUserRelLocalServiceUtil.
					getAccountEntryUserRelsByAccountEntryId(
						MapUtil.getLong(
							objectEntry.getValues(),
							"r_accountToAIHubChatbots_accountEntryId"))) {

			User accountEntryUserRelUser = accountEntryUserRel.getUser();

			if (!accountEntryUserRelUser.isServiceAccountUser()) {
				continue;
			}

			AccessControlUtil.initContextUser(
				accountEntryUserRelUser.getUserId());

			return accountEntryUserRelUser;
		}

		throw new UnsupportedOperationException();
	}

}