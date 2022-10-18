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

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.fragment.entry.processor.freemarker.internal.configuration.FreeMarkerFragmentEntryProcessorConfiguration;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.helper.FragmentEntryLinkHelper;
import com.liferay.fragment.input.template.parser.FragmentEntryInputTemplateNodeContextHelper;
import com.liferay.fragment.input.template.parser.InputTemplateNode;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.io.DummyWriter;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=1",
	service = FragmentEntryProcessor.class
)
public class FreeMarkerFragmentEntryProcessor
	implements FragmentEntryProcessor {

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		return _fragmentEntryConfigurationParser.
			getConfigurationDefaultValuesJSONObject(configuration);
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		if (Validator.isNull(html)) {
			return html;
		}

		FreeMarkerFragmentEntryProcessorConfiguration
			freeMarkerFragmentEntryProcessorConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FreeMarkerFragmentEntryProcessorConfiguration.class,
					fragmentEntryLink.getCompanyId());

		if (!freeMarkerFragmentEntryProcessorConfiguration.enable() &&
			Validator.isNull(fragmentEntryLink.getRendererKey()) &&
			!fragmentEntryLink.isSystem()) {

			return html;
		}

		if (fragmentEntryProcessorContext.getHttpServletRequest() == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"HTTP servlet request is not set in the fragment entry " +
						"processor context");
			}

			return html;
		}

		if (fragmentEntryProcessorContext.getHttpServletResponse() == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"HTTP servlet response is not set in the fragment entry " +
						"processor context");
			}

			return html;
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource("template_id", "[#ftl] " + html), true);

		template.put(TemplateConstants.WRITER, unsyncStringWriter);

		JSONObject configurationValuesJSONObject =
			_fragmentEntryConfigurationParser.getConfigurationJSONObject(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(),
				fragmentEntryProcessorContext.getLocale());

		Optional<InfoItemReference> contextInfoItemReferenceOptional =
			fragmentEntryProcessorContext.getContextInfoItemReferenceOptional();

		template.putAll(
			HashMapBuilder.<String, Object>put(
				"configuration", configurationValuesJSONObject
			).put(
				"fragmentElementId",
				fragmentEntryProcessorContext.getFragmentElementId()
			).put(
				"fragmentEntryLinkNamespace", fragmentEntryLink.getNamespace()
			).put(
				"layoutMode",
				_getLayoutMode(
					fragmentEntryProcessorContext.getHttpServletRequest())
			).putAll(
				_fragmentEntryConfigurationParser.getContextObjects(
					configurationValuesJSONObject,
					fragmentEntryLink.getConfiguration(),
					_getInfoItem(contextInfoItemReferenceOptional.orElse(null)),
					fragmentEntryProcessorContext.getSegmentsEntryIds())
			).build());

		if (fragmentEntryLink.isTypeInput()) {
			FragmentEntryInputTemplateNodeContextHelper
				fragmentEntryInputTemplateNodeContextHelper =
					new FragmentEntryInputTemplateNodeContextHelper(
						_fragmentEntryLinkHelper.getFragmentEntryName(
							fragmentEntryLink,
							fragmentEntryProcessorContext.getLocale()),
						_dlAppLocalService, _fragmentEntryConfigurationParser,
						_itemSelector);

			template.put(
				"input",
				fragmentEntryInputTemplateNodeContextHelper.toInputTemplateNode(
					fragmentEntryLink,
					fragmentEntryProcessorContext.getHttpServletRequest(),
					fragmentEntryProcessorContext.getInfoFormOptional(),
					fragmentEntryProcessorContext.getLocale()));
		}

		template.prepareTaglib(
			fragmentEntryProcessorContext.getHttpServletRequest(),
			fragmentEntryProcessorContext.getHttpServletResponse());

		template.prepare(fragmentEntryProcessorContext.getHttpServletRequest());

		try {
			template.processTemplate(unsyncStringWriter);
		}
		catch (TemplateException templateException) {
			throw new FragmentEntryContentException(
				_getMessage(templateException), templateException);
		}

		return unsyncStringWriter.toString();
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
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

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource("template_id", "[#ftl] " + html), true);

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

			if ((httpServletRequest != null) &&
				(httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY) !=
					null)) {

				JSONObject configurationDefaultValuesJSONObject =
					_fragmentEntryConfigurationParser.
						getConfigurationDefaultValuesJSONObject(configuration);

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
		}
		catch (TemplateException templateException) {
			throw new FragmentEntryContentException(
				_getMessage(templateException), templateException);
		}
	}

	private Object _getInfoItem(InfoItemReference infoItemReference) {
		if (infoItemReference == null) {
			return null;
		}

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, infoItemReference.getClassName(),
				infoItemIdentifier.getInfoItemServiceFilter());

		try {
			return infoItemObjectProvider.getInfoItem(infoItemIdentifier);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchInfoItemException);
			}
		}

		return null;
	}

	private String _getLayoutMode(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(
			_portal.getOriginalServletRequest(httpServletRequest), "p_l_mode",
			Constants.VIEW);
	}

	private String _getMessage(TemplateException templateException) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		String message = _language.get(
			resourceBundle, "freemarker-syntax-is-invalid");

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

	private static final Log _log = LogFactoryUtil.getLog(
		FreeMarkerFragmentEntryProcessor.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkHelper _fragmentEntryLinkHelper;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}