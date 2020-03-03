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
import com.liferay.portal.kernel.service.ContactServiceUtil;
import com.liferay.portal.kernel.service.OrganizationServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class EditContactInformationDisplayContext {

	public EditContactInformationDisplayContext(
		String contactInfoTypeName, HttpServletRequest httpServletRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		_className = ParamUtil.getString(httpServletRequest, "className");
		_classPK = ParamUtil.getLong(httpServletRequest, "classPK");
		_primaryKey = ParamUtil.getLong(httpServletRequest, "primaryKey");
		_redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(httpServletRequest, "redirect"));

		if (_primaryKey > 0) {
			_sheetTitle = LanguageUtil.get(
				_httpServletRequest, "edit-" + contactInfoTypeName);
		}
		else {
			_sheetTitle = LanguageUtil.get(
				_httpServletRequest, "add-" + contactInfoTypeName);
		}
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
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
			portletDisplay.setURLBack(getRedirect());

			String portletTitle = StringPool.BLANK;

			if (_className.equals(Organization.class.getName())) {
				Organization organization =
					OrganizationServiceUtil.getOrganization(_classPK);

				portletTitle = LanguageUtil.format(
					_httpServletRequest, "edit-x", organization.getName(),
					false);
			}
			else if (_className.equals(Contact.class.getName())) {
				Contact contact = ContactServiceUtil.getContact(_classPK);

				portletTitle = LanguageUtil.format(
					_httpServletRequest, "edit-user-x", contact.getFullName(),
					false);
			}

			_renderResponse.setTitle(portletTitle);
		}
	}

	private final String _className;
	private final long _classPK;
	private final HttpServletRequest _httpServletRequest;
	private final long _primaryKey;
	private final String _redirect;
	private final RenderResponse _renderResponse;
	private final String _sheetTitle;

}