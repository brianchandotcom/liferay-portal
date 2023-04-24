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

package com.liferay.osb.faro.engine.client.constants;

import com.liferay.osb.faro.engine.client.model.Asset;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class AssetConstants {

	public static final String TYPE_ASSET = "Asset";

	public static Map<String, String> getTypes() {
		return _types;
	}

	public static List<String> getTypes(int action) {
		return _actionTypes.getOrDefault(action, Collections.emptyList());
	}

	private static final Map<Integer, List<String>> _actionTypes =
		HashMapBuilder.<Integer, List<String>>put(
			ActivityConstants.ACTION_DOWNLOADS,
			Collections.singletonList(Asset.AssetType.Document.name())
		).put(
			ActivityConstants.ACTION_SUBMISSIONS,
			Collections.singletonList(Asset.AssetType.Form.name())
		).put(
			ActivityConstants.ACTION_VISITS,
			Arrays.asList(
				Asset.AssetType.Form.name(), Asset.AssetType.Page.name())
		).build();
	private static final Map<String, String> _types = HashMapBuilder.put(
		"asset", TYPE_ASSET
	).put(
		"document", Asset.AssetType.Document.name()
	).put(
		"form", Asset.AssetType.Form.name()
	).put(
		"webPage", Asset.AssetType.Page.name()
	).build();

}