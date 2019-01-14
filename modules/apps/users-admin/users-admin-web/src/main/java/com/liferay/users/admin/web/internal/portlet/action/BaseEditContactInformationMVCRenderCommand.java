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

package com.liferay.users.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.ContactService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import com.liferay.users.admin.web.internal.constants.UsersAdminWebKeys;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
public abstract class BaseEditContactInformationMVCRenderCommand
	implements MVCRenderCommand {

	protected void setRenderRequestAttributes(RenderRequest renderRequest)
		throws PortalException {

		String className = ParamUtil.getString(renderRequest, "className");

		PortletConfig portletConfig = (PortletConfig)renderRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		String portletName = portletConfig.getPortletName();

		if (!portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT)) {
			Long classPK = ParamUtil.getLong(renderRequest, "classPK");

			if (className.equals(Organization.class.getName())) {
				Organization organization = organizationService.getOrganization(
					classPK);

				renderRequest.setAttribute(
					UsersAdminWebKeys.PORTLET_TITLE,
					LanguageUtil.format(
						PortalUtil.getHttpServletRequest(renderRequest),
						"edit-x", organization.getName(), false));
			}
			else {
				Contact contact = contactService.getContact(classPK);

				renderRequest.setAttribute(
					UsersAdminWebKeys.PORTLET_TITLE,
					LanguageUtil.format(
						PortalUtil.getHttpServletRequest(renderRequest),
						"edit-user-x", contact.getFullName(), false));
			}
		}
	}

	@Reference
	protected ContactService contactService;

	@Reference
	protected OrganizationService organizationService;

}