/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.service.taglib;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.client.extension.type.GlobalCSSCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = DynamicInclude.class)
public class ClientExtensionTopHeadDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PrintWriter printWriter = httpServletResponse.getWriter();

		for (ClientExtensionEntryRel clientExtensionEntryRel :
				_getClientExtensionEntryRels(themeDisplay.getLayout())) {

			GlobalCSSCET globalCSSCET = (GlobalCSSCET)_cetManager.getCET(
				clientExtensionEntryRel.getCompanyId(),
				clientExtensionEntryRel.getCETExternalReferenceCode());

			printWriter.print(
				"<link data-senna-track=\"temporary\" href=\"" +
					globalCSSCET.getURL() +
						"\" rel=\"stylesheet\" type=\"text/css\" />");
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	private List<ClientExtensionEntryRel> _getClientExtensionEntryRels(
		Layout layout) {

		LayoutSet layoutSet = layout.getLayoutSet();

		List<ClientExtensionEntryRel> clientExtensionEntryRels =
			new ArrayList<>(
				_clientExtensionEntryRelLocalService.
					getClientExtensionEntryRels(
						_portal.getClassNameId(LayoutSet.class),
						layoutSet.getLayoutSetId(),
						ClientExtensionEntryConstants.TYPE_GLOBAL_CSS,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		Layout masterLayout = _layoutLocalService.fetchLayout(
			layout.getMasterLayoutPlid());

		if (masterLayout != null) {
			clientExtensionEntryRels.addAll(
				_clientExtensionEntryRelLocalService.
					getClientExtensionEntryRels(
						_portal.getClassNameId(Layout.class),
						masterLayout.getPlid(),
						ClientExtensionEntryConstants.TYPE_GLOBAL_CSS,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS));
		}

		clientExtensionEntryRels.addAll(
			_clientExtensionEntryRelLocalService.getClientExtensionEntryRels(
				_portal.getClassNameId(Layout.class), layout.getPlid(),
				ClientExtensionEntryConstants.TYPE_GLOBAL_CSS,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		return clientExtensionEntryRels;
	}

	@Reference
	private CETManager _cetManager;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}