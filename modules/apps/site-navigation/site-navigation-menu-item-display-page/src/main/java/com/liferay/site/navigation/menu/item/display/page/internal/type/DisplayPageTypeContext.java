/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.item.display.page.internal.type;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.display.page.LayoutDisplayPageInfoItemFieldValuesProvider;
import com.liferay.layout.display.page.LayoutDisplayPageInfoItemFieldValuesProviderRegistry;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProvider;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProviderRegistry;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;

import java.util.Locale;

/**
 * @author Lourdes Fernández Besada
 */
public class DisplayPageTypeContext {

	public DisplayPageTypeContext(
		String className, InfoItemServiceRegistry infoItemServiceRegistry,
		LayoutDisplayPageInfoItemFieldValuesProviderRegistry
			layoutDisplayPageInfoItemFieldValuesProviderRegistry,
		LayoutDisplayPageMultiSelectionProviderRegistry
			layoutDisplayPageMultiSelectionProviderRegistry,
		LayoutDisplayPageProviderRegistry layoutDisplayPageProviderRegistry) {

		_className = className;
		_infoItemServiceRegistry = infoItemServiceRegistry;
		_layoutDisplayPageInfoItemFieldValuesProviderRegistry =
			layoutDisplayPageInfoItemFieldValuesProviderRegistry;
		_layoutDisplayPageMultiSelectionProviderRegistry =
			layoutDisplayPageMultiSelectionProviderRegistry;
		_layoutDisplayPageProviderRegistry = layoutDisplayPageProviderRegistry;
	}

	public String getClassName() {
		return _className;
	}

	public InfoItemClassDetails getInfoItemClassDetails() {
		InfoItemDetailsProvider<?> infoItemDetailsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemDetailsProvider.class, _className);

		if (infoItemDetailsProvider == null) {
			return null;
		}

		return infoItemDetailsProvider.getInfoItemClassDetails();
	}

	public InfoItemFormVariationsProvider<?>
		getInfoItemFormVariationsProvider() {

		return _infoItemServiceRegistry.getFirstInfoItemService(
			InfoItemFormVariationsProvider.class, _className);
	}

	public String getLabel(Locale locale) {
		InfoItemClassDetails infoItemClassDetails = getInfoItemClassDetails();

		if (infoItemClassDetails == null) {
			return null;
		}

		return infoItemClassDetails.getLabel(locale);
	}

	public LayoutDisplayPageInfoItemFieldValuesProvider<?>
		getLayoutDisplayPageInfoItemFieldValuesProvider() {

		return _layoutDisplayPageInfoItemFieldValuesProviderRegistry.
			getLayoutDisplayPageInfoItemFieldValuesProvider(_className);
	}

	public LayoutDisplayPageMultiSelectionProvider<?>
		getLayoutDisplayPageMultiSelectionProvider() {

		return _layoutDisplayPageMultiSelectionProviderRegistry.
			getLayoutDisplayPageMultiSelectionProvider(_className);
	}

	public LayoutDisplayPageObjectProvider<?>
		getLayoutDisplayPageObjectProvider(long classPK) {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			getLayoutDisplayPageProvider();

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			new InfoItemReference(_className, classPK));
	}

	public LayoutDisplayPageProvider<?> getLayoutDisplayPageProvider() {
		return _layoutDisplayPageProviderRegistry.
			getLayoutDisplayPageProviderByClassName(_className);
	}

	public boolean isAvailable() {
		InfoItemDetailsProvider<?> infoItemDetailsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemDetailsProvider.class, _className);

		if (infoItemDetailsProvider == null) {
			return false;
		}

		return true;
	}

	private final String _className;
	private final InfoItemServiceRegistry _infoItemServiceRegistry;
	private final LayoutDisplayPageInfoItemFieldValuesProviderRegistry
		_layoutDisplayPageInfoItemFieldValuesProviderRegistry;
	private final LayoutDisplayPageMultiSelectionProviderRegistry
		_layoutDisplayPageMultiSelectionProviderRegistry;
	private final LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;

}