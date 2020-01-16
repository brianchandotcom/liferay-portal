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

package com.liferay.change.tracking.web.internal.portlet;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.context.ChangeListsHistoryDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Laszlo Pap
 */
@Component(
	configurationPid = "com.liferay.change.tracking.web.internal.configuration.CTConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=false",
		"com.liferay.portlet.css-class-wrapper=portlet-change-lists-history",
		"com.liferay.portlet.header-portlet-css=/change_lists_history/css/main.css",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.show-portlet-access-denied=false",
		"com.liferay.portlet.show-portlet-inactive=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=History",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/change_lists_history/view.jsp",
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS_HISTORY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class ChangeListsHistoryPortlet extends BaseChangeListsPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		checkRender(renderRequest);

		long ctCollectionId = ParamUtil.getLong(
			renderRequest, "ctCollectionId");

		try {
			if (ctCollectionId > 0) {
				renderRequest.setAttribute(
					CTWebKeys.CT_COLLECTION,
					_ctCollectionLocalService.getCTCollection(ctCollectionId));
			}
		}
		catch (PortalException portalException) {
			SessionErrors.add(renderRequest, portalException.getClass());
		}

		ChangeListsHistoryDisplayContext changeListsHistoryDisplayContext =
			new ChangeListsHistoryDisplayContext(
				_portal.getHttpServletRequest(renderRequest), renderResponse);

		renderRequest.setAttribute(
			CTWebKeys.CHANGE_LISTS_HISTORY_DISPLAY_CONTEXT,
			changeListsHistoryDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private Portal _portal;

}