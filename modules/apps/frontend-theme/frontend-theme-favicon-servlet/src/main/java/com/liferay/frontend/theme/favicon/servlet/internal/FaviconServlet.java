/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.theme.favicon.servlet.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.ThemeFaviconCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	property = {
		"osgi.http.whiteboard.context.path=/favicon",
		"osgi.http.whiteboard.servlet.name=com.liferay.frontend.theme.favicon.servlet.internal.FaviconServlet",
		"osgi.http.whiteboard.servlet.pattern=/favicon/*"
	},
	service = {FaviconServlet.class, Servlet.class}
)
public class FaviconServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			httpServletResponse.sendRedirect(themeDisplay.getFaviconURL());

			return;
		}

		LayoutSet layoutSet = (LayoutSet)httpServletRequest.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if (layoutSet == null) {
			return;
		}

		String faviconURL = _getFaviconURL(layoutSet);

		if (Validator.isNotNull(faviconURL)) {
			httpServletResponse.sendRedirect(faviconURL);
		}

		Theme theme = layoutSet.getTheme();

		httpServletResponse.sendRedirect(
			theme.getContextPath() + theme.getImagesPath() + "/favicon.ico");
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

	private String _getFaviconURL(LayoutSet layoutSet) {
		String faviconURL = _getThemeFaviconCETURL(
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

	private String _getThemeFaviconCETURL(
		long classNameId, long classPK, long companyId) {

		CET cet = _getCET(classNameId, classPK, companyId);

		if (cet == null) {
			return null;
		}

		ThemeFaviconCET themeFaviconCET = (ThemeFaviconCET)cet;

		return themeFaviconCET.getURL();
	}

	private static final long serialVersionUID = 1L;

	@Reference
	private CETManager _cetManager;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private Portal _portal;

}