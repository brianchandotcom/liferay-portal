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
	immediate = true, service = UserToolAssetAddonEntryTracker.class
)
public class UserToolAssetAddonEntryTracker {

	public static List<UserToolAssetAddonEntry> getUserToolAssetAddonEntries() {
		return new ArrayList<>(_userToolAssetAddonEntries.values());
	}

	public static UserToolAssetAddonEntry getUserToolAssetAddonEntry(String key) {
		return _userToolAssetAddonEntries.get(key);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeUserToolAssetAddonEntry"
	)
	protected void addUserToolAssetAddonEntry(
		UserToolAssetAddonEntry userToolAssetAddonEntry) {

		_userToolAssetAddonEntries.put(
			userToolAssetAddonEntry.getKey(), userToolAssetAddonEntry);
	}

	protected void removeUserToolAssetAddonEntry(
		UserToolAssetAddonEntry userToolAssetAddonEntry) {

		_userToolAssetAddonEntries.remove(userToolAssetAddonEntry.getKey());
	}

	private static final Map<String, UserToolAssetAddonEntry>
		_userToolAssetAddonEntries = new ConcurrentHashMap<>();

}