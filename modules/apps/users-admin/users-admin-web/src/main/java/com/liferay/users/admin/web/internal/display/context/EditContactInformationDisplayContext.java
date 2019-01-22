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
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;
import com.liferay.users.admin.web.internal.util.UsersAdminPortletURLUtil;

import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class EditContactInformationDisplayContext {

	public EditContactInformationDisplayContext(
		String contactInfoTypeName, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderResponse = renderResponse;
		_request = request;

		_className = ParamUtil.getString(request, "className");
		_classPK = ParamUtil.getLong(request, "classPK");
		_contextOrganizationId = ParamUtil.getLong(
			request, "contextOrganizationId");
		_primaryKey = ParamUtil.getLong(request, "primaryKey", 0L);
		_redirect = ParamUtil.getString(request, "redirect");

		if (_primaryKey > 0) {
			_sheetTitle = LanguageUtil.get(
				_request, "edit-" + contactInfoTypeName);
		}
		else {
			_sheetTitle = LanguageUtil.get(
				_request, "add-" + contactInfoTypeName);
		}
	}

	public String getBackURL() {
		if (_contextOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

			RenderURL renderURL = _renderResponse.createRenderURL();

			return renderURL.toString();
		}

		return UsersAdminPortletURLUtil.createOrganizationViewTreeURL(
			_contextOrganizationId, _renderResponse);
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public long getContextOrganizationId() {
		return _contextOrganizationId;
	}

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public String getRedirect() {
		return _redirect;
	}

	public String getSheetTitle() {
		return _sheetTitle;
	}

	public void setPortletDisplay(
			PortletDisplay portletDisplay, String portletName)
		throws PortalException {

		if (!portletName.equals(UsersAdminPortletKeys.MY_ACCOUNT)) {
			portletDisplay.setShowBackIcon(true);
			portletDisplay.setURLBack(getBackURL());

			String portletTitle = StringPool.BLANK;

			if (_className.equals(Organization.class.getName())) {
				Organization organization =
					OrganizationServiceUtil.getOrganization(_classPK);

				portletTitle = LanguageUtil.format(
					_request, "edit-x", organization.getName(), false);
			}
			else if (_className.equals(Contact.class.getName())) {
				Contact contact = ContactServiceUtil.getContact(_classPK);

				portletTitle = LanguageUtil.format(
					_request, "edit-user-x", contact.getFullName(), false);
			}

			_renderResponse.setTitle(portletTitle);
		}
	}

	private final String _className;
	private final long _classPK;
	private final long _contextOrganizationId;
	private final long _primaryKey;
	private final String _redirect;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final String _sheetTitle;

}