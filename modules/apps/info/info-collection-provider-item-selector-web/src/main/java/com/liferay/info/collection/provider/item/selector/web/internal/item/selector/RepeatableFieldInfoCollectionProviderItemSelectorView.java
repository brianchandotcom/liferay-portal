/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.collection.provider.item.selector.web.internal.item.selector;

import com.liferay.info.collection.provider.item.selector.criterion.RepeatableFieldInfoCollectionProviderItemSelectorCriterion;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.RepeatableFieldsInfoItemFormProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = ItemSelectorView.class)
public class RepeatableFieldInfoCollectionProviderItemSelectorView
	implements ItemSelectorView
		<RepeatableFieldInfoCollectionProviderItemSelectorCriterion> {

	@Override
	public Class
		<? extends RepeatableFieldInfoCollectionProviderItemSelectorCriterion>
			getItemSelectorCriterionClass() {

		return RepeatableFieldInfoCollectionProviderItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "repeatable-fields-collection-providers");
	}

	@Override
	public boolean isVisible(
		RepeatableFieldInfoCollectionProviderItemSelectorCriterion
			itemSelectorCriterion,
		ThemeDisplay themeDisplay) {

		RepeatableFieldsInfoItemFormProvider<?>
			repeatableFieldsInfoItemFormProvider =
				_infoItemServiceRegistry.getFirstInfoItemService(
					RepeatableFieldsInfoItemFormProvider.class,
					itemSelectorCriterion.getItemType());

		if (repeatableFieldsInfoItemFormProvider != null) {
			return true;
		}

		return false;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			RepeatableFieldInfoCollectionProviderItemSelectorCriterion
				repeatableFieldInfoCollectionProviderItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			repeatableFieldInfoCollectionProviderItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new RepeatableFieldInfoCollectionProviderItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, portletURL,
				repeatableFieldInfoCollectionProviderItemSelectorCriterion,
				_getRepeatableFieldsInfoItemFormProvider(
					repeatableFieldInfoCollectionProviderItemSelectorCriterion.
						getItemType())));
	}

	private RepeatableFieldsInfoItemFormProvider<?>
		_getRepeatableFieldsInfoItemFormProvider(String itemType) {

		return _infoItemServiceRegistry.getFirstInfoItemService(
			RepeatableFieldsInfoItemFormProvider.class, itemType);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoListProviderItemSelectorReturnType());

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<RepeatableFieldInfoCollectionProviderItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

}