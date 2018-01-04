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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 */
public class NavigationItem implements Serializable {

	public NavigationItem(Builder builder) {
		active = builder._active;
		disabled = builder._disabled;
		href = builder._href;
		icon = builder._icon;
		inputName = builder._inputName;
		inputValue = builder._inputValue;
		items = builder._items;
		label = builder._label;
		separator = builder._separator;
		type = builder._type;
	}

	public final Boolean active;
	public final Boolean disabled;
	public final String href;
	public final String icon;
	public final String inputName;
	public final String inputValue;
	public final List<NavigationItem> items;
	public final String label;
	public final Boolean separator;
	public final String type;

	public static class Builder {

		public Builder active(Boolean active) {
			_active = active;

			return this;
		}

		public NavigationItem build() {
			return new NavigationItem(this);
		}

		public Builder disabled(Boolean disabled) {
			_disabled = disabled;

			return this;
		}

		public Builder href(String href) {
			_href = href;

			return this;
		}

		public Builder icon(String icon) {
			_icon = icon;

			return this;
		}

		public Builder inputName(String inputName) {
			_inputName = inputName;

			return this;
		}

		public Builder inputValue(String inputValue) {
			_inputValue = inputValue;

			return this;
		}

		public Builder item(
			Function<NavigationItem.Builder, NavigationItem> function) {

			_items.add(function.apply(new NavigationItem.Builder()));

			return this;
		}

		public Builder label(String label) {
			_label = label;

			return this;
		}

		public Builder separator(Boolean separator) {
			_separator = separator;

			return this;
		}

		public Builder type(String type) {
			_type = type;

			return this;
		}

		private Boolean _active;
		private Boolean _disabled;
		private String _href;
		private String _icon;
		private String _inputName;
		private String _inputValue;
		private final List<NavigationItem> _items = new ArrayList<>();
		private String _label;
		private Boolean _separator;
		private String _type;

	}

}