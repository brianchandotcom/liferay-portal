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
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tamas Molnar
 */
@Component(service = CTDisplayRenderer.class)
public class ContactCTDisplayRenderer extends BaseCTDisplayRenderer<Contact> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, Contact contact)
		throws PortalException {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, null, UsersAdminPortletKeys.USERS_ADMIN, 0,
				0, PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/common/edit_address.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"className", contact.getClassName()
		).setParameter(
			"classPK", contact.getClassPK()
		).setParameter(
			"primaryKey", contact.getContactId()
		).buildString();
	}

	@Override
	public Class<Contact> getModelClass() {
		return Contact.class;
	}

	@Override
	public String getTitle(Locale locale, Contact contact) {
		return _language.format(
			locale, "x-for-x",
			new String[] {
				_language.get(
					locale, "model.resource." + Contact.class.getName()),
				contact.getFullName()
			});
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}