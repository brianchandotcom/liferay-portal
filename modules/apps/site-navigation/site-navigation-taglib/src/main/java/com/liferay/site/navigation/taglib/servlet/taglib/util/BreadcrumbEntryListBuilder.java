/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.taglib.servlet.taglib.util;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class BreadcrumbEntryListBuilder {

	public static BreadcrumbEntryListWrapper add(
		BreadcrumbEntry breadcrumbEntry) {

		BreadcrumbEntryListWrapper breadcrumbEntryListWrapper =
			new BreadcrumbEntryListWrapper();

		return breadcrumbEntryListWrapper.add(breadcrumbEntry);
	}

	public static BreadcrumbEntryListWrapper add(
		UnsafeConsumer<BreadcrumbEntry, Exception> unsafeConsumer) {

		BreadcrumbEntryListWrapper breadcrumbEntryListWrapper =
			new BreadcrumbEntryListWrapper();

		return breadcrumbEntryListWrapper.add(unsafeConsumer);
	}

	public static BreadcrumbEntryListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		BreadcrumbEntry breadcrumbEntry) {

		BreadcrumbEntryListWrapper breadcrumbEntryListWrapper =
			new BreadcrumbEntryListWrapper();

		return breadcrumbEntryListWrapper.add(unsafeSupplier, breadcrumbEntry);
	}

	public static BreadcrumbEntryListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<BreadcrumbEntry, Exception> unsafeConsumer) {

		BreadcrumbEntryListWrapper breadcrumbEntryListWrapper =
			new BreadcrumbEntryListWrapper();

		return breadcrumbEntryListWrapper.add(unsafeSupplier, unsafeConsumer);
	}

	public static BreadcrumbEntryListWrapper addAll(
		List<BreadcrumbEntry> breadcrumbEntries) {

		BreadcrumbEntryListWrapper breadcrumbEntryListWrapper =
			new BreadcrumbEntryListWrapper();

		return breadcrumbEntryListWrapper.addAll(breadcrumbEntries);
	}

	public static BreadcrumbEntryListWrapper addAll(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeSupplier<List<BreadcrumbEntry>, Exception>
			breadcrumbEntriesUnsafeSupplier) {

		BreadcrumbEntryListWrapper breadcrumbEntryListWrapper =
			new BreadcrumbEntryListWrapper();

		return breadcrumbEntryListWrapper.addAll(
			unsafeSupplier, breadcrumbEntriesUnsafeSupplier);
	}

	public static BreadcrumbEntryListWrapper addAll(
		UnsafeSupplier<List<BreadcrumbEntry>, Exception>
			breadcrumbEntriesUnsafeSupplier) {

		BreadcrumbEntryListWrapper breadcrumbEntryListWrapper =
			new BreadcrumbEntryListWrapper();

		return breadcrumbEntryListWrapper.addAll(
			breadcrumbEntriesUnsafeSupplier);
	}

	public static final class BreadcrumbEntryListWrapper {

		public BreadcrumbEntryListWrapper add(BreadcrumbEntry breadcrumbEntry) {
			if (breadcrumbEntry != null) {
				_breadcrumbEntries.add(breadcrumbEntry);
			}

			return this;
		}

		public BreadcrumbEntryListWrapper add(
			UnsafeConsumer<BreadcrumbEntry, Exception> unsafeConsumer) {

			if (unsafeConsumer == null) {
				return this;
			}

			BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

			try {
				unsafeConsumer.accept(breadcrumbEntry);
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			add(breadcrumbEntry);

			return this;
		}

		public BreadcrumbEntryListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			BreadcrumbEntry breadcrumbEntry) {

			try {
				if (unsafeSupplier.get()) {
					add(breadcrumbEntry);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public BreadcrumbEntryListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<BreadcrumbEntry, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public BreadcrumbEntryListWrapper addAll(
			List<BreadcrumbEntry> breadcrumbEntries) {

			for (BreadcrumbEntry breadcrumbEntry : breadcrumbEntries) {
				add(breadcrumbEntry);
			}

			return this;
		}

		public BreadcrumbEntryListWrapper addAll(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeSupplier<List<BreadcrumbEntry>, Exception>
				breadcrumbEntriesUnsafeSupplier) {

			try {
				if (unsafeSupplier.get()) {
					addAll(breadcrumbEntriesUnsafeSupplier.get());
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public BreadcrumbEntryListWrapper addAll(
			UnsafeSupplier<List<BreadcrumbEntry>, Exception>
				breadcrumbEntriesUnsafeSupplier) {

			try {
				addAll(breadcrumbEntriesUnsafeSupplier.get());
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public List<BreadcrumbEntry> build() {
			return _breadcrumbEntries;
		}

		private final List<BreadcrumbEntry> _breadcrumbEntries =
			new ArrayList<>();

	}

}