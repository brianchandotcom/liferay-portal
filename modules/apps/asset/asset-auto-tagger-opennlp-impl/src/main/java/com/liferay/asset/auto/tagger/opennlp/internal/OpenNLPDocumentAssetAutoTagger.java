/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.opennlp.internal;

import com.liferay.asset.auto.tagger.opennlp.internal.configuration.OpenNLPDocumentAssetAutoTaggerCompanyConfiguration;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = OpenNLPDocumentAssetAutoTagger.class)
public class OpenNLPDocumentAssetAutoTagger {

	public Collection<String> getTagNames(
			long companyId, String content, Locale locale, String mimeType)
		throws Exception {

		return getTagNames(companyId, () -> content, locale, mimeType);
	}

	public Collection<String> getTagNames(
			long companyId, String content, String mimeType)
		throws Exception {

		return getTagNames(companyId, content, null, mimeType);
	}

	public Collection<String> getTagNames(
			long companyId, Supplier<String> textSupplier, Locale locale,
			String mimeType)
		throws Exception {

		if ((Objects.nonNull(locale) &&
			 !Objects.equals(
				 locale.getLanguage(), LocaleUtil.ENGLISH.getLanguage())) ||
			!_supportedContentTypes.contains(mimeType)) {

			return Collections.emptyList();
		}

		SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(
			_sentenceModelDCLSingleton.getSingleton(
				this::_createSentenceModel));

		TokenizerME tokenizerME = new TokenizerME(
			_tokenizerModelDCLSingleton.getSingleton(
				this::_createTokenizerModel));

		List<TokenNameFinderModel> tokenNameFinderModels =
			_tokenNameFinderModelsDCLSingleton.getSingleton(
				this::_createTokenNameFinderModels);

		OpenNLPDocumentAssetAutoTaggerCompanyConfiguration
			openNLPDocumentAssetAutoTaggerCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					OpenNLPDocumentAssetAutoTaggerCompanyConfiguration.class,
					companyId);

		Set<String> tagNames = new HashSet<>();

		for (String sentence :
				sentenceDetectorME.sentDetect(textSupplier.get())) {

			Collections.addAll(
				tagNames,
				_getTagNames(
					tokenNameFinderModels, tokenizerME.tokenize(sentence),
					openNLPDocumentAssetAutoTaggerCompanyConfiguration.
						confidenceThreshold()));
		}

		return tagNames;
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws IOException {
		_bundle = bundleContext.getBundle();
	}

	private SentenceModel _createSentenceModel() {
		try {
			return new SentenceModel(
				_bundle.getResource("org.apache.opennlp.model.en.sent.bin"));
		}
		catch (IOException ioException) {
			return ReflectionUtil.throwException(ioException);
		}
	}

	private TokenizerModel _createTokenizerModel() {
		try {
			return new TokenizerModel(
				_bundle.getResource("org.apache.opennlp.model.en.token.bin"));
		}
		catch (IOException ioException) {
			return ReflectionUtil.throwException(ioException);
		}
	}

	private List<TokenNameFinderModel> _createTokenNameFinderModels() {
		try {
			return Arrays.asList(
				new TokenNameFinderModel(
					_bundle.getResource(
						"org.apache.opennlp.model.en.ner.location.bin")),
				new TokenNameFinderModel(
					_bundle.getResource(
						"org.apache.opennlp.model.en.ner.organization.bin")),
				new TokenNameFinderModel(
					_bundle.getResource(
						"org.apache.opennlp.model.en.ner.person.bin")));
		}
		catch (IOException ioException) {
			return ReflectionUtil.throwException(ioException);
		}
	}

	private String[] _getTagNames(
		List<TokenNameFinderModel> tokenNameFinderModels, String[] tokens,
		double confidenceThreshold) {

		List<Span> spans = new ArrayList<>();

		for (TokenNameFinderModel tokenNameFinderModel :
				tokenNameFinderModels) {

			NameFinderME nameFinderME = new NameFinderME(tokenNameFinderModel);

			Span[] nameSpans = nameFinderME.find(tokens);

			for (Span nameSpan : nameSpans) {
				if (nameSpan.getProb() > confidenceThreshold) {
					spans.add(nameSpan);
				}
			}
		}

		return Span.spansToStrings(spans.toArray(new Span[0]), tokens);
	}

	private static final Set<String> _supportedContentTypes = new HashSet<>(
		Arrays.asList(
			"application/epub+zip", "application/vnd.apple.pages.13",
			"application/vnd.google-apps.document",
			"application/vnd.oasis.opendocument.text",
			"application/vnd.openxmlformats-officedocument.wordprocessingml." +
				"document",
			ContentTypes.APPLICATION_MSWORD, ContentTypes.APPLICATION_PDF,
			ContentTypes.APPLICATION_TEXT, ContentTypes.TEXT,
			ContentTypes.TEXT_PLAIN, ContentTypes.TEXT_HTML,
			ContentTypes.TEXT_HTML_UTF8));

	private Bundle _bundle;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private final DCLSingleton<SentenceModel> _sentenceModelDCLSingleton =
		new DCLSingleton<>();
	private final DCLSingleton<TokenizerModel> _tokenizerModelDCLSingleton =
		new DCLSingleton<>();
	private final DCLSingleton<List<TokenNameFinderModel>>
		_tokenNameFinderModelsDCLSingleton = new DCLSingleton<>();

}