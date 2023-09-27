/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Hugo Huijser
 */
public class CreationMenuBuilder {

	public static CreationMenuWrapper addDropdownItem(
		DropdownItem dropdownItem) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addDropdownItem(dropdownItem);
	}

	public static CreationMenuWrapper addDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addDropdownItem(unsafeConsumer);
	}

	public static CreationMenuWrapper addDropdownItem(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		DropdownItem dropdownItem) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addDropdownItem(
			unsafeSupplier, dropdownItem);
	}

	public static CreationMenuWrapper addDropdownItem(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addDropdownItem(
			unsafeSupplier, unsafeConsumer);
	}

	public static CreationMenuWrapper addFavoriteDropdownItem(
		DropdownItem dropdownItem) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addFavoriteDropdownItem(dropdownItem);
	}

	public static CreationMenuWrapper addFavoriteDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addFavoriteDropdownItem(unsafeConsumer);
	}

	public static CreationMenuWrapper addFavoriteDropdownItem(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		DropdownItem dropdownItem) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addFavoriteDropdownItem(
			unsafeSupplier, dropdownItem);
	}

	public static CreationMenuWrapper addFavoriteDropdownItem(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addFavoriteDropdownItem(
			unsafeSupplier, unsafeConsumer);
	}

	public static CreationMenuWrapper addPrimaryDropdownItem(
		DropdownItem dropdownItem) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addPrimaryDropdownItem(dropdownItem);
	}

	public static CreationMenuWrapper addPrimaryDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addPrimaryDropdownItem(unsafeConsumer);
	}

	public static CreationMenuWrapper addPrimaryDropdownItem(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		DropdownItem dropdownItem) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addPrimaryDropdownItem(
			unsafeSupplier, dropdownItem);
	}

	public static CreationMenuWrapper addPrimaryDropdownItem(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addPrimaryDropdownItem(
			unsafeSupplier, unsafeConsumer);
	}

	public static CreationMenuWrapper addRestDropdownItem(
		DropdownItem dropdownItem) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addRestDropdownItem(dropdownItem);
	}

	public static CreationMenuWrapper addRestDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addRestDropdownItem(unsafeConsumer);
	}

	public static CreationMenuWrapper addRestDropdownItem(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		DropdownItem dropdownItem) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addRestDropdownItem(
			unsafeSupplier, dropdownItem);
	}

	public static CreationMenuWrapper addRestDropdownItem(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		CreationMenuWrapper creationMenuWrapper = new CreationMenuWrapper();

		return creationMenuWrapper.addRestDropdownItem(
			unsafeSupplier, unsafeConsumer);
	}

	public static final class CreationMenuWrapper {

		public CreationMenuWrapper addDropdownItem(DropdownItem dropdownItem) {
			_creationMenu.addDropdownItem(dropdownItem);

			return this;
		}

		public CreationMenuWrapper addDropdownItem(
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			_creationMenu.addDropdownItem(unsafeConsumer);

			return this;
		}

		public CreationMenuWrapper addDropdownItem(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			DropdownItem dropdownItem) {

			try {
				if (unsafeSupplier.get()) {
					_creationMenu.addDropdownItem(dropdownItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public CreationMenuWrapper addDropdownItem(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_creationMenu.addDropdownItem(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public CreationMenuWrapper addFavoriteDropdownItem(
			DropdownItem dropdownItem) {

			_creationMenu.addFavoriteDropdownItem(dropdownItem);

			return this;
		}

		public CreationMenuWrapper addFavoriteDropdownItem(
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			_creationMenu.addFavoriteDropdownItem(unsafeConsumer);

			return this;
		}

		public CreationMenuWrapper addFavoriteDropdownItem(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			DropdownItem dropdownItem) {

			try {
				if (unsafeSupplier.get()) {
					_creationMenu.addFavoriteDropdownItem(dropdownItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public CreationMenuWrapper addFavoriteDropdownItem(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_creationMenu.addFavoriteDropdownItem(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public CreationMenuWrapper addPrimaryDropdownItem(
			DropdownItem dropdownItem) {

			_creationMenu.addPrimaryDropdownItem(dropdownItem);

			return this;
		}

		public CreationMenuWrapper addPrimaryDropdownItem(
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			_creationMenu.addPrimaryDropdownItem(unsafeConsumer);

			return this;
		}

		public CreationMenuWrapper addPrimaryDropdownItem(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			DropdownItem dropdownItem) {

			try {
				if (unsafeSupplier.get()) {
					_creationMenu.addPrimaryDropdownItem(dropdownItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public CreationMenuWrapper addPrimaryDropdownItem(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_creationMenu.addPrimaryDropdownItem(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public CreationMenuWrapper addRestDropdownItem(
			DropdownItem dropdownItem) {

			_creationMenu.addRestDropdownItem(dropdownItem);

			return this;
		}

		public CreationMenuWrapper addRestDropdownItem(
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			_creationMenu.addRestDropdownItem(unsafeConsumer);

			return this;
		}

		public CreationMenuWrapper addRestDropdownItem(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			DropdownItem dropdownItem) {

			try {
				if (unsafeSupplier.get()) {
					_creationMenu.addRestDropdownItem(dropdownItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public CreationMenuWrapper addRestDropdownItem(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_creationMenu.addRestDropdownItem(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public CreationMenu build() {
			return _creationMenu;
		}

		private final CreationMenu _creationMenu = new CreationMenu();

	}

}