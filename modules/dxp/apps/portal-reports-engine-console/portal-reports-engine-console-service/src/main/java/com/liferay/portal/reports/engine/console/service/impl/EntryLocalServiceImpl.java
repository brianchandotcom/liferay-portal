/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.service.impl;

import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.petra.memory.DeleteFileFinalizeAction;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.reports.engine.MemoryReportDesignRetriever;
import com.liferay.portal.reports.engine.ReportDataSourceType;
import com.liferay.portal.reports.engine.ReportDesignRetriever;
import com.liferay.portal.reports.engine.ReportRequest;
import com.liferay.portal.reports.engine.ReportRequestContext;
import com.liferay.portal.reports.engine.console.configuration.ReportsGroupServiceEmailConfiguration;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsoleConstants;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsoleDestinationNames;
import com.liferay.portal.reports.engine.console.exception.DefinitionNameException;
import com.liferay.portal.reports.engine.console.exception.EntryEmailDeliveryException;
import com.liferay.portal.reports.engine.console.exception.EntryEmailNotificationsException;
import com.liferay.portal.reports.engine.console.internal.util.ReportsEngineConsoleSubscriptionSender;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.base.EntryLocalServiceBaseImpl;
import com.liferay.portal.reports.engine.console.status.ReportStatus;
import com.liferay.portal.reports.engine.constants.ReportsEngineDestinationNames;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gavin Wan
 */
@Component(
	property = "model.class.name=com.liferay.portal.reports.engine.console.model.Entry",
	service = AopService.class
)
public class EntryLocalServiceImpl extends EntryLocalServiceBaseImpl {

	@Override
	public Entry addEntry(
			long userId, long groupId, long definitionId, String format,
			boolean schedulerRequest, Date startDate, Date endDate,
			boolean repeating, String recurrence, String emailNotifications,
			String emailDelivery, String portletId, String pageURL,
			String reportName, String reportParameters,
			ServiceContext serviceContext)
		throws PortalException {

		// Entry

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validate(emailNotifications, emailDelivery, reportName);

		long entryId = counterLocalService.increment();

		Entry entry = entryPersistence.create(entryId);

		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(serviceContext.getCreateDate(now));
		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setDefinitionId(definitionId);
		entry.setFormat(format);
		entry.setScheduleRequest(schedulerRequest);
		entry.setStartDate(startDate);
		entry.setEndDate(endDate);
		entry.setReportParameters(reportParameters);
		entry.setRepeating(repeating);
		entry.setRecurrence(recurrence);
		entry.setEmailNotifications(emailNotifications);
		entry.setEmailDelivery(emailDelivery);
		entry.setPortletId(portletId);

		StringBundler sb = new StringBundler(5);

		sb.append(pageURL);
		sb.append("&");
		sb.append(_portal.getPortletNamespace(portletId));
		sb.append("entryId=");
		sb.append(entryId);

		entry.setPageURL(sb.toString());

		entry.setStatus(ReportStatus.PENDING.getValue());

		entry = entryPersistence.update(entry);

		// Resources

		resourceLocalService.addModelResources(entry, serviceContext);

		// Scheduler

		if (schedulerRequest) {
			scheduleEntry(entry, reportName);
		}

		// Report

		if (!schedulerRequest) {
			generateReport(entryId, reportName);
		}

		return entry;
	}

	@Override
	public void addEntryResources(
			Entry entry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			Entry.class.getName(), entry.getEntryId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	@Override
	public void addEntryResources(
			Entry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			Entry.class.getName(), entry.getEntryId(), communityPermissions,
			guestPermissions);
	}

	@Override
	public void deleteAttachment(long companyId, String fileName)
		throws PortalException {

		DLStoreUtil.deleteFile(companyId, CompanyConstants.SYSTEM, fileName);
	}

	@Override
	public Entry deleteEntry(Entry entry) throws PortalException {

		// Entry

		entryPersistence.remove(entry);

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), Entry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Scheduler

		if (entry.isRepeating()) {
			_schedulerEngineHelper.unschedule(
				entry.getJobName(), entry.getSchedulerRequestName(),
				StorageType.PERSISTED);
		}

		// Attachments

		DLStoreUtil.deleteDirectory(
			entry.getCompanyId(), CompanyConstants.SYSTEM,
			entry.getAttachmentsDir());

