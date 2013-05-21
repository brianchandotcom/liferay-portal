/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.admin.action;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.captcha.CaptchaImpl;
import com.liferay.portal.captcha.recaptcha.ReCaptchaImpl;
import com.liferay.portal.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.portal.convert.ConvertProcess;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.image.GhostscriptUtil;
import com.liferay.portal.kernel.image.ImageMagickUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.messaging.BaseAsyncDestination;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.proxy.MessageValuesThreadLocal;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.servlet.DirectServletRegistryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ProgressStatusConstants;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThreadUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.search.lucene.LuceneIndexer;
import com.liferay.portal.search.lucene.cluster.LuceneClusterUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicy;
import com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicyFactoryUtil;
import com.liferay.portal.security.membershippolicy.RoleMembershipPolicy;
import com.liferay.portal.security.membershippolicy.RoleMembershipPolicyFactoryUtil;
import com.liferay.portal.security.membershippolicy.SiteMembershipPolicy;
import com.liferay.portal.security.membershippolicy.SiteMembershipPolicyFactoryUtil;
import com.liferay.portal.security.membershippolicy.UserGroupMembershipPolicy;
import com.liferay.portal.security.membershippolicy.UserGroupMembershipPolicyFactoryUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceComponentLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.admin.util.CleanUpPermissionsUtil;
import com.liferay.portlet.documentlibrary.util.DLPreviewableProcessor;
import com.liferay.util.log4j.Log4JUtil;

