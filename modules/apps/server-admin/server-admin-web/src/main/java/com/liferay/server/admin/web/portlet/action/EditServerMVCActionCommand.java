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

package com.liferay.server.admin.web.portlet.action;

import com.liferay.mail.service.MailService;
import com.liferay.portal.captcha.CaptchaImpl;
import com.liferay.portal.captcha.recaptcha.ReCaptchaImpl;
import com.liferay.portal.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.portal.convert.ConvertException;
import com.liferay.portal.convert.ConvertProcess;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.GhostscriptUtil;
import com.liferay.portal.kernel.image.ImageMagickUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.SanitizerLogWrapper;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingHelperUtil;
import com.liferay.portal.kernel.scripting.ScriptingUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.servlet.DirectServletRegistryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThreadUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.lang.DoPrivilegedBean;
import com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicy;
import com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicyFactoryUtil;
import com.liferay.portal.security.membershippolicy.RoleMembershipPolicy;
import com.liferay.portal.security.membershippolicy.RoleMembershipPolicyFactoryUtil;
import com.liferay.portal.security.membershippolicy.SiteMembershipPolicy;
import com.liferay.portal.security.membershippolicy.SiteMembershipPolicyFactoryUtil;
import com.liferay.portal.security.membershippolicy.UserGroupMembershipPolicy;
import com.liferay.portal.security.membershippolicy.UserGroupMembershipPolicyFactoryUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceComponentLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.admin.util.CleanUpPermissionsUtil;
import com.liferay.portlet.documentlibrary.util.DLPreviewableProcessor;
import com.liferay.util.log4j.Log4JUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.apache.log4j.Level;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Philip Jones
 */
