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

package com.liferay.currency.converter.web.portlet;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.currency.converter.web.configuration.CurrencyConverterConfiguration;
import com.liferay.currency.converter.web.upgrade.CurrencyConverterWebUpgrade;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Peter Fellwock
 */
@Component(
		configurationPid = "com.liferay.currency.converter.web",
		configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-currency-converter",
		"com.liferay.portlet.display-category=category.finance",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/currency_converter.png",
		"com.liferay.portlet.preferences-owned-by-group=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.remoteable=true",
		"com.liferay.portlet.render-weight=0",
		"com.liferay.portlet.struts-path=currency_converter",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Currency Converter",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.edit-guest-template=/edit.jsp",
		"javax.portlet.init-param.edit-template=/edit.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.portlet-mode=text/html;edit,edit-guest",
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class CurrencyConverterPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		_defaultSymbols(renderRequest);

		renderRequest.setAttribute(
			CurrencyConverterConfiguration.class.getName(),
			_CurrencyConverterConfiguration);

		super.doView(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_CurrencyConverterConfiguration = Configurable.createConfigurable(
			CurrencyConverterConfiguration.class, properties);
	}

	@Reference(unbind = "-")
	protected void setCurrencyConverterWebUpgrade(
		CurrencyConverterWebUpgrade currencyConverterWebUpgrade) {
	}

	private void _defaultSymbols(PortletRequest portletRequest)
		throws ReadOnlyException {

		PortletPreferences portletPreferences = portletRequest.getPreferences();

		String[] symbols = StringUtil.split(
			StringUtil.toUpperCase(
				ParamUtil.getString(portletRequest, "symbols")));

		if (symbols.length > 1) {
			portletPreferences.setValues("symbols", symbols);
		}
		else {
			portletPreferences.setValues(
				"symbols", _CurrencyConverterConfiguration.symbols());
		}
	}

	private volatile CurrencyConverterConfiguration
		_CurrencyConverterConfiguration;

}