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

package com.liferay.translator.web.portlet;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslatorException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.translator.web.configuration.TranslatorConfiguration;
import com.liferay.translator.web.model.Translation;
import com.liferay.translator.web.upgrade.TranslatorWebUpgrade;
import com.liferay.translator.web.util.TranslatorUtil;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
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
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.translator.web.configuration.TranslatorConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-translator",
		"com.liferay.portlet.display-category=category.tools",
		"com.liferay.portlet.icon=/icons/translator.png",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.remoteable=true",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Translator",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class TranslatorPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			TranslatorConfiguration.class.getName(), _translatorConfiguration);

		super.include(viewTemplate, renderRequest, renderResponse);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		actionRequest.setAttribute(
			TranslatorConfiguration.class.getName(), _translatorConfiguration);

		try {
			String fromLanguageId = ParamUtil.getString(
				actionRequest, "fromLanguageId");
			String toLanguageId = ParamUtil.getString(
				actionRequest, "toLanguageId");
			String fromText = ParamUtil.getString(actionRequest, "text");

			if (Validator.isNotNull(fromText)) {
				Translation translation = TranslatorUtil.getTranslation(
					fromLanguageId, toLanguageId, fromText);

				actionRequest.setAttribute(
					TranslatorConfiguration.TRANSLATOR_TRANSLATION,
					translation);
			}
		}
		catch (WebCacheException wce) {
			Throwable cause = wce.getCause();

			if (cause instanceof MicrosoftTranslatorException) {
				SessionErrors.add(actionRequest, cause.getClass(), cause);
			}
			else {
				throw new PortletException(wce);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_translatorConfiguration = Configurable.createConfigurable(
			TranslatorConfiguration.class, properties);
	}

	@Reference(unbind = "-")
	protected void setTranslatorWebUpgrade(
		TranslatorWebUpgrade translatorWebUpgrade) {
	}

	private volatile TranslatorConfiguration _translatorConfiguration;

}