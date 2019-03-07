/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.internal.background.task;

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.exception.CTException;
import com.liferay.change.tracking.internal.background.task.display.CTPublishBackgroundTaskDisplay;
import com.liferay.change.tracking.internal.process.log.CTProcessLog;
import com.liferay.change.tracking.internal.process.util.CTProcessMessageSenderUtil;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalServiceUtil;
import com.liferay.change.tracking.service.CTEntryAggregateLocalServiceUtil;
import com.liferay.change.tracking.service.CTEntryLocalServiceUtil;
import com.liferay.change.tracking.service.CTProcessLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;
import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Zoltan Csaszi
 * @author Daniel Kocsis
 */
public class CTPublishBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public CTPublishBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new CTPublishBackgroundTaskStatusMessageTranslator());
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY);

		Bundle bundle = FrameworkUtil.getBundle(
			CTPublishBackgroundTaskExecutor.class);

		ServiceTracker<CTEngineManager, CTEngineManager> serviceTracker =
			new ServiceTracker<>(
				bundle.getBundleContext(), CTEngineManager.class, null);

		serviceTracker.open();

		_ctEngineManager = serviceTracker.getService();
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return new CTPublishBackgroundTaskExecutor();
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		final Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		final long ctProcessId = GetterUtil.getLong(
			taskContextMap.get("ctProcessId"));

		final long ctCollectionId = GetterUtil.getLong(
			taskContextMap.get("ctCollectionId"));

		_publishCTCollection(backgroundTask, ctProcessId, ctCollectionId);

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return new CTPublishBackgroundTaskDisplay(backgroundTask);
	}

	private void _attachLogs(BackgroundTask backgroundTask)
		throws IOException, PortalException {

		BackgroundTaskStatus backgroundTaskStatus =
			BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());

		CTProcessLog ctProcessLog =
			(CTProcessLog)backgroundTaskStatus.getAttribute("ctProcessLog");

		String ctProcessLogJSON = ctProcessLog.toString();

		BackgroundTaskManagerUtil.addBackgroundTaskAttachment(
			backgroundTask.getUserId(), backgroundTask.getBackgroundTaskId(),
			"log", FileUtil.createTempFile(ctProcessLogJSON.getBytes()));
	}

	private void _publishCTCollection(
			BackgroundTask backgroundTask, long ctProcessId,
			long ctCollectionId)
		throws Exception {

		try {
			CTProcess ctProcess = CTProcessLocalServiceUtil.getCTProcess(
				ctProcessId);

			CTProcessMessageSenderUtil.logCTProcessStarted(ctProcess);

			List<CTEntry> ctEntries = _ctEngineManager.getCTEntries(
				ctCollectionId);

			if (ListUtil.isEmpty(ctEntries)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find change tracking entries with change " +
							"tracking collection ID " + ctCollectionId);
				}

				return;
			}

			List<CTEntryAggregate> ctEntryAggregates =
				_ctEngineManager.getCTEntryAggregates(ctCollectionId);

			_publishCTEntriesAndCTEntryAggregates(
				backgroundTask.getUserId(), ctCollectionId, ctEntries,
				ctEntryAggregates);

			_updatePortletPreferences(ctCollectionId);

			_attachLogs(backgroundTask);
		}
		catch (Exception e) {
			CTProcessMessageSenderUtil.logCTProcessFailed();

			_log.error(
				"Unable to publish change tracking collection " +
					ctCollectionId,
				e);

			throw e;
		}
		finally {
			CTProcessMessageSenderUtil.logCTProcessFinished();
		}
	}

	private void _publishCTEntriesAndCTEntryAggregates(
			long userId, long ctCollectionId, List<CTEntry> ctEntries,
			List<CTEntryAggregate> ctEntryAggregates)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(userId);

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				user.getCompanyId());

		long productionCTCollectionId = productionCTCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElseThrow(
			() -> new CTException(
				user.getCompanyId(),
				"Unable to find production the change tracking collection")
		);

		Stream<CTEntry> ctEntryStream = ctEntries.stream();

		ctEntryStream.peek(
			CTProcessMessageSenderUtil::logCTEntryPublished
		).forEach(
			ctEntry -> _publishCTEntry(ctEntry, productionCTCollectionId)
		);

		if (ListUtil.isNotEmpty(ctEntryAggregates)) {
			Stream<CTEntryAggregate> ctEntryAggregateStream =
				ctEntryAggregates.stream();

			ctEntryAggregateStream.forEach(
				ctEntryAggregate -> _publishCTEntryAggregate(
					ctEntryAggregate, productionCTCollectionId));
		}

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(ctCollectionId);

		CTCollection ctCollection = ctCollectionOptional.orElseThrow(
			() -> new CTException(
				user.getCompanyId(),
				"Unable to find change tracking collection " + ctCollectionId));

		try {
			CTCollectionLocalServiceUtil.updateStatus(
				userId, ctCollection, WorkflowConstants.STATUS_APPROVED,
				new ServiceContext());
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to update the status of the published change " +
					"tracking collection",
				pe);

			throw pe;
		}
	}

	private void _publishCTEntry(
		CTEntry ctEntry, long productionCTCollectionId) {

		CTEntryLocalServiceUtil.addCTCollectionCTEntry(
			productionCTCollectionId, ctEntry);

		CTEntryLocalServiceUtil.updateStatus(
			ctEntry.getCtEntryId(), WorkflowConstants.STATUS_APPROVED);
	}

	private void _publishCTEntryAggregate(
		CTEntryAggregate ctEntryAggregate, long productionCTCollectionId) {

		CTEntryAggregateLocalServiceUtil.addCTCollectionCTEntryAggregate(
			productionCTCollectionId, ctEntryAggregate);
	}

	private void _updatePortletPreferences(final long ctCollectionId) {
		List<com.liferay.portal.kernel.model.PortletPreferences>
			portletPreferencesList =
				PortletPreferencesLocalServiceUtil.getPortletPreferences();

		Stream<com.liferay.portal.kernel.model.PortletPreferences> stream =
			portletPreferencesList.parallelStream();

		stream.filter(
			pp -> pp.getOwnerType() == 3
		).filter(
			pp -> StringUtil.startsWith(
				pp.getPortletId(),
				"com_liferay_journal_content_web_portlet_" +
					"JournalContentPortlet_")
		).map(
			pp -> {
				PortletPreferences portletPreferences =
					PortletPreferencesLocalServiceUtil.fetchPreferences(
						pp.getCompanyId(), pp.getOwnerId(), pp.getOwnerType(),
						pp.getPlid(), pp.getPortletId());

				return new PortletPreferencesWrapper(
					portletPreferences, pp.getCompanyId(), pp.getOwnerId(),
					pp.getOwnerType(), pp.getPlid(), pp.getPortletId());
			}
		).forEach(
			pp -> {
				PortletPreferences portletPreferences =
					pp.getPortletPreferences();

				String ctCollectionArticleId = portletPreferences.getValue(
					"ctCollectionArticleId" + ctCollectionId, StringPool.BLANK);

				try {
					if (Validator.isNotNull(ctCollectionArticleId)) {
						portletPreferences.setValue(
							"articleId", ctCollectionArticleId);
					}
				}
				catch (ReadOnlyException roe) {
					roe.printStackTrace();
				}

				String xml = PortletPreferencesFactoryUtil.toXML(
					portletPreferences);

				PortletPreferencesLocalServiceUtil.updatePreferences(
					pp.getOwnerId(), pp.getOwnerType(), pp.getPlid(),
					pp.getPortletId(), xml);
			}
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTPublishBackgroundTaskExecutor.class);

	private final CTEngineManager _ctEngineManager;

	private class PortletPreferencesWrapper {

		public PortletPreferencesWrapper(
			PortletPreferences portletPreferences, long companyId, long ownerId,
			int ownerType, long plid, String portletId) {

			_portletPreferences = portletPreferences;
			_companyId = companyId;
			_ownerId = ownerId;
			_ownerType = ownerType;
			_plid = plid;
			_portletId = portletId;
		}

		public long getCompanyId() {
			return _companyId;
		}

		public long getOwnerId() {
			return _ownerId;
		}

		public int getOwnerType() {
			return _ownerType;
		}

		public long getPlid() {
			return _plid;
		}

		public String getPortletId() {
			return _portletId;
		}

		public PortletPreferences getPortletPreferences() {
			return _portletPreferences;
		}

		private final long _companyId;
		private final long _ownerId;
		private final int _ownerType;
		private final long _plid;
		private final String _portletId;
		private final PortletPreferences _portletPreferences;

	}

}