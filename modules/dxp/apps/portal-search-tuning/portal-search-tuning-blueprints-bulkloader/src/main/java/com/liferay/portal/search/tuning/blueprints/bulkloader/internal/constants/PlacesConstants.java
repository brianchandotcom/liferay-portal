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

package com.liferay.portal.search.tuning.blueprints.bulkloader.internal.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class PlacesConstants {

	public static final Map<String, String> fileNameToCityMap =
		HashMapBuilder.put(
			"la-restaurant.json", "Los Angeles"
		).put(
			"la-tourist.json", "Los Angeles"
		).put(
			"nashville-restaurant.json", "Nashville"
		).put(
			"nashville-tourist.json", "Nashville"
		).put(
			"ny-restaurant.json", "New York"
		).put(
			"ny-tourist.json", "New York"
		).build();

}