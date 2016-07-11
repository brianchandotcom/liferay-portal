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

package com.liferay.frontend.image.editor.web.internal.portlet.tracker;

import com.liferay.frontend.image.editor.capability.ImageEditorCapability;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Bruno Basto
 */
@Component(immediate = true, service = ImageEditorCapabilityTracker.class)
public class ImageEditorCapabilityTracker {

	public Set<String> getImageEditorCapabilitiesRequirements() {
		return _imageEditorCapabilitiesRequirements;
	}

	public List<ImageEditorCapabilityDescriptor>
		getImageEditorCapabilityDescriptors(String imageEditorCapabilityType) {

		return _serviceTrackerMap.getService(imageEditorCapabilityType);
	}

	public static class ImageEditorCapabilityDescriptor {

		public ImageEditorCapabilityDescriptor(
			ImageEditorCapability imageEditorCapability,
			Map<String, Object> properties) {

			_imageEditorCapability = imageEditorCapability;
			_properties = properties;
		}

		public ImageEditorCapability getImageEditorCapability() {
			return _imageEditorCapability;
		}

		public Map<String, Object> getProperties() {
			return _properties;
		}

		private final ImageEditorCapability _imageEditorCapability;
		private final Map<String, Object> _properties;

	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ImageEditorCapability.class,
			"(com.liferay.frontend.image.editor.capability.type=*)",
			new PropertyServiceReferenceMapper<String, ImageEditorCapability>(
				"com.liferay.frontend.image.editor.capability.type"),
			new ImageEditorCapabilityDescriptorServiceTrackerCustomizer(),
			new PropertyServiceReferenceComparator<ImageEditorCapability>(
				"service.ranking"),
			new ImageEditorCapabilityServiceTrackerMapListener());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getJavaScriptFileName(String fileName) {
		String shortFileName = FileUtil.getShortFileName(fileName);

		return StringUtil.replace(shortFileName, ".js", StringPool.BLANK);
	}

	private Set<String> _rebuildImageEditorCapabilitiesRequirements(
		ServiceTrackerMap<String, List<ImageEditorCapabilityDescriptor>>
			serviceTrackerMap) {

		Set<String> requiredModules = new HashSet<>();

		for (String key : serviceTrackerMap.keySet()) {
			List<ImageEditorCapabilityDescriptor>
				imageEditorCapabilityDescriptors = serviceTrackerMap.getService(
					key);

			for (ImageEditorCapabilityDescriptor
					imageEditorCapabilityDescriptor :
						imageEditorCapabilityDescriptors) {

				try {
					ImageEditorCapability imageEditorCapability =
						imageEditorCapabilityDescriptor.
							getImageEditorCapability();

					String moduleName = imageEditorCapability.getModuleName();

					List<URL> resourceURLs =
						imageEditorCapability.getResourceURLs();

					for (URL resourceURL : resourceURLs) {
						String fileName = _getJavaScriptFileName(
							resourceURL.getFile());

						requiredModules.add(
							moduleName.concat(StringPool.SLASH).concat(
								fileName));
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}

		return requiredModules;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageEditorCapabilityTracker.class);

	private BundleContext _bundleContext;
	private volatile Set<String> _imageEditorCapabilitiesRequirements =
		Collections.emptySet();
	private ServiceTrackerMap<String, List<ImageEditorCapabilityDescriptor>>
		_serviceTrackerMap;

	private class ImageEditorCapabilityDescriptorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ImageEditorCapability, ImageEditorCapabilityDescriptor> {

		@Override
		public ImageEditorCapabilityDescriptor addingService(
			ServiceReference<ImageEditorCapability> serviceReference) {

			ImageEditorCapability imageEditorCapability =
				_bundleContext.getService(serviceReference);

			Map<String, Object> properties = new HashMap<>();

			String[] propertyKeys = serviceReference.getPropertyKeys();

			for (String propertyKey : propertyKeys) {
				properties.put(
					propertyKey, serviceReference.getProperty(propertyKey));
			}

			try {
				return new ImageEditorCapabilityDescriptor(
					imageEditorCapability, properties);
			}
			catch (Exception e) {
				_bundleContext.ungetService(serviceReference);

				return null;
			}
		}

		@Override
		public void modifiedService(
			ServiceReference<ImageEditorCapability> serviceReference,
			ImageEditorCapabilityDescriptor imageEditorCapabilityDescriptor) {

			removedService(serviceReference, imageEditorCapabilityDescriptor);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<ImageEditorCapability> serviceReference,
			ImageEditorCapabilityDescriptor imageEditorCapabilityDescriptor) {

			_bundleContext.ungetService(serviceReference);
		}

	}

	private class ImageEditorCapabilityServiceTrackerMapListener implements
		ServiceTrackerMapListener
			<String, ImageEditorCapabilityDescriptor,
				List<ImageEditorCapabilityDescriptor>> {

		@Override
		public synchronized void keyEmitted(
			ServiceTrackerMap<String, List<ImageEditorCapabilityDescriptor>>
				serviceTrackerMap,
			String key,
			ImageEditorCapabilityDescriptor imageEditorCapabilityDescriptor,
			List<ImageEditorCapabilityDescriptor>
				imageEditorCapabilityDescriptors) {

			_imageEditorCapabilitiesRequirements =
				_rebuildImageEditorCapabilitiesRequirements(serviceTrackerMap);
		}

		@Override
		public synchronized void keyRemoved(
			ServiceTrackerMap<String, List<ImageEditorCapabilityDescriptor>>
				serviceTrackerMap,
			String key,
			ImageEditorCapabilityDescriptor imageEditorCapabilityDescriptor,
			List<ImageEditorCapabilityDescriptor>
				imageEditorCapabilityDescriptors) {

			_imageEditorCapabilitiesRequirements =
				_rebuildImageEditorCapabilitiesRequirements(serviceTrackerMap);
		}

	}

}