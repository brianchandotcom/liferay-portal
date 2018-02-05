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

package com.liferay.fragment.entry.processor.portlet;

import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.processor.WhiteListEntry;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = WhiteListEntry.class)
public class PortletWhiteListEntry implements WhiteListEntry {

	@Override
	public String[] getAttributes() {
		return new String[0];
	}

	@Override
	public List<String> getTagNames() {
		List<String> tagNames = new ArrayList<>();

		for (String alias : _portletRegistry.getPortletAliases()) {
			tagNames.add("lfr-app-" + alias);
		}

		return tagNames;
	}

	@Reference
	private PortletRegistry _portletRegistry;

}