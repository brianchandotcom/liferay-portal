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

package com.liferay.portal.soy;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.SoyFileSet.Builder;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.BaseTemplateManager;
import com.liferay.portal.template.RestrictedTemplate;

import java.util.Map;

/**
 * @author Bruno Basto
 */
@DoPrivileged
public class SoyManager extends BaseTemplateManager {

	@Override
	public void destroy() {
		_builder = null;
	}

	@Override
	public void destroy(ClassLoader classLoader) {
		_builder = null;
	}

	@Override
	public String getName() {
		return TemplateConstants.LANG_TYPE_SOY;
	}

	@Override
	public void init() throws TemplateException {
		if (_builder != null) {
			return;
		}

		_builder = SoyFileSet.builder();
	}

	@Override
	protected Template doGetTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted,
		Map<String, Object> helperUtilities, boolean privileged) {

		Template template = new SoyTemplate(
			templateResource, errorTemplateResource, helperUtilities, _builder,
			templateContextHelper, privileged);

		if (restricted) {
			template = new RestrictedTemplate(
				template, templateContextHelper.getRestrictedVariables());
		}

		return template;
	}

	private Builder _builder;

}