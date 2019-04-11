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

package com.liferay.portal.kernel.template.bundle.templatemanagerutil;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateResource;

import java.io.Writer;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Philip Jones
 */
public class TestTemplate implements Template {

	@Override
	public void clear() {
	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public Set<Map.Entry<String, Object>> entrySet() {
		return null;
	}

	@Override
	public Object get(Object key) {
		return null;
	}

	@Override
	public Object get(String key) {
		return null;
	}

	@Override
	public String[] getKeys() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public void prepare(HttpServletRequest httpServletRequest) {
	}

	@Override
	public void processTemplate(Writer writer) {
	}

	@Override
	public void processTemplate(
		Writer writer,
		Supplier<TemplateResource> errorTemplateResourceSupplier) {
	}

	@Override
	public Object put(String key, Object value) {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
	}

	@Override
	public Object remove(Object key) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Collection<Object> values() {
		return null;
	}

}