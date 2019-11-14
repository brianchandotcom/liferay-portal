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

package com.liferay.fragment.internal.renderer;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.util.FragmentEntryConfigUtil;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = FragmentRendererController.class)
public class FragmentRendererControllerImpl
	implements FragmentRendererController {

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		FragmentRenderer fragmentRenderer = _getFragmentRenderer(
			fragmentRendererContext.getFragmentEntryLink());

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fragmentRenderer.getConfiguration(fragmentRendererContext));

			return _translateConfigurationFields(
				jsonObject, fragmentRendererContext.getLocale());
		}
		catch (JSONException jsone) {
			_log.error(
				"Unable to parse fragment entry link configuration", jsone);
		}

		return StringPool.BLANK;
	}

	@Override
	public String render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		try {
			if (Validator.isNotNull(fragmentEntryLink.getConfiguration())) {
				_fragmentEntryValidator.validateConfiguration(
					fragmentEntryLink.getConfiguration());
			}
		}
		catch (FragmentEntryConfigurationException fece) {
			SessionErrors.add(
				httpServletRequest, "fragmentEntryContentInvalid");

			return _getFragmentEntryConfigurationExceptionMessage(
				httpServletRequest, fece);
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		FragmentRenderer fragmentRenderer = _getFragmentRenderer(
			fragmentEntryLink);

		try {
			fragmentRenderer.render(
				fragmentRendererContext, httpServletRequest,
				new PipingServletResponse(
					httpServletResponse, unsyncStringWriter));
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to render content of fragment entry ",
						fragmentEntryLink.getFragmentEntryId(), ":",
						e.getMessage()),
					e);
			}
			else {
				_log.error(
					StringBundler.concat(
						"Unable to render content of fragment entry ",
						fragmentEntryLink.getFragmentEntryId(), ":",
						e.getMessage()));
			}

			SessionErrors.add(
				httpServletRequest, "fragmentEntryContentInvalid");

			return _getFragmentEntryContentExceptionMessage(
				e, httpServletRequest);
		}

		return unsyncStringWriter.toString();
	}

	private String _getFragmentEntryConfigurationExceptionMessage(
		HttpServletRequest httpServletRequest,
		FragmentEntryConfigurationException fece) {

		StringBundler divSB = new StringBundler(3);

		divSB.append("<div class=\"alert alert-danger m-2\">");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(), FragmentRendererControllerImpl.class);

		StringBundler detailedErrorMessageSB = new StringBundler(4);

		detailedErrorMessageSB.append(
			LanguageUtil.get(
				resourceBundle, "fragment-configuration-is-invalid"));
		detailedErrorMessageSB.append(StringPool.NEW_LINE);
		detailedErrorMessageSB.append(StringPool.NEW_LINE);
		detailedErrorMessageSB.append(fece.getLocalizedMessage());

		String detailedErrorMessage = detailedErrorMessageSB.toString();

		divSB.append(detailedErrorMessage.replaceAll("\\n", "<br>"));

		divSB.append("</div>");

		return divSB.toString();
	}

	private String _getFragmentEntryContentExceptionMessage(
		Exception e, HttpServletRequest httpServletRequest) {

		StringBundler sb = new StringBundler(3);

		sb.append("<div class=\"alert alert-danger m-2\">");

		String errorMessage = "an-unexpected-error-occurred";

		Throwable throwable = e.getCause();

		if (throwable instanceof FragmentEntryContentException) {
			FragmentEntryContentException fece =
				(FragmentEntryContentException)throwable;

			errorMessage = fece.getLocalizedMessage();
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String localizedErrorMessage = LanguageUtil.get(
			themeDisplay.getLocale(), errorMessage);

		sb.append(localizedErrorMessage.replaceAll("\\n", "<br>"));

		sb.append("</div>");

		return sb.toString();
	}

	private FragmentRenderer _getFragmentRenderer(
		FragmentEntryLink fragmentEntryLink) {

		FragmentRenderer fragmentRenderer = null;

		if (Validator.isNotNull(fragmentEntryLink.getRendererKey())) {
			fragmentRenderer = _fragmentRendererTracker.getFragmentRenderer(
				fragmentEntryLink.getRendererKey());
		}

		if (fragmentRenderer == null) {
			fragmentRenderer = _fragmentRendererTracker.getFragmentRenderer(
				FragmentRendererConstants.FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY);
		}

		return fragmentRenderer;
	}

	private String _translateConfigurationFields(
		JSONObject jsonObject, Locale locale) {

		ResourceBundleLoader resourceBundleLoader =
			new AggregateResourceBundleLoader(
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader(),
				_fragmentCollectionContributorTracker.
					getResourceBundleLoader());

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return FragmentEntryConfigUtil.translateConfiguration(
			jsonObject, resourceBundle);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentRendererControllerImpl.class);

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryValidator _fragmentEntryValidator;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

}