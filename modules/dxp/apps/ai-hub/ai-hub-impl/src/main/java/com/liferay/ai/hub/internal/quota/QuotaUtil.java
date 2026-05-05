/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.quota;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.CountTokensResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;

import com.liferay.account.model.AccountEntry;
import com.liferay.ai.hub.internal.configuration.VertexAIConfiguration;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.IOException;
import java.io.Serializable;

import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class QuotaUtil {

	public static void checkUsage(long companyId, String text, long userId)
		throws PortalException {

		ObjectEntry objectEntry = _fetchQuotaObjectEntry(companyId, userId);

		if (objectEntry == null) {
			return;
		}

		VertexAIConfiguration vertexAIConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		long tokensCount = 0L;

		try (VertexAI vertexAI = new VertexAI(
				vertexAIConfiguration.projectId(),
				vertexAIConfiguration.location())) {

			GenerativeModel generativeModel = new GenerativeModel(
				vertexAIConfiguration.modelName(), vertexAI);

			CountTokensResponse countTokensResponse =
				generativeModel.countTokens(text);

			tokensCount = countTokensResponse.getTotalTokens();
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}

		Map<String, Serializable> values = objectEntry.getValues();

		if ((GetterUtil.getLong(values.get("usage")) + tokensCount) >
				GetterUtil.getLong(values.get("limit"))) {

			throw new UnsupportedOperationException(
				"You have exceeded your token quota");
		}

		updateUsage(companyId, tokensCount, userId);
	}

	public static void updateUsage(
			long companyId, long tokensCount, long userId)
		throws PortalException {

		ObjectEntry objectEntry = _fetchQuotaObjectEntry(companyId, userId);

		if (objectEntry == null) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		ObjectEntryLocalServiceUtil.partialUpdateObjectEntry(
			userId, objectEntry.getObjectEntryId(), 0,
			HashMapBuilder.<String, Serializable>put(
				"usage",
				() -> {
					long usage = MapUtil.getLong(
						objectEntry.getValues(), "usage");

					return usage + tokensCount;
				}
			).build(),
			serviceContext);
	}

	private static ObjectEntry _fetchQuotaObjectEntry(
			long companyId, long userId)
		throws PortalException {

		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			userId);

		if (accountEntry == null) {
			return null;
		}

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_QUOTA", companyId);

		if (objectDefinition == null) {
			return null;
		}

		User user = UserLocalServiceUtil.getUser(userId);

		String externalReferenceCode =
			"quota-" + accountEntry.getAccountEntryId();

		if (user.isServiceAccountUser()) {
			externalReferenceCode =
				"guest-quota-" + accountEntry.getAccountEntryId();
		}

		return ObjectEntryLocalServiceUtil.fetchObjectEntry(
			externalReferenceCode, 0, objectDefinition.getObjectDefinitionId());
	}

}