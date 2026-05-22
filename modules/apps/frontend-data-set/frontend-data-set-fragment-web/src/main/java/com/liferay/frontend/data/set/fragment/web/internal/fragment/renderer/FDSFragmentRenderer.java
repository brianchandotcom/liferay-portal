/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.fragment.web.internal.fragment.renderer;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.data.set.renderer.FDSRenderer;
import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 * @author Marko Cikos
 */
@Component(service = FragmentRenderer.class)
public class FDSFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public JSONObject getConfigurationJSONObject(
		FragmentRendererContext fragmentRendererContext) {

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.read(getClass(), "fds/configuration.json"));

			return _fragmentEntryConfigurationParser.translateConfiguration(
				jsonObject,
				ResourceBundleUtil.getBundle("content.Language", getClass()));
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
		return "table";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "data-set");
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		return FeatureFlagManagerUtil.isEnabled(
			_portal.getCompanyId(httpServletRequest), "LPS-164563");
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(true);

			PrintWriter printWriter = httpServletResponse.getWriter();

			FragmentEntryLink fragmentEntryLink =
				fragmentRendererContext.getFragmentEntryLink();

			JSONObject configurationJSONObject = getConfigurationJSONObject(
				fragmentRendererContext);

			JSONObject itemSelectorJSONObject =
				(JSONObject)_fragmentEntryConfigurationParser.getFieldValue(
					configurationJSONObject,
					fragmentEntryLink.getEditableValuesJSONObject(),
					fragmentRendererContext.getLocale(), "itemSelector");

			String externalReferenceCode = itemSelectorJSONObject.getString(
				"externalReferenceCode");

			ObjectEntry dataSetObjectEntry = null;

			if (Validator.isNotNull(externalReferenceCode)) {
				try {
					ObjectDefinition dataSetObjectDefinition =
						_dataSetObjectDefinitionLocalService.
							fetchObjectDefinitionByExternalReferenceCode(
								"L_DATA_SET", fragmentEntryLink.getCompanyId());

					DefaultObjectEntryManager defaultObjectEntryManager =
						DefaultObjectEntryManagerProvider.provide(
							_dataSetObjectEntryManagerRegistry.
								getObjectEntryManager(
									dataSetObjectDefinition.getCompanyId(),
									dataSetObjectDefinition.getStorageType()));

					dataSetObjectEntry =
						defaultObjectEntryManager.getObjectEntry(
							fragmentEntryLink.getCompanyId(),
							new DefaultDTOConverterContext(
								false, null, null, null, null,
								LocaleUtil.getMostRelevantLocale(), null, null),
							externalReferenceCode, dataSetObjectDefinition,
							null);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get frontend data set with external " +
								"reference code " + externalReferenceCode,
							exception);
					}
				}
			}

			if ((dataSetObjectEntry == null) &&
				fragmentRendererContext.isEditMode()) {

				printWriter.write(
					StringBundler.concat(
						"<div class=\"portlet-msg-info\">",
						_language.get(httpServletRequest, "select-a-data-set"),
						"</div>"));
			}

			if (dataSetObjectEntry == null) {
				return;
			}

			if (!FeatureFlagManagerUtil.isEnabled(
					_portal.getCompanyId(httpServletRequest), "LPD-38564")) {

				_fdsRenderer.render(
					HashMapBuilder.<String, Object>put(
						"namespace",
						fragmentRendererContext.getFragmentElementId()
					).put(
						"style", "fluid"
					).build(),
					fragmentRendererContext.getFragmentElementId(),
					externalReferenceCode, httpServletRequest,
					httpServletResponse, true, null, printWriter);

				return;
			}

			boolean hasTokens = _hasTokens(
				externalReferenceCode, httpServletRequest);

			JSONObject apiURLTokenValuesJSONObject =
				_getAPIURLTokenValuesJSONObject(
					(String)_fragmentEntryConfigurationParser.getFieldValue(
						configurationJSONObject,
						fragmentEntryLink.getEditableValuesJSONObject(),
						fragmentRendererContext.getLocale(),
						"apiURLTokenValues"));

			JSONObject tokenValuesJSONObject = _getTokenResolutionsJSONObject(
				apiURLTokenValuesJSONObject, externalReferenceCode,
				httpServletRequest);

			if (fragmentRendererContext.isEditMode() && hasTokens) {
				printWriter.write(
					_getAPIURLResolutionHTML(
						tokenValuesJSONObject, externalReferenceCode,
						httpServletRequest,
						fragmentRendererContext.getLocale()));
			}

			if (_isResolved(
					tokenValuesJSONObject, externalReferenceCode,
					httpServletRequest)) {

				printWriter.write("<div>");

				_fdsRenderer.render(
					HashMapBuilder.<String, Object>put(
						"namespace",
						fragmentRendererContext.getFragmentElementId()
					).put(
						"style", "fluid"
					).put(
						"tokenResolutions",
						() -> {
							if (hasTokens) {
								return tokenValuesJSONObject;
							}

							return null;
						}
					).build(),
					fragmentRendererContext.getFragmentElementId(),
					externalReferenceCode, httpServletRequest,
					httpServletResponse, true, null, printWriter);

				printWriter.write("</div>");
			}
		}
		catch (Exception exception) {
			_log.error("Unable to render frontend data set", exception);

			throw new IOException(exception);
		}
		finally {
			ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(false);
		}
	}

	private String _getAPIURLResolutionHTML(
		JSONObject apiURLTokenValuesJSONObject, String externalReferenceCode,
		HttpServletRequest httpServletRequest, Locale locale) {

		StringBundler sb = new StringBundler(10);

		sb.append("<div class=\"p-2\"><span class=\"workflow-status\">");
		sb.append("<strong class=\"label ml-2 text-uppercase ");

		if (_isResolved(
				apiURLTokenValuesJSONObject, externalReferenceCode,
				httpServletRequest)) {

			sb.append("label-success\">");
			sb.append(_language.get(locale, "resolved"));
		}
		else {
			sb.append("label-info\">");
			sb.append(_language.get(locale, "not-resolved"));
		}

		sb.append("</strong></span> ");
		sb.append(_language.get(locale, "api-url"));
		sb.append(StringPool.COLON);
		sb.append(StringPool.SPACE);

		Matcher matcher = _pattern.matcher(
			_fdsRenderer.getFDSAPIURL(
				externalReferenceCode, httpServletRequest, true,
				_getTokenResolutionsJSONObject(
					apiURLTokenValuesJSONObject, externalReferenceCode,
					httpServletRequest)));

		sb.append(
			matcher.replaceAll(
				match -> {
					String tokenName = match.group(1);

					String tokenValue = apiURLTokenValuesJSONObject.getString(
						tokenName);

					if (Validator.isNull(tokenValue)) {
						tokenValue = "{" + tokenName + "}";
					}

					String tokenHTML =
						"<span><strong>" + HtmlUtil.escape(tokenValue) +
							"</strong></span>";

					return Matcher.quoteReplacement(tokenHTML);
				}));

		sb.append("</div>");

		return sb.toString();
	}

	private JSONObject _getAPIURLTokenValuesJSONObject(String value) {
		if (Validator.isNull(value)) {
			return _jsonFactory.createJSONObject();
		}

		try {
			return _jsonFactory.createJSONObject(value);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to serialize APIURLTokenValues to JSON: " + value,
					jsonException);
			}
		}

		return null;
	}

	private Set<String> _getTokenNames(
		String externalReferenceCode, HttpServletRequest httpServletRequest) {

		Set<String> tokenNames = new HashSet<>();

		Matcher matcher = _pattern.matcher(
			_fdsRenderer.getFDSAPIURL(
				externalReferenceCode, httpServletRequest, false, null));

		while (matcher.find()) {
			tokenNames.add(matcher.group(1));
		}

		return tokenNames;
	}

	private JSONObject _getTokenResolutionsJSONObject(
		JSONObject apiURLTokenValuesJSONObject, String externalReferenceCode,
		HttpServletRequest httpServletRequest) {

		Set<String> tokenNames = _getTokenNames(
			externalReferenceCode, httpServletRequest);

		JSONObject tokenResolutionsJSONObject = _jsonFactory.createJSONObject();

		for (String tokenName : tokenNames) {
			String tokenValue = _getTokenValue(
				apiURLTokenValuesJSONObject, tokenName);

			if (Validator.isNotNull(tokenValue)) {
				tokenResolutionsJSONObject.put(
					tokenName, HtmlUtil.escape(tokenValue));
			}
			else {
				tokenResolutionsJSONObject.remove(tokenName);
			}
		}

		return tokenResolutionsJSONObject;
	}

	private String _getTokenValue(
		JSONObject apiURLTokenValuesJSONObject,
		HttpServletRequest httpServletRequest, String tokenName) {

		JSONObject mappingJSONObject =
			apiURLTokenValuesJSONObject.getJSONObject(tokenName);

		if (mappingJSONObject == null) {
			return apiURLTokenValuesJSONObject.getString(tokenName);
		}

		String fieldId = mappingJSONObject.getString("fieldId");

			if (Objects.equals(fieldId, "externalReferenceCode")) {
				return mappingJSONObject.getString("externalReferenceCode");
			}

		return mappingJSONObject.getString("classPK");
	}

		return apiURLTokenValuesJSONObject.getString(tokenName);
	}

	private boolean _hasTokens(
		String externalReferenceCode, HttpServletRequest httpServletRequest) {

		Matcher matcher = _pattern.matcher(
			_fdsRenderer.getFDSAPIURL(
				externalReferenceCode, httpServletRequest, false, null));

		return matcher.find();
	}

	private boolean _isResolved(
		JSONObject tokenValuesJSONObject, String externalReferenceCode,
		HttpServletRequest httpServletRequest) {

		Matcher matcher = _pattern.matcher(
			_fdsRenderer.getFDSAPIURL(
				externalReferenceCode, httpServletRequest, true,
				tokenValuesJSONObject));

		return !matcher.find();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSFragmentRenderer.class);

	private static final Pattern _pattern = Pattern.compile("\\{(.*?)\\}");

	@Reference
	private ObjectDefinitionLocalService _dataSetObjectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _dataSetObjectEntryManagerRegistry;

	@Reference
	private FDSRenderer _fdsRenderer;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}