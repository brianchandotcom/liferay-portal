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

import com.liferay.fragment.entry.processor.freemarker.configuration.FreeMarkerFragmentEntryProcessorConfiguration;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.util.FragmentEntryConfigUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsConstants;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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

		if (Validator.isNull(configuration)) {
			return null;
		}

		return JSONUtil.put(
			SegmentsConstants.SEGMENTS_EXPERIENCE_ID_PREFIX +
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT,
			FragmentEntryConfigUtil.getConfigurationDefaultValuesJSONObject(
				configuration));
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		FreeMarkerFragmentEntryProcessorConfiguration
			freeMarkerFragmentEntryProcessorConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FreeMarkerFragmentEntryProcessorConfiguration.class,
					fragmentEntryLink.getCompanyId());

		if (!freeMarkerFragmentEntryProcessorConfiguration.enable()) {
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
			new StringTemplateResource("template_id", "[#ftl]\n" + html), true);

		template.put(TemplateConstants.WRITER, unsyncStringWriter);

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(
				TemplateConstants.LANG_TYPE_FTL);

		Map<String, Object> contextObjects = new HashMap<>();

		JSONObject configurationJSONObject = null;

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		Class<?> clazz = getClass();

		String className = clazz.getName();

		if ((editableValuesJSONObject == null) ||
			(editableValuesJSONObject.get(className) == null)) {

			configurationJSONObject =
				FragmentEntryConfigUtil.getConfigurationDefaultValuesJSONObject(
					fragmentEntryLink.getConfiguration());
		}
		else {
			JSONObject configurationValuesJSONObject =
				editableValuesJSONObject.getJSONObject(className);

			long[] segmentsExperienceIds =
				fragmentEntryProcessorContext.getSegmentsExperienceIds();

			long segmentsExperienceId =
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT;

			if (segmentsExperienceIds.length > 0) {
				segmentsExperienceId = segmentsExperienceIds[0];
			}

			configurationJSONObject =
				configurationValuesJSONObject.getJSONObject(
					SegmentsConstants.SEGMENTS_EXPERIENCE_ID_PREFIX +
						segmentsExperienceId);
		}

		contextObjects.put("configuration", configurationJSONObject);

		templateManager.addContextObjects(template, contextObjects);

		templateManager.addTaglibSupport(
			template, fragmentEntryProcessorContext.getHttpServletRequest(),
			fragmentEntryProcessorContext.getHttpServletResponse());

		template.prepare(fragmentEntryProcessorContext.getHttpServletRequest());

		try {
			template.processTemplate(unsyncStringWriter);
		}
		catch (TemplateException te) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			String message = LanguageUtil.get(
				resourceBundle, "freemarker-syntax-is-invalid");

			throw new FragmentEntryContentException(message, te);
		}

		return unsyncStringWriter.toString();
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode,
			Locale locale, long[] segmentsExperienceIds, long previewClassPK,
			int previewType)
		throws PortalException {

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					null, null, mode, locale);

		defaultFragmentEntryProcessorContext.setPreviewClassPK(previewClassPK);
		defaultFragmentEntryProcessorContext.setPreviewType(previewType);
		defaultFragmentEntryProcessorContext.setSegmentsExperienceIds(
			segmentsExperienceIds);

		return processFragmentEntryLinkHTML(
			fragmentEntryLink, html, defaultFragmentEntryProcessorContext);
	}

	@Override
	public void validateFragmentEntryHTML(String html) throws PortalException {
		FreeMarkerFragmentEntryProcessorConfiguration
			freeMarkerFragmentEntryProcessorConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FreeMarkerFragmentEntryProcessorConfiguration.class,
					CompanyThreadLocal.getCompanyId());

		if (!freeMarkerFragmentEntryProcessorConfiguration.enable()) {
			return;
		}

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource("template_id", "[#ftl]\n" + html), true);

		try {
			HttpServletRequest httpServletRequest = null;
			HttpServletResponse httpServletResponse = null;

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext != null) {
				httpServletRequest = serviceContext.getRequest();
				httpServletResponse = serviceContext.getResponse();
			}

			if ((httpServletRequest != null) && (httpServletResponse != null)) {
				TemplateManager templateManager =
					TemplateManagerUtil.getTemplateManager(
						TemplateConstants.LANG_TYPE_FTL);

				templateManager.addTaglibSupport(
					template, httpServletRequest, httpServletResponse);

				template.prepare(httpServletRequest);

				template.processTemplate(new UnsyncStringWriter());
			}
		}
		catch (TemplateException te) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			String message = LanguageUtil.get(
				resourceBundle, "freemarker-syntax-is-invalid");

			throw new FragmentEntryContentException(message, te);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FreeMarkerFragmentEntryProcessor.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}