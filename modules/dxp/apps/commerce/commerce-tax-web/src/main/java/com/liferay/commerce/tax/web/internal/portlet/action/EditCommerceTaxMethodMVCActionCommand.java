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

package com.liferay.commerce.tax.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.exception.CommerceTaxMethodNameException;
import com.liferay.commerce.exception.NoSuchTaxMethodException;
import com.liferay.commerce.model.CommerceTaxEngine;
import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.service.CommerceTaxMethodService;
import com.liferay.commerce.util.CommerceTaxEngineRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceTaxMethod"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTaxMethodMVCActionCommand
	extends BaseMVCActionCommand {

	protected CommerceTaxMethod createCommerceTaxMethod(
			ActionRequest actionRequest)
		throws PortalException {

		return createCommerceTaxMethod(actionRequest, false);
	}

	protected CommerceTaxMethod createCommerceTaxMethod(
			ActionRequest actionRequest, boolean active)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale siteDefaultLocale = themeDisplay.getSiteDefaultLocale();

		String engineKey = ParamUtil.getString(actionRequest, "engineKey");

		CommerceTaxEngine commerceTaxEngine =
			_commerceTaxEngineRegistry.getCommerceTaxEngine(engineKey);

		Map<Locale, String> nameMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		nameMap.put(
			siteDefaultLocale, commerceTaxEngine.getName(siteDefaultLocale));
		descriptionMap.put(
			siteDefaultLocale,
			commerceTaxEngine.getDescription(siteDefaultLocale));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceTaxMethod.class.getName(), actionRequest);

		return _commerceTaxMethodService.addCommerceTaxMethod(
			nameMap, descriptionMap, engineKey, true, active, serviceContext);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.EDIT)) {
				editCommerceTaxMethod(actionRequest, actionResponse);

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommerceTaxMethod(actionRequest);
			}
			else if (cmd.equals("setActive")) {
				setActive(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchTaxMethodException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommerceTaxMethodNameException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void editCommerceTaxMethod(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String redirect = null;

		long commerceTaxMethodId = ParamUtil.getLong(
			actionRequest, "commerceTaxMethodId");

		if (commerceTaxMethodId > 0) {
			redirect = getEditCommerceTaxMethodURL(
				actionRequest, commerceTaxMethodId);
		}
		else {
			CommerceTaxMethod commerceTaxMethod = createCommerceTaxMethod(
				actionRequest);

			redirect = getEditCommerceTaxMethodURL(
				actionRequest, commerceTaxMethod.getCommerceTaxMethodId());
		}

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected String getEditCommerceTaxMethodURL(
		ActionRequest actionRequest, long commerceTaxMethodId) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CommerceAdminPortletKeys.COMMERCE_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceTaxMethod");
		portletURL.setParameter(
			"commerceTaxMethodId", String.valueOf(commerceTaxMethodId));

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String engineKey = ParamUtil.getString(actionRequest, "engineKey");

		if (Validator.isNotNull(engineKey)) {
			portletURL.setParameter("engineKey", engineKey);
		}

		return portletURL.toString();
	}

	protected void setActive(ActionRequest actionRequest) throws Exception {
		long commerceTaxMethodId = ParamUtil.getLong(
			actionRequest, "commerceTaxMethodId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		if (commerceTaxMethodId > 0) {
			_commerceTaxMethodService.setActive(commerceTaxMethodId, active);
		}
		else {
			createCommerceTaxMethod(actionRequest, active);
		}
	}

	protected CommerceTaxMethod updateCommerceTaxMethod(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceTaxMethodId = ParamUtil.getLong(
			actionRequest, "commerceTaxMethodId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		String engineKey = ParamUtil.getString(actionRequest, "engineKey");
		boolean percentage = ParamUtil.getBoolean(actionRequest, "percentage");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceTaxMethod.class.getName(), actionRequest);

		CommerceTaxMethod commerceTaxMethod = null;

		if (commerceTaxMethodId <= 0) {
			commerceTaxMethod = _commerceTaxMethodService.addCommerceTaxMethod(
				nameMap, descriptionMap, engineKey, percentage, active,
				serviceContext);
		}
		else {
			commerceTaxMethod =
				_commerceTaxMethodService.updateCommerceTaxMethod(
					commerceTaxMethodId, nameMap, descriptionMap, percentage,
					active);
		}

		return commerceTaxMethod;
	}

	@Reference
	private CommerceTaxEngineRegistry _commerceTaxEngineRegistry;

	@Reference
	private CommerceTaxMethodService _commerceTaxMethodService;

	@Reference
	private Portal _portal;

}