		return entry;
	}

	@Override
	public Entry deleteEntry(long entryId) throws PortalException {
		Entry entry = entryPersistence.findByPrimaryKey(entryId);

		return deleteEntry(entry);
	}

	@Override
	public void generateReport(long entryId) throws PortalException {
		Entry entry = entryPersistence.findByPrimaryKey(entryId);

		Definition definition = definitionPersistence.findByPrimaryKey(
			entry.getDefinitionId());

		generateReport(entryId, definition.getReportName());
	}

	@Override
	public void generateReport(long entryId, String reportName)
		throws PortalException {

		Entry entry = entryPersistence.findByPrimaryKey(entryId);

		Definition definition = definitionPersistence.findByPrimaryKey(
			entry.getDefinitionId());

		String[] existingFiles = definition.getAttachmentsFiles();

		byte[] templateFile = DLStoreUtil.getFileAsBytes(
			definition.getCompanyId(), CompanyConstants.SYSTEM,
			existingFiles[0]);

		ReportDesignRetriever retriever = new MemoryReportDesignRetriever(
			reportName + StringPool.PERIOD + entry.getFormat(),
			definition.getModifiedDate(), templateFile);

		long sourceId = definition.getSourceId();

		Map<String, String> reportParameters = new HashMap<>();

		JSONArray reportParametersJSONArray = _jsonFactory.createJSONArray(
			entry.getReportParameters());

		for (int i = 0; i < reportParametersJSONArray.length(); i++) {
			JSONObject reportParameterJSONObject =
				reportParametersJSONArray.getJSONObject(i);

			reportParameters.put(
				reportParameterJSONObject.getString("key"),
				reportParameterJSONObject.getString("value"));
		}

		ReportRequestContext reportRequestContext = null;

		if (sourceId == 0) {
			reportRequestContext = new ReportRequestContext(
				ReportDataSourceType.PORTAL);
		}
		else {
			Source source = sourcePersistence.findByPrimaryKey(sourceId);

			reportRequestContext = new ReportRequestContext(
				ReportDataSourceType.JDBC);

			reportRequestContext.setAttribute(
				ReportRequestContext.JDBC_DRIVER_CLASS,
				source.getDriverClassName());
			reportRequestContext.setAttribute(
				ReportRequestContext.JDBC_PASSWORD, source.getDriverPassword());
			reportRequestContext.setAttribute(
				ReportRequestContext.JDBC_URL, source.getDriverUrl());
			reportRequestContext.setAttribute(
				ReportRequestContext.JDBC_USER_NAME,
				source.getDriverUserName());
		}

		ReportRequest reportRequest = new ReportRequest(
			reportRequestContext, retriever, reportParameters,
			entry.getFormat());

		Message message = new Message();

		message.setPayload(reportRequest);
		message.setResponseDestinationName(
			ReportsEngineConsoleDestinationNames.REPORTS_ADMIN);
		message.setResponseId(String.valueOf(entry.getEntryId()));

		_messageBus.sendMessage(
			ReportsEngineDestinationNames.REPORT_REQUEST, message);
	}

	@Override
	public List<Entry> getEntries(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andSearch, int start, int end,
		OrderByComparator orderByComparator) {

		return entryFinder.findByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andSearch, start, end, orderByComparator);
	}

	@Override
	public int getEntriesCount(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andSearch) {

		return entryFinder.countByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andSearch);
	}

	@Override
	public void sendEmails(
			long entryId, String fileName, String[] emailAddresses,
			boolean notification)
		throws PortalException {

		Entry entry = entryLocalService.getEntry(entryId);

		try {
			notifySubscribers(entry, emailAddresses, fileName, notification);
		}
		catch (IOException ioException) {
			throw new PortalException(ioException.getMessage());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public void unscheduleEntry(long entryId) throws PortalException {
		Entry entry = entryPersistence.findByPrimaryKey(entryId);

		entry.setScheduleRequest(false);
		entry.setRepeating(false);

		entry = entryPersistence.update(entry);

		_schedulerEngineHelper.unschedule(
			entry.getJobName(), entry.getSchedulerRequestName(),
			StorageType.PERSISTED);
	}

	@Override
	public void updateEntry(
			long entryId, String reportName, byte[] reportResults)
		throws PortalException {

		Entry entry = entryPersistence.findByPrimaryKey(entryId);
		Date now = new Date();

		if (entry.isScheduleRequest()) {
			StringBundler sb = new StringBundler(4);

			sb.append(StringUtil.extractFirst(reportName, StringPool.PERIOD));

			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"MM_dd_yyyy_HH_mm");

			sb.append(dateFormat.format(now));

			sb.append(StringPool.PERIOD);
			sb.append(StringUtil.extractLast(reportName, StringPool.PERIOD));

			reportName = sb.toString();
		}

		String fileName =
			entry.getAttachmentsDir() + StringPool.SLASH + reportName;

		DLStoreUtil.addFile(
			entry.getCompanyId(), CompanyConstants.SYSTEM, fileName, false,
			reportResults);

		String[] emailAddresses = StringUtil.split(entry.getEmailDelivery());

		sendEmails(entryId, fileName, emailAddresses, false);

		emailAddresses = StringUtil.split(entry.getEmailNotifications());

		sendEmails(entryId, fileName, emailAddresses, true);

		entry.setModifiedDate(now);
		entry.setStatus(ReportStatus.COMPLETE.getValue());

		entryPersistence.update(entry);
	}

	@Override
	public void updateEntryStatus(
			long entryId, ReportStatus status, String errorMessage)
		throws PortalException {

		Entry entry = entryLocalService.getEntry(entryId);

		entry.setStatus(status.getValue());
		entry.setErrorMessage(errorMessage);

		entryPersistence.update(entry);
	}

	protected ReportsGroupServiceEmailConfiguration
			getReportsGroupServiceEmailConfiguration(long groupId)
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			ReportsGroupServiceEmailConfiguration.class,
			new GroupServiceSettingsLocator(
				groupId, ReportsEngineConsoleConstants.SERVICE_NAME));
	}

	protected File getTemporaryReportFile(
			Entry entry, String fileName, boolean notification)
		throws IOException, PortalException {

		if (notification) {
			return null;
		}

		try (InputStream inputStream = DLStoreUtil.getFileAsStream(
				entry.getCompanyId(), CompanyConstants.SYSTEM, fileName)) {

			if (inputStream == null) {
				throw new IOException("Unable to open file " + fileName);
			}

			return FileUtil.createTempFile(inputStream);
		}
	}

	protected void notifySubscribers(
			Entry entry, String[] emailAddresses, String fileName,
			boolean notification)
		throws Exception {

		if (emailAddresses.length == 0) {
			return;
		}

		String portletId = entry.getPortletId();

		ReportsGroupServiceEmailConfiguration
			reportsGroupServiceEmailConfiguration =
				getReportsGroupServiceEmailConfiguration(entry.getGroupId());

		String fromName = reportsGroupServiceEmailConfiguration.emailFromName();

		String fromAddress =
			reportsGroupServiceEmailConfiguration.emailFromAddress();

		Map<Locale, String> localizedSubjectMap = null;
		Map<Locale, String> localizedBodyMap = null;

		if (notification) {
			localizedBodyMap = LocalizationUtil.getMap(
				reportsGroupServiceEmailConfiguration.emailNotificationsBody());

			localizedSubjectMap = LocalizationUtil.getMap(
				reportsGroupServiceEmailConfiguration.
					emailNotificationsSubject());
		}
		else {
			localizedBodyMap = LocalizationUtil.getMap(
				reportsGroupServiceEmailConfiguration.emailDeliveryBody());

			localizedSubjectMap = LocalizationUtil.getMap(
				reportsGroupServiceEmailConfiguration.emailDeliverySubject());
		}

		SubscriptionSender subscriptionSender =
			new ReportsEngineConsoleSubscriptionSender();

		String reportName = StringUtil.extractLast(
			fileName, StringPool.FORWARD_SLASH);

		if (!notification) {
			File file = getTemporaryReportFile(entry, fileName, notification);

			FinalizeManager.register(
				subscriptionSender,
				new DeleteFileFinalizeAction(file.getAbsolutePath()),
				FinalizeManager.PHANTOM_REFERENCE_FACTORY);

			subscriptionSender.addFileAttachment(file, reportName);
		}

		subscriptionSender.setCompanyId(entry.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$FROM_ADDRESS$]", fromAddress, "[$FROM_NAME$]", fromName,
			"[$PAGE_URL$]", entry.getPageURL(), "[$REPORT_NAME$]", reportName);
		subscriptionSender.setCurrentUserId(entry.getUserId());
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setGroupId(entry.getGroupId());
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(localizedBodyMap);
		subscriptionSender.setLocalizedSubjectMap(localizedSubjectMap);
		subscriptionSender.setMailId("reports_entry", entry.getEntryId());
		subscriptionSender.setReplyToAddress(fromAddress);

		subscriptionSender.setPortletId(portletId);

		subscriptionSender.setScopeGroupId(entry.getGroupId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(entry.getCompanyId());
		serviceContext.setScopeGroupId(entry.getGroupId());

		subscriptionSender.setServiceContext(serviceContext);

		for (String emailAddress : emailAddresses) {
			subscriptionSender.addRuntimeSubscribers(
				emailAddress, emailAddress);
		}

		subscriptionSender.flushNotificationsAsync();
	}

	protected void scheduleEntry(Entry entry, String reportName)
		throws PortalException {

		Trigger trigger = TriggerFactoryUtil.createTrigger(
			entry.getJobName(), entry.getSchedulerRequestName(),
			entry.getStartDate(), entry.getEndDate(), entry.getRecurrence());

		Message message = new Message();

		message.put("entryId", entry.getEntryId());
		message.put("reportName", reportName);

		_schedulerEngineHelper.schedule(
			trigger, StorageType.PERSISTED, null,
			"liferay/reports_scheduler_event", message, 0);
	}

	protected void validate(
			String emailNotifications, String emailDelivery, String reportName)
		throws PortalException {

		for (String emailAddress : StringUtil.split(emailNotifications)) {
			if (!Validator.isEmailAddress(emailAddress)) {
				throw new EntryEmailNotificationsException();
			}
		}

		for (String emailAddress : StringUtil.split(emailDelivery)) {
			if (!Validator.isEmailAddress(emailAddress)) {
				throw new EntryEmailDeliveryException();
			}
		}

		if (Validator.isNull(reportName)) {
			throw new DefinitionNameException();
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private Portal _portal;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private UserLocalService _userLocalService;

}