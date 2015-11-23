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

package com.liferay.portal.kernel.servlet.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Carlos Sierra Andrés
 * @author Raymond Augé
 */
public class TagDynamicIncludeUtil {

	public static List<TagDynamicInclude> getTagDynamicIncludes(
		String tagClassName, String tagDynamicId, String tagPoint) {

		String key = _getKey(tagClassName, tagDynamicId, tagPoint);

		return _instance._tagDynamicIncludes.getService(key);
	}

	public static boolean hasTagDynamicInclude(
		String tagClassName, String tagDynamicId, String tagPoint) {

		List<TagDynamicInclude> tagDynamicIncludes = getTagDynamicIncludes(
			tagClassName, tagDynamicId, tagPoint);

		if ((tagDynamicIncludes == null) || tagDynamicIncludes.isEmpty()) {
			return false;
		}

		return true;
	}

	public static void include(
		HttpServletRequest request, HttpServletResponse response,
		String tagClassName, String tagDynamicId, String tagPoint,
		boolean ascendingPriority) {

		List<TagDynamicInclude> tagDynamicIncludes = getTagDynamicIncludes(
			tagClassName, tagDynamicId, tagPoint);

		if ((tagDynamicIncludes == null) || tagDynamicIncludes.isEmpty()) {
			return;
		}

		Iterator<TagDynamicInclude> iterator = null;

		if (ascendingPriority) {
			iterator = tagDynamicIncludes.iterator();
		}
		else {
			iterator = ListUtil.reverseIterator(tagDynamicIncludes);
		}

		while (iterator.hasNext()) {
			TagDynamicInclude tagDynamicInclude = iterator.next();

			try {
				tagDynamicInclude.include(
					request, response, tagClassName, tagDynamicId, tagPoint);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static String _getKey(
		String tagClassName, String tagDynamicId, String tagPoint) {

		StringBundler sb = new StringBundler(5);

		sb.append(tagClassName);
		sb.append(CharPool.POUND);
		sb.append(tagPoint);
		sb.append(CharPool.POUND);
		sb.append(tagDynamicId);

		return sb.toString();
	}

	private TagDynamicIncludeUtil() {
		_tagDynamicIncludes = ServiceTrackerCollections.openMultiValueMap(
			TagDynamicInclude.class, null,
			new ServiceReferenceMapper<String, TagDynamicInclude>() {

				@Override
				public void map(
					ServiceReference<TagDynamicInclude> serviceReference,
					final Emitter<String> emitter) {

					Registry registry = RegistryUtil.getRegistry();

					TagDynamicInclude tagDynamicInclude = registry.getService(
						serviceReference);

					try {
						tagDynamicInclude.register(
							new TagDynamicInclude.TagDynamicIncludeRegistry() {

								@Override
								public void register(
									String tagClassName, String tagDynamicId,
									String tagPoint) {

									String key = _getKey(
										tagClassName, tagDynamicId, tagPoint);

									emitter.emit(key);
								}

							});
					}
					finally {
						registry.ungetService(serviceReference);
					}
				}

			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TagDynamicIncludeUtil.class);

	private static final TagDynamicIncludeUtil _instance =
		new TagDynamicIncludeUtil();

	private final ServiceTrackerMap<String, List<TagDynamicInclude>>
		_tagDynamicIncludes;

}