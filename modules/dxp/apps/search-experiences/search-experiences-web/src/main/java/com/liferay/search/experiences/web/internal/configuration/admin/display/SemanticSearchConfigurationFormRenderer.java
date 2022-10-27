/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.web.internal.display.context.SemanticSearchCompanyConfigurationDisplayContext;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.search.experiences.configuration.SemanticSearchConfiguration",
	enabled = false, immediate = true, service = ConfigurationFormRenderer.class
)
public class SemanticSearchConfigurationFormRenderer
	implements ConfigurationFormRenderer {

	@Override
	public String getPid() {
		return SemanticSearchConfiguration.class.getName();
	}

	@Override
	public Map<String, Object> getRequestParameters(
		HttpServletRequest httpServletRequest) {

		return HashMapBuilder.<String, Object>put(
			"assetEntryClassNames",
			ParamUtil.getStringValues(
				httpServletRequest, "assetEntryClassNames")
		).put(
			"cacheTimeout",
			ParamUtil.getInteger(httpServletRequest, "cacheTimeout")
		).put(
			"embeddingVectorDimensions",
			ParamUtil.getInteger(
				httpServletRequest, "embeddingVectorDimensions")
		).put(
			"enableGPU", ParamUtil.getBoolean(httpServletRequest, "enableGPU")
		).put(
			"huggingFaceAccessToken",
			ParamUtil.getString(httpServletRequest, "huggingFaceAccessToken")
		).put(
			"languageIds",
			ParamUtil.getStringValues(httpServletRequest, "languageIds")
		).put(
			"maxCharacterCount",
			ParamUtil.getInteger(httpServletRequest, "maxCharacterCount")
		).put(
			"model", ParamUtil.getString(httpServletRequest, "model")
		).put(
			"modelTimeout",
			ParamUtil.getInteger(httpServletRequest, "modelTimeout")
		).put(
			"sentenceTransformerEnabled",
			ParamUtil.getBoolean(
				httpServletRequest, "sentenceTransformerEnabled")
		).put(
			"sentenceTransformProvider",
			ParamUtil.getString(httpServletRequest, "sentenceTransformProvider")
		).put(
			"textTruncationStrategy",
			ParamUtil.getString(httpServletRequest, "textTruncationStrategy")
		).put(
			"txtaiHostAddress",
			ParamUtil.getString(httpServletRequest, "txtaiHostAddress")
		).build();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688"))) {
			PrintWriter writer = httpServletResponse.getWriter();

			writer.print(
				"<div class=\"alert alert-info\">This feature is not " +
					"available.</div>");

			return;
		}

		SemanticSearchCompanyConfigurationDisplayContext
			semanticSearchCompanyConfigurationDisplayContext =
				new SemanticSearchCompanyConfigurationDisplayContext();

		semanticSearchCompanyConfigurationDisplayContext.
			setAssetEntryClassNames(
				ListUtil.fromArray(
					_semanticSearchConfiguration.assetEntryClassNames()));
		semanticSearchCompanyConfigurationDisplayContext.
			setAvailableAssetEntryClassNames(
				_getAvailableAssetEntryClassNames(httpServletRequest));
		semanticSearchCompanyConfigurationDisplayContext.
			setAvailableEmbeddingVectorDimensions(
				_getAvailableEmbeddingVectorDimensions());
		semanticSearchCompanyConfigurationDisplayContext.
			setAvailableLanguageDisplayNames(
				_getAvailableLanguageDisplayNames(httpServletRequest));
		semanticSearchCompanyConfigurationDisplayContext.
			setAvailableSentenceTranformProviders(
				_getAvailableSentenceTranformProviders(httpServletRequest));
		semanticSearchCompanyConfigurationDisplayContext.
			setAvailableTextTruncationStrategies(
				_getAvailableTextTruncationStrategies(httpServletRequest));
		semanticSearchCompanyConfigurationDisplayContext.setCacheTimeout(
			_semanticSearchConfiguration.cacheTimeout());
		semanticSearchCompanyConfigurationDisplayContext.
			setEmbeddingVectorDimensions(
				_semanticSearchConfiguration.embeddingVectorDimensions());
		semanticSearchCompanyConfigurationDisplayContext.setEnableGPU(
			_semanticSearchConfiguration.enableGPU());
		semanticSearchCompanyConfigurationDisplayContext.
			setHuggingFaceAccessToken(
				_semanticSearchConfiguration.huggingFaceAccessToken());
		semanticSearchCompanyConfigurationDisplayContext.setLanguageIds(
			ListUtil.fromArray(_semanticSearchConfiguration.languageIds()));
		semanticSearchCompanyConfigurationDisplayContext.setMaxCharacterCount(
			_semanticSearchConfiguration.maxCharacterCount());
		semanticSearchCompanyConfigurationDisplayContext.setModel(
			_semanticSearchConfiguration.model());
		semanticSearchCompanyConfigurationDisplayContext.setModelTimeout(
			_semanticSearchConfiguration.modelTimeout());
		semanticSearchCompanyConfigurationDisplayContext.
			setSentenceTransformerEnabled(
				_semanticSearchConfiguration.sentenceTransformerEnabled());
		semanticSearchCompanyConfigurationDisplayContext.
			setSentenceTransformProvider(
				_semanticSearchConfiguration.sentenceTransformProvider());
		semanticSearchCompanyConfigurationDisplayContext.
			setTextTruncationStrategy(
				_semanticSearchConfiguration.textTruncationStrategy());
		semanticSearchCompanyConfigurationDisplayContext.setTxtaiHostAddress(
			_semanticSearchConfiguration.txtaiHostAddress());

		httpServletRequest.setAttribute(
			SemanticSearchCompanyConfigurationDisplayContext.class.getName(),
			semanticSearchCompanyConfigurationDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/semantic_search/configuration.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_semanticSearchConfiguration = ConfigurableUtil.createConfigurable(
			SemanticSearchConfiguration.class, properties);
	}

	private Map<String, String> _getAvailableAssetEntryClassNames(
		HttpServletRequest httpServletRequest) {

		return _sortByValue(
			HashMapBuilder.put(
				"com.liferay.blogs.model.BlogsEntry",
				_language.get(
					httpServletRequest,
					"model.resource.com.liferay.blogs.model.BlogsEntry")
			).put(
				"com.liferay.journal.model.JournalArticle",
				_language.get(
					httpServletRequest,
					"model.resource.com.liferay.journal.model.JournalArticle")
			).put(
				"com.liferay.knowledge.base.model.KBArticle",
				_language.get(
					httpServletRequest,
					"model.resource.com.liferay.knowledge.base.model.KBArticle")
			).put(
				"com.liferay.message.boards.model.MBMessage",
				_language.get(
					httpServletRequest,
					"model.resource.com.liferay.message.boards.model.MBMessage")
			).put(
				"model.resource.com.liferay.wiki.model.WikiPage",
				_language.get(
					httpServletRequest,
					"model.resource.com.liferay.wiki.model.WikiPage")
			).build());
	}

	private List<String> _getAvailableEmbeddingVectorDimensions() {
		return new ArrayList<String>() {
			{
				add("384");
				add("512");
				add("768");
			}
		};
	}

	private Map<String, String> _getAvailableLanguageDisplayNames(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Map<String, String> availableLanguageDisplayNames = new HashMap<>();

		for (Locale locale :
				_language.getCompanyAvailableLocales(
					themeDisplay.getCompanyId())) {

			availableLanguageDisplayNames.put(
				LocaleUtil.toLanguageId(locale),
				locale.getDisplayName(themeDisplay.getLocale()));
		}

		return _sortByValue(availableLanguageDisplayNames);
	}

	private Map<String, String> _getAvailableSentenceTranformProviders(
		HttpServletRequest httpServletRequest) {

		return LinkedHashMapBuilder.put(
			"huggingFace", _language.get(httpServletRequest, "hugging-face")
		).put(
			"txtai", _language.get(httpServletRequest, "txtai")
		).build();
	}

	private Map<String, String> _getAvailableTextTruncationStrategies(
		HttpServletRequest httpServletRequest) {

		return LinkedHashMapBuilder.put(
			"beginning", _language.get(httpServletRequest, "beginning")
		).put(
			"middle", _language.get(httpServletRequest, "middle")
		).put(
			"end", _language.get(httpServletRequest, "end")
		).build();
	}

	private Map<String, String> _sortByValue(Map<String, String> map) {
		Set<Map.Entry<String, String>> entrySet = map.entrySet();

		Stream<Map.Entry<String, String>> stream = entrySet.stream();

		return stream.sorted(
			Map.Entry.comparingByValue()
		).collect(
			Collectors.toMap(
				entry -> entry.getKey(), entry -> entry.getValue(),
				(entry1, entry2) -> entry2, LinkedHashMap::new)
		);
	}

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _language;

	private volatile SemanticSearchConfiguration _semanticSearchConfiguration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.search.experiences.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}