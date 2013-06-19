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

package com.liferay.portal.service.impl;

import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.NoSuchVirtualHostException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutSetLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Date now = new Date();

		long layoutSetId = counterLocalService.increment();

		LayoutSet layoutSet = layoutSetPersistence.create(layoutSetId);

		layoutSet.setGroupId(groupId);
		layoutSet.setCompanyId(group.getCompanyId());
		layoutSet.setCreateDate(now);
		layoutSet.setModifiedDate(now);
		layoutSet.setPrivateLayout(privateLayout);

		if (group.isStagingGroup()) {
			LayoutSet liveLayoutSet = null;

			Group liveGroup = group.getLiveGroup();

			if (privateLayout) {
				liveLayoutSet = liveGroup.getPrivateLayoutSet();
			}
			else {
				liveLayoutSet = liveGroup.getPublicLayoutSet();
			}

			layoutSet.setLogo(liveLayoutSet.getLogo());
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
			layoutSet.setWapThemeId(liveLayoutSet.getWapThemeId());
			layoutSet.setWapColorSchemeId(liveLayoutSet.getWapColorSchemeId());
			layoutSet.setCss(liveLayoutSet.getCss());
			layoutSet.setSettings(liveLayoutSet.getSettings());
		}
		else {
			layoutSet.setThemeId(
				ThemeFactoryUtil.getDefaultRegularThemeId(
					group.getCompanyId()));
			layoutSet.setColorSchemeId(
				ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId());
			layoutSet.setWapThemeId(
				ThemeFactoryUtil.getDefaultWapThemeId(group.getCompanyId()));
			layoutSet.setWapColorSchemeId(
				ColorSchemeFactoryUtil.getDefaultWapColorSchemeId());
			layoutSet.setCss(StringPool.BLANK);
			layoutSet.setSettings(StringPool.BLANK);
		}

		layoutSetPersistence.update(layoutSet);

		return layoutSet;
	}

	@Override
	public void deleteLayoutSet(
			long groupId, boolean privateLayout, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		// Layouts

		serviceContext.setSkipUpdatePageCount(true);

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
						"Unable to delete image " + layoutSet.getLogoId());
				}
			}
		}

		// Layout set

		layoutSetPersistence.removeByG_P(groupId, privateLayout);

		if (!group.isStagingGroup() && group.isOrganization() &&
			group.isSite()) {

			LayoutSet newLayoutSet = addLayoutSet(
				group.getGroupId(), privateLayout);

			newLayoutSet.setLogoId(layoutSet.getLogoId());

			layoutSetPersistence.update(newLayoutSet);
		}

		// Counter

		counterLocalService.reset(
			LayoutLocalServiceImpl.getCounterName(groupId, privateLayout));

		// Virtual host

		try {
			virtualHostPersistence.removeByC_L(
				layoutSet.getCompanyId(), layoutSet.getLayoutSetId());
		}
		catch (NoSuchVirtualHostException nsvhe) {
		}
	}

	@Override
	public LayoutSet fetchLayoutSet(String virtualHostname)
		throws SystemException {

		virtualHostname = virtualHostname.trim().toLowerCase();

		VirtualHost virtualHost = virtualHostPersistence.fetchByHostname(
			virtualHostname);

		if ((virtualHost == null) || (virtualHost.getLayoutSetId() == 0)) {
			return null;
		}

		return layoutSetPersistence.fetchByPrimaryKey(
			virtualHost.getLayoutSetId());
	}

	@Override
	public LayoutSet getLayoutSet(long groupId, boolean privateLayout)
		throws PortalException, SystemException {

		return layoutSetPersistence.findByG_P(groupId, privateLayout);
	}

	@Override
	public LayoutSet getLayoutSet(String virtualHostname)
		throws PortalException, SystemException {

		virtualHostname = virtualHostname.trim().toLowerCase();

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
			String layoutSetPrototypeUuid)
		throws SystemException {

		return layoutSetPersistence.findByLayoutSetPrototypeUuid(
			layoutSetPrototypeUuid);
	}

	/**
	 * Updates the state of the layout set prototype link.
	 *
	 * <p>
	 * This method can disable the layout set prototype's link by setting
	 * <code>layoutSetPrototypeLinkEnabled</code> to <code>false</code>.
	 * However, this method can only enable the layout set prototype's link if
	 * the layout set prototype's current uuid is not <code>null</code>. Setting
	 * the <code>layoutSetPrototypeLinkEnabled</code> to <code>true</code> when
	 * the layout set prototype's current uuid is <code>null</code> will have no
	 * effect.
	 * </p>
	 *
	 * @param      groupId the primary key of the group
	 * @param      privateLayout whether the layout set is private to the group
	 * @param      layoutSetPrototypeLinkEnabled whether the layout set
	 *             prototype is link enabled
	 * @throws     PortalException if a portal exception occurred
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.1.0, replaced by {@link
	 *             #updateLayoutSetPrototypeLinkEnabled(long, boolean, boolean,
	 *             String)}
	 */
	@Override
	public void updateLayoutSetPrototypeLinkEnabled(
			long groupId, boolean privateLayout,
			boolean layoutSetPrototypeLinkEnabled)
		throws PortalException, SystemException {

		updateLayoutSetPrototypeLinkEnabled(
			groupId, privateLayout, layoutSetPrototypeLinkEnabled, null);
	}

	/**
	 * Updates the state of the layout set prototype link.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout set is private to the group
	 * @param  layoutSetPrototypeLinkEnabled whether the layout set prototype is
	 *         link enabled
	 * @param  layoutSetPrototypeUuid the uuid of the layout set prototype to
	 *         link with
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updateLayoutSetPrototypeLinkEnabled(
			long groupId, boolean privateLayout,
			boolean layoutSetPrototypeLinkEnabled,
			String layoutSetPrototypeUuid)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

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
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, byte[] bytes)
		throws PortalException, SystemException {

		InputStream is = null;

		if (logo) {
			is = new ByteArrayInputStream(bytes);
		}

		return updateLogo(groupId, privateLayout, logo, is);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, File file)
		throws PortalException, SystemException {

		InputStream is = null;

		if (logo) {
			try {
				is = new FileInputStream(file);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		return updateLogo(groupId, privateLayout, logo, is);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, InputStream is)
		throws PortalException, SystemException {

		return updateLogo(groupId, privateLayout, logo, is, true);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, InputStream is,
			boolean cleanUpStream)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		layoutSet.setModifiedDate(new Date());
		layoutSet.setLogo(logo);

		if (logo) {
			long logoId = layoutSet.getLogoId();

			if (logoId <= 0) {
				logoId = counterLocalService.increment();

				layoutSet.setLogoId(logoId);
			}
		}
		else {
			layoutSet.setLogoId(0);
		}

		if (logo) {
			imageLocalService.updateImage(
				layoutSet.getLogoId(), is, cleanUpStream);
		}
		else {
			imageLocalService.deleteImage(layoutSet.getLogoId());
		}

		return layoutSetPersistence.update(layoutSet);
	}

	@Override
	public LayoutSet updateLookAndFeel(
			long groupId, boolean privateLayout, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		layoutSet.setModifiedDate(new Date());

		if (Validator.isNull(themeId)) {
			themeId = ThemeFactoryUtil.getDefaultRegularThemeId(
				layoutSet.getCompanyId());
		}

		if (Validator.isNull(colorSchemeId)) {
			colorSchemeId =
				ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId();
		}

		if (wapTheme) {
			layoutSet.setWapThemeId(themeId);
			layoutSet.setWapColorSchemeId(colorSchemeId);
		}
		else {
			layoutSet.setThemeId(themeId);
			layoutSet.setColorSchemeId(colorSchemeId);
			layoutSet.setCss(css);
		}

		layoutSetPersistence.update(layoutSet);

		if (PrefsPropsUtil.getBoolean(
				PropsKeys.THEME_SYNC_ON_GROUP,
				PropsValues.THEME_SYNC_ON_GROUP)) {

			LayoutSet otherLayoutSet = layoutSetPersistence.findByG_P(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout());

			if (wapTheme) {
				otherLayoutSet.setWapThemeId(themeId);
				otherLayoutSet.setWapColorSchemeId(colorSchemeId);
			}
			else {
				otherLayoutSet.setThemeId(themeId);
				otherLayoutSet.setColorSchemeId(colorSchemeId);
			}

			layoutSetPersistence.update(otherLayoutSet);
		}

		return layoutSet;
	}

	@Override
	public void updateLookAndFeel(
			long groupId, String themeId, String colorSchemeId, String css,
			boolean wapTheme)
		throws PortalException, SystemException {

		updateLookAndFeel(
			groupId, false, themeId, colorSchemeId, css, wapTheme);
		updateLookAndFeel(groupId, true, themeId, colorSchemeId, css, wapTheme);
	}

	@Override
	public LayoutSet updatePageCount(long groupId, boolean privateLayout)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		layoutSet.setModifiedDate(new Date());
		layoutSet.setSettings(settings);

		layoutSetPersistence.update(layoutSet);

		return layoutSet;
	}

	@Override
	public LayoutSet updateVirtualHost(
			long groupId, boolean privateLayout, String virtualHostname)
		throws PortalException, SystemException {

		virtualHostname = virtualHostname.trim().toLowerCase();

		if (virtualHostname.startsWith(Http.HTTP_WITH_SLASH) ||
			virtualHostname.startsWith(Http.HTTPS_WITH_SLASH)) {

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
			}
		}

		return layoutSet;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutSetLocalServiceImpl.class);

}