import java.io.File;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class EditServerAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.admin.error");

			return;
		}

		PortletPreferences preferences = PrefsPropsUtil.getPreferences();

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = null;

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

			setForward(actionRequest, ActionConstants.COMMON_NULL);

			return;
		}
		else if (cmd.equals("reindex")) {
			reindex(actionRequest);
		}
		else if (cmd.equals("reindexDictionaries")) {
			reindexDictionaries(actionRequest);
		}
		else if (cmd.equals("runScript")) {
			runScript(portletConfig, actionRequest, actionResponse);
		}
		else if (cmd.equals("shutdown")) {
			shutdown(actionRequest);
		}
		else if (cmd.equals("threadDump")) {
			threadDump();
		}
		else if (cmd.equals("updateCaptcha")) {
			updateCaptcha(actionRequest, preferences);
		}
		else if (cmd.equals("updateExternalServices")) {
			updateExternalServices(actionRequest, preferences);
		}
		else if (cmd.equals("updateFileUploads")) {
			updateFileUploads(actionRequest, preferences);
		}
		else if (cmd.equals("updateLogLevels")) {
			updateLogLevels(actionRequest);
		}
		else if (cmd.equals("updateMail")) {
			updateMail(actionRequest, preferences);
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
		WebCachePoolUtil.clear();
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

		String path = convertProcess.getPath();

		if (path != null) {
			PortletURL portletURL = actionResponseImpl.createRenderURL();

			portletURL.setParameter("struts_action", path);
			portletURL.setWindowState(WindowState.MAXIMIZED);

			return portletURL.toString();
		}
		else {
			MaintenanceUtil.maintain(portletSession.getId(), className);

			MessageBusUtil.sendMessage(
				DestinationNames.CONVERT_PROCESS, className);

			return null;
		}
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

		ProgressTracker progressTracker = new ProgressTracker(
			actionRequest, WebKeys.XUGGLER_INSTALL_STATUS);

		progressTracker.addProgress(
			ProgressStatusConstants.DOWNLOADING, 15, "downloading-xuggler");
		progressTracker.addProgress(
			ProgressStatusConstants.COPYING, 70, "copying-xuggler-files");

		progressTracker.initialize();

		String jarName = ParamUtil.getString(actionRequest, "jarName");

		try {
			XugglerUtil.installNativeLibraries(jarName, progressTracker);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("success", Boolean.TRUE);

			writeJSON(actionRequest, actionResponse, jsonObject);
		}
		catch (Exception e) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("exception", e.getMessage());
			jsonObject.put("success", Boolean.FALSE);

			writeJSON(actionRequest, actionResponse, jsonObject);
		}

		progressTracker.finish();
	}

	protected void reindex(ActionRequest actionRequest) throws Exception {
		String portletId = ParamUtil.getString(actionRequest, "portletId");

		long[] companyIds = PortalInstances.getCompanyIds();

		if (LuceneHelperUtil.isLoadIndexFromClusterEnabled()) {
			MessageValuesThreadLocal.setValue(
				ClusterLink.CLUSTER_FORWARD_MESSAGE, true);
		}

		Set<String> usedSearchEngineIds = new HashSet<String>();

		if (Validator.isNull(portletId)) {
			for (long companyId : companyIds) {
				try {
					LuceneIndexer luceneIndexer = new LuceneIndexer(companyId);

					luceneIndexer.reindex();

					usedSearchEngineIds.addAll(
						luceneIndexer.getUsedSearchEngineIds());
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
		else {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyIds[0], portletId);

			if (portlet == null) {
				return;
			}

			List<Indexer> indexers = portlet.getIndexerInstances();

			if (indexers == null) {
				return;
			}

			Set<String> searchEngineIds = new HashSet<String>();

			for (Indexer indexer : indexers) {
				searchEngineIds.add(indexer.getSearchEngineId());
			}

			for (String searchEngineId : searchEngineIds) {
				for (long companyId : companyIds) {
					SearchEngineUtil.deletePortletDocuments(
						searchEngineId, companyId, portletId);
				}
			}

			for (Indexer indexer : indexers) {
				for (long companyId : companyIds) {
					ShardUtil.pushCompanyService(companyId);

					try {
						indexer.reindex(
							new String[] {String.valueOf(companyId)});

						usedSearchEngineIds.add(indexer.getSearchEngineId());
					}
					catch (Exception e) {
						_log.error(e, e);
					}
					finally {
						ShardUtil.popCompanyService();
					}
				}
			}
		}

		if (!LuceneHelperUtil.isLoadIndexFromClusterEnabled()) {
			return;
		}

		Set<BaseAsyncDestination> searchWriterDestinations =
			new HashSet<BaseAsyncDestination>();

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		for (String usedSearchEngineId : usedSearchEngineIds) {
			String searchWriterDestinationName =
				SearchEngineUtil.getSearchWriterDestinationName(
					usedSearchEngineId);

			Destination destination = messageBus.getDestination(
				searchWriterDestinationName);

			if (destination instanceof BaseAsyncDestination) {
				BaseAsyncDestination baseAsyncDestination =
					(BaseAsyncDestination)destination;

				searchWriterDestinations.add(baseAsyncDestination);
			}
		}

		submitClusterIndexLoadingSyncJob(searchWriterDestinations, companyIds);
	}

	protected void reindexDictionaries(ActionRequest actionRequest)
		throws Exception {

		long[] companyIds = PortalInstances.getCompanyIds();

		for (long companyId : companyIds) {
			SearchEngineUtil.indexDictionaries(companyId);
		}
	}

	protected void runScript(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String language = ParamUtil.getString(actionRequest, "language");
		String script = ParamUtil.getString(actionRequest, "script");

		PortletContext portletContext = portletConfig.getPortletContext();

		Map<String, Object> portletObjects = ScriptingUtil.getPortletObjects(
			portletConfig, portletContext, actionRequest, actionResponse);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		UnsyncPrintWriter unsyncPrintWriter = UnsyncPrintWriterPool.borrow(
			unsyncByteArrayOutputStream);

		portletObjects.put("out", unsyncPrintWriter);

		try {
			SessionMessages.add(actionRequest, "language", language);
			SessionMessages.add(actionRequest, "script", script);

			ScriptingUtil.exec(null, portletObjects, language, script);

			unsyncPrintWriter.flush();

			SessionMessages.add(
				actionRequest, "scriptOutput",
				unsyncByteArrayOutputStream.toString());
		}
		catch (ScriptingException se) {
			SessionErrors.add(
				actionRequest, ScriptingException.class.getName(), se);

			_log.error(se.getMessage());
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

	protected void submitClusterIndexLoadingSyncJob(
			Set<BaseAsyncDestination> baseAsyncDestinations, long[] companyIds)
		throws Exception {

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(
				baseAsyncDestinations.size() + 1);

			sb.append("[");

			for (BaseAsyncDestination baseAsyncDestination :
					baseAsyncDestinations) {

				sb.append(baseAsyncDestination.getName());
				sb.append(", ");
			}

			sb.setStringAt("]", sb.index() - 1);

			_log.info(
				"Synchronizecluster index loading for destinations " +
					sb.toString());
		}

		int totalWorkersMaxSize = 0;

		for (BaseAsyncDestination baseAsyncDestination :
				baseAsyncDestinations) {

			totalWorkersMaxSize += baseAsyncDestination.getWorkersMaxSize();
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"There are " + totalWorkersMaxSize +
					" synchronization threads");
		}

		CountDownLatch countDownLatch = new CountDownLatch(
			totalWorkersMaxSize + 1);

		ClusterLoadingSyncJob slaveClusterLoadingSyncJob =
			new ClusterLoadingSyncJob(companyIds, countDownLatch, false);

		for (BaseAsyncDestination baseAsyncDestination :
				baseAsyncDestinations) {

			ThreadPoolExecutor threadPoolExecutor =
				PortalExecutorManagerUtil.getPortalExecutor(
					baseAsyncDestination.getName());

			for (int i = 0; i < baseAsyncDestination.getWorkersMaxSize(); i++) {
				threadPoolExecutor.execute(slaveClusterLoadingSyncJob);
			}
		}

		ClusterLoadingSyncJob masterClusterLoadingSyncJob =
			new ClusterLoadingSyncJob(companyIds, countDownLatch, true);

		ThreadPoolExecutor threadPoolExecutor =
			PortalExecutorManagerUtil.getPortalExecutor(
				EditServerAction.class.getName());

		threadPoolExecutor.execute(masterClusterLoadingSyncJob);
	}

	protected void threadDump() throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info(ThreadUtil.threadDump());
		}
		else {
			_log.error(
				"Thread dumps require the log level to be at least INFO for " +
					getClass().getName());
		}
	}

	protected void updateCaptcha(
			ActionRequest actionRequest, PortletPreferences preferences)
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
			preferences.setValue(
				PropsKeys.CAPTCHA_ENGINE_IMPL, captcha.getClass().getName());
			preferences.setValue(
				PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE,
				reCaptchaPrivateKey);
			preferences.setValue(
				PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC,
				reCaptchaPublicKey);

			preferences.store();

			CaptchaImpl captchaImpl = (CaptchaImpl)CaptchaUtil.getCaptcha();

			captchaImpl.setCaptcha(captcha);
		}
	}

	protected void updateExternalServices(
			ActionRequest actionRequest, PortletPreferences preferences)
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

		preferences.setValue(
			PropsKeys.IMAGEMAGICK_ENABLED, String.valueOf(imageMagickEnabled));
		preferences.setValue(
			PropsKeys.IMAGEMAGICK_GLOBAL_SEARCH_PATH, imageMagickPath);
		preferences.setValue(
			PropsKeys.OPENOFFICE_SERVER_ENABLED,
			String.valueOf(openOfficeEnabled));
		preferences.setValue(
			PropsKeys.OPENOFFICE_SERVER_PORT, String.valueOf(openOfficePort));
		preferences.setValue(
			PropsKeys.XUGGLER_ENABLED, String.valueOf(xugglerEnabled));

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith("imageMagickLimit")) {
				String key = name.substring(16, name.length()).toLowerCase();
				String value = ParamUtil.getString(actionRequest, name);

				preferences.setValue(
					PropsKeys.IMAGEMAGICK_RESOURCE_LIMIT + key, value);
			}
		}

		preferences.store();

		GhostscriptUtil.reset();
		ImageMagickUtil.reset();
	}

	protected void updateFileUploads(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

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

		preferences.setValue(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT,
			String.valueOf(dlFileEntryThumbnailMaxHeight));
		preferences.setValue(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH,
			String.valueOf(dlFileEntryThumbnailMaxWidth));
		preferences.setValue(PropsKeys.DL_FILE_EXTENSIONS, dlFileExtensions);
		preferences.setValue(
			PropsKeys.DL_FILE_MAX_SIZE, String.valueOf(dlFileMaxSize));
		preferences.setValue(
			PropsKeys.JOURNAL_IMAGE_EXTENSIONS, journalImageExtensions);
		preferences.setValue(
			PropsKeys.JOURNAL_IMAGE_SMALL_MAX_SIZE,
			String.valueOf(journalImageSmallMaxSize));
		preferences.setValue(
			PropsKeys.SHOPPING_IMAGE_EXTENSIONS, shoppingImageExtensions);
		preferences.setValue(
			PropsKeys.SHOPPING_IMAGE_LARGE_MAX_SIZE,
			String.valueOf(shoppingImageLargeMaxSize));
		preferences.setValue(
			PropsKeys.SHOPPING_IMAGE_MEDIUM_MAX_SIZE,
			String.valueOf(shoppingImageMediumMaxSize));
		preferences.setValue(
			PropsKeys.SHOPPING_IMAGE_SMALL_MAX_SIZE,
			String.valueOf(shoppingImageSmallMaxSize));
		preferences.setValue(
			PropsKeys.SC_IMAGE_MAX_SIZE, String.valueOf(scImageMaxSize));
		preferences.setValue(
			PropsKeys.SC_IMAGE_THUMBNAIL_MAX_HEIGHT,
			String.valueOf(scImageThumbnailMaxHeight));
		preferences.setValue(
			PropsKeys.SC_IMAGE_THUMBNAIL_MAX_WIDTH,
			String.valueOf(scImageThumbnailMaxWidth));
		preferences.setValue(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
			String.valueOf(uploadServletRequestImplMaxSize));

		if (Validator.isNotNull(uploadServletRequestImplTempDir)) {
			preferences.setValue(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
				uploadServletRequestImplTempDir);

			UploadServletRequestImpl.setTempDir(
				new File(uploadServletRequestImplTempDir));
		}

		preferences.setValue(
			PropsKeys.USERS_IMAGE_MAX_SIZE, String.valueOf(usersImageMaxSize));

		preferences.store();
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
			ActionRequest actionRequest, PortletPreferences preferences)
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

		preferences.setValue(PropsKeys.MAIL_SESSION_MAIL, "true");
		preferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_ADVANCED_PROPERTIES,
			advancedProperties);
		preferences.setValue(PropsKeys.MAIL_SESSION_MAIL_POP3_HOST, pop3Host);
		preferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_POP3_PASSWORD, pop3Password);
		preferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_POP3_PORT, String.valueOf(pop3Port));
		preferences.setValue(PropsKeys.MAIL_SESSION_MAIL_POP3_USER, pop3User);
		preferences.setValue(PropsKeys.MAIL_SESSION_MAIL_SMTP_HOST, smtpHost);
		preferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_PASSWORD, smtpPassword);
		preferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_PORT, String.valueOf(smtpPort));
		preferences.setValue(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER, smtpUser);
		preferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_STORE_PROTOCOL, storeProtocol);
		preferences.setValue(
			PropsKeys.MAIL_SESSION_MAIL_TRANSPORT_PROTOCOL, transportProtocol);

		preferences.store();

		MailServiceUtil.clearSession();
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
		ServiceComponentLocalServiceUtil.verifyDB();
	}

	private static Log _log = LogFactoryUtil.getLog(EditServerAction.class);

	private static MethodKey _loadIndexesFromClusterMethodKey = new MethodKey(
		LuceneClusterUtil.class, "loadIndexesFromCluster", long[].class,
		Address.class);

	private static class ClusterLoadingSyncJob implements Runnable {

		public ClusterLoadingSyncJob(
			long[] companyIds, CountDownLatch countDownLatch, boolean master) {

			_companyIds = companyIds;
			_countDownLatch = countDownLatch;
			_master = master;
		}

		public void run() {
			_countDownLatch.countDown();

			String logPrefix = StringPool.BLANK;

			if (_log.isInfoEnabled()) {
				Thread currentThread = Thread.currentThread();

				if (_master) {
					logPrefix =
						"Monitor thread name " + currentThread.getName() +
							" with thread ID " + currentThread.getId();
				}
				else {
					logPrefix =
						"Thread name " + currentThread.getName() +
							" with thread ID " + currentThread.getId();
				}
			}

			if (!_master && _log.isInfoEnabled()) {
				_log.info(
					logPrefix + " synchronized on latch. Waiting for others.");
			}

			try {
				if (_master) {
					_countDownLatch.await();
				}
				else {
					boolean result = _countDownLatch.await(
						PropsValues.LUCENE_CLUSTER_INDEX_LOADING_SYNC_TIMEOUT,
						TimeUnit.MILLISECONDS);

					if (!result) {
						_log.error(
							logPrefix + " timed out. You may need to " +
								"re-trigger a reindex process.");
					}
				}
			}
			catch (InterruptedException ie) {
				if (_master) {
					_log.error(
						logPrefix + " was interrupted. Skip cluster index " +
							"loading notification.",
						ie);

					return;
				}
				else {
					_log.error(
						logPrefix + " was interrupted. You may need to " +
							"re-trigger a reindex process.",
						ie);
				}
			}

			if (_master) {
				Address localClusterNodeAddress =
					ClusterExecutorUtil.getLocalClusterNodeAddress();

				ClusterRequest clusterRequest =
					ClusterRequest.createMulticastRequest(
						new MethodHandler(
							_loadIndexesFromClusterMethodKey, _companyIds,
							localClusterNodeAddress),
						true);

				try {
					ClusterExecutorUtil.execute(clusterRequest);
				}
				catch (SystemException se) {
					_log.error(
						"Unable to notify peers to start index loading", se);
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						logPrefix + " unlocked latch. Notified peers to " +
							"start index loading.");
				}
			}
		}

		private long[] _companyIds;
		private CountDownLatch _countDownLatch;
		private boolean _master;

	}

}