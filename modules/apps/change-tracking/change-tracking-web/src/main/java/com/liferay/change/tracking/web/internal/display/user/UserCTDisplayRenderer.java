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

package com.liferay.change.tracking.web.internal.display.user;

import com.liferay.change.tracking.display.CTDisplayRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Writer;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class UserCTDisplayRenderer implements CTDisplayRenderer<User> {

	@Override
	public String getEditURL(HttpServletRequest httpServletRequest, User user) {
		return null;
	}

	@Override
	public Class<User> getModelClass() {
		return User.class;
	}

	@Override
	public String getTitle(Locale locale, User user) {
		String title = user.getFullName();

		if (Validator.isNotNull(title)) {
			return title;
		}

		return user.getScreenName();
	}

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return _language.get(
			resourceBundle,
			"model.resource.com.liferay.portal.kernel.model.User");
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, User user)
		throws Exception {

		Writer writer = httpServletResponse.getWriter();

		writer.write("<p><b>");
		writer.write(_language.get(httpServletRequest, "user-id"));
		writer.write("</b>: ");
		writer.write(String.valueOf(user.getUserId()));
		writer.write("</p><p><b>");
		writer.write(_language.get(httpServletRequest, "name"));
		writer.write("</b>: ");
		writer.write(HtmlUtil.escape(user.getFullName()));
		writer.write("</p><p><b>");
		writer.write(_language.get(httpServletRequest, "screen-name"));
		writer.write("</b>: ");
		writer.write(HtmlUtil.escape(user.getScreenName()));
		writer.write("</p><p><b>");
		writer.write(_language.get(httpServletRequest, "email-address"));
		writer.write("</b>: ");
		writer.write(user.getEmailAddress());
		writer.write("</p>");
	}

	@Reference
	private Language _language;

}