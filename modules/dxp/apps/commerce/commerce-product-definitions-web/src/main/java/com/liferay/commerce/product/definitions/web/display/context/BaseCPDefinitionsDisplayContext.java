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

package com.liferay.commerce.product.definitions.web.display.context;

import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.type.CPType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public abstract class BaseCPDefinitionsDisplayContext {

	public BaseCPDefinitionsDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest) {

		this.actionHelper = actionHelper;
		this.httpServletRequest = httpServletRequest;

		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			this.httpServletRequest);

		cpRequestHelper = new CPRequestHelper(httpServletRequest);

		liferayPortletRequest = cpRequestHelper.getLiferayPortletRequest();
		liferayPortletResponse = cpRequestHelper.getLiferayPortletResponse();
	}

	public CPDefinition getCPDefinition() throws PortalException {
		if (_cpDefinition != null) {
			return _cpDefinition;
		}

		_cpDefinition = actionHelper.getCPDefinition(
			cpRequestHelper.getRenderRequest());

		return _cpDefinition;
	}

	public long getCPDefinitionId() throws PortalException {
		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition == null) {
			return 0;
		}

		return cpDefinition.getCPDefinitionId();
	}

	public CPType getCPType() throws PortalException {
		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition == null) {
			return actionHelper.getCPType(
				ParamUtil.getString(httpServletRequest, "productTypeName"));
		}

		return actionHelper.getCPType(cpDefinition.getProductTypeName());
	}

	public CPType getCPType(String name) {
		return actionHelper.getCPType(name);
	}

	public List<CPType> getCPTypes() {
		return actionHelper.getCPTypes();
	}

	public PortletURL getEditProductDefinitionURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));

		return portletURL;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition != null) {
			portletURL.setParameter(
				"cpDefinitionId", String.valueOf(getCPDefinitionId()));
		}

		return portletURL;
	}

	public long getScopeGroupId() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	public String getScreenNavigationCategoryKey() {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_DETAILS;
	}

	public String getSelectedScreenNavigationCategoryKey() {
		return ParamUtil.getString(
			httpServletRequest, "screenNavigationCategoryKey",
			getScreenNavigationCategoryKey());
	}

	public String getSku(CPDefinition cpDefinition, Locale locale) {
		List<CPInstance> cpInstances = cpDefinition.getCPInstances();

		if (cpInstances.size() > 1) {
			return LanguageUtil.get(locale, "multiple-skus");
		}

		CPInstance cpInstance = cpInstances.get(0);

		return cpInstance.getSku();
	}

	protected final ActionHelper actionHelper;
	protected final CPRequestHelper cpRequestHelper;
	protected final HttpServletRequest httpServletRequest;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final PortalPreferences portalPreferences;

	private CPDefinition _cpDefinition;

}