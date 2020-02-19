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

import com.liferay.petra.function.UnsafeConsumer;

/**
 * @author Hugo Huijser
 */
public class CreationMenuBuilder {

	public static CreationMenuWrapper addDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addDropdownItem(unsafeConsumer);
	}

	public static CreationMenuWrapper addFavoriteDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addFavoriteDropdownItem(unsafeConsumer);
	}

	public static CreationMenuWrapper addPrimaryDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addPrimaryDropdownItem(unsafeConsumer);
	}

	public static CreationMenuWrapper addRestDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addRestDropdownItem(unsafeConsumer);
	}

	public static final class CreationMenuWrapper {

		public CreationMenuWrapper addDropdownItem(
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			_creationMenu.addDropdownItem(unsafeConsumer);

			return this;
		}

		public CreationMenuWrapper addFavoriteDropdownItem(
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			_creationMenu.addFavoriteDropdownItem(unsafeConsumer);

			return this;
		}

		public CreationMenuWrapper addPrimaryDropdownItem(
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			_creationMenu.addPrimaryDropdownItem(unsafeConsumer);

			return this;
		}

		public CreationMenuWrapper addRestDropdownItem(
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			_creationMenu.addRestDropdownItem(unsafeConsumer);

			return this;
		}

		public CreationMenu build() {
			return _creationMenu;
		}

		private final CreationMenu _creationMenu = new CreationMenu();

	}

}