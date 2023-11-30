/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.context.BaseMVCRenderCommand;
import com.liferay.sharing.web.internal.display.context.ViewSharedAssetsDisplayContext;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=" + SharingPortletKeys.SHARED_ASSETS,
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class ViewSharedAssetsMVCRenderCommand
	extends BaseMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			ViewSharedAssetsDisplayContext.class.getName(),
			getViewSharedAssetsDisplayContext(renderRequest, renderResponse));

		return "/shared_assets/view.jsp";
	}

}