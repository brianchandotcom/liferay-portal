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

package com.liferay.exportimport.staging;

import com.liferay.portal.LayoutPrototypeException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutBranchException;
import com.liferay.portal.NoSuchLayoutRevisionException;
import com.liferay.portal.PortletIdException;
import com.liferay.portal.RemoteOptionsException;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.model.adapter.StagedTheme;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.RemoteAuthException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutBranchLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutRevisionLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.service.http.ClassNameServiceHttp;
import com.liferay.portal.service.http.GroupServiceHttp;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileEntryException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.exportimport.LARFileException;
import com.liferay.portlet.exportimport.LARFileSizeException;
import com.liferay.portlet.exportimport.LARTypeException;
import com.liferay.portlet.exportimport.MissingReferenceException;
import com.liferay.portlet.exportimport.RemoteExportException;
import com.liferay.portlet.exportimport.background.task.BackgroundTaskExecutorNames;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationParameterMapFactory;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portlet.exportimport.lar.ExportImportDateUtil;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.MissingReference;
import com.liferay.portlet.exportimport.lar.MissingReferences;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalService;
import com.liferay.portlet.exportimport.service.StagingLocalService;
import com.liferay.portlet.exportimport.staging.LayoutStagingUtil;
import com.liferay.portlet.exportimport.staging.ProxiedLayoutsThreadLocal;
import com.liferay.portlet.exportimport.staging.Staging;
import com.liferay.portlet.exportimport.staging.StagingConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;
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
@Component(immediate = true)
@DoPrivileged
public class StagingImpl implements Staging {

