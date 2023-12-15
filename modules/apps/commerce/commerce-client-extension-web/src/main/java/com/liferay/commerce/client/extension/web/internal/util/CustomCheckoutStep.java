/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.client.extension.web.internal.util;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.Http;

import java.util.Locale;
import java.util.Objects;

import javax.portlet.ActionParameters;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Andrea Sbarra
 */
public class CustomCheckoutStep implements CommerceCheckoutStep {

	public CustomCheckoutStep(
		boolean active, String actionURL, String baseURL,
		JSONFactory jsonFactory, JSPRenderer jspRenderer, String label,
		String name, String oAuth2ApplicationExternalReferenceCode,
		boolean order, PortalCatapult portalCatapult, String readyURL,
		String renderURL, boolean sennaDisabled, ServletContext servletContext,
		boolean showControls, UserService userService, boolean visible) {

		_active = active;
		_actionURL = actionURL;
		_baseURL = baseURL;
		_jsonFactory = jsonFactory;
		_jspRenderer = jspRenderer;
		_label = label;
		_name = name;
		_oAuth2ApplicationExternalReferenceCode =
			oAuth2ApplicationExternalReferenceCode;
		_order = order;
		_portalCatapult = portalCatapult;
		_readyURL = readyURL;
		_renderURL = renderURL;
		_sennaDisabled = sennaDisabled;
		_servletContext = servletContext;
		_showControls = showControls;
		_userService = userService;
		_visible = visible;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, _label);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		User currentUser = _userService.getCurrentUser();

		try {
			String status = new String(
				_portalCatapult.launch(
					commerceOrder.getCompanyId(), Http.Method.GET,
					_oAuth2ApplicationExternalReferenceCode,
					_jsonFactory.createJSONObject(), _readyURL,
					currentUser.getUserId()
				).get());

			if (Objects.equals(status, "READY") && _active) {
				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return false;
		}

		return false;
	}

	@Override
	public boolean isOrder() {
		return _order;
	}

	@Override
	public boolean isSennaDisabled() {
		return _sennaDisabled;
	}

	@Override
	public boolean isVisible(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return _visible;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		JSONObject jsonObject = JSONUtil.put(
			"commerceOrderId", commerceOrder.getCommerceOrderId());

		ActionParameters actionParameters = actionRequest.getActionParameters();

		for (String name : actionParameters.getNames()) {
			jsonObject.put(name, actionParameters.getValue(name));
		}

		User currentUser = _userService.getCurrentUser();

		_portalCatapult.launch(
			commerceOrder.getCompanyId(), Http.Method.POST,
			_oAuth2ApplicationExternalReferenceCode, jsonObject, _actionURL,
			currentUser.getUserId()
		).get();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute("renderURL", _renderURL);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/checkout_step/cx_checkout_step.jsp");
	}

	@Override
	public boolean showControls(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _showControls;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomCheckoutStep.class);

	private final String _actionURL;
	private final boolean _active;
	private final String _baseURL;
	private final JSONFactory _jsonFactory;
	private final JSPRenderer _jspRenderer;
	private final String _label;
	private final String _name;
	private final String _oAuth2ApplicationExternalReferenceCode;
	private final boolean _order;
	private final PortalCatapult _portalCatapult;
	private final String _readyURL;
	private final String _renderURL;
	private final boolean _sennaDisabled;
	private final ServletContext _servletContext;
	private final boolean _showControls;
	private final UserService _userService;
	private final boolean _visible;

}