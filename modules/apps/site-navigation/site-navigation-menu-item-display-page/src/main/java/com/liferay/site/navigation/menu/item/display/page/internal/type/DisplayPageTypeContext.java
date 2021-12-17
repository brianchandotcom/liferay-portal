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

package com.liferay.site.navigation.menu.item.display.page.internal.type;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProvider;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProviderTracker;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;

import java.util.Locale;
import java.util.Optional;

/**
 * @author Lourdes Fernández Besada
 */
public class DisplayPageTypeContext {

	public DisplayPageTypeContext(
		String className, InfoItemServiceTracker infoItemServiceTracker,
		LayoutDisplayPageMultiSelectionProviderTracker
			layoutDisplayPageMultiSelectionProviderTracker,
		LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker) {

		_className = className;
		_infoItemServiceTracker = infoItemServiceTracker;
		_layoutDisplayPageMultiSelectionProviderTracker =
			layoutDisplayPageMultiSelectionProviderTracker;
		_layoutDisplayPageProviderTracker = layoutDisplayPageProviderTracker;
	}

	public String getClassName() {
		return _className;
	}

	public InfoItemClassDetails getInfoItemClassDetails() {
		if (_infoItemClassDetails != null) {
			return _infoItemClassDetails;
		}

		InfoItemDetailsProvider<?> infoItemDetailsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class, _className);

		if (infoItemDetailsProvider == null) {
			return null;
		}

		_infoItemClassDetails =
			infoItemDetailsProvider.getInfoItemClassDetails();

		return _infoItemClassDetails;
	}

	public InfoItemFormVariationsProvider<?>
		getInfoItemFormVariationsProvider() {

		if (_infoItemFormVariationsProvider != null) {
			return _infoItemFormVariationsProvider;
		}

		_infoItemFormVariationsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class, _className);

		return _infoItemFormVariationsProvider;
	}

	public String getLabel(Locale locale) {
		InfoItemClassDetails infoItemClassDetails = getInfoItemClassDetails();

		if (infoItemClassDetails == null) {
			return null;
		}

		return infoItemClassDetails.getLabel(locale);
	}

	public Optional<LayoutDisplayPageMultiSelectionProvider<?>>
		getLayoutDisplayPageMultiSelectionProviderOptional() {

		if (_layoutDisplayPageMultiSelectionProvider == null) {
			_layoutDisplayPageMultiSelectionProvider =
				_layoutDisplayPageMultiSelectionProviderTracker.
					getLayoutDisplayPageMultiSelectionProvider(_className);
		}

		return Optional.ofNullable(_layoutDisplayPageMultiSelectionProvider);
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
		if (_layoutDisplayPageProvider != null) {
			return _layoutDisplayPageProvider;
		}

		_layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(_className);

		return _layoutDisplayPageProvider;
	}

	private final String _className;
	private InfoItemClassDetails _infoItemClassDetails;
	private InfoItemFormVariationsProvider<?> _infoItemFormVariationsProvider;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private LayoutDisplayPageMultiSelectionProvider<?>
		_layoutDisplayPageMultiSelectionProvider;
	private final LayoutDisplayPageMultiSelectionProviderTracker
		_layoutDisplayPageMultiSelectionProviderTracker;
	private LayoutDisplayPageProvider<?> _layoutDisplayPageProvider;
	private final LayoutDisplayPageProviderTracker
		_layoutDisplayPageProviderTracker;

}