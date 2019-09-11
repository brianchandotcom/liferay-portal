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

package com.liferay.asset.auto.tagger.opennlp.internal;

import com.liferay.asset.auto.tagger.opennlp.OpenNLPDocumentAssetAutoTagger;
import com.liferay.asset.auto.tagger.opennlp.internal.configuration.OpenNLPDocumentAssetAutoTaggerCompanyConfiguration;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
@Component(
	service = {
		OpenNLPDocumentAssetAutoTagger.class,
		OpenNLPDocumentAssetAutoTaggerImpl.class
	}
)
public class OpenNLPDocumentAssetAutoTaggerImpl
	implements OpenNLPDocumentAssetAutoTagger {

	@Override
	public Collection<String> getTagNames(
			long companyId, String content, Locale locale, String mimeType)
		throws Exception {

		return getTagNames(companyId, () -> content, locale, mimeType);
	}

	@Override
	public Collection<String> getTagNames(
			long companyId, String content, String mimeType)
		throws Exception {

		return getTagNames(companyId, content, null, mimeType);
	}

	public Collection<String> getTagNames(
			long companyId, Supplier<String> textSupplier, Locale locale,
			String mimeType)
		throws Exception {

		if (Objects.nonNull(locale) &&
			!Objects.equals(
				locale.getLanguage(), LocaleUtil.ENGLISH.getLanguage())) {

			return Collections.emptyList();
		}

		if (!_supportedContentTypes.contains(mimeType)) {
			return Collections.emptyList();
		}

		SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(
			_sentenceModelHolder.getModel());

		TokenizerME tokenizerME = new TokenizerME(
			_tokenizerModelHolder.getModel());

		List<TokenNameFinderModel> tokenNameFinderModels =
			_tokenNameFinderModelsHolder.getModels();

		OpenNLPDocumentAssetAutoTaggerCompanyConfiguration
			openNLPDocumentAssetAutoTaggerCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					OpenNLPDocumentAssetAutoTaggerCompanyConfiguration.class,
					companyId);

		return Stream.of(
			sentenceDetectorME.sentDetect(textSupplier.get())
		).map(
			tokenizerME::tokenize
		).map(
			tokens -> _getTagNames(
				tokenNameFinderModels, tokens,
				openNLPDocumentAssetAutoTaggerCompanyConfiguration.
					confidenceThreshold())
		).flatMap(
			Arrays::stream
		).collect(
			Collectors.toSet()
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws IOException {
		Bundle bundle = bundleContext.getBundle();

		_sentenceModelHolder = new SentenceModelHolder(bundle);
		_tokenizerModelHolder = new TokenizerModelHolder(bundle);
		_tokenNameFinderModelsHolder = new TokenNameFinderModelsHolder(bundle);
	}

	private String[] _getTagNames(
		List<TokenNameFinderModel> tokenNameFinderModels, String[] tokens,
		double confidenceThreshold) {

		Stream<TokenNameFinderModel> stream = tokenNameFinderModels.stream();

		return Span.spansToStrings(
			stream.map(
				NameFinderME::new
			).map(
				nameFinderME -> nameFinderME.find(tokens)
			).flatMap(
				Arrays::stream
			).filter(
				span -> span.getProb() > confidenceThreshold
			).collect(
				Collectors.toList()
			).toArray(
				new Span[0]
			),
			tokens);
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

	@Reference
	private ConfigurationProvider _configurationProvider;

	private SentenceModelHolder _sentenceModelHolder;
	private TokenizerModelHolder _tokenizerModelHolder;
	private TokenNameFinderModelsHolder _tokenNameFinderModelsHolder;

	private static class SentenceModelHolder {

		public SentenceModel getModel() throws IOException {
			SentenceModel sentenceModel = _sentenceModel;

			if (sentenceModel != null) {
				return sentenceModel;
			}

			synchronized (this) {
				if (_sentenceModel == null) {
					_sentenceModel = new SentenceModel(
						_bundle.getResource(
							"/lib/org.apache.opennlp.model.en.sent-1.5.0-" +
								"bin.bin"));
				}

				return _sentenceModel;
			}
		}

		private SentenceModelHolder(Bundle bundle) {
			_bundle = bundle;
		}

		private final Bundle _bundle;
		private volatile SentenceModel _sentenceModel;

	}

	private static class TokenizerModelHolder {

		public TokenizerModel getModel() throws IOException {
			TokenizerModel tokenizerModel = _tokenizerModel;

			if (tokenizerModel != null) {
				return tokenizerModel;
			}

			synchronized (this) {
				if (_tokenizerModel == null) {
					_tokenizerModel = new TokenizerModel(
						_bundle.getResource(
							"/lib/org.apache.opennlp.model.en.token-1.5.0-" +
								"bin.bin"));
				}

				return _tokenizerModel;
			}
		}

		private TokenizerModelHolder(Bundle bundle) {
			_bundle = bundle;
		}

		private final Bundle _bundle;
		private volatile TokenizerModel _tokenizerModel;

	}

	private static class TokenNameFinderModelsHolder {

		public List<TokenNameFinderModel> getModels() throws IOException {
			List<TokenNameFinderModel> tokenNameFinderModels =
				_tokenNameFinderModels;

			if (tokenNameFinderModels != null) {
				return tokenNameFinderModels;
			}

			synchronized (this) {
				if (_tokenNameFinderModels == null) {
					_tokenNameFinderModels = Arrays.asList(
						new TokenNameFinderModel(
							_bundle.getResource(
								"/lib/org.apache.opennlp.model.en.ner." +
									"location-1.5.0-bin.bin")),
						new TokenNameFinderModel(
							_bundle.getResource(
								"/lib/org.apache.opennlp.model.en.ner." +
									"organization-1.5.0-bin.bin")),
						new TokenNameFinderModel(
							_bundle.getResource(
								"/lib/org.apache.opennlp.model.en.ner.person-" +
									"1.5.0-bin.bin")));
				}

				return _tokenNameFinderModels;
			}
		}

		private TokenNameFinderModelsHolder(Bundle bundle) {
			_bundle = bundle;
		}

		private final Bundle _bundle;
		private volatile List<TokenNameFinderModel> _tokenNameFinderModels;

	}

}