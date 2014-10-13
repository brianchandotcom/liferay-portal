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

package com.liferay.kernel.servlet.taglib;

import com.liferay.kernel.servlet.taglib.IncludeTagExtension.ExtensionPoint;
import com.liferay.portal.util.TagIdResolver;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.EnumSet;
import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class IncludeTagExtensionUtil {

	public static List<IncludeTagExtension> getIncludeTagExtension(
		String extensionId) {

		return _instance._viewExtensions.getService(extensionId);
	}

	public static TagIdResolver getTagIdResolver(String target) {
		return _instance._tagResolvers.getService(target);
	}

	private IncludeTagExtensionUtil() {
		_tagResolvers.open();

		_viewExtensions.open();
	}

	private static IncludeTagExtensionUtil _instance =
		new IncludeTagExtensionUtil();

	private ServiceTrackerMap<String, TagIdResolver>
		_tagResolvers = ServiceTrackerCollections.singleValueMap(
			TagIdResolver.class, "target");

	private ServiceTrackerMap<String, List<IncludeTagExtension>>
		_viewExtensions =
			ServiceTrackerCollections.multiValueMap(
				IncludeTagExtension.class, null,
				new ServiceReferenceMapper<String, IncludeTagExtension>() {

					@Override
					public void map(
						ServiceReference<IncludeTagExtension> serviceReference,
						Emitter<String> emitter) {

						Registry registry = RegistryUtil.getRegistry();

						IncludeTagExtension extension =
							registry.getService(serviceReference);

						try {
							EnumSet<ExtensionPoint> extensionPoints =
								extension.getExtensionPoints();

							for (ExtensionPoint extensionPoint :
									extensionPoints) {

								String prefix = extension.getTagKey();

								String key = prefix + ":" + extensionPoint;

								emitter.emit(key);
							}
						}
						finally {
							registry.ungetService(serviceReference);
						}
					}
				});

}