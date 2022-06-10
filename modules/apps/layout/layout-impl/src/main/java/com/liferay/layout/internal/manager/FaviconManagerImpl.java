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

package com.liferay.layout.internal.manager;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.CETThemeFavicon;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.layout.manager.FaviconManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = FaviconManager.class)
public class FaviconManagerImpl implements FaviconManager {

	@Override
	public String getFaviconTitle(Layout layout, Locale locale) {
		String faviconTitle = _getCETThemeFaviconTitle(
			_portal.getClassNameId(Layout.class), layout.getPlid(),
			layout.getCompanyId(), locale);

		if (Validator.isNotNull(faviconTitle)) {
			return faviconTitle;
		}

		if (layout.getFaviconFileEntryId() > 0) {
			try {
				FileEntry fileEntry = _dlAppLocalService.getFileEntry(
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
			Layout masterLayout = _layoutLocalService.fetchLayout(
				layout.getMasterLayoutPlid());

			if (masterLayout != null) {
				faviconTitle = _getCETThemeFaviconTitle(
					_portal.getClassNameId(Layout.class),
					masterLayout.getPlid(), layout.getCompanyId(), locale);

				if ((masterLayout.getFaviconFileEntryId() > 0) ||
					Validator.isNotNull(faviconTitle)) {

					return LanguageUtil.get(locale, "favicon-from-master");
				}
			}
		}

		return getFaviconTitle(layout.getLayoutSet(), locale);
	}

	@Override
	public String getFaviconTitle(LayoutSet layoutSet, Locale locale) {
		String faviconTitle = _getCETThemeFaviconTitle(
			_portal.getClassNameId(LayoutSet.class), layoutSet.getLayoutSetId(),
			layoutSet.getCompanyId(), locale);

		if ((layoutSet.getFaviconFileEntryId() > 0) ||
			Validator.isNotNull(faviconTitle)) {

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

	@Override
	public String getFaviconURL(Layout layout) {
		String faviconURL = _getCETThemeFaviconURL(
			_portal.getClassNameId(Layout.class), layout.getPlid(),
			layout.getCompanyId());

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		faviconURL = layout.getFaviconURL();

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		if (layout.getMasterLayoutPlid() > 0) {
			Layout masterLayout = _layoutLocalService.fetchLayout(
				layout.getMasterLayoutPlid());

			if (masterLayout != null) {
				faviconURL = _getCETThemeFaviconURL(
					_portal.getClassNameId(Layout.class),
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

		return getFaviconURL(layout.getLayoutSet());
	}

	@Override
	public String getFaviconURL(LayoutSet layoutSet) {
		String faviconURL = _getCETThemeFaviconURL(
			_portal.getClassNameId(LayoutSet.class), layoutSet.getLayoutSetId(),
			layoutSet.getCompanyId());

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		faviconURL = layoutSet.getFaviconURL();

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		return null;
	}

	private CET _getCET(long classNameId, long classPK, long companyId) {
		ClientExtensionEntryRel clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.fetchClientExtensionEntryRel(
				classNameId, classPK,
				ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

		if (clientExtensionEntryRel == null) {
			return null;
		}

		return _cetManager.getCET(
			companyId, clientExtensionEntryRel.getCETExternalReferenceCode());
	}

	private String _getCETThemeFaviconTitle(
		long classNameId, long classPK, long companyId, Locale locale) {

		CET cet = _getCET(classNameId, classPK, companyId);

		if (cet == null) {
			return null;
		}

		CETThemeFavicon cetThemeFavicon = (CETThemeFavicon)cet;

		return cetThemeFavicon.getName(locale);
	}

	private String _getCETThemeFaviconURL(
		long classNameId, long classPK, long companyId) {

		CET cet = _getCET(classNameId, classPK, companyId);

		if (cet == null) {
			return null;
		}

		CETThemeFavicon cetThemeFavicon = (CETThemeFavicon)cet;

		return cetThemeFavicon.getURL();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FaviconManagerImpl.class);

	@Reference
	private CETManager _cetManager;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}