@Component(
	property = {
		"javax.portlet.name=" + PortletKeys.SERVER_ADMIN,
		"mvc.command.name=/server_admin/edit_server"
	},
	service = MVCActionCommand.class
)
public class EditServerMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			SessionErrors.add(
				actionRequest,
				PrincipalException.MustBeOmniadmin.class.getName());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp" );

			return;
		}

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences();

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (cmd.equals("addLogLevel")) {
			addLogLevel(actionRequest);
		}
		else if (cmd.equals("cacheDb")) {
			cacheDb();
		}
		else if (cmd.equals("cacheMulti")) {
			cacheMulti();
		}
		else if (cmd.equals("cacheServlet")) {
			cacheServlet();
		}
		else if (cmd.equals("cacheSingle")) {
			cacheSingle();
		}
		else if (cmd.equals("cleanUpPermissions")) {
			CleanUpPermissionsUtil.cleanUpAddToPagePermissions(actionRequest);
		}
		else if (cmd.startsWith("convertProcess.")) {
			redirect = convertProcess(actionRequest, actionResponse, cmd);
		}
		else if (cmd.equals("dlPreviews")) {
			DLPreviewableProcessor.deleteFiles();
		}
		else if (cmd.equals("gc")) {
			gc();
		}
		else if (cmd.equals("installXuggler")) {
			installXuggler(actionRequest, actionResponse);
		}
		else if (cmd.equals("reindex")) {
			reindex(actionRequest);
		}
		else if (cmd.equals("reindexDictionaries")) {
			reindexDictionaries(actionRequest);
		}
		else if (cmd.equals("runScript")) {
			runScript(actionRequest, actionResponse);
		}
		else if (cmd.equals("shutdown")) {
			shutdown(actionRequest);
		}
		else if (cmd.equals("threadDump")) {
			threadDump();
		}
		else if (cmd.equals("toggleIndexerEnabled")) {
			toggleIndexerEnabled(actionRequest);
		}
		else if (cmd.equals("updateCaptcha")) {
			updateCaptcha(actionRequest, portletPreferences);
		}
		else if (cmd.equals("updateExternalServices")) {
			updateExternalServices(actionRequest, portletPreferences);
		}
		else if (cmd.equals("updateFileUploads")) {
			updateFileUploads(actionRequest, portletPreferences);
		}
		else if (cmd.equals("updateLogLevels")) {
			updateLogLevels(actionRequest);
		}
		else if (cmd.equals("updateMail")) {
			updateMail(actionRequest, portletPreferences);
		}
		else if (cmd.equals("verifyMembershipPolicies")) {
			verifyMembershipPolicies();
		}
		else if (cmd.equals("verifyPluginTables")) {
			verifyPluginTables();
		}

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected void addLogLevel(ActionRequest actionRequest) throws Exception {
		String loggerName = ParamUtil.getString(actionRequest, "loggerName");
		String priority = ParamUtil.getString(actionRequest, "priority");

		Log4JUtil.setLevel(loggerName, priority, true);
	}

	protected void cacheDb() throws Exception {
		CacheRegistryUtil.clear();
	}

	protected void cacheMulti() throws Exception {
		MultiVMPoolUtil.clear();
	}

	protected void cacheServlet() throws Exception {
		DirectServletRegistryUtil.clearServlets();
	}

	protected void cacheSingle() throws Exception {
		SingleVMPoolUtil.clear();
	}

	protected String convertProcess(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String cmd)
		throws Exception {

		ActionResponseImpl actionResponseImpl =
			(ActionResponseImpl)actionResponse;

		PortletSession portletSession = actionRequest.getPortletSession();

		String className = StringUtil.replaceFirst(
			cmd, "convertProcess.", StringPool.BLANK);

		ConvertProcess convertProcess = (ConvertProcess)InstancePool.get(
			className);

		String[] parameters = convertProcess.getParameterNames();

		if (parameters != null) {
			String[] values = new String[parameters.length];

			for (int i = 0; i < parameters.length; i++) {
				String parameter =
					className + StringPool.PERIOD + parameters[i];

				if (parameters[i].contains(StringPool.EQUAL)) {
					String[] parameterPair = StringUtil.split(
						parameters[i], CharPool.EQUAL);

					parameter =
						className + StringPool.PERIOD + parameterPair[0];
				}

				values[i] = ParamUtil.getString(actionRequest, parameter);
			}

			convertProcess.setParameterValues(values);
		}

		try {
			convertProcess.validate();
		}
		catch (ConvertException ce) {
			SessionErrors.add(actionRequest, ce.getClass(), ce);

			return null;
		}

		String path = convertProcess.getPath();

		if (path != null) {
			PortletURL portletURL = actionResponseImpl.createRenderURL();

			portletURL.setParameter("mvcRenderCommandName", path);
			portletURL.setWindowState(WindowState.MAXIMIZED);

			return portletURL.toString();
		}

		MaintenanceUtil.maintain(portletSession.getId(), className);

		MessageBusUtil.sendMessage(DestinationNames.CONVERT_PROCESS, className);

		return null;
	}

	protected void gc() throws Exception {
		Runtime.getRuntime().gc();
	}

	protected String getFileExtensions(
		ActionRequest actionRequest, String name) {

		String value = ParamUtil.getString(actionRequest, name);

		return value.replace(", .", ",.");
	}

	protected void installXuggler(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String jarName = ParamUtil.getString(actionRequest, "jarName");

		XugglerUtil.installNativeLibraries(jarName);
	}

	protected void reindex(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, Serializable> taskContextMap = new HashMap<>();

		String className = ParamUtil.getString(actionRequest, "className");

		taskContextMap.put("className", className);

		taskContextMap.put("companyIds", PortalInstances.getCompanyIds());

		String taskExecutorClassName =
			_CLASS_NAME_REINDEX_PORTAL_BACKGROUND_TASK_EXECUTOR;

		if (Validator.isNotNull(className)) {
			taskExecutorClassName =
				_CLASS_NAME_REINDEX_SINGLE_INDEXER_BACKGROUND_TASK_EXECUTOR;
		}

		if (!ParamUtil.getBoolean(actionRequest, "blocking")) {
			BackgroundTaskManagerUtil.addBackgroundTask(
				themeDisplay.getUserId(), CompanyConstants.SYSTEM, "reindex",
				taskExecutorClassName, taskContextMap, new ServiceContext());

			return;
		}

		final String uuid = PortalUUIDUtil.generate();

		taskContextMap.put("uuid", uuid);

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		MessageListener messageListener = new MessageListener() {

			@Override
			public void receive(Message message)
				throws MessageListenerException {

				try {
					BackgroundTask backgroundTask =
						BackgroundTaskManagerUtil.getBackgroundTask(
							message.getLong("backgroundTaskId"));

					Map<String, Serializable> taskContextMap =
						backgroundTask.getTaskContextMap();

					if (!uuid.equals(taskContextMap.get("uuid"))) {
						return;
					}
				}
				catch (PortalException pe) {
					throw new MessageListenerException(pe);
				}

				int status = message.getInteger("status");

				if ((status ==
						BackgroundTaskConstants.STATUS_CANCELLED) ||
					(status == BackgroundTaskConstants.STATUS_FAILED) ||
					(status == BackgroundTaskConstants.STATUS_SUCCESSFUL)) {

					countDownLatch.countDown();
				}
			}
		};

		MessageBusUtil.registerMessageListener(
			DestinationNames.BACKGROUND_TASK_STATUS, messageListener);

		try {
			BackgroundTaskManagerUtil.addBackgroundTask(
				themeDisplay.getUserId(), CompanyConstants.SYSTEM, "reindex",
				taskExecutorClassName, taskContextMap, new ServiceContext());

			countDownLatch.await(
				ParamUtil.getLong(actionRequest, "timeout", Time.HOUR),
				TimeUnit.MILLISECONDS);
		}
		finally {
			MessageBusUtil.unregisterMessageListener(
				DestinationNames.BACKGROUND_TASK_STATUS, messageListener);
		}
	}

	protected void reindexDictionaries(ActionRequest actionRequest)
		throws Exception {

		long[] companyIds = PortalInstances.getCompanyIds();

		for (long companyId : companyIds) {
			SearchEngineUtil.indexQuerySuggestionDictionaries(companyId);
			SearchEngineUtil.indexSpellCheckerDictionaries(companyId);
		}
	}

	protected void runScript(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String language = ParamUtil.getString(actionRequest, "language");
		String script = ParamUtil.getString(actionRequest, "script");

		PortletConfig portletConfig = getPortletConfig(actionRequest);

		PortletContext portletContext = portletConfig.getPortletContext();

		Map<String, Object> portletObjects =
			ScriptingHelperUtil.getPortletObjects(
				portletConfig, portletContext, actionRequest, actionResponse);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		UnsyncPrintWriter unsyncPrintWriter = UnsyncPrintWriterPool.borrow(
			unsyncByteArrayOutputStream);

		portletObjects.put("out", unsyncPrintWriter);

		try {
			SessionMessages.add(actionRequest, "language", language);
			SessionMessages.add(actionRequest, "script", script);

			ScriptingUtil.exec(
				null, portletObjects, language, script, StringPool.EMPTY_ARRAY);

			unsyncPrintWriter.flush();

			SessionMessages.add(
				actionRequest, "scriptOutput",
				unsyncByteArrayOutputStream.toString());
		}
		catch (ScriptingException se) {
			SessionErrors.add(
				actionRequest, ScriptingException.class.getName(), se);

			Log log = SanitizerLogWrapper.allowCRLF(_log);

			log.error(se.getMessage());
		}
	}

	@Reference(unbind = "-")
	protected void setMailService(MailService mailService) {
		_mailService = mailService;
	}

	@Reference(unbind = "-")
	protected void setServiceComponentLocalService(
		ServiceComponentLocalService serviceComponentLocalService) {

		_serviceComponentLocalService = serviceComponentLocalService;
	}

	protected void shutdown(ActionRequest actionRequest) throws Exception {
		if (ShutdownUtil.isInProcess()) {
			ShutdownUtil.cancel();
		}
		else {
			long minutes =
				ParamUtil.getInteger(actionRequest, "minutes") * Time.MINUTE;

			if (minutes <= 0) {
				SessionErrors.add(actionRequest, "shutdownMinutes");
			}
			else {
				String message = ParamUtil.getString(actionRequest, "message");

				ShutdownUtil.shutdown(minutes, message);
			}
		}
	}

	protected void threadDump() throws Exception {
		if (_log.isInfoEnabled()) {
			Log log = SanitizerLogWrapper.allowCRLF(_log);

			log.info(ThreadUtil.threadDump());
		}
		else {
			Class<?> clazz = getClass();

			_log.error(
				"Thread dumps require the log level to be at least INFO for " +
					clazz.getName());
		}
	}

	protected void toggleIndexerEnabled(ActionRequest actionRequest)
		throws Exception {

		String className = ParamUtil.getString(actionRequest, "className");

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(className);

		boolean indexerEnabled = indexer.isIndexerEnabled();

		if (indexerEnabled) {
			indexer.setIndexerEnabled(false);
		}
		else {
			indexer.setIndexerEnabled(true);

			reindex(actionRequest);
		}
	}

	protected void updateCaptcha(
			ActionRequest actionRequest, PortletPreferences portletPreferences)
		throws Exception {

		boolean reCaptchaEnabled = ParamUtil.getBoolean(
			actionRequest, "reCaptchaEnabled");
		String reCaptchaPrivateKey = ParamUtil.getString(
			actionRequest, "reCaptchaPrivateKey");
		String reCaptchaPublicKey = ParamUtil.getString(
			actionRequest, "reCaptchaPublicKey");

		Captcha captcha = null;

		if (reCaptchaEnabled) {
			captcha = new ReCaptchaImpl();
		}
		else {
			captcha = new SimpleCaptchaImpl();
		}

		validateCaptcha(actionRequest);

		if (SessionErrors.isEmpty(actionRequest)) {
			portletPreferences.setValue(
				PropsKeys.CAPTCHA_ENGINE_IMPL, captcha.getClass().getName());
			portletPreferences.setValue(
				PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE,
				reCaptchaPrivateKey);
			portletPreferences.setValue(
				PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC,
				reCaptchaPublicKey);

			portletPreferences.store();

			CaptchaImpl captchaImpl = null;

			Captcha currentCaptcha = CaptchaUtil.getCaptcha();

			if (currentCaptcha instanceof DoPrivilegedBean) {
				DoPrivilegedBean doPrivilegedBean =
					(DoPrivilegedBean)currentCaptcha;

				captchaImpl = (CaptchaImpl)doPrivilegedBean.getActualBean();
			}
			else {
				captchaImpl = (CaptchaImpl)currentCaptcha;
			}

			captchaImpl.setCaptcha(captcha);
		}
	}

	protected void updateExternalServices(
			ActionRequest actionRequest, PortletPreferences portletPreferences)
		throws Exception {

		boolean imageMagickEnabled = ParamUtil.getBoolean(
			actionRequest, "imageMagickEnabled");
		String imageMagickPath = ParamUtil.getString(
			actionRequest, "imageMagickPath");
		boolean openOfficeEnabled = ParamUtil.getBoolean(
			actionRequest, "openOfficeEnabled");
		int openOfficePort = ParamUtil.getInteger(
			actionRequest, "openOfficePort");
		boolean xugglerEnabled = ParamUtil.getBoolean(
			actionRequest, "xugglerEnabled");

		portletPreferences.setValue(
			PropsKeys.IMAGEMAGICK_ENABLED, String.valueOf(imageMagickEnabled));
		portletPreferences.setValue(
			PropsKeys.IMAGEMAGICK_GLOBAL_SEARCH_PATH, imageMagickPath);
		portletPreferences.setValue(
			PropsKeys.OPENOFFICE_SERVER_ENABLED,
			String.valueOf(openOfficeEnabled));
		portletPreferences.setValue(
			PropsKeys.OPENOFFICE_SERVER_PORT, String.valueOf(openOfficePort));
		portletPreferences.setValue(
			PropsKeys.XUGGLER_ENABLED, String.valueOf(xugglerEnabled));

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith("imageMagickLimit")) {
				String key = StringUtil.toLowerCase(
					name.substring(16, name.length()));
				String value = ParamUtil.getString(actionRequest, name);

				portletPreferences.setValue(
					PropsKeys.IMAGEMAGICK_RESOURCE_LIMIT + key, value);
			}
		}

		portletPreferences.store();

		GhostscriptUtil.reset();
		ImageMagickUtil.reset();
	}

	protected void updateFileUploads(
			ActionRequest actionRequest, PortletPreferences portletPreferences)
		throws Exception {

		long dlFileEntryPreviewableProcessorMaxSize = ParamUtil.getLong(
			actionRequest, "dlFileEntryPreviewableProcessorMaxSize");
		long dlFileEntryThumbnailMaxHeight = ParamUtil.getLong(
			actionRequest, "dlFileEntryThumbnailMaxHeight");
		long dlFileEntryThumbnailMaxWidth = ParamUtil.getLong(
			actionRequest, "dlFileEntryThumbnailMaxWidth");
		String dlFileExtensions = getFileExtensions(
			actionRequest, "dlFileExtensions");
		long dlFileMaxSize = ParamUtil.getLong(actionRequest, "dlFileMaxSize");
		String journalImageExtensions = getFileExtensions(
			actionRequest, "journalImageExtensions");
		long journalImageSmallMaxSize = ParamUtil.getLong(
			actionRequest, "journalImageSmallMaxSize");
		String shoppingImageExtensions = getFileExtensions(
			actionRequest, "shoppingImageExtensions");
		long scImageMaxSize = ParamUtil.getLong(
			actionRequest, "scImageMaxSize");
		long scImageThumbnailMaxHeight = ParamUtil.getLong(
			actionRequest, "scImageThumbnailMaxHeight");
		long scImageThumbnailMaxWidth = ParamUtil.getLong(
			actionRequest, "scImageThumbnailMaxWidth");
		long shoppingImageLargeMaxSize = ParamUtil.getLong(
			actionRequest, "shoppingImageLargeMaxSize");
		long shoppingImageMediumMaxSize = ParamUtil.getLong(
			actionRequest, "shoppingImageMediumMaxSize");
		long shoppingImageSmallMaxSize = ParamUtil.getLong(
			actionRequest, "shoppingImageSmallMaxSize");
		long uploadServletRequestImplMaxSize = ParamUtil.getLong(
			actionRequest, "uploadServletRequestImplMaxSize");
		String uploadServletRequestImplTempDir = ParamUtil.getString(
			actionRequest, "uploadServletRequestImplTempDir");
		long usersImageMaxSize = ParamUtil.getLong(
			actionRequest, "usersImageMaxSize");

		portletPreferences.setValue(
			PropsKeys.DL_FILE_ENTRY_PREVIEWABLE_PROCESSOR_MAX_SIZE,
			String.valueOf(dlFileEntryPreviewableProcessorMaxSize));
		portletPreferences.setValue(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT,
			String.valueOf(dlFileEntryThumbnailMaxHeight));
		portletPreferences.setValue(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH,
			String.valueOf(dlFileEntryThumbnailMaxWidth));
		portletPreferences.setValue(
			PropsKeys.DL_FILE_EXTENSIONS, dlFileExtensions);
		portletPreferences.setValue(
			PropsKeys.DL_FILE_MAX_SIZE, String.valueOf(dlFileMaxSize));
		portletPreferences.setValue(
			PropsKeys.JOURNAL_IMAGE_EXTENSIONS, journalImageExtensions);
		portletPreferences.setValue(
			PropsKeys.JOURNAL_IMAGE_SMALL_MAX_SIZE,
			String.valueOf(journalImageSmallMaxSize));
		portletPreferences.setValue(
			PropsKeys.SHOPPING_IMAGE_EXTENSIONS, shoppingImageExtensions);
		portletPreferences.setValue(
			PropsKeys.SHOPPING_IMAGE_LARGE_MAX_SIZE,
			String.valueOf(shoppingImageLargeMaxSize));
		portletPreferences.setValue(
			PropsKeys.SHOPPING_IMAGE_MEDIUM_MAX_SIZE,
			String.valueOf(shoppingImageMediumMaxSize));
		portletPreferences.setValue(
			PropsKeys.SHOPPING_IMAGE_SMALL_MAX_SIZE,
			String.valueOf(shoppingImageSmallMaxSize));
		portletPreferences.setValue(
			PropsKeys.SC_IMAGE_MAX_SIZE, String.valueOf(scImageMaxSize));
		portletPreferences.setValue(
			PropsKeys.SC_IMAGE_THUMBNAIL_MAX_HEIGHT,
			String.valueOf(scImageThumbnailMaxHeight));
		portletPreferences.setValue(
			PropsKeys.SC_IMAGE_THUMBNAIL_MAX_WIDTH,
			String.valueOf(scImageThumbnailMaxWidth));
		portletPreferences.setValue(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
			String.valueOf(uploadServletRequestImplMaxSize));

		if (Validator.isNotNull(uploadServletRequestImplTempDir)) {
			portletPreferences.setValue(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
				uploadServletRequestImplTempDir);

			UploadServletRequestImpl.setTempDir(
				new File(uploadServletRequestImplTempDir));
		}

		portletPreferences.setValue(
			PropsKeys.USERS_IMAGE_MAX_SIZE, String.valueOf(usersImageMaxSize));

		portletPreferences.store();
	}

	protected void updateLogLevels(ActionRequest actionRequest)
		throws Exception {

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith("logLevel")) {
				String loggerName = name.substring(8);

				String priority = ParamUtil.getString(
					actionRequest, name, Level.INFO.toString());

				Log4JUtil.setLevel(loggerName, priority, true);
			}
		}
	}

	protected void updateMail(
			ActionRequest actionRequest, PortletPreferences portletPreferences)
		throws Exception {

		String advancedProperties = ParamUtil.getString(
			actionRequest, "advancedProperties");
		String pop3Host = ParamUtil.getString(actionRequest, "pop3Host");
		String pop3Password = ParamUtil.getString(
			actionRequest, "pop3Password");
		int pop3Port = ParamUtil.getInteger(actionRequest, "pop3Port");
		boolean pop3Secure = ParamUtil.getBoolean(actionRequest, "pop3Secure");
		String pop3User = ParamUtil.getString(actionRequest, "pop3User");
		String smtpHost = ParamUtil.getString(actionRequest, "smtpHost");
		String smtpPassword = ParamUtil.getString(
			actionRequest, "smtpPassword");
		int smtpPort = ParamUtil.getInteger(actionRequest, "smtpPort");
		boolean smtpSecure = ParamUtil.getBoolean(actionRequest, "smtpSecure");
		String smtpUser = ParamUtil.getString(actionRequest, "smtpUser");

		String storeProtocol = Account.PROTOCOL_POP;

		if (pop3Secure) {
			storeProtocol = Account.PROTOCOL_POPS;
		}

		String transportProtocol = Account.PROTOCOL_SMTP;

		if (smtpSecure) {
			transportProtocol = Account.PROTOCOL_SMTPS;
		}

		portletPreferences.setValue(PropsKeys.MAIL_SESSION_MAIL, "true");
		portletPreferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_ADVANCED_PROPERTIES,
			advancedProperties);
		portletPreferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_POP3_HOST, pop3Host);

		if (!pop3Password.equals(Portal.TEMP_OBFUSCATION_VALUE)) {
			portletPreferences.setValue(
				PropsKeys.MAIL_SESSION_MAIL_POP3_PASSWORD, pop3Password);
		}

		portletPreferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_POP3_PORT, String.valueOf(pop3Port));
		portletPreferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_POP3_USER, pop3User);
		portletPreferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_HOST, smtpHost);

		if (!smtpPassword.equals(Portal.TEMP_OBFUSCATION_VALUE)) {
			portletPreferences.setValue(
				PropsKeys.MAIL_SESSION_MAIL_SMTP_PASSWORD, smtpPassword);
		}

		portletPreferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_PORT, String.valueOf(smtpPort));
		portletPreferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_USER, smtpUser);
		portletPreferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_STORE_PROTOCOL, storeProtocol);
		portletPreferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_TRANSPORT_PROTOCOL, transportProtocol);

		portletPreferences.store();

		_mailService.clearSession();
	}

	protected void validateCaptcha(ActionRequest actionRequest)
		throws Exception {

		boolean reCaptchaEnabled = ParamUtil.getBoolean(
			actionRequest, "reCaptchaEnabled");

		if (!reCaptchaEnabled) {
			return;
		}

		String reCaptchaPrivateKey = ParamUtil.getString(
			actionRequest, "reCaptchaPrivateKey");
		String reCaptchaPublicKey = ParamUtil.getString(
			actionRequest, "reCaptchaPublicKey");

		if (Validator.isNull(reCaptchaPublicKey)) {
			SessionErrors.add(actionRequest, "reCaptchaPublicKey");
		}
		else if (Validator.isNull(reCaptchaPrivateKey)) {
			SessionErrors.add(actionRequest, "reCaptchaPrivateKey");
		}
	}

	protected void verifyMembershipPolicies() throws Exception {
		OrganizationMembershipPolicy organizationMembershipPolicy =
			OrganizationMembershipPolicyFactoryUtil.
				getOrganizationMembershipPolicy();

		organizationMembershipPolicy.verifyPolicy();

		RoleMembershipPolicy roleMembershipPolicy =
			RoleMembershipPolicyFactoryUtil.getRoleMembershipPolicy();

		roleMembershipPolicy.verifyPolicy();

		SiteMembershipPolicy siteMembershipPolicy =
			SiteMembershipPolicyFactoryUtil.getSiteMembershipPolicy();

		siteMembershipPolicy.verifyPolicy();

		UserGroupMembershipPolicy userGroupMembershipPolicy =
			UserGroupMembershipPolicyFactoryUtil.getUserGroupMembershipPolicy();

		userGroupMembershipPolicy.verifyPolicy();
	}

	protected void verifyPluginTables() throws Exception {
		_serviceComponentLocalService.verifyDB();
	}

	private static final String
		_CLASS_NAME_REINDEX_PORTAL_BACKGROUND_TASK_EXECUTOR =
			"com.liferay.portal.search.internal.background.task." +
				"ReindexPortalBackgroundTaskExecutor";

	private static final String
		_CLASS_NAME_REINDEX_SINGLE_INDEXER_BACKGROUND_TASK_EXECUTOR =
			"com.liferay.portal.search.internal.background.task." +
				"ReindexSingleIndexerBackgroundTaskExecutor";

	private static final Log _log = LogFactoryUtil.getLog(
		EditServerMVCActionCommand.class);

	private volatile MailService _mailService;
	private volatile ServiceComponentLocalService _serviceComponentLocalService;

}