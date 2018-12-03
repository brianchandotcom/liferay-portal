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

package com.liferay.portal.search.web.internal.custom.filter.portlet.action;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class OccurEntriesHolder {

	public OccurEntriesHolder() {
		add("filter", "filter");
		add("must", "must");
		add("must_not", "must_not");
		add("should", "should");
	}

	public List<OccurEntry> getOccurEntries() {
		return _occurEntries;
	}

	protected void add(String occur, String name) {
		_occurEntries.add(
			new OccurEntry() {
				{
					setName(name);
					setOccur(occur);
				}
			});
	}

	private final List<OccurEntry> _occurEntries = new ArrayList<>();

}