	@Override
	public String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId, boolean privateLayout) {

		StringBundler sb = new StringBundler((remoteGroupId > 0) ? 4 : 9);

		if (secureConnection) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		sb.append(remoteAddress);

		if (remotePort > 0) {
			sb.append(StringPool.COLON);
			sb.append(remotePort);
		}

		if (Validator.isNotNull(remotePathContext)) {
			sb.append(remotePathContext);
		}

		if (remoteGroupId > 0) {
			sb.append("/c/my_sites/view?");
			sb.append("groupId=");
			sb.append(remoteGroupId);
			sb.append("&amp;privateLayout=");
			sb.append(privateLayout);
		}

		return sb.toString();
	}

	@Override
	public String buildRemoteURL(UnicodeProperties typeSettingsProperties) {
		String remoteAddress = typeSettingsProperties.getProperty(
			"remoteAddress");
		int remotePort = GetterUtil.getInteger(
			typeSettingsProperties.getProperty("remotePort"));
		String remotePathContext = typeSettingsProperties.getProperty(
			"remotePathContext");
		boolean secureConnection = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("secureConnection"));

		return buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             _stagingLocalService#checkDefaultLayoutSetBranches(long,
	 *             Group, boolean, boolean, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void checkDefaultLayoutSetBranches(
			long userId, Group liveGroup, boolean branchingPublic,
			boolean branchingPrivate, boolean remote,
			ServiceContext serviceContext)
		throws PortalException {

		_stagingLocalService.checkDefaultLayoutSetBranches(
			userId, liveGroup, branchingPublic, branchingPrivate, remote,
			serviceContext);
	}

	@Override
	public void copyFromLive(PortletRequest portletRequest)
		throws PortalException {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group stagingGroup = _groupLocalService.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);

		publishLayouts(
			portletRequest, liveGroupId, stagingGroupId, parameterMap, false);
	}

	@Override
	public void copyFromLive(PortletRequest portletRequest, Portlet portlet)
		throws PortalException {

		long plid = ParamUtil.getLong(portletRequest, "plid");

		Layout targetLayout = _layoutLocalService.getLayout(plid);

		Group stagingGroup = targetLayout.getGroup();
		Group liveGroup = stagingGroup.getLiveGroup();

		Layout sourceLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			targetLayout.getUuid(), liveGroup.getGroupId(),
			targetLayout.isPrivateLayout());

		copyPortlet(
			portletRequest, liveGroup.getGroupId(), stagingGroup.getGroupId(),
			sourceLayout.getPlid(), targetLayout.getPlid(),
			portlet.getPortletId());
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishPortlet(long, long,
	 *             long, long, long, String, Map)}
	 */
	@Deprecated
	@Override
	public void copyPortlet(
			PortletRequest portletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);

		publishPortlet(
			themeDisplay.getUserId(), sourceGroupId, targetGroupId, sourcePlid,
			targetPlid, portletId, parameterMap);
	}

	@Override
	public void copyRemoteLayouts(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
		String remoteAddress = MapUtil.getString(settingsMap, "remoteAddress");
		int remotePort = MapUtil.getInteger(settingsMap, "remotePort");
		String remotePathContext = MapUtil.getString(
			settingsMap, "remotePathContext");
		boolean secureConnection = MapUtil.getBoolean(
			settingsMap, "secureConnection");

		validateRemoteGroup(
			exportImportConfiguration.getGroupId(), targetGroupId,
			remoteAddress, remotePort, remotePathContext, secureConnection);

		boolean remotePrivateLayout = MapUtil.getBoolean(
			settingsMap, "remotePrivateLayout");

		doCopyRemoteLayouts(
			exportImportConfiguration, remoteAddress, remotePort,
			remotePathContext, secureConnection, remotePrivateLayout);
	}

	@Override
	public void copyRemoteLayouts(long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		copyRemoteLayouts(exportImportConfiguration);
	}

	@Override
	public void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout)
		throws PortalException {

		validateRemoteGroup(
			sourceGroupId, remoteGroupId, remoteAddress, remotePort,
			remotePathContext, secureConnection);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		Map<String, Serializable> publishLayoutRemoteSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildPublishLayoutRemoteSettingsMap(
					user.getUserId(), sourceGroupId, privateLayout, layoutIdMap,
					parameterMap, remoteAddress, remotePort, remotePathContext,
					secureConnection, remoteGroupId, remotePrivateLayout,
					user.getLocale(), user.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_REMOTE,
					publishLayoutRemoteSettingsMap);

		doCopyRemoteLayouts(
			exportImportConfiguration, remoteAddress, remotePort,
			remotePathContext, secureConnection, remotePrivateLayout);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #copyRemoteLayouts(long,
	 *             boolean, Map, Map, String, int, String, boolean, long,
	 *             boolean)}
	 */
	@Deprecated
	@Override
	public void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate)
		throws PortalException {

		copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout);
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
				layout.getGroupId(), layout.getPrivateLayout(),
				layout.getLayoutId(), typeSettingsProperties.toString());
		}
	}

	@Override
	public void deleteRecentLayoutRevisionId(
		HttpServletRequest request, long layoutSetBranchId, long plid) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		deleteRecentLayoutRevisionId(
			portalPreferences, layoutSetBranchId, plid);
	}

	@Override
	public void deleteRecentLayoutRevisionId(
		long userId, long layoutSetBranchId, long plid) {

		User user = _userLocalService.fetchUser(userId);

		PortalPreferences portalPreferences = null;

		if (user != null) {
			portalPreferences = getPortalPreferences(user);
		}
		else {
			portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					userId, false);
		}

		deleteRecentLayoutRevisionId(
			portalPreferences, layoutSetBranchId, plid);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #deleteRecentLayoutRevisionId(long, long, long)}
	 */
	@Deprecated
	@Override
	public void deleteRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid) {

		PortalPreferences portalPreferences = getPortalPreferences(user);

		deleteRecentLayoutRevisionId(
			portalPreferences, layoutSetBranchId, plid);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             StagingLocalService#disableStaging(Group, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void disableStaging(
			Group scopeGroup, Group liveGroup, ServiceContext serviceContext)
		throws Exception {

		disableStaging((PortletRequest)null, liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             StagingLocalService#disableStaging(Group, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void disableStaging(Group liveGroup, ServiceContext serviceContext)
		throws Exception {

		disableStaging((PortletRequest)null, liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             StagingLocalService#disableStaging(PortletRequest, Group,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public void disableStaging(
			PortletRequest portletRequest, Group scopeGroup, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception {

		disableStaging(portletRequest, liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             StagingLocalService#disableStaging(PortletRequest, Group,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public void disableStaging(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception {

		_stagingLocalService.disableStaging(
			portletRequest, liveGroup, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             StagingLocalService#enableLocalStaging(long, Group, boolean,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void enableLocalStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			ServiceContext serviceContext)
		throws Exception {

		_stagingLocalService.enableLocalStaging(
			userId, liveGroup, branchingPublic, branchingPrivate,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             StagingLocalService#enableRemoteStaging(long, Group, boolean,
	 *             boolean, String, int, String, boolean, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void enableRemoteStaging(
			long userId, Group scopeGroup, Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			ServiceContext serviceContext)
		throws Exception {

		_stagingLocalService.enableRemoteStaging(
			userId, liveGroup, branchingPublic, branchingPrivate, remoteAddress,
			remotePort, remotePathContext, secureConnection, remoteGroupId,
			serviceContext);
	}

	@Override
	public JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences) {

		JSONArray errorMessagesJSONArray = JSONFactoryUtil.createJSONArray();

		for (String missingReferenceDisplayName : missingReferences.keySet()) {
			MissingReference missingReference = missingReferences.get(
				missingReferenceDisplayName);

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
					referrerDisplayName = PortalUtil.getPortletTitle(
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
						}, false));
			}
			else {
				errorMessageJSONObject.put(
					"info",
					LanguageUtil.format(
						locale, "referenced-by-x-elements", referrers.size(),
						true));
			}

			errorMessageJSONObject.put("name", missingReferenceDisplayName);

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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getErrorMessagesJSONArray(Locale, Map<String,
	 *             MissingReference>)}
	 */
	@Deprecated
	@Override
	public JSONArray getErrorMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return getErrorMessagesJSONArray(locale, missingReferences);
	}

	@Override
	public JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e,
		ExportImportConfiguration exportImportConfiguration) {

		JSONObject exceptionMessagesJSONObject =
			JSONFactoryUtil.createJSONObject();

		String errorMessage = StringPool.BLANK;
		JSONArray errorMessagesJSONArray = null;
		int errorType = 0;
		JSONArray warningMessagesJSONArray = null;

		if (e instanceof DuplicateFileEntryException) {
			errorMessage = LanguageUtil.get(
				locale, "please-enter-a-unique-document-name");
			errorType = ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION;
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

			long fileMaxSize = PropsValues.DL_FILE_MAX_SIZE;

			try {
				fileMaxSize = PrefsPropsUtil.getLong(
					PropsKeys.DL_FILE_MAX_SIZE);

				if (fileMaxSize == 0) {
					fileMaxSize = PrefsPropsUtil.getLong(
						PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
				}
			}
			catch (Exception e1) {
			}

			if ((exportImportConfiguration != null) &&
				((exportImportConfiguration.getType() ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_LOCAL) ||
				(exportImportConfiguration.getType() ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_REMOTE) ||
				(exportImportConfiguration.getType() ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_PORTLET))) {

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
					TextFormatter.formatStorageSize(fileMaxSize, locale),
					false);
			}

			errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
		}
		else if (e instanceof LARTypeException) {
			LARTypeException lte = (LARTypeException)e;

			errorMessage = LanguageUtil.format(
				locale,
				"please-import-a-lar-file-of-the-correct-type-x-is-not-valid",
				lte.getMessage());
			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof LARFileException) {
			errorMessage = LanguageUtil.get(
				locale, "please-specify-a-lar-file-to-import");
			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof LayoutPrototypeException) {
			LayoutPrototypeException lpe = (LayoutPrototypeException)e;

			StringBundler sb = new StringBundler(4);

			sb.append("the-lar-file-could-not-be-imported-because-it-");
			sb.append("requires-page-templates-or-site-templates-that-could-");
			sb.append("not-be-found.-please-import-the-following-templates-");
			sb.append("manually");

			errorMessage = LanguageUtil.get(locale, sb.toString());

			errorMessagesJSONArray = JSONFactoryUtil.createJSONArray();

			List<Tuple> missingLayoutPrototypes =
				lpe.getMissingLayoutPrototypes();

			for (Tuple missingLayoutPrototype : missingLayoutPrototypes) {
				JSONObject errorMessageJSONObject =
					JSONFactoryUtil.createJSONObject();

				String layoutPrototypeUuid =
					(String)missingLayoutPrototype.getObject(1);

				errorMessageJSONObject.put("info", layoutPrototypeUuid);

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
				}, false);

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
						TYPE_PUBLISH_PORTLET))) {

				errorMessage = LanguageUtil.get(
					locale,
					"there-are-missing-references-that-could-not-be-found-in-" +
						"the-live-environment");
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

			StagedModel stagedModel = pde.getStagedModel();

			String referrerClassName = StringPool.BLANK;
			String referrerDisplayName = StringPool.BLANK;

			if (stagedModel != null) {
				StagedModelType stagedModelType =
					stagedModel.getStagedModelType();

				referrerClassName = stagedModelType.getClassName();
				referrerDisplayName = StagedModelDataHandlerUtil.getDisplayName(
					stagedModel);
			}

			if (pde.getType() == PortletDataException.INVALID_GROUP) {
				errorMessage = LanguageUtil.format(
					locale,
					"the-x-x-could-not-be-exported-because-it-is-not-in-the-" +
						"currently-exported-group",
					new String[] {
						ResourceActionsUtil.getModelResource(
							locale, referrerClassName),
						referrerDisplayName
					}, false);
			}
			else if (pde.getType() == PortletDataException.MISSING_DEPENDENCY) {
				errorMessage = LanguageUtil.format(
					locale,
					"the-x-x-has-missing-references-that-could-not-be-found-" +
						"during-the-export",
					new String[] {
						ResourceActionsUtil.getModelResource(
							locale, referrerClassName),
						referrerDisplayName
					}, false);
			}
			else if (pde.getType() == PortletDataException.STATUS_IN_TRASH) {
				errorMessage = LanguageUtil.format(
					locale,
					"the-x-x-could-not-be-exported-because-it-is-in-the-" +
						"recycle-bin",
					new String[] {
						ResourceActionsUtil.getModelResource(
							locale, referrerClassName),
						referrerDisplayName
					}, false);
			}
			else if (pde.getType() == PortletDataException.STATUS_UNAVAILABLE) {
				errorMessage = LanguageUtil.format(
					locale,
					"the-x-x-could-not-be-exported-because-its-workflow-" +
						"status-is-not-exportable",
					new String[] {
						ResourceActionsUtil.getModelResource(
							locale, referrerClassName),
						referrerDisplayName
					}, false);
			}
			else {
				errorMessage = e.getLocalizedMessage();
			}

			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else if (e instanceof PortletIdException) {
			errorMessage = LanguageUtil.get(
				locale, "please-import-a-lar-file-for-the-current-portlet");
			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}
		else {
			errorMessage = e.getLocalizedMessage();
			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}

		exceptionMessagesJSONObject.put("message", errorMessage);

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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getExceptionMessagesJSONObject(Locale, Exception,
	 *             ExportImportConfiguration)}
	 */
	@Deprecated
	@Override
	public JSONObject getExceptionMessagesJSONObject(
		Locale locale, Exception e, Map<String, Serializable> contextMap) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Group getLiveGroup(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		if (!group.isStagedRemotely() && group.isStagingGroup()) {
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

	/**
	 * @deprecated As of 7.0.0, moved to {@link
	 *             ExportImportHelperUtil#getMissingParentLayouts(Layout, long)}
	 */
	@Deprecated
	@Override
	public List<Layout> getMissingParentLayouts(Layout layout, long liveGroupId)
		throws PortalException {

		return ExportImportHelperUtil.getMissingParentLayouts(
			layout, liveGroupId);
	}

	@Override
	public long getRecentLayoutRevisionId(
			HttpServletRequest request, long layoutSetBranchId, long plid)
		throws PortalException {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		return getRecentLayoutRevisionId(
			portalPreferences, layoutSetBranchId, plid);
	}

	@Override
	public long getRecentLayoutRevisionId(
			User user, long layoutSetBranchId, long plid)
		throws PortalException {

		PortalPreferences portalPreferences = getPortalPreferences(user);

		return getRecentLayoutRevisionId(
			portalPreferences, layoutSetBranchId, plid);
	}

	@Override
	public long getRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		try {
			return getRecentLayoutAttribute(
				portalPreferences, getRecentLayoutSetBranchIdKey(layoutSetId));
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get recent layout set branch ID with layout " +
						"set " + layoutSetId,
					jsone);
			}
		}

		return 0;
	}

	@Override
	public long getRecentLayoutSetBranchId(User user, long layoutSetId) {
		PortalPreferences portalPreferences = getPortalPreferences(user);

		try {
			return getRecentLayoutAttribute(
				portalPreferences, getRecentLayoutSetBranchIdKey(layoutSetId));
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get recent layout set branch ID with user " +
						user.getUserId() + " and layout set " + layoutSetId,
					jsone);
			}
		}

		return 0;
	}

	@Override
	public String getSchedulerGroupName(String destinationName, long groupId) {
		return destinationName.concat(StringPool.SLASH).concat(
			String.valueOf(groupId));
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             )}
	 */
	@Deprecated
	@Override
	public Map<String, String[]> getStagingParameters() {
		return ExportImportConfigurationParameterMapFactory.buildParameterMap();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             ExportImportConfigurationParameterMapFactory#buildParameterMap(
	 *             PortletRequest)}
	 */
	@Deprecated
	@Override
	public Map<String, String[]> getStagingParameters(
		PortletRequest portletRequest) {

		return ExportImportConfigurationParameterMapFactory.buildParameterMap(
			portletRequest);
	}

	@Override
	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences) {

		JSONArray warningMessagesJSONArray = JSONFactoryUtil.createJSONArray();

		for (String missingReferenceReferrerClassName :
				missingReferences.keySet()) {

			MissingReference missingReference = missingReferences.get(
				missingReferenceReferrerClassName);

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

			errorMessageJSONObject.put("size", referrers.size());
			errorMessageJSONObject.put(
				"type",
				ResourceActionsUtil.getModelResource(
					locale, missingReferenceReferrerClassName));

			warningMessagesJSONArray.put(errorMessageJSONObject);
		}

		return warningMessagesJSONArray;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getWarningMessagesJSONArray(Locale, Map<String,
	 *             MissingReference>)}
	 */
	@Deprecated
	@Override
	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences,
		Map<String, Serializable> contextMap) {

		return getWarningMessagesJSONArray(locale, missingReferences);
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
	public boolean isIncomplete(Layout layout, long layoutSetBranchId) {
		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			try {
				layoutRevision = _layoutRevisionLocalService.getLayoutRevision(
					layoutSetBranchId, layout.getPlid(), true);

				return false;
			}
			catch (Exception e) {
			}
		}

		try {
			layoutRevision = _layoutRevisionLocalService.getLayoutRevision(
				layoutSetBranchId, layout.getPlid(), false);
		}
		catch (Exception e) {
		}

		if ((layoutRevision == null) ||
			(layoutRevision.getStatus() ==
				WorkflowConstants.STATUS_INCOMPLETE)) {

			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	@Override
	public void lockGroup(long userId, long groupId) throws PortalException {
		if (LockManagerUtil.isLocked(Staging.class.getName(), groupId)) {
			Lock lock = LockManagerUtil.getLock(
				Staging.class.getName(), groupId);

			throw new DuplicateLockException(lock);
		}

		LockManagerUtil.lock(
			userId, Staging.class.getName(), String.valueOf(groupId),
			StagingImpl.class.getName(), false,
			StagingConstants.LOCK_EXPIRATION_TIME);
	}

	@Override
	public void publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws PortalException {

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});

		Layout layout = _layoutLocalService.getLayout(plid);

		List<Layout> layouts = new ArrayList<>();

		layouts.add(layout);

		List<Layout> parentLayouts =
			ExportImportHelperUtil.getMissingParentLayouts(layout, liveGroupId);

		layouts.addAll(parentLayouts);

		if (includeChildren) {
			layouts.addAll(layout.getAllChildren());
		}

		long[] layoutIds = ExportImportHelperUtil.getLayoutIds(layouts);

		publishLayouts(
			userId, layout.getGroupId(), liveGroupId, layout.isPrivateLayout(),
			layoutIds, parameterMap);
	}

	@Override
	public void publishLayouts(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		BackgroundTaskManagerUtil.addBackgroundTask(
			userId, exportImportConfiguration.getGroupId(), StringPool.BLANK,
			BackgroundTaskExecutorNames.
				LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR, taskContextMap,
			new ServiceContext());
	}

	@Override
	public void publishLayouts(long userId, long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		publishLayouts(userId, exportImportConfiguration);
	}

	@Override
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap)
		throws PortalException {

		parameterMap.put(
			PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
			new String[] {Boolean.TRUE.toString()});

		User user = _userLocalService.getUser(userId);

		Map<String, Serializable> publishLayoutLocalSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildPublishLayoutLocalSettingsMap(
					user, sourceGroupId, targetGroupId, privateLayout,
					layoutIds, parameterMap);

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					userId,
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_LOCAL,
					publishLayoutLocalSettingsMap);

		publishLayouts(userId, exportImportConfiguration);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, long[], Map)}
	 */
	@Deprecated
	@Override
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, long[], Map)}
	 */
	@Deprecated
	@Override
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout,
			ExportImportHelperUtil.getLayoutIds(layoutIdMap, targetGroupId),
			parameterMap, startDate, endDate);
	}

	@Override
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException {

		List<Layout> sourceGroupLayouts = _layoutLocalService.getLayouts(
			sourceGroupId, privateLayout);

		publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout,
			ExportImportHelperUtil.getLayoutIds(sourceGroupLayouts),
			parameterMap);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishLayouts(long, long,
	 *             long, boolean, Map)}
	 */
	@Deprecated
	@Override
	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException {

		publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, parameterMap);
	}

	@Override
	public void publishPortlet(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		BackgroundTaskManagerUtil.addBackgroundTask(
			userId, exportImportConfiguration.getGroupId(),
			exportImportConfiguration.getName(),
			BackgroundTaskExecutorNames.
				PORTLET_STAGING_BACKGROUND_TASK_EXECUTOR,
			taskContextMap, new ServiceContext());
	}

	@Override
	public void publishPortlet(long userId, long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		publishPortlet(userId, exportImportConfiguration);
	}

	@Override
	public void publishPortlet(
			long userId, long sourceGroupId, long targetGroupId,
			long sourcePlid, long targetPlid, String portletId,
			Map<String, String[]> parameterMap)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		Map<String, Serializable> publishPortletSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildPublishPortletSettingsMap(
					userId, sourceGroupId, sourcePlid, targetGroupId,
					targetPlid, portletId, parameterMap, user.getLocale(),
					user.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					userId,
					ExportImportConfigurationConstants.TYPE_PUBLISH_PORTLET,
					publishPortletSettingsMap);

		publishPortlet(userId, exportImportConfiguration);
	}

	@Override
	public void publishToLive(PortletRequest portletRequest)
		throws PortalException {

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		Group liveGroup = getLiveGroup(groupId);

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);

		if (liveGroup.isStaged()) {
			if (liveGroup.isStagedRemotely()) {
				publishToRemote(portletRequest);
			}
			else {
				Group stagingGroup = liveGroup.getStagingGroup();

				publishLayouts(
					portletRequest, stagingGroup.getGroupId(),
					liveGroup.getGroupId(), parameterMap, false);
			}
		}
	}

	@Override
	public void publishToLive(PortletRequest portletRequest, Portlet portlet)
		throws PortalException {

		long plid = ParamUtil.getLong(portletRequest, "plid");

		Layout sourceLayout = _layoutLocalService.getLayout(plid);

		Group stagingGroup = null;
		Group liveGroup = null;

		Layout targetLayout = null;

		long scopeGroupId = PortalUtil.getScopeGroupId(portletRequest);

		if (sourceLayout.isTypeControlPanel()) {
			stagingGroup = _groupLocalService.fetchGroup(scopeGroupId);
			liveGroup = stagingGroup.getLiveGroup();

			targetLayout = sourceLayout;
		}
		else if (sourceLayout.hasScopeGroup() &&
				 (sourceLayout.getScopeGroup().getGroupId() == scopeGroupId)) {

			stagingGroup = sourceLayout.getScopeGroup();
			liveGroup = stagingGroup.getLiveGroup();

			targetLayout = _layoutLocalService.getLayout(
				liveGroup.getClassPK());
		}
		else {
			stagingGroup = sourceLayout.getGroup();
			liveGroup = stagingGroup.getLiveGroup();

			targetLayout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				sourceLayout.getUuid(), liveGroup.getGroupId(),
				sourceLayout.isPrivateLayout());
		}

		copyPortlet(
			portletRequest, stagingGroup.getGroupId(), liveGroup.getGroupId(),
			sourceLayout.getPlid(), targetLayout.getPlid(),
			portlet.getPortletId());
	}

	@Override
	public void publishToRemote(PortletRequest portletRequest)
		throws PortalException {

		publishToRemote(portletRequest, false);
	}

	@Override
	public void scheduleCopyFromLive(PortletRequest portletRequest)
		throws PortalException {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group stagingGroup = _groupLocalService.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);

		publishLayouts(
			portletRequest, liveGroupId, stagingGroupId, parameterMap, true);
	}

	@Override
	public void schedulePublishToLive(PortletRequest portletRequest)
		throws PortalException {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group stagingGroup = _groupLocalService.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);

		publishLayouts(
			portletRequest, stagingGroupId, liveGroupId, parameterMap, true);
	}

	@Override
	public void schedulePublishToRemote(PortletRequest portletRequest)
		throws PortalException {

		publishToRemote(portletRequest, true);
	}

	@Override
	public void setRecentLayoutBranchId(
		HttpServletRequest request, long layoutSetBranchId, long plid,
		long layoutBranchId) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		setRecentLayoutBranchId(
			portalPreferences, layoutSetBranchId, plid, layoutBranchId);
	}

	@Override
	public void setRecentLayoutBranchId(
		User user, long layoutSetBranchId, long plid, long layoutBranchId) {

		PortalPreferences portalPreferences = getPortalPreferences(user);

		setRecentLayoutBranchId(
			portalPreferences, layoutSetBranchId, plid, layoutBranchId);
	}

	@Override
	public void setRecentLayoutRevisionId(
		HttpServletRequest request, long layoutSetBranchId, long plid,
		long layoutRevisionId) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		setRecentLayoutRevisionId(
			portalPreferences, layoutSetBranchId, plid, layoutRevisionId);
	}

	@Override
	public void setRecentLayoutRevisionId(
		User user, long layoutSetBranchId, long plid, long layoutRevisionId) {

		PortalPreferences portalPreferences = getPortalPreferences(user);

		setRecentLayoutRevisionId(
			portalPreferences, layoutSetBranchId, plid, layoutRevisionId);
	}

	@Override
	public void setRecentLayoutSetBranchId(
		HttpServletRequest request, long layoutSetId, long layoutSetBranchId) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		try {
			setRecentLayoutAttribute(
				portalPreferences, getRecentLayoutSetBranchIdKey(layoutSetId),
				layoutSetBranchId);

			ProxiedLayoutsThreadLocal.clearProxiedLayouts();
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to set recent layout set branch ID with layout " +
						"set " + layoutSetId + " and layout set branch " +
							layoutSetBranchId,
					jsone);
			}
		}
	}

	@Override
	public void setRecentLayoutSetBranchId(
		User user, long layoutSetId, long layoutSetBranchId) {

		PortalPreferences portalPreferences = getPortalPreferences(user);

		try {
			setRecentLayoutAttribute(
				portalPreferences, getRecentLayoutSetBranchIdKey(layoutSetId),
				layoutSetBranchId);

			ProxiedLayoutsThreadLocal.clearProxiedLayouts();
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to set recent layout set branch ID with user " +
						user.getUserId() + " and layout set " + layoutSetId +
							" and layout set branch " + layoutSetBranchId,
					jsone);
			}
		}
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

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor#getIsolationLevel(
	 *             )}
	 */
	@Deprecated
	@Override
	public void unlockGroup(long groupId) {
		LockManagerUtil.unlock(Staging.class.getName(), groupId);
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

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		String jobName = ParamUtil.getString(portletRequest, "jobName");
		String groupName = getSchedulerGroupName(
			DestinationNames.LAYOUTS_REMOTE_PUBLISHER, groupId);

		_layoutService.unschedulePublishToRemote(groupId, jobName, groupName);
	}

	@Override
	public void updateLastImportSettings(
		Element layoutElement, Layout layout,
		PortletDataContext portletDataContext) {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		String cmd = MapUtil.getString(parameterMap, Constants.CMD);

		if (!cmd.equals(Constants.PUBLISH_TO_LIVE)) {
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             ExportImportDateUtil#updateLastPublishDate(long, boolean,
	 *             DateRange, Date)}
	 */
	@Deprecated
	@Override
	public void updateLastPublishDate(
			long groupId, boolean privateLayout, Date lastPublishDate)
		throws PortalException {

		ExportImportDateUtil.updateLastPublishDate(
			groupId, privateLayout, null, lastPublishDate);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             ExportImportDateUtil#updateLastPublishDate(String,
	 *             PortletPreferences, DateRange, Date)}
	 */
	@Deprecated
	@Override
	public void updateLastPublishDate(
		String portletId, PortletPreferences portletPreferences,
		Date lastPublishDate) {

		ExportImportDateUtil.updateLastPublishDate(
			portletId, portletPreferences, null, lastPublishDate);
	}

	@Override
	public void updateStaging(PortletRequest portletRequest, Group liveGroup)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long userId = permissionChecker.getUserId();

		if (!GroupPermissionUtil.contains(
				permissionChecker, liveGroup, ActionKeys.MANAGE_STAGING)) {

			return;
		}

		int stagingType = getStagingType(portletRequest, liveGroup);

		boolean branchingPublic = getBoolean(
			portletRequest, liveGroup, "branchingPublic");
		boolean branchingPrivate = getBoolean(
			portletRequest, liveGroup, "branchingPrivate");
		boolean forceDisable = ParamUtil.getBoolean(
			portletRequest, "forceDisable");

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		serviceContext.setAttribute("forceDisable", forceDisable);

		if (stagingType == StagingConstants.TYPE_NOT_STAGED) {
			if (liveGroup.hasStagingGroup() || liveGroup.isStagedRemotely()) {
				_stagingLocalService.disableStaging(
					portletRequest, liveGroup, serviceContext);
			}
		}
		else if (stagingType == StagingConstants.TYPE_LOCAL_STAGING) {
			_stagingLocalService.enableLocalStaging(
				userId, liveGroup, branchingPublic, branchingPrivate,
				serviceContext);
		}
		else if (stagingType == StagingConstants.TYPE_REMOTE_STAGING) {
			String remoteAddress = getString(
				portletRequest, liveGroup, "remoteAddress");

			remoteAddress = stripProtocolFromRemoteAddress(remoteAddress);

			int remotePort = getInteger(
				portletRequest, liveGroup, "remotePort");
			String remotePathContext = getString(
				portletRequest, liveGroup, "remotePathContext");
			boolean secureConnection = getBoolean(
				portletRequest, liveGroup, "secureConnection");
			long remoteGroupId = getLong(
				portletRequest, liveGroup, "remoteGroupId");

			_stagingLocalService.enableRemoteStaging(
				userId, liveGroup, branchingPublic, branchingPrivate,
				remoteAddress, remotePort, remotePathContext, secureConnection,
				remoteGroupId, serviceContext);
		}
	}

	@Override
	public void validateRemote(
			long groupId, String remoteAddress, int remotePort,
			String remotePathContext, boolean secureConnection,
			long remoteGroupId)
		throws PortalException {

		RemoteOptionsException roe = null;

		if (!Validator.isDomain(remoteAddress) &&
			!Validator.isIPAddress(remoteAddress)) {

			roe = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_ADDRESS);

			roe.setRemoteAddress(remoteAddress);

			throw roe;
		}

		if ((remotePort < 1) || (remotePort > 65535)) {
			roe = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_PORT);

			roe.setRemotePort(remotePort);

			throw roe;
		}

		if (Validator.isNotNull(remotePathContext) &&
			(!remotePathContext.startsWith(StringPool.FORWARD_SLASH) ||
			 remotePathContext.endsWith(StringPool.FORWARD_SLASH))) {

			roe = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_PATH_CONTEXT);

			roe.setRemotePathContext(remotePathContext);

			throw roe;
		}

		validateRemoteGroup(
			groupId, remoteGroupId, remoteAddress, remotePort,
			remotePathContext, secureConnection);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #validateRemote(long, String,
	 *             int, String, boolean, long)}
	 */
	@Deprecated
	@Override
	public void validateRemote(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId) {
	}

	protected void deleteRecentLayoutRevisionId(
		PortalPreferences portalPreferences, long layoutSetBranchId,
		long plid) {

		String oldPortalPreferences = portalPreferences.getValue(
			Staging.class.getName(),
			StagingConstants.STAGING_RECENT_LAYOUT_IDS_MAP);

		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			JSONArray oldJsonArray = JSONFactoryUtil.createJSONArray(
				oldPortalPreferences);

			String recentLayoutRevisionIdKey = getRecentLayoutRevisionIdKey(
				layoutSetBranchId, plid);

			for (int i = 0; i < oldJsonArray.length(); i ++) {
				JSONObject jsonObject = oldJsonArray.getJSONObject(i);

				if (Validator.isNotNull(
						jsonObject.getString(recentLayoutRevisionIdKey))) {

					continue;
				}

				jsonArray.put(jsonObject);
			}

			portalPreferences.setValue(
				Staging.class.getName(),
				StagingConstants.STAGING_RECENT_LAYOUT_IDS_MAP,
				jsonArray.toString());
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to delete recent layout revision ID with layout " +
						"set branch " + layoutSetBranchId + " and PLID " + plid,
					jsone);
			}
		}
	}

	protected void doCopyRemoteLayouts(
			ExportImportConfiguration exportImportConfiguration,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, boolean remotePrivateLayout)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		String remoteURL = buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, remotePrivateLayout);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			remoteURL, user.getLogin(), user.getPassword(),
			user.getPasswordEncrypted());

		taskContextMap.put("httpPrincipal", httpPrincipal);

		BackgroundTaskManagerUtil.addBackgroundTask(
			user.getUserId(), exportImportConfiguration.getGroupId(),
			StringPool.BLANK,
			BackgroundTaskExecutorNames.
				LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR,
			taskContextMap, new ServiceContext());
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

	protected PortalPreferences getPortalPreferences(User user) {
		boolean signedIn = !user.isDefaultUser();

		return PortletPreferencesFactoryUtil.getPortalPreferences(
			user.getUserId(), signedIn);
	}

	protected long getRecentLayoutBranchId(
		PortalPreferences portalPreferences, long layoutSetBranchId,
		long plid) {

		try {
			return getRecentLayoutAttribute(
				portalPreferences,
				getRecentLayoutBranchIdKey(layoutSetBranchId, plid));
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get recent layout branch ID with layout set " +
						"branch " + layoutSetBranchId + " and PLID " + plid,
					jsone);
			}
		}

		return 0;
	}

	protected String getRecentLayoutBranchIdKey(
		long layoutSetBranchId, long plid) {

		StringBundler sb = new StringBundler(4);

		sb.append("layoutBranchId-");
		sb.append(layoutSetBranchId);
		sb.append(StringPool.DASH);
		sb.append(plid);

		return sb.toString();
	}

	protected long getRecentLayoutRevisionId(
			PortalPreferences portalPreferences, long layoutSetBranchId,
			long plid)
		throws PortalException {

		long layoutRevisionId = 0;

		try {
			layoutRevisionId = getRecentLayoutAttribute(
				portalPreferences,
				getRecentLayoutRevisionIdKey(layoutSetBranchId, plid));
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get recent layout revision ID with layout set " +
						"branch " + layoutSetBranchId + " and PLID " + plid,
					jsone);
			}
		}

		if (layoutRevisionId > 0) {
			return layoutRevisionId;
		}

		long layoutBranchId = getRecentLayoutBranchId(
			portalPreferences, layoutSetBranchId, plid);

		if (layoutBranchId > 0) {
			try {
				_layoutBranchLocalService.getLayoutBranch(layoutBranchId);
			}
			catch (NoSuchLayoutBranchException nslbe) {
				LayoutBranch layoutBranch =
					_layoutBranchLocalService.getMasterLayoutBranch(
						layoutSetBranchId, plid);

				layoutBranchId = layoutBranch.getLayoutBranchId();
			}
		}

		if (layoutBranchId > 0) {
			try {
				LayoutRevision layoutRevision =
					_layoutRevisionLocalService.getLayoutRevision(
						layoutSetBranchId, layoutBranchId, plid);

				if (layoutRevision != null) {
					layoutRevisionId = layoutRevision.getLayoutRevisionId();
				}
			}
			catch (NoSuchLayoutRevisionException nslre) {
			}
		}

		return layoutRevisionId;
	}

	protected String getRecentLayoutRevisionIdKey(
		long layoutSetBranchId, long plid) {

		StringBundler sb = new StringBundler(4);

		sb.append("layoutRevisionId-");
		sb.append(layoutSetBranchId);
		sb.append(StringPool.DASH);
		sb.append(plid);

		return sb.toString();
	}

	protected String getRecentLayoutSetBranchIdKey(long layoutSetId) {
		return "layoutSetBranchId_" + layoutSetId;
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

	protected boolean isCompanyGroup(HttpPrincipal httpPrincipal, Group group) {
		ClassName className = ClassNameServiceHttp.fetchClassName(
			httpPrincipal, String.valueOf(group.getClassNameId()));

		if (Validator.equals(
				className.getClassName(), Company.class.getName())) {

			return true;
		}

		return false;
	}

	protected void publishLayouts(
			PortletRequest portletRequest, long sourceGroupId,
			long targetGroupId, Map<String, String[]> parameterMap,
			boolean schedule)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String tabs1 = ParamUtil.getString(portletRequest, "tabs1");

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		long[] layoutIds = ExportImportHelperUtil.getLayoutIds(
			portletRequest, targetGroupId);

		if (schedule) {
			String groupName = getSchedulerGroupName(
				DestinationNames.LAYOUTS_LOCAL_PUBLISHER, targetGroupId);

			int recurrenceType = ParamUtil.getInteger(
				portletRequest, "recurrenceType");

			Calendar startCalendar = ExportImportDateUtil.getCalendar(
				portletRequest, "schedulerStartDate", true);

			String cronText = SchedulerEngineHelperUtil.getCronText(
				portletRequest, startCalendar, true, recurrenceType);

			Date schedulerEndDate = null;

			int endDateType = ParamUtil.getInteger(
				portletRequest, "endDateType");

			if (endDateType == 1) {
				Calendar endCalendar = ExportImportDateUtil.getCalendar(
					portletRequest, "schedulerEndDate", true);

				schedulerEndDate = endCalendar.getTime();
			}

			String description = ParamUtil.getString(
				portletRequest, "description");

			_layoutService.schedulePublishToLive(
				sourceGroupId, targetGroupId, privateLayout, layoutIds,
				parameterMap, groupName, cronText, startCalendar.getTime(),
				schedulerEndDate, description);
		}
		else {
			publishLayouts(
				themeDisplay.getUserId(), sourceGroupId, targetGroupId,
				privateLayout, layoutIds, parameterMap);
		}
	}

	protected void publishToRemote(
			PortletRequest portletRequest, boolean schedule)
		throws PortalException {

		String tabs1 = ParamUtil.getString(portletRequest, "tabs1");

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		Map<Long, Boolean> layoutIdMap = ExportImportHelperUtil.getLayoutIdMap(
			portletRequest);

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);

		Group group = _groupLocalService.getGroup(groupId);

		UnicodeProperties groupTypeSettingsProperties =
			group.getTypeSettingsProperties();

		String remoteAddress = ParamUtil.getString(
			portletRequest, "remoteAddress",
			groupTypeSettingsProperties.getProperty("remoteAddress"));

		remoteAddress = stripProtocolFromRemoteAddress(remoteAddress);

		int remotePort = ParamUtil.getInteger(
			portletRequest, "remotePort",
			GetterUtil.getInteger(
				groupTypeSettingsProperties.getProperty("remotePort")));
		String remotePathContext = ParamUtil.getString(
			portletRequest, "remotePathContext",
			groupTypeSettingsProperties.getProperty("remotePathContext"));
		boolean secureConnection = ParamUtil.getBoolean(
			portletRequest, "secureConnection",
			GetterUtil.getBoolean(
				groupTypeSettingsProperties.getProperty("secureConnection")));
		long remoteGroupId = ParamUtil.getLong(
			portletRequest, "remoteGroupId",
			GetterUtil.getLong(
				groupTypeSettingsProperties.getProperty("remoteGroupId")));
		boolean remotePrivateLayout = ParamUtil.getBoolean(
			portletRequest, "remotePrivateLayout");

		validateRemote(
			groupId, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId);

		if (schedule) {
			String groupName = getSchedulerGroupName(
				DestinationNames.LAYOUTS_REMOTE_PUBLISHER, groupId);

			int recurrenceType = ParamUtil.getInteger(
				portletRequest, "recurrenceType");

			Calendar startCalendar = ExportImportDateUtil.getCalendar(
				portletRequest, "schedulerStartDate", true);

			String cronText = SchedulerEngineHelperUtil.getCronText(
				portletRequest, startCalendar, true, recurrenceType);

			Date schedulerEndDate = null;

			int endDateType = ParamUtil.getInteger(
				portletRequest, "endDateType");

			if (endDateType == 1) {
				Calendar endCalendar = ExportImportDateUtil.getCalendar(
					portletRequest, "schedulerEndDate", true);

				schedulerEndDate = endCalendar.getTime();
			}

			String description = ParamUtil.getString(
				portletRequest, "description");

			_layoutService.schedulePublishToRemote(
				groupId, privateLayout, layoutIdMap, parameterMap,
				remoteAddress, remotePort, remotePathContext, secureConnection,
				remoteGroupId, remotePrivateLayout, null, null, groupName,
				cronText, startCalendar.getTime(), schedulerEndDate,
				description);
		}
		else {
			copyRemoteLayouts(
				groupId, privateLayout, layoutIdMap, parameterMap,
				remoteAddress, remotePort, remotePathContext, secureConnection,
				remoteGroupId, remotePrivateLayout);
		}
	}

	@Reference(unbind = "-")
	protected void setExportImportConfigurationLocalService(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService) {

		_exportImportConfigurationLocalService =
			exportImportConfigurationLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutBranchLocalService(
		LayoutBranchLocalService layoutBranchLocalService) {

		_layoutBranchLocalService = layoutBranchLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutService(LayoutService layoutService) {
		_layoutService = layoutService;
	}

	protected void setRecentLayoutBranchId(
		PortalPreferences portalPreferences, long layoutSetBranchId, long plid,
		long layoutBranchId) {

		try {
			setRecentLayoutAttribute(
				portalPreferences,
				getRecentLayoutBranchIdKey(layoutSetBranchId, plid),
				layoutBranchId);

			ProxiedLayoutsThreadLocal.clearProxiedLayouts();
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to set recent layout branch ID with layout set " +
						"branch " + layoutSetBranchId + " and PLID " + plid +
							" and layout branch " + layoutBranchId,
					jsone);
			}
		}
	}

	protected void setRecentLayoutRevisionId(
		PortalPreferences portalPreferences, long layoutSetBranchId, long plid,
		long layoutRevisionId) {

		long layoutBranchId = 0;

		try {
			LayoutRevision layoutRevision =
				_layoutRevisionLocalService.getLayoutRevision(layoutRevisionId);

			layoutBranchId = layoutRevision.getLayoutBranchId();

			LayoutRevision lastLayoutRevision =
				_layoutRevisionLocalService.getLayoutRevision(
					layoutSetBranchId, layoutBranchId, plid);

			if (lastLayoutRevision.getLayoutRevisionId() == layoutRevisionId) {
				deleteRecentLayoutRevisionId(
					portalPreferences, layoutSetBranchId, plid);
			}
			else {
				setRecentLayoutAttribute(
					portalPreferences,
					getRecentLayoutRevisionIdKey(layoutSetBranchId, plid),
					layoutRevisionId);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to set recent layout revision ID with layout set " +
						"branch " + layoutSetBranchId + " and PLID " + plid +
							" and layout branch " + layoutBranchId,
					pe);
			}
		}

		setRecentLayoutBranchId(
			portalPreferences, layoutSetBranchId, plid, layoutBranchId);
	}

	@Reference(unbind = "-")
	protected void setStagingLocalService(
		StagingLocalService stagingLocalService) {

		_stagingLocalService = stagingLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected void setWorkflowInstanceLinkLocalService(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {

		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	protected void validateRemoteGroup(
			long groupId, long remoteGroupId, String remoteAddress,
			int remotePort, String remotePathContext, boolean secureConnection)
		throws PortalException {

		if (remoteGroupId <= 0) {
			RemoteOptionsException roe = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_GROUP_ID);

			roe.setRemoteGroupId(remoteGroupId);

			throw roe;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		String remoteURL = buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, false);

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			remoteURL, user.getLogin(), user.getPassword(),
			user.getPasswordEncrypted());

		try {

			// Ping the remote host and verify that the remote group exists in
			// the same company as the remote user

			GroupServiceHttp.checkRemoteStagingGroup(
				httpPrincipal, remoteGroupId);

			// Ensure that the local group and the remote group are not the same
			// group and that they are either both company groups or both not
			// company groups

			Group group = _groupLocalService.getGroup(groupId);

			Group remoteGroup = GroupServiceHttp.getGroup(
				httpPrincipal, remoteGroupId);

			if (group.isCompany() ^
				isCompanyGroup(httpPrincipal, remoteGroup)) {

				RemoteExportException ree = new RemoteExportException(
					RemoteExportException.INVALID_GROUP);

				ree.setGroupId(remoteGroupId);

				throw ree;
			}
		}
		catch (NoSuchGroupException nsge) {
			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_GROUP);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (PrincipalException pe) {
			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_PERMISSIONS);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (RemoteAuthException rae) {
			rae.setURL(remoteURL);

			throw rae;
		}
		catch (SystemException se) {
			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.BAD_CONNECTION, se.getMessage());

			ree.setURL(remoteURL);

			throw ree;
		}
	}

	private long getRecentLayoutAttribute(
			PortalPreferences portalPreferences, String key)
		throws JSONException {

		String preferencesString = portalPreferences.getValue(
			Staging.class.getName(),
			StagingConstants.STAGING_RECENT_LAYOUT_IDS_MAP);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			preferencesString);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.has(key)) {
				return GetterUtil.getLong(jsonObject.getString(key));
			}
		}

		return 0;
	}

	private void setRecentLayoutAttribute(
			PortalPreferences portalPreferences, String key, long value)
		throws JSONException {

		String oldPortalPreferences = portalPreferences.getValue(
			Staging.class.getName(),
			StagingConstants.STAGING_RECENT_LAYOUT_IDS_MAP);

		JSONArray oldJsonArray = JSONFactoryUtil.createJSONArray(
			oldPortalPreferences);

		boolean alreadyExists = false;

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < oldJsonArray.length(); i++) {
			JSONObject jsonObject = oldJsonArray.getJSONObject(i);

			if (Validator.isNotNull(jsonObject.getString(key))) {
				alreadyExists = true;

				jsonObject.remove(key);

				jsonObject.put(key, String.valueOf(value));
			}

			jsonArray.put(jsonObject);
		}

		if (!alreadyExists) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(key, String.valueOf(value));

			jsonArray.put(jsonObject);
		}

		portalPreferences.setValue(
			Staging.class.getName(),
			StagingConstants.STAGING_RECENT_LAYOUT_IDS_MAP,
			jsonArray.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(StagingImpl.class);

	private volatile ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;
	private volatile GroupLocalService _groupLocalService;
	private volatile LayoutBranchLocalService _layoutBranchLocalService;
	private volatile LayoutLocalService _layoutLocalService;
	private volatile LayoutRevisionLocalService _layoutRevisionLocalService;
	private volatile LayoutService _layoutService;
	private volatile StagingLocalService _stagingLocalService;
	private volatile UserLocalService _userLocalService;
	private volatile WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}