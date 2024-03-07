/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.background.task;

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.constants.PublicationRoleConstants;
import com.liferay.change.tracking.exception.CTPublishConflictException;
import com.liferay.change.tracking.internal.CTServiceRegistry;
import com.liferay.change.tracking.internal.background.task.display.CTPublishBackgroundTaskDisplay;
import com.liferay.change.tracking.internal.helper.CTTableMapperHelper;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 * @author Daniel Kocsis
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.change.tracking.internal.background.task.CTPublishBackgroundTaskExecutor",
	service = AopService.class
)
public class CTPublishBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor implements AopService {

	public CTPublishBackgroundTaskExecutor() {
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return _backgroundTaskExecutor;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class
	)
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long fromCTCollectionId = GetterUtil.getLong(
			taskContextMap.get("fromCTCollectionId"));

		CTCollection fromCTCollection =
			_ctCollectionLocalService.getCTCollection(fromCTCollectionId);

		String fromCTCollectionName = fromCTCollection.getName();

		long toCTCollectionId = GetterUtil.getLong(
			taskContextMap.get("toCTCollectionId"));

		String toCTCollectionName;

		if (toCTCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			toCTCollectionName = "Production";
		}
		else {
			CTCollection toCTCollection =
				_ctCollectionLocalService.getCTCollection(toCTCollectionId);

			toCTCollectionName = toCTCollection.getName();
		}

