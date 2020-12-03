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

package com.liferay.document.library.external.video.internal.item.selector;

import com.liferay.document.library.external.video.internal.constants.DLExternalVideoWebKeys;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.VideoEmbeddableHTMLItemSelectorReturnType;
import com.liferay.item.selector.criteria.video.criterion.VideoItemSelectorCriterion;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrín Chaudhary
 */
@Component(service = ItemSelectorView.class)
public class DLExternalVideoVideoURLItemSelectorView
	implements ItemSelectorView<VideoItemSelectorCriterion> {

	@Override
	public Class<VideoItemSelectorCriterion> getItemSelectorCriterionClass() {
		return VideoItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "url");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			VideoItemSelectorCriterion videoItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/url.jsp");

		servletRequest.setAttribute(
			DLExternalVideoWebKeys.EVENT_NAME, itemSelectedEventName);

		servletRequest.setAttribute(
			DLExternalVideoWebKeys.RETURN_TYPE, ClassUtil.getClassName(
				getSupportedItemSelectorReturnTypes().get(0)));

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.external.video)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new VideoEmbeddableHTMLItemSelectorReturnType()));

	private ServletContext _servletContext;

}