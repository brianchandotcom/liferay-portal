/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.renderer.collection.filter.internal;

import com.liferay.fragment.collection.filter.FragmentCollectionFilter;
import com.liferay.fragment.collection.filter.FragmentCollectionFilterRegistry;
import com.liferay.fragment.constants.FragmentConfigurationFieldDataType;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = FragmentRenderer.class)
public class CollectionFilterFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public JSONObject getConfigurationJSONObject(
		FragmentRendererContext fragmentRendererContext) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", LocaleUtil.getMostRelevantLocale(), getClass());

		try {
			String json = StringUtil.read(
				getClass(),
				"/com/liferay/fragment/renderer/collection/filter/internal" +
					"/dependencies/collection-filter-configuration.json");

			return _fragmentEntryConfigurationParser.translateConfiguration(
				_jsonFactory.createJSONObject(json), resourceBundle);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}
	}

	@Override
	public String getIcon() {
		return "filter";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "collection-filter");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		FragmentCollectionFilter fragmentCollectionFilter =
			_fragmentCollectionFilterRegistry.getFragmentCollectionFilter(
				_getInfoFilterKey(fragmentRendererContext));

		if ((fragmentCollectionFilter == null) &&
			!fragmentRendererContext.isEditMode()) {

			return;
		}

		try {
			httpServletRequest.setAttribute(
				FragmentCollectionFilter.class.getName(),
				fragmentCollectionFilter);
			httpServletRequest.setAttribute(
				FragmentRendererContext.class.getName(),
				fragmentRendererContext);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/page.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to render collection filter fragment", exception);
		}
	}

	private String _getInfoFilterKey(
		FragmentRendererContext fragmentRendererContext) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		return GetterUtil.getString(
			_fragmentEntryConfigurationParser.getConfigurationFieldValue(
				fragmentEntryLink.getEditableValuesJSONObject(), "filterKey",
				FragmentConfigurationFieldDataType.STRING));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionFilterFragmentRenderer.class);

	@Reference
	private FragmentCollectionFilterRegistry _fragmentCollectionFilterRegistry;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.renderer.collection.filter.impl)"
	)
	private ServletContext _servletContext;

}