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

package com.liferay.layout.internal.events;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.CETThemeCSS;
import com.liferay.client.extension.type.CETThemeJS;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.layout.manager.FaviconManager;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "key=servlet.service.events.pre",
	service = LifecycleAction.class
)
public class ClientExtensionsServicePreAction extends Action {

	@Override
	public void run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout == null) {
			return;
		}

		themeDisplay.setFaviconURL(_faviconManager.getFaviconURL(layout));

		CETThemeCSS cetThemeCSS = _getCETThemeCSS(layout);

		if (cetThemeCSS != null) {
			themeDisplay.setClayCSSURL(cetThemeCSS.getClayURL());
			themeDisplay.setMainCSSURL(cetThemeCSS.getMainURL());
		}

		CETThemeJS cetThemeJS = _getCETThemeJS(layout);

		if (cetThemeJS != null) {
			themeDisplay.setMainJSURL(cetThemeJS.getURL());
		}
	}

	private CET _getCET(
		long classNameId, long classPK, long companyId, String type) {

		ClientExtensionEntryRel clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.fetchClientExtensionEntryRel(
				classNameId, classPK, type);

		if (clientExtensionEntryRel == null) {
			return null;
		}

		return _cetManager.getCET(
			companyId, clientExtensionEntryRel.getCETExternalReferenceCode());
	}

	private CETThemeCSS _getCETThemeCSS(Layout layout) {
		CET cet = _getCET(
			_portal.getClassNameId(Layout.class), layout.getPlid(),
			layout.getCompanyId(),
			ClientExtensionEntryConstants.TYPE_THEME_CSS);

		if (cet == null) {
			LayoutSet layoutSet = layout.getLayoutSet();

			cet = _getCET(
				_portal.getClassNameId(LayoutSet.class),
				layoutSet.getLayoutSetId(), layout.getCompanyId(),
				ClientExtensionEntryConstants.TYPE_THEME_CSS);
		}

		if (cet != null) {
			return (CETThemeCSS)cet;
		}

		return null;
	}

	private CETThemeJS _getCETThemeJS(Layout layout) {
		CET cet = _getCET(
			_portal.getClassNameId(Layout.class), layout.getPlid(),
			layout.getCompanyId(), ClientExtensionEntryConstants.TYPE_THEME_JS);

		if (cet == null) {
			LayoutSet layoutSet = layout.getLayoutSet();

			cet = _getCET(
				_portal.getClassNameId(LayoutSet.class),
				layoutSet.getLayoutSetId(), layout.getCompanyId(),
				ClientExtensionEntryConstants.TYPE_THEME_JS);
		}

		if (cet != null) {
			return (CETThemeJS)cet;
		}

		return null;
	}

	@Reference
	private CETManager _cetManager;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private FaviconManager _faviconManager;

	@Reference
	private Portal _portal;

}