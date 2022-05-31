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

package com.liferay.client.extension.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.client.extension.type.CETThemeFavicon;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.client.extension.ThemeCSSURLs;
import com.liferay.portal.kernel.client.extension.ThemeClientExtensions;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = ThemeClientExtensions.class)
public class ThemeClientExtensionsImpl implements ThemeClientExtensions {

	@Override
	public String getFaviconURL(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if ((layout == null) ||
			!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-153457"))) {

			return themeDisplay.getPathThemeImages() + StringPool.SLASH +
				PropsValues.THEME_SHORTCUT_ICON;
		}

		String faviconURL = _getCETThemeFaviconURL(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		faviconURL = _getFileEntryFaviconURL(layout.getFaviconFileEntryId());

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		LayoutSet layoutSet = themeDisplay.getLayoutSet();

		faviconURL = _getCETThemeFaviconURL(
			_portal.getClassNameId(LayoutSet.class),
			layoutSet.getLayoutSetId());

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		faviconURL = _getFileEntryFaviconURL(layoutSet.getFaviconFileEntryId());

		if (Validator.isNotNull(faviconURL)) {
			return faviconURL;
		}

		return themeDisplay.getPathThemeImages() + StringPool.SLASH +
			PropsValues.THEME_SHORTCUT_ICON;
	}

	@Override
	public ThemeCSSURLs getThemeCSSURLs(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new ThemeCSSURLsImpl(
			HtmlUtil.escapeAttribute(
				_portal.getStaticResourceURL(
					httpServletRequest,
					themeDisplay.getPathThemeCss() + "/main.css")),
			HtmlUtil.escapeAttribute(
				_portal.getStaticResourceURL(
					httpServletRequest,
					themeDisplay.getPathThemeCss() + "/clay.css")));
	}

	@Override
	public List<String> getThemeJSURLs(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return Collections.singletonList(
			HtmlUtil.escape(
				_portal.getStaticResourceURL(
					httpServletRequest,
					themeDisplay.getPathThemeJavaScript() + "/main.js")));
	}

	private String _getCETThemeFaviconURL(long classNameId, long classPK) {
		ClientExtensionEntryRel clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.fetchClientExtensionEntryRel(
				classNameId, classPK,
				ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

		if (clientExtensionEntryRel == null) {
			return null;
		}

		ClientExtensionEntry clientExtensionEntry =
			_clientExtensionEntryLocalService.fetchClientExtensionEntry(
				clientExtensionEntryRel.getClientExtensionEntryId());

		if (clientExtensionEntry == null) {
			return null;
		}

		CETThemeFavicon cetThemeFavicon = _cetFactory.themeFavicon(
			clientExtensionEntry);

		return cetThemeFavicon.getURL();
	}

	private String _getFileEntryFaviconURL(long faviconFileEntryId) {
		if (faviconFileEntryId > 0) {
			return null;
		}

		try {
			FileEntry fileEntry = _dlAppService.getFileEntry(
				faviconFileEntryId);

			return HtmlUtil.escape(
				StringBundler.concat(
					_portal.getPathContext(), "/documents/",
					fileEntry.getRepositoryId(), StringPool.SLASH,
					fileEntry.getFolderId(), StringPool.SLASH,
					URLCodec.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())),
					StringPool.SLASH, URLCodec.encodeURL(fileEntry.getUuid())));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeClientExtensionsImpl.class);

	@Reference
	private CETFactory _cetFactory;

	@Reference
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Portal _portal;

}