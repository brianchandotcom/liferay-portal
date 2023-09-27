/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.util;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalServiceUtil;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.ThemeFaviconCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class FaviconUtil {

	public static String getFaviconTitle(
		CETManager cetManager, Layout layout, Locale locale) {

		CET cet = _getCET(
			cetManager, PortalUtil.getClassNameId(Layout.class),
			layout.getPlid(), layout.getCompanyId());

		if (cet != null) {
			ThemeFaviconCET themeFaviconCET = (ThemeFaviconCET)cet;

			String faviconTitle = themeFaviconCET.getName(locale);

			if (Validator.isNotNull(faviconTitle)) {
				return faviconTitle;
			}
		}

		if (layout.getFaviconFileEntryId() > 0) {
			try {
				FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
					layout.getFaviconFileEntryId());

				return fileEntry.getTitle();
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		if (layout.getMasterLayoutPlid() > 0) {
			Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
				layout.getMasterLayoutPlid());

			if (masterLayout != null) {
				ClientExtensionEntryRel clientExtensionEntryRel =
					ClientExtensionEntryRelLocalServiceUtil.
						fetchClientExtensionEntryRel(
							PortalUtil.getClassNameId(Layout.class),
							masterLayout.getPlid(),
							ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

				if ((masterLayout.getFaviconFileEntryId() > 0) ||
					(clientExtensionEntryRel != null)) {

					return LanguageUtil.get(locale, "favicon-from-master");
				}
			}
		}

		return getFaviconTitle(layout.getLayoutSet(), locale);
	}

	public static String getFaviconTitle(LayoutSet layoutSet, Locale locale) {
		ClientExtensionEntryRel clientExtensionEntryRel =
			ClientExtensionEntryRelLocalServiceUtil.
				fetchClientExtensionEntryRel(
					PortalUtil.getClassNameId(LayoutSet.class),
					layoutSet.getLayoutSetId(),
					ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

		if ((layoutSet.getFaviconFileEntryId() > 0) ||
			(clientExtensionEntryRel != null)) {

			try {
				Group group = layoutSet.getGroup();

				return LanguageUtil.format(
					locale, "favicon-from-x",
					group.getLayoutRootNodeName(
						layoutSet.isPrivateLayout(), locale));
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		return LanguageUtil.get(locale, "favicon-from-theme");
	}

	public static String getFaviconURL(CETManager cetManager, Layout layout) {
		String faviconURL = _getThemeFaviconCETURL(
			cetManager, PortalUtil.getClassNameId(Layout.class),
			layout.getPlid(), layout.getCompanyId());

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		faviconURL = layout.getFaviconURL();

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		if (layout.getMasterLayoutPlid() > 0) {
			Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
				layout.getMasterLayoutPlid());

			if (masterLayout != null) {
				faviconURL = _getThemeFaviconCETURL(
					cetManager, PortalUtil.getClassNameId(Layout.class),
					masterLayout.getPlid(), layout.getCompanyId());

				if (Validator.isNotNull(faviconURL)) {
					return faviconURL;
				}

				faviconURL = masterLayout.getFaviconURL();

				if (Validator.isNotNull(faviconURL)) {
					return faviconURL;
				}
			}
		}

		return getFaviconURL(cetManager, layout.getLayoutSet());
	}

	public static String getFaviconURL(
		CETManager cetManager, LayoutSet layoutSet) {

		String faviconURL = _getThemeFaviconCETURL(
			cetManager, PortalUtil.getClassNameId(LayoutSet.class),
			layoutSet.getLayoutSetId(), layoutSet.getCompanyId());

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		faviconURL = layoutSet.getFaviconURL();

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		return null;
	}

	private static CET _getCET(
		CETManager cetManager, long classNameId, long classPK, long companyId) {

		ClientExtensionEntryRel clientExtensionEntryRel =
			ClientExtensionEntryRelLocalServiceUtil.
				fetchClientExtensionEntryRel(
					classNameId, classPK,
					ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

		if (clientExtensionEntryRel == null) {
			return null;
		}

		return cetManager.getCET(
			companyId, clientExtensionEntryRel.getCETExternalReferenceCode());
	}

	private static String _getThemeFaviconCETURL(
		CETManager cetManager, long classNameId, long classPK, long companyId) {

		CET cet = _getCET(cetManager, classNameId, classPK, companyId);

		if (cet == null) {
			return null;
		}

		ThemeFaviconCET themeFaviconCET = (ThemeFaviconCET)cet;

		return themeFaviconCET.getURL();
	}

	private static final Log _log = LogFactoryUtil.getLog(FaviconUtil.class);

}