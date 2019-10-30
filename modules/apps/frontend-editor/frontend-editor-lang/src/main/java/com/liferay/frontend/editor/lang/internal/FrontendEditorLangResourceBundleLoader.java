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

package com.liferay.frontend.editor.lang.internal;

import com.liferay.portal.kernel.language.UTF8Control;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true,
	property = "bundle.symbolic.name=com.liferay.frontend.editor.lang",
	service = ResourceBundleLoader.class
)
public class FrontendEditorLangResourceBundleLoader
	implements ResourceBundleLoader {

	@Override
	public ResourceBundle loadResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		return ResourceBundle.getBundle(
			"content.Language", locale, clazz.getClassLoader(),
			UTF8Control.INSTANCE);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

}