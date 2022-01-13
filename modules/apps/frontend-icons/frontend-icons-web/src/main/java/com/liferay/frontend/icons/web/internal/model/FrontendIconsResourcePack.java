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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Bryce Osterhaus
 */
public class FrontendIconsResourcePack {

	public FrontendIconsResourcePack(String name) {
		this(name, true);
	}

	public FrontendIconsResourcePack(String name, boolean editable) {
		_name = name;
		_editable = editable;
	}

	public void addIconResource(FrontendIconsResource frontendIconsResource) {
		_iconResources.put(
			frontendIconsResource.getId(), frontendIconsResource);
	}

	public void addIconResources(
		List<FrontendIconsResource> frontendIconsResources) {

		frontendIconsResources.forEach(this::addIconResource);
	}

	public Collection<FrontendIconsResource> getFrontendIconsResources() {
		return _iconResources.values();
	}

	public Optional<FrontendIconsResource> getIconResourceOptional(
		String iconName) {

		return Optional.ofNullable(_iconResources.get(iconName));
	}

	public String getName() {
		return _name;
	}

	public boolean isEditable() {
		return _editable;
	}

	public void removeIconResource(String iconName) {
		_iconResources.remove(iconName);
	}

	private final boolean _editable;
	private final Map<String, FrontendIconsResource> _iconResources =
		new HashMap<>();
	private final String _name;

}