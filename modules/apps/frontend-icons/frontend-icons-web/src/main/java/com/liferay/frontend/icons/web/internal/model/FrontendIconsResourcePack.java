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

package com.liferay.frontend.icons.web.internal.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bryce Osterhaus
 */
public class FrontendIconsResourcePack {

	public FrontendIconsResourcePack(boolean editable, String name) {
		_editable = editable;
		_name = name;
	}

	public FrontendIconsResourcePack(String name) {
		this(true, name);
	}

	public void addFrontendIconResource(
		FrontendIconsResource frontendIconsResource) {

		_frontendIconsResources.put(
			frontendIconsResource.getName(), frontendIconsResource);
	}

	public void addFrontendIconsResources(
		List<FrontendIconsResource> frontendIconsResources) {

		frontendIconsResources.forEach(this::addFrontendIconResource);
	}

	public void deleteFrontendIconsResource(String name) {
		_frontendIconsResources.remove(name);
	}

	public FrontendIconsResource getFrontendIconsResource(String name) {
		return _frontendIconsResources.get(name);
	}

	public Collection<FrontendIconsResource> getFrontendIconsResources() {
		List<FrontendIconsResource> frontendIconsResources = new ArrayList<>(
			_frontendIconsResources.values());

		Collections.sort(
			frontendIconsResources,
			Comparator.comparing(FrontendIconsResource::getName));

		return frontendIconsResources;
	}

	public String getName() {
		return _name;
	}

	public boolean isEditable() {
		return _editable;
	}

	private final boolean _editable;
	private final Map<String, FrontendIconsResource> _frontendIconsResources =
		new HashMap<>();
	private final String _name;

}