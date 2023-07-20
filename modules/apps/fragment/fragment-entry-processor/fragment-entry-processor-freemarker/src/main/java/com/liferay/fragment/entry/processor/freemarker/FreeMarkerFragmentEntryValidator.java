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

package com.liferay.fragment.entry.processor.freemarker;

import com.liferay.fragment.entry.processor.freemarker.internal.configuration.FreeMarkerFragmentEntryProcessorConfiguration;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.input.template.parser.InputTemplateNode;
import com.liferay.fragment.processor.FragmentEntryValidator;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.io.DummyWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "fragment.entry.processor.priority:Integer=1",
	service = FragmentEntryValidator.class
)
public class FreeMarkerFragmentEntryValidator
	implements FragmentEntryValidator {

	@Override
	public void validateFragmentEntryHTML(
			String html, String configuration, Locale locale)
		throws PortalException {

		FreeMarkerFragmentEntryProcessorConfiguration
			freeMarkerFragmentEntryProcessorConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FreeMarkerFragmentEntryProcessorConfiguration.class,
					CompanyThreadLocal.getCompanyId());

		if (!freeMarkerFragmentEntryProcessorConfiguration.enable() ||
			!_isFreemarkerTemplate(html)) {

			return;
		}

		try {
			HttpServletRequest httpServletRequest = null;
			HttpServletResponse httpServletResponse = null;

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext != null) {
				httpServletRequest = serviceContext.getRequest();
				httpServletResponse = serviceContext.getResponse();
			}

			if (httpServletResponse == null) {
				httpServletResponse = new DummyHttpServletResponse();
			}

			if ((httpServletRequest == null) ||
				(httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY) ==
					null)) {
				return;
			}

				JSONObject configurationDefaultValuesJSONObject =
					_fragmentEntryConfigurationParser.
						getConfigurationDefaultValuesJSONObject(configuration);

				Template template = TemplateManagerUtil.getTemplate(
					TemplateConstants.LANG_TYPE_FTL,
					new StringTemplateResource("template_id", "[#ftl] " + html),
					true);

				template.putAll(
					HashMapBuilder.<String, Object>put(
						"configuration", configurationDefaultValuesJSONObject
					).put(
						"fragmentElementId", StringPool.BLANK
					).put(
						"fragmentEntryLinkNamespace", StringPool.BLANK
					).put(
						"input",
						new InputTemplateNode(
							StringPool.BLANK, StringPool.BLANK,
							StringPool.BLANK, "name", false, false, false,
							"type", "value")
					).put(
						"layoutMode", Constants.VIEW
					).putAll(
						_fragmentEntryConfigurationParser.getContextObjects(
							configurationDefaultValuesJSONObject, configuration,
							null, new long[0])
					).build());

				template.prepareTaglib(httpServletRequest, httpServletResponse);

				template.prepare(httpServletRequest);

				template.processTemplate(new DummyWriter());
		}
		catch (TemplateException templateException) {
			throw new FragmentEntryContentException(
				_getMessage(templateException, locale), templateException);
		}
	}

	private String _getMessage(
		TemplateException templateException, Locale locale) {

		String message = _language.get(locale, "freemarker-syntax-is-invalid");

		Throwable causeThrowable = templateException.getCause();

		String causeThrowableMessage = causeThrowable.getLocalizedMessage();

		if (Validator.isNotNull(causeThrowableMessage)) {
			message = message + "\n\n" + causeThrowableMessage;
		}

		return message;
	}

	private boolean _isFreemarkerTemplate(String html) {
		if (html.contains("${") || html.contains("<#") || html.contains("<@")) {
			return true;
		}

		return false;
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private Language _language;

}