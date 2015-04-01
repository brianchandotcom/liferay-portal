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

package com.liferay.journal.content.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true, service = ContentMetadataEntryTracker.class
)
public class ContentMetadataEntryTracker {

	public static List<ContentMetadataEntry> getContentMetadataEntries() {
		return new ArrayList<>(_contentMetadataEntries.values());
	}

	public static ContentMetadataEntry getContentMetadataEntry(String key) {
		return _contentMetadataEntries.get(key);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeContentMetadataEntry"
	)
	protected void addContentMetadataEntry(
		ContentMetadataEntry contentMetadataEntry) {

		_contentMetadataEntries.put(
			contentMetadataEntry.getKey(), contentMetadataEntry);
	}

	protected void removeContentMetadataEntry(
		ContentMetadataEntry contentMetadataEntry) {

		_contentMetadataEntries.remove(contentMetadataEntry.getKey());
	}

	private static final Map<String, ContentMetadataEntry>
		_contentMetadataEntries = new ConcurrentHashMap<>();

}