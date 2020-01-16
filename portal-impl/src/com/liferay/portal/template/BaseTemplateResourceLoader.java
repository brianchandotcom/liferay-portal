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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceCache;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;

/**
 * @author Tina Tian
 */
public abstract class BaseTemplateResourceLoader
	implements TemplateResourceLoader {

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

		if (templateResource ==
				BaseTemplateResourceCache.DUMMY_TEMPLATE_RESOURCE) {

			return null;
		}

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

	protected void init(
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
			catch (TemplateException templateException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to parse template ", templateId,
							" with parser ", templateResourceParser),
						templateException);
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseTemplateResourceLoader.class);

	private String _name;
	private TemplateResourceCache _templateResourceCache;
	private Set<TemplateResourceParser> _templateResourceParsers;

}