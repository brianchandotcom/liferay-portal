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

package com.liferay.fragment.internal.renderer;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(service = FragmentRenderer.class)
public class DropZoneFragmentComponentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "drop-zone-component";
	}

	@Override
	public String getKey() {
		return "drop-zone-component";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "Drop zone component");
	}

	@Override
	public int getType() {
		return FragmentConstants.TYPE_COMPONENT;
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);

		if (true ||
			Objects.equals(
				layout.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_PAGE)) {

			return true;
		}

		return false;
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		PrintWriter writer = httpServletResponse.getWriter();

		writer.write(
			"<div style=\"min-height: 400px;\" data-drop-zone> </div>");
	}

}