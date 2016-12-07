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
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.LayoutSetLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.List;

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

		long layoutSetId = counterLocalService.increment();

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
		virtualHostname = StringUtil.toLowerCase(virtualHostname.trim());

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

		virtualHostname = StringUtil.toLowerCase(virtualHostname.trim());

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

			layoutSet.setLayoutSetPrototypeLinkEnabled(
				layoutSetPrototypeLinkEnabled);
			layoutSet.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);

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

		layoutSetBranch.setLayoutSetPrototypeLinkEnabled(
			layoutSetPrototypeLinkEnabled);
		layoutSetBranch.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);

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
			layoutSet.setModifiedDate(new Date());

			PortalUtil.updateImageId(layoutSet, logo, bytes, "logoId", 0, 0, 0);

			return layoutSetPersistence.update(layoutSet);
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
			layoutSet.setColorSchemeId(colorSchemeId);
			layoutSet.setCss(css);
			layoutSet.setModifiedDate(new Date());
			layoutSet.setThemeId(themeId);

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

		layoutSetBranch.setColorSchemeId(colorSchemeId);
		layoutSetBranch.setCss(css);
		layoutSetBranch.setModifiedDate(new Date());
		layoutSetBranch.setThemeId(themeId);

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

		layoutSet.setModifiedDate(new Date());
		layoutSet.setPageCount(pageCount);

		layoutSetPersistence.update(layoutSet);

		return layoutSet;
	}

	@Override
	public LayoutSet updateSettings(
			long groupId, boolean privateLayout, String settings)
		throws PortalException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		LayoutSetBranch layoutSetBranch = _getLayoutSetBranch(layoutSet);

		if (layoutSetBranch == null) {
			layoutSet.setModifiedDate(new Date());
			layoutSet.setSettings(settings);

			layoutSetPersistence.update(layoutSet);

			return layoutSet;
		}

		layoutSetBranch.setModifiedDate(new Date());
		layoutSetBranch.setSettings(settings);

		layoutSetBranchPersistence.update(layoutSetBranch);

		return layoutSet;
	}

	@Override
	public LayoutSet updateVirtualHost(
			long groupId, boolean privateLayout, String virtualHostname)
		throws PortalException {

		virtualHostname = StringUtil.toLowerCase(virtualHostname.trim());

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

		boolean privateLayout = layoutSet.isPrivateLayout();

		if (group.isStagingGroup()) {
			LayoutSet liveLayoutSet = null;

			Group liveGroup = group.getLiveGroup();

			if (privateLayout) {
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

	private LayoutSetBranch _getLayoutSetBranch(LayoutSet layoutSet)
		throws PortalException {

		LayoutSetStagingHandler layoutSetStagingHandler =
			LayoutStagingUtil.getLayoutSetStagingHandler(layoutSet);

		if (layoutSetStagingHandler != null) {
			return layoutSetStagingHandler.getLayoutSetBranch();
		}

		if (LayoutStagingUtil.isBranchingLayoutSet(
				layoutSet.getGroup(), layoutSet.getPrivateLayout())) {

			layoutSetStagingHandler = new LayoutSetStagingHandler(layoutSet);

			return layoutSetStagingHandler.getLayoutSetBranch();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetLocalServiceImpl.class);

}