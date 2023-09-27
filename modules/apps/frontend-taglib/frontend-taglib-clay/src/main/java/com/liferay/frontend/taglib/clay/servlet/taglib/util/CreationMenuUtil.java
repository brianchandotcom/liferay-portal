/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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