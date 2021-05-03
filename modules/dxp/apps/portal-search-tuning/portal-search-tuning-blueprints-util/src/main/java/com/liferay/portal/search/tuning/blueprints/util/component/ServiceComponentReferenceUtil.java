/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.util.component;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Set;

/**
 * @author Petteri Karttunen
 */
public class ServiceComponentReferenceUtil {

	public static <T> void addToMapByName(
		Map<String, ServiceComponentReference<T>> map, T obj,
		Map<String, Object> properties) {

		addToMapByProperty(map, obj, properties, "name");
	}

	public static <T> void addToMapByProperty(
		Map<String, ServiceComponentReference<T>> map, T obj,
		Map<String, Object> properties, String idProperty) {

		Class<?> clazz = obj.getClass();

		String id = (String)properties.get(idProperty);

		if (Validator.isBlank(id)) {
			StringBundler sb = new StringBundler(5);

			sb.append("Unable to register service component ");
			sb.append(clazz.getName());
			sb.append(". ");
			sb.append(idProperty);
			sb.append(" property empty.");

			_log.error(sb.toString());

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<T> serviceComponentReference =
			new ServiceComponentReference<>(obj, serviceRanking);

		if (map.containsKey(id)) {
			ServiceComponentReference<T> previousReference = map.get(id);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				map.put(id, serviceComponentReference);
			}
		}
		else {
			map.put(id, serviceComponentReference);
		}
	}

	public static <T> String[] getComponentKeys(
		Map<String, ServiceComponentReference<T>> map) {

		Set<String> keySet = map.keySet();

		return keySet.toArray(new String[0]);
	}

	public static <T> void removeFromMapByName(
		Map<String, ServiceComponentReference<T>> map, T obj,
		Map<String, Object> properties) {

		removeFromMapByProperty(map, obj, properties, "name");
	}

	public static <T> void removeFromMapByProperty(
		Map<String, ServiceComponentReference<T>> map, T obj,
		Map<String, Object> properties, String idProperty) {

		String id = (String)properties.get(idProperty);

		if (Validator.isBlank(id)) {
			return;
		}

		map.remove(id);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceComponentReferenceUtil.class);

}