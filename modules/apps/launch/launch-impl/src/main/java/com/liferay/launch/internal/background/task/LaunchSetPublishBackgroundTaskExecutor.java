/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.background.task;

import com.liferay.launch.entry.manager.LaunchEntryManager;
import com.liferay.launch.entry.manager.LaunchEntryManagerRegistry;
import com.liferay.launch.internal.helper.LaunchSetErrorHelper;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jose Luis Navarro
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.launch.internal.background.task.LaunchSetPublishBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class LaunchSetPublishBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public LaunchSetPublishBackgroundTaskExecutor() {
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		long launchSetId = MapUtil.getLong(
			backgroundTask.getTaskContextMap(), "launchSetId");

		ObjectEntry launchSetObjectEntry = null;

		try {
			launchSetObjectEntry = _objectEntryLocalService.getObjectEntry(
				launchSetId);
		}
		catch (PortalException portalException) {
			String message = StringBundler.concat(
				"Unable to publish launch set ", launchSetId, ": ",
				portalException.getMessage());

			_log.error(message, portalException);

			return new BackgroundTaskResult(
				BackgroundTaskConstants.STATUS_FAILED, message);
		}

		if (launchSetObjectEntry.getStatus() ==
				WorkflowConstants.STATUS_APPROVED) {

			String message = StringBundler.concat(
				"Launch set ", launchSetId, " was already published");

			_log.error(message);

			return new BackgroundTaskResult(
				BackgroundTaskConstants.STATUS_FAILED, message);
		}

		if (MapUtil.getBoolean(
				backgroundTask.getTaskContextMap(), "scheduled") &&
			(launchSetObjectEntry.getStatus() !=
				WorkflowConstants.STATUS_SCHEDULED)) {

			String message = StringBundler.concat(
				"Launch set ", launchSetId, " is no longer scheduled");

			_log.error(message);

			return new BackgroundTaskResult(
				BackgroundTaskConstants.STATUS_FAILED, message);
		}

		long publishErrorsCount = MapUtil.getLong(
			launchSetObjectEntry.getValues(), "publishErrorsCount");

		if (publishErrorsCount > 0) {
			String message = StringBundler.concat(
				"Unable to publish launch set ", launchSetId,
				" because another publish failed after its background task ",
				"was queued");

			_log.error(message);

			return new BackgroundTaskResult(
				BackgroundTaskConstants.STATUS_FAILED, message);
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(launchSetObjectEntry.getCompanyId());

		long userId = backgroundTask.getUserId();

		serviceContext.setUserId(userId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		List<Map<String, Serializable>> launchSetErrorValuesMaps =
			new ArrayList<>();

		try {
			Group group = _groupLocalService.getGroup(
				launchSetObjectEntry.getCompanyId(), GroupConstants.GUEST);

			serviceContext.setScopeGroupId(group.getGroupId());

			PermissionThreadLocal.setPermissionChecker(
				_permissionCheckerFactory.create(
					_userLocalService.getUser(userId)));
			PrincipalThreadLocal.setName(userId);

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_publish(
						launchSetErrorValuesMaps,
						_objectEntryLocalService.getObjectEntry(launchSetId),
						serviceContext);

					return null;
				});
		}
		catch (Throwable throwable) {
			if (launchSetErrorValuesMaps.isEmpty()) {
				launchSetErrorValuesMaps.add(
					_launchSetErrorHelper.getLaunchSetErrorValues(
						null, throwable));
			}

			try {
				_launchSetErrorHelper.markPublishFailed(
					launchSetErrorValuesMaps, launchSetObjectEntry, userId);
			}
			catch (Exception exception) {
				throwable.addSuppressed(exception);
			}

			_log.error(
				"Unable to publish launch set " +
					launchSetObjectEntry.getObjectEntryId(),
				throwable);

			return new BackgroundTaskResult(
				BackgroundTaskConstants.STATUS_FAILED, throwable.getMessage());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Activate
	protected void activate() {
		_launchSetErrorHelper = new LaunchSetErrorHelper(
			_launchEntryManagerRegistry, _objectDefinitionLocalService,
			_objectEntryLocalService, _userLocalService);
	}

	private void _publish(
			List<Map<String, Serializable>> launchSetErrorValuesMaps,
			ObjectEntry launchSetObjectEntry, ServiceContext serviceContext)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				launchSetObjectEntry.getObjectDefinitionId(),
				"launchSetToLaunchEntries");

		for (ObjectEntry launchEntryObjectEntry :
				_objectEntryLocalService.getOneToManyObjectEntries(
					launchSetObjectEntry.getGroupId(),
					objectRelationship.getObjectRelationshipId(), null, false,
					launchSetObjectEntry.getObjectEntryId(), true, null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			Map<String, Serializable> values =
				launchEntryObjectEntry.getValues();

			if ((MapUtil.getLong(values, "draftId") == 0) &&
				Validator.isNotNull(MapUtil.getString(values, "versionKey"))) {

				continue;
			}

			try {
				TransactionInvokerUtil.invoke(
					_nestedTransactionConfig,
					() -> {
						_publishLaunchEntryObjectEntry(
							launchEntryObjectEntry, serviceContext);

						return null;
					});
			}
			catch (Throwable throwable) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to publish launch entry " +
							launchEntryObjectEntry.getObjectEntryId(),
						throwable);
				}

				launchSetErrorValuesMaps.add(
					_launchSetErrorHelper.getLaunchSetErrorValues(
						launchEntryObjectEntry, throwable));
			}
		}

		if (!launchSetErrorValuesMaps.isEmpty()) {
			throw new PortalException(
				StringBundler.concat(
					"Unable to publish ", launchSetErrorValuesMaps.size(),
					" launch entries of launch set ",
					launchSetObjectEntry.getObjectEntryId()));
		}

		_objectEntryLocalService.updateStatus(
			serviceContext.getUserId(), launchSetObjectEntry.getObjectEntryId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private void _publishLaunchEntryObjectEntry(
			ObjectEntry launchEntryObjectEntry, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> values = launchEntryObjectEntry.getValues();

		String className = MapUtil.getString(values, "className");

		LaunchEntryManager launchEntryManager =
			_launchEntryManagerRegistry.getLaunchEntryManager(className);

		if (launchEntryManager == null) {
			throw new PortalException(
				"No launch entry manager exists for " + className);
		}

		LaunchEntryManager.Version version = launchEntryManager.publishDraft(
			MapUtil.getLong(values, "classPK"),
			MapUtil.getLong(values, "draftId"));

		values.put("draftId", 0L);
		values.put("versionKey", version.getVersionKey());

		_objectEntryLocalService.updateObjectEntry(
			serviceContext.getUserId(),
			launchEntryObjectEntry.getObjectEntryId(),
			launchEntryObjectEntry.getObjectEntryFolderId(), values,
			serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LaunchSetPublishBackgroundTaskExecutor.class);

	private static final TransactionConfig _nestedTransactionConfig =
		TransactionConfig.Factory.create(
			Propagation.NESTED, new Class<?>[] {Exception.class});
	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LaunchEntryManagerRegistry _launchEntryManagerRegistry;

	private LaunchSetErrorHelper _launchSetErrorHelper;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private UserLocalService _userLocalService;

}