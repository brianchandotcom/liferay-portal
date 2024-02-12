/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.item.display.page.internal.type;

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.layout.display.page.LayoutDisplayPageInfoItemFieldValuesProvider;
import com.liferay.layout.display.page.LayoutDisplayPageInfoItemFieldValuesProviderRegistry;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProvider;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProviderRegistry;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

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

	public InfoItemPermissionProvider getInfoItemPermissionProvider() {
		return _infoItemServiceRegistry.getFirstInfoItemService(
			InfoItemPermissionProvider.class, _className);
	}

	public InfoItemReference getInfoItemReference(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		return new InfoItemReference(
			_className,
			new ClassPKInfoItemIdentifier(
				GetterUtil.getLong(
					typeSettingsUnicodeProperties.get("classPK"))));
	}

	public String getLabel(Locale locale) {
		InfoItemClassDetails infoItemClassDetails = getInfoItemClassDetails();

		if (infoItemClassDetails == null) {
			return StringPool.BLANK;
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
		InfoItemClassDetails infoItemClassDetails = getInfoItemClassDetails();

		if (infoItemClassDetails == null) {
			return false;
		}

		InfoItemDetailsProvider<?> infoItemDetailsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemDetailsProvider.class, _className);

		if (infoItemDetailsProvider == null) {
			return false;
		}

		InfoPermissionProvider infoPermissionProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoPermissionProvider.class, _className);

		if ((infoPermissionProvider != null) &&
			!infoPermissionProvider.hasViewPermission(
				PermissionThreadLocal.getPermissionChecker())) {

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