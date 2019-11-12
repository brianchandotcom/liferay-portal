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

package com.liferay.portal.service.impl;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.exception.LayoutSetVirtualHostException;
import com.liferay.portal.kernel.exception.NoSuchImageException;
import com.liferay.portal.kernel.exception.NoSuchVirtualHostException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetStagingHandler;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.model.impl.LayoutSetModelImpl;
import com.liferay.portal.service.base.LayoutSetLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.IDN;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Callable;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 * @author Ganesh Ram
 */
public class LayoutSetLocalServiceImpl extends LayoutSetLocalServiceBaseImpl {

	@Override
	public LayoutSet addLayoutSet(long groupId, boolean privateLayout)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Date now = new Date();

		long layoutSetId = counterLocalService.increment(
			LayoutSet.class.getName());

		LayoutSet layoutSet = layoutSetPersistence.create(layoutSetId);

		layoutSet.setGroupId(groupId);
		layoutSet.setCompanyId(group.getCompanyId());
		layoutSet.setCreateDate(now);
		layoutSet.setModifiedDate(now);
		layoutSet.setPrivateLayout(privateLayout);

		layoutSet = initLayoutSet(layoutSet);

		layoutSetPersistence.update(layoutSet);

		return layoutSet;
	}

	@Override
	public void deleteLayoutSet(
			long groupId, boolean privateLayout, ServiceContext serviceContext)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		// Layouts

		serviceContext.setAttribute("updatePageCount", Boolean.FALSE);

		layoutLocalService.deleteLayouts(
			groupId, privateLayout, serviceContext);

		// Logo

		if (group.isStagingGroup() || !group.isOrganization() ||
			!group.isSite()) {

			try {
				imageLocalService.deleteImage(layoutSet.getLogoId());
			}
			catch (NoSuchImageException nsie) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to delete image " + layoutSet.getLogoId(),
						nsie);
				}
			}
		}

		// Layout set

		if (!group.isStagingGroup() && group.isOrganization() &&
			group.isSite()) {

			layoutSet = initLayoutSet(layoutSet);

			layoutSet.setLogoId(layoutSet.getLogoId());

			layoutSetPersistence.update(layoutSet);
		}
		else {
			layoutSetPersistence.removeByG_P(groupId, privateLayout);
		}

		// Virtual host

		virtualHostPersistence.removeByC_L(
			layoutSet.getCompanyId(), layoutSet.getLayoutSetId());
	}

	@Override
	public LayoutSet fetchLayoutSet(long groupId, boolean privateLayout) {
		return layoutSetPersistence.fetchByG_P(groupId, privateLayout);
	}

	@Override
	public LayoutSet fetchLayoutSet(String virtualHostname) {
		virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(virtualHostname));

		VirtualHost virtualHost = virtualHostPersistence.fetchByHostname(
			virtualHostname);

		if ((virtualHost == null) && virtualHostname.startsWith("xn--")) {
			virtualHost = virtualHostPersistence.fetchByHostname(
				IDN.toUnicode(virtualHostname));
		}

		if ((virtualHost == null) || (virtualHost.getLayoutSetId() == 0)) {
			return null;
		}

		return layoutSetPersistence.fetchByPrimaryKey(
			virtualHost.getLayoutSetId());
	}

	@Override
	public LayoutSet fetchLayoutSetByLogoId(boolean privateLayout, long logoId)
		throws PortalException {

		return layoutSetPersistence.fetchByP_L(privateLayout, logoId);
	}

	@Override
	public LayoutSet getLayoutSet(long groupId, boolean privateLayout)
		throws PortalException {

		return layoutSetPersistence.findByG_P(groupId, privateLayout);
	}

	@Override
	public LayoutSet getLayoutSet(String virtualHostname)
		throws PortalException {

		virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(virtualHostname));

		VirtualHost virtualHost = null;

		try {
			virtualHost = virtualHostPersistence.findByHostname(
				virtualHostname);
		}
		catch (NoSuchVirtualHostException nsvhe) {
			if (virtualHostname.startsWith("xn--")) {
				virtualHost = virtualHostPersistence.findByHostname(
					IDN.toUnicode(virtualHostname));
			}
			else {
				throw nsvhe;
			}
		}

		if (virtualHost.getLayoutSetId() == 0) {
			throw new LayoutSetVirtualHostException(
				"Virtual host is associated with company " +
					virtualHost.getCompanyId());
		}

		return layoutSetPersistence.findByPrimaryKey(
			virtualHost.getLayoutSetId());
	}

	@Override
	public List<LayoutSet> getLayoutSetsByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {

		return layoutSetPersistence.findByLayoutSetPrototypeUuid(
			layoutSetPrototypeUuid);
	}

	@Override
	public int getPageCount(long groupId, boolean privateLayout) {
		return layoutPersistence.countByG_P(groupId, privateLayout);
	}

	/**
	 * Updates the state of the layout set prototype link.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout set is private to the group
	 * @param layoutSetPrototypeLinkEnabled whether the layout set prototype is
	 *        link enabled
	 * @param layoutSetPrototypeUuid the uuid of the layout set prototype to
	 *        link with
	 */
	@Override
	public void updateLayoutSetPrototypeLinkEnabled(
			long groupId, boolean privateLayout,
			boolean layoutSetPrototypeLinkEnabled,
			String layoutSetPrototypeUuid)
		throws PortalException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		LayoutSetBranch layoutSetBranch = _getLayoutSetBranch(layoutSet);

		if (layoutSetBranch == null) {
			if (Validator.isNull(layoutSetPrototypeUuid)) {
				layoutSetPrototypeUuid = layoutSet.getLayoutSetPrototypeUuid();
			}

			if (Validator.isNull(layoutSetPrototypeUuid)) {
				layoutSetPrototypeLinkEnabled = false;
			}

			layoutSet.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
			layoutSet.setLayoutSetPrototypeLinkEnabled(
				layoutSetPrototypeLinkEnabled);

			layoutSetPersistence.update(layoutSet);

			return;
		}

		if (Validator.isNull(layoutSetPrototypeUuid)) {
			layoutSetPrototypeUuid =
				layoutSetBranch.getLayoutSetPrototypeUuid();
		}

		if (Validator.isNull(layoutSetPrototypeUuid) &&
			layoutSetPrototypeLinkEnabled) {

			throw new IllegalStateException(
				"Cannot set layoutSetPrototypeLinkEnabled to true when " +
					"layoutSetPrototypeUuid is null");
		}

		layoutSetBranch.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
		layoutSetBranch.setLayoutSetPrototypeLinkEnabled(
			layoutSetPrototypeLinkEnabled);

		layoutSetBranchPersistence.update(layoutSetBranch);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean hasLogo, byte[] bytes)
		throws PortalException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		LayoutSetBranch layoutSetBranch = _getLayoutSetBranch(layoutSet);

		if (layoutSetBranch == null) {
			layoutSet.setModifiedDate(new Date());

			PortalUtil.updateImageId(
				layoutSet, hasLogo, bytes, "logoId", 0, 0, 0);

			return layoutSetPersistence.update(layoutSet);
		}

		layoutSetBranch.setModifiedDate(new Date());

		PortalUtil.updateImageId(
			layoutSetBranch, hasLogo, bytes, "logoId", 0, 0, 0);

		layoutSetBranchPersistence.update(layoutSetBranch);

		return layoutSet;
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean hasLogo, File file)
		throws PortalException {

		byte[] bytes = null;

		try {
			bytes = FileUtil.getBytes(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return updateLogo(groupId, privateLayout, hasLogo, bytes);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean hasLogo,
			InputStream is)
		throws PortalException {

		return updateLogo(groupId, privateLayout, hasLogo, is, true);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean hasLogo,
			InputStream is, boolean cleanUpStream)
		throws PortalException {

		byte[] bytes = null;

		try {
			bytes = FileUtil.getBytes(is, -1, cleanUpStream);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return updateLogo(groupId, privateLayout, hasLogo, bytes);
	}

	@Override
	public LayoutSet updateLookAndFeel(
			long groupId, boolean privateLayout, String themeId,
			String colorSchemeId, String css)
		throws PortalException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		if (Validator.isNull(themeId)) {
			themeId = ThemeFactoryUtil.getDefaultRegularThemeId(
				layoutSet.getCompanyId());
		}

		if (Validator.isNull(colorSchemeId)) {
			colorSchemeId =
				ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId();
		}

		LayoutSetBranch layoutSetBranch = _getLayoutSetBranch(layoutSet);

		if (layoutSetBranch == null) {
			layoutSet.setModifiedDate(new Date());
			layoutSet.setThemeId(themeId);
			layoutSet.setColorSchemeId(colorSchemeId);
			layoutSet.setCss(css);

			layoutSetPersistence.update(layoutSet);

			if (PrefsPropsUtil.getBoolean(
					PropsKeys.THEME_SYNC_ON_GROUP,
					PropsValues.THEME_SYNC_ON_GROUP)) {

				LayoutSet otherLayoutSet = layoutSetPersistence.findByG_P(
					layoutSet.getGroupId(), layoutSet.isPrivateLayout());

				otherLayoutSet.setThemeId(themeId);
				otherLayoutSet.setColorSchemeId(colorSchemeId);

				layoutSetPersistence.update(otherLayoutSet);
			}

			return layoutSet;
		}

		layoutSetBranch.setModifiedDate(new Date());
		layoutSetBranch.setThemeId(themeId);
		layoutSetBranch.setColorSchemeId(colorSchemeId);
		layoutSetBranch.setCss(css);

		layoutSetBranchPersistence.update(layoutSetBranch);

		return layoutSet;
	}

	@Override
	public void updateLookAndFeel(
			long groupId, String themeId, String colorSchemeId, String css)
		throws PortalException {

		updateLookAndFeel(groupId, false, themeId, colorSchemeId, css);
		updateLookAndFeel(groupId, true, themeId, colorSchemeId, css);
	}

	@Override
	public LayoutSet updateSettings(
			long groupId, boolean privateLayout, String settings)
		throws PortalException {

		UnicodeProperties settingsProperties = new UnicodeProperties();

		settingsProperties.fastLoad(settings);

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		LayoutSetBranch layoutSetBranch = _getLayoutSetBranch(layoutSet);

		if (layoutSetBranch == null) {
			layoutSet.setModifiedDate(new Date());

			validateSettings(
				layoutSet.getSettingsProperties(), settingsProperties);

			layoutSet.setSettingsProperties(settingsProperties);

			layoutSetPersistence.update(layoutSet);

			return layoutSet;
		}

		layoutSetBranch.setModifiedDate(new Date());

		validateSettings(
			layoutSetBranch.getSettingsProperties(), settingsProperties);

		layoutSetBranch.setSettingsProperties(settingsProperties);

		layoutSetBranchPersistence.update(layoutSetBranch);

		return layoutSet;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #updateVirtualHosts(long, boolean, TreeMap)}
	 */
	@Deprecated
	@Override
	public LayoutSet updateVirtualHost(
			long groupId, boolean privateLayout, String virtualHostname)
		throws PortalException {

		return updateVirtualHosts(
			groupId, privateLayout,
			TreeMapBuilder.put(
				virtualHostname, StringPool.BLANK
			).build());
	}

	@Override
	public LayoutSet updateVirtualHosts(
			long groupId, boolean privateLayout,
			TreeMap<String, String> virtualHostnames)
		throws PortalException {

		for (String curVirtualHostname : virtualHostnames.keySet()) {
			if (!Validator.isDomain(curVirtualHostname)) {
				throw new LayoutSetVirtualHostException(
					"Invalid host name {" + curVirtualHostname + "}");
			}
		}

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		if (!virtualHostnames.isEmpty()) {
			virtualHostLocalService.updateVirtualHosts(
				layoutSet.getCompanyId(), layoutSet.getLayoutSetId(),
				virtualHostnames);
		}
		else {
			virtualHostPersistence.removeByC_L(
				layoutSet.getCompanyId(), layoutSet.getLayoutSetId());

			layoutSetPersistence.clearCache(layoutSet);

			TransactionCommitCallbackUtil.registerCallback(
				new Callable<Void>() {

					@Override
					public Void call() {
						EntityCacheUtil.removeResult(
							LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
							LayoutSetImpl.class, layoutSet.getLayoutSetId());

						return null;
					}

				});
		}

		return layoutSet;
	}

	protected LayoutSet initLayoutSet(LayoutSet layoutSet)
		throws PortalException {

		Group group = layoutSet.getGroup();

		if (group.isStagingGroup()) {
			LayoutSet liveLayoutSet = null;

			Group liveGroup = group.getLiveGroup();

			if (layoutSet.isPrivateLayout()) {
				liveLayoutSet = liveGroup.getPrivateLayoutSet();
			}
			else {
				liveLayoutSet = liveGroup.getPublicLayoutSet();
			}

			layoutSet.setLogoId(liveLayoutSet.getLogoId());

			if (liveLayoutSet.isLogo()) {
				Image logoImage = imageLocalService.getImage(
					liveLayoutSet.getLogoId());

				long logoId = counterLocalService.increment();

				imageLocalService.updateImage(
					logoId, logoImage.getTextObj(), logoImage.getType(),
					logoImage.getHeight(), logoImage.getWidth(),
					logoImage.getSize());

				layoutSet.setLogoId(logoId);
			}

			layoutSet.setThemeId(liveLayoutSet.getThemeId());
			layoutSet.setColorSchemeId(liveLayoutSet.getColorSchemeId());
			layoutSet.setCss(liveLayoutSet.getCss());
			layoutSet.setSettings(liveLayoutSet.getSettings());
		}
		else {
			layoutSet.setThemeId(
				ThemeFactoryUtil.getDefaultRegularThemeId(
					group.getCompanyId()));
			layoutSet.setColorSchemeId(
				ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId());
			layoutSet.setCss(StringPool.BLANK);
			layoutSet.setSettings(StringPool.BLANK);
		}

		return layoutSet;
	}

	protected void validateSettings(
		UnicodeProperties oldSettingsProperties,
		UnicodeProperties newSettingsProperties) {

		boolean enableJavaScript =
			PropsValues.
				FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_LAYOUTSET_JAVASCRIPT;

		if (!enableJavaScript) {
			String javaScript = oldSettingsProperties.getProperty("javascript");

			newSettingsProperties.setProperty("javascript", javaScript);
		}
	}

	private LayoutSetBranch _getLayoutSetBranch(LayoutSet layoutSet)
		throws PortalException {

		LayoutSetStagingHandler layoutSetStagingHandler =
			LayoutStagingUtil.getLayoutSetStagingHandler(layoutSet);

		if (layoutSetStagingHandler != null) {
			return layoutSetStagingHandler.getLayoutSetBranch();
		}

		if (LayoutStagingUtil.isBranchingLayoutSet(
				layoutSet.getGroup(), layoutSet.isPrivateLayout())) {

			layoutSetStagingHandler = new LayoutSetStagingHandler(layoutSet);

			return layoutSetStagingHandler.getLayoutSetBranch();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetLocalServiceImpl.class);

}