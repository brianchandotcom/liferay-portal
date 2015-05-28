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

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class CollatorUtil {

	public static String collate(
			Map<String, List<String>> suggestions, List<String> keywords)
		throws SearchException {

		return _getCollator().collate(suggestions, keywords);
	}

	public void setCollator(Collator collator) {
		_collator = collator;
	}

	private static Collator _getCollator() {
		return _collator;
	}

	private static Collator _collator;

}