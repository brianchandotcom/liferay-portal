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

package com.liferay.fragment.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = WhitelistEntryRegistry.class)
public class WhitelistEntryRegistry {

	public String[] getWhitelistEntriesAttributes(String tagName) {
		return _whitelistEntries.get(tagName);
	}

	public List<String> getWhitelistEntriesTagNames() {
		return new ArrayList<>(_whitelistEntries.keySet());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC, unbind = "unsetWhitelistEntry"
	)
	protected void setWhitelistEntry(WhitelistEntry whiteListEntry) {
		for (String tagName : whiteListEntry.getTagNames()) {
			_whitelistEntries.put(tagName, whiteListEntry.getAttributes());
		}
	}

	protected void unsetWhitelistEntry(WhitelistEntry whiteListEntry) {
		for (String tagName : whiteListEntry.getTagNames()) {
			_whitelistEntries.remove(tagName);
		}
	}

	private final Map<String, String[]> _whitelistEntries =
		new ConcurrentHashMap<>();

}