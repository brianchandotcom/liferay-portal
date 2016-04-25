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

package com.liferay.image.editor.web;

import com.liferay.image.editor.api.ImageEditorFeature;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(immediate = true, service = ImageEditorFeatureTracker.class)
public class ImageEditorFeatureTracker {

	public ImageEditorFeature getFeature(String featureName) {
		return _featuresServicesMap.getService(featureName);
	}

	public Map<String, Map<String, Object>> getFeaturesProperties() {
		return _featuresPropertiesMap;
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
		String filter = "(com.liferay.image.editor.tool.type=tool)";

		_featuresPropertiesMap = new HashMap<>();

		_featuresServicesMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ImageEditorFeature.class, filter,
			new ServiceReferenceMapper<String, ImageEditorFeature>() {

				@Override
				public void map(
					ServiceReference<ImageEditorFeature> serviceReference,
					Emitter<String> emitter) {

					String[] propertyKeys = serviceReference.getPropertyKeys();

					Map<String, Object> properties = new HashMap<>();

					for (String propertyKey : propertyKeys) {
						properties.put(
							propertyKey,
							serviceReference.getProperty(propertyKey));
					}

					String featureName = GetterUtil.getString(
						serviceReference.getProperty(
							"com.liferay.image.editor.tool.name"));

					_featuresPropertiesMap.put(featureName, properties);

					emitter.emit(featureName);
				}

			});
	}

	protected void collectFeaturesRequirements(Set<String> requiredModules) {
		for (String key : _featuresServicesMap.keySet()) {
			ImageEditorFeature feature = _featuresServicesMap.getService(key);

			Bundle toolBundle = FrameworkUtil.getBundle(feature.getClass());

			try {
				String moduleName = _getModuleName(toolBundle);

				List<URL> resourceURLs = _getResourceURLs(toolBundle);

				for (URL resourceURL : resourceURLs) {
					String fullFileName = resourceURL.getFile();

					String fileName = _getJavaScriptFileName(fullFileName);

					requiredModules.add(
						moduleName.concat(StringPool.SLASH).concat(fileName));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String _getJavaScriptFileName(String fileName) {
		String shortFileName = FileUtil.getShortFileName(fileName);

		return StringUtil.replace(shortFileName, ".js", StringPool.BLANK);
	}

	private String _getModuleName(Bundle bundle) throws Exception {
		URL url = bundle.getEntry("package.json");

		if (url == null) {
			return null;
		}

		String json = StringUtil.read(url.openStream());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		String moduleName = jsonObject.getString("name");

		if (Validator.isNull(moduleName)) {
			return null;
		}

		return moduleName;
	}

	private List<URL> _getResourceURLs(Bundle bundle) {
		List<URL> allURs = new ArrayList<>();

		Enumeration<URL> urls = bundle.findEntries(
			"META-INF/resources", _ES_JS_FILE_EXTENSION, true);

		if (urls != null) {
			allURs.addAll(Collections.list(urls));
		}

		return allURs;
	}

	private static final String _ES_JS_FILE_EXTENSION = "*.es.js";

	private Map<String, Map<String, Object>> _featuresPropertiesMap;
	private ServiceTrackerMap<String, ImageEditorFeature> _featuresServicesMap;

}