/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.collection.provider.item.selector.web.internal.item.selector;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.item.selector.criterion.InfoCollectionProviderItemSelectorCriterion;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "item.selector.view.order:Integer=200",
	service = ItemSelectorView.class
)
public class InfoCollectionProviderItemSelectorView
	implements ItemSelectorView<InfoCollectionProviderItemSelectorCriterion> {

	@Override
	public Class<? extends InfoCollectionProviderItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoCollectionProviderItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "collection-providers");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoCollectionProviderItemSelectorCriterion
				infoCollectionProviderItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			infoCollectionProviderItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new InfoCollectionProviderItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, portletURL,
				_getInfoCollectionProviders(
					infoCollectionProviderItemSelectorCriterion),
				_infoItemServiceRegistry));
	}

	private List<InfoCollectionProvider<?>> _getInfoCollectionProviders(
		InfoCollectionProviderItemSelectorCriterion
			infoCollectionProviderItemSelectorCriterion) {

		if (infoCollectionProviderItemSelectorCriterion.getType() ==
				InfoCollectionProviderItemSelectorCriterion.Type.
					SUPPORTED_INFO_FRAMEWORK_COLLECTIONS) {

			return Collections.unmodifiableList(
				ListUtil.filter(
					_infoItemServiceRegistry.getAllInfoItemServices(
						(Class<InfoCollectionProvider<?>>)
							(Class<?>)InfoCollectionProvider.class),
					InfoCollectionProvider::isAvailable));
		}

		String itemType =
			infoCollectionProviderItemSelectorCriterion.getItemType();

		if (infoCollectionProviderItemSelectorCriterion.getType() ==
				InfoCollectionProviderItemSelectorCriterion.Type.
					ALL_COLLECTIONS) {

			itemType = AssetEntry.class.getName();
		}

		return Collections.unmodifiableList(
			ListUtil.filter(
				_infoItemServiceRegistry.getAllInfoItemServices(
					(Class<InfoCollectionProvider<?>>)
						(Class<?>)InfoCollectionProvider.class,
					itemType),
				InfoCollectionProvider::isAvailable));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoListProviderItemSelectorReturnType());

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<InfoCollectionProviderItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.info.collection.provider.item.selector.web)"
	)
	private ServletContext _servletContext;

}