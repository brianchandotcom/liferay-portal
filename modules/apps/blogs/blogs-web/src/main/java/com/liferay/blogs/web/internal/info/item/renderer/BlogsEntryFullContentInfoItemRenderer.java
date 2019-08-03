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

package com.liferay.blogs.web.internal.info.item.renderer;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoItemRenderer.class)
public class BlogsEntryFullContentInfoItemRenderer
	implements InfoItemRenderer<BlogsEntry> {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "full-content");
	}

	@Override
	public void render(
		BlogsEntry entry, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		httpServletRequest.setAttribute(WebKeys.BLOGS_ENTRY, entry);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/blogs/info/item/renderer/full_content.jsp");

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.blogs.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private ServletContext _servletContext;

}