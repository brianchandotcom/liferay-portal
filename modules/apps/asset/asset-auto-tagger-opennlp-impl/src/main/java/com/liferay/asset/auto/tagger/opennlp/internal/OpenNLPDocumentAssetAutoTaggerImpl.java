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

import com.liferay.asset.auto.tagger.opennlp.api.OpenNLPDocumentAssetAutoTagger;
import com.liferay.asset.auto.tagger.opennlp.api.configuration.OpenNLPDocumentAssetAutoTagCompanyConfiguration;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
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

/**
 * @author Cristina González
 */
@Component(service = OpenNLPDocumentAssetAutoTagger.class)
public class OpenNLPDocumentAssetAutoTaggerImpl
	implements OpenNLPDocumentAssetAutoTagger {

	@Override
	public Collection<String> getTagNames(
			OpenNLPDocumentAssetAutoTagCompanyConfiguration
				openNLPDocumentAssetAutoTagCompanyConfiguration,
			String content, Locale locale, String mimeType)
		throws Exception {

		if (Objects.nonNull(locale) &&
			!Objects.equals(
				locale.getLanguage(), Locale.ENGLISH.getLanguage())) {

			return Collections.emptyList();
		}

		if (!openNLPDocumentAssetAutoTagCompanyConfiguration.enabled()) {
			return Collections.emptyList();
		}

		if (!_supportedContentTypes.contains(mimeType)) {
			return Collections.emptyList();
		}

		SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(
			_sentenceModel);

		TokenizerME tokenizerME = new TokenizerME(_tokenizerModel);

		return Stream.of(
			sentenceDetectorME.sentDetect(content)
		).map(
			tokenizerME::tokenize
		).map(
			tokens -> _getTagNames(
				tokens,
				openNLPDocumentAssetAutoTagCompanyConfiguration.
					confidenceThreshold())
		).flatMap(
			Arrays::stream
		).collect(
			Collectors.toSet()
		);
	}

	@Override
	public Collection<String> getTagNames(
			OpenNLPDocumentAssetAutoTagCompanyConfiguration
				openNLPDocumentAssetAutoTagCompanyConfiguration,
			String content, String mimeType)
		throws Exception {

		return getTagNames(
			openNLPDocumentAssetAutoTagCompanyConfiguration, content, null,
			mimeType);
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws IOException {
		Bundle bundle = bundleContext.getBundle();

		_sentenceModel = new SentenceModel(
			bundle.getResource(
				"/lib/org.apache.opennlp.model.en.sent-1.5.0-bin.bin"));
		_tokenizerModel = new TokenizerModel(
			bundle.getResource(
				"/lib/org.apache.opennlp.model.en.token-1.5.0-bin.bin"));
		_tokenNameFinderModels = Arrays.asList(
			new TokenNameFinderModel(
				bundle.getResource(
					"/lib/org.apache.opennlp.model.en.ner.location-1.5.0-" +
						"bin.bin")),
			new TokenNameFinderModel(
				bundle.getResource(
					"/lib/org.apache.opennlp.model.en.ner.organization-1.5.0-" +
						"bin.bin")),
			new TokenNameFinderModel(
				bundle.getResource(
					"/lib/org.apache.opennlp.model.en.ner.person-1.5.0-" +
						"bin.bin")));
	}

	private String[] _getTagNames(String[] tokens, double confidenceThreshold) {
		Stream<TokenNameFinderModel> stream = _tokenNameFinderModels.stream();

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

	private SentenceModel _sentenceModel;
	private TokenizerModel _tokenizerModel;
	private List<TokenNameFinderModel> _tokenNameFinderModels;

}