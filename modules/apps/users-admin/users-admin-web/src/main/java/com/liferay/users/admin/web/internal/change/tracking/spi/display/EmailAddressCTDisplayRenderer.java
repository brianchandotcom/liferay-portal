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

package com.liferay.users.admin.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tamas Molnar
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class EmailAddressCTDisplayRenderer
	extends BaseCTDisplayRenderer<EmailAddress> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, EmailAddress emailAddress)
		throws PortalException {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, null, UsersAdminPortletKeys.USERS_ADMIN, 0,
				0, PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/common/edit_email_address.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"className", emailAddress.getClassName()
		).setParameter(
			"classPK", emailAddress.getClassPK()
		).setParameter(
			"primaryKey", emailAddress.getEmailAddressId()
		).buildString();
	}

	@Override
	public Class<EmailAddress> getModelClass() {
		return EmailAddress.class;
	}

	@Override
	public String getTitle(Locale locale, EmailAddress emailAddress) {
		return emailAddress.getAddress();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<EmailAddress> displayBuilder)
		throws PortalException {

		EmailAddress emailAddress = displayBuilder.getModel();

		displayBuilder.display(
			"name", emailAddress.getAddress()
		).display(
			"created-by",
			() -> {
				String userName = emailAddress.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", emailAddress.getCreateDate()
		).display(
			"last-modified", emailAddress.getModifiedDate()
		).display(
			"primary", emailAddress.isPrimary()
		).display(
			"type",
			() -> {
				ListType listType = emailAddress.getListType();

				if (listType != null) {
					return _language.get(
						displayBuilder.getLocale(), listType.getName());
				}

				return null;
			}
		).display(
			"address", emailAddress.getAddress()
		);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}