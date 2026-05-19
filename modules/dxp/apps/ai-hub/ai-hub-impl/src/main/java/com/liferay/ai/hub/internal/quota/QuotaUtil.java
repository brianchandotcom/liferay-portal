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
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

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

		long tokensCount = _countTokens(companyId, text);

		try (Closeable closeable = _lock(objectEntry.getObjectEntryId())) {
			objectEntry = ObjectEntryLocalServiceUtil.getObjectEntry(
				objectEntry.getObjectEntryId());

			long usage =
				MapUtil.getLong(objectEntry.getValues(), "usage") + tokensCount;

			if (usage > MapUtil.getLong(objectEntry.getValues(), "limit")) {
				throw new UnsupportedOperationException(
					"You have exceeded your token quota");
			}

			_partialUpdateObjectEntry(companyId, objectEntry, usage, userId);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static void updateUsage(
			long companyId, long tokensCount, long userId)
		throws PortalException {

		ObjectEntry objectEntry = _fetchQuotaObjectEntry(companyId, userId);

		if (objectEntry == null) {
			return;
		}

		try (Closeable closeable = _lock(objectEntry.getObjectEntryId())) {
			objectEntry = ObjectEntryLocalServiceUtil.getObjectEntry(
				objectEntry.getObjectEntryId());

			_partialUpdateObjectEntry(
				companyId, objectEntry,
				MapUtil.getLong(objectEntry.getValues(), "usage") + tokensCount,
				userId);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static long _countTokens(long companyId, String text)
		throws PortalException {

		VertexAIConfiguration vertexAIConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		String location = vertexAIConfiguration.location();
		String modelName = vertexAIConfiguration.modelName();

		if (Objects.equals(location, "global")) {
			location = "europe-central2";
			modelName = "gemini-2.5-flash";
		}

		try (VertexAI vertexAI = new VertexAI(
				vertexAIConfiguration.projectId(), location)) {

			GenerativeModel generativeModel = new GenerativeModel(
				modelName, vertexAI);

			CountTokensResponse countTokensResponse =
				generativeModel.countTokens(text);

			return countTokensResponse.getTotalTokens();
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
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

	private static Closeable _lock(long objectEntryId) throws PortalException {
		String updatedOwner = PortalUUIDUtil.generate();

		long deadline = System.currentTimeMillis() + (10 * Time.SECOND);

		while (true) {
			Lock lock = LockManagerUtil.lock(
				QuotaUtil.class.getName(), String.valueOf(objectEntryId), null,
				updatedOwner);

			if (Objects.equals(lock.getOwner(), updatedOwner)) {
				break;
			}

			if (System.currentTimeMillis() >= deadline) {
				throw new PortalException(new TimeoutException());
			}

			try {
				Thread.sleep(50);
			}
			catch (InterruptedException interruptedException) {
				Thread thread = Thread.currentThread();

				thread.interrupt();

				throw new PortalException(interruptedException);
			}
		}

		return () -> LockManagerUtil.unlock(
			QuotaUtil.class.getName(), String.valueOf(objectEntryId),
			updatedOwner);
	}

	private static void _partialUpdateObjectEntry(
			long companyId, ObjectEntry objectEntry, long usage, long userId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		ObjectEntryLocalServiceUtil.partialUpdateObjectEntry(
			userId, objectEntry.getObjectEntryId(), 0,
			HashMapBuilder.<String, Serializable>put(
				"usage", usage
			).build(),
			serviceContext);
	}

}