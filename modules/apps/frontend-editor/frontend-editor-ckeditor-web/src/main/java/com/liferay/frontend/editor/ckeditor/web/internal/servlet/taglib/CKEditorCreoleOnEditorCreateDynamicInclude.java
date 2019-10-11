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

package com.liferay.frontend.editor.ckeditor.web.internal.servlet.taglib;

import com.liferay.frontend.editor.ckeditor.web.internal.constants.CKEditorConstants;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Chema Balsas
 */
@Component(immediate = true, service = DynamicInclude.class)
public class CKEditorCreoleOnEditorCreateDynamicInclude
	implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		Bundle bundle = _bundleContext.getBundle();

		URL entryURL = bundle.getEntry(
			"/META-INF/resources/ckeditor/extension" +
				"/creole_dialog_definition.js");

		StreamUtil.transfer(
			entryURL.openStream(), httpServletResponse.getOutputStream(),
			false);

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.println();

		String toolbarSet = (String)httpServletRequest.getAttribute(
			CKEditorConstants.ATTRIBUTE_NAMESPACE + ":toolbarSet");

		if (toolbarSet.equals("creole")) {
			entryURL = bundle.getEntry(
				"/META-INF/resources/ckeditor/extension/creole_dialog_show.js");

			StreamUtil.transfer(
				entryURL.openStream(), httpServletResponse.getOutputStream(),
				false);

			printWriter.println();
		}
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register(
			"com.liferay.frontend.editor.ckeditor.web#ckeditor_creole#" +
				"onEditorCreate");
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

}