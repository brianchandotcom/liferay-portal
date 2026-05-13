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
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.IOException;
import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

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

		_updateUsage(
			objectEntry.getObjectEntryId(),
			() -> {
				ObjectEntry currentObjectEntry =
					ObjectEntryLocalServiceUtil.getObjectEntry(
						objectEntry.getObjectEntryId());

				Map<String, Serializable> values =
					currentObjectEntry.getValues();

				long currentUsage = GetterUtil.getLong(values.get("usage"));

				long limit = GetterUtil.getLong(values.get("limit"));

				if ((currentUsage + tokensCount) > limit) {
					throw new UnsupportedOperationException(
						"You have exceeded your token quota");
				}

				_persistUsage(
					companyId, currentObjectEntry, currentUsage + tokensCount,
					userId);
			});
	}

	public static void updateUsage(
			long companyId, long tokensCount, long userId)
		throws PortalException {

		ObjectEntry objectEntry = _fetchQuotaObjectEntry(companyId, userId);

		if (objectEntry == null) {
			return;
		}

		_updateUsage(
			objectEntry.getObjectEntryId(),
			() -> {
				ObjectEntry currentObjectEntry =
					ObjectEntryLocalServiceUtil.getObjectEntry(
						objectEntry.getObjectEntryId());

				long currentUsage = GetterUtil.getLong(
					currentObjectEntry.getValues(
					).get(
						"usage"
					));

				_persistUsage(
					companyId, currentObjectEntry, currentUsage + tokensCount,
					userId);
			});
	}

	private static void _acquireLock(String className, String key, String owner)
		throws PortalException {

		long deadline =
			System.currentTimeMillis() + _LOCK_ACQUIRE_TIMEOUT_MILLIS;

		while (true) {
			Lock lock = LockManagerUtil.lock(className, key, null, owner);

			if (Objects.equals(lock.getOwner(), owner)) {
				return;
			}

			if (System.currentTimeMillis() >= deadline) {
				throw new PortalException("Timed out acquiring quota lock");
			}

			try {
				Thread.sleep(_LOCK_RETRY_INTERVAL_MILLIS);
			}
			catch (InterruptedException interruptedException) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();

				throw new PortalException(
					"Interrupted while waiting for quota lock",
					interruptedException);
			}
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

	private static String _getLockKey(long objectEntryId) {
		return "ai-hub-quota-" + objectEntryId;
	}

	private static void _persistUsage(
			long companyId, ObjectEntry objectEntry, long newUsage, long userId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		ObjectEntryLocalServiceUtil.partialUpdateObjectEntry(
			userId, objectEntry.getObjectEntryId(), 0,
			HashMapBuilder.<String, Serializable>put(
				"usage", newUsage
			).build(),
			serviceContext);
	}

	private static void _updateUsage(
			long objectEntryId, UnsafeRunnable<PortalException> unsafeRunnable)
		throws PortalException {

		String className = QuotaUtil.class.getName();
		String key = _getLockKey(objectEntryId);
		String owner = PortalUUIDUtil.generate();

		_acquireLock(className, key, owner);

		try {
			unsafeRunnable.run();
		}
		finally {
			LockManagerUtil.unlock(className, key, owner);
		}
	}

	private static final long _LOCK_ACQUIRE_TIMEOUT_MILLIS = 10_000L;

	private static final long _LOCK_RETRY_INTERVAL_MILLIS = 50L;

}