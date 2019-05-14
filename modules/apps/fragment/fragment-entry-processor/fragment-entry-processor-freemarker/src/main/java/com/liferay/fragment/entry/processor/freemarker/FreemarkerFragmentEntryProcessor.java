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

import com.liferay.fragment.entry.processor.freemarker.configuration.FreemarkerFragmentEntryProcessorConfiguration;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=1",
	service = FragmentEntryProcessor.class
)
public class FreemarkerFragmentEntryProcessor
	implements FragmentEntryProcessor {

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		FreemarkerFragmentEntryProcessorConfiguration
			freemarkerFragmentEntryProcessorConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FreemarkerFragmentEntryProcessorConfiguration.class,
					fragmentEntryLink.getCompanyId());

		if (!freemarkerFragmentEntryProcessorConfiguration.enable()) {
			return html;
		}

		if ((fragmentEntryProcessorContext.getHttpServletRequest() == null) ||
			(fragmentEntryProcessorContext.getHttpServletResponse() == null)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"HttpServletRequest or HttpServletResponse is absent in " +
						"fragment entry processor context");
			}

			return html;
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource("template_id", "[#ftl]\n" + html),
			false);

		template.put(TemplateConstants.WRITER, unsyncStringWriter);

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(
				TemplateConstants.LANG_TYPE_FTL);

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
				resourceBundle, "invalid-freemarker-syntax");

			throw new FragmentEntryContentException(message, te);
		}

		return unsyncStringWriter.toString();
	}

	@Override
	public String processFragmentEntryLinkHTML(
		FragmentEntryLink fragmentEntryLink, String html, String mode,
		Locale locale, long[] segmentsExperienceIds, long previewClassPK,
		int previewType) {

		return html;
	}

	@Override
	public void validateFragmentEntryHTML(String html) throws PortalException {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FreemarkerFragmentEntryProcessor.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}