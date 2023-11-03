/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.taglib.servlet.taglib.util;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class BreadcrumbEntryBuilder {

	public static AfterPutDataStep putData(String key, String value) {
		BreadcrumbEntryStep breadcrumbEntryStep = new BreadcrumbEntryStep();

		return breadcrumbEntryStep.putData(key, value);
	}

	public static AfterPutDataStep putData(
		String key, UnsafeSupplier<String, Exception> valueUnsafeSupplier) {

		BreadcrumbEntryStep breadcrumbEntryStep = new BreadcrumbEntryStep();

		return breadcrumbEntryStep.putData(key, valueUnsafeSupplier);
	}

	public static AfterSetDataStep setData(Map<String, Object> data) {
		BreadcrumbEntryStep breadcrumbEntryStep = new BreadcrumbEntryStep();

		return breadcrumbEntryStep.setData(data);
	}

	public static AfterTitleStep setTitle(String title) {
		BreadcrumbEntryStep breadcrumbEntryStep = new BreadcrumbEntryStep();

		return breadcrumbEntryStep.setTitle(title);
	}

	public static AfterTitleStep setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		BreadcrumbEntryStep breadcrumbEntryStep = new BreadcrumbEntryStep();

		return breadcrumbEntryStep.setTitle(titleUnsafeSupplier);
	}

	public static AfterURLStep setURL(String url) {
		BreadcrumbEntryStep breadcrumbEntryStep = new BreadcrumbEntryStep();

		return breadcrumbEntryStep.setURL(url);
	}

	public static class BreadcrumbEntryStep
		implements AfterBrowsableStep, AfterPutDataStep, AfterSetDataStep,
				   AfterTitleStep, AfterURLStep, BrowsableStep, TitleStep,
				   URLStep {

		@Override
		public BreadcrumbEntry build() {
			return _breadcrumbEntry;
		}

		@Override
		public AfterPutDataStep putData(String key, String value) {
			_breadcrumbEntry.putData(key, value);

			return this;
		}

		@Override
		public AfterPutDataStep putData(
			String key, UnsafeSupplier<String, Exception> valueUnsafeSupplier) {

			try {
				String value = valueUnsafeSupplier.get();

				if (value != null) {
					_breadcrumbEntry.putData(key, value);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		@Override
		public AfterBrowsableStep setBrowasable(boolean browsable) {
			_breadcrumbEntry.setBrowsable(browsable);

			return this;
		}

		@Override
		public AfterSetDataStep setData(Map<String, Object> data) {
			_breadcrumbEntry.setData(data);

			return this;
		}

		@Override
		public AfterTitleStep setTitle(String title) {
			_breadcrumbEntry.setTitle(title);

			return this;
		}

		@Override
		public AfterTitleStep setTitle(
			UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

			try {
				String title = titleUnsafeSupplier.get();

				if (title != null) {
					_breadcrumbEntry.setTitle(title);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		@Override
		public AfterURLStep setURL(String url) {
			_breadcrumbEntry.setURL(url);

			return this;
		}

		private final BreadcrumbEntry _breadcrumbEntry = new BreadcrumbEntry();

	}

	public interface AfterBrowsableStep extends BuildStep {
	}

	public interface AfterPutDataStep
		extends BuildStep, PutDataStep, SetDataStep {
	}

	public interface AfterSetDataStep extends BuildStep, TitleStep, URLStep {
	}

	public interface AfterTitleStep extends BuildStep, URLStep {
	}

	public interface AfterURLStep extends BuildStep {
	}

	public interface BrowsableStep {

		public AfterBrowsableStep setBrowasable(boolean browsable);

	}

	public interface BuildStep {

		public BreadcrumbEntry build();

	}

	public interface PutDataStep {

		public AfterPutDataStep putData(String key, String value);

		public AfterPutDataStep putData(
			String key, UnsafeSupplier<String, Exception> valueUnsafeSupplier);

	}

	public interface SetDataStep {

		public AfterSetDataStep setData(Map<String, Object> data);

	}

	public interface TitleStep {

		public AfterTitleStep setTitle(String title);

		public AfterTitleStep setTitle(
			UnsafeSupplier<String, Exception> titleUnsafeSupplier);

	}

	public interface URLStep {

		public AfterURLStep setURL(String url);

	}

}