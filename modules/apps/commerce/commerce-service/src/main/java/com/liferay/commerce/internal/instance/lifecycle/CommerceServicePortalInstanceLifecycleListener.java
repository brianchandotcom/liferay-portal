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

package com.liferay.commerce.internal.instance.lifecycle;

import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.oauth2.provider.scope.spi.scope.mapper.ScopeMapper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ClassResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, property = "sap.scope.finder=true",
	service = {
		PortalInstanceLifecycleListener.class, ScopeFinder.class,
		ScopeMapper.class
	}
)
public class CommerceServicePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener
	implements ScopeFinder, ScopeMapper {

	@Override
	public Collection<String> findScopes() {
		return _scopeAliasesList;
	}

	@Override
	public Set<String> map(String scope) {
		return Collections.singleton(scope);
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		User user = _userLocalService.getDefaultUser(company.getCompanyId());

		_addSAPEntries(company.getCompanyId(), user.getUserId());
	}

	@Activate
	protected void activate() {
		Stream<String[]> stream = Arrays.stream(_SAP_ENTRY_OBJECT_ARRAYS);

		_scopeAliasesList = stream.map(
			sapEntryObjectArray -> StringUtil.replaceFirst(
				sapEntryObjectArray[0], "OAUTH2_", StringPool.BLANK)
		).collect(
			Collectors.toList()
		);
	}

	private void _addSAPEntries(long companyId, long userId) throws Exception {
		Class<?> clazz = getClass();

		ResourceBundleLoader resourceBundleLoader =
			new AggregateResourceBundleLoader(
				new ClassResourceBundleLoader(
					"content.Language", clazz.getClassLoader()),
				LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER);

		for (String[] sapEntryObjectArray : _SAP_ENTRY_OBJECT_ARRAYS) {
			String sapEntryName = sapEntryObjectArray[0];

			SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
				companyId, sapEntryName);

			if (sapEntry != null) {
				continue;
			}

			Map<Locale, String> titleMap =
				ResourceBundleUtil.getLocalizationMap(
					resourceBundleLoader,
					"public-access-to-the-commerce-service-apis");

			_sapEntryLocalService.addSAPEntry(
				userId, sapEntryObjectArray[1], true, true, sapEntryName,
				titleMap, new ServiceContext());
		}
	}

	private static final String _CLASS_NAME_COMMERCE_CART_RESOURCE =
		"com.liferay.commerce.frontend.internal.cart.CommerceCartResource";

	private static final String
		_CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE =
			"com.liferay.headless.commerce.delivery.cart.internal.resource." +
				"v1_0.CartItemResourceImpl";

	private static final String _CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE =
		"com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0." +
			"CartResourceImpl";

	private static final String _CLASS_NAME_COMMERCE_SEARCH_RESOURCE =
		"com.liferay.commerce.frontend.internal.search.CommerceSearchResource";

	private static final String _SAP_ENTRY_NAME = "COMMERCE_DEFAULT";

	private static final String[][] _SAP_ENTRY_OBJECT_ARRAYS = {
		{
			_SAP_ENTRY_NAME,
			StringBundler.concat(
				CommerceAccountService.class.getName(), "#getCommerceAccount\n",
				CommerceCountryService.class.getName(),
				"#getBillingCommerceCountriesByChannelId\n",
				CommerceCountryService.class.getName(),
				"#getCommerceCountries\n",
				CommerceCountryService.class.getName(),
				"#getShippingCommerceCountriesByChannelId\n",
				CommerceOrderItemService.class.getName(),
				"#getCommerceOrderItem\n",
				CommerceOrderItemService.class.getName(),
				"#getCommerceOrderItems\n",
				CommerceOrderItemService.class.getName(),
				"#getCommerceOrderItemsQuantity\n",
				CommerceOrderItemService.class.getName(),
				"#upsertCommerceOrderItem\n",
				CommerceOrderService.class.getName(), "#addCommerceOrder\n",
				CommerceOrderService.class.getName(), "#fetchCommerceOrder\n",
				CommerceOrderService.class.getName(), "#getCommerceOrder\n",
				CommerceRegionService.class.getName(), "#getCommerceRegions\n",
				_CLASS_NAME_COMMERCE_CART_RESOURCE, "*\n",
				_CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE, "*\n",
				_CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE, "*\n",
				_CLASS_NAME_COMMERCE_SEARCH_RESOURCE)
		}
	};

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	private List<String> _scopeAliasesList;

	@Reference
	private UserLocalService _userLocalService;

}