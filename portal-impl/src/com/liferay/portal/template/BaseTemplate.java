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

package com.liferay.portal.template;

import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceCache;

import java.io.Writer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tina Tian
 */
public abstract class BaseTemplate implements Template {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #BaseTemplate(TemplateResource, Map, TemplateContextHelper,
	 *             boolean)}
	 */
	@Deprecated
	public BaseTemplate(
		TemplateResource templateResource, Map<String, Object> context,
		TemplateContextHelper templateContextHelper) {

		this(templateResource, context, templateContextHelper, false);
	}

	public BaseTemplate(
		TemplateResource templateResource, Map<String, Object> context,
		TemplateContextHelper templateContextHelper, boolean restricted) {

		if (templateResource == null) {
			throw new IllegalArgumentException("Template resource is null");
		}

		if (templateContextHelper == null) {
			throw new IllegalArgumentException(
				"Template context helper is null");
		}

		_templateResource = templateResource;

		this.context = new HashMap<>();

		if (context != null) {
			for (Map.Entry<String, Object> entry : context.entrySet()) {
				put(entry.getKey(), entry.getValue());
			}
		}

		_templateContextHelper = templateContextHelper;
		_restricted = restricted;
	}

	@Override
	public void clear() {
		context.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return context.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return context.containsValue(value);
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return context.entrySet();
	}

	@Override
	public Object get(Object key) {
		if (key == null) {
			return null;
		}

		return context.get(key);
	}

	@Override
	public boolean isEmpty() {
		return context.isEmpty();
	}

	public boolean isRestricted() {
		return _restricted;
	}

	@Override
	public Set<String> keySet() {
		return context.keySet();
	}

	@Override
	public void prepare(HttpServletRequest httpServletRequest) {
		_templateContextHelper.prepare(this, httpServletRequest);
	}

	@Override
	public void prepareTaglib(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {
	}

	@Override
	public void processTemplate(Writer writer) throws TemplateException {
		try {
			processTemplate(_templateResource, writer);
		}
		catch (Exception exception) {
			throw new TemplateException(
				"Unable to process template " +
					_templateResource.getTemplateId(),
				exception);
		}
	}

	@Override
	public void processTemplate(
			Writer writer,
			Supplier<TemplateResource> errorTemplateResourceSupplier)
		throws TemplateException {

		if (errorTemplateResourceSupplier == null) {
			processTemplate(writer);

			return;
		}

		Writer oldWriter = (Writer)get(TemplateConstants.WRITER);

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			put(TemplateConstants.WRITER, unsyncStringWriter);

			processTemplate(_templateResource, unsyncStringWriter);

			StringBundler sb = unsyncStringWriter.getStringBundler();

			sb.writeTo(writer);
		}
		catch (Exception exception) {
			TemplateResource errorTemplateResource =
				errorTemplateResourceSupplier.get();

			if (errorTemplateResource == null) {
				throw new TemplateException(
					"Unable to process template " +
						_templateResource.getTemplateId(),
					exception);
			}

			put(TemplateConstants.WRITER, writer);

			handleException(
				_templateResource, errorTemplateResource, exception, writer);
		}
		finally {
			put(TemplateConstants.WRITER, oldWriter);
		}
	}

	@Override
	public Object put(String key, Object value) {
		if ((key == null) || (value == null)) {
			return null;
		}

		if (_restricted) {
			Set<String> restrictedVariables =
				_templateContextHelper.getRestrictedVariables();

			if (restrictedVariables.contains(key)) {
				return null;
			}
		}

		if (value instanceof Class) {
			return putClass(key, (Class<?>)value);
		}

		return context.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		context.putAll(map);
	}

	@Override
	public Object remove(Object key) {
		return context.remove(key);
	}

	@Override
	public int size() {
		return context.size();
	}

	@Override
	public Collection<Object> values() {
		return context.values();
	}

	protected void cacheTemplateResource(
		TemplateResourceCache templateResourceCache,
		TemplateResource templateResource) {

		TemplateResource cachedTemplateResource =
			templateResourceCache.getTemplateResource(
				templateResource.getTemplateId());

		if ((cachedTemplateResource == null) ||
			!templateResource.equals(cachedTemplateResource)) {

			templateResourceCache.put(
				templateResource.getTemplateId(), templateResource);
		}
	}

	protected String getTemplateResourceUUID(
		TemplateResource templateResource) {

		return StringBundler.concat(
			TemplateConstants.TEMPLATE_RESOURCE_UUID_PREFIX, StringPool.POUND,
			templateResource.getTemplateId());
	}

	protected abstract void handleException(
			TemplateResource templateResource,
			TemplateResource errorTemplateResource, Exception exception,
			Writer writer)
		throws TemplateException;

	protected abstract void processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception;

	protected Object putClass(String key, Class<?> clazz) {
		return context.put(key, clazz);
	}

	protected Map<String, Object> context;

	private final boolean _restricted;
	private final TemplateContextHelper _templateContextHelper;
	private final TemplateResource _templateResource;

}