/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceCache;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.BundleContext;

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
		_serviceTrackerList.close();
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
		BundleContext bundleContext, String name,
		TemplateResourceCache templateResourceCache) {

		if (Validator.isNull(name)) {
			throw new IllegalArgumentException(
				"Template resource loader name is null");
		}

		_name = name;

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, TemplateResourceParser.class,
			"(lang.type=" + _name + ")");

		_templateResourceCache = templateResourceCache;
	}

	private TemplateResource _loadFromParser(String templateId) {
		for (TemplateResourceParser templateResourceParser :
				_serviceTrackerList) {

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
	private ServiceTrackerList<TemplateResourceParser> _serviceTrackerList;
	private TemplateResourceCache _templateResourceCache;

}