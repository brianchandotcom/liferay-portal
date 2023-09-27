/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.transformer;

import com.liferay.portal.json.JoddJSONContext;
import com.liferay.portal.kernel.json.JSONContext;
import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.util.TreeMapBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import jodd.json.impl.MapJsonSerializer;

/**
 * @author Igor Spasic
 */
public class SortedHashMapJSONTransformer
	extends MapJsonSerializer implements JSONTransformer {

	@Override
	public void transform(JSONContext jsonContext, Object map) {
		if (map instanceof HashMap) {
			TreeMap<Object, Object> treeMap =
				TreeMapBuilder.<Object, Object>putAll(
					(HashMap<Object, Object>)map
				).build();

			map = treeMap;
		}

		JoddJSONContext joddJSONContext = (JoddJSONContext)jsonContext;

		super.serialize(joddJSONContext.getImplementation(), (Map<?, ?>)map);
	}

}