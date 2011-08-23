/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchVirtualHostException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.model.impl.ThemeImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutSetLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 * @author Ganesh Ram
 */
public class LayoutSetLocalServiceImpl extends LayoutSetLocalServiceBaseImpl {

	public LayoutSet addLayoutSet(long groupId, boolean privateLayout)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long layoutSetId = counterLocalService.increment();

		LayoutSet layoutSet = layoutSetPersistence.create(layoutSetId);

		layoutSet.setGroupId(groupId);
		layoutSet.setCompanyId(group.getCompanyId());
		layoutSet.setPrivateLayout(privateLayout);
		layoutSet.setThemeId(
			ThemeImpl.getDefaultRegularThemeId(group.getCompanyId()));
		layoutSet.setColorSchemeId(
			ColorSchemeImpl.getDefaultRegularColorSchemeId());
		layoutSet.setWapThemeId(
			ThemeImpl.getDefaultWapThemeId(group.getCompanyId()));
		layoutSet.setWapColorSchemeId(
			ColorSchemeImpl.getDefaultWapColorSchemeId());
		layoutSet.setCss(StringPool.BLANK);

		layoutSetPersistence.update(layoutSet, false);

		return layoutSet;
	}

	public void deleteLayoutSet(
			long groupId, boolean privateLayout, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		// Layouts

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		for (Layout layout : layouts) {
			try {
				layoutLocalService.deleteLayout(layout, false, serviceContext);
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		// Logo

		imageLocalService.deleteImage(layoutSet.getLogoId());

		// Layout set

		if (group.isOrganization() && group.isSite()) {
			layoutSet.setPageCount(0);

			layoutSetPersistence.update(layoutSet, false);
		}
		else {
			layoutSetPersistence.removeByG_P(groupId, privateLayout);
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

	public LayoutSet getLayoutSet(long groupId, boolean privateLayout)
		throws PortalException, SystemException {

		return layoutSetPersistence.findByG_P(groupId, privateLayout);
	}

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

	public List<LayoutSet> getLayoutSetsByLayoutSetPrototypeUuid(
			String layoutSetPrototypeUuid)
		throws SystemException {

		return layoutSetPersistence.findByLayoutSetPrototypeUuid(
			layoutSetPrototypeUuid);
	}

	public void updateLogo(
			long groupId, boolean privateLayout, boolean logo, File file)
		throws PortalException, SystemException {

		InputStream is = null;

		if (logo) {
			try{
				is = new FileInputStream(file);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		updateLogo(groupId, privateLayout, logo, is);
	}

	public void updateLogo(
			long groupId, boolean privateLayout, boolean logo, InputStream is)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		layoutSet.setLogo(logo);

		if (logo) {
			long logoId = layoutSet.getLogoId();

			if (logoId <= 0) {
				logoId = counterLocalService.increment();

				layoutSet.setLogoId(logoId);
			}
		}

		layoutSetPersistence.update(layoutSet, false);

		if (logo) {
			imageLocalService.updateImage(layoutSet.getLogoId(), is);
		}
		else {
			imageLocalService.deleteImage(layoutSet.getLogoId());
		}
	}

	public void updateLookAndFeel(
			long groupId, String themeId, String colorSchemeId, String css,
			boolean wapTheme)
		throws PortalException, SystemException {

		updateLookAndFeel(
			groupId, false, themeId, colorSchemeId, css, wapTheme);
		updateLookAndFeel(
			groupId, true, themeId, colorSchemeId, css, wapTheme);
	}

	public LayoutSet updateLookAndFeel(
			long groupId, boolean privateLayout, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		if (Validator.isNull(themeId)) {
			themeId = ThemeImpl.getDefaultRegularThemeId(
				layoutSet.getCompanyId());
		}

		if (Validator.isNull(colorSchemeId)) {
			colorSchemeId = ColorSchemeImpl.getDefaultRegularColorSchemeId();
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

		layoutSetPersistence.update(layoutSet, false);

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

			layoutSetPersistence.update(otherLayoutSet, false);
		}

		return layoutSet;
	}

	public LayoutSet updatePageCount(long groupId, boolean privateLayout)
		throws PortalException, SystemException {

		int pageCount = layoutPersistence.countByG_P(groupId, privateLayout);

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		layoutSet.setPageCount(pageCount);

		layoutSetPersistence.update(layoutSet, false);

		return layoutSet;
	}

	public LayoutSet updateSettings(
			long groupId, boolean privateLayout, String settings)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		layoutSet.setSettings(settings);

		layoutSetPersistence.update(layoutSet, false);

		return layoutSet;
	}

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
			try {
				VirtualHost virtualHost = virtualHostPersistence.findByHostname(
					virtualHostname);

				if ((virtualHost.getCompanyId() != layoutSet.getCompanyId()) ||
					(virtualHost.getLayoutSetId() !=
						layoutSet.getLayoutSetId())) {

					throw new LayoutSetVirtualHostException();
				}
			}
			catch (NoSuchVirtualHostException nsvhe) {
				virtualHostLocalService.updateVirtualHost(
					layoutSet.getCompanyId(), layoutSet.getLayoutSetId(),
					virtualHostname);
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

}