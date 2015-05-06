/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.wiki.engine.input.editor.common.util;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true)
public class WikiEngineInputEditorCommonComponentProvider {

	public static WikiEngineInputEditorCommonComponentProvider
		getWikiEngineInputEditorCommonComponentProvider() {

		return _wikiEngineInputEditorCommonComponentProvider;
	}

	@Activate
	public void activate() {
		_wikiEngineInputEditorCommonComponentProvider = this;
	}

	@Deactivate
	public void deactivate() {
		_wikiEngineInputEditorCommonComponentProvider = null;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(
		target =
			"(osgi.web.symbolicname=" +
				"com.liferay.wiki.engine.input.editor.common)"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static WikiEngineInputEditorCommonComponentProvider
		_wikiEngineInputEditorCommonComponentProvider;

	private ServletContext _servletContext;

}