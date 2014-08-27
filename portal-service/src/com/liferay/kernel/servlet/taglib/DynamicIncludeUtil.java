/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.kernel.servlet.taglib;

import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class DynamicIncludeUtil {

	public static List<DynamicInclude> getDynamicIncludes(String key) {
		return _instance._dynamicIncludes.getService(key);
	}

	private DynamicIncludeUtil() {
		_dynamicIncludes = ServiceTrackerCollections.multiValueMap(
			DynamicInclude.class, "key");

		_dynamicIncludes.open();
	}

	private static DynamicIncludeUtil _instance = new DynamicIncludeUtil();

	private ServiceTrackerMap<String, List<DynamicInclude>> _dynamicIncludes;

}