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
 * @author     Hugo Huijser
 * @deprecated As of Athanasius (7.3.x), replaced by {@link CreationMenuBuilder}
 */
@Deprecated
public class CreationMenuUtil {

	public static CreationMenu addDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenu creationMenu = new CreationMenu();

		return creationMenu.addDropdownItem(unsafeConsumer);
	}

	public static CreationMenu addFavoriteDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenu creationMenu = new CreationMenu();

		return creationMenu.addFavoriteDropdownItem(unsafeConsumer);
	}

	public static CreationMenu addPrimaryDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenu creationMenu = new CreationMenu();

		return creationMenu.addPrimaryDropdownItem(unsafeConsumer);
	}

	public static CreationMenu addRestDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenu creationMenu = new CreationMenu();

		return creationMenu.addRestDropdownItem(unsafeConsumer);
	}

}