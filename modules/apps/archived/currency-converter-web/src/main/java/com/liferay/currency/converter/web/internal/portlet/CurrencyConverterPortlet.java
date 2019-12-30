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

package com.liferay.currency.converter.web.internal.portlet;

import com.liferay.currency.converter.web.internal.configuration.CurrencyConverterConfiguration;
import com.liferay.currency.converter.web.internal.constants.CurrencyConverterPortletKeys;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
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
	configurationPid = "com.liferay.currency.converter.web.internal.configuration.CurrencyConverterConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-currency-converter",
		"com.liferay.portlet.display-category=category.finance",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/currency_converter.png",
		"com.liferay.portlet.preferences-owned-by-group=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=0",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Currency Converter",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.edit-guest-template=/edit.jsp",
		"javax.portlet.init-param.edit-template=/edit.jsp",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CurrencyConverterPortletKeys.CURRENCY_CONVERTER,
		"javax.portlet.portlet-mode=text/html;edit,edit-guest",
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class CurrencyConverterPortlet extends MVCPortlet {

	@Override
	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			CurrencyConverterConfiguration.class.getName(),
			_currencyConverterConfiguration);

		super.doEdit(renderRequest, renderResponse);
	}

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			CurrencyConverterConfiguration.class.getName(),
			_currencyConverterConfiguration);

		super.doView(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_currencyConverterConfiguration = ConfigurableUtil.createConfigurable(
			CurrencyConverterConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			CurrencyConverterConfiguration.class.getName(),
			_currencyConverterConfiguration);

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.currency.converter.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private volatile CurrencyConverterConfiguration
		_currencyConverterConfiguration;

}