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

package com.liferay.roles.admin.web.internal.display.context;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.portal.kernel.security.SecureRandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Drew Brokke
 * @author Evan Thibodeau
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ClayVerticalNavItem {

	public static ClayVerticalNavItem create(
		String label,
		Consumer<ClayVerticalNavItem> clayVerticalNavItemConsumer) {

		ClayVerticalNavItem clayVerticalNavItem = new ClayVerticalNavItem(
			label);

		clayVerticalNavItemConsumer.accept(clayVerticalNavItem);

		if (clayVerticalNavItem.id == null) {
			clayVerticalNavItem.setId(
				_CLAY_VERTICAL_NAV_ITEM + SecureRandomUtil.nextLong());
		}

		return clayVerticalNavItem;
	}

	public ClayVerticalNavItem(String label) {
		this.label = label;
	}

	public void addItems(ClayVerticalNavItem... clayVerticalNavItems) {
		addItems(Arrays.asList(clayVerticalNavItems));
	}

	public void addItems(Collection<ClayVerticalNavItem> clayVerticalNavItems) {
		items.addAll(clayVerticalNavItems);
	}

	public void put(String key, Object value) {
		properties.put(key, value);
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInitialExpanded(boolean initialExpanded) {
		this.initialExpanded = initialExpanded;
	}

	@JsonProperty
	protected boolean active;

	@JsonProperty
	protected String id;

	@JsonProperty
	protected boolean initialExpanded;

	@JsonProperty
	protected List<ClayVerticalNavItem> items = new ArrayList<>();

	@JsonProperty
	protected final String label;

	@JsonAnyGetter
	protected final Map<String, Object> properties = new HashMap<>();

	private static final String _CLAY_VERTICAL_NAV_ITEM =
		"CLAY_VERTICAL_NAV_ITEM";

}