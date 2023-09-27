/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.components.sample.web.internal.portlet;

import com.liferay.frontend.js.components.sample.web.internal.constants.FrontendJSComponentsSampleWebKeys;
import com.liferay.frontend.js.components.sample.web.internal.constants.FrontendJSComponentsSampleWebPortletKeys;
import com.liferay.frontend.js.components.sample.web.internal.display.context.TranslationManagerDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Allegrini
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"javax.portlet.display-name=JS Components Sample",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + FrontendJSComponentsSampleWebPortletKeys.JS_COMPONENTS_SAMPLE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class FrontendJSComponentsSampleWebPortlet extends MVCPortlet {

	@Override
	public void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			FrontendJSComponentsSampleWebKeys.
				TRANSLATION_MANAGER_DISPLAY_CONTEXT,
			new TranslationManagerDisplayContext());

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference
	private Portal _portal;

}