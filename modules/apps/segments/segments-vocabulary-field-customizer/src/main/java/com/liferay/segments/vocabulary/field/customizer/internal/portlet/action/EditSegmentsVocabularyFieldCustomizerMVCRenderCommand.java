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

package com.liferay.segments.vocabulary.field.customizer.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeService;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyFieldCustomizerConfiguration;
import com.liferay.segments.vocabulary.field.customizer.internal.constants.SegmentsVocabularyFieldCustomizerWebKeys;
import com.liferay.segments.vocabulary.field.customizer.internal.display.context.SegmentsVocabularyFieldCustomizerDisplayContext;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/edit_segments_vocabulary_field_customizer"
	},
	service = MVCRenderCommand.class
)
public class EditSegmentsVocabularyFieldCustomizerMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/edit_segments_vocabulary_field_customizer.jsp");

			ExtendedMetaTypeInformation metaTypeInformation =
				_extendedMetaTypeService.getMetaTypeInformation(_bundle);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			ExtendedObjectClassDefinition extendedObjectClassDefinition =
				metaTypeInformation.getObjectClassDefinition(
					SegmentsVocabularyFieldCustomizerConfiguration.class.
						getCanonicalName(),
					LanguageUtil.getLanguageId(themeDisplay.getLocale()));

			renderRequest.setAttribute(
				SegmentsVocabularyFieldCustomizerWebKeys.
					SEGMENTS_VOCABULARY_FIELD_CUSTOMIZER_DISPLAY_CONTEXT,
				new SegmentsVocabularyFieldCustomizerDisplayContext(
					renderRequest, renderResponse, _configurationAdmin,
					extendedObjectClassDefinition,
					_fieldNameConfigurationFieldOptionsProvider.getOptions(),
					_vocabularyConfigurationFieldOptionsProvider.getOptions()));

			requestDispatcher.forward(
				_portal.getHttpServletRequest(renderRequest),
				_portal.getHttpServletResponse(renderResponse));
		}
		catch (Exception exception) {
			throw new PortletException(
				"Unable to include error.jsp", exception);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();
	}

	@Deactivate
	protected void deactivate() {
		_bundle = null;
	}

	private Bundle _bundle;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ExtendedMetaTypeService _extendedMetaTypeService;

	@Reference(
		target = "(&(configuration.field.name=fieldName)(configuration.pid=com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyFieldCustomizerConfiguration))"
	)
	private ConfigurationFieldOptionsProvider
		_fieldNameConfigurationFieldOptionsProvider;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.segments.vocabulary.field.customizer)"
	)
	private ServletContext _servletContext;

	@Reference(
		target = "(&(configuration.field.name=vocabularyName)(configuration.pid=com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyFieldCustomizerConfiguration))"
	)
	private ConfigurationFieldOptionsProvider
		_vocabularyConfigurationFieldOptionsProvider;

}