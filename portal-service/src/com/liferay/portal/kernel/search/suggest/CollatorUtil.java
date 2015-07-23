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

package com.liferay.portal.kernel.search.suggest;

import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class CollatorUtil {

	public static String collate(
			Map<String, List<String>> suggestions, List<String> keywords)
		throws SearchException {

		Collator collator = _instance._serviceTracker.getService();

		if (collator != null) {
			return collator.collate(suggestions, keywords);
		}

		return StringPool.BLANK;
	}

	private CollatorUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(Collator.class);

		_serviceTracker.open();
	}

	private static final CollatorUtil _instance = new CollatorUtil();

	private final ServiceTracker<Collator, Collator> _serviceTracker;

}