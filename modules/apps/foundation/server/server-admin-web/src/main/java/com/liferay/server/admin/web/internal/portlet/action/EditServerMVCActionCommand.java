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

package com.liferay.server.admin.web.internal.portlet.action;

import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.mail.kernel.model.Account;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.petra.log4j.Log4JUtil;
import com.liferay.portal.captcha.CaptchaImpl;
import com.liferay.portal.captcha.recaptcha.ReCaptchaImpl;
import com.liferay.portal.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.portal.convert.ConvertException;
import com.liferay.portal.convert.ConvertProcess;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.image.GhostscriptUtil;
import com.liferay.portal.kernel.image.ImageMagickUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.SanitizerLogWrapper;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingHelperUtil;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.membershippolicy.OrganizationMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.OrganizationMembershipPolicyFactory;
import com.liferay.portal.kernel.security.membershippolicy.RoleMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.RoleMembershipPolicyFactory;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyFactory;
import com.liferay.portal.kernel.security.membershippolicy.UserGroupMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.UserGroupMembershipPolicyFactory;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.servlet.DirectServletRegistry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThreadUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.xuggler.XugglerInstallException;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portal.security.lang.DoPrivilegedBean;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.admin.util.CleanUpPermissionsUtil;
import com.liferay.portlet.admin.util.CleanUpPortletPreferencesUtil;

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

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

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
		else if (cmd.equals("cleanUpPortletPreferences")) {
			CleanUpPortletPreferencesUtil.
				cleanUpLayoutRevisionPortletPreferences();
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
			try {
				installXuggler(actionRequest, actionResponse);
			}
			catch (XugglerInstallException xie) {
				SessionErrors.add(
					actionRequest, XugglerInstallException.class.getName(),
					xie);
			}
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
		_multiVMPool.clear();
	}

	protected void cacheServlet() throws Exception {
		_directServletRegistry.clearServlets();
	}

	protected void cacheSingle() throws Exception {
		_singleVMPool.clear();
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

	protected void reindex(final ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, Serializable> taskContextMap = new HashMap<>();

		String className = ParamUtil.getString(actionRequest, "className");

		if (!ParamUtil.getBoolean(actionRequest, "blocking")) {
			_indexWriterHelper.reindex(
				themeDisplay.getUserId(), "reindex",
				_portalInstancesLocalService.getCompanyIds(), className,
				taskContextMap);

			return;
		}

		final String jobName = "reindex-".concat(_portalUUID.generate());

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		MessageListener messageListener = new MessageListener() {

			@Override
			public void receive(Message message)
				throws MessageListenerException {

				int status = message.getInteger("status");

				if ((status != BackgroundTaskConstants.STATUS_CANCELLED) &&
					(status != BackgroundTaskConstants.STATUS_FAILED) &&
					(status != BackgroundTaskConstants.STATUS_SUCCESSFUL)) {

					return;
				}

				if (!jobName.equals(message.getString("name"))) {
					return;
				}

				PortletSession portletSession =
					actionRequest.getPortletSession();

				long lastAccessedTime = portletSession.getLastAccessedTime();
				int maxInactiveInterval =
					portletSession.getMaxInactiveInterval();

				int extendedMaxInactiveIntervalTime =
					(int)(System.currentTimeMillis() - lastAccessedTime +
						maxInactiveInterval);

				portletSession.setMaxInactiveInterval(
					extendedMaxInactiveIntervalTime);

				countDownLatch.countDown();
			}

		};

		_messageBus.registerMessageListener(
			DestinationNames.BACKGROUND_TASK_STATUS, messageListener);

		try {
			_indexWriterHelper.reindex(
				themeDisplay.getUserId(), jobName,
				_portalInstancesLocalService.getCompanyIds(), className,
				taskContextMap);

			countDownLatch.await(
				ParamUtil.getLong(actionRequest, "timeout", Time.HOUR),
				TimeUnit.MILLISECONDS);
		}
		finally {
			_messageBus.unregisterMessageListener(
				DestinationNames.BACKGROUND_TASK_STATUS, messageListener);
		}
	}

	protected void reindexDictionaries(ActionRequest actionRequest)
		throws Exception {

		long[] companyIds = _portalInstancesLocalService.getCompanyIds();

		for (long companyId : companyIds) {
			_indexWriterHelper.indexQuerySuggestionDictionaries(companyId);
			_indexWriterHelper.indexSpellCheckerDictionaries(companyId);
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

			_scripting.exec(null, portletObjects, language, script);

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
			Class<?> clazz = captcha.getClass();

			portletPreferences.setValue(
				PropsKeys.CAPTCHA_ENGINE_IMPL, clazz.getName());

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
			_organizationMembershipPolicyFactory.
				getOrganizationMembershipPolicy();

		organizationMembershipPolicy.verifyPolicy();

		RoleMembershipPolicy roleMembershipPolicy =
			_roleMembershipPolicyFactory.getRoleMembershipPolicy();

		roleMembershipPolicy.verifyPolicy();

		SiteMembershipPolicy siteMembershipPolicy =
			_siteMembershipPolicyFactory.getSiteMembershipPolicy();

		siteMembershipPolicy.verifyPolicy();

		UserGroupMembershipPolicy userGroupMembershipPolicy =
			_userGroupMembershipPolicyFactory.getUserGroupMembershipPolicy();

		userGroupMembershipPolicy.verifyPolicy();
	}

	protected void verifyPluginTables() throws Exception {
		_serviceComponentLocalService.verifyDB();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditServerMVCActionCommand.class);

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private DirectServletRegistry _directServletRegistry;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private MailService _mailService;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private MultiVMPool _multiVMPool;

	@Reference
	private OrganizationMembershipPolicyFactory
		_organizationMembershipPolicyFactory;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

	@Reference
	private PortalUUID _portalUUID;

	@Reference
	private RoleMembershipPolicyFactory _roleMembershipPolicyFactory;

	@Reference
	private Scripting _scripting;

	@Reference
	private ServiceComponentLocalService _serviceComponentLocalService;

	@Reference
	private SingleVMPool _singleVMPool;

	@Reference
	private SiteMembershipPolicyFactory _siteMembershipPolicyFactory;

	@Reference
	private UserGroupMembershipPolicyFactory _userGroupMembershipPolicyFactory;

}