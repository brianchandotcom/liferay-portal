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

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceCache;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;

/**
 * @author Tina Tian
 */
public class DefaultTemplateResourceLoader implements TemplateResourceLoader {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #DefaultTemplateResourceLoader(
	 * 				String, Set, long, MultiVMPool, SingleVMPool)}
	 */
	@Deprecated
	public DefaultTemplateResourceLoader(
		String name, long modificationCheckInterval, MultiVMPool multiVMPool,
		SingleVMPool singleVMPool) {

		throw new UnsupportedOperationException(
			"This constructor is deprecated and replaced by " +
				"#DefaultTemplateResourceLoader(String, Set, long, " +
					"MultiVMPool, SingleVMPool)");
	}

	public DefaultTemplateResourceLoader(
		String name, Set<TemplateResourceParser> templateResourceParsers,
		TemplateResourceCache templateResourceCache) {

		if (Validator.isNull(name)) {
			throw new IllegalArgumentException(
				"Template resource loader name is null");
		}

		_name = name;

		_templateResourceParsers = templateResourceParsers;
		_templateResourceCache = templateResourceCache;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	public DefaultTemplateResourceLoader(
		String name, String[] templateResourceParserClassNames,
		long modificationCheckInterval, MultiVMPool multiVMPool,
		SingleVMPool singleVMPool) {

		this(name, modificationCheckInterval, multiVMPool, singleVMPool);
	}

	@Override
	public void clearCache() {
		_templateResourceCache.clear();
	}

	@Override
	public void clearCache(String templateId) {
		_templateResourceCache.remove(templateId);
	}

	@Override
	public void destroy() {
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public TemplateResource getTemplateResource(String templateId) {
		if (!_templateResourceCache.isEnabled()) {
			return _loadFromParser(templateId);
		}

		TemplateResource templateResource =
			_templateResourceCache.getTemplateResource(templateId);

		if (templateResource == null) {
			templateResource = _loadFromParser(templateId);

			_templateResourceCache.put(templateId, templateResource);
		}

		return templateResource;
	}

	@Override
	public boolean hasTemplateResource(String templateId) {
		TemplateResource templateResource = getTemplateResource(templateId);

		if (templateResource != null) {
			return true;
		}

		return false;
	}

	private TemplateResource _loadFromParser(String templateId) {
		for (TemplateResourceParser templateResourceParser :
				_templateResourceParsers) {

			try {
				if (!templateResourceParser.isTemplateResourceValid(
						templateId, getName())) {

					continue;
				}

				TemplateResource templateResource =
					templateResourceParser.getTemplateResource(templateId);

				if (templateResource != null) {
					return templateResource;
				}
			}
			catch (TemplateException te) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to parse template ", templateId,
							" with parser ",
							String.valueOf(templateResourceParser)),
						te);
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultTemplateResourceLoader.class);

	private final String _name;
	private final TemplateResourceCache _templateResourceCache;
	private final Set<TemplateResourceParser> _templateResourceParsers;

}