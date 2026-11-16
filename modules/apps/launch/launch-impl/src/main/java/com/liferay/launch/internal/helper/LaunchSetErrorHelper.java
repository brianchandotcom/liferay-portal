/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.helper;

import com.liferay.launch.entry.manager.LaunchEntryManager;
import com.liferay.launch.entry.manager.LaunchEntryManagerRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Jose Luis Navarro
 */
public class LaunchSetErrorHelper {

	public LaunchSetErrorHelper(
		LaunchEntryManagerRegistry launchEntryManagerRegistry,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		UserLocalService userLocalService) {

		_launchEntryManagerRegistry = launchEntryManagerRegistry;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_userLocalService = userLocalService;
	}

	public void deleteLaunchSetErrors(ObjectEntry launchSetObjectEntry)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				launchSetObjectEntry.getObjectDefinitionId(),
				"launchSetToLaunchSetErrors");

		for (ObjectEntry launchSetErrorObjectEntry :
				_objectEntryLocalService.getOneToManyObjectEntries(
					launchSetObjectEntry.getGroupId(),
					objectRelationship.getObjectRelationshipId(), null, false,
					launchSetObjectEntry.getObjectEntryId(), true, null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			_objectEntryLocalService.deleteObjectEntry(
				launchSetErrorObjectEntry);
		}
	}

	public Map<String, Serializable> getLaunchSetErrorValues(
		ObjectEntry launchEntryObjectEntry, Throwable throwable) {

		long launchEntryId = 0;
		String launchEntryTitle = StringPool.BLANK;

		if (launchEntryObjectEntry != null) {
			launchEntryId = launchEntryObjectEntry.getObjectEntryId();
			launchEntryTitle = _getLaunchEntryTitle(launchEntryObjectEntry);
		}

		String message = throwable.getMessage();

		if (Validator.isNull(message)) {
			Class<?> clazz = throwable.getClass();

			message = clazz.getName();
		}

		return HashMapBuilder.<String, Serializable>put(
			"launchEntryId", launchEntryId
		).put(
			"launchEntryTitle", _shorten(launchEntryTitle)
		).put(
			"message", _shorten(message)
		).put(
			"severity", "error"
		).build();
	}

	public void markPublishFailed(
			List<Map<String, Serializable>> launchSetErrorValuesMaps,
			ObjectEntry launchSetObjectEntry, long userId)
		throws PortalException {

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_markPublishFailed(
						launchSetErrorValuesMaps, launchSetObjectEntry, userId);

					return null;
				});
		}
		catch (Throwable throwable) {
			if (throwable instanceof PortalException) {
				throw (PortalException)throwable;
			}

			throw new PortalException(throwable);
		}
	}

	private String _getLaunchEntryTitle(ObjectEntry launchEntryObjectEntry) {
		Map<String, Serializable> values = launchEntryObjectEntry.getValues();

		LaunchEntryManager launchEntryManager =
			_launchEntryManagerRegistry.getLaunchEntryManager(
				MapUtil.getString(values, "className"));

		if (launchEntryManager == null) {
			return StringPool.BLANK;
		}

		try {
			LaunchEntryManager.Item item = launchEntryManager.fetchItem(
				MapUtil.getLong(values, "classPK"));

			if (item == null) {
				return StringPool.BLANK;
			}

			return item.getTitle(LocaleUtil.getSiteDefault());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private long _getStatusByUserId(
			ObjectEntry launchSetObjectEntry, long userId)
		throws PortalException {

		if (_userLocalService.fetchUser(userId) != null) {
			return userId;
		}

		return _userLocalService.getGuestUserId(
			launchSetObjectEntry.getCompanyId());
	}

	private void _markPublishFailed(
			List<Map<String, Serializable>> launchSetErrorValuesMaps,
			ObjectEntry launchSetObjectEntry, long userId)
		throws PortalException {

		ObjectEntry currentLaunchSetObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				launchSetObjectEntry.getObjectEntryId());

		if (currentLaunchSetObjectEntry.isApproved()) {
			return;
		}

		deleteLaunchSetErrors(launchSetObjectEntry);

		long statusByUserId = _getStatusByUserId(launchSetObjectEntry, userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(launchSetObjectEntry.getCompanyId());
		serviceContext.setUserId(statusByUserId);

		if (!currentLaunchSetObjectEntry.isDraft()) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			_objectEntryLocalService.updateStatus(
				statusByUserId, launchSetObjectEntry.getObjectEntryId(),
				WorkflowConstants.STATUS_DRAFT, serviceContext);

			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				launchSetObjectEntry.getCompanyId(), "LaunchSetError");

		for (Map<String, Serializable> launchSetErrorValues :
				launchSetErrorValuesMaps) {

			_objectEntryLocalService.addObjectEntry(
				0, statusByUserId, objectDefinition.getObjectDefinitionId(), 0,
				null,
				HashMapBuilder.<String, Serializable>putAll(
					launchSetErrorValues
				).put(
					"r_launchSetToLaunchSetErrors_c_launchSetId",
					launchSetObjectEntry.getObjectEntryId()
				).build(),
				serviceContext);
		}
	}

	private String _shorten(String s) {
		if ((s == null) || (s.length() <= 65000)) {
			return s;
		}

		int endIndex = 65000;

		if (Character.isHighSurrogate(s.charAt(endIndex - 1))) {
			endIndex--;
		}

		return s.substring(0, endIndex);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LaunchSetErrorHelper.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	private final LaunchEntryManagerRegistry _launchEntryManagerRegistry;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final UserLocalService _userLocalService;

}