		if (!_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				fromCTCollection.getSchemaVersionId())) {

			throw new IllegalArgumentException(
				StringBundler.concat(
					"Unable to publish from ", fromCTCollectionName, " to ",
					toCTCollectionName,
					" because it is out of date with the current release"));
		}

		if (toCTCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						fromCTCollectionId)) {

				_ctServiceRegistry.onBeforePublish(fromCTCollectionId);
			}
		}

		List<CTEntry> ctEntries = null;

		long[] ctEntryIds = (long[])taskContextMap.get("ctEntryIds");

		if (ctEntryIds != null) {
			ctEntries = _ctCollectionLocalService.getRelatedCTEntries(
				fromCTCollectionId, ctEntryIds);
		}
		else {
			ctEntries = _ctEntryLocalService.getCTCollectionCTEntries(
				fromCTCollectionId);
		}

		Map<Long, List<ConflictInfo>> conflictInfosMap =
			_ctCollectionLocalService.checkConflicts(
				fromCTCollection.getCompanyId(), ctEntries, fromCTCollectionId,
				fromCTCollectionName, toCTCollectionId, toCTCollectionName);

		if (!conflictInfosMap.isEmpty()) {
			List<ConflictInfo> unresolvedConflictInfos = new ArrayList<>();

			for (Map.Entry<Long, List<ConflictInfo>> entry :
					conflictInfosMap.entrySet()) {

				for (ConflictInfo conflictInfo : entry.getValue()) {
					if (!conflictInfo.isResolved()) {
						unresolvedConflictInfos.add(conflictInfo);
					}
				}
			}

			if (!unresolvedConflictInfos.isEmpty()) {
				throw new CTPublishConflictException(
					StringBundler.concat(
						"Unable to publish from ", fromCTCollectionName, " to ",
						toCTCollectionName,
						" because of unresolved conflicts: ",
						unresolvedConflictInfos));
			}
		}

		Map<Long, CTServicePublisher<?>> ctServicePublishers = new HashMap<>();

		for (CTEntry ctEntry : ctEntries) {
			CTServicePublisher<?> ctServicePublisher =
				ctServicePublishers.computeIfAbsent(
					ctEntry.getModelClassNameId(),
					modelClassNameId -> {
						CTService<?> ctService =
							_ctServiceRegistry.getCTService(modelClassNameId);

						if (ctService != null) {
							return new CTServicePublisher<>(
								_ctEntryLocalService, ctService,
								modelClassNameId, fromCTCollectionId,
								toCTCollectionId);
						}

						throw new SystemException(
							StringBundler.concat(
								"Unable to publish from ", fromCTCollectionName,
								" to ", toCTCollectionName,
								" because service for ", modelClassNameId,
								" is missing"));
					});

			ctServicePublisher.addCTEntry(ctEntry);
		}

		for (CTServicePublisher<?> ctServicePublisher :
				ctServicePublishers.values()) {

			ctServicePublisher.publish();
		}

		for (CTTableMapperHelper ctTableMapperHelper :
				_ctServiceRegistry.getCTTableMapperHelpers()) {

			ctTableMapperHelper.publish(
				fromCTCollectionId, toCTCollectionId,
				_multiVMPool.getPortalCacheManager());
		}

		if (toCTCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			Date modifiedDate = new Date();

			fromCTCollection.setModifiedDate(modifiedDate);

			fromCTCollection.setStatus(WorkflowConstants.STATUS_APPROVED);
			fromCTCollection.setStatusByUserId(backgroundTask.getUserId());
			fromCTCollection.setStatusDate(modifiedDate);

			_ctCollectionLocalService.updateCTCollection(fromCTCollection);

			_ctServiceRegistry.onAfterPublish(fromCTCollectionId);
		}
		else {
			for (CTEntry ctEntry : ctEntries) {
				ctEntry.setCtCollectionId(toCTCollectionId);

				_ctEntryLocalService.updateCTEntry(ctEntry);
			}
		}

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {BackgroundTaskExecutor.class};
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return new CTPublishBackgroundTaskDisplay(backgroundTask);
	}

	@Override
	public String handleException(
		BackgroundTask backgroundTask, Exception exception) {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-11018")) {
			return super.handleException(backgroundTask, exception);
		}

		boolean showConflicts = false;

		if (exception instanceof CTPublishConflictException) {
			showConflicts = true;
		}

		try {
			long fromCTCollectionId = MapUtil.getLong(
				backgroundTask.getTaskContextMap(), "fromCTCollectionId");

			CTCollection fromCTCollection =
				_ctCollectionLocalService.getCTCollection(fromCTCollectionId);

			for (long userId :
					_getPublicationRolesUserIds(
						fromCTCollection, showConflicts)) {

				if (UserNotificationManagerUtil.isDeliver(
						userId, CTPortletKeys.PUBLICATIONS, 0,
						UserNotificationDefinition.
							NOTIFICATION_TYPE_REVIEW_ENTRY,
						UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

					_userNotificationEventLocalService.
						sendUserNotificationEvents(
							userId, CTPortletKeys.PUBLICATIONS,
							UserNotificationDeliveryConstants.TYPE_WEBSITE,
							false,
							JSONUtil.put(
								"backgroundTaskId",
								backgroundTask.getBackgroundTaskId()
							).put(
								"ctCollectionId", fromCTCollectionId
							).put(
								"ctCollectionName",
								HtmlUtil.escape(fromCTCollection.getName())
							).put(
								"notificationType",
								UserNotificationDefinition.
									NOTIFICATION_TYPE_REVIEW_ENTRY
							).put(
								"showConflicts", showConflicts
							));
				}
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return super.handleException(backgroundTask, exception);
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		_backgroundTaskExecutor = (BackgroundTaskExecutor)aopProxy;
	}

	private Set<Long> _getPublicationRolesUserIds(
			CTCollection ctCollection, boolean showConflicts)
		throws PortalException {

		Set<Long> userIds = new HashSet<>();

		userIds.add(ctCollection.getUserId());

		if (!showConflicts) {
			Role role = _roleLocalService.getRole(
				ctCollection.getCompanyId(), RoleConstants.ADMINISTRATOR);

			for (long userId :
					_userLocalService.getRoleUserIds(role.getRoleId())) {

				userIds.add(userId);
			}
		}

		Group group = _groupLocalService.fetchGroup(
			ctCollection.getCompanyId(),
			_portal.getClassNameId(CTCollection.class),
			ctCollection.getCtCollectionId());

		if (group == null) {
			return userIds;
		}

		String[] roleNames = {
			PublicationRoleConstants.NAME_ADMIN,
			PublicationRoleConstants.NAME_EDITOR,
			PublicationRoleConstants.NAME_PUBLISHER
		};

		for (String roleName : roleNames) {
			Role role = _roleLocalService.fetchRole(
				group.getCompanyId(), roleName);

			if (role == null) {
				continue;
			}

			userIds.addAll(
				TransformUtil.transform(
					_userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
						group.getGroupId(), role.getRoleId()),
					UserGroupRole::getUserId));
		}

		return userIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTPublishBackgroundTaskExecutor.class);

	private BackgroundTaskExecutor _backgroundTaskExecutor;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTSchemaVersionLocalService _ctSchemaVersionLocalService;

	@Reference
	private CTServiceRegistry _ctServiceRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private MultiVMPool _multiVMPool;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}