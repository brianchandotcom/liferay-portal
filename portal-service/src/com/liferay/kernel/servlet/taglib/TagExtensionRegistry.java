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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.TagKeyResolver;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class TagExtensionRegistry {

	public static List<TagExtension> getTagExtension(
		String className, String extensionId) {

		return _instance._viewExtensions.getService(
			className + ":" + extensionId);
	}

	public static TagKeyResolver getTagIdResolver(String target) {
		return _instance._tagResolvers.getService(target);
	}

	private TagExtensionRegistry() {
		_tagResolvers.open();

		_viewExtensions.open();
	}

	private static final TagExtensionRegistry _instance =
		new TagExtensionRegistry();

	private final ServiceTrackerMap<String, TagKeyResolver>
		_tagResolvers = ServiceTrackerCollections.singleValueMap(
			TagKeyResolver.class, "target");

	private final ServiceTrackerMap<String, List<TagExtension>>
		_viewExtensions =
			ServiceTrackerCollections.multiValueMap(
				TagExtension.class, null,
				new ServiceReferenceMapper<String, TagExtension>() {

					@Override
					public void map(
						ServiceReference<TagExtension> serviceReference,
						final Emitter<String> emitter) {

						Registry registry = RegistryUtil.getRegistry();

						TagExtension extension = registry.getService(
							serviceReference);

						try {
							extension.register(
								new TagExtension.TagItemRegistry() {

									@Override
									public void register(
										String tagClassName, String tagKey,
										String itemKey) {

										StringBundler sb = new StringBundler(5);

										sb.append(tagClassName);
										sb.append(':');
										sb.append(tagKey);
										sb.append('#');
										sb.append(itemKey);

										emitter.emit(sb.toString());
									}
							});
						}
						finally {
							registry.ungetService(serviceReference);
						}
					}
				});

}