/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.sites.util;

import com.liferay.background.task.kernel.util.comparator.BackgroundTaskCreateDateComparator;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.change.tracking.CTTransactionException;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.service.persistence.LayoutUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.sites.kernel.util.Sites;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletPreferences;

/**
 * @author Raymond Augé
 * @author Ryan Park
 * @author Zsolt Berentey
 */
public class SitesImpl implements Sites {

	@Override
	public void applyLayoutPrototype(
			LayoutPrototype layoutPrototype, Layout targetLayout,
			boolean linkEnabled)
		throws Exception {

		Locale siteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		LayoutTypePortlet targetLayoutType =
			(LayoutTypePortlet)targetLayout.getLayoutType();

		List<String> targetLayoutPortletIds = targetLayoutType.getPortletIds();

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		byte[] iconBytes = null;

		if (layoutPrototypeLayout.isIconImage()) {
			Image image = ImageLocalServiceUtil.getImage(
				layoutPrototypeLayout.getIconImageId());

			iconBytes = image.getTextObj();
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Serializable originalLayoutPrototypeLinkEnabled =
			serviceContext.getAttribute("layoutPrototypeLinkEnabled");
		Serializable originalLayoutPrototypeUuid = serviceContext.getAttribute(
			"layoutPrototypeUuid");

		try {
			serviceContext.setAttribute(
				"layoutPrototypeLinkEnabled", linkEnabled);
			serviceContext.setAttribute(
				"layoutPrototypeUuid", layoutPrototype.getUuid());

			Locale targetSiteDefaultLocale = PortalUtil.getSiteDefaultLocale(
				targetLayout.getGroupId());

			LocaleThreadLocal.setSiteDefaultLocale(targetSiteDefaultLocale);

			targetLayout = LayoutLocalServiceUtil.updateLayout(
				targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
				targetLayout.getLayoutId(), targetLayout.getParentLayoutId(),
				targetLayout.getNameMap(), targetLayout.getTitleMap(),
				targetLayout.getDescriptionMap(), targetLayout.getKeywordsMap(),
				targetLayout.getRobotsMap(), layoutPrototypeLayout.getType(),
				targetLayout.isHidden(), targetLayout.getFriendlyURLMap(),
				layoutPrototypeLayout.isIconImage(), iconBytes, 0, 0,
				layoutPrototypeLayout.getMasterLayoutPlid(), serviceContext);
		}
		finally {
			if (originalLayoutPrototypeLinkEnabled == null) {
				serviceContext.removeAttribute("layoutPrototypeLinkEnabled");
			}
			else {
				serviceContext.setAttribute(
					"layoutPrototypeLinkEnabled",
					originalLayoutPrototypeLinkEnabled);
			}

			if (originalLayoutPrototypeUuid == null) {
				serviceContext.removeAttribute("layoutPrototypeUuid");
			}
			else {
				serviceContext.setAttribute(
					"layoutPrototypeUuid", originalLayoutPrototypeUuid);
			}

			LocaleThreadLocal.setSiteDefaultLocale(siteDefaultLocale);
		}

		targetLayout = LayoutLocalServiceUtil.updateLayout(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(),
			layoutPrototypeLayout.getTypeSettings());

		copyExpandoBridgeAttributes(layoutPrototypeLayout, targetLayout);

		copyPortletPermissions(targetLayout, layoutPrototypeLayout);

		copyPortletSetups(layoutPrototypeLayout, targetLayout);

		LayoutLocalServiceUtil.updateLookAndFeel(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(), layoutPrototypeLayout.getThemeId(),
			layoutPrototypeLayout.getColorSchemeId(),
			layoutPrototypeLayout.getCss());

		deleteUnreferencedPortlets(
			targetLayoutPortletIds, targetLayout, layoutPrototypeLayout);

		targetLayout = LayoutLocalServiceUtil.getLayout(targetLayout.getPlid());

		UnicodeProperties typeSettingsUnicodeProperties =
			targetLayout.getTypeSettingsProperties();

		Date modifiedDate = targetLayout.getModifiedDate();

		typeSettingsUnicodeProperties.setProperty(
			LAST_MERGE_TIME, String.valueOf(modifiedDate.getTime()));

		LayoutLocalServiceUtil.updateLayout(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(), targetLayout.getTypeSettings());

		UnicodeProperties prototypeTypeSettingsUnicodeProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		if (prototypeTypeSettingsUnicodeProperties.containsKey(
				MERGE_FAIL_COUNT)) {

			prototypeTypeSettingsUnicodeProperties.remove(MERGE_FAIL_COUNT);

			LayoutLocalServiceUtil.updateLayout(layoutPrototypeLayout);
		}
	}

	@Override
	public void copyPortletPermissions(Layout targetLayout, Layout sourceLayout)
		throws Exception {

		List<Role> roles = RoleLocalServiceUtil.getGroupRelatedRoles(
			targetLayout.getGroupId());
		Group targetGroup = targetLayout.getGroup();

		LayoutTypePortlet sourceLayoutTypePortlet =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> sourcePortletIds = sourceLayoutTypePortlet.getPortletIds();

		for (String sourcePortletId : sourcePortletIds) {
			String resourceName = PortletIdCodec.decodePortletName(
				sourcePortletId);

			String sourceResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				sourceLayout.getPlid(), sourcePortletId);

			String targetResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				targetLayout.getPlid(), sourcePortletId);

			List<String> actionIds =
				ResourceActionsUtil.getPortletResourceActions(resourceName);

			for (Role role : roles) {
				String roleName = role.getName();

				if (roleName.equals(RoleConstants.ADMINISTRATOR) ||
					(!targetGroup.isLayoutSetPrototype() &&
					 targetLayout.isPrivateLayout() &&
					 roleName.equals(RoleConstants.GUEST))) {

					continue;
				}

				List<String> actions =
					ResourcePermissionLocalServiceUtil.
						getAvailableResourcePermissionActionIds(
							targetLayout.getCompanyId(), resourceName,
							ResourceConstants.SCOPE_INDIVIDUAL,
							sourceResourcePrimKey, role.getRoleId(), actionIds);

				ResourcePermissionLocalServiceUtil.setResourcePermissions(
					targetLayout.getCompanyId(), resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, targetResourcePrimKey,
					role.getRoleId(), actions.toArray(new String[0]));
			}
		}
	}

	@Override
	public void copyPortletSetups(Layout sourceLayout, Layout targetLayout)
		throws Exception {

		LayoutTypePortlet sourceLayoutTypePortlet =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> sourcePortletIds = ListUtil.toList(
			sourceLayoutTypePortlet.getAllPortlets(),
			Portlet.PORTLET_ID_ACCESSOR);

		for (String sourcePortletId : sourcePortletIds) {
			PortletPreferences sourcePreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					sourceLayout, sourcePortletId, null);

			PortletPreferencesImpl sourcePortletPreferencesImpl =
				(PortletPreferencesImpl)sourcePreferences;

			PortletPreferences targetPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					targetLayout, sourcePortletId, null);

			PortletPreferencesImpl targetPortletPreferencesImpl =
				(PortletPreferencesImpl)targetPreferences;

			PortletPreferencesLocalServiceUtil.updatePreferences(
				targetPortletPreferencesImpl.getOwnerId(),
				targetPortletPreferencesImpl.getOwnerType(),
				targetPortletPreferencesImpl.getPlid(), sourcePortletId,
				sourcePreferences);

			if ((sourcePortletPreferencesImpl.getOwnerId() !=
					PortletKeys.PREFS_OWNER_ID_DEFAULT) &&
				(sourcePortletPreferencesImpl.getOwnerType() !=
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT)) {

				sourcePreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						sourceLayout, sourcePortletId);

				targetPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						targetLayout, sourcePortletId);

				targetPortletPreferencesImpl =
					(PortletPreferencesImpl)targetPreferences;

				PortletPreferencesLocalServiceUtil.updatePreferences(
					targetPortletPreferencesImpl.getOwnerId(),
					targetPortletPreferencesImpl.getOwnerType(),
					targetPortletPreferencesImpl.getPlid(), sourcePortletId,
					sourcePreferences);
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			_updateLayoutScopes(
				serviceContext.getUserId(), sourceLayout, targetLayout,
				sourcePreferences, targetPreferences, sourcePortletId,
				serviceContext.getLanguageId());
		}
	}

	@Override
	public boolean isLayoutModifiedSinceLastMerge(Layout layout) {
		if ((layout == null) ||
			Validator.isNull(layout.getSourcePrototypeLayoutUuid()) ||
			layout.isLayoutPrototypeLinkActive() ||
			(layout instanceof VirtualLayout) || !layout.isLayoutUpdateable()) {

			return false;
		}

		long lastMergeTime = GetterUtil.getLong(
			layout.getTypeSettingsProperty(LAST_MERGE_TIME));

		if (lastMergeTime == 0) {
			return false;
		}

		Date existingLayoutModifiedDate = layout.getModifiedDate();

		if ((existingLayoutModifiedDate != null) &&
			(existingLayoutModifiedDate.getTime() > lastMergeTime)) {

			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the linked site template can be merged into
	 * the layout set. This method checks the current number of merge fail
	 * attempts stored for the linked site template and, if greater than the
	 * merge fail threshold, will return <code>false</code>.
	 *
	 * @param  group the site template's group, which is about to be merged into
	 *         the layout set
	 * @param  layoutSet the site in which the site template is attempting to
	 *         merge into
	 * @return <code>true</code> if the linked site template can be merged into
	 *         the layout set; <code>false</code> otherwise
	 */
	@Override
	public boolean isLayoutSetMergeable(Group group, LayoutSet layoutSet)
		throws PortalException {

		if (!layoutSet.isLayoutSetPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.isLayoutSetPrototype()) {

			return false;
		}

		UnicodeProperties settingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		long lastMergeTime = GetterUtil.getLong(
			settingsUnicodeProperties.getProperty(LAST_MERGE_TIME));
		long lastMergeVersion = GetterUtil.getLong(
			settingsUnicodeProperties.getProperty(LAST_MERGE_VERSION));

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		Date modifiedDate = layoutSetPrototype.getModifiedDate();

		if ((lastMergeTime >= modifiedDate.getTime()) &&
			((lastMergeVersion == 0) ||
			 (lastMergeVersion == layoutSetPrototype.getMvccVersion())) &&
			!isAnyFailedLayoutModifiedSinceLastMerge(layoutSet)) {

			return false;
		}

		UnicodeProperties layoutSetPrototypeSettingsUnicodeProperties =
			layoutSetPrototype.getSettingsProperties();

		boolean readyForPropagation = GetterUtil.getBoolean(
			layoutSetPrototypeSettingsUnicodeProperties.getProperty(
				"readyForPropagation"),
			true);

		if (!readyForPropagation && !(lastMergeTime == 0)) {
			return false;
		}

		LayoutSet layoutSetPrototypeLayoutSet =
			layoutSetPrototype.getLayoutSet();

		UnicodeProperties layoutSetPrototypeLayoutSetSettingsUnicodeProperties =
			layoutSetPrototypeLayoutSet.getSettingsProperties();

		int mergeFailCount = GetterUtil.getInteger(
			layoutSetPrototypeLayoutSetSettingsUnicodeProperties.getProperty(
				MERGE_FAIL_COUNT));

		if (mergeFailCount >
				PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Merge not performed because the fail threshold was ",
						"reached for layoutSetPrototypeId ",
						layoutSetPrototype.getLayoutSetPrototypeId(),
						" and layoutId ",
						layoutSetPrototypeLayoutSet.getLayoutSetId(),
						". Update the count in the database to try again."));
			}

			return false;
		}

		return true;
	}

	@Override
	public void mergeLayoutPrototypeLayout(Group group, Layout layout)
		throws Exception {

		String sourcePrototypeLayoutUuid =
			layout.getSourcePrototypeLayoutUuid();

		if (Validator.isNull(sourcePrototypeLayoutUuid)) {
			doMergeLayoutPrototypeLayout(group, layout);

			return;
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		long layoutSetPrototypeId = layoutSet.getLayoutSetPrototypeId();

		if (layoutSetPrototypeId > 0) {
			Group layoutSetPrototypeGroup =
				GroupLocalServiceUtil.getLayoutSetPrototypeGroup(
					layout.getCompanyId(), layoutSetPrototypeId);

			Layout sourcePrototypeLayout =
				LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
					sourcePrototypeLayoutUuid,
					layoutSetPrototypeGroup.getGroupId(), true);

			if (sourcePrototypeLayout != null) {
				doMergeLayoutPrototypeLayout(
					layoutSetPrototypeGroup, sourcePrototypeLayout);
			}
		}

		doMergeLayoutPrototypeLayout(group, layout);
	}

	@Override
	public void mergeLayoutSetPrototypeLayouts(Group group, LayoutSet layoutSet)
		throws Exception {

		if (MergeLayoutPrototypesThreadLocal.isSkipMerge()) {
			return;
		}

		MergeLayoutPrototypesThreadLocal.setSkipMerge(true);

		layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			layoutSet.getLayoutSetId());

		if (!isLayoutSetMergeable(group, layoutSet)) {
			return;
		}

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		mergeLayoutSetPrototypeLayoutsInBackground(
			layoutSetPrototype, layoutSet);
	}

	@Override
	public void removeMergeFailFriendlyURLLayouts(LayoutSet layoutSet)
		throws PortalException {

		UnicodeProperties settingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		if (settingsUnicodeProperties.containsKey(
				MERGE_FAIL_FRIENDLY_URL_LAYOUTS)) {

			settingsUnicodeProperties.remove(MERGE_FAIL_FRIENDLY_URL_LAYOUTS);

			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
		}
	}

	@Override
	public void updateLayoutSetPrototypesLinks(
			Group group, long publicLayoutSetPrototypeId,
			long privateLayoutSetPrototypeId,
			boolean publicLayoutSetPrototypeLinkEnabled,
			boolean privateLayoutSetPrototypeLinkEnabled)
		throws Exception {

		updateLayoutSetPrototypeLink(
			group.getGroupId(), true, privateLayoutSetPrototypeId,
			privateLayoutSetPrototypeLinkEnabled);
		updateLayoutSetPrototypeLink(
			group.getGroupId(), false, publicLayoutSetPrototypeId,
			publicLayoutSetPrototypeLinkEnabled);
	}

	protected void deleteUnreferencedPortlets(
			List<String> targetLayoutPortletIds, Layout targetLayout,
			Layout sourceLayout)
		throws Exception {

		LayoutTypePortlet sourceLayoutType =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> unreferencedPortletIds = new ArrayList<>(
			targetLayoutPortletIds);

		unreferencedPortletIds.removeAll(sourceLayoutType.getPortletIds());

		PortletLocalServiceUtil.deletePortlets(
			targetLayout.getCompanyId(),
			unreferencedPortletIds.toArray(new String[0]),
			targetLayout.getPlid());
	}

	protected void doMergeLayoutPrototypeLayout(Group group, Layout layout)
		throws Exception {

		if (!layout.isLayoutPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.hasStagingGroup()) {

			return;
		}

		long lastMergeTime = GetterUtil.getLong(
			layout.getTypeSettingsProperty(LAST_MERGE_TIME));

		if (lastMergeTime == 0) {
			try {
				MergeLayoutPrototypesThreadLocal.setInProgress(true);

				Layout targetLayout = LayoutLocalServiceUtil.getLayout(
					layout.getPlid());

				if (targetLayout != null) {
					lastMergeTime = GetterUtil.getLong(
						targetLayout.getTypeSettingsProperty(LAST_MERGE_TIME));
				}
			}
			finally {
				MergeLayoutPrototypesThreadLocal.setInProgress(false);
			}
		}

		LayoutPrototype layoutPrototype =
			LayoutPrototypeLocalServiceUtil.
				getLayoutPrototypeByUuidAndCompanyId(
					layout.getLayoutPrototypeUuid(), layout.getCompanyId());

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		Date modifiedDate = layoutPrototypeLayout.getModifiedDate();

		if (lastMergeTime >= modifiedDate.getTime()) {
			return;
		}

		UnicodeProperties prototypeTypeSettingsUnicodeProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		int mergeFailCount = GetterUtil.getInteger(
			prototypeTypeSettingsUnicodeProperties.getProperty(
				MERGE_FAIL_COUNT));

		if (mergeFailCount >
				PropsValues.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Merge not performed because the fail threshold was ",
						"reached for layoutPrototypeId ",
						layoutPrototype.getLayoutPrototypeId(),
						" and layoutId ", layoutPrototypeLayout.getLayoutId(),
						". Update the count in the database to try again."));
			}

			return;
		}

		String owner = _acquireLock(
			Layout.class.getName(), layout.getPlid(),
			PropsValues.LAYOUT_PROTOTYPE_MERGE_LOCK_MAX_TIME);

		if (owner == null) {
			return;
		}

		EntityCacheUtil.clearLocalCache();

		layout = LayoutLocalServiceUtil.fetchLayout(layout.getPlid());

		try {
			MergeLayoutPrototypesThreadLocal.setInProgress(true);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Applying layout prototype ", layoutPrototype.getUuid(),
						" (mvccVersion ", layoutPrototype.getMvccVersion(),
						") to layout ", layout.getPlid(), " (mvccVersion ",
						layout.getMvccVersion(), ")"));
			}

			applyLayoutPrototype(layoutPrototype, layout, true);
		}
		catch (CTTransactionException ctTransactionException) {
			throw ctTransactionException;
		}
		catch (Exception exception) {
			_log.error(exception);

			prototypeTypeSettingsUnicodeProperties.setProperty(
				MERGE_FAIL_COUNT, String.valueOf(++mergeFailCount));

			// Invoke updateImpl so that we do not trigger the listeners

			LayoutUtil.updateImpl(layoutPrototypeLayout);
		}
		finally {
			MergeLayoutPrototypesThreadLocal.setInProgress(false);

			_releaseLock(Layout.class.getName(), layout.getPlid(), owner);
		}
	}

	protected File exportLayoutSetPrototype(
		User user, LayoutSetPrototype layoutSetPrototype,
		Map<String, String[]> parameterMap, String cacheFileName) {

		File cacheFile = null;

		if (cacheFileName != null) {
			cacheFile = new File(cacheFileName);

			if (cacheFile.exists()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Using cached layout set prototype LAR file " +
							cacheFile.getAbsolutePath());
				}

				return cacheFile;
			}
		}

		long layoutSetPrototypeGroupId = 0;

		try {
			layoutSetPrototypeGroupId = layoutSetPrototype.getGroupId();
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get groupId for layout set prototype " +
					layoutSetPrototype.getLayoutSetPrototypeId(),
				portalException);

			return null;
		}

		List<Layout> layoutSetPrototypeLayouts =
			LayoutLocalServiceUtil.getLayouts(layoutSetPrototypeGroupId, true);

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, layoutSetPrototypeGroupId, true,
					ExportImportHelperUtil.getLayoutIds(
						layoutSetPrototypeLayouts),
					parameterMap);

		ExportImportConfiguration exportImportConfiguration = null;

		try {
			exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						exportLayoutSettingsMap);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add draft export-import configuration",
				portalException);

			return null;
		}

		File file = null;

		try {
			file = ExportImportLocalServiceUtil.exportLayoutsAsFile(
				exportImportConfiguration);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to export layout set prototype " +
					layoutSetPrototype.getLayoutSetPrototypeId(),
				portalException);

			return null;
		}

		if (cacheFile == null) {
			return file;
		}

		try {
			FileUtil.copyFile(file, cacheFile);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Copied ", file.getAbsolutePath(), " to ",
						cacheFile.getAbsolutePath()));
			}
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to copy file ", file.getAbsolutePath(), " to ",
					cacheFile.getAbsolutePath()),
				exception);
		}

		return cacheFile;
	}

	protected Map<String, String[]> getLayoutSetPrototypesParameters(
		boolean importData) {

		Map<String, String[]> parameterMap = LinkedHashMapBuilder.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE
			}
		).put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID}
		).build();

		if (importData) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
			parameterMap.put(
				PortletDataHandlerKeys.LOGO,
				new String[] {Boolean.TRUE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.TRUE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {Boolean.TRUE.toString()});
		}
		else {
			parameterMap.put(
				PortletDataHandlerKeys.DELETE_LAYOUTS,
				new String[] {Boolean.TRUE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.DELETIONS,
				new String[] {Boolean.TRUE.toString()});

			if (PropsValues.LAYOUT_SET_PROTOTYPE_PROPAGATE_LOGO) {
				parameterMap.put(
					PortletDataHandlerKeys.LOGO,
					new String[] {Boolean.TRUE.toString()});
			}
			else {
				parameterMap.put(
					PortletDataHandlerKeys.LOGO,
					new String[] {Boolean.FALSE.toString()});
			}

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {Boolean.FALSE.toString()});
		}

		return parameterMap;
	}

	protected void importLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype, long groupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			boolean importData)
		throws PortalException {

		File file = null;

		User user = UserLocalServiceUtil.getGuestUser(
			layoutSetPrototype.getCompanyId());

		long lastMergeVersion = layoutSetPrototype.getMvccVersion();

		parameterMap.put(
			"lastMergeVersion",
			new String[] {String.valueOf(lastMergeVersion)});

		parameterMap.put(
			"layoutSetPrototypeId",
			new String[] {
				String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId())
			});

		if (importData) {
			file = exportLayoutSetPrototype(
				user, layoutSetPrototype, parameterMap, null);
		}
		else {
			String cacheFileName = StringBundler.concat(
				_TEMP_DIR, layoutSetPrototype.getUuid(), ".v", lastMergeVersion,
				".lar");

			file = _exportInProgressMap.computeIfAbsent(
				cacheFileName,
				fileName -> exportLayoutSetPrototype(
					user, layoutSetPrototype, parameterMap, fileName));

			_exportInProgressMap.remove(cacheFileName);
		}

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		if ((file == null) ||
			isSkipImport(groupId, layoutSet, false, lastMergeVersion) ||
			isSkipImport(groupId, layoutSet, true, lastMergeVersion)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Skipping import of layout set prototype ",
						layoutSetPrototype.getUuid(), " (mvccVersion ",
						layoutSetPrototype.getMvccVersion(), ") to layout set ",
						layoutSet.getLayoutSetId(), " (mvccVersion ",
						layoutSet.getMvccVersion(), ")"));
			}

			return;
		}

		removeMergeFailFriendlyURLLayouts(layoutSet);

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildImportLayoutSettingsMap(
					user.getUserId(), groupId, privateLayout, null,
					parameterMap, user.getLocale(), user.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					user.getUserId(), groupId, StringPool.BLANK,
					StringPool.BLANK,
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap, WorkflowConstants.STATUS_DRAFT,
					new ServiceContext());

		ExportImportLocalServiceUtil.importLayoutSetPrototypeInBackground(
			user.getUserId(), exportImportConfiguration, file);
	}

	protected boolean isAnyFailedLayoutModifiedSinceLastMerge(
		LayoutSet layoutSet) {

		UnicodeProperties unicodeProperties = layoutSet.getSettingsProperties();

		String uuids = unicodeProperties.getProperty(
			MERGE_FAIL_FRIENDLY_URL_LAYOUTS);

		if (Validator.isNotNull(uuids)) {
			for (String uuid : StringUtil.split(uuids)) {
				Layout layout =
					LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
						uuid, layoutSet.getGroupId(),
						layoutSet.isPrivateLayout());

				if (layout == null) {
					return true;
				}

				Date modifiedDate = layout.getModifiedDate();

				long lastMergeTime = GetterUtil.getLong(
					unicodeProperties.getProperty(LAST_MERGE_TIME));

				if (modifiedDate.getTime() > lastMergeTime) {
					return true;
				}
			}
		}

		return false;
	}

	protected boolean isLayoutSetPrototypeMergeBackgroundTaskExists(
			LayoutSetPrototype layoutSetPrototype, LayoutSet layoutSet)
		throws PortalException {

		List<BackgroundTask> incompleteBackgroundTasks =
			BackgroundTaskManagerUtil.getBackgroundTasks(
				layoutSet.getGroupId(),
				BackgroundTaskExecutorNames.
					LAYOUT_SET_PROTOTYPE_MERGE_BACKGROUND_TASK_EXECUTOR,
				false, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new BackgroundTaskCreateDateComparator());

		for (BackgroundTask incompleteBackgroundTask :
				incompleteBackgroundTasks) {

			long exportImportConfigurationId = MapUtil.getLong(
				incompleteBackgroundTask.getTaskContextMap(),
				"exportImportConfigurationId");

			ExportImportConfiguration exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					fetchExportImportConfiguration(exportImportConfigurationId);

			if (exportImportConfiguration != null) {
				Map<String, Serializable> settingsMap =
					exportImportConfiguration.getSettingsMap();

				Map<String, String[]> parameterMap =
					(Map<String, String[]>)settingsMap.get("parameterMap");

				long layoutSetId = MapUtil.getLong(parameterMap, "layoutSetId");

				if (layoutSetId == layoutSet.getLayoutSetId()) {
					if (incompleteBackgroundTask.getStatus() !=
							BackgroundTaskConstants.STATUS_IN_PROGRESS) {

						return true;
					}

					long lastMergeVersion = MapUtil.getLong(
						parameterMap, "lastMergeVersion");

					if (lastMergeVersion ==
							layoutSetPrototype.getMvccVersion()) {

						return true;
					}
				}
			}
		}

		return false;
	}

	protected boolean isSkipImport(
		long groupId, LayoutSet layoutSet, boolean completed,
		long lastMergeVersion) {

		BackgroundTask previousBackgroundTask =
			BackgroundTaskManagerUtil.fetchFirstBackgroundTask(
				groupId,
				BackgroundTaskExecutorNames.
					LAYOUT_SET_PROTOTYPE_IMPORT_BACKGROUND_TASK_EXECUTOR,
				completed, new BackgroundTaskCreateDateComparator(false));

		if (previousBackgroundTask == null) {
			return false;
		}

		Map<String, Serializable> contextMap =
			previousBackgroundTask.getTaskContextMap();

		ExportImportConfiguration previousExportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				fetchExportImportConfiguration(
					MapUtil.getLong(contextMap, "exportImportConfigurationId"));

		if (previousExportImportConfiguration == null) {
			return false;
		}

		Map<String, Serializable> settingsMap =
			previousExportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		long previousLastMergeVersion = MapUtil.getLong(
			parameterMap, "lastMergeVersion");

		if (previousLastMergeVersion == lastMergeVersion) {
			if (isAnyFailedLayoutModifiedSinceLastMerge(layoutSet)) {
				return false;
			}

			UnicodeProperties settingsUnicodeProperties =
				layoutSet.getSettingsProperties();

			long lastResetTime = GetterUtil.getLong(
				settingsUnicodeProperties.getProperty(LAST_RESET_TIME));

			Date previousBackgroundTaskCreateDate =
				previousBackgroundTask.getCreateDate();

			if (previousBackgroundTaskCreateDate.getTime() > lastResetTime) {
				return true;
			}
		}

		return false;
	}

	protected void mergeLayoutSetPrototypeLayoutsInBackground(
			LayoutSetPrototype layoutSetPrototype, LayoutSet layoutSet)
		throws PortalException {

		if (ExportImportThreadLocal.isExportInProcess() ||
			ExportImportThreadLocal.isImportInProcess() ||
			ExportImportThreadLocal.isStagingInProcess()) {

			return;
		}

		if (isLayoutSetPrototypeMergeBackgroundTaskExists(
				layoutSetPrototype, layoutSet)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Layout set prototype merge is in progress for layout " +
						"set " + layoutSet.getLayoutSetId());
			}

			return;
		}

		UnicodeProperties settingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		boolean importData = true;

		long lastMergeTime = GetterUtil.getLong(
			settingsUnicodeProperties.getProperty(LAST_MERGE_TIME));
		long lastResetTime = GetterUtil.getLong(
			settingsUnicodeProperties.getProperty(LAST_RESET_TIME));

		if ((lastMergeTime > 0) || (lastResetTime > 0)) {
			importData = false;
		}

		Map<String, String[]> parameterMap = getLayoutSetPrototypesParameters(
			importData);

		parameterMap.put(
			"anyFailedLayoutModifiedSinceLastMerge",
			new String[] {
				String.valueOf(
					isAnyFailedLayoutModifiedSinceLastMerge(layoutSet))
			});
		parameterMap.put(
			"importData", new String[] {String.valueOf(importData)});
		parameterMap.put(
			"lastMergeVersion",
			new String[] {String.valueOf(layoutSetPrototype.getMvccVersion())});
		parameterMap.put(
			"layoutSetId",
			new String[] {String.valueOf(layoutSet.getLayoutSetId())});
		parameterMap.put(
			"layoutSetPrototypeId",
			new String[] {
				String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId())
			});

		User user = UserLocalServiceUtil.getDefaultUser(
			layoutSet.getCompanyId());

		List<Layout> layoutSetPrototypeLayouts =
			LayoutLocalServiceUtil.getLayouts(
				layoutSetPrototype.getGroupId(), true);

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, layoutSetPrototype.getGroupId(), true,
					ExportImportHelperUtil.getLayoutIds(
						layoutSetPrototypeLayouts),
					parameterMap);

		ExportImportConfiguration exportImportConfiguration = null;

		try {
			exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						exportLayoutSettingsMap);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add draft export-import configuration",
				portalException);

			return;
		}

		ExportImportLocalServiceUtil.mergeLayoutSetPrototypeInBackground(
			user.getUserId(), layoutSet.getGroupId(),
			exportImportConfiguration);
	}

	protected void updateLayoutSetPrototypeLink(
			long groupId, boolean privateLayout, long layoutSetPrototypeId,
			boolean layoutSetPrototypeLinkEnabled)
		throws Exception {

		String layoutSetPrototypeUuid = null;

		if (layoutSetPrototypeId > 0) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(
					layoutSetPrototypeId);

			if (layoutSetPrototype != null) {
				layoutSetPrototypeUuid = layoutSetPrototype.getUuid();

				// Merge without enabling the link

				if (!layoutSetPrototypeLinkEnabled &&
					(layoutSetPrototypeId > 0)) {

					boolean mergeLayoutPrototypesThreadLocalInProgress =
						MergeLayoutPrototypesThreadLocal.isInProgress();

					try {
						MergeLayoutPrototypesThreadLocal.setInProgress(true);

						importLayoutSetPrototype(
							layoutSetPrototype, groupId, privateLayout,
							getLayoutSetPrototypesParameters(true), true);
					}
					finally {
						MergeLayoutPrototypesThreadLocal.setInProgress(
							mergeLayoutPrototypesThreadLocalInProgress);
					}
				}
			}
		}

		LayoutSetServiceUtil.updateLayoutSetPrototypeLinkEnabled(
			groupId, privateLayout, layoutSetPrototypeLinkEnabled,
			layoutSetPrototypeUuid);

		LayoutLocalServiceUtil.updatePriorities(groupId, privateLayout);
	}

	private String _acquireLock(
		String className, long classPK, long mergeLockMaxTime) {

		String owner = PortalUUIDUtil.generate();

		try {
			Lock lock = LockManagerUtil.lock(
				SitesImpl.class.getName(), String.valueOf(classPK), owner);

			// Double deep check

			if (!owner.equals(lock.getOwner())) {
				Date createDate = lock.getCreateDate();

				if ((System.currentTimeMillis() - createDate.getTime()) >=
						mergeLockMaxTime) {

					// Acquire lock if the lock is older than the lock max time

					lock = LockManagerUtil.lock(
						SitesImpl.class.getName(), String.valueOf(classPK),
						lock.getOwner(), owner);

					// Check if acquiring the lock succeeded or if another
					// process has the lock

					if (!owner.equals(lock.getOwner())) {
						return null;
					}
				}
				else {
					return null;
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Acquired lock for ", SitesImpl.class.getName(),
					" to update ", className, StringPool.POUND, classPK));
		}

		return owner;
	}

	private void _releaseLock(String className, long classPK, String owner) {
		LockManagerUtil.unlock(
			SitesImpl.class.getName(), String.valueOf(classPK), owner);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Released lock for ", SitesImpl.class.getName(),
					" to update ", className, StringPool.POUND, classPK));
		}
	}

	private void _updateLayoutScopes(
			long userId, Layout sourceLayout, Layout targetLayout,
			PortletPreferences sourcePreferences,
			PortletPreferences targetPreferences, String sourcePortletId,
			String languageId)
		throws Exception {

		String scopeType = GetterUtil.getString(
			sourcePreferences.getValue("lfrScopeType", null));

		if (Validator.isNull(scopeType) || !scopeType.equals("layout")) {
			return;
		}

		Layout targetScopeLayout =
			LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
				targetLayout.getUuid(), targetLayout.getGroupId(),
				targetLayout.isPrivateLayout());

		if (!targetScopeLayout.hasScopeGroup()) {
			GroupLocalServiceUtil.addGroup(
				userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
				Layout.class.getName(), targetLayout.getPlid(),
				GroupConstants.DEFAULT_LIVE_GROUP_ID, targetLayout.getNameMap(),
				null, 0, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
				null, false, true, null);
		}

		String newPortletTitle = PortalUtil.getNewPortletTitle(
			PortalUtil.getPortletTitle(
				PortletIdCodec.decodePortletName(sourcePortletId), languageId),
			String.valueOf(sourceLayout.getLayoutId()),
			targetLayout.getName(languageId));

		targetPreferences.setValue(
			"groupId", String.valueOf(targetLayout.getGroupId()));
		targetPreferences.setValue("lfrScopeType", "layout");
		targetPreferences.setValue(
			"lfrScopeLayoutUuid", targetLayout.getUuid());
		targetPreferences.setValue(
			"portletSetupTitle_" + languageId, newPortletTitle);
		targetPreferences.setValue(
			"portletSetupUseCustomTitle", Boolean.TRUE.toString());

		targetPreferences.store();
	}

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/layout_set_prototype/";

	private static final Log _log = LogFactoryUtil.getLog(SitesImpl.class);

	private final ConcurrentHashMap<String, File> _exportInProgressMap =
		new ConcurrentHashMap<>();

}