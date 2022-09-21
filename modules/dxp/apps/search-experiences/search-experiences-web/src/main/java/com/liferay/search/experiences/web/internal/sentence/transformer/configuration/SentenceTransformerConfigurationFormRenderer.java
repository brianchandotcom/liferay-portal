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

package com.liferay.search.experiences.web.internal.sentence.transformer.configuration;

import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.configuration.SentenceTransformerConfiguration;
import com.liferay.search.experiences.web.internal.sentence.transformer.configuration.display.context.SentenceTransformerCompanyConfigurationDisplayContext;

import java.io.IOException;

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
	configurationPid = "com.liferay.search.experiences.configuration.SentenceTransformerConfiguration",
	enabled = false, immediate = true, service = ConfigurationFormRenderer.class
)
public class SentenceTransformerConfigurationFormRenderer
	implements ConfigurationFormRenderer {

	@Override
	public String getPid() {
		return SentenceTransformerConfiguration.class.getName();
	}

	@Override
	public Map<String, Object> getRequestParameters(
		HttpServletRequest httpServletRequest) {

		return HashMapBuilder.<String, Object>put(
			"cacheTimeout",
			ParamUtil.getInteger(httpServletRequest, "cacheTimeout")
		).put(
			"embeddingVectorDimensions",
			ParamUtil.getInteger(
				httpServletRequest, "embeddingVectorDimensions")
		).put(
			"enabled", ParamUtil.getBoolean(httpServletRequest, "enabled")
		).put(
			"enableGPU", ParamUtil.getBoolean(httpServletRequest, "enableGPU")
		).put(
			"entryClassNames",
			ParamUtil.getStringValues(httpServletRequest, "entryClassNames")
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
			"sentenceTransformerProvider",
			ParamUtil.getString(
				httpServletRequest, "sentenceTransformerProvider")
		).put(
			"textTruncationStrategy",
			ParamUtil.getString(httpServletRequest, "textTruncationStrategy")
		).put(
			"txtAihost", ParamUtil.getString(httpServletRequest, "txtAihost")
		).build();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		SentenceTransformerCompanyConfigurationDisplayContext
			sentenceTransformerCompanyConfigurationDisplayContext =
				new SentenceTransformerCompanyConfigurationDisplayContext();

		sentenceTransformerCompanyConfigurationDisplayContext.
			setAvailableEmbeddingVectorDimensions(
				_getAvailableEmbeddingVectorDimensions());

		sentenceTransformerCompanyConfigurationDisplayContext.
			setAvailableEntryClassNames(
				_getAvailableEntryClassNames(httpServletRequest));

		sentenceTransformerCompanyConfigurationDisplayContext.
			setAvailableLanguages(_getAvailableLanguages(httpServletRequest));

		sentenceTransformerCompanyConfigurationDisplayContext.
			setAvailableSentenceTranformerProviders(
				_getAvailableSentenceTranformerProviders(httpServletRequest));

		sentenceTransformerCompanyConfigurationDisplayContext.
			setAvailableTextTruncationStrategies(
				_getAvailableTextTruncationStrategies(httpServletRequest));

		sentenceTransformerCompanyConfigurationDisplayContext.setCacheTimeout(
			_sentenceTransformerConfiguration.cacheTimeout());

		sentenceTransformerCompanyConfigurationDisplayContext.
			setEmbeddingVectorDimensions(
				_sentenceTransformerConfiguration.embeddingVectorDimensions());

		sentenceTransformerCompanyConfigurationDisplayContext.setEnabled(
			_sentenceTransformerConfiguration.enabled());

		sentenceTransformerCompanyConfigurationDisplayContext.setEnableGPU(
			_sentenceTransformerConfiguration.enableGPU());

		sentenceTransformerCompanyConfigurationDisplayContext.
			setEntryClassNames(
				ListUtil.fromArray(
					_sentenceTransformerConfiguration.entryClassNames()));

		sentenceTransformerCompanyConfigurationDisplayContext.
			setHuggingFaceAccessToken(
				_sentenceTransformerConfiguration.huggingFaceAccessToken());

		sentenceTransformerCompanyConfigurationDisplayContext.setLanguageIds(
			ListUtil.fromArray(
				_sentenceTransformerConfiguration.languageIds()));

		sentenceTransformerCompanyConfigurationDisplayContext.
			setMaxCharacterCount(
				_sentenceTransformerConfiguration.maxCharacterCount());

		sentenceTransformerCompanyConfigurationDisplayContext.setModel(
			_sentenceTransformerConfiguration.model());

		sentenceTransformerCompanyConfigurationDisplayContext.setModelTimeout(
			_sentenceTransformerConfiguration.modelTimeout());

		sentenceTransformerCompanyConfigurationDisplayContext.
			setSentenceTransformerProvider(
				_sentenceTransformerConfiguration.
					sentenceTransformerProvider());

		sentenceTransformerCompanyConfigurationDisplayContext.
			setTextTruncationStrategy(
				_sentenceTransformerConfiguration.textTruncationStrategy());

		sentenceTransformerCompanyConfigurationDisplayContext.setTxtAihost(
			_sentenceTransformerConfiguration.txtAiHost());

		httpServletRequest.setAttribute(
			SentenceTransformerCompanyConfigurationDisplayContext.class.
				getName(),
			sentenceTransformerCompanyConfigurationDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/sentence_transformer/configuration.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_sentenceTransformerConfiguration = ConfigurableUtil.createConfigurable(
			SentenceTransformerConfiguration.class, properties);
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

	private Map<String, String> _getAvailableEntryClassNames(
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

	private Map<String, String> _getAvailableLanguages(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Map<String, String> availableLanguages = new HashMap<>();

		for (Locale locale :
				_language.getCompanyAvailableLocales(
					themeDisplay.getCompanyId())) {

			availableLanguages.put(
				LocaleUtil.toLanguageId(locale),
				locale.getDisplayName(themeDisplay.getLocale()));
		}

		return _sortByValue(availableLanguages);
	}

	private Map<String, String> _getAvailableSentenceTranformerProviders(
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

	private volatile SentenceTransformerConfiguration
		_sentenceTransformerConfiguration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.search.experiences.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}