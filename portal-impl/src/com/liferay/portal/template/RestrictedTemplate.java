/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;

import java.io.Writer;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tina Tian
 */
public class RestrictedTemplate implements Template {

	public RestrictedTemplate(
		Template template, Set<String> restrictedVariables) {

		_template = template;
		_restrictedVariables = restrictedVariables;
	}

	@Override
	public Object get(String key) {
		return _template.get(key);
	}

	@Override
	public String[] getKeys() {
		return _template.getKeys();
	}

	@Override
	public void prepare(HttpServletRequest request) {
		_template.prepare(request);
	}

	@Override
	public void processTemplate(Writer writer) throws TemplateException {
		_template.processTemplate(writer);
	}

	@Override
	public void put(String key, Object value) {
		if (_restrictedVariables.contains(key)) {
			return;
		}

		_template.put(key, value);
	}

	private Set<String> _restrictedVariables;
	private Template _template;

}