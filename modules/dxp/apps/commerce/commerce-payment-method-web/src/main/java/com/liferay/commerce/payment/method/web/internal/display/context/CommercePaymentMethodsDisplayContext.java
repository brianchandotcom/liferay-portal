/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.payment.method.web.internal.display.context;

import com.liferay.commerce.model.CommercePaymentEngine;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.payment.method.web.internal.admin.PaymentMethodsCommerceAdminModule;
import com.liferay.commerce.service.CommercePaymentMethodService;
import com.liferay.commerce.util.CommercePaymentEngineRegistry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Andrea Di Giorgi
 */
public class CommercePaymentMethodsDisplayContext {

	public CommercePaymentMethodsDisplayContext(
		CommercePaymentEngineRegistry commercePaymentEngineRegistry,
		CommercePaymentMethodService commercePaymentMethodService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commercePaymentEngineRegistry = commercePaymentEngineRegistry;
		_commercePaymentMethodService = commercePaymentMethodService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CommercePaymentEngine getCommercePaymentEngine()
		throws PortalException {

		String key = null;

		CommercePaymentMethod commercePaymentMethod =
			getCommercePaymentMethod();

		if (commercePaymentMethod != null) {
			key = commercePaymentMethod.getEngineKey();
		}
		else {
			key = ParamUtil.getString(_renderRequest, "engineKey");

			if (Validator.isNull(key)) {
				return null;
			}
		}

		return _commercePaymentEngineRegistry.getCommercePaymentEngine(key);
	}

	public CommercePaymentMethod getCommercePaymentMethod()
		throws PortalException {

		if (_commercePaymentMethod != null) {
			return _commercePaymentMethod;
		}

		long commercePaymentMethodId = ParamUtil.getLong(
			_renderRequest, "commercePaymentMethodId");
		String engineKey = ParamUtil.getString(_renderRequest, "engineKey");

		if (commercePaymentMethodId > 0) {
			_commercePaymentMethod =
				_commercePaymentMethodService.getCommercePaymentMethod(
					commercePaymentMethodId);
		}
		else if (Validator.isNotNull(engineKey)) {
			_commercePaymentMethod = getDefaultCommercePaymentMethod(engineKey);
		}

		return _commercePaymentMethod;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"commerceAdminModuleKey", PaymentMethodsCommerceAdminModule.KEY);

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		portletURL.setParameter("navigation", getNavigation());

		return portletURL;
	}

	public SearchContainer<CommercePaymentMethod> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Boolean active = null;
		String emptyResultsMessage = "there-are-no-payment-methods";

		String navigation = getNavigation();

		if (navigation.equals("active")) {
			active = Boolean.TRUE;
			emptyResultsMessage = "there-are-no-active-payment-methods";
		}
		else if (navigation.equals("inactive")) {
			active = Boolean.FALSE;
			emptyResultsMessage = "there-are-no-inactive-payment-methods";
		}

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultsMessage);

		List<CommercePaymentMethod> results;

		if (active != null) {
			results = _commercePaymentMethodService.getCommercePaymentMethods(
				themeDisplay.getScopeGroupId(), active);
		}
		else {
			results = _commercePaymentMethodService.getCommercePaymentMethods(
				themeDisplay.getScopeGroupId());
		}

		if ((active == null) || !active) {
			results = addDefaultCommercePaymentMethods(results);
		}

		_searchContainer.setTotal(results.size());
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	protected List<CommercePaymentMethod> addDefaultCommercePaymentMethods(
			List<CommercePaymentMethod> commercePaymentMethods)
		throws PortalException {

		commercePaymentMethods = ListUtil.copy(commercePaymentMethods);

		Map<String, CommercePaymentEngine> commercePaymentEngines =
			_commercePaymentEngineRegistry.getCommercePaymentEngines();

		Set<String> commerceEngineKeys = new TreeSet<>(
			commercePaymentEngines.keySet());

		for (CommercePaymentMethod commercePaymentMethod :
				commercePaymentMethods) {

			commerceEngineKeys.remove(commercePaymentMethod.getEngineKey());
		}

		for (String name : commerceEngineKeys) {
			CommercePaymentMethod commercePaymentMethod =
				getDefaultCommercePaymentMethod(name);

			commercePaymentMethods.add(commercePaymentMethod);
		}

		return commercePaymentMethods;
	}

	protected CommercePaymentMethod getDefaultCommercePaymentMethod(
			String engineKey)
		throws PortalException {

		CommercePaymentEngine commercePaymentEngine =
			_commercePaymentEngineRegistry.getCommercePaymentEngine(engineKey);

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentMethodService.createCommercePaymentMethod(0L);

		Locale locale = LocaleUtil.getSiteDefault();

		commercePaymentMethod.setName(
			commercePaymentEngine.getName(locale), locale);
		commercePaymentMethod.setDescription(
			commercePaymentEngine.getDescription(locale), locale);

		commercePaymentMethod.setEngineKey(engineKey);

		return commercePaymentMethod;
	}

	protected String getNavigation() {
		return ParamUtil.getString(_renderRequest, "navigation");
	}

	private final CommercePaymentEngineRegistry _commercePaymentEngineRegistry;
	private CommercePaymentMethod _commercePaymentMethod;
	private final CommercePaymentMethodService _commercePaymentMethodService;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CommercePaymentMethod> _searchContainer;

}