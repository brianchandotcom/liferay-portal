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

package com.liferay.exportimport.internal.staging;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetCollectionLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.exception.ExportImportContentProcessorException;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.exportimport.kernel.exception.ExportImportDocumentException;
import com.liferay.exportimport.kernel.exception.ExportImportIOException;
import com.liferay.exportimport.kernel.exception.ExportImportRuntimeException;
import com.liferay.exportimport.kernel.exception.LARFileException;
import com.liferay.exportimport.kernel.exception.LARFileSizeException;
import com.liferay.exportimport.kernel.exception.LARTypeException;
import com.liferay.exportimport.kernel.exception.LayoutImportException;
import com.liferay.exportimport.kernel.exception.MissingReferenceException;
import com.liferay.exportimport.kernel.exception.RemoteExportException;
import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.exportimport.kernel.staging.StagingURLHelper;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.LayoutPrototypeException;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchLayoutBranchException;
import com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.PortletIdException;
import com.liferay.portal.kernel.exception.RemoteOptionsException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.RecentLayoutBranch;
import com.liferay.portal.kernel.model.RecentLayoutRevision;
import com.liferay.portal.kernel.model.RecentLayoutSetBranch;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.TypedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.model.adapter.StagedTheme;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.RemoteAuthException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutBranchLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.RecentLayoutBranchLocalService;
import com.liferay.portal.kernel.service.RecentLayoutRevisionLocalService;
import com.liferay.portal.kernel.service.RecentLayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.http.GroupServiceHttp;
import com.liferay.portal.service.http.LayoutServiceHttp;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.service.http.StagingServiceHttp;
import com.liferay.portlet.exportimport.staging.ProxiedLayoutsThreadLocal;
import com.liferay.staging.StagingGroupHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Wesley Gong
 * @author Zsolt Balogh
 */
@Component(immediate = true, service = Staging.class)
public class StagingImpl implements Staging {

	@Override
	public <T extends BaseModel> void addModelToChangesetCollection(T model)
		throws PortalException {

		if (!(model instanceof StagedGroupedModel) ||
			ExportImportThreadLocal.isInitialLayoutStagingInProcess()) {

			return;
		}

		StagedGroupedModel stagedGroupedModel = (StagedGroupedModel)model;

		long groupId = stagedGroupedModel.getGroupId();

		if (groupId <= 0) {
			return;
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return;
		}

		if (!_stagingGroupHelper.isStagingGroup(group)) {
			return;
		}

		if (_stagedModelRepositoryHelper.isStagedModelInTrash(
				stagedGroupedModel)) {

			removeModelFromChangesetCollection(model);

			return;
		}

		if (stagedGroupedModel instanceof WorkflowedModel) {
			int[] exportableStatuses = {WorkflowConstants.STATUS_APPROVED};

			String className = ExportImportClassedModelUtil.getClassName(
				stagedGroupedModel);

			StagedModelDataHandler stagedModelDataHandler =
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					className);

			if (stagedModelDataHandler != null) {
				exportableStatuses =
					stagedModelDataHandler.getExportableStatuses();
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get staged model data handler for class name " +
						className);
			}

			WorkflowedModel workflowedModel =
				(WorkflowedModel)stagedGroupedModel;

			if (!ArrayUtil.contains(
					exportableStatuses, workflowedModel.getStatus())) {

				removeModelFromChangesetCollection(model);

				return;
			}
		}

		ChangesetCollection changesetCollection =
			_changesetCollectionLocalService.fetchOrAddChangesetCollection(
				groupId,
				StagingConstants.RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME);

		long classNameId = _classNameLocalService.getClassNameId(
			stagedGroupedModel.getModelClassName());
		long classPK = (long)stagedGroupedModel.getPrimaryKeyObj();

		_changesetEntryLocalService.fetchOrAddChangesetEntry(
			changesetCollection.getChangesetCollectionId(), classNameId,
			classPK);
	}

	@Override
	public long copyFromLive(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		long targetGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group stagingGroup = _groupLocalService.getGroup(targetGroupId);

		long sourceGroupId = stagingGroup.getLiveGroupId();

		boolean privateLayout = getPrivateLayout(portletRequest);
		long[] layoutIds = _exportImportHelper.getLayoutIds(
			portletRequest, targetGroupId);

		Map<String, String[]> parameterMap =
			_exportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);

		parameterMap.put(
			PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
			new String[] {Boolean.TRUE.toString()});

		Map<String, Serializable> publishLayoutLocalSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildPublishLayoutLocalSettingsMap(
					user, sourceGroupId, targetGroupId, privateLayout,
					layoutIds, parameterMap);

		ExportImportConfiguration exportImportConfiguration = null;

		String name = ParamUtil.getString(portletRequest, "name");

		if (Validator.isNotNull(name)) {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(), name,
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_LOCAL,
						publishLayoutLocalSettingsMap);
		}
		else {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_LOCAL,
						publishLayoutLocalSettingsMap);
		}

		return publishLayouts(user.getUserId(), exportImportConfiguration);
	}

	@Override
	public long copyFromLive(PortletRequest portletRequest, Portlet portlet)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = _portal.getScopeGroupId(
			_portal.getHttpServletRequest(portletRequest),
			portlet.getPortletId());
		long plid = ParamUtil.getLong(portletRequest, "plid");

		Map<String, String[]> parameterMap =
			_exportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);

		return publishPortlet(
			themeDisplay.getUserId(), scopeGroupId, plid,
			portlet.getPortletId(), parameterMap, true);
	}

	@Override
	public long copyRemoteLayouts(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		String remoteAddress = MapUtil.getString(settingsMap, "remoteAddress");
		int remotePort = MapUtil.getInteger(settingsMap, "remotePort");
		String remotePathContext = MapUtil.getString(
			settingsMap, "remotePathContext");
		boolean secureConnection = MapUtil.getBoolean(
			settingsMap, "secureConnection");
		boolean remotePrivateLayout = MapUtil.getBoolean(
			settingsMap, "remotePrivateLayout");

		return doCopyRemoteLayouts(
			exportImportConfiguration, remoteAddress, remotePort,
			remotePathContext, secureConnection, remotePrivateLayout);
	}

	@Override
	public long copyRemoteLayouts(long exportImportConfigurationId)
		throws PortalException {

		return copyRemoteLayouts(
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId));
	}

	@Override
	public long copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout)
		throws PortalException {

		return copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, null, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout);
	}

	@Override
	public long copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, String name,
			Map<String, String[]> parameterMap, String remoteAddress,
			int remotePort, String remotePathContext, boolean secureConnection,
			long remoteGroupId, boolean remotePrivateLayout)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		Map<String, Serializable> publishLayoutRemoteSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildPublishLayoutRemoteSettingsMap(
					user.getUserId(), sourceGroupId, privateLayout, layoutIdMap,
					parameterMap, remoteAddress, remotePort, remotePathContext,
					secureConnection, remoteGroupId, remotePrivateLayout,
					user.getLocale(), user.getTimeZone());

		ExportImportConfiguration exportImportConfiguration = null;

		if (Validator.isNotNull(name)) {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(), name,
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_REMOTE,
						publishLayoutRemoteSettingsMap);
		}
		else {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_REMOTE,
						publishLayoutRemoteSettingsMap);
		}

		return doCopyRemoteLayouts(
			exportImportConfiguration, remoteAddress, remotePort,
			remotePathContext, secureConnection, remotePrivateLayout);
	}

	@Override
	public void deleteLastImportSettings(Group liveGroup, boolean privateLayout)
		throws PortalException {

		List<Layout> layouts = _layoutLocalService.getLayouts(
			liveGroup.getGroupId(), privateLayout);

		for (Layout layout : layouts) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			Set<String> keys = new HashSet<>();

			for (String key : typeSettingsProperties.keySet()) {
				if (key.startsWith("last-import-")) {
					keys.add(key);
				}
			}

			if (keys.isEmpty()) {
				continue;
			}

			for (String key : keys) {
				typeSettingsProperties.remove(key);
			}

			_layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), typeSettingsProperties.toString());
		}
	}

	@Override
	public void deleteRecentLayoutRevisionId(
		HttpServletRequest httpServletRequest, long layoutSetBranchId,
		long plid) {

		deleteRecentLayoutRevisionId(
			_portal.getUserId(httpServletRequest), layoutSetBranchId, plid);
	}

	@Override
	public void deleteRecentLayoutRevisionId(
		long userId, long layoutSetBranchId, long plid) {

		RecentLayoutRevision recentLayoutRevision =
			_recentLayoutRevisionLocalService.fetchRecentLayoutRevision(
				userId, layoutSetBranchId, plid);

		if (recentLayoutRevision != null) {
			_recentLayoutRevisionLocalService.deleteRecentLayoutRevision(
				recentLayoutRevision);
		}
	}

	@Override
	public JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences) {

		JSONArray errorMessagesJSONArray = JSONFactoryUtil.createJSONArray();

		for (Map.Entry<String, MissingReference> missingReferenceEntry :
				missingReferences.entrySet()) {

			MissingReference missingReference =
				missingReferenceEntry.getValue();

			JSONObject errorMessageJSONObject =
				JSONFactoryUtil.createJSONObject();

			String className = missingReference.getClassName();
			Map<String, String> referrers = missingReference.getReferrers();

			if (className.equals(StagedTheme.class.getName())) {
				errorMessageJSONObject.put(
					"info",
					LanguageUtil.format(
						locale,
						"the-referenced-theme-x-is-not-deployed-in-the-" +
							"current-environment",
						missingReference.getClassPK(), false));
			}
			else if (referrers.size() == 1) {
				Set<Map.Entry<String, String>> referrerDisplayNames =
					referrers.entrySet();

				Iterator<Map.Entry<String, String>> iterator =
					referrerDisplayNames.iterator();

				Map.Entry<String, String> entry = iterator.next();

				String referrerDisplayName = entry.getKey();
				String referrerClassName = entry.getValue();

				if (referrerClassName.equals(Portlet.class.getName())) {
					referrerDisplayName = _portal.getPortletTitle(
						referrerDisplayName, locale);
				}

				errorMessageJSONObject.put(
					"info",
					LanguageUtil.format(
						locale, "referenced-by-a-x-x",
						new String[] {
							ResourceActionsUtil.getModelResource(
								locale, referrerClassName),
							referrerDisplayName
						},
						false));
			}
			else {
				errorMessageJSONObject.put(
					"info",
					LanguageUtil.format(
						locale, "referenced-by-x-elements", referrers.size(),
						true));
			}

			errorMessageJSONObject.put("name", missingReferenceEntry.getKey());

			Group group = _groupLocalService.fetchGroup(
				missingReference.getGroupId());

			if (group != null) {
				errorMessageJSONObject.put(
					"site",
					LanguageUtil.format(
						locale, "in-site-x", missingReference.getGroupId(),
						false));
			}

			errorMessageJSONObject.put(
				"type",
				ResourceActionsUtil.getModelResource(
					locale, missingReference.getClassName()));

			errorMessagesJSONArray.put(errorMessageJSONObject);
		}

		return errorMessagesJSONArray;
	}

	@Override
	public JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e,
		ExportImportConfiguration exportImportConfiguration) {

		String errorMessage = StringPool.BLANK;
		JSONArray errorMessagesJSONArray = null;
		int errorType = 0;
		JSONArray warningMessagesJSONArray = null;

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		Throwable cause = e.getCause();

		if (e.getCause() instanceof ConnectException) {
			Map settingsMap = exportImportConfiguration.getSettingsMap();

			String remoteAddress = MapUtil.getString(
				settingsMap, "remoteAddress");
			String remotePort = MapUtil.getString(settingsMap, "remotePort");

			String argument = remoteAddress + ":" + remotePort;

			errorMessage = LanguageUtil.format(
				resourceBundle,
				"could-not-connect-to-address-x.-please-verify-that-the-" +
					"specified-port-is-correct-and-that-the-remote-server-is-" +
						"configured-to-accept-requests-from-this-server",
				argument);

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof DuplicateFileEntryException) {
			errorMessage = LanguageUtil.get(
				locale, "please-enter-a-unique-document-name");
			errorType = ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION;
		}
		else if (e instanceof ExportImportContentProcessorException) {
			ExportImportContentProcessorException eicpe =
				(ExportImportContentProcessorException)e;

			if (eicpe.getType() ==
					ExportImportContentProcessorException.ARTICLE_NOT_FOUND) {

				if (Validator.isNotNull(eicpe.getStagedModelClassName())) {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						"unable-to-process-referenced-article-because-it-" +
							"cannot-be-found-with-key-x",
						String.valueOf(eicpe.getStagedModelClassPK()));
				}
				else {
					errorMessage = LanguageUtil.get(
						resourceBundle,
						"unable-to-process-referenced-article-because-it-" +
							"cannot-be-found");
				}
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof ExportImportContentValidationException) {
			ExportImportContentValidationException eicve =
				(ExportImportContentValidationException)e;

			if (eicve.getType() ==
					ExportImportContentValidationException.ARTICLE_NOT_FOUND) {

				if ((cause != null) && (cause.getLocalizedMessage() != null)) {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						"unable-to-validate-referenced-journal-article-x",
						cause.getLocalizedMessage());
				}
				else {
					errorMessage = LanguageUtil.get(
						resourceBundle,
						"unable-to-validate-referenced-journal-article");
				}
			}
			else if (eicve.getType() ==
						ExportImportContentValidationException.
							FILE_ENTRY_NOT_FOUND) {

				if (Validator.isNotNull(eicve.getStagedModelClassName())) {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						"unable-to-validate-referenced-file-entry-because-it-" +
							"cannot-be-found-with-the-following-parameters-x-" +
								"within-the-content-of-x-with-primary-key-x",
						new String[] {
							MapUtil.toString(eicve.getDlReferenceParameters()),
							eicve.getStagedModelClassName(),
							String.valueOf(eicve.getStagedModelPrimaryKeyObj())
						});
				}
				else {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						"unable-to-validate-referenced-file-entry-because-it-" +
							"cannot-be-found-with-the-following-parameters-x",
						eicve.getDlReferenceParameters());
				}
			}
			else if (eicve.getType() ==
						ExportImportContentValidationException.
							LAYOUT_GROUP_NOT_FOUND) {

				if (Validator.isNotNull(eicve.getStagedModelClassName())) {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						StringBundler.concat(
							"unable-to-validate-referenced-page-with-url-x-",
							"because-the-page-group-with-url-x-cannot-be-",
							"found-within-the-content-of-x-with-primary-key-x"),
						new String[] {
							eicve.getLayoutURL(), eicve.getGroupFriendlyURL(),
							eicve.getStagedModelClassName(),
							String.valueOf(eicve.getStagedModelPrimaryKeyObj())
						});
				}
				else {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						"unable-to-validate-referenced-page-with-url-x-" +
							"because-the-page-group-with-url-x-cannot-be-found",
						new String[] {
							eicve.getLayoutURL(), eicve.getGroupFriendlyURL()
						});
				}
			}
			else if (eicve.getType() ==
						ExportImportContentValidationException.
							LAYOUT_NOT_FOUND) {

				if (Validator.isNotNull(eicve.getStagedModelClassName())) {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						"unable-to-validate-referenced-page-because-it-" +
							"cannot-be-found-with-the-following-parameters-x-" +
								"within-the-content-of-x-with-primary-key-x",
						new String[] {
							MapUtil.toString(
								eicve.getLayoutReferenceParameters()),
							eicve.getStagedModelClassName(),
							String.valueOf(eicve.getStagedModelPrimaryKeyObj())
						});
				}
				else {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						"unable-to-validate-referenced-page-because-it-" +
							"cannot-be-found-with-the-following-parameters-x",
						eicve.getLayoutReferenceParameters());
				}
			}
			else if (eicve.getType() ==
						ExportImportContentValidationException.
							LAYOUT_WITH_URL_NOT_FOUND) {

				if (Validator.isNotNull(eicve.getStagedModelClassName())) {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						"unable-to-validate-referenced-page-because-it-" +
							"cannot-be-found-with-url-x-within-the-content-" +
								"of-x-with-primary-key-x",
						new String[] {
							eicve.getLayoutURL(),
							eicve.getStagedModelClassName(),
							String.valueOf(eicve.getStagedModelPrimaryKeyObj())
						});
				}
				else {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						"unable-to-validate-referenced-page-because-it-" +
							"cannot-be-found-with-url-x",
						eicve.getLayoutURL());
				}
			}
			else {
				if (Validator.isNotNull(eicve.getStagedModelClassName())) {
					errorMessage = LanguageUtil.format(
						resourceBundle,
						"unable-to-validate-content-of-x-with-primary-key-x-" +
							"in-x",
						new String[] {
							eicve.getClassName(),
							eicve.getStagedModelClassName(),
							String.valueOf(eicve.getStagedModelPrimaryKeyObj())
						});
				}
				else {
					errorMessage = LanguageUtil.format(
						resourceBundle, "unable-to-validate-content-in-x",
						eicve.getClassName());
				}
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof ExportImportDocumentException) {
			ExportImportDocumentException eide =
				(ExportImportDocumentException)e;

			if (eide.getType() ==
					ExportImportDocumentException.PORTLET_DATA_IMPORT) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-parse-xml-document-for-portlet-x-during-import",
					eide.getPortletId());
			}
			else if (eide.getType() ==
						ExportImportDocumentException.
							PORTLET_PREFERENCES_IMPORT) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-parse-xml-portlet-preferences-for-portlet-x-" +
						"while-importing-portlet-preferences",
					eide.getPortletId());
			}
			else {
				errorMessage = LanguageUtil.get(
					resourceBundle, "unable-to-parse-xml-document");
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if ((e instanceof ExportImportIOException) ||
				 ((cause instanceof SystemException) &&
				  (cause.getCause() instanceof ExportImportIOException))) {

			ExportImportIOException eiioe = null;

			if (e instanceof ExportImportIOException) {
				eiioe = (ExportImportIOException)e;
			}
			else {
				eiioe = (ExportImportIOException)cause.getCause();
			}

			if (eiioe.getType() ==
					ExportImportIOException.ADD_ZIP_ENTRY_BYTES) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-add-data-bytes-to-the-lar-file-with-path-x",
					eiioe.getFileName());
			}
			else if (eiioe.getType() ==
						ExportImportIOException.ADD_ZIP_ENTRY_STREAM) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-add-data-stream-to-the-lar-file-with-path-x",
					eiioe.getFileName());
			}
			else if (eiioe.getType() ==
						ExportImportIOException.ADD_ZIP_ENTRY_STRING) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-add-data-string-to-the-lar-file-with-path-x",
					eiioe.getFileName());
			}
			else if (eiioe.getType() == ExportImportIOException.LAYOUT_IMPORT) {
				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-process-lar-file-for-layout-import-while-" +
						"executing-x-due-to-a-file-system-error",
					eiioe.getClassName());
			}
			else if (eiioe.getType() ==
						ExportImportIOException.LAYOUT_IMPORT_FILE) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-process-lar-file-x-for-layout-import-while-" +
						"executing-x-due-to-a-file-system-error",
					new String[] {eiioe.getFileName(), eiioe.getClassName()});
			}
			else if (eiioe.getType() ==
						ExportImportIOException.LAYOUT_VALIDATE) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-process-lar-file-for-layout-import-validation-" +
						"while-executing-x-due-to-a-file-system-error",
					eiioe.getClassName());
			}
			else if (eiioe.getType() ==
						ExportImportIOException.LAYOUT_VALIDATE_FILE) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-process-lar-file-x-for-layout-import-" +
						"validation-while-executing-x-due-to-a-file-system-" +
							"error",
					new String[] {eiioe.getFileName(), eiioe.getClassName()});
			}
			else if (eiioe.getType() ==
						ExportImportIOException.PORTLET_EXPORT) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-create-the-export-lar-manifest-file-for-" +
						"portlet-x",
					eiioe.getPortletId());
			}
			else if (eiioe.getType() ==
						ExportImportIOException.PORTLET_IMPORT) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-process-lar-file-for-portlet-import-while-" +
						"executing-x-due-to-a-file-system-error",
					eiioe.getClassName());
			}
			else if (eiioe.getType() ==
						ExportImportIOException.PORTLET_IMPORT_FILE) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-process-lar-file-x-for-portlet-import-while-" +
						"executing-x-due-to-a-file-system-error",
					new String[] {eiioe.getFileName(), eiioe.getClassName()});
			}
			else if (eiioe.getType() ==
						ExportImportIOException.PORTLET_VALIDATE) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-process-lar-file-for-portlet-import-" +
						"validation-while-executing-x-due-to-a-file-system-" +
							"error",
					eiioe.getClassName());
			}
			else if (eiioe.getType() ==
						ExportImportIOException.PORTLET_VALIDATE_FILE) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-process-lar-file-x-for-portlet-import-" +
						"validation-while-executing-x-due-to-a-file-system-" +
							"error",
					new String[] {eiioe.getFileName(), eiioe.getClassName()});
			}
			else if (eiioe.getType() ==
						ExportImportIOException.PUBLISH_STAGING_REQUEST) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-complete-remote-staging-publication-request-x-" +
						"due-to-a-file-system-error",
					eiioe.getStagingRequestId());
			}
			else if (eiioe.getType() ==
						ExportImportIOException.STAGING_REQUEST_CHECKSUM) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-process-lar-file-pieces-for-remote-staging-" +
						"publication-because-lar-file-checksum-is-not-x",
					eiioe.getChecksum());
			}
			else if (eiioe.getType() ==
						ExportImportIOException.
							STAGING_REQUEST_REASSEMBLE_FILE) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-reassemble-lar-file-for-remote-staging-" +
						"publication-request-x",
					eiioe.getStagingRequestId());
			}
			else {
				errorMessage = LanguageUtil.format(
					resourceBundle, "x-failed-due-to-a-file-system-error",
					eiioe.getClassName());
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof ExportImportRuntimeException) {
			_log.error(e, e);

			ExportImportRuntimeException eire = (ExportImportRuntimeException)e;

			if (Validator.isNull(eire.getMessage())) {
				errorMessage = LanguageUtil.format(
					resourceBundle, "an-unexpected-error-occurred-within-x",
					eire.getClassName());
			}
			else {
				errorMessage = LanguageUtil.format(
					resourceBundle, "the-following-error-occurred-within-x-x",
					new String[] {eire.getClassName(), eire.getMessage()});
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof FileExtensionException) {
			errorMessage = LanguageUtil.format(
				locale,
				"document-names-must-end-with-one-of-the-following-extensions",
				".lar", false);
			errorType = ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
		}
		else if (e instanceof FileNameException) {
			errorMessage = LanguageUtil.get(
				locale, "please-enter-a-file-with-a-valid-file-name");
			errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
		}
		else if (e instanceof FileSizeException ||
				 e instanceof LARFileSizeException) {

			if ((exportImportConfiguration != null) &&
				((exportImportConfiguration.getType() ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_LOCAL) ||
				 (exportImportConfiguration.getType() ==
					 ExportImportConfigurationConstants.
						 TYPE_PUBLISH_LAYOUT_REMOTE) ||
				 (exportImportConfiguration.getType() ==
					 ExportImportConfigurationConstants.
						 TYPE_PUBLISH_PORTLET_LOCAL) ||
				 (exportImportConfiguration.getType() !=
					 ExportImportConfigurationConstants.
						 TYPE_PUBLISH_PORTLET_REMOTE))) {

				errorMessage = LanguageUtil.get(
					locale,
					"file-size-limit-exceeded.-please-ensure-that-the-file-" +
						"does-not-exceed-the-file-size-limit-in-both-the-" +
							"live-environment-and-the-staging-environment");
			}
			else {
				errorMessage = LanguageUtil.format(
					locale,
					"please-enter-a-file-with-a-valid-file-size-no-larger-" +
						"than-x",
					TextFormatter.formatStorageSize(
						_dlValidator.getMaxAllowableSize(), locale),
					false);
			}

			errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
		}
		else if (e instanceof LARTypeException) {
			LARTypeException lte = (LARTypeException)e;

			if (lte.getType() == LARTypeException.TYPE_COMPANY_GROUP) {
				errorMessage = LanguageUtil.format(
					resourceBundle, "a-x-can-only-be-imported-to-a-x",
					"global-site");
			}
			else if (lte.getType() == LARTypeException.TYPE_LAYOUT_PROTOTYPE) {
				errorMessage = LanguageUtil.format(
					resourceBundle, "a-x-can-only-be-imported-to-a-x",
					LanguageUtil.get(locale, "page-template"));
			}
			else if (lte.getType() == LARTypeException.TYPE_LAYOUT_SET) {
				errorMessage = LanguageUtil.format(
					resourceBundle, "a-x-can-only-be-imported-to-a-x", "site");
			}
			else if (lte.getType() ==
						LARTypeException.TYPE_LAYOUT_SET_PROTOTYPE) {

				errorMessage = LanguageUtil.format(
					resourceBundle, "a-x-can-only-be-imported-to-a-x",
					LanguageUtil.get(locale, "site-template"));
			}
			else {
				errorMessage = LanguageUtil.format(
					resourceBundle, "uploaded-lar-file-type-x-does-not-match-x",
					new Object[] {
						lte.getActualLARType(),
						StringUtil.merge(
							lte.getExpectedLARTypes(),
							StringPool.COMMA_AND_SPACE)
					});
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof LARFileException) {
			LARFileException lfe = (LARFileException)e;

			if (lfe.getType() == LARFileException.TYPE_INVALID_MANIFEST) {
				errorMessage = LanguageUtil.format(
					resourceBundle, "invalid-manifest.xml-x", lfe.getMessage());
			}
			else if (lfe.getType() == LARFileException.TYPE_MISSING_MANIFEST) {
				errorMessage = LanguageUtil.get(
					resourceBundle, "missing-manifest.xml");
			}
			else {
				errorMessage = LanguageUtil.get(
					locale, "please-specify-a-lar-file-to-import");
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if ((e instanceof LayoutImportException) ||
				 (cause instanceof LayoutImportException)) {

			LayoutImportException lie = null;

			if (e instanceof LayoutImportException) {
				lie = (LayoutImportException)e;
			}
			else {
				lie = (LayoutImportException)cause;
			}

			if (lie.getType() ==
					LayoutImportException.TYPE_WRONG_BUILD_NUMBER) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"lar-build-number-x-does-not-match-portal-build-number-x",
					lie.getArguments());
			}
			else if (lie.getType() ==
						LayoutImportException.TYPE_WRONG_LAR_SCHEMA_VERSION) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"lar-schema-version-x-does-not-match-deployed-export-" +
						"import-schema-version-x",
					lie.getArguments());
			}
			else if (lie.getType() ==
						LayoutImportException.
							TYPE_WRONG_PORTLET_SCHEMA_VERSION) {

				Object[] arguments = lie.getArguments();

				Portlet portlet = _portletLocalService.getPortletById(
					(String)arguments[1]);

				arguments[1] = portlet.getDisplayName();

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"applications's-schema-version-x-in-the-lar-is-not-valid-" +
						"for-the-deployed-application-x-with-schema-version-x",
					lie.getArguments());
			}
			else {
				errorMessage = e.getLocalizedMessage();
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof LayoutPrototypeException) {
			LayoutPrototypeException lpe = (LayoutPrototypeException)e;

			StringBundler sb = new StringBundler(4);

			sb.append("the-lar-file-could-not-be-imported-because-it-");
			sb.append("requires-page-templates-or-site-templates-that-could-");
			sb.append("not-be-found.-please-import-the-following-templates-");
			sb.append("manually");

			errorMessage = LanguageUtil.get(resourceBundle, sb.toString());

			errorMessagesJSONArray = JSONFactoryUtil.createJSONArray();

			List<Tuple> missingLayoutPrototypes =
				lpe.getMissingLayoutPrototypes();

			for (Tuple missingLayoutPrototype : missingLayoutPrototypes) {
				String layoutPrototypeUuid =
					(String)missingLayoutPrototype.getObject(1);

				JSONObject errorMessageJSONObject = JSONUtil.put(
					"info", layoutPrototypeUuid);

				String layoutPrototypeName =
					(String)missingLayoutPrototype.getObject(2);

				errorMessageJSONObject.put("name", layoutPrototypeName);

				String layoutPrototypeClassName =
					(String)missingLayoutPrototype.getObject(0);

				errorMessageJSONObject.put(
					"type",
					ResourceActionsUtil.getModelResource(
						locale, layoutPrototypeClassName));

				errorMessagesJSONArray.put(errorMessageJSONObject);
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof LocaleException) {
			LocaleException le = (LocaleException)e;

			errorMessage = LanguageUtil.format(
				locale,
				"the-available-languages-in-the-lar-file-x-do-not-match-the-" +
					"site's-available-languages-x",
				new String[] {
					StringUtil.merge(
						le.getSourceAvailableLocales(),
						StringPool.COMMA_AND_SPACE),
					StringUtil.merge(
						le.getTargetAvailableLocales(),
						StringPool.COMMA_AND_SPACE)
				},
				false);

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof MissingReferenceException) {
			MissingReferenceException mre = (MissingReferenceException)e;

			if ((exportImportConfiguration != null) &&
				((exportImportConfiguration.getType() ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_LOCAL) ||
				 (exportImportConfiguration.getType() ==
					 ExportImportConfigurationConstants.
						 TYPE_PUBLISH_LAYOUT_REMOTE) ||
				 (exportImportConfiguration.getType() ==
					 ExportImportConfigurationConstants.
						 TYPE_PUBLISH_PORTLET_LOCAL) ||
				 (exportImportConfiguration.getType() !=
					 ExportImportConfigurationConstants.
						 TYPE_PUBLISH_PORTLET_REMOTE))) {

				errorMessage = LanguageUtil.get(
					locale,
					"there-are-missing-references-that-could-not-be-found-in-" +
						"the-live-environment-the-following-elements-are-" +
							"published-from-their-own-site");
			}
			else {
				errorMessage = LanguageUtil.get(
					locale,
					"there-are-missing-references-that-could-not-be-found-in-" +
						"the-current-site");
			}

			MissingReferences missingReferences = mre.getMissingReferences();

			errorMessagesJSONArray = getErrorMessagesJSONArray(
				locale, missingReferences.getDependencyMissingReferences());

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
			warningMessagesJSONArray = getWarningMessagesJSONArray(
				locale, missingReferences.getWeakMissingReferences());
		}
		else if (e instanceof PortletDataException) {
			PortletDataException pde = (PortletDataException)e;

			String referrerClassName = pde.getStagedModelClassName();
			String referrerDisplayName = pde.getStagedModelDisplayName();

			String modelResource = ResourceActionsUtil.getModelResource(
				locale, referrerClassName);

			if (pde.getType() == PortletDataException.COMPANY_BEING_DELETED) {
				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-create-a-portlet-data-context-for-company-x-" +
						"because-it-is-being-deleted",
					String.valueOf(pde.getCompanyId()));
			}
			else if (pde.getType() ==
						PortletDataException.DELETE_PORTLET_DATA) {

				if (Validator.isNotNull(pde.getLocalizedMessage())) {
					errorMessage = LanguageUtil.format(
						locale,
						"the-following-error-in-x-while-deleting-its-data-" +
							"has-stopped-the-process-x",
						new String[] {
							_portal.getPortletTitle(pde.getPortletId(), locale),
							pde.getLocalizedMessage()
						},
						false);
				}
				else {
					errorMessage = LanguageUtil.format(
						locale,
						"an-unexpected-error-in-x-while-deleting-its-data-" +
							"has-stopped-the-process",
						new String[] {
							_portal.getPortletTitle(pde.getPortletId(), locale)
						},
						false);
				}
			}
			else if (pde.getType() ==
						PortletDataException.EXPORT_DATA_GROUP_ELEMENT) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-return-the-export-data-group-element-for-" +
						"group-x-because-the-root-data-element-is-not-" +
							"initialized",
					pde.getStagedModelClassName());
			}
			else if (pde.getType() ==
						PortletDataException.EXPORT_PORTLET_DATA) {

				if (Validator.isNotNull(pde.getLocalizedMessage())) {
					errorMessage = LanguageUtil.format(
						locale,
						"the-following-error-in-x-while-exporting-its-data-" +
							"has-stopped-the-process-x",
						new String[] {
							_portal.getPortletTitle(pde.getPortletId(), locale),
							pde.getLocalizedMessage()
						},
						false);
				}
				else {
					errorMessage = LanguageUtil.format(
						locale,
						"an-unexpected-error-in-x-while-exporting-its-data-" +
							"has-stopped-the-process",
						new String[] {
							_portal.getPortletTitle(pde.getPortletId(), locale)
						},
						false);
				}
			}
			else if (pde.getType() ==
						PortletDataException.EXPORT_PORTLET_PERMISSIONS) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-export-portlet-permissions-for-x-while-" +
						"processing-portlet-preferences-during-export",
					pde.getPortletId());
			}
			else if (pde.getType() ==
						PortletDataException.EXPORT_REFERENCED_TEMPLATE) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-export-referenced-article-template-for-x-" +
						"while-processing-portlet-preferences-during-export",
					pde.getPortletId());
			}
			else if (pde.getType() ==
						PortletDataException.EXPORT_STAGED_MODEL) {

				String localizedMessage = pde.getLocalizedMessage();

				if ((pde.getCause() instanceof ExportImportRuntimeException) &&
					Validator.isNull(pde.getMessage())) {

					ExportImportRuntimeException eire =
						(ExportImportRuntimeException)pde.getCause();

					localizedMessage = LanguageUtil.format(
						resourceBundle, eire.getMessageKey(), eire.getData());
				}

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"the-x-x-could-not-be-exported-because-of-the-following-" +
						"error-x",
					new String[] {
						modelResource, referrerDisplayName, localizedMessage
					},
					false);
			}
			else if (pde.getType() ==
						PortletDataException.IMPORT_DATA_GROUP_ELEMENT) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-return-the-import-data-group-element-for-" +
						"group-x-because-the-root-data-element-is-not-" +
							"initialized",
					pde.getStagedModelClassName());
			}
			else if (pde.getType() ==
						PortletDataException.IMPORT_PORTLET_DATA) {

				if (Validator.isNotNull(pde.getLocalizedMessage())) {
					errorMessage = LanguageUtil.format(
						locale,
						"the-following-error-in-x-while-importing-its-data-" +
							"has-stopped-the-process-x",
						new String[] {
							_portal.getPortletTitle(pde.getPortletId(), locale),
							pde.getLocalizedMessage()
						},
						false);
				}
				else {
					errorMessage = LanguageUtil.format(
						locale,
						"an-unexpected-error-in-x-while-importing-its-data-" +
							"has-stopped-the-process",
						new String[] {
							_portal.getPortletTitle(pde.getPortletId(), locale)
						},
						false);
				}
			}
			else if (pde.getType() ==
						PortletDataException.IMPORT_PORTLET_PERMISSIONS) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-import-portlet-permissions-for-x-while-" +
						"processing-portlet-preferences-during-import",
					pde.getPortletId());
			}
			else if (pde.getType() ==
						PortletDataException.IMPORT_STAGED_MODEL) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"the-x-x-could-not-be-imported-because-of-the-following-" +
						"error-x",
					new String[] {
						modelResource, referrerDisplayName,
						pde.getLocalizedMessage()
					},
					false);
			}
			else if (pde.getType() == PortletDataException.INVALID_GROUP) {
				errorMessage = LanguageUtil.format(
					locale,
					"the-x-x-could-not-be-exported-because-it-is-not-in-the-" +
						"currently-exported-group",
					new String[] {modelResource, referrerDisplayName}, false);
			}
			else if (pde.getType() == PortletDataException.MISSING_DEPENDENCY) {
				errorMessage = LanguageUtil.format(
					locale,
					"the-x-x-has-missing-references-that-could-not-be-found-" +
						"during-the-process",
					new String[] {modelResource, referrerDisplayName}, false);
			}
			else if (pde.getType() == PortletDataException.MISSING_REFERENCE) {
				errorMessage = LanguageUtil.format(
					locale,
					"the-x-x-missing-reference-could-not-be-found-during-the-" +
						"process",
					new String[] {modelResource, referrerDisplayName}, false);
			}
			else if (pde.getType() ==
						PortletDataException.PREPARE_MANIFEST_SUMMARY) {

				if (Validator.isNotNull(pde.getLocalizedMessage())) {
					errorMessage = LanguageUtil.format(
						locale,
						"the-following-error-in-x-while-preparing-its-" +
							"manifest-has-stopped-the-process-x",
						new String[] {
							_portal.getPortletTitle(pde.getPortletId(), locale),
							pde.getLocalizedMessage()
						},
						false);
				}
				else {
					errorMessage = LanguageUtil.format(
						locale,
						"an-unexpected-error-in-x-while-preparing-its-" +
							"manifest-has-stopped-the-process",
						new String[] {
							_portal.getPortletTitle(pde.getPortletId(), locale)
						},
						false);
				}
			}
			else if (pde.getType() == PortletDataException.STATUS_IN_TRASH) {
				errorMessage = LanguageUtil.format(
					locale,
					"the-x-x-could-not-be-exported-because-it-is-in-the-" +
						"recycle-bin",
					new String[] {modelResource, referrerDisplayName}, false);
			}
			else if (pde.getType() == PortletDataException.STATUS_UNAVAILABLE) {
				errorMessage = LanguageUtil.format(
					locale,
					"the-x-x-could-not-be-exported-because-its-workflow-" +
						"status-is-not-exportable",
					new String[] {modelResource, referrerDisplayName}, false);
			}
			else if (pde.getType() ==
						PortletDataException.
							UPDATE_JOURNAL_CONTENT_SEARCH_DATA) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-update-journal-content-search-data-for-x-" +
						"while-processing-portlet-preferences-during-import",
					pde.getPortletId());
			}
			else if (pde.getType() ==
						PortletDataException.UPDATE_PORTLET_PREFERENCES) {

				errorMessage = LanguageUtil.format(
					resourceBundle,
					"unable-to-update-portlet-preferences-for-x-during-import",
					pde.getPortletId());
			}
			else if (Validator.isNotNull(referrerDisplayName)) {
				errorMessage = LanguageUtil.format(
					resourceBundle,
					"the-following-error-occurred-while-processing-the-x-x-x",
					new String[] {
						modelResource, referrerDisplayName,
						e.getLocalizedMessage()
					});
			}
			else {
				errorMessage = e.getLocalizedMessage();
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof PortletIdException) {
			PortletIdException pie = (PortletIdException)e;

			Portlet portlet = _portletLocalService.getPortletById(
				pie.getMessage());

			errorMessage = LanguageUtil.format(
				resourceBundle, "a-x-can-only-be-imported-to-a-x",
				portlet.getDisplayName() + " Portlet");

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof UploadRequestSizeException) {
			errorMessage = LanguageUtil.format(
				resourceBundle,
				"upload-request-reached-the-maximum-permitted-size-of-x-bytes",
				String.valueOf(
					UploadServletRequestConfigurationHelperUtil.getMaxSize()));
			errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
		}
		else {
			errorMessage = e.getLocalizedMessage();
			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}

		JSONObject exceptionMessagesJSONObject = JSONUtil.put(
			"message", errorMessage);

		if ((errorMessagesJSONArray != null) &&
			(errorMessagesJSONArray.length() > 0)) {

			exceptionMessagesJSONObject.put(
				"messageListItems", errorMessagesJSONArray);
		}

		exceptionMessagesJSONObject.put("status", errorType);

		if ((warningMessagesJSONArray != null) &&
			(warningMessagesJSONArray.length() > 0)) {

			exceptionMessagesJSONObject.put(
				"warningMessages", warningMessagesJSONArray);
		}

		return exceptionMessagesJSONObject;
	}

	@Override
	public Group getLiveGroup(Group group) {
		if (group == null) {
			return null;
		}

		if (group.isStagingGroup() && !group.isStagedRemotely()) {
			return group.getLiveGroup();
		}

		return group;
	}

	@Override
	public Group getLiveGroup(long groupId) {
		if (groupId <= 0) {
			return null;
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		if (group.isStagingGroup() && !group.isStagedRemotely()) {
			return group.getLiveGroup();
		}

		return group;
	}

	@Override
	public long getLiveGroupId(long groupId) {
		Group group = getLiveGroup(groupId);

		if (group == null) {
			return groupId;
		}

		return group.getGroupId();
	}

	@Override
	public Group getPermissionStagingGroup(Group group) {
		if (group == null) {
			return null;
		}

		Group stagingGroup = group;

		if (!group.isStagedRemotely() && group.hasStagingGroup()) {
			try {
				PermissionChecker permissionChecker =
					PermissionThreadLocal.getPermissionChecker();

				long scopeGroupId = stagingGroup.getGroupId();

				boolean hasManageStagingPermission =
					GroupPermissionUtil.contains(
						permissionChecker, scopeGroupId,
						ActionKeys.MANAGE_STAGING);
				boolean hasPublishStagingPermission =
					GroupPermissionUtil.contains(
						permissionChecker, scopeGroupId,
						ActionKeys.PUBLISH_STAGING);
				boolean hasViewStagingPermission = GroupPermissionUtil.contains(
					permissionChecker, scopeGroupId, ActionKeys.VIEW_STAGING);

				if (hasManageStagingPermission || hasPublishStagingPermission ||
					hasViewStagingPermission) {

					stagingGroup = group.getStagingGroup();
				}
			}
			catch (Exception e) {
			}
		}

		return stagingGroup;
	}

	@Override
	public long getRecentLayoutRevisionId(
			HttpServletRequest httpServletRequest, long layoutSetBranchId,
			long plid)
		throws PortalException {

		return getRecentLayoutRevisionId(
			_portal.getUserId(httpServletRequest), layoutSetBranchId, plid);
	}

	@Override
	public long getRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws PortalException {

		return getRecentLayoutRevisionId(
			user.getUserId(), layoutSetBranchId, plid);
	}

	@Override
	public long getRecentLayoutSetBranchId(
		HttpServletRequest httpServletRequest, long layoutSetId) {

		RecentLayoutSetBranch recentLayoutSetBranch =
			_recentLayoutSetBranchLocalService.fetchRecentLayoutSetBranch(
				_portal.getUserId(httpServletRequest), layoutSetId);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch.getLayoutSetBranchId();
		}

		return 0;
	}

	@Override
	public long getRecentLayoutSetBranchId(User user, long layoutSetId) {
		RecentLayoutSetBranch recentLayoutSetBranch =
			_recentLayoutSetBranchLocalService.fetchRecentLayoutSetBranch(
				user.getUserId(), layoutSetId);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch.getLayoutSetBranchId();
		}

		return 0;
	}

	@Override
	public long getRemoteLayoutPlid(long userId, long stagingGroupId, long plid)
		throws PortalException {

		Group stagingGroup = _groupLocalService.fetchGroup(stagingGroupId);
		User user = _userLocalService.fetchUser(userId);

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			_stagingURLHelper.buildRemoteURL(
				stagingGroup.getTypeSettingsProperties()),
			user.getLogin(), user.getPassword(), user.isPasswordEncrypted());

		Layout layout = _layoutLocalService.fetchLayout(plid);

		return LayoutServiceHttp.getLayoutPlid(
			httpPrincipal, layout.getUuid(),
			stagingGroup.getRemoteLiveGroupId(), layout.isPrivateLayout());
	}

	@Override
	public String getRemoteSiteURL(Group stagingGroup, boolean privateLayout)
		throws PortalException {

		if (!stagingGroup.isStagedRemotely()) {
			return StringPool.BLANK;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		if (stagingGroup.isLayout()) {
			stagingGroup = stagingGroup.getParentGroup();
		}

		UnicodeProperties typeSettingsProperties =
			stagingGroup.getTypeSettingsProperties();

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			_stagingURLHelper.buildRemoteURL(typeSettingsProperties),
			user.getLogin(), user.getPassword(), user.isPasswordEncrypted());

		long remoteGroupId = GetterUtil.getLong(
			typeSettingsProperties.getProperty("remoteGroupId"));
		boolean secureConnection = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("secureConnection"));

		String groupDisplayURL = GroupServiceHttp.getGroupDisplayURL(
			httpPrincipal, remoteGroupId, privateLayout, secureConnection);

		try {
			URL remoteSiteURL = new URL(groupDisplayURL);

			String remoteAddress = typeSettingsProperties.getProperty(
				"remoteAddress");

			remoteSiteURL = new URL(
				remoteSiteURL.getProtocol(), remoteAddress,
				remoteSiteURL.getPort(), remoteSiteURL.getFile());

			return remoteSiteURL.toString();
		}
		catch (MalformedURLException murle) {
			throw new PortalException(murle);
		}
	}

	@Override
	public String getSchedulerGroupName(String destinationName, long groupId) {
		return destinationName.concat(
			StringPool.SLASH
		).concat(
			String.valueOf(groupId)
		);
	}

	@Override
	public String getStagedPortletId(String portletId) {
		String key = portletId;

		if (key.startsWith(StagingConstants.STAGED_PORTLET)) {
			return key;
		}

		return StagingConstants.STAGED_PORTLET.concat(portletId);
	}

	@Override
	public long[] getStagingAndLiveGroupIds(long groupId)
		throws PortalException {

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return new long[] {groupId, 0L};
		}

		Group stagingGroup = group.getStagingGroup();

		if (stagingGroup != null) {
			return new long[] {stagingGroup.getGroupId(), groupId};
		}

		Group liveGroup = group.getLiveGroup();

		if (liveGroup != null) {
			return new long[] {groupId, liveGroup.getGroupId()};
		}

		return new long[] {groupId, 0L};
	}

	@Override
	public Group getStagingGroup(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		Group stagingGroup = group;

		if (!group.isStagedRemotely() && group.hasStagingGroup()) {
			stagingGroup = group.getStagingGroup();
		}

		return stagingGroup;
	}

	@Override
	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences) {

		JSONArray warningMessagesJSONArray = JSONFactoryUtil.createJSONArray();

		for (Map.Entry<String, MissingReference> entry :
				missingReferences.entrySet()) {

			MissingReference missingReference = entry.getValue();

			Map<String, String> referrers = missingReference.getReferrers();

			JSONObject errorMessageJSONObject =
				JSONFactoryUtil.createJSONObject();

			if (Validator.isNotNull(missingReference.getClassName())) {
				errorMessageJSONObject.put(
					"info",
					LanguageUtil.format(
						locale,
						"the-original-x-does-not-exist-in-the-current-" +
							"environment",
						ResourceActionsUtil.getModelResource(
							locale, missingReference.getClassName()),
						false));
			}

			errorMessageJSONObject.put(
				"size", referrers.size()
			).put(
				"type",
				ResourceActionsUtil.getModelResource(locale, entry.getKey())
			);

			warningMessagesJSONArray.put(errorMessageJSONObject);
		}

		return warningMessagesJSONArray;
	}

	@Override
	public WorkflowTask getWorkflowTask(
			long userId, LayoutRevision layoutRevision)
		throws PortalException {

		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				layoutRevision.getCompanyId(), layoutRevision.getGroupId(),
				LayoutRevision.class.getName(),
				layoutRevision.getLayoutRevisionId());

		if (workflowInstanceLink == null) {
			return null;
		}

		List<WorkflowTask> workflowTasks =
			WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(
				layoutRevision.getCompanyId(), userId,
				workflowInstanceLink.getWorkflowInstanceId(), false, 0, 1,
				null);

		if (!workflowTasks.isEmpty()) {
			return workflowTasks.get(0);
		}

		return null;
	}

	@Override
	public boolean hasWorkflowTask(long userId, LayoutRevision layoutRevision)
		throws PortalException {

		WorkflowTask workflowTask = getWorkflowTask(userId, layoutRevision);

		if (workflowTask != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isChangeTrackingEnabled(long companyId) {
		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(companyId, 0);

		if (ctPreferences == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isGroupAccessible(Group group, Group fromGroup) {
		if (fromGroup == null) {
			long companyId = group.getCompanyId();

			try {
				Company company = _companyLocalService.getCompany(companyId);

				Group companyGroup = company.getGroup();

				if (group.equals(companyGroup)) {
					return true;
				}
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn("Company group does not exist", pe);
				}
			}

			return false;
		}

		if (group.equals(fromGroup)) {
			return true;
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			group.isStagingGroup()) {

			return false;
		}

		if (group.hasStagingGroup() &&
			fromGroup.equals(group.getStagingGroup())) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isGroupAccessible(long groupId, long fromGroupId)
		throws PortalException {

		return isGroupAccessible(
			_groupLocalService.getGroup(groupId),
			_groupLocalService.getGroup(fromGroupId));
	}

	@Override
	public boolean isIncomplete(Layout layout) {
		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if ((layoutRevision != null) &&
			isLayoutRevisionIncomplete(
				layout.getPlid(), layoutRevision,
				layoutRevision.getLayoutSetBranchId())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isIncomplete(Layout layout, long layoutSetBranchId) {
		return isLayoutRevisionIncomplete(
			layout.getPlid(), LayoutStagingUtil.getLayoutRevision(layout),
			layoutSetBranchId);
	}

	@Override
	public boolean isRemoteLayoutHasPortletId(
		long userId, long stagingGroupId, long plid, String portletId) {

		Group stagingGroup = _groupLocalService.fetchGroup(stagingGroupId);
		User user = _userLocalService.fetchUser(userId);

		try {
			HttpPrincipal httpPrincipal = new HttpPrincipal(
				_stagingURLHelper.buildRemoteURL(
					stagingGroup.getTypeSettingsProperties()),
				user.getLogin(), user.getPassword(),
				user.isPasswordEncrypted());

			return LayoutServiceHttp.hasPortletId(
				httpPrincipal, plid, portletId);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to determine if remote layout ", plid,
						" contains portlet ", portletId),
					pe);
			}
		}

		return false;
	}

	@Override
	public void populateLastPublishDateCounts(
			PortletDataContext portletDataContext,
			StagedModelType[] stagedModelTypes)
		throws PortalException {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		ChangesetCollection changesetCollection =
			_changesetCollectionLocalService.fetchChangesetCollection(
				portletDataContext.getScopeGroupId(),
				StagingConstants.RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME);

		for (StagedModelType stagedModelType : stagedModelTypes) {
			long modelAdditionCount = manifestSummary.getModelAdditionCount(
				stagedModelType);

			if (modelAdditionCount > -1) {
				continue;
			}

			if (changesetCollection != null) {
				if (stagedModelType.getReferrerClassName() == null) {
					modelAdditionCount =
						_changesetEntryLocalService.getChangesetEntriesCount(
							changesetCollection.getChangesetCollectionId(),
							stagedModelType.getClassNameId());
				}
				else {
					StagedModelRepository<?> stagedModelRepository =
						StagedModelRepositoryRegistryUtil.
							getStagedModelRepository(
								stagedModelType.getClassName());

					if (stagedModelRepository != null) {
						List<ChangesetEntry> changesetEntries =
							_changesetEntryLocalService.getChangesetEntries(
								changesetCollection.getChangesetCollectionId(),
								stagedModelType.getClassNameId());

						modelAdditionCount = 0;

						for (ChangesetEntry changesetEntry : changesetEntries) {
							StagedModel stagedModel =
								stagedModelRepository.getStagedModel(
									changesetEntry.getClassPK());

							if (stagedModel instanceof TypedModel) {
								TypedModel typedModel = (TypedModel)stagedModel;

								if (Objects.equals(
										typedModel.getClassName(),
										stagedModelType.
											getReferrerClassName())) {

									modelAdditionCount++;
								}
							}
						}
					}
				}

				manifestSummary.addModelAdditionCount(
					stagedModelType, modelAdditionCount);
			}

			long modelDeletionCount = _exportImportHelper.getModelDeletionCount(
				portletDataContext, stagedModelType);

			manifestSummary.addModelDeletionCount(
				stagedModelType, modelDeletionCount);
		}
	}

	@Override
	public void populateLastPublishDateCounts(
			PortletDataContext portletDataContext, String[] classNames)
		throws PortalException {

		if (ArrayUtil.isEmpty(classNames)) {
			return;
		}

		StagedModelType[] stagedModelTypes =
			new StagedModelType[classNames.length];

		for (int i = 0; i < classNames.length; i++) {
			stagedModelTypes[i] = new StagedModelType(classNames[i]);
		}

		populateLastPublishDateCounts(portletDataContext, stagedModelTypes);
	}

	@Override
	public long publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws PortalException {

		Map<String, String[]> parameterMap =
			_exportImportConfigurationParameterMapFactory.buildParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});

		Layout layout = _layoutLocalService.getLayout(plid);

		List<Layout> layouts = new ArrayList<>();

		layouts.add(layout);

		List<Layout> parentLayouts =
			_exportImportHelper.getMissingParentLayouts(layout, liveGroupId);

		layouts.addAll(parentLayouts);

		if (includeChildren) {
			layouts.addAll(layout.getAllChildren());
		}

		return publishLayouts(
			userId, layout.getGroupId(), liveGroupId, layout.isPrivateLayout(),
			_exportImportHelper.getLayoutIds(layouts), parameterMap);
	}

	@Override
	public long publishLayouts(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		String backgroundTaskName = MapUtil.getString(
			parameterMap, "name", exportImportConfiguration.getName());

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				"exportImportConfigurationId",
				exportImportConfiguration.getExportImportConfigurationId()
			).put(
				"privateLayout",
				MapUtil.getBoolean(settingsMap, "privateLayout")
			).build();

		BackgroundTask backgroundTask =
			_backgroundTaskManager.addBackgroundTask(
				userId, exportImportConfiguration.getGroupId(),
				backgroundTaskName,
				BackgroundTaskExecutorNames.
					LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR,
				taskContextMap, new ServiceContext());

		return backgroundTask.getBackgroundTaskId();
	}

	@Override
	public long publishLayouts(long userId, long exportImportConfigurationId)
		throws PortalException {

		return publishLayouts(
			userId,
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId));
	}

	@Override
	public long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap)
		throws PortalException {

		return publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIds,
			null, parameterMap);
	}

	@Override
	public long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds, String name,
			Map<String, String[]> parameterMap)
		throws PortalException {

		parameterMap.put(
			PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
			new String[] {Boolean.TRUE.toString()});

		Map<String, Serializable> publishLayoutLocalSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildPublishLayoutLocalSettingsMap(
					_userLocalService.getUser(userId), sourceGroupId,
					targetGroupId, privateLayout, layoutIds, parameterMap);

		ExportImportConfiguration exportImportConfiguration = null;

		if (Validator.isNotNull(name)) {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						userId, name,
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_LOCAL,
						publishLayoutLocalSettingsMap);
		}
		else {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						userId,
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_LOCAL,
						publishLayoutLocalSettingsMap);
		}

		return publishLayouts(userId, exportImportConfiguration);
	}

	@Override
	public long publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException {

		List<Layout> sourceGroupLayouts = _layoutLocalService.getLayouts(
			sourceGroupId, privateLayout);

		return publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout,
			_exportImportHelper.getLayoutIds(sourceGroupLayouts), parameterMap);
	}

	@Override
	public long publishPortlet(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				"exportImportConfigurationId",
				exportImportConfiguration.getExportImportConfigurationId()
			).build();

		String backgroundTaskExecutor =
			BackgroundTaskExecutorNames.
				PORTLET_STAGING_BACKGROUND_TASK_EXECUTOR;

		if (exportImportConfiguration.getType() ==
				ExportImportConfigurationConstants.
					TYPE_PUBLISH_PORTLET_REMOTE) {

			backgroundTaskExecutor =
				BackgroundTaskExecutorNames.
					PORTLET_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR;

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			Map<String, String[]> parameterMap =
				(HashMap<String, String[]>)settingsMap.get("parameterMap");

			long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");

			Group sourceGroup = _groupLocalService.getGroup(sourceGroupId);

			UnicodeProperties typeSettingsProperties =
				sourceGroup.getTypeSettingsProperties();

			String remoteAddress = MapUtil.getString(
				parameterMap, "remoteAddress",
				typeSettingsProperties.getProperty("remoteAddress"));
			int remotePort = MapUtil.getInteger(
				parameterMap, "remotePort",
				GetterUtil.getInteger(
					typeSettingsProperties.getProperty("remotePort")));
			String remotePathContext = MapUtil.getString(
				parameterMap, "remotePathContext",
				typeSettingsProperties.getProperty("remotePathContext"));
			boolean secureConnection = MapUtil.getBoolean(
				parameterMap, "secureConnection",
				GetterUtil.getBoolean(
					typeSettingsProperties.getProperty("secureConnection")));

			_groupLocalService.validateRemote(
				sourceGroupId, remoteAddress, remotePort, remotePathContext,
				secureConnection, sourceGroup.getRemoteLiveGroupId());

			String remoteURL = _stagingURLHelper.buildRemoteURL(
				remoteAddress, remotePort, remotePathContext, secureConnection);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			User user = permissionChecker.getUser();

			HttpPrincipal httpPrincipal = new HttpPrincipal(
				remoteURL, user.getLogin(), user.getPassword(),
				user.isPasswordEncrypted());

			taskContextMap.put("httpPrincipal", httpPrincipal);
		}

		BackgroundTask backgroundTask =
			_backgroundTaskManager.addBackgroundTask(
				userId, exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(), backgroundTaskExecutor,
				taskContextMap, new ServiceContext());

		return backgroundTask.getBackgroundTaskId();
	}

	@Override
	public long publishPortlet(long userId, long exportImportConfigurationId)
		throws PortalException {

		return publishPortlet(
			userId,
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId));
	}

	@Override
	public long publishPortlet(
			long userId, long sourceGroupId, long targetGroupId,
			long sourcePlid, long targetPlid, String portletId,
			Map<String, String[]> parameterMap)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		Map<String, Serializable> publishPortletSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildPublishPortletSettingsMap(
					userId, sourceGroupId, sourcePlid, targetGroupId,
					targetPlid, portletId, parameterMap, user.getLocale(),
					user.getTimeZone());

		Group sourceGroup = _groupLocalService.getGroup(sourceGroupId);

		int type =
			ExportImportConfigurationConstants.TYPE_PUBLISH_PORTLET_LOCAL;

		if (sourceGroup.isStagedRemotely()) {
			type =
				ExportImportConfigurationConstants.TYPE_PUBLISH_PORTLET_REMOTE;
		}

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					userId, type, publishPortletSettingsMap);

		return publishPortlet(userId, exportImportConfiguration);
	}

	@Override
	public long publishToLive(PortletRequest portletRequest)
		throws PortalException {

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		Group targetGroup = getLiveGroup(groupId);

		if (!targetGroup.isStaged()) {
			return 0;
		}

		if (targetGroup.isStagedRemotely()) {
			return publishToRemote(portletRequest);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		Map<String, Serializable> publishLayoutLocalSettingsMap = null;

		long exportImportConfigurationId = ParamUtil.getLong(
			portletRequest, "exportImportConfigurationId");

		String name = ParamUtil.getString(portletRequest, "name");

		if (exportImportConfigurationId > 0) {
			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					fetchExportImportConfiguration(exportImportConfigurationId);

			if (exportImportConfiguration != null) {
				publishLayoutLocalSettingsMap =
					exportImportConfiguration.getSettingsMap();

				Map<String, String[]> parameterMap =
					(Map<String, String[]>)publishLayoutLocalSettingsMap.get(
						"parameterMap");

				parameterMap.put(
					PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
					new String[] {Boolean.TRUE.toString()});

				if (!Validator.isBlank(name)) {
					parameterMap.put("name", new String[] {name});
				}
			}
		}

		if (publishLayoutLocalSettingsMap == null) {
			Group sourceGroup = targetGroup.getStagingGroup();

			long sourceGroupId = sourceGroup.getGroupId();

			long targetGroupId = targetGroup.getGroupId();
			boolean privateLayout = getPrivateLayout(portletRequest);

			long[] layoutIds = _exportImportHelper.getLayoutIds(
				portletRequest, targetGroupId);

			Map<String, String[]> parameterMap =
				_exportImportConfigurationParameterMapFactory.buildParameterMap(
					portletRequest);

			parameterMap.put(
				PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
				new String[] {Boolean.TRUE.toString()});

			publishLayoutLocalSettingsMap =
				_exportImportConfigurationSettingsMapFactory.
					buildPublishLayoutLocalSettingsMap(
						user, sourceGroupId, targetGroupId, privateLayout,
						layoutIds, parameterMap);
		}

		ExportImportConfiguration exportImportConfiguration = null;

		if (Validator.isNotNull(name)) {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(), name,
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_LOCAL,
						publishLayoutLocalSettingsMap);
		}
		else {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_LOCAL,
						publishLayoutLocalSettingsMap);
		}

		return publishLayouts(user.getUserId(), exportImportConfiguration);
	}

	@Override
	public long publishToLive(PortletRequest portletRequest, Portlet portlet)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = _portal.getScopeGroupId(
			_portal.getHttpServletRequest(portletRequest),
			portlet.getPortletId());

		long plid = ParamUtil.getLong(portletRequest, "plid");

		Map<String, String[]> parameterMap =
			_exportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);

		return publishPortlet(
			themeDisplay.getUserId(), scopeGroupId, plid,
			portlet.getPortletId(), parameterMap, false);
	}

	@Override
	public long publishToRemote(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		Group group = _groupLocalService.getGroup(groupId);

		UnicodeProperties groupTypeSettingsProperties =
			group.getTypeSettingsProperties();

		long remoteGroupId = ParamUtil.getLong(
			portletRequest, "remoteGroupId",
			GetterUtil.getLong(
				groupTypeSettingsProperties.getProperty("remoteGroupId")));

		Map<String, Serializable> publishLayoutRemoteSettingsMap = null;
		String remoteAddress = null;
		int remotePort = 0;
		String remotePathContext = null;
		boolean secureConnection = false;
		boolean remotePrivateLayout = false;

		long exportImportConfigurationId = ParamUtil.getLong(
			portletRequest, "exportImportConfigurationId");

		String name = ParamUtil.getString(portletRequest, "name");

		if (exportImportConfigurationId > 0) {
			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					fetchExportImportConfiguration(exportImportConfigurationId);

			if (exportImportConfiguration != null) {
				publishLayoutRemoteSettingsMap =
					exportImportConfiguration.getSettingsMap();

				remoteAddress = MapUtil.getString(
					publishLayoutRemoteSettingsMap, "remoteAddress");
				remotePort = MapUtil.getInteger(
					publishLayoutRemoteSettingsMap, "remotePort");
				remotePathContext = MapUtil.getString(
					publishLayoutRemoteSettingsMap, "remotePathContext");
				secureConnection = MapUtil.getBoolean(
					publishLayoutRemoteSettingsMap, "secureConnection");
				remoteGroupId = MapUtil.getLong(
					publishLayoutRemoteSettingsMap, "targetGroupId");
				remotePrivateLayout = MapUtil.getBoolean(
					publishLayoutRemoteSettingsMap, "remotePrivateLayout");

				if (!Validator.isBlank(name)) {
					Map<String, String[]> parameterMap =
						(Map<String, String[]>)
							publishLayoutRemoteSettingsMap.get("parameterMap");

					parameterMap.put("name", new String[] {name});
				}
			}
		}

		if (publishLayoutRemoteSettingsMap == null) {
			Map<String, String[]> parameterMap =
				_exportImportConfigurationParameterMapFactory.buildParameterMap(
					portletRequest);
			remoteAddress = ParamUtil.getString(
				portletRequest, "remoteAddress",
				groupTypeSettingsProperties.getProperty("remoteAddress"));
			remotePort = ParamUtil.getInteger(
				portletRequest, "remotePort",
				GetterUtil.getInteger(
					groupTypeSettingsProperties.getProperty("remotePort")));
			remotePathContext = ParamUtil.getString(
				portletRequest, "remotePathContext",
				groupTypeSettingsProperties.getProperty("remotePathContext"));
			secureConnection = ParamUtil.getBoolean(
				portletRequest, "secureConnection",
				GetterUtil.getBoolean(
					groupTypeSettingsProperties.getProperty(
						"secureConnection")));
			remotePrivateLayout = ParamUtil.getBoolean(
				portletRequest, "remotePrivateLayout");

			publishLayoutRemoteSettingsMap =
				_exportImportConfigurationSettingsMapFactory.
					buildPublishLayoutRemoteSettingsMap(
						user.getUserId(), groupId,
						getPrivateLayout(portletRequest),
						_exportImportHelper.getLayoutIdMap(portletRequest),
						parameterMap, remoteAddress, remotePort,
						remotePathContext, secureConnection, remoteGroupId,
						remotePrivateLayout, user.getLocale(),
						user.getTimeZone());
		}

		remoteAddress = stripProtocolFromRemoteAddress(remoteAddress);

		_groupLocalService.validateRemote(
			groupId, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId);

		ExportImportConfiguration exportImportConfiguration = null;

		if (Validator.isNotNull(name)) {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(), name,
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_REMOTE,
						publishLayoutRemoteSettingsMap);
		}
		else {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_REMOTE,
						publishLayoutRemoteSettingsMap);
		}

		return doCopyRemoteLayouts(
			exportImportConfiguration, remoteAddress, remotePort,
			remotePathContext, secureConnection, remotePrivateLayout);
	}

	@Override
	public <T extends BaseModel> void removeModelFromChangesetCollection(
			T model)
		throws PortalException {

		if (!(model instanceof StagedGroupedModel)) {
			return;
		}

		StagedGroupedModel stagedGroupedModel = (StagedGroupedModel)model;

		ChangesetCollection changesetCollection =
			_changesetCollectionLocalService.fetchChangesetCollection(
				stagedGroupedModel.getGroupId(),
				StagingConstants.RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME);

		if (changesetCollection == null) {
			return;
		}

		long classNameId = _classNameLocalService.getClassNameId(
			stagedGroupedModel.getModelClassName());
		long classPK = (long)stagedGroupedModel.getPrimaryKeyObj();

		ChangesetEntry changesetEntry =
			_changesetEntryLocalService.fetchChangesetEntry(
				changesetCollection.getChangesetCollectionId(), classNameId,
				classPK);

		if (changesetEntry == null) {
			return;
		}

		_changesetEntryLocalService.deleteChangesetEntry(
			changesetEntry.getChangesetEntryId());
	}

	@Override
	public void scheduleCopyFromLive(PortletRequest portletRequest)
		throws PortalException {

		long targetGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group targetGroup = _groupLocalService.getGroup(targetGroupId);

		long sourceGroupId = targetGroup.getLiveGroupId();

		long[] layoutIds = _exportImportHelper.getLayoutIds(
			portletRequest, targetGroupId);
		Map<String, String[]> parameterMap =
			_exportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);
		ScheduleInformation scheduleInformation = getScheduleInformation(
			portletRequest, targetGroupId, false);
		String name = ParamUtil.getString(portletRequest, "name");

		_layoutService.schedulePublishToLive(
			sourceGroupId, targetGroupId, getPrivateLayout(portletRequest),
			layoutIds, parameterMap, scheduleInformation.getGroupName(),
			scheduleInformation.getCronText(),
			scheduleInformation.getStartDate(),
			scheduleInformation.getSchedulerEndDate(), name);
	}

	@Override
	public void schedulePublishToLive(PortletRequest portletRequest)
		throws PortalException {

		long sourceGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group sourceGroup = _groupLocalService.getGroup(sourceGroupId);

		long targetGroupId = sourceGroup.getLiveGroupId();

		long exportImportConfigurationId = ParamUtil.getLong(
			portletRequest, "exportImportConfigurationId");

		Map<String, String[]> parameterMap = null;
		boolean privateLayout = false;
		long[] layoutIds = null;

		if (exportImportConfigurationId > 0) {
			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					fetchExportImportConfiguration(exportImportConfigurationId);

			if (exportImportConfiguration != null) {
				Map<String, Serializable> settingsMap =
					exportImportConfiguration.getSettingsMap();

				parameterMap = (Map<String, String[]>)settingsMap.get(
					"parameterMap");
				privateLayout = MapUtil.getBoolean(
					settingsMap, "privateLayout");
				layoutIds = GetterUtil.getLongValues(
					settingsMap.get("layoutIds"));

				parameterMap.put(
					"timeZoneId",
					ParamUtil.getParameterValues(portletRequest, "timeZoneId"));
			}
		}

		if (parameterMap == null) {
			privateLayout = getPrivateLayout(portletRequest);
			layoutIds = _exportImportHelper.getLayoutIds(
				portletRequest, targetGroupId);
			parameterMap =
				_exportImportConfigurationParameterMapFactory.buildParameterMap(
					portletRequest);
		}

		ScheduleInformation scheduleInformation = getScheduleInformation(
			portletRequest, targetGroupId, false);

		String name = ParamUtil.getString(portletRequest, "name");

		if (!Validator.isBlank(name)) {
			parameterMap.put("name", new String[] {name});
		}

		_layoutService.schedulePublishToLive(
			sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap, scheduleInformation.getGroupName(),
			scheduleInformation.getCronText(),
			scheduleInformation.getStartDate(),
			scheduleInformation.getSchedulerEndDate(), name);
	}

	@Override
	public void schedulePublishToRemote(PortletRequest portletRequest)
		throws PortalException {

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		Group group = _groupLocalService.getGroup(groupId);

		UnicodeProperties groupTypeSettingsProperties =
			group.getTypeSettingsProperties();

		boolean privateLayout = false;
		Map<Long, Boolean> layoutIdMap = null;
		Map<String, String[]> parameterMap = null;
		String remoteAddress = null;
		int remotePort = 0;
		String remotePathContext = null;
		boolean secureConnection = false;
		long remoteGroupId = ParamUtil.getLong(
			portletRequest, "remoteGroupId",
			GetterUtil.getLong(
				groupTypeSettingsProperties.getProperty("remoteGroupId")));
		boolean remotePrivateLayout = false;

		long exportImportConfigurationId = ParamUtil.getLong(
			portletRequest, "exportImportConfigurationId");

		if (exportImportConfigurationId > 0) {
			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					fetchExportImportConfiguration(exportImportConfigurationId);

			if (exportImportConfiguration != null) {
				Map<String, Serializable> settingsMap =
					exportImportConfiguration.getSettingsMap();

				privateLayout = MapUtil.getBoolean(
					settingsMap, "privateLayout");
				layoutIdMap = (Map<Long, Boolean>)settingsMap.get(
					"layoutIdMap");
				parameterMap = (Map<String, String[]>)settingsMap.get(
					"parameterMap");
				remoteAddress = MapUtil.getString(settingsMap, "remoteAddress");
				remotePort = MapUtil.getInteger(settingsMap, "remotePort");
				remotePathContext = MapUtil.getString(
					settingsMap, "remotePathContext");
				secureConnection = MapUtil.getBoolean(
					settingsMap, "secureConnection");
				remoteGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
				remotePrivateLayout = MapUtil.getBoolean(
					settingsMap, "remotePrivateLayout");

				parameterMap.put(
					"timeZoneId",
					ParamUtil.getParameterValues(portletRequest, "timeZoneId"));
			}
		}

		if (parameterMap == null) {
			privateLayout = getPrivateLayout(portletRequest);
			layoutIdMap = _exportImportHelper.getLayoutIdMap(portletRequest);
			parameterMap =
				_exportImportConfigurationParameterMapFactory.buildParameterMap(
					portletRequest);
			remoteAddress = ParamUtil.getString(
				portletRequest, "remoteAddress",
				groupTypeSettingsProperties.getProperty("remoteAddress"));
			remotePort = ParamUtil.getInteger(
				portletRequest, "remotePort",
				GetterUtil.getInteger(
					groupTypeSettingsProperties.getProperty("remotePort")));
			remotePathContext = ParamUtil.getString(
				portletRequest, "remotePathContext",
				groupTypeSettingsProperties.getProperty("remotePathContext"));
			secureConnection = ParamUtil.getBoolean(
				portletRequest, "secureConnection",
				GetterUtil.getBoolean(
					groupTypeSettingsProperties.getProperty(
						"secureConnection")));
			remotePrivateLayout = ParamUtil.getBoolean(
				portletRequest, "remotePrivateLayout");
		}

		remoteAddress = stripProtocolFromRemoteAddress(remoteAddress);

		_groupLocalService.validateRemote(
			groupId, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId);

		ScheduleInformation scheduleInformation = getScheduleInformation(
			portletRequest, groupId, true);

		String name = ParamUtil.getString(portletRequest, "name");

		if (!Validator.isBlank(name)) {
			parameterMap.put("name", new String[] {name});
		}

		_layoutService.schedulePublishToRemote(
			groupId, privateLayout, layoutIdMap, parameterMap, remoteAddress,
			remotePort, remotePathContext, secureConnection, remoteGroupId,
			remotePrivateLayout, null, null, scheduleInformation.getGroupName(),
			scheduleInformation.getCronText(),
			scheduleInformation.getStartDate(),
			scheduleInformation.getSchedulerEndDate(), name);
	}

	@Override
	public void setRecentLayoutBranchId(
			HttpServletRequest httpServletRequest, long layoutSetBranchId,
			long plid, long layoutBranchId)
		throws PortalException {

		setRecentLayoutBranchId(
			_portal.getUserId(httpServletRequest), layoutSetBranchId, plid,
			layoutBranchId);
	}

	@Override
	public void setRecentLayoutBranchId(
			User user, long layoutSetBranchId, long plid, long layoutBranchId)
		throws PortalException {

		setRecentLayoutBranchId(
			user.getUserId(), layoutSetBranchId, plid, layoutBranchId);
	}

	@Override
	public void setRecentLayoutRevisionId(
			HttpServletRequest httpServletRequest, long layoutSetBranchId,
			long plid, long layoutRevisionId)
		throws PortalException {

		setRecentLayoutRevisionId(
			_portal.getUserId(httpServletRequest), layoutSetBranchId, plid,
			layoutRevisionId);
	}

	@Override
	public void setRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid, long layoutRevisionId)
		throws PortalException {

		setRecentLayoutRevisionId(
			user.getUserId(), layoutSetBranchId, plid, layoutRevisionId);
	}

	@Override
	public void setRecentLayoutSetBranchId(
			HttpServletRequest httpServletRequest, long layoutSetId,
			long layoutSetBranchId)
		throws PortalException {

		setRecentLayoutSetBranchId(
			_portal.getUserId(httpServletRequest), layoutSetId,
			layoutSetBranchId);
	}

	@Override
	public void setRecentLayoutSetBranchId(
			User user, long layoutSetId, long layoutSetBranchId)
		throws PortalException {

		setRecentLayoutSetBranchId(
			user.getUserId(), layoutSetId, layoutSetBranchId);
	}

	@Override
	public String stripProtocolFromRemoteAddress(String remoteAddress) {
		if (remoteAddress.startsWith(Http.HTTP_WITH_SLASH)) {
			remoteAddress = remoteAddress.substring(
				Http.HTTP_WITH_SLASH.length());
		}
		else if (remoteAddress.startsWith(Http.HTTPS_WITH_SLASH)) {
			remoteAddress = remoteAddress.substring(
				Http.HTTPS_WITH_SLASH.length());
		}

		return remoteAddress;
	}

	@Override
	public void transferFileToRemoteLive(
			File file, long stagingRequestId, HttpPrincipal httpPrincipal)
		throws Exception {

		byte[] bytes =
			new byte[PropsValues.STAGING_REMOTE_TRANSFER_BUFFER_SIZE];

		int i = 0;
		int j = 0;

		String numberString = String.valueOf(
			(int)(file.length() / bytes.length));

		String numberFormat = String.format(
			"%%0%dd", numberString.length() + 1);

		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			while ((i = fileInputStream.read(bytes)) >= 0) {
				String fileName =
					file.getName() + String.format(numberFormat, j++);

				if (i < PropsValues.STAGING_REMOTE_TRANSFER_BUFFER_SIZE) {
					byte[] tempBytes = new byte[i];

					System.arraycopy(bytes, 0, tempBytes, 0, i);

					StagingServiceHttp.updateStagingRequest(
						httpPrincipal, stagingRequestId, fileName, tempBytes);
				}
				else {
					StagingServiceHttp.updateStagingRequest(
						httpPrincipal, stagingRequestId, fileName, bytes);
				}

				bytes =
					new byte[PropsValues.STAGING_REMOTE_TRANSFER_BUFFER_SIZE];
			}
		}
	}

	@Override
	public void unscheduleCopyFromLive(PortletRequest portletRequest)
		throws PortalException {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		String jobName = ParamUtil.getString(portletRequest, "jobName");
		String groupName = getSchedulerGroupName(
			DestinationNames.LAYOUTS_LOCAL_PUBLISHER, stagingGroupId);

		_layoutService.unschedulePublishToLive(
			stagingGroupId, jobName, groupName);
	}

	@Override
	public void unschedulePublishToLive(PortletRequest portletRequest)
		throws PortalException {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group stagingGroup = _groupLocalService.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		String jobName = ParamUtil.getString(portletRequest, "jobName");
		String groupName = getSchedulerGroupName(
			DestinationNames.LAYOUTS_LOCAL_PUBLISHER, liveGroupId);

		_layoutService.unschedulePublishToLive(liveGroupId, jobName, groupName);
	}

	@Override
	public void unschedulePublishToRemote(PortletRequest portletRequest)
		throws PortalException {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		String jobName = ParamUtil.getString(portletRequest, "jobName");
		String groupName = getSchedulerGroupName(
			DestinationNames.LAYOUTS_REMOTE_PUBLISHER, stagingGroupId);

		_layoutService.unschedulePublishToRemote(
			stagingGroupId, jobName, groupName);
	}

	@Override
	public void updateLastImportSettings(
		Element layoutElement, Layout layout,
		PortletDataContext portletDataContext) {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		String cmd = MapUtil.getString(parameterMap, Constants.CMD);

		if (!cmd.equals(Constants.PUBLISH_TO_LIVE) &&
			!cmd.equals("schedule_publish_to_live")) {

			return;
		}

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"last-import-date", String.valueOf(System.currentTimeMillis()));

		String layoutRevisionId = GetterUtil.getString(
			layoutElement.attributeValue("layout-revision-id"));

		typeSettingsProperties.setProperty(
			"last-import-layout-revision-id", layoutRevisionId);

		String layoutSetBranchId = MapUtil.getString(
			parameterMap, "layoutSetBranchId");

		typeSettingsProperties.setProperty(
			"last-import-layout-set-branch-id", layoutSetBranchId);

		String layoutSetBranchName = MapUtil.getString(
			parameterMap, "layoutSetBranchName");

		typeSettingsProperties.setProperty(
			"last-import-layout-set-branch-name", layoutSetBranchName);

		String lastImportUserName = MapUtil.getString(
			parameterMap, "lastImportUserName");

		typeSettingsProperties.setProperty(
			"last-import-user-name", lastImportUserName);

		String lastImportUserUuid = MapUtil.getString(
			parameterMap, "lastImportUserUuid");

		typeSettingsProperties.setProperty(
			"last-import-user-uuid", lastImportUserUuid);

		String layoutBranchId = GetterUtil.getString(
			layoutElement.attributeValue("layout-branch-id"));

		typeSettingsProperties.setProperty(
			"last-import-layout-branch-id", layoutBranchId);

		String layoutBranchName = GetterUtil.getString(
			layoutElement.attributeValue("layout-branch-name"));

		typeSettingsProperties.setProperty(
			"last-import-layout-branch-name", layoutBranchName);

		layout.setTypeSettingsProperties(typeSettingsProperties);
	}

	@Override
	public void validateRemoteGroupIsSame(
			long groupId, long remoteGroupId, String remoteAddress,
			int remotePort, String remotePathContext, boolean secureConnection)
		throws PortalException {

		if (remoteGroupId <= 0) {
			RemoteOptionsException roe = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_GROUP_ID);

			roe.setRemoteGroupId(remoteGroupId);

			throw roe;
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		String remoteURL = _stagingURLHelper.buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection);

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			remoteURL, user.getLogin(), user.getPassword(),
			user.isPasswordEncrypted());

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			// Ping the remote host and verify that the remote group exists in
			// the same company as the remote user

			GroupServiceHttp.checkRemoteStagingGroup(
				httpPrincipal, remoteGroupId);

			Group group = _groupLocalService.getGroup(groupId);

			Group remoteGroup = GroupServiceHttp.getGroup(
				httpPrincipal, remoteGroupId);

			if ((group.getGroupId() == remoteGroup.getGroupId()) &&
				Objects.equals(group.getUuid(), remoteGroup.getUuid())) {

				String validationTimestamp = String.valueOf(
					System.currentTimeMillis());

				_setGroupTypeSetting(
					groupId, "validationTimestamp", validationTimestamp);

				remoteGroup = GroupServiceHttp.getGroup(
					httpPrincipal, remoteGroupId);

				UnicodeProperties remoteTypeSettingsProperties =
					remoteGroup.getTypeSettingsProperties();

				String remoteValidationTimestamp = GetterUtil.getString(
					remoteTypeSettingsProperties.getProperty(
						"validationTimestamp"));

				if (validationTimestamp.equals(remoteValidationTimestamp)) {
					RemoteExportException ree = new RemoteExportException(
						RemoteExportException.SAME_GROUP);

					ree.setGroupId(remoteGroupId);

					throw ree;
				}
			}
		}
		catch (NoSuchGroupException nsge) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsge, nsge);
			}

			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_GROUP);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (PrincipalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_PERMISSIONS);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (RemoteAuthException rae) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(rae, rae);
			}

			rae.setURL(remoteURL);

			throw rae;
		}
		catch (SystemException se) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(se, se);
			}

			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.BAD_CONNECTION, se.getMessage());

			ree.setURL(remoteURL);

			throw ree;
		}
		finally {
			_setGroupTypeSetting(groupId, "validationTimestamp", null);

			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected long doCopyRemoteLayouts(
			ExportImportConfiguration exportImportConfiguration,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, boolean remotePrivateLayout)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		String backgroundTaskName = MapUtil.getString(
			parameterMap, "name", exportImportConfiguration.getName());

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				"exportImportConfigurationId",
				exportImportConfiguration.getExportImportConfigurationId()
			).put(
				"httpPrincipal",
				new HttpPrincipal(
					_stagingURLHelper.buildRemoteURL(
						remoteAddress, remotePort, remotePathContext,
						secureConnection),
					user.getLogin(), user.getPassword(),
					user.isPasswordEncrypted())
			).put(
				"privateLayout", remotePrivateLayout
			).build();

		BackgroundTask backgroundTask =
			_backgroundTaskManager.addBackgroundTask(
				user.getUserId(), exportImportConfiguration.getGroupId(),
				backgroundTaskName,
				BackgroundTaskExecutorNames.
					LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR,
				taskContextMap, new ServiceContext());

		return backgroundTask.getBackgroundTaskId();
	}

	protected boolean getBoolean(
		PortletRequest portletRequest, Group group, String param) {

		return ParamUtil.getBoolean(
			portletRequest, param,
			GetterUtil.getBoolean(group.getTypeSettingsProperty(param)));
	}

	protected int getInteger(
		PortletRequest portletRequest, Group group, String param) {

		return ParamUtil.getInteger(
			portletRequest, param,
			GetterUtil.getInteger(group.getTypeSettingsProperty(param)));
	}

	protected long getLong(
		PortletRequest portletRequest, Group group, String param) {

		return ParamUtil.getLong(
			portletRequest, param,
			GetterUtil.getLong(group.getTypeSettingsProperty(param)));
	}

	protected boolean getPrivateLayout(PortletRequest portletRequest) {
		String tabs1 = ParamUtil.getString(portletRequest, "tabs1");

		if (Validator.isNotNull(tabs1)) {
			if (tabs1.equals("public-pages")) {
				return false;
			}

			return true;
		}

		return ParamUtil.getBoolean(portletRequest, "privateLayout", true);
	}

	protected long getRecentLayoutBranchId(
			long userId, long layoutSetBranchId, long plid)
		throws PortalException {

		RecentLayoutBranch recentLayoutBranch =
			_recentLayoutBranchLocalService.fetchRecentLayoutBranch(
				userId, layoutSetBranchId, plid);

		if (recentLayoutBranch != null) {
			return recentLayoutBranch.getLayoutBranchId();
		}

		try {
			LayoutBranch masterLayoutBranch =
				_layoutBranchLocalService.getMasterLayoutBranch(
					layoutSetBranchId, plid);

			return masterLayoutBranch.getLayoutBranchId();
		}
		catch (NoSuchLayoutBranchException nslbe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nslbe, nslbe);
			}
		}

		return 0;
	}

	protected long getRecentLayoutRevisionId(
			long userId, long layoutSetBranchId, long plid)
		throws PortalException {

		if (ExportImportThreadLocal.isLayoutStagingInProcess()) {
			List<LayoutRevision> layoutRevisions =
				_layoutRevisionLocalService.getLayoutRevisions(
					layoutSetBranchId, plid, true);

			if (ListUtil.isNotEmpty(layoutRevisions)) {
				LayoutRevision layoutRevision = layoutRevisions.get(0);

				return layoutRevision.getLayoutRevisionId();
			}

			return 0;
		}

		RecentLayoutRevision recentLayoutRevision =
			_recentLayoutRevisionLocalService.fetchRecentLayoutRevision(
				userId, layoutSetBranchId, plid);

		if (recentLayoutRevision != null) {
			return recentLayoutRevision.getLayoutRevisionId();
		}

		long layoutBranchId = getRecentLayoutBranchId(
			userId, layoutSetBranchId, plid);

		LayoutBranch layoutBranch = _layoutBranchLocalService.fetchLayoutBranch(
			layoutBranchId);

		if (layoutBranch == null) {
			try {
				layoutBranch = _layoutBranchLocalService.getMasterLayoutBranch(
					layoutSetBranchId, plid);

				layoutBranchId = layoutBranch.getLayoutBranchId();
			}
			catch (NoSuchLayoutBranchException nslbe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nslbe, nslbe);
				}
			}
		}

		if (layoutBranchId > 0) {
			try {
				LayoutRevision layoutRevision =
					_layoutRevisionLocalService.getLayoutRevision(
						layoutSetBranchId, layoutBranchId, plid);

				if (layoutRevision != null) {
					return layoutRevision.getLayoutRevisionId();
				}
			}
			catch (NoSuchLayoutRevisionException nslre) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nslre, nslre);
				}
			}
		}

		return 0;
	}

	protected ScheduleInformation getScheduleInformation(
		PortletRequest portletRequest, long targetGroupId, boolean remote) {

		ScheduleInformation scheduleInformation = new ScheduleInformation();

		int recurrenceType = ParamUtil.getInteger(
			portletRequest, "recurrenceType");

		Calendar startCalendar = ExportImportDateUtil.getCalendar(
			portletRequest, "schedulerStartDate", true);

		String cronText = SchedulerEngineHelperUtil.getCronText(
			portletRequest, startCalendar, false, recurrenceType);

		scheduleInformation.setCronText(cronText);

		String destinationName = DestinationNames.LAYOUTS_LOCAL_PUBLISHER;

		if (remote) {
			destinationName = DestinationNames.LAYOUTS_REMOTE_PUBLISHER;
		}

		String groupName = getSchedulerGroupName(
			destinationName, targetGroupId);

		scheduleInformation.setGroupName(groupName);

		Date schedulerEndDate = null;

		int endDateType = ParamUtil.getInteger(portletRequest, "endDateType");

		if (endDateType == 1) {
			Calendar endCalendar = ExportImportDateUtil.getCalendar(
				portletRequest, "schedulerEndDate", true);

			schedulerEndDate = endCalendar.getTime();
		}

		scheduleInformation.setSchedulerEndDate(schedulerEndDate);

		scheduleInformation.setStartCalendar(startCalendar);

		return scheduleInformation;
	}

	protected int getStagingType(
		PortletRequest portletRequest, Group liveGroup) {

		String stagingType = portletRequest.getParameter("stagingType");

		if (stagingType != null) {
			return GetterUtil.getInteger(stagingType);
		}

		if (liveGroup.isStagedRemotely()) {
			return StagingConstants.TYPE_REMOTE_STAGING;
		}

		if (liveGroup.hasStagingGroup()) {
			return StagingConstants.TYPE_LOCAL_STAGING;
		}

		return StagingConstants.TYPE_NOT_STAGED;
	}

	protected String getString(
		PortletRequest portletRequest, Group group, String param) {

		return ParamUtil.getString(
			portletRequest, param,
			GetterUtil.getString(group.getTypeSettingsProperty(param)));
	}

	protected boolean isLayoutRevisionIncomplete(
		long plid, LayoutRevision layoutRevision, long layoutSetBranchId) {

		if (layoutRevision == null) {
			List<LayoutRevision> layoutRevisions =
				_layoutRevisionLocalService.getLayoutRevisions(
					layoutSetBranchId, plid, true);

			if (!layoutRevisions.isEmpty()) {
				return false;
			}
		}

		List<LayoutRevision> layoutRevisions =
			_layoutRevisionLocalService.getLayoutRevisions(
				layoutSetBranchId, plid, false);

		if (!layoutRevisions.isEmpty()) {
			layoutRevision = layoutRevisions.get(0);
		}

		if ((layoutRevision == null) ||
			(layoutRevision.getStatus() ==
				WorkflowConstants.STATUS_INCOMPLETE)) {

			return true;
		}

		return false;
	}

	protected long publishPortlet(
			long userId, long scopeGroupId, long plid, String portletId,
			Map<String, String[]> parameterMap, boolean copyFromLive)
		throws PortalException {

		Layout sourceLayout = _layoutLocalService.getLayout(plid);

		Group scopeGroup = sourceLayout.getScopeGroup();

		Group liveGroup = null;
		Group stagingGroup = null;

		long targetGroupId = 0L;
		long targetLayoutPlid = 0L;

		if (sourceLayout.isTypeControlPanel()) {
			stagingGroup = _groupLocalService.fetchGroup(scopeGroupId);

			if (stagingGroup.isStagedRemotely()) {
				targetGroupId = stagingGroup.getRemoteLiveGroupId();

				User user = _userLocalService.getUser(userId);

				HttpPrincipal httpPrincipal = new HttpPrincipal(
					_stagingURLHelper.buildRemoteURL(
						stagingGroup.getTypeSettingsProperties()),
					user.getLogin(), user.getPassword(),
					user.isPasswordEncrypted());

				targetLayoutPlid = LayoutServiceHttp.getControlPanelLayoutPlid(
					httpPrincipal);
			}
			else {
				liveGroup = stagingGroup.getLiveGroup();

				targetGroupId = liveGroup.getGroupId();

				targetLayoutPlid = sourceLayout.getPlid();
			}
		}
		else if (sourceLayout.hasScopeGroup() &&
				 (scopeGroup.getGroupId() == scopeGroupId)) {

			stagingGroup = scopeGroup;

			liveGroup = stagingGroup.getLiveGroup();

			targetGroupId = liveGroup.getGroupId();

			Layout layout = _layoutLocalService.getLayout(
				liveGroup.getClassPK());

			targetLayoutPlid = layout.getPlid();
		}
		else {
			stagingGroup = sourceLayout.getGroup();

			if (stagingGroup.isStagedRemotely()) {
				targetGroupId = stagingGroup.getRemoteLiveGroupId();

				targetLayoutPlid = getRemoteLayoutPlid(
					userId, stagingGroup.getGroupId(), sourceLayout.getPlid());
			}
			else {
				liveGroup = stagingGroup.getLiveGroup();

				targetGroupId = liveGroup.getGroupId();

				Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
					sourceLayout.getUuid(), liveGroup.getGroupId(),
					sourceLayout.isPrivateLayout());

				targetLayoutPlid = layout.getPlid();
			}
		}

		if (copyFromLive) {
			return publishPortlet(
				userId, liveGroup.getGroupId(), stagingGroup.getGroupId(),
				targetLayoutPlid, sourceLayout.getPlid(), portletId,
				parameterMap);
		}

		return publishPortlet(
			userId, stagingGroup.getGroupId(), targetGroupId,
			sourceLayout.getPlid(), targetLayoutPlid, portletId, parameterMap);
	}

	protected void setRecentLayoutBranchId(
			long userId, long layoutSetBranchId, long plid, long layoutBranchId)
		throws PortalException {

		LayoutBranch layoutBranch = _layoutBranchLocalService.fetchLayoutBranch(
			layoutBranchId);

		if (layoutBranch == null) {
			return;
		}

		RecentLayoutBranch recentLayoutBranch =
			_recentLayoutBranchLocalService.fetchRecentLayoutBranch(
				userId, layoutSetBranchId, plid);

		if (layoutBranch.isMaster()) {
			if (recentLayoutBranch != null) {
				_recentLayoutBranchLocalService.deleteRecentLayoutBranch(
					recentLayoutBranch);
			}
		}
		else {
			if (recentLayoutBranch == null) {
				recentLayoutBranch =
					_recentLayoutBranchLocalService.addRecentLayoutBranch(
						userId, layoutBranchId, layoutSetBranchId, plid);
			}

			recentLayoutBranch.setLayoutBranchId(layoutBranchId);

			_recentLayoutBranchLocalService.updateRecentLayoutBranch(
				recentLayoutBranch);
		}

		ProxiedLayoutsThreadLocal.clearProxiedLayouts();
	}

	protected void setRecentLayoutRevisionId(
			long userId, long layoutSetBranchId, long plid,
			long layoutRevisionId)
		throws PortalException {

		if (layoutRevisionId <= 0) {
			return;
		}

		long layoutBranchId = 0;

		try {
			LayoutRevision layoutRevision =
				_layoutRevisionLocalService.getLayoutRevision(layoutRevisionId);

			layoutBranchId = layoutRevision.getLayoutBranchId();

			LayoutRevision lastLayoutRevision =
				_layoutRevisionLocalService.getLayoutRevision(
					layoutSetBranchId, layoutBranchId, plid);

			if (lastLayoutRevision.getLayoutRevisionId() == layoutRevisionId) {
				deleteRecentLayoutRevisionId(userId, layoutSetBranchId, plid);
			}
			else {
				RecentLayoutRevision recentLayoutRevision =
					_recentLayoutRevisionLocalService.fetchRecentLayoutRevision(
						userId, layoutSetBranchId, plid);

				if (recentLayoutRevision == null) {
					recentLayoutRevision =
						_recentLayoutRevisionLocalService.
							addRecentLayoutRevision(
								userId, layoutRevisionId, layoutSetBranchId,
								plid);
				}

				recentLayoutRevision.setLayoutRevisionId(layoutRevisionId);

				_recentLayoutRevisionLocalService.updateRecentLayoutRevision(
					recentLayoutRevision);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to set recent layout revision ID",
						"with layout set branch ", layoutSetBranchId,
						" and PLID ", plid, " and layout branch ",
						layoutBranchId),
					pe);
			}
		}

		setRecentLayoutBranchId(
			userId, layoutSetBranchId, plid, layoutBranchId);
	}

	protected void setRecentLayoutSetBranchId(
			long userId, long layoutSetId, long layoutSetBranchId)
		throws PortalException {

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.fetchLayoutSetBranch(
				layoutSetBranchId);

		if (layoutSetBranch == null) {
			return;
		}

		RecentLayoutSetBranch recentLayoutSetBranch =
			_recentLayoutSetBranchLocalService.fetchRecentLayoutSetBranch(
				userId, layoutSetId);

		if (layoutSetBranch.isMaster()) {
			if (recentLayoutSetBranch != null) {
				_recentLayoutSetBranchLocalService.deleteRecentLayoutSetBranch(
					recentLayoutSetBranch);
			}
		}
		else {
			if (recentLayoutSetBranch == null) {
				recentLayoutSetBranch =
					_recentLayoutSetBranchLocalService.addRecentLayoutSetBranch(
						userId, layoutSetBranchId, layoutSetId);
			}

			recentLayoutSetBranch.setLayoutSetBranchId(layoutSetBranchId);

			_recentLayoutSetBranchLocalService.updateRecentLayoutSetBranch(
				recentLayoutSetBranch);
		}

		ProxiedLayoutsThreadLocal.clearProxiedLayouts();
	}

	private void _setGroupTypeSetting(long groupId, String key, String value) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return;
		}

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		if (Validator.isNotNull(value)) {
			typeSettingsProperties.setProperty(key, value);
		}
		else {
			typeSettingsProperties.remove(key);
		}

		group.setTypeSettingsProperties(typeSettingsProperties);
		group.setTypeSettings(typeSettingsProperties.toString());

		_groupLocalService.updateGroup(group);
	}

	private static final Log _log = LogFactoryUtil.getLog(StagingImpl.class);

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ChangesetCollectionLocalService _changesetCollectionLocalService;

	@Reference
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportConfigurationParameterMapFactory
		_exportImportConfigurationParameterMapFactory;

	@Reference
	private ExportImportConfigurationSettingsMapFactory
		_exportImportConfigurationSettingsMapFactory;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutBranchLocalService _layoutBranchLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Reference
	private LockManager _lockManager;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private RecentLayoutBranchLocalService _recentLayoutBranchLocalService;

	@Reference
	private RecentLayoutRevisionLocalService _recentLayoutRevisionLocalService;

	@Reference
	private RecentLayoutSetBranchLocalService
		_recentLayoutSetBranchLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	@Reference
	private StagingLocalService _stagingLocalService;

	@Reference
	private StagingURLHelper _stagingURLHelper;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	private class ScheduleInformation {

		public ScheduleInformation() {
		}

		public String getCronText() {
			return _cronText;
		}

		public String getGroupName() {
			return _groupName;
		}

		public Date getSchedulerEndDate() {
			return _schedulerEndDate;
		}

		public Calendar getStartCalendar() {
			return _startCalendar;
		}

		public Date getStartDate() {
			return _startCalendar.getTime();
		}

		public void setCronText(String cronText) {
			_cronText = cronText;
		}

		public void setGroupName(String groupName) {
			_groupName = groupName;
		}

		public void setSchedulerEndDate(Date schedulerEndDate) {
			_schedulerEndDate = schedulerEndDate;
		}

		public void setStartCalendar(Calendar startCalendar) {
			_startCalendar = startCalendar;
		}

		private String _cronText;
		private String _groupName;
		private Date _schedulerEndDate;
		private Calendar _startCalendar;

	}

}