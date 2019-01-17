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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.ContactServiceUtil;
import com.liferay.portal.kernel.service.OrganizationServiceUtil;
import com.liferay.users.admin.web.internal.util.UsersAdminPortletURLUtil;

import javax.portlet.PortletResponse;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class EditContactInformationDisplayContext {

	public EditContactInformationDisplayContext(
		String className, long classPK, long parentOrganizationId,
		PortletResponse portletResponse, HttpServletRequest request) {

		_className = className;
		_classPK = classPK;
		_parentOrganizationId = parentOrganizationId;
		_portletResponse = portletResponse;
		_request = request;
	}

	public String getBackURL() {
		if (_parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

			RenderResponse renderResponse = (RenderResponse)_portletResponse;

			RenderURL renderURL = renderResponse.createRenderURL();

			return renderURL.toString();
		}

		return UsersAdminPortletURLUtil.createOrganizationViewTreeURL(
			_parentOrganizationId, _portletResponse);
	}

	public String getPortletTitle() throws PortalException {
		if (_className.equals(Organization.class.getName())) {
			Organization organization = OrganizationServiceUtil.getOrganization(
				_classPK);

			return LanguageUtil.format(
				_request, "edit-x", organization.getName(), false);
		}
		else if (_className.equals(Contact.class.getName())) {
			Contact contact = ContactServiceUtil.getContact(_classPK);

			return LanguageUtil.format(
				_request, "edit-user-x", contact.getFullName(), false);
		}

		return StringPool.BLANK;
	}

	private final String _className;
	private final long _classPK;
	private final long _parentOrganizationId;
	private final PortletResponse _portletResponse;
	private final HttpServletRequest _request;

}