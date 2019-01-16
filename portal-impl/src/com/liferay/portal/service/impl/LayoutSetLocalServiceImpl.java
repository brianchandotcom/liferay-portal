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

import java.util.Date;
import java.util.List;
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

		LayoutSet draftLayoutSet = create();

		draftLayoutSet.setGroupId(groupId);
		draftLayoutSet.setCompanyId(group.getCompanyId());
		draftLayoutSet.setCreateDate(now);
		draftLayoutSet.setModifiedDate(now);
		draftLayoutSet.setPrivateLayout(privateLayout);

		draftLayoutSet = initLayoutSet(draftLayoutSet);

		return publishDraft(draftLayoutSet);
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

			LayoutSet draftLayoutSet = getDraft(layoutSet);

			draftLayoutSet = initLayoutSet(draftLayoutSet);

			draftLayoutSet.setLogoId(layoutSet.getLogoId());

			layoutSet = publishDraft(draftLayoutSet);
		}
		else {
			layoutSetPersistence.removeByG_P(groupId, privateLayout);
		}

		// Virtual host

		try {
			virtualHostPersistence.removeByC_L(
				layoutSet.getCompanyId(), layoutSet.getLayoutSetId());
		}
		catch (NoSuchVirtualHostException nsvhe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsvhe, nsvhe);
			}
		}
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

		VirtualHost virtualHost = virtualHostPersistence.findByHostname(
			virtualHostname);

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
	public LayoutSet updateLayoutSet(LayoutSet layoutSet)
		throws PortalException {

		return publishDraft(getDraft(layoutSet));
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

			LayoutSet draftLayoutSet = getDraft(layoutSet);

			draftLayoutSet.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
			draftLayoutSet.setLayoutSetPrototypeLinkEnabled(
				layoutSetPrototypeLinkEnabled);

			publishDraft(draftLayoutSet);

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
			long groupId, boolean privateLayout, boolean logo, byte[] bytes)
		throws PortalException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		LayoutSetBranch layoutSetBranch = _getLayoutSetBranch(layoutSet);

		if (layoutSetBranch == null) {
			LayoutSet draftLayoutSet = getDraft(layoutSet);

			draftLayoutSet.setModifiedDate(new Date());

			PortalUtil.updateImageId(
				draftLayoutSet, logo, bytes, "logoId", 0, 0, 0);

			return publishDraft(draftLayoutSet);
		}

		layoutSetBranch.setModifiedDate(new Date());

		PortalUtil.updateImageId(
			layoutSetBranch, logo, bytes, "logoId", 0, 0, 0);

		layoutSetBranchPersistence.update(layoutSetBranch);

		return layoutSet;
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, File file)
		throws PortalException {

		byte[] bytes = null;

		try {
			bytes = FileUtil.getBytes(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return updateLogo(groupId, privateLayout, logo, bytes);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, InputStream is)
		throws PortalException {

		return updateLogo(groupId, privateLayout, logo, is, true);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, InputStream is,
			boolean cleanUpStream)
		throws PortalException {

		byte[] bytes = null;

		try {
			bytes = FileUtil.getBytes(is, -1, cleanUpStream);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return updateLogo(groupId, privateLayout, logo, bytes);
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
			LayoutSet draftLayoutSet = getDraft(layoutSet);

			draftLayoutSet.setModifiedDate(new Date());
			draftLayoutSet.setThemeId(themeId);
			draftLayoutSet.setColorSchemeId(colorSchemeId);
			draftLayoutSet.setCss(css);

			layoutSet = publishDraft(draftLayoutSet);

			if (PrefsPropsUtil.getBoolean(
					PropsKeys.THEME_SYNC_ON_GROUP,
					PropsValues.THEME_SYNC_ON_GROUP)) {

				LayoutSet otherLayoutSet = layoutSetPersistence.findByG_P(
					layoutSet.getGroupId(), layoutSet.isPrivateLayout());

				LayoutSet otherDraftLayoutSet = getDraft(otherLayoutSet);

				otherDraftLayoutSet.setThemeId(themeId);
				otherDraftLayoutSet.setColorSchemeId(colorSchemeId);

				publishDraft(otherDraftLayoutSet);
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
	public LayoutSet updatePageCount(long groupId, boolean privateLayout)
		throws PortalException {

		int pageCount = layoutPersistence.countByG_P(groupId, privateLayout);

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		LayoutSet draftLayoutSet = getDraft(layoutSet);

		draftLayoutSet.setModifiedDate(new Date());
		draftLayoutSet.setPageCount(pageCount);

		return publishDraft(draftLayoutSet);
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
			LayoutSet draftLayoutSet = getDraft(layoutSet);

			draftLayoutSet.setModifiedDate(new Date());

			validateSettings(
				draftLayoutSet.getSettingsProperties(), settingsProperties);

			draftLayoutSet.setSettingsProperties(settingsProperties);

			return publishDraft(draftLayoutSet);
		}

		layoutSetBranch.setModifiedDate(new Date());

		validateSettings(
			layoutSetBranch.getSettingsProperties(), settingsProperties);

		layoutSetBranch.setSettingsProperties(settingsProperties);

		layoutSetBranchPersistence.update(layoutSetBranch);

		return layoutSet;
	}

	@Override
	public LayoutSet updateVirtualHost(
			long groupId, boolean privateLayout, String virtualHostname)
		throws PortalException {

		virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(virtualHostname));

		if (Validator.isNotNull(virtualHostname) &&
			!Validator.isDomain(virtualHostname)) {

			throw new LayoutSetVirtualHostException();
		}

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		if (Validator.isNotNull(virtualHostname)) {
			VirtualHost virtualHost = virtualHostPersistence.fetchByHostname(
				virtualHostname);

			if (virtualHost == null) {
				virtualHostLocalService.updateVirtualHost(
					layoutSet.getCompanyId(), layoutSet.getLayoutSetId(),
					virtualHostname);
			}
			else {
				if ((virtualHost.getCompanyId() != layoutSet.getCompanyId()) ||
					(virtualHost.getLayoutSetId() !=
						layoutSet.getLayoutSetId())) {

					throw new LayoutSetVirtualHostException();
				}
			}
		}
		else {
			try {
				virtualHostPersistence.removeByC_L(
					layoutSet.getCompanyId(), layoutSet.getLayoutSetId());

				layoutSetPersistence.clearCache(layoutSet);

				TransactionCommitCallbackUtil.registerCallback(
					new Callable<Void>() {

						@Override
						public Void call() {
							EntityCacheUtil.removeResult(
								LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
								LayoutSetImpl.class,
								layoutSet.getLayoutSetId());

							return null;
						}

					});
			}
			catch (NoSuchVirtualHostException nsvhe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsvhe, nsvhe);
				}